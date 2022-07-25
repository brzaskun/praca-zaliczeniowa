/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.SrodekTrw;
import entityfk.Dokfk;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PreDestroy;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import session.SessionFacade;


/**
 *
 * @author Osito
 */
@Stateless
@Transactional
public class STRDAO extends DAO implements Serializable{
   
    @Inject
    private SessionFacade strFacade;
    //tablica wciagnieta z bazy danych
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

    public STRDAO() {
        super(SrodekTrw.class);
        super.em = this.em;
    }

   
    
   public boolean findSTR(String podatnik, Double netto, String numer){
       return strFacade.findSTR(podatnik, netto, numer);
   }
//    
//    public SrodekTrw znajdzDuplikat(SrodekTrw selD) throws Exception{
//        SrodekTrw tmp = null;
//        tmp = strFacade.duplicat(selD);
//        return tmp;
//        }
   
 
   
   public  List<SrodekTrw> findStrPod(String pod){
        try {
            return strFacade.findStrPod(pod);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
   
   public  SrodekTrw findStrId(SrodekTrw p ){
        try {
            return strFacade.findStrId(p.getId());
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
   
   public  List<SrodekTrw> findStrPodDokfk(String pod, Dokfk dokfk){
        try {
            return strFacade.findStrPodDokfk(pod, dokfk);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
}
