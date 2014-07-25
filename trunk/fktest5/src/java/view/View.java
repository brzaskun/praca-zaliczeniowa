/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import entity.Dok;
import entity.Platnosc;
import entity.Rachunek;
import entity.Transakcja;
import entity.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.LockModeType;
import javax.persistence.Persistence;

/**
 *
 * @author Osito
 */
@Stateless
public class View implements Serializable{
    private static int licznikDok;
    private static int licznikWiersz;
    private static final EntityManagerFactory emFactory;
    private static HashMap<Integer, String> listanazw;

    static {
        licznikDok = 1;
        licznikWiersz = 1;
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
//        Dok dok1 = stworzjedendokument();
//        p(utrwalam(dok1));
//        licznikDok++;
//        Dok dok2 = stworzjedendokument();
//        p(utrwalam(dok2));
//        p("--------------"); 
//        p("Stwarzam rachunek, platnosc");
        Rachunek r = stworzRachunek();
        p(r.toString());
        utrwalam(r);
        Platnosc p = stworzPlatnosc();
        p(p.toString());
        utrwalam(p);
        p("Stwarzam tansakcje");
        Transakcja t = stworzTransakcje(r, p, 1000);
        p(t);
        edytuje(t);
        Platnosc pbaza = znajdzPlatnosc(p);
        edytuje(pbaza);
        p(pbaza.toString());
        Rachunek rbaza = znajdzRachunek(r);
        p(rbaza);
        p("-------------- usuwam ");
//        usuwam(rbaza);
//        p("-------------- platnosc");
//        Platnosc pbaza2 = znajdzPlatnosc(pbaza);
//        refresh2(pbaza2);
//        usuwam(pbaza2);
//        Rachunek rbaza2 = znajdzRachunek(rbaza);
//        p(rbaza2);
//        usuwam(rbaza2);
//        Platnosc pbaza3 = znajdzPlatnosc(pbaza2);
//        p(pbaza3);
        p("-------------- rachunek");
        //Rachunek rbaza2 = znajdzRachunek(rbaza);
       refresh2(rbaza);
        p(rbaza);
        usuwam(rbaza);
        //rbaza2 = znajdzRachunek(rbaza);
        p(rbaza);
        //usuwam(rbaza2);
        Platnosc pbaza3 = znajdzPlatnosc(pbaza);
        p(pbaza3);
        //edytuje(r);
        //edytuje(p);
//        edytuje(dok1);
//        //edytuje(dok2.getWiersz(1));
        //edytuje(dok2.getWiersz(1));// to jest zbedne jak robie refresh powiazanych dokumentow!!!!
//        p("--------------");
//        p("dokumenty z bazy");
//        List<Dok> listdok = findAllDokuments();
//        for (Dok s : listdok) {
//            printDok(refreshDokument(s));  
//        }
        
//      p("--------------");
//      p("usuwam strone");
        //refresh2(dok1);
//        p(usuwam(dok1.getWiersz(0).getStronaN(0)));
//        edytuje(dok1);
//        edytuje(dok2);
//        p("--------------");
//        p("edytuje wiersz");
//        dok1.getWiersz(0).setNazwa("ZMIANA");
//        edytuje(dok1);
//        Dok dok1baza = znajdzDokument(dok1);
//        p("--------------");
//        p("usuwam wiersz");
//        Wiersz wierszdousuniecia = null;
//        Iterator it = dok1baza.getWierszDok().iterator();
//        while (it.hasNext()) {
//            Wiersz w = (Wiersz) it.next();
//            if (w.getNazwaWiersz().equals("Wiersz pierwszy-")) {
//                it.remove();
//            }
//        }
//        edytuje(dok1baza);
//        p("--------------");
//        p("dokumenty z bazy");
//        listdok = findAllDokuments();
//        for (Dok p : listdok) {
//            printDok(refreshDokument(p));
//        }
//        p("--------------");
//        p("usuwam Dokument");
//        p(usuwam(dok1baza));
//        p("--------------");
//        p("dokumenty z bazy");
//        listdok  = findAllDokuments();
//        for (Dok z : listdok) {
//            printDok(refreshDokument(z));
//        }
    }
//    
//     public static Dok znajdzDokument(Dok dokument) {
//        try {
//            EntityManager em = getEntityManager();
//            Dok odnalezionyDokument = em.find(Dok.class, dokument.getIdDok());
//            return odnalezionyDokument;
//        } catch (Exception e) {
//            return null;
//        }   
//    }
     
