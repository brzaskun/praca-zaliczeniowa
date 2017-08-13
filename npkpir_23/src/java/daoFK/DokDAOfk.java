/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import embeddable.Kwartaly;
import entity.Klienci;
import entity.Podatnik;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named(value="DokDAOfk")

public class DokDAOfk extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject private SessionFacade dokFacade;
    
    public DokDAOfk() {
        super(Dokfk.class);
    }

    public DokDAOfk(Class entityClass) {
        super(entityClass);
    }


    public DokDAOfk(SessionFacade dokFacade, Class entityClass) {
        super(entityClass);
        this.dokFacade = dokFacade;
    }
    
    public void refresh(Dokfk dokfk) {
        dokFacade.refresh(dokfk);
    }
        
    public List<Dokfk> findAll(){
        return dokFacade.findAll(Dokfk.class);
    }

    public void usun(Dokfk selected) {
        dokFacade.remove(selected);
    }

    public List<Dokfk> findDokfkPodatnikRokMc(WpisView wpisView) {
        try {
           return dokFacade.findDokfkPodatnikRokMc(wpisView);
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnikRokKw(WpisView wpisView) {
        List<Dokfk> zwrot = new ArrayList<>();
        try {
           String mcwpisu = wpisView.getMiesiacWpisu();
           List<String> mcekw = Kwartaly.mctoMcewKw(mcwpisu);
           zwrot =  dokFacade.findDokfkPodatnikRokKw(wpisView, mcekw);
       } catch (Exception e ){
           
       }
        return zwrot;
    }
    
    public List<Dokfk> findDokfkPodatnikRok(WpisView wpisView) {
        try {
           return dokFacade.findDokfkPodatnikRok(wpisView);
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnik(WpisView wpisView) {
        try {
           return dokFacade.findDokfkPodatnik(wpisView);
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnikRok(Podatnik p, String rok) {
        try {
           return sessionFacade.getEntityManager().createNamedQuery("Dokfk.findByPodatnikRok").setParameter("podatnik", p).setParameter("rok", rok).getResultList();
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnikRokSrodkiTrwale(WpisView wpisView) {
        try {
           return dokFacade.findDokfkPodatnikRokSrodkiTrwale(wpisView);
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnikRokRMK(WpisView wpisView) {
        try {
           return dokFacade.findDokfkPodatnikRokRMK(wpisView);
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnikRokMcKategoria(WpisView wpisView, String kategoria) {
        try {
           return dokFacade.findDokfkPodatnikRokMcKategoria(wpisView, kategoria);
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnikRokKategoria(WpisView wpisView, String kategoria) {
        try {
           return dokFacade.findDokfkPodatnikRokKategoria(wpisView, kategoria);
       } catch (Exception e ){
           return null;
       }
    }
    
    public List<Dokfk> findDokfkPodatnikRokKategoriaOrderByNo(WpisView wpisView, String kategoria) {
        try {
           return dokFacade.findDokfkPodatnikRokKategoria(wpisView, kategoria);
       } catch (Exception e ){
           return null;
       }
    }

    public Dokfk findDokfk(String data, String numer) {
       try {
           return dokFacade.findDokfk(data, numer);
       } catch (Exception e ){
           return null;
       }
    }
    
  
    public Dokfk findDokfkObj(Dokfk selected) {
       try {
           return dokFacade.findDokfk(selected);
       } catch (Exception e ){
           return null;
       }
    }
    
       
    public Dokfk findDokfkObjKontrahent(Dokfk selected) {
       try {
           return dokFacade.findDokfkKontrahent(selected);
       } catch (Exception e ){
           return null;
       }
    }

    public Dokfk findDokfkLastofaType(Podatnik podatnik, String seriadokfk, String rok) {
       try {
           return dokFacade.findDokfkLastofaType(podatnik,seriadokfk, rok);
       } catch (Exception e ){
           return null;
       }
    }
    
    public Dokfk findDokfkLastofaTypeMc(Podatnik podatnik, String seriadokfk, String rok, String mc) {
       try {
           return dokFacade.findDokfkLastofaTypeMc(podatnik,seriadokfk, rok, mc);
       } catch (Exception e ){
           return null;
       }
    }
    
    public Dokfk findDokfkLastofaTypeKontrahent(Podatnik podatnik, String seriadokfk, Klienci kontr, String rok) {
       try {
           return dokFacade.findDokfkLastofaTypeKontrahent(podatnik,seriadokfk, kontr,rok);
       } catch (Exception e ){
           return null;
       }
    }

    public Collection<? extends Dokfk> findDokByTypeYear(String BO, String rok) {
        try {
           return dokFacade.findDokByTypeYear(BO,rok);
       } catch (Exception e ){
           return null;
       }
    }

    public List<Dokfk> zwrocBiezacegoKlientaRokVAT(Podatnik podatnik, String rok) {
        return dokFacade.findDokfkBKVAT(podatnik,rok);
    }

    public Dokfk findDokfofaType(Podatnik podatnikWpisu, String vat, String rokWpisuSt, String mc) {
        try {
           return dokFacade.findDokfofaTypeKontrahent(podatnikWpisu,vat, rokWpisuSt, mc);
       } catch (Exception e ){
           return null;
       }
    }

    public Dokfk findDokfkObjUsun(Dokfk dousuniecia) {
        try {
           return dokFacade.findDokfkObject(dousuniecia);
       } catch (Exception e ){
           return null;
       }
    }

    public List<String> znajdzDokumentPodatnikWprFK(String wpr) {
        return dokFacade.znajdzDokumentPodatnikWprFK(wpr);
    }

    public List<String> findZnajdzSeriePodatnik(WpisView wpisView) {
        return dokFacade.findZnajdzSeriePodatnik(wpisView);
    }
    
}
