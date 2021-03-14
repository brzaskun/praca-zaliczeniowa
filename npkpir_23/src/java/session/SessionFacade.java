/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import dao.VATZDDAO;
import embeddable.Mce;
import entity.Dok;
import entity.EVatOpis;
import entity.EVatwpis1;
import entity.Evpozycja;
import entity.Faktura;
import entity.FakturaRozrachunki;
import entity.FakturaXXLKolumna;
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
import entity.PodatnikEwidencjaDok;
import entity.PodatnikOpodatkowanieD;
import entity.PodatnikUdzialy;
import entity.Podstawki;
import entity.Ryczpoz;
import entity.SMTPSettings;
import entity.Sprawa;
import entity.SrodekTrw;
import entity.Srodkikst;
import entity.StornoDok;
import entity.Sumypkpir;
import entity.UmorzenieN;
import entity.Uz;
import entity.WniosekVATZDEntity;
import entity.ZamkniecieRokuEtap;
import entity.ZamkniecieRokuRozliczenie;
import entity.Zamknietemiesiace;
import entity.Zobowiazanie;
import entity.Zusmail;
import entity.Zusstawki;
import entityfk.Cechazapisu;
import entityfk.Delegacja;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.KontopozycjaZapis;
import entityfk.MiejsceKosztow;
import entityfk.Pojazdy;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.RMK;
import entityfk.SprawozdanieFinansowe;
import entityfk.StronaWiersza;
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
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TemporalType;
import javax.transaction.Transactional;
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
@Transactional
public class SessionFacade<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    @PreDestroy
    private void preDestroy() {
         getEntityManager().clear();
         getEntityManager().close();
        em = null;
        error.E.s("koniec jpa");
    }

    @PersistenceContext(unitName = "npkpir_22PU")
    private EntityManager em;

    public SessionFacade() {
       // error.E.s("SessionFacade init");
    }

    public EntityManager getEntityManager() {
        return em;
    }

   

    

    public Evpozycja findEvpozycjaByName(String nazwapola) {
        return (Evpozycja)  getEntityManager().createNamedQuery("Evpozycja.findByNazwapola").setParameter("nazwapola", nazwapola).getSingleResult();
    }

    public List<Pitpoz> findPitpozAll() {
        List<Pitpoz> lista =  getEntityManager().createNamedQuery("Pitpoz.findAll").getResultList();
        return Collections.synchronizedList(lista);
    }
    

    public Pitpoz findPitpoz(String rok, String mc, String pod) {
        Pitpoz tmp = (Pitpoz)  getEntityManager().createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).getSingleResult();
        return tmp;
    }

    public List<Pitpoz> findPitpozLista(String rok, String mc, String pod) {
        List<Pitpoz> lista =  getEntityManager().createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).getResultList();
        return Collections.synchronizedList(lista);
    }

    public Pitpoz findPitpoz(String rok, String mc, String pod, String udzialowiec, Cechazapisu cecha) {
        Pitpoz tmp;
        if (cecha==null) {
            tmp = (Pitpoz)  getEntityManager().createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik AND p.udzialowiec = :udzialowiec AND p.cechazapisu IS NULL").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).setParameter("udzialowiec", udzialowiec).getSingleResult();
        } else {
            tmp = (Pitpoz)  getEntityManager().createQuery("SELECT p FROM Pitpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik AND p.udzialowiec = :udzialowiec AND p.cechazapisu = :cecha").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).setParameter("udzialowiec", udzialowiec).setParameter("cecha", cecha).getSingleResult();
        }
        return tmp;
    }

    
    public List<Ryczpoz> findRyczAll() {
        List<Ryczpoz> lista =  getEntityManager().createNamedQuery("Ryczpoz.findAll").getResultList();
        return Collections.synchronizedList(lista);
    }

    public Ryczpoz findRycz(String rok, String mc, String pod) {
        Ryczpoz tmp = (Ryczpoz)  getEntityManager().createQuery("SELECT p FROM Ryczpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).getSingleResult();
        return tmp;
    }

    public Ryczpoz findRycz(String rok, String mc, String pod, String udzialowiec) {
        Ryczpoz tmp = (Ryczpoz)  getEntityManager().createQuery("SELECT p FROM Ryczpoz p WHERE p.pkpirR = :pkpirR AND p.pkpirM = :pkpirM AND p.podatnik = :podatnik AND p.udzialowiec = :udzialowiec").setParameter("pkpirR", rok).setParameter("pkpirM", mc).setParameter("podatnik", pod).setParameter("udzialowiec", udzialowiec).getSingleResult();
        return tmp;
    }

    public List<Ryczpoz> findRyczpodatnik(String rok, String pod) {
        List<Ryczpoz> tmp =  getEntityManager().createQuery("SELECT p FROM Ryczpoz p WHERE p.pkpirR = :pkpirR AND p.podatnik = :podatnik").setParameter("pkpirR", rok).setParameter("podatnik", pod).getResultList();
        return Collections.synchronizedList(tmp);
    }


    
    
    public Podstawki findPodstawkiyear(Integer rok) {
        Podstawki tmp = (Podstawki)  getEntityManager().createNamedQuery("Podstawki.findByRok").setParameter("rok", rok).getSingleResult();
        return tmp;
    }

    

    

    public Platnosci findPlatnosciPK(PlatnosciPK key) throws Exception {
        Platnosci tmp = (Platnosci)  getEntityManager().createNamedQuery("Platnosci.findByKey").setParameter("podatnik", key.getPodatnik()).setParameter("rok", key.getRok()).setParameter("miesiac", key.getMiesiac()).getSingleResult();
        return tmp;
    }

    public List<Platnosci> findPlatnosciPodRok(String rok, String podatnik) throws Exception {
        List<Platnosci> tmp =  getEntityManager().createNamedQuery("Platnosci.findByPodRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList();
        return Collections.synchronizedList(tmp);
    }

    
    

    

    public Zobowiazanie findZobowiazanie(String rok, String mc) throws Exception {
        String[] nowedane = Mce.zwiekszmiesiac(rok, mc);
        try {
            Zobowiazanie tmp = (Zobowiazanie)  getEntityManager().createQuery("SELECT p FROM Zobowiazanie p WHERE p.zobowiazaniePK.rok = :rok AND p.zobowiazaniePK.mc = :mc").setParameter("rok", nowedane[0]).setParameter("mc", nowedane[1]).getSingleResult();
            return tmp;
        } catch (Exception e) {
            E.e(e);
            throw new Exception();
        }
    }

    public StornoDok findStornoDok(Integer rok, String mc, String podatnik) {
        try {
            StornoDok tmp = (StornoDok)  getEntityManager().createQuery("SELECT p FROM StornoDok p WHERE p.rok = :rok AND p.mc = :mc AND p.podatnik = :podatnik").setParameter("rok", rok).setParameter("mc", mc).setParameter("podatnik", podatnik).getSingleResult();
            return tmp;
        } catch (Exception e) {
            return null;
        }
    }

    public List<StornoDok> findStornoDok(Integer rok, String podatnik) {
        List<StornoDok> tmp =  getEntityManager().createQuery("SELECT p FROM StornoDok p WHERE p.rok = :rok AND p.podatnik = :podatnik").setParameter("rok", rok).setParameter("podatnik", podatnik).getResultList();
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
            wynik = (Dok)  getEntityManager().createNamedQuery("Dok.findPoprzednik").setParameter("pkpirR", rok).setParameter("pkpirM", mcS).setParameter("opis", "umorzenie za miesiac").getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
        return wynik;
    }

    

    

    
  

    public boolean findSTR(String podatnik, Double netto, String numer) {
        try {
             getEntityManager().createNamedQuery("SrodekTrw.findSTR").setParameter("podatnik", podatnik).setParameter("netto", netto).setParameter("podatnik", podatnik).setParameter("nrwldokzak", numer).getSingleResult();
            return true;
        } catch (Exception e) {
            E.e(e);
            return false;
        }
    }

    public Zamknietemiesiace findZM(String podatnik) {
        return (Zamknietemiesiace)  getEntityManager().createNamedQuery("Zamknietemiesiace.findByPodatnik").setParameter("podatnik", podatnik).getSingleResult();
    }

 
    public List<Srodkikst> findSrodekkst(String nazwa) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Srodkikst.findByNazwa").setParameter("nazwa", nazwa).getResultList());
    }

    public Srodkikst findSrodekkst1(String nazwa) {
        return (Srodkikst)  getEntityManager().createNamedQuery("Srodkikst.findByNazwa").setParameter("nazwa", nazwa).getSingleResult();
    }


    public Srodkikst findSr(Srodkikst srodek) {
        return  getEntityManager().find(Srodkikst.class, srodek);
    }

    public EVatOpis findEVatOpis(String name) {
        return (EVatOpis)  getEntityManager().createNamedQuery("EVatOpis.findByLogin").setParameter("login", name).getSingleResult();
    }

//    public List<Dok> findDokBKVAT(Podatnik pod, String rok) {
//        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dok.findByBKVAT").setParameter("podatnik", pod).setParameter("vatR", rok).getResultList());
//    }

    public List<Dok> findDokfkBKVAT(Podatnik pod, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dokfk.findByBKVAT").setParameter("podatnik", pod).setParameter("vatR", rok).getResultList());
    }

    

    public List<Dok> findDokBKWaluta(Podatnik pod, String rok, String mc) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dok.findByBKMWaluta").setParameter("podatnik", pod).setParameter("pkpirR", rok).setParameter("pkpirM", mc).getResultList());
    }

  

    public List<Sumypkpir> findSumy(String podatnik, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Sumypkpir.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    

    

    public List<SrodekTrw> findStrPod(String podatnik) {
        try {
            return Collections.synchronizedList( getEntityManager().createNamedQuery("SrodekTrw.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<SrodekTrw> findStrPodDokfk(String podatnik, Dokfk dokfk) {
        try {
            return Collections.synchronizedList( getEntityManager().createNamedQuery("SrodekTrw.findByPodatnikDokfk").setParameter("podatnik", podatnik).setParameter("dokfk", dokfk).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    

    public List<Inwestycje> findInwestycje(String podatnik, boolean zakonczona) {
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("doklist");
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Inwestycje.findByPodatnikZakonczona").setParameter("podatnik", podatnik).setParameter("zakonczona", zakonczona).setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    }

    
   

    public Dokfk findZZapisu(String numer) {
        return (Dokfk)  getEntityManager().createNamedQuery("Dokfk.findByNumer").setParameter("numer", numer).getSingleResult();
    }

    public Dokfk findDokfk(String data, String numer) {
        return (Dokfk)  getEntityManager().createNamedQuery("Dokfk.findByDatawystawieniaNumer").setParameter("datawystawienia", data).setParameter("numer", numer).getSingleResult();
    }

    public List<Fakturyokresowe> findPodatnik(String podatnik) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Fakturyokresowe.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
    }

    public List<Fakturywystokresowe> findPodatnikFaktury(String podatnik) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Fakturywystokresowe.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
    }

    
    public List<Fakturywystokresowe> findPodatnikRokFaktury(String podatnik, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Fakturywystokresowe.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }


    
    
    

    public List<Faktura> findByKontrahent_nip(String kontrahent_nip, Podatnik wystawca) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Faktura.findByKontrahent").setParameter("kontrahent_nip", kontrahent_nip).setParameter("wystawcanazwa", wystawca).getResultList());
    }

    
    
    public List<Faktura> findByKontrahentNipPo2015(String kontrahent_nip, Podatnik wystawca) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Faktura.findByKontrahentRokPo2015").setParameter("kontrahent_nip", kontrahent_nip).setParameter("wystawcanazwa", wystawca).getResultList());
    }

    public List<Faktura> findFakturyByRok(String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Faktura.findByRok").setParameter("rok", rok).getResultList());
    }

    
    

    

    
    public List<Wiersz> findWierszefkRozrachunki(String podatnik, Konto konto, Dokfk dokfk) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Wiersz.findByRozrachunki").setParameter("podatnik", podatnik).setParameter("konto", konto).setParameter("dokfk", dokfk).getResultList());
    }

    public List<Wiersz> findWierszeZapisy(String podatnik, String konto) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Wiersz.findByZapisy").setParameter("podatnik", podatnik).setParameter("konto", konto).getResultList());
    }

    public List<Wiersz> findWierszePodatnikMcRok(Podatnik podatnik, WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Wiersz.findByPodatnikMcRok").setParameter("podatnik", podatnik).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList());
    }

    public List<Wiersz> findWierszePodatnikMcRokWNTWDT(Podatnik podatnik, WpisView wpisView, String wntwdt) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Wiersz.findByPodatnikMcRokWNTWDT").setParameter("podatnik", podatnik).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).setParameter("wntwdt", wntwdt).getResultList());
    }

    public List<Wiersz> findWierszePodatnikRok(Podatnik podatnik, WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Wiersz.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", wpisView.getRokWpisuSt()).getResultList());
    }

//
//    public List<Wiersze> findWierszefkRozrachunki(String podatnik, String kontonumer) {
//        return  getEntityManager().createNamedQuery("Wiersz.findByRozrachunki1").setParameter("podatnik", podatnik).getResultList());
//    }
    public Dokfk findDokfk(Dokfk selected) {
        try {
            return (Dokfk)  getEntityManager().createNamedQuery("Dokfk.findByDokEdycjaFK")
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
        return  getEntityManager().find(Dokfk.class, selected);
    }

    public Dokfk findDokfkKontrahent(Dokfk selected) {
        try {
            return (Dokfk)  getEntityManager().createNamedQuery("Dokfk.findByDuplikatKontrahent").setParameter("seriadokfk", selected.getSeriadokfk()).setParameter("rok", selected.getRok()).setParameter("podatnikObj", selected.getPodatnikObj()).setParameter("numerwlasnydokfk", selected.getNumerwlasnydokfk()).setParameter("kontrahent", selected.getKontr()).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }


    

//    public Object findKontaPotomneWzorcowyCount(WpisView wpisView, String macierzyste) {
//        return  getEntityManager().createNamedQuery("Konto.findByMacierzystePodatnikCOUNTWZOR").setParameter("macierzyste", macierzyste).setParameter("rok", wpisView.getRokWpisu()).getSingleResult();
//    }

    
    
//    public List<Konto> findKontaPotomneWzorcowy(Integer rok, String macierzyste, String bilansowewynikowe) {
//        if (bilansowewynikowe.equals("bilansowe")) {
//            return Collections.synchronizedList( getEntityManager().createNamedQuery("Konto.findByMacierzysteBilansoweWZOR").setParameter("macierzyste", macierzyste).setParameter("rok", rok).getResultList());
//        } else {
//            return Collections.synchronizedList( getEntityManager().createNamedQuery("Konto.findByMacierzysteWynikoweWZOR").setParameter("macierzyste", macierzyste).setParameter("rok", rok).getResultList());
//        }
//    }

    
    
    
   
    public Dokfk findDokfkLastofaTypeMc(Podatnik podatnik, String seriadokfk, String rok, String mc) {
        try {
            return (Dokfk)  getEntityManager().createNamedQuery("Dokfk.findByLastofaTypeMc").setParameter("podatnik", podatnik).setParameter("seriadokfk", seriadokfk).setParameter("rok", rok).setParameter("mc", mc).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public Dokfk findDokfkLastofaType(Podatnik podatnik, String seriadokfk, String rok) {
        try {
            return (Dokfk)  getEntityManager().createNamedQuery("Dokfk.findByLastofaType").setParameter("podatnik", podatnik).setParameter("seriadokfk", seriadokfk).setParameter("rok", rok).setMaxResults(1).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    

    public Waluty findWalutaBySymbolWaluty(String staranazwa) {
        try {
            return (Waluty)  getEntityManager().createNamedQuery("Waluty.findBySymbolwaluty").setParameter("symbolwaluty", staranazwa).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    

    public Collection<? extends Dokfk> findDokByTypeYear(String BO, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dokfk.findBySeriaRokdokfk").setParameter("seriadokfk", BO).setParameter("rok", rok).getResultList());
    }

    public List<PozycjaRZiS> findUkladBR(UkladBR u) {
        String uklad = u.getUklad();
        String podatnik = u.getPodatnik().getNazwapelna();
        String rok = u.getRok();
        return Collections.synchronizedList( getEntityManager().createNamedQuery("PozycjaRZiS.findByUkladPodRok").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<PozycjaRZiS> findUkladBR(String uklad, String podatnik, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("PozycjaRZiS.findByUkladPodRok").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }
    
    public List<PozycjaRZiSBilans> findRZiSPozString(String pozycjaString, String rokWpisuSt, String uklad) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("PozycjaRZiS.findBilansPozString").setParameter("pozycjaString", pozycjaString).setParameter("rok", rokWpisuSt).setParameter("uklad", uklad).getResultList());
    }


    public List<PozycjaRZiS> findUkladBRBilans(String uklad, String podatnik, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("PozycjaBilans.findByUkladPodRok").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<PozycjaRZiS> findBilansukladAktywa(UkladBR u) {
        String uklad = u.getUklad();
        String podatnik = u.getPodatnik().getNazwapelna();
        String rok = u.getRok();
        return Collections.synchronizedList( getEntityManager().createNamedQuery("PozycjaBilans.findByUkladPodRokAktywa").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<PozycjaRZiS> findBilansukladPasywa(UkladBR u) {
        String uklad = u.getUklad();
        String podatnik = u.getPodatnik().getNazwapelna();
        String rok = u.getRok();
        return Collections.synchronizedList( getEntityManager().createNamedQuery("PozycjaBilans.findByUkladPodRokPasywa").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }
    
    public List<PozycjaRZiS> findBilansukladAktywaPasywa(UkladBR u) {
        String uklad = u.getUklad();
        String podatnik = u.getPodatnik().getNazwapelna();
        String rok = u.getRok();
        return Collections.synchronizedList( getEntityManager().createNamedQuery("PozycjaBilans.findByUkladPodRokAtywaPasywa").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<PozycjaRZiSBilans> findBilansPozString(String pozycjaString, String rokWpisuSt, String uklad) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("PozycjaBilans.findBilansPozString").setParameter("pozycjaString", pozycjaString).setParameter("rok", rokWpisuSt).setParameter("uklad", uklad).getResultList());
    }

    
       public List<KontopozycjaZapis> findKontaZapisPodatnikUklad(UkladBR uklad, String rb) {
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("ukladBR");
        lg.addAttribute("kontoID");
        if (rb.equals("wynikowe")) {
            return Collections.synchronizedList( getEntityManager().createNamedQuery("KontopozycjaZapis.findByUkladWynikowe").setParameter("uklad", uklad).setHint(QueryHints.LOAD_GROUP, lg).getResultList());
        } else {
            return Collections.synchronizedList( getEntityManager().createNamedQuery("KontopozycjaZapis.findByUkladBilansowe").setParameter("uklad", uklad).setHint(QueryHints.LOAD_GROUP, lg).getResultList());
        }
    }
    
//    public List<KontopozycjaZapis> findKontaZapisPodatnikUkladWzorzec(UkladBR uklad, String rb) {
//        if (rb.equals("wynikowe")) {
//            return Collections.synchronizedList( getEntityManager().createNamedQuery("KontopozycjaZapis.findByUkladWynikowe").setParameter("uklad", uklad).getResultList());
//        } else {
//            return Collections.synchronizedList( getEntityManager().createNamedQuery("KontopozycjaZapis.findByUkladBilansoweWzorzec").setParameter("uklad", uklad).getResultList());
//        }
//    }

    public Object findVatuepodatnik(String rokWpisu, String symbolokresu, String podatnikWpisu) {
        return  getEntityManager().createNamedQuery("Vatuepodatnik.findByRokKlientSymbolokresu").setParameter("rok", rokWpisu).setParameter("klient", podatnikWpisu).setParameter("symbolokresu", symbolokresu).getSingleResult();
    }

    public List<Pismoadmin> findPismoadminBiezace() {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Pismoadmin.findByNOTStatus").setParameter("status", "archiwalna").getResultList());
    }

    public List<Pismoadmin> findPismoadminNowe() {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Pismoadmin.findByStatus").setParameter("status", "wysłana").getResultList());
    }

  
    

    
    
    
    

    public Zusmail findZusmail(Zusmail zusmail) {
        return (Zusmail)  getEntityManager().createNamedQuery("Zusmail.findByPK").setParameter("podatnik", zusmail.getPodatnik()).setParameter("rok", zusmail.getRok()).setParameter("mc", zusmail.getMc()).getSingleResult();
    }

    public List<Zusmail> findZusRokMc(String rok, String mc) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Zusmail.findByRokMc").setParameter("rok", rok).setParameter("mc", mc).getResultList());
    }

    public List<Dokfk> findDokfkPodatnikRokMc(Podatnik podatnik, String rok, String mc) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dokfk.findByPodatnikRokMc").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList());
    }
    
    public List<Dokfk> findDokfkPodatnikRokMcVAT(WpisView w) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dokfk.findByPodatnikRokMcVAT").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).setParameter("mc", w.getMiesiacWpisu()).getResultList());
    }
    
    public List<Dokfk> findDokfkPodatnikRokKw(WpisView w, List<String> mcekw) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dokfk.findByPodatnikRokKw").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).setParameter("mc1", mcekw.get(0)).setParameter("mc2", mcekw.get(1)).setParameter("mc3", mcekw.get(2)).getResultList());
    }

    public List<Dokfk> findDokfkPodatnikRok(WpisView w) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dokfk.findByPodatnikRok").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).getResultList());
    }
    
    public List<Dokfk> findDokfkPodatnik(WpisView w) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dokfk.findByPodatnik").setParameter("podatnik", w.getPodatnikObiekt()).getResultList());
    }

    public List<Dokfk> findDokfkPodatnikRokSrodkiTrwale(WpisView w) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dokfk.findByPodatnikRokSrodkiTrwale").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).getResultList());
    }

    public List<Dokfk> findDokfkPodatnikRokRMK(WpisView w) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dokfk.findByPodatnikRokRMK").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).getResultList());
    }

    public List<Dokfk> findDokfkPodatnikRokMcKategoria(WpisView w, String kategoria) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dokfk.findByPodatnikRokMcKategoria").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).setParameter("mc", w.getMiesiacWpisu()).setParameter("kategoria", kategoria).getResultList());
    }

    public List<Dokfk> findDokfkPodatnikRokKategoria(WpisView w, String kategoria) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dokfk.findByPodatnikRokKategoria").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).setParameter("kategoria", kategoria).getResultList());
    }
    
    public List<Dokfk> findDokfkPodatnikRokKategoria(Podatnik podatnik, String rok, String kategoria) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dokfk.findByPodatnikRokKategoria").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("kategoria", kategoria).getResultList());
    }

    public List<Dokfk> findDokfkPodatnikRokKategoriaOrderByNo(WpisView w, String kategoria) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dokfk.findByPodatnikRokKategoriaOrderByNo").setParameter("podatnik", w.getPodatnikObiekt()).setParameter("rok", w.getRokWpisuSt()).setParameter("kategoria", kategoria).getResultList());
    }

    

    
    public List<Podatnik> findAktywnyPodatnik() {
        List<Podatnik> zwrot = Collections.synchronizedList( getEntityManager().createNamedQuery("Podatnik.findByPodmiotaktywny").getResultList());
        return zwrot;
    }
    
    

    public int usunniezaksiegowane(String podatnik) {
        return  getEntityManager().createNamedQuery("Rozrachunekfk.usunNiezaksiegowane").setParameter("podatnik", podatnik).executeUpdate();
    }

    public int usunTransakcjeNiezaksiegowane(String podatnik) {
        return  getEntityManager().createNamedQuery("Transakcja.usunNiezaksiegowane").setParameter("podatnik", podatnik).executeUpdate();
    }

    public Transakcja findTransakcja(int rozliczany, int sparowany) {
        try {
            return (Transakcja)  getEntityManager().createNamedQuery("Transakcja.findByRozliczanySparowany").setParameter("rozliczany", rozliczany).setParameter("sparowany", sparowany).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Transakcja> findTransakcjeRozliczonyID(int idrozrachunku) {
        try {
            return Collections.synchronizedList( getEntityManager().createNamedQuery("Transakcja.findByRozliczonyID").setParameter("rozliczany", idrozrachunku).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Transakcja> findTransakcjeSparowanyID(String podatnik) {
        try {
            return Collections.synchronizedList( getEntityManager().createNamedQuery("Transakcja.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<Dok> znajdzOdDo(long odd, long dod) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dok.findByIdDokOdDo").setParameter("odd", odd).setParameter("dod", dod).getResultList());
    }

    
    
   
    
    
  
    
    
    
    

        
    public List<Dok> findByKontr(Klienci numer) {
        return   getEntityManager().createNamedQuery("Dok.findByKontr").setParameter("kontr", numer.getId()).getResultList();
    }

    public Dok findDokByNr(String numer) {
        return (Dok)  getEntityManager().createNamedQuery("Dok.findByNrWlDk").setParameter("nrWlDk", "fvp/2013/13185/m").getSingleResult();
    }

    public List<Dok> znajdzKontr1Null() {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dok.findByKontr1Null").getResultList());
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMaWaluta(Konto konto, String symbolwaluty, String wnma) {
        try {
            
            return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByStronaWierszaKontoWaluta").setParameter("konto", konto).setParameter("symbolwaluty", symbolwaluty).setParameter("wnma", wnma).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMa(Konto konto, String wnma) {
        try {
            LoadGroup lg = new LoadGroup();
            lg.addAttribute("nowetransakcje");
            return  getEntityManager().createNamedQuery("StronaWiersza.findByStronaWierszaKonto").setParameter("konto", konto).setParameter("wnma", wnma)
                    .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }
    
    public List<StronaWiersza> findStronaWierszaByKontoOnly(Konto konto) {
        try {
            return  getEntityManager().createNamedQuery("StronaWiersza.findByStronaWierszaKontoOnly").setParameter("konto", konto).getResultList();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMaWalutaKorekta(Konto konto, String symbolwaluty, String wnma) {
        try {
            return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByStronaWierszaKontoWalutaKorekta").setParameter("konto", konto).setParameter("symbolwaluty", symbolwaluty).setParameter("wnma", wnma).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMaKorekta(Konto konto, String wnma) {
        try {
            LoadGroup lg = new LoadGroup();
            lg.addAttribute("nowetransakcje");
            return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByStronaWierszaKontoKorekta").setParameter("konto", konto).setParameter("wnma", wnma)
                    .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMaBO(Konto konto, String wnma) {
        try {
            return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByStronaWierszaKontoBO").setParameter("konto", konto).setParameter("wnma", wnma).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaWierszaByKontoWnMaWalutaBO(Konto konto, String symbolwaluty, String wnma) {
        try {
             return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByStronaWierszaKontoWalutaBO").setParameter("konto", konto).setParameter("symbolwaluty", symbolwaluty).setParameter("wnma", wnma).getResultList());
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    

    
    
    
    

    public List<StronaWiersza> findStronaByPodatnikKontoRokWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteRokWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty, MiejsceKosztow p) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteRokWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).setParameter("nazwapelna", p.getOpismiejsca()).getResultList());
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteRok(Podatnik podatnik, Konto konto, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteRok").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList());
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoSyntetyczneRok(Podatnik podatnik, Konto konto, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoSyntetyczneRok").setParameter("podatnikObj", podatnik).setParameter("kontonumer", konto.getPelnynumer()).setParameter("konto", konto).setParameter("rok", rok).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWaluta(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, MiejsceKosztow p) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteMcWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("mc", mc).setParameter("symbolwaluty", skrotWaluty).setParameter("nazwapelna", p.getOpismiejsca()).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWalutaPojazdy(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, Pojazdy p) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteMcWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto.getPelnynumer()).setParameter("mc", mc).setParameter("symbolwaluty", skrotWaluty).setParameter("nazwapelna", p.getNrrejestracyjny()).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoMacierzysteMcWalutaDelegacja(Podatnik podatnik, Konto konto, String mc, String skrotWaluty, Delegacja p) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoMacierzysteMcWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto.getPelnynumer()).setParameter("mc", mc).setParameter("symbolwaluty", skrotWaluty).setParameter("nazwapelna", p.getOpisdlugi()).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoBOWaluta(Podatnik podatnik, Konto konto, String rok, String skrotWaluty) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoBOWaluta").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoBOWalutaWszystkie(Podatnik podatnik, Konto konto, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoBOWalutaWszystkie").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutaWszystkie(Podatnik podatnik, Konto konto, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutaWszystkie").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokMcWalutaWszystkie(Podatnik podatnik, Konto konto, String rok, String mc) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokMcWalutaWszystkie").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).setParameter("mc", mc).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWszystkieNT(Podatnik podatnik, Konto konto, String rok) {
        //List lista =  getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWszystkieNT").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList();
        //this.refresh(lista);
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("nowetransakcje");
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWszystkieNT").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok)
                .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutyWszystkieNT(Podatnik podatnik, String wybranaWalutaDlaKonta, Konto konto, String rok) {
        //List lista =  getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutyWszystkieNT").setParameter("podatnikObj", podatnik).setParameter("wybranaWalutaDlaKonta", wybranaWalutaDlaKonta).setParameter("konto", konto).setParameter("rok", rok).getResultList();
        //this.refresh(lista);
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("nowetransakcje");
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutyWszystkieNT").setParameter("podatnikObj", podatnik).setParameter("wybranaWalutaDlaKonta", wybranaWalutaDlaKonta).setParameter("konto", konto).setParameter("rok", rok)
                .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWszystkieR(Podatnik podatnik, Konto konto, String rok) {
        //List lista =  getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWszystkieR").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok).getResultList();
        //this.refresh(lista);
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("platnosci");
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWszystkieR").setParameter("podatnikObj", podatnik).setParameter("konto", konto).setParameter("rok", rok)
                .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutyWszystkieR(Podatnik podatnik, String wybranaWalutaDlaKonta, Konto konto, String rok) {
        //List lista =  getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutyWszystkieR").setParameter("podatnikObj", podatnik).setParameter("wybranaWalutaDlaKonta", wybranaWalutaDlaKonta).setParameter("konto", konto).setParameter("rok", rok).getResultList();
        //this.refresh(lista);
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("platnosci");
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutyWszystkieR").setParameter("podatnikObj", podatnik).setParameter("wybranaWalutaDlaKonta", wybranaWalutaDlaKonta).setParameter("konto", konto).setParameter("rok", rok)
                .setHint(QueryHints.LOAD_GROUP, lg).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikRokWalutaWynik(Podatnik podatnik, String rok, String skrotWaluty) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWalutaWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList());
    }

    
    public List<StronaWiersza> findStronaByPodatnikRokWynikBO(Podatnik podatnik, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWynikBO").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikWynikCecha(Podatnik podatnik) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikWynikCecha").setParameter("podatnikObj", podatnik).getResultList());
    }
    
    public List<StronaWiersza> findStronaByPodatnikWynikCechaRokMc(Podatnik podatnik, String rok, String mc) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikWynikCechaRokMc").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikRokMcWynik(Podatnik podatnik, String rok, String mc) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokMcWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikRokMcWynikSlownik(Podatnik podatnik, String rok, String mc) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokMcWynikSlownik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc).getResultList());
    }
    
    public List<Konto> findStronaByPodatnikRokKontoDist(Podatnik podatnik, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findStronaByPodatnikRokKontoDist").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("rokK", Integer.parseInt(rok)).getResultList());
    }
    public List<StronaWiersza> findStronaByPodatnikRokMcodMcdo(Podatnik podatnik, String rok, String mcod, String mcdo) {
        //return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRok").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList());
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("wiersz.dokfk");
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokMcodMcdo").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mcod", mcod).setParameter("mcdo", mcdo)
                .setHint(QueryHints.READ_ONLY, HintValues.TRUE)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                .setHint(QueryHints.REFRESH, HintValues.TRUE)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    }
    
    public List<StronaWiersza> findStronaByPodatnikRokRO(Podatnik podatnik, String rok) {
        //return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRok").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList());
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("wiersz");
        lg.addAttribute("wiersz.dokfk");
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRok").setParameter("podatnikObj", podatnik).setParameter("rok", rok)
                .setHint(QueryHints.READ_ONLY, HintValues.TRUE)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikRokWalutaBilans(Podatnik podatnik, String rok, String skrotWaluty) {
        //return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWalutaBilans").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList());
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("wiersz.dokfk");
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWalutaBilans").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty)
                .setHint(QueryHints.READ_ONLY, HintValues.TRUE)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                .setHint(QueryHints.REFRESH, HintValues.TRUE)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikRokWynik(Podatnik podatnik, String rok, String mc) {
        //return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList());
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("wiersz.dokfk");
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc)
                .setHint(QueryHints.READ_ONLY, HintValues.TRUE)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    }
    
//    public List<StronaWiersza> findStronaByPodatnikRokWynikRO(Podatnik podatnik, String rok) {
//        //return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList());
//        LoadGroup lg = new LoadGroup();
//        lg.addAttribute("wiersz.dokfk");
//        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWynik").setParameter("podatnikObj", podatnik).setParameter("rok", rok)
//                .setHint(QueryHints.READ_ONLY, HintValues.TRUE)
//                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
//                
//                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
//    }
    
    public List<StronaWiersza> findStronaByPodatnikRokBilans(Podatnik podatnik, String rok, String mc) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokBilans").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc)
                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
                .setHint(QueryHints.REFRESH, HintValues.TRUE).setHint(QueryHints.READ_ONLY, HintValues.TRUE).getResultList());
    }
    
//    public List<StronaWiersza> findStronaByPodatnikRokBilansRO(Podatnik podatnik, String rok, String mc) {
//        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokBilans").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("mc", mc)
//                .setHint(QueryHints.QUERY_RESULTS_CACHE, HintValues.TRUE)
//                
//                .setHint(QueryHints.READ_ONLY, HintValues.TRUE).getResultList());
//    }

    

    public List<StronaWiersza> findStronaByPodatnikRokWalutaBilansBO(Podatnik podatnik, String rok, String skrotWaluty) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokWalutaBilansBO").setParameter("podatnikObj", podatnik).setParameter("rok", rok).setParameter("symbolwaluty", skrotWaluty).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikRokBilansBO(Podatnik podatnik, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikRokBilansBO").setParameter("podatnikObj", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<WierszBO> findBOLista0(String grupa, WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("WierszBO.findByLista").setParameter("grupakonta", grupa).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList());
    }
    
    public List<WierszBO> findBOLista0likwidacja(String grupa, WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("WierszBO.findByListaLikwidacja").setParameter("grupakonta", grupa).setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList());
    }
    
    public List<WierszBO> findBOListaRokMc(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("WierszBO.findByListaRokMc").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList());
    }

    public List<WierszBO> findWierszBOPodatnikRok(Podatnik podatnik, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("WierszBO.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<WierszBO> findWierszBOPodatnikRokRozrachunkowe(Podatnik podatnik, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("WierszBO.findByPodatnikRokRozrachunkowe").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    public List<WierszBO> findWierszBOPodatnikRokKonto(Podatnik podatnikObiekt, String rokWpisuSt, Konto konto) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("WierszBO.findByPodatnikRokKonto").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("konto", konto).getResultList());
    }

    public List<WierszBO> findWierszBOPodatnikRokKontoWaluta(Podatnik podatnikObiekt, String rokWpisuSt, Konto konto, String waluta) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("WierszBO.findByPodatnikRokKontoWaluta").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("konto", konto).setParameter("waluta", waluta).getResultList());
    }

    public List<Transakcja> findByNowaTransakcja(StronaWiersza s) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Transakcja.findByNowaTransakcja").setParameter("nowatransakcja", s).setHint(QueryHints.REFRESH, HintValues.TRUE).getResultList());
    }

    public List<Transakcja> findByRozliczajacy(StronaWiersza s) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Transakcja.findByRozliczajacy").setParameter("rozliczajacy", s).getResultList());
    }

    

    

    

    

    

//    public List<Konto> findlistaKontGrupa1(WpisView wpisView) {
//        return Collections.synchronizedList( getEntityManager().createNamedQuery("Konto.findlistaKontGrupa1").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
//    }
//
//    public List<Konto> findlistaKontGrupa2(WpisView wpisView) {
//        return Collections.synchronizedList( getEntityManager().createNamedQuery("Konto.findlistaKontGrupa2").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
//    }
//
//    public List<Konto> findlistaKontGrupa3(WpisView wpisView) {
//        return Collections.synchronizedList( getEntityManager().createNamedQuery("Konto.findlistaKontGrupa3").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
//    }
////moga
//    public List<Konto> findlistaKontGrupa4(WpisView wpisView) {
//        return Collections.synchronizedList( getEntityManager().createNamedQuery("Konto.findlistaKontGrupa4").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
//    }
//
//    public List<Konto> findlistaKontGrupa5(WpisView wpisView) {
//        return Collections.synchronizedList( getEntityManager().createNamedQuery("Konto.findlistaKontGrupa5").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
//    }
//
//    public List<Konto> findlistaKontGrupa6(WpisView wpisView) {
//        return Collections.synchronizedList( getEntityManager().createNamedQuery("Konto.findlistaKontGrupa6").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
//    }
//
//    public List<Konto> findlistaKontGrupa7(WpisView wpisView) {
//        return Collections.synchronizedList( getEntityManager().createNamedQuery("Konto.findlistaKontGrupa7").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
//    }
//
//    public List<Konto> findlistaKontGrupa8(WpisView wpisView) {
//        return Collections.synchronizedList( getEntityManager().createNamedQuery("Konto.findlistaKontGrupa8").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).getResultList());
//    }

    public List<Transakcja> findByKonto(Konto wybraneKontoNode) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery(("Transakcja.findByKonto")).setParameter("konto", wybraneKontoNode).getResultList());
    }

    public List<Transakcja> findByPodatniRok(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Transakcja.findByPodatnikRok").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("podatnikObj", wpisView.getPodatnikObiekt()).getResultList());
    }

    public List<Transakcja> findByPodatnikBO(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Transakcja.findByPodatnikBO").setParameter("podatnik", wpisView.getPodatnikWpisu()).getResultList());
    }

    public List<Transakcja> findByPodatniRokRozniceKursowe(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Transakcja.findByPodatnikRokRozniceKursowe").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).setParameter("podatnikObj", wpisView.getPodatnikObiekt()).getResultList());
    }
    
    public List<Transakcja> findByPodatniRokRozniceKursowe(WpisView wpisView, String mc) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Transakcja.findByPodatnikRokRozniceKursowe").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", mc).setParameter("podatnikObj", wpisView.getPodatnikObiekt()).getResultList());
    }

    public List<Transakcja> findByPodatnikBORozniceKursowe(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Transakcja.findByPodatnikBORozniceKursowe").setParameter("podatnik", wpisView.getPodatnikWpisu()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList());
    }

    public Faktura findfaktura(Faktura f) {
        return  getEntityManager().find(Faktura.class, f);
    }

    public Fakturaelementygraficzne findFaktElementyGraficzne(String podatnik) {
        try {
            return (Fakturaelementygraficzne)  getEntityManager().createNamedQuery("Fakturaelementygraficzne.findByPodatnik").setParameter("podatnik", podatnik).getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokWalutyWszystkie(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokWalutyWszystkie").setParameter("podatnikObj", podatnikObiekt).setParameter("konto", konto).setParameter("rok", rokWpisuSt).getResultList());
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoStartRokWalutyWszystkie(Podatnik podatnikObiekt, String konto, String rokWpisuSt, String mcod, String mcdo) {
        //t.platnosci t.wiersz.dokfk t.wiersz.tabelanbp
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("platnosci");
        lg.addAttribute("konto");
        lg.addAttribute("wiersz.dokfk");
        lg.addAttribute("wiersz.tabelanbp");
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoStartRokWalutyWszystkie")
                .setParameter("podatnikObj", podatnikObiekt)
                .setParameter("konto", konto)
                .setParameter("rok", rokWpisuSt)
                .setParameter("mcod", mcod)
                .setParameter("mcdo", mcdo)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    }
    
    public List<StronaWiersza> findStronaByPodatnikKontoStartRokWalutyWszystkieOdswiez(Podatnik podatnikObiekt, String konto, String rokWpisuSt, String mcod, String mcdo) {
        //t.platnosci t.wiersz.dokfk t.wiersz.tabelanbp
        LoadGroup lg = new LoadGroup();
        lg.addAttribute("platnosci");
        lg.addAttribute("konto");
        lg.addAttribute("wiersz.dokfk");
        lg.addAttribute("wiersz.tabelanbp");
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoStartRokWalutyWszystkie")
                .setParameter("podatnikObj", podatnikObiekt)
                .setParameter("konto", konto)
                .setParameter("rok", rokWpisuSt)
                .setParameter("mcod", mcod)
                .setParameter("mcdo", mcdo)
                .setHint(QueryHints.LOAD_GROUP, lg).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokMcWalutyWszystkie(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt, String mc) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokMcWalutyWszystkie").setParameter("podatnikObj", podatnikObiekt).setParameter("konto", konto).setParameter("rok", rokWpisuSt).setParameter("mc", mc).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokMcVAT(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt, String mc) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokMcVAT").setParameter("podatnikObj", podatnikObiekt).setParameter("konto", konto).setParameter("rok", rokWpisuSt).setParameter("mc", mc).getResultList());
    }

    public List<StronaWiersza> findStronaByPodatnikKontoRokVAT(Podatnik podatnikObiekt, Konto konto, String rokWpisuSt) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("StronaWiersza.findByPodatnikKontoRokVAT").setParameter("podatnikObj", podatnikObiekt).setParameter("konto", konto).setParameter("rok", rokWpisuSt).getResultList());
    }

    public Dokfk findDokfofaTypeKontrahent(Podatnik podatnikWpisu, String vat, String rokWpisuSt, String mc) {
        return (Dokfk)  getEntityManager().createNamedQuery("Dokfk.findBySeriaNumerRokdokfk").setParameter("seriadokfk", vat).setParameter("rok", rokWpisuSt).setParameter("podatnik", podatnikWpisu).setParameter("mc", mc).getSingleResult();
    }
    
    public List<Dokfk> findDokfofaTypeKontrahentKilka(Podatnik podatnikWpisu, String vat, String rokWpisuSt, String mc) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dokfk.findBySeriaNumerRokdokfk").setParameter("seriadokfk", vat).setParameter("rok", rokWpisuSt).setParameter("podatnik", podatnikWpisu).setParameter("mc", mc).getResultList());
    }

