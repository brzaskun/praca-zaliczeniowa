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
            return fakturyokresoweFacade.findAll(Fakturyokresowe.class);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); 
            return null;
        }
   }
    
    public List<Fakturyokresowe> findPodatnik(String podatnik){
        List<Fakturyokresowe> zwrot = new ArrayList<>();
        try {
            zwrot = fakturyokresoweFacade.findPodatnik(podatnik);
        } catch (Exception e) { System.out.println("Blad "+e.getStackTrace()[0].toString()+" "+e.toString()); }
        return zwrot;
    }
}
