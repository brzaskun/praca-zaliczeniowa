/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.UzDAO;
import entity.Uz;
import error.E;
import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import mail.Mail;
import msg.Msg;
import org.primefaces.component.panel.Panel;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;
import params.Params;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "UzView")
@ViewScoped
public class UzView implements Serializable {

    //tablica obiektów

    private List<Uz> obiektUZjsf;
    private List<Uz> obiektUZjsfselected;
    @Inject
    private UzDAO uzDAO;
    @Inject
    private Uz selUzytkownik;
    private String confPassword;
    private String login;
    private String firstPassword;
    private String nowymail;
    private String nowehaslo;
    private String nowedrugiehaslo;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private Panel panelrejestracji;

    public UzView() {
        obiektUZjsf = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        List<Uz> c = new ArrayList<>();;
        try {
            c.addAll(uzDAO.findAll());
        } catch (Exception e) { E.e(e); 
        }
        for (Uz p : c) {
            obiektUZjsf.add(p);
        }
        selUzytkownik = wpisView.getWprowadzil();
        nowymail = selUzytkownik.getEmail();
    }

    public void dodaj() {
        selUzytkownik.setUprawnienia("Noobie");
        selUzytkownik.setLogin(selUzytkownik.getLogin().toLowerCase());
        selUzytkownik.setIloscwierszy("12");
        selUzytkownik.setTheme("redmond");
        sformatuj();
        if (validateData()) {
            try {
                haszuj(selUzytkownik.getHaslo());
                uzDAO.dodaj(selUzytkownik);
                String wiadomosc = "Rejestracja udana. Administrator musi teraz nadac Ci uprawnienia. Nastąpi to w ciągu najbliższej godziny. Dopiero wtedy będzie możliwe zalogowanie się.";
                Msg.msg(wiadomosc);
                Mail.nadajMailRejestracjaNowegoUzera(selUzytkownik.getEmail(), selUzytkownik.getLogin());
                panelrejestracji.setRendered(false);
                RequestContext.getCurrentInstance().reset("pole");
                RequestContext.getCurrentInstance().update("pole");
            } catch (Exception e) {
                E.e(e); 
                Msg.msg("e","Uzytkownik o takim loginie już istnieje. Wprowadź inny login.");
            }
        }
    }

    public void edytuj() {
        if (!nowymail.equals(selUzytkownik.getEmail()) || !"".equals(nowehaslo)) {
            if (!"".equals(nowehaslo) && nowehaslo.length()<6) {
                Msg.msg("e", "Minimalna długość hasła to 6 znaków. Krótkie hasło nie może zostać zaakceptowane. Dane nie zostały zmienione");
                return;
            } else {
                try {
                    haszuj(nowehaslo);
                    if (!nowymail.equals(selUzytkownik.getEmail())) {
                        selUzytkownik.setEmail(nowymail);
                    }
                    uzDAO.edit(selUzytkownik);
                    Msg.msg("Udana zmiana hasła/adresu email");
                    Mail.udanazmianaHasla(selUzytkownik.getEmail(), selUzytkownik.getLogin());
                    nowehaslo = null;
                    nowedrugiehaslo = null;
                } catch (Exception e) { 
                    E.e(e); 
                    Msg.msg("e", "Wystąpił błąd. Nastąpiła nieudana zmiana hasła/adresu email.");
                }
            }
        } else {
            Msg.msg("Dane nie zostały zmienione.");
        }
    }
    
    public void edit() {
        try {
            uzDAO.edit(selUzytkownik);
            Msg.msg("Udana edycja danych użytkownika "+selUzytkownik.getLogin());
        } catch (Exception e) {
            E.e(e);
        }
    }

    public void porownajHaslaWTrakcie() {
        if (nowedrugiehaslo.length() >= 6) {
            if (!nowehaslo.equals(nowedrugiehaslo)) {
                RequestContext.getCurrentInstance().execute("$(document.getElementById('form:przycisk')).hide();");
                Msg.msg("e", "Hasła nie są identyczne. Wprowadź je ponownie!");

            } else {
                RequestContext.getCurrentInstance().execute("$(document.getElementById('form:przycisk')).show();");
            }
        }
    }

