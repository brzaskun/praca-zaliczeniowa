/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewadmin;

import dao.AdminmailDAO;
import dao.FakturywystokresoweDAO;
import embeddable.Mce;
import entity.Adminmail;
import entity.Fakturywystokresowe;
import entity.Klienci;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import mail.MailAdmin;
import msg.Msg;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class AdminMailView implements Serializable {

    private String zawartoscmaila;
    private String tematwiadomosci;
    @Inject
    private FakturywystokresoweDAO fakturywystokresoweDAO;
    @Inject
    private AdminmailDAO adminmailDAO;
    private Set<Klienci> klientList;
    private List<Adminmail> wyslanemaile;

    public AdminMailView() {
        klientList = new HashSet<>();
        wyslanemaile = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        try {
            DateTime dt = new DateTime();  // current time
            int month = dt.getMonthOfYear();
            String mc = Mce.getNumberToMiesiac().get(month);
            String rok = String.valueOf(dt.getYear());
            List<Fakturywystokresowe> wykazfaktur = fakturywystokresoweDAO.findOkresoweOstatnie("GRZELCZYK", mc, rok);
            if (month == 1) {
                mc = Mce.getNumberToMiesiac().get(12);
                rok = String.valueOf(dt.getYear()-1);
                wykazfaktur = fakturywystokresoweDAO.findOkresoweOstatnie("GRZELCZYK", mc, rok);
            }
            if (wykazfaktur == null) {
                mc = Mce.getNumberToMiesiac().get(month-1);
                wykazfaktur = fakturywystokresoweDAO.findOkresoweOstatnie("GRZELCZYK", mc, rok);
            }
            for (Fakturywystokresowe p : wykazfaktur) {
                if (p.getDokument().getKontrahent().getEmail() != null && !p.getDokument().getKontrahent().getEmail().contains("brak")) {
                    klientList.add(p.getDokument().getKontrahent());
                }
            }
            wyslanemaile = adminmailDAO.findAll();
        } catch (Exception e) {
            Msg.msg("e", "Brak wystawionych faktur okresowych w bieżącym miesiącu");
        }
    }

    public void zachowajMaila() {
        Msg.msg("i", "Zachowano treść wiadomości mailowej");
    }

    public void wyslijAdminMail() {
        for (Klienci p : klientList) {
        try {
            if (p.getEmail() != null) {
                MailAdmin.mailAdmin(p.getEmail(), tematwiadomosci, zawartoscmaila);
            } else {
                Msg.msg("w", "Brak maila dla " + p.getNpelna());
            }
            Msg.msg("i", "Wyslano wiadomości");
        } catch (Exception e) {
            Msg.msg("e", "Blad nie wyslano wiadomosci! " + e.toString());
        }
        }
        zachowajmail();
    }
    
    public void wyslijAdminMailTest() {
        try {
            MailAdmin.mailAdmin("info@taxman.biz.pl", tematwiadomosci, zawartoscmaila);
        } catch (Exception e) {
            Msg.msg("e", "Blad nie wyslano wiadomosci! " + e.toString());
        }
        Msg.msg("i", "Wyslano wiadomości testowa na adres info@taxman.biz.pl");
    }
    
    
    private void zachowajmail() {
        try {
            Adminmail adminmail = new Adminmail();
            adminmail.setDatawysylki(new Date());
            adminmail.setTytul(tematwiadomosci);
            adminmail.setTresc(zawartoscmaila);
            List<String> email = new ArrayList<>();
            List<String> nazwy = new ArrayList<>();
            for (Klienci p : klientList) {
                if (p.getEmail() != null) {
                    email.add(p.getEmail());
                    nazwy.add(p.getNpelna());
                }
            }
            adminmail.setMaile(email);
            adminmail.setPodatnicy(nazwy);
            adminmailDAO.dodaj(adminmail);
            wyslanemaile.add(adminmail);
            RequestContext.getCurrentInstance().update("akordeon:formmaile:wyslenemaile");
            Msg.msg("i", "Zachowano maila");
        } catch (Exception e) {
            Msg.msg("e", "Blad nie zachowano maila! " + e.toString());
        }
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public String getZawartoscmaila() {
        return zawartoscmaila;
    }

    public void setZawartoscmaila(String zawartoscmaila) {
        this.zawartoscmaila = zawartoscmaila;
    }

    public Set<Klienci> getKlientList() {
        return klientList;
    }

    public void setKlientList(Set<Klienci> klientList) {
        this.klientList = klientList;
    }


    public List<Adminmail> getWyslanemaile() {
        return wyslanemaile;
    }

    public void setWyslanemaile(List<Adminmail> wyslanemaile) {
        this.wyslanemaile = wyslanemaile;
    }

    public String getTematwiadomosci() {
        return tematwiadomosci;
    }

    public void setTematwiadomosci(String tematwiadomosci) {
        this.tematwiadomosci = tematwiadomosci;
    }
    //</editor-fold>    
}
