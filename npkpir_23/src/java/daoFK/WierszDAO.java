/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entity.Podatnik;
import entityfk.Tabelanbp;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
public class WierszDAO extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private SessionFacade sessionFacade;
    
    public List<Wiersz> findAll(){
        return sessionFacade.findAll(Wiersz.class);
    }
    
    public List<Wiersz> findWierszeRok(String rok){
        return sessionFacade.findWierszeRok(rok);
    }
    
    public List<Wiersz> pobierzWiersze(Tabelanbp tabelanbp, Podatnik podatnik, String rok) {
        List<Wiersz> zwrot = null;
        try {
            zwrot = sessionFacade.getEntityManager().createNamedQuery("Wiersz.findByPodatnikRokTabela").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("tabelanbp", tabelanbp).getResultList();
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
    public List<Wiersz> pobierzWierszeMcDok(Podatnik podatnik, String rok, String mc, String rodzajdok) {
        List<Wiersz> zwrot = null;
        try {
            zwrot = sessionFacade.getEntityManager().createNamedQuery("Wiersz.findByPodatnikRokMcSeria").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("mc", mc).setParameter("rodzajdok", rodzajdok).getResultList();
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
    public List<Wiersz> pobierzWierszeMcDokImport(Podatnik podatnik, String rok) {
        List<Wiersz> zwrot = null;
        try {
            zwrot = sessionFacade.getEntityManager().createNamedQuery("Wiersz.findByPodatnikRokMcImport").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
    public List<Wiersz> pobierzWierszeRok(Podatnik podatnik, String rok) {
        List<Wiersz> zwrot = null;
        try {
            zwrot = sessionFacade.getEntityManager().createNamedQuery("Wiersz.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
        } catch (Exception e) {
            E.e(e);
        }
        return zwrot;
    }
    
}
