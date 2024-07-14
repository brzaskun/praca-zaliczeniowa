/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.KontoDAOfk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
@ViewScoped
@Named @Interceptors(ConstructorInterceptor.class)
public class PlanKontJSView implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String opiskonta;
    private List<String> opisKontaLista;
    private String pelnynumerkonta;
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject
    private WpisView wpisView;

    public PlanKontJSView() {
         ////E.m(this);
        opisKontaLista = Collections.synchronizedList(new ArrayList<>());
    }

    private void init() { //E.m(this);
//            if (wpisView instanceof WpisView) {
//            List<Konto> wykazkont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
//            opiskonta = "";
//            pelnynumerkonta = "";
//            if (wykazkont != null) {
//                for (Konto t : wykazkont) {
//                    if (!t.getNrkonta().equals("0")) {
//                        opisKontaLista.add(t.getNazwaskrocona());
//                        opiskonta = opiskonta + t.getNazwaskrocona() + ",";
//                        pelnynumerkonta = pelnynumerkonta + t.getPelnynumer() + ",";
//                    }
//                }
//            } else {
//                Msg.msg("e", "Nie zdefiniowanu planu kont dla firmy");
//            }
//        }
    }
    
    public List<String> complete(String query) {
        if (!opisKontaLista.isEmpty()) {
            List<String> wynik = Collections.synchronizedList(new ArrayList<>());
            for (String p : opisKontaLista) {
                String a = p.toLowerCase();
                String b = query.toLowerCase();
                if (a.contains(b)) {
                    wynik.add(p);
                }
            }
            return wynik;
        }
        return null;
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
