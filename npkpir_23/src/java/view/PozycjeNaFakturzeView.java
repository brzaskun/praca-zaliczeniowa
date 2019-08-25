/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PozycjenafakturzeDAO;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Pozycjenafakturze;
import entity.PozycjenafakturzePK;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import msg.Msg;
/**
 *
 * @author Osito
 */
@ManagedBean
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
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private int lewy;
    private int gora;
    private String co;

    @PostConstruct
    private void init() { //E.m(this);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (request.isUserInRole("Guest")) {
            west = "sub/layoutFaktura/west.xhtml";
            westustawienia = "sub/layoutFaktura/westustawienia.xhtml";
        } else if (request.isUserInRole("Bookkeeper")) {
            west = "sub/layoutFakturaKsiegowa/west.xhtml";
            westustawienia = "sub/layoutFakturaKsiegowa/westustawienia.xhtml";
        } else if (request.isUserInRole("GuestFK")) {
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

    @Inject
    private PozycjenafakturzeDAO pozycjeDAO;

    public void zachowajpozycje() {
        PozycjenafakturzePK klucz = new PozycjenafakturzePK();
        klucz.setNazwa(co);
        klucz.setPodatnik(wpisView.getPodatnikWpisu());
        Pozycjenafakturze pozycje = new Pozycjenafakturze(klucz, true, gora, lewy);
        try {
            pozycjeDAO.dodaj(pozycje);
        } catch (Exception e) { E.e(e); 
            pozycjeDAO.edit(pozycje);
        }
        Msg.msg("i", "Zachowano "+pozycje.toString(), "form:messages");
    }

    public void odchowaj() {
        try {
            List<Pozycjenafakturze> lista = pozycjeDAO.findFakturyPodatnik(wpisView.getPodatnikWpisu());
            if (!lista.isEmpty()) {
                lewyTablica = "";
                goraTablica = "";
                coTablica = "";
                for (Pozycjenafakturze p : lista) {
                    lewyTablica = lewyTablica + p.getLewy() + ",";
                    goraTablica = goraTablica + p.getGora() + ",";
                    coTablica = coTablica + p.getPozycjenafakturzePK().getNazwa() + ",";
                }
            }
        } catch (Exception e) { E.e(e); 
            Msg.msg("i", "Elementy faktury nie są ustawione");
        }
    }

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
