/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import data.Data;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "vieskontrahent", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"nip", "rok", "mc", "tydzien"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Vieskontrahent.findAll", query = "SELECT v FROM Vieskontrahent v"),
    @NamedQuery(name = "Vieskontrahent.findById", query = "SELECT v FROM Vieskontrahent v WHERE v.id = :id"),
    @NamedQuery(name = "Vieskontrahent.findByRok", query = "SELECT v FROM Vieskontrahent v WHERE v.rok = :rok"),
    @NamedQuery(name = "Vieskontrahent.findByRokMcTyd", query = "SELECT v FROM Vieskontrahent v WHERE v.rok = :rok and v.mc = :mc and v.tydzien = :tydzien"),
    @NamedQuery(name = "Vieskontrahent.findByMc", query = "SELECT v FROM Vieskontrahent v WHERE v.mc = :mc"),
    @NamedQuery(name = "Vieskontrahent.findByTydzien", query = "SELECT v FROM Vieskontrahent v WHERE v.tydzien = :tydzien"),
    @NamedQuery(name = "Vieskontrahent.findByStatus", query = "SELECT v FROM Vieskontrahent v WHERE v.status = :status"),
    @NamedQuery(name = "Vieskontrahent.findByData", query = "SELECT v FROM Vieskontrahent v WHERE v.data = :data"),
    @NamedQuery(name = "Vieskontrahent.findByIdentyfikatorsprawdzenia", query = "SELECT v FROM Vieskontrahent v WHERE v.identyfikatorsprawdzenia = :identyfikatorsprawdzenia"),
    @NamedQuery(name = "Vieskontrahent.findByKraj", query = "SELECT v FROM Vieskontrahent v WHERE v.kraj = :kraj")})
public class Vieskontrahent implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer id;
    @Basic(optional = false)
    @Column(nullable = false, length = 4)
    private String rok;
    @Basic(optional = false)
    @Column(nullable = false, length = 2)
    private String mc;
    @Basic(optional = false)
    @Column(nullable = false, length = 2)
    private String tydzien;
    @Basic(optional = false)
    @Column(nullable = false)
    private String nip;
    private boolean status;
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;
    @Column(length = 255)
    private String identyfikatorsprawdzenia;
    @Column(length = 255)
    private String kraj;
    @Column(length = 512)
    private String uwagi;

    public Vieskontrahent() {
    }

    public Vieskontrahent(Integer id) {
        this.id = id;
    }

    public Vieskontrahent(Integer id, String rok, String mc, String tydzien) {
        this.id = id;
        this.rok = rok;
        this.mc = mc;
        this.tydzien = tydzien;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getTydzien() {
        return tydzien;
    }

    public void setTydzien(String tydzien) {
        this.tydzien = tydzien;
    }

    public String getNip() {
        return nip;
    }

    public void setNip(String nip) {
        this.nip = nip;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getIdentyfikatorsprawdzenia() {
        return identyfikatorsprawdzenia;
    }

    public void setIdentyfikatorsprawdzenia(String identyfikatorsprawdzenia) {
        this.identyfikatorsprawdzenia = identyfikatorsprawdzenia;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public String getUwagi() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
    }


    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Vieskontrahent)) {
            return false;
        }
        Vieskontrahent other = (Vieskontrahent) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.Vieskontrahent[ id=" + id + " ]";
    }

    public void ustawRokMcTydzien() {
        this.setRok(Data.aktualnyRok());
        this.setMc(Data.aktualnyMc());
        this.setTydzien(Data.aktualnyTydzien());
    }
    
      public String getWynikVies() {
        String zwrot = "nieaktywny";
        if (!this.isStatus() && this.uwagi != null) {
            if (this.uwagi.equals("MS_UNAVAILABLE")) {
                zwrot = "serwer kraju wyłączony";
            } else {
                zwrot = this.uwagi;
            }
        } else if (this.isStatus()) {
            zwrot = "ok";
        }
        return zwrot;
    }
    
}
