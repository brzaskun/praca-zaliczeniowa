/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.StornoDokDAO;
import embeddable.Mce;
import embeddable.Rozrachunek;
import embeddable.Stornodoch;
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
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScope
public class StornoDokView implements Serializable {

    @Inject
    private StornoDok stornoDok;
    private ArrayList<Dok> lista;
    private ArrayList<Dok> pobraneDok;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAO dokDAO;
    private static String stornonadzien;
    private Double wyst;
    @Inject
    private StornoDokDAO stornoDokDAO;
    private StornoDok selected;

    public StornoDokView() {
        lista = new ArrayList<>();
        pobraneDok = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        String mc = wpisView.getMiesiacWpisu();
        Integer rok = wpisView.getRokWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        Integer mcCalendar = Mce.getMapamcyCalendar().get(mc);
        try {
            StornoDok tmp = stornoDokDAO.find(rok, mc, podatnik);
            lista = (ArrayList<Dok>) tmp.getDokument();
        } catch (Exception e) {
            System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
        }
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
                if (tmpx.getPkpirR().equals(r.toString()) && tmpx.getRozliczony() == false) {
                    pobraneDok.add(tmpx);
                }
            }
        }
    }

    public String stornodokumentow() throws ParseException {
        Integer rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
        StornoDok tmp = stornoDokDAO.find(rok, mc, podatnik);
        } catch (Exception x){
        stornonadzien = ustaldzienmiesiaca();
        String termin;
        Double wystornowac;
        Iterator it;
        it = pobraneDok.iterator();
        while (it.hasNext()) {
            Dok tmp = (Dok) it.next();
            if (tmp.getTermin30() != null) {
                termin = tmp.getTermin30();
            } else {
                termin = tmp.getTermin90();
            }
            if (roznicaDni(termin, stornonadzien) > 0) {
                System.out.println("trzeba stornowac " + roznicaDni(termin, stornonadzien));
                ArrayList<Rozrachunek> rozrachunki = new ArrayList<>();
                try {
                    rozrachunki.addAll(tmp.getRozrachunki());
                } catch (Exception ex) {
                }
                if (rozrachunki.isEmpty()) {
                    wyst = tmp.getNetto();
                } else {
                    wyst = rozrachunki.get((rozrachunki.size() - 1)).getDorozliczenia();
                }
                ArrayList<Stornodoch> wystornowane = new ArrayList<>();
                try {
                    wystornowane.addAll(tmp.getStorno());
                } catch (Exception ex) {
                }
                double doplacono;
                if (wystornowane.size() == 0) {
                    //jezeli nie bylo storna to wyksieguj
                    ArrayList<Stornodoch> storno = new ArrayList<>();
                    storno.add(new Stornodoch(stornonadzien, wyst, wyst, true));
                    tmp.setStorno(storno);
                    dokDAO.edit(tmp);
                    System.out.println("Udalo sie");
                    stornodokument(tmp);
                } else {
                    if ((-wyst) < tmp.getNetto()) {
                        ArrayList<Stornodoch> storno = tmp.getStorno();
                        double roznica = storno.get(storno.size()-1).getDorozliczenia();
                        doplacono = roznica-wyst;
                        storno.add(new Stornodoch(stornonadzien, -doplacono, wyst, true));
                        tmp.setStorno(storno);
                        dokDAO.edit(tmp);
                        System.out.println("Udalo sie");
                        stornodokument(tmp);
                    }
                }
            }
        }
    }
        return "/ksiegowa/ksiegowaNiezaplacone.xhtml?faces-redirect=true";
    }

    public String ustaldzienmiesiaca() {
        Calendar calendar = Calendar.getInstance();
        String mc = wpisView.getMiesiacWpisu();
        Integer rok = wpisView.getRokWpisu();
        Integer mcCalendar = Mce.getMapamcyCalendar().get(mc);
        calendar.set(rok, mcCalendar, 1);
        Integer lastDate = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(Calendar.DATE, lastDate);
        System.out.println("Last Date: " + calendar.getTime());
        return rok.toString().concat("-").concat(mc).concat("-").concat(lastDate.toString());

    }

    private long roznicaDni(String da_od, String da_do) throws ParseException {
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date_od = (Date) formatter.parse(da_od);
        Date date_do = (Date) formatter.parse(da_do);
        long x = date_do.getTime();
        long y = date_od.getTime();
        long wynik = (x - y);
        wynik = wynik / (1000 * 60 * 60 * 24);
        System.out.println("Roznica miedzy datami to " + wynik + " dni...");
        return wynik;
    }

    private void stornodokument(Dok dokument) {
        StornoDok stornoDok = new StornoDok();
        Integer rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        ArrayList<Dok> listawew;
        //pobiera nowy dokument. ewentualnie uzupelnia stary
        try {
            stornoDok = stornoDokDAO.find(rok, mc, podatnik);
            listawew = (ArrayList) stornoDok.getDokument();
        } catch (Exception e) {
            stornoDok.setRok(rok);
            stornoDok.setMc(mc);
            stornoDok.setPodatnik(wpisView.getPodatnikWpisu());
            listawew = new ArrayList<>();
        }
        listawew.add(dokument);
        stornoDok.setDokument(listawew);
        stornoDokDAO.edit(stornoDok);
        RequestContext.getCurrentInstance().update("form:dokumentyLista");
    }

    public void usunstornodokumentow() {
        //trzeba jeszcze dac makro usuwania zapisow z dokumentow!!!!
        Integer rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        StornoDok tmp = stornoDokDAO.find(rok, mc, podatnik);
        //stornoDokDAO.destroy(tmp);
        RequestContext.getCurrentInstance().update("form:dokumentyLista_s");
    }

    public StornoDok getStornoDok() {
        return stornoDok;
    }

    public void setStornoDok(StornoDok stornoDok) {
        this.stornoDok = stornoDok;
    }

    public ArrayList<Dok> getLista() {
        return lista;
    }

    public void setLista(ArrayList<Dok> lista) {
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

    public StornoDok getSelected() {
        return selected;
    }

    public void setSelected(StornoDok selected) {
        this.selected = selected;
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
        calendar.set(2013, Calendar.FEBRUARY, 1);
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
