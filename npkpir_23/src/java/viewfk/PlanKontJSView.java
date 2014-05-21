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
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@ViewScoped
@Named("planKontJSView")
public class PlanKontJSView implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private static List<Konto> wykazkontS;
    private static String opiskonta;
    private static String pelnynumerkonta;
    @Inject
    private KontoDAOfk kontoDAO;

    public PlanKontJSView() {
    }

    @PostConstruct
    private void init() {
        wykazkontS = kontoDAO.findAll();
        opiskonta = "";
        pelnynumerkonta = "";
        for (Konto t : wykazkontS) {
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


}
