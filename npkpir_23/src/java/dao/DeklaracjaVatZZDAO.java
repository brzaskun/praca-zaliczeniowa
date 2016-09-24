/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.DeklaracjaVatZZ;
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
public class DeklaracjaVatZZDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public DeklaracjaVatZZDAO() {
        super(DeklaracjaVatZZ.class);
    }
    
    public List findAll() {
        return sessionFacade.findAll(DeklaracjaVatZZ.class);
    }
    
}
