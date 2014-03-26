/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Klienci;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@ManagedBean
public class KlienciDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade klienciFacade;

    public KlienciDAO() {
        super(Klienci.class);
    }
    
    public  List<Klienci> findAll(){
        try {
            System.out.println("Pobieram KlienciDAO");
            return klienciFacade.findAll(Klienci.class);
        } catch (Exception e) {
            return null;
        }
   }
   
   
}
