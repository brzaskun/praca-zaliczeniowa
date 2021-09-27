/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansFK.TabelaNBPBean;
import beansRegon.SzukajDaneBean;
import dao.DokDAO;
import dao.EvewidencjaDAO;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import dao.TabelanbpDAO;
import embeddable.FakturaEbay;
import entity.Dok;
import entity.EVatwpis1;
import entity.Evewidencja;
import entity.Klienci;
import entity.KwotaKolumna1;
import entity.Rodzajedok;
import entityfk.Tabelanbp;
import error.E;
import gus.GUSView;
import java.io.InputStream;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import msg.Msg;import org.joda.time.DateTime;
 import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pdf.PdfDok;
import pdf.PdfEbay;
import waluty.Z;
import xls.ImportCSVEbay;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ImportEbayView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<FakturaEbay> faktury;
    private List<FakturaEbay> fakturyfiltered;
    private List<Dok> dokumenty;
    private List<Klienci> klienci;
    @Inject
    private WpisView wpisView;
    @Inject
    private GUSView gUSView;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    private Map<String,Rodzajedok> rodzajedok;
    @Inject
    private DokDAO dokDAO;
//    @Inject
//    private WalutyDAOfk walutyDAOfk;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    private List<Evewidencja> evewidencje;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private KlienciDAO klDAO;
//    private boolean fakturypolska;
//    private boolean deklaracjaniemiecka;
    private double netto;
    private double vat;
    private double nettopln;
    private double vatpln;
    private Tabelanbp tabeladomyslna;
        
    @PostConstruct
    private void init() { //E.m(this);
        rodzajedok = new HashMap<>();
        rodzajedok.put("SZ", rodzajedokDAO.find("SZ", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt()));
        rodzajedok.put("RACHSP", rodzajedokDAO.find("RACHSP", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt()));
        evewidencje = evewidencjaDAO.znajdzpotransakcji("sprzedaz");
        tabeladomyslna = tabelanbpDAO.findByTabelaPLN();
    }
    
    public void sumuj() {
        List<FakturaEbay> fakt = fakturyfiltered!=null? fakturyfiltered : faktury;
        if (fakt!=null) {
            netto = 0.0;
            vat= 0.0;
            nettopln = 0.0;
            vatpln= 0.0;
            for (FakturaEbay p : fakt) {
                netto += p.getNetto();
                vat += p.getVAT();
            }
            netto = Z.z(netto);
            vat = Z.z(vat);
            nettopln = Z.z(nettopln);
            vatpln = Z.z(vatpln);
            Msg.msg("Podsumowano wartości");
        }
    }
    
    public void importujsprzedaz(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            InputStream is = uploadedFile.getInputstream();
            faktury = ImportCSVEbay.pobierz(is);
            sumuj();
            Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
            if (faktury.size()==0) {
                Msg.msg("e", "Brak dokumentów w pliku json wg zadanych kruteriów");
            }
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
        PrimeFaces.current().executeScript("PF('dialogAjaxCzekaj').hide()");
    }
    
    
    public void drukuj() {
        List<FakturaEbay> pobrane = fakturyfiltered !=null ? fakturyfiltered: faktury;
        PdfEbay.drukuj(pobrane, wpisView, 0);
    }
  
    public void stworzdokumenty() {
        List<FakturaEbay> pobrane = fakturyfiltered !=null ? fakturyfiltered: faktury;
        dokumenty = Collections.synchronizedList(new ArrayList<>());
        klienci = Collections.synchronizedList(new ArrayList<>());
        if (pobrane != null) {
            pobrane.forEach((p) -> {
                Dok dok = generujdok(p);
                dok.setFakturaEbay(p);
                if (dok!=null) {
                    dokumenty.add(dok);
                }
            });
        }
    }

    private Dok generujdok(FakturaEbay wiersz) {
        Dok selDokument = new Dok();
        try {
            HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            selDokument.setWprowadzil(principal.getName());
            String datawystawienia = wiersz.getRechnungsdatum();
            String miesiac = datawystawienia.substring(5, 7);
            String rok = datawystawienia.substring(0, 4);
            selDokument.setPkpirM(miesiac);
            selDokument.setPkpirR(rok);
            selDokument.setVatM(miesiac);
            selDokument.setVatR(rok);
            selDokument.setPodatnik(wpisView.getPodatnikObiekt());
            selDokument.setStatus("bufor");
            selDokument.setUsunpozornie(false);
            selDokument.setDataWyst(datawystawienia);
            selDokument.setDataSprz(datawystawienia);
            selDokument.setKontr(pobierzkontrahenta(wiersz, pobierzNIPkontrahenta(wiersz)));
            selDokument.setRodzajedok(ustalrodzajdok(wiersz));
            selDokument.setNrWlDk(wiersz.getRechnungsnummer());
            String waldok = wiersz.getWaluta();
            Tabelanbp innatabela = pobierztabele(waldok, selDokument.getDataWyst());
            if (waldok.equals("PLN")) {
                selDokument.setTabelanbp(tabeladomyslna);
                selDokument.setWalutadokumentu(tabeladomyslna.getWaluta());
            } else {
                selDokument.setTabelanbp(innatabela);
                selDokument.setWalutadokumentu(innatabela.getWaluta());
            }
            selDokument.setOpis("przychód ze sprzedaży");
            List<KwotaKolumna1> listaX = Collections.synchronizedList(new ArrayList<>());
            KwotaKolumna1 tmpX = new KwotaKolumna1();
            if (waldok.equals("PLN")) {
                tmpX.setNetto(wiersz.getNetto());
                netto += wiersz.getNetto();
                tmpX.setVat(wiersz.getVAT());
                vat += wiersz.getVAT();
                tmpX.setNazwakolumny("przych. sprz");
                tmpX.setDok(selDokument);
                tmpX.setBrutto(Z.z(Z.z(wiersz.getNetto()+wiersz.getVAT())));
            } else {
                tmpX.setNettowaluta(wiersz.getNetto());
                tmpX.setVatwaluta(wiersz.getVAT());
                netto += wiersz.getNetto();
                vat += wiersz.getVAT();
                tmpX.setVat(przeliczpln(wiersz.getVAT(), innatabela));
                tmpX.setNetto(przeliczpln(wiersz.getNetto(), innatabela));
                tmpX.setNazwakolumny("przych. sprz");
                tmpX.setDok(selDokument);
                tmpX.setBrutto(Z.z(Z.z(przeliczpln(wiersz.getVAT(), innatabela)+przeliczpln(wiersz.getNetto(), innatabela))));
            }
            listaX.add(tmpX);
            selDokument.setListakwot1(listaX);
            selDokument.setNetto(Z.z(tmpX.getNetto()));
            selDokument.setBrutto(Z.z(tmpX.getBrutto()));
            selDokument.setRozliczony(true);
            selDokument.setEwidencjaVAT1(dodajewidencje(selDokument, wiersz, innatabela, miesiac, rok));
            if (selDokument.getKontr()!=null && sprawdzCzyNieDuplikat(selDokument)!=null) {
                selDokument = null;
            }
        } catch (Exception e) {
            E.e(e);
        }
        return selDokument;
    }

    private Klienci pobierzkontrahenta(FakturaEbay wiersz, String nrKontrahenta) {
        if (nrKontrahenta.equals("")) {
           Klienci inc = new Klienci();
           inc.setNpelna(wiersz.getNamedesKäufers());
           inc.setNskrocona(wiersz.getNamedesKäufers());
           inc.setAdresincydentalny(pobierzadres(wiersz));
           return inc;
        } else {
            Klienci klientznaleziony = klDAO.findKlientByNipImport(nrKontrahenta);
            if (klientznaleziony==null) {
                klientznaleziony = SzukajDaneBean.znajdzdaneregonAutomat(nrKontrahenta);
                if (klientznaleziony!=null && klientznaleziony.getNip()!=null) {
                    boolean juzjest = false;
                    for (Klienci p : klienci) {
                        if (p.getNip().equals(klientznaleziony.getNip())) {
                            juzjest = true;
                            break;
                        }
                    }
                    if (juzjest==false && !klientznaleziony.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                        klienci.add(klientznaleziony);
                    }
                }
            } else if (klientznaleziony.getNpelna()==null) {
                klientznaleziony.setNpelna("istnieje wielu kontrahentów o tym samym numerze NIP! "+nrKontrahenta);
            }
            return klientznaleziony;
        }
    }
    
    private String pobierzadres(FakturaEbay wiersz) {
        String adres = "";
        adres = adres+wiersz.getPLZ()+" ";
        adres = adres+wiersz.getOrtdesKäufers()+" ";
        adres = adres+wiersz.getKäuferadresse1();
        return adres;
    }
    
    private String pobierzNIPkontrahenta(FakturaEbay wiersz) {
        String nip = "";
        if (wiersz.getNIP()!=null) {
            nip = nip+wiersz.getNIP();
        }
        return nip;
    }
    
    private Rodzajedok ustalrodzajdok(FakturaEbay wiersz) {
        double stawka = wiersz.getStawka();
        Rodzajedok zwrot = rodzajedok.get("SZ");
        if (stawka==19.0) {
            zwrot = rodzajedok.get("RACHSP");
        }
        return zwrot;
    }
    
    private Evewidencja pobierzewidencje(FakturaEbay wiersz, List<Evewidencja> evewidencje) {
        Evewidencja zwrot = null;
        double stawka = wiersz.getStawka();
        for (Evewidencja p : evewidencje) {
            if (p.getStawkavat()==stawka) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }
    public Dok sprawdzCzyNieDuplikat(Dok selD) {
        Dok tmp = null;
        tmp = dokDAO.znajdzDuplikatwtrakcie(selD, wpisView.getPodatnikObiekt(), selD.getRodzajedok().getSkrot());
        return tmp;
    }
    
    public List<EVatwpis1> dodajewidencje(Dok dok, FakturaEbay wiersz, Tabelanbp innatabela, String miesiac, String rok) {
        List<EVatwpis1> ewidencjaTransformowana = null;
        dok.setDokumentProsty(true);
        if (wiersz.getStawka()!=19.00) {
            ewidencjaTransformowana = Collections.synchronizedList(new ArrayList<>());
            EVatwpis1 eVatwpis1 = new EVatwpis1(pobierzewidencje(wiersz,evewidencje), przeliczpln(wiersz.getNetto(), innatabela), przeliczpln(wiersz.getVAT(), innatabela), "sprz.op", miesiac, rok);
            eVatwpis1.setDok(dok);
            ewidencjaTransformowana.add(eVatwpis1);
            dok.setDokumentProsty(false);
        }
        return ewidencjaTransformowana;
    }
  
    public void usun(Dok dok) {
        dokumenty.remove(dok);
        Msg.msg("Usunięto dokument z listy");
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
            List<Dok> innykraj = new ArrayList<>();
            List<Dok> innykrajzero = new ArrayList<>();
            List<Dok> polskaprywatne = new ArrayList<>();
            int i = 0;
            int j = 1;
            int k = 1;
            int l = 1;
            for (Dok p: dokumenty) {
                try {
                    if (p.getKontr().getNip()!=null) {
                        dokDAO.create(p);
                        i++;
                    } else {
                        if (p.getVat()!=0) {
                            p.setNrWpkpir(j++);
                            polskaprywatne.add(p);
                        } else if (p.getVatWalutaCSV()!=0) {
                            p.setNrWpkpir(k++);
                            innykraj.add(p);
                        } else {
                            p.setNrWpkpir(l++);
                            innykrajzero.add(p);
                        }
                        
                    }
                } catch(Exception e){
                }
            }
            if (innykraj.size()>0) {
                try {
                    dokDAO.create(wygenerujdokumentsumaryczny(innykraj, "1"));
                    PdfDok.drukujDok(innykraj, wpisView,0, null, "de");
                    Msg.msg("Zaksięgowano dokument sumaryczny DE");
                } catch (Exception e) {
                    Msg.msg("e", "Błąd podczas księgowania dokumentu sumarycznego DE. Prawdopodobnie duplikat.");
                }
            }
            if (innykrajzero.size()>0) {
                try {
                    dokDAO.create(wygenerujdokumentsumaryczny(innykraj, "2"));
                    PdfDok.drukujDok(innykrajzero, wpisView,0, null, "zero");
                    Msg.msg("Zaksięgowano dokument sumaryczny ze stawką 0%");
                } catch (Exception e) {
                    Msg.msg("e", "Błąd podczas księgowania dokumentu sumarycznego stawka 0%. Prawdopodobnie duplikat.");
                }
            }
            if (polskaprywatne.size()>0) {
                try {
                    dokDAO.create(wygenerujdokumentsumarycznyPL(polskaprywatne));
                    PdfDok.drukujDok(polskaprywatne, wpisView,0, null, "pl");
                    Msg.msg("Zaksięgowano dokument sumaryczny PL");
                } catch (Exception e) {
                    Msg.msg("e", "Błąd podczas księgowania dokumentu sumarycznego PL. Prawdopodobnie duplikat.");
                }
            }
            dokumenty = Collections.synchronizedList(new ArrayList<>());
            if (i>0) {
                Msg.msg("Zaksięgowano zaimportowane dokumenty");
            } else {
                Msg.msg("w", "Nie zaksięgowano dokumentów Polska");
            }
        }
    }
    
    private Dok wygenerujdokumentsumaryczny(List<Dok> lista, String nrkolejny) {
        FakturaEbay sumaryczna = new FakturaEbay();
        sumaryczna.setRechnungsdatum(data.Data.ostatniDzien(wpisView));
        sumaryczna.setNIP(wpisView.getPodatnikObiekt().getNip());
        sumaryczna.setRechnungsnummer(nrkolejny+"/"+wpisView.getMiesiacWpisu()+"/sum");
        sumaryczna.setInklusiveMehrwertsteuersatz("19%");
        double nettopln = 0.0;
        for (Dok p : lista) {
            nettopln += p.getNetto();
        }
        sumaryczna.setGesamtpreis(String.valueOf(nettopln));
        sumaryczna.setVAT_recznie(0.0);
        sumaryczna.setVerpackungundVersand("PLN");
        Dok doksuma = generujdok(sumaryczna);
        doksuma.setOpis("zestawienie sumaryczne dokumenty DE przewalutowane");
        return doksuma;
    }
    
    private Dok wygenerujdokumentsumarycznyPL(List<Dok> lista) {
        FakturaEbay sumaryczna = new FakturaEbay();
        sumaryczna.setRechnungsdatum(data.Data.ostatniDzien(wpisView));
        sumaryczna.setNIP(wpisView.getPodatnikObiekt().getNip());
        sumaryczna.setRechnungsnummer("1/"+wpisView.getMiesiacWpisu()+"/sumPLN");
        sumaryczna.setInklusiveMehrwertsteuersatz("23%");
        double nettopln = 0.0;
        double vatpln = 0.0;
        for (Dok p : lista) {
            nettopln += p.getNetto();
            vatpln += p.getVat();
        }
        sumaryczna.setGesamtpreis(String.valueOf(nettopln));
        sumaryczna.setVAT_recznie(vatpln);
        sumaryczna.setVerpackungundVersand("PLN");
        Dok doksuma = generujdok(sumaryczna);
        doksuma.setOpis("zestawienie sumaryczne dokumenty PL");
        return doksuma;
    }
    
    private Tabelanbp pobierztabele(String waldok, String dataWyst) {
        DateTime dzienposzukiwany = new DateTime(dataWyst);
        return TabelaNBPBean.pobierzTabeleNBP(dzienposzukiwany, tabelanbpDAO, waldok);
    }
    
    private Double przeliczpln(double vat, Tabelanbp innatabela) {
        if (innatabela!=null) {
            return Z.z((vat*innatabela.getKurssredniPrzelicznik()));
        } else {
            return vat;
        }
    }
