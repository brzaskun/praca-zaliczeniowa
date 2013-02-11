/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import embeddable.Mce;
import entity.Amodok;
import entity.Deklaracjevat;
import entity.Dok;
import entity.Evewidencja;
import entity.Evpozycja;
import entity.Ewidencjevat;
import entity.Pitpoz;
import entity.Platnosci;
import entity.PlatnosciPK;
import entity.Podatnik;
import entity.Podstawki;
import entity.Rodzajedok;
import entity.Sesja;
import entity.Srodkikst;
import entity.StornoDok;
import entity.Uz;
import entity.Wpis;
import entity.Zamknietemiesiace;
import entity.Zobowiazanie;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Stateless
public class SessionFacade<T> {

    @PersistenceContext(unitName = "npkpir_PU")
    private EntityManager em;

    public SessionFacade() {
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public List<T> findAll(Class<T> entityClass) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return getEntityManager().createQuery(cq).getResultList();
    }

    public void create(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }

    public Evewidencja findEvewidencjaByName(String nazwa) {
        return (Evewidencja) em.createNamedQuery("Evewidencja.findByNazwa").setParameter("nazwa", nazwa).getSingleResult();
    }

    public Evpozycja findEvpozycjaByName(String nazwapola) {
        return (Evpozycja) em.createNamedQuery("Evpozycja.findByNazwapola").setParameter("nazwapola", nazwapola).getSingleResult();
    }

