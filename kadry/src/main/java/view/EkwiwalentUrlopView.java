/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EkwiwalentUrlopFacade;
import dao.WspolczynnikEkwiwalentFacade;
import entity.EkwiwalentUrlop;
import entity.WspolczynnikEkwiwalent;
import java.io.Serializable;
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
public class EkwiwalentUrlopView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private EkwiwalentUrlop selected;
    private WspolczynnikEkwiwalent wpolczynnik;
    @Inject
    private WspolczynnikEkwiwalentFacade wspolczynnikEkwiwalentFacade;
    @Inject
    private EkwiwalentUrlopFacade ekwiwalentSkladnikiFacade;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        wpolczynnik = wspolczynnikEkwiwalentFacade.findbyRok(wpisView.getRokWpisu());
//        selected = ekwiwalentSkladnikiFacade.findbyRok(wpisView.getRokWpisu());
//        if (selected==null) {
//            selected = new EkwiwalentUrlop();
//        }
    }
    
    

//    public EkwiwalentUrlop getSelected() {
//        return selected;
//    }
//
//    public void setSelected(EkwiwalentUrlop selected) {
//        this.selected = selected;
//    }

    public WspolczynnikEkwiwalent getWpolczynnik() {
        return wpolczynnik;
    }

    public void setWpolczynnik(WspolczynnikEkwiwalent wpolczynnik) {
        this.wpolczynnik = wpolczynnik;
    }
    
    
    
    
}
