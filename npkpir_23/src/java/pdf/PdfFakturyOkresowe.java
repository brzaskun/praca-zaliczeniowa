/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Fakturywystokresowe;
import entity.Uz;
import entityfk.Dokfk;
import java.util.List;
import org.primefaces.context.RequestContext;
import static pdffk.PdfMain.dodajDate;
import static pdffk.PdfMain.dodajLinieOpisu;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.dodajpodpis;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.infooFirmie;
import static pdffk.PdfMain.informacjaoZaksiegowaniu;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import static pdffk.PdfMain.saldokoncowe;
import static pdffk.PdfMain.saldopoczatkowe;

/**
 *
 * @author Osito
 */
public class PdfFakturyOkresowe {
    public static void drukuj(List<Fakturywystokresowe> fakturyokresowe, String nip) {
        String nazwa = nip+"fakturyokresowe";
        Document document = inicjacjaA4Portrait();
        PdfWriter writer = inicjacjaWritera(document, nazwa);
        naglowekStopkaP(writer);
        otwarcieDokumentu(document, nazwa);
        dodajLinieOpisu(document, "Zestawienie faktur okresowych");
        dodajTabele(document, testobjects.testobjects.getFakturyOkresowe(fakturyokresowe),100,0);
        finalizacjaDokumentuQR(document,nazwa);
        String f = "pokazwydruk('"+nazwa+"');";
        RequestContext.getCurrentInstance().execute(f);
    }
}
