/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.Kwartaly;
import entity.Klienci;
import entity.Podatnik;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import session.SessionFacade;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class DokDAOfk extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject private SessionFacade sessionFacade;
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

    public DokDAOfk() {
        super(Dokfk.class);
        super.em = this.em;
    } 

  
    public void usun(Dokfk selected) {
        remove(selected);
    }

    public List findDokfkPodatnikRokMc(WpisView wpisView) {
        try {
           return Collections.synchronizedList(sessionFacade.findDokfkPodatnikRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()));
       } catch (Exception e ){
           return null;
       }
    }
    
    public List findDokfkPodatnikRokMc(Podatnik podatnik, String rok, String mc) {
        try {
           return Collections.synchronizedList(sessionFacade.findDokfkPodatnikRokMc(podatnik, rok, mc));
       } catch (Exception e ){
           return null;
       }
    }
  
    
    public List findDokfkPodatnikRokMcVAT(WpisView wpisView) {
        try {
           return Collections.synchronizedList(sessionFacade.findDokfkPodatnikRokMcVAT(wpisView));
       } catch (Exception e ){
           return null;
       }
    }
    
    public List findDokfkPodatnikRokKw(WpisView wpisView) {
        List<Dokfk> zwrot = Collections.synchronizedList(new ArrayList<>());
        try {
           String mcwpisu = wpisView.getMiesiacWpisu();
           List<String> mcekw = Kwartaly.mctoMcewKw(mcwpisu);
           zwrot =  Collections.synchronizedList(sessionFacade.findDokfkPodatnikRokKw(wpisView, mcekw));
       } catch (Exception e ){
           
       }
        return zwrot;
    }
    
    public List<Dokfk> findDokfkPodatnikRokKw(WpisView wpisView, String mcwpisu) {
        List<Dokfk> zwrot = Collections.synchronizedList(new ArrayList<>());
        try {
           List<String> mcekw = Kwartaly.mctoMcewKw(mcwpisu);
           zwrot =  Collections.synchronizedList(sessionFacade.findDokfkPodatnikRokKw(wpisView, mcekw));
       } catch (Exception e ){
           
       }
        return zwrot;
    }
    
    public List<Dokfk> findDokfkPodatnikRok(WpisView w) {
        try {
           return Collections.synchronizedList(sessionFacade.findDokfkPodatnikRok(w));
       } catch (Exception e ){
           return null;
       }
    }
    
