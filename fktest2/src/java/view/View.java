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
        dok.setWiersz(new Wiersz(listanazw.get(1)));
        dok.getWiersz().setStrona(new Strona(listanazw.get(1)));
        dok.getWiersz().getStrona().setRozrachunek(new Rozrachunek(listanazw.get(1)));
        dok.getWiersz().getStrona().getRozrachunek().setTransakcja(new Transakcja(listanazw.get(1)));
        p(dok);
        p(dok.getWiersz());
        p(dok.getWiersz().getStrona());
        p(dok.getWiersz().getStrona().getRozrachunek());
        p(dok.getWiersz().getStrona().getRozrachunek().getTransakcja());
        p(usunDokument(dok));
    }
    
    public static int usunDokument(Dok dokument) {
         try {
            System.out.println("Usuwam Dokument");
            EntityManager em = getEntityManager();
            em.getTransaction().begin();    
            em.remove(em.merge(dokument));
            em.getTransaction().commit();
            em.clear();
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }
    public static Object utrwalam(int i, String klasa) {
        try {
            System.out.println("Utrwalam "+listanazw.get(i));
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            Object nowyObject = null;
            switch (klasa) {
                case "Dok":
                    nowyObject = new Dok(listanazw.get(i));
                    break;
                case "Wiersz":
                    nowyObject = new Wiersz(listanazw.get(i));
                    break;
                case "Strona":
                    nowyObject = new Strona(listanazw.get(i));
                    break;
                case "Rozrachunek":
                    nowyObject = new Rozrachunek(listanazw.get(i));
                    break;
                case "Transakcja":
                    nowyObject = new Transakcja(listanazw.get(i));
                    break;
            }
            em.persist(nowyObject);
            em.getTransaction().commit();
            em.clear();
            return nowyObject;
        } catch (Exception e) {
            p("Nieudane tworzenie "+klasa);
            return null;
        }
    }
    
     public static Object stwarzam(String klasaobj1, int i) {
        try {
            System.out.println("Tworze object klasa "+klasaobj1+" "+listanazw.get(i));
            Object obj1 = null;
            switch (klasaobj1) {
                case "Dok":
                    obj1 = new Dok(listanazw.get(i));
                    break;
                case "Wiersz":
                    obj1 = new Wiersz(listanazw.get(i));
                    break;
                case "Strona":
                    obj1 = new Strona(listanazw.get(i));
                    break;
                case "Rozrachunek":
                    obj1 = new Rozrachunek(listanazw.get(i));
                    break;
                case "Transakcja":
                    obj1 = new Transakcja(listanazw.get(i));
                    break;
            }
            return obj1;
        } catch (Exception e) {
            p("Nieudane tworzenie "+klasaobj1);
            return null;
        }
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
