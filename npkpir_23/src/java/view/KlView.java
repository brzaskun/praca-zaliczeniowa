
package view;

import dao.KlienciDAO;
import embeddable.PanstwaMap;
import entity.Klienci;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import org.primefaces.event.SelectEvent;
import params.Params;
import viewfk.DokfkView;
import viewfk.PlanKontCompleteView;

/**
 *
 * @author Osito
 */
@ManagedBean(name="KlView")
@ViewScoped
public class KlView implements Serializable{
    private static final long serialVersionUID = 1L;
    final static String FILE_NAME = "C:\\Temp\\dane.txt";
    final static String OUTPUT_FILE_NAME = "C:\\Temp\\outputdane.txt";
    final static Charset ENCODING = StandardCharsets.UTF_8;
    private ArrayList<Klienci> kl1;
    private ArrayList<Klienci> klienciFiltered;
    private Klienci doUsuniecia;
    private boolean edycja;
    @ManagedProperty(value = "#{dokfkView}")
    private DokfkView dokfkView;
    @ManagedProperty(value = "#{planKontCompleteView}")
    private PlanKontCompleteView planKontCompleteView;
    
   
    public static void main(String[] args) {
        String mse = "XX0000000001";
        mse = mse.substring(2);
        mse = String.valueOf(Integer.parseInt(mse));
    }
    @Inject private KlienciDAO klDAO;
    @Inject private Klienci selected;
    @Inject PanstwaMap ps1;
    private Integer ilesrodkow;

    
    @PostConstruct
    private void init(){
        kl1 = new ArrayList<>();
        kl1.addAll(klDAO.findAll());
    }
    
    public void wyszukajduplikat(ValueChangeEvent e) {
        String klient = (String) e.getNewValue();
        Klienci klientznaleziony = null;
        try {
            klientznaleziony = klDAO.findKlientByNazwa(klient);
        } catch (Exception e1) {
            
        }
        if (klientznaleziony != null) {
            selected.setNpelna("");
            selected.setNskrocona("");
            Msg.msg("e", "Klient o takiej nazwie jest już w bazie");
            RequestContext.getCurrentInstance().update("formnkfaktura");
            RequestContext.getCurrentInstance().execute("fakturaduplikatklienta()");
        }
        
    }
    
    public void wyszukajduplikatkontrahent() {
        String klient = selected.getNpelna();
        Klienci klientznaleziony = null;
        try {
            klientznaleziony = klDAO.findKlientByNazwa(klient);
        } catch (Exception e1) {
            
        }
        if (klientznaleziony != null) {
            if (edycja) {
                Msg.msg("w", "Klient o takiej nazwie jest już w bazie");
            } else {
                selected = new Klienci();
                Msg.msg("e", "Klient o takiej nazwie jest już w bazie");
                RequestContext.getCurrentInstance().execute("fakturaduplikatklientakontrahent()");
            }
        }
        
    }
  
