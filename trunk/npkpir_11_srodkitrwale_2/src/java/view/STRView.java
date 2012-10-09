/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.STRDAO;
import entity.SrodekTrw;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean(name="SrodkiTrwaleView")
@RequestScoped
public class STRView implements Serializable{
    @Inject
    private STRDAO sTRDAO;
    
    private SrodekTrw selectedSTR;

    private Integer ilesrodkow;
    
    private boolean pokazSTR;
    
    private boolean umorzeniaBiezace;
    
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
    

    public boolean isUmorzeniaBiezace() {
        return umorzeniaBiezace;
    }

    public void setUmorzeniaBiezace(boolean umorzeniaBiezace) {
        this.umorzeniaBiezace = umorzeniaBiezace;
    }
    
    public STRView() {
        selectedSTR = new SrodekTrw();
         dokHashTable = new HashMap<String, SrodekTrw>();
        kluczDOKjsf = new ArrayList<String>();
        obiektDOKjsf = new ArrayList<SrodekTrw>();
        obiektDOKjsfSel = new ArrayList<SrodekTrw>();
        obiektDOKmrjsfSel = new ArrayList<SrodekTrw>();
        obiektDOKmrjsfSelX = new ArrayList<SrodekTrw>();
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

    
    
    public boolean isPokazSTR() {
        return pokazSTR;
    }

    public void setPokazSTR(boolean pokazSTR) {
        this.pokazSTR = pokazSTR;
    }

    
    
    public Integer getIlesrodkow() {
        return ilesrodkow;
    }

    public void setIlesrodkow(Integer ilesrodkow) {
        this.ilesrodkow = ilesrodkow;
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

    
    public void dodajSrodekTrwaly(SrodekTrw STR){
        try { 
        Double netto = STR.getNetto();
        Double stawkaamortyzacji = STR.getStawka();
        BigDecimal tmp1 = BigDecimal.valueOf(stawkaamortyzacji);
        tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
        tmp1 = tmp1.multiply(BigDecimal.valueOf(netto));
        tmp1 = tmp1.divide(BigDecimal.valueOf(100));
        tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal tmp2 = tmp1.divide(BigDecimal.valueOf(12),2, RoundingMode.HALF_EVEN);
        tmp2 = tmp2.setScale(2, RoundingMode.HALF_EVEN);
        Double odpisrok = Double.parseDouble(tmp1.toString());
        Double odpismiesiac = Double.parseDouble(tmp2.toString());
        STR.setOdpisrok(odpisrok);
        STR.setOdpismc(odpismiesiac);
        sTRDAO.dodajNowyWpis(STR);
        setSelectedSTR(STR);
        RequestContext ctx = null;
        ctx.getCurrentInstance().update("srodki:panelekXA");
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Nowy srodek zachowany", selectedSTR.getNazwa());
        FacesContext.getCurrentInstance().addMessage(null, msg);
//        if(umorzeniaBiezace==true){
//            Double odpis = STR.getMiesiecznaKwotaOdpisu();
//        }
        } catch (Exception e) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nowy srodek nie zachowany", selectedSTR.getNazwa());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
   }
    
    public int ile(){
        return sTRDAO.getdownloadedSTR().size();
    }
    
     public void aktualizujTabele(AjaxBehaviorEvent e) {
        RequestContext ctx = null;
        ctx.getCurrentInstance().update("formSTR:srodkiLista");
        ctx.getCurrentInstance().update("westSrodki:westSrodkiWidok");
    }

    
    public static void main(String [] args){
        Double netto = 150.0;
        Double stawkaamortyzacji = 20.0;
        BigDecimal tmp1 = BigDecimal.valueOf(stawkaamortyzacji);
        tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
        tmp1 = tmp1.multiply(BigDecimal.valueOf(netto));
        tmp1 = tmp1.divide(BigDecimal.valueOf(100));
        tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal tmp2 = tmp1.divide(BigDecimal.valueOf(12),2, RoundingMode.HALF_EVEN);
        tmp2 = tmp2.setScale(2, RoundingMode.HALF_EVEN);
        Double odpisrok = Double.parseDouble(tmp1.toString());
        Double odpismiesiac = Double.parseDouble(tmp2.toString());
   }
}
