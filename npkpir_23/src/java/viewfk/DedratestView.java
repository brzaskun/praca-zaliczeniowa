/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import entity.Klienci;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import wydajnosc.ConstructorInterceptor;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class DedratestView  implements Serializable {
    private double netto;
    private double vat;
    private String datadokumentu;
    private String dataoperacji;
    private String faktura;
    private Klienci klient;

    public double getNetto() {
        return netto;
    }

    public void setNetto(double netto) {
        this.netto = netto;
    }

    public double getVat() {
        return vat;
    }

    public void setVat(double vat) {
        this.vat = vat;
    }

    public String getDatadokumentu() {
        return datadokumentu;
    }

    public void setDatadokumentu(String datadokumentu) {
        this.datadokumentu = datadokumentu;
    }

    public String getDataoperacji() {
        return dataoperacji;
    }

    public void setDataoperacji(String dataoperacji) {
        this.dataoperacji = dataoperacji;
    }

    public String getFaktura() {
        return faktura;
    }

    public void setFaktura(String faktura) {
        this.faktura = faktura;
    }

    public Klienci getKlient() {
        return klient;
    }

    public void setKlient(Klienci klient) {
        this.klient = klient;
    }
    
    
}
