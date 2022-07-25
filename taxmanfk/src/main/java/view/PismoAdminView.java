/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PismoadminDAO;
import dao.SMTPSettingsDAO;
import dao.UzDAO;
import entity.Pismoadmin;
import entity.Uz;
import error.E;
import java.io.Serializable;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.inject.Inject;
import mail.MailAdmin;
import msg.Msg;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PismoAdminView implements Serializable{
    @Inject
    private UzDAO uzDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    private static final List<String> listamenu;
    private static final List<String> listamenu_fk;
    private static final List<String> waznosc;
    private static final List<String> status;
    private static final List<String> statusadmin;
    static {
        listamenu = Collections.synchronizedList(new ArrayList<>());
        listamenu.add("ekran logowanie");
        listamenu.add("zmiana podatnika");
        listamenu.add("info podatnik");
        listamenu.add("parametry");
        listamenu.add("wpisywanie");
        listamenu.add("wykaz dokumentów");
        listamenu.add("podatkowa ksiega");
        listamenu.add("faktury miesieczne");
        listamenu.add("faktury wystawione i rozl.");
        listamenu.add("jpk");
        listamenu.add("niezaplacone");
        listamenu.add("zaplacone");
        listamenu.add("obroty w roku");
        listamenu.add("kontrahenci fakt.");
        listamenu.add("zestaw. sumaryczne");
        listamenu.add("środki trwałe");
        listamenu.add("pity");
        listamenu.add("ewidencje vat");
        listamenu.add("zamknięcie miesiąca");
        listamenu.add("faktury - panel");
        listamenu.add("zus");
        listamenu.add("napisz do admina");
        listamenu_fk = Collections.synchronizedList(new ArrayList<>());
        listamenu_fk.add("księgowanie");
        listamenu_fk.add("przeglądanie zaksięgowanych");
        listamenu_fk.add("zapisy konta");
        listamenu_fk.add("jpk");
        listamenu_fk.add("zestawienia sald");
        listamenu_fk.add("zestawienia sald narast.");
        listamenu_fk.add("symulacja");
        listamenu_fk.add("symulacja narastająco");
        listamenu.add("faktury wystawione i rozl.");
        listamenu_fk.add("ewidencja vat");
        listamenu_fk.add("bilans");
        listamenu_fk.add("rachunek zysków i strat");
        listamenu_fk.add("plan kont");
        listamenu_fk.add("dane firmy");
        listamenu_fk.add("środki trwałe");
        listamenu_fk.add("słowniki");
        listamenu_fk.add("rmk");
        waznosc = Collections.synchronizedList(new ArrayList<>());
        waznosc.add("na przyszłość");
        waznosc.add("przydałoby się");
        waznosc.add("przeszkadza");
        waznosc.add("ważne");
        waznosc.add("nie można bez tego pracować");
        status = Collections.synchronizedList(new ArrayList<>());
        status.add("wysłana");
        status.add("za mało danych");
        status.add("admin nierozumie");
        status.add("admin przeczytał");
        status.add("admin pracuje");
        status.add("odrzucona");
        status.add("zmiany naniesione");
        statusadmin = Collections.synchronizedList(new ArrayList<>());
        statusadmin.add("wysłana");
        statusadmin.add("za mało danych");
        statusadmin.add("admin nierozumie");
        statusadmin.add("admin pracuje");
        statusadmin.add("odrzucona");
        statusadmin.add("zmiany naniesione");
        statusadmin.add("archiwalna");
    }
    @Inject
    private Pismoadmin pismoadmin;
    private List<Pismoadmin> listapism;
    private List<Pismoadmin> listapismwszytskie;
    @Inject private PismoadminDAO pismoadminDAO;
    @Inject
    private WpisView wpisView;
    private boolean razemzarchiwalnymi;
    private boolean wybierznowe;
    
    
       
    public PismoAdminView() {
    }
    
    @PostConstruct
    public void init() { //E.m(this);
        listapism = pismoadminDAO.findNowe();
        if (razemzarchiwalnymi==true) {
            listapismwszytskie = pismoadminDAO.findAll();
        } else if (wybierznowe==true) {
            listapismwszytskie = pismoadminDAO.findBiezace();
        } else {
            listapismwszytskie = pismoadminDAO.findNowe();
        }
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
        Collections.sort(listamenu, collator);
        Collections.sort(listamenu_fk, collator);
    }
    
     public void molestujadmina() {
         try {
             pismoadmin.setMenulink(wpisView.getPodatnikObiekt().getPrintnazwa());
             pismoadmin.setDatawiadomosci(new Date());
             pismoadmin.setNadawca(wpisView.getUzer().getLogin());
             pismoadmin.setStatus("wysłana");
             pismoadmin.setWaznosc(".");
             int wynik = sprawdzduplikat();
             if (wynik == 0) {
                 pismoadminDAO.create(pismoadmin);
                 listapism.clear();
                 listapism.addAll(pismoadminDAO.findAll());
                 pismoadmin = new Pismoadmin();
                 Msg.msg("i", "Udało się dodać infomację dla Admina");
             }
         } catch (Exception e) { E.e(e); 
             Msg.msg("e", "Wystąpił błąd, nie udało się dodać infomacji dla Admina");
         }
     }
     
     public void edytujpismoadmin(Pismoadmin p) {
         try {
             p.setDatastatus(new Date());
             pismoadminDAO.edit(p);
             if (wybierznowe == false) {
                 listapismwszytskie.remove(p);
             }
             Msg.msg("i", "Udało się dodać infomację dla Admina");
             if (p.getStatus().equals("zmiany naniesione")){
                 Uz uz = uzDAO.findUzByLogin(p.getNadawca());
                 MailAdmin.usterkausunieta(p, uz, wpisView, sMTPSettingsDAO.findSprawaByDef());
                 Msg.msg("i", "Poinformowano zlecającego o załatwieniu sprawy.");
             }
         } catch (Exception e) { E.e(e); 
             Msg.msg("e", "Wystąpił błąd, nie udało się dodać infomacji dla Admina");
         }
     }
     
     private int sprawdzduplikat() {
         List<Pismoadmin> lista = pismoadminDAO.findAll();
         for (Pismoadmin p : lista) {
             if (p.equals(pismoadmin)) {
                 return 1;
             }
         }
         return 0;
     }
    
    public void usunpismoadmin(Pismoadmin p) {
        try {
            pismoadminDAO.remove(p);
            listapismwszytskie.remove(p);
            Msg.msg("i", "Udało się usunąć infomację dla Admina");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd, nie udało się usunąć infomacji dla Admina");
        }
    }
    
    public void walidacjaobszaru(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
        String obszar = value.toString();
        if (!obszar.equals("wybierz obszar")) {
        } else {
            throw new ValidatorException(Msg.validator("w","Wybierz temat wiadomości, obszar programu gdzie pojawił się błąd"));
        }
    }
    
    public void walidacjawaznosc(FacesContext ctx, UIComponent component, Object value) throws ValidatorException {
        String obszar = value.toString();
        if (!obszar.equals("wybierz ważność")) {
        } else {
            throw new ValidatorException(Msg.validator("w","Określ jak bardzo pilna jest to wiadomość"));
        }
    }
    
    

    public Pismoadmin getPismoadmin() {
        return pismoadmin;
    }

    public void setPismoadmin(Pismoadmin pismoadmin) {
        this.pismoadmin = pismoadmin;
    }

    public List<String> getListamenu() {
        return listamenu;
    }
    
    public List<String> getListamenuFK() {
        return listamenu_fk;
    }

    public List<String> getWaznosc() {
        return waznosc;
    }

    
    
    public List<Pismoadmin> getListapism() {
        return listapism;
    }

    public void setListapism(List<Pismoadmin> listapism) {
        this.listapism = listapism;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<String> getStatus() {
        return status;
    }

    public List<String> getStatusadmin() {
        return statusadmin;
    }

    public List<Pismoadmin> getListapismwszytskie() {
        return listapismwszytskie;
    }

    public void setListapismwszytskie(List<Pismoadmin> listapismwszytskie) {
        this.listapismwszytskie = listapismwszytskie;
    }

    public boolean isRazemzarchiwalnymi() {
        return razemzarchiwalnymi;
    }

    public void setRazemzarchiwalnymi(boolean razemzarchiwalnymi) {
        this.razemzarchiwalnymi = razemzarchiwalnymi;
    }

    public boolean isWybierznowe() {
        return wybierznowe;
    }

    public void setWybierznowe(boolean wybierznowe) {
        this.wybierznowe = wybierznowe;
    }

   

    
    
    
    
    
}
