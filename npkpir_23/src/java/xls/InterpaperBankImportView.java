/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import beansDok.ListaEwidencjiVat;
import beansRegon.SzukajDaneBean;
import dao.EvewidencjaDAO;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import daoFK.DokDAOfk;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaZapisDAO;
import daoFK.TabelanbpDAO;
import daoFK.UkladBRDAO;
import daoFK.WalutyDAOfk;
import data.Data;
import dedra.Dedraparser;
import embeddable.PanstwaEUSymb;
import embeddablefk.ImportBankXML;
import embeddablefk.InterpaperXLS;
import entity.Evewidencja;
import entity.Klienci;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersz;
import error.E;
import gus.GUSView;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
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
public class InterpaperBankImportView implements Serializable {
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
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private ListaEwidencjiVat listaEwidencjiVat;
    private byte[] plikinterpaper;
    public  List<ImportBankXML> pobranefaktury;
    public  List<ImportBankXML> pobranefakturyfilter;
    public  List<ImportBankXML> selected;
    private List<Rodzajedok> rodzajedokKlienta;
    private String wiadomoscnieprzypkonta;
    private String rodzajdok;
    private PanelGrid grid1;
    private PanelGrid grid2;
    private PanelGrid grid3;
    private CommandButton generujbutton; 
    private Konto kontonetto;
    private Konto kontonettokoszt;
    private Konto kontovat;
    private Konto kontovatnaliczony;
    private Tabelanbp tabelanbppl;
    private Waluty walutapln;
    private String wyciagdataod;
    private String wyciagdatado;
    private String wyciagnr;
    private String wyciagkonto;
    private String wyciagwaluta;
    private double wyciagbo;
    private double wyciagbz;

    
    
