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
import dedra.Dedraparser;
import embeddable.PanstwaEUSymb;
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
import java.io.File;
import java.io.IOException;
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
    }
    
    
    public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            if (extension.equals("csv")) {
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
            pobranefaktury = ReadXLSFile.getListafakturCSV(plikinterpaper, k, klienciDAO, rodzajdok);
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
        Konto kontonetto = kontoDAO.findKonto("702-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
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
        Konto kontovat = kontoDAO.findKonto("221-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
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
        Konto kontonetto = kontoDAO.findKonto("403", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        strma.setKwotaPLN(interpaperXLS.getBruttoPLN());
        strwn.setKwotaPLN(interpaperXLS.getNettoPLNvat());
        strwn.setKonto(kontonetto);
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
        Konto kontovat = kontoDAO.findKonto("221-3", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        strwn.setKonto(kontovat);
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

  
    
public static void main(String[] args) throws SAXException, IOException {
        try {
            File fXmlFile = new File("D:\\bank.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            doc.getDocumentElement().normalize();
            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
            NodeList nList1 = doc.getElementsByTagName("Ntry");
            System.out.println("----------------------------");
            int len = nList1.getLength();
                for (int temp = 0; temp < len; temp++) {
                    Node nNode1 = nList1.item(temp);
                    if (nNode1.getNodeType() == Node.ELEMENT_NODE) {
                        try {
                            Element eElement = (Element) nNode1;
                            String elt = eElement.getElementsByTagName("Id").item(0) == null ? "brak" : eElement.getElementsByTagName("Id").item(0).getFirstChild().getNextSibling().getTextContent();
                            System.out.println("IBAN : " + elt);
                            String elt1 = eElement.getElementsByTagName("Amt").item(0).getTextContent();
                            System.out.println("amount : " + elt1+" "+eElement.getAttribute("Ccy"))
                                    ;
                        } catch (Exception e) {
                            E.e(e);
                        }
                    }
                }
        } catch (ParserConfigurationException ex) {
            Logger.getLogger(Dedraparser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
   
    
   
    
}
