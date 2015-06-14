/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import beansLogowanie.IPaddress;
import beansLogowanie.Liczniklogowan;
import dao.OstatnidokumentDAO;
import dao.PodatnikDAO;
import dao.RejestrlogowanDAO;
import dao.SesjaDAO;
import dao.UzDAO;
import dao.WpisDAO;
import entity.Podatnik;
import entity.Sesja;
import entity.Uz;
import entity.Wpis;
import java.io.Serializable;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import language.LocaleChanger;
import msg.Msg;
import view.SesjaView;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "Logowanie")
@ViewScoped
public class Logowanie implements Serializable {
    
    private String uzytk;
    private String haslo;
    private String ipusera;
    private int liczniklogowan;
    @Inject
    UzDAO uzDAO;
    @Inject
    PodatnikDAO podatnikDAO;
    @Inject
    private Sesja sesja;
    @Inject
    private SesjaDAO sesjaDAO;
    @Inject
    private WpisDAO wpisDAO;
    @Inject
    private Wpis wpis;
    @Inject
    private SesjaView sesjaView;
    private WpisView wpisView;
    @Inject
    OstatnidokumentDAO ostatnidokumentDAO;
    @Inject
    private RejestrlogowanDAO rejestrlogowanDAO;
    @ManagedProperty(value ="#{localeChanger}")
    private LocaleChanger localeChanger;
    
