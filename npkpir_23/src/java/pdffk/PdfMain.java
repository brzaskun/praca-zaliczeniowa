/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfGrafika.prost;
import beansPdf.PdfHeaderFooter;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import embeddable.ZestawienieRyczalt;
import embeddablefk.KontoKwota;
import entity.Faktura;
import entity.Podatnik;
import entity.Uz;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.Transakcja;
import entityfk.WierszBO;
import error.E;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import testobjects.WierszCecha;
import testobjects.WierszDokfk;
import testobjects.WierszKonta;
import testobjects.WierszTabeli;
import testobjects.WierszWNTWDT;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Stateless
public class PdfMain {
    
    public static final Font[] ft = czcionki();
    
    public static void main (String[] args) {
        Document document = inicjacjaA4Landscape();
        PdfWriter writer = inicjacjaWritera(document, "testowy2");
        naglowekStopkaL(writer);
        otwarcieDokumentu(document, "tytul pliku");
        //informacjaoZaksiegowaniu(document, "1233");
        //dodajDate(document, "2015-05-02");
        dodajOpisWstepny(document, testobjects.testobjects.getDokfk("PK"));
//        infooFirmie(document, testobjects.testobjects.getDokfk("PK"));
//        //dodajTabele(document, testobjects.testobjects.getTabela());
        dodajTabele(document, testobjects.testobjects.getTabelaKonta(testobjects.testobjects.getWiersze()), 100,0);
//        dodajpodpis(document,"Jan","Kowalski");
        //dodajStopke(writer);
        finalizacjaDokumentu(document);
        
    }
    
    
//    public static void main (String[] args) {
//        Document document = inicjacjaA4Portrait();
//        PdfWriter writer = inicjacjaWritera(document, "testowy");
//        otwarcieDokumentu(document, "tytul pliku");  
//        dodajNaglowek(writer);
//        informacjaoZaksiegowaniu(document, "1233");
//        dodajDate(document, "2015-05-02");
//        dodajOpisWstepny(document, testobjects.testobjects.getDokfk("PK"));
//        infooFirmie(document, testobjects.testobjects.getDokfk("PK"));
//        //dodajTabele(document, testobjects.testobjects.getTabela());
//        dodajTabele(document, testobjects.testobjects.getTabelaKonta(testobjects.testobjects.getWiersze()));
//        dodajpodpis(document,"Jan","Kowalski");
//        dodajStopke(writer);
//        finalizacjaDokumentu(document);
//    }
    
    public static Document inicjacjaA4Portrait() {
        return new Document(PageSize.A4, 20, 20, 40, 20);
    }
    
