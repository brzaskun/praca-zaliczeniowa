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
public class PodatnikKsiegowaView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<Podatnik> listapodatnikow;
    private List<Uz> listaksiegowych;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private UzDAO uzDAO;
    
    @PostConstruct
    private void init() {
        listapodatnikow = podatnikDAO.findAll();
        Collections.sort(listapodatnikow, new Podatnikcomparator());
        listaksiegowych = uzDAO.findByUprawnienia("Bookkeeper");
        listaksiegowych.addAll(uzDAO.findByUprawnienia("BookkeeperFK"));
        Collections.sort(listaksiegowych, new Uzcomparator());
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
    
    
}
