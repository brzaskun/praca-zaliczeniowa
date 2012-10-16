/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;


import dao.DokDAO;
import entity.Dok;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
@ManagedBean(name="DokTabView")
@RequestScoped
public class DokTabView implements Serializable{
   
    private HashMap<String,Dok> dokHashTable;
    //tablica kluczy do obiektów
    private List<String> kluczDOKjsf;
    //tablica obiektów
    private List<Dok> obiektDOKjsf;
    //tablica obiektw danego klienta
    private List<Dok> obiektDOKjsfSel;
    //tablica obiektów danego klienta z określonego roku i miesiąca
    private List<Dok> obiektDOKmrjsfSel;
     //tablica obiektów danego klienta z określonego roku i miesiecy
    private List<Dok> obiektDOKmrjsfSelX;
    /*pkpir*/
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private Dok selDokument;
    
    private static Dok dokdoUsuniecia;

   

    public DokTabView() {
        dokHashTable = new HashMap<String, Dok>();
        kluczDOKjsf = new ArrayList<String>();
        obiektDOKjsf = new ArrayList<Dok>();
        obiektDOKjsfSel = new ArrayList<Dok>();
        obiektDOKmrjsfSel = new ArrayList<Dok>();
        obiektDOKmrjsfSelX = new ArrayList<Dok>();
    }

    @PostConstruct
    public void init() {
        if (wpisView.getPodatnikWpisu() != null) {
            Collection c = null;
            try {
                c = dokDAO.getDownloadedDok();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            if (c != null) {
                Iterator it;
                it = c.iterator();
                int j=1;
                while (it.hasNext()) {
                    Dok tmp = (Dok) it.next();
                    tmp.setNrWpkpir(j);
                    j++;
                    kluczDOKjsf.add(tmp.getIdDok().toString());
                    obiektDOKjsf.add(tmp);
                    if (tmp.getPodatnik().equals(wpisView.getPodatnikWpisu())) {
                        obiektDOKjsfSel.add(tmp);
                    }
                    dokHashTable.put(tmp.getIdDok().toString(), tmp);
                }
                Iterator itx;
                itx = obiektDOKjsfSel.iterator();
                while (itx.hasNext()) {
                    Dok tmpx = (Dok) itx.next();
                    String m = wpisView.getMiesiacWpisu();
                    Integer r = wpisView.getRokWpisu();
                    if (tmpx.getPkpirM().equals(m) && tmpx.getPkpirR().equals(r.toString())) {

                        obiektDOKmrjsfSel.add(tmpx);
                    }
                }
                if (wpisView.getMiesiacOd() != null) {
                    obiektDOKmrjsfSelX.clear();
                    Iterator itxX;
                    itxX = obiektDOKjsfSel.iterator();
                    Integer r = wpisView.getRokWpisu();
                    String mOd = wpisView.getMiesiacOd();
                    Integer mOdI = Integer.parseInt(mOd);
                    String mDo = wpisView.getMiesiacDo();
                    Integer mDoI = Integer.parseInt(mDo);
                    Map<Integer, String> mapa;
                    mapa = new HashMap<Integer, String>();
                    mapa.put(1, "01");
                    mapa.put(2, "02");
                    mapa.put(3, "03");
                    mapa.put(4, "04");
                    mapa.put(5, "05");
                    mapa.put(6, "06");
                    mapa.put(7, "07");
                    mapa.put(8, "08");
                    mapa.put(9, "09");
                    mapa.put(10, "10");
                    mapa.put(11, "11");
                    mapa.put(12, "12");
                    while (itxX.hasNext()) {
                        Dok tmpx = (Dok) itxX.next();
                        for (int i = mOdI; i <= mDoI; i++) {
                            if (tmpx.getPkpirM().equals(mapa.get(i)) && tmpx.getPkpirR().equals(r.toString())) {
                                obiektDOKmrjsfSelX.add(tmpx);
                            }
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
            //refresh();
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

    public void aktualizujTabele(AjaxBehaviorEvent e) {
        RequestContext ctx = null;
        ctx.getCurrentInstance().update("form:dokumentyLista");
        ctx.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
        FacesContext facesContext = FacesContext.getCurrentInstance();
        Application application = facesContext.getApplication();
        ValueBinding binding = application.createValueBinding("#{PodatekView}");
        PodatekView podatekView = (PodatekView) binding.getValue(facesContext);
        podatekView.sprawozdaniePodatkowe();
        ctx.getCurrentInstance().update("form:prezentacjaPodatku");
    }

    public void aktualizujObroty(AjaxBehaviorEvent e) {
        obiektDOKmrjsfSelX.clear();
        RequestContext ctx = null;
        ctx.getCurrentInstance().update("formX");
        ctx.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
    }

    public void aktualizujWestWpisWidok(AjaxBehaviorEvent e) {
        RequestContext ctx = null;
        ctx.getCurrentInstance().update("dodWiad:panelDodawaniaDokumentu");
        ctx.getCurrentInstance().update("westWpis:westWpisWidok");

    }

    public HashMap<String, Dok> getDokHashTable() {
        return dokHashTable;
    }

    public void setDokHashTable(HashMap<String, Dok> dokHashTable) {
        this.dokHashTable = dokHashTable;
    }

    public List<String> getKluczDOKjsf() {
        return kluczDOKjsf;
    }

    public void setKluczDOKjsf(List<String> kluczDOKjsf) {
        this.kluczDOKjsf = kluczDOKjsf;
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

    public  void setDokdoUsuniecia(Dok dokdoUsuniecia) {
        DokTabView.dokdoUsuniecia = dokdoUsuniecia;
    }
 
    
}
