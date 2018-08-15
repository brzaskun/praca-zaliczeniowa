/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import daoFK.CechazapisuDAOfk;
import entity.Dok;
import entity.KwotaKolumna1;
import entityfk.Cechazapisu;
import java.io.Serializable;
import java.util.Iterator;
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
    @Inject
    private DokDAO dokDAO;
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
            for (Cechazapisu p  : pobranecechypodatnik) {
                if (!p.getDokLista().isEmpty()) {
                    for (Iterator<Dok> it=p.getDokLista().iterator(); it.hasNext();) {
                        Dok dok = it.next();
                        if (!dok.getPkpirR().equals(wpisView.getRokWpisuSt())) {
                            it.remove();
                        } else if (!dok.getPkpirM().equals(mc)) {
                            it.remove();
                        } else {
                            for (KwotaKolumna1 x  : dok.getListakwot1()) {
                                 switch (x.getNazwakolumny()) {
                                    case "przych. sprz":
                                    case "pozost. przych.":
                                        p.setSumaprzychodow(x.getNetto());
                                        break;
                                    default:
                                        p.setSumakosztow(x.getNetto());
                                        break;
                                 }
                            }

                        }
                    }
                }

            }
        }
        System.out.println("");
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
