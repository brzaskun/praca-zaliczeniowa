/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Fakturadodelementy;
import entity.Klienci;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@ManagedBean
public class FakturadodelementyDAO extends DAO implements Serializable {

    @Inject
    private SessionFacade fakturadodelementyFacade;

    public FakturadodelementyDAO() {
        super(Fakturadodelementy.class);
    }
    
    public  List<Fakturadodelementy> findAll(){
        try {
            System.out.println("Pobieram FakturadodelementyDAO");
            return fakturadodelementyFacade.findAll(Fakturadodelementy.class);
        } catch (Exception e) {
            return null;
        }
   }
    
    public  List<Fakturadodelementy> findFaktElementyPodatnik(String podatnik){
        try {
            System.out.println("Pobieram FakturadodelementyDAO wg Podatnik");
            return fakturadodelementyFacade.findFaktElementyPodatnik(podatnik);
        } catch (Exception e) {
            return null;
        }
   }
   
   
}
