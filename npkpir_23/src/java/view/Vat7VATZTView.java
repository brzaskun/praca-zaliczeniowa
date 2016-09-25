/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import deklaracjaVAT7_13.VATZT;
import entity.DeklaracjaVatZZPowod;
import entity.Deklaracjevat;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class Vat7VATZTView extends Vat7DKView implements Serializable{
    public static String dekl = "<?xml version=\"1.0\" encoding=\"utf-8\"?><ns:Deklaracja xmlns:etd=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2011/06/21/eD/DefinicjeTypy/\" xmlns:ns=\"http://crd.gov.pl/wzor/2013/01/17/1085/\" xmlns:vzd=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2013/01/10/eD/VATZD/\" xmlns:vzt=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2012/08/28/eD/VATZT/\" xmlns:vzz=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2012/08/28/eD/VATZZ/\" xmlns:zzu=\"http://crd.gov.pl/xml/schematy/dziedzinowe/mf/2011/10/07/eD/ORDZU/\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://crd.gov.pl/wzor/2013/01/17/1085/ file:///C:/uslugi/schemat%20(2).xsd\"><ns:Naglowek><ns:KodFormularza kodSystemowy=\"VAT-7 (13)\" kodPodatku=\"VAT\" rodzajZobowiazania=\"Z\" wersjaSchemy=\"1-0E\">VAT-7</ns:KodFormularza><ns:WariantFormularza>13</ns:WariantFormularza><ns:CelZlozenia poz=\"P_7\">2</ns:CelZlozenia><ns:Rok>2013</ns:Rok><ns:Miesiac>2</ns:Miesiac><ns:KodUrzedu>3214</ns:KodUrzedu></ns:Naglowek><ns:Podmiot1 rola=\"Podatnik\"> <etd:OsobaFizyczna><etd:NIP>8541273736</etd:NIP><etd:ImiePierwsze>PRZEMYSŁAW</etd:ImiePierwsze><etd:Nazwisko>BORYSIEWICZ</etd:Nazwisko><etd:DataUrodzenia>1968-05-08</etd:DataUrodzenia></etd:OsobaFizyczna><etd:AdresZamieszkaniaSiedziby rodzajAdresu=\"RAD\"><etd:AdresPol><etd:KodKraju>PL</etd:KodKraju><etd:Wojewodztwo>ZACHODNIOPOMORSKIE</etd:Wojewodztwo><etd:Powiat>STARGARDZKI</etd:Powiat><etd:Gmina>CHOCIWEL</etd:Gmina><etd:Ulica>ZWYCIĘZCÓW</etd:Ulica><etd:NrDomu>9</etd:NrDomu><etd:NrLokalu>-</etd:NrLokalu><etd:Miejscowosc>CHOCIWEL</etd:Miejscowosc><etd:KodPocztowy>73-120</etd:KodPocztowy><etd:Poczta>CHOCIWEL</etd:Poczta></etd:AdresPol></etd:AdresZamieszkaniaSiedziby></ns:Podmiot1><ns:PozycjeSzczegolowe><ns:P_29>74929</ns:P_29><ns:P_30>17236</ns:P_30><ns:P_45>74929</ns:P_45><ns:P_46>17236</ns:P_46><ns:P_47>56</ns:P_47><ns:P_51>58070</ns:P_51><ns:P_52>13354</ns:P_52><ns:P_55>13410</ns:P_55><ns:P_57>0</ns:P_57><ns:P_58>3826</ns:P_58><ns:P_60>0</ns:P_60><ns:P_61>0</ns:P_61><ns:P_65>0</ns:P_65></ns:PozycjeSzczegolowe><ns:Pouczenie>W przypadku niewpłacenia w obowiązującym terminie kwoty z poz.58 lub wpłacenia jej w niepełnej wysokości, niniejsza deklaracja stanowi podstawę do wystawienia tytułu wykonawczego, zgodnie z przepisami ustawy z dnia 17 czerwca 1966 r. o postępowaniu egzekucyjnym w administracji (Dz.U. z 2012 r. poz. 1015, z późn. zm.).</ns:Pouczenie><ns:Oswiadczenie>Oświadczam, że są mi znane przepisy Kodeksu karnego skarbowego o odpowiedzialności za podanie danych niezgodnych z rzeczywistością.</ns:Oswiadczenie><podp:DaneAutoryzujace xmlns:podp=\"http://e-deklaracje.mf.gov.pl/Repozytorium/Definicje/Podpis/\"><podp:NIP>8541273736</podp:NIP><podp:ImiePierwsze>PRZEMYSŁAW</podp:ImiePierwsze><podp:Nazwisko>BORYSIEWICZ</podp:Nazwisko><podp:DataUrodzenia>1968-05-08</podp:DataUrodzenia><podp:Kwota>1</podp:Kwota></podp:DaneAutoryzujace></ns:Deklaracja>";

    public static void main(String[] args) {
        System.out.println(dekl);
        int lastIndexOf = dekl.lastIndexOf("<podp:DaneAutoryzujace");
        System.out.println(lastIndexOf);
        dekl = dekl.substring(0, lastIndexOf);
    }
    private String kwota;
    private String informacja;
    private DeklaracjaVatZZPowod powod;
    
    public void dodajzalacznikVATZT() throws IOException{
        String rok = wpisView.getRokWpisu().toString();
        String mc = wpisView.getMiesiacWpisu();
        String podatnik = wpisView.getPodatnikWpisu();
        Deklaracjevat temp = deklaracjevatDAO.findDeklaracjeDowyslania(podatnik);
        Msg.msg("i","Wprowadzono tresc informacji "+informacja,"formX:msg");
        String zalacznik;
        String trescdeklaracji = temp.getDeklaracja();
        //pozbywamy sie koncowki </ns:Deklaracja> ale szukamy wpierw czy isteje juz inny zalacznik
        int lastIndexOf = trescdeklaracji.lastIndexOf("</Zalaczniki>");
        if (lastIndexOf == -1) {
            zalacznik = new VATZT(kwota,informacja,0).getVatzt();
            lastIndexOf = trescdeklaracji.lastIndexOf("<podp:DaneAutoryzujace");
        } else {
            zalacznik = new VATZT(kwota,informacja,1).getVatzt();
        }
        String koncowka = trescdeklaracji.substring(lastIndexOf);
        trescdeklaracji = trescdeklaracji.substring(0, lastIndexOf);
        //zalaczamy zalacznik
        trescdeklaracji = trescdeklaracji+zalacznik;
        //dodajemy usuniete zakonczenie
        trescdeklaracji = trescdeklaracji+koncowka;
        temp.setDeklaracja(trescdeklaracji);
        temp.setVatzt(informacja);
        try{
            deklaracjevatDAO.edit(temp);
            Msg.msg("i","Sukces, załączono VAT-ZT.","formX:msg");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e","Wystapil błąd. Nie udało się załączyć VAT-ZT.","formX:msg");
        }
        FacesContext.getCurrentInstance().getExternalContext().redirect("ksiegowaVatdowysylki.xhtml?faces-redirect=true");
    }

    public String getInformacja() {
        return informacja;
    }

    public void setInformacja(String informacja) {
        this.informacja = informacja;
    }

    public String getKwota() {
        return kwota;
    }

    public void setKwota(String kwota) {
        this.kwota = kwota;
    }

    public DeklaracjaVatZZPowod getPowod() {
        return powod;
    }

    public void setPowod(DeklaracjaVatZZPowod powod) {
        this.powod = powod;
    }
    
    
    
}
