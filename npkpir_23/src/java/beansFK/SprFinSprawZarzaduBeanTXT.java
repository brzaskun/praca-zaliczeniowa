/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import static beansPdf.PdfFont.ustawfrazeAlign;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import entityfk.SprFinKwotyInfDod;
import view.WpisView;
import java.io.File;
import pdffk.PdfMain;
import static pdffk.PdfMain.*;
import com.itextpdf.text.pdf.PdfPTable;
import embeddablefk.SaldoKonto;
import entity.Podatnik;
import entityfk.Konto;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import plik.Plik;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class SprFinSprawZarzaduBeanTXT {

    static void naglowekglowny(Document document, SprFinKwotyInfDod sprFinKwotyInfDod, String nazwa, String siedziba, String nrkrs) {
        PdfMain.dodajLinieOpisu(document, "SPRAWOZDANIE ZARZĄDU", Element.ALIGN_CENTER,2);
        PdfMain.dodajLinieOpisuBezOdstepu(document, nazwa, Element.ALIGN_CENTER,2);
        PdfMain.dodajLinieOpisu(document, "nr KRS "+nrkrs, Element.ALIGN_CENTER);
        PdfMain.dodajLinieOpisu(document, "z siedzibą w "+siedziba, Element.ALIGN_CENTER);
        PdfMain.dodajLinieOpisu(document, "za okres od "+sprFinKwotyInfDod.getDataod()+" do "+sprFinKwotyInfDod.getDatado(), Element.ALIGN_CENTER);
    }
    
    static void naglowekglownyL(Document document, SprFinKwotyInfDod sprFinKwotyInfDod, String nazwa, String siedziba, String nrkrs) {
        PdfMain.dodajLinieOpisu(document, "SPRAWOZDANIE LIKWIDATORA/ÓW", Element.ALIGN_CENTER,2);
        nazwa = nazwa +" "+" W LIKWIDACJI";
        PdfMain.dodajLinieOpisuBezOdstepu(document, nazwa, Element.ALIGN_CENTER,2);
        PdfMain.dodajLinieOpisu(document, "nr KRS "+nrkrs, Element.ALIGN_CENTER);
        PdfMain.dodajLinieOpisu(document, "z siedzibą w "+siedziba, Element.ALIGN_CENTER);
        PdfMain.dodajLinieOpisu(document, "za okres od "+sprFinKwotyInfDod.getDataod()+" do "+sprFinKwotyInfDod.getDatado(), Element.ALIGN_CENTER);
    }

    static void podnaglowek1(Document document, String opispkd, String rok) {
        PdfMain.dodajLinieOpisu(document, "I. WPROWADZENIE", Element.ALIGN_LEFT,2);
        PdfMain.dodajLinieOpisu(document, "Spółka prowadzi działalność  gospodarczą w zakresie "+opispkd+". Rok "+rok+" był w kolejnym rokiem działalności spółki w tym zakresie.", Element.ALIGN_JUSTIFIED);
    }
    
    static void podnaglowek1L(Document document, String opispkd, String rok) {
        PdfMain.dodajLinieOpisu(document, "I. WPROWADZENIE", Element.ALIGN_LEFT,2);
        PdfMain.dodajLinieOpisu(document, "Spółka jest w stanie likwidacji. Uprzednio prowadziła działalność  gospodarczą w zakresie "+opispkd+". Rok "+rok+" był w rokiem działalności likwidatorów w spółce.", Element.ALIGN_JUSTIFIED);
    }
    
    static void podnaglowek2(Document document, String rok, double zyskstrata, double sumabilansowa, String datado) {
        PdfMain.dodajLinieOpisu(document, "II. SPRAWOZDANIE FINANSOWE  ZA "+rok, Element.ALIGN_LEFT,2);
        PdfMain.dodajLinieOpisu(document, "Na sprawozdanie finansowe za "+rok+"r. składa się", Element.ALIGN_JUSTIFIED);
        PdfMain.dodajLinieOpisu(document, "- rachunek zysków i strat wykazujący zysk w kwocie "+format.F.curr(zyskstrata), Element.ALIGN_JUSTIFIED);
        PdfMain.dodajLinieOpisu(document, "- bilans sporządzony na dzień "+datado+"r. z sumą bilansową "+format.F.curr(sumabilansowa), Element.ALIGN_JUSTIFIED);
    }

    static void podnaglowek3(Document document) {
        PdfMain.dodajLinieOpisu(document, "III. REALIZOWANE PRZEDSIĘWZIĘCIA:", Element.ALIGN_LEFT,2);
        PdfMain.dodajLinieOpisu(document, "Planowanymi głównym odbiorcami usług firmy są firmy polskie i krajowe osoby fizyczne.", Element.ALIGN_JUSTIFIED);
    }
    
    static void podnaglowek3L(Document document) {
        PdfMain.dodajLinieOpisu(document, "III. REALIZOWANE PRZEDSIĘWZIĘCIA:", Element.ALIGN_LEFT,2);
        PdfMain.dodajLinieOpisu(document, "Likwidatorzy przeprowadzają likwidację spółki zgodnie z obowiązującymi przepisami.", Element.ALIGN_JUSTIFIED);
    }
    
    static void podnaglowek4(Document document) {
        PdfMain.dodajLinieOpisu(document, "IV. OCENA SYTUACJI I DZIAŁAŃ SPÓŁKI", Element.ALIGN_LEFT,2);
        PdfMain.dodajLinieOpisu(document, "Nie ma zagrożeń dla działalności spółki. Spółka nie jest finansowana z zewnątrz. Możliwe jest dzięki temu planowanie długoterminowe aktywności spółki.  Spółka nie poczyniła żadnych istotnych zakupów ani nie podpisała umów które by obciążały ją finansowo w sposób znaczny. Zarząd dbał o finanse spółki. Główne zobowiązania to zobowiązania względem dostawców towarów i usług na rzecz spółki", Element.ALIGN_JUSTIFIED);
    }
    
    static void podnaglowek4L(Document document) {
        PdfMain.dodajLinieOpisu(document, "IV. OCENA SYTUACJI I DZIAŁAŃ SPÓŁKI", Element.ALIGN_LEFT,2);
        PdfMain.dodajLinieOpisu(document, "Nie ma zagrożeń dla procesu likwidacji. Spółka nie jest finansowana z zewnątrz. Możliwe jest dzięki temu planowanie długoterminowe aktywności spółki.  Spółka nie poczyniła żadnych istotnych zakupów ani nie podpisała umów które by obciążały ją finansowo w sposób znaczny. Zarząd dbał o finanse spółki. Główne zobowiązania to zobowiązania względem dostawców towarów i usług na rzecz spółki", Element.ALIGN_JUSTIFIED);
    }
    
    static void podnaglowek5(Document document) {
        PdfMain.dodajLinieOpisu(document, "V. PROGRAM DZIAŁANIA SPÓŁKI NA ROK NATĘPNY  I LATA KOLEJNE", Element.ALIGN_LEFT,2);
        PdfMain.dodajLinieOpisu(document, "W związku z panującym zastojem w branży, w której spółka się specjalizuje Zarząd planuje podjąć działania ograniczające koszty", Element.ALIGN_JUSTIFIED);
    }
    
    static void podnaglowek5L(Document document) {
        PdfMain.dodajLinieOpisu(document, "V. PROGRAM DZIAŁANIA SPÓŁKI NA ROK NATĘPNY  I LATA KOLEJNE", Element.ALIGN_LEFT,2);
        PdfMain.dodajLinieOpisu(document, "Likwidatorzy podejmą wszelkie możliwe działania w celu jak najszybszego zakończenia procesu likwidacji", Element.ALIGN_JUSTIFIED);
    }
    
    static void podnaglowek6(Document document) {
        PdfMain.dodajLinieOpisu(document, "VI. PODSUMOWANIE", Element.ALIGN_LEFT,2);
        PdfMain.dodajLinieOpisu(document, "Kluczowym zadaniem Zarządu tej kadencji jest właściwe przygotowanie do nowych warunków rynkowych, a także poszukiwanie atrakcyjnych odbiorców na usługi oferowane przez spółkę. ", Element.ALIGN_JUSTIFIED);
    }
    
    static void podnaglowek6L(Document document) {
        PdfMain.dodajLinieOpisu(document, "VI. PODSUMOWANIE", Element.ALIGN_LEFT,2);
        PdfMain.dodajLinieOpisu(document, "Kluczowym zadaniem likwidatorów jest wyprzedaż majątku firmy i rozlcizenie się z udziałowcami oraz wierzycielami.", Element.ALIGN_JUSTIFIED);
    }
    
    static void podnaglowek7(Document document) {
        PdfMain.dodajLinieOpisu(document, ".............................", Element.ALIGN_LEFT);
        PdfMain.dodajLinieOpisu(document, "Za zarząd", Element.ALIGN_LEFT);
    }
    
    static void podnaglowek7L(Document document) {
        PdfMain.dodajLinieOpisu(document, ".............................", Element.ALIGN_LEFT);
        PdfMain.dodajLinieOpisu(document, "Likwidatorzy", Element.ALIGN_LEFT);
    }
}
