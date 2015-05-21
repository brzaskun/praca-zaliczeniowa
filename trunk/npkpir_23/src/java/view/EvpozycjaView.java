/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EvpozycjaDAO;
import entity.Evpozycja;
import error.E;
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
public class EvpozycjaView {
    private List<Evpozycja> lista;

    @Inject
    private Evpozycja selected;
    @Inject
    private EvpozycjaDAO epozycjaDAO;

    public EvpozycjaView() {
        lista = new ArrayList<>();
    }
    
    
    @PostConstruct
    private void init() {
        try{
        lista.addAll(epozycjaDAO.findAll());
        } catch (Exception e) { E.e(e); }
    }

    public void dodaj() {
        Iterator it;
        it = lista.iterator();
        try {
            while (it.hasNext()) {
                Evpozycja tmp = (Evpozycja) it.next();
                if (tmp.getNazwapola().equals(selected.getNazwapola())) {
                    throw new Exception();
                }
            }
            epozycjaDAO.dodaj(selected);
            lista.add(selected);
        } catch (Exception e) { E.e(e); 
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taka pozycja już istnieje", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void edytuj(RowEditEvent ev) {
        try {
            epozycjaDAO.edit(selected);
        } catch (Exception e) { E.e(e); 
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taka pozycja już istnieje", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void usun() {
        epozycjaDAO.destroy(selected);
        lista.remove(selected);
        RequestContext.getCurrentInstance().update("akordeon:form2");
    }

    public Evpozycja getSelected() {
        return selected;
    }

    public void setSelected(Evpozycja selected) {
        this.selected = selected;
    }

    public List<Evpozycja> getLista() {
        return lista;
    }

    public void setLista(List<Evpozycja> lista) {
        this.lista = lista;
    }

   
    public EvpozycjaDAO getEpozycjaDAO() {
        return epozycjaDAO;
    }

    public void setEpozycjaDAO(EvpozycjaDAO epozycjaDAO) {
        this.epozycjaDAO = epozycjaDAO;
    }

    public EvpozycjaDAO getEvpozycjaDAO() {
        return epozycjaDAO;
    }

    public void setEvpozycjaDAO(EvpozycjaDAO epozycjaDAO) {
        this.epozycjaDAO = epozycjaDAO;
    }
}
