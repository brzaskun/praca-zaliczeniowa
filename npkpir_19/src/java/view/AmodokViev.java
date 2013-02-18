/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AmoDokDAO;
import entity.Amodok;
import entity.Amodok;
import entity.Amodok;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean(name="AmodokView")
@RequestScoped
public class AmodokViev {
     @Inject
    private AmoDokDAO amoDokDAO;
     private Amodok selected;
     
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
     
     private HashMap<String,Amodok> dokHashTable;
    //tablica kluczy do obiektów
    private List<String> kluczDOKjsf;
    //tablica obiektów
    private List<Amodok> obiektDOKjsf;
    //tablica obiektw danego klienta
    private List<Amodok> obiektDOKjsfSel;
    //tablica obiektów danego klienta z określonego roku i miesiąca
    private List<Amodok> obiektDOKmrjsfSel;
     //tablica obiektów danego klienta z określonego roku i miesiecy
    private List<Amodok> obiektDOKmrjsfSelX;

    public AmodokViev() {
         selected = new Amodok();
         dokHashTable = new HashMap<String, Amodok>();
        kluczDOKjsf = new ArrayList<String>();
        obiektDOKjsf = new ArrayList<Amodok>();
        obiektDOKjsfSel = new ArrayList<Amodok>();
        obiektDOKmrjsfSel = new ArrayList<Amodok>();
        obiektDOKmrjsfSelX = new ArrayList<Amodok>();
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    
    public AmoDokDAO getAmoDokDAO() {
        return amoDokDAO;
    }

    public void setAmoDokDAO(AmoDokDAO amoDokDAO) {
        this.amoDokDAO = amoDokDAO;
    }

    public Amodok getSelected() {
        return selected;
    }

    public void setSelected(Amodok selected) {
        this.selected = selected;
    }

    public HashMap<String, Amodok> getDokHashTable() {
        return dokHashTable;
    }

    public void setDokHashTable(HashMap<String, Amodok> dokHashTable) {
        this.dokHashTable = dokHashTable;
    }

    public List<String> getKluczDOKjsf() {
        return kluczDOKjsf;
    }

    public void setKluczDOKjsf(List<String> kluczDOKjsf) {
        this.kluczDOKjsf = kluczDOKjsf;
    }

    public List<Amodok> getObiektDOKjsf() {
        return obiektDOKjsf;
    }

    public void setObiektDOKjsf(List<Amodok> obiektDOKjsf) {
        this.obiektDOKjsf = obiektDOKjsf;
    }

    public List<Amodok> getObiektDOKjsfSel() {
        return obiektDOKjsfSel;
    }

    public void setObiektDOKjsfSel(List<Amodok> obiektDOKjsfSel) {
        this.obiektDOKjsfSel = obiektDOKjsfSel;
    }

    public List<Amodok> getObiektDOKmrjsfSel() {
        return obiektDOKmrjsfSel;
    }

    public void setObiektDOKmrjsfSel(List<Amodok> obiektDOKmrjsfSel) {
        this.obiektDOKmrjsfSel = obiektDOKmrjsfSel;
    }

    public List<Amodok> getObiektDOKmrjsfSelX() {
        return obiektDOKmrjsfSelX;
    }

    public void setObiektDOKmrjsfSelX(List<Amodok> obiektDOKmrjsfSelX) {
        this.obiektDOKmrjsfSelX = obiektDOKmrjsfSelX;
    }
    
      @PostConstruct
    public void init() {
        if (wpisView.getPodatnikWpisu() != null) {
            Collection c = null;
            try {
                c = amoDokDAO.getDownloaded();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            if (c != null) {
                Iterator it;
                it = c.iterator();
                while (it.hasNext()) {
                    Amodok tmp = (Amodok) it.next();
                    kluczDOKjsf.add(tmp.getId().toString());
                    obiektDOKjsf.add(tmp);
                    if (tmp.getPodatnik().equals(wpisView.getPodatnikWpisu())) {
                        obiektDOKjsfSel.add(tmp);
                    }
                    dokHashTable.put(tmp.getId().toString(), tmp);
                }
                int ie = 1;
                Iterator itx;
                itx = obiektDOKjsfSel.iterator();
                while (itx.hasNext()) {
                    Amodok tmpx = (Amodok) itx.next();
                    String m = wpisView.getMiesiacWpisu();
                    Integer r = wpisView.getRokWpisu();
                    //if (tmpx.getPkpirM().equals(m) && tmpx.getPkpirR().equals(r.toString())) {
                        tmpx.setId(ie);
                        obiektDOKmrjsfSel.add(tmpx);
                        ie++;
                //}
            }
                if (wpisView.getMiesiacOd() != null) {
                    obiektDOKmrjsfSelX.clear();
                    Iterator itxX;
                    itxX = obiektDOKjsfSel.iterator();
                    Integer r = wpisView.getRokWpisu();
                    String mOd = wpisView.getMiesiacOd();
                    Integer mOdI = Integer.parseInt(mOd);
                    String mDo = wpisView.getMiesiacDo();
                    Integer mDoI = Integer.parseInt(mDo);
                    Map<Integer, String> mapa;
                    while (itxX.hasNext()) {
                        Amodok tmpx = (Amodok) itxX.next();
                        for (int i = mOdI; i <= mDoI; i++) {
                         //   if (tmpx.getPkpirM().equals(mapa.get(i)) && tmpx.getPkpirR().equals(r.toString())) {
                                obiektDOKmrjsfSelX.add(tmpx);
                         //   }
        }
                    }
                }
            }
        }
    }

    
    
}
