/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dao;

import entity.Podatnik;
import entityfk.Konto;
import entityfk.WierszBO;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Stateless
@Transactional

public class WierszBODAO extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
  @PersistenceContext(unitName = "npkpir_22PU")
    private EntityManager em;
    
    @PreDestroy
    private void preDestroy() {
        em.clear();
        em.close();
        em.getEntityManagerFactory().close();
        em = null;
        error.E.s("koniec jpa");
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public WierszBODAO() {
        super(WierszBO.class);
        super.em = this.em;
    }   
    public List<WierszBO> lista(String grupa, WpisView wpisView, boolean likwidacja) {
        List<WierszBO> zwrot = new ArrayList<>();
        try {
            if (likwidacja) {
                zwrot  = getEntityManager().createNamedQuery("WierszBO.findByListaLikwidacja").setParameter("grupakonta", grupa).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList();
    
            } else {
                zwrot = getEntityManager().createNamedQuery("WierszBO.findByLista").setParameter("grupakonta", grupa).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList();
            }
        } catch (Exception e) { E.e(e); 
            
        }
        return zwrot;
    }
    
    
    
    public List<WierszBO> listaRokMc(WpisView wpisView) {
        List<WierszBO> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("WierszBO.findByListaRokMc").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList();
        } catch (Exception e) { E.e(e); 
            
        }
        return zwrot;
    }
  
    
    public List<WierszBO> findPodatnikRok(Podatnik podatnik, String rok) {
        List<WierszBO> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("WierszBO.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
        } catch (Exception e) { E.e(e); 
            
        }
        return zwrot;
    }
    
    public int deletePodatnikRok(Podatnik podatnik, String rok) {
        return getEntityManager().createNamedQuery("WierszBO.findByDeletePodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).executeUpdate();
    }
    
    public int deletePodatnikRokMc(Podatnik podatnik, String rok, String mc) {
        return getEntityManager().createNamedQuery("WierszBO.findByDeletePodatnikRokMc").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("mc", mc).executeUpdate();
    }
    
    public List<WierszBO> findPodatnikRokRozrachunkowe(Podatnik podatnik, String rok) {
        List<WierszBO> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("WierszBO.findByPodatnikRokRozrachunkowe").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
        } catch (Exception e) { E.e(e); 
            
        }
        return zwrot;
    }
    //jets lista bo w BO moze byc klika wierszy z tym samym kontem
    public List<WierszBO> findPodatnikRokKonto(Podatnik podatnikObiekt, String rokWpisuSt, Konto konto) {
        List<WierszBO> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("WierszBO.findByPodatnikRokKonto").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("konto", konto).getResultList();
        } catch (Exception e) { E.e(e); 
            
        }
        return zwrot;
    }
    
    public List<WierszBO> findPodatnikRokKontoWaluta(Podatnik podatnikObiekt, String rokWpisuSt, Konto konto, String waluta) {
        List<WierszBO> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("WierszBO.findByPodatnikRokKontoWaluta").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("konto", konto).setParameter("waluta", waluta).getResultList();
        } catch (Exception e) { E.e(e); 
            
        }
        return zwrot;
    }
    
     public WierszBO findById(int id) {
        WierszBO zwrot = null;
        try {
            zwrot = (WierszBO) getEntityManager().createNamedQuery("WierszBO.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) { E.e(e); 
            
        }
        return zwrot;
    }
    
    
}
