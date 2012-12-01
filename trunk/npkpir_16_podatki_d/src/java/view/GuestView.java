/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PodatnikDAO;
import entity.Podatnik;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@SessionScoped
public class GuestView implements Serializable{
    private static String podatnikString;
    private static Podatnik podatnik;
    
    @Inject
    PodatnikDAO podDAO;

    public GuestView() {
        podatnik = new Podatnik();
    }
    
    @PostConstruct
    private void init(){
        podatnik = podDAO.find(podatnikString);
    }

     public void aktualizujTabele(AjaxBehaviorEvent e) {
        RequestContext.getCurrentInstance().update("form:dokumentyLista");
        RequestContext.getCurrentInstance().update("westKsiegowa:westKsiegowaWidok");
    }

    public static String getPodatnikString() {
        return podatnikString;
    }

    public static void setPodatnikString(String podatnikString) {
        GuestView.podatnikString = podatnikString;
    }

    public static Podatnik getPodatnik() {
        return podatnik;
    }

    public static void setPodatnik(Podatnik podatnik) {
        GuestView.podatnik = podatnik;
    }
    
    
    public PodatnikDAO getPodDAO() {
        return podDAO;
    }

    public void setPodDAO(PodatnikDAO podDAO) {
        this.podDAO = podDAO;
    }

     
    
}
