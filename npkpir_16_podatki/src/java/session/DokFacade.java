/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import entity.Dok;
import javax.ejb.Stateless;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class DokFacade extends AbstractFacade<Dok> {
    @PersistenceContext(unitName = "npkpir_PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public DokFacade() {
        super(Dok.class);
    }
    
    public Dok duplicat(Dok selD) throws Exception{
        Dok wynik = null;
        try {
        wynik = (Dok) em.createNamedQuery("Dok.findDuplicate").setParameter("kontr",selD.getKontr()).setParameter("nrWlDk",selD.getNrWlDk()).setParameter("kwota",selD.getKwota()).getSingleResult();
        } catch (Exception e){
            System.out.println("Nie znaleziono duplikatu - DokFacade");
            return null;
        }
            System.out.println("Znaleziono duplikat - DokFacade");
            return wynik;
        }
    
//      public Dok poprzednik(Integer rok, Integer mc) throws Exception{
//        String mcS;
//        if(mc<9){
//            mcS="0"+mc;
//        } else {
//            mcS = String.valueOf(mc);
//        }
//        Dok wynik = null;
//        try {
//        wynik = (Dok) em.createNamedQuery("Dok.findPoprzednik").setParameter("pkpirR",rok).setParameter("pkpirM",mcS).setParameter("opis","umorzenie za miesiac").getSingleResult();
//        } catch (Exception e){
//            System.out.println("Nie znaleziono duplikatu - DokFacade");
//            return null;
//        }
//            System.out.println("Znaleziono poprzednika - DokFacade");
//            return wynik;
//        }
//   
}
