/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
@SessionScoped
public class Kl implements Serializable{
    
    private int Id;
    private String NIP;
    private String npelna;
    private String pkpirKolumna;
    private String evat;
    private static List<Kl> klList;
       

    static{
        klList = new ArrayList<Kl>();
        klList.add(new Kl(120, "8511005008","Klient1","zakup tow.i mat.","zakup 23%"));
        klList.add(new Kl(123, "8511005018","Klient2","poz. koszty","zakup 23%"));
        klList.add(new Kl(124, "8511005108","Klient3","poz. koszty","zakup 23%"));
        klList.add(new Kl(135, "8511001008","Klient4","zakup tow.i mat.","zakup 23%"));
        klList.add(new Kl(136, "8511015008","Klient5","przych. sprz","zakup 23%"));
    }
    
    public Kl() {
    }

    public Kl(int Id, String NIP, String npelna, String pkpirKolumna, String evat) {
        this.Id = Id;
        this.NIP = NIP;
        this.npelna = npelna;
        this.pkpirKolumna = pkpirKolumna;
        this.evat =  evat;
    }

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public String getNpelna() {
        return npelna;
    }

    public void setNpelna(String npelna) {
        this.npelna = npelna;
    }

    public String getPkpirKolumna() {
        return pkpirKolumna;
    }

    public void setPkpirKolumna(String pkpirKolumna) {
        this.pkpirKolumna = pkpirKolumna;
    }

    public List<Kl> getKlList() {
        return klList;
    }

    public String getEvat() {
        return evat;
    }

    public void setEvat(String evat) {
        this.evat = evat;
    }

   

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 79 * hash + this.Id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Kl other = (Kl) obj;
        if (this.Id != other.Id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Kl{" + "Id=" + Id + ", NIP=" + NIP + ", npelna=" + npelna + ", pkpirKolumna=" + pkpirKolumna + ", evat=" + evat + '}';
    }

    
    
}
