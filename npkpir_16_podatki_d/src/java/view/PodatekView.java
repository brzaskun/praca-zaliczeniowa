/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PodStawkiDAO;
import dao.PodatnikDAO;
import dao.ZobowiazanieDAO;
import embeddable.Kolmn;
import entity.Dok;
import entity.Podatnik;
import entity.Podstawki;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean(name="PodatekView")
@RequestScoped
public class PodatekView implements Serializable{
    @ManagedProperty(value="#{DokTabView.obiektDOKmrjsfSel}")
    private ArrayList<Dok> oDOK;
    @Inject
    PodatnikDAO podatnikDAO;
    @Inject
    PodStawkiDAO podstawkiDAO;
    @Inject
    private Podatnik selected;
    @Inject
    private ZobowiazanieDAO zv;
    @ManagedProperty(value="#{wpisView}")
    private WpisView wpisView;
    private BigDecimal przychody;
    private BigDecimal koszty;
    private BigDecimal inwestycje;
    private BigDecimal dochód;
    private BigDecimal podatek;
    private String opodatkowanie;
    private String rokmiesiac;
  
    public PodatekView() {
        przychody = BigDecimal.valueOf(0);
        koszty = BigDecimal.valueOf(0);
        inwestycje = BigDecimal.valueOf(0);
    }
    
    @PostConstruct
    private void init(){
        String nazwapodatnika = GuestView.getPodatnikString();
        try{
        selected = podatnikDAO.find(nazwapodatnika);
        } catch (Exception e){}
    }
  
    
  
    public void sprawozdaniePodatkowe(){
        MathContext mc = new MathContext(10, RoundingMode.HALF_UP);
        Iterator it;
        List<Dok> lista = new ArrayList<Dok>();
        lista.addAll(getoDOK());
        it = lista.iterator();
        while(it.hasNext()){
            Dok tmp = (Dok) it.next();
            Kolmn kolmn = new Kolmn();
            if(tmp.getPkpirKol().equals(kolmn.getKolumnPrzychody().get(0) )||tmp.getPkpirKol().equals(kolmn.getKolumnPrzychody().get(1) )){
                przychody = przychody.add(BigDecimal.valueOf(tmp.getKwota())).setScale(2, RoundingMode.HALF_EVEN);
            } else if (tmp.getPkpirKol().equals(kolmn.getKolumnST().get(0) )||tmp.getPkpirKol().equals(kolmn.getKolumnST().get(1) )) {
                inwestycje = inwestycje.add(BigDecimal.valueOf(tmp.getKwota())).setScale(2, RoundingMode.HALF_EVEN);
            } else {
                koszty = koszty.add(BigDecimal.valueOf(tmp.getKwota())).setScale(2, RoundingMode.HALF_EVEN);
            }
        }
        
        dochód = (przychody.subtract(koszty));
        dochód = dochód.setScale(0, RoundingMode.HALF_EVEN);
        wpisView = new WpisView();
        String poszukiwany = wpisView.getPodatnikWpisu();
        Podatnik selected=podatnikDAO.find(poszukiwany);
        int index = selected.getPodatekdochodowy().size()-1;
        opodatkowanie = selected.getPodatekdochodowy().get(index).getParametr();
        rokmiesiac = selected.getPodatekdochodowy().get(index).getRokOd();
        String rodzajop = opodatkowanie;
        Podstawki tmpY = podstawkiDAO.find(Integer.parseInt(rokmiesiac));
        Double stawka = 0.0;
        switch (rodzajop){
            case "zasady ogólne" :
                stawka = tmpY.getStawka1();
                podatek = (dochód.multiply(BigDecimal.valueOf(stawka)));
                podatek = podatek.subtract(BigDecimal.valueOf(tmpY.getKwotawolna()));
                        podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                break;
            case "podatek liniowy" :
                stawka = tmpY.getStawkaliniowy();
                podatek = (dochód.multiply(BigDecimal.valueOf(stawka)));
                        podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                break;
            case "ryczałt" :
                stawka = tmpY.getStawkaryczalt1();
                podatek = (przychody.multiply(BigDecimal.valueOf(stawka)));
                        podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                break;
        }
        rokmiesiac = rokmiesiac+"/"+selected.getPodatekdochodowy().get(index).getMcOd();
    }
    
     public String getOpodatkowanie() {
        return opodatkowanie;
    }

    public void setOpodatkowanie(String opodatkowanie) {
        this.opodatkowanie = opodatkowanie;
    }

    
    public PodatnikDAO getPodatnikDAO() {
        return podatnikDAO;
    }

    public void setPodatnikDAO(PodatnikDAO podatnikDAO) {
        this.podatnikDAO = podatnikDAO;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public String getRokmiesiac() {
        return rokmiesiac;
    }

    public void setRokmiesiac(String rokmiesiac) {
        this.rokmiesiac = rokmiesiac;
    }

    
    
    public BigDecimal getPrzychody() {
        return przychody;
    }

    public void setPrzychody(BigDecimal przychody) {
        this.przychody = przychody;
    }

    public BigDecimal getKoszty() {
        return koszty;
    }

    public void setKoszty(BigDecimal koszty) {
        this.koszty = koszty;
    }

    public BigDecimal getInwestycje() {
        return inwestycje;
    }

    public void setInwestycje(BigDecimal inwestycje) {
        this.inwestycje = inwestycje;
    }

    public BigDecimal getDochód() {
        return dochód;
    }

    public void setDochód(BigDecimal dochód) {
        this.dochód = dochód;
    }

    public BigDecimal getPodatek() {
        return podatek;
    }

    public void setPodatek(BigDecimal podatek) {
        this.podatek = podatek;
    }

    public ArrayList<Dok> getoDOK() {
        return oDOK;
    }

    public void setoDOK(ArrayList<Dok> oDOK) {
        this.oDOK = oDOK;
    }

    public PodStawkiDAO getPodstawkiDAO() {
        return podstawkiDAO;
    }

    public void setPodstawkiDAO(PodStawkiDAO podstawkiDAO) {
        this.podstawkiDAO = podstawkiDAO;
    }

    public Podatnik getSelected() {
        return selected;
    }

    public void setSelected(Podatnik selected) {
        this.selected = selected;
    }

    public ZobowiazanieDAO getZv() {
        return zv;
    }

    public void setZv(ZobowiazanieDAO zv) {
        this.zv = zv;
    }

}
