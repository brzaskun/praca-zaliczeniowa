/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.Rozrachunki;
import comparator.Dokcomparator;
import dao.AmoDokDAO;
import dao.DokDAO;
import dao.InwestycjeDAO;
import dao.PodatnikDAO;
import dao.STRDAO;
import dao.StornoDokDAO;
import dao.UzDAO;
import dao.WpisDAO;
import entity.Amodok;
import entity.Dok;
import entity.EVatwpis1;
import entity.Inwestycje;
import entity.Podatnik;
import entity.StornoDok;
import entity.Uz;
import entity.Wpis;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import msg.Msg;
import org.primefaces.context.RequestContext;
import pdf.PDFDirectPrint;
import pdf.PdfPK;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "DokTabView")
@ViewScoped
public class DokTabView implements Serializable {
    //wybranedokumentyDoDruku
    private List<Dok> gosciuwybral;
    //wybranedokumenty do usuniecia
    private List<Dok> grupausun;
    private Dok dokdoUsuniecia;
    @Inject
    private PodatnikDAO podatnikDAO;
    //tablica obiektów
    private List<Dok> obiektDOKjsf;
    //tablica obiektw danego klienta
    private List<Dok> obiektDOKjsfSel;
    //tablica obiektów danego klienta z określonego roku i miesiąca
    private List<Dok> obiektDOKmrjsfSel;
    //tablica obiektów danego klienta z określonego roku i miesiąca
    private List<Dok> dokumentyFiltered;
    //dokumenty o tym samym okresie vat
    private List<Dok> dokvatmc;
    
    //dokumenty okresowe
    private List<Dok> dokumentyokresowe;
    
    /*pkpir*/
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject private DokDAO dokDAO;
    @Inject private Dok selDokument;
    @Inject private StornoDokDAO stornoDokDAO;
    @Inject private STRDAO sTRDAO;
    private boolean button;
    @Inject private Uz uzytkownik;
    @Inject private UzDAO uzDAO;
    @Inject private WpisDAO wpisDAO;
    @Inject private AmoDokDAO amoDokDAO;
    
     private List<Inwestycje> inwestycje;
     @Inject private InwestycjeDAO inwestycjeDAO;

    public DokTabView() {
       inicjalizacjalist();
    }
    
    private void inicjalizacjalist() {
         //dokumenty podatnika
        obiektDOKjsfSel = new ArrayList<>();
        //dokumenty podatnika z miesiaca
        obiektDOKmrjsfSel = new ArrayList<>();
        //dekumenty o tym samym okresie vat
        dokvatmc = new ArrayList<>();
        //dokumenty okresowe
        dokumentyokresowe = new ArrayList<>();
        gosciuwybral = new ArrayList<>();
    }

   
    
