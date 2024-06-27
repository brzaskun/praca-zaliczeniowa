/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PlanKontTablicaBean;
import dao.KontoDAOfk;
import entityfk.Konto;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import view.WpisView;
import wydajnosc.ConstructorInterceptor;
/**
 *
 * @author Osito
 */
@Interceptors(ConstructorInterceptor.class)
public class PlanKontTablicaView {
    private static final long serialVersionUID = 1L;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private WpisView wpisView;
    
    private Map<Integer, List<Konto>> leveleKonta;
    private List<Konto> tablica;

    public PlanKontTablicaView() {
         ////E.m(this);
        leveleKonta = new ConcurrentHashMap<>();
    }
    
    
    
    @PostConstruct
    private void init() { //E.m(this);
        int maxlevel = kontoDAOfk.findMaxLevelPodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        for (int i = 0; i <= maxlevel; i++) {
            List<Konto> pobranekontazlevelu = kontoDAOfk.findKontazLevelu(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), i);
            if (pobranekontazlevelu != null) {
                leveleKonta.put(i, pobranekontazlevelu);
            }
        }
        tablica = PlanKontTablicaBean.generujTablicePlanKont(leveleKonta, wpisView);
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Map<Integer, List<Konto>> getLeveleKonta() {
        return leveleKonta;
    }

    public void setLeveleKonta(Map<Integer, List<Konto>> leveleKonta) {
        this.leveleKonta = leveleKonta;
    }

    public List<Konto> getTablica() {
        return tablica;
    }

    public void setTablica(List<Konto> tablica) {
        this.tablica = tablica;
    }
    
   
}
