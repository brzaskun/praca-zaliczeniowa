/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import entity.Angaz;
import entity.FirmaKadry;
import entity.Rozwiazanieumowy;
import entity.Umowa;
import entity.Zmiennawynagrodzenia;
import error.E;
import java.io.ByteArrayOutputStream;
import java.util.List;
import msg.Msg;
import org.primefaces.PrimeFaces;
import static pdf.PdfMain.finalizacjaDokumentuQR;
import static pdf.PdfMain.inicjacjaWritera;
import static pdf.PdfMain.naglowekStopkaP;
import static pdf.PdfMain.otwarcieDokumentu;

/**
 *
 * @author Osito
 */
public class PdfWypowiedzenie {
  
    
    
    
    
    public static void drukuj(Rozwiazanieumowy rozwiazanieumowy, String nazwa) {
        try {
            Angaz angaz = rozwiazanieumowy.getUmowa().getAngaz();
            if (angaz != null) {
                Document document = PdfMain.inicjacjaA4Portrait(80,60);
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                dodajtrescPracodawca(rozwiazanieumowy, document, rozwiazanieumowy.getDatadokumentu(), rozwiazanieumowy.getDatawypowiedzenia());
                finalizacjaDokumentuQR(document,nazwa);
                String f = "pokazwydruk('"+nazwa+"');";
                PrimeFaces.current().executeScript(f);
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public static ByteArrayOutputStream drukujMail(Rozwiazanieumowy rozwiazanieumowy, String  nazwa) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(); 
        try {
            Angaz angaz = rozwiazanieumowy.getUmowa().getAngaz();
            if (angaz != null) {
                Document document = PdfMain.inicjacjaA4Portrait(80,60);
                PdfWriter writer = PdfWriter.getInstance(document, out);
                writer.setInitialLeading(16);
                writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                dodajtrescPracodawca(rozwiazanieumowy, document, rozwiazanieumowy.getDatadokumentu(), rozwiazanieumowy.getDatawypowiedzenia());
                finalizacjaDokumentuQR(document,nazwa);
                String f = "pokazwydruk('"+nazwa+"');";
                PrimeFaces.current().executeScript(f);
            } else {
                Msg.msg("w", "Nie ma Paska do wydruku");
            }
        } catch (Exception e) {
            E.e(e);
        }
        return out;
    }
    
    public static void drukujwszystkie(List<Rozwiazanieumowy> listaumowy, String innewarunkizatrudnienia, FirmaKadry firmaKadry, String dataaneksu, String odkiedyzmiana) {
        try {
            String nazwa = firmaKadry.getNip()+"rozwiazanieUmowy.pdf";
            if (listaumowy != null) {
                Document document = PdfMain.inicjacjaA4Portrait(80,60);
                PdfWriter writer = inicjacjaWritera(document, nazwa);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                int licznik = 0;
                for (Rozwiazanieumowy roz : listaumowy) {
                    Zmiennawynagrodzenia p = roz.getUmowa().getZmiennawynagrodzenia();
                    if (p!=null&&p.getNowakwota()>0.0 || (innewarunkizatrudnienia!=null&&!innewarunkizatrudnienia.isEmpty())) {
                        if (licznik>0) {
                            document.newPage();
                        }
                        Angaz angaz = p.getSkladnikwynagrodzenia().getAngaz();
                        dodajtrescPracodawca(roz,document, dataaneksu, odkiedyzmiana);
                        licznik++;
                    }
                }
                finalizacjaDokumentuQR(document,nazwa);
                String f = "pokazwydruk('"+nazwa+"');";
                PrimeFaces.current().executeScript(f);
                Msg.msg("Wydrukowano aneksy");
            } else {
                Msg.msg("w", "Nie ma danych");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie wprowadzono kwot");
        }
    }
    
    public static ByteArrayOutputStream drukujanekswszystkieMail(List<Rozwiazanieumowy> listaumowy, String  innewarunkizatrudnienia, FirmaKadry firmaKadry, String dataaneksu, String odkiedyzmiana) {
        ByteArrayOutputStream out = new ByteArrayOutputStream(); 
        try {
            String nazwa = firmaKadry.getNip()+"rozwiazanieUmowy.pdf";
            if (listaumowy != null) {
                Document document = PdfMain.inicjacjaA4Portrait(80,60);
                PdfWriter writer = PdfWriter.getInstance(document, out);
                writer.setInitialLeading(16);
                writer.setViewerPreferences(PdfWriter.PageLayoutSinglePage);
                naglowekStopkaP(writer);
                otwarcieDokumentu(document, nazwa);
                int licznik = 0;
                for (Rozwiazanieumowy roz : listaumowy) {
                    Zmiennawynagrodzenia p = roz.getUmowa().getZmiennawynagrodzenia();
                    if (roz!=null&&roz.getDatawypowiedzenia()!=null) {
                        if (licznik>0) {
                            document.newPage();
                        }
                        dodajtrescPracodawca(roz, document, dataaneksu, odkiedyzmiana);
                        licznik++;
                    }
                }
                finalizacjaDokumentuQR(document,nazwa);
                Msg.msg("Przygotowano aneksy dla maila");
            } else {
                Msg.msg("w", "Nie ma danych");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Nie wprowadzono kwot");
        }
        return out;
    }
     
     private static void dodajtrescPracodawca(Rozwiazanieumowy rozwiazanieumowy, Document document, String dataaneksu, String datazmiany) {
        try {
            Umowa umowa = rozwiazanieumowy.getUmowa();
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 11);
            Font fontM = new Font(helvetica, 9);
            Font fontS = new Font(helvetica, 6);
            FirmaKadry firma = umowa.getAngaz().getFirma();
            Paragraph paragraph = new Paragraph(new Phrase(firma.getMiasto()+", dnia "+dataaneksu, fontM));
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("Pracodawca:", fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase(firma.getNazwa(), fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("z siedzibą w : ", fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase(firma.getAdres(), fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("NIP : "+firma.getNip(), fontM));
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            String naglowek = umowa.getUmowakodzus().isPraca()?"ROZWIĄZANIE UMOWY O PRACĘ":"ROZWIĄZANIE UMOWY ZLECENIA";
            PdfMain.dodajLinieOpisuBezOdstepu(document, naglowek, Element.ALIGN_CENTER, 3);
            String osoba = umowa.getUmowakodzus().isPraca()?"przez pracodawcę":"przez zleceniodawcę";
            if (rozwiazanieumowy.isPracownik()) {
                osoba = umowa.getUmowakodzus().isPraca()?"przez pracownika":"przez zleceniobiorcę";
            }
            PdfMain.dodajLinieOpisu(document, osoba, Element.ALIGN_CENTER, 2);
            document.add(Chunk.NEWLINE);
            String osoba1 = umowa.getUmowakodzus().isPraca()?" pracodawcą ":" zleceniodawcą ";
            paragraph = new Paragraph(new Phrase("zawartej w dniu "+umowa.getDatazawarcia()+" pomiędzy"+osoba1, fontM));
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase(firma.getNazwa(), fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase(firma.getAdres(), fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("NIP : "+firma.getNip(), fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("reprezentowanym przez: "+firma.getReprezentant(), fontM));
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            String osoba2 = umowa.getUmowakodzus().isPraca()?" pracownikiem ":" zleceniobiorcą ";
            paragraph = new Paragraph(new Phrase("a "+osoba2, fontM));
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase(umowa.getPracownik().getNazwiskoImie(), fontM));
            document.add(paragraph);
            if (umowa.getPracownik().getPlec()!=null && umowa.getPracownik().getPlec().equals("K")) {
                paragraph = new Paragraph(new Phrase("zamieszkałą w "+umowa.getPracownik().getAdres(), fontM));
            } else {
                paragraph = new Paragraph(new Phrase("zamieszkałym w "+umowa.getPracownik().getAdres(), fontM));
            }
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            if (rozwiazanieumowy.isPracodawca()&&!rozwiazanieumowy.isPorozumienie()) {
                if (rozwiazanieumowy.isWypowiedzenie()&&!rozwiazanieumowy.isSkroceneiokresuwyp()) {
                    String wypowiadam = "Niniejszym wypowiadam ww umowę z zachowaniem okresu wypowiedzenia, który upływa dnia "+rozwiazanieumowy.getDatauplywuokresuwyp();
                    PdfMain.dodajLinieOpisu(document, wypowiadam, Element.ALIGN_JUSTIFIED, 1);
                    String przyczyna = "Przyczyna wypowiedzenia: "+rozwiazanieumowy.getPrzyczyna();
                    PdfMain.dodajLinieOpisu(document, przyczyna, Element.ALIGN_JUSTIFIED, 1);
                } else if (rozwiazanieumowy.isWypowiedzenie()&&rozwiazanieumowy.isSkroceneiokresuwyp()) { 
                    String wypowiadam = "Niniejszym wypowiadam ww umowę bez zachowania okresu wypowiedzenia";
                    PdfMain.dodajLinieOpisu(document, wypowiadam, Element.ALIGN_JUSTIFIED, 1);
                    String przyczyna = "Przyczyna wypowiedzenia: "+rozwiazanieumowy.getPrzyczyna();
                    PdfMain.dodajLinieOpisu(document, przyczyna, Element.ALIGN_JUSTIFIED, 1);
                }
                String pouczenie =  "Jednocześnie informuję, że przysługuje Panu/ Pani, prawo do wniesienia odwołania do "+firma.getSadpracy()+" w terminie 21 dni od dnia otrzymania niniejszego zawiadomienia.";
                PdfMain.dodajLinieOpisu(document, pouczenie, Element.ALIGN_JUSTIFIED, 1);
            } else if (rozwiazanieumowy.isPorozumienie()) {
                    String wypowiadam = "Proponuję rozwiązac wyżej wymienioną umowę za porozumieniem stron z dniem "+rozwiazanieumowy.getDatawypowiedzenia();
                    PdfMain.dodajLinieOpisu(document, wypowiadam, Element.ALIGN_JUSTIFIED, 1);
                } 
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            //document.add(new Paragraph(new Phrase(rozwiazanieUmowyNowe.getUmowa().getPracownik().getNazwiskoImie()+"                                                          "+rozwiazanieUmowyNowe.getUmowa().getAngaz().getFirma().getReprezentant(), fontM)));
            if (umowa.getUmowakodzus().isPraca()) {
                Paragraph p = new Paragraph();
                p.add(new Phrase("...................................", fontM));
                document.add(p);
                p = new Paragraph();
                p.add(new Phrase("potwierdzam otrzymanie wypowiedzenia", fontS));
                p.setTabSettings(new TabSettings(300));
                p.add(Chunk.TABBING);
                p.add(new Phrase(umowa.getAngaz().getFirma().getReprezentant(), fontM));
                document.add(p);
                p = new Paragraph();
                p.add(new Phrase("(data i podpis pracownika)", fontS));
                p.setTabSettings(new TabSettings(300));
                p.add(Chunk.TABBING);
                p.add(new Phrase("(podpis pracodawcy lub osoby rep. pracodawcę)", fontS));
                document.add(p);
            } else {
                Paragraph p = new Paragraph();
                p.add(new Phrase("...................................", fontM));
                document.add(p);
                p = new Paragraph();
                p.add(new Phrase("potwierdzam otrzymanie wypowiedzenia", fontS));
                p.setTabSettings(new TabSettings(300));
                p.add(Chunk.TABBING);
                p.add(new Phrase(umowa.getAngaz().getFirma().getReprezentant(), fontM));
                document.add(p);
                p = new Paragraph();
                p.add(new Phrase("(data i podpis Zleceniobiorcy)", fontS));
                p.setTabSettings(new TabSettings(300));
                p.add(Chunk.TABBING);
                p.add(new Phrase("(podpis Zleceniodawcy lub osoby rep. Zlec.)", fontS));
                document.add(p);
            }
        } catch (Exception ex) {
            E.e(ex);
        }
    }

    
      
}

