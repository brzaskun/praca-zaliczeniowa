/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PismoadminDAO;
import dao.UzDAO;
import entity.Pismoadmin;
import entity.Uz;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
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
@ManagedBean
@ViewScoped
public class PismoAdminView implements Serializable{
    @Inject
    private UzDAO uzDAO;
    private static final List<String> listamenu;
    private static final List<String> waznosc;
    private static final List<String> status;
    private static final List<String> statusadmin;
    static {
        listamenu = new ArrayList<>();
        listamenu.add("ekran logowanie");
        listamenu.add("zmiana podatnika");
        listamenu.add("info podatnik");
        listamenu.add("parametry");
        listamenu.add("wpisywanie");
        listamenu.add("wykaz dokumentów");
        listamenu.add("podatkowa ksiega");
        listamenu.add("faktury miesieczne");
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
        waznosc = new ArrayList<>();
        waznosc.add("nie wiem po co to zgłaszam");
        waznosc.add("takie tam");
        waznosc.add("wypada naprawić");
        waznosc.add("ważne");
        waznosc.add("pali się");
        status = new ArrayList<>();
        status.add("wysłana");
        status.add("admin przeczytał");
        status.add("admin pracuje");
        status.add("odrzucona");
        status.add("zmiany naniesione");
        statusadmin = new ArrayList<>();
        statusadmin.add("wysłana");
        statusadmin.add("admin przeczytał");
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
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private static boolean wybierztylkobiezace;
    
    
    public PismoAdminView() {
    }
    
     public void molestujadmina() {
         try {
             pismoadmin.setDatawiadomosci(new Date());
             pismoadmin.setNadawca(wpisView.getWprowadzil().getLogin());
             pismoadmin.setStatus("wysłana");
             int wynik = sprawdzduplikat();
             if (wynik == 0) {
                 pismoadminDAO.dodaj(pismoadmin);
                 listapism.add(pismoadmin);
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
             listapism.clear();
             listapism.addAll(pismoadminDAO.findAll());
             Msg.msg("i", "Udało się dodać infomację dla Admina");
             if (p.getStatus().equals("zmiany naniesione")){
                 Uz uz = uzDAO.findUzByLogin(p.getNadawca());
                 MailAdmin.usterkausunieta(p, uz, wpisView);
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
            pismoadminDAO.destroy(p);
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
    
    @PostConstruct
    public void init() {
        listapism = pismoadminDAO.findBiezace();
        //zrobilem odwrotnie bez zmiany nazwy bo przeca po co mi widzie cte archiwalne
        if (wybierztylkobiezace==true) {
            listapismwszytskie = pismoadminDAO.findAll();
        } else {
            listapismwszytskie = pismoadminDAO.findBiezace();
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

    public boolean isWybierztylkobiezace() {
        return wybierztylkobiezace;
    }

    public void setWybierztylkobiezace(boolean wybierztylkobiezace) {
        PismoAdminView.wybierztylkobiezace = wybierztylkobiezace;
    }

    
    
    
    
}
