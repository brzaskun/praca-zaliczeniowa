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
    
    private String vatzz;

    public VATZZ() {
        vatzz = "";
    }
    
    public VATZZ(DeklaracjaVatZZ zal, DeklaracjaVatZZPowod powod, String kwota, String informacja, int innezalaczniki) {
        if (innezalaczniki == 0) {
            vatzz = "<Zalaczniki>";
        }
        vatzz += zal.getWstep();
        vatzz += zal.getNaglowek();
        vatzz += "<vzz:PozycjeSzczegolowe>";
        vatzz += "<vzz:P_"+zal.getPowod()+">"+powod.getNr()+"</vzz:P_"+zal.getPowod()+">";
        vatzz += "<vzz:P_"+zal.getKwota()+">"+kwota+"</vzz:P_"+zal.getKwota()+">";
        vatzz += "<vzz:P_"+zal.getUzasadnienie()+">"+informacja+"</vzz:P_"+zal.getUzasadnienie()+">";
        vatzz += "</vzz:PozycjeSzczegolowe></vzz:Wniosek_VAT-ZZ>";
        if (innezalaczniki == 0) {
            vatzz += "</Zalaczniki>";
        }
    }

    public String getVatzz() {
        return vatzz;
    }

    public void setVatzz(String vatzz) {
        this.vatzz = vatzz;
    }
    
    
}
