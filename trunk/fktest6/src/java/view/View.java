/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import entity.Dok;
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
        //Wersja z jednym Rozrachuenk
//        Dok dok1 = stworzjedendokument();
//        p(utrwalam(dok1));
//        licznikDok++;
//        Dok dok2 = stworzjedendokument();
//        p(utrwalam(dok2));
//        p("--------------"); 
//        p("Stwarzam rachunek, platnosc");
        Wiersz wiersz = new Wiersz("wierszPierwszy", listanazw.get(licznikWiersz));
        utrwalam(wiersz);
        Wiersz wiersz2 = new Wiersz("wierszDrugi", listanazw.get(licznikWiersz+1));
        utrwalam(wiersz2);
        Wiersz wiersz3 = new Wiersz("wierszTrzeci", listanazw.get(licznikWiersz+2));
        utrwalam(wiersz3);
        p("Stwarzam tansakcje");
        Transakcja t = stworzTransakcje(wiersz.getRachunek(), wiersz2.getRachunek(), 1000);
        p(t);
        p("--------------"); 
        p("Stwarzam tansakcje t2");
        edytuje(t);
        Transakcja t1 = stworzTransakcje(wiersz3.getRachunek(), wiersz.getRachunek(), 4000);
        p(t1);
        p("--------------"); 
        edytuje(wiersz);
        
//        Platnosc pbaza = znajdzPlatnosc(wiersz2.getPlatnosc());
//        //pobieram i edytuje rozrachunki z transakcji sparowanych
//        edytuje(pbaza);
//        p(pbaza.toString());
//        Rachunek rbaza = znajdzRachunek(wiersz.getRachunek());
//        boolean zgodne = rbaza.equals(wiersz.getRachunek());
//        refresh2(rbaza);
//        zgodne = rbaza.equals(wiersz.getRachunek());
//        p("zgodne : "+zgodne);
//        p(rbaza);
//        p("-------------- usuwam "); 
//        usuwam(wiersz);
        //usuwam(r);
//        p("-------------- platnosc");
//        Platnosc pbaza2 = znajdzPlatnosc(pbaza);
//        refresh2(pbaza2);
//        usuwam(pbaza2);
//        Rachunek rbaza2 = znajdzRachunek(rbaza);
//        p(rbaza2);
//        usuwam(rbaza2);
//        Platnosc pbaza3 = znajdzPlatnosc(pbaza2);
//        p(pbaza3);
         List<Wiersz> listwiersz = findAllWiersz();
        for (Wiersz s : listwiersz) {
            printWiersz(s);  
        }
//        p("-------------- usuwam wiersz");
//        Wiersz wierszbaza = znajdzWiersz(wiersz2);
//        refresh2(wierszbaza);
//        //p(rbaza);
//        usuwam(wierszbaza);
        //rbaza2 = znajdzRachunek(rbaza);
        //p(rbaza);
        //usuwam(rbaza2);
//        Platnosc pbaza3 = znajdzPlatnosc(pbaza);
//        //refresh2(pbaza);
//        p(pbaza3);
        //edytuje(r);
        //edytuje(p);
//        edytuje(dok1);
//        //edytuje(dok2.getWiersz(1));
        //edytuje(dok2.getWiersz(1));// to jest zbedne jak robie refresh powiazanych dokumentow!!!!
        p("po usunieciu wiersza --------------");
//        p("dokumenty z bazy");
        List<Wiersz> listwiersz2 = findAllWiersz();
        for (Wiersz s : listwiersz2) {
            printWiersz(s);  
        }
        
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
     
      
    public static Rachunek znajdzRachunek(Rachunek p) {
        try {
            EntityManager em = getEntityManager();
            Rachunek odnalezionyDokument = em.find(Rachunek.class, p.getIdR());
            return odnalezionyDokument;
        } catch (Exception e) {
            return null;
        }   
    }
    
    public static Wiersz znajdzWiersz(Wiersz p) {
        try {
            EntityManager em = getEntityManager();
            Wiersz odnalezionyDokument = em.find(Wiersz.class, p.getIdWiersz());
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
    
    private static Transakcja stworzTransakcje(Rachunek r, Rachunek p, double kwota) {
        Transakcja transakcja = new Transakcja(r,p,kwota);
        r.setTransakcjeR(transakcja);
        p.setTransakcjeP(transakcja);
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
//                p(dok.getWiersz(0).getRachunek().getTransakcjeR(0));
//            }
//            if (dok.getWiersz(0).getPlatnosc() != null) {
//                p(dok.getWiersz(0).getPlatnosc().getTransakcjeR(0));
//            }
//            if (dok.getWiersz(1).getRachunek() != null) {
//                p(dok.getWiersz(1).getRachunek().getTransakcjeR(0));
//            }
//            if (dok.getWiersz(1).getPlatnosc() != null) {
//                p(dok.getWiersz(1).getPlatnosc().getTransakcjeR(0));
//            }
//        } catch (Exception e) {
//        }
//        p("--------------");
//    }
    
    private static void printWiersz(Wiersz wiersz) {
        p("<---------------drukowanie wiersza");
        p(wiersz);
        try {
            p(wiersz.getRachunek());
        } catch (Exception e) {
        }
        try {
            if (wiersz.getRachunek() != null) {
                if (!wiersz.getRachunek().getTransakcjeR().isEmpty()){
                    p(wiersz.getRachunek().getTransakcjeR(0));
                }
                if (!wiersz.getRachunek().getTransakcjeP().isEmpty()){
                    p(wiersz.getRachunek().getTransakcjeP(0));
                }
            }
        } catch (Exception e) {
        }
        p("koniec drukowania wiersza-------------->");
    }
    
//    public static Dok refreshDokument (Dok p) {
//        EntityManager em = getEntityManager();
//        Dok refreshowany = em.find(Dok.class, p.getIdDok());
//        em.refresh(refreshowany);
//        return refreshowany;
//    }
    
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
     
     public static List<Wiersz> findAllWiersz() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Wiersz.class));
        EntityManager em = getEntityManager();
        List<Wiersz> wszystkieDokumenty = getEntityManager().createQuery(cq).getResultList();
        //List<Dok> refreshowane = new ArrayList<>();
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

    private static Rachunek stworzRachunek(Wiersz wiersz) {
        Rachunek r = new Rachunek("Rachunek "+listanazw.get(licznikWiersz));
        r.setWierszR(wiersz);
        wiersz.setRachunek(r);
        return r;
    }

   
}
