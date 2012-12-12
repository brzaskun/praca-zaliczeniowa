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
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "SrodkiTrwaleView")
@ViewScoped
public class STRView implements Serializable {

    @Inject
    private STRDAO sTRDAO;
    private Integer ilesrodkow;

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

    //tutaj oblicza ilosc odpisow
    public void dodajSrodekTrwaly(SrodekTrw STR) {
        try {
            Double netto = STR.getNetto();
            Double stawkaamortyzacji = STR.getStawka();
            BigDecimal tmp1 = BigDecimal.valueOf(stawkaamortyzacji);
            tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
            tmp1 = tmp1.multiply(BigDecimal.valueOf(netto));
            tmp1 = tmp1.divide(BigDecimal.valueOf(100));
            tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
            BigDecimal tmp2 = tmp1.divide(BigDecimal.valueOf(12), 2, RoundingMode.HALF_EVEN);
            tmp2 = tmp2.setScale(2, RoundingMode.HALF_EVEN);
            Double odpisrok = Double.parseDouble(tmp1.toString());
            Double odpismiesiac = Double.parseDouble(tmp2.toString());
            STR.setOdpisrok(odpisrok);
            if (stawkaamortyzacji == 100) {
                STR.setOdpismc(odpisrok);
            } else {
                STR.setOdpismc(odpismiesiac);
            }
            //oblicza planowane umorzenia
            Double opm = STR.getOdpismc();
            Double max = STR.getNetto();
            Double nar = 0.0;
            List<Double> listaplanum = new ArrayList<Double>();

            while (max - nar > 0) {
                Double odp = (max - nar) > opm ? opm : max - nar;
                DecimalFormat df2 = new DecimalFormat("###.##");
                double tmp = odp.doubleValue();
                String tmpX = df2.format(tmp);
                tmpX = tmpX.replace(",", ".");
                odp = Double.valueOf(tmpX);
                listaplanum.add(odp);
                nar = nar + odp;
            }
            STR.setUmorzPlan(listaplanum);
            sTRDAO.dodaj(STR);
            RequestContext ctx = null;
            ctx.getCurrentInstance().update("srodki:panelekXA");
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Nowy srodek nie zachowany", STR.getNazwa());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }

    public void aktualizujTabele(AjaxBehaviorEvent e) {
        RequestContext ctx = null;
        ctx.getCurrentInstance().update("formSTR:srodkiLista");
        ctx.getCurrentInstance().update("westSrodki:westSrodkiWidok");
    }

    public void aktualizujTabele2(AjaxBehaviorEvent e) {
        RequestContext.getCurrentInstance().update("formSTR:dokumUmorzenieLista");
        RequestContext.getCurrentInstance().update("westSrodki:westSrodkiWidok");
    }

    public int ile() {
        return sTRDAO.getDownloaded().size();
    }

    public static void main(String[] args) {
        Double opm = 9.33;
        Double max = 45.11;
        Double nar = 0.0;
        List<Double> listaplanum = new ArrayList<>();
        while (max - nar > 0) {
            Double odp = (max - nar) > opm ? opm : max - nar;
            DecimalFormat df2 = new DecimalFormat("###.##");
            double tmp = odp.doubleValue();
            String tmpX = df2.format(tmp);
            tmpX = tmpX.replace(",", ".");
            odp = Double.valueOf(tmpX);
            listaplanum.add(odp);
            nar = nar + odp;
        }
    }
}
