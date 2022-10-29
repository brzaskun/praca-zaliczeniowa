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
import z.Z;

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
        entity.setDnioddelegowania(0.0);
        for (Dzien p : entity.getDzienList()) {
            entity.setNorma(Z.z(entity.getNorma()+p.getNormagodzin()));
            entity.setPrzepracowane(Z.z(entity.getPrzepracowane()+p.getPrzepracowano()));
            entity.setUrlop(Z.z(entity.getUrlop()+p.getUrlopPlatny()));
            entity.setUrlopbezplatny(Z.z(entity.getUrlopbezplatny()+p.getUrlopbezplatny()));
            entity.setChoroba(Z.z(entity.getChoroba()+p.getWynagrodzeniezachorobe()));
            entity.setZasilek(Z.z(entity.getZasilek()+p.getZasilek()));
            entity.setPiecdziesiatka(Z.z(entity.getPiecdziesiatka()+p.getPiecdziesiatki()));
            entity.setSetka(Z.z(entity.getSetka()+p.getSetki()));
            entity.setPoranocna(Z.z(entity.getPoranocna()+p.getPoranocna()));
            entity.setOpiekadziecko(Z.z(entity.getOpiekadziecko()+p.getOpiekadziecko()));
            entity.setMacierzynski(Z.z(entity.getMacierzynski()+p.getMacierzynski()));
            entity.setWychowawczy(Z.z(entity.getWychowawczy()+p.getWychowawczy()));
            if (p.getKod()!=null&&p.getKod().equals("Z")) {
                entity.setDnioddelegowania(Z.z(entity.getDnioddelegowania()+1));
            }
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
        entity.setDnioddelegowania(0.0);
        for (Dzien p : entity.getDzienList()) {
            entity.setNorma(Z.z(entity.getNorma()+p.getNormagodzin()));
            entity.setPrzepracowane(Z.z(entity.getPrzepracowane()+p.getPrzepracowano()));
            entity.setUrlop(Z.z(entity.getUrlop()+p.getUrlopPlatny()));
            entity.setUrlopbezplatny(Z.z(entity.getUrlopbezplatny()+p.getUrlopbezplatny()));
            entity.setChoroba(Z.z(entity.getChoroba()+p.getWynagrodzeniezachorobe()));
            entity.setZasilek(Z.z(entity.getZasilek()+p.getZasilek()));
            entity.setPiecdziesiatka(Z.z(entity.getPiecdziesiatka()+p.getPiecdziesiatki()));
            entity.setSetka(Z.z(entity.getSetka()+p.getSetki()));
            entity.setPoranocna(Z.z(entity.getPoranocna()+p.getPoranocna()));
            entity.setOpiekadziecko(Z.z(entity.getOpiekadziecko()+p.getOpiekadziecko()));
            entity.setMacierzynski(Z.z(entity.getMacierzynski()+p.getMacierzynski()));
            entity.setWychowawczy(Z.z(entity.getWychowawczy()+p.getWychowawczy()));
            if (p.getKod()!=null&&p.getKod().equals("Z")) {
                entity.setDnioddelegowania(Z.z(entity.getDnioddelegowania()+1));
            }
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
    
    public List<Kalendarzmiesiac> findByUmowa(Umowa umowa) {
        List<Kalendarzmiesiac> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Kalendarzmiesiac.findByUmowa").setParameter("umowa", umowa).getResultList();
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
