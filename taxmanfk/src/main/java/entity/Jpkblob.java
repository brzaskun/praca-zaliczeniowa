/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "jpkblob")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Jpkblob.findAll", query = "SELECT j FROM Jpkblob j"),
    @NamedQuery(name = "Jpkblob.findById", query = "SELECT j FROM Jpkblob j WHERE j.id = :id")})
public class Jpkblob implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Lob
    @Column(name = "jpk")
    private JPKSuper jpk;
    @JoinColumn(name = "upo", referencedColumnName = "id")
    private UPO upo;

    public Jpkblob() {
    }

    public Jpkblob(Integer id) {
        this.id = id;
    }

    public Jpkblob(JPKSuper jpk, UPO upo) {
        this.jpk = jpk;
        this.upo = upo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public JPKSuper getJpk() {
        return jpk;
    }

    public void setJpk(JPKSuper jpk) {
        this.jpk = jpk;
    }

    public UPO getUpo() {
        return upo;
    }

    public void setUpo(UPO upo) {
        this.upo = upo;
    }

    
   
   
    
}
