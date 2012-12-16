/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import embeddable.Mce;
import entity.Dok;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.el.ValueBinding;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
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
    //tablica obiektów danego klienta z określonego roku i miesiecy
    private List<Dok> obiektDOKmrjsfSelX;
    //dokumenty o tym samym okresie vat
    private List<Dok> dokvatmc;
    //dokumenty niezaplacone
    private List<Dok> niezaplacone;
    /*pkpir*/
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private Dok selDokument;
    private static Dok dokdoUsuniecia;
    
    public DokTabView() {
        //dokumenty podatnika
        obiektDOKjsfSel = new ArrayList<>();
        //dokumenty podatnika z roku
        obiektDOKjsfSelRok = new ArrayList<>();
        //dokumenty podatnika z miesiaca
        obiektDOKmrjsfSel = new ArrayList<>();
        //dokumenty podatnika za okres od-do
        obiektDOKmrjsfSelX = new ArrayList<>();
        //dekumenty o tym samym okresie vat
        dokvatmc = new ArrayList<>();
        //dokumenty niezaplacone
        niezaplacone = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
        if (wpisView.getPodatnikWpisu() != null) {
            try {
                obiektDOKjsfSel.addAll(dokDAO.zwrocBiezacegoKlienta(wpisView.getPodatnikWpisu()));
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            String m = wpisView.getMiesiacWpisu();
            Integer m1 = Integer.parseInt(m);
            String mn = Mce.getMapamcy().get(m1);
            Iterator itx;
            itx = obiektDOKjsfSel.iterator();
            int inu = 1;
            int inus = 1;
            Integer r = wpisView.getRokWpisu();
            while (itx.hasNext()) {
                Dok tmpx = (Dok) itx.next();
                if (tmpx.getPkpirR().equals(r.toString())) {
                    obiektDOKjsfSelRok.add(tmpx);
                     if (tmpx.getRozliczony() == false) {
                        niezaplacone.add(tmpx);
                    }
                }
                if (tmpx.getVatM().equals(mn)) {
                    tmpx.setNrWpkpir(inu);
                    dokvatmc.add(tmpx);
                    inu++;
                }
                if (tmpx.getPkpirM().equals(m)) {
                    tmpx.setNrWpkpir(inus);
                    obiektDOKmrjsfSel.add(tmpx);
                   
                    inus++;
                }
            }
            if (wpisView.getMiesiacOd() != null) {
                obiektDOKmrjsfSelX.clear();
                Iterator itxX;
                itxX = obiektDOKjsfSel.iterator();
                String mOd = wpisView.getMiesiacOd();
                Integer mOdI = Integer.parseInt(mOd);
                String mDo = wpisView.getMiesiacDo();
                Integer mDoI = Integer.parseInt(mDo);
                while (itxX.hasNext()) {
                    Dok tmpx = (Dok) itxX.next();
                    for (int i = mOdI; i <= mDoI; i++) {
                        if (tmpx.getPkpirM().equals(Mce.getMapamcy().get(i)) && tmpx.getPkpirR().equals(r.toString())) {
                            obiektDOKmrjsfSelX.add(tmpx);
                        }
                    }
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
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //       Principal principal = request.getUserPrincipal();
//        if(request.isUserInRole("Administrator")){
        try {
            obiektDOKjsfSel.remove(dokdoUsuniecia);
            obiektDOKmrjsfSel.remove(dokdoUsuniecia);
            dokDAO.destroy(dokdoUsuniecia);
        } catch (Exception e) {
            System.out.println("Nie usnieto " + dokdoUsuniecia.getIdDok() + " " + e.toString());
        }
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dokument usunięty", dokdoUsuniecia.getIdDok().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
//    } else {
//            FacesMessage msg = new FacesMessage("Nie masz uprawnien do usuniecia dokumentu", selDokument.getIdDok().toString());
//          FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//     }
    }
    //usun jak wciaz dziala bez nich
//    public void aktualizujTabele(AjaxBehaviorEvent e) {
//        RequestContext.getCurrentInstance().update("form:dokumentyLista");
//        RequestContext.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
//        FacesContext facesContext = FacesContext.getCurrentInstance();
//        Application application = facesContext.getApplication();
//        ValueBinding binding = application.createValueBinding("#{PodatekView}");
//        PodatekView podatekView = (PodatekView) binding.getValue(facesContext);
//        podatekView.sprawozdaniePodatkowe();
//        RequestContext.getCurrentInstance().update("form:prezentacjaPodatku");
//    }
//    
//    public void aktualizujObroty(AjaxBehaviorEvent e) {
//        obiektDOKmrjsfSelX.clear();
//        RequestContext.getCurrentInstance().update("formX");
//        RequestContext.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
//    }
//    
//    public void aktualizujWestWpisWidok(AjaxBehaviorEvent e) {
//        RequestContext ctx = null;
//        RequestContext.getCurrentInstance().update("dodWiad:panelDodawaniaDokumentu");
//        RequestContext.getCurrentInstance().update("westWpis:westWpisWidok");
//        
//    }
    
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
    
    public List<Dok> getObiektDOKmrjsfSelX() {
        return obiektDOKmrjsfSelX;
    }
    
    public void setObiektDOKmrjsfSelX(List<Dok> obiektDOKmrjsfSelX) {
        this.obiektDOKmrjsfSelX = obiektDOKmrjsfSelX;
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
}
