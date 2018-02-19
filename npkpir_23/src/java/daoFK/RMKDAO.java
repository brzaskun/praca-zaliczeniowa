/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Dokfk;
import entityfk.RMK;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named

public class RMKDAO extends DAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SessionFacade sessionFacade;

    public RMKDAO() {
        super(RMK.class);
    }

    public List<RMK> findAll() {
        try {
            return sessionFacade.findAll(RMK.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<RMK> findRMKByPodatnikRok(WpisView wpisView) {
        try {
            return sessionFacade.findRMKByPodatnikRok(wpisView);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    
    public List<RMK> findRMKByPodatnikRokDokfk(WpisView wpisView, Dokfk dokfk) {
        try {
            return sessionFacade.findRMKByPodatnikRokDokfk(wpisView, dokfk);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }

}
