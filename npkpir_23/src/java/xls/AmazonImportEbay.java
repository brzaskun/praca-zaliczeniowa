/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import beansFK.TabelaNBPBean;
import beansJPK.KlienciJPKBean;
import beansRegon.SzukajDaneBean;
import dao.DokDAO;
import dao.EvewidencjaDAO;
import dao.KlienciDAO;
import dao.KlientJPKDAO;
import dao.RodzajedokDAO;
import dao.TabelanbpDAO;
import dao.WalutyDAOfk;
import data.Data;
import deklaracje.vatue.m4.VATUEM4Bean;
import embeddable.AmazonCSV;
import embeddable.PanstwaMap;
import embeddablefk.InterpaperXLS;
import entity.Dok;
import entity.EVatwpisKJPK;
import entity.Evewidencja;
import entity.Klienci;
import entity.KlientJPK;
import entity.Rodzajedok;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import error.E;
import gus.GUSView;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.joda.time.DateTime;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import pdf.PdfDok;
import plik.Plik;
import view.*;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class AmazonImportEbay  implements Serializable {
    private static final long serialVersionUID = 1L;

    

    
    private List<Dok> dokumenty;
    private List<KlientJPK> listafk;
    private List<Klienci> klienci;
    @Inject
    private WpisView wpisView;
    @Inject
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
    private KlientJPKDAO klientJPKDAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    private List<Waluty> listaWalut;
    private Waluty walutapln;
    private double kursumst;
    private String wybortransakcji;
        
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
        wybortransakcji= "WSZYSTKIE";
    }
    
    public void importujsprzedaz(FileUploadEvent event) {
        try {
            dokumenty = Collections.synchronizedList(new ArrayList<>());
            klienci = Collections.synchronizedList(new ArrayList<>());
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            listafk = pobierzJPK(uploadedFile);
            //dokumenty = stworzdokumenty(amazonCSV);
            Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
        PrimeFaces.current().executeScript("PF('dialogAjaxCzekaj').hide()");
    }
    
    private List<KlientJPK> pobierzJPK(UploadedFile uploadedFile) {
        List<KlientJPK> zwrot = Collections.synchronizedList(new ArrayList<>());
        KlientJPK tmpzwrot = null;
        Evewidencja evewidencja = evewidencjaDAO.znajdzponazwie("rejestr WDT");
//        String line = "";
//        String cvsSplitBy = ",";
        try {
           InputStream is = uploadedFile.getInputStream();
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheet("Template");
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 0;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (i>2) {
                     tmpzwrot = tworzobiektAmazonNowy(row, wybortransakcji);
                     if (tmpzwrot!=null) {
                         zwrot.add(tmpzwrot);
                    }
                }
                i++;
            }
        } catch (Exception ex) {
            E.e(ex);
        }
        return zwrot;
    }
    
    private KlientJPK tworzobiektAmazonNowy(Row row, String wybortrans) {
        KlientJPK klientJPK = new KlientJPK();
        String rodzajtransakcji = row.getCell(2).getStringCellValue();
        String serial = row.getCell(4).getStringCellValue();
        try {
            if (rodzajtransakcji.equals("NON_AMAZON")) {
                klientJPK.setDowodSprzedazy(rodzajtransakcji);
                klientJPK.setSerial(serial);
                String data = row.getCell(9).getStringCellValue();
                klientJPK.setDataSprzedazy(Data.zmienkolejnosc(data));
                klientJPK.setDataWystawienia(Data.zmienkolejnosc(data));
                String krajcdocelowy = row.getCell(39).getStringCellValue();
                String krajwysylki = row.getCell(34).getStringCellValue();
                String stawka = row.getCell(17).getStringCellValue();
                double stawkavat = Double.valueOf(stawka);
                klientJPK.setStawkavat(stawkavat);
                String opodatkowanie = krajcdocelowy;
                klientJPK.setJurysdykcja(opodatkowanie);
                klientJPK.setKodKrajuNadania(krajwysylki);
                klientJPK.setKodKrajuDoreczenia(krajcdocelowy);
                klientJPK.setRok(wpisView.getRokWpisuSt());
                klientJPK.setMc(wpisView.getMiesiacWpisu());
                String waluta = row.getCell(26).getStringCellValue();
                klientJPK.setWaluta(waluta);
                klientJPK.setNettowaluta(format.F.kwota(row.getCell(14).getStringCellValue()));
                klientJPK.setVatwaluta(format.F.kwota(row.getCell(18).getStringCellValue()));
                double kurs = pobierzkurs(klientJPK.getDataSprzedazy(), waluta);
                klientJPK.setKurs(kurs);
                klientJPK.setNetto(Z.z(klientJPK.getNettowaluta()*kurs));
                klientJPK.setVat(Z.z(klientJPK.getVatwaluta()*kurs));
            } else {
                klientJPK = null;
            }
        } catch (Exception e) {
        }
        return klientJPK;
    }

     public void zaksiegujdokjpk() {
        klientJPKDAO.deleteByPodRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        List<KlientJPK> lista = KlienciJPKBean.zaksiegujdokJPK(null, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        klientJPKDAO.createList(lista);
        Msg.msg("Zaksięgowano dokumenty dla JPK");
    }
     
     public void zaksiegujWDTjpk() {
        klientJPKDAO.deleteByPodRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        List<KlientJPK> lista = listafk.stream().filter(p->p.isWdt()).collect(Collectors.toList());
        System.out.println("");
        klientJPKDAO.createList(lista);
        Msg.msg("Zaksięgowano dokumenty dla JPK");
    }
    
    private List<Dok> stworzdokumenty(List<AmazonCSV> lista) {
        List<Dok> dokumenty = Collections.synchronizedList(new ArrayList<>());
        if (lista != null) {
            lista.forEach((p) -> {
                if (p.getOrderID() != null) {
//                    Dok dok = generujdok(p);
//                    if (dok!=null) {
//                        dokumenty.add(dok);
//                    }
                }
            });
        }
        return dokumenty;
    }
    
//    private Dok generujdok(Object p) {
//        AmazonCSV wiersz = (AmazonCSV) p;
//        Dok selDokument = new Dok();
//        selDokument.setAmazonCSV(wiersz);
//        try {
//            HttpServletRequest request;
//            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            Principal principal = request.getUserPrincipal();
//            selDokument.setWprowadzil(principal.getName());
//            String datawystawienia = wiersz.getData();
//            String miesiac = datawystawienia.substring(5, 7);
//            String rok = datawystawienia.substring(0, 4);
//            selDokument.setPkpirM(miesiac);
//            selDokument.setPkpirR(rok);
//            selDokument.setVatM(miesiac);
//            selDokument.setVatR(rok);
//            selDokument.setPodatnik(wpisView.getPodatnikObiekt());
//            selDokument.setStatus("bufor");
//            selDokument.setUsunpozornie(false);
//            selDokument.setDataWyst(wiersz.getData());
//            selDokument.setDataSprz(wiersz.getData());
//            selDokument.setKontr(pobierzkontrahenta(wiersz));
//            if ((wiersz.getBuyerTaxRegistration().equals("") || wiersz.getBuyerTaxRegistrationJurisdiction().equals("DE")) && wiersz.getTaxRateD()>0.0){
//                selDokument.setRodzajedok(dokSZ);
//                selDokument.setOpis("sprzedaż detaliczna");
//            } else {
//                selDokument.setRodzajedok(dokWDT);
//                selDokument.setOpis("sprzedaż WDT");
//            }
//            selDokument.setNrWlDk(wiersz.getShipmentID());
//            List<KwotaKolumna1> listaX = Collections.synchronizedList(new ArrayList<>());
//            KwotaKolumna1 tmpX = new KwotaKolumna1();
//            tmpX.setNetto(wiersz.getNetto());
//            tmpX.setVat(wiersz.getVat());
//            tmpX.setNettowaluta(wiersz.getNettowaluta());
//            tmpX.setVatwaluta(wiersz.getVatWaluta());
//            tmpX.setNazwakolumny("przych. sprz");
//            tmpX.setDok(selDokument);
//            tmpX.setBrutto(Z.z(Z.z(wiersz.getNetto()+wiersz.getVat())));
//            listaX.add(tmpX);
//            String symbolwalt = "EUR";
//            if (selDokument.getAmazonCSV()!=null) {
//                //error.E.s(selDokument.getAmazonCSV().getCurrency());
//                symbolwalt = selDokument.getAmazonCSV().getCurrency();
//            }
//            Tabelanbp innatabela = pobierztabele(symbolwalt, selDokument.getDataWyst());
//            selDokument.setTabelanbp(innatabela);
//            Tabelanbp walutadok = pobierztabele(wiersz.getCurrency(), selDokument.getDataWyst());
//            selDokument.setWalutadokumentu(walutadok.getWaluta());
//            selDokument.setListakwot1(listaX);
//            selDokument.setNetto(tmpX.getNetto());
//            selDokument.setBrutto(tmpX.getBrutto());
//            selDokument.setRozliczony(true);
//            List<EVatwpis1> ewidencjaTransformowana = Collections.synchronizedList(new ArrayList<>());
//            EVatwpis1 eVatwpis1 = new EVatwpis1(pobierzewidencje(wiersz,evewidencje), wiersz.getNetto(), wiersz.getVat(), "sprz.op", miesiac, rok);
//            eVatwpis1.setDok(selDokument);
//            ewidencjaTransformowana.add(eVatwpis1);
//            selDokument.setEwidencjaVAT1(ewidencjaTransformowana);
//            if (selDokument.getKontr()!=null && sprawdzCzyNieDuplikat(selDokument)!=null) {
//                selDokument = null;
//            }
//        } catch (Exception e) {
//            E.e(e);
//        }
//        return selDokument;
//    }
//    
//    private Tabelanbp pobierztabele(String waldok, String dataWyst) {
//        DateTime dzienposzukiwany = new DateTime(dataWyst);
//        return TabelaNBPBean.pobierzTabeleNBP(dzienposzukiwany, tabelanbpDAO, waldok);
//    }
//    
//    public Dok sprawdzCzyNieDuplikat(Dok selD) {
//        if (selD.getKontr().getNpelna().equals("OPTEGRA POLSKA sp. z o.o.")) {
//            error.E.s("");
//        }
//        Dok tmp = null;
//        tmp = dokDAO.znajdzDuplikatwtrakcie(selD, wpisView.getPodatnikObiekt(), selD.getRodzajedok().getSkrot());
//        return tmp;
//    }
//    
//    private Klienci pobierzkontrahenta(AmazonCSV wiersz) {
//        Klienci inc = new Klienci();
//        inc.setNip(wiersz.getBuyerTaxRegistration()!=null && !wiersz.getBuyerTaxRegistration().equals("") ? wiersz.getBuyerTaxRegistration():null);
//        inc.setNpelna(wiersz.getOrderID()!=null ? wiersz.getOrderID(): "brak nazwy indycentalnego");
//        inc.setNskrocona(wiersz.getOrderID()!=null ? wiersz.getOrderID(): "brak nazwy indycentalnego");
//        inc.setAdresincydentalny(wiersz.getAdress()!=null ? wiersz.getAdress(): "brak adresu indycentalnego");
//        return inc;
//    }
//    
//    private Evewidencja pobierzewidencje(AmazonCSV wiersz, List<Evewidencja> evewidencje) {
//        Evewidencja zwrot = null;
//        double stawka = obliczstawke(wiersz);
//        for (Evewidencja p : this.evewidencje) {
//            if (p.getStawkavat()==stawka) {
//                zwrot = p;
//                break;
//            }
//        }
//        return zwrot;
//    }
//    
//    private double obliczstawke(AmazonCSV wiersz) {
//        double stawka = Double.valueOf(wiersz.getTaxRate())*100;
//        return stawka;
//    }
//    
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
    public void drukuj() {
        try {
            PdfDok.drukujDokAmazon(dokumenty, wpisView, 1);
            Msg.msg("Wydrukowano zestawienie zaimportowanych dokumentów");
        } catch (Exception e) {
            
        }
    }
    
    public void drukujfk() {
        try {
            PdfDok.drukujDokAmazonfk(listafk, wpisView, 1);
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
private static Klienci ustawkontrahenta(InterpaperXLS interpaperXLS, List<Klienci> k, KlienciDAO klienciDAO, Map<String, Klienci> znalezieni) {
//       if (interpaperXLS.getKontrahent().equals("HST")) {
//           error.E.s("");
//       }
       Klienci klient = null;
        try {
            if (!znalezieni.isEmpty()) {
                for (String p : znalezieni.keySet()) {
                    if (p.equals(interpaperXLS.getKontrahent().trim())) {
                        klient = znalezieni.get(p);
                        break;
                    }
                }
            }
            if (klient==null && interpaperXLS.getNip()!=null && interpaperXLS.getNip().length()>6) {
                for (Klienci p : k) {
                    if (p.getNip().contains(interpaperXLS.getNip().trim())) {
                        klient = p;
                        znalezieni.put(interpaperXLS.getKontrahent(), p);
                        break;
                    }
                }
            }
            if (klient==null && interpaperXLS.getKontrahent().toLowerCase().trim().length()>3 && interpaperXLS.getNip()!=null && interpaperXLS.getNip().length()>6) {
                for (Klienci p : k) {
                    if (p.getNpelna().toLowerCase().contains(interpaperXLS.getKontrahent().toLowerCase().trim()) || (p.getNskrocona()!=null && p.getNskrocona().toLowerCase().contains(interpaperXLS.getKontrahent().toLowerCase().trim()))) {
                        klient = p;
                        znalezieni.put(interpaperXLS.getKontrahent(), p);
                        break;
                    }
                }
            }
            if (klient==null && interpaperXLS.getNip()!=null && interpaperXLS.getNip().length()>6) {
                String nip = interpaperXLS.getNip().trim();
                if (nip.length()==10 && Character.isDigit(nip.charAt(0))) {
                    klient = SzukajDaneBean.znajdzdaneregonAutomat(nip);
                    if (klient.getNpelna()==null) {
                        klient = null;
                    } else {
                        if (!klient.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                            klienciDAO.create(klient);
                        }
                    }
                    znalezieni.put(interpaperXLS.getKontrahent(), klient);
                }
            }
             
            if (klient==null && interpaperXLS.getNip()!=null && interpaperXLS.getNip().length()>6) {
                String nip = interpaperXLS.getNip().trim();
                if (interpaperXLS.getKlientpaństwo()!=null) {
                    klient = new Klienci(null, interpaperXLS.getKlientnazwa(), interpaperXLS.getKlientnazwa(), interpaperXLS.getNip(), interpaperXLS.getKlientkod(), interpaperXLS.getKlientmiasto(), interpaperXLS.getKlientulica(), interpaperXLS.getKlientdom(), interpaperXLS.getKlientlokal());
                    klient.setKrajnazwa(interpaperXLS.getKlientpaństwo());
                    klient.setKrajkod(PanstwaMap.getWykazPanstwSX().get(klient.getKrajnazwa()));
                    if (klient.getNip()!=null && klient.getNip().length()>5) {
                        if (!klient.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                            klienciDAO.create(klient);
                        }
                        znalezieni.put(interpaperXLS.getKontrahent(), klient);
                    }
                }
            }
            //sa dwie opcje moze nie znalesc po nipoie polskiego i te bez nipu
            if (klient==null) {
                klient = new Klienci(null, interpaperXLS.getKlientnazwa(), interpaperXLS.getKlientnazwa(), null, interpaperXLS.getKlientkod(), interpaperXLS.getKlientmiasto(), interpaperXLS.getKlientulica(), interpaperXLS.getKlientdom(), interpaperXLS.getKlientlokal());
                klient.setKrajnazwa(interpaperXLS.getKlientpaństwo());
                klient.setKrajkod(PanstwaMap.getWykazPanstwSX().get(klient.getKrajnazwa()));
                znalezieni.put(interpaperXLS.getKontrahent(), klient);
            }
            if (klient.getKrajnazwa()==null) {
                String nip = klient.getNip();
                String kod = klient.getKodpocztowy();
                if (nip.length()==10 && kod.contains("-")) {
                    klient.setKrajnazwa("Polska");
                    klient.setKrajkod("PL");
                }
                klienciDAO.edit(klient);
            }
        } catch (Exception e) {
            E.e(e);
        }
        return klient;
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
    
    private static Evewidencja pobierzewidencje(KlientJPK klientJPK) {
        return null;
    }
    private KlientJPK tworzobiektAmazonNowy(CSVRecord row, String wybortrans) {
        KlientJPK klientJPK = new KlientJPK();
        String rodzajtransakcji = row.get("TRANSACTION_TYPE");
        String serial = row.get("TRANSACTION_EVENT_ID");
        try {
            if (wybortrans.equals("FC_TRANSFER")||wybortrans.equals(rodzajtransakcji)) {
                klientJPK.setDowodSprzedazy(rodzajtransakcji);
                if (serial.equals("203-1765216-3856314")) {
                    System.out.println("");
                }
                klientJPK.setSerial(row.get("TRANSACTION_EVENT_ID"));
                String data = row.get("TRANSACTION_DEPART_DATE");
                String data2 = row.get("TRANSACTION_DEPART_DATE");
                if (data2.equals("")) {
                    data2 = data;
                }
                klientJPK.setDataSprzedazy(Data.zmienkolejnosc(data));
                klientJPK.setDataWystawienia(Data.zmienkolejnosc(data2));
                String krajcdocelowy = row.get("SALE_ARRIVAL_COUNTRY");
                String krajwysylki = row.get("SALE_DEPART_COUNTRY");
                String stawka = row.get("PRICE_OF_ITEMS_VAT_RATE_PERCENT").equals("") ? "0.0":row.get("PRICE_OF_ITEMS_VAT_RATE_PERCENT");
                double stawkavat = Double.valueOf(stawka);
                klientJPK.setStawkavat(stawkavat);
                String opodatkowanie = row.get("TAXABLE_JURISDICTION");
                klientJPK.setJurysdykcja(opodatkowanie);
                if (opodatkowanie.equals("POLAND")) {
                    klientJPK.setEwidencja(pobierzewidencje(klientJPK));
                }
                String nip = row.get("BUYER_VAT_NUMBER");
                klientJPK.setKodKrajuNadania(krajwysylki);
                klientJPK.setKodKrajuDoreczenia(krajcdocelowy);
                klientJPK.setNrKontrahenta(nip);
                klientJPK.setRok(wpisView.getRokWpisuSt());
                klientJPK.setMc(wpisView.getMiesiacWpisu());
                String waluta = row.get("TRANSACTION_CURRENCY_CODE");
                klientJPK.setWaluta(waluta);
                double brutto = format.F.kwota(row.get("TOTAL_ACTIVITY_VALUE_AMT_VAT_INCL"));
                klientJPK.setNettowaluta(format.F.kwota(row.get("TOTAL_ACTIVITY_VALUE_AMT_VAT_EXCL")));
                klientJPK.setVatwaluta(Z.z(brutto -klientJPK.getNettowaluta()));
                double kurs = pobierzkurs(klientJPK.getDataSprzedazy(), waluta);
                klientJPK.setKurs(kurs);
                klientJPK.setNetto(Z.z(klientJPK.getNettowaluta()*kurs));
                klientJPK.setVat(Z.z(klientJPK.getVatwaluta()*kurs));
                klientJPK.setWdt(true);
            } else if (wybortrans.equals("WSZYSTKIE")||wybortrans.equals(rodzajtransakcji)) {
                klientJPK.setDowodSprzedazy(rodzajtransakcji);
                if (serial.equals("203-1765216-3856314")) {
                    System.out.println("");
                }
                klientJPK.setSerial(row.get("TRANSACTION_EVENT_ID"));
                String data = row.get("TAX_CALCULATION_DATE");
                String data2 = row.get("TRANSACTION_DEPART_DATE");
                if (data2.equals("")) {
                    data2 = data;
                }
                klientJPK.setDataSprzedazy(Data.zmienkolejnosc(data));
                klientJPK.setDataWystawienia(Data.zmienkolejnosc(data2));
                String krajcdocelowy = row.get("SALE_ARRIVAL_COUNTRY");
                String krajwysylki = row.get("SALE_DEPART_COUNTRY");
                String stawka = row.get("PRICE_OF_ITEMS_VAT_RATE_PERCENT").equals("") ? "0.0":row.get("PRICE_OF_ITEMS_VAT_RATE_PERCENT");
                double stawkavat = Double.valueOf(stawka);
                klientJPK.setStawkavat(stawkavat);
                String opodatkowanie = row.get("TAXABLE_JURISDICTION");
                klientJPK.setJurysdykcja(opodatkowanie);
                if (opodatkowanie.equals("POLAND")) {
                    klientJPK.setEwidencja(pobierzewidencje(klientJPK));
                }
                String nip = row.get("BUYER_VAT_NUMBER");
                klientJPK.setKodKrajuNadania(krajwysylki);
                klientJPK.setKodKrajuDoreczenia(krajcdocelowy);
                klientJPK.setNrKontrahenta(nip);
                klientJPK.setRok(wpisView.getRokWpisuSt());
                klientJPK.setMc(wpisView.getMiesiacWpisu());
                String waluta = row.get("TRANSACTION_CURRENCY_CODE");
                klientJPK.setWaluta(waluta);
                double brutto = format.F.kwota(row.get("TOTAL_ACTIVITY_VALUE_AMT_VAT_INCL"));
                klientJPK.setNettowaluta(format.F.kwota(row.get("TOTAL_ACTIVITY_VALUE_AMT_VAT_EXCL")));
                klientJPK.setVatwaluta(Z.z(brutto -klientJPK.getNettowaluta()));
                double kurs = pobierzkurs(klientJPK.getDataSprzedazy(), waluta);
                klientJPK.setKurs(kurs);
                klientJPK.setNetto(Z.z(klientJPK.getNettowaluta()*kurs));
                klientJPK.setVat(Z.z(klientJPK.getVatwaluta()*kurs));
                if (klientJPK.getNrKontrahenta().length()>0&&Z.z(klientJPK.getVat())==0.0) {
                    klientJPK.setWdt(true);
                }
                //System.out.println(klientJPK.getSerial());
            } else {
                klientJPK = null;
            }
        } catch (Exception e) {
        }
        return klientJPK;
    }

    private List<EVatwpisKJPK> tworzewidencjeVAT(Evewidencja evewidencja, KlientJPK tmpzwrot) {
        List<EVatwpisKJPK> zwrot = new ArrayList<>();
        EVatwpisKJPK nowa = new EVatwpisKJPK();
        nowa.setKlientJPK(tmpzwrot);
        nowa.setEwidencja(evewidencja);
        nowa.setRokEw(tmpzwrot.getRok());
        nowa.setMcEw(tmpzwrot.getMc());
        nowa.setNetto(tmpzwrot.getNetto());
        nowa.setVat(tmpzwrot.getVat());
        zwrot.add(nowa);
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

    public List<KlientJPK> getListafk() {
        return listafk;
    }

    public void setListafk(List<KlientJPK> listafk) {
        this.listafk = listafk;
    }

    public String getWybortransakcji() {
        return wybortransakcji;
    }

    public void setWybortransakcji(String wybortransakcji) {
        this.wybortransakcji = wybortransakcji;
    }


    
     public static void main(String[] args) {
        try {
            String filename = "D://amazonnowy.csv";
            FileInputStream file = new FileInputStream(new File(filename));
            Iterable<CSVRecord> recordss = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new InputStreamReader(file, Charset.forName("windows-1252")));
            List<KlientJPK> lista = new ArrayList<>();
            int i = 1;
            String rok = "2021";
            String mc = "01";
            for (CSVRecord row : recordss) {
                KlientJPK klientJPK = new KlientJPK();
        String rodzajtransakcji = row.get("TRANSACTION_TYPE");
        String serial = row.get("TRANSACTION_EVENT_ID");
        try {
            if ((rodzajtransakcji.equals("SALE") || rodzajtransakcji.equals("REFUND"))&&!serial.equals("")) {
                klientJPK.setDowodSprzedazy(rodzajtransakcji);
                if (serial.equals("203-1765216-3856314")) {
                    System.out.println("");
                }
                klientJPK.setSerial(row.get("TRANSACTION_EVENT_ID"));
                String data = row.get("TAX_CALCULATION_DATE");
                String data2 = row.get("TRANSACTION_DEPART_DATE");
                if (data2.equals("")) {
                    data2 = data;
                }
                klientJPK.setDataSprzedazy(Data.zmienkolejnosc(data));
                klientJPK.setDataWystawienia(Data.zmienkolejnosc(data2));
                String krajcdocelowy = row.get("SALE_ARRIVAL_COUNTRY");
                String krajwysylki = row.get("SALE_DEPART_COUNTRY");
                String stawka = row.get("PRICE_OF_ITEMS_VAT_RATE_PERCENT").equals("") ? "0.0":row.get("PRICE_OF_ITEMS_VAT_RATE_PERCENT");
                double stawkavat = Double.valueOf(stawka);
                klientJPK.setStawkavat(stawkavat);
                String opodatkowanie = row.get("TAXABLE_JURISDICTION");
                klientJPK.setJurysdykcja(opodatkowanie);
                if (opodatkowanie.equals("POLAND")) {
                    klientJPK.setEwidencja(pobierzewidencje(klientJPK));
                }
                String nip = row.get("BUYER_VAT_NUMBER");
                klientJPK.setKodKrajuNadania(krajwysylki);
                klientJPK.setKodKrajuDoreczenia(krajcdocelowy);
                klientJPK.setNrKontrahenta(nip);
                klientJPK.setRok("2021");
                klientJPK.setMc("01");
                klientJPK.setWaluta(row.get("TRANSACTION_CURRENCY_CODE"));
                double brutto = format.F.kwota(row.get("TOTAL_ACTIVITY_VALUE_AMT_VAT_INCL"));
                klientJPK.setNettowaluta(format.F.kwota(row.get("TOTAL_ACTIVITY_VALUE_AMT_VAT_EXCL")));
                klientJPK.setVatwaluta(Z.z(brutto -klientJPK.getNettowaluta()));
                //System.out.println(klientJPK.getSerial());
            }
        } catch (Exception e) {
        }
            }
            file.close();
            System.out.println("");
        } catch (Exception e) {
            E.e(e);
        }
    }
    
     private double pobierzkurs(String data, String waluta) {
         double zwrot = 0.0;
         DateTime dzienposzukiwany = new DateTime(data);
        //tu sie dodaje tabele do dokumentu :)
        Tabelanbp tabela = TabelaNBPBean.pobierzTabeleNBP(dzienposzukiwany, tabelanbpDAO, waluta);
        zwrot = Z.z6(tabela.getKurssredniPrzelicznik());
        return zwrot;
    }    
    
}
