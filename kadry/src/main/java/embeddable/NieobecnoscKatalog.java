/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class NieobecnoscKatalog implements Serializable {
     private static final long serialVersionUID = 1L;
     
     private static Map<String, String> lista;
     
    @PostConstruct
    public void init() {
        lista = new HashMap<>();
        lista.put("111", "Urlop bezpłatny");
        lista.put("112", "Urlop bezpłatny, inny pracodawca");
        lista.put("121", "Urlop wychowawczy art. 186 § 2 Kp");
        lista.put("122", "Urlop wychowawczy art. 186 § 3 Kp");
        lista.put("130", "Odszkod. za skrócenie okr. wypowiedzenia");
        lista.put("140", "Przekrocz. roczna podst. wymiaru składek");
        lista.put("151", "Nieobecność uspraw. bez wyn./zas.");
        lista.put("152", "Nieobecność nieusprawiedliwiona");
        lista.put("211", "Zasiłek porodowy");
        lista.put("212", "Zasiłek wyrównawczy z ub. chorobowego");
        lista.put("213", "Zasiłek pogrzebowy");
        lista.put("220", "Skł. wyn. w okr. niezdoln. (nie uw. w podst.)");
        lista.put("312", "Zasiłek opiekuńczy z ub. chor.");
        lista.put("313", "Zasiłek chorobowy z ub. chor.");
        lista.put("332", "Wynagrodzenie za czas niezd. do pracy (FGŚP)");
        lista.put("314", "Zasiłek chorobowy z ub. wyp.");
        lista.put("321", "Świadczenie rehabilit. z ub. chor.");
        lista.put("322", "Świadczenie rehabilit z ub. wyp.");
        lista.put("331", "Wynagrodzenie za czas niezd. do pracy");
        lista.put("333", "Niezd. do pracy spowod. wyp. lub ch.zaw.");
        lista.put("334", "Niezd. do pracy spowod. wyp. lub ch.zaw.");
        lista.put("Y_A", "Rozlicz. zasiłku macierzyńsk. z ZUS (em./rent.)");
        lista.put("Y_C", "Rozlicz. zasiłku wych. z ZUS (zdrow.)");
        lista.put("X_1", "Zasiłek rodzinny");
        lista.put("X_2", "Zasiłek pielęgnacyjny");
        lista.put("X_3", "Zasiłek wychowawczy");
        lista.put("350", "Przerwa w opł.skł. z tytułu zas. macierz.");
        lista.put("311", "Zasiłek macierzyński z ub. chor.");
        lista.put("350", "Inne świadcz. i przerwy");
        lista.put("Y_B", "Rozlicz. urlopu wychow. z ZUS (em./rent.)");
        lista.put("350", "Przerwa w opł.skł. z tytułu urlopu wychow.");
        lista.put("Z_1", "Dni dla Matki z dzieckiem do 14 lat");
        lista.put("155", "Okres niezd. do pr. bez prawa do wynagr.");
        lista.put("214", "Zasiłek wyrównawczy z ub. wypadkowego");
        lista.put("215", "Wyrównanie zas. wyrówn. z ub. chorobow.");
        lista.put("216", "Wyrównanie zas. wyrówn. z ub. wypadkow.");
        lista.put("315", "Wyrówn. zas. macierzyńskiego z ub. chor.");
        lista.put("316", "Wyrówn. zas. opiekuńczego z ub. chorob.");
        lista.put("317", "Wyrówn. zas. chorobowego z ub. chorob.");
        lista.put("318", "Wyrówn. zas. chorobowego z ub. wypadk.");
        lista.put("323", "Wyrównanie św. rehabilit. z ub. chorob.");
        lista.put("324", "Wyrównanie św. rehabilit. z ub. wypadk.");
        lista.put("335", "Wyrówn.wyn. za czas niezd.do pr.(pracod.)");
        lista.put("336", "Wyrówn.wyn. za czas niezd.do pr.(FGŚP)");
        lista.put("337", "Wyr.wyn.za cz.niezd.pr.(do 31-12-02) (prac.)");
        lista.put("338", "Wyr.wyn.za cz.niezd.pr.(do31-12-02)(FGŚP)");
        lista.put("Del", "Delegacja służbowa");
        lista.put("X_A", "Zasiłek z tytułu urodzenia dziecka");
        lista.put("X_B", "Zasiłek z tytułu kszt. i rehab. dziecka niep.");
        lista.put("X_C", "Zasiłek z tytułu rozpoczęcia roku szkolnego");
        lista.put("X_D", "Zasiłek z tytułu nauki poza miejscem zam.");
        lista.put("U_O", "Urlop okolicznościowy");
        lista.put("313", "Zasiłek chorobowy z ub. chor. (szpital)");
        lista.put("230", "Ekwiwalent za pranie odzieży");
        lista.put("311", "Zasiłek macierzyński za okr.url.maci.-wsp.wnios.");
        lista.put("319", "Zas. macierz. za okres urlopu rodzicielskiego");
        lista.put("Y_A", "Rozlicz.zas.mac.z ZUS (em./rent.)(mac.i rodzic.)");
        lista.put("Y_A", "Rozlicz.zas.mac.z ZUS (em./rent.)(rodzicielski)");
        lista.put("319", "Zasiłek macierzyński za okr.url.rodz.-wsp.wnios.");
        lista.put("320", "Wyrówn. zas.macierz. za okr. urlopu rodzicielsk.");
        lista.put("327", "Zasiłek macierz. z ub. chor. za urlop ojcowski");
        lista.put("328", "Wyrówn. zas.macierz.z ub.chor. za urlop ojcowski");
        lista.put("325", "Zasiłek macierzyński za okres dodatk. url. maci.");
        lista.put("326", "Wyrówn. zas macierz. za okres dodatk. url. maci.");
        lista.put("329", "Podwyższenie zasiłku macierzyńskiego");
        lista.put("Y_D", "Rozlicz.zas.mac.z ZUS (em./rent.)(podwyższenie)");
        lista.put("NN", "ieobecność nieusprawiedliwiona");
        lista.put("100", "Urlop wypoczynkowy");
        lista.put("101", "Urlop okolicznościowy");
        lista.put("200", "Redukcja za czas nieprzepracowany");
        lista.put("104", "Urlop ojcowski");
        lista.put("105", "Urlop rodzicielski");
        lista.put("106", "Urlop na warunkach url. macierz.");
        lista.put("107", "Urlop macierzyński");
        lista.put("201", "Służba wojskowa lub zastępcza");
        lista.put("202", "Okres nieobecności w pracy");
        lista.put("203", "Wypadek przy pracy lub choroba zawodowa");
        lista.put("204", "Wypadek");
        lista.put("205", "Skrócenie okresu wypowiedzenia");
        lista.put("206", "Świadczenie z obecnością w pracy");
        lista.put("207", "Nieobecność nieusprawiedliwiona niepłatna");
        lista.put("777", "Praca za granicą");
    }

    public static Map<String, String> getLista() {
        return lista;
    }
    

}
