/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Dok;
import entity.Ostatnidokument;
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
public class OstatnidokumentDAO extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject private SessionFacade ostatnidokumentFacade;

    public OstatnidokumentDAO() {
        super(Ostatnidokument.class);
    }

    public OstatnidokumentDAO(Class entityClass) {
        super(entityClass);
    }

        
    public Dok pobierz(String nazwa){
        try {
            List<Ostatnidokument> temp = ostatnidokumentFacade.findAll(Ostatnidokument.class);
            for(Ostatnidokument p :temp){
                if(p.getUzytkownik().equals(nazwa)){
                    return p.getDokument();
                }
            }
        } catch (Exception e) {
            E.e(e);
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
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
}
