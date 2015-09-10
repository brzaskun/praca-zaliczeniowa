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
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;
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
@ViewScoped
public class EvewidencjaView implements  Serializable {
    private static final long serialVersionUID = 1L;
    private List<Evewidencja> lista;

    @Inject
    private Evewidencja selected;
    private Evewidencja wybrany;
    @Inject private EvewidencjaDAO evwidencjaDAO;
    @Inject private EvpozycjaDAO evpozycjaDAO;

    public EvewidencjaView() {
        lista = new ArrayList<>();
    }
    
    
    @PostConstruct
    private void init() {
        try{
            lista = evwidencjaDAO.findAll();
        } catch (Exception e) { 
            E.e(e); 
        }
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
            evwidencjaDAO.dodaj(selected);
            lista.add(selected);
            selected = new Evewidencja();
            Msg.msg("i", "Dodano nową ewidencję VAT");
        } catch (Exception e) { 
            E.e(e); 
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taka ewidencja już istnieje", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
    
    public void edytuj() {
        try {
            evwidencjaDAO.edit(selected);
            lista = evwidencjaDAO.findAll();
            selected = new Evewidencja();
        } catch (Exception e) { 
            E.e(e);
            Msg.msg("e", "Błąd edycji, nie zachowano zmian");
        } 
    }
    
    public void zachowajnowenazwy() {
        evwidencjaDAO.editList(lista);
        Msg.msg("Naniesiono zmiany");
    }
    
    public void przygotujdoedycji() {
        selected = wybrany;
        Msg.msg("Wybrano wiesz do edycji "+wybrany.getNazwa());
    }

    public void edytuj(RowEditEvent ev) {
        try {
            evwidencjaDAO.edit(selected);
            Msg.msg("i", "Poprawiono ewidencję VAT");
        } catch (Exception e) { E.e(e); 
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Taka ewidencja już istnieje", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }

    public void usun() {
        evwidencjaDAO.destroy(selected);
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
        return evwidencjaDAO;
    }

    public void setEvewidencjaDAO(EvewidencjaDAO eewidencjaDAO) {
        this.evwidencjaDAO = eewidencjaDAO;
    }

    public Evewidencja getWybrany() {
        return wybrany;
    }

    public void setWybrany(Evewidencja wybrany) {
        this.wybrany = wybrany;
    }
    
    
}
