/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.STRDAO;
import entity.SrodekTrw;
import java.io.Serializable;
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
@ManagedBean(name="STRTableView")
@RequestScoped
public class STRTabView implements Serializable{
    @Inject
    private STRDAO sTRDAO;
    
    private SrodekTrw selectedSTR;

   
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
     
    private HashMap<String,SrodekTrw> dokHashTable;
    //tablica kluczy do obiektów
    private List<String> kluczDOKjsf;
    //tablica obiektów
    private List<SrodekTrw> obiektDOKjsf;
    //tablica obiektw danego klienta
    private List<SrodekTrw> obiektDOKjsfSel;
    //tablica obiektów danego klienta z określonego roku i miesiąca
    private List<SrodekTrw> obiektDOKmrjsfSel;
     //tablica obiektów danego klienta z określonego roku i miesiecy
    private List<SrodekTrw> obiektDOKmrjsfSelX;
    

   
    
    public STRTabView() {
        selectedSTR = new SrodekTrw();
         dokHashTable = new HashMap<String, SrodekTrw>();
        kluczDOKjsf = new ArrayList<String>();
        obiektDOKjsf = new ArrayList<SrodekTrw>();
        obiektDOKjsfSel = new ArrayList<SrodekTrw>();
        obiektDOKmrjsfSel = new ArrayList<SrodekTrw>();
        obiektDOKmrjsfSelX = new ArrayList<SrodekTrw>();
    }

    public STRDAO getsTRDAO() {
        return sTRDAO;
    }

    public void setsTRDAO(STRDAO sTRDAO) {
        this.sTRDAO = sTRDAO;
    }

    public SrodekTrw getSelectedSTR() {
        return selectedSTR;
    }

    public void setSelectedSTR(SrodekTrw selectedSTR) {
        this.selectedSTR = selectedSTR;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public HashMap<String, SrodekTrw> getDokHashTable() {
        return dokHashTable;
    }

    public void setDokHashTable(HashMap<String, SrodekTrw> dokHashTable) {
        this.dokHashTable = dokHashTable;
    }

    public List<String> getKluczDOKjsf() {
        return kluczDOKjsf;
    }

    public void setKluczDOKjsf(List<String> kluczDOKjsf) {
        this.kluczDOKjsf = kluczDOKjsf;
    }

    public List<SrodekTrw> getObiektDOKjsf() {
        return obiektDOKjsf;
    }

    public void setObiektDOKjsf(List<SrodekTrw> obiektDOKjsf) {
        this.obiektDOKjsf = obiektDOKjsf;
    }

    public List<SrodekTrw> getObiektDOKjsfSel() {
        return obiektDOKjsfSel;
    }

    public void setObiektDOKjsfSel(List<SrodekTrw> obiektDOKjsfSel) {
        this.obiektDOKjsfSel = obiektDOKjsfSel;
    }

    public List<SrodekTrw> getObiektDOKmrjsfSel() {
        return obiektDOKmrjsfSel;
    }

    public void setObiektDOKmrjsfSel(List<SrodekTrw> obiektDOKmrjsfSel) {
        this.obiektDOKmrjsfSel = obiektDOKmrjsfSel;
    }

    public List<SrodekTrw> getObiektDOKmrjsfSelX() {
        return obiektDOKmrjsfSelX;
    }

    public void setObiektDOKmrjsfSelX(List<SrodekTrw> obiektDOKmrjsfSelX) {
        this.obiektDOKmrjsfSelX = obiektDOKmrjsfSelX;
    }

 
    
       @PostConstruct
    public void init() {
        if (wpisView.getPodatnikWpisu() != null) {
            Collection c = null;
            try {
                c = sTRDAO.getdownloadedSTR();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            if (c != null) {
                Iterator it;
                it = c.iterator();
                int j=1;
                while (it.hasNext()) {
                    SrodekTrw tmp = (SrodekTrw) it.next();
                    tmp.setNrsrodka(j);
                    j++;
                    kluczDOKjsf.add(tmp.getId().toString());
                    obiektDOKjsf.add(tmp);
                    if (tmp.getPodatnik().equals(wpisView.getPodatnikWpisu())) {
                        obiektDOKjsfSel.add(tmp);
                    }
                    dokHashTable.put(tmp.getId().toString(), tmp);
                }
                Iterator itx;
                itx = obiektDOKjsfSel.iterator();
                while (itx.hasNext()) {
                    SrodekTrw tmpx = (SrodekTrw) itx.next();
                    String m = wpisView.getMiesiacWpisu();
                    Integer r = wpisView.getRokWpisu();
                    //if (tmpx.getPkpirM().equals(m) && tmpx.getPkpirR().equals(r.toString())) {
                   
                        obiektDOKmrjsfSel.add(tmpx);
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
                    mapa = new HashMap<Integer, String>();
                    mapa.put(1, "01");
                    mapa.put(2, "02");
                    mapa.put(3, "03");
                    mapa.put(4, "04");
                    mapa.put(5, "05");
                    mapa.put(6, "06");
                    mapa.put(7, "07");
                    mapa.put(8, "08");
                    mapa.put(9, "09");
                    mapa.put(10, "10");
                    mapa.put(11, "11");
                    mapa.put(12, "12");
                    while (itxX.hasNext()) {
                        SrodekTrw tmpx = (SrodekTrw) itxX.next();
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

    
  public void generujodpisy(){
            Map<Integer, String> mapa;
                    mapa = new HashMap<Integer, String>();
                    mapa.put(1, "01");
                    mapa.put(2, "02");
                    mapa.put(3, "03");
                    mapa.put(4, "04");
                    mapa.put(5, "05");
                    mapa.put(6, "06");
                    mapa.put(7, "07");
                    mapa.put(8, "08");
                    mapa.put(9, "09");
                    mapa.put(10, "10");
                    mapa.put(11, "11");
                    mapa.put(12, "12");
             Integer rok = wpisView.getRokWpisu();
             String mc = wpisView.getMiesiacWpisu();
             List<SrodekTrw> lista = null;
             lista.addAll(obiektDOKjsfSel);
             
     }
    
  
}
