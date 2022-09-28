/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Uczestnicy;
import error.E;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import msg.Msg;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.model.file.UploadedFile;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ImportView implements Serializable {
    private static final long serialVersionUID = 1L;
    private byte[] pobraneplikibytes;
    private List<Uczestnicy> lista;

    public ImportView() {
        lista = new ArrayList<>();
    }
    
    
     public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName()).toLowerCase();
            if (extension.equals("xls")||extension.equals("xlsx")) {
                String filename = uploadedFile.getFileName();
                pobraneplikibytes = uploadedFile.getContent();
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
                if (row.getRowNum()>0) {
                    Uczestnicy uczestnik = new Uczestnicy();
                    uczestnik.setEmail(row.getCell(1).getStringCellValue());
                    uczestnik.setImienazwisko(row.getCell(2).getStringCellValue());
                    uczestnik.setPlec(row.getCell(3).getStringCellValue());
                    uczestnik.setNazwaszkolenia(row.getCell(4).getStringCellValue());
                    uczestnik.setIndetyfikator(row.getCell(5).getStringCellValue());
                    uczestnik.setNrupowaznienia(row.getCell(6).getStringCellValue());
                    
                    lista.add(uczestnik);
                    i++;
                 }
            }
            file.close();
        }
        catch (Exception e) {
            E.e(e);
        }
    }
     
     public void onRowEdit(RowEditEvent<Uczestnicy> event) {
        Msg.msg("Zmieniono dane użytkownika");
    }

    public void onRowCancel(RowEditEvent<Uczestnicy> event) {
        Msg.msg("w","Niezmieniono danych użytkownika");
    }

    public List<Uczestnicy> getLista() {
        return lista;
    }

    public void setLista(List<Uczestnicy> lista) {
        this.lista = lista;
    }
     
     
}
