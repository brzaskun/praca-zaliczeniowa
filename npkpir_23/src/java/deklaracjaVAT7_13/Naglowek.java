/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import embeddable.Kwartaly;
import embeddable.Vatpoz;
import entity.DeklaracjaVatSchema;

/**
 *
 * @author Osito
 */
public class Naglowek {
    private static String Naglowek;

   

    public Naglowek() {
    }
    
    public Naglowek(Vatpoz selected, DeklaracjaVatSchema schema) {
        String celZlozenia = selected.getCelzlozenia();
        String rok = selected.getRok();
        String miesiac = selected.getMiesiac();
        String kodUrzedu = selected.getKodurzedu();
        Integer kw = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(miesiac)));
        String kwartal = String.valueOf(kw);
        String ng = schema.getNaglowek();
        if (schema.isMc0kw1() == false) {
            Naglowek = ng+"<CelZlozenia poz=\"P_7\">"+celZlozenia+"</CelZlozenia><Rok>"+rok+"</Rok><Miesiac>"+miesiac+"</Miesiac><KodUrzedu>"+kodUrzedu+"</KodUrzedu></Naglowek>";
        } else {
            Naglowek = ng+"<CelZlozenia poz=\"P_7\">"+celZlozenia+"</CelZlozenia><Rok>"+rok+"</Rok><Kwartal>"+kwartal+"</Kwartal><KodUrzedu>"+kodUrzedu+"</KodUrzedu></Naglowek>";
        }
    }
   
   
    public String getNaglowek() {
        return Naglowek;
    }

   
}
