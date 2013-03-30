/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokcomparator;
import dao.AmoDokDAO;
import dao.DokDAO;
import dao.STRDAO;
import dao.StornoDokDAO;
import dao.UzDAO;
import dao.WpisDAO;
import embeddable.Mce;
import embeddable.Stornodoch;
import entity.Amodok;
import entity.Dok;
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
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
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
@RequestScoped
public class DokTabView implements Serializable {
    //tablica obiektów

    private List<Dok> obiektDOKjsf;
    //tablica obiektw danego klienta
    private List<Dok> obiektDOKjsfSel;
    //tablica obiektw danego klienta
    private List<Dok> obiektDOKjsfSelRok;
    //tablica obiektów danego klienta z określonego roku i miesiąca
    private List<Dok> obiektDOKmrjsfSel;
   
    //dokumenty o tym samym okresie vat
    private List<Dok> dokvatmc;
    //dokumenty niezaplacone
    private List<Dok> niezaplacone;
    //dokumenty zaplacone
    private List<Dok> zaplacone;
   
    
    /*pkpir*/
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject private DokDAO dokDAO;
    @Inject private Dok selDokument;
    private static Dok dokdoUsuniecia;
    @Inject private StornoDokDAO stornoDokDAO;
    @Inject private STRDAO sTRDAO;
    private boolean button;
    @Inject private Uz uzytkownik;
    @Inject private UzDAO uzDAO;
    @Inject private WpisDAO wpisDAO;
    @Inject private AmoDokDAO amoDokDAO;

    public DokTabView() {
        //dokumenty podatnika
        obiektDOKjsfSel = new ArrayList<>();
        //dokumenty podatnika z roku
        obiektDOKjsfSelRok = new ArrayList<>();
        //dokumenty podatnika z miesiaca
        obiektDOKmrjsfSel = new ArrayList<>();
        //dekumenty o tym samym okresie vat
        dokvatmc = new ArrayList<>();
        //dokumenty niezaplacone
        niezaplacone = new ArrayList<>();
        //dokumenty zaplacone
        zaplacone = new ArrayList<>();
        
    }

