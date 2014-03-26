/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.Mce;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import msg.Msg;
import org.primefaces.context.RequestContext;
import view.ZestawienieView;

/**
 *
 * @author Osito
 */
@ManagedBean
public class PdfZestRok extends Pdf implements Serializable {
    @ManagedProperty(value="#{ZestawienieView}")
    private ZestawienieView zestawienieView;
    public void drukujksiege() throws DocumentException, FileNotFoundException, IOException {
        Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), -20, -20, 20, 10);
        PdfWriter writer = PdfWriter.getInstance(pdf, new FileOutputStream("C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/pkpir" + wpisView.getPodatnikWpisu() + ".pdf"));
        HeaderFooter event = new HeaderFooter();
        writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
        writer.setPageEvent(event);
        pdf.addTitle("Podatkowa księga przychodów i rozchodów - sumy w roku");
        pdf.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        pdf.addSubject("Wydruk danych z PKPiR");
        pdf.addKeywords("PKPiR, PDF");
        pdf.addCreator("Grzegorz Grzelczyk");
        pdf.open();
        BaseFont helvetica = null;
        try {
            helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        Font font = new Font(helvetica, 8);
        pdf.setPageSize(PageSize.A4);
        PdfPTable table = new PdfPTable(15);
        //tu sie ustawia szerokosci kolumn
        table.setWidths(new int[]{1, 4, 4, 4, 4, 4, 3, 2, 3, 3, 3, 3, 3, 3, 4});
        PdfPCell cell = new PdfPCell();
        try {

            table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 4, 0));
            table.addCell(ustawfraze("wydruk obrotów podatkowej księgi przychodów i rozchodów", 4, 0));
            table.addCell(ustawfraze("firma: " + wpisView.getPodatnikWpisu(), 4, 0));
            table.addCell(ustawfraze("za rok : " + wpisView.getRokWpisu(), 3, 0));
            table.addCell(ustawfraze("lp", 0, 2));
            table.addCell(ustawfraze("Opis", 0, 2));
            table.addCell(ustawfraze("Okres rozl", 0, 2));
            table.addCell(ustawfraze("Przych. razem", 0, 2));
            table.addCell(ustawfraze("Koszty razem", 0, 2));
            table.addCell(ustawfraze("Wynik", 0, 2));
            table.addCell(ustawfraze("Przychody", 3, 0));
            table.addCell(ustawfraze("Zakup towarów handlowych i materiałów wg cen zakupu", 0, 2));
            table.addCell(ustawfraze("Koszty uboczne zakupu", 0, 2));
            table.addCell(ustawfraze("Wydatki(koszty)", 4, 0));

            table.addCell(ustawfrazebez("wartość sprzedanych towarów i usług", "center",6));
            table.addCell(ustawfrazebez("pozostałe przychody", "center",6));
            table.addCell(ustawfrazebez("razem przychód (7+8)", "center",6));
            table.addCell(ustawfrazebez("wynagrodzenia w gotówce i w naturze", "center",6));
            table.addCell(ustawfrazebez("pozostałe wydatki", "center",6));
            table.addCell(ustawfrazebez("razem wydatki (12+13)", "center",6));
            table.addCell(ustawfrazebez("inwestycje", "center",6));

            table.addCell(ustawfrazebez("1", "center",6));
            table.addCell(ustawfrazebez("2", "center",6));
            table.addCell(ustawfrazebez("3", "center",6));
            table.addCell(ustawfrazebez("4", "center",6));
            table.addCell(ustawfrazebez("5", "center",6));
            table.addCell(ustawfrazebez("6", "center",6));
            table.addCell(ustawfrazebez("7", "center",6));
            table.addCell(ustawfrazebez("8", "center",6));
            table.addCell(ustawfrazebez("9", "center",6));
            table.addCell(ustawfrazebez("10", "center",6));
            table.addCell(ustawfrazebez("11", "center",6));
            table.addCell(ustawfrazebez("12", "center",6));
            table.addCell(ustawfrazebez("13", "center",6));
            table.addCell(ustawfrazebez("14", "center",6));
            table.addCell(ustawfrazebez("15", "center",6));

            table.addCell(ustawfrazebez("1", "center",6));
            table.addCell(ustawfrazebez("2", "center",6));
            table.addCell(ustawfrazebez("3", "center",6));
            table.addCell(ustawfrazebez("4", "center",6));
            table.addCell(ustawfrazebez("5", "center",6));
            table.addCell(ustawfrazebez("6", "center",6));
            table.addCell(ustawfrazebez("7", "center",6));
            table.addCell(ustawfrazebez("8", "center",6));
            table.addCell(ustawfrazebez("9", "center",6));
            table.addCell(ustawfrazebez("10", "center",6));
            table.addCell(ustawfrazebez("11", "center",6));
            table.addCell(ustawfrazebez("12", "center",6));
            table.addCell(ustawfrazebez("13", "center",6));
            table.addCell(ustawfrazebez("14", "center",6));
            table.addCell(ustawfrazebez("15", "center",6));

            table.setHeaderRows(5);
            table.setFooterRows(1);
        } catch (IOException ex) {
            Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        List<List <Double>> wykaz = new ArrayList<>();
        wykaz.add(zestawienieView.getStyczen());
        wykaz.add(zestawienieView.getLuty());
        wykaz.add(zestawienieView.getMarzec());
        wykaz.add(zestawienieView.getKwiecien());
        wykaz.add(zestawienieView.getMaj());
        wykaz.add(zestawienieView.getCzerwiec());
        wykaz.add(zestawienieView.getIpolrocze());
        wykaz.add(zestawienieView.getLipiec());
        wykaz.add(zestawienieView.getSierpien());
        wykaz.add(zestawienieView.getWrzesien());
        wykaz.add(zestawienieView.getPazdziernik());
        wykaz.add(zestawienieView.getListopad());
        wykaz.add(zestawienieView.getGrudzien());
        wykaz.add(zestawienieView.getIIpolrocze());
        wykaz.add(zestawienieView.getRok());
        int nr = 1;
        int nrmca = 1;
        Map<String,Integer> mapamcyCalendar = Mce.getMapamcyCalendar();
        for (List<Double> rs : wykaz) {
            table.addCell(ustawfrazebez(String.valueOf(nr), "center",6));
            if(nr==15){
                table.addCell(ustawfrazebez("podsumowanie za rok", "left",6));
            } else if (nr==7||nr==14){
                table.addCell(ustawfrazebez("podsumowanie za półrocze", "left",6));
            } else {
                table.addCell(ustawfrazebez("sumy za miesiąc", "left",6));
            }
            if(nr==15){
                table.addCell(ustawfrazebez(wpisView.getRokWpisu().toString(), "left",6));
            } else if (nr==7){
                table.addCell(ustawfrazebez("pierwsze półrocze", "left",6));
            } else if (nr==14){
                table.addCell(ustawfrazebez("drugie półrocze", "left",6));
            } else {
                table.addCell(ustawfrazebez(Mce.getMapamcynazwa().get(nrmca++), "left",6));
            }
            table.addCell(ustawfrazebez(formatujliczby(rs.get(7)), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.get(8)), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.get(9)), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.get(0)), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.get(1)), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.get(0)+rs.get(1)), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.get(2)), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.get(3)), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.get(4)), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.get(5)), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.get(4)+rs.get(5)), "right",6));
            table.addCell(ustawfrazebez(formatujliczby(rs.get(6)), "right",6));
            nr++;
        }
        pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
        pdf.add(table);
        pdf.addAuthor("Biuro Rachunkowe Taxman");
        pdf.close();
        RequestContext.getCurrentInstance().execute("wydrukzbiorcze('"+wpisView.getPodatnikWpisu()+"');");
        Msg.msg("i", "Wydrukowano zestawienie obrotów", "form:messages");
    }

    public ZestawienieView getZestawienieView() {
        return zestawienieView;
    }

    public void setZestawienieView(ZestawienieView zestawienieView) {
        this.zestawienieView = zestawienieView;
    }

    
}
