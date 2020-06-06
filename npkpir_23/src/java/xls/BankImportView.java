/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import beansFK.DialogWpisywanie;
import com.sun.faces.taglib.html_basic.OutputTextTag;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import dao.StronaWierszaDAO;
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
import java.io.IOException;
import java.io.Serializable;
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
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.inject.Inject;
import msg.Msg;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.text.WordUtils;
import org.joda.time.DateTime;
import org.primefaces.PrimeFaces;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import org.xml.sax.SAXException;
import view.WpisView;
import waluty.Z;
/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class BankImportView implements Serializable {
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
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject
    private ImportPlikKonta importPlikKonta;
    private List<byte[]> pobraneplikibytes;
    public  List<ImportBankWiersz> pobranefaktury;
    public  List<ImportBankWiersz> pobranefakturyfilter;
    public  List<ImportBankWiersz> selected;
    private List<Rodzajedok> rodzajedokKlienta;
    private List<ImportowanyPlik> rodzajeimportu;
    private ImportowanyPlik wybranyrodzajimportu;
    private String wiadomoscnieprzypkonta;
    private Rodzajedok rodzajdok;
    private PanelGrid grid0;
    private PanelGrid grid1;
    private PanelGrid grid2;
    private PanelGrid grid3;
    private CommandButton generujbutton; 
    private HtmlOutputText alert1;
    private boolean sabraki;
    private Tabelanbp tabelanbppl;
    private Waluty walutapln;
    private double wyciagbo_transfer;
    private String wyciagdataod;
    private String wyciagdatado;
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
    private Konto konto1494;
    private Konto przychodystowarzyszenie;
   
    private String datakontrol;
    private List<Wiersz> wierszezmiesiaca;
    private Map<String,Konto> ibankonto;
    private String nrwyciagupoprzedni;
    private ImportowanyPlikNaglowek naglowek;
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
                    wiadomoscnieprzypkonta = "Istnieją definicje WB bez przyporządkowanych kont. Import będzie nieprawidłowy!";
                }
            }
        }
        wplyw = kontoDAO.findKonto("213", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        zaplata = kontoDAO.findKonto("213", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        prowizja = kontoDAO.findKonto("404-3", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        wyplatakarta = kontoDAO.findKonto("149-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        platnosckarta = kontoDAO.findKonto("149-3", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        przelewUS = kontoDAO.findKonto("222", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        przelewZUS = kontoDAO.findKonto("231", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        przelewGmina = kontoDAO.findKonto("205", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        przelewBankBank = kontoDAO.findKonto("149-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        konto213 = kontoDAO.findKonto("213", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        konto1494 =  kontoDAO.findKonto("149-4", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        przychodystowarzyszenie =  kontoDAO.findKonto("710-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        rodzajeimportu = zrobrodzajeimportu();
        tabelanbppl = tabelanbpDAO.findByTabelaPLN();
        walutapln = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        pobraneplikibytes = new ArrayList<>();
        importPlikKonta.pobierzkonta(kontoDAO, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        if (!importPlikKonta.isSawszystkiekonta()) {
            Msg.msg("e","Brakuje wszystkich dla importu wyciągu bankowego");
        }
        ibankonto = zrobibankonto();
    }
    
    private Map<String, Konto> zrobibankonto() {
        List<Wiersz> wiersze = wierszDAO.pobierzWierszeMcDokImportIBAN(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Map<String,Konto> lista = new HashMap<>();
        if (wiersze!=null && wiersze.size()>0) {
            for (Wiersz p : wiersze) {
                Konto zwrot = null;
                if (p.getIban()!=null && !p.getIban().equals("")) {
                    if (p.getStronaWn()!=null && !p.getStronaWn().getKonto().getPelnynumer().startsWith("13")) {
                        zwrot = p.getStronaWn().getKonto();
                    } else if (p.getStronaMa()!=null && !p.getStronaMa().getKonto().getPelnynumer().startsWith("13")) {
                        zwrot = p.getStronaMa().getKonto();
                    }
                }
                if (zwrot!=null) {
                    String nowyiban = p.getIban().replace("\"", "").replace("'", "").replace("'", "");
                    lista.put(nowyiban, zwrot);
                }
            }
        }
        return lista;
    }
    
    private List<ImportowanyPlik> zrobrodzajeimportu() {
        List<ImportowanyPlik> zwrot = new ArrayList<>();
        zwrot.add(new ImportowanyPlik("Bank PeKaO SA xml","xml",1));
        zwrot.add(new ImportowanyPlik("Santander csv ;","csv",";",2));
        zwrot.add(new ImportowanyPlik("Mbank csv ;","csv",";",3));
        zwrot.add(new ImportowanyPlik("MT940 csv ;","csv",";",4));
        zwrot.add(new ImportowanyPlik("Bank PKO BP csv ;","csv",5));
        zwrot.add(new ImportowanyPlik("BNP Paribas BP csv ;","csv",6));
        zwrot.add(new ImportowanyPlik("ING xml","xml",7));
        return zwrot;
    }
    
    public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            if (extension.equals("csv")||extension.equals("xml")) {
                String filename = uploadedFile.getFileName();
                pobraneplikibytes.add(uploadedFile.getContents());
                //plikinterpaper = uploadedFile.getContents();
                PrimeFaces.current().ajax().update("panelplikbankall");
                resetuj2();
                grid1.setRendered(true);
                grid2.setRendered(false);
                rodzajdok = null;
                if (pobraneplikibytes!=null && pobraneplikibytes.size()==1) {
                    Msg.msg("Sukces. Wyciąg bankowy " + filename + " został skutecznie załadowany");
                } else {
                    Msg.msg("Sukces. Wyciągi bankowe zostały skutecznie załadowane");
                }
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
        pobranefaktury = new ArrayList();
        pobraneplikibytes = new ArrayList();
        grid1.setRendered(false);
        grid2.setRendered(false);
        grid3.setRendered(false);
        alert1.setRendered(false);
        wybranyrodzajimportu = null;
    }
    
    public void resetuj1() {
        rodzajdok = null;
        pobranefaktury = new ArrayList();
        pobraneplikibytes = new ArrayList();
        grid1.setRendered(false);
        grid2.setRendered(false);
        grid3.setRendered(false);
        alert1.setRendered(false);
    }
    
    public void resetuj2() {
        pobranefaktury = new ArrayList();
        grid2.setRendered(false);
        grid3.setRendered(false);
        alert1.setRendered(false);
    }
    
    
    
    public void importujdok() {
        try {
            List zwrot = null;
            pobranefaktury = new ArrayList<>();
            if (pobraneplikibytes!=null && pobraneplikibytes.size()>0) {
                int numerwyciagu = -1;
                int lpwiersza = 1;
                for (byte[] partia : pobraneplikibytes) {
                    switch (wybranyrodzajimportu.getLp()) {
                        case 1 :
                           zwrot = ImportPKO_XML.importujdok(partia, wyciagdataod, numerwyciagu);
                           break;
                        case 2 :
                           zwrot = ImportSantander_CSV.importujdok(partia, wyciagdataod, numerwyciagu, lpwiersza, wpisView.getMiesiacWpisu());
                           break;
                        case 3 :
                           zwrot = ImportMbankHist_CSV.importujdok(partia, wyciagdataod, numerwyciagu, lpwiersza, wpisView.getMiesiacWpisu());
                           break;
                        case 4 :
                           return;
                        case 5 :
                            zwrot = ImportPKOBP_CSV.importujdok(partia, wyciagdataod, numerwyciagu, lpwiersza, wpisView.getMiesiacWpisu());
                            break;
                        case 6 :
                            zwrot = ImportBNPParibas_CSV.importujdok(partia, wyciagdataod, numerwyciagu, lpwiersza, wpisView.getMiesiacWpisu());
                            break;
                        case 7 :
                            zwrot = ImportING_XML.importujdok(partia, wyciagdataod, numerwyciagu, lpwiersza, wpisView.getMiesiacWpisu());
                            break;
                    }
                    if (zwrot.size()==5) {
                        Msg.msg("e", "Nie pobrano wszystkich plików. Wystąpił błąd");
                        break;
                    } else {
                        if (naglowek==null) {
                            naglowek = (ImportowanyPlikNaglowek) zwrot.get(0);
                        } else {
                            naglowek.setWyciagnrdo(((ImportowanyPlikNaglowek)zwrot.get(0)).getWyciagnrdo());
                            naglowek.setWyciagdatado(((ImportowanyPlikNaglowek)zwrot.get(0)).getWyciagdatado());
                            naglowek.setWyciagbz(((ImportowanyPlikNaglowek)zwrot.get(0)).getWyciagbz());
                        }
                         pobranefaktury.addAll((List<ImportBankWiersz>) zwrot.get(1));
                         numerwyciagu = (int) zwrot.get(2);
                         lpwiersza = zwrot.size()==4 ? (int) zwrot.get(3):1;
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
                grid3.setRendered(true);
                alert1.setRendered(false);
                generujbutton.setRendered(true);
                Msg.msg("Pobrano dane");
            } else {
                grid2.setRendered(false);
                grid2.setRendered(false);
                alert1.setRendered(true);
                Msg.msg("e","Zaciągnięto wyciągi z błędem");
            }
            
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd przy pobieraniu danych ");
            System.out.println(E.e(e));
        }
    }

    public void generuj() {
        if (pobranefaktury !=null && pobranefaktury.size()>0) {
            datakontrol = null;
            int ile = 1;
            int duplikaty = 0;
            Klienci kontr = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
            Waluty walutadokumentu = walutyDAOfk.findWalutaBySymbolWaluty(naglowek.getWyciagwaluta());
            while (pobranefaktury!=null && pobranefaktury.size() >0) {
                int czyduplikat = generowanieDokumentu(ile, kontr, walutadokumentu);
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
    
        
     public int generowanieDokumentu(int i, Klienci kontr, Waluty walutadokumentu) {
        int zwrot = 0;
        try {
            int numerkolejny = ImportBean.oblicznumerkolejny(rodzajdok.getSkrotNazwyDok(), dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            Dokfk dokument = stworznowydokument(i, kontr, numerkolejny, wpisView, walutadokumentu);
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
     
      public Dokfk stworznowydokument(int i, Klienci kontr, int numerkolejny, WpisView wpisView, Waluty walutadokumentu) {
        ImportowanyPlikNaglowek pn = pobranefaktury.get(0).getNaglowek();
        Dokfk nd = new Dokfk(numerkolejny, wpisView.getRokWpisuSt());
        nd.setKontr(kontr);
        nd.setNumerwlasnydokfk("wyciag nr "+pn.getWyciagnr()+" "+wpisView.getMiesiacWpisu()+"/"+i);
        nd.setOpisdokfk("rozliczenie wyciągu za "+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisuSt());
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        nd.setSeriadokfk(rodzajdok.getSkrot());
        nd.setRodzajedok(rodzajdok);
        nd.setEwidencjaVAT(null);
        nd.setSaldopoczatkowe(pn.getWyciagbo());
        if (naglowek.getWyciagwaluta().equals("PLN")) {
            nd.setTabelanbp(tabelanbppl);
            nd.setWalutadokumentu(walutapln);
        } else {
            nd.setWalutadokumentu(walutyDAOfk.findWalutaBySymbolWaluty(naglowek.getWyciagwaluta()));
        }
        Dokfk juzjest = dokDAOfk.findDokfkObjKontrahent(nd);
        if (juzjest!=null) {
            nd = null;
            usunduplikat(juzjest);
        }
        ustawwiersze(nd);
        if (nd.getListawierszy()!=null && nd.getListawierszy().size()>0) {
            ustawdaty(nd, pn);
            nd.setImportowany(true);
            nd.setWprowadzil(wpisView.getUzer().getLogin());
            nd.przeliczKwotyWierszaDoSumyDokumentu();
            rozliczsaldoWBRK(nd);
        } else {
            nd=null;
        }
        return nd;
    }
      
    private void ustawdaty(Dokfk nd, ImportowanyPlikNaglowek pn) {
        nd.setDatadokumentu(wyciagdatado);
        nd.setDataoperacji(wyciagdatado);
        nd.setDatawplywu(pn.getWyciagdatado());
        nd.setDatawystawienia(wyciagdatado);
        nd.setDataujecia(new Date());
        nd.setMiesiac(wpisView.getMiesiacWpisu());
        nd.setRok(wpisView.getRokWpisuSt());
        nd.setVatM(pn.getWyciagdatado().split("-")[1]);
        nd.setVatR(pn.getWyciagdatado().split("-")[0]);
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
        wyciagbo_transfer = selected.getSaldokoncowe();
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
        try {
            zwrot = ibankonto.get(p.getIBAN());
            p.setZnalezionokonto(true);
        } catch (Exception e){}
        if (zwrot ==null) {
            p.setZnalezionokonto(false);
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
                case 10:
                    zwrot = konto1494;
                    break;
                case 11:
                    zwrot = przychodystowarzyszenie;
                    break;
            }
        }
        return zwrot;
    }    

    private Wiersz przygotujwierszNetto(int lpwiersza,Dokfk nd, ImportBankWiersz importBankWiersz, Konto kontown, Konto kontoma) {
        Wiersz w = new Wiersz(lpwiersza, nd, 0);
        w.setDataWalutyWiersza(Data.getDzien(importBankWiersz.getDatatransakcji()));
        uzupelnijwiersz(w, nd, importBankWiersz);
        w.setOpisWiersza(zrobopiswiersza(importBankWiersz));
        StronaWiersza strwn = new StronaWiersza(w, "Wn", importBankWiersz.getKwota(), kontown);
        StronaWiersza strma = new StronaWiersza(w, "Ma", importBankWiersz.getKwota(), kontoma);
        strwn.setKwotaPLN(zrobpln(w,importBankWiersz));
        strma.setKwotaPLN(zrobpln(w,importBankWiersz));
        w.setStronaWn(strwn);
        w.setStronaMa(strma);
        //w.setZnalezionokonto(importBankWiersz.isZnalezionokonto());
        return w;
    }
    
    private void uzupelnijwiersz(Wiersz w, Dokfk nd, ImportBankWiersz importBankWiersz) {
        if (importBankWiersz.getWaluta().equals("PLN")) {
            w.setTabelanbp(tabelanbppl);
        } else {
            DateTime dzienposzukiwany = new DateTime(importBankWiersz.getDatawaluty());
            boolean znaleziono = false;
            int zabezpieczenie = 0;
            while (!znaleziono && (zabezpieczenie < 365)) {
                dzienposzukiwany = dzienposzukiwany.minusDays(1);
                String doprzekazania = dzienposzukiwany.toString("yyyy-MM-dd");
                Tabelanbp tabelanbppobrana = tabelanbpDAO.findByDateWaluta(doprzekazania, importBankWiersz.getWaluta());
                if (tabelanbppobrana instanceof Tabelanbp) {
                    znaleziono = true;
                    w.setTabelanbp(tabelanbppobrana);
                    break;
                }
                zabezpieczenie++;
            }
        }
        nd.setWalutadokumentu(w.getTabelanbp().getWaluta());
        w.setIban(importBankWiersz.getIBAN());
        w.setDokfk(nd);
        w.setLpmacierzystego(0);
        w.setDataksiegowania(nd.getDatawplywu());
    }
    
     private String zrobopiswiersza(ImportBankWiersz ImportBankWiersz) {
        String opis = ImportBankWiersz.getOpistransakcji().toLowerCase(new Locale("pl"));
        String kontr = WordUtils.capitalizeFully(ImportBankWiersz.getKontrahent().trim());
        if (kontr.equals("NOTPROVIDED")) {
            kontr=null;
        } else if (kontr.equals(" ") || kontr.equals("\"  \"")) {
            kontr=null;
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
        
        opis = opis.replaceAll("\\s{2,}", " ").replaceAll("\"", "").trim();
        String zwrot = opis;
        if (kontr!=null) {
            zwrot = opis+" "+kontr.replaceAll("\"", "");
        }
        return zwrot;
    }
    
     private double zrobpln(Wiersz w, ImportBankWiersz ImportBankWiersz) {
        double zwrot = ImportBankWiersz.getKwota();
        if (!w.getWalutaWiersz().equals("PLN")) {
            zwrot = Z.z(ImportBankWiersz.getKwota()*w.getTabelanbp().getKurssredniPrzelicznik());
        }
        return zwrot;
    }
    public void grid0pokaz() {
        if (sabraki==false) {
            resetuj1();
            grid0.setRendered(true);
            Msg.msg("i","Wybranonastępujący format importu "+wybranyrodzajimportu);
        } else {
            Msg.msg("e", "Są braki. Nie można wszytać pliku");
        }
    }
    
    public void grid2pokaz() {
        resetuj2();
        grid2.setRendered(true);
        grid3.setRendered(false);
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

    public List<ImportowanyPlik> getRodzajeimportu() {
        return rodzajeimportu;
    }

    public void setRodzajeimportu(List<ImportowanyPlik> rodzajeimportu) {
        this.rodzajeimportu = rodzajeimportu;
    }

    public ImportowanyPlik getWybranyrodzajimportu() {
        return wybranyrodzajimportu;
    }

    public void setWybranyrodzajimportu(ImportowanyPlik wybranyrodzajimportu) {
        this.wybranyrodzajimportu = wybranyrodzajimportu;
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

    public PanelGrid getGrid0() {
        return grid0;
    }

    public void setGrid0(PanelGrid grid0) {
        this.grid0 = grid0;
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

    public ImportowanyPlikNaglowek getNaglowek() {
        return naglowek;
    }

    public void setNaglowek(ImportowanyPlikNaglowek naglowek) {
        this.naglowek = naglowek;
    }

    public HtmlOutputText getAlert1() {
        return alert1;
    }

    public void setAlert1(HtmlOutputText alert1) {
        this.alert1 = alert1;
    }

   
    
    public static void main(String[] args) throws SAXException, IOException {
        try {
            Path pathToFile = Paths.get("D:\\mbank.csv");
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
            List<ImportBankWiersz> listaswierszy = new ArrayList<>();
            for (Iterator<List<String>> it = records.iterator(); it.hasNext();) {
                List<String> baza = it.next();
                List<String> row = new ArrayList<>();
//                for (String r : baza) {
//                    row.add(r.replace("\"", ""));
//                }
//                if (i==0) {
//                    String wyciagnr = baza.get(0);
//                    String wyciagdataod = baza.get(2);
//                    String wyciagdatado = baza.get(1);
//                    String wyciagkonto = baza.get(5);;
//                    String wyciagwaluta = baza.get(6);
//                    String wyciagbz = baza.get(12);
//                    String wyciagobrotywn = baza.get(10);
//                    String wyciagobrotyma = baza.get(11);
//                    System.out.println("");
//                } else if (i==1) {
//                    String wyciagbo = baza.get(12);
//                } else {
//                    ImportBankWiersz x = new ImportBankWiersz();
//                    x.setDatatransakcji(baza.get(1));
//                    x.setDatawaluty(baza.get(2));
//                    x.setIBAN(baza.get(5));//??
//                    x.setKontrahent(baza.get(4));//??
//                    x.setKwota(Double.parseDouble(baza.get(10).replace(",",".")));
//                    x.setWnma("Wn");
//                    if (!baza.get(11).equals("")) {
//                        x.setKwota(-Double.parseDouble(baza.get(11).replace(",",".")));
//                        x.setWnma("Ma");
//                    }
//                    x.setNrtransakji(baza.get(8));
//                    x.setOpistransakcji(baza.get(3));
//                    x.setTyptransakcji(oblicztyptransakcji(x));
//                    listaswierszy.add(x);
//                }
                i++;
            }
            System.out.println("");
        } catch (Exception ex) {
            Logger.getLogger(Dedraparser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    

    

   

    

    

   
    

    
   
    
   
    
}
