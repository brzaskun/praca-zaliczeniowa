/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.Kwartaly;
import entity.EVatwpis1;
import entity.Podatnik;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
public class EVatwpis1DAO  extends DAO implements Serializable {

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

    public EVatwpis1DAO() {
        super(EVatwpis1.class);
        super.em = this.em;
    }


    public List<EVatwpis1> zwrocBiezacegoKlientaRokMcKasowe(Podatnik podatnikWpisu, String rokWpisuSt, String miesiacWpisu) {
        return getEntityManager().createNamedQuery("EVatwpis1.findByRokMcKasowe").setParameter("podatnik", podatnikWpisu).setParameter("pkpirR", rokWpisuSt).setParameter("mc", miesiacWpisu).getResultList();
    }

    public List<EVatwpis1> zwrocBiezacegoKlientaRokMc(Podatnik podatnikWpisu, String rokWpisuSt, String miesiacWpisu) {
        return getEntityManager().createNamedQuery("EVatwpis1.findByRokMc").setParameter("podatnik", podatnikWpisu).setParameter("pkpirR", rokWpisuSt).setParameter("mc", miesiacWpisu).getResultList();
    }

    public List<EVatwpis1> zwrocBiezacegoKlientaRokKWKasowe(Podatnik podatnikWpisu, String rokWpisuSt, String miesiacWpisu) {
        List<String> mce = Kwartaly.mctoMcewKw(miesiacWpisu);
        return getEntityManager().createNamedQuery("EVatwpis1.findByRokKWKasowe").setParameter("podatnik", podatnikWpisu).setParameter("pkpirR", rokWpisuSt).setParameter("mc1", mce.get(0)).setParameter("mc2", mce.get(1)).setParameter("mc3", mce.get(2)).getResultList();
    }
    
    public List<EVatwpis1> zwrocRok(String rokWpisuSt) {
        return getEntityManager().createNamedQuery("EVatwpis1.findByRok").setParameter("rok", rokWpisuSt).getResultList();
    }
    
    public List<EVatwpis1> zwrocRokMc(String rokWpisuSt, String mc) {
        return getEntityManager().createNamedQuery("EVatwpis1.findByMcRok").setParameter("rok", rokWpisuSt).setParameter("mc", mc).getResultList();
    }
    public List<EVatwpis1> zwrocNULL() {
        return getEntityManager().createNamedQuery("EVatwpis1.findByNULL").getResultList();
    }

    public List<EVatwpis1> zwrocBiezacegoKlientaRokKW(Podatnik podatnikWpisu, String rokWpisuSt, String miesiacWpisu) {
        List<String> mce = Kwartaly.mctoMcewKw(miesiacWpisu);
        return getEntityManager().createNamedQuery("EVatwpis1.findByRokKW").setParameter("podatnik", podatnikWpisu).setParameter("pkpirR", rokWpisuSt).setParameter("mc1", mce.get(0)).setParameter("mc2", mce.get(1)).setParameter("mc3", mce.get(2)).getResultList();
    }
}
