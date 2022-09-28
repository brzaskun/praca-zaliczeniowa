/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.GrupyupowaznienFacade;
import dao.UczestnicyFacade;
import dao.UczestnikGrupyFacade;
import dao.ZakladpracyFacade;
import entity.Grupyupowaznien;
import entity.Uczestnicy;
import entity.UczestnikGrupa;
import entity.Uczestnikgrupy;
import entity.Zakladpracy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
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
public class UpgrupyView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private GrupyupowaznienFacade grupyupowaznienFacade;
    @Inject
    private UczestnikGrupyFacade uczestnikGrupyFacade;
    @Inject
    private UczestnicyFacade uczestnicyFacade;
    @Inject
    private ZakladpracyFacade zakladpracyFacade;
    private Zakladpracy zakladpracy;
    private List<Zakladpracy> zakladpracylista;
    private List<Uczestnicy> listaosoby;
    private List<Uczestnicy> listaosobyselected;
    private List<Uczestnikgrupy> uczestnikgrupylistaall;
    
    @PostConstruct
    private void init() {
        zakladpracylista = zakladpracyFacade.findAll();
        uczestnikgrupylistaall = uczestnikGrupyFacade.findAll();
    }
    
    public void generujosoby() {
        if (zakladpracy!=null) {
            listaosoby  = uczestnicyFacade.findByFirmaId(zakladpracy);
            List<Grupyupowaznien> grupyfirma = grupyupowaznienFacade.findById(zakladpracy.getId());
            if (grupyfirma!=null) {
                for (Uczestnicy p :listaosoby) {
                    p.setUczestnikgrupy(new ArrayList<UczestnikGrupa>());
                    List<Uczestnikgrupy> uczestnikgrupylista = uczestnikgrupylistaall.stream().filter(c->c.getIdUczestnik()!=null&&c.getIdUczestnik().equals(p.getId())).collect(Collectors.toList());
                    for (Grupyupowaznien r : grupyfirma) {
                        boolean dodac = true;
                        for (Uczestnikgrupy s : uczestnikgrupylista) {
                            if (s.getGrupa().equals(r.getNazwagrupy())) {
                                p.getUczestnikgrupy().add(new UczestnikGrupa(r.getNazwagrupy(), true));
                                dodac = false;
                                break;
                            }
                        }
                        if (dodac) {
                            p.getUczestnikgrupy().add(new UczestnikGrupa(r.getNazwagrupy(), false));
                        }
                    }
                }
            }
            Msg.msg("generuje osoby");
        }
    }

    public void onRowEdit(RowEditEvent<Uczestnicy> event) {
        Msg.msg("Zmieniono dane użytkownika");
    }

    public void onRowCancel(RowEditEvent<Uczestnicy> event) {
        Msg.msg("w","Niezmieniono danych użytkownika");
    }
    
    public Zakladpracy getZakladpracy() {
        return zakladpracy;
    }

    public void setZakladpracy(Zakladpracy zakladpracy) {
        this.zakladpracy = zakladpracy;
    }

    public List<Zakladpracy> getZakladpracylista() {
        return zakladpracylista;
    }

    public void setZakladpracylista(List<Zakladpracy> zakladpracylista) {
        this.zakladpracylista = zakladpracylista;
    }

    public List<Uczestnicy> getListaosoby() {
        return listaosoby;
    }

    public void setListaosoby(List<Uczestnicy> listaosoby) {
        this.listaosoby = listaosoby;
    }

    public List<Uczestnicy> getListaosobyselected() {
        return listaosobyselected;
    }

    public void setListaosobyselected(List<Uczestnicy> listaosobyselected) {
        this.listaosobyselected = listaosobyselected;
    }
    
    
    
    
}
