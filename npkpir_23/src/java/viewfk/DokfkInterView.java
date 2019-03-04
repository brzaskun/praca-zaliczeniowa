/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.RodzajedokDAO;
import entity.Rodzajedok;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;
import xls.ReadXLSFile;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokfkInterView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Rodzajedok> rodzajedoklista;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private RodzajedokDAO rodzajedokDAO;

    public DokfkInterView() {
         E.m(this);
    }
    
    
    
    @PostConstruct
    public void init() {
        rodzajedoklista = rodzajedokDAO.findListaPodatnik(wpisView.getPodatnikObiekt());
    }
    
    public void zachowajzmiany() {
        try {
            rodzajedokDAO.editList(rodzajedoklista);
            Msg.msg("Zachowano nazwy dokumentów");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd podczas zachowywania nazw dokumentów");
        }
    }
    
    public void pobierzdanezpliku() {
        ReadXLSFile.updateRodzajedok(rodzajedokDAO, wpisView, "c://temp//rodzajedok.xlsx");
        rodzajedoklista = rodzajedokDAO.findListaPodatnik(wpisView.getPodatnikObiekt());
    }

    public List<Rodzajedok> getRodzajedoklista() {
        return rodzajedoklista;
    }

    public void setRodzajedoklista(List<Rodzajedok> rodzajedoklista) {
        this.rodzajedoklista = rodzajedoklista;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
    
}
