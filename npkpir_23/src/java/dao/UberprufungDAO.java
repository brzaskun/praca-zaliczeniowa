/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;


import entity.Podatnik;
import entity.Uberprufung;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

@Stateless
@Transactional
public class UberprufungDAO extends DAO<Uberprufung> implements Serializable {
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

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public UberprufungDAO() {
        super(Uberprufung.class);
        super.em = this.em;
    }

    // Metoda do wyszukiwania encji Uberprufung na podstawie obiektu Podatnik
    public List<Uberprufung> findByPodatnik(Podatnik podatnik) {
        TypedQuery<Uberprufung> query = em.createNamedQuery("Uberprufung.findByPodatnik", Uberprufung.class);
        query.setParameter("podatnik", podatnik);
        return query.getResultList();
    }
}
