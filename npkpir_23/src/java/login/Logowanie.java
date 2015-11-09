/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package login;

import beansLogowanie.IPaddress;
import beansLogowanie.Liczniklogowan;
import dao.PodatnikDAO;
import dao.RejestrlogowanDAO;
import dao.SesjaDAO;
import dao.UzDAO;
import dao.WpisDAO;
import entity.Podatnik;
import entity.Sesja;
import entity.Uz;
import entity.Wpis;
import error.E;
import java.io.Serializable;
import java.security.Principal;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import language.LocaleChanger;
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@SessionScoped
public class Logowanie implements Serializable {

    private String uzytkownik;
    private String haslo;
    private String ipusera;
    private int liczniklogowan;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private Sesja sesja;
    @Inject
    private SesjaDAO sesjaDAO;
    @Inject
    private WpisDAO wpisDAO;
    @Inject
    private RejestrlogowanDAO rejestrlogowanDAO;
    @ManagedProperty(value = "#{localeChanger}")
    private LocaleChanger localeChanger;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public Logowanie() {
        invalidatesession();
    }

    @PostConstruct
    private void init() {
        ipusera = IPaddress.getIpAddr((HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest());
        liczniklogowan = Liczniklogowan.pobierzIloscLogowan(ipusera, rejestrlogowanDAO);
    }

    public String login() {
        invalidatesession();
        String navto = "";
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        try {
            if (haslo.equals("haslo")) {
                navto = "nowehaslo";
            } else {
                request.login(uzytkownik, haslo);
                request.setAttribute("user", uzytkownik);
                String lo = request.getRemoteUser();
                if (request.isUserInRole("Administrator")) {
                    navto = "Administrator";
                } else if (request.isUserInRole("Manager")) {
                    navto = "Manager";
                } else if (request.isUserInRole("Bookkeeper")) {
                    navto = "Bookkeeper";
                } else if (request.isUserInRole("BookkeeperFK")) {
                    navto = "BookkeeperFK";
                } else if (request.isUserInRole("Multiuser")) {
                    navto = "Multiuser";
                } else if (request.isUserInRole("Guest")) {
                    String nip = uzDAO.findUzByLogin(uzytkownik).getFirma();
                    Podatnik p = podatnikDAO.findPodatnikByNIP(nip);
                    if (p == null) {
                        Msg.msg("e", "Firma, której nip został podany przy rejestracji, tj.: " + nip + ", nie istnieje w systemie. Nastąpi wylogowanie");
                        return "failure";
                    }
                    navto = "Guest";
                } else if (request.isUserInRole("GuestFK")) {
                    String nip = uzDAO.findUzByLogin(uzytkownik).getFirma();
                    Podatnik p = podatnikDAO.findPodatnikByNIP(nip);
                    if (p == null) {
                        Msg.msg("e", "Firma, której nip został podany przy rejestracji, tj.: " + nip + ", nie istnieje w systemie. Nastąpi wylogowanie");
                        return "failure";
                    }
                    navto = "GuestFK";
                } else if (request.isUserInRole("GuestFaktura")) {
                    String nip = uzDAO.findUzByLogin(uzytkownik).getFirma();
                    Podatnik p = podatnikDAO.findPodatnikByNIP(nip);
                    if (p == null) {
                        Msg.msg("e", "Firma, której nip został podany przy rejestracji, tj.: " + nip + ", nie istnieje w systemie. Nastąpi wylogowanie");
                        return "failure";
                    }
                    navto = "GuestFaktura";
                } else if (request.isUserInRole("Noobie")) {
                    navto = "Noobie";
                }

                Wpis wpisX = wpisDAO.find(uzytkownik);
                try {
                    wpisX.setBiezacasesja(dodajInfoDoSesji());
                    wpisDAO.edit(wpisX);
                } catch (Exception e) {
                    //to kiedys trzeba usunac :)
                }
                Liczniklogowan.resetujLogowanie(ipusera, rejestrlogowanDAO);
            }
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
                    localeChanger.polishAction();
                    break;
                case "de":
                    localeChanger.deutschAction();
                    break;
                case "en":
                    localeChanger.englishAction();
                    break;
            }
        } else {
            localeChanger.polishAction();
        }
    }

    private String dodajInfoDoSesji() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal = request.getUserPrincipal();
        session.setAttribute("user", principal.getName());
        session.setAttribute("id", session.getId());
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
            sesjaDAO.edit(sesja);
        } catch (Exception e) {
            E.e(e);
        }
        try {
            Uz wprowadzil = uzDAO.findUzByLogin(uzytkownik);
            wprowadzil.setBiezacasesja(nrsesji);
            uzDAO.edit(wprowadzil);
        } catch (Exception e) {
            E.e(e);
        }
        return nrsesji;
    }

    public void logout() {
        try {
            HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
            Uz wprowadzil = uzDAO.findUzByLogin(wpisView.getWprowadzil().getLogin());
            sesja = sesjaDAO.find(wprowadzil.getBiezacasesja());
            Calendar calendar = Calendar.getInstance();
            sesja.setWylogowanie(new Timestamp(calendar.getTime().getTime()));
            sesjaDAO.edit(sesja);
        } catch (Exception e) {
            E.e(e);
        }
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/SessionExpired.xhtml?faces-redirect=true");
    }

    public final void invalidatesession() {
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    //po okreslonym czasie bezczynnosci na stronie Access denied przerzuci do strony logowania
    public final void autologin() {
        FacesContext.getCurrentInstance().getApplication().getNavigationHandler().handleNavigation(FacesContext.getCurrentInstance(), null, "/login.xhtml?faces-redirect=true");
    }
    
    public void sprawdzciasteczka() {
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Cookie[] cookies = request.getCookies();
            for (Cookie p : cookies) {
                if (p.getName().equals("gabiurms")) {
                    String[] o = p.getValue().split("_");
                    uzytkownik = o[0];
                    haslo = o[1];
                }
            }
            RequestContext.getCurrentInstance().update("formlog1:logowaniepanel");
        } catch (Exception e){
            E.e(e);
        }
    }

    public String savelogin() {
        HttpServletResponse response = (HttpServletResponse) FacesContext.getCurrentInstance().getExternalContext().getResponse();
        StringBuilder p = new StringBuilder();
        p.append(uzytkownik);
        p.append("_");
        p.append(haslo);
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Cookie[] cookies = request.getCookies();
        for (Cookie r : cookies) {
            if (r.getName().equals("gabiurms")) {
                r.setValue(null);
                r.setMaxAge(0);
                r.setPath("/");
                response.addCookie(r);
            }
        }
        Cookie cookie = new Cookie("gabiurms",p.toString());
        cookie.setPath("/");
        cookie.setMaxAge(60*60*8); //1 hour
        response.addCookie(cookie);
        return login();
    }
    //<editor-fold defaultstate="collapsed" desc="comment">
    public int getLiczniklogowan() {
        return liczniklogowan;
    }

    public void setLiczniklogowan(int liczniklogowan) {
        this.liczniklogowan = liczniklogowan;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
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
