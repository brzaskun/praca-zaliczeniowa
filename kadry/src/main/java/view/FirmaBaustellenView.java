/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dao.FirmabaustelleFacade;
import entity.Firmabaustelle;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class FirmaBaustellenView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private List<Firmabaustelle> lista;
    @Inject
    private FirmabaustelleFacade firmabaustelleFacade;
    @Inject
    private Firmabaustelle selected;
    
    @PostConstruct
    public void pobierzedane() {
        if (wpisView.getFirma()!=null) {
            lista = firmabaustelleFacade.findbyRokFirma(wpisView.getRokWpisu(), wpisView.getFirma());
            if (lista==null) {
                lista = new ArrayList<>();
            }
            selected.setKraj("Niemcy");
            Msg.msg("Pobrano dane");
        } else {
            Msg.msg("e","Nie wybrano firmy");
        }
    }
    
    public void dodaj() {
        try {
            selected.setFirmakadry(wpisView.getFirma());
            selected.setRok(wpisView.getRokWpisu());
            firmabaustelleFacade.create(selected);
            lista.add(selected);
            selected = new Firmabaustelle();
            selected.setKraj("Niemcy");
            Msg.msg("Dodano budowę");
        } catch (Exception e){
            Msg.dPe();
        }
    }
    
    public void usun(Firmabaustelle firmabaustelle) {
        try {
            firmabaustelleFacade.remove(firmabaustelle);
            lista.remove(firmabaustelle);
            Msg.msg("Usunięto budowę");
        } catch (Exception e){
            Msg.dPe();
        }
    }

    public List<Firmabaustelle> getLista() {
        return lista;
    }

    public void setLista(List<Firmabaustelle> lista) {
        this.lista = lista;
    }

    public Firmabaustelle getSelected() {
        return selected;
    }

    public void setSelected(Firmabaustelle selected) {
        this.selected = selected;
    }
    
    
    
}
