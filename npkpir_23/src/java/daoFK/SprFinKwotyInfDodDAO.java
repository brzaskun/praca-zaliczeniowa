/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entity.Podatnik;
import entityfk.SprFinKwotyInfDod;
import error.E;
import java.io.Serializable;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class SprFinKwotyInfDodDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;

    public SprFinKwotyInfDodDAO(Class entityClass) {
        super(entityClass);
    }

    public SprFinKwotyInfDodDAO() {
    }

    public SprFinKwotyInfDod findsprfinkwoty(Podatnik podatnikObiekt, String rokWpisuSt) {
        SprFinKwotyInfDod zwrot = null;
        try {
            zwrot = (SprFinKwotyInfDod)sessionFacade.getEntityManager().createNamedQuery("SprFinKwotyInfDod.findsprfinkwoty").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).getSingleResult();
        } catch (Exception e){
            E.e(e);
        }
        return zwrot;
    }
    
    
    
}
