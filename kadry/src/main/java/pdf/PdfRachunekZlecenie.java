/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import data.Data;
import entity.Angaz;
import entity.FirmaKadry;
import entity.Naliczenienieobecnosc;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Pasekwynagrodzen;
import entity.Rachunekdoumowyzlecenia;
import entity.Umowa;
import entity.Zmiennawynagrodzenia;
import error.E;
import java.io.ByteArrayOutputStream;
import java.util.List;
import msg.Msg;
import org.primefaces.PrimeFaces;
import static pdf.PdfFont.formatujWaluta;
import static pdf.PdfFont.ustawfrazeAlign;
import static pdf.PdfMain.finalizacjaDokumentuQR;
import static pdf.PdfMain.inicjacjaWritera;
import static pdf.PdfMain.naglowekStopkaP;
import static pdf.PdfMain.otwarcieDokumentu;
import plik.Plik;

/**
 *
 * @author Osito
 */
public class PdfRachunekZlecenie {
    
    public static ByteArrayOutputStream drukujJeden(Pasekwynagrodzen pasek, String nazwa) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            if (pasek != null) {
                Document document = PdfMain.inicjacjaA4Portrait(80,60);
                PdfWriter writer = PdfMain.inicjacjaWriteraOut(document, out);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                dodajtresc(pasek, document);
                finalizacjaDokumentuQR(document,nazwa);
                Plik.zapiszBufferdoPlik(nazwa, out);
                String f = "pokazwydruk('"+nazwa+"');";
                PrimeFaces.current().executeScript(f);
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
        return out;
    }
    
    public static ByteArrayOutputStream drukuj(List<Pasekwynagrodzen> lista, String nazwa) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            if (lista != null) {
                Document document = PdfMain.inicjacjaA4Portrait(80,60);
                PdfWriter writer = PdfMain.inicjacjaWriteraOut(document, out);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                for (Pasekwynagrodzen pasek : lista) {
                    dodajtresc(pasek, document);
                    document.add(Chunk.NEXTPAGE);
                }
                finalizacjaDokumentuQR(document,nazwa);
                Plik.zapiszBufferdoPlik(nazwa, out);
                String f = "pokazwydruk('"+nazwa+"');";
                PrimeFaces.current().executeScript(f);
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
        return out;
    }
    
