/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Logofaktura;
import entity.Podatnik;
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
//pomaga przenosci opisy bo inaczej nie chca sie zachowac. scopy nie pasuja
public class LogofakturaDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade sessionFacade;

    public LogofakturaDAO() {
        super(Logofaktura.class);
    }
    
    public  List<Logofaktura> findAll(){
        try {
            return sessionFacade.findAll(Logofaktura.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    public void usun(Podatnik podatnikObiekt) {
        try {
            sessionFacade.usunlogoplik(podatnikObiekt);
        } catch (Exception e) { 
            E.e(e); 
        }
    }

    public Logofaktura findByPodatnik(Podatnik podatnikObiekt) {
        return sessionFacade.findLogoByPodatnik(podatnikObiekt);
    }
    
    
}
