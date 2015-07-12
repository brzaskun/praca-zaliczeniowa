/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import embeddable.Mce;
import entity.Dok;
import entity.Klienci;
import entityfk.Dokfk;
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
    private static final Logger LOG = Logger.getLogger(DokDAO.class.getName());

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
    
    public List<Dok> findAll(){
        return dokFacade.findAll(Dok.class);
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
    
    public Dok znajdzDuplikatwtrakcie(Dok selD, String nazwapelna, String typdokumentu) {
        return dokFacade.dokumentDuplicatwtrakcie(selD, nazwapelna, typdokumentu);
    }
  
    public List<Dok> zwrocBiezacegoKlienta(String pod) {
        return dokFacade.findDokPod(pod);
    }

    public List<Dok> zwrocBiezacegoKlientaRokVAT(String pod, String rok) {
        return dokFacade.findDokBKVAT(pod,rok);
    }
  
    public List<Dok> zwrocBiezacegoKlientaRok(String pod, String rok) {
        return dokFacade.findDokBK(pod,rok);
    }
    
    public List<Dok> zwrocBiezacegoKlientaRokPrzychody(String pod, String rok) {
        return dokFacade.findDokBKPrzychody(pod,rok);
    }
     
    public List<Dok> zwrocRok(String rok) {
        return dokFacade.findDokRok(rok);
    }
    
   
    public List<Dok> zwrocBiezacegoKlientaRokMC(String pod, String rok, String mc) {
        return dokFacade.findDokBK(pod, rok, mc);
    }
    
    public Object iledokumentowklienta(String pod, String rok, String mc) {
        return dokFacade.findDokBKCount(pod, rok, mc);
    }
    
    public List<Dok> zwrocBiezacegoKlientaDuplikat(String pod, String rok) {
        return dokFacade.findDokDuplikat(pod, rok);
    }
    
    
    public Dok find(String typdokumentu, String podatnik, Integer rok){
        return  dokFacade.findDokTPR(typdokumentu,podatnik,rok.toString());
    }
    
    public Dok findDokMC(String typdokumentu, String podatnik, String rok, String mc){
        return dokFacade.findDokMC(typdokumentu, podatnik, rok, mc);
    }
    
    public void destroyStornoDok(String rok, String mc, String podatnik) {
        Dok tmp = dokFacade.findStornoDok(rok, mc, podatnik);
        dokFacade.remove(tmp);
    }

    public Dok findFaktWystawione(String nazwapelna, Klienci kontrahent, String numerkolejny, double brutto) {
        return dokFacade.findFaktWystawione(nazwapelna, kontrahent, numerkolejny, brutto);
    }

    public int liczdokumenty(String rok, String mc, String podatnik) {
        List<String> poprzedniemce = Mce.poprzedniemce(mc);
        int iloscdok = 0;
        for (String p : poprzedniemce) {
            iloscdok += Integer.parseInt(dokFacade.findDokBKCount(podatnik, rok, p).toString());
        }
        return iloscdok;
    }
   
     public Dok findDokLastofaKontrahent(String podatnik, Klienci kontr, String rok) {
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
  
}
