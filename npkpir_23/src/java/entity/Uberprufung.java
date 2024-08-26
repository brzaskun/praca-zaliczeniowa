/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entity;

import java.io.Serializable;
import javax.persistence.*;

@Entity
@Table(name = "uberprufung", uniqueConstraints = @UniqueConstraint(columnNames = {"podatnik", "rok"}))
@NamedQueries({
    @NamedQuery(name = "Uberprufung.findAll", query = "SELECT u FROM Uberprufung u"),
    @NamedQuery(name = "Uberprufung.findById", query = "SELECT u FROM Uberprufung u WHERE u.id = :id"),
    @NamedQuery(name = "Uberprufung.findByPodatnik", query = "SELECT u FROM Uberprufung u WHERE u.podatnik = :podatnik"),
    @NamedQuery(name = "Uberprufung.findByRok", query = "SELECT u FROM Uberprufung u WHERE u.rok = :rok"),
    @NamedQuery(name = "Uberprufung.findByDatado", query = "SELECT u FROM Uberprufung u WHERE u.datado = :datado"),
    @NamedQuery(name = "Uberprufung.findByMailprzypom1", query = "SELECT u FROM Uberprufung u WHERE u.mailprzypom1 = :mailprzypom1"),
    @NamedQuery(name = "Uberprufung.findByMailprzypom2", query = "SELECT u FROM Uberprufung u WHERE u.mailprzypom2 = :mailprzypom2")
})
public class Uberprufung implements Serializable {
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

    @Column(name = "datawyslaniadourzedu")
    private String datawyslaniadourzedu;

    @Column(name = "telefondourzedu")
    private String telefondourzedu;

    @Column(name = "datatelefonuwsprawie")
    private String datatelefonuwsprawie;

    @Column(name = "powstaniezakladu")
    private boolean powstaniezakladu;

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

    public String getDatawyslaniadourzedu() {
        return datawyslaniadourzedu;
    }

    public void setDatawyslaniadourzedu(String datawyslaniadourzedu) {
        this.datawyslaniadourzedu = datawyslaniadourzedu;
    }

    public String getTelefondourzedu() {
        return telefondourzedu;
    }

    public void setTelefondourzedu(String telefondourzedu) {
        this.telefondourzedu = telefondourzedu;
    }

    public String getDatatelefonuwsprawie() {
        return datatelefonuwsprawie;
    }

    public void setDatatelefonuwsprawie(String datatelefonuwsprawie) {
        this.datatelefonuwsprawie = datatelefonuwsprawie;
    }

    public boolean isPowstaniezakladu() {
        return powstaniezakladu;
    }

    public void setPowstaniezakladu(boolean powstaniezakladu) {
        this.powstaniezakladu = powstaniezakladu;
    }

    // Constructors

    public Uberprufung() {
    }

    public Uberprufung(Podatnik podatnik, String rok, String datado, String mailprzypom1, String mailprzypom2,
                       String datawyslaniadourzedu, String telefondourzedu, String datatelefonuwsprawie, boolean powstaniezakladu) {
        this.podatnik = podatnik;
        this.rok = rok;
        this.datado = datado;
        this.mailprzypom1 = mailprzypom1;
        this.mailprzypom2 = mailprzypom2;
        this.datawyslaniadourzedu = datawyslaniadourzedu;
        this.telefondourzedu = telefondourzedu;
        this.datatelefonuwsprawie = datatelefonuwsprawie;
        this.powstaniezakladu = powstaniezakladu;
    }

    // toString method

    @Override
    public String toString() {
        return "Uberprufung{" +
                "id=" + id +
                ", podatnik=" + podatnik +
                ", rok='" + rok + '\'' +
                ", datado='" + datado + '\'' +
                ", mailprzypom1='" + mailprzypom1 + '\'' +
                ", mailprzypom2='" + mailprzypom2 + '\'' +
                ", datawyslaniadourzedu='" + datawyslaniadourzedu + '\'' +
                ", telefondourzedu='" + telefondourzedu + '\'' +
                ", datatelefonuwsprawie='" + datatelefonuwsprawie + '\'' +
                ", powstaniezakladu=" + powstaniezakladu +
                '}';
    }
}
