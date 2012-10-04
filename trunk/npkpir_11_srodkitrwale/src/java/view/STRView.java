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
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
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
    @Inject
    private SrodekTrw selectedSTR;

    private Integer ilesrodkow;
    
    private boolean pokazSTR;
    
    
    public STRView() {
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
