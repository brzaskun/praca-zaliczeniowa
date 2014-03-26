/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import embeddable.Vatpoz;

/**
 *
 * @author Osito
 */
class DaneAutoryzujace {
    static String DaneAutoryzujace;
    
    String NIP;
    String ImiePierwsze;
    String Nazwisko;
    String DataUrodzenia;
    String Kwota;
    

    public DaneAutoryzujace(Vatpoz selected) {
        Podmiot podmiot = new Podmiot(selected);
        NIP = podmiot.getNIP();
        ImiePierwsze = podmiot.ImiePierwsze;
        Nazwisko = podmiot.getNazwisko();
        DataUrodzenia = podmiot.getDataUrodzenia();
        Kwota = selected.getKwotaautoryzacja();
        DaneAutoryzujace = "<podp:DaneAutoryzujace xmlns:podp=\"http://e-deklaracje.mf.gov.pl/Repozytorium/Definicje/Podpis/\"><podp:NIP>"
                            +NIP+"</podp:NIP><podp:ImiePierwsze>"+ImiePierwsze+"</podp:ImiePierwsze><podp:Nazwisko>"+Nazwisko
                            +"</podp:Nazwisko><podp:DataUrodzenia>"+DataUrodzenia+"</podp:DataUrodzenia><podp:Kwota>"+Kwota
                            +"</podp:Kwota></podp:DaneAutoryzujace></Deklaracja>";
    }

    public String getKwota() {
        return Kwota;
    }

    public void setKwota(String Kwota) {
        this.Kwota = Kwota;
    }

    public String getDaneAutoryzujace() {
        return DaneAutoryzujace;
    }
    
    
}
