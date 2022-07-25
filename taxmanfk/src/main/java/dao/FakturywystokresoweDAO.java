/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Faktura;
import entity.Fakturyokresowe;
import entity.Fakturywystokresowe;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class FakturywystokresoweDAO  extends DAO implements Serializable {
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

    public FakturywystokresoweDAO() {
        super(Fakturyokresowe.class);
        super.em = this.em;
    }


   
    public Fakturywystokresowe findFakturaOkresowaById(Integer id){
        try {
            return (Fakturywystokresowe)  getEntityManager().createNamedQuery("Fakturywystokresowe.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
//    public List<Fakturywystokresowe> findPodatnik(String podatnik){
//        List<Fakturywystokresowe> zwrot = Collections.synchronizedList(new ArrayList<>());
//        try {
//            zwrot = fakturywystokresoweFacade.findPodatnikFaktury(podatnik);
//        } catch (Exception e) { E.e(e); }
//        return zwrot;
//    }
    
//     public List<Fakturywystokresowe> findPodatnik(String podatnik, String rok){
//        List<Fakturywystokresowe> zwrot = Collections.synchronizedList(new ArrayList<>());
//        try {
//            zwrot = fakturywystokresoweFacade.findPodatnikRokFaktury(podatnik, rok);
//        } catch (Exception e) { E.e(e); }
//        return zwrot;
//    }
 
    
     public List<Fakturywystokresowe> findPodatnikBiezace(String podatnik, String rok){
        List<Fakturywystokresowe> zwrot = Collections.synchronizedList(new ArrayList<>());
        try {
            zwrot = getEntityManager().createNamedQuery("Fakturywystokresowe.findByPodatnikRokBiezace").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
        } catch (Exception e) { 
            E.e(e); }
        return zwrot;
    }

    public Fakturywystokresowe findOkresowa(String rok, String klientnip, String nazwapelna, double brutto) {
        try {
            return (Fakturywystokresowe)  getEntityManager().createNamedQuery("Fakturywystokresowe.findByOkresowa").setParameter("rok", rok).setParameter("podatnik", nazwapelna).setParameter("nipodbiorcy", klientnip).setParameter("brutto", brutto).getSingleResult();
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    
  
     public List<Fakturywystokresowe> findOkresoweOstatnie(String podatnik, String mc, String rok) {
        try {
            switch (mc) {
            case "01":
                return Collections.synchronizedList( getEntityManager().createNamedQuery("Fakturywystokresowe.findByM1").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "02":
                return Collections.synchronizedList( getEntityManager().createNamedQuery("Fakturywystokresowe.findByM2").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "03":
                return Collections.synchronizedList( getEntityManager().createNamedQuery("Fakturywystokresowe.findByM3").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "04":
                return Collections.synchronizedList( getEntityManager().createNamedQuery("Fakturywystokresowe.findByM4").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "05":
                return Collections.synchronizedList( getEntityManager().createNamedQuery("Fakturywystokresowe.findByM5").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "06":
                return Collections.synchronizedList( getEntityManager().createNamedQuery("Fakturywystokresowe.findByM6").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "07":
                return Collections.synchronizedList( getEntityManager().createNamedQuery("Fakturywystokresowe.findByM7").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "08":
                return Collections.synchronizedList( getEntityManager().createNamedQuery("Fakturywystokresowe.findByM8").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "09":
                return Collections.synchronizedList( getEntityManager().createNamedQuery("Fakturywystokresowe.findByM9").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "10":
                return Collections.synchronizedList( getEntityManager().createNamedQuery("Fakturywystokresowe.findByM10").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "11":
                return Collections.synchronizedList( getEntityManager().createNamedQuery("Fakturywystokresowe.findByM11").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "12":
                return Collections.synchronizedList( getEntityManager().createNamedQuery("Fakturywystokresowe.findByM12").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
        }
        return null;
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }


    public List<Fakturywystokresowe> findFakturaOkresowaByFaktura(Faktura p) {
        try {
            return getEntityManager().createNamedQuery("Fakturywystokresowe.findByFaktura").setParameter("faktura", p).getResultList();
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
}
