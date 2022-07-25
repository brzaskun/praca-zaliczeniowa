/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDok;

import entity.DeklaracjaVatZT;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
/**
 *
 * @author Osito
 */

public class DeklaracjaVatZTBean implements Serializable{

    public static int sprawdzScheme(DeklaracjaVatZT deklaracjaVatSchema, List schemyDeklaracjiVat) {
        int jestjuztaka = 0;
        if (!schemyDeklaracjiVat.isEmpty() && deklaracjaVatSchema != null) {
                jestjuztaka = sprawdznazwe(deklaracjaVatSchema, schemyDeklaracjiVat);
            if (jestjuztaka == 0) {
                jestjuztaka = sprawdznazwe(deklaracjaVatSchema);
            }
        }
        return jestjuztaka;
    }

    private static int sprawdznazwe(DeklaracjaVatZT deklaracjaVatSchema, List schemyDeklaracjiVat) {
        int jesttakanazwa = 0;
        for (Iterator<DeklaracjaVatZT> it = schemyDeklaracjiVat.iterator(); it.hasNext();) {
                DeklaracjaVatZT p = it.next();
                if (deklaracjaVatSchema.getNazwaschemy().equals(p.getNazwaschemy())) {
                    jesttakanazwa = 1;
                    break;
                }
        }
        return jesttakanazwa;
    }

    private static int sprawdznazwe(DeklaracjaVatZT deklaracjaVatSchema) {
        int jesttakiokres = 0;
            if (deklaracjaVatSchema.getNazwaschemy().isEmpty()) {
                jesttakiokres = 3;
            } else if (!deklaracjaVatSchema.getNazwaschemy().startsWith("ZT-")) {
                jesttakiokres = 4;
            }
        return jesttakiokres;
    }
    
}
