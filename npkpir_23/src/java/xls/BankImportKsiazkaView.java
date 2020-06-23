/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import comparator.FakturaRozrachunkicomparator;
import dao.FakturaRozrachunkiDAO;
import daoFK.TabelanbpDAO;
import daoFK.WalutyDAOfk;
import data.Data;
import embeddable.Mce;
import entity.FakturaRozrachunki;
import entityfk.Tabelanbp;
import entityfk.Waluty;
import entityfk.Wiersz;
import error.E;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.component.html.HtmlOutputText;
import javax.inject.Inject;
import msg.Msg;
import org.apache.commons.io.FilenameUtils;
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
public class BankImportKsiazkaView implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;
    @Inject
    private FakturaRozrachunkiDAO fakturaRozrachunkiDAO;
    private List<byte[]> pobraneplikibytes;
    public  List<ImportBankWiersz> pobranefaktury;
    public  List<ImportBankWiersz> pobranefakturyfilter;
    public  List<ImportBankWiersz> selected;
    private List<String> rodzajedokKlienta;
    private List<ImportowanyPlik> rodzajeimportu;
    private ImportowanyPlik wybranyrodzajimportu;
    private String wiadomoscnieprzypkonta;
    private String rodzajdok;
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
    
    
    @PostConstruct
    public void init() { //E.m(this);
        rodzajedokKlienta = new ArrayList<>(Arrays.asList("wb","wbe","wbm","wbme"));
        rodzajeimportu = zrobrodzajeimportu();
        tabelanbppl = tabelanbpDAO.findByTabelaPLN();
        walutapln = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        pobraneplikibytes = new ArrayList<>();
        //ibankonto = zrobibankonto();
    }
    
