/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.WierszBODAO;
import embeddable.Mce;
import embeddablefk.ListaSum;
import entityfk.Konto;
import entityfk.StronaWiersza;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
import pdf.PdfKontoObroty;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KontoObrotyFKView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<StronaWiersza> kontozapisy;
    private List<Konto> kontaprzejrzane;
    @Inject private StronaWiersza wybranyzapis;
    private List<StronaWiersza> kontorozrachunki;
    private List<ObrotykontaTabela> wybranekontadosumowania;
    @Inject private StronaWierszaDAO stronaWierszaDAO;
    @Inject private KontoDAOfk kontoDAOfk;
    @Inject private Konto wybranekonto;
    @Inject
    private WierszBODAO wierszBODAO;
    private Double sumaWn;
    private Double sumaMa;
    private Double saldoWn;
    private Double saldoMa;
    List<ObrotykontaTabela> lista;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private String wybranaWalutaDlaKont;
    private List<ListaSum> listasum;
    private List<Konto> wykazkont;
    
    

    public KontoObrotyFKView() {
         //E.m(this);
        this.lista = Collections.synchronizedList(new ArrayList<>());
        this.kontozapisy = Collections.synchronizedList(new ArrayList<>());
        this.wybranekontadosumowania = Collections.synchronizedList(new ArrayList<>());
        wybranaWalutaDlaKont = "wszystkie";
        listasum = Collections.synchronizedList(new ArrayList<>());
        ListaSum l = new ListaSum();
        listasum.add(l);
    }
    
    
    public void init(){
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBez0(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());;
        usunkontabezsald();
    }
 
    
    private void usunkontabezsald() {
        kontozapisy = stronaWierszaDAO.findStronaByPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Set<Konto> listakont = new HashSet<>();
        for (StronaWiersza p : kontozapisy) {
            listakont.add(p.getKonto());
        }
        Set<Konto> listamacierzyste = wyluskajmacierzyste(listakont);
        wykazkont = Collections.synchronizedList(new ArrayList<>());
        wykazkont.addAll(listakont);
        wykazkont.addAll(listamacierzyste);
    }
    
    private Set<Konto> wyluskajmacierzyste(Set<Konto> listakont) {
        Set<Konto> listamacierzyste = new HashSet<>();
        for (Konto p : listakont) {
            Konto m = p.getKontomacierzyste();
            while (m != null) {
                listamacierzyste.add(m);
                m = m.getKontomacierzyste();
            }
        }
        return listamacierzyste;
    }
    
    public void pobierzZapisyNaKoncieNode() {
        wybranekontadosumowania = Collections.synchronizedList(new ArrayList<>());
        listasum = Collections.synchronizedList(new ArrayList<>());
        ListaSum l = new ListaSum();
        listasum.add(l);
        pobierzZapisyNaKoncie();
    }
     
    public void pobierzZapisyNaKoncieNodeUnselect() {
        lista.clear();
        wybranekontadosumowania = Collections.synchronizedList(new ArrayList<>());
        listasum = Collections.synchronizedList(new ArrayList<>());
        ListaSum l = new ListaSum();
        listasum.add(l);
    }
    
      public void pobierzZapisyNaKoncie() {
        if (wybranekonto instanceof Konto) {
         lista = Collections.synchronizedList(new ArrayList<>());
         kontozapisy = Collections.synchronizedList(new ArrayList<>());
         kontaprzejrzane = Collections.synchronizedList(new ArrayList<>());
         if (wybranekonto.isMapotomkow()==true) {
             List<Konto> kontamacierzyste = Collections.synchronizedList(new ArrayList<>());
             kontamacierzyste.addAll(pobierzpotomkow(wybranekonto));
             while (kontamacierzyste.size()>0) {
                 znajdzkontazpotomkami(kontamacierzyste);
             }
             for(Konto p : kontaprzejrzane) {
                  if (wybranaWalutaDlaKont.equals("wszystkie")) {
                        kontozapisy.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkie(wpisView.getPodatnikObiekt(), p, wpisView.getRokWpisuSt()));
                    } else {
                        kontozapisy.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWaluta(wpisView.getPodatnikObiekt(), p, wpisView.getRokWpisuSt(), wybranaWalutaDlaKont));
                    }
                 
                 //tu jest BO, to nie podwojnie wpisana linia
             }
             //Collections.sort(kontozapisy, new Kontozapisycomparator());
             
         } else {
             if (wybranaWalutaDlaKont.equals("wszystkie")) {
                        kontozapisy.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkie(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt()));
                    } else {
                        kontozapisy.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWaluta(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt(), wybranaWalutaDlaKont));
                    }
         }
         sumamiesiecy();
         sumazapisow();
         sumazapisowpln();
        }
     }
      
            
      private List<Konto> pobierzpotomkow(Konto macierzyste) {
          try {
              return kontoDAOfk.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), macierzyste);
          } catch (Exception e) {  E.e(e);
              Msg.msg("e", "nie udane pobierzpotomkow");
          }
          return null;
      }
      
      private void znajdzkontazpotomkami(List<Konto> wykaz) {
          List<Konto> listakontposrednia = Collections.synchronizedList(new ArrayList<>());
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
    public void sumazapisowtotal() {
        sumazapisow();
        sumazapisowpln();
    }
      
    public void sumazapisow(){
        sumaWn = 0.0;
        sumaMa = 0.0;
        for(ObrotykontaTabela p : wybranekontadosumowania){
            sumaWn = Z.z(sumaWn + p.getKwotaWn());
            sumaMa = Z.z(sumaMa + p.getKwotaMa());
        }
        saldoWn = 0.0;
        saldoMa = 0.0;
        if(sumaWn>sumaMa){
            saldoWn = Z.z(sumaWn-sumaMa);
        } else {
            saldoMa = Z.z(sumaMa-sumaWn);
        }
        listasum.get(0).setSumaWn(sumaWn);
        listasum.get(0).setSumaMa(sumaMa);
        listasum.get(0).setSaldoWn(saldoWn);
        listasum.get(0).setSaldoMa(saldoMa);
    }
    
    public void sumazapisowpln(){
        sumaWn = 0.0;
        sumaMa = 0.0;
        for(ObrotykontaTabela p : wybranekontadosumowania){
            sumaWn = Z.z(sumaWn + p.getKwotaWnPLN());
            sumaMa = Z.z(sumaMa + p.getKwotaMaPLN());
        }
        saldoWn = 0.0;
        saldoMa = 0.0;
        if(sumaWn>sumaMa){
            saldoWn = Z.z(sumaWn-sumaMa);
        } else {
            saldoMa = Z.z(sumaMa-sumaWn);
        }
        listasum.get(0).setSumaWnPLN(sumaWn);
        listasum.get(0).setSumaMaPLN(sumaMa);
        listasum.get(0).setSaldoWnPLN(saldoWn);
        listasum.get(0).setSaldoMaPLN(saldoMa);
    }
    
     private void sumamiesiecy() {
        lista.addAll(ObrotykontaTabela.wygenerujlisteObrotow(wpisView.getRokWpisuSt()));
        for (StronaWiersza p : kontozapisy) {
            ObrotykontaTabela obrotyMiesiac = new ObrotykontaTabela();
            if (p.getWnma().equals("Wn")) {
                if (p.getTypStronaWiersza() == 9) {
                    obrotyMiesiac = lista.get(0);
                } else if (p.getTypStronaWiersza() != 9) {
                    int numermiesiaca = Mce.getMiesiacToNumber().get((p.getWiersz().getDokfk().getMiesiac()));
                    obrotyMiesiac = lista.get(numermiesiaca);
                }
                obrotyMiesiac.setKwotaWn(Z.z(obrotyMiesiac.getKwotaWn()+p.getKwota()));
                obrotyMiesiac.setKwotaWnPLN(Z.z(obrotyMiesiac.getKwotaWnPLN()+p.getKwotaPLN()));
            } else {
                if (p.getTypStronaWiersza() == 9) {
                    obrotyMiesiac = lista.get(0);
                } else if (p.getTypStronaWiersza() != 9) {
                    int numermiesiaca = Mce.getMiesiacToNumber().get((p.getWiersz().getDokfk().getMiesiac()));
                    obrotyMiesiac = lista.get(numermiesiaca);
                }
                obrotyMiesiac.setKwotaMa(Z.z(obrotyMiesiac.getKwotaMa()+p.getKwota()));
                obrotyMiesiac.setKwotaMaPLN(Z.z(obrotyMiesiac.getKwotaMaPLN()+p.getKwotaPLN()));
            }
            
        }
        //a teraz czas na sumowanie narastajaco i wyliczanie sald
        double sumaWn = 0.0;
        double sumaMa = 0.0;
        double sumaWnPLN = 0.0;
        double sumaMaPLN = 0.0;
        for (ObrotykontaTabela tmp: lista) {
            sumaWn = Z.z(sumaWn + tmp.getKwotaWn());
            sumaWnPLN = Z.z(sumaWnPLN + tmp.getKwotaWnPLN());
            tmp.setKwotaWnnarastajaco(sumaWn);
            tmp.setKwotaWnnarastajacoPLN(sumaWnPLN);
            sumaMa = Z.z(sumaMa + tmp.getKwotaMa());
            sumaMaPLN = Z.z(sumaMaPLN + tmp.getKwotaMaPLN());
            tmp.setKwotaManarastajaco(sumaMa);
            tmp.setKwotaManarastajacoPLN(sumaMaPLN);
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
            kwota = tmp.getKwotaWnnarastajacoPLN()-tmp.getKwotaManarastajacoPLN();
            if (kwota > 0) {
                tmp.setKwotaWnsaldoPLN(kwota);
                tmp.setKwotaMasaldoPLN(0);
            } else if (kwota < 0 ) {
                tmp.setKwotaWnsaldoPLN(0);
                tmp.setKwotaMasaldoPLN(-kwota);
            } else {
                tmp.setKwotaWnsaldoPLN(0);
                tmp.setKwotaMasaldoPLN(0);
            }
        }
        
    }
    public void drukujPdfObrotyNaKoncie() {
        try {
            PdfKontoObroty.drukujobroty(wpisView, lista, wybranekonto, listasum);
            String wydruk = "wydrukzapisynakoncie('"+wpisView.getPodatnikWpisu()+"')";
            RequestContext.getCurrentInstance().execute(wydruk);
        } catch (Exception e) {  E.e(e);

        }
    }
 
    //<editor-fold defaultstate="collapsed" desc="comment">
     
     
    public String getWybranaWalutaDlaKont() {
        return wybranaWalutaDlaKont;
    }

    public void setWybranaWalutaDlaKont(String wybranaWalutaDlaKont) {
        this.wybranaWalutaDlaKont = wybranaWalutaDlaKont;
    }

    public List<Konto> getWykazkont() {
        return wykazkont;
    }

    public void setWykazkont(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<StronaWiersza> getKontozapisy() {
        return kontozapisy;
    }

    public void setKontozapisy(List<StronaWiersza> kontozapisy) {
        this.kontozapisy = kontozapisy;
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

    public StronaWiersza getWybranyzapis() {
        return wybranyzapis;
    }

    public void setWybranyzapis(StronaWiersza wybranyzapis) {
        this.wybranyzapis = wybranyzapis;
    }

    public List<StronaWiersza> getKontorozrachunki() {
        return kontorozrachunki;
    }

    public void setKontorozrachunki(List<StronaWiersza> kontorozrachunki) {
        this.kontorozrachunki = kontorozrachunki;
    }
    
   

    public List<ObrotykontaTabela> getLista() {
        return lista;
    }

    public void setLista(List<ObrotykontaTabela> lista) {
        this.lista = lista;
    }

    public List<ListaSum> getListasum() {
        return listasum;
    }

    public void setListasum(List<ListaSum> listasum) {
        this.listasum = listasum;
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
         private double kwotaWnPLN;
         private double kwotaMaPLN;
         private double kwotaWnnarastajacoPLN;
         private double kwotaManarastajacoPLN;
         private double kwotaWnsaldoPLN;
         private double kwotaMasaldoPLN;
            
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
            this.kwotaWnPLN = 0.0;
            this.kwotaMaPLN = 0.0;
            this.kwotaWnnarastajacoPLN = 0.0;
            this.kwotaManarastajacoPLN = 0.0;
            this.kwotaWnsaldoPLN = 0.0;
            this.kwotaMasaldoPLN = 0.0;
        }
        
        public static List<ObrotykontaTabela> wygenerujlisteObrotow(String rok) {
            List<ObrotykontaTabela> lista = Collections.synchronizedList(new ArrayList<>());
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

        public double getKwotaWnPLN() {
            return kwotaWnPLN;
        }

        public void setKwotaWnPLN(double kwotaWnPLN) {
            this.kwotaWnPLN = kwotaWnPLN;
        }

        public double getKwotaMaPLN() {
            return kwotaMaPLN;
        }

        public void setKwotaMaPLN(double kwotaMaPLN) {
            this.kwotaMaPLN = kwotaMaPLN;
        }

        public double getKwotaWnnarastajacoPLN() {
            return kwotaWnnarastajacoPLN;
        }

        public void setKwotaWnnarastajacoPLN(double kwotaWnnarastajacoPLN) {
            this.kwotaWnnarastajacoPLN = kwotaWnnarastajacoPLN;
        }

        public double getKwotaManarastajacoPLN() {
            return kwotaManarastajacoPLN;
        }

        public void setKwotaManarastajacoPLN(double kwotaManarastajacoPLN) {
            this.kwotaManarastajacoPLN = kwotaManarastajacoPLN;
        }

        public double getKwotaWnsaldoPLN() {
            return kwotaWnsaldoPLN;
        }

        public void setKwotaWnsaldoPLN(double kwotaWnsaldoPLN) {
            this.kwotaWnsaldoPLN = kwotaWnsaldoPLN;
        }

        public double getKwotaMasaldoPLN() {
            return kwotaMasaldoPLN;
        }

        public void setKwotaMasaldoPLN(double kwotaMasaldoPLN) {
            this.kwotaMasaldoPLN = kwotaMasaldoPLN;
        }
        
        
        //</editor-fold>
        
    }

   
}

