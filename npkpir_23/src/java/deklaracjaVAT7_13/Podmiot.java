/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjaVAT7_13;

import embeddable.Daneteleadresowe;
import embeddable.Vatpoz;
import org.apache.commons.lang3.StringEscapeUtils;


/**
 *
 * @author Osito
 */
class Podmiot {
    static String Podmiot;
    Integer Rok;
    Integer Miesiac;
    
    
    String NIP;
    String ImiePierwsze;
    String Nazwisko;
    String DataUrodzenia;
    String Wojewodztwo;
    String Powiat;
    String Gmina;
    String Ulica;
    String NrDomu;
    String NrLokalu;
    String Miejscowosc;
    String KodPocztowy;
    String Poczta;
    

    public Podmiot(Vatpoz selected) {
        Rok = Integer.parseInt(selected.getRok());
        Miesiac = Integer.parseInt(selected.getMiesiac());
        Daneteleadresowe adres = selected.getAdres();
        NIP=adres.getNIP();
        ImiePierwsze = adres.getImiePierwsze();
        Nazwisko = adres.getNazwisko();
        DataUrodzenia = adres.getDataUrodzenia();
        Wojewodztwo = adres.getWojewodztwo();
        Powiat = adres.getPowiat();
        Gmina = adres.getGmina();
        Ulica = adres.getUlica();
        NrDomu = adres.getNrDomu();
        NrLokalu = adres.getNrLokalu();
        Miejscowosc = adres.getMiejscowosc();
        KodPocztowy = adres.getKodPocztowy();
        Poczta = adres.getPoczta();
        if(Rok == 2013 && Miesiac<4){
        Podmiot = "<Podmiot1 rola=\"Podatnik\"> <etd:OsobaFizyczna><etd:NIP>"+NIP
                +"</etd:NIP><etd:ImiePierwsze>"+ImiePierwsze+"</etd:ImiePierwsze><etd:Nazwisko>"
                +Nazwisko+"</etd:Nazwisko><etd:DataUrodzenia>"+DataUrodzenia
                +"</etd:DataUrodzenia></etd:OsobaFizyczna><etd:AdresZamieszkaniaSiedziby rodzajAdresu=\"RAD\"><etd:AdresPol><etd:KodKraju>PL</etd:KodKraju><etd:Wojewodztwo>"
                +Wojewodztwo+"</etd:Wojewodztwo><etd:Powiat>"+Powiat+"</etd:Powiat><etd:Gmina>"+Gmina+"</etd:Gmina><etd:Ulica>"+Ulica
                +"</etd:Ulica><etd:NrDomu>"+NrDomu+"</etd:NrDomu><etd:NrLokalu>"+NrLokalu+"</etd:NrLokalu><etd:Miejscowosc>"+Miejscowosc
                +"</etd:Miejscowosc><etd:KodPocztowy>"+KodPocztowy+"</etd:KodPocztowy><etd:Poczta>"+Poczta+"</etd:Poczta></etd:AdresPol></etd:AdresZamieszkaniaSiedziby></Podmiot1>";
        } else if (Rok <= 2015 && Miesiac<8){
           Podmiot = "<Podmiot1 rola=\"Podatnik\"> <etd:OsobaFizyczna><etd:NIP>"+NIP
                +"</etd:NIP><etd:ImiePierwsze>"+ImiePierwsze+"</etd:ImiePierwsze><etd:Nazwisko>"
                +Nazwisko+"</etd:Nazwisko><etd:DataUrodzenia>"+DataUrodzenia
                +"</etd:DataUrodzenia></etd:OsobaFizyczna></Podmiot1>"; 
        } else if (Rok <= 2016 && Miesiac<8) {
           Podmiot = "<Podmiot1 rola=\"Podatnik\"> <OsobaFizyczna><NIP>"+NIP
                +"</NIP><ImiePierwsze>"+ImiePierwsze+"</ImiePierwsze><Nazwisko>"
                +Nazwisko+"</Nazwisko><DataUrodzenia>"+DataUrodzenia
                +"</DataUrodzenia></OsobaFizyczna></Podmiot1>"; 
        } else  if (Rok <= 2018 && Miesiac<7) {
           Podmiot = "<Podmiot1 rola=\"Podatnik\"> <etd:OsobaFizyczna><etd:NIP>"+NIP
                +"</etd:NIP><etd:ImiePierwsze>"+ImiePierwsze+"</etd:ImiePierwsze><etd:Nazwisko>"
                +Nazwisko+"</etd:Nazwisko><etd:DataUrodzenia>"+DataUrodzenia
                +"</etd:DataUrodzenia></etd:OsobaFizyczna></Podmiot1>"; 
        } else  {
           Podmiot = "<Podmiot1 rola=\"Podatnik\"> <OsobaFizyczna><etd:NIP>"+NIP
                +"</etd:NIP><etd:ImiePierwsze>"+ImiePierwsze+"</etd:ImiePierwsze><etd:Nazwisko>"
                +Nazwisko+"</etd:Nazwisko><etd:DataUrodzenia>"+DataUrodzenia
                +"</etd:DataUrodzenia></OsobaFizyczna></Podmiot1>"; 
        }
    }
    
    

    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }
   
    public String getImiePierwsze() {
        return ImiePierwsze;
    }

    public void setImiePierwsze(String ImiePierwsze) {
        this.ImiePierwsze = ImiePierwsze;
    }

    public String getNazwisko() {
        return Nazwisko;
    }

    public void setNazwisko(String Nazwisko) {
        this.Nazwisko = Nazwisko;
    }

    public String getDataUrodzenia() {
        return DataUrodzenia;
    }

    public void setDataUrodzenia(String DataUrodzenia) {
        this.DataUrodzenia = DataUrodzenia;
    }

    public String getWojewodztwo() {
        return Wojewodztwo;
    }

    public void setWojewodztwo(String Wojewodztwo) {
        this.Wojewodztwo = Wojewodztwo;
    }

    public String getPowiat() {
        return Powiat;
    }

    public void setPowiat(String Powiat) {
        this.Powiat = Powiat;
    }

    public String getGmina() {
        return Gmina;
    }

    public void setGmina(String Gmina) {
        this.Gmina = Gmina;
    }

    public String getUlica() {
        return Ulica;
    }

    public void setUlica(String Ulica) {
        this.Ulica = Ulica;
    }

    public String getNrDomu() {
        return NrDomu;
    }

    public void setNrDomu(String NrDomu) {
        this.NrDomu = NrDomu;
    }

    public String getNrLokalu() {
        return NrLokalu;
    }

    public void setNrLokalu(String NrLokalu) {
        this.NrLokalu = NrLokalu;
    }

    public String getMiejscowosc() {
        return Miejscowosc;
    }

    public void setMiejscowosc(String Miejscowosc) {
        this.Miejscowosc = Miejscowosc;
    }

    public String getKodPocztowy() {
        return KodPocztowy;
    }

    public void setKodPocztowy(String KodPocztowy) {
        this.KodPocztowy = KodPocztowy;
    }

    public String getPoczta() {
        return Poczta;
    }

    public void setPoczta(String Poczta) {
        this.Poczta = Poczta;
    }

    public String getPodmiot() {
        return Podmiot;
    }

    public static void main(String[] args) {
        String escapedBodyValue = StringEscapeUtils.escapeXml("MWP D&H UG");
    }
    
}
