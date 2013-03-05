/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SesjaDAO;
import entity.Sesja;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class SesjaView implements Serializable{
    @Inject private Sesja sesja;
    @Inject private SesjaDAO sesjaDAO;
    private List<Sesja> wykazsesji;

    public SesjaView() {
        wykazsesji = new ArrayList<>();
    }
    
    @PostConstruct
    private void init(){
       try{
       wykazsesji.addAll(sesjaDAO.findAll()); 
       } catch (Exception e){}
    }
    
    
    
    public void dodajwydruk(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        try {
            sesja = sesjaDAO.find(session.getId());
            int ilosc = sesja.getIloscwydrukow();
            ilosc = ilosc+1;
            sesja.setIloscwydrukow(ilosc);
            sesjaDAO.edit(sesja);
        } catch (Exception e) {
        }
    }
    
    public void dodajdokument(){
        HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(true);
        try {
            sesja = sesjaDAO.find(session.getId());
            int ilosc = sesja.getIloscdokumentow();
            ilosc = ilosc+1;
            sesja.setIloscdokumentow(ilosc);
            sesjaDAO.edit(sesja);
        } catch (Exception e) {
        }
        
    }

    

    public Sesja getSesja() {
        return sesja;
    }

    public void setSesja(Sesja sesja) {
        this.sesja = sesja;
    }

    public SesjaDAO getSesjaDAO() {
        return sesjaDAO;
    }

    public void setSesjaDAO(SesjaDAO sesjaDAO) {
        this.sesjaDAO = sesjaDAO;
    }

    public List<Sesja> getWykazsesji() {
        return wykazsesji;
    }

    public void setWykazsesji(List<Sesja> wykazsesji) {
        this.wykazsesji = wykazsesji;
    }

    
    
}
