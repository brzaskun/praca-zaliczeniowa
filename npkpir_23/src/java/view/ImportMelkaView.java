/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.ListaEwidencjiVat;
import beansJPK.KlienciJPKBean;
import dao.KlientJPKDAO;
import data.Data;
import embeddablefk.InterpaperXLS;
import entity.Evewidencja;
import entity.KlientJPK;
import error.E;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ImportMelkaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<InterpaperXLS> dokumenty;
    private List<InterpaperXLS> selected;
    private List<InterpaperXLS> filter;
    @Inject
    private WpisView wpisView;
    @Inject
    private KlientJPKDAO klientJPKDAO;
    private byte[] pobraneplikibytes;
    @Inject
    private ListaEwidencjiVat listaEwidencjiVat;
    private Evewidencja evewidencja23;
    private Evewidencja evewidencja8;

      
//    private boolean fakturypolska;
//    private boolean deklaracjaniemiecka;
        
    @PostConstruct
    private void init() { //E.m(this);
        dokumenty = Collections.synchronizedList(new ArrayList<>());
        for (Evewidencja p : listaEwidencjiVat.getSprzedazVList()) {
            if (p.getNazwa().equals("sprzedaż 23%")) {
                evewidencja23 = p;
            }
            if (p.getNazwa().equals("sprzedaż 8%")) {
                evewidencja8 = p;
            }
        }
    }
    
    public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName()).toLowerCase();
            if (extension.equals("xls")||extension.equals("xlsx")) {
                String filename = uploadedFile.getFileName();
                pobraneplikibytes = uploadedFile.getContents();
                getListafaktur();
                //plikinterpaper = uploadedFile.getContents();
                Msg.msg("Sukces. Plik xls " + filename + " został skutecznie załadowany");
            } else {
                Msg.msg("e","Niewłaściwy typ pliku");
            }
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
    }
    
   private static String filename = "d://matacz.xls";
    
    public void getListafaktur() {
         try {
            InputStream file = new ByteArrayInputStream(pobraneplikibytes);
             //Create Workbook instance holding reference to .xlsx file
            Workbook workbook = WorkbookFactory.create(file);
             //Get first/desired sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);
             //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int i =1;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getCell(0).getRowIndex()>0) {
                    String nrfakt = row.getCell(3).getStringCellValue();
                    Date data = row.getCell(1).getDateCellValue();
                    String mc = Data.getMc(Data.data_yyyyMMdd(data));
                    InterpaperXLS interpaperXLS = new InterpaperXLS();
                    interpaperXLS.setNrfaktury(nrfakt);
                    interpaperXLS.setDatawystawienia(data);
                    interpaperXLS.setDatasprzedaży(data);
                    interpaperXLS.setKontrahent(pobierzkontrahenta(row.getCell(4)));
                    String nip = pobierznip(row.getCell(8));
                    interpaperXLS.setNip(nip);
                    String waluta = "PLN";
                    //interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni));
                    interpaperXLS.setWalutaplatnosci(waluta);
                    interpaperXLS.setNettowaluta(row.getCell(9).getNumericCellValue());
                    interpaperXLS.setVatwaluta(row.getCell(10).getNumericCellValue());
                    interpaperXLS.setBruttowaluta(Z.z(interpaperXLS.getNettowaluta()+interpaperXLS.getVatwaluta()));
                    interpaperXLS.setNettoPLN(row.getCell(9).getNumericCellValue());
                    interpaperXLS.setVatPLN(row.getCell(10).getNumericCellValue());
                    interpaperXLS.setBruttoPLN(Z.z(interpaperXLS.getNettowaluta()+interpaperXLS.getVatwaluta()));
                    interpaperXLS.setNr(i);
                    if (mc.equals(wpisView.getMiesiacWpisu())) {
                        i++;
                        dokumenty.add(interpaperXLS);
                    }
                 }
            }
            file.close();
        }
        catch (Exception e) {
            E.e(e);
        }
    }
    
     
    
    
    
      
    public static void main(String[] args) {
        try {
            FileInputStream file = new FileInputStream(new File(filename));
             //Create Workbook instance holding reference to .xlsx file
            Workbook workbook = WorkbookFactory.create(file);
             //Get first/desired sheet from the workbook
            Sheet sheet = workbook.getSheetAt(0);
             //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 1;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getCell(0).getRowIndex()>0) {
                    String text = row.getCell(1).getStringCellValue();
                    int textl = text.length();
                    String nrfakt = text.substring(0, textl-10);
                    String data = text.substring(textl-10,textl);
                    InterpaperXLS interpaperXLS = new InterpaperXLS();
                    interpaperXLS.setNrfaktury(nrfakt);
                    interpaperXLS.setDatawystawienia(Data.stringToDate(data));
                    interpaperXLS.setDatasprzedaży(Data.stringToDate(data));
                    interpaperXLS.setKontrahent(pobierzkontrahenta(row.getCell(4)));
                    String nip = row.getCell(8).getStringCellValue();
                    interpaperXLS.setNip(nip);
                    String waluta = "PLN";
                    //interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni));
                    interpaperXLS.setWalutaplatnosci(waluta);
                    interpaperXLS.setNettowaluta(zamiennakwote(row.getCell(9).getStringCellValue()));
                    interpaperXLS.setVatwaluta(zamiennakwote(row.getCell(10).getStringCellValue()));
                    interpaperXLS.setBruttowaluta(Z.z(interpaperXLS.getNettowaluta()+interpaperXLS.getVatwaluta()));
                    interpaperXLS.setNettoPLN(zamiennakwote(row.getCell(9).getStringCellValue()));
                    interpaperXLS.setVatPLN(zamiennakwote(row.getCell(10).getStringCellValue()));
                    interpaperXLS.setBruttoPLN(Z.z(interpaperXLS.getNettowaluta()+interpaperXLS.getVatwaluta()));
                    interpaperXLS.setNr(i);
                    i++;
                 }
            }
            file.close();
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    private static double zamiennakwote(String stringCellValue) {
        int nettowalutasize = stringCellValue.length();
        String waluta = stringCellValue.substring(nettowalutasize-3, nettowalutasize);
        waluta = " "+waluta;
        String kwota = stringCellValue.replace(waluta,"");
        kwota = kwota.replace(",", ".");
        kwota = kwota.replace(" ", "");
        double zwrot = Z.z(Double.parseDouble(kwota));
        return zwrot;
    }
    
    private static String pobierzkontrahenta(Cell cell) {
        String zwrot = "Błąd w nazwie kontrahenta";
        try {
            zwrot = cell.getStringCellValue();
        } catch (Exception e) {}
        return zwrot;
    }
    
    
    private String pobierznip(Cell cell) {
        DataFormatter formatter = new DataFormatter(new Locale("pl_pl"));;
        String nip = null;
        try {
            nip = formatter.formatCellValue(cell);
            nip = nip.replace("\\s+", "");
        } catch (Exception e){}
        try {
            nip = cell.getStringCellValue().replace("\\s+", "");
        } catch (Exception e){}            
        return nip;
    }

    public void zaksiegujdokjpk() {
        klientJPKDAO.deleteByPodRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        List<KlientJPK> lista = KlienciJPKBean.zaksiegujdokJPK(dokumenty, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        klientJPKDAO.createList(lista);
        Msg.msg("Zaksięgowano dokumenty dla JPK");
    }
    

    public List<InterpaperXLS> getDokumenty() {
        return dokumenty;
    }

    public void setDokumenty(List<InterpaperXLS> dokumenty) {
        this.dokumenty = dokumenty;
    }

    public List<InterpaperXLS> getSelected() {
        return selected;
    }

    public void setSelected(List<InterpaperXLS> selected) {
        this.selected = selected;
    }

    public List<InterpaperXLS> getFilter() {
        return filter;
    }

    public void setFilter(List<InterpaperXLS> filter) {
        this.filter = filter;
    }


    
    
 
    
}
