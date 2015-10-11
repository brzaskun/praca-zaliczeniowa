/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import beansVAT.VATDeklaracja;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfWriter;
import dao.DeklaracjaVatSchemaDAO;
import dao.PodatnikDAO;
import dao.SchemaEwidencjaDAO;
import embeddable.EVatwpisSuma;
import embeddable.SchemaEwidencjaSuma;
import entity.DeklaracjaVatSchema;
import entity.DeklaracjaVatSchemaWierszSum;
import entity.Deklaracjevat;
import entity.SchemaEwidencja;
import entity.Uz;
import entity.Wpis;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import msg.Msg;
import org.primefaces.context.RequestContext;
import pdffk.PdfMain;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentu;
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
@Stateless
public class PdfVAT7new {
    

    public static void drukujNowaVAT7(PodatnikDAO podatnikDAO, Deklaracjevat d, DeklaracjaVatSchema pasujacaSchema, SchemaEwidencjaDAO schemaEwidencjaDAO, WpisView wpisView) {
        List<SchemaEwidencja> schemaewidencjalista = schemaEwidencjaDAO.findEwidencjeSchemy(pasujacaSchema);
        ArrayList<EVatwpisSuma> sumaewidencji = new ArrayList<>();
        sumaewidencji.addAll(d.getPodsumowanieewidencji().values());
        List<SchemaEwidencjaSuma> schematewidencjesprzedazy = VATDeklaracja.wyluskajiPrzyporzadkujSprzedaz(schemaewidencjalista, sumaewidencji);
        List<DeklaracjaVatSchemaWierszSum> schemawierszsumarycznylista = d.getSchemawierszsumarycznylista();
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"vat7";
        File file = new File(nazwa);
        if (file.isFile()) {
            file.delete();
        }
        if (schematewidencjesprzedazy != null && schematewidencjesprzedazy.size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = PdfMain.inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Deklaracja VAT firma: "+wpisView.getPodatnikWpisu(), wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.getSchemaEwidencjaSuma(schematewidencjesprzedazy),97,0);
            dodajTabele(document, testobjects.getDeklaracjaVatSchemaWierszSum(schemawierszsumarycznylista),97,0);
            uzupelnijDlaVAT7(document, d, wpisView);
            finalizacjaDokumentu(document);
            String f = "pokazwydruk('"+nazwa+"');";
            RequestContext.getCurrentInstance().execute(f);
        } else {
            Msg.msg("w", "Nie wybrano deklaracji VAT do wydruku");
        }
        System.out.println("test");
    }

    private static void uzupelnijDlaVAT7(Document document, Deklaracjevat d, WpisView wpisView) {
        if (d.getUpo() == null || d.getUpo().equals("")) {
            try {
                document.add(new Chunk());
                document.add(new Paragraph(new Phrase("Deklaracja przygotowana do wysłania", ft[1])));
            } catch (DocumentException ex) {
                Logger.getLogger(PdfVAT7new.class.getName()).log(Level.SEVERE, null, ex);
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
                Logger.getLogger(PdfVAT7new.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
