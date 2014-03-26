/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracjapit37;

/**
 *
 * @author Osito
 */
class Podmiot {
    static String Podmiot;
    String Pesel;
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
    

    public Podmiot() {
        Pesel = "70052809810";
        ImiePierwsze = "GRZEGORZ";
        Nazwisko = "GRZELCZYK";
        DataUrodzenia = "1970-05-28";
        Wojewodztwo = "MAZOWIECKIE";
        Powiat = "WARSZAWSKI";
        Gmina = "M.ST.WARSZAWA";
        Ulica = "LOKALNA";
        NrDomu = "1";
        NrLokalu = "2";
        Miejscowosc = "WARSZAWA";
        KodPocztowy = "00-001";
        Poczta = "WARSZAWA";
        Podmiot = "<Podmiot1 poz=\"P_1A\" rola=\"Podatnik\"><etd:OsobaFizyczna><etd:PESEL>"+Pesel
                +"</etd:PESEL><etd:ImiePierwsze>"+ImiePierwsze+"</etd:ImiePierwsze><etd:Nazwisko>"
                +Nazwisko+"</etd:Nazwisko><etd:DataUrodzenia>"+DataUrodzenia
                +"</etd:DataUrodzenia></etd:OsobaFizyczna><etd:AdresZamieszkania rodzajAdresu=\"RAD\"><etd:AdresPol><etd:KodKraju>PL</etd:KodKraju><etd:Wojewodztwo>"
                +Wojewodztwo+"</etd:Wojewodztwo><etd:Powiat>"+Powiat+"</etd:Powiat><etd:Gmina>"+Gmina+"</etd:Gmina><etd:Ulica>"+Ulica
                +"</etd:Ulica><etd:NrDomu>"+NrDomu+"</etd:NrDomu><etd:NrLokalu>"+NrLokalu+"</etd:NrLokalu><etd:Miejscowosc>"+Miejscowosc
                +"</etd:Miejscowosc><etd:KodPocztowy>"+KodPocztowy+"</etd:KodPocztowy><etd:Poczta>"+Poczta+"</etd:Poczta></etd:AdresPol></etd:AdresZamieszkania></Podmiot1>";
    }
    
    
    public String getPesel() {
        return Pesel;
    }

    public void setPesel(String Pesel) {
        this.Pesel = Pesel;
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

}
