/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import data.Data;
import entity.Amodok;
import entityfk.BankImportWzory;
import error.E;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class BankImportWzoryDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade sessionFacade;

    public BankImportWzoryDAO() {
        super(Amodok.class);
    }
    
    public  List<Amodok> findAll(){
        try {
            return sessionFacade.findAll(Amodok.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    public List<BankImportWzory> findByBank(String wybranybankimport) {
        return sessionFacade.getEntityManager().createNamedQuery("BankImportWzory.findByBank").setParameter("bank", wybranybankimport).getResultList();
    }
    
  
}

