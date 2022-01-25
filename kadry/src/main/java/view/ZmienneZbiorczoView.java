/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SkladnikWynagrodzeniaFacade;
import dao.ZmiennaWynagrodzeniaFacade;
import entity.Skladnikwynagrodzenia;
import java.io.Serializable;
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
public class ZmienneZbiorczoView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private ZmiennaWynagrodzeniaFacade zmiennaWynagrodzeniaFacade;
    @Inject
    private SkladnikWynagrodzeniaFacade skladnikWynagrodzeniaFacade;
    List<Skladnikwynagrodzenia> skladniki;
    
    @PostConstruct
    private void init() {
        //skladniki = skladnikWynagrodzeniaFacade.findByUmowa(umowa);
    }
    
}