    @PostConstruct
    public void init() {
            Integer rok = wpisView.getRokWpisu();
            String mc = wpisView.getMiesiacWpisu();
            String podatnik = wpisView.getPodatnikWpisu();
            Podatnik pod = wpisView.getPodatnikObiekt();
            uzytkownik = wpisView.getWprowadzil();
            try {
                StornoDok tmp = stornoDokDAO.find(rok, mc, podatnik);
                setButton(false);
            } catch (Exception ef) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + ef.toString());
                setButton(true);
            }
            try {
                obiektDOKjsfSel.addAll(dokDAO.zwrocBiezacegoKlienta(wpisView.getPodatnikWpisu()));
                //sortowanie dokumentów
                    Collections.sort(obiektDOKjsfSel, new Dokcomparator());
                //
                int numerkolejny = 1;
                for(Dok p : obiektDOKjsfSel){
                    p.setNrWpkpir(numerkolejny++);
                }
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            String m = wpisView.getMiesiacWpisu();
            Integer m1 = Integer.parseInt(m);
            String mn = Mce.getMapamcy().get(m1);
            Integer r = wpisView.getRokWpisu();
            obiektDOKmrjsfSel.clear();
            int numerkolejny = 1;
            for(Dok tmpx : obiektDOKjsfSel){
                if (tmpx.getPkpirR().equals(r.toString())) {
                    tmpx.setNrWpkpir(numerkolejny++);
                    obiektDOKjsfSelRok.add(tmpx);
                    if (tmpx.getRozliczony() == false) {
                        niezaplacone.add(tmpx);
                    } else {
                        //pobiera tylko przelewowe
                        if (tmpx.getRozrachunki() != null) {
                            zaplacone.add(tmpx);
                        }
                    }
                    if (tmpx.getPkpirM().equals(m)) {
                        obiektDOKmrjsfSel.add(tmpx);
                    }
                    if (tmpx.getVatM().equals(mn)) {
                        dokvatmc.add(tmpx);
                    }
                    
                }
            }
        }
    
    
    
    
    public void edit(RowEditEvent ex) {
        try {
            //sformatuj();
            dokDAO.edit(selDokument);
            FacesMessage msg = new FacesMessage("Nowy dokytkownik edytowany " + ex.getObject().toString(), selDokument.getIdDok().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            System.out.println(e.getStackTrace().toString());
            FacesMessage msg = new FacesMessage("Dokytkownik nie zedytowany", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void destroy(Dok selDok) {
        dokdoUsuniecia = new Dok();
        dokdoUsuniecia = selDok;
    }

    public void destroy2() {
        if(dokdoUsuniecia.getStatus().equals("bufor")){
        String temp = dokdoUsuniecia.getTypdokumentu();
        if ((sprawdzczyniemarozrachunkow(dokdoUsuniecia) == true)) {
            Msg.msg("e",  "Dokument nie usunięty - Usuń wpierw dokument strono, proszę "+dokdoUsuniecia.getIdDok().toString(),"form:messages");
        } else if (sprawdzczytoniesrodek(dokdoUsuniecia) == true) {
            Msg.msg("e",  "Dokument nie usunięty - Usuń wpierw środek z ewidencji "+dokdoUsuniecia.getIdDok().toString(),"form:messages");
        } else {
            if(!dokdoUsuniecia.getTypdokumentu().equals("OT")){
                Amodok amotmp = amoDokDAO.findMR(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                amotmp.setZaksiegowane(false);
                amoDokDAO.edit(amotmp);
            }
            try {
                obiektDOKjsfSel.remove(dokdoUsuniecia);
                obiektDOKmrjsfSel.remove(dokdoUsuniecia);
                dokDAO.destroy(dokdoUsuniecia);
            } catch (Exception e) {
                System.out.println("Nie usnieto " + dokdoUsuniecia.getIdDok() + " " + e.toString());
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
                obiektDOKjsfSel.remove(dokdoUsuniecia);
                obiektDOKmrjsfSel.remove(dokdoUsuniecia);
                dokDAO.destroy(dokdoUsuniecia);
            } catch (Exception e) {
                System.out.println("Nie usnieto " + dokdoUsuniecia.getIdDok() + " " + e.toString());
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
    }
    
  
     public void aktualizujGuest(String strona) throws IOException {
        aktualizujGuest();
        FacesContext.getCurrentInstance().getExternalContext().redirect(strona);
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
            System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + ef.toString());
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
    
     public void aktualizujTablicaGuest(AjaxBehaviorEvent e) {
        aktualizujGuest();
        RequestContext.getCurrentInstance().update("form:dokumentyLista");
        RequestContext.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
    }

    public void aktualizujWestWpisWidok(AjaxBehaviorEvent e) {
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
        wpistmp.setPodatnikWpisu(wpisView.getPodatnikWpisu());
        wpisDAO.edit(wpistmp);
    }
     private void aktualizujGuest(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
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
        Date datawystawienia = (Date) formatter.parse(dataWyst);
        Date terminplatnosci = (Date) formatter.parse(dataPlat);
        Date dataujeciawkosztach = (Date) formatter.parse(data);
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
                System.out.println("Roznica miedzy datami to "+wynik+" dni..."); 
                if(wynik<=61){
                    return true;
                } else {
                    return false;
                }
     }
    
    
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

    public List<Dok> getObiektDOKjsfSelRok() {
        return obiektDOKjsfSelRok;
    }

    public void setObiektDOKjsfSelRok(List<Dok> obiektDOKjsfSelRok) {
        this.obiektDOKjsfSelRok = obiektDOKjsfSelRok;
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

    public AmoDokDAO getAmoDokDAO() {
        return amoDokDAO;
    }

    public void setAmoDokDAO(AmoDokDAO amoDokDAO) {
        this.amoDokDAO = amoDokDAO;
    }

   
        
}
