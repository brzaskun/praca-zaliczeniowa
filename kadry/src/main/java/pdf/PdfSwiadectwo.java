/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfWriter;
import data.Data;
import entity.Angaz;
import entity.EkwiwalentUrlop;
import entity.EtatPrac;
import entity.FirmaKadry;
import entity.Naliczeniepotracenie;
import entity.Nieobecnosc;
import entity.Nieobecnoscprezentacja;
import entity.Pracownik;
import entity.Rozwiazanieumowy;
import entity.Skladnikpotracenia;
import entity.Stanowiskoprac;
import entity.Swiadectwo;
import entity.Swiadectwodni;
import entity.Umowa;
import error.E;
import f.F;
import java.util.ArrayList;
import java.util.List;
import msg.Msg;
import org.primefaces.PrimeFaces;
import static pdf.PdfMain.finalizacjaDokumentuQR;
import static pdf.PdfMain.inicjacjaWritera;
import static pdf.PdfMain.naglowekStopkaP;
import static pdf.PdfMain.otwarcieDokumentu;
import z.Z;

/**
 *
 * @author Osito
 */
public class PdfSwiadectwo {
     public static void drukuj(Swiadectwo swiadectwo, List<Swiadectwodni> dnidoswiadectwa, EkwiwalentUrlop ekwiwalentUrlop, Angaz angaz, Nieobecnoscprezentacja urlopprezentacja, String datanawiazaniastosunkupracy) {
        try {
            Rozwiazanieumowy rozwiazanieumowy = swiadectwo.getRozwiazanieumowy();
            Umowa umowa = swiadectwo.getRozwiazanieumowy().getUmowa();
            Pracownik pracownik = umowa.getPracownik();
            FirmaKadry firma = angaz.getFirma();
            String pesel = pracownik.getPesel()!=null?pracownik.getPesel():pracownik.getPaszport();
            String nazwa = pesel+"swiadectwo.pdf";
            boolean mezczyzna = pracownik.isMezczyzna();
            if (nazwa != null) {
                Document document = PdfMain.inicjacjaA4PortraitLean();
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                String gdzie = firma.getMiasto()+", "+swiadectwo.getDatawystawienia();
                PdfMain.dodajLinieOpisu(document, gdzie, Element.ALIGN_RIGHT, 2);
                String pracodawca1 = firma.getNazwa();
                //String pracodawca2 = "Regon "+firma.getRegon()+", PKD brak";
                String pracodawca2 = "NIP "+firma.getNip();
                String pracodawca3 = firma.getAdres();
                PdfMain.dodajLinieOpisuBezOdstepu(document, pracodawca1, Element.ALIGN_LEFT, 2);
                PdfMain.dodajLinieOpisuBezOdstepu(document, pracodawca2, Element.ALIGN_LEFT, 2);
                PdfMain.dodajLinieOpisu(document, pracodawca3, Element.ALIGN_LEFT, 2);
                PdfMain.dodajLinieOpisu(document, "ŚWIADECTWO PRACY", Element.ALIGN_CENTER, 3);
                String text1 = mezczyzna?"urodzony":"urodzona";
                String pracownik1 = "1. Stwierdza się, że "+pracownik.getNazwiskoImie()+" "+text1+" "+pracownik.getDataurodzenia();
                PdfMain.dodajLinieOpisuBezOdstepu(document, pracownik1, Element.ALIGN_LEFT, 2);
                text1 = mezczyzna?"był zatrudniony":"była zatrudniona";
                String pracownik3 = text1+" "+pracodawca1+" "+pracodawca3;
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, pracownik3, Element.ALIGN_LEFT, 2);
                List<EtatPrac> etatList = angaz.getEtatList();
                for (EtatPrac et : etatList) {
                    if (Data.czyjestpoTerminData(datanawiazaniastosunkupracy, et.getDataod())) {
                        String etat = "w okresie od "+et.getDataod()+" do "+et.getDatado()+" w wymiarze etatu: "+et.getEtat();
                        PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, etat, Element.ALIGN_LEFT, 2);
                    }
                }
                document.add(Chunk.NEWLINE);
                text1 = mezczyzna?"wykonywał":"wykonywała";
                String stanowiskotmp = "2. W okresie zatrudnienia pracownik "+text1+" pracę tymczasową na rzecz: - nie dotyczy";
                PdfMain.dodajLinieOpisuBezOdstepu(document, stanowiskotmp, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                text1 = mezczyzna?"wykonywał":"wykonywała";
                String stanowisko = "3. W okresie zatrudnienia pracownik "+text1+" pracę";
                PdfMain.dodajLinieOpisuBezOdstepu(document, stanowisko, Element.ALIGN_LEFT, 2);
                List<Stanowiskoprac> stanowiskopracList = angaz.getStanowiskopracList();
                if (stanowiskopracList==null||stanowiskopracList.size()==0) {
                    String stan = "w okresie od ................. do ............ na stanowisku: BRAK OPISU STANOWISKA W SKŁADNIKACH";
                        PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, stan, Element.ALIGN_LEFT, 2);
                } else {
                    for (Stanowiskoprac p : stanowiskopracList) {
                        if (Data.czyjestpoTerminData(datanawiazaniastosunkupracy, p.getDataod())) {
                            String stan = "w okresie od "+p.getDataod()+" do "+p.getDatado()+" na stanowisku: "+p.getOpis();
                            PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, stan, Element.ALIGN_LEFT, 2);
                        }
                    }
                }
                document.add(Chunk.NEWLINE);
                String ustanie = "4. Stosunek pracy ustał w wyniku: ";
                PdfMain.dodajLinieOpisuBezOdstepu(document, ustanie, Element.ALIGN_LEFT, 2);
                boolean rozwiazanie = rozwiazanieumowy.isRozwiazanie();
                boolean wypowiedzenie = rozwiazanieumowy.isWypowiedzenie();
                boolean porozumieniestron = rozwiazanieumowy.isPorozumienie();
                boolean uplywczasu = rozwiazanieumowy.isUplywczasuzawarcia();
                if (wypowiedzenie) {
                    boolean pracownik2 = rozwiazanieumowy.isPracownik();
                    if (pracownik2) {
                        String ustanie1 = "za wypowiedzeniem ze strony pracownika "+rozwiazanieumowy.getPodstawaprawna();
                        PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, ustanie1, Element.ALIGN_LEFT, 2);
                    }
                    boolean pracodawca = rozwiazanieumowy.isPracodawca();
                    if (pracodawca) {
                        String ustanie1 = "za wypowiedzeniem ze strony pracodawcy "+rozwiazanieumowy.getPodstawaprawna();
                        PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, ustanie1, Element.ALIGN_LEFT, 2);
                    }
                } else if (rozwiazanie){
                    String ustanie1 = rozwiazanieumowy.getPodstawaprawna();
                    PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, ustanie1, Element.ALIGN_LEFT, 2);
                } else if (porozumieniestron){
                    String ustanie1 = rozwiazanieumowy.getPodstawaprawna();
                    PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, ustanie1, Element.ALIGN_LEFT, 2);
                }else if (uplywczasu){
                    String ustanie1 = rozwiazanieumowy.getPodstawaprawna();
                    PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, ustanie1, Element.ALIGN_LEFT, 2);
                }
                document.add(Chunk.NEWLINE);
                String skr = "5. Został zastosowany skrócony okres wypowiedzenia umowy o pracę na podstawie art. 36 1 § 1 Kodeksu pracy: ";
                if (rozwiazanieumowy.isSkroceneiokresuwyp()) {
                    skr = skr+" tak";
                } else {
                    skr = skr+" - nie dotyczy";
                }
                PdfMain.dodajLinieOpisuBezOdstepu(document, skr, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                boolean czydodano = false;
                String punkt6 = "6. W okresie zatrudnienia pracownik: ";
                PdfMain.dodajLinieOpisuBezOdstepu(document, punkt6, Element.ALIGN_LEFT, 2);
                text1 = mezczyzna?"wykorzystał":"wykorzystała";
                String zwolnienie148 = "1) "+text1+" zwolnienie od pracy przewidziane w art. 148(1) §1 Kodeksu pracu: 0";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, zwolnienie148, Element.ALIGN_LEFT, 2);
                String zwolnienie148a = "(liczba dni lub godzin zwolnienia wykorzystanego w roku kalendarzowym, w którym ustał stosunek pracy)";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, zwolnienie148a, Element.ALIGN_LEFT, 1);
                document.add(Chunk.NEWLINE);
                czydodano = false;
                text1 = mezczyzna?"wykorzystał":"wykorzystała";
                String urlop1 = "2) "+text1+" urlop wypoczynkowy w wymiarze: ";
                int dniwykorzystane = 0;
                //usuniete bo teraz bierze urlop tylko z tego roku przemycany w zmiennej urlopprezentacja