    public static Platnosc znajdzPlatnosc(Platnosc p) {
        try {
            EntityManager em = getEntityManager();
            Platnosc odnalezionyDokument = em.find(Platnosc.class, p.getIdP());
            return odnalezionyDokument;
        } catch (Exception e) {
            return null;
        }   
    }
    
    public static Rachunek znajdzRachunek(Rachunek p) {
        try {
            EntityManager em = getEntityManager();
            Rachunek odnalezionyDokument = em.find(Rachunek.class, p.getIdR());
            return odnalezionyDokument;
        } catch (Exception e) {
            return null;
        }   
    }
    
//    private static Dok stworzjedendokument() {
//        Dok dok = new Dok(listanazw.get(licznikDok));
//        dok.setWiersz(new Wiersz(listanazw.get(licznikWiersz++), dok));
//        dok.setWiersz(new Wiersz(listanazw.get(licznikWiersz++), dok));
//        printDok(dok);
//        return dok;
//    }
    
    private static Transakcja stworzTransakcje(Rachunek r, Platnosc p, double kwota) {
        Transakcja transakcja = new Transakcja(r,p,kwota);
        r.setTransakcje(transakcja);
        p.setTransakcje(transakcja);
        return transakcja;
    }
    
//    private static void printDok(Dok dok) {
//        p("--------------");
//        p(dok);
//        p(dok.getWiersz(0));
//        p(dok.getWiersz(1)); 
//        try {
//            p(dok.getWiersz(0).getRachunek());
//            p(dok.getWiersz(0).getPlatnosc());
//            p(dok.getWiersz(1).getRachunek());
//            p(dok.getWiersz(1).getPlatnosc());
//        } catch (Exception e) {
//        }
//        try {
//            if (dok.getWiersz(0).getRachunek() != null) {
//                p(dok.getWiersz(0).getRachunek().getTransakcje(0));
//            }
//            if (dok.getWiersz(0).getPlatnosc() != null) {
//                p(dok.getWiersz(0).getPlatnosc().getTransakcje(0));
//            }
//            if (dok.getWiersz(1).getRachunek() != null) {
//                p(dok.getWiersz(1).getRachunek().getTransakcje(0));
//            }
//            if (dok.getWiersz(1).getPlatnosc() != null) {
//                p(dok.getWiersz(1).getPlatnosc().getTransakcje(0));
//            }
//        } catch (Exception e) {
//        }
//        p("--------------");
//    }
//    
//    public static Dok refreshDokument (Dok p) {
//        EntityManager em = getEntityManager();
//        Dok refreshowany = em.find(Dok.class, p.getIdDok());
//        em.refresh(refreshowany);
//        return refreshowany;
//    }
//    
     public static void refresh2 (Object p) {
        EntityManager em = getEntityManager();
        em.refresh(em.merge(p));
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
            p("Nieudane usuniecie  "+e.toString());
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
            p("utrwalono");
            return 0;
        } catch (Exception e) {
            p("Nieudane persist "+obj.toString());
            return 1;
        }
    }
    
    
    
    public static void p(Object p) {
        if (p != null) {
           System.out.println("-------------------");
           System.out.println(p);
        }
    }
  //      public static void main(String[] args) {
//        System.out.println("Start funkcji");
//        Dok dok = (Dok) utrwalam(1, "Dok");
//        p(dok);
//        Wiersz wiersz = (Wiersz) utrwalam(1, "Wiersz");
//        p(wiersz);
//        Rachunek strona = (Rachunek) utrwalam(1, "Rachunek");
//        p(strona);
//        Rozrachunek rozrachunek = (Rozrachunek) utrwalam(1, "Rozrachunek");
//        p(rozrachunek);
//        Transakcja transakcja = (Transakcja) utrwalam(1, "Transakcja");
//        p(transakcja);
//        p(usuwam(dok));
//        p(usuwam(wiersz));
//        p(usuwam(strona));
//        p(usuwam(rozrachunek));
//        p(usuwam(transakcja))
//        System.out.println("Koniec funkcji");
//    
//    }

    private static Rachunek stworzRachunek() {
        Rachunek r = new Rachunek("Rachunek "+listanazw.get(licznikWiersz));
        return r;
    }

    private static Platnosc stworzPlatnosc() {
        Platnosc p = new Platnosc("Platnosc "+listanazw.get(licznikWiersz));
        return p;
    }

}
