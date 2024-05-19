/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import entity.Definicjalistaplac;
import entity.Pasekwynagrodzen;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import msg.B;
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
import view.WpisView;
import z.Z;

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
    
    public static Workbook listaplacXLS(List paski, WpisView wpisView, Definicjalistaplac wybranalistaplac, double kursdlalisty){
        Workbook workbook = null;
        try {
            List headerpasekwyn = headerpasek();
            // Using XSSF for xlsx format, for xls use HSSF
            workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet(wybranalistaplac.getDatasporzadzenia());
            insertPrintHeader(sheet, wpisView);
            int rowIndex = 0;
            rowIndex = drawATable(workbook, sheet, rowIndex, headerpasekwyn, paski, "Lista płac "+wybranalistaplac.getNrkolejny(), 0, "wynikmcepop", wpisView.getFirma().getNazwa(), kursdlalisty);
            sheet.createRow(rowIndex++);
    //         workbook.setPrintArea(
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
    
    
    
    public static List headerpasek() {
        List headersListPrzychodKoszt = new ArrayList();
        headersListPrzychodKoszt.add("nazwisko i imie");
        headersListPrzychodKoszt.add("data wypłaty");
        headersListPrzychodKoszt.add("pesel");
        headersListPrzychodKoszt.add("wiek");
        headersListPrzychodKoszt.add("brutto");
        headersListPrzychodKoszt.add("z zus");
        headersListPrzychodKoszt.add("z zus bez pod.");
        headersListPrzychodKoszt.add("bez społ.");
        headersListPrzychodKoszt.add("bez zus");
        headersListPrzychodKoszt.add("bez zus i pod.");
        headersListPrzychodKoszt.add("za oddeleg. w pln");
        headersListPrzychodKoszt.add("brutto kraj");
        headersListPrzychodKoszt.add("za oddeleg. w wal.");
        headersListPrzychodKoszt.add("kurs");
        headersListPrzychodKoszt.add("dieta waluta");
        headersListPrzychodKoszt.add("dieta pln");
        headersListPrzychodKoszt.add("limit bez zus");
        headersListPrzychodKoszt.add("kwota ponad limit");
        headersListPrzychodKoszt.add("podst.społ.");
        headersListPrzychodKoszt.add("chorobowe");
        headersListPrzychodKoszt.add("emerytalne");
        headersListPrzychodKoszt.add("rentowe");
        headersListPrzychodKoszt.add("razem społ. prac.");
        headersListPrzychodKoszt.add("razem społ. prac. Polska");
        headersListPrzychodKoszt.add("brutto-zus");
        headersListPrzychodKoszt.add("1/3 diet");
        headersListPrzychodKoszt.add("koszty uzyskania");
        headersListPrzychodKoszt.add("podstawa opodatk.");
        headersListPrzychodKoszt.add("podstawa korekta");
        headersListPrzychodKoszt.add("podatek");
        headersListPrzychodKoszt.add("podatek wst. korekta");
        headersListPrzychodKoszt.add("kwota wolna");
        headersListPrzychodKoszt.add("pod. ubezp zdrow.");
        headersListPrzychodKoszt.add("prac zdrowotne");
        headersListPrzychodKoszt.add("podatek dochodowy");
        headersListPrzychodKoszt.add("podatek korekta");
        headersListPrzychodKoszt.add("podatek zagr. wal.");
        headersListPrzychodKoszt.add("podatek zagr. pln");
        headersListPrzychodKoszt.add("netto");
        headersListPrzychodKoszt.add("potracenia");
        headersListPrzychodKoszt.add("do wypł.");
        headersListPrzychodKoszt.add("w EUR");
        headersListPrzychodKoszt.add("emerytalne");
        headersListPrzychodKoszt.add("rentowe");
        headersListPrzychodKoszt.add("wypadkowe");
        headersListPrzychodKoszt.add("razem społ. firma.");
        headersListPrzychodKoszt.add("FP");
        headersListPrzychodKoszt.add("FGŚP");
        headersListPrzychodKoszt.add("koszt prac.");
        headersListPrzychodKoszt.add("wolne od zajęcia");
        headersListPrzychodKoszt.add("wyn. minimalne");
        headersListPrzychodKoszt.add("dni obowiązku");
        headersListPrzychodKoszt.add("dni przepracowane");
        headersListPrzychodKoszt.add("godziny obowiązku");
        headersListPrzychodKoszt.add("godziny przepracowane");
        headersListPrzychodKoszt.add("nr konta");
        return headersListPrzychodKoszt;
    }
    
    
  
    private static String obetnijwalute(String netto, String waluta) {
        String zwrot = netto;
        if (netto.contains(waluta)) {
            zwrot = netto.replace(waluta,"");
            zwrot = zwrot.trim();
        }
        return zwrot;
    }

    private static <T> int drawATable(Workbook workbook, Sheet sheet, int rowIndex, List headers, List<T> elements, String tableheader, int typ, String nazwasumy, String firma, double kursdlalisty) {
        int startindex = rowIndex+3;
        int columnIndex = 0;
        Row rowTH = sheet.createRow(rowIndex++);
        CellStyle styleheader = styleHeader(workbook, HorizontalAlignment.CENTER, VerticalAlignment.CENTER, (short) 10);
        createHeaderCell(styleheader, rowTH, (short) 0, firma);
        createHeaderCell(styleheader, rowTH, (short) 6, tableheader);
        CellRangeAddress region = new CellRangeAddress( rowIndex-1, rowIndex-1, (short) 6, (short)14);
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
            ustawWiersz(workbook, row, columnIndex, st, rowIndex, styletext, styletextcenter, styledouble, kursdlalisty);
        }
//        sheet.createRow(rowIndex++);//pusty row
//        if (headers.size()> 3) {
//            rowIndex = summaryRow(startindex, rowIndex, workbook, sheet, typ, nazwasumy, styletext, styleformula);
//        }
        autoAlign(sheet);
        sheet.createFreezePane(4, 2);
        return rowIndex;
    }
    
    private static <T> void ustawWiersz(Workbook workbook, Row row, int columnIndex, T ob, int rowIndex, CellStyle styletext, CellStyle styletextcenter, CellStyle styledouble, double kursdlalisty) {
        Class c = ob.getClass();
        if (c.getName().contains("Pasekwynagrodzen")) {
            Pasekwynagrodzen st = (Pasekwynagrodzen) ob;
            createTextCell(styletext, row, (short) columnIndex++, String.valueOf(st.getNazwiskoImie()));
            createTextCell(styletext, row, (short) columnIndex++, st.getDatawyplaty());
            createTextCell(styletext, row, (short) columnIndex++, st.getPesel());
            createTextCell(styletext, row, (short) columnIndex++, st.getWiekpasek());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getBrutto());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getBruttozus());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getBruttobezzusbezpodatek());
            //createDoubleCell(styledouble, row, (short) columnIndex++, -1000);
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getBruttobezspolecznych());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getBruttobezzus());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getBruttobezzusbezpodatek());
            //createDoubleCell(styledouble, row, (short) columnIndex++, -1000);
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getOddelegowanieplnToczek());
            double bruttokraj = st.getBruttozuskraj()>0.0?st.getBruttozuskraj():st.getBrutto()-st.getOddelegowanieplnToczek();
            createDoubleCell(styledouble, row, (short) columnIndex++, bruttokraj);
            double oddelegowaniewaluta = Z.z(st.getOddelegowaniepln()/kursdlalisty);
            createDoubleCell(styledouble, row, (short) columnIndex++, oddelegowaniewaluta);
            double kursdowstawienia = st.getKurs()>0.0?st.getKurs():kursdlalisty;
            createDoubleCell(styledouble, row, (short) columnIndex++, kursdowstawienia);
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getDietawaluta());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getDieta());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getLimitzus());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getLimitzuspoza());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getPodstawaskladkizus());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getPracchorobowe());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getPracemerytalne());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getPracrentowe());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getRazemspolecznepracownik());
            double porcentskladek = bruttokraj/st.getPodstawaskladkizus();
            double skladkipolska = Z.z(st.getRazemspolecznepracownik()*porcentskladek);
            createDoubleCell(styledouble, row, (short) columnIndex++, skladkipolska);
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getBruttominusspoleczne());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getDietaodliczeniepodstawaop());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getKosztyuzyskania());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getPodstawaopodatkowania());
            createDoubleCell(styledouble, row, (short) columnIndex++, Z.z0(bruttokraj-skladkipolska-st.getKosztyuzyskania()));
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getPodatekwstepny());
            createDoubleCell(styledouble, row, (short) columnIndex++, 0.0);
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getKwotawolna());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getPodstawaubezpzdrowotne());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getPraczdrowotne());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getPodatekdochodowy());
            createDoubleCell(styledouble, row, (short) columnIndex++, 0.0);
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getPodatekdochodowyzagranicawaluta());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getPodatekdochodowyzagranica());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getNettoprzedpotraceniami());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getPotracenia());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getNetto());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getNettowaluta());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getPracemerytalne());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getRentowe());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getWypadkowe());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getRazemspolecznefirma());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getFp());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getFgsp());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getKosztpracodawcy());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getWolneodzajecia());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getWynagrodzenieminimalne());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getDniobowiazku());
            createDoubleCell(styledouble, row, (short) columnIndex++, st.getDniprzepracowane());
            if (st.getGodzinyobowiazku()!=null) {
                createDoubleCell(styledouble, row, (short) columnIndex++, st.getGodzinyobowiazku());
                createDoubleCell(styledouble, row, (short) columnIndex++, st.getGodzinyprzepracowane());
            } else {
                createTextCell(styledouble, row, (short) columnIndex++, "");
                createTextCell(styledouble, row, (short) columnIndex++, "");
            }
            createTextCell(styledouble, row, (short) columnIndex++, st.getNrkonta());
        }
        //else if (c.getName().contains("PozycjaObliczenia")) {
