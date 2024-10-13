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
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.joda.time.DateTime;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.file.UploadedFile;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class AmazonTaxually implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private KlientJPKDAO klientJPKDAO;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    private List<KlientJPK> lista;
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

    private List<KlientJPK> pobierzJPK(UploadedFile uploadedFile) {
        List<KlientJPK> zwrot = Collections.synchronizedList(new ArrayList<>());
        KlientJPK tmpzwrot = null;
        Evewidencja rejestrWDT = evewidencjaDAO.znajdzponazwie("rejestr WDT");
        Evewidencja rejestrWNT = evewidencjaDAO.znajdzponazwie("rejestr WNT");
//        String line = "";
//        String cvsSplitBy = ",";
        try {
            String filename = uploadedFile.getFileName();
            InputStream is = uploadedFile.getInputStream();
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheet("Tax return detail");
            Map<String, Integer> naglowki = pobierznaglowki(sheet);
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 0;
            id_nazwa = new HashMap<>();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (i > 2) {
                    String rodzajtransakcji = row.getCell(naglowki.get("Transaction type")).getStringCellValue();
                    if (rodzajtransakcji.contains("Movement of own goods")) {
                        tmpzwrot = tworzobiektAmazonNowy(row, naglowki, rodzajtransakcji);
                        if (tmpzwrot != null) {
                            tmpzwrot.setPodatnik(wpisView.getPodatnikObiekt());
                            tmpzwrot.setEwidencjaVAT(tworzewidencjeVAT(rejestrWDT, rejestrWNT, tmpzwrot));
                            zwrot.add(tmpzwrot);
                        }
                    }
                }
                i++;
            }
        } catch (Exception ex) {
            E.e(ex);
        }
        return zwrot;
    }

    private KlientJPK tworzobiektAmazonNowy(Row row, Map<String, Integer> naglowki, String rodzajtransakcji) {
        KlientJPK klientJPK = new KlientJPK();
        String nipue = "PL" + wpisView.getPodatnikObiekt().getNip();
        String serial = row.getCell(naglowki.get("Transaction ID")).getStringCellValue();
        try {
            klientJPK.setDowodSprzedazy(rodzajtransakcji);
            klientJPK.setSerial(serial);
            String dataa = xls.X.xData(row.getCell(naglowki.get("Transaction date")));
            klientJPK.setDataSprzedazy(dataa);
            klientJPK.setDataWystawienia(dataa);
            boolean nabycie = rodzajtransakcji.equals("Movement of own goods (in)") ? true : false;
            String krajcdocelowy = xls.X.xString(row.getCell(naglowki.get("Tax code")));
            krajcdocelowy = krajcdocelowy.substring(krajcdocelowy.length() - 2);
            String krajnadania = xls.X.xString(row.getCell(naglowki.get("Tax code")));
            krajnadania = krajnadania.substring(krajnadania.length() - 4, krajnadania.length() - 2);
            klientJPK.setJurysdykcja(krajcdocelowy);
            String nipwysylki = "PL8513251349";
            String nipodbioru = "PL8513251349";
            boolean czypolska = krajnadania.equals("PL")||krajcdocelowy.equals("PL");
            String nipprzeciwstawny = pobierznip(krajcdocelowy,krajnadania);
            if (nabycie == false) {
                klientJPK.setWnt(false);
                klientJPK.setWdt(true);
                klientJPK.setStawkavat(0.0);
                klientJPK.setNrKontrahenta(nipprzeciwstawny);
            } else {
                klientJPK.setWnt(true);
                klientJPK.setWdt(false);
                klientJPK.setNrKontrahenta(nipprzeciwstawny);
            }
            if (czypolska&&(nipwysylki.equals(nipue) || nipodbioru.equals(nipue))) {
                klientJPK.setKodKrajuNadania(krajcdocelowy);
                klientJPK.setKodKrajuDoreczenia(krajcdocelowy);
                klientJPK.setNazwaKontrahenta(wpisView.getPrintNazwa());
                klientJPK.setRok(wpisView.getRokWpisuSt());
                klientJPK.setMc(wpisView.getMiesiacWpisu());
                klientJPK.setAmazontax0additional1(1);
                String waluta = "EUR";
                if (nabycie&&krajnadania.equals("CZ")) {
                    waluta = "CZK";
                }
                if (nabycie==false&&krajcdocelowy.equals("CZ")) {
                    waluta = "CZK";
                }
                klientJPK.setWaluta(waluta);
                String kraj = nabycie?krajcdocelowy:krajnadania;
                double netto = pobierznetto(kraj, row, nabycie, naglowki);
                klientJPK.setNettowaluta(netto);
                double vat = pobierzvat(kraj, row, nabycie, naglowki);
                klientJPK.setVatwaluta(vat);
                double stawkavat = 0.0;
                klientJPK.setStawkavat(Z.z(vat/netto));
                double kurs = pobierzkurs(klientJPK.getDataSprzedazy(), waluta);
                klientJPK.setKurs(kurs);
                klientJPK.setNetto(Z.z(netto * kurs));
                if (klientJPK.isWnt()) {
                    klientJPK.setVat(Z.z(vat * kurs));
                }
            } else {
                klientJPK = null;
            }
        } catch (Exception e) {
        }
         naglowki.forEach((header, index) -> System.out.println(header + " : " + index));
        return klientJPK;
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

    public void zaksiegujWDTjpk() {
        klientJPKDAO.deleteByPodRokMcAmazon(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), 1);
        List<KlientJPK> amazontaxreport = klientJPKDAO.findbyKlientRokMcAmazon(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), 0);
        int duplikaty = 0;
        if (amazontaxreport == null || amazontaxreport.isEmpty()) {
            Msg.msg("e", "Nalezy wcześniej zaksięgować pozycje z Amazon Tax Report!");
        } else {
            for (KlientJPK p : amazontaxreport) {
                for (Iterator it = lista.iterator(); it.hasNext();) {
                    KlientJPK r = (KlientJPK) it.next();
                    if (r.getSerial().contains(p.getSerial())) {
                        duplikaty++;
                        it.remove();
                        break;
                    }
                }
            }
            if (!lista.isEmpty()) {
                klientJPKDAO.createList(lista);
                Msg.msg("Zaksięgowano dokumenty dla JPK");
            }
            if (duplikaty > 0) {
                Msg.msg("Znaleziono duplikaty w ilości: " + duplikaty);
            }
        }
    }

    public List<KlientJPK> getLista() {
        return lista;
    }

    public void setLista(List<KlientJPK> lista) {
        this.lista = lista;
    }

    public void usun(KlientJPK item) {
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
        Map<String, Integer> headers = new HashMap<>();
        Row row1 = sheet.getRow(1); // Zero-based index
        Row row2 = sheet.getRow(2);

        if (row1 == null || row2 == null) {
            throw new IllegalArgumentException("Invalid header rows specified");
        }

        if (row1 == null || row2 == null) {
                throw new IllegalArgumentException("Invalid header rows specified");
            }

            int maxColumns = 30;
             for (int i = 0; i < maxColumns; i++) {
                String header = "";
                if (i < 12) {
                    Cell cell1 = row1.getCell(i);
                    if (cell1 != null && cell1.getCellType() == CellType.STRING && !cell1.getStringCellValue().isEmpty()) {
                        header = cell1.getStringCellValue();
                    }
                } else {
                    Cell cell2 = row2.getCell(i);
                    if (cell2 != null && cell2.getCellType() == CellType.STRING && !cell2.getStringCellValue().isEmpty()) {
                        header = cell2.getStringCellValue();
                    }
                }

                if (!header.isEmpty()) {
                    headers.put(header, i);
                }
            }
        return headers;
    }

    private double pobierznetto(String kraj, Row row, boolean nabycie, Map<String, Integer> naglowki) {
        double zwrot = 0.0;
        switch (kraj) {
            case "CZ":
                if(nabycie) {
                    zwrot = xls.X.xKwota(row.getCell(naglowki.get("3 (NET)")));
                } else {
                    zwrot = xls.X.xKwota(row.getCell(naglowki.get("20 (NET)")));
                }
                break;
            case "ES":
                if(nabycie) {
                    zwrot = xls.X.xKwota(row.getCell(naglowki.get("10 (NET)")));
                } else {
                    zwrot = xls.X.xKwota(row.getCell(naglowki.get("124 (NET)")));
                }
                break;
            case "IT":
                if(nabycie) {
                    zwrot = xls.X.xKwota(row.getCell(naglowki.get("VP3 (NET)")));
                } else {
                    zwrot = xls.X.xKwota(row.getCell(naglowki.get("VP2 (NET)")));
                }
                break;
            case "FR":
                if(nabycie) {
                    zwrot = xls.X.xKwota(row.getCell(naglowki.get("B2 (NET)")));
                } else {
                    zwrot = xls.X.xKwota(row.getCell(naglowki.get("F2 (NET)")));
                }
                break;
        }
        return zwrot;
    }

    private double pobierzvat(String kraj, Row row, boolean nabycie, Map<String, Integer> naglowki) {
        double zwrot = 0.0;
        switch (kraj) {
            case "CZ":
                if(nabycie) {
                    zwrot = xls.X.xKwota(row.getCell(naglowki.get("3 (VAT)")));
                }
                break;
            case "ES":
                if(nabycie) {
                    zwrot = xls.X.xKwota(row.getCell(naglowki.get("11 (VAT)")));
                }
                break;
            case "IT":
                if(nabycie) {
                    zwrot = xls.X.xKwota(row.getCell(naglowki.get("VP4 (VAT)")));
                }
                break;
            case "FR":
                if(nabycie) {
                    zwrot = xls.X.xKwota(row.getCell(naglowki.get("20 (VAT TOTAL)")));
                }
                break;
        }
        return zwrot;
    }

    private String pobierznip(String krajcdocelowy, String krajnadania) {
        String zwrot = "zły kraj";
        if (krajcdocelowy.equals("CZ")||krajnadania.equals("CZ")) {
            zwrot = "nip czeski";
        } else if (krajcdocelowy.equals("ES")||krajnadania.equals("ES")) {
            zwrot = "nip hiszpanski";
        } else if (krajcdocelowy.equals("IT")||krajnadania.equals("IT")) {
            zwrot = "nip włoski";
        } else if (krajcdocelowy.equals("FR")||krajnadania.equals("FR")) {
            zwrot = "nip francuski";
        }
        return zwrot;
    }
}
