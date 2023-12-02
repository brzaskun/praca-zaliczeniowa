/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "rejestrurlopow")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Rejestrurlopow.findAll", query = "SELECT r FROM Rejestrurlopow r"),
    @NamedQuery(name = "Rejestrurlopow.findById", query = "SELECT r FROM Rejestrurlopow r WHERE r.id = :id"),
    @NamedQuery(name = "Rejestrurlopow.findByAngazRok", query = "SELECT r FROM Rejestrurlopow r WHERE r.angaz = :angaz AND r.rok = :rok"),
    @NamedQuery(name = "Rejestrurlopow.findByFirmaRok", query = "SELECT r FROM Rejestrurlopow r WHERE r.angaz.firma = :firma AND r.rok = :rok"),
    @NamedQuery(name = "Rejestrurlopow.findByRok", query = "SELECT r FROM Rejestrurlopow r WHERE r.rok = :rok"),
    @NamedQuery(name = "Rejestrurlopow.findByWymiarbo", query = "SELECT r FROM Rejestrurlopow r WHERE r.wymiarbo = :wymiarbo"),
    @NamedQuery(name = "Rejestrurlopow.findByDatapodwyzszeniawymiaru", query = "SELECT r FROM Rejestrurlopow r WHERE r.datapodwyzszeniawymiaru = :datapodwyzszeniawymiaru"),
    @NamedQuery(name = "Rejestrurlopow.findByNowywymiar", query = "SELECT r FROM Rejestrurlopow r WHERE r.nowywymiar = :nowywymiar"),
    @NamedQuery(name = "Rejestrurlopow.findByDodatkowywymiar", query = "SELECT r FROM Rejestrurlopow r WHERE r.dodatkowywymiar = :dodatkowywymiar"),
    @NamedQuery(name = "Rejestrurlopow.findByUzasadnieniedodwymiaru", query = "SELECT r FROM Rejestrurlopow r WHERE r.uzasadnieniedodwymiaru = :uzasadnieniedodwymiaru"),
    @NamedQuery(name = "Rejestrurlopow.findByDatadodwymiar", query = "SELECT r FROM Rejestrurlopow r WHERE r.datadodwymiar = :datadodwymiar"),
    @NamedQuery(name = "Rejestrurlopow.findByWykorzystaniesuma", query = "SELECT r FROM Rejestrurlopow r WHERE r.wykorzystaniesuma = :wykorzystaniesuma"),
    @NamedQuery(name = "Rejestrurlopow.findByM1", query = "SELECT r FROM Rejestrurlopow r WHERE r.m1 = :m1"),
    @NamedQuery(name = "Rejestrurlopow.findByM2", query = "SELECT r FROM Rejestrurlopow r WHERE r.m2 = :m2"),
    @NamedQuery(name = "Rejestrurlopow.findByM3", query = "SELECT r FROM Rejestrurlopow r WHERE r.m3 = :m3"),
    @NamedQuery(name = "Rejestrurlopow.findByM4", query = "SELECT r FROM Rejestrurlopow r WHERE r.m4 = :m4"),
    @NamedQuery(name = "Rejestrurlopow.findByM5", query = "SELECT r FROM Rejestrurlopow r WHERE r.m5 = :m5"),
    @NamedQuery(name = "Rejestrurlopow.findByM6", query = "SELECT r FROM Rejestrurlopow r WHERE r.m6 = :m6"),
    @NamedQuery(name = "Rejestrurlopow.findByM7", query = "SELECT r FROM Rejestrurlopow r WHERE r.m7 = :m7"),
    @NamedQuery(name = "Rejestrurlopow.findByM8", query = "SELECT r FROM Rejestrurlopow r WHERE r.m8 = :m8"),
    @NamedQuery(name = "Rejestrurlopow.findByM9", query = "SELECT r FROM Rejestrurlopow r WHERE r.m9 = :m9"),
    @NamedQuery(name = "Rejestrurlopow.findByM10", query = "SELECT r FROM Rejestrurlopow r WHERE r.m10 = :m10"),
    @NamedQuery(name = "Rejestrurlopow.findByM11", query = "SELECT r FROM Rejestrurlopow r WHERE r.m11 = :m11"),
    @NamedQuery(name = "Rejestrurlopow.findByM12", query = "SELECT r FROM Rejestrurlopow r WHERE r.m12 = :m12"),
    @NamedQuery(name = "Rejestrurlopow.findByEwiwalent", query = "SELECT r FROM Rejestrurlopow r WHERE r.ewiwalent = :ewiwalent"),
    @NamedQuery(name = "Rejestrurlopow.findByDatawyplatyekwiwalentu", query = "SELECT r FROM Rejestrurlopow r WHERE r.datawyplatyekwiwalentu = :datawyplatyekwiwalentu"),
    @NamedQuery(name = "Rejestrurlopow.findByUrlopzalegly", query = "SELECT r FROM Rejestrurlopow r WHERE r.urlopzalegly = :urlopzalegly"),
    @NamedQuery(name = "Rejestrurlopow.findByDowykorzystanianastrok", query = "SELECT r FROM Rejestrurlopow r WHERE r.dowykorzystanianastrok = :dowykorzystanianastrok")})
