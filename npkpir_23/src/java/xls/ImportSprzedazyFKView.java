/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import beansDok.ListaEwidencjiVat;
import dao.EvewidencjaDAO;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import daoFK.DokDAOfk;
import daoFK.KliencifkDAO;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import daoFK.KontopozycjaZapisDAO;
import daoFK.UkladBRDAO;
import embeddable.PanstwaEUSymb;
import embeddablefk.ImportJPKSprzedaz;
import entity.Evewidencja;
import entity.JPKSuper;
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
import java.io.InputStream;
import java.io.Serializable;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import jpk201801.JPK.SprzedazWiersz;
import jpkabstract.SprzedazWierszA;
import msg.Msg;import org.apache.commons.io.FilenameUtils;
import org.joda.time.DateTime;
 import org.primefaces.PrimeFaces;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class ImportSprzedazyFKView  implements Serializable {
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
    private DokDAOfk dokDAOfk;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private KlienciDAO klDAO;
    @Inject
    private ListaEwidencjiVat listaEwidencjiVat;
    private JPKSuper jpk;
    private String rodzajdok;
    private PanelGrid grid1;
    private PanelGrid grid2;
    private PanelGrid grid3;
    private byte[] plikinterpaper;
    private CommandButton generujbutton;
    private List<ImportJPKSprzedaz> listasprzedaz;
    private List<ImportJPKSprzedaz> listasprzedazselected;
    private Konto kontonetto;
    private Konto kontonettokoszt;
    private Konto kontovat;
    private Konto kontovatnaliczony;
    private Tabelanbp tabelanbppl;
    private Waluty walutapln;
        
    
    public void init() { //E.m(this);
        kontonetto = kontoDAO.findKonto("702-2", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontovat = kontoDAO.findKonto("221-1", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontonettokoszt = kontoDAO.findKonto("403", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        kontovatnaliczony = kontoDAO.findKonto("221-3", wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        tabelanbppl = tabelanbpDAO.findByTabelaPLN();
        walutapln = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
    }
    
    
    
    public void pobierzplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName());
            if (extension.equals("xml")) {
                plikinterpaper = uploadedFile.getContents();
                PrimeFaces.current().ajax().update("panelplikjpk");
                grid1.setRendered(true);
                Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
            } else {
                Msg.msg("e","Niewłaściwy typ pliku");
            }
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
        PrimeFaces.current().executeScript("PF('dialogAjaxCzekaj').hide()");
    }
    
    public void importujsprzedaz() {
        try {
                jpk = pobierzJPK();
                if (rodzajdok.equals("sprzedaż")) {
                    listasprzedaz = stworzlistesprzedaz(jpk);
                }
                grid3.setRendered(true);
                generujbutton.setRendered(true);
                Msg.msg("Sukces. Dane z pliu  zostały skutecznie załadowane");
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać danych z pliku");
        }
        PrimeFaces.current().executeScript("PF('dialogAjaxCzekaj').hide()");
    }
    
    private List<ImportJPKSprzedaz> stworzlistesprzedaz(JPKSuper jpk) {
        List<Klienci> k = klDAO.findAll();
        List<ImportJPKSprzedaz> zwrot = new ArrayList<>();
        List<SprzedazWiersz> wiersze = jpk.getSprzedazWiersz();
        int i = 1;
        for (SprzedazWiersz p : wiersze) {
            ImportJPKSprzedaz s = new ImportJPKSprzedaz(p);
            String pobranadata = p.getDataSprzedazy()!=null ? p.getDataSprzedazy().toString() : p.getDataWystawienia().toString();
            boolean czydobradata = data.Data.czydatajestwmcu(pobranadata, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            if (czydobradata) {
                s.setKlient(ImportBean.ustawkontrahenta(p.getNrKontrahenta(), p.getNazwaKontrahenta(), k, gUSView, klDAO));
                s.setId(i++);
                String rodzajdk = "SZ";
                //int polska0unia1zagranica2 = 0;
                if (!wpisView.isVatowiec()) {
                    rodzajdk = "RACHSP";
                }
                if (s.getKlient().getKrajnazwa()!=null && !s.getKlient().getKrajkod().equals("PL")) {
                    //polska0unia1zagranica2 = 2;
                    rodzajdk = "EXP";
                    if (PanstwaEUSymb.getWykazPanstwUE().contains(s.getKlient().getKrajkod())) {
                        //polska0unia1zagranica2 = 1;
                        rodzajdk = "WDT";
                    }
                }
                Dokfk nd = new Dokfk(s, wpisView, rodzajdk);
                Dokfk juzjest = dokDAOfk.findDokfkObjKontrahent(nd);
                if (juzjest!=null) {
                    s.setJuzzaksiegowany(true);
                } 
                zwrot.add(s);
            }
        }
        return zwrot;
    }
    
    
     public void generujsprzedaz() {
        try {
            List<Dokfk> lista = stworzdokumenty();
            if (lista.isEmpty()) {
                Msg.msg("w", "Nie wygenerowano żadnych dokumentów");
            } else {
                Msg.msg("Wygenerowano dokumenty w liczbie "+lista.size());
            }
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się wygenerować dokumentów");
        }
        PrimeFaces.current().executeScript("PF('dialogAjaxCzekaj').hide()");
    }
    
    private JPKSuper pobierzJPK() {
       InputStream is = new ByteArrayInputStream(plikinterpaper);
       JPKSuper zwrot = null;
       try {
           JAXBContext context = JAXBContext.newInstance(jpk201801.JPK.class);
           Unmarshaller unmarshaller = context.createUnmarshaller();
           zwrot = (jpk201801.JPK) unmarshaller.unmarshal(is);
       } catch (Exception ex) {}
       return zwrot;
    }
    public void grid2pokaz() {
        grid2.setRendered(true);
    }
     
    private List<Dokfk> stworzdokumenty() {
        List<Dokfk> dokumenty = Collections.synchronizedList(new ArrayList<>());
        List<Klienci> k = klDAO.findAll();
        List<ImportJPKSprzedaz> lista = listasprzedazselected!=null ? listasprzedazselected : listasprzedaz;
        if (lista != null) {
            lista.forEach((wiersz) -> {
                if (wiersz.getSprzedazWiersz().getNrKontrahenta() != null && wiersz.getSprzedazWiersz().getNrKontrahenta().length()==10) {
                    Zwrotgenerowania dok = stworznowydokument(wiersz,k);
                    if (dok.getDokfk()!=null) {
                        dokumenty.add(dok.getDokfk());
                        dokDAOfk.dodaj(dok.getDokfk());
                    } else {
                        Msg.msg("e", dok.getWiadomosc());
                    }
                }
            });
        }
        return dokumenty;
    }
    
    private Zwrotgenerowania stworznowydokument(ImportJPKSprzedaz wiersz,List<Klienci> klienci) {
        Zwrotgenerowania zw = new Zwrotgenerowania();
        Dokfk nd = null;
        String msg = zw.getWiadomosc();
        Klienci kontrahent = ImportBean.ustawkontrahenta(wiersz.getSprzedazWiersz().getNrKontrahenta(), wiersz.getSprzedazWiersz().getNazwaKontrahenta(), klienci, gUSView, klienciDAO);
        if (kontrahent!=null) {
            try {
                String rodzajdk = "SZ";
                if (!wpisView.isVatowiec()) {
                    rodzajdk = "RACHSP";
                }
                if (kontrahent.getKrajnazwa()!=null && !kontrahent.getKrajkod().equals("PL")) {
                    rodzajdk = "EXP";
                    if (PanstwaEUSymb.getWykazPanstwUE().contains(kontrahent.getKrajkod())) {
                        rodzajdk = "WDT";
                    }
                }
                msg = "Problem z generowaniem numeru kolejnego dokumentu dla poz.:"+wiersz.getId();
                int numerkolejny = ImportBean.oblicznumerkolejny(rodzajdk, dokDAOfk, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                nd = new Dokfk(numerkolejny, wpisView.getRokWpisuSt());
                nd.setKontr(kontrahent);
                ustawdaty(nd, wiersz.getSprzedazWiersz());
                msg = "Problem z generowaniem numeru własnego dokumentu dla poz.:"+wiersz.getId();
                ImportBean.ustawnumerwlasny(nd, wiersz.getSprzedazWiersz().getDowodSprzedazy());
                nd.setOpisdokfk("sprzedaż towaru");
                nd.setPodatnikObj(wpisView.getPodatnikObiekt());
                msg = "Problem z ustawieniem rodzaju dok. dla poz.:"+wiersz.getId();
                ImportBean.ustawrodzajedok(nd, rodzajdk, rodzajedokDAO, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
                nd.setTabelanbp(tabelanbppl);
                nd.setWalutadokumentu(walutapln);
                msg = "Problem z generowaniem ewidencji vat dla poz.:"+wiersz.getId();
                if (rodzajdk.equals("RACHSP")) {
                    nd.setEwidencjaVAT(null);
                } else {
                    ImportBean.podepnijEwidencjeVat(nd, wiersz.getSprzedazWiersz().getNetto(), wiersz.getSprzedazWiersz().getVat(), listaEwidencjiVat);
                }
                Dokfk juzjest = dokDAOfk.findDokfkObjKontrahent(nd);
                if (juzjest!=null) {
                    nd = null;
                    wiersz.setJuzzaksiegowany(true);
                    msg = "Duplikat dla poz.:"+wiersz.getId();
                } else {
                    ustawwiersze(nd, wiersz);
                    nd.setImportowany(true);
                    nd.setWprowadzil(wpisView.getUzer().getLogin());
                    nd.przeliczKwotyWierszaDoSumyDokumentu();
                }
                zw.setDokfk(nd);
                zw.setWiadomosc(null);
            } catch (Exception e) {
                zw.setWiadomosc(msg);
            }
        } else {
            msg = "Problem z generowaniem kontrahenta dla poz.:"+wiersz.getId();
            zw.setWiadomosc(msg);
        }
        return zw;
    }
    
    class Zwrotgenerowania {
        private String wiadomosc;
        private Dokfk dokfk;

        public String getWiadomosc() {
            return wiadomosc;
        }

        public void setWiadomosc(String wiadomosc) {
            this.wiadomosc = wiadomosc;
        }

        public Dokfk getDokfk() {
            return dokfk;
        }

        public void setDokfk(Dokfk dokfk) {
            this.dokfk = dokfk;
        }
        
        
    }
    
    private void ustawdaty(Dokfk nd, SprzedazWiersz wiersz) {
        Format formatterX = new SimpleDateFormat("yyyy-MM-dd");
        String datadokumentu = formatterX.format(wiersz.getDataWystawienia().toGregorianCalendar().getTime());
        String datasprzedazy = formatterX.format(wiersz.getDataWystawienia().toGregorianCalendar().getTime());
        if (wiersz.getDataSprzedazy()!=null) {
            datasprzedazy = formatterX.format(wiersz.getDataSprzedazy().toGregorianCalendar().getTime());
        }
        nd.setDatadokumentu(datadokumentu);
        nd.setDataoperacji(datasprzedazy);
        nd.setDatawplywu(datadokumentu);
        nd.setDatawystawienia(datadokumentu);
        nd.setDataujecia(new Date());
        nd.setMiesiac(wpisView.getMiesiacWpisu());
        nd.setVatM(datasprzedazy.split("-")[1]);
        nd.setVatR(datasprzedazy.split("-")[0]);
    }
    
     
     
     
   
     private void ustawwiersze(Dokfk nd, ImportJPKSprzedaz wiersz) {
        nd.setListawierszy(new ArrayList<Wiersz>());
        if (rodzajdok.equals("sprzedaż")) {
            nd.getListawierszy().add(przygotujwierszNetto(wiersz.getKlient(), wiersz.getSprzedazWiersz(), nd));
            if (wiersz.getSprzedazWiersz().getVat() != 0) {
                nd.getListawierszy().add(przygotujwierszVat(wiersz.getSprzedazWiersz(), nd));
            }
        } else {
            nd.getListawierszy().add(przygotujwierszNettoK(wiersz.getKlient(), wiersz.getSprzedazWiersz(), nd));
            if (wiersz.getSprzedazWiersz().getVat() != 0) {
                nd.getListawierszy().add(przygotujwierszVatK(wiersz.getSprzedazWiersz(), nd));
            }
        }
    }
   private Wiersz przygotujwierszNetto(Klienci klient, SprzedazWiersz wiersz, Dokfk nd) {
        Wiersz w = new Wiersz(1, 0);
        uzupelnijwiersz(w, nd, 0);
        String opiswiersza = "sprzedaż towarów"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", Z.z(wiersz.getNetto()+wiersz.getVat()), null);
        StronaWiersza strma = new StronaWiersza(w, "Ma", wiersz.getNetto(), null);
        strwn.setKwotaPLN(Z.z(wiersz.getNetto()+wiersz.getVat()));
        strma.setKwotaPLN(wiersz.getNetto());
        strma.setKonto(kontonetto);
        strwn.setKonto(ImportBean.pobierzkontoWn(klient, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO));
        w.setStronaWn(strwn);
        w.setStronaMa(strma);
        return w;
    }
    
    private Wiersz przygotujwierszVat(SprzedazWiersz wiersz, Dokfk nd) {
        Wiersz w = new Wiersz(2, 2);
        uzupelnijwiersz(w, nd, 1);
        String opiswiersza = "sprzedaż towarów - VAT"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strma = new StronaWiersza(w, "Ma", wiersz.getVat(), null);
        strma.setKwotaPLN(wiersz.getVat());
        strma.setKonto(kontovat);
        w.setStronaMa(strma);
        return w;
    }
    
    private Wiersz przygotujwierszNettoK(Klienci klient, SprzedazWiersz wiersz, Dokfk nd) {
        Wiersz w = new Wiersz(1, 0);
        uzupelnijwiersz(w, nd, 0);
        String opiswiersza = "usługa transportowa"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strma = new StronaWiersza(w, "Ma", Z.z(wiersz.getNetto()+wiersz.getVat()), null);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", wiersz.getNetto(), null);
        strma.setKwotaPLN(Z.z(wiersz.getNetto()+wiersz.getVat()));
        strwn.setKwotaPLN(wiersz.getNetto());
        strwn.setKonto(kontonettokoszt);
        strma.setKonto(ImportBean.pobierzkontoMa(klient, kliencifkDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBRDAO));
        w.setStronaMa(strma);
        w.setStronaWn(strwn);
        return w;
    }
    
    private Wiersz przygotujwierszVatK(SprzedazWiersz wiersz, Dokfk nd) {
        Wiersz w = new Wiersz(2, 1);
        uzupelnijwiersz(w, nd, 1);
        String opiswiersza = "usługa transportowa - VAT"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strwn = new StronaWiersza(w, "Wn", wiersz.getVat(), null);
        strwn.setKwotaPLN(wiersz.getVat());
        strwn.setKonto(kontovatnaliczony);
        w.setStronaWn(strwn);
        return w;
    }
    
    private void uzupelnijwiersz(Wiersz w, Dokfk nd, int lpmacierzystego) {
        Tabelanbp t = tabelanbpDAO.findByTabelaPLN();
        w.setTabelanbp(t);
        w.setDokfk(nd);
        w.setLpmacierzystego(lpmacierzystego);
        w.setTabelanbp(w.getTabelanbp());
        w.setDataksiegowania(nd.getDatawplywu());
    }
    
    private double obliczstawke(SprzedazWierszA wiersz) {
        double stawka = 23;
        double netto = wiersz.getNetto();
        double vat = wiersz.getVat();
        double procent = Z.z4(vat/netto);
        if (procent>0.18) {
            stawka = 23;
        } else if (procent>0.07) {
            stawka = 8;
        }  else if (procent>0.04) {
            stawka = 5;
        } else {
            stawka = 0;
        }
        return stawka;
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

    public List<ImportJPKSprzedaz> getListasprzedaz() {
        return listasprzedaz;
    }

    public void setListasprzedaz(List<ImportJPKSprzedaz> listasprzedaz) {
        this.listasprzedaz = listasprzedaz;
    }

    public List<ImportJPKSprzedaz> getListasprzedazselected() {
        return listasprzedazselected;
    }

    public void setListasprzedazselected(List<ImportJPKSprzedaz> listasprzedazselected) {
        this.listasprzedazselected = listasprzedazselected;
    }

  

    
    
}
