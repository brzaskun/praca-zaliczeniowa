/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoFK;

import dao.DAO;
import entity.Podatnik;
import entityfk.MiejscePrzychodow;
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

public class MiejscePrzychodowDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;

    public MiejscePrzychodowDAO() {
        super(MiejscePrzychodow.class);
    }
    
    public MiejscePrzychodowDAO(Class entityClass) {
        super(entityClass);
    }
    

    public List<MiejscePrzychodow> findAll() {
        return sessionFacade.findAll(MiejscePrzychodow.class);
    }

    public List<MiejscePrzychodow> findMiejscaPodatnikRok(Podatnik podatnikObiekt, int rok) {
        return sessionFacade.findMiejscaPrzychodowPodatnikRok(podatnikObiekt, rok);
    }
    
    public List<MiejscePrzychodow> findMiejscaPodatnik(Podatnik podatnikObiekt) {
        return sessionFacade.findMiejscaPrzychodowPodatnik(podatnikObiekt);
    }
    
    public List<MiejscePrzychodow> findCzlonkowieStowarzyszenia(Podatnik podatnikObiekt) {
        return sessionFacade.getEntityManager().createNamedQuery("MiejscePrzychodow.findCzlonekStowarzyszenia").setParameter("podatnik", podatnikObiekt).getResultList();
    }

    public int countMiejscaPrzychodow(Podatnik podatnikObiekt) {
        return (int) sessionFacade.countMiejscaPrzychodow(podatnikObiekt);
    }
}
