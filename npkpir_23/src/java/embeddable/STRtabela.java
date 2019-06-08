/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.SrodekTrw;
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
    private String datasprzedazy;
    private String kst;
    private Double odpisrok;
    private Double netto;
    private Map<String, Double> m;
    private double umorzeniaDo;
    private double pozostaloDoUmorzenia;
    private SrodekTrw srodekTrw;

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
        this.setDatasprzedazy(str.getDatasprzedazy());
        this.m = mapaMce();
        this.srodekTrw = str;
        
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
    
    public String getDataprzeksub() {
        String zwrot = dataprzek;
        if (zwrot.length() == 10) {
            zwrot = dataprzek.substring(2, 10);
        }
        return zwrot;
    }
    
    public String getDatasprzedazysub() {
        String zwrot = datasprzedazy;
        if (zwrot !=null && zwrot.length() == 10) {
            zwrot = datasprzedazy.substring(2, 10);
        }
        return zwrot;
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
    
   

    public double getUmorzeniaDo() {
        return umorzeniaDo;
    }

    public void setUmorzeniaDo(double umorzeniaDo) {
        this.umorzeniaDo = umorzeniaDo;
    }

    public String getDatasprzedazy() {
        return datasprzedazy;
    }

    public void setDatasprzedazy(String datasprzedazy) {
        this.datasprzedazy = datasprzedazy;
    }

    public double getPozostaloDoUmorzenia() {
        return pozostaloDoUmorzenia;
    }

    public void setPozostaloDoUmorzenia(double pozostaloDoUmorzenia) {
        this.pozostaloDoUmorzenia = pozostaloDoUmorzenia;
    }
    
    
    
    //</editor-fold>

    public SrodekTrw getSrodekTrw() {
        return srodekTrw;
    }

    public void setSrodekTrw(SrodekTrw srodekTrw) {
        this.srodekTrw = srodekTrw;
    }
    
}
