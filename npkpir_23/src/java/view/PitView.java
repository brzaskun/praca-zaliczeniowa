/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.CechazapisuDAOfk;
import dao.PitDAO;
import dao.PodatnikDAO;
import dao.SMTPSettingsDAO;
import entity.Pitpoz;
import entityfk.Cechazapisu;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import mail.MailOther;
import msg.Msg;
import org.primefaces.PrimeFaces;
 import pdf.PdfPIT5;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PitView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Pitpoz> lista;
    private Pitpoz biezacyPit;

    @Inject private PitDAO pitDAO;
    @Inject private PodatnikDAO podatnikDAO;
    @Inject
    private WpisView wpisView;
    @Inject
    private SMTPSettingsDAO sMTPSettingsDAO;
    private List<Cechazapisu> pobranecechypodatnik;
    private Cechazapisu wybranacechadok;
    @Inject
    private CechazapisuDAOfk cechazapisuDAOfk;
   

    public PitView() {
        lista = Collections.synchronizedList(new ArrayList<>());
        biezacyPit = new Pitpoz();
    }
    
    public void wybranopit() {
        if (biezacyPit!=null) {
            Msg.msg("i", String.format("Wybrano PIT za %s/%s", biezacyPit.getPkpirR(), biezacyPit.getPkpirM()));
        }
    }
    
    
    @PostConstruct
    public void init() { //E.m(this);
        biezacyPit = new Pitpoz();
        lista = pitDAO.findPitPod(wpisView.getRokWpisuSt(), wpisView.getPodatnikWpisu(), wybranacechadok);
        Collections.sort(lista, new PitpozComparator());
        pobranecechypodatnik = cechazapisuDAOfk.findPodatnikOnlyAktywne(wpisView.getPodatnikObiekt());
       
    }
    
     public void usun() {
        int index = lista.size() - 1;
        Pitpoz selected = lista.get(index);
        pitDAO.remove(selected);
        lista.remove(selected);
        PrimeFaces.current().ajax().update("formpi:tablicapit");
        Msg.msg("i", "Usunieto ostatni PIT dla podatnika "+selected.getUdzialowiec()+" za m-c: "+selected.getPkpirM(),"formpi:messages");
    }
      // Calculate the total należna zaliczka
    public double getTotalNaleznaZal() {
        return lista.stream()
                     .map(Pitpoz::getNaleznazal)
                    .filter(Objects::nonNull)
                    .mapToDouble(naleznazal -> naleznazal != null ? naleznazal.doubleValue() : 0.0)
                    .sum();

    }
     public void drukujarch() {
         try {
            PdfPIT5.drukuj(biezacyPit, wpisView, podatnikDAO);
         } catch (Exception e) { E.e(e); 
             
         }
     }

     public void mailPIT5() {
         try {
             MailOther.pit5(wpisView, sMTPSettingsDAO.findSprawaByDef());
         } catch (Exception e) { E.e(e); 
             
         }
     }
     
     public void obliczPitCecha() {
         init();
         if (wybranacechadok!=null) {
            Msg.msg("Pobrano PIT z uwzględnieniem cechy");
         } else {
            Msg.msg("Pobrano PIT z uwzględnieniem cechy");
         }
     }
     
     private void aktualizujGuest(){
        wpisView.naniesDaneDoWpis();
    }
     private void aktualizuj(){
        wpisView.naniesDaneDoWpis();
    }
    
     public void aktualizujGuest(String strona) throws IOException {
        aktualizujGuest();
        aktualizuj();
        init();
        //FacesContext.getCurrentInstance().getExternalContext().redirect(strona);
    }

    public List<Pitpoz> getLista() {
        return lista;
    }
   
    public void setLista(List<Pitpoz> lista) {
        lista = lista;
    }

    public PitDAO getPitDAO() {
        return pitDAO;
    }

    public void setPitDAO(PitDAO pitDAO) {
        this.pitDAO = pitDAO;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Pitpoz getBiezacyPit() {
        return biezacyPit;
    }

    public void setBiezacyPit(Pitpoz biezacyPit) {
        this.biezacyPit = biezacyPit;
    }

    public List<Cechazapisu> getPobranecechypodatnik() {
        return pobranecechypodatnik;
    }

    public void setPobranecechypodatnik(List<Cechazapisu> pobranecechypodatnik) {
        this.pobranecechypodatnik = pobranecechypodatnik;
    }

    public Cechazapisu getWybranacechadok() {
        return wybranacechadok;
    }

    public void setWybranacechadok(Cechazapisu wybranacechadok) {
        this.wybranacechadok = wybranacechadok;
    }

    


   
    
}

 class PitpozComparator implements Comparator<Pitpoz>, Serializable {
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(Pitpoz o1, Pitpoz o2) {
        // Porównanie pola pkpirR (rok)
        int year1 = parseIntSafe(o1.getPkpirR());
        int year2 = parseIntSafe(o2.getPkpirR());
        int yearComparison = Integer.compare(year1, year2);

        if (yearComparison != 0) {
            return yearComparison;
        }

        // Jeśli lata są równe, porównaj pole pkpirM (miesiąc)
        int month1 = parseIntSafe(o1.getPkpirM());
        int month2 = parseIntSafe(o2.getPkpirM());
        return Integer.compare(month1, month2);
    }

    // Metoda pomocnicza do bezpiecznego parsowania liczb całkowitych
    private int parseIntSafe(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return 0; // Lub inna wartość domyślna w zależności od kontekstu
        }
    }
}