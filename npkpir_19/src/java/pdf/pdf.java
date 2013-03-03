/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.NumberFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.naming.NamingException;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.jsp.jstl.core.Config;
import msg.Msg;

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
   

    private static PdfPCell ustawfraze(String fraza, int colsp, int rowsp) throws DocumentException, IOException{
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica,8);
        PdfPCell cell = new PdfPCell(new Phrase(fraza,font));
        if(rowsp>0){
            cell.setRowspan(rowsp);
        } else {
            cell.setColspan(colsp);
        }
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
    
    private static PdfPCell ustawfrazebez(String fraza) throws DocumentException, IOException{
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica,8);
        PdfPCell cell = new PdfPCell(new Phrase(fraza,font));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
        return cell;
    }
  
    public String drukujksiege() throws DocumentException, FileNotFoundException{
    Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), -20, -20, 20, 10);
    PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("c:/filename.pdf"));
    pdf.open();  
    BaseFont helvetica = null;
        try {
            helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (IOException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    Font font = new Font(helvetica,8);
    pdf.setPageSize(PageSize.A4);
    PdfPTable table = new PdfPTable(16);
    table.setWidths(new int[]{1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
    PdfPCell cell = new PdfPCell();
    try {
    table.addCell(ustawfraze("lp", 0, 2));
    table.addCell(ustawfraze("Data zdarzenia gosp.", 0, 2));
    table.addCell(ustawfraze("Nr dowodu księgowego", 0, 2));
    table.addCell(ustawfraze("Kontrahent", 2, 0));
    table.addCell(ustawfraze("Opis zdarzenia gospodarcz", 0, 2));
    table.addCell(ustawfraze("Przychody", 3, 0));
    table.addCell(ustawfraze("Zakup towarów handlowych i materiałów wg cen zakupu", 0, 2));
    table.addCell(ustawfraze("Koszty uboczne zakupu", 0, 2));
    table.addCell(ustawfraze("Wydatki(koszty)", 4, 0));
    table.addCell(ustawfraze("Uwagi", 0, 2));
    
    table.addCell(ustawfrazebez("imię i nazwisko (firma)"));
    table.addCell(ustawfrazebez("adres"));
    table.addCell(ustawfrazebez("wartość sprzedanych towarów i usług"));
    table.addCell(ustawfrazebez("pozostałe przychody"));
    table.addCell(ustawfrazebez("razem przychód (7+8)"));
    table.addCell(ustawfrazebez("wynagrodzenia w gotówce i w naturze"));
    table.addCell(ustawfrazebez("pozostałe wydatki"));
    table.addCell(ustawfrazebez("razem wydatki (12+13)"));
    table.addCell(ustawfrazebez("inwestycje"));
    
    table.addCell(ustawfrazebez("1"));
    table.addCell(ustawfrazebez("2"));
    table.addCell(ustawfrazebez("3"));
    table.addCell(ustawfrazebez("4"));
    table.addCell(ustawfrazebez("5"));
    table.addCell(ustawfrazebez("6"));
    table.addCell(ustawfrazebez("7"));
    table.addCell(ustawfrazebez("8"));
    table.addCell(ustawfrazebez("9"));
    table.addCell(ustawfrazebez("10"));
    table.addCell(ustawfrazebez("11"));
    table.addCell(ustawfrazebez("12"));
    table.addCell(ustawfrazebez("13"));
    table.addCell(ustawfrazebez("14"));
    table.addCell(ustawfrazebez("15"));
    table.addCell(ustawfrazebez("16"));
    
    table.addCell(ustawfrazebez("1"));
    table.addCell(ustawfrazebez("2"));
    table.addCell(ustawfrazebez("3"));
    table.addCell(ustawfrazebez("4"));
    table.addCell(ustawfrazebez("5"));
    table.addCell(ustawfrazebez("6"));
    table.addCell(ustawfrazebez("7"));
    table.addCell(ustawfrazebez("8"));
    table.addCell(ustawfrazebez("9"));
    table.addCell(ustawfrazebez("10"));
    table.addCell(ustawfrazebez("11"));
    table.addCell(ustawfrazebez("12"));
    table.addCell(ustawfrazebez("13"));
    table.addCell(ustawfrazebez("14"));
    table.addCell(ustawfrazebez("15"));
    table.addCell(ustawfrazebez("16"));
    table.setHeaderRows(4);
    table.setFooterRows(1);
    } catch (IOException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
    }
    try {
    Connection connection = getConnection();
    Statement stm = connection.createStatement();
    ResultSet rs = stm.executeQuery("SELECT * FROM dok WHERE podatnik = 'EKSTRA S.C. EWA CYBULSKA, HELENA JAKUBIAK' ORDER BY id_dok");
    while (rs.next()) {
        System.out.println(new String(rs.getBytes("opis")));
        table.addCell(ustawfrazebez("id_dok"));
        table.addCell(ustawfrazebez("data_wyst"));
        table.addCell(ustawfrazebez("nr_wl_dk"));
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(1020.28);
        System.out.println(moneyString);
        //String konwer = new String().format(Locale.CANADA, "%10.2f", "100");
        table.addCell(ustawfrazebez(moneyString));
        table.addCell(ustawfrazebez("adres kontr"));
    }
    stm.close();
    connection.close();
    } catch (Exception e) {
        
    }
    pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
    pdf.add(table);
    
    Phrase hd = new Phrase("Biuro Rachunkowe Taxman - wydruk");
    Phrase ft = new Phrase("podatnik:");
    pdf.addAuthor("Biuro Rachunkowe Taxman");
    pdf.close();
    Msg.msg("i","Wydrukowano księgę","form:messages");
    return "wydruki/filename.pdf";
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
    pdf.open();  
    BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
    Font font = new Font(helvetica,8);
    pdf.setPageSize(PageSize.A4);
    PdfPTable table = new PdfPTable(16);
    table.setWidths(new int[]{1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
    PdfPCell cell = new PdfPCell();
    table.addCell(ustawfraze("lp", 0, 2));
    table.addCell(ustawfraze("Data zdarzenia gosp.", 0, 2));
    table.addCell(ustawfraze("Nr dowodu księgowego", 0, 2));
    table.addCell(ustawfraze("Kontrahent", 2, 0));
    table.addCell(ustawfraze("Opis zdarzenia gospodarcz", 0, 2));
    table.addCell(ustawfraze("Przychody", 3, 0));
    table.addCell(ustawfraze("Zakup towarów handlowych i materiałów wg cen zakupu", 0, 2));
    table.addCell(ustawfraze("Koszty uboczne zakupu", 0, 2));
    table.addCell(ustawfraze("Wydatki(koszty)", 4, 0));
    table.addCell(ustawfraze("Uwagi", 0, 2));
    
    table.addCell(ustawfrazebez("imię i nazwisko (firma)"));
    table.addCell(ustawfrazebez("adres"));
    table.addCell(ustawfrazebez("wartość sprzedanych towarów i usług"));
    table.addCell(ustawfrazebez("pozostałe przychody"));
    table.addCell(ustawfrazebez("razem przychód (7+8)"));
    table.addCell(ustawfrazebez("wynagrodzenia w gotówce i w naturze"));
    table.addCell(ustawfrazebez("pozostałe wydatki"));
    table.addCell(ustawfrazebez("razem wydatki (12+13)"));
    table.addCell(ustawfrazebez("inwestycje"));
    
    table.addCell(ustawfrazebez("1"));
    table.addCell(ustawfrazebez("2"));
    table.addCell(ustawfrazebez("3"));
    table.addCell(ustawfrazebez("4"));
    table.addCell(ustawfrazebez("5"));
    table.addCell(ustawfrazebez("6"));
    table.addCell(ustawfrazebez("7"));
    table.addCell(ustawfrazebez("8"));
    table.addCell(ustawfrazebez("9"));
    table.addCell(ustawfrazebez("10"));
    table.addCell(ustawfrazebez("11"));
    table.addCell(ustawfrazebez("12"));
    table.addCell(ustawfrazebez("13"));
    table.addCell(ustawfrazebez("14"));
    table.addCell(ustawfrazebez("15"));
    table.addCell(ustawfrazebez("16"));
    
    table.addCell(ustawfrazebez("1"));
    table.addCell(ustawfrazebez("2"));
    table.addCell(ustawfrazebez("3"));
    table.addCell(ustawfrazebez("4"));
    table.addCell(ustawfrazebez("5"));
    table.addCell(ustawfrazebez("6"));
    table.addCell(ustawfrazebez("7"));
    table.addCell(ustawfrazebez("8"));
    table.addCell(ustawfrazebez("9"));
    table.addCell(ustawfrazebez("10"));
    table.addCell(ustawfrazebez("11"));
    table.addCell(ustawfrazebez("12"));
    table.addCell(ustawfrazebez("13"));
    table.addCell(ustawfrazebez("14"));
    table.addCell(ustawfrazebez("15"));
    table.addCell(ustawfrazebez("16"));
    table.setHeaderRows(4);
    table.setFooterRows(1);
    
    Connection connection = getConnection();
    Statement stm = connection.createStatement();
    ResultSet rs = stm.executeQuery("SELECT * FROM dok WHERE podatnik = 'EKSTRA S.C. EWA CYBULSKA, HELENA JAKUBIAK' ORDER BY id_dok");
    while (rs.next()) {
        System.out.println(new String(rs.getBytes("opis")));
        table.addCell(ustawfrazebez("id_dok"));
        table.addCell(ustawfrazebez("data_wyst"));
        table.addCell(ustawfrazebez("nr_wl_dk"));
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        String moneyString = formatter.format(1020.28);
        System.out.println(moneyString);
        //String konwer = new String().format(Locale.CANADA, "%10.2f", "100");
        table.addCell(ustawfrazebez(moneyString));
        table.addCell(ustawfrazebez("adres kontr"));
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
