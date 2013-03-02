/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfName;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPage;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.inject.Named;
import javax.naming.NamingException;

/**
 *
 * @author Osito
 */
@Named
public class pdf {
    public void preProcessPDF(Object document) throws IOException, BadElementException, DocumentException {
    Document pdf = (Document) document;
    pdf.setPageSize(PageSize.A4);
    pdf.setMargins(20, 10, 20, 10);
    Phrase ft = new Phrase("podatnik:");
    
    }
        
    public static Connection getConnection() throws NamingException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException{

          String url = "jdbc:mysql://localhost:3306/";
          String dbName = "pkpir";
          String driver = "com.mysql.jdbc.Driver";
          String userName = "brzaskun";
          String password = "pufikun";
          Class.forName(driver).newInstance();
          Connection conn = DriverManager.getConnection(url+dbName,userName,password);
    return conn;
}
    static class RotateEvent extends PdfPageEventHelper {
    
    @Override
    public void onStartPage(PdfWriter writer, Document document) {
        writer.addPageDictEntry(PdfName.A, PdfPage.LANDSCAPE);
    }
}

  
   public static void main(String[] args) throws DocumentException, FileNotFoundException, SQLException, NamingException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedEncodingException, IOException{
       
//       Document document = new Document();
//        PdfWriter writer = PdfWriter.getInstance(
//        document, new FileOutputStream("c:/filename.pdf"));
//        document.open();
//        PdfContentByte canvas = writer.getDirectContentUnder();
//        writer.setCompressionLevel(0);
//        canvas.saveState(); // q
//        canvas.beginText(); // BT
//        canvas.moveText(36, 788); // 36 788 Td
//        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
//        canvas.setFontAndSize(helvetica, 12); // /F1 12 Tf
//        canvas.showText("Hello Wośćążźrld"); // (Hello World)Tj
//        canvas.endText(); // ET
//        canvas.restoreState(); // Q
//        document.close();
    
    Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), -20, -20, 20, 10);
    PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("c:/filename.pdf"));
    writer.setPageEvent(new RotateEvent());
    pdf.open();  
    BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
    Font font = new Font(helvetica,12);
    pdf.setPageSize(PageSize.A4);
    PdfPTable table = new PdfPTable(4);
    table.setWidths(new int[]{1, 2, 3, 2});
    
    PdfPCell cell = new PdfPCell(new Phrase("Naglowek", font));
    cell.setBackgroundColor(BaseColor.LIGHT_GRAY);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    cell.setColspan(7);
    table.addCell(cell);
    table.getDefaultCell().setBackgroundColor(BaseColor.LIGHT_GRAY);
    for (int i = 0; i < 2; i++) {
    table.addCell("Location");
    table.addCell("Time");
    table.addCell("Run Length");
    table.addCell("Data");
    }
    table.getDefaultCell().setBackgroundColor(null);
    table.setHeaderRows(3);
    table.setFooterRows(1);
    
    Connection connection = getConnection();
    Statement stm = connection.createStatement();
    ResultSet rs = stm.executeQuery("SELECT * FROM dok WHERE podatnik = 'EKSTRA S.C. EWA CYBULSKA, HELENA JAKUBIAK' ORDER BY id_dok");
    while (rs.next()) {
        System.out.println(new String(rs.getBytes("opis")));
        table.addCell("id_dok");
        table.addCell("data_wyst");
        table.addCell("kontr");
        cell = new PdfPCell(new Phrase(rs.getString("opis"),font));
        table.addCell(cell);
    }
    stm.close();
    connection.close();
    pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
    pdf.add(table);
    
    Phrase hd = new Phrase("Biuro Rachunkowe Taxman - wydruk");
    Phrase ft = new Phrase("podatnik:");
    pdf.addAuthor("Biuro Rachunkowe Taxman");
    pdf.close();
   }   
}