//    public List<Konto> findKontaWzorcowy(WpisView wpisView) {
//        return Collections.synchronizedList( getEntityManager().createNamedQuery("Konto.findWzorcowe").setParameter("rok", wpisView.getRokWpisu()).getResultList());
//    }
//    
//    public List<Konto> findKontaWzorcowy(Integer rok) {
//        return Collections.synchronizedList( getEntityManager().createNamedQuery("Konto.findWzorcowe").setParameter("rok", rok).getResultList());
//    }

    public List<RMK> findRMKByPodatnikRok(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("RMK.findByPodatnikRok").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("podatnikObj", wpisView.getPodatnikObiekt()).getResultList());
    }

    public List<RMK> findRMKByPodatnikRokDokfk(WpisView wpisView, Dokfk dokfk) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("RMK.findByPodatnikRokDokfk").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("podatnikObj", wpisView.getPodatnikObiekt()).setParameter("dokfk", dokfk).getResultList());
    }

    public List<MiejsceKosztow> findMiejscaPodatnik(Podatnik podatnik) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("MiejsceKosztow.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
    }
    
    public List<MiejsceKosztow> findMiejscaPodatnikWszystkie(Podatnik podatnik) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("MiejsceKosztow.findByPodatnikWszystkie").setParameter("podatnik", podatnik).getResultList());
    }
    
    public List<MiejsceKosztow> findMiejscaPrzychodowPodatnikRok(Podatnik podatnik, int rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("MiejscePrzychodow.findByPodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }
    
    public List<MiejsceKosztow> findMiejscaPrzychodowPodatnik(Podatnik podatnik) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("MiejscePrzychodow.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
    }
    
    public List<MiejsceKosztow> findMiejscaPrzychodowPodatnikWszystkie(Podatnik podatnik) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("MiejscePrzychodow.findByPodatnikWszystkie").setParameter("podatnik", podatnik).getResultList());
    }


    public List<Pojazdy> findPojazdyPodatnik(Podatnik podatnik) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Pojazdy.findByPodatnik").setParameter("podatnik", podatnik).getResultList());
    }

    public long countPojazdy(Podatnik podatnikObiekt) {
        return (long)  getEntityManager().createNamedQuery("Pojazdy.countByPodatnik").setParameter("podatnik", podatnikObiekt).getSingleResult();
    }

    public List<Delegacja> findDelegacjaPodatnik(WpisView wpisView, boolean krajowa0zagraniczna1) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Delegacja.findByPodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("krajowa0zagraniczna1", krajowa0zagraniczna1).getResultList());
    }

    public long countDelegacja(WpisView wpisView, boolean krajowa0zagraniczna1) {
        return (long)  getEntityManager().createNamedQuery("Delegacja.countByPodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("krajowa0zagraniczna1", krajowa0zagraniczna1).getSingleResult();
    }

    

    public List<MultiuserSettings> findMutliuserSettingsByUz(Uz user) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("MultiuserSettings.findByUser").setParameter("user", user).getResultList());
    }

    public FakturaXXLKolumna findXXLByPodatnik(Podatnik p) {
        try {
            return (FakturaXXLKolumna)  getEntityManager().createNamedQuery("FakturaXXLKolumna.findByPodatnik").setParameter("podatnik", p).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

   
    
    public List<UkladBR> findUkladBRRok(Podatnik podatnik, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("UkladBR.findByRokNieWzor").setParameter("podatnik", podatnik).setParameter("rok", rok).getResultList());
    }

    
    
    public void findRemoveRzisuklad(String uklad, String podatnik, String rok) {
         getEntityManager().createNamedQuery("PozycjaRZiS.Delete").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).executeUpdate();
    }

    public void findRemoveBilansuklad(String uklad, String podatnik, String rok) {
         getEntityManager().createNamedQuery("PozycjaBilans.Delete").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).executeUpdate();
    }

    public Integer findMaxLevelRzisuklad(String uklad, String podatnik, String rok) {
        return (Integer)  getEntityManager().createNamedQuery("PozycjaRZiS.findByMaxLevelPodatnik").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getSingleResult();
    }

    public Integer findMaxLevelBilansukladAktywa(String uklad, String podatnik, String rok) {
        return (Integer)  getEntityManager().createNamedQuery("PozycjaBilans.findByMaxLevelPodatnikAktywa").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getSingleResult();
    }

    public Integer findMaxLevelBilansukladPasywa(String uklad, String podatnik, String rok) {
        return (Integer)  getEntityManager().createNamedQuery("PozycjaBilans.findByMaxLevelPodatnikPasywa").setParameter("uklad", uklad).setParameter("podatnik", podatnik).setParameter("rok", rok).getSingleResult();
    }

    public PozycjaRZiS findPozycjaRZiSLP(int lp) {
        return (PozycjaRZiS)  getEntityManager().createNamedQuery("PozycjaRZiS.findByLp").setParameter("lp", lp).getSingleResult();
    }

    public PozycjaBilans findPozycjaBilansLP(int lp) {
        return (PozycjaBilans)  getEntityManager().createNamedQuery("PozycjaBilans.findByLp").setParameter("lp", lp).getSingleResult();
    }

    

