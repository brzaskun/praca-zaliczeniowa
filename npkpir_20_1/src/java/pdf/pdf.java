/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Font.FontFamily;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
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
    PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/trunk/npkpir_20_1/build/web/wydruki/pkpir"+wpisView.getPodatnikWpisu()+".pdf"));
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
    Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), 0, 0, 40, 5);
    String nowanazwa;
    if(p.contains("sprzedaż")){
        nowanazwa = p.substring(0, p.length()-1);
    } else{
        nowanazwa = p;
    }
    PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/trunk/npkpir_20_1/build/web/wydruki/vat-"+nowanazwa+"-"+wpisView.getPodatnikWpisu()+".pdf"));
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
    Font font = new Font(FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
//    Chunk id = new Chunk("Wielka linijka do wklejenia. Chunk", font);
//    id.setBackground(BaseColor.BLACK);
//    Paragraph parag = new Paragraph();
//    parag.setLeading(100);
//    parag.add(id);
//    parag.add(Chunk.NEWLINE);
//    pdf.add(parag);
    font = new Font(helvetica,8);
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

    
   
    
  /** The resulting PDF. */
    public static final String RESULT
        = "c:/graphics_state.pdf";
 
    /**
     * Main method.
     *
     * @param    args    no arguments needed
     * @throws DocumentException 
     * @throws IOException
     */
    public static void main(String[] args)
        throws IOException, DocumentException {
    	// step 1
        Document document = new Document(new Rectangle(200, 120));
        // step 2
        PdfWriter writer
             = PdfWriter.getInstance(document, new FileOutputStream(RESULT));
        // step 3
        document.open();
        // step 4
        PdfContentByte canvas = writer.getDirectContent();
        // state 1:
        canvas.setRGBColorFill(0xFF, 0x45, 0x00);
        // fill a rectangle in state 1
        canvas.rectangle(10, 10, 60, 60);
        canvas.fill();
        canvas.saveState();
        // state 2;
        canvas.setLineWidth(3);
        canvas.setRGBColorFill(0x8B, 0x00, 0x00);
        // fill and stroke a rectangle in state 2
        canvas.rectangle(40, 20, 60, 60);
        canvas.fillStroke();
        canvas.saveState();
        // state 3:
        canvas.concatCTM(1, 0, 0.1f, 1, 0, 0);
        canvas.setRGBColorStroke(0xFF, 0x45, 0x00);
        canvas.setRGBColorFill(0xFF, 0xD7, 0x00);
        // fill and stroke a rectangle in state 3
        canvas.rectangle(70, 30, 60, 60);
        canvas.fillStroke();
        canvas.restoreState();
        // stroke a rectangle in state 2
        canvas.rectangle(100, 40, 60, 60);
        canvas.stroke();
        canvas.restoreState();
        // fill and stroke a rectangle in state 1
        canvas.rectangle(130, 50, 60, 60);
        canvas.fillStroke();
        // step 5
        document.close();
    }


    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
}
