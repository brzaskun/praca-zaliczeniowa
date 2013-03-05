/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import dao.EwidencjeVatDAO;
import embeddable.DokKsiega;
import embeddable.EVatViewPola;
import entity.Ewidencjevat;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.naming.NamingException;
import msg.Msg;
import view.KsiegaView;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
public class pdf extends PdfPageEventHelper implements  Serializable {
    
    @ManagedProperty(value="#{ksiegaView}")
    private KsiegaView ksiegaView;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject EwidencjeVatDAO ewidencjeVatDAO;
    private int liczydlo = 0;
    
    
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
    
    private  PdfPCell ustawfrazebez(String fraza, String orient) throws DocumentException, IOException{
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica,6);
        PdfPCell cell = new PdfPCell(new Phrase(fraza,font));
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        switch (orient) {
                case "right" :
                cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
                break;
                case "left" :
                cell.setHorizontalAlignment(Element.ALIGN_LEFT);
                break;
                case "center" :
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                break;
                case "just" :
                cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                break;
        }
        return cell;
    }
     
  
    private String formatujliczby(Double wsad){
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        try{
        String moneyString = formatter.format(wsad);
            return moneyString;
        } catch (Exception e){
            return "";
        }
    }

    public int getLiczydlo() {
        return liczydlo;
    }

    public void setLiczydlo(int liczydlo) {
        this.liczydlo = liczydlo;
    }
     
    
    
    class HeaderFooter extends PdfPageEventHelper {

              
        @Override
        public void onStartPage(PdfWriter writer, Document document) {
            liczydlo++;
        }

        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            BaseFont helvetica = null;
            try {
                helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            } catch (Exception ex) {}
            Font font = new Font(helvetica,8);
            Rectangle rect = writer.getBoxSize("art");
            ColumnText.showTextAligned(writer.getDirectContent(),
                    Element.ALIGN_CENTER, new Phrase(String.format("strona %d", liczydlo),font),
                    (rect.getLeft() + rect.getRight()) / 2, rect.getBottom() - 18, 0);
         }
        
        @Override
        public void onCloseDocument(PdfWriter writer, Document document){
            liczydlo = 0;
    }
    }
    
    
    public void drukujksiege() throws DocumentException, FileNotFoundException, IOException{
    Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), -20, -20, 20, 10);
    PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/trunk/npkpir_20/build/web/wydruki/pkpir"+wpisView.getPodatnikWpisu()+".pdf"));
     HeaderFooter event = new HeaderFooter();
     writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
     writer.setPageEvent(event);
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
    table.setWidths(new int[]{1, 2, 2, 4, 4, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2});
    PdfPCell cell = new PdfPCell();
    try {
    table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 4, 0));
    table.addCell(ustawfraze("wydruk podatkowej księgi przychodów i rozchodów", 4, 0));
    table.addCell(ustawfraze("firma: "+wpisView.getPodatnikWpisu(), 5, 0));
    table.addCell(ustawfraze("za okres: "+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu(), 3, 0));
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
    
    table.addCell(ustawfrazebez("imię i nazwisko (firma)","center"));
    table.addCell(ustawfrazebez("adres","center"));
    table.addCell(ustawfrazebez("wartość sprzedanych towarów i usług","center"));
    table.addCell(ustawfrazebez("pozostałe przychody","center"));
    table.addCell(ustawfrazebez("razem przychód (7+8)","center"));
    table.addCell(ustawfrazebez("wynagrodzenia w gotówce i w naturze","center"));
    table.addCell(ustawfrazebez("pozostałe wydatki","center"));
    table.addCell(ustawfrazebez("razem wydatki (12+13)","center"));
    table.addCell(ustawfrazebez("inwestycje", "center"));
    
    table.addCell(ustawfrazebez("1","center"));
    table.addCell(ustawfrazebez("2","center"));
    table.addCell(ustawfrazebez("3","center"));
    table.addCell(ustawfrazebez("4","center"));
    table.addCell(ustawfrazebez("5","center"));
    table.addCell(ustawfrazebez("6","center"));
    table.addCell(ustawfrazebez("7","center"));
    table.addCell(ustawfrazebez("8","center"));
    table.addCell(ustawfrazebez("9","center"));
    table.addCell(ustawfrazebez("10","center"));
    table.addCell(ustawfrazebez("11","center"));
    table.addCell(ustawfrazebez("12","center"));
    table.addCell(ustawfrazebez("13","center"));
    table.addCell(ustawfrazebez("14","center"));
    table.addCell(ustawfrazebez("15","center"));
    table.addCell(ustawfrazebez("16","center"));
    
     table.addCell(ustawfrazebez("1","center"));
    table.addCell(ustawfrazebez("2","center"));
    table.addCell(ustawfrazebez("3","center"));
    table.addCell(ustawfrazebez("4","center"));
    table.addCell(ustawfrazebez("5","center"));
    table.addCell(ustawfrazebez("6","center"));
    table.addCell(ustawfrazebez("7","center"));
    table.addCell(ustawfrazebez("8","center"));
    table.addCell(ustawfrazebez("9","center"));
    table.addCell(ustawfrazebez("10","center"));
    table.addCell(ustawfrazebez("11","center"));
    table.addCell(ustawfrazebez("12","center"));
    table.addCell(ustawfrazebez("13","center"));
    table.addCell(ustawfrazebez("14","center"));
    table.addCell(ustawfrazebez("15","center"));
    table.addCell(ustawfrazebez("16","center"));
    table.setHeaderRows(5);
    table.setFooterRows(1);
    } catch (IOException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
    }
   
    List<DokKsiega> wykaz = ksiegaView.getLista();
    for(DokKsiega rs : wykaz){
        if(rs.getNrWpkpir()!=0){
        table.addCell(ustawfrazebez(String.valueOf(rs.getNrWpkpir()),"center"));
        } else {
        table.addCell(ustawfrazebez("","center"));
        }
        table.addCell(ustawfrazebez(rs.getDataWyst(),"left"));
        table.addCell(ustawfrazebez(rs.getNrWlDk(),"left"));
        table.addCell(ustawfrazebez(rs.getKontr().getNpelna(),"left"));
        if(rs.getKontr().getKodpocztowy()!=null){
        table.addCell(ustawfrazebez(rs.getKontr().getKodpocztowy()+" "+rs.getKontr().getMiejscowosc()+" ul. "+rs.getKontr().getUlica()+" "+rs.getKontr().getDom(),"left"));
        } else {
        table.addCell(ustawfrazebez("","left"));
        }
        table.addCell(ustawfrazebez(rs.getOpis(),"left"));
        table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna7()),"right"));
        table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna8()),"right"));
        table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna9()),"right"));
        table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna10()),"right"));
        table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna11()),"right"));
        table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna12()),"right"));
        table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna13()),"right"));
        table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna14()),"right"));
        table.addCell(ustawfrazebez(formatujliczby(rs.getKolumna15()),"right"));
        table.addCell(ustawfrazebez(rs.getUwagi(),"right"));
    }
    pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
    pdf.add(table);
    pdf.addAuthor("Biuro Rachunkowe Taxman");
    pdf.close();
    Msg.msg("i","Wydrukowano księgę","form:messages");
    }   
    
    
    public void drukujewidencje() throws DocumentException, FileNotFoundException, IOException{
    Ewidencjevat lista = ewidencjeVatDAO.find(wpisView.getRokWpisu().toString(), wpisView.getMiesiacWpisu(), wpisView.getPodatnikWpisu()); 
    HashMap<String,ArrayList> mapa = lista.getEwidencje();
    Set<String> nazwy = mapa.keySet();
    for(String p :nazwy){
    Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), -20, -20, 20, 10);
    String nowanazwa;
    if(p.contains("sprzedaż")){
        nowanazwa = p.substring(0, p.length()-1);
    } else{
        nowanazwa = p;
    }
    PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/trunk/npkpir_20/build/web/wydruki/vat-"+nowanazwa+"-"+wpisView.getPodatnikWpisu()+".pdf"));
     HeaderFooter event = new HeaderFooter();
     writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
     writer.setPageEvent(event);
     pdf.open();  
    BaseFont helvetica = null;
        try {
            helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (IOException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
    Font font = new Font(helvetica,8);
    pdf.setPageSize(PageSize.A4);
    PdfPTable table = new PdfPTable(10);
    table.setWidths(new int[]{1, 2, 2, 2, 4, 2, 2, 2, 2, 2});
    PdfPCell cell = new PdfPCell();
    try {
    table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 2, 0));
    table.addCell(ustawfraze("wydruk ewidencji vat "+p, 2, 0));
    table.addCell(ustawfraze("firma: "+wpisView.getPodatnikWpisu(), 4, 0));
    table.addCell(ustawfraze("za okres: "+wpisView.getRokWpisu()+"/"+wpisView.getMiesiacWpisu(), 2, 0));
    table.addCell(ustawfraze("lp", 0, 2));
    table.addCell(ustawfraze("Data zdarzenia gosp.", 0, 2));
    table.addCell(ustawfraze("Data wystawienia faktury", 0, 2));
    table.addCell(ustawfraze("Nr dowodu księgowego", 0, 2));
    table.addCell(ustawfraze("Kontrahent", 2, 0));
    table.addCell(ustawfraze("Opis zdarzenia gospodarcz", 0, 2));
    table.addCell(ustawfraze("Netto", 0, 2));
    table.addCell(ustawfraze("Vat", 0, 2));
    table.addCell(ustawfraze("Brutto", 0, 2));
    
    
    table.addCell(ustawfrazebez("imię i nazwisko (firma)","center"));
    table.addCell(ustawfrazebez("adres","center"));
    
    table.addCell(ustawfrazebez("1","center"));
    table.addCell(ustawfrazebez("2","center"));
    table.addCell(ustawfrazebez("3","center"));
    table.addCell(ustawfrazebez("4","center"));
    table.addCell(ustawfrazebez("5","center"));
    table.addCell(ustawfrazebez("6","center"));
    table.addCell(ustawfrazebez("7","center"));
    table.addCell(ustawfrazebez("8","center"));
    table.addCell(ustawfrazebez("9","center"));
    table.addCell(ustawfrazebez("10","center"));
    
    
    table.addCell(ustawfrazebez("1","center"));
    table.addCell(ustawfrazebez("2","center"));
    table.addCell(ustawfrazebez("3","center"));
    table.addCell(ustawfrazebez("4","center"));
    table.addCell(ustawfrazebez("5","center"));
    table.addCell(ustawfrazebez("6","center"));
    table.addCell(ustawfrazebez("7","center"));
    table.addCell(ustawfrazebez("8","center"));
    table.addCell(ustawfrazebez("9","center"));
    table.addCell(ustawfrazebez("10","center"));
    
    
    table.setHeaderRows(5);
    table.setFooterRows(1);
    } catch (IOException ex) {
            Logger.getLogger(pdf.class.getName()).log(Level.SEVERE, null, ex);
    }
   
    ArrayList<EVatViewPola> ew  = lista.getEwidencje().get(p);
    Integer i = 1;
    for(EVatViewPola rs : ew){
        table.addCell(ustawfrazebez(i.toString(),"center"));
        table.addCell(ustawfrazebez(rs.getDataSprz(),"left"));
        table.addCell(ustawfrazebez(rs.getDataWyst(),"left"));
        table.addCell(ustawfrazebez(rs.getNrWlDk(),"left"));
        try{
            table.addCell(ustawfrazebez(rs.getKontr().getNpelna(),"left"));
             if(rs.getKontr().getKodpocztowy()!=null){
                table.addCell(ustawfrazebez(rs.getKontr().getKodpocztowy()+" "+rs.getKontr().getMiejscowosc()+" ul. "+rs.getKontr().getUlica()+" "+rs.getKontr().getDom(),"left"));
            } else {
            table.addCell(ustawfrazebez("","left"));
            }
        } catch (Exception e){
            table.addCell(ustawfrazebez("","left"));
            table.addCell(ustawfrazebez("","left"));
        }
       
        table.addCell(ustawfrazebez(rs.getOpis(),"left"));
        table.addCell(ustawfrazebez(formatujliczby(rs.getNetto()),"right"));
        table.addCell(ustawfrazebez(formatujliczby(rs.getVat()),"right"));
        table.addCell(ustawfrazebez(formatujliczby(rs.getNetto()+rs.getVat()),"right"));
        i++;
    }
    pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
    pdf.add(table);
    pdf.addAuthor("Biuro Rachunkowe Taxman");
    pdf.close();
    }
    Msg.msg("i","Wydrukowano ewidencje","form:messages");
    }   

    public KsiegaView getKsiegaView() {
        return ksiegaView;
    }

    public void setKsiegaView(KsiegaView ksiegaView) {
        this.ksiegaView = ksiegaView;
    }

    
   
    
