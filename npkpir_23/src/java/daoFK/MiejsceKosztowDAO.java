/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoFK;

import dao.DAO;
import entity.Podatnik;
import entityfk.MiejsceKosztow;
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

public class MiejsceKosztowDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public MiejsceKosztowDAO() {
        super(MiejsceKosztow.class);
    }
    
    public MiejsceKosztowDAO(Class entityClass) {
        super(entityClass);
    }
    

    public List<MiejsceKosztow> findAll() {
        return sessionFacade.findAll(MiejsceKosztow.class);
    }

    public List<MiejsceKosztow> findMiejscaPodatnik(Podatnik podatnikObiekt) {
        return sessionFacade.findMiejscaPodatnik(podatnikObiekt);
    }

    public int countMiejscaKosztow(Podatnik podatnikObiekt) {
        return (int) sessionFacade.countMiejscaKosztow(podatnikObiekt);
    }
}
