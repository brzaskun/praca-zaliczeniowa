/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.Umorzenie;
import entity.Amodok;
import entity.Dok;
import entity.Podatnik;
import entity.Uz;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.faces.bean.ManagedBean;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
public class PdfPK extends Pdf implements Serializable {

    public void drukujPK(Dok selected) throws DocumentException, FileNotFoundException, IOException {
        System.out.println("Drukuje PK dokumentu "+selected.toString());
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_20_2/build/web/wydruki/pk" + wpisView.getPodatnikWpisu() + ".pdf")).setInitialLeading(16);
        document.open();
            //Rectangle rect = new Rectangle(0, 832, 136, 800);
            //rect.setBackgroundColor(BaseColor.RED);
            //document.add(rect);
            document.add(new Chunk("Biuro Rachunkowe Taxman"));
            document.add(Chunk.NEWLINE);
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica,12);  
            Font fontM = new Font(helvetica,10);
            Font fontS = new Font(helvetica,6);
            document.add(Chunk.NEWLINE);
            Date date = Calendar.getInstance().getTime();
            DateFormat formatt = new SimpleDateFormat("dd/MM/yyyy");
            String today = formatt.format(date);
            System.out.println("Today : " + today);
            Paragraph miziu = new Paragraph(new Phrase("Szczecin, dnia "+today,font));
            miziu.setAlignment(Element.ALIGN_RIGHT);
            miziu.setLeading(50);
            document.add(miziu);
            document.add(new Chunk().NEWLINE);
            Paragraph miziu1 = new Paragraph(new Phrase("Polecenie księgowania "+selected.getNrWlDk(),font));
            miziu1.setAlignment(Element.ALIGN_CENTER);
            document.add(miziu1);
            document.add(new Chunk().NEWLINE);
            miziu1 = new Paragraph(new Phrase("okres rozliczeniony "+selected.getPkpirM()+"/"+selected.getPkpirR(),fontM));
            document.add(miziu1);
            document.add(new Chunk().NEWLINE);
            miziu1 = new Paragraph(new Phrase("Firma: "+selected.getPodatnik(),fontM));
            document.add(miziu1);
            Podatnik pod = podatnikDAO.find(selected.getPodatnik());
            miziu1 = new Paragraph(new Phrase("adres: "+pod.getMiejscowosc()+" "+pod.getUlica()+" "+pod.getNrdomu(),fontM));
            document.add(miziu1);
            miziu1 = new Paragraph(new Phrase("NIP: "+pod.getNip(),fontM));
            document.add(miziu1);
            PdfPTable table = new PdfPTable(6);
            table.setWidths(new int[]{1, 5, 2, 2, 2, 2});
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
                formatter.setMaximumFractionDigits(2);
                formatter.setMinimumFractionDigits(2);
                formatter.setGroupingUsed(true);
            try {
                table.addCell(ustawfrazebez("lp","center",10));
                table.addCell(ustawfrazebez("opis","center",10));
                table.addCell(ustawfrazebez("netto","center",10));
                table.addCell(ustawfrazebez("vat","center",10));
                table.addCell(ustawfrazebez("brutto","center",10));
                table.addCell(ustawfrazebez("uwagi","center",10));
                table.setHeaderRows(1);
                
                table.addCell(ustawfrazebez(String.valueOf(selected.getNrWpkpir()),"center",10));
                table.addCell(ustawfrazebez(selected.getOpis(),"left",10));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getNetto())),"right",10));
                try {
                    table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getBrutto()-selected.getNetto())),"right",10));
                    table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getBrutto())),"right",10));
                } catch (Exception e){
                    table.addCell(ustawfrazebez("0.00","right",10));
                    table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getNetto())),"right",10));
                }
                table.addCell(ustawfrazebez(selected.getUwagi(),"center",10));
               } catch (DocumentException | IOException e){
                
            }
            document.add(Chunk.NEWLINE);
            document.add(table);
            document.add(Chunk.NEWLINE);
            if(selected.getOpis().equals("umorzenie za miesiac")){
                document.add(new Paragraph("Zawartość dokumentu amortyzacji",fontM));
                document.add(Chunk.NEWLINE);
                dodajamo(document,formatter);
                document.add(Chunk.NEWLINE);
            }
            Uz uz = uzDAO.find(selected.getWprowadzil());
            document.add(new Paragraph(String.valueOf(uz.getImie()+" "+uz.getNazw()),fontM));
            document.add(new Paragraph("___________________________",fontM));
            document.add(new Paragraph("sporządził",fontM));
        document.close();
        Msg.msg("i", "Wydrukowano PK", "form:messages");
    }
    
    private void dodajamo(Document document, NumberFormat formatter) throws DocumentException, IOException{
        Amodok odpis = amoDokDAO.findMR(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        List<Umorzenie> umorzenia = odpis.getUmorzenia();
        System.out.println("Drukuje " +odpis.toString());
        PdfPTable table = new PdfPTable(4);
        table.setWidths(new int[]{1, 6, 2, 2});
        table.addCell(ustawfrazebez("lp","center",10));
        table.addCell(ustawfrazebez("nazwa środka trwałego","center",10));
        table.addCell(ustawfrazebez("nr umorzenia","center",10));
        table.addCell(ustawfrazebez("kwota umorzenia","center",10));
        table.setHeaderRows(1);
        int i = 1;
        for(Umorzenie p : umorzenia){
            table.addCell(ustawfrazebez(String.valueOf(i++),"center",10));
            table.addCell(ustawfrazebez(p.getNazwaSrodka(),"center",10));
            table.addCell(ustawfrazebez(String.valueOf(p.getNrUmorzenia()),"center",10));
            table.addCell(ustawfrazebez(formatter.format(p.getKwota()),"center",10));
        }
        document.add(table);
    }
}
