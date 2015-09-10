/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.DeklaracjaVatSchema;
import entity.DeklaracjaVatWierszSumaryczny;
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
public class DeklaracjaVatWierszSumarycznyDAO  extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;
    
    public DeklaracjaVatWierszSumarycznyDAO() {
        super(DeklaracjaVatWierszSumaryczny.class);
    }
    
    public List findAll() {
        return sessionFacade.findAll(DeklaracjaVatWierszSumaryczny.class);
    }

    public DeklaracjaVatWierszSumaryczny findWiersz(String razem_suma_przychodów) {
        return sessionFacade.findWierszSumaryczny(razem_suma_przychodów);
    }
    
}
