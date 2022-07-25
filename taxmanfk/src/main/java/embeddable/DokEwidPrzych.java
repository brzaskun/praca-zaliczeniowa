/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.Dok;
import entity.EVatwpis1;
import entity.Klienci;
import java.io.Serializable;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named

public class DokEwidPrzych implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long idDok;
    private String typdokumentu;
    private int nrWpkpir;
    private String nrWlDk;
    private Klienci kontr;
    private String podatnik;
    private String dataWyst;
    private String opis;
    private double kwota;
    private double kolumna_17;
    private double kolumna_15;
    private double kolumna_14;
    private double kolumna_125;
    private double kolumna_12;
    private double kolumna_10;
    private double kolumna_85;
    private double kolumna_55;
    private double kolumna_3;
    private double kolumna_2;
    private double razem;
    private String uwagi;
    private String pkpirM;
    private String pkpirR;
    private String vatM;
    private String vatR;
    private String status;
    private List<EVatwpis1> ewidencjaVAT1;
    boolean dokumentProsty;

    public DokEwidPrzych() {
    }

    public DokEwidPrzych(Dok tmp) {
        this.setIdDok(tmp.getIdDok());
        this.setTypdokumentu(tmp.getRodzajedok().getSkrot());
        this.setNrWpkpir(tmp.getNrWpkpir());
        this.setNrWlDk(tmp.getNrWlDk());
        this.setKontr(tmp.getKontr());
        this.setPodatnik(tmp.getPodatnik().getNazwapelna());
        this.setDataWyst(tmp.getDataWyst());
        this.setOpis(tmp.getOpis());
        this.setUwagi(tmp.getUwagi());
        this.setPkpirM(tmp.getPkpirM());
        this.setPkpirR(tmp.getPkpirR());
        this.setVatM(tmp.getVatM());
        this.setVatR(tmp.getVatR());
        this.setStatus(tmp.getStatus());
        this.setEwidencjaVAT1(tmp.getEwidencjaVAT1());
        this.setDokumentProsty(tmp.isDokumentProsty());
    }
    
    public Long getIdDok() {
        return idDok;
    }

    public void setIdDok(Long idDok) {
        this.idDok = idDok;
    }

    public String getTypdokumentu() {
        return typdokumentu;
    }

    public void setTypdokumentu(String typdokumentu) {
        this.typdokumentu = typdokumentu;
    }

    public int getNrWpkpir() {
        return nrWpkpir;
    }

    public void setNrWpkpir(int nrWpkpir) {
        this.nrWpkpir = nrWpkpir;
    }

    public String getNrWlDk() {
        return nrWlDk;
    }

    public void setNrWlDk(String nrWlDk) {
        this.nrWlDk = nrWlDk;
    }

    public Klienci getKontr() {
        return kontr;
    }

    public void setKontr(Klienci kontr) {
        this.kontr = kontr;
    }

    public String getPodatnik() {
        return podatnik;
    }

    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }

    public String getDataWyst() {
        return dataWyst;
    }

    public void setDataWyst(String dataWyst) {
        this.dataWyst = dataWyst;
    }

    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public double getKolumna_17() {
        return kolumna_17;
    }

    public void setKolumna_17(double kolumna_17) {
        this.kolumna_17 = kolumna_17;
    }

    public double getKolumna_15() {
        return kolumna_15;
    }

    public void setKolumna_15(double kolumna_15) {
        this.kolumna_15 = kolumna_15;
    }

    public double getKolumna_14() {
        return kolumna_14;
    }

    public void setKolumna_14(double kolumna_14) {
        this.kolumna_14 = kolumna_14;
    }

    public double getKolumna_125() {
        return kolumna_125;
    }

    public void setKolumna_125(double kolumna_125) {
        this.kolumna_125 = kolumna_125;
    }

    public double getKolumna_12() {
        return kolumna_12;
    }

    public void setKolumna_12(double kolumna_12) {
        this.kolumna_12 = kolumna_12;
    }

    public double getKolumna_10() {
        return kolumna_10;
    }

    public void setKolumna_10(double kolumna_10) {
        this.kolumna_10 = kolumna_10;
    }

    public double getKolumna_85() {
        return kolumna_85;
    }

    public void setKolumna_85(double kolumna_85) {
        this.kolumna_85 = kolumna_85;
    }

    public double getKolumna_55() {
        return kolumna_55;
    }

    public void setKolumna_55(double kolumna_55) {
        this.kolumna_55 = kolumna_55;
    }

    public double getKolumna_3() {
        return kolumna_3;
    }

    public void setKolumna_3(double kolumna_3) {
        this.kolumna_3 = kolumna_3;
    }

    public double getKolumna_2() {
        return kolumna_2;
    }

    public void setKolumna_2(double kolumna_2) {
        this.kolumna_2 = kolumna_2;
    }


    public String getUwagi() {
        return uwagi;
    }

    public void setUwagi(String uwagi) {
        this.uwagi = uwagi;
    }

    public String getPkpirM() {
        return pkpirM;
    }

    public void setPkpirM(String pkpirM) {
        this.pkpirM = pkpirM;
    }

    public String getPkpirR() {
        return pkpirR;
    }

    public void setPkpirR(String pkpirR) {
        this.pkpirR = pkpirR;
    }

    public String getVatM() {
        return vatM;
    }

    public void setVatM(String vatM) {
        this.vatM = vatM;
    }

    public String getVatR() {
        return vatR;
    }

    public void setVatR(String vatR) {
        this.vatR = vatR;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<EVatwpis1> getEwidencjaVAT1() {
        return ewidencjaVAT1;
    }

    public void setEwidencjaVAT1(List<EVatwpis1> ewidencjaVAT1) {
        this.ewidencjaVAT1 = ewidencjaVAT1;
    }

    public boolean isDokumentProsty() {
        return dokumentProsty;
    }

    public void setDokumentProsty(boolean dokumentProsty) {
        this.dokumentProsty = dokumentProsty;
    }

    public double getRazem() {
        return razem;
    }

    public void setRazem(double razem) {
        this.razem = razem;
    }
    
    
}
