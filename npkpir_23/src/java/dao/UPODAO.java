/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.UPO;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
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
public class UPODAO extends DAO implements Serializable{
    
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

    public UPODAO() {
        super(UPO.class);
        super.em = this.em;
    }

    
    public List<UPO> findPodatnikRok(WpisView wpisView) {
        try {
            List<UPO> lista = getEntityManager().createNamedQuery("UPO.findUPOPodatnikRok").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList();
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }


    public List<UPO> findUPORokMc(WpisView wpisView) {
        try {
            List<UPO> lista = getEntityManager().createNamedQuery("UPO.findUPORokMc").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList();
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<UPO> findUPORokMc(String rok, String mc) {
        try {
            List<UPO> lista = getEntityManager().createNamedQuery("UPO.findUPORokMc").setParameter("rok", rok).setParameter("mc", mc).getResultList();
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    public List<UPO> findUPOBez200() {
        try {
            List<UPO> lista = getEntityManager().createNamedQuery("UPO.findUPOBez200").getResultList();
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

   public List<UPO> findUPORokMCDataKsiegowania(String roks, String mcs){
        List<UPO> zwrot = new ArrayList<>();
        try {
            int rok = Integer.parseInt(roks);
            int mc = Integer.parseInt(mcs) - 1; // Miesiące w Calendar są indeksowane od 0

             // Początek miesiąca
            Calendar start = new GregorianCalendar(rok, mc, 1, 0, 0, 0);
            Date startDate = start.getTime();

            // Pierwszy dzień kolejnego miesiąca
            Calendar end = new GregorianCalendar(rok, mc+1, 1, 0, 0, 0);
            Date endDate = end.getTime();

            zwrot = getEntityManager().createQuery("SELECT d FROM UPO d WHERE d.dataupo >= :startDate AND d.dataupo < :endDate", UPO.class)
                                .setParameter("startDate", startDate)
                                .setParameter("endDate", endDate)
                                .getResultList();
        } catch (Exception e) {
        }
        return zwrot;
    }
    
    
}
