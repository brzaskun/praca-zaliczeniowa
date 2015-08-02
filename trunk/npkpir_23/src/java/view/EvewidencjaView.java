/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EvewidencjaDAO;
import dao.EvpozycjaDAO;
import entity.Evewidencja;
import entity.Evpozycja;
import error.E;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Osito
 */
@ManagedBean
@SessionScoped
public class EvewidencjaView {
    private List<Evewidencja> lista;

    @Inject
    private Evewidencja selected;
    @Inject private EvewidencjaDAO eewidencjaDAO;
    @Inject private EvpozycjaDAO evpozycjaDAO;

    public EvewidencjaView() {
        lista = new ArrayList<>();
    }
    
    
    @PostConstruct
    private void init() {
        try{
        lista.addAll(eewidencjaDAO.findAll());
        } catch (Exception e) { E.e(e); }
    }

    public void dodaj() {
        Evpozycja ewodszukana = evpozycjaDAO.find(selected.getPole());
        selected.setNrpolanetto(ewodszukana.getNrpolanetto());
        try{
        selected.setNrpolavat(ewodszukana.getNrpolavat());
        } catch (Exception e) { E.e(e); }
        Iterator it;
        it = lista.iterator();
        try {
            while (it.hasNext()) {
                Evewidencja tmp = (Evewidencja) it.next();
                if (tmp.getNazwa().equals(selected.getNazwa())) {
                    throw new Exception();
                }
            }
            eewidencjaDAO.dodaj(selected);
            lista.add(selected);
            Msg.msg("i", "Dodano nową ewidencję VAT");
        } catch (Exception e) { E.e(e); 
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taka ewidencja już istnieje", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void zachowajnowenazwy() {
        eewidencjaDAO.editList(lista);
    }

    public void edytuj(RowEditEvent ev) {
        try {
            eewidencjaDAO.edit(selected);
            Msg.msg("i", "Poprawiono ewidencję VAT");
        } catch (Exception e) { E.e(e); 
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taka ewidencja już istnieje", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void usun() {
        eewidencjaDAO.destroy(selected);
        lista.remove(selected);
        Msg.msg("i", "Usunięto ewidencję VAT"+selected.getNazwa());
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
        this.lista = lista;
    }

   

    public EvewidencjaDAO getEvewidencjaDAO() {
        return eewidencjaDAO;
    }

    public void setEvewidencjaDAO(EvewidencjaDAO eewidencjaDAO) {
        this.eewidencjaDAO = eewidencjaDAO;
    }
}
