/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDok;

import data.Data;
import embeddable.Kwartaly;
import embeddable.Schema;
import entity.DeklaracjaVatSchema;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Osito
 */

public class DeklaracjaVatSchemaBean implements Serializable{

    public static int sprawdzScheme(DeklaracjaVatSchema deklaracjaVatSchema, List schemyDeklaracjiVat) {
        int jestjuztaka = 0;
        if (!schemyDeklaracjiVat.isEmpty() && deklaracjaVatSchema != null) {
            jestjuztaka = sprawdznazwe(deklaracjaVatSchema, schemyDeklaracjiVat);
            if (jestjuztaka == 0) {
             jestjuztaka = sprawdzokres(deklaracjaVatSchema, schemyDeklaracjiVat);
            }
        }
        return jestjuztaka;
    }

    private static int sprawdznazwe(DeklaracjaVatSchema deklaracjaVatSchema, List schemyDeklaracjiVat) {
        int jesttakanazwa = 0;
        for (Iterator<DeklaracjaVatSchema> it = schemyDeklaracjiVat.iterator(); it.hasNext();) {
                DeklaracjaVatSchema p = it.next();
                if (deklaracjaVatSchema.getNazwaschemy().equals(p.getNazwaschemy())) {
                    jesttakanazwa = 1;
                    break;
                }
        }
        return jesttakanazwa;
    }

    private static int sprawdzokres(DeklaracjaVatSchema deklaracjaVatSchema, List schemyDeklaracjiVat) {
        int jesttakiokres = 0;
        for (Iterator<DeklaracjaVatSchema> it = schemyDeklaracjiVat.iterator(); it.hasNext();) {
            DeklaracjaVatSchema p = it.next();
            if (deklaracjaVatSchema.isMc0kw1() == p.isMc0kw1()) {
                int wynik = Data.compare(deklaracjaVatSchema.getRokOd(), deklaracjaVatSchema.getMcOd(), p.getRokOd(), p.getMcOd());
                if (wynik < 1) {
                    jesttakiokres = 2;
                }
            }
        }
        return jesttakiokres;
    }

}
