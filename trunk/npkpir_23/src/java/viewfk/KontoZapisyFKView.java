/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Kontozapisycomparator;
import dao.RozrachunkiDAO;
import daoFK.KontoDAOfk;
import daoFK.KontoZapisyFKDAO;
import entityfk.Konto;
import entityfk.Kontozapisy;
import entityfk.Rozrachunki;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KontoZapisyFKView implements Serializable{
    private List<Kontozapisy> kontozapisy;
    private List<Konto> kontaprzejrzane;
    @Inject private Kontozapisy wybranyzapis;
    private List<Kontozapisy> kontorozrachunki;
    private List<Kontozapisy> wybranekontadosumowania;
    @Inject private KontoZapisyFKDAO kontoZapisyFKDAO;
    @Inject private KontoDAOfk kontoDAOfk;
    @Inject private RozrachunkiDAO rozrachunkiDAO;
    @Inject private Konto wybranekonto;
    private Double sumaWn;
    private Double sumaMa;
    private Double saldoWn;
    private Double saldoMa;
    private List zapisydopodswietlenia;
    

    public KontoZapisyFKView() {
        kontozapisy = new ArrayList<>();
        wybranekontadosumowania = new ArrayList<>();    
    }
    
    @PostConstruct
    private void init(){
    }
    
      public void pobierzZapisyNaKoncie() {
         if (wybranekonto instanceof Konto) {
         kontozapisy = new ArrayList<>();
         kontaprzejrzane = new ArrayList<>();
         if (wybranekonto.isMapotomkow()==true) {
             List<Konto> kontamacierzyste = new ArrayList<>();
             kontamacierzyste.addAll(pobierzpotomkow(wybranekonto));
             while (kontamacierzyste.size()>0) {
                 znajdzkontazpotomkami(kontamacierzyste);
             }
             for(Konto p : kontaprzejrzane) {
                 kontozapisy.addAll(kontoZapisyFKDAO.findZapisyKontoPodatnik("Kowalski", p.getPelnynumer()));
             }
             Collections.sort(kontozapisy, new Kontozapisycomparator());
             
         } else {
             kontozapisy = kontoZapisyFKDAO.findZapisyKontoPodatnik("Kowalski", wybranekonto.getPelnynumer());
         }
         sumazapisow();
         }
     }
      
      private List<Konto> pobierzpotomkow(Konto macierzyste) {
          try {
              return kontoDAOfk.findKontaPotomne(macierzyste.getPelnynumer());
          } catch (Exception e) {
              Msg.msg("e", "nie udane pobierzpotomkow");
          }
          return null;
      }
      
      private void znajdzkontazpotomkami(List<Konto> wykaz) {
          List<Konto> listakontposrednia = new ArrayList<>();
          Iterator it = wykaz.iterator();
          while(it.hasNext()) {
              Konto p = (Konto) it.next();
              if (p.isMapotomkow()) {
                  listakontposrednia.addAll(pobierzpotomkow(p));
                  it.remove();
              } else {
                  kontaprzejrzane.add(p);
                  it.remove();
              }
          }
          wykaz.addAll(listakontposrednia);
      }
    
      
    public void sumazapisow(){
        sumaWn = 0.0;
        sumaMa = 0.0;
        for(Kontozapisy p : wybranekontadosumowania){
            sumaWn = sumaWn + p.getKwotawn();
            sumaMa = sumaMa + p.getKwotama();
        }
        saldoWn = 0.0;
        saldoMa = 0.0;
        if(sumaWn>sumaMa){
            saldoWn = sumaWn-sumaMa;
        } else {
            saldoMa = sumaMa-sumaWn;
        }
    }
    
    //poszukuje rozrachunkow do sparowania
    public void odszukajsparowanerozrachunki() {
        Kontozapisy wybranyrozrachunek = wybranekontadosumowania.get(0);
        int numerpodswietlonegowiersza = wybranyrozrachunek.getWiersz().getIdwiersza();
        List<Rozrachunki> pobranezapisy = rozrachunkiDAO.findByWierszID(numerpodswietlonegowiersza);
        List znalezionenumery = new ArrayList<>();
        //poszukam innych numerow wierszy
        for (Rozrachunki p : pobranezapisy) {
            if (p.getRozrachunkiPK().getZapisrozliczany() == numerpodswietlonegowiersza) {
                znalezionenumery.add(p.getRozrachunkiPK().getZapissparowany());
            } else {
                znalezionenumery.add(p.getRozrachunkiPK().getZapisrozliczany());
            }
        }
        zapisydopodswietlenia = new ArrayList<>();
        //wyszukujemy numery Kontozapisy dla javascriptu
        for (Kontozapisy r : kontozapisy) {
            if (znalezionenumery.contains(r.getWiersz().getIdwiersza())) {
                zapisydopodswietlenia.add(r.getId());
            }
        }
        RequestContext.getCurrentInstance().update("zapisydopodswietlenia");
        RequestContext.getCurrentInstance().execute("podswietlrozrachunki();");
    }
    
 
    //<editor-fold defaultstate="collapsed" desc="comment">
    public List<Kontozapisy> getKontozapisy() {
        return kontozapisy;
    }
    
    public void setKontozapisy(List<Kontozapisy> kontozapisy) {
        this.kontozapisy = kontozapisy;
    }
    
    public KontoZapisyFKDAO getKontoZapisyFKDAO() {
        return kontoZapisyFKDAO;
    }
    
    public void setKontoZapisyFKDAO(KontoZapisyFKDAO kontoZapisyFKDAO) {
        this.kontoZapisyFKDAO = kontoZapisyFKDAO;
    }
    
    public Konto getWybranekonto() {
        return wybranekonto;
    }
    
    public void setWybranekonto(Konto wybranekonto) {
        this.wybranekonto = wybranekonto;
    }
    
    public List<Kontozapisy> getWybranekontadosumowania() {
        return wybranekontadosumowania;
    }
    
    public void setWybranekontadosumowania(List<Kontozapisy> wybranekontadosumowania) {
        this.wybranekontadosumowania = wybranekontadosumowania;
    }

    public List getZapisydopodswietlenia() {
        return zapisydopodswietlenia;
    }

    public void setZapisydopodswietlenia(List zapisydopodswietlenia) {
        this.zapisydopodswietlenia = zapisydopodswietlenia;
    }
    
    
    public Double getSumaWn() {
        return sumaWn;
    }
    
    public void setSumaWn(Double sumaWn) {
        this.sumaWn = sumaWn;
    }
    
    public Double getSumaMa() {
        return sumaMa;
    }
    
    public void setSumaMa(Double sumaMa) {
        this.sumaMa = sumaMa;
    }
    
    public Double getSaldoWn() {
        return saldoWn;
    }
    
    public void setSaldoWn(Double saldoWn) {
        this.saldoWn = saldoWn;
    }
    
    public Double getSaldoMa() {
        return saldoMa;
    }
    
    public void setSaldoMa(Double saldoMa) {
        this.saldoMa = saldoMa;
    }
    
    public List<Kontozapisy> getKontorozrachunki() {
        return kontorozrachunki;
    }
    
    public void setKontorozrachunki(List<Kontozapisy> kontorozrachunki) {
        this.kontorozrachunki = kontorozrachunki;
    }
    
    public Kontozapisy getWybranyzapis() {
        return wybranyzapis;
    }
    
    public void setWybranyzapis(Kontozapisy wybranyzapis) {
        this.wybranyzapis = wybranyzapis;
    }
    //</editor-fold>

   
}

