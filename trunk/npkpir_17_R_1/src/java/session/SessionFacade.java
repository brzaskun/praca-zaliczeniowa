/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import embeddable.Mce;
import entity.Dok;
import entity.Evewidencja;
import entity.Pitpoz;
import entity.Platnosci;
import entity.PlatnosciPK;
import entity.Podatnik;
import entity.Podstawki;
import entity.Sesja;
import entity.StornoDok;
import entity.Uz;
import entity.Zobowiazanie;
import java.util.ArrayList;
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

    public Pitpoz findPitpoz(String rok, String mc, String pod) {
        Pitpoz tmp = (Pitpoz) em.createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).getSingleResult();
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
        StornoDok tmp = (StornoDok) em.createQuery("SELECT p FROM StornoDok p WHERE p.rok = :rok AND p.mc = :mc AND p.podatnik = :podatnik").setParameter("rok", rok).setParameter("mc", mc).setParameter("podatnik",podatnik).getSingleResult();
        return tmp;
    }
}
