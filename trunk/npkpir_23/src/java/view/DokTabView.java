/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokcomparator;
import dao.AmoDokDAO;
import dao.DokDAO;
import dao.InwestycjeDAO;
import dao.STRDAO;
import dao.StornoDokDAO;
import dao.UzDAO;
import dao.WpisDAO;
import embeddable.EVatwpis;
import embeddable.Stornodoch;
import entity.Amodok;
import entity.Dok;
import entity.Inwestycje;
import entity.Podatnik;
import entity.StornoDok;
import entity.Uz;
import entity.Wpis;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
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
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Osito
 */
@ManagedBean(name = "DokTabView")
@ViewScoped
public class DokTabView implements Serializable {
    //wybranedokumenty do druku
    private static List<Dok> gosciuwybral;
    //wybranedokumenty do usuniecia
    private static List<Dok> grupausun;
    private static boolean pokaztablice;
    //wartosc przefiltrowana
    private static List<Dok> filteredValue;
    private static Dok dokdoUsuniecia;
    private static final List frozenrows;
    static {
        frozenrows = new ArrayList<>();
        frozenrows.add("lolo");
        frozenrows.add("manolo");
    }

    public static List<Dok> getGosciuwybralS() {
        return gosciuwybral;
    }
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
    //dokumenty niezaplacone
    private List<Dok> niezaplacone;
    //dokumenty zaplacone
    private List<Dok> zaplacone;
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
        //dokumenty podatnika
        obiektDOKjsfSel = new ArrayList<>();
        //dokumenty podatnika z miesiaca
        obiektDOKmrjsfSel = new ArrayList<>();
        //dekumenty o tym samym okresie vat
        dokvatmc = new ArrayList<>();
        //dokumenty niezaplacone
        niezaplacone = new ArrayList<>();
        //dokumenty zaplacone
        zaplacone = new ArrayList<>();
        //dokumenty okresowe
        dokumentyokresowe = new ArrayList<>();
        gosciuwybral = new ArrayList<>();
        filteredValue = new ArrayList<>();
       
    }
          

    public List getFrozenrows() {
        return frozenrows;
    }

    
    @PostConstruct
    public void init() {
            try {
                inwestycje = inwestycjeDAO.findInwestycje(wpisView.getPodatnikWpisu(), false);
            } catch (Exception e){}
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
            } catch (Exception e){
                System.out.println("Brak numeru pkpir wprowadzonego w trakcie roku");
            }
            }
            try {
                obiektDOKjsfSel.addAll(dokDAO.zwrocBiezacegoKlientaRokMC(podatnik, rok.toString(),mc));
                //sortowanie dokumentów
                    Collections.sort(obiektDOKjsfSel, new Dokcomparator());
            } catch (Exception e) {
            }
            numerkolejny = dokDAO.liczdokumenty(rok.toString(), mc, podatnik)+1;
            obiektDOKmrjsfSel.clear();
            for(Dok tmpx : obiektDOKjsfSel){
                   tmpx.setNrWpkpir(numerkolejny++);
                   if (tmpx.getRozliczony() == false) {
                        niezaplacone.add(tmpx);
                    } else {
                        //pobiera tylko przelewowe
                        if (tmpx.getRozrachunki() != null) {
                            zaplacone.add(tmpx);
                        }
                    }
                    if (tmpx.getPkpirM().equals(mc)) {
                        obiektDOKmrjsfSel.add(tmpx);
                    }
                }
            }
    
