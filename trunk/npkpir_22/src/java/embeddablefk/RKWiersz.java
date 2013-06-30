/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddablefk;

import entityfk.Konto;
import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class RKWiersz implements Serializable{
    private int id;
    private String podatnik;
    private String dataksiegowania;
    private String opis;
    private String kontoWn;
    private String kwotaWn;
    private String kontoMa;
    private String kwotaMa;
    private Konto konto;
    private Boolean zaksiegowane;

    public RKWiersz() {
    }

    public RKWiersz(int id) {
        this.id = id;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public String getKwotaWn() {
        return kwotaWn;
    }

    public void setKwotaWn(String kwotaWn) {
        this.kwotaWn = kwotaWn;
    }

    public String getKwotaMa() {
        return kwotaMa;
    }

    public void setKwotaMa(String kwotaMa) {
        this.kwotaMa = kwotaMa;
    }

    public Konto getKonto() {
        return konto;
    }

    public void setKonto(Konto konto) {
        this.konto = konto;
    }

    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + this.id;
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
        final RKWiersz other = (RKWiersz) obj;
        if (this.id != other.id) {
            return false;
        }
        return true;
    }

    /**
     * @return the podatnik
     */
    public String getPodatnik() {
        return podatnik;
    }

    /**
     * @param podatnik the podatnik to set
     */
    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    /**
     * @return the dataksiegowania
     */
    public String getDataksiegowania() {
        return dataksiegowania;
    }

    /**
     * @param dataksiegowania the dataksiegowania to set
     */
    public void setDataksiegowania(String dataksiegowania) {
        this.dataksiegowania = dataksiegowania;
    }

    public String getKontoWn() {
        return kontoWn;
    }

    public void setKontoWn(String kontoWn) {
        this.kontoWn = kontoWn;
    }

    public String getKontoMa() {
        return kontoMa;
    }

    public void setKontoMa(String kontoMa) {
        this.kontoMa = kontoMa;
    }

    public Boolean getZaksiegowane() {
        return zaksiegowane;
    }

    public void setZaksiegowane(Boolean zaksiegowane) {
        this.zaksiegowane = zaksiegowane;
    }

    
    
}
