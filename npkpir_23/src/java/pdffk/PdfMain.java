/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfGrafika.prost;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Podatnik;
import entity.Uz;
import entityfk.Dokfk;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import testobjects.WierszKonta;
import testobjects.WierszTabeli;

/**
 *
 * @author Osito
 */
@Stateless
public class PdfMain {
    
    public static final Font[] ft = czcionki();
    
    
    
    public static void main (String[] args) {
        Document document = inicjacjaDokumentu();
        PdfWriter writer = inicjacjaWritera(document, "testowy", "tytul pliku");
        dodajNaglowek(writer);
        informacjaoZaksiegowaniu(document, "1233");
        dodajDate(document, "2015-05-02");
        dodajOpisWstepny(document, testobjects.testobjects.getDokfk("PK"));
        infooFirmie(document, testobjects.testobjects.getDokfk("PK"));
        //dodajTabele(document, testobjects.testobjects.getTabela());
        //dodajTabele(document, testobjects.testobjects.getTabelaKonta());
        dodajpodpis(document,"Jan","Kowalski");
        dodajStopke(writer);
        finalizacjaDokumentu(document);
    }
    
    public static Document inicjacjaDokumentu() {
        return new Document(PageSize.A4, 20, 20, 40, 20);
    }
    
       
    private static Font[] czcionki() {
        try {
            Font[] czcionki = new Font[3];
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 12);
            Font fontM = new Font(helvetica, 10);
            Font fontS = new Font(helvetica, 6);
            czcionki[0] = fontS;
            czcionki[1] = fontM;
            czcionki[2] = font;
            return czcionki;
        } catch (DocumentException ex) {
            System.out.println("Problem z generowaniem czcionek");
            Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (IOException ex) {
            System.out.println("Problem z generowaniem czcionek");
            Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static PdfWriter inicjacjaWritera(Document document, String nazwapliku, String tytul) {
        try {
            String nazwa = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/" + nazwapliku + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nazwa));
            writer.setInitialLeading(16);
            writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
            document.addTitle(tytul);
            document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
            document.addSubject("Wydruk danych z programu księgowego");
            document.addKeywords("PKPiR, PDF");
            document.addCreator("Grzegorz Grzelczyk");
            document.open();
            return writer;
        } catch (FileNotFoundException ex) {
            System.out.println("Problem z zachowaniem pliku PDFMain inicjacjaWritera");
            Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (DocumentException ex) {
            System.out.println("Problem z otwarciem dokumentu PDFMain inicjacjaWritera");
            Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    public static void finalizacjaDokumentu(Document document) {
        document.close();
    }

    public static void dodajNaglowek(PdfWriter writer) {
        absText(writer, "Biuro Rachunkowe Taxman - program księgowy online", 15, 820, 6);
        prost(writer.getDirectContent(), 12, 817, 560, 10);
    }
    
    public static void dodajDate(Document document, String datawystawienia) {
        try {
            Paragraph poledaty = new Paragraph(new Phrase("Szczecin, dnia " + datawystawienia, ft[2]));
            poledaty.setAlignment(Element.ALIGN_RIGHT);
            poledaty.setLeading(50);
            document.add(poledaty);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            System.out.println("Problem z dodaniem daty PDFMain dodajDate");
            Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void dodajOpisWstepny(Document document, Dokfk selected) {
        try {
            Paragraph opiswstepny;
            switch (selected.getRodzajedok().getSkrot()) {
                case "PK":
                case "OT":
                case "IN":
                    opiswstepny = new Paragraph(new Phrase("Polecenie księgowania " + selected.getNumerwlasnydokfk(), ft[2]));
                    break;
                case "AMO":
                    opiswstepny = new Paragraph(new Phrase("Umorzenie miesięczne " + selected.getNumerwlasnydokfk(), ft[2]));
                    break;
                case "LP":
                    opiswstepny = new Paragraph(new Phrase("Lista płac " + selected.getNumerwlasnydokfk(), ft[2]));
                    break;
                case "ZUS":
                    opiswstepny = new Paragraph(new Phrase("Ubezpieczenia społeczne " + selected.getNumerwlasnydokfk(), ft[2]));
                    break;
                case "RF":
                    opiswstepny = new Paragraph(new Phrase("Zestawienie - kasa fiskalna " + selected.getNumerwlasnydokfk(), ft[2]));
                    break;
                default:
                    opiswstepny = new Paragraph(new Phrase("Faktura VAT " + selected.getNumerwlasnydokfk(), ft[2]));
                    break;
            }
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            document.add(opiswstepny);
            document.add(Chunk.NEWLINE);
            opiswstepny = new Paragraph(new Phrase("okres rozliczeniony " + selected.getMiesiac() + "/" + selected.getDokfkPK().getRok(), ft[1]));
            document.add(opiswstepny);
        } catch (DocumentException ex) {
            System.out.println("Problem z dodaniem opisu wstepnego PDFMain dodajOpisWstepny");
            Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void informacjaoZaksiegowaniu(Document document, String lp) {
        try {
            document.add(new Chunk("Biuro Rachunkowe Taxman"));
            Paragraph zaksiegowany = new Paragraph(new Phrase("dokument zaksięgowany pod lp: " + lp, ft[1]));
            zaksiegowany.setAlignment(Element.ALIGN_LEFT);
            document.add(zaksiegowany);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            System.out.println("Problem z dodaniem informacj z lp PDFMain informacjaoZaksiegowaniu");
            Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void infooFirmie(Document document, Dokfk selected) {
        try {
            document.add(Chunk.NEWLINE);
            Paragraph danefirmy = new Paragraph(new Phrase("Firma: " + selected.getPodatnikObj().getNazwapelna(), ft[1]));
            document.add(danefirmy);
            Podatnik pod = selected.getPodatnikObj();
            danefirmy = new Paragraph(new Phrase("adres: " + pod.getMiejscowosc() + " " + pod.getUlica() + " " + pod.getNrdomu(), ft[1]));
            document.add(danefirmy);
            danefirmy = new Paragraph(new Phrase("NIP: " + pod.getNip(), ft[1]));
            document.add(danefirmy);
            String rodzajdok = selected.getRodzajedok().getSkrot();
            if (!rodzajdok.equals("PK") && !rodzajdok.equals("OT") && !rodzajdok.equals("IN") && !rodzajdok.equals("ZUS") && !rodzajdok.equals("AMO")) {
                document.add(Chunk.NEWLINE);
                danefirmy = new Paragraph(new Phrase("Kontrahent: " + selected.getKontr().getNpelna(), ft[1]));
                document.add(danefirmy);
                danefirmy = new Paragraph(new Phrase("adres: " + selected.getKontr().getMiejscowosc() + " " + selected.getKontr().getUlica() + " " + selected.getKontr().getDom(), ft[1]));
                document.add(danefirmy);
                danefirmy = new Paragraph(new Phrase("NIP: " + selected.getKontr().getNip(), ft[1]));
                document.add(danefirmy);
            }
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            System.out.println("Problem z dodaniem informacji o firmie PDFMain infooFirmie");
            Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void dodajTabele(Document document, List[] tabela) {
        try {
            List naglowki = tabela[0];
            List wiersze = tabela[1];
            String nazwaklasy = wiersze.get(0).getClass().getName();
            int[] col = obliczKolumny(naglowki.size(), nazwaklasy);
            PdfPTable table = przygotujtabele(naglowki.size(),col);
            ustawnaglowki(table, naglowki);
            ustawwiersze(table,wiersze, nazwaklasy);
            document.add(table);
        } catch (DocumentException ex) {
            System.out.println("Problem z wstepnym przygotowaniem tabeli PDFMain dodajTabele");
            Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static int[] obliczKolumny(int size, String classname) {
        switch (classname) {
            case "testobjects.WierszTabeli":
                int[] col = new int[size];
                col[0] = 1;
                col[1] = 5;
                for (int i = 2; i < size; i++) {
                    col[i] = 2;
                }
                return col;
            case "testobjects.WierszKonta":
                int[] col2 = new int[size];
                col2[0] = 1;
                col2[1] = 5;
                for (int i = 2; i < size; i++) {
                    col2[i] = 3;
                }
                return col2;
        }
        return null;
    }
    
    private static PdfPTable przygotujtabele(int size, int[] col) {
        try {
            PdfPTable p = new PdfPTable(size);
            p.setWidths(col);
            p.setWidthPercentage(100);
            p.setSpacingBefore(2f);
            p.setSpacingAfter(3f);
            return p;
        } catch (DocumentException ex) {
            System.out.println("Problem z wstepnym przygotowaniem tabeli PDFMain przygotujtabele");
            Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    private static NumberFormat getNumFormater() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        return formatter;
    }
    
    public static void dodajpodpis(Document document, String imie, String nazwisko) {
        try {
            document.add(Chunk.NEWLINE);
            document.add(new Paragraph(String.valueOf(imie + " " + nazwisko), ft[1]));
            document.add(new Paragraph("___________________________", ft[1]));
            document.add(new Paragraph("sporządził", ft[1]));
        } catch (DocumentException ex) {
            System.out.println("Problem z podpisem PDFMain dodajpodpis");
            Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static void dodajStopke(PdfWriter writer) {
        absText(writer, "Dokument wygenerowano elektronicznie w autorskim programie księgowym Biura Rachunkowego Taxman.", 15, 26, 6);
        absText(writer, "Dokument nie wymaga podpisu.", 15, 18, 6);
        prost(writer.getDirectContent(), 12, 15, 560, 20);
    }

    private static void absText(PdfWriter writer, String text, int x, int y) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
            cb.saveState();
            cb.beginText();
            cb.moveText(x, y);
            cb.setFontAndSize(bf, 12);
            cb.showText(text);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
        }
    }

    private static void absText(PdfWriter writer, String text, int x, int y, String f) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
            cb.saveState();
            cb.beginText();
            try {
                Integer.parseInt(text);
                int dl = text.length();
                if (dl > 6) {
                    text = text.substring(0, 1) + " " + text.substring(1, 4) + " " + text.substring(4);
                } else if (dl > 3 && dl <= 6) {
                    text = text.substring(0, dl - 3) + " " + text.substring(dl - 3);
                    x += 6 * (7 - dl);
                } else {
                    x += 6 * (7.5 - dl);
                }
            } catch (Exception e) {
            }
            cb.moveText(x, y);
            cb.setFontAndSize(bf, 12);
            cb.showText(text);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
        }
    }

    private static void absText(PdfWriter writer, String text, int x, int y, int font) {
        try {
            PdfContentByte cb = writer.getDirectContent();
            BaseFont bf = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.NOT_EMBEDDED);
            cb.saveState();
            cb.beginText();
            cb.moveText(x, y);
            cb.setFontAndSize(bf, font);
            cb.showText(text);
            cb.endText();
            cb.restoreState();
        } catch (DocumentException | IOException e) {
        }
    }

    private static void ustawnaglowki(PdfPTable table, List naglowki) {
        try {
            for (int i = 0; i < naglowki.size(); i++) {
                table.addCell(ustawfrazeAlign((String) naglowki.get(i), "center", 10));
            }
            table.setHeaderRows(1);
        } catch (DocumentException ex) {
            System.out.println("Problem z wstepnym przygotowaniem tabeli PDFMain ustawnaglowki");
            Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("Problem z wstepnym przygotowaniem tabeli PDFMain ustawnaglowki");
            Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static void ustawwiersze(PdfPTable table, List wiersze, String nazwaklasy) {
        NumberFormat formatter = getNumFormater();
        for (Iterator it = wiersze.iterator(); it.hasNext();) {
            try {
                if (nazwaklasy.equals("testobjects.WierszTabeli")) {
                    WierszTabeli p = (WierszTabeli) it.next();
                    table.addCell(ustawfrazeAlign(String.valueOf(p.getLp()), "center", 10));
                    table.addCell(ustawfrazeAlign(p.getOpis(), "left", 10));
                    table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getWartosc())), "right", 10));
                }
                if (nazwaklasy.equals("testobjects.WierszKonta")) {
                    WierszKonta p = (WierszKonta) it.next();
                    table.addCell(ustawfrazeAlign(String.valueOf(p.getLp()), "center", 10));
                    table.addCell(ustawfrazeAlign(p.getOpis(), "left", 10));
                    table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getKwotawn())), "right", 10));
                    table.addCell(ustawfrazeAlign(p.getOpiskontawn(), "left", 10));
                    table.addCell(ustawfrazeAlign(String.valueOf(formatter.format(p.getKwotama())), "right", 10));
                    table.addCell(ustawfrazeAlign(p.getOpiskontama(), "left", 10));
                }
            } catch (DocumentException ex) {
                System.out.println("Problem z wstepnym przygotowaniem tabeli PDFMain ustawwiersze");
                Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                System.out.println("Problem z wstepnym przygotowaniem tabeli PDFMain ustawwiersze");
                Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
