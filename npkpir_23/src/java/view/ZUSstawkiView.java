/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.ZUSDAO;
import entity.Zusstawki;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import msg.Msg;
import org.joda.time.DateTime;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class ZUSstawkiView implements Serializable{
    @Inject
    private ZUSDAO zusDAO;
    @Inject
    private Zusstawki selected;
    private List<Zusstawki> listapobranychstawek;
    public ZUSstawkiView() {
        listapobranychstawek = new ArrayList<>();
    }
    private String biezacadata;

    @PostConstruct
    private void init() {
        try {
            listapobranychstawek = zusDAO.findAll();
        } catch (Exception e) {
        }
        biezacadata = String.valueOf(new DateTime().getYear());
    }
    
     public void dodajzus(){
         try{
            zusDAO.dodaj(selected);
            listapobranychstawek.add(selected);
            Msg.msg("Dodatno parametr ZUS do podatnika za m-c: "+selected.getZusstawkiPK().getMiesiac());
         } catch (Exception e) {
             Msg.msg("e","Niedodatno parametru ZUS. Wpis za rok "+selected.getZusstawkiPK().getRok()+" i miesiąc "+selected.getZusstawkiPK().getMiesiac()+" już istnieje");
       }
        
     }
     
      public void edytujzus(){
         try{
         zusDAO.edit(selected);
         listapobranychstawek = zusDAO.findAll();
         Msg.msg("Edytowano parametr ZUS do podatnika za m-c:"+selected.getZusstawkiPK().getMiesiac());
       
         } catch (Exception e) {
         Msg.msg("e", "Wystąpił błąd. Nieudana edycja parametru ZUS za m-c:"+selected.getZusstawkiPK().getMiesiac());
         }
        
     }

     
      public void usunzus(Zusstawki zusstawki){
        try {
            zusDAO.destroy(zusstawki);
            listapobranychstawek.remove(zusstawki);
            Msg.msg("Usunieto parametr ZUS do podatnika za m-c: "+zusstawki.getZusstawkiPK().getMiesiac());
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd - nie usunięto stawki.");
        }
     }
    
    
    public ZUSDAO getZusDAO() {
        return zusDAO;
    }

    public void setZusDAO(ZUSDAO zusDAO) {
        this.zusDAO = zusDAO;
    }

    public Zusstawki getSelected() {
        return selected;
    }

    public void setSelected(Zusstawki selected) {
        this.selected = selected;
    }

    public List<Zusstawki> getListapobranychstawek() {
        return listapobranychstawek;
    }

    public String getBiezacadata() {
        return biezacadata;
    }

    public void setBiezacadata(String biezacadata) {
        this.biezacadata = biezacadata;
    }
  
    
    
}
