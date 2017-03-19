/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.FakturaWalutaKonto;
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
public class FakturaWalutaKontoDAO extends DAO implements Serializable{
    private static final long serialVersionUID = 1L;

    public FakturaWalutaKontoDAO() {
    }

    
    public FakturaWalutaKontoDAO(Class entityClass) {
        super(entityClass);
    }
    
    public List<FakturaWalutaKonto> findAll() {
        return sessionFacade.findAll(FakturaWalutaKonto.class);
    }

    public List<FakturaWalutaKonto> findPodatnik(WpisView wpisView) {
        return sessionFacade.getEntityManager().createNamedQuery("FakturaWalutaKonto.findByPodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList();
    }
    
    public List<FakturaWalutaKonto> findPodatnikAktywne(WpisView wpisView) {
        return sessionFacade.getEntityManager().createNamedQuery("FakturaWalutaKonto.findByPodatnikAktywne").setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList();
    }
}
