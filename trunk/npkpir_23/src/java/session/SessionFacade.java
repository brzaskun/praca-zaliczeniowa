/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import com.sun.xml.messaging.saaj.soap.impl.ElementFactory;
import embeddable.Mce;
import entityfk.WierszStronafkPK;
import entity.Amodok;
import entity.Deklaracjevat;
import entity.Dok;
import entity.EVatOpis;
import entity.Evewidencja;
import entity.Evpozycja;
import entity.Ewidencjevat;
import entity.Faktura;
import entity.Fakturadodelementy;
import entity.Fakturyokresowe;
import entity.Fakturywystokresowe;
import entity.Inwestycje;
import entity.Klienci;
import entity.Pismoadmin;
import entity.Pitpoz;
import entity.Platnosci;
import entity.PlatnosciPK;
import entity.Podatnik;
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
import entityfk.Dokfk;
import entityfk.DokfkPK;
import entityfk.Kliencifk;
import entityfk.Konto;
import entityfk.Kontopozycjarzis;
import entityfk.Kontozapisy;
import entityfk.PozycjaRZiS;
import entityfk.Rozrachunekfk;
import entityfk.Rzisuklad;
import entityfk.Tabelanbp;
import entityfk.Transakcja;
import entityfk.Waluty;
import entityfk.Wiersze;
import entityfk.Zestawienielisttransakcji;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 * @param <T>
 */
@Stateless
public class SessionFacade<T> implements Serializable{
    private static final long serialVersionUID = 1L;

    @PersistenceContext(unitName = "npkpir_22PU")
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
    
    public T findEntity(Class<T> entityClass, T entityPK) {
        T find = getEntityManager().find(entityClass, entityPK);
        getEntityManager().flush();
        return find;
    }

    public void remove(T entity) {
        getEntityManager().remove(getEntityManager().merge(entity));
    }

    public void edit(T entity) {
        getEntityManager().merge(entity);
        getEntityManager().flush();
    }
    
