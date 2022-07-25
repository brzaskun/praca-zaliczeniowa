/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.RodzajCzlonkostwaDAO;
import entityfk.RodzajCzlonkostwa;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import msg.Msg;
/**
 *
 * @author Osito
 */
@Named
@RequestScoped
public class RodzajeCzlonkostwaView implements Serializable {
    @Inject
    private RodzajCzlonkostwa rodzajCzlonkostwa;
    private List<RodzajCzlonkostwa> rodzajCzlonkostwaLista;
    @Inject
    private RodzajCzlonkostwaDAO rodzajCzlonkostwaDAO;
    boolean zapisz0edytuj1;
    
    @PostConstruct
    private void init() { //E.m(this);
        rodzajCzlonkostwaLista = rodzajCzlonkostwaDAO.findAll();
    }
    public void pobierz() {
        init();
    }
    public void dodaj() {
        try {
            rodzajCzlonkostwaDAO.create(rodzajCzlonkostwa);
            rodzajCzlonkostwaLista.add(rodzajCzlonkostwa);
            rodzajCzlonkostwa = new RodzajCzlonkostwa();
            Msg.msg("Nanieniono nowy rodzaj członkostwa");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił bład. Sprawdź czy nazwa rodzaju członkostwa/skrót nazwy sie nie powtarza");
        }
    }
    public void edytuj() {
        try {
            rodzajCzlonkostwaDAO.edit(rodzajCzlonkostwa);
            rodzajCzlonkostwa = new RodzajCzlonkostwa();
            zapisz0edytuj1= false;
            Msg.msg("Zmieniono pozycję");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił bład. Sprawdź czy nazwa rodzaju członkostwa/skrót nazwy sie nie powtarza");
        }
    }
    
    public void edytuj(RodzajCzlonkostwa p) {        
        rodzajCzlonkostwa = p;
        zapisz0edytuj1 = true;
        Msg.msg("Wybrano pozycję");
    }
    
    public void usun(RodzajCzlonkostwa p) {
        try {
            rodzajCzlonkostwaDAO.remove(p);
            rodzajCzlonkostwaLista.remove(p);
            Msg.msg("Usunięto pozycję");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił bład podczas usuwania pozycji");
        }
    }
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public RodzajCzlonkostwa getRodzajCzlonkostwa() {
        return rodzajCzlonkostwa;
    }
    
    public void setRodzajCzlonkostwa(RodzajCzlonkostwa rodzajCzlonkostwa) {
        this.rodzajCzlonkostwa = rodzajCzlonkostwa;
    }

    public boolean isZapisz0edytuj1() {
        return zapisz0edytuj1;
    }

    public void setZapisz0edytuj1(boolean zapisz0edytuj1) {
        this.zapisz0edytuj1 = zapisz0edytuj1;
    }
    
    public List<RodzajCzlonkostwa> getRodzajCzlonkostwaLista() {
        return rodzajCzlonkostwaLista;
    }
    
    public void setRodzajCzlonkostwaLista(List<RodzajCzlonkostwa> rodzajCzlonkostwaLista) {
        this.rodzajCzlonkostwaLista = rodzajCzlonkostwaLista;
    }
    
//</editor-fold>
    
}