    public static Document inicjacjaA4Landscape() {
        return new Document(PageSize.A4_LANDSCAPE.rotate(), 20, 20, 40, 20);
    }
    
       
    private static Font[] czcionki() {
        try {
            Font[] czcionki = new Font[3];
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 11);
            Font fontM = new Font(helvetica, 9);
            Font fontS = new Font(helvetica, 5);
            czcionki[0] = fontS;
            czcionki[1] = fontM;
            czcionki[2] = font;
            return czcionki;
        } catch (DocumentException ex) {
            System.out.println("Problem z generowaniem czcionek");
            E.e(ex);
            return null;
        } catch (IOException ex) {
            System.out.println("Problem z generowaniem czcionek");
            E.e(ex);
            return null;
        }
    }

    public static PdfWriter inicjacjaWritera(Document document, String nazwapliku) {
        try {
            String nazwa = "C:/Users/Osito/Documents/NetBeansProjects/npkpir_23/build/web/wydruki/" + nazwapliku + ".pdf";
            File file = new File(nazwa);
            if (file.isFile()) {
                file.delete();
            }
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(nazwa));
            writer.setInitialLeading(16);
            writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
            return writer;
        } catch (FileNotFoundException ex) {
            System.out.println("Problem z zachowaniem pliku PDFMain inicjacjaWritera");
            E.e(ex);
            return null;
        } catch (DocumentException ex) {
            System.out.println("Problem z otwarciem dokumentu PDFMain inicjacjaWritera");
            E.e(ex);
            return null;
        }
    }
    
    public static void otwarcieDokumentu(Document document, String tytul) {
        document.addTitle(tytul);
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("Wydruk danych z programu księgowego");
        document.addKeywords("PKPiR, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
    }

    public static void finalizacjaDokumentu(Document document) {
        document.close();
    }

    public static void naglowekStopkaL(PdfWriter writer) {
        PdfHeaderFooter headerfoter = new PdfHeaderFooter(1);
        writer.setBoxSize("art", new Rectangle(842, 595, 0, 0));
        writer.setPageEvent(headerfoter);
    }
    
    public static void naglowekStopkaP(PdfWriter writer) {
        PdfHeaderFooter headerfoter = new PdfHeaderFooter(1);
        writer.setBoxSize("art", new Rectangle(595, 842, 0, 0));
        writer.setPageEvent(headerfoter);
    }
    
    public static void dodajNaglowek(PdfWriter writer) {
        absText(writer, "Biuro Rachunkowe Taxman - program księgowy online", 15, 820, 6);
        prost(writer.getDirectContent(), 12, 817, 560, 10);
    }
    
    public static void dodajDate(Document document, String datawystawienia) {
        try {
            Paragraph poledaty = new Paragraph(new Phrase("Szczecin, dnia " + datawystawienia, ft[2]));
            poledaty.setAlignment(Element.ALIGN_RIGHT);
            poledaty.setLeading(10);
            document.add(poledaty);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            System.out.println("Problem z dodaniem daty PDFMain dodajDate");
            E.e(ex);
        }
    }
    
    public static void dodajOpisWstepny(Document document, Dokfk selected) {
        try {
            Paragraph opiswstepny;
            switch (selected.getRodzajedok().getSkrot()) {
                case "PK":
                    opiswstepny = new Paragraph(new Phrase("Polecenie księgowania " + selected.getNumerwlasnydokfk(), ft[2]));
                    break;
                case "OT":
                    opiswstepny = new Paragraph(new Phrase("Przyjęcie środka trwałego " + selected.getNumerwlasnydokfk(), ft[2]));
                    break;
                case "RK":
                    opiswstepny = new Paragraph(new Phrase("Raport kasowy " + selected.getNumerwlasnydokfk(), ft[2]));
                    break;
                case "IN":
                    opiswstepny = new Paragraph(new Phrase("Polecenie księgowania " + selected.getNumerwlasnydokfk(), ft[2]));
                    break;
                case "AMO":
                    opiswstepny = new Paragraph(new Phrase("Umorzenie miesięczne " + selected.getNumerwlasnydokfk(), ft[2]));
                    break;
                case "DEL":
                    opiswstepny = new Paragraph(new Phrase("Delegacja numer " + selected.getNumerwlasnydokfk(), ft[2]));
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
                case "ZZ":
                case "SZ":
                    opiswstepny = new Paragraph(new Phrase("Faktura VAT " + selected.getNumerwlasnydokfk(), ft[2]));
                    break;
                case "WDT":
                    opiswstepny = new Paragraph(new Phrase("Faktura WDT " + selected.getNumerwlasnydokfk(), ft[2]));
                    break;
                case "WNT":
                    opiswstepny = new Paragraph(new Phrase("Faktura WNT " + selected.getNumerwlasnydokfk(), ft[2]));
                    break;
                case "WB":
                    opiswstepny = new Paragraph(new Phrase("Wyciąg bankowy " + selected.getNumerwlasnydokfk(), ft[2]));
                    break;
                default:
                    opiswstepny = new Paragraph(new Phrase("Zaksięgowany dok. nr " + selected.getNumerwlasnydokfk(), ft[2]));
                    break;
            }
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            document.add(opiswstepny);
            document.add(Chunk.NEWLINE);
            opiswstepny = new Paragraph(new Phrase("okres rozliczeniony " + selected.getMiesiac() + "/" + selected.getDokfkPK().getRok(), ft[1]));
            document.add(opiswstepny);
        } catch (DocumentException ex) {
            System.out.println("Problem z dodaniem opisu wstepnego PDFMain dodajOpisWstepny");
            E.e(ex);
        }
    }
    
    public static void dodajOpisWstepny(Document document, String opis, String mc, String rok) {
        try {
            Paragraph opiswstepny = new Paragraph(new Phrase(opis, ft[2]));
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            document.add(opiswstepny);
            document.add(Chunk.NEWLINE);
            opiswstepny = new Paragraph(new Phrase("okres rozliczeniony " + mc + "/" + rok, ft[1]));
            document.add(opiswstepny);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            System.out.println("Problem z dodaniem opisu wstepnego PDFMain dodajOpisWstepny(Document, Strin, String, String)");
            E.e(ex);
        }
    }
    
    public static void dodajOpisWstepny(Document document, String opis, String rok) {
        try {
            Paragraph opiswstepny = new Paragraph(new Phrase(opis, ft[2]));
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            document.add(opiswstepny);
            document.add(Chunk.NEWLINE);
            opiswstepny = new Paragraph(new Phrase("roku rozliczeniowy" + rok, ft[1]));
            document.add(opiswstepny);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            System.out.println("Problem z dodaniem opisu wstepnego PDFMain dodajOpisWstepny(Document, String)");
            E.e(ex);
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
            E.e(ex);
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
            int rodzajdok = selected.getRodzajedok().getKategoriadokumentu();
            if (rodzajdok != 0 && rodzajdok != 5) {
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
            E.e(ex);
        }
    }
    
    public static void saldopoczatkowe(Document document, Dokfk selected) {
        try {
            int rodzajdok = selected.getRodzajedok().getKategoriadokumentu();
            if (rodzajdok == 0) {
                document.add(Chunk.NEWLINE);
                NumberFormat currency = getCurrencyFormater();
                String saldo = String.valueOf(currency.format(selected.getSaldopoczatkowe()));
                Paragraph saldopoczatkowe = new Paragraph(new Phrase("saldo początkowe: " + saldo, ft[1]));
                document.add(saldopoczatkowe);
                document.add(Chunk.NEWLINE);
            }
        } catch (DocumentException ex) {
            System.out.println("Problem z dodaniem saldapoczatkowego PDFMain infooFirmie");
            E.e(ex);
        }
    }
    
    public static void saldokoncowe(Document document, Dokfk selected) {
        try {
            int rodzajdok = selected.getRodzajedok().getKategoriadokumentu();
            if (rodzajdok == 0) {
                document.add(Chunk.NEWLINE);
                NumberFormat currency = getCurrencyFormater();
                String saldo = String.valueOf(currency.format(selected.getSaldokoncowe()));
                Paragraph saldopoczatkowe = new Paragraph(new Phrase("saldo końcowe: " + saldo, ft[1]));
                document.add(saldopoczatkowe);
                document.add(Chunk.NEWLINE);
            }
        } catch (DocumentException ex) {
            System.out.println("Problem z dodaniem saldapoczatkowego PDFMain infooFirmie");
            E.e(ex);
        }
    }
    
    public static void dodajTabele(Document document, List[] tabela, int perc, int modyfikator) {
        try {
            List naglowki = tabela[0];
            List wiersze = tabela[1];
            String nazwaklasy = wiersze.get(0).getClass().getName();
            int[] col = obliczKolumny(naglowki.size(), nazwaklasy, modyfikator);
            PdfPTable table = przygotujtabele(naglowki.size(),col, perc);
            ustawnaglowki(table, naglowki);
            ustawwiersze(table,wiersze, nazwaklasy, modyfikator);
            document.add(table);
        } catch (DocumentException ex) {
            System.out.println("Problem z wstepnym przygotowaniem tabeli PDFMain dodajTabele");
            E.e(ex);
        }
    }
    
    private static int[] obliczKolumny(int size, String classname, int modyfikator) {
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
            case "testobjects.WierszWNTWDT":
                int[] col3 = new int[size];
                col3[0] = 1;
                col3[1] = 2;
                col3[2] = 2;
                col3[3] = 2;
                col3[4] = 3;
                col3[5] = 2;
                col3[6] = 2;
                col3[7] = 2;
                col3[8] = 2;
                col3[9] = 3;
                col3[10] = 2;
                col3[11] = 2;
                col3[12] = 3;
                return col3;
            case "testobjects.WierszCecha":
                int[] col4 = new int[size];
                col4[0] = 1;
                col4[1] = 2;
                col4[2] = 2;
                col4[3] = 2;
                col4[4] = 2;
                col4[5] = 2;
                col4[6] = 4;
                col4[7] = 4;
                col4[8] = 2;
                return col4;
            case "embeddable.ZestawienieRyczalt":
                col = new int[size];
                col[0] = 1;
                col[1] = 3;
                col[2] = 3;
                col[3] = 3;
                col[4] = 3;
                col[5] = 3;
                return col;
            case "testobjects.WierszDokfk":
                int[] col5 = new int[size];
                col5[0] = 1;
                col5[1] = 2;
                col5[2] = 2;
                col5[3] = 2;
                col5[4] = 4;
                col5[5] = 2;
                col5[6] = 3;
                col5[7] = 2;
                col5[8] = 1;
                return col5;
            case "entity.Faktura":
                int[] col6 = new int[size];
                col6[0] = 1;
                col6[1] = 2;
                col6[2] = 2;
                col6[3] = 4;
                col6[4] = 4;
                col6[5] = 2;
                col6[6] = 2;
                return col6;
            case "entityfk.PozycjaRZiS":
                 if (modyfikator==1) {
                    int[] col91 = new int[size];
                    int leveleB = size-3;
                    for (int i = 0; i < leveleB ; i++) {
                        col91[i] = 1;
                    }
                    col91[leveleB++] = 10;
                    col91[leveleB++] = 3;
                    col91[leveleB] = 7;
                    return col91;
                } else if (modyfikator==2) {
                    int[] col71 = new int[size];
                    int levele = size-2;
                    for (int i = 0; i < levele ; i++) {
                        col71[i] = 1;
                    }
                    col71[levele++] = 10;
                    col71[levele] = 8;
                    return col71;
                } else {
                    int[] col7 = new int[size];
                    int levele = size-2;
                    for (int i = 0; i < levele ; i++) {
                        col7[i] = 1;
                    }
                    col7[levele++] = 10;
                    col7[levele] = 3;
                    return col7;
                }
            case "entityfk.PozycjaBilans":
                if (modyfikator==1) {
                    int[] col9 = new int[size];
                    int leveleB = size-3;
                    for (int i = 0; i < leveleB ; i++) {
                        col9[i] = 1;
                    }
                    col9[leveleB++] = 10;
                    col9[leveleB++] = 3;
                    col9[leveleB] = 7;
                    return col9;
                } else if (modyfikator==2) {
                    int[] col10 = new int[size];
                    int leveleB = size-2;
                    for (int i = 0; i < leveleB ; i++) {
                        col10[i] = 1;
                    }
                    col10[leveleB++] = 10;
                    col10[leveleB] = 8;
                    return col10;
                } else {
                    int[] col8 = new int[size];
                    int leveleB = size-2;
                    for (int i = 0; i < leveleB ; i++) {
                        col8[i] = 1;
                    }
                    col8[leveleB++] = 10;
                    col8[leveleB] = 3;
                    return col8;
                }
            case "entityfk.Konto":
                if (modyfikator==1) {
                    int[] col10 = new int[size];
                    col10[0] = 1;
                    col10[1] = 2;
                    col10[2] = 4;
                    col10[3] = 3;
                    col10[4] = 3;
                    col10[5] = 3;
                    col10[6] = 3;
                    col10[7] = 3;
                    return col10;
                } else {
                    int[] col10 = new int[size];
                    col10[0] = 3;
                    col10[1] = 9;
                    col10[2] = 6;
                    col10[3] = 3;
                    col10[4] = 3;
                    col10[5] = 2;
                    col10[6] = 3;
                    col10[7] = 3;
                    return col10;
                }
            case "entityfk.Transakcja":
                int[] col13 = new int[size];
                col13[0] = 1;
                col13[1] = 5;
                col13[2] = 2;
                col13[3] = 5;
                col13[4] = 2;
                col13[5] = 3;
                col13[6] = 3;
                col13[7] = 3;
                return col13;
            case "entityfk.WierszBO":
                int[] col14 = new int[size];
                col14[0] = 1;
                col14[1] = 6;
                col14[2] = 3;
                col14[3] = 2;
                col14[4] = 2;
                col14[5] = 3;
                col14[6] = 3;
                col14[7] = 3;
                col14[8] = 3;
                return col14;
        }
        return null;
    }
    
    private static PdfPTable przygotujtabele(int size, int[] col, int perc) {
        try {
            PdfPTable p = new PdfPTable(size);
            p.setWidths(col);
            p.setWidthPercentage(perc);
            p.setSpacingBefore(2f);
            p.setSpacingAfter(3f);
            return p;
        } catch (DocumentException ex) {
            System.out.println("Problem z wstepnym przygotowaniem tabeli PDFMain przygotujtabele");
            E.e(ex);
            return null;
        }
    }
    
    private static NumberFormat getCurrencyFormater() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        return formatter;
    }
    
    private static NumberFormat getNumberFormater() {
        NumberFormat formatter = NumberFormat.getNumberInstance();
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
            E.e(ex);
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
        for (int i = 0; i < naglowki.size(); i++) {
            table.addCell(ustawfrazeAlign((String) naglowki.get(i), "center", 9));
        }
        table.setHeaderRows(1);
    }
    

    private static void ustawwiersze(PdfPTable table, List wiersze, String nazwaklasy, int modyfikator) {
        NumberFormat currency = getCurrencyFormater();
        NumberFormat number = getNumberFormater();
        int i = 1;
        for (Iterator it = wiersze.iterator(); it.hasNext();) {
            if (nazwaklasy.equals("testobjects.WierszTabeli")) {
                WierszTabeli p = (WierszTabeli) it.next();
                table.addCell(ustawfrazeAlign(String.valueOf(p.getLp()), "center", 8));
                table.addCell(ustawfrazeAlign(p.getOpis(), "left", 8));
                if (p.getWartosc() != 0.0) {
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getWartosc())), "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign("", "right", 8));
                }
            }
            if (nazwaklasy.equals("entityfk.PozycjaRZiS")) {
                int maxlevel = 0;
                for (Iterator<PozycjaRZiS> itX = wiersze.iterator(); itX.hasNext();) {
                    PozycjaRZiS s = (PozycjaRZiS) itX.next();
                    if (s.getLevel() > maxlevel) {
                        maxlevel = s.getLevel();
                    }
                }
                PozycjaRZiSBilans p = (PozycjaRZiSBilans) it.next();
                int levelPlus = p.getLevel()+1;
                if (p.getLevel() != 0) {
                    for (int j = 0; j < p.getLevel(); j++) {
                        table.addCell(ustawfrazeAlign("", "l", 7));
                    }
                }
                table.addCell(ustawfrazeAlign(p.getPozycjaSymbol(), "center", 7));
                if (p.getLevel() < maxlevel) {
                    for (int k = levelPlus; k <= maxlevel; k++) {
                        table.addCell(ustawfrazeAlign("", "l", 7));
                    }
                }
                if (p.getLevel() == 0) {
                    table.addCell(ustawfrazeAlign(p.getNazwa(), "left", 8));
                } else {
                    table.addCell(ustawfrazeAlign(p.getNazwa(), "left", 7));
                }
                if (modyfikator != 2) {
                    if (p.getKwota() != 0.0) {
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 7));
                    } else {
                        table.addCell(ustawfrazeAlign("", "right", 7));
                    }
                }
                if (modyfikator != 0) {
                    String konta = p.getPrzyporzadkowanekonta() != null ? p.getPrzyporzadkowanekonta().toString() : "";
                    table.addCell(ustawfrazeAlign(konta, "left", 7));
                }
            }
            if (nazwaklasy.equals("entityfk.PozycjaBilans")) {
                    int maxlevel = 0;
                    for (Iterator<PozycjaBilans> itX = wiersze.iterator(); itX.hasNext();) {
                        PozycjaBilans s = (PozycjaBilans) itX.next();
                        if (s.getLevel() > maxlevel) {
                            maxlevel = s.getLevel();
                        }
                    }
                    PozycjaRZiSBilans p = (PozycjaRZiSBilans) it.next();
                    int levelPlus = p.getLevel()+1;
                    if (p.getLevel() != 0) {
                        for (int j = 0; j < p.getLevel(); j++) {
                            table.addCell(ustawfrazeAlign("", "l", 7));
                        }
                    }
                    table.addCell(ustawfrazeAlign(p.getPozycjaSymbol(), "center", 7));
                    if (p.getLevel() < maxlevel) {
                        for (int k = levelPlus; k <= maxlevel; k++) {
                            table.addCell(ustawfrazeAlign("", "l", 7));
                        }   
                    }
                    if (p.getLevel() == 0) {
                        table.addCell(ustawfrazeAlign(p.getNazwa(), "left", 8));
                    } else {
                        table.addCell(ustawfrazeAlign(p.getNazwa(), "left", 7));
                    }
                    if (modyfikator != 2) {
                        if (p.getKwota() != 0.0) {
                            table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 7));
                        } else {
                            table.addCell(ustawfrazeAlign("", "right", 7));
                        }
                    }
                    if (modyfikator != 0) {
                        String konta = "";
                        if (p.getPrzyporzadkowanekonta() != null && p.getPrzyporzadkowanekonta().size() > 0) {
                            for (KontoKwota u : p.getPrzyporzadkowanekonta()) {
                                konta = konta+u.getKonto().getPelnynumer()+": "+Z.z(u.getKwota())+"; ";
                            }
                        }
                        table.addCell(ustawfrazeAlign(konta, "right", 7));
                    }
            }
            if (nazwaklasy.equals("testobjects.WierszCecha")) {
                WierszCecha p = (WierszCecha) it.next();
                table.addCell(ustawfrazeAlign(String.valueOf(p.getId()), "center", 8));
                table.addCell(ustawfrazeAlign(p.getNazwacechy(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getRodzajcechy(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getDokfks(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getDatawystawienia(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getDataoperacji(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getOpisWiersza(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getOpiskonta(), "center", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 8));
            }
            if (nazwaklasy.equals("testobjects.WierszDokfk")) {
                WierszDokfk p = (WierszDokfk) it.next();
                table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 7));
                table.addCell(ustawfrazeAlign(p.getDatadok(), "center", 7));
                table.addCell(ustawfrazeAlign(p.getDataoperacji(), "center", 7));
                table.addCell(ustawfrazeAlign(p.getIddok(), "center", 7));
                table.addCell(ustawfrazeAlign(p.getKontrahent(), "left", 7));
                table.addCell(ustawfrazeAlign(p.getNrwlasny(), "left", 7));
                table.addCell(ustawfrazeAlign(p.getOpis(), "left", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getWartosc())), "right", 7));
                table.addCell(ustawfrazeAlign(p.getWaluta(), "center", 7));
            }
            if (nazwaklasy.equals("entityfk.Konto")) {
                if (modyfikator == 1) {
                    Konto p = (Konto) it.next();
                    table.addCell(ustawfrazeAlign(i++, "left", 8));
                    table.addCell(ustawfrazeAlign(p.getPelnynumer(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getNazwapelna(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getZwyklerozrachszczegolne(), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getKontopozycjaID().getPozycjaWn(), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getKontopozycjaID().getPozycjaMa(), "center", 8));
                    double kwota = p.getBoWn() > 0 ? p.getBoWn(): 0;
                    if (kwota > 0) {
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                    kwota = p.getBoMa() > 0 ? p.getBoMa() : 0;
                    if (kwota > 0) {
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                } else {
                    Konto p = (Konto) it.next();
                    table.addCell(ustawfrazeAlign(p.getPelnynumer(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getNazwapelna(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getNazwaskrocona(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getBilansowewynikowe(), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getZwyklerozrachszczegolne(), "center", 8));
                    table.addCell(ustawfrazeAlign(p.isMapotomkow() == true ? "T": "N", "center", 8));
                    table.addCell(ustawfrazeAlign(p.getKontopozycjaID() != null ? p.getKontopozycjaID().getPozycjaWn() : "", "left", 8));
                    table.addCell(ustawfrazeAlign(p.getKontopozycjaID() != null ? p.getKontopozycjaID().getPozycjaMa() : "", "left", 8));
                }
            }
            if (nazwaklasy.equals("entityfk.WierszBO")) {
                WierszBO p = (WierszBO) it.next();
                table.addCell(ustawfrazeAlign(i++, "left", 7));
                table.addCell(ustawfrazeAlign(p.getKonto().getPelnynumer()+" "+p.getKonto().getNazwapelna(), "left", 7));
                table.addCell(ustawfrazeAlign(p.getWierszBOPK().getOpis(), "left", 7));
                double kwota = p.getKurs();
                if (kwota > 0) {
                    table.addCell(ustawfrazeAlign(number.format(kwota), "center", 7));
                } else {
                    table.addCell(ustawfrazeAlign("", "center", 7));
                }
                String waluta = p.getWaluta().getSymbolwaluty();
                table.addCell(ustawfrazeAlign(waluta, "center", 7));
                kwota = p.getKwotaWn(); 
                if (kwota > 0) {
                    table.addCell(ustawfrazeAlign(number.format(kwota), "center", 7));
                } else {
                    table.addCell(ustawfrazeAlign("", "center", 7));
                }
                kwota = p.getKwotaWnPLN();
                if (kwota > 0 && !waluta.equals("PLN")) {
                    table.addCell(ustawfrazeAlign(number.format(kwota), "center", 7));
                } else {
                    table.addCell(ustawfrazeAlign("", "center", 7));
                }
                kwota = p.getKwotaMa();
                if (kwota > 0) {
                    table.addCell(ustawfrazeAlign(number.format(kwota), "right", 7));
                } else {
                    table.addCell(ustawfrazeAlign("", "center", 7));
                }
                kwota = p.getKwotaMaPLN();
                if (kwota > 0 && !waluta.equals("PLN")) {
                    table.addCell(ustawfrazeAlign(number.format(kwota), "right", 7));
                } else {
                    table.addCell(ustawfrazeAlign("", "center", 7));
                }
            }
            if (nazwaklasy.equals("entityfk.Transakcja")) {
                Transakcja p = (Transakcja) it.next();
                table.addCell(ustawfrazeAlign(i++, "center", 8));
                String rachunek = p.getNowaTransakcja().getWiersz().getOpisWiersza()+"/"+p.getNowaTransakcja().getWiersz().getDokfkS();
                table.addCell(ustawfrazeAlign(rachunek, "left", 8));
                double kurs = p.getNowaTransakcja().getKursBO() != 0.0 ? p.getNowaTransakcja().getKursBO() : p.getNowaTransakcja().getWiersz().getTabelanbp().getKurssredni();
                table.addCell(ustawfrazeAlign(number.format(kurs), "right", 8));
                String platnosc = p.getRozliczajacy().getWiersz().getOpisWiersza()+"/"+p.getRozliczajacy().getWiersz().getDokfkS();
                table.addCell(ustawfrazeAlign(platnosc, "left", 8));
                kurs = p.getRozliczajacy().getKursBO() != 0.0 ? p.getRozliczajacy().getKursBO() : p.getRozliczajacy().getWiersz().getTabelanbp().getKurssredni();
                table.addCell(ustawfrazeAlign(number.format(kurs), "right", 8));
                table.addCell(ustawfrazeAlign(p.getDatarozrachunku(), "center", 8));
                table.addCell(ustawfrazeAlign(number.format(p.getRoznicekursowe()), "right", 8));
                table.addCell(ustawfrazeAlign(p.getNowaTransakcja().getKonto().getPelnynumer(), "right", 8));
            }
            if (nazwaklasy.equals("testobjects.WierszKonta")) {
                if (modyfikator == 0) {
                    WierszKonta p = (WierszKonta) it.next();
                    table.addCell(ustawfrazeAlign(String.valueOf(p.getLp()), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getOpis(), "left", 8));
                        if (p.getKwotaWn() != 0.0) {
                            table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwotaWn())), "right", 8));
                        } else {
                            table.addCell(ustawfrazeAlign("", "right", 8));
                        }
                        table.addCell(ustawfrazeAlign(p.getOpiskontaWn(), "left", 8));
                        if (p.getKwotaMa() != 0.0) {
                            table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwotaMa())), "right", 8));
                        } else {
                            table.addCell(ustawfrazeAlign("", "right", 8));
                        }
                        table.addCell(ustawfrazeAlign(p.getOpiskontaMa(), "left", 8));
                } else {
                    WierszKonta p = (WierszKonta) it.next();
                    table.addCell(ustawfrazeAlign(String.valueOf(p.getLp()), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getOpis(), "left", 8));
                        if (p.getKwotaWn() != 0.0) {
                            table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwotaWn())), "right", 8));
                        } else {
                            table.addCell(ustawfrazeAlign("", "right", 8));
                        }
                        table.addCell(ustawfrazeAlign(p.getOpiskontaWn(), "left", 8));
                        if (p.getKwotaMa() != 0.0) {
                            table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwotaMa())), "right", 8));
                        } else {
                            table.addCell(ustawfrazeAlign("", "right", 8));
                        }
                        table.addCell(ustawfrazeAlign(p.getOpiskontaMa(), "left", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getSaldo())), "right", 8));
                }
            }
            if (nazwaklasy.equals("testobjects.WierszWNTWDT")) {
                WierszWNTWDT p = (WierszWNTWDT) it.next();
                table.addCell(ustawfrazeAlign(String.valueOf(p.getId()), "center", 8));
                table.addCell(ustawfrazeAlign(p.getData(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getIddok(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getNrwlasny(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getOpis(), "left", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKg())), "right", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getSzt())), "right", 8));
                if (p.getOpis().equals("podsumowanie")) {
                    table.addCell(ustawfrazeAlign("", "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwotaWn())), "right", 8));
                }
                table.addCell(ustawfrazeAlign(String.valueOf(currency.format(p.getKwotaWnPLN())), "right", 8));
                table.addCell(ustawfrazeAlign(p.getOpiskontaWn(), "left", 8));
                if (p.getOpis().equals("podsumowanie")) {
                    table.addCell(ustawfrazeAlign("", "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwotaMa())), "right", 8));
                }
                table.addCell(ustawfrazeAlign(String.valueOf(currency.format(p.getKwotaMaPLN())), "right", 8));
                table.addCell(ustawfrazeAlign(p.getOpiskontaMa(), "left", 8));
            }
            if (nazwaklasy.equals("entity.Faktura")) {
                Faktura p = (Faktura) it.next();
                table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 8));
                table.addCell(ustawfrazeAlign(p.getDatawystawienia(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getFakturaPK().getNumerkolejny(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getKontrahent().getNpelna()+" "+p.getKontrahent().getNip(), "left", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(p.getPozycjenafakturze().get(0).getNazwa()), "center", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getBrutto())), "right", 8));
                table.addCell(ustawfrazeAlign(p.getTerminzaplaty(), "center", 8));
            }
            if (nazwaklasy.equals("embeddable.ZestawienieRyczalt")) {
                ZestawienieRyczalt p = (ZestawienieRyczalt) it.next();
                table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 8));
                table.addCell(ustawfrazeAlign(p.getOkres(), "left", 8));
                if (p.getS170() > 0) {
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getS170())), "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign("", "left", 8));
                }
                if (p.getS85() > 0) {
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getS85())), "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign("", "left", 8));
                }
                if (p.getS55() > 0) {
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getS55())), "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign("", "left", 8));
                }
                if (p.getS30() > 0) {
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getS30())), "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign("", "left", 8));
                }
            }
        }
    }
    
}
