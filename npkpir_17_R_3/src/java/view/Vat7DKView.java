/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjevatDAO;
import dao.EwidencjeVatDAO;
import dao.PodatnikDAO;
import deklaracjaVAT7_13.VAT713;
import embeddable.Daneteleadresowe;
import embeddable.EVatwpis;
import embeddable.Parametr;
import embeddable.PozycjeSzczegoloweVAT;
import embeddable.TKodUS;
import embeddable.Vatpoz;
import entity.Deklaracjevat;
import entity.Podatnik;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    @Inject private Deklaracjevat pobranadeklaracja;
    @Inject private Deklaracjevat nowadeklaracja;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject private Vatpoz selected;
    @Inject private PozycjeSzczegoloweVAT pozycjeSzczegoloweVAT;
    @Inject private Daneteleadresowe adres;
    @Inject PodatnikDAO podatnikDAO;
    @Inject EwidencjeVatDAO ewidencjeVatDAO;
    @Inject private TKodUS tKodUS;
    @Inject private DeklaracjevatDAO deklaracjevatDAO;
    
    
    

    public Vat7DKView() {
    }
    
    
    
    
    public void oblicz() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        Podatnik pod = podatnikDAO.find(podatnik);
        HashMap<String, EVatwpis> sumaewidencji = ewidencjeVatDAO.find(rok, mc, podatnik).getSumaewidencji();
        Collection <EVatwpis> wyciagnieteewidencje =  sumaewidencji.values();
        for (EVatwpis ew : wyciagnieteewidencje){
            String nrpolanetto = ew.getEwidencja().getNrpolanetto();
            String nrpolavat = ew.getEwidencja().getNrpolavat();
            String netto = String.valueOf(ew.getNetto());
            String vat = String.valueOf(ew.getVat());
            Class[] paramString = new Class[1];	
            paramString[0] = String.class;
            Method met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPole"+nrpolanetto, paramString);
            met.invoke(pozycjeSzczegoloweVAT, new String(netto));
            if(nrpolavat!=null){
                met = PozycjeSzczegoloweVAT.class.getDeclaredMethod("setPole"+nrpolavat, paramString);
                met.invoke(pozycjeSzczegoloweVAT, new String(vat));
            }
        }
        
        //a gdzie sumy????
        selected.setPozycjeszczegolowe(pozycjeSzczegoloweVAT);
        selected.setPodatnik(podatnik);
        selected.setRok(rok);
        String mcx = String.valueOf(Integer.parseInt(mc));
        selected.setMiesiac(mcx);
        selected.setKodurzedu(tKodUS.getLista().get(pod.getUrzadskarbowy()));
        selected.setNazwaurzedu(pod.getUrzadskarbowy());
       
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
        String kwotaautoryzujaca = null;
        List<Parametr> listakwotaautoryzujaca = pod.getKwotaautoryzujaca();
        for(Parametr par : listakwotaautoryzujaca){
            if(par.getRokOd().equals(rok)){
                kwotaautoryzujaca = par.getParametr();
                break;
            }
        }
        selected.setKwotaautoryzacja(kwotaautoryzujaca);
        stworzdeklaracje();
        RequestContext.getCurrentInstance().update("vat7:");
               
    }

    private void stworzdeklaracje() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
         try{
            pobranadeklaracja =  deklaracjevatDAO.findDeklaracje(wpisView.getRokWpisu().toString(), wpisView.getMiesiacWpisu(), wpisView.getPodatnikWpisu());
            selected.setCelzlozenia("2");
        } catch (Exception e){
            selected.setCelzlozenia("1");
        }
        VAT713 vat713 = new VAT713(selected);
        String wiersz = vat713.getWiersz();
        System.out.println(wiersz);
        nowadeklaracja.setDeklaracja(wiersz);
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

