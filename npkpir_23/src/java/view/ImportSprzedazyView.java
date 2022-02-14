/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansRegon.SzukajDaneBean;
import dao.DokDAO;
import dao.EvewidencjaDAO;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import entity.Dok;
import entity.EVatwpis1;
import entity.Evewidencja;
import entity.JPKSuper;
import entity.Klienci;
import entity.KwotaKolumna1;
import entity.Rodzajedok;
import error.E;
import gus.GUSView;
import java.io.InputStream;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import jpkabstract.SprzedazWierszA;
import msg.Msg;
 import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pdf.PdfDok;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ImportSprzedazyView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Dok> dokumenty;
    private List<Klienci> klienci;
    @Inject
    private WpisView wpisView;
    @Inject
    private GUSView gUSView;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private DokDAO dokDAO;
    private Rodzajedok rodzajedok;
    private List<Evewidencja> evewidencje;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private KlienciDAO klDAO;
    private boolean wybierzosobyfizyczne;
    private boolean wybierzfirmyzagraniczne;
    private double netto;
    private double vat;
    private double brutto;
        
    @PostConstruct
    private void init() { //E.m(this);
        rodzajedok = rodzajedokDAO.find("SZ", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        evewidencje = evewidencjaDAO.znajdzpotransakcji("sprzedaz");
    }
    
    public void importujsprzedaz(FileUploadEvent event) {
        try {
            dokumenty = Collections.synchronizedList(new ArrayList<>());
            klienci = Collections.synchronizedList(new ArrayList<>());
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            JPKSuper jpk = pobierzJPK(uploadedFile);
            if (wybierzosobyfizyczne) {
                dokumenty = stworzdokumentyFizyczne(jpk);
            } else if (wybierzfirmyzagraniczne) {
                dokumenty = stworzdokumentyZagraniczne(jpk);
            } else {
                dokumenty = stworzdokumenty(jpk);
            }
            if (dokumenty!=null&&!dokumenty.isEmpty()) {
                netto = 0.0;
                vat = 0.0;
                brutto = 0.0;
                for (Dok p : dokumenty) {
                    netto = netto+p.getNetto();
                    vat = vat+p.getVat();
                }
                brutto = Z.z(netto+vat);
                Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
            } else {
                Msg.msg("e","Plik " + filename + " pusty lub wystąpił błąd podczas generowania.");
            }
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
        try {
            PrimeFaces.current().executeScript("PF('dialogAjaxCzekaj').hide()");
        } catch (Exception e){}
    }
    
    private JPKSuper pobierzJPK(UploadedFile uploadedFile) {
       JPKSuper zwrot = null;
       try {
           InputStream is = uploadedFile.getInputstream();
           JAXBContext context = JAXBContext.newInstance(pl.gov.crd.wzor._2021._12._27._11148.JPK.class);
           Unmarshaller unmarshaller = context.createUnmarshaller();
           zwrot = (pl.gov.crd.wzor._2021._12._27._11148.JPK) unmarshaller.unmarshal(is);
       } catch (Exception ex) {}
       try {
           InputStream is = uploadedFile.getInputstream();
           JAXBContext context = JAXBContext.newInstance(pl.gov.crd.wzor._2020._05._08._9393.JPK.class);
           Unmarshaller unmarshaller = context.createUnmarshaller();
           zwrot = (pl.gov.crd.wzor._2020._05._08._9393.JPK) unmarshaller.unmarshal(is);
       } catch (Exception ex) {}
       try {
           InputStream is = uploadedFile.getInputstream();
           JAXBContext context = JAXBContext.newInstance(jpk201701.JPK.class);
           Unmarshaller unmarshaller = context.createUnmarshaller();
           zwrot = (jpk201701.JPK) unmarshaller.unmarshal(is);
       } catch (Exception ex) {}
       try {
           InputStream is = uploadedFile.getInputstream();
           JAXBContext context = JAXBContext.newInstance(jpk201801.JPK.class);
           Unmarshaller unmarshaller = context.createUnmarshaller();
           zwrot = (jpk201801.JPK) unmarshaller.unmarshal(is);
       } catch (Exception ex) {}
       return zwrot;
    }
    
    private List<Dok> stworzdokumenty(JPKSuper jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            if (jpk instanceof pl.gov.crd.wzor._2021._12._27._11149.JPK) {
                ((pl.gov.crd.wzor._2021._12._27._11149.JPK) jpk).getEwidencja().getSprzedazWiersz().forEach((p) -> {
                    SprzedazWierszA wiersz = (SprzedazWierszA) p;
                    if (wiersz.getNrKontrahenta() != null && wiersz.getNrKontrahenta().length()==10) {
                        Dok dok = generujdok(p);
                        if (dok!=null) {
                            dokumenty.add(dok);
                        }
                    }
                });
            } else if (jpk instanceof pl.gov.crd.wzor._2021._12._27._11148.JPK) {
                int miesiac = ((pl.gov.crd.wzor._2021._12._27._11148.JPK) jpk).getNaglowek().getMiesiac();
                int mcpkpir = Integer.parseInt(wpisView.getMiesiacWpisu());
                if (mcpkpir!=miesiac) {
                    Msg.msg("e","Uwaga. Plik z innego miesiąca!");
                } else {
                    ((pl.gov.crd.wzor._2021._12._27._11148.JPK) jpk).getEwidencja().getSprzedazWiersz().forEach((p) -> {
                        SprzedazWierszA wiersz = (SprzedazWierszA) p;
                        if (wiersz.getNrKontrahenta() != null && wiersz.getNrKontrahenta().length()==10) {
                            Dok dok = generujdok(p);
                            if (dok!=null) {
                                dokumenty.add(dok);
                            }
                        }
                    });
                }
            } else 
            if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9394.JPK) {
                ((pl.gov.crd.wzor._2020._05._08._9394.JPK) jpk).getEwidencja().getSprzedazWiersz().forEach((p) -> {
                    SprzedazWierszA wiersz = (SprzedazWierszA) p;
                    if (wiersz.getNrKontrahenta() != null && wiersz.getNrKontrahenta().length()==10) {
                        Dok dok = generujdok(p);
                        if (dok!=null) {
                            dokumenty.add(dok);
                        }
                    }
                });
            } else if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9393.JPK) {
                int miesiac = ((pl.gov.crd.wzor._2020._05._08._9393.JPK) jpk).getNaglowek().getMiesiac();
                int mcpkpir = Integer.parseInt(wpisView.getMiesiacWpisu());
                if (mcpkpir!=miesiac) {
                    Msg.msg("e","Uwaga. Plik z innego miesiąca!");
                } else {
                    ((pl.gov.crd.wzor._2020._05._08._9393.JPK) jpk).getEwidencja().getSprzedazWiersz().forEach((p) -> {
                        SprzedazWierszA wiersz = (SprzedazWierszA) p;
                        if (wiersz.getNrKontrahenta() != null && wiersz.getNrKontrahenta().length()==10) {
                            Dok dok = generujdok(p);
                            if (dok!=null) {
                                dokumenty.add(dok);
                            }
                        }
                    });
                }
            } else {
                jpk.getSprzedazWiersz().forEach((p) -> {
                    SprzedazWierszA wiersz = (SprzedazWierszA) p;
                    if (wiersz.getNrKontrahenta() != null && wiersz.getNrKontrahenta().length()==10) {
                        Dok dok = generujdok(p);
                        if (dok!=null) {
                            dokumenty.add(dok);
                        }
                    }
                });
            }
        }
        return dokumenty;
    }
    
    private List<Dok> stworzdokumentyFizyczne(JPKSuper jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
            if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9393.JPK) {
                ((pl.gov.crd.wzor._2020._05._08._9393.JPK) jpk).getEwidencja().getSprzedazWiersz().forEach((p) -> {
                    SprzedazWierszA wiersz = (SprzedazWierszA) p;
                    if (wiersz.getNrKontrahenta() == null ||(wiersz.getNrKontrahenta() != null && wiersz.getNrKontrahenta().length()<6)) {
                        Dok dok = generujdok(p);
                        if (dok!=null) {
                            dokumenty.add(dok);
                        }
                    }
                });
            } else {
                jpk.getSprzedazWiersz().forEach((p) -> {
                    SprzedazWierszA wiersz = (SprzedazWierszA) p;
                    if (wiersz.getNrKontrahenta() == null ||(wiersz.getNrKontrahenta() != null && wiersz.getNrKontrahenta().length()<6)) {
                        Dok dok = generujdok(p);
                        if (dok!=null) {
                            dokumenty.add(dok);
                        }
                    }
                });
            }
        }
        return dokumenty;
    }
    
    private List<Dok> stworzdokumentyZagraniczne(JPKSuper jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
                 if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9393.JPK) {
                ((pl.gov.crd.wzor._2020._05._08._9393.JPK) jpk).getEwidencja().getSprzedazWiersz().forEach((p) -> {
                    SprzedazWierszA wiersz = (SprzedazWierszA) p;
                    if (wiersz.getNrKontrahenta() != null && wiersz.getNrKontrahenta().length()==10) {
                        Dok dok = generujdok(p);
                        if (dok!=null) {
                            dokumenty.add(dok);
                        }
                    }
                });
            } else {
                jpk.getSprzedazWiersz().forEach((p) -> {
                    SprzedazWierszA wiersz = (SprzedazWierszA) p;
                    if (wiersz.getNrKontrahenta() != null && !wiersz.getNrKontrahenta().startsWith("PL") && wiersz.getNrKontrahenta().matches("^[A-I].*$")  && wiersz.getNrKontrahenta().length()> 6) {
                        Dok dok = generujdok(p);
                        if (dok!=null) {
                            dokumenty.add(dok);
                        }
                    }
                });
            }
        }
        return dokumenty;
    }
     
    
    private Dok generujdok(Object p) {
        jpkabstract.SprzedazWierszA wiersz = (jpkabstract.SprzedazWierszA) p;
        Dok selDokument = new Dok();
        try {
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            selDokument.setWprowadzil(principal.getName());
            String datawystawienia = wiersz.getDataWystawienia().toString();
            String miesiac = datawystawienia.substring(5, 7);
            String rok = datawystawienia.substring(0, 4);
            selDokument.setPkpirM(miesiac);
            selDokument.setPkpirR(rok);
            selDokument.setVatM(miesiac);
            selDokument.setVatR(rok);
            selDokument.setPodatnik(wpisView.getPodatnikObiekt());
            selDokument.setStatus("bufor");
            selDokument.setUsunpozornie(false);
            selDokument.setDataWyst(wiersz.getDataWystawienia().toString());
            if (wiersz.getDataSprzedazy()!=null) {
                selDokument.setDataSprz(wiersz.getDataSprzedazy().toString());
            } else {
                selDokument.setDataSprz(wiersz.getDataWystawienia().toString());
            }
            selDokument.setKontr(pobierzkontrahenta(wiersz.getNazwaKontrahenta(), wiersz.getAdresKontrahenta(), wiersz.getNrKontrahenta()));
            selDokument.setRodzajedok(rodzajedok);
            selDokument.setNrWlDk(wiersz.getDowodSprzedazy());
            selDokument.setOpis("przychód ze sprzedaży");
            List<KwotaKolumna1> listaX = Collections.synchronizedList(new ArrayList<>());
            KwotaKolumna1 tmpX = new KwotaKolumna1();
            tmpX.setNetto(wiersz.getNetto());
            tmpX.setVat(wiersz.getVat());
            tmpX.setNazwakolumny("przych. sprz");
            tmpX.setDok(selDokument);
            tmpX.setBrutto(Z.z(Z.z(wiersz.getNetto()+wiersz.getVat())));
            listaX.add(tmpX);
            selDokument.setListakwot1(listaX);
            selDokument.setNetto(tmpX.getNetto());
            selDokument.setBrutto(tmpX.getBrutto());
            selDokument.setRozliczony(true);
            List<EVatwpis1> ewidencjaTransformowana = Collections.synchronizedList(new ArrayList<>());
            EVatwpis1 eVatwpis1 = new EVatwpis1(pobierzewidencje(wiersz.getNetto(), wiersz.getVat(),evewidencje), wiersz.getNetto(), wiersz.getVat(), "sprz.op", miesiac, rok);
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
    
    private Dok generujdok2020(Object p) {
        pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazWiersz wiersz = (pl.gov.crd.wzor._2020._05._08._9393.JPK.Ewidencja.SprzedazWiersz) p;
        Dok selDokument = new Dok();
        try {
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            selDokument.setWprowadzil(principal.getName());
            String datawystawienia = wiersz.getDataWystawienia().toString();
            String miesiac = datawystawienia.substring(5, 7);
            String rok = datawystawienia.substring(0, 4);
            selDokument.setPkpirM(miesiac);
            selDokument.setPkpirR(rok);
            selDokument.setVatM(miesiac);
            selDokument.setVatR(rok);
            selDokument.setPodatnik(wpisView.getPodatnikObiekt());
            selDokument.setStatus("bufor");
            selDokument.setUsunpozornie(false);
            selDokument.setDataWyst(wiersz.getDataWystawienia().toString());
            if (wiersz.getDataSprzedazy()!=null) {
                selDokument.setDataSprz(wiersz.getDataSprzedazy().toString());
            } else {
                selDokument.setDataSprz(wiersz.getDataWystawienia().toString());
            }
            selDokument.setKontr(pobierzkontrahenta(wiersz.getNazwaKontrahenta(), "", wiersz.getNrKontrahenta()));
            selDokument.setRodzajedok(rodzajedok);
            selDokument.setNrWlDk(wiersz.getDowodSprzedazy());
            selDokument.setOpis("przychód ze sprzedaży");
            List<KwotaKolumna1> listaX = Collections.synchronizedList(new ArrayList<>());
            KwotaKolumna1 tmpX = new KwotaKolumna1();
            tmpX.setNetto(wiersz.getNetto());
            tmpX.setVat(wiersz.getVat());
            tmpX.setNazwakolumny("przych. sprz");
            tmpX.setDok(selDokument);
            tmpX.setBrutto(Z.z(Z.z(wiersz.getNetto()+wiersz.getVat())));
            listaX.add(tmpX);
            selDokument.setListakwot1(listaX);
            selDokument.setNetto(tmpX.getNetto());
            selDokument.setBrutto(tmpX.getBrutto());
            selDokument.setRozliczony(true);
            List<EVatwpis1> ewidencjaTransformowana = Collections.synchronizedList(new ArrayList<>());
            EVatwpis1 eVatwpis1 = new EVatwpis1(pobierzewidencje(wiersz.getNetto(), wiersz.getVat(),evewidencje), wiersz.getNetto(), wiersz.getVat(), "sprz.op", miesiac, rok);
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
            error.E.s("");
        }
        Dok tmp = null;
        tmp = dokDAO.znajdzDuplikatwtrakcie(selD, wpisView.getPodatnikObiekt(), selD.getRodzajedok().getSkrot());
        return tmp;
    }
    
    private Klienci pobierzkontrahenta(String nazwa, String adres, String nrKontrahenta) {
        if (nrKontrahenta.equals("9551014391")) {
            error.E.s("");
        }
        if (wybierzosobyfizyczne || wybierzfirmyzagraniczne) {
           Klienci inc = new Klienci();
           inc.setNip(nrKontrahenta);
           inc.setNpelna(nazwa!=null ? nazwa: "brak nazwy indycentalnego");
           inc.setAdresincydentalny(adres!=null ? adres: "brak adresu indycentalnego");
           return inc;
        } else {
            Klienci klientznaleziony = klDAO.findKlientByNipImport(nrKontrahenta);
            if (klientznaleziony==null) {
                klientznaleziony = SzukajDaneBean.znajdzdaneregonAutomat(nrKontrahenta);
                if (klientznaleziony!=null && klientznaleziony.getNpelna()!=null) {
                    boolean juzjest = false;
                    for (Klienci p : klienci) {
                        if (p.getNip()!=null && p.getNip().equals(klientznaleziony.getNip())) {
                            juzjest = true;
                            break;
                        }
                    }
                    if (juzjest==false) {
                        klienci.add(klientznaleziony);
                    }
                } else if (klientznaleziony!=null){
                     klientznaleziony.setNskrocona(klientznaleziony.getNpelna());
                     klientznaleziony.setNpelna("nie znaleziono firmy w bazie Regon");
                }
            } else if (klientznaleziony==null||klientznaleziony.getNpelna()==null) {
                klientznaleziony.setNpelna("istnieje wielu kontrahentów o tym samym numerze NIP! "+nrKontrahenta);
            }
            return klientznaleziony;
        }
    }
    
    private Evewidencja pobierzewidencje(double netto, double vat, List<Evewidencja> evewidencje) {
        Evewidencja zwrot = null;
        double stawka = obliczstawke(netto, vat);
        for (Evewidencja p : evewidencje) {
            if (p.getStawkavat()==stawka) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }
    
    private double obliczstawke(double netto, double vat) {
        double stawka = 23;
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
                    if (!p.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
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
                    if (!p.getKontr().getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                        dokDAO.create(p);
                    } else {
                        Msg.msg("e","Bład kontrahenta. Nie zaksięgowano dokumentu!");
                    }
                } catch(Exception e){
                }
            }
            dokumenty = Collections.synchronizedList(new ArrayList<>());
            Msg.msg("Zaksiowano zaimportowane dokumenty");
        }
    }
    
     public void drukuj() {
        try {
            PdfDok.drukujJPK_FA(dokumenty, wpisView, 1, false);
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

    public boolean isWybierzfirmyzagraniczne() {
        return wybierzfirmyzagraniczne;
    }

    public void setWybierzfirmyzagraniczne(boolean wybierzfirmyzagraniczne) {
        this.wybierzfirmyzagraniczne = wybierzfirmyzagraniczne;
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
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    
    
}
