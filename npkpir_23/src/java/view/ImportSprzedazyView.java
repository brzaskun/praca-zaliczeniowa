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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import jpkabstract.SprzedazWierszA;
import msg.Msg; import org.primefaces.PrimeFaces;
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
public class ImportSprzedazyView  implements Serializable {
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
    private Rodzajedok rodzajedok;
    private List<Evewidencja> evewidencje;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private KlienciDAO klDAO;
    private boolean wybierzosobyfizyczne;
    private boolean wybierzfirmyzagraniczne;
    public double netto;
    public double vat;
    public double brutto;
        
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
            netto = 0.0;
            vat = 0.0;
            brutto = 0.0;
            for (Dok p : dokumenty) {
                netto = netto+p.getNetto();
                vat = vat+p.getVat();
            }
            brutto = Z.z(netto+vat);
            Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
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
        return dokumenty;
    }
    
    private List<Dok> stworzdokumentyFizyczne(JPKSuper jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
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
        return dokumenty;
    }
    
    private List<Dok> stworzdokumentyZagraniczne(JPKSuper jpk) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (jpk != null) {
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
        return dokumenty;
    }
    
    private Dok generujdok(Object p) {
        jpk201801.JPK.SprzedazWiersz wiersz = (jpk201801.JPK.SprzedazWiersz) p;
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
            selDokument.setKontr(pobierzkontrahenta(wiersz, wiersz.getNrKontrahenta()));
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
            EVatwpis1 eVatwpis1 = new EVatwpis1(pobierzewidencje(wiersz,evewidencje), wiersz.getNetto(), wiersz.getVat(), "sprz.op", miesiac, rok);
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
    
    private Klienci pobierzkontrahenta(jpk201801.JPK.SprzedazWiersz wiersz, String nrKontrahenta) {
        if (nrKontrahenta.equals("9551014391")) {
            System.out.println("");
        }
        if (wybierzosobyfizyczne || wybierzfirmyzagraniczne) {
           Klienci inc = new Klienci();
           inc.setNip(wiersz.getNrKontrahenta());
           inc.setNpelna(wiersz.getNazwaKontrahenta()!=null ? wiersz.getNazwaKontrahenta(): "brak nazwy indycentalnego");
           inc.setAdresincydentalny(wiersz.getAdresKontrahenta()!=null ? wiersz.getAdresKontrahenta(): "brak adresu indycentalnego");
           return inc;
        } else {
            Klienci klientznaleziony = klDAO.findKlientByNipImport(nrKontrahenta);
            if (klientznaleziony==null) {
                klientznaleziony = SzukajDaneBean.znajdzdaneregonAutomat(nrKontrahenta, gUSView);
                if (klientznaleziony!=null && klientznaleziony.getNpelna()!=null) {
                    boolean juzjest = false;
                    for (Klienci p : klienci) {
                        if (p.getNip().equals(klientznaleziony.getNip())) {
                            juzjest = true;
                            break;
                        }
                    }
                    if (juzjest==false) {
                        klienci.add(klientznaleziony);
                    }
                } else {
                     klientznaleziony.setNskrocona(klientznaleziony.getNpelna());
                     klientznaleziony.setNpelna("nie znaleziono firmy w bazie Regon");
                }
            } else if (klientznaleziony==null||klientznaleziony.getNpelna()==null) {
                klientznaleziony.setNpelna("istnieje wielu kontrahentów o tym samym numerze NIP! "+nrKontrahenta);
            }
            return klientznaleziony;
        }
    }
    
    private Evewidencja pobierzewidencje(SprzedazWierszA wiersz, List<Evewidencja> evewidencje) {
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
    
    private double obliczstawke(SprzedazWierszA wiersz) {
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
                    if (!p.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
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
                    if (!p.getKontr().getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                        dokDAO.dodaj(p);
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
