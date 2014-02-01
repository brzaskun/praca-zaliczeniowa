/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.RzisukladDAO;
import entityfk.Rzisuklad;
import entityfk.RzisukladPK;
import java.io.Serializable;
import java.util.ArrayList;
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
public class RzisukladView implements Serializable{
    @Inject private Rzisuklad selected;
    private String nowy;
    private static List<Rzisuklad> lista;
    @Inject private RzisukladDAO rzisukladDAO;

    public RzisukladView() {
        lista = new ArrayList<>();
    }
    
    @PostConstruct
    private void init() {
       try {
            lista = rzisukladDAO.findAll();
        } catch (Exception e) {} 
    }
    
    public void wybranouklad() {
        Msg.msg("i", "Wybrano uklad "+selected.getRzisukladPK().getUklad());
    }
    
    public void dodaj() {
        try {
            RzisukladPK rzisukladPK = new RzisukladPK();
            rzisukladPK.setPodatnik("Tymczasowy");
            rzisukladPK.setRok("2014");
            rzisukladPK.setUklad(nowy);
            Rzisuklad rzisuklad = new Rzisuklad();
            rzisuklad.setRzisukladPK(rzisukladPK);
            nowy = "";
            rzisukladDAO.dodaj(rzisuklad);
            lista.add(rzisuklad);
            Msg.msg("i", "Dodano nowy układ");
        } catch (Exception e) {
            Msg.msg("e", "Nieudana próba dodania układu.");
        }
    }

    public void usun(Rzisuklad rzisuklad) {
        try {
            rzisukladDAO.destroy(rzisuklad);
            lista.remove(rzisuklad);
            Msg.msg("i", "Usunięto wybrany układ");
        } catch (Exception e) {
            Msg.msg("e", "Nieudana próba usuniecia układu.");
        }
    }
    public Rzisuklad getSelected() {
        return selected;
    }

    public void setSelected(Rzisuklad selected) {
        this.selected = selected;
    }

    public String getNowy() {
        return nowy;
    }

    public void setNowy(String nowy) {
        this.nowy = nowy;
    }

    
    public List<Rzisuklad> getLista() {
        return lista;
    }

    public static List<Rzisuklad> getListaS() {
        return lista;
    }
    
    public void setLista(List<Rzisuklad> lista) {
        RzisukladView.lista = lista;
    }

    public RzisukladDAO getRzisukladDAO() {
        return rzisukladDAO;
    }

    public void setRzisukladDAO(RzisukladDAO rzisukladDAO) {
        this.rzisukladDAO = rzisukladDAO;
    }
    
    
}
