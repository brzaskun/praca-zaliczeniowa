/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import static beansPdf.PdfFont.ustawfrazeAlign;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entityfk.SprFinKwotyInfDod;
import view.WpisView;
import java.io.File;
import pdffk.PdfMain;
import static pdffk.PdfMain.*;
import com.itextpdf.text.pdf.PdfPTable;
import embeddablefk.SaldoKonto;
import entity.Podatnik;
import entityfk.Konto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import plik.Plik;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class SprFinInfDodBeanTXT {

    static void naglowekglowny(Document document, String rok) {
        PdfMain.dodajLinieOpisuBezOdstepu(document, "INFORMACJA DODATKOWA", Element.ALIGN_CENTER);
        PdfMain.dodajLinieOpisuBezOdstepu(document, "Za rok podatkowy "+rok, Element.ALIGN_CENTER);
        PdfMain.dodajLinieOpisu(document, "WG ZAŁĄCZNIKA NR 1 DO USTAWY O RACHUNKOWOŚCI", Element.ALIGN_CENTER);
    }

    static void podnaglowek1(Document document) {
        PdfMain.dodajLinieOpisu(document, "I. WPROWADZENIE DO SPRAWOZDANIA FINANSOWEGO.", Element.ALIGN_LEFT);
        PdfMain.dodajLinieOpisu(document, "1. PODSTAWOWE DANE SPÓŁKI:", Element.ALIGN_LEFT);
    }

    
    static void podnaglowek2(Document document) {
        PdfMain.dodajLinieOpisu(document, "2. PRZYJĘTE ZASADY RACHUNKOWOŚCI:", Element.ALIGN_LEFT);
    }
    
    static void podnaglowek3(Document document) {
        PdfMain.dodajLinieOpisu(document, "II. DODATKOWA INFORMACJA I OBJAŚNIENIA..", Element.ALIGN_LEFT);
        PdfMain.dodajLinieOpisu(document, "CZĘŚĆ 1. WYJAŚNIENIA DO BILANSU.:", Element.ALIGN_LEFT);
        PdfMain.dodajLinieOpisuBezOdstepu(document, "Tabela zmian wartości grup składników majątku trwałego:", Element.ALIGN_LEFT);
    }
    
    static void podnaglowek4(Document document) {
        PdfMain.dodajLinieOpisuBezOdstepu(document, "Zobowiązania wobec budżetu państwa", Element.ALIGN_LEFT);
    }
    
    static void podnaglowek5(Document document) {
        PdfMain.dodajLinieOpisuBezOdstepu(document, "Stan na początek roku obrotowego, zwiększenia i wykorzystanie oraz stan końcowy kapitałów zapasowych i rezerwowych", Element.ALIGN_LEFT);
    }
    
    static void podnaglowek6(Document document) {
        PdfMain.dodajLinieOpisuBezOdstepu(document, "Propozycje co do sposobu podziału zysku lub pokrycia straty za rok obrotowy", Element.ALIGN_LEFT);
    }
    static void podnaglowek7(Document document) {
        PdfMain.dodajLinieOpisuBezOdstepu(document, "Wykaz istotnych pozycji czynnych i biernych rozliczeń międzyokresowych.", Element.ALIGN_LEFT);
    }
    
    static void podnaglowek8(Document document) {
        PdfMain.dodajLinieOpisu(document, "CZĘŚĆ 2. WYJAŚNIENIA DO RACHUNKU ZYSKÓW I STRAT.", Element.ALIGN_LEFT);
        PdfMain.dodajLinieOpisu(document, "1. Struktura rzeczowa (rodzaje działalności) przychodów netto ze sprzedaży produktów, towarów i materiałów.", Element.ALIGN_LEFT);
        PdfMain.dodajLinieOpisuBezOdstepu(document, "Przychody ze sprzedaży", Element.ALIGN_LEFT);
    }
     
    static void podnaglowek9(Document document) {
        PdfMain.dodajLinieOpisuBezOdstepu(document, "Struktura ważniejszych kosztów operacyjnych", Element.ALIGN_LEFT);
    }
    
    static void podnaglowek10(Document document) {
        PdfMain.dodajLinieOpisuBezOdstepu(document, "Rozliczenie głównych pozycji różniącą podstawę opodatkowania podatkiem dochodowym od wyniku finansowego (zysku, straty) brutto.", Element.ALIGN_LEFT);
    }
    
    static void podnaglowek11(Document document) {
        PdfMain.dodajLinieOpisu(document, "CZĘŚĆ 3. OBJAŚNIENIA NIEKTÓRYCH ZAGADNIEŃ OSOBOWYCH.", Element.ALIGN_LEFT);
        PdfMain.dodajLinieOpisuBezOdstepu(document, "Informacja o przeciętnym zatrudnieniu w roku obrotowym, z podziałem na grupy zawodowe.", Element.ALIGN_LEFT);
    }
    

    static void zasadyrachunkowosci(Document document) {
        PdfMain.dodajLinieOpisuBezOdstepu(document,"Sprawozdanie finansowe zostało przygotowane zgodnie z wymogami ustawy z dnia 29 września 1994 roku o rachunkowości obowiązującymi jednostki.", Element.ALIGN_JUSTIFIED);
        PdfMain.dodajLinieOpisuBezOdstepu(document,"Jednostka sporządza rachunek zysków i strat w układzie jednostronnym ", Element.ALIGN_JUSTIFIED);
        PdfMain.dodajLinieOpisuBezOdstepu(document,"W sprawozdaniu finansowym Jednostka wykazuje zdarzenia gospodarcze zgodnie z ich treścią ekonomiczną.", Element.ALIGN_JUSTIFIED);
        PdfMain.dodajLinieOpisuBezOdstepu(document,"Wynik finansowy jednostki za dany rok obrotowy obejmuje wszystkie osiągnięte i przypadające na jej rzecz przychody oraz związane z tymi przychodami koszty zgodnie z zasadami memoriału, współmierności przychodów i kosztów oraz ostrożnej wyceny.", Element.ALIGN_JUSTIFIED);
        PdfMain.dodajLinieOpisuBezOdstepu(document,"Wartości niematerialne i prawne wycenia się według cen nabycia lub kosztów wytworzenia dla kosztów prac rozwojowych, pomniejszonych o skumulowane odpisy umorzeniowe oraz o odpisy z tytułu trwałej utraty wartości.", Element.ALIGN_JUSTIFIED);
        PdfMain.dodajLinieOpisuBezOdstepu(document,"W 2018 roku wśród wartości niematerialnych i prawnych jednostka nie posiadała.", Element.ALIGN_JUSTIFIED);
        PdfMain.dodajLinieOpisuBezOdstepu(document,"Środki trwałe są wycenianie w cenie nabycia lub koszcie wytworzenia po aktualizacji wyceny składników majątku pomniejszonych o skumulowane umorzenie oraz dokonane odpisy aktualizujące ich wartość.", Element.ALIGN_JUSTIFIED);
        PdfMain.dodajLinieOpisuBezOdstepu(document,"Dla celów podatkowych przyjmowane były stawki amortyzacyjne wynikające z ustawy z dnia 15 lutego 1992 roku o podatku dochodowym od osób prawnych określającej wysokość amortyzacji stanowiącej koszty uzyskania przychodów. Określa ono wysokość amortyzacji stanowiącej koszty uzyskania przychodu.", Element.ALIGN_JUSTIFIED);
        PdfMain.dodajLinieOpisuBezOdstepu(document,"Składniki majątku o przewidywanym okresie użytkowania nieprzekraczającym jednego roku oraz wartości początkowej nieprzekraczającej 3,5 tysiąca złotych są jednorazowo odpisywane w ciężar kosztów w momencie przekazania do użytkowania. ", Element.ALIGN_JUSTIFIED);
        PdfMain.dodajLinieOpisuBezOdstepu(document,"Środki trwałe umarzane są według metody liniowej począwszy od miesiąca następnego po miesiącu przyjęcia do eksploatacji w okresie odpowiadającym szacowanemu okresowi ich ekonomicznej użyteczności.", Element.ALIGN_JUSTIFIED);
        PdfMain.dodajLinieOpisuBezOdstepu(document,"Środki trwałe w budowie wycenia się w wysokości ogółu kosztów pozostających w bezpośrednim związku z ich nabyciem lub wytworzeniem, pomniejszonych o odpisy z tytułu trwałej utraty wartości.", Element.ALIGN_JUSTIFIED);
        PdfMain.dodajLinieOpisuBezOdstepu(document,"Należności wycenia się w kwotach wymaganej zapłaty, z zachowaniem zasady ostrożnej wyceny (po pomniejszeniu o odpisy aktualizujące).", Element.ALIGN_JUSTIFIED);
        PdfMain.dodajLinieOpisuBezOdstepu(document,"Środki pieniężne w walucie polskiej wykazuje się w wartości nominalnej.", Element.ALIGN_JUSTIFIED);
        PdfMain.dodajLinieOpisuBezOdstepu(document,"Kapitały (fundusze) własne ujmuje się w księgach rachunkowych w wartości nominalnej według ich rodzajów i zasad określonych przepisami prawa, statutu lub umowy spółki.", Element.ALIGN_JUSTIFIED);
        PdfMain.dodajLinieOpisuBezOdstepu(document,"Kapitał zakładowy wykazuje się w wysokości określonej w umowie lub statucie i wpisanej w rejestrze sądowym.", Element.ALIGN_JUSTIFIED);
        PdfMain.dodajLinieOpisu(document,"Zobowiązania, szczególnie wobec budżetu, zostały wycenione na dzień bilansowy w kwocie wymagającej zapłaty.", Element.ALIGN_JUSTIFIED);
    }
    
    static List<String> wierszeTab1(SprFinKwotyInfDod sprFinKwotyInfDod) {
        List<String> wiersze =  new ArrayList();
        Podatnik p = sprFinKwotyInfDod.getPodatnik();
        wiersze.add("Nazwa i forma spółki");
        wiersze.add(p.getNazwaRejestr());
        wiersze.add("Numer KRS");
        wiersze.add(p.getImie());
        wiersze.add("Podstawowy przedmiot działalności");
        wiersze.add(sprFinKwotyInfDod.getPpdzialalnosci());
        wiersze.add("Pozostałe przedmioty działalności");
        wiersze.add(sprFinKwotyInfDod.getPozpdzialalnosci());
        wiersze.add("Sąd (lub inny organ) prowadzący rejestr");
        wiersze.add(sprFinKwotyInfDod.getSad());
        wiersze.add("Czas trwania spółki:");
        wiersze.add("nieokreślony");
        wiersze.add("Okres obejmujący sprawozdanie:");
        wiersze.add("Roczne sprawozdanie finansowe za okres "+sprFinKwotyInfDod.getDataod()+" do "+sprFinKwotyInfDod.getDatado());
        wiersze.add("Dane łączne");
        wiersze.add("nie dotyczy");
        wiersze.add("Kontunuacja działalności");
        wiersze.add("Roczne sprawozdanie sporządzono przy założeniu kontynuowania działalności przez spółkę przez co najmniej 12 kolejnych miesięcy i dłużej. Nie są nam znane okoliczności, które wskazywałyby na istnienie poważnych zagrożeń dla kontynuowania przez spółkę działalności.");
        wiersze.add("Metody rozliczania po połączeniu spółek");
        wiersze.add("nie dotyczy");
        return wiersze;
    }
    
    static List<String> wierszeTab2(List<SaldoKonto> listaSaldoKonto) {
        List<String> wiersze =  new ArrayList();
        wiersze.add("Nazwa grupy składników majątku trwałego ");
        wiersze.add("Stan na początek roku obrotowego");
        wiersze.add("Zwiększenia");
        wiersze.add("Zmniejszenia");
        wiersze.add("Stan na koniec roku obrotoweg");
        wiersze.add("Grunty i prawa użytkowania wieczystego gruntów");
        wiersze.add(pobierz("010-1", listaSaldoKonto, 0, 1));
        wiersze.add(pobierzobroty("010-1", listaSaldoKonto, 0));
        wiersze.add(pobierzobroty("010-1", listaSaldoKonto, 1));
        wiersze.add(pobierz("010-1", listaSaldoKonto, 0, 0));
        wiersze.add("Budynki, lokale i obiekty inżynierii lądowej i wodnej");
        wiersze.add(pobierz("010-2", listaSaldoKonto, 0, 1));
        wiersze.add(pobierzobroty("010-2", listaSaldoKonto, 0));
        wiersze.add(pobierzobroty("010-2", listaSaldoKonto, 1));
        wiersze.add(pobierz("010-2", listaSaldoKonto, 0, 0));
        wiersze.add("Urządzenia techniczne i maszyny");
        wiersze.add(pobierz("010-3", listaSaldoKonto, 0, 1));
        wiersze.add(pobierzobroty("010-3", listaSaldoKonto, 0));
        wiersze.add(pobierzobroty("010-3", listaSaldoKonto, 1));
        wiersze.add(pobierz("010-3", listaSaldoKonto, 0, 0));
        wiersze.add("Środki transportu");
        wiersze.add(pobierz("010-4", listaSaldoKonto, 0, 1));
        wiersze.add(pobierzobroty("010-4", listaSaldoKonto, 0));
        wiersze.add(pobierzobroty("010-4", listaSaldoKonto, 1));
        wiersze.add(pobierz("010-4", listaSaldoKonto, 0, 0));
        wiersze.add("Inne środki trwałe");
        wiersze.add(pobierz("010-5", listaSaldoKonto, 0, 1));
        wiersze.add(pobierzobroty("010-5", listaSaldoKonto, 0));
        wiersze.add(pobierzobroty("010-5", listaSaldoKonto, 1));
        wiersze.add(pobierz("010-5", listaSaldoKonto, 0, 0));
        return wiersze;
    }
    
     static List<String> wierszeTab3(List<SaldoKonto> listaSaldoKonto) {
        List<String> wiersze =  new ArrayList();
        wiersze.add("Wartość środków w cenach zak.");
        wiersze.add(pobierz("010", listaSaldoKonto, 0, 0));
        wiersze.add("Amortyzacja za rok");
        wiersze.add(pobierz("401", listaSaldoKonto, 0, 0));
        wiersze.add("Umorzenie narastająco");
        wiersze.add(pobierz("070", listaSaldoKonto, 1, 0));
        wiersze.add("Wartośc netto pocz. roku");
        wiersze.add(pobierzroznica("010", "070", listaSaldoKonto, 1));
        wiersze.add("Wartośc netto koniec roku");
        wiersze.add(pobierzroznica("010", "070", listaSaldoKonto, 0));
        return wiersze;
    }

    static List<String> wierszeTab4(List<SaldoKonto> listaSaldoKonto) {
        List<String> wiersze =  new ArrayList();
        wiersze.add("Rodzaj zob.");
        wiersze.add("Rok bież.");
        wiersze.add("Rok poprz.");
        wiersze.add("Różnica");
        wiersze.add("ZUS");
        wiersze.add(pobierz("231", listaSaldoKonto, 1, 0));
        wiersze.add(pobierz("231", listaSaldoKonto, 1, 1));
        wiersze.add(pobierzprzyrost("231", listaSaldoKonto, 1));
        wiersze.add("PIT");
        wiersze.add(pobierz("220-2", listaSaldoKonto, 1, 0));
        wiersze.add(pobierz("220-2", listaSaldoKonto, 1, 1));
        wiersze.add(pobierzprzyrost("220-3", listaSaldoKonto, 1));
        wiersze.add("CIT");
        wiersze.add(pobierz("220-1", listaSaldoKonto, 1, 0));
        wiersze.add(pobierz("220-1", listaSaldoKonto, 1, 1));
        wiersze.add(pobierzprzyrost("220-1", listaSaldoKonto, 1));
        wiersze.add("VAT");
        wiersze.add(pobierz("222", listaSaldoKonto, 1, 0));
        wiersze.add(pobierz("222", listaSaldoKonto, 1, 1));
        wiersze.add(pobierzprzyrost("222", listaSaldoKonto, 1));
        return wiersze;
    }
    
    static List<String> wierszeTab5(List<SaldoKonto> listaSaldoKonto) {
        List<String> wiersze =  new ArrayList();
        wiersze.add("Rodzaj");
        wiersze.add("Rok bież.");
        wiersze.add("Rok poprz.");
        wiersze.add("Różnica");
        wiersze.add("Kap. zapasowy");
        wiersze.add(pobierz("805", listaSaldoKonto, 1, 0));
        wiersze.add(pobierz("805", listaSaldoKonto, 1, 1));
        wiersze.add(pobierzprzyrost("805", listaSaldoKonto, 1));
        wiersze.add("Kap. rezerwowy");
        wiersze.add(pobierz("806", listaSaldoKonto, 1, 0));
        wiersze.add(pobierz("806", listaSaldoKonto, 1, 1));
        wiersze.add(pobierzprzyrost("806", listaSaldoKonto, 1));
        return wiersze;
    }
    
    static List<String> wierszeTab6(List<SaldoKonto> listaSaldoKonto, SprFinKwotyInfDod sprFinKwotyInfDod) {
        List<String> wiersze =  new ArrayList();
        wiersze.add("Wyszczególnienie");
        wiersze.add("Kwota");
        wiersze.add("1. Wynik finansowy netto");
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getDopodzialu()));
        wiersze.add("- w tym rozliczenie wyniku finansowego z lat ub");
        wiersze.add(pobierz("821", listaSaldoKonto, 0, 1));
        double wynik = Z.z(sprFinKwotyInfDod.getPid1A().doubleValue()+sprFinKwotyInfDod.getPid11A().doubleValue()+pobierzNum("821", listaSaldoKonto, 0, 1));
        if (wynik>0.0) {
            wiersze.add("2. Proponowany sposób podziału dochodu");
            wiersze.add("");
            wiersze.add("Utworzenie kapitału zapasowego");
            wiersze.add(format.F.curr(sprFinKwotyInfDod.getKapitalrezerwowy()));
            wiersze.add("Pokrycie straty z lat ubiegłych");
            wiersze.add(format.F.curr(sprFinKwotyInfDod.getStratazlatubieglych()));
            wiersze.add("Wypłata wspólnikom");
            wiersze.add(format.F.curr(sprFinKwotyInfDod.getDopodzialu()));
            wiersze.add("3 Wynik finansowy niepodzielony");
            wiersze.add(format.F.curr(Z.z(sprFinKwotyInfDod.getWynikniepodzielony())));
        } else if (wynik<0.0) {
            wiersze.add("2. Proponowany sposób pokrycia straty");
            wiersze.add("Z zysków lat następnych");
        }
        return wiersze;
    }
    
    static List<String> wierszeTab7(List<SaldoKonto> listaSaldoKonto) {
        List<String> wiersze =  new ArrayList();
        wiersze.add("Tytuł");
        wiersze.add("Rok bież.");
        wiersze.add("Rok poprz.");
        wiersze.add("Różnica");
        wiersze.add("Czynne rozliczenia m.o. kosztów");
        wiersze.add(pobierz("641", listaSaldoKonto, 0, 0));
        wiersze.add(pobierz("641", listaSaldoKonto, 0, 1));
        wiersze.add(pobierzprzyrost("641", listaSaldoKonto, 0));
        wiersze.add("Czynne rozliczenia m.o. przychodów");
        wiersze.add(pobierz("646", listaSaldoKonto, 0, 0));
        wiersze.add(pobierz("646", listaSaldoKonto, 0, 1));
        wiersze.add(pobierzprzyrost("646", listaSaldoKonto, 0));
        wiersze.add("Bierne rozliczenia m.o. przychodów");
        wiersze.add(pobierz("844", listaSaldoKonto, 1, 0));
        wiersze.add(pobierz("844", listaSaldoKonto, 1, 1));
        wiersze.add(pobierzprzyrost("844", listaSaldoKonto, 1));
        return wiersze;
    }
    
    static List<String> wierszeTab8(List<SaldoKonto> listaSaldoKonto) {
        List<String> wiersze =  new ArrayList();
        wiersze.add("Przychód netto ze sprzedaży");
        wiersze.add("Sprzed. ogółem Rok bież.");
        wiersze.add("Sprzed. ogółem Rok poprz.");
        wiersze.add("Jedn pow. Rok bież.");
        wiersze.add("Jedn pow. Rok poprzedni");
        wiersze.add("Wyroby");
        wiersze.add(pobierz("701-2", listaSaldoKonto, 1, 0));
        wiersze.add(pobierz("701-2", listaSaldoKonto, 1, 1));
        wiersze.add(pobierz("701-1", listaSaldoKonto, 1, 0));
        wiersze.add(pobierz("701-1", listaSaldoKonto, 1, 1));
        wiersze.add("Usługi");
        wiersze.add(pobierz("702-2", listaSaldoKonto, 1, 0));
        wiersze.add(pobierz("702-2", listaSaldoKonto, 1, 1));
        wiersze.add(pobierz("702-1", listaSaldoKonto, 1, 0));
        wiersze.add(pobierz("702-1", listaSaldoKonto, 1, 1));
        wiersze.add("Towary");
        wiersze.add(pobierz("731-2", listaSaldoKonto, 1, 0));
        wiersze.add(pobierz("731-2", listaSaldoKonto, 1, 1));
        wiersze.add(pobierz("731-1", listaSaldoKonto, 1, 0));
        wiersze.add(pobierz("731-1", listaSaldoKonto, 1, 1));
        wiersze.add("Materiały");
        wiersze.add(pobierz("732-2", listaSaldoKonto, 1, 0));
        wiersze.add(pobierz("732-2", listaSaldoKonto, 1, 1));
        wiersze.add(pobierz("732-1", listaSaldoKonto, 1, 0));
        wiersze.add(pobierz("732-1", listaSaldoKonto, 1, 1));
        return wiersze;
    }
    
    static List<String> wierszeTab9(List<SaldoKonto> listaSaldoKonto) {
        List<String> wiersze =  new ArrayList();
        wiersze.add("Pozycja");
        wiersze.add("Rok bież.");
        wiersze.add("Rok poprzedni");
        wiersze.add("Różnica");
        wiersze.add("Amortyzacja");
        wiersze.add(pobierz("401", listaSaldoKonto, 0, 0));
        wiersze.add(pobierz("401", listaSaldoKonto, 0, 1));
        wiersze.add(pobierzprzyrost("401", listaSaldoKonto, 0));
        wiersze.add("Zużycie materiałów");
        wiersze.add(pobierz("402", listaSaldoKonto, 0, 0));
        wiersze.add(pobierz("402", listaSaldoKonto, 0, 1));
        wiersze.add(pobierzprzyrost("402", listaSaldoKonto, 0));
        wiersze.add("Usługi obce");
        wiersze.add(pobierz("403", listaSaldoKonto, 0, 0));
        wiersze.add(pobierz("403", listaSaldoKonto, 0, 1));
        wiersze.add(pobierzprzyrost("403", listaSaldoKonto, 0));
        wiersze.add("Wynagrodzenia");
        wiersze.add(pobierz("405", listaSaldoKonto, 0, 0));
        wiersze.add(pobierz("405", listaSaldoKonto, 0, 1));
        wiersze.add(pobierzprzyrost("405", listaSaldoKonto, 0));
        wiersze.add("Ubezpieczenia społeczne");
        wiersze.add(pobierz("406", listaSaldoKonto, 0, 0));
        wiersze.add(pobierz("406", listaSaldoKonto, 0, 1));
        wiersze.add(pobierzprzyrost("406", listaSaldoKonto, 0));
        return wiersze;
    }
    
    static List<String> wierszeTab10(SprFinKwotyInfDod sprFinKwotyInfDod) {
        List<String> wiersze =  new ArrayList();
        wiersze.add("Nazwa");
        wiersze.add("Rok bież.");
        wiersze.add("Rok poprzedni");
        wiersze.add("Różnica");
        wiersze.add("A. Zysk (strata) brutto za dany rok");
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid1A().doubleValue()));
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid1B().doubleValue()));
        wiersze.add(format.F.curr(Z.z(sprFinKwotyInfDod.getPid1A().doubleValue()-sprFinKwotyInfDod.getPid1B().doubleValue())));
        wiersze.add("B. Przychody zwolnione z opodatkowania (trwałe różnice pomiędzy zyskiem/stratą dla celów rachunkowych a dochodem/stratą dla celów podatkowych");
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid2A().doubleValue()));
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid2B().doubleValue()));
        wiersze.add(format.F.curr(Z.z(sprFinKwotyInfDod.getPid2A().doubleValue()-sprFinKwotyInfDod.getPid2B().doubleValue())));
        wiersze.add("C. Przychody niepodlegające opodatkowania w roku bieżącym");
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid3A().doubleValue()));
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid3B().doubleValue()));
        wiersze.add(format.F.curr(Z.z(sprFinKwotyInfDod.getPid3A().doubleValue()-sprFinKwotyInfDod.getPid3B().doubleValue())));
        wiersze.add("D. Przychody podlegające opodatkowania w roku bieżącym, ujęte w księgach rachunkowych lat ubiegłych");
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid4A().doubleValue()));
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid4B().doubleValue()));
        wiersze.add(format.F.curr(Z.z(sprFinKwotyInfDod.getPid4A().doubleValue()-sprFinKwotyInfDod.getPid4B().doubleValue())));
        wiersze.add("E. Koszty niestanowiące kosztów uzyskania przychodów (trwałe różnice pomiędzy zyskiem/stratą dla celów rachunkowych a dochodem/stratą dla celów podatkowych)	");
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid5A().doubleValue()));
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid5B().doubleValue()));
        wiersze.add(format.F.curr(Z.z(sprFinKwotyInfDod.getPid5A().doubleValue()-sprFinKwotyInfDod.getPid5B().doubleValue())));
        wiersze.add("F. Koszty nieuznawane za koszty uzyskania przychodów w bieżącym roku");
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid6A().doubleValue()));
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid6B().doubleValue()));
        wiersze.add(format.F.curr(Z.z(sprFinKwotyInfDod.getPid6A().doubleValue()-sprFinKwotyInfDod.getPid6B().doubleValue())));
        wiersze.add("G. Koszty uznawane za koszty uzyskania przychodów w roku bieżącym ujęte w księgach lat ubiegłych");
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid7A().doubleValue()));
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid7B().doubleValue()));
        wiersze.add(format.F.curr(Z.z(sprFinKwotyInfDod.getPid7A().doubleValue()-sprFinKwotyInfDod.getPid7B().doubleValue())));
        wiersze.add("H. Strata z lat ubiegłych");
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid8A().doubleValue()));
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid8B().doubleValue()));
        wiersze.add(format.F.curr(Z.z(sprFinKwotyInfDod.getPid8A().doubleValue()-sprFinKwotyInfDod.getPid8B().doubleValue())));
        wiersze.add("I. Inne zmiany podstawy opodatkowania");
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid9A().doubleValue()));
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid9B().doubleValue()));
        wiersze.add(format.F.curr(Z.z(sprFinKwotyInfDod.getPid9A().doubleValue()-sprFinKwotyInfDod.getPid9B().doubleValue())));
        wiersze.add("J. Podstawa opodatkowania podatkiem dochodowy");
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid10A().doubleValue()));
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid10B().doubleValue()));
        wiersze.add(format.F.curr(Z.z(sprFinKwotyInfDod.getPid10A().doubleValue()-sprFinKwotyInfDod.getPid10B().doubleValue())));
        wiersze.add("K. Podatek dochodowy");
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid11A().doubleValue()));
        wiersze.add(format.F.curr(sprFinKwotyInfDod.getPid11B().doubleValue()));
        wiersze.add(format.F.curr(Z.z(sprFinKwotyInfDod.getPid11A().doubleValue()-sprFinKwotyInfDod.getPid11B().doubleValue())));
        return wiersze;
    }
    
    static List<String> wierszeTab11(SprFinKwotyInfDod sprFinKwotyInfDod) {
        List<String> wiersze =  new ArrayList();
        wiersze.add("Pracownicy na umowie o pracę");
        wiersze.add(format.F.number(sprFinKwotyInfDod.getPracownicy()));
        wiersze.add("Zleceniobiorcy");
        wiersze.add(format.F.number(sprFinKwotyInfDod.getZleceniobiorcy()));
        wiersze.add("Pracownicy przebywający na urlopach macierzyńskich, wychowawczych lub bezpłatnych");
        wiersze.add(format.F.number(sprFinKwotyInfDod.getInni()));
        wiersze.add("Ogółem");
        wiersze.add(format.F.number(sprFinKwotyInfDod.getPracownicy()+sprFinKwotyInfDod.getZleceniobiorcy()+sprFinKwotyInfDod.getInni()));
        return wiersze;
    }

    private static String pobierz(String szukane, List<SaldoKonto> listaSaldoKonto, int modyfikatorstrona, int modyfikatorrok) {
        SaldoKonto saldoKonto = null;
        for (SaldoKonto p : listaSaldoKonto) {
            if (p.getKonto().getPelnynumer().equals(szukane)) {
                saldoKonto = p;
                break;
            }
        }
        double kwota = 0.0;
        if (saldoKonto!=null) {
            if (modyfikatorrok==1) {
                if (modyfikatorstrona==0) {
                    kwota = saldoKonto.getBoWn()-saldoKonto.getBoMa();
                } else {
                    kwota = saldoKonto.getBoMa()-saldoKonto.getBoWn();
                }
            } else {
                if (modyfikatorstrona==0) {
                    kwota = saldoKonto.getSaldoWn()-saldoKonto.getSaldoMa();
                } else {
                    kwota = saldoKonto.getSaldoMa()-saldoKonto.getSaldoWn();
                }
            }
        }
        return format.F.curr(Z.z(kwota));
    }
    
    private static double pobierzNum(String szukane, List<SaldoKonto> listaSaldoKonto, int modyfikatorstrona, int modyfikatorrok) {
        SaldoKonto saldoKonto = null;
        for (SaldoKonto p : listaSaldoKonto) {
            if (p.getKonto().getPelnynumer().equals(szukane)) {
                saldoKonto = p;
                break;
            }
        }
        double kwota = 0.0;
        if (saldoKonto!=null) {
            if (modyfikatorrok==1) {
                if (modyfikatorstrona==0) {
                    kwota = saldoKonto.getBoWn()-saldoKonto.getBoMa();
                } else {
                    kwota = saldoKonto.getBoMa()-saldoKonto.getBoWn();
                }
            } else {
                if (modyfikatorstrona==0) {
                    kwota = saldoKonto.getSaldoWn()-saldoKonto.getSaldoMa();
                } else {
                    kwota = saldoKonto.getSaldoMa()-saldoKonto.getSaldoWn();
                }
            }
        }
        return Z.z(kwota);
    }
    
    private static String pobierzobroty(String szukane, List<SaldoKonto> listaSaldoKonto, int modyfikatorstrona) {
        SaldoKonto saldoKonto = null;
        for (SaldoKonto p : listaSaldoKonto) {
            if (p.getKonto().getPelnynumer().equals(szukane)) {
                saldoKonto = p;
                break;
            }
        }
        double kwota = 0.0;
        if (saldoKonto!=null) {
            if (modyfikatorstrona==0) {
                if (saldoKonto.getObrotyWn()==0.0) {
                    kwota = 0.0;
                } else if (saldoKonto.getObrotyWn()!=0.0) {
                    kwota = saldoKonto.getObrotyWn();
                } else {
                    kwota = -saldoKonto.getObrotyMa();
                }
            } else {
                if (saldoKonto.getObrotyMa()==0.0) {
                    kwota = 0.0;
                } else if (saldoKonto.getObrotyMa()!=0.0) {
                    kwota = saldoKonto.getObrotyMa();
                } else {
                    kwota = -saldoKonto.getObrotyWn();
                }
            }
        }
        return format.F.curr(Z.z(kwota));
    }

    private static String pobierzroznica(String szukane, String szukane1, List<SaldoKonto> listaSaldoKonto,  int modyfikatorrok) {
        SaldoKonto saldoKonto = null;
        SaldoKonto saldoKonto1 = null;
        for (SaldoKonto p : listaSaldoKonto) {
            if (p.getKonto().getPelnynumer().equals(szukane)) {
                saldoKonto = p;
            }
            if (p.getKonto().getPelnynumer().equals(szukane1)) {
                saldoKonto1 = p;
                if (saldoKonto!=null) {
                    break;
                }
            }
        }
        double kwota1 = 0.0;
        if (saldoKonto!=null) {
            if (modyfikatorrok==1) {
                    kwota1 = Z.z(saldoKonto.getBoWn()-saldoKonto.getBoMa());
            } else {
                    kwota1 = Z.z(saldoKonto.getSaldoWn()-saldoKonto.getSaldoMa());
            }
        }
        double kwota2 = 0.0;
        if (saldoKonto1!=null) {
            if (modyfikatorrok==1) {
                    kwota2 = Z.z(saldoKonto1.getBoMa()-saldoKonto1.getBoWn());
            } else {
                    kwota2 = Z.z(saldoKonto1.getSaldoMa()-saldoKonto1.getSaldoWn());
            }
        }
        double roznica = Z.z(kwota1-kwota2);
        return format.F.curr(roznica);
    }

    private static String pobierzprzyrost(String szukane, List<SaldoKonto> listaSaldoKonto, int modyfikatorstrona) {
        SaldoKonto saldoKonto = null;
        for (SaldoKonto p : listaSaldoKonto) {
            if (p.getKonto().getPelnynumer().equals(szukane)) {
                saldoKonto = p;
                break;
            }
        }
        double kwota1 = 0.0;
        if (saldoKonto!=null) {
            if (modyfikatorstrona==0) {
                    kwota1 = Z.z(saldoKonto.getBoWn()-saldoKonto.getBoMa());
            } else {
                    kwota1 = Z.z(saldoKonto.getBoMa()-saldoKonto.getBoWn());
            }
        }
        double kwota2 = 0.0;
        if (saldoKonto!=null) {
            if (modyfikatorstrona==0) {
                    kwota2 = Z.z(saldoKonto.getSaldoWn()-saldoKonto.getSaldoMa());
            } else {
                    kwota2 = Z.z(saldoKonto.getSaldoMa()-saldoKonto.getSaldoWn());
            }
        }
        double roznica = Z.z(kwota2-kwota1);
        return format.F.curr(roznica);
    }
}
