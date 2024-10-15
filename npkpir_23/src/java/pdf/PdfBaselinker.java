/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import beansPdf.PdfFont;
import static beansPdf.PdfFont.ustawfrazeAlign;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.AmazonBaselinkerRecord;
import entity.Dok;
import entity.Klienci;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.primefaces.PrimeFaces;
import plik.Plik;
 import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */

public class PdfBaselinker  {

    public static void drukuj(List<AmazonBaselinkerRecord> records, WpisView wpisView) throws DocumentException, FileNotFoundException, IOException {
         Map<String, Map<String, SummaryResult>> summary = sumujpozycje(records);
         System.out.println("koniec");
          // Ścieżka do pliku PDF, który chcemy wygenerować
        String pdfFilePath = "amazon_baselinker_summary.pdf";
        
        try {
            // Tworzenie dokumentu PDF
            Document document = new Document(PageSize.A4, 10,10,20,20);
            String nazwapliku = "amazon_baselinker_summary" + wpisView.getPodatnikObiekt().getNip() + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR(nazwapliku));
            
            // Otwieranie dokumentu do zapisu
            document.open();
            
            // Dodanie tytułu do dokumentu
            document.add(new Paragraph("Podsumowanie wartości wg kraju i numeru", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
            document.add(Chunk.NEWLINE);
            
            // Tworzenie tabeli PDF (5 kolumn: Kraj, Pelny Numer, Netto, VAT, Kwota VAT, Brutto)
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(75);
           table.setWidths(new int[]{3, 2, 3, 2, 2, 2});
            
            // Nagłówki tabeli
            addTableHeader(table);

            // Dodanie wierszy z podsumowaniem
            summary.forEach((kraj, pelnyNumerMap) -> {
                pelnyNumerMap.forEach((pelnyNumer, result) -> {
                    addSummaryRow(table, kraj, pelnyNumer, result);
                });
            });
            
            // Dodanie tabeli do dokumentu
            document.add(table);
            
            // Zamknięcie dokumentu
            document.close();
            PrimeFaces.current().executeScript("wydrukCSV('" + nazwapliku+"');");
            System.out.println("Plik PDF został wygenerowany: " + pdfFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        Document pdf = new Document(PageSize.A4_LANDSCAPE.rotate(), -20, -20, 20, 10);
//        String nazwapliku = "obroty" + wpisView.getPodatnikWpisu() + ".pdf";
//        PdfWriter writer = PdfWriter.getInstance(pdf, Plik.plikR(nazwapliku));
//        int liczydlo = 1;
//        PdfHeaderFooter headerfoter = new PdfHeaderFooter(liczydlo);
//        writer.setBoxSize("art", new Rectangle(1500, 600, 0, 0));
//        writer.setPageEvent(headerfoter);
//        pdf.addTitle("Obroty z kontrahentami");
//        pdf.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
//        pdf.addSubject("Wydruk danych z PKPiR");
//        pdf.addKeywords("PKPiR, PDF");
//        pdf.addCreator("Grzegorz Grzelczyk");
//        pdf.open();
//        BaseFont helvetica = null;
//        try {
//            helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
//        } catch (IOException ex) {
//            // Logger.getLogger(Pdf.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        Font font = new Font(helvetica, 8);
//        pdf.setPageSize(PageSize.A4);
//        PdfPTable table = new PdfPTable(12);
//        table.setWidthPercentage(95);
//        table.setWidths(new int[]{1, 2, 5, 4, 2, 1, 2, 2, 2, 2,2,3});
//        PdfPCell cell = new PdfPCell();
//        table.addCell(ustawfrazeAlign("nr kolejny", "center",8));
//        table.addCell(ustawfrazeAlign("data wystawienia", "center",8));
//        table.addCell(ustawfrazeAlign("kontrahent", "center",8));
//        table.addCell(ustawfrazeAlign("adres", "center",8));
//        table.addCell(ustawfrazeAlign("transakcja", "center",8));
//        table.addCell(ustawfrazeAlign("symb. dok.", "center",8));
//        table.addCell(ustawfrazeAlign("nr własny", "center",8));
//        table.addCell(ustawfrazeAlign("opis", "center",8));
//        table.addCell(ustawfrazeAlign("netto", "center",8));
//        table.addCell(ustawfrazeAlign("brutto", "center",8));
//        table.addCell(ustawfrazeAlign("netto wal.", "center",8));
//        table.addCell(ustawfrazeAlign("tabela", "center",8));
//        table.setHeaderRows(1);
//
//        Object[] suma = obliczsume(goscwybral);
//        goscwybral.add((Dok) suma[0]);
//        for (Dok rs : goscwybral) {
//            if (rs.getNrWpkpir() != 0) {
//                table.addCell(ustawfrazeAlign(String.valueOf(rs.getNrWpkpir()), "center",8));
//            } else {
//                table.addCell(ustawfrazeAlign("", "center",8));
//            }
//            table.addCell(ustawfrazeAlign(rs.getDataWyst(), "left",8));
//            table.addCell(ustawfrazeAlign(rs.getKontr().getNpelna(), "left",8));
//            table.addCell(ustawfrazeAlign(rs.getKontr().getAdres(), "left",7));
//            table.addCell(ustawfrazeAlign(rs.getRodzajedok() != null ? rs.getRodzajedok().getSkrot():"", "left",8));
//            table.addCell(ustawfrazeAlign(rs.getRodzajedok() != null ? rs.getRodzajedok().getRodzajtransakcji():"", "center",8));
//            table.addCell(ustawfrazeAlign(rs.getNrWlDk(), "left",8));
//            table.addCell(ustawfrazeAlign(rs.getOpis(), "left",8));
//            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getNetto()), "right",8));
//            table.addCell(ustawfrazeAlign(formatujWaluta(rs.getBrutto()), "right",8));
//            if (rs.getNrWpkpir() != 0) {
//                if (rs.getTabelanbp() == null || rs.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
//                    table.addCell(ustawfrazeAlign("", "right",8));
//                    table.addCell(ustawfrazeAlign("", "right",8));
//                } else if (!rs.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")){
//                    table.addCell(ustawfrazeAlign(formatujLiczba(rs.getNettoWaluta()), "right",8));
//                    table.addCell(ustawfrazeAlign(rs.getTabelanbp().getNrtabeli()+"/"+rs.getTabelanbp().getWaluta().getSymbolwaluty(), "right",7));
//                }
//            } else {
//                table.addCell(ustawfrazeAlign(formatujLiczba((Double) suma[1]), "right",8));
//                table.addCell(ustawfrazeAlign("", "right",8));
//            }
//        }
//        pdf.setPageSize(PageSize.A4_LANDSCAPE.rotate());
//        pdf.add(new Chunk());
//        Paragraph miziu1 = ustawparagraf("Wydruk obrotów z kontrahentem dla klienta: "+wpisView.getPodatnikObiekt().getNazwapelnaPDF()+" od "+wpisView.getMiesiacOd()+"/"+wpisView.getRokWpisu()+" do "+wpisView.getMiesiacDo()+"/"+wpisView.getRokWpisu());
//        miziu1.setAlignment(Element.ALIGN_CENTER);
//        pdf.add(miziu1);
//        pdf.add(Chunk.NEWLINE);
//        pdf.add(table);
//        pdf.addAuthor("Biuro Rachunkowe Taxman");
//        pdf.close();
//        pdffk.PdfMain.dodajQR(nazwapliku);
//        PrimeFaces.current().executeScript("wydrukobroty('"+wpisView.getPodatnikWpisu()+"');");
//        Msg.msg("i", "Wydrukowano obroty");
    }
    
    // Metoda do dodania nagłówków tabeli
    private static void addTableHeader(PdfPTable table) {
        Stream.of("Kraj", "Pełny Numer", "Netto", "VAT", "Kwota VAT", "Brutto")
            .forEach(columnTitle -> {
                table.addCell(ustawfrazeAlign(columnTitle,"center",9));
            });
    }

    // Metoda do dodania wiersza z podsumowaniem
    private static void addSummaryRow(PdfPTable table, String kraj, String pelnyNumer, SummaryResult result) {
        table.addCell(ustawfrazeAlign(kraj,"center",8));
        table.addCell(ustawfrazeAlign(pelnyNumer,"center",8));
        table.addCell(ustawfrazeAlign(PdfFont.formatujLiczba(Z.z(result.getNetto())), "right",8));
        table.addCell(ustawfrazeAlign(PdfFont.formatujLiczba(Z.z(result.getVat())), "right",8));
        table.addCell(ustawfrazeAlign(PdfFont.formatujLiczba(Z.z(result.getKwotaVat())), "right",8));
        table.addCell(ustawfrazeAlign(PdfFont.formatujLiczba(Z.z(result.getBrutto())), "right",8));
    }


    private static Object[] obliczsume(List<Dok> wykaz) {
        Object[] tab = new Object[2];
        Double nettosuma = 0.0;
        Double bruttosuma = 0.0;
        Double waluta = 0.0;
        for(Dok p : wykaz){
            nettosuma += p.getNetto() != null ? p.getNetto() : 0.0;
            bruttosuma += p.getBrutto() != null ? p.getBrutto() : 0.0;
            waluta += p.getNettoWaluta() != null ? p.getNettoWaluta() : 0.0;
        }
        Dok suma = new Dok();
        suma.setNrWpkpir(0);
        suma.setNrWlDk("");
        suma.setKontr(new Klienci());
        suma.setOpis("podsumowanie");
        suma.setNetto(nettosuma);
        suma.setBrutto(bruttosuma);
        tab[0] = suma;
        tab[1] = waluta;
        return tab;
    }
    
    public static  Map<String, Map<String, SummaryResult>> sumujpozycje(List<AmazonBaselinkerRecord> records) {
        // Grupa na podstawie kraju oraz drugiego elementu w pelnyNumer
        // Grupa na podstawie kraju oraz drugiego elementu w pelnyNumer z zabezpieczeniem przed null
        Map<String, Map<String, SummaryResult>> summary = records.stream()
            .collect(Collectors.groupingBy(
                record -> Optional.ofNullable(record.getKraj()).orElse("N/D"), // Zabezpieczenie przed null
                Collectors.groupingBy(record -> {
                    String[] parts = Optional.ofNullable(record.getPelnyNumer()).orElse("").split("/");
                    return parts.length > 1 ? parts[1] : "UNKNOWN"; // Zabezpieczenie przed brakiem elementu
                }, Collectors.collectingAndThen(
                        Collectors.toList(),
                        PdfBaselinker::sumValues
                ))
            ));

        // Wyświetlenie wyników
//        summary.forEach((kraj, pelnyNumerMap) -> {
//            System.out.println("Kraj: " + kraj);
//            pelnyNumerMap.forEach((pelnyNumer, result) -> {
//                System.out.println("  PelnyNumer: " + pelnyNumer);
//                System.out.println("    Netto: " + result.getNetto());
//                System.out.println("    VAT: " + result.getVat());
//                System.out.println("    Kwota VAT: " + result.getKwotaVat());
//                System.out.println("    Brutto: " + result.getBrutto());
//            });
//        });
        return summary;
    }

    // Metoda podsumowująca wartości liczbowe w grupie
    private static SummaryResult sumValues(List<AmazonBaselinkerRecord> records) {
        SummaryResult result = new SummaryResult();
        for (AmazonBaselinkerRecord record : records) {
            result.setNetto(result.getNetto() + record.getNetto());
            result.setVat(result.getVat() + record.getVat());
            result.setKwotaVat(result.getKwotaVat() + record.getKwotaVat());
            result.setBrutto(result.getBrutto() + record.getBrutto());
        }
        return result;
    }
}

// Klasa przechowująca wyniki podsumowania
class SummaryResult {
    private double netto;
    private double vat;
    private double kwotaVat;
    private double brutto;

    // Gettery i Settery
    public double getNetto() { return netto; }
    public void setNetto(double netto) { this.netto = netto; }

    public double getVat() { return vat; }
    public void setVat(double vat) { this.vat = vat; }

    public double getKwotaVat() { return kwotaVat; }
    public void setKwotaVat(double kwotaVat) { this.kwotaVat = kwotaVat; }

    public double getBrutto() { return brutto; }
    public void setBrutto(double brutto) { this.brutto = brutto; }
}

