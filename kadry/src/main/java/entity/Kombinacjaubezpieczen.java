/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import data.Data;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import kadryiplace.OsobaPropTyp;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "kombinacjaubezpieczen", uniqueConstraints = {
    @UniqueConstraint(columnNames={"opis", "dataod", "aktywne"})
})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Kombinacjaubezpieczen.findAll", query = "SELECT k FROM Kombinacjaubezpieczen k"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findById", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.id = :id"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByOpis", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.opis = :opis"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByEmerytalne", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.emerytalne = :emerytalne"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByRentowe", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.rentowe = :rentowe"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByChorobowe", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.chorobowe = :chorobowe"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByWypadkowe", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.wypadkowe = :wypadkowe"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByZdrowotne", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.zdrowotne = :zdrowotne"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByFp", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.fp = :fp"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByFgsp", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.fgsp = :fgsp"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByEmUbezp", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.emUbezp = :emUbezp"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByEmPracodawca", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.emPracodawca = :emPracodawca"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByEmBudzet", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.emBudzet = :emBudzet"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByEmPfron", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.emPfron = :emPfron"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByRentUbezp", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.rentUbezp = :rentUbezp"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByRentPracodawca", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.rentPracodawca = :rentPracodawca"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByRentBudzet", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.rentBudzet = :rentBudzet"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByRentPfron", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.rentPfron = :rentPfron"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByChorUbezp", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.chorUbezp = :chorUbezp"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByChorPracodawca", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.chorPracodawca = :chorPracodawca"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByChorBudzet", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.chorBudzet = :chorBudzet"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByChorPfron", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.chorPfron = :chorPfron"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByWypUbezp", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.wypUbezp = :wypUbezp"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByWypPracodawca", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.wypPracodawca = :wypPracodawca"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByWypBudzet", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.wypBudzet = :wypBudzet"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByWypPfron", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.wypPfron = :wypPfron"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByZdrowUbezp", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.zdrowUbezp = :zdrowUbezp"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByZdrowPracodawca", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.zdrowPracodawca = :zdrowPracodawca"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByZdrowBudzet", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.zdrowBudzet = :zdrowBudzet"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByZdrowPfron", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.zdrowPfron = :zdrowPfron"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByFpUbezp", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.fpUbezp = :fpUbezp"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByFpPracodawca", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.fpPracodawca = :fpPracodawca"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByFpBudzet", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.fpBudzet = :fpBudzet"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByFpPfron", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.fpPfron = :fpPfron"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByFgspUbezp", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.fgspUbezp = :fgspUbezp"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByFgspPracodawca", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.fgspPracodawca = :fgspPracodawca"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByFgspBudzet", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.fgspBudzet = :fgspBudzet"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByFgspPfron", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.fgspPfron = :fgspPfron"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByKolejnosc", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.kolejnosc = :kolejnosc"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByDataod", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.dataod = :dataod"),
    @NamedQuery(name = "Kombinacjaubezpieczen.findByDatado", query = "SELECT k FROM Kombinacjaubezpieczen k WHERE k.datado = :datado")})
