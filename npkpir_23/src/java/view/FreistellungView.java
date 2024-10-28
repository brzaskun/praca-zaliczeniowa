/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import comparator.Podatnikcomparator;
import dao.FreistellungDAO;
import dao.PodatnikDAO;
import entity.Freistellung;
import entity.Podatnik;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

@Named
@RequestScoped
public class FreistellungView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private FreistellungDAO freistellungDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    private List<Freistellung> freistellungList;
    private Freistellung newFreistellung;
    private List<Podatnik> podatnikList;
    @Inject
    private WpisView wpisView;

    @PostConstruct
    public void init() {
        freistellungList = freistellungDAO.findByPodatnik(wpisView.getPodatnikObiekt());
        newFreistellung = new Freistellung();
        newFreistellung.setPodatnik(wpisView.getPodatnikObiekt());
        newFreistellung.setRok(wpisView.getRokWpisuSt());
        podatnikList = podatnikDAO.findAktywny();
        Collections.sort(podatnikList, new Podatnikcomparator());
    }

    public List<Freistellung> getFreistellungList() {
        return freistellungList;
    }

    public Freistellung getNewFreistellung() {
        return newFreistellung;
    }

    public void setNewFreistellung(Freistellung newFreistellung) {
        this.newFreistellung = newFreistellung;
    }

    public List<Podatnik> getPodatnikList() {
        return podatnikList;
    }

    public void setPodatnikList(List<Podatnik> podatnikList) {
        this.podatnikList = podatnikList;
    }

    public void createFreistellung() {
        try {
            freistellungDAO.create(newFreistellung);
            freistellungList.add(newFreistellung);
            newFreistellung = new Freistellung(); // Reset the form
            newFreistellung.setPodatnik(wpisView.getPodatnikObiekt());
            newFreistellung.setRok(wpisView.getRokWpisuSt());
            Msg.msg("Dodano Freistellung");
        } catch (Exception e) {
            Msg.msg("e", "Błąd podczas dodawania Freistellung: " + e.getMessage());
        }
    }

    public void deleteFreistellung(Freistellung freistellung) {
        try {
            freistellungDAO.remove(freistellung);
            freistellungList.remove(freistellung);
            Msg.msg("Usunięto Freistellung");
        } catch (Exception e) {
            Msg.msg("e", "Błąd podczas usuwania Freistellung: " + e.getMessage());
        }
    }
}
