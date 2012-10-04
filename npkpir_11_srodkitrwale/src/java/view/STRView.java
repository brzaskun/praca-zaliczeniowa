/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.STRDAO;
import entity.SrodekTrw;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
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
    
    public void dodajSrodekTrwaly(){
          sTRDAO.getEwidencjaSrodkowTrwalych().add(selectedSTR);
         RequestContext ctx = null;
         ctx.getCurrentInstance().update("srodki:panelekXA");
        FacesMessage msg = new FacesMessage("Nowy srodek zachowany", selectedSTR.getNazwaSrodka());
        FacesContext.getCurrentInstance().addMessage(null, msg);
   }
    
    public int ile(){
        return sTRDAO.getEwidencjaSrodkowTrwalych().size();
    }
    
   
}
