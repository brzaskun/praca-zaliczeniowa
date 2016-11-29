/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Dok;
import entity.Evopis;
import entity.PlatnoscWaluta;
import entity.Podatnik;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

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
}
