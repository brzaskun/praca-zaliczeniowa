/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import beansDok.ListaEwidencjiVat;
import static beansFK.DokFKVATBean.pobierzKontoRozrachunkowe;
import beansFK.PlanKontFKBean;
import beansRegon.SzukajDaneBean;
import comparator.Kliencifkcomparator;
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
import org.joda.time.DateTime;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
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
            String filename = uploadedFile.getFileName();
            plikinterpaper = uploadedFile.getContents();
            Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
    }
    
    
    public void importujdok() {
        try {
            pobranefaktury = ReadXLSFile.getListafakturCSV(plikinterpaper);
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
            for (InterpaperXLS p : pobranefaktury) {
               generowanieDokumentu(p, k);
            }
            Msg.msg("Wygenerowano dokumenty księgowe");
        } else {
            Msg.msg("e", "Błąd! Lista danych źrdółowych jest pusta");
        }
    }
    
     public void generowanieDokumentu(InterpaperXLS interpaperXLS, List<Klienci> k) {
        String rodzajdok = interpaperXLS.getVatwaluta() > 0 ? "SZ" : "UPTK";
        Dokfk dokument = stworznowydokument(oblicznumerkolejny(rodzajdok),interpaperXLS, rodzajdok, k);
        try {
            dokument.setImportowany(true);
            dokDAOfk.dodaj(dokument);
            Msg.msg("Zaksięgowano dokument "+rodzajdok+" o nr własnym"+dokument.getNumerwlasnydokfk());
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu "+rodzajdok);
        }
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
        ustawwiersze(nd, interpaperXLS);
        nd.setImportowany(true);
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
        nd.setDatadokumentu(datadokumentu);
        nd.setDataoperacji(datasprzedazy);
        nd.setDatawplywu(datadokumentu);
        nd.setDatawystawienia(datadokumentu);
        nd.setDataujecia(new Date());
        nd.setMiesiac(wpisView.getMiesiacWpisu());
        nd.setVatM(datasprzedazy.split("-")[1]);
        nd.setVatR(datasprzedazy.split("-")[0]);
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
        nd.getListawierszy().add(przygotujwierszNetto(interpaperXLS, nd));
        if (interpaperXLS.getVatwaluta() != 0) {
            nd.getListawierszy().add(przygotujwierszVat(interpaperXLS, nd));
        }
    }

    private Wiersz przygotujwierszNetto(InterpaperXLS interpaperXLS, Dokfk nd) {
        Wiersz w = new Wiersz(0, 0);
        uzupelnijwiersz(w, nd);
        String opiswiersza = "usługa transportowa"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", interpaperXLS.getBruttowaluta(), null);
        StronaWiersza strma = new StronaWiersza(w, "Ma", interpaperXLS.getNettowaluta(), null);
        Konto kontonetto = kontoDAO.findKonto("702-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        strwn.setKwotaPLN(interpaperXLS.getBruttoPLN());
        strma.setKwotaPLN(interpaperXLS.getNettoPLN());
        strma.setKonto(kontonetto);
        strwn.setKonto(pobierzkontoWn(nd, interpaperXLS, nd.getKontr()));
        w.setStronaWn(strwn);
        w.setStronaMa(strma);
        return w;
    }
    
    private Wiersz przygotujwierszVat(InterpaperXLS interpaperXLS, Dokfk nd) {
        Wiersz w = new Wiersz(1, 2);
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
                            if (p.getNazwa().equals("sprzedaż 23%")||p.getNazwa().equals("usługi świad. poza ter.kraju")) {
                                eVatwpisFK.setNetto(Z.z(interpaperXLS.getNettoPLN()));
                                eVatwpisFK.setVat(Z.z(interpaperXLS.getVatPLN()));
                                eVatwpisFK.setBrutto(Z.z(interpaperXLS.getNettoPLN()+interpaperXLS.getVatPLN()));
                                eVatwpisFK.setDokfk(nd);
                                eVatwpisFK.setEstawka("op");
                                nd.getEwidencjaVAT().add(eVatwpisFK);
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
            if (!klient.getKrajkod().equals("PL")) {
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

   
    
   
    
}
