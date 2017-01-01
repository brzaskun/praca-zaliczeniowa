/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import entity.DeklaracjaVatZT;
import entity.DeklaracjaVatZZ;
import entity.DeklaracjaVatZZPowod;
import java.io.Serializable;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class VATZZ implements Serializable{
    
    private static String vatzt;

    public VATZZ() {
    }
    
    public VATZZ(DeklaracjaVatZZ zal, DeklaracjaVatZZPowod powod, String kwota, String informacja, int innezalaczniki) {
        if (innezalaczniki == 0) {
            vatzt = "<Zalaczniki>";
        }
        vatzt += zal.getWstep();
        vatzt += zal.getNaglowek();
        vatzt += "<vzz:PozycjeSzczegolowe>";
        vatzt += "<vzz:P_"+zal.getPowod()+">"+powod.getNr()+"</vzz:P_"+zal.getPowod()+">";
        vatzt += "<vzz:P_"+zal.getKwota()+">"+kwota+"</vzz:P_"+zal.getKwota()+">";
        vatzt += "<vzz:P_"+zal.getUzasadnienie()+">"+informacja+"</vzz:P_"+zal.getUzasadnienie()+">";
        vatzt += "</vzz:PozycjeSzczegolowe></vzz:Wniosek_VAT-ZZ>";
        if (innezalaczniki == 0) {
            vatzt += "</Zalaczniki>";
        }
    }

    public String getVatzt() {
        return vatzt;
    }

    public void setVatzt(String vatzt) {
        VATZZ.vatzt = vatzt;
    }
    
    
}
