/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

import dao.IntrastatwierszDAO;
import data.Data;
import embeddable.Mce;
import entity.Podatnik;
import entity.Uz;
import entityfk.Intrastatwiersz;
import error.E;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import msg.Msg;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.primefaces.PrimeFaces;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;
import pl.gov.mf.xsd.intrastat.ist.IST;
import pl.gov.mf.xsd.intrastat.ist.ObjectFactory;
import view.WpisView;
import xml.XMLValid;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class AmazonImportIntrastat implements Serializable {
   private static final long serialVersionUID = 1L;
   @Inject
   private WpisView wpisView;
   private List<Intrastatwiersz> lista;
   @Inject
   private IntrastatwierszDAO intrastatwierszDAO;
   
   
   public void init() {
       
   }
    
   public void importujsprzedaz(FileUploadEvent event) {
        try {
            UploadedFile uploadedFile = event.getFile();
            String filename = uploadedFile.getFileName();
             try {
            InputStream is = uploadedFile.getInputstream();
            Workbook workbook = WorkbookFactory.create(is);
            Sheet sheet = workbook.getSheet("Template");
            Iterator<Row> rowIterator = sheet.iterator();
            int i = 0;
            lista = new ArrayList<>();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (i>2) {
                    Intrastatwiersz w = new Intrastatwiersz(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu(), wpisView.getPodatnikObiekt());
                    String transakcja = row.getCell(3)!=null? row.getCell(3).getStringCellValue():null;
                    if (transakcja!=null&&transakcja.equals("FC_TRANSFER")) {
                        w.setNumerkolejny(row.getCell(4).getStringCellValue());
                        String quantity = row.getCell(11).getStringCellValue();
                        w.setIlosc(quantity);
                        String waga = row.getCell(12).getStringCellValue();
                        w.setMasanettokg(waga);
                        double cena = Double.valueOf(row.getCell(13).getStringCellValue());
                        w.setWartoscfaktury((int)cena);
                        String kod = row.getCell(27).getStringCellValue();
                        w.setKodtowaru(Integer.parseInt(kod));
                        w.setRodzajtransakcji(11);
                        String opis = row.getCell(59)!=null&&!row.getCell(59).getStringCellValue().equals("") ? row.getCell(59).getStringCellValue().replace("\"", ""):"towar";
                        w.setOpistowaru(opis);
                        String kraj = row.getCell(39).getStringCellValue();
                        w.setKrajprzeznaczenia(kraj);
                        String nip = row.getCell(46).getStringCellValue();
                        w.setVatuekontrahenta(nip);
                        if(!kraj.equals("PL")) {
                            lista.add(w);
                        }
                    }
                }
                i++;
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
           intrastatwierszDAO.deleteByPodRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
           intrastatwierszDAO.createList(lista);
           Msg.msg("Zachowano wiersze");
       } catch (Exception e){}
   }

   
    public IST intrastat() {
        IST ist = new IST();
        if (lista.isEmpty()) {
            Msg.msg("e", "Brak zaimportowanych wierszy nie można wygenerować Intrastat");
        } else {
            try {
                ist.setEmail(wpisView.getUzer().getEmail());
                ObjectFactory ob = new ObjectFactory();
                IST.Deklaracja deklaracja = ob.createISTDeklaracja();
                deklaracja.setData(Data.databiezaca());
                deklaracja.setLacznaLiczbaPozycji(lista.size());
                deklaracja.setLacznaWartoscFaktur(podsumuj(lista));
                deklaracja.setMiejscowosc("Szczecin");
                deklaracja.setMiesiac(Mce.getMiesiacToNumber().get(wpisView.getMiesiacWpisu()));
                deklaracja.setNrWlasny("1D"+wpisView.getRokWpisuSt()+wpisView.getMiesiacWpisu()+"W1");
                deklaracja.setNumer(1);
                deklaracja.setPodmiotZobowiazany(zrobpodmiot(wpisView));
                deklaracja.setZglaszajacy(zrobzglaszajacy(wpisView));
                deklaracja.setRodzaj("D");
                deklaracja.setRok(Short.valueOf(wpisView.getRokWpisuSt()));
                deklaracja.setTyp("W");
                deklaracja.setUC(420000);
                deklaracja.setWersja(1);
                deklaracja.setWypelniajacy(zrobwypelniajacy(wpisView));
                dodajwiersze(deklaracja, lista);
                ist.setDeklaracja(deklaracja);
                String sciezka = marszajuldoplikuxml(wpisView.getPodatnikObiekt(), ist);
            } catch (Exception e) {
                String wiad = "Wystąpił błąd. Nie wygenerowano Intrastat ";
                Msg.msg("e",wiad);
            }
        }
        return ist;
    }
    
    private String marszajuldoplikuxml(Podatnik podatnik, IST ist) {
        String sciezka = null;
        try {
            JAXBContext context = JAXBContext.newInstance(ist.getClass());
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            String mainfilename = "intrastat"+podatnik.getNip()+"mcrok"+wpisView.getMiesiacWpisu()+wpisView.getRokWpisuSt()+".xml";
            ServletContext ctx = (ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext();
            String realPath = ctx.getRealPath("/")+"resources\\xml\\";
            FileOutputStream fileStream = new FileOutputStream(new File(realPath+mainfilename));
            OutputStreamWriter writer = new OutputStreamWriter(fileStream, "UTF-8");
            marshaller.marshal(ist, writer);
            Object[] walidacja = XMLValid.walidujIntrastat(mainfilename);
            String[] zwrot = new String[2];
//            if (walidacja!=null && walidacja[0]==Boolean.TRUE) {
                zwrot[0] = mainfilename;
                zwrot[1] = "ok";
//                Msg.msg("Walidacja Intrastat pomyślna");
                Msg.msg("Zachowano Intrastat");
                String exec = "wydrukJPK('"+mainfilename+"')";
                PrimeFaces.current().executeScript(exec);
//            } else if (walidacja!=null && walidacja[0]==Boolean.FALSE){
//                zwrot[0] = mainfilename;
//                zwrot[1] = null;
//                Msg.msg("Nie zachowano Intrastat");
//                Msg.msg("e", (String) walidacja[1]);
//            }
            sciezka = mainfilename;
        } catch (Exception ex) {
            E.e(ex);
        }
        return sciezka;
    }
    
    
    private Short podsumuj(List<Intrastatwiersz> lista) {
        int suma = 0;
        for (Intrastatwiersz p : lista) {
            suma = suma +p.getWartoscfaktury();
        }
        return (short) suma;
    }
   
    
    private IST.Deklaracja.PodmiotZobowiazany zrobpodmiot(WpisView wpisView) {
        ObjectFactory ob = new ObjectFactory();
        IST.Deklaracja.PodmiotZobowiazany pod = ob.createISTDeklaracjaPodmiotZobowiazany();
        Podatnik p = wpisView.getPodatnikObiekt();
        pod.setNazwa(p.getNazwapelna());
        pod.setMiejscowosc(p.getMiejscowosc());
        pod.setKodPocztowy(p.getKodpocztowy());
        pod.setNip(Long.parseLong(p.getNip()));
        pod.setRegon(Long.parseLong(p.getRegon()+"00000"));
        pod.setUlicaNumer(p.getUlica()+" "+p.getNrdomu());
        return pod;
    }
    
    private IST.Deklaracja.Zglaszajacy zrobzglaszajacy(WpisView wpisView) {
        ObjectFactory ob = new ObjectFactory();
        IST.Deklaracja.Zglaszajacy pod = ob.createISTDeklaracjaZglaszajacy();
        Podatnik p = wpisView.getPodatnikObiekt();
        pod.setNazwa(p.getNazwapelna());
        pod.setMiejscowosc(p.getMiejscowosc());
        pod.setKodPocztowy(p.getKodpocztowy());
        pod.setNip(Long.parseLong(p.getNip()));
        pod.setRegon(Long.parseLong(p.getRegon()+"00000"));
        pod.setUlicaNumer(p.getUlica()+" "+p.getNrdomu());
        return pod;
    }

    private IST.Deklaracja.Wypelniajacy zrobwypelniajacy(WpisView wpisView) {
        ObjectFactory ob = new ObjectFactory();
        IST.Deklaracja.Wypelniajacy wyp = ob.createISTDeklaracjaWypelniajacy();
        Uz w = wpisView.getUzer();
        wyp.setNazwiskoImie(w.getImieNazwisko());
        wyp.setEmail(w.getEmail());
        wyp.setTelefon(Integer.parseInt(w.getNrtelefonu()));
        return wyp;
    }

    private void dodajwiersze(IST.Deklaracja deklaracja, List<Intrastatwiersz> lista) {
        Integer i = 1;
        for (Intrastatwiersz p : lista) {
            try {
                IST.Deklaracja.Towar tow = new IST.Deklaracja.Towar();
                tow.setIdKontrahenta(p.getVatuekontrahenta());
                Integer ilosc = Integer.parseInt(p.getIlosc());
                tow.setIloscUzupelniajacaJm(ilosc);
                tow.setKodTowarowy(p.getKodtowaru());
                tow.setKrajPrzeznaczeniaWysylki(p.getKrajprzeznaczenia());
                Integer masa = (int) Double.parseDouble(p.getMasanettokg());
                tow.setMasaNetto(masa);
                tow.setOpisTowaru(p.getOpistowaru());
                tow.setPozId(i++);
                tow.setRodzajTransakcji(p.getRodzajtransakcji());
                tow.setWartoscFaktury(p.getWartoscfaktury().shortValue());
                deklaracja.getTowar().add(tow);
            } catch (Exception e) {
                System.out.println("");
            }
        }
    }
    
    
    public List<Intrastatwiersz> getLista() {
        return lista;
    }

    public void setLista(List<Intrastatwiersz> lista) {
        this.lista = lista;
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

    

  

}
