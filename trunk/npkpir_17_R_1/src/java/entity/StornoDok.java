/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import embeddable.Storno;
import embeddable.Umorzenie;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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
@Table(name = "stornodok")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Amodok.findAll", query = "SELECT a FROM Amodok a"),
    @NamedQuery(name = "Amodok.findById", query = "SELECT a FROM Amodok a WHERE a.id = :id"),
    @NamedQuery(name = "Amodok.findByMc", query = "SELECT a FROM Amodok a WHERE a.mc = :mc"),
    @NamedQuery(name = "Amodok.findByRok", query = "SELECT a FROM Amodok a WHERE a.rok = :rok"),
    @NamedQuery(name = "Amodok.findByZaksiegowane", query = "SELECT a FROM Amodok a WHERE a.zaksiegowane = :zaksiegowane"),
    })
public class StornoDok implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "mc")
    private Integer mc;
    @Column(name = "rok")
    private Integer rok;
    @Lob
    @Column(name = "dokumentstorno")
    private List<Storno> dokumentstrono;
    @Column(name = "zaksiegowane")
    private Boolean zaksiegowane;
    @Column(name = "podatnik")
    private String podatnik;

    public StornoDok() {
    }

    public StornoDok(Integer id, Integer mc, Integer rok, List<Storno> dokumentstrono, Boolean zaksiegowane, String podatnik) {
        this.id = id;
        this.mc = mc;
        this.rok = rok;
        this.dokumentstrono = dokumentstrono;
        this.zaksiegowane = zaksiegowane;
        this.podatnik = podatnik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMc() {
        return mc;
    }

    public void setMc(Integer mc) {
        this.mc = mc;
    }

    public Integer getRok() {
        return rok;
    }

    public void setRok(Integer rok) {
        this.rok = rok;
    }

    public List<Storno> getDokumentstrono() {
        return dokumentstrono;
    }

    public void setDokumentstrono(List<Storno> dokumentstrono) {
        this.dokumentstrono = dokumentstrono;
    }

    public Boolean getZaksiegowane() {
        return zaksiegowane;
    }

    public void setZaksiegowane(Boolean zaksiegowane) {
        this.zaksiegowane = zaksiegowane;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
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
        final StornoDok other = (StornoDok) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "StornoDok{" + "id=" + id + ", mc=" + mc + ", rok=" + rok + ", dokumentstrono=" + dokumentstrono + ", zaksiegowane=" + zaksiegowane + ", podatnik=" + podatnik + '}';
    }

   
}