//            PozycjaObliczenia st = (PozycjaObliczenia) ob;
//            createTextCell(styletext, row, (short) columnIndex++, String.valueOf(st.getLp()));
//            createTextCell(styletext, row, (short) columnIndex++, st.getOpis());
//            if (st.getKwota().getClass().getName().contains("Double")) {
//                createDoubleCell(styledouble, row, (short) columnIndex++, (Double) st.getKwota());
//            } else {
//                createFormulaCell(styledouble, row, (short) columnIndex++, (String) st.getKwota());
//            }
//            setCellName(workbook, st.getOpis().replaceAll("\\s+",""), "C", String.valueOf(rowIndex));
//        }   else if (c.getName().contains("Rodzajedok")) {
//            Rodzajedok st = (Rodzajedok) ob;
//            createTextCell(styletext, row, (short) columnIndex++, String.valueOf(rowIndex));
//            createTextCell(styletext, row, (short) columnIndex++, st.getSkrot());
//            createTextCell(styletext, row, (short) columnIndex++, st.getNazwa());
//            createTextCell(styletext, row, (short) columnIndex++, st.getDe());
//        }   else if (c.getName().equals("entityfk.PozycjaBilans")||c.getName().equals("entityfk.PozycjaRZiS")) {
//            PozycjaRZiSBilans st = (PozycjaRZiSBilans) ob;
//            createTextCell(styletext, row, (short) columnIndex++, String.valueOf(rowIndex));
//            createTextCell(styletext, row, (short) columnIndex++, String.valueOf(st.getLp()));
//            createTextCell(styletext, row, (short) columnIndex++, st.getPozycjaString());
//            createTextCell(styletext, row, (short) columnIndex++, st.getNazwa());
//            createTextCell(styletext, row, (short) columnIndex++, st.getDe());
//        }   else if (c.getName().contains("SaldoKonto")) {
//            SaldoKonto st = (SaldoKonto) ob;
//            createTextCell(styletextcenter, row, (short) columnIndex++, String.valueOf(rowIndex));
//            createTextCell(styletext, row, (short) columnIndex++, st.getKonto().getPelnynumer());
//            if (czyjezykniemiecki()) {
//                createTextCell(styletext, row, (short) columnIndex++, st.getKonto().getNazwapelna());
//            } else {
//                createTextCell(styletext, row, (short) columnIndex++, st.getKonto().getDe());
//            }
//            createDoubleCell(styledouble, row, (short) columnIndex++, st.getBoWn());
//            createDoubleCell(styledouble, row, (short) columnIndex++, st.getBoMa());
//            createDoubleCell(styledouble, row, (short) columnIndex++, st.getObrotyWnMc());
//            createDoubleCell(styledouble, row, (short) columnIndex++, st.getObrotyMaMc());
//            createDoubleCell(styledouble, row, (short) columnIndex++, st.getObrotyWn());
//            createDoubleCell(styledouble, row, (short) columnIndex++, st.getObrotyMa());
//            createDoubleCell(styledouble, row, (short) columnIndex++, st.getObrotyBoWn());
//            createDoubleCell(styledouble, row, (short) columnIndex++, st.getObrotyBoMa());
//            createDoubleCell(styledouble, row, (short) columnIndex++, st.getSaldoWn());
//            createDoubleCell(styledouble, row, (short) columnIndex++, st.getSaldoMa());
//            createTextCell(styletext, row, (short) columnIndex++, st.getKonto().getKontomacierzyste()!=null? st.getKonto().getKontomacierzyste().getPelnynumer():"");
//            if (czyjezykniemiecki()) {
//                createTextCell(styletext, row, (short) columnIndex++, st.getKonto().getKontomacierzyste()!=null? st.getKonto().getKontomacierzyste().getNazwapelna():"");
//            } else {
//                createTextCell(styletext, row, (short) columnIndex++, st.getKonto().getKontomacierzyste()!=null? st.getKonto().getKontomacierzyste().getDe():"");
//            }
//        } else if (c.getName().contains("STRtabela")) {
//            STRtabela st = (STRtabela) ob;
//            if (!st.getDataprzek().equals("razem")) {
//                createTextCell(styletextcenter, row, (short) columnIndex++, String.valueOf(st.getId()));
//                createTextCell(styletext, row, (short) columnIndex++, st.getNazwa());
//                createTextCell(styletext, row, (short) columnIndex++, st.getDataprzek());
//                createTextCell(styletext, row, (short) columnIndex++, st.getDatasprzedazy());
//                createTextCell(styletext, row, (short) columnIndex++, st.getKst());
//                createDoubleCell(styledouble, row, (short) columnIndex++, st.getNetto());
//                createDoubleCell(styledouble, row, (short) columnIndex++, st.getOdpisrok());
//                createDoubleCell(styledouble, row, (short) columnIndex++, st.getUmorzeniaDo());
//                for (String mc : Mce.getMceListS()) {
//                    if (st.getM().get(mc)!=0.0) {
//                        createDoubleCell(styledouble, row, (short) columnIndex++, st.getM().get(mc));
//                    } else {
//                        createTextCell(styletext, row, (short) columnIndex++, "");
//                    }
//                }
//            }
//        } else if (c.getName().contains("Konto")) {
//            Konto st = (Konto) ob;
//            createTextCell(styletext, row, (short) columnIndex++, String.valueOf(rowIndex));
//            createTextCell(styletext, row, (short) columnIndex++, st.getPelnynumer());
//            createTextCell(styletext, row, (short) columnIndex++, st.getNazwapelna());
//            createTextCell(styletext, row, (short) columnIndex++, st.getSyntetykaanalityka()!=null?st.getSyntetykaanalityka():"");
//            createTextCell(styletext, row, (short) columnIndex++, st.getDe());
//        }
        
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
        header.setCenter(wpisView.getFirma().getNazwa());
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
        cellStyle.setWrapText(true);
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
        for (int i=0;i<4;i++) {
            sheet.autoSizeColumn((short) i);
        }
    }
    
    public static void main (String[] args){
        //List lista = listy.get("kontasalda");
        //List headersList = headersusa();
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
