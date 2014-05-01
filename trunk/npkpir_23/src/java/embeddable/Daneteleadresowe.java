/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Embeddable
public class Daneteleadresowe implements Serializable {
    private static final long serialVersionUID = 5881566564852339314L;
    
    private String NIP;
    private String ImiePierwsze;
    private String Nazwisko;
    private String DataUrodzenia;
    private String Wojewodztwo;
    private String Powiat;
    private String Gmina;
    private String Ulica;
    private String NrDomu;
    private String NrLokalu;
    private String Miejscowosc;
    private String KodPocztowy;
    private String Poczta;

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }

    public String getImiePierwsze() {
        return ImiePierwsze;
    }

    public void setImiePierwsze(String ImiePierwsze) {
        this.ImiePierwsze = ImiePierwsze;
    }

    public String getNazwisko() {
        return Nazwisko;
    }

    public void setNazwisko(String Nazwisko) {
        this.Nazwisko = Nazwisko;
    }

    public String getDataUrodzenia() {
        return DataUrodzenia;
    }

    public void setDataUrodzenia(String DataUrodzenia) {
        this.DataUrodzenia = DataUrodzenia;
    }

    public String getWojewodztwo() {
        return Wojewodztwo;
    }

    public void setWojewodztwo(String Wojewodztwo) {
        this.Wojewodztwo = Wojewodztwo;
    }

    public String getPowiat() {
        return Powiat;
    }

    public void setPowiat(String Powiat) {
        this.Powiat = Powiat;
    }

    public String getGmina() {
        return Gmina;
    }

    public void setGmina(String Gmina) {
        this.Gmina = Gmina;
    }

    public String getUlica() {
        return Ulica;
    }

    public void setUlica(String Ulica) {
        this.Ulica = Ulica;
    }

    public String getNrDomu() {
        return NrDomu;
    }

    public void setNrDomu(String NrDomu) {
        this.NrDomu = NrDomu;
    }

    public String getNrLokalu() {
        return NrLokalu;
    }

    public void setNrLokalu(String NrLokalu) {
        this.NrLokalu = NrLokalu;
    }

    public String getMiejscowosc() {
        return Miejscowosc;
    }

    public void setMiejscowosc(String Miejscowosc) {
        this.Miejscowosc = Miejscowosc;
    }

    public String getKodPocztowy() {
        return KodPocztowy;
    }

    public void setKodPocztowy(String KodPocztowy) {
        this.KodPocztowy = KodPocztowy;
    }

    public String getPoczta() {
        return Poczta;
    }

    public void setPoczta(String Poczta) {
        this.Poczta = Poczta;
    }
    
    
    
}
