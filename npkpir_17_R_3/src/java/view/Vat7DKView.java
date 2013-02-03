/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PodatnikDAO;
import dao.VatDAO;
import embeddable.TKodUS;
import entity.Podatnik;
import entity.Vatpoz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

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
    @Inject Vatpoz selected;
    @Inject PodatnikDAO podatnikDAO;
    @ManagedProperty(value="#{TKodUS}")
    private TKodUS tKodUS;

    public Vat7DKView() {
        lista = new ArrayList<>();
    }
    
    
    
    @PostConstruct
    private void init(){
        lista = (List<Vatpoz>) vatDAO.findVatPod(wpisView.getRokWpisu().toString(), wpisView.getPodatnikWpisu());
    }
    
    public void oblicz(){
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        Podatnik pod = podatnikDAO.find(podatnik);
        selected.setPodatnik(podatnik);
        selected.setRok(rok);
        selected.setMiesiac(mc);
        selected.setKodurzedu(tKodUS.getLista().get(pod.getUrzadskarbowy()));
        selected.setNazwaurzedu(pod.getUrzadskarbowy());
        //selected.getCelzlozenia() z formularza
        selected.getAdres().setNIP(pod.getNip());
        selected.getAdres().setImiePierwsze(pod.getImie());
        selected.getAdres().setNazwisko(pod.getNazwisko());
        selected.getAdres().setDataUrodzenia(pod.getDataurodzenia());
        selected.getAdres().setWojewodztwo(pod.getWojewodztwo());
        selected.getAdres().setPowiat(pod.getPowiat());
        selected.getAdres().setGmina(pod.getGmina());
        selected.getAdres().setUlica(pod.getUlica());
        selected.getAdres().setNrDomu(pod.getNrdomu());
        selected.getAdres().setNrLokalu(pod.getNrlokalu());
        selected.getAdres().setMiejscowosc(pod.getMiejscowosc());
        selected.getAdres().setKodPocztowy(pod.getKodpocztowy());
        selected.getAdres().setPoczta(pod.getPoczta());
        //daneszczegolen zformularza
        selected.setKwotaautoryzacja(pod.getKwotaautoryzujaca().get(pod.getKwotaautoryzujaca().size()-1));
                
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
    
    
}

