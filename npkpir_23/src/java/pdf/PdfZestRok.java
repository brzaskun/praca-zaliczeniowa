/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.*;
import static beansPdf.PdfFont.ustawfrazeAlign;
import beansPdf.PdfHeaderFooter;
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
import embeddable.ZestawienieRyczalt;
import entity.Uz;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import msg.B;
import msg.Msg;
import org.primefaces.context.RequestContext;
import static pdffk.PdfMain.dodajOpisWstepny;
import static pdffk.PdfMain.dodajTabele;
import static pdffk.PdfMain.finalizacjaDokumentuQR;
import static pdffk.PdfMain.inicjacjaA4Portrait;
import static pdffk.PdfMain.inicjacjaWritera;
import static pdffk.PdfMain.naglowekStopkaP;
import static pdffk.PdfMain.otwarcieDokumentu;
import plik.Plik;
import view.WpisView;
import view.ZestawienieView;

/**
 *
 * @author Osito
 */

public class PdfZestRok{
    
    public static void drukuj(WpisView wpisView, ZestawienieView zestawienieView) throws DocumentException, FileNotFoundException, IOException {
        Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), -20, -20, 20, 10);
        PdfWriter writer = PdfWriter.getInstance(pdf, Plik.plikR("pkpir" + wpisView.getPodatnikWpisu() + ".pdf"));
        int liczydlo = 1;
        PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
        writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
        writer.setPageEvent(headerfoter);
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
        table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 4, 0));
        table.addCell(ustawfraze(B.b("wydrukpkpir"), 4, 0));
        if (wpisView.getPodatnikObiekt().getNazwadlafaktury() != null && !wpisView.getPodatnikObiekt().getNazwadlafaktury().equals("")) {
            table.addCell(ustawfraze(wpisView.getPodatnikObiekt().getNazwadlafaktury(), 4, 0));
        } else if (wpisView.getPodatnikObiekt().getPrintnazwa()!=null) {
            table.addCell(ustawfraze(wpisView.getPodatnikObiekt().getPrintnazwa(), 4, 0));
        } else {
            table.addCell(ustawfraze(wpisView.getPodatnikWpisu(), 4, 0));
        }
        table.addCell(ustawfraze(B.b("rok")+" : " + wpisView.getRokWpisu(), 3, 0));
        table.addCell(ustawfraze(B.b("lp"), 0, 2));
        table.addCell(ustawfraze(B.b("opis"), 0, 2));
        table.addCell(ustawfraze(B.b("okresrozliczeniowy"), 0, 2));
        table.addCell(ustawfraze(B.b("przychrazem"), 0, 2));
        table.addCell(ustawfraze(B.b("kosztyrazem"), 0, 2));
        table.addCell(ustawfraze(B.b("wynik"), 0, 2));
        table.addCell(ustawfraze(B.b("przychody"), 3, 0));
        table.addCell(ustawfraze(B.b("towaryhandlowemat"), 0, 2));
        table.addCell(ustawfraze(B.b("kosztyubocznezakupu"), 0, 2));
        table.addCell(ustawfraze(B.b("Wydatki(koszty)"), 4, 0));
        table.addCell(ustawfrazeAlign(B.b("wartoscsprzedaz"), "center",6));
        table.addCell(ustawfrazeAlign(B.b("pozostaleprzychody"), "center",6));
        table.addCell(ustawfrazeAlign(B.b("razemprzychod"), "center",6));
        table.addCell(ustawfrazeAlign(B.b("wynagrodzeniawgot"), "center",6));
        table.addCell(ustawfrazeAlign(B.b("pozostalewydatki"), "center",6));
        table.addCell(ustawfrazeAlign(B.b("razemwydatki"), "center",6));
        table.addCell(ustawfrazeAlign(B.b("inwestycje"), "center",6));
        table.addCell(ustawfrazeAlign("1", "center",6));
        table.addCell(ustawfrazeAlign("2", "center",6));
        table.addCell(ustawfrazeAlign("3", "center",6));
        table.addCell(ustawfrazeAlign("4", "center",6));
        table.addCell(ustawfrazeAlign("5", "center",6));
        table.addCell(ustawfrazeAlign("6", "center",6));
        table.addCell(ustawfrazeAlign("7", "center",6));
        table.addCell(ustawfrazeAlign("8", "center",6));
        table.addCell(ustawfrazeAlign("9", "center",6));
        table.addCell(ustawfrazeAlign("10", "center",6));
        table.addCell(ustawfrazeAlign("11", "center",6));
        table.addCell(ustawfrazeAlign("12", "center",6));
        table.addCell(ustawfrazeAlign("13", "center",6));
        table.addCell(ustawfrazeAlign("14", "center",6));
        table.addCell(ustawfrazeAlign("15", "center",6));
        table.addCell(ustawfrazeAlign("1", "center",6));
        table.addCell(ustawfrazeAlign("2", "center",6));
        table.addCell(ustawfrazeAlign("3", "center",6));
        table.addCell(ustawfrazeAlign("4", "center",6));
        table.addCell(ustawfrazeAlign("5", "center",6));
        table.addCell(ustawfrazeAlign("6", "center",6));
        table.addCell(ustawfrazeAlign("7", "center",6));
        table.addCell(ustawfrazeAlign("8", "center",6));
        table.addCell(ustawfrazeAlign("9", "center",6));
        table.addCell(ustawfrazeAlign("10", "center",6));
        table.addCell(ustawfrazeAlign("11", "center",6));
        table.addCell(ustawfrazeAlign("12", "center",6));
        table.addCell(ustawfrazeAlign("13", "center",6));
        table.addCell(ustawfrazeAlign("14", "center",6));
        table.addCell(ustawfrazeAlign("15", "center",6));
        table.setHeaderRows(5);
        table.setFooterRows(1);
        
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
            table.addCell(ustawfrazeAlign(String.valueOf(nr), "center",6));
            if(nr==15){
                table.addCell(ustawfrazeAlign(B.b("podsumowaniezarok"), "left",6));
            } else if (nr==7||nr==14){
                table.addCell(ustawfrazeAlign(B.b("podsumowaniezapolrocze"), "left",6));
            } else {
                table.addCell(ustawfrazeAlign(B.b("sumyzamiesiac"), "left",6));
            }
            if(nr==15){
                table.addCell(ustawfrazeAlign(wpisView.getRokWpisu().toString(), "left",6));
            } else if (nr==7){
                table.addCell(ustawfrazeAlign(B.b("pierwszepolrocze"), "left",6));
            } else if (nr==14){
                table.addCell(ustawfrazeAlign(B.b("drugiepolrocze"), "left",6));
            } else {
                if (FacesContext.getCurrentInstance().getViewRoot().getLocale().toString().equals("en")) {
                    table.addCell(ustawfrazeAlign(Mce.getNumberToNazwamiesiacaEN().get(nrmca++), "left",6));
                } else {
                    table.addCell(ustawfrazeAlign(Mce.getNumberToNazwamiesiaca().get(nrmca++), "left",6));
                }
            }
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.get(7)), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.get(8)), "right",6));
            table.addCell(ustawfrazeAlign(formatujWaluta(rs.get(9)), "right",6));
            table.addCell(ustawfrazeAlign(formatujWalutaNoZero(rs.get(0)), "right",6));
            table.addCell(ustawfrazeAlign(formatujWalutaNoZero(rs.get(1)), "right",6));
            table.addCell(ustawfrazeAlign(formatujWalutaNoZero(rs.get(0)+rs.get(1)), "right",6));
            table.addCell(ustawfrazeAlign(formatujWalutaNoZero(rs.get(2)), "right",6));
            table.addCell(ustawfrazeAlign(formatujWalutaNoZero(rs.get(3)), "right",6));
            table.addCell(ustawfrazeAlign(formatujWalutaNoZero(rs.get(4)), "right",6));
            table.addCell(ustawfrazeAlign(formatujWalutaNoZero(rs.get(5)), "right",6));
            table.addCell(ustawfrazeAlign(formatujWalutaNoZero(rs.get(4)+rs.get(5)), "right",6));
            table.addCell(ustawfrazeAlign(formatujWalutaNoZero(rs.get(6)), "right",6));
            nr++;
        }
        pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
        pdf.add(table);
        pdf.addAuthor("Biuro Rachunkowe Taxman");
        pdf.close();
        RequestContext.getCurrentInstance().execute("wydrukzbiorcze('"+wpisView.getPodatnikWpisu()+"');");
        Msg.msg("i", "Wydrukowano zestawienie obrotów", "form:messages");
    }
    
    public static void drukujRyczalt(WpisView wpisView, List<ZestawienieRyczalt> pobranetransakcje) throws DocumentException, FileNotFoundException, IOException {
        String nazwa = "pkpir" + wpisView.getPodatnikWpisu().trim();
        File file = new File(nazwa);
        if (file.isFile()) {
            file.delete();
        }
        if (pobranetransakcje != null && pobranetransakcje.size() > 0) {
            Uz uz = wpisView.getWprowadzil();
            Document document = inicjacjaA4Portrait();
            PdfWriter writer = inicjacjaWritera(document, nazwa);
            naglowekStopkaP(writer);
            otwarcieDokumentu(document, nazwa);
            dodajOpisWstepny(document, "Zestawienie przychodów ewidencjonowanych firmy: ", wpisView.getPodatnikObiekt(), null, wpisView.getRokWpisuSt());
            dodajTabele(document, testobjects.testobjects.getZestawienieRyczalt(pobranetransakcje),97,0);
            finalizacjaDokumentuQR(document,nazwa);
            //String f = "pokazwydruk('"+nazwa+"');";
            //RequestContext.getCurrentInstance().execute(f);
            RequestContext.getCurrentInstance().execute("wydrukzbiorczeryczalt('"+wpisView.getPodatnikWpisu().trim()+"');");
            Msg.msg("i", "Wydrukowano zestawienie obrotów ryczałt", "form:messages");
        } else {
            Msg.msg("w", "Nie wybrano Zestawienia Ryczałtu do wydruku");
        }
    }

       
}
