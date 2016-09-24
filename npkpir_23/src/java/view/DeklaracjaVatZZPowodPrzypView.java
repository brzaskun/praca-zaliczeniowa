/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjaVatZZPowodDAO;
import entity.DeklaracjaVatZZPowod;
import error.E;
import java.io.Serializable;
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
public class DeklaracjaVatZZPowodPrzypView  implements Serializable {
    @Inject
    private DeklaracjaVatZZPowodDAO deklaracjaVatZZPowodDAO;
    private List<DeklaracjaVatZZPowod> deklracjaVatZZpowody;
    @Inject
    private DeklaracjaVatZZPowod nowypowod;
    
    @PostConstruct
    private void init() {
        deklracjaVatZZpowody = deklaracjaVatZZPowodDAO.findAll();
    }
    
    public void dodaj() {
        try {
            deklaracjaVatZZPowodDAO.dodaj(nowypowod);
            deklracjaVatZZpowody.add(nowypowod);
            nowypowod = new DeklaracjaVatZZPowod();
            Msg.msg("Dodano powód");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił błąd, nie dodano powodu");
        }
    }
    
    public void edytuj() {
        deklaracjaVatZZPowodDAO.edit(nowypowod);
        Msg.msg("Wyedytowano powód");
    }
    
    public void usun(DeklaracjaVatZZPowod p) {
        deklaracjaVatZZPowodDAO.destroy(p);
        deklracjaVatZZpowody.remove(p);
        Msg.msg("Usunięto powód");
    }

    public List<DeklaracjaVatZZPowod> getDeklracjaVatZZpowody() {
        return deklracjaVatZZpowody;
    }

    public void setDeklracjaVatZZpowody(List<DeklaracjaVatZZPowod> deklracjaVatZZpowody) {
        this.deklracjaVatZZpowody = deklracjaVatZZpowody;
    }

    public DeklaracjaVatZZPowod getNowypowod() {
        return nowypowod;
    }

    public void setNowypowod(DeklaracjaVatZZPowod nowypowod) {
        this.nowypowod = nowypowod;
    }
    
    
}
