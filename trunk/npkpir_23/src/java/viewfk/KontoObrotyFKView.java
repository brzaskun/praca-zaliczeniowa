/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Kontozapisycomparator;
import daoFK.KontoDAOfk;
import daoFK.KontoZapisyFKDAO;
import embeddable.Mce;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.Kontozapisy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KontoObrotyFKView implements Serializable{
    private List<Kontozapisy> kontozapisy;
    private List<Konto> kontaprzejrzane;
    @Inject private Kontozapisy wybranyzapis;
    private List<Kontozapisy> kontorozrachunki;
    private List<ObrotykontaTabela> wybranekontadosumowania;
    @Inject private KontoZapisyFKDAO kontoZapisyFKDAO;
    @Inject private KontoDAOfk kontoDAOfk;
    @Inject private Konto wybranekonto;
    private Double sumaWn;
    private Double sumaMa;
    private Double saldoWn;
    private Double saldoMa;
    List<ObrotykontaTabela> lista;
    @Inject private TreeNodeExtended<Konto> wybranekontoNode;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    

    public KontoObrotyFKView() {
        this.lista = new ArrayList<>();
        this.kontozapisy = new ArrayList<>();
        this.wybranekontadosumowania = new ArrayList<>();    
    }
    
    @PostConstruct
    private void init(){
    }
     public void pobierzZapisyNaKoncieNode(NodeSelectEvent event) {
        TreeNodeExtended<Konto> node = (TreeNodeExtended<Konto>) event.getTreeNode();
        wybranekonto = (Konto) node.getData();
        pobierzZapisyNaKoncie();
    }
     
    public void pobierzZapisyNaKoncieNodeUnselect(NodeUnselectEvent event) {
        lista.clear();
    }
    
      public void pobierzZapisyNaKoncie() {
        if (wybranekonto instanceof Konto) {
         lista = new ArrayList<>();
         kontozapisy = new ArrayList<>();
         kontaprzejrzane = new ArrayList<>();
         if (wybranekonto.isMapotomkow()==true) {
             List<Konto> kontamacierzyste = new ArrayList<>();
             kontamacierzyste.addAll(pobierzpotomkow(wybranekonto));
             while (kontamacierzyste.size()>0) {
                 znajdzkontazpotomkami(kontamacierzyste);
             }
             for(Konto p : kontaprzejrzane) {
                 kontozapisy.addAll(kontoZapisyFKDAO.findZapisyKontoPodatnik(wpisView.getPodatnikWpisu(), p.getPelnynumer()));
                 //tu jest BO, to nie podwojnie wpisana linia
                 kontozapisy.addAll(kontoZapisyFKDAO.findZapisyKontoBOPodatnik(wpisView.getPodatnikWpisu(), p.getPelnynumer()));
             }
             Collections.sort(kontozapisy, new Kontozapisycomparator());
             
         } else {
             kontozapisy = kontoZapisyFKDAO.findZapisyKontoPodatnik(wpisView.getPodatnikWpisu(), wybranekonto.getPelnynumer());
             kontozapisy.addAll(kontoZapisyFKDAO.findZapisyKontoBOPodatnik(wpisView.getPodatnikWpisu(), wybranekonto.getPelnynumer()));
         }
         sumamiesiecy();
         sumazapisow();
        }
     }
      
      private List<Konto> pobierzpotomkow(Konto macierzyste) {
          try {
              return kontoDAOfk.findKontaPotomneBO(wpisView.getPodatnikWpisu(), macierzyste.getPelnynumer());
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
        for(ObrotykontaTabela p : wybranekontadosumowania){
            sumaWn = sumaWn + p.getKwotaWn();
            sumaMa = sumaMa + p.getKwotaMa();
        }
        saldoWn = 0.0;
        saldoMa = 0.0;
        if(sumaWn>sumaMa){
            saldoWn = sumaWn-sumaMa;
        } else {
            saldoMa = sumaMa-sumaWn;
        }
    }
    
     private void sumamiesiecy() {
        lista.addAll(ObrotykontaTabela.wygenerujlisteObrotow(wpisView.getRokWpisuSt()));
        for (Kontozapisy p : kontozapisy) {
            ObrotykontaTabela obrotyMiesiac = new ObrotykontaTabela();
            int numermiesiaca = Mce.getMiesiacToNumber().get((p.getWiersz().getDokfk().getMiesiac()));
            if (p.getKonto().equals("000")) {
                obrotyMiesiac = lista.get(0);
            } else if (!p.getKontoprzeciwstawne().equals("000")){
                obrotyMiesiac = lista.get(numermiesiaca);
            }
            obrotyMiesiac.setKwotaWn(obrotyMiesiac.getKwotaWn()+p.getKwotawn());
            obrotyMiesiac.setKwotaMa(obrotyMiesiac.getKwotaMa()+p.getKwotama());
        }
        //a teraz czas na sumowanie narastajaco i wyliczanie sald
        double sumaWn = 0.0;
        double sumaMa = 0.0;
        for (ObrotykontaTabela tmp: lista) {
            sumaWn += tmp.getKwotaWn();
            tmp.setKwotaWnnarastajaco(sumaWn);
            sumaMa += tmp.getKwotaMa();
            tmp.setKwotaManarastajaco(sumaMa);
            double kwota = tmp.getKwotaWnnarastajaco()-tmp.getKwotaManarastajaco();
            if (kwota > 0) {
                tmp.setKwotaWnsaldo(kwota);
                tmp.setKwotaMasaldo(0);
            } else if (kwota < 0 ) {
                tmp.setKwotaWnsaldo(0);
                tmp.setKwotaMasaldo(-kwota);
            } else {
                tmp.setKwotaWnsaldo(0);
                tmp.setKwotaMasaldo(0);
            }
        }
        
    }
    
 
    //<editor-fold defaultstate="collapsed" desc="comment">
     
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
     
    public List<Kontozapisy> getKontozapisy() {
        return kontozapisy;
    }
    
    public void setKontozapisy(List<Kontozapisy> kontozapisy) {
        this.kontozapisy = kontozapisy;
    }

    public TreeNodeExtended<Konto> getWybranekontoNode() {
        return wybranekontoNode;
    }

    public void setWybranekontoNode(TreeNodeExtended<Konto> wybranekontoNode) {
        this.wybranekontoNode = wybranekontoNode;
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

    public List<ObrotykontaTabela> getWybranekontadosumowania() {
        return wybranekontadosumowania;
    }

    public void setWybranekontadosumowania(List<ObrotykontaTabela> wybranekontadosumowania) {
        this.wybranekontadosumowania = wybranekontadosumowania;
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

    public List<ObrotykontaTabela> getLista() {
        return lista;
    }

    public void setLista(List<ObrotykontaTabela> lista) {
        this.lista = lista;
    }
    
        
    public Kontozapisy getWybranyzapis() {
        return wybranyzapis;
    }
    
    public void setWybranyzapis(Kontozapisy wybranyzapis) {
        this.wybranyzapis = wybranyzapis;
    }
    //</editor-fold>

   

    public static class ObrotykontaTabela {

         private String rok;
         private String miesiac;
         private double kwotaWn;
         private double kwotaMa;
         private double kwotaWnnarastajaco;
         private double kwotaManarastajaco;
         private double kwotaWnsaldo;
         private double kwotaMasaldo;
            
        public ObrotykontaTabela() {
        }

        public ObrotykontaTabela(String rok, String miesiac) {
            this.rok = rok;
            this.miesiac = miesiac;
            this.kwotaWn = 0.0;
            this.kwotaMa = 0.0;
            this.kwotaWnnarastajaco = 0.0;
            this.kwotaManarastajaco = 0.0;
            this.kwotaWnsaldo = 0.0;
            this.kwotaMasaldo = 0.0;
        }
        
        public static List<ObrotykontaTabela> wygenerujlisteObrotow(String rok) {
            List<ObrotykontaTabela> lista = new ArrayList<>();
            lista.add(new ObrotykontaTabela(rok,"BO"));
            lista.add(new ObrotykontaTabela(rok,"styczeń"));
            lista.add(new ObrotykontaTabela(rok,"luty"));
            lista.add(new ObrotykontaTabela(rok,"marzec"));
            lista.add(new ObrotykontaTabela(rok,"kwiecień"));
            lista.add(new ObrotykontaTabela(rok,"maj"));
            lista.add(new ObrotykontaTabela(rok,"czerwiec"));
            lista.add(new ObrotykontaTabela(rok,"lipiec"));
            lista.add(new ObrotykontaTabela(rok,"sierpień"));
            lista.add(new ObrotykontaTabela(rok,"wrzesień"));
            lista.add(new ObrotykontaTabela(rok,"październik"));
            lista.add(new ObrotykontaTabela(rok,"listopad"));
            lista.add(new ObrotykontaTabela(rok,"grudzień"));
            return lista;
        }

        //<editor-fold defaultstate="collapsed" desc="comment">
        
        public String getRok() {
            return rok;
        }
        
        public void setRok(String rok) {
            this.rok = rok;
        }
               
        
        public String getMiesiac() {
            return miesiac;
        }
        
        public void setMiesiac(String miesiac) {
            this.miesiac = miesiac;
        }
        
        public double getKwotaWn() {
            return kwotaWn;
        }
        
        public void setKwotaWn(double kwotaWn) {
            this.kwotaWn = kwotaWn;
        }
        
        public double getKwotaMa() {
            return kwotaMa;
        }
        
        public void setKwotaMa(double kwotaMa) {
            this.kwotaMa = kwotaMa;
        }
        
        public double getKwotaWnnarastajaco() {
            return kwotaWnnarastajaco;
        }
        
        public void setKwotaWnnarastajaco(double kwotaWnnarastajaco) {
            this.kwotaWnnarastajaco = kwotaWnnarastajaco;
        }
        
        public double getKwotaManarastajaco() {
            return kwotaManarastajaco;
        }
        
        public void setKwotaManarastajaco(double kwotaManarastajaco) {
            this.kwotaManarastajaco = kwotaManarastajaco;
        }
        
        public double getKwotaWnsaldo() {
            return kwotaWnsaldo;
        }
        
        public void setKwotaWnsaldo(double kwotaWnsaldo) {
            this.kwotaWnsaldo = kwotaWnsaldo;
        }
        
        public double getKwotaMasaldo() {
            return kwotaMasaldo;
        }
        
        public void setKwotaMasaldo(double kwotaMasaldo) {
            this.kwotaMasaldo = kwotaMasaldo;
        }
        
        
        //</editor-fold>
        
    }

   
}