//    
//    public void drukuj() {
//        try {
//            PdfDok.drukujJPK_FA(dokumenty, wpisView, 1, deklaracjaniemiecka);
//            Msg.msg("Wydrukowano zestawienie zaimportowanych dokumentów");
//        } catch (Exception e) {
//            
//        }
//    }
    
    public List<Dok> getDokumenty() {
        return dokumenty;
    }

    public void setDokumenty(List<Dok> dokumenty) {
        this.dokumenty = dokumenty;
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

//    public boolean isWybierzosobyfizyczne() {
//        return wybierzosobyfizyczne;
//    }
//
//    public void setWybierzosobyfizyczne(boolean wybierzosobyfizyczne) {
//        this.wybierzosobyfizyczne = wybierzosobyfizyczne;
//    }
//
//    public double getNetto() {
//        return netto;
//    }
//
//    public void setNetto(double netto) {
//        this.netto = netto;
//    }
//
//    public double getVat() {
//        return vat;
//    }
//
//    public void setVat(double vat) {
//        this.vat = vat;
//    }
//
//    public double getBrutto() {
//        return Z.z(this.netto+this.vat);
//    }
//
//    public boolean isDeklaracjaniemiecka() {
//        return deklaracjaniemiecka;
//    }
//
//    public void setDeklaracjaniemiecka(boolean deklaracjaniemiecka) {
//        this.deklaracjaniemiecka = deklaracjaniemiecka;
//    }
//
//    

    public List<FakturaEbay> getFaktury() {
        return faktury;
    }

    public void setFaktury(List<FakturaEbay> faktury) {
        this.faktury = faktury;
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

    public List<FakturaEbay> getFakturyfiltered() {
        return fakturyfiltered;
    }

    public void setFakturyfiltered(List<FakturaEbay> fakturyfiltered) {
        this.fakturyfiltered = fakturyfiltered;
    }

    public double getNettopln() {
        return nettopln;
    }

    public void setNettopln(double nettopln) {
        this.nettopln = nettopln;
    }

    public double getVatpln() {
        return vatpln;
    }

    public void setVatpln(double vatpln) {
        this.vatpln = vatpln;
    }

    

    

    

    
    
}
