/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.ListaEwidencjiVat;
import beansJPK.KlienciJPKBean;
import comparator.Dokcomparator;
import comparator.Dokfkcomparator;
import comparator.Kontocomparator;
import comparator.Rodzajedokcomparator;
import dao.DokDAO;
import dao.DokDAOfk;
import dao.EvewidencjaDAO;
import dao.JPKOznaczeniaDAO;
import dao.KlienciDAO;
import dao.KliencifkDAO;
import dao.KlientJPKDAO;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.RodzajedokDAO;
import dao.TabelanbpDAO;
import dao.UkladBRDAO;
import entity.Dok;
import entity.Evewidencja;
import entity.JPKoznaczenia;
import entity.Klienci;
import entity.KlientJPK;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.Tabelanbp;
import error.E;
import gus.GUSView;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import jpkfa.CurrCodeType;
import msg.Msg;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
 import org.primefaces.model.file.UploadedFile;
import pdf.PdfDok;
import pl.gov.mf.jpk.wzor._2022._02._17._02171.TKodWaluty;
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
    private Rodzajedok sprzedazwybranyRodzaj;
    private List<Evewidencja> evewidencje;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private KlientJPKDAO klientJPKDAO;
    @Inject
    private KlienciDAO klDAO;
    @Inject
    private JPKOznaczeniaDAO jPKOznaczeniaDAO;
    private boolean wybierzosobyfizyczne;
    private boolean wybierzdlajpk;
    private boolean deklaracjaniemiecka;
    private double netto;
    private double vat;
    private Tabelanbp tabeladomyslna;
    @Inject
    private ListaEwidencjiVat listaEwidencjiVat;
    private List<Konto> listaKontKasaBank;
    private Konto kontokasadlajpk;
    private boolean usunaktualnewpisy;
    private String symbol;
    private List<Rodzajedok> rodzajedok;
        
    
    public void init() { //E.m(this);
        sprzedazkraj = rodzajedokDAO.find("SZ", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        sprzedazwdt = rodzajedokDAO.find("WDT", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        evewidencje = evewidencjaDAO.znajdzpotransakcji("sprzedaz");
        tabeladomyslna = tabelanbpDAO.findByTabelaPLN();
        dokumentyfk = new ArrayList<>();
        listaKontKasaBank = kontoDAO.findlistaKontKasaBank(wpisView);
        Collections.sort(listaKontKasaBank, new Kontocomparator());
        usunaktualnewpisy = false;
        rodzajedok = rodzajedokDAO.findListaPodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Collections.sort(rodzajedok, new Rodzajedokcomparator());
    }
    
    public void importujsprzedaz(FileUploadEvent event) {
        try {
            dokumenty = Collections.synchronizedList(new ArrayList<>());
            klienci = Collections.synchronizedList(new ArrayList<>());
            netto = 0.0;
            vat= 0.0;
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            jpkfa.JPK jpk = pobierzJPK(uploadedFile.getInputStream());
            jpkfa2.JPK jpkfa2 = pobierzJPK2(uploadedFile.getInputStream());
            jpkfa3.JPK jpkfa3 = pobierzJPK3(uploadedFile.getInputStream());
            pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK jpkfa4 = pobierzJPK4(uploadedFile.getInputStream());
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
            if (jpkfa4 != null) {
                if (deklaracjaniemiecka) {
                    dokumenty = stworzdokumentyde(jpkfa4);
                } else if (wybierzosobyfizyczne) {
                    dokumenty = stworzdokumentyfiz(jpkfa4);
                } else {
                    dokumenty = stworzdokumenty(jpkfa4);
                }
                Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
                if (dokumenty.size() == 0) {
                    Msg.msg("e", "Brak dokumentów w pliku jpk wg zadanych kruteriów");
                }
            }
            JPKoznaczenia oz = null;
            String stary = null;
            symbol = symbol.toUpperCase();
            if (symbol!=null&&symbol.length()==2) {
                for (Dok d : dokumenty) {
                    if (stary!=null&&stary.equals(symbol)) {
                        d.setOznaczenie1(oz);
                    } else {
                        oz = jPKOznaczeniaDAO.findBySymbol(symbol);
                        stary = symbol;
                        d.setOznaczenie1(oz);
                }
            }
            if (jpkfa2==null&&jpkfa3==null) {
                Msg.msg("e", "Pobrany plik to nie JPK_FA2(2) ani JPK_FA3(1)");
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
            InputStream inputstream = uploadedFile.getInputStream();
            pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK jpkfa4 = pobierzJPK4(inputstream);
            if (jpkfa4 != null) {
                if (wybierzdlajpk) {
                    dokumentyfk = stworzdokumentyjpkfk(jpkfa4);
                } else  if (deklaracjaniemiecka) {
                    dokumentyfk = stworzdokumentydefk(jpkfa4);
                } else if (wybierzosobyfizyczne) {
                    dokumentyfk = stworzdokumentyfizfk(jpkfa4);
                } else {
                    dokumentyfk = stworzdokumentyfk(jpkfa4);
                }
                Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
                if (dokumentyfk.size() == 0) {
                    Msg.msg("e", "Brak dokumentów w pliku jpk wg zadanych kruteriów");
                }
            }
            inputstream = uploadedFile.getInputStream();
            jpkfa3.JPK jpkfa3 = pobierzJPK3(inputstream);
            if (jpkfa3 != null) {
                if (wybierzdlajpk) {
                    dokumentyfk = stworzdokumentyjpkfk(jpkfa3);
                } else  if (deklaracjaniemiecka) {
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
            inputstream = uploadedFile.getInputStream();
            jpkfa2.JPK jpkfa2 = pobierzJPK2(inputstream);
            if (jpkfa2 != null) {
                if (wybierzdlajpk) {
                    dokumentyfk = stworzdokumentyjpkfk(jpkfa2);
                } else  if (deklaracjaniemiecka) {
                    dokumentyfk = stworzdokumentydefk(jpkfa2);
                } else if (wybierzosobyfizyczne) {
                    dokumentyfk = stworzdokumentyfizfk(jpkfa2);
                } else {
                    dokumentyfk = stworzdokumentyfk(jpkfa2);
                }
                Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
                if (dokumentyfk.size() == 0) {
                    Msg.msg("e", "Brak dokumentów w pliku jpk wg zadanych kruteriów");
                }
            }
            
        } catch (Exception ex) {
            System.out.println(E.e(ex));
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
    private jpkfa2.JPK pobierzJPK2(InputStream is) {
       jpkfa2.JPK zwrot = null;
       try {
           JAXBContext context = JAXBContext.newInstance(jpkfa2.JPK.class);
           Unmarshaller unmarshaller = context.createUnmarshaller();
           zwrot = (jpkfa2.JPK) unmarshaller.unmarshal(is);
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
    
    private pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK pobierzJPK4(InputStream is) {
       pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK zwrot = null;
       try {
           JAXBContext context = JAXBContext.newInstance(pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK.class);
           Unmarshaller unmarshaller = context.createUnmarshaller();
           zwrot = (pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK) unmarshaller.unmarshal(is);
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
            Collections.sort(dokumenty, new Dokcomparator());
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
        Collections.sort(dokumenty, new Dokcomparator());
        return dokumenty;
    }
    
    private List<Dok> stworzdokumenty(pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            jpk.getFaktura().forEach((p) -> {
                pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK.Faktura wiersz = (pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK.Faktura) p;
                String waldok = "PLN";
                if (wiersz.getKodWaluty()!=null) {
                    pl.gov.mf.jpk.wzor._2022._02._17._02171.TKodWaluty walutapliku = wiersz.getKodWaluty();
                    waldok = walutapliku.toString();
                } else {
                    String text = wiersz.getP2A()!=null?wiersz.getP2A():"";
                    Msg.msg("e", "Brak waluty dla faktury "+text);
                }
                
                if (wiersz.getP5B() != null && wiersz.getP5B().length()>=10) {
                    Dok dok = pl.gov.mf.jpk.wzor._2022._02._17._02171.Beanjpk.generujdok(p, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAO, sprzedazkraj, false);
                    if (dok!=null) {
                        netto += dok.getNetto();
                        vat += dok.getVat();
                        dokumenty.add(dok);
                    }
                }
            });
        }
        Collections.sort(dokumenty, new Dokcomparator());
        return dokumenty;
    }
    private List<Dokfk> stworzdokumentyfk(jpkfa2.JPK jpk) {
        List<Dokfk> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            int numerkolejny = ImportBean.oblicznumerkolejny(sprzedazkraj.getSkrotNazwyDok(), dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (jpkfa2.JPK.Faktura wiersz : jpk.getFaktura()) {
                pl.gov.crd.xml.schematy.dziedzinowe.mf._2013._05._23.ed.kodycechkrajow.CurrCodeType walutapliku = jpk.getNaglowek().getDomyslnyKodWaluty();
                String waldok = walutapliku.toString();
                if (wiersz.getP5B() != null && wiersz.getP5B().length()>=0) {
                    Dokfk dok = null;
                    jpkfa2.JPK.Faktura faktura = (jpkfa2.JPK.Faktura) wiersz;
                    if (wiersz.getP5A()!=null && !wiersz.getP5A().toString().equals("PL") && faktura.getVat()==0.0) {    
                        dok = jpkfa2.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAOfk, 
                                sprzedazwdt, false, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny, waldok);
                    } else {
                        dok = jpkfa2.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAOfk,
                                sprzedazkraj, false, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny, waldok);
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
         Collections.sort(dokumenty, new Dokfkcomparator());
        return dokumenty;
    }
    
    private List<Dokfk> stworzdokumentyfk(jpkfa3.JPK jpk) {
        List<Dokfk> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            int numerkolejny = ImportBean.oblicznumerkolejny(sprzedazkraj.getSkrotNazwyDok(), dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (jpkfa3.JPK.Faktura wiersz : jpk.getFaktura()) {
                String waldok = "PLN";
                if (wiersz.getKodWaluty()!=null) {
                    jpkfa3.CurrCodeType walutapliku = wiersz.getKodWaluty();
                    waldok = walutapliku.toString();
                } else {
                    String text = wiersz.getP2A()!=null?wiersz.getP2A():"";
                    Msg.msg("e", "Brak waluty dla faktury "+text);
                }
                //wiersz.getP5B() nip odbiorcy faktury dla firm
                if (wiersz.getP5B() != null && wiersz.getP5B().length()>=0) {
                    System.out.println(wiersz.getP2A()!=null?wiersz.getP2A():"brak");
                    Dokfk dok = null;
                    if (wiersz.getP5A()!=null && !wiersz.getP5A().toString().equals("PL")) {    
                        dok = jpkfa3.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, 
                                wpisView.getPodatnikObiekt(), dokDAOfk, sprzedazwdt, false, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny, kontokasadlajpk);
                    } else {
                        dok = jpkfa3.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, 
                                wpisView.getPodatnikObiekt(), dokDAOfk, sprzedazkraj, false, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny, kontokasadlajpk);
                    }
                    if (dok!=null) {
                        netto += dok.getNettoVAT();
                        vat += dok.getVATVAT();
                        dokumenty.add(dok);
                        numerkolejny++;
                    } else {
                        Msg.msg("Błąd podczas generowania dokumentów ");
                    }
                }
            };
        }
        Collections.sort(dokumenty, new Dokfkcomparator());
        return dokumenty;
    }
    
    private List<Dokfk> stworzdokumentyfk(pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK jpk) {
        List<Dokfk> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            int numerkolejny = ImportBean.oblicznumerkolejny(sprzedazkraj.getSkrotNazwyDok(), dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK.Faktura wiersz : jpk.getFaktura()) {
                String waldok = "PLN";
                if (wiersz.getKodWaluty()!=null) {
                    pl.gov.mf.jpk.wzor._2022._02._17._02171.TKodWaluty walutapliku = wiersz.getKodWaluty();
                    waldok = walutapliku.toString();
                } else {
                    String text = wiersz.getP2A()!=null?wiersz.getP2A():"";
                    Msg.msg("e", "Brak waluty dla faktury "+text);
                }
                if (wiersz.getP5B() != null && wiersz.getP5B().length()>=0) {
                    Dokfk dok = null;
                    if (wiersz.getP5A()!=null && !wiersz.getP5A().toString().equals("PL")) {
                        Rodzajedok rodzaj = sprzedazwdt;
                        if (sprzedazwybranyRodzaj!=null) {
                            rodzaj = sprzedazwybranyRodzaj;
                        }
                        dok = pl.gov.mf.jpk.wzor._2022._02._17._02171.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, 
                                wpisView.getPodatnikObiekt(), dokDAOfk, rodzaj, false, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny, kontokasadlajpk);
                    } else {
                        dok = pl.gov.mf.jpk.wzor._2022._02._17._02171.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, 
                                wpisView.getPodatnikObiekt(), dokDAOfk, sprzedazkraj, false, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny, kontokasadlajpk);
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
        Collections.sort(dokumenty, new Dokfkcomparator());
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
        Collections.sort(dokumenty, new Dokcomparator());
        return dokumenty;
    }
    
    private List<Dok> stworzdokumentyfiz(jpkfa3.JPK jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            jpk.getFaktura().forEach((p) -> {
                jpkfa3.JPK.Faktura wiersz = (jpkfa3.JPK.Faktura) p;
                String waldok = "PLN";
                if (wiersz.getKodWaluty()!=null) {
                    jpkfa3.CurrCodeType walutapliku = wiersz.getKodWaluty();
                    waldok = walutapliku.toString();
                } else {
                    String text = wiersz.getP2A()!=null?wiersz.getP2A():"";
                    Msg.msg("e", "Brak waluty dla faktury "+text);
                }
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
        Collections.sort(dokumenty, new Dokcomparator());
        return dokumenty;
    }
    
     private List<Dok> stworzdokumentyfiz(pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            jpk.getFaktura().forEach((p) -> {
                pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK.Faktura wiersz = (pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK.Faktura) p;
                String waldok = "PLN";
                if (wiersz.getKodWaluty()!=null) {
                    pl.gov.mf.jpk.wzor._2022._02._17._02171.TKodWaluty walutapliku = wiersz.getKodWaluty();
                    waldok = walutapliku.toString();
                } else {
                    String text = wiersz.getP2A()!=null?wiersz.getP2A():"";
                    Msg.msg("e", "Brak waluty dla faktury "+text);
                }
                if (wiersz.getP5B() == null || wiersz.getP5B().length()!=10) {
                    Dok dok = pl.gov.mf.jpk.wzor._2022._02._17._02171.Beanjpk.generujdok(p, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAO, sprzedazkraj, false);
                    if (dok!=null) {
                        netto += dok.getNetto();
                        vat += dok.getVat();
                        dokumenty.add(dok);
                    }
                }
            });
        }
        Collections.sort(dokumenty, new Dokcomparator());
        return dokumenty;
    }
    
    private List<Dokfk> stworzdokumentyfizfk(jpkfa2.JPK jpk) {
        List<Dokfk> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            int numerkolejny = ImportBean.oblicznumerkolejny(sprzedazkraj.getSkrotNazwyDok(), dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (jpkfa2.JPK.Faktura wiersz : jpk.getFaktura()) {
                pl.gov.crd.xml.schematy.dziedzinowe.mf._2013._05._23.ed.kodycechkrajow.CurrCodeType walutapliku = jpk.getNaglowek().getDomyslnyKodWaluty();
                String waldok = walutapliku.toString();
                if (wiersz.getP5B() == null || wiersz.getP5B().length()!=10) {
                    Dokfk dok = jpkfa2.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAOfk, sprzedazkraj, false, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny, waldok);
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
    
    private List<Dokfk> stworzdokumentyfizfk(jpkfa3.JPK jpk) {
        List<Dokfk> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            int numerkolejny = ImportBean.oblicznumerkolejny(sprzedazkraj.getSkrotNazwyDok(), dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (jpkfa3.JPK.Faktura wiersz : jpk.getFaktura()) {
                String waldok = "PLN";
                if (wiersz.getKodWaluty()!=null) {
                    jpkfa3.CurrCodeType walutapliku = wiersz.getKodWaluty();
                    waldok = walutapliku.toString();
                } else {
                    String text = wiersz.getP2A()!=null?wiersz.getP2A():"";
                    Msg.msg("e", "Brak waluty dla faktury "+text);
                }
                if (wiersz.getP5B() == null || wiersz.getP5B().length()!=10) {
                    Dokfk dok = jpkfa3.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, 
                            wpisView.getPodatnikObiekt(), dokDAOfk, sprzedazkraj, false, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny, kontokasadlajpk);
                    if (dok!=null) {
                        netto += dok.getNettoVAT();
                        vat += dok.getVATVAT();
                        dokumenty.add(dok);
                    }
                }
            };
        }
        Collections.sort(dokumenty, new Dokfkcomparator());
        return dokumenty;
    }
    private List<Dokfk> stworzdokumentyfizfk(pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK jpk) {
        List<Dokfk> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            int numerkolejny = ImportBean.oblicznumerkolejny(sprzedazkraj.getSkrotNazwyDok(), dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK.Faktura wiersz : jpk.getFaktura()) {
                String waldok = "PLN";
                if (wiersz.getKodWaluty()!=null) {
                    pl.gov.mf.jpk.wzor._2022._02._17._02171.TKodWaluty walutapliku = wiersz.getKodWaluty();
                    waldok = walutapliku.toString();
                } else {
                    String text = wiersz.getP2A()!=null?wiersz.getP2A():"";
                    Msg.msg("e", "Brak waluty dla faktury "+text);
                }
                if (wiersz.getP5B() == null || wiersz.getP5B().length()!=10) {
                    Dokfk dok = pl.gov.mf.jpk.wzor._2022._02._17._02171.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, 
                            wpisView.getPodatnikObiekt(), dokDAOfk, sprzedazkraj, false, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny, kontokasadlajpk);
                    if (dok!=null) {
                        netto += dok.getNettoVAT();
                        vat += dok.getVATVAT();
                        dokumenty.add(dok);
                    }
                }
            };
        }
        Collections.sort(dokumenty, new Dokfkcomparator());
        return dokumenty;
    }
    
    
    private List<Dokfk> stworzdokumentyjpkfk(jpkfa2.JPK jpk) {
        List<Dokfk> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            int numerkolejny = ImportBean.oblicznumerkolejny(sprzedazkraj.getSkrotNazwyDok(), dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (jpkfa2.JPK.Faktura wiersz : jpk.getFaktura()) {
                pl.gov.crd.xml.schematy.dziedzinowe.mf._2013._05._23.ed.kodycechkrajow.CurrCodeType walutapliku = jpk.getNaglowek().getDomyslnyKodWaluty();
                String waldok = walutapliku.toString();
                Dokfk dok = jpkfa2.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzdlajpk, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAOfk, sprzedazkraj, false, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny, jpk.getNaglowek().getDomyslnyKodWaluty().value());
                if (dok!=null) {
                    netto += dok.getNettoVAT();
                    vat += dok.getVATVAT();
                    dokumenty.add(dok);
                }
            };
        }
        return dokumenty;
    }
    
    private List<Dokfk> stworzdokumentyjpkfk(pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK jpk) {
        List<Dokfk> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            int numerkolejny = ImportBean.oblicznumerkolejny(sprzedazkraj.getSkrotNazwyDok(), dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK.Faktura wiersz : jpk.getFaktura()) {
                String waldok = "PLN";
                if (wiersz.getKodWaluty()!=null) {
                    pl.gov.mf.jpk.wzor._2022._02._17._02171.TKodWaluty walutapliku = wiersz.getKodWaluty();
                    waldok = walutapliku.toString();
                } else {
                    String text = wiersz.getP2A()!=null?wiersz.getP2A():"";
                    Msg.msg("e", "Brak waluty dla faktury "+text);
                }
                Dokfk dok = pl.gov.mf.jpk.wzor._2022._02._17._02171.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzdlajpk, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), 
                        dokDAOfk, sprzedazkraj, false, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny, kontokasadlajpk);
                if (dok!=null) {
                    netto += dok.getNettoVAT();
                    vat += dok.getVATVAT();
                    dokumenty.add(dok);
                    numerkolejny++;
                }
            };
        }
        Collections.sort(dokumenty, new Dokfkcomparator());
        return dokumenty;
    }
    
    private List<Dokfk> stworzdokumentyjpkfk(jpkfa3.JPK jpk) {
        List<Dokfk> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            int numerkolejny = ImportBean.oblicznumerkolejny(sprzedazkraj.getSkrotNazwyDok(), dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (jpkfa3.JPK.Faktura wiersz : jpk.getFaktura()) {
                String waldok = "PLN";
                if (wiersz.getKodWaluty()!=null) {
                    jpkfa3.CurrCodeType walutapliku = wiersz.getKodWaluty();
                    waldok = walutapliku.toString();
                } else {
                    String text = wiersz.getP2A()!=null?wiersz.getP2A():"";
                    Msg.msg("e", "Brak waluty dla faktury "+text);
                }
                Dokfk dok = jpkfa3.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzdlajpk, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), 
                        dokDAOfk, sprzedazkraj, false, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny, kontokasadlajpk);
                if (dok!=null) {
                    netto += dok.getNettoVAT();
                    vat += dok.getVATVAT();
                    dokumenty.add(dok);
                    numerkolejny++;
                }
            };
        }
        Collections.sort(dokumenty, new Dokfkcomparator());
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
        Collections.sort(dokumenty, new Dokcomparator());
        return dokumenty;
    }
    
    private List<Dok> stworzdokumentyde(jpkfa2.JPK jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            pl.gov.crd.xml.schematy.dziedzinowe.mf._2013._05._23.ed.kodycechkrajow.CurrCodeType walutapliku = jpk.getNaglowek().getDomyslnyKodWaluty();
            String waldok = walutapliku.toString();
            jpk.getFaktura().forEach((p) -> {
                jpkfa2.JPK.Faktura wiersz = (jpkfa2.JPK.Faktura) p;
                if (wiersz.getP5B() == null || wiersz.getP5B().length()!=10) {
                    Dok dok =  jpkfa2.Beanjpk.generujdok(p, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAO, sprzedazkraj, true);
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
                String waldok = "PLN";
                if (wiersz.getKodWaluty()!=null) {
                    jpkfa3.CurrCodeType walutapliku = wiersz.getKodWaluty();
                    waldok = walutapliku.toString();
                } else {
                    String text = wiersz.getP2A()!=null?wiersz.getP2A():"";
                    Msg.msg("e", "Brak waluty dla faktury "+text);
                }
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
     private List<Dok> stworzdokumentyde(pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            jpk.getFaktura().forEach((p) -> {
                pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK.Faktura wiersz = (pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK.Faktura) p;
                String waldok = "PLN";
                if (wiersz.getKodWaluty()!=null) {
                    TKodWaluty walutapliku = wiersz.getKodWaluty();
                    waldok = walutapliku.toString();
                } else {
                    String text = wiersz.getP2A()!=null?wiersz.getP2A():"";
                    Msg.msg("e", "Brak waluty dla faktury "+text);
                }

                if (wiersz.getP5B() == null || wiersz.getP5B().length()!=10) {
                    Dok dok =  pl.gov.mf.jpk.wzor._2022._02._17._02171.Beanjpk.generujdok(p, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAO, sprzedazkraj, true);
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
    
    private List<Dokfk> stworzdokumentydefk(jpkfa2.JPK jpk) {
        List<Dokfk> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            int numerkolejny = ImportBean.oblicznumerkolejny(sprzedazkraj.getSkrotNazwyDok(), dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (jpkfa2.JPK.Faktura wiersz : jpk.getFaktura()) {
                String walutapliku = jpk.getNaglowek().getDomyslnyKodWaluty().value();
                String waldok = walutapliku.toString();
                if (wiersz.getP5B() == null || wiersz.getP5B().length()!=10) {
                    Dokfk dok =  jpkfa2.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, wpisView.getPodatnikObiekt(), dokDAOfk, sprzedazkraj, true, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny, waldok);
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
    
    
    private List<Dokfk> stworzdokumentydefk(jpkfa3.JPK jpk) {
        List<Dokfk> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            int numerkolejny = ImportBean.oblicznumerkolejny(sprzedazkraj.getSkrotNazwyDok(), dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (jpkfa3.JPK.Faktura wiersz : jpk.getFaktura()) {
                String waldok = "PLN";
                if (wiersz.getKodWaluty()!=null) {
                    jpkfa3.CurrCodeType walutapliku = wiersz.getKodWaluty();
                    waldok = walutapliku.toString();
                } else {
                    String text = wiersz.getP2A()!=null?wiersz.getP2A():"";
                    Msg.msg("e", "Brak waluty dla faktury "+text);
                }
                if (wiersz.getP5B() == null || wiersz.getP5B().length()!=10) {
                    Dokfk dok =  jpkfa3.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, 
                            wpisView.getPodatnikObiekt(), dokDAOfk, sprzedazkraj, true, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny, kontokasadlajpk);
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
    
    private List<Dokfk> stworzdokumentydefk(pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK jpk) {
        List<Dokfk> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            int numerkolejny = ImportBean.oblicznumerkolejny(sprzedazkraj.getSkrotNazwyDok(), dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            for (pl.gov.mf.jpk.wzor._2022._02._17._02171.JPK.Faktura wiersz : jpk.getFaktura()) {
                String waldok = "PLN";
                if (wiersz.getKodWaluty()!=null) {
                    pl.gov.mf.jpk.wzor._2022._02._17._02171.TKodWaluty walutapliku = wiersz.getKodWaluty();
                    waldok = walutapliku.toString();
                } else {
                    String text = wiersz.getP2A()!=null?wiersz.getP2A():"";
                    Msg.msg("e", "Brak waluty dla faktury "+text);
                }
                if (wiersz.getP5B() == null || wiersz.getP5B().length()!=10) {
                    Dokfk dok =  pl.gov.mf.jpk.wzor._2022._02._17._02171.Beanjpk.generujdokfk(wiersz, waldok, evewidencje, tabelanbpDAO, tabeladomyslna, klienci, wybierzosobyfizyczne, deklaracjaniemiecka, klDAO, 
                            wpisView.getPodatnikObiekt(), dokDAOfk, sprzedazkraj, true, listaEwidencjiVat, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO, numerkolejny, kontokasadlajpk);
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
    
    
    public void usundokumentimportjpkfa(Dokfk dok) {
        try {
            dokumentyfk.remove(dok);
            Msg.msg("i", "Dokument importowy usunięty");
            
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie udało się usunąć dokumentu importowego"+E.e(e));
        }
    }
    
    
    public void zaksiegujdlajpk() {
        
        klientJPKDAO.deleteByPodRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        List<KlientJPK> lista = KlienciJPKBean.zaksiegujdok(dokumenty, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        if (symbol!=null&&symbol.length()==2) {
            for (KlientJPK p : lista) {
                p.setOpissprzedaz(symbol);
            }
        }
        klientJPKDAO.createList(lista);
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
    
    public void generujdlajpk() {
         try {
             if (dokumentyfk==null||dokumentyfk.isEmpty()) {
                 Msg.msg("e","Nie wybrano dokumentów");
             } else {
                if (usunaktualnewpisy) {
                    klientJPKDAO.deleteByPodRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                    usunaktualnewpisy = false;
                }
                List<KlientJPK> lista = KlienciJPKBean.zaksiegujdokJPKJPKFA(dokumentyfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
               if (lista.isEmpty()) {
                   Msg.msg("w", "Nie wygenerowano żadnych dokumentów");
               } else {
                   klientJPKDAO.createList(lista);
                   Msg.msg("Wygenerowano dokumenty w liczbie "+lista.size());
               }
             }
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się wygenerować dokumentów");
        }
        PrimeFaces.current().executeScript("PF('dialogAjaxCzekaj').hide()");
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

    public boolean isWybierzdlajpk() {
        return wybierzdlajpk;
    }

    public void setWybierzdlajpk(boolean wybierzdlajpk) {
        this.wybierzdlajpk = wybierzdlajpk;
    }

    public List<Konto> getListaKontKasaBank() {
        return listaKontKasaBank;
    }

    public void setListaKontKasaBank(List<Konto> listaKontKasaBank) {
        this.listaKontKasaBank = listaKontKasaBank;
    }

    public Konto getKontokasadlajpk() {
        return kontokasadlajpk;
    }

    public void setKontokasadlajpk(Konto kontokasadlajpk) {
        this.kontokasadlajpk = kontokasadlajpk;
    }

    public boolean isUsunaktualnewpisy() {
        return usunaktualnewpisy;
    }

    public void setUsunaktualnewpisy(boolean usunaktualnewpisy) {
        this.usunaktualnewpisy = usunaktualnewpisy;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public Rodzajedok getSprzedazwybranyRodzaj() {
        return sprzedazwybranyRodzaj;
    }

    public void setSprzedazwybranyRodzaj(Rodzajedok sprzedazwybranyRodzaj) {
        this.sprzedazwybranyRodzaj = sprzedazwybranyRodzaj;
    }

    public List<Rodzajedok> getRodzajedok() {
        return rodzajedok;
    }

    public void setRodzajedok(List<Rodzajedok> rodzajedok) {
        this.rodzajedok = rodzajedok;
    }

    

    

    
    
}
