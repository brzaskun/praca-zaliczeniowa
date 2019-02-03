/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entity.Podatnik;
import entityfk.SprFinKwotyInfDod;
import java.io.Serializable;

/**
 *
 * @author Osito
 */
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
            zwrot = sessionFacade.findsprfinkwoty(podatnikObiekt, rokWpisuSt);
        } catch (Exception e){}
        return zwrot;
    }
    
    
    
}
