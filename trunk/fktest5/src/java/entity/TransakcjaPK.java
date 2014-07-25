/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class TransakcjaPK implements Serializable{
    
    private Integer rachunekT_PK;
    private Integer platnoscT_PK;

    public TransakcjaPK() {
    }
    
    

    public TransakcjaPK(Integer rachunek, Integer platnosc) {
        this.rachunekT_PK = rachunek;
        this.platnoscT_PK = platnosc;
    }
    

    public Integer getRachunekT_PK() {
        return rachunekT_PK;
    }

    public void setRachunekT_PK(Integer rachunekT_PK) {
        this.rachunekT_PK = rachunekT_PK;
    }

    public Integer getPlatnoscT_PK() {
        return platnoscT_PK;
    }

    public void setPlatnoscT_PK(Integer platnoscT_PK) {
        this.platnoscT_PK = platnoscT_PK;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.rachunekT_PK);
        hash = 97 * hash + Objects.hashCode(this.platnoscT_PK);
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
        final TransakcjaPK other = (TransakcjaPK) obj;
        if (!Objects.equals(this.rachunekT_PK, other.rachunekT_PK)) {
            return false;
        }
        if (!Objects.equals(this.platnoscT_PK, other.platnoscT_PK)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TransakcjaPK{" + "rozliczajacyId=" + rachunekT_PK + ", rozliczanyId=" + platnoscT_PK + '}';
    }
}

