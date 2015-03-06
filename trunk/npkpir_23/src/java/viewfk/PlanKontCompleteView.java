/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.KontoDAOfk;
import entityfk.Konto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
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
    @Inject
    private KontoDAOfk kontoDAOfk;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
  @PostConstruct
  private void init() {
      listakontOstatniaAnalitykaklienta = kontoDAOfk.findKontaOstAlityka(wpisView);
  }
    
    public List<Konto> complete(String qr) {
        if (qr != null) {
            String query = qr.split(" ")[0];
            List<Konto> results = new ArrayList<>();
            if (listakontOstatniaAnalitykaklienta != null) {
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
                } catch (NumberFormatException e) {
                    for (Konto p : listakontOstatniaAnalitykaklienta) {
                        if (p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                            results.add(p);
                        }
                    }
                }
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
    
    
}
