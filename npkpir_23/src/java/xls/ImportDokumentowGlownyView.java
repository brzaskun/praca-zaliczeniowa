/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import beansDok.ListaEwidencjiVat;
import beansFK.PlanKontFKBean;
import beansRegon.SzukajDaneBean;
import comparator.Kliencifkcomparator;
import dao.DokDAOfk;
import dao.EvewidencjaDAO;
import dao.KlienciDAO;
import dao.KliencifkDAO;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.RodzajedokDAO;
import dao.TabelanbpDAO;
import dao.UkladBRDAO;
import dao.WalutyDAOfk;
import data.Data;
import embeddable.Mce;
import embeddable.PanstwaEUSymb;
import embeddable.PanstwaMap;
import embeddable.Roki;
import embeddablefk.InterpaperXLS;
import entity.Evewidencja;
import entity.Klienci;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Kliencifk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersz;
import error.E;
import gus.GUSView;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.joda.time.DateTime;
import org.primefaces.PrimeFaces;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import pdf.PdfXLSImport;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ImportDokumentowGlownyView implements Serializable {
    private static final long serialVersionUID = 1L;
   @Inject
    private WpisView wpisView;
   @Inject
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
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private ListaEwidencjiVat listaEwidencjiVat;
    private byte[] pobranyplik;
    private List<byte[]> pobraneplikibytes;
    private List<InterpaperXLS> pobranefaktury;
    private List<InterpaperXLS> przerwanyimport;
    private List<InterpaperXLS> importyzbrakami;
    private List<InterpaperXLS> innyokres;
    private  List<InterpaperXLS> pobranefakturyfilter;
    private  List<InterpaperXLS> selected;
    private List<Rodzajedok> rodzajedokKlienta;
    private String wiadomoscnieprzypkonta;
    private String rodzajdok;
    private PanelGrid grid0;
    private PanelGrid grid1;
    private PanelGrid grid2;
    private boolean sabraki;
    private CommandButton generujbutton;
    private CommandButton drkujfizbutton;
    private SelectOneMenu kontobutton;
    private SelectOneMenu kontobutton2;
    private Konto kontonetto;
    private Konto kontonettokoszt;
    private Konto kontonettotowary;
    private Konto kontovat;
    private Konto kontovatnaliczony;
    private Konto kontovatnalezny;
    private Konto kontovatnaliczonyprzesuniecie;
    private Konto kontovatzagranica;
    private Tabelanbp tabelanbppl;
    private Waluty walutapln;
    private List<ImportowanyPlik> rodzajeimportu;
    private ImportowanyPlik wybranyrodzajimportu;
    
    private Klienci selectedimport1;
    private String selectedimport1text;
    @Inject
    private Klienci selectedimport;
    @Inject
    private PanstwaMap panstwaMapa;
    private List<String> rodzajedokimportu;
    private Konto kontodlanetto;
    private Konto kontodlabrutto;
    private List<Konto> listakontoRZiS;
    private String jakipobor;
    private double nettopln;
    private double vatpln;
    private double bruttopln;
    

    public void init() { //E.m(this);
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
        pobraneplikibytes = new ArrayList<>();
        rodzajeimportu = zrobrodzajeimportu();
        kontonetto = kontoDAO.findKonto("702-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontovat = kontoDAO.findKonto("221-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontovatzagranica = kontoDAO.findKonto("223", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontonettokoszt = kontoDAO.findKonto("403", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontonettotowary = kontoDAO.findKonto("330-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontovatnaliczony = kontoDAO.findKonto("221-3", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontovatnalezny = kontoDAO.findKonto("221-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontovatnaliczonyprzesuniecie = kontoDAO.findKonto("221-4", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        tabelanbppl = tabelanbpDAO.findByTabelaPLN();
        walutapln = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        listakontoRZiS = kontoDAO.findKontaRZiS(wpisView);
        jakipobor = "wszystko";
    }
    
     private List<ImportowanyPlik> zrobrodzajeimportu() {
        List<ImportowanyPlik> zwrot = new ArrayList<>();
        zwrot.add(new ImportowanyPlik("Interpaper csv ;","csv",1));
        zwrot.add(new ImportowanyPlik("Zorint xls","xls","",2));
        zwrot.add(new ImportowanyPlik("Tomtech xls","xls","",3));
        zwrot.add(new ImportowanyPlik("Exolight xls","xls","",4));
        zwrot.add(new ImportowanyPlik("Murawski xls","xls","",5));
        zwrot.add(new ImportowanyPlik("Mucha xls","xls","",6));
        zwrot.add(new ImportowanyPlik("Zorin nowy xml","xml","",7));
        zwrot.add(new ImportowanyPlik("Seven xml","xml","",8));
        zwrot.add(new ImportowanyPlik("K3F xml","xml","",9));
        zwrot.add(new ImportowanyPlik("Amazon taxraport csv","csv","",10));
        zwrot.add(new ImportowanyPlik("Bud-Instal epp","epp","",11));
        zwrot.add(new ImportowanyPlik("AGLP xls","xls","",12));
        zwrot.add(new ImportowanyPlik("Domeguru xls","xls","",13));
        zwrot.add(new ImportowanyPlik("Szyszko Logistics csv","csv","",14));
        zwrot.add(new ImportowanyPlik("Vintis Vendor csv","csv","",15));
        zwrot.add(new ImportowanyPlik("Amazon json json","json","",16));
        return zwrot;
    }
    
    public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            if (extension.equals("csv")||extension.equals("xls")||extension.equals("xlsx")||extension.equals("xml")||extension.equals("epp")||extension.equals("json")) {
                String filename = uploadedFile.getFileName();
                pobranyplik = uploadedFile.getContent();
                pobraneplikibytes.add(uploadedFile.getContent());
                PrimeFaces.current().ajax().update("panelplik");
                grid1.setRendered(true);
                grid2.setRendered(false);
                    pobranefaktury = null;
                rodzajdok = null;
                jakipobor = "wszystko";
                rodzajedokimportu = pobierzrodzajeimportu(wybranyrodzajimportu.getLp());
                if (pobraneplikibytes!=null&&pobraneplikibytes.size()>1) {
                    Msg.msg("Sukces. Skutecznie załadowano "+pobraneplikibytes.size()+" plików");
                } else {
                    Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
                }
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
            switch (wybranyrodzajimportu.getLp()) {
                case 1:
                    pobranefaktury = ReadCSVInterpaperFile.getListafakturCSV(pobranyplik, k, klienciDAO, rodzajdok, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                    break;
                case 2:
                    pobranefaktury = ReadXLSFirmaoFile.getListafakturXLS(pobranyplik, k, klienciDAO, rodzajdok, wpisView.getMiesiacWpisu());
                    break;
                case 3:
                    pobranefaktury = ReadXLSTomTechFile.getListafakturXLS(pobranyplik, k, klienciDAO, rodzajdok, wpisView.getMiesiacWpisu());
                    break;
                case 4:
                    pobranefaktury = ReadXLSExolightFile.getListafakturXLS(pobranyplik, k, klienciDAO, rodzajdok, wpisView.getMiesiacWpisu());
                    break;
                case 5:
                    if (pobraneplikibytes.size()>0) {
                        pobranefaktury = new ArrayList<>();
                        for (byte[] p : pobraneplikibytes) {
                            pobranefaktury.addAll(ReadXLSMurawskiFile.getListafakturXLS(p, k, klienciDAO, rodzajdok, tabelanbpDAO, wpisView.getMiesiacWpisu()));
                        }
                    }
                    break;
                case 6:
                    pobranefaktury = ReadXLSMuchaFile.getListafakturXLS(pobranyplik, k, klienciDAO, rodzajdok, wpisView.getMiesiacWpisu());
                    break;
                case 7:
                    Object[] zwrot = null;
                    if (wpisView.getRokWpisuSt().equals("2020")) {
                        zwrot = ReadXMLZorinOptimaFile.getListafakturXLS2020(pobranyplik, k, klienciDAO, rodzajdok, jakipobor, wpisView.getMiesiacWpisu(), dokDAOfk, wpisView);
                    } else {
                        zwrot = ReadXMLZorinOptimaFile.getListafakturXLS(pobranyplik, k, klienciDAO, rodzajdok, jakipobor, wpisView.getMiesiacWpisu(), dokDAOfk, wpisView);
                    }
                    pobranefaktury = (List<InterpaperXLS>) zwrot[0];
                    przerwanyimport = (List<InterpaperXLS>) zwrot[1];
                    importyzbrakami = (List<InterpaperXLS>) zwrot[2];
                    innyokres = (List<InterpaperXLS>) zwrot[3];
                    break;
                case 8:
                    Object[] zwrot1 = ReadXMLSevenFile.getListafaktur(pobranyplik, k, klienciDAO, rodzajdok, jakipobor, wpisView.getMiesiacWpisu());
                    pobranefaktury = (List<InterpaperXLS>) zwrot1[0];
                    przerwanyimport = (List<InterpaperXLS>) zwrot1[1];
                    importyzbrakami = (List<InterpaperXLS>) zwrot1[2];
                    innyokres = (List<InterpaperXLS>) zwrot1[3];
                    break;
                case 9:
                    Object[] zwrot2 = ReadXMLK3FFile.getListafaktur(pobranyplik, k, klienciDAO, rodzajdok, jakipobor, wpisView.getMiesiacWpisu());
                    pobranefaktury = (List<InterpaperXLS>) zwrot2[0];
                    przerwanyimport = (List<InterpaperXLS>) zwrot2[1];
                    importyzbrakami = (List<InterpaperXLS>) zwrot2[2];
                    innyokres = (List<InterpaperXLS>) zwrot2[3];
                    break;
                case 10:
                    Object[] zwrot3 = ReadXMLK3FFile.getListafaktur(pobranyplik, k, klienciDAO, rodzajdok, jakipobor, wpisView.getMiesiacWpisu());
                    pobranefaktury = (List<InterpaperXLS>) zwrot3[0];
                    przerwanyimport = (List<InterpaperXLS>) zwrot3[1];
                    importyzbrakami = (List<InterpaperXLS>) zwrot3[2];
                    innyokres = (List<InterpaperXLS>) zwrot3[3];
                    break;
                 case 11:
                    Object[] zwrot4 = ReadBudInstalFile.getListafaktur(pobranyplik, k, klienciDAO, rodzajdok, jakipobor, wpisView.getMiesiacWpisu());
                    pobranefaktury = (List<InterpaperXLS>) zwrot4[0];
                    przerwanyimport = (List<InterpaperXLS>) zwrot4[1];
                    importyzbrakami = (List<InterpaperXLS>) zwrot4[2];
                    innyokres = (List<InterpaperXLS>) zwrot4[3];
                    break;
                case 12:
                    Object[] zwrot4a = ReadXLSAGLPFile.getListafakturXLS(pobranyplik, k, klienciDAO, rodzajdok, wpisView.getMiesiacWpisu());
                    pobranefaktury = (List<InterpaperXLS>) zwrot4a[0];
                    przerwanyimport = (List<InterpaperXLS>) zwrot4a[1];
                    importyzbrakami = (List<InterpaperXLS>) zwrot4a[2];
                    innyokres = (List<InterpaperXLS>) zwrot4a[3];
                    break;
                case 13:
                    pobranefaktury = ReadXLSDomeguruFile.getListafakturXLS(pobranyplik, k, klienciDAO, rodzajdok, wpisView.getMiesiacWpisu());
                    break;
                case 14:
                    Object[] zwrot5 = SzyszkoFile.getListafaktur(pobranyplik, k, klienciDAO, rodzajdok, jakipobor, wpisView.getMiesiacWpisu());
                    pobranefaktury = (List<InterpaperXLS>) zwrot5[0];
                    przerwanyimport = (List<InterpaperXLS>) zwrot5[1];
                    importyzbrakami = (List<InterpaperXLS>) zwrot5[2];
                    innyokres = (List<InterpaperXLS>) zwrot5[3];
                    break;
                case 15:
                    Object[] zwrot6 = ReadCSVAmznVendorFile.getListafaktur(pobranyplik, k, klienciDAO, rodzajdok, jakipobor, wpisView.getMiesiacWpisu());
                    pobranefaktury = (List<InterpaperXLS>) zwrot6[0];
                    przerwanyimport = (List<InterpaperXLS>) zwrot6[1];
                    importyzbrakami = (List<InterpaperXLS>) zwrot6[2];
                    innyokres = (List<InterpaperXLS>) zwrot6[3];
                    break;
                case 16:
                    Object[] zwrot7 = ReadCSVAmznJsonFile.getListafaktur(pobranyplik, k, klienciDAO, rodzajdok, jakipobor, wpisView.getMiesiacWpisu(), tabelanbpDAO);
                    pobranefaktury = (List<InterpaperXLS>) zwrot7[0];
                    przerwanyimport = (List<InterpaperXLS>) zwrot7[1];
                    importyzbrakami = (List<InterpaperXLS>) zwrot7[2];
                    innyokres = (List<InterpaperXLS>) zwrot7[3];
                    break;
            }
            sumujnadole(pobranefaktury);
            if (jakipobor!=null) {
//                if (jakipobor.equals("wszystko")) {
//                    
//                } else if (jakipobor.equals("fiz")) {
//                    for (Iterator<InterpaperXLS> it = pobranefaktury.iterator(); it.hasNext();) {
//                        InterpaperXLS f = it.next();
//                        if (f.getNip()!=null && !f.getNip().equals("")) {
//                            it.remove();
//                        }
//                    }
//                } else {
//                    for (Iterator<InterpaperXLS> it = pobranefaktury.iterator(); it.hasNext();) {
//                        InterpaperXLS f = it.next();
//                        if (f.getNip()==null) {
//                            it.remove();
//                        }
//                    }
//                }
            }
            if (wybranyrodzajimportu.getLp()==2 && (rodzajdok.equals("sprzedaż NIP") || rodzajdok.contains("zakup") || rodzajdok.contains("WNT") || rodzajdok.contains("IU"))) {
                generujbutton.setRendered(true);
                drkujfizbutton.setRendered(true);
                kontobutton.setRendered(true);kontobutton2.setRendered(true);
            } else if (wybranyrodzajimportu.getLp()==1 && (rodzajdok.equals("sprzedaż os.fiz"))) {
                generujbutton.setRendered(true);
                drkujfizbutton.setRendered(true);
                kontobutton.setRendered(true);kontobutton2.setRendered(true);
            } else if (wybranyrodzajimportu.getLp()==1 && (rodzajdok.equals("sprzedaż") || rodzajdok.contains("zakup") || rodzajdok.contains("WNT") || rodzajdok.contains("IU"))){
                drkujfizbutton.setRendered(true);
                generujbutton.setRendered(true);
                kontobutton.setRendered(true);kontobutton2.setRendered(true);
            } else if (wybranyrodzajimportu.getLp()==3 && (rodzajdok.equals("sprzedaż") || rodzajdok.equals("zakup") || rodzajdok.contains("WNT") || rodzajdok.contains("IU"))){
                drkujfizbutton.setRendered(true);
                generujbutton.setRendered(true);
                kontobutton.setRendered(true);kontobutton2.setRendered(true);
            } else if (wybranyrodzajimportu.getLp()==4 && (rodzajdok.equals("sprzedaż") || rodzajdok.contains("zakup") || rodzajdok.contains("WNT") || rodzajdok.contains("IU"))){
                drkujfizbutton.setRendered(true);
                generujbutton.setRendered(true);
                kontobutton.setRendered(true);kontobutton2.setRendered(true);
            } else if (wybranyrodzajimportu.getLp()==7 && (jakipobor.equals("fiz"))){
                drkujfizbutton.setRendered(true);
                generujbutton.setRendered(true);
                kontobutton.setRendered(true);kontobutton2.setRendered(true);
            }else {
                if (jakipobor==null||jakipobor.equals("fiz")) {
                    drkujfizbutton.setRendered(true);
                } else {
                    drkujfizbutton.setRendered(true);
                    kontobutton.setRendered(true);kontobutton2.setRendered(true);
                    generujbutton.setRendered(true);
                }
                
                //generujbutton.setRendered(true);
            }
            Msg.msg("Pobrano wszystkie dane");
        } catch (OfficeXmlFileException e1) {
            E.e(e1);
            Msg.msg("e", "Niewłaściwa wersja pliku. Może problem z kodowaniem");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd przy pobieraniu danych "+E.e(e));
        }
//        for (InterpaperXLS p : pobranefaktury) {
//           generowanieDokumentu(p);
//        }
    }
    
    public void grid0pokaz() {
        if (sabraki==false) {
            grid0.setRendered(true);
            pobraneplikibytes = new ArrayList<>();
            Msg.msg("i","Wybranonastępujący format importu "+wybranyrodzajimportu.getOpis());
            
        } else {
            Msg.msg("e", "Są braki. Nie można wszytać pliku");
        }
    }
    
    private HashMap<String,Konto> pobierzmapekont1() {
        HashMap<String,Konto> konta = new HashMap<>();
        konta.put("Niemcy", kontoDAO.findKonto("731-2-3-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Francja", kontoDAO.findKonto("731-2-3-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        return konta;
    }
    
    private HashMap<String,Konto> pobierzmapekont() {
        HashMap<String,Konto> konta = new HashMap<>();
        konta.put("Portugalia", kontoDAO.findKonto("731-2-2-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Polinezja", kontoDAO.findKonto("731-2-2-3", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Malta", kontoDAO.findKonto("731-2-2-4", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Austria", kontoDAO.findKonto("731-2-2-5", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Włochy", kontoDAO.findKonto("731-2-2-6", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Słowacja", kontoDAO.findKonto("731-2-2-7", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Holandia", kontoDAO.findKonto("731-2-2-8", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Belgia", kontoDAO.findKonto("731-2-2-9", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Irlandia", kontoDAO.findKonto("731-2-2-10", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Francja", kontoDAO.findKonto("731-2-2-11", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Litwa", kontoDAO.findKonto("731-2-2-12", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Wielka Brytania", kontoDAO.findKonto("731-2-2-13", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Hiszpania", kontoDAO.findKonto("731-2-2-14", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Dania", kontoDAO.findKonto("731-2-2-15", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Luksemburg", kontoDAO.findKonto("731-2-2-16", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Monako", kontoDAO.findKonto("731-2-2-17", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Łotwa", kontoDAO.findKonto("731-2-2-18", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Finlandia", kontoDAO.findKonto("731-2-2-19", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Grecja", kontoDAO.findKonto("731-2-2-20", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Estonia", kontoDAO.findKonto("731-2-2-21", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Jersey", kontoDAO.findKonto("731-2-2-22", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Słowenia", kontoDAO.findKonto("731-2-2-23", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Rumunia", kontoDAO.findKonto("731-2-2-24", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Chorwacja", kontoDAO.findKonto("731-2-2-25", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Szwecja", kontoDAO.findKonto("731-2-2-26", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Islandia", kontoDAO.findKonto("731-2-2-27", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Bułgaria", kontoDAO.findKonto("731-2-2-28", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Czechy", kontoDAO.findKonto("731-2-2-30", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Eksport poza UE", kontoDAO.findKonto("731-2-2-31", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Lichtenstein", kontoDAO.findKonto("731-2-2-32", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Guernsey", kontoDAO.findKonto("731-2-2-33", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Szwajcaria", kontoDAO.findKonto("731-2-2-34", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Cypr", kontoDAO.findKonto("731-2-2-35", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Arabia Saudyjska", kontoDAO.findKonto("731-2-2-36", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Norwegia", kontoDAO.findKonto("731-2-2-37", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Węgry", kontoDAO.findKonto("731-2-2-38", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        konta.put("Polska", kontoDAO.findKonto("702-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu()));
        return konta;
    }
    
    
    public void generuj() {
        if (pobranefaktury !=null && pobranefaktury.size()>0) {
            List<Klienci> k = klienciDAO.findAll();
            int ile = 0;
            if (wybranyrodzajimportu.getLp()==7&&jakipobor.equals("fiz")) {
                List tabelazbiorcza = sumujpozycje(pobranefaktury);
                List tabelarachsp = new ArrayList();
                List tabelaszue = new ArrayList();
               for (Object p : tabelazbiorcza) {
                   Object[] r = ((List) p).toArray();
                   if (!r[8].equals("23.0%")&&!r[8].equals("8.0%")) {
                       tabelarachsp.add(p);
                       System.out.println("");
                   } else {
                       tabelaszue.add(p);
                       System.out.println("");
                   }
               }
               Konto kontorozrachunkowe = kontoDAO.findKonto("203-3-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
               Klienci klient = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
               //to byl chuba ZORIN
               HashMap<String,Konto> listakont = pobierzmapekont1();
               int kolejny = 0;
               Konto kontovat = kontoDAO.findKonto("223", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
               for (Object listarachunkow : tabelarachsp) {
                   try {
                        String nrfaktury = null;
                        Dokfk duplikat = null;
                        do {
                            ++kolejny;
                            nrfaktury = kolejny+"/"+wpisView.getMiesiacWpisu()+"/OptimaImp";
                            duplikat = dokDAOfk.findDokfkSzcz("RACHSP", wpisView.getRokWpisuSt(), wpisView.getPodatnikObiekt(), nrfaktury, klient);
                        } while (duplikat!=null);
                        String rodzajdk = "RACHSP";
                        Object[] r = ((List) listarachunkow).toArray();
                        InterpaperXLS interpaperXLS = new InterpaperXLS(r, wpisView, klient, nrfaktury);
                        String opis = "pobrane z xml vat lokalny "+r[0]+" w "+r[1];
                        Konto kontonetto = listakont.get((String)r[0]);
                        if (kontonetto==null) {
                            kontonetto = kontoDAO.findKonto("702-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
                        }
                        String stawkavat = (String)r[8];
                        Dokfk dokument = stworznowydokumentZorin(oblicznumerkolejny(rodzajdk),interpaperXLS, rodzajdk, k, opis, true, kontorozrachunkowe, kontonetto, kontovat, stawkavat);
                        dokDAOfk.create(dokument);
                   } catch (Exception e) {
                       Msg.msg("e","Nie udało się zachować dokumentu");
                   }
               }
               listakont = pobierzmapekont();
               kolejny = 0;
               kontovat = kontoDAO.findKonto("221-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
               for (Object p : tabelaszue) {
                   try {
                        String nrfaktury = null;
                        Dokfk duplikat = null;
                        do {
                            ++kolejny;
                            nrfaktury = kolejny+"/"+wpisView.getMiesiacWpisu()+"/OptimaImp/VAT";
                            duplikat = dokDAOfk.findDokfkSzcz("SZUE", wpisView.getRokWpisuSt(), wpisView.getPodatnikObiekt(), nrfaktury, klient);
                        } while (duplikat!=null);
                        String rodzajdk = "SZUE";
                        Object[] r = ((List) p).toArray();
                        String opis = "pobrane z xml sprzedaż bez vat "+r[0]+" w "+r[1];
                        Konto kontonetto = listakont.get((String)r[0]);
                        if (kontonetto==null) {
                            kontonetto = kontoDAO.findKonto("702-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
                        }
                        InterpaperXLS interpaperXLS = new InterpaperXLS(r, wpisView, klient, nrfaktury);
                        String stawkavat = (String)r[8];
                        Dokfk dokument = stworznowydokumentZorin(oblicznumerkolejny(rodzajdk),interpaperXLS, rodzajdk, k, opis, true, kontorozrachunkowe, kontonetto, kontovat, stawkavat);
                        dokDAOfk.create(dokument);
                   } catch (Exception e) {
                       Msg.msg("e","Nie udało się zachować dokumentu ");
                   }
               }
               Msg.msg("Zaksiegowano dokumenty");
               System.out.println("");
            } else {
                //tu generujemy dokumenty dla firm
                Konto kontovatzagr = kontoDAO.findKonto("223", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
                Konto kontovatpol = kontoDAO.findKonto("221-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
                if (wpisView.getPodatnikObiekt().getNip().equals("9552538274")) {
                    Konto kontozaliczki2062 = kontoDAO.findKonto("206-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
                    List<Konto> kontazaliczkowepotomne = kontoDAO.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontozaliczki2062);
                    Map<String,Konto> mapakrajkontoRozrachunki = kontazaliczkowepotomne.stream().collect(Collectors.toMap(Konto::getNazwaskrocona, Function.identity()));
                    Konto kontozaliczki844 = kontoDAO.findKonto("844-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
                    List<Konto> kontazaliczkoweNettopotomne = kontoDAO.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontozaliczki844);
                    Map<String,Konto> mapakrajkontoNetto = kontazaliczkoweNettopotomne.stream().collect(Collectors.toMap(Konto::getNazwaskrocona, Function.identity()));
                    Konto kontovat223 = kontoDAO.findKonto("223", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
                    List<Konto> kontavatzagrpotomne = kontoDAO.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontovat223);
                    Map<String,Konto> mapakontavatzagr = kontavatzagrpotomne.stream().collect(Collectors.toMap(Konto::getNazwaskrocona, Function.identity()));
                    for (InterpaperXLS p : pobranefaktury) {
                        if (p.getNip()==null||p.getNip().equals("")) {
                            
                        }
                        ile += generowanieDokumentuDomeguru(p, k, kontovatzagr, kontovatpol, mapakrajkontoRozrachunki, mapakrajkontoNetto, mapakontavatzagr);
                    }
                    Msg.msg("Zaksięgowano "+ile+" dokumentów Domeguru");
                    
                } else if (selected !=null && selected.size()>0) {
                    for (InterpaperXLS p : selected) {
                        if (p.getNip()!=null) {
                            ile += generowanieDokumentu(p, k, kontovatzagr, kontovatpol);
                        }
                    }
                    Msg.msg("Zaksięgowano "+ile+" z wybranych dokumentów");
                } else  if (pobranefakturyfilter !=null && pobranefakturyfilter.size()>0) {
                    for (InterpaperXLS p : pobranefakturyfilter) {
                        if (p.getNip()!=null) {
                            ile += generowanieDokumentu(p, k, kontovatzagr, kontovatpol);
                        }
                    }
                    Msg.msg("Zaksięgowano "+ile+" z przefiltrowanych dokumentów");
                } else {
                    for (InterpaperXLS p : pobranefaktury) {
                       if (p.getNip()!=null) {
                            ile += generowanieDokumentu(p, k, kontovatzagr, kontovatpol);
                       }
                    }
                    Msg.msg("Zaksięgowano "+ile+" dokumentów");
                }
            }
        } else {
            Msg.msg("e", "Błąd! Lista danych źrdółowych jest pusta");
        }
    }
    
    private List sumujpozycje(List<InterpaperXLS> pobranefaktury) {
        Set<String> waluty = pobierzwaluty(pobranefaktury);
            Set<String> kraje = pobierzkraje(pobranefaktury);
            List tabelazbiorcza = new ArrayList<>();
            if (kraje!=null&&kraje.size()>1) {
                for (String kraj : kraje) {
                    List<InterpaperXLS> sprzedazkraj = pobranefaktury.stream().filter((p)->p.getKlientpaństwo().equals(kraj)).collect(Collectors.toList());
                    for (String waluta : waluty) {
                        List<InterpaperXLS> sprzedazwaluty = sprzedazkraj.stream().filter((p)->p.getWalutaplatnosci().equals(waluta)).collect(Collectors.toList());
                        if (!sprzedazwaluty.isEmpty()) {
                            dodajsumy(sprzedazwaluty, waluta, tabelazbiorcza, kraj);
                        }
                    }
                }
            } else {
                for (String waluta : waluty) {
                        List<InterpaperXLS> sprzedazwaluty = pobranefaktury.stream().filter((p)->p.getWalutaplatnosci().equals(waluta)).collect(Collectors.toList());
                        if (!sprzedazwaluty.isEmpty()) {
                            dodajsumy(sprzedazwaluty, waluta, tabelazbiorcza, "PL");
                        }
                    }
            }
            return tabelazbiorcza;
    }
    
    public static void dodajsumy(List<InterpaperXLS> lista, String waluta, List tabelazbiorcza,String kraj) {
        double nettowaluta = 0.0;
        double vatwaluta = 0.0;
        double nettopl = 0.0;
        double vatpl = 0.0;
        String vatstawka = null;
        for (InterpaperXLS p : lista) {
            nettopl = Z.z(nettopl+p.getNettoPLN());
            vatpl = Z.z(vatpl+p.getVatPLN());
            nettowaluta = Z.z(nettowaluta+p.getNettowaluta());
            vatwaluta = Z.z(vatwaluta+p.getVatwaluta());
            if (!p.getVatstawka().equals("błąd!")) {
                vatstawka = p.getVatstawka();
            } else {
                System.out.println("");
            }
        }
        double bruttopln = Z.z(nettopl+vatpl);
        double bruttowal = Z.z(nettowaluta+vatwaluta);
        Object[] a = new Object[]{kraj, waluta, nettowaluta, vatwaluta, bruttowal, nettopl, vatpl, bruttopln, vatstawka, lista.size()};
        tabelazbiorcza.add(Arrays.asList(a));
        
    }
    
    private Set<String> pobierzwaluty(List<InterpaperXLS> lista) {
        Set<String> zwrot = new HashSet<>();
        for (InterpaperXLS p : lista) {
            String poz = p.getWalutaplatnosci();
            if (poz!=null) {
                zwrot.add(poz);
            }
        }
        return zwrot;
    }

    private Set<String> pobierzkraje(List<InterpaperXLS> lista) {
        Set<String> zwrot = new HashSet<>();
        for (InterpaperXLS p : lista) {
            String poz = p.getKlientpaństwo();
            if (poz!=null) {
                zwrot.add(poz);
            }
        }
        return zwrot;
    }
    
     public int generowanieDokumentu(InterpaperXLS interpaperXLS, List<Klienci> k, Konto kontovatzagr, Konto kontovatpol) {
        int ile = 0;
        try {
            int polska0unia1zagranica2 = 0;
            if (interpaperXLS.getKlient().getKrajnazwa()!=null && !interpaperXLS.getKlient().getKrajkod().equals("PL")) {
                polska0unia1zagranica2 = 2;
                if (PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod())) {
                    if (interpaperXLS.getVatPLN()!=0.0) {
                        polska0unia1zagranica2 = 0;
                    } else if (interpaperXLS.getKlient().getKrajkod().equals("GB")){
                        polska0unia1zagranica2 = 2;
                    }  else {
                        polska0unia1zagranica2 = 1;
                    }
                }
            }
            String rodzajdk = "ZZ";
            Dokfk dokument = null;
            if (this.rodzajdok.contains("sprzedaż")) {
                if (wybranyrodzajimportu.getLp()==1) {
                    rodzajdk = polska0unia1zagranica2==0 ? "SZ" : polska0unia1zagranica2==1 ? "UPTK100" : "UPTK";
                } else if (wpisView.getPodatnikObiekt().getNip().equals("8522677786")) {
                    rodzajdk = polska0unia1zagranica2==0 ? "SZ" : polska0unia1zagranica2==1 ? "UPTK100" : "UPTK";
                }else {
                    if (Z.z(interpaperXLS.getVatPLN())==0.0&&interpaperXLS.getVatwaluta()==0.0) {
                        rodzajdk = polska0unia1zagranica2==2 ? "EXP" : "WDT";
                    } else {
                        rodzajdk = "SZ";
                    }
                        
                }
                dokument = stworznowydokument(oblicznumerkolejny(rodzajdk),interpaperXLS, rodzajdk, k, "przychody ze sprzedaży", true, kontovatzagr, kontovatpol);
            } else {
                if (this.rodzajdok.equals("zakup")) {
                    rodzajdk = "ZZ";
                    if (interpaperXLS.getVatPLN()!=0.0 && interpaperXLS.getKlientpaństwo()!=null && !interpaperXLS.getKlientpaństwo().equals("Polska")) {
                        rodzajdk = "RACH";
                    } else if (interpaperXLS.getKlientpaństwo()!=null && !interpaperXLS.getKlientpaństwo().equals("Polska")) {
                        rodzajdk = "IU";
                    } else if (interpaperXLS.getKlient()!=null && !interpaperXLS.getKlient().getKrajkod().equals("PL")) {
                        rodzajdk = "IU";
                    }
                } else if (this.rodzajdok.equals("WNT")) {
                    rodzajdk = "WNT";
                } else if (this.rodzajdok.equals("IU")) {
                    rodzajdk = "IU";
                }
                dokument = stworznowydokument(oblicznumerkolejny(rodzajdk),interpaperXLS, rodzajdk, k, "zakup towarów/usług/koszty", true, kontovatzagr, kontovatpol);
            }
            
            try {
                if (dokument!=null) {
                    dokument.setImportowany(true);
                    try {
                        dokDAOfk.create(dokument);
                    } catch (Exception ex) {
                        Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu "+dokument.getNumerwlasnydokfk());
                    }
                    interpaperXLS.setSymbolzaksiegowanego(dokument.getDokfkSN());
                    ile = 1;
                }
            } catch (Exception e) {
                ile = 0;
                Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu "+rodzajdok);
            }
        } catch (Exception e) {
            ile = 0;
            E.e(e);
            Msg.msg("e","Błąd przy generowaniu dokumentu "+interpaperXLS.getNrfaktury());
        }
        return ile;
    }
     
     public int generowanieDokumentuDomeguru(InterpaperXLS interpaperXLS, List<Klienci> k, Konto kontovatzagr, Konto kontovatpol, Map<String, Konto> mapakont206, Map<String, Konto> mapakont844, Map<String, Konto> mapakont223) {
        int ile = 0;
        try {
            int polska0unia1zagranica2 = 0;
            if (interpaperXLS.getKlient().getKrajnazwa()!=null && !interpaperXLS.getKlient().getKrajkod().equals("PL")) {
                polska0unia1zagranica2 = 2;
                if (PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod())) {
                    if (interpaperXLS.getVatPLN()!=0.0) {
                        polska0unia1zagranica2 = 0;
                    } else if (interpaperXLS.getKlient().getKrajkod().equals("GB")){
                        polska0unia1zagranica2 = 2;
                    }  else {
                        polska0unia1zagranica2 = 1;
                    }
                }
            }
            String rodzajdk = "ZZ";
            Dokfk dokument = null;
            if (wybranyrodzajimportu.getLp()==1&&this.rodzajdok.contains("sprzedaż")) {
                rodzajdk = polska0unia1zagranica2==0 ? "RACHSP" : polska0unia1zagranica2==1 ? "UPTK100" : "UPTK";
            } else if (this.rodzajdok.contains("sprzedaż")) {
                if (wybranyrodzajimportu.getLp()==13) {
                    if (Z.z(interpaperXLS.getVatwaluta())==0.0) {
                        rodzajdk = polska0unia1zagranica2==2 ? "EXP" : "RACHSP";
                    } else {
                        rodzajdk = "RACHSP";
                    }
                    if (interpaperXLS.getNrfaktury().contains("ZAL")) {
                        String nazwaskrocona = interpaperXLS.getKlientpaństwosymbol();
                        String nazwaskroconaOF = interpaperXLS.getKlientpaństwosymbol()+"OF";
                        Konto rozrachunek = mapakont206.get(nazwaskrocona);
                        Konto kontonetto = mapakont844.get(nazwaskrocona);
                        if (!interpaperXLS.getKlientpaństwosymbol().equals("PL")&interpaperXLS.getVatwaluta()>0.0) {
                            kontovatzagr = mapakont223.get(nazwaskrocona);
                        }
                        if (interpaperXLS.getNip()==null||interpaperXLS.getNip().equals("")) {
                            Klienci klient = interpaperXLS.getKlient();
                            klient.setNip(wygenerujnip(klienciDAO));
                            kontonetto = mapakont844.get(nazwaskroconaOF);
                        } else if (interpaperXLS.getNip().startsWith("XX")) {
                            kontonetto = mapakont844.get(nazwaskroconaOF);
                        }
                        dokument = stworznowydokumentDomeguruZaliczka(oblicznumerkolejny(rodzajdk),interpaperXLS, rodzajdk, "przychody ze sprzedaży", true, kontovatzagr, kontovatpol, kontonetto, rozrachunek);
                    } 
                } 
            }
            try {
                if (dokument!=null) {
                    dokument.setImportowany(true);
                    try {
                        if (dokument.getKontr().getId()==null) {
                            klienciDAO.create(dokument.getKontr());
                        }
                        dokDAOfk.create(dokument);
                    } catch (Exception ex) {
                        Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu "+dokument.getNumerwlasnydokfk());
                    }
                    interpaperXLS.setSymbolzaksiegowanego(dokument.getDokfkSN());
                    ile = 1;
                }
            } catch (Exception e) {
                ile = 0;
                Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu "+rodzajdok);
            }
        } catch (Exception e) {
            ile = 0;
            E.e(e);
            Msg.msg("e","Błąd przy generowaniu dokumentu "+interpaperXLS.getNrfaktury());
        }
        return ile;
    }
     
      private String wygenerujnip(KlienciDAO klDAO) {
        String wygenerowanynip = null;
        Pattern pattern = Pattern.compile("X{2}\\d{10}");
        try {
            List<String> nipy = klDAO.findKlientByNipXX();
            Collections.sort(nipy);
            Integer max = 0;
            boolean szukaj = true;
            int licznik = 1;
            int nipysize = nipy.size();
            while (szukaj && licznik<nipysize) {
                if (nipysize > 0) {
                    String pozycja = nipy.get(nipysize - licznik);
                    Matcher m = pattern.matcher(pozycja.toUpperCase());
                    boolean czypasuje = m.matches();
                    if (czypasuje) {
                        max = Integer.parseInt(pozycja.substring(2));
                        max++;
                        szukaj = false;
                        break;
                    } else {
                        licznik++;
                    }
                }
            }
            //uzupelnia o zera i robi stringa;
            wygenerowanynip = max.toString();
            while (wygenerowanynip.length() < 10) {
                wygenerowanynip = "0" + wygenerowanynip;
            }
            wygenerowanynip = "XX" + wygenerowanynip;
        } catch (Exception e) {
            E.e(e);
        }
        return wygenerowanynip;
    }
    
     
      private Dokfk stworznowydokument(int numerkolejny, InterpaperXLS interpaperXLS, String rodzajdok, List<Klienci> k, String opis, boolean nieprzeliczajwalut, Konto kontovatzagr, Konto kontovatpol) {
        Dokfk nd = new Dokfk(numerkolejny, wpisView.getRokWpisuSt());
        nd.setNieprzeliczaj(nieprzeliczajwalut);
        ustawrodzajedok(nd, rodzajdok);
        ustawdaty(nd, interpaperXLS);
        nd.setKontr(interpaperXLS.getKlient());
        ustawnumerwlasny(nd, interpaperXLS);
        nd.setOpisdokfk(opis);
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        ustawtabelenbp(nd, interpaperXLS);
        przewalutuj(nd, interpaperXLS);
        Konto kontovat = kontovatzagr;
        if (nd.getRodzajedok().getKategoriadokumentu()==1||nd.getRodzajedok().getKategoriadokumentu()==2) {
            if (interpaperXLS.getPobranastawkavat()==23.0||interpaperXLS.getPobranastawkavat()==0.0) {
                podepnijEwidencjeVat(nd, interpaperXLS);
                kontovat = kontovatpol;
            } else {
                System.out.println("");
            }
        }
        Dokfk juzjest = dokDAOfk.findDokfkObjKontrahent(nd);
        if (juzjest!=null || nd.getKontr()==null) {
            nd = null;
            interpaperXLS.setJuzzaksiegowany(true);
        } else {
            ustawwiersze(nd, interpaperXLS, kontovat);
            nd.setImportowany(true);
            nd.setWprowadzil(wpisView.getUzer().getLogin());
            nd.przeliczKwotyWierszaDoSumyDokumentu();
        }
        return nd;
    }
      
      private Dokfk stworznowydokumentDomeguruZaliczka(int numerkolejny, InterpaperXLS interpaperXLS, String rodzajdok, String opis, boolean nieprzeliczajwalut, Konto kontovatzagr, Konto kontovatpol, Konto kontonetto, Konto rozrachunek) {
        Dokfk nd = new Dokfk(numerkolejny, wpisView.getRokWpisuSt());
        nd.setNieprzeliczaj(nieprzeliczajwalut);
        ustawrodzajedok(nd, rodzajdok);
        ustawdaty(nd, interpaperXLS);
        nd.setKontr(interpaperXLS.getKlient());
        ustawnumerwlasny(nd, interpaperXLS);
        nd.setOpisdokfk(opis);
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        ustawtabelenbp(nd, interpaperXLS);
        przewalutuj(nd, interpaperXLS);
        Konto kontovat = kontovatzagr;
        if (nd.getRodzajedok().getKategoriadokumentu()==1||nd.getRodzajedok().getKategoriadokumentu()==2) {
            if (interpaperXLS.getKlientpaństwo().equals("Polska")) {
                podepnijEwidencjeVat(nd, interpaperXLS);
                kontovat = kontovatpol;
            } else if (interpaperXLS.getVatwaluta()>0.0){
                podepnijEwidencjeVat(nd, interpaperXLS);
            } else if (nd.getRodzajedok().getSkrot().equals("EXP")||nd.getRodzajedok().getSkrot().equals("WDT")){
                podepnijEwidencjeVat(nd, interpaperXLS);
            }
        }
        Dokfk juzjest = dokDAOfk.findDokfkObjKontrahent(nd);
        if (juzjest!=null || nd.getKontr()==null) {
            nd = null;
            interpaperXLS.setJuzzaksiegowany(true);
        } else {
            ustawwierszeDomeguruZaliczka(nd, interpaperXLS, kontovat, kontonetto, rozrachunek);
            nd.setImportowany(true);
            nd.setWprowadzil(wpisView.getUzer().getLogin());
            nd.przeliczKwotyWierszaDoSumyDokumentu();
        }
        return nd;
    }
      
     private Dokfk stworznowydokumentZorin(int numerkolejny, InterpaperXLS interpaperXLS, String rodzajdok, List<Klienci> k, String opis, boolean nieprzeliczajwalut, Konto kontorozrachunkowe, Konto kontonetto, Konto kontovat, String stawkavat) {
        Dokfk nd = new Dokfk(numerkolejny, wpisView.getRokWpisuSt());
        nd.setNieprzeliczaj(nieprzeliczajwalut);
        ustawrodzajedok(nd, rodzajdok);
        ustawdaty(nd, interpaperXLS);
        nd.setKontr(interpaperXLS.getKlient());
        ustawnumerwlasny(nd, interpaperXLS);
        nd.setOpisdokfk(opis);
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        ustawtabelenbp(nd, interpaperXLS);
        przewalutuj(nd, interpaperXLS);
        if (nd.getRodzajedok().getKategoriadokumentu()==1||nd.getRodzajedok().getKategoriadokumentu()==2) {
             if (stawkavat.equals("23.0%")||stawkavat.equals("0.0%")) {
                podepnijEwidencjeVat(nd, interpaperXLS);
             }
        }
        Dokfk juzjest = dokDAOfk.findDokfkObjKontrahent(nd);
        if (juzjest!=null || nd.getKontr()==null) {
            nd = null;
            interpaperXLS.setJuzzaksiegowany(true);
        } else {
            ustawwierszeZorin(nd, interpaperXLS, kontorozrachunkowe, kontonetto, kontovat);
            nd.setImportowany(true);
            nd.setWprowadzil(wpisView.getUzer().getLogin());
            nd.przeliczKwotyWierszaDoSumyDokumentu();
        }
        return nd;
    }
      
    private void przewalutuj(Dokfk nd, InterpaperXLS interpaperXLS) {
        Tabelanbp t = nd.getTabelanbp();
        if (t!=null && !t.getWaluta().getSymbolwaluty().equals("PLN")) {
            if (nd.isNieprzeliczaj()==false) {
                interpaperXLS.setNettoPLN(Z.z(interpaperXLS.getNettowaluta()*t.getKurssredniPrzelicznik()));
                interpaperXLS.setVatPLN(Z.z(interpaperXLS.getVatwaluta()*t.getKurssredniPrzelicznik()));
            }
        } else {
            interpaperXLS.setNettoPLN(interpaperXLS.getNettowaluta());
            interpaperXLS.setVatPLN(interpaperXLS.getVatPLN());
        }
        interpaperXLS.setBruttowaluta(Z.z(interpaperXLS.getNettowaluta()+interpaperXLS.getVatwaluta()));
        interpaperXLS.setBruttoPLN(Z.z(interpaperXLS.getNettoPLN()+interpaperXLS.getVatPLN()));
    }
      
    private int oblicznumerkolejny(String rodzajdok) {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), rodzajdok, wpisView.getRokWpisuSt());
        return poprzednidokumentvat == null ? 1 : poprzednidokumentvat.getNrkolejnywserii() + 1;
    }
    
    private void ustawdaty(Dokfk nd, InterpaperXLS interpaperXLS) {
        Format formatterX = new SimpleDateFormat("yyyy-MM-dd");
        String datawystawienia = formatterX.format(interpaperXLS.getDatawystawienia());
        String datasprzedazy = formatterX.format(interpaperXLS.getDatasprzedaży());
        if (interpaperXLS.getDataotrzymania()!=null) {
            String dataotrzymania = formatterX.format(interpaperXLS.getDataotrzymania());
            nd.setDatawplywu(dataotrzymania);
            nd.setDatawystawienia(dataotrzymania);
        } else {
            String koniecmiesiaca = Data.ostatniDzien(datawystawienia);
            nd.setDatawystawienia(koniecmiesiaca);
            nd.setDatawplywu(koniecmiesiaca);
        }
        nd.setDataoperacji(datasprzedazy);
        nd.setDatadokumentu(datawystawienia);
        nd.setDataujecia(new Date());
        nd.setMiesiac(wpisView.getMiesiacWpisu());
        if (rodzajdok.contains("sprzedaż")) {
            nd.setVatM(datasprzedazy.split("-")[1]);
            nd.setVatR(datasprzedazy.split("-")[0]);
        } else {
            String dataotrzymania = formatterX.format(interpaperXLS.getDataotrzymania()!=null?interpaperXLS.getDataotrzymania():interpaperXLS.getDatawystawienia());
            nd.setVatM(dataotrzymania.split("-")[1]);
            nd.setVatR(dataotrzymania.split("-")[0]);
            if (nd.getRodzajedok().getSkrotNazwyDok().equals("WNT")) {
                String dataoperacji = formatterX.format(interpaperXLS.getDatasprzedaży());
                nd.setVatM(dataoperacji.split("-")[1]);
                nd.setVatR(dataoperacji.split("-")[0]);
            }
        }
    }
    
//    private void ustawkontrahenta(Dokfk nd, InterpaperXLS interpaperXLS, List<Klienci> k) {
//        try {
//            Klienci klient = null;
//            for (Klienci p : k) {
//                if (p.getNip().contains(interpaperXLS.getNip().trim())) {
//                    klient = p;
//                    break;
//                }
//            }
//            if (klient==null) {
//                for (Klienci p : k) {
//                    if (p.getNpelna().contains(interpaperXLS.getKontrahent().trim()) || p.getNskrocona().contains(interpaperXLS.getKontrahent().trim())) {
//                        klient = p;
//                        break;
//                    }
//                }
//            }
//            if (klient==null) {
//                klient = znajdzdaneregonAutomat(interpaperXLS.getNip().trim());
//            }
//            nd.setKontr(klient);
//            k.add(klient);
//        } catch (Exception e) {
//            
//        }
//    }
    
    public Klienci znajdzdaneregonAutomat(String nip) {
        Klienci zwrot = null;
        try {
            zwrot = SzukajDaneBean.znajdzdaneregonAutomat(nip);
            if (!zwrot.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                klienciDAO.create(zwrot);
                Msg.msg("Zaktualizowano dane klienta pobranymi z GUS");
            }
        } catch (Exception e) {
            Msg.msg("e","Błąd, niezaktualizowano dane klienta pobranymi z GUS");
            E.e(e);
        }
        return zwrot;
    }
    
    private void ustawnumerwlasny(Dokfk nd, InterpaperXLS interpaperXLS) {
        String numer = interpaperXLS.getNrfaktury();
        nd.setNumerwlasnydokfk(numer);
    }
    
    private void ustawrodzajedok(Dokfk nd, String rodzajdok) {
        Rodzajedok rodzajedok = rodzajedokDAO.find(rodzajdok, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        if (rodzajedok != null) {
            nd.setSeriadokfk(rodzajedok.getSkrot());
            nd.setRodzajedok(rodzajedok);
        } else {
            Msg.msg("e", "Brak zdefiniowanego dokumentu "+rodzajdok);
        }
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
    
    private void ustawwiersze(Dokfk nd, InterpaperXLS interpaperXLS, Konto kontovat) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        if (rodzajdok.contains("sprzedaż")) {
            nd.getListawierszy().add(przygotujwierszNetto(interpaperXLS, nd));
            if (interpaperXLS.getVatwaluta() != 0) {
                nd.getListawierszy().add(przygotujwierszVat(interpaperXLS, nd, kontovat));
            }
        } else {
            nd.getListawierszy().add(przygotujwierszNettoK(interpaperXLS, nd));
            if (interpaperXLS.getVatwaluta() != 0) {
                nd.getListawierszy().add(przygotujwierszVatK(interpaperXLS, nd));
            }
        }
    }
    
    private void ustawwierszeDomeguruZaliczka(Dokfk nd, InterpaperXLS interpaperXLS, Konto kontovat, Konto kontonetto, Konto rozrachunek) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        if (rodzajdok.contains("sprzedaż")) {
            nd.getListawierszy().add(przygotujwierszNettoDomeguruZaliczka(interpaperXLS, nd, kontonetto, rozrachunek));
            if (interpaperXLS.getVatwaluta() != 0) {
                nd.getListawierszy().add(przygotujwierszVat(interpaperXLS, nd, kontovat));
            }
        } else {
            nd.getListawierszy().add(przygotujwierszNettoK(interpaperXLS, nd));
            if (interpaperXLS.getVatwaluta() != 0) {
                nd.getListawierszy().add(przygotujwierszVatK(interpaperXLS, nd));
            }
        }
    }
    
    private void ustawwierszeZorin(Dokfk nd, InterpaperXLS interpaperXLS, Konto kontorozrachunkowe, Konto kontonetto, Konto kontovat) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        if (rodzajdok.contains("sprzedaż")) {
            nd.getListawierszy().add(przygotujwierszNettoZorin(interpaperXLS, nd, kontorozrachunkowe, kontonetto));
            if (interpaperXLS.getVatwaluta() != 0) {
                nd.getListawierszy().add(przygotujwierszVat(interpaperXLS, nd, kontovat));
            }
        } else {
            nd.getListawierszy().add(przygotujwierszNettoKZorin(interpaperXLS, nd, kontorozrachunkowe, kontonetto));
            if (interpaperXLS.getVatwaluta() != 0) {
                nd.getListawierszy().add(przygotujwierszVatK(interpaperXLS, nd));
            }
        }
    }

    private Wiersz przygotujwierszNetto(InterpaperXLS interpaperXLS, Dokfk nd) {
        Wiersz w = new Wiersz(1, nd, 0);
        uzupelnijwiersz(w, nd, 0);
        String opiswiersza = nd.getOpisdokfk(); 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", interpaperXLS.getBruttowaluta(), null);
        StronaWiersza strma = new StronaWiersza(w, "Ma", interpaperXLS.getNettowaluta(), null);
        double kurs = nd.getTabelanbp().getKurssredniPrzelicznik();
        double nettopln = interpaperXLS.getNettoPLNvat()!=0.0 ? interpaperXLS.getNettoPLNvat():interpaperXLS.getNettoPLN(kurs);
        double vatpln = interpaperXLS.getVatPLN()!=0.0 ? interpaperXLS.getVatPLN():interpaperXLS.getVatPLN(kurs);
        strwn.setKwotaPLN(Z.z(nettopln+vatpln));
        strma.setKwotaPLN(Z.z(nettopln));
        strma.setKonto(kontodlanetto!=null?kontodlanetto:kontonetto);
        if (kontodlabrutto!=null) {
            strwn.setKonto(kontodlabrutto);
        } else {
            strwn.setKonto(pobierzkontoWn(nd, interpaperXLS, nd.getKontr()));
        }
        w.setStronaWn(strwn);
        w.setStronaMa(strma);
        return w;
    }
    
    private Wiersz przygotujwierszNettoDomeguruZaliczka(InterpaperXLS interpaperXLS, Dokfk nd, Konto kontonetto, Konto rozrachunek) {
        Wiersz w = new Wiersz(1, nd, 0);
        uzupelnijwiersz(w, nd, 0);
        String opiswiersza = nd.getOpisdokfk(); 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", interpaperXLS.getBruttowaluta(), null);
        StronaWiersza strma = new StronaWiersza(w, "Ma", interpaperXLS.getNettowaluta(), null);
        double kurs = nd.getTabelanbp().getKurssredniPrzelicznik();
        double nettopln = interpaperXLS.getNettoPLNvat()!=0.0 ? interpaperXLS.getNettoPLNvat():interpaperXLS.getNettoPLN(kurs);
        double vatpln = interpaperXLS.getVatPLN()!=0.0 ? interpaperXLS.getVatPLN():interpaperXLS.getVatPLN(kurs);
        strwn.setKwotaPLN(Z.z(nettopln+vatpln));
        strma.setKwotaPLN(Z.z(nettopln));
        if (kontonetto!=null) {
             strma.setKonto(kontonetto!=null?kontonetto:kontodlanetto);
        } 
        if (rozrachunek !=null) {
            strwn.setKonto(rozrachunek);   
        } else {
            strwn.setKonto(pobierzkontoWn(nd, interpaperXLS, nd.getKontr()));
        }
        w.setStronaWn(strwn);
        w.setStronaMa(strma);
        return w;
    }
    
    private Wiersz przygotujwierszNettoZorin(InterpaperXLS interpaperXLS, Dokfk nd, Konto kontorozrachunkowe, Konto kontonetto) {
        Wiersz w = new Wiersz(1, nd, 0);
        uzupelnijwiersz(w, nd, 0);
        String opiswiersza = nd.getOpisdokfk(); 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", interpaperXLS.getBruttowaluta(), null);
        StronaWiersza strma = new StronaWiersza(w, "Ma", interpaperXLS.getNettowaluta(), null);
        double kurs = nd.getTabelanbp().getKurssredniPrzelicznik();
        double nettopln = interpaperXLS.getNettoPLNvat()!=0.0 ? interpaperXLS.getNettoPLNvat():interpaperXLS.getNettoPLN(kurs);
        double vatpln = interpaperXLS.getVatPLN()!=0.0 ? interpaperXLS.getVatPLN():interpaperXLS.getVatPLN(kurs);
        strwn.setKwotaPLN(Z.z(nettopln+vatpln));
        strma.setKwotaPLN(Z.z(nettopln));
        strma.setKonto(kontonetto);
        strwn.setKonto(kontorozrachunkowe);
        w.setStronaWn(strwn);
        w.setStronaMa(strma);
        return w;
    }
    
    private Wiersz przygotujwierszVat(InterpaperXLS interpaperXLS, Dokfk nd, Konto kontovat) {
        Wiersz w = new Wiersz(2, nd, 2);
        uzupelnijwiersz(w, nd, 1);
        String opiswiersza = nd.getOpisdokfk()+" - VAT"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strma = new StronaWiersza(w, "Ma", interpaperXLS.getVatwaluta(), null);
        double kurs = nd.getTabelanbp().getKurssredniPrzelicznik();
        double vatpln = interpaperXLS.getVatPLN()!=0.0 ? interpaperXLS.getVatPLN():interpaperXLS.getVatPLN(kurs);
        strma.setKwotaPLN(Z.z(vatpln));
        strma.setKonto(kontovat);
        w.setStronaMa(strma);
        return w;
    }
    
    private Wiersz przygotujwierszVatZorin(InterpaperXLS interpaperXLS, Dokfk nd, Konto kontovat) {
        Wiersz w = new Wiersz(2, nd, 2);
        uzupelnijwiersz(w, nd, 1);
        String opiswiersza = nd.getOpisdokfk()+" - VAT"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strma = new StronaWiersza(w, "Ma", interpaperXLS.getVatwaluta(), null);
        double kurs = nd.getTabelanbp().getKurssredniPrzelicznik();
        double vatpln = interpaperXLS.getVatPLN()!=0.0 ? interpaperXLS.getVatPLN():interpaperXLS.getVatPLN(kurs);
        strma.setKwotaPLN(Z.z(vatpln));
        strma.setKonto(kontovat);
        w.setStronaMa(strma);
        return w;
    }
    
    private Wiersz przygotujwierszNettoK(InterpaperXLS interpaperXLS, Dokfk nd) {
        Wiersz w = new Wiersz(1, nd, 0);
        uzupelnijwiersz(w, nd, 0);
        String opiswiersza = nd.getOpisdokfk(); 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strma = new StronaWiersza(w, "Ma", interpaperXLS.getBruttowaluta(), null);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", interpaperXLS.getNettowaluta(), null);
        double kurs = nd.getTabelanbp().getKurssredniPrzelicznik();
        double nettopln = interpaperXLS.getNettoPLNvat()!=0.0 ? interpaperXLS.getNettoPLNvat():interpaperXLS.getNettoPLN(kurs);
        double vatpln = interpaperXLS.getVatPLN()!=0.0 ? interpaperXLS.getVatPLN():interpaperXLS.getVatPLN(kurs);
        strma.setKwotaPLN(Z.z(nettopln+vatpln));
        strwn.setKwotaPLN(Z.z(nettopln));
        if (nd.getRodzajedok().getSkrotNazwyDok().equals("WNT")) {
            strwn.setKonto(kontodlanetto!=null?kontodlanetto:kontonettotowary);
            strma.setKwotaPLN(Z.z(nettopln));
            strma.setKwota(Z.z(interpaperXLS.getNettowaluta()));
        } else {
            strwn.setKonto(kontodlanetto!=null?kontodlanetto:kontonettokoszt);
        }
        strma.setKonto(pobierzkontoMa(nd, interpaperXLS, nd.getKontr()));
        w.setStronaMa(strma);
        w.setStronaWn(strwn);
        return w;
    }
    
     private Wiersz przygotujwierszNettoKZorin(InterpaperXLS interpaperXLS, Dokfk nd, Konto kontorozrachunkowe, Konto kontonetto) {
        Wiersz w = new Wiersz(1, nd, 0);
        uzupelnijwiersz(w, nd, 0);
        String opiswiersza = nd.getOpisdokfk(); 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strma = new StronaWiersza(w, "Ma", interpaperXLS.getBruttowaluta(), null);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", interpaperXLS.getNettowaluta(), null);
        double kurs = nd.getTabelanbp().getKurssredniPrzelicznik();
        double nettopln = interpaperXLS.getNettoPLNvat()!=0.0 ? interpaperXLS.getNettoPLNvat():interpaperXLS.getNettoPLN(kurs);
        double vatpln = interpaperXLS.getVatPLN()!=0.0 ? interpaperXLS.getVatPLN():interpaperXLS.getVatPLN(kurs);
        strma.setKwotaPLN(Z.z(nettopln+vatpln));
        strwn.setKwotaPLN(Z.z(nettopln));
        if (nd.getRodzajedok().getSkrotNazwyDok().equals("WNT")) {
            strwn.setKonto(kontonetto);
            strma.setKwotaPLN(Z.z(nettopln));
            strma.setKwota(Z.z(interpaperXLS.getNettowaluta()));
        } else {
            strwn.setKonto(kontonetto);
        }
        strma.setKonto(kontorozrachunkowe);
        w.setStronaMa(strma);
        w.setStronaWn(strwn);
        return w;
    }
    
    private Wiersz przygotujwierszVatK(InterpaperXLS interpaperXLS, Dokfk nd) {
        Wiersz w = new Wiersz(2, nd, 1);
        if (nd.getRodzajedok().getSkrotNazwyDok().equals("WNT")) {
            w = new Wiersz(2, nd, 0);
        }
        uzupelnijwiersz(w, nd, 1);
        String opiswiersza = nd.getOpisdokfk()+" - VAT"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", interpaperXLS.getVatwaluta(), null);
        double kurs = nd.getTabelanbp().getKurssredniPrzelicznik();
        double vatpln = interpaperXLS.getVatPLN()!=0.0 ? interpaperXLS.getVatPLN():interpaperXLS.getVatPLN(kurs);
        strwn.setKwotaPLN(Z.z(vatpln));
        if (nd.getRodzajedok().getSkrotNazwyDok().equals("RACH")) {
            strwn.setKonto(kontovatzagranica);
        } else if (nd.getMiesiac().equals(nd.getVatM())) {
            strwn.setKonto(kontovatnaliczony);
        } else {
            strwn.setKonto(kontovatnaliczonyprzesuniecie);
        }
        if (nd.getRodzajedok().getSkrotNazwyDok().equals("WNT")) {
            strwn.setKonto(kontovatnaliczony);
            StronaWiersza strma = new StronaWiersza(w, "Ma", interpaperXLS.getVatwaluta(), null);
            strma.setKwotaPLN(Z.z(vatpln));
            strma.setKonto(kontovatnalezny);
            w.setStronaMa(strma);
        }
        w.setStronaWn(strwn);
        return w;
    }
    
    private void uzupelnijwiersz(Wiersz w, Dokfk nd, int lpmacierzystego) {
        w.setTabelanbp(nd.getTabelanbp());
        w.setDokfk(nd);
        w.setLpmacierzystego(lpmacierzystego);
        w.setTabelanbp(w.getTabelanbp());
        w.setDataksiegowania(nd.getDatawplywu());
    }
    @Inject
    private EvewidencjaDAO eVDAO;
    private void podepnijEwidencjeVat(Dokfk nd, InterpaperXLS interpaperXLS) {
        if (nd.getNumerwlasnydokfk().equals("0029/08/2022")) {
            System.out.println("");
        }
        if (nd.getRodzajedok().getKategoriadokumentu() != 0 && nd.getRodzajedok().getKategoriadokumentu() != 5) {
            if (nd.iswTrakcieEdycji() == false) {
                double kurs = nd.getTabelanbp().getKurssredniPrzelicznik();
                double nettopln = interpaperXLS.getNettoPLNvat()!=0.0 ? interpaperXLS.getNettoPLNvat():interpaperXLS.getNettoPLN(kurs);
                double vatpln = interpaperXLS.getVatPLN()!=0.0 ? interpaperXLS.getVatPLN():interpaperXLS.getVatPLN(kurs);
                nd.setEwidencjaVAT(new ArrayList<EVatwpisFK>());
                    boolean vatowiec = wpisView.isVatowiec();
                    if (vatowiec) {
                        /*wyswietlamy ewidencje VAT*/
                        List<Evewidencja> opisewidencji = Collections.synchronizedList(new ArrayList<>());
                        opisewidencji.addAll(listaEwidencjiVat.pobierzEvewidencje(nd.getRodzajedok().getRodzajtransakcji()));
                        Evewidencja ewidencjazakupu = eVDAO.znajdzponazwie("zakup");
                        int k = 0;
                        for (Evewidencja p : opisewidencji) {
                            EVatwpisFK eVatwpisFK = new EVatwpisFK(k++,p,nd);
                            przesuniecie(nd,eVatwpisFK);
                            eVatwpisFK.setLp(k++);
                            eVatwpisFK.setEwidencja(p);
                            if ((interpaperXLS.getVatPLN()==0.0 && p.getNazwa().equals("sprzedaż 0%"))||p.getNazwa().equals("zakup")) {
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(Z.z(interpaperXLS.getVatwaluta()));
                                    eVatwpisFK.setNetto(Z.z(nettopln));
                                    eVatwpisFK.setVat(Z.z(vatpln));
                                    eVatwpisFK.setBrutto(Z.z(nettopln+vatpln));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                            } else if ((interpaperXLS.getVatPLN()!=0.0 && p.getNazwa().equals("sprzedaż 23%"))||p.getNazwa().equals("zakup")) {
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(Z.z(interpaperXLS.getVatwaluta()));
                                    eVatwpisFK.setNetto(Z.z(nettopln));
                                    eVatwpisFK.setVat(Z.z(vatpln));
                                    eVatwpisFK.setBrutto(Z.z(nettopln+vatpln));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                            } else if (PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod()) && p.getNazwa().equals("rejestr WDT")) {
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(0.0);
                                    eVatwpisFK.setNetto(Z.z(nettopln));
                                    eVatwpisFK.setVat(0.0);
                                    eVatwpisFK.setBrutto(Z.z(nettopln));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                } else if (PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod()) && p.getNazwa().equals("rejestr WNT")) {
                                      if(nd.isDwarejestry() && czyrozjechalysiemce(nd)) {
                                        EVatwpisFK pierwszaewid = new EVatwpisFK(k++, p, nd);
                                        pierwszaewid.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                        pierwszaewid.setVatwwalucie(Z.z(interpaperXLS.getVatwaluta()));
                                        pierwszaewid.setNetto(Z.z(nettopln));
                                        pierwszaewid.setVat(Z.z(vatpln));
                                        pierwszaewid.setBrutto(Z.z(nettopln));
                                        pierwszaewid.setDokfk(nd);
                                        pierwszaewid.setEstawka("op");
                                        pierwszaewid.setNieduplikuj(true);
                                        nd.getEwidencjaVAT().add(pierwszaewid);
                                        String[] rokmiesiacduplikatu = rokmiesiacduplikatu(nd);
                                        EVatwpisFK drugaewid = (EVatwpisFK) beansVAT.EwidencjaVATSporzadzanie.duplikujEVatwpisSuper(pierwszaewid,ewidencjazakupu);
                                        drugaewid.setLp(k++);
                                        drugaewid.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                        drugaewid.setVatwwalucie(Z.z(interpaperXLS.getVatwaluta()));
                                        drugaewid.setNetto(Z.z(nettopln));
                                        drugaewid.setVat(Z.z(vatpln));
                                        drugaewid.setBrutto(Z.z(nettopln));
                                        drugaewid.setDokfk(nd);
                                        drugaewid.setEstawka("op");
                                        drugaewid.setNieduplikuj(true);
                                        drugaewid.setRokEw(rokmiesiacduplikatu[0]);
                                        drugaewid.setMcEw(rokmiesiacduplikatu[1]);
                                        nd.getEwidencjaVAT().add(drugaewid);
                                    } else {
                                        eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                        eVatwpisFK.setVatwwalucie(Z.z(interpaperXLS.getVatwaluta()));
                                        eVatwpisFK.setNetto(Z.z(nettopln));
                                        eVatwpisFK.setVat(Z.z(vatpln));
                                        eVatwpisFK.setBrutto(Z.z(nettopln));
                                        eVatwpisFK.setDokfk(nd);
                                        eVatwpisFK.setEstawka("op");
                                        nd.getEwidencjaVAT().add(eVatwpisFK); 
                                      }
                                    
                                    break;
                                } else if (p.getNazwa().equals("eksport towarów")) {
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(0.0);
                                    eVatwpisFK.setNetto(Z.z(nettopln));
                                    eVatwpisFK.setVat(0.0);
                                    eVatwpisFK.setBrutto(Z.z(nettopln));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                } else if (PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod()) && p.getNazwa().equals("import usług art. 28b")) {
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(Z.z(interpaperXLS.getVatwaluta()));
                                    eVatwpisFK.setNetto(Z.z(nettopln));
                                    eVatwpisFK.setVat(Z.z(vatpln));
                                    eVatwpisFK.setBrutto(Z.z(nettopln+vatpln));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                } else if (!PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod()) && p.getNazwa().equals("import usług")) {
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(Z.z(interpaperXLS.getVatwaluta()));
                                    eVatwpisFK.setNetto(Z.z(nettopln));
                                    eVatwpisFK.setVat(Z.z(vatpln));
                                    eVatwpisFK.setBrutto(Z.z(nettopln+vatpln));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                } else if (!PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod()) && p.getNazwa().equals("import towarów")) {
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(Z.z(interpaperXLS.getVatwaluta()));
                                    eVatwpisFK.setNetto(Z.z(nettopln));
                                    eVatwpisFK.setVat(Z.z(vatpln));
                                    eVatwpisFK.setBrutto(Z.z(nettopln+vatpln));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                } else if (PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod()) && p.getNazwa().equals("usługi świad. poza ter.kraju art. 100 ust.1 pkt 4")) {
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(0.0);
                                    eVatwpisFK.setNetto(Z.z(nettopln));
                                    eVatwpisFK.setVat(0.0);
                                    eVatwpisFK.setBrutto(Z.z(nettopln));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                } else if (!PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod()) && p.getNazwa().equals("usługi świad. poza ter.kraju")) {
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(0.0);
                                    eVatwpisFK.setNetto(Z.z(nettopln));
                                    eVatwpisFK.setVat(0.0);
                                    eVatwpisFK.setBrutto(Z.z(nettopln));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                } else if (p.getNazwa().equals("sprzedaż 0%")||p.getNazwa().equals("zakup")) {
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(0.0);
                                    eVatwpisFK.setNetto(Z.z(nettopln));
                                    eVatwpisFK.setVat(0.0);
                                    eVatwpisFK.setBrutto(Z.z(nettopln));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                }
                            }
                } else {
                    Msg.msg("e", "Brak podstawowych ustawień dla podatnika dotyczących opodatkowania. Nie można wpisywać dokumentów! podepnijEwidencjeVat()");
                }
            }
        }
    }

    private boolean czyrozjechalysiemce(Dokfk selected) {
        boolean zwrot = false;
        if (selected.getDataoperacji() != null && selected.getDatawplywu() != null) {
            String dataoperacji = selected.getDataoperacji();
            String datawplywu = selected.getDatawplywu();
            if (Data.getMc(dataoperacji).equals(Data.getMc(datawplywu))) {
                return false;
            }
            if (wpisView.getRokWpisu()<2022) {
                int ilemiesiecy = Mce.odlegloscMcy(Data.getMc(dataoperacji), Data.getRok(dataoperacji), Data.getMc(datawplywu), Data.getRok(datawplywu));
                if (ilemiesiecy > 3) {
                    zwrot = true;
                }
            }
        }
        return zwrot;
    }
    
    private String[] rokmiesiacduplikatu(Dokfk selected) {
        String[] zwrot = new String[2];
        String dzienwplywu = Data.getDzien(selected.getDatawplywu());
        String miesiacwplywu = Data.getMc(selected.getDatawplywu());
        String rokwplywu = Data.getRok(selected.getDatawplywu());
        if (Integer.valueOf(dzienwplywu) < 25) {
                if (miesiacwplywu.equals("01")) {
                    zwrot[0] = Roki.rokPoprzedni(rokwplywu);
                } else {
                    zwrot[0] = rokwplywu;
                }
                zwrot[1] = Mce.zmniejszmiesiac(rokwplywu, miesiacwplywu)[1];
        } else {
                zwrot[0] = rokwplywu;
                zwrot[1] = miesiacwplywu;
        }
        return zwrot;
    }
    
     private Konto pobierzkontoWn(Dokfk nd, InterpaperXLS interpaperXLS, Klienci klient) {
        Konto kontoRozrachunkowe = null;
        Kliencifk klientMaKonto = kliencifkDAO.znajdzkontofk(klient.getNip(), wpisView.getPodatnikObiekt());
        if (klientMaKonto == null) {
            klientMaKonto = new Kliencifk();
            klientMaKonto.setNazwa(klient.getNpelna());
            klientMaKonto.setNip(klient.getNip());
            klientMaKonto.setPodatnik(wpisView.getPodatnikObiekt());
            klientMaKonto.setNrkonta(pobierznastepnynumer());
            kliencifkDAO.create(klientMaKonto);
            List<Konto> wykazkont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            PlanKontFKBean.aktualizujslownikKontrahenci(wykazkont, kliencifkDAO, klientMaKonto, kontoDAO, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
            String numerkonta = "201-2-"+klientMaKonto.getNrkonta();
            if (klient.getKrajkod()!=null && !klient.getKrajkod().equals("PL")) {
                numerkonta = "203-2-"+klientMaKonto.getNrkonta();
            }
            kontoRozrachunkowe = kontoDAO.findKonto(numerkonta, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        } else {
            String numerkonta = "201-2-"+klientMaKonto.getNrkonta();
            if (klient.getKrajkod()!=null && !klient.getKrajkod().equals("PL")) {
                numerkonta = "203-2-"+klientMaKonto.getNrkonta();
            }
            kontoRozrachunkowe = kontoDAO.findKonto(numerkonta, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        }
        return kontoRozrachunkowe;
    }
     
     private Konto pobierzkontoMa(Dokfk nd, InterpaperXLS interpaperXLS, Klienci klient) {
        Konto kontoRozrachunkowe = null;
        Kliencifk klientMaKonto = kliencifkDAO.znajdzkontofk(klient.getNip(), wpisView.getPodatnikObiekt());
        if (klientMaKonto == null) {
            klientMaKonto = new Kliencifk();
            klientMaKonto.setNazwa(klient.getNpelna());
            klientMaKonto.setNip(klient.getNip());
            klientMaKonto.setPodatnik(wpisView.getPodatnikObiekt());
            klientMaKonto.setNrkonta(pobierznastepnynumer());
            kliencifkDAO.create(klientMaKonto);
            List<Konto> wykazkont = kontoDAO.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            PlanKontFKBean.aktualizujslownikKontrahenci(wykazkont, kliencifkDAO, klientMaKonto, kontoDAO, wpisView, kontopozycjaZapisDAO, ukladBRDAO);
            String numerkonta = "202-2-"+klientMaKonto.getNrkonta();
            if (klient.getKrajkod()!=null && !klient.getKrajkod().equals("PL")) {
                numerkonta = "204-2-"+klientMaKonto.getNrkonta();
            }
            kontoRozrachunkowe = kontoDAO.findKonto(numerkonta, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        } else {
            String numerkonta = "202-2-"+klientMaKonto.getNrkonta();
            if (klient.getKrajkod()!=null && !klient.getKrajkod().equals("PL")) {
                numerkonta = "204-2-"+klientMaKonto.getNrkonta();
            }
            kontoRozrachunkowe = kontoDAO.findKonto(numerkonta, wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        }
        return kontoRozrachunkowe;
    }
    
     private String pobierznastepnynumer() {
        try {
            List<Kliencifk> przyporzadkowani = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt());
            Collections.sort(przyporzadkowani, new Kliencifkcomparator());
            return String.valueOf(Integer.parseInt(przyporzadkowani.get(przyporzadkowani.size() - 1).getNrkonta()) + 1);
        } catch (Exception e) {
            E.e(e);
            return "1";
        }
    }
     
    public void przeniesklienta() {
        if (selected!=null && selected.size()==1) {
            selectedimport1text = selected.get(0).getKontrahent();
            selectedimport1 = selected.get(0).getKlient();
            PrimeFaces.current().ajax().update("form_dialog_nowyklientimport_wybor");
            Msg.msg("Pobrano klienta");
        } else {
            Msg.msg("e","Proszę wybrać tylko jeden wiersz");
        }
    }
    
    public void usunwybrane() {
        if (selected!=null && selected.size()>0) {
            for (InterpaperXLS s : selected) {
                pobranefaktury.remove(s);
            }
            selected = null;
            Msg.msg("Usunięto wybrane pozycje");
        } else {
            Msg.msg("e","Nie wybrano pozycji do usunięcia");
        }
    }
    
    public void usunduplikaty() {
        if (pobranefaktury!=null && pobranefaktury.size()>0) {
            for (Iterator<InterpaperXLS> it = pobranefaktury.iterator(); it.hasNext();) {
                InterpaperXLS s = it.next();
                if (s.isJuzzaksiegowany()) {
                    it.remove();
                }
            }
            selected = null;
            Msg.msg("Usunięto duplikaty");
        } else {
            Msg.msg("e","Lista pusta");
        }
    }
    
    public void dodajKlienta() {
        try {
            String formatka = selectedimport.getNskrocona().toUpperCase();
            selectedimport.setNskrocona(formatka);
            formatka = selectedimport.getUlica().substring(0, 1).toUpperCase();
            formatka = formatka.concat(selectedimport.getUlica().substring(1));
            selectedimport.setUlica(formatka);
            try {
                selectedimport.getKrajnazwa();
            } catch (Exception e) {
                E.e(e);
                selectedimport.setKrajnazwa("Polska");
            }
            String kraj = selectedimport.getKrajnazwa();
            String symbol = panstwaMapa.getWykazPanstwSX().get(kraj);
            selectedimport.setKrajkod(symbol);
            if (selectedimport.getLokal() == null || selectedimport.getLokal().equals("")) {
                selectedimport.setLokal("-");
            }
            if (!selectedimport.getNpelna().equals("nie znaleziono firmy w bazie Regon")) {
                klienciDAO.create(selectedimport);
            }
            for (InterpaperXLS p : pobranefaktury) {
                if (p.getKontrahent().equals(selectedimport1text)) {
                    p.setKlient(selectedimport);
                }
            }
            Msg.msg("i", "Dodano nowego klienta" + selectedimport.getNpelna());
            selectedimport = new Klienci();
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie dodano nowego klienta. Klient o takim Nip/Nazwie pełnej juz istnieje");
        }

    }
    
    private List<String> pobierzrodzajeimportu(int i) {
        List<String> zwrot = new ArrayList<>();
        switch (i) {
            case 1:
                zwrot.add("zakup");
                zwrot.add("IU");
                zwrot.add("sprzedaż");
                break;
            case 2:
                zwrot.add("zakup");
                zwrot.add("WNT");
                zwrot.add("sprzedaż NIP");
                zwrot.add("sprzedaż os.fiz");
                break;
            case 3: 
                zwrot.add("zakup");
                zwrot.add("sprzedaż");
                break;
            case 4: 
                zwrot.add("zakup");
                zwrot.add("WNT");
                zwrot.add("sprzedaż");
                break;
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                zwrot.add("sprzedaż");
                break;
            case 12:
                zwrot.add("zakup");
                break;
            case 13:
            case 14:
            case 15:
            case 16:
                zwrot.add("sprzedaż");
                break;
        }
        return zwrot;
    }
    
    
    
    public void nanieszmianytabela() {
        if (selectedimport1!=null) {
            for (InterpaperXLS p : pobranefaktury) {
                if (p.getKontrahent().equals(selectedimport1text)) {
                    p.setKlient(selectedimport1);
                }
            }
        }
    }
    
    private void przesuniecie(Dokfk nd, EVatwpisFK eVatwpisFK) {
        if (!nd.getMiesiac().equals(nd.getVatM())) {
            int mcdok = Integer.parseInt(nd.getMiesiac());
            int mdotrzymania =  Integer.parseInt(nd.getVatM());
            int innyokres = mdotrzymania-mcdok;
            eVatwpisFK.setInnyokres(innyokres);
        }
    }
     
    public void grid2pokaz() {
        if (rodzajdok!=null) {
            grid2.setRendered(true);
            pobranefaktury = null;
        } else {
            grid2.setRendered(false);
            pobranefaktury = null;
        }
    }
    
    public void drukuj() {
        PdfXLSImport.drukuj(pobranefaktury, wpisView, 0);
    }
    
    public void usunduplikatyInter() {
        if (pobranefaktury!=null) {
            for (Iterator<InterpaperXLS> it = pobranefaktury.iterator();it.hasNext();) {
                InterpaperXLS p = it.next();
                if (p.isJuzzaksiegowany()) {
                    it.remove();
                }
            }
            Msg.msg("Usunięto duplikaty");
        }
    }
     
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<InterpaperXLS> getPobranefaktury() {
        return pobranefaktury;
    }

    public void setPobranefaktury(List<InterpaperXLS> pobranefaktury) {
        this.pobranefaktury = pobranefaktury;
    }

    public List<InterpaperXLS> getSelected() {
        return selected;
    }

    public void setSelected(List<InterpaperXLS> selected) {
        this.selected = selected;
    }

  

    public List<InterpaperXLS> getPobranefakturyfilter() {
        return pobranefakturyfilter;
    }

    public void setPobranefakturyfilter(List<InterpaperXLS> pobranefakturyfilter) {
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

      public CommandButton getGenerujbutton() {
        return generujbutton;
    }

    public void setGenerujbutton(CommandButton generujbutton) {
        this.generujbutton = generujbutton;
    }

    public Klienci getSelectedimport1() {
        return selectedimport1;
    }

    public void setSelectedimport1(Klienci selectedimport1) {
        this.selectedimport1 = selectedimport1;
    }

    public Klienci getSelectedimport() {
        return selectedimport;
    }

    public void setSelectedimport(Klienci selectedimport) {
        this.selectedimport = selectedimport;
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

    public PanelGrid getGrid0() {
        return grid0;
    }

    public void setGrid0(PanelGrid grid0) {
        this.grid0 = grid0;
    }

    public CommandButton getDrkujfizbutton() {
        return drkujfizbutton;
    }

    public void setDrkujfizbutton(CommandButton drkujfizbutton) {
        this.drkujfizbutton = drkujfizbutton;
    }

    public List<String> getRodzajedokimportu() {
        return rodzajedokimportu;
    }

    public void setRodzajedokimportu(List<String> rodzajedokimportu) {
        this.rodzajedokimportu = rodzajedokimportu;
    }

    public Konto getKontonetto() {
        return kontonetto;
    }

    public void setKontonetto(Konto kontonetto) {
        this.kontonetto = kontonetto;
    }

    public Konto getKontodlanetto() {
        return kontodlanetto;
    }

    public void setKontodlanetto(Konto kontodlanetto) {
        this.kontodlanetto = kontodlanetto;
    }

    public List<Konto> getListakontoRZiS() {
        return listakontoRZiS;
    }

    public void setListakontoRZiS(List<Konto> listakontoRZiS) {
        this.listakontoRZiS = listakontoRZiS;
    }

    public SelectOneMenu getKontobutton() {
        return kontobutton;
    }

    public void setKontobutton(SelectOneMenu kontobutton) {
        this.kontobutton = kontobutton;
    }

    public String getJakipobor() {
        return jakipobor;
    }

    public void setJakipobor(String jakipobor) {
        this.jakipobor = jakipobor;
    }

    public List<InterpaperXLS> getPrzerwanyimport() {
        return przerwanyimport;
    }

    public void setPrzerwanyimport(List<InterpaperXLS> przerwanyimport) {
        this.przerwanyimport = przerwanyimport;
    }

    public List<InterpaperXLS> getImportyzbrakami() {
        return importyzbrakami;
    }

    public void setImportyzbrakami(List<InterpaperXLS> importyzbrakami) {
        this.importyzbrakami = importyzbrakami;
    }

    public List<InterpaperXLS> getInnyokres() {
        return innyokres;
    }

    public void setInnyokres(List<InterpaperXLS> innyokres) {
        this.innyokres = innyokres;
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

    public double getBruttopln() {
        return bruttopln;
    }

    public void setBruttopln(double bruttopln) {
        this.bruttopln = bruttopln;
    }

    public Konto getKontodlabrutto() {
        return kontodlabrutto;
    }

    public void setKontodlabrutto(Konto kontodlabrutto) {
        this.kontodlabrutto = kontodlabrutto;
    }

    public SelectOneMenu getKontobutton2() {
        return kontobutton2;
    }

    public void setKontobutton2(SelectOneMenu kontobutton2) {
        this.kontobutton2 = kontobutton2;
    }
    
    

    private void sumujnadole(List<InterpaperXLS> pobranefaktury) {
        nettopln = 0.0;
        vatpln = 0.0;
        bruttopln = 0.0;
        if (pobranefaktury!=null && pobranefaktury.size()>0) {
            for (InterpaperXLS p : pobranefaktury) {
                nettopln = Z.z(nettopln+p.getNettoPLN());
                vatpln = Z.z(vatpln+p.getVatPLN());
            }
            bruttopln = Z.z(nettopln+vatpln);
        }
    }

  
    

    

  
    

   
    
   
    
}
