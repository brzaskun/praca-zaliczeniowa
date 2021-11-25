/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.RodzajnieobecnosciFacade;
import dao.SwiadczeniekodzusFacade;
import entity.Rodzajnieobecnosci;
import entity.Swiadczeniekodzus;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import kadryiplace.Absencja;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class SwiadczeniekodzusView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private SwiadczeniekodzusFacade nieobecnosckodzusFacade;
    private Swiadczeniekodzus selectedlista;
    private List<Swiadczeniekodzus> lista;
    
    @PostConstruct
    private void init() {
        lista = nieobecnosckodzusFacade.findAll();
    }

    public void zachowaj() {
        nieobecnosckodzusFacade.editList(lista);
        Msg.msg("Zmiany zachowane");
    }

    public Swiadczeniekodzus getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Swiadczeniekodzus selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Swiadczeniekodzus> getLista() {
        return lista;
    }

    public void setLista(List<Swiadczeniekodzus> lista) {
        this.lista = lista;
    }
    
   @Inject
    private DAOsuperplace.AbsencjaFacade absencjaFacade;
   @Inject
    private RodzajnieobecnosciFacade rodzajnieobecnosciFacade;@Inject
    public void generujtabele() {
        Msg.msg("Start");
        List<Absencja> findAll = absencjaFacade.findAll();
        for (Absencja p : findAll) {
            Rodzajnieobecnosci s  = new Rodzajnieobecnosci();
            s.setAbsSerial(p.getAbsSerial());
            s.setKod(p.getAbsKod());
            s.setOpis(p.getAbsOpis());
            s.setRedukcjawyn(p.getAbsRedWyn());
            rodzajnieobecnosciFacade.create(s);
        }
        Msg.dP();
    }
    
}
