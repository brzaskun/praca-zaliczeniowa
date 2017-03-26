/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import daoFK.CechazapisuDAOfk;
import entityfk.Cechazapisu;
import entityfk.CharakterCechy;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class CechazapisuView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject private CechazapisuDAOfk cechazapisuDAOfk;
    private List<Cechazapisu> pobranecechy;
    private String nazwacechy;
    private String rodzajcechy;

    public CechazapisuView() {
         E.m(this);
        this.pobranecechy = new ArrayList<>();
    }
    
    
    public void init() {
        pobranecechy = cechazapisuDAOfk.findAll();
    }
    
    public void dodajnowacecha() {
        try {
            Cechazapisu nc = new Cechazapisu(nazwacechy, rodzajcechy);
            cechazapisuDAOfk.dodaj(nc);
            pobranecechy.add(nc);
            Msg.msg("Dodano nową cechę");
        } catch (Exception e) {
            Msg.dPe();
        }
    }
  
    public void usun(Cechazapisu c) {
        try {
            cechazapisuDAOfk.destroy(c);
            pobranecechy.remove(c);
            Msg.msg("Usunięto cechę");
        } catch (Exception e) {
            Msg.dPe();
        }
    }
    
    public String charakter(int num) {
        return CharakterCechy.numtoString(num);
    }
    
      //<editor-fold defaultstate="collapsed" desc="comment">
    public String getNazwacechy() {
        return nazwacechy;
    }

    public void setNazwacechy(String nazwacechy) {
        this.nazwacechy = nazwacechy;
    }

    public String getRodzajcechy() {
        return rodzajcechy;
    }

    public void setRodzajcechy(String rodzajcechy) {
        this.rodzajcechy = rodzajcechy;
    }
       
    public List<Cechazapisu> getPobranecechy() {
        return pobranecechy;
    }
    
    public void setPobranecechy(List<Cechazapisu> pobranecechy) {
        this.pobranecechy = pobranecechy;
    }
//</editor-fold>
    
    
    
}
