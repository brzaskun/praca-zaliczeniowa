/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EvpozycjaDAO;
import dao.EwidencjeVatDAO;
import dao.PodatnikDAO;
import dao.VatDAO;
import embeddable.Daneteleadresowe;
import embeddable.EVatwpis;
import embeddable.PozycjeSzczegoloweVAT;
import embeddable.TKodUS;
import entity.Podatnik;
import entity.Vatpoz;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScope
public class Vat7DKView implements Serializable {
    private List<Vatpoz> lista;
    @Inject private VatDAO vatDAO;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject private Vatpoz selected;
    @Inject private PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT;
    @Inject private Daneteleadresowe adres;
    @Inject PodatnikDAO podatnikDAO;
    @Inject EwidencjeVatDAO ewidencjeVatDAO;
    @Inject private TKodUS tKodUS;
    @Inject EvpozycjaDAO evpozycjaDAO;

    public Vat7DKView() {
        lista = new ArrayList<>();
    }
    
    
    
    @PostConstruct
    private void init(){
        lista = (List<Vatpoz>) vatDAO.findVatPod(wpisView.getRokWpisu().toString(), wpisView.getPodatnikWpisu());
    }
    
    public void oblicz() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        Podatnik pod = podatnikDAO.find(podatnik);
        HashMap<String, EVatwpis> sumaewidencji = ewidencjeVatDAO.find(rok, mc, podatnik).getSumaewidencji();
        Collection <EVatwpis> wyciagnieteewidencje =  sumaewidencji.values();
        for (EVatwpis ew : wyciagnieteewidencje){
            String nazpola = ew.getEwidencja().getPole();
            String nrpolanetto = evpozycjaDAO.find(nazpola).getNrpolanetto();
            String nrpolavat = evpozycjaDAO.find(nazpola).getNrpolavat();
            String netto = String.valueOf(ew.getNetto());
            String vat = String.valueOf(ew.getVat());
            String zrobnionenetto = "selected.getPozycjeszczegolowe().setPole"+nrpolanetto+"("+netto+")";
            String cyfra = "1";
            Class[] paramString = new Class[1];	
            paramString[0] = String.class;
            Method met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPole"+nrpolanetto, paramString);
            met.invoke(pozycjeSzczegoloweVAT, new String(netto));
            if(nrpolavat!=null){
                met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPole"+nrpolavat, paramString);
                met.invoke(pozycjeSzczegoloweVAT, new String(vat));
            }
        }
        selected.setPozycjeszczegolowe(pozycjeSzczegoloweVAT);
        selected.setPodatnik(podatnik);
        selected.setRok(rok);
        selected.setMiesiac(mc);
        selected.setKodurzedu(tKodUS.getLista().get(pod.getUrzadskarbowy()));
        selected.setNazwaurzedu(pod.getUrzadskarbowy());
        //selected.getCelzlozenia() jezeli jest deklaracja to korekta
        adres.setNIP(pod.getNip());
        adres.setImiePierwsze(pod.getImie());
        adres.setNazwisko(pod.getNazwisko());
        adres.setDataUrodzenia(pod.getDataurodzenia());
        adres.setWojewodztwo(pod.getWojewodztwo());
        adres.setPowiat(pod.getPowiat());
        adres.setGmina(pod.getGmina());
        adres.setUlica(pod.getUlica());
        adres.setNrDomu(pod.getNrdomu());
        adres.setNrLokalu(pod.getNrlokalu());
        adres.setMiejscowosc(pod.getMiejscowosc());
        adres.setKodPocztowy(pod.getKodpocztowy());
        adres.setPoczta(pod.getPoczta());
        selected.setAdres(adres);
        RequestContext.getCurrentInstance().update("vat7:");
        //dodac kwota atoryzujaca z formularza
        //dodac sumowanie        
    }

    public List<Vatpoz> getLista() {
        return lista;
    }

    public void setLista(List<Vatpoz> lista) {
        this.lista = lista;
    }

    public VatDAO getVatDAO() {
        return vatDAO;
    }

    public void setVatDAO(VatDAO vatDAO) {
        this.vatDAO = vatDAO;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Vatpoz getSelected() {
        return selected;
    }

    public void setSelected(Vatpoz selected) {
        this.selected = selected;
    }

    public PozycjeSzczegoloweVAT getPozycjeSzczegoloweVAT() {
        return pozycjeSzczegoloweVAT;
    }

    public void setPozycjeSzczegoloweVAT(PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT) {
        this.pozycjeSzczegoloweVAT = pozycjeSzczegoloweVAT;
    }
    
    
}

