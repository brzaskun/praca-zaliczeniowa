/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entity.Dok;
import entity.Klienci;
import entity.Podatnik;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named(value="DokDAOfk")
@Singleton
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
    
        
    public List<Dokfk> findAll(){
        return dokFacade.findAll(Dokfk.class);
    }

    public void usun(Dokfk selected) {
        dokFacade.remove(selected);
    }

    public List<Dokfk> findDokfkPodatnik(Podatnik podatnik, String rok) {
        try {
           return dokFacade.findDokfkPodatnik(podatnik, rok);
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

    public Dokfk findDokfkLastofaType(String podatnik, String seriadokfk, String rok) {
       try {
           return dokFacade.findDokfkLastofaType(podatnik,seriadokfk, rok);
       } catch (Exception e ){
           return null;
       }
    }
    
    public Dokfk findDokfkLastofaTypeKontrahent(String podatnik, String seriadokfk, Klienci kontr, String rok) {
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

   
    
}
