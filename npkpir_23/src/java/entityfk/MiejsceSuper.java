/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import entity.Podatnik;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@MappedSuperclass
public class MiejsceSuper implements Serializable {
    protected static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    protected int id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aktywny", nullable = false)
    protected boolean aktywny;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "opismiejsca", nullable = false, length = 255, unique = true)
    protected String opismiejsca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "opisskrocony", nullable = false, length = 255)
    protected String opisskrocony;
    @JoinColumn(name = "podid", referencedColumnName = "id")
    @ManyToOne
    protected Podatnik podatnikObj;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "nrkonta", nullable = false, length = 5)
    protected String nrkonta;
    @Column(name = "rok")
    protected int rok;
    @Basic(optional = true)
    @Column(name = "pokaz0chowaj1", nullable = true)
    protected boolean pokaz0chowaj1;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public boolean isAktywny() {
        return aktywny;
    }

    public void setAktywny(boolean aktywny) {
        this.aktywny = aktywny;
    }

    public String getOpismiejsca() {
        return opismiejsca;
    }

    public void setOpismiejsca(String opismiejsca) {
        this.opismiejsca = opismiejsca;
    }

    public String getOpisskrocony() {
        return opisskrocony;
    }

    public void setOpisskrocony(String opisskrocony) {
        this.opisskrocony = opisskrocony;
    }

    public Podatnik getPodatnikObj() {
        return podatnikObj;
    }

    public void setPodatnikObj(Podatnik podatnikObj) {
        this.podatnikObj = podatnikObj;
    }

    public String getNrkonta() {
        return nrkonta;
    }

    public void setNrkonta(String nrkonta) {
        this.nrkonta = nrkonta;
    }

    public int getRok() {
        return rok;
    }

    public void setRok(int rok) {
        this.rok = rok;
    }

    public boolean isPokaz0chowaj1() {
        return pokaz0chowaj1;
    }

    public void setPokaz0chowaj1(boolean pokaz0chowaj1) {
        this.pokaz0chowaj1 = pokaz0chowaj1;
    }
    
    
}
