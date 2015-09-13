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
    static String Naglowek;

    String CelZlozenia;
    String Rok;
    String Miesiac;
    String KodUrzedu;
    

    public Naglowek() {
    }
    
    public Naglowek(Vatpoz selected, String vatokres) {
        CelZlozenia = selected.getCelzlozenia();
        Rok = selected.getRok();
        Miesiac = selected.getMiesiac();
        KodUrzedu = selected.getKodurzedu();
        if(vatokres.equals("miesięczne")){
            if(Integer.parseInt(Rok) == 2013 && Integer.parseInt(Miesiac)<4){
                //dekalracha VAT7 13
                Naglowek = "<Naglowek><KodFormularza kodSystemowy=\"VAT-7 (13)\" kodPodatku=\"VAT\" rodzajZobowiazania=\"Z\" wersjaSchemy=\"1-0E\">VAT-7</KodFormularza>"
                +"<WariantFormularza>13</WariantFormularza>"
                +"<CelZlozenia poz=\"P_7\">"+CelZlozenia+"</CelZlozenia><Rok>"+Rok+"</Rok><Miesiac>"+Miesiac
                +"</Miesiac><KodUrzedu>"+KodUrzedu+"</KodUrzedu></Naglowek>";
            } else if (Integer.parseInt(Rok) <= 2015 && Integer.parseInt(Miesiac) < 8) {
                //deklaracja VAT7 14
                Naglowek = "<Naglowek><KodFormularza kodSystemowy=\"VAT-7 (14)\" kodPodatku=\"VAT\" rodzajZobowiazania=\"Z\" wersjaSchemy=\"1-0E\">VAT-7</KodFormularza>"
                +"<WariantFormularza>14</WariantFormularza>"
                +"<CelZlozenia poz=\"P_7\">"+CelZlozenia+"</CelZlozenia><Rok>"+Rok+"</Rok><Miesiac>"+Miesiac
                +"</Miesiac><KodUrzedu>"+KodUrzedu+"</KodUrzedu></Naglowek>";
            } else {
                //deklaracja VAT7 15
                Naglowek = "<Naglowek><KodFormularza kodSystemowy=\"VAT-7 (15)\" kodPodatku=\"VAT\" rodzajZobowiazania=\"Z\" wersjaSchemy=\"1-1E\">VAT-7</KodFormularza>"
                +"<WariantFormularza>15</WariantFormularza>"
                +"<CelZlozenia poz=\"P_7\">"+CelZlozenia+"</CelZlozenia><Rok>"+Rok+"</Rok><Miesiac>"+Miesiac
                +"</Miesiac><KodUrzedu>"+KodUrzedu+"</KodUrzedu></Naglowek>";
            }
        
        } else {
            if(Integer.parseInt(Rok) == 2013 && Integer.parseInt(Miesiac)<4){
                //dekalracja VAT7K 7
                String kwartal = zamienmcnakw();
                Naglowek = "<Naglowek><KodFormularza kodSystemowy=\"VAT-7K (7)\" kodPodatku=\"VAT\" rodzajZobowiazania=\"Z\" wersjaSchemy=\"1-0E\">VAT-7K</KodFormularza>"
                +"<WariantFormularza>7</WariantFormularza>"
                +"<CelZlozenia poz=\"P_7\">"+CelZlozenia+"</CelZlozenia><Rok>"+Rok+"</Rok><Kwartal>"+kwartal
                +"</Kwartal><KodUrzedu>"+KodUrzedu+"</KodUrzedu></Naglowek>";
            } else if (Integer.parseInt(Rok) <= 2015 && Integer.parseInt(Miesiac) < 8) {
                //deklaracja VAT7K 8
                String kwartal = zamienmcnakw();
                Naglowek = "<Naglowek><KodFormularza kodSystemowy=\"VAT-7K (8)\" kodPodatku=\"VAT\" rodzajZobowiazania=\"Z\" wersjaSchemy=\"1-0E\">VAT-7K</KodFormularza>"
                +"<WariantFormularza>8</WariantFormularza>"
                +"<CelZlozenia poz=\"P_7\">"+CelZlozenia+"</CelZlozenia><Rok>"+Rok+"</Rok><Kwartal>"+kwartal
                +"</Kwartal><KodUrzedu>"+KodUrzedu+"</KodUrzedu></Naglowek>";
            } else {
                //deklaracja VAT7 15
                Naglowek = "<Naglowek><KodFormularza kodSystemowy=\"VAT-7 (9)\" kodPodatku=\"VAT\" rodzajZobowiazania=\"Z\" wersjaSchemy=\"1-1E\">VAT-7K</KodFormularza>"
                +"<WariantFormularza>9</WariantFormularza>"
                +"<CelZlozenia poz=\"P_7\">"+CelZlozenia+"</CelZlozenia><Rok>"+Rok+"</Rok><Miesiac>"+Miesiac
                +"</Miesiac><KodUrzedu>"+KodUrzedu+"</KodUrzedu></Naglowek>";
            }
           
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