//    public List<Konto> findKontazLeveluWzorcowy(WpisView wpisView, int i) {
//        return Collections.synchronizedList( getEntityManager().createNamedQuery("Konto.findByLevelWzorcowy").setParameter("level", i).setParameter("rok", wpisView.getRokWpisu()).getResultList());
//    }

    

    

    public WynikFKRokMc findWynikFKRokMc(WynikFKRokMc wynikFKRokMc) {
        return (WynikFKRokMc)  getEntityManager().createNamedQuery("WynikFKRokMc.findPodatnikRokMc").setParameter("podatnik", wynikFKRokMc.getPodatnikObj()).setParameter("rok", wynikFKRokMc.getRok()).setParameter("mc", wynikFKRokMc.getMc()).getSingleResult();
    }

    public WynikFKRokMc findWynikFKRokMcFirma(WynikFKRokMc wynikFKRokMc) {
        return (WynikFKRokMc)  getEntityManager().createNamedQuery("WynikFKRokMc.findPodatnikRokMcFirma").setParameter("podatnik", wynikFKRokMc.getPodatnikObj()).setParameter("rok", wynikFKRokMc.getRok()).setParameter("mc", wynikFKRokMc.getMc()).getSingleResult();
    }

    public List<WynikFKRokMc> findWynikFKPodatnikRok(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("WynikFKRokMc.findPodatnikRok").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList());
    }
    
    public List<WynikFKRokMc> findWynikFKPodatnikRokFirma(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("WynikFKRokMc.findPodatnikRokFirma").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList());
    }

    public List<WynikFKRokMc> findWynikFKPodatnikRokUdzialowiec(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("WynikFKRokMc.findPodatnikRokUdzialowiec").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getResultList());
    }


    public void usunZapisaneKontoPozycjaPodatnikUklad(UkladBR uklad, String rb) {
        if (rb.equals("wynikowe")) {
             getEntityManager().createNamedQuery("KontopozycjaZapis.DeleteWynikowe").setParameter("uklad", uklad).executeUpdate();
        } else {
             getEntityManager().createNamedQuery("KontopozycjaZapis.DeleteBilansowe").setParameter("uklad", uklad).executeUpdate();
        }
    }
    
    public void usunZapisaneKontoPozycjaPodatnikUkladByKonto(UkladBR uklad, Konto konto) {
         getEntityManager().createNamedQuery("KontopozycjaZapis.DeleteByKonto").setParameter("uklad", uklad).setParameter("konto", konto).executeUpdate();
     }


    
    
    
