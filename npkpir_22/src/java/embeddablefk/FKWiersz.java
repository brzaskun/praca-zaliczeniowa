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
public class FKWiersz implements Serializable{
    private int id;
    private String podatnik;
    private String dataksiegowania;
    private String opis;
    private String kontoWn;
    private Double kwotaWn;
    private String kontoMa;
    private Double kwotaMa;
    private Konto konto;
    private int typwiersza; //0 pelny, 1 winien, 2 ma
    private Boolean zaksiegowane;

    public FKWiersz() {
    }

    public FKWiersz(int id, int typwiersza) {
        this.id = id;
        this.typwiersza = typwiersza;
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

    public Double getKwotaWn() {
        return kwotaWn;
    }

    public void setKwotaWn(Double kwotaWn) {
        this.kwotaWn = kwotaWn;
    }

    public Double getKwotaMa() {
        return kwotaMa;
    }

    public void setKwotaMa(Double kwotaMa) {
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
        final FKWiersz other = (FKWiersz) obj;
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

    /**
     * @return the typwiersza
     */
    public int getTypwiersza() {
        return typwiersza;
    }

    /**
     * @param typwiersza the typwiersza to set
     */
    public void setTypwiersza(int typwiersza) {
        this.typwiersza = typwiersza;
    }

    
    
}
