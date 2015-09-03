/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.DeklaracjaVatSchema;
import entity.SchemaEwidencja;
import entityfk.RMK;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class SchemaEwidencjaDAO  extends DAO implements Serializable {

    private static final long serialVersionUID = 1L;

    @Inject
    private SessionFacade sessionFacade;

    public SchemaEwidencjaDAO() {
        super(SchemaEwidencja.class);
    }

    public List<SchemaEwidencja> findAll() {
        try {
            return sessionFacade.findAll(SchemaEwidencja.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }

    public List<SchemaEwidencja> findEwidencjeSchemy(DeklaracjaVatSchema wybranaschema) {
        try {
            return sessionFacade.findEwidencjeSchemy(wybranaschema);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    
    
}
