/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokcomparator;
import dao.DokDAO;
import dao.SMTPSettingsDAO;
import dao.WpisDAO;
import embeddable.Mce;
import entity.Dok;
import entity.Klienci;
import entity.Wpis;
import error.E;
import java.io.Serializable;
import java.security.Principal;
import java.text.Collator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mail.MailOther;
import org.primefaces.context.RequestContext;
import pdf.PdfObroty;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class ObrotyView implements Serializable{
    
     //tablica obiektów danego klienta z określonego roku i miesiecy
    private List<Dok> obiektDOKmrjsfSelX;
    //tablica obiektów danego klienta z określonego roku i miesiecy przefiltrowana
    private List<Dok> dokumentyFiltered;
     //tablica obiektw danego klienta
    private List<Dok> obiektDOKjsfSelRok;
    //lista wybranych dokumentow w panelu Guest
    private List<Dok> goscwybral;
    /*pkpir*/
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject  private DokDAO dokDAO;
    @Inject private WpisDAO wpisDAO;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    private Double podsumowaniewybranych;
    private Double podsumowaniewybranychnetto;
    private List<String> dokumentypodatnika;
    private List<String> kontrahentypodatnika;
    private List<String> rodzajetransakcji;
    private boolean czyduplikaty;

    public ObrotyView() {
        //lista porzechowujaca przefiltrowane widoki
        goscwybral = new ArrayList<>();
    }
    
    @PostConstruct
    private void initC() {
        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        Principal principal;
        if (obiektDOKmrjsfSelX == null) {
            if (request.isUserInRole("Guest")) {
                initGuest();
            } else {
                init();
            }
        }
    }
    
    public void init() {
        //dokumenty podatnika za okres od-do
        obiektDOKmrjsfSelX = new ArrayList<>();
        //dokumenty podatnika z roku
        obiektDOKjsfSelRok = new ArrayList<>();
        dokumentypodatnika = new ArrayList<>();
        kontrahentypodatnika = new ArrayList<>();
        rodzajetransakcji = new ArrayList<>();
          if (wpisView.getMiesiacOd() != null) {
               obiektDOKjsfSelRok = dokDAO.zwrocBiezacegoKlientaRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu().toString());
                obiektDOKmrjsfSelX.clear();
                String mOd = wpisView.getMiesiacOd();
                Integer mOdI = Integer.parseInt(mOd);
                String mDo = wpisView.getMiesiacDo();
                Integer mDoI = Integer.parseInt(mDo);
                List<String> zakres = new ArrayList<>();
//                for(int i = mOdI; i <= mDoI; i++){
//                    zakres.add(Mce.getNumberToMiesiac().get(i));
//                }
                Set<String> dokumentyl = new HashSet<>();
                Set<String> kontrahenty = new HashSet<>();
                Set<String> rodzaje = new HashSet<>();
                for (Dok tmpx : obiektDOKjsfSelRok){
                    int mcint = Integer.parseInt(tmpx.getPkpirM());
                    if (mcint>=mOdI && mcint<=mDoI) {
                        obiektDOKmrjsfSelX.add(tmpx);
                        dokumentyl.add(tmpx.getRodzajedok().getSkrot());
                        rodzaje.add(tmpx.getRodzajedok().getRodzajtransakcji());
                        kontrahenty.add(tmpx.getKontr().getNpelna());
                    }
                }
                Collections.sort(obiektDOKmrjsfSelX, new Dokcomparator());
                int nrkol =1;
                for(Dok p :obiektDOKmrjsfSelX){
                    p.setNrWpkpir(nrkol++);
                }
                dokumentypodatnika.addAll(dokumentyl);
                Collections.sort(dokumentypodatnika);
                Collator collator = Collator.getInstance(new Locale("pl", "PL"));
                collator.setStrength(Collator.PRIMARY);
                kontrahentypodatnika.addAll(kontrahenty);
                Collections.sort(kontrahentypodatnika, collator);
                rodzajetransakcji.addAll(rodzaje);
                Collections.sort(rodzajetransakcji, collator);
            }
     }
    
    
    
     public String initGuest() {
        //dokumenty podatnika za okres od-do
        obiektDOKmrjsfSelX = new ArrayList<>();
        //dokumenty podatnika z roku
        obiektDOKjsfSelRok = new ArrayList<>();
          if (wpisView.getMiesiacOd() != null) {
               obiektDOKjsfSelRok = dokDAO.zwrocBiezacegoKlientaRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu().toString());
                obiektDOKmrjsfSelX.clear();
                String mOd = wpisView.getMiesiacOd();
                Integer mOdI = Integer.parseInt(mOd);
                String mDo = wpisView.getMiesiacDo();
                Integer mDoI = Integer.parseInt(mDo);
                List<String> zakres = new ArrayList<>();
                for(int i = mOdI; i <= mDoI; i++){
                    zakres.add(Mce.getNumberToMiesiac().get(i));
                }
               for (Dok tmpx : obiektDOKjsfSelRok){
                    if (zakres.contains(tmpx.getPkpirM())) {
                        obiektDOKmrjsfSelX.add(tmpx);
                    }
                }
                 //sortowanie dokumentów
                    Collections.sort(obiektDOKmrjsfSelX, new Dokcomparator());
                //
                    int nrkol =1;
                    for(Dok p :obiektDOKmrjsfSelX){
                         p.setNrWpkpir(nrkol++);
                    }
            } 
          return "/guest/ksiegowaKontrahenci.xhtml?faces-redirect=true";
     }
    
      public void aktualizujObrotyX(ActionEvent e) {
        aktualizujGuest();
        RequestContext.getCurrentInstance().update("formX:dokumentyLista");
        RequestContext.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
    }
      
    public void aktualizujObroty(AjaxBehaviorEvent e) {
        aktualizuj();
        RequestContext.getCurrentInstance().update("formX:dokumentyLista");
        RequestContext.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
    }
    private void aktualizuj(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpistmp.setPodatnikWpisu(wpisView.getPodatnikWpisu());
        wpistmp.setMiesiacOd(wpisView.getMiesiacOd());
        wpistmp.setMiesiacDo(wpisView.getMiesiacDo());
        wpisDAO.edit(wpistmp);
        wpisView.naniesDaneDoWpis();
    }
       private void aktualizujGuest(){
        HttpSession sessionX = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);
        String user = (String) sessionX.getAttribute("user");
        Wpis wpistmp = wpisDAO.find(user);
        wpistmp.setMiesiacWpisu(wpisView.getMiesiacWpisu());
        wpistmp.setRokWpisu(wpisView.getRokWpisu());
        wpisDAO.edit(wpistmp);
    }

           
   public void sumawartosciwybranych(){
        podsumowaniewybranych = 0.0;
        podsumowaniewybranychnetto = 0.0;
        for(Dok p : goscwybral){
            try {
                podsumowaniewybranych += p.getBrutto();
            } catch (Exception e) {
                podsumowaniewybranych += p.getNetto();
            }
            podsumowaniewybranychnetto += p.getNetto();
        }
    }
   
     
   public void czyscwybrane() {
        goscwybral = null;
        podsumowaniewybranych = 0.0;
        podsumowaniewybranychnetto = 0.0;
   }
   
   public void mailobroty() {
       try {
           MailOther.obroty(wpisView, sMTPSettingsDAO.findSprawaByDef());
       } catch (Exception e) { 
           E.e(e); 
       }
   }
   
   public void drukujObroty() {
        try {
            if (goscwybral != null && goscwybral.size() > 0) {
                PdfObroty.drukuj(goscwybral, wpisView);
            } else {
                PdfObroty.drukuj(obiektDOKmrjsfSelX, wpisView);
            }
        } catch (Exception e) {
            E.e(e); 
        }
    }
   
   public void usunduplikaty() {
       if (czyduplikaty) {
            if (dokumentyFiltered != null && dokumentyFiltered.size() > 0) {
               porzadekduplikaty(dokumentyFiltered);
            } else {
                porzadekduplikaty(obiektDOKmrjsfSelX);
            }
       } else {
           init();
       }
   }
   
    private void porzadekduplikaty(List<Dok> dok) {
        Set<Klienci> kontrahenci = new HashSet<>();
        for (Iterator<Dok> it = dok.iterator(); it.hasNext();) {
            Klienci k = it.next().getKontr1();
            if (kontrahenci.contains(k)) {
                it.remove();
            } else {
                kontrahenci.add(k);
            }
        }
    }
       
    public List<Dok> getObiektDOKmrjsfSelX() {
        return obiektDOKmrjsfSelX;
    }

    public void setObiektDOKmrjsfSelX(List<Dok> obiektDOKmrjsfSelX) {
        this.obiektDOKmrjsfSelX = obiektDOKmrjsfSelX;
    }
   

    public List<Dok> getGoscwybral() {
        return goscwybral;
    }

    public void setGoscwybral(List<Dok> goscwybral) {
        this.goscwybral = goscwybral;
    }

    public Double getPodsumowaniewybranych() {
        return podsumowaniewybranych;
    }

    public void setPodsumowaniewybranych(Double podsumowaniewybranych) {
        this.podsumowaniewybranych = podsumowaniewybranych;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Double getPodsumowaniewybranychnetto() {
        return podsumowaniewybranychnetto;
    }

    public void setPodsumowaniewybranychnetto(Double podsumowaniewybranychnetto) {
        this.podsumowaniewybranychnetto = podsumowaniewybranychnetto;
    }

    public List<Dok> getDokumentyFiltered() {
        return dokumentyFiltered;
    }

    public void setDokumentyFiltered(List<Dok> dokumentyFiltered) {
        this.dokumentyFiltered = dokumentyFiltered;
    }

    public List<String> getDokumentypodatnika() {
        return dokumentypodatnika;
    }

    public void setDokumentypodatnika(List<String> dokumentypodatnika) {
        this.dokumentypodatnika = dokumentypodatnika;
    }

    public List<String> getKontrahentypodatnika() {
        return kontrahentypodatnika;
    }

    public void setKontrahentypodatnika(List<String> kontrahentypodatnika) {
        this.kontrahentypodatnika = kontrahentypodatnika;
    }

    public List<String> getRodzajetransakcji() {
        return rodzajetransakcji;
    }

    public void setRodzajetransakcji(List<String> rodzajetransakcji) {
        this.rodzajetransakcji = rodzajetransakcji;
    }

    public boolean isCzyduplikaty() {
        return czyduplikaty;
    }

    public void setCzyduplikaty(boolean czyduplikaty) {
        this.czyduplikaty = czyduplikaty;
    }

   

    
    
}
