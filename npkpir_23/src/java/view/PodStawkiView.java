/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PodStawkiDAO;
import entity.Podstawki;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class PodStawkiView implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private PodStawkiDAO podStawkiDAO;
    @Inject
    private Podstawki selected;
    
    private List<Podstawki> listapobranychstawek;

    public PodStawkiView() {
        listapobranychstawek = new ArrayList<>();
       
    }

    @PostConstruct
    private void init(){
        Collection c = podStawkiDAO.findAll();
        listapobranychstawek.addAll(c); 
    }
    
     public void dodaj(){
         try{
         podStawkiDAO.dodaj(selected);
          listapobranychstawek.add(selected);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno parametr podatkowy za rok:", selected.getRok().toString() );
         FacesContext.getCurrentInstance().addMessage(":formzus:msgzus" , msg);
       
         } catch (Exception e) { E.e(e); 
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno parametru podatkowego. Wystapil błąd.", "");
         FacesContext.getCurrentInstance().addMessage(":formzus:msgzus", msg);
       
         }
        
     }

     
      public void usun(){
        int index = listapobranychstawek.size()-1;
        selected = listapobranychstawek.get(index);
        podStawkiDAO.destroy(selected);
        listapobranychstawek.remove(index);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usunieto parametru podatkowego za rok:", selected.getRok().toString());
         FacesContext.getCurrentInstance().addMessage(":formzus:msgzus" , msg);
      
     }

    public List<Podstawki> getListapobranychstawek() {
        return listapobranychstawek;
    }

    public void setListapobranychstawek(List<Podstawki> listapobranychstawek) {
        this.listapobranychstawek = listapobranychstawek;
    }

    public Podstawki getSelected() {
        return selected;
    }

    public void setSelected(Podstawki selected) {
        this.selected = selected;
    }
    
      
}
