/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FakturaRozrachunki;
import error.E;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class FakturaRozrachunkiDAO extends DAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SessionFacade sessionFacade;

    public FakturaRozrachunkiDAO() {
        super(FakturaRozrachunki.class);
    }
    
    public List<FakturaRozrachunki> rozrachunkiZDnia () {
        Date d = new Date();
        try {
            return sessionFacade.rozrachunkiZDnia(d);
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

}
