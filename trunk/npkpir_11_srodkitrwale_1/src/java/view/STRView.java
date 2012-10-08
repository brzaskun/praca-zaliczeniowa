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
import java.util.Map;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean(name="SrodkiTrwaleView")
@ViewScoped
public class STRView implements Serializable{
    @Inject
    private STRDAO sTRDAO;
    
    private SrodekTrw selectedSTR;

    private Integer ilesrodkow;
    
    private boolean pokazSTR;
    
    
    
    public STRView() {
        selectedSTR = new SrodekTrw();
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
    
    public void dodajSrodekTrwaly(SrodekTrw STR){
        try { 
        Double netto = STR.getWartoscPoczatkowa();
        Double stawkaamortyzacji = STR.getStawkaAmortyzacji();
        BigDecimal tmp1 = BigDecimal.valueOf(stawkaamortyzacji);
        tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
        tmp1 = tmp1.multiply(BigDecimal.valueOf(netto));
        tmp1 = tmp1.divide(BigDecimal.valueOf(100));
        tmp1 = tmp1.setScale(2, RoundingMode.HALF_EVEN);
        BigDecimal tmp2 = tmp1.divide(BigDecimal.valueOf(12));
        tmp2 = tmp2.setScale(2, RoundingMode.HALF_EVEN);
        Double odpisrok = Double.parseDouble(tmp1.toString());
        Double odpismiesiac = Double.parseDouble(tmp2.toString());
        STR.setRocznaKwotaOdpisu(odpisrok);
        STR.setMiesiecznaKwotaOdpisu(odpismiesiac);
        sTRDAO.getEwidencjaSrodkowTrwalych().add(STR);
        setSelectedSTR(STR);
        RequestContext ctx = null;
        ctx.getCurrentInstance().update("srodki:panelekXA");
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO,"Nowy srodek zachowany", selectedSTR.getNazwaSrodka());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Nowy srodek nie zachowany", selectedSTR.getNazwaSrodka());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        }
   }
    
    public int ile(){
        return sTRDAO.getEwidencjaSrodkowTrwalych().size();
    }
    
   
}
