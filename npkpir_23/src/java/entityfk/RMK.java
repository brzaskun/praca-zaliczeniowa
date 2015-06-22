/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package entityfk;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(catalog = "pkpir", schema = "", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"opiskosztu", "podatnikObj", "rok","nrkolejnywserii","seriadokfk"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "RMK.findAll", query = "SELECT t FROM RMK t"),
    @NamedQuery(name = "RMK.findByPodatnikRok", query = "SELECT t FROM RMK t WHERE t.dokfk.podatnikObj = :podatnikObj AND t.rokkosztu = :rok")
})
public class RMK  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "opiskosztu")
    private String opiskosztu;
    @JoinColumns({
          @JoinColumn(name = "seriadokfk", referencedColumnName = "seriadokfk"),
          @JoinColumn(name = "nrkolejnywserii", referencedColumnName = "nrkolejnywserii"),
          @JoinColumn(name = "podatnikObj", referencedColumnName = "podatnikObj"),
          @JoinColumn(name = "rok", referencedColumnName = "rok")
     })
    @ManyToOne
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

    public RMK() {
        this.planowane = new ArrayList<>();
        this.ujetewksiegach = new ArrayList<>();
    }
    
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpiskosztu() {
        return opiskosztu;
    }

    public void setOpiskosztu(String opiskosztu) {
        this.opiskosztu = opiskosztu;
    }

    public Dokfk getDokfk() {
        return dokfk;
    }

    public void setDokfk(Dokfk dokfk) {
        this.dokfk = dokfk;
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
    
    
}
