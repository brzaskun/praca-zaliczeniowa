/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package daoFK;

import dao.DAO;
import entityfk.Rozrachunekfk;
import entityfk.Transakcja;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class TransakcjaDAO  extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private SessionFacade sessionFacade;

    public TransakcjaDAO(Class entityClass) {
        super(entityClass);
    }

    public TransakcjaDAO() {
        super(Transakcja.class);
    }
    
    public void usunniezaksiegowane(String podatnik) {
        try {
            sessionFacade.usunTransakcjeNiezaksiegowane(podatnik);
        } catch (Exception e) {
            
        }
    }
    
    //odnajduje istniejaca transakcje i ja edytuje. kluczem transakji jest boiem id a nie para sparowany/rozliczany
    public void edytujTransakcje(Transakcja transakcja) {
        int rozliczany = transakcja.getRozliczany().getIdrozrachunku();
        int sparowany = transakcja.getSparowany().getIdrozrachunku();
        Transakcja poszukiwanaTransakcja = sessionFacade.findTransakcja(rozliczany,sparowany);
        if (poszukiwanaTransakcja instanceof Transakcja) {
            transakcja.setId(poszukiwanaTransakcja.getId());
            sessionFacade.edit(transakcja);
        } else {
            sessionFacade.create(transakcja);
        }
    }

    public List<Transakcja> findByRozliczonyID(int idrozrachunku) {
        return sessionFacade.findTransakcjeRozliczonyID(idrozrachunku);
    }
    
    public List<Transakcja> findBySparowanyID(int idrozrachunku) {
        List<Transakcja> odnalezione = new ArrayList<>();
        List<Transakcja> transakcje = sessionFacade.findAll(Transakcja.class);
        for(Transakcja f : transakcje) {
            Map<Boolean, Rozrachunekfk> roz = f.getRozrachunki();
            int idroz = ((Rozrachunekfk) roz.get(true)).getIdrozrachunku();
            if (idroz == idrozrachunku && f.getKwotatransakcji() > 0) {
                odnalezione.add(f);
            }
        }
        return odnalezione;
    }
    
    
}
