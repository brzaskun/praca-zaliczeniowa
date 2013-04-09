/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.ColumnText;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPageEventHelper;
import com.itextpdf.text.pdf.PdfWriter;
import dao.AmoDokDAO;
import dao.EwidencjeVatDAO;
import dao.PodatnikDAO;
import dao.UzDAO;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.text.NumberFormat;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import javax.naming.NamingException;
import view.KsiegaView;
import view.ObrotyView;
import view.WpisView;

/**
 *
 * @author Osito
 */
public class Pdf extends PdfPageEventHelper implements  Serializable {
    
    @ManagedProperty(value="#{ksiegaView}")
    protected KsiegaView ksiegaView;
    @ManagedProperty(value="#{WpisView}")
    protected WpisView wpisView;
    @ManagedProperty(value="#{obrotyView}")
    protected ObrotyView obrotyView;
    @Inject protected EwidencjeVatDAO ewidencjeVatDAO;
    protected int liczydlo = 0;
    @Inject protected UzDAO uzDAO;
    @Inject protected PodatnikDAO podatnikDAO;
    @Inject protected AmoDokDAO amoDokDAO;
    
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
   

    protected PdfPCell ustawfraze(String fraza, int colsp, int rowsp) throws DocumentException, IOException{
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
    
    protected  PdfPCell ustawfrazebez(String fraza, String orient,int fontsize) throws DocumentException, IOException{
        BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        Font font = new Font(helvetica,fontsize);
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
     
  
    protected String formatujliczby(Double wsad){
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

    public ObrotyView getObrotyView() {
        return obrotyView;
    }

    public void setObrotyView(ObrotyView obrotyView) {
        this.obrotyView = obrotyView;
    }
    
    
}
