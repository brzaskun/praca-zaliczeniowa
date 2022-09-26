/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Uczestnicy;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import msg.Msg;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
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
                //getListafaktur();
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
//     
//     public void getListafaktur() {
//         try {
//            InputStream file = new ByteArrayInputStream(pobraneplikibytes);
//             //Create Workbook instance holding reference to .xlsx file
//            Workbook workbook = WorkbookFactory.create(file);
//             //Get first/desired sheet from the workbook
//            Sheet sheet = workbook.getSheetAt(0);
//             //Iterate through each rows one by one
//            Iterator<Row> rowIterator = sheet.iterator();
//            int i =1;
//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//                if (row.getCell(0).getRowIndex()>0) {
//                    String text = row.getCell(1).getStringCellValue();
//                    int textl = text.length();
//                    String nrfakt = text.substring(0, textl-10);
//                    String data = text.substring(textl-10,textl);
//                    Uczestnicy uczestnik = new Uczestnicy();
//                    uczestnik.setEmail(data);
////                    InterpaperXLS interpaperXLS = new InterpaperXLS();
////                    interpaperXLS.setNrfaktury(nrfakt);
////                    interpaperXLS.setDatawystawienia(Data.stringToDate(data));
////                    interpaperXLS.setDatasprzedaży(Data.stringToDate(data));
////                    interpaperXLS.setKontrahent(pobierzkontrahenta(row.getCell(3)));
////                    String nip = null;
////                    interpaperXLS.setNip(nip);
////                    String waluta = row.getCell(23).getStringCellValue();
////                    waluta = waluta.substring(0, 3);
////                    //interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni));
////                    interpaperXLS.setWalutaplatnosci(waluta);
////                    interpaperXLS.setNettowaluta(zamiennakwote(row.getCell(19)));
////                    interpaperXLS.setVatwaluta(zamiennakwote(row.getCell(20)));
////                    interpaperXLS.setBruttowaluta(Z.z(interpaperXLS.getNettowaluta()+interpaperXLS.getVatwaluta()));
////                    interpaperXLS.setNettoPLN(zamiennakwote(row.getCell(19)));
////                    interpaperXLS.setVatPLN(zamiennakwote(row.getCell(20)));
////                    interpaperXLS.setBruttoPLN(Z.z(interpaperXLS.getNettoPLN()+interpaperXLS.getVatPLN()));
////                    if (zamiennakwote(row.getCell(6))>0) {
////                        interpaperXLS.setEvewidencja(evewidencja23);
////                    } else {
////                        interpaperXLS.setEvewidencja(evewidencja8);
////                    }
////                    interpaperXLS.setNr(i);
//                    i++;
//                    dokumenty.add(interpaperXLS);
//                 }
//            }
//            file.close();
//        }
//        catch (Exception e) {
//            E.e(e);
//        }
//    }
}
