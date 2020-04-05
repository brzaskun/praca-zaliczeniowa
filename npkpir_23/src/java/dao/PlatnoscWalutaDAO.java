/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.Kwartaly;
import entity.Dok;
import entity.PlatnoscWaluta;
import entity.Podatnik;
import error.E;
import java.io.Serializable;
import java.util.Collections;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class PlatnoscWalutaDAO extends DAO implements Serializable {


    public PlatnoscWalutaDAO() {
        super(PlatnoscWaluta.class);
    }
    
    public  List<PlatnoscWaluta> findAll(){
        try {
            return sessionFacade.findAll(PlatnoscWaluta.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    public List<PlatnoscWaluta> findByDok(Dok selected) {
        return sessionFacade.findPlatnoscWalutaByDok(selected);
    }

    public List<PlatnoscWaluta> findByPodRokMc(Podatnik podatnikObiekt, String rokWpisuSt, String miesiacWpisu) {
        return sessionFacade.findPlatnoscWalutaByPodRokMc(podatnikObiekt, rokWpisuSt, miesiacWpisu);
    }
    
    public List<PlatnoscWaluta> findByPodRokKw(Podatnik podatnikObiekt, String rokWpisuSt, String miesiacWpisu) {
        List<String> mce = Kwartaly.mctoMcewKw(miesiacWpisu);
        return sessionFacade.getEntityManager().createNamedQuery("PlatnoscWaluta.findByPodRokKw").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("mc", mce.get(0)).setParameter("mc1", mce.get(1)).setParameter("mc2", mce.get(2)).getResultList();
    }
    
    public List<PlatnoscWaluta> findByPodRok(Podatnik podatnikObiekt, String rokWpisuSt) {
        return sessionFacade.getEntityManager().createNamedQuery("PlatnoscWaluta.findByPodRok").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).getResultList();
    }
}
