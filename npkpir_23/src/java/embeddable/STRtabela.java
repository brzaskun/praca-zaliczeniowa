/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.SrodekTrw;
import java.math.BigDecimal;

/**
 *
 * @author Osito
 */
public class STRtabela {

    private Integer id;
    private String podatnik;
    private String symbol;
    private String nazwa;
    private String datawy;
    private String dataprzek;
    private String datazak;
    private String kst;
    private Double odpisrok;
    private Double netto;
    private Double styczen;
    private Double luty;
    private Double marzec;
    private Double kwiecien;
    private Double maj;
    private Double czerwiec;
    private Double lipiec;
    private Double sierpien;
    private Double wrzesien;
    private Double pazdziernik;
    private Double listopad;
    private Double grudzien;
    private BigDecimal umorzeniaDo;
    private BigDecimal pozostaloDoUmorzenia;

    public STRtabela() {
        this.setStyczen(0.0);
        this.setLuty(0.0);
        this.setMarzec(0.0);
        this.setKwiecien(0.0);
        this.setMaj(0.0);
        this.setCzerwiec(0.0);
        this.setLipiec(0.0);
        this.setSierpien(0.0);
        this.setWrzesien(0.0);
        this.setPazdziernik(0.0);
        this.setListopad(0.0);
        this.setGrudzien(0.0);
    }

    
    public STRtabela(int i, SrodekTrw str) {
        this.setId(i);
        this.setNazwa(str.getNazwa());
        this.setKst(str.getKst());
        this.setOdpisrok(0.0);
        this.setSymbol(str.getSymbol());
        this.setDatazak(str.getDatazak());
        this.setDataprzek(str.getDataprzek());
        this.setDatawy("");
        this.setNetto(str.getNetto());
        this.setPodatnik(str.getPodatnik());
        this.setStyczen(0.0);
        this.setLuty(0.0);
        this.setMarzec(0.0);
        this.setKwiecien(0.0);
        this.setMaj(0.0);
        this.setCzerwiec(0.0);
        this.setLipiec(0.0);
        this.setSierpien(0.0);
        this.setWrzesien(0.0);
        this.setPazdziernik(0.0);
        this.setListopad(0.0);
        this.setGrudzien(0.0);
    }
    
    

    //<editor-fold defaultstate="collapsed" desc="comment">
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getPodatnik() {
        return podatnik;
    }
    
    public void setPodatnik(String podatnik) {
        this.podatnik = podatnik;
    }
    
    
    
    public String getSymbol() {
        return symbol;
    }
    
    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }
    
    public String getNazwa() {
        return nazwa;
    }
    
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
    
    public String getDatawy() {
        return datawy;
    }
    
    public void setDatawy(String datawy) {
        this.datawy = datawy;
    }
    
    public String getDataprzek() {
        return dataprzek;
    }
    
    public void setDataprzek(String dataprzek) {
        this.dataprzek = dataprzek;
    }
    
    public String getDatazak() {
        return datazak;
    }
    
    public void setDatazak(String datazak) {
        this.datazak = datazak;
    }
    
    public String getKst() {
        return kst;
    }
    
    public void setKst(String kst) {
        this.kst = kst;
    }
    
    public Double getOdpisrok() {
        return odpisrok;
    }
    
    public void setOdpisrok(Double odpisrok) {
        this.odpisrok = odpisrok;
    }
    
    public Double getNetto() {
        return netto;
    }
    
    public void setNetto(Double netto) {
        this.netto = netto;
    }
    
    public Double getStyczen() {
        return styczen;
    }
    
    public void setStyczen(Double styczen) {
        this.styczen = styczen;
    }
    
    public Double getLuty() {
        return luty;
    }
    
    public void setLuty(Double luty) {
        this.luty = luty;
    }
    
    public Double getMarzec() {
        return marzec;
    }
    
    public void setMarzec(Double marzec) {
        this.marzec = marzec;
    }
    
    public Double getKwiecien() {
        return kwiecien;
    }
    
    public void setKwiecien(Double kwiecien) {
        this.kwiecien = kwiecien;
    }
    
    public Double getMaj() {
        return maj;
    }
    
    public void setMaj(Double maj) {
        this.maj = maj;
    }
    
    public Double getCzerwiec() {
        return czerwiec;
    }
    
    public void setCzerwiec(Double czerwiec) {
        this.czerwiec = czerwiec;
    }
    
    public Double getLipiec() {
        return lipiec;
    }
    
    public void setLipiec(Double lipiec) {
        this.lipiec = lipiec;
    }
    
    public Double getSierpien() {
        return sierpien;
    }
    
    public void setSierpien(Double sierpien) {
        this.sierpien = sierpien;
    }
    
    public Double getWrzesien() {
        return wrzesien;
    }
    
    public void setWrzesien(Double wrzesien) {
        this.wrzesien = wrzesien;
    }
    
    public Double getPazdziernik() {
        return pazdziernik;
    }
    
    public void setPazdziernik(Double pazdziernik) {
        this.pazdziernik = pazdziernik;
    }
    
    public Double getListopad() {
        return listopad;
    }
    
    public void setListopad(Double listopad) {
        this.listopad = listopad;
    }
    
    public Double getGrudzien() {
        return grudzien;
    }
    
    public void setGrudzien(Double grudzien) {
        this.grudzien = grudzien;
    }
    
    public BigDecimal getUmorzeniaDo() {
        return umorzeniaDo;
    }
    
    public void setUmorzeniaDo(BigDecimal umorzeniaDo) {
        this.umorzeniaDo = umorzeniaDo;
    }
    
    public BigDecimal getPozostaloDoUmorzenia() {
        return pozostaloDoUmorzenia;
    }
    
    public void setPozostaloDoUmorzenia(BigDecimal pozostaloDoUmorzenia) {
        this.pozostaloDoUmorzenia = pozostaloDoUmorzenia;
    }
    
    
    //</editor-fold>
    
}
