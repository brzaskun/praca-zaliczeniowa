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

    public void updateRok(){
        definicjalistaplacView.init();
        kalendarzmiesiacView.init();
        kalendarzwzorView.init();
        pasekwynagrodzenView.init();
        draView.init();
    }
    
}


