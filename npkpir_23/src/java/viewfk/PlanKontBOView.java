/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Kontocomparator;
import daoFK.KontoDAOfk;
import entityfk.Konto;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
@ViewScoped
@ManagedBean
public class PlanKontBOView implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private KontoDAOfk kontoDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private Map<Integer,List<Konto>> wykazkontGrupa;
    private List<Konto> wykazkont;

    public PlanKontBOView() {
         E.m(this);
        this.wykazkontGrupa = new HashMap<>();
    }

    @PostConstruct
    public void init() {
        if (wpisView instanceof WpisView) {
            this.wykazkontGrupa = new HashMap<>();
            List<Konto> wykazkont0 = kontoDAO.findWszystkieKontaPodatnikaBO(wpisView, "0%");
            List<Konto> wykazkont1 = kontoDAO.findWszystkieKontaPodatnikaBO(wpisView, "1%");
            List<Konto> wykazkont2 = kontoDAO.findWszystkieKontaPodatnikaBO(wpisView, "2%");
            List<Konto> wykazkont3 = kontoDAO.findWszystkieKontaPodatnikaBO(wpisView, "3%");
            List<Konto> wykazkont4 = kontoDAO.findWszystkieKontaPodatnikaBO(wpisView, "4%");
            List<Konto> wykazkont6 = kontoDAO.findWszystkieKontaPodatnikaBO(wpisView, "6%");
            List<Konto> wykazkont7 = kontoDAO.findWszystkieKontaPodatnikaBO(wpisView, "7%");
            List<Konto> wykazkont8 = kontoDAO.findWszystkieKontaPodatnikaBO(wpisView, "8%");
            wykazkont = kontoDAO.findWszystkieKontaBilansowePodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            wykazkontGrupa.put(0, wykazkont0);
            wykazkontGrupa.put(1, wykazkont1);
            wykazkontGrupa.put(2, wykazkont2);
            wykazkontGrupa.put(3, wykazkont3);
            wykazkontGrupa.put(4, wykazkont4);
            wykazkontGrupa.put(6, wykazkont6);
            wykazkontGrupa.put(7, wykazkont7);
            wykazkontGrupa.put(8, wykazkont8);
        }
    }
    
     public List<Konto> completeBO(String qr) {
        if (qr != null) {
            System.out.println("Wywołanie PlanKontBOView completeBO()");
            String query = null;
            List<Konto> listakontOstatniaAnalitykaklienta = null;
            List<Konto> results = new ArrayList<>();
                String nazwa = null;
                if (qr.trim().matches("^(.*\\s+.*)+$") && qr.length() > 6) {
                    String[] pola = qr.split(" ");
                    if (pola.length > 1) {
                        query = pola[0];
                        nazwa = pola[1];
                    } else {
                        query = qr;
                    }
                } else {
                    query = qr.split(" ")[0];
                }
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    listakontOstatniaAnalitykaklienta = wykazkontGrupa.get(Integer.parseInt(qr.substring(0,1)));
                    if (query.length() == 4 && !query.contains("-")) {
                        //wstawia - do ciagu konta
                        query = query.substring(0, 3) + "-" + query.substring(3, 4);
                    }
                    for (Konto p : listakontOstatniaAnalitykaklienta) {
                        if (p.getPelnynumer().startsWith(query)) {
                            results.add(p);
                        }
                    }
                    //rozwiazanie dla rozrachunkow szukanie po nazwie kontrahenta
                    if (nazwa != null && nazwa.length() > 2) {
                        for (Iterator<Konto> it = results.iterator(); it.hasNext();) {
                            Konto r = it.next();
                            if (!r.getNazwapelna().toLowerCase().contains(nazwa.toLowerCase())) {
                                it.remove();
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    listakontOstatniaAnalitykaklienta = wykazkont;
                    for (Konto p : listakontOstatniaAnalitykaklienta) {
                        if (p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                            results.add(p);
                        }
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            Collections.sort(results, new Kontocomparator());
            return results;
        }
        return null;
    }

    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    
  
}