//                for (Swiadectwodni s : dnidoswiadectwa) {
//                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKodzbiorczy().equals("U")) {
//                        dniwykorzystane = (int) (dniwykorzystane+s.getDni());
//                        czydodano = true;
//                    }
//                }
                if (urlopprezentacja!=null) {
                    if (urlopprezentacja.getDoswiadectwadni()>0) {
                        dniwykorzystane = urlopprezentacja.getDoswiadectwadni();
                    }
                }
                int dniekwiwalentu = 0;
                if (ekwiwalentUrlop!=null&&ekwiwalentUrlop.getKwota()>0.0) {
                    int dnirokpoprzeni = urlopprezentacja.getDoswiadectwadni() <0?urlopprezentacja.getDoswiadectwadni():0;
                    dniwykorzystane = dniwykorzystane+dnirokpoprzeni+ekwiwalentUrlop.getDni();
                    dniekwiwalentu = dnirokpoprzeni+dniekwiwalentu+ekwiwalentUrlop.getDni();
                    czydodano = true;
                }
                int sumadniurlopu = dniwykorzystane; 
                if (czydodano==false) {
                    // nie mam pojecia po to rto jest
                    //urlop1 = urlop1+sumadniurlopu+" 0";
                    urlop1 = urlop1+sumadniurlopu;
                } else {
                    urlop1 = urlop1+sumadniurlopu;
                }
                String urlopdni = urlop1+" dni";
                double urlopgodzi = sumadniurlopu*8.0;
                String urlopgodziny = " tj. "+urlopgodzi+" godzin";
                String razemurlop = urlopdni+urlopgodziny;
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, razemurlop, Element.ALIGN_LEFT, 2);
                String razemurlopa = "(urlop wypoczynkowy wykorzystany w roku kalendarzowym, w którym ustał stosunek pracy)";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, razemurlopa, Element.ALIGN_LEFT, 1);
                if (dniekwiwalentu>0) {
                    String urlop1a = "W tym wypłacono ekwiwalent za dni: " +dniekwiwalentu;
                    PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, urlop1a, Element.ALIGN_LEFT, 2);
                }
                czydodano = false;
                String urlop2 = "w tym urlop wypoczynkowy wykorzystany na podstawie art. 167 2 Kodeksu pracy w roku kalendarzowym, w którym ustał stosunek pracy: ";
                for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKod().equals("UZ")) {
                        urlop2 = urlop2+s.getDnirobocze();
                        czydodano = true;
                    }
                }
                if (czydodano==false) {
                    urlop2 = urlop2+" nie dotyczy";
                }
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, urlop2, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                czydodano = false;
                text1 = mezczyzna?"wykorzystał":"wykorzystała";
                String urlopopiekunczy = "3) "+text1+" urlop opiekuńczy w wymiarze: 0";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, urlopopiekunczy, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                czydodano = false;
                String urlop3 = "4) korzystał z urlopu bezpłatnego: ";
                 for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKod().equals("X")) {
                        //jak to bylo to byla zdublowana tresc punktu drugiego
//                        String urlop3a = "2) korzystał z urlopu bezpłatnego: ";
//                        PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, urlop3a, Element.ALIGN_LEFT, 2);
                        List<Nieobecnosc> nieobecnoscilista = s.getNieobecnoscilista()!=null?s.getNieobecnoscilista():new ArrayList<>();
                        for (Nieobecnosc nie : nieobecnoscilista) {
                            String nieobdetal = s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getOpis() + " w okresie od "+nie.getDataod()+" do "+nie.getDatado()+" dni "+nie.getDnikalendarzowe();
                            PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, nieobdetal, Element.ALIGN_LEFT, 2);
                            czydodano = true;
                        }
                    }
                }
                if (czydodano==false) {
                    urlop3 = urlop3+" nie dotyczy";
                    PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, urlop3, Element.ALIGN_LEFT, 2);
                }
                document.add(Chunk.NEWLINE);
                czydodano = false;
                String urlop4a = "5) wykorzystał urlop ojcowski w wymiarze: ";
                String urlop4b = " w ";
                for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKod().equals("UO")) {
                        urlop4a = urlop4a+s.getDnikalendarzowe();
                        urlop4b = urlop4b+s.getCzesci();
                        String urlop4c = s.getCzesci()==1? " częsci":" częsciach";
                        urlop4a = urlop4a+urlop4b+urlop4c;
                        czydodano = true;
                    }
                }
                if (czydodano==false) {
                    urlop4a = urlop4a+" nie dotyczy";
                }
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, urlop4a, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                czydodano = false;
                String urlop5a = "6) wykorzystał urlop rodzicielski udzielony na podstawie: ";
                String urlop5b = " w wymiarze ";
                String urlop5c = " w okresie (okresach) ";
                String urlop5d = " w ";
                String urlop5e = " częsciach ";
                String urlop5f = " w tym na podstawie art. 182(1c) §3 Kodeksu pracy w ";
                String urlop5g = " częsciach.";
                for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKodzbiorczy().equals("R")) {
                        urlop5b = urlop5b+s.getDnikalendarzowe();
                        urlop5c = okresy(s);
                        urlop5d = urlop5d+s.getCzesci();
                        czydodano = true;
                    }
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKod().equals("RZ")) {
                        urlop5f = urlop5f+s.getCzesci();
                    }
                }
                if (czydodano==false) {
                    urlop5a = urlop5a+" nie dotyczy";
                } else {
                    urlop5a = urlop5a+urlop5b+urlop5c+urlop5d+urlop5e+urlop5f+urlop5g;//uzupelnic
                }
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, urlop5a, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                czydodano = false;
                String urlop6a = "7) wykorzystał urlop wychowawczy udzielony na podstawie: ";
                String urlop6b = " w wymiarze ";
                String urlop6c = " w okresie (okresach) ";
                String urlop6d = " w ";
                String urlop6e = " częsciach ";
                for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKod().equals("WY")) {
                        urlop6b = urlop6b+s.getDnikalendarzowe();
                        urlop6c = okresy(s);
                        urlop6d = urlop6d+s.getCzesci();
                        urlop6a = urlop6a+urlop6b+urlop6c+urlop6d+urlop6e;//uzupelnic
                        czydodano = true;
                    }
                }
                if (czydodano==false) {
                    urlop6a = urlop6a+" nie dotyczy";
                }
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, urlop6a, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                czydodano = false;
                String ochrona = "8) korzystał z ochrony stosunku pracy, o której mowa w art. 186 8 § 1 pkt 2 Kodeksu pracy, w okresie (okresach) ";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, ochrona, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                String opieka = "9) wykorzystał zwolnienie od pracy przewidziane w art. 188 Kodeksu pracy ";
                for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKod().equals("MD")) {
                        opieka = opieka+s.getDnikalendarzowe();
                        czydodano = true;
                    }
                }
                if (czydodano==false) {
                    opieka = opieka+" nie dotyczy";
                }
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, opieka, Element.ALIGN_LEFT, 2);
                String opiekaa = "(liczba dni lub godzin zwolnienia wykorzystanego w roku kalendarzowym, w którym ustał stosunek pracy)";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, opiekaa, Element.ALIGN_LEFT, 1);
                document.add(Chunk.NEWLINE);
                czydodano = false;
                String pracazdalna = "10) wykonywał pracę zdalną przewidzianą w art. 188 Kodeksu pracy : nie";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, pracazdalna, Element.ALIGN_LEFT, 2);
                String pracazdalnaa ="(liczba dni wykonywania pracy zdalnej w roku kalendarzowym, w którym ustał stosunek pracy)";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, pracazdalnaa, Element.ALIGN_LEFT, 1);
                czydodano = false;
                document.add(Chunk.NEWLINE);
                String choroba = "11) był niezdolny do pracy przez okres:  ";
                double chorobadni = 0;
                for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKod().equals("CH")) {
                        chorobadni = chorobadni+s.getDnikalendarzowe();
                        czydodano = true;
                    }
                }
                if (czydodano==false) {
                    choroba = choroba+" nie dotyczy";
                } else {
                    choroba = choroba +chorobadni+" dni";
                }
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, choroba, Element.ALIGN_LEFT, 2);
                String chorobaa = "(liczba dni, za które pracownik otrzymał wynagrodzenie, zgodnie z art. 92 Kodeksu pracy, w roku kalendarzowym, w którym\n" +
