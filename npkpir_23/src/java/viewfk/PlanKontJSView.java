/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.KontoDAOfk;
import entityfk.Konto;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ViewScoped
@Named("planKontJSView")
public class PlanKontJSView implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static String opiskonta;
    private static String pelnynumerkonta;
    @Inject
    private KontoDAOfk kontoDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public PlanKontJSView() {
    }

    @PostConstruct
    private void init() {
        List<Konto> wykazkont = kontoDAO.findKontoPodatnik(wpisView.getPodatnikWpisu());
        opiskonta = "";
        pelnynumerkonta = "";
        for (Konto t : wykazkont) {
            opiskonta = opiskonta + t.getNazwaskrocona() + ",";
            pelnynumerkonta = pelnynumerkonta + t.getPelnynumer() + ",";
        }
    }

    public String getOpiskonta() {
        return opiskonta;
    }

    public String getPelnynumerkonta() {
        return pelnynumerkonta;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    


}
