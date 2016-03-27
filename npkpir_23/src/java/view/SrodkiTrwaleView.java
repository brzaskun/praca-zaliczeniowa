/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansSrodkiTrwale.SrodkiTrwBean;
import dao.STRDAO;
import entity.SrodekTrw;
import entity.SrodekTrw_NowaWartosc;
import entity.Srodkikst;
import entityfk.Dokfk;
import error.E;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SrodkiTrwaleView implements Serializable {


    @Inject
    private STRDAO sTRDAO;
    private Integer ilesrodkow;
    @Inject
    private Srodkikst srodekkategoria;
    @Inject
    private SrodekTrw selectedSTR;
    @Inject
    private Dokfk dokfk;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public SrodkiTrwaleView() {
        E.m(this);
    }
    
    
  
    public void dodajSTR() {
        String podatnik = wpisView.getPodatnikWpisu();
        selectedSTR.setPodatnik(podatnik);
        RequestContext.getCurrentInstance().execute("PF('dialogwpissrodkitrwale').hide();");
        dodajSrodekTrwaly(selectedSTR);
        Msg.msg("Dodaje srodek");
    }
    
    public void dodajSrodekTrwalyDokfk(List<Dokfk> wybranydok) {
        try {
            this.dokfk = wybranydok.get(0);
        } catch (Exception e) { E.e(e); 
        }
    }
    
    public void dodajSrodekTrwalyFK() {
        try {
            selectedSTR.setDatazak(dokfk.getDataoperacji());
            selectedSTR.setPodatnik(wpisView.getPodatnikWpisu());
            selectedSTR.setDatazak(dokfk.getDataoperacji());
            selectedSTR.setUmorzeniezaksiegowane(Boolean.FALSE);
            selectedSTR.setNrwldokzak(dokfk.getNumerwlasnydokfk());
            selectedSTR.setZlikwidowany(0);
            selectedSTR.setDatasprzedazy("");
            selectedSTR.setDokfk(dokfk);
            selectedSTR.setNrwldokumentu(dokfk.getNumerwlasnydokfk());
            dodajSrodekTrwaly(selectedSTR);
            selectedSTR = new SrodekTrw();
            srodekkategoria = null;
        } catch (Exception e) { E.e(e); 
            System.out.println("StrodkiTrwaleView - dodajSrodekTrwalyFK() blad");
            Msg.msg("e", "Blad podczas dodawanie srodkow trwalych");
        }
    }
    //tutaj oblicza ilosc odpisow przed przyporzadkowaniem do miesiecy
//    public void dodajSrodekTrwaly(SrodekTrw dodawanysrodektrwaly) {
//        try {
//            Double netto = dodawanysrodektrwaly.getNetto()-dodawanysrodektrwaly.getNiepodlegaamortyzacji();
//            Double stawkaamortyzacji = dodawanysrodektrwaly.getStawka();
//            BigDecimal tmp1 = BigDecimal.valueOf(stawkaamortyzacji);
//            tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
//            tmp1 = tmp1.multiply(BigDecimal.valueOf(netto));
//            tmp1 = tmp1.divide(BigDecimal.valueOf(100));
//            tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
//            BigDecimal tmp2 = tmp1.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_EVEN);
//            tmp2 = tmp2.setScale(2, RoundingMode.HALF_EVEN);
//            Double odpisrok = Double.parseDouble(tmp1.toString());
//            Double odpismiesiac = Double.parseDouble(tmp2.toString());
//            dodawanysrodektrwaly.setOdpisrok(odpisrok);
//            if (stawkaamortyzacji == 100) {
//                dodawanysrodektrwaly.setOdpismc(odpisrok);
//            } else {
//                dodawanysrodektrwaly.setOdpismc(odpismiesiac);
//            }
//            //oblicza planowane umorzenia
//            Double opm = dodawanysrodektrwaly.getOdpismc();
//            Double max = dodawanysrodektrwaly.getNetto()-dodawanysrodektrwaly.getNiepodlegaamortyzacji();
//            //uwzglednia umorzenie poczatkowe przy odpisach
//            try{
//            max = max - dodawanysrodektrwaly.getUmorzeniepoczatkowe();
//            } catch (Exception et){}
//            Double nar = 0.0;
//            List<Double> listaplanum = new ArrayList<Double>();
//            while (max - nar > 0) {
//                Double odp = (max - nar) >= opm ? opm : max - nar;
//                listaplanum.add(Z.z(odp.doubleValue()));
//                nar = Z.z(nar + odp);
//            }
//            dodawanysrodektrwaly.setUmorzPlan(listaplanum);
//            dodawanysrodektrwaly.setUmorzWyk(SrodkiTrwBean.generujumorzeniadlasrodka(dodawanysrodektrwaly, wpisView));
//            sTRDAO.dodaj(dodawanysrodektrwaly);
//            RequestContext.getCurrentInstance().update("srodki:panelekXA");
//            Msg.msg("i", "Środek trwały "+dodawanysrodektrwaly.getNazwa()+" dodany", "formSTR:messages");
//        } catch (Exception e) { E.e(e); 
//            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nowy srodek nie zachowany", dodawanysrodektrwaly.getNazwa());
//            FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//
//    }
    
    public void dodajSrodekTrwaly(SrodekTrw dodawanysrodektrwaly) {
        try {
            SrodkiTrwBean.odpisroczny(dodawanysrodektrwaly);
            SrodkiTrwBean.odpismiesieczny(dodawanysrodektrwaly);
            //oblicza planowane umorzenia
            dodawanysrodektrwaly.setUmorzPlan(SrodkiTrwBean.naliczodpisymczne(dodawanysrodektrwaly));
            dodawanysrodektrwaly.setUmorzWyk(SrodkiTrwBean.generujumorzeniadlasrodka(dodawanysrodektrwaly, wpisView));
            sTRDAO.dodaj(dodawanysrodektrwaly);
            RequestContext.getCurrentInstance().update("srodki:panelekXA");
            Msg.msg("i", "Środek trwały "+dodawanysrodektrwaly.getNazwa()+" dodany", "formSTR:messages");
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e","Nowy srodek nie zachowany "+dodawanysrodektrwaly.getNazwa());
        }
    }
    
   
    
    
//    public static void main(String[] args) {
//        SrodekTrw s = new SrodekTrw();
//        s.setNetto(1150.0);
//        s.setStawka(50.0);
//        SrodkiTrwBean.odpisroczny(s);
//        SrodkiTrwBean.odpismiesieczny(s);
//        Double max = s.getNetto();
//        Double opm = s.getOdpismc();
//        Double nar = 0.0;
//        List<Double> listaplanum = new ArrayList<>();
//        while (max - nar > 0) {
//            Double odp = (max - nar) > opm ? opm : max - nar;
////            DecimalFormat df2 = new DecimalFormat("###.##");
////            double tmp = odp.doubleValue();
////            String tmpX = df2.format(tmp);
////            tmpX = tmpX.replace(",", ".");
////            odp = Double.valueOf(tmpX);
//            listaplanum.add(Z.z(odp));
//            nar = Z.z(nar + odp);
//        }
//        System.out.println("");
//    }
    
    

    public void aktualizujTabele(AjaxBehaviorEvent e) {
        RequestContext.getCurrentInstance().update("formSTR");
        RequestContext.getCurrentInstance().update("westSrodki:westSrodkiWidok");
    }

    public void aktualizujTabele2(AjaxBehaviorEvent e) {
        RequestContext.getCurrentInstance().update("formSTR");
        RequestContext.getCurrentInstance().update("westSrodki:westSrodkiWidok");
    }

    public int ile() {
        return sTRDAO.findAll().size();
    }
    
     public void skopiujSTR() {
        try {
            selectedSTR.setKst(srodekkategoria.getSymbol());
            selectedSTR.setUmorzeniepoczatkowe(0.0);
            selectedSTR.setStawka(Double.parseDouble(srodekkategoria.getStawka()));
            RequestContext.getCurrentInstance().update("srodkiwpis:nowypanelsrodki");
        } catch (Exception e1) {
        }
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

    public Srodkikst getSrodekkategoria() {
        return srodekkategoria;
    }

    public void setSrodekkategoria(Srodkikst srodekkategoria) {
        this.srodekkategoria = srodekkategoria;
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

    public Dokfk getDokfk() {
        return dokfk;
    }

    public void setDokfk(Dokfk dokfk) {
        this.dokfk = dokfk;
    }
    
    
    
}
