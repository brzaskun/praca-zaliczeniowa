/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Zusstawki;
import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class ZUSDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade SFacade;
   
    public ZUSDAO(){
        super(Zusstawki.class);
    }
}
