/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.PodatnikDAO;
import dao.UPODAO;
import embeddable.Mce;
import entity.UPO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 * Informacja na dzien dorby co zostalo niewyslane z deklaracji, moze zostac ini
 */
@Named
@ViewScoped
public class InfoViewAll implements Serializable {
private static final long serialVersionUID = 1L;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private UPODAO upodao;
    @Inject
    PodatnikDAO podatnikDAO;
    @Inject
    private WpisView wpisView;

    /**
     * Zmienne
     */
    private List<String> deklaracjeniewyslane;
    private List<String> deklaracjeniebezupo;
    private List<String> kliencinieruszeni;

    
    public InfoViewAll() {
        this.deklaracjeniewyslane = new ArrayList<>();
        this.deklaracjeniebezupo = new ArrayList<>();
    }

    @PostConstruct
    private void init() { //E.m(this);
        Calendar c = Calendar.getInstance();
        String rokdzisiejszy = null;
        String mcdzisiejszy = null;
        if (c.get(c.MONTH) == 0) {
            rokdzisiejszy = String.valueOf(c.get(c.YEAR) - 1);
            mcdzisiejszy = Mce.getNumberToMiesiac().get(12);
        } else {
            rokdzisiejszy = String.valueOf(c.get(c.YEAR));
            mcdzisiejszy = Mce.getNumberToMiesiac().get(c.get(c.MONTH));
        }
        try {
//            List<Deklaracjevat> deklaracje = deklaracjevatDAO.findDeklaracjewysylka(rokdzisiejszy, mcdzisiejszy);
//            for (Deklaracjevat p : deklaracje) {
//                if(p.getIdentyfikator().isEmpty() && p.getSporzadzil()!= null && p.getSporzadzil().equals(sporzadzil)){
//                    deklaracjeniewyslane.add(p.getPodatnik());
//                }
//                if(p.getStatus().startsWith("3") && p.getSporzadzil()!= null && p.getSporzadzil().equals(sporzadzil)){
//                    deklaracjeniebezupo.add(p.get);
//                }
//            }
            List<UPO> deklaracje = upodao.findUPORokMc(rokdzisiejszy, mcdzisiejszy);
            for (UPO p : deklaracje) {
//                if(p.getIdentyfikator().isEmpty() && p.getSporzadzil()!= null && p.getSporzadzil().equals(sporzadzil)){
//                    deklaracjeniewyslane.add(p.getPodatnik());
//                }
                if(p.getCode()!=200 && p.getWprowadzil().equals(wpisView.getUzer())){
                    deklaracjeniebezupo.add(p.getPodatnik().getPrintnazwa());
                }
            }
        } catch (Exception e) {}
        /**
         * Klienci nie ruszeni zajmuja duzo czasu
         *
         */
        //int day = c.get(Calendar.DAY_OF_MONTH);
//        List<Podatnik> tmp = podatnikDAO.findAll();
//        kliencinieruszeni = Collections.synchronizedList(new ArrayList<>());
//        for(Podatnik p : tmp){
//            Integer dok = Integer.parseInt(dokDAO.iledokumentowklienta(p.getNazwapelna(), rokdzisiejszy, mcdzisiejszy).toString());
//            if(dok == 0){
//                if(day>14&&day<25){
//                    kliencinieruszeni.add(p.getNazwapelna());
//                }
//            }
//        }
//        error.E.s(kliencinieruszeni);
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public List<String> getDeklaracjeniewyslane() {
        return deklaracjeniewyslane;
    }
      
    public List<String> getDeklaracjeniebezupo() {
        return deklaracjeniebezupo;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
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
//</editor-fold>

    public static void main(String[] args) {
        Calendar c = Calendar.getInstance();
        int miesica = c.get(Calendar.MONTH);
        int year = c.get(Calendar.YEAR);
        String mc = Mce.getNumberToMiesiac().get(miesica);
        String rok = String.valueOf(Calendar.getInstance().get(year));
    }

}
