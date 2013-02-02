/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

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
        CelZlozenia = "1";
        Rok = "2013";
        Miesiac = "2";
        KodUrzedu = "1449";
        Naglowek = "<ns:Naglowek><ns:KodFormularza kodSystemowy=\"VAT-7 (13)\" kodPodatku=\"VAT\" rodzajZobowiazania=\"Z\" wersjaSchemy=\"1-0E\">VAT-7</ns:KodFormularza>"
                +"<ns:WariantFormularza>13</ns:WariantFormularza>"
                +"<ns:CelZlozenia poz=\"P_7\">"+CelZlozenia+"</ns:CelZlozenia><ns:Rok>"+Rok+"</ns:Rok><ns:Miesiac>"+Miesiac
                +"</ns:Miesiac><ns:KodUrzedu>"+KodUrzedu+"</ns:KodUrzedu></ns:Naglowek>";
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
