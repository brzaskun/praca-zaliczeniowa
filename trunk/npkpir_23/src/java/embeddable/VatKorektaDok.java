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

/**
 *
 * @author Osito
 */
public class VatKorektaDok implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String symbolDokumentu;
    private String nipKontrahenta;
    private String nrwłasny;
    private String opisDokumnetu;
    private List<EwidencjaAddwiad> ewidencjaVAT;

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
    
    
            
            
}
