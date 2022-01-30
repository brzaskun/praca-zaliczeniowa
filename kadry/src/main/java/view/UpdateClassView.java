/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class UpdateClassView   implements Serializable {
    private static final long serialVersionUID = 1L;

    @Inject
    private DefinicjalistaplacView definicjalistaplacView;
    @Inject
    private KalendarzmiesiacView kalendarzmiesiacView;
    @Inject
    private KalendarzwzorView kalendarzwzorView;
    @Inject
    private PasekwynagrodzenView pasekwynagrodzenView;
    @Inject
    private DraView draView;
    @Inject
    private NieobecnoscView nieobecnoscView;
    @Inject
    private PracownikNieobecnoscView pracownikUrlopView;
    @Inject
    private KartaWynagrodzenView kartaWynagrodzenView;
    @Inject
    private UmowaView umowaView;
    @Inject
    private SkladnikWynagrodzeniaView skladnikWynagrodzeniaView;
    @Inject
    private ZmiennaWynagrodzeniaView zmiennaWynagrodzeniaView;
    @Inject
    private RachunkidoZlecenListaView rachunkidoZlecenView;
    @Inject
    private ZmiennaPotraceniaView zmiennaPotraceniaView;
    @Inject
    private EtatView etatView;
    @Inject
    private SkladnikPotraceniaView skladnikPotraceniaView;


    public void updateRok(){
        kalendarzmiesiacView.init();
        nieobecnoscView.init();
    }
    
    public void updateUmowa(){
        umowaView.init();
        skladnikWynagrodzeniaView.init();
        zmiennaWynagrodzeniaView.init();
        kalendarzmiesiacView.init();
        zmiennaPotraceniaView.init();
        etatView.init();
        skladnikPotraceniaView.init();
    }
    
    public void updateUmowaPlace(){
        kalendarzmiesiacView.init();
        nieobecnoscView.init();
    }
    
}


