/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjapit37;

/**
 *
 * @author Osito
 */
class Naglowek {
    static String Naglowek;
    //";
    String CelZlozenia;
    String Rok;
    String Miesiac;
    String KodUrzedu;
    

    public Naglowek() {
        CelZlozenia = "2";
        Rok = "2012";
        KodUrzedu = "1449";
        Naglowek = "<Naglowek><KodFormularza kodPodatku=\"PIT\" kodSystemowy=\"PIT-37 (18)\" rodzajZobowiazania=\"Z\" wersjaSchemy=\"1-0E\">PIT-37</KodFormularza>"
                +"<WariantFormularza>18</WariantFormularza>"
                +"<CelZlozenia poz=\"P_10\">"+CelZlozenia+"</CelZlozenia><Rok>"+Rok+"</Rok><KodUrzedu>"+KodUrzedu+"</KodUrzedu></Naglowek>";
    }
    
    public String getCelZlozenia() {
        return CelZlozenia;
    }

    public void setCelZlozenia(String CelZlozenia) {
        this.CelZlozenia = CelZlozenia;
    }

    public String getRok() {
        return Rok;
    }

    public void setRok(String Rok) {
        this.Rok = Rok;
    }

    public String getMiesiac() {
        return Miesiac;
    }

    public void setMiesiac(String Miesiac) {
        this.Miesiac = Miesiac;
    }

    public String getKodUrzedu() {
        return KodUrzedu;
    }

    public void setKodUrzedu(String KodUrzedu) {
        this.KodUrzedu = KodUrzedu;
    }

    public String getNaglowek() {
        return Naglowek;
    }

}
