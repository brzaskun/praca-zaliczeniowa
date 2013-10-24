/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package daoFK;

import dao.DAO;
import entityfk.Kontozapisy;
import java.io.Serializable;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Named;
import session.SessionFacade; 

/**
 *
 * @author Osito
 */
//@Named
public class KontoZapisyFKDAO {
//    @Inject
//    private SessionFacade kontozapisyFacade;
//
//    public KontoZapisyFKDAO() {
//        super(Kontozapisy.class);
//    }
//    
//     public  List<Kontozapisy> findAll(){
//        try {
//            System.out.println("Pobieram KontoZapisyFKDAO");
//            return kontozapisyFacade.findAll(Kontozapisy.class);
//        } catch (Exception e) {
//            return null;
//        }
//   }
//
//    public List<Kontozapisy> findZapisy(String numer) {
//        try {
//            System.out.println("Pobieram KontoZapisyFKDAO wg numeru");
//            return kontozapisyFacade.findZapisyNumer(numer); 
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public List<Kontozapisy> findZapisyKonto(String numerkonta) {
//        try {
//            System.out.println("Pobieram KontoZapisyFKDAO wg konta");
//            return kontozapisyFacade.findZapisyKonto(numerkonta); 
//        } catch (Exception e) {
//            return null;
//        }
//    }

//    public List<Kontozapisy> findZapisyKontoMacierzyste(String pelnynumer) {
//        List<Kontozapisy> zwrot = new ArrayList<>(); 
//        try {
//            System.out.println("Pobieram KontoZapisyFKDAO wg konta macierzystego");
//            List<Kontozapisy> worek = kontozapisyFacade.findAll(Kontozapisy.class);
//            for(Kontozapisy p : worek){
//                if(p.getKontoob().getMacierzyste().equals(pelnynumer)){ 
//                    zwrot.add(p);
//                }
//            }
//            return zwrot;
//        } catch (Exception e) {
//            return null;
//        }
//    }

    
}
