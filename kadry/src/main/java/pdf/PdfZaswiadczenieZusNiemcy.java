/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
import com.itextpdf.text.pdf.PdfWriter;
import data.Data;
import entity.FirmaKadry;
import entity.Pasekwynagrodzen;
import error.E;
import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import msg.Msg;
import org.primefaces.PrimeFaces;
import static pdf.PdfMain.finalizacjaDokumentuQR;
import static pdf.PdfMain.ft;
import static pdf.PdfMain.naglowekStopkaP;
import static pdf.PdfMain.otwarcieDokumentu;
import plik.Plik;

/**
 *
 * @author Osito
 */
public class PdfZaswiadczenieZusNiemcy {
      public static ByteArrayOutputStream drukuj(FirmaKadry firma, List<Pasekwynagrodzen> paskiwynagrodzen, String rok, String podpis) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            String nazwa = firma.getNip() + "_zusniemcy.pdf";
            if (paskiwynagrodzen.isEmpty()==false) {
                Document document = PdfMain.inicjacjaA4Portrait(60, 20);
                PdfWriter writer = PdfMain.inicjacjaWriteraOut(document, out);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                for (Pasekwynagrodzen pasek:paskiwynagrodzen) {
                    dodajtresc(document, pasek, firma, rok, podpis);
                    document.add(Chunk.NEXTPAGE);
                }
                finalizacjaDokumentuQR(document, nazwa);
                Plik.zapiszBufferdoPlik(nazwa, out);
                String f = "pokazwydruk('" + nazwa + "');";
                PrimeFaces.current().executeScript(f);
                Msg.msg("Wydrukowano zestawienie składek");
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku. Nie można wydrukować zestawienia składek");
            }
        } catch (Exception e) {
            E.e(e);
        }
        return out;
    }

    private static void dodajtresc(Document document, Pasekwynagrodzen pasek, FirmaKadry firma, String rok, String podpis) {
          try {
              BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
              Font font = new Font(helvetica, 11);
              Font fontM = new Font(helvetica, 9);
              Font fontS = new Font(helvetica, 6);
              Paragraph paragraph = new Paragraph(new Phrase(firma.getMiasto()+", den "+Data.aktualnaData(), font));
              paragraph.setAlignment(Element.ALIGN_RIGHT);
              document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase(firma.getNazwa(), font));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase(firma.getAdres(), font));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("Steuernummer : "+firma.getSteuernummer(), font));
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("BESCHEINIGUNG DES ARBEITGEBERS", font));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("über die in Polen bezahlten Sozialversicherungsbeiträge im Jahr "+rok, font));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("wären der Beschäftigung in Deutschland", font));
            paragraph.setAlignment(Element.ALIGN_CENTER);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("Mitarbeiter: "+pasek.getNazwiskoImie(), font));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("Geburtsdatum: "+pasek.getKalendarzmiesiac().getDataUrodzenia(), font));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("wohn.: "+pasek.getKalendarzmiesiac().getAngaz().getPracownik().getAdres(), font));
            paragraph.setAlignment(Element.ALIGN_LEFT);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setTabSettings(new TabSettings(180));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase("Bezahlte Beiträge", ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase("Bezahlte Beiträge", ft[2]));
            document.add(paragraph);
            paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setTabSettings(new TabSettings(180));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase("Arbeithenmeranteil", ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase("Arbeitgeberanteil", ft[2]));
            document.add(paragraph);
            paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setFirstLineIndent(110f);
            paragraph.setTabSettings(new TabSettings(80));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase("PLN", ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase("EUR", ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase("PLN", ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase("EUR", ft[2]));
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            //****************
            paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setFirstLineIndent(10f);
            paragraph.setTabSettings(new TabSettings(80));
            paragraph.add(new Phrase("Rentenversicherung", ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getPracemerytalneOddelegowanie()), ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getPracemerytalneOddelegowanieEuro(),"EUR"), ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getEmerytalneOddelegowanie()), ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getEmerytalneOddelegowanieEuro(),"EUR"), ft[2]));
            document.add(paragraph);
            //****************
            //****************
            paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setFirstLineIndent(10f);
            paragraph.setTabSettings(new TabSettings(80));
            paragraph.add(new Phrase("Erwerbsminderungsrente", ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getPracrentoweOddelegowanie()), ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getPracrentoweOddelegowanieEuro(),"EUR"), ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getRentoweOddelegowanie()), ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getRentoweOddelegowanieEuro(),"EUR"), ft[2]));
            document.add(paragraph);
            //****************
            document.add(Chunk.NEWLINE);
            //****************
            paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setFirstLineIndent(10f);
            paragraph.setTabSettings(new TabSettings(80));
            paragraph.add(new Phrase("Insgesamt..........", ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getPracrentoweOddelegowanie()+pasek.getPracemerytalneOddelegowanie()), ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getPracrentoweOddelegowanieEuro()+pasek.getPracemerytalneOddelegowanieEuro(),"EUR"), ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getRentoweOddelegowanie()+pasek.getEmerytalneOddelegowanie()), ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getRentoweOddelegowanieEuro()+pasek.getEmerytalneOddelegowanieEuro(),"EUR"), ft[2]));
            document.add(paragraph);
            //****************
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            //****************
            paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setFirstLineIndent(10f);
            paragraph.setTabSettings(new TabSettings(80));
            paragraph.add(new Phrase("Krankenversicherung", ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getPraczdrowotneOddelegowanie()), ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getPraczdrowotneOddelegowanieEuro(),"EUR"), ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase("-", ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase("-", ft[2]));
            document.add(paragraph);
            //****************
            //****************
            paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setFirstLineIndent(10f);
            paragraph.setTabSettings(new TabSettings(80));
            paragraph.add(new Phrase("Krankengeldversicherung", ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getPracchoroboweOddelegowanie()), ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getPracchoroboweOddelegowanieEuro(),"EUR"), ft[2]));
             paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase("-", ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase("-", ft[2]));
            document.add(paragraph);
            //****************
            document.add(Chunk.NEWLINE);
            //****************
            paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setFirstLineIndent(10f);
            paragraph.setTabSettings(new TabSettings(80));
            paragraph.add(new Phrase("Insgesamt..........", ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getPraczdrowotneOddelegowanie()+pasek.getPracchoroboweOddelegowanie()), ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase(f.F.curr(pasek.getPraczdrowotneOddelegowanieEuro()+pasek.getPracchoroboweOddelegowanieEuro(),"EUR"), ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase("-", ft[2]));
            paragraph.add(Chunk.TABBING);
            paragraph.add(new Phrase("-", ft[2]));
            document.add(paragraph);
            //****************
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            //****************
            paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setFirstLineIndent(10f);
            paragraph.add(new Phrase("im Auftrag", ft[2]));
            document.add(paragraph);
            paragraph = new Paragraph();
            paragraph.setAlignment(Element.ALIGN_LEFT);
            paragraph.setFirstLineIndent(10f);
            paragraph.add(new Phrase(podpis, ft[2]));
            document.add(paragraph);
          } catch (Exception ex) {
              Logger.getLogger(PdfZaswiadczenieZusNiemcy.class.getName()).log(Level.SEVERE, null, ex);
          }
    }
}
