/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import data.Data;
import entity.Pitpoz;
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
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import msg.Msg;
import view.PitView;
import view.ZestawienieView;

/**
 *
 * @author Osito
 */
@ManagedBean
public class PdfPIT5 extends Pdf implements Serializable {
    
    @ManagedProperty(value="#{ZestawienieView}")
    private ZestawienieView zestawienieView;
  
     public void drukuj() throws DocumentException, FileNotFoundException, IOException {
        Pitpoz selected = zestawienieView.getBiezacyPit();
        drukujcd(selected);
     }
    
     public void drukujarch() throws DocumentException, FileNotFoundException, IOException {
        Pitpoz selected = PitView.getBiezacyPitS();
        drukujcd(selected);
     }
     
    public void drukujcd(Pitpoz selected) throws DocumentException, FileNotFoundException, IOException {
        System.out.println("Drukuje PK dokumentu "+selected.toString());
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/pit5" + wpisView.getPodatnikWpisu() + ".pdf")).setInitialLeading(16);
        document.addTitle("PIT5");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk PIT5");
        document.addKeywords("PIT5, PDF");
        document.addCreator("Grzegorz Grzelczyk");
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
            Paragraph miziu = new Paragraph(new Phrase("Szczecin, dnia "+Data.datapk(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()),font));
            miziu.setAlignment(Element.ALIGN_RIGHT);
            miziu.setLeading(50);
            document.add(miziu);
            document.add(new Chunk().NEWLINE);
            Paragraph miziu1 = new Paragraph(new Phrase("Zestawienie PIT-5",font));
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
            PdfPTable table = new PdfPTable(2);
            table.setWidths(new int[]{5, 5,});
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
                formatter.setMaximumFractionDigits(2);
                formatter.setMinimumFractionDigits(2);
                formatter.setGroupingUsed(true);
            try {
                table.addCell(ustawfrazebez("imię i nazwisko podatnika","center",10));
                table.addCell(ustawfrazebez(selected.getUdzialowiec(),"right",10));
                table.addCell(ustawfrazebez("przychody narastająco","center",10));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getPrzychody())),"right",10));
                table.addCell(ustawfrazebez("koszty narastająco","center",10));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getKoszty())),"right",10));
                if (selected.getRemanent()!=null) {
                    table.addCell(ustawfrazebez("różnica remanentów","center",10));
                    table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getRemanent())),"right",10));
                }
                table.addCell(ustawfrazebez("udział","center",10));
                table.addCell(ustawfrazebez(selected.getUdzial()+"%","right",10));
                table.addCell(ustawfrazebez("","center",10));
                table.addCell(ustawfrazebez("","center",10));
                table.addCell(ustawfrazebez("przychody narast./udział","center",10));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getPrzychodyudzial())),"right",10));
                table.addCell(ustawfrazebez("koszty narast./udział","center",10));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getKosztyudzial())),"right",10));
               
                table.addCell(ustawfrazebez("wynik od początku roku","center",10));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getWynik())),"right",10));
                table.addCell(ustawfrazebez("","center",10));
                table.addCell(ustawfrazebez("","center",10));
                
                table.addCell(ustawfrazebez("ZUS 51","center",10));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getZus51())),"right",10));
                table.addCell(ustawfrazebez("strata z lat ub.","center",10));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getStrata())),"right",10));
                
                table.addCell(ustawfrazebez("podstawa op.","center",10));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getPodstawa())),"right",10));
                table.addCell(ustawfrazebez("podatek od pocz.roku","center",10));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getPodatek())),"right",10));
                
                table.addCell(ustawfrazebez("ZUS 52","center",10));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getZus52   ())),"right",10));
                table.addCell(ustawfrazebez("strata z lat ub.","center",10));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getStrata())),"right",10));
                
                table.addCell(ustawfrazebez("zaliczki za pop.mce","center",10));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getNalzalodpoczrok())),"right",10));
                table.addCell(ustawfrazebez("podatek od pocz.roku","center",10));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getNaleznazal())),"right",10));

                table.addCell(ustawfrazebez("do zapłaty","center",10));
                table.addCell(ustawfrazebez(String.valueOf(formatter.format(selected.getDozaplaty())),"right",10));
                table.addCell(ustawfrazebez("termin płatności","center",10));
                table.addCell(ustawfrazebez(selected.getTerminwplaty(),"center",10));

               } catch (DocumentException | IOException e){
                
            }
            document.add(Chunk.NEWLINE);
            document.add(table);
            document.add(Chunk.NEWLINE);
            Uz uz = wpisView.getWprowadzil();
            document.add(new Paragraph(String.valueOf(uz.getImie()+" "+uz.getNazw()),fontM));
            document.add(new Paragraph("___________________________",fontM));
            document.add(new Paragraph("sporządził",fontM));
        document.close();
        Msg.msg("i", "Wydrukowano PIT5", "form:messages");
    }
    
   
    public ZestawienieView getZestawienieView() {
        return zestawienieView;
    }

    public void setZestawienieView(ZestawienieView zestawienieView) {
        this.zestawienieView = zestawienieView;
    }
  
}
