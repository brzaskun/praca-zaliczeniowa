/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testobjects;

import java.io.Serializable;

/**
 *
 * @author Osito
 */
public class WierszCecha  implements Serializable {
    private static final long serialVersionUID = 1L;
    private int id;
    private String nazwacechy;
    private String rodzajcechy;
    private String dokfks;
    private String datawystawienia;
    private String dataoperacji;
    private String opisWiersza;
    private String opiskonta;
    private double kwota;

    public WierszCecha() {
    }

    public WierszCecha(int id, String nazwacechy, String rodzajcechy, String dokfks, String datawystawienia, String dataoperacji, String opisWiersza, String opiskonta, double kwota) {
        this.id = id;
        this.nazwacechy = nazwacechy;
        this.rodzajcechy = rodzajcechy;
        this.dokfks = dokfks;
        this.datawystawienia = datawystawienia;
        this.dataoperacji = dataoperacji;
        this.opisWiersza = opisWiersza;
        this.opiskonta = opiskonta;
        this.kwota = kwota;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNazwacechy() {
        return nazwacechy;
    }

    public void setNazwacechy(String nazwacechy) {
        this.nazwacechy = nazwacechy;
    }

    public String getRodzajcechy() {
        return rodzajcechy;
    }

    public void setRodzajcechy(String rodzajcechy) {
        this.rodzajcechy = rodzajcechy;
    }

    public String getDokfks() {
        return dokfks;
    }

    public void setDokfks(String dokfks) {
        this.dokfks = dokfks;
    }

    public String getDatawystawienia() {
        return datawystawienia;
    }

    public void setDatawystawienia(String datawystawienia) {
        this.datawystawienia = datawystawienia;
    }

    public String getDataoperacji() {
        return dataoperacji;
    }

    public void setDataoperacji(String dataoperacji) {
        this.dataoperacji = dataoperacji;
    }

    public String getOpisWiersza() {
        return opisWiersza;
    }

    public void setOpisWiersza(String opisWiersza) {
        this.opisWiersza = opisWiersza;
    }

    public String getOpiskonta() {
        return opiskonta;
    }

    public void setOpiskonta(String opiskonta) {
        this.opiskonta = opiskonta;
    }

    public double getKwota() {
        return kwota;
    }

    public void setKwota(double kwota) {
        this.kwota = kwota;
    }

    
    @Override
    public String toString() {
        return "WierszCecha{" + "id=" + id + ", nazwacechy=" + nazwacechy + ", rodzajcechy=" + rodzajcechy + ", dokfks=" + dokfks + ", datawystawienia=" + datawystawienia + ", dataoperacji=" + dataoperacji + ", opisWiersza=" + opisWiersza + ", opiskonta=" + opiskonta + ", kwota=" + kwota + '}';
    }
    
    
    
    
}
