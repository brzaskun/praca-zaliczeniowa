/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import dao.KlientJPKDAO;
import entity.KlientJPK;
import error.E;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class AmazonImportNazwy implements Serializable {
    private static final long serialVersionUID = 1L;
   @Inject
    private WpisView wpisView;
   @Inject
   private KlientJPKDAO klientJPKDAO;
   private List<KlientJPK> lista;
   private HashMap<String, String> id_nazwa;
   
   public void init() {
       lista = klientJPKDAO.findbyKlientRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
   }
    
   public void importujsprzedaz(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
             try {
            InputStream is = uploadedFile.getInputstream();
            Iterable<CSVRecord> recordss = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(new InputStreamReader(is, Charset.forName("UTF-8")));
            id_nazwa = new HashMap<>();
            for (CSVRecord row : recordss) {
                String nip = row.get("BUYER_VAT_NUMBER");
                if (nip!=null&&!nip.equals("")) {
                    String id = row.get("ORIGINAL_INVOICE_NUMBER");
                    String nazwa = row.get("BUYER_NAME");
                    id_nazwa.put(id, nazwa);
                }
                
            }
        } catch (Exception ex) {
            E.e(ex);
        }
            //dokumenty = stworzdokumenty(amazonCSV);
            Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
        }
        PrimeFaces.current().executeScript("PF('dialogAjaxCzekaj').hide()");
    }
   
   public void zaksieguj() {
       try {
           for (KlientJPK p : lista) {
               if (id_nazwa.containsKey(p.getSerial())) {
                   p.setNazwaKontrahenta(id_nazwa.get(p.getSerial()));
               }
           }
           klientJPKDAO.editList(lista);
           Msg.msg("Zachowano nazwy");
       } catch (Exception e){}
   }
   
    public List<KlientJPK> getLista() {
        return lista;
    }

    public void setLista(List<KlientJPK> lista) {
        this.lista = lista;
    }
   
    public static void main(String[] args) {
        HashMap<String, String> id_nazwa = new HashMap<>();
        try {
            String filename = "D://aba.xlsx";
            FileInputStream file = new FileInputStream(new File(filename));
            Iterable<CSVRecord> recordss = CSVFormat.EXCEL.withFirstRecordAsHeader().parse(new InputStreamReader(file));
            for (CSVRecord row : recordss) {
                String nip = row.get("BUYER_VAT_NUMBER");
                if (nip!=null&&!nip.equals("")) {
                    String id = row.get("ORIGINAL_INVOICE_NUMBER");
                    String nazwa = row.get("BUYER_NAME");
                    id_nazwa.put(id, nazwa);
                }
                
            }
        } catch (Exception e) {
            
        }
        System.out.println("koniec");
    }
}
