/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import java.io.Serializable;
import javax.ejb.Stateless;

/**
 *
 * @author Osito
 */
@Stateless
public class SessionFacade2 <T> implements Serializable {

    private static final long serialVersionUID = 1L;

//    @PreDestroy
//    private void preDestroy() {
//        em.clear();
//        em.close();
//        em = null;
//        error.E.s("koniec jpa");
//    }

//    @PersistenceContext(unitName = "dokfpPU")
//    private EntityManager em;
//
//    public SessionFacade2() {
//       // error.E.s("SessionFacade init");
//    }
//
//    public EntityManager getEntityManager() {
//        return em;
//    }
    
}
