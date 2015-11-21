/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.SprawozdanieUkladDAO;
import entityfk.SprawozdanieUklad;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SprawozdanieUkladView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<SprawozdanieUklad> zachowaneuklady;
    @Inject
    private SprawozdanieUkladDAO sprawozdanieUkladDAO;
    @Inject
    private SprawozdanieUklad selected;
    private String nazwanowegoukladu;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public SprawozdanieUkladView() {
        this.zachowaneuklady = new ArrayList<>();
    }
    
    @PostConstruct
    private void init() {
        zachowaneuklady = sprawozdanieUkladDAO.findAll();
    }
    
    public void wybranouklad() {
        Msg.msg("i", "Wybrano uklad "+selected.getNazwaukladu());
    }

     public void dodaj() {
        try {
            SprawozdanieUklad uklad = new SprawozdanieUklad();
            uklad.setRok(wpisView.getRokWpisuSt());
            uklad.setNazwaukladu(nazwanowegoukladu);
            sprawozdanieUkladDAO.dodaj(uklad);
            zachowaneuklady.add(uklad);
            nazwanowegoukladu = "";
            Msg.msg("i", "Dodano nowy układ");
        } catch (Exception e) {  
            E.e(e);
            Msg.msg("e", "Nieudana próba dodania układu. "+e.getMessage());
        }
    }
     
      public void usun(SprawozdanieUklad uklad) {
        try {
            sprawozdanieUkladDAO.destroy(uklad);
            zachowaneuklady.remove(uklad);
            Msg.msg("i", "Usunięto wybrany układ");
        } catch (Exception e) {  
            E.e(e);
            Msg.msg("e", "Nieudana próba usuniecia układu."+e.getMessage());
        }
    }
    
      //<editor-fold defaultstate="collapsed" desc="comment">
      public List<SprawozdanieUklad> getZachowaneuklady() {
          return zachowaneuklady;
      }
      
      public void setZachowaneuklady(List<SprawozdanieUklad> zachowaneuklady) {
          this.zachowaneuklady = zachowaneuklady;
      }
      
      public SprawozdanieUkladDAO getSprawozdanieUkladDAO() {
          return sprawozdanieUkladDAO;
      }
      
      public void setSprawozdanieUkladDAO(SprawozdanieUkladDAO sprawozdanieUkladDAO) {
          this.sprawozdanieUkladDAO = sprawozdanieUkladDAO;
      }
      
      public SprawozdanieUklad getSelected() {
          return selected;
      }
      
      public void setSelected(SprawozdanieUklad selected) {
          this.selected = selected;
      }
      
      public String getNazwanowegoukladu() {
          return nazwanowegoukladu;
      }
      
      public void setNazwanowegoukladu(String nazwanowegoukladu) {
          this.nazwanowegoukladu = nazwanowegoukladu;
      }
      
      public WpisView getWpisView() {
          return wpisView;
      }
      
      public void setWpisView(WpisView wpisView) {
          this.wpisView = wpisView;
      }
//</editor-fold>
    
    
    
}