//    
//    public void edit(RowEditEvent ex) {
//        try {
//            //sformatuj();
//            dokDAO.edit(ex.getObject());
//            Msg.msg("i",  "Nowy dokument wyedytowany i zachowany.","form:messages");
//        } catch (Exception e) {
//            System.out.println(e.toString());
//            Msg.msg("e",  "Wystąpił błąd. Dokument nie zachowany po  edycji.","form:messages");
//        }
//    }
    
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
        if ((sprawdzczyniemarozrachunkow(dokdoUsuniecia) == true)) {
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
            } catch (Exception e){
            }
            try {
                dokDAO.destroy(dokdoUsuniecia);
                obiektDOKjsfSel.remove(dokdoUsuniecia);
                obiektDOKmrjsfSel.remove(dokdoUsuniecia);
                dokumentyFiltered.remove(dokdoUsuniecia);
            } catch (Exception e) {
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
        if ((sprawdzczyniemarozrachunkow(dokdoUsuniecia) == true)) {
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
            } catch (Exception e) {
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

    private boolean sprawdzczyniemarozrachunkow(Dok dok) {
        ArrayList<Stornodoch> temp = new ArrayList<>();
        try {
            temp = dok.getStorno();
            if (temp.size() > 0) {
                return true;
            } else {
                return false;
            }

        } catch (Exception e) {
            return false;
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
  
     public void aktualizujGuest(String strona) throws IOException {
        aktualizujGuest();
        aktualizuj();
        init();
        //FacesContext.getCurrentInstance().getExternalContext().redirect(strona);
    }

  

    public void aktualizujNiezaplacone(AjaxBehaviorEvent e) throws IOException {
        RequestContext.getCurrentInstance().update("form:dokumentyLista");
        RequestContext.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
        RequestContext.getCurrentInstance().update("form:labelstorno");
        Integer rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            StornoDok tmp = stornoDokDAO.find(rok, mc, podatnik);
            setButton(false);
            FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaNiezaplacone.xhtml");
        } catch (Exception ef) {
            setButton(true);
            FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaNiezaplacone.xhtml");
        }

    }
    
     public void aktualizujNiezaplaconeGuest(AjaxBehaviorEvent e) throws IOException {
        RequestContext.getCurrentInstance().update("form:dokumentyLista");
        RequestContext.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
        RequestContext.getCurrentInstance().update("form:labelstorno");
   }

    
    public void aktualizujTablica(AjaxBehaviorEvent e) {
        aktualizuj();
        RequestContext.getCurrentInstance().update("formX:dokumentyLista");
        RequestContext.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
    }
    
     public void aktualizujPIT(AjaxBehaviorEvent e) {
        aktualizuj();
        Msg.msg("i", "Zmieniono miesiąc obrachunkowy.");
    }
    
    
     public void aktualizujTablicaGuest(AjaxBehaviorEvent e) {
        aktualizujGuest();
        RequestContext.getCurrentInstance().update("form:dokumentyLista");
        RequestContext.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
    }

    public void aktualizujWestWpisWidok(AjaxBehaviorEvent e) throws IOException {
        RequestContext ctx = null;
        RequestContext.getCurrentInstance().update("dodWiad:panelDodawaniaDokumentu");
        RequestContext.getCurrentInstance().update("westWpis:westWpisWidok");
        
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
     private void aktualizujGuest(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpisDAO.edit(wpistmp);
    }
    
//    public void napraw(){
//        List<Dok> temp = dokDAO.findAll();
//        for(Dok p : temp){
//            List<EVatwpis> ew = p.getEwidencjaVAT();
//            Double netto = 0.0;
//            for(EVatwpis w : ew){
//                netto = netto + w.getNetto();
//            }
//            p.setKwota(netto);
//            dokDAO.edit(p);
//        }
//    }

   
    
    public void usunzzaplaconych(RowEditEvent ex){
        Dok tmp = (Dok) ex.getObject();
        Msg.msg("i","Probuje zmienić rozliczenia: "+tmp.getOpis(),"form:messages");
        try{
            //jak bedzie storno to ma wyrzuci blad. trzeba usunac strono wpierw
            try {
            tmp.getStorno();
                throw new Exception();
            } catch (Exception s){}
            dodajdatydlaStorno(tmp);
            tmp.setRozliczony(false);
            tmp.setRozrachunki(null);
            tmp.setStorno(null);
            dokdoUsuniecia = tmp;
            destroy2roz();
            dokDAO.dodaj(tmp);
            zaplacone.remove(tmp);
            RequestContext.getCurrentInstance().update("form:dokumentyLista");
            Msg.msg("i","Dokument z nowymi datami zaksięgowany: "+tmp.getOpis(),"form:messages");
        } catch (Exception e){
            Msg.msg("e","Nie udało się usunąć rozliczeń: "+tmp.getOpis()+"Sprawdź obecność storno.","form:messages");
        }
    }
    
    
     public void dodajdatydlaStorno(Dok tmpDok) throws ParseException{
        String data;
            switch (wpisView.getMiesiacWpisu()) {
                case "01":
                case "03":
                case "05":
                case "07":
                case "08":
                case "10":
                case "12":
                    data = wpisView.getRokWpisu().toString()+"-"+wpisView.getMiesiacWpisu()+"-31";
                    break;
                case "02":
                    data = wpisView.getRokWpisu().toString()+"-"+wpisView.getMiesiacWpisu()+"-28";
                    break;
                default:
                    data = wpisView.getRokWpisu().toString()+"-"+wpisView.getMiesiacWpisu()+"-30";
                    break;
            }
        String dataWyst = tmpDok.getDataWyst();
        String dataPlat = tmpDok.getTerminPlatnosci();
        Calendar c = Calendar.getInstance();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datawystawienia = formatter.parse(dataWyst);
        Date terminplatnosci = formatter.parse(dataPlat);
        Date dataujeciawkosztach = formatter.parse(data);
       if(roznicaDni(datawystawienia,terminplatnosci)==true){
         c.setTime(terminplatnosci);
         c.add(Calendar.DAY_OF_MONTH, 30);
         String nd30 = formatter.format(c.getTime());
         tmpDok.setTermin30(nd30);
         tmpDok.setTermin90("");
        } else {
          c.setTime(dataujeciawkosztach);
          c.add(Calendar.DAY_OF_MONTH, 90);
          String nd90 = formatter.format(c.getTime());
          tmpDok.setTermin90(nd90);
          tmpDok.setTermin30("");
         }
        c.setTime(terminplatnosci);
        c.add(Calendar.DAY_OF_MONTH, 150);
        String nd150 = formatter.format(c.getTime());
        tmpDok.setTermin150(nd150);
    }
     
      private boolean roznicaDni(Date date_od, Date date_do){ 
                long x=date_do.getTime(); 
                long y=date_od.getTime(); 
                long wynik=Math.abs(x-y); 
                wynik=wynik/(1000*60*60*24); 
                if(wynik<=61){
                    return true;
                } else {
                    return false;
                }
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
        } catch (Exception e){
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
              List<EVatwpis> vatlist = p.getEwidencjaVAT();
              if (vatlist != null) {
                double vatsuma = 0.0;
                for (EVatwpis r : vatlist) {
                    vatsuma += r.getVat();
                }
                p.setBrutto(Math.round((p.getNetto()+vatsuma) * 100.0) / 100.0);
                dokDAO.edit(p);
              }
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
            DokTabView.dokdoUsuniecia = dokdoUsuniecia;
        }
        
        
        public List<Dok> getDokvatmc() {
            return dokvatmc;
        }
        
        public void setDokvatmc(List<Dok> dokvatmc) {
            this.dokvatmc = dokvatmc;
        }
        
        public List<Dok> getNiezaplacone() {
            return niezaplacone;
        }
        
        public void setNiezaplacone(List<Dok> niezaplacone) {
            this.niezaplacone = niezaplacone;
        }
        
        public List<Dok> getZaplacone() {
            return zaplacone;
        }
        
        public void setZaplacone(List<Dok> zaplacone) {
            this.zaplacone = zaplacone;
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
            DokTabView.gosciuwybral = gosciuwybral;
        }
        
        public boolean isPokaztablice() {
            return pokaztablice;
        }
        
        public void setPokaztablice(boolean pokaztablice) {
            DokTabView.pokaztablice = pokaztablice;
        }
        
        public List<Dok> getFilteredValue() {
            return filteredValue;
        }
        
        public void setFilteredValue(List<Dok> filteredValue) {
            DokTabView.filteredValue = filteredValue;
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
