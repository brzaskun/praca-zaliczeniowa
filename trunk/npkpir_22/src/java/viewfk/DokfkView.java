/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.DokDAOfk;
import embeddablefk.FKWiersz;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokfkView implements Serializable{
    
    private Dokfk selected;
    private int liczbawierszy;
    private List<FKWiersz> wiersze;
    @Inject private DokDAOfk dokDAOfk;
    private List<Dokfk> wykaz;

    public DokfkView() {
        liczbawierszy = 1;
        selected = new Dokfk();
        wiersze = new ArrayList<>();
        wiersze.add(new FKWiersz(1,0));
        selected.setKonta(wiersze);
        wykaz = new ArrayList<>();
    }
    
    @PostConstruct
    private void init(){
        wykaz = dokDAOfk.findAll();
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
   
    
    
    public void dodaj(){
        try {
            dokDAOfk.dodaj(selected);
            Msg.msg("i", "Dokument dodany");
        } catch (Exception e){
            Msg.msg("e", "Nie udało się dodac dokumentu");
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

    public Dokfk getSelected() {
        return selected;
    }

    public void setSelected(Dokfk selected) {
        this.selected = selected;
    }

    public List<Dokfk> getWykaz() {
        return wykaz;
    }

    public void setWykaz(List<Dokfk> wykaz) {
        this.wykaz = wykaz;
    }
  
    
    
}
