/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.ZobowiazanieDAO;
import entity.Zobowiazanie;
import error.E;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import org.joda.time.DateTime;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ZobowiazanieView implements Serializable{
    @Inject
    private ZobowiazanieDAO zobowiazanieDAO;
    @Inject
    private Zobowiazanie selected;
    @Inject 
    private WpisView wpisView;
    
    private List<Zobowiazanie> listapobranychstawek;

    public ZobowiazanieView() {
        listapobranychstawek = Collections.synchronizedList(new ArrayList<>());
       
    }

    @PostConstruct
    private void init() { //E.m(this);
        Collection c  = zobowiazanieDAO.findAll();
        try{
            listapobranychstawek.addAll(c);
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            String rok = String.valueOf(new DateTime().getYear());
            if(request.isUserInRole("Manager")){
                wpisView.setRokWpisuSt(rok);
            }
        } catch (Exception e) { E.e(e); }
    }
    
     public void dodaj(){
         try{
         zobowiazanieDAO.create(selected);
         listapobranychstawek.add(selected);
         selected = new Zobowiazanie();
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno zobowiązanie za rok i mc:", selected.getZobowiazaniePK().getRok().concat(selected.getZobowiazaniePK().getMc()) );
         FacesContext.getCurrentInstance().addMessage(":formzus:msgzus" , msg);
       
         } catch (Exception e) { E.e(e); 
         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Niedodatno zobowiązania. Wystapil błąd.", "");
         FacesContext.getCurrentInstance().addMessage(":formzus:msgzus", msg);
       
         }
        
     }
        public void updateZobowiazanie(Zobowiazanie zobowiazanie) {
        zobowiazanieDAO.edit(zobowiazanie);
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Zobowiazanie zostało zaktualizowane"));
    }

     
      public void usun(Zobowiazanie zobowiazanie){
        zobowiazanieDAO.remove(zobowiazanie);
        listapobranychstawek.remove(zobowiazanie);
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

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
    
      
}
