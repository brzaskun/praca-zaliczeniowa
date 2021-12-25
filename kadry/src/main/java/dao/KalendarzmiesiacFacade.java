/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Dzien;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Umowa;
import java.io.Serializable;
import java.util.ArrayList;
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
public class KalendarzmiesiacFacade  extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "kadryPU")
    private EntityManager em;
    
    @PreDestroy
    private void preDestroy() {
        em.clear();
        em.close();
        em.getEntityManagerFactory().close();
        em = null;
        
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public KalendarzmiesiacFacade() {
        super(Kalendarzmiesiac.class);
        super.em = em;
    }

   
   public void edit(Kalendarzmiesiac entity) {
        entity.setNorma(0.0);
        entity.setPrzepracowane(0.0);
        entity.setUrlop(0.0);
        entity.setUrlopbezplatny(0.0);
        entity.setChoroba(0.0);
        entity.setZasilek(0.0);
        entity.setPiecdziesiatka(0.0);
        entity.setSetka(0.0);
        entity.setPoranocna(0.0);
        for (Dzien p : entity.getDzienList()) {
            entity.setNorma(entity.getNorma()+p.getNormagodzin());
            entity.setPrzepracowane(entity.getPrzepracowane()+p.getPrzepracowano());
            entity.setUrlop(entity.getUrlop()+p.getUrlopPlatny());
            entity.setUrlopbezplatny(entity.getUrlopbezplatny()+p.getUrlopbezplatny());
            entity.setChoroba(entity.getChoroba()+p.getWynagrodzeniezachorobe());
            entity.setZasilek(entity.getZasilek()+p.getZasilek());
            entity.setPiecdziesiatka(entity.getPiecdziesiatka()+p.getPiecdziesiatki());
            entity.setSetka(entity.getSetka()+p.getSetki());
            entity.setPoranocna(entity.getPoranocna()+p.getPoranocna());
        }
        super.edit(entity);
    }
   
   public void create(Kalendarzmiesiac entity) {
        entity.setNorma(0.0);
        entity.setPrzepracowane(0.0);
        entity.setUrlop(0.0);
        entity.setUrlopbezplatny(0.0);
        entity.setChoroba(0.0);
        entity.setZasilek(0.0);
        entity.setPiecdziesiatka(0.0);
        entity.setSetka(0.0);
        entity.setPoranocna(0.0);
        for (Dzien p : entity.getDzienList()) {
            entity.setNorma(entity.getNorma()+p.getNormagodzin());
            entity.setPrzepracowane(entity.getPrzepracowane()+p.getPrzepracowano());
            entity.setUrlop(entity.getUrlop()+p.getUrlopPlatny());
            entity.setUrlopbezplatny(entity.getUrlopbezplatny()+p.getUrlopbezplatny());
            entity.setChoroba(entity.getChoroba()+p.getWynagrodzeniezachorobe());
            entity.setZasilek(entity.getZasilek()+p.getZasilek());
            entity.setPiecdziesiatka(entity.getPiecdziesiatka()+p.getPiecdziesiatki());
            entity.setSetka(entity.getSetka()+p.getSetki());
            entity.setPoranocna(entity.getPoranocna()+p.getPoranocna());
        }
        super.create(entity);
    }
   
    public Kalendarzmiesiac findByRokMcUmowa(Umowa umowa, String rok, String mc) {
        Kalendarzmiesiac zwrot = null;
        try {
            zwrot = (Kalendarzmiesiac) getEntityManager().createNamedQuery("Kalendarzmiesiac.findByRokMcUmowa").setParameter("rok", rok).setParameter("mc", mc).setParameter("umowa", umowa).getSingleResult();
        } catch (Exception e) {}
        return zwrot;
    }
    
    public List<Kalendarzmiesiac> findByRokUmowa(Umowa umowa, String rok) {
        List<Kalendarzmiesiac> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Kalendarzmiesiac.findByRokUmowa").setParameter("rok", rok).setParameter("umowa", umowa).getResultList();
        } catch (Exception e) {}
        return zwrot;
    }

    public List<Kalendarzmiesiac> findByFirmaRokMc(FirmaKadry firma, String rok, String mc) {
        List<Kalendarzmiesiac> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Kalendarzmiesiac.findByFirmaRokMc").setParameter("rok", rok).setParameter("mc", mc).setParameter("firma", firma).getResultList();
        } catch (Exception e) {}
        return zwrot;
    }
    
    public List<Kalendarzmiesiac> findByFirmaRokMcPraca(FirmaKadry firma, String rok, String mc) {
        List<Kalendarzmiesiac> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Kalendarzmiesiac.findByFirmaRokMcPraca").setParameter("rok", rok).setParameter("mc", mc).setParameter("firma", firma).getResultList();
        } catch (Exception e) {}
        return zwrot;
    }
    
    public List<Kalendarzmiesiac> findByFirmaRokMcZlecenie(FirmaKadry firma, String rok, String mc) {
        List<Kalendarzmiesiac> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Kalendarzmiesiac.findByFirmaRokMcZlecenie").setParameter("rok", rok).setParameter("mc", mc).setParameter("firma", firma).getResultList();
        } catch (Exception e) {}
        return zwrot;
    }
    
    public List<Kalendarzmiesiac> findByFirmaRokMcFunkcja(FirmaKadry firma, String rok, String mc) {
        List<Kalendarzmiesiac> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Kalendarzmiesiac.findByFirmaRokMcFunkcja").setParameter("rok", rok).setParameter("mc", mc).setParameter("firma", firma).getResultList();
        } catch (Exception e) {}
        return zwrot;
    }
    
    public List<Kalendarzmiesiac> findByFirmaRokMcNierezydent(FirmaKadry firma, String rok, String mc) {
        List<Kalendarzmiesiac> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Kalendarzmiesiac.findByFirmaRokMcNierezydent").setParameter("rok", rok).setParameter("mc", mc).setParameter("firma", firma).getResultList();
        } catch (Exception e) {}
        return zwrot;
    }

   
}
