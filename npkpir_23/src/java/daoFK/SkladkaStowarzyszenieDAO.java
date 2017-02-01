/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.RodzajCzlonkostwa;
import entityfk.SkladkaStowarzyszenie;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
public class SkladkaStowarzyszenieDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;

    public SkladkaStowarzyszenieDAO() {
    }

    
    public SkladkaStowarzyszenieDAO(Class entityClass) {
        super(entityClass);
    }
    
    public List<SkladkaStowarzyszenie> findAll() {
        return sessionFacade.findAll(SkladkaStowarzyszenie.class);
    }

    public List<SkladkaStowarzyszenie> findByPodatnikRok(WpisView wpisView) {
        return sessionFacade.getEntityManager().createNamedQuery("SkladkaStowarzyszenie.findByPodatnikRok").setParameter("podatnikObj", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList();
    }
    
    public List<SkladkaStowarzyszenie> findByPodatnik(WpisView wpisView) {
        return sessionFacade.getEntityManager().createNamedQuery("SkladkaStowarzyszenie.findByPodatnik").setParameter("podatnikObj", wpisView.getPodatnikObiekt()).getResultList();
    }
    
}
