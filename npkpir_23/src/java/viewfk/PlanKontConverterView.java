/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import dao.KontoDAOfk;
import entityfk.Konto;
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
public class PlanKontConverterView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Konto> wykazkont;
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject
    private WpisView wpisView;

    public PlanKontConverterView() {
         ////E.m(this);
    }

    
    @PostConstruct
    private void init() { //E.m(this);
        wykazkont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public List<Konto> getWykazkont() {
        return wykazkont;
    }
    
    public void setWykazkont(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
    }
    
//</editor-fold>
    
    
    
}
