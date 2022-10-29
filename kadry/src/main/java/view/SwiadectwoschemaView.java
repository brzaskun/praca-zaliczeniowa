/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Rodzajnieobecnoscicomparator;
import dao.NieobecnoscswiadectwoschemaFacade;
import dao.RodzajnieobecnosciFacade;
import dao.SwiadectwoschemaFacade;
import entity.Nieobecnoscswiadectwoschema;
import entity.Rodzajnieobecnosci;
import entity.Swiadectwoschema;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class SwiadectwoschemaView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private SwiadectwoschemaFacade swiadectwoschemaFacade;
    @Inject
    private NieobecnoscswiadectwoschemaFacade nieobecnoscswiadectwoschemaFacade;
    @Inject
    private RodzajnieobecnosciFacade rodzajnieobecnosciFacade;
    private List<Swiadectwoschema> listaswiadectwoschemy;
    private List<Rodzajnieobecnosci> listanieobecnosci;
    private List<Nieobecnoscswiadectwoschema> listanieob;
    @Inject
    private Swiadectwoschema selected;
    @Inject
    private Nieobecnoscswiadectwoschema selectednieobecnoscschema;

    
    @PostConstruct
    public void init() {
        listaswiadectwoschemy = swiadectwoschemaFacade.findAll();
        listanieobecnosci = rodzajnieobecnosciFacade.findAll();
        Collections.sort(listanieobecnosci, new Rodzajnieobecnoscicomparator());
        listanieob = nieobecnoscswiadectwoschemaFacade.findAll();
    }
    
    public void dodajrok() {
        if (selected!=null && selected.getId()==null) {
            swiadectwoschemaFacade.create(selected);
            if (listaswiadectwoschemy==null) {
                listaswiadectwoschemy = new ArrayList<>();
            }
            listaswiadectwoschemy.add(selected);
            selected = new Swiadectwoschema();
            Msg.msg("Dodano scheme świadectwa");
        } else if (selected!=null && selected.getId()!=null) {
            swiadectwoschemaFacade.edit(selected);
            selected = new Swiadectwoschema();
            Msg.msg("Zmieniono schemę świadectwa");
        }
    }
    
    public void dodaj() {
        if (selectednieobecnoscschema!=null && selectednieobecnoscschema.getId()==null) {
            nieobecnoscswiadectwoschemaFacade.create(selectednieobecnoscschema);
            if (listanieob==null) {
                listanieob = new ArrayList<>();
            }
            listanieob.add(selectednieobecnoscschema);
            selected = new Swiadectwoschema();
            selectednieobecnoscschema = new Nieobecnoscswiadectwoschema();
            Msg.msg("Dodano scheme świadectwa i nieobescnosc");
        } else if (selectednieobecnoscschema!=null && selectednieobecnoscschema.getId()!=null) {
            nieobecnoscswiadectwoschemaFacade.edit(selectednieobecnoscschema);
            selected = new Swiadectwoschema();
            selectednieobecnoscschema = new Nieobecnoscswiadectwoschema();
            Msg.msg("Zmieniono schemę świadectwa i nieobescnosc");
        }
    }

    public void usun(Nieobecnoscswiadectwoschema item) {
        if (item!=null) {
            nieobecnoscswiadectwoschemaFacade.remove(item);
            listanieob.remove(item);
            Msg.msg("Usunięto pozycję");
        } else {
            Msg.msg("e","Nie wybrano pozycji");
        }
    }
    
    public List<Swiadectwoschema> getListaswiadectwoschemy() {
        return listaswiadectwoschemy;
    }

    public void setListaswiadectwoschemy(List<Swiadectwoschema> listaswiadectwoschemy) {
        this.listaswiadectwoschemy = listaswiadectwoschemy;
    }

    public List<Rodzajnieobecnosci> getListanieobecnosci() {
        return listanieobecnosci;
    }

    public void setListanieobecnosci(List<Rodzajnieobecnosci> listanieobecnosci) {
        this.listanieobecnosci = listanieobecnosci;
    }

    public List<Nieobecnoscswiadectwoschema> getListanieob() {
        return listanieob;
    }

    public void setListanieob(List<Nieobecnoscswiadectwoschema> listanieob) {
        this.listanieob = listanieob;
    }

    public Swiadectwoschema getSelected() {
        return selected;
    }

    public void setSelected(Swiadectwoschema selected) {
        this.selected = selected;
    }

    public NieobecnoscswiadectwoschemaFacade getNieobecnoscswiadectwoschemaFacade() {
        return nieobecnoscswiadectwoschemaFacade;
    }

    public void setNieobecnoscswiadectwoschemaFacade(NieobecnoscswiadectwoschemaFacade nieobecnoscswiadectwoschemaFacade) {
        this.nieobecnoscswiadectwoschemaFacade = nieobecnoscswiadectwoschemaFacade;
    }

    public Nieobecnoscswiadectwoschema getSelectednieobecnoscschema() {
        return selectednieobecnoscschema;
    }

    public void setSelectednieobecnoscschema(Nieobecnoscswiadectwoschema selectednieobecnoscschema) {
        this.selectednieobecnoscschema = selectednieobecnoscschema;
    }

   
      
    
    
}
