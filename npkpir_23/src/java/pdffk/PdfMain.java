/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdffk;

import static beansPdf.PdfFont.emptyCell;
import static beansPdf.PdfFont.ustawfrazeAlign;
import static beansPdf.PdfFont.ustawfrazeAlignLevel;
import static beansPdf.PdfFont.ustawfrazeAlignNOBorder;
import static beansPdf.PdfGrafika.prost;
import beansPdf.PdfHeaderFooter;
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
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;
import com.itextpdf.text.pdf.PdfWriter;
import data.Data;
import embeddable.FakturaPodatnikRozliczenie;
import embeddable.Mce;
import embeddable.SchemaEwidencjaSuma;
import embeddable.ZestawienieRyczalt;
import embeddablefk.KontoBO;
import entity.DeklaracjaVatSchemaWierszSum;
import entity.Dok;
import entity.Faktura;
import entity.Fakturywystokresowe;
import entity.KwotaKolumna1;
import entity.Podatnik;
import entity.Ryczpoz;
import entity.SrodekTrw;
import entity.Statystyka;
import entity.UmorzenieN;
import entity.VatSuper;
import entityfk.Cechazapisu;
import entityfk.Dokfk;
import entityfk.EVatwpisDedra;
import entityfk.Konto;
import entityfk.MiejscePrzychodow;
import entityfk.PozycjaBilans;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import entityfk.Transakcja;
import entityfk.Wiersz;
import entityfk.WierszBO;
import error.E;
import format.F;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import msg.B;
import plik.Plik;
import testobjects.WierszCecha;
import testobjects.WierszDokfk;
import testobjects.WierszKonta;
import testobjects.WierszTabeli;
import testobjects.WierszWNTWDT;
import vies.Vies;
import viewfk.StowRozrachCzlonkView;
import viewfk.StowRozrachCzlonkZbiorczeView;
import waluty.Z;

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

    public static PdfWriter inicjacjaWritera(Document document, String nazwapliku) {
        try {
            String nazwa = nazwapliku + ".pdf";
            File file = Plik.plik(nazwa, true);
            if (file.isFile()) {
                file.delete();
            }
            PdfWriter writer = PdfWriter.getInstance(document, Plik.plikR(nazwa));
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
        document.addSubject("Wydruk danych z programu księgowego");
        document.addKeywords("PKPiR, PDF");
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
            String nazwaplikuzbazy = realPath+"resources/images/logo/taxman.jpg";
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
    
    public static void main(String[] args) {
        try {
             ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            String nazwaplikuzbazy = realPath+"resources/images/logo/taxman.jpg";
            String nazwa = Plik.getKatalog()+"2.pdf";
            File f = new File(nazwaplikuzbazy);
            if(f.exists()) {
                Image logo = Image.getInstance(nazwaplikuzbazy);
                logo.scaleToFit(50f, 50f);
                PdfReader reader = new PdfReader(nazwa);
                String plikzqr = Plik.getKatalog()+"nowa.pdf";
                PdfStamper pdfStamper = new PdfStamper(reader,new FileOutputStream(plikzqr));
                PdfContentByte content = pdfStamper.getUnderContent(reader.getNumberOfPages());
                int rot = reader.getPageRotation(reader.getNumberOfPages());
                logo.setAbsolutePosition(10, 10); //e
                content.addImage(logo);
                pdfStamper.close();
                reader.close();
                Plik.usun(nazwa);
                Plik.zapiszjako(plikzqr,nazwa);
            }
        } catch (Exception ex) {
            Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
        }
   }
    
   
    
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
            Logger.getLogger(PdfMain.class.getName()).log(Level.SEVERE, null, ex);
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
            opiswstepny = new Paragraph(new Phrase("symbol księgowy " + selected.getDokfkSN(), ft[1]));
            document.add(opiswstepny);
            opiswstepny = new Paragraph(new Phrase("okres rozliczeniowy " + selected.getMiesiac() + "/" + selected.getRok(), ft[1]));
            document.add(opiswstepny);
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    
    public static void dodajOpisWstepnyKompakt(Document document, String opis, Podatnik podatnik, String mc, String rok) {
        try {
            StringBuilder s = new StringBuilder();
            s.append(opis);
            s.append(" ");
            s.append(podatnik.getFirmaForma());
            s.append(" ");
            s.append(podatnik.getNazwapelnaPDF());
            s.append(" NIP ");
            s.append(podatnik.getNip());
            s.append(" ");
            s.append(B.b("danenakoniecmca"));
            s.append(" ");
            s.append(mc);
            s.append("/");
            s.append(rok);
            Paragraph opiswstepny = new Paragraph(new Phrase(s.toString(), ft[2]));
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            document.add(opiswstepny);
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    
    
    public static void dodajOpisWstepny(Document document, String opis, Podatnik podatnik, String mc, String rok) {
        try {
            StringBuilder s = new StringBuilder();
            s.append(podatnik.getFirmaForma());
            s.append(" ");
            s.append(podatnik.getPrintnazwa());
            s.append(" NIP ");
            s.append(podatnik.getNip());
            Paragraph opiswstepny = new Paragraph(new Phrase(s.toString(), ft[2]));
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            document.add(opiswstepny);
            s = new StringBuilder();
            s.append(opis);
            opiswstepny = new Paragraph(new Phrase(s.toString(), ft[2]));
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            document.add(opiswstepny);
            if (mc != null) {
                opiswstepny = new Paragraph(new Phrase(B.b("okresrozliczeniony") + " " + mc + "/" + rok, ft[1]));
            } else {
                opiswstepny = new Paragraph(new Phrase(B.b("okresrozliczeniony") + " rok "+ rok, ft[1]));
            }
            document.add(opiswstepny);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    
    public static void dodajOpisWstepnySF(Document document, String opis, Podatnik podatnik, String bilansnadzien, String bilansoddnia) {
        try {
            StringBuilder s = new StringBuilder();
            s.append("Firma ");
            s.append(podatnik.getNazwapelnaPDF());
            s.append(" NIP ");
            s.append(podatnik.getNip());
            Paragraph opiswstepny = new Paragraph(new Phrase(s.toString(), ft[2]));
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            document.add(opiswstepny);
            s = new StringBuilder();
            s.append(opis);
            opiswstepny = new Paragraph(new Phrase(s.toString(), ft[2]));
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            document.add(opiswstepny);
            if (bilansoddnia != null) {
               opiswstepny = new Paragraph(new Phrase("bilans otwarcia na dzień "+bilansoddnia, ft[1]));
            }
            document.add(opiswstepny);
            if (bilansnadzien != null) {
               opiswstepny = new Paragraph(new Phrase("bilans zamknięcia na dzień "+bilansnadzien, ft[1]));
            }
            document.add(opiswstepny);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    
     public static void dodajOpisWstepnySFRZIS(Document document, String opis, Podatnik podatnik, String bilansnadzien, String bilansoddnia) {
        try {
            StringBuilder s = new StringBuilder();
            s.append("Firma ");
            s.append(podatnik.getNazwapelnaPDF());
            s.append(" NIP ");
            s.append(podatnik.getNip());
            Paragraph opiswstepny = new Paragraph(new Phrase(s.toString(), ft[2]));
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            document.add(opiswstepny);
            s = new StringBuilder();
            s.append(opis);
            opiswstepny = new Paragraph(new Phrase(s.toString(), ft[2]));
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            document.add(opiswstepny);
            if (bilansoddnia != null) {
               opiswstepny = new Paragraph(new Phrase("stan na koniec roku poprzedniego - "+bilansoddnia, ft[1]));
            }
            document.add(opiswstepny);
            if (bilansnadzien != null) {
               opiswstepny = new Paragraph(new Phrase("stan na koniec roku bieżącego - "+bilansnadzien, ft[1]));
            }
            document.add(opiswstepny);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    
    public static void dodajOpisWstepnyFaktury(Document document, String opis, String podatnik, String nip, String mc, String rok) {
        try {
            StringBuilder s = new StringBuilder();
            s.append(opis);
            s.append(" ");
            s.append(podatnik);
            s.append(" NIP ");
            s.append(nip);
            Paragraph opiswstepny = new Paragraph(new Phrase(s.toString(), ft[2]));
            opiswstepny.setAlignment(Element.ALIGN_CENTER);
            document.add(opiswstepny);
            document.add(Chunk.NEWLINE);
            opiswstepny = new Paragraph("rozliczenia z uwzględnieniem okresu " + mc + "/" + rok, ft[1]);
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
    
    public static void dodajLinieOpisu(Document document, String opis) {
        try {
            Paragraph opiswstepny = new Paragraph(new Phrase(opis, ft[1]));
            opiswstepny.setAlignment(Element.ALIGN_LEFT);
            document.add(opiswstepny);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    public static void dodajLinieOpisuBezOdstepu(Document document, String opis) {
        try {
            Paragraph opiswstepny = new Paragraph(new Phrase(opis, ft[1]));
            opiswstepny.setAlignment(Element.ALIGN_LEFT);
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
    
    public static void informacjaoZaksiegowaniu(Document document, String lp) {
        try {
            document.add(new Chunk("Biuro Rachunkowe Taxman"));
            Paragraph zaksiegowany = new Paragraph(new Phrase("dokument zaksięgowany pod lp: " + lp, ft[1]));
            zaksiegowany.setAlignment(Element.ALIGN_LEFT);
            document.add(zaksiegowany);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    
    public static void infooFirmie(Document document, Dokfk selected) {
        try {
            document.add(Chunk.NEWLINE);
            Paragraph danefirmy = new Paragraph(new Phrase("Firma: " + selected.getPodatnikObj().getNazwapelnaPDF(), ft[1]));
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
    
     public static void dodajTabeleVies(Document document, List[] tabela, int perc, int modyfikator) {
        try {
            List naglowki = tabela[0];
            List wiersze = tabela[1];
            if (wiersze != null && wiersze.size() > 0) {
                String nazwaklasy = wiersze.get(0).getClass().getName();
                int[] col = obliczKolumnyVies(naglowki.size());
                PdfPTable table = przygotujtabele(naglowki.size(),col, perc, 2f, 3f);
                ustawnaglowki(table, naglowki);
                ustawwiersze(table,wiersze, nazwaklasy, modyfikator);
                document.add(table);
            }
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    
    public static void dodajTabeleNar(Document document, List[] tabela, int perc, int modyfikator, List<String> mce) {
        try {
            List naglowki = tabela[0];
            List wiersze = tabela[1];
            if (wiersze != null && wiersze.size() > 0) {
                String nazwaklasy = wiersze.get(0).getClass().getName();
                int[] col = obliczKolumnyNar(naglowki.size(), mce.size());
                PdfPTable table = przygotujtabele(naglowki.size(),col, perc, 2f, 3f);
                ustawnaglowki(table, naglowki, "center");
                ustawwierszeNar(table,wiersze, nazwaklasy, modyfikator, mce);
                document.add(table);
            }
        } catch (DocumentException ex) {
            E.e(ex);
        }
    }
    
     public static void dodajTabelePorMcy(Document document, List[] tabela, int perc, int modyfikator, List<String> mce) {
        try {
            List naglowki = tabela[0];
            List wiersze = tabela[1];
            if (wiersze != null && wiersze.size() > 0) {
                String nazwaklasy = wiersze.get(0).getClass().getName();
                int[] col = obliczKolumnyNar(naglowki.size(), mce.size());
                PdfPTable table = przygotujtabele(naglowki.size(),col, perc, 2f, 3f);
                ustawnaglowki(table, naglowki, "center");
                ustawwierszeNar(table,wiersze, nazwaklasy, modyfikator, mce);
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
    
     private static int[] obliczKolumnyNar(int size, int mce) {
        int[] col71 = new int[size];
        int levele = size-mce-1;
        for (int i = 0; i < levele ; i++) {
            col71[i] = 1;
        }
        col71[levele++] = 8;
        for (int i = levele; i < size ; i++) {
            col71[i] = 4;
        }
        return col71;
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
            case "embeddablefk.KontoKwota":
                col = new int[size];
                col[0] = 2;
                col[1] = 6;
                col[2] = 3;
                return col;
            case "entity.Fakturywystokresowe":
                col = new int[size];
                col[0] = 2;
                col[1] = 8;
                col[2] = 8;
                col[3] = 3;
                col[4] = 3;
                return col;
            case "entityfk.Cechazapisu":
                col = new int[size];
                col[0] = 2;
                col[1] = 9;
                col[2] = 2;
                col[3] = 4;
                col[4] = 4;
                col[5] = 4;
                return col;
            case "jpk201701.JPK$SprzedazWiersz":
            case "jpk201801.JPK$SprzedazWiersz":
                col = new int[size];
                col[0] = 1;
                col[1] = 4;
                col[2] = 7;
                col[3] = 3;
                col[4] = 3;
                col[5] = 3;
                col[6] = 3;
                col[7] = 3;
                col[8] = 2;
                col[9] = 2;
                return col;
            case "jpk201701.JPK$ZakupWiersz":
            case "jpk201801.JPK$ZakupWiersz":
                col = new int[size];
                col[0] = 1;
                col[1] = 4;
                col[2] = 7;
                col[3] = 3;
                col[4] = 3;
                col[5] = 3;
                col[6] = 3;
                col[7] = 3;
                col[8] = 2;
                col[9] = 2;
                return col;
            case "entityfk.MiejscePrzychodow":
                col = new int[size];
                col[0] = 2;
                col[1] = 6;
                col[2] = 3;
                col[3] = 3;
                col[4] = 3;
                col[5] = 4;
                return col;
            case "entity.Statystyka":
                col = new int[size];
                col[0] = 2;
                col[1] = 6;
                col[2] = 3;
                col[3] = 2;
                col[4] = 2;
                col[5] = 3;
                col[6] = 2;
                col[7] = 3;
                col[8] = 3;
                col[9] = 3;
                col[10] = 3;
                return col;
            case "entity.Podatnik":
                col = new int[size];
                col[0] = 2;
                col[1] = 5;
                col[2] = 4;
                col[3] = 3;
                col[4] = 2;
                col[5] = 2;
                col[6] = 3;
                col[7] = 4;
                return col;
            case "viewfk.StowRozrachCzlonkView$Pozycja":
                col = new int[size];
                col[0] = 1;
                col[1] = 9;
                col[2] = 2;
                col[3] = 2;
                col[4] = 2;
                col[5] = 2;
                col[6] = 2;
                col[7] = 2;
                col[8] = 2;
                col[9] = 2;
                col[10] = 2;
                col[11] = 2;
                col[12] = 2;
                col[13] = 2;
                col[14] = 2;
                return col;
            case "embeddable.FakturaPodatnikRozliczenie":
                if (modyfikator == 0) {
                    col = new int[size];
                    col[0] = 1;
                    col[1] = 3;
                    col[2] = 4;
                    col[3] = 3;
                    col[4] = 3;
                    col[5] = 3;
                    col[6] = 2;
                    col[7] = 3;
                    col[8] = 3;
                    col[9] = 3;
                    return col;
                } else {
                    col = new int[size];
                    col[0] = 1;
                    col[1] = 6;
                    col[2] = 3;
                    col[3] = 4;
                    col[4] = 3;
                    col[5] = 3;
                    col[6] = 3;
                    col[7] = 3;
                    return col;
                }
            case "entity.UmorzenieN":
                col = new int[size];
                col[0] = 2;
                col[1] = 2;
                col[2] = 2;
                col[3] = 3;
                return col;
            case "entityfk.EVatwpisDedra":
                col = new int[size];
                col[0] = 2;
                col[1] = 3;
                col[2] = 3;
                col[3] = 5;
                col[4] = 4;
                col[5] = 5;
                col[6] = 3;
                col[7] = 3;
                col[8] = 2;
                col[9] = 3;
                return col;
            case "embeddable.SchemaEwidencjaSuma":
            case "entity.DeklaracjaVatSchemaWierszSum":
                col = new int[size];
                col[0] = 1;
                col[1] = 6;
                col[2] = 1;
                col[3] = 2;
                col[4] = 1;
                col[5] = 2;
                return col;
            case "entityfk.Wiersz":
                col = new int[size];
                col[0] = 2;
                col[1] = 2;
                col[2] = 3;
                col[3] = 1;
                col[4] = 6;
                col[5] = 2;
                col[6] = 2;
                col[7] = 3;
                col[8] = 2;
                col[9] = 2;
                col[10] = 3;
                return col;
            case "entity.Dok":
                col = new int[size];
                if (modyfikator==0) {
                    col[0] = 1;
                    col[1] = 2;
                    col[2] = 6;
                    col[3] = 1;
                    col[4] = 3;
                    col[5] = 3;
                    col[6] = 2;
                    col[7] = 2;
                    col[8] = 2;
                    col[9] = 4;
                    col[10] = 1;
                    col[11] = 3;
                } else {
                    col[0] = 1;
                    col[1] = 2;
                    col[2] = 3;
                    col[3] = 4;
                    col[4] = 2;
                    col[5] = 2;
                    col[6] = 2;
                    col[7] = 2;
                    col[8] = 2;
                    col[9] = 2;
                    col[10] = 2;
                    col[11] = 3;
                }
                return col;
            case "entity.Ryczpoz":
                col = new int[size];
                col[0] = 1;
                col[1] = 2;
                col[2] = 6;
                col[3] = 3;
                col[4] = 3;
                col[5] = 3;
                col[6] = 3;
                col[7] = 3;
                col[8] = 2;
                col[9] = 3;
                col[10] = 3;
                col[11] = 3;
                col[12] = 3;
                return col;
            case "entity.SrodekTrw":
                if (modyfikator == 0) {
                    col = new int[size];
                    col[0] = 1;
                    col[1] = 2;
                    col[2] = 2;
                    col[3] = 5;
                    col[4] = 1;
                    col[5] = 2;
                    col[6] = 2;
                    col[7] = 2;
                    col[8] = 2;
                    col[9] = 2;
                    col[10] = 2;
                    col[11] = 2;
                    return col;
                } else if (modyfikator == 2) {
                    col = new int[size];
                    col[0] = 1;
                    col[1] = 2;
                    col[2] = 5;
                    col[3] = 2;
                    col[4] = 2;
                    col[5] = 2;
                    col[6] = 2;
                    col[7] = 2;
                    col[8] = 2;
                    col[9] = 2;
                    col[10] = 2;
                    col[11] = 2;
                    return col;
                } else {
                    col = new int[size];
                    col[0] = 1;
                    col[1] = 2;
                    col[2] = 2;
                    col[3] = 5;
                    col[4] = 3;
                    col[5] = 2;
                    col[6] = 2;
                    col[7] = 2;
                    col[8] = 2;
                    col[9] = 2;
                    return col;
                }
            case "entityfk.StronaWiersza":
                if (modyfikator == 0 || modyfikator == 1) {
                    col = new int[size];
                    col[0] = 1;
                    col[1] = 2;
                    col[2] = 2;
                    col[3] = 3;
                    col[4] = 5;
                    col[5] = 2;
                    col[6] = 2;
                    col[7] = 2;
                    return col;
                } else {
                    //PdfKontoZapisy drukujzapisyKompakt
                    col = new int[size];
                    col[0] = 1;
                    col[1] = 3;
                    col[2] = 3;
                    col[3] = 2;
                    col[4] = 3;
                    col[5] = 7;
                    col[6] = 3;
                    col[7] = 3;
                    col[8] = 2;
                    col[9] = 2;
                    return col;
                }
            case "testobjects.WierszKonta":
                if (modyfikator == 0) {
                    int[] col2 = new int[size];
                    col2[0] = 1;
                    col2[1] = 6;
                    col2[2] = 3;
                    col2[3] = 4;
                    col2[4] = 3;
                    col2[5] = 4;
                    if (size > 6) {
                        col2[6] = 1;
                    }
                    return col2;
                } else {
                    int[] col2 = new int[size];
                    col2[0] = 1;
                    col2[1] = 5;
                    col2[2] = 2;
                    col2[3] = 3;
                    col2[4] = 2;
                    col2[5] = 3;
                    if (size > 6) {
                        col2[6] = 2;
                    }
                    return col2;
                }
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
                col4[5] = 5;
                col4[6] = 5;
                col4[7] = 3;
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
                col5[4] = 5;
                col5[5] = 2;
                col5[6] = 4;
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
            case "viewfk.StowRozrachCzlonkZbiorczeView$Zapisy":
                col6 = new int[size];
                col6[0] = 1;
                col6[1] = 4;
                col6[2] = 2;
                col6[3] = 2;
                col6[4] = 2;
                col6[5] = 3;
                col6[6] = 2;
                return col6;
            case "entityfk.PozycjaRZiS":
                 if (modyfikator==1) {//pozycje
                    int[] col91 = new int[size];
                    int leveleB = size-3;
                    for (int i = 0; i < leveleB ; i++) {
                        col91[i] = 1;
                    }
                    col91[leveleB++] = 10;
                    col91[leveleB++] = 3;
                    col91[leveleB] = 7;
                    return col91;
                } else if (modyfikator==2) {//bez 0
                    int[] col71 = new int[size];
                    int levele = size-2;
                    for (int i = 0; i < levele ; i++) {
                        col71[i] = 1;
                    }
                    col71[levele++] = 10;
                    col71[levele] = 8;
                    return col71;
                } else if (modyfikator==3) {//rok biezacy plus uprzedni
                    int[] col71 = new int[size];
                    int levele = size-3;
                    for (int i = 0; i < levele ; i++) {
                        col71[i] = 1;
                    }
                    col71[levele++] = 10;
                    col71[levele++] = 3;
                    col71[levele] = 3;
                    return col71;
                } else if (modyfikator == 5) {//BO + Data
                    int[] col7 = new int[size];
                    int levele = size-3;
                    for (int i = 0; i < levele ; i++) {
                        col7[i] = 1;
                    }
                    col7[levele++] = 10;
                    col7[levele] = 3;
                    col7[levele] = 3;
                    return col7;
                } else {//normalne
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
                if (modyfikator==0) {
                    int[] col101 = new int[size];
                    col101[0] = 3;
                    col101[1] = 12;
                    col101[2] = 3;
                    return col101;
                } else if (modyfikator==5) {
                    int[] col101 = new int[size];
                    col101[0] = 3;
                    col101[1] = 12;
                    col101[2] = 3;
                    col101[3] = 3;
                    return col101;
                } else {
                    int[] col101 = new int[size];
                    col101[0] = 3;
                    col101[1] = 10;
                    col101[2] = 4;
                    col101[3] = 12;
                    return col101;
                }
            case "embeddablefk.KontoBO":
                int[] col102 = new int[size];
                    col102[0] = 1;
                    col102[1] = 2;
                    col102[2] = 5;
                    col102[3] = 3;
                    col102[4] = 3;
                    col102[5] = 3;
                    col102[6] = 3;
                    col102[7] = 3;
                    col102[8] = 3;
                    col102[9] = 3;
                    return col102;
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
                } else if (modyfikator==2) {
                    int[] col10 = new int[size];
                    col10[0] = 1;
                    col10[1] = 2;
                    col10[2] = 5;
                    col10[3] = 3;
                    col10[4] = 5;
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
                if (modyfikator == 1) {
                    int[] col13 = new int[size];
                    col13[0] = 3;
                    col13[1] = 3;
                    col13[2] = 3;
                    col13[3] = 3;
                    col13[4] = 3;
                    col13[5] = 3;
                    col13[6] = 5;
                    col13[7] = 3;
                    return col13;
                } else {
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
                }
            case "entityfk.WierszBO":
                if (modyfikator==0 || modyfikator==2) {
                    int[] col14 = new int[size];
                    col14[0] = 1;
                    col14[1] = 6;
                    col14[2] = 5;
                    col14[3] = 2;
                    col14[4] = 2;
                    col14[5] = 3;
                    col14[6] = 3;
                    col14[7] = 3;
                    col14[8] = 3;
                    return col14;
                } else {
                    int[] col14 = new int[size];
                    col14[0] = 1;
                    col14[1] = 6;
                    col14[2] = 5;
                    col14[3] = 3;
                    col14[4] = 3;
                    col14[5] = 3;
                    col14[6] = 3;
                    return col14;
                }
            case "entity.VatUe":
            case "entity.Vat27":
                if (modyfikator==1) {
                    int[] col14 = new int[size];
                    col14[0] = 1;
                    col14[1] = 2;
                    col14[2] = 2;
                    col14[3] = 3;
                    col14[4] = 4;
                    col14[5] = 3;
                    col14[6] = 2;
                    col14[7] = 1;
                    return col14;
                } else {
                    int[] col14 = new int[size];
                    col14[0] = 1;
                    col14[1] = 3;
                    col14[2] = 2;
                    col14[3] = 4;
                    col14[4] = 5;
                    col14[5] = 3;
                    col14[6] = 2;
                    return col14;
                }
        }
        return null;
    }
    
    private static PdfPTable przygotujtabele(int size, int[] col, int perc, float a, float b) {
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

    private static void ustawnaglowki(PdfPTable table, List naglowki) {
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
            if (nazwaklasy.equals("embeddable.FakturaPodatnikRozliczenie")) {
                if (modyfikator == 0) {
                    FakturaPodatnikRozliczenie p = (FakturaPodatnikRozliczenie) it.next();
                    table.addCell(ustawfrazeAlign(String.valueOf(p.getLp()), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getRodzajDok(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getNrDok(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getData(), "center", 8));
                    if (p.isFaktura0rozliczenie1()) {
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 8));
                    }
                    table.addCell(ustawfrazeAlign(p.pokazWalute(), "center", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getSaldopln())), "right", 8));
                    table.addCell(ustawfrazeAlign(Data.data_yyyyMMdd(p.getDatatelefon()), "center", 8));
                    table.addCell(ustawfrazeAlign(Data.data_yyyyMMdd(p.getDataupomnienia()), "center", 8));
                } else {
                    FakturaPodatnikRozliczenie p = (FakturaPodatnikRozliczenie) it.next();
                    table.addCell(ustawfrazeAlign(String.valueOf(p.getLp()), "center", 8, 22f));
                    table.addCell(ustawfrazeAlign(p.getKontrahent(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getRodzajDok(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getNrDok(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getData(), "center", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getSaldopln())), "right", 8));
                    table.addCell(ustawfrazeAlign(p.getOstatniaplatnoscdata(), "center", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getOstatniaplatnosckwota())), "right", 8));
                }
            }
            if (nazwaklasy.equals("jpk201701.JPK$ZakupWiersz") || nazwaklasy.equals("jpk201801.JPK$ZakupWiersz")) {
                jpkabstract.ZakupWierszA p =  (jpkabstract.ZakupWierszA) it.next();
                table.addCell(ustawfrazeAlign(i++, "center", 7));
                table.addCell(ustawfrazeAlign(p.getNrDostawcy(), "left", 7, 22f));
                table.addCell(ustawfrazeAlign(p.getNazwaDostawcyShort(), "left", 7));
                table.addCell(ustawfrazeAlign(p.getDowodZakupu(), "left", 7));
                table.addCell(ustawfrazeAlign(p.getDataWplywu(), "left", 7));
                table.addCell(ustawfrazeAlign(p.getDataZakupu(), "left", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getNetto())), "right", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getVat())), "right", 8));
                table.addCell(ustawfrazeAlign(p.getNettoPole(), "center", 7));
                table.addCell(ustawfrazeAlign(p.getVatPole(), "center", 7));
            }
            if (nazwaklasy.equals("jpk201701.JPK$SprzedazWiersz") || nazwaklasy.equals("jpk201801.JPK$SprzedazWiersz")) {
                jpkabstract.SprzedazWierszA p = (jpkabstract.SprzedazWierszA) it.next();
                table.addCell(ustawfrazeAlign(i++, "center", 7));
                table.addCell(ustawfrazeAlign(p.getNrKontrahenta(), "left", 7, 22f));
                table.addCell(ustawfrazeAlign(p.getNazwaKontrahentaShort(), "left", 7));
                table.addCell(ustawfrazeAlign(p.getDowodSprzedazy(), "left", 7));
                table.addCell(ustawfrazeAlign(p.getDataWystawienia(), "left", 7));
                table.addCell(ustawfrazeAlign(p.getDataSprzedazy(), "left", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getNetto())), "right", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getVat())), "right", 8));
                table.addCell(ustawfrazeAlign(p.getNettoPole(), "center", 7));
                table.addCell(ustawfrazeAlign(p.getVatPole(), "center", 7));
            }
            if (nazwaklasy.equals("vies.Vies")) {
                Vies p = (Vies) it.next();
                table.addCell(ustawfrazeAlign(i++, "center", 7));
                table.addCell(ustawfrazeAlign(Data.data_ddMMMMyyyy(p.getData()), "center", 7, 15f));
                table.addCell(ustawfrazeAlign(p.getKraj(), "center", 7));
                table.addCell(ustawfrazeAlign(p.getNIP(), "center", 7));
                table.addCell(ustawfrazeAlign(p.getNazwafirmy(), "left", 7));
                table.addCell(ustawfrazeAlign(p.getAdresfirmy(), "left", 7));
                table.addCell(ustawfrazeAlign(p.getIdentyfikatorsprawdzenia(), "center", 7));
                table.addCell(ustawfrazeAlign(p.getWynikVies(), "center", 7));
            }
            if (nazwaklasy.equals("entityfk.Cechazapisu")) {
                if (modyfikator==0) {
                    Cechazapisu p = (Cechazapisu) it.next();
                    table.addCell(ustawfrazeAlign(i++, "center", 9));
                    table.addCell(ustawfrazeAlign(p.getNazwacechy(), "left", 9, 15f));
                    table.addCell(ustawfrazeAlign(p.getDokLista().size(), "center", 9));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getSumaprzychodow())), "right", 9));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getSumakosztow())), "right", 9));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getWynik())), "right", 9));
                } else if (modyfikator==1) {
                    Cechazapisu p = (Cechazapisu) it.next();
                    table.addCell(ustawfrazeAlign(i++, "center", 9));
                    table.addCell(ustawfrazeAlign(p.getNazwacechy(), "left", 9, 15f));
                    table.addCell(ustawfrazeAlign(p.getDokLista().size(), "center", 9));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getSumaprzychodow())), "right", 9));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getSumakosztow())), "right", 9));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getWynik())), "right", 9));
                    PdfPCell cell = new PdfPCell(PdfCechyZapisow.tabeladokumenty(p));
                    cell.setColspan(6);
                    table.addCell(cell);
                }
            }
            if (nazwaklasy.equals("entity.Podatnik")) {
                Podatnik p = (Podatnik) it.next();
                table.addCell(ustawfrazeAlign(i++, "center", 8));
                table.addCell(ustawfrazeAlign(p.getPrintnazwa(), "left", 8, 15f));
                table.addCell(ustawfrazeAlign(p.getImie()+" "+p.getNazwisko(), "left", 8, 15f));
                table.addCell(ustawfrazeAlign(p.getUlica(), "left", 8));
                table.addCell(ustawfrazeAlign(p.getNrdomu(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getNrlokalu(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getKodpocztowy(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getMiejscowosc(), "left", 8));
            }
            if (nazwaklasy.equals("entity.Fakturywystokresowe")) {
                Fakturywystokresowe p = (Fakturywystokresowe) it.next();
                table.addCell(ustawfrazeAlign(i++, "center", 8));
                table.addCell(ustawfrazeAlign(p.getDokument().getKontrahent().getNpelna(), "left", 8, 15f));
                table.addCell(ustawfrazeAlign(p.getDokument().getOpisFaktury(), "left", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getDokument().getNetto())), "right", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getDokument().getBrutto())), "right", 8));
            }
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
            if (nazwaklasy.equals("entityfk.MiejscePrzychodow")) {
                MiejscePrzychodow p = (MiejscePrzychodow) it.next();
                table.addCell(ustawfrazeAlign(i++, "center", 8));
                table.addCell(ustawfrazeAlign(p.getOpismiejsca(), "left", 8));
                table.addCell(ustawfrazeAlign(p.getNrkonta(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getPoczatek(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getKoniec(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getEmail(), "left", 8));
            }
            if (nazwaklasy.equals("viewfk.StowRozrachCzlonkView$Pozycja")) {
                StowRozrachCzlonkView.Pozycja p = (StowRozrachCzlonkView.Pozycja) it.next();
                table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 8));
                table.addCell(ustawfrazeAlign(p.isPrzypis0wplata1() ? p.getOpisW() : p.getOpisP(), "left", 7, 15f));
                table.addCell(ustawfrazeAlign(String.valueOf(p.getM01() != 0 ? number.format(p.getM01()) : ""), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(p.getM02() != 0 ? number.format(p.getM02()) : ""), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(p.getM03() != 0 ? number.format(p.getM03()) : ""), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(p.getM04() != 0 ? number.format(p.getM04()) : ""), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(p.getM05() != 0 ? number.format(p.getM05()) : ""), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(p.getM06() != 0 ? number.format(p.getM06()) : ""), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(p.getM07() != 0 ? number.format(p.getM07()) : ""), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(p.getM08() != 0 ? number.format(p.getM08()) : ""), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(p.getM09() != 0 ? number.format(p.getM09()) : ""), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(p.getM10() != 0 ? number.format(p.getM10()) : ""), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(p.getM11() != 0 ? number.format(p.getM11()) : ""), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(p.getM12() != 0 ? number.format(p.getM12()) : ""), "right", 7));
                table.addCell(ustawfrazeAlign(String.valueOf(p.getRazem()!= 0 ? number.format(p.getRazem()) : ""), "right", 7));
            }
            if (nazwaklasy.equals("entity.Statystyka")) {
                Statystyka p = (Statystyka) it.next();
                table.addCell(ustawfrazeAlign(String.valueOf(p.getLp()), "center", 8));
                table.addCell(ustawfrazeAlign(p.getPodatnik() != null ? p.getPodatnik().getNazwapelnaPDF() : "", "left", 8));
                table.addCell(ustawfrazeAlign(p.getPodatnik() != null ? p.getPodatnik().getNip() : "", "left", 8));
                table.addCell(ustawfrazeAlign(p.getRok(), "left", 8));
                table.addCell(ustawfrazeAlign(p.getIloscdokumentow(), "center", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getObroty())), "right", 8));
                table.addCell(ustawfrazeAlign(p.getIloscfaktur(), "center", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwotafaktur())), "right", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getFakturaNaObroty())), "right", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getFakturaNaDokumenty())), "right", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getRanking())), "right", 8));
            }
            if (nazwaklasy.equals("entityfk.PozycjaRZiS")) {
                if (maxlevel == 0) {
                    for (Iterator<PozycjaRZiS> itX = wiersze.iterator(); itX.hasNext();) {
                        PozycjaRZiS s = (PozycjaRZiS) itX.next();
                        if (s.getLevel() > maxlevel) {
                            maxlevel = s.getLevel();
                        }
                    }
                }
                PozycjaRZiSBilans p = (PozycjaRZiSBilans) it.next();
                int levelPlus = p.getLevel() + 1;
                if (p.getLevel() != 0) {
                    for (int j = 0; j < p.getLevel(); j++) {
                        table.addCell(ustawfrazeAlign("", "l", 8));
                    }
                }
                table.addCell(ustawfrazeAlign(p.getPozycjaSymbol(), "center", 8));
                if (p.getLevel() < maxlevel) {
                    for (int k = levelPlus; k <= maxlevel; k++) {
                        table.addCell(ustawfrazeAlign("", "l", 8));
                    }
                }
                if (p.getLevel() == 0) {
                    if (l.equals("pl")) {
                        table.addCell(ustawfrazeAlign(p.getNazwa(), "left", 8));
                    } else {
                        table.addCell(ustawfrazeAlign(p.getDe(), "left", 8));
                    }
                } else {
                    if (l.equals("pl")) {
                        table.addCell(ustawfrazeAlign(p.getNazwa(), "left", 8));
                    } else {
                        table.addCell(ustawfrazeAlign(p.getDe(), "left", 8));
                    }
                }
                if (modyfikator == 3) {
                    if (p.getKwotabo()!= 0.0) {
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwotabo())), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "right", 8));
                    }
                }
                if (modyfikator != 2) {
                    if (p.getKwota() != 0.0) {
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "right", 8));
                    }
                }
                if (modyfikator != 0 && modyfikator != 3) {
                    if (p.getPrzyporzadkowanekonta()!=null) {
                        for (Iterator<Konto> itp = p.getPrzyporzadkowanekonta().iterator(); itp.hasNext();) {
                            Konto kk = itp.next();
                            if (Z.z(kk.getKwota())==0.0) {
                                itp.remove();
                            }
                        }
                    }
                    String konta = p.getPrzyporzadkowanekontaString();
                    table.addCell(ustawfrazeAlign(konta, "left", 7));
                }
            }
            if (nazwaklasy.equals("entityfk.PozycjaBilans")) {
                if (maxlevel == 0) {
                    for (Iterator<PozycjaBilans> itX = wiersze.iterator(); itX.hasNext();) {
                        PozycjaBilans s = (PozycjaBilans) itX.next();
                        if (s.getLevel() > maxlevel) {
                            maxlevel = s.getLevel();
                        }
                    }
                }
                PozycjaRZiSBilans p = (PozycjaRZiSBilans) it.next();
                StringBuilder pozycja = new StringBuilder("   ");
                if (p.getLevel() != 0) {
                    for (int j = 0; j < p.getLevel(); j++) {
                        pozycja.append("  ");
                    }
                }
                int lev = p.getLevel();
                pozycja.append(p.getPozycjaSymbol());
                table.addCell(ustawfrazeAlignLevel(pozycja.toString(), "left", 8, lev));
                if (p.getLevel() == 0) {
                    if (l.equals("pl")) {
                        table.addCell(ustawfrazeAlignLevel(p.getNazwa(), "left", 9, lev));
                    } else {
                        table.addCell(ustawfrazeAlignLevel(p.getDe(), "left", 9, lev));
                    }
                } else if (p.getLevel() == 1) {
                    if (l.equals("pl")) {
                        table.addCell(ustawfrazeAlignLevel(p.getNazwa(), "left", 8, lev));
                    } else {
                        table.addCell(ustawfrazeAlignLevel(p.getDe(), "left", 8, lev));
                    }
                } else {
                    if (l.equals("pl")) {
                        table.addCell(ustawfrazeAlignLevel(p.getNazwa(), "left", 8, lev));
                    } else {
                        table.addCell(ustawfrazeAlignLevel(p.getDe(), "left", 8, lev));
                    }
                }
                if (modyfikator == 5) {
                    if (p.getKwotabo() != 0.0) {
                       if (p.getLevel() == 0) {
                           table.addCell(ustawfrazeAlignLevel(String.valueOf(number.format(p.getKwotabo())), "right", 9, lev));
                       } else if (p.getLevel() == 1) {
                           table.addCell(ustawfrazeAlignLevel(String.valueOf(number.format(p.getKwotabo())), "right", 8, lev));
                       } else {
                           table.addCell(ustawfrazeAlignLevel(String.valueOf(number.format(p.getKwotabo())), "right", 7, lev));
                       }
                   } else {
                       table.addCell(ustawfrazeAlignLevel("", "right", 7, lev));
                   }   
                }
                if (p.getKwota() != 0.0) {
                    if (p.getLevel() == 0) {
                        table.addCell(ustawfrazeAlignLevel(String.valueOf(number.format(p.getKwota())), "right", 9, lev));
                    } else if (p.getLevel() == 1) {
                        table.addCell(ustawfrazeAlignLevel(String.valueOf(number.format(p.getKwota())), "right", 8, lev));
                    } else {
                        table.addCell(ustawfrazeAlignLevel(String.valueOf(number.format(p.getKwota())), "right", 7, lev));
                    }
                } else {
                    table.addCell(ustawfrazeAlignLevel("", "right", 7, lev));
                }
                if (modyfikator == 1 || modyfikator == 2) {
                    if (modyfikator != 0) {
                    if (p.getPrzyporzadkowanekonta() != null && p.getPrzyporzadkowanekonta().size() > 0) {
                        List<Konto> k = p.getPrzyporzadkowanekonta();
                        for (Iterator<Konto> itr = k.iterator(); itr.hasNext();) {
                            Konto k1 = itr.next();
                            if (Z.z(k1.getKwota()) == 0.0) {
                                itr.remove();
                            }
                        }
                        if (k.size() > 0) {
                            String konta = p.getPrzyporzadkowanekontaString();
                            table.addCell(ustawfrazeAlign(konta, "left", 7));
//                            PdfPTable subtable = dodajSubTabele(testobjects.testobjects.getTabelaBilansKonta(k), 95, 1, 7);
//                            if (subtable != null) {
//                                PdfPCell r = new PdfPCell(subtable);
//                                table.addCell(r);
//                            }
                        } else {
                            String konta = "";
                            table.addCell(ustawfrazeAlign(konta, "just", 7));
                        }
                    } else {
                        String konta = "";
                        table.addCell(ustawfrazeAlign(konta, "just", 7));
                    }
                }
//                        String konta = "";
//                            Collections.sort(p.getPrzyporzadkowanekonta(), new KontoKwotacomparator());
//                            for (KontoKwota u : p.getPrzyporzadkowanekonta()) {
//                                konta = konta+u.getKonto().getPelnynumer()+": "+Z.z(u.getKwota())+"; ";
//                            }
//                        }
//                        table.addCell(ustawfrazeAlign(konta, "just", 7));
                }
            }
            if (nazwaklasy.equals("entity.SrodekTrw")) {
                SrodekTrw p = (SrodekTrw) it.next();
                if (modyfikator == 0) {
                    if (p.getNrsrodka() == 999999) {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign("podsumowanie", "center", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getNetto())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getVat())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getUmorzeniepoczatkowe())), "right", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getOdpisrok())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getOdpismc())), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign(p.getNrsrodka(), "center", 8));
                        table.addCell(ustawfrazeAlign(p.getDatazak(), "center", 8));
                        table.addCell(ustawfrazeAlign(p.getDataprzek(), "center", 8));
                        table.addCell(ustawfrazeAlign(p.getNazwa(), "left", 8));
                        table.addCell(ustawfrazeAlign(p.getKst(), "center", 8));
                        table.addCell(ustawfrazeAlign(p.getNrwldokzak(), "left", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getNetto())), "right", 8));
                        if (p.getVat() == 0.0) {
                            table.addCell(ustawfrazeAlign("", "left", 8));
                        } else {
                            table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getVat())), "right", 8));
                        }
                        if (p.getUmorzeniepoczatkowe() == 0.0) {
                            table.addCell(ustawfrazeAlign("", "left", 8));
                        } else {
                            table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getUmorzeniepoczatkowe())), "right", 8));
                        }
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getStawka())) + "%", "center", 8));
                        if (p.getOdpisrok() != null) {
                            table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getOdpisrok())), "right", 8));
                            table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getOdpismc())), "right", 8));
                        } else {
                            table.addCell(ustawfrazeAlign("", "right", 8));
                            table.addCell(ustawfrazeAlign("", "right", 8));
                        }
                    }
                } else if (modyfikator == 2) {
                     if (p.getNrsrodka() == 999999) {
                        table.addCell(ustawfrazeAlign(i++, "center", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign("suma", "center", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getNetto())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getUmorzeniepoczatkowe())), "right", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getOdpisrok())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getOdpismc())), "right", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getVat())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getStawka())), "right", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                     } else {
                        table.addCell(ustawfrazeAlign(i++, "center", 8));
                        table.addCell(ustawfrazeAlign(p.getDataprzek(), "center", 8));
                        table.addCell(ustawfrazeAlign(p.getNazwa(), "left", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getNetto())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getUmorzeniepoczatkowe())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getStrMcePlan())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getStrOdpisyPlan())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getStrNettoPlan())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getStawka().doubleValue())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getOdpisrok()!=null?p.getOdpisrok():0)), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getOdpismc()!=null?p.getOdpismc():0)), "right", 8));
                        table.addCell(ustawfrazeAlign(p.getUmarzanyDoRokMc(), "right", 8));
                     }
                } else {
                    if (p.getNrsrodka() == 999999) {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign("podsumowanie", "center", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getNetto())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getUmorzeniepoczatkowe())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getVat())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getOdpisrok())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getOdpismc())), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign(p.getNrsrodka(), "center", 8));
                        table.addCell(ustawfrazeAlign(p.getDatazak(), "center", 8));
                        table.addCell(ustawfrazeAlign(p.getDataprzek(), "center", 8));
                        table.addCell(ustawfrazeAlign(p.getNazwa(), "left", 8));
                        table.addCell(ustawfrazeAlign(p.getNrwldokzak(), "left", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getNetto())), "right", 8));
                        if (p.getUmorzeniepoczatkowe() == 0.0) {
                            table.addCell(ustawfrazeAlign("", "left", 8));
                        } else {
                            table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getUmorzeniepoczatkowe())), "right", 8));
                        }
                        if (p.getStrNetto() == 0.0) {
                            table.addCell(ustawfrazeAlign("", "left", 8));
                        } else {
                            table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getStrNetto())), "right", 8));
                        }
                        if (p.getOdpisrok() != null) {
                            table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getOdpisrok())), "right", 8));
                            table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getOdpismc())), "right", 8));
                        } else {
                            table.addCell(ustawfrazeAlign("", "right", 8));
                            table.addCell(ustawfrazeAlign("", "right", 8));
                        }
                    }
                }
            }
            if (nazwaklasy.equals("entity.DeklaracjaVatSchemaWierszSum")) {
                DeklaracjaVatSchemaWierszSum p = (DeklaracjaVatSchemaWierszSum) it.next();
                table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 7));
                table.addCell(ustawfrazeAlign(p.getDeklaracjaVatWierszSumaryczny().getNazwapozycji(), "left", 8));
                if (p.getDeklaracjaVatWierszSumaryczny().getSumanetto() > 0.0) {
                    table.addCell(ustawfrazeAlign(p.getPolenetto(), "center", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getDeklaracjaVatWierszSumaryczny().getSumanetto())), "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign("", "center", 8));
                    table.addCell(ustawfrazeAlign("", "center", 8));
                }
                table.addCell(ustawfrazeAlign(p.getPolevat(), "center", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getDeklaracjaVatWierszSumaryczny().getSumavat())), "right", 8));
            }
             if (nazwaklasy.equals("entityfk.Wiersz")) {
                Wiersz p = (Wiersz) it.next();
                if (p.getOpisWiersza().equals("podsumowanie")) {
                    table.addCell(ustawfrazeAlign("", "left", 8));
                    table.addCell(ustawfrazeAlign("", "left", 8));
                    table.addCell(ustawfrazeAlign("", "left", 8));
                    table.addCell(ustawfrazeAlign("", "left", 8));
                    table.addCell(ustawfrazeAlign("podsumowanie", "left", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getStronaWn().getKwota())), "right", 8));
                    table.addCell(ustawfrazeAlign("", "left", 8));
                    table.addCell(ustawfrazeAlign("", "left", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getStronaMa().getKwota())), "right", 8));
                    table.addCell(ustawfrazeAlign("", "left", 8));
                    table.addCell(ustawfrazeAlign("", "left", 8));
                } else {
                    table.addCell(ustawfrazeAlign(p.getDokfk().getDatadokumentu(), "center", 7, 25f));
                    table.addCell(ustawfrazeAlign(p.getDokfk().getDokfkLP(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getDokfk().getNumerwlasnydokfk(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getIdporzadkowy(), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getOpisWiersza(), "left", 8));
                    if (p.getStronaWn() != null) {
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getStronaWn().getKwota())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getStronaWn().getKwotaPLN())), "right", 8));
                        table.addCell(ustawfrazeAlign(p.getStronaWn().getKonto().getPelnynumer(), "left", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "left", 8));
                        table.addCell(ustawfrazeAlign("", "left", 8));
                        table.addCell(ustawfrazeAlign("", "left", 8));
                    }
                    if (p.getStronaMa() != null) {
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getStronaMa().getKwota())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getStronaMa().getKwotaPLN())), "right", 8));
                        table.addCell(ustawfrazeAlign(p.getStronaMa().getKonto().getPelnynumer(), "left", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "left", 8));
                        table.addCell(ustawfrazeAlign("", "left", 8));
                        table.addCell(ustawfrazeAlign("", "left", 8));
                    }
                }
            }
             if (nazwaklasy.equals("entity.UmorzenieN")) {
                UmorzenieN p = (UmorzenieN) it.next();
                table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 7));
                table.addCell(ustawfrazeAlign(p.getRokUmorzenia(), "center", 8));
                table.addCell(ustawfrazeAlign(Mce.getNumberToMiesiac().get(p.getMcUmorzenia()), "center", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 8));
            }
            if (nazwaklasy.equals("entity.Ryczpoz")) {
                Ryczpoz p = (Ryczpoz) it.next();
                if (p.getUdzialowiec().equals("podsumowanie")) {
                    table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 8));
                    table.addCell(ustawfrazeAlign("", "center", 8));
                    table.addCell(ustawfrazeAlign(p.getUdzialowiec(), "center", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getP17())), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getP85())), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getP55())), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getP30())), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getPrzychody())), "right", 8));
                    table.addCell(ustawfrazeAlign("", "center", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getPrzychodyudzial())), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(p.getZus51()!=null ? number.format(p.getZus51()) :"0.00"), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(p.getZus52()!=null ? number.format(p.getZus52()) :"0.00"), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getNaleznazal())), "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getPkpirR()+"-"+p.getPkpirM(), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getUdzialowiec(), "left", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getP17())), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getP85())), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getP55())), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getP30())), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getPrzychody())), "right", 8));
                    table.addCell(ustawfrazeAlign(p.getUdzial(), "center", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getPrzychodyudzial())), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(p.getZus51()!=null ? number.format(p.getZus51()) :"0.00"), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(p.getZus52()!=null ? number.format(p.getZus52()) :"0.00"), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getNaleznazal())), "right", 8));
                }
            }
            if (nazwaklasy.equals("entity.VatUe")||nazwaklasy.equals("entity.Vat27")) {
                VatSuper p = (VatSuper) it.next();
                if (modyfikator == 1) {
                    if (p.getTransakcja().equals("podsum.")) {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign("podsumowanie", "center", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getNetto())), "right", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    } else {
                        table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 8));
                        table.addCell(ustawfrazeAlign(p.getTransakcja(), "center", 8));
                        table.addCell(ustawfrazeAlign(p.getKontrahent().getKrajkod(), "center", 8));
                        table.addCell(ustawfrazeAlign(p.getKontrahent().getNip(), "left", 8));
                        table.addCell(ustawfrazeAlign(p.getKontrahent().getNpelna(), "left", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getNetto())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(p.getLiczbadok()), "center", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                } else {
                    if (p.getTransakcja().equals("podsum.")) {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                        table.addCell(ustawfrazeAlign("podsumowanie", "center", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getNetto())), "right", 8));
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    } else {
                        table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 8));
                        table.addCell(ustawfrazeAlign(p.getTransakcja(), "center", 8));
                        if (p.getKontrahent().getKrajkod() != null) {
                            table.addCell(ustawfrazeAlign(p.getKontrahent().getKrajkod(), "center", 8));
                        } else {
                            table.addCell(ustawfrazeAlign("", "center", 8));
                        }
                        table.addCell(ustawfrazeAlign(p.getKontrahent().getNip(), "left", 8));
                        table.addCell(ustawfrazeAlign(p.getKontrahent().getNpelna(), "left", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getNetto())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(p.getLiczbadok()), "center", 8));
                    }
                }
            }
            if (nazwaklasy.equals("viewfk.StowRozrachCzlonkZbiorczeView$Zapisy")) {
                StowRozrachCzlonkZbiorczeView.Zapisy p = (StowRozrachCzlonkZbiorczeView.Zapisy) it.next();
                table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 7));
                if (p.getKonto() == null) {
                    table.addCell(ustawfrazeAlign("razem", "center", 8));
                } else {
                    table.addCell(ustawfrazeAlign(p.getKonto().getNazwapelna(), "left", 8));
                }
                if (p.getSumawn() != 0.0) {
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getSumawn())), "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign("", "center", 8));
                }
                if (p.getSumama() != 0.0) {
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getSumama())), "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign("", "center", 8));
                }
                if (p.getSaldo() >= 0.0) {
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getSaldo())), "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getSaldo())), "right", 8, BaseColor.RED));
                }
                if (p.getMp() != null) {
                    table.addCell(ustawfrazeAlign(p.getMp().getPoczatek(), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getMp().getNrlegitymacji(), "center", 8));
                } else {
                    table.addCell(emptyCell());
                    table.addCell(emptyCell());
                }
            }
            if (nazwaklasy.equals("embeddable.SchemaEwidencjaSuma")) {
                SchemaEwidencjaSuma p = (SchemaEwidencjaSuma) it.next();
                table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 7));
                table.addCell(ustawfrazeAlign(p.getSchemaEwidencja().getEvewidencja().getNazwa(), "left", 8));
                table.addCell(ustawfrazeAlign(p.getSchemaEwidencja().getPolenetto(), "center", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getEVatwpisSuma().getNetto())), "right", 8));
                if (p.getEVatwpisSuma().getVat().doubleValue() != 0.0) {
                    table.addCell(ustawfrazeAlign(p.getSchemaEwidencja().getPolevat(), "center", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getEVatwpisSuma().getVat())), "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign("", "center", 8));
                    table.addCell(ustawfrazeAlign("", "center", 8));
                }
            }
            if (nazwaklasy.equals("entityfk.EVatwpisDedra")) {
                EVatwpisDedra p = (EVatwpisDedra) it.next();
                table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 7, 24f));
                table.addCell(ustawfrazeAlign(p.getDatadokumentu(), "center", 7));
                table.addCell(ustawfrazeAlign(p.getMcRok(), "center", 7));
                table.addCell(ustawfrazeAlign(p.getImienazwisko(), "left", 8));
                table.addCell(ustawfrazeAlign(p.getFaktura(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getAdres(), "left", 8));
                table.addCell(ustawfrazeAlign(number.format(p.getNetto()), "right", 8));
                table.addCell(ustawfrazeAlign(number.format(p.getVat()), "right", 8));
                table.addCell(ustawfrazeAlign(p.getEstawka(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getDataoperacji(), "center", 7));
            }
            if (nazwaklasy.equals("testobjects.WierszCecha")) {
                WierszCecha p = (WierszCecha) it.next();
                table.addCell(ustawfrazeAlign(String.valueOf(p.getId()), "center", 8, 25f));
                table.addCell(ustawfrazeAlign(p.getNazwacechy(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getDokfks(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getDatawystawienia(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getDataoperacji(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getOpisWiersza(), "left", 8));
                table.addCell(ustawfrazeAlign(p.getOpiskonta(), "left", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 8));
            }
            if (nazwaklasy.equals("entity.Dok")) {
                if (modyfikator==0) {
                    Dok p = (Dok) it.next();
                    table.addCell(ustawfrazeAlign(String.valueOf(p.getNrWpkpir()), "center", 9, 25f));
                    table.addCell(ustawfrazeAlign(p.getDataWyst(), "center", 9));
                    table.addCell(ustawfrazeAlign(p.getKontr1().toString3(), "left", 9));
                    table.addCell(ustawfrazeAlign(p.getRodzajedok().getSkrot(), "center", 9));
                    table.addCell(ustawfrazeAlign(p.getNrWlDk(), "center", 9));
                    table.addCell(ustawfrazeAlign(p.getOpis(), "left", 9));
                    tablicaWkomorce(table, p, "entity.Dok", 0);
                    tablicaWkomorce(table, p, "entity.Dok", 2);
                    tablicaWkomorce(table, p, "entity.Dok", 3);
                    tablicaWkomorce(table, p, "entity.Dok", 1);
                    table.addCell(ustawfrazeAlign(p.getVatM(), "center", 9));
                    if (p.getTabelanbp()!=null ) {
                        if (p.getTabelanbp().getNrtabeli().equals("000/A/NBP/0000")) {
                            table.addCell(ustawfrazeAlign("", "left", 9));
                        } else {
                            table.addCell(ustawfrazeAlign(p.getTabelanbp().getNrtabeli()+" "+p.getTabelanbp().getKurssredniPrzelicznik(), "just", 9));
                        }
                    } else {
                        table.addCell(ustawfrazeAlign("", "left", 9));
                    }
                } else {
                    Dok p = (Dok) it.next();
                    try {
                        //System.out.println(""+p.toString());
                        table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 8, 20f));
                        table.addCell(ustawfrazeAlign(p.getDataWyst(), "center", 8));
                        table.addCell(ustawfrazeAlign(p.getKontr1().getNpelna(), "left", 8));
                        table.addCell(ustawfrazeAlign(p.getKontr1().toString4(), "left", 8));
                        table.addCell(ustawfrazeAlign(p.getNrWlDk(), "center", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getNetto())), "right", 8));
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getVat())), "right", 8));
                        if (p.getWalutadokumentu()!=null) {
                            table.addCell(ustawfrazeAlign(p.getWalutadokumentu().getSymbolwaluty().equals("PLN")? "":String.valueOf(number.format(p.getNettoWaluta())), "right", 8));
                            table.addCell(ustawfrazeAlign(p.getWalutadokumentu().getSymbolwaluty().equals("PLN")? "":String.valueOf(number.format(p.getVatWalutaCSV())), "right", 8));
                        } else {
                            table.addCell(ustawfrazeAlign("", "center", 8));
                            table.addCell(ustawfrazeAlign("", "center", 8));
                        }
                        table.addCell(ustawfrazeAlign(String.valueOf(percent.format(p.getAmazonCSV().getTaxRateD())), "right", 8));
                        table.addCell(ustawfrazeAlign(p.getAmazonCSV().getJurisdictionName(), "left", 8));
                        if (p.getTabelanbp()==null || p.getTabelanbp().getNrtabeli().equals("000/A/NBP/0000")) {
                            if (p.getWalutadokumentu()!=null) {
                                table.addCell(ustawfrazeAlign(p.getWalutadokumentu().getSymbolwaluty(), "left", 8));
                            } else {
                                table.addCell(ustawfrazeAlign("", "left", 8));
                            }
                        } else {
                            table.addCell(ustawfrazeAlign(p.getTabelanbp().getNrtabeli()+" "+p.getTabelanbp().getKurssredniPrzelicznik()+" / "+p.getWalutadokumentu().getSymbolwaluty(), "just", 8));
                        }
                    } catch (Exception e) {
                        E.e(e);
                    }
                }
            }
            if (nazwaklasy.equals("embeddablefk.KontoKwota")) {
                Konto p = (Konto) it.next();
                table.addCell(ustawfrazeAlign(p.getPelnynumer(), "left", 7));
                if (l.equals("pl")) {
                    table.addCell(ustawfrazeAlign(p.getNazwapelna(), "left", 7));
                } else {
                    table.addCell(ustawfrazeAlign(p.getDe(), "left", 7));
                }
                
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 7));
            }
            if (nazwaklasy.equals("testobjects.WierszDokfk")) {
                WierszDokfk p = (WierszDokfk) it.next();
                table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 8, 32f));
                table.addCell(ustawfrazeAlign(p.getDatadok(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getDataoperacji(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getIddok(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getKontrahent(), "left", 8));
                table.addCell(ustawfrazeAlign(p.getNrwlasny(), "left", 8));
                table.addCell(ustawfrazeAlign(p.getOpis(), "left", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getWartosc())), "right", 8));
                table.addCell(ustawfrazeAlign(p.getWaluta(), "center", 8));
            }
            if (nazwaklasy.equals("entityfk.StronaWiersza")) {
                if (modyfikator == 0 || modyfikator == 1) {
                    StronaWiersza p = (StronaWiersza) it.next();
                    table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 7));
                    String nw = p.getNazwaWaluty().equals("PLN") ? p.getNazwaWaluty() : p.getNazwaWaluty()+" "+p.getKursWalutyBOSW();
                    table.addCell(ustawfrazeAlign(nw, "center", 7));
                    table.addCell(ustawfrazeAlign(p.getDokfk().getDataoperacji(), "left", 7));
                    table.addCell(ustawfrazeAlign(p.getDokfkS()+" "+p.getDokfk().getNumerwlasnydokfk(), "center", 7));
                    table.addCell(ustawfrazeAlign(p.getWiersz().getOpisWiersza(), "left", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getRozliczono())), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getPozostalo())), "right", 8));
                    if(modyfikator==1) {
                        PdfPTable subtable = dodajSubTabele(testobjects.testobjects.getTabelaTransakcje(p.getPlatnosci()),95,1, 8);
                        if (subtable != null) {
                            PdfPCell r = new PdfPCell(subtable);
                            //r.setRightIndent(30f);
                            r.setColspan(8);
                            table.addCell(r);
                        }
                    }
                } else {
                    //PdfKontoZapisy drukujzapisyKompakt
                    StronaWiersza p = (StronaWiersza) it.next();
                    table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 7));
                    try {
                    if (p.getDokfk() != null && p.getDokfk().getRodzajedok() != null && p.getDokfk().getRodzajedok().getKategoriadokumentu()==0) {
                        table.addCell(ustawfrazeAlign(p.getDataWierszaPelna(), "center", 7));
                    } else {
                        table.addCell(ustawfrazeAlign(p.getDataOperacji(), "center", 7));
                    }
                    } catch (Exception e) {
                        table.addCell(ustawfrazeAlign("", "center", 7));
                    }
                    table.addCell(ustawfrazeAlign(p.getDokfkS(), "center", 7));
                    table.addCell(ustawfrazeAlign(p.getIdporzadkowy() > 0 ? p.getIdporzadkowy(): "", "center", 7));
                    table.addCell(ustawfrazeAlign(p.getNumerwlasnydokfk(), "center", 7));
                    table.addCell(ustawfrazeAlign(p.getOpisWiersza(34), "left", 7));
                    if (p.isWn()) {
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 7));
                    } else {
                        table.addCell(ustawfrazeAlign("", "right", 7));
                    }
                    if (p.isWn()) {
                        table.addCell(ustawfrazeAlign("", "right", 7));
                    } else {
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwota())), "right", 7));
                    }
                    table.addCell(ustawfrazeAlign(p.getSymbolWalutPrint(), "center", 7));
                    table.addCell(ustawfrazeAlign(p.getKontoPrzeciwstawneNumer(), "left", 7));
                }
            }
            if (nazwaklasy.equals("embeddablefk.KontoBO")) {
                    KontoBO p = (KontoBO) it.next();
                    table.addCell(ustawfrazeAlign(i++, "left", 8, 22f));
                    table.addCell(ustawfrazeAlign(p.getPelnynumer(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getNazwapelna(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getZwyklerozrachszczegolne(), "center", 8));
                    table.addCell(ustawfrazeAlign(F.numberS(p.getBoWn()), "right", 8));
//                    double roznica = p.getBoWn() - p.getBoMa();
//                    double kwota = roznica > 0.0 ? roznica: 0.0;
//                    if (kwota != 0.0) {
//                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
//                    } else {
//                        table.addCell(ustawfrazeAlign("", "center", 8));
//                    }
                    table.addCell(ustawfrazeAlign(F.numberS(p.getSaldorokpopWn()), "right", 8));
                    table.addCell(ustawfrazeAlign(F.numberS(p.getBoMa()), "right", 8));
//                    kwota = roznica < 0 ? -roznica : 0.0;
//                    if (kwota != 0.0) {
//                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
//                    } else {
//                        table.addCell(ustawfrazeAlign("", "center", 8));
//                    }
                    table.addCell(ustawfrazeAlign(F.numberS(p.getSaldorokpopMa()), "right", 8));
                    table.addCell(ustawfrazeAlign(F.numberS(p.getRoznicaWn()), "right", 8));
                    table.addCell(ustawfrazeAlign(F.numberS(p.getRoznicaMa()), "right", 8));
            }
            if (nazwaklasy.equals("entityfk.Konto")) {
                if (modyfikator == 1) {
                    Konto p = (Konto) it.next();
                    table.addCell(ustawfrazeAlign(i++, "left", 8, 22f));
                    table.addCell(ustawfrazeAlign(p.getPelnynumer(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getNazwapelna(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getZwyklerozrachszczegolne(), "center", 8));
                    String pozycjaWn = p.getKontopozycjaID() != null ? p.getKontopozycjaID().getPozycjaWn() : "brak przyp.Wn";
                    table.addCell(ustawfrazeAlign(pozycjaWn, "center", 8));
                    String pozycjaMa = p.getKontopozycjaID() != null ? p.getKontopozycjaID().getPozycjaMa() : "brak przyp.Ma";
                    table.addCell(ustawfrazeAlign(pozycjaMa, "center", 8));
                    double roznica = p.getBoWn() - p.getBoMa();
                    double kwota = roznica > 0.0 ? roznica: 0.0;
                    if (kwota != 0.0) {
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                    kwota = roznica < 0 ? -roznica : 0.0;
                    if (kwota != 0.0) {
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                } else if (modyfikator == 2) {
                    Konto p = (Konto) it.next();
                    table.addCell(ustawfrazeAlign(i++, "left", 7, 22f));
                    table.addCell(ustawfrazeAlign(p.getPelnynumer(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getNazwapelna(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getNazwaskrocona(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getDe(), "left", 8));
                } else {
                    Konto p = (Konto) it.next();
                    table.addCell(ustawfrazeAlign(p.getPelnynumer(), "left", 8, 22f));
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
                if (modyfikator == 0) {
                    WierszBO p = (WierszBO) it.next();
                    table.addCell(ustawfrazeAlign(i++, "left", 7, 22f));
                    table.addCell(ustawfrazeAlign(p.getKonto().getPelnynumer()+" "+p.getKonto().getNazwapelna(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getOpis(), "left", 8));
                    double kwota = p.getKurs();
                    if (kwota > 0.0) {
                        number.setMinimumFractionDigits(4);
                        number.setMaximumFractionDigits(4);
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                    String waluta = p.getWaluta().getSymbolwaluty();
                    table.addCell(ustawfrazeAlign(waluta, "center", 8));
                    kwota = p.getKwotaWn(); 
                    number.setMinimumFractionDigits(2);
                    number.setMaximumFractionDigits(2);
                    if (kwota != 0.0) {
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                    kwota = p.getKwotaWnPLN();
                    if (kwota != 0.0 && !waluta.equals("PLN")) {
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                    kwota = p.getKwotaMa();
                    if (kwota != 0.0) {
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                    kwota = p.getKwotaMaPLN();
                    if (kwota != 0.0 && !waluta.equals("PLN")) {
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                } else if (modyfikator == 2) {
                    WierszBO p = (WierszBO) it.next();
                    table.addCell(ustawfrazeAlign(i++, "left", 8, 22f));
                    table.addCell(ustawfrazeAlign(p.getKonto().getPelnynumer()+" "+p.getKonto().getNazwapelna(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getOpis(), "left", 8));
                    double kwota = p.getKurs();
                    if (kwota > 0.0) {
                        number.setMinimumFractionDigits(4);
                        number.setMaximumFractionDigits(4);
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                    String waluta = p.getWaluta().getSymbolwaluty();
                    table.addCell(ustawfrazeAlign(waluta, "center", 8));
                    kwota = p.getKwotaWn(); 
                    number.setMinimumFractionDigits(2);
                    number.setMaximumFractionDigits(2);
                    if (kwota != 0.0) {
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                    kwota = p.getKwotaWnPLN();
                    if (kwota != 0.0 && !waluta.equals("PLN")) {
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                    kwota = p.getKwotaMa();
                    if (kwota != 0.0) {
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                    kwota = p.getKwotaMaPLN();
                    if (kwota != 0.0 && !waluta.equals("PLN")) {
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                } else {
                    WierszBO p = (WierszBO) it.next();
                    table.addCell(ustawfrazeAlign(i++, "left", 7, 22f));
                    table.addCell(ustawfrazeAlign(p.getKonto().getPelnynumer()+" "+p.getKonto().getNazwapelna(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getOpis(), "left", 8));
                    double kwota = p.getKwotaWn(); 
                    String waluta = p.getWaluta().getSymbolwaluty();
                    number.setMinimumFractionDigits(2);
                    number.setMaximumFractionDigits(2);
                    if (kwota != 0) {
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                    kwota = p.getKwotaWnPLN();
                    if (kwota != 0 && !waluta.equals("PLN")) {
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                    kwota = p.getKwotaMa();
                    if (kwota != 0.0) {
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                    kwota = p.getKwotaMaPLN();
                    if (kwota != 0.0 && !waluta.equals("PLN")) {
                        table.addCell(ustawfrazeAlign(number.format(kwota), "right", 8));
                    } else {
                        table.addCell(ustawfrazeAlign("", "center", 8));
                    }
                }
            }
            if (nazwaklasy.equals("entityfk.Transakcja")) {
                if (modyfikator == 1) {
                    Transakcja p = (Transakcja) it.next();
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getKwotatransakcji())), "rigth", 8));
                    table.addCell(ustawfrazeAlign(p.getDatarozrachunku(), "right", 8));
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getRozliczajacy().getKwotaR())), "right", 8));
                    table.addCell(ustawfrazeAlign(p.getRozliczajacy().getDokfkS(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getRozliczajacy().getWiersz().getIdporzadkowy(), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getRozliczajacy().getWiersz().getDokfk().getNumerwlasnydokfk(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getRozliczajacy().getWiersz().getOpisWiersza(), "left", 8));
                    table.addCell(ustawfrazeAlign(p.getRozliczajacy().getKonto().getPelnynumer(), "right", 8));
                } else {
                    Transakcja p = (Transakcja) it.next();
                    table.addCell(ustawfrazeAlign(i++, "center", 8));
                    String rachunek = p.getNowaTransakcja().getWiersz().getOpisWiersza()+"/"+p.getNowaTransakcja().getWiersz().getDokfkS();
                    table.addCell(ustawfrazeAlign(rachunek, "left", 8));
                    double kurs = p.getNowaTransakcja().getKursBO() != 0.0 ? p.getNowaTransakcja().getKursBO() : p.getNowaTransakcja().getWiersz().getTabelanbp().getKurssredniPrzelicznik();
                    table.addCell(ustawfrazeAlign(number.format(kurs), "right", 8));
                    String platnosc = p.getRozliczajacy().getWiersz().getOpisWiersza()+"/"+p.getRozliczajacy().getWiersz().getDokfkS();
                    table.addCell(ustawfrazeAlign(platnosc, "left", 8));
                    kurs = p.getRozliczajacy().getKursBO() != 0.0 ? p.getRozliczajacy().getKursBO() : p.getRozliczajacy().getWiersz().getTabelanbp().getKurssredni();
                    table.addCell(ustawfrazeAlign(number.format(kurs), "right", 8));
                    table.addCell(ustawfrazeAlign(p.getDatarozrachunku(), "center", 8));
                    table.addCell(ustawfrazeAlign(number.format(p.getRoznicekursowe()), "right", 8));
                    table.addCell(ustawfrazeAlign(p.getNowaTransakcja().getKonto().getPelnynumer(), "right", 8));
                }
            }
            if (nazwaklasy.equals("testobjects.WierszKonta")) {
                if (modyfikator == 0) {
                    WierszKonta p = (WierszKonta) it.next();
                    table.addCell(ustawfrazeAlign(String.valueOf(p.getLp()), "center", 8, 25f));
                    table.addCell(ustawfrazeAlign(p.getOpis(), "left", 8));
                        if (p.getKwotaWn() != 0.0) {
                            table.addCell(ustawfrazeAlign(number.format(p.getKwotaWn()), "right", 8));
                        } else {
                            table.addCell(ustawfrazeAlign("", "right", 8));
                        }
                        table.addCell(ustawfrazeAlign(p.getOpiskontaWn(), "left", 8));
                        if (p.getKwotaMa() != 0.0) {
                            table.addCell(ustawfrazeAlign(number.format(p.getKwotaMa()), "right", 8));
                        } else {
                            table.addCell(ustawfrazeAlign("", "right", 8));
                        }
                        table.addCell(ustawfrazeAlign(p.getOpiskontaMa(), "left", 8));
                        table.addCell(ustawfrazeAlign(p.getWaluta(), "center", 7));
                        if (p.getWaluta() != null && !p.getWaluta().equals("PLN") && !p.getOpis().equals("podsumowanie")) {
                            table.addCell(ustawfrazeAlign("", "center", 8, 25f));
                            String opis = "brak wprowadzoej tabeli NBP";
                            if (p.getTabela() != null) {
                                opis = "wartość w pln "+p.getTabela().getNrtabeli()+" z "+p.getTabela().getDatatabeli()+" k.w. "+p.getKurs();
                            }
                            table.addCell(ustawfrazeAlign(opis, "left", 8));
                            if (p.getKwotaWn() != 0.0) {
                                table.addCell(ustawfrazeAlign(number.format(p.getKwotaWnPLN()), "right", 8));
                            } else {
                                table.addCell(ustawfrazeAlign("", "right", 8));
                            }
                            table.addCell(ustawfrazeAlign("", "left", 7));
                            if (p.getKwotaMa() != 0.0) {
                                table.addCell(ustawfrazeAlign(number.format(p.getKwotaMaPLN()), "right", 8));
                            } else {
                                table.addCell(ustawfrazeAlign("", "right", 8));
                            }
                            table.addCell(ustawfrazeAlign("", "left", 7));
                            table.addCell(ustawfrazeAlign("PLN", "center", 7));
                        }
                } else {
                    WierszKonta p = (WierszKonta) it.next();
                    table.addCell(ustawfrazeAlign(String.valueOf(p.getLp()), "center", 8));
                    table.addCell(ustawfrazeAlign(p.getOpis(), "left", 8));
                        if (p.getKwotaWn() != 0.0) {
                            table.addCell(ustawfrazeAlign(number.format(p.getKwotaWn()), "right", 8));
                        } else {
                            table.addCell(ustawfrazeAlign("", "right", 8));
                        }
                        table.addCell(ustawfrazeAlign(p.getOpiskontaWn(), "left", 8));
                        if (p.getKwotaMa() != 0.0) {
                            table.addCell(ustawfrazeAlign(number.format(p.getKwotaMa()), "right", 8));
                        } else {
                            table.addCell(ustawfrazeAlign("", "right", 8));
                        }
                        table.addCell(ustawfrazeAlign(p.getOpiskontaMa(), "left", 8));
                        table.addCell(ustawfrazeAlign(number.format(p.getSaldo()), "right", 8));
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
                table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 8, 20f));
                table.addCell(ustawfrazeAlign(p.getDatawystawienia(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getNumerkolejny(), "center", 8));
                table.addCell(ustawfrazeAlign(p.getKontrahent().getNpelna()+" "+p.getKontrahent().getNip(), "left", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(p.getPozycjenafakturze().get(0).getNazwa()), "left", 8));
                table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getBrutto())), "right", 8));
                table.addCell(ustawfrazeAlign(p.getTerminzaplaty(), "center", 8));
            }
            if (nazwaklasy.equals("embeddable.ZestawienieRyczalt")) {
                ZestawienieRyczalt p = (ZestawienieRyczalt) it.next();
                table.addCell(ustawfrazeAlign(String.valueOf(i++), "center", 8));
                table.addCell(ustawfrazeAlign(p.getOkres(), "left", 8));
                if (p.getS170() != 0.0) {
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getS170())), "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign("", "left", 8));
                }
                if (p.getS85() != 0.0) {
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getS85())), "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign("", "left", 8));
                }
                if (p.getS55() != 0.0) {
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getS55())), "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign("", "left", 8));
                }
                if (p.getS30() != 0.0) {
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getS30())), "right", 8));
                } else {
                    table.addCell(ustawfrazeAlign("", "left", 8));
                }
            }
        }
    }
    
    private static void ustawwierszeNar(PdfPTable table, List wiersze, String nazwaklasy, int modyfikator, List<String> mce) {
        Locale locale = FacesContext.getCurrentInstance().getViewRoot().getLocale();
        String l = locale.getLanguage();
        NumberFormat currency = getCurrencyFormater();
        NumberFormat number = getNumberFormater();
        int i = 1;
        int maxlevel = 0;
        for (Iterator it = wiersze.iterator(); it.hasNext();) {
            if (maxlevel == 0) {
                for (Iterator<PozycjaRZiS> itX = wiersze.iterator(); itX.hasNext();) {
                    PozycjaRZiS s = (PozycjaRZiS) itX.next();
                    if (s.getLevel() > maxlevel) {
                        maxlevel = s.getLevel();
                    }
                }
            }
            PozycjaRZiS p = (PozycjaRZiS) it.next();
            int levelPlus = p.getLevel() + 1;
            if (p.getLevel() != 0) {
                for (int j = 0; j < p.getLevel(); j++) {
                    table.addCell(ustawfrazeAlign("", "l", 7,15f));
                }
            }
            table.addCell(ustawfrazeAlign(p.getPozycjaSymbol(), "center", 8));
            if (p.getLevel() < maxlevel) {
                for (int k = levelPlus; k <= maxlevel; k++) {
                    table.addCell(ustawfrazeAlign("", "l", 8));
                }
            }
            if (p.getLevel() == 0) {
                if (l.equals("pl")) {
                    table.addCell(ustawfrazeAlign(p.getNazwa(), "left", 8));
                } else {
                    table.addCell(ustawfrazeAlign(p.getDe(), "left", 8));
                }
            } else {
                if (l.equals("pl")) {
                    table.addCell(ustawfrazeAlign(p.getNazwa(), "left", 8));
                } else {
                    table.addCell(ustawfrazeAlign(p.getDe(), "left", 8));
                }
            }
            int czcionka = 9;
            if (mce.size() > 9) {
                czcionka = 7;
            } else if (mce.size() > 5) {
                czcionka = 8;
            }
            for (String m : mce) {
                if (p.getMce().get(m) != 0.0) {
                    table.addCell(ustawfrazeAlign(String.valueOf(number.format(p.getMce().get(m))), "right", czcionka));
                } else {
                    table.addCell(ustawfrazeAlign("", "right", czcionka));
                }
            }
        }
    }

    private static void tablicaWkomorce(PdfPTable table, Object p, String nazwaklasy, int modyfikator) {
        if (nazwaklasy.equals("entity.Dok")) {
            if (modyfikator == 0) {
                try {
                    NumberFormat number = getNumberFormater();
                    Dok d = (Dok) p;
                    if (d.getTabelanbp()==null || d.getTabelanbp().getNrtabeli().equals("000/A/NBP/0000")) {
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(d.getNetto())), "right", 9));
                    } else {
                        PdfPTable t = new PdfPTable(1);
                        t.getDefaultCell().setBorderWidth(0f);
                        int[] col = new int[1];
                        col[0] = 2;
                        t.setWidths(col);
                        t.setWidthPercentage(100);
                        t.addCell(ustawfrazeAlignNOBorder(String.valueOf(number.format(d.getNetto())), "right", 9));
                        t.addCell(ustawfrazeAlignNOBorder(d.getTabelanbp().getWaluta().getSkrotsymbolu()+" "+String.valueOf(number.format(d.getNettoWaluta())), "right", 9));
                        table.addCell(t);
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            } else if (modyfikator == 2) {
                try {
                    NumberFormat number = getNumberFormater();
                    Dok d = (Dok) p;
                    if (d.getTabelanbp()==null || d.getTabelanbp().getNrtabeli().equals("000/A/NBP/0000")) {
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(d.getVat())), "right", 9));
                    } else {
                        PdfPTable t = new PdfPTable(1);
                        t.getDefaultCell().setBorderWidth(0f);
                        int[] col = new int[1];
                        col[0] = 2;
                        t.setWidths(col);
                        t.setWidthPercentage(100);
                        t.addCell(ustawfrazeAlignNOBorder(String.valueOf(number.format(d.getVat())), "right", 9));
                        t.addCell(ustawfrazeAlignNOBorder(d.getTabelanbp().getWaluta().getSkrotsymbolu()+" "+String.valueOf(number.format(d.getVatWaluta())), "right", 9));
                        table.addCell(t);
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            } else if (modyfikator == 3) {
                try {
                    NumberFormat number = getNumberFormater();
                    Dok d = (Dok) p;
                    if (d.getTabelanbp()==null || d.getTabelanbp().getNrtabeli().equals("000/A/NBP/0000")) {
                        table.addCell(ustawfrazeAlign(String.valueOf(number.format(d.getBrutto())), "right", 9));
                    } else {
                        PdfPTable t = new PdfPTable(1);
                        t.getDefaultCell().setBorderWidth(0f);
                        int[] col = new int[1];
                        col[0] = 2;
                        t.setWidths(col);
                        t.setWidthPercentage(100);
                        t.addCell(ustawfrazeAlignNOBorder(String.valueOf(number.format(d.getBrutto())), "right", 9));
                        t.addCell(ustawfrazeAlignNOBorder(d.getTabelanbp().getWaluta().getSkrotsymbolu()+" "+String.valueOf(number.format(d.getBruttoWaluta())), "right", 9));
                        table.addCell(t);
                    }
                } catch (Exception e) {
                    E.e(e);
                }
            } else {
                 try {
                    NumberFormat number = getNumberFormater();
                    Dok d = (Dok) p;
                    PdfPTable t = new PdfPTable(2);
                    t.getDefaultCell().setBorderWidth(0f);
                    int[] col = new int[2];
                    col[0] = 3;
                    col[1] = 2;
                    t.setWidths(col);
                    t.setWidthPercentage(100);
                    for (KwotaKolumna1 r : d.getListakwot1()) {
                        t.addCell(ustawfrazeAlignNOBorder(r.getNazwakolumny(), "left", 9));
                        t.addCell(ustawfrazeAlignNOBorder(String.valueOf(number.format(r.getNetto())), "right", 9));
                    }
                    table.addCell(t);
                } catch (Exception e) {
                    E.e(e);
                }
            }
        }
    }
 
//    public static void main(String[] args) throws Exception {
//
//		NumberFormat format1 = NumberFormat.getInstance();
//		displayNumbers("default", format1);
//
//		NumberFormat format2 = NumberFormat.getInstance();
//		format2.setMinimumFractionDigits(2);
//		format2.setMaximumFractionDigits(4);
//		displayNumbers("min fraction digits 2, max fraction digits 4", format2);
//
//		NumberFormat format3 = NumberFormat.getInstance();
//		format3.setMinimumIntegerDigits(6);
//		displayNumbers("min integer digits 6", format3);
//
//		NumberFormat format4 = NumberFormat.getInstance();
//		format4.setMaximumIntegerDigits(5);
//		displayNumbers("max integer digits 5", format4);
//
//		NumberFormat format5 = NumberFormat.getInstance();
//		format5.setGroupingUsed(false);
//		displayNumbers("grouping off", format5);
//
//	}

	public static void displayNumbers(String whichFormat, NumberFormat numberFormat) {
		//System.out.println("Format:" + whichFormat);
		for (int i = 0; i <= 10; i++) {
			double num = Math.PI * Math.pow(i, i) * i;
			System.out.print("  formatted:" + numberFormat.format(num));
		}
	}
}
