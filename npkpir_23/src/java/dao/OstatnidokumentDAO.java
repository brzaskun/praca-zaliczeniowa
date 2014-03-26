/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Dok;
import entity.Ostatnidokument;
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
public class OstatnidokumentDAO extends DAO implements Serializable {
    @Inject private SessionFacade ostatnidokumentFacade;

    public OstatnidokumentDAO() {
        super(Ostatnidokument.class);
    }
    
    public Dok pobierz(String nazwa){
        List<Ostatnidokument> temp = ostatnidokumentFacade.findAll(Ostatnidokument.class);
        for(Ostatnidokument p :temp){
            if(p.getUzytkownik().equals(nazwa)){
                return p.getDokument();
            }
        }
        return null;
    }
    
    public void usun(String nazwa){
        List<Ostatnidokument> temp = ostatnidokumentFacade.findAll(Ostatnidokument.class);
        for(Ostatnidokument p :temp){
            if(p.getUzytkownik().equals(nazwa)){
                ostatnidokumentFacade.remove(p);
                break;
            }
        }
        
    }
    
    public  List<Ostatnidokument> findAll(){
        try {
            return ostatnidokumentFacade.findAll(Ostatnidokument.class);
        } catch (Exception e) {
            return null;
        }
   }
    
}
