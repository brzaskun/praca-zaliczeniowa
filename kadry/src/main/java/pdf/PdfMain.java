/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Angaz;
import entity.Definicjalistaplac;
import error.E;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.commons.lang3.RandomStringUtils;
import static pdf.PdfFont.ustawfrazeAlign;
import static pdf.PdfGrafika.prost;
import plik.Plik;

/**
 *
 * @author Osito
 */

public class PdfMain {
    
    public static final Font[] ft = czcionki();
    
//    public static void main (String[] args) {
//        Document document = inicjacjaA4Landscape();
//        PdfWriter writer = inicjacjaWritera(document, "testowy2");
//        naglowekStopkaL(writer);
//        otwarcieDokumentu(document, "tytul pliku");
//        //informacjaoZaksiegowaniu(document, "1233");
//        //dodajDate(document, "2015-05-02");
//        dodajOpisWstepny(document, testobjects.testobjects.getDokfk("PK"));
////        infooFirmie(document, testobjects.testobjects.getDokfk("PK"));
////        //dodajTabele(document, testobjects.testobjects.getTabela());
//        dodajTabele(document, testobjects.testobjects.getTabelaKonta(testobjects.testobjects.getWiersze()), 100,0);
////        dodajpodpis(document,"Jan","Kowalski");
//        //dodajStopke(writer);
//        finalizacjaDokumentuQR(document,nazwa);
//        
//    }
    
    
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
//        finalizacjaDokumentuQR(document,nazwa);
//    }
    
    public static Document inicjacjaA4Portrait() {
        return new Document(PageSize.A4, 20, 20, 40, 20);
    }
    
    public static Document inicjacjaA4Portrait(float left, float right) {
        return new Document(PageSize.A4,left, right, 40, 20);
    }
    
    public static Document inicjacjaA4Landscape() {
        return new Document(PageSize.A4_LANDSCAPE.rotate(), 20, 20, 40, 20);
    }
    
     public static Document inicjacjaA4Landscape(int left, int right, int top, int bottom) {
        return new Document(PageSize.A4_LANDSCAPE.rotate(), left, right, top, bottom);
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
            E.e(ex);
            return null;
        } catch (IOException ex) {
            E.e(ex);
            return null;
        }
    }

    public static PdfWriter inicjacjaWritera(Document document, String nazwa) {
        try {
            PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR(nazwa));
            writer.setInitialLeading(16);
            writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
            return writer;
        } catch (DocumentException ex) {
            E.e(ex);
            return null;
        }
    }
    
    public static PdfWriter inicjacjaWriteraTmp(Document document, String nazwapliku) {
        try {
            String nazwa = "D://"+nazwapliku + ".pdf";
            File file = Plik.plikTmp(nazwa, true);
            if (file.isFile()) {
                file.delete();
            }
            PdfWriter writer = PdfWriter.getInstance(document, Plik.plikRTmp(nazwa));
            writer.setInitialLeading(16);
            writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
            return writer;
        } catch (DocumentException ex) {
            E.e(ex);
            return null;
        }
    }
    
    public static PdfWriter inicjacjaWritera(Document document, String nazwapliku, int leading) {
        try {
            String nazwa = nazwapliku + ".pdf";
            File file = Plik.plik(nazwa, true);
            if (file.isFile()) {
                file.delete();
            }
            PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR(nazwa));
            writer.setInitialLeading(leading);
            writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
            return writer;
        } catch (DocumentException ex) {
            E.e(ex);
            return null;
        }
    }
    
    public static void otwarcieDokumentu(Document document, String tytul) {
        document.addTitle(tytul);
        document.addAuthor("Biuro Rachunkowe Taxman Grzegorz Grzelczyk");
        document.addSubject("lista płac klienta");
        document.addKeywords("LP, PDF");
        document.addCreator("Grzegorz Grzelczyk");
        document.open();
    }

    public static void finalizacjaDokumentu(Document document) {
        document.close();
    }
    
    public static void finalizacjaDokumentuQR(Document document, String nazwapliku) {
        document.close();
        dodajQR(nazwapliku);
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
            E.e(ex);
        }
    }
    
    public static void dodajQR(String nazwapliku) {
        try {
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            String nazwaplikuzbazy = realPath+"resources\\images\\taxman.jpg";
            String nazwa = nazwapliku;
            if (!nazwapliku.endsWith(".pdf")) {
                nazwapliku = nazwapliku+".pdf";
            }
            if (!nazwapliku.contains("/")) {
                nazwa = Plik.getKatalog()+nazwapliku;
            }
            File f = new File(nazwaplikuzbazy);
            if(f.exists()) {
                Image logo = Image.getInstance(nazwaplikuzbazy);
                logo.scaleToFit(50f, 50f);
                PdfReader reader = new PdfReader(nazwa);
                String plikzqr = Plik.getKatalog()+"nowa.pdf";
                PdfStamper pdfStamper = new PdfStamper(reader,new FileOutputStream(plikzqr));
                PdfContentByte content = pdfStamper.getUnderContent(reader.getNumberOfPages());
                logo.setAbsolutePosition(10, 10); //e
                content.addImage(logo);
                pdfStamper.close();
                reader.close();
                Plik.usun(nazwa);
                Plik.zapiszjako(plikzqr,nazwa);
            }
        } catch (Exception ex) {
            E.e(ex);
        }
   }
    
