/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Zusmail;
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
public class ZusmailDAO extends DAO implements Serializable {
    
    @Inject
    private SessionFacade sessionFacade;

    public ZusmailDAO() {
        super(Zusmail.class);
    }
    
    public Zusmail findZusmail(Zusmail zusmail) {
        try {
            return sessionFacade.findZusmail(zusmail);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
        }
        return null;
    }
    
    public List<Zusmail> findZusRokMc(String rok, String mc) {
        try {
            return sessionFacade.findZusRokMc(rok,mc);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
        }
        return null;
    }
    
}