    public Pitpoz findPitpoz(String rok, String mc, String pod) {
        Pitpoz tmp = (Pitpoz) em.createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).getSingleResult();
        return tmp;
    }

    public Ewidencjevat findEwidencjeVAT(String rok, String mc, String pod) {
        Ewidencjevat tmp = (Ewidencjevat) em.createQuery("SELECT p FROM  Ewidencjevat p WHERE p.rok = :rok AND p.miesiac = :miesiac AND p.podatnik = :podatnik").setParameter("rok", rok).setParameter("miesiac", mc).setParameter("podatnik", pod).getSingleResult();
        return tmp;
    }

    public List<Pitpoz> findPitpodatnik(String rok, String pod) {
        List<Pitpoz> tmp = em.createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("podatnik", pod).getResultList();
        return tmp;
    }

    public Uz findUzNP(String np) {
        Uz tmp = (Uz) em.createNamedQuery("Uz.findByLogin").setParameter("login", np).getSingleResult();
        return tmp;
    }

    public Sesja findSesja(String nrsesji) {
        return (Sesja) em.createNamedQuery("Sesja.findByNrsesji").setParameter("nrsesji", nrsesji).getSingleResult();
    }

    public Podstawki findPodstawkiyear(Integer rok) {
        Podstawki tmp = (Podstawki) em.createNamedQuery("Podstawki.findByRok").setParameter("rok", rok).getSingleResult();
        return tmp;
    }

    public Podatnik findPodatnikNP(String np) {
        Podatnik tmp = (Podatnik) em.createNamedQuery("Podatnik.findByNazwapelna").setParameter("nazwapelna", np).getSingleResult();
        return tmp;
    }

    public Podatnik findPodatnikNPN(String np) {
        Podatnik tmp = (Podatnik) em.createNamedQuery("Podatnik.findByNip").setParameter("nip", np).getSingleResult();
        return tmp;
    }

    public Platnosci findPlatnosciPK(PlatnosciPK key) throws Exception {
        Platnosci tmp = (Platnosci) em.createNamedQuery("Platnosci.findByKey").setParameter("podatnik", key.getPodatnik()).setParameter("rok", key.getRok()).setParameter("miesiac", key.getMiesiac()).getSingleResult();
        return tmp;
    }
    
    public List<Platnosci> findPlatnosciPodRok(String rok, String podatnik) throws Exception {
        List<Platnosci> tmp = em.createNamedQuery("Platnosci.findByPodRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
        return tmp;
    }

    public Dok dokumentDuplicat(Dok selD) throws Exception {
        Dok wynik = null;
        try {
            wynik = (Dok) em.createNamedQuery("Dok.findDuplicate").setParameter("kontr", selD.getKontr()).setParameter("nrWlDk", selD.getNrWlDk()).setParameter("kwota", selD.getKwota()).getSingleResult();
        } catch (Exception e) {
            System.out.println("Nie znaleziono duplikatu - DokFacade");
            return null;
        }
        System.out.println("Znaleziono duplikat - DokFacade");
        return wynik;
    }

    public Zobowiazanie findZobowiazanie(String rok, String mc) throws Exception {
        try {
            Integer przesunmc = Mce.getMapamcyX().get(mc) + 1;
            String pm = Mce.getMapamcy().get(przesunmc);
            Zobowiazanie tmp = (Zobowiazanie) em.createQuery("SELECT p FROM Zobowiazanie p WHERE p.zobowiazaniePK.rok = :rok AND p.zobowiazaniePK.mc = :mc").setParameter("rok", rok).setParameter("mc", pm).getSingleResult();
            return tmp;
        } catch (Exception e) {
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Brak danych", "");
            FacesContext.getCurrentInstance().addMessage(null, msg);
            throw new Exception();
        }
    }

    public StornoDok findStornoDok(Integer rok, String mc, String podatnik) {
        StornoDok tmp = (StornoDok) em.createQuery("SELECT p FROM StornoDok p WHERE p.rok = :rok AND p.mc = :mc AND p.podatnik = :podatnik").setParameter("rok", rok).setParameter("mc", mc).setParameter("podatnik", podatnik).getSingleResult();
        return tmp;
    }

    public List<StornoDok> findStornoDok(Integer rok, String podatnik) {
        List<StornoDok> tmp = em.createQuery("SELECT p FROM StornoDok p WHERE p.rok = :rok AND p.podatnik = :podatnik").setParameter("rok", rok).setParameter("podatnik", podatnik).getResultList();
        return tmp;
    }

    public Dok poprzednik(Integer rok, Integer mc) throws Exception {
        String mcS;
        if (mc < 9) {
            mcS = "0" + mc;
        } else {
            mcS = String.valueOf(mc);
        }
        Dok wynik = null;
        try {
            wynik = (Dok) em.createNamedQuery("Dok.findPoprzednik").setParameter("pkpirR", rok).setParameter("pkpirM", mcS).setParameter("opis", "umorzenie za miesiac").getSingleResult();
        } catch (Exception e) {
            System.out.println("Nie znaleziono duplikatu - DokFacade");
            return null;
        }
        System.out.println("Znaleziono poprzednika - DokFacade");
        return wynik;
    }

    public Rodzajedok findRodzajedok(String skrot) {
        Rodzajedok wynik = null;
        wynik = (Rodzajedok) em.createNamedQuery("Rodzajedok.findBySkrot").setParameter("skrot", skrot).getSingleResult();
        return wynik;
    }

    public List<Amodok> findAmodok(String podatnik) {
        List<Amodok> tmp = em.createNamedQuery("Amodok.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
        return tmp;
    }

    public Dok findStornoDok(String rok, String mc, String podatnik) {
        Dok wynik = null;
        try {
            wynik = (Dok) em.createNamedQuery("Dok.findStornoDok").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", podatnik).setParameter("opis", "storno za miesiac").getSingleResult();
        } catch (Exception e) {
            System.out.println("Nie znaleziono storno dok - DokFacade");
            return null;
        }
        System.out.println("Znaleziono storno dok - DokFacade");
        return wynik;
    }

    public boolean findSTR(String podatnik, Double netto, String numer) {
        try {
            em.createNamedQuery("SrodekTrw.findSTR").setParameter("podatnik", podatnik).setParameter("netto", netto).setParameter("podatnik", podatnik).setParameter("nrwldokzak", numer).getSingleResult();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Zamknietemiesiace findZM(String podatnik) {
        return (Zamknietemiesiace) em.createNamedQuery("Zamknietemiesiace.findByPodatnik").setParameter("podatnik", podatnik).getSingleResult();
    }

    public Wpis findWpis(String login) {
        try {
            return (Wpis) em.createNamedQuery("Wpis.findByWprowadzil").setParameter("wprowadzil", login).getSingleResult();
        } catch (Exception e) {
            Wpis wpis = new Wpis();
            wpis.setWprowadzil(login);
            getEntityManager().persist(wpis);
            return wpis;
        }
    }

    public List<Dok> findDokPod(String pod) {
        return em.createNamedQuery("Dok.findByPodatnik").setParameter("podatnik", pod).getResultList();
    }

    public List<Srodkikst> findSrodekkst(String nazwa) {
        return em.createNamedQuery("Srodkikst.findByNazwa").setParameter("nazwa", nazwa).getResultList();
    }

    public Srodkikst findSrodekkst1(String nazwa) {
        return (Srodkikst) em.createNamedQuery("Srodkikst.findByNazwa").setParameter("nazwa", nazwa).getSingleResult();
    }
    
    public Deklaracjevat findDeklaracjevat(String rok, String mc, String pod) {
        return (Deklaracjevat) em.createNamedQuery("Deklaracjavat.findByRokMcPod").setParameter("rok", rok).setParameter("miesiac", mc).setParameter("podatnik", pod).getSingleResult();
    }

    public List<Deklaracjevat> findDeklaracjewszystkie(String rok, String mc, String pod) {
        return em.createNamedQuery("Deklaracjavat.findByRokMcPod").setParameter("rok", rok).setParameter("miesiac", mc).setParameter("podatnik", pod).getResultList();
    }

    public List<Deklaracjevat> findDeklaracjewysylka(String pod) {
        return em.createNamedQuery("Deklaracjevat.findByPodatnik").setParameter("podatnik", pod).getResultList();
    }

    public Srodkikst findSr(Srodkikst srodek) {
       return em.find(Srodkikst.class, srodek);
    }
}
