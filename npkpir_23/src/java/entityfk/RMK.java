/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

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
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(uniqueConstraints = {
    @UniqueConstraint(columnNames = {"opiskosztu", "dokid"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RMK.findAll", query = "SELECT t FROM RMK t"),
    @NamedQuery(name = "RMK.findByPodatnikRok", query = "SELECT t FROM RMK t WHERE t.dokfk.podatnikObj = :podatnikObj AND t.rokkosztu = :rok"),
    @NamedQuery(name = "RMK.findByPodatnikRokDokfk", query = "SELECT t FROM RMK t WHERE t.dokfk.podatnikObj = :podatnikObj AND t.rokkosztu = :rok AND t.dokfk = :dokfk")
})
public class RMK  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private int id;
    @Column(name = "opiskosztu")
    private String opiskosztu;
    @JoinColumn(name = "dokid", referencedColumnName = "id")
    private Dokfk dokfk;
    @Column(name = "kwotacalkowita")
    private double kwotacalkowita;
    @Column(name = "liczbamiesiecy")
    private int liczbamiesiecy;
    @Column(name = "kwotamiesieczna")
    private double kwotamiesieczna;
    @ManyToOne
    @JoinColumn(name = "kontokosztowe", referencedColumnName = "id")
    private Konto kontokosztowe;
     @ManyToOne
    @JoinColumn(name = "kontormk", referencedColumnName = "id")
    private Konto kontormk;
    @Size(max = 255)
    @Column(name = "dataksiegowania")
    private String dataksiegowania;
    @Column(name = "rokkosztu")
    private String rokkosztu;
    @Column(name = "mckosztu")
    private String mckosztu;
    @Column(name = "rozliczony")
    private boolean rozliczony;
    @Lob
    @Column(name = "planowane")
    private List<Double> planowane;
    @Lob
    @Column(name = "ujetewksiegach")
    private List<Double> ujetewksiegach;
    @Column(name = "procentkosztpodatkowy")
    private double procentkosztpodatkowy;

    public RMK() {
        this.planowane = new ArrayList<>();
        this.ujetewksiegach = new ArrayList<>();
        this.procentkosztpodatkowy = 100.0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    

    public String getOpiskosztu() {
        return opiskosztu;
    }

    public void setOpiskosztu(String opiskosztu) {
        this.opiskosztu = opiskosztu;
    }

     public double getKwotacalkowita() {
        return kwotacalkowita;
    }

    public void setKwotacalkowita(double kwotacalkowita) {
        this.kwotacalkowita = kwotacalkowita;
    }

    public int getLiczbamiesiecy() {
        return liczbamiesiecy;
    }

    public Dokfk getDokfk() {
        return dokfk;
    }

    public void setDokfk(Dokfk dokfk) {
        this.dokfk = dokfk;
    }

    

    public void setLiczbamiesiecy(int liczbamiesiecy) {
        this.liczbamiesiecy = liczbamiesiecy;
    }

    public double getKwotamiesieczna() {
        return kwotamiesieczna;
    }

    public void setKwotamiesieczna(double kwotamiesieczna) {
        this.kwotamiesieczna = kwotamiesieczna;
    }

    public Konto getKontokosztowe() {
        return kontokosztowe;
    }

    public void setKontokosztowe(Konto kontokosztowe) {
        this.kontokosztowe = kontokosztowe;
    }

    public String getDataksiegowania() {
        return dataksiegowania;
    }

    public void setDataksiegowania(String dataksiegowania) {
        this.dataksiegowania = dataksiegowania;
    }

    public String getRokkosztu() {
        return rokkosztu;
    }

    public void setRokkosztu(String rokkosztu) {
        this.rokkosztu = rokkosztu;
    }

    public String getMckosztu() {
        return mckosztu;
    }

    public void setMckosztu(String mckosztu) {
        this.mckosztu = mckosztu;
    }
   
    public boolean isRozliczony() {
        return rozliczony;
    }

    public void setRozliczony(boolean rozliczony) {
        this.rozliczony = rozliczony;
    }

    public List<Double> getPlanowane() {
        return planowane;
    }

    public void setPlanowane(List<Double> planowane) {
        this.planowane = planowane;
    }

    public List<Double> getUjetewksiegach() {
        return ujetewksiegach;
    }

    public void setUjetewksiegach(List<Double> ujetewksiegach) {
        this.ujetewksiegach = ujetewksiegach;
    }

    public Konto getKontormk() {
        return kontormk;
    }

    public void setKontormk(Konto kontormk) {
        this.kontormk = kontormk;
    }

    public double getProcentkosztpodatkowy() {
        return procentkosztpodatkowy;
    }

    public void setProcentkosztpodatkowy(double procentkosztpodatkowy) {
        this.procentkosztpodatkowy = procentkosztpodatkowy;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final RMK other = (RMK) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "RMK{" + "opiskosztu=" + opiskosztu + ", dokfk=" + dokfk.getDokfkSN() + ", kwotacalkowita=" + kwotacalkowita + ", liczbamiesiecy=" + liczbamiesiecy + ", kwotamiesieczna=" + kwotamiesieczna + ", kontokosztowe=" + kontokosztowe + ", kontormk=" + kontormk + ", dataksiegowania=" + dataksiegowania + ", rokkosztu=" + rokkosztu + ", mckosztu=" + mckosztu + ", rozliczony=" + rozliczony + ", planowane=" + planowane + ", ujetewksiegach=" + ujetewksiegach + '}';
    }
    
    
    
}
