/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjevatDAO;
import dao.DokDAO;
import dao.PitDAO;
import embeddable.Mce;
import entity.Dok;
import entity.Podatnik;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;


/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScope
public class InfoViewAll implements Serializable{
    @Inject private Podatnik pod;
    private String podatnik;
    @Inject private DokDAO dokDAO;
    private List<Dok> dokumenty;
    @Inject private Dok selectedDok;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private String rok;
    @Inject private PitDAO pitDAO;
    @Inject private DeklaracjevatDAO deklaracjevatDAO;
    private String rokdzisiejszy;
    private String mcdzisiejszy;
    
   
    /**
     * Zmienne
     */
    
    private List<String> deklaracjeniewyslane;
    private List<String> deklaracjeniebezupo;
    
    @PostConstruct
    private void init(){
        rokdzisiejszy = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        mcdzisiejszy = Mce.getMapamcy().get(Calendar.getInstance().get(Calendar.MONTH));
        deklaracjeniewyslane = deklaracjevatDAO.findDeklaracjeDowyslania(rokdzisiejszy,mcdzisiejszy);
        deklaracjeniebezupo = deklaracjevatDAO.findDeklaracjeBezupo(rokdzisiejszy,mcdzisiejszy);
    }
    
    public static void main(String[] args){
        int datadzisiejsza = Calendar.getInstance().get(Calendar.MONTH);
        String mc = Mce.getMapamcy().get(datadzisiejsza);
        String rok = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
       
    }

    public List<String> getDeklaracjeniewyslane() {
        return deklaracjeniewyslane;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<String> getDeklaracjeniebezupo() {
        return deklaracjeniebezupo;
    }

    public void setDeklaracjeniebezupo(List<String> deklaracjeniebezupo) {
        this.deklaracjeniebezupo = deklaracjeniebezupo;
    }
  
    
    
    
}
