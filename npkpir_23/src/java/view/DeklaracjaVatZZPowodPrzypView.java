/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjaVatZZDAO;
import dao.DeklaracjaVatZZPowodDAO;
import entity.DeklaracjaVatZZ;
import entity.DeklaracjaVatZZPowod;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.event.TransferEvent;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DeklaracjaVatZZPowodPrzypView  implements Serializable {
    @Inject
    private DeklaracjaVatZZPowodDAO deklaracjaVatZZPowodDAO;
    @Inject
    private DeklaracjaVatZZDAO deklaracjaVatZZDAO;
    private List<DeklaracjaVatZZPowod> deklracjaVatZZpowody;
    private List<DeklaracjaVatZZPowod> powodysource;
    private List<DeklaracjaVatZZPowod> powodytarget;
    private DualListModel<DeklaracjaVatZZPowod> picklista;
    private List<DeklaracjaVatZZ> zalaczniki;
    @Inject
    private DeklaracjaVatZZPowod nowypowod;
    @Inject
    private DeklaracjaVatZZ wybranyzalacznik;
    
    @PostConstruct
    private void init() {
        deklracjaVatZZpowody = deklaracjaVatZZPowodDAO.findAll();
        zalaczniki = deklaracjaVatZZDAO.findAll();
        powodysource = deklaracjaVatZZPowodDAO.findAll();
        powodytarget = new ArrayList<>();
        picklista = new DualListModel<>(powodysource, powodytarget);
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
    
    public void pobierzprzyporzadkowanie() {
        powodysource = deklaracjaVatZZPowodDAO.findAll();
        powodytarget = wybranyzalacznik.getPowody();
        for (DeklaracjaVatZZPowod p : powodytarget) {
            if (powodysource.contains(p)) {
                powodysource.remove(p);
            }
        }
        picklista = new DualListModel<>(powodysource, powodytarget);
    }

    public void naniesprzyporzadkowanie(TransferEvent event) {
        boolean dodawanie = event.isAdd();
        List<DeklaracjaVatZZPowod> powody = (List<DeklaracjaVatZZPowod>) event.getItems();
        for (DeklaracjaVatZZPowod p : powody) {
            if (dodawanie) {
                wybranyzalacznik.getPowody().add(p);
                p.getVatzzty().add(wybranyzalacznik);
            } else {
                wybranyzalacznik.getPowody().remove(p);
                p.getVatzzty().remove(wybranyzalacznik);
            }
        }
        deklaracjaVatZZDAO.edit(wybranyzalacznik);
        Msg.msg("Zachowano przyporządkowanie");
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

    public DualListModel<DeklaracjaVatZZPowod> getPicklista() {
        return picklista;
    }

    public void setPicklista(DualListModel<DeklaracjaVatZZPowod> picklista) {
        this.picklista = picklista;
    }

    public DeklaracjaVatZZ getWybranyzalacznik() {
        return wybranyzalacznik;
    }

    public void setWybranyzalacznik(DeklaracjaVatZZ wybranyzalacznik) {
        this.wybranyzalacznik = wybranyzalacznik;
    }

    public List<DeklaracjaVatZZ> getZalaczniki() {
        return zalaczniki;
    }

    public void setZalaczniki(List<DeklaracjaVatZZ> zalaczniki) {
        this.zalaczniki = zalaczniki;
    }

    public List<DeklaracjaVatZZPowod> getPowodysource() {
        return powodysource;
    }

    public void setPowodysource(List<DeklaracjaVatZZPowod> powodysource) {
        this.powodysource = powodysource;
    }

    
    
}
