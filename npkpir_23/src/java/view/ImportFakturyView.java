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
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import deklaracje.jpkfa.CurrCodeType;
import deklaracje.jpkfa.JPK;
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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import msg.Msg; import org.primefaces.PrimeFaces;
import org.joda.time.DateTime;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pdf.PdfDok;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class ImportFakturyView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Dok> dokumenty;
    private List<Klienci> klienci;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{gUSView}")
    private GUSView gUSView;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    private Rodzajedok rodzajedok;
    private List<Evewidencja> evewidencje;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private KlienciDAO klDAO;
    private boolean wybierzosobyfizyczne;
    private boolean deklaracjaniemiecka;
    private double netto;
    private double vat;
    private Tabelanbp tabeladomyslna;
        
    @PostConstruct
    private void init() { //E.m(this);
        rodzajedok = rodzajedokDAO.find("SZ", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
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
            InputStream is = uploadedFile.getInputstream();
            deklaracje.jpkfa.JPK jpk = pobierzJPK(is);
            if (deklaracjaniemiecka) {
                dokumenty = stworzdokumentyde(jpk);
            } else if (wybierzosobyfizyczne) {
                dokumenty = stworzdokumentyfiz(jpk);
            } else {
                dokumenty = stworzdokumenty(jpk);
            }
            Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
            if (dokumenty.size()==0) {
                Msg.msg("e", "Brak dokumentów w pliku jpk wg zadanych kruteriów");
            }
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
        PrimeFaces.current().executeScript("PF('dialogAjaxCzekaj').hide()");
    }
    
    private deklaracje.jpkfa.JPK pobierzJPK(InputStream is) {
       deklaracje.jpkfa.JPK zwrot = null;
       try {
           JAXBContext context = JAXBContext.newInstance(deklaracje.jpkfa.JPK.class);
           Unmarshaller unmarshaller = context.createUnmarshaller();
           zwrot = (deklaracje.jpkfa.JPK) unmarshaller.unmarshal(is);
       } catch (Exception ex) {}
       return zwrot;
    }
    
    private List<Dok> stworzdokumenty(deklaracje.jpkfa.JPK jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        CurrCodeType walutapliku = jpk.getNaglowek().getDomyslnyKodWaluty();
        String waldok = walutapliku.toString();
        if (jpk != null) {
            jpk.getFaktura().forEach((p) -> {
                deklaracje.jpkfa.JPK.Faktura wiersz = (deklaracje.jpkfa.JPK.Faktura) p;
                if (wiersz.getP5B() != null && wiersz.getP5B().length()>=10) {
                    Dok dok = generujdok(p, waldok);
                    if (dok!=null) {
                        dokumenty.add(dok);
                    }
                }
            });
        }
        return dokumenty;
    }
    
    private List<Dok> stworzdokumentyfiz(deklaracje.jpkfa.JPK jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        CurrCodeType walutapliku = jpk.getNaglowek().getDomyslnyKodWaluty();
        String waldok = walutapliku.toString();
        if (jpk != null) {
            jpk.getFaktura().forEach((p) -> {
                deklaracje.jpkfa.JPK.Faktura wiersz = (deklaracje.jpkfa.JPK.Faktura) p;
                if (wiersz.getP5B() == null || wiersz.getP5B().length()!=10) {
                    Dok dok = generujdok(p, waldok);
                    if (dok!=null) {
                        dokumenty.add(dok);
                    }
                }
            });
        }
        return dokumenty;
    }
    
    private List<Dok> stworzdokumentyde(deklaracje.jpkfa.JPK jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        CurrCodeType walutapliku = jpk.getNaglowek().getDomyslnyKodWaluty();
        String waldok = walutapliku.toString();
        if (jpk != null) {
            jpk.getFaktura().forEach((p) -> {
                deklaracje.jpkfa.JPK.Faktura wiersz = (deklaracje.jpkfa.JPK.Faktura) p;
                if (wiersz.getP5B() == null || wiersz.getP5B().length()!=10) {
                    Dok dok = generujdokde(p, waldok);
                    if (dok!=null) {
                        dokumenty.add(dok);
                    }
                }
            });
        }
        return dokumenty;
    }
    
    private Dok generujdok(Object p, String waldok) {
        deklaracje.jpkfa.JPK.Faktura wiersz = (deklaracje.jpkfa.JPK.Faktura) p;
        Dok selDokument = new Dok();
        try {
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            selDokument.setWprowadzil(principal.getName());
            String datawystawienia = wiersz.getP1().toString();
            String miesiac = datawystawienia.substring(5, 7);
            String rok = datawystawienia.substring(0, 4);
            selDokument.setPkpirM(miesiac);
            selDokument.setPkpirR(rok);
            selDokument.setVatM(miesiac);
            selDokument.setVatR(rok);
            selDokument.setPodatnik(wpisView.getPodatnikObiekt());
            selDokument.setStatus("bufor");
            selDokument.setUsunpozornie(false);
            selDokument.setDataWyst(wiersz.getP1().toString());
            selDokument.setDataSprz(wiersz.getP6().toString());
            selDokument.setKontr(pobierzkontrahenta(wiersz, pobierzNIPkontrahenta(wiersz)));
            selDokument.setRodzajedok(rodzajedok);
            selDokument.setNrWlDk(wiersz.getP2A());
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
                tmpX.setVat(wiersz.getVat());
                vat += wiersz.getVat();
                tmpX.setNazwakolumny("przych. sprz");
                tmpX.setDok(selDokument);
                tmpX.setBrutto(Z.z(Z.z(wiersz.getNetto()+wiersz.getVat())));
            } else {
                tmpX.setNettowaluta(wiersz.getNetto());
                tmpX.setVatwaluta(wiersz.getVat());
                netto += wiersz.getNetto();
                vat += wiersz.getVat();
                tmpX.setVat(przeliczpln(wiersz.getVat(), innatabela));
                tmpX.setNetto(przeliczpln(wiersz.getNetto(), innatabela));
                tmpX.setNazwakolumny("przych. sprz");
                tmpX.setDok(selDokument);
                tmpX.setBrutto(Z.z(Z.z(przeliczpln(wiersz.getVat(), innatabela)+przeliczpln(wiersz.getNetto(), innatabela))));
            }
            listaX.add(tmpX);
            selDokument.setListakwot1(listaX);
            selDokument.setNetto(Z.z(tmpX.getNetto()));
            selDokument.setBrutto(Z.z(tmpX.getBrutto()));
            selDokument.setRozliczony(true);
            List<EVatwpis1> ewidencjaTransformowana = Collections.synchronizedList(new ArrayList<>());
            EVatwpis1 eVatwpis1 = new EVatwpis1(pobierzewidencje(wiersz,evewidencje), przeliczpln(wiersz.getNetto(), innatabela), przeliczpln(wiersz.getVat(), innatabela), "sprz.op", miesiac, rok);
            eVatwpis1.setDok(selDokument);
            ewidencjaTransformowana.add(eVatwpis1);
            selDokument.setEwidencjaVAT1(ewidencjaTransformowana);
            if (selDokument.getKontr()!=null && sprawdzCzyNieDuplikat(selDokument)!=null) {
                selDokument = null;
            }
        } catch (Exception e) {
            E.e(e);
        }
        return selDokument;
    }
    
    private Dok generujdokde(Object p, String waldok) {
        deklaracje.jpkfa.JPK.Faktura faktura = (deklaracje.jpkfa.JPK.Faktura) p;
        Dok selDokument = new Dok();
        try {
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            selDokument.setWprowadzil(principal.getName());
            String datawystawienia = faktura.getP1().toString();
            String miesiac = datawystawienia.substring(5, 7);
            String rok = datawystawienia.substring(0, 4);
            selDokument.setPkpirM(miesiac);
            selDokument.setPkpirR(rok);
            selDokument.setVatM(miesiac);
            selDokument.setVatR(rok);
            selDokument.setPodatnik(wpisView.getPodatnikObiekt());
            selDokument.setStatus("bufor");
            selDokument.setUsunpozornie(false);
            selDokument.setDataWyst(faktura.getP1().toString());
            selDokument.setDataSprz(faktura.getP6().toString());
            selDokument.setKontr(pobierzkontrahenta(faktura, pobierzNIPkontrahenta(faktura)));
            selDokument.setRodzajedok(rodzajedok);
            selDokument.setNrWlDk(faktura.getP2A());
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
            tmpX.setNettowaluta(faktura.getNettoDE());
            tmpX.setVatwaluta(faktura.getVatDE());
            netto += faktura.getNettoDE();
            vat += faktura.getVatDE();
            tmpX.setVat(faktura.getVatDE());
            tmpX.setNetto(faktura.getNettoDE());
            tmpX.setNazwakolumny("przych. sprz");
            tmpX.setDok(selDokument);
            tmpX.setBrutto(Z.z(Z.z(faktura.getVatDE()+faktura.getNettoDE())));
            listaX.add(tmpX);
            selDokument.setListakwot1(listaX);
            selDokument.setNetto(Z.z(tmpX.getNetto()));
            selDokument.setBrutto(Z.z(tmpX.getBrutto()));
            selDokument.setRozliczony(true);
            List<EVatwpis1> ewidencjaTransformowana = Collections.synchronizedList(new ArrayList<>());
            EVatwpis1 eVatwpis1 = new EVatwpis1(pobierzewidencje(faktura,evewidencje), przeliczpln(faktura.getNetto(), innatabela), przeliczpln(faktura.getVat(), innatabela), "sprz.op", miesiac, rok);
            eVatwpis1.setDok(selDokument);
            ewidencjaTransformowana.add(eVatwpis1);
            selDokument.setEwidencjaVAT1(ewidencjaTransformowana);
            if (selDokument.getKontr()!=null && sprawdzCzyNieDuplikat(selDokument)!=null) {
                selDokument = null;
            }
        } catch (Exception e) {
            E.e(e);
        }
        return selDokument;
    }
    
    public Dok sprawdzCzyNieDuplikat(Dok selD) {
        if (selD.getKontr().getNpelna().equals("OPTEGRA POLSKA sp. z o.o.")) {
            System.out.println("");
        }
        Dok tmp = null;
        tmp = dokDAO.znajdzDuplikatwtrakcie(selD, wpisView.getPodatnikObiekt(), selD.getRodzajedok().getSkrot());
        return tmp;
    }
    
    private Klienci pobierzkontrahenta(deklaracje.jpkfa.JPK.Faktura wiersz, String nrKontrahenta) {
        if (wybierzosobyfizyczne||deklaracjaniemiecka) {
           Klienci inc = new Klienci();
           inc.setNpelna(wiersz.getP3A()!=null ? wiersz.getP3A(): "brak nazwy indycentalnego");
           inc.setAdresincydentalny(wiersz.getP3B()!=null ? wiersz.getP3B(): "brak adresu indycentalnego");
           return inc;
        } else {
            Klienci klientznaleziony = klDAO.findKlientByNipImport(nrKontrahenta);
            if (klientznaleziony==null) {
                klientznaleziony = SzukajDaneBean.znajdzdaneregonAutomat(nrKontrahenta, gUSView);
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
    
    private String pobierzNIPkontrahenta(JPK.Faktura wiersz) {
        String nip = "";
        if (wiersz.getP5A()!=null) {
            nip = nip+wiersz.getP5A();
        }
        if (wiersz.getP5B()!=null) {
            nip = nip+wiersz.getP5B();
        }
        return nip;
    }
    
    private Evewidencja pobierzewidencje(deklaracje.jpkfa.JPK.Faktura wiersz, List<Evewidencja> evewidencje) {
        Evewidencja zwrot = null;
        double stawka = obliczstawke(wiersz);
        for (Evewidencja p : this.evewidencje) {
            if (p.getStawkavat()==stawka) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }
    
    private double obliczstawke(deklaracje.jpkfa.JPK.Faktura wiersz) {
        double stawka = 23;
        double netto = wiersz.getNetto();
        double vat = wiersz.getVat();
        double procent = Z.z4(vat/netto);
        if (procent>0.18) {
            stawka = 23;
        } else if (procent>0.07) {
            stawka = 8;
        }  else if (procent>0.04) {
            stawka = 5;
        } else {
            stawka = 0;
        }
        return stawka;
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
                        klDAO.dodaj(p);
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
                        dokDAO.dodaj(p);
                    }
                } catch(Exception e){
                }
            }
            dokumenty = Collections.synchronizedList(new ArrayList<>());
            Msg.msg("Zaksiowano zaimportowane dokumenty");
        }
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
