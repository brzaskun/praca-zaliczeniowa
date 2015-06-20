/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import embeddable.Mce;
import entity.Amodok;
import entity.Deklaracjevat;
import entity.Dok;
import entity.EVatOpis;
import entity.Evewidencja;
import entity.Evpozycja;
import entity.Ewidencjevat;
import entity.Faktura;
import entity.FakturaXXLKolumna;
import entity.Fakturadodelementy;
import entity.Fakturaelementygraficzne;
import entity.Fakturyokresowe;
import entity.Fakturywystokresowe;
import entity.Inwestycje;
import entity.Klienci;
import entity.MultiuserSettings;
import entity.Pismoadmin;
import entity.Pitpoz;
import entity.Platnosci;
import entity.PlatnosciPK;
import entity.Podatnik;
import entity.PodatnikUdzialy;
import entity.Podstawki;
import entity.Pozycjenafakturze;
import entity.Rejestrlogowan;
import entity.Rodzajedok;
import entity.Ryczpoz;
import entity.Sesja;
import entity.SrodekTrw;
import entity.Srodkikst;
import entity.StornoDok;
import entity.Sumypkpir;
import entity.Uz;
import entity.Wpis;
import entity.Zamknietemiesiace;
import entity.Zobowiazanie;
import entity.Zusmail;
import entityfk.Delegacja;
import entityfk.Dokfk;
import entityfk.DokfkPK;
import entityfk.EVatwpisFK;
import entityfk.Kliencifk;
import entityfk.Konto;
import entityfk.KontopozycjaBiezaca;
import entityfk.KontopozycjaZapis;
import entityfk.MiejsceKosztow;
import entityfk.Pojazdy;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.RMK;
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
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import view.WpisView;
import viewfk.UkladBRView;

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
    private Exception EVatwpisFK;

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
    }

    public T findEntity(Class<T> entityClass, T entityPK) {
        T find = getEntityManager().find(entityClass, entityPK);
        return find;
    }

    public void remove(T entity) {
        getEntityManager().remove(em.merge(entity));
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
    }
    
    public void edit(List<T> entityList) {
        for (T p : entityList) {
            getEntityManager().merge(p);
        }
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
        return q.getResultList();
    }

    public Evewidencja findEvewidencjaByName(String nazwa) {
        return (Evewidencja) em.createNamedQuery("Evewidencja.findByNazwa").setParameter("nazwa", nazwa).getSingleResult();
    }

    public Evpozycja findEvpozycjaByName(String nazwapola) {
        return (Evpozycja) em.createNamedQuery("Evpozycja.findByNazwapola").setParameter("nazwapola", nazwapola).getSingleResult();
    }

    public List<Pitpoz> findPitpozAll() {
        List<Pitpoz> lista = em.createNamedQuery("Pitpoz.findAll").getResultList();
        return lista;
    }

    public Pitpoz findPitpoz(String rok, String mc, String pod) {
        Pitpoz tmp = (Pitpoz) em.createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).getSingleResult();
        return tmp;
    }

    public List<Pitpoz> findPitpozLista(String rok, String mc, String pod) {
        List<Pitpoz> lista = em.createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).getResultList();
        return lista;
    }

    public Pitpoz findPitpoz(String rok, String mc, String pod, String udzialowiec) {
        Pitpoz tmp = (Pitpoz) em.createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik AND p.udzialowiec = :udzialowiec").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).setParameter("udzialowiec", udzialowiec).getSingleResult();
        return tmp;
    }

    public List<Pitpoz> findPitpodatnik(String rok, String pod) {
        List<Pitpoz> tmp = em.createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("podatnik", pod).getResultList();
        return tmp;
    }

    public List<Ryczpoz> findRyczAll() {
        List<Ryczpoz> lista = em.createNamedQuery("Ryczpoz.findAll").getResultList();
        return lista;
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
        return tmp;
    }

    public Ewidencjevat findEwidencjeVAT(String rok, String mc, String pod) {
        Ewidencjevat tmp = (Ewidencjevat) em.createQuery("SELECT p FROM  Ewidencjevat p WHERE p.rok = :rok AND p.miesiac = :miesiac AND p.podatnik = :podatnik").setParameter("rok", rok).setParameter("miesiac", mc).setParameter("podatnik", pod).getSingleResult();
        return tmp;
    }

    public Uz findUzNP(String login) {
        Uz tmp = (Uz) em.createNamedQuery("Uz.findByLogin").setParameter("login", login).getSingleResult();
        return tmp;
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
        return tmp;
    }

    public Dok dokumentDuplicat(Dok selD, String pkpirR) throws Exception {
        Dok wynik = null;
        try {
            wynik = (Dok) em.createNamedQuery("Dok.findDuplicate").setParameter("kontr", selD.getKontr()).setParameter("nrWlDk", selD.getNrWlDk()).setParameter("netto", selD.getNetto()).setParameter("pkpirR", pkpirR).getSingleResult();
        } catch (Exception e) {
            E.e(e);
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

    public Dok dokumentDuplicatwtrakcie(Dok selD, String nazwapelna, String typdokumentu) {
        List<Dok> wynik = null;
        try {
            wynik = em.createNamedQuery("Dok.findDuplicatewTrakcie").setParameter("kontr", selD.getKontr()).setParameter("nrWlDk", selD.getNrWlDk()).setParameter("podatnik", nazwapelna).setParameter("typdokumentu", typdokumentu).getResultList();
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

    public Rodzajedok findRodzajedokPodatnik(String skrot, Podatnik podatnik) {
        Rodzajedok wynik = null;
        try {
            wynik = (Rodzajedok) em.createNamedQuery("Rodzajedok.findBySkrotPodatnik").setParameter("skrot", skrot).setParameter("podatnik", podatnik).getSingleResult();
        } catch (Exception e) {
            E.e(e);

        }
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
        return (Deklaracjevat) em.createNamedQuery("Deklaracjevat.findByRokMcPod").setParameter("rok", rok).setParameter("miesiac", mc).setParameter("podatnik", pod).getSingleResult();
    }

    public List<Deklaracjevat> findDeklaracjewszystkie(String rok, String mc, String pod) {
        return em.createNamedQuery("Deklaracjevat.findByRokMcPod").setParameter("rok", rok).setParameter("miesiac", mc).setParameter("podatnik", pod).getResultList();
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
            return em.createNamedQuery("Deklaracjevat.findByPodatnikWysylka").setParameter("podatnik", pod).setParameter("identyfikator", "").getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Deklaracjevat> findDeklaracjewyslane(String pod) {
        return em.createNamedQuery("Deklaracjevat.findByPodatnikWyslane").setParameter("podatnik", pod).setParameter("identyfikator", "").getResultList();
    }

    public Deklaracjevat findDeklaracjaPodatnik(String identyfikator, String podatnik) {
        return (Deklaracjevat) em.createNamedQuery("Deklaracjevat.findByIdentyfikatorPodatnik").setParameter("identyfikator", identyfikator).setParameter("podatnik", podatnik).getSingleResult();
    }

    public List<Deklaracjevat> findDeklaracjewyslane(String pod, String rok) {
        return em.createNamedQuery("Deklaracjevat.findByPodatnikWyslaneRok").setParameter("podatnik", pod).setParameter("identyfikator", "").setParameter("rok", rok).getResultList();
    }

    public List<Deklaracjevat> findDeklaracjewyslane200(String pod, String rok) {
        return em.createNamedQuery("Deklaracjevat.findByPodatnikWyslaneRok200").setParameter("podatnik", pod).setParameter("identyfikator", "").setParameter("rok", rok).getResultList();
    }

    public Srodkikst findSr(Srodkikst srodek) {
        return em.find(Srodkikst.class, srodek);
    }

    public EVatOpis findEVatOpis(String name) {
        return (EVatOpis) em.createNamedQuery("EVatOpis.findByLogin").setParameter("login", name).getSingleResult();
    }

    public List<Dok> findDokBK(String pod, String rok) {
        return em.createNamedQuery("Dok.findByBK").setParameter("podatnik", pod).setParameter("pkpirR", rok).getResultList();
    }
    
    public List<Dok> findDokBKPrzychody(String pod, String rok) {
        return em.createNamedQuery("Dok.findByBKPrzychody").setParameter("podatnik", pod).setParameter("pkpirR", rok).getResultList();
    }

    public List<Dok> findDokRok(String rok) {
        return em.createNamedQuery("Dok.findByPkpirR").setParameter("pkpirR", rok).getResultList();
    }

    public List<Dok> findDokBKVAT(String pod, String rok) {
        return em.createNamedQuery("Dok.findByBKVAT").setParameter("podatnik", pod).setParameter("vatR", rok).getResultList();
    }

    public List<Dok> findDokfkBKVAT(Podatnik pod, String rok) {
        return em.createNamedQuery("Dokfk.findByBKVAT").setParameter("podatnik", pod).setParameter("vatR", rok).getResultList();
    }

    public Dok findDokTPR(String typdokumentu, String pod, String rok) {
        List<Dok> lista = em.createNamedQuery("Dok.findByTPR").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("typdokumentu", typdokumentu).getResultList();
        return lista.get(lista.size() - 1);
    }

    public List<Dok> findDokBK(String pod, String rok, String mc) {
        return em.createNamedQuery("Dok.findByBKM").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("pkpirM", mc).getResultList();
    }

    public Object findDokBKCount(String pod, String rok, String mc) {
        return em.createNamedQuery("Dok.findByPkpirRMCount").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("pkpirM", mc).getSingleResult();
    }

    public List<Dok> findDokDuplikat(String pod, String rok) {
        return em.createNamedQuery("Dok.findByDuplikat").setParameter("podatnik", pod).setParameter("pkpirR", rok).getResultList();
    }

    public List<Sumypkpir> findSumy(String podatnik, String rok) {
        return em.createNamedQuery("Sumypkpir.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
    }

    public Amodok findMR(String pod, Integer rok, String mc) {
        Integer miesiac = Integer.parseInt(mc);
        return (Amodok) em.createNamedQuery("Amodok.findByPMR").setParameter("podatnik", pod).setParameter("rok", rok).setParameter("mc", miesiac).getSingleResult();
    }

    public List<Deklaracjevat> findDeklaracjewysylka(String rok, String mc) {
        return em.createNamedQuery("Deklaracjevat.findByRokMc").setParameter("rok", rok).setParameter("miesiac", mc).getResultList();
    }

    public List<SrodekTrw> findStrPod(String podatnik) {
        try {
            return em.createNamedQuery("SrodekTrw.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Dok findDokMC(String typdokumentu, String podatnik, String rok, String mc) {
        try {
            return (Dok) em.createNamedQuery("Dok.findByRMPT").setParameter("typdokumentu", typdokumentu).setParameter("podatnik", podatnik).setParameter("pkpirR", rok).setParameter("pkpirM", mc).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Inwestycje> findInwestycje(String podatnik, boolean zakonczona) {
        return em.createNamedQuery("Inwestycje.findByPodatnikZakonczona").setParameter("podatnik", podatnik).setParameter("zakonczona", zakonczona).getResultList();
    }

    public List<Amodok> findPod(String podatnik) {
        try {
            return em.createNamedQuery("Amodok.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Amodok> AmoDokPodRok(String podatnik, String rok) {
        try {
            return em.createNamedQuery("Amodok.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList();
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
            return em.createNamedQuery("Amodok.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public Konto findKonto(String numer, String podatnik, Integer rok) {
        return (Konto) em.createNamedQuery("Konto.findByPelnynumerPodatnik").setParameter("pelnynumer", numer).setParameter("podatnik", podatnik).setParameter("rok", rok).getSingleResult();
    }

    public Konto findKontoWzorcowy(String numer, Integer rok) {
        return (Konto) em.createNamedQuery("Konto.findByPelnynumerWzorcowy").setParameter("pelnynumer", numer).setParameter("podatnik", "Wzorcowy").setParameter("rok", rok).getSingleResult();
    }

    public Konto findKontoNazwaPodatnik(String nazwaskrocona, WpisView wpisView) {
        return (Konto) em.createNamedQuery("Konto.findByNazwaPodatnik").setParameter("nazwaskrocona", nazwaskrocona).setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getSingleResult();
    }

    public List<Konto> findKontaNazwaPodatnik(String nazwaskrocona, WpisView wpisView) {
        return em.createNamedQuery("Konto.findByNazwaPodatnik").setParameter("nazwaskrocona", nazwaskrocona).setParameter("podatnik", wpisView.getPodatnikObiekt().getNazwapelna()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public List<Konto> findKontaRozrachunkowe(WpisView wpisView) {
        return em.createNamedQuery("Konto.findByRozrachunkowePodatnik").setParameter("zwyklerozrachszczegolne", "rozrachunkowe").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }
    
    public List<Konto> findKontaRozrachunkoweZpotomkami(WpisView wpisView) {
        return em.createNamedQuery("Konto.findByRozrachunkowePodatnikZpotomkami").setParameter("zwyklerozrachszczegolne", "rozrachunkowe").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public List<Konto> findKontaRozrachunkoweWszystkie(WpisView wpisView) {
        return em.createNamedQuery("Konto.findByRozrachunkiPodatnikWszystkie").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public List<Konto> findKontaVAT(String podatnik, int rok) {
        return em.createNamedQuery("Konto.findByVATPodatnik").setParameter("zwyklerozrachszczegolne", "vat").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<Konto> findKontaSrodkiTrw(String podatnik, int rok) {
        return em.createNamedQuery("Konto.findBySrodkiTrwPodatnik").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<Konto> findKontaRMK(String podatnik, int rok) {
        return em.createNamedQuery("Konto.findByRMKPodatnik").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<Konto> findKontaRZiS(WpisView wpisView) {
        return em.createNamedQuery("Konto.findByBilansowewynikowePodatnik").setParameter("bilansowewynikowe", "wynikowe").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public Konto findKonto(int id) {
        return (Konto) em.createNamedQuery("Konto.findById").setParameter("id", id).getSingleResult();
    }

    public Dokfk findZZapisu(String numer) {
        return (Dokfk) em.createNamedQuery("Dokfk.findByNumer").setParameter("numer", numer).getSingleResult();
    }

    public Dokfk findDokfk(String data, String numer) {
        return (Dokfk) em.createNamedQuery("Dokfk.findByDatawystawieniaNumer").setParameter("datawystawienia", data).setParameter("numer", numer).getSingleResult();
    }

    public List<Fakturyokresowe> findPodatnik(String podatnik) {
        return em.createNamedQuery("Fakturyokresowe.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
    }

    public List<Fakturywystokresowe> findPodatnikFaktury(String podatnik) {
        return em.createNamedQuery("Fakturywystokresowe.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
    }

    public Fakturywystokresowe findFakturaOkresowaById(Integer id) {
        return (Fakturywystokresowe) em.createNamedQuery("Fakturywystokresowe.findById").setParameter("id", id).getSingleResult();
    }

    public List<Fakturywystokresowe> findPodatnikRokFaktury(String podatnik, String rok) {
        return em.createNamedQuery("Fakturywystokresowe.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<Pozycjenafakturze> findFakturyPodatnik(String podatnik) {
        return em.createNamedQuery("Pozycjenafakturze.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
    }

    public List<Evewidencja> findEvewidencjaByTransakcja(String transakcja) {
        return em.createNamedQuery("Evewidencja.findByTransakcja").setParameter("transakcja", transakcja).getResultList();
    }

    public List<Faktura> findByKontrahent_nip(String kontrahent_nip, String wystawca) {
        return em.createNamedQuery("Faktura.findByKontrahent").setParameter("kontrahent_nip", kontrahent_nip).setParameter("wystawcanazwa", wystawca).getResultList();
    }

    public List<Faktura> findByKontrahentNipRok(String kontrahent_nip, String wystawca, String rok) {
        return em.createNamedQuery("Faktura.findByKontrahentRok").setParameter("kontrahent_nip", kontrahent_nip).setParameter("wystawcanazwa", wystawca).setParameter("rok", rok).getResultList();
    }

    public List<Faktura> findFakturyByRok(String rok) {
        return em.createNamedQuery("Faktura.findByRok").setParameter("rok", rok).getResultList();
    }

    public List<Faktura> findFakturyByRokPodatnik(String rok, String wystawcanazwa) {
        return em.createNamedQuery("Faktura.findByRokPodatnik").setParameter("rok", rok).setParameter("wystawcanazwa", wystawcanazwa).getResultList();
    }

    public Faktura findOstatniaFakturaByRokPodatnik(String rok, String wystawcanazwa) {
        return (Faktura) em.createNamedQuery("Faktura.findOstatniaFakturaByRokPodatnik").setParameter("rok", rok).setParameter("wystawcanazwa", wystawcanazwa).setMaxResults(1).getSingleResult();
    }

    public List<Fakturadodelementy> findFaktElementyPodatnik(String podatnik) {
        return em.createNamedQuery("Fakturadodelementy.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
    }

    public List<Faktura> findByPodatnik(String podatnik) {
        return em.createNamedQuery("Faktura.findByWystawcanazwa").setParameter("wystawcanazwa", podatnik).getResultList();
    }

    public Faktura findByNumerPodatnik(String numerkolejny, String podatnik) {
        return (Faktura) em.createNamedQuery("Faktura.findByNumerkolejnyWystawcanazwa").setParameter("wystawcanazwa", podatnik).setParameter("numerkolejny", numerkolejny).getSingleResult();
    }

    public List<Faktura> findByPodatnikRok(String podatnik, String rok) {
        return em.createNamedQuery("Faktura.findByWystawcanazwaRok").setParameter("wystawcanazwa", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<Faktura> findByPodatnikRokMc(String podatnik, String rok, String mc) {
        return em.createNamedQuery("Faktura.findByWystawcanazwaRokMc").setParameter("wystawcanazwa", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList();
    }

    public List<Faktura> findByPodatnikRokMcPlatnosci(String podatnik, String rok, String mc, boolean niezaplacone0zaplacone1) {
        if (niezaplacone0zaplacone1 == false) {
            return em.createNamedQuery("Faktura.findByWystawcanazwaRokMcNiezaplacone").setParameter("wystawcanazwa", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList();
        } else {
            return em.createNamedQuery("Faktura.findByWystawcanazwaRokMcZaplacone").setParameter("wystawcanazwa", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList();
        }
    }

    public Fakturywystokresowe findOkresowa(String rok, String klientnip, String nazwapelna, double brutto) {
        return (Fakturywystokresowe) em.createNamedQuery("Fakturywystokresowe.findByOkresowa").setParameter("rok", rok).setParameter("podatnik", nazwapelna).setParameter("nipodbiorcy", klientnip).setParameter("brutto", brutto).getSingleResult();
    }

    public Dok findFaktWystawione(String nazwapelna, Klienci kontrahent, String numerkolejny, double brutto) {
        return (Dok) em.createNamedQuery("Dok.findByFakturaWystawiona").setParameter("podatnik", nazwapelna).setParameter("kontr", kontrahent).setParameter("nrWlDk", numerkolejny).setParameter("brutto", brutto).getSingleResult();
    }

    public List<Wiersz> findWierszefkRozrachunki(String podatnik, Konto konto, DokfkPK dokfkPK) {
        return em.createNamedQuery("Wiersz.findByRozrachunki").setParameter("podatnik", podatnik).setParameter("konto", konto).setParameter("dokfkPK", dokfkPK).getResultList();
    }

    public List<Wiersz> findWierszeZapisy(String podatnik, String konto) {
        return em.createNamedQuery("Wiersz.findByZapisy").setParameter("podatnik", podatnik).setParameter("konto", konto).getResultList();
    }

    public List<Wiersz> findWierszePodatnikMcRok(Podatnik podatnik, WpisView wpisView) {
        return em.createNamedQuery("Wiersz.findByPodatnikMcRok").setParameter("podatnik", podatnik).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList();
    }

    public List<Wiersz> findWierszePodatnikMcRokWNTWDT(Podatnik podatnik, WpisView wpisView, String wntwdt) {
        return em.createNamedQuery("Wiersz.findByPodatnikMcRokWNTWDT").setParameter("podatnik", podatnik).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).setParameter("wntwdt", wntwdt).getResultList();
    }

    public List<Wiersz> findWierszePodatnikRok(Podatnik podatnik, WpisView wpisView) {
        return em.createNamedQuery("Wiersz.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", wpisView.getRokWpisuSt()).getResultList();
    }

//
//    public List<Wiersze> findWierszefkRozrachunki(String podatnik, String kontonumer) {
//        return em.createNamedQuery("Wiersz.findByRozrachunki1").setParameter("podatnik", podatnik).getResultList();
//    }
    public Dokfk findDokfk(Dokfk selected) {
        try {
            return (Dokfk) em.createNamedQuery("Dokfk.findByDokEdycjaFK")
                    .setParameter("seriadokfk", selected.getDokfkPK().getSeriadokfk())
                    .setParameter("rok", selected.getDokfkPK().getRok())
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
        return em.find(Dokfk.class, selected.getDokfkPK());
    }

    public Dokfk findDokfkKontrahent(Dokfk selected) {
        try {
            return (Dokfk) em.createNamedQuery("Dokfk.findByDuplikatKontrahent")
                    .setParameter("seriadokfk", selected.getDokfkPK().getSeriadokfk())
                    .setParameter("rok", selected.getDokfkPK().getRok())
                    .setParameter("podatnikObj", selected.getPodatnikObj())
                    .setParameter("numerwlasnydokfk", selected.getNumerwlasnydokfk())
                    .setParameter("kontrahent", selected.getKontr())
                    .getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Konto> findKontaOstAlityka(String podatnik, Integer rok) {
        return em.createNamedQuery("Konto.findByMapotomkowMaSlownikPodatnik").setParameter("mapotomkow", false).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<Konto> findKontaOstAlitykaWynikowe(String podatnik, Integer rok) {
        return em.createNamedQuery("Konto.findByMapotomkowMaSlownikPodatnikWynikowe").setParameter("mapotomkow", false).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<Konto> findKontaOstAlityka5(WpisView wpisView) {
        return em.createNamedQuery("Konto.findByMapotomkowMaSlownikPodatnik5").setParameter("mapotomkow", false).setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public List<Fakturywystokresowe> findOkresoweOstatnie(String podatnik, String mc) {
        switch (mc) {
            case "01":
                return em.createNamedQuery("Fakturywystokresowe.findByM1").setParameter("podatnik", podatnik).getResultList();
            case "02":
                return em.createNamedQuery("Fakturywystokresowe.findByM2").setParameter("podatnik", podatnik).getResultList();
            case "03":
                return em.createNamedQuery("Fakturywystokresowe.findByM3").setParameter("podatnik", podatnik).getResultList();
            case "04":
                return em.createNamedQuery("Fakturywystokresowe.findByM4").setParameter("podatnik", podatnik).getResultList();
            case "05":
                return em.createNamedQuery("Fakturywystokresowe.findByM5").setParameter("podatnik", podatnik).getResultList();
            case "06":
                return em.createNamedQuery("Fakturywystokresowe.findByM6").setParameter("podatnik", podatnik).getResultList();
            case "07":
                return em.createNamedQuery("Fakturywystokresowe.findByM7").setParameter("podatnik", podatnik).getResultList();
            case "08":
                return em.createNamedQuery("Fakturywystokresowe.findByM8").setParameter("podatnik", podatnik).getResultList();
            case "09":
                return em.createNamedQuery("Fakturywystokresowe.findByM9").setParameter("podatnik", podatnik).getResultList();
            case "10":
                return em.createNamedQuery("Fakturywystokresowe.findByM10").setParameter("podatnik", podatnik).getResultList();
            case "11":
                return em.createNamedQuery("Fakturywystokresowe.findByM11").setParameter("podatnik", podatnik).getResultList();
            case "12":
                return em.createNamedQuery("Fakturywystokresowe.findByM12").setParameter("podatnik", podatnik).getResultList();
        }
        return null;
    }

    public List<Konto> findKontaPotomnePodatnik(WpisView wpisView, String macierzyste) {
        return em.createNamedQuery("Konto.findByMacierzysteBOPodatnik").setParameter("macierzyste", macierzyste).setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public List<Konto> findKontaPotomneWzorcowy(Integer rok, String macierzyste) {
        return em.createNamedQuery("Konto.findByMacierzysteBOPodatnik").setParameter("macierzyste", macierzyste).setParameter("podatnik", "Wzorcowy").setParameter("rok", rok).getResultList();
    }

    public List<Konto> findKontaSiostrzanePodatnik(WpisView wpisView, String macierzyste) {
        return em.createNamedQuery("Konto.findBySiostrzaneBOPodatnik").setParameter("macierzyste", macierzyste).setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public List<Konto> findKontaSiostrzaneWzorcowy(WpisView wpisView, String macierzyste) {
        return em.createNamedQuery("Konto.findBySiostrzaneBOPodatnik").setParameter("macierzyste", macierzyste).setParameter("podatnik", "Wzorcowy").setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public Object findKontaPotomnePodatnikCount(WpisView wpisView, String macierzyste) {
        return em.createNamedQuery("Konto.findByMacierzystePodatnikCOUNT").setParameter("macierzyste", macierzyste).setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getSingleResult();
    }

    public Object findKontaPotomneWzorcowyCount(WpisView wpisView, String macierzyste) {
        return em.createNamedQuery("Konto.findByMacierzystePodatnikCOUNT").setParameter("macierzyste", macierzyste).setParameter("podatnik", "Wzorcowy").setParameter("rok", wpisView.getRokWpisu()).getSingleResult();
    }

    public List<Konto> findKontaMaSlownik(WpisView wpisView, int idslownika) {
        return em.createNamedQuery("Konto.findByMaSlownik").setParameter("idslownika", idslownika).setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public List<Konto> findKontaPotomne(WpisView wpisView, String macierzyste, String bilansowewynikowe) {
        if (bilansowewynikowe.equals("bilansowe")) {
            return em.createNamedQuery("Konto.findByMacierzysteBilansowe").setParameter("macierzyste", macierzyste).setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
        } else {
            return em.createNamedQuery("Konto.findByMacierzysteWynikowe").setParameter("macierzyste", macierzyste).setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
        }
    }

    public List<Konto> findKontaPotomneWzorcowy(Integer rok, String macierzyste, String bilansowewynikowe) {
        if (bilansowewynikowe.equals("bilansowe")) {
            return em.createNamedQuery("Konto.findByMacierzysteBilansowe").setParameter("macierzyste", macierzyste).setParameter("podatnik", "Wzorcowy").setParameter("rok", rok).getResultList();
        } else {
            return em.createNamedQuery("Konto.findByMacierzysteWynikowe").setParameter("macierzyste", macierzyste).setParameter("podatnik", "Wzorcowy").setParameter("rok", rok).getResultList();
        }
    }

    public List<Konto> findKontaPrzyporzadkowane(String pozycja, String bilansowewynikowe, WpisView wpisView, String aktywa0pasywa1) {
        if (bilansowewynikowe.equals("bilansowe")) {
            return em.createNamedQuery("Konto.findByPozycjaBilansowe").setParameter("pozycja", pozycja).setParameter("aktywa0pasywa1", aktywa0pasywa1).setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
        } else {
            return em.createNamedQuery("Konto.findByPozycjaWynikowe").setParameter("pozycja", pozycja).setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
        }
    }

    public List<Konto> findKontaPrzyporzadkowaneWzorcowy(String pozycja, String bilansowewynikowe, int rok, String aktywa0pasywa1) {
        if (bilansowewynikowe.equals("bilansowe")) {
            return em.createNamedQuery("Konto.findByPozycjaBilansowe").setParameter("pozycja", pozycja).setParameter("aktywa0pasywa1", aktywa0pasywa1).setParameter("podatnik", "Wzorcowy").setParameter("rok", rok).getResultList();
        } else {
            return em.createNamedQuery("Konto.findByPozycjaWynikowe").setParameter("pozycja", pozycja).setParameter("podatnik", "Wzorcowy").setParameter("rok", rok).getResultList();
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

    public Dokfk findDokfkLastofaTypeKontrahent(String podatnik, String seriadokfk, Klienci kontr, String rok) {
        try {
            return (Dokfk) em.createNamedQuery("Dokfk.findByLastofaTypeKontrahent").setParameter("podatnik", podatnik).setParameter("seriadokfk", seriadokfk).setParameter("kontr", kontr).setParameter("rok", rok).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Dok findDokLastofaTypeKontrahent(String podatnik, Klienci kontr, String pkpirR) {
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

    public List<Tabelanbp> findByDateWalutaLista(String datatabeli, String nazwawaluty) {
        return em.createNamedQuery("Tabelanbp.findByDatatabeliSymbolwaluty").setParameter("datatabeli", datatabeli).setParameter("symbolwaluty", nazwawaluty).getResultList();
    }

    public Tabelanbp findTabelaPLN() {
        return (Tabelanbp) em.createNamedQuery("Tabelanbp.findBySymbolWaluty").setParameter("symbolwaluty", "PLN").getSingleResult();
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
        return em.createNamedQuery("Sesja.findByUzytkownik").setParameter("uzytkownik", user).getResultList();
    }

    public Collection<? extends Dokfk> findDokByTypeYear(String BO, String rok) {
        return em.createNamedQuery("Dokfk.findBySeriaRokdokfk").setParameter("seriadokfk", BO).setParameter("rok", rok).getResultList();
    }

    public List<PozycjaRZiS> findUkladBR(UkladBR u) {
        String uklad = u.getUklad();
        String podatnik = u.getPodatnik();
        String rok = u.getRok();
        return em.createNamedQuery("PozycjaRZiS.findByUkladPodRok").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<PozycjaRZiS> findUkladBR(String uklad, String podatnik, String rok) {
        return em.createNamedQuery("PozycjaRZiS.findByUkladPodRok").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<PozycjaRZiS> findBilansukladAktywa(UkladBR u) {
        String uklad = u.getUklad();
        String podatnik = u.getPodatnik();
        String rok = u.getRok();
        return em.createNamedQuery("PozycjaBilans.findByUkladPodRokAktywa").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<PozycjaRZiS> findBilansukladPasywa(UkladBR u) {
        String uklad = u.getUklad();
        String podatnik = u.getPodatnik();
        String rok = u.getRok();
        return em.createNamedQuery("PozycjaBilans.findByUkladPodRokPasywa").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<KontopozycjaBiezaca> findKontaBiezacePodatnikUklad(UkladBR uklad, String rb) {
        if (rb.equals("wynikowe")) {
            return em.createNamedQuery("KontopozycjaBiezaca.findByUkladWynikowe").setParameter("uklad", uklad).getResultList();
        } else {
            return em.createNamedQuery("KontopozycjaBiezaca.findByUkladBilansowe").setParameter("uklad", uklad).getResultList();
        }
    }

    public List<KontopozycjaZapis> findKontaZapisPodatnikUklad(UkladBR uklad, String rb) {
        if (rb.equals("wynikowe")) {
            return em.createNamedQuery("KontopozycjaZapis.findByUkladWynikowe").setParameter("uklad", uklad).getResultList();
        } else {
            return em.createNamedQuery("KontopozycjaZapis.findByUkladBilansowe").setParameter("uklad", uklad).getResultList();
        }
    }

    public Object findVatuepodatnik(String rokWpisu, String symbolokresu, String podatnikWpisu) {
        return em.createNamedQuery("Vatuepodatnik.findByRokKlientSymbolokresu").setParameter("rok", rokWpisu).setParameter("klient", podatnikWpisu).setParameter("symbolokresu", symbolokresu).getSingleResult();
    }

    public List<Pismoadmin> findPismoadminBiezace() {
        return em.createNamedQuery("Pismoadmin.findByNOTStatus").setParameter("status", "archiwalna").getResultList();
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
        return em.createNamedQuery("Kliencifk.findByPodatniknip").setParameter("podatniknip", podatniknip).getResultList();
    }

    public List<Podatnik> findPodatnikFK() {
        return em.createNamedQuery("Podatnik.findByFirmafk").setParameter("firmafk", true).getResultList();
    }

    public List<Podatnik> findPodatnikNieFK() {
        return em.createNamedQuery("Podatnik.findByFirmafk").setParameter("firmafk", false).getResultList();
    }

    public List<Podatnik> findPodatnikZUS() {
        return em.createNamedQuery("Podatnik.findByZUS").getResultList();
    }

    public List<Konto> findKontoPodatnik(String podatnik, String rok) {
        return em.createNamedQuery("Konto.findByPodatnik").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList();
    }
    
    public List<Konto> findKontoPodatnikBezSlownik(String podatnik, String rok) {
        return em.createNamedQuery("Konto.findByPodatnikBezSlownik").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList();
    }

    public List<Konto> findKontoPodatnikBez0(String podatnik, String rok) {
        return em.createNamedQuery("Konto.findByPodatnikBez0").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList();
    }

    public List<Konto> findWszystkieKontaPodatnikaBO(WpisView wpisView, String kategoriaKonta) {
        return em.createNamedQuery("Konto.findByKontaPodatnikaBO").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).setParameter("wzorzec", kategoriaKonta).getResultList();
    }

    public List<Konto> findWszystkieKontaBilansowePodatnika(WpisView wpisView) {
        return em.createNamedQuery("Konto.findByPodatnikBilansowe").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public List<Konto> findWszystkieKontaWynikowePodatnika(WpisView wpisView) {
        return em.createNamedQuery("Konto.findByPodatnikWynikowe").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public List<Konto> findKontaWynikowePodatnikaBezPotomkow(WpisView wpisView) {
        return em.createNamedQuery("Konto.findByPodatnikWynikoweBezPotomkow").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public List<Konto> findKontaBilansowePodatnikaBezPotomkow(WpisView wpisView) {
        return em.createNamedQuery("Konto.findByPodatnikBilansoweBezPotomkow").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public Konto findKonto860(WpisView wpisView) {
        return (Konto) em.createNamedQuery("Konto.findByKonto860").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getSingleResult();
    }

    public Konto findKontoPodatnik490(WpisView wpisView) {
        return (Konto) em.createNamedQuery("Konto.findByPodatnik490").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getSingleResult();
    }

    public int resetujKolumneMapotomkow(WpisView wpisView) {
        return em.createNamedQuery("Konto.updateMapotomkow").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).executeUpdate();
    }

    public int resetujKolumneZablokowane(WpisView wpisView) {
        return em.createNamedQuery("Konto.updateZablokowane").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).executeUpdate();
    }

    public List<String> findKlienciNIP() {
        return em.createNamedQuery("Klienci.findKlienciNip").getResultList();
    }

    public List<String> findNazwaPelna(String nowanazwa) {
        return em.createNamedQuery("Klienci.findByNpelna").setParameter("npelna", nowanazwa).getResultList();
    }

    public Zusmail findZusmail(Zusmail zusmail) {
        return (Zusmail) em.createNamedQuery("Zusmail.findByPK").setParameter("podatnik", zusmail.getZusmailPK().getPodatnik()).setParameter("rok", zusmail.getZusmailPK().getRok()).setParameter("mc", zusmail.getZusmailPK().getMc()).getSingleResult();
    }

    public List<Zusmail> findZusRokMc(String rok, String mc) {
        return em.createNamedQuery("Zusmail.findByRokMc").setParameter("rok", rok).setParameter("mc", mc).getResultList();
    }

    public List<Dokfk> findDokfkPodatnikRokMc(WpisView w) {
        return em.createNamedQuery("Dokfk.findByPodatnikRokMc").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).setParameter("mc", w.getMiesiacWpisu()).getResultList();
    }

    public List<Dokfk> findDokfkPodatnikRok(WpisView w) {
        return em.createNamedQuery("Dokfk.findByPodatnikRok").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).getResultList();
    }

    public List<Dokfk> findDokfkPodatnikRokMcKategoria(WpisView w, String kategoria) {
        return em.createNamedQuery("Dokfk.findByPodatnikRokMcKategoria").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).setParameter("mc", w.getMiesiacWpisu()).setParameter("kategoria", kategoria).getResultList();
    }

    public List<Dokfk> findDokfkPodatnikRokKategoria(WpisView w, String kategoria) {
        return em.createNamedQuery("Dokfk.findByPodatnikRokKategoria").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).setParameter("kategoria", kategoria).getResultList();
    }

    public Rejestrlogowan findRejestrlogowanByIP(String ipusera) {
        return (Rejestrlogowan) em.createNamedQuery("Rejestrlogowan.findByIpusera").setParameter("ipusera", ipusera).getSingleResult();
    }

    public List<Rejestrlogowan> RejestrlogowanfindByLiczbalogowan0() {
        return em.createNamedQuery("Rejestrlogowan.findByIloscLogowan0").getResultList();
    }

    public List<Podatnik> findAktywnyPodatnik(Boolean podmiotaktywny) {
        return em.createNamedQuery("Podatnik.findByPodmiotaktywny").setParameter("podmiotaktywny", podmiotaktywny).getResultList();
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
            return em.createNamedQuery("Transakcja.findByRozliczonyID").setParameter("rozliczany", idrozrachunku).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Transakcja> findTransakcjeSparowanyID(String podatnik) {
        try {
            return em.createNamedQuery("Transakcja.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Dok> znajdzOdDo(long odd, long dod) {
        return em.createNamedQuery("Dok.findByIdDokOdDo").setParameter("odd", odd).setParameter("dod", dod).getResultList();
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

    public Dok findDokByNr(String numer) {
        return (Dok) em.createNamedQuery("Dok.findByNrWlDk").setParameter("nrWlDk", "fvp/2013/13185/m").getSingleResult();
    }

    public List<Dok> znajdzKontr1Null() {
        return em.createNamedQuery("Dok.findByKontr1Null").getResultList();
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMaWaluta(Konto konto, String symbolwaluty, String wnma) {
        try {
            return em.createNamedQuery("StronaWiersza.findByStronaWierszaKontoWaluta").setParameter("konto", konto).setParameter("symbolwaluty", symbolwaluty).setParameter("wnma", wnma).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMa(Konto konto, String wnma) {
        try {
            return em.createNamedQuery("StronaWiersza.findByStronaWierszaKonto").setParameter("konto", konto).setParameter("wnma", wnma).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMaWalutaKorekta(Konto konto, String symbolwaluty, String wnma) {
        try {
            return em.createNamedQuery("StronaWiersza.findByStronaWierszaKontoWalutaKorekta").setParameter("konto", konto).setParameter("symbolwaluty", symbolwaluty).setParameter("wnma", wnma).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMaKorekta(Konto konto, String wnma) {
        try {
            return em.createNamedQuery("StronaWiersza.findByStronaWierszaKontoKorekta").setParameter("konto", konto).setParameter("wnma", wnma).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMaBO(Konto konto, String wnma) {
        try {
            return em.createNamedQuery("StronaWiersza.findByStronaWierszaKontoBO").setParameter("konto", konto).setParameter("wnma", wnma).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMaWalutaBO(Konto konto, String symbolwaluty, String wnma) {
        try {
            return em.createNamedQuery("StronaWiersza.findByStronaWierszaKontoWalutaBO").setParameter("konto", konto).setParameter("symbolwaluty", symbolwaluty).setParameter("wnma", wnma).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Rodzajedok> findListaWspolne() {
        return em.createNamedQuery("Rodzajedok.findByListaWspolna").getResultList();
    }

    public List<Rodzajedok> findListaPodatnik(Podatnik podatnik) {
        return em.createNamedQuery("Rodzajedok.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteRokWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty, MiejsceKosztow p) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteRokWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto.getPelnynumer()).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).setParameter("nazwapelna", p.getOpismiejsca()).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWaluta(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, MiejsceKosztow p) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteMcWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto.getPelnynumer()).setParameter("mc", mc).setParameter("symbolwaluty", skrotWaluty).setParameter("nazwapelna", p.getOpismiejsca()).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWalutaPojazdy(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, Pojazdy p) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteMcWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto.getPelnynumer()).setParameter("mc", mc).setParameter("symbolwaluty", skrotWaluty).setParameter("nazwapelna", p.getNrrejestracyjny()).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWalutaDelegacja(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, Delegacja p) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteMcWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto.getPelnynumer()).setParameter("mc", mc).setParameter("symbolwaluty", skrotWaluty).setParameter("nazwapelna", p.getOpisdlugi()).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikKontoBOWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikKontoBOWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikKontoBOWalutaWszystkie(Podatnik podatnik, Konto konto, String rok) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikKontoBOWalutaWszystkie").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutaWszystkie(Podatnik podatnik, Konto konto, String rok) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutaWszystkie").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokMcWalutaWszystkie(Podatnik podatnik, Konto konto, String rok, String mc) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokMcWalutaWszystkie").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).setParameter("mc", mc).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWszystkieNT(Podatnik podatnik, Konto konto, String rok) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokWszystkieNT").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutyWszystkieNT(Podatnik podatnik, String wybranaWalutaDlaKonta, Konto konto, String rok) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutyWszystkieNT").setParameter("podatnikObj", podatnik).setParameter("wybranaWalutaDlaKonta", wybranaWalutaDlaKonta).setParameter("konto", konto).setParameter("rok", rok).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikRokWalutaWynik(Podatnik podatnik, String rok, String skrotWaluty) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikRokWalutaWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikRokWynik(Podatnik podatnik, String rok) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikRokWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikRokMcWynik(Podatnik podatnik, String rok, String mc) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikRokMcWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokMcWynikSlownik(Podatnik podatnik, String rok, String mc) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikRokMcWynikSlownik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikRok(Podatnik podatnik, String rok) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikRok").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikRokWalutaBilans(Podatnik podatnik, String rok, String skrotWaluty) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikRokWalutaBilans").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikRokBilans(Podatnik podatnik, String rok) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikRokBilans").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikRokWalutaBilansBO(Podatnik podatnik, String rok, String skrotWaluty) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikRokWalutaBilansBO").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList();
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokBilansBO(Podatnik podatnik, String rok) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikRokBilansBO").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<WierszBO> findBOLista0(String grupa, WpisView wpisView) {
        return em.createNamedQuery("WierszBO.findByLista").setParameter("grupakonta", grupa).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList();
    }

    public List<WierszBO> findWierszBOPodatnikRok(Podatnik podatnik, String rok) {
        return em.createNamedQuery("WierszBO.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<WierszBO> findWierszBOPodatnikRokRozrachunkowe(Podatnik podatnik, String rok) {
        return em.createNamedQuery("WierszBO.findByPodatnikRokRozrachunkowe").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<WierszBO> findWierszBOPodatnikRokKonto(Podatnik podatnikObiekt, String rokWpisuSt, Konto konto) {
        return em.createNamedQuery("WierszBO.findByPodatnikRokKonto").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("konto", konto).getResultList();
    }

    public List<WierszBO> findWierszBOPodatnikRokKontoWaluta(Podatnik podatnikObiekt, String rokWpisuSt, Konto konto, String waluta) {
        return em.createNamedQuery("WierszBO.findByPodatnikRokKontoWaluta").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("konto", konto).setParameter("waluta", waluta).getResultList();
    }

    public List<Transakcja> findByNowaTransakcja(StronaWiersza s) {
        return em.createNamedQuery("Transakcja.findByNowaTransakcja").setParameter("nowatransakcja", s).getResultList();
    }

    public List<Transakcja> findByRozliczajacy(StronaWiersza s) {
        return em.createNamedQuery("Transakcja.findByRozliczajacy").setParameter("rozliczajacy", s).getResultList();
    }

    public Dok znajdzDokumentInwestycja(WpisView wpisView, Dok r) {
        return (Dok) em.createNamedQuery("Dok.znajdzInwestycja").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("data", r.getDataWyst()).setParameter("netto", r.getNetto()).setParameter("numer", r.getNrWlDk()).getSingleResult();
    }

    public EVatwpisFK znajdzEVatwpisFKPoWierszu(Wiersz wiersz) {
        return (EVatwpisFK) em.createNamedQuery("EVatwpisFK.findByWiersz").setParameter("wiersz", wiersz).getSingleResult();
    }

    public List<Konto> findlistaKontKasaBank(WpisView wpisView) {
        return em.createNamedQuery("Konto.findlistaKontKasaBank").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public List<Konto> findlistaKontGrupa0(WpisView wpisView) {
        return em.createNamedQuery("Konto.findlistaKontGrupa0").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }
    
    public List<Konto> findlistaKontGrupa0Analityka(WpisView wpisView) {
        return em.createNamedQuery("Konto.findlistaKontGrupa0Analityka").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }
    public List<Konto> findlistaKontGrupa1(WpisView wpisView) {
        return em.createNamedQuery("Konto.findlistaKontGrupa1").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }
    public List<Konto> findlistaKontGrupa2(WpisView wpisView) {
        return em.createNamedQuery("Konto.findlistaKontGrupa2").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }
    public List<Konto> findlistaKontGrupa3(WpisView wpisView) {
        return em.createNamedQuery("Konto.findlistaKontGrupa3").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }
    public List<Konto> findlistaKontGrupa4(WpisView wpisView) {
        return em.createNamedQuery("Konto.findlistaKontGrupa4").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }
    public List<Konto> findlistaKontGrupa5(WpisView wpisView) {
        return em.createNamedQuery("Konto.findlistaKontGrupa5").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }
   public List<Konto> findlistaKontGrupa6(WpisView wpisView) {
        return em.createNamedQuery("Konto.findlistaKontGrupa6").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }
    public List<Konto> findlistaKontGrupa7(WpisView wpisView) {
        return em.createNamedQuery("Konto.findlistaKontGrupa7").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }
    public List<Konto> findlistaKontGrupa8(WpisView wpisView) {
        return em.createNamedQuery("Konto.findlistaKontGrupa8").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }
    public List<Transakcja> findByKonto(Konto wybraneKontoNode) {
        return em.createNamedQuery(("Transakcja.findByKonto")).setParameter("konto", wybraneKontoNode).getResultList();
    }

    public List<Transakcja> findByPodatniRok(WpisView wpisView) {
        return em.createNamedQuery("Transakcja.findByPodatnikRok").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("podatnikObj", wpisView.getPodatnikObiekt()).getResultList();
    }

    public List<Transakcja> findByPodatnikBO(WpisView wpisView) {
        return em.createNamedQuery("Transakcja.findByPodatnikBO").setParameter("podatnik", wpisView.getPodatnikWpisu()).getResultList();
    }

    public List<Transakcja> findByPodatniRokRozniceKursowe(WpisView wpisView) {
        return em.createNamedQuery("Transakcja.findByPodatnikRokRozniceKursowe").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).setParameter("podatnikObj", wpisView.getPodatnikObiekt()).getResultList();
    }

    public List<Transakcja> findByPodatnikBORozniceKursowe(WpisView wpisView) {
        return em.createNamedQuery("Transakcja.findByPodatnikBORozniceKursowe").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList();
    }

    public Faktura findfaktura(Faktura f) {
        return em.find(Faktura.class, f.getFakturaPK());
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
        return em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutyWszystkie").setParameter("podatnikObj", podatnikObiekt).setParameter("konto", konto).setParameter("rok", rokWpisuSt).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokMcWalutyWszystkie(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt, String mc) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokMcWalutyWszystkie").setParameter("podatnikObj", podatnikObiekt).setParameter("konto", konto).setParameter("rok", rokWpisuSt).setParameter("mc", mc).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokMcVAT(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt, String mc) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokMcVAT").setParameter("podatnikObj", podatnikObiekt).setParameter("konto", konto).setParameter("rok", rokWpisuSt).setParameter("mc", mc).getResultList();
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokVAT(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt) {
        return em.createNamedQuery("StronaWiersza.findByPodatnikKontoRokVAT").setParameter("podatnikObj", podatnikObiekt).setParameter("konto", konto).setParameter("rok", rokWpisuSt).getResultList();
    }

    public Dokfk findDokfofaTypeKontrahent(Podatnik podatnikWpisu, String vat, String rokWpisuSt, String mc) {
        return (Dokfk) em.createNamedQuery("Dokfk.findBySeriaNumerRokdokfk").setParameter("seriadokfk", vat).setParameter("rok", rokWpisuSt).setParameter("podatnik", podatnikWpisu).setParameter("mc", mc).getSingleResult();
    }

    public List<Konto> findKontaWzorcowy(WpisView wpisView) {
        return em.createNamedQuery("Konto.findWzorcowe").setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }
    
    public List<Konto> findKontaWynikoweWzorcowy(WpisView wpisView) {
        return em.createNamedQuery("Konto.findWzorcoweWynikowe").setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }
    
    public List<Konto> findKontaBilansoweWzorcowy(WpisView wpisView) {
        return em.createNamedQuery("Konto.findWzorcoweBilansowe").setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public List<RMK> findRMKByPodatnikRok(WpisView wpisView) {
        return em.createNamedQuery("RMK.findByPodatnikRok").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("podatnikObj", wpisView.getPodatnikObiekt()).getResultList();
    }

    public List<MiejsceKosztow> findMiejscaPodatnik(Podatnik podatnik) {
        return em.createNamedQuery("MiejsceKosztow.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
    }

    public long countMiejscaKosztow(Podatnik podatnikObiekt) {
        return (long) em.createNamedQuery("MiejsceKosztow.countByPodatnik").setParameter("podatnik", podatnikObiekt).getSingleResult();
    }

    public List<Pojazdy> findPojazdyPodatnik(Podatnik podatnik) {
        return em.createNamedQuery("Pojazdy.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
    }

    public long countPojazdy(Podatnik podatnikObiekt) {
        return (long) em.createNamedQuery("Pojazdy.countByPodatnik").setParameter("podatnik", podatnikObiekt).getSingleResult();
    }

    public List<Delegacja> findDelegacjaPodatnik(WpisView wpisView, boolean krajowa0zagraniczna1) {
        return em.createNamedQuery("Delegacja.findByPodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("krajowa0zagraniczna1", krajowa0zagraniczna1).getResultList();
    }

    public long countDelegacja(WpisView wpisView, boolean krajowa0zagraniczna1) {
        return (long) em.createNamedQuery("Delegacja.countByPodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("krajowa0zagraniczna1", krajowa0zagraniczna1).getSingleResult();
    }

    public List<Uz> findMultiuser() {
        return em.createNamedQuery("Uz.findByUprawnienia").setParameter("uprawnienia", "Multiuser").getResultList();
    }

    public List<MultiuserSettings> findMutliuserSettingsByUz(Uz user) {
        return em.createNamedQuery("MultiuserSettings.findByUser").setParameter("user", user).getResultList();
    }

    public FakturaXXLKolumna findXXLByPodatnik(Podatnik p) {
        return (FakturaXXLKolumna) em.createNamedQuery("FakturaXXLKolumna.findByPodatnik").setParameter("podatnik", p).getSingleResult();
    }

    public int findMaxLevelPodatnik(String podatnik, int rokWpisu) {
        return (int) em.createNamedQuery("Konto.findByMaxLevelPodatnik").setParameter("podatnik", podatnik).setParameter("rok", rokWpisu).getSingleResult();
    }

    public List<UkladBR> findUkladBRPodatnik(String nazwapelna) {
        return em.createNamedQuery("UkladBR.findByPodatnik").setParameter("podatnik", nazwapelna).getResultList();
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

    public List<Konto> findKontazLevelu(WpisView wpisView, int i) {
        return em.createNamedQuery("Konto.findByLevelPodatnik").setParameter("level", i).setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).getResultList();
    }

    public List<Konto> findKontazLeveluRok(WpisView wpisView, int i) {
        return em.createNamedQuery("Konto.findByLevelRok").setParameter("level", i).setParameter("rok", 2015).getResultList();
    }

    public List<Konto> findSlownikoweKlienci(WpisView wpisView, Kliencifk kliencifk) {
        return em.createNamedQuery("Konto.findByPodatnikKliencifk").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).setParameter("nazwa", kliencifk.getNazwa()).setParameter("nip", kliencifk.getNip()).getResultList();
    }

    public WynikFKRokMc findWynikFKRokMc(WynikFKRokMc wynikFKRokMc) {
        return (WynikFKRokMc) em.createNamedQuery("WynikFKRokMc.findPodatnikRokMc").setParameter("podatnik", wynikFKRokMc.getPodatnikObj()).setParameter("rok", wynikFKRokMc.getRok()).setParameter("mc", wynikFKRokMc.getMc()).getSingleResult();
    }

    public List<WynikFKRokMc> findWynikFKPodatnikRok(WpisView wpisView) {
        return em.createNamedQuery("WynikFKRokMc.findPodatnikRok").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList();
    }

    public Klienci findKlientById(int i) {
        return (Klienci) em.createNamedQuery("Klienci.findById").setParameter("id", i).getSingleResult();
    }

    public List<EVatwpisFK> findEVatwpisFKByPodatnik(Podatnik podatnik) {
        return em.createNamedQuery("EVatwpisFK.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
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

    public void usunZapisaneKontoPozycjaBiezacaPodatnikUklad(UkladBR uklad, String rb) {
        if (rb.equals("wynikowe")) {
            em.createNamedQuery("KontopozycjaBiezaca.DeleteWynikowe").setParameter("uklad", uklad).executeUpdate();
        } else {
            em.createNamedQuery("KontopozycjaBiezaca.DeleteBilansowe").setParameter("uklad", uklad).executeUpdate();
        }
    }

    public void wyzerujPozycjeWKontach(WpisView wpisView, String bilansowewynikowe) {
        em.createNamedQuery("Konto.NullPozycjaBilansoweWynikowe").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).setParameter("bilansowewynikowe", bilansowewynikowe).executeUpdate();
    }
    
    public void wyzerujPozycjeWKontachWzorcowy(UkladBR uklad, String bilansowewynikowe) {
        em.createNamedQuery("Konto.NullPozycjaBilansoweWynikowe").setParameter("podatnik", "Wzorcowy").setParameter("rok", Integer.parseInt(uklad.getRok())).setParameter("bilansowewynikowe", bilansowewynikowe).executeUpdate();
    }

    public void zerujkontazLevelu(WpisView wpisView, int i) {
        em.createNamedQuery("Konto.NullObrotyWnLevel").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).setParameter("level", i).executeUpdate();
        em.createNamedQuery("Konto.NullObrotyMaLevel").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisu()).setParameter("level", i).executeUpdate();
    }

    public List<UkladBR> findUkladBRWzorcowyRok(String rokWpisu) {
        return em.createNamedQuery("UkladBR.findByWzorcowyRok").setParameter("rok", rokWpisu).getResultList();
    }

    public List<UkladBR> findukladBRPodatnikRok(String podatnikWpisu, String rokWpisuSt) {
        return em.createNamedQuery("UkladBR.findByPodatnikRok").setParameter("podatnik", podatnikWpisu).setParameter("rok", rokWpisuSt).getResultList();
    }

    public KontopozycjaZapis fintKontoPozycjaZapisByKonto(Konto konto) {
        return (KontopozycjaZapis) em.createNamedQuery("KontopozycjaZapis.findByKontoId").setParameter("kontoId", konto).getSingleResult();
    }

    public Delegacja findDelegacja(Delegacja delegacja) {
        return (Delegacja) em.createNamedQuery("Delegacja.findById").setParameter("id", delegacja.getId()).getSingleResult();
    }

    public List<PodatnikUdzialy> findUdzialyPodatnik(WpisView wpisView) {
        return em.createNamedQuery("PodatnikUdzialy.findBypodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList();
    }

}
