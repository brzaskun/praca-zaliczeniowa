/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.DeklaracjaVatZZPowod;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class DeklaracjaVatZZPowodDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public DeklaracjaVatZZPowodDAO() {
        super(DeklaracjaVatZZPowod.class);
    }
    
    public List findAll() {
        return sessionFacade.findAll(DeklaracjaVatZZPowod.class);
    }
    
}
