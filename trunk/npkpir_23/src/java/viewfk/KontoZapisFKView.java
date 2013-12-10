/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Kontozapisycomparator;
import daoFK.KontoDAOfk;
import daoFK.KontoZapisyFKDAO;
import daoFK.RozrachunekfkDAO;
import daoFK.ZestawienielisttransakcjiDAO;
import embeddablefk.Transakcja;
import embeddablefk.WierszStronafk;
import embeddablefk.WierszStronafkPK;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.Kontozapisy;
import entityfk.Rozrachunekfk;
import entityfk.Zestawienielisttransakcji;
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
public class KontoZapisFKView implements Serializable{
    private List<Kontozapisy> kontozapisy;
    private List<Konto> kontaprzejrzane;
    @Inject private Kontozapisy wybranyzapis;
    private List<Kontozapisy> kontorozrachunki;
    private List<Kontozapisy> wybranekontadosumowania;
    @Inject private KontoZapisyFKDAO kontoZapisyFKDAO;
    @Inject private KontoDAOfk kontoDAOfk;
    @Inject private Konto wybranekonto;
    @Inject private RozrachunekfkDAO rozrachunekfkDAO;
    @Inject private ZestawienielisttransakcjiDAO zestawienielisttransakcjiDAO;
    private Double sumaWn;
    private Double sumaMa;
    private Double saldoWn;
    private Double saldoMa;
    private List zapisydopodswietlenia;
    

    public KontoZapisFKView() {
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
             //tu jest ten loop ala TreeeNode
             while (kontamacierzyste.size()>0) {
                 znajdzkontazpotomkami(kontamacierzyste);
             }
             for(Konto p : kontaprzejrzane) {
                 kontozapisy.addAll(kontoZapisyFKDAO.findZapisyKontoPodatnik("Kowalski", p.getPelnynumer()));
             }
             Collections.sort(kontozapisy, new Kontozapisycomparator());
             
         } else {
             kontozapisy = kontoZapisyFKDAO.findZapisyKontoPodatnik("Kowalski", wybranekonto.getPelnynumer
());
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
        int numerpodswietlonegowiersza = wybranyrozrachunek.getWiersz().getIdporzadkowy();
        Dokfk zjakiegodokumentupochodzi = wybranyrozrachunek.getWiersz().getDokfk();
        WierszStronafk wierszIDrozrachunku = new WierszStronafk();
        WierszStronafkPK wierszIDrozrachunkuPK = new WierszStronafkPK();
        wierszIDrozrachunkuPK.setNrPorzadkowyWiersza(numerpodswietlonegowiersza);
        wierszIDrozrachunkuPK.setNrkolejnydokumentu(zjakiegodokumentupochodzi.getDokfkPK().getNrkolejny());
        String wnma;
        if (wybranyrozrachunek.getKwotawn() > 0) { 
            wnma = "Wn";
        } else {
            wnma= "Ma";
        }
        wierszIDrozrachunkuPK.setStronaWnlubMa(wnma);
        wierszIDrozrachunkuPK.setTypdokumentu(zjakiegodokumentupochodzi.getDokfkPK().getSeriadokfk());
        wierszIDrozrachunku.setWierszStronafkPK(wierszIDrozrachunkuPK);
        //mamy juz skonstruowany wiersz, teraz z bazy pobierzemy wszytskie rozrachunki i bedziemy sobie szukac
        List<Zestawienielisttransakcji> zestawienietransakcji = zestawienielisttransakcjiDAO.findAll();
        List<Transakcja> listytransakcji = new ArrayList<>();
        for (Zestawienielisttransakcji p : zestawienietransakcji) {
            for (Transakcja r: p.getListatransakcji()) {
                listytransakcji.add(r);
            }
        }
        List<WierszStronafkPK> znalezionenumery = new ArrayList<>();
        //poszukam innych numerow wierszy
        for (Transakcja p : listytransakcji) {
            if (p.idSparowany().equals(wierszIDrozrachunkuPK)) {
                znalezionenumery.add(p.idRozliczany());
            } 
            if (p.idRozliczany().equals(wierszIDrozrachunkuPK)) {
                znalezionenumery.add(p.idSparowany());
            }
        }
        zapisydopodswietlenia = new ArrayList<>();
        //wyszukujemy numery Kontozapisy dla javascriptu
        for (Kontozapisy r : kontozapisy) {
            boolean zgodneWierszStronaPK = false;
            if (wnma.equals("Wn")) {
                zgodneWierszStronaPK = znalezionenumery.contains(r.getWiersz().getWierszStronaMa().getWierszStronafkPK());
            } else {
                zgodneWierszStronaPK = znalezionenumery.contains(r.getWiersz().getWierszStronaWn().getWierszStronafkPK());
            }
            if (zgodneWierszStronaPK) {
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

