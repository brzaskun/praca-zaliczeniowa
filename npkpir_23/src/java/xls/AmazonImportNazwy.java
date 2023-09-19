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
import data.Data;
import entity.EVatwpisKJPK;
import entity.Evewidencja;
import entity.KlientJPK;
import entityfk.Tabelanbp;
import error.E;
import java.io.File;
import java.io.FileInputStream;
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
import org.primefaces.model.UploadedFile;
import view.WpisView;
import waluty.Z;

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
            Msg.msg("e","Wystąpił błąd. Nie udało się załadowanać pliku");
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
            InputStream is = uploadedFile.getInputstream();
                    Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheet("Template");
            Map<String,Integer> naglowki = pobierznaglowki(sheet);
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 0;
            id_nazwa = new HashMap<>();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (i>2) {
                String rodzajtransakcji = row.getCell(naglowki.get("TRANSACTION_TYPE")).getStringCellValue();
                String jurysdykcja = row.getCell(naglowki.get("TAXABLE_JURISDICTION")).getStringCellValue();
                if (jurysdykcja.equals("PL")) {
                    if (rodzajtransakcji.equals("SALE")||rodzajtransakcji.equals("REFUND")) {
                        tmpzwrot = tworzobiektAmazonNowy(row, naglowki);
                        if (tmpzwrot!=null) {
                            tmpzwrot.setPodatnik(wpisView.getPodatnikObiekt());
                            tmpzwrot.setEwidencjaVAT(tworzewidencjeVAT(rejestrWDT, rejestrWNT, tmpzwrot));
                            zwrot.add(tmpzwrot);
                        }
                    }
                    }
                    if (rodzajtransakcji.equals("FC_TRANSFER")) {
                        tmpzwrot = tworzobiektAmazonNowy(row, naglowki);
                        if (tmpzwrot!=null) {
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
   
    private KlientJPK tworzobiektAmazonNowy(Row row, Map<String,Integer> naglowki) {
        KlientJPK klientJPK = new KlientJPK();
        String nipue = "PL"+wpisView.getPodatnikObiekt().getNip();
        String rodzajtransakcji = row.getCell(naglowki.get("TRANSACTION_TYPE")).getStringCellValue();
        String serial = row.getCell(naglowki.get("TRANSACTION_EVENT_ID")).getStringCellValue();
        try {
            klientJPK.setDowodSprzedazy(rodzajtransakcji);
            if (serial.equals("203-1765216-3856314")) {
                System.out.println("");
            }
            klientJPK.setSerial(row.getCell(naglowki.get("TRANSACTION_EVENT_ID")).getStringCellValue());
            String data = row.getCell(naglowki.get("TRANSACTION_DEPART_DATE_UTC")).getStringCellValue();
            String data2 = row.getCell(naglowki.get("TRANSACTION_DEPART_DATE_UTC")).getStringCellValue();
            if (rodzajtransakcji.equals("REFUND")) {
               String datarefund = row.getCell(naglowki.get("TRANSACTION_COMPLETE_DATE_UTC")).getStringCellValue();
               data = datarefund;
               data2 = datarefund;
            }
            if (data2.equals("")) {
                data2 = data;
            }
            klientJPK.setDataSprzedazy(Data.zmienkolejnosc(data));
            klientJPK.setDataWystawienia(Data.zmienkolejnosc(data2));
            String krajcdocelowy = row.getCell(naglowki.get("ARRIVAL_COUNTRY")).getStringCellValue();
            String krajwysylki = row.getCell(naglowki.get("DEPARTURE_COUNTRY")).getStringCellValue();
            String stawka = row.getCell(naglowki.get("PRICE_OF_ITEMS_VAT_RATE_PERCENT")).getStringCellValue();
            double stawkavat = Double.valueOf(stawka);
            klientJPK.setStawkavat(stawkavat);
            String opodatkowanie = row.getCell(naglowki.get("TAXABLE_JURISDICTION")).getStringCellValue();
            klientJPK.setJurysdykcja(opodatkowanie);
            String nipwysylki = row.getCell(naglowki.get("SELLER_DEPART_COUNTRY_VAT_NUMBER")).getStringCellValue();
            String nipodbioru = row.getCell(naglowki.get("SELLER_ARRIVAL_COUNTRY_VAT_NUMBER")).getStringCellValue();
            String nipbuyer = row.getCell(naglowki.get("BUYER_VAT_NUMBER")).getStringCellValue();

            if (nipwysylki.equals(nipue)&&krajwysylki.equals("PL")) {
                klientJPK.setWnt(false);
                klientJPK.setWdt(true);
                klientJPK.setStawkavat(0.0);
                if (rodzajtransakcji.equals("FC_TRANSFER")) {
                    klientJPK.setNrKontrahenta(nipodbioru);
                } else {
                    klientJPK.setNrKontrahenta(nipbuyer);
                }
            } else if (nipodbioru.equals(nipue)) {
                klientJPK.setWnt(true);
                klientJPK.setWdt(false);
                klientJPK.setNrKontrahenta(nipwysylki);
            }
            if (nipwysylki.equals(nipue) || nipodbioru.equals(nipue)) {
                klientJPK.setKodKrajuNadania(krajwysylki);
                klientJPK.setKodKrajuDoreczenia(krajcdocelowy);
                if (rodzajtransakcji.equals("FC_TRANSFER")) {
                    klientJPK.setNazwaKontrahenta(wpisView.getPrintNazwa());
                } else {
                    if (row.getCell(naglowki.get("BUYER_NAME")).getStringCellValue()==null||row.getCell(naglowki.get("BUYER_NAME")).getStringCellValue().equals("")) {
                        klientJPK.setNazwaKontrahenta("brakk");
                    } else {
                        klientJPK.setNazwaKontrahenta(row.getCell(naglowki.get("BUYER_NAME")).getStringCellValue());
                    }
                }
                klientJPK.setRok(wpisView.getRokWpisuSt());
                klientJPK.setMc(wpisView.getMiesiacWpisu());
                klientJPK.setAmazontax0additional1(1);
                String waluta = row.getCell(naglowki.get("TRANSACTION_CURRENCY_CODE")).getStringCellValue();
                klientJPK.setWaluta(waluta);
                double brutto = format.F.kwota(row.getCell(naglowki.get("TOTAL_PRICE_OF_ITEMS_VAT_INCL")).getStringCellValue());
                klientJPK.setNettowaluta(format.F.kwota(row.getCell(naglowki.get("TOTAL_PRICE_OF_ITEMS_AMT_VAT_EXCL")).getStringCellValue()));
                klientJPK.setVatwaluta(Z.z(brutto - klientJPK.getNettowaluta()));
                double kurs = pobierzkurs(klientJPK.getDataSprzedazy(), waluta);
                klientJPK.setKurs(kurs);
                klientJPK.setNetto(Z.z(klientJPK.getNettowaluta() * kurs));
                if (klientJPK.isWnt()) {
                    klientJPK.setVat(Z.z(klientJPK.getVatwaluta() * kurs));
                }
                if (klientJPK.getNrKontrahenta().equals("")) {
                    klientJPK = null;
                }
            } else {
                klientJPK = null;
            }
        } catch (Exception e) {
        }
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
        klientJPKDAO.deleteByPodRokMcAmazon(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(),1);
        List<KlientJPK> amazontaxreport = klientJPKDAO.findbyKlientRokMcAmazon(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(),0);
        int duplikaty = 0;
        if (amazontaxreport==null || amazontaxreport.isEmpty()) {
            Msg.msg("e","Nalezy wcześniej zaksięgować pozycje z Amazon Tax Report!");
        } else {
            for (KlientJPK p : amazontaxreport) {
                for (Iterator it = lista.iterator();it.hasNext();) {
                    KlientJPK r =(KlientJPK) it.next();
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
            if (duplikaty>0) {
                Msg.msg("Znaleziono duplikaty w ilości: "+duplikaty);
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
            if (item!=null) {
                lista.remove(item);
                Msg.msg("Usunięto pozycje");
            }
        } catch (Exception ex){}
    }
    
    
    public static void main(String[] args) {
        HashMap<String, String> id_nazwa = new HashMap<>();
        try {
            String filename = "D://amaz.xlsx";
            FileInputStream file = new FileInputStream(new File(filename));
            Workbook workbook = WorkbookFactory.create(file);
            Sheet sheet = workbook.getSheet("Template");
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 0;
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (i>2) {
                    String nip = row.getCell(46)!=null? row.getCell(46).getStringCellValue():null;
                    String id1 = row.getCell(5).getStringCellValue();
                    if (nip!=null&&!nip.equals("")) {
                        String id = row.getCell(5).getStringCellValue();
                        String nazwa = row.getCell(36).getStringCellValue();
                        id_nazwa.put(id, nazwa);
                    }
                }
                i++;
            }
        } catch (Exception e) {
            System.out.println(""); 
        }
        System.out.println("koniec");
    }

    private Map<String, Integer> pobierznaglowki(Sheet sheet) {
        Map<String, Integer> zwrot = new HashMap<>();
        Row pierwszy = sheet.getRow(2);
        for (Iterator<Cell> it = pierwszy.cellIterator(); it.hasNext();) {
            Cell cell = it.next();
            zwrot.put(cell.getStringCellValue(), cell.getColumnIndex());
        }
        return zwrot;
    }
}
