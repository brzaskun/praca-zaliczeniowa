/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import entity.Dok;
import entity.Rozrachunek;
import entity.Strona;
import entity.Transakcja;
import entity.Wiersz;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Osito
 */
@Stateless
public class View implements Serializable{
    private static int licznik;
    private static final EntityManagerFactory emFactory;
    private static HashMap<Integer, String> listanazw;

    static {
        licznik = 1;
        emFactory = Persistence.createEntityManagerFactory("fktest2PUStatic");
        listanazw = new HashMap<>();
        listanazw.put(1, "pierwszy-");
        listanazw.put(2, "drugi-");
        listanazw.put(3, "trzeci-");
        listanazw.put(4, "czwarty-");
        listanazw.put(5, "piąty-");
        listanazw.put(6, "szósty-");
        listanazw.put(7, "siódmy-");
    }

    public static EntityManager getEntityManager() {
        EntityManager em = emFactory.createEntityManager();
        return em;
    }
    
    
   
    public static void main(String[] args) {
        Dok dok = new Dok(listanazw.get(1));
        dok.setWiersz(new Wiersz(listanazw.get(1), dok));
        dok.setWiersz(new Wiersz(listanazw.get(2), dok));
        dok.getWiersz().setStrona(new Strona(listanazw.get(1), dok.getWiersz()));
        dok.getWiersz().getStrona().setRozrachunek(new Rozrachunek(listanazw.get(1), dok.getWiersz().getStrona()));
        dok.getWiersz().getStrona().getRozrachunek().setTransakcja(new Transakcja(listanazw.get(1), dok.getWiersz().getStrona().getRozrachunek()));
        p(dok);
        p(dok.getWiersz());
        p(dok.getWiersz(1));
        p(dok.getWiersz().getStrona());
        p(dok.getWiersz().getStrona().getRozrachunek());
        p(dok.getWiersz().getStrona().getRozrachunek().getTransakcja());
        p("--------------");
        p(utrwalam(dok));
        p("--------------");
        dok.getWiersz().getStrona().setNazwa("lolo");
        edytuje(dok.getWiersz().getStrona());
        p(usuwam(dok.getWiersz().getStrona().getRozrachunek().getTransakcja()));
    }
    
   
    private static int usuwam(Object obj) {
        try {
            System.out.println("Usuwam "+obj.toString());
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(obj));
            em.getTransaction().commit();
            em.clear();
            return 0;
        } catch (Exception e) {
            p("Nieudane usuniecie "+obj.toString());
            return 1;
        }
    }
    
     private static int edytuje(Object obj) {
        try {
            System.out.println("Edytuje "+obj.toString());
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            em.merge(obj);
            em.getTransaction().commit();
            em.clear();
            return 0;
        } catch (Exception e) {
            p("Nieudana edycja "+obj.toString());
            return 1;
        }
    }
    
    private static int utrwalam(Object obj) {
        try {
            System.out.println("Utrwalam "+obj.toString());
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            em.persist(obj);
            em.getTransaction().commit();
            em.clear();
            return 0;
        } catch (Exception e) {
            p("Nieudane persist "+obj.toString());
            return 1;
        }
    }
    
    
    
    public static void p(Object p) {
        if (p != null) {
           System.out.println(p);
        }
    }
  //      public static void main(String[] args) {
//        System.out.println("Start funkcji");
//        Dok dok = (Dok) utrwalam(1, "Dok");
//        p(dok);
//        Wiersz wiersz = (Wiersz) utrwalam(1, "Wiersz");
//        p(wiersz);
//        Strona strona = (Strona) utrwalam(1, "Strona");
//        p(strona);
//        Rozrachunek rozrachunek = (Rozrachunek) utrwalam(1, "Rozrachunek");
//        p(rozrachunek);
//        Transakcja transakcja = (Transakcja) utrwalam(1, "Transakcja");
//        p(transakcja);
//        p(usuwam(dok));
//        p(usuwam(wiersz));
//        p(usuwam(strona));
//        p(usuwam(rozrachunek));
//        p(usuwam(transakcja));
//        System.out.println("Koniec funkcji");
//    
//    }

}