    public Logowanie() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }
    
    @PostConstruct
    private void init() {
        ipusera = IPaddress.getIpAddr((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
        liczniklogowan = Liczniklogowan.pobierzIloscLogowan(ipusera, rejestrlogowanDAO);
    }
    
    public String login() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        String message = "";
        String navto = "";
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal;
        try {
            //Login via the Servlet Context
            request.login(uzytk, haslo);
            //Retrieve the Principal
            principal = request.getUserPrincipal();
            //Display a message based on the User role
            if (request.isUserInRole("Administrator")) {
                message = "Username : " + principal.getName() + " You are an Administrator, you can really f**k things up now";
                navto = "Administrator";
            } else if (request.isUserInRole("Manager")) {
                message = "Username : " + principal.getName() + " You are only a Manager, Don't you have a Spreadsheet to be working on??";
                navto = "Manager";
            } else if (request.isUserInRole("Bookkeeper")) {
                message = "Username : " + principal.getName() + " You are only a Bookkeeper, Don't you have a Spreadsheet to be working on??";
                navto = "Bookkeeper";
            } else if (request.isUserInRole("BookkeeperFK")) {
                message = "Username : " + principal.getName() + " You are only a BookkeeperFK, Don't you have a Spreadsheet to be working on??";
                navto = "BookkeeperFK";
            } else if (request.isUserInRole("Multiuser")) {
                message = "Username : " + principal.getName() + " You are only a BookkeeperFK, Don't you have a Spreadsheet to be working on??";
                navto = "Multiuser";
            } else if (request.isUserInRole("Guest")) {
                String nip = uzDAO.findUzByLogin(uzytk).getFirma();
                Podatnik p = podatnikDAO.findPodatnikByNIP(nip);
                if (p == null) {
                    Msg.msg("e", "Firma, której nip został podany przy rejestracji, tj.: "+nip+", nie istnieje w systemie. Nastąpi wylogowanie");
                    return "failure";
                }
                String firma = p.getNazwapelna();
                wpis.setPodatnikWpisu(firma);
                message = "Username : " + principal.getName() + " You're wasting my resources...";
                navto = "Guest";
            } else if (request.isUserInRole("GuestFK")) {
                String nip = uzDAO.findUzByLogin(uzytk).getFirma();
                Podatnik p = podatnikDAO.findPodatnikByNIP(nip);
                if (p == null) {
                    Msg.msg("e", "Firma, której nip został podany przy rejestracji, tj.: "+nip+", nie istnieje w systemie. Nastąpi wylogowanie");
                    return "failure";
                }
                String firma = p.getNazwapelna();
                wpis.setPodatnikWpisu(firma);
                message = "Username : " + principal.getName() + " You're wasting my resources...";
                navto = "GuestFK";
            } else if (request.isUserInRole("GuestFaktura")) {
                String nip = uzDAO.findUzByLogin(uzytk).getFirma();
                Podatnik p = podatnikDAO.findPodatnikByNIP(nip);
                if (p == null) {
                    Msg.msg("e", "Firma, której nip został podany przy rejestracji, tj.: "+nip+", nie istnieje w systemie. Nastąpi wylogowanie");
                    return "failure";
                }
                String firma = p.getNazwapelna();
                wpis.setPodatnikWpisu(firma);
                message = "Username : " + principal.getName() + " You're wasting my resources...";
                navto = "GuestFaktura";
            } else if (request.isUserInRole("Noobie")) {
                message = "Username : " + principal.getName() + " You're wasting my resources...";
                navto = "Noobie";
            }
            if (haslo.equals("haslo")) {
                navto = "nowehaslo";
            }
            Wpis wpisX = wpisDAO.find(uzytk);
            try {
                wpisX.setBiezacasesja(dodajInfoDoSesji());
                wpisDAO.edit(wpisX);
            } catch (Exception e) {
                //to kiedys trzeba usunac :)
            }
            Liczniklogowan.resetujLogowanie(ipusera, rejestrlogowanDAO);
            return navto;
        } catch (ServletException e) {
            Msg.msg("e", "Podałeś nieprawidłowy login lub hasło. Nie możesz rozpocząć pracy z programem");
            Liczniklogowan.odejmijLogowanie(ipusera, rejestrlogowanDAO);
            return "failure";
        }
    }
    
    public void ustawLocale(String uzytk) {
        Uz uz = uzDAO.findUzByLogin(uzytk);
        if (uz != null) {
            switch (uz.getLocale()) {
                case "pl":
                    break;
                case "de":
                    localeChanger.deutschAction();
                    break;
                case "en":
                    localeChanger.englishAction();
                    break;
            }
        }
    }
    
    
    private String dodajInfoDoSesji() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        session.setAttribute("user", principal.getName());
        String nrsesji = session.getId();
        sesja.setNrsesji(nrsesji);
        sesja.setUzytkownik(principal.getName());
        sesja.setIloscdokumentow(0);
        sesja.setIloscmaili(0);
        sesja.setIloscwydrukow(0);
        sesja.setIp(IPaddress.getIpAddr(request));
        Calendar calendar = Calendar.getInstance();
        sesja.setZalogowanie(new Timestamp(calendar.getTime().getTime()));
        try {
            sesjaDAO.dodaj(sesja);
        } catch (Exception e) {
            try {
                sesjaDAO.edit(sesja);
            } catch (Exception e1) {
                
            }
        }
        try {
            Uz wpr = uzDAO.findUzByLogin(uzytk);
            wpr.setBiezacasesja(nrsesji);
            uzDAO.edit(wpr);
        } catch (Exception e) {
            
        }
        return nrsesji;
    }

    
    public void logout() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        try {
            String nrsesji = session.getId();
            sesja = sesjaDAO.find(nrsesji);
            Calendar calendar = Calendar.getInstance();
            sesja.setWylogowanie(new Timestamp(calendar.getTime().getTime()));
            sesjaDAO.edit(sesja);
        } catch (Exception e) {
        }
        if (session != null) {
            session.invalidate();
        }
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/SessionExpired.xhtml?faces-redirect=true");
        System.gc();
    }
    
    public void invalidatesession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
        System.gc();
    }
    
    //po okreslonym czasie bezczynnosci na stronie Access denied przerzuci do strony logowania
    public void autologin() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/login.xhtml?faces-redirect=true");
        System.gc();
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public int getLiczniklogowan() {
        return liczniklogowan;
    }

    public void setLiczniklogowan(int liczniklogowan) {
        this.liczniklogowan = liczniklogowan;
    }

    
    public LocaleChanger getLocaleChanger() {
        return localeChanger;
    }

    public void setLocaleChanger(LocaleChanger localeChanger) {
        this.localeChanger = localeChanger;
    }

     
    
    public String getIpusera() {
        return ipusera;
    }

    public void setIpusera(String ipusera) {
        this.ipusera = ipusera;
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
//</editor-fold>

}
