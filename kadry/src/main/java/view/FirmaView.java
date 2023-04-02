/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FirmaKadryFacade;
import entity.FirmaKadry;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class FirmaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private FirmaKadryFacade firmaKadryFacade;
    private List<FirmaKadry> lista;
    private FirmaKadry selectedeast;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        lista  = firmaKadryFacade.findAll();
        if (!wpisView.getUzer().getLogin().equals("mariola")&&!wpisView.getUzer().getLogin().equals("2")) {
            for (Iterator<FirmaKadry> it = lista.iterator();it.hasNext();) {
                FirmaKadry f = it.next();
                if (f.getNip().equals("8511005008")) {
                    it.remove();
                    break;
                }
            }
        }
        if (wpisView.getFirma()!=null) {
            selectedeast = wpisView.getFirma();
        }
    }

    public List<FirmaKadry> getLista() {
        return lista;
    }

    public void setLista(List<FirmaKadry> lista) {
        this.lista = lista;
    }

    public FirmaKadry getSelectedeast() {
        return selectedeast;
    }

    public void setSelectedeast(FirmaKadry selectedeast) {
        this.selectedeast = selectedeast;
    }
    
    
    
}
