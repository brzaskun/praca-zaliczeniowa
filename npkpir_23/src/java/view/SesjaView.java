/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SesjaDAO;
import entity.Sesja;
import error.E;
import java.io.Serializable;
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

    public SesjaView() {

    }

    @PostConstruct
    private void init() {
       
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

    

}
