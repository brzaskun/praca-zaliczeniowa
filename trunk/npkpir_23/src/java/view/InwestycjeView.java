/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.InwestycjeDAO;
import entity.Dok;
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
    private List<String> inwestycjesymbole;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject private InwestycjeDAO inwestycjeDAO;
    @Inject private Inwestycje selected;
    @Inject private Inwestycje wybrany;


    
    @PostConstruct
    private void init(){
        inwestycje = inwestycjeDAO.findInwestycje(wpisView.getPodatnikWpisu());
        inwestycjesymbole = new ArrayList<>();
        if(inwestycje!=null){
        int i = 1;
        for(Inwestycje p : inwestycje){
            List<Dok> tmp = p.getDokumenty();
            for(Dok r : tmp){
                r.setNrWpkpir(i++);
            }
            p.setDokumenty(tmp);
            inwestycjesymbole.add("wybierz");
            inwestycjesymbole.add(p.getSymbol());
            i=1;
        }
        }
    }

    public void dodaj() {
        try {
            selected.setPodatnik(wpisView.getPodatnikWpisu());
            selected.setSymbol(wpisView.getRokWpisu()+"/"+selected.getSkrot());
            inwestycjeDAO.dodaj(selected);
            selected.setOpis("");
            selected.setSkrot("");
            Msg.msg("i","Dodałem nową inwestycję","form:messages");
            inwestycje = inwestycjeDAO.findInwestycje(wpisView.getPodatnikWpisu());
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd. Nie dodałem nowej inwestycji","form:messages");
        }
    }
    
    public void usun(){
        try{
            if(!wybrany.getDokumenty().isEmpty()){
                Msg.msg("e","Inwestycja zawiera dokumenty! Usuń je najpierw","form:messages");
                throw new Exception();  
            } else {
                inwestycjeDAO.destroy(wybrany);
                inwestycje.remove(wybrany);
                Msg.msg("i","Usunąłem wybrnaą inwestycję","form:messages");
            }
        } catch (Exception e){
            Msg.msg("e","Wystąpił błąd. Nie usunąłem wkazanej inwestycji","form:messages");
        }
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public List<Inwestycje> getInwestycje() {
        return inwestycje;
    }

    public void setInwestycje(List<Inwestycje> inwestycje) {
        this.inwestycje = inwestycje;
    }

    public Inwestycje getSelected() {
        return selected;
    }

    public void setSelected(Inwestycje selected) {
        this.selected = selected;
    }

    public List<String> getInwestycjesymbole() {
        return inwestycjesymbole;
    }

    public void setInwestycjesymbole(List<String> inwestycjesymbole) {
        this.inwestycjesymbole = inwestycjesymbole;
    }

    public Inwestycje getWybrany() {
        return wybrany;
    }

    public void setWybrany(Inwestycje wybrany) {
        this.wybrany = wybrany;
    }

  
}
