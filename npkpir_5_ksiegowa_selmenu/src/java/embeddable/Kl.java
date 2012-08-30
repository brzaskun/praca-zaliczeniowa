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
    @Id
    private String NIP;
    private String npelna;
    private static final List<Kl> klList;

    static{
        klList = new ArrayList<Kl>();
        klList.add(new Kl("8511005008","Klient1"));
        klList.add(new Kl("8511005018","Klient2"));
        klList.add(new Kl("8511005108","Klient3"));
        klList.add(new Kl("8511001008","Klient4"));
        klList.add(new Kl("8511015008","Klient5"));
    }
    
    public Kl() {
    }

    public Kl(String NIP, String npelna) {
        this.NIP = NIP;
        this.npelna = npelna;
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
        hash = 59 * hash + (this.NIP != null ? this.NIP.hashCode() : 0);
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
        if ((this.NIP == null) ? (other.NIP != null) : !this.NIP.equals(other.NIP)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Kl{" + ", NIP=" + NIP + ", npelna=" + npelna + '}';
    }

    
    
    
    
    
}
