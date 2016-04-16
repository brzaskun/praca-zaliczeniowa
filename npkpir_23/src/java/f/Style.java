/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package f;

import entity.Odsetki;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Osito
 */
public class Style {
     public static void main(String[] args)  {
        EntityManagerFactory emfH2 = javax.persistence.Persistence.createEntityManagerFactory("fkKonto1");
        EntityManager emH2 = emfH2.createEntityManager();
        List<Odsetki> resultList = emH2.createQuery("SELECT o FROM Odsetki o").getResultList();
        for (Odsetki p : resultList) {
            System.out.println(p);
        }
        System.out.println("e");
    }
}
