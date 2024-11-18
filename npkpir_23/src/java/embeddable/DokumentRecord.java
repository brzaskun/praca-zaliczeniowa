/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package embeddable;

import java.util.Date;

/**
 *
 * @author Osito
 */
public class DokumentRecord {
    
    private int id;
    private String nrksiegowy;
    private String kontrahent;
    private double kwota;
    private Date dataujecia;

    public DokumentRecord(int id, String nrksiegowy, String kontrahent, double kwota, Date dataujecia) {
        this.id = id;
        this.nrksiegowy = nrksiegowy;
        this.kontrahent = kontrahent;
        this.kwota = kwota;
        this.dataujecia = dataujecia;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNrksiegowy() {
        return nrksiegowy;
    }

    public void setNrksiegowy(String nrksiegowy) {
        this.nrksiegowy = nrksiegowy;
    }

    public String getKontrahent() {
        return kontrahent;
    }

    public void setKontrahent(String kontrahent) {
        this.kontrahent = kontrahent;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    public Date getDataujecia() {
        return dataujecia;
    }

    public void setDataujecia(Date dataujecia) {
        this.dataujecia = dataujecia;
    }
    
}
