/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.SessionScoped;
import javax.inject.Named;
import javax.persistence.Embeddable;

/**
 *
 * @author Osito
 */
@Named
@Embeddable
@SessionScoped
public class Panstwa implements Serializable {
    private final static List<String> wykazPanstw;
    
static{
    wykazPanstw = new ArrayList<>();
wykazPanstw.add("Afghanistan");
wykazPanstw.add("Albania");
wykazPanstw.add("Algieria");
wykazPanstw.add("Andora");
wykazPanstw.add("Angola");
wykazPanstw.add("Anguilla");
wykazPanstw.add("Antarktyda");
wykazPanstw.add("Antigua i Barbuda");
wykazPanstw.add("Antyle Holenderskie");
wykazPanstw.add("Arabia Saudyjska");

wykazPanstw.add("Argentyna");
wykazPanstw.add("Armenia");
wykazPanstw.add("Aruba");
wykazPanstw.add("Australia");
wykazPanstw.add("Austria");
wykazPanstw.add("Azerbejdżan");
wykazPanstw.add("Bahamy");
wykazPanstw.add("Bahrajn");
wykazPanstw.add("Bangladesz");
wykazPanstw.add("Barbados");

wykazPanstw.add("Belgia");
wykazPanstw.add("Belize");
wykazPanstw.add("Benin");
wykazPanstw.add("Bermudy");
wykazPanstw.add("Bhutan");
wykazPanstw.add("Białoruś");
wykazPanstw.add("Boliwia");
wykazPanstw.add("Bośnia i Hercegowina");
wykazPanstw.add("Botswana");
wykazPanstw.add("Brazylia");

wykazPanstw.add("Brunei Darussalam");
wykazPanstw.add("Brytyjskie Terytorium Oceanu Indyjskiego");
wykazPanstw.add("Bułgaria");
wykazPanstw.add("Burkina Faso");
wykazPanstw.add("Burundi");
wykazPanstw.add("Ceuta");
wykazPanstw.add("Chile");
wykazPanstw.add("Chiny");
wykazPanstw.add("Chorwacja");
wykazPanstw.add("Cypr");

wykazPanstw.add("Czad");
wykazPanstw.add("Czarnogóra");
wykazPanstw.add("Dania");
wykazPanstw.add("Dominika");
wykazPanstw.add("Dominikana");
wykazPanstw.add("Dżibuti");
wykazPanstw.add("Egipt");
wykazPanstw.add("Ekwador");
wykazPanstw.add("Erytrea");
wykazPanstw.add("Estonia");

wykazPanstw.add("Etiopia");
wykazPanstw.add("Falklandy");
wykazPanstw.add("Fidżi Republika");
wykazPanstw.add("Filipiny");
wykazPanstw.add("Finlandia");
wykazPanstw.add("Francuskie Terytorium Południowe");
wykazPanstw.add("Francja");
wykazPanstw.add("Gabon");
wykazPanstw.add("Gambia");
wykazPanstw.add("Ghana");

wykazPanstw.add("Gibraltar");
wykazPanstw.add("Grecja");
wykazPanstw.add("Grenada");
wykazPanstw.add("Grenlandia");
wykazPanstw.add("Gruzja");
wykazPanstw.add("Guam");
wykazPanstw.add("Gujana");
wykazPanstw.add("Gwatemala");
wykazPanstw.add("Gwinea");
wykazPanstw.add("Gwinea Równikowa");

wykazPanstw.add("Gwinea-Bissau");
wykazPanstw.add("Haiti");
wykazPanstw.add("Hiszpania");
wykazPanstw.add("Honduras");
wykazPanstw.add("Hongkong");
wykazPanstw.add("Indie");
wykazPanstw.add("Indonezja");
wykazPanstw.add("Irak");
wykazPanstw.add("Iran");
wykazPanstw.add("Irlandia");

wykazPanstw.add("Islandia");
wykazPanstw.add("Izrael");
wykazPanstw.add("Jamajka");
wykazPanstw.add("Japonia");
wykazPanstw.add("Jemen");
wykazPanstw.add("Jordania");
wykazPanstw.add("Kajmany");
wykazPanstw.add("Kambodża");
wykazPanstw.add("Kamerun");
wykazPanstw.add("Kanada");

wykazPanstw.add("Katar");
wykazPanstw.add("Kazachstan");
wykazPanstw.add("Kenia");
wykazPanstw.add("Kirgistan");
wykazPanstw.add("Kiribati");
wykazPanstw.add("Kolumbia");
wykazPanstw.add("Komory");
wykazPanstw.add("Kongo");
wykazPanstw.add("Kongo, Republika Demokratyczna");
wykazPanstw.add("Koreańska Republika Ludowo-Demokratyczna");

wykazPanstw.add("Kosowo");
wykazPanstw.add("Kostaryka");
wykazPanstw.add("Kraje i terytoria niewyszczególnione w ramach handlu wewnątrzwspólnotowego");
wykazPanstw.add("Kuba");
wykazPanstw.add("Kuwejt");
wykazPanstw.add("Laos");
wykazPanstw.add("Lesotho");
wykazPanstw.add("Liban");
wykazPanstw.add("Liberia");
wykazPanstw.add("Libia");

wykazPanstw.add("Liechtenstein");
wykazPanstw.add("Litwa");
wykazPanstw.add("Luksemburg");
wykazPanstw.add("Łotwa");
wykazPanstw.add("Macedonia");
wykazPanstw.add("Madagaskar");
wykazPanstw.add("Majotta");
wykazPanstw.add("Makau");
wykazPanstw.add("Malawi");
wykazPanstw.add("Malediwy");

wykazPanstw.add("Malezja");
wykazPanstw.add("Mali");
wykazPanstw.add("Malta");
wykazPanstw.add("Mariany Północne");
wykazPanstw.add("Maroko Włącznie z Saharą Zachodnią EH");
wykazPanstw.add("Mauretania");
wykazPanstw.add("Mauritius");
wykazPanstw.add("Meksyk");
wykazPanstw.add("Melilla");
wykazPanstw.add("Mikronezja");

wykazPanstw.add("Minor (Powiernicze Wyspy Pacyfiku Stanów Zjednoczonych)");
wykazPanstw.add("Mołdowa");
wykazPanstw.add("Mongolia");
wykazPanstw.add("Montserrat");
wykazPanstw.add("Mozambik");
wykazPanstw.add("Myanmar (Burma)");
wykazPanstw.add("Namibia");
wykazPanstw.add("Nauru");
wykazPanstw.add("Nepal");
wykazPanstw.add("Niderlandy");

wykazPanstw.add("Niemcy");
wykazPanstw.add("Niger");
wykazPanstw.add("Nigeria");
wykazPanstw.add("Nikaragua");
wykazPanstw.add("Niue");
wykazPanstw.add("Norfolk");
wykazPanstw.add("Norwegia Włącznie z wyspami Svalbard i Jan Mayen SJ");
wykazPanstw.add("Nowa Kaledonia");
wykazPanstw.add("Nowa Zelandia");
wykazPanstw.add("Okupowane Terytorium Palestyny");

wykazPanstw.add("Oman");
wykazPanstw.add("Pakistan");
wykazPanstw.add("Palau");
wykazPanstw.add("Panama");
wykazPanstw.add("Papua Nowa Gwinea");
wykazPanstw.add("Paragwaj");
wykazPanstw.add("Peru");
wykazPanstw.add("Pitcairn");
wykazPanstw.add("Polinezja Francuska");
wykazPanstw.add("Polska");

wykazPanstw.add("Południowa Georgia i Południowe Wyspy Sandwich");
wykazPanstw.add("Portugalia");
wykazPanstw.add("Republika Czeska");
wykazPanstw.add("Republika Korei");
wykazPanstw.add("Rep.Połud.Afryki");
wykazPanstw.add("Rep.Środkowoafryańska");
wykazPanstw.add("Rosja");
wykazPanstw.add("Rwanda");
wykazPanstw.add("Rumunia");
wykazPanstw.add("Salwador");

wykazPanstw.add("Samoa");
wykazPanstw.add("Samoa Amerykańskie");
wykazPanstw.add("San Marino");
wykazPanstw.add("Senegal");
wykazPanstw.add("Serbia");
wykazPanstw.add("Seszele");
wykazPanstw.add("Sierra Leone");
wykazPanstw.add("Singapur");
wykazPanstw.add("Suazi");
wykazPanstw.add("Słowacja");

wykazPanstw.add("Słowenia");
wykazPanstw.add("Somalia");
wykazPanstw.add("Sri Lanka");
wykazPanstw.add("St. Pierre i Miquelon");
wykazPanstw.add("St.Kitts i Nevis");
wykazPanstw.add("St.Lucia");
wykazPanstw.add("St.Vincent i Grenadyny");
wykazPanstw.add("Stany Zjedn. Ameryki Włącznie z Portoryko PR");
wykazPanstw.add("Sudan");
wykazPanstw.add("Surinam");

wykazPanstw.add("Syria");
wykazPanstw.add("Szwajcaria");
wykazPanstw.add("Szwecja");
wykazPanstw.add("Święta Helena");
wykazPanstw.add("Tadżykistan");
wykazPanstw.add("Tajlandia");
wykazPanstw.add("Tajwan");
wykazPanstw.add("Tanzania");
wykazPanstw.add("Togo");
wykazPanstw.add("Tokelau");

wykazPanstw.add("Tonga");
wykazPanstw.add("Trynidad i Tobago");
wykazPanstw.add("Tunezja");
wykazPanstw.add("Turcja");
wykazPanstw.add("Turkmenistan");
wykazPanstw.add("Wyspy Turks i Caicos");
wykazPanstw.add("Tuvalu");
wykazPanstw.add("Uganda");
wykazPanstw.add("Ukraina");
wykazPanstw.add("Urugwaj");

wykazPanstw.add("Uzbekistan");
wykazPanstw.add("Vanuatu");
wykazPanstw.add("Wallis i Futuna");
wykazPanstw.add("Watykan");
wykazPanstw.add("Wenezuela");
wykazPanstw.add("Węgry");
wykazPanstw.add("Wielka Brytania");
wykazPanstw.add("Wietnam");
wykazPanstw.add("Włochy");
wykazPanstw.add("Wschodni Tirom");

wykazPanstw.add("Wyb.Kości Słoniowej");
wykazPanstw.add("Wyspa Bouveta");
wykazPanstw.add("Wyspa Bożego Narodzenia");
wykazPanstw.add("Wyspy Cooka");
wykazPanstw.add("Wyspy Dziewicze-USA");
wykazPanstw.add("Wyspy Dziewicze-W.B");
wykazPanstw.add("Wyspy Heard i McDonald");
wykazPanstw.add("Wyspy Kokosowe (Keelinga)");
wykazPanstw.add("Wyspy Owcze");
wykazPanstw.add("Wyspy Marshalla");

wykazPanstw.add("Wyspy Salomona");
wykazPanstw.add("Wyspy Św.Tomasza i Książęca");
wykazPanstw.add("Zambia");
wykazPanstw.add("Zapasy i zaopatrzenie statków w ramach handlu wewnątrzwspólnotowego");
wykazPanstw.add("Zielony Przylądek");
wykazPanstw.add("Zimbabwe");
wykazPanstw.add("Zjedn.Emiraty Arabskie");

}

 public static List<String> getWykazPanstw() {
     return wykazPanstw;  
 }  
    private String miasto;

    public List<String> complete(String query) {  
        List<String> results = new ArrayList<String>();
        String kl = new String();
        for(String p : getWykazPanstw()) {  
            if(p.toLowerCase().startsWith(query.toLowerCase())) {
                results.add(p);
            }
        }
        return results;
    }

    
}
