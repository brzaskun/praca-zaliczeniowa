/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Pismoadmin;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class PismoAdminView implements Serializable{
    private Pismoadmin pismoadmin;
    
    private static final List<String> listamenu;
    private static final List<String> waznosc;
    
    static {
        listamenu = new ArrayList<>();
        listamenu.add("zmiana podatnika");
        listamenu.add("info podatnik");
        listamenu.add("wpisywanie");
        listamenu.add("wykaz dokumentów");
        listamenu.add("podatkowa ksiega");
        listamenu.add("faktury miesieczne");
        listamenu.add("niezaplacone");
        listamenu.add("zaplacone");
        listamenu.add("obroty w roku");
        listamenu.add("kontrahenci fakt.");
        listamenu.add("zestaw. sumaryczne");
        listamenu.add("środki trwałe");
        listamenu.add("pity");
        listamenu.add("ewidencje vat");
        listamenu.add("zamknięcie miesiąca");
        listamenu.add("faktury - panel");
        listamenu.add("napisz do admina");
        waznosc = new ArrayList<>();
        waznosc.add("pali się");
        waznosc.add("ważne");
        waznosc.add("wypada naprawić");
        waznosc.add("takie tam");
        waznosc.add("nie wiem po co to zgłaszam");
    }
    
    public void molestujadmina() {
        
    }

    public PismoAdminView() {
    }

    public Pismoadmin getPismoadmin() {
        return pismoadmin;
    }

    public void setPismoadmin(Pismoadmin pismoadmin) {
        this.pismoadmin = pismoadmin;
    }

    public List<String> getListamenu() {
        return listamenu;
    }

    public List<String> getWaznosc() {
        return waznosc;
    }

    
   
    
    
}
