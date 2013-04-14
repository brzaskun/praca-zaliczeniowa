/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import embeddable.Kwartaly;
import embeddable.Vatpoz;

/**
 *
 * @author Osito
 */
class Naglowek {

    String CelZlozenia;
    String Rok;
    String Miesiac;
    String KodUrzedu;
    
    static String Naglowek;

    public Naglowek() {
    }
    
    public Naglowek(Vatpoz selected, String vatokres) {
        CelZlozenia = selected.getCelzlozenia();
        Rok = selected.getRok();
        Miesiac = selected.getMiesiac();
        KodUrzedu = selected.getKodurzedu();
        if(vatokres.equals("miesięczne")){
        Naglowek = "<ns:Naglowek><ns:KodFormularza kodSystemowy=\"VAT-7 (13)\" kodPodatku=\"VAT\" rodzajZobowiazania=\"Z\" wersjaSchemy=\"1-0E\">VAT-7</ns:KodFormularza>"
                +"<ns:WariantFormularza>13</ns:WariantFormularza>"
                +"<ns:CelZlozenia poz=\"P_7\">"+CelZlozenia+"</ns:CelZlozenia><ns:Rok>"+Rok+"</ns:Rok><ns:Miesiac>"+Miesiac
                +"</ns:Miesiac><ns:KodUrzedu>"+KodUrzedu+"</ns:KodUrzedu></ns:Naglowek>";
        } else {
            String kwartal = zamienmcnakw();
            Naglowek = "<ns:Naglowek><ns:KodFormularza kodSystemowy=\"VAT-7K (7)\" kodPodatku=\"VAT\" rodzajZobowiazania=\"Z\" wersjaSchemy=\"1-0E\">VAT-7K</ns:KodFormularza>"
                +"<ns:WariantFormularza>7</ns:WariantFormularza>"
                +"<ns:CelZlozenia poz=\"P_7\">"+CelZlozenia+"</ns:CelZlozenia><ns:Rok>"+Rok+"</ns:Rok><ns:Kwartal>"+kwartal
                +"</ns:Kwartal><ns:KodUrzedu>"+KodUrzedu+"</ns:KodUrzedu></ns:Naglowek>";
        }
    }
   
   
    private String zamienmcnakw(){
        Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(Miesiac)));
        return String.valueOf(kwartal);
    }

    public String getNaglowek() {
        return Naglowek;
    }

   
}
