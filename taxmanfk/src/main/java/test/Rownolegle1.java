/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import entityfk.StronaWiersza;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Osito
 */
public class Rownolegle1 extends Thread {

    private EntityManager em;
    private StronaWiersza sw;

    
    public Rownolegle1(StronaWiersza sw) {
        this.sw = sw;
        this.start();
    }
    
    
    
    @Override
    public void run() {
//       this.sw.setMc("1");
//       this.sw.setRok("2");
    }
    
    private static List<Integer> zrobliste() {
        List<Integer> l = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 1000000; i++) {
            l.add(new Integer(i+i*3/5));
        }
        return l;
    }
      
    
//     public static void main(String[] args) throws Exception  {
//        EntityManager em = Em.getEm();
//        List<Object> podatnicy = em.createNamedQuery("Podatnik.findAll").getResultList();
//        List<Object> konta = em.createNamedQuery("Konto.findAll").getResultList();
//        for (Iterator<Object> it = podatnicy.iterator(); it.hasNext();) {
//            Podatnik p = (Podatnik) it.next();
//            if (p.getFirmafk() == 1) {
//                Rownolegle1 r = new Rownolegle1(p, konta);
//            }
//        }
//        error.E.s("zachowanie "+licz);
//    }
}