public class Kombinacjaubezpieczen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 256)
    @Column(name = "opis", unique = true)
    private String opis;
    @Column(name = "emerytalne")
    private Boolean emerytalne;
    @Column(name = "rentowe")
    private Boolean rentowe;
    @Column(name = "chorobowe")
    private Boolean chorobowe;
    @Column(name = "wypadkowe")
    private Boolean wypadkowe;
    @Column(name = "zdrowotne")
    private Boolean zdrowotne;
    @Column(name = "fp")
    private Boolean fp;
    @Column(name = "fgsp")
    private Boolean fgsp;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "em_ubezp")
    private Double emUbezp;
    @Column(name = "em_pracodawca")
    private Double emPracodawca;
    @Column(name = "em_budzet")
    private Double emBudzet;
    @Column(name = "em_pfron")
    private Double emPfron;
    @Column(name = "rent_ubezp")
    private Double rentUbezp;
    @Column(name = "rent_pracodawca")
    private Double rentPracodawca;
    @Column(name = "rent_budzet")
    private Double rentBudzet;
    @Column(name = "rent_pfron")
    private Double rentPfron;
    @Column(name = "chor_ubezp")
    private Double chorUbezp;
    @Column(name = "chor_pracodawca")
    private Double chorPracodawca;
    @Column(name = "chor_budzet")
    private Double chorBudzet;
    @Column(name = "chor_pfron")
    private Double chorPfron;
    @Column(name = "wyp_ubezp")
    private Double wypUbezp;
    @Column(name = "wyp_pracodawca")
    private Double wypPracodawca;
    @Column(name = "wyp_budzet")
    private Double wypBudzet;
    @Column(name = "wyp_pfron")
    private Double wypPfron;
    @Column(name = "zdrow_ubezp")
    private Double zdrowUbezp;
    @Column(name = "zdrow_pracodawca")
    private Double zdrowPracodawca;
    @Column(name = "zdrow_budzet")
    private Double zdrowBudzet;
    @Column(name = "zdrow_pfron")
    private Double zdrowPfron;
    @Column(name = "fp_ubezp")
    private Double fpUbezp;
    @Column(name = "fp_pracodawca")
    private Double fpPracodawca;
    @Column(name = "fp_budzet")
    private Double fpBudzet;
    @Column(name = "fp_pfron")
    private Double fpPfron;
    @Column(name = "fgsp_ubezp")
    private Double fgspUbezp;
    @Column(name = "fgsp_pracodawca")
    private Double fgspPracodawca;
    @Column(name = "fgsp_budzet")
    private Double fgspBudzet;
    @Column(name = "fgsp_pfron")
    private Double fgspPfron;
    @Column(name = "kolejnosc")
    private Integer kolejnosc;
    @Size(max = 45)
    @Column(name = "dataod")
    private String dataod;
    @Size(max = 45)
    @Column(name = "datado")
    private String datado;
    @Column(name = "aktywne")
    private boolean aktywne;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kombinacjaubezpieczen")
    private List<Umowa> umowaList;

    public Kombinacjaubezpieczen() {
    }

    public Kombinacjaubezpieczen(Integer id) {
        this.id = id;
    }

    public Kombinacjaubezpieczen(Integer id, String opis) {
        this.id = id;
        this.opis = opis;
    }

    public Kombinacjaubezpieczen(OsobaPropTyp p) {
        this.opis = p.getOptOpis();
        this.emerytalne = p.getOptZusEmer().equals('T');
        this.rentowe = p.getOptZusRent().equals('T');
        this.chorobowe = p.getOptZusChor().equals('T');
        this.wypadkowe = p.getOptZusWyp().equals('T');
        this.zdrowotne = p.getOptZusZdro().equals('T');
        this.fp = p.getOptZusFp().equals('T');
        this.fgsp = p.getOptZusFgsp().equals('T');
        this.emUbezp = p.getOptUbEmUbez().doubleValue();
        this.emPracodawca = p.getOptUbEmPrac().doubleValue();
        this.emBudzet = p.getOptUbEmBudz().doubleValue();
        this.emPfron = p.getOptUbEmPfron().doubleValue();
        this.rentUbezp = p.getOptUbReUbez().doubleValue();
        this.rentPracodawca = p.getOptUbRePrac().doubleValue();
        this.rentBudzet = p.getOptUbReBudz().doubleValue();
        this.rentPfron = p.getOptUbRePfron().doubleValue();
        this.chorUbezp = p.getOptUbChUbez().doubleValue();
        this.chorPracodawca = p.getOptUbChPrac().doubleValue();
        this.chorBudzet = p.getOptUbChBudz().doubleValue();
        this.chorPfron = p.getOptUbChPfron().doubleValue();
        this.wypUbezp = p.getOptUbWyUbez().doubleValue();
        this.wypPracodawca = p.getOptUbWyPrac().doubleValue();
        this.wypBudzet = p.getOptUbWyBudz().doubleValue();
        this.wypPfron = p.getOptUbWyPfron().doubleValue();
        this.zdrowUbezp = p.getOptUbZdUbez().doubleValue();
        this.zdrowPracodawca = p.getOptUbZdPrac().doubleValue();
        this.zdrowBudzet = p.getOptUbZdBudz().doubleValue();
        this.zdrowPfron = p.getOptUbZdPfron().doubleValue();
        this.fpUbezp = p.getOptUbFpUbez().doubleValue();
        this.fpPracodawca = p.getOptUbFpPrac().doubleValue();
        this.fpBudzet = p.getOptUbFpBudz().doubleValue();
        this.fpPfron = p.getOptUbFpPfron().doubleValue();
        this.fgspUbezp = p.getOptUbFgUbez().doubleValue();
        this.fgspPracodawca = p.getOptUbFgPrac().doubleValue();
        this.fgspBudzet = p.getOptUbFgBudz().doubleValue();
        this.fgspPfron = p.getOptUbFgPfron().doubleValue();
        this.kolejnosc = p.getOptKolejnosc().intValue();
        this.dataod = Data.data_yyyyMMddNull(p.getOptDataOd());
        this.datado = Data.data_yyyyMMddNull(p.getOptDataDo());
        this.aktywne = p.getOptStatus().equals('A');
        this.umowaList = new ArrayList<>();
    }

   
    

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public Boolean getEmerytalne() {
        return emerytalne;
    }

    public void setEmerytalne(Boolean emerytalne) {
        this.emerytalne = emerytalne;
    }

    public Boolean getRentowe() {
        return rentowe;
    }

    public void setRentowe(Boolean rentowe) {
        this.rentowe = rentowe;
    }

    public Boolean getChorobowe() {
        return chorobowe;
    }

    public void setChorobowe(Boolean chorobowe) {
        this.chorobowe = chorobowe;
    }

    public Boolean getWypadkowe() {
        return wypadkowe;
    }

    public void setWypadkowe(Boolean wypadkowe) {
        this.wypadkowe = wypadkowe;
    }

    public Boolean getZdrowotne() {
        return zdrowotne;
    }

    public void setZdrowotne(Boolean zdrowotne) {
        this.zdrowotne = zdrowotne;
    }

    public Boolean getFp() {
        return fp;
    }

    public void setFp(Boolean fp) {
        this.fp = fp;
    }

    public Boolean getFgsp() {
        return fgsp;
    }

    public void setFgsp(Boolean fgsp) {
        this.fgsp = fgsp;
    }

    public Double getEmUbezp() {
        return emUbezp;
    }

    public void setEmUbezp(Double emUbezp) {
        this.emUbezp = emUbezp;
    }

    public Double getEmPracodawca() {
        return emPracodawca;
    }

    public void setEmPracodawca(Double emPracodawca) {
        this.emPracodawca = emPracodawca;
    }

    public Double getEmBudzet() {
        return emBudzet;
    }

    public void setEmBudzet(Double emBudzet) {
        this.emBudzet = emBudzet;
    }

    public Double getEmPfron() {
        return emPfron;
    }

    public void setEmPfron(Double emPfron) {
        this.emPfron = emPfron;
    }

    public Double getRentUbezp() {
        return rentUbezp;
    }

    public void setRentUbezp(Double rentUbezp) {
        this.rentUbezp = rentUbezp;
    }

    public Double getRentPracodawca() {
        return rentPracodawca;
    }

    public void setRentPracodawca(Double rentPracodawca) {
        this.rentPracodawca = rentPracodawca;
    }

    public Double getRentBudzet() {
        return rentBudzet;
    }

    public void setRentBudzet(Double rentBudzet) {
        this.rentBudzet = rentBudzet;
    }

    public Double getRentPfron() {
        return rentPfron;
    }

    public void setRentPfron(Double rentPfron) {
        this.rentPfron = rentPfron;
    }

    public Double getChorUbezp() {
        return chorUbezp;
    }

    public void setChorUbezp(Double chorUbezp) {
        this.chorUbezp = chorUbezp;
    }

    public Double getChorPracodawca() {
        return chorPracodawca;
    }

    public void setChorPracodawca(Double chorPracodawca) {
        this.chorPracodawca = chorPracodawca;
    }

    public Double getChorBudzet() {
        return chorBudzet;
    }

    public void setChorBudzet(Double chorBudzet) {
        this.chorBudzet = chorBudzet;
    }

    public Double getChorPfron() {
        return chorPfron;
    }

    public void setChorPfron(Double chorPfron) {
        this.chorPfron = chorPfron;
    }

    public Double getWypUbezp() {
        return wypUbezp;
    }

    public void setWypUbezp(Double wypUbezp) {
        this.wypUbezp = wypUbezp;
    }

    public Double getWypPracodawca() {
        return wypPracodawca;
    }

    public void setWypPracodawca(Double wypPracodawca) {
        this.wypPracodawca = wypPracodawca;
    }

    public Double getWypBudzet() {
        return wypBudzet;
    }

    public void setWypBudzet(Double wypBudzet) {
        this.wypBudzet = wypBudzet;
    }

    public Double getWypPfron() {
        return wypPfron;
    }

    public void setWypPfron(Double wypPfron) {
        this.wypPfron = wypPfron;
    }

    public Double getZdrowUbezp() {
        return zdrowUbezp;
    }

    public void setZdrowUbezp(Double zdrowUbezp) {
        this.zdrowUbezp = zdrowUbezp;
    }

    public Double getZdrowPracodawca() {
        return zdrowPracodawca;
    }

    public void setZdrowPracodawca(Double zdrowPracodawca) {
        this.zdrowPracodawca = zdrowPracodawca;
    }

    public Double getZdrowBudzet() {
        return zdrowBudzet;
    }

    public void setZdrowBudzet(Double zdrowBudzet) {
        this.zdrowBudzet = zdrowBudzet;
    }

    public Double getZdrowPfron() {
        return zdrowPfron;
    }

    public void setZdrowPfron(Double zdrowPfron) {
        this.zdrowPfron = zdrowPfron;
    }

    public Double getFpUbezp() {
        return fpUbezp;
    }

    public void setFpUbezp(Double fpUbezp) {
        this.fpUbezp = fpUbezp;
    }

    public Double getFpPracodawca() {
        return fpPracodawca;
    }

    public void setFpPracodawca(Double fpPracodawca) {
        this.fpPracodawca = fpPracodawca;
    }

    public Double getFpBudzet() {
        return fpBudzet;
    }

    public void setFpBudzet(Double fpBudzet) {
        this.fpBudzet = fpBudzet;
    }

    public Double getFpPfron() {
        return fpPfron;
    }

    public void setFpPfron(Double fpPfron) {
        this.fpPfron = fpPfron;
    }

    public Double getFgspUbezp() {
        return fgspUbezp;
    }

    public void setFgspUbezp(Double fgspUbezp) {
        this.fgspUbezp = fgspUbezp;
    }

    public Double getFgspPracodawca() {
        return fgspPracodawca;
    }

    public void setFgspPracodawca(Double fgspPracodawca) {
        this.fgspPracodawca = fgspPracodawca;
    }

    public Double getFgspBudzet() {
        return fgspBudzet;
    }

    public void setFgspBudzet(Double fgspBudzet) {
        this.fgspBudzet = fgspBudzet;
    }

    public Double getFgspPfron() {
        return fgspPfron;
    }

    public void setFgspPfron(Double fgspPfron) {
        this.fgspPfron = fgspPfron;
    }

    public Integer getKolejnosc() {
        return kolejnosc;
    }

    public void setKolejnosc(Integer kolejnosc) {
        this.kolejnosc = kolejnosc;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    public List<Umowa> getUmowaList() {
        return umowaList;
    }

    public void setUmowaList(List<Umowa> umowaList) {
        this.umowaList = umowaList;
    }

    public boolean isAktywne() {
        return aktywne;
    }

    public void setAktywne(boolean aktywne) {
        this.aktywne = aktywne;
    }

    

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 37 * hash + Objects.hashCode(this.id);
        hash = 37 * hash + Objects.hashCode(this.opis);
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
        final Kombinacjaubezpieczen other = (Kombinacjaubezpieczen) obj;
        if (!Objects.equals(this.opis, other.opis)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

   

    @Override
    public String toString() {
        return "Kombinacjaubezpieczen{" + "opis=" + opis + ", emerytalne=" + emerytalne + ", rentowe=" + rentowe + ", chorobowe=" + chorobowe + ", wypadkowe=" + wypadkowe + ", zdrowotne=" + zdrowotne + ", fp=" + fp + ", fgsp=" + fgsp + ", dataod=" + dataod + ", datado=" + datado + '}';
    }

   
    
}
