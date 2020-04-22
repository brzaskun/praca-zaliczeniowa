/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewadmin;

import dao.AdminmailDAO;
import dao.DodatkoweMaileDAO;
import dao.FakturywystokresoweDAO;
import dao.PodatnikDAO;
import dao.PodatnikOpodatkowanieDAO;
import dao.SMTPSettingsDAO;
import embeddable.Mce;
import embeddable.Parametr;
import entity.Adminmail;
import entity.DodatkoweMaile;
import entity.Fakturywystokresowe;
import entity.Klienci;
import entity.Podatnik;
import error.E;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import mail.MailAdmin;
import msg.Msg;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import view.ParametrView;
import view.WpisView; import org.primefaces.PrimeFaces;

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
    private PodatnikOpodatkowanieDAO podatnikOpodatkowanieDDAO;
    @Inject
    private AdminmailDAO adminmailDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    private List<Klienci> klientList;
    private List<Adminmail> wyslanemaile;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private boolean tylkozus;
    private boolean tylkospolki;
    private boolean tylkofizyczne;
    private boolean tylkovat;
    private boolean tylkonievat;
    private boolean tylkododatkowe;
    private boolean tylkoaktywni;
    private byte[] zalacznik;
    private String nazwazalacznik;
    private String jezykmaila;
    @Inject
    private DodatkoweMaileDAO dodatkoweMaileDAO;
    private List<DodatkoweMaile> lista;

    public AdminMailView() {
    }

    @PostConstruct
    public void init() { //E.m(this);
        try {
            klientList = Collections.synchronizedList(new ArrayList<>());
            wyslanemaile = Collections.synchronizedList(new ArrayList<>());
            DateTime dt = new DateTime();  // current time
            int month = dt.getMonthOfYear();
            String mc = Mce.getNumberToMiesiac().get(month);
            String rok = String.valueOf(dt.getYear());
            if (month == 1) {
                mc = Mce.getNumberToMiesiac().get(12);
                rok = String.valueOf(dt.getYear()-1);
            } else {
                mc = Mce.getNumberToMiesiac().get(month-1);
                rok = String.valueOf(dt.getYear());   
            }
            System.out.println("pobieram rok "+rok);
            System.out.println("pobieram miesiac "+mc);
            System.out.println("pobieram month "+month);
            List<Fakturywystokresowe> wykazfaktur = fakturywystokresoweDAO.findOkresoweOstatnie("GRZELCZYK", mc, rok);
            if (wykazfaktur == null || wykazfaktur.size() == 0) {
                String[] nowyrokmc = Mce.zmniejszmiesiac(rok,mc);
                wykazfaktur = fakturywystokresoweDAO.findOkresoweOstatnie("GRZELCZYK", nowyrokmc[1], nowyrokmc[0]);
            }
            Set<Klienci> klientListtemp = new HashSet<>();
            for (Fakturywystokresowe p : wykazfaktur) {
                if (p.getDokument().getKontrahent().getEmail() != null && !p.getDokument().getKontrahent().getEmail().contains("brak") && !p.getDokument().getKontrahent().getEmail().equals("")) {
                    if (jezykmaila==null) {
                        Podatnik pod = podatnikDAO.findPodatnikByNIP(p.getDokument().getKontrahent().getNip());
                        if (pod != null) {
                            Klienci k = p.getDokument().getKontrahent();
                            k.setJezykwysylki(pod.getJezykmaila());
                            klientListtemp.add(k);
                        }
                    } else {
                        Podatnik pod = podatnikDAO.findPodatnikByNIP(p.getDokument().getKontrahent().getNip());
                        if (pod != null && pod.getJezykmaila() != null && pod.getJezykmaila().equals(jezykmaila)) {
                            Klienci k = p.getDokument().getKontrahent();
                            k.setJezykwysylki(pod.getJezykmaila());
                            klientListtemp.add(k);
                            klientListtemp.add(p.getDokument().getKontrahent());
                        }
                    }
                }
            }
            lista = dodatkoweMaileDAO.findAll();
            if (tylkozus) {
                for (Iterator<Klienci> it = klientListtemp.iterator();it.hasNext();) {
                    Klienci p = it.next();
                    Podatnik pod = podatnikDAO.findPodatnikByNIP(p.getNip());
                    if (pod == null || !pod.isZatrudniapracownikow()) {
                        it.remove();
                    }
                }
            }
            if (tylkospolki) {
                for (Iterator<Klienci> it = klientListtemp.iterator();it.hasNext();) {
                    Klienci p = it.next();
                    Podatnik pod = podatnikDAO.findPodatnikByNIP(p.getNip());
                    if (pod == null || pod.getFirmafk() != 1) {
                        it.remove();
                    }
                }
            }
            if (tylkofizyczne) {
                for (Iterator<Klienci> it = klientListtemp.iterator();it.hasNext();) {
                    Klienci p = it.next();
                    Podatnik pod = podatnikDAO.findPodatnikByNIP(p.getNip());
                    if (pod == null || pod.getFirmafk() != 0) {
                        it.remove();
                    }
                }
            }
            if (tylkovat) {
                for (Iterator<Klienci> it = klientListtemp.iterator();it.hasNext();) {
                    Klienci p = it.next();
                    Podatnik pod = podatnikDAO.findPodatnikByNIP(p.getNip());
                    if (pod == null || pod.isTylkodlaZUS() || !sprawdzjakiokresvat(pod)) {
                        it.remove();
                    }
                }
            }
            if (tylkonievat) {
                for (Iterator<Klienci> it = klientListtemp.iterator();it.hasNext();) {
                    Klienci p = it.next();
                    Podatnik pod = podatnikDAO.findPodatnikByNIP(p.getNip());
                    if (pod == null || pod.isTylkodlaZUS() || sprawdzjakiokresvat(pod)) {
                        it.remove();
                    }
                }
            }
            if (tylkoaktywni) {
                for (Iterator<Klienci> it = klientListtemp.iterator();it.hasNext();) {
                    Klienci p = it.next();
                    Podatnik pod = podatnikDAO.findPodatnikByNIP(p.getNip());
                    if (pod == null || !pod.isPodmiotaktywny()) {
                        it.remove();
                    }
                }
            }
            klientList.addAll(klientListtemp);
            wyslanemaile = adminmailDAO.findAll();
        } catch (Exception e) {
            Msg.msg("e", "Brak wystawionych faktur okresowych w bieżącym miesiącu");
        }
    }
    
    private boolean sprawdzjakiokresvat(Podatnik pod) {
        boolean zwrot = false;
        Integer rok = Calendar.getInstance().get(Calendar.YEAR);
        Integer mc = Calendar.getInstance().get(Calendar.MONTH);
        List<Parametr> parametry = pod.getVatokres();
        String czyjestvat = ParametrView.zwrocParametr(parametry, rok, mc);
        if (!czyjestvat.equals("blad")) {
            zwrot = true;
        }
        return zwrot;
    }
        
    public void zachowajMaila() {
        Msg.msg("i", "Zachowano treść wiadomości mailowej");
    }

    public void wyslijAdminMail() {
        int ilosc = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        if (tylkododatkowe) {
            for (DodatkoweMaile p : lista) {
                try {
                    if (p != null) {
                        MailAdmin.mailAdmin(p.getMail(), tematwiadomosci, zawartoscmaila, sMTPSettingsDAO.findSprawaByDef(), zalacznik, nazwazalacznik);
                        ilosc++;
                    } else {
                        Msg.msg("w", "Brak maila/zakaz wysyłki dla " + p);
                    }
                } catch (Exception e) {
                    E.e(e);
                    Msg.msg("e", "Blad nie wyslano wiadomosci dla " + p);
                }
            }
        } else {
            for (Klienci p : klientList) {
                try {
                    if (p.getEmail() != null && p.getJezykwysylki()!=null) {
                        MailAdmin.mailAdmin(p.getEmail(), tematwiadomosci, zawartoscmaila, sMTPSettingsDAO.findSprawaByDef(), zalacznik, nazwazalacznik);
                        sms.SmsSend.wyslijSMSyMail(p, "Wysłano ważne informacje na adres firmy", podatnikDAO);
                        ilosc++;
                    } else {
                        Msg.msg("w", "Brak maila/zakaz wysyłki dla " + p.getNpelna());
                    }
                } catch (Exception e) {
                    E.e(e);
                    Msg.msg("e", "Blad nie wyslano wiadomosci dla " + p.getNpelna());
                }
            }
        }
        String wiadomosc = "Wyslano "+ilosc+" wiadomości";
        Msg.msg("i", wiadomosc);
        zachowajmail();
    }
    
    public void wyslijAdminMailTest() {
        try {
            MailAdmin.mailAdmin("info@taxman.biz.pl", tematwiadomosci, zawartoscmaila, sMTPSettingsDAO.findSprawaByDef(), zalacznik, nazwazalacznik);
            Msg.msg("i", "Wyslano wiadomości testowa na adres info@taxman.biz.pl");
        } catch (Exception e) {
            Msg.msg("e", "Blad nie wyslano wiadomosci! " + e.toString());
        }
        
    }
    
    
    private void zachowajmail() {
        try {
            Adminmail adminmail = new Adminmail();
            adminmail.setDatawysylki(new Date());
            adminmail.setTytul(tematwiadomosci);
            adminmail.setTresc(zawartoscmaila);
            if (zalacznik != null) {
                adminmail.setPlik(zalacznik);
            }
            adminmail.setNazwazalacznika(nazwazalacznik);
            List<String> email = Collections.synchronizedList(new ArrayList<>());
            List<String> nazwy = Collections.synchronizedList(new ArrayList<>());
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
            PrimeFaces.current().ajax().update("akordeon:formmaile:wyslenemaile");
            Msg.msg("i", "Zachowano maila");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Blad nie zachowano maila! " + e.toString());
        }
    }
    
    public void zachowajZalacznik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            if (extension.equals("pdf")) {
                zalacznik = IOUtils.toByteArray(uploadedFile.getInputstream());
                nazwazalacznik = uploadedFile.getFileName();
                Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
            } else {
                Msg.msg("e","Wystąppił błąd. Akceptuję tylko pliki pdf");
            }
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
    }
    
    public void naniesjezykmaila(Klienci k) {
    try {
        Podatnik pod = podatnikDAO.findPodatnikByNIP(k.getNip());
        pod.setJezykmaila(k.getJezykwysylki());
        podatnikDAO.edit(pod);
        Msg.msg("Zmieniono język maila");
    } catch (Exception E) {
        Msg.msg("e","Wystąpił błąd nie zmieniono języka maila");
    }
}

    public static void main(String[] args) {
        try {
            ByteArrayInputStream by = new ByteArrayInputStream(FileUtils.readFileToByteArray(new File("d:/aplik.pdf")));
            byte[] array = new byte[by.available()];
            by.read(array);
            FileUtils.writeByteArrayToFile(new File("D:/plik1.pdf"),  array);
            FileUtils.writeByteArrayToFile(new File("D:/plik1a.pdf"), array);
        } catch (Exception ex) {
            Logger.getLogger(AdminMailView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public String getZawartoscmaila() {
        return zawartoscmaila;
    }

    public void setZawartoscmaila(String zawartoscmaila) {
        this.zawartoscmaila = zawartoscmaila;
    }

    public boolean isTylkododatkowe() {
        return tylkododatkowe;
    }

    public void setTylkododatkowe(boolean tylkododatkowe) {
        this.tylkododatkowe = tylkododatkowe;
    }

    public String getJezykmaila() {
        return jezykmaila;
    }

    public void setJezykmaila(String jezykmaila) {
        this.jezykmaila = jezykmaila;
    }

    public String getNazwazalacznik() {
        return nazwazalacznik;
    }

    public void setNazwazalacznik(String nazwazalacznik) {
        this.nazwazalacznik = nazwazalacznik;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Klienci> getKlientList() {
        return klientList;
    }

    public void setKlientList(List<Klienci> klientList) {
        this.klientList = klientList;
    }


    public List<Adminmail> getWyslanemaile() {
        return wyslanemaile;
    }

    public void setWyslanemaile(List<Adminmail> wyslanemaile) {
        this.wyslanemaile = wyslanemaile;
    }

    public boolean isTylkozus() {
        return tylkozus;
    }

    public void setTylkozus(boolean tylkozus) {
        this.tylkozus = tylkozus;
    }

    public boolean isTylkospolki() {
        return tylkospolki;
    }

    public void setTylkospolki(boolean tylkospolki) {
        this.tylkospolki = tylkospolki;
    }

    public List<DodatkoweMaile> getLista() {
        return lista;
    }

    public void setLista(List<DodatkoweMaile> lista) {
        this.lista = lista;
    }

    public boolean isTylkovat() {
        return tylkovat;
    }

    public void setTylkovat(boolean tylkovat) {
        this.tylkovat = tylkovat;
    }

    public boolean isTylkofizyczne() {
        return tylkofizyczne;
    }

    public void setTylkofizyczne(boolean tylkofizyczne) {
        this.tylkofizyczne = tylkofizyczne;
    }

    public boolean isTylkonievat() {
        return tylkonievat;
    }

    public void setTylkonievat(boolean tylkonievat) {
        this.tylkonievat = tylkonievat;
    }

    public String getTematwiadomosci() {
        return tematwiadomosci;
    }

    public void setTematwiadomosci(String tematwiadomosci) {
        this.tematwiadomosci = tematwiadomosci;
    }
    

    public boolean isTylkoaktywni() {
        return tylkoaktywni;
    }

    public void setTylkoaktywni(boolean tylkoaktywni) {
        this.tylkoaktywni = tylkoaktywni;
    }
        
    //</editor-fold>    

}
