/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.KlienciDAO;
import embeddable.PanstwaMap;
import entity.Klienci;
import error.E;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class KontrahentImportView implements Serializable {

    private static final long serialVersionUID = 1L;
 
    @Inject
    private WpisView wpisView;
    @Inject
    private KlienciDAO klienciDAO;
    private byte[] pobraneplikibytes;
    private List<Klienci> lista;
     
        public void zachowajplik(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String extension = FilenameUtils.getExtension(uploadedFile.getFileName()).toLowerCase();
            if (extension.equals("xls")||extension.equals("xlsx")) {
                String filename = uploadedFile.getFileName();
                pobraneplikibytes = uploadedFile.getContent();
                getListafaktur();
                //plikinterpaper = uploadedFile.getContent();
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
            lista = new ArrayList<>();
            List<Klienci> obecniklienci = klienciDAO.findAll();
            List<String> niplist = obecniklienci.stream().map(p->p.getNip()).collect(Collectors.toList());
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
                if (row.getCell(1)!=null&&row.getCell(1).getRowIndex()>1) {
                    Klienci nowy = new Klienci();
                    nowy.setNpelna(row.getCell(1).getStringCellValue());
                    nowy.setNskrocona(row.getCell(1).getStringCellValue());
                    String kodpocztowy = row.getCell(2).getStringCellValue();
                    if (kodpocztowy!=null||!kodpocztowy.equals("-")) {
                        nowy.setKodpocztowy(kodpocztowy);
                        nowy.setMiejscowosc(row.getCell(3).getStringCellValue());
                        nowy.setUlica(row.getCell(4).getStringCellValue());
                        nowy.setDom(row.getCell(5).getStringCellValue());
                        String lokal = row.getCell(6)==null?"":row.getCell(6).getStringCellValue();
                        nowy.setLokal(lokal);
                        String kraj = row.getCell(7)==null?"":row.getCell(7).getStringCellValue();
                        String nipwstepny = row.getCell(8)==null?"":row.getCell(8).getStringCellValue();
                        nipwstepny = nipwstepny.replace("-", "");
                        String nip = kraj+nipwstepny;
                        if (kraj.equals("PL")) {
                            nip = nipwstepny;
                        }
                        nowy.setNip(nip);
                        if (kraj!=null&&!kraj.equals("")) {
                            nowy.setKrajkod(kraj);
                            nowy.setKrajnazwa(PanstwaMap.getWykazPanstwXS().get(kraj));
                        }
                        if (!nip.equals("") && !niplist.contains(nip) && nip.length()>3) {
                            lista.add(nowy);
                        } else if (niplist.contains(nip)) {
                            System.out.println("duplikat "+nowy.getNpelna());
                        }
                    }
                    i++;
                    //dokumenty.add(interpaperXLS);
                 }
            }
            file.close();
        }
        catch (Exception e) {
            E.e(e);
            Msg.msg("e",E.e(e));
        }
    }
     
     public void zapiszKontrahentow() {
         if (lista!=null) {
             for (Klienci k : lista) {
                 try {
                     klienciDAO.create(k);
                 } catch (Exception e) {}
             }
             Msg.msg("Zachowano klientów");
         } else {
             Msg.msg("e","Lista jest pusta");
         }
     }
 
    public byte[] getPobraneplikibytes() {
        return pobraneplikibytes;
    }

    public void setPobraneplikibytes(byte[] pobraneplikibytes) {
        this.pobraneplikibytes = pobraneplikibytes;
    }

    public List<Klienci> getLista() {
        return lista;
    }

    public void setLista(List<Klienci> lista) {
        this.lista = lista;
    }
    
    
     
}
