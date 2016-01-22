/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.VATDeklaracjaKorektaDok;
import java.io.Serializable;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named

public class VATDeklaracjaKorektaDokDAO  extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject 
    private SessionFacade sessionFacade;

    public VATDeklaracjaKorektaDokDAO() {
        super(VATDeklaracjaKorektaDok.class);
    }

    public VATDeklaracjaKorektaDokDAO(Class entityClass) {
        super(entityClass);
    }
    
    
    
    
}
