/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class VatKorektaDok implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    private Integer id;
    private Integer idDeklaracji;
    private String symbolDokumentu;
    private String nipKontrahenta;
    private String nrwłasny;
    private String opisDokumnetu;
    private List<EwidencjaAddwiad> ewidencjaVAT;
    private double netto;
    private double vat;
    private double brutto;
    

    public VatKorektaDok() {
        this.ewidencjaVAT = new ArrayList<>();
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Objects.hashCode(this.symbolDokumentu);
        hash = 13 * hash + Objects.hashCode(this.nipKontrahenta);
        hash = 13 * hash + Objects.hashCode(this.nrwłasny);
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
        final VatKorektaDok other = (VatKorektaDok) obj;
        if (!Objects.equals(this.idDeklaracji, other.idDeklaracji)) {
            return false;
        }
        if (!Objects.equals(this.symbolDokumentu, other.symbolDokumentu)) {
            return false;
        }
        if (!Objects.equals(this.nipKontrahenta, other.nipKontrahenta)) {
            return false;
        }
        if (!Objects.equals(this.nrwłasny, other.nrwłasny)) {
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

    public String getSymbolDokumentu() {
        return symbolDokumentu;
    }

    public void setSymbolDokumentu(String symbolDokumentu) {
        this.symbolDokumentu = symbolDokumentu;
    }

    public String getNipKontrahenta() {
        return nipKontrahenta;
    }

    public void setNipKontrahenta(String nipKontrahenta) {
        this.nipKontrahenta = nipKontrahenta;
    }

    public String getNrwłasny() {
        return nrwłasny;
    }

    public void setNrwłasny(String nrwłasny) {
        this.nrwłasny = nrwłasny;
    }

    public String getOpisDokumnetu() {
        return opisDokumnetu;
    }

    public void setOpisDokumnetu(String opisDokumnetu) {
        this.opisDokumnetu = opisDokumnetu;
    }

    public List<EwidencjaAddwiad> getEwidencjaVAT() {
        return ewidencjaVAT;
    }

    public void setEwidencjaVAT(List<EwidencjaAddwiad> ewidencjaVAT) {
        this.ewidencjaVAT = ewidencjaVAT;
    }

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public double getBrutto() {
        return brutto;
    }

    public void setBrutto(double brutto) {
        this.brutto = brutto;
    }

    public Integer getIdDeklaracji() {
        return idDeklaracji;
    }

    public void setIdDeklaracji(Integer idDeklaracji) {
        this.idDeklaracji = idDeklaracji;
    }

            
}
