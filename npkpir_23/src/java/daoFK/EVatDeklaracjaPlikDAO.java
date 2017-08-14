/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.EVatDeklaracjaPlik;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;
import view.WpisView;

/**
 *
 * @author Osito
 */
public class EVatDeklaracjaPlikDAO   extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject private SessionFacade sessionFacade;

    public EVatDeklaracjaPlikDAO() {
        super(EVatDeklaracjaPlik.class);
    }

    public List<EVatDeklaracjaPlik> findDeklaracjePodatnikMc(WpisView wpisView) {
        return sessionFacade.getEntityManager().createNamedQuery("EVatDeklaracjaPlik.findByPodatnikRok").setParameter("podatnik",wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList();
    }
     public List<EVatDeklaracjaPlik> findAll() {
        return sessionFacade.findAll(EVatDeklaracjaPlik.class);
    }
    
}
