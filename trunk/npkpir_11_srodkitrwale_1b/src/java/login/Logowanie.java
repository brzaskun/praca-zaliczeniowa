/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import java.io.Serializable;
import java.security.Principal;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Osito
 */
@ManagedBean(name="Logowanie")
@SessionScoped
public class Logowanie implements Serializable{
 
    private String uzytk;
    private String haslo;
 
    public Logowanie(){
         HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(session != null){
            session.invalidate();
        }
    }

    public String getUzytk() {
        return uzytk;
    }

    public void setUzytk(String uzytk) {
        this.uzytk = uzytk;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }
 
   
 
    public String login(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(session != null){
            session.invalidate();
        }
        String message = "";
        String navto = "";
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
 
            //Login via the Servlet Context
            request.login(uzytk, haslo);
 
            //Retrieve the Principal
            Principal principal = request.getUserPrincipal();
 
            //Display a message based on the User role
            if(request.isUserInRole("Administrator")){
                message = "Username : " + principal.getName() + " You are an Administrator, you can really f**k things up now";
                navto="Administrator";
            }else if(request.isUserInRole("Manager")){
                message = "Username : " + principal.getName() + " You are only a Manager, Don't you have a Spreadsheet to be working on??";
                navto = "Manager";
            }else if(request.isUserInRole("Bookkeeper")){
                message = "Username : " + principal.getName() + " You are only a Manager, Don't you have a Spreadsheet to be working on??";
                navto = "Bookkeeper";
            }else if(request.isUserInRole("Guest")){
                message = "Username : " + principal.getName() + " You're wasting my resources...";
                navto = "Guest";
            }
            message = message+(" Jest "+request.isUserInRole("Guest") +" pojest "+principal.getName());
            //Add the welcome message to the faces context
            return navto;
        } catch (ServletException e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Błąd - nieprawidłowy login lub hasło",null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return "failure";
    }
 
    public void logout(ActionEvent e){
     HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(session != null){
            session.invalidate();
        }
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/index.xhtml");
    }
}