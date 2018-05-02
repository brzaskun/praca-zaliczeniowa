/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.Kolmn;
import beansPIT.WyliczPodatekZasadyOgolne;
import dao.PodStawkiDAO;
import dao.PodatnikDAO;
import dao.ZobowiazanieDAO;
import entity.Dok;
import entity.KwotaKolumna1;
import entity.Podatnik;
import entity.Podstawki;
import error.E;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean(name="PodatekView")
@RequestScoped
public class PodatekView implements Serializable{
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value="#{DokTabView.dokumentypodatnika}")
    private ArrayList<Dok> oDOK;
    @Inject
    PodatnikDAO podatnikDAO;
    @Inject
    PodStawkiDAO podstawkiDAO;
    @Inject
    private Podatnik selected;
    @Inject
    private ZobowiazanieDAO zv;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    private BigDecimal przychody;
    private HashMap<String, BigDecimal> przychodyRyczalt;
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
        przychodyRyczalt = new HashMap<>();
    }
    
    @PostConstruct
    private void init(){
        String nazwapodatnika = null;
        try{
            nazwapodatnika = wpisView.findNazwaPodatnika();
            selected = podatnikDAO.find(nazwapodatnika);
        } catch (Exception e) { E.e(e); 
            nazwapodatnika = wpisView.findNazwaPodatnika();
            selected = podatnikDAO.find(nazwapodatnika);
        }
        przychodyRyczalt.put("17%", BigDecimal.ZERO);
        przychodyRyczalt.put("8.5%", BigDecimal.ZERO);
        przychodyRyczalt.put("5.5%", BigDecimal.ZERO);
        przychodyRyczalt.put("3%", BigDecimal.ZERO);
    }
  
    
  
    public void sprawozdaniePodatkowe(){
        Iterator it;
        List<Dok> lista = new ArrayList<>();
        lista.addAll(getoDOK());
        it = lista.iterator();
        while(it.hasNext()){
            Dok tmpX = (Dok) it.next();
            Kolmn kolmn = new Kolmn();
            List<KwotaKolumna1> listadok = tmpX.getListakwot1();
            for(KwotaKolumna1 tmp : listadok){
            if (tmp.getNazwakolumny().contains("%")) {
                switch (tmp.getNazwakolumny()){
                    case "17%":
                        przychodyRyczalt.put("17%", przychodyRyczalt.get("17%").add(BigDecimal.valueOf(tmp.getNetto()))).setScale(2, RoundingMode.HALF_EVEN);
                        przychody = przychody.add(BigDecimal.valueOf(tmp.getNetto())).setScale(2, RoundingMode.HALF_EVEN);
                        break;
                    case "8.5%":
                        przychodyRyczalt.put("8.5%", przychodyRyczalt.get("8.5%").add(BigDecimal.valueOf(tmp.getNetto()))).setScale(2, RoundingMode.HALF_EVEN);
                        przychody = przychody.add(BigDecimal.valueOf(tmp.getNetto())).setScale(2, RoundingMode.HALF_EVEN);
                        break;
                    case "5.5%":
                        przychodyRyczalt.put("5.5%", przychodyRyczalt.get("5.5%").add(BigDecimal.valueOf(tmp.getNetto()))).setScale(2, RoundingMode.HALF_EVEN);
                        przychody = przychody.add(BigDecimal.valueOf(tmp.getNetto())).setScale(2, RoundingMode.HALF_EVEN);
                        break;
                    case "3%":
                        przychodyRyczalt.put("3%", przychodyRyczalt.get("3%").add(BigDecimal.valueOf(tmp.getNetto()))).setScale(2, RoundingMode.HALF_EVEN);
                        przychody = przychody.add(BigDecimal.valueOf(tmp.getNetto())).setScale(2, RoundingMode.HALF_EVEN);
                        break;
                }

            } else {
                if (tmp.getNazwakolumny().equals(kolmn.getKolumnPrzychody().get(0)) || tmp.getNazwakolumny().equals(kolmn.getKolumnPrzychody().get(1))) {
                    przychody = przychody.add(BigDecimal.valueOf(tmp.getNetto())).setScale(2, RoundingMode.HALF_EVEN);
                } else if (tmp.getNazwakolumny().equals(kolmn.getKolumnST().get(0)) || tmp.getNazwakolumny().equals(kolmn.getKolumnST().get(1))) {
                    inwestycje = inwestycje.add(BigDecimal.valueOf(tmp.getNetto())).setScale(2, RoundingMode.HALF_EVEN);
                } else {
                    koszty = koszty.add(BigDecimal.valueOf(tmp.getNetto())).setScale(2, RoundingMode.HALF_EVEN);
                }
            }
            }
       }
        dochód = (przychody.subtract(koszty));
        dochód = dochód.setScale(0, RoundingMode.HALF_EVEN);
        String poszukiwany = wpisView.getPodatnikWpisu();
        Podatnik selected=podatnikDAO.find(poszukiwany);
        opodatkowanie = wpisView.getRodzajopodatkowania();
        String rodzajop = opodatkowanie;
        Double stawka;
        Podstawki skalaPodatkowaZaDanyRok;
        try {
            skalaPodatkowaZaDanyRok = podstawkiDAO.find(wpisView.getRokWpisu());
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Brak wprowadzonej skali opodatkowania dla wszystkich podatników na obecny rok. Przerywam wyliczanie PIT-u");
            return;
        }
        try{
        switch (rodzajop){
            case "zasady ogólne" :
                podatek = WyliczPodatekZasadyOgolne.wyliczopodateksymulacja(skalaPodatkowaZaDanyRok, dochód);
                break;
            case "zasady ogólne bez VAT" :
                podatek = WyliczPodatekZasadyOgolne.wyliczopodateksymulacja(skalaPodatkowaZaDanyRok, dochód);
                break;
            case "podatek liniowy" :
                stawka = skalaPodatkowaZaDanyRok.getStawkaliniowy();
                podatek = (dochód.multiply(BigDecimal.valueOf(stawka)));
                podatek = podatek.subtract(BigDecimal.valueOf(selected.getZusparametr().get(selected.getZusparametr().size()-1).getZus52odl()));
                podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                break;
            case "podatek liniowy bez VAT" :
                stawka = skalaPodatkowaZaDanyRok.getStawkaliniowy();
                podatek = (dochód.multiply(BigDecimal.valueOf(stawka)));
                podatek = podatek.subtract(BigDecimal.valueOf(selected.getZusparametr().get(selected.getZusparametr().size()-1).getZus52odl()));
                podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                break;
            case "ryczałt" :
                podatek = (przychodyRyczalt.get("17%").multiply(BigDecimal.valueOf(skalaPodatkowaZaDanyRok.getStawkaryczalt4())));
                podatek = podatek.add(przychodyRyczalt.get("8.5%").multiply(BigDecimal.valueOf(skalaPodatkowaZaDanyRok.getStawkaryczalt3())));
                podatek = podatek.add(przychodyRyczalt.get("5.5%").multiply(BigDecimal.valueOf(skalaPodatkowaZaDanyRok.getStawkaryczalt2())));
                podatek = podatek.add(przychodyRyczalt.get("3%").multiply(BigDecimal.valueOf(skalaPodatkowaZaDanyRok.getStawkaryczalt1())));
                podatek = podatek.subtract(BigDecimal.valueOf(selected.getZusparametr().get(selected.getZusparametr().size()-1).getZus52odl()));
                podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                break;
            case "ryczałt bez VAT" :
                podatek = (przychodyRyczalt.get("17%").multiply(BigDecimal.valueOf(skalaPodatkowaZaDanyRok.getStawkaryczalt4())));
                podatek = podatek.add(przychodyRyczalt.get("8.5%").multiply(BigDecimal.valueOf(skalaPodatkowaZaDanyRok.getStawkaryczalt3())));
                podatek = podatek.add(przychodyRyczalt.get("5.5%").multiply(BigDecimal.valueOf(skalaPodatkowaZaDanyRok.getStawkaryczalt2())));
                podatek = podatek.add(przychodyRyczalt.get("3%").multiply(BigDecimal.valueOf(skalaPodatkowaZaDanyRok.getStawkaryczalt1())));
                podatek = podatek.subtract(BigDecimal.valueOf(selected.getZusparametr().get(selected.getZusparametr().size()-1).getZus52odl()));
                podatek = podatek.setScale(0, RoundingMode.HALF_EVEN);
                break;
        }} catch (Exception e) { E.e(e); 
            Msg.msg("e", "Brak wprowadzonych stawek ZUS, nie wyliczam podatku!!!");
            return;
        }
        rokmiesiac = wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu();
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

    public HashMap<String, BigDecimal> getPrzychodyRyczalt() {
        return przychodyRyczalt;
    }

    public void setPrzychodyRyczalt(HashMap<String, BigDecimal> przychodyRyczalt) {
        this.przychodyRyczalt = przychodyRyczalt;
    }

   

   
    
}
