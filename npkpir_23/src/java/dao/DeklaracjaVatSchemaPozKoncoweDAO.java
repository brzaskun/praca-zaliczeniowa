/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.DeklaracjaVatSchema;
import entity.DeklaracjaVatSchemaPozKoncowe;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named

public class DeklaracjaVatSchemaPozKoncoweDAO  extends DAO implements Serializable {

    private static final long serialVersionUID = 1L;


    public DeklaracjaVatSchemaPozKoncoweDAO() {
        super(DeklaracjaVatSchemaPozKoncowe.class);
    }

    public List<DeklaracjaVatSchemaPozKoncowe> findAll() {
        try {
            return sessionFacade.findAll(DeklaracjaVatSchemaPozKoncowe.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }

    public List<DeklaracjaVatSchemaPozKoncowe> findWierszeSchemy(DeklaracjaVatSchema wybranaschema) {
        try {
            return sessionFacade.getEntityManager().createNamedQuery("DeklaracjaVatSchemaPozKoncowe.findEwidencjeSchemy").setParameter("deklaracjaVatSchema", wybranaschema).getResultList();
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    
    public void usunliste(DeklaracjaVatSchema s) {
        try {
            sessionFacade.getEntityManager().createNamedQuery("DeklaracjaVatSchemaPozKoncowe.usunliste").setParameter("deklaracjaVatSchema", s).executeUpdate();
        } catch (Exception e) { 
            E.e(e); 
        }
    }
    
}
