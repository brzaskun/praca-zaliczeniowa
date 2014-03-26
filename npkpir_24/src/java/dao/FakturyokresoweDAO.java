/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Fakturyokresowe;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class FakturyokresoweDAO  extends DAO implements Serializable {

    @Inject
    private SessionFacade fakturyokresoweFacade;

    public FakturyokresoweDAO() {
        super(Fakturyokresowe.class);
    }
    
    public  List<Fakturyokresowe> findAll(){
        try {
            System.out.println("Pobieram FakturyokresoweDAO");
            return fakturyokresoweFacade.findAll(Fakturyokresowe.class);
        } catch (Exception e) {
            return null;
        }
   }
    
    public List<Fakturyokresowe> findPodatnik(String podatnik){
        List<Fakturyokresowe> zwrot = new ArrayList<>();
        try {
            System.out.println("Pobieram FakturyokresoweDAO");
            zwrot = fakturyokresoweFacade.findPodatnik(podatnik);
        } catch (Exception e) {}
        return zwrot;
    }
}
