/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import beansDok.ListaEwidencjiVat;
import dao.DokDAOfk;
import dao.KlienciDAO;
import dao.KliencifkDAO;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.RodzajedokDAO;
import dao.TabelanbpDAO;
import dao.UkladBRDAO;
import dao.WalutyDAOfk;
import embeddable.PanstwaEUSymb;
import embeddablefk.ImportJPKSprzedaz;
import entity.JPKSuper;
import entity.Klienci;
import entityfk.Dokfk;
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
import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import jpk201801.JPK.SprzedazWiersz;
import jpkabstract.SprzedazWierszA;
 import msg.Msg;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.PrimeFaces;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pdffk.PdfJPKSprzedaz;
import view.WpisView;
import waluty.Z;


/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ImportSprzedazyFKView  implements Serializable {
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
    private int jpk1inne2;
    private PanelGrid grid1;
    private PanelGrid grid2;
    private PanelGrid grid3;
    private byte[] plikinterpaper;
    private CommandButton generujbutton;
    private CommandButton drukujbutton;
    private List<ImportJPKSprzedaz> listasprzedaz;
    private List<ImportJPKSprzedaz> listasprzedazselected;
            private List<ImportJPKSprzedaz> listasprzedazfilter;
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
        listasprzedaz = null;
        grid1.setRendered(false);
        grid2.setRendered(false);
        grid3.setRendered(false);
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
                    listasprzedaz = stworzlistesprzedaz(jpk, jpk1inne2);
                }
                grid3.setRendered(true);
                if (jpk1inne2==1) {
                    generujbutton.setRendered(true);
                }
                drukujbutton.setRendered(true);
                Msg.msg("Sukces. Dane z pliu  zostały skutecznie załadowane");
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać danych z pliku");
        }
        PrimeFaces.current().executeScript("PF('dialogAjaxCzekaj').hide()");
    }
    
    private List<ImportJPKSprzedaz> stworzlistesprzedaz(JPKSuper jpk, int jpk1inne2) {
        List<Klienci> k = klDAO.findAll();
        List<ImportJPKSprzedaz> zwrot = new ArrayList<>();
        List<SprzedazWierszA> wiersze = new ArrayList<>();
        if (jpk instanceof pl.gov.crd.wzor._2021._12._27._11148.JPK) {
                ((pl.gov.crd.wzor._2020._05._08._9393.JPK) jpk).getEwidencja().getSprzedazWiersz().forEach((p) -> {
                    SprzedazWierszA wiersz = (SprzedazWierszA) p;
                    wiersze.add(wiersz);
                });
            } else if (jpk instanceof pl.gov.crd.wzor._2021._12._27._11149.JPK) {
                ((pl.gov.crd.wzor._2021._12._27._11148.JPK) jpk).getEwidencja().getSprzedazWiersz().forEach((p) -> {
                   SprzedazWierszA wiersz = (SprzedazWierszA) p;
                    wiersze.add(wiersz);
                });
            } else if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9394.JPK) {
                ((pl.gov.crd.wzor._2020._05._08._9394.JPK) jpk).getEwidencja().getSprzedazWiersz().forEach((p) -> {
                    SprzedazWierszA wiersz = (SprzedazWierszA) p;
                    wiersze.add(wiersz);
                });
            } else if (jpk instanceof pl.gov.crd.wzor._2020._05._08._9393.JPK) {
                ((pl.gov.crd.wzor._2020._05._08._9393.JPK) jpk).getEwidencja().getSprzedazWiersz().forEach((p) -> {
                   SprzedazWierszA wiersz = (SprzedazWierszA) p;
                    wiersze.add(wiersz);
                });
            } else {
                jpk.getSprzedazWiersz().forEach((p) -> {
                    SprzedazWierszA wiersz = (SprzedazWierszA) p;
                    wiersze.add(wiersz);
                });
            }
        int i = 1;
        for (SprzedazWierszA p : wiersze) {
                ImportJPKSprzedaz s = new ImportJPKSprzedaz(p);
            try {
                String pobranadata = p.getDataSprzedazy()!=null ? p.getDataSprzedazy().toString() : p.getDataWystawienia().toString();
                boolean czydobradata = data.Data.czydatajestwmcu(pobranadata, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                if (czydobradata) {
                    Klienci klient = ImportBean.ustawkontrahentaImportJPK(p.getNrKontrahenta(), p.getNazwaKontrahenta(), k, klDAO);
                    if (jpk1inne2==1) {
                        if (klient.getNip()!=null && klient.getNip().length()>8) {
                           s.setKlient(klient);
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
                    } else {
                         if (klient.getNip()==null || klient.getNip().length()<8) {
                            s.setKlient(klient);
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
                            if (jpk1inne2==3 && (s.getSprzedazWiersz().getNetto()!=0.0 || s.getSprzedazWiersz().getVat()!=0.0)) {
                                zwrot.add(s);
                            } else if (jpk1inne2!=3){
                                zwrot.add(s);
                            }
                         }
                    }
                }
            } catch (Exception e) {
              E.e(e);
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
            listasprzedaz = null;
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się wygenerować dokumentów");
        }
        PrimeFaces.current().executeScript("PF('dialogAjaxCzekaj').hide()");
    }
    
    private JPKSuper pobierzJPK() {
       JPKSuper zwrot = null;
       InputStream is = new ByteArrayInputStream(plikinterpaper);
       try {
           JAXBContext context = JAXBContext.newInstance(pl.gov.crd.wzor._2020._05._08._9394.JPK.class);
           Unmarshaller unmarshaller = context.createUnmarshaller();
           zwrot = (pl.gov.crd.wzor._2020._05._08._9394.JPK) unmarshaller.unmarshal(is);
       } catch (Exception ex) {}
       if (zwrot ==null) {
            try {
                is = new ByteArrayInputStream(plikinterpaper);
                JAXBContext context = JAXBContext.newInstance(pl.gov.crd.wzor._2020._05._08._9393.JPK.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                zwrot = (pl.gov.crd.wzor._2020._05._08._9393.JPK) unmarshaller.unmarshal(is);
            } catch (Exception ex) {}
       }
        if (zwrot ==null) {
            try {
                is = new ByteArrayInputStream(plikinterpaper);
                JAXBContext context = JAXBContext.newInstance(jpk201701.JPK.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                zwrot = (jpk201701.JPK) unmarshaller.unmarshal(is);
            } catch (Exception ex) {}
        }
        if (zwrot ==null) {
            try {
                is = new ByteArrayInputStream(plikinterpaper);
                JAXBContext context = JAXBContext.newInstance(jpk201801.JPK.class);
                Unmarshaller unmarshaller = context.createUnmarshaller();
                zwrot = (jpk201801.JPK) unmarshaller.unmarshal(is);
            } catch (Exception ex) {}
        }
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
                        dokDAOfk.create(dok.getDokfk());
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
        Klienci kontrahent = ImportBean.ustawkontrahenta(wiersz.getSprzedazWiersz().getNrKontrahenta(), wiersz.getSprzedazWiersz().getNazwaKontrahenta(), klienci, klienciDAO);
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
    
    private void ustawdaty(Dokfk nd, SprzedazWierszA wiersz) {
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
   private Wiersz przygotujwierszNetto(Klienci klient, SprzedazWierszA wiersz, Dokfk nd) {
        Wiersz w = new Wiersz(1, nd, 0);
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
    
    private Wiersz przygotujwierszVat(SprzedazWierszA wiersz, Dokfk nd) {
        Wiersz w = new Wiersz(2, nd, 2);
        uzupelnijwiersz(w, nd, 1);
        String opiswiersza = "sprzedaż towarów - VAT"; 
        w.setOpisWiersza(opiswiersza);
        StronaWiersza strma = new StronaWiersza(w, "Ma", wiersz.getVat(), null);
        strma.setKwotaPLN(wiersz.getVat());
        strma.setKonto(kontovat);
        w.setStronaMa(strma);
        return w;
    }
    
    private Wiersz przygotujwierszNettoK(Klienci klient, SprzedazWierszA wiersz, Dokfk nd) {
        Wiersz w = new Wiersz(1, nd, 0);
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
    
    private Wiersz przygotujwierszVatK(SprzedazWierszA wiersz, Dokfk nd) {
        Wiersz w = new Wiersz(2, nd, 1);
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
    
   public void drukuj() {
       List<ImportJPKSprzedaz> lista = listasprzedazfilter!=null? listasprzedazfilter : listasprzedazselected!=null? listasprzedazselected : listasprzedaz;
       double netto = 0.0;
       double vat = 0.0;
       for (ImportJPKSprzedaz p : lista) {
           netto += p.getSprzedazWiersz().getNetto();
           vat += p.getSprzedazWiersz().getVat();
       }
       ImportJPKSprzedaz suma = new ImportJPKSprzedaz();
       suma.setSprzedazWiersz(new SprzedazWiersz());
       suma.setKlient(new Klienci());
       suma.getSprzedazWiersz().setK10(new BigDecimal(netto));
       suma.getSprzedazWiersz().setK16(new BigDecimal(vat));
       lista.add(suma);
       PdfJPKSprzedaz.drukuj(lista, wpisView);
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

    public int getJpk1inne2() {
        return jpk1inne2;
    }

    public void setJpk1inne2(int jpk1inne2) {
        this.jpk1inne2 = jpk1inne2;
    }

    public List<ImportJPKSprzedaz> getListasprzedazfilter() {
        return listasprzedazfilter;
    }

    public void setListasprzedazfilter(List<ImportJPKSprzedaz> listasprzedazfilter) {
        this.listasprzedazfilter = listasprzedazfilter;
    }

    public CommandButton getDrukujbutton() {
        return drukujbutton;
    }

    public void setDrukujbutton(CommandButton drukujbutton) {
        this.drukujbutton = drukujbutton;
    }

  

    
    
}