    private static void dodajtresc(Pasekwynagrodzen pasek, Document document) {
        try {
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 11);
            Font fontM = new Font(helvetica, 9);
            Font fontS = new Font(helvetica, 6);
            FirmaKadry firma = pasek.getAngaz().getFirma();
            Paragraph paragraph = new Paragraph(new Phrase(firma.getMiasto()+", dnia "+pasek.getDatawyplaty(), fontM));
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("RACHUNEK", font));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("__________________________________________________________________________", font));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("dla "+firma.getNazwa(), fontM));
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("z siedzibą w : ", fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase(firma.getAdres(), fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("NIP : "+firma.getNip(), fontM));
            document.add(paragraph);
            boolean zlecenie = false;
            if (pasek.getRachunekdoumowyzleceniaList()!=null&&pasek.getRachunekdoumowyzleceniaList().size()>0) {
                Rachunekdoumowyzlecenia rachunek = pasek.getRachunekdoumowyzleceniaList().get(0);
                paragraph = new Paragraph(new Phrase("za wykonanie w czasie okresie od "+rachunek.getDataod()+" do "+rachunek.getDatado(), fontM));
                document.add(paragraph);
                zlecenie = rachunek.isZlecenie();
                if (zlecenie) {
                    paragraph = new Paragraph(new Phrase("prac zgodnie z dwustronnie zawartą umową nr "+rachunek.getUmowa().getNrkolejny(), fontM));
                } else {
                    paragraph = new Paragraph(new Phrase("dzieła zgodnie z dwustronnie zawartą umową nr "+rachunek.getUmowa().getNrkolejny(), fontM));
                }
                document.add(paragraph);
                paragraph = new Paragraph(new Phrase("- ilośc godzin "+rachunek.getIloscgodzin(), fontM));
                document.add(paragraph);
                if (rachunek.getIloscgodzinoddelegowanie()>0.0) {
                    paragraph = new Paragraph(new Phrase("- ilośc godzin oddelegowanie"+rachunek.getIloscgodzinoddelegowanie(), fontM));
                }
                document.add(Chunk.NEWLINE);
            } else {
                zlecenie = pasek.isZlecenie();
                paragraph = new Paragraph(new Phrase("za wykonanie w czasie okresie "+pasek.getRok()+"/"+pasek.getMc(), fontM));
                document.add(paragraph);
                if (zlecenie) {
                    paragraph = new Paragraph(new Phrase("prac zgodnie z dwustronnie zawartą umową nr "+pasek.getKalendarzmiesiac().getAngaz().getAktywnaUmowa().getNrkolejny(), fontM));
                } else {
                    paragraph = new Paragraph(new Phrase("dzieła zgodnie z dwustronnie zawartą umową nr "+pasek.getKalendarzmiesiac().getAngaz().getAktywnaUmowa().getNrkolejny(), fontM));
                }
                document.add(paragraph);
                document.add(Chunk.NEWLINE);
            }
            if (zlecenie) {
                paragraph = new Paragraph(new Phrase("Stwierdzam, że praca została wykonana według warunków umowy dwustronnie zawartej.", fontM));
            } else {
                paragraph = new Paragraph(new Phrase("Stwierdzam, że dzieło została wykonana według warunków umowy dwustronnie zawartej.", fontM));
            }
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("..............................", fontM));
            document.add(paragraph);
            if (zlecenie) {
                paragraph = new Paragraph(new Phrase("podpis Zleceniodawcy", fontM));
            } else {
                paragraph = new Paragraph(new Phrase("podpis Zamawiającego", fontM));
            }
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            if (zlecenie) {
                paragraph = new Paragraph(new Phrase("Dane Zleceniobiorcy ", fontM));
            } else {
                paragraph = new Paragraph(new Phrase("Dane Wykonawcy ", fontM));
            }   
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase(pasek.getPracownik().getNazwiskoImie()+" PESEL: "+pasek.getPracownik().getPesel(), fontM));
            document.add(paragraph);
            if (pasek.getPracownik().getPlec().equals("K")) {
                paragraph = new Paragraph(new Phrase("zamieszkała w "+pasek.getPracownik().getAdres(), fontM));
            } else {
                paragraph = new Paragraph(new Phrase("zamieszkały w "+pasek.getPracownik().getAdres(), fontM));
            }
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("Pozycje rachunku: ", fontM));
            document.add(paragraph);
            PdfMain.dodajLinieOpisuBezOdstepuTab(document, "1) Wynagrodzenie brutto: ", f.F.curr(pasek.getBrutto()), Element.ALIGN_LEFT, 1, 100);
            PdfMain.dodajLinieOpisuBezOdstepuTab(document, "2) Składki na ub. społeczne: ", f.F.curr(pasek.getRazemspolecznepracownik()), Element.ALIGN_LEFT, 1, 100);
            PdfMain.dodajLinieOpisuBezOdstepuTab(document, "3) Koszt uzyskania przychodu: ", f.F.curr(pasek.getKosztyuzyskania()), Element.ALIGN_LEFT, 1, 100);
            PdfMain.dodajLinieOpisuBezOdstepuTab(document, "4) Podstawa opodatkowania: ", f.F.curr(pasek.getPodstawaopodatkowania()), Element.ALIGN_LEFT, 1, 100);
            PdfMain.dodajLinieOpisuBezOdstepuTab(document, "5) Naliczona zaliczka na pod. doch.: ", f.F.curr(pasek.getPodatekdochodowy()), Element.ALIGN_LEFT, 1, 100);
            PdfMain.dodajLinieOpisuBezOdstepuTab(document, "6) Pobrana składka na ub. zdrowotne: ", f.F.curr(pasek.getPraczdrowotne()), Element.ALIGN_LEFT, 1, 100);
            PdfMain.dodajLinieOpisuBezOdstepuTab(document, "7) Kwota wolna:           ", f.F.curr(pasek.getKwotawolna()), Element.ALIGN_LEFT, 1, 100);
            PdfMain.dodajLinieOpisuBezOdstepuTab(document, "8) Potrącenia:            ", f.F.curr(pasek.getPotracenia()), Element.ALIGN_LEFT, 1, 100);
            document.add(Chunk.NEWLINE);
            PdfMain.dodajLinieOpisuBezOdstepuTab(document, "9) Kwota do wypłaty: ", f.F.curr(pasek.getNetto()), Element.ALIGN_LEFT, 1, 100);
            PdfMain.dodajLinieOpisuBezOdstepuTab(document, "słownie: ", slownie.Slownie.slownie(String.valueOf(pasek.getNetto())), Element.ALIGN_LEFT, 1, 100);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("Data wypłaty: "+pasek.getDatawyplaty(), fontM));
            if (pasek.getPracownik().getBankkonto()!=null) {
                paragraph = new Paragraph(new Phrase("Forma wypłaty - przelew", fontM));
                paragraph = new Paragraph(new Phrase("Nr konta: "+pasek.getPracownik().getBankkonto(), fontM));
            } else {
                paragraph = new Paragraph(new Phrase("Forma wypłaty - gotówka", fontM));
                paragraph = new Paragraph(new Phrase("", fontM));
            }

