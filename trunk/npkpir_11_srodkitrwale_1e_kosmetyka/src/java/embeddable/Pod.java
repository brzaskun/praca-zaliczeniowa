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
@ManagedBean(name="PodatnicyEmbedded")
@Embeddable
@SessionScoped
public class Pod implements Serializable{
    
    private int Id;
    private String NIP;
    private String npelna;
    private static final List<Pod> podList;

    static{
        podList = new ArrayList<Pod>();
        podList.add(new Pod(120, "8511005008","Techbud"));
        podList.add(new Pod(123, "8511005018","Stępol"));
        podList.add(new Pod(124, "8511005108","Wasiak"));
        podList.add(new Pod(135, "8511001008","Helmutowski"));
        podList.add(new Pod(136, "8511015008","Panikarz"));
    }
    
    public Pod() {
    }

    public Pod(int Id, String NIP, String npelna) {
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

    public List<Pod> getPodList() {
        return podList;
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
        final Pod other = (Pod) obj;
        if (this.Id != other.Id) {
            return false;
        }
        return true;
    }

    

    @Override
    public String toString() {
        return "Pod{" + "Id=" + Id + ", NIP=" + NIP + ", npelna=" + npelna + '}';
    }

    
    
    
    
    
}
