/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PitDAO;
import dao.PodatnikDAO;
import dao.SMTPSettingsDAO;
import dao.WpisDAO;
import entity.Pitpoz;
import entity.Wpis;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import mail.MailOther;
import msg.Msg;
import org.primefaces.context.RequestContext;
import pdf.PdfPIT5;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PitView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Pitpoz> lista;
    private Pitpoz biezacyPit;

    @Inject private PitDAO pitDAO;
    @Inject private PodatnikDAO podatnikDAO;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject
    private WpisDAO wpisDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
   

    public PitView() {
        lista = new ArrayList<>();
        biezacyPit = new Pitpoz();
    }
    
    public void wybranopit() {
        Msg.msg("i", String.format("Wybrano PIT za %s/%s", biezacyPit.getPkpirR(), biezacyPit.getPkpirM()));
    }
    
    
    @PostConstruct
    private void init(){
        lista = new ArrayList<>();
        biezacyPit = new Pitpoz();
        lista = pitDAO.findPitPod(wpisView.getRokWpisu().toString(), wpisView.getPodatnikWpisu());
       
    }
    
     public void usun() {
        int index = lista.size() - 1;
        Pitpoz selected = lista.get(index);
        pitDAO.destroy(selected);
        lista.remove(selected);
        RequestContext.getCurrentInstance().update("formpi:tablicapit");
        Msg.msg("i", "Usunieto ostatni PIT dla podatnika "+selected.getUdzialowiec()+" za m-c: "+selected.getPkpirM(),"formpi:messages");
    }
     
     public void drukujarch() {
         try {
            PdfPIT5.drukuj(biezacyPit, wpisView, podatnikDAO);
         } catch (Exception e) { E.e(e); 
             
         }
     }

     public void mailPIT5() {
         try {
             MailOther.pit5(wpisView, sMTPSettingsDAO.findSprawaByDef());
         } catch (Exception e) { E.e(e); 
             
         }
     }
     
     private void aktualizujGuest(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setRokWpisuSt(String.valueOf(wpisView.getRokWpisu()));
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpisDAO.edit(wpistmp);
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
        wpisView.naniesDaneDoWpis();
    }
    
     public void aktualizujGuest(String strona) throws IOException {
        aktualizujGuest();
        aktualizuj();
        init();
        //FacesContext.getCurrentInstance().getExternalContext().redirect(strona);
    }

    public List<Pitpoz> getLista() {
        return lista;
    }
   
    public void setLista(List<Pitpoz> lista) {
        lista = lista;
    }

    public PitDAO getPitDAO() {
        return pitDAO;
    }

    public void setPitDAO(PitDAO pitDAO) {
        this.pitDAO = pitDAO;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Pitpoz getBiezacyPit() {
        return biezacyPit;
    }

    public void setBiezacyPit(Pitpoz biezacyPit) {
        this.biezacyPit = biezacyPit;
    }

    


   
    
}

