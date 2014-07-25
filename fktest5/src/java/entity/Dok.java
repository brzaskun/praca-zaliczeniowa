/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
public class Dok implements Serializable{
//    private static final long serialVersionUID = 1L;
//    
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Basic(optional = false)
//    @Column(nullable = false)
//    private Integer idDok;
//    @Basic(optional = false)
//    @NotNull
//    @Size(min = 1, max = 100)
//    @Column(nullable = false, length = 100)
//    private String nazwaDok;
//    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "_dok")
//    private List<Wiersz> wierszDok;
//
//    public Dok() {
//    }
//
//    public Dok(String nazwa) {
//        this.wierszDok = new ArrayList<>();
//        this.nazwaDok = "Dok "+nazwa;
//    }
//
//    public Integer getIdDok() {
//        return idDok;
//    }
//
//    public void setIdDok(Integer idDok) {
//        this.idDok = idDok;
//    }
//
//    public String getNazwaDok() {
//        return nazwaDok;
//    }
//
//    public void setNazwaDok(String nazwaDok) {
//        this.nazwaDok = nazwaDok;
//    }
//
//    public List<Wiersz> getWierszDok() {
//        return wierszDok;
//    }
//
//    public void setWierszDok(List<Wiersz> wierszDok) {
//        this.wierszDok = wierszDok;
//    }
//
//    
//    public Wiersz getWiersz(int i) {
//        try {
//            return this.wierszDok.get(i);
//        } catch (Exception e) {
//            return null;
//        }
//    }
//
//    public void setWiersz(Wiersz wiersz) {
//        this.wierszDok.add(wiersz);
//    }
//
//    @Override
//    public String toString() {
//        return "Dok{" + "id=" + idDok + ", nazwa=" + nazwaDok + ", wiersz rozmiar=" + wierszDok.size() + '}';
//    }

    
    

    
    
    
}
