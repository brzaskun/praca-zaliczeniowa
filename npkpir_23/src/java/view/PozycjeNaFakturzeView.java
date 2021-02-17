/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PozycjenafakturzeDAO;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Pozycjenafakturze;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import msg.Msg;
/**
 *
 * @author Osito
 */
@Named
@RequestScoped
public class PozycjeNaFakturzeView implements Serializable {

    private String lewyTablica;
    private String goraTablica;
    private String coTablica;
    private static final List<Pozycjenafakturzebazadanych> zestaw;
    private String west;
    private String westustawienia;

    static {
        zestaw = Collections.synchronizedList(new ArrayList<>());
        zestaw.add(new Pozycjenafakturzebazadanych(1, "serek topiony", "20.45", "kg", 12, 8.5, 125, 23, 12, 147));
        zestaw.add(new Pozycjenafakturzebazadanych(1, "koperek topiony", "20.45", "kg", 12, 8.5, 125, 23, 12, 147));
        zestaw.add(new Pozycjenafakturzebazadanych(1, "marchewka topiona", "20.45", "kg", 12, 8.5, 125, 23, 12, 147));
    }
    @Inject
    private WpisView wpisView;
    private int lewy;
    private int gora;
    private String co;
    @Inject
    private PozycjenafakturzeDAO pozycjeDAO;

    @PostConstruct
    private void init() { //E.m(this);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request.isUserInRole("Guest")) {
            west = "sub/layoutFaktura/west.xhtml";
            westustawienia = "sub/layoutFaktura/westustawienia.xhtml";
        } else if (request.isUserInRole("Bookkeeper")) {
            west = "sub/layoutFakturaKsiegowa/west.xhtml";
            westustawienia = "sub/layoutFakturaKsiegowa/westustawienia.xhtml";
        } else if (request.isUserInRole("GuestFK")||request.isUserInRole("GuestFKBook")) {
            west = "../wspolny/sub/layoutFakturaGuestFK/west.xhtml";
            westustawienia = "../wspolny/sub/layoutFakturaGuestFK/westustawienia.xhtml";
        } else if (request.isUserInRole("GuestFaktura")) {
            west = "../wspolny/sub/layoutFakturaGuestFaktura/west.xhtml";
            westustawienia = "../wspolny/sub/layoutFakturaGuestFaktura/westustawienia.xhtml";
        } else if (request.isUserInRole("Multiuser") && wpisView.isKsiegirachunkowe()) {
            west = "../wspolny/sub/layoutFakturaMultiuser/west.xhtml";
            westustawienia = "../wspolny/sub/layoutFakturaMultiuser/westustawienia.xhtml";
        } else if (request.isUserInRole("Multiuser") && !wpisView.isKsiegirachunkowe()) {
            west = "../wspolny/sub/layoutFakturaMultiuser/west.xhtml";
            westustawienia = "sub/layoutFaktura/westustawienia.xhtml";
        }
    }

   

    public void zachowajpozycje() {
        Pozycjenafakturze pozycje = pozycjeDAO.findPozycjePodatnikCo(wpisView.getPodatnikObiekt(),co);
        if (pozycje == null) {
                pozycje = new Pozycjenafakturze(co, wpisView.getPodatnikObiekt(), gora, lewy);
        } else {
            pozycje.setGora(gora);
            pozycje.setLewy(lewy);
        }
        try {
            if (pozycje.getId()==null) {
                pozycjeDAO.create(pozycje);
            } else {
                pozycjeDAO.edit(pozycje);
            }
            Msg.msg("i", "Zachowano "+pozycje.toString(), "form:messages");
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e", "Wystapił błąd przy zachowywaniu pozycji", "form:messages");
        }
        
    }

    public void odchowaj() {
        try {
            List<Pozycjenafakturze> lista = pozycjeDAO.findFakturyPodatnik(wpisView.getPodatnikObiekt());
            if (!lista.isEmpty()) {
                lewyTablica = "";
                goraTablica = "";
                coTablica = "";
                for (Pozycjenafakturze p : lista) {
                    lewyTablica = lewyTablica + p.getLewy() + ",";
                    goraTablica = goraTablica + p.getGora() + ",";
                    coTablica = coTablica + p.getNazwa() + ",";
                }
            }
        } catch (Exception e) { E.e(e); 
            Msg.msg("i", "Elementy faktury nie są ustawione");
        }
    }

//    @Inject
//    private PodatnikDAO podatnikDAO;
//    
//    public void dodajnowakolumne() {
//        System.out.println("startt");
//        List<Pozycjenafakturze> lista = pozycjeDAO.findAll();
//        int i =1;
//        for (Pozycjenafakturze p: lista) {
//            Podatnik podid = podatnikDAO.findByNazwaPelna(p.getPozycjenafakturzePK.getPodatnik());
//            System.out.println("p: "+p.getPozycjenafakturzePK().getPodatnik());
//            p.setPodid(podid);
//            p.setId(i++);
//            pozycjeDAO.edit(p);
//            //System.out.println("podid: "+podid.getPrintnazwa());
//        }
//    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public String getWest() {
        return west;
    }

    public void setWest(String west) {
        this.west = west;
    }

    public int getLewy() {
        return lewy;
    }

    public void setLewy(int lewy) {
        this.lewy = lewy;
    }

    public int getGora() {
        return gora;
    }

    public void setGora(int gora) {
        this.gora = gora;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getLewyTablica() {
        return lewyTablica;
    }

    public void setLewyTablica(String lewyTablica) {
        this.lewyTablica = lewyTablica;
    }

    public String getGoraTablica() {
        return goraTablica;
    }

    public void setGoraTablica(String goraTablica) {
        this.goraTablica = goraTablica;
    }

    public String getCoTablica() {
        return coTablica;
    }

    public void setCoTablica(String coTablica) {
        this.coTablica = coTablica;
    }

    public List<Pozycjenafakturzebazadanych> getZestaw() {
        return zestaw;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public String getWestustawienia() {
        return westustawienia;
    }

    public void setWestustawienia(String westustawienia) {
        this.westustawienia = westustawienia;
    }
    
    
   //</editor-fold>

}
