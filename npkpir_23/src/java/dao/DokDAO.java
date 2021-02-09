/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.Kwartaly;
import embeddable.Mce;
import entity.Dok;
import entity.Klienci;
import entity.Podatnik;
import error.E;
import java.io.Serializable;
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
public class DokDAO extends DAO implements Serializable {
    //private static final Logger LOG =  Logger.getLogger(DokDAO.class.getName());

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

    public DokDAO() {
        super(Dok.class);
        super.em = this.em;
    }
    
    public Dok znajdzDuplikat(Dok selD, String rok) throws Exception {
        Dok wynik = null;
        try {
            wynik = (Dok)  getEntityManager().createNamedQuery("Dok.findDuplicate").setParameter("podatnik", selD.getPodatnik()).setParameter("nip", selD.getKontr().getNip()).setParameter("nrWlDk", selD.getNrWlDk()).setParameter("netto", selD.getNetto()).setParameter("pkpirR", rok).getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return wynik;
    }
    
 
    public Dok znajdzDuplikatAMO(Dok selD, String rok) throws Exception {
        Dok wynik = null;
        try {
            wynik = (Dok)  getEntityManager().createNamedQuery("Dok.findDuplicateAMO").setParameter("podatnik", selD.getPodatnik()).setParameter("nrWlDk", selD.getNrWlDk()).setParameter("pkpirR", rok).getSingleResult();
        } catch (Exception e) {
            E.e(e);
        }
        return wynik;
    }
    
    
    public Dok znajdzDuplikatwtrakcie(Dok selD, Podatnik podatnik, String typdokumentu) {
         List<Dok> wynik = null;
        try {
            wynik =  getEntityManager().createNamedQuery("Dok.findDuplicatewTrakcie").setParameter("nip", selD.getKontr().getNip()).setParameter("nrWlDk", selD.getNrWlDk()).setParameter("podatnik", podatnik).setParameter("typdokumentu", typdokumentu).getResultList();
            if (!wynik.isEmpty()) {
                return wynik.get(wynik.size() - 1);
            } else {
                return null;
            }
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Dok> zwrocBiezacegoKlienta(Podatnik pod) {
        List<Dok> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Dok.findByPodatnik").setParameter("podatnik", pod).getResultList();
        } catch (Exception e) {
        }
        return zwrot;
    }

//    public List<Dok> zwrocBiezacegoKlientaRokVAT(Podatnik pod, String rok) {
//        return dokFacade.findDokBKVAT(pod,rok);
//    }

    public List<Dok> zwrocBiezacegoKlientaRok(Podatnik pod, String rok) {
        List<Dok> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Dok.findByBK").setParameter("podatnik", pod).setParameter("pkpirR", rok).getResultList();
        } catch (Exception e) {
        }
        return zwrot;
    }

    public List<Dok> zwrocBiezacegoKlientaRokOdMc(Podatnik pod, String rok, String mc) {
        List<Dok> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Dok.findByBKodMca").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("mc", mc).getResultList();
        } catch (Exception e) {
        }
        return zwrot;
    }