//    public List<Dokfk> findDokfkPodatnikRokUnique(WpisView w) {
//        List<Dokfk> zwrot = new ArrayList<>();
//        try {
//           List<Dokfk> zwrot1 = Collections.synchronizedList(dokFacade.getEntityManager().createNamedQuery("Dokfk.findByPodatnikRokUnique").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).getResultList());
//           List<Dokfk> zwrot2 = Collections.synchronizedList(dokFacade.getEntityManager().createNamedQuery("Dokfk.findByPodatnikRokUnique2").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).getResultList());
//           if (zwrot1!=null) {
//               zwrot.addAll(zwrot1);
//           }
//           if (zwrot2!=null) {
//               zwrot.addAll(zwrot2);
//           }
//       } catch (Exception e ){
//           
//       }
//        return zwrot;
//    }
    
    
    public List<Dokfk> findDokfkPodatnik(WpisView wpisView) {
        try {
           return Collections.synchronizedList(sessionFacade.findDokfkPodatnik(wpisView));
       } catch (Exception e ){
           return null;
       }
    }
    
    
    
    public List<Dokfk> findByKontr(Klienci klienci) {
        try {
           return sessionFacade.getEntityManager().createNamedQuery("Dokfk.findByKontrahentDofk").setParameter("kontr", klienci.getId()).getResultList();
       } catch (Exception e ){
           return null;
       }
    }
            
            
    public List<Dokfk> findDokfkAllRok(String rok) {
        try {
           return sessionFacade.getEntityManager().createNamedQuery("Dokfk.findByRok").setParameter("rok", rok).getResultList();
       } catch (Exception e ){
           return null;
       }
    }
    
     public List<Dokfk> findDokfkPodatnikRok(Podatnik p, String rok) {
        try {
           return sessionFacade.getEntityManager().createNamedQuery("Dokfk.findByPodatnikRok").setParameter("podatnik", p).setParameter("rok", rok).getResultList();
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnikRokSrodkiTrwale(WpisView wpisView) {
        try {
           return Collections.synchronizedList(sessionFacade.findDokfkPodatnikRokSrodkiTrwale(wpisView));
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnikRokRMK(WpisView wpisView) {
        try {
           return Collections.synchronizedList(sessionFacade.findDokfkPodatnikRokRMK(wpisView));
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnikRokMcKategoria(WpisView wpisView, String kategoria) {
        try {
           return sessionFacade.findDokfkPodatnikRokMcKategoria(wpisView, kategoria);
       } catch (Exception e ){
           return null;
       }
    }
    
     
    public List<Dokfk> findDokfkPodatnikRokKategoria(Podatnik podatnik, String rok, String kategoria) {
        try {
           return Collections.synchronizedList(sessionFacade.findDokfkPodatnikRokKategoria(podatnik, rok, kategoria));
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnikRokKategoriaOrderByNo(WpisView wpisView, String kategoria) {
        try {
           return Collections.synchronizedList(sessionFacade.findDokfkPodatnikRokKategoriaOrderByNo(wpisView, kategoria));
       } catch (Exception e ){
           return null;
       }
    }

    public Dokfk findDokfk(String data, String numer) {
       try {
           return sessionFacade.findDokfk(data, numer);
       } catch (Exception e ){
           return null;
       }
    }
    
  
    public Dokfk findDokfkObj(Dokfk selected) {
       try {
           return sessionFacade.findDokfk(selected);
       } catch (Exception e ){
           return null;
       }
    }
    
       
    public Dokfk findDokfkObjKontrahent(Dokfk selected) {
       try {
           return sessionFacade.findDokfkKontrahent(selected);
       } catch (Exception e ){
           return null;
       }
    }

    public Dokfk findDokfkLastofaType(Podatnik podatnik, String seriadokfk, String rok) {
       try {
           return sessionFacade.findDokfkLastofaType(podatnik,seriadokfk, rok);
       } catch (Exception e ){
           return null;
       }
    }
    
    public Dokfk findDokfkLastofaTypeMc(Podatnik podatnik, String seriadokfk, String rok, String mc) {
       try {
           return sessionFacade.findDokfkLastofaTypeMc(podatnik,seriadokfk, rok, mc);
       } catch (Exception e ){
           return null;
       }
    }
    
    public Dokfk findDokfkLastofaTypeKontrahent(Podatnik podatnik, String seriadokfk, Klienci kontr, String rok, Set<Dokfk> listaostatnich) {
        Dokfk zwrot = null;
        try {
            if (listaostatnich != null) {
                for (Dokfk p : listaostatnich) {
                    if (p.getSeriadokfk().equals(seriadokfk) && p.getKontr().equals(kontr)) {
                        zwrot = p;
                        break;
                    }
                }
            }
            if (zwrot == null) {
                zwrot = sessionFacade.findDokfkLastofaTypeKontrahent(podatnik, seriadokfk, kontr, rok);
            }
        } catch (Exception e) {

        }
        return zwrot;
    }

    public Collection<? extends Dokfk> findDokByTypeYear(String BO, String rok) {
        try {
           return sessionFacade.findDokByTypeYear(BO,rok);
       } catch (Exception e ){
           return null;
       }
    }

    public List<Dokfk> zwrocBiezacegoKlientaRokVAT(Podatnik podatnik, String rok) {
        return sessionFacade.findDokfkBKVAT(podatnik,rok);
    }

    public Dokfk findDokfofaType(Podatnik podatnikWpisu, String vat, String rokWpisuSt, String mc) {
        try {
           return sessionFacade.findDokfofaTypeKontrahent(podatnikWpisu,vat, rokWpisuSt, mc);
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfofaTypeKilkaLista(Podatnik podatnikWpisu, String vat, String rokWpisuSt, String mc) {
        try {
           List<Dokfk> lista = Collections.synchronizedList(sessionFacade.findDokfofaTypeKontrahentKilka(podatnikWpisu,vat, rokWpisuSt, mc));
           return lista;
           } catch (Exception e ){
           return null;
       }
    }
    
    public Dokfk findDokfofaTypeKilka(Podatnik podatnikWpisu, String vat, String rokWpisuSt, String mc) {
        try {
           List<Dokfk> lista = Collections.synchronizedList(sessionFacade.findDokfofaTypeKontrahentKilka(podatnikWpisu,vat, rokWpisuSt, mc));
           Dokfk d = null;
           int max = 0;
           if (lista != null && lista.size() > 0) {
               for (Dokfk l: lista) {
                   int nr = Integer.parseInt(l.getNumerwlasnydokfk().split("/")[0]);
                   if (nr > max) {
                       max = nr;
                       d = l;
                   }
               }
           }
           return d;
       } catch (Exception e ){
           return null;
       }
    }

    public Dokfk findDokfkObjUsun(Dokfk dousuniecia) {
        try {
           return sessionFacade.findDokfkObject(dousuniecia);
       } catch (Exception e ){
           return null;
       }
    }

    public List<String> znajdzDokumentPodatnikWprFK(String wpr) {
        return Collections.synchronizedList(sessionFacade.znajdzDokumentPodatnikWprFK(wpr));
    }

    public List<String> findZnajdzSeriePodatnik(WpisView wpisView) {
        return Collections.synchronizedList(sessionFacade.findZnajdzSeriePodatnik(wpisView));
    }

    public Dokfk findDokfkID(Dokfk wybranyDokfk) {
         try {
           return sessionFacade.findDokfId(wybranyDokfk);
       } catch (Exception e ){
           return null;
       }
    }
    
}
