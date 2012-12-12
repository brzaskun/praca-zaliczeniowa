/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Evewidencja;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class EvewidencjaDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade evewidencjaFacade;

    public EvewidencjaDAO() {
        super(Evewidencja.class);
    }

    public Evewidencja znajdzponazwie(String nazwa) throws Exception {
        Evewidencja tmp = new Evewidencja();
        try {
            tmp = evewidencjaFacade.findEvewidencjaByName(nazwa);
            return tmp;
        } catch (Exception e) {
            throw new Exception();
        }
    }
}
