/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.KlientJPK;
import entity.Podatnik;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class KlientJPKDAO  extends DAO implements Serializable {
    

    private String test;
    
    public KlientJPKDAO() {
        super(KlientJPK.class);
    }
    
    public List<KlientJPK> findAll() {
        return sessionFacade.findAll(KlientJPK.class);
    }
    
    public void deleteByPodRokMc (Podatnik podatnik, String rok, String mc) {
        sessionFacade.klientJPKdeleteByPodRokMc(podatnik, rok, mc);
    }

    public List<KlientJPK> findbyKlientRokMc(Podatnik podatnikObiekt, String rokWpisuSt, String miesiacWpisu) {
        return sessionFacade.getEntityManager().createNamedQuery("KlientJPK.findByPodRokMc").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("mc", miesiacWpisu).getResultList();
    }
    
    
    
}
