/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.Kwartaly;
import entity.Klienci;
import entity.Podatnik;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
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

    public List<Dokfk> findDokfkByDateAndType(Podatnik podatnik, String dataod, String datado, List<Integer> rodzajeDokumentow) {
    return getEntityManager()
            .createQuery("SELECT d FROM Dokfk d WHERE d.podatnikObj = :podatnik AND d.dataoperacji >= :dataod AND d.dataoperacji <= :datado AND d.rodzajedok.kategoriadokumentu IN :rodzaje", Dokfk.class)
            .setParameter("dataod", dataod)
            .setParameter("datado", datado)
            .setParameter("podatnik", podatnik)
            .setParameter("rodzaje", rodzajeDokumentow)
            .getResultList();
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
    
     public List<Dokfk> findDokRokMC(String rok, String mc){
        List<Dokfk> zwrot = new ArrayList<>();
        try {
            zwrot = getEntityManager().createNamedQuery("Dokfk.findByRokMc").setParameter("rok", rok).setParameter("mc", mc).getResultList();
        } catch (Exception e) {
        }
        return zwrot;
    }
     
     public List<Dokfk> findDokRokMCDataKsiegowania(String roks, String mcs){
        List<Dokfk> zwrot = new ArrayList<>();
        try {
            int rok = Integer.parseInt(roks);
            int mc = Integer.parseInt(mcs) - 1; // Miesiące w Calendar są indeksowane od 0

             // Początek miesiąca
            Calendar start = new GregorianCalendar(rok, mc, 1, 0, 0, 0);
            Date startDate = start.getTime();

            // Pierwszy dzień kolejnego miesiąca
            Calendar end = new GregorianCalendar(rok, mc+1, 1, 0, 0, 0);
            Date endDate = end.getTime();

            zwrot = getEntityManager().createNamedQuery("Dokfk.findByRokMcDataKsiegowania", Dokfk.class)
                                .setParameter("startDate", startDate)
                                .setParameter("endDate", endDate)
                                .getResultList();
        } catch (Exception e) {
        }
        return zwrot;
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
       Dokfk zwrot = null;
       try {
            zwrot =  (Dokfk)  getEntityManager().createNamedQuery("Dokfk.findByDokEdycjaFK")
                    .setParameter("seriadokfk", selected.getSeriadokfk())
                    .setParameter("rok", selected.getRok())
                    .setParameter("podatnikObj", selected.getPodatnikObj())
                    .setParameter("numerwlasnydokfk", selected.getNumerwlasnydokfk())
                    .setParameter("kontrahent", selected.getKontr())
                    .getSingleResult();
       } catch (Exception e ){
           return null;
       }
       return zwrot;
    }
    
    public Dokfk findDokfkObjRK(EVatwpisFK ewidencjaVatRK) {
       Dokfk zwrot = null;
       try {
            zwrot =  (Dokfk)  getEntityManager().createNamedQuery("Dokfk.findByDokEdycjaFKRK")
                    .setParameter("rok", ewidencjaVatRK.getDokfk().getRok())
                    .setParameter("podatnikObj", ewidencjaVatRK.getDokfk().getPodatnikObj())
                    .setParameter("numerwlasnydokfk", ewidencjaVatRK.getNumerwlasnydokfk())
                    .setParameter("kontrahent", ewidencjaVatRK.getKontr())
                    .getSingleResult();
       } catch (Exception e ){
           return null;
       }
       return zwrot;
    }
    
     public Dokfk findDokfkSzcz(String seria, String rok, Podatnik podatnik, String nrwlasny, Klienci klient) {
        try {
            return (Dokfk)  getEntityManager().createNamedQuery("Dokfk.findByDokEdycjaFK")
                    .setParameter("seriadokfk", seria)
                    .setParameter("rok", rok)
                    .setParameter("podatnikObj", podatnik)
                    .setParameter("numerwlasnydokfk", nrwlasny)
                    .setParameter("kontrahent", klient)
                    .getSingleResult();
        } catch (Exception e) {
            E.e(e);
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
       Dokfk zwrot = null;
       try {
           zwrot = sessionFacade.findDokfkLastofaTypeMc(podatnik,seriadokfk, rok, mc);
       } catch (Exception e ){
           System.out.println("");
       }
       return zwrot;
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
                zwrot = (Dokfk)  getEntityManager().createNamedQuery("Dokfk.findByLastofaTypeKontrahent").setParameter("podatnik", podatnik).setParameter("seriadokfk", seriadokfk).setParameter("kontr", kontr).setParameter("rok", rok).setMaxResults(1).getSingleResult();
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

    public Dokfk findDokfofaType(Podatnik podatnikWpisu, String symboldokumentu, String rokWpisuSt, String mc) {
        Dokfk zwrot = null;
        try {
           zwrot = sessionFacade.findDokfofaTypeKontrahent(podatnikWpisu,symboldokumentu, rokWpisuSt, mc);
       } catch (Exception e ){
           
       }
        return zwrot;
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
