/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.EVatwpisDedra;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;
import view.WpisView;
/**
 *
 * @author Osito
 */
public class EVatwpisDedraDAO   extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject private SessionFacade sessionFacade;

    public EVatwpisDedraDAO() {
        super(EVatwpisDedra.class);
    }
    public List<EVatwpisDedra> findWierszePodatnikMc(WpisView wpisView) {
        return sessionFacade.getEntityManager().createNamedQuery("EVatwpisDedra.findByPodatnikRokMc").setParameter("podatnik",wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList();
    }
    
    public List<EVatwpisDedra> findWierszePodatnikMcZakupSprzedaz(WpisView wpisView, boolean zakup0sprzedaz1) {
        if (zakup0sprzedaz1) {
            return sessionFacade.getEntityManager().createNamedQuery("EVatwpisDedra.findByPodatnikRokMcSprzedaz").setParameter("podatnik",wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).setParameter("nazwa", "zakup").getResultList();
        } else {
            return sessionFacade.getEntityManager().createNamedQuery("EVatwpisDedra.findByPodatnikRokMcZakup").setParameter("podatnik",wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).setParameter("nazwa", "zakup").getResultList();
        }
    }
    public List<EVatwpisDedra> findAll() {
        return sessionFacade.findAll(EVatwpisDedra.class);
    }
}
