/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.JPKoznaczenia;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class JPKOznaczeniaDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;

    public JPKOznaczeniaDAO() {
        super(JPKoznaczenia.class);
    }
    
    public List findAll() {
        return sessionFacade.findAll(JPKoznaczenia.class);
    }
    
    public List<JPKoznaczenia> findByKlasa(int klasa) {
        List<JPKoznaczenia> zwrot = new ArrayList<>();
        try {
            zwrot = sessionFacade.getEntityManager().createNamedQuery("JPKoznaczenia.findyByKlasa").setParameter("klasa", klasa).getResultList();
        } catch (Exception e) {
            
        }
        return zwrot;
    }
    
    
}
