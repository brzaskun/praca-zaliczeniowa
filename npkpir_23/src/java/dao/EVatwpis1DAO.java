/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.Kwartaly;
import entity.EVatwpis1;
import entity.Podatnik;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class EVatwpis1DAO  extends DAO implements Serializable {

    @Inject
    private SessionFacade sessionFacade;

    public  List<EVatwpis1> findAll(){
        try {
            return sessionFacade.findAll(EVatwpis1.class);
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
   }

    public List<EVatwpis1> zwrocBiezacegoKlientaRokMcKasowe(Podatnik podatnikWpisu, String rokWpisuSt, String miesiacWpisu) {
        return sessionFacade.getEntityManager().createNamedQuery("EVatwpis1.findByRokMcKasowe").setParameter("podatnik", podatnikWpisu).setParameter("pkpirR", rokWpisuSt).setParameter("mc", miesiacWpisu).getResultList();
    }
    
    public List<EVatwpis1> zwrocBiezacegoKlientaRokMc(Podatnik podatnikWpisu, String rokWpisuSt, String miesiacWpisu) {
        return sessionFacade.zwrocEVatwpis1KlientRokMc(podatnikWpisu, rokWpisuSt, miesiacWpisu);
    }

    public List<EVatwpis1> zwrocBiezacegoKlientaRokKWKasowe(Podatnik podatnikWpisu, String rokWpisuSt, String miesiacWpisu) {
        List<String> mce = Kwartaly.mctoMcewKw(miesiacWpisu);
        return sessionFacade.getEntityManager().createNamedQuery("EVatwpis1.findByRokKWKasowe").setParameter("podatnik", podatnikWpisu).setParameter("pkpirR", rokWpisuSt).setParameter("mc1", mce.get(0)).setParameter("mc2", mce.get(1)).setParameter("mc3", mce.get(2)).getResultList();
    }
    
    public List<EVatwpis1> zwrocBiezacegoKlientaRokKW(Podatnik podatnikWpisu, String rokWpisuSt, String miesiacWpisu) {
        List<String> mce = Kwartaly.mctoMcewKw(miesiacWpisu);
        return sessionFacade.zwrocEVatwpis1KlientRokKw(podatnikWpisu, rokWpisuSt, mce);
    }
}
