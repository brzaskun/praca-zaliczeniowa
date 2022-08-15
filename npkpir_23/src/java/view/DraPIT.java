/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DraSumyDAO;
import entity.DraSumy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class DraPIT implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<DraSumy> drasumy;
    private DraSumy selected1;
    @Inject
    private WpisView wpisView;
    @Inject
    private DraSumyDAO draSumyDAO;
    
    @PostConstruct
    private void init() {
        pobierz();
    }
    
    public void pobierz() {
        drasumy = new ArrayList<>();
        try {
            drasumy = draSumyDAO.zwrocRokPodatnik(wpisView.getRokWpisuSt(), wpisView.getPodatnikObiekt());
        } catch (Exception e){}
    }
    
    
    public List<DraSumy> getDrasumy() {
        return drasumy;
    }

    public void setDrasumy(List<DraSumy> drasumy) {
        this.drasumy = drasumy;
    }

    public DraSumy getSelected1() {
        return selected1;
    }

    public void setSelected1(DraSumy selected1) {
        this.selected1 = selected1;
    }
    
    
}
