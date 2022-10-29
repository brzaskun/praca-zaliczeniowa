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
import entity.EtatPrac;
import entity.FirmaKadry;
import entity.Naliczeniepotracenie;
import entity.Nieobecnosc;
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
     public static void drukuj(Swiadectwo swiadectwo, List<Swiadectwodni> dnidoswiadectwa) {
        try {
            Rozwiazanieumowy rozwiazanieumowy = swiadectwo.getRozwiazanieumowy();
            Umowa umowa = swiadectwo.getRozwiazanieumowy().getUmowa();
            Pracownik pracownik = umowa.getPracownik();
            FirmaKadry firma = umowa.getAngaz().getFirma();
            String pesel = pracownik.getPesel()!=null?pracownik.getPesel():pracownik.getPaszport();
            String nazwa = pesel+"swiadectwo.pdf";
            if (nazwa != null) {
                Document document = PdfMain.inicjacjaA4PortraitLean();
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                String gdzie = firma.getMiasto()+", "+swiadectwo.getDatawystawienia();
                PdfMain.dodajLinieOpisu(document, gdzie, Element.ALIGN_RIGHT, 2);
                String pracodawca1 = firma.getNazwa();
                String pracodawca2 = "Regon "+firma.getRegon()+", PKD brak";
                String pracodawca3 = firma.getAdres();
                PdfMain.dodajLinieOpisuBezOdstepu(document, pracodawca1, Element.ALIGN_LEFT, 2);
                PdfMain.dodajLinieOpisuBezOdstepu(document, pracodawca2, Element.ALIGN_LEFT, 2);
                PdfMain.dodajLinieOpisu(document, pracodawca3, Element.ALIGN_LEFT, 2);
                PdfMain.dodajLinieOpisu(document, "ŚWIADECTWO PRACY", Element.ALIGN_CENTER, 3);
                String pracownik1 = "1. Stwierdza się, że "+pracownik.getNazwiskoImie()+" urodzony/a "+pracownik.getDataurodzenia();
                PdfMain.dodajLinieOpisuBezOdstepu(document, pracownik1, Element.ALIGN_LEFT, 2);
                String pracownik3 = "był/a zatrudniony "+pracodawca1+" "+pracodawca3;
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, pracownik3, Element.ALIGN_LEFT, 2);
                List<EtatPrac> etatList = umowa.getEtatList();
                for (EtatPrac et : etatList) {
                    String etat = "w okresie od "+et.getDataod()+" do "+et.getDatado()+" w wymiarze "+et.getEtat();
                    PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, etat, Element.ALIGN_LEFT, 2);
                }
                document.add(Chunk.NEWLINE);
                String stanowisko = "2. W okresie zatrudnienia pracownik wykonywał/a pracę";
                List<Stanowiskoprac> stanowiskopracList = umowa.getStanowiskopracList();
                for (Stanowiskoprac p : stanowiskopracList) {
                    String stan = "w okresie od "+p.getDataod()+" do "+p.getDatado()+" w wymiarze "+p.getOpis();
                    PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, stan, Element.ALIGN_LEFT, 2);
                }
                document.add(Chunk.NEWLINE);
                String tym = "3. W okresie zatrudnienia pracownik wykonywał/a pracę tymczasową na rzecz - nie dotyczy";
                PdfMain.dodajLinieOpisuBezOdstepu(document, tym, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                String ustanie = "4. Stosunek pracy ustał w wyniku: ";
                PdfMain.dodajLinieOpisuBezOdstepu(document, ustanie, Element.ALIGN_LEFT, 2);
                boolean rozwiazanie = rozwiazanieumowy.isRozwiazanie()||rozwiazanieumowy.isWypowiedzenie();
                if (rozwiazanie) {
                    boolean pracownik2 = rozwiazanieumowy.isPracownik();
                    if (pracownik2) {
                        String ustanie1 = "za wypowiedzeniem ze strony pracownika "+rozwiazanieumowy.getPodstawaprawna();
                        PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, ustanie1, Element.ALIGN_LEFT, 2);
                    }
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
                String urlop = "6. W okresie zatrudnienia pracownik: ";
                PdfMain.dodajLinieOpisuBezOdstepu(document, urlop, Element.ALIGN_LEFT, 2);
                String urlop1 = "1) wykorzystał urlop wypoczynkowy w wymiarze: ";
                for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKodzbiorczy().equals("U")) {
                        urlop1 = urlop1+s.getDni();
                        czydodano = true;
                    }
                }
                if (czydodano==false) {
                    urlop1 = urlop1+" nie dotyczy";
                }
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, urlop1, Element.ALIGN_LEFT, 2);
                czydodano = false;
                String urlop2 = "w tym urlop wypoczynkowy wykorzystany na podstawie art. 167 2 Kodeksu pracy w roku kalendarzowym, w którym ustał stosunek pracy: ";
                for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKod().equals("UZ")) {
                        urlop2 = urlop2+s.getDni();
                        czydodano = true;
                    }
                }
                if (czydodano==false) {
                    urlop2 = urlop2+" nie dotyczy";
                }
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, urlop2, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                czydodano = false;
                String urlop3 = "2) korzystał z urlopu bezpłatnego: ";
                 for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKod().equals("X")) {
                        urlop3 = urlop3+s.getDni();
                        czydodano = true;
                    }
                }
                if (czydodano==false) {
                    urlop3 = urlop3+" nie dotyczy";
                }
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, urlop3, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                czydodano = false;
                String urlop4a = "3) wykorzystał urlop ojcowski w wymiarze: ";
                String urlop4b = " w ";
                String urlop4c = " częsciach";
                for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKod().equals("J")) {
                        urlop4a = urlop4a+s.getDni();
                        urlop4b = urlop4b+s.getCzesci();
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
                String urlop5a = "4) wykorzystał urlop rodzicielski udzielony na podstawie: ";
                String urlop5b = " w wymiarze ";
                String urlop5c = " w okresie (okresach) ";
                String urlop5d = " w ";
                String urlop5e = " częsciach ";
                String urlop5f = " w tym na podstawie art. 182(1c) §3 Kodeksu pracy w ";
                String urlop5g = " częsciach.";
                for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKodzbiorczy().equals("R")) {
                        urlop5b = urlop5b+s.getDni();
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
                String urlop6a = "5) wykorzystał urlop wychowawczy udzielony na podstawie: ";
                String urlop6b = " w wymiarze ";
                String urlop6c = " w okresie (okresach) ";
                String urlop6d = " w ";
                String urlop6e = " częsciach ";
                for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKod().equals("WY")) {
                        urlop6b = urlop6b+s.getDni();
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
                String ochrona = "6) korzystał z ochrony stosunku pracy, o której mowa w art. 186 8 § 1 pkt 2 Kodeksu pracy, w okresie (okresach) ";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, ochrona, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                String opieka = "7) wykorzystał zwolnienie od pracy przewidziane w art. 188 Kodeksu pracy ";
                for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKod().equals("MD")) {
                        opieka = opieka+s.getDni();
                        czydodano = true;
                    }
                }
                if (czydodano==false) {
                    opieka = opieka+" nie dotyczy";
                }
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, opieka, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                czydodano = false;
                String choroba = "8) był niezdolny do pracy przez okres:  ";
                for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKodzbiorczy().equals("CH")) {
                        choroba = choroba+s.getDni()+" dni";
                        czydodano = true;
                    }
                }
                if (czydodano==false) {
                    choroba = choroba+" nie dotyczy";
                }
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, choroba, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, "9) ", Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                czydodano = false;
                String wojsko = "10) odbył służbę wojskową w okresie:  ";
                for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getKod().equals("SW")) {
                        wojsko = wojsko+s.getDni()+" dni";
                        czydodano = true;
                    }
                }
                if (czydodano==false) {
                    wojsko = wojsko+" nie dotyczy";
                }
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, wojsko, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                String szczegolne = "11) wykonywał pracę w szczególnych warunkach lub w szczególnym charakterze";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, szczegolne, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                String dodurlop = "12) wykorzystał dodatkowy urlop albo inne uprawnienia lub świadczenia przewidziane przepisami prawa pracy:  ";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, dodurlop, Element.ALIGN_LEFT, 2);
                document.add(Chunk.NEWLINE);
                String okresynieskladkowe = "13) okresy nieskładkowe:  ";
                PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, okresynieskladkowe, Element.ALIGN_LEFT, 2);
                for (Swiadectwodni s : dnidoswiadectwa) {
                    if (s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().isNieskladkowy()) {
                        List<Nieobecnosc> nieobecnoscilista = s.getNieobecnoscilista()!=null?s.getNieobecnoscilista():new ArrayList<>();
                        for (Nieobecnosc nie : nieobecnoscilista) {
                            String nieobdetal = s.getNieobecnoscswiadectwoschema().getRodzajnieobecnosci().getOpis() + " w okresie od "+nie.getDataod()+" do "+nie.getDatado();
                            PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, nieobdetal, Element.ALIGN_LEFT, 2);
                        }
                    }
                }
                document.add(Chunk.NEWLINE);
                String komornik = "7. Informacja o zajęciu wynagrodzenia: ";
                PdfMain.dodajLinieOpisuBezOdstepu(document, komornik, Element.ALIGN_LEFT, 2);
                List<Skladnikpotracenia> skladnikpotraceniaList = umowa.getSkladnikpotraceniaList();
                czydodano = false;
                for (Skladnikpotracenia skl : skladnikpotraceniaList) {
                    if (skl.getRodzajpotracenia().getOpis().contains("Tytuł wykonawczy")&&skl.isRozliczony()==false) {
                        double pozostalo = pozostalo(skl);
                        if (pozostalo>0.0) {
                            String poz = F.curr(pozostalo);
                            String opis = skl.getRodzajpotracenia().getOpis()+" od: "+skl.getDataOd()+" kwota: "+poz;
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
                String pouczenie = "Pracownik może w ciągu 14 dni od otrzymania świadectwa pracy wystąpić z wnioskiem do pracodawcy o sprostowanie " +
                "świadectwa pracy. W razie nieuwzględnienia wniosku pracownikowi przysługuje, w ciągu 14 dni od zawiadomienia o odmowie " +
                "sprostowania świadectwa pracy, prawo wystąpienia z żądaniem jego sprostowania do sądu pracy. W przypadku " +
                "niezawiadomienia przez pracodawcę o odmowie sprostowania świadectwa pracy, żądanie sprostowania świadectwa pracy " +
                "wnosi się do sądu pracy.";
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

    private static double pozostalo(Skladnikpotracenia skl) {
        double kwota = skl.getKwotakomornicza();
        double zwrot = 0.0;
        List<Naliczeniepotracenie> naliczeniepotracenieList = skl.getNaliczeniepotracenieList();
        for (Naliczeniepotracenie s : naliczeniepotracenieList) {
            zwrot = zwrot + s.getKwota();
        }
        return Z.z(kwota-zwrot);
    }
}
