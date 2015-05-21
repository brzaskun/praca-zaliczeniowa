/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.ZUSDAO;
import entity.Zusstawki;
import entity.ZusstawkiPK;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
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
    @Inject
    private Zusstawki wprowadzanie;
    private List<Zusstawki> listapobranychstawek;
    public ZUSstawkiView() {
        listapobranychstawek = new ArrayList<>();
    }
    private String biezacadata;

    @PostConstruct
    private void init() {
        try {
            listapobranychstawek = zusDAO.findAll();
        } catch (Exception e) { E.e(e); 
        }
        biezacadata = String.valueOf(new DateTime().getYear());
    }
    
     public void dodajzus(){
         try{
            zusDAO.dodaj(wprowadzanie);
            listapobranychstawek = new ArrayList<>();
            listapobranychstawek = zusDAO.findAll();
            wprowadzanie = new Zusstawki();
            Msg.msg("Dodatno parametr ZUS do podatnika za m-c: "+wprowadzanie.getZusstawkiPK().getMiesiac());
         } catch (Exception e) { E.e(e); 
             Msg.msg("e","Niedodatno parametru ZUS. Wpis za rok "+wprowadzanie.getZusstawkiPK().getRok()+" i miesiąc "+wprowadzanie.getZusstawkiPK().getMiesiac()+" już istnieje");
       }
        
     }
     
      public void edytujzus(){
         try{
         zusDAO.edit(wprowadzanie);
         listapobranychstawek = new ArrayList<>();
         listapobranychstawek = zusDAO.findAll();
         wprowadzanie = new Zusstawki();
         Msg.msg("Edytowano parametr ZUS do podatnika za m-c:"+wprowadzanie.getZusstawkiPK().getMiesiac());
       
         } catch (Exception e) { E.e(e); 
         Msg.msg("e", "Wystąpił błąd. Nieudana edycja parametru ZUS za m-c:"+wprowadzanie.getZusstawkiPK().getMiesiac());
         }
        
     }

     
      public void usunzus(Zusstawki zusstawki){
        try {
            zusDAO.destroy(zusstawki);
            listapobranychstawek = new ArrayList<>();
            listapobranychstawek = zusDAO.findAll();
            wprowadzanie = new Zusstawki();
            Msg.msg("Usunieto parametr ZUS do podatnika za m-c: "+zusstawki.getZusstawkiPK().getMiesiac());
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd - nie usunięto stawki.");
        }
     }
      
      public void wybranowiadomosc() {
        wprowadzanie = serialclone.SerialClone.clone(selected);
        Msg.msg("Wybrano stawki ZUS.");
    }
    
    public int sortujZUSstawki(Object obP, Object obW)  {
        int rokO1 = Integer.parseInt(((ZusstawkiPK) obP).getRok());
        int rokO2 = Integer.parseInt(((ZusstawkiPK) obW).getRok());
        int mcO1 = Integer.parseInt(((ZusstawkiPK) obP).getMiesiac());
        int mcO2 = Integer.parseInt(((ZusstawkiPK) obW).getMiesiac());
        if (rokO1 < rokO2) {
            return -1;
        } else if (rokO1 > rokO2) {
            return 1;
        } else if (rokO1 == rokO2) {
            if (mcO1 == mcO2) {
                return 0;
            } else if (mcO1 < mcO2) {
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
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

    public Zusstawki getWprowadzanie() {
        return wprowadzanie;
    }

    public void setWprowadzanie(Zusstawki wprowadzanie) {
        this.wprowadzanie = wprowadzanie;
    }
  
    
    
}
