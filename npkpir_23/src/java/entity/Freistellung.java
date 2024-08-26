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
import javax.persistence.UniqueConstraint;
/**
 *
 * @author Osito
 */


@Entity
@Table(name = "freistellung", uniqueConstraints = @UniqueConstraint(columnNames = {"podatnik", "datado"}))
@NamedQueries({
    @NamedQuery(name = "Freistellung.findAll", query = "SELECT f FROM Freistellung f"),
    @NamedQuery(name = "Freistellung.findById", query = "SELECT f FROM Freistellung f WHERE f.id = :id"),
    @NamedQuery(name = "Freistellung.findByRok", query = "SELECT f FROM Freistellung f WHERE f.rok = :rok"),
    @NamedQuery(name = "Freistellung.findByDatado", query = "SELECT f FROM Freistellung f WHERE f.datado = :datado"),
    @NamedQuery(name = "Freistellung.findByMailprzypom1", query = "SELECT f FROM Freistellung f WHERE f.mailprzypom1 = :mailprzypom1"),
    @NamedQuery(name = "Freistellung.findByMailprzypom2", query = "SELECT f FROM Freistellung f WHERE f.mailprzypom2 = :mailprzypom2")
})
public class Freistellung implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private int id;

    @ManyToOne
    @JoinColumn(name = "podatnik", referencedColumnName = "id")
    private Podatnik podatnik;

    @Column(name = "rok")
    private String rok;

    @Column(name = "datado")
    private String datado;

    @Column(name = "mailprzypom1")
    private String mailprzypom1;

    @Column(name = "mailprzypom2")
    private String mailprzypom2;

    // Getters and Setters

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Podatnik getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(Podatnik podatnik) {
        this.podatnik = podatnik;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    public String getMailprzypom1() {
        return mailprzypom1;
    }

    public void setMailprzypom1(String mailprzypom1) {
        this.mailprzypom1 = mailprzypom1;
    }

    public String getMailprzypom2() {
        return mailprzypom2;
    }

    public void setMailprzypom2(String mailprzypom2) {
        this.mailprzypom2 = mailprzypom2;
    }

    // Constructors

    public Freistellung() {
    }

    public Freistellung(Podatnik podatnik, String rok, String datado, String mailprzypom1, String mailprzypom2) {
        this.podatnik = podatnik;
        this.rok = rok;
        this.datado = datado;
        this.mailprzypom1 = mailprzypom1;
        this.mailprzypom2 = mailprzypom2;
    }

    // toString method

    @Override
    public String toString() {
        return "Freistellung{" +
                ", podatnik=" + podatnik.getPrintnazwa() +
                ", rok='" + rok + '\'' +
                ", datado='" + datado + '\'' +
                ", mailprzypom1='" + mailprzypom1 + '\'' +
                ", mailprzypom2='" + mailprzypom2 + '\'' +
                '}';
    }
}

