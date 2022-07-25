/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package deklaracje.vatue.m4;

import embeddable.Parametr;
import entity.Podatnik;
import error.E;
import java.util.List;
import org.joda.time.DateTime;
import view.WpisView;
/**
 *
 * @author Osito
 */
public class DaneAutoryzujace {
    
    public static String DaneAutoryzujace;
    
    public DaneAutoryzujace(WpisView wpisView) {
        Podatnik podmiot = wpisView.getPodatnikObiekt();
        String NIP = podmiot.getNip();
        String ImiePierwsze = podmiot.getImie();
        String Nazwisko = podmiot.getNazwisko();
        String DataUrodzenia = podmiot.getDataurodzenia();
        String Kwota = kwotaautoryzujcaPobierz(wpisView);
        DaneAutoryzujace = "<podp:DaneAutoryzujace xmlns:podp=\"http://e-deklaracje.mf.gov.pl/Repozytorium/Definicje/Podpis/\"><podp:NIP>"
                            +NIP+"</podp:NIP><podp:ImiePierwsze>"+ImiePierwsze+"</podp:ImiePierwsze><podp:Nazwisko>"+Nazwisko
                            +"</podp:Nazwisko><podp:DataUrodzenia>"+DataUrodzenia+"</podp:DataUrodzenia><podp:Kwota>"+Kwota
                            +"</podp:Kwota></podp:DaneAutoryzujace></Deklaracja>";
    }

    private String kwotaautoryzujcaPobierz(WpisView wpisView) {
        String kwotaautoryzujaca = null;
        String kwotaautoryzujacarokpop = null;
        try {
            Podatnik p = wpisView.getPodatnikObiekt();
            List<Parametr> listakwotaautoryzujaca = p.getKwotaautoryzujaca();
            if (listakwotaautoryzujaca.isEmpty()) {
                throw new Exception();
            }
            //bo wazny jet nie rok na deklaracji ale rok z ktorego sie wysyla
            DateTime datawysylki = new DateTime();
            String rokwysylki = String.valueOf(datawysylki.getYear());
            String rokpoprzedni = String.valueOf(datawysylki.getYear()-1);
            for (Parametr par : listakwotaautoryzujaca) {
                if (par.getRokOd().equals(rokpoprzedni)) {
                    kwotaautoryzujacarokpop = par.getParametr();
                }
                if (par.getRokOd().equals(rokwysylki)) {
                    kwotaautoryzujaca = par.getParametr();
                }
            }
            if (kwotaautoryzujaca == null) {
                kwotaautoryzujaca = kwotaautoryzujacarokpop;
            }
        } catch (Exception e) {
            E.e(e);
        }
        return kwotaautoryzujaca;
    }
    
    
    public String getDaneAutoryzujace() {
        return DaneAutoryzujace;
    }
    
    
}
