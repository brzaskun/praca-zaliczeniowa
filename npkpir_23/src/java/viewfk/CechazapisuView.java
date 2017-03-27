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
    @Inject
    private Cechazapisu selected;

    public CechazapisuView() {
        E.m(this);
        this.pobranecechy = new ArrayList<>();
    }
    
    
    public void init() {
        pobranecechy = cechazapisuDAOfk.findAll();
    }
    
    public void dodajnowacecha() {
        try {
            cechazapisuDAOfk.edit(selected);
            if (!pobranecechy.contains(selected)) {
                pobranecechy.add(selected);
                Msg.msg("Dodano nową cechę");
            } else {
                Msg.msg("Wyedytowano wskazaną cechę");
            }
            selected = new Cechazapisu();
        } catch (Exception e) {
            Msg.dPe();
        }
    }
  
    public void usun(Cechazapisu c) {
        try {
            if (c.getCechazapisuPK().getNazwacechy().equals("KUPMN") || c.getCechazapisuPK().getNazwacechy().equals("NKUP") || c.getCechazapisuPK().getNazwacechy().equals("NPUP")|| c.getCechazapisuPK().getNazwacechy().equals("PMN")) {
                Msg.msg("e", "Nie można usunąć cech podstawowych");
                return;
            } else {
                cechazapisuDAOfk.destroy(c);
                pobranecechy.remove(c);
                Msg.msg("Usunięto cechę");
            }
        } catch (Exception e) {
            Msg.dPe();
        }
    }
    
    public void edit(Cechazapisu c) {
        try {
            selected = c;
            Msg.msg("Wybrano cechę do edycji");
        } catch (Exception e) {
            Msg.dPe();
        }
    }
//<editor-fold defaultstate="collapsed" desc="comment">    
    public String charakter(int num) {
        return CharakterCechy.numtoString(num);
    }
    
    public String przesuniecie(int num) {
        return CharakterCechy.przesuniecie(num);
    }
    

    public Cechazapisu getSelected() {
        return selected;
    }

    public void setSelected(Cechazapisu selected) {
        this.selected = selected;
    }
   
       
    public List<Cechazapisu> getPobranecechy() {
        return pobranecechy;
    }
    
    public void setPobranecechy(List<Cechazapisu> pobranecechy) {
        this.pobranecechy = pobranecechy;
    }
//</editor-fold>
    
    
    
}
