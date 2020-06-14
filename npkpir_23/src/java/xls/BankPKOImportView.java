/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import beansFK.DialogWpisywanie;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import daoFK.DokDAOfk;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import daoFK.WierszDAO;
import data.Data;
import dedra.Dedraparser;
import entity.Klienci;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersz;
import error.E;
import gus.GUSView;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import msg.Msg;
import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
import org.primefaces.PrimeFaces;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import view.WpisView;import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class BankPKOImportView implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{gUSView}")
    private GUSView gUSView;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private KliencifkDAO kliencifkDAO;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject
    private WierszDAO wierszDAO;
    private byte[] plikinterpaper;
    public  List<ImportBankWiersz> pobranefaktury;
    public  List<ImportBankWiersz> pobranefakturyfilter;
    public  List<ImportBankWiersz> selected;
    private List<Rodzajedok> rodzajedokKlienta;
    private String wiadomoscnieprzypkonta;
    private Rodzajedok rodzajdok;
    private PanelGrid grid1;
    private PanelGrid grid2;
    private PanelGrid grid3;
    private CommandButton generujbutton; 
    private Tabelanbp tabelanbppl;
    private Waluty walutapln;
    private String wyciagdataod;
    private String wyciagdatado;
    private String wyciagnr;
    private String wyciagkonto;
    private String wyciagwaluta;
    private double wyciagbo;
    private double wyciagbz;
    private Konto wplyw;
    private Konto zaplata;
    private Konto prowizja;
    private Konto wyplatakarta;
    private Konto platnosckarta;
    private Konto przelewUS;
    private Konto przelewZUS;
    private Konto przelewGmina;
    private Konto przelewBankBank;
    private Konto konto213;
    private String datakontrol;
    private List<Wiersz> wierszezmiesiaca;
 //typ transakcji
        //1 wpływ faktura 201,203
        //2 zapłata faktura 202,204
        //3 prowizja - 404-3
        //4 wypłata karta - 149-1
        //5 płatnośc karta - 149-3
        //6 US - 222
        //7 ZUS - 231
        //8 Gmina - 226
        //9 bank-bank - 149-2
    
    
    @PostConstruct
    private void init() { //E.m(this);
        rodzajedokKlienta = rodzajedokDAO.findListaPodatnikRO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        for (Iterator<Rodzajedok> it = rodzajedokKlienta.iterator(); it.hasNext();) {
            Rodzajedok p = it.next();
            if (!p.getSkrot().startsWith("WB")) {
                it.remove();
            } else {
                if (p.getKontorozrachunkowe()==null && p.getKontoRZiS()==null) {
                    wiadomoscnieprzypkonta = "Istnieją dokumenty z VAT bez przyporządkowanych kont. Import będzie nieprawidłowy";
                }
            }
        } 
        wyciagwaluta="PLN";
        tabelanbppl = tabelanbpDAO.findByTabelaPLN();
        walutapln = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        wplyw = kontoDAO.findKonto("213", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        zaplata = kontoDAO.findKonto("213", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        prowizja = kontoDAO.findKonto("404-3", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        wyplatakarta = kontoDAO.findKonto("149-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        platnosckarta = kontoDAO.findKonto("149-3", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        przelewUS = kontoDAO.findKonto("222", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        przelewZUS = kontoDAO.findKonto("231", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        przelewGmina = kontoDAO.findKonto("226", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        przelewBankBank = kontoDAO.findKonto("149-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        konto213 = kontoDAO.findKonto("213", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
    }
    
    
    public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            if (extension.equals("xml")) {
                String filename = uploadedFile.getFileName();
                plikinterpaper = uploadedFile.getContent();
                PrimeFaces.current().ajax().update("panelplik");
                grid1.setRendered(true);
                Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
            } else {
                Msg.msg("e","Niewłaściwy typ pliku");
            }
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
    }
    
    public void resetuj() {
        rodzajdok = null;
        pobranefaktury = null;
        wyciagnr = null;
        wyciagdataod = null;
        wyciagdatado = null;
        wyciagkonto = null;
        wyciagwaluta = null;
        wyciagbo = 0.0;
        wyciagbz = 0.0;
        plikinterpaper = null;
        grid1.setRendered(false);
        grid2.setRendered(false);
        grid3.setRendered(false);
    }
    
    
    
    public void importujdok() {
        try {
            List<Klienci> k = klienciDAO.findAll();
            InputStream file = new ByteArrayInputStream(plikinterpaper);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            try {
                NodeList nList1 = doc.getElementsByTagName("Stmt");
                wyciagnr = pT(nList1.item(0), "Id");;
                wyciagdataod = pT(nList1.item(0), "FrDtTm");
                wyciagdatado = pT(nList1.item(0), "ToDtTm");
                if (wyciagdatado!=null) {
                    wyciagdatado = wyciagdatado.substring(0, 10);
                }
                wyciagkonto = pT(nList1.item(0), "IBAN");
                wyciagwaluta = pT(nList1.item(0), "Ccy");
                wyciagbo = Double.valueOf(pT(nList1.item(0), "Amt", 0));
                wyciagbz = Double.valueOf(pT(nList1.item(0), "Amt", 1));
            } catch (Exception e){
                wyciagkonto = "11111";
            }
            NodeList nList = doc.getElementsByTagName("Ntry");
            pobranefaktury = new ArrayList<>();
            System.out.println("----------------------------");
            int len = nList.getLength();
                for (int temp = 0; temp < len; temp++) {
                    Node nNode = nList.item(temp);
                    if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                        try {
                            ImportBankWiersz p = new ImportBankWiersz();
                            p.setNr(temp+1);
                            String iban = pE(nNode, "Id") == null ? "brak" : pTFC(nNode, "Id");
                            p.setIBAN(iban);
                            String elt1 = pT(nNode, "Amt");
                            p.setKwota(Double.valueOf(elt1));
                            elt1 = pE(nNode, "Amt").getAttribute("Ccy");
                            p.setWaluta(elt1);
                            elt1 = pT(nNode, "CdtDbtInd");
                            p.setWnma(elt1.equals("DBIT")?"Ma":"Wn");
                            elt1 = pTFC(nNode, "BookgDt");
                            p.setDatatransakcji(elt1.substring(0,10));
                            elt1 = pTFC(nNode, "ValDt");
                            p.setDatawaluty(elt1.substring(0,10));
                            elt1 = pT(nNode, "Ustrd");
                            p.setOpistransakcji(elt1);
                            elt1 = pE(nNode, "Nm") == null ? "brak" : pT(nNode, "Nm");
                            p.setKontrahent(elt1);
                            elt1 = pE(nNode, "Ctry") == null ? "brak" : pT(nNode, "Ctry");
                            p.setKontrahentakraj(elt1);
                            elt1 = pE(nNode, "AdrLine") == null ? "brak" : pT(nNode, "AdrLine");
                            p.setKontrahentaadres(elt1);
                            elt1 = pT(nNode, "TxId");
                            p.setNrtransakji(elt1);
                            p.setTyptransakcji(oblicztyptransakcji(p));
                            pobranefaktury.add(p);
                        } catch (Exception e) {
                            E.e(e);
                        }
                    }
                }
            if (pobranefaktury!=null && !pobranefaktury.isEmpty()) {
                String mc = Data.getMc(pobranefaktury.get(0).getDatatransakcji());
                if (!mc.equals(wpisView.getMiesiacWpisu())) {
                    Msg.msg("e","Importowany wyciąg nalezy do innego miesiąca. Proszę zmienić miesiąc.");
                } else {
                    generujbutton.setRendered(true);
                }
                wierszezmiesiaca = wierszDAO.pobierzWierszeMcDok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), rodzajdok.getSkrotNazwyDok());
                if (wierszezmiesiaca!=null && wierszezmiesiaca.size()>0) {
                    for (ImportBankWiersz p : pobranefaktury) {
                        if (sprawdzduplikat(p,wierszezmiesiaca)) {
                            p.setJuzzaksiegowany(true);
                        }
                    }
                }
            }
            
            grid3.setRendered(true);
            Msg.msg("Pobrano wszystkie dane");
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd przy pobieraniu danych");
        }
//        for (InterpaperXLS p : pobranefaktury) {
//           generowanieDokumentu(p);
//        }
    }
    
     //typ transakcji
        //1 wpływ faktura 201,203
        //2 zapłata faktura 202,204
        //3 prowizja - 404-3
        //4 wypłata karta - 149-1
        //5 płatnośc karta - 149-3
        //6 US - 222
        //7 ZUS - 231
        //8 Gmina - 226
        //9 bank-bank - 149-2
    private int oblicztyptransakcji(ImportBankWiersz p) {
        int zwrot = 0;
        if (p.getKontrahent().equals("NOTPROVIDED")) {
            zwrot = 3;
        } else if (p.getKontrahent().toLowerCase().contains("INTERPAPER SP Z O O SK")) {
            zwrot = 8;
        } else if (p.getKontrahent().toLowerCase().contains("Gmina")) {
            zwrot = 8;
        } else if (p.getKontrahent().toLowerCase().contains("Zakład Ubezpieczeń Społecznych")) {
            zwrot = 7;
        } else if (p.getKontrahent().toLowerCase().contains("urząd")) {
            zwrot = 6;
        } else if (p.getOpistransakcji().equals("WYPŁATA KARTĄ")) {
            zwrot = 4;
        } else if (p.getOpistransakcji().equals("TRANSAKCJA KARTĄ PŁATNICZĄ")) {
            zwrot = 5;
        } else if (p.getWnma().equals("Wn")) {
            zwrot = 1;
        } else if (p.getWnma().equals("Ma")) {
            zwrot = 2;
        }
        return zwrot;
    }
    public void generuj() {
        if (pobranefaktury !=null && pobranefaktury.size()>0) {
            datakontrol = null;
            List<Klienci> k = klienciDAO.findAll();
            int ile = 1;
            int duplikaty = 0;
            while (pobranefaktury!=null && pobranefaktury.size() >0) {
                int czyduplikat = generowanieDokumentu(k, ile);
                if (czyduplikat==1) {
                    duplikaty++;
                    ile++;
                } else {
                    ile++;
                }
            }
            int iloscdok = ile-duplikaty-1;
            Msg.msg("Wygenerowano "+iloscdok+" wyciągów bankowych");
            if (duplikaty>0) {
                Msg.msg("e", "Ilość duplikatów "+duplikaty);
            }
        } else {
            Msg.msg("e", "Błąd! Lista danych źrdółowych jest pusta");
        }
    }
    
     public int generowanieDokumentu(List<Klienci> k, int i) {
        int zwrot = 0;
        try {
            Dokfk dokument = stworznowydokument(k, i);
            try {
                if (dokument!=null) {
                    dokument.setImportowany(true);
                    dokDAOfk.dodaj(dokument);
                } else {
                    zwrot++;
                }
            } catch (Exception e) {
                Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu "+rodzajdok);
            }
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
     
      private Dokfk stworznowydokument(List<Klienci> klienci, int i) {
        int numerkolejny = ImportBean.oblicznumerkolejny(rodzajdok.getSkrotNazwyDok(), dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Dokfk nd = new Dokfk(numerkolejny, wpisView.getRokWpisuSt());
        nd.setKontr(ImportBean.ustawkontrahenta(wpisView.getPodatnikObiekt().getNip(), wpisView.getPodatnikWpisu(), klienci, gUSView, klienciDAO));
        ImportBean.ustawnumerwlasny(nd, "wyciag nr "+wyciagnr+"/"+i);
        nd.setOpisdokfk("rozliczenie wyciągu za "+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisuSt());
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        nd.setSeriadokfk(rodzajdok.getSkrot());
        nd.setRodzajedok(rodzajdok);
        nd.setEwidencjaVAT(null);
        nd.setSaldopoczatkowe(wyciagbo);
        if (wyciagwaluta.equals("PLN")) {
            nd.setTabelanbp(tabelanbppl);
            nd.setWalutadokumentu(walutapln);
        } else {
            nd.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty(wyciagwaluta));
        }
        Dokfk juzjest = dokDAOfk.findDokfkObjKontrahent(nd);
        if (juzjest!=null) {
            nd = null;
            usunduplikat(juzjest);
        } else {
            ustawwiersze(nd);
            if (nd.getListawierszy()!=null && nd.getListawierszy().size()>0) {
                ustawdaty(nd);
                nd.setImportowany(true);
                nd.setWprowadzil(wpisView.getUzer().getLogin());
                nd.przeliczKwotyWierszaDoSumyDokumentu();
                rozliczsaldoWBRK(nd);
            } else {
                nd=null;
            }
        }
        return nd;
    }
      
    
    
    private void ustawdaty(Dokfk nd) {
        nd.setDatadokumentu(wyciagdatado);
        nd.setDataoperacji(wyciagdatado);
        nd.setDatawplywu(wyciagdatado);
        nd.setDatawystawienia(wyciagdatado);
        nd.setDataujecia(new Date());
        nd.setMiesiac(wpisView.getMiesiacWpisu());
        nd.setRok(wpisView.getRokWpisuSt());
        nd.setVatM(wyciagdatado.split("-")[1]);
        nd.setVatR(wyciagdatado.split("-")[0]);
    }
    
    
      
    private void ustawwiersze(Dokfk nd) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        int lpwiersza = 1;
        for (Iterator<ImportBankWiersz> it = pobranefaktury.iterator(); it.hasNext();) {
            ImportBankWiersz p = it.next();
            if (datakontrol==null) {
                datakontrol = p.getDatatransakcji();
            } else {
                if (!datakontrol.equals(p.getDatatransakcji())) {
                    datakontrol = p.getDatatransakcji();
                    break;
                }
            }
            if (p.isJuzzaksiegowany()==false) {
                wyciagdatado = p.getDatatransakcji();
                Konto kontown = p.getWnma().equals("Wn") ? rodzajdok.getKontorozrachunkowe() : ustawkonto(p);
                Konto kontoma = p.getWnma().equals("Ma") ? rodzajdok.getKontorozrachunkowe() :ustawkonto(p);
                nd.getListawierszy().add(przygotujwierszNetto(lpwiersza, nd, p, kontown, kontoma));
                lpwiersza++;
                it.remove();
            } else {
                it.remove();
            }
        }

    }

    private Wiersz przygotujwierszNetto(int lpwiersza,Dokfk nd, ImportBankWiersz ImportBankWiersz, Konto kontown, Konto kontoma) {
        Wiersz w = new Wiersz(lpwiersza, nd, 0);
        w.setDataWalutyWiersza(Data.getDzien(ImportBankWiersz.getDatatransakcji()));
        uzupelnijwiersz(w, nd, ImportBankWiersz);
        w.setOpisWiersza(zrobopiswiersza(ImportBankWiersz));
        StronaWiersza strwn = new StronaWiersza(w, "Wn", ImportBankWiersz.getKwota(), kontown);
        StronaWiersza strma = new StronaWiersza(w, "Ma", ImportBankWiersz.getKwota(), kontoma);
        strwn.setKwotaPLN(zrobpln(w,ImportBankWiersz));
        strma.setKwotaPLN(zrobpln(w,ImportBankWiersz));
        w.setStronaWn(strwn);
        w.setStronaMa(strma);
        return w;
    }
    //typ transakcji
        //1 wpływ faktura
        //2 zapłata faktura
        //3 prowizja
        //4 wypłata karta - 149-1
        //5 płatnośc karta - 149-3
        //6 US
        //7 ZUS
        //8 Gmina
        //9 bank-bank
    private Konto ustawkonto(ImportBankWiersz p) {
        Konto zwrot = null;
        int numer = p.getTyptransakcji();
        switch (numer) {
            case 1:
                zwrot = konto213;
                break;
            case 2:
                zwrot = konto213;
                break;
            case 3:
                zwrot = prowizja;
                break;
            case 4:
                zwrot = wyplatakarta;
                break;
            case 5:
                zwrot = platnosckarta;
                break;
            case 6:
                zwrot = przelewUS;
                break;
            case 7:
                zwrot = przelewZUS;
                break;
            case 8:
                zwrot = przelewGmina;
                break;
            case 9:
                zwrot = przelewBankBank;
                break;
        }
        return zwrot;
    }    
        
    private void uzupelnijwiersz(Wiersz w, Dokfk nd, ImportBankWiersz ImportBankWiersz) {
        if (ImportBankWiersz.getWaluta().equals("PLN")) {
            w.setTabelanbp(tabelanbppl);
        } else {
            DateTime dzienposzukiwany = new DateTime(ImportBankWiersz.getDatawaluty());
            boolean znaleziono = false;
            int zabezpieczenie = 0;
            while (!znaleziono && (zabezpieczenie < 365)) {
                dzienposzukiwany = dzienposzukiwany.minusDays(1);
                String doprzekazania = dzienposzukiwany.toString("yyyy-MM-dd");
                Tabelanbp tabelanbppobrana = tabelanbpDAO.findByDateWaluta(doprzekazania, ImportBankWiersz.getWaluta());
                if (tabelanbppobrana instanceof Tabelanbp) {
                    znaleziono = true;
                    w.setTabelanbp(tabelanbppobrana);
                    break;
                }
                zabezpieczenie++;
            }
        }
        nd.setWalutadokumentu(w.getTabelanbp().getWaluta());
        w.setIban(ImportBankWiersz.getIBAN());
        w.setDokfk(nd);
        w.setLpmacierzystego(0);
        w.setDataksiegowania(nd.getDatawplywu());
    }
    
     private double zrobpln(Wiersz w, ImportBankWiersz ImportBankWiersz) {
        double zwrot = ImportBankWiersz.getKwota();
        if (!w.getWalutaWiersz().equals("PLN")) {
            zwrot = Z.z(ImportBankWiersz.getKwota()*w.getTabelanbp().getKurssredniPrzelicznik());
        }
        return zwrot;
    }

      public void rozliczsaldoWBRK(Dokfk selected) {
        Konto kontorozrachunkowe = selected.getRodzajedok().getKontorozrachunkowe();
        if (selected.getRodzajedok().getKategoriadokumentu() == 0 && kontorozrachunkowe != null) {
            int koncowyindex = selected.getListawierszy().size();
            for (int i = 0; i < koncowyindex; i++) {
                DialogWpisywanie.rozliczPojedynczeSaldoWBRK(selected, i, kontorozrachunkowe);
            }
        }
        selected.setSaldokoncowe(selected.getListawierszy().get(selected.getListawierszy().size() - 1).getSaldoWBRK());
        wyciagbo = selected.getSaldokoncowe();
    }
    
   private String zrobopiswiersza(ImportBankWiersz ImportBankWiersz) {
        String opis = ImportBankWiersz.getOpistransakcji().toLowerCase(new Locale("pl"));
        String kontr = ImportBankWiersz.getKontrahent();
        if (kontr.equals("NOTPROVIDED")) {
            kontr="";
        } else {
            kontr = kontr.replaceAll("\\s{2,}", " ").trim();
        }
        if (opis.contains("TRANSAKCJA KARTĄ PŁATNICZĄ")) {
            kontr="";
            opis = opis.replace("TRANSAKCJA KARTĄ PŁATNICZĄ", "transakcja kartą");
        }
        if (opis.contains("WYPŁATA KARTĄ")) {
            kontr="";
            opis = opis.replace("WYPŁATA KARTĄ", "wypłatya kartą");
        }
        
        opis = opis.replaceAll("\\s{2,}", " ").trim();
        return kontr+" "+opis;
    }
    
    private boolean sprawdzduplikat(ImportBankWiersz p, List<Wiersz> wierszezmiesiaca) {
        boolean zwrot = false;
        for (Wiersz r : wierszezmiesiaca) {
            if (r.getIban()!=null && r.getIban().equals(p.getIBAN())) {
                if (r.getDataWalutyWiersza().equals(Data.getDzien(p.getDatatransakcji()))){
                    if (r.getKwotaWn()==p.getKwota() || r.getKwotaMa()==p.getKwota()) {
                        zwrot = true;
                    }
                }
            }
        }
        return zwrot;
    }
   
     private void usunduplikat(Dokfk juzjest) {
        for (Wiersz p : juzjest.getListawierszy()) {
            for (Iterator<ImportBankWiersz> it = pobranefaktury.iterator(); it.hasNext();) {
                ImportBankWiersz s = it.next();
                if (p.getIban()!=null && p.getIban().equals(s.getIBAN())) {
                    if (p.getDataWalutyWiersza().equals(Data.getDzien(s.getDatatransakcji()))){
                        if (p.getKwotaWn()==s.getKwota() || p.getKwotaMa()==s.getKwota()) {
                            it.remove();
                        }
                    }
                }
            }
        }
    }
    
    public void grid2pokaz() {
        grid2.setRendered(true);
    }
     
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<ImportBankWiersz> getPobranefaktury() {
        return pobranefaktury;
    }

    public void setPobranefaktury(List<ImportBankWiersz> pobranefaktury) {
        this.pobranefaktury = pobranefaktury;
    }

    public List<ImportBankWiersz> getSelected() {
        return selected;
    }

    public void setSelected(List<ImportBankWiersz> selected) {
        this.selected = selected;
    }

  

    public List<ImportBankWiersz> getPobranefakturyfilter() {
        return pobranefakturyfilter;
    }

    public void setPobranefakturyfilter(List<ImportBankWiersz> pobranefakturyfilter) {
        this.pobranefakturyfilter = pobranefakturyfilter;
    }

    public GUSView getgUSView() {
        return gUSView;
    }

    public void setgUSView(GUSView gUSView) {
        this.gUSView = gUSView;
    }

    public List<Rodzajedok> getRodzajedokKlienta() {
        return rodzajedokKlienta;
    }

    public void setRodzajedokKlienta(List<Rodzajedok> rodzajedokKlienta) {
        this.rodzajedokKlienta = rodzajedokKlienta;
    }

    public String getWiadomoscnieprzypkonta() {
        return wiadomoscnieprzypkonta;
    }

    public void setWiadomoscnieprzypkonta(String wiadomoscnieprzypkonta) {
        this.wiadomoscnieprzypkonta = wiadomoscnieprzypkonta;
    }

    public Rodzajedok getRodzajdok() {
        return rodzajdok;
    }

    public void setRodzajdok(Rodzajedok rodzajdok) {
        this.rodzajdok = rodzajdok;
    }


    public PanelGrid getGrid1() {
        return grid1;
    }

    public void setGrid1(PanelGrid grid1) {
        this.grid1 = grid1;
    }

    public PanelGrid getGrid2() {
        return grid2;
    }

    public void setGrid2(PanelGrid grid2) {
        this.grid2 = grid2;
    }

    public PanelGrid getGrid3() {
        return grid3;
    }

    public void setGrid3(PanelGrid grid3) {
        this.grid3 = grid3;
    }

    public CommandButton getGenerujbutton() {
        return generujbutton;
    }

    public void setGenerujbutton(CommandButton generujbutton) {
        this.generujbutton = generujbutton;
    }

    public String getWyciagdataod() {
        return wyciagdataod;
    }

    public void setWyciagdataod(String wyciagdataod) {
        this.wyciagdataod = wyciagdataod;
    }

    public String getWyciagdatado() {
        return wyciagdatado;
    }

    public void setWyciagdatado(String wyciagdatado) {
        this.wyciagdatado = wyciagdatado;
    }

    public String getWyciagnr() {
        return wyciagnr;
    }

    public void setWyciagnr(String wyciagnr) {
        this.wyciagnr = wyciagnr;
    }

    public String getWyciagkonto() {
        return wyciagkonto;
    }

    public void setWyciagkonto(String wyciagkonto) {
        this.wyciagkonto = wyciagkonto;
    }

    public double getWyciagbo() {
        return wyciagbo;
    }

    public void setWyciagbo(double wyciagbo) {
        this.wyciagbo = wyciagbo;
    }

    public double getWyciagbz() {
        return wyciagbz;
    }

    public void setWyciagbz(double wyciagbz) {
        this.wyciagbz = wyciagbz;
    }

    public String getWyciagwaluta() {
        return wyciagwaluta;
    }

    public void setWyciagwaluta(String wyciagwaluta) {
        this.wyciagwaluta = wyciagwaluta;
    }

 public static Element pE(Node nNode1, String nazwa) {
     Element eElement = (Element) nNode1;
     return (Element) eElement.getElementsByTagName(nazwa).item(0);
 }
 
 public static String pT(Node nNode1, String nazwa) {
     Element eElement = (Element) nNode1;
     return ((Element) eElement.getElementsByTagName(nazwa).item(0)).getTextContent();
 }
 public static String pT(Node nNode1, String nazwa, int pozycja) {
     Element eElement = (Element) nNode1;
     return ((Element) eElement.getElementsByTagName(nazwa).item(pozycja)).getTextContent();
 }
 
 public static String pTFC(Node nNode1, String nazwa) {
     Element eElement = (Element) nNode1;
     return ((Element) eElement.getElementsByTagName(nazwa).item(0)).getFirstChild().getNextSibling().getTextContent();
 }
    
//public static void main(String[] args) throws SAXException, IOException {
//        try {
//            File fXmlFile = new File("e:\\wgo.xml");
//            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
//            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
//            Document doc = dBuilder.parse(fXmlFile);
//            doc.getDocumentElement().normalize();
//            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
//            NodeList nList1 = doc.getElementsByTagName("account");
//            String iban = pT(nList1.item(0), "iban");
//            String waluta = pT(nList1.item(0), "currency");
//            nList1 = doc.getElementsByTagName("stmt");
//            String nrwyciagu = pT(nList1.item(0), "stmt-no");;
//            String dataod = pT(nList1.item(0), "begin");
//            String datado = pT(nList1.item(0), "end");
//            String bo = pT(nList1.item(0), "begin-value");
//            String bz = pT(nList1.item(0), "end-value");
//            System.out.println("----------------------------");
//            nList1 = doc.getElementsByTagName("trn");
//            System.out.println("----------------------------");
//            int len = nList1.getLength();
//                for (int temp = 0; temp < len; temp++) {
//                    Node nNode1 = nList1.item(temp);
//                    if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
//                        try {
//                            Element eElement = (Element) nNode1;
//                            String elt = pT(nNode1, "trn-code") == null ? "brak" : pT(nNode1, "trn-code");
//                            System.out.println("trn-code : " + elt);
//                            String elt1 = pT(nNode1, "value");
//                            System.out.println("value : " + elt1);
//                            System.out.println("data : " + pT(nNode1, "creat-date"));
//                            System.out.println("data waluty : " + pT(nNode1, "exe-date"));
//                            System.out.println("opis : " + pT(nNode1, "desc-base"));
//                            String elt3 = pE(nNode1, "desc-opt") == null ? "brak" : pT(nNode1, "desc-opt");
//                            System.out.println("odbiorca : " + elt3);
//                        } catch (Exception e) {
//                            E.e(e);
//                        }
//                    }
//                }
//        } catch (ParserConfigurationException ex) {
//            Logger.getLogger(Dedraparser.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }

    public static void main(String[] args) throws SAXException, IOException {
        try {
            String csvFile = "D:\\pkobank.txt";
            List<List<String>> records = new ArrayList<>();
            try (BufferedReader br =  new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "ISO-8859-2"))) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split("\t");
                    records.add(Arrays.asList(values));
                }
            } catch (Exception e) {
            }
            List<ImportBankWiersz> listaswierszy = new ArrayList<>();
            for (Iterator<List<String>> it = records.iterator(); it.hasNext();) {
                List<String> baza = it.next();
                List<String> row = new ArrayList<>();
                ImportBankWiersz x = new ImportBankWiersz();
                x.setDatatransakcji(baza.get(1));
                x.setDatawaluty(baza.get(2));
                x.setIBAN(baza.get(5));//??
                x.setKontrahent(baza.get(4));//??
                x.setKwota(Double.parseDouble(baza.get(10).replace(",",".")));
                x.setWnma("Wn");
                if (!baza.get(11).equals("")) {
                    x.setKwota(-Double.parseDouble(baza.get(11).replace(",",".")));
                    x.setWnma("Ma");
                }
                x.setNrtransakji(baza.get(8));
                x.setOpistransakcji(baza.get(3));
                System.out.println(x.toString());
                //x.setTyptransakcji(oblicztyptransakcji(x));
                listaswierszy.add(x);
                }
            System.out.println("");
        } catch (Exception ex) {
            Logger.getLogger(Dedraparser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    

   
    

    
   
    
   
    
}
