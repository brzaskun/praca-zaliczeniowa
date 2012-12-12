/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EvopisDAO;
import entity.Evopis;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScope
public class EvopisView {

    @Inject
    private Evopis selected;
    @Inject
    private EvopisDAO eopisDAO;
    private static List<Evopis> lista;

    public EvopisView() {
        lista = new ArrayList<>();
    }
    
    
    @PostConstruct
    private void init() {
        try{
        lista = eopisDAO.getDownloaded();
        } catch (Exception e){}
    }

    public void dodaj() {
        Iterator it;
        it = lista.iterator();
        try {
            while (it.hasNext()) {
                Evopis tmp = (Evopis) it.next();
                if (tmp.getOpis().equals(selected.getOpis())) {
                    throw new Exception();
                }
            }
            eopisDAO.dodaj(selected);
            lista.add(selected);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taka opis już istnieje", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void edytuj(RowEditEvent ev) {
        try {
            eopisDAO.edit(selected);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taka opis już istnieje", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void usun() {
        eopisDAO.destroy(selected);
        lista.remove(selected);
        RequestContext.getCurrentInstance().update("akordeon:form1");
    }

    public Evopis getSelected() {
        return selected;
    }

    public void setSelected(Evopis selected) {
        this.selected = selected;
    }

    public List<Evopis> getLista() {
        return lista;
    }

    public void setLista(List<Evopis> lista) {
        EvopisView.lista = lista;
    }

    public EvopisDAO getEvopisDAO() {
        return eopisDAO;
    }

    public void setEvopisDAO(EvopisDAO eopisDAO) {
        this.eopisDAO = eopisDAO;
    }
}
