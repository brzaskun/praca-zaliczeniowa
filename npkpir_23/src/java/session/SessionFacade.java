/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import dao.VATZDDAO;
import embeddable.Mce;
import entity.Amodok;
import entity.DeklaracjaVatSchema;
import entity.DeklaracjaVatSchemaWierszSum;
import entity.DeklaracjaVatWierszSumaryczny;
import entity.Deklaracjavat27;
import entity.DeklaracjavatUE;
import entity.Deklaracjevat;
import entity.Dok;
import entity.EVatOpis;
import entity.EVatwpis1;
import entity.Evewidencja;
import entity.Evpozycja;
import entity.Faktura;
import entity.FakturaRozrachunki;
import entity.FakturaStopkaNiemiecka;
import entity.FakturaXXLKolumna;
import entity.Fakturadodelementy;
import entity.Fakturaelementygraficzne;
import entity.Fakturyokresowe;
import entity.Fakturywystokresowe;
import entity.Inwestycje;
import entity.Klienci;
import entity.Logofaktura;
import entity.MultiuserSettings;
import entity.Pismoadmin;
import entity.Pitpoz;
import entity.PlatnoscWaluta;
import entity.Platnosci;
import entity.PlatnosciPK;
import entity.Podatnik;
import entity.PodatnikOpodatkowanieD;
import entity.PodatnikUdzialy;
import entity.Podstawki;
import entity.Pozycjenafakturze;
import entity.Rejestrlogowan;
import entity.Rodzajedok;
import entity.Ryczpoz;
import entity.SMTPSettings;
import entity.SchemaEwidencja;
import entity.Sesja;
import entity.Sprawa;
import entity.SrodekTrw;
import entity.Srodkikst;
import entity.StornoDok;
import entity.Sumypkpir;
import entity.UPO;
import entity.UmorzenieN;
import entity.Uz;
import entity.WniosekVATZDEntity;
import entity.Wpis;
import entity.ZamkniecieRokuEtap;
import entity.ZamkniecieRokuRozliczenie;
import entity.Zamknietemiesiace;
import entity.Zobowiazanie;
import entity.Zusmail;
import entity.Zusstawki;
import entityfk.Cechazapisu;
import entityfk.Delegacja;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
import entityfk.Kliencifk;
import entityfk.Konto;
import entityfk.KontopozycjaBiezaca;
import entityfk.KontopozycjaZapis;
import entityfk.MiejsceKosztow;
import entityfk.Pojazdy;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.RMK;
import entityfk.SprFinKwotyInfDod;
import entityfk.SprawozdanieFinansowe;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Transakcja;
import entityfk.UkladBR;
import entityfk.Waluty;
import entityfk.Wiersz;
import entityfk.WierszBO;
import entityfk.WynikFKRokMc;
import error.E;
import java.io.Serializable;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.eclipse.persistence.config.CascadePolicy;
import org.eclipse.persistence.config.HintValues;
import org.eclipse.persistence.config.QueryHints;
import org.eclipse.persistence.queries.LoadGroup;
import view.WpisView;


/**
 *
 * @author Osito
 * @param <T>
 */
@Stateless
public class SessionFacade<T> implements Serializable {

    private static final long serialVersionUID = 1L;


    @PersistenceContext(unitName = "npkpir_22PU")
    private EntityManager em;

    public SessionFacade() {
       // System.out.println("SessionFacade init");
    }

    public EntityManager getEntityManager() {
        return em;
    }

    public List<T> findAll(Class<T> entityClass) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return Collections.synchronizedList(getEntityManager().createQuery(cq).getResultList());
    }
    
    public List<T> findAllReadOnly(Class<T> entityClass) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        return Collections.synchronizedList(getEntityManager().createQuery(cq).setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE).setHint(QueryHints.READ_ONLY, HintValues.TRUE).getResultList());
    }

    public void create(T entity) {
        getEntityManager().persist(entity);
        getEntityManager().flush();
    }
    
    public void create(List<T> entityList) {
        for (T p : entityList) {
            getEntityManager().persist(p);
        }
        getEntityManager().flush();
    }
    
//    public void refresh(List<T> entityList) {
//        for (T p : entityList) {
//            try {
//                getEntityManager().refresh(p);
//            } catch(Exception e){}
//        }
//        
//    }

