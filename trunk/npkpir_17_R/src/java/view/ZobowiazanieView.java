/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.ZobowiazanieDAO;
import entity.Zobowiazanie;
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
public class ZobowiazanieView implements Serializable{
    @Inject
    private ZobowiazanieDAO zobowiazanieDAO;
    @Inject
    private Zobowiazanie selected;
    
    private List<Zobowiazanie> listapobranychstawek;

    public ZobowiazanieView() {
        listapobranychstawek = new ArrayList<>();
       
    }

    @PostConstruct
    private void init(){
        Collection c;
        c = zobowiazanieDAO.getDownloaded();
        listapobranychstawek.addAll(c); 
    }
    
     public void dodaj(){
         try{
         zobowiazanieDAO.dodajNowyWpis(selected);
         listapobranychstawek.add(selected);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno zobowiązanie za rok i mc:", selected.getZobowiazaniePK().getRok().toString().concat(selected.getZobowiazaniePK().getMc()) );
         FacesContext.getCurrentInstance().addMessage(":formzus:msgzus" , msg);
       
         } catch (Exception e) {
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno zobowiązania. Wystapil błąd.", "");
         FacesContext.getCurrentInstance().addMessage(":formzus:msgzus", msg);
       
         }
        
     }

     
      public void usun(){
        int index = listapobranychstawek.size()-1;
        selected = listapobranychstawek.get(index);
        zobowiazanieDAO.destroy(selected);
        listapobranychstawek.remove(index);
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Usunieto zobowiązanie za rok i mc:", selected.getZobowiazaniePK().toString());
         FacesContext.getCurrentInstance().addMessage(":formzus:msgzus" , msg);
      
     }

    public List<Zobowiazanie> getListapobranychstawek() {
        return listapobranychstawek;
    }

  
    
    public void setListapobranychstawek(List<Zobowiazanie> listapobranychstawek) {
        this.listapobranychstawek = listapobranychstawek;
    }

    public Zobowiazanie getSelected() {
        return selected;
    }

    public void setSelected(Zobowiazanie selected) {
        this.selected = selected;
    }
    
      
}