//    public static void main(String[] args) {
//        try {
//             ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
//            String realPath = ctx.getRealPath("/");
//            String nazwaplikuzbazy = realPath+"resources/images/logo/taxman.jpg";
//            String nazwa = Plik.getKatalog()+"2.pdf";
//            File f = new File(nazwaplikuzbazy);
//            if(f.exists()) {
//                Image logo = Image.getInstance(nazwaplikuzbazy);
//                logo.scaleToFit(50f, 50f);
//                PdfReader reader = new PdfReader(nazwa);
//                String plikzqr = Plik.getKatalog()+"nowa.pdf";
//                PdfStamper pdfStamper = new PdfStamper(reader,new FileOutputStream(plikzqr));
//                PdfContentByte content = pdfStamper.getUnderContent(reader.getNumberOfPages());
//                int rot = reader.getPageRotation(reader.getNumberOfPages());
//                logo.setAbsolutePosition(10, 10); //e
//                content.addImage(logo);
//                pdfStamper.close();
//                reader.close();
//                Plik.usun(nazwa);
//                Plik.zapiszjako(plikzqr,nazwa);
//            }
//        } catch (Exception ex) {
//            // Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
//        }
//   }
    
   
    
    public static void dodajpagraf(Document document, String tresc, String lrcj, int rozmiar) {
        try {
            Paragraph par = new Paragraph(new Phrase(tresc, ft[rozmiar]));
            switch (lrcj) {
                case "l":
                    par.setAlignment(Element.ALIGN_LEFT);
                    break;
                case "r":
                    par.setAlignment(Element.ALIGN_RIGHT);
                    break;
                case "c":
                    par.setAlignment(Element.ALIGN_CENTER);
                    break;
                case "j":
                    par.setAlignment(Element.ALIGN_JUSTIFIED);
                    break;
                default:
                    par.setAlignment(Element.ALIGN_LEFT);
                    break;
            }
            document.add(par);
        } catch (DocumentException ex) {
            // Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void dodajOpisWstepny(Document document, Definicjalistaplac def, String nazwadok) {
        try {
            String mc = def.getMc();
            String rok = def.getRok();
            StringBuilder s = new StringBuilder();
            s.append(nazwadok);
            s.append(" ");
            s.append(def.getFirma().getNazwa());
            s.append(" nr ");
            s.append(def.getNrkolejny());
            Paragraph opiswstepny = new Paragraph(new Phrase(s.toString(), ft[2]));
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            opiswstepny = new Paragraph(new Phrase(s.toString(), ft[2]));
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            document.add(opiswstepny);
            if (mc != null) {
                opiswstepny = new Paragraph(new Phrase("okres rozliczeniony" + " " + mc + "/" + rok, ft[1]));
            } else {
                opiswstepny = new Paragraph(new Phrase("okres rozliczeniony" + " rok "+ rok, ft[1]));
            }
            document.add(opiswstepny);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    
    public static void dodajOpisWstepny(Document document, Angaz angaz, String nazwadok, String rok) {
        try {
            StringBuilder s = new StringBuilder();
            s.append(nazwadok);
            s.append(" ");
            s.append(angaz.getPracownik().getNazwiskoImie());
            StringBuilder s1 = new StringBuilder();
            s1.append("Firma ");
            s1.append(angaz.getFirma().getNazwa());
            s1.append(" NIP ");
            s1.append(angaz.getFirma().getNip());
            Paragraph opiswstepny = new Paragraph(new Phrase(s.toString(), ft[2]));
            opiswstepny = new Paragraph(new Phrase(s.toString(), ft[2]));
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            document.add(opiswstepny);
            opiswstepny = new Paragraph(new Phrase(s1.toString(), ft[2]));
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            document.add(opiswstepny);
            opiswstepny = new Paragraph(new Phrase("okres rozliczeniony rok "  + rok, ft[1]));
            document.add(opiswstepny);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    
    public static void dodajpodpis(Document document, String formaprawna) {
        try {
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            Paragraph opiswstepny = new Paragraph(new Phrase(".....................................                                            .......................................", ft[1]));
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            document.add(opiswstepny);
            if (formaprawna.equals("STOWARZYSZENIE")) {
                opiswstepny = new Paragraph(new Phrase("sporządzający                                                           za stowarzyszenie", ft[1]));
            } else if (formaprawna.equals("FEDERACJA")) {
                opiswstepny = new Paragraph(new Phrase("sporządzający                                                           za federację", ft[1]));
            } else if (formaprawna.equals("FUNDACJA")) {
                opiswstepny = new Paragraph(new Phrase("sporządzający                                                           za fundację", ft[1]));
            } else {
                opiswstepny = new Paragraph(new Phrase("sporządzający                                                           za spółkę", ft[1]));
            }
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            document.add(opiswstepny);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }

    
    public static void dodajLinieOpisu(Document document, String opis, int al, int fontsize) {
        try {
            Paragraph opiswstepny = new Paragraph(new Phrase(opis, ft[fontsize]));
            opiswstepny.setAlignment(al);
            opiswstepny.setLeading(20, 0);
            document.add(opiswstepny);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    
    
     public static void dodajLinieOpisuBezOdstepu(Document document, String opis, int al, int fontsize) {
        try {
            Paragraph opiswstepny = new Paragraph(new Phrase(opis, ft[fontsize]));
            opiswstepny.setAlignment(al);
            document.add(opiswstepny);
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    
    
    public static void dodajLinieOpisuBezOdstepuKolor(Document document, String opis, BaseColor color) {
        try {
            Font font = ft[1];
            font.setColor(color);
            Paragraph opiswstepny = new Paragraph(new Phrase(opis, font));
            opiswstepny.setAlignment(Element.ALIGN_LEFT);
            document.add(opiswstepny);
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    
    public static void dodajTabele(Document document, List[] tabela, int perc, int modyfikator, int wielkoscnaglowka) {
        try {
            List naglowki = tabela[0];
            List wiersze = tabela[1];
            if (wiersze != null && wiersze.size() > 0) {
                String nazwaklasy = wiersze.get(0).getClass().getName();
                int[] col = obliczKolumny(naglowki.size(), nazwaklasy, modyfikator);
                PdfPTable table = przygotujtabele(naglowki.size(),col, perc, 2f, 3f);
                ustawnaglowki(table, naglowki, wielkoscnaglowka);
                ustawwiersze(table,wiersze, nazwaklasy, modyfikator);
                document.add(table);
            }
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    
    public static void dodajTabele(Document document, List[] tabela, int perc, int modyfikator) {
        try {
            List naglowki = tabela[0];
            List wiersze = tabela[1];
            if (wiersze != null && wiersze.size() > 0) {
                String nazwaklasy = wiersze.get(0).getClass().getName();
                int[] col = obliczKolumny(naglowki.size(), nazwaklasy, modyfikator);
                PdfPTable table = przygotujtabele(naglowki.size(),col, perc, 2f, 3f);
                ustawnaglowki(table, naglowki);
                ustawwiersze(table,wiersze, nazwaklasy, modyfikator);
                document.add(table);
            }
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    
    public static void dodajTabele2(Document document, String[] nag, int[] col, List wiersze, int perc, int modyfikator, String nazwaklasy) {
        try {
            List nag1 = Arrays.asList(nag);
            if (wiersze != null && wiersze.size() > 0) {
                String nazwaklasy1 = nazwaklasy==null? wiersze.get(0).getClass().getName():nazwaklasy;
                PdfPTable table = przygotujtabele(nag1.size(), col, perc, 2f, 3f);
                ustawnaglowki(table, nag1);
                ustawwiersze(table, wiersze, nazwaklasy1, modyfikator);
                document.add(table);
            }
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    
     public static PdfPTable dodajSubTabele(List[] tabela, int perc, int modyfikator, int size) {
        try {
            List naglowki = tabela[0];
            List wiersze = tabela[1];
            if (wiersze != null && wiersze.size() > 0) {
                String nazwaklasy = wiersze.get(0).getClass().getName();
                int[] col = obliczKolumny(naglowki.size(), nazwaklasy, modyfikator);
                PdfPTable table = przygotujtabele(naglowki.size(),col, perc, 1f, 2f);
                ustawnaglowki(table, naglowki, size);
                ustawwiersze(table,wiersze, nazwaklasy, modyfikator);
                return table;
            } else {
                return null;
            }
        } catch (Exception ex) {
            E.e(ex);
            return null;
        }
    }
    
        
    
    private static int[] obliczKolumnyVies(int size) {
        int[] col = new int[size];
        col[0] = 1;
        col[1] = 4;
        col[2] = 2;
        col[3] = 3;
        col[4] = 4;
        col[5] = 4;
        col[6] = 4;
        col[7] = 2;
        return col;
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
        }
        return null;
    }
    
    public static PdfPTable przygotujtabele(int size, int[] col, int perc, float a, float b) {
        try {
            PdfPTable p = new PdfPTable(size);
            p.setWidths(col);
            p.setWidthPercentage(perc);
            //normalnie 2f i 3f
            p.setSpacingBefore(a);
            p.setSpacingAfter(b);
            return p;
        } catch (DocumentException ex) {
            E.e(ex);
            return null;
        }
    }
    
    public static NumberFormat getPercentFormater() {
        NumberFormat formatter = NumberFormat.getPercentInstance(new Locale("pl","PL"));
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        return formatter;
    }
    
    public static NumberFormat getCurrencyFormater() {
        NumberFormat formatter = NumberFormat.getCurrencyInstance();
        formatter.setMaximumFractionDigits(2);
        formatter.setMinimumFractionDigits(2);
        formatter.setGroupingUsed(true);
        return formatter;
    }
    
    public static NumberFormat getNumberFormater() {
        NumberFormat formatter = NumberFormat.getInstance();
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

    public static void ustawnaglowki(PdfPTable table, List naglowki) {
        for (int i = 0; i < naglowki.size(); i++) {
            table.addCell(ustawfrazeAlign((String) naglowki.get(i), "left", 9));
        }
        table.setHeaderRows(1);
    }
    
    private static void ustawnaglowki(PdfPTable table, List naglowki, String align) {
        for (int i = 0; i < naglowki.size(); i++) {
            table.addCell(ustawfrazeAlign((String) naglowki.get(i), align, 9));
        }
        table.setHeaderRows(1);
    }
    
     private static void ustawnaglowki(PdfPTable table, List naglowki, int size) {
        for (int i = 0; i < naglowki.size(); i++) {
            table.addCell(ustawfrazeAlign((String) naglowki.get(i), "left", size));
        }
        table.setHeaderRows(1);
    }
    

   private static void ustawwiersze(PdfPTable table, List wiersze, String nazwaklasy, int modyfikator) {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        String l = locale.getLanguage();
        NumberFormat currency = getCurrencyFormater();
        NumberFormat number = getNumberFormater();
        NumberFormat percent = getPercentFormater();
        int i = 1;
        int maxlevel = 0;
        for (Iterator it = wiersze.iterator(); it.hasNext();) {
            
        }
    }
   
//    private static void tablicaWkomorce(PdfPTable table, Object p, String nazwaklasy, int modyfikator) {
//        if (nazwaklasy.equals("entity.Dok")) {
//            if (modyfikator == 0) {
//                try {
//                    NumberFormat number = getNumberFormater();
//                    Dok d = (Dok) p;
//                    if (d.getTabelanbp()==null || d.getTabelanbp().getNrtabeli().equals("000/A/NBP/0000")) {
//                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(d.getNetto())), "right", 9));
//                    } else {
//                        PdfPTable t = new PdfPTable(1);
//                        t.getDefaultCell().setBorderWidth(0f);
//                        int[] col = new int[1];
//                        col[0] = 2;
//                        t.setWidths(col);
//                        t.setWidthPercentage(100);
//                        t.addCell(ustawfrazeAlignNOBorder(String.valueOf(number.format(d.getNetto())), "right", 9));
//                        t.addCell(ustawfrazeAlignNOBorder(d.getTabelanbp().getWaluta().getSkrotsymbolu()+" "+String.valueOf(number.format(d.getNettoWaluta())), "right", 9));
//                        table.addCell(t);
//                    }
//                } catch (Exception e) {
//                    E.e(e);
//                }
//            } else if (modyfikator == 2) {
//                try {
//                    NumberFormat number = getNumberFormater();
//                    Dok d = (Dok) p;
//                    if (d.getTabelanbp()==null || d.getTabelanbp().getNrtabeli().equals("000/A/NBP/0000")) {
//                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(d.getVat())), "right", 9));
//                    } else {
//                        PdfPTable t = new PdfPTable(1);
//                        t.getDefaultCell().setBorderWidth(0f);
//                        int[] col = new int[1];
//                        col[0] = 2;
//                        t.setWidths(col);
//                        t.setWidthPercentage(100);
//                        t.addCell(ustawfrazeAlignNOBorder(String.valueOf(number.format(d.getVat())), "right", 9));
//                        t.addCell(ustawfrazeAlignNOBorder(d.getTabelanbp().getWaluta().getSkrotsymbolu()+" "+String.valueOf(number.format(d.getVatWaluta())), "right", 9));
//                        table.addCell(t);
//                    }
//                } catch (Exception e) {
//                    E.e(e);
//                }
//            } else if (modyfikator == 3) {
//                try {
//                    NumberFormat number = getNumberFormater();
//                    Dok d = (Dok) p;
//                    if (d.getTabelanbp()==null || d.getTabelanbp().getNrtabeli().equals("000/A/NBP/0000")) {
//                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(d.getBrutto())), "right", 9));
//                    } else {
//                        PdfPTable t = new PdfPTable(1);
//                        t.getDefaultCell().setBorderWidth(0f);
//                        int[] col = new int[1];
//                        col[0] = 2;
//                        t.setWidths(col);
//                        t.setWidthPercentage(100);
//                        t.addCell(ustawfrazeAlignNOBorder(String.valueOf(number.format(d.getBrutto())), "right", 9));
//                        t.addCell(ustawfrazeAlignNOBorder(d.getTabelanbp().getWaluta().getSkrotsymbolu()+" "+String.valueOf(number.format(d.getBruttoWaluta())), "right", 9));
//                        table.addCell(t);
//                    }
//                } catch (Exception e) {
//                    E.e(e);
//                }
//            } else {
//                 try {
//                    NumberFormat number = getNumberFormater();
//                    Dok d = (Dok) p;
//                    PdfPTable t = new PdfPTable(2);
//                    t.getDefaultCell().setBorderWidth(0f);
//                    int[] col = new int[2];
//                    col[0] = 3;
//                    col[1] = 2;
//                    t.setWidths(col);
//                    t.setWidthPercentage(100);
//                    for (KwotaKolumna1 r : d.getListakwot1()) {
//                        t.addCell(ustawfrazeAlignNOBorder(r.getNazwakolumny(), "left", 9));
//                        t.addCell(ustawfrazeAlignNOBorder(String.valueOf(number.format(r.getNetto())), "right", 9));
//                    }
//                    table.addCell(t);
//                } catch (Exception e) {
//                    E.e(e);
//                }
//            }
//        }
//    }
 

	public static void displayNumbers(String whichFormat, NumberFormat numberFormat) {
		//error.E.s("Format:" + whichFormat);
		for (int i = 0; i <= 10; i++) {
			double num = Math.PI * Math.pow(i, i) * i;
			System.out.print("  formatted:" + numberFormat.format(num));
		}
	}
        
        public static String losowanazwa() {
            int length = 10;
            boolean useLetters = true;
            boolean useNumbers = false;
            String generatedString = RandomStringUtils.random(length, useLetters, useNumbers);
            return generatedString;
        }

//    public static void main(String[] args) {
//        Method[] fields = JPK.Ewidencja.SprzedazWiersz.class.getMethods();
//        List<String> metodystring = new ArrayList<>();
//        for (Method m : fields) {
//            if (m.getReturnType().equals(Byte.class)) {
//                Byte wynik = m.invoke(p, null);
//                if (wynik==(byte)1) {
//                    metodystring.add(m.getName().replace("get", "").replace(")(", ""));
//                }
//            }
//        }
//        System.out.println("");
//    }
}
