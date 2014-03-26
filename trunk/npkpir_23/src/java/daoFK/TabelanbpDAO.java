/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Tabelanbp;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import org.joda.time.DateTime;
import session.SessionFacade;

/**
 *
 * @author Osito
 */
@Named
public class TabelanbpDAO extends DAO implements Serializable {
    
    @Inject
    private SessionFacade tabelanbpFacade;

    public TabelanbpDAO() {
        super(Tabelanbp.class);
    }
    
    public  List<Tabelanbp> findAll(){
        try {
            return tabelanbpFacade.findAll(Tabelanbp.class);
        } catch (Exception e) {
            return null;
        }
   }
    public  List<Tabelanbp> findKursyRok(){
        try {
            DateTime dzisiejszadata = new DateTime();
            String rok = String.valueOf(dzisiejszadata.getYear());
            List<Tabelanbp> kursyrok = tabelanbpFacade.findAll(Tabelanbp.class);
            List<Tabelanbp> nowalista = new ArrayList<>();
            Iterator it = kursyrok.iterator();
            while (it.hasNext()) {
                Tabelanbp p = (Tabelanbp) it.next();
                String rokp = p.getDatatabeli().substring(0,4);
                if (rokp.equals(rok)) {
                    nowalista.add(p);
                }
            }
            return nowalista;
        } catch (Exception e) {
            return null;
        }
   }
   
    public List<Tabelanbp> findLast(){
        try {
            return tabelanbpFacade.findXLast(Tabelanbp.class,1);
        } catch (Exception e) {
            return null;
        }
    }

    public Tabelanbp findByDateWaluta(String doprzekazania, String nazwawaluty) {
         try {
            return tabelanbpFacade.findByDateWaluta(doprzekazania, nazwawaluty);
        } catch (Exception e) {
            return null;
        }
    }
}