public class Rejestrurlopow implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @Column(name = "rok")
    private String rok;
    @Column(name = "wymiarbo")
    private int wymiarbo;
    @Column(name = "datapodwyzszeniawymiaru")
    private String datapodwyzszeniawymiaru;
    @Column(name = "nowywymiar")
    private int nowywymiar;
    @Column(name = "dodatkowywymiar")
    private int dodatkowywymiar;
    @Column(name = "uzasadnieniedodwymiaru")
    private String uzasadnieniedodwymiaru;
    @Column(name = "datadodwymiar")
    private String datadodwymiar;
    @Column(name = "wykorzystaniesuma")
    private int wykorzystaniesuma;
    @Column(name = "m1")
    private int m1;
    @Column(name = "m2")
    private int m2;
    @Column(name = "m3")
    private int m3;
    @Column(name = "m4")
    private int m4;
    @Column(name = "m5")
    private int m5;
    @Column(name = "m6")
    private int m6;
    @Column(name = "m7")
    private int m7;
    @Column(name = "m8")
    private int m8;
    @Column(name = "m9")
    private int m9;
    @Column(name = "m10")
    private int m10;
    @Column(name = "m11")
    private int m11;
    @Column(name = "m12")
    private int m12;
    @Column(name = "ewiwalent")
    private int ewiwalent;
    @Column(name = "datawyplatyekwiwalentu")
    private String datawyplatyekwiwalentu;
    @Column(name = "urlopzalegly")
    private int urlopzalegly;
    @Column(name = "dowykorzystanianastrok")
    private int dowykorzystanianastrok;
    @JoinColumn(name = "angaz", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Angaz angaz;

    public Rejestrurlopow() {
        
    }

    public Rejestrurlopow(Angaz angaz, String rok) {
        this.angaz = angaz;
        this.rok = rok;
    }

    public Rejestrurlopow(int id) {
        this.id = id;
    }

    public Rejestrurlopow(int id, String rok) {
        this.id = id;
        this.rok = rok;
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

    public int getWymiarbo() {
        return wymiarbo;
    }

    public void setWymiarbo(int wymiarbo) {
        this.wymiarbo = wymiarbo;
    }

    public String getDatapodwyzszeniawymiaru() {
        return datapodwyzszeniawymiaru;
    }

    public void setDatapodwyzszeniawymiaru(String datapodwyzszeniawymiaru) {
        this.datapodwyzszeniawymiaru = datapodwyzszeniawymiaru;
    }

    public int getNowywymiar() {
        return nowywymiar;
    }

    public void setNowywymiar(int nowywymiar) {
        this.nowywymiar = nowywymiar;
    }

    public int getDodatkowywymiar() {
        return dodatkowywymiar;
    }

    public void setDodatkowywymiar(int dodatkowywymiar) {
        this.dodatkowywymiar = dodatkowywymiar;
    }

    public String getUzasadnieniedodwymiaru() {
        return uzasadnieniedodwymiaru;
    }

    public void setUzasadnieniedodwymiaru(String uzasadnieniedodwymiaru) {
        this.uzasadnieniedodwymiaru = uzasadnieniedodwymiaru;
    }

    public String getDatadodwymiar() {
        return datadodwymiar;
    }

    public void setDatadodwymiar(String datadodwymiar) {
        this.datadodwymiar = datadodwymiar;
    }

    public int getWykorzystaniesuma() {
        return wykorzystaniesuma;
    }

    public void setWykorzystaniesuma(int wykorzystaniesuma) {
        this.wykorzystaniesuma = wykorzystaniesuma;
    }

    public int getM1() {
        return m1;
    }

    public void setM1(int m1) {
        this.m1 = m1;
    }

    public int getM2() {
        return m2;
    }

    public void setM2(int m2) {
        this.m2 = m2;
    }

    public int getM3() {
        return m3;
    }

    public void setM3(int m3) {
        this.m3 = m3;
    }

    public int getM4() {
        return m4;
    }

    public void setM4(int m4) {
        this.m4 = m4;
    }

    public int getM5() {
        return m5;
    }

    public void setM5(int m5) {
        this.m5 = m5;
    }

    public int getM6() {
        return m6;
    }

    public void setM6(int m6) {
        this.m6 = m6;
    }

    public int getM7() {
        return m7;
    }

    public void setM7(int m7) {
        this.m7 = m7;
    }

    public int getM8() {
        return m8;
    }

    public void setM8(int m8) {
        this.m8 = m8;
    }

    public int getM9() {
        return m9;
    }

    public void setM9(int m9) {
        this.m9 = m9;
    }

    public int getM10() {
        return m10;
    }

    public void setM10(int m10) {
        this.m10 = m10;
    }

    public int getM11() {
        return m11;
    }

    public void setM11(int m11) {
        this.m11 = m11;
    }

    public int getM12() {
        return m12;
    }

    public void setM12(int m12) {
        this.m12 = m12;
    }

    public int getEwiwalent() {
        return ewiwalent;
    }

    public void setEwiwalent(int ewiwalent) {
        this.ewiwalent = ewiwalent;
    }

    public String getDatawyplatyekwiwalentu() {
        return datawyplatyekwiwalentu;
    }

    public void setDatawyplatyekwiwalentu(String datawyplatyekwiwalentu) {
        this.datawyplatyekwiwalentu = datawyplatyekwiwalentu;
    }

    public int getUrlopzalegly() {
        return urlopzalegly;
    }

    public void setUrlopzalegly(int urlopzalegly) {
        this.urlopzalegly = urlopzalegly;
    }

    public int getDowykorzystanianastrok() {
        return dowykorzystanianastrok;
    }

    public void setDowykorzystanianastrok(int dowykorzystanianastrok) {
        this.dowykorzystanianastrok = dowykorzystanianastrok;
    }

    public Angaz getAngaz() {
        return angaz;
    }

    public void setAngaz(Angaz angaz) {
        this.angaz = angaz;
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
        if (!(object instanceof Rejestrurlopow)) {
            return false;
        }
        Rejestrurlopow other = (Rejestrurlopow) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "javaapplication2.Rejestrurlopow[ id=" + id + " ]";
    }
    
}
