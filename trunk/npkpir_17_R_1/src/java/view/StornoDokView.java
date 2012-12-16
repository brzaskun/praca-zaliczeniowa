/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import embeddable.Mce;
import embeddable.Rozrachunek;
import entity.Dok;
import entity.StornoDok;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScope
public class StornoDokView implements Serializable {
    @Inject private StornoDok stornoDok;
    private ArrayList<StornoDok> lista;
    private ArrayList<Dok> pobraneDok;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject private DokDAO dokDAO;
    private static String stornonadzien;
    private Double wyst;

    public StornoDokView() {
        lista = new ArrayList<>();
        pobraneDok = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
        ArrayList<Dok> tmplist = new ArrayList<>();
        if (wpisView.getPodatnikWpisu() != null) {
            try {
                tmplist.addAll(dokDAO.zwrocBiezacegoKlienta(wpisView.getPodatnikWpisu()));
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            Integer r = wpisView.getRokWpisu();
            Iterator itx;
            itx = tmplist.iterator();
            while (itx.hasNext()) {
                Dok tmpx = (Dok) itx.next();
                if (tmpx.getPkpirR().equals(r.toString())&&tmpx.getRozliczony() == false) {
                        pobraneDok.add(tmpx);
                }
            }
        }
    }
    
    public void stornodokumentow() throws ParseException{
        ustaldzienmiesiaca();
        String termin;
        Double wystornowac;
        Iterator it;
        it = pobraneDok.iterator();
        while(it.hasNext()){
            Dok tmp = (Dok) it.next();
            if(tmp.getTermin30()!=null){
                termin = tmp.getTermin30();
            } else {
                termin = tmp.getTermin90();
            }
            if(roznicaDni(termin,stornonadzien)>0){
                System.out.println("trzeba stornowac " + roznicaDni(termin,stornonadzien));
                ArrayList<Rozrachunek> rozrachunki  = new ArrayList<>();
                try {
                    rozrachunki.addAll(tmp.getRozrachunki());
                } catch (Exception e) {}
                if(rozrachunki.isEmpty()){
                    wyst = tmp.getNetto();
                } else {
                    wyst = rozrachunki.get((rozrachunki.size()-1)).getDorozliczenia();
                }
                ArrayList<Rozrachunek> wystornowane = new ArrayList<>();
                try {
                    wystornowane.addAll(tmp.getStorno());
                } catch (Exception e) {}
                if(wystornowane.size()==0){
                    //jezeli nie bylo storna to wyksieguj
                    ArrayList<Rozrachunek> storno = new ArrayList<>();
                    storno.add(new Rozrachunek(stornonadzien, wyst, -wyst ));
                    tmp.setStorno(storno);
                    dokDAO.edit(tmp);
                    System.out.println("Udalo sie");
                } else {
                    //jezeli juz bylo storno to sprawdz czy nie ma drugiej wplaty i ew. doksieguj in plus
                }
                }
            }
        }
        
    
    
    public void ustaldzienmiesiaca(){
         Calendar calendar = Calendar.getInstance();
         String mc = wpisView.getMiesiacWpisu();
         Integer rok = wpisView.getRokWpisu();
         Integer mcCalendar = Mce.getMapamcyCalendar().get(mc);
         calendar.set(rok, mcCalendar,1);
         Integer lastDate = calendar.getActualMaximum(Calendar.DATE);
         calendar.set(Calendar.DATE, lastDate);  
         System.out.println("Last Date: " + calendar.getTime());
         stornonadzien = rok.toString().concat("-").concat(mc).concat("-").concat(lastDate.toString());
         
    }

     private long roznicaDni(String da_od, String da_do) throws ParseException{
            DateFormat formatter;
            formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date_od = (Date) formatter.parse(da_od);
            Date date_do = (Date) formatter.parse(da_do);
                long x=date_do.getTime(); 
                long y=date_od.getTime(); 
                long wynik=(x-y); 
                wynik=wynik/(1000*60*60*24); 
                System.out.println("Roznica miedzy datami to "+wynik+" dni..."); 
                return wynik;
     }
    
    public StornoDok getStornoDok() {
        return stornoDok;
    }

    public void setStornoDok(StornoDok stornoDok) {
        this.stornoDok = stornoDok;
    }

    public ArrayList<StornoDok> getLista() {
        return lista;
    }

    public void setLista(ArrayList<StornoDok> lista) {
        this.lista = lista;
    }

    public ArrayList<Dok> getPobraneDok() {
        return pobraneDok;
    }

    public void setPobraneDok(ArrayList<Dok> pobraneDok) {
        this.pobraneDok = pobraneDok;
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
    
    
    
    
    public static void main(String[] args) {
    //
    // Get a calendar instance
    //
    Calendar calendar = Calendar.getInstance();
     
    //
    // Get the last date of the current month. To get the last date for a
    // specific month you can set the calendar month using calendar object
    // calendar.set(Calendar.MONTH, theMonth) method.
    //  
    calendar.set(2013, Calendar.FEBRUARY,1);
    int lastDate = calendar.getActualMaximum(Calendar.DATE);
     
    //
    // Set the calendar date to the last date of the month so then we can
    // get the last day of the month
    //
    calendar.set(Calendar.DATE, lastDate);  
    int lastDay = calendar.get(Calendar.DAY_OF_WEEK);
     
    //
    // Print the current date and the last date of the month
    //
    System.out.println("Last Date: " + calendar.getTime());
     
    //
    // The lastDay will be in a value from 1 to 7 where 1 = monday and 7 =
    // saturday respectively.
    //
    System.out.println("Last Day : " + lastDay);    
    }
    
}
        
