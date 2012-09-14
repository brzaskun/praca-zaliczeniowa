/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.persistence.Embeddable;
import javax.persistence.Id;

/**
 *
 * @author Osito
 */
@ManagedBean(name="KlEmbedded")
@Embeddable
@SessionScoped
public class Kl implements Serializable{
    
    private int Id;
    private String NIP;
    private String npelna;
    private static final List<Kl> klList;

    static{
        klList = new ArrayList<Kl>();
        klList.add(new Kl(120, "8511005008","Klient1"));
        klList.add(new Kl(123, "8511005018","Klient2"));
        klList.add(new Kl(124, "8511005108","Klient3"));
        klList.add(new Kl(135, "8511001008","Klient4"));
        klList.add(new Kl(136, "8511015008","Klient5"));
    }
    
    public Kl() {
    }

    public Kl(int Id, String NIP, String npelna) {
        this.Id = Id;
        this.NIP = NIP;
        this.npelna = npelna;
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

    public List<Kl> getKlList() {
        return klList;
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
        return "Kl{" + "Id=" + Id + ", NIP=" + NIP + ", npelna=" + npelna + '}';
    }

    
    
    
    
    
}
