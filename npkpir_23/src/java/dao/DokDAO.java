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
import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Named(value = "DokDAO")
public class DokDAO extends DAO implements Serializable {
    //private static final Logger LOG =  Logger.getLogger(DokDAO.class.getName());

    @Inject private SessionFacade dokFacade;
    
    //tablica wciagnieta z bazy danych

    public DokDAO() {
        super(Dok.class);
    }

    public DokDAO(Class entityClass) {
        super(entityClass);
    }
    
    public List<Dok> znajdzOdDo(long odd, long dod) {
        return dokFacade.znajdzOdDo(odd,dod);
    }
    
    public List<Dok> znajdzKontr1NullOdDo() {
        return dokFacade.znajdzKontr1Null();
    }
    
            
    public List<Dok> findByKontr(Klienci numer) {
        return dokFacade.findByKontr(numer);
    }
    
    public Dok findDokByNr(String numer) {
        return dokFacade.findDokByNr(numer);
    }
    
    public Dok znajdzDuplikat(Dok selD, String rok) throws Exception {
        return dokFacade.dokumentDuplicat(selD, rok);
    }
    public Dok znajdzDuplikatAMO(Dok selD, String rok) throws Exception {
        return dokFacade.dokumentDuplicatAMO(selD, rok);
    }
    
    public Dok znajdzDuplikatwtrakcie(Dok selD, Podatnik podatnik, String typdokumentu) {
        return dokFacade.dokumentDuplicatwtrakcie(selD, podatnik, typdokumentu);
    }
  
    public List<Dok> zwrocBiezacegoKlienta(Podatnik pod) {
        return dokFacade.findDokPod(pod);
    }

    public List<Dok> zwrocBiezacegoKlientaRokVAT(Podatnik pod, String rok) {
        return dokFacade.findDokBKVAT(pod,rok);
    }
  
    public List<Dok> zwrocBiezacegoKlientaRok(Podatnik pod, String rok) {
        return dokFacade.findDokBK(pod,rok);
    }
    
    public List<Dok> zwrocBiezacegoKlientaRokOdMc(Podatnik pod, String rok, String mc) {
        return dokFacade.findDokBKodMca(pod,rok, mc);
    }
    
    public List<Dok> zwrocBiezacegoKlientaRokOdMcaDoMca(Podatnik pod, String rok, String mcdo, String mcod) {
        return dokFacade.findDokBKodMcadoMca(pod,rok, mcod, mcdo);
    }
    
    public List<Dok> zwrocBiezacegoKlientaRokPrzychody(Podatnik pod, String rok) {
        return dokFacade.findDokBKPrzychody(pod,rok);
    }
    
    public List<Dok> zwrocBiezacegoKlientaRokMcPrzychody(Podatnik pod, String rok, String mc) {
        return dokFacade.findDokBKMCPrzychody(pod,rok, mc);
    }
     
    public List<Dok> zwrocRok(String rok) {
        return dokFacade.findDokRok(rok);
    }
    
   
    public List<Dok> zwrocBiezacegoKlientaRokMC(Podatnik pod, String rok, String mc) {
        return dokFacade.findDokBK(pod, rok, mc);
    }
    
    public List zwrocBiezacegoKlientaRokMC(WpisView wpisView) {
        return dokFacade.findDokBK(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
    }
    
    public List zwrocBiezacegoKlientaRokMCVAT(WpisView wpisView) {
        return dokFacade.findDokBKVAT(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
    }
    
    public List<Dok> zwrocBiezacegoKlientaRokMCWaluta(Podatnik pod, String rok, String mc) {
        return dokFacade.findDokBKWaluta(pod, rok, mc);
    }
    
    public List<Dok> zwrocBiezacegoKlientaRokKW(Podatnik pod, String rok, String mc) {
        List<String> mce = Kwartaly.mctoMcewKw(mc);
        return dokFacade.findDokRokKW(pod, rok, mce);
    }
    
    public List zwrocBiezacegoKlientaRokKW(WpisView wpisView) {
        List<String> mce = Kwartaly.mctoMcewKw(wpisView.getMiesiacWpisu());
        return dokFacade.findDokRokKW(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt(), mce);
    }
    
    public Object iledokumentowklienta(Podatnik pod, String rok, String mc) {
        return dokFacade.findDokBKCount(pod, rok, mc);
    }
    
    public List<Dok> zwrocBiezacegoKlientaDuplikat(Podatnik pod, String rok) {
        return dokFacade.findDokDuplikat(pod, rok);
    }
    
    
    public Dok find(String typdokumentu, Podatnik podatnik, Integer rok){
        return  dokFacade.findDokTPR(typdokumentu,podatnik,rok.toString());
    }
    
    public Dok findDokMC(String typdokumentu, Podatnik podatnik, String rok, String mc){
        return dokFacade.findDokMC(typdokumentu, podatnik, rok, mc);
    }
    
    public void destroyStornoDok(String rok, String mc, Podatnik podatnik) {
        Dok tmp = dokFacade.findStornoDok(rok, mc, podatnik);
        dokFacade.remove(tmp);
    }

    public Dok findFaktWystawione(Podatnik podatnik, Klienci kontrahent, String numerkolejny, double brutto) {
        return dokFacade.findFaktWystawione(podatnik, kontrahent, numerkolejny, brutto);
    }

    public int liczdokumenty(String rok, String mc, Podatnik podatnik) {
        List<String> poprzedniemce = Mce.poprzedniemce(mc);
        int iloscdok = 0;
        for (String p : poprzedniemce) {
            iloscdok += Integer.parseInt(dokFacade.findDokBKCount(podatnik, rok, p).toString());
        }
        return iloscdok;
    }
   
     public Dok findDokLastofaKontrahent(Podatnik podatnik, Klienci kontr, String rok) {
       try {
           return dokFacade.findDokLastofaTypeKontrahent(podatnik, kontr,rok);
       } catch (Exception e ){
           return null;
       }
    }

    public Dok znajdzDokumentInwestycja(WpisView wpisView, Dok r) {
        return dokFacade.znajdzDokumentInwestycja(wpisView, r);
    }
    
    public List<String> znajdzDokumentPodatnikWpr(String wpr) {
        return dokFacade.znajdzDokumentPodatnikWpr(wpr);
    }

    public List<Dok> findDokByInwest() {
        return sessionFacade.getEntityManager().createNamedQuery("Dok.findByInwestycje").getResultList();
    }

    public List<Dok> findAll() {
        return sessionFacade.findAll(Dok.class);
    }
  
}
