/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import static beansPdf.PdfFont.formatujWaluta;
import static beansPdf.PdfFont.ustawfraze;
import static beansPdf.PdfFont.ustawfrazeAlign;
import beansPdf.PdfHeaderFooter;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import comparator.EVatViewPolaWartoscNettocomparator;
import comparator.EVatViewPolaWartosccomparator;
import comparator.EVatViewPolacomparator;
import entity.EVatwpis1;
import entity.EVatwpisSuper;
import entity.Podatnik;
import error.E;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import plik.Plik;
import view.WpisView;
/**
 *
 * @author Osito
 */

public class PdfVAT {

    public static void drukujewidencje(WpisView wpisView, HashMap<String, List<EVatwpisSuper>> mapa, String nazwaewidencji, boolean wartosc) throws DocumentException, FileNotFoundException, IOException {
        try {
            Set<String> nazwy = mapa.keySet();
            for (String p : nazwy) {
                if (p.equals(nazwaewidencji)) {
                    Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), 0, 0, 40, 25);
                    String nowanazwa;
                    if (p.contains("sprzedaż")) {
                        nowanazwa = p.substring(0, p.length() - 1);
                    } else {
                        nowanazwa = p;
                    }
                    String nazwapliku = "vat-" + nowanazwa + "-" + wpisView.getPodatnikObiekt().getNip() + ".pdf";
                    try {
                        File file = Plik.plik(nazwapliku, true);
                        if (file.isFile()) {
                            file.delete();
                        }
                    } catch (Exception e) {

                    }
                    PdfWriter writer = PdfWriter.getInstance(pdf, Plik.plikR(nazwapliku));
                    int liczydlo = 1;
                    PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
                    writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
                    writer.setPageEvent(headerfoter);
                    pdf.addTitle("Ewidencja VAT");
                    pdf.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
                    pdf.addSubject("Wydruk danych z ewidencji VAT");
                    pdf.addKeywords("VAT, PDF");
                    pdf.addCreator("Grzegorz Grzelczyk");
                    pdf.open();
                    BaseFont helvetica = null;
                    try {
                        helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
                    } catch (IOException ex) {
                        // Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Font font = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
                    font = new Font(helvetica, 8);
                    pdf.setPageSize(PageSize.A4);
                    PdfPTable table = new PdfPTable(12);
                    table.setWidthPercentage(95);
                    table.setWidths(new int[]{1, 2, 2, 2, 3, 3, 3, 3, 4, 2, 2, 2});
                    PdfPCell cell = new PdfPCell();
                    table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 2, 0));
                    table.addCell(ustawfraze("wydruk ewidencji vat " + p, 3, 0));
                    table.addCell(ustawfraze("firma: " + wpisView.getPodatnikObiekt().podatnikDaneWydruk(), 5, 0));
                    table.addCell(ustawfraze("za okres: " + wpisView.getRokWpisu() + "/" + wpisView.getMiesiacWpisu(), 2, 0));
                    
                    table.addCell(ustawfraze("lp", 0, 2));
                    table.addCell(ustawfraze("Data zdarzenia goswp.", 0, 2));
                    table.addCell(ustawfraze("Data wystawienia faktury", 0, 2));
                    table.addCell(ustawfraze("Nr dowodu księgowego", 0, 2));
                    table.addCell(ustawfraze("Nr własny dok.", 0, 2));
                    table.addCell(ustawfraze("Kontrahent", 3, 0));
                    table.addCell(ustawfraze("Opis zdarzenia gospodarcz", 0, 2));
                    table.addCell(ustawfraze("Netto", 0, 2));
                    table.addCell(ustawfraze("Vat", 0, 2));
                    table.addCell(ustawfraze("Brutto", 0, 2));
                    
                    table.addCell(ustawfrazeAlign("imię i nazwisko (firma)", "center", 6));
                    table.addCell(ustawfrazeAlign("NIP", "center", 6));
                    table.addCell(ustawfrazeAlign("adres", "center", 6));
                    
                    table.addCell(ustawfrazeAlign("1", "center", 6));
                    table.addCell(ustawfrazeAlign("2", "center", 6));
                    table.addCell(ustawfrazeAlign("3", "center", 6));
                    table.addCell(ustawfrazeAlign("4", "center", 6));
                    table.addCell(ustawfrazeAlign("5", "center", 6));
                    table.addCell(ustawfrazeAlign("6", "center", 6));
                    table.addCell(ustawfrazeAlign("7", "center", 6));
                    table.addCell(ustawfrazeAlign("8", "center", 6));
                    table.addCell(ustawfrazeAlign("9", "center", 6));
                    table.addCell(ustawfrazeAlign("10", "center", 6));
                    table.addCell(ustawfrazeAlign("11", "center", 6));
                    table.addCell(ustawfrazeAlign("12", "center", 6));
                    
                    table.addCell(ustawfrazeAlign("1", "center", 6));
                    table.addCell(ustawfrazeAlign("2", "center", 6));
                    table.addCell(ustawfrazeAlign("3", "center", 6));
                    table.addCell(ustawfrazeAlign("4", "center", 6));
                    table.addCell(ustawfrazeAlign("5", "center", 6));
                    table.addCell(ustawfrazeAlign("6", "center", 6));
                    table.addCell(ustawfrazeAlign("7", "center", 6));
                    table.addCell(ustawfrazeAlign("8", "center", 6));
                    table.addCell(ustawfrazeAlign("9", "center", 6));
                    table.addCell(ustawfrazeAlign("10", "center", 6));
                    table.addCell(ustawfrazeAlign("11", "center", 6));
                    table.addCell(ustawfrazeAlign("12", "center", 6));
                    
                    table.setHeaderRows(5);
                    table.setFooterRows(1);

                    List<EVatwpisSuper> ew = mapa.get(p);
                    int size = ew.size();
                    EVatwpisSuper polesuma = ew.get(size - 1);
                    ew.remove(polesuma);
                     boolean sortujponetto = false;
                    if (size > 0) {
                        EVatwpisSuper wp = ew.get(0);
                        if (wp.getNazwaewidencji().getTransakcja().equals("usługi poza ter.")) {
                            sortujponetto = true;
                        }
                    }
                    if (wartosc==true) {
                        if (sortujponetto) {
                            Collections.sort(ew, new EVatViewPolaWartoscNettocomparator());
                        } else {
                            Collections.sort(ew, new EVatViewPolaWartosccomparator());
                        }
                    } else {
                        Collections.sort(ew, new EVatViewPolacomparator());
                    }
                    ew.add(polesuma);
                    Integer i = 1;
                    for (EVatwpisSuper rs : ew) {
                        if (rs instanceof EVatwpis1) {
                            dodajwiersztabeliEVatwpis1(table, rs, i);
                        } else {
                            dodajwiersztabeli(table, rs, i);
                        }
                        i++;
                    }
                    pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
                    pdf.add(table);
                    pdf.addAuthor("Biuro Rachunkowe Taxman");
                    pdf.close();
pdffk.PdfMain.dodajQR(nazwapliku);
                
                }
            }
            //Msg.msg("i","Wydrukowano ewidencje","form:messages");
        } catch (Exception e) {
        }
    }
    
    public static void drukujewidencjeWybrane(WpisView wpisView, HashMap<String, List<EVatwpisSuper>> mapa, String nazwaewidencji, boolean wartosc,List<EVatwpisSuper> wybranewierszeewidencji) throws DocumentException, FileNotFoundException, IOException {
        try {
            Set<String> nazwy = mapa.keySet();
            for (String p : nazwy) {
                if (p.equals(nazwaewidencji)) {
                    Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), 0, 0, 40, 25);
                    String nowanazwa;
                    if (p.contains("sprzedaż")) {
                        nowanazwa = p.substring(0, p.length() - 1);
                    } else {
                        nowanazwa = p;
                    }
                    String nazwapliku = "vat-" + nowanazwa + "-" + wpisView.getPodatnikObiekt().getNip() + ".pdf";
                    try {
                        File file = Plik.plik(nazwapliku, true);
                        if (file.isFile()) {
                            file.delete();
                        }
                    } catch (Exception e) {

                    }
                    PdfWriter writer = PdfWriter.getInstance(pdf, Plik.plikR(nazwapliku));
                    int liczydlo = 1;
                    PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
                    writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
                    writer.setPageEvent(headerfoter);
                    pdf.addTitle("Ewidencja VAT");
                    pdf.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
                    pdf.addSubject("Wydruk danych z ewidencji VAT");
                    pdf.addKeywords("VAT, PDF");
                    pdf.addCreator("Grzegorz Grzelczyk");
                    pdf.open();
                    BaseFont helvetica = null;
                    try {
                        helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
                    } catch (IOException ex) {
                        // Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    Font font = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
                    font = new Font(helvetica, 8);
                    pdf.setPageSize(PageSize.A4);
                    PdfPTable table = new PdfPTable(12);
                    table.setWidthPercentage(95);
                    table.setWidths(new int[]{1, 2, 2, 2, 3, 3, 3, 3, 4, 2, 2, 2});
                    PdfPCell cell = new PdfPCell();
                    table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 2, 0));
                    table.addCell(ustawfraze("wydruk ewidencji vat " + p, 3, 0));
                    table.addCell(ustawfraze("firma: " + wpisView.getPodatnikObiekt().podatnikDaneWydruk(), 5, 0));
                    table.addCell(ustawfraze("za okres: " + wpisView.getRokWpisu() + "/" + wpisView.getMiesiacWpisu(), 2, 0));
                    
                    table.addCell(ustawfraze("lp", 0, 2));
                    table.addCell(ustawfraze("Data zdarzenia goswp.", 0, 2));
                    table.addCell(ustawfraze("Data wystawienia faktury", 0, 2));
                    table.addCell(ustawfraze("Nr dowodu księgowego", 0, 2));
                    table.addCell(ustawfraze("Nr własny dok.", 0, 2));
                    table.addCell(ustawfraze("Kontrahent", 3, 0));
                    table.addCell(ustawfraze("Opis zdarzenia gospodarcz", 0, 2));
                    table.addCell(ustawfraze("Netto", 0, 2));
                    table.addCell(ustawfraze("Vat", 0, 2));
                    table.addCell(ustawfraze("Brutto", 0, 2));
                    
                    table.addCell(ustawfrazeAlign("imię i nazwisko (firma)", "center", 6));
                    table.addCell(ustawfrazeAlign("NIP", "center", 6));
                    table.addCell(ustawfrazeAlign("adres", "center", 6));
                    
                    table.addCell(ustawfrazeAlign("1", "center", 6));
                    table.addCell(ustawfrazeAlign("2", "center", 6));
                    table.addCell(ustawfrazeAlign("3", "center", 6));
                    table.addCell(ustawfrazeAlign("4", "center", 6));
                    table.addCell(ustawfrazeAlign("5", "center", 6));
                    table.addCell(ustawfrazeAlign("6", "center", 6));
                    table.addCell(ustawfrazeAlign("7", "center", 6));
                    table.addCell(ustawfrazeAlign("8", "center", 6));
                    table.addCell(ustawfrazeAlign("9", "center", 6));
                    table.addCell(ustawfrazeAlign("10", "center", 6));
                    table.addCell(ustawfrazeAlign("11", "center", 6));
                    table.addCell(ustawfrazeAlign("12", "center", 6));
                    
                    table.addCell(ustawfrazeAlign("1", "center", 6));
                    table.addCell(ustawfrazeAlign("2", "center", 6));
                    table.addCell(ustawfrazeAlign("3", "center", 6));
                    table.addCell(ustawfrazeAlign("4", "center", 6));
                    table.addCell(ustawfrazeAlign("5", "center", 6));
                    table.addCell(ustawfrazeAlign("6", "center", 6));
                    table.addCell(ustawfrazeAlign("7", "center", 6));
                    table.addCell(ustawfrazeAlign("8", "center", 6));
                    table.addCell(ustawfrazeAlign("9", "center", 6));
                    table.addCell(ustawfrazeAlign("10", "center", 6));
                    table.addCell(ustawfrazeAlign("11", "center", 6));
                    table.addCell(ustawfrazeAlign("12", "center", 6));
                    
                    table.setHeaderRows(5);
                    table.setFooterRows(1);
                    int size = wybranewierszeewidencji.size();
                    EVatwpisSuper polesuma = wybranewierszeewidencji.get(size - 1);
                    wybranewierszeewidencji.remove(polesuma);
                    boolean sortujponetto = false;
                    if (size > 0) {
                        EVatwpisSuper wp = wybranewierszeewidencji.get(0);
                        if (wp.getNazwaewidencji().getTransakcja().equals("usługi poza ter.")) {
                            sortujponetto = true;
                        }
                    }
                    if (wartosc==true) {
                        if (sortujponetto) {
                            Collections.sort(wybranewierszeewidencji, new EVatViewPolaWartoscNettocomparator());
                        } else {
                            Collections.sort(wybranewierszeewidencji, new EVatViewPolaWartosccomparator());
                        }
                    } else {
                        Collections.sort(wybranewierszeewidencji, new EVatViewPolacomparator());
                    }
                    wybranewierszeewidencji.add(polesuma);
                    Integer i = 1;
                     for (EVatwpisSuper rs : wybranewierszeewidencji) {
                        if (rs instanceof EVatwpis1) {
                            dodajwiersztabeliEVatwpis1(table, rs, i);
                        } else {
                            dodajwiersztabeli(table, rs, i);
                        }
                        i++;
                    }
                    pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
                    pdf.add(table);
                    pdf.addAuthor("Biuro Rachunkowe Taxman");
                    pdf.close();
                    pdffk.PdfMain.dodajQR(nazwapliku);
                }
            }
            //Msg.msg("i","Wydrukowano ewidencje","form:messages");
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private static void dodajwiersztabeli(PdfPTable table, EVatwpisSuper rs, Integer i) throws DocumentException, IOException {
        table.addCell(ustawfrazeAlign(i.toString(), "center", 6));
        table.addCell(ustawfrazeAlign(rs.getDataSprz(), "left", 7));
        table.addCell(ustawfrazeAlign(rs.getDataWyst(), "left", 7));
        table.addCell(ustawfrazeAlign(rs.getNrKolejny(), "left", 6));
        try {
            if (!rs.getOpis().equals("podsumowanie")&& rs.getDokfk() != null && rs.getDokfk().getRodzajedok().getKategoriadokumentu()==0 && rs.getNumerwlasnydokfk() != null) {
                table.addCell(ustawfrazeAlign(rs.getNumerwlasnydokfk(), "left", 6));
            } else {
                table.addCell(ustawfrazeAlign(rs.getNrWlDk(), "left", 6));
            }
            if (rs.getKontr() != null) {
                table.addCell(ustawfrazeAlign(rs.getKontr().getNpelna(), "left", 6));
            } else {
                table.addCell(ustawfrazeAlign("", "left", 6));
            }
            if (rs.getKontr() != null && rs.getKontr().getNip() != null) {
                table.addCell(ustawfrazeAlign(rs.getKontr().getNip(), "left", 6));
            } else {
                table.addCell(ustawfrazeAlign("", "left", 6));
            }
            if (rs.getKontr() != null && rs.getKontr().getKodpocztowy() != null) {
                table.addCell(ustawfrazeAlign(rs.getKontr().getKodpocztowy() + " " + rs.getKontr().getMiejscowosc() + " ul. " + rs.getKontr().getUlica() + " " + rs.getKontr().getDom(), "left", 6));
            } else {
                table.addCell(ustawfrazeAlign("", "left", 6));
            }
        } catch (Exception e) {
            E.e(e); 
        }
        table.addCell(ustawfrazeAlign(rs.getOpis(), "left", 6));
        table.addCell(ustawfrazeAlign(formatujWaluta(rs.getNetto()), "right", 7));
        table.addCell(ustawfrazeAlign(formatujWaluta(rs.getVat()), "right", 7));
        table.addCell(ustawfrazeAlign(formatujWaluta(rs.getNetto() + rs.getVat()), "right", 7));
    }
    
    private static void dodajwiersztabeliEVatwpis1(PdfPTable table, EVatwpisSuper eVatwpisSuper, Integer i) throws DocumentException, IOException {
        EVatwpis1 rs = (EVatwpis1) eVatwpisSuper;
        table.addCell(ustawfrazeAlign(i.toString(), "center", 6));
        table.addCell(ustawfrazeAlign(rs.getDataSprz(), "left", 7));
        table.addCell(ustawfrazeAlign(rs.getDataWyst(), "left", 7));
        table.addCell(ustawfrazeAlign(rs.getNrKolejny(), "left", 6));
        try {
            if (!rs.getOpis().equals("podsumowanie")&& rs.getNrWlDk() != null) {
                table.addCell(ustawfrazeAlign(rs.getNrWlDk(), "left", 6));
            } else {
                table.addCell(ustawfrazeAlign("podsumowanie", "left", 6));
            }
            if (!rs.getOpis().equals("podsumowanie")&&rs.getKontr() != null) {
                table.addCell(ustawfrazeAlign(rs.getKontr().getNpelna(), "left", 6));
            } else {
                table.addCell(ustawfrazeAlign("", "left", 6));
            }
            if (!rs.getOpis().equals("podsumowanie")&&rs.getKontr() != null && rs.getKontr().getNip() != null) {
                table.addCell(ustawfrazeAlign(rs.getKontr().getNip(), "left", 6));
            } else {
                table.addCell(ustawfrazeAlign("", "left", 6));
            }
            if (rs.getKontr() != null && rs.getKontr().getKodpocztowy() != null) {
                table.addCell(ustawfrazeAlign(rs.getKontr().getKodpocztowy() + " " + rs.getKontr().getMiejscowosc() + " ul. " + rs.getKontr().getUlica() + " " + rs.getKontr().getDom(), "left", 6));
            } else {
                table.addCell(ustawfrazeAlign("", "left", 6));
            }
        } catch (Exception e) {
            E.e(e); 
        }
        table.addCell(ustawfrazeAlign(rs.getOpis(), "left", 6));
        table.addCell(ustawfrazeAlign(formatujWaluta(rs.getNetto()), "right", 7));
        table.addCell(ustawfrazeAlign(formatujWaluta(rs.getVat()), "right", 7));
        table.addCell(ustawfrazeAlign(formatujWaluta(rs.getNetto() + rs.getVat()), "right", 7));
    }
    
    
    public static void drukujewidencjenajednejkartce(String nazwapliku, Podatnik pod, String rok, String mc, HashMap<String, List<EVatwpisSuper>> mapa, boolean wartosc) throws DocumentException, FileNotFoundException, IOException {
        Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), 0, 0, 40, 25);
        try {
            List<String> nazwy = Collections.synchronizedList(new ArrayList<>());
            nazwy.addAll(mapa.keySet());
            if (wartosc) {
                nazwapliku = "vat-wszystko-wartosc-" + pod.getNip() + ".pdf";
            } 
            if (!wartosc && nazwapliku==null) {
                nazwapliku = "vat-wszystko-" + pod.getNip()+ ".pdf";
            }
                File file = Plik.plik(nazwapliku, true);
                if (file.isFile()) {
                    file.delete();
                }
                PdfWriter writer = PdfWriter.getInstance(pdf, Plik.plikR(nazwapliku));
                int liczydlo = 1;
                PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
                writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
                writer.setPageEvent(headerfoter);
                pdf.addTitle("Ewidencja VAT - zestawienie");
                pdf.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
                pdf.addSubject("Wydruk ewidencji VAT za okres rozliczeniowy");
                pdf.addKeywords("VAT, PDF");
                pdf.addCreator("Grzegorz Grzelczyk");
                pdf.open();
                BaseFont helvetica = null;
                try {
                    helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
                } catch (IOException ex) {
                    // Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
                }
                Font font = new Font(Font.FontFamily.HELVETICA, 10, Font.BOLD, BaseColor.WHITE);
                //    Chunk id = new Chunk("Wielka linijka do wklejenia. Chunk", font);
                //    id.setBackground(BaseColor.BLACK);
                //    Paragraph parag = new Paragraph();
                //    parag.setLeading(100);
                //    parag.add(id);
                //    parag.add(Chunk.NEWLINE);
                //    Pdf.add(parag);
                font = new Font(helvetica, 8);
                pdf.setPageSize(PageSize.A4);
                pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
                Collections.sort(nazwy);
                for (Iterator<String> nazwa = nazwy.iterator(); nazwa.hasNext();) {
                  String p = nazwa.next();
                  pdf.add(stworztabele(mapa, p, pod, rok, mc, wartosc));
                  if (nazwa.hasNext()) {
                    Paragraph parag = new Paragraph();
                    parag.setLeading(20);
                    parag.add(Chunk.NEWLINE);
                    pdf.add(parag);
                  }
                }
                pdf.addAuthor("Biuro Rachunkowe Taxman");
                pdf.close();
            //Msg.msg("i","Wydrukowano ewidencje","form:messages");
        } catch (Exception e) {
            E.e(e); 
            pdf.close();
        }
    }
    
    private static PdfPTable stworztabele(HashMap<String, List<EVatwpisSuper>> mapa, String nazwaewidencji, Podatnik pod, String rok, String mc, boolean wartosc) {
        try {
            PdfPTable table = new PdfPTable(12);
            table.setWidthPercentage(95);
            table.setWidths(new int[]{1, 2, 2, 2, 3, 3, 3, 3, 4, 2, 2, 2});
            PdfPCell cell = new PdfPCell();
            table.addCell(ustawfraze("Biuro Rachunkowe Taxman", 2, 0));
            table.addCell(ustawfraze("wydruk ewidencji vat " + nazwaewidencji, 3, 0));
            table.addCell(ustawfraze("firma: " + pod.podatnikDaneWydruk(), 5, 0));
            table.addCell(ustawfraze("za okres: " + rok + "/" + mc, 2, 0));

            table.addCell(ustawfraze("lp", 0, 2));
            table.addCell(ustawfraze("Data zdarzenia goswp.", 0, 2));
            table.addCell(ustawfraze("Data wystawienia faktury", 0, 2));
            table.addCell(ustawfraze("Nr dowodu księgowego", 0, 2));
            table.addCell(ustawfraze("Nr własny dok.", 0, 2));
            table.addCell(ustawfraze("Kontrahent", 3, 0));
            table.addCell(ustawfraze("Opis zdarzenia gospodarcz", 0, 2));
            table.addCell(ustawfraze("Netto", 0, 2));
            table.addCell(ustawfraze("Vat", 0, 2));
            table.addCell(ustawfraze("Brutto", 0, 2));

            table.addCell(ustawfrazeAlign("imię i nazwisko (firma)", "center", 6));
            table.addCell(ustawfrazeAlign("NIP", "center", 6));
            table.addCell(ustawfrazeAlign("adres", "center", 6));

            table.addCell(ustawfrazeAlign("1", "center", 6));
            table.addCell(ustawfrazeAlign("2", "center", 6));
            table.addCell(ustawfrazeAlign("3", "center", 6));
            table.addCell(ustawfrazeAlign("4", "center", 6));
            table.addCell(ustawfrazeAlign("5", "center", 6));
            table.addCell(ustawfrazeAlign("6", "center", 6));
            table.addCell(ustawfrazeAlign("7", "center", 6));
            table.addCell(ustawfrazeAlign("8", "center", 6));
            table.addCell(ustawfrazeAlign("9", "center", 6));
            table.addCell(ustawfrazeAlign("10", "center", 6));
            table.addCell(ustawfrazeAlign("11", "center", 6));
            table.addCell(ustawfrazeAlign("12", "center", 6));

            table.addCell(ustawfrazeAlign("1", "center", 6));
            table.addCell(ustawfrazeAlign("2", "center", 6));
            table.addCell(ustawfrazeAlign("3", "center", 6));
            table.addCell(ustawfrazeAlign("4", "center", 6));
            table.addCell(ustawfrazeAlign("5", "center", 6));
            table.addCell(ustawfrazeAlign("6", "center", 6));
            table.addCell(ustawfrazeAlign("7", "center", 6));
            table.addCell(ustawfrazeAlign("8", "center", 6));
            table.addCell(ustawfrazeAlign("9", "center", 6));
            table.addCell(ustawfrazeAlign("10", "center", 6));
            table.addCell(ustawfrazeAlign("11", "center", 6));
            table.addCell(ustawfrazeAlign("12", "center", 6));

            table.setHeaderRows(5);
            table.setFooterRows(1);

            List<EVatwpisSuper> ew = mapa.get(nazwaewidencji);
            if (wartosc==true) {
                Collections.sort(ew, new EVatViewPolaWartosccomparator());
            } else {
                Collections.sort(ew, new EVatViewPolacomparator());
            }
            Integer i = 1;
            for (EVatwpisSuper rs : ew) {
                      if (rs instanceof EVatwpis1) {
                          dodajwiersztabeliEVatwpis1(table, rs, i);
                      } else {
                          dodajwiersztabeli(table, rs, i);
                      }
                      i++;
            }
            return table;
        } catch (Exception e) {
            E.e(e); 
            return null;
        }

    }
}
