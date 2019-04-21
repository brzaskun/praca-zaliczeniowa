/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaDAO;

import entity.Faktura;

import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpSession;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class FakturyPlatnosciView  implements Serializable {
    private List<Faktura> fakturyniezaplacone;
    private List<Faktura> fakturyzaplacone;
    private List<Faktura> fakturyniezaplaconeF;
    private List<Faktura> fakturyzaplaconeF;
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private Faktura selected;
    private String datazaplaty;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    private double sumaniezaplaconych;

    @PostConstruct
    private void init() {
        fakturyniezaplacone = fakturaDAO.findbyPodatnikRokMcPlatnosci(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), false);
        fakturyzaplacone = fakturaDAO.findbyPodatnikRokMcPlatnosci(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), true);
        if (fakturyniezaplacone!=null && fakturyniezaplacone.size()>0) {
            for (Faktura f : fakturyniezaplacone) {
                    sumaniezaplaconych += f.getBruttoFaktura();
            }
        }
    }
    
    public void aktualizujTabeleTabelaGuest(AjaxBehaviorEvent e) throws IOException {
        fakturyniezaplacone.clear();
        fakturyzaplacone.clear();
        aktualizuj();
        init();
        Msg.msg("i", "Udana zamiana miesiąca. Aktualny okres rozliczeniowy: " + wpisView.getRokWpisu() + "/" + wpisView.getMiesiacWpisu(), "form:messages");
    }
    
    private void aktualizuj() {
        wpisView.naniesDaneDoWpis();
    }
    
    public void skopiujfakture(Faktura faktura) {
        this.selected = faktura;
    }
    
    public void naniesplatnoscfaktury() {
        try {
            selected.setDatazaplaty(datazaplaty);
            fakturaDAO.edit(selected);
            fakturyniezaplacone.remove(selected);
            fakturyzaplacone.add(selected);
            datazaplaty = null;
            Msg.msg("Naniesiono płatność");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd, nie naniesiono płatności");
        }
        
    }
    
     public void usundatezaplaty(Faktura f) {
        try {
            f.setDatazaplaty(null);
            fakturaDAO.edit(f);
            fakturyzaplacone.remove(f);
            fakturyniezaplacone.add(f);
            datazaplaty = null;
            Msg.msg("wyzerowano płatność");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystąpił błąd, nie wyzerowano płatności.");
        }
    }
    
    public List<Faktura> getFakturyniezaplacone() {
        return fakturyniezaplacone;
    }

    public void setFakturyniezaplacone(List<Faktura> fakturyniezaplacone) {
        this.fakturyniezaplacone = fakturyniezaplacone;
    }

    public List<Faktura> getFakturyzaplacone() {
        return fakturyzaplacone;
    }

    public void setFakturyzaplacone(List<Faktura> fakturyzaplacone) {
        this.fakturyzaplacone = fakturyzaplacone;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public String getDatazaplaty() {
        return datazaplaty;
    }

    public void setDatazaplaty(String datazaplaty) {
        this.datazaplaty = datazaplaty;
    }

    public Faktura getSelected() {
        return selected;
    }

    public void setSelected(Faktura selected) {
        this.selected = selected;
    }

    public List<Faktura> getFakturyniezaplaconeF() {
        return fakturyniezaplaconeF;
    }

    public void setFakturyniezaplaconeF(List<Faktura> fakturyniezaplaconeF) {
        this.fakturyniezaplaconeF = fakturyniezaplaconeF;
    }

    public List<Faktura> getFakturyzaplaconeF() {
        return fakturyzaplaconeF;
    }

    public void setFakturyzaplaconeF(List<Faktura> fakturyzaplaconeF) {
        this.fakturyzaplaconeF = fakturyzaplaconeF;
    }

    public double getSumaniezaplaconych() {
        return sumaniezaplaconych;
    }

    public void setSumaniezaplaconych(double sumaniezaplaconych) {
        this.sumaniezaplaconych = sumaniezaplaconych;
    }
    
    
    
    
}
