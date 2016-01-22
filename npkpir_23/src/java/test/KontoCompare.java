/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import databaseDirect.ConnectToDatabase;
import entityfk.Konto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Singleton;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.Root;
import sortfunction.KontonumerComparator;

/**
 *
 * @author Osito
 */

public class KontoCompare implements Serializable {
      
//      public static List<Konto> findAllKontos() {
//        javax.persistence.criteria.CriteriaQuery cq = ConnectToDatabase.getEntityManager().getCriteriaBuilder().createQuery();
//        Root<Konto> Konto_ = cq.from(Konto.class);
//        cq.select(Konto_);
//        cq.where(Konto_.get("podatnik").in("MATMAX SYSTEMY SPRZĄTAJĄCE SP Z O.O. SP. K."));
//        List<Konto> wszystkieKontoy = ConnectToDatabase.getEntityManager().createQuery(cq).getResultList();
//        return wszystkieKontoy;
//    }
//      
//      public static void main(String[] args) {
//          List<Konto> listazbazy = findAllKontos();
//          Collections.sort(listazbazy, new KontonumerComparator());
//          for (Konto p : listazbazy) {
//              System.out.println(p.getPelnynumer());
//          }
//      }
    
}
