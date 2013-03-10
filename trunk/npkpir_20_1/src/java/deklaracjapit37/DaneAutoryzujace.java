/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjapit37;

/**
 *
 * @author Osito
 */
class DaneAutoryzujace {
    
    String Pesel;
    String ImiePierwsze;
    String Nazwisko;
    String DataUrodzenia;
    String Kwota;
    
    static String DaneAutoryzujace;

    public DaneAutoryzujace() {
        Podmiot podmiot = new Podmiot();
        Pesel = podmiot.getPesel();
        ImiePierwsze = podmiot.getImiePierwsze();
        Nazwisko = podmiot.getNazwisko();
        DataUrodzenia = podmiot.getDataUrodzenia();
        Kwota = "220800.00";
        DaneAutoryzujace = "<podp:DaneAutoryzujace xmlns:podp=\"http://e-deklaracje.mf.gov.pl/Repozytorium/Definicje/Podpis/\"><podp:PESEL>"
                            +Pesel+"</podp:PESEL><podp:ImiePierwsze>"+ImiePierwsze+"</podp:ImiePierwsze><podp:Nazwisko>"+Nazwisko
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
