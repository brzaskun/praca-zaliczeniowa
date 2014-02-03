/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Kontopozycjarzis;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class KontopozycjarzisDAO extends DAO implements Serializable{
    @Inject
    private SessionFacade sessionFacade;

    public KontopozycjarzisDAO() {
        super(Kontopozycjarzis.class);
    }
    
    
}
