/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package databaseDAO;

import entity.Dok;
import javax.faces.bean.ManagedBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@ManagedBean
public class DokumentFacade extends AbstractFacade<Dok> {
    @PersistenceContext(unitName = "fktest2PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DokumentFacade() {
        super(Dok.class);
    }
    
}
