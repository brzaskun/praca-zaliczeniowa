/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PodatnikDAO;
import entity.Podatnik;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import mail.Mail;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class PodatnikView {
    @Inject
    private PodatnikDAO podatnikDAO;
    private Podatnik selected;

    public Podatnik getSelected() {
        return selected;
    }

    public void setSelected(Podatnik selected) {
        this.selected = selected;
    }
    
     public void dodajNowyWpis(){
         System.out.println("Wpis do bazy zaczynam");
         sformatuj();
             try {
                 podatnikDAO.dodajNowyWpis(selected);
                 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dodatno nowego podatnika.", selected.getNazwapelna());
                 FacesContext.getCurrentInstance().addMessage(null, msg);
                 Mail.nadajMail(selected.getEmail(), selected.getNazwapelna());
                 
             } catch (Exception e) {
                 System.out.println(e.getStackTrace().toString());
                 FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Uzytkownik o takim NIP już istnieje.", e.getStackTrace().toString());
                 FacesContext.getCurrentInstance().addMessage(null, msg);
             }
    }
   
    public void sformatuj(){
        String formatka=null;
        selected.setNazwapelna(selected.getNazwapelna().toUpperCase());
        selected.setImie(selected.getImie().substring(0,1).toUpperCase()+selected.getImie().substring(1).toLowerCase());
        selected.setNazwisko(selected.getNazwisko().substring(0,1).toUpperCase()+selected.getNazwisko().substring(1).toLowerCase());
        
    }
    
}
