/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokcomparator;
import dao.DokDAO;
import dao.WpisDAO;
import embeddable.Mce;
import entity.Dok;
import entity.Wpis;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class ObrotyView implements Serializable{
    
    /*pkpir*/
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
     //tablica obiektów danego klienta z określonego roku i miesiecy
    private List<Dok> obiektDOKmrjsfSelX;
     //tablica obiektw danego klienta
    private List<Dok> obiektDOKjsfSelRok;
    @Inject  private DokDAO dokDAO;
    @Inject private WpisDAO wpisDAO;
     //lista wybranych dokumentow w panelu Guest
    private List<Dok> goscwybral;
    private Double podsumowaniewybranych;

    public ObrotyView() {
         //dokumenty podatnika za okres od-do
        obiektDOKmrjsfSelX = new ArrayList<>();
        //dokumenty podatnika z roku
        obiektDOKjsfSelRok = new ArrayList<>();
        //lista porzechowujaca przefiltrowane widoki
        goscwybral = new ArrayList<>();
    }
    
    @PostConstruct
    public void init() {
          if (wpisView.getMiesiacOd() != null) {
               obiektDOKjsfSelRok = dokDAO.zwrocBiezacegoKlientaRok(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu().toString());
                obiektDOKmrjsfSelX.clear();
                String mOd = wpisView.getMiesiacOd();
                Integer mOdI = Integer.parseInt(mOd);
                String mDo = wpisView.getMiesiacDo();
                Integer mDoI = Integer.parseInt(mDo);
                List<String> zakres = new ArrayList<>();
                for(int i = mOdI; i <= mDoI; i++){
                    zakres.add(Mce.getMapamcy().get(i));
                }
                for (Dok tmpx : obiektDOKjsfSelRok){
                    Iterator it;
                    it = zakres.iterator();
                    while(it.hasNext()){
                        String miesiaczakres = (String) it.next();
                        if (tmpx.getPkpirM().equals(miesiaczakres)) {
                           
                            obiektDOKmrjsfSelX.add(tmpx);
                        }
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
        for(Dok p : goscwybral){
            podsumowaniewybranych += p.getBrutto();
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

     
}
