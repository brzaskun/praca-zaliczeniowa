/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FakturaXXLKolumna;
import entity.Podatnik;
import entityfk.Kliencifk;
import error.E;
import java.io.Serializable;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class FakturaXXLKolumnaDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject private SessionFacade sessionFacade;

    public FakturaXXLKolumnaDAO() {
        super(FakturaXXLKolumna.class);
    }

    public FakturaXXLKolumnaDAO(Class entityClass) {
        super(entityClass);
    }
    
    public FakturaXXLKolumna findXXLByPodatnik(Podatnik p) {
        FakturaXXLKolumna zwrot = null;
        try {
            zwrot = sessionFacade.findXXLByPodatnik(p);
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
}
