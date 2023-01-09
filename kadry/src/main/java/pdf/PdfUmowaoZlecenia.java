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

/**
 *
 * @author Osito
 */
public class PdfUmowaoZlecenia {
    public static void drukuj(Umowa umowa) {
        try {
            String nazwa = umowa.getAngaz().getPracownik().getPesel()+"umowa.pdf";
            if (umowa != null) {
                Document document = PdfMain.inicjacjaA4Portrait(80,60);
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                dodajtresc(umowa, document);
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
    
    private static void dodajtresc(Umowa umowa, Document document) {
        try {
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 11);
            Font fontM = new Font(helvetica, 9);
            Font fontS = new Font(helvetica, 6);
            FirmaKadry firma = umowa.getAngaz().getFirma();
            Paragraph paragraph = new Paragraph(new Phrase(firma.getMiasto()+", dnia "+umowa.getDatazawarcia(), fontM));
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph(new Phrase("..............................", fontM)));
            document.add(new Paragraph(new Phrase("pieczątka firmy", fontS)));
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("UMOWA ZLECENIA", font));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
//            paragraph = new Paragraph(new Phrase(umowa.getCzastrwania().replace("biezacy", ""), fontM));
//            paragraph.setAlignment(Element.ALIGN_CENTER);
//            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("zawarta w dniu "+umowa.getDatazawarcia()+" pomiędzy Zleceniodawcą:", fontM));
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
            paragraph = new Paragraph(new Phrase("a Zleceniobiorcą", fontM));
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase(umowa.getPracownik().getNazwiskoImie(), fontM));
            document.add(paragraph);
            if (umowa.getPracownik().getPlec().equals("K")) {
                paragraph = new Paragraph(new Phrase("zamieszkałą w "+umowa.getPracownik().getAdres(), fontM));
            } else {
                paragraph = new Paragraph(new Phrase("zamieszkałym w "+umowa.getPracownik().getAdres(), fontM));
            }
            document.add(paragraph);
            PdfMain.dodajLinieOpisuBezOdstepu(document, "§1", Element.ALIGN_CENTER, 1);
            PdfMain.dodajLinieOpisu(document, "Zleceniodawca zleca Zleceniobiorcy wykonanie następującej pracy: "+umowa.getStanowisko(), Element.ALIGN_LEFT, 1);
            PdfMain.dodajLinieOpisuBezOdstepu(document, "§2", Element.ALIGN_CENTER, 1);
//            if (umowa.getDatado()==null) {
//                paragraph = new Paragraph(new Phrase("na "+umowa.getCzastrwania().replace("biezacy", ""), fontM));
//            } else {
                paragraph = new Paragraph(new Phrase("Zleceniobiorca wykonywać będzie zlecenie w okresie "+umowa.getCzastrwania()+" od "+umowa.getDataod()+" do "+umowa.getDatado(), fontM));
//            }
            document.add(paragraph);
            PdfMain.dodajLinieOpisuBezOdstepu(document, "§3", Element.ALIGN_CENTER, 1);
            String par3 = "Potwierdzeniem czasu wykonywania czynności określonych w § 1 umowy będzie ewidencja godzin wykonywania\n" +
"obejmująca okres miesiąca kalendarzowego, zwana dalej ewidencją, której wzór stanowi załącznik nr 1 i która " +
"wykazuje ilość godzin i minut przepracowanych każdego dnia przez Zleceniobiorcę. Ewidencja jest dostarczana " +
"Zleceniodawcy przez Zleceniobiorcę najpóźniej do godziny 16 ostatniego dnia roboczego miesiąca, którego dotyczy. " +
"Dane zawarte w ewidencji są akceptowane przez Zleceniodawcę i podlegają kontroli oraz wyjaśnieniu w przypadku " +
"wątpliwości.";
            PdfMain.dodajLinieOpisu(document, par3, Element.ALIGN_JUSTIFIED, 1);
            PdfMain.dodajLinieOpisuBezOdstepu(document, "§4", Element.ALIGN_CENTER, 1);
            String par4 = "Z tytułu wykonania zleconej pracy Zleceniobiorca otrzyma wynagrodzenie w wysokości: "+umowa.pobierzwynagrodzenieString();
            PdfMain.dodajLinieOpisu(document, par4, Element.ALIGN_JUSTIFIED, 1);

            PdfMain.dodajLinieOpisuBezOdstepu(document, "§5", Element.ALIGN_CENTER, 1);
            String par5 = "Wypłata wynagrodzenia nastąpi po wystawieniu rachunku przez Zleceniobiorcę i stwierdzeniu przez Zleceniodawcę terminowego i prawidłowego wykonania zleconej pracy będącej przedmiotem niniejszej umowy.";
            PdfMain.dodajLinieOpisu(document, par5, Element.ALIGN_JUSTIFIED, 1);
            
            PdfMain.dodajLinieOpisuBezOdstepu(document, "§6", Element.ALIGN_CENTER, 1);
            String par6 = "Dane osobowe Zleceniobiorcy podlegają ochronie zgodnie z obowiązującymi przepisami. Zleceniobiorca wyraża zgodę na przetwarzanie danych osobowych w celach ewidencyjnych, podatkowych i ubezpieczeniowych przez Zleceniodawcę, zgodnie z obowiązującymi przepisami o systemie ubezpieczeń społecznych oraz o podatku dochodowym od osób fizycznych.";
            PdfMain.dodajLinieOpisu(document, par5, Element.ALIGN_JUSTIFIED, 1);
            
            PdfMain.dodajLinieOpisuBezOdstepu(document, "§7", Element.ALIGN_CENTER, 1);
            String par7 = "";
            PdfMain.dodajLinieOpisu(document, par7, Element.ALIGN_JUSTIFIED, 1);
            
            PdfMain.dodajElementListy(document, "4) Wynagrodzenie: ", umowa.pobierzwynagrodzenieString(), fontM);
            PdfMain.dodajElementListy(document, "5) Inne warunki zatrudnienia: ", umowa.getInnewarunkizatrudnienia(), fontM);
            PdfMain.dodajElementListy(document, "6) termin rozpoczęcia pracy: ", umowa.getTerminrozpoczeciapracy(), fontM);
            PdfMain.dodajElementListy(document, "7) dopuszczalna liczba godzin, których przekroczenie uprawnia pracownika do dodatku z art. 151(1)§1 KP", umowa.getDopuszczalnailoscgodzin(), fontM);
            paragraph = new Paragraph(new Phrase("2. Przyczyny uzasadniające zawarcie umowy o pracę na czas nieokreślony w celu, o którym mowa w art. 25(1)§4pkt.1-3,4: ", fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase(umowa.getPrzyczynaumowaokreslony(), fontM));
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            //document.add(new Paragraph(new Phrase(umowa.getPracownik().getNazwiskoImie()+"                                                          "+umowa.getAngaz().getFirma().getReprezentant(), fontM)));
            Paragraph p = new Paragraph();
            p.add(new Phrase(umowa.getPracownik().getNazwiskoImie(), fontM));
            p.setTabSettings(new TabSettings(350));
            p.add(Chunk.TABBING);
            p.add(new Phrase(umowa.getAngaz().getFirma().getReprezentant(), fontM));
            document.add(p);
            p = new Paragraph();
            p.add(new Phrase("(data i podpis pracownika)", fontS));
            p.setTabSettings(new TabSettings(350));
            p.add(Chunk.TABBING);
            p.add(new Phrase("(podpis pracodawcy lub osoby reprezentującej pracodawcę)", fontS));
            document.add(p);
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

