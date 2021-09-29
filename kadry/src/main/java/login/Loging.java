/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import dao.UzFacade;
import entity.Uz;
import error.E;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class Loging implements Serializable {

    private String uzytkownik;
    private String haslo;
    @Inject
    private UzFacade uzFacade;
    @Inject WpisView wpisView;

    public Loging() {
       
    }

    @PostConstruct
    private void init() { //E.m(this);
        try {
            invalidatesession();
            uzytkownik ="2";
            haslo = "12";
        } catch (Exception e) {
            E.e(e);
        }
    }

    public String login() {
        //invalidatesession();
        String navto = "";
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            System.out.println(uzytkownik);
            System.out.println(haslo);
            if (haslo.equals("haslo")) {
                navto = "nowehaslo";
            } else {
                request.login(uzytkownik, haslo);
                request.setAttribute("user", uzytkownik);
                String lo = request.getRemoteUser(); 
                if (request.isUserInRole("Administrator")) {
                    navto = "Administrator";
                } else if (request.isUserInRole("Pracownik")) {
                    navto = "PortalPracownik";
                } else if (request.isUserInRole("Pracodawca")) {
                    navto = "PortalPracodawca";
                }
                Uz uzer = uzFacade.findUzByLogin(lo);
                wpisView.setUzer(uzer);
                wpisView.setFirma(uzer.getFirma());
                wpisView.init();
            }
            //Msg.msg("Zweryfikowano hasło");
            System.out.println("zweryfikowano haslo");
            return navto;
        } catch (Exception exp) {
            E.e(exp);
            Msg.msg("e", "Podałeś nieprawidłowy login lub hasło. Nie możesz rozpocząć pracy z programem");
            return "failure";
        }
    }


    public void invalidatesession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    //po okreslonym czasie bezczynnosci na stronie Access denied przerzuci do strony logowania
    public void autologin() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/login.xhtml?faces-redirect=true");
    }
    
    public void uniewaznijsesje() {
        invalidatesession();
    }

    public String getUzytkownik() {
        return uzytkownik;
    }

    public void setUzytkownik(String uzytkownik) {
        this.uzytkownik = uzytkownik;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }
    
    

}
