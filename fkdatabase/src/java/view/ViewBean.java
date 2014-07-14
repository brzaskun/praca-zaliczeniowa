/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import entity.Dokument;
import entity.Rozrachunek;
import entity.Transakcja;
import entity.TransakcjaPK;
import entity.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.Cache;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author Osito
 */
@Stateless
public class ViewBean implements Serializable{
    
    private static int licznik;
    private static final EntityManagerFactory emFactory;
    private static HashMap<Integer, String> listanazw;

    static {
        licznik = 1;
        emFactory = Persistence.createEntityManagerFactory("fkdatabasePUStatic");
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
        System.out.println("Start funkcji");
        petlausunWszystko();
        //petlaDwaDokumenty();
        //petlaTworzenieTransakcji();
        //petlaUsuwanieTransakcji();
        //petlaUsunWiersz();
        //petlaUsunRozrachunek();
        Dokument[] doktab = DokumentWiersz_i_PotemRozrachunkiOrazTransakcja();
        wydruk(doktab[0]);
        wydruk(doktab[1]);
        //wydruk(doktab[2]);
        //Wiersz wiersz = doktab[1].getWierszelista().get(0);
        //doktab[0].getWierszelista().remove(wiersz);
        p("-----------------");
        //p("Usuwam "+wiersz.getRozrachunekMa());
        //wiersz.getRozrachunki().remove(wiersz.getRozrachunekMa());
        //edytujDokument(doktab[1]);
        //usunDokument(doktab[0]);
        List<Dokument> pobraneZBazyPoZmianach = findAllDokuments();
        for (Dokument p : pobraneZBazyPoZmianach) {
            wydruk(p);
        }
        //Dokument refreshowany = refreshDokument(doktab[1]);;
        //wydruk(refreshowany);
        System.out.println("Koniec funkcji");
    }
    
