/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.WpisDAO;
import entity.Uz;
import entity.Wpis;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean(name="WpisView")
@RequestScoped
public class WpisView implements Serializable{

    private static String podatnikWpisu;
    private static Integer rokWpisu;
    private static String miesiacWpisu;
    private static Uz wprowadzil;
    private static String miesiacOd;
    private static String miesiacDo;
    private boolean srodkTrw;
    
    @Inject private Wpis wpis;
    @Inject private WpisDAO wpisDAO;

    
    @PostConstruct
    private void init(){
        if(miesiacDo==null&&miesiacWpisu!=null){
            miesiacDo = miesiacWpisu;
            miesiacOd = miesiacWpisu;
        }
    }

    public void wpisAktualizuj(){
        findWpis();
    }
    
    private void findWpis(){
        wpis = wpisDAO.find(wprowadzil.getLogin());
        wpis.setPodatnikWpisu(podatnikWpisu);
        wpis.setMiesiacWpisu(miesiacWpisu);
        wpis.setRokWpisu(rokWpisu);
        wpisDAO.edit(wpis);
    }
    
    public String getPodatnikWpisu() {
        return podatnikWpisu;
    }

    public static String getPodatnikWpisuS() {
        return podatnikWpisu;
    }
     
    public void setPodatnikWpisu(String podatnikWpisu) {
        WpisView.podatnikWpisu = podatnikWpisu;
    }

    public static void setPodatnikWpisuS(String podatnikWpisu) {
        WpisView.podatnikWpisu = podatnikWpisu;
    }
    
    public Integer getRokWpisu() {
        return rokWpisu;
    }

    public void setRokWpisu(Integer rokWpisu) {
        WpisView.rokWpisu = rokWpisu;
    }

    public String getMiesiacWpisu() {
        return miesiacWpisu;
    }

    public void setMiesiacWpisu(String miesiacWpisu) {
        WpisView.miesiacWpisu = miesiacWpisu;
    }

    public Uz getWprowadzil() {
        return wprowadzil;
    }

    public void setWprowadzil(Uz wprowadzil) {
        WpisView.wprowadzil = wprowadzil;
    }

    public String getMiesiacOd() {
        return miesiacOd;
    }

    public void setMiesiacOd(String miesiacOd) {
        WpisView.miesiacOd = miesiacOd;
    }

    public String getMiesiacDo() {
        return miesiacDo;
    }

    public void setMiesiacDo(String miesiacDo) {
        WpisView.miesiacDo = miesiacDo;
    }

    public boolean isSrodkTrw() {
        return srodkTrw;
    }

    public void setSrodkTrw(boolean srodkTrw) {
        this.srodkTrw = srodkTrw;
    }
}
