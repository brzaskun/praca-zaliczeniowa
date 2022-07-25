/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "BankImportWzory.findAll", query = "SELECT c FROM BankImportWzory c"),
    @NamedQuery(name = "BankImportWzory.findByBank", query = "SELECT c FROM BankImportWzory c WHERE c.bank=:bank"),
})
public class BankImportWzory implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "bank", nullable = false)
    private String bank;
    @Column(name = "poleopis")
    private String poleopis;
    @Column(name = "polekontrahent")
    private String polekontrahent;
    @Column(name = "polekonto")
    private String polekonto;
    @Column(name = "nronta")
    private String nrkonta;
    @Column(name = "miesiace")
    private boolean miesiace;
    @Column(name = "nadpisz")
    private boolean nadpisz;

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + Objects.hashCode(this.id);
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
        final BankImportWzory other = (BankImportWzory) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getPoleopis() {
        return poleopis;
    }

    public void setPoleopis(String poleopis) {
        this.poleopis = poleopis;
    }

    public String getPolekontrahent() {
        return polekontrahent;
    }

    public void setPolekontrahent(String polekontrahent) {
        this.polekontrahent = polekontrahent;
    }

    public String getPolekonto() {
        return polekonto;
    }

    public void setPolekonto(String polekonto) {
        this.polekonto = polekonto;
    }

    public String getNrkonta() {
        return nrkonta;
    }

    public void setNrkonta(String nrkonta) {
        this.nrkonta = nrkonta;
    }

    public boolean isMiesiace() {
        return miesiace;
    }

    public void setMiesiace(boolean miesiace) {
        this.miesiace = miesiace;
    }

    public boolean isNadpisz() {
        return nadpisz;
    }

    public void setNadpisz(boolean nadpisz) {
        this.nadpisz = nadpisz;
    }
    
    
    
}
