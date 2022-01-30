/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PismoadminFacade;
import dao.SMTPSettingsFacade;
import dao.UzFacade;
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
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
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
    private UzFacade uzFacade;
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
    private static final List<String> status;
    private static final List<String> statusadmin;
    static {
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
    @Inject 
    private PismoadminFacade pismoadminFacade;
    @Inject
    private WpisView wpisView;
    private boolean razemzarchiwalnymi;
    private boolean wybierznowe;
    
    
       
    public PismoAdminView() {
    }
    
    @PostConstruct
    public void init() { //E.m(this);
        listapism = pismoadminFacade.findNowe();
        if (razemzarchiwalnymi==true) {
            listapismwszytskie = pismoadminFacade.findAll();
        } else if (wybierznowe==true) {
            listapismwszytskie = pismoadminFacade.findBiezace();
        } else {
            listapismwszytskie = pismoadminFacade.findNowe();
        }
        Collator collator = Collator.getInstance(new Locale("pl", "PL"));
        collator.setStrength(Collator.PRIMARY);
    }
    
     public void molestujadmina() {
         try {
             pismoadmin.setDatawiadomosci(new Date());
             pismoadmin.setNadawca(wpisView.getUzer().getLogin());
             pismoadmin.setStatus("wysłana");
             int wynik = sprawdzduplikat();
             if (wynik == 0) {
                 pismoadminFacade.create(pismoadmin);
                 listapism.clear();
                 listapism.addAll(pismoadminFacade.findAll());
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
             pismoadminFacade.edit(p);
             if (wybierznowe == false) {
                 listapismwszytskie.remove(p);
             }
             Msg.msg("i", "Udało się dodać infomację dla Admina");
             if (p.getStatus().equals("zmiany naniesione")){
                 Uz uz = uzFacade.findUzByLogin(p.getNadawca());
                 MailAdmin.usterkausunieta(p, uz, wpisView, sMTPSettingsFacade.findSprawaByDef());
                 Msg.msg("i", "Poinformowano zlecającego o załatwieniu sprawy.");
             }
         } catch (Exception e) { E.e(e); 
             Msg.msg("e", "Wystąpił błąd, nie udało się dodać infomacji dla Admina");
         }
     }
     
     private int sprawdzduplikat() {
         List<Pismoadmin> lista = pismoadminFacade.findAll();
         for (Pismoadmin p : lista) {
             if (p.equals(pismoadmin)) {
                 return 1;
             }
         }
         return 0;
     }
    
    public void usunpismoadmin(Pismoadmin p) {
        try {
            pismoadminFacade.remove(p);
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
