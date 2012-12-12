/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Pitpoz;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class PitDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade pitpozFacade;

    public PitDAO() {
        super(Pitpoz.class);
    }

    public Pitpoz find(String rok, String mc, String pod) {
        return pitpozFacade.findPitpoz(rok, mc, pod);
    }
}
