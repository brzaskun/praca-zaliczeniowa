/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Ewidencjevat;
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
public class EwidencjeVatDAO extends DAO implements Serializable {
    
    @Inject
    private SessionFacade ewidencjevatFacade;

    public EwidencjeVatDAO() {
        super(Ewidencjevat.class);
    }
    
    public Ewidencjevat find(String rok, String mc, String pod) {
        return ewidencjevatFacade.findEwidencjeVAT(rok, mc, pod);
    }
    
    public void dodajewidencje(Ewidencjevat ew){
        try {
            Ewidencjevat tmp = find(ew.getRok(),ew.getMiesiac(),ew.getPodatnik());
            destroy(tmp);
            dodaj(ew);
        } catch (Exception e) { E.e(e); 
            dodaj(ew);
        }
    }
    public  List<Ewidencjevat> findAll(){
        try {
            return ewidencjevatFacade.findAll(Ewidencjevat.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
}
