/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FakturaStopkaNiemiecka;
import entity.Podatnik;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class FakturaStopkaNiemieckaDAO  extends DAO implements Serializable {

    @Inject
    private SessionFacade sessionFacade;
    
    public FakturaStopkaNiemieckaDAO() {
        super(FakturaStopkaNiemiecka.class);
    }
    
    public  List<FakturaStopkaNiemiecka> findAll(){
        List<FakturaStopkaNiemiecka> zwrot = null;
        try {
            zwrot = sessionFacade.findAll(FakturaStopkaNiemiecka.class);
        } catch (Exception e) { 
            E.e(e); 
        }
        return zwrot;
   }

    public FakturaStopkaNiemiecka findByPodatnik(Podatnik podatnikObiekt) {
        FakturaStopkaNiemiecka zwrot = null;
        try {
            zwrot = sessionFacade.findStopkaNiemieckaByPodatnik(podatnikObiekt);
        } catch (Exception e) {
            E.e(e); 
        }
        return zwrot;
    }
    
}
