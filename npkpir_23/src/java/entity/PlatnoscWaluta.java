/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import entityfk.Tabelanbp;
import entityfk.Waluty;
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
import javax.validation.constraints.NotNull;
import view.WpisView;
/**
 *
 * @author Osito
 */
@Entity
@NamedQueries({
    @NamedQuery(name = "PlatnoscWaluta.findAll", query = "SELECT o FROM Odsetki o"),
    @NamedQuery(name = "PlatnoscWaluta.findByDok", query = "SELECT o FROM PlatnoscWaluta o WHERE o.dokument = :dokument"),
    @NamedQuery(name = "PlatnoscWaluta.findByPodRokMc", query = "SELECT o FROM PlatnoscWaluta o WHERE o.dokument.podatnik = :podatnik AND o.rok = :rok AND o.mc = :mc")
})
public class PlatnoscWaluta implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private int id;
    @JoinColumn(name = "dokument", referencedColumnName = "id_dok")
    @ManyToOne
    private Dok dokument;
    @JoinColumn(name = "tabelanbp", referencedColumnName = "idtabelanbp")
    @ManyToOne
    private Tabelanbp tabelanbp;
    @JoinColumn(name = "walutadokumentu", referencedColumnName = "idwaluty")
    @ManyToOne
    private Waluty walutadokumentu;
    @Column(name = "dataplatnosci")
    private String dataplatnosci;
    @Column(name = "typplatnosci")
    private String typplatnosci;
    @Column(name = "kwota")
    private double kwota;
    @Column(name = "kwotapln")
    private double kwotapln;
    @Column(name = "roznice")
    private double roznice;
    @Column(name = "rok")
    private String rok;
    @Column(name = "mc")
    private String mc;
    @Column(name = "zaksiegowane")
    private boolean zaksiegowane;

    public PlatnoscWaluta() {
    }

    
    public PlatnoscWaluta(Dok selected) {
        this.dokument = selected;
    }

    public PlatnoscWaluta(Dok selected, WpisView wpisView) {
        this.dokument = selected;
        this.rok = wpisView.getRokWpisuSt();
        this.mc = wpisView.getMiesiacWpisu();
    }

    public double getSprzedazPlus() {
        double zwrot = 0.0;
        if (this.dokument.getRodzajedok().getSkrot().equals("sprzedaz")) {
            if (this.roznice > 0.0) {
                zwrot = this.roznice;
            }
        }
        return zwrot;
    }
    
    public double getSprzedazMinus() {
        double zwrot = 0.0;
        if (this.dokument.getRodzajedok().getSkrot().equals("sprzedaz")) {
            if (this.roznice < 0.0) {
                zwrot = this.roznice;
            }
        }
        return zwrot;
    }
    
    public double getZakupPlus() {
        double zwrot = 0.0;
        if (!this.dokument.getRodzajedok().getSkrot().equals("sprzedaz")) {
            if (this.roznice > 0.0) {
                zwrot = this.roznice;
            }
        }
        return zwrot;
    }
    
    public double getZakupMinus() {
        double zwrot = 0.0;
        if (!this.dokument.getRodzajedok().getSkrot().equals("sprzedaz")) {
            if (this.roznice < 0.0) {
                zwrot = this.roznice;
            }
        }
        return zwrot;
    }
    

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Dok getDokument() {
        return dokument;
    }

    public void setDokument(Dok dokument) {
        this.dokument = dokument;
    }

    public Tabelanbp getTabelanbp() {
        return tabelanbp;
    }

    public void setTabelanbp(Tabelanbp tabelanbp) {
        this.tabelanbp = tabelanbp;
    }

    public String getDataplatnosci() {
        return dataplatnosci;
    }

    public void setDataplatnosci(String dataplatnosci) {
        this.dataplatnosci = dataplatnosci;
    }

    public String getTypplatnosci() {
        return typplatnosci;
    }

    public void setTypplatnosci(String typplatnosci) {
        this.typplatnosci = typplatnosci;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public double getKwotapln() {
        return kwotapln;
    }

    public void setKwotapln(double kwotapln) {
        this.kwotapln = kwotapln;
    }

    public double getRoznice() {
        return roznice;
    }

    public void setRoznice(double roznice) {
        this.roznice = roznice;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public boolean isZaksiegowane() {
        return zaksiegowane;
    }

    public void setZaksiegowane(boolean zaksiegowane) {
        this.zaksiegowane = zaksiegowane;
    }

    public Waluty getWalutadokumentu() {
        return walutadokumentu;
    }

    public void setWalutadokumentu(Waluty walutadokumentu) {
        this.walutadokumentu = walutadokumentu;
    }
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.id;
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
        final PlatnoscWaluta other = (PlatnoscWaluta) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "PlatnoscWaluta{" + "dokument=" + dokument.toString2() + ", tabelanbp=" + tabelanbp.getNrtabeli() + ", data=" + dataplatnosci + ", typplatnosci=" + typplatnosci + ", kwota=" + kwota + ", kwotapln=" + kwotapln + ", roznice=" + roznice + ", rok=" + rok + ", mc=" + mc + ", zaksiegowane=" + zaksiegowane + '}';
    }
 
    
    
}
