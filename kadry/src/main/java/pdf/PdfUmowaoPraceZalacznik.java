/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pdf;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPTable;
import entity.FirmaKadry;
import entity.Umowa;
import error.E;
import java.util.logging.Level;
import java.util.logging.Logger;
import static pdf.PdfFont.ustawfrazeAlign;

/**
 *
 * @author Osito
 */
public class PdfUmowaoPraceZalacznik {
    
    
    public static void dodajtresc(Umowa umowa, Document document) {
        try {
            BaseFont helvetica = BaseFont.createFont(BaseFont.HELVETICA, BaseFont.CP1250, BaseFont.EMBEDDED);
            Font font = new Font(helvetica, 11);
            Font fontM = new Font(helvetica, 9);
            Font fontS = new Font(helvetica, 6);
            FirmaKadry firma = umowa.getAngaz().getFirma();
            Paragraph paragraph = new Paragraph(new Phrase(firma.getMiasto()+", dnia "+umowa.getDatazawarcia(), fontM));
            paragraph.setAlignment(Element.ALIGN_RIGHT);
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("Dane pracodawcy:", fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase(firma.getNazwa(), fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase(firma.getAdres(), fontM));
            document.add(paragraph);
            paragraph = new Paragraph(new Phrase("NIP : "+firma.getNip(), fontM));
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            paragraph = new Paragraph(new Phrase("Dane pracownika:", fontM));
            document.add(paragraph);
           paragraph = new Paragraph(new Phrase(umowa.getPracownik().getNazwiskoImie(), fontM));
            document.add(paragraph);
            if (umowa.getPracownik().getPlec().equals("K")) {
                paragraph = new Paragraph(new Phrase("zamieszkałą w "+umowa.getPracownik().getAdres(), fontM));
            } else {
                paragraph = new Paragraph(new Phrase("zamieszkałym w "+umowa.getPracownik().getAdres(), fontM));
            }
            document.add(paragraph);
            document.add(Chunk.NEWLINE);
            PdfMain.dodajLinieOpisu(document, "INFORMACJA O WARUNKACH ZATRUDNIENIA I UPRAWNIENIACH PRACOWNICZYCH", Element.ALIGN_CENTER, 1);
            dodajtabele(document);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            //document.add(new Paragraph(new Phrase(umowa.getPracownik().getNazwiskoImie()+"                                                          "+umowa.getAngaz().getFirma().getReprezentant(), fontM)));
            Paragraph p = new Paragraph();
            p.add(new Phrase(umowa.getPracownik().getNazwiskoImie(), fontM));
            p.setTabSettings(new TabSettings(350));
            p.add(Chunk.TABBING);
            p.add(new Phrase(umowa.getAngaz().getFirma().getReprezentant(), fontM));
            document.add(p);
            p = new Paragraph();
            p.add(new Phrase("(data i podpis pracownika)", fontS));
            p.setTabSettings(new TabSettings(300));
            p.add(Chunk.TABBING);
            p.add(new Phrase("(podpis pracodawcy", fontS));
            p.setTabSettings(new TabSettings(350));
            p.add(Chunk.TABBING);
            p.add(new Phrase("lub osoby reprezentującej pracodawcę)", fontS));
            document.add(p);
        } catch (Exception ex) {
            E.e(ex);
        }
    }

    private static void dodajtabele(Document document) {
        try {
            PdfPTable table =  new PdfPTable(3);
            table.setWidthPercentage(100);
            table.setWidths(new int[]{1, 3, 7});
            int i = 1;
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
            String tytul = "Obowiązująca dobowa i tygodniowa norma czasu pracy:";
            table.addCell(ustawfrazeAlign(tytul, "left",8));
            String opis = "8 godz. na dobę i przeciętnie 40 godz. w przeciętnie pięciodniowym tygodniu pracy";
            table.addCell(ustawfrazeAlign(opis, "left",8));
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
            tytul = "Obowiązujący Pracownika dobowy i tygodniowy wymiar czasu pracy:";
            table.addCell(ustawfrazeAlign(tytul, "left",8));
            opis = "8 godz. na dobę i przeciętnie 40 godz. w przeciętnie pięciodniowym tygodniu pracy";
            table.addCell(ustawfrazeAlign(opis, "left",8));
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
            tytul = "Przysługujące Pracownikowi przerwy w pracy:";
            table.addCell(ustawfrazeAlign(tytul, "left",8));
            opis = "w dniach pracy od 6 do 9 godz. - jedna przerwa trwająca 15 min.; w dniach pracy powyżej 9 godz. - dwie przerwy po 15 min.";
            table.addCell(ustawfrazeAlign(opis, "left",8));
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
            tytul = "Przysługujący Pracownikowi dobowy i tygodniowy odpoczynek:";
            table.addCell(ustawfrazeAlign(tytul, "left",8));
            opis = "nieprzerwane 11 godz. w każdej dobie i nieprzerwane 35 godz. w każdym tygodniu, a w przypadku akcji ratowniczej w celu ochrony życia lub zdrowia ludzkiego, ochrony mienia lub środowiska albo usunięcia awarii oraz przejścia na inną zmianę - nieprzerwane 24 godz. w tygodniu";
            table.addCell(ustawfrazeAlign(opis, "left",8));
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
            tytul = "Zasady dotyczące pracy w godzinach nadliczbowych i rekompensaty za nią:";
            table.addCell(ustawfrazeAlign(tytul, "left",8));
            opis = "Pracodawca może zlecić pracę w godzinach nadliczbowych, tj. ponad obowiązujące normy czasu pracy w razie konieczności prowadzenia akcji ratowniczej w celu ochrony życia lub zdrowia ludzkiego, ochrony mienia lub środowiska albo usunięcia awarii lub szczególnych potrzeb pracodawcy;\n" +
"za pracę w godzinach nadliczbowych, oprócz normalnego wynagrodzenia, przysługuje dodatek w wysokości:\n" +
"a)	100% wynagrodzenia - za pracę: w nadgodzinach wynikających z przekroczenia przeciętnej tygodniowej normy czasu pracy, a także w nadgodzinach dobowych przypadających w nocy, w niedziele i święta niebędące dla pracownika dniami pracy, zgodnie z obowiązującym go rozkładem czasu pracy albo w dniu wolnym od pracy udzielonym w zamian za pracę w niedzielę lub w święto, zgodnie z obowiązującym go rozkładem czasu pracy,\n" +
"b)	50% wynagrodzenia - za pracę w nadgodzinach dobowych przypadających w innym dniu/porze niż określony w punkcie a).\n" +
"Zamiast ww. dodatku pracodawca w zamian za nadgodziny może udzielić czasu wolnego:\n" +
"- w tym samym wymiarze - na pisemny wniosek Pracownika,\n" +
"- w wymiarze o połowę wyższym niż liczba nadgodzin – gdy udzielenie czasu wolnego następuje bez wniosku Pracownika (w takim przypadku udzielenie czasu wolnego następuje najpóźniej do końca okresu rozliczeniowego, jednakże nie może to spowodować obniżenia wynagrodzenia należnego Pracownikowi za pełny miesięczny wymiar czasu pracy)";
            table.addCell(ustawfrazeAlign(opis, "left",8));
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
            tytul = "Zasady dotyczące przechodzenia ze zmiany na zmianę:";
            table.addCell(ustawfrazeAlign(tytul, "left",8));
            opis = "W zakładzie pracy obowiązują następujące zmiany:\n" +
"•	I zmiana: praca w godz.: ……………………………………………..\n" +
"•	II zmiana: praca w godz.: …………………………………………….\n" +
"•	III zmiana: praca w godz.: ……………………………………………\n" +
"Zmiana pory wykonywania pracy wynika z haromonogramu ustalanego na okresy miesięczne i dostarczanego nie później niż tydzień przed rozpoczęciem danego miesiąca";
            table.addCell(ustawfrazeAlign(opis, "left",8));
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
            tytul = "Zasady dotyczące przemieszczania się między miejscami wykonywania pracy: ";
            table.addCell(ustawfrazeAlign(tytul, "left",8));
            opis = "";
            table.addCell(ustawfrazeAlign(opis, "left",8));
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
            tytul = "Inne niż uzgodnione w umowie o pracę składniki wynagrodzenia oraz świadczenia pieniężne lub rzeczowe: ";
            table.addCell(ustawfrazeAlign(tytul, "left",8));
            opis = "Pracownikowi przysługuje niewymienione w umowie o pracę :\n" +
"a)	Składniki wynagrodzenia …………………………………………….\n" +
"b)	Świadczenia pieniężne ………………………………………………\n" +
"c)	Świadczenia rzeczowe ……………………………………………….";
            table.addCell(ustawfrazeAlign(opis, "left",8));
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
            tytul = "Wymiar przysługującego Pracownikowi płatnego urlopu, w szczególności urlopu wypoczynkowego:";
            table.addCell(ustawfrazeAlign(tytul, "left",8));
            opis = "Pracownikowi przysługuje urlop wypoczynkowy w wymiarze …… dni za cały rok pracy (w przypadku pozostawania w zatrudnieniu przez część roku urlop wypoczynkowy należny jest w wymiarze ustalonym w sposób proporcjonalny)";
            table.addCell(ustawfrazeAlign(opis, "left",8));
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
            tytul = "Obowiązująca procedura rozwiązania stosunku pracy, w tym wymogi formalne, długość okresów wypowiedzenia oraz termin odwołania się do sądu pracy: ";
            table.addCell(ustawfrazeAlign(tytul, "left",8));
            opis = "Umowa o pracę może ulec rozwiązaniu:\n" +
"- na mocy porozumienia stron,\n" +
"- za wypowiedzeniem czyli z zachowaniem okresu wypowiedzenia poprzez złożenie przez Pracodawcę lub Pracownika oświadczenia na piśmie,\n" +
"- bez zachowania okresu wypowiedzenia poprzez złożenie przez Pracodawcę lub Pracownika oświadczenia na piśmie.\n" +
"Pracownika obowiązuje 2-tygodniowy okres wypowiedzenia, który po osiągnięciu 6-miesięcznego zakładowego stażu pracy podlega wydłużeniu do 1 miesiąca, a po osiągnięciu 3-letniego zakładowego stażu pracy - do 3 miesięcy.\n" +
"Odwołanie od wypowiedzenia umowy o pracę wnosi się do sądu pracy w ciągu 21 dni od dnia doręczenia pisma wypowiadającego.\n" +
"Żądanie przywrócenia do pracy lub odszkodowania wnosi się do sądu pracy w ciągu 21 dni od dnia doręczenia zawiadomienia o rozwiązaniu umowy o pracę bez wypowiedzenia lub od dnia wygaśnięcia umowy o pracę.\n" +
"Żądanie nawiązania umowy o pracę wnosi się do sądu pracy w ciągu 21 dni od dnia doręczenia zawiadomienia o odmowie przyjęcia do pracy.";
            table.addCell(ustawfrazeAlign(opis, "left",8));
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
            tytul = "Prawo do szkoleń, w szczególności ogólne zasady polityki szkoleniowej Pracodawcy:";
            table.addCell(ustawfrazeAlign(tytul, "left",8));
            opis = "Pracodawca wprowadził/nie wprowadził aktu regulującego wewnętrzna politykę szkoleniową.\n" +
"…………………………………………………………………………………….";
            table.addCell(ustawfrazeAlign(opis, "left",8));
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
            tytul = "Układ zbiorowy pracy lub inne porozumienie zbiorowe, którym pracownik jest objęty:";
            table.addCell(ustawfrazeAlign(tytul, "left",8));
            opis = "Pracownik nie jest objęty układem zbiorowym pracy ani innym porozumieniem zbiorowym";
            table.addCell(ustawfrazeAlign(opis, "left",8));
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
            tytul = "Nazwa instytucji zabezpieczenia społecznego, do których wpływają składki na ubezpieczenia\n" +
"społeczne związane ze stosunkiem pracy oraz informacje na temat ochrony związanej z zabezpieczeniem społecznym, zapewnianej przez Pracodawcę:";
            table.addCell(ustawfrazeAlign(tytul, "left",8));
            opis = "Zakład Ubezpieczeń Społecznych";
            table.addCell(ustawfrazeAlign(opis, "left",8));
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
            tytul = "Pora nocna:";
            table.addCell(ustawfrazeAlign(tytul, "left",8));
            opis = "od 22:00 do 06:00";
            table.addCell(ustawfrazeAlign(opis, "left",8));
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
            tytul = "Termin, miejsce, czas i częstotliwość wypłacania wynagrodzenia za pracę:";
            table.addCell(ustawfrazeAlign(tytul, "left",8));
            opis = "Wypłata wynagrodzenia następuje raz w miesiącu, w ostatni roboczy dzień miesiąca/10-go dnia następnego miesiąca, na rachunek bankowy Pracownika wskazany w pisemnym upoważnieniu";
            table.addCell(ustawfrazeAlign(opis, "left",8));
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
            tytul = "Przyjęty sposób potwierdzania przybycia i obecności w pracy:";
            table.addCell(ustawfrazeAlign(tytul, "left",8));
            opis = "złożenie podpisu na liście obecności";
            table.addCell(ustawfrazeAlign(opis, "left",8));
            table.addCell(ustawfrazeAlign(String.valueOf(i++), "center",8));
            tytul = "Przyjęty sposób usprawiedliwiania nieobecności w pracy:";
            table.addCell(ustawfrazeAlign(tytul, "left",8));
            opis = "Pracownik informuje bezpośredniego przełożonego lub jego zastępcę o przyczynie nieobecności najpóźniej w drugim dniu jej trwania telefonicznie, e-mailem lub SMS-em, a przyczynę nieobecności jest obowiązany udokumentować najpóźniej w dniu powrotu do pracy";
            table.addCell(ustawfrazeAlign(opis, "left",8));
            document.add(table);
        } catch (DocumentException ex) {
            Logger.getLogger(PdfDRA.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
         
      
}

