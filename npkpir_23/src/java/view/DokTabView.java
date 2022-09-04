/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.CechaBean;
import beansDok.Rozrachunki;
import comparator.Dokcomparator;
import comparator.Rodzajedokcomparator;
import dao.AmoDokDAO;
import dao.DokDAO;
import dao.FakturaDAO;
import dao.InwestycjeDAO;
import dao.PodatnikDAO;
import dao.STRDAO;
import dao.StornoDokDAO;
import dao.UmorzenieNDAO;
import dao.UzDAO;
import entity.Dok;
import entity.EVatwpis1;
import entity.Faktura;
import entity.Inwestycje;
import entity.Podatnik;
import entity.Rodzajedok;
import entity.StornoDok;
import entity.UmorzenieN;
import entity.Uz;
import entityfk.Cechazapisu;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
 import org.primefaces.PrimeFaces;
import pdf.PdfDok;
import pdf.PdfPK;

/**
 *
 * @author Osito
 */
@Named(value = "DokTabView")
@ViewScoped
public class DokTabView implements Serializable {
    private static final long serialVersionUID = 1L;
    //wybranedokumentyDoDruku
    private List<Dok> gosciuwybral;
    //wybranedokumenty do usuniecia
    private List<Dok> grupausun;
    private Dok dokdoUsuniecia;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private UmorzenieNDAO umorzenieNDAO;
    //tablica obiektów
    private List<Dok> obiektDOKjsf;
    //tablica obiektw danego klienta
    private List<Dok> dokumentypobrane;
    //tablica obiektów danego klienta z określonego roku i miesiąca
    private List<Dok> dokumentylista;
    //tablica obiektów danego klienta z określonego roku i miesiąca
    private List<Dok> dokumentyFiltered;
    //dokumenty o tym samym okresie vat
    private List<Dok> dokvatmc;
    private List<Rodzajedok> dokumentypodatnika;
    private List<String> kontrahentypodatnika;
    //dokumenty okresowe
    private List<Dok> dokumentyokresowe;
    private List<String> walutywdokum;
    
    /*pkpir*/
    @Inject
    private WpisView wpisView;
    @Inject private DokDAO dokDAO;
    @Inject private Dok selDokument;
    @Inject private StornoDokDAO stornoDokDAO;
    @Inject private STRDAO sTRDAO;
    @Inject private FakturaDAO fakturaDAO;
    private boolean button;
    @Inject private Uz uzytkownik;
    @Inject private 
    UzDAO uzDAO;
    @Inject private 
    AmoDokDAO amoDokDAO;
    private List<Inwestycje> inwestycje;
     @Inject private InwestycjeDAO inwestycjeDAO;
     private String wybranacechadok;
     private List<String> cechydokzlisty;
     private boolean sumuj;
     private double sumanetto;
     private double sumavat;
     private double sumabrutto;
     private boolean dodajdlajpk;

    public DokTabView() {
       inicjalizacjalist();
   }
    
    private void inicjalizacjalist() {
         //dokumenty podatnika
        dokumentypobrane = Collections.synchronizedList(new ArrayList<>());
        //dokumenty podatnika z miesiaca
        dokumentylista = Collections.synchronizedList(new ArrayList<>());
        //dekumenty o tym samym okresie vat
        dokvatmc = Collections.synchronizedList(new ArrayList<>());
        //dokumenty okresowe
        dokumentyokresowe = Collections.synchronizedList(new ArrayList<>());
        gosciuwybral = Collections.synchronizedList(new ArrayList<>());
        dokumentypodatnika = Collections.synchronizedList(new ArrayList<>());
        kontrahentypodatnika = Collections.synchronizedList(new ArrayList<>());
        walutywdokum = Collections.synchronizedList(new ArrayList<>());
        dokumentyFiltered = null;
        sumanetto = 0.0;
        sumavat = 0.0;
        sumabrutto = 0.0;
    }

   
    