"ustał stosunek pracy)";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, chorobaa, Element.ALIGN_LEFT, 1);
                document.add(Chunk.NEWLINE);
                czydodano = false;
                 String punkt12 = "12) nie";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, punkt12, Element.ALIGN_LEFT, 2);
                String punkt12a = "(dni, za które pracownik nie zachował prawa do wynagrodzenia, przypadające w okresie od dnia 1 stycznia 2003 r. do dnia 31 grudnia 2003 r., zgodnie z art. 92 § 1(1) Kodeksu pracy obowiązującym w tym okresie)";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, punkt12a, Element.ALIGN_LEFT, 1);
                czydodano = false;
                document.add(Chunk.NEWLINE);
                String wojsko = "13) odbył służbę wojskową w okresie:  ";
                for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKod().equals("SW")) {
                        wojsko = wojsko+s.getDnikalendarzowe()+" dni";
                        czydodano = true;
                    }
                }
                if (czydodano==false) {
                    wojsko = wojsko+" nie dotyczy";
                }
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, wojsko, Element.ALIGN_LEFT, 2);
                String wojskoa = "(okres odbywania czynnej służby wojskowej lub jej form zastępczych)";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, wojsko, Element.ALIGN_LEFT, 1);
                document.add(Chunk.NEWLINE);
                String szczegolne = "14) wykonywał pracę w szczególnych warunkach lub w szczególnym charakterze";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, szczegolne, Element.ALIGN_LEFT, 2);
                String szczagolnea = "(okresy wykonywania pracy oraz jej rodzaj i zajmowane stanowiska)";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, szczegolne, Element.ALIGN_LEFT, 1);
                document.add(Chunk.NEWLINE);
                String dodurlop = "15) wykorzystał dodatkowy urlop albo inne uprawnienia lub świadczenia przewidziane przepisami prawa pracy:  ";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, dodurlop, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
               
                String okresynieskladkowe = "16) okresy nieskładkowe:  ";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, okresynieskladkowe, Element.ALIGN_LEFT, 2);
                for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().isNieskladkowy()) {
                        List<Nieobecnosc> nieobecnoscilista = s.getNieobecnoscilista()!=null?s.getNieobecnoscilista():new ArrayList<>();
                        for (Nieobecnosc nie : nieobecnoscilista) {
                            String nieobdetal = s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getOpis() + " w okresie od "+nie.getDataod()+" do "+nie.getDatado()+" dni "+nie.getDnikalendarzowe();
                            PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, nieobdetal, Element.ALIGN_LEFT, 2);
                        }
                    }
                }
                String okresynieskladkowea = "(okresy nieskładkowe, przypadające w okresie zatrudnienia wskazanym w ust. 1, uwzględniane przy ustalaniu prawa do\n" +
