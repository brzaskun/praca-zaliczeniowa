/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.StornoDokDAO;
import embeddable.Mce;
import embeddable.Stornodoch;
import entity.Dok;
import entity.Rozrachunek1;
import entity.StornoDok;
import error.E;
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
import java.util.ListIterator;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScope
public class StornoDokView implements Serializable {
    private static final long serialVersionUID = 1L;
    private String stornonadzien;

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

        
    }

    @Inject
    private StornoDok stornoDok;
    private List<Dok> lista;
    private List<Dok> pobraneDok;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAO dokDAO;
    private Double wyst;
    @Inject
    private StornoDokDAO stornoDokDAO;
    private StornoDok selected;
    private boolean button;
    
    

    public StornoDokView() {
        lista = Collections.synchronizedList(new ArrayList<>());
        pobraneDok = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    public void init() { //E.m(this);
        String mc = wpisView.getMiesiacWpisu();
        Integer rok = wpisView.getRokWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        Integer mcCalendar = Mce.getMapamcyCalendar().get(mc);
        try {
            StornoDok tmp = stornoDokDAO.find(rok, mc, podatnik);
            lista = (ArrayList<Dok>) tmp.getDokument();
        } catch (Exception e) { E.e(e); 
        }
        List<Dok> tmplist = Collections.synchronizedList(new ArrayList<>());
        if (wpisView.getPodatnikWpisu() != null) {
            try {
                tmplist.addAll(dokDAO.zwrocBiezacegoKlienta(wpisView.getPodatnikObiekt()));
            } catch (Exception e) { E.e(e); 
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

    public String policzdokumentystorno() throws IOException{
        Integer rok = wpisView.getRokWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        List<StornoDok> listax = stornoDokDAO.find(rok, podatnik);
        String result;
        if(listax.size()>0){
            StornoDok ostatnidokumnetstoro = listax.get(listax.size()-1);
            String miesiac = ostatnidokumnetstoro.getMc();
            result = miesiac;
        } else {
            result = "  - za żaden miesiąc. Rok pusty.";
        }
        return result;
    }
     
    
    public String stornodokumentow(ActionEvent xe) throws ParseException {
        Integer rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
        StornoDok tmp = stornoDokDAO.find(rok, mc, podatnik);
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dokumenty za okres wystornowane. Wygenerowano dokument storno. Usuń go wpierw.", "");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        PrimeFaces.current().ajax().update("form:messages");
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
                List<Rozrachunek1> rozrachunki = Collections.synchronizedList(new ArrayList<>());
                try {
                    rozrachunki.addAll(tmp.getRozrachunki1());
                } catch (Exception ex) {
                }
                if (rozrachunki.isEmpty()) {
                    wyst = -tmp.getNetto();
                } else {
                    //SPRAWDZIC DATE
                    ListIterator ita;
                    ita = rozrachunki.listIterator(rozrachunki.size());
                    while(ita.hasPrevious()){
                        Rozrachunek1 tmpx = (Rozrachunek1) ita.previous();
                        String data = tmpx.getDataplatnosci();
                        String r = data.substring(0, 4);
                        String m = data.substring(5, 7);
                        Integer mcs = Integer.parseInt(m);
                        Integer mcp = Integer.parseInt(mc);
                        if (r.equals(rok.toString()) && (mcs<=mcp)) {
                            wyst = tmpx.getDorozliczenia();
                            break;
                        }
                    }
                }
                List<Stornodoch> wystornowane = Collections.synchronizedList(new ArrayList<>());
                try {
                    wystornowane.addAll(tmp.getStorno());
                } catch (Exception ex) {
                }
                double doplacono;
                if (wystornowane.isEmpty()) {
                    //jezeli nie bylo storna to wyksieguj
                    List<Stornodoch> storno = Collections.synchronizedList(new ArrayList<>());
                    storno.add(new Stornodoch(stornonadzien, wyst, wyst, true));
                    tmp.setStorno(storno);
                    dokDAO.edit(tmp);
                    stornodokument(tmp);
                } else {
                    if ((-wyst) < tmp.getNetto()) {
                        List<Stornodoch> storno = tmp.getStorno();
                        double roznica = storno.get(storno.size()-1).getDorozliczenia();
                        doplacono = roznica-wyst;
                        storno.add(new Stornodoch(stornonadzien, -doplacono, wyst, true));
                        tmp.setStorno(storno);
                        dokDAO.edit(tmp);
                        stornodokument(tmp);
                    }
                }
            } else {
                //trzeba zeby wprowadzal dokument pusty jednak!!!
            }
    }
        }
        FacesContext context = FacesContext.getCurrentInstance();
        DokView dokView = (DokView) context.getELContext().getELResolver().getValue(context.getELContext(), null,"dokumentView");
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        Application application = facesContext.getApplication();
//        ValueBinding binding = application.createValueBinding("#{DokumentView}");
//        DokView dokView = (DokView) binding.getValue(facesContext);archeo
        dokView.dodajNowyWpisAutomatycznyStorno();
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
        return rok.toString().concat("-").concat(mc).concat("-").concat(lastDate.toString());

    }

    private long roznicaDni(String da_od, String da_do) throws ParseException {
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date_od = formatter.parse(da_od);
        Date date_do = formatter.parse(da_do);
        long x = date_do.getTime();
        long y = date_od.getTime();
        long wynik = (x - y);
        wynik = wynik / (1000 * 60 * 60 * 24);
        return wynik;
    }

    private void stornodokument(Dok dokument) {
        StornoDok stornoDok = new StornoDok();
        Integer rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        List<Dok> listawew;
        //pobiera nowy dokument. ewentualnie uzupelnia stary
        try {
            stornoDok = stornoDokDAO.find(rok, mc, podatnik);
            listawew = (ArrayList) stornoDok.getDokument();
        } catch (Exception e) { E.e(e); 
            stornoDok.setRok(rok);
            stornoDok.setMc(mc);
            stornoDok.setPodatnik(wpisView.getPodatnikWpisu());
            listawew = Collections.synchronizedList(new ArrayList<>());
        }
        listawew.add(dokument);
        stornoDok.setDokument(listawew);
        stornoDokDAO.edit(stornoDok);
        PrimeFaces.current().ajax().update("form:dokumentyLista");
    }
    
    public void usunstornodokumentow(ActionEvent xf) throws Exception {
        Integer rok = wpisView.getRokWpisu();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            Integer mci = Integer.parseInt(mc)+1;
            String mcn = Mce.getNumberToMiesiac().get(mci);
            StornoDok tmp = stornoDokDAO.find(rok, mcn, podatnik);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Istnieje dokument późniejszy. Usuń go wpierw.", tmp.getMc());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().ajax().update("super:super");
        } catch (Exception x){
        StornoDok stornodok = stornoDokDAO.find(rok, mc, podatnik);
        List<Dok> dokumentystorno = (ArrayList<Dok>) stornodok.getDokument();
        Iterator it;
        it = dokumentystorno.iterator();
        while(it.hasNext()){
            Dok tmp = dokDAO.znajdzDuplikat((Dok) it.next(), wpisView.getRokWpisuSt());
            if(tmp!=null){
                List<Stornodoch> stornodoch = tmp.getStorno();
                String data = stornodoch.get(stornodoch.size()-1).getDataplatnosci();
                    String r = data.substring(0,4);
                    String m = data.substring(5, 7);
                    if(r.equals(rok.toString())&&m.equals(mc)){
                        stornodoch.remove(stornodoch.size()-1);
                        tmp.setStorno(stornodoch);
                        dokDAO.edit(tmp);
                    } else {
                         FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Istnieje dokument późniejszy. Usuń go wpierw.", stornodok.getMc());
        FacesContext.getCurrentInstance().addMessage(null, msg);
        PrimeFaces.current().ajax().update("form:niezaplaconech");
                }
            }
        }
        stornoDokDAO.destroy(stornodok);
        dokDAO.destroyStornoDok(rok.toString(), mc, wpisView.getPodatnikObiekt());
        PrimeFaces.current().ajax().update("form:dokumentyLista");
        }
    }

    public StornoDok getStornoDok() {
        return stornoDok;
    }

    public void setStornoDok(StornoDok stornoDok) {
        this.stornoDok = stornoDok;
    }

    public List<Dok> getLista() {
        return lista;
    }

    public void setLista(List<Dok> lista) {
        this.lista = lista;
    }

    public List<Dok> getPobraneDok() {
        return pobraneDok;
    }

    public void setPobraneDok(List<Dok> pobraneDok) {
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

    public boolean isButton() {
        return button;
    }

    public void setButton(boolean button) {
        this.button = button;
    }

    
}