    @PostConstruct
    public void init() {
        inicjalizacjalist();
            try {
                inwestycje = inwestycjeDAO.findInwestycje(wpisView.getPodatnikWpisu(), false);
            } catch (Exception e) { E.e(e); }
            Integer rok = wpisView.getRokWpisu();
            String mc = wpisView.getMiesiacWpisu();
            String podatnik = wpisView.getPodatnikWpisu();
            Podatnik pod = wpisView.getPodatnikObiekt();
            uzytkownik = wpisView.getWprowadzil();
            try {
                StornoDok tmp = stornoDokDAO.find(rok, mc, podatnik);
                setButton(false);
            } catch (Exception ef) {
                setButton(true);
            }
            int numerkolejny = 1;
            if(wpisView.getPodatnikObiekt().getNumerpkpir()!=null){
            try{
            //zmienia numer gdy srodek roku
             int index = wpisView.getPodatnikObiekt().getNumerpkpir().size()-1;
             String wartosc = wpisView.getPodatnikObiekt().getNumerpkpir().get(index).getParametr();
             numerkolejny = Integer.parseInt(wartosc);
            } catch (Exception e) { 
                System.out.println("Blad " + e.toString()); 
                System.out.println("Brak numeru pkpir wprowadzonego w trakcie roku");
            }
            }
            try {
                obiektDOKjsfSel.addAll(dokDAO.zwrocBiezacegoKlientaRokMC(podatnik, rok.toString(),mc));
                //sortowanie dokumentów
                    Collections.sort(obiektDOKjsfSel, new Dokcomparator());
            } catch (Exception e) { E.e(e); 
            }
            numerkolejny = dokDAO.liczdokumenty(rok.toString(), mc, podatnik)+1;
            obiektDOKmrjsfSel.clear();
            for(Dok tmpx : obiektDOKjsfSel){
                   tmpx.setNrWpkpir(numerkolejny++);
                    if (tmpx.getPkpirM().equals(mc)) {
                        obiektDOKmrjsfSel.add(tmpx);
                    }
                }
            }
    

    
     public void sprawdzCzyNieDuplikat() {
        Msg.msg("i", "Rozpoczynam badanie bazy klienta "+wpisView.getPodatnikWpisu()+" na obecność duplikatów");
        List<Dok> pobranedokumentypodatnika = dokDAO.zwrocBiezacegoKlientaDuplikat(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
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
                String wiadomosc = "Dokument typu "+badany.getTypdokumentu()+" dla tego klienta, o numerze "+badany.getNrWlDk()+" i kwocie netto "+badany.getNetto()+" jest juz zaksiegowany u podatnika: " + badany.getPodatnik();
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
        grupausun = new ArrayList<>();
        grupausun = gosciuwybral;
    }

     public void destroygrupa2() {
          for(Dok p : grupausun){
              dokdoUsuniecia = p;
              destroy2();
          }
      }
    public void destroy2() {
        if(dokdoUsuniecia.getStatus().equals("bufor")){
        String temp = dokdoUsuniecia.getTypdokumentu();
        if ((Rozrachunki.sprawdzczyniemarozrachunkow(dokdoUsuniecia) == true)) {
            Msg.msg("e",  "Dokument nie usunięty - Usuń wpierw dokument strono, proszę "+dokdoUsuniecia.getIdDok().toString(),"form:messages");
        } else if (sprawdzczytoniesrodek(dokdoUsuniecia) == true) {
            Msg.msg("e",  "Dokument nie usunięty - Usuń wpierw środek z ewidencji "+dokdoUsuniecia.getIdDok().toString(),"form:messages");
        } else {
            if(dokdoUsuniecia.getTypdokumentu().equals("AMO")){
                //poszukiwanie czy nie ma po nim jakiegos
                Amodok amotmpnas = new Amodok();
                if(!"12".equals(wpisView.getMiesiacWpisu())){
                    amotmpnas = amoDokDAO.findMR(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), wpisView.getMiesiacNastepny());
                }
                if(amotmpnas.getZaksiegowane()&&(!amotmpnas.getUmorzenia().isEmpty())){
                   Msg.msg("e", "Nie można usunąć dokumentu AMO. Usuń najpierw ten z następnego miesiąca!", "form:messages"); 
                   return;
                }
                Amodok amotmp = amoDokDAO.findMR(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                amotmp.setZaksiegowane(false);
                amoDokDAO.edit(amotmp);
            }
            try {
                String probsymbolu = dokdoUsuniecia.getSymbolinwestycji();
                if(!probsymbolu.equals("wybierz")&&(!probsymbolu.isEmpty())){
                    usunDokInwestycje(dokdoUsuniecia);
                }
            } catch (Exception e) { E.e(e); 
            }
            try {
                dokDAO.destroy(dokdoUsuniecia);
                obiektDOKjsfSel.remove(dokdoUsuniecia);
                obiektDOKmrjsfSel.remove(dokdoUsuniecia);
                dokumentyFiltered.remove(dokdoUsuniecia);
            } catch (Exception e) { E.e(e); 
            }
            Msg.msg("i", "Dokument usunięty " + dokdoUsuniecia.getIdDok().toString(), "form:messages");
        }
    } else {
            Msg.msg("e","Dokument w księgach, nie można usunąć ","form:messages");
        }
    }
    
     public void destroy2roz() throws Exception {
        if(dokdoUsuniecia.getStatus().equals("bufor")){
        String temp = dokdoUsuniecia.getTypdokumentu();
        if ((Rozrachunki.sprawdzczyniemarozrachunkow(dokdoUsuniecia) == true)) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dokument nie usunięty - Usuń wpierw dokument strono, proszę", dokdoUsuniecia.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            throw new Exception();
        } else if (sprawdzczytoniesrodek(dokdoUsuniecia) == true) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dokument nie usunięty - Usuń wpierw środek z ewidencji", dokdoUsuniecia.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            throw new Exception();
        } else {
            dokdoUsuniecia.getTypdokumentu().equals("OT");
            try {
                dokDAO.destroy(dokdoUsuniecia);
                obiektDOKjsfSel.remove(dokdoUsuniecia);
                obiektDOKmrjsfSel.remove(dokdoUsuniecia);
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
        return sTRDAO.findSTR(dok.getPodatnik(), dok.getNetto(), dok.getNrWlDk());
    }

    //usun jak wciaz dziala bez nich
    public void aktualizujTabele(AjaxBehaviorEvent e) throws IOException {
        aktualizuj();
        Msg.msg("i","Udana zamiana klienta. Aktualny klient to: " +wpisView.getPodatnikWpisu()+" okres rozliczeniowy: "+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu(),"form:messages");
    }
    
    //usun jak wciaz dziala bez nich
    public void aktualizujTabeleTabela(AjaxBehaviorEvent e) throws IOException {
        obiektDOKmrjsfSel.clear();
        aktualizuj();
        init();
        Msg.msg("i","Udana zamiana klienta. Aktualny klient to: " +wpisView.getPodatnikWpisu()+" okres rozliczeniowy: "+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu(),"form:messages");
    }
  
   
    private void aktualizuj(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpistmp.setPodatnikWpisu(wpisView.getPodatnikWpisu());
        wpisDAO.edit(wpistmp);
        wpisView.findWpis();
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

       
    
     
     
    
      public void aktywacjadeaktywacja(){
         for(Dok p : gosciuwybral){
             if(p.getUsunpozornie()==false){
                 p.setUsunpozornie(true);
             } else {
                 p.setUsunpozornie(false);
             }
             dokDAO.edit(p);
         }
         RequestContext.getCurrentInstance().update("form:dokumentyLista");
      }
      
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
                biezaca.getDokumenty().remove(dok);
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
		Msg.msg("i","Data updated","form:messages");
	}
	
	public void delete(ActionEvent actionEvent) {
		Msg.msg("i","Data deleted","form:messages");
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
            PdfPK.drukujPK(gosciuwybral, podatnikDAO, wpisView, uzDAO, amoDokDAO);
         } catch (Exception e) { E.e(e); 
             
         }
     }
       
       public void printPDFPKWydruk() {
         try {
             PDFDirectPrint.silentPrintPdf(PdfPK.drukujPK(gosciuwybral, podatnikDAO, wpisView, uzDAO, amoDokDAO));
         } catch (Exception e) { E.e(e); 
             
         }
     }
    
    public String wysokosctabeli() {
              //FacesContext.getCurrentInstance().getExternalContext().get
        return "500px";
    }

      
      
    
        //<editor-fold defaultstate="collapsed" desc="comment">
        public List<Dok> getObiektDOKjsf() {
            return obiektDOKjsf;
        }
        
        public void setObiektDOKjsf(List<Dok> obiektDOKjsf) {
            this.obiektDOKjsf = obiektDOKjsf;
        }
        
        public List<Dok> getObiektDOKjsfSel() {
            return obiektDOKjsfSel;
        }
        
        public void setObiektDOKjsfSel(List<Dok> obiektDOKjsfSel) {
            this.obiektDOKjsfSel = obiektDOKjsfSel;
        }
        
        public List<Dok> getObiektDOKmrjsfSel() {
            return obiektDOKmrjsfSel;
        }
        
        public void setObiektDOKmrjsfSel(List<Dok> obiektDOKmrjsfSel) {
            this.obiektDOKmrjsfSel = obiektDOKmrjsfSel;
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
        
      
        
              
        public List<Dok> getDokumentyokresowe() {
            return dokumentyokresowe;
        }
        
        public void setDokumentyokresowe(List<Dok> dokumentyokresowe) {
            this.dokumentyokresowe = dokumentyokresowe;
        }

        public List<Dok> getDokumentyFiltered() {
            return dokumentyFiltered;
        }

        public void setDokumentyFiltered(List<Dok> dokumentyFiltered) {
            this.dokumentyFiltered = dokumentyFiltered;
        }
    
        
    //</editor-fold>

   
        
}
