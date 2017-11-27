/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.Deklaracjavat27DAO;
import dao.DeklaracjavatUEDAO;
import dao.DeklaracjevatDAO;
import entity.DeklSuper;
import entity.Deklaracjavat27;
import entity.DeklaracjavatUE;
import entity.Deklaracjevat;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DeklaracjeListaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject 
    private DeklaracjevatDAO deklaracjevatDAO;
    @Inject
    private DeklaracjavatUEDAO deklaracjavatUEDAO;
    @Inject
    private Deklaracjavat27DAO deklaracjavat27DAO;
    private List<Deklaracjevat> oczekujaceVAT7;
    private List<DeklaracjavatUE> oczekujaceVATUE;
    private List<Deklaracjavat27> oczekujaceVAT27;
    
    @PostConstruct
    public void init() {
        oczekujaceVAT7 = deklaracjevatDAO.findDeklaracjewysylka(wpisView);
        usunzbedne(oczekujaceVAT7);
        oczekujaceVATUE = deklaracjavatUEDAO.findDeklaracjewysylka(wpisView);
        usunzbedne(oczekujaceVATUE);
        oczekujaceVAT27 = deklaracjavat27DAO.findDeklaracjewysylka(wpisView);
        usunzbedne(oczekujaceVAT27);
    }
    
    private void usunzbedne(List<? extends DeklSuper> lista) {
        for (Iterator<? extends DeklSuper> it = lista.iterator(); it.hasNext();) {
            DeklSuper p = it.next();
            if (p.getStatus() == null) {
                it.remove();
            } else if (!p.getStatus().startsWith("301") &&  !p.getStatus().startsWith("302")) {
                it.remove();
            }
        }
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Deklaracjevat> getOczekujaceVAT7() {
        return oczekujaceVAT7;
    }

    public void setOczekujaceVAT7(List<Deklaracjevat> oczekujaceVAT7) {
        this.oczekujaceVAT7 = oczekujaceVAT7;
    }

    public List<DeklaracjavatUE> getOczekujaceVATUE() {
        return oczekujaceVATUE;
    }

    public void setOczekujaceVATUE(List<DeklaracjavatUE> oczekujaceVATUE) {
        this.oczekujaceVATUE = oczekujaceVATUE;
    }

    public List<Deklaracjavat27> getOczekujaceVAT27() {
        return oczekujaceVAT27;
    }

    public void setOczekujaceVAT27(List<Deklaracjavat27> oczekujaceVAT27) {
        this.oczekujaceVAT27 = oczekujaceVAT27;
    }
    
    
    
}
