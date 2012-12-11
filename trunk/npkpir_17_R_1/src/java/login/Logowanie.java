/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import dao.PodatnikDAO;
import dao.SesjaDAO;
import dao.UzDAO;
import entity.Sesja;
import java.io.Serializable;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;
import view.GuestView;
import view.SesjaView;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean(name="Logowanie")
@SessionScoped
public class Logowanie implements Serializable{
 
    private String uzytk;
    private String haslo;
    @Inject
    UzDAO uzDAO;
    @Inject
    PodatnikDAO podatnikDAO;
    @Inject
    private Sesja sesja;
    @Inject
    private SesjaDAO sesjaDAO;
    
    
 
    public Logowanie(){
         HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(session != null){
            session.invalidate();
        }
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
                String nip = uzDAO.find(uzytk).getFirma();
                String firma = podatnikDAO.findN(nip).getNazwapelna();
                GuestView.setPodatnikString(firma);
                WpisView.setPodatnikWpisuS(firma);
                message = "Username : " + principal.getName() + " You're wasting my resources...";
                navto = "Guest";
            } else if(request.isUserInRole("Noobie")){
                message = "Username : " + principal.getName() + " You're wasting my resources...";
                navto = "Noobie";
            }
            SesjaView.setUzytk(uzytk);
            String sessionId = SesjaView.getNrsesji();
            System.out.println("Sesja przeniesiona Logowanie" + sessionId );
            sesja.setNrsesji(sessionId);
            sesja.setUzytkownik(SesjaView.getUzytk());
            sesja.setIloscdokumentow(0);
            sesja.setIloscmaili(0);
            sesja.setIloscwydrukow(0);
            HttpServletRequest httpServletRequest = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();  
            String ip = httpServletRequest.getRemoteAddr();  
            sesja.setIp(ip);
            Calendar calendar = Calendar.getInstance();
            sesja.setZalogowanie(new Timestamp(calendar.getTime().getTime()));
                try {
                    sesjaDAO.dodajNowyWpis(sesja);
                } catch (Exception e) {
                    sesjaDAO.edit(sesja);
                }
            //Add the welcome message to the faces context
            return navto;
        } catch (ServletException e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Błąd - nieprawidłowy login lub hasło",null);
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
        return "failure";
    }
 
    public void logout(){
     HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if(session != null){
            session.invalidate();
        }
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/login.xhtml");
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

    public UzDAO getUzDAO() {
        return uzDAO;
    }

    public void setUzDAO(UzDAO uzDAO) {
        this.uzDAO = uzDAO;
    }

    public PodatnikDAO getPodatnikDAO() {
        return podatnikDAO;
    }

    public void setPodatnikDAO(PodatnikDAO podatnikDAO) {
        this.podatnikDAO = podatnikDAO;
    }

    public Sesja getSesja() {
        return sesja;
    }

    public void setSesja(Sesja sesja) {
        this.sesja = sesja;
    }

    public SesjaDAO getSesjaDAO() {
        return sesjaDAO;
    }

    public void setSesjaDAO(SesjaDAO sesjaDAO) {
        this.sesjaDAO = sesjaDAO;
    }

}