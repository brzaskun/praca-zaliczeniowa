/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SesjaDAO;
import entity.Sesja;
import error.E;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SesjaView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private Sesja sesja;
    @Inject
    private SesjaDAO sesjaDAO;
    private List<Sesja> wykazsesji;
    private List<Sesja> wykazsesjiZalogowani;

    public SesjaView() {

    }

    @PostConstruct
    private void init() { //E.m(this);
       pobierzsesjeZalogowani();
    }

    public void pobierzsesjeZalogowani() {
        try {
            wykazsesjiZalogowani = sesjaDAO.findSesjaZalogowani();
            for (Iterator<Sesja> it = wykazsesjiZalogowani.iterator(); it.hasNext(); ) {
                Sesja s = it.next();
                if (s.getWylogowanie() == null) {
                    java.time.LocalDate zalogowanie = asLocalDate(s.getZalogowanie());
                    java.time.LocalDate limes = asLocalDate(new Date()).minusDays(1);
                    if (limes.compareTo(zalogowanie) > 0) {
                        it.remove();
                    }
                }
            }
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
    public static void main(String[] args){
        java.time.LocalDate ld = asLocalDate(new Date()).minusDays(1);
        System.out.println(ld);
        java.time.LocalDate ld2 = asLocalDate(new Date());
        System.out.println(ld2);
    }
    
  public static java.time.LocalDate asLocalDate(Date date) {
    return Instant.ofEpochMilli(date.getTime()).atZone(ZoneId.systemDefault()).toLocalDate();
  }
    
    public void pobierzsesje() {
        try {
            wykazsesji = sesjaDAO.findAll();
            for (Iterator<Sesja> it = wykazsesji.iterator(); it.hasNext(); ) {
                Sesja s = it.next();
                if (s.getWylogowanie() == null) {
                    sesjaDAO.destroy(s);
                    it.remove();
                }
            }
        } catch (Exception e) { 
            E.e(e); 
        }
    }

    public void dodajwydruk() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        try {
            Sesja sesja = sesjaDAO.find(session.getId());
            int ilosc = sesja.getIloscwydrukow();
            ilosc = ilosc + 1;
            sesja.setIloscwydrukow(ilosc);
            sesjaDAO.edit(sesja);
        } catch (Exception e) { E.e(e); 
        }
    }

    public void dodajmail() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        try {
            Sesja sesja = sesjaDAO.find(session.getId());
            int ilosc = sesja.getIloscmaili();
            ilosc = ilosc + 1;
            sesja.setIloscmaili(ilosc);
            sesjaDAO.edit(sesja);
        } catch (Exception e) { E.e(e); 
        }
    }

    public void dodajdokument() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        try {
            Sesja sesja = sesjaDAO.find(session.getId());
            int ilosc = sesja.getIloscdokumentow();
            ilosc = ilosc + 1;
            sesja.setIloscdokumentow(ilosc);
            sesjaDAO.edit(sesja);
        } catch (Exception e) { E.e(e); 
        }

    }

    public Sesja getSesja() {
        return sesja;
    }

    public void setSesja(Sesja sesja) {
        this.sesja = sesja;
    }

    public SesjaDAO getSesjaDAO() {
        return sesjaDAO;
    }

    public void setSesjaDAO(SesjaDAO sesjaDAO) {
        this.sesjaDAO = sesjaDAO;
    }

    public List<Sesja> getWykazsesji() {
        return wykazsesji;
    }

    public void setWykazsesji(List<Sesja> wykazsesji) {
        this.wykazsesji = wykazsesji;
    }

    public List<Sesja> getWykazsesjiZalogowani() {
        return wykazsesjiZalogowani;
    }

    public void setWykazsesjiZalogowani(List<Sesja> wykazsesjiZalogowani) {
        this.wykazsesjiZalogowani = wykazsesjiZalogowani;
    }

    

}
