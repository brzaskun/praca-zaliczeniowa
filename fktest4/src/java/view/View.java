/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import entity.Dok;
import entity.Strona;
import entity.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
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
        Dok dok1 = stworzjedendokument();
        p(utrwalam(dok1));
        licznik++;
        Dok dok2 = stworzjedendokument();
        p(utrwalam(dok2));
        p("--------------");
        p("Stwarzam strone");
        stworzStrone(dok1, dok2);
        edytuje(dok1);
        p("--------------");
        p("dokumenty z bazy");
        List<Dok> listdok = findAllDokuments();
        for (Dok p : listdok) {
            printDok(p);
        }
//        p("--------------");
//        p("usuwam strone");
//        p(usuwam(dok1.getWiersz(0).getStronaN(0)));
//        edytuje(dok1);
//        p("--------------");
//        p("edytuje wiersz");
//        dok1.getWiersz(0).setNazwa("ZMIANA");
//        edytuje(dok1);
        p("--------------");
        p("dokumenty z bazy");
        listdok = findAllDokuments();
        for (Dok p : listdok) {
            printDok(refreshDokument(p));
        }
        p("--------------");
        p("usuwam WIERSZ");
        p(usuwam(dok1.getWiersz(0)));
        edytuje(dok1);
//        p("--------------");
//        p("dokumenty z bazy");
//        listdok = findAllDokuments();
//        for (Dok p : listdok) {
//            printDok(refreshDokument(p));
//        }
    }
    
    private static Dok stworzjedendokument() {
        Dok dok = new Dok(listanazw.get(licznik));
        dok.setWiersz(new Wiersz(listanazw.get(licznik), dok));
        dok.setWiersz(new Wiersz(listanazw.get(licznik+1), dok));
        printDok(dok);
        return dok;
    }
    
    private static void stworzStrone(Dok dok1, Dok dok2) {
        Strona strona = new Strona(listanazw.get(licznik), dok1.getWiersz(0), dok2.getWiersz(1));
        dok1.getWiersz(0).setStronaN(strona);
        dok2.getWiersz(1).setStronaN(strona);
    }
    
    private static void printDok(Dok dok) {
        p("--------------");
        p(dok);
        p(dok.getWiersz(0));
        p(dok.getWiersz(1));
        try {
            p(dok.getWiersz(0).getStronaN(0));
            p(dok.getWiersz(1).getStronaN(0));
        } catch (Exception e) {
            
        }
        p("--------------");
    }
    
    public static Dok refreshDokument (Dok p) {
        EntityManager em = getEntityManager();
        Dok refreshowany = em.find(Dok.class, p.getId());
        em.refresh(refreshowany);
        return refreshowany;
    }
  
    
     public static List<Dok> findAllDokuments() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Dok.class));
        EntityManager em = getEntityManager();
        List<Dok> wszystkieDokumenty = getEntityManager().createQuery(cq).getResultList();
        List<Dok> refreshowane = new ArrayList<>();
        //for (Dokument p : wszystkieDokumenty) {
        //    refreshowane.add(refreshDokument(p));
        //}
        //return refreshowane;
        return wszystkieDokumenty;
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
