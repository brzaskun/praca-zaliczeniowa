/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.ZUSDAO;
import entity.Zusstawki;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class ZUSstawkiView implements Serializable{
    @Inject
    private ZUSDAO zusDAO;
    @Inject
    private Zusstawki selected;
    
    private List<Zusstawki> listapobranychstawek;

    public ZUSstawkiView() {
        listapobranychstawek = new ArrayList<>();
       
    }

    @PostConstruct
    private void init() {
        try {
            listapobranychstawek = zusDAO.findAll();
        } catch (Exception e) {
        }
    }
    
     public void dodajzus(){
         try{
         zusDAO.dodaj(selected);
          listapobranychstawek.add(selected);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno parametr ZUS do podatnika za m-c:", selected.getZusstawkiPK().getMiesiac());
         FacesContext.getCurrentInstance().addMessage(":formzus:msgzus" , msg);
       
         } catch (Exception e) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru ZUS. Wpis za taki miesiąc już istnieje", "");
         FacesContext.getCurrentInstance().addMessage(":formzus:msgzus", msg);
       
         }
        
     }

     
      public void usunzus(){
        int index = listapobranychstawek.size()-1;
        selected = listapobranychstawek.get(index);
        zusDAO.destroy(selected);
        listapobranychstawek.remove(index);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usunieto parametr ZUS do podatnika za m-c:", selected.getZusstawkiPK().getMiesiac());
         FacesContext.getCurrentInstance().addMessage(":formzus:msgzus" , msg);
      
     }
    
    
    public ZUSDAO getZusDAO() {
        return zusDAO;
    }

    public void setZusDAO(ZUSDAO zusDAO) {
        this.zusDAO = zusDAO;
    }

    public Zusstawki getSelected() {
        return selected;
    }

    public void setSelected(Zusstawki selected) {
        this.selected = selected;
    }

    public List<Zusstawki> getListapobranychstawek() {
        return listapobranychstawek;
    }
  
    
}
