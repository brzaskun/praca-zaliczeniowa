/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Podatnikcomparator;
import comparator.Uzcomparator;
import dao.PodatnikDAO;
import dao.UzDAO;
import entity.Podatnik;
import entity.Uz;
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PodatnikKsiegowaShortView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Podatnik> listapodatnikow;
    private List<Uz> listaksiegowych;
    private Uz wybrany;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private UzDAO uzDAO;
    
    @PostConstruct
    public void init() { //E.m(this);
        listapodatnikow = podatnikDAO.findAktywny();
        Collections.sort(listapodatnikow, new Podatnikcomparator());
        listaksiegowych = uzDAO.findByUprawnienia("Bookkeeper");
        listaksiegowych.addAll(uzDAO.findByUprawnienia("BookkeeperFK"));
        Collections.sort(listaksiegowych, new Uzcomparator());
    }
    
    public void init2() { //E.m(this);
        if (wybrany!=null) {
            listapodatnikow = podatnikDAO.findByKsiegowa(wybrany);
            Collections.sort(listapodatnikow, new Podatnikcomparator());
            Msg.msg("Pobrano klientów wg księgowej");
        } else {
            listapodatnikow = podatnikDAO.findAktywny();
            Collections.sort(listapodatnikow, new Podatnikcomparator());
            Msg.msg("Pobrano wszystkich klientów");
        }
    }
    
    public void zachowaj() {
        try {
            uzDAO.editList(listaksiegowych);
            Msg.msg("Zaksiegowano zmiany księgowych");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd, nie naniesiono zmian księgowych");
        }
    }
    
    public void zapisz() {
        try {
            podatnikDAO.editList(listapodatnikow);
            Msg.msg("Zaksiegowano zmiany w ustawieniach podatników");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd, nie naniesiono zmian.");
        }
    }
    
    public void edytujpodatnika(Podatnik podatnik) {
        try {
            podatnikDAO.edit(podatnik);
            Msg.msg("Zmieniono aktywacje podatnika");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd, nie naniesiono zmian.");
        }
    }
    
    public List<Podatnik> getListapodatnikow() {
        return listapodatnikow;
    }

    public void setListapodatnikow(List<Podatnik> listapodatnikow) {
        this.listapodatnikow = listapodatnikow;
    }

    public List<Uz> getListaksiegowych() {
        return listaksiegowych;
    }

    public void setListaksiegowych(List<Uz> listaksiegowych) {
        this.listaksiegowych = listaksiegowych;
    }

    public Uz getWybrany() {
        return wybrany;
    }

    public void setWybrany(Uz wybrany) {
        this.wybrany = wybrany;
    }

    
    
}