//            PdfMain.dodajElementListy(document, "1) Rodzaj wykonywanej pracy: ", umowa.getStanowisko(), fontM);
//            PdfMain.dodajElementListy(document, "2) Miejsce wykonywania pracy: ", umowa.getMiejscepracy(), fontM);
//            PdfMain.dodajElementListy(document, "3) Wymiar czasu pracy: ", umowa.getEtat(), fontM);
//            double wynagrodzeniemc = umowa.getWynagrodzeniemiesieczne();
//            if (wynagrodzeniemc!=0.0) {
//                PdfMain.dodajElementListy(document, "4) Wynagrodzenie zasadnicze: ", umowa.pobierzwynagrodzenieString(wynagrodzeniemc), fontM);
//            }
//            double wynagrodzeniegodzinowe = umowa.getWynagrodzeniegodzinowe();
//            if (wynagrodzeniegodzinowe!=0.0) {
//                PdfMain.dodajElementListy(document, "4) Wynagrodzenie godzinowe: ", umowa.pobierzwynagrodzenieString(wynagrodzeniegodzinowe), fontM);
//            }
//            PdfMain.dodajElementListy(document, "5) Inne warunki zatrudnienia: ", "", fontM);
//            String par8 = umowa.getInnewarunkizatrudnienia();
//            PdfMain.dodajLinieOpisuSpacing(document, par8, Element.ALIGN_JUSTIFIED, 1, 10);
//            PdfMain.dodajElementListy(document, "6) termin rozpoczęcia pracy: ", umowa.getTerminrozpoczeciapracy(), fontM);
//            PdfMain.dodajElementListy(document, "7) dopuszczalna liczba godzin, których przekroczenie uprawnia pracownika do dodatku z art. 151(1)§1 KP", umowa.getDopuszczalnailoscgodzin(), fontM);
//            paragraph = new Paragraph(new Phrase("2. Przyczyny uzasadniające zawarcie umowy o pracę na czas nieokreślony w celu, o którym mowa w art. 25(1)§4pkt.1-3,4: ", fontM));
//            document.add(paragraph);
//            paragraph = new Paragraph(new Phrase(umowa.getPrzyczynaumowaokreslony(), fontM));
//            document.add(paragraph);
//            document.add(Chunk.NEWLINE);
//            document.add(Chunk.NEWLINE);
//            document.add(Chunk.NEWLINE);
            //document.add(new Paragraph(new Phrase(umowa.getPracownik().getNazwiskoImie()+"                                                          "+umowa.getAngaz().getFirma().getReprezentant(), fontM)));
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("..............................", fontM));
            document.add(paragraph);
            if (zlecenie) {
                paragraph = new Paragraph(new Phrase("data i podpis Zleceniobiorcy", fontM));
            } else {
                paragraph = new Paragraph(new Phrase("data i podpis Wykonawcy", fontM));
            }
            document.add(paragraph);
        } catch (Exception ex) {
            E.e(ex);
        }
    }
    
    
    
    
         
     public static void dodajwierszeSkladniki(List<Naliczenieskladnikawynagrodzenia> wykaz,List<Naliczenienieobecnosc> wykaznieob, PdfPTable table) {
        int i = 1;
        for (Naliczenieskladnikawynagrodzenia rs : wykaz) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
            table.addCell(ustawfrazeAlign(rs.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getOpisskrocony(), "left",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotadolistyplac()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotaumownazacalymc()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotadolistyplac()), "left",6));
        }
        for (Naliczenienieobecnosc rs : wykaznieob) {
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "left",6,10f));
            table.addCell(ustawfrazeAlign(rs.getNieobecnosc().getKod(), "left",6));
            table.addCell(ustawfrazeAlign(rs.getNieobecnosc().getOpisRodzajSwiadczenie()+" "+rs.getJakiskladnikredukowalny(), "left",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotazus()), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getKwotabezzus()), "right",6));
            if (rs.getKwotastatystyczna()!=0.0) {
                table.addCell(ustawfrazeAlign(formatujWaluta(-rs.getKwotastatystyczna()), "left",6));
            } else {
                table.addCell(ustawfrazeAlign(formatujWaluta(-rs.getKwotaredukcji()), "left",6));
            }
        }
    }

    public static void drukujaneks(Zmiennawynagrodzenia p, String dataaneksu, boolean netto0brutto1) {
        try {
            Angaz angaz = p.getSkladnikwynagrodzenia().getAngaz();
            String nazwa = angaz.getPracownik().getPesel()+"umowa.pdf";
            if (angaz != null) {
                Document document = PdfMain.inicjacjaA4Portrait(80,60);
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                dodajtrescAneks(p, angaz, document, dataaneksu, netto0brutto1);
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
    
    public static ByteArrayOutputStream drukujaneksMail(Zmiennawynagrodzenia p, String dataaneksu, boolean netto0brutto1) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(); 
        try {
            Angaz angaz = p.getSkladnikwynagrodzenia().getAngaz();
            String nazwa = angaz.getPracownik().getPesel()+"umowa.pdf";
            if (angaz != null) {
                Document document = PdfMain.inicjacjaA4Portrait(80,60);
                PdfWriter writer = PdfWriter.getInstance(document, out);
                writer.setInitialLeading(16);
                writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                dodajtrescAneks(p, angaz, document, dataaneksu, netto0brutto1);
                finalizacjaDokumentuQR(document,nazwa);
                String f = "pokazwydruk('"+nazwa+"');";
                PrimeFaces.current().executeScript(f);
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
        return out;
    }
    
    public static void drukujanekswszystkie(List<Umowa> listaumowy, FirmaKadry firmaKadry, String dataaneksu) {
        try {
            String nazwa = firmaKadry.getNip()+"aneksy.pdf";
            if (listaumowy != null) {
                Document document = PdfMain.inicjacjaA4Portrait(80,60);
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                int licznik = 0;
                for (Umowa umowa : listaumowy) {
                    Zmiennawynagrodzenia p = umowa.getZmiennawynagrodzenia();
                    if (p!=null&&p.getNowakwota()>0.0) {
                        if (licznik>0) {
                            document.newPage();
                        }
                        Angaz angaz = p.getSkladnikwynagrodzenia().getAngaz();
                        dodajtrescAneks(p, angaz, document, dataaneksu, umowa.isNetto0brutto1());
                        licznik++;
                    }
                }
                finalizacjaDokumentuQR(document,nazwa);
                String f = "pokazwydruk('"+nazwa+"');";
                PrimeFaces.current().executeScript(f);
                Msg.msg("Wydrukowano aneksy");
            } else {
                Msg.msg("w", "Nie ma danych");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie wprowadzono kwot");
        }
    }
    
    public static ByteArrayOutputStream drukujanekswszystkieMail(List<Umowa> listaumowy, FirmaKadry firmaKadry, String dataaneksu) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(); 
        try {
            String nazwa = firmaKadry.getNip()+"aneksy.pdf";
            if (listaumowy != null) {
                Document document = PdfMain.inicjacjaA4Portrait(80,60);
                PdfWriter writer = PdfWriter.getInstance(document, out);
                writer.setInitialLeading(16);
                writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                int licznik = 0;
                for (Umowa umowa : listaumowy) {
                    Zmiennawynagrodzenia p = umowa.getZmiennawynagrodzenia();
                    if (p!=null&&p.getNowakwota()>0.0) {
                        if (licznik>0) {
                            document.newPage();
                        }
                        Angaz angaz = p.getSkladnikwynagrodzenia().getAngaz();
                        dodajtrescAneks(p, angaz, document, dataaneksu, umowa.isNetto0brutto1());
                        licznik++;
                    }
                }
                finalizacjaDokumentuQR(document,nazwa);
                Msg.msg("Przygotowano aneksy dla maila");
            } else {
                Msg.msg("w", "Nie ma danych");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie wprowadzono kwot");
        }
        return out;
    }
     
     private static void dodajtrescAneks(Zmiennawynagrodzenia zm, Angaz angaz, Document document, String datazmiany, boolean netto0brutto1) {
        try {
            String dataaneksu = Data.odejmijdni(datazmiany,1);
            Umowa umowa = angaz.getAktywnaUmowa();
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 11);
            Font fontM = new Font(helvetica, 9);
            Font fontS = new Font(helvetica, 6);
            FirmaKadry firma = angaz.getFirma();
            Paragraph paragraph = new Paragraph(new Phrase(firma.getMiasto()+", dnia "+dataaneksu, fontM));
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph(new Phrase("..............................", fontM)));
            document.add(new Paragraph(new Phrase("pieczątka firmy", fontS)));
            document.add(Chunk.NEWLINE);
            String naglowek = umowa.getUmowakodzus().isPraca()?"ANEKS DO UMOWY O PRACĘ":"ANEKS DO UMOWY ZLECENIA";
            paragraph = new Paragraph(new Phrase(naglowek, font));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            String czastrwania = umowa.getCzastrwania().replace("biezacy", "");
            paragraph = new Paragraph(new Phrase(czastrwania, fontM));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(Chunk.NEWLINE);
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("zawartej w dniu "+umowa.getDatazawarcia()+" pomiędzy:", fontM));
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase(firma.getNazwa(), fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase(firma.getAdres(), fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("NIP : "+firma.getNip(), fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("reprezentowanym przez: "+firma.getReprezentant(), fontM));
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("a ", fontM));
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase(umowa.getPracownik().getNazwiskoImie(), fontM));
            document.add(paragraph);
            if (umowa.getPracownik().getPlec()!=null && umowa.getPracownik().getPlec().equals("K")) {
                paragraph = new Paragraph(new Phrase("zamieszkałą w "+umowa.getPracownik().getAdres(), fontM));
            } else {
                paragraph = new Paragraph(new Phrase("zamieszkałym w "+umowa.getPracownik().getAdres(), fontM));
            }
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("1. Strony zgodnie postanawiają, że od dnia "+datazmiany+" ulegają zmianie następujące warunki umowy:", fontM));
            document.add(paragraph);
            String zmienna = zm.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getOpispelny();
            String nettobruttostring = netto0brutto1?"brutto":"netto";
            PdfMain.dodajElementListy(document, "a) "+zmienna+": ", f.F.curr(zm.getNowakwota(), "PLN")+" "+nettobruttostring, fontM);
            paragraph = new Paragraph(new Phrase("2. Pozostałe warunki umowy pozostają bez zmian ", fontM));
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            //document.add(new Paragraph(new Phrase(umowa.getPracownik().getNazwiskoImie()+"                                                          "+umowa.getAngaz().getFirma().getReprezentant(), fontM)));
            if (umowa.getUmowakodzus().isPraca()) {
                Paragraph p = new Paragraph();
                p.add(new Phrase("...........................", fontM));
                p.setTabSettings(new TabSettings(300));
                p.add(Chunk.TABBING);
                p.add(new Phrase(umowa.getAngaz().getFirma().getReprezentant(), fontM));
                document.add(p);
                p = new Paragraph();
                p.add(new Phrase("(data i podpis pracownika)", fontS));
                p.setTabSettings(new TabSettings(300));
                p.add(Chunk.TABBING);
                p.add(new Phrase("(podpis pracodawcy lub osoby rep. pracodawcę)", fontS));
                document.add(p);
            } else {
                Paragraph p = new Paragraph();
                p.add(new Phrase("...........................", fontM));
                p.setTabSettings(new TabSettings(300));
                p.add(Chunk.TABBING);
                p.add(new Phrase(umowa.getAngaz().getFirma().getReprezentant(), fontM));
                document.add(p);
                p = new Paragraph();
                p.add(new Phrase("(data i podpis Zleceniobiorcy)", fontS));
                p.setTabSettings(new TabSettings(300));
                p.add(Chunk.TABBING);
                p.add(new Phrase("(podpis Zleceniodawcy lub osoby rep. Zlec.)", fontS));
                document.add(p);
            }
        } catch (Exception ex) {
            E.e(ex);
        }
    }

    
      
}

