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
import dao.PodatnikDAO;
import dao.SchemaEwidencjaDAO;
import embeddable.EVatwpisSuma;
import embeddable.SchemaEwidencjaSuma;
import entity.DeklaracjaVatSchema;
import entity.DeklaracjaVatSchemaWierszSum;
import entity.Deklaracjevat;
import entity.Podatnik;
import entity.SchemaEwidencja;
import entity.Uz;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.primefaces.PrimeFaces;
import pdffk.PdfMain;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.ft;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
import testobjects.testobjects;
import view.WpisView;

/**
 *
 * @author Osito
 */

public class PdfVAT7new {
    

    public static ByteArrayOutputStream drukujNowaVAT7(PodatnikDAO podatnikDAO, Deklaracjevat d, DeklaracjaVatSchema pasujacaSchema, SchemaEwidencjaDAO schemaEwidencjaDAO, WpisView wpisView) {
        List<SchemaEwidencja> schemaewidencjalista = schemaEwidencjaDAO.findEwidencjeSchemy(pasujacaSchema);
        List<SchemaEwidencjaSuma> schematewidencjesprzedazy = null;
        if (d.getPodsumowanieewidencji() != null) {
            List<EVatwpisSuma> sumaewidencji = Collections.synchronizedList(new ArrayList<>());
            sumaewidencji.addAll(d.getPodsumowanieewidencji().values());
            schematewidencjesprzedazy = VATDeklaracja.wyluskajiPrzyporzadkujSprzedaz(schemaewidencjalista, sumaewidencji);
        }
        List<DeklaracjaVatSchemaWierszSum> schemawierszsumarycznylista = d.getSchemawierszsumarycznylista();
        String nazwa = wpisView.getPodatnikObiekt().getNip()+"vat7";
        File file = new File(nazwa);
        if (file.isFile()) {
            file.delete();
        }
        Uz uz = wpisView.getUzer();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = PdfMain.inicjacjaA4Landscape(40,20,40,40);
        PdfWriter writer = PdfMain.inicjacjaWriteraOut(document, out);
        naglowekStopkaP(writer);
        otwarcieDokumentu(document, nazwa);
        Podatnik pod = podatnikDAO.findByNazwaPelna(d.getPodatnik());
        if (d.getWzorschemy().contains("M")) {
            dodajOpisWstepny(document, "Deklaracja VAT firma: ", pod, d.getMiesiac(), d.getRok());
        } else {
            dodajOpisWstepny(document, "Deklaracja VAT firma: ", pod, d.getNrkwartalu(), d.getRok());
        }
        if (schematewidencjesprzedazy != null) {
            dodajTabele(document, testobjects.getSchemaEwidencjaSuma(schematewidencjesprzedazy),97,0);
        }
        dodajTabele(document, testobjects.getDeklaracjaVatSchemaWierszSum(schemawierszsumarycznylista),97,0);
        uzupelnijDlaVAT7(document, d, wpisView);
        finalizacjaDokumentuQR(document,nazwa);
        Plik.zapiszBufferdoPlik(nazwa+".pdf", out);
        String f = "pokazwydruk('"+nazwa+"');";
        PrimeFaces.current().executeScript(f);
        return out;
    }

    private static void uzupelnijDlaVAT7(Document document, Deklaracjevat d, WpisView wpisView) {
        if (d.getUpo() == null || d.getUpo().equals("")) {
            try {
                document.add(new Chunk());
                document.add(new Paragraph(new Phrase("Deklaracja przygotowana do wysłania", ft[1])));
                if (d.getSelected().getCelzlozenia().equals("1")) {
                    document.add(new Paragraph(new Phrase("Deklaracja pierwotna", ft[1])));
                } else {
                    document.add(new Paragraph(new Phrase("Deklaracja korygująca", ft[1])));
                }
                document.add(new Paragraph(new Phrase("Wersja deklaracji: "+d.getWzorschemy(), ft[1])));
                document.add(new Paragraph(new Phrase("Urząd Skarbowy: "+d.getSelected().getNazwaurzedu(), ft[1])));
                document.add(new Paragraph(new Phrase("Kwota autoryzacyjna: "+d.getSelected().getKwotaautoryzacja(), ft[1])));
            } catch (DocumentException ex) {
                // Logger.getLogger(PdfVAT7new.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            try {
                document.add(new Chunk());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                String data = sdf.format(d.getDatazlozenia());
                document.add(new Paragraph(new Phrase("Deklaracja wysłana dnia: "+data, ft[1])));
                if (d.getSelected().getCelzlozenia().equals("1")) {
                    document.add(new Paragraph(new Phrase("Deklaracja pierwotna", ft[1])));
                } else {
                    document.add(new Paragraph(new Phrase("Deklaracja korygująca", ft[1])));
                }
                document.add(new Paragraph(new Phrase("Wersja deklaracji: "+d.getWzorschemy(), ft[1])));
                document.add(new Paragraph(new Phrase("Sporządzający: "+d.getSporzadzil(), ft[1])));
                document.add(new Paragraph(new Phrase("Status: "+d.getStatus(), ft[1])));
                document.add(new Paragraph(new Phrase("Urząd Skarbowy: "+d.getSelected().getNazwaurzedu(), ft[1])));
                document.add(new Paragraph(new Phrase("Kwota autoryzacyjna: "+d.getSelected().getKwotaautoryzacja(), ft[1])));
                document.add(new Paragraph(new Phrase("Identyfikator: "+d.getIdentyfikator(), ft[1])));
                if (d.getDataupo() != null) {
                    data = sdf.format(d.getDataupo());
                    document.add(new Paragraph(new Phrase("Data UPO: "+data, ft[1])));
                }
                int odo = d.getUpo().indexOf("<DataWplyniecia>");
                int doo = d.getUpo().indexOf("</Potwierdzenie>");
                document.add(new Paragraph(new Phrase("UPO: "+d.getUpo().substring(odo, doo), ft[1])));
            } catch (DocumentException ex) {
                // Logger.getLogger(PdfVAT7new.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