"emerytury lub renty)";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, okresynieskladkowe, Element.ALIGN_LEFT, 1);
                document.add(Chunk.NEWLINE);
                String komornik = "7. Informacja o zajęciu wynagrodzenia: ";
                PdfMain.dodajLinieOpisuBezOdstepu(document, komornik, Element.ALIGN_LEFT, 2);
                List<Skladnikpotracenia> skladnikpotraceniaList = angaz.getSkladnikpotraceniaList();
                czydodano = false;
                for (Skladnikpotracenia skl : skladnikpotraceniaList) {
                    if (skl.getRodzajpotracenia().isPotraceniekomornicze()&&skl.isRozliczony()==false) {
                        double potracono = potracono(skl);
                        if (potracono>0.0) {
                            String poz = F.curr(potracono);
                            String opis = skl.getRodzajpotracenia().getOpis()+" komornik: "+skl.getKomornik()+" nr sprawy: "+skl.getNrsprawy()+" kwota potrącona: "+poz;
                            PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, opis, Element.ALIGN_LEFT, 2);
                            czydodano = true;
                        }
                    }
                }
                if (czydodano==false) {
                    PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, "nie dotyczy", Element.ALIGN_LEFT, 2);
                }
                document.add(Chunk.NEWLINE);
                String dodatkowe = "8. Informacje uzupełniające: Świadectwo sporządzono na podstawie akt osobowych pracownika.";
                PdfMain.dodajLinieOpisuBezOdstepu(document, dodatkowe, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                document.add(Chunk.NEWLINE);
                PdfMain.dodajLinieOpisuBezOdstepu(document, "..................................................................", Element.ALIGN_LEFT, 2);
                PdfMain.dodajLinieOpisuBezOdstepu(document, "(pieczęć i podpis pracodawcy lub osoby działającej w jego imieniu)", Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                PdfMain.dodajLinieOpisu(document, "POUCZENIE", Element.ALIGN_CENTER, 1);
                String pouczenie = "Pracownik może w ciągu 14 dni od otrzymania świadectwa pracy wystąpić z wnioskiem do pracodawcy o sprostowanie świadectwa pracy. W razie nieuwzględnienia wniosku "
                        + "pracownikowi przysługuje, w ciągu 14 dni od zawiadomienia o odmowie sprostowania świadectwa pracy, prawo wystąpienia z żądaniem jego sprostowania do sądu pracy. "
                        + "W przypadku niezawiadomienia przez pracodawcę o odmowie sprostowania świadectwa pracy, żądanie sprostowania świadectwa pracy wnosi się do sądu pracy."
                        + "(podstawa prawna – art. 97 § 2¹ Kodeksu pracy)";
//                if (firma.getSadpracy()!=null&&!firma.getSadpracy().equals("")) {
//                    pouczenie = pouczenie + " "+firma.getSadpracy();
//                } else {
//                    pouczenie = pouczenie + "..............................................................................";
//                }
                PdfMain.dodajLinieOpisu(document, pouczenie, Element.ALIGN_JUSTIFIED, 1);
                PdfMain.dodajLinieOpisu(document, "(podstawa prawna - art. 97 §2(1) Kodeksu pracy)", Element.ALIGN_CENTER, 1);
                finalizacjaDokumentuQR(document,nazwa);
                String f = "pokazwydruk('"+nazwa+"');";
                PrimeFaces.current().executeScript(f);
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
    }

    private static String okresy(Swiadectwodni s) {
        String zwrot = "";
         List<Nieobecnosc> nieobecnoscilista = s.getNieobecnoscilista();
         for (Nieobecnosc sa : nieobecnoscilista) {
             zwrot = zwrot + " od " + sa.getDataod()+" do "+sa.getDatado()+",";
         }
         return zwrot;
    }

    private static double potracono(Skladnikpotracenia skl) {
        double zwrot = 0.0;
        List<Naliczeniepotracenie> naliczeniepotracenieList = skl.getNaliczeniepotracenieList();
        for (Naliczeniepotracenie s : naliczeniepotracenieList) {
            zwrot = zwrot + s.getKwota();
        }
        return Z.z(zwrot);
    }
    
    //zmeiniono 18.09.2023 bo maja byc kwoty potracone
//    private static double pozostalo(Skladnikpotracenia skl) {
//        double kwota = skl.getKwotakomornicza();
//        double zwrot = 0.0;
//        List<Naliczeniepotracenie> naliczeniepotracenieList = skl.getNaliczeniepotracenieList();
//        for (Naliczeniepotracenie s : naliczeniepotracenieList) {
//            zwrot = zwrot + s.getKwota();
//        }
//        if (kwota>0) {
//            zwrot = Z.z(kwota-zwrot);
//        }
//        return Z.z(zwrot);
//    }
}
