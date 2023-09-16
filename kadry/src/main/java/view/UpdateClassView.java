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
    private DRAView draView;
    @Inject
    private NieobecnoscView nieobecnoscView;
    @Inject
    private KartaWynagrodzenView kartaWynagrodzenView;
    @Inject
    private PracownikEkwiwalentView pracownikEkwiwalentView;
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
    private StanowiskoPracView stanowiskoPracView;
    @Inject
    private Z3daneView z3daneView;
    @Inject
    private WpisView wpisView;
    @Inject
    private ZaswiadczeniaView zaswiadczeniaView;
    @Inject
    private WypadkowefirmaView wypadkowefirmaView;
    @Inject
    private KalendarzGlobalnyView kalendarzGlobalnyView;
    

    public void updateRok(){
        kalendarzmiesiacView.init();
        nieobecnoscView.init();
    }
    
    public void updateKalendarz(){
        kalendarzwzorView.init();
        kalendarzGlobalnyView.init();
        wypadkowefirmaView.init();
    }
    
    public void updateRozwiazanieSkladniki(){
        skladnikWynagrodzeniaView.init();
        zmiennaWynagrodzeniaView.init();
        skladnikPotraceniaView.init();
        zmiennaPotraceniaView.init();
        stanowiskoPracView.init();
        etatView.init();
        pracownikEkwiwalentView.init();
        //pasekwynagrodzenView.init();
    }
    
    public void updateUmowaRozwiazanei(){
        umowaView.init();
        rozwiazanieumowyView.init();
        skladnikWynagrodzeniaView.init();
        zmiennaWynagrodzeniaView.init();
        skladnikPotraceniaView.init();
        zmiennaPotraceniaView.init();
        stanowiskoPracView.init();
        etatView.init();
        pracownikEkwiwalentView.init();
        //pasekwynagrodzenView.init();
    }
    
    //identyczne z tym powyzej bo nie chce mi sie poprawiac
    public void updateUmowa(){
        umowaView.init();
        rozwiazanieumowyView.init();
        skladnikWynagrodzeniaView.init();
        zmiennaWynagrodzeniaView.init();
        etatView.init();
        skladnikPotraceniaView.init();
        zmiennaPotraceniaView.init();
        pasekwynagrodzenView.init();
    }
    
    public void updateAdminTab(){
        kalendarzmiesiacView.init();
        nieobecnoscView.reloadDialog();
        //wywalilem bo jest RequestScoped
        //przywrocilem do jest reloadDialoh
        //request nie dziala
        pracownikNieobecnoscView.reloadDialog();
        skladnikWynagrodzeniaView.init();
        pasekwynagrodzenView.reloadDialog();
        draView.reloadDialog();
        zmienneZbiorczoView.init();
        skladnikiZbiorczoView.init();
        z3daneView.reloadDialog();
        zaswiadczeniaView.init();
        try {
            kartaWynagrodzenView.pobierzdane(wpisView.getAngaz());
            rachunekZlecenieView.init(wpisView.getUmowa());
        } catch (Exception e) {}
        
    }
    
}