    public List<T> findXLast(Class<T> entityClass,int ile) {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        javax.persistence.criteria.Root<T> rt = cq.from(entityClass);
        cq.select(getEntityManager().getCriteriaBuilder().count(rt));
        javax.persistence.Query q = getEntityManager().createQuery(cq);
        int ilosc = ((Number) q.getSingleResult()).intValue();
        int kontrolailosci = ilosc-ile;
        if(kontrolailosci<0){
            kontrolailosci=0;
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

    public List<Pitpoz> findPitpozAll(){
        List<Pitpoz> lista = em.createNamedQuery("Pitpoz.findAll").getResultList();
        return lista;
    }
    
    public Pitpoz findPitpoz(String rok, String mc, String pod) {
        Pitpoz tmp = (Pitpoz) em.createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).getSingleResult();
        return tmp;
    }
    
    public List<Pitpoz> findPitpozLista(String rok, String mc, String pod) {
       List<Pitpoz> lista =  em.createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).getResultList();
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
    
     public List<Ryczpoz> findRyczAll(){
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

    
    public Uz findUzNP(String np) {
        Uz tmp = (Uz) em.createNamedQuery("Uz.findByLogin").setParameter("login", np).getSingleResult();
        return tmp;
    }
    

    public Sesja findSesja(String nrsesji) {
        try {
        return (Sesja) em.createNamedQuery("Sesja.findByNrsesji").setParameter("nrsesji", nrsesji).getSingleResult();
        } catch (Exception e){
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
            return null;
        }
        return wynik;
    }
    
     public Dok dokumentDuplicatAMO(Dok selD, String pkpirR) throws Exception {
        Dok wynik = null;
        try {
            wynik = (Dok) em.createNamedQuery("Dok.findDuplicateAMO").setParameter("podatnik", selD.getPodatnik()).setParameter("nrWlDk", selD.getNrWlDk()).setParameter("pkpirR", pkpirR).getSingleResult();
        } catch (Exception e) {
            return null;
        }
        return wynik;
    }
    
     public Dok dokumentDuplicatwtrakcie(Dok selD, String nazwapelna, String typdokumentu) {
        List<Dok> wynik = null;
        try {
            wynik = em.createNamedQuery("Dok.findDuplicatewTrakcie").setParameter("kontr", selD.getKontr()).setParameter("nrWlDk", selD.getNrWlDk()).setParameter("podatnik", nazwapelna).setParameter("typdokumentu", typdokumentu).getResultList();
            if (!wynik.isEmpty()) {
                return wynik.get(wynik.size()-1);
            } else {
                return null;
            }
        } catch (Exception e) {
            return null;
        }
    }

    public Zobowiazanie findZobowiazanie(String rok, String mc) throws Exception {
        String[] nowedane = Mce.zwiekszmiesiac(rok, mc);
        try {
            Zobowiazanie tmp = (Zobowiazanie) em.createQuery("SELECT p FROM Zobowiazanie p WHERE p.zobowiazaniePK.rok = :rok AND p.zobowiazaniePK.mc = :mc").setParameter("rok", nowedane[0]).setParameter("mc", nowedane[1]).getSingleResult();
            return tmp;
        } catch (Exception e) {
            throw new Exception();
        }
    }

    public StornoDok findStornoDok(Integer rok, String mc, String podatnik) {
        try {
        StornoDok tmp = (StornoDok) em.createQuery("SELECT p FROM StornoDok p WHERE p.rok = :rok AND p.mc = :mc AND p.podatnik = :podatnik").setParameter("rok", rok).setParameter("mc", mc).setParameter("podatnik", podatnik).getSingleResult();
        return tmp;
        } catch (Exception e){
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
            return null;
        }
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
            return null;
        }
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
        return (Deklaracjevat) em.createNamedQuery("Deklaracjevat.findByRokMcPod").setParameter("rok", rok).setParameter("miesiac", mc).setParameter("podatnik", pod).getSingleResult();
    }

    public List<Deklaracjevat> findDeklaracjewszystkie(String rok, String mc, String pod) {
        return em.createNamedQuery("Deklaracjevat.findByRokMcPod").setParameter("rok", rok).setParameter("miesiac", mc).setParameter("podatnik", pod).getResultList();
    }

    public Deklaracjevat findDeklaracjewysylka(String pod) {
        try {
            return (Deklaracjevat) em.createNamedQuery("Deklaracjevat.findByPodatnikWysylka").setParameter("podatnik", pod).setParameter("identyfikator", "").getSingleResult();
        } catch (Exception e){
            return null;
        }
    }
    
    public List<Deklaracjevat> findDeklaracjewysylkaLista(String pod) {
        try {
            return em.createNamedQuery("Deklaracjevat.findByPodatnikWysylka").setParameter("podatnik", pod).setParameter("identyfikator", "").getResultList();
        } catch (Exception e){
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
    public List<Dok> findDokRok(String rok) {
        return em.createNamedQuery("Dok.findByPkpirR").setParameter("pkpirR", rok).getResultList();
    }
    
    public List<Dok> findDokBKVAT(String pod, String rok) {
        return em.createNamedQuery("Dok.findByBKVAT").setParameter("podatnik", pod).setParameter("vatR", rok).getResultList();
    }
    
    public Dok findDokTPR(String typdokumentu,String pod, String rok) {
        List<Dok> lista = em.createNamedQuery("Dok.findByTPR").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("typdokumentu", typdokumentu).getResultList();
        return lista.get(lista.size()-1);
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
            return null;
        }
    }

    public Dok findDokMC(String typdokumentu, String podatnik, String rok, String mc) {
         try {
            return (Dok) em.createNamedQuery("Dok.findByRMPT").setParameter("typdokumentu",typdokumentu).setParameter("podatnik", podatnik).setParameter("pkpirR", rok).setParameter("pkpirM", mc).getSingleResult();
            } catch (Exception e) {
            return null;
        }
    }
    
    
    public List<Inwestycje> findInwestycje(String podatnik, boolean zakonczona) {
        return em.createNamedQuery("Inwestycje.findByPodatnikZakonczona").setParameter("podatnik", podatnik).setParameter("zakonczona", zakonczona).getResultList();
    }

    public List<Amodok> findPod(String podatnik) {
        try{
            return em.createNamedQuery("Amodok.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
        } catch (Exception e){
            return null;
        }
    }
    
    public List<Amodok> AmoDokPodRok(String podatnik, String rok) {
        try{
            return em.createNamedQuery("Amodok.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", Integer.parseInt(rok)).getResultList();
        } catch (Exception e){
            return null;
        }
    }
    
    public Amodok AmoDokPodMcRok(String podatnik, String mc, Integer rok) {
        try{
            return (Amodok) em.createNamedQuery("Amodok.findByPodatnikMcRok").setParameter("podatnik", podatnik).setParameter("mc", Mce.getMiesiacToNumber().get(mc)).setParameter("rok", rok).getSingleResult();
        } catch (Exception e){
            return null;
        }
    }
    
    public List<Amodok> findAmoDokBiezacy(String podatnik, String mc, String rok) {
        try{
            return em.createNamedQuery("Amodok.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
        } catch (Exception e){
            return null;
        }
    }

    public Konto findKonto(String numer, String podatnik) {
        return (Konto) em.createNamedQuery("Konto.findByPelnynumerPodatnik").setParameter("pelnynumer", numer).setParameter("podatnik", podatnik).getSingleResult();
    }
    
    public List<Konto> findKontaRozrachunkowe(String podatnik) {
        return em.createNamedQuery("Konto.findByZwyklerozrachszczegolnePodatnik").setParameter("zwyklerozrachszczegolne", "rozrachunkowe").setParameter("podatnik", podatnik).getResultList();
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

    public Fakturywystokresowe findOkresowa(Double brutto, String rok, String klientnip, String nazwapelna) {
        return (Fakturywystokresowe) em.createNamedQuery("Fakturywystokresowe.findByOkresowa").setParameter("brutto", brutto).setParameter("rok", rok).setParameter("podatnik", nazwapelna).setParameter("nipodbiorcy", klientnip).getSingleResult();
    }

    public Dok findFaktWystawione(String nazwapelna, Klienci kontrahent, String numerkolejny, double brutto) {
        return (Dok) em.createNamedQuery("Dok.findByFakturaWystawiona").setParameter("podatnik", nazwapelna).setParameter("kontr", kontrahent).setParameter("nrWlDk", numerkolejny).setParameter("brutto", brutto).getSingleResult();
    }
    
    public List<Wiersze> findWierszefkRozrachunki(String podatnik, Konto konto, DokfkPK dokfkPK) {
        return em.createNamedQuery("Wiersze.findByRozrachunki").setParameter("podatnik", podatnik).setParameter("konto", konto).setParameter("dokfkPK", dokfkPK).getResultList();
    }
    
    
    public List<Wiersze> findWierszeZapisy(String podatnik, String konto) {
        return em.createNamedQuery("Wiersze.findByZapisy").setParameter("podatnik", podatnik).setParameter("konto", konto).getResultList();
    }
    
    public List<Wiersze> findWierszePodatnik(String podatnik) {
        return em.createNamedQuery("Wiersze.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
    }

//
//    public List<Wiersze> findWierszefkRozrachunki(String podatnik, String kontonumer) {
//        return em.createNamedQuery("Wiersze.findByRozrachunki1").setParameter("podatnik", podatnik).getResultList();
//    }

    public Dokfk findDokfk(Dokfk selected) {
        try {
            return (Dokfk) em.createNamedQuery("Dokfk.findByPK").setParameter("dokfkPK", selected.getDokfkPK()).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Konto> findKontaOstAlityka(String podatnik) {
        return em.createNamedQuery("Konto.findByMapotomkowMaSlownikPodatnik").setParameter("mapotomkow", false).setParameter("podatnik", podatnik).getResultList();
    }

    public List<Fakturywystokresowe> findOkresoweOstatnie(String podatnik, String mc) {
        switch (mc) {
            case "01" : 
                return em.createNamedQuery("Fakturywystokresowe.findByM1").setParameter("podatnik", podatnik).getResultList();
            case "02" : 
                return em.createNamedQuery("Fakturywystokresowe.findByM2").setParameter("podatnik", podatnik).getResultList();
            case "03" : 
                return em.createNamedQuery("Fakturywystokresowe.findByM3").setParameter("podatnik", podatnik).getResultList();
            case "04" : 
                return em.createNamedQuery("Fakturywystokresowe.findByM4").setParameter("podatnik", podatnik).getResultList();
            case "05" : 
                return em.createNamedQuery("Fakturywystokresowe.findByM5").setParameter("podatnik", podatnik).getResultList();
            case "06" : 
                return em.createNamedQuery("Fakturywystokresowe.findByM6").setParameter("podatnik", podatnik).getResultList();
            case "07" : 
                return em.createNamedQuery("Fakturywystokresowe.findByM7").setParameter("podatnik", podatnik).getResultList();
            case "081" : 
                return em.createNamedQuery("Fakturywystokresowe.findByM8").setParameter("podatnik", podatnik).getResultList();
            case "09" : 
                return em.createNamedQuery("Fakturywystokresowe.findByM9").setParameter("podatnik", podatnik).getResultList();
            case "10" : 
                return em.createNamedQuery("Fakturywystokresowe.findByM10").setParameter("podatnik", podatnik).getResultList();
            case "11" : 
                return em.createNamedQuery("Fakturywystokresowe.findByM11").setParameter("podatnik", podatnik).getResultList();
            case "12" : 
                return em.createNamedQuery("Fakturywystokresowe.findByM12").setParameter("podatnik", podatnik).getResultList();
        }
        return null;
    }
  public List<Kontozapisy> findZapisyNumer(String numer) {
        return  em.createNamedQuery("Kontozapisy.findByNumer").setParameter("numer", numer).getResultList();
    }
    
    public List<Kontozapisy> findZapisyKonto(String konto) {
        return  em.createNamedQuery("Kontozapisy.findByKonto").setParameter("konto", konto).getResultList();
    }
    
    public List<Kontozapisy> findZapisyWierszID(int wierszID) {
        return  em.createNamedQuery("Kontozapisy.findByWierszID").setParameter("wierszID", wierszID).getResultList();
    }
    
     public List<Kontozapisy> findZapisyKontoPodatnik(String podatnik, String konto, String symbolwaluty) {
        return  em.createNamedQuery("Kontozapisy.findByKontoPodatnik").setParameter("podatnik", podatnik).setParameter("konto", konto).setParameter("symbolwaluty", symbolwaluty).getResultList();
    }
     
     public List<Kontozapisy> findZapisyKontoBOPodatnik(String podatnik, String konto) {
        return  em.createNamedQuery("Kontozapisy.findByKontoBO").setParameter("podatnik", podatnik).setParameter("konto", konto).getResultList();
    }
     
    public List<Kontozapisy> findZapisyPodatnikRok(String podatnik, String rok) {
        return  em.createNamedQuery("Kontozapisy.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
    }

    public List<Konto> findKontaPotomnePodatnik(String podatnik, String macierzyste) {
        return em.createNamedQuery("Konto.findByMacierzysteBOPodatnik").setParameter("macierzyste", macierzyste).setParameter("podatnik", podatnik).getResultList();
    }
    
    public Object findKontaPotomnePodatnikCount(String podatnik, String macierzyste) {
        return em.createNamedQuery("Konto.findByMacierzystePodatnikCOUNT").setParameter("macierzyste", macierzyste).setParameter("podatnik", podatnik).getSingleResult();
    }
    
    public List<Konto> findKontaMaSlownik() {
        return em.createNamedQuery("Konto.findByMaSlownik").setParameter("maslownik", true).getResultList();
    }
    
    public List<Konto> findKontaPotomne(String podatnik, String macierzyste, String bilansowewynikowe) {
        if (bilansowewynikowe.equals("bilansowe")) {
            return em.createNamedQuery("Konto.findByMacierzysteBilansowe").setParameter("macierzyste", macierzyste).setParameter("podatnik", podatnik).getResultList();
        } else {
            return em.createNamedQuery("Konto.findByMacierzysteWynikowe").setParameter("macierzyste", macierzyste).setParameter("podatnik", podatnik).getResultList();
        }
    }
    public List<Konto> findKontaPrzyporzadkowane(String pozycja, String bilansowewynikowe) {
        if (bilansowewynikowe.equals("bilansowe")) {
            return em.createNamedQuery("Konto.findByPozycjaBilansowe").setParameter("pozycja", pozycja).getResultList();
        } else {
            return em.createNamedQuery("Konto.findByPozycjaWynikowe").setParameter("pozycja", pozycja).getResultList();
        }
    }

   
    public Dokfk findDokfkLastofaType(String podatnik, String seriadokfk) {
        try {
            return (Dokfk) em.createNamedQuery("Dokfk.findByLastofaType").setParameter("podatnik", podatnik).setParameter("seriadokfk", seriadokfk).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Rozrachunekfk findRozrachunekfk(Rozrachunekfk p) {
//         try {
//            return (Rozrachunekfk) em.createNamedQuery("Rozrachunekfk.findByWierszStronafk").setParameter("wierszStronafkPK", p.getWierszStronafk().getWierszStronafkPK()).getSingleResult();
//        } catch (Exception e) {
//            return null;
//        }
         return null;
    }
    
    public List<Rozrachunekfk> findRozrachunekfkByPodatnik(String podatnik) {
         try {
            return em.createNamedQuery("Rozrachunekfk.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Rozrachunekfk> findRozrachunekfkByPodatnikkonto(String podatnik, String nrkonta) {
         try {
            return em.createNamedQuery("Rozrachunekfk.findByPodatnikKonto").setParameter("podatnik", podatnik).setParameter("nrkonta", nrkonta).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Rozrachunekfk> findRozrachunekfkByPodatnikKontoWaluta(String podatnik, String nrkonta, String waluta) {
         try {
            return em.createNamedQuery("Rozrachunekfk.findByPodatnikKontoWaluta").setParameter("podatnik", podatnik).setParameter("nrkonta", nrkonta).setParameter("waluta", waluta).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Rozrachunekfk> findRozrachunkifkByKonto(String nrkonta, String wnma, String walutarozrachunku) {
        String wnmaNew = null;
        if (wnma.equals("Wn")) {
            wnmaNew = "Ma";
        } else {
            wnmaNew = "Wn";
        }
        try {
            return em.createNamedQuery("Rozrachunekfk.findRozrachunkifkByKonto").setParameter("nrkonta", nrkonta).setParameter("wnmaNew", wnmaNew).setParameter("walutarozrachunku", walutarozrachunku).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
//     public List<Rozrachunekfk> findRozrachunkifkByKontoAktualny(String nrkonta, String wnma, String walutarozrachunku) {
//        try {
//            return em.createNamedQuery("Rozrachunekfk.findRozrachunkifkByKonto").setParameter("nrkonta", nrkonta).setParameter("wnmaNew", wnma).setParameter("walutarozrachunku", walutarozrachunku).getResultList();
//        } catch (Exception e) {
//            return null;
//        }
//    }

    public Zestawienielisttransakcji findByKlucz(WierszStronafkPK kluczlisty) {
         try {
            return (Zestawienielisttransakcji) em.createNamedQuery("Zestawienielisttransakcji.findByKluczlisty").setParameter("kluczlisty", kluczlisty).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public Rozrachunekfk findRozrachunkifkByWierszStronafk(WierszStronafkPK wierszStronafkPK) {
        try {
            return (Rozrachunekfk) em.createNamedQuery("Rozrachunekfk.findByWierszStronafk").setParameter("wierszStronafkPK", wierszStronafkPK).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    
    public Tabelanbp findByDateWaluta(String doprzekazania, String nazwawaluty) {
        return (Tabelanbp) em.createNamedQuery("Tabelanbp.findByDatatabeliSymbolwaluty").setParameter("datatabeli", doprzekazania).setParameter("symbolwaluty", nazwawaluty).getSingleResult();
    }

    public Waluty findWalutaByName(String staranazwa) {
        try {
            return (Waluty) em.createNamedQuery("Waluty.findBySymbolwaluty").setParameter("symbolwaluty", staranazwa).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Rozrachunekfk> findRozrachunkifkByDokfk(String seriadokfk, int nrkolejny, String podatnik, String rok) {
         try {
            return em.createNamedQuery("Rozrachunekfk.findRozrachunkifkByDokfk").setParameter("typDokfk", seriadokfk).setParameter("nrkolejnyDokfk", nrkolejny)
                    .setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Sesja> findUser(String user) {
        return em.createNamedQuery("Sesja.findByUzytkownik").setParameter("uzytkownik", user).getResultList();
    }

    public Collection<? extends Dokfk> findDokByTypeYear(String BO, String rok) {
        return em.createNamedQuery("Dokfk.findBySeriaRokdokfk").setParameter("seriadokfk", BO).setParameter("rok", rok).getResultList();
    }

    public List<PozycjaRZiS> findRzisuklad(Rzisuklad rzisuklad) {
        String uklad = rzisuklad.getRzisukladPK().getUklad();
        String podatnik = rzisuklad.getRzisukladPK().getPodatnik();
        String rok = rzisuklad.getRzisukladPK().getRok();
        return em.createNamedQuery("PozycjaRZiS.findByUkladPodRok").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
    }

    public List<Kontopozycjarzis> findKontaPodatnikUklad(String podatnik, String rok, String uklad) {
        return em.createNamedQuery("Kontopozycjarzis.findByPodatnikRokUklad").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("uklad", uklad).getResultList();
    }

    public Object findVatuepodatnik(String rokWpisu, String symbolokresu, String podatnikWpisu) {
        return em.createNamedQuery("Vatuepodatnik.findByRokKlientSymbolokresu").setParameter("rok", rokWpisu).setParameter("klient", podatnikWpisu).setParameter("symbolokresu", symbolokresu).getSingleResult();
    }

    public List<Pismoadmin> findPismoadminBiezace() {
        return em.createNamedQuery("Pismoadmin.findByNOTStatus").setParameter("status", "archiwalna").getResultList();
    }

    public Object znajdzkontofk(String nip, String podatniknip) {
        return em.createNamedQuery("Kliencifk.findByNipPodatniknip").setParameter("nip", nip).setParameter("podatniknip", podatniknip).getSingleResult();
    }

    public List<Kliencifk> znajdzkontofkKlient(String podatniknip) {
        return em.createNamedQuery("Kliencifk.findByPodatniknip").setParameter("podatniknip", podatniknip).getResultList();
    } 

    public List<Podatnik> findPodatnikFK() {
        return em.createNamedQuery("Podatnik.findByFirmafk").setParameter("firmafk", true).getResultList();
    }

    public List<Konto> findKontoPodatnik(String podatnik) {
        return em.createNamedQuery("Konto.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
    }

    public int resetujKolumneMapotomkow(String podatnik) {
        return em.createNamedQuery("Konto.updateMapotomkow").setParameter("podatnik", podatnik).executeUpdate();
    }
    
    public int resetujKolumneZablokowane(String podatnik) {
        return em.createNamedQuery("Konto.updateZablokowane").setParameter("podatnik", podatnik).executeUpdate();
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

    public List<Dokfk> findDokfkPodatnik(String podatnik, String rok) {
        return em.createNamedQuery("Dokfk.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
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
        return em.createNamedQuery("Transakcja.usunNiezaksiegowane").setParameter("podatnik",podatnik).executeUpdate();
    }

    public Transakcja findTransakcja(int rozliczany, int sparowany) {
        try {
            return (Transakcja) em.createNamedQuery("Transakcja.findByRozliczanySparowany").setParameter("rozliczany", rozliczany).setParameter("sparowany", sparowany).getSingleResult();
        } catch (Exception e) {
            return null;
        }
     }

    public List<Transakcja> findTransakcjeRozliczonyID(int idrozrachunku) {
        try {
            return em.createNamedQuery("Transakcja.findByRozliczonyID").setParameter("rozliczany", idrozrachunku).getResultList();
        } catch (Exception e) {
            return null;
        }
    }
    
    public List<Transakcja> findTransakcjeSparowanyID(int idrozrachunku) {
        try {
            return em.createNamedQuery("Transakcja.findBySparowanyID").setParameter("sparowany", idrozrachunku).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Rozrachunekfk> findRozrachunekfkByPodatnikKontoWalutaRozliczone(String podatnik, String nrkonta, String waluta) {
        try {
            return em.createNamedQuery("Rozrachunekfk.findByPodatnikKontoWalutaRozliczone").setParameter("podatnik", podatnik).setParameter("nrkonta", nrkonta).setParameter("waluta", waluta).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Rozrachunekfk> findRozrachunekfkByPodatnikKontoWalutaCzesciowo(String podatnik, String nrkonta, String waluta) {
        try {
            return em.createNamedQuery("Rozrachunekfk.findByPodatnikKontoWalutaCzesciowo").setParameter("podatnik", podatnik).setParameter("nrkonta", nrkonta).setParameter("waluta", waluta).getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public List<Rozrachunekfk> findRozrachunekfkByPodatnikKontoWalutaNowe(String podatnik, String nrkonta, String waluta) {
        try {
            return em.createNamedQuery("Rozrachunekfk.findByPodatnikKontoWalutaNowe").setParameter("podatnik", podatnik).setParameter("nrkonta", nrkonta).setParameter("waluta", waluta).getResultList();
        } catch (Exception e) {
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
        return (Klienci) em.createNamedQuery("Klienci.findByNip").setParameter("nip", nip).getSingleResult();
    }

    public Dok findDokByNr(String numer) {
        return (Dok) em.createNamedQuery("Dok.findByNrWlDk").setParameter("nrWlDk", "fvp/2013/13185/m").getSingleResult();
    }

    public List<Dok> znajdzKontr1Null() {
        return em.createNamedQuery("Dok.findByKontr1Null").getResultList();
    }

    
  
  
}