//    private Map<String, Konto> zrobibankonto() {
//        List<Wiersz> wiersze = wierszDAO.pobierzWierszeMcDokImportIBAN(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
//        Map<String,Konto> lista = new HashMap<>();
//        if (wiersze!=null && wiersze.size()>0) {
//            for (Wiersz p : wiersze) {
//                Konto zwrot = null;
//                if (p.getIban()!=null && !p.getIban().equals("")) {
//                    if (p.getStronaWn()!=null && !p.getStronaWn().getKonto().getPelnynumer().startsWith("13")) {
//                        zwrot = p.getStronaWn().getKonto();
//                    } else if (p.getStronaMa()!=null && !p.getStronaMa().getKonto().getPelnynumer().startsWith("13")) {
//                        zwrot = p.getStronaMa().getKonto();
//                    }
//                }
//                if (zwrot!=null) {
//                    String nowyiban = p.getIban().replace("\"", "").replace("'", "").replace("'", "");
//                    lista.put(nowyiban, zwrot);
//                }
//            }
//        }
//        return lista;
//    }
//    
    private List<ImportowanyPlik> zrobrodzajeimportu() {
        List<ImportowanyPlik> zwrot = BankImportWykaz.getWYKAZ();
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
                PrimeFaces.current().ajax().update("akordeon:panelplikbankall");
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
                         for (Iterator<ImportBankWiersz> it = pobranefaktury.iterator(); it.hasNext();) {
                             ImportBankWiersz wie = it.next();
                             if (wie.getWnma().equals("Ma")||!wie.getNrtransakji().contains("PRZELEW")||!wie.getNrtransakji().contains("PRZYCHODZĄCY")) {
                                 it.remove();
                             }
                         }
                    }
                }
                pobraneplikibytes =  new ArrayList();
            }
            if (pobranefaktury!=null && !pobranefaktury.isEmpty()) {
                String mc = Data.getMc(pobranefaktury.get(0).getDatatransakcji());
                if (!mc.equals(wpisView.getMiesiacWpisu())) {
                    Msg.msg("e","Importowany wyciąg nalezy do innego miesiąca. Proszę zmienić miesiąc.");
                } else {
                    generujbutton.setRendered(true);
                }
                List<FakturaRozrachunki> wierszezmiesiaca = fakturaRozrachunkiDAO.findByPodatnikrokMc(wpisView);
                List<FakturaRozrachunki> wierszepoprzednie = fakturaRozrachunkiDAO.findByPodatnikIBAN(wpisView);
                if (wierszezmiesiaca!=null && wierszezmiesiaca.size()>0) {
                    for (ImportBankWiersz p : pobranefaktury) {
                        if (sprawdzduplikat(p,wierszezmiesiaca)) {
                            p.setJuzzaksiegowany(true);
                        }
                    }
                }
                if (wierszepoprzednie!=null && wierszepoprzednie.size()>0) {
                    for (ImportBankWiersz p : pobranefaktury) {
                        uzupelnijkontrahenta(p,wierszepoprzednie);
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
            error.E.s(E.e(e));
        }
    }

    public void generuj() {
        if (pobranefaktury !=null && pobranefaktury.size()>0) {
            int ile = 0;
            Waluty walutadokumentu = walutyDAOfk.findWalutaBySymbolWaluty(naglowek.getWyciagwaluta());
            for (ImportBankWiersz r : pobranefaktury) {
                if (!r.isJuzzaksiegowany()) {
                    int zwrot = generowanieDokumentu(r, walutadokumentu);
                    if (zwrot==0) {
                        ile++;
                    } else {
                        Msg.msg("e","Błąd przy generowaniu wpisu "+r.getKontrahent());
                        break;
                    }
                }
            }
            Msg.msg("Zaksięgowano "+ile+" przelewów");
        } else {
            Msg.msg("e", "Błąd! Lista danych źrdółowych jest pusta");
        }
    }
    
        
     public int generowanieDokumentu(ImportBankWiersz r, Waluty walutadokumentu) {
        int zwrot = 0;
        try {
            FakturaRozrachunki nowe = new FakturaRozrachunki(r, wpisView.getPodatnikObiekt(), wpisView.getUzer(), rodzajdok, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            pobierzostatninumer(nowe);
            try {
                if (nowe!=null && nowe.getKontrahent()!=null) {
                    fakturaRozrachunkiDAO.dodaj(nowe);
                } else {
                    zwrot++;
                }
            } catch (Exception e) {
                Msg.msg("e", "Wystąpił błąd - nie zaksięgowano pozycji "+rodzajdok);
            }
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
     
     public void pobierzostatninumer(FakturaRozrachunki sel) {
        List<FakturaRozrachunki> wprowadzoneplatnosci = fakturaRozrachunkiDAO.findByPodatnikrokMc(wpisView);
        sel.setNrdokumentu(null);
        for (Iterator<FakturaRozrachunki> it = wprowadzoneplatnosci.iterator(); it.hasNext();) {
            if (!it.next().getRodzajdokumentu().equals(sel.getRodzajdokumentu())) {
                it.remove();
            }
        }
        if (wprowadzoneplatnosci.size() > 0) {
            Collections.sort(wprowadzoneplatnosci, new FakturaRozrachunkicomparator());
            String ostatninumer = wprowadzoneplatnosci.get(wprowadzoneplatnosci.size()-1).getNrdokumentu();
            sel.setNrdokumentu(zrobnowynumer(ostatninumer));
        } else {
            String nr = String.valueOf(Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu()))+"/1";
            sel.setNrdokumentu(nr);
        }
    }
    
     private String zrobnowynumer(String starynumer) {
        String nowynumer = starynumer;
        if (starynumer.contains("/")) {
            String[] numertablica = starynumer.split("/");
            if (numertablica.length == 2) {
                String koncowka = numertablica[numertablica.length-1];
                int nowakoncowka = Integer.parseInt(koncowka)+1;
                String nowakoncowka2 = String.valueOf(nowakoncowka);
                nowynumer = numertablica[0]+"/"+nowakoncowka2;
            } else if (numertablica.length == 3) {
                String poczatek = numertablica[0];
                int nowypoczatek = Integer.parseInt(poczatek)+1;
                String nowypoczatek2 = String.valueOf(nowypoczatek);
                nowynumer = nowypoczatek2+"/"+numertablica[1]+"/"+numertablica[2];
            }
        }
        return nowynumer;
    }
    
    private boolean sprawdzduplikat(ImportBankWiersz p, List<FakturaRozrachunki> wierszezmiesiaca) {
        boolean zwrot = false;
        for (FakturaRozrachunki r : wierszezmiesiaca) {
            if (r.getIban()!=null && r.getIban().equals(p.getIBAN())) {
                if (r.getData().equals(p.getDatatransakcji())){
                    if (r.getKwotapln()==p.getKwota()) {
                        zwrot = true;
                    }
                }
            }
        }
        return zwrot;
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

    public List<String> getRodzajedokKlienta() {
        return rodzajedokKlienta;
    }

    public void setRodzajedokKlienta(List<String> rodzajedokKlienta) {
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
//                    error.E.s("");
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
            error.E.s("");
        } catch (Exception ex) {
            // Logger.getLogger(Dedraparser.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void uzupelnijkontrahenta(ImportBankWiersz p, List<FakturaRozrachunki> wierszepoprzednie) {
        for (FakturaRozrachunki r : wierszepoprzednie) {
            if (r.getIban().equals(p.getIBAN())) {
                p.setKlient(r.getKontrahent());
                break;
            }
        }
    }

    

    

    

    

    

   

    

    

   
    

    
   
    
   
    
}
