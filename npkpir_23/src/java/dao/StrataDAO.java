/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.Strata;
import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Osito
 */
public class StrataDAO  extends DAO implements Serializable {
    

    public List<Strata> findPodatnik(Podatnik podatnikObiekt) {
        return sessionFacade.getEntityManager().createNamedQuery("Strata.findByPodatnik").setParameter("podatnik", podatnikObiekt).getResultList();
    }

    public void usuntensamrok(Strata nowastrata) {
        Strata strata = (Strata) sessionFacade.getEntityManager().createNamedQuery("Strata.findByPodatnikRok").setParameter("podatnik", nowastrata.getPodatnikObj()).setParameter("rok", nowastrata.getRok());
        if (strata != null) {
            sessionFacade.remove(strata);
        }
    }
    
}
