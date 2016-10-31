/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import data.Data;
import java.io.File;
import java.util.List;
import msg.Msg;
import org.primefaces.context.RequestContext;
import static pdffk.PdfMain.*;
import plik.Plik;
import viewfk.StowRozrachCzlonkView;

/**
 *
 * @author Osito
 */
public class PDFStowRozrachCzlonk {
     public static void drukuj(List<StowRozrachCzlonkView.Pozycja> wykaz) {
        String nazwa = Data.aktualnaData()+"_stowrozrachczlonk";
        File file = Plik.plik(nazwa, true);
        if (file.isFile()) {
            file.delete();
        }
        if (wykaz != null && wykaz.size() > 0) {
            Document document = inicjacjaA4Landscape();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajLinieOpisu(document, "Zestawienie naliczonych i zapłaconych kwot w roku kalendarzowym");
            dodajTabele(document, testobjects.testobjects.getStowRozrachCzlonk(wykaz),95,0);
            finalizacjaDokumentu(document);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Błąd podczas wydruku");
        }
    }
}
