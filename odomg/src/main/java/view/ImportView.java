/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SzkolenieWykazFacade;
import dao.UczestnicyFacade;
import dao.ZakladpracyFacade;
import entity.Szkoleniewykaz;
import entity.Uczestnicy;
import entity.Zakladpracy;
import error.E;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
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
    private Zakladpracy zakladpracy;
    private List<Zakladpracy> zakladpracylista;
    private List<Uczestnicy> listaosobyselected;
    private List<Uczestnicy> listaosobyfiltered;
    @Inject
    private ZakladpracyFacade zakladpracyFacade;
    @Inject
    private UczestnicyFacade uczestnicyFacade;
    @Inject
    private SzkolenieWykazFacade szkolenieWykazFacade;

    public ImportView() {
        lista = new ArrayList<>();
    }
    
    @PostConstruct
    private void init() {
        zakladpracylista = zakladpracyFacade.findAll();
    }
    
    
     public void zachowajplik(FileUploadEvent event) {
        if (zakladpracy!=null) {
            try {
                UploadedFile uploadedFile = event.getFile();
                String extension = FilenameUtils.getExtension(uploadedFile.getFileName()).toLowerCase();
                if (extension.equals("xls")||extension.equals("xlsx")) {
                    String filename = uploadedFile.getFileName();
                    pobraneplikibytes = uploadedFile.getContent();
                    getListafaktur();
                    sprawdzduplikaty();
                    //plikinterpaper = uploadedFile.getContents();
                    Msg.msg("Sukces. Plik xls " + filename + " został skutecznie załadowany");
                } else {
                    Msg.msg("e","Niewłaściwy typ pliku");
                }
            } catch (Exception ex) {
                E.e(ex);
                Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
            }
        } else {
            Msg.msg("e","Nie wybrano firmy. Nie można importować");
        }
    }
     
     public void getListafaktur() {
         try {
            List<Szkoleniewykaz> szkolenielista = szkolenieWykazFacade.findAll();
            List<String> szkolenianazwy = szkolenielista.stream().map(Szkoleniewykaz::getNazwa).collect(Collectors.toList());
            lista = new ArrayList<>();
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
                int numer = row.getRowNum();
                if (row.getRowNum()>0) {
                    Uczestnicy uczestnik = new Uczestnicy();
                    if (row.getCell(1)==null) {
                        uczestnik.setEmail("!brak adresu mail");
                        uczestnik.setBraki(true);
                    } else {
                        boolean dobrymail = sprawdzmail(row.getCell(1).getStringCellValue());
                        if (dobrymail) {
                            uczestnik.setEmail(row.getCell(1).getStringCellValue());
                        } else {
                            uczestnik.setEmail("!zły mail");
                            uczestnik.setBraki(true);
                        }
                    }
                    if (row.getCell(2)==null) {
                        uczestnik.setImienazwisko("!brak nazwiska i imienia");
                        uczestnik.setBraki(true);
                    } else {
                        uczestnik.setImienazwisko(row.getCell(2).getStringCellValue());
                    }
                    if (row.getCell(3)==null) {
                        uczestnik.setPlec("!brak symbolu plci");
                        uczestnik.setBraki(true);
                    } else {
                        String plec = row.getCell(3).getStringCellValue();
                        plec = plec.toLowerCase();
                        boolean dobry = plec.equals("k")||plec.equals("m");
                        if (dobry) {
                            uczestnik.setPlec(row.getCell(3).getStringCellValue());
                        } else {
                            uczestnik.setPlec("!zły symbol płci");
                            uczestnik.setBraki(true);
                        }
                    }
                    if (row.getCell(4)==null) {
                        uczestnik.setNazwaszkolenia("!brak symbolu plci");
                        uczestnik.setBraki(true);
                    } else {
                        boolean dobry = sprawdzszkolenie(row.getCell(4).getStringCellValue(), szkolenianazwy);
                        if (dobry) {
                            uczestnik.setNazwaszkolenia(row.getCell(4).getStringCellValue());
                        } else {
                            uczestnik.setNazwaszkolenia("!nie ma takiego szkolenia");
                            uczestnik.setBraki(true);
                        }
                    }
                    uczestnik.setIndetyfikator(row.getCell(5).getStringCellValue());
                    uczestnik.setNrupowaznienia(row.getCell(6).getStringCellValue());
                    uczestnik.setNrwierszaimport(numer);
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
    
    private boolean sprawdzmail(String emailAddress)  {
        boolean zwrot = false;
        if (emailAddress!=null) {
            String regex ="^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
            Pattern pattern = Pattern.compile(regex);  
            Matcher matcher = pattern.matcher(emailAddress);
            zwrot = matcher.matches();
        }
        return zwrot;
    }
     
    private void sprawdzduplikaty() {
        if (lista!=null) {
            List<Uczestnicy> pracownicyfirmy = uczestnicyFacade.findByFirmaId(zakladpracy);
            for (Uczestnicy p : lista) {
                for (Uczestnicy r : pracownicyfirmy) {
                    if (p.getEmail().equals(r.getEmail())) {
                        if (p.getNazwaszkolenia().equals(r.getNazwaszkolenia())) {
                            p.setDuplikat(true);
                        }
                    }   
                }
            }
        }
    }
    private boolean sprawdzszkolenie(String nazwaszkolenia, List<String> szkolenianazwy) {
        boolean zwrot = false;
        String ns = nazwaszkolenia.toLowerCase();
        if (nazwaszkolenia!=null && szkolenianazwy!=null) {
            if (szkolenianazwy.contains(ns)) {
                zwrot = true;
            }
        }
        return zwrot;
    }
    
    public void usun(Uczestnicy u) {
        if (u!=null) {
            lista.remove(u);
            Msg.msg("Usunięto osobę");
        } else {
            Msg.msg("Błąd. Nie usunieto osoby");
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
     
     public List<Zakladpracy> getZakladpracylista() {
        return zakladpracylista;
    }

    public void setZakladpracylista(List<Zakladpracy> zakladpracylista) {
        this.zakladpracylista = zakladpracylista;
    }

    public Zakladpracy getZakladpracy() {
        return zakladpracy;
    }

    public void setZakladpracy(Zakladpracy zakladpracy) {
        this.zakladpracy = zakladpracy;
    }

    public List<Uczestnicy> getListaosobyselected() {
        return listaosobyselected;
    }

    public void setListaosobyselected(List<Uczestnicy> listaosobyselected) {
        this.listaosobyselected = listaosobyselected;
    }

    public List<Uczestnicy> getListaosobyfiltered() {
        return listaosobyfiltered;
    }

    public void setListaosobyfiltered(List<Uczestnicy> listaosobyfiltered) {
        this.listaosobyfiltered = listaosobyfiltered;
    }

    

    
    
     
}