    public void dodajKlienta(){
      try {
        if(selected.getNip().isEmpty()){
            wygenerujnip();
        }
        //Usunalem formatowanie pelnej nazwy klienta bo przeciez imie i nazwiko pisze sie wielkimi a ten zmniejszal wszystko
//        String formatka = selected.getNpelna().substring(0, 1).toUpperCase();
//        formatka = formatka.concat(selected.getNpelna().substring(1).toLowerCase());
//        selected.setNpelna(formatka);
        String formatka = selected.getNskrocona().toUpperCase();
        selected.setNskrocona(formatka);
        formatka = selected.getUlica().substring(0, 1).toUpperCase();
        formatka = formatka.concat(selected.getUlica().substring(1));
        selected.setUlica(formatka);
        try {
            selected.getKrajnazwa();
        } catch (Exception e) { E.e(e); 
            selected.setKrajnazwa("Polska");
        }
        String kraj = selected.getKrajnazwa();
        String symbol = ps1.getWykazPanstwSX().get(kraj);
        selected.setKrajkod(symbol);
        poszukajDuplikatNip();
        poszukajDuplikatNazwa();
        klDAO.dodaj(selected);
        kl1 = new ArrayList<>();
        kl1.addAll(klDAO.findAll());
        RequestContext.getCurrentInstance().update("formX:");
        RequestContext.getCurrentInstance().update("formY:tabelaKontr");
        Msg.msg("i","Dodano nowego klienta"+selected.getNpelna(),"formX:mess_add");
        } catch (Exception e) { E.e(e); 
        Msg.msg("e","Nie dodano nowego klienta. Klient o takim Nip/Nazwie pełnej juz istnieje","formX:mess_add");
        }
         
         
   }
    
       
    public void dodajKlientafk(){
      try {
        if(selected.getNip().isEmpty()){
            wygenerujnip();
        }
        String formatka = selected.getNskrocona().toUpperCase();
        selected.setNskrocona(formatka);
        try {
            selected.getKrajnazwa();
        } catch (Exception e) { E.e(e); 
            selected.setKrajnazwa("Polska");
        }
        String kraj = selected.getKrajnazwa();
        String symbol = ps1.getWykazPanstwSX().get(kraj);
        selected.setKrajkod(symbol);
        poszukajDuplikatNip();
        poszukajDuplikatNazwa();
        klDAO.dodaj(selected);
        kl1.add(selected);
        planKontCompleteView.init();
        Msg.msg("i","Dodano nowego klienta"+selected.getNpelna());
       
        } catch (Exception e) { E.e(e); 
            Msg.msg("e","Nie dodano nowego klienta. Klient o takim Nip/Nazwie pełnej juz istnieje");
        }
      //jeżeli funkcja jest wywolana z wpisywania dokumnetu to zerujemy pola
        if (dokfkView.getSelected() != null) {
            dokfkView.getSelected().setKontr(selected);
            RequestContext.getCurrentInstance().update("formwpisdokument:acForce");
            RequestContext.getCurrentInstance().update("formXNowyKlient:polawprowadzania");
            RequestContext.getCurrentInstance().update("formXNowyKlient:polawprowadzania1");
        } else {
            selected = new Klienci();
        }
        if (dokfkView.getEwidencjaVatRK() != null) {
            dokfkView.getEwidencjaVatRK().setKlient(selected);
            RequestContext.getCurrentInstance().update("ewidencjavatRK:klientRK");
        }
        selected = new Klienci();
   }
    
    
    
     public void pobierzklientazPliku() throws IOException{
          readLargerTextFile(FILE_NAME);
    
   }
    
    public void readLargerTextFile(String aFileName) throws IOException {
    Path path = Paths.get(aFileName);
    try (Scanner scanner =  new Scanner(path, "Windows-1250")){
      int i = 0;
      while (scanner.hasNextLine()){
        String tmp = String.valueOf(scanner.nextLine());
        while(i<28){
        if (tmp.contains("bnazwa")){
            while (!tmp.contains("Kontrahent")) {
                tmp = String.valueOf(scanner.nextLine());
            }
            i=27;
            break;
        } else if(tmp.contains("nazwa")){
            i++;
            tmp = tmp.replace("\"","");
            tmp = tmp.replace("'","");
            selected.setNpelna(tmp.substring(8));
            break;
        }  else if (tmp.contains("miejscowosc")) {
            i++;
            selected.setMiejscowosc(tmp.substring(14));
            break;
        } else if (tmp.contains("ulica")) {
            i++;
            selected.setUlica(tmp.substring(8));
            break;
        } else if (tmp.contains("dom")) {
            i++;
            selected.setDom(tmp.substring(6));
            break;
        }else if (tmp.contains("lokal")) {
            i++;
            selected.setLokal(tmp.substring(8));
            break;
        }else if (tmp.contains("kodPocztowy")) {
            i++;
            selected.setKodpocztowy(tmp.substring(14));
            break;
        }else if(tmp.contains("kod")){
            i++;
            tmp = tmp.replace("\"","");
            tmp = tmp.replace("'","");
            selected.setNskrocona(tmp.substring(6));
            break;
        }else if (tmp.contains("NIP")) {    
            i++;
            tmp = tmp.replace("-","");
            selected.setNip(tmp.substring(6));
            break;
        }else if (tmp.contains("pesel")) {
            i++;
            selected.setPesel(tmp.substring(8));
            break;
        }else if (tmp.contains("krajKod")) {
            i++;
            selected.setKrajkod(tmp.substring(10));
            break;
        }else if (tmp.contains("krajNazwa")) {
            i++;
            selected.setKrajnazwa(tmp.substring(12));
            break;
        }else {
            i++;
            break;
        }
        }
          if (i == 27) {
              Klienci knazwa = null;
              try {
                  knazwa = klDAO.findKlientByNazwa(selected.getNpelna());
              } catch (Exception e1) {
                  
              }
              Klienci knip = null;
              try {
                  knip = klDAO.findKlientByNip(selected.getNip());
              } catch (Exception e1) {
                  
              }
              try {
                  if (knazwa==null && knip == null) {
                    klDAO.dodaj(selected);
                  System.out.println("import "+selected.toString2());
                  }
              } catch (Exception es) {
              }
              i = 0;
          }
      }
      Msg.msg("Skonczylem import");
    }
  }
  
