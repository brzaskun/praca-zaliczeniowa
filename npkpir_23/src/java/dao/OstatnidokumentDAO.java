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
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.ejb.Stateless;import javax.transaction.Transactional;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class OstatnidokumentDAO extends DAO implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject private SessionFacade ostatnidokumentFacade;
       @PersistenceContext(unitName = "npkpir_22PU")
    private EntityManager em;
    
    @PreDestroy
    private void preDestroy() {
        em.clear();
        em.close();
        em.getEntityManagerFactory().close();
        em = null;
        error.E.s("koniec jpa");
    }

    protected EntityManager getEntityManager() {
        return em;
    }

    public OstatnidokumentDAO() {
        super(Ostatnidokument.class);
        super.em = this.em;
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
