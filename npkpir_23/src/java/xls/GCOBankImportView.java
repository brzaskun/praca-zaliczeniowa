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
import embeddablefk.ImportBankXML;
import embeddablefk.InterpaperXLS;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
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
import org.primefaces.model.UploadedFile;
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
public class GCOBankImportView implements Serializable {
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
    private List<byte[]> plikinterpapermulti;
    public  List<ImportBankXML> pobranefaktury;
    public  List<ImportBankXML> pobranefakturyfilter;
    public  List<ImportBankXML> selected;
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
    private String wyciagnrod;
    private String wyciagnrdo;
    private String wyciagkonto;
    private String wyciagwaluta;
    private double wyciagobrotywn;
    private double wyciagobrotyma;
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
    
    
    
    public void init() { //E.m(this);
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
        plikinterpapermulti = new ArrayList<>();
    }
    
    
    public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            if (extension.equals("csv")) {
                String filename = uploadedFile.getFileName();
                plikinterpapermulti.add(uploadedFile.getContents());
                //plikinterpaper = uploadedFile.getContents();
                PrimeFaces.current().ajax().update("panelplikbankgco");
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
        wyciagnrod = null;
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
            pobranefaktury = new ArrayList<>();
            if (plikinterpapermulti!=null && !plikinterpapermulti.isEmpty()) {
                int nrwyciagu = 0;
                int j = 1;
                for (byte[] plik : plikinterpapermulti) {
                    InputStream file = new ByteArrayInputStream(plik);
                    List<List<String>> records = new ArrayList<>();
                    try (BufferedReader br =  new BufferedReader(new InputStreamReader(file, Charset.forName("UTF-8")))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            String[] values = line.split(";");
                            records.add(Arrays.asList(values));
                        }
                    } catch (Exception e) {
                    }
                    int i = 0;
                    for (Iterator<List<String>> it = records.iterator(); it.hasNext();) {
                        List<String> baza = it.next();
                        List<String> row = new ArrayList<>();
                        if (i==0) {
                            if (nrwyciagu==0) {
                                wyciagnrod = baza.get(0);
                                wyciagdataod = Data.zmienkolejnosc(baza.get(2));
                            }
                            wyciagdatado = Data.zmienkolejnosc(baza.get(1));
                            wyciagkonto = baza.get(5);;
                            wyciagwaluta = baza.get(6);
                            wyciagbz = Double.parseDouble(baza.get(12).replace(",","."));
                            wyciagobrotywn += !baza.get(9).equals("") ? Double.parseDouble(baza.get(9).replace(",",".")) : 0.0;
                            wyciagobrotyma += !baza.get(10).equals("") ? Double.parseDouble(baza.get(10).replace(",",".")) : 0.0;
                            wyciagnrdo = baza.get(0);
                        } else if (i==1) {
                            if (nrwyciagu==0) {
                                wyciagbo = Double.parseDouble(baza.get(12).replace(",","."));
                            }
                        } else {
                            ImportBankXML x = new ImportBankXML();
                            x.setNr(j++);
                            x.setDatatransakcji(Data.zmienkolejnosc(baza.get(1)));
                            x.setDatawaluty(Data.zmienkolejnosc(baza.get(2)));
                            x.setNrwyciagu(baza.get(0));
                            x.setIBAN(baza.get(5));//??
                            x.setKontrahent(baza.get(4));//??
                            if (!baza.get(10).equals("")) {
                                x.setKwota(Double.parseDouble(baza.get(10).replace(",",".")));
                                x.setWnma("Ma");
                            } else if (!baza.get(11).equals("")) {
                                x.setKwota(Double.parseDouble(baza.get(11).replace(",",".")));
                                x.setWnma("Wn");
                            }
                            x.setWaluta(wyciagwaluta);
                            x.setNrtransakji(baza.get(8));
                            x.setOpistransakcji(baza.get(3));
                            x.setTyptransakcji(oblicztyptransakcji(x));
                            pobranefaktury.add(x);
                        }
                        i++;
                    }
                    nrwyciagu++;
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
                    for (ImportBankXML p : pobranefaktury) {
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
    private static int oblicztyptransakcji(ImportBankXML p) {
        int zwrot = 0;
        if (p.getNrtransakji().equals("OPŁATA/PROWIZJA")) {
            zwrot = 3;
        } else if (p.getNrtransakji().equals("PRZELEW ELIXIR - ONLINE") || p.getNrtransakji().equals("PRZELEW NA RACHUNEK W SAN PL - ONLINE")) {
            zwrot = 1;
        } else if (p.getNrtransakji().equals("UZNANIE") || p.getNrtransakji().equals("UZNANIE - PŁATNOŚĆ PODZIELONA")) {
            zwrot = 2;
        } else if (p.getKontrahent().toLowerCase().contains("INTERPAPER SP Z O O SK")) {
            zwrot = 8;
        } else if (p.getKontrahent().toLowerCase().contains("Gmina")) {
            zwrot = 8;
        } else if (p.getKontrahent().toLowerCase().contains("PRZELEW ELIXIR NA RACHUNEK ZUS - ONLINE")) {
            zwrot = 7;
        } else if (p.getKontrahent().toLowerCase().contains("PRZELEW ELIXIR NA RACH. ORGANU PODATK. - ONLINE")) {
            zwrot = 6;
        } else if (p.getNrtransakji().equals("WYPŁATA KARTĄ")) {
            zwrot = 4;
        } else if (p.getNrtransakji().contains("TRANSAKCJA KARTĄ ")) {
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
        ImportBean.ustawnumerwlasny(nd, "wyciag nr "+pobranefaktury.get(0).getNrwyciagu());
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
            ustawwiersze(nd);
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
        for (Iterator<ImportBankXML> it = pobranefaktury.iterator(); it.hasNext();) {
            ImportBankXML p = it.next();
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
                //Konto kontoklienta = ustawkonto(p);
                Konto kontoklienta = null;
                Konto kontown = p.getWnma().equals("Wn") ? rodzajdok.getKontorozrachunkowe() : kontoklienta!=null? kontoklienta : ustawkonto(p);
                Konto kontoma = p.getWnma().equals("Ma") ? rodzajdok.getKontorozrachunkowe() : kontoklienta!=null? kontoklienta : ustawkonto(p);
                nd.getListawierszy().add(przygotujwierszNetto(lpwiersza, nd, p, kontown, kontoma));
                lpwiersza++;
                it.remove();
            } else {
                it.remove();
            }
        }

    }

    private Wiersz przygotujwierszNetto(int lpwiersza,Dokfk nd, ImportBankXML importBankXML, Konto kontown, Konto kontoma) {
        Wiersz w = new Wiersz(lpwiersza, 0);
        w.setDataWalutyWiersza(Data.getDzien(importBankXML.getDatatransakcji()));
        uzupelnijwiersz(w, nd, importBankXML);
        w.setOpisWiersza(zrobopiswiersza(importBankXML));
        StronaWiersza strwn = new StronaWiersza(w, "Wn", importBankXML.getKwota(), kontown);
        StronaWiersza strma = new StronaWiersza(w, "Ma", importBankXML.getKwota(), kontoma);
        strwn.setKwotaPLN(zrobpln(w,importBankXML));
        strma.setKwotaPLN(zrobpln(w,importBankXML));
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
    private Konto ustawkonto(ImportBankXML p) {
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
        
    private void uzupelnijwiersz(Wiersz w, Dokfk nd, ImportBankXML importBankXML) {
        if (importBankXML.getWaluta().equals("PLN")) {
            w.setTabelanbp(tabelanbppl);
        } else {
            DateTime dzienposzukiwany = new DateTime(importBankXML.getDatawaluty());
            boolean znaleziono = false;
            int zabezpieczenie = 0;
            while (!znaleziono && (zabezpieczenie < 365)) {
                dzienposzukiwany = dzienposzukiwany.minusDays(1);
                String doprzekazania = dzienposzukiwany.toString("yyyy-MM-dd");
                Tabelanbp tabelanbppobrana = tabelanbpDAO.findByDateWaluta(doprzekazania, importBankXML.getWaluta());
                if (tabelanbppobrana instanceof Tabelanbp) {
                    znaleziono = true;
                    w.setTabelanbp(tabelanbppobrana);
                    break;
                }
                zabezpieczenie++;
            }
        }
        nd.setWalutadokumentu(w.getTabelanbp().getWaluta());
        w.setIban(importBankXML.getIBAN());
        w.setDokfk(nd);
        w.setLpmacierzystego(0);
        w.setDataksiegowania(nd.getDatawplywu());
    }
    
     private double zrobpln(Wiersz w, ImportBankXML importBankXML) {
        double zwrot = importBankXML.getKwota();
        if (!w.getWalutaWiersz().equals("PLN")) {
            zwrot = Z.z(importBankXML.getKwota()*w.getTabelanbp().getKurssredniPrzelicznik());
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
    
   private String zrobopiswiersza(ImportBankXML importBankXML) {
        String opis = importBankXML.getOpistransakcji().toLowerCase(new Locale("pl"));
        String kontr = importBankXML.getKontrahent();
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
    
    private boolean sprawdzduplikat(ImportBankXML p, List<Wiersz> wierszezmiesiaca) {
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
            for (Iterator<ImportBankXML> it = pobranefaktury.iterator(); it.hasNext();) {
                ImportBankXML s = it.next();
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

    public List<ImportBankXML> getPobranefaktury() {
        return pobranefaktury;
    }

    public void setPobranefaktury(List<ImportBankXML> pobranefaktury) {
        this.pobranefaktury = pobranefaktury;
    }

    public List<ImportBankXML> getSelected() {
        return selected;
    }

    public void setSelected(List<ImportBankXML> selected) {
        this.selected = selected;
    }

  

    public List<ImportBankXML> getPobranefakturyfilter() {
        return pobranefakturyfilter;
    }

    public void setPobranefakturyfilter(List<ImportBankXML> pobranefakturyfilter) {
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

    public String getWyciagnrdo() {
        return wyciagnrdo;
    }

    public void setWyciagnrdo(String wyciagnrdo) {
        this.wyciagnrdo = wyciagnrdo;
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

    public String getWyciagnrod() {
        return wyciagnrod;
    }

    public void setWyciagnrod(String wyciagnrod) {
        this.wyciagnrod = wyciagnrod;
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
    
    public static void main(String[] args) throws SAXException, IOException {
        try {
            Path pathToFile = Paths.get("D:\\gcocsv.csv");
            List<List<String>> records = new ArrayList<>();
            try (BufferedReader br =  Files.newBufferedReader(pathToFile)) {
                String line;
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(";");
                    records.add(Arrays.asList(values));
                }
            } catch (Exception e) {
            }
            int i = 0;
            List<ImportBankXML> listaswierszy = new ArrayList<>();
            for (Iterator<List<String>> it = records.iterator(); it.hasNext();) {
                List<String> baza = it.next();
                List<String> row = new ArrayList<>();
//                for (String r : baza) {
//                    row.add(r.replace("\"", ""));
//                }
                if (i==0) {
                    String wyciagnr = baza.get(0);
                    String wyciagdataod = baza.get(2);
                    String wyciagdatado = baza.get(1);
                    String wyciagkonto = baza.get(5);;
                    String wyciagwaluta = baza.get(6);
                    String wyciagbz = baza.get(12);
                    String wyciagobrotywn = baza.get(10);
                    String wyciagobrotyma = baza.get(11);
                    System.out.println("");
                } else if (i==1) {
                    String wyciagbo = baza.get(12);
                } else {
                    ImportBankXML x = new ImportBankXML();
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
                    x.setTyptransakcji(oblicztyptransakcji(x));
                    listaswierszy.add(x);
                }
                i++;
            }
            System.out.println("");
        } catch (Exception ex) {
            Logger.getLogger(Dedraparser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

   

    

    

   
    

    
   
    
   
    
}
