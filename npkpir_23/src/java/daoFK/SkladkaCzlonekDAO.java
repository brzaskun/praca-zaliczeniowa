/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.SkladkaCzlonek;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
public class SkladkaCzlonekDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;

    public SkladkaCzlonekDAO() {
    }

    
    public SkladkaCzlonekDAO(Class entityClass) {
        super(entityClass);
    }
    
    public List<SkladkaCzlonek> findAll() {
        return sessionFacade.findAll(SkladkaCzlonek.class);
    }

    public List<SkladkaCzlonek> findPodatnikRok(WpisView wpisView) {
        return sessionFacade.getEntityManager().createNamedQuery("SkladkaCzlonek.findByPodatnikRok").setParameter("podatnikObj", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList();
    }
    
}
