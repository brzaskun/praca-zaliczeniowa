/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.SrodekTrw;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
    private Map<String, Double> m;
    private BigDecimal umorzeniaDo;
    private BigDecimal pozostaloDoUmorzenia;

    public STRtabela() {
       this.m = mapaMce();
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
        this.m = mapaMce();
        
    }
    
    private Map<String, Double> mapaMce() {
        Map m = new HashMap();
        for (String mc : Mce.getMceListS()) {
            m.put(mc, 0.0);
        }
        return m;
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

    public Map<String, Double> getM() {
        return m;
    }

    public void setM(Map<String, Double> m) {
        this.m = m;
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
