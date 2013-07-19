/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.DokDAOfk;
import entityfk.Dokfk;
import entityfk.Kontozapisy;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class DokfkPodgladView implements Serializable{
    
    @Inject private Dokfk selected;
    private int liczbawierszy;
    @Inject private Kontozapisy kontozapisy;
    @Inject private DokDAOfk dokDAOfk;
    
    
    public void znajdzdokumentzzapisu(){
        selected = dokDAOfk.findZZapisu(kontozapisy);
        liczbawierszy = selected.getKonta().size();
        RequestContext.getCurrentInstance().update("dialogEdycja");
        RequestContext.getCurrentInstance().update("form");
    }
    
    
    
    public Dokfk getSelected() {
        return selected;
    }

    public void setSelected(Dokfk selected) {
        this.selected = selected;
    }

    public Kontozapisy getKontozapisy() {
        return kontozapisy;
    }

    public void setKontozapisy(Kontozapisy kontozapisy) {
        this.kontozapisy = kontozapisy;
    }

    public int getLiczbawierszy() {
        return liczbawierszy;
    }

    public void setLiczbawierszy(int liczbawierszy) {
        this.liczbawierszy = liczbawierszy;
    }
    
    
    
}
