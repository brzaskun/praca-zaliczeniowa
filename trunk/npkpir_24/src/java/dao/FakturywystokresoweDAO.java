/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import entity.Fakturyokresowe;
import entity.Fakturywystokresowe;
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
public class FakturywystokresoweDAO  extends DAO implements Serializable {

    @Inject
    private SessionFacade fakturywystokresoweFacade;

    public FakturywystokresoweDAO() {
        super(Fakturyokresowe.class);
    }
    
    public  List<Fakturywystokresowe> findAll(){
        try {
            System.out.println("Pobieram FakturyokresoweDAO");
            return fakturywystokresoweFacade.findAll(Fakturywystokresowe.class);
        } catch (Exception e) {
            return null;
        }
   }
    
    public List<Fakturywystokresowe> findPodatnik(String podatnik){
        List<Fakturywystokresowe> zwrot = new ArrayList<>();
        try {
            System.out.println("Pobieram FakturywystokresoweDAO");
            zwrot = fakturywystokresoweFacade.findPodatnikFaktury(podatnik);
        } catch (Exception e) {}
        return zwrot;
    }
    
     public List<Fakturywystokresowe> findPodatnik(String podatnik, String rok){
        List<Fakturywystokresowe> zwrot = new ArrayList<>();
        try {
            System.out.println("Pobieram POdatnik Rok FakturywystokresoweDAO");
            zwrot = fakturywystokresoweFacade.findPodatnikRokFaktury(podatnik, rok);
        } catch (Exception e) {}
        return zwrot;
    }

    public Fakturywystokresowe findOkresowa(Double brutto, String rok, String klientnip, String nazwapelna) {
        try {
            return fakturywystokresoweFacade.findOkresowa(brutto, rok, klientnip, nazwapelna);
        } catch (Exception e) {
            return null;
        }
    }
     public List<Fakturywystokresowe> findOkresoweOstatnie(String podatnik, String mc) {
        try {
            return fakturywystokresoweFacade.findOkresoweOstatnie(podatnik, mc);
        } catch (Exception e) {
            return null;
        }
    }
}
