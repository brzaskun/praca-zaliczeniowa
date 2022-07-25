/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import embeddable.Mce;
import embeddable.STRtabela;
import embeddablefk.SaldoKonto;
import entity.Rodzajedok;
import entityfk.Konto;
import entityfk.PozycjaRZiSBilans;
import error.E;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import msg.B;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import pl.com.cdn.optima.offline.ROOT;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class WriteXLSFile {
    private static final String FILE_PATH1 = "wydruki/axlsfile.xls";
    private static final String FILE_PATH2 = "d:/axlsfile.xls";
    //We are making use of a single instance to prevent multiple write access to same file.
    private static final WriteXLSFile INSTANCE = new WriteXLSFile();

    public static WriteXLSFile getInstance() {
        return INSTANCE;
    }

    private WriteXLSFile() {
    }
//
//    public static void main(String args[]){
//        Map<String, List> l = new ConcurrentHashMap<>();
//        l.put("p", listaprzychody());
//        l.put("k", listakoszty());
//        l.put("w", listawynik());
//        l.put("o", listapodatek());
//        //zachowajXLS(l, WpisView);
//    }
    
    public static Workbook zachowajXLS(Map<String, List> listy, WpisView wpisView){
        List wynikpopmc = listy.get("b");
        List przychody = listy.get("p");
        List koszty = listy.get("k");
        List wynik = listy.get("w");
        List podatek = listy.get("o");
        List dywidenda = listy.get("d");
        List headersListPrzychodKoszt = headerprzychodykoszty();
        List headersListWyliczenia = headerswynik();
        List headerwynikpopmc = headerwynikpopmc();
        // Using XSSF for xlsx format, for xls use HSSF
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Symulacja wyniku");
        insertPrintHeader(sheet, wpisView);
        int rowIndex = 0;
        rowIndex = drawATable(workbook, sheet, rowIndex, headerwynikpopmc, wynikpopmc, "Wynik za m-ce poprzednie", 0, "wynikmcepop");
        sheet.createRow(rowIndex++);
        rowIndex = drawATable(workbook, sheet, rowIndex, headersListPrzychodKoszt, przychody, "Przychody za mc "+wpisView.getMiesiacWpisu(), 1, "przychody");
        sheet.createRow(rowIndex++);
        rowIndex = drawATable(workbook, sheet, rowIndex, headersListPrzychodKoszt, koszty, "Koszty za mc "+wpisView.getMiesiacWpisu(), 1, "koszty");
        sheet.createRow(rowIndex++);
        rowIndex = drawATable(workbook, sheet, rowIndex, headersListWyliczenia, wynik, "Obliczenie wyniku fin. i pod.", 2, "");
        sheet.createRow(rowIndex++);
        rowIndex = drawATable(workbook, sheet, rowIndex, headersListWyliczenia, podatek, "Obliczenie podatku dochodowego", 2, "");
        sheet.createRow(rowIndex++);
        rowIndex = drawATable(workbook, sheet, rowIndex, headersListWyliczenia, dywidenda, "Obliczenie kwot do wypłaty", 2, "");
//        workbook.setPrintArea(
//        0, //sheet index
//        0, //start column
//        3, //end column
//        0, //start row
//        rowIndex //end row
        //);
      //set paper size
        sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
        sheet.setFitToPage(true);
        //write this workbook in excel file.
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/");
        String FILE_PATH = realPath+FILE_PATH1;
        try {
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }
    
    public static Workbook zachowajPlanKontXLS(Map<String, List> listy, WpisView wpisView){
        List plankont = listy.get("plankont");
        List headersList = headerplankont();
        // Using XSSF for xlsx format, for xls use HSSF
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Plan Kont");
        insertPrintHeader(sheet, wpisView);
        int rowIndex = 0;
        rowIndex = drawATable(workbook, sheet, rowIndex, headersList, plankont, "Plan Kont", 1, "plankont");
        workbook.setPrintArea(
        0, //sheet index
        0, //start column
        4, //end column
        0, //start row
        rowIndex //end row
        );
      //set paper size
        sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
        sheet.setFitToPage(true);
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/");
        String FILE_PATH = realPath+FILE_PATH1;
        //write this workbook in excel file.
        try {
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }
    
    
    public static Workbook zachowajRZiSInterXLS(List listapozycji, WpisView wpisView){
        List headersList = headerRZiSBilansInter();
        // Using XSSF for xlsx format, for xls use HSSF
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("RZiSInter");
        insertPrintHeader(sheet, wpisView);
        int rowIndex = 0;
        rowIndex = drawATable(workbook, sheet, rowIndex, headersList, listapozycji, "RZiSInter", 1, "RZiSInter");
        workbook.setPrintArea(
        0, //sheet index
        0, //start column
        4, //end column
        0, //start row
        rowIndex //end row
        );
      //set paper size
        sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_ROTATED_PAPERSIZE);
        sheet.setFitToPage(true);
        sheet.getPrintSetup().setLandscape(true);
        //write this workbook in excel file.
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/");
        String FILE_PATH = realPath+FILE_PATH1;
        try {
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }
    
    public static Workbook zachowajBilansInterXLS(List listapozycji, WpisView wpisView){
        List headersList = headerRZiSBilansInter();
        // Using XSSF for xlsx format, for xls use HSSF
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("BilansInter");
        insertPrintHeader(sheet, wpisView);
        int rowIndex = 0;
        rowIndex = drawATable(workbook, sheet, rowIndex, headersList, listapozycji, "BilansInter", 1, "BilansInter");
        workbook.setPrintArea(
        0, //sheet index
        0, //start column
        4, //end column
        0, //start row
        rowIndex //end row
        );
      //set paper size
        sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_ROTATED_PAPERSIZE);
        sheet.setFitToPage(true);
        sheet.getPrintSetup().setLandscape(true);
        //write this workbook in excel file.
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/");
        String FILE_PATH = realPath+FILE_PATH1;
        try {
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }
    
    
    public static Workbook zachowajRodzajedokXLS(Map<String, List> listy, WpisView wpisView){
        List rodzajedok = listy.get("rodzajedok");
        List headersList = headerrodzajedok();
        // Using XSSF for xlsx format, for xls use HSSF
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Rodzajedok");
        insertPrintHeader(sheet, wpisView);
        int rowIndex = 0;
        rowIndex = drawATable(workbook, sheet, rowIndex, headersList, rodzajedok, "Rodzajedok", 1, "rodzajedok");
        workbook.setPrintArea(
        0, //sheet index
        0, //start column
        3, //end column
        0, //start row
        rowIndex //end row
        );
      //set paper size
        sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
        sheet.setFitToPage(true);
        //write this workbook in excel file.
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/");
        String FILE_PATH = realPath+FILE_PATH1;
        try {
            FileOutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }
    
    public static Workbook zachowajSuSaXLS(Map<String, List> listy, WpisView wpisView){
        List lista = listy.get("kontasalda");
        List headersList = headersusa();
        // Using XSSF for xlsx format, for xls use HSSF
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Obr.Salda");
        insertPrintHeader(sheet, wpisView);
        int rowIndex = 0;
        String opis = B.b("firma")+" "+wpisView.getPrintNazwa()+" "+B.b("zestawienieobrotówkontanalitycznych")+wpisView.getRokWpisuSt()+"/"+wpisView.getMiesiacWpisu();
        rowIndex = drawATable(workbook, sheet, rowIndex, headersList, lista, opis, 3, "kontasalda");
        workbook.setPrintArea(
        0, //sheet index
        0, //start column
        4, //end column
        0, //start row
        rowIndex //end row
        );
      //set paper size
        sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
        sheet.setFitToPage(true);
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/");
        String FILE_PATH = realPath+FILE_PATH1;
        //write this workbook in excel file.
        try {
            OutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }
    
    public static Workbook zachowajRaportZorin(ROOT.REJESTRYSPRZEDAZYVAT lista, WpisView wpisView){
        Workbook workbook = new XSSFWorkbook();
        try {
            List headersList = headerzorinraport();
            // Using XSSF for xlsx format, for xls use HSSF
            Sheet sheet = workbook.createSheet("Raport");
    //        Header header = sheet.getHeader();
    //        header.setCenter(wpisView.getPodatnikWpisu());
    //        header.setLeft("ZORIN RAPORT "+wpisView.getMiesiacWpisu());
            int rowIndex = 0;
            String opis = B.b("firma")+" "+wpisView.getPrintNazwa()+" "+"RAPORTOPTIMA"+wpisView.getRokWpisuSt()+"/"+wpisView.getMiesiacWpisu();
            rowIndex = drawATableZorin(workbook, sheet, rowIndex, headersList, lista.getREJESTRSPRZEDAZYVAT(), opis, 3, "suma");
            workbook.setPrintArea(
            0, //sheet index
            0, //start column
            4, //end column
            0, //start row
            rowIndex //end row
            );
          //set paper size
            sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
            sheet.setFitToPage(true);
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/");
            String FILE_PATH = realPath+FILE_PATH1;
            //write this workbook in excel file.
            OutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ez) {
            System.out.println(E.e(ez));
        }
        return workbook;
    }
    
    public static Workbook zachowajSrodkiXLS(Map<String, List> listy, WpisView wpisView){
        List lista = listy.get("srodkitrwale");
        List headersList = headersrodki();
        // Using XSSF for xlsx format, for xls use HSSF
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Śr.trwale");
        insertPrintHeader(sheet, wpisView);
        int rowIndex = 0;
        String opis = "Ewidencja środków trwałych "+wpisView.getPrintNazwa()+" na koniec "+wpisView.getRokWpisuSt()+"/"+wpisView.getMiesiacWpisu();
        rowIndex = drawATable(workbook, sheet, rowIndex, headersList, lista, opis, 4, "srodkitrwale");
        workbook.setPrintArea(
        0, //sheet index
        0, //start column
        4, //end column
        0, //start row
        rowIndex //end row
        );
      //set paper size
        sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
        sheet.setFitToPage(true);
        ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        String realPath = ctx.getRealPath("/");
        String FILE_PATH = realPath+FILE_PATH1;
        //write this workbook in excel file.
        try {
            OutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return workbook;
    }
    
    public static List listaprzychody() {
        List przychody = new ArrayList();
        przychody.add(new PozycjaPrzychodKoszt(1,"702-2-1", "Przychody", "Sprzedaż krajowa", 141196.48));
        przychody.add(new PozycjaPrzychodKoszt(2,"702-2-2", "Przychody", "Sprzedaż zagraniczna", 37610.47));
        przychody.add(new PozycjaPrzychodKoszt(3,"", "przychód symulowany", "", 0.00));
        przychody.add(new PozycjaPrzychodKoszt(4,"", "przychód symulowany", "", 0.00));
        przychody.add(new PozycjaPrzychodKoszt(5,"", "przychód symulowany", "", 0.00));
        return przychody;
    }
    public static List listakoszty() {
        List koszty = new ArrayList();
        koszty.add(new PozycjaPrzychodKoszt(1,"401-1-1", "Amortyzacja", "stanowiaca kup", 93488.25));
        koszty.add(new PozycjaPrzychodKoszt(2,"402-1", "Materiały", "Materiały biurowe", 1100.11));
        koszty.add(new PozycjaPrzychodKoszt(3,"", "koszt symulowany", "", 0.00));
        koszty.add(new PozycjaPrzychodKoszt(4,"", "koszt symulowany", "", 0.00));
        koszty.add(new PozycjaPrzychodKoszt(5,"", "koszt symulowany", "", 0.00));
        return koszty;
    }
    public static List listawynik() {
        List wynik = new ArrayList();
        wynik.add(new PozycjaObliczenia(1,"przychody razem", "+przychody"));
        wynik.add(new PozycjaObliczenia(2,"koszty razem", "+koszty"));
        wynik.add(new PozycjaObliczenia(3,"wynik finansowy", "przychody-koszty"));
        wynik.add(new PozycjaObliczenia(4,"npup", 0.00));
        wynik.add(new PozycjaObliczenia(5,"nkup", -1309.18));
        wynik.add(new PozycjaObliczenia(6,"wynik podatkowy", "wynikfinansowy-npup-nkup"));
        return wynik;
    }
    public static List listapodatek() {
        List podatek = new ArrayList();
        podatek.add(new PozycjaObliczenia(1,"udziałowiec 1", 0.99));
        podatek.add(new PozycjaObliczenia(2,"podstawa opodatkowania 1", "round(wynikpodatkowy*udziałowiec1,0)"));
        podatek.add(new PozycjaObliczenia(3,"podatek udziałowiec 1", "round(podstawaopodatkowania1*0.19,0)"));
        podatek.add(new PozycjaObliczenia(4,"udziałowiec 2", 0.01));
        podatek.add(new PozycjaObliczenia(5,"podstawa opodatkowania 2", "round(wynikpodatkowy*udziałowiec2,0)"));
        podatek.add(new PozycjaObliczenia(6,"podatek udziałowiec 2", "round(podstawaopodatkowania2*0.19,0)"));
        return podatek;
    }
    public static List headerprzychodykoszty() {
        List headersListPrzychodKoszt = new ArrayList();
        headersListPrzychodKoszt.add("lp");
        headersListPrzychodKoszt.add("nr konta");
        headersListPrzychodKoszt.add("nazwa konta");
        headersListPrzychodKoszt.add("kwota");
        return headersListPrzychodKoszt;
    }
    public static List headerwynikpopmc() {
        List headersListPrzychodKoszt = new ArrayList();
        headersListPrzychodKoszt.add("");
        headersListPrzychodKoszt.add("");
        headersListPrzychodKoszt.add("pozycja");
        headersListPrzychodKoszt.add("kwota");
        return headersListPrzychodKoszt;
    }
    public static List headerswynik() {
        List headersListWyliczenia = new ArrayList();
        headersListWyliczenia.add("lp");
        headersListWyliczenia.add("opis");
        headersListWyliczenia.add("kwota");
        return headersListWyliczenia;
    }
    
    public static List headerplankont() {
        List headers = new ArrayList();
        headers.add("lp");
        headers.add("nr konta");
        headers.add("nazwa konta");
        headers.add("przyporządkowanie");
        headers.add("tłumaczenie");
        return headers;
    }
    
    public static List headersusa() {
        List headers = new ArrayList();
        headers.add(B.b("lp"));
        headers.add(B.b("numerkonta"));
        headers.add(B.b("nazwakonta"));
        headers.add(B.b("saldoBOWn"));
        headers.add(B.b("saldoBOMa"));
        headers.add(B.b("obrotyWn"));
        headers.add(B.b("obrotyMa"));
        headers.add(B.b("obrotyWnNar"));
        headers.add(B.b("obrotyMaNar"));
        headers.add(B.b("sumaBOWn"));
        headers.add(B.b("sumaBOMa"));
        headers.add(B.b("saldoWn"));
        headers.add(B.b("saldoMa"));
        headers.add(B.b("kontosyntetyczne"));
        headers.add(B.b("nazwakonta"));
        return headers;
    }
    
    public static List headerzorinraport() {
        List headers = new ArrayList();
        headers.add("lp");
        headers.add("ID_ZRODLA");
        headers.add("REJESTR");
        headers.add("DATA_WYSTAWIENIA");
        headers.add("DATA_SPRZEDAZY");
        headers.add("NUMER");
        headers.add("KOREKTA");
        headers.add("FISKALNA");
        headers.add("PODMIOT");
        headers.add("PODMIOT_ID");
        headers.add("NAZWA1");
        headers.add("KRAJ");
        headers.add("NIP");
        headers.add("NIPKRAJ");
        headers.add("OPIS");
        headers.add("NETTO");
        headers.add("VAT");
        headers.add("BRUTTO");
        headers.add("WALUTA");
        headers.add("NETTOpln");
        headers.add("VATpln");
        headers.add("BRUTTOpln");
        return headers;
    }
    
    
    public static List headersrodki() {
        List headers = new ArrayList();
        headers.add("lp");
        headers.add("nazwa środka");
        headers.add("data przyj.");
        headers.add("data sprzed.");
        headers.add("KST");
        headers.add("cena zakupu");
        headers.add("odpis roczny");
        headers.add("umorz. dot.");
        headers.add(Mce.getNumberToNazwamiesiaca().get(1));
        headers.add(Mce.getNumberToNazwamiesiaca().get(2));
        headers.add(Mce.getNumberToNazwamiesiaca().get(3));
        headers.add(Mce.getNumberToNazwamiesiaca().get(4));
        headers.add(Mce.getNumberToNazwamiesiaca().get(5));
        headers.add(Mce.getNumberToNazwamiesiaca().get(6));
        headers.add(Mce.getNumberToNazwamiesiaca().get(7));
        headers.add(Mce.getNumberToNazwamiesiaca().get(8));
        headers.add(Mce.getNumberToNazwamiesiaca().get(9));
        headers.add(Mce.getNumberToNazwamiesiaca().get(10));
        headers.add(Mce.getNumberToNazwamiesiaca().get(11));
        headers.add(Mce.getNumberToNazwamiesiaca().get(12));
        return headers;
    }
    
    public static List headerRZiSBilansInter() {
        List headers = new ArrayList();
        headers.add("lp");
        headers.add("id");
        headers.add("pozycja");
        headers.add("nazwa pozycji");
        headers.add("tłumaczenie");
        return headers;
    }
    
    public static List headerrodzajedok() {
        List headers = new ArrayList();
        headers.add("lp");
        headers.add("skrót");
        headers.add("nazwa");
        headers.add("tłumaczenie");
        return headers;
    }
    
    private static <T> int drawATableZorin(Workbook workbook, Sheet sheet, int rowIndex, List headers, List<ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT> elements, String tableheader, int typ, String nazwasumy) {
        int startindex = rowIndex+3;
        int columnIndex = 0;
        //Row rowTH = sheet.createRow(rowIndex++);
        CellStyle styleheader = styleHeader(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, (short) 10);
//        createHeaderCell(styleheader, rowTH, (short) 2, tableheader);
//        CellRangeAddress region = new CellRangeAddress( rowIndex-1, rowIndex-1, (short) 2, (short)12);
//        sheet.addMergedRegion(region);
        sheet.setAutoFilter(CellRangeAddress.valueOf("A1:U1"));
        CellStyle styletext = styleText(workbook, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
        CellStyle styletextcenter = styleText(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        CellStyle styledata = styleData(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        CellStyle styleformula = styleFormula(workbook, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER);
        CellStyle styledouble = styleDouble(workbook, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER);
        Row rowH = sheet.createRow(rowIndex++);
        for(Iterator it = headers.iterator(); it.hasNext();){
            String header = (String) it.next();
            createHeaderCell(styleheader, rowH, (short) columnIndex++, header);
        }
        int i = 1;
        for (ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT st : elements) {
            Row row = sheet.createRow(rowIndex++);
            columnIndex = 0;
            ustawWierszZorin(i, workbook, row, columnIndex, st, rowIndex, styletext, styletextcenter, styledouble, styledata);
            i++;
        }
//        sheet.createRow(rowIndex++);//pusty row
//        if (headers.size()> 3) {
//            rowIndex = summaryRowZorin(startindex, rowIndex, workbook, sheet, nazwasumy, styletext, styleformula);
//        }
        autoAlign(sheet);
        return rowIndex;
    }
    
    private static <T> void ustawWierszZorin(int i, Workbook workbook, Row row, int columnIndex, ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT ob, int rowIndex, CellStyle styletext, CellStyle styletextcenter, CellStyle styledouble, CellStyle styledata) {
        createTextCell(styletext, row, (short) columnIndex++, String.valueOf(i++));
        createTextCell(styletext, row, (short) columnIndex++, ob.getIDZRODLA());
        if (ob.getIDZRODLA().equals("45070899-0E3F-4499-AD4E-B7061E47809F")) {
            System.out.println("");
        }
        if (ob.getOPIS().equals("Shipment no. CM330418465DE")){
            System.out.println("");
        }
        createTextCell(styletext, row, (short) columnIndex++, ob.getREJESTR());
        createDateCell(styledata, row, (short) columnIndex++, ob.getDATAWYSTAWIENIA().toGregorianCalendar().getTime());
        createDateCell(styledata, row, (short) columnIndex++, ob.getDATASPRZEDAZY().toGregorianCalendar().getTime());
        createTextCell(styletext, row, (short) columnIndex++, ob.getNUMER());
        createTextCell(styletext, row, (short) columnIndex++, ob.getKOREKTA());
        createTextCell(styletext, row, (short) columnIndex++, ob.getFISKALNA());
        createTextCell(styletext, row, (short) columnIndex++, ob.getPODMIOT());
        createTextCell(styletext, row, (short) columnIndex++, ob.getPODMIOTID());
        createTextCell(styletext, row, (short) columnIndex++, ob.getNAZWA1());
        createTextCell(styletext, row, (short) columnIndex++, ob.getKRAJ());
        createTextCell(styletext, row, (short) columnIndex++, ob.getNIP());
        createTextCell(styletext, row, (short) columnIndex++, ob.getNIPKRAJ());
        createTextCell(styletext, row, (short) columnIndex++, ob.getOPIS());
        List<ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT.POZYCJE.POZYCJA> poz = ob.getPOZYCJE().getPOZYCJA();
        ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT.PLATNOSCI.PLATNOSC plt = ob.getPLATNOSCI().getPLATNOSC()!=null?ob.getPLATNOSCI().getPLATNOSC():null;
        double kwoty[] = obliczkwoty(poz,plt, pobierzwalute(ob));
        createDoubleCell(styledouble, row, (short) columnIndex++, kwoty[3]);
        createDoubleCell(styledouble, row, (short) columnIndex++, kwoty[4]);
        createDoubleCell(styledouble, row, (short) columnIndex++, kwoty[5]);
        createTextCell(styletext, row, (short) columnIndex++, pobierzwalute(ob));
        createDoubleCell(styledouble, row, (short) columnIndex++, kwoty[0]);
        createDoubleCell(styledouble, row, (short) columnIndex++, kwoty[1]);
        createDoubleCell(styledouble, row, (short) columnIndex++, kwoty[2]);
        
    }
    private static String pobierzwalute(ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT row) {
        String zwrot = "PLN";
        if (row.getPLATNOSCI().getPLATNOSC()!=null) {
            zwrot = row.getPLATNOSCI().getPLATNOSC().getWALUTADOK();
        }
        return zwrot;
    }
    private static double[] obliczkwoty(List<ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT.POZYCJE.POZYCJA> poz, ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT.PLATNOSCI.PLATNOSC plt, String waluta) {
        double zwrot[] = new double[6];
        double pozycjenetto = 0.0;
        double pozycjevat = 0.0;
        double sumapozycje = 0.0;
        double nettopln = 0.0;
        double vatpln = 0.0;
        double bruttopln = 0.0;
        for (ROOT.REJESTRYSPRZEDAZYVAT.REJESTRSPRZEDAZYVAT.POZYCJE.POZYCJA p :poz) {
            pozycjenetto = Z.z(pozycjenetto+Double.parseDouble(obetnijwalute(p.getNETTO(), waluta)));
            pozycjevat = Z.z(pozycjevat+Double.parseDouble(obetnijwalute(p.getVAT(), waluta)));
        }
        sumapozycje = Z.z(pozycjenetto+pozycjevat);
        if (plt==null) {
            nettopln = pozycjenetto;
            vatpln = pozycjevat;
            zwrot[0] = nettopln;
            zwrot[1] = vatpln;
            zwrot[2] = Z.z(nettopln+vatpln);
            zwrot[3] = nettopln;
            zwrot[4] = vatpln;
            zwrot[5] = Z.z(nettopln+vatpln);
        } else {
            boolean pozycjesawzlotowkach = Z.z(sumapozycje) == Z.z(plt.getKWOTAPLNPLAT());
            boolean kwotyrowne = Z.z(plt.getKWOTAPLAT())==Z.z(plt.getKWOTAPLNPLAT());
            if (pozycjesawzlotowkach && kwotyrowne) {
                nettopln = pozycjenetto;
                vatpln = pozycjevat;
                zwrot[0] = nettopln;
                zwrot[1] = vatpln;
                zwrot[2] = Z.z(nettopln+vatpln);
                zwrot[3] = nettopln;
                zwrot[4] = vatpln;
                zwrot[5] = Z.z(nettopln+vatpln);
            } else {
                try {
                    if (Z.z(Math.abs(sumapozycje))==Z.z(Math.abs(plt.getKWOTAPLAT()))) {
                        double nettowaluta = pozycjenetto;
                        double vatwaluta = pozycjevat;
                        double stawkavat = vatwaluta/nettowaluta;
                        double procentvat = stawkavat/(1.0+stawkavat);
                        vatpln = Z.z(plt.getKWOTAPLNPLAT()*procentvat);
                        nettopln = Z.z(plt.getKWOTAPLNPLAT()-vatpln);
                        if (nettowaluta<0.0) {
                            nettopln = -nettopln;
                            vatpln = -vatpln;
                        }
                        zwrot[0] = nettopln;
                        zwrot[1] = vatpln;
                        zwrot[2] = Z.z(nettopln+vatpln);
                        zwrot[3] = nettowaluta;
                        zwrot[4] = vatwaluta;
                        zwrot[5] = Z.z(nettowaluta+vatwaluta);
                    } else {
                        nettopln = pozycjenetto;
                        vatpln = pozycjevat;
                        double stawkavat = vatpln/nettopln;
                        double procentvat = stawkavat/(1+stawkavat);
                        double vatwaluta = Z.z(plt.getKWOTAPLAT()*procentvat);
                        double nettowaluta = Z.z(plt.getKWOTAPLAT()-vatwaluta);
                        if (nettopln<0.0) {
                            nettowaluta = -nettowaluta;
                            vatwaluta = -vatwaluta;
                        }
                        zwrot[0] = nettopln;
                        zwrot[1] = vatpln;
                        zwrot[2] = Z.z(nettopln+vatpln);
                        zwrot[3] = nettowaluta;
                        zwrot[4] = vatwaluta;
                        zwrot[5] = Z.z(nettowaluta+vatwaluta);
                    }
                } catch (Exception e){}
            }
        }
        return zwrot;
    }
    private static String obetnijwalute(String netto, String waluta) {
        String zwrot = netto;
        if (netto.contains(waluta)) {
            zwrot = netto.replace(waluta,"");
            zwrot = zwrot.trim();
        }
        return zwrot;
    }

    private static <T> int drawATable(Workbook workbook, Sheet sheet, int rowIndex, List headers, List<T> elements, String tableheader, int typ, String nazwasumy) {
        int startindex = rowIndex+3;
        int columnIndex = 0;
        Row rowTH = sheet.createRow(rowIndex++);
        CellStyle styleheader = styleHeader(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, (short) 10);
        createHeaderCell(styleheader, rowTH, (short) 2, tableheader);
        CellRangeAddress region = new CellRangeAddress( rowIndex-1, rowIndex-1, (short) 2, (short)12);
        sheet.addMergedRegion(region);
        CellStyle styletext = styleText(workbook, HorizontalAlignment.LEFT, VerticalAlignment.CENTER);
        CellStyle styletextcenter = styleText(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER);
        CellStyle styleformula = styleFormula(workbook, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER);
        CellStyle styledouble = styleDouble(workbook, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER);
        Row rowH = sheet.createRow(rowIndex++);
        for(Iterator it = headers.iterator(); it.hasNext();){
            String header = (String) it.next();
            createHeaderCell(styleheader, rowH, (short) columnIndex++, header);
        }
        for(Iterator it = elements.iterator(); it.hasNext();){
            T st = (T) it.next();
            Row row = sheet.createRow(rowIndex++);
            columnIndex = 0;
            ustawWiersz(workbook, row, columnIndex, st, rowIndex, styletext, styletextcenter, styledouble);
        }
//        sheet.createRow(rowIndex++);//pusty row
        if (headers.size()> 3) {
            rowIndex = summaryRow(startindex, rowIndex, workbook, sheet, typ, nazwasumy, styletext, styleformula);
        }
        autoAlign(sheet);
        return rowIndex;
    }
    
    private static <T> void ustawWiersz(Workbook workbook, Row row, int columnIndex, T ob, int rowIndex, CellStyle styletext, CellStyle styletextcenter, CellStyle styledouble) {
        Class c = ob.getClass();
        if (c.getName().contains("PozycjaPrzychodKoszt")) {
            PozycjaPrzychodKoszt st = (PozycjaPrzychodKoszt) ob;
            createTextCell(styletext, row, (short) columnIndex++, String.valueOf(st.getLp()));
            createTextCell(styletext, row, (short) columnIndex++, st.getNrkonta());
            createTextCell(styletext, row, (short) columnIndex++, st.getKontoNazwapelna());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getKwota());
        } else if (c.getName().contains("PozycjaObliczenia")) {
            PozycjaObliczenia st = (PozycjaObliczenia) ob;
            createTextCell(styletext, row, (short) columnIndex++, String.valueOf(st.getLp()));
            createTextCell(styletext, row, (short) columnIndex++, st.getOpis());
            if (st.getKwota().getClass().getName().contains("Double")) {
                createDoubleCell(styledouble, row, (short) columnIndex++, (Double) st.getKwota());
            } else {
                createFormulaCell(styledouble, row, (short) columnIndex++, (String) st.getKwota());
            }
            setCellName(workbook, st.getOpis().replaceAll("\\s+",""), "C", String.valueOf(rowIndex));
        }   else if (c.getName().contains("Rodzajedok")) {
            Rodzajedok st = (Rodzajedok) ob;
            createTextCell(styletext, row, (short) columnIndex++, String.valueOf(rowIndex));
            createTextCell(styletext, row, (short) columnIndex++, st.getSkrot());
            createTextCell(styletext, row, (short) columnIndex++, st.getNazwa());
            createTextCell(styletext, row, (short) columnIndex++, st.getDe());
        }   else if (c.getName().equals("entityfk.PozycjaBilans")||c.getName().equals("entityfk.PozycjaRZiS")) {
            PozycjaRZiSBilans st = (PozycjaRZiSBilans) ob;
            createTextCell(styletext, row, (short) columnIndex++, String.valueOf(rowIndex));
            createTextCell(styletext, row, (short) columnIndex++, String.valueOf(st.getLp()));
            createTextCell(styletext, row, (short) columnIndex++, st.getPozycjaString());
            createTextCell(styletext, row, (short) columnIndex++, st.getNazwa());
            createTextCell(styletext, row, (short) columnIndex++, st.getDe());
        }   else if (c.getName().contains("SaldoKonto")) {
            SaldoKonto st = (SaldoKonto) ob;
            createTextCell(styletextcenter, row, (short) columnIndex++, String.valueOf(rowIndex));
            createTextCell(styletext, row, (short) columnIndex++, st.getKonto().getPelnynumer());
            if (czyjezykniemiecki()) {
                createTextCell(styletext, row, (short) columnIndex++, st.getKonto().getNazwapelna());
            } else {
                createTextCell(styletext, row, (short) columnIndex++, st.getKonto().getDe());
            }
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getBoWn());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getBoMa());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getObrotyWnMc());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getObrotyMaMc());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getObrotyWn());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getObrotyMa());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getObrotyBoWn());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getObrotyBoMa());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getSaldoWn());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getSaldoMa());
            createTextCell(styletext, row, (short) columnIndex++, st.getKonto().getKontomacierzyste()!=null? st.getKonto().getKontomacierzyste().getPelnynumer():"");
            if (czyjezykniemiecki()) {
                createTextCell(styletext, row, (short) columnIndex++, st.getKonto().getKontomacierzyste()!=null? st.getKonto().getKontomacierzyste().getNazwapelna():"");
            } else {
                createTextCell(styletext, row, (short) columnIndex++, st.getKonto().getKontomacierzyste()!=null? st.getKonto().getKontomacierzyste().getDe():"");
            }
        } else if (c.getName().contains("STRtabela")) {
            STRtabela st = (STRtabela) ob;
            if (!st.getDataprzek().equals("razem")) {
                createTextCell(styletextcenter, row, (short) columnIndex++, String.valueOf(st.getId()));
                createTextCell(styletext, row, (short) columnIndex++, st.getNazwa());
                createTextCell(styletext, row, (short) columnIndex++, st.getDataprzek());
                createTextCell(styletext, row, (short) columnIndex++, st.getDatasprzedazy());
                createTextCell(styletext, row, (short) columnIndex++, st.getKst());
                createDoubleCell(styledouble, row, (short) columnIndex++, st.getNetto());
                createDoubleCell(styledouble, row, (short) columnIndex++, st.getOdpisrok());
                createDoubleCell(styledouble, row, (short) columnIndex++, st.getUmorzeniaDo());
                for (String mc : Mce.getMceListS()) {
                    if (st.getM().get(mc)!=0.0) {
                        createDoubleCell(styledouble, row, (short) columnIndex++, st.getM().get(mc));
                    } else {
                        createTextCell(styletext, row, (short) columnIndex++, "");
                    }
                }
            }
        } else if (c.getName().contains("Konto")) {
            Konto st = (Konto) ob;
            createTextCell(styletext, row, (short) columnIndex++, String.valueOf(rowIndex));
            createTextCell(styletext, row, (short) columnIndex++, st.getPelnynumer());
            createTextCell(styletext, row, (short) columnIndex++, st.getNazwapelna());
            createTextCell(styletext, row, (short) columnIndex++, st.getSyntetykaanalityka()!=null?st.getSyntetykaanalityka():"");
            createTextCell(styletext, row, (short) columnIndex++, st.getDe());
        }
        
    }
    
    private static boolean czyjezykniemiecki() {
        boolean zwrot = false;
        if (FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage().equals("pl_pl") || FacesContext.getCurrentInstance().getViewRoot().getLocale().getLanguage().equals("pl")) {
            zwrot = true;
        }
        return zwrot;
    }
    
    private static int summaryRow(int startindex, int rowIndex, Workbook workbook, Sheet sheet, int typ, String nazwasumy, CellStyle styletext, CellStyle styleformula) {
         if (typ == 0) {
            String formula = "SUM(D"+startindex+":D"+rowIndex+")";
            Row row = sheet.createRow(rowIndex++);
            createTextCell(styletext, row, (short) 2, "Dochód/strata za miesiące poprzednie: ");
            createFormulaCell(styletext, row, (short) 3, formula);
            setCellName(workbook, nazwasumy, "D", String.valueOf(rowIndex));
         } else if (typ == 1) {
            String formula = "SUM(D"+startindex+":D"+rowIndex+")";
            Row row = sheet.createRow(rowIndex++);
            createTextCell(styletext, row, (short) 2, B.b("razem")+": ");
            createFormulaCell(styletext, row, (short) 3, formula);
            setCellName(workbook, nazwasumy, "D", String.valueOf(rowIndex));
        } else if (typ == 2){
            String formula = "SUM(C"+startindex+":C"+rowIndex+")";
            Row row = sheet.createRow(rowIndex++);
            createTextCell(styletext, row, (short) 1, B.b("razem")+": ");
            createFormulaCell(styletext, row, (short) 2, formula);
        } else if (typ == 3) {
            String formula = "SUM(D"+startindex+":D"+rowIndex+")";
            Row row = sheet.createRow(rowIndex);
            createTextCell(styletext, row, (short) 2, B.b("razem")+": ");
            createFormulaCell(styleformula, row, (short) 3, formula);
            formula = "SUM(E"+startindex+":E"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 4, formula);
            formula = "SUM(F"+startindex+":F"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 5, formula);
            formula = "SUM(G"+startindex+":G"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 6, formula);
            formula = "SUM(H"+startindex+":H"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 7, formula);
            formula = "SUM(I"+startindex+":I"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 8, formula);
            formula = "SUM(J"+startindex+":J"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 9, formula);
            formula = "SUM(K"+startindex+":K"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 10, formula);
            formula = "SUM(L"+startindex+":L"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 11, formula);
            formula = "SUM(M"+startindex+":M"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 12, formula);
            
        } else if (typ == 4) {
            Row row = sheet.createRow(rowIndex);
            createTextCell(styletext, row, (short) 3, B.b("razem")+": ");
            createTextCell(styletext, row, (short) 4, "");
            String formula = "SUM(F"+startindex+":F"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 5, formula);
            formula = "SUM(G"+startindex+":G"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 6, formula);
            formula = "SUM(H"+startindex+":H"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 7, formula);
            formula = "SUM(I"+startindex+":I"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 8, formula);
            formula = "SUM(J"+startindex+":J"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 9, formula);
            formula = "SUM(K"+startindex+":K"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 10, formula);
            formula = "SUM(L"+startindex+":L"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 11, formula);
            formula = "SUM(M"+startindex+":M"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 12, formula);
            formula = "SUM(N"+startindex+":N"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 13, formula);
            formula = "SUM(O"+startindex+":O"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 14, formula);
            formula = "SUM(P"+startindex+":P"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 15, formula);
            formula = "SUM(Q"+startindex+":Q"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 16, formula);
            formula = "SUM(R"+startindex+":R"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 17, formula);
            formula = "SUM(S"+startindex+":S"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 18, formula);
            formula = "SUM(T"+startindex+":T"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 19, formula);
        } 
        return rowIndex;
    }

    private static int summaryRowZorin(int startindex, int rowIndex, Workbook workbook, Sheet sheet, String nazwasumy, CellStyle styletext, CellStyle styleformula) {
            Row row = sheet.createRow(rowIndex);
            createTextCell(styletext, row, (short) 12, "Razem");
            String formula = "SUBTOTAL(9;N"+startindex+":N"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 13, formula);
            formula = "SUBTOTAL(9;O"+startindex+":O"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 14, formula);
            formula = "SUBTOTAL(9;P"+startindex+":P"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 15, formula);
            createTextCell(styletext, row, (short) 16, "");
            formula = "SUBTOTAL(9;R"+startindex+":R"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 17, formula);
            formula = "SUBTOTAL(9;S"+startindex+":S"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 18, formula);
            formula = "SUBTOTAL(9;T"+startindex+":T"+rowIndex+")";
            createFormulaCell(styleformula, row, (short) 19, formula);
        return rowIndex;
    }    

    private static void setCellName(Workbook workbook, String nazwasumy, String kolumna, String wiersz) {
        String nazwakom = "!$"+kolumna+"$"+wiersz;
        Name name;
        name = workbook.createName();
        name.setNameName(nazwasumy);
        String nazwarelacji = "'Symulacja wyniku'"+nazwakom;
        name.setRefersToFormula(nazwarelacji);
    }
    
    private static void insertPrintHeader(Sheet sheet, WpisView wpisView) {
        //do druku
        Header header = sheet.getHeader();
        header.setCenter(wpisView.getPodatnikWpisu());
        header.setLeft("Symulacja wyniku za "+wpisView.getMiesiacWpisu());
    }
    
    private static CellStyle styleHeader(Workbook wb, HorizontalAlignment halign, VerticalAlignment valign, short size) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setFont(headerfont(wb, size));
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        return cellStyle;
    }
    
    private static void createHeaderCell(CellStyle cellStyle, Row row, short column, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(new XSSFRichTextString(value));
        cell.setCellStyle(cellStyle);
    }
    
    private static CellStyle styleFormula(Workbook wb, HorizontalAlignment halign, VerticalAlignment valign) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat((short) 4);
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        return cellStyle;
    }
        
    private static void createFormulaCell(CellStyle cellStyle, Row row, short column, String formula) {
        Cell cell = row.createCell(column);
        addFormula(cell, formula);
        cell.setCellStyle(cellStyle);
    }
    
    private static CellStyle styleText(Workbook wb, HorizontalAlignment halign, VerticalAlignment valign) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        return cellStyle;
    }
    
    private static CellStyle styleData(Workbook wb, HorizontalAlignment halign, VerticalAlignment valign) {
        CellStyle cellStyle = wb.createCellStyle();
        DataFormat format = wb.createDataFormat();
        CreationHelper createHelper = wb.getCreationHelper();
        cellStyle.setDataFormat(createHelper.createDataFormat().getFormat("dd-MM-yyyy"));
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        return cellStyle;
    }
    
    private static void createTextCell(CellStyle cellStyle, Row row, short column, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(new XSSFRichTextString(value));
        cell.setCellStyle(cellStyle);
    }
    
     private static void createDateCell(CellStyle cellStyle, Row row, short column, Date value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }
    
