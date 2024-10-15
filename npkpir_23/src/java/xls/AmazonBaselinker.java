/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import beansFK.TabelaNBPBean;
import dao.EvewidencjaDAO;
import dao.KlientJPKDAO;
import dao.TabelanbpDAO;
import embeddable.AmazonBaselinkerRecord;
import entity.EVatwpisKJPK;
import entity.Evewidencja;
import entity.KlientJPK;
import entityfk.Tabelanbp;
import error.E;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.joda.time.DateTime;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import pdf.PdfBaselinker;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class AmazonBaselinker implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private KlientJPKDAO klientJPKDAO;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    private List<AmazonBaselinkerRecord> lista;
    private HashMap<String, String> id_nazwa;

    public void init() {
        lista = new ArrayList<>();
    }

    public void importujsprzedaz(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
            lista = pobierzJPK(uploadedFile);
            //dokumenty = stworzdokumenty(amazonCSV);
            Msg.msg("Sukces. Plik " + filename + " został skutecznie załadowany");
        } catch (Exception ex) {
            E.e(ex);
            Msg.msg("e", "Wystąpił błąd. Nie udało się załadowanać pliku");
        }
        PrimeFaces.current().executeScript("PF('dialogAjaxCzekaj').hide()");
    }

    // Modified method pobierzJPK
    private List<AmazonBaselinkerRecord> pobierzJPK(UploadedFile uploadedFile) {
        List<AmazonBaselinkerRecord> zwrot = Collections.synchronizedList(new ArrayList<>());
        AmazonBaselinkerRecord tmpzwrot = null;
        try {
            String filename = uploadedFile.getFileName();
            InputStream is = uploadedFile.getInputStream();
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheetAt(0);
            Map<String, Integer> naglowki = pobierznaglowki(sheet);
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 0;
            id_nazwa = new HashMap<>();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (i > 1) {
                    tmpzwrot = tworzobiektAmazonNowy(row, naglowki);
                    if (tmpzwrot != null) {
                        zwrot.add(tmpzwrot);
                    }
                }
                i++;
            }
        } catch (Exception ex) {
            E.e(ex);
        }
        return zwrot;
    }

    private AmazonBaselinkerRecord tworzobiektAmazonNowy(Row row, Map<String, Integer> naglowki) {
        AmazonBaselinkerRecord record = null;
         try {
            record = new AmazonBaselinkerRecord();
            record.setId((int) xls.X.xKwota(row.getCell(naglowki.get("Lp."))));
            record.setTyp(row.getCell(naglowki.get("Typ")).getStringCellValue());
            record.setPelnyNumer(row.getCell(naglowki.get("Pełny numer")).getStringCellValue());
            record.setNabywca(row.getCell(naglowki.get("Nabywca")).getStringCellValue());
            record.setAdres(row.getCell(naglowki.get("Adres")).getStringCellValue());
            record.setKodPocztowy(row.getCell(naglowki.get("Kod pocztowy")).getStringCellValue());
            record.setMiasto(row.getCell(naglowki.get("Miasto")).getStringCellValue());
            record.setNip(row.getCell(naglowki.get("NIP")).getStringCellValue());
            record.setDataWystawienia(row.getCell(naglowki.get("Data wystawienia")).getStringCellValue());
            record.setDataSprzedazy(row.getCell(naglowki.get("Data sprzedaży")).getStringCellValue());
            record.setNetto(xls.X.xKwota(row.getCell(naglowki.get("Netto"))));
            record.setVat(xls.X.xKwota(row.getCell(naglowki.get("VAT"))));
            record.setKwotaVat(xls.X.xKwota(row.getCell(naglowki.get("Kwota VAT"))));
            record.setBrutto(xls.X.xKwota(row.getCell(naglowki.get("Brutto"))));
            record.setSposobPlatnosci(row.getCell(naglowki.get("Sposób płatności")).getStringCellValue());
            record.setWaluta(row.getCell(naglowki.get("Waluta")).getStringCellValue());
            record.setKursWaluty(row.getCell(naglowki.get("Kurs waluty")).getStringCellValue());
            record.setKraj(row.getCell(naglowki.get("Kraj")).getStringCellValue());
            record.setDokumentPowiazany(row.getCell(naglowki.get("Dokument powiązany")).getStringCellValue());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return record;
    }

    private List<EVatwpisKJPK> tworzewidencjeVAT(Evewidencja rejestrWDT, Evewidencja rejestrWNT, KlientJPK tmpzwrot) {
        List<EVatwpisKJPK> zwrot = new ArrayList<>();
        EVatwpisKJPK nowa = new EVatwpisKJPK();
        nowa.setKlientJPK(tmpzwrot);
        if (tmpzwrot.isWdt()) {
            nowa.setEwidencja(rejestrWDT);
        } else {
            nowa.setEwidencja(rejestrWNT);
        }
        nowa.setRokEw(tmpzwrot.getRok());
        nowa.setMcEw(tmpzwrot.getMc());
        nowa.setNetto(tmpzwrot.getNetto());
        nowa.setVat(tmpzwrot.getVat());
        nowa.setEstawka(String.valueOf(tmpzwrot.getStawkavat()));
        zwrot.add(nowa);
        return zwrot;
    }

    private double pobierzkurs(String data, String waluta) {
        double zwrot = 0.0;
        DateTime dzienposzukiwany = new DateTime(data);
        //tu sie dodaje tabele do dokumentu :)
        Tabelanbp tabela = TabelaNBPBean.pobierzTabeleNBP(dzienposzukiwany, tabelanbpDAO, waluta);
        zwrot = Z.z6(tabela.getKurssredniPrzelicznik());
        return zwrot;
    }

    public void drukuj() {
        try {
            if (lista != null && !lista.isEmpty()) {
                PdfBaselinker.drukuj(lista, wpisView);
            } 
            msg.Msg.dP();
        } catch (Exception e) {
            msg.Msg.dPe();
        }
    }

    public List<AmazonBaselinkerRecord> getLista() {
        return lista;
    }

    public void setLista(List<AmazonBaselinkerRecord> lista) {
        this.lista = lista;
    }

    public void usun(AmazonBaselinkerRecord item) {
        try {
            if (item != null) {
                lista.remove(item);
                Msg.msg("Usunięto pozycje");
            }
        } catch (Exception ex) {
        }
    }

    public static void main(String[] args) {
//        HashMap<String, String> id_nazwa = new HashMap<>();
//        try {
//            String filename = "D://amaz.xlsx";
//            FileInputStream file = new FileInputStream(new File(filename));
//            Workbook workbook = WorkbookFactory.create(file);
//            Sheet sheet = workbook.getSheet("Template");
//            Iterator<Row> rowIterator = sheet.iterator();
//            int i = 0;
//            while (rowIterator.hasNext()) {
//                Row row = rowIterator.next();
//                if (i>2) {
//                    String nip = row.getCell(46)!=null? row.getCell(46).getStringCellValue():null;
//                    String id1 = row.getCell(5).getStringCellValue();
//                    if (nip!=null&&!nip.equals("")) {
//                        String id = row.getCell(5).getStringCellValue();
//                        String nazwa = row.getCell(36).getStringCellValue();
//                        id_nazwa.put(id, nazwa);
//                    }
//                }
//                i++;
//            }
//        } catch (Exception e) {
//            System.out.println(""); 
//        }
//        System.out.println("koniec");
        String str = "ESSL210GESES";
        String char4and3FromEnd = str.substring(str.length() - 4, str.length() - 2);
        System.out.println(char4and3FromEnd); // Output: "GE"
    }

    public static Map<String, Integer> pobierznaglowki(Sheet sheet) {
        Map<String, Integer> zwrot = new HashMap<>();
        Row pierwszy = sheet.getRow(0);
        for (Iterator<Cell> it = pierwszy.cellIterator(); it.hasNext();) {
            Cell cell = it.next();
            zwrot.put(cell.getStringCellValue(), cell.getColumnIndex());
        }
        return zwrot;
    }

    
}