    public static List<Dokument> findAllDokuments() {
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Dokument.class));
        EntityManager em = getEntityManager();
        List<Dokument> wszystkieDokumenty = getEntityManager().createQuery(cq).getResultList();
        List<Dokument> refreshowane = new ArrayList<>();
        for (Dokument p : wszystkieDokumenty) {
            refreshowane.add(refreshDokument(p));
        }
        return refreshowane;
        //return wszystkieDokumenty;
    }
    public static Dokument refreshDokument (Dokument p) {
        EntityManager em = getEntityManager();
        Dokument refreshowany = em.find(Dokument.class, p.getId());
        em.refresh(refreshowany);
        return refreshowany;
    }
    
    public static void petlaUsunRozrachunek() {
        Rozrachunek rozrachunek = znajdzRozrachunek("drugi-rozrachunek-Ma");
        int wynik  = usunNowyRozrachunek(rozrachunek);
        if (wynik == 0) {
            System.out.println("Usunieto wiersz "+rozrachunek.toString());
        }
    }
    
    public static void petlaUsunWiersz() {
        Wiersz wiersz = znajdzWiersz();
        int wynik  = usunNowyWiersz(wiersz);
        if (wynik == 0) {
            System.out.println("Usunieto wiersz "+wiersz.toString());
        }
    }
    
    public static void petlausunWszystko() {
        List<Dokument> dokumenty = znajdzDokumenty();
        for (Dokument d : dokumenty) {
            usunDokument(d);
        }
    }
    
    public static List<Dokument> znajdzDokumenty() {
        EntityManager em = getEntityManager();
        List<Dokument> znalezionedokumenty = new ArrayList<>();
        javax.persistence.criteria.CriteriaQuery cq = getEntityManager().getCriteriaBuilder().createQuery();
        cq.select(cq.from(Dokument.class));
        znalezionedokumenty.addAll(em.createQuery(cq).getResultList());
        return znalezionedokumenty;
    }
    
    public static void petlaDwaDokumenty() {
        petlaTworzenieDokumentu();
        licznik++;
        petlaTworzenieDokumentu();
    }
    
    public static void petlaTworzenieTransakcji() {
        Transakcja transkacja = utrwalNowaTransakcja();
        if (transkacja != null) {
            System.out.println("Utworzono nowa transakcja "+transkacja.toString());
        }
    }
    
    public static void petlaTworzenieRozrachnkow() {
        Transakcja transkacja = utrwalNowaTransakcja();
        if (transkacja != null) {
            System.out.println("Utworzono nowa transakcja "+transkacja.toString());
        }
    }
    
    public static void petlaUsuwanieTransakcji() {
        Transakcja transakcja = znajdzTransakcje(new TransakcjaPK("pierwszy-rozrachunek-Wn","drugi-rozrachunek-Ma"));
        int wynik  = usunNowaTransakcja(transakcja);
        if (wynik == 0) {
            System.out.println("Usunieto transakcje "+transakcja.toString());
        }
    }
    
    public static int usunDokument(Dokument dokument) {
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
    
    public static int usunNowaTransakcja(Transakcja transakcja) {
         try {
            System.out.println("Usuwam Transakcja");
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(transakcja));
            em.getTransaction().commit();
            em.clear();
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public static int usunNowyRozrachunek(Rozrachunek rozrachunek) {
         try {
            System.out.println("Usuwam Rozrachunek");
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(rozrachunek));
            em.getTransaction().commit();
            em.clear();
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public static int usunNowyWiersz(Wiersz wiersz) {
         try {
            System.out.println("Usuwam Wiersz");
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            em.remove(em.merge(wiersz));
            em.getTransaction().commit();
            em.clear();
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public static void petlaTworzenieDokumentu() {
        Dokument dokument = utrwalNowyDokument();
        if (dokument != null) {
            System.out.println("Utworzono nowy dokument "+dokument.toString());
        }
        Dokument odnalezionyDokument = znajdzDokument(dokument);
        if (dokument.equals(odnalezionyDokument)) {
            System.out.println("Odnaleziono dokument "+dokument.toString());
        }
    }
    
    public static Dokument znajdzDokument(Dokument dokument) {
        try {
            EntityManager em = getEntityManager();
            Dokument odnalezionyDokument = em.find(Dokument.class, dokument.getId());
            return odnalezionyDokument;
        } catch (Exception e) {
            return null;
        }   
    }
    
    public static Transakcja znajdzTransakcje(TransakcjaPK transakcjaPK) {
        try {
            EntityManager em = getEntityManager();
            Transakcja odnalezione = em.find(Transakcja.class, transakcjaPK);
            return odnalezione;
        } catch (Exception e) {
            return null;
        }   
    }
    
     public static Rozrachunek znajdzRozrachunek(String rozrachunekID) {
        try {
            EntityManager em = getEntityManager();
            Rozrachunek odnalezione = em.find(Rozrachunek.class, rozrachunekID);
            return odnalezione;
        } catch (Exception e) {
            return null;
        }   
    }
    
    public static Wiersz znajdzWiersz() {
        try {
            EntityManager em = getEntityManager();
            Query q = em.createNativeQuery("SELECT * FROM Wiersz o WHERE o.wiersznazwa = ?", Wiersz.class);
            q.setParameter(1, "pierwszy-wiersz");
            Wiersz odnalezione = (Wiersz) q.getSingleResult();
            return odnalezione;
        } catch (Exception e) {
            return null;
        }   
    }
    
    public static Wiersz znajdzWiersz(int numer) {
        try {
            EntityManager em = getEntityManager();
            Query q = em.createNativeQuery("SELECT * FROM Wiersz o WHERE o.wiersznazwa = ?", Wiersz.class);
            switch (numer) {
                case 1:
                    q.setParameter(1, "pierwszy-wiersz");
                    break;
                case 2:
                    q.setParameter(1, "drugi-wiersz");
                    break;
            }
            Wiersz odnalezione = (Wiersz) q.getSingleResult();
            return odnalezione;
        } catch (Exception e) {
            return null;
        }   
    }
    
       
    public static Dokument utrwalNowyDokument() {
        try {
            System.out.println("Utrwalam dokument");
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            Dokument dokument = stworzdokument();
            em.persist(dokument);
            em.getTransaction().commit();
            em.clear();
            return dokument;
        } catch (Exception e) {
            return null;
        }
    }
    
    public static Dokument stworzdokument() {
        StringBuilder mp = new StringBuilder();
        mp.append(listanazw.get(licznik));
        mp.append("dokument");
        Dokument dokument = new Dokument(mp.toString());
        Wiersz wiersz = stworzwiersz(dokument);
        dokument.getWierszelista().add(wiersz);
        System.out.println("Dokument siup!");
        return dokument;
    }
   
    public static Wiersz stworzwiersz(Dokument dokument) {
        StringBuilder mp = new StringBuilder();
        mp.append(listanazw.get(licznik));
        mp.append("wiersz");
        Wiersz wiersz = new Wiersz(mp.toString());
        Rozrachunek rozrachunek1 = stworzrozrachunek(wiersz, "-Wn");
        wiersz.getRozrachunki().add(rozrachunek1);
        Rozrachunek rozrachunek2 = stworzrozrachunek(wiersz, "-Ma");
        wiersz.getRozrachunki().add(rozrachunek2);
        wiersz.setDokument(dokument);
        System.out.println("Wiersz siup!");
        return wiersz;
    }
    
    public static Rozrachunek stworzrozrachunek(Wiersz wiersz, String wnma) {
        StringBuilder mp = new StringBuilder();
        mp.append(listanazw.get(licznik));
        mp.append("rozrachunek");
        mp.append(wnma);
        Rozrachunek rozrachunek = new Rozrachunek(mp.toString());
        rozrachunek.setWiersz(wiersz);
        System.out.println("Rozrachunek siup!");
        return rozrachunek;
    }
    
     public static Rozrachunek stworzrozrachunekNazwa(Wiersz wiersz, String wnma) {
        StringBuilder mp = new StringBuilder();
        mp.append(wiersz.getWiersznazwa());
        mp.append("-rozrachunek");
        mp.append(wnma);
        Rozrachunek rozrachunek = new Rozrachunek(mp.toString());
        rozrachunek.setWiersz(wiersz);
        System.out.println("Rozrachunek siup!");
        return rozrachunek;
    }
    
    public static Transakcja stworzTransakcje() {
        Rozrachunek rozliczajacy = znajdzRozrachunek("pierwszy-rozrachunek-Wn");
        Rozrachunek rozliczany = znajdzRozrachunek("drugi-rozrachunek-Ma");
        double kwota = 10000;
        Transakcja transakcja = new Transakcja();
        transakcja.setRozliczajacy(rozliczajacy);
        transakcja.setRozliczany(rozliczany);
        transakcja.setKwota(kwota);
        rozliczajacy.getTransakcje().add(transakcja);
        rozliczany.getTransakcje().add(transakcja);
        return transakcja;
    }
    
    public static Transakcja stworzTransakcje(Rozrachunek rozliczajacy, Rozrachunek rozliczany) {
        double kwota = 10000;
        Transakcja transakcja = new Transakcja();
        transakcja.setRozliczajacy(rozliczajacy);
        transakcja.setRozliczany(rozliczany);
        transakcja.setKwota(kwota);
        rozliczajacy.getTransakcje().add(transakcja);
        rozliczany.getTransakcje().add(transakcja);
        return transakcja;
    }
    
     public static Transakcja utrwalNowaTransakcja() {
        try {
            System.out.println("Utrwalam Transakcja");
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            Transakcja transakcja = stworzTransakcje();
            em.persist(transakcja);
            em.getTransaction().commit();
            em.clear();
            return transakcja;
        } catch (Exception e) {
            return null;
        }
    }
     
    public static int edytujWiersz(Wiersz wiersz) {
        try {
            System.out.println("Edytuje wiersz");
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            em.merge(wiersz);
            em.getTransaction().commit();
            em.clear();
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }
    
    public static int edytujDokument(Dokument dokument) {
        try {
            System.out.println("Edytuje dokument");
            EntityManager em = getEntityManager();
            em.getTransaction().begin();
            em.merge(dokument);
            em.getTransaction().commit();
            em.clear();
            return 0;
        } catch (Exception e) {
            return 1;
        }
    }

     public static Dokument[] DokumentWiersz_i_PotemRozrachunkiOrazTransakcja() {
        try {
            Dokument dokument1 = utrwalNowyDokument();
            licznik++;
            Dokument dokument2 = utrwalNowyDokument();
            //licznik++;
            //Dokument dokument3 = utrwalNowyDokument();
            Wiersz wiersz1 = dokument1.getWierszelista().get(0);
            Rozrachunek rozrachunek1 = wiersz1.getRozrachunekWn();
            Wiersz wiersz2 = dokument2.getWierszelista().get(0);
            Rozrachunek rozrachunek2 = wiersz2.getRozrachunekMa();
            //Wiersz wiersz3 = dokument3.getWierszelista().get(0);
            stworzTransakcje(rozrachunek1, rozrachunek2);
            //Rozrachunek rozrachunek3 = wiersz3.getRozrachunekMa();
            //stworzTransakcje(rozrachunek2, rozrachunek3);
            edytujDokument(dokument1);
            edytujDokument(dokument2);
            //edytujDokument(dokument3);
            System.out.println("Wiersze edytowane, rozrachunki nowe dodane, Transakcjs stworzona!");
            Dokument[] doktab = new Dokument[2];
            doktab[0] = dokument1;
            doktab[1] = dokument2;
            //doktab[2] = dokument3;
            return doktab;
        } catch (Exception e) {
            return null;
        }
    }
     
     public static void wydruk(Dokument dok) {
         p("------------");
         p("Drukuje dokument"+dok.getNazwa());
         p(dok);
         List<Wiersz> listawierszy = dok.getWierszelista();
         for (Wiersz p : listawierszy) {
             p("Drukuje wiersz");
             p(p);
             if (p.getRozrachunekWn()!=null) {
                p("Drukuje rozrachunekWn");
                p(p.getRozrachunekWn());
             }
             if (p.getRozrachunekMa()!=null) {
                p("Drukuje rozrachunekMa");
                p(p.getRozrachunekMa());
             }
             List<Rozrachunek> listarozrachunkow = new ArrayList<>();
             listarozrachunkow.add(p.getRozrachunekWn());
             listarozrachunkow.add(p.getRozrachunekMa());
             for (Rozrachunek r1 : listarozrachunkow) {
                if (r1 != null) {
                    List<Transakcja> listatrans = r1.getTransakcje();
                    for (Transakcja p1 : listatrans) {
                        p("Drukuje transakcje");
                        p(p1);
                    }
                }
             }
         }
     }
   
     public static void p(Object p) {
         if (p != null) {
            System.out.println(p);
         }
     }
    
}
