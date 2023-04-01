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
    private RachunekZlecenieView rachunekZlecenieView;
    @Inject
    private ZmiennaPotraceniaView zmiennaPotraceniaView;
    @Inject
    private EtatView etatView;
    @Inject
    private SkladnikPotraceniaView skladnikPotraceniaView;
    @Inject
    private PracownikNieobecnoscView pracownikNieobecnoscView;
    @Inject
    private ZmienneZbiorczoView zmienneZbiorczoView;
    @Inject
    private SkladnikZbiorczoView skladnikiZbiorczoView;
    @Inject
    private RozwiazanieumowyView rozwiazanieumowyView;
    @Inject
    private WpisView wpisView;


    public void updateRok(){
        kalendarzmiesiacView.init();
        nieobecnoscView.init();
    }
    
    
    public void updateUmowaRozwiazanei(){
        umowaView.init();
        rozwiazanieumowyView.init();
        skladnikWynagrodzeniaView.init();
        zmiennaWynagrodzeniaView.init();
        skladnikPotraceniaView.init();
        zmiennaPotraceniaView.init();
        etatView.init();
        pasekwynagrodzenView.init();
    }
    
    //identyczne z tym powyzej bo nie chce mi sie poprawiac
    public void updateUmowa(){
        umowaView.init();
        rozwiazanieumowyView.init();
        skladnikWynagrodzeniaView.init();
        zmiennaWynagrodzeniaView.init();
        zmiennaPotraceniaView.init();
        etatView.init();
        skladnikPotraceniaView.init();
        pasekwynagrodzenView.init();
    }
    
    public void updateAdminTab(){
        kalendarzmiesiacView.init();
        nieobecnoscView.init();
        //wywalilem bo jest RequestScoped
        //pracownikNieobecnoscView.init();
        skladnikWynagrodzeniaView.init();
        pasekwynagrodzenView.init();
        zmienneZbiorczoView.init();
        skladnikiZbiorczoView.init();
        try {
            kartaWynagrodzenView.pobierzdane(wpisView.getAngaz());
            rachunekZlecenieView.init(wpisView.getUmowa());
        } catch (Exception e) {}
        
    }
    
}


