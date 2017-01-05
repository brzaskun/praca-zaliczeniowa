/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Uz;
import java.util.List;
import static pdffk.PdfMain.*;


/**
 *
 * @author Osito
 */
public class PdfBilansGen {
    
    public static void drukujbilansgen(String nazwa, List<String> komunikatyerror,List<String> komunikatyerror2,List<String> komunikatyerror3, Uz uz) {
        Document document = inicjacjaA4Portrait();
        PdfWriter writer = inicjacjaWritera(document, nazwa);
        naglowekStopkaP(writer);
        otwarcieDokumentu(document, nazwa);
        dodajDate(document, data.Data.aktualnaData());
        dodajLinieOpisu(document, "Zestawienie błędów przy generowaniu BO");
        dodajkomunikat(document,komunikatyerror);
        dodajkomunikat(document,komunikatyerror2);
        dodajkomunikat(document,komunikatyerror3);
        dodajpodpis(document, uz.getImie(), uz.getNazw());
        finalizacjaDokumentuQR(document,nazwa);
    }

    private static void dodajkomunikat(Document document, List<String> komunikatyerror) {
        for (String p: komunikatyerror) {
            dodajLinieOpisuBezOdstepu(document, p);
        }
    }
}
