/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Amodok;
import java.io.Serializable;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class AmoDokDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade amodokFacade;

    public AmoDokDAO() {
        super(Amodok.class);
    }
}
