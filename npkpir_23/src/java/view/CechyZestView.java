/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import daoFK.CechazapisuDAOfk;
import entity.Dok;
import entityfk.Cechazapisu;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import pdffk.PdfCechyZapisow;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class CechyZestView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private String mc;
    private List<Cechazapisu> pobranecechypodatnik;
    @Inject
    private CechazapisuDAOfk cechazapisuDAOfk;
    @Inject DokDAO dokDAO;
    private boolean rozwinwszystkie;

    public CechyZestView() {
        
    }

    @PostConstruct
    public void pobierz(){
        if (mc==null) {
            mc = wpisView.getMiesiacWpisu();
        }
        if (mc!=null) {
            pobranecechypodatnik = cechazapisuDAOfk.findPodatnikOnlyStatystyczne(wpisView.getPodatnikObiekt());
            List<Dok> doku = dokDAO.zwrocBiezacegoKlientaRokMC(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), mc);
            beansDok.CechaBean.sumujcechy(pobranecechypodatnik, doku, wpisView.getRokWpisuSt(), mc);
        }
        error.E.s("");
    }
    
    public void drukuj(int nr) {
        if (nr==1) {
            PdfCechyZapisow.drukujcechy(wpisView, pobranecechypodatnik);
        } else {
            PdfCechyZapisow.drukujcechyszczegoly(wpisView, pobranecechypodatnik);
        }
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public List<Cechazapisu> getPobranecechypodatnik() {
        return pobranecechypodatnik;
    }

    public void setPobranecechypodatnik(List<Cechazapisu> pobranecechypodatnik) {
        this.pobranecechypodatnik = pobranecechypodatnik;
    }

    public boolean isRozwinwszystkie() {
        return rozwinwszystkie;
    }

    public void setRozwinwszystkie(boolean rozwinwszystkie) {
        this.rozwinwszystkie = rozwinwszystkie;
    }
    
    
    
    
}