    @PostConstruct
    public void init() { //E.m(this);
        inicjalizacjalist();
        try {
            inwestycje = inwestycjeDAO.findInwestycje(wpisView.getPodatnikWpisu(), false);
        } catch (Exception e) {
            E.e(e);
        }
        Integer rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        Podatnik podatnik = wpisView.getPodatnikObiekt();
        uzytkownik = wpisView.getUzer();
        try {
            StornoDok tmp = stornoDokDAO.find(rok, mc, podatnik.getNazwapelna());
            setButton(false);
        } catch (Exception ef) {
            setButton(true);
        }
        int numerkolejny = 1;
        if (wpisView.getPodatnikObiekt().getNumerpkpir() != null) {
            try {
                //zmienia numer gdy srodek roku
                int index = wpisView.getPodatnikObiekt().getNumerpkpir().size() - 1;
                String wartosc = wpisView.getPodatnikObiekt().getNumerpkpir().get(index).getParametr();
                numerkolejny = Integer.parseInt(wartosc);
            } catch (Exception e) {
                error.E.s("Blad " + e.toString());
            }
        }
        try {
            dokumentypobrane.addAll(dokDAO.zwrocBiezacegoKlientaRokMC(podatnik, rok.toString(), mc));
            //sortowanie dokumentów
            if (dokumentypobrane!=null) {
                for (Iterator<Dok> it = dokumentypobrane.iterator(); it.hasNext();) {
                    Dok tmpx = it.next();
                    if (dodajdlajpk==false && tmpx.getRodzajedok().isTylkojpk()) {
                        it.remove();
                    }
                }
            }
            Collections.sort(dokumentypobrane, new Dokcomparator());
        } catch (Exception e) {
            E.e(e);
        }
        numerkolejny = dokDAO.liczdokumenty(rok.toString(), mc, podatnik) + 1;
        dokumentylista = Collections.synchronizedList(new ArrayList<>());
        Set<Rodzajedok> dokumentyl = new HashSet<>();
        Set<String> kontrahenty = new HashSet<>();
        Set<String> waluty = new HashSet<>();
         if (dokumentypobrane != null) {
            cechydokzlisty = CechaBean.znajdzcechy(dokumentypobrane);
        }
        for (Dok tmpx : dokumentypobrane) {
            boolean dodaj = false;
            tmpx.setNrWpkpir(numerkolejny++);
            if (tmpx.getNrWlDk()==null) {
                System.out.println("");
                dokDAO.remove(tmpx);
            } else {
                if (tmpx.getPkpirM().equals(mc)) {
                    dokumentyl.add(tmpx.getRodzajedok());
                    String kontra = tmpx.getKontr()!=null?tmpx.getKontr().getNpelna():"problem "+tmpx.getNrWlDk();
                    kontrahenty.add(kontra);
                    waluty.add(tmpx.getWalutadokumentu() != null ? tmpx.getWalutadokumentu().getSymbolwaluty() : "PLN");
                    if (wybranacechadok == null) {
                        dodaj = true;
                    } else if (!tmpx.getCechadokumentuLista().isEmpty() && !wybranacechadok.equals("bezcechy")) {
                        for (Cechazapisu cz : tmpx.getCechadokumentuLista()) {
                            if (cz.getNazwacechy().equals(wybranacechadok)) {
                                dodaj = true;
                                break;
                            }
                        }
                    } else if (wybranacechadok.equals("bezcechy") && (tmpx.getCechadokumentuLista() == null || tmpx.getCechadokumentuLista().isEmpty())){
                        dodaj = true;
                    }
                    if (dodaj) {
                        dokumentylista.add(tmpx);
                        sumujdokumentydodane(tmpx);
                    }
                }
            }
        }
         dokumentypodatnika.addAll(dokumentyl);
        Collections.sort(dokumentypodatnika, new Rodzajedokcomparator());
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        kontrahentypodatnika.addAll(kontrahenty);
        Collections.sort(kontrahentypodatnika, collator);
        walutywdokum.addAll(waluty);
        Collections.sort(walutywdokum);
       
    }
    
    
    
