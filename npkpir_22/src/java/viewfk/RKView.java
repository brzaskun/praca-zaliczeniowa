/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import embeddable.KwotaKolumna;
import embeddablefk.RKWiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RKView implements Serializable{
    private int liczbawierszy;
    private static List<RKWiersz> wiersze;

    public RKView() {
        liczbawierszy = 1;
        wiersze = new ArrayList<>();
        wiersze.add(new RKWiersz(1));
    }
    
    public void liczbaw() {
        if(wiersze.get(liczbawierszy-1).getKwotaWn()!=null||wiersze.get(liczbawierszy-1).getKwotaMa()!=null){
            liczbawierszy++;
         wiersze.add(new RKWiersz(liczbawierszy));
        }
    }

    public int getLiczbawierszy() {
        return liczbawierszy;
    }

    public void setLiczbawierszy(int liczbawierszy) {
        this.liczbawierszy = liczbawierszy;
    }

    public List<RKWiersz> getWiersze() {
        return wiersze;
    }

    public void setWiersze(List<RKWiersz> wiersze) {
        this.wiersze = wiersze;
    }
  
    
}
