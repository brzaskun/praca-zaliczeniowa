/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Klienci;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class KlienciDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade klienciFacade;

    public KlienciDAO() {
        super(Klienci.class);
    }
    
    public  List<Klienci> findAll(){
        try {
            return klienciFacade.findAll(Klienci.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public  List<Klienci> findAllReadOnly(){
        try {
            return klienciFacade.findAllReadOnly(Klienci.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    
    public  List<String> findNIP(){
        try {
            return klienciFacade.findKlienciNIP();
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public  List<String> findNazwaPelna(String nowanazwa){
        try {
            return klienciFacade.findNazwaPelna(nowanazwa);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public Klienci findKlientByNazwa(String nazwapelna) {
            return klienciFacade.findKlientByNazwa(nazwapelna);
    }
    
    public Klienci findKlientByNip(String nip) {
        return klienciFacade.findKlientByNip(nip);
    }
    
    public List<String> findKlientByNipXX() {
        return klienciFacade.findKlientByNipXX();
    }
    
    public Klienci findKlientByNipImport(String nip) {
        return klienciFacade.findKlientByNipImport(nip);
    }

    public Klienci findKlientById(int i) {
        return klienciFacade.findKlientById(i);
    }
   
   
}
