/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaSlownikDAO;
import entity.Fakturaslownik;
import error.E;
import java.io.Serializable;
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
public class FakturaslownikView  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private FakturaSlownikDAO fakturaSlownikDAO;
    private List<Fakturaslownik> lista;
    @Inject
    private Fakturaslownik selected;
  
       
    
    @PostConstruct
    private void init() { //E.m(this);
        lista = fakturaSlownikDAO.findByPodatnik(wpisView.getPodatnikObiekt());
    }
    
    public void dodaj() {
        try {
            selected.setPodatnik(wpisView.getPodatnikObiekt());
            fakturaSlownikDAO.create(selected);
            lista.add(selected);
            selected = new Fakturaslownik();
            Msg.msg("Dodano opis");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("Nie udało się dodać opisu");
        }
    }
    
    public void nanies(Fakturaslownik sprawa) {
        Msg.msg("Odnotowano zmianę statusu");
    }
    
    public void destroy(Fakturaslownik dousuniecia) {
        try {
            fakturaSlownikDAO.remove(dousuniecia);
            lista.remove(dousuniecia);
            Msg.msg("Usunięto opis");
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public List<Fakturaslownik> getLista() {
        return lista;
    }

    public void setLista(List<Fakturaslownik> lista) {
        this.lista = lista;
    }

    public Fakturaslownik getSelected() {
        return selected;
    }

    public void setSelected(Fakturaslownik selected) {
        this.selected = selected;
    }
    
    
   
    
    
    
}
