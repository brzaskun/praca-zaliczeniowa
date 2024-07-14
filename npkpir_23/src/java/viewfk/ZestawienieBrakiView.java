/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.DokDAOfk;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import view.WpisView;
import interceptor.ConstructorInterceptor;
/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class ZestawienieBrakiView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private DokDAOfk dokDAOfk;
    
    private boolean jestASR;
    private boolean jestAMO;
    
    @PostConstruct
    private void init() { //E.m(this);
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
