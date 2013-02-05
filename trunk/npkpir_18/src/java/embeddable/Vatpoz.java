/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.validation.constraints.Size;

/**
 *
 * @author Osito
 */
@Embeddable
public class Vatpoz implements Serializable {
   private static final long serialVersionUID = 1L;
    private Integer id;
    private Daneteleadresowe adres;
    private String celzlozenia;
    private String kodurzedu;
    private String kwotaautoryzacja;
    private String miesiac;
    private String nazwaurzedu;
    private String podatnik;
    private PozycjeSzczegoloweVAT pozycjeszczegolowe;
    private String rok;

    public Vatpoz() {
    }

    public Vatpoz(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Daneteleadresowe getAdres() {
        return adres;
    }

    public void setAdres(Daneteleadresowe adres) {
        this.adres = adres;
    }

    public PozycjeSzczegoloweVAT getPozycjeszczegolowe() {
        return pozycjeszczegolowe;
    }

    public void setPozycjeszczegolowe(PozycjeSzczegoloweVAT pozycjeszczegolowe) {
        this.pozycjeszczegolowe = pozycjeszczegolowe;
    }

    public String getCelzlozenia() {
        return celzlozenia;
    }

    public void setCelzlozenia(String celzlozenia) {
        this.celzlozenia = celzlozenia;
    }

    public String getKodurzedu() {
        return kodurzedu;
    }

    public void setKodurzedu(String kodurzedu) {
        this.kodurzedu = kodurzedu;
    }

    public String getKwotaautoryzacja() {
        return kwotaautoryzacja;
    }

    public void setKwotaautoryzacja(String kwotaautoryzacja) {
        this.kwotaautoryzacja = kwotaautoryzacja;
    }

    public String getMiesiac() {
        return miesiac;
    }

    public void setMiesiac(String miesiac) {
        this.miesiac = miesiac;
    }

    public String getNazwaurzedu() {
        return nazwaurzedu;
    }

    public void setNazwaurzedu(String nazwaurzedu) {
        this.nazwaurzedu = nazwaurzedu;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

   
    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    
}
