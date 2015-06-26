/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Kontocomparator;
import daoFK.KontoDAOfk;
import entityfk.Konto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
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
public class PlanKontCompleteView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Konto> listakontOstatniaAnalitykaklienta;
    private List<Konto> listakont;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
  @PostConstruct
  public void init() {
      listakontOstatniaAnalitykaklienta = kontoDAOfk.findKontaOstAlityka(wpisView);
      listakont = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
      System.out.println("Wywołanie PlanKontCompleteView init()");
  }
    
    public List<Konto> complete(String qr) {
        if (qr != null) {
            System.out.println("Wywołanie PlanKontCompleteView complete()");
            String query = null;
            List<Konto> results = new ArrayList<>();
            if (listakontOstatniaAnalitykaklienta != null) {
                String nazwa = null;
                if (qr.contains(" ") && qr.length() > 6) {
                    String[] pola = qr.split(" ");
                    if (pola.length > 1) {
                        query = pola[0];
                        nazwa = pola[1];
                    }
                } else {
                    query = qr.split(" ")[0];
                }
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    for (Konto p : listakontOstatniaAnalitykaklienta) {
                        if (query.length() == 4 && !query.contains("-")) {
                            //wstawia - do ciagu konta
                            query = query.substring(0, 3) + "-" + query.substring(3, 4);
                        }
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
                    for (Konto p : listakontOstatniaAnalitykaklienta) {
                        if (p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                            results.add(p);
                        }
                    }
                }
            }
            Collections.sort(results, new Kontocomparator());
            if (results.isEmpty()) {
                Konto p = new Konto();
                p.setNazwapelna("dodaj konto");
                p.setPelnynumer(query);
                results.add(p);
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

    public List<Konto> getListakontOstatniaAnalitykaklienta() {
        return listakontOstatniaAnalitykaklienta;
    }

    public void setListakontOstatniaAnalitykaklienta(List<Konto> listakontOstatniaAnalitykaklienta) {
        this.listakontOstatniaAnalitykaklienta = listakontOstatniaAnalitykaklienta;
    }

    public List<Konto> getListakont() {
        return listakont;
    }

    public void setListakont(List<Konto> listakont) {
        this.listakont = listakont;
    }
    
    
    
}