    public String dodajnowe() {
        try {
            selUzytkownik = uzDAO.findUzByLogin(login);
            selUzytkownik.setHaslo(firstPassword);
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e", "Podany login: '" + login + "' nie istnieje", "formlog1:logowanie");
            login = null;
            return "failure";
        }
        if (validateData()) {
            try {
                haszuj(selUzytkownik.getHaslo());
                uzDAO.edit(selUzytkownik);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Reset hasła udany.", selUzytkownik.getLogin());
                FacesContext.getCurrentInstance().addMessage(null, msg);
                return "failure";

            } catch (Exception e) { System.out.println("Blad " + e.getStackTrace()[0].toString()+" "+e.toString()); 
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wystąpił błąd. Reset hasła nie udany.", e.getStackTrace().toString());
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
        return "failure";
    }

    public void sformatuj() {
        selUzytkownik.setImie(selUzytkownik.getImie().substring(0, 1).toUpperCase() + selUzytkownik.getImie().substring(1).toLowerCase());
        selUzytkownik.setNazw(selUzytkownik.getNazw().substring(0, 1).toUpperCase() + selUzytkownik.getNazw().substring(1).toLowerCase());
    }

    public void haszuj(String password) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        md.update(password.getBytes());
        byte byteData[] = md.digest();
        //convert the byte to hex format method 1
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        selUzytkownik.setHaslo(sb.toString());
    }

    public void destroy(Uz uzytkownik) {
        try {
            uzDAO.destroy(uzytkownik);
            obiektUZjsf.remove(uzytkownik);
            obiektUZjsfselected.remove(uzytkownik);
            Msg.msg("i", "Usunąłem użytkownika " + uzytkownik.getLogin());
        } catch (Exception e) { 
            E.e(e); 
            Msg.msg("e", "Wystąpił błąd. Nie usunąłem użytkownika " + uzytkownik.getLogin());
        }
    }

    public void edit(RowEditEvent ex) {
        try {
            sformatuj();
            uzDAO.edit(selUzytkownik);
            Mail.nadanoUprawniednia(selUzytkownik.getEmail(), selUzytkownik.getLogin(), selUzytkownik.getUprawnienia());
            System.out.println("Nadano uprawnienia "+selUzytkownik.getEmail()+" "+selUzytkownik.getLogin()+" "+selUzytkownik.getUprawnienia());
            FacesMessage msg = new FacesMessage("Nowy uzytkownik edytowany View", selUzytkownik.getLogin());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) { E.e(e); 
            System.out.println("Nie nadano uprawnien "+selUzytkownik.getEmail()+" "+selUzytkownik.getLogin()+" "+selUzytkownik.getUprawnienia());
            FacesMessage msg = new FacesMessage("Uzytkownik nie zedytowany View", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void wybranoUzytkownika() {
        Msg.msg("Wybrano użytkownika: " + selUzytkownik.getLogin());
    }

    private boolean validateData() {
        boolean toReturn = true;
        // check passwordConfirm is same as password
        if (!confPassword.equals(selUzytkownik.getHaslo())) {
            Msg.msg("e","Podane hasła nie pasują. Sprawdź to.","registerForm:passwordConfirm");
            toReturn = false;
        }
        return toReturn;
    }

    public void sprawdzidentycznoschasel() {
        if (!confPassword.equals(selUzytkownik.getHaslo())) {
            String locale = FacesContext.getCurrentInstance().getELContext().getLocale().getLanguage();
            if (locale.equals(new Locale("pl").getLanguage())) {
                Msg.msg("e","Hasła nie pasuja. Sprawdź to.", "registerForm:passwordConfirm");
            } else if (locale.equals(new Locale("de").getLanguage())) {
                Msg.msg("e","Passwörter stimmen nicht überein. Prüfen Sie das.", "registerForm:passwordConfirm");
            } else if (locale.equals(new Locale("en").getLanguage())) {
                Msg.msg("e","Hasła nie pasuja. Sprawdź to.", "registerForm:passwordConfirm");
            }
        } 
    }
    
    public void zmienustawienia() {
        try {
            uzDAO.edit(selUzytkownik);
            Msg.msg("i", "Dane zmienione dla:" + selUzytkownik.getLogin(), "form:mess_add");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Błąd! Dane nie zmienione", "form:mess_add");
        }
    }

    public void sprawdzloginduplikat(AjaxBehaviorEvent e) {
        String login = (String) Params.params("pole:login");
        try {
            Uz user = uzDAO.findUzByLogin(login);
            String locale = FacesContext.getCurrentInstance().getELContext().getLocale().getLanguage();
            if (user == null) {
            } else {
                if (locale.equals(new Locale("pl").getLanguage())) {
                    Msg.msg("e", "Użytkownik o takim loginie już istnienie. Wpisz inny.", "registerForm:passwordConfirm");
                } else if (locale.equals(new Locale("de").getLanguage())) {
                    Msg.msg("e", "Eingegebener Benutzername existiert schon", "registerForm:passwordConfirm");
                } else if (locale.equals(new Locale("en").getLanguage())) {
                    Msg.msg("e", "Użytkownik o takim loginie już istnienie. Wpisz inny.", "registerForm:passwordConfirm");
                }
            }
        } catch (Exception ef) {
            //Msg.msg("e", "Nie można sprawdzić loginu. Wsytąpił błąd!");
        }
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public Panel getPanelrejestracji() {
        return panelrejestracji;
    }

    public void setPanelrejestracji(Panel panelrejestracji) {
        this.panelrejestracji = panelrejestracji;
    }

    public String getNowymail() {
        return nowymail;
    }

    public void setNowymail(String nowymail) {
        this.nowymail = nowymail;
    }

    public String getNowehaslo() {
        return nowehaslo;
    }

    public void setNowehaslo(String nowehaslo) {
        this.nowehaslo = nowehaslo;
    }

    public String getNowedrugiehaslo() {
        return nowedrugiehaslo;
    }

    public void setNowedrugiehaslo(String nowedrugiehaslo) {
        this.nowedrugiehaslo = nowedrugiehaslo;
    }

    //tabela obiektow
    public List<Uz> getObiektUZjsf() {
        return obiektUZjsf;
    }

    public UzDAO getUzDAO() {
        return uzDAO;
    }

    public Uz getSelUzytkownik() {
        return selUzytkownik;
    }

    public void setSelUzytkownik(Uz selUzytkownik) {
        this.selUzytkownik = selUzytkownik;
    }

    public String getConfPassword() {
        return confPassword;
    }

    public void setConfPassword(String confPassword) {
        this.confPassword = confPassword;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstPassword() {
        return firstPassword;
    }

    public void setFirstPassword(String firstPassword) {
        this.firstPassword = firstPassword;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Uz> getObiektUZjsfselected() {
        return obiektUZjsfselected;
    }

    public void setObiektUZjsfselected(List<Uz> obiektUZjsfselected) {
        this.obiektUZjsfselected = obiektUZjsfselected;
    }

    //</editor-fold>
}
