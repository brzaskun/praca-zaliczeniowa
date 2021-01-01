/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmp;

import entity.Definicjalistaplac;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class DefinicjalistaplacFacade extends AbstractFacade<Definicjalistaplac> {

    @PersistenceContext(unitName = "kadryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DefinicjalistaplacFacade() {
        super(Definicjalistaplac.class);
    }
    
}
