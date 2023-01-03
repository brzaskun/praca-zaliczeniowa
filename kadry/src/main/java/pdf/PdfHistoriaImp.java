/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfWriter;
import error.E;
import java.io.ByteArrayOutputStream;
import java.util.List;
import msg.Msg;
import org.primefaces.PrimeFaces;
import static pdf.PdfMain.finalizacjaDokumentuQR;
import static pdf.PdfMain.inicjacjaWritera;
import static pdf.PdfMain.naglowekStopkaP;
import static pdf.PdfMain.otwarcieDokumentu;

/**
 *
 * @author Osito
 */
public class PdfHistoriaImp {
     public static void drukuj(List<String> log, String firma, String nip) {
        try {
            String nazwa = nip+"RapImport.pdf";
            if (log != null) {
                Document document = PdfMain.inicjacjaA4Portrait();
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                PdfMain.dodajLinieOpisu(document, "Raport z importu", Element.ALIGN_CENTER, 2);
                PdfMain.dodajLinieOpisu(document, firma, Element.ALIGN_CENTER, 2);
                for (String p : log) {
                    if (p.contains("BŁĄD")) {
                        PdfMain.dodajLinieOpisuBezOdstepuKolor(document, p, BaseColor.RED);
                    } else {
                        PdfMain.dodajLinieOpisuBezOdstepuKolor(document, p, BaseColor.BLACK);
                    }
                }
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
     
     public static ByteArrayOutputStream drukujMail(List<String> log, String firma, String nip) {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            String nazwa = nip+"RapImport.pdf";
            if (log != null) {
                Document document = PdfMain.inicjacjaA4Portrait();
                PdfWriter writer = PdfWriter.getInstance(document, out);
                writer.setInitialLeading(16);
                writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                PdfMain.dodajLinieOpisu(document, "Raport z importu", Element.ALIGN_CENTER, 2);
                PdfMain.dodajLinieOpisu(document, firma, Element.ALIGN_CENTER, 2);
                for (String p : log) {
                    if (p.contains("BŁĄD")) {
                        PdfMain.dodajLinieOpisuBezOdstepuKolor(document, p, BaseColor.RED);
                    } else {
                        PdfMain.dodajLinieOpisuBezOdstepuKolor(document, p, BaseColor.BLACK);
                    }
                }
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
}
