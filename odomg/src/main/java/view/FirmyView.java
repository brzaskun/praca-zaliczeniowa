/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.ZakladpracyFacade;
import entity.Zakladpracy;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class FirmyView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private Zakladpracy zakladpracy;
    private Zakladpracy zakladpracyselected;
    private List<Zakladpracy> zakladpracylista;
    @Inject
    private ZakladpracyFacade zakladpracyFacade;
    @Inject
    private Zakladpracy nowy;
    private Zakladpracy nowy_arch;
    
    @PostConstruct
    private void init() {
        zakladpracylista = zakladpracyFacade.findAll();
    }
    
     public void dodaj() {
        if (nowy!=null&&nowy.getNazwazakladu()!=null) {
            try {
                zakladpracyFacade.create(nowy);
                //nowy.setUtworzony(new Date());
                nowy_arch = new Zakladpracy(nowy);
                nowy=new Zakladpracy();
                Msg.msg("Dodano nowego uczestnika");
            } catch (Exception e) {
                Msg.msg("e","Wystąpił błąd, nie dodano nowego uczestnika");
            }
        }
    }

    public void onRowEdit(RowEditEvent<Zakladpracy> event) {
        Msg.msg("Zmieniono dane firmy");
    }

    public void onRowCancel(RowEditEvent<Zakladpracy> event) {
        Msg.msg("w","Niezmieniono danych firmy");
    }

    public Zakladpracy getZakladpracy() {
        return zakladpracy;
    }

    public void setZakladpracy(Zakladpracy zakladpracy) {
        this.zakladpracy = zakladpracy;
    }

    public Zakladpracy getZakladpracyselected() {
        return zakladpracyselected;
    }

    public void setZakladpracyselected(Zakladpracy zakladpracyselected) {
        this.zakladpracyselected = zakladpracyselected;
    }

    public List<Zakladpracy> getZakladpracylista() {
        return zakladpracylista;
    }

    public void setZakladpracylista(List<Zakladpracy> zakladpracylista) {
        this.zakladpracylista = zakladpracylista;
    }

    public Zakladpracy getNowy() {
        return nowy;
    }

    public void setNowy(Zakladpracy nowy) {
        this.nowy = nowy;
    }

    public Zakladpracy getNowy_arch() {
        return nowy_arch;
    }

    public void setNowy_arch(Zakladpracy nowy_arch) {
        this.nowy_arch = nowy_arch;
    }
    
    
    
}
