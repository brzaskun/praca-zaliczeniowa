/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.RodzajedokDAO;
import entity.Rodzajedok;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RodzajedokView implements Serializable {

    private static Rodzajedok doUsuniecia;
    private static HashMap<String, String> rodzajedokMap;

    public static HashMap<String, String> getRodzajedokMapS() {
        return rodzajedokMap;
    }
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private Rodzajedok wprowadzany;
    @Inject
    private Rodzajedok selected;

    private List<Rodzajedok> lista;

    public RodzajedokView() {
        lista = new ArrayList<>();
        rodzajedokMap = new HashMap<>();

    }

    @PostConstruct
    private void init() {
        try {
            lista.addAll(rodzajedokDAO.findAll());
            for (Rodzajedok tmp : lista) {
                rodzajedokMap.put(tmp.getSkrot(), tmp.getRodzajtransakcji());
            }
        } catch (Exception e) {
        }

    }

    public void dodaj() {
        try {
            rodzajedokDAO.dodaj(wprowadzany);
            lista.add(wprowadzany);
            wprowadzany = new Rodzajedok();
            Msg.msg("Dodatno nowy rodzaj dokumentu: " + wprowadzany.getNazwa());
        } catch (Exception e) {
            Msg.msg("e", "Niedodatno nowego rodzaju dokumentu. Sprawdz czy skrót się nie powtarza.");
        }

    }
    
    public void edytuj() {
        try {
            rodzajedokDAO.edit(wprowadzany);
            wprowadzany = new Rodzajedok();
            Msg.msg("Wyedytowano nowy rodzaj dokumentu: " + wprowadzany.getNazwa());
        } catch (Exception e) {
            Msg.msg("e", "Niewyedytowano rodzaju dokumentu. Wystąpił błąd");
        }

    }

    public void destroy(Rodzajedok selDok) {
        doUsuniecia = selDok;
    }

    public void destroy2() {
        try {
            rodzajedokDAO.destroy(doUsuniecia);
            lista.remove(doUsuniecia);
            RequestContext.getCurrentInstance().update("form:dokLista");
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Wzorzec usunięty", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Wzorzec NIE usunięty", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }

    }
    
    public void skopiujwierszdoedycji() {
        wprowadzany = selected;
        RequestContext.getCurrentInstance().update("form1:parametrydokument");
    }

//<editor-fold defaultstate="collapsed" desc="comment">
    public Rodzajedok getSelected() {
        return selected;
    }

    public void setSelected(Rodzajedok selected) {
        this.selected = selected;
    }
    
    
    public List<Rodzajedok> getLista() {
        return lista;
    }
    
    public void setLista(List<Rodzajedok> lista) {
        this.lista = lista;
    }
    
    public Rodzajedok getWprowadzany() {
        return wprowadzany;
    }
    
    public void setWprowadzany(Rodzajedok wprowadzany) {
        this.wprowadzany = wprowadzany;
    }
    
    public HashMap<String, String> getRodzajedokMap() {
        return rodzajedokMap;
    }
    
    public void setRodzajedokMap(HashMap<String, String> rodzajedokMap) {
        RodzajedokView.rodzajedokMap = rodzajedokMap;
    }
//</editor-fold>
}
