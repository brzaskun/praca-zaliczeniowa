/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import entityfk.Konto;
import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

/**
 *
 * @author Osito
 */
@Embeddable
public class FKWiersz implements Serializable{
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name="idwiersza")
    private int idwiersza;
    @Column(name="podatnik")
    private String podatnik;
    @Column(name="dataksiegowania")
    private String dataksiegowania;
    @Column(name="opis")
    private String opis;
    @JoinColumn(name="kontoWn",referencedColumnName = "id")
    private Konto kontoWn;
    @Column(name="kwotaWn")
    private double kwotaWn;
    @JoinColumn(name="kontoMa",referencedColumnName = "id")
    private Konto kontoMa;
    @Column(name="kwotaMa")
    private double kwotaMa;
    @Column(name="kontonumer")
    private String kontonumer;
    @Column(name="kontoprzeciwstawne")
    private String kontoprzeciwstawne; 
    @JoinColumn(name="konto",referencedColumnName = "id")
    private Konto konto;
    @Column(name="typwiersza")
    private int typwiersza; //0 pelny, 1 winien, 2 ma
    @Column(name="zaksiegowane")
    private Boolean zaksiegowane = false;
    @Column(name="kwotapierwotna")
    private double kwotapierwotna = 0;
    @Column(name="rozliczono")
    private double rozliczono = 0;
    @Column(name="pozostalodorozliczenia")
    private double pozostalodorozliczenia = 0;
    @Column(name="idwierszarozliczenia")
    private int idwierszarozliczenia = 0;

    public FKWiersz() {
    }

    public FKWiersz(int idwiersza, int typwiersza) {
        this.idwiersza = idwiersza;
        this.typwiersza = typwiersza;
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public int getIdwiersza() {
        return idwiersza;
    }

    public void setIdwiersza(int idwiersza) {
        this.idwiersza = idwiersza;
    }
   
    
    public String getPodatnik() {
        return podatnik;
    }
    
    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }
    
    public String getDataksiegowania() {
        return dataksiegowania;
    }
    
    public void setDataksiegowania(String dataksiegowania) {
        this.dataksiegowania = dataksiegowania;
    }
    
    public String getOpis() {
        return opis;
    }
    
    public void setOpis(String opis) {
        this.opis = opis;
    }
    
    public Konto getKontoWn() {
        return kontoWn;
    }
    
    public void setKontoWn(Konto kontoWn) {
        this.kontoWn = kontoWn;
    }
    
    public double getKwotaWn() {
        return kwotaWn;
    }
    
    public void setKwotaWn(double kwotaWn) {
        this.kwotaWn = kwotaWn;
    }
    
    public Konto getKontoMa() {
        return kontoMa;
    }
    
    public void setKontoMa(Konto kontoMa) {
        this.kontoMa = kontoMa;
    }
    
    public double getKwotaMa() {
        return kwotaMa;
    }
    
    public void setKwotaMa(double kwotaMa) {
        this.kwotaMa = kwotaMa;
    }
    
    public String getKontonumer() {
        return kontonumer;
    }
    
    public void setKontonumer(String kontonumer) {
        this.kontonumer = kontonumer;
    }
    
    public String getKontoprzeciwstawne() {
        return kontoprzeciwstawne;
    }
    
    public void setKontoprzeciwstawne(String kontoprzeciwstawne) {
        this.kontoprzeciwstawne = kontoprzeciwstawne;
    }
    
    public Konto getKonto() {
        return konto;
    }
    
    public void setKonto(Konto konto) {
        this.konto = konto;
    }
    
    public int getTypwiersza() {
        return typwiersza;
    }
    
    public void setTypwiersza(int typwiersza) {
        this.typwiersza = typwiersza;
    }
    
    public Boolean getZaksiegowane() {
        return zaksiegowane;
    }
    
    public void setZaksiegowane(Boolean zaksiegowane) {
        this.zaksiegowane = zaksiegowane;
    }
    
    public double getKwotapierwotna() {
        return kwotapierwotna;
    }
    
    public void setKwotapierwotna(double kwotapierwotna) {
        this.kwotapierwotna = kwotapierwotna;
    }
    
    public double getRozliczono() {
        return rozliczono;
    }
    
    public void setRozliczono(double rozliczono) {
        this.rozliczono = rozliczono;
    }
    
    public double getPozostalodorozliczenia() {
        return pozostalodorozliczenia;
    }
    
    public void setPozostalodorozliczenia(double pozostalodorozliczenia) {
        this.pozostalodorozliczenia = pozostalodorozliczenia;
    }

    public int getIdwierszarozliczenia() {
        return idwierszarozliczenia;
    }

    public void setIdwierszarozliczenia(int idwierszarozliczenia) {
        this.idwierszarozliczenia = idwierszarozliczenia;
    }

   
    
    
    //</editor-fold>
   
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.idwiersza;
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
        final FKWiersz other = (FKWiersz) obj;
        if (this.idwiersza != other.idwiersza) {
            return false;
        }
        return true;
    }

    
}
