/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.KontoDAOfk;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ViewScoped
@ManagedBean
public class PlanKontSrTrw implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private KontoDAOfk kontoDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private List<Konto> listakontSrodkiTrwale;
    private List<Konto> listakontSrodkiTrwaleUmorzenia;

    public PlanKontSrTrw() {
         E.m(this);
    }

    @PostConstruct
    public void init() {E.m(this);
        if (wpisView instanceof WpisView) {
            listakontSrodkiTrwale = kontoDAO.findWszystkieKontaPodatnikaBO(wpisView, "0%");
            if (listakontSrodkiTrwale != null) {
                usunzbednekonta(listakontSrodkiTrwale);
            }
            listakontSrodkiTrwaleUmorzenia  = kontoDAO.findWszystkieKontaPodatnikaBO(wpisView, "07%");
        }
    }
    
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Konto> getListakontSrodkiTrwale() {
        return listakontSrodkiTrwale;
    }

    public void setListakontSrodkiTrwale(List<Konto> listakontSrodkiTrwale) {
        this.listakontSrodkiTrwale = listakontSrodkiTrwale;
    }

    public List<Konto> getListakontSrodkiTrwaleUmorzenia() {
        return listakontSrodkiTrwaleUmorzenia;
    }

    public void setListakontSrodkiTrwaleUmorzenia(List<Konto> listakontSrodkiTrwaleUmorzenia) {
        this.listakontSrodkiTrwaleUmorzenia = listakontSrodkiTrwaleUmorzenia;
    }

    private void usunzbednekonta(List<Konto> listakontSrodkiTrwale) {
        for (Iterator<Konto> it = listakontSrodkiTrwale.iterator(); it.hasNext();) {
            if (it.next().getPelnynumer().startsWith("07")) {
                it.remove();
            }
        }
    }

    
  
}
