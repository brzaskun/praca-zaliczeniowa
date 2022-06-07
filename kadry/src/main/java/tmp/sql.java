/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tmp;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Osito
 */
public class sql {
//     public static void main(String[] args) {
//        EntityManagerFactory emfH2 = javax.persistence.Persistence.createEntityManagerFactory("WebApplication1PU");
//        EntityManager emH2 = emfH2.createEntityManager();
//        List<Miasto> miasto = emH2.createQuery("SELECT m.miaNazwa FROM Miasto m").getResultList();
//         System.out.println("");
//     }
     
      public static void main(String[] args) {
        EntityManagerFactory emfH2 = javax.persistence.Persistence.createEntityManagerFactory("PlatnikPU");
        EntityManager emH2 = emfH2.createEntityManager();
        //List<entityplatnik.ZUSDRA> miasto = emH2.createQuery("SELECT z FROM ZUSDRA z WHERE z.ii1Nip = :Nip AND z.i22okresdeklar = :okres").setParameter("Nip", "8511005008").setParameter("okres", "052022").getResultList();
        List<entityplatnik.ZUSDRA> miasto = emH2.createQuery("SELECT z FROM Zusdra z WHERE z.ii1Nip = :Nip").setParameter("Nip", "8511005008").getResultList();
        for (entityplatnik.ZUSDRA p : miasto) {
            System.out.println(p.toString());
        }
         
     }
}
