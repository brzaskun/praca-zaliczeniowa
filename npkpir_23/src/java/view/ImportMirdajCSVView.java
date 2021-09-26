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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ImportMirdajCSVView  implements Serializable {
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
            if (extension.equals("csv")) {
                String filename = uploadedFile.getFileName();
                pobraneplikibytes = uploadedFile.getContent();
                getListafaktur();
                //plikinterpaper = uploadedFile.getContent();
                Msg.msg("Sukces. Plik csv " + filename + " został skutecznie załadowany");
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
            InputStream is = new ByteArrayInputStream(pobraneplikibytes);
            int i =1;
                Iterable<CSVRecord> recordss = CSVFormat.newFormat(';').withQuote('"').withFirstRecordAsHeader().parse(new InputStreamReader(is, Charset.forName("windows-1250")));
            for (CSVRecord row : recordss) {
                    String data = Data.zmienkolejnosc(row.get("Data wystawienia"));
                    InterpaperXLS interpaperXLS = new InterpaperXLS();
                    interpaperXLS.setNrfaktury(row.get("Dokument"));
                    interpaperXLS.setDatawystawienia(Data.stringToDate(data));
                    interpaperXLS.setDatasprzedaży(Data.stringToDate(Data.zmienkolejnosc(row.get("Data sprzedaży"))));
                    interpaperXLS.setKontrahent(pobierzkontrahenta(row.get("Kontrahent")));
                    interpaperXLS.setNip(pobierznip(row.get("NIP")));
                    //interpaperXLS.setKlient(ustawkontrahenta(interpaperXLS, k, klienciDAO, znalezieni));
                    interpaperXLS.setWalutaplatnosci("PLN");
                    interpaperXLS.setNettowaluta(zamiennakwote(row.get("Netto")));
                    interpaperXLS.setVatwaluta(zamiennakwote(row.get("VAT")));
                    interpaperXLS.setBruttowaluta(Z.z(interpaperXLS.getNettowaluta()+interpaperXLS.getVatwaluta()));
                    interpaperXLS.setNettoPLN(zamiennakwote(row.get("Netto")));
                    interpaperXLS.setVatPLN(zamiennakwote(row.get("VAT")));
                    interpaperXLS.setBruttoPLN(Z.z(interpaperXLS.getNettowaluta()+interpaperXLS.getVatwaluta()));
                    interpaperXLS.setEvewidencja(evewidencja23);
//                    if (zamiennakwote(row.get("W. netto 22 %"))>0.0) {
//                        interpaperXLS.setEvewidencja(evewidencja23);
//                    } else {
//                        interpaperXLS.setEvewidencja(evewidencja8);
//                    }
                    interpaperXLS.setNr(i);
                    i++;
                    dokumenty.add(interpaperXLS);
                 }
            is.close();
        }
        catch (Exception e) {
            E.e(e);
        }
    }
    
     
    
    
    
        
    private static double zamiennakwote(String pobrana) {
        double zwrot = 0.0;
        String kwota = pobrana;
        kwota = kwota.replace(",", "");
        kwota = kwota.replace(" ", "");
        zwrot = Z.z(Double.parseDouble(kwota));
        return zwrot;
    }
    
    private static String pobierzkontrahenta(String nazwa) {
        String zwrot = "Błąd w nazwie kontrahenta";
        try {
            zwrot = nazwa;
        } catch (Exception e) {}
        return zwrot;
    }
    
private static String pobierznip(String nazwa) {
        String zwrot = "Błąd w NIP kontrahenta";
        try {
            zwrot = nazwa.replace("-", "");
        } catch (Exception e) {}
        return zwrot;
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

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    
    
 
    
}
