/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.DokDAOfk;
import entityfk.Dokfk;
import error.E;
import java.io.Serializable;
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
@ManagedBean
@ViewScoped
public class ZestawienieBrakiView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAOfk dokDAOfk;
    
    private boolean jestASR;
    private boolean jestAMO;
    
    @PostConstruct
    private void init() {E.m(this);
        List<Dokfk> dokmc = dokDAOfk.findDokfkPodatnikRokMc(wpisView);
        for (Dokfk p : dokmc) {
            if (p.getSeriadokfk().equals("ARS")) {
                jestASR = true;
            }
            if (p.getSeriadokfk().equals("AMO")) {
                jestAMO = true;
            }
        }
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public DokDAOfk getDokDAOfk() {
        return dokDAOfk;
    }

    public void setDokDAOfk(DokDAOfk dokDAOfk) {
        this.dokDAOfk = dokDAOfk;
    }

    public boolean isJestASR() {
        return jestASR;
    }

    public void setJestASR(boolean jestASR) {
        this.jestASR = jestASR;
    }

    public boolean isJestAMO() {
        return jestAMO;
    }

    public void setJestAMO(boolean jestAMO) {
        this.jestAMO = jestAMO;
    }
    
    
    
}