//    public void refresh(T entity) {
//        getEntityManager().refresh(getEntityManager().merge(entity));
//    }

    public T findEntity(Class<T> entityClass, T entityPK) {
        T find = getEntityManager().find(entityClass, entityPK);
        return find;
    }

    public void remove(T entity) {
        
        em.remove(em.merge(entity));
        
    }
    
    public void remove(List<T> entityList) {
        
        for (T p : entityList) {
            em.remove(em.merge(p));
        }
        
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
        getEntityManager().flush();
        
    }

    public void edit(List<T> entityList) {
        for (T p : entityList) {
            try {
                getEntityManager().merge(p);
            } catch (Exception e) {
                E.e(e);
            }
        }
        getEntityManager().flush();
    }

    //to jest po to, ze jk juz jest cos w np. planie kont to 
    //wywali blad w jednym, ele reszte zasejwuje w bazie :)
    public void createRefresh(List<T> entityList) {
        for (T p : entityList) {
            try {
                getEntityManager().persist(p);
            } catch (Exception e) {
                E.e(e);
            }
        }
        getEntityManager().flush();
    }

    public List<T> findXLast(Class<T> entityClass, int ile) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        int ilosc = ((Number) q.getSingleResult()).intValue();
        int kontrolailosci = ilosc - ile;
        if (kontrolailosci < 0) {
            kontrolailosci = 0;
        }
        int[] range = {ilosc, kontrolailosci};
        cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(entityClass));
        q = getEntityManager().createQuery(cq);
        q.setMaxResults(ile);
        q.setFirstResult(range[1]);
        return Collections.synchronizedList(q.getResultList());
    }

    public Evewidencja findEvewidencjaByName(String nazwa) {
        return (Evewidencja) em.createNamedQuery("Evewidencja.findByNazwa").setParameter("nazwa", nazwa).getSingleResult();
    }

    public Evpozycja findEvpozycjaByName(String nazwapola) {
        return (Evpozycja) em.createNamedQuery("Evpozycja.findByNazwapola").setParameter("nazwapola", nazwapola).getSingleResult();
    }

    public List<Pitpoz> findPitpozAll() {
        List<Pitpoz> lista = em.createNamedQuery("Pitpoz.findAll").getResultList();
        return Collections.synchronizedList(lista);
    }
    

    public Pitpoz findPitpoz(String rok, String mc, String pod) {
        Pitpoz tmp = (Pitpoz) em.createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).getSingleResult();
        return tmp;
    }

    public List<Pitpoz> findPitpozLista(String rok, String mc, String pod) {
        List<Pitpoz> lista = em.createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).getResultList();
        return Collections.synchronizedList(lista);
    }

    public Pitpoz findPitpoz(String rok, String mc, String pod, String udzialowiec) {
        Pitpoz tmp = (Pitpoz) em.createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik AND p.udzialowiec = :udzialowiec").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).setParameter("udzialowiec", udzialowiec).getSingleResult();
        return tmp;
    }

    public List<Pitpoz> findPitpodatnik(String rok, String pod) {
        List<Pitpoz> tmp = em.createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("podatnik", pod).getResultList();
        return Collections.synchronizedList(tmp);
    }

    public List<Ryczpoz> findRyczAll() {
        List<Ryczpoz> lista = em.createNamedQuery("Ryczpoz.findAll").getResultList();
        return Collections.synchronizedList(lista);
    }

    public Ryczpoz findRycz(String rok, String mc, String pod) {
        Ryczpoz tmp = (Ryczpoz) em.createQuery("SELECT p FROM Ryczpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).getSingleResult();
        return tmp;
    }

    public Ryczpoz findRycz(String rok, String mc, String pod, String udzialowiec) {
        Ryczpoz tmp = (Ryczpoz) em.createQuery("SELECT p FROM Ryczpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik AND p.udzialowiec = :udzialowiec").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).setParameter("udzialowiec", udzialowiec).getSingleResult();
        return tmp;
    }

    public List<Ryczpoz> findRyczpodatnik(String rok, String pod) {
        List<Ryczpoz> tmp = em.createQuery("SELECT p FROM Ryczpoz p WHERE p.pkpirR = :pkpirR AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("podatnik", pod).getResultList();
        return Collections.synchronizedList(tmp);
    }


    public Uz findUzNP(String login) {
        Uz zwrot = null;
        try {
            zwrot = (Uz) em.createNamedQuery("Uz.findByLogin").setParameter("login", login).getSingleResult();
        } catch (Exception e) {
            
        }
        return zwrot;
    }

    public Sesja findSesja(String nrsesji) {
        try {
            return (Sesja) em.createNamedQuery("Sesja.findByNrsesji").setParameter("nrsesji", nrsesji).getSingleResult();
        } catch (Exception e) {
            return null;
        }
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
        return Collections.synchronizedList(tmp);
    }

    public Dok dokumentDuplicat(Dok selD, String pkpirR) throws Exception {
        Dok wynik = null;
        try {
            wynik = (Dok) em.createNamedQuery("Dok.findDuplicate").setParameter("podatnik", selD.getPodatnik()).setParameter("kontr", selD.getKontr()).setParameter("nrWlDk", selD.getNrWlDk()).setParameter("netto", selD.getNetto()).setParameter("pkpirR", pkpirR).getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return wynik;
    }

    public Dok dokumentDuplicatAMO(Dok selD, String pkpirR) throws Exception {
        Dok wynik = null;
        try {
            wynik = (Dok) em.createNamedQuery("Dok.findDuplicateAMO").setParameter("podatnik", selD.getPodatnik()).setParameter("nrWlDk", selD.getNrWlDk()).setParameter("pkpirR", pkpirR).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
        return wynik;
    }

    public Dok dokumentDuplicatwtrakcie(Dok selD, Podatnik podatnik, String typdokumentu) {
        List<Dok> wynik = null;
        try {
            wynik = em.createNamedQuery("Dok.findDuplicatewTrakcie").setParameter("kontr", selD.getKontr()).setParameter("nrWlDk", selD.getNrWlDk()).setParameter("podatnik", podatnik).setParameter("typdokumentu", typdokumentu).getResultList();
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

    public Zobowiazanie findZobowiazanie(String rok, String mc) throws Exception {
        String[] nowedane = Mce.zwiekszmiesiac(rok, mc);
        try {
            Zobowiazanie tmp = (Zobowiazanie) em.createQuery("SELECT p FROM Zobowiazanie p WHERE p.zobowiazaniePK.rok = :rok AND p.zobowiazaniePK.mc = :mc").setParameter("rok", nowedane[0]).setParameter("mc", nowedane[1]).getSingleResult();
            return tmp;
        } catch (Exception e) {
            E.e(e);
            throw new Exception();
        }
    }

    public StornoDok findStornoDok(Integer rok, String mc, String podatnik) {
        try {
            StornoDok tmp = (StornoDok) em.createQuery("SELECT p FROM StornoDok p WHERE p.rok = :rok AND p.mc = :mc AND p.podatnik = :podatnik").setParameter("rok", rok).setParameter("mc", mc).setParameter("podatnik", podatnik).getSingleResult();
            return tmp;
        } catch (Exception e) {
            return null;
        }
    }

    public List<StornoDok> findStornoDok(Integer rok, String podatnik) {
        List<StornoDok> tmp = em.createQuery("SELECT p FROM StornoDok p WHERE p.rok = :rok AND p.podatnik = :podatnik").setParameter("rok", rok).setParameter("podatnik", podatnik).getResultList();
        return Collections.synchronizedList(tmp);
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
            E.e(e);
            return null;
        }
        return wynik;
    }

    public Rodzajedok findRodzajedok(String skrot) {
        Rodzajedok wynik = null;
        wynik = (Rodzajedok) em.createNamedQuery("Rodzajedok.findBySkrot").setParameter("skrot", skrot).getSingleResult();
        return wynik;
    }

    public Rodzajedok findRodzajedokPodatnikRok(String skrot, Podatnik podatnik, String rok) {
        Rodzajedok wynik = null;
        try {
            wynik = (Rodzajedok) em.createNamedQuery("Rodzajedok.findBySkrotPodatnikRok").setParameter("skrot", skrot).setParameter("podatnik", podatnik).setParameter("rok", rok).getSingleResult();
        } catch (Exception e) {
            E.e(e);

        }
        return wynik;
    }

    public List<Amodok> findAmodok(String podatnik) {
        List<Amodok> tmp = em.createNamedQuery("Amodok.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
        return Collections.synchronizedList(tmp);
    }

    public Dok findStornoDok(String rok, String mc, Podatnik podatnik) {
        Dok wynik = null;
        try {
            wynik = (Dok) em.createNamedQuery("Dok.findStornoDok").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", podatnik).setParameter("opis", "storno za miesiac").getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
        return wynik;
    }

    public boolean findSTR(String podatnik, Double netto, String numer) {
        try {
            em.createNamedQuery("SrodekTrw.findSTR").setParameter("podatnik", podatnik).setParameter("netto", netto).setParameter("podatnik", podatnik).setParameter("nrwldokzak", numer).getSingleResult();
            return true;
        } catch (Exception e) {
            E.e(e);
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
            E.e(e);
            Wpis wpis = new Wpis();
            wpis.setWprowadzil(login);
            getEntityManager().persist(wpis);
            return wpis;
        }
    }

    public List<Dok> findDokPod(Podatnik pod) {
        return Collections.synchronizedList(em.createNamedQuery("Dok.findByPodatnik").setParameter("podatnik", pod).getResultList());
    }

    public List<Srodkikst> findSrodekkst(String nazwa) {
        return Collections.synchronizedList(em.createNamedQuery("Srodkikst.findByNazwa").setParameter("nazwa", nazwa).getResultList());
    }

    public Srodkikst findSrodekkst1(String nazwa) {
        return (Srodkikst) em.createNamedQuery("Srodkikst.findByNazwa").setParameter("nazwa", nazwa).getSingleResult();
    }

    public Deklaracjevat findDeklaracjevat(String rok, String mc, String pod) {
        return (Deklaracjevat) em.createNamedQuery("Deklaracjevat.findByRokMcPod").setParameter("rok", rok).setParameter("miesiac", mc).setParameter("podatnik", pod).getSingleResult();
    }

    public List<Deklaracjevat> findDeklaracjewszystkie(String rok, String mc, String pod) {
        return Collections.synchronizedList(em.createNamedQuery("Deklaracjevat.findByRokMcPod").setParameter("rok", rok).setParameter("miesiac", mc).setParameter("podatnik", pod).getResultList());
    }

    public Deklaracjevat findDeklaracjewysylka(String pod) {
        try {
            return (Deklaracjevat) em.createNamedQuery("Deklaracjevat.findByPodatnikWysylka").setParameter("podatnik", pod).setParameter("identyfikator", "").getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Deklaracjevat> findDeklaracjewysylkaLista(String pod) {
        try {
            return Collections.synchronizedList(em.createNamedQuery("Deklaracjevat.findByPodatnikWysylka").setParameter("podatnik", pod).setParameter("identyfikator", "").getResultList());
        } catch (Exception e) {
            return null;
        }
    }

    public List<Deklaracjevat> findDeklaracjewyslane(String pod) {
        return Collections.synchronizedList(em.createNamedQuery("Deklaracjevat.findByPodatnikWyslane").setParameter("podatnik", pod).setParameter("identyfikator", "").getResultList());
    }

    public Deklaracjevat findDeklaracjaPodatnik(String identyfikator, String podatnik) {
        return (Deklaracjevat) em.createNamedQuery("Deklaracjevat.findByIdentyfikatorPodatnik").setParameter("identyfikator", identyfikator).setParameter("podatnik", podatnik).getSingleResult();
    }

    public List<Deklaracjevat> findDeklaracjewyslane(String pod, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Deklaracjevat.findByPodatnikWyslaneRok").setParameter("podatnik", pod).setParameter("identyfikator", "").setParameter("rok", rok).getResultList());
    }
    
    public List<Deklaracjevat> findDeklaracjewyslaneMc(String pod, String rok, String mc) {
        return Collections.synchronizedList(em.createNamedQuery("Deklaracjevat.findByPodatnikWyslaneRokMc").setParameter("podatnik", pod).setParameter("identyfikator", "").setParameter("rok", rok).setParameter("mc", mc).getResultList());
    }

    public List<Deklaracjevat> findDeklaracjewyslane200(String pod, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Deklaracjevat.findByPodatnikWyslaneRok200").setParameter("podatnik", pod).setParameter("identyfikator", "").setParameter("rok", rok).getResultList());
    }
    public List<Deklaracjevat> findDeklaracjewyslane200RokMc(String rok, String mc) {
        return Collections.synchronizedList(em.createNamedQuery("Deklaracjevat.findByPodatnikWyslaneRokMc200").setParameter("status", "200").setParameter("rok", rok).setParameter("mc", mc).getResultList());
    }

    public Srodkikst findSr(Srodkikst srodek) {
        return em.find(Srodkikst.class, srodek);
    }

    public EVatOpis findEVatOpis(String name) {
        return (EVatOpis) em.createNamedQuery("EVatOpis.findByLogin").setParameter("login", name).getSingleResult();
    }

    public List<Dok> findDokBK(Podatnik pod, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Dok.findByBK").setParameter("podatnik", pod).setParameter("pkpirR", rok).getResultList());
    }

    public List<Dok> findDokBKPrzychody(Podatnik pod, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Dok.findByBKPrzychody").setParameter("podatnik", pod).setParameter("pkpirR", rok).getResultList());
    }

    public List<Dok> findDokBKMCPrzychody(Podatnik pod, String rok, String mc) {
        return Collections.synchronizedList(em.createNamedQuery("Dok.findByBKMCPrzychody").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("pkpirM", mc).getResultList());
    }

    public List<Dok> findDokRok(String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Dok.findByPkpirR").setParameter("pkpirR", rok).getResultList());
    }

    public List<Dok> findDokBKVAT(Podatnik pod, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Dok.findByBKVAT").setParameter("podatnik", pod).setParameter("vatR", rok).getResultList());
    }

    public List<Dok> findDokfkBKVAT(Podatnik pod, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Dokfk.findByBKVAT").setParameter("podatnik", pod).setParameter("vatR", rok).getResultList());
    }

    public Dok findDokTPR(String typdokumentu, Podatnik pod, String rok) {
        Dok d = null;
        List<Dok> lista = em.createNamedQuery("Dok.findByTPR").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("typdokumentu", typdokumentu).getResultList();
        if (lista != null && lista.size() > 0) {
            d = lista.get(lista.size() - 1);
        }
        return d;
    }

    public List<Dok> findDokBK(Podatnik pod, String rok, String mc) {
        return Collections.synchronizedList(em.createNamedQuery("Dok.findByBKM").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("pkpirM", mc).getResultList());
    }
    
    public List<Dok> findDokBKWaluta(Podatnik pod, String rok, String mc) {
        return Collections.synchronizedList(em.createNamedQuery("Dok.findByBKMWaluta").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("pkpirM", mc).getResultList());
    }

    public List<Dok> findDokRokKW(Podatnik pod, String rok, List<String> mce) {
        return Collections.synchronizedList(em.createNamedQuery("Dok.findByRokKW").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("mc1", mce.get(0)).setParameter("mc2", mce.get(1)).setParameter("mc3", mce.get(2)).getResultList());
    }

    public Object findDokBKCount(Podatnik pod, String rok, String mc) {
        return em.createNamedQuery("Dok.findByPkpirRMCount").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("pkpirM", mc).getSingleResult();
    }

    public List<Dok> findDokDuplikat(Podatnik pod, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Dok.findByDuplikat").setParameter("podatnik", pod).setParameter("pkpirR", rok).getResultList());
    }

    public List<Sumypkpir> findSumy(String podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Sumypkpir.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public Amodok findMR(String pod, Integer rok, String mc) {
        Integer miesiac = Integer.parseInt(mc);
        return (Amodok) em.createNamedQuery("Amodok.findByPMR").setParameter("podatnik", pod).setParameter("rok", rok).setParameter("mc", miesiac).getSingleResult();
    }

    public List<Deklaracjevat> findDeklaracjewysylka(String rok, String mc) {
        return Collections.synchronizedList(em.createNamedQuery("Deklaracjevat.findByRokMc").setParameter("rok", rok).setParameter("miesiac", mc).getResultList());
    }

    public List<SrodekTrw> findStrPod(String podatnik) {
        try {
            return Collections.synchronizedList(em.createNamedQuery("SrodekTrw.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<SrodekTrw> findStrPodDokfk(String podatnik, Dokfk dokfk) {
        try {
            return Collections.synchronizedList(em.createNamedQuery("SrodekTrw.findByPodatnikDokfk").setParameter("podatnik", podatnik).setParameter("dokfk", dokfk).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Dok findDokMC(String typdokumentu, Podatnik podatnik, String rok, String mc) {
        try {
            return (Dok) em.createNamedQuery("Dok.findByRMPT").setParameter("typdokumentu", typdokumentu).setParameter("podatnik", podatnik).setParameter("pkpirR", rok).setParameter("pkpirM", mc).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Inwestycje> findInwestycje(String podatnik, boolean zakonczona) {
        return Collections.synchronizedList(em.createNamedQuery("Inwestycje.findByPodatnikZakonczona").setParameter("podatnik", podatnik).setParameter("zakonczona", zakonczona).getResultList());
    }

    public List<Amodok> findPod(String podatnik) {
        try {
            return Collections.synchronizedList(em.createNamedQuery("Amodok.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
        } catch (Exception e) {
            return null;
        }
    }

    public List<Amodok> AmoDokPodRok(String podatnik, String rok) {
        try {
            return Collections.synchronizedList(em.createNamedQuery("Amodok.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList());
        } catch (Exception e) {
            return null;
        }
    }

    public Amodok AmoDokPodMcRok(String podatnik, String mc, Integer rok) {
        try {
            return (Amodok) em.createNamedQuery("Amodok.findByPodatnikMcRok").setParameter("podatnik", podatnik).setParameter("mc", Mce.getMiesiacToNumber().get(mc)).setParameter("rok", rok).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Amodok> findAmoDokBiezacy(String podatnik, String mc, String rok) {
        try {
            return Collections.synchronizedList(em.createNamedQuery("Amodok.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
        } catch (Exception e) {
            return null;
        }
    }

    public Konto findKonto(String numer, Podatnik podatnik, Integer rok) {
        return (Konto) em.createNamedQuery("Konto.findByPelnynumerPodatnik").setParameter("pelnynumer", numer).setParameter("podatnik", podatnik).setParameter("rok", rok).getSingleResult();
    }

    public Konto findKontoNazwaPelnaPodatnik(String nazwapelna, WpisView wpisView) {
        return (Konto) em.createNamedQuery("Konto.findByNazwaPelnaPodatnik").setParameter("nazwapelna", nazwapelna).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getSingleResult();
    }

    public Konto findKontoNazwaPodatnik(String nazwaskrocona, WpisView wpisView) {
        return (Konto) em.createNamedQuery("Konto.findByNazwaPodatnik").setParameter("nazwaskrocona", nazwaskrocona).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getSingleResult();
    }

    public List<Konto> findKontaNazwaPodatnik(String nazwaskrocona, WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByNazwaPodatnik").setParameter("nazwaskrocona", nazwaskrocona).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }

    public List<Konto> findKontaRozrachunkowe(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByRozrachunkowePodatnik").setParameter("zwyklerozrachszczegolne", "rozrachunkowe").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }

    public List<Konto> findKontaRozrachunkoweZpotomkami(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByRozrachunkowePodatnikZpotomkami").setParameter("zwyklerozrachszczegolne", "rozrachunkowe").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }

    public List<Konto> findKontaRozrachunkoweWszystkie(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByRozrachunkiPodatnikWszystkie").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }

    public List<Konto> findKontaVAT(Podatnik podatnik, int rok) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByVATPodatnik").setParameter("zwyklerozrachszczegolne", "vat").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<Konto> findKontaSrodkiTrw(Podatnik podatnik, int rok) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findBySrodkiTrwPodatnik").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<Konto> findKontaRMK(Podatnik podatnik, int rok) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByRMKPodatnik").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<Konto> findKontaRZiS(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByBilansowewynikowePodatnik").setParameter("bilansowewynikowe", "wynikowe").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }

    public Konto findKonto(int id) {
        return (Konto) em.createNamedQuery("Konto.findById").setParameter("id", id).getSingleResult();
    }

    public Konto findKonto2(int id) {
        Konto k = null;
        try {
            k = (Konto) em.createNamedQuery("Konto.findById").setParameter("id", id).getSingleResult();
        } catch (Exception e) {

        }
        return k;
    }

    public Dokfk findZZapisu(String numer) {
        return (Dokfk) em.createNamedQuery("Dokfk.findByNumer").setParameter("numer", numer).getSingleResult();
    }

    public Dokfk findDokfk(String data, String numer) {
        return (Dokfk) em.createNamedQuery("Dokfk.findByDatawystawieniaNumer").setParameter("datawystawienia", data).setParameter("numer", numer).getSingleResult();
    }

    public List<Fakturyokresowe> findPodatnik(String podatnik) {
        return Collections.synchronizedList(em.createNamedQuery("Fakturyokresowe.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
    }

    public List<Fakturywystokresowe> findPodatnikFaktury(String podatnik) {
        return Collections.synchronizedList(em.createNamedQuery("Fakturywystokresowe.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
    }

    public Fakturywystokresowe findFakturaOkresowaById(Integer id) {
        return (Fakturywystokresowe) em.createNamedQuery("Fakturywystokresowe.findById").setParameter("id", id).getSingleResult();
    }

    public List<Fakturywystokresowe> findPodatnikRokFaktury(String podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Fakturywystokresowe.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }
public List<Fakturywystokresowe> findPodatnikRokFakturyBiezace(String podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Fakturywystokresowe.findByPodatnikRokBiezace").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }
    public List<Pozycjenafakturze> findFakturyPodatnik(String podatnik) {
        return Collections.synchronizedList(em.createNamedQuery("Pozycjenafakturze.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
    }

    public List<Evewidencja> findEvewidencjaByTransakcja(String transakcja) {
        return Collections.synchronizedList(em.createNamedQuery("Evewidencja.findByTransakcja").setParameter("transakcja", transakcja).getResultList());
    }
    
    public Evewidencja findEvewidencjaByPole(Evpozycja macierzysty) {
        return (Evewidencja) em.createNamedQuery("Evewidencja.findByPole").setParameter("pole", macierzysty).getSingleResult();
    }

    public List<Faktura> findByKontrahent_nip(String kontrahent_nip, String wystawca) {
        return Collections.synchronizedList(em.createNamedQuery("Faktura.findByKontrahent").setParameter("kontrahent_nip", kontrahent_nip).setParameter("wystawcanazwa", wystawca).getResultList());
    }

    public List<Faktura> findByKontrahentNipRok(String kontrahent_nip, String wystawca, String rok) {
        if (kontrahent_nip.equals("9552379284")) {
        }
        return Collections.synchronizedList(em.createNamedQuery("Faktura.findByKontrahentRok").setParameter("kontrahent_nip", kontrahent_nip).setParameter("wystawcanazwa", wystawca).setParameter("rok", rok).getResultList());
    }
    
    public List<Faktura> findByKontrahentNipPo2015(String kontrahent_nip, String wystawca) {
        return Collections.synchronizedList(em.createNamedQuery("Faktura.findByKontrahentRokPo2015").setParameter("kontrahent_nip", kontrahent_nip).setParameter("wystawcanazwa", wystawca).getResultList());
    }

    public List<Faktura> findFakturyByRok(String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Faktura.findByRok").setParameter("rok", rok).getResultList());
    }

    public List<Faktura> findFakturyByRokPodatnik(String rok, String wystawcanazwa) {
        return Collections.synchronizedList(em.createNamedQuery("Faktura.findByRokPodatnik").setParameter("rok", rok).setParameter("wystawcanazwa", wystawcanazwa).getResultList());
    }

    public Long liczFakturyByRokPodatnik(String rok, String wystawcanazwa) {
        return (Long) em.createNamedQuery("Faktura.liczByRokPodatnik").setParameter("rok", rok).setParameter("wystawcanazwa", wystawcanazwa).getSingleResult();
    }

    public Faktura findOstatniaFakturaByRokPodatnik(String rok, String wystawcanazwa) {
        return (Faktura) em.createNamedQuery("Faktura.findOstatniaFakturaByRokPodatnik").setParameter("rok", rok).setParameter("wystawcanazwa", wystawcanazwa).setMaxResults(1).getSingleResult();
    }

    public List<Fakturadodelementy> findFaktElementyPodatnik(String podatnik) {
        return Collections.synchronizedList(em.createNamedQuery("Fakturadodelementy.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
    }

    public Fakturadodelementy findFaktStopkaPodatnik(String podatnik) {
        return (Fakturadodelementy) em.createNamedQuery("Fakturadodelementy.findByNazwaelementuPodatnik").setParameter("podatnik", podatnik).setParameter("nazwaelementu", "mailstopka").getSingleResult();
    }

    public List<Faktura> findByPodatnik(String podatnik) {
        return Collections.synchronizedList(em.createNamedQuery("Faktura.findByWystawcanazwa").setParameter("wystawcanazwa", podatnik).getResultList());
    }

    public Faktura findByNumerPodatnik(String numerkolejny, String podatnik) {
        return (Faktura) em.createNamedQuery("Faktura.findByNumerkolejnyWystawcanazwa").setParameter("wystawcanazwa", podatnik).setParameter("numerkolejny", numerkolejny).getSingleResult();
    }

    public Faktura findByNumerPodatnikDlaOkresowej(String numerkolejny, String podatnik) {
        return (Faktura) em.createNamedQuery("Faktura.findByNumerkolejnyWystawcanazwaDlaOkresowej").setParameter("wystawcanazwa", podatnik).setParameter("numerkolejny", numerkolejny).getSingleResult();
    }
    
    public List<Faktura> findByPodatnikRok(String podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Faktura.findByWystawcanazwaRok").setParameter("wystawcanazwa", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<Faktura> findByPodatnikRokMc(String podatnik, String rok, String mc) {
        return Collections.synchronizedList(em.createNamedQuery("Faktura.findByWystawcanazwaRokMc").setParameter("wystawcanazwa", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList());
    }

    public List<Faktura> findByPodatnikRokMcPlatnosci(String podatnik, String rok, String mc, boolean niezaplacone0zaplacone1) {
        if (niezaplacone0zaplacone1 == false) {
            return Collections.synchronizedList(em.createNamedQuery("Faktura.findByWystawcanazwaRokMcNiezaplacone").setParameter("wystawcanazwa", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList());
        } else {
            return Collections.synchronizedList(em.createNamedQuery("Faktura.findByWystawcanazwaRokMcZaplacone").setParameter("wystawcanazwa", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList());
        }
    }

    public Fakturywystokresowe findOkresowa(String rok, String klientnip, String nazwapelna, double brutto) {
        return (Fakturywystokresowe) em.createNamedQuery("Fakturywystokresowe.findByOkresowa").setParameter("rok", rok).setParameter("podatnik", nazwapelna).setParameter("nipodbiorcy", klientnip).setParameter("brutto", brutto).getSingleResult();
    }

    public Dok findFaktWystawione(Podatnik podatnik, Klienci kontrahent, String numerkolejny, double brutto) {
        return (Dok) em.createNamedQuery("Dok.findByFakturaWystawiona").setParameter("podatnik", podatnik).setParameter("kontr", kontrahent).setParameter("nrWlDk", numerkolejny).setParameter("brutto", brutto).getSingleResult();
    }

    public List<Wiersz> findWierszefkRozrachunki(String podatnik, Konto konto, Dokfk dokfk) {
        return Collections.synchronizedList(em.createNamedQuery("Wiersz.findByRozrachunki").setParameter("podatnik", podatnik).setParameter("konto", konto).setParameter("dokfk", dokfk).getResultList());
    }

    public List<Wiersz> findWierszeZapisy(String podatnik, String konto) {
        return Collections.synchronizedList(em.createNamedQuery("Wiersz.findByZapisy").setParameter("podatnik", podatnik).setParameter("konto", konto).getResultList());
    }

    public List<Wiersz> findWierszePodatnikMcRok(Podatnik podatnik, WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Wiersz.findByPodatnikMcRok").setParameter("podatnik", podatnik).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList());
    }

    public List<Wiersz> findWierszePodatnikMcRokWNTWDT(Podatnik podatnik, WpisView wpisView, String wntwdt) {
        return Collections.synchronizedList(em.createNamedQuery("Wiersz.findByPodatnikMcRokWNTWDT").setParameter("podatnik", podatnik).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).setParameter("wntwdt", wntwdt).getResultList());
    }

    public List<Wiersz> findWierszePodatnikRok(Podatnik podatnik, WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Wiersz.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", wpisView.getRokWpisuSt()).getResultList());
    }

//
//    public List<Wiersze> findWierszefkRozrachunki(String podatnik, String kontonumer) {
//        return em.createNamedQuery("Wiersz.findByRozrachunki1").setParameter("podatnik", podatnik).getResultList());
//    }
    public Dokfk findDokfk(Dokfk selected) {
        try {
            return (Dokfk) em.createNamedQuery("Dokfk.findByDokEdycjaFK")
                    .setParameter("seriadokfk", selected.getSeriadokfk())
                    .setParameter("rok", selected.getRok())
                    .setParameter("podatnikObj", selected.getPodatnikObj())
                    .setParameter("numerwlasnydokfk", selected.getNumerwlasnydokfk())
                    .setParameter("kontrahent", selected.getKontr())
                    .getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
  
    public Dokfk findDokfkObject(Dokfk selected) {
        return em.find(Dokfk.class, selected);
    }

    public Dokfk findDokfkKontrahent(Dokfk selected) {
        try {
            return (Dokfk) em.createNamedQuery("Dokfk.findByDuplikatKontrahent").setParameter("seriadokfk", selected.getSeriadokfk()).setParameter("rok", selected.getRok()).setParameter("podatnikObj", selected.getPodatnikObj()).setParameter("numerwlasnydokfk", selected.getNumerwlasnydokfk()).setParameter("kontrahent", selected.getKontr()).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaOstAlityka(Podatnik podatnik, Integer rok) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByMapotomkowMaSlownikPodatnik").setParameter("mapotomkow", false).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }
    
    public List<Konto> findKontaOstAlitykaRO(Podatnik podatnik, Integer rok) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByMapotomkowMaSlownikPodatnik").setParameter("mapotomkow", false).setParameter("podatnik", podatnik).setParameter("rok", rok)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                .setHint(QueryHints.READ_ONLY, HintValues.TRUE)
                .getResultList());
    }

    public List<Konto> findKontaOstAlitykaWynikowe(Podatnik podatnik, Integer rok) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByMapotomkowMaSlownikPodatnikWynikowe").setParameter("mapotomkow", false).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<Konto> findKontaOstAlityka5(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByMapotomkowMaSlownikPodatnik5").setParameter("mapotomkow", false).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }

    public List<Fakturywystokresowe> findOkresoweOstatnie(String podatnik, String mc, String rok) {
        switch (mc) {
            case "01":
                return Collections.synchronizedList(em.createNamedQuery("Fakturywystokresowe.findByM1").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "02":
                return Collections.synchronizedList(em.createNamedQuery("Fakturywystokresowe.findByM2").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "03":
                return Collections.synchronizedList(em.createNamedQuery("Fakturywystokresowe.findByM3").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "04":
                return Collections.synchronizedList(em.createNamedQuery("Fakturywystokresowe.findByM4").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "05":
                return Collections.synchronizedList(em.createNamedQuery("Fakturywystokresowe.findByM5").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "06":
                return Collections.synchronizedList(em.createNamedQuery("Fakturywystokresowe.findByM6").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "07":
                return Collections.synchronizedList(em.createNamedQuery("Fakturywystokresowe.findByM7").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "08":
                return Collections.synchronizedList(em.createNamedQuery("Fakturywystokresowe.findByM8").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "09":
                return Collections.synchronizedList(em.createNamedQuery("Fakturywystokresowe.findByM9").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "10":
                return Collections.synchronizedList(em.createNamedQuery("Fakturywystokresowe.findByM10").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "11":
                return Collections.synchronizedList(em.createNamedQuery("Fakturywystokresowe.findByM11").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            case "12":
                return Collections.synchronizedList(em.createNamedQuery("Fakturywystokresowe.findByM12").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
        }
        return null;
    }

    public List<Konto> findKontaPotomnePodatnik(Podatnik podatnik, Integer rok, Konto macierzyste) {
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("kontopozycjaID");
        lg.addAttribute("kontokategoria");
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByMacierzysteBOPodatnik").setParameter("macierzyste", macierzyste).setParameter("podatnik", podatnik).setParameter("rok", rok)
                .setHint(QueryHints.REFRESH, HintValues.TRUE)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    }


    public List<Konto> findKontaSiostrzanePodatnik(Podatnik podatnik, Integer rok, Konto macierzyste) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findBySiostrzaneBOPodatnik").setParameter("macierzyste", macierzyste).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

 
    public Object findKontaPotomnePodatnikCount(Podatnik podatnik, Integer rok, Konto macierzyste) {
        return em.createNamedQuery("Konto.findByMacierzystePodatnikCOUNT").setParameter("macierzyste", macierzyste).setParameter("podatnik", podatnik).setParameter("rok", rok).getSingleResult();
    }

//    public Object findKontaPotomneWzorcowyCount(WpisView wpisView, String macierzyste) {
//        return em.createNamedQuery("Konto.findByMacierzystePodatnikCOUNTWZOR").setParameter("macierzyste", macierzyste).setParameter("rok", wpisView.getRokWpisu()).getSingleResult();
//    }

    public List<Konto> findKontaMaSlownik(Podatnik podatnik, Integer rok, int idslownika) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByMaSlownik").setParameter("idslownika", idslownika).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<Konto> findKontaPotomne(Podatnik podatnik, Integer rok, Konto macierzyste, String bilansowewynikowe) {
        if (macierzyste==null) {
            if (bilansowewynikowe.equals("bilansowe")) {
               return Collections.synchronizedList(em.createNamedQuery("Konto.findByMacierzysteBilansoweNull").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
           } else {
               return Collections.synchronizedList(em.createNamedQuery("Konto.findByMacierzysteWynikoweNull").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
           }   
        } else {
            if (bilansowewynikowe.equals("bilansowe")) {
                return Collections.synchronizedList(em.createNamedQuery("Konto.findByMacierzysteBilansowe").setParameter("macierzyste", macierzyste).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            } else {
                return Collections.synchronizedList(em.createNamedQuery("Konto.findByMacierzysteWynikowe").setParameter("macierzyste", macierzyste).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
            }
        }
    }

//    public List<Konto> findKontaPotomneWzorcowy(Integer rok, String macierzyste, String bilansowewynikowe) {
//        if (bilansowewynikowe.equals("bilansowe")) {
//            return Collections.synchronizedList(em.createNamedQuery("Konto.findByMacierzysteBilansoweWZOR").setParameter("macierzyste", macierzyste).setParameter("rok", rok).getResultList());
//        } else {
//            return Collections.synchronizedList(em.createNamedQuery("Konto.findByMacierzysteWynikoweWZOR").setParameter("macierzyste", macierzyste).setParameter("rok", rok).getResultList());
//        }
//    }

    public List<Konto> findKontaPrzyporzadkowaneAll(String bilansowewynikowe, Podatnik podatnik, Integer rok) {
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("kontopozycjaID");
        lg.addAttribute("kontokategoria");
        if (bilansowewynikowe.equals("bilansowe")) {
            return Collections.synchronizedList(em.createNamedQuery("Konto.findByPozycjaBilansoweAll").setParameter("podatnik", podatnik).setParameter("rok", rok)
                .setHint(QueryHints.REFRESH, HintValues.TRUE)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
            //return Collections.synchronizedList(em.createNamedQuery("Konto.findByPozycjaBilansoweAll").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
        } else {
            return Collections.synchronizedList(em.createNamedQuery("Konto.findByPozycjaWynikoweAll").setParameter("podatnik", podatnik).setParameter("rok", rok)
                .setHint(QueryHints.REFRESH, HintValues.TRUE)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
//            return Collections.synchronizedList(em.createNamedQuery("Konto.findByPozycjaWynikoweAll").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
        }
    }
    
    public List<Konto> findKontaPrzyporzadkowane(String pozycja, String bilansowewynikowe, Podatnik podatnik, Integer rok, String aktywa0pasywa1) {
        if (bilansowewynikowe.equals("bilansowe")) {
            return Collections.synchronizedList(em.createNamedQuery("Konto.findByPozycjaBilansowe").setParameter("pozycja", pozycja).setParameter("aktywa0pasywa1", aktywa0pasywa1).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
        } else {
            return Collections.synchronizedList(em.createNamedQuery("Konto.findByPozycjaWynikowe").setParameter("pozycja", pozycja).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
        }
    }
   
    public Dokfk findDokfkLastofaTypeMc(Podatnik podatnik, String seriadokfk, String rok, String mc) {
        try {
            return (Dokfk) em.createNamedQuery("Dokfk.findByLastofaTypeMc").setParameter("podatnik", podatnik).setParameter("seriadokfk", seriadokfk).setParameter("rok", rok).setParameter("mc", mc).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Dokfk findDokfkLastofaType(Podatnik podatnik, String seriadokfk, String rok) {
        try {
            return (Dokfk) em.createNamedQuery("Dokfk.findByLastofaType").setParameter("podatnik", podatnik).setParameter("seriadokfk", seriadokfk).setParameter("rok", rok).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Dokfk findDokfkLastofaTypeKontrahent(Podatnik podatnik, String seriadokfk, Klienci kontr, String rok) {
        try {
            return (Dokfk) em.createNamedQuery("Dokfk.findByLastofaTypeKontrahent").setParameter("podatnik", podatnik).setParameter("seriadokfk", seriadokfk).setParameter("kontr", kontr).setParameter("rok", rok).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Dok findDokLastofaTypeKontrahent(Podatnik podatnik, Klienci kontr, String pkpirR) {
        try {
            return (Dok) em.createNamedQuery("Dok.findByfindByLastofaTypeKontrahent").setParameter("podatnik", podatnik).setParameter("kontr", kontr).setParameter("pkpirR", pkpirR).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Tabelanbp findByDateWaluta(String datatabeli, String nazwawaluty) {
        return (Tabelanbp) em.createNamedQuery("Tabelanbp.findByDatatabeliSymbolwaluty").setParameter("datatabeli", datatabeli).setParameter("symbolwaluty", nazwawaluty).getSingleResult();
    }
    
    public Tabelanbp findById(int id) {
        return (Tabelanbp) em.createNamedQuery("Tabelanbp.findByIdtabelanbp").setParameter("idtabelanbp", id).getSingleResult();
    }

    public List<Tabelanbp> findByWaluta(Waluty waluta) {
        return Collections.synchronizedList(em.createNamedQuery("Tabelanbp.findByWaluta").setParameter("waluta", waluta).getResultList());
    }
    
    public List<Tabelanbp> findByWalutaMcRok(String symbolwaluty, String mc, String rok) {
        String likedatatabeli = rok+"-"+mc+"-%";
        return Collections.synchronizedList(em.createNamedQuery("Tabelanbp.findBySymbolWalutyRokMc").setParameter("symbolwaluty", symbolwaluty).setParameter("likedatatabeli", likedatatabeli).getResultList());
    }

    public List<Tabelanbp> findByDateWalutaLista(String datatabeli, String nazwawaluty) {
        return Collections.synchronizedList(em.createNamedQuery("Tabelanbp.findByDatatabeliSymbolwaluty").setParameter("datatabeli", datatabeli).setParameter("symbolwaluty", nazwawaluty).getResultList());
    }

    public Tabelanbp findTabelaPLN() {
        return (Tabelanbp) em.createNamedQuery("Tabelanbp.findBySymbolWaluty").setParameter("symbolwaluty", "PLN").getSingleResult();
    }

    public Tabelanbp findOstatniaTabela(String symbolwaluty) {
        return (Tabelanbp) em.createNamedQuery("Tabelanbp.findBySymbolWalutyOstatnia").setParameter("symbolwaluty", symbolwaluty).setMaxResults(1).getSingleResult();
    }

    public Waluty findWalutaBySymbolWaluty(String staranazwa) {
        try {
            return (Waluty) em.createNamedQuery("Waluty.findBySymbolwaluty").setParameter("symbolwaluty", staranazwa).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Sesja> findUser(String user) {
        return Collections.synchronizedList(em.createNamedQuery("Sesja.findByUzytkownik").setParameter("uzytkownik", user).getResultList());
    }

    public Collection<? extends Dokfk> findDokByTypeYear(String BO, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Dokfk.findBySeriaRokdokfk").setParameter("seriadokfk", BO).setParameter("rok", rok).getResultList());
    }

    public List<PozycjaRZiS> findUkladBR(UkladBR u) {
        String uklad = u.getUklad();
        String podatnik = u.getPodatnik().getNazwapelna();
        String rok = u.getRok();
        return Collections.synchronizedList(em.createNamedQuery("PozycjaRZiS.findByUkladPodRok").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<PozycjaRZiS> findUkladBR(String uklad, String podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("PozycjaRZiS.findByUkladPodRok").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }
    
    public List<PozycjaRZiSBilans> findRZiSPozString(String pozycjaString, String rokWpisuSt, String uklad) {
        return Collections.synchronizedList(em.createNamedQuery("PozycjaRZiS.findBilansPozString").setParameter("pozycjaString", pozycjaString).setParameter("rok", rokWpisuSt).setParameter("uklad", uklad).getResultList());
    }


    public List<PozycjaRZiS> findUkladBRBilans(String uklad, String podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("PozycjaBilans.findByUkladPodRok").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<PozycjaRZiS> findBilansukladAktywa(UkladBR u) {
        String uklad = u.getUklad();
        String podatnik = u.getPodatnik().getNazwapelna();
        String rok = u.getRok();
        return Collections.synchronizedList(em.createNamedQuery("PozycjaBilans.findByUkladPodRokAktywa").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<PozycjaRZiS> findBilansukladPasywa(UkladBR u) {
        String uklad = u.getUklad();
        String podatnik = u.getPodatnik().getNazwapelna();
        String rok = u.getRok();
        return Collections.synchronizedList(em.createNamedQuery("PozycjaBilans.findByUkladPodRokPasywa").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }
    
    public List<PozycjaRZiS> findBilansukladAktywaPasywa(UkladBR u) {
        String uklad = u.getUklad();
        String podatnik = u.getPodatnik().getNazwapelna();
        String rok = u.getRok();
        return Collections.synchronizedList(em.createNamedQuery("PozycjaBilans.findByUkladPodRokAtywaPasywa").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<PozycjaRZiSBilans> findBilansPozString(String pozycjaString, String rokWpisuSt, String uklad) {
        return Collections.synchronizedList(em.createNamedQuery("PozycjaBilans.findBilansPozString").setParameter("pozycjaString", pozycjaString).setParameter("rok", rokWpisuSt).setParameter("uklad", uklad).getResultList());
    }

    
    public List<KontopozycjaBiezaca> findKontaBiezacePodatnikUklad(UkladBR uklad, String rb) {
        if (rb.equals("wynikowe")) {
            return Collections.synchronizedList(em.createNamedQuery("KontopozycjaBiezaca.findByUkladWynikowe").setParameter("uklad", uklad).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
        } else {
            return Collections.synchronizedList(em.createNamedQuery("KontopozycjaBiezaca.findByUkladBilansowe").setParameter("uklad", uklad).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
        }
    }

    public List<KontopozycjaZapis> findKontaZapisPodatnikUklad(UkladBR uklad, String rb) {
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("ukladBR");
        lg.addAttribute("kontoID");
        if (rb.equals("wynikowe")) {
            return Collections.synchronizedList(em.createNamedQuery("KontopozycjaZapis.findByUkladWynikowe").setParameter("uklad", uklad).setHint(QueryHints.REFRESH, HintValues.TRUE).setHint(QueryHints.LOAD_GROUP, lg).getResultList());
        } else {
            return Collections.synchronizedList(em.createNamedQuery("KontopozycjaZapis.findByUkladBilansowe").setParameter("uklad", uklad).setHint(QueryHints.REFRESH, HintValues.TRUE).setHint(QueryHints.LOAD_GROUP, lg).getResultList());
        }
    }
    
//    public List<KontopozycjaZapis> findKontaZapisPodatnikUkladWzorzec(UkladBR uklad, String rb) {
//        if (rb.equals("wynikowe")) {
//            return Collections.synchronizedList(em.createNamedQuery("KontopozycjaZapis.findByUkladWynikowe").setParameter("uklad", uklad).getResultList());
//        } else {
//            return Collections.synchronizedList(em.createNamedQuery("KontopozycjaZapis.findByUkladBilansoweWzorzec").setParameter("uklad", uklad).getResultList());
//        }
//    }

    public Object findVatuepodatnik(String rokWpisu, String symbolokresu, String podatnikWpisu) {
        return em.createNamedQuery("Vatuepodatnik.findByRokKlientSymbolokresu").setParameter("rok", rokWpisu).setParameter("klient", podatnikWpisu).setParameter("symbolokresu", symbolokresu).getSingleResult();
    }

    public List<Pismoadmin> findPismoadminBiezace() {
        return Collections.synchronizedList(em.createNamedQuery("Pismoadmin.findByNOTStatus").setParameter("status", "archiwalna").getResultList());
    }

    public List<Pismoadmin> findPismoadminNowe() {
        return Collections.synchronizedList(em.createNamedQuery("Pismoadmin.findByStatus").setParameter("status", "wysłana").getResultList());
    }

    public Kliencifk znajdzkontofk(String nip, String podatniknip) {
        try {
            return (Kliencifk) em.createNamedQuery("Kliencifk.findByNipPodatniknip").setParameter("nip", nip).setParameter("podatniknip", podatniknip).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Kliencifk> znajdzkontofkKlient(String podatniknip) {
        return Collections.synchronizedList(em.createNamedQuery("Kliencifk.findByPodatniknip").setParameter("podatniknip", podatniknip).getResultList());
    }
    public Kliencifk znajdzkontofkByKonto(Konto konto) {
        return (Kliencifk) em.createNamedQuery("Kliencifk.findByNrkonta").setParameter("nrkonta", konto.getNrkonta()).setParameter("podatniknip", konto.getPodatnik().getNip()).getSingleResult();
    }


    public List<Podatnik> findPodatnikFK() {
        return Collections.synchronizedList(em.createNamedQuery("Podatnik.findByFirmafk").setParameter("firmafk", 1).getResultList());
    }

    public List<Podatnik> findPodatnikNieFK() {
        return Collections.synchronizedList(em.createNamedQuery("Podatnik.findByFirmafk").setParameter("firmafk", 0).getResultList());
    }

    public List<Podatnik> findPodatnikFKPkpir() {
        return Collections.synchronizedList(em.createNamedQuery("Podatnik.findByFirmafk").setParameter("firmafk", 3).getResultList());
    }

    public List<Podatnik> findPodatnikZUS() {
        return Collections.synchronizedList(em.createNamedQuery("Podatnik.findByZUS").getResultList());
    }

    public List<Konto> findKontoPodatnik(Podatnik podatnik, String rok) {
        return em.createNamedQuery("Konto.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
    }
    
    public List<Konto> findKontoPodatnikRO(Podatnik podatnik, String rok) {
        return em.createNamedQuery("Konto.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok))
                .setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
    }
    
    public List<Konto> findKontoPodatnikRelacje(Podatnik podatnik, String rok) {
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("kontopozycjaID");
        lg.addAttribute("kontokategoria");
        lg.addAttribute("kontomacierzyste");
        lg.setIsConcurrent(Boolean.TRUE);
        return em.createNamedQuery("Konto.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok))
                .setHint(QueryHints.REFRESH, HintValues.TRUE)
                .setHint(QueryHints.REFRESH_CASCADE, CascadePolicy.CascadeAllParts)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList();
    }
    
//    public List<Konto> findKontoPodatnikEager(Podatnik podatnik, String rok) {
//        return Collections.synchronizedList(em.createNamedQuery("Konto.findByPodatnikEager").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList());
//    }
    
    public List<Konto> findKontoPodatnikKsiegi(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByPodatnikKsiegi").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList());
    }

    public List<Konto> findKontoPodatnikBezSlownik(Podatnik podatnik, String rok) {
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("kontopozycjaID");
         return Collections.synchronizedList(em.createNamedQuery("Konto.findByPodatnikBezSlownik").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).setHint(QueryHints.REFRESH, HintValues.TRUE)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    }
    public List<Konto> findKontoPodatnikBezSlownikKsiegi(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByPodatnikBezSlownikKsiegi").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList());
    }

    public List<Konto> findKontoPodatnikTylkoSlownik(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByPodatnikTylkoSlownik").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList());
    }

    public List<Konto> findKontoPodatnikBez0(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByPodatnikBez0").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE).setHint(QueryHints.READ_ONLY, HintValues.TRUE).getResultList());
    }

    public List<Konto> findWszystkieKontaPodatnikaBO(WpisView wpisView, String kategoriaKonta) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByKontaPodatnikaBO").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("wzorzec", kategoriaKonta).getResultList());
    }

    public List<Konto> findWszystkieKontaBilansowePodatnika(Podatnik podatnik, String rok) {
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("kontopozycjaID");
        lg.addAttribute("kontokategoria");
        lg.addAttribute("kontomacierzyste");
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByPodatnikBilansowe").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).setHint(QueryHints.REFRESH, HintValues.TRUE)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    }

    public List<Konto> findWszystkieKontaWynikowePodatnika(Podatnik podatnik, String rok) {
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("kontopozycjaID");
        lg.addAttribute("kontokategoria");
        lg.addAttribute("kontomacierzyste");
       return Collections.synchronizedList(em.createNamedQuery("Konto.findByPodatnikWynikowe").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).setHint(QueryHints.REFRESH, HintValues.TRUE)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    }

    public List<Konto> findKontaWynikowePodatnikaBezPotomkow(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByPodatnikWynikoweBezPotomkow").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }
    
    public List<Konto> findKontaPodatnikZPotomkami(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByPodatnikZPotomkami").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }
    
    public List<Konto> findKontaWynikowePodatnikaBezPotomkowRokPop(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByPodatnikWynikoweBezPotomkow").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokUprzedni()).getResultList());
    }

    public List<Konto> findKontaBilansowePodatnikaBezPotomkow(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByPodatnikBilansoweBezPotomkow").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }
    
    public List<Konto> findKontaBilansowePodatnikaBezPotomkowRokPoprzedni(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByPodatnikBilansoweBezPotomkow").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokUprzedni()).getResultList());
    }
    public List<Konto> findKontaBilansowePodatnikaKwotaBezPotomkow(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByPodatnikBilansoweKwotaBezPotomkow").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }

    public Konto findKonto860(WpisView wpisView) {
        return (Konto) em.createNamedQuery("Konto.findByKonto860").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getSingleResult();
    }

    public Konto findKontoPodatnik490(WpisView wpisView) {
        return (Konto) em.createNamedQuery("Konto.findByPodatnik490").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getSingleResult();
    }

    public int resetujKolumneMapotomkow(WpisView wpisView) {
        return em.createNamedQuery("Konto.updateMapotomkow").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).executeUpdate();
    }

    public int resetujKolumneZablokowane(WpisView wpisView) {
        return em.createNamedQuery("Konto.updateZablokowane").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).executeUpdate();
    }

    public List<String> findKlienciNIP() {
        return Collections.synchronizedList(em.createNamedQuery("Klienci.findKlienciNip").getResultList());
    }

    public List<String> findNazwaPelna(String nowanazwa) {
        return Collections.synchronizedList(em.createNamedQuery("Klienci.findByNpelna").setParameter("npelna", nowanazwa).getResultList());
    }

    public Zusmail findZusmail(Zusmail zusmail) {
        return (Zusmail) em.createNamedQuery("Zusmail.findByPK").setParameter("podatnik", zusmail.getPodatnik()).setParameter("rok", zusmail.getRok()).setParameter("mc", zusmail.getMc()).getSingleResult();
    }

    public List<Zusmail> findZusRokMc(String rok, String mc) {
        return Collections.synchronizedList(em.createNamedQuery("Zusmail.findByRokMc").setParameter("rok", rok).setParameter("mc", mc).getResultList());
    }

    public List<Dokfk> findDokfkPodatnikRokMc(WpisView w) {
        return Collections.synchronizedList(em.createNamedQuery("Dokfk.findByPodatnikRokMc").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).setParameter("mc", w.getMiesiacWpisu()).getResultList());
    }
    
    public List<Dokfk> findDokfkPodatnikRokMcVAT(WpisView w) {
        return Collections.synchronizedList(em.createNamedQuery("Dokfk.findByPodatnikRokMcVAT").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).setParameter("mc", w.getMiesiacWpisu()).getResultList());
    }
    
    public List<Dokfk> findDokfkPodatnikRokKw(WpisView w, List<String> mcekw) {
        return Collections.synchronizedList(em.createNamedQuery("Dokfk.findByPodatnikRokKw").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).setParameter("mc1", mcekw.get(0)).setParameter("mc2", mcekw.get(1)).setParameter("mc3", mcekw.get(2)).getResultList());
    }

    public List<Dokfk> findDokfkPodatnikRok(WpisView w) {
        return Collections.synchronizedList(em.createNamedQuery("Dokfk.findByPodatnikRok").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).getResultList());
    }
    
    public List<Dokfk> findDokfkPodatnik(WpisView w) {
        return Collections.synchronizedList(em.createNamedQuery("Dokfk.findByPodatnik").setParameter("podatnik", w.getPodatnikObiekt()).getResultList());
    }

    public List<Dokfk> findDokfkPodatnikRokSrodkiTrwale(WpisView w) {
        return Collections.synchronizedList(em.createNamedQuery("Dokfk.findByPodatnikRokSrodkiTrwale").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).getResultList());
    }

    public List<Dokfk> findDokfkPodatnikRokRMK(WpisView w) {
        return Collections.synchronizedList(em.createNamedQuery("Dokfk.findByPodatnikRokRMK").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).getResultList());
    }

    public List<Dokfk> findDokfkPodatnikRokMcKategoria(WpisView w, String kategoria) {
        return Collections.synchronizedList(em.createNamedQuery("Dokfk.findByPodatnikRokMcKategoria").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).setParameter("mc", w.getMiesiacWpisu()).setParameter("kategoria", kategoria).getResultList());
    }

    public List<Dokfk> findDokfkPodatnikRokKategoria(WpisView w, String kategoria) {
        return Collections.synchronizedList(em.createNamedQuery("Dokfk.findByPodatnikRokKategoria").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).setParameter("kategoria", kategoria).getResultList());
    }
    
    public List<Dokfk> findDokfkPodatnikRokKategoria(Podatnik podatnik, String rok, String kategoria) {
        return Collections.synchronizedList(em.createNamedQuery("Dokfk.findByPodatnikRokKategoria").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("kategoria", kategoria).getResultList());
    }

    public List<Dokfk> findDokfkPodatnikRokKategoriaOrderByNo(WpisView w, String kategoria) {
        return Collections.synchronizedList(em.createNamedQuery("Dokfk.findByPodatnikRokKategoriaOrderByNo").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).setParameter("kategoria", kategoria).getResultList());
    }

    public Rejestrlogowan findRejestrlogowanByIP(String ipusera) {
        return (Rejestrlogowan) em.createNamedQuery("Rejestrlogowan.findByIpusera").setParameter("ipusera", ipusera).getSingleResult();
    }

    public List<Rejestrlogowan> RejestrlogowanfindByLiczbalogowan0() {
        return Collections.synchronizedList(em.createNamedQuery("Rejestrlogowan.findByIloscLogowan0").getResultList());
    }

    public List<Podatnik> findAktywnyPodatnik(Boolean podmiotaktywny) {
        return Collections.synchronizedList(em.createNamedQuery("Podatnik.findByPodmiotaktywny").setParameter("podmiotaktywny", podmiotaktywny).getResultList());
    }
    
    public List<Podatnik> findAktywnyPodatnikRO(Boolean podmiotaktywny) {
        return Collections.synchronizedList(em.createNamedQuery("Podatnik.findByPodmiotaktywny").setParameter("podmiotaktywny", podmiotaktywny)
                .setHint(QueryHints.READ_ONLY, HintValues.TRUE)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                .getResultList());
    }

    public int usunniezaksiegowane(String podatnik) {
        return em.createNamedQuery("Rozrachunekfk.usunNiezaksiegowane").setParameter("podatnik", podatnik).executeUpdate();
    }

    public int usunTransakcjeNiezaksiegowane(String podatnik) {
        return em.createNamedQuery("Transakcja.usunNiezaksiegowane").setParameter("podatnik", podatnik).executeUpdate();
    }

    public Transakcja findTransakcja(int rozliczany, int sparowany) {
        try {
            return (Transakcja) em.createNamedQuery("Transakcja.findByRozliczanySparowany").setParameter("rozliczany", rozliczany).setParameter("sparowany", sparowany).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Transakcja> findTransakcjeRozliczonyID(int idrozrachunku) {
        try {
            return Collections.synchronizedList(em.createNamedQuery("Transakcja.findByRozliczonyID").setParameter("rozliczany", idrozrachunku).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Transakcja> findTransakcjeSparowanyID(String podatnik) {
        try {
            return Collections.synchronizedList(em.createNamedQuery("Transakcja.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Dok> znajdzOdDo(long odd, long dod) {
        return Collections.synchronizedList(em.createNamedQuery("Dok.findByIdDokOdDo").setParameter("odd", odd).setParameter("dod", dod).getResultList());
    }

    public Klienci findKlientByNazwa(String nazwapelna) {
        return (Klienci) em.createNamedQuery("Klienci.findByNpelna").setParameter("npelna", nazwapelna).getSingleResult();
    }

    public Klienci findKlientByNip(String nip) {
        try {
            return (Klienci) em.createNamedQuery("Klienci.findByNip").setParameter("nip", nip).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<String> findKlientByNipXX() {
        try {
            return em.createNamedQuery("Klienci.findByNipXX").setParameter("nip", "XX%").getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public Klienci findKlientByNipImport(String nip) {
        List<Klienci> wynik = null;
        try {
            wynik = em.createNamedQuery("Klienci.findByNip").setParameter("nip", nip).getResultList();
            if (!wynik.isEmpty() && wynik.size()==1) {
                return wynik.get(0);
            } else if (!wynik.isEmpty() && wynik.size()>1){
                return new Klienci();
            } else {
                return null;
            }
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    

    public Dok findDokByNr(String numer) {
        return (Dok) em.createNamedQuery("Dok.findByNrWlDk").setParameter("nrWlDk", "fvp/2013/13185/m").getSingleResult();
    }

    public List<Dok> znajdzKontr1Null() {
        return Collections.synchronizedList(em.createNamedQuery("Dok.findByKontr1Null").getResultList());
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMaWaluta(Konto konto, String symbolwaluty, String wnma) {
        try {
            
            return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByStronaWierszaKontoWaluta").setParameter("konto", konto).setParameter("symbolwaluty", symbolwaluty).setParameter("wnma", wnma).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMa(Konto konto, String wnma) {
        try {
            LoadGroup lg = new LoadGroup();
            lg.addAttribute("nowetransakcje");
            return em.createNamedQuery("StronaWiersza.findByStronaWierszaKonto").setParameter("konto", konto).setParameter("wnma", wnma)
                    .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<StronaWiersza> findStronaWierszaByKontoOnly(Konto konto) {
        try {
            return em.createNamedQuery("StronaWiersza.findByStronaWierszaKontoOnly").setParameter("konto", konto).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMaWalutaKorekta(Konto konto, String symbolwaluty, String wnma) {
        try {
            return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByStronaWierszaKontoWalutaKorekta").setParameter("konto", konto).setParameter("symbolwaluty", symbolwaluty).setParameter("wnma", wnma).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMaKorekta(Konto konto, String wnma) {
        try {
            LoadGroup lg = new LoadGroup();
            lg.addAttribute("nowetransakcje");
            return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByStronaWierszaKontoKorekta").setParameter("konto", konto).setParameter("wnma", wnma)
                    .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMaBO(Konto konto, String wnma) {
        try {
            return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByStronaWierszaKontoBO").setParameter("konto", konto).setParameter("wnma", wnma).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMaWalutaBO(Konto konto, String symbolwaluty, String wnma) {
        try {
             return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByStronaWierszaKontoWalutaBO").setParameter("konto", konto).setParameter("symbolwaluty", symbolwaluty).setParameter("wnma", wnma).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Rodzajedok> findListaWspolne(Podatnik podatnik) {
         return Collections.synchronizedList(em.createNamedQuery("Rodzajedok.findByListaWspolna").setParameter("podatnik", podatnik).getResultList());
    }

    public List<Rodzajedok> findListaPodatnik(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Rodzajedok.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }
    
    public List<Rodzajedok> findListaPodatnikNull(Podatnik podatnik) {
        return Collections.synchronizedList(em.createNamedQuery("Rodzajedok.findByPodatnikNull").setParameter("podatnik", podatnik).getResultList());
    }
    
    public List<Rodzajedok> findListaPodatnikRO(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("Rodzajedok.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).setHint(QueryHints.READ_ONLY, HintValues.TRUE).setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteRokWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty, MiejsceKosztow p) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteRokWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).setParameter("nazwapelna", p.getOpismiejsca()).getResultList());
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteRok(Podatnik podatnik, Konto konto, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteRok").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList());
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoSyntetyczneRok(Podatnik podatnik, Konto konto, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoSyntetyczneRok").setParameter("podatnikObj", podatnik).setParameter("kontonumer", konto.getPelnynumer()).setParameter("konto", konto).setParameter("rok", rok).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWaluta(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, MiejsceKosztow p) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteMcWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("mc", mc).setParameter("symbolwaluty", skrotWaluty).setParameter("nazwapelna", p.getOpismiejsca()).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWalutaPojazdy(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, Pojazdy p) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteMcWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto.getPelnynumer()).setParameter("mc", mc).setParameter("symbolwaluty", skrotWaluty).setParameter("nazwapelna", p.getNrrejestracyjny()).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWalutaDelegacja(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, Delegacja p) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteMcWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto.getPelnynumer()).setParameter("mc", mc).setParameter("symbolwaluty", skrotWaluty).setParameter("nazwapelna", p.getOpisdlugi()).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoBOWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoBOWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoBOWalutaWszystkie(Podatnik podatnik, Konto konto, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoBOWalutaWszystkie").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutaWszystkie(Podatnik podatnik, Konto konto, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutaWszystkie").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokMcWalutaWszystkie(Podatnik podatnik, Konto konto, String rok, String mc) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokMcWalutaWszystkie").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).setParameter("mc", mc).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWszystkieNT(Podatnik podatnik, Konto konto, String rok) {
        //List lista = em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokWszystkieNT").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList();
        //this.refresh(lista);
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("nowetransakcje");
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokWszystkieNT").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok)
                .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutyWszystkieNT(Podatnik podatnik, String wybranaWalutaDlaKonta, Konto konto, String rok) {
        //List lista = em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutyWszystkieNT").setParameter("podatnikObj", podatnik).setParameter("wybranaWalutaDlaKonta", wybranaWalutaDlaKonta).setParameter("konto", konto).setParameter("rok", rok).getResultList();
        //this.refresh(lista);
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("nowetransakcje");
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutyWszystkieNT").setParameter("podatnikObj", podatnik).setParameter("wybranaWalutaDlaKonta", wybranaWalutaDlaKonta).setParameter("konto", konto).setParameter("rok", rok)
                .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWszystkieR(Podatnik podatnik, Konto konto, String rok) {
        //List lista = em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokWszystkieR").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList();
        //this.refresh(lista);
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("platnosci");
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokWszystkieR").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok)
                .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutyWszystkieR(Podatnik podatnik, String wybranaWalutaDlaKonta, Konto konto, String rok) {
        //List lista = em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutyWszystkieR").setParameter("podatnikObj", podatnik).setParameter("wybranaWalutaDlaKonta", wybranaWalutaDlaKonta).setParameter("konto", konto).setParameter("rok", rok).getResultList();
        //this.refresh(lista);
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("platnosci");
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutyWszystkieR").setParameter("podatnikObj", podatnik).setParameter("wybranaWalutaDlaKonta", wybranaWalutaDlaKonta).setParameter("konto", konto).setParameter("rok", rok)
                .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikRokWalutaWynik(Podatnik podatnik, String rok, String skrotWaluty) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikRokWalutaWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList());
    }

    
    public List<StronaWiersza> findStronaByPodatnikRokWynikBO(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikRokWynikBO").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikWynikCecha(Podatnik podatnik) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikWynikCecha").setParameter("podatnikObj", podatnik).getResultList());
    }
    
    public List<StronaWiersza> findStronaByPodatnikWynikCechaRokMc(Podatnik podatnik, String rok, String mc) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikWynikCechaRokMc").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikRokMcWynik(Podatnik podatnik, String rok, String mc) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikRokMcWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikRokMcWynikSlownik(Podatnik podatnik, String rok, String mc) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikRokMcWynikSlownik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList());
    }
    
    public List<Konto> findStronaByPodatnikRokKontoDist(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findStronaByPodatnikRokKontoDist").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList());
    }
    public List<StronaWiersza> findStronaByPodatnikRok(Podatnik podatnik, String rok) {
        //return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikRok").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList());
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("wiersz.dokfk");
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikRok").setParameter("podatnikObj", podatnik).setParameter("rok", rok)
                .setHint(QueryHints.READ_ONLY, HintValues.TRUE)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                .setHint(QueryHints.REFRESH, HintValues.TRUE)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikRokWalutaBilans(Podatnik podatnik, String rok, String skrotWaluty) {
        //return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikRokWalutaBilans").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList());
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("wiersz.dokfk");
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikRokWalutaBilans").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty)
                .setHint(QueryHints.READ_ONLY, HintValues.TRUE)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                .setHint(QueryHints.REFRESH, HintValues.TRUE)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikRokWynik(Podatnik podatnik, String rok) {
        //return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikRokWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList());
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("wiersz.dokfk");
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikRokWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok)
                .setHint(QueryHints.READ_ONLY, HintValues.TRUE)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                .setHint(QueryHints.REFRESH, HintValues.TRUE)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokBilans(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikRokBilans").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setHint(QueryHints.READ_ONLY, HintValues.TRUE).getResultList());
    }

    public List<Konto> findKontoByPodatnikRokBilans(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByKontoDistinctPodatnikRokBilans").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikRokWalutaBilansBO(Podatnik podatnik, String rok, String skrotWaluty) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikRokWalutaBilansBO").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikRokBilansBO(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikRokBilansBO").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<WierszBO> findBOLista0(String grupa, WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("WierszBO.findByLista").setParameter("grupakonta", grupa).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList());
    }
    
    public List<WierszBO> findBOLista0likwidacja(String grupa, WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("WierszBO.findByListaLikwidacja").setParameter("grupakonta", grupa).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList());
    }
    
    public List<WierszBO> findBOListaRokMc(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("WierszBO.findByListaRokMc").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList());
    }

    public List<WierszBO> findWierszBOPodatnikRok(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("WierszBO.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<WierszBO> findWierszBOPodatnikRokRozrachunkowe(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("WierszBO.findByPodatnikRokRozrachunkowe").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<WierszBO> findWierszBOPodatnikRokKonto(Podatnik podatnikObiekt, String rokWpisuSt, Konto konto) {
        return Collections.synchronizedList(em.createNamedQuery("WierszBO.findByPodatnikRokKonto").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("konto", konto).getResultList());
    }

    public List<WierszBO> findWierszBOPodatnikRokKontoWaluta(Podatnik podatnikObiekt, String rokWpisuSt, Konto konto, String waluta) {
        return Collections.synchronizedList(em.createNamedQuery("WierszBO.findByPodatnikRokKontoWaluta").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("konto", konto).setParameter("waluta", waluta).getResultList());
    }

    public List<Transakcja> findByNowaTransakcja(StronaWiersza s) {
        return Collections.synchronizedList(em.createNamedQuery("Transakcja.findByNowaTransakcja").setParameter("nowatransakcja", s).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
    }

    public List<Transakcja> findByRozliczajacy(StronaWiersza s) {
        return Collections.synchronizedList(em.createNamedQuery("Transakcja.findByRozliczajacy").setParameter("rozliczajacy", s).getResultList());
    }

    public Dok znajdzDokumentInwestycja(WpisView wpisView, Dok r) {
        return (Dok) em.createNamedQuery("Dok.znajdzInwestycja").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("data", r.getDataWyst()).setParameter("netto", r.getNetto()).setParameter("numer", r.getNrWlDk()).getSingleResult();
    }

    public EVatwpisFK znajdzEVatwpisFKPoWierszu(Wiersz wiersz) {
        return (EVatwpisFK) em.createNamedQuery("EVatwpisFK.findByWiersz").setParameter("wiersz", wiersz).getSingleResult();
    }

    public List<Konto> findlistaKontKasaBank(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findlistaKontKasaBank").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }

    public List<Konto> findlistaKontGrupa0(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findlistaKontGrupa0").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }

    public List<Konto> findlistaKontGrupa0Analityka(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findlistaKontGrupa0Analityka").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }

    public List<Konto> findlistaKontGrupa1(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findlistaKontGrupa1").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }

    public List<Konto> findlistaKontGrupa2(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findlistaKontGrupa2").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }

    public List<Konto> findlistaKontGrupa3(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findlistaKontGrupa3").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }
//moga
    public List<Konto> findlistaKontGrupa4(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findlistaKontGrupa4").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }

    public List<Konto> findlistaKontGrupa5(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findlistaKontGrupa5").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }

    public List<Konto> findlistaKontGrupa6(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findlistaKontGrupa6").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }

    public List<Konto> findlistaKontGrupa7(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findlistaKontGrupa7").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }

    public List<Konto> findlistaKontGrupa8(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findlistaKontGrupa8").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
    }

    public List<Transakcja> findByKonto(Konto wybraneKontoNode) {
        return Collections.synchronizedList(em.createNamedQuery(("Transakcja.findByKonto")).setParameter("konto", wybraneKontoNode).getResultList());
    }

    public List<Transakcja> findByPodatniRok(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Transakcja.findByPodatnikRok").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("podatnikObj", wpisView.getPodatnikObiekt()).getResultList());
    }

    public List<Transakcja> findByPodatnikBO(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Transakcja.findByPodatnikBO").setParameter("podatnik", wpisView.getPodatnikWpisu()).getResultList());
    }

    public List<Transakcja> findByPodatniRokRozniceKursowe(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Transakcja.findByPodatnikRokRozniceKursowe").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).setParameter("podatnikObj", wpisView.getPodatnikObiekt()).getResultList());
    }
    
    public List<Transakcja> findByPodatniRokRozniceKursowe(WpisView wpisView, String mc) {
        return Collections.synchronizedList(em.createNamedQuery("Transakcja.findByPodatnikRokRozniceKursowe").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", mc).setParameter("podatnikObj", wpisView.getPodatnikObiekt()).getResultList());
    }

    public List<Transakcja> findByPodatnikBORozniceKursowe(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Transakcja.findByPodatnikBORozniceKursowe").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList());
    }

    public Faktura findfaktura(Faktura f) {
        return em.find(Faktura.class, f);
    }

    public Fakturaelementygraficzne findFaktElementyGraficzne(String podatnik) {
        try {
            return (Fakturaelementygraficzne) em.createNamedQuery("Fakturaelementygraficzne.findByPodatnik").setParameter("podatnik", podatnik).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutyWszystkie(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutyWszystkie").setParameter("podatnikObj", podatnikObiekt).setParameter("konto", konto).setParameter("rok", rokWpisuSt).getResultList());
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoStartRokWalutyWszystkie(Podatnik podatnikObiekt, String konto, String rokWpisuSt) {
        //t.platnosci t.wiersz.dokfk t.wiersz.tabelanbp
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("platnosci");
        lg.addAttribute("konto");
        lg.addAttribute("wiersz.dokfk");
        lg.addAttribute("wiersz.tabelanbp");
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoStartRokWalutyWszystkie")
                .setParameter("podatnikObj", podatnikObiekt)
                .setParameter("konto", konto)
                .setParameter("rok", rokWpisuSt)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoStartRokWalutyWszystkieOdswiez(Podatnik podatnikObiekt, String konto, String rokWpisuSt) {
        //t.platnosci t.wiersz.dokfk t.wiersz.tabelanbp
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("platnosci");
        lg.addAttribute("konto");
        lg.addAttribute("wiersz.dokfk");
        lg.addAttribute("wiersz.tabelanbp");
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoStartRokWalutyWszystkie")
                .setParameter("podatnikObj", podatnikObiekt)
                .setParameter("konto", konto)
                .setParameter("rok", rokWpisuSt)
                .setHint(QueryHints.REFRESH, HintValues.TRUE)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokMcWalutyWszystkie(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt, String mc) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokMcWalutyWszystkie").setParameter("podatnikObj", podatnikObiekt).setParameter("konto", konto).setParameter("rok", rokWpisuSt).setParameter("mc", mc).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokMcVAT(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt, String mc) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokMcVAT").setParameter("podatnikObj", podatnikObiekt).setParameter("konto", konto).setParameter("rok", rokWpisuSt).setParameter("mc", mc).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokVAT(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt) {
        return Collections.synchronizedList(em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokVAT").setParameter("podatnikObj", podatnikObiekt).setParameter("konto", konto).setParameter("rok", rokWpisuSt).getResultList());
    }

    public Dokfk findDokfofaTypeKontrahent(Podatnik podatnikWpisu, String vat, String rokWpisuSt, String mc) {
        return (Dokfk) em.createNamedQuery("Dokfk.findBySeriaNumerRokdokfk").setParameter("seriadokfk", vat).setParameter("rok", rokWpisuSt).setParameter("podatnik", podatnikWpisu).setParameter("mc", mc).getSingleResult();
    }
    
    public List<Dokfk> findDokfofaTypeKontrahentKilka(Podatnik podatnikWpisu, String vat, String rokWpisuSt, String mc) {
        return Collections.synchronizedList(em.createNamedQuery("Dokfk.findBySeriaNumerRokdokfk").setParameter("seriadokfk", vat).setParameter("rok", rokWpisuSt).setParameter("podatnik", podatnikWpisu).setParameter("mc", mc).getResultList());
    }

//    public List<Konto> findKontaWzorcowy(WpisView wpisView) {
//        return Collections.synchronizedList(em.createNamedQuery("Konto.findWzorcowe").setParameter("rok", wpisView.getRokWpisu()).getResultList());
//    }
//    
//    public List<Konto> findKontaWzorcowy(Integer rok) {
//        return Collections.synchronizedList(em.createNamedQuery("Konto.findWzorcowe").setParameter("rok", rok).getResultList());
//    }

    public List<RMK> findRMKByPodatnikRok(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("RMK.findByPodatnikRok").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("podatnikObj", wpisView.getPodatnikObiekt()).getResultList());
    }

    public List<RMK> findRMKByPodatnikRokDokfk(WpisView wpisView, Dokfk dokfk) {
        return Collections.synchronizedList(em.createNamedQuery("RMK.findByPodatnikRokDokfk").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("podatnikObj", wpisView.getPodatnikObiekt()).setParameter("dokfk", dokfk).getResultList());
    }

    public List<MiejsceKosztow> findMiejscaPodatnik(Podatnik podatnik) {
        return Collections.synchronizedList(em.createNamedQuery("MiejsceKosztow.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
    }
    
    public List<MiejsceKosztow> findMiejscaPodatnikWszystkie(Podatnik podatnik) {
        return Collections.synchronizedList(em.createNamedQuery("MiejsceKosztow.findByPodatnikWszystkie").setParameter("podatnik", podatnik).getResultList());
    }
    
    public List<MiejsceKosztow> findMiejscaPrzychodowPodatnikRok(Podatnik podatnik, int rok) {
        return Collections.synchronizedList(em.createNamedQuery("MiejscePrzychodow.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }
    
    public List<MiejsceKosztow> findMiejscaPrzychodowPodatnik(Podatnik podatnik) {
        return Collections.synchronizedList(em.createNamedQuery("MiejscePrzychodow.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
    }
    
    public List<MiejsceKosztow> findMiejscaPrzychodowPodatnikWszystkie(Podatnik podatnik) {
        return Collections.synchronizedList(em.createNamedQuery("MiejscePrzychodow.findByPodatnikWszystkie").setParameter("podatnik", podatnik).getResultList());
    }


    public List<Pojazdy> findPojazdyPodatnik(Podatnik podatnik) {
        return Collections.synchronizedList(em.createNamedQuery("Pojazdy.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
    }

    public long countPojazdy(Podatnik podatnikObiekt) {
        return (long) em.createNamedQuery("Pojazdy.countByPodatnik").setParameter("podatnik", podatnikObiekt).getSingleResult();
    }

    public List<Delegacja> findDelegacjaPodatnik(WpisView wpisView, boolean krajowa0zagraniczna1) {
        return Collections.synchronizedList(em.createNamedQuery("Delegacja.findByPodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("krajowa0zagraniczna1", krajowa0zagraniczna1).getResultList());
    }

    public long countDelegacja(WpisView wpisView, boolean krajowa0zagraniczna1) {
        return (long) em.createNamedQuery("Delegacja.countByPodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("krajowa0zagraniczna1", krajowa0zagraniczna1).getSingleResult();
    }

    public List<Uz> findByUprawnienia(String uprawnienia) {
        return Collections.synchronizedList(em.createNamedQuery("Uz.findByUprawnienia").setParameter("uprawnienia", uprawnienia).getResultList());
    }

    public List<MultiuserSettings> findMutliuserSettingsByUz(Uz user) {
        return Collections.synchronizedList(em.createNamedQuery("MultiuserSettings.findByUser").setParameter("user", user).getResultList());
    }

    public FakturaXXLKolumna findXXLByPodatnik(Podatnik p) {
        try {
            return (FakturaXXLKolumna) em.createNamedQuery("FakturaXXLKolumna.findByPodatnik").setParameter("podatnik", p).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    public int findMaxLevelPodatnik(Podatnik podatnik, int rokWpisu) {
        return (int) em.createNamedQuery("Konto.findByMaxLevelPodatnik").setParameter("podatnik", podatnik).setParameter("rok", rokWpisu).getSingleResult();
    }

    public List<UkladBR> findUkladBRPodatnik(Podatnik podatnik) {
        return Collections.synchronizedList(em.createNamedQuery("UkladBR.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
    }
    
    public List<UkladBR> findUkladBRPodatnikRok(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("UkladBR.findByPodatnikRok").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList());
    }
    
    public List<UkladBR> findUkladBRPodatnikRok(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("UkladBR.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }
    
    public List<UkladBR> findUkladBRRok(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("UkladBR.findByRokNieWzor").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<UkladBR> findRokUkladnazwa(String rok, String ukladnazwa) {
        return em.createNamedQuery("UkladBR.findByRokNazwa").setParameter("ukladnazwa", ukladnazwa).setParameter("rok", rok).getResultList();
    }
    
    public void findRemoveRzisuklad(String uklad, String podatnik, String rok) {
        em.createNamedQuery("PozycjaRZiS.Delete").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).executeUpdate();
    }

    public void findRemoveBilansuklad(String uklad, String podatnik, String rok) {
        em.createNamedQuery("PozycjaBilans.Delete").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).executeUpdate();
    }

    public Integer findMaxLevelRzisuklad(String uklad, String podatnik, String rok) {
        return (Integer) em.createNamedQuery("PozycjaRZiS.findByMaxLevelPodatnik").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getSingleResult();
    }

    public Integer findMaxLevelBilansukladAktywa(String uklad, String podatnik, String rok) {
        return (Integer) em.createNamedQuery("PozycjaBilans.findByMaxLevelPodatnikAktywa").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getSingleResult();
    }

    public Integer findMaxLevelBilansukladPasywa(String uklad, String podatnik, String rok) {
        return (Integer) em.createNamedQuery("PozycjaBilans.findByMaxLevelPodatnikPasywa").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getSingleResult();
    }

    public PozycjaRZiS findPozycjaRZiSLP(int lp) {
        return (PozycjaRZiS) em.createNamedQuery("PozycjaRZiS.findByLp").setParameter("lp", lp).getSingleResult();
    }

    public PozycjaBilans findPozycjaBilansLP(int lp) {
        return (PozycjaBilans) em.createNamedQuery("PozycjaBilans.findByLp").setParameter("lp", lp).getSingleResult();
    }

    public List<Konto> findKontazLevelu(Podatnik podatnik, Integer rok, int i) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByLevelPodatnik").setParameter("level", i).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

//    public List<Konto> findKontazLeveluWzorcowy(WpisView wpisView, int i) {
//        return Collections.synchronizedList(em.createNamedQuery("Konto.findByLevelWzorcowy").setParameter("level", i).setParameter("rok", wpisView.getRokWpisu()).getResultList());
//    }

    public List<Konto> findKontazLeveluRok(WpisView wpisView, int i) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByLevelRok").setParameter("level", i).setParameter("rok", 2015).getResultList());
    }

    public List<Konto> findSlownikoweKlienci(WpisView wpisView, Kliencifk kliencifk) {
        return Collections.synchronizedList(em.createNamedQuery("Konto.findByPodatnikKliencifk").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("nazwa", kliencifk.getNazwa()).setParameter("nip", kliencifk.getNip()).getResultList());
    }

    public WynikFKRokMc findWynikFKRokMc(WynikFKRokMc wynikFKRokMc) {
        return (WynikFKRokMc) em.createNamedQuery("WynikFKRokMc.findPodatnikRokMc").setParameter("podatnik", wynikFKRokMc.getPodatnikObj()).setParameter("rok", wynikFKRokMc.getRok()).setParameter("mc", wynikFKRokMc.getMc()).getSingleResult();
    }

    public WynikFKRokMc findWynikFKRokMcFirma(WynikFKRokMc wynikFKRokMc) {
        return (WynikFKRokMc) em.createNamedQuery("WynikFKRokMc.findPodatnikRokMcFirma").setParameter("podatnik", wynikFKRokMc.getPodatnikObj()).setParameter("rok", wynikFKRokMc.getRok()).setParameter("mc", wynikFKRokMc.getMc()).getSingleResult();
    }

    public List<WynikFKRokMc> findWynikFKPodatnikRok(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("WynikFKRokMc.findPodatnikRok").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList());
    }
    
    public List<WynikFKRokMc> findWynikFKPodatnikRokFirma(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("WynikFKRokMc.findPodatnikRokFirma").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList());
    }

    public List<WynikFKRokMc> findWynikFKPodatnikRokUdzialowiec(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("WynikFKRokMc.findPodatnikRokUdzialowiec").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList());
    }

    public Klienci findKlientById(int i) {
        return (Klienci) em.createNamedQuery("Klienci.findById").setParameter("id", i).getSingleResult();
    }

    public List<EVatwpisFK> findEVatwpisFKByPodatnik(Podatnik podatnik) {
        return Collections.synchronizedList(em.createNamedQuery("EVatwpisFK.findByPodatnik").setParameter("podatnik", podatnik).setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE).setHint(QueryHints.READ_ONLY, HintValues.TRUE).getResultList());
    }

    public List<EVatwpisFK> findEVatwpisFKByPodatnikRok(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("EVatwpisFK.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE).setHint(QueryHints.READ_ONLY, HintValues.TRUE).getResultList());
    }

    public List<EVatwpisFK> findEVatwpisFKByPodatnikRokInnyOkres(Podatnik podatnik, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("EVatwpisFK.findByPodatnikRokInnyOkres").setParameter("podatnik", podatnik).setParameter("rok", rok).setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE).setHint(QueryHints.READ_ONLY, HintValues.TRUE).getResultList());
    }

    public UkladBR findUkladBRUklad(UkladBR ukladBR) {
        return (UkladBR) em.createNamedQuery("UkladBR.findByUkladPodRok").setParameter("uklad", ukladBR.getUklad()).setParameter("podatnik", ukladBR.getPodatnik()).setParameter("rok", ukladBR.getRok()).getSingleResult();
    }

    public void usunZapisaneKontoPozycjaPodatnikUklad(UkladBR uklad, String rb) {
        if (rb.equals("wynikowe")) {
            em.createNamedQuery("KontopozycjaZapis.DeleteWynikowe").setParameter("uklad", uklad).executeUpdate();
        } else {
            em.createNamedQuery("KontopozycjaZapis.DeleteBilansowe").setParameter("uklad", uklad).executeUpdate();
        }
    }
    
    public void usunZapisaneKontoPozycjaPodatnikUkladByKonto(UkladBR uklad, Konto konto) {
        em.createNamedQuery("KontopozycjaZapis.DeleteByKonto").setParameter("uklad", uklad).setParameter("konto", konto).executeUpdate();
     }

    public void usunZapisaneKontoPozycjaBiezacaPodatnikUklad(UkladBR uklad, String rb) {
        if (rb.equals("wynikowe")) {
            em.createNamedQuery("KontopozycjaBiezaca.DeleteWynikowe").setParameter("uklad", uklad).executeUpdate();
        } else {
            em.createNamedQuery("KontopozycjaBiezaca.DeleteBilansowe").setParameter("uklad", uklad).executeUpdate();
        }
    }
    
    public void usunZapisaneKontoPozycjaBiezacaPodatnikUkladByKonto(UkladBR uklad, Konto konto) {
        em.createNamedQuery("KontopozycjaBiezaca.DeleteByKonto").setParameter("uklad", uklad).setParameter("konto", konto).executeUpdate();
    }


    public void wyzerujBoWnBoMawKontach(WpisView wpisView, String bilansowewynikowe) {
        em.createNamedQuery("Konto.wyzerujBoWnwKontach").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("bilansowewynikowe", bilansowewynikowe).executeUpdate();
        em.createNamedQuery("Konto.wyzerujBoMawKontach").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("bilansowewynikowe", bilansowewynikowe).executeUpdate();
    }
    public void wyzerujBoWnBoMawKontach(WpisView wpisView) {
        em.createNamedQuery("Konto.wyzerujBowKontach").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).executeUpdate();
    }
    
//    public void wyzerujPozycjeWKontachWzorcowy(UkladBR uklad, String bilansowewynikowe) {
//        em.createNamedQuery("Konto.NullPozycjaBilansoweWynikowe").setParameter("podatnik", null).setParameter("rok", Integer.parseInt(uklad.getRok())).setParameter("bilansowewynikowe", bilansowewynikowe).executeUpdate();
//    }
//    public void zerujkontazLevelu(WpisView wpisView, int i) {
//        em.createNamedQuery("Konto.NullObrotyWnLevel").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("level", i).executeUpdate();
//        em.createNamedQuery("Konto.NullObrotyMaLevel").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("level", i).executeUpdate();
//    }
    
    public Konto findByKontoSlownikoweMacierzyste(Konto konto, String nrkonta, WpisView wpisView) {
        return (Konto) em.createNamedQuery("Konto.findBySlownikoweMacierzyste").setParameter("kontomacierzyste", konto).setParameter("nrkonta", nrkonta).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getSingleResult();
    }

    public void wyzerujSaldaZaksiegowanewKontach(WpisView wpisView) {
        em.createNamedQuery("Konto.wyzerujSaldaZaksiegowanewKontach").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).executeUpdate();
    }
    
    
//    public List<UkladBR> findUkladBRWzorcowyRok(String rokWpisu) {
//        return Collections.synchronizedList(em.createNamedQuery("UkladBR.findByWzorcowyRok").setParameter("rok", rokWpisu).getResultList());
//    }

    public List<UkladBR> findukladBRPodatnikRok(Podatnik podatnikWpisu, String rokWpisuSt) {
        return Collections.synchronizedList(em.createNamedQuery("UkladBR.findByPodatnikRok").setParameter("podatnik", podatnikWpisu).setParameter("rok", rokWpisuSt).getResultList());
    }
    
    public UkladBR findukladBRPodatnikRokPodstawowy(Podatnik podatnikWpisu, String rokWpisuSt) {
        return (UkladBR) em.createNamedQuery("UkladBR.findByPodatnikRokPodstawowy").setParameter("podatnik", podatnikWpisu).setParameter("rok", rokWpisuSt).getSingleResult();
    }
    
    public UkladBR findukladBRPodatnikRokAktywny(Podatnik podatnikWpisu, String rokWpisuSt) {
        return (UkladBR) em.createNamedQuery("UkladBR.findByPodatnikRokAktywny").setParameter("podatnik", podatnikWpisu).setParameter("rok", rokWpisuSt).getSingleResult();
    }

    public KontopozycjaZapis fintKontoPozycjaZapisByKonto(Konto konto, UkladBR ukladBR) {
        return (KontopozycjaZapis) em.createNamedQuery("KontopozycjaZapis.findByKontoId").setParameter("kontoId", konto).setParameter("ukladBR", ukladBR).getSingleResult();
    }

    public Delegacja findDelegacja(Delegacja delegacja) {
        return (Delegacja) em.createNamedQuery("Delegacja.findById").setParameter("id", delegacja.getId()).getSingleResult();
    }

    public Delegacja findDelegacjaByNr(String nrdelegacji) {
        return (Delegacja) em.createNamedQuery("Delegacja.findByOpisdlugiOnly").setParameter("opisdlugi", nrdelegacji).getSingleResult();
    }

    public List<PodatnikUdzialy> findUdzialyPodatnik(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("PodatnikUdzialy.findBypodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList());
    }

    public List<PodatnikOpodatkowanieD> findOpodatkowaniePodatnik(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("PodatnikOpodatkowanieD.findBypodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList());
    }

    public PodatnikOpodatkowanieD findOpodatkowaniePodatnikRok(WpisView wpisView) {
        return (PodatnikOpodatkowanieD) em.createNamedQuery("PodatnikOpodatkowanieD.findBypodatnikRok").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getSingleResult();
    }
    
    public PodatnikOpodatkowanieD findOpodatkowaniePodatnikRok(Podatnik p, String rok) {
        return (PodatnikOpodatkowanieD) em.createNamedQuery("PodatnikOpodatkowanieD.findBypodatnikRok").setParameter("podatnik", p).setParameter("rok", rok).getSingleResult();
    }
    
    public PodatnikOpodatkowanieD findOpodatkowaniePodatnikRokPoprzedni(WpisView wpisView) {
        PodatnikOpodatkowanieD zwrot = null;
        try {
            zwrot = (PodatnikOpodatkowanieD) em.createNamedQuery("PodatnikOpodatkowanieD.findBypodatnikRok").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokUprzedniSt()).getSingleResult();
        } catch (Exception e) {
        }
        return zwrot;
    }

    public List<String> znajdzDokumentPodatnikWpr(String wpr) {
        return Collections.synchronizedList(em.createNamedQuery("Dok.znajdzDokumentPodatnikWpr").setParameter("wprowadzil", wpr).getResultList());
    }

    public List<String> znajdzDokumentPodatnikWprFK(String wpr) {
        return Collections.synchronizedList(em.createNamedQuery("Dokfk.znajdzDokumentPodatnikWpr").setParameter("wprowadzil", wpr).getResultList());
    }

    public List<String> findUzByUprawnienia(String uprawnienia) {
        return Collections.synchronizedList(em.createNamedQuery("Uz.findByUzUprawnienia").setParameter("uprawnienia", uprawnienia).getResultList());
    }

    public List<String> findZnajdzSeriePodatnik(WpisView wpisView) {
        return Collections.synchronizedList((List<String>) em.createNamedQuery("Dokfk.znajdzSeriePodatnik").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList());
    }

    public List<SchemaEwidencja> findEwidencjeSchemy(DeklaracjaVatSchema wybranaschema) {
        return Collections.synchronizedList((List<SchemaEwidencja>) em.createNamedQuery("SchemaEwidencja.findEwidencjeSchemy").setParameter("deklarachaVatSchema", wybranaschema).getResultList());
    }

    public List<DeklaracjaVatSchemaWierszSum> findWierszSumSchemy(DeklaracjaVatSchema wybranaschema) {
        return Collections.synchronizedList((List<DeklaracjaVatSchemaWierszSum>) em.createNamedQuery("DeklaracjaVatSchemaWierszSum.findEwidencjeSchemy").setParameter("deklarachaVatSchema", wybranaschema).getResultList());
    }

    public DeklaracjaVatWierszSumaryczny findWierszSumaryczny(String razem_suma_przychodów) {
        return (DeklaracjaVatWierszSumaryczny) em.createNamedQuery("DeklaracjaVatWierszSumaryczny.findWiersz").setParameter("nazwapozycji", razem_suma_przychodów).getSingleResult();
    }

    public WynikFKRokMc findWynikFKRokMcUdzialowiec(WynikFKRokMc wynikFKRokMc) {
        return (WynikFKRokMc) em.createNamedQuery("WynikFKRokMc.findPodatnikRokMcUdzialowiec").setParameter("podatnik", wynikFKRokMc.getPodatnikObj()).setParameter("rok", wynikFKRokMc.getRok()).setParameter("mc", wynikFKRokMc.getMc()).setParameter("udzialowiec", wynikFKRokMc.getUdzialowiec()).getSingleResult();
    }
    
    public WynikFKRokMc findWynikFKRokMcUdzialowiec(Podatnik podatnik, String rok, String mc, String udzialowiec) {
        return (WynikFKRokMc) em.createNamedQuery("WynikFKRokMc.findPodatnikRokMcUdzialowiec").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("mc", mc).setParameter("udzialowiec", udzialowiec).getSingleResult();
    }

    public List<Zusstawki> findZUS(boolean duzy0maly1) {
        return Collections.synchronizedList(em.createNamedQuery("Zusstawki.findZUS").setParameter("duzy0maly1", duzy0maly1).getResultList());
    }

    public Collection<? extends Klienci> findKontrahentFaktury(Podatnik podatnikObiekt) {
        return Collections.synchronizedList(em.createNamedQuery("Faktura.findByKonrahentPodatnik").setParameter("podatnik", podatnikObiekt).getResultList());
    }

    public List<FakturaRozrachunki> rozrachunkiZDnia(Date d, WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("FakturaRozrachunki.findByData_k").setParameter("data", d, TemporalType.DATE).setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList());
    }

    public List<FakturaRozrachunki> findByPodatnik(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("FakturaRozrachunki.findByPodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList());
    }
    
    public List<FakturaRozrachunki> findByPodatnikRokMc(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("FakturaRozrachunki.findByPodatnikRokMc").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList());
    }

    public List<FakturaRozrachunki> findByPodatnikKontrahent(WpisView wpisView, Klienci kontrahent) {
        return Collections.synchronizedList(em.createNamedQuery("FakturaRozrachunki.findByPodatnikKontrahent").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("kontrahent", kontrahent).getResultList());
    }

    public List<FakturaRozrachunki> findByPodatnikKontrahentRok(WpisView wpisView, Klienci kontrahent) {
        return Collections.synchronizedList(em.createNamedQuery("FakturaRozrachunki.findByPodatnikKontrahentRok").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("kontrahent", kontrahent).getResultList());
    }
    public int deleteWierszBOPodatnikRok(Podatnik podatnik, String rok) {
        return em.createNamedQuery("WierszBO.findByDeletePodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).executeUpdate();
    }
    
    public int deleteWierszBOPodatnikRokMc(Podatnik podatnik, String rok, String mc) {
        return em.createNamedQuery("WierszBO.findByDeletePodatnikRokMc").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("mc", mc).executeUpdate();
    }

    public void statystykaUsunrok(String rok) {
        em.createNamedQuery("Statystyka.findUsunRok").setParameter("rok", rok).executeUpdate();
    }

    
    public void usunnaliczeniemc(WpisView wpisView, String kategoria) {
         em.createNamedQuery("StowNaliczenie.DeleteNaliczoneMcRok").setParameter("podatnikObj",wpisView.getPodatnikObiekt()).setParameter("rok",wpisView.getRokWpisuSt()).setParameter("mc",wpisView.getMiesiacWpisu()).setParameter("kategoria",kategoria).executeUpdate();
    }

    public Konto findKontoMacierzystyNrkonta(Podatnik podatnik, Integer rok, Konto kontomacierzyste, String numerkonta) {
        return (Konto) em.createNamedQuery("Konto.findKontoMacierzystyNrkonta").setParameter("kontomacierzyste", kontomacierzyste).setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("nrkonta", numerkonta).getSingleResult();
    }

    public void usunAmoDokByMcRok(String podatnik, int rok, int mc) {
        em.createNamedQuery("Amodok.usunAmoDokByMcRok").setParameter("podatnik",podatnik).setParameter("rok", rok).setParameter("mc", mc).executeUpdate();
    }

    public List<Deklaracjevat> findDeklaracjeByPodatnik(String podatnikWpisu) {
        return Collections.synchronizedList(em.createNamedQuery("Deklaracjevat.findByPodatnik").setParameter("podatnik", podatnikWpisu).getResultList());
    }

    public List<PlatnoscWaluta> findPlatnoscWalutaByDok(Dok selected) {
        return Collections.synchronizedList(em.createNamedQuery("PlatnoscWaluta.findByDok").setParameter("dokument", selected).getResultList());
    }

    public List<ZamkniecieRokuRozliczenie> findZakmniecieRokuByRokPodatnik(Podatnik podatnikObiekt, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("ZamkniecieRokuRozliczenie.findByRokPodatnik").setParameter("podatnik", podatnikObiekt).setParameter("rok", rok).getResultList());
    }

    public List<ZamkniecieRokuEtap> findZakmniecieRokuEtapByRok(String rok) {
        return Collections.synchronizedList(em.createNamedQuery("ZamkniecieRokuEtap.findByRok").setParameter("rok", rok).getResultList());
    }

    public List<PlatnoscWaluta> findPlatnoscWalutaByPodRokMc(Podatnik podatnikObiekt, String rokWpisuSt, String miesiacWpisu) {
        return Collections.synchronizedList(em.createNamedQuery("PlatnoscWaluta.findByPodRokMc").setParameter("podatnik", podatnikObiekt.getNazwapelna()).setParameter("rok", rokWpisuSt).setParameter("mc", miesiacWpisu).getResultList());
    }

    public List<Sprawa> findSprawaByOdbiorca(Uz odbiorca) {
        return Collections.synchronizedList(em.createNamedQuery("Sprawa.findByOdbiorca").setParameter("odbiorca", odbiorca).getResultList());
    }

    public List<Sprawa> findSprawaByNadawca(Uz nadawca) {
        return Collections.synchronizedList(em.createNamedQuery("Sprawa.findByNadawca").setParameter("nadawca", nadawca).getResultList());
    }

    public FakturaStopkaNiemiecka findStopkaNiemieckaByPodatnik(Podatnik podatnikObiekt) {
        return (FakturaStopkaNiemiecka) em.createNamedQuery("FakturaStopkaNiemiecka.findByPodatnik").setParameter("podatnik", podatnikObiekt).getSingleResult();
    }

    public SMTPSettings findSMTPSettingsByUzytkownik(Uz uzytkownik) {
        return (SMTPSettings) em.createNamedQuery("SMTPSettings.findByUzytkownik").setParameter("uzytkownik", uzytkownik).getSingleResult();
    }

    public SMTPSettings findSMTPSettingsByDef() {
        return (SMTPSettings) em.createNamedQuery("SMTPSettings.findByDef").getSingleResult();
    }

    public List<UmorzenieN> findUmorzenieBySrodek(SrodekTrw str) {
        return Collections.synchronizedList(em.createNamedQuery("UmorzenieN.findStr").setParameter("srodekTrw", str).getResultList());
    }

    public void ukladBRustawnieaktywne(Podatnik podatnik) {
         em.createNamedQuery("UkladBR.ustawNieaktywne").setParameter("podatnik", podatnik).executeUpdate();
    }

    public List<SprawozdanieFinansowe> findSprawozdanieRokPodatnik(WpisView wpisView, String rok) {
        return Collections.synchronizedList(em.createNamedQuery("SprawozdanieFinansowe.findByPodatnikRok").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", rok).getResultList());
    }
    
    public List<SprawozdanieFinansowe> findSprawozdanieRok(String rok) {
        return Collections.synchronizedList(em.createNamedQuery("SprawozdanieFinansowe.findByRok").setParameter("rok", rok).getResultList());
    }

    public List<Cechazapisu> findCechaZapisuByPodatnikOnly(Podatnik podatnikObiekt) {
        return Collections.synchronizedList(em.createNamedQuery("Cechazapisu.findByPodatnikOnly").setParameter("podatnik", podatnikObiekt).getResultList());
    }
    
    public List<Cechazapisu> findCechaZapisuByPodatnikOnlyStatystyczne(Podatnik podatnikObiekt) {
        return Collections.synchronizedList(em.createNamedQuery("Cechazapisu.findByPodatnikOnlyStatystyczne").setParameter("podatnik", podatnikObiekt).getResultList());
    }
    
    public List<Cechazapisu> findCechaZapisuByPodatnik(Podatnik podatnikObiekt) {
        return Collections.synchronizedList(em.createNamedQuery("Cechazapisu.findByPodatnik").setParameter("podatnik", podatnikObiekt).getResultList());
    }
    
    public Cechazapisu findCechaZapisuByPodatnikNKUP() {
        return (Cechazapisu) em.createNamedQuery("Cechazapisu.findByPodatnikNKUP").setParameter("nazwacechy", "NKUP").getSingleResult();
    }

    public List<Fakturywystokresowe> findOkresoweOstatnieByfaktura(Faktura p) {
        return Collections.synchronizedList(em.createNamedQuery("Fakturywystokresowe.findByFaktura").setParameter("faktura", p).getResultList());
    }

    public void usunlogoplik(Podatnik podatnikObiekt) {
        em.createNamedQuery("Logofaktura.usunlogo").setParameter("podatnik", podatnikObiekt).executeUpdate();
    }

    public Logofaktura findLogoByPodatnik(Podatnik podatnikObiekt) {
        return (Logofaktura) em.createNamedQuery("Logofaktura.findByPodatnik").setParameter("podatnik", podatnikObiekt).getSingleResult();
    }

    public List<DeklaracjavatUE> findDeklUEbyPodatnikRok(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("DeklaracjavatUE.findByPodatnikRok").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList());
    }

    public List<DeklaracjavatUE> findDeklUEbyPodatnikRokMc(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("DeklaracjavatUE.findByPodatnikRokMc").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("miesiac", wpisView.getMiesiacWpisu()).getResultList());
    }

    public void usundeklaracjeUE(WpisView wpisView) {
        em.createNamedQuery("DeklaracjavatUE.usundeklaracjeUE").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("miesiac", wpisView.getMiesiacWpisu()).executeUpdate();
        em.flush();
    }
    public List<Deklaracjavat27> findDekl27byPodatnikRok(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Deklaracjavat27.findByPodatnikRok").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList());
    }

    public List<Deklaracjavat27> findDekl27byPodatnikRokMc(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("Deklaracjavat27.findByPodatnikRokMc").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("miesiac", wpisView.getMiesiacWpisu()).getResultList());
    }

    public void usundeklaracje27(WpisView wpisView) {
        em.createNamedQuery("Deklaracjavat27.usundeklaracje27").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("miesiac", wpisView.getMiesiacWpisu()).executeUpdate();
        em.flush();
    }

    public List<DeklaracjavatUE> findDeklaracjeUEwysylka(String rokWpisuSt, String miesiacWpisu) {
        return Collections.synchronizedList(em.createNamedQuery("DeklaracjavatUE.findByRokMc").setParameter("rok", rokWpisuSt).setParameter("miesiac", miesiacWpisu).getResultList());
    }

    public List<Deklaracjavat27> findDeklaracje27wysylka(String rokWpisuSt, String miesiacWpisu) {
        return Collections.synchronizedList(em.createNamedQuery("Deklaracjavat27.findByRokMc").setParameter("rok", rokWpisuSt).setParameter("miesiac", miesiacWpisu).getResultList());
    }

    public void usunSumyPKPiR(String podatnik, String rok, String mc) {
        em.createNamedQuery("Sumypkpir.deleteByPodatnikRokMc").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("mc", mc).executeUpdate();
    }

    public List<UPO> findUPOPodatnikRok(Podatnik podatnikObiekt, String rokWpisuSt) {
        return Collections.synchronizedList(em.createNamedQuery("UPO.findUPOPodatnikRok").setParameter("rok", rokWpisuSt).setParameter("podatnik", podatnikObiekt).getResultList());
    }
    
    public List<UPO> findUPORokMc(String rokWpisuSt, String miesiacWpisu) {
        return Collections.synchronizedList(em.createNamedQuery("UPO.findUPORokMc").setParameter("rok", rokWpisuSt).setParameter("mc", miesiacWpisu).getResultList());
    }

    public List<EVatwpis1> zwrocEVatwpis1KlientRokMc(Podatnik podatnikWpisu, String rokWpisuSt, String miesiacWpisu) {
        return Collections.synchronizedList(em.createNamedQuery("EVatwpis1.findByRokMc").setParameter("podatnik", podatnikWpisu).setParameter("pkpirR", rokWpisuSt).setParameter("mc", miesiacWpisu).getResultList());
    }

    public List<EVatwpis1> zwrocEVatwpis1KlientRokKw(Podatnik podatnikWpisu, String rokWpisuSt, List<String> mce) {
        return Collections.synchronizedList(em.createNamedQuery("EVatwpis1.findByRokKW").setParameter("podatnik", podatnikWpisu).setParameter("pkpirR", rokWpisuSt).setParameter("mc1", mce.get(0)).setParameter("mc2", mce.get(1)).setParameter("mc3", mce.get(2)).getResultList());
    }
    
    public List<EVatwpis1> zwrocEVatwpisFKKlientRokMc(Podatnik podatnikWpisu, String rokWpisuSt, String miesiacWpisu) {
        return Collections.synchronizedList(em.createNamedQuery("EVatwpisFK.findByRokMc").setParameter("podatnik", podatnikWpisu).setParameter("pkpirR", rokWpisuSt).setParameter("mc", miesiacWpisu)
                .getResultList());
    }

    public List<EVatwpis1> zwrocEVatwpisFKKlientRokKw(Podatnik podatnikWpisu, String rokWpisuSt, List<String> mce) {
        return Collections.synchronizedList(em.createNamedQuery("EVatwpisFK.findByRokKW").setParameter("podatnik", podatnikWpisu).setParameter("pkpirR", rokWpisuSt).setParameter("mc1", mce.get(0)).setParameter("mc2", mce.get(1)).setParameter("mc3", mce.get(2)).getResultList());
    }

    public List<Sesja> findSesjaZalogowani() {
        return Collections.synchronizedList(em.createNamedQuery("Sesja.findByZalogowani").getResultList());
    }

    public String findEVatwpisFKPodatnikKlient(Podatnik podatnikObiekt, Klienci klient, String rok) {
        return ((EVatwpisFK) em.createNamedQuery("EVatwpisFK.findEVatwpisFKPodatnikKlient").setParameter("podatnik", podatnikObiekt).setParameter("klient", klient).setParameter("rok", rok).setMaxResults(1).getSingleResult()).getOpisvat();
    }

    public List<KontopozycjaZapis> findByKontoOnly(Konto konto) {
        return Collections.synchronizedList(em.createNamedQuery("KontopozycjaZapis.findByKontoOnly").setParameter("konto", konto).getResultList());
                
    }

    public List<VATZDDAO> findVATPozycjaByPodatnikRokMcFK(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("VATZDpozycja.findByPodatnikRokMcFK").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList());
    }

    public List<WniosekVATZDEntity> findWniosekZDByPodatnikRokMcFK(WpisView wpisView) {
        return Collections.synchronizedList(em.createNamedQuery("WniosekVATZDEntity.findByPodatnikRokMcFK").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList());
    }

    public List<UPO> findUPOBez200() {
         return Collections.synchronizedList(em.createNamedQuery("UPO.findUPOBez200").getResultList());
    }

    public List<WniosekVATZDEntity> findWniosekZDByPodatnikRokMcFK(Podatnik podatnik, String rok, String mc) {
        return Collections.synchronizedList(em.createNamedQuery("WniosekVATZDEntity.findByPodatnikRokMcFK").setParameter("rok", rok).setParameter("mc", mc).setParameter("podatnik", podatnik).getResultList());
    }

    public void ukladBRustawnieaktywneUkladBR() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<UkladBR> findWszystkieUkladBR() {
        return Collections.synchronizedList(em.createNamedQuery("UkladBR.findAll").setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
    }

    public List<KontopozycjaBiezaca> findKontaPozycjaBiezacaPodatnikRok(Podatnik podatnik, String rok) {
        return em.createNamedQuery("KontopozycjaBiezaca.findByPodatnikRok").setParameter("rok", rok).setParameter("podatnik", podatnik).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
    }

    public List<KontopozycjaZapis> findKontaPozycjaZapisPodatnikRok(Podatnik podatnik, String rok) {
        return em.createNamedQuery("KontopozycjaZapis.findByPodatnikRok").setParameter("rok", rok).setParameter("podatnik", podatnik).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
    }
    
    public List<KontopozycjaZapis> findKontoPozycjaByRokUkladBilans(String rokWpisuSt, String uklad) {
        return em.createNamedQuery("KontopozycjaZapis.findKontoPozycjaByRokUkladBilans").setParameter("rok", rokWpisuSt).setParameter("uklad", uklad).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
    }
    public List<KontopozycjaZapis> findKontoPozycjaByRokUkladRZiS(String rokWpisuSt, String uklad) {
        return em.createNamedQuery("KontopozycjaZapis.findKontoPozycjaByRokUkladRZiS").setParameter("rok", rokWpisuSt).setParameter("uklad", uklad).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
    }


    public Dokfk findDokfId(Dokfk wybranyDokfk) {
        try {
            LoadGroup lg = new LoadGroup();
            lg.addAttribute("listawierszy");
            lg.addAttribute("listawierszy.strona.nowetransakcje");
            lg.addAttribute("listawierszy.strona.platnosci"); 
            return (Dokfk) em.createNamedQuery("Dokfk.findById")
                    .setParameter("id", wybranyDokfk.getId())
                    .setHint(QueryHints.REFRESH, HintValues.TRUE)
                    .setHint(QueryHints.LOAD_GROUP, lg)
                    .getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Wiersz> findWierszeRok(String rok) {
        return em.createNamedQuery("Wiersz.findByRok").setParameter("rok", rok).getResultList();
    }

    public SprFinKwotyInfDod findsprfinkwoty(Podatnik podatnikObiekt, String rokWpisuSt) {
        return (SprFinKwotyInfDod) em.createNamedQuery("SprFinKwotyInfDod.findsprfinkwoty").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).getSingleResult();
    }

    
    

    
    
    
    

    

    

    

    

    
}
