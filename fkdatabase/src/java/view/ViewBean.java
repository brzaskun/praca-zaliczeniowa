/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package view;

import entity.Dokument;
import entity.Rozrachunek;
import entity.Wiersz;
import java.io.Serializable;
import java.util.HashMap;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

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
        return emFactory.createEntityManager();
    }

 
    public static void main(String[] args) {
        Dokument dokument = utrwalNowyDokument();
        if (dokument != null) {
            System.out.println("Utworzono nowy dokument "+dokument.toString());
        }
        Dokument odnalezionyDokument = znajdzDokument(dokument);
        if (dokument.equals(odnalezionyDokument)) {
            System.out.println("Odnaleziono dokument "+dokument.toString());
        }
        dokument = utrwalNowyDokument();
        if (dokument != null) {
            System.out.println("Utworzono nowy dokument "+dokument.toString());
        }
        odnalezionyDokument = znajdzDokument(dokument);
        if (dokument.equals(odnalezionyDokument)) {
            System.out.println("Odnaleziono dokument "+dokument.toString());
        }
        System.out.println("Koniec funkcji");
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
        Rozrachunek rozrachunek1 = stworzrozrachunek(wiersz);
        wiersz.getRozrachuneklista().add(rozrachunek1);
        Rozrachunek rozrachunek2 = stworzrozrachunek(wiersz);
        wiersz.getRozrachuneklista().add(rozrachunek2);
        wiersz.setDokument(dokument);
        System.out.println("Wiersz siup!");
        return wiersz;
    }
    
    public static Rozrachunek stworzrozrachunek(Wiersz wiersz) {
        StringBuilder mp = new StringBuilder();
        mp.append(listanazw.get(licznik));
        mp.append("rozrachunek");
        Rozrachunek rozrachunek = new Rozrachunek(mp.toString());
        rozrachunek.setWiersz(wiersz);
        System.out.println("Rozrachunek siup!");
        return rozrachunek;
    }
}
