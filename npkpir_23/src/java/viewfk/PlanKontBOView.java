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
import javax.inject.Named;
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
    private List<Konto> wykazkont0;
    private List<Konto> wykazkont1;
    private List<Konto> wykazkont2;
    private List<Konto> wykazkont3;
    private List<Konto> wykazkont6;
    private List<Konto> wykazkont8;
    private Map<Integer,List<Konto>> wykazkontGrupa;
    private List<Konto> wykazkont;

    public PlanKontBOView() {
        this.wykazkontGrupa = new HashMap<>();
    }

    @PostConstruct
    private void init() {
        if (wpisView instanceof WpisView) {
            wykazkont0 = kontoDAO.findWszystkieKontaPodatnikaBO(wpisView, "0%");
            wykazkont1 = kontoDAO.findWszystkieKontaPodatnikaBO(wpisView, "1%");
            wykazkont2 = kontoDAO.findWszystkieKontaPodatnikaBO(wpisView, "2%");
            wykazkont3 = kontoDAO.findWszystkieKontaPodatnikaBO(wpisView, "3%");
            wykazkont6 = kontoDAO.findWszystkieKontaPodatnikaBO(wpisView, "6%");
            wykazkont8 = kontoDAO.findWszystkieKontaPodatnikaBO(wpisView, "8%");
            wykazkont = kontoDAO.findWszystkieKontaBilansowePodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            wykazkontGrupa.put(0, wykazkont0);
            wykazkontGrupa.put(1, wykazkont1);
            wykazkontGrupa.put(2, wykazkont2);
            wykazkontGrupa.put(3, wykazkont3);
            wykazkontGrupa.put(6, wykazkont6);
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

    public List<Konto> complete0(String qr) {
        if (qr != null) {
            String query = qr.split(" ")[0];
            List<Konto> results = new ArrayList<>();
            if (wykazkont0 != null) {
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    for (Konto p : wykazkont0) {
                        if (query.length() == 4 && !query.contains("-")) {
                            //wstawia - do ciagu konta
                            query = query.substring(0, 3) + "-" + query.substring(3, 4);
                        }
                        if (p.getPelnynumer().startsWith(query)) {
                            results.add(p);
                        }
                    }
                } catch (NumberFormatException e) {
                    for (Konto p : wykazkont0) {
                        if (p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                            results.add(p);
                        }
                    }
                }
             Collections.sort(results, new Kontocomparator());
            }
            return results;
        }
        return null;
    }
    
    public List<Konto> complete1(String qr) {
        if (qr != null) {
            String query = qr.split(" ")[0];
            List<Konto> results = new ArrayList<>();
            if (wykazkont1 != null) {
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    for (Konto p : wykazkont1) {
                        if (query.length() == 4 && !query.contains("-")) {
                            //wstawia - do ciagu konta
                            query = query.substring(0, 3) + "-" + query.substring(3, 4);
                        }
                        if (p.getPelnynumer().startsWith(query)) {
                            results.add(p);
                        }
                    }
                } catch (NumberFormatException e) {
                    for (Konto p : wykazkont1) {
                        if (p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                            results.add(p);
                        }
                    }
                }
                Collections.sort(results, new Kontocomparator());
            }
            return results;
        }
        return null;
    }
    
       
    public List<Konto> complete2(String qr) {
        if (qr != null) {
            System.out.println("Wywołanie PlanKontBOView complete2()");
            String query = null;
            List<Konto> results = new ArrayList<>();
            if (wykazkont2 != null) {
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
                    if (query.length() == 4 && !query.contains("-")) {
                        //wstawia - do ciagu konta
                        query = query.substring(0, 3) + "-" + query.substring(3, 4);
                    }
                    for (Konto p : wykazkont2) {
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
                    for (Konto p : wykazkont2) {
                        if (p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                            results.add(p);
                        }
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            }
            Collections.sort(results, new Kontocomparator());
            if (results.isEmpty()) {
                Konto p = new Konto();
                p.setNazwapelna("dodaj konto");
                p.setPelnynumer(query);
                results.add(p);
                p = new Konto();
                p.setNazwapelna("dodaj kontrahenta");
                p.setPelnynumer(query);
                results.add(p);

            }
            return results;
        }
        return null;
    }
    
    public List<Konto> complete3(String qr) {
        if (qr != null) {
            String query = qr.split(" ")[0];
            List<Konto> results = new ArrayList<>();
            if (wykazkont3 != null) {
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    for (Konto p : wykazkont3) {
                        if (query.length() == 4 && !query.contains("-")) {
                            //wstawia - do ciagu konta
                            query = query.substring(0, 3) + "-" + query.substring(3, 4);
                        }
                        if (p.getPelnynumer().startsWith(query)) {
                            results.add(p);
                        }
                    }
                } catch (NumberFormatException e) {
                    for (Konto p : wykazkont3) {
                        if (p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                            results.add(p);
                        }
                    }
                }
                Collections.sort(results, new Kontocomparator());
            }
            return results;
        }
        return null;
    }
    
    public List<Konto> complete6(String qr) {
        if (qr != null) {
            String query = qr.split(" ")[0];
            List<Konto> results = new ArrayList<>();
            if (wykazkont6 != null) {
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    for (Konto p : wykazkont6) {
                        if (query.length() == 4 && !query.contains("-")) {
                            //wstawia - do ciagu konta
                            query = query.substring(0, 3) + "-" + query.substring(3, 4);
                        }
                        if (p.getPelnynumer().startsWith(query)) {
                            results.add(p);
                        }
                    }
                } catch (NumberFormatException e) {
                    for (Konto p : wykazkont6) {
                        if (p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                            results.add(p);
                        }
                    }
                }
                Collections.sort(results, new Kontocomparator());
            }
            return results;
        }
        return null;
    }
    
    public List<Konto> complete8(String qr) {
        if (qr != null) {
            String query = qr.split(" ")[0];
            List<Konto> results = new ArrayList<>();
            if (wykazkont8 != null) {
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    for (Konto p : wykazkont8) {
                        if (query.length() == 4 && !query.contains("-")) {
                            //wstawia - do ciagu konta
                            query = query.substring(0, 3) + "-" + query.substring(3, 4);
                        }
                        if (p.getPelnynumer().startsWith(query)) {
                            results.add(p);
                        }
                    }
                } catch (NumberFormatException e) {
                    for (Konto p : wykazkont8) {
                        if (p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                            results.add(p);
                        }
                    }
                }
                Collections.sort(results, new Kontocomparator());
            }
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
