/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.ListaEwidencjiVat;
import beansJPK.KlienciJPKBean;
import dao.DokDAO;
import dao.EvewidencjaDAO;
import dao.KlienciDAO;
import dao.KlientJPKDAO;
import dao.RodzajedokDAO;
import dao.DokDAOfk;
import dao.KliencifkDAO;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.TabelanbpDAO;
import dao.UkladBRDAO;
import entity.Dok;
import entity.Evewidencja;
import entity.Klienci;
import entity.KlientJPK;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Tabelanbp;
import error.E;
import gus.GUSView;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import jpkfa.CurrCodeType;
import msg.Msg;
 import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pdf.PdfDok;
import waluty.Z;
import xls.ImportBean;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ImportFakturyView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Dok> dokumenty;
    private List<Dokfk> dokumentyfk;
    private List<Klienci> klienci;
    @Inject
    private WpisView wpisView;
    @Inject
    private GUSView gUSView;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private KliencifkDAO kliencifkDAO;
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    private Rodzajedok sprzedazkraj;
    private Rodzajedok sprzedazwdt;
    private List<Evewidencja> evewidencje;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private KlientJPKDAO klientJPKDAO;
    @Inject
    private KlienciDAO klDAO;
    private boolean wybierzosobyfizyczne;
    private boolean deklaracjaniemiecka;
    private double netto;
    private double vat;
    private Tabelanbp tabeladomyslna;
    @Inject
    private ListaEwidencjiVat listaEwidencjiVat;
        
    @PostConstruct
    public void init() { //E.m(this);
        sprzedazkraj = rodzajedokDAO.find("SZ", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        sprzedazwdt = rodzajedokDAO.find("WDT", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        evewidencje = evewidencjaDAO.znajdzpotransakcji("sprzedaz");
        tabeladomyslna = tabelanbpDAO.findByTabelaPLN();
    }
    
    public void importujsprzedaz(FileUploadEvent event) {
        try {
            dokumenty = Collections.synchronizedList(new ArrayList<>());
            klienci = Collections.synchronizedList(new ArrayList<>());
            netto = 0.0;
            vat= 0.0;
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            jpkfa.JPK jpk = pobierzJPK(uploadedFile.getInputstream());
            jpkfa3.JPK jpkfa3 = pobierzJPK3(uploadedFile.getInputstream());
            if (jpk != null) {
                if (deklaracjaniemiecka) {
                    dokumenty = stworzdokumentyde(jpk);
                } else if (wybierzosobyfizyczne) {
                    dokumenty = stworzdokumentyfiz(jpk);
                } else {
                    dokumenty = stworzdokumenty(jpk);
                }
                Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
                if (dokumenty.size() == 0) {
                    Msg.msg("e", "Brak dokumentów w pliku jpk wg zadanych kruteriów");
                }
            }
            if (jpkfa3 != null) {
                if (deklaracjaniemiecka) {
                    dokumenty = stworzdokumentyde(jpkfa3);
                } else if (wybierzosobyfizyczne) {
                    dokumenty = stworzdokumentyfiz(jpkfa3);
                } else {
                    dokumenty = stworzdokumenty(jpkfa3);
                }
                Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
                if (dokumenty.size() == 0) {
                    Msg.msg("e", "Brak dokumentów w pliku jpk wg zadanych kruteriów");
                }
            }
            
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
        PrimeFaces.current().executeScript("PF('dialogAjaxCzekaj').hide()");
    }
    
    public void importujsprzedazfk(FileUploadEvent event) {
        try {
            dokumentyfk = Collections.synchronizedList(new ArrayList<>());
            klienci = Collections.synchronizedList(new ArrayList<>());
            netto = 0.0;
            vat= 0.0;
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            jpkfa3.JPK jpkfa3 = pobierzJPK3(uploadedFile.getInputstream());
            if (jpkfa3 != null) {
                if (deklaracjaniemiecka) {
                    dokumentyfk = stworzdokumentydefk(jpkfa3);
                } else if (wybierzosobyfizyczne) {
                    dokumentyfk = stworzdokumentyfizfk(jpkfa3);
                } else {
                    dokumentyfk = stworzdokumentyfk(jpkfa3);
                }
                Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
                if (dokumentyfk.size() == 0) {
                    Msg.msg("e", "Brak dokumentów w pliku jpk wg zadanych kruteriów");
                }
            }
            
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
    }
    
    private jpkfa.JPK pobierzJPK(InputStream is) {
        jpkfa.JPK zwrot = null;
       try {
           JAXBContext context = JAXBContext.newInstance(jpkfa.JPK.class);
           Unmarshaller unmarshaller = context.createUnmarshaller();
           zwrot = (jpkfa.JPK) unmarshaller.unmarshal(is);
       } catch (Exception ex) {
           error.E.s("");
       }
       return zwrot;
    }
    private jpkfa3.JPK pobierzJPK3(InputStream is) {
       jpkfa3.JPK zwrot = null;
       try {
           JAXBContext context = JAXBContext.newInstance(jpkfa3.JPK.class);
           Unmarshaller unmarshaller = context.createUnmarshaller();
           zwrot = (jpkfa3.JPK) unmarshaller.unmarshal(is);
       } catch (Exception ex) {
           error.E.s("");
       }
       return zwrot;
    }
     private List<Dok> stworzdokumenty(jpkfa.JPK jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            CurrCodeType walutapliku = jpk.getNaglowek().getDomyslnyKodWaluty();
            String waldok = walutapliku.toString();
            jpk.getFaktura().forEach((p) -> {
                jpkfa.JPK.Faktura wiersz = (jpkfa.JPK.Faktura) p;
                if (wiersz.getP5B() != null && wiersz.getP5B().length()>=10) {
                    Dok dok = jpkfa.Beanjpk.generujdok(p, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAO, sprzedazkraj, false);
                    if (dok!=null) {
                        dokumenty.add(dok);
                    }
                }
            });
        }
        return dokumenty;
    }
    private List<Dok> stworzdokumenty(jpkfa3.JPK jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            jpk.getFaktura().forEach((p) -> {
                jpkfa3.JPK.Faktura wiersz = (jpkfa3.JPK.Faktura) p;
                jpkfa3.CurrCodeType walutapliku = wiersz.getKodWaluty();
                String waldok = walutapliku.toString();
                if (wiersz.getP5B() != null && wiersz.getP5B().length()>=10) {
                    Dok dok = jpkfa3.Beanjpk.generujdok(p, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAO, sprzedazkraj, false);
                    if (dok!=null) {
                        netto += dok.getNetto();
                        vat += dok.getVat();
                        dokumenty.add(dok);
                    }
                }
            });
        }
        return dokumenty;
    }
    
    private List<Dokfk> stworzdokumentyfk(jpkfa3.JPK jpk) {
        List<Dokfk> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            int numerkolejny = ImportBean.oblicznumerkolejny(sprzedazkraj.getSkrotNazwyDok(), dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (jpkfa3.JPK.Faktura wiersz : jpk.getFaktura()) {
                jpkfa3.CurrCodeType walutapliku = wiersz.getKodWaluty();
                String waldok = walutapliku.toString();
                if (wiersz.getP5B() != null && wiersz.getP5B().length()>=0) {
                    Dokfk dok = null;
                    if (wiersz.getP5A()!=null && !wiersz.getP5A().toString().equals("PL")) {    
                        dok = jpkfa3.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAOfk, sprzedazwdt, false, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny);
                    } else {
                        dok = jpkfa3.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAOfk, sprzedazkraj, false, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny);
                    }
                    if (dok!=null) {
                        netto += dok.getNettoVAT();
                        vat += dok.getVATVAT();
                        dokumenty.add(dok);
                        numerkolejny++;
                    }
                }
            };
        }
        return dokumenty;
    }
    
    private List<Dok> stworzdokumentyfiz(jpkfa.JPK jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            CurrCodeType walutapliku = jpk.getNaglowek().getDomyslnyKodWaluty();
            String waldok = walutapliku.toString();
            jpk.getFaktura().forEach((p) -> {
                jpkfa.JPK.Faktura wiersz = (jpkfa.JPK.Faktura) p;
                if (wiersz.getP5B() == null || wiersz.getP5B().length()!=10) {
                    Dok dok = jpkfa.Beanjpk.generujdok(p, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAO, sprzedazkraj, false);
                    if (dok!=null) {
                        netto += dok.getNetto();
                        vat += dok.getVat();
                        dokumenty.add(dok);
                    }
                }
            });
        }
        return dokumenty;
    }
    
    private List<Dok> stworzdokumentyfiz(jpkfa3.JPK jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            jpk.getFaktura().forEach((p) -> {
                jpkfa3.JPK.Faktura wiersz = (jpkfa3.JPK.Faktura) p;
                jpkfa3.CurrCodeType walutapliku = wiersz.getKodWaluty();
                String waldok = walutapliku.toString();
                if (wiersz.getP5B() == null || wiersz.getP5B().length()!=10) {
                    Dok dok = jpkfa3.Beanjpk.generujdok(p, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAO, sprzedazkraj, false);
                    if (dok!=null) {
                        netto += dok.getNetto();
                        vat += dok.getVat();
                        dokumenty.add(dok);
                    }
                }
            });
        }
        return dokumenty;
    }
    
    private List<Dokfk> stworzdokumentyfizfk(jpkfa3.JPK jpk) {
        List<Dokfk> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            int numerkolejny = ImportBean.oblicznumerkolejny(sprzedazkraj.getSkrotNazwyDok(), dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (jpkfa3.JPK.Faktura wiersz : jpk.getFaktura()) {
                jpkfa3.CurrCodeType walutapliku = wiersz.getKodWaluty();
                String waldok = walutapliku.toString();
                if (wiersz.getP5B() == null || wiersz.getP5B().length()!=10) {
                    Dokfk dok = jpkfa3.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAOfk, sprzedazkraj, false, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny);
                    if (dok!=null) {
                        netto += dok.getNettoVAT();
                        vat += dok.getVATVAT();
                        dokumenty.add(dok);
                    }
                }
            };
        }
        return dokumenty;
    }
    
    private List<Dok> stworzdokumentyde(jpkfa.JPK jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            CurrCodeType walutapliku = jpk.getNaglowek().getDomyslnyKodWaluty();
            String waldok = walutapliku.toString();
            jpk.getFaktura().forEach((p) -> {
                jpkfa.JPK.Faktura wiersz = (jpkfa.JPK.Faktura) p;
                if (wiersz.getP5B() == null || wiersz.getP5B().length()!=10) {
                    Dok dok =  jpkfa.Beanjpk.generujdok(p, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAO, sprzedazkraj, true);
                    if (dok!=null) {
                        netto += dok.getNetto();
                        vat += dok.getVat();
                        dokumenty.add(dok);
                    }
                }
            });
        }
        return dokumenty;
    }
    
    private List<Dok> stworzdokumentyde(jpkfa3.JPK jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            jpk.getFaktura().forEach((p) -> {
                jpkfa3.JPK.Faktura wiersz = (jpkfa3.JPK.Faktura) p;
                jpkfa3.CurrCodeType walutapliku = wiersz.getKodWaluty();
                String waldok = walutapliku.toString();
                if (wiersz.getP5B() == null || wiersz.getP5B().length()!=10) {
                    Dok dok =  jpkfa3.Beanjpk.generujdok(p, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAO, sprzedazkraj, true);
                    if (dok!=null) {
                        netto += dok.getNetto();
                        vat += dok.getVat();
                        dokumenty.add(dok);
                    }
                }
            });
        }
        return dokumenty;
    }
    
    private List<Dokfk> stworzdokumentydefk(jpkfa3.JPK jpk) {
        List<Dokfk> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            int numerkolejny = ImportBean.oblicznumerkolejny(sprzedazkraj.getSkrotNazwyDok(), dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (jpkfa3.JPK.Faktura wiersz : jpk.getFaktura()) {
                jpkfa3.CurrCodeType walutapliku = wiersz.getKodWaluty();
                String waldok = walutapliku.toString();
                if (wiersz.getP5B() == null || wiersz.getP5B().length()!=10) {
                    Dokfk dok =  jpkfa3.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAOfk, sprzedazkraj, true, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny);
                    if (dok!=null) {
                        netto += dok.getNettoVAT();
                        vat += dok.getVATVAT();
                        dokumenty.add(dok);
                    }
                }
            };
        }
        return dokumenty;
    }
    
   
    
    
    
    public void usun(Dok dok) {
        dokumenty.remove(dok);
        Msg.msg("Usunięto dokument z listy");
    }
    
    public void zaksiegujdlajpk() {
        klientJPKDAO.deleteByPodRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        List<KlientJPK> lista = KlienciJPKBean.zaksiegujdok(dokumenty, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        klientJPKDAO.create(lista);
        Msg.msg("Zaksięgowano dokumenty dla JPK");
    }
    
    public void zaksieguj() {
        if (klienci!=null && klienci.size()>0) {
            for (Klienci p: klienci) {
                try {
                    if (p.getNip()!=null) {
                        klDAO.create(p);
                    }
                } catch(Exception e){
                }
            }
            klienci = Collections.synchronizedList(new ArrayList<>());
            Msg.msg("Dodano nowych klientw z importowanych dokumentów");
        }
        if (dokumenty!=null && dokumenty.size()>0) {
            for (Dok p: dokumenty) {
                try {
                    if (p.getKontr().getNip()!=null) {
                        dokDAO.create(p);
                    }
                } catch(Exception e){
                }
            }
            dokumenty = Collections.synchronizedList(new ArrayList<>());
            Msg.msg("Zaksiowano zaimportowane dokumenty");
        }
    }
    
    public void zaksiegujfk() {
        if (klienci!=null && klienci.size()>0) {
            for (Klienci p: klienci) {
                try {
                    if (p.getNip()!=null) {
                        klDAO.create(p);
                    }
                } catch(Exception e){
                }
            }
            klienci = Collections.synchronizedList(new ArrayList<>());
            Msg.msg("Dodano nowych klientw z importowanych dokumentów");
        }
        if (dokumentyfk!=null && dokumentyfk.size()>0) {
            for (Dokfk p: dokumentyfk) {
                try {
                    if (p.getKontr().getNip()!=null) {
                        dokDAOfk.create(p);
                    }
                } catch(Exception e){
                }
            }
            dokumentyfk = Collections.synchronizedList(new ArrayList<>());
            Msg.msg("Zaksiowano zaimportowane dokumenty");
        }
    }
    
    
    
    public void drukuj() {
        try {
            PdfDok.drukujJPK_FA(dokumenty, wpisView, 1, deklaracjaniemiecka);
            Msg.msg("Wydrukowano zestawienie zaimportowanych dokumentów");
        } catch (Exception e) {
            
        }
    }
    
    public List<Dok> getDokumenty() {
        return dokumenty;
    }

    public void setDokumenty(List<Dok> dokumenty) {
        this.dokumenty = dokumenty;
    }

    public List<Dokfk> getDokumentyfk() {
        return dokumentyfk;
    }

    public void setDokumentyfk(List<Dokfk> dokumentyfk) {
        this.dokumentyfk = dokumentyfk;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public GUSView getgUSView() {
        return gUSView;
    }

    public void setgUSView(GUSView gUSView) {
        this.gUSView = gUSView;
    }

    public boolean isWybierzosobyfizyczne() {
        return wybierzosobyfizyczne;
    }

    public void setWybierzosobyfizyczne(boolean wybierzosobyfizyczne) {
        this.wybierzosobyfizyczne = wybierzosobyfizyczne;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getBrutto() {
        return Z.z(this.netto+this.vat);
    }

    public boolean isDeklaracjaniemiecka() {
        return deklaracjaniemiecka;
    }

    public void setDeklaracjaniemiecka(boolean deklaracjaniemiecka) {
        this.deklaracjaniemiecka = deklaracjaniemiecka;
    }

    

    

    
    
}
