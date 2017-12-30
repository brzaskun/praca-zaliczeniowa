/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.DeklaracjaVatPozycjeKoncowe;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Embeddable;
import javax.persistence.Lob;

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
    private String rodzajdeklaracji;
    private String miesiac;
    private String nazwaurzedu;
    private String podatnik;
    private PozycjeSzczegoloweVAT pozycjeszczegolowe;
    @Lob
    private List<DeklaracjaVatPozycjeKoncowe> pozycjekoncowe;
    private String rok;
    private String regon;

    public Vatpoz() {
        this.pozycjekoncowe = new ArrayList<>();
    }

    public Vatpoz(Integer id) {
        this.id = id;
        this.pozycjekoncowe = new ArrayList<>();
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

    public String getRodzajdeklaracji() {
        return rodzajdeklaracji;
    }

    public void setRodzajdeklaracji(String rodzajdeklaracji) {
        this.rodzajdeklaracji = rodzajdeklaracji;
    }
    @Lob
    public List<DeklaracjaVatPozycjeKoncowe> getPozycjekoncowe() {
        return pozycjekoncowe;
    }

    public void setPozycjekoncowe(List<DeklaracjaVatPozycjeKoncowe> pozycjekoncowe) {
        this.pozycjekoncowe = pozycjekoncowe;
    }

    public String getRegon() {
        return regon;
    }

    public void setRegon(String regon) {
        this.regon = regon;
    }
    
    
    

    @Override
    public String toString() {
        return "Rodzaj deklaracji " + rodzajdeklaracji + ", celzlozenia (1-pierwsza, 2-korekta): " + celzlozenia + ", nazwa urzedu skarbowego: " + nazwaurzedu + ", kwota autoryzacji: " + kwotaautoryzacja + ", nazwa podatnika: " + podatnik +", vat należny "+ pozycjeszczegolowe.getPole46() + ", vat naliczony "+ pozycjeszczegolowe.getPole55() + ", suma do zapłaty: " + pozycjeszczegolowe.getPole58() + ", suma do zwrotu/przeniesienia: " + pozycjeszczegolowe.getPole60();
    }

    
    
}