    @PostConstruct
    private void init() {
        rodzajedokKlienta = rodzajedokDAO.findListaPodatnikRO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        for (Iterator<Rodzajedok> it = rodzajedokKlienta.iterator(); it.hasNext();) {
            Rodzajedok p = it.next();
            if (!p.getSkrot().equals("SZ") && p.getSkrot().equals("UPTK") && p.getSkrot().equals("ZZ")) {
                it.remove();
            } else {
                if (p.getKontorozrachunkowe()==null && p.getKontoRZiS()==null) {
                    wiadomoscnieprzypkonta = "Istnieją dokumenty z VAT bez przyporządkowanych kont. Import będzie nieprawidłowy";
                }
            }
        } 
        kontonetto = kontoDAO.findKonto("702-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontovat = kontoDAO.findKonto("221-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontonettokoszt = kontoDAO.findKonto("403", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontovatnaliczony = kontoDAO.findKonto("221-3", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        tabelanbppl = tabelanbpDAO.findByTabelaPLN();
        walutapln = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
    }
    
    
    public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            if (extension.equals("xml")) {
                String filename = uploadedFile.getFileName();
                plikinterpaper = uploadedFile.getContents();
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
                            ImportBankXML p = new ImportBankXML();
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
            grid3.setRendered(true);
            generujbutton.setRendered(true);
            Msg.msg("Pobrano wszystkie dane");
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd przy pobieraniu danych");
        }
//        for (InterpaperXLS p : pobranefaktury) {
//           generowanieDokumentu(p);
//        }
    }
    
    //typ transakcji
        //1 wpływ faktura
        //2 zapłata faktura
        //3 prowizja
        //4 wypłata karta
        //5 płatnośc karta
        //6 podatki
    private int oblicztyptransakcji(ImportBankXML p) {
        int zwrot = 0;
        if (p.getKontrahent().equals("NOTPROVIDED")) {
            zwrot = 3;
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
//        if (pobranefaktury !=null && pobranefaktury.size()>0) {
//            List<Klienci> k = klienciDAO.findAll();
//            int ile = 0;
//            if (selected !=null && selected.size()>0) {
//                for (ImportBankXML p : selected) {
//                   ile += generowanieDokumentu(p, k);
//                }
//                Msg.msg("Zaksięgowano "+ile+" z wybranych dokumentów");
//            } else {
//                for (ImportBankXML p : pobranefaktury) {
//                   ile += generowanieDokumentu(p, k);
//                }
//                Msg.msg("Zaksięgowano "+ile+" dokumentów");
//            }
//        } else {
//            Msg.msg("e", "Błąd! Lista danych źrdółowych jest pusta");
//        }
    }
    
     public int generowanieDokumentu(InterpaperXLS interpaperXLS, List<Klienci> k) {
        int ile = 0;
        try {
            int polska0unia1zagranica2 = 0;
            if (interpaperXLS.getKlient().getKrajnazwa()!=null && !interpaperXLS.getKlient().getKrajkod().equals("PL")) {
                polska0unia1zagranica2 = 2;
                if (PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod())) {
                    polska0unia1zagranica2 = 1;
                }
            }
            String rodzajdk = "ZZ";
            if (rodzajdok.equals("sprzedaż")) {
                rodzajdk = polska0unia1zagranica2==0 ? "SZ" : polska0unia1zagranica2==1 ? "UPTK100" : "UPTK";
            } else {
                rodzajdk = polska0unia1zagranica2==0 ? "ZZ" : "IU";
            }
            Dokfk dokument = stworznowydokument(interpaperXLS, rodzajdk, k);
            try {
                if (dokument!=null) {
                    dokument.setImportowany(true);
                    dokDAOfk.dodaj(dokument);
                    ile++;
                }
            } catch (Exception e) {
                Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu "+rodzajdok);
            }
        } catch (Exception e) {
            E.e(e);
        }
        return ile;
    }
     
      private Dokfk stworznowydokument(InterpaperXLS interpaperXLS, String rodzajdok, List<Klienci> klienci) {
        int numerkolejny = ImportBean.oblicznumerkolejny(rodzajdok, dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Dokfk nd = new Dokfk(numerkolejny, wpisView.getRokWpisuSt());
        ustawdaty(nd, interpaperXLS);
        nd.setKontr(ImportBean.ustawkontrahenta(interpaperXLS.getNip(), interpaperXLS.getKontrahent(), klienci, gUSView, klienciDAO));
        ImportBean.ustawnumerwlasny(nd, interpaperXLS.getNrfaktury());
        nd.setOpisdokfk("usługa transportowa");
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        ImportBean.ustawrodzajedok(nd, rodzajdok, rodzajedokDAO, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        ustawtabelenbp(nd, interpaperXLS);
        podepnijEwidencjeVat(nd, interpaperXLS);
        Dokfk juzjest = dokDAOfk.findDokfkObjKontrahent(nd);
        if (juzjest!=null) {
            nd = null;
            interpaperXLS.setJuzzaksiegowany(true);
        } else {
            ustawwiersze(nd, interpaperXLS);
            nd.setImportowany(true);
            nd.setWprowadzil(wpisView.getUzer().getLogin());
            nd.przeliczKwotyWierszaDoSumyDokumentu();
        }
        return nd;
    }
      
    
    
    private void ustawdaty(Dokfk nd, InterpaperXLS interpaperXLS) {
        Format formatterX = new SimpleDateFormat("yyyy-MM-dd");
        String datadokumentu = formatterX.format(interpaperXLS.getDatawystawienia());
        String datasprzedazy = formatterX.format(interpaperXLS.getDatasprzedaży());
        nd.setDatadokumentu(datadokumentu);
        nd.setDataoperacji(datasprzedazy);
        nd.setDatawplywu(datadokumentu);
        nd.setDatawystawienia(datadokumentu);
        nd.setDataujecia(new Date());
        nd.setMiesiac(wpisView.getMiesiacWpisu());
        nd.setVatM(datasprzedazy.split("-")[1]);
        nd.setVatR(datasprzedazy.split("-")[0]);
    }
    
    
    
    private void ustawtabelenbp(Dokfk nd, InterpaperXLS interpaperXLS) {
        if (interpaperXLS.getWalutaplatnosci().equals("PLN")) {
            nd.setTabelanbp(tabelanbppl);
            nd.setWalutadokumentu(walutapln);
        } else {
            Format formatterX = new SimpleDateFormat("yyyy-MM-dd");
            String datadokumentu = formatterX.format(interpaperXLS.getDatasprzedaży());
            DateTime dzienposzukiwany = new DateTime(datadokumentu);
            boolean znaleziono = false;
            int zabezpieczenie = 0;
            while (!znaleziono && (zabezpieczenie < 365)) {
                dzienposzukiwany = dzienposzukiwany.minusDays(1);
                String doprzekazania = dzienposzukiwany.toString("yyyy-MM-dd");
                Tabelanbp tabelanbppobrana = tabelanbpDAO.findByDateWaluta(doprzekazania, interpaperXLS.getWalutaplatnosci());
                if (tabelanbppobrana instanceof Tabelanbp) {
                    znaleziono = true;
                    nd.setTabelanbp(tabelanbppobrana);
                    break;
                }
                zabezpieczenie++;
            }
            Waluty w = walutyDAOfk.findWalutaBySymbolWaluty(interpaperXLS.getWalutaplatnosci());
            nd.setWalutadokumentu(w);
        }
    }
    
    private void ustawwiersze(Dokfk nd, InterpaperXLS interpaperXLS) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        if (rodzajdok.equals("sprzedaż")) {
            nd.getListawierszy().add(przygotujwierszNetto(interpaperXLS, nd));
            if (interpaperXLS.getVatwaluta() != 0) {
                nd.getListawierszy().add(przygotujwierszVat(interpaperXLS, nd));
            }
        } else {
            nd.getListawierszy().add(przygotujwierszNettoK(interpaperXLS, nd));
            if (interpaperXLS.getVatwaluta() != 0) {
                nd.getListawierszy().add(przygotujwierszVatK(interpaperXLS, nd));
            }
        }
    }

    private Wiersz przygotujwierszNetto(InterpaperXLS interpaperXLS, Dokfk nd) {
        Wiersz w = new Wiersz(1, 0);
        uzupelnijwiersz(w, nd);
        String opiswiersza = "usługa transportowa"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", interpaperXLS.getBruttowaluta(), null);
        StronaWiersza strma = new StronaWiersza(w, "Ma", interpaperXLS.getNettowaluta(), null);
        strwn.setKwotaPLN(interpaperXLS.getBruttoPLN());
        strma.setKwotaPLN(interpaperXLS.getNettoPLNvat());
        strma.setKonto(kontonetto);
        strwn.setKonto(ImportBean.pobierzkontoWn(interpaperXLS.getKlient(), kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO));
        w.setStronaWn(strwn);
        w.setStronaMa(strma);
        return w;
    }
    
    private Wiersz przygotujwierszVat(InterpaperXLS interpaperXLS, Dokfk nd) {
        Wiersz w = new Wiersz(2, 2);
        uzupelnijwiersz(w, nd);
        String opiswiersza = "usługa transportowa - VAT"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strma = new StronaWiersza(w, "Ma", interpaperXLS.getVatwaluta(), null);
        strma.setKwotaPLN(interpaperXLS.getVatPLN());
        strma.setKonto(kontovat);
        w.setStronaMa(strma);
        return w;
    }
    
    private Wiersz przygotujwierszNettoK(InterpaperXLS interpaperXLS, Dokfk nd) {
        Wiersz w = new Wiersz(1, 0);
        uzupelnijwiersz(w, nd);
        String opiswiersza = "usługa transportowa"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strma = new StronaWiersza(w, "Ma", interpaperXLS.getBruttowaluta(), null);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", interpaperXLS.getNettowaluta(), null);
        strma.setKwotaPLN(interpaperXLS.getBruttoPLN());
        strwn.setKwotaPLN(interpaperXLS.getNettoPLNvat());
        strwn.setKonto(kontonettokoszt);
        strma.setKonto(ImportBean.pobierzkontoMa(interpaperXLS.getKlient(), kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO));
        w.setStronaMa(strma);
        w.setStronaWn(strwn);
        return w;
    }
    
    private Wiersz przygotujwierszVatK(InterpaperXLS interpaperXLS, Dokfk nd) {
        Wiersz w = new Wiersz(2, 1);
        uzupelnijwiersz(w, nd);
        String opiswiersza = "usługa transportowa - VAT"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", interpaperXLS.getVatwaluta(), null);
        strwn.setKwotaPLN(interpaperXLS.getVatPLN());
        strwn.setKonto(kontovatnaliczony);
        w.setStronaWn(strwn);
        return w;
    }
    
    private void uzupelnijwiersz(Wiersz w, Dokfk nd) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        w.setTabelanbp(t);
        w.setDokfk(nd);
        w.setLpmacierzystego(0);
        w.setTabelanbp(w.getTabelanbp());
        w.setDataksiegowania(nd.getDatawplywu());
    }
    
    private void podepnijEwidencjeVat(Dokfk nd, InterpaperXLS interpaperXLS) {
        if (nd.getRodzajedok().getKategoriadokumentu() != 0 && nd.getRodzajedok().getKategoriadokumentu() != 5) {
            if (nd.iswTrakcieEdycji() == false) {
                nd.setEwidencjaVAT(new ArrayList<EVatwpisFK>());
                    boolean vatowiec = wpisView.isVatowiec();
                    if (vatowiec) {
                        /*wyswietlamy ewidencje VAT*/
                        List<Evewidencja> opisewidencji = Collections.synchronizedList(new ArrayList<>());
                        opisewidencji.addAll(listaEwidencjiVat.pobierzEvewidencje(nd.getRodzajedok().getRodzajtransakcji()));
                        int k = 0;
                        for (Evewidencja p : opisewidencji) {
                            EVatwpisFK eVatwpisFK = new EVatwpisFK(); 
                            eVatwpisFK.setLp(k++);
                            eVatwpisFK.setEwidencja(p);
                            if (Z.z(interpaperXLS.getVatPLN())!=0.0) {
                                if (p.getNazwa().equals("sprzedaż 23%")||p.getNazwa().equals("zakup")) {
                                    eVatwpisFK.setNetto(Z.z(interpaperXLS.getNettoPLNvat()));
                                    eVatwpisFK.setVat(Z.z(interpaperXLS.getVatPLN()));
                                    eVatwpisFK.setBrutto(Z.z(interpaperXLS.getNettoPLNvat()+interpaperXLS.getVatPLN()));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                }
                            } else {
                                if (PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod()) && p.getNazwa().equals("import usług art. 28b")) {
                                    eVatwpisFK.setNetto(Z.z(interpaperXLS.getNettoPLNvat()));
                                    eVatwpisFK.setVat(Z.z(interpaperXLS.getVatPLN()));
                                    eVatwpisFK.setBrutto(Z.z(interpaperXLS.getNettoPLNvat()+interpaperXLS.getVatPLN()));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                } else if (!PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod()) && p.getNazwa().equals("import usług")) {
                                    eVatwpisFK.setNetto(Z.z(interpaperXLS.getNettoPLNvat()));
                                    eVatwpisFK.setVat(Z.z(interpaperXLS.getVatPLN()));
                                    eVatwpisFK.setBrutto(Z.z(interpaperXLS.getNettoPLNvat()+interpaperXLS.getVatPLN()));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                } else if (PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod()) && p.getNazwa().equals("usługi świad. poza ter.kraju art. 100 ust.1 pkt 4")) {
                                    eVatwpisFK.setNetto(Z.z(interpaperXLS.getNettoPLNvat()));
                                    eVatwpisFK.setVat(0.0);
                                    eVatwpisFK.setBrutto(Z.z(interpaperXLS.getNettoPLNvat()));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                } else if (!PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod()) && p.getNazwa().equals("usługi świad. poza ter.kraju")) {
                                    eVatwpisFK.setNetto(Z.z(interpaperXLS.getNettoPLNvat()));
                                    eVatwpisFK.setVat(0.0);
                                    eVatwpisFK.setBrutto(Z.z(interpaperXLS.getNettoPLNvat()));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                } else if (p.getNazwa().equals("sprzedaż 0%")||p.getNazwa().equals("zakup")) {
                                    eVatwpisFK.setNetto(Z.z(interpaperXLS.getNettoPLNvat()));
                                    eVatwpisFK.setVat(0.0);
                                    eVatwpisFK.setBrutto(Z.z(interpaperXLS.getNettoPLNvat()));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                }
                            }
                        }
                } else {
                    Msg.msg("e", "Brak podstawowych ustawień dla podatnika dotyczących opodatkowania. Nie można wpisywać dokumentów! podepnijEwidencjeVat()");
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

    public String getWiadomoscnieprzypkonta() {
        return wiadomoscnieprzypkonta;
    }

    public void setWiadomoscnieprzypkonta(String wiadomoscnieprzypkonta) {
        this.wiadomoscnieprzypkonta = wiadomoscnieprzypkonta;
    }

    public String getRodzajdok() {
        return rodzajdok;
    }

    public void setRodzajdok(String rodzajdok) {
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
    
public static void main(String[] args) throws SAXException, IOException {
        try {
            File fXmlFile = new File("D:\\bank1.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList1 = doc.getElementsByTagName("Stmt");
            String nrwyciagu = pT(nList1.item(0), "Id");;
            String dataod = pT(nList1.item(0), "FrDtTm");
            String datado = pT(nList1.item(0), "ToDtTm");
            String iban = pT(nList1.item(0), "IBAN");
            String waluta = pT(nList1.item(0), "Ccy");
            String bo = pT(nList1.item(0), "Amt", 0);
            String bz = pT(nList1.item(0), "Amt", 1);
            String currency = pE(nList1.item(0), "Amt").getAttribute("Ccy");
            System.out.println("----------------------------");
//            NodeList nList1 = doc.getElementsByTagName("Ntry");
//            System.out.println("----------------------------");
//            int len = nList1.getLength();
//                for (int temp = 0; temp < len; temp++) {
//                    Node nNode1 = nList1.item(temp);
//                    if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
//                        try {
//                            Element eElement = (Element) nNode1;
//                            String elt = pE(nNode1, "Id") == null ? "brak" : pTFC(nNode1, "Id");
//                            System.out.println("IBAN : " + elt);
//                            String elt1 = pT(nNode1, "Amt");
//                            System.out.println("amount : " + elt1);
//                            System.out.println("waluta "+pE(nNode1, "Amt").getAttribute("Ccy"));
//                            String elt2 = pT(nNode1, "CdtDbtInd");
//                            System.out.println("WnMa : " + elt2);
//                            System.out.println("data : " + pTFC(nNode1, "BookgDt"));
//                            System.out.println("data waluty : " + pTFC(nNode1, "ValDt"));
//                            System.out.println("opis : " + pT(nNode1, "Ustrd"));
//                            String elt3 = pE(nNode1, "Nm") == null ? "brak" : pT(nNode1, "Nm");
//                            System.out.println("odbiorca : " + elt3);
//                            String elt4 = pE(nNode1, "Ctry") == null ? "brak" : pT(nNode1, "Ctry");
//                            System.out.println("kraj : " + elt4);
//                            String elt5 = pE(nNode1, "AdrLine") == null ? "brak" : pT(nNode1, "AdrLine");
//                            System.out.println("adres : " + elt5);
//                            System.out.println("lp : " + pT(nNode1, "TxId"));
//                        } catch (Exception e) {
//                            E.e(e);
//                        }
//                    }
//                }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Dedraparser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    
   
    
   
    
}
