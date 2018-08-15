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
public class FakturaDuplikatDAO extends DAO implements Serializable {
    public List<Strata> findPodatnik(Podatnik podatnikObiekt) {
        return sessionFacade.getEntityManager().createNamedQuery("Strata.findByPodatnik").setParameter("podatnik", podatnikObiekt).getResultList();
    }
    
}