//     private static void createCell(Workbook wb, Row row, short column, HorizontalAlignment halign, VerticalAlignment valign, String value, boolean locked) {
//        Cell cell = row.createCell(column);
//        cell.setCellValue(new HSSFRichTextString(value));
//        CellStyle cellStyle = wb.createCellStyle();
//        cellStyle.setAlignment(halign);
//        cellStyle.setLocked(locked);
//        cellStyle.setVerticalAlignment(valign);
//        cellStyle.setBorderBottom(BorderStyle.THIN);
//        cellStyle.setBorderTop(BorderStyle.THIN);
//        cellStyle.setBorderRight(BorderStyle.THIN);
//        cellStyle.setBorderLeft(BorderStyle.THIN);
//        cell.setCellStyle(cellStyle);
//    }
    
    private static CellStyle styleDouble(Workbook wb, HorizontalAlignment halign, VerticalAlignment valign) {
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat((short) 4);
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        return cellStyle;
    }
    
    private static void createDoubleCell(CellStyle cellStyle, Row row, short column, double value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        cell.setCellStyle(cellStyle);
    }
    
    
    private static Font headerfont(Workbook wb, short size) {
     // Create a new font and alter it.
        Font font = wb.createFont();
        font.setFontHeightInPoints(size);
        font.setFontName("Arial");
        font.setBold(true);
        return font;
    }
    private static void addFormula(Cell cell, String formula) {
        //cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
        cell.setCellFormula(formula);
    }
    
    private static void autoAlign(Sheet sheet) {
        for (int i=0;i<22;i++) {
            sheet.autoSizeColumn((short) i);
        }
    }
    
    public static void main (String[] args){
        //List lista = listy.get("kontasalda");
        List headersList = headersusa();
        // Using XSSF for xlsx format, for xls use HSSF
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Obr.Saldaa");
        Header header = sheet.getHeader();
        header.setCenter("Podatnik");
        header.setLeft("Symulacja wyniku za 05/2019");
        int rowIndex = 0;
        String opis = "Zestawienie obrotów i sald  na koniec 12/85";
        //rowIndex = drawATable(workbook, sheet, rowIndex, headersList, lista, opis, 3, "kontasalda");
//        workbook.setPrintArea(
//        0, //sheet index
//        0, //start column
//        4, //end column
//        0, //start row
//        rowIndex //end row
//        );
      //set paper size
//        sheet.getPrintSetup().setPaperSize(XSSFPrintSetup.A4_PAPERSIZE);
//        sheet.setFitToPage(true);
        //ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
        //String realPath = ctx.getRealPath("/");
        String FILE_PATH = FILE_PATH2;
        //write this workbook in excel file.
        try {
            OutputStream fos = new FileOutputStream(FILE_PATH);
            workbook.write(fos);
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
     }
}
