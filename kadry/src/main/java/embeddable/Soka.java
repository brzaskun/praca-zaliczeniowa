/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Osito
 */
public class Soka implements Serializable {
     private int id;
     private static final long serialVersionUID = 1L;
     private String nazwiskoiimie;
     private String pesel;
     private List<Soka1> lista;

    public Soka(int id, String nazwiskoimie, String pesel) {
        this.id = id;
        this.nazwiskoiimie = nazwiskoimie;
        this.pesel = pesel;
        this.lista = new ArrayList<>();
      }

    public List<Soka1> getLista() {
        return lista;
    }

    public void setLista(List<Soka1> lista) {
        this.lista = lista;
    }


    public String getNazwiskoiimie() {
        return nazwiskoiimie;
    }
    
    
    public void setNazwiskoiimie(String nazwiskoiimie) {
        this.nazwiskoiimie = nazwiskoiimie;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + this.id;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Soka other = (Soka) obj;
        return this.id == other.id;
    }
     
    

    

    @Override
    public String toString() {
        return "Soka{" + "nazwiskoiimie=" + nazwiskoiimie + ", pesel=" + pesel;
    }
     
     
    
}
