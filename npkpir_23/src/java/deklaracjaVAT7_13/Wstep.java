/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import entity.DeklaracjaVatSchema;
import javax.annotation.PostConstruct;

/**
 *
 * @author Osito
 */
public class Wstep {
    
    private String wstep; 

    public Wstep(DeklaracjaVatSchema schema) {
        if (schema.getNazwaschemy().equals("M-15")) {
            wstep = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Deklaracja xmlns=\"http://crd.gov.pl/wzor/2015/09/04/2567/\">";
        } else {
            wstep = "<Deklaracja xmlns=\"http://crd.gov.pl/wzor/"+schema.getWstep()+"\" xmlns:etd=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2011/06/21/eD/DefinicjeTypy/\">";
        }
    }

    public Wstep() {
    }
    
    
    
    public String getWstep() {
        return wstep;
    }

    
}

