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
import entity.Fakturadodelementy;
import entity.Fakturyokresowe;
import entity.Fakturywystokresowe;
import entity.Inwestycje;
import entity.Klienci;
import entity.Pitpoz;
import entity.Platnosci;
import entity.PlatnosciPK;
import entity.Podatnik;
import entity.Podstawki;
import entity.Pozycjenafakturze;
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
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.Rozrachunki;
//import entityfk.Rozrachunki;
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
        int ilosc = ((Long) q.getSingleResult()).intValue();
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

    public Dok dokumentDuplicat(Dok selD) throws Exception {
        Dok wynik = null;
        try {
            wynik = (Dok) em.createNamedQuery("Dok.findDuplicate").setParameter("kontr", selD.getKontr()).setParameter("nrWlDk", selD.getNrWlDk()).setParameter("netto", selD.getNetto()).getSingleResult();
        } catch (Exception e) {
            System.out.println("Nie znaleziono duplikatu - DokFacade");
            return null;
        }
        System.out.println("Znaleziono duplikat - DokFacade");
        return wynik;
    }
    
     public Dok dokumentDuplicatwtrakcie(Dok selD, String nazwapelna, String typdokumentu) {
        Dok wynik = null;
        try {
            wynik = (Dok) em.createNamedQuery("Dok.findDuplicatewTrakcie").setParameter("kontr", selD.getKontr()).setParameter("nrWlDk", selD.getNrWlDk()).setParameter("podatnik", nazwapelna).setParameter("typdokumentu", typdokumentu).getSingleResult();
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
    
    public List<Deklaracjevat> findDeklaracjewyslane(String pod) {
        return em.createNamedQuery("Deklaracjevat.findByPodatnikWyslane").setParameter("podatnik", pod).setParameter("identyfikator", "").getResultList();
    }
    public List<Deklaracjevat> findDeklaracjewyslane(String pod, String rok) {
        return em.createNamedQuery("Deklaracjevat.findByPodatnikWyslaneRok").setParameter("podatnik", pod).setParameter("identyfikator", "").setParameter("rok", rok).getResultList();
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
    public Dok findDokTPR(String typdokumentu,String pod, String rok) {
        List<Dok> lista = em.createNamedQuery("Dok.findByTPR").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("typdokumentu", typdokumentu).getResultList();
        return lista.get(lista.size()-1);
    }

    public List<Dok> findDokBK(String pod, String rok, String mc) {
        return em.createNamedQuery("Dok.findByBKM").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("pkpirM", mc).getResultList();
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
        return (Dok) em.createNamedQuery("Dok.findByRMPT").setParameter("typdokumentu",typdokumentu).setParameter("podatnik", podatnik).setParameter("pkpirR", rok).setParameter("pkpirM", mc).getSingleResult();
    }
    
    
    public List<Inwestycje> findInwestycje(String podatnik, boolean zakonczona) {
        return em.createNamedQuery("Inwestycje.findByPodatnikZakonczona").setParameter("podatnik", podatnik).setParameter("zakonczona", zakonczona).getResultList();
    }

    public List<Amodok> findPod(String podatnik) {
        try{
            return em.createNamedQuery("Amodok.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
        } catch (Exception e){
            System.out.println("Nie ma dokumentow amo");
            return null;
        }
    }

    public Konto findKonto(String numer) {
        return (Konto) em.createNamedQuery("Konto.findByPelnynumer").setParameter("pelnynumer", numer).getSingleResult();
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

    public List<Pozycjenafakturze> findFakturyPodatnik(String podatnik) {
        return em.createNamedQuery("Pozycjenafakturze.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
    }

    public List<Evewidencja> findEvewidencjaByTransakcja(String transakcja) {
        return em.createNamedQuery("Evewidencja.findByTransakcja").setParameter("transakcja", transakcja).getResultList();
    }

    public List<Faktura> findByKontrahent_nip(String kontrahent_nip, String wystawca) {
        return em.createNamedQuery("Faktura.findByKontrahent").setParameter("kontrahent_nip", kontrahent_nip).setParameter("wystawcanazwa", wystawca).getResultList();
    }

    public List<Fakturadodelementy> findFaktElementyPodatnik(String podatnik) {
        return em.createNamedQuery("Fakturadodelementy.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
    }

    public List<Rozrachunki> findRozliczany(Integer zapisrozliczany) {
        return em.createNamedQuery("Rozrachunki.findByZapisrozliczany").setParameter("zapisrozliczany", zapisrozliczany).getResultList();
    }
     
  
    public List<Faktura> findByPodatnik(String podatnik) {
        return em.createNamedQuery("Faktura.findByWystawcanazwa").setParameter("wystawcanazwa", podatnik).getResultList();
}

    public Fakturywystokresowe findOkresowa(Double brutto, String klientnip, String nazwapelna) {
        return (Fakturywystokresowe) em.createNamedQuery("Fakturywystokresowe.findByOkresowa").setParameter("brutto", brutto).setParameter("podatnik", nazwapelna).setParameter("nipodbiorcy", klientnip).getSingleResult();
    }

    public Dok findFaktWystawione(String nazwapelna, Klienci kontrahent, String numerkolejny, double brutto) {
        return (Dok) em.createNamedQuery("Dok.findByFakturaWystawiona").setParameter("podatnik", nazwapelna).setParameter("kontr", kontrahent).setParameter("nrWlDk", numerkolejny).setParameter("brutto", brutto).getSingleResult();
    }

   
  
}
