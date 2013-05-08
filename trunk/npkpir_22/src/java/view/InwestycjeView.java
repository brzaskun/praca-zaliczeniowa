/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.InwestycjeDAO;
import entity.Inwestycje;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class InwestycjeView implements Serializable{
    
    private List<Inwestycje> inwestycje;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject private InwestycjeDAO inwestycjeDAO;
    @Inject private Inwestycje selected;

    
    @PostConstruct
    private void init(){
        inwestycje = inwestycjeDAO.findInwestycje(wpisView.getPodatnikWpisu());
    }

    public void dodaj() {
        try {
            selected.getInwestycjePK().setPodatnik(wpisView.getPodatnikWpisu());
            selected.getInwestycjePK().setSymbol(wpisView.getRokWpisu()+"/"+selected.getSkrot());
            inwestycjeDAO.dodaj(selected);
            Msg.msg("i","Dodałem nową inwestycję","form:messages");
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd. Nie dodałem nowej inwestycji","form:messages");
        }
    }
    
    
    
    public List<Inwestycje> getInwestycje() {
        return inwestycje;
    }

    public void setInwestycje(List<Inwestycje> inwestycje) {
        this.inwestycje = inwestycje;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Inwestycje getSelected() {
        return selected;
    }

    public void setSelected(Inwestycje selected) {
        this.selected = selected;
    }
    
    
}
