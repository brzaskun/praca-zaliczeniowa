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
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
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
import org.joda.time.DateTime;
import org.primefaces.PrimeFaces;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;

import view.WpisView;import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class InterpaperImportMT940View implements Serializable {
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
    

    
    
    @PostConstruct
    private void init() { //E.m(this);
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
            if (extension.equals("csv")) {
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
    
    
    public void importujdok() {
        try {
            List<Klienci> k = klienciDAO.findAll();
            pobranefaktury = ReadCSVInterpaperFile.getListafakturCSV(plikinterpaper, k, klienciDAO, rodzajdok, gUSView, wpisView.getMiesiacWpisu());
            for (InterpaperXLS p : pobranefaktury) {
                if (p.getKlient()==null) {
                    p.setKlient(SzukajDaneBean.znajdzdaneregonAutomat(p.getNip(), gUSView));
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
    
    public void generuj() {
        if (pobranefaktury !=null && pobranefaktury.size()>0) {
            List<Klienci> k = klienciDAO.findAll();
            int ile = 0;
            if (selected !=null && selected.size()>0) {
                for (InterpaperXLS p : selected) {
                   ile += generowanieDokumentu(p, k);
                }
                Msg.msg("Zaksięgowano "+ile+" z wybranych dokumentów");
            } else {
                for (InterpaperXLS p : pobranefaktury) {
                   ile += generowanieDokumentu(p, k);
                }
                Msg.msg("Zaksięgowano "+ile+" dokumentów");
            }
        } else {
            Msg.msg("e", "Błąd! Lista danych źrdółowych jest pusta");
        }
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
             if (interpaperXLS.getNrfaktury().equals("191009413")) {
                System.out.println("");
            }
            String rodzajdk = "ZZ";
            if (rodzajdok.equals("sprzedaż")) {
                rodzajdk = polska0unia1zagranica2==0 ? "SZ" : polska0unia1zagranica2==1 ? "UPTK100" : "UPTK";
            } else {
                rodzajdk = polska0unia1zagranica2==0 ? "ZZ" : "IU";
            }
            Dokfk dokument = stworznowydokument(oblicznumerkolejny(rodzajdk),interpaperXLS, rodzajdk, k);
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
     
      private Dokfk stworznowydokument(int numerkolejny, InterpaperXLS interpaperXLS, String rodzajdok, List<Klienci> k) {
        Dokfk nd = new Dokfk(numerkolejny, wpisView.getRokWpisuSt());
        ustawdaty(nd, interpaperXLS);
        ustawkontrahenta(nd,interpaperXLS, k);
        ustawnumerwlasny(nd, interpaperXLS);
        nd.setOpisdokfk("usługa transportowa");
        nd.setPodatnikObj(wpisView.getPodatnikObiekt());
        ustawrodzajedok(nd, rodzajdok);
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
      
    private int oblicznumerkolejny(String rodzajdok) {
        Dokfk poprzednidokumentvat = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), rodzajdok, wpisView.getRokWpisuSt());
        return poprzednidokumentvat == null ? 1 : poprzednidokumentvat.getNrkolejnywserii() + 1;
    }
    
    private void ustawdaty(Dokfk nd, InterpaperXLS interpaperXLS) {
        Format formatterX = new SimpleDateFormat("yyyy-MM-dd");
        String datadokumentu = formatterX.format(interpaperXLS.getDatawystawienia());
        String datasprzedazy = formatterX.format(interpaperXLS.getDatasprzedaży());
        if (interpaperXLS.getDataobvat()!=null) {
            datasprzedazy = formatterX.format(interpaperXLS.getDataobvat());
        }
        nd.setDatadokumentu(datadokumentu);
        nd.setDataoperacji(datasprzedazy);
        nd.setDatawplywu(datadokumentu);
        nd.setDatawystawienia(datadokumentu);
        nd.setDataujecia(new Date());
        nd.setMiesiac(wpisView.getMiesiacWpisu());
        if (rodzajdok.equals("sprzedaż")) {
            nd.setVatM(datasprzedazy.split("-")[1]);
            nd.setVatR(datasprzedazy.split("-")[0]);
        } else {
            String dataotrzymania = formatterX.format(interpaperXLS.getDataotrzymania());
            nd.setVatM(dataotrzymania.split("-")[1]);
            nd.setVatR(dataotrzymania.split("-")[0]);
        }
    }
    
    private void ustawkontrahenta(Dokfk nd, InterpaperXLS interpaperXLS, List<Klienci> k) {
        try {
            Klienci klient = null;
            for (Klienci p : k) {
                if (p.getNip().contains(interpaperXLS.getNip().trim())) {
                    klient = p;
                    break;
                }
            }
            if (klient==null) {
                for (Klienci p : k) {
                    if (p.getNpelna().contains(interpaperXLS.getKontrahent().trim()) || p.getNskrocona().contains(interpaperXLS.getKontrahent().trim())) {
                        klient = p;
                        break;
                    }
                }
            }
            if (klient==null) {
                klient = znajdzdaneregonAutomat(interpaperXLS.getNip().trim());
            }
            nd.setKontr(klient);
            k.add(klient);
        } catch (Exception e) {
            
        }
    }
    
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
        Wiersz w = new Wiersz(1, nd, 0);
        uzupelnijwiersz(w, nd);
        String opiswiersza = "usługa transportowa"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", interpaperXLS.getBruttowaluta(), null);
        StronaWiersza strma = new StronaWiersza(w, "Ma", interpaperXLS.getNettowaluta(), null);
        strwn.setKwotaPLN(interpaperXLS.getBruttoPLN());
        strma.setKwotaPLN(interpaperXLS.getNettoPLNvat());
        strma.setKonto(kontonetto);
        strwn.setKonto(pobierzkontoWn(nd, interpaperXLS, nd.getKontr()));
        w.setStronaWn(strwn);
        w.setStronaMa(strma);
        return w;
    }
    
    private Wiersz przygotujwierszVat(InterpaperXLS interpaperXLS, Dokfk nd) {
        Wiersz w = new Wiersz(2, nd, 2);
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
        Wiersz w = new Wiersz(1, nd, 0);
        uzupelnijwiersz(w, nd);
        String opiswiersza = "usługa transportowa"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strma = new StronaWiersza(w, "Ma", interpaperXLS.getBruttowaluta(), null);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", interpaperXLS.getNettowaluta(), null);
        strma.setKwotaPLN(interpaperXLS.getBruttoPLN());
        strwn.setKwotaPLN(interpaperXLS.getNettoPLNvat());
        strwn.setKonto(kontonettokoszt);
        strma.setKonto(pobierzkontoMa(nd, interpaperXLS, nd.getKontr()));
        w.setStronaMa(strma);
        w.setStronaWn(strwn);
        return w;
    }
    
    private Wiersz przygotujwierszVatK(InterpaperXLS interpaperXLS, Dokfk nd) {
        Wiersz w = new Wiersz(2, nd, 1);
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
        w.setTabelanbp(nd.getTabelanbp());
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
                            przesuniecie(nd,eVatwpisFK);
                            if (Z.z(interpaperXLS.getVatPLN())!=0.0) {
                                if (p.getNazwa().equals("sprzedaż 23%")||p.getNazwa().equals("zakup")) {
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(Z.z(interpaperXLS.getVatwaluta()));
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
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(Z.z(interpaperXLS.getVatwaluta()));
                                    eVatwpisFK.setNetto(Z.z(interpaperXLS.getNettoPLNvat()));
                                    eVatwpisFK.setVat(Z.z(interpaperXLS.getVatPLN()));
                                    eVatwpisFK.setBrutto(Z.z(interpaperXLS.getNettoPLNvat()+interpaperXLS.getVatPLN()));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                } else if (!PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod()) && p.getNazwa().equals("import usług")) {
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(Z.z(interpaperXLS.getVatwaluta()));
                                    eVatwpisFK.setNetto(Z.z(interpaperXLS.getNettoPLNvat()));
                                    eVatwpisFK.setVat(Z.z(interpaperXLS.getVatPLN()));
                                    eVatwpisFK.setBrutto(Z.z(interpaperXLS.getNettoPLNvat()+interpaperXLS.getVatPLN()));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                } else if (PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod()) && p.getNazwa().equals("usługi świad. poza ter.kraju art. 100 ust.1 pkt 4")) {
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(0.0);
                                    eVatwpisFK.setNetto(Z.z(interpaperXLS.getNettoPLNvat()));
                                    eVatwpisFK.setVat(0.0);
                                    eVatwpisFK.setBrutto(Z.z(interpaperXLS.getNettoPLNvat()));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                } else if (!PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod()) && p.getNazwa().equals("usługi świad. poza ter.kraju")) {
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(0.0);
                                    eVatwpisFK.setNetto(Z.z(interpaperXLS.getNettoPLNvat()));
                                    eVatwpisFK.setVat(0.0);
                                    eVatwpisFK.setBrutto(Z.z(interpaperXLS.getNettoPLNvat()));
                                    eVatwpisFK.setDokfk(nd);
                                    eVatwpisFK.setEstawka("op");
                                    nd.getEwidencjaVAT().add(eVatwpisFK);
                                    break;
                                } else if (p.getNazwa().equals("sprzedaż 0%")||p.getNazwa().equals("zakup")) {
                                    eVatwpisFK.setNettowwalucie(Z.z(interpaperXLS.getNettowaluta()));
                                    eVatwpisFK.setVatwwalucie(0.0);
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
private void przesuniecie(Dokfk nd, EVatwpisFK eVatwpisFK) {
        if (!nd.getMiesiac().equals(nd.getVatM())) {
            int mcdok = Integer.parseInt(nd.getMiesiac());
            int mdotrzymania =  Integer.parseInt(nd.getVatM());
            int innyokres = mdotrzymania-mcdok;
            eVatwpisFK.setInnyokres(innyokres);
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
     
    public void grid2pokaz() {
        grid2.setRendered(true);
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

  
     public static void main(String[] args) {
        try {
            FileInputStream file = new FileInputStream(new File("d:\\mat.sta"));
            //InputStream file = new ByteArrayInputStream(file);
            List<String> records = new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(file, Charset.forName("UTF-8")))) {
                String line;
                ArrayList<String> opis = new ArrayList();
                while ((line = br.readLine()) != null) {
                    //String[] values = line.split(":");
                    //records.add(Arrays.asList(values));
                    records.add(line);
                    //System.out.println(line);
                    String nrkonta = pobierzdane(opis,line);
                    if (nrkonta!=null && nrkonta.startsWith("konto")) {
                        System.out.println(opis.toString());
                        opis = new ArrayList();
                    }
                }
            }
            file.close();
//            PDDocument document = PDDocument.load(new File("d:\\pdf.pdf"));
//            if (!document.isEncrypted()) {
//                PDFTextStripper stripper = new PDFTextStripper();
//                String text = stripper.getText(document);
//                System.out.println("Text:" + text);
//            }
//            document.close();
        } catch (Exception e) {
            E.e(e);
        }
    }

   public static String pobierzdane(ArrayList<String> opis, String linia) {
       String zwrot = null;
       if (linia != null) {
           try {
               if (linia.startsWith(":25:/")) {
                    String konto = linia.substring(5);
                    zwrot = konto;
                    System.out.println(zwrot);
               }
               if (linia.startsWith(":28C:")) {
                    String nrwyciagu = linia.substring(5);
                    zwrot = nrwyciagu;
                    System.out.println(zwrot);
               }
               if (linia.startsWith(":60F:C")) {
                    String data = linia.substring(6,12);
                    zwrot = data;
                    System.out.println(zwrot);
                    String waluta = linia.substring(12,15);
                    zwrot = waluta;
                    System.out.println(zwrot);
                    String saldo = linia.substring(15);
                    zwrot = saldo;
                    System.out.println(saldo);
               }
               if (linia.startsWith(":61:")) {
                    System.out.println("-----------------------------------");
                    String data = linia.substring(4,10);
                    zwrot = data;
                    System.out.println(zwrot);
                    int indexofD = linia.indexOf("D")+1;
                    int indexofS = linia.indexOf("S");
                    int indexofC = linia.indexOf("C")+1;
                    if (indexofD>0) {
                        String kwotaD = linia.substring(indexofD, indexofS);
                        kwotaD = kwotaD.replace(",", ".");
                        zwrot = kwotaD;
                        System.out.println(zwrot);
                    } else if (indexofC>0) {
                        String kwotaC = linia.substring(indexofC, indexofS);
                        kwotaC = kwotaC.replace(",", ".");
                        zwrot = kwotaC;
                        System.out.println("-"+zwrot);
                    }
                    
                    
               }
               if (linia.startsWith(":86:020~")) {
                   String rodzaj = "przelew obcy przychodzący i wychodzący";
                   zwrot = rodzaj;
                   System.out.println(zwrot);
               } else if (linia.startsWith(":86:034~")) {
                   String rodzaj = "przelew międzybankowy :konto własne przychodzący";
                   zwrot = rodzaj;
                   System.out.println(zwrot);
               } else if (linia.startsWith(":86:076~")) {
                   String rodzaj = "przelew międzybankowy :konto własne wychodzący";
                   zwrot = rodzaj;
                   System.out.println(zwrot);
               } else if (linia.startsWith(":86:094~")) {
                   String rodzaj = "prowizja bankowa";
                   zwrot = rodzaj;
                   System.out.println(zwrot);
               } else if (linia.startsWith(":86:099~")) {
                   String rodzaj = "prowizja międzybankowy na prowizje konto własne";
                   zwrot = rodzaj;
                   System.out.println(zwrot);
               } else if (linia.startsWith(":86:020~")) {
                   String rodzaj = "przelew wychodzący";
                   zwrot = rodzaj;
                   System.out.println(zwrot);
               } 
               
               if (linia.startsWith("~20")||linia.startsWith("~21")) {
                   opis.add(linia.substring(3,linia.indexOf("~22")));
               }
               if (linia.contains("~22") && linia.length()>6) {
                   opis.add(linia.substring(linia.indexOf("~22")+3));
               }
               if (linia.startsWith("~23") && linia.length()>3) {
                   opis.add(linia.substring(3,linia.indexOf("~24")));
               }
               if (linia.contains("~24") && linia.length()>6) {
                   opis.add(linia.substring(linia.indexOf("~24")+3));
               }
               if (linia.startsWith("~25") && linia.length()>3) {
                   opis.add(linia.substring(3));
               }
               if (linia.startsWith("~26") && linia.length()>3) {
                   opis.add(linia.substring(3));
                   
               }
               if (linia.contains("~32") && linia.length()>6) {
                   zwrot = linia.substring(linia.indexOf("~32")+3);
                   System.out.println(zwrot);
               }
               if (linia.startsWith("~33") && linia.length()>6) {
                   zwrot = linia.substring(3);
                   System.out.println(zwrot);
               }
               if (linia.startsWith("~34") && linia.length()>6) {
                   zwrot = linia.substring(3);
                   System.out.println(zwrot);
               }
               if (linia.startsWith("~38")) {
                   zwrot = "konto: "+linia.substring(2,28);
                   System.out.println("konto: "+zwrot);
               }
               
               
           } catch (Exception e) {}
       }
       return zwrot;
   }
    
   
    
}
