/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;
import dao.AmoDokDAO;
import dao.PodatnikDAO;
import dao.UzDAO;
import dao.VATDeklaracjaKorektaDokDAO;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Executable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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
@ManagedBean
public class Pdf implements Serializable {

    //<editor-fold defaultstate="collapsed" desc="comment">

    /**
     * The resulting PDF.
     */
    public static final String RESULT = "c:/graphics_state.pdf";

    public static Connection getConnection() throws NamingException, ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        String url = "jdbc:mysql://localhost:3306/";
        String dbName = "pkpir";
        String driver = "com.mysql.jdbc.Driver";
        String userName = "brzaskun";
        String password = "pufikun";
        Class.forName(driver).newInstance();
        Connection conn = DriverManager.getConnection(url + dbName, userName, password);
        return conn;
    }

    /**
     * Main method.
     *
     * @param args no arguments needed
     * @throws DocumentException
     * @throws IOException
     */
    public static void main(String[] args) throws IOException, DocumentException {
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
//</editor-fold>

    @ManagedProperty(value = "#{ksiegaView}")
    protected KsiegaView ksiegaView;
    @ManagedProperty(value = "#{WpisView}")
    protected WpisView wpisView;
    @ManagedProperty(value = "#{obrotyView}")
    protected ObrotyView obrotyView;
    @Inject
    protected UzDAO uzDAO;
    @Inject
    protected PodatnikDAO podatnikDAO;
    @Inject
    protected AmoDokDAO amoDokDAO;
    @Inject
    protected VATDeklaracjaKorektaDokDAO vATDeklaracjaKorektaDokDAO;

    protected int liczydlo = 1;

//    protected void init(String fileName) {
//        try{
//               Executable ex = new Executable();
//               ex.openDocument(fileName);
//               ex.printDocument(fileName);
//            }catch(IOException e){
//               e.printStackTrace();
//            }
//    }
    //<editor-fold defaultstate="collapsed" desc="comment">
    public int getLiczydlo() {
        return liczydlo;
    }

    public void setLiczydlo(int liczydlo) {
        this.liczydlo = liczydlo;
    }

    public KsiegaView getKsiegaView() {
        return ksiegaView;
    }

    public void setKsiegaView(KsiegaView ksiegaView) {
        this.ksiegaView = ksiegaView;
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
//</editor-fold>

}
