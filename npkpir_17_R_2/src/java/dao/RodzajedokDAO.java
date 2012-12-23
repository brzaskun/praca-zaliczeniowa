/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Rodzajedok;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class RodzajedokDAO extends DAO implements Serializable{

    @Inject
    private SessionFacade rodzajedokFacade;

    public RodzajedokDAO() {
        super(Rodzajedok.class);
    }
    
    public Rodzajedok find(String skrot){
        return rodzajedokFacade.findRodzajedok(skrot);
    }
}
