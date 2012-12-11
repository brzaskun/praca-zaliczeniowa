/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EvewidencjaDAO;
import entity.Evewidencja;
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
public class EvewidencjaView {

    @Inject
    private Evewidencja selected;
    @Inject
    private EvewidencjaDAO eewidencjaDAO;
    private static List<Evewidencja> lista;

    public EvewidencjaView() {
        lista = new ArrayList<>();
    }
    
    
    @PostConstruct
    private void init() {
        try{
        lista = eewidencjaDAO.getDownloaded();
        } catch (Exception e){}
    }

    public void dodaj() {
        Iterator it;
        it = lista.iterator();
        try {
            while (it.hasNext()) {
                Evewidencja tmp = (Evewidencja) it.next();
                if (tmp.getNazwa().equals(selected.getNazwa())) {
                    throw new Exception();
                }
            }
            eewidencjaDAO.dodajNowyWpis(selected);
            lista.add(selected);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taka ewidencja już istnieje", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void edytuj(RowEditEvent ev) {
        try {
            eewidencjaDAO.edit(selected);
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taka ewidencja już istnieje", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void usun() {
        eewidencjaDAO.destroy(selected);
        lista.remove(selected);
        RequestContext.getCurrentInstance().update("akordeon:form0");
    }

    public Evewidencja getSelected() {
        return selected;
    }

    public void setSelected(Evewidencja selected) {
        this.selected = selected;
    }

    public List<Evewidencja> getLista() {
        return lista;
    }

    public void setLista(List<Evewidencja> lista) {
        EvewidencjaView.lista = lista;
    }

    public EvewidencjaDAO getEvewidencjaDAO() {
        return eewidencjaDAO;
    }

    public void setEvewidencjaDAO(EvewidencjaDAO eewidencjaDAO) {
        this.eewidencjaDAO = eewidencjaDAO;
    }
}
