/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import embeddable.KwotaKolumna;
import embeddablefk.FKWiersz;
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
public class FKView implements Serializable{
    private int liczbawierszy;
    private static List<FKWiersz> wiersze;

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
