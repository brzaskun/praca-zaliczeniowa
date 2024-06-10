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
import data.Data;
import entity.FirmaKadry;
import entity.Umowa;
import error.E;

/**
 *
 * @author Osito
 */
public class PdfSwiadectwoZalacznik {
      public static void dodajtresc(Umowa umowa, Document document) {
        try {
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 11);
            Font fontM = new Font(helvetica, 9);
            Font fontS = new Font(helvetica, 6);
            FirmaKadry firma = umowa.getAngaz().getFirma();
            document.add(Chunk.NEWLINE);
            Paragraph paragraph = new Paragraph(new Phrase("Dane pracodawcy:", fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase(firma.getNazwa(), fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase(firma.getAdres(), fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("NIP : "+firma.getNip(), fontM));
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("Dane pracownika:", fontM));
            document.add(paragraph);
           paragraph = new Paragraph(new Phrase(umowa.getPracownik().getNazwiskoImie(), fontM));
            document.add(paragraph);
            if (umowa.getPracownik().getPlec().equals("K")) {
                paragraph = new Paragraph(new Phrase("zamieszkałą w "+umowa.getPracownik().getAdres(), fontM));
            } else {
                paragraph = new Paragraph(new Phrase("zamieszkałym w "+umowa.getPracownik().getAdres(), fontM));
            }
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            PdfMain.dodajLinieOpisu(document, "INFORMACJA", Element.ALIGN_CENTER, 2);
            String text = "W związku z rozwiązaniem/wygaśnięciem umowy o pracę zawartej w dniu   "+umowa.getDatazawarcia()+", na podstawie art. 946 Ustawy z dnia 26 czerwca 1974r. Kodeks pracy (Dz.U. 2016r. poz. 1666, 2138 i 2255, z 2017r. poz. 60 i 962, z 2018r., poz.4) informuję, iż:";
            PdfMain.dodajLinieOpisuBezOdstepu(document, text, Element.ALIGN_JUSTIFIED, 2);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            text = "1. Na podstawie art. 94 pkt 9b/art. 945 § 2 okres przechowywania Pani/Pana dokumentacji pracowniczej wynosi 10 lat, licząc od końca roku kalendarzowego, w którym stosunek pracy uległ rozwiązaniu/wygasł.";
            PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, text, Element.ALIGN_LEFT, 2);
            document.add(Chunk.NEWLINE);
            String data = umowa.getRozwiazanieumowy().getDatauplywuokresuwyp()!=null?umowa.getRozwiazanieumowy().getDatauplywuokresuwyp():umowa.getDatado();
            Integer rokI = Data.getRokI(data);
            rokI = rokI+11;
            text = "2.	Ma Pani/Pan możliwość odbioru dokumentacji pracowniczej do końca miesiąca kalendarzowego następującego po upływie okresu przechowywania dokumentacji pracowniczej, o którym mowa w art. 94 pkt 9b/art. 945 § 2 tj. 31.01."+rokI+"r.";
            PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, text, Element.ALIGN_LEFT, 2);
            document.add(Chunk.NEWLINE);
            text = "3.	Dokumentacja pracownicza w przypadku jej nieodebrania w okresie, o którym mowa w pkt. 2 podlega zniszczeniu.";
            PdfMain.dodajLinieOpisuBezOdstepuWciecie(document, text, Element.ALIGN_LEFT, 2);
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
            p.setTabSettings(new TabSettings(300));
            p.add(Chunk.TABBING);
            p.add(new Phrase("(podpis pracodawcy", fontS));
            p.setTabSettings(new TabSettings(350));
            p.add(Chunk.TABBING);
            p.add(new Phrase("lub osoby reprezentującej pracodawcę)", fontS));
            document.add(p);
        } catch (Exception ex) {
            E.e(ex);
        }
    }
}
