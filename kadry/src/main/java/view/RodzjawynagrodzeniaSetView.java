/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.RodzajwynagrodzeniaFacade;
import entity.Rodzajwynagrodzenia;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class RodzjawynagrodzeniaSetView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private RodzajwynagrodzeniaFacade rodzajwynagrodzeniaFacade;
    private Rodzajwynagrodzenia selectedlista;
    private List<Rodzajwynagrodzenia> lista;
    
    @PostConstruct
    private void init() {
        lista = rodzajwynagrodzeniaFacade.findAll();
    }

    public void zachowaj() {
        rodzajwynagrodzeniaFacade.edit(lista);
        Msg.msg("Zmiany zachowane");
    }
    
    public List<Rodzajwynagrodzenia> getLista() {
        return lista;
    }

    public void setLista(List<Rodzajwynagrodzenia> lista) {
        this.lista = lista;
    }

 

    public Rodzajwynagrodzenia getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Rodzajwynagrodzenia selectedlista) {
        this.selectedlista = selectedlista;
    }
    
    
}