    public List<Dok> zwrocBiezacegoKlientaRokOdMcaDoMca(Podatnik pod, String rok, String mcdo, String mcod) {
       List<Dok> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Dok.findByBKodMcadoMca").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("mcod", mcod).setParameter("mcdo", mcdo).getResultList();
        } catch (Exception e) {
        }
        return zwrot;
    }

    public List<Dok> zwrocBiezacegoKlientaRokPrzychody(Podatnik pod, String rok) {
        List<Dok> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Dok.findByBKPrzychody").setParameter("podatnik", pod).setParameter("pkpirR", rok).getResultList();
        } catch (Exception e) {
        }
        return zwrot;
    }
    
    
    public List<Dok> zwrocBiezacegoKlientaRokMcPrzychody(Podatnik pod, String rok, String mc) {
        List<Dok> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Dok.findByBKMCPrzychody").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("pkpirM", mc).getResultList();
        } catch (Exception e) {
        }
        return zwrot;
    }
    
      
    public List<Dok> zwrocRok(String rok) {
         List<Dok> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Dok.findByPkpirR").setParameter("pkpirR", rok).getResultList();
        } catch (Exception e) {
        }
        return zwrot;
    }
    
    public List<Dok> zwrocBiezacegoKlientaRokMC(Podatnik pod, String rok, String mc) {
         List<Dok> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Dok.findByBKM").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("pkpirM", mc).getResultList();
        } catch (Exception e) {
        }
        return zwrot;
    }
    
    public List zwrocBiezacegoKlientaRokMC(WpisView wpisView) {
        return zwrocBiezacegoKlientaRokMC(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
    }
    public List<Dok> findDokBKVAT(Podatnik pod, String rok, String mc) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dok.findByBKMVAT").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("vatM", mc).getResultList());
    }
    public List zwrocBiezacegoKlientaRokMCVAT(WpisView wpisView) {
         List<Dok> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Dok.findByBKMVAT").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("pkpirR", wpisView.getRokWpisuSt()).setParameter("vatM", wpisView.getMiesiacWpisu()).getResultList();
        } catch (Exception e) {
        }
        return zwrot;
    }

    public List<Dok> zwrocBiezacegoKlientaRokMCWaluta(Podatnik pod, String rok, String mc) {
        List<Dok> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Dok.findByBKMWaluta").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("pkpirM", mc).getResultList();
        } catch (Exception e) {
        }
        return zwrot;
    }

    public List<Dok> zwrocBiezacegoKlientaRokKW(Podatnik pod, String rok, String mc) {
        List<String> mce = Kwartaly.mctoMcewKw(mc);
        List<Dok> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Dok.findByRokKW").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("mc1", mce.get(0)).setParameter("mc2", mce.get(1)).setParameter("mc3", mce.get(2)).getResultList();
        } catch (Exception e) {
        }
        return zwrot;
    }
    
    public List zwrocBiezacegoKlientaRokKW(WpisView wpisView) {
        return zwrocBiezacegoKlientaRokKW(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt(), wpisView.getMiesiacWpisu());
    }
 
    public Object iledokumentowklienta(Podatnik pod, String rok, String mc) {
        Object zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Dok.findByPkpirRMCount").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("pkpirM", mc).getSingleResult();
        } catch (Exception e) {
        }
        return zwrot;
    }

    public List<Dok> zwrocBiezacegoKlientaDuplikat(Podatnik pod, String rok) {
         List<Dok> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Dok.findByDuplikat").setParameter("podatnik", pod).setParameter("pkpirR", rok).getResultList();
        } catch (Exception e) {
        }
        return zwrot;
    }
  
    
    public Dok find(String typdokumentu, Podatnik podatnik, Integer rok){
         Dok zwrot = null;
        try {
            zwrot = (Dok) getEntityManager().createNamedQuery("Dok.findByTPR").setParameter("podatnik", podatnik).setParameter("pkpirR", rok).setParameter("typdokumentu", typdokumentu).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
        }
        return zwrot;
    }

    public Dok findDokMC(String typdokumentu, Podatnik podatnik, String rok, String mc){
        Dok zwrot = null;
        try {
            zwrot = (Dok) getEntityManager().createNamedQuery("Dok.findByRMPT").setParameter("typdokumentu", typdokumentu).setParameter("podatnik", podatnik).setParameter("pkpirR", rok).setParameter("pkpirM", mc).getSingleResult();
        } catch (Exception e) {
        }
        return zwrot;
    }

    public void destroyStornoDok(String rok, String mc, Podatnik podatnik) {
        Dok wynik = null;
        try {
            wynik = (Dok)  getEntityManager().createNamedQuery("Dok.findStornoDok").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", podatnik).setParameter("opis", "storno za miesiac").getSingleResult();
        } catch (Exception e) {
            E.e(e);
        }
        remove(wynik);
    }

    public Dok findFaktWystawione(Podatnik podatnik, Klienci kontrahent, String numerkolejny, double brutto) {
       Dok zwrot = null;
        try {
            zwrot = (Dok) getEntityManager().createNamedQuery("Dok.findByFakturaWystawiona").setParameter("podatnik", podatnik).setParameter("kontr", kontrahent).setParameter("nrWlDk", numerkolejny).setParameter("brutto", brutto).getSingleResult();
        } catch (Exception e) {
        }
        return zwrot;
    }

    public int liczdokumenty(String rok, String mc, Podatnik podatnik) {
        List<String> poprzedniemce = Mce.poprzedniemce(mc);
        int iloscdok = 0;
        for (String p : poprzedniemce) {
            iloscdok += Integer.parseInt(iledokumentowklienta(podatnik, rok, p).toString());
        }
        return iloscdok;
    }
   
     public Dok findDokLastofaKontrahent(Podatnik podatnik, Klienci kontr, String rok) {
       Dok zwrot = null;
       try {
           zwrot = (Dok)  getEntityManager().createNamedQuery("Dok.findByfindByLastofaTypeKontrahent").setParameter("podatnik", podatnik).setParameter("kontr", kontr).setParameter("pkpirR", rok).setMaxResults(1).getSingleResult();
       } catch (Exception e ){
           
       }
       return zwrot;
    }

    public Dok znajdzDokumentInwestycja(WpisView wpisView, Dok r) {
        Dok zwrot = null;
       try {
           zwrot = (Dok)   getEntityManager().createNamedQuery("Dok.znajdzInwestycja").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("data", r.getDataWyst()).setParameter("netto", r.getNetto()).setParameter("numer", r.getNrWlDk()).getSingleResult();
       } catch (Exception e ){
           
       }
       return zwrot;
    }

    public List<String> znajdzDokumentPodatnikWpr(String wpr) {
        List<String> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Dok.znajdzDokumentPodatnikWpr").setParameter("wprowadzil", wpr).getResultList();
        } catch (Exception e) {
        }
        return zwrot;
    }

    public List<Dok> findDokByInwest() {
        List<Dok> zwrot = null;
        try {
            zwrot = getEntityManager().createNamedQuery("Dok.findByInwestycje").getResultList();
        } catch (Exception e) {
        }
        return zwrot;
    }

  
}
