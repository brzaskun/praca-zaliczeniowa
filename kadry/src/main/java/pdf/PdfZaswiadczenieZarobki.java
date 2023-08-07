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
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import data.Data;
import entity.FirmaKadry;
import entity.Pasekwynagrodzen;
import entity.Pracownik;
import error.E;
import java.io.ByteArrayOutputStream;
import java.util.List;
import msg.Msg;
import org.primefaces.PrimeFaces;
import static pdf.PdfMain.finalizacjaDokumentuQR;
import static pdf.PdfMain.naglowekStopkaP;
import static pdf.PdfMain.otwarcieDokumentu;
import plik.Plik;

/**
 *
 * @author Osito
 */
public class PdfZaswiadczenieZarobki {
    
   

    public static ByteArrayOutputStream drukuj(FirmaKadry firma, List<Pasekwynagrodzen> paskiwynagrodzen, Pracownik pracownik, String dataod, String datado, 
            boolean zatrudnienie, boolean zarobki, String rodzajumowy, String czastrwania, String stanowisko, String etat, double bruttosrednia, double nettosrednia, boolean czyjestkomornik, String datarozpoczeciaostatnieumowy, String datazakonczeniaostatnieumowy, boolean czyjesttytulkomorniczy) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            String nazwa = pracownik.getPesel() + "_zaswiadczenie.pdf";
            if ((bruttosrednia > 0.0 && zarobki) || (zatrudnienie && zarobki==false)) {
                Document document = PdfMain.inicjacjaA4Portrait(60, 20);
                PdfWriter writer = PdfMain.inicjacjaWriteraOut(document, out);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                dodajtresc(firma, document, paskiwynagrodzen, pracownik, dataod, datado, zatrudnienie, zarobki, rodzajumowy, czastrwania, stanowisko, etat, bruttosrednia, nettosrednia, czyjestkomornik, datarozpoczeciaostatnieumowy, datazakonczeniaostatnieumowy, czyjesttytulkomorniczy);
                if (zarobki) {
                    drukujPasek(paskiwynagrodzen, document);
                }
                finalizacjaDokumentuQR(document, nazwa);
                Plik.zapiszBufferdoPlik(nazwa, out);
                String f = "pokazwydruk('" + nazwa + "');";
                PrimeFaces.current().executeScript(f);
                Msg.msg("Wydrukowano zaświadczenie");
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku. Nie można wydrukować zarobków");
            }
        } catch (Exception e) {
            E.e(e);
        }
        return out;
    }
    
    public static ByteArrayOutputStream drukujMini(FirmaKadry firma, List<Pasekwynagrodzen> paskiwynagrodzen, Pracownik pracownik, String dataod, String datado, 
            boolean zatrudnienie, boolean zarobki, String rodzajumowy, String czastrwania, String stanowisko, String etat, double bruttosrednia, double nettosrednia, boolean czyjestkomornik, String datarozpoczeciaostatnieumowy, String datazakonczeniaostatnieumowy, 
            boolean czyjesttytulkomorniczy) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            String nazwa = pracownik.getPesel() + "_zaswiadczenie.pdf";
            if ((bruttosrednia > 0.0 && zarobki) || (zatrudnienie && zarobki==false)) {
                Document document = PdfMain.inicjacjaA4Portrait(60, 20);
                PdfWriter writer = PdfMain.inicjacjaWriteraOut(document, out);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                dodajtresc(firma, document, paskiwynagrodzen, pracownik, dataod, datado, zatrudnienie, zarobki, rodzajumowy, czastrwania, stanowisko, etat, bruttosrednia, nettosrednia, czyjestkomornik, datarozpoczeciaostatnieumowy, datazakonczeniaostatnieumowy, czyjesttytulkomorniczy);
                if (zarobki) {
                    drukujPasekMini(paskiwynagrodzen, document, firma, pracownik.getNazwiskoImie(), pracownik.getPesel());
                }
                finalizacjaDokumentuQR(document, nazwa);
                Plik.zapiszBufferdoPlik(nazwa, out);
                String f = "pokazwydruk('" + nazwa + "');";
                PrimeFaces.current().executeScript(f);
                Msg.msg("Wydrukowano zaświadczenie");
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku. Nie można wydrukować zarobków");
            }
        } catch (Exception e) {
            E.e(e);
        }
        return out;
    }
    
     public static void drukujPasek(List<Pasekwynagrodzen> paski,Document document) {
        try {
            if (document != null) {
                document.setPageSize(PageSize.A4.rotate());
                document.newPage();
                PdfMain.dodajLinieOpisu(document, "Załącznik do zaświadczenia o zarobkach z dnia "+Data.aktualnaData(), Element.ALIGN_CENTER, 3);
                for (Pasekwynagrodzen p : paski) {
                    PdfListaPlac.dodajtabeleglowna(p, document);
                }
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
     
     public static void drukujPasekMini(List<Pasekwynagrodzen> paski,Document document, FirmaKadry firma, String nazwiskoiimie, String pesel) {
        try {
            if (document != null) {
                document.setPageSize(PageSize.A4.rotate());
                document.newPage();
                PdfMain.dodajLinieOpisu(document, "Załącznik do zaświadczenia o zarobkach z dnia "+Data.aktualnaData(), Element.ALIGN_CENTER, 3);
                PdfMain.dodajLinieOpisu(document, "Pracownik: "+nazwiskoiimie+", Pesel: "+pesel, Element.ALIGN_CENTER, 3);
                PdfListaPlac.dodajtabeleglownaMini(paski, document, firma);
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private static void dodajtresc(FirmaKadry firma, Document document, List<Pasekwynagrodzen> paskiwynagrodzen, Pracownik pracownik, String dataod, String datado, boolean zatrudnienie, 
            boolean zarobki, String rodzajumowy, String czastrwania, String stanowisko, String etat, double bruttosrednia, double nettosrednia, boolean czyjestkomornik, String datarozpoczeciaostatnieumowy, String datazakonczeniaostatnieumowy, boolean czyjesttytulkomorniczy) {
        try {
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 11);
            Font fontM = new Font(helvetica, 9);
            Font fontS = new Font(helvetica, 6);
            String datawystawienia = Data.aktualnaData();
            Paragraph paragraph = new Paragraph(new Phrase(firma.getMiasto()+", dnia "+datawystawienia, fontM));
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase(firma.getNazwa(), fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase(firma.getAdres(), fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("NIP : "+firma.getNip(), fontM));
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("ZAŚWIADCZENIE", font));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("o zatrudnieniu i zarobkach", font));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("Zaświadcza się, że ", fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase(pracownik.getNazwiskoImie(), fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("Pesel "+pracownik.getPesel(), fontM));
            document.add(paragraph);
            boolean czyustalozatrudnienie = Data.czyjestpoTerminData(datazakonczeniaostatnieumowy, datawystawienia);
            if (pracownik.getPlec().equals("K")) {
                paragraph = new Paragraph(new Phrase("zamieszkała w "+pracownik.getAdres(), fontM));
                document.add(paragraph);
                document.add(Chunk.NEWLINE);
                if (czyustalozatrudnienie) {
                    paragraph = new Paragraph(new Phrase("Była zatrudniona od dnia "+pracownik.getDatazatrudnienia(), fontM));
                } else {
                    paragraph = new Paragraph(new Phrase("Jest zatrudniona od dnia "+pracownik.getDatazatrudnienia(), fontM));
                }
            } else {
                paragraph = new Paragraph(new Phrase("zamieszkały w "+pracownik.getAdres(), fontM));
                document.add(paragraph);
                document.add(Chunk.NEWLINE);
                if (czyustalozatrudnienie) {
                    paragraph = new Paragraph(new Phrase("Był zatrudniony od dnia "+pracownik.getDatazatrudnienia(), fontM));
                } else {
                    paragraph = new Paragraph(new Phrase("Jest zatrudniony od dnia "+pracownik.getDatazatrudnienia(), fontM));
                }
            }
            document.add(paragraph);
            if (zatrudnienie) {
                if (datazakonczeniaostatnieumowy==null||czyustalozatrudnienie==false) {
                    PdfMain.dodajElementListy(document, "1) Rodzaj umowy: ", rodzajumowy, fontM);
                    PdfMain.dodajElementListy(document, "2) Data rozpoczęcia bieżącej umowy: ", datarozpoczeciaostatnieumowy, fontM);
                    PdfMain.dodajElementListy(document, "3) Umowa na okres: ", czastrwania, fontM);
                    PdfMain.dodajElementListy(document, "4) Stanowisko: ", stanowisko, fontM);
                    PdfMain.dodajElementListy(document, "5) Wymiar czasu pracy: ", etat, fontM);
                } else {
                    PdfMain.dodajElementListy(document, "1) Rodzaj umowy: ", rodzajumowy, fontM);
                    PdfMain.dodajElementListy(document, "2) Data rozpoczęcia bieżącej umowy: ", datarozpoczeciaostatnieumowy, fontM);
                    PdfMain.dodajElementListy(document, "3) Data zakończenia bieżącej umowy: ", datazakonczeniaostatnieumowy, fontM);
                    PdfMain.dodajElementListy(document, "4) Umowa na okres: ", czastrwania, fontM);
                    PdfMain.dodajElementListy(document, "5) Stanowisko: ", stanowisko, fontM);
                    PdfMain.dodajElementListy(document, "6) Wymiar czasu pracy: ", etat, fontM);
                }
            }
            if (zarobki) {
                PdfMain.dodajElementListy(document, "Wynagrodzenie za okres ", "od "+dataod+" do "+datado, fontM);
                PdfMain.dodajElementListy(document, "1) Średnie miesięczne wynagrodzenie brutto: ", f.F.curr(bruttosrednia), fontM);
                PdfMain.dodajElementListy(document, "2) Średnie miesięczne wynagrodzenie netto: ", f.F.curr(nettosrednia), fontM);
            }
            if (zarobki&&czyjestkomornik==false&&czyjesttytulkomorniczy==false) {
                Paragraph p = new Paragraph();
                p.add(new Phrase("Wynagrodzenie powyższe nie jest obciążone z tytułu wyroków sądowych lub innych tytułów.", fontM));
                document.add(p);
            } else {
                Paragraph p = new Paragraph();
                p.add(new Phrase("Wynagrodzenie powyższe jest obciążone z egzekucją komorniczą.", fontM));
                document.add(p);
            }
            Paragraph p = new Paragraph();
            if (czyustalozatrudnienie) {
                p.add(new Phrase("Stosunek pracy został zakończony dnia "+datazakonczeniaostatnieumowy, fontM));
            } else {
                p.add(new Phrase("Wyżej wymieniony zatrudniony nie znajduje się w okresie wypowiedzenia ani w okresie próbnym.", fontM));
            }
            document.add(p);
            p = new Paragraph();
            p.add(new Phrase("Zakład nie znajduje się w okresie likwidacji.", fontM));
            document.add(p);
            p = new Paragraph();
            p.add(new Phrase("Zaświadczenie ważne jest 30 dni od daty wystawienia.", fontM));
            document.add(p);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            p = new Paragraph();
            p.add(new Phrase("(podpis pracodawcy lub osoby reprezentującej pracodawcę)", fontS));
            document.add(p);
        } catch (Exception ex) {
            E.e(ex);
        }
    }
}
