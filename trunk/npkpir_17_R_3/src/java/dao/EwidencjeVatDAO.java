/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Ewidencjevat;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class EwidencjeVatDAO extends DAO implements Serializable {
    
    @Inject
    private SessionFacade ewidencjevatFacade;

    public EwidencjeVatDAO() {
        super(Ewidencjevat.class);
    }
    
    public Ewidencjevat find(String rok, String mc, String pod) {
        return ewidencjevatFacade.findEwidencjeVAT(rok, mc, pod);
    }
}
