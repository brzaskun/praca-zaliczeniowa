/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Pismoadmin;
import entity.UPO;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
public class UPODAO extends DAO implements Serializable{
    @Inject
    private SessionFacade sessionFacade;

    public UPODAO() {
        super(UPO.class);
    }
    
    public  List<Pismoadmin> findAll(){
        try {
            List<Pismoadmin> lista = sessionFacade.findAll(UPO.class);
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    
    public List<UPO> findPodatnikRok(WpisView wpisView) {
        try {
            List<UPO> lista = sessionFacade.findUPOPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<UPO> findUPORokMc(WpisView wpisView) {
        try {
            List<UPO> lista = sessionFacade.findUPORokMc(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }
    
    public List<UPO> findUPORokMc(String rok, String mc) {
        try {
            List<UPO> lista = sessionFacade.findUPORokMc(rok, mc);
            return lista;
        } catch (Exception e) { E.e(e); 
            return null;
        }
    }

   
    
    
}
