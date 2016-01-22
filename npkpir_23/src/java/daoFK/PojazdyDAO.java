/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoFK;

import dao.DAO;
import entity.Podatnik;
import entityfk.Pojazdy;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named

public class PojazdyDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public PojazdyDAO() {
        super(Pojazdy.class);
    }
    
    public PojazdyDAO(Class entityClass) {
        super(entityClass);
    }
    

    public List<Pojazdy> findAll() {
        return sessionFacade.findAll(Pojazdy.class);
    }

    public List<Pojazdy> findPojazdyPodatnik(Podatnik podatnikObiekt) {
        return sessionFacade.findPojazdyPodatnik(podatnikObiekt);
    }

    public int countPojazdy(Podatnik podatnikObiekt) {
        return (int) sessionFacade.countPojazdy(podatnikObiekt);
    }
}
