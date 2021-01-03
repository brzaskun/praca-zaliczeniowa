/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmp;

import entity.Pasekwynagrodzen;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class PasekwynagrodzenFacade extends AbstractFacade<Pasekwynagrodzen> {

    @PersistenceContext(unitName = "kadryPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PasekwynagrodzenFacade() {
        super(Pasekwynagrodzen.class);
    }
    
}