   public List<Klienci> complete(String query) {  
        List<Klienci> results = new ArrayList<>();
        try{
            String q = query.substring(0,1);
            int i = Integer.parseInt(q);
            for(Klienci p : kl1) {  
             if(p.getNip().startsWith(query)) {
                 results.add(p);
             }
            }
        } catch (NumberFormatException e){
            for(Klienci p : kl1) {
            if(p.getNpelna().toLowerCase().contains(query.toLowerCase())) {
                 results.add(p);
             }
            }
        }  
        results.add(new Klienci("nowy klient", "nowy klient", "0123456789", "11-111", "miejscowosc", "ulica", "1", "1", "ewidencja", "kolumna"));
        return results;  
    }  
 
     public void edit() {
        try {
            //sformatuj();
            klDAO.edit(selected);
            edycja = false;
            //refresh();
            kl1 = new ArrayList<>();
            kl1.addAll(klDAO.findAll());
            FacesMessage msg = new FacesMessage("Zapisano zmienione dane klienta",  selected.getNpelna());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) { E.e(e); 
            FacesMessage msg = new FacesMessage("Wystąpił błąd. Nie zapisano zmienionych danych klienta", e.getStackTrace().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        }
    }
     
    public void wybranodoedycji(SelectEvent ex) {
        edycja = true;
        Klienci wybrany = (Klienci) ex.getObject();
        Msg.msg("Wybrano klienta do edycji: "+wybrany.getNpelna());
    }

      public void destroy(Klienci selDok) {
          doUsuniecia = new Klienci();
          doUsuniecia = selDok;
          
    }
      
         public void destroy2() {
        try {
            klDAO.destroy(doUsuniecia);
            kl1.remove(doUsuniecia);
            klienciFiltered.remove(doUsuniecia);
            RequestContext.getCurrentInstance().update("formY:");
            Msg.msg("i","Usunięto wskazanego klienta","formX:mess_add");
        } catch (Exception e) { E.e(e); 
            Msg.msg("i","Nie można usunąć klienta. Nazwa klienta występuje w zaksiękowancyh dokumentach.");
        }
      
    }
   
    public void dodajpustegomaila(){
        selected.setEmail("niema@maila.pl");
        RequestContext.getCurrentInstance().update("formX:emailpole");
    }
    
    private void poszukajDuplikatNip() throws Exception {
         String nippoczatkowy = selected.getNip();
         if(!nippoczatkowy.equals("0000000000")){
         List<String> kliencitmp  = klDAO.findNIP();
         if (!kliencitmp.isEmpty()) {
             if (kliencitmp.contains(nippoczatkowy)) {
                 throw new Exception();
             }
         }
         }
    }
    
     public void poszukajDuplikatNipWTrakcie() throws Exception {
         String nippoczatkowy = (String) Params.params("formX:nipPole");
         if(!nippoczatkowy.equals("0000000000") && !nippoczatkowy.equals("")){
         List<String> kliencitmp  = klDAO.findNIP();
         if (!kliencitmp.isEmpty()) {
             if (kliencitmp.contains(nippoczatkowy)) {
                 if (edycja)  {
                    Msg.msg("w","Klient o takim numerze NIP juz istnieje!");   
                 } else {
                    RequestContext.getCurrentInstance().execute("rj('formX:nipPole').value = 'taki nip jest już w bazie';");
                    Msg.msg("e","Klient o takim numerze NIP juz istnieje!");
                 }
             }
         }
         }
    }
    
    private void poszukajDuplikatNazwa() throws Exception {
         String nowanazwa = selected.getNpelna();
         List<String> kliencitmp  = klDAO.findNazwaPelna(nowanazwa.trim());
         if (!kliencitmp.isEmpty()) {
                 throw new Exception();
         }
    }
     
    
    public void poszukajDuplikatNazwaWTrakcie() throws Exception {
         String nowanazwa = (String) Params.params("formX:nazwaPole");
         List<String> kliencitmp  = klDAO.findNazwaPelna(nowanazwa.trim());
         if (!kliencitmp.isEmpty()) {
                 Msg.msg("e","Klient o takim numerze NIP juz istnieje!");
         }
    }
     
     
    private void wygenerujnip() {
       List<Klienci> kliencitmp  = klDAO.findAll();
       List<Klienci> kliencinip = new ArrayList<>();
       //odnajduje klientow jednorazowych
       for (Klienci p : kliencitmp){
           if(p.getNip().startsWith("XX")){
               kliencinip.add(p);
           }
       }
       //wyciaga nipy
       List<Integer> nipy = new ArrayList<>();
       for (Klienci p : kliencinip){
             nipy.add(Integer.parseInt(p.getNip().substring(2)));
       }
       Collections.sort(nipy);
       Integer max;
       if(nipy.size()>0){
          max = nipy.get(nipy.size()-1);
          max++;
       } else {
          max = 0;
       }
       //uzupelnia o zera i robi stringa;
       String wygenerowanynip = max.toString();
       while(wygenerowanynip.length()<10){
           wygenerowanynip = "0"+wygenerowanynip;
       }
       wygenerowanynip = "XX"+wygenerowanynip;
       selected.setNip(wygenerowanynip);
    }


    
    public Klienci getSelected() {
        return selected;
    }

    public void setSelected(Klienci selected) {
        this.selected = selected;
    }
    
    public Integer getIlesrodkow() {
        return ilesrodkow;
    }

    public void setIlesrodkow(Integer ilesrodkow) {
        this.ilesrodkow = ilesrodkow;
    }

    public KlienciDAO getKlDAO() {
        return klDAO;
    }

    public void setKlDAO(KlienciDAO klDAO) {
        this.klDAO = klDAO;
    }

   
    public ArrayList<Klienci> getKl1() {
        return kl1;
    }

    public ArrayList<Klienci> getKlienciFiltered() {
        return klienciFiltered;
    }

    public void setKlienciFiltered(ArrayList<Klienci> klienciFiltered) {
        this.klienciFiltered = klienciFiltered;
    }

    public Klienci getDoUsuniecia() {
        return doUsuniecia;
    }

    public void setDoUsuniecia(Klienci doUsuniecia) {
        this.doUsuniecia = doUsuniecia;
    }

    public boolean isEdycja() {
        return edycja;
    }

    public void setEdycja(boolean edycja) {
        this.edycja = edycja;
    }

    public DokfkView getDokfkView() {
        return dokfkView;
    }

    public void setDokfkView(DokfkView dokfkView) {
        this.dokfkView = dokfkView;
    }

    public PlanKontCompleteView getPlanKontCompleteView() {
        return planKontCompleteView;
    }

    public void setPlanKontCompleteView(PlanKontCompleteView planKontCompleteView) {
        this.planKontCompleteView = planKontCompleteView;
    }

    
    
   
}
