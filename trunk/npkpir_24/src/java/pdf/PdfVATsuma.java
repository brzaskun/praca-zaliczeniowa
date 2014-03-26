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
import embeddable.EVatwpisSuma;
import embeddable.Kwartaly;
import entity.Ewidencjevat;
import entity.Podatnik;
import entity.Uz;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import view.VatView;

/**
 *
 * @author Osito
 */
@ManagedBean(name="pdfVATsuma")
public class PdfVATsuma extends Pdf implements Serializable {
    @ManagedProperty(value="#{vatView}")
    public VatView vatView;
     
    public void drukuj() throws FileNotFoundException, DocumentException, IOException  {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/vatsuma" + wpisView.getPodatnikWpisu() + ".pdf")).setInitialLeading(16);
        document.addTitle("Zestawienie sum z ewidencji VAT");
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk danych z ewidencji VAT");
        document.addKeywords("VAT, PDF");
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
            System.out.println("PdfVATsuma Today : " + today);
            Paragraph miziu = new Paragraph(new Phrase("Szczecin, dnia "+Data.datapk(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu()),font));
            miziu.setAlignment(Element.ALIGN_RIGHT);
            miziu.setLeading(50);
            document.add(miziu);
            document.add(new Chunk().NEWLINE);
            Paragraph miziu1 = new Paragraph(new Phrase("Zestawienie ewidencji VAT ",font));
            miziu1.setAlignment(Element.ALIGN_CENTER);
            document.add(miziu1);
            document.add(new Chunk().NEWLINE);
            miziu1 = new Paragraph(new Phrase("okres rozliczeniony "+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisu(),fontM));
            document.add(miziu1);
            document.add(new Chunk().NEWLINE);
            miziu1 = new Paragraph(new Phrase("Firma: "+wpisView.getPodatnikWpisu(),fontM));
            document.add(miziu1);
            Podatnik pod = podatnikDAO.find(wpisView.getPodatnikWpisu());
            miziu1 = new Paragraph(new Phrase("adres: "+pod.getMiejscowosc()+" "+pod.getUlica()+" "+pod.getNrdomu(),fontM));
            document.add(miziu1);
            miziu1 = new Paragraph(new Phrase("NIP: "+pod.getNip(),fontM));
            document.add(miziu1);
            PdfPTable tableSprzedaz = new PdfPTable(5);
            tableSprzedaz.setWidths(new int[]{1, 5, 2, 2, 2});
            PdfPTable tableZakup = new PdfPTable(5);
            tableZakup.setWidths(new int[]{1, 5, 2, 2, 2});
            NumberFormat formatter = NumberFormat.getCurrencyInstance();
                formatter.setMaximumFractionDigits(2);
                formatter.setMinimumFractionDigits(2);
                formatter.setGroupingUsed(true);
            List<EVatwpisSuma> sumaVatSprzedaz = new ArrayList<>();
            List<EVatwpisSuma> sumaVatZakup = new ArrayList<>();
            Ewidencjevat lista;
            try {
                lista = ewidencjeVatDAO.find(wpisView.getRokWpisu().toString(), wpisView.getMiesiacWpisu(), wpisView.getPodatnikWpisu());
            } catch (Exception e) {
                Integer kwartal = Integer.parseInt(Kwartaly.getMapanrkw().get(Integer.parseInt(wpisView.getMiesiacWpisu())));
                List<String> miesiacewkwartale = Kwartaly.getMapakwnr().get(kwartal);
                lista = ewidencjeVatDAO.find(wpisView.getRokWpisu().toString(), miesiacewkwartale.get(2), wpisView.getPodatnikWpisu());
            }
            for (EVatwpisSuma ew : lista.getSumaewidencji().values()) {
                String typeewidencji = ew.getEwidencja().getTypewidencji();
                switch (typeewidencji) {
                    case "s" : sumaVatSprzedaz.add(ew);
                        break;
                    case "z" : sumaVatZakup.add(ew);
                        break;
                    case "sz": sumaVatSprzedaz.add(ew);
                               sumaVatZakup.add(ew);
                        break;
                }
            }
            //tu robimy wykaz ewidencji sprzedazy
            document.add(Chunk.NEWLINE);
            miziu1 = new Paragraph(new Phrase("zestawienie ewidencji sprzedaży",fontM));
            document.add(miziu1);
            try {
                tableSprzedaz.addCell(ustawfrazebez("lp","center",10));
                tableSprzedaz.addCell(ustawfrazebez("ewidencja","center",10));
                tableSprzedaz.addCell(ustawfrazebez("netto","center",10));
                tableSprzedaz.addCell(ustawfrazebez("vat","center",10));
                tableSprzedaz.addCell(ustawfrazebez("brutto","center",10));
                tableSprzedaz.setHeaderRows(1);
                int i = 1;
                for(EVatwpisSuma p : sumaVatSprzedaz){
                    tableSprzedaz.addCell(ustawfrazebez(String.valueOf(i),"center",10));
                    tableSprzedaz.addCell(ustawfrazebez(p.getEwidencja().getNazwa(),"left",10));
                    tableSprzedaz.addCell(ustawfrazebez(String.valueOf(formatter.format(p.getNetto())),"right",10));
                    tableSprzedaz.addCell(ustawfrazebez(String.valueOf(formatter.format(p.getVat())),"right",10));
                    try {
                        tableSprzedaz.addCell(ustawfrazebez(String.valueOf(formatter.format(p.getVat().add(p.getNetto()))),"right",10));
                    } catch (Exception e){
                        tableSprzedaz.addCell(ustawfrazebez("","right",10));
                    }
                    i++;
                }
               } catch (DocumentException | IOException e){
                
            }
            document.add(Chunk.NEWLINE);
            document.add(tableSprzedaz);
            document.add(Chunk.NEWLINE);
            //tu robimy wykaz ewidencji zakupu
            miziu1 = new Paragraph(new Phrase("zestawienie ewidencji zakupu",fontM));
            document.add(miziu1);
            try {
                tableZakup.addCell(ustawfrazebez("lp","center",10));
                tableZakup.addCell(ustawfrazebez("ewidencja","center",10));
                tableZakup.addCell(ustawfrazebez("netto","center",10));
                tableZakup.addCell(ustawfrazebez("vat","center",10));
                tableZakup.addCell(ustawfrazebez("brutto","center",10));
                tableZakup.setHeaderRows(1);
                int i = 1;
                for(EVatwpisSuma p : sumaVatZakup){
                    tableZakup.addCell(ustawfrazebez(String.valueOf(i),"center",10));
                    tableZakup.addCell(ustawfrazebez(p.getEwidencja().getNazwa(),"left",10));
                    tableZakup.addCell(ustawfrazebez(String.valueOf(formatter.format(p.getNetto())),"right",10));
                    tableZakup.addCell(ustawfrazebez(String.valueOf(formatter.format(p.getVat())),"right",10));
                    try {
                        tableZakup.addCell(ustawfrazebez(String.valueOf(formatter.format(p.getVat().add(p.getNetto()))),"right",10));
                    } catch (Exception e){
                        tableZakup.addCell(ustawfrazebez("","right",10));
                    }
                    i++;
                }
               } catch (DocumentException | IOException e){
                
            }
            document.add(Chunk.NEWLINE);
            document.add(tableZakup);
            document.add(Chunk.NEWLINE);
            Uz uz = wpisView.getWprowadzil();
            document.add(new Paragraph(String.valueOf(uz.getImie()+" "+uz.getNazw()),fontM));
            document.add(new Paragraph("___________________________",fontM));
            document.add(new Paragraph("sporządził",fontM));
        document.close();
        //Msg.msg("i", "Wydrukowano sume ewidencji VAT", "form:messages");
    }

    public VatView getVatView() {
        return vatView;
    }

    public void setVatView(VatView vatView) {
        this.vatView = vatView;
    }
    
    
}
