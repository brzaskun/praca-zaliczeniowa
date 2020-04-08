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
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import daoFK.DokDAOfk;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaZapisDAO;
import daoFK.TabelanbpDAO;
import daoFK.UkladBRDAO;
import daoFK.WalutyDAOfk;
import embeddable.PanstwaEUSymb;
import embeddable.PanstwaMap;
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
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.joda.time.DateTime;
import org.primefaces.PrimeFaces;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.component.selectonelistbox.SelectOneListbox;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pdf.PdfXLSImport;
import view.WpisView;import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class InterpaperImportView implements Serializable {
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
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private ListaEwidencjiVat listaEwidencjiVat;
    private byte[] plikinterpaper;
    public  List<InterpaperXLS> pobranefaktury;
    public  List<InterpaperXLS> pobranefakturyfilter;
    public  List<InterpaperXLS> selected;
    private List<Rodzajedok> rodzajedokKlienta;
    private String wiadomoscnieprzypkonta;
    private String rodzajdok;
    private PanelGrid grid0;
    private PanelGrid grid1;
    private PanelGrid grid2;
    private PanelGrid grid3;
    private boolean sabraki;
    private CommandButton generujbutton;
    private CommandButton drkujfizbutton;
    private SelectOneMenu kontobutton;
    private Konto kontonetto;
    private Konto kontonettokoszt;
    private Konto kontonettotowary;
    private Konto kontovat;
    private Konto kontovatnaliczony;
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
    private List<Konto> listakontoRZiS;
    

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
        rodzajeimportu = zrobrodzajeimportu();
        kontonetto = kontoDAO.findKonto("702-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontovat = kontoDAO.findKonto("221-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontovatzagranica = kontoDAO.findKonto("223", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontonettokoszt = kontoDAO.findKonto("403", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontonettotowary = kontoDAO.findKonto("330-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontovatnaliczony = kontoDAO.findKonto("221-3", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontovatnaliczonyprzesuniecie = kontoDAO.findKonto("221-4", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        tabelanbppl = tabelanbpDAO.findByTabelaPLN();
        walutapln = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        listakontoRZiS = kontoDAO.findKontaRZiS(wpisView);
    }
    
     private List<ImportowanyPlik> zrobrodzajeimportu() {
        List<ImportowanyPlik> zwrot = new ArrayList<>();
        zwrot.add(new ImportowanyPlik("Interpaper csv ;","csv",1));
        zwrot.add(new ImportowanyPlik("Zorint xls","xls","",2));
        zwrot.add(new ImportowanyPlik("Tomtech xls","xls","",3));
        zwrot.add(new ImportowanyPlik("Exolight xls","xls","",4));
        return zwrot;
    }
    
    public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            if (extension.equals("csv")||extension.equals("xls")||extension.equals("xlsx")) {
                String filename = uploadedFile.getFileName();
                plikinterpaper = uploadedFile.getContents();
                PrimeFaces.current().ajax().update("panelplik");
                grid1.setRendered(true);
                grid2.setRendered(false);
                grid3.setRendered(false);
                pobranefaktury = null;
                rodzajdok = null;
                Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
                switch (wybranyrodzajimportu.getLp()) {
                    case 1:
                       rodzajedokimportu = pobierzrodzajeimportu(1);
                       break;
                    case 2:
                       rodzajedokimportu = pobierzrodzajeimportu(2);
                       break;
                    case 3:
                       rodzajedokimportu = pobierzrodzajeimportu(3);
                       break;
                    case 4:
                       rodzajedokimportu = pobierzrodzajeimportu(4);
                       break;
                    default:
                        rodzajedokimportu = new ArrayList<>();
                        break;
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
                    pobranefaktury = ReadCSVInterpaperFile.getListafakturCSV(plikinterpaper, k, klienciDAO, rodzajdok, gUSView);
                    break;
                case 2:
                    pobranefaktury = ReadXLSFirmaoFile.getListafakturXLS(plikinterpaper, k, klienciDAO, rodzajdok, gUSView);
                    break;
                case 3:
                    pobranefaktury = ReadXLSTomTechFile.getListafakturXLS(plikinterpaper, k, klienciDAO, rodzajdok, gUSView);
                    break;
                case 4:
                    pobranefaktury = ReadXLSExolightFile.getListafakturXLS(plikinterpaper, k, klienciDAO, rodzajdok, gUSView);
                    break;
            }
            grid3.setRendered(true);
            if (wybranyrodzajimportu.getLp()==2 && (rodzajdok.equals("sprzedaż NIP") || rodzajdok.contains("zakup"))) {
                generujbutton.setRendered(true);
                drkujfizbutton.setRendered(true);
                kontobutton.setRendered(true);
            } else if (wybranyrodzajimportu.getLp()==1 && (rodzajdok.equals("sprzedaż os.fiz"))) {
                generujbutton.setRendered(true);
                drkujfizbutton.setRendered(true);
                kontobutton.setRendered(true);
            } else if (wybranyrodzajimportu.getLp()==1 && (rodzajdok.equals("sprzedaż") || rodzajdok.contains("zakup"))){
                drkujfizbutton.setRendered(true);
                generujbutton.setRendered(true);
                kontobutton.setRendered(true);
            } else if (wybranyrodzajimportu.getLp()==3 && (rodzajdok.equals("sprzedaż") || rodzajdok.equals("zakup"))){
                drkujfizbutton.setRendered(true);
                generujbutton.setRendered(true);
                kontobutton.setRendered(true);
            } else if (wybranyrodzajimportu.getLp()==4 && (rodzajdok.equals("sprzedaż") || rodzajdok.contains("zakup"))){
                drkujfizbutton.setRendered(true);
                generujbutton.setRendered(true);
                kontobutton.setRendered(true);
            }
            Msg.msg("Pobrano wszystkie dane");
        } catch (OfficeXmlFileException e1) {
            E.e(e1);
            Msg.msg("e", "Niewłaściwa wersja pliku xls");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd przy pobieraniu danych");
        }
//        for (InterpaperXLS p : pobranefaktury) {
//           generowanieDokumentu(p);
//        }
    }
    
    public void grid0pokaz() {
        if (sabraki==false) {
            grid0.setRendered(true);
            Msg.msg("i","Wybranonastępujący format importu "+wybranyrodzajimportu);
            
        } else {
            Msg.msg("e", "Są braki. Nie można wszytać pliku");
        }
    }
    
    public void generuj() {
        if (pobranefaktury !=null && pobranefaktury.size()>0) {
            List<Klienci> k = klienciDAO.findAll();
            int ile = 0;
            if (selected !=null && selected.size()>0) {
                for (InterpaperXLS p : selected) {
                    if (p.getNip()!=null) {
                        ile += generowanieDokumentu(p, k);
                    }
                }
                Msg.msg("Zaksięgowano "+ile+" z wybranych dokumentów");
            } else {
                for (InterpaperXLS p : pobranefaktury) {
                   if (p.getNip()!=null) {
                        ile += generowanieDokumentu(p, k);
                   }
                }
                Msg.msg("Zaksięgowano "+ile+" dokumentów");
            }
        } else {
            Msg.msg("e", "Błąd! Lista danych źrdółowych jest pusta");
        }
    }
    
     public int generowanieDokumentu(InterpaperXLS interpaperXLS, List<Klienci> k) {
        int ile = 1;
        try {
            int polska0unia1zagranica2 = 0;
            if (interpaperXLS.getKlient().getKrajnazwa()!=null && !interpaperXLS.getKlient().getKrajkod().equals("PL")) {
                polska0unia1zagranica2 = 2;
                if (PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod())) {
                    polska0unia1zagranica2 = 1;
                }
            }
             if (interpaperXLS.getNrfaktury().equals("191009413")) {
                System.out.println("");
            }
            String rodzajdk = "ZZ";
            Dokfk dokument = null;
            if (this.rodzajdok.contains("sprzedaż")) {
                if (wybranyrodzajimportu.getLp()==1) {
                    rodzajdk = polska0unia1zagranica2==0 ? "SZ" : polska0unia1zagranica2==1 ? "UPTK100" : "UPTK";
                } else {
                    rodzajdk = polska0unia1zagranica2==0 ? "SZ" : polska0unia1zagranica2==1 ? "WDT" : "EXP";
                }
                dokument = stworznowydokument(oblicznumerkolejny(rodzajdk),interpaperXLS, rodzajdk, k, "przychody ze sprzedaży");
            } else {
                if (this.rodzajdok.equals("zakup/WNT")) {
                    rodzajdk = polska0unia1zagranica2==0 ? "ZZ" : "WNT";
                    if (interpaperXLS.getVatPLN()!=0.0 && !interpaperXLS.getKlientpaństwo().equals("Polska")) {
                        rodzajdk = "RACH";
                    }
                } else {
                    rodzajdk = polska0unia1zagranica2==0 ? "ZZ" : "IU";
                    if (interpaperXLS.getVatPLN()!=0.0 && !interpaperXLS.getKlientpaństwo().equals("Polska")) {
                        rodzajdk = "RACH";
                    }
                }
                dokument = stworznowydokument(oblicznumerkolejny(rodzajdk),interpaperXLS, rodzajdk, k, "zakup towarów/koszty");
            }
            
            try {
                if (dokument!=null) {
                    dokument.setImportowany(true);
                    dokDAOfk.dodaj(dokument);
                }
            } catch (Exception e) {
                ile = 0;
                Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu "+rodzajdok);
            }
        } catch (Exception e) {
            ile = 0;
            E.e(e);
        }
        return ile;
    }
     
      private Dokfk stworznowydokument(int numerkolejny, InterpaperXLS interpaperXLS, String rodzajdok, List<Klienci> k, String opis) {
        Dokfk nd = new Dokfk(numerkolejny, wpisView.getRokWpisuSt());
        ustawdaty(nd, interpaperXLS);
        nd.setKontr(interpaperXLS.getKlient());
        ustawnumerwlasny(nd, interpaperXLS);
        nd.setOpisdokfk(opis);
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        ustawrodzajedok(nd, rodzajdok);
        ustawtabelenbp(nd, interpaperXLS);
        przewalutuj(nd, interpaperXLS);
        podepnijEwidencjeVat(nd, interpaperXLS);
        Dokfk juzjest = dokDAOfk.findDokfkObjKontrahent(nd);
        if (juzjest!=null || nd.getKontr()==null) {
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
      
    private void przewalutuj(Dokfk nd, InterpaperXLS interpaperXLS) {
        Tabelanbp t = nd.getTabelanbp();
        if (t!=null && !t.getWaluta().getSymbolwaluty().equals("PLN")) {
            interpaperXLS.setNettoPLN(Z.z(interpaperXLS.getNettowaluta()*t.getKurssredniPrzelicznik()));
            interpaperXLS.setVatPLN(Z.z(interpaperXLS.getVatwaluta()*t.getKurssredniPrzelicznik()));
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
            nd.setDatadokumentu(datawystawienia);
            nd.setDatawplywu(dataotrzymania);
        } else {
            nd.setDatadokumentu(datawystawienia);
            nd.setDatawplywu(datawystawienia);
        }
        nd.setDataoperacji(datasprzedazy);
        nd.setDatawystawienia(datawystawienia);
        nd.setDataujecia(new Date());
        nd.setMiesiac(wpisView.getMiesiacWpisu());
        if (rodzajdok.contains("sprzedaż")) {
            nd.setVatM(datasprzedazy.split("-")[1]);
            nd.setVatR(datasprzedazy.split("-")[0]);
        } else {
            String dataotrzymania = formatterX.format(interpaperXLS.getDataotrzymania()!=null?interpaperXLS.getDataotrzymania():interpaperXLS.getDatawystawienia());
            nd.setVatM(dataotrzymania.split("-")[1]);
            nd.setVatR(dataotrzymania.split("-")[0]);
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
            zwrot = SzukajDaneBean.znajdzdaneregonAutomat(nip, gUSView);
            klienciDAO.dodaj(zwrot);
            Msg.msg("Zaktualizowano dane klienta pobranymi z GUS");
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
    
    private void ustawwiersze(Dokfk nd, InterpaperXLS interpaperXLS) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        if (rodzajdok.contains("sprzedaż")) {
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
        strwn.setKonto(pobierzkontoWn(nd, interpaperXLS, nd.getKontr()));
        w.setStronaWn(strwn);
        w.setStronaMa(strma);
        return w;
    }
    
    private Wiersz przygotujwierszVat(InterpaperXLS interpaperXLS, Dokfk nd) {
        Wiersz w = new Wiersz(2, 2);
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
        Wiersz w = new Wiersz(1, 0);
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
        } else {
            strwn.setKonto(kontodlanetto!=null?kontodlanetto:kontonettokoszt);
        }
        strma.setKonto(pobierzkontoMa(nd, interpaperXLS, nd.getKontr()));
        w.setStronaMa(strma);
        w.setStronaWn(strwn);
        return w;
    }
    
    private Wiersz przygotujwierszVatK(InterpaperXLS interpaperXLS, Dokfk nd) {
        Wiersz w = new Wiersz(2, 1);
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
    
    private void podepnijEwidencjeVat(Dokfk nd, InterpaperXLS interpaperXLS) {
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
                        int k = 0;
                        for (Evewidencja p : opisewidencji) {
                            EVatwpisFK eVatwpisFK = new EVatwpisFK();
                            przesuniecie(nd,eVatwpisFK);
                            eVatwpisFK.setLp(k++);
                            eVatwpisFK.setEwidencja(p);
                            if (Z.z(interpaperXLS.getVatwaluta())!=0.0) {
                                if (p.getNazwa().equals("sprzedaż 23%")||p.getNazwa().equals("zakup")) {
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(Z.z(interpaperXLS.getVatwaluta()));
                                    eVatwpisFK.setNetto(Z.z(nettopln));
                                    eVatwpisFK.setVat(Z.z(vatpln));
                                    eVatwpisFK.setBrutto(Z.z(nettopln+vatpln));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                }
                            } else {
                                if (PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod()) && p.getNazwa().equals("rejestr WDT")) {
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
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(0.0);
                                    eVatwpisFK.setNetto(Z.z(nettopln));
                                    eVatwpisFK.setVat(0.0);
                                    eVatwpisFK.setBrutto(Z.z(nettopln));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                } else if (!PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod()) && p.getNazwa().equals("eksport towarów")) {
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
                        }
                } else {
                    Msg.msg("e", "Brak podstawowych ustawień dla podatnika dotyczących opodatkowania. Nie można wpisywać dokumentów! podepnijEwidencjeVat()");
                }
            }
        }
    }

     private Konto pobierzkontoWn(Dokfk nd, InterpaperXLS interpaperXLS, Klienci klient) {
        Konto kontoRozrachunkowe = null;
        Kliencifk klientMaKonto = kliencifkDAO.znajdzkontofk(klient.getNip(), wpisView.getPodatnikObiekt().getNip());
        if (klientMaKonto == null) {
            klientMaKonto = new Kliencifk();
            klientMaKonto.setNazwa(klient.getNpelna());
            klientMaKonto.setNip(klient.getNip());
            klientMaKonto.setPodatniknazwa(wpisView.getPodatnikWpisu());
            klientMaKonto.setPodatniknip(wpisView.getPodatnikObiekt().getNip());
            klientMaKonto.setNrkonta(pobierznastepnynumer());
            kliencifkDAO.dodaj(klientMaKonto);
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
        Kliencifk klientMaKonto = kliencifkDAO.znajdzkontofk(klient.getNip(), wpisView.getPodatnikObiekt().getNip());
        if (klientMaKonto == null) {
            klientMaKonto = new Kliencifk();
            klientMaKonto.setNazwa(klient.getNpelna());
            klientMaKonto.setNip(klient.getNip());
            klientMaKonto.setPodatniknazwa(wpisView.getPodatnikWpisu());
            klientMaKonto.setPodatniknip(wpisView.getPodatnikObiekt().getNip());
            klientMaKonto.setNrkonta(pobierznastepnynumer());
            kliencifkDAO.dodaj(klientMaKonto);
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
            List<Kliencifk> przyporzadkowani = kliencifkDAO.znajdzkontofkKlient(wpisView.getPodatnikObiekt().getNip());
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
            klienciDAO.dodaj(selectedimport);
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
                zwrot.add("zakup/IU");
                zwrot.add("sprzedaż");
                break;
            case 2:
                zwrot.add("zakup/WNT");
                zwrot.add("sprzedaż NIP");
                zwrot.add("sprzedaż os.fiz");
                break;
            case 3: 
                zwrot.add("zakup");
                zwrot.add("sprzedaż");
                break;
            case 4: 
                zwrot.add("zakup/WNT");
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
            grid3.setRendered(false);
        } else {
            grid2.setRendered(false);
            pobranefaktury = null;
            grid3.setRendered(false);
        }
    }
    
    public void drukuj() {
        PdfXLSImport.drukuj(pobranefaktury, wpisView, 0);
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

    

    

  
    

   
    
   
    
}
