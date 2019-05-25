/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import embeddablefk.SaldoKonto;
import embeddablefk.TreeNodeExtended;
import entity.Rodzajedok;
import entityfk.Konto;
import entityfk.PozycjaRZiSBilans;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Header;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Name;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFPrintSetup;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import view.WpisView;

/**
 *
 * @author Osito
 */
public class WriteXLSFile {
    private static final String FILE_PATH1 = "wydruki/xlsfile.xlsx";
    //We are making use of a single instance to prevent multiple write access to same file.
    private static final WriteXLSFile INSTANCE = new WriteXLSFile();

    public static WriteXLSFile getInstance() {
        return INSTANCE;
    }

    private WriteXLSFile() {
    }

    public static void main(String args[]){
        Map<String, List> l = new ConcurrentHashMap<>();
        l.put("p", listaprzychody());
        l.put("k", listakoszty());
        l.put("w", listawynik());
        l.put("o", listapodatek());
        //zachowajXLS(l, WpisView);
    }
    
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
    
    
    public static Workbook zachowajRZiSInterXLS(Map<String, List> listy, WpisView wpisView){
        List listapozycji = listy.get("rzisinter");
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
    
    public static Workbook zachowajBilansInterXLS(Map<String, List> listy, WpisView wpisView){
        List listapozycji = listy.get("bilansinter");
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
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("Obr.Salda");
        insertPrintHeader(sheet, wpisView);
        int rowIndex = 0;
        String opis = "Zestawienie obrotów i sald "+wpisView.getPrintNazwa()+" na koniec "+wpisView.getRokWpisuSt()+"/"+wpisView.getMiesiacWpisu();
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
        headers.add("lp");
        headers.add("nr konta");
        headers.add("nazwa konta");
        headers.add("saldo BO Wn");
        headers.add("saldo BO Ma");
        headers.add("obroty Wn");
        headers.add("obroty Ma");
        headers.add("obroty Wn nar");
        headers.add("obroty Ma nar");
        headers.add("suma BO Wn");
        headers.add("suma BO Ma");
        headers.add("saldo Wn");
        headers.add("saldo Ma");
        headers.add("nr konta");
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
    
    private static <T> int drawATable(Workbook workbook, Sheet sheet, int rowIndex, List headers, List<T> elements, String tableheader, int typ, String nazwasumy) {
        int startindex = rowIndex+3;
        int columnIndex = 0;
        Row rowTH = sheet.createRow(rowIndex++);
        createHeaderCell(workbook, rowTH, (short) 2, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, (short) 11, tableheader);
        Row rowH = sheet.createRow(rowIndex++);
        for(Iterator it = headers.iterator(); it.hasNext();){
            String header = (String) it.next();
            createHeaderCell(workbook, rowH, (short) columnIndex++, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, (short) 10, header);
        }
        for(Iterator it = elements.iterator(); it.hasNext();){
            T st = (T) it.next();
            Row row = sheet.createRow(rowIndex++);
            columnIndex = 0;
            ustawWiersz(workbook, row, columnIndex, st, rowIndex);
        }
//        sheet.createRow(rowIndex++);//pusty row
        if (headers.size()> 3) {
            rowIndex = summaryRow(startindex, rowIndex, workbook, sheet, typ, nazwasumy);
        }
        autoAlign(sheet);
        return rowIndex;
    }
    
    private static <T> void ustawWiersz(Workbook workbook, Row row, int columnIndex, T ob, int rowIndex) {
        Class c = ob.getClass();
        if (c.getName().contains("PozycjaPrzychodKoszt")) {
            PozycjaPrzychodKoszt st = (PozycjaPrzychodKoszt) ob;
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, String.valueOf(st.getLp()));
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, st.getNrkonta());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, st.getKontoNazwapelna());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, st.getKwota());
        } else if (c.getName().contains("PozycjaObliczenia")) {
            PozycjaObliczenia st = (PozycjaObliczenia) ob;
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, String.valueOf(st.getLp()));
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, st.getOpis());
            if (st.getKwota().getClass().getName().contains("Double")) {
                createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, (Double) st.getKwota());
            } else {
                createFormulaCell(workbook, row, (short) columnIndex++, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, (String) st.getKwota());
            }
            setCellName(workbook, st.getOpis().replaceAll("\\s+",""), "C", String.valueOf(rowIndex));
        }   else if (c.getName().contains("Rodzajedok")) {
            Rodzajedok st = (Rodzajedok) ob;
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, String.valueOf(rowIndex));
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, st.getSkrot());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, st.getNazwa());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, st.getDe());
        }   else if (c.getName().contains("TreeNodeExtended")) {
            PozycjaRZiSBilans st = (PozycjaRZiSBilans) ((TreeNodeExtended) ob).getData();
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, String.valueOf(rowIndex));
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, String.valueOf(st.getLp()));
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, st.getPozycjaString());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, st.getNazwa());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, st.getDe());
        }   else if (c.getName().contains("SaldoKonto")) {
            SaldoKonto st = (SaldoKonto) ob;
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, String.valueOf(rowIndex));
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, st.getKonto().getPelnynumer());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, st.getKonto().getNazwapelna());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, (Double) st.getBoWn());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, (Double) st.getBoMa());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, (Double) st.getObrotyWnMc());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, (Double) st.getObrotyMaMc());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, (Double) st.getObrotyWn());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, (Double) st.getObrotyMa());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, (Double) st.getObrotyBoWn());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, (Double) st.getObrotyBoMa());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, (Double) st.getSaldoWn());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, (Double) st.getSaldoMa());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, st.getKonto().getPelnynumer());
        } else if (c.getName().contains("Konto")) {
            Konto st = (Konto) ob;
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, String.valueOf(rowIndex));
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, st.getPelnynumer());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, st.getNazwapelna());
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, st.getSyntetykaanalityka()!=null?st.getSyntetykaanalityka():"");
            createCell(workbook, row, (short) columnIndex++, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, st.getDe());
        }
        
    }
    
    private static int summaryRow(int startindex, int rowIndex, Workbook workbook, Sheet sheet, int typ, String nazwasumy) {
         if (typ == 0) {
            String formula = "SUM(D"+startindex+":D"+rowIndex+")";
            Row row = sheet.createRow(rowIndex++);
            createCell(workbook, row, (short) 2, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, "Dochód/strata za miesiące poprzednie: ");
            createFormulaCell(workbook, row, (short) 3, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, formula);
            setCellName(workbook, nazwasumy, "D", String.valueOf(rowIndex));
         } else if (typ == 1) {
            String formula = "SUM(D"+startindex+":D"+rowIndex+")";
            Row row = sheet.createRow(rowIndex++);
            createCell(workbook, row, (short) 2, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, "Razem: ");
            createFormulaCell(workbook, row, (short) 3, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, formula);
            setCellName(workbook, nazwasumy, "D", String.valueOf(rowIndex));
        } else if (typ == 2){
            String formula = "SUM(C"+startindex+":C"+rowIndex+")";
            Row row = sheet.createRow(rowIndex++);
            createCell(workbook, row, (short) 1, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, "Razem: ");
            createFormulaCell(workbook, row, (short) 2, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, formula);
        } else if (typ == 3) {
            String formula = "SUM(D"+startindex+":D"+rowIndex+")";
            Row row = sheet.createRow(rowIndex);
            createCell(workbook, row, (short) 2, HorizontalAlignment.LEFT, VerticalAlignment.CENTER, "Razem: ");
            createFormulaCell(workbook, row, (short) 3, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, formula);
            formula = "SUM(E"+startindex+":E"+rowIndex+")";
            createFormulaCell(workbook, row, (short) 4, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, formula);
            formula = "SUM(F"+startindex+":F"+rowIndex+")";
            createFormulaCell(workbook, row, (short) 5, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, formula);
            formula = "SUM(G"+startindex+":G"+rowIndex+")";
            createFormulaCell(workbook, row, (short) 6, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, formula);
            formula = "SUM(H"+startindex+":H"+rowIndex+")";
            createFormulaCell(workbook, row, (short) 7, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, formula);
            formula = "SUM(I"+startindex+":I"+rowIndex+")";
            createFormulaCell(workbook, row, (short) 8, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, formula);
            formula = "SUM(J"+startindex+":J"+rowIndex+")";
            createFormulaCell(workbook, row, (short) 9, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, formula);
            formula = "SUM(K"+startindex+":K"+rowIndex+")";
            createFormulaCell(workbook, row, (short) 10, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, formula);
            formula = "SUM(L"+startindex+":L"+rowIndex+")";
            createFormulaCell(workbook, row, (short) 11, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, formula);
            formula = "SUM(M"+startindex+":M"+rowIndex+")";
            createFormulaCell(workbook, row, (short) 12, HorizontalAlignment.RIGHT, VerticalAlignment.CENTER, formula);
            
        } 
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
    
    private static void createHeaderCell(Workbook wb, Row row, short column, HorizontalAlignment halign, VerticalAlignment valign, short size, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(new HSSFRichTextString(value));
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setFont(headerfont(wb, size));
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cell.setCellStyle(cellStyle);
    }
    
    private static void createFormulaCell(Workbook wb, Row row, short column, HorizontalAlignment halign, VerticalAlignment valign, String formula) {
        Cell cell = row.createCell(column);
        addFormula(cell, formula);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat((short) 4);
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cell.setCellStyle(cellStyle);
    }
    
    private static void createCell(Workbook wb, Row row, short column, HorizontalAlignment halign, VerticalAlignment valign, String value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(new HSSFRichTextString(value));
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cell.setCellStyle(cellStyle);
    }
    
     private static void createCell(Workbook wb, Row row, short column, HorizontalAlignment halign, VerticalAlignment valign, String value, boolean locked) {
        Cell cell = row.createCell(column);
        cell.setCellValue(new HSSFRichTextString(value));
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(halign);
        cellStyle.setLocked(locked);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cell.setCellStyle(cellStyle);
    }
    
    private static void createCell(Workbook wb, Row row, short column, HorizontalAlignment halign, VerticalAlignment valign, double value) {
        Cell cell = row.createCell(column);
        cell.setCellValue(value);
        CellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat((short) 4);
        cellStyle.setAlignment(halign);
        cellStyle.setVerticalAlignment(valign);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
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
        cell.setCellType(HSSFCell.CELL_TYPE_FORMULA);
        cell.setCellFormula(formula);
    }
    
    private static void autoAlign(Sheet sheet) {
        sheet.autoSizeColumn((short) 0);
        sheet.autoSizeColumn((short) 1);
        sheet.autoSizeColumn((short) 2);
        sheet.autoSizeColumn((short) 3);
        sheet.autoSizeColumn((short) 4);
    }
}
