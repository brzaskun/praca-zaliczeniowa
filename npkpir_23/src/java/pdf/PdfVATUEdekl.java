/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import dao.PodatnikDAO;
import entity.DeklaracjavatUE;
import entity.Podatnik;
import entity.Uz;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.primefaces.context.RequestContext;
import pdffk.PdfMain;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.ft;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import testobjects.testobjects;
import view.WpisView;

/**
 *
 * @author Osito
 */

public class PdfVATUEdekl {
    

    public static void drukujVATUE(PodatnikDAO podatnikDAO, DeklaracjavatUE d, WpisView wpisView) {
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"vatue";
        File file = new File(nazwa);
        if (file.isFile()) {
            file.delete();
        }
        Uz uz = wpisView.getWprowadzil();
        Document document = PdfMain.inicjacjaA4Portrait();
        try {
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            Podatnik pod = podatnikDAO.find(d.getPodatnik());
            dodajOpisWstepny(document, "Deklaracja VAT-UE firma: ", pod, d.getMiesiac(), d.getRok());
            dodajTabele(document, testobjects.getPozycje(d.getPozycje()),97,1);
            uzupelnijDlaVAT7(document, d, wpisView);
            finalizacjaDokumentuQR(document,nazwa);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
            System.out.println("test");
        } catch(Exception e) {
            document.close();
        }
    }

    private static void uzupelnijDlaVAT7(Document document, DeklaracjavatUE d, WpisView wpisView) {
        if (d.getUpo() == null || d.getUpo().equals("")) {
            try {
                document.add(new Chunk());
                document.add(new Paragraph(new Phrase("Deklaracja przygotowana do wysłania", ft[1])));
                document.add(new Paragraph(new Phrase("Urząd Skarbowy: "+d.getKodurzedu(), ft[1])));
                document.add(new Paragraph(new Phrase("Sporządzający: "+d.getSporzadzil(), ft[1])));
            } catch (DocumentException ex) {
                Logger.getLogger(PdfVATUEdekl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                document.add(new Chunk());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String data = sdf.format(d.getDatazlozenia());
                document.add(new Paragraph(new Phrase("Deklaracja wysłana dnia: "+data, ft[1])));
                document.add(new Paragraph(new Phrase("Sporządzający: "+d.getSporzadzil(), ft[1])));
                document.add(new Paragraph(new Phrase("Status: "+d.getStatus(), ft[1])));
                document.add(new Paragraph(new Phrase("Urząd Skarbowy: "+d.getKodurzedu(), ft[1])));
                document.add(new Paragraph(new Phrase("Identyfikator: "+d.getIdentyfikator(), ft[1])));
                if (d.getDataupo() != null) {
                    data = sdf.format(d.getDataupo());
                    document.add(new Paragraph(new Phrase("Data UPO: "+data, ft[1])));
                }
                int odo = d.getUpo().indexOf("<DataWplyniecia>");
                int doo = d.getUpo().indexOf("</Potwierdzenie>");
                document.add(new Paragraph(new Phrase("UPO: "+d.getUpo().substring(odo, doo), ft[1])));
            } catch (DocumentException ex) {
                Logger.getLogger(PdfVATUEdekl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
