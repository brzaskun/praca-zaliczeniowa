/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import em.Em;
import entity.Podatnik;
import entityfk.Konto;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author Osito
 */
public class Rownolegle extends Thread {

    private EntityManager em;
    private Podatnik p;
    private static int licz;
    private List<Object> konta;
    
    public Rownolegle(Podatnik p, List<Object> konta) {
        this.p = p;
        this.licz = 1;
        this.konta = konta;
        this.start();
    }
    
    
    
    @Override
    public void run() {
        for (Object rx : konta) {
            Konto r = (Konto) rx;
            if (r.getPodatnik().equals(p.getNazwapelna())) {
                licz++;
            }
        }
//        em = Em.getEm();
//        List<Konto> l = em.createNamedQuery("Konto.findByPodatnik").setParameter("podatnik", p.getNazwapelna()).setParameter("rok", 2016).getResultList();
//        if (l != null) {
            //konta.addAll(l);
//            licz++;
//            error.E.s("dodane "+p.getNazwapelna());
//        }
    }
      private static List<Integer> zrobliste() {
        List<Integer> l = Collections.synchronizedList(new ArrayList<>());
        for (int i = 0; i < 1000000; i++) {
            l.add(i+i*3/5);
        }
        return l;
    }
      
    
     public static void main(String[] args) throws Exception  {
        EntityManager em = Em.getEm();
        List<Object> podatnicy = em.createNamedQuery("Podatnik.findAll").getResultList();
        List<Object> konta = em.createNamedQuery("Konto.findAll").getResultList();
        for (Iterator<Object> it = podatnicy.iterator(); it.hasNext();) {
            Podatnik p = (Podatnik) it.next();
            if (p.getFirmafk() == 1) {
                Rownolegle r = new Rownolegle(p, konta);
            }
        }
    }
}
