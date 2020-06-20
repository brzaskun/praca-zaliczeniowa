/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansFK.TabelaNBPBean;
import dao.DokDAO;
import dao.EvewidencjaDAO;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import deklaracje.vatue.m4.VATUEM4Bean;
import embeddable.AmazonCSV;
import entity.Dok;
import entity.EVatwpis1;
import entity.Evewidencja;
import entity.Klienci;
import entity.KwotaKolumna1;
import entity.Rodzajedok;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import error.E;
import gus.GUSView;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
import msg.Msg; import org.primefaces.PrimeFaces;
import org.joda.time.DateTime;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import pdf.PdfDok;
import plik.Plik;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class ImportCSVView  implements Serializable {
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
    private TabelanbpDAO tabelanbpDAO;
    private Rodzajedok dokSZ;
    private Rodzajedok dokWDT;
    private List<Evewidencja> evewidencje;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private KlienciDAO klDAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    private List<Waluty> listaWalut;
    private Waluty walutapln;
    private double kursumst;
        
    @PostConstruct
    private void init() { //E.m(this);
        dokSZ = rodzajedokDAO.find("SZ", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        dokWDT = rodzajedokDAO.find("WDT", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        evewidencje = evewidencjaDAO.znajdzpotransakcji("sprzedaz");
        listaWalut = walutyDAOfk.findAll();
        if (listaWalut!=null) {
            listaWalut.forEach((p)->{
              if (p.getSymbolwaluty().equals("PLN")) {
                  walutapln = p;
              }
            });
        }
    }
    
    public void importujsprzedaz(FileUploadEvent event) {
        try {
            dokumenty = Collections.synchronizedList(new ArrayList<>());
            klienci = Collections.synchronizedList(new ArrayList<>());
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            List<AmazonCSV> amazonCSV = pobierzJPK(uploadedFile);
            dokumenty = stworzdokumenty(amazonCSV);
            Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
        PrimeFaces.current().executeScript("PF('dialogAjaxCzekaj').hide()");
    }
    
    private List<AmazonCSV> pobierzJPK(UploadedFile uploadedFile) {
        List<AmazonCSV> zwrot = Collections.synchronizedList(new ArrayList<>());
        AmazonCSV tmpzwrot = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            InputStream is = uploadedFile.getInputStream();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(is, "windows-1252"))) {
                while ((line = br.readLine()) != null) {
                    try {
                        // use comma as separator
                        String[] tmpline = line.split(cvsSplitBy);
                        if (line.contains("GBP")) {
                            error.E.s("");
                        }
                        tmpzwrot = new AmazonCSV(tmpline);
                        zwrot.add(tmpzwrot);
                    } catch (Exception ex) {
                        E.e(ex);
                    }
                }
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception ex) {
            E.e(ex);
        }
        return zwrot;
    }
    
    private List<Dok> stworzdokumenty(List<AmazonCSV> lista) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (lista != null) {
            lista.forEach((p) -> {
                if (p.getOrderID() != null) {
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
        AmazonCSV wiersz = (AmazonCSV) p;
        Dok selDokument = new Dok();
        selDokument.setAmazonCSV(wiersz);
        try {
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            selDokument.setWprowadzil(principal.getName());
            String datawystawienia = wiersz.getData();
            String miesiac = datawystawienia.substring(5, 7);
            String rok = datawystawienia.substring(0, 4);
            selDokument.setPkpirM(miesiac);
            selDokument.setPkpirR(rok);
            selDokument.setVatM(miesiac);
            selDokument.setVatR(rok);
            selDokument.setPodatnik(wpisView.getPodatnikObiekt());
            selDokument.setStatus("bufor");
            selDokument.setUsunpozornie(false);
            selDokument.setDataWyst(wiersz.getData());
            selDokument.setDataSprz(wiersz.getData());
            selDokument.setKontr(pobierzkontrahenta(wiersz));
            if ((wiersz.getBuyerTaxRegistration().equals("") || wiersz.getBuyerTaxRegistrationJurisdiction().equals("DE")) && wiersz.getTaxRateD()>0.0){
                selDokument.setRodzajedok(dokSZ);
                selDokument.setOpis("sprzedaż detaliczna");
            } else {
                selDokument.setRodzajedok(dokWDT);
                selDokument.setOpis("sprzedaż WDT");
            }
            selDokument.setNrWlDk(wiersz.getShipmentID());
            List<KwotaKolumna1> listaX = Collections.synchronizedList(new ArrayList<>());
            KwotaKolumna1 tmpX = new KwotaKolumna1();
            tmpX.setNetto(wiersz.getNetto());
            tmpX.setVat(wiersz.getVat());
            tmpX.setNettowaluta(wiersz.getNettowaluta());
            tmpX.setVatwaluta(wiersz.getVatWaluta());
            tmpX.setNazwakolumny("przych. sprz");
            tmpX.setDok(selDokument);
            tmpX.setBrutto(Z.z(Z.z(wiersz.getNetto()+wiersz.getVat())));
            listaX.add(tmpX);
            String symbolwalt = "EUR";
            if (selDokument.getAmazonCSV()!=null) {
                //error.E.s(selDokument.getAmazonCSV().getCurrency());
                symbolwalt = selDokument.getAmazonCSV().getCurrency();
            }
            Tabelanbp innatabela = pobierztabele(symbolwalt, selDokument.getDataWyst());
            selDokument.setTabelanbp(innatabela);
            Tabelanbp walutadok = pobierztabele(wiersz.getCurrency(), selDokument.getDataWyst());
            selDokument.setWalutadokumentu(walutadok.getWaluta());
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
    
    private Tabelanbp pobierztabele(String waldok, String dataWyst) {
        DateTime dzienposzukiwany = new DateTime(dataWyst);
        return TabelaNBPBean.pobierzTabeleNBP(dzienposzukiwany, tabelanbpDAO, waldok);
    }
    
    public Dok sprawdzCzyNieDuplikat(Dok selD) {
        if (selD.getKontr().getNpelna().equals("OPTEGRA POLSKA sp. z o.o.")) {
            error.E.s("");
        }
        Dok tmp = null;
        tmp = dokDAO.znajdzDuplikatwtrakcie(selD, wpisView.getPodatnikObiekt(), selD.getRodzajedok().getSkrot());
        return tmp;
    }
    
    private Klienci pobierzkontrahenta(AmazonCSV wiersz) {
        Klienci inc = new Klienci();
        inc.setNip(wiersz.getBuyerTaxRegistration()!=null && !wiersz.getBuyerTaxRegistration().equals("") ? wiersz.getBuyerTaxRegistration():null);
        inc.setNpelna(wiersz.getOrderID()!=null ? wiersz.getOrderID(): "brak nazwy indycentalnego");
        inc.setNskrocona(wiersz.getOrderID()!=null ? wiersz.getOrderID(): "brak nazwy indycentalnego");
        inc.setAdresincydentalny(wiersz.getAdress()!=null ? wiersz.getAdress(): "brak adresu indycentalnego");
        return inc;
    }
    
    private Evewidencja pobierzewidencje(AmazonCSV wiersz, List<Evewidencja> evewidencje) {
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
    
    private double obliczstawke(AmazonCSV wiersz) {
        double stawka = Double.valueOf(wiersz.getTaxRate())*100;
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
    public void drukuj() {
        try {
            PdfDok.drukujDokAmazon(dokumenty, wpisView, 1);
            Msg.msg("Wydrukowano zestawienie zaimportowanych dokumentów");
        } catch (Exception e) {
            
        }
    }
    
    
    public void robcsv() {
        try {
            if (kursumst!=0.0) {
                String nazwa = wpisView.getPodatnikObiekt().getNip()+"CSVWDT";
                List<PozycjeCSV> pozycje = new ArrayList<>();
                for (Dok p : dokumenty) {
                    if (p.getRodzajedok().getSkrotNazwyDok().equals("WDT") && p.getAmazonCSV().getJurisdictionName().equals("GERMANY") && !p.getAmazonCSV().getBuyerTaxRegistration().equals("")) {
                        PozycjeCSV pozycja = zrobpozycje(pozycje,p);
                        if (pozycja!=null) {
                            pozycje.add(pozycja);
                        }
                    }
                }
                String header = "Laenderkennzeichen (der USt-IdNr.) *,USt-IdNr. (ohne Laenderkennzeichen) *,Betrag (Euro) *,Art der Leistung *,Importmeldung"+"\n";
                List<String> zwrot = new ArrayList<>();
                zwrot.add(header);
                for (PozycjeCSV p : pozycje) {
                    String zw = p.getKraj()+",";
                    zw = zw + p.getNip()+",";
                    zw = zw + String.valueOf(Z.zUD(p.getKwota()))+",";
                    zw = zw + "L,";
                    zw = zw + "";
                    zw = zw + "\n";
                    zwrot.add(zw);
                }
                StringBuffer sb = new StringBuffer();
                for (String s : zwrot) {
                   sb.append(s);
                }
                String tresc = sb.toString();
                String name = Plik.zapiszplik(nazwa, "csv", tresc);
                String f = "wydrukCSV('"+name+"');";
                PrimeFaces.current().executeScript(f);
                Msg.msg("Wygenerowano pliki CSV WDT");
            } else {
                Msg.msg("e", "Brak kursu UMSt, nie można wygenerować plików CSV WDT");
            }
        } catch (Exception e) {
            
        }
    }

    private PozycjeCSV zrobpozycje(List<PozycjeCSV> pozycje, Dok p) {
        PozycjeCSV zwrot = new PozycjeCSV();
        for (PozycjeCSV r : pozycje) {
            String nip = VATUEM4Bean.przetworznip(p.getAmazonCSV().getBuyerTaxRegistration());
            if (r.getNip().equals(nip)) {
                r.setKwota(r.getKwota()+p.getNettoWaluta());
                zwrot = null;
                break;
            }
        }
        if (zwrot!=null) {
            zwrot.setKraj(p.getAmazonCSV().getBuyerTaxRegistrationJurisdiction());
            zwrot.setNip(VATUEM4Bean.przetworznip(p.getAmazonCSV().getBuyerTaxRegistration()));
            zwrot.setKwota(p.getNettoWaluta());
        }
        return zwrot;
    }
    
    class PozycjeCSV {
        private String kraj;
        private String nip;
        private double kwota;
        private final String niptransakcja = "L";

        public String getKraj() {
            return kraj;
        }

        public void setKraj(String kraj) {
            this.kraj = kraj;
        }

        public String getNip() {
            return nip;
        }

        public void setNip(String nip) {
            this.nip = nip;
        }

        public double getKwota() {
            return kwota;
        }

        public void setKwota(double kwota) {
            this.kwota = kwota;
        }

        public String getNiptransakcja() {
            return niptransakcja;
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

    public double getKursumst() {
        return kursumst;
    }

    public void setKursumst(double kursumst) {
        this.kursumst = kursumst;
    }

    
    
}
