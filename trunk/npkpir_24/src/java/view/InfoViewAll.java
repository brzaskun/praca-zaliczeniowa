/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjevatDAO;
import dao.DokDAO;
import dao.PitDAO;
import dao.PodatnikDAO;
import embeddable.Mce;
import entity.Dok;
import entity.Podatnik;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;


/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
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
    @Inject PodatnikDAO podatnikDAO;
    
   
    /**
     * Zmienne
     */
    
    private List<String> deklaracjeniewyslane;
    private List<String> deklaracjeniebezupo;
    private List<String> kliencinieruszeni;
    
    @PostConstruct
    private void init(){
        rokdzisiejszy = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        mcdzisiejszy = Mce.getMapamcy().get(Calendar.getInstance().get(Calendar.MONTH));
        deklaracjeniewyslane = deklaracjevatDAO.findDeklaracjeDowyslania(rokdzisiejszy,mcdzisiejszy);
        deklaracjeniebezupo = deklaracjevatDAO.findDeklaracjeBezupo(rokdzisiejszy,mcdzisiejszy);
        /**Klienci nie ruszeni zajmuja duzo czasu
         **/
//        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
//        String mc = Mce.getMapamcy().get(Calendar.getInstance().get(Calendar.MONTH));
//        String rok = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
//        List<Podatnik> tmp = podatnikDAO.findAll();
//        kliencinieruszeni = new ArrayList<>();
//        for(Podatnik p : tmp){
//            List<Dok> dok = dokDAO.zwrocBiezacegoKlientaRokMC(p.getNazwapelna(), rok, mc);
//            if(dok.isEmpty()){
//                if(day>14&&day<21){
//                    kliencinieruszeni.add(p.getNazwapelna());
//                }
//            }
//        }
//        System.out.println(kliencinieruszeni);
    }
    
    public static void main(String[] args){
        int datadzisiejsza = Calendar.getInstance().get(Calendar.MONTH);
        String mc = Mce.getMapamcy().get(datadzisiejsza);
        String rok = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        Calendar c = Calendar.getInstance();
    System.out.println(c.get(Calendar.DAY_OF_MONTH));
    System.out.println(c.get(Calendar.DAY_OF_WEEK));
    System.out.println(c.get(Calendar.DAY_OF_YEAR));
       
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

    public List<String> getKliencinieruszeni() {
        return kliencinieruszeni;
    }

    public void setKliencinieruszeni(List<String> kliencinieruszeni) {
        this.kliencinieruszeni = kliencinieruszeni;
    }
  
    
    
    
}
