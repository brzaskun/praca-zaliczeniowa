/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import dao.DokDAO;
import daoFK.CechazapisuDAOfk;
import entity.Dok;
import entityfk.Cechazapisu;
import entityfk.CharakterCechy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
public class CechazapisuView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject private CechazapisuDAOfk cechazapisuDAOfk;
    private List<Cechazapisu> pobranecechy;
    private List<Cechazapisu> pobranecechyfiltered;
    @Inject
    private Cechazapisu selected;

    public CechazapisuView() {
        //E.m(this);
        this.pobranecechy = Collections.synchronizedList(new ArrayList<>());
    }
    
    @PostConstruct
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
            if (c.getNazwacechy().equals("KUPMN") || c.getNazwacechy().equals("NKUP") || c.getNazwacechy().equals("NPUP")|| c.getNazwacechy().equals("PMN")) {
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
    
    @Inject
    private DokDAO dokDAO;
    
    public void nadajid() {
        List<Dok> wszystkie = dokDAO.findAll();
        List<Cechazapisu> cechy = cechazapisuDAOfk.findAll();
        HashMap<String, Cechazapisu> cechymapa = stworzmape(cechy);
        for (Dok p : wszystkie) {
            p.getCechadokumentuLista().forEach((r)->{
                r.setId(cechymapa.get(r.getNazwacechy()).getId());
            });
        }
        cechazapisuDAOfk.editList(wszystkie);
    }
    
    private HashMap<String, Cechazapisu> stworzmape(List<Cechazapisu> cechy) {
        HashMap<String, Cechazapisu> mapa = new HashMap<>();
        cechy.forEach((p) -> {
            mapa.put(p.getNazwacechy(), p);
        });
        return mapa;
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

    public List<Cechazapisu> getPobranecechyfiltered() {
        return pobranecechyfiltered;
    }

    public void setPobranecechyfiltered(List<Cechazapisu> pobranecechyfiltered) {
        this.pobranecechyfiltered = pobranecechyfiltered;
    }
   
       
    public List<Cechazapisu> getPobranecechy() {
        return pobranecechy;
    }
    
    public void setPobranecechy(List<Cechazapisu> pobranecechy) {
        this.pobranecechy = pobranecechy;
    }
//</editor-fold>

    
    
    
    
}
