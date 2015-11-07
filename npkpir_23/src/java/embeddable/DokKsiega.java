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
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Named

public class DokKsiega implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Long idDok;
    private String typdokumentu;
    private int nrWpkpir;
    private String nrWlDk;
    private Klienci kontr;
    private String podatnik;
    private String dataWyst;
    private String opis;
    private Double kwota;
    private Double kolumna7;
    private Double kolumna8;
    private Double kolumna9;
    private Double kolumna10;
    private Double kolumna11;
    private Double kolumna12;
    private Double kolumna13;
    private Double kolumna14;
    private Double kolumna15;
    private String uwagi;
    private String pkpirM;
    private String pkpirR;
    private String vatM;
    private String vatR;
    private String status;
    private List<EVatwpis1> ewidencjaVAT1;
    boolean dokumentProsty;

    public DokKsiega() {
    }

    public DokKsiega(Dok tmp) {
        this.setIdDok(tmp.getIdDok());
        this.setTypdokumentu(tmp.getTypdokumentu());
        this.setNrWpkpir(tmp.getNrWpkpir());
        this.setNrWlDk(tmp.getNrWlDk());
        this.setKontr(tmp.getKontr());
        this.setPodatnik(tmp.getPodatnik());
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

    @Override
    public String toString() {
        return "DokKsiega{" + "idDok=" + idDok + ", typdokumentu=" + typdokumentu + ", nrWpkpir=" + nrWpkpir + ", nrWlDk=" + nrWlDk + ", kontr=" + kontr + ", podatnik=" + podatnik + ", dataWyst=" + dataWyst + ", opis=" + opis + '}';
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

    public Double getKwota() {
        return kwota;
    }

    public void setKwota(Double kwota) {
        this.kwota = kwota;
    }

    public Double getKolumna7() {
        return kolumna7;
    }

    public void setKolumna7(Double kolumna7) {
        this.kolumna7 = kolumna7;
    }

    public Double getKolumna8() {
        return kolumna8;
    }

    public void setKolumna8(Double kolumna8) {
        this.kolumna8 = kolumna8;
    }

    public Double getKolumna9() {
        return kolumna9;
    }

    public void setKolumna9(Double kolumna9) {
        this.kolumna9 = kolumna9;
    }

    public Double getKolumna10() {
        return kolumna10;
    }

    public void setKolumna10(Double kolumna10) {
        this.kolumna10 = kolumna10;
    }

    public Double getKolumna11() {
        return kolumna11;
    }

    public void setKolumna11(Double kolumna11) {
        this.kolumna11 = kolumna11;
    }

    public Double getKolumna12() {
        return kolumna12;
    }

    public void setKolumna12(Double kolumna12) {
        this.kolumna12 = kolumna12;
    }

    public Double getKolumna13() {
        return kolumna13;
    }

    public void setKolumna13(Double kolumna13) {
        this.kolumna13 = kolumna13;
    }

    public Double getKolumna14() {
        return kolumna14;
    }

    public void setKolumna14(Double kolumna14) {
        this.kolumna14 = kolumna14;
    }

    public Double getKolumna15() {
        return kolumna15;
    }

    public void setKolumna15(Double kolumna15) {
        this.kolumna15 = kolumna15;
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
    
    
}
