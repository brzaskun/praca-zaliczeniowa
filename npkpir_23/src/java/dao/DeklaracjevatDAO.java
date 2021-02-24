/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Deklaracjevat;
import entity.Podatnik;
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
import view.WpisView;
/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class DeklaracjevatDAO extends DAO implements Serializable{

    //tablica wciagnieta z bazy danych
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

    public DeklaracjevatDAO() {
        super(Deklaracjevat.class);
        super.em = this.em;
    }


    public List<Deklaracjevat> findDeklaracjewszystkie(String rok, String mc, String pod){
        return getEntityManager().createNamedQuery("Deklaracjevat.findByRokMcPod").setParameter("rok", rok).setParameter("miesiac", mc).setParameter("podatnik", pod).getResultList();
    }

    public Deklaracjevat findDeklaracjeDowyslania(String pod){
        try {
        return (Deklaracjevat)  getEntityManager().createNamedQuery("Deklaracjevat.findByPodatnikWysylka").setParameter("podatnik", pod).setParameter("identyfikator", "").getSingleResult();
        } catch (Exception e) { 
            E.e(e); 
            return null;
        }
    }
    

    public List<Deklaracjevat> findDeklaracjeDowyslaniaList(String pod){
        try {
            return getEntityManager().createNamedQuery("Deklaracjevat.findByPodatnikWysylka").setParameter("podatnik", pod).setParameter("identyfikator", "").getResultList();
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

    public Deklaracjevat findDeklaracjeDopotwierdzenia(String identyfikator, Podatnik podatnik) {
        List<Deklaracjevat> temp = getEntityManager().createNamedQuery("Deklaracjevat.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
        Deklaracjevat wynik = new Deklaracjevat();
        for(Deklaracjevat p :temp){
            if(p.getIdentyfikator() != null && p.getIdentyfikator().equals(identyfikator)){
                wynik = p;
                break;
            }
        }
        return wynik;
    }
    

    public List<Deklaracjevat> findDeklaracjeWyslane(String pod) {
        return getEntityManager().createNamedQuery("Deklaracjevat.findByPodatnikWyslane").setParameter("podatnik", pod).setParameter("identyfikator", "").getResultList();
    }
      
  
    public List<Deklaracjevat> findDeklaracjeWyslaneMc(String podatnik, String rok, String mc) {
       List<Deklaracjevat> znalezionedeklaracje = new ArrayList<>();
        try {
            znalezionedeklaracje = getEntityManager().createNamedQuery("Deklaracjevat.findByPodatnikWyslaneRokMc").setParameter("podatnik", podatnik).setParameter("identyfikator", "").setParameter("rok", rok).setParameter("mc", mc).getResultList();
        } catch (Exception e) { 
            E.e(e);
        }
        return znalezionedeklaracje;
    }
    
    
    public List<Deklaracjevat> findDeklaracjeWyslaneMcJPK(String podatnik, String rok, String mc) {
       List<Deklaracjevat> znalezionedeklaracje = Collections.synchronizedList(new ArrayList<>());
        try {
            znalezionedeklaracje = getEntityManager().createNamedQuery("Deklaracjevat.findByPodatnikWyslaneRokMcJPK").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList();
        } catch (Exception e) { 
            E.e(e);
        }
        return znalezionedeklaracje;
    }
    

    public List<Deklaracjevat> findDeklaracjeWyslane(String pod, String rok) {
       List<Deklaracjevat> znalezionedeklaracje = Collections.synchronizedList(new ArrayList<>());
       try {
            znalezionedeklaracje = getEntityManager().createNamedQuery("Deklaracjevat.findByPodatnikWyslaneRok").setParameter("podatnik", pod).setParameter("identyfikator", "").setParameter("rok", rok).getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
       return znalezionedeklaracje;
    }


    public List<Deklaracjevat> findDeklaracjeWyslane200RokMc(String rok, String mc) {
       List<Deklaracjevat> znalezionedeklaracje = Collections.synchronizedList(new ArrayList<>());
       try {
            znalezionedeklaracje = getEntityManager().createNamedQuery("Deklaracjevat.findByPodatnikWyslaneRokMc200").setParameter("status", "200").setParameter("rok", rok).setParameter("mc", mc).getResultList();
        } catch (Exception e) { 
            E.e(e); 
        }
       return znalezionedeklaracje;
    }
    
    public Deklaracjevat findDeklaracjeWyslane303PodRokMc(String podatnik,String rok, String mc) {
       Deklaracjevat zwrot = null;
       try {
            zwrot =  (Deklaracjevat) getEntityManager().createNamedQuery("Deklaracjevat.findByPodatnikWyslaneRokMc303").setParameter("status", "399").setParameter("rok", rok).setParameter("mc", mc).setParameter("podatnik", podatnik).getSingleResult();
        } catch (Exception e) { 
            E.e(e); 
        }
       return zwrot;
    }

    public List<Deklaracjevat> findDeklaracjeWyslane200(String pod, String rok) {
       List<Deklaracjevat> znalezionedeklaracje = Collections.synchronizedList(new ArrayList<>());
       try {
            znalezionedeklaracje = getEntityManager().createNamedQuery("Deklaracjevat.findByPodatnikWyslaneRok200").setParameter("podatnik", pod).setParameter("identyfikator", "").setParameter("rok", rok).getResultList();
        } catch (Exception e) { E.e(e); }
       //szuka zawsze w roku poprzednim. jak nie ma wywala blad
       if (znalezionedeklaracje.isEmpty()) {
        String rokuprzedni = String.valueOf(Integer.parseInt(rok)-1);
        znalezionedeklaracje = getEntityManager().createNamedQuery("Deklaracjevat.findByPodatnikWyslaneRok200").setParameter("podatnik", pod).setParameter("identyfikator", "").setParameter("rok", rokuprzedni).getResultList();
       }
       return znalezionedeklaracje;
    }

    
    public List<Deklaracjevat> findDeklaracjewysylka(String rok, String mc) {
         return getEntityManager().createNamedQuery("Deklaracjevat.findByRokMc").setParameter("rok", rok).setParameter("miesiac", mc).getResultList();
    }
    
    
    public Deklaracjevat findDeklaracjaPodatnik(String identyfikator, String podatnik) {
         return (Deklaracjevat)  getEntityManager().createNamedQuery("Deklaracjevat.findByIdentyfikatorPodatnik").setParameter("identyfikator", identyfikator).setParameter("podatnik", podatnik).getSingleResult();
    }

    public Deklaracjevat findDeklaracjeDopotwierdzenia(WpisView wpisView) {
        List<Deklaracjevat> temp = getEntityManager().createNamedQuery("Deklaracjevat.findByPodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList();
        Deklaracjevat wynik = null;
        String sporzadzil = wpisView.getUzer().getImie()+" "+wpisView.getUzer().getNazw();
        for(Deklaracjevat p :temp){
            if(p.getStatus().startsWith("3") && p.getSporzadzil()!= null && p.getSporzadzil().equals(sporzadzil)){
                wynik = p;
            }
        }
        return wynik;
    }

 
}
