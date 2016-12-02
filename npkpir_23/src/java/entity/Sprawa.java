/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "Sprawa.findAll", query = "SELECT p FROM Sprawa p"),
    @NamedQuery(name = "Sprawa.findByOdbiorca", query = "SELECT p FROM Sprawa p WHERE p.odbiorca = :odbiorca"),
    @NamedQuery(name = "Sprawa.findByNadawca", query = "SELECT p FROM Sprawa p WHERE p.nadawca = :nadawca")
})
public class Sprawa  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private int id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datasporzadzenia", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date datasporzadzenia;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "tresc", nullable = false, length = 65535)
    private String tresc;
    @NotNull
    @JoinColumn(name = "nadawca",referencedColumnName = "login")
    @ManyToOne
    private Uz nadawca;
    @JoinColumn(name = "odbiorca",referencedColumnName = "login", nullable = true)
    @ManyToOne
    private Uz odbiorca;
    @ManyToOne
    @JoinColumn(name = "klient", referencedColumnName = "nip", nullable = true)
    private Podatnik klient;
    @Size(min = 1, max = 100)
    @Column(name = "organ", nullable = false, length = 100)
    private String organ;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "waznosc", nullable = false, length = 100)
    private String waznosc;
    @Column(name = "termindo", nullable = true)
    @Temporal(TemporalType.TIMESTAMP)
    private Date termindo;
    @Size(max = 150)
    @Column(name = "status", length = 150)
    private String status;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "datastatus")
    private Date datastatusu;
    @Column(name = "usunieta")
    private boolean usunieta;

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + this.id;
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
        final Sprawa other = (Sprawa) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sprawy{" + "datasporzadzenia=" + datasporzadzenia + ", tresc=" + tresc + ", nadawca=" + nadawca.getLogin() + ", odbiorca=" + odbiorca.getLogin() + ", klient=" + klient.getNazwapelna() + ", organ=" + organ + ", waznosc=" + waznosc + ", termindo=" + termindo + ", status=" + status + ", datastatusu=" + datastatusu + '}';
    }
    
    
    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDatasporzadzenia() {
        return datasporzadzenia;
    }

    public void setDatasporzadzenia(Date datasporzadzenia) {
        this.datasporzadzenia = datasporzadzenia;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public Uz getNadawca() {
        return nadawca;
    }

    public void setNadawca(Uz nadawca) {
        this.nadawca = nadawca;
    }

    public Uz getOdbiorca() {
        return odbiorca;
    }

    public void setOdbiorca(Uz odbiorca) {
        this.odbiorca = odbiorca;
    }

    public Podatnik getKlient() {
        return klient;
    }

    public void setKlient(Podatnik klient) {
        this.klient = klient;
    }

    public String getOrgan() {
        return organ;
    }

    public void setOrgan(String organ) {
        this.organ = organ;
    }

    public String getWaznosc() {
        return waznosc;
    }

    public void setWaznosc(String waznosc) {
        this.waznosc = waznosc;
    }

    public Date getTermindo() {
        return termindo;
    }

    public void setTermindo(Date termindo) {
        this.termindo = termindo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getDatastatusu() {
        return datastatusu;
    }

    public void setDatastatusu(Date datastatusu) {
        this.datastatusu = datastatusu;
    }

    public boolean isUsunieta() {
        return usunieta;
    }

    public void setUsunieta(boolean usunieta) {
        this.usunieta = usunieta;
    }
    
    
    
    
    
}
