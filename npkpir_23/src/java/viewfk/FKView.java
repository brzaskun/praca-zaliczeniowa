/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import embeddablefk.FKWiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class FKView implements Serializable{
    private int liczbawierszy;
    private List<FKWiersz> wiersze;

    public FKView() {
        liczbawierszy = 1;
        wiersze = new ArrayList<>();
        wiersze.add(new FKWiersz(1,0));
    }
    
    public void liczbaw() {
        Double pierwsze = wiersze.get(liczbawierszy-1).getKwotaWn();
        Double drugie = wiersze.get(liczbawierszy-1).getKwotaMa();
        if(pierwsze!=null||drugie!=null){
            liczbawierszy++;
            wiersze.add(new FKWiersz(liczbawierszy,0));
        }
    }
    
   /**
    * Usuwanie wierszy z dokumenu ksiegowego
    */
    public void liczbawu() {
        if(liczbawierszy>1){
            liczbawierszy--;
            wiersze.remove(liczbawierszy);
        }
    }
   

    public int getLiczbawierszy() {
        return liczbawierszy;
    }

    public void setLiczbawierszy(int liczbawierszy) {
        this.liczbawierszy = liczbawierszy;
    }

    public List<FKWiersz> getWiersze() {
        return wiersze;
    }

    public void setWiersze(List<FKWiersz> wiersze) {
        this.wiersze = wiersze;
    }
  
    
}
