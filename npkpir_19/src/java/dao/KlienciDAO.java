/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Klienci;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class KlienciDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade klienciFacade;

    public KlienciDAO() {
        super(Klienci.class);
    }
}
