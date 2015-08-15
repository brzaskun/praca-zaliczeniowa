/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import entity.Dok;
import entity.Strona;
import entity.Transakcja;
import entity.Wiersz;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;

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
        Dok dok1 = new Dok(listanazw.get(1));
        Wiersz w1 = new Wiersz(listanazw.get(1));
        w1.setDok(dok1);
        dok1.setWiersz(w1);
        Strona s1 = new Strona(listanazw.get(1));
        s1.setWiersz(w1);
        dok1.getWiersz().setStrona(s1);
        zachowaj(dok1);
        p(dok1);
        Dok dok2 = new Dok(listanazw.get(2));
        Wiersz w2 = new Wiersz(listanazw.get(2));
        w2.setDok(dok2);
        dok2.setWiersz(w2);
        Strona s2 = new Strona(listanazw.get(2));
        s2.setWiersz(w2);
        dok2.getWiersz().setStrona(s2);
        zachowaj(dok2);
        p(dok2);
        Dok dok1t = (Dok) pobierz(Dok.class, 1);
        Strona st = (Strona) pobierz(Strona.class, 2);
        Transakcja t1 = new Transakcja("jeden-dwa");
        t1.setNowaTransakcja(dok1t.getWiersz().getStrona());
        t1.setRozliczajacy(st);
        dok1t.getWiersz().getStrona().getNowetransakcje().add(t1);
        st.getPlatnosci().add(t1);
        edytuj(dok1t);
        System.out.println("zachowano");
        Dok domodyfikacji = (Dok) pobierz(Dok.class, 1);
        Dok niezmieniony = (Dok) pobierz(Dok.class, 2);
        domodyfikacji.setWiersz(null);
        //edytuj(domodyfikacji);
        niezmieniony = (Dok) pobierz(Dok.class, 2);
        System.out.println("koniec");
        List<Object> dokumenty = pobierzWszystkieDok(Dok.class);
        Strona st2 = (Strona) pobierz(Strona.class, 2);
        System.out.println("pobrano dok");
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
    
    public static Object pobierz(Class c, Object id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Object ret = em.find(c, id);
        //em.refresh(ret);
        em.getTransaction().commit();
        em.clear();
        return ret;
    }
    
     public static List<Object> pobierzWszystkieDok(Class c) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        CriteriaQuery<Object> criteria = em.getCriteriaBuilder().createQuery(c);
        criteria.select(criteria.from(c));
        List<Object> lista = em.createQuery(criteria).getResultList();
        em.getTransaction().commit();
        em.clear();
        return lista;
    }
    
    public static void zachowaj(Object o) {
        System.out.println("Utrwalam "+o.getClass().getName());
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        em.clear();
    }
    
    public static void edytuj(Object o) {
        System.out.println("Edytuje "+o.getClass().getName());
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.merge(o);
        em.getTransaction().commit();
        em.clear();
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
