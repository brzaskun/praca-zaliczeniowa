/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Podatnik;
import entity.VATZDpozycja;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import session.SessionFacade;
import view.WpisView;

/**
 *
 * @author Osito
 */
public class VATZDDAO extends DAO implements Serializable{
    
    @Inject private SessionFacade wierszeFacade;
    
    public VATZDDAO() {
        super(VATZDDAO.class);
    }
    
    public  List<VATZDpozycja> findAll(){
        try {
            return wierszeFacade.findAll(VATZDpozycja.class);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }
    
    public  List<VATZDpozycja> findByPodatnikRokMcFK(WpisView wpisView){
        try {
            return wierszeFacade.findVATPozycjaByPodatnikRokMcFK(wpisView);
        } catch (Exception e) { E.e(e); 
            return null;
        }
   }

    
   
}