//   public static void main(String[] args) throws DocumentException, FileNotFoundException, SQLException, NamingException, ClassNotFoundException, InstantiationException, IllegalAccessException, UnsupportedEncodingException, IOException{
//       
////       Document document = new Document();
////        PdfWriter writer = PdfWriter.getInstance(
////        document, new FileOutputStream("c:/filename.pdf"));
////        document.open();
////        PdfContentByte canvas = writer.getDirectContentUnder();
////        writer.setCompressionLevel(0);
////        canvas.saveState(); // q
////        canvas.beginText(); // BT
////        canvas.moveText(36, 788); // 36 788 Td
////        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
////        canvas.setFontAndSize(helvetica, 12); // /F1 12 Tf
////        canvas.showText("Hello Wośćążźrld"); // (Hello World)Tj
////        canvas.endText(); // ET
////        canvas.restoreState(); // Q
////        document.close();
//    
//    Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), -20, -20, 20, 10);
//    PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("c:/filename.pdf"));
//    pdf.open();  
//    BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
//    Font font = new Font(helvetica,8);
//    pdf.setPageSize(PageSize.A4);
//    PdfPTable table = new PdfPTable(16);
//    table.setWidths(new int[]{1, 1, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1});
//    PdfPCell cell = new PdfPCell();
//    table.addCell(ustawfraze("lp", 0, 2));
//    table.addCell(ustawfraze("Data zdarzenia gosp.", 0, 2));
//    table.addCell(ustawfraze("Nr dowodu księgowego", 0, 2));
//    table.addCell(ustawfraze("Kontrahent", 2, 0));
//    table.addCell(ustawfraze("Opis zdarzenia gospodarcz", 0, 2));
//    table.addCell(ustawfraze("Przychody", 3, 0));
//    table.addCell(ustawfraze("Zakup towarów handlowych i materiałów wg cen zakupu", 0, 2));
//    table.addCell(ustawfraze("Koszty uboczne zakupu", 0, 2));
//    table.addCell(ustawfraze("Wydatki(koszty)", 4, 0));
//    table.addCell(ustawfraze("Uwagi", 0, 2));
//    
//    table.addCell(ustawfrazebez("imię i nazwisko (firma)"));
//    table.addCell(ustawfrazebez("adres"));
//    table.addCell(ustawfrazebez("wartość sprzedanych towarów i usług"));
//    table.addCell(ustawfrazebez("pozostałe przychody"));
//    table.addCell(ustawfrazebez("razem przychód (7+8)"));
//    table.addCell(ustawfrazebez("wynagrodzenia w gotówce i w naturze"));
//    table.addCell(ustawfrazebez("pozostałe wydatki"));
//    table.addCell(ustawfrazebez("razem wydatki (12+13)"));
//    table.addCell(ustawfrazebez("inwestycje"));
//    
//    table.addCell(ustawfrazebez("1"));
//    table.addCell(ustawfrazebez("2"));
//    table.addCell(ustawfrazebez("3"));
//    table.addCell(ustawfrazebez("4"));
//    table.addCell(ustawfrazebez("5"));
//    table.addCell(ustawfrazebez("6"));
//    table.addCell(ustawfrazebez("7"));
//    table.addCell(ustawfrazebez("8"));
//    table.addCell(ustawfrazebez("9"));
//    table.addCell(ustawfrazebez("10"));
//    table.addCell(ustawfrazebez("11"));
//    table.addCell(ustawfrazebez("12"));
//    table.addCell(ustawfrazebez("13"));
//    table.addCell(ustawfrazebez("14"));
//    table.addCell(ustawfrazebez("15"));
//    table.addCell(ustawfrazebez("16"));
//    
//    table.addCell(ustawfrazebez("1"));
//    table.addCell(ustawfrazebez("2"));
//    table.addCell(ustawfrazebez("3"));
//    table.addCell(ustawfrazebez("4"));
//    table.addCell(ustawfrazebez("5"));
//    table.addCell(ustawfrazebez("6"));
//    table.addCell(ustawfrazebez("7"));
//    table.addCell(ustawfrazebez("8"));
//    table.addCell(ustawfrazebez("9"));
//    table.addCell(ustawfrazebez("10"));
//    table.addCell(ustawfrazebez("11"));
//    table.addCell(ustawfrazebez("12"));
//    table.addCell(ustawfrazebez("13"));
//    table.addCell(ustawfrazebez("14"));
//    table.addCell(ustawfrazebez("15"));
//    table.addCell(ustawfrazebez("16"));
//    table.setHeaderRows(4);
//    table.setFooterRows(1);
//    
//    Connection connection = getConnection();
//    Statement stm = connection.createStatement();
//    ResultSet rs = stm.executeQuery("SELECT * FROM dok WHERE podatnik = 'EKSTRA S.C. EWA CYBULSKA, HELENA JAKUBIAK' ORDER BY id_dok");
//    while (rs.next()) {
//        System.out.println(new String(rs.getBytes("opis")));
//        table.addCell(ustawfrazebez("id_dok"));
//        table.addCell(ustawfrazebez("data_wyst"));
//        table.addCell(ustawfrazebez("nr_wl_dk"));
//        NumberFormat formatter = NumberFormat.getCurrencyInstance();
//        String moneyString = formatter.format(1020.28);
//        System.out.println(moneyString);
//        //String konwer = new String().format(Locale.CANADA, "%10.2f", "100");
//        table.addCell(ustawfrazebez(moneyString));
//        table.addCell(ustawfrazebez("adres kontr"));
//    }
//    stm.close();
//    connection.close();
//    pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
//    pdf.add(table);
//    
//    Phrase hd = new Phrase("Biuro Rachunkowe Taxman - wydruk");
//    Phrase ft = new Phrase("podatnik:");
//    pdf.addAuthor("Biuro Rachunkowe Taxman");
//    pdf.close();
//   }   

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
}
