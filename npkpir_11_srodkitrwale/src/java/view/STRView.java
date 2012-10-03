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
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;

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

    public STRView() {
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
        FacesMessage msg = new FacesMessage("Nowy srodek zachowany", selectedSTR.getNazwaSrodka());
        FacesContext.getCurrentInstance().addMessage(null, msg);
       
   }
}
