/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
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
    @NamedQuery(name = "StornoDok.findAll", query = "SELECT a FROM StornoDok a"),
    @NamedQuery(name = "StornoDok.findById", query = "SELECT a FROM StornoDok a WHERE a.id = :id"),
    @NamedQuery(name = "StornoDok.findByMc", query = "SELECT a FROM StornoDok a WHERE a.mc = :mc"),
    @NamedQuery(name = "StornoDok.findByRok", query = "SELECT a FROM StornoDok a WHERE a.rok = :rok"),
    @NamedQuery(name = "StornoDok.findByZaksiegowane", query = "SELECT a FROM StornoDok a WHERE a.zaksiegowane = :zaksiegowane"),
    })
public class StornoDok implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "mc")
    private String mc;
    @Column(name = "rok")
    private Integer rok;
    @Lob
    @Column(name = "dokument")
    private List<Dok> dokument;
    @Column(name = "zaksiegowane")
    private Boolean zaksiegowane;
    @Column(name = "podatnik")
    private String podatnik;

    public StornoDok() {
    }

    public StornoDok(Integer id, String mc, Integer rok, List<Dok> dokument, Boolean zaksiegowane, String podatnik) {
        this.id = id;
        this.mc = mc;
        this.rok = rok;
        this.dokument = dokument;
        this.zaksiegowane = zaksiegowane;
        this.podatnik = podatnik;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public Integer getRok() {
        return rok;
    }

    public void setRok(Integer rok) {
        this.rok = rok;
    }

    public List<Dok> getDokument() {
        return dokument;
    }

    public void setDokument(List<Dok> dokument) {
        this.dokument = dokument;
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
        return "StornoDok{" + "id=" + id + ", mc=" + mc + ", rok=" + rok + ", dokumentstrono=" + dokument    + ", zaksiegowane=" + zaksiegowane + ", podatnik=" + podatnik + '}';
    }

   
}