//    public void wyzerujPozycjeWKontachWzorcowy(UkladBR uklad, String bilansowewynikowe) {
//         getEntityManager().createNamedQuery("Konto.NullPozycjaBilansoweWynikowe").setParameter("podatnik", null).setParameter("rok", Integer.parseInt(uklad.getRok())).setParameter("bilansowewynikowe", bilansowewynikowe).executeUpdate();
//    }
//    public void zerujkontazLevelu(WpisView wpisView, int i) {
//         getEntityManager().createNamedQuery("Konto.NullObrotyWnLevel").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("level", i).executeUpdate();
//         getEntityManager().createNamedQuery("Konto.NullObrotyMaLevel").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisu()).setParameter("level", i).executeUpdate();
//    }
    
    

    
    
//    public List<UkladBR> findUkladBRWzorcowyRok(String rokWpisu) {
//        return Collections.synchronizedList( getEntityManager().createNamedQuery("UkladBR.findByWzorcowyRok").setParameter("rok", rokWpisu).getResultList());
//    }

    
    
    
    
    public KontopozycjaZapis fintKontoPozycjaZapisByKonto(Konto konto, UkladBR ukladBR) {
        return (KontopozycjaZapis)  getEntityManager().createNamedQuery("KontopozycjaZapis.findByKontoId").setParameter("kontoId", konto).setParameter("ukladBR", ukladBR).getSingleResult();
    }

    public Delegacja findDelegacja(Delegacja delegacja) {
        return (Delegacja)  getEntityManager().createNamedQuery("Delegacja.findById").setParameter("id", delegacja.getId()).getSingleResult();
    }

    public Delegacja findDelegacjaByNr(String nrdelegacji) {
        return (Delegacja)  getEntityManager().createNamedQuery("Delegacja.findByOpisdlugiOnly").setParameter("opisdlugi", nrdelegacji).getSingleResult();
    }

    public List<PodatnikUdzialy> findUdzialyPodatnik(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("PodatnikUdzialy.findBypodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList());
    }

    public List<PodatnikOpodatkowanieD> findOpodatkowaniePodatnik(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("PodatnikOpodatkowanieD.findBypodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList());
    }

    public PodatnikOpodatkowanieD findOpodatkowaniePodatnikRok(WpisView wpisView) {
        return (PodatnikOpodatkowanieD)  getEntityManager().createNamedQuery("PodatnikOpodatkowanieD.findBypodatnikRok").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).getSingleResult();
    }
    
    public List<PodatnikOpodatkowanieD> findOpodatkowaniePodatnikRokWiele(Podatnik p, String rok) {
        return  getEntityManager().createNamedQuery("PodatnikOpodatkowanieD.findBypodatnikRok").setParameter("podatnik", p).setParameter("rok", rok).getResultList();
    }
    
    
    public PodatnikOpodatkowanieD findOpodatkowaniePodatnikRokPoprzedni(WpisView wpisView) {
        PodatnikOpodatkowanieD zwrot = null;
        try {
            zwrot = (PodatnikOpodatkowanieD)  getEntityManager().createNamedQuery("PodatnikOpodatkowanieD.findBypodatnikRok").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokUprzedniSt()).getSingleResult();
        } catch (Exception e) {
        }
        return zwrot;
    }

    

    public List<String> znajdzDokumentPodatnikWprFK(String wpr) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Dokfk.znajdzDokumentPodatnikWpr").setParameter("wprowadzil", wpr).getResultList());
    }


    public List<String> findZnajdzSeriePodatnik(WpisView wpisView) {
        return Collections.synchronizedList((List<String>)  getEntityManager().createNamedQuery("Dokfk.znajdzSeriePodatnik").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList());
    }

    

    public WynikFKRokMc findWynikFKRokMcUdzialowiec(WynikFKRokMc wynikFKRokMc) {
        return (WynikFKRokMc)  getEntityManager().createNamedQuery("WynikFKRokMc.findPodatnikRokMcUdzialowiec").setParameter("podatnik", wynikFKRokMc.getPodatnikObj()).setParameter("rok", wynikFKRokMc.getRok()).setParameter("mc", wynikFKRokMc.getMc()).setParameter("udzialowiec", wynikFKRokMc.getUdzialowiec()).getSingleResult();
    }
    
    public WynikFKRokMc findWynikFKRokMcUdzialowiec(Podatnik podatnik, String rok, String mc, String udzialowiec) {
        return (WynikFKRokMc)  getEntityManager().createNamedQuery("WynikFKRokMc.findPodatnikRokMcUdzialowiec").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("mc", mc).setParameter("udzialowiec", udzialowiec).getSingleResult();
    }

    public List<Zusstawki> findZUS(boolean duzy0maly1) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Zusstawki.findZUS").setParameter("duzy0maly1", duzy0maly1).getResultList());
    }

    
    
    

    public List<FakturaRozrachunki> rozrachunkiZDnia(Date d, WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("FakturaRozrachunki.findByData_k").setParameter("data", d, TemporalType.DATE).setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList());
    }

    public List<FakturaRozrachunki> findByPodatnik(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("FakturaRozrachunki.findByPodatnik").setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList());
    }
    
    public List<FakturaRozrachunki> findByPodatnikRokMc(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("FakturaRozrachunki.findByPodatnikRokMc").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).getResultList());
    }

    public List<FakturaRozrachunki> findByPodatnikKontrahent(WpisView wpisView, Klienci kontrahent) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("FakturaRozrachunki.findByPodatnikKontrahent").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("kontrahent", kontrahent).getResultList());
    }

    public List<FakturaRozrachunki> findByPodatnikKontrahentRok(WpisView wpisView, Klienci kontrahent) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("FakturaRozrachunki.findByPodatnikKontrahentRok").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", wpisView.getRokWpisuSt()).setParameter("kontrahent", kontrahent).getResultList());
    }
    public int deleteWierszBOPodatnikRok(Podatnik podatnik, String rok) {
        return  getEntityManager().createNamedQuery("WierszBO.findByDeletePodatnikRok").setParameter("podatnik", podatnik).setParameter("rok", rok).executeUpdate();
    }
    
    public int deleteWierszBOPodatnikRokMc(Podatnik podatnik, String rok, String mc) {
        return  getEntityManager().createNamedQuery("WierszBO.findByDeletePodatnikRokMc").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("mc", mc).executeUpdate();
    }

    public void statystykaUsunrok(String rok) {
         getEntityManager().createNamedQuery("Statystyka.findUsunRok").setParameter("rok", rok).executeUpdate();
    }

    
    public void usunnaliczeniemc(WpisView wpisView, String kategoria) {
          getEntityManager().createNamedQuery("StowNaliczenie.DeleteNaliczoneMcRok").setParameter("podatnikObj",wpisView.getPodatnikObiekt()).setParameter("rok",wpisView.getRokWpisuSt()).setParameter("mc",wpisView.getMiesiacWpisu()).setParameter("kategoria",kategoria).executeUpdate();
    }

    
    

    

    public List<PlatnoscWaluta> findPlatnoscWalutaByDok(Dok selected) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("PlatnoscWaluta.findByDok").setParameter("dokument", selected).getResultList());
    }

    public List<ZamkniecieRokuRozliczenie> findZakmniecieRokuByRokPodatnik(Podatnik podatnikObiekt, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("ZamkniecieRokuRozliczenie.findByRokPodatnik").setParameter("podatnik", podatnikObiekt).setParameter("rok", rok).getResultList());
    }

    public List<ZamkniecieRokuEtap> findZakmniecieRokuEtapByRok(String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("ZamkniecieRokuEtap.findByRok").setParameter("rok", rok).getResultList());
    }

    public List<PlatnoscWaluta> findPlatnoscWalutaByPodRokMc(Podatnik podatnikObiekt, String rokWpisuSt, String miesiacWpisu) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("PlatnoscWaluta.findByPodRokMc").setParameter("podatnik", podatnikObiekt).setParameter("rok", rokWpisuSt).setParameter("mc", miesiacWpisu).getResultList());
    }

    public List<Sprawa> findSprawaByOdbiorca(Uz odbiorca) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Sprawa.findByOdbiorca").setParameter("odbiorca", odbiorca).getResultList());
    }

    public List<Sprawa> findSprawaByNadawca(Uz nadawca) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("Sprawa.findByNadawca").setParameter("nadawca", nadawca).getResultList());
    }

    

    public SMTPSettings findSMTPSettingsByUzytkownik(Uz uzytkownik) {
        SMTPSettings zwrot = null;
        try {
            zwrot = (SMTPSettings)  getEntityManager().createNamedQuery("SMTPSettings.findByUzytkownik").setParameter("uzytkownik", uzytkownik).getSingleResult();
        } catch (Exception e) {
            
        }
        return zwrot;
    }

    public SMTPSettings findSMTPSettingsByDef() {
        SMTPSettings zwrot = null;
        try {
            zwrot = (SMTPSettings)  getEntityManager().createNamedQuery("SMTPSettings.findByDef").getSingleResult();
        } catch (Exception e) {
            
        }
        return zwrot;
    }

    public List<UmorzenieN> findUmorzenieBySrodek(SrodekTrw str) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("UmorzenieN.findStr").setParameter("srodekTrw", str).getResultList());
    }

    
    public List<SprawozdanieFinansowe> findSprawozdanieRokPodatnik(WpisView wpisView, String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("SprawozdanieFinansowe.findByPodatnikRok").setParameter("podatnik", wpisView.getPodatnikObiekt()).setParameter("rok", rok).getResultList());
    }
    
    public List<SprawozdanieFinansowe> findSprawozdanieRok(String rok) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("SprawozdanieFinansowe.findByRok").setParameter("rok", rok).getResultList());
    }

    

    
    public void usunlogoplik(Podatnik podatnikObiekt) {
         getEntityManager().createNamedQuery("Logofaktura.usunlogo").setParameter("podatnik", podatnikObiekt).executeUpdate();
    }

    public Logofaktura findLogoByPodatnik(Podatnik podatnikObiekt) {
        return (Logofaktura)  getEntityManager().createNamedQuery("Logofaktura.findByPodatnik").setParameter("podatnik", podatnikObiekt).getSingleResult();
    }


    public void usunSumyPKPiR(String podatnik, String rok, String mc) {
         getEntityManager().createNamedQuery("Sumypkpir.deleteByPodatnikRokMc").setParameter("podatnik", podatnik).setParameter("rok", rok).setParameter("mc", mc).executeUpdate();
    }

    
    public List<EVatwpis1> zwrocEVatwpisFKKlientRokMc(Podatnik podatnikWpisu, String rokWpisuSt, String miesiacWpisu) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("EVatwpisFK.findByRokMc").setParameter("podatnik", podatnikWpisu).setParameter("pkpirR", rokWpisuSt).setParameter("mc", miesiacWpisu)
                .getResultList());
    }

    public List<EVatwpis1> zwrocEVatwpisFKKlientRokKw(Podatnik podatnikWpisu, String rokWpisuSt, List<String> mce) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("EVatwpisFK.findByRokKW").setParameter("podatnik", podatnikWpisu).setParameter("pkpirR", rokWpisuSt).setParameter("mc1", mce.get(0)).setParameter("mc2", mce.get(1)).setParameter("mc3", mce.get(2)).getResultList());
    }

   

  
    public List<KontopozycjaZapis> findByKontoOnly(Konto konto) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("KontopozycjaZapis.findByKontoOnly").setParameter("konto", konto).getResultList());
                
    }

    public List<VATZDDAO> findVATPozycjaByPodatnikRokMcFK(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("VATZDpozycja.findByPodatnikRokMcFK").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList());
    }

    public List<WniosekVATZDEntity> findWniosekZDByPodatnikRokMcFK(WpisView wpisView) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("WniosekVATZDEntity.findByPodatnikRokMcFK").setParameter("rok", wpisView.getRokWpisuSt()).setParameter("mc", wpisView.getMiesiacWpisu()).setParameter("podatnik", wpisView.getPodatnikObiekt()).getResultList());
    }

    

    public List<WniosekVATZDEntity> findWniosekZDByPodatnikRokMcFK(Podatnik podatnik, String rok, String mc) {
        return Collections.synchronizedList( getEntityManager().createNamedQuery("WniosekVATZDEntity.findByPodatnikRokMcFK").setParameter("rok", rok).setParameter("mc", mc).setParameter("podatnik", podatnik).getResultList());
    }

    public void ukladBRustawnieaktywneUkladBR() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    public List<KontopozycjaZapis> findKontaPozycjaZapisPodatnikRok(Podatnik podatnik, String rok) {
        return  getEntityManager().createNamedQuery("KontopozycjaZapis.findByPodatnikRok").setParameter("rok", rok).setParameter("podatnik", podatnik).getResultList();
    }
    
    public List<KontopozycjaZapis> findKontoPozycjaByRokUkladBilans(String rokWpisuSt, String uklad) {
        return  getEntityManager().createNamedQuery("KontopozycjaZapis.findKontoPozycjaByRokUkladBilans").setParameter("rok", rokWpisuSt).setParameter("uklad", uklad).getResultList();
    }
    public List<KontopozycjaZapis> findKontoPozycjaByRokUkladRZiS(String rokWpisuSt, String uklad) {
        return  getEntityManager().createNamedQuery("KontopozycjaZapis.findKontoPozycjaByRokUkladRZiS").setParameter("rok", rokWpisuSt).setParameter("uklad", uklad).getResultList();
    }


    public Dokfk findDokfId(Dokfk wybranyDokfk) {
        try {
            LoadGroup lg = new LoadGroup();
            lg.addAttribute("listawierszy");
            lg.addAttribute("listawierszy.strWn.nowetransakcje");
            lg.addAttribute("listawierszy.strMa.nowetransakcje");
            lg.addAttribute("listawierszy.strWn.platnosci"); 
            lg.addAttribute("listawierszy.strMa.platnosci"); 
            return (Dokfk)  getEntityManager().createNamedQuery("Dokfk.findById")
                    .setParameter("id", wybranyDokfk.getId())
                    //
                    .setHint(QueryHints.LOAD_GROUP, lg)
                    .getSingleResult();
        } catch (Exception e) {
            E.e(e);
            return null;
        }
    }


    public StronaWiersza findStronaWierszaById(StronaWiersza strona) {
        return (StronaWiersza)  getEntityManager().createNamedQuery("StronaWiersza.findById").setParameter("id", strona.getId()).getSingleResult();
    }

    public List<PodatnikEwidencjaDok> findPodatnikEwidencjaByPodatnik(Podatnik podatnik) {
        return  getEntityManager().createNamedQuery("PodatnikEwidencjaDok.findByPodatnik").setParameter("podatnik", podatnik).getResultList();
    }

    public SrodekTrw findStrId(Integer id) {
        return (SrodekTrw)  getEntityManager().createNamedQuery("SrodekTrw.findById").setParameter("id", id).getSingleResult();
    }

    

     

       
    
    

    

    

    

    

    
}