     public void sprawdzCzyNieDuplikat() {
        Msg.msg("i", "Rozpoczynam badanie bazy klienta "+wpisView.getPodatnikWpisu()+" na obecność duplikatów");
        List<Dok> pobranedokumentypodatnika = dokDAO.zwrocBiezacegoKlientaDuplikat(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Iterator it = pobranedokumentypodatnika.iterator();
        int licznik = 0;
        while (it.hasNext()) {
            Dok badany = (Dok) it.next();
            it.remove();
            boolean tmp = false;
            try {
                tmp = pobranedokumentypodatnika.contains(badany);
                ++licznik;
            } catch (Exception ex) {}
            if (tmp == true) {
                String wiadomosc = "Dokument typu "+badany.getRodzajedok().getSkrot()+" dla tego klienta, o numerze "+badany.getNrWlDk()+" i kwocie netto "+badany.getNetto()+" jest juz zaksiegowany u podatnika: " + badany.getPodatnik();
                Msg.msg("e", wiadomosc);
                return;
            }
        }
        Msg.msg("i", "Skończono badanie "+licznik+" dokumenów na obecność duplikatów. Nie znaleziono ani jednego");
    }

    public void destroy(Dok selDok) {
        dokdoUsuniecia = new Dok();
        dokdoUsuniecia = selDok;
    }
    
     public void destroygrupa() {
        grupausun = Collections.synchronizedList(new ArrayList<>());
        grupausun = gosciuwybral;
    }

     public void destroygrupa2() {
          for(Dok p : grupausun){
              dokdoUsuniecia = p;
              destroy2();
          }
      }
    public void destroy2() {
        if (dokdoUsuniecia.getRodzajedok() == null) {
            try {
                dokdoUsuniecia.setInwestycja(null);
                dokDAO.edit(dokdoUsuniecia);
                dokDAO.remove(dokdoUsuniecia);
                if (dokdoUsuniecia.getFaktura()!=null) {
                    Faktura f = dokdoUsuniecia.getFaktura();
                    f.setZaksiegowana(false);
                    fakturaDAO.edit(f);
                    Msg.msg("Oznaczono dokument źródłowy jako niezaksięgowany");
                }
                if (dokdoUsuniecia.getFaktura()!=null) {
                    Faktura f = dokdoUsuniecia.getFakturakontrahent();
                    f.setZaksiegowanakontrahent(false);
                    fakturaDAO.edit(f);
                    Msg.msg("Oznaczono dokument źródłowy jako niezaksięgowany");
                }
                dokumentypobrane.remove(dokdoUsuniecia);
                dokumentylista.remove(dokdoUsuniecia);
                dokumentyFiltered.remove(dokdoUsuniecia);
            } catch (Exception e) {
                E.e(e);
            }
            Msg.msg("i", "Dokument usunięty " + dokdoUsuniecia.getIdDok().toString(), "form:messages");
        } else if (dokdoUsuniecia.getStatus().equals("bufor")) {
            if ((Rozrachunki.sprawdzczyniemarozrachunkow(dokdoUsuniecia) == true)) {
                Msg.msg("e", "Dokument nie usunięty - Usuń wpierw dokument strono, proszę " + dokdoUsuniecia.getIdDok().toString());
            } else if (sprawdzczytoniesrodek(dokdoUsuniecia) == true) {
                Msg.msg("e", "Dokument nie usunięty - Usuń wpierw środek z ewidencji " + dokdoUsuniecia.getIdDok().toString());
            } else {
                if (dokdoUsuniecia.getRodzajedok().getSkrot().equals("AMO")) {
                    //poszukiwanie czy nie ma po nim jakiegos
                    List<UmorzenieN> umorzenia = umorzenieNDAO.findByDok(dokdoUsuniecia);
                    if (!umorzenia.isEmpty()) {
                        for (UmorzenieN pa : umorzenia) {
                            pa.setDokfk(null);
                            umorzenieNDAO.edit(pa);
                        }
                    }
                }
                try {
                    String probsymbolu = dokdoUsuniecia.getSymbolinwestycji();
                    if (!probsymbolu.equals("wybierz") && (!probsymbolu.isEmpty())) {
                        usunDokInwestycje(dokdoUsuniecia);
                    }
                } catch (Exception e) {
                    E.e(e);
                }
                try {
                    dokdoUsuniecia.setInwestycja(null);
                    dokDAO.edit(dokdoUsuniecia);
                    dokDAO.remove(dokdoUsuniecia);
                    dokumentypobrane.remove(dokdoUsuniecia);
                    dokumentylista.remove(dokdoUsuniecia);
                    dokumentyFiltered.remove(dokdoUsuniecia);
                    if (dokdoUsuniecia.getFaktura()!=null) {
                        Faktura f = dokdoUsuniecia.getFaktura();
                        f.setZaksiegowana(false);
                        fakturaDAO.edit(f);
                        Msg.msg("Oznaczono dokument źródłowy jako niezaksięgowany");
                    }
                    if (dokdoUsuniecia.getFaktura()!=null) {
                        Faktura f = dokdoUsuniecia.getFakturakontrahent();
                        f.setZaksiegowanakontrahent(false);
                        fakturaDAO.edit(f);
                        Msg.msg("Oznaczono dokument źródłowy jako niezaksięgowany");
                    }
                } catch (Exception e) {
                    E.e(e);
                }
                Msg.msg("i", "Dokument usunięty " + dokdoUsuniecia.getIdDok().toString());
            }
        } else {
            Msg.msg("e", "Dokument w księgach, nie można usunąć ");
        }
    }
    
     public void destroy2roz() throws Exception {
        if(dokdoUsuniecia.getStatus().equals("bufor")){
        String temp = dokdoUsuniecia.getRodzajedok().getSkrot();
        if ((Rozrachunki.sprawdzczyniemarozrachunkow(dokdoUsuniecia) == true)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dokument nie usunięty - Usuń wpierw dokument strono, proszę", dokdoUsuniecia.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            throw new Exception();
        } else if (sprawdzczytoniesrodek(dokdoUsuniecia) == true) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dokument nie usunięty - Usuń wpierw środek z ewidencji", dokdoUsuniecia.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            throw new Exception();
        } else {
            dokdoUsuniecia.getRodzajedok().getSkrot().equals("OT");
            try {
                dokDAO.remove(dokdoUsuniecia);
                dokumentypobrane.remove(dokdoUsuniecia);
                dokumentylista.remove(dokdoUsuniecia);
                dokumentyFiltered.remove(dokdoUsuniecia);
            } catch (Exception e) { E.e(e); 
                throw new Exception();
            }
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dokument usunięty", dokdoUsuniecia.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    } else {
            Msg.msg("e","Dokument w księgach, nie można usunąć");
            throw new Exception();
        }
    }

    
    private boolean sprawdzczytoniesrodek(Dok dok) {
        return sTRDAO.findSTR(dok.getPodatnik().getNazwapelna(), dok.getNetto(), dok.getNrWlDk());
    }

    //usun jak wciaz dziala bez nich
    public void aktualizujTabele(AjaxBehaviorEvent e) throws IOException {
        aktualizuj();
        Msg.msg("i","Udana zamiana klienta. Aktualny klient to: " +wpisView.getPrintNazwa()+" okres rozliczeniowy: "+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu()+" opodatkowanie: "+wpisView.getRodzajopodatkowania());
    }
    
    //usun jak wciaz dziala bez nich
    public void aktualizujTabeleTabela(AjaxBehaviorEvent e) throws IOException {
        dokumentylista = Collections.synchronizedList(new ArrayList<>());
        dokumentyFiltered = null;
        gosciuwybral = null;
        aktualizuj();
        init();
        Msg.msg("i","Udana zamiana klienta. Aktualny klient to: " +wpisView.getPrintNazwa()+" okres rozliczeniowy: "+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu()+" opodatkowanie: "+wpisView.getRodzajopodatkowania());
    }
  
   
    private void aktualizuj(){
        wpisView.naniesDaneDoWpis();
    }
        
    
//    public void napraw(){
//        List<Dok> temp = dokDAO.findAll();
//        for(Dok p : temp){
//            List<EVatwpis1> ew = p.getEwidencjaVAT();
//            Double netto = 0.0;
//            for(EVatwpis1 w : ew){
//                netto = netto + w.getNetto();
//            }
//            p.setKwota(netto);
//            dokDAO.edit(p);
//        }
//    }

    
    public void fakturasprawdzanie(int l) {
        Dok w = gosciuwybral.get(0);
        w.setSprawdzony(l);
        int rowek = 0;
        for (Dok s : dokumentylista) {
            if (s!=w) {
                rowek++;
            } else {
                break;
            }
        }
        String p = "form:dokumentyLista:"+rowek+":polespr";
        PrimeFaces.current().ajax().update(p);
        dokDAO.edit(w);
    }
    
    public void fakturaoznaczanie() {
        Dok w = gosciuwybral.get(0);
        w.setSprawdzony(w.getSprawdzony()==0?1:w.getSprawdzony()==1?2:0);
        int rowek = 0;
        for (Dok s : dokumentylista) {
            if (s!=w) {
                rowek++;
            } else {
                break;
            }
        }
        String p = "form:dokumentyLista:"+rowek+":polespr";
        PrimeFaces.current().ajax().update(p);
        dokDAO.edit(w);
    }
    
    public void fakturasprawdzanieajax(Object i) {
    }
     
    
      public void aktywacjadeaktywacja(){
         for(Dok p : gosciuwybral){
             if(p.getUsunpozornie()==false){
                 p.setUsunpozornie(true);
             } else {
                 p.setUsunpozornie(false);
             }
             dokDAO.edit(p);
         }
         PrimeFaces.current().ajax().update("form:dokumentyLista");
      }
      //sprawdzic
       private void usunDokInwestycje(Dok dok) {
          try{
            String symbol = dok.getSymbolinwestycji();
            if(!symbol.equals("wybierz")){
                Inwestycje biezaca = null;
                for(Inwestycje p : inwestycje){
                    if(p.getSymbol().equals(symbol)){
                        biezaca = p;
                        break;
                    }
                }
                biezaca.setTotal(biezaca.getTotal()-dok.getNetto());
                List<Inwestycje.Sumazalata> sumazalata = biezaca.getSumazalata();
                for(Inwestycje.Sumazalata p : sumazalata){
                    if(p.getRok().equals(dok.getPkpirR())){
                        p.setKwota(p.getKwota()-dok.getNetto());
                        biezaca.setSumazalata(sumazalata);
                        break;
                    }
                }
                biezaca.getDoklist().remove(dok);
                inwestycjeDAO.edit(biezaca);
                Msg.msg("i","Usunąłem z inwestycji "+symbol,"dodWiad:mess_add");
                
            }
        } catch (Exception e) { E.e(e); 
          Msg.msg("e","Błąd nie usunąłem z inwestycji!","dodWiad:mess_add");
        }
            }
      
        /**
   * Usunąc jak już bedzie dobrze
   * @param actionEvent 
   */
	public void update(ActionEvent actionEvent) {
		Msg.msg("i","Data updated");
	}
	
	public void delete(ActionEvent actionEvent) {
		Msg.msg("i","Data deleted");
	}

      public void naprawiamdokumenty() {
          List<Dok> dok = dokDAO.zwrocRok("2014");
          for (Dok p : dok) {
              List<EVatwpis1> vatlist = p.getEwidencjaVAT1();
              if (vatlist != null) {
                double vatsuma = 0.0;
                for (EVatwpis1 r : vatlist) {
                    vatsuma += r.getVat();
                }
                p.setBrutto(Math.round((p.getNetto()+vatsuma) * 100.0) / 100.0);
                dokDAO.edit(p);
              }
          }
      }
      
       public void printPDFPK() {
         try {
            PdfPK.drukujPK(gosciuwybral, podatnikDAO, wpisView, uzDAO, umorzenieNDAO);
         } catch (Exception e) { E.e(e); 
             
         }
     }
       
       public void printPDFPKWydruk() {
         try {
             //PDFDirectPrint.silentPrintPdf(PdfPK.drukujPK(gosciuwybral, podatnikDAO, wpisView, uzDAO, amoDokDAO));
         } catch (Exception e) { E.e(e); 
             
         }
     }
    
    public String wysokosctabeli() {
              //FacesContext.getCurrentInstance().getExternalContext().get
        return "500px";
    }

    
    
    public void drukujdokumentyuproszczona() {
        if (dokumentyFiltered != null && dokumentyFiltered.size()>0) {
            PdfDok.drukujDok(dokumentyFiltered, wpisView,0, wybranacechadok, "dokuprsel");
        } else {
            PdfDok.drukujDok(dokumentylista, wpisView,0, wybranacechadok, "dokupr");
        }
    }
    
    
    public void dodajcechedodokumenty(Cechazapisu c) {
        if (gosciuwybral != null) {
            for (Dok p : gosciuwybral) {
                p .getCechadokumentuLista().add(c);
                //c.getDokLista().add(p);
            }
            dokDAO.editList(gosciuwybral);
            if (cechydokzlisty == null) {
                cechydokzlisty = new ArrayList();
            } else if (!cechydokzlisty.contains(c.getNazwacechy())) {
                cechydokzlisty.add(c.getNazwacechy());
            }
            Msg.msg("Nadano wybranym dokumentom żądaną cechę");
        }
    }
    public void usuncechedodokumenty(Cechazapisu c) {
        if (gosciuwybral != null) {
            for (Dok p : gosciuwybral) {
                p .getCechadokumentuLista().remove(c);
                //c.getDokLista().remove(p);
            }
            dokDAO.editList(gosciuwybral);
            cechydokzlisty.remove(c.getNazwacechy());
            wybranacechadok = null;
            init();
            Msg.msg("Usunięto wybranym dokumentom żądaną cechę");
        }
    }
      
    
        //<editor-fold defaultstate="collapsed" desc="comment">
        public List<Dok> getObiektDOKjsf() {
            return obiektDOKjsf;
        }
        
        public void setObiektDOKjsf(List<Dok> obiektDOKjsf) {
            this.obiektDOKjsf = obiektDOKjsf;
        }

    public String getWybranacechadok() {
        return wybranacechadok;
    }

    public void setWybranacechadok(String wybranacechadok) {
        this.wybranacechadok = wybranacechadok;
    }

    public List<Rodzajedok> getDokumentypodatnika() {
        return dokumentypodatnika;
    }

    public void setDokumentypodatnika(List<Rodzajedok> dokumentypodatnika) {
        this.dokumentypodatnika = dokumentypodatnika;
    }

  

    public List<String> getWalutywdokum() {
        return walutywdokum;
    }

    public void setWalutywdokum(List<String> walutywdokum) {
        this.walutywdokum = walutywdokum;
    }
        
        public List<Dok> getDokumentypobrane() {
            return dokumentypobrane;
        }
        
        public void setDokumentypobrane(List<Dok> dokumentypobrane) {
            this.dokumentypobrane = dokumentypobrane;
        }
        
        public List<Dok> getDokumentylista() {
            return dokumentylista;
        }
        
        public void setDokumentylista(List<Dok> dokumentylista) {
            this.dokumentylista = dokumentylista;
        }
        
        public WpisView getWpisView() {
            return wpisView;
        }
        
        public void setWpisView(WpisView wpisView) {
            this.wpisView = wpisView;
        }
        
        public DokDAO getDokDAO() {
            return dokDAO;
        }
        
        public void setDokDAO(DokDAO dokDAO) {
            this.dokDAO = dokDAO;
        }
        
        public Dok getSelDokument() {
            return selDokument;
        }
        
        public void setSelDokument(Dok selDokument) {
            this.selDokument = selDokument;
        }
        
        public Dok getDokdoUsuniecia() {
            return dokdoUsuniecia;
        }
        
        public void setDokdoUsuniecia(Dok dokdoUsuniecia) {
            dokdoUsuniecia = dokdoUsuniecia;
        }
        
        
        public List<Dok> getDokvatmc() {
            return dokvatmc;
        }
        
        public void setDokvatmc(List<Dok> dokvatmc) {
            this.dokvatmc = dokvatmc;
        }
        
              
        public boolean isButton() {
            return button;
        }
        
        public void setButton(boolean button) {
            this.button = button;
        }
        
        public Uz getUzytkownik() {
            return uzytkownik;
        }
        
        public void setUzytkownik(Uz uzytkownik) {
            this.uzytkownik = uzytkownik;
        }
        
        
        public AmoDokDAO getAmoDokDAO() {
            return amoDokDAO;
        }
        
        public void setAmoDokDAO(AmoDokDAO amoDokDAO) {
            this.amoDokDAO = amoDokDAO;
        }
        
        public List<Dok> getGosciuwybral() {
            return gosciuwybral;
        }
        
        public void setGosciuwybral(List<Dok> gosciuwybral) {
            this.gosciuwybral = gosciuwybral;
        }

    public List<String> getKontrahentypodatnika() {
        return kontrahentypodatnika;
    }

    public void setKontrahentypodatnika(List<String> kontrahentypodatnika) {
        this.kontrahentypodatnika = kontrahentypodatnika;
    }
        
      
        
              
        public List<Dok> getDokumentyokresowe() {
            return dokumentyokresowe;
        }
        
        public void setDokumentyokresowe(List<Dok> dokumentyokresowe) {
            this.dokumentyokresowe = dokumentyokresowe;
        }

    public List getCechydokzlisty() {
        return cechydokzlisty;
    }

    public void setCechydokzlisty(List cechydokzlisty) {
        this.cechydokzlisty = cechydokzlisty;
    }

    public double getSumanetto() {
        return sumanetto;
    }

    public void setSumanetto(double sumanetto) {
        this.sumanetto = sumanetto;
    }

    public double getSumavat() {
        return sumavat;
    }

    public void setSumavat(double sumavat) {
        this.sumavat = sumavat;
    }

    public double getSumabrutto() {
        return sumabrutto;
    }

    public void setSumabrutto(double sumabrutto) {
        this.sumabrutto = sumabrutto;
    }
    public boolean isSumuj() {
        return sumuj;
    }

    public void setSumuj(boolean sumuj) {
        this.sumuj = sumuj;
    }

    public boolean isDodajdlajpk() {
        return dodajdlajpk;
    }

    public void setDodajdlajpk(boolean dodajdlajpk) {
        this.dodajdlajpk = dodajdlajpk;
    }

        public List<Dok> getDokumentyFiltered() {
            return dokumentyFiltered;
        }

        public void setDokumentyFiltered(List<Dok> dokumentyFiltered) {
            this.dokumentyFiltered = dokumentyFiltered;
        }
    
        
    //</editor-fold>

    private void sumujdokumentydodane(Dok tmpx) {
        try {
            sumanetto = sumanetto + tmpx.getNetto();
            sumabrutto = sumabrutto + tmpx.getBrutto();
            if (tmpx.getBrutto() != 0.0) {
                sumavat = sumavat + (tmpx.getBrutto()-tmpx.getNetto());
            }
        } catch (Exception ex){}
    }

    public void sumujwybrane() {
        sumanetto = 0.0;
        sumavat = 0.0;
        sumabrutto = 0.0;
        if (dokumentyFiltered != null) {
            for (Dok p : dokumentyFiltered) {
                sumujdokumentydodane(p);
            }
        } else {
            for (Dok p : dokumentylista) {
                sumujdokumentydodane(p);
            }
        }
    }

   
        
}
