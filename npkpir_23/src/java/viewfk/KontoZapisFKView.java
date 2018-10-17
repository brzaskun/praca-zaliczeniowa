/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.DokumentFKBean;
import beansFK.KontaFKBean;
import beansFK.RozliczTransakcjeBean;
import comparator.Kontocomparator;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import dao.StronaWierszaDAO;
import daoFK.CechazapisuDAOfk;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import daoFK.TransakcjaDAO;
import daoFK.WierszBODAO;
import embeddable.Mce;
import embeddablefk.ListaSum;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Transakcja;
import error.E;
import extclass.ReverseIterator;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.DoubleAccumulator;
import java.util.stream.Collectors;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.context.RequestContext;
import org.primefaces.model.TreeNode;
import pdf.PdfKontoZapisy;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KontoZapisFKView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<StronaWiersza> kontozapisy;
    private List<StronaWiersza> kontozapisyfiltered;
    @Inject private StronaWiersza wybranyzapis;
    private List<StronaWiersza> kontorozrachunki;
    private List<StronaWiersza> wybranezapisydosumowania;
    @Inject private StronaWierszaDAO stronaWierszaDAO;
    @Inject private KontoDAOfk kontoDAOfk;
    @Inject private TransakcjaDAO transakcjaDAO;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private CechazapisuDAOfk cechazapisuDAOfk;
    @Inject private Konto wybranekonto;
    @Inject private Konto kontodoprzeksiegowania;
    private Double sumaWn;
    private Double sumaMa;
    private Double saldoWn;
    private Double saldoMa;
    private Double sumaWnPLN;
    private Double sumaMaPLN;
    private Double saldoWnPLN;
    private Double saldoMaPLN;
    private List zapisydopodswietlenia;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private String wybranaWalutaDlaKont;
    private List<ListaSum> listasum;
    private List<Konto> wykazkont;
    private List<Konto> wszystkiekonta;
    private List<StronaWiersza> zapisyRok;
    private String wybranyRodzajKonta;
    private boolean nierenderujkolumnnywalut;
    private boolean pokaztransakcje;
    private List<Konto> ostatniaanalityka;
    private boolean pobierzrokpoprzedni;

    

    public KontoZapisFKView() {
        E.m(this);
        kontozapisy = Collections.synchronizedList(new ArrayList<>());
        wybranezapisydosumowania = Collections.synchronizedList(new ArrayList<>());
        wybranaWalutaDlaKont = "wszystkie";
        listasum = Collections.synchronizedList(new ArrayList<>());
        ListaSum l = new ListaSum();
        listasum.add(l);
    }
    

    public void init() {
        ostatniaanalityka = kontoDAOfk.findKontaOstAlityka(wpisView);
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBez0(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        wszystkiekonta = new ArrayList<>(wykazkont);
        pobierzzapisy(wpisView.getRokWpisuSt());
        usunkontabezsald();
        wybierzgrupekont();
//        if (wykazkont != null) {
//            wybranekonto = wykazkont.get(0);
//        }
    }
    
    public void publicinit() {
       ostatniaanalityka = kontoDAOfk.findKontaOstAlityka(wpisView);
       wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBez0(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        if (wykazkont != null) {
            wybranekonto = wykazkont.get(0);
            usunkontabezsald();
        }
        wybierzgrupekont();
    }
    
    private void wybierzgrupekont() {
         if (wybranyRodzajKonta != null) {
            if (wybranyRodzajKonta.equals("bilansowe")) {
                for (Iterator<Konto> it = wykazkont.iterator(); it.hasNext();) {
                    if (it.next().getBilansowewynikowe().equals("wynikowe")) {
                        it.remove();
                    }
                }
            } else if (wybranyRodzajKonta.equals("wynikowe")) {
                for (Iterator<Konto> it = wykazkont.iterator(); it.hasNext();) {
                    if (it.next().getBilansowewynikowe().equals("bilansowe")) {
                        it.remove();
                    }
                }
            }
        }
    }
    
    private void usunkontabezsald() {
        List<Konto> konta = Collections.synchronizedList(new ArrayList<>());
        
//        zapisyRok.stream().filter((p) -> (p.getKonto() != null)).forEach((p) -> {
//            konta.add(p.getKonto());
//        });
        List<Konto> listakont = pobierzkontazsaldem(wpisView.getRokWpisuSt());
        Set<Konto> listamacierzyste = wyluskajmacierzyste(listakont);
        wykazkont = Collections.synchronizedList(new ArrayList<>());
        wykazkont.addAll(listakont);
        wykazkont.addAll(listamacierzyste);
    }
    
    private Set<Konto> wyluskajmacierzyste(List<Konto> listakont) {
        Set<Konto> listamacierzyste = new HashSet<>();
        listakont.stream().map((p) -> p.getKontomacierzyste()).forEachOrdered((m) -> {
            while (m != null) {
                listamacierzyste.add(m);
                m = m.getKontomacierzyste();
            }
        });
        return listamacierzyste;
    }
    
    public void pobierzzapisy(String rok) {
        List<StronaWiersza> zapisy = Collections.synchronizedList(new ArrayList<>());
        try {
            zapisy = stronaWierszaDAO.findStronaByPodatnikRok(wpisView.getPodatnikObiekt(), rok);
        } catch (Exception e) {
            E.e(e);
        }
        zapisyRok = zapisy;
    }
    
     public List<Konto> pobierzkontazsaldem(String rok) {
        List<Konto> zapisy = Collections.synchronizedList(new ArrayList<>());
        try {
            zapisy = stronaWierszaDAO.findStronaByPodatnikRokKontoDist(wpisView.getPodatnikObiekt(), rok);
        } catch (Exception e) {
            E.e(e);
        }
        return zapisy;
    }
    
    
    
    
    public void pobierzZapisyNaKoncieNode(Konto wybraneKontoNode) {
        pokaztransakcje = false;
        nierenderujkolumnnywalut = true;
        try {
            wybranezapisydosumowania = Collections.synchronizedList(new ArrayList<>());
            kontozapisyfiltered = null;
            wybranekonto = serialclone.SerialClone.clone(wybraneKontoNode);
            kontozapisy = Collections.synchronizedList(new ArrayList<>());
            String rok = pobierzrokpoprzedni ? wpisView.getRokUprzedniSt() : wpisView.getRokWpisuSt();
            List<StronaWiersza> zapisyshort = stronaWierszaDAO.findStronaByPodatnikKontoStartRokWszystkie(wpisView.getPodatnikObiekt(), wybranekonto, rok);
            if (zapisyshort!=null) {
                List<Konto> kontapotomnetmp = Collections.synchronizedList(new ArrayList<>());
                List<Konto> kontapotomneListaOstateczna = Collections.synchronizedList(new ArrayList<>());
                kontapotomnetmp.add(wybranekonto);
                KontaFKBean.pobierzKontaPotomne(kontapotomnetmp, kontapotomneListaOstateczna, wykazkont);
                int granicaDolna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacOd());
                int granicaGorna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacDo());
                List<StronaWiersza> zapisyshortfilter = zapisyshort.stream().filter((r) -> (kontapotomneListaOstateczna.contains(r.getKonto()))).collect(Collectors.toList());
                 zapisyshortfilter.parallelStream().forEach((r) -> {
                    if (wybranaWalutaDlaKont.equals("wszystkie")) {
                        int mc = Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac());
                        if (mc >= granicaDolna && mc <= granicaGorna) {
                            kontozapisy.add(r);
                        }
                    } else {
                        if (r.getSymbolWalutBOiSW().equals(wybranaWalutaDlaKont)) {
                            int mc = Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac());
                            if (mc >= granicaDolna && mc <= granicaGorna) {
                                kontozapisy.add(r);
                            }
                        }
                    }
                });
                for (StronaWiersza p : kontozapisy) {
                    if (!p.getSymbolWalutBOiSW().equals("PLN")) {
                        nierenderujkolumnnywalut = false;
                        break;
                    }
                }
                sumazapisow();
                sumazapisowpln();
                //wybranekontoNode = (TreeNodeExtended<Konto>) odnajdzNode(wybranekonto);
                Msg.msg("Pobrano zapisy dla konta "+wybraneKontoNode.getPelnynumer());
            }
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public void pobierzZapisyNaKoncieRokPop() {
        if (wybranekonto instanceof Konto) {
            if (pobierzrokpoprzedni) {
                pobierzzapisy(wpisView.getRokUprzedniSt());
                pobierzZapisyNaKoncieNode(wybranekonto);
                pobierzzapisy(wpisView.getRokWpisuSt());
                Msg.msg("Rok "+wpisView.getRokUprzedniSt());
            } else {
                pobierzZapisyNaKoncieNode(wybranekonto);
                Msg.msg("Rok "+wpisView.getRokWpisuSt());
            }
        }
    }
    
    public void pobierzZapisyNaKoncieNodeRozrachunki(Konto wybraneKontoNode) {
        if (wykazkont == null) {
            init();
        }
        try {
            wybranezapisydosumowania = Collections.synchronizedList(new ArrayList<>());
            wybranekonto = serialclone.SerialClone.clone(wybraneKontoNode);
            kontozapisy = Collections.synchronizedList(new ArrayList<>());
            List<Konto> kontapotomnetmp = Collections.synchronizedList(new ArrayList<>());
            List<Konto> kontapotomneListaOstateczna = Collections.synchronizedList(new ArrayList<>());
            kontapotomnetmp.add(wybranekonto);
            KontaFKBean.pobierzKontaPotomne(kontapotomnetmp, kontapotomneListaOstateczna, wykazkont);
            int granicaDolna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacOd());
            int granicaGorna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacDo());
            zapisyRok.stream().filter((r) -> (kontapotomneListaOstateczna.contains(r.getKonto()))).forEachOrdered((r) -> {
                int mc = Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac());
                if (mc >= granicaDolna && mc <=granicaGorna) {
                    kontozapisy.add(r);
                }
            });
            sumazapisow();
            sumazapisowpln();
            //wybranekontoNode = (TreeNodeExtended<Konto>) odnajdzNode(wybranekonto);
        } catch (Exception e) {
            E.e(e);
        }
    }
    

    
    
    public void zapisykontmiesiace() {
         wpisView.wpisAktualizuj();
         if (wybranekonto!=null && wybranekonto.getPodatnik()!=null) {
            pobierzZapisyZmianaWaluty();
            sumazapisow();
            sumazapisowpln();
         }
    }
    
    
    public void pobierzZapisyZmianaWaluty() {
        if (wybranekonto != null) {
            pobierzZapisyNaKoncieNode(wybranekonto);
        } else {
            Msg.msg("e", "Nie wybrano konta");
        }
    }
    
    
     
    public void pobierzZapisyNaKoncieNodeUnselect() {
        kontozapisy.clear();
    }
    
    
//    public void pobierzZapisyNaKoncie() {
//        if (wybranekonto instanceof Konto) {
//            kontozapisy = Collections.synchronizedList(new ArrayList<>());
//            List<Konto> kontapotomnetmp = Collections.synchronizedList(new ArrayList<>());
//            List<Konto> kontapotomneListaOstateczna = Collections.synchronizedList(new ArrayList<>());
//            kontapotomnetmp.add(wybranekonto);
//            KontaFKBean.pobierzKontaPotomne(kontapotomnetmp, kontapotomneListaOstateczna, wykazkont);
//            int granicaDolna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacOd());
//            int granicaGorna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacDo());
//            for (StronaWiersza r : zapisyRok) {
//                if (kontapotomneListaOstateczna.contains(r.getKonto())) {
//                    int mc = Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac());
//                    if (mc >= granicaDolna && mc <= granicaGorna) {
//                        kontozapisy.add(r);
//                    }
//                }
//            }
//            sumazapisow();
//            sumazapisowpln();
//            System.out.println("odnalazlem pobierzZapisyNaKoncie() kontoZapisFKView");
//        }
//    }
    
    
    public void reversetoggle(ActionEvent e) {
        DataTable d = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("tabelazzapisami:tabela");
        List<Object> sel = (List<Object>) d.getSelection();
        List<StronaWiersza> ko = Collections.synchronizedList(new ArrayList<>());
        ko.addAll(kontozapisy);
        for (Iterator it = sel.iterator(); it.hasNext();) {
            StronaWiersza n = (StronaWiersza) it.next();
            if (ko.contains(n)) {
                ko.remove(n);
            }
        }
        wybranezapisydosumowania = Collections.synchronizedList(new ArrayList<>());
        wybranezapisydosumowania.addAll(ko);
        sumazapisowtotal();
    } 
    
    public void toggleAll(ActionEvent e) {
        DataTable d = (DataTable) FacesContext.getCurrentInstance().getViewRoot().findComponent("tabelazzapisami:tabela");
        List<Object> sel = (List<Object>) d.getSelection();
        int selsize = sel.size();
        int kontosize = kontozapisy.size();
        if (selsize==kontosize) {
            wybranezapisydosumowania = Collections.synchronizedList(new ArrayList<>());
        } else {
            wybranezapisydosumowania = Collections.synchronizedList(new ArrayList<>());
            wybranezapisydosumowania.addAll(kontozapisy);
        }
        sumazapisowtotal();
    } 
    
   
   public void sumazapisowtotal2(AjaxBehaviorEvent e) {
        DataTable d = (DataTable) e.getSource();
        //Msg.msg("numer "+d.getSelection());
        sumazapisow();
        sumazapisowpln();
        //odszukajsparowanerozrachunki();
    }
    
    public void sumazapisowtotal() {
        sumazapisow();
        sumazapisowpln();
        //odszukajsparowanerozrachunki();
    }
    
    private TreeNode odnajdzNode(Konto kontoPoszukiwane) {
//        TreeNodeExtended<Konto> root = planKontView.getRoot();
//        List<TreeNode> konta = root.getChildren();
//        for (int i = 0; i < konta.size(); i++) {
//            if (((Konto) konta.get(i).getData()).equals(kontoPoszukiwane)) {
//                return konta.get(i);
//            } else {
//                odnajdzNode((Konto) konta.get(i).getData());
//            }
//        }
        return null;
    }
      
      private List<Konto> pobierzpotomkow(Konto macierzyste) {
          try {
              return kontoDAOfk.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(),macierzyste.getPelnynumer());
          } catch (Exception e) {  E.e(e);
              Msg.msg("e", "nie udane pobierzpotomkow");
          }
          return null;
      }
      
      private void znajdzkontazpotomkami(List<Konto> kontapotomne, List<Konto> kontamacierzyste) {
          List<Konto> listakontposrednia = Collections.synchronizedList(new ArrayList<>());
          Iterator it = kontamacierzyste.iterator();
          while(it.hasNext()) {
              Konto p = (Konto) it.next();
              if (p.isMapotomkow()) {
                  listakontposrednia.addAll(pobierzpotomkow(p));
                  it.remove();
              } else {
                  kontapotomne.add(p);
                  it.remove();
              }
          }
          kontamacierzyste.addAll(listakontposrednia);
      }
    
      
    public void sumazapisow(){
        try {
            sumaWn = 0.0;
            sumaMa = 0.0;
            DoubleAccumulator  wn = new DoubleAccumulator(Double::sum,0.d);
            DoubleAccumulator  ma = new DoubleAccumulator(Double::sum,0.d);
            if (wybranezapisydosumowania != null && wybranezapisydosumowania.size() > 0) {
                wybranezapisydosumowania.parallelStream().forEach((p) -> {
                    sumujstrony(wn, ma, p);
                });
            } else if (kontozapisyfiltered != null && kontozapisyfiltered.size() > 0) {
                kontozapisyfiltered.parallelStream().forEach((p) -> {
                    sumujstrony(wn, ma, p);
                });
            } else {
                kontozapisy.parallelStream().forEach((p) -> {
                    sumujstrony(wn, ma, p);
                });
            }
            sumaWn = Z.z(sumaWn+wn.doubleValue());
            sumaMa = Z.z(sumaMa+ma.doubleValue());
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
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Brak tabeli NBP w dokumencie. Podsumowanie nie jest prawidłowe. KontoZapisFVView sumazapisow()");
        }
    }
    
    public void sumazapisowAut(){
        try {
            sumaWn = 0.0;
            sumaMa = 0.0;
            DoubleAccumulator  wn = new DoubleAccumulator(Double::sum,0.d);
            DoubleAccumulator  ma = new DoubleAccumulator(Double::sum,0.d);
            kontozapisy.stream().forEach((p) -> {
                sumujstrony(wn, ma, p);
            });
            sumaWn = Z.z(sumaWn+wn.doubleValue());
            sumaMa = Z.z(sumaMa+ma.doubleValue());
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
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Brak tabeli NBP w dokumencie. Podsumowanie nie jest prawidłowe. KontoZapisFVView sumazapisow()");
        }
    }
    
    private void sumujstrony(DoubleAccumulator  wn, DoubleAccumulator  ma, StronaWiersza p) {
        if (p.getWnma().equals("Wn")) {
            if (wybranaWalutaDlaKont.equals("wszystkie")) {
                if (!p.getSymbolWalutBOiSW().equals("PLN")) {
                    sumawiersz(wn, p);
                }
            } else {
                sumawiersz(wn, p);
            }
        } else if (p.getWnma().equals("Ma")) {
            if (wybranaWalutaDlaKont.equals("wszystkie")) {
                if (!p.getSymbolWalutBOiSW().equals("PLN")) {
                    sumawiersz(ma, p);
                }
            } else {
                sumawiersz(ma,p);
            }
        }
    }
    
    private void sumawiersz(DoubleAccumulator  wn, StronaWiersza p) {
        if (pokaztransakcje) {
            wn.accumulate(p.getPozostalo());
        } else {
            wn.accumulate(p.getKwota());
        }
    }
    
        
    public void sumazapisowpln(){
        sumaWnPLN = 0.0;
        sumaMaPLN = 0.0;
        DoubleAccumulator  wn = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator  ma = new DoubleAccumulator(Double::sum,0.d);
        if (wybranezapisydosumowania != null && wybranezapisydosumowania.size() > 0) {
            wybranezapisydosumowania.parallelStream().forEach((p) -> {
                double kwotadlasumy = pokaztransakcje ? p.getPozostalo() : p.getKwotaPLN();
                if (p.getWnma().equals("Wn")) {
                    wn.accumulate(kwotadlasumy);
                } else if (p.getWnma().equals("Ma")){
                    ma.accumulate(kwotadlasumy);
                }
            });
        } else if (kontozapisyfiltered != null && kontozapisyfiltered.size() > 0) {
            kontozapisyfiltered.parallelStream().forEach((p) -> {
                double kwotadlasumy = pokaztransakcje ? p.getPozostaloPLN() : p.getKwotaPLN();
                if (p.getWnma().equals("Wn")) {
                    wn.accumulate(kwotadlasumy);
                } else if (p.getWnma().equals("Ma")){
                    ma.accumulate(kwotadlasumy);
                }
            });
        }  else {
            kontozapisy.parallelStream().forEach((p) -> {
                double kwotadlasumy = pokaztransakcje ? p.getPozostaloPLN() : p.getKwotaPLN();
                if (p.getWnma().equals("Wn")) {
                    wn.accumulate(kwotadlasumy);
                } else if (p.getWnma().equals("Ma")){
                    ma.accumulate(kwotadlasumy);
                }
            });
        }
        sumaWnPLN = Z.z(wn.doubleValue());
        sumaMaPLN = Z.z(ma.doubleValue());
        saldoWnPLN = 0.0;
        saldoMaPLN = 0.0;
        if(sumaWnPLN>sumaMaPLN){
            Z.z(saldoWnPLN = sumaWnPLN-sumaMaPLN);
        } else {
            Z.z(saldoMaPLN = sumaMaPLN-sumaWnPLN);
        }
        listasum.get(0).setSumaWnPLN(sumaWnPLN);
        listasum.get(0).setSumaMaPLN(sumaMaPLN);
        listasum.get(0).setSaldoWnPLN(saldoWnPLN);
        listasum.get(0).setSaldoMaPLN(saldoMaPLN);
    }
    
    public void sumazapisowplnAut(){
        sumaWnPLN = 0.0;
        sumaMaPLN = 0.0;
        kontozapisy.stream().forEach((p)->{
            if (p.getWnma().equals("Wn")) {
                Z.z(sumaWnPLN = sumaWnPLN + p.getKwotaPLN());
            } else if (p.getWnma().equals("Ma")){
                Z.z(sumaMaPLN = sumaMaPLN + p.getKwotaPLN());
            }
        });
        saldoWnPLN = 0.0;
        saldoMaPLN = 0.0;
        if(sumaWnPLN>sumaMaPLN){
            Z.z(saldoWnPLN = sumaWnPLN-sumaMaPLN);
        } else {
            Z.z(saldoMaPLN = sumaMaPLN-sumaWnPLN);
        }
        listasum.get(0).setSumaWnPLN(sumaWnPLN);
        listasum.get(0).setSumaMaPLN(sumaMaPLN);
        listasum.get(0).setSaldoWnPLN(saldoWnPLN);
        listasum.get(0).setSaldoMaPLN(saldoMaPLN);
    }
    
    private  Map<String, ListaSum> sumujzapisyPLN() {
        Map<String, ListaSum> zbiorcza = stworzlisteSumwWalutachPLN(kontozapisy);
        ListaSum rpln = zbiorcza.get("PLN");
        for (StronaWiersza p : kontozapisy) {
            if (rpln != null) {
                if (p.getWnma().equals("Wn")) {
                    rpln.setSumaWn(Z.z(rpln.getSumaWn() + p.getKwotaPLN()));
                    rpln.setSumaWnPLN(Z.z(rpln.getSumaWnPLN()+ p.getKwotaPLN()));
                } else if (p.getWnma().equals("Ma")) {
                    rpln.setSumaMa(Z.z(rpln.getSumaMa() + p.getKwotaPLN()));
                    rpln.setSumaMaPLN(Z.z(rpln.getSumaMaPLN()+ p.getKwotaPLN()));
                }
            }
        }
        if (rpln.getSumaWn() > rpln.getSumaMa()) {
            rpln.setSaldoWn(Z.z(rpln.getSumaWn() - rpln.getSumaMa()));
        } else {
            rpln.setSaldoMa(Z.z(rpln.getSumaMa() - rpln.getSumaWn()));
        }
        if (rpln.getSumaWnPLN()> rpln.getSumaMaPLN()) {
            rpln.setSaldoWnPLN(Z.z(rpln.getSumaWnPLN()- rpln.getSumaMaPLN()));
        } else {
            rpln.setSaldoMaPLN(Z.z(rpln.getSumaMaPLN()- rpln.getSumaWnPLN()));
        }
        return zbiorcza;
    }
    
    private  Map<String, ListaSum> sumujzapisy() {
        Map<String, ListaSum> zbiorcza = stworzlisteSumwWalutach(kontozapisy);
        for (StronaWiersza p : kontozapisy) {
            ListaSum r = zbiorcza.get(p.getSymbolWalutBOiSW());
            r.setTabelanbp(p.getWiersz().getTabelanbp());
            r.setWalutabo(p.getWierszbo() != null ? p.getWierszbo().getWaluta(): null);
            r.setKurswaluty(p.getKursWalutyBOSW());
            if (r != null && !r.getWaluta().equals("PLN")) {
                if (p.getWnma().equals("Wn")) {
                    r.setSumaWn(Z.z(r.getSumaWn() + p.getKwota()));
                    r.setSumaWnPLN(Z.z(r.getSumaWnPLN()+ p.getKwotaPLN()));
                } else if (p.getWnma().equals("Ma")) {
                    r.setSumaMa(Z.z(r.getSumaMa() + p.getKwota()));
                    r.setSumaMaPLN(Z.z(r.getSumaMaPLN()+ p.getKwotaPLN()));
                }
            }
            ListaSum rpln = zbiorcza.get("PLN");
            if (rpln != null) {
                if (p.getWnma().equals("Wn")) {
                    rpln.setSumaWn(Z.z(rpln.getSumaWn() + p.getKwotaPLN()));
                    rpln.setSumaWnPLN(Z.z(rpln.getSumaWnPLN()+ p.getKwotaPLN()));
                } else if (p.getWnma().equals("Ma")) {
                    rpln.setSumaMa(Z.z(rpln.getSumaMa() + p.getKwotaPLN()));
                    rpln.setSumaMaPLN(Z.z(rpln.getSumaMaPLN()+ p.getKwotaPLN()));
                }
            }
        }
        ListaSum rpln = zbiorcza.get("PLN");
        for (ListaSum s : zbiorcza.values()) {
            if (!s.getWaluta().equals("PLN")) {
                if (s.getSumaWn() > s.getSumaMa()) {
                    s.setSaldoWn(Z.z(s.getSumaWn() - s.getSumaMa()));
                } else {
                    s.setSaldoMa(Z.z(s.getSumaMa() - s.getSumaWn()));
                }
                if (s.getSumaWnPLN()> s.getSumaMaPLN()) {
                    s.setSaldoWnPLN(Z.z(s.getSumaWnPLN()- s.getSumaMaPLN()));
                } else {
                    s.setSaldoMaPLN(Z.z(s.getSumaMaPLN()- s.getSumaWnPLN()));
                }
                if (s.getSaldoWn() > 0.0) {
                    rpln.setSumaWn(Z.z(rpln.getSumaWn() - s.getSaldoWnPLN()));
                    rpln.setSumaWnPLN(Z.z(rpln.getSumaWnPLN() - s.getSaldoWnPLN()));
                } else if (s.getSaldoMa() > 0.0) {
                    rpln.setSumaMa(Z.z(rpln.getSumaMa() - s.getSaldoMaPLN()));
                    rpln.setSumaMaPLN(Z.z(rpln.getSumaMaPLN() - s.getSaldoMaPLN()));
                }
            }
        }
        if (rpln.getSumaWn() > rpln.getSumaMa()) {
            rpln.setSaldoWn(Z.z(rpln.getSumaWn() - rpln.getSumaMa()));
        } else {
            rpln.setSaldoMa(Z.z(rpln.getSumaMa() - rpln.getSumaWn()));
        }
        if (rpln.getSumaWnPLN()> rpln.getSumaMaPLN()) {
            rpln.setSaldoWnPLN(Z.z(rpln.getSumaWnPLN()- rpln.getSumaMaPLN()));
        } else {
            rpln.setSaldoMaPLN(Z.z(rpln.getSumaMaPLN()- rpln.getSumaWnPLN()));
        }
        return zbiorcza;
    }
    
    private  double[] sumujzapisyKontobankowe() {
        double saldowalutywn = listasum.get(0).getSaldoWn();
        double saldowalutyma = listasum.get(0).getSaldoMa();
        String waluta = wybranaWalutaDlaKont;
        double sumazliczanie = 0.0;
        double sumazliczaniePLN = 0.0;
        double sumagranica = Z.z(saldowalutywn) > 0.0 ? Z.z(saldowalutywn) : Z.z(saldowalutyma);
        double saldowalutywnpln = listasum.get(0).getSaldoWnPLN();
        double saldowalutymapln = listasum.get(0).getSaldoMaPLN();
        double roznicawn = 0.0;
        double roznicama = 0.0;
        for (Iterator<StronaWiersza> it = new ReverseIterator<StronaWiersza>(kontozapisy).iterator(); it.hasNext();) {
            StronaWiersza p = it.next();
            if (!p.getSymbolWalutBOiSW().equals("PLN")) {
                if (saldowalutywn > 0.0) {
                    if (p.isWn()) {
                        if (p.getKwota() < (sumagranica-sumazliczanie)) {
                            sumazliczanie += p.getKwota();
                            sumazliczaniePLN += p.getKwotaPLN();
                        } else {
                            double ilezostalo = (sumagranica-sumazliczanie);
                            double proporcja = ilezostalo/p.getKwota();
                            sumazliczaniePLN += Z.z(p.getKwotaPLN()*proporcja);
                            roznicawn = saldowalutywnpln > 0.0 ? Z.z(saldowalutywnpln-sumazliczaniePLN) : -Z.z(saldowalutymapln+sumazliczaniePLN);
                            break;
                        }

                    }
                }
                if (saldowalutyma > 0.0) {
                    if (!p.isWn()) {
                        if (p.getKwota() < (sumagranica-sumazliczanie)) {
                            sumazliczanie += p.getKwota();
                            sumazliczaniePLN += p.getKwotaPLN();
                        } else {
                            double ilezostalo = (sumagranica-sumazliczanie);
                            double proporcja = ilezostalo/p.getKwota();
                            sumazliczaniePLN += Z.z(p.getKwotaPLN()*proporcja);
                            roznicama = saldowalutymapln > 0.0 ? Z.z(saldowalutymapln-sumazliczaniePLN) : -Z.z(saldowalutywnpln+sumazliczaniePLN);
                            break;
                        }

                    }
                }
                //gdy nie ma walut ale zostalo saldo w pln
                if (saldowalutywn == 0.0 && saldowalutyma == 0.0 && (saldowalutywnpln > 0.0 || saldowalutymapln > 0.0)) {
                    if (saldowalutywnpln > 0.0) {
                        roznicawn = saldowalutywnpln;
                    }
                    if (saldowalutymapln > 0.0) {
                        roznicama = saldowalutymapln;
                    }
                }
            }
        }
        if (roznicawn < 0.0) {
            roznicama = -roznicawn;
            roznicawn = 0.0;
        } else if (roznicama < 0.0) {
            roznicawn = -roznicama;
            roznicama = 0.0;
        }
        return new double[]{roznicawn,roznicama};
    }
    
    public void rozliczzaznaczone() {
        if (wybranezapisydosumowania != null && wybranezapisydosumowania.size() > 1) {
            if (wybranezapisydosumowania.size()==2 && RozliczTransakcjeBean.sprawdzczyjestkorekta(wybranezapisydosumowania)==null) {
                Msg.msg("e", "Jedna z pozyji to korekta, naniesiono oznaczenia");
            }
            if (wybranezapisydosumowania.size()==2 && RozliczTransakcjeBean.sprawdznowatransakcje(wybranezapisydosumowania)==null) {
                RozliczTransakcjeBean.wybierzjednatransakcje(wybranezapisydosumowania);
            }
            if (RozliczTransakcjeBean.sprawdznowatransakcje(wybranezapisydosumowania)==null) {
                Msg.msg("e", "Żadna z pozycji nie jest oznaczona jako rachunek. Nie można zrobić szybkiego rozliczenia");
            } else if (RozliczTransakcjeBean.wiecejnizjednatransakcja(wybranezapisydosumowania)) {
                Msg.msg("e", "Wśród wybranych wierszy znajdują sie dwie nowe transakcje/faktury. Nie można rozliczyć zapisów. Zmień oznaczenie w zakładce Rozrachunki");
            } else if (czyroznewaluty(wybranezapisydosumowania)){
                Msg.msg("e", "Zaznaczone dokumenty są różnowalutowe. Nie można zrobić szybkiego rozliczenia");
            } else {
                Msg.msg("Rozliczam oznaczone transakcje");
                List<StronaWiersza> listapoedycji = RozliczTransakcjeBean.naniestransakcje(wybranezapisydosumowania);
                stronaWierszaDAO.editList(listapoedycji);
                for (StronaWiersza p : wybranezapisydosumowania) {
                    if (Z.z(p.getPozostalo()) == 0.0) {
                        kontozapisy.remove(p);
                    }
                }
            }
        } else {
            Msg.msg("e", "Należy wybrać przynajmniej dwa zapisy po różnych stronach konta w celu rozliczenia transakcji");
        }
    }
    private boolean czyroznewaluty(List<StronaWiersza> l) {
        boolean zwrot = false;
        if (l != null) {
            String waluta = l.get(0).getSymbolWalutBOiSW();
            for (StronaWiersza p : l) {
                if (!waluta.equals(p.getSymbolWalutBOiSW())) {
                    zwrot = true;
                }
            }
        } else {
            zwrot = true;
        }
        return zwrot;
    }
    public void rozliczsaldo() {
        if (kontozapisy != null && kontozapisy.size() > 0) {
            Map<String, ListaSum> listasum = sumujzapisy();
            Dokfk nowydok = DokumentFKBean.generujdokumentAutomRozrach(wpisView, klienciDAO, "ARS", "automatyczne rozliczenie salda konta", rodzajedokDAO, tabelanbpDAO, kontoDAOfk, kontozapisy, listasum, dokDAOfk, cechazapisuDAOfk);
            dokDAOfk.dodaj(nowydok);
            dodajzapisy(kontozapisy.get(0).getKonto(), nowydok);
            this.listasum = Collections.synchronizedList(new ArrayList<>());
            ListaSum l = new ListaSum();
            this.listasum.add(l);
            sumazapisowAut();
            sumazapisowplnAut();
            Msg.msg("Dodano rozliczenie konta");
        } else {
            Msg.msg("e", "Nie ma czego rozliczać, Lista zapisów jest pusta.");
        }
    }
    public void rozliczsaldopln() {
        if (kontozapisy != null && kontozapisy.size() > 0) {
            Map<String, ListaSum> listasum = sumujzapisyPLN();
            Dokfk nowydok = DokumentFKBean.generujdokumentAutomRozrach(wpisView, klienciDAO, "ARS", "automatyczne rozliczenie salda konta", rodzajedokDAO, tabelanbpDAO, kontoDAOfk, kontozapisy, listasum, dokDAOfk, cechazapisuDAOfk);
            dokDAOfk.dodaj(nowydok);
            dodajzapisy(kontozapisy.get(0).getKonto(), nowydok);
            this.listasum = Collections.synchronizedList(new ArrayList<>());
            ListaSum l = new ListaSum();
            this.listasum.add(l);
            sumazapisowAut();
            sumazapisowplnAut();
            Msg.msg("Dodano rozliczenie konta");
        } else {
            Msg.msg("e", "Nie ma czego rozliczać, Lista zapisów jest pusta.");
        }
    }
    
    public void rozliczkontobankowe() {
        if (!wpisView.getMiesiacDo().equals(wpisView.getMiesiacWpisu())) {
            Msg.msg("e", "Miesiąc 'do' nie jest taki sam jak bieżący miesiąc wpisu. Nie można rozliczyć salda.");
        } else {
            if (kontozapisy != null && kontozapisy.size() > 0) {
                double[] roznicawnroznicama = sumujzapisyKontobankowe();
                if (roznicawnroznicama[0] > 0.0 || roznicawnroznicama[1] > 0.0) {
                    String opis = "automatyczne rozliczenie walut na koncie bankowym na koniec "+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisu();
                    Dokfk nowydok = DokumentFKBean.generujdokumentAutomSaldo(wpisView, klienciDAO, "ARS", opis, rodzajedokDAO, tabelanbpDAO, kontoDAOfk, kontozapisy, roznicawnroznicama, dokDAOfk);
                    dokDAOfk.dodaj(nowydok);
                    dodajzapisy(kontozapisy.get(0).getKonto(), nowydok);
                    this.listasum = Collections.synchronizedList(new ArrayList<>());
                    ListaSum l = new ListaSum();
                    this.listasum.add(l);
                    sumazapisowAut();
                    sumazapisowplnAut();
                    Msg.msg("Dodano rozliczenie walut konta bankowego");
                } else {
                    Msg.msg("e", "Różnice wynoszą zero. Dokument niezaksięgowany");
                }
            } else {
                Msg.msg("e", "Nie ma czego rozliczać, Lista zapisów jest pusta.");
            }
        }
    }
   
   private Map<String, ListaSum> stworzlisteSumwWalutach(List<StronaWiersza> kontozapisy) {
        Map<String, ListaSum> zbiorcza = new ConcurrentHashMap<>();
        Set<String> waluty = new HashSet<>();
        for (StronaWiersza p : kontozapisy) {
            waluty.add(p.getSymbolWalutBOiSW());
        }
        for (String r : waluty) {
            ListaSum l = new ListaSum();
            l.setWaluta(r);
            zbiorcza.put(r, l);
        }
        return zbiorcza;
    }
   
   private Map<String, ListaSum> stworzlisteSumwWalutachPLN(List<StronaWiersza> kontozapisy) {
        Map<String, ListaSum> zbiorcza = new ConcurrentHashMap<>();
        Set<String> waluty = new HashSet<>();
        waluty.add( "PLN");
        for (String r : waluty) {
            ListaSum l = new ListaSum();
            l.setWaluta(r);
            zbiorcza.put(r, l);
        }
        return zbiorcza;
    }
    
   
   private void dodajzapisy(Konto konto, Dokfk nowydok) {
       for (StronaWiersza p : nowydok.getStronyWierszy())
           if (p.getKonto() == konto) {
               kontozapisy.add(p);
               zapisyRok.add(p);
           }
   }
    
    //poszukuje rozrachunkow do sparowania
    public void odszukajsparowanerozrachunki() {
        try {
            StronaWiersza wybranyrozrachunek = wybranezapisydosumowania.get(0);
            if (wybranyrozrachunek.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
                List<Transakcja> listytransakcjiNT = transakcjaDAO.findByNowaTransakcja(wybranyrozrachunek);
                List<Transakcja> listytransakcjiR = transakcjaDAO.findByRozliczajacy(wybranyrozrachunek);
                zapisydopodswietlenia = Collections.synchronizedList(new ArrayList<>());
                if (!listytransakcjiNT.isEmpty()) {
                    for (Transakcja p :listytransakcjiNT) {
                        zapisydopodswietlenia.add(p.getRozliczajacy().getId());
                    }
                }
                if (!listytransakcjiR.isEmpty()) {
                    for (Transakcja p :listytransakcjiR) {
                        zapisydopodswietlenia.add(p.getNowaTransakcja().getId());
                    }
                }
                //wyszukujemy roznice kursowe do podswietlenia
                for (StronaWiersza r : kontozapisy) {
                        if (r.getWnma().equals("Wn")) {
                        if (r.getWiersz().getStronaMa() != null && r.getWiersz().getStronaMa().getKonto().getPelnynumer().equals("755")) {
                            if (r.getKonto().equals(wybranyrozrachunek.getKonto()) && r.getWiersz().getOpisWiersza().contains(String.valueOf(wybranyrozrachunek.getId()))) {
                                zapisydopodswietlenia.add(r.getId());
                            }
                        }
                    } else {
                        if (r.getWiersz().getStronaWn() != null && r.getWiersz().getStronaWn().getKonto().getPelnynumer().equals("755")) {
                            if (r.getKonto().equals(wybranyrozrachunek.getKonto()) && r.getWiersz().getOpisWiersza().contains(String.valueOf(wybranyrozrachunek.getId()))) {
                                zapisydopodswietlenia.add(r.getId());
                            }
                        }
                    }
                }
                RequestContext.getCurrentInstance().update("zapisydopodswietlenia");
                RequestContext.getCurrentInstance().execute("podswietlrozrachunki();");
            }
        } catch (Exception e) {  E.e(e);
            
        }
    }
   
    
    public int sortZapisynaKoncie(Object o1, Object o2) {
        String datao1 = ((StronaWiersza) o1).getWiersz().getDokfk().getDatadokumentu();
        String datao2 = ((StronaWiersza) o2).getWiersz().getDokfk().getDatadokumentu();
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date datao1date = null;
        Date datao2date = null;
        try {
            datao1date = formatter.parse(datao1);
            datao2date = formatter.parse(datao2);
            if (datao1date.before(datao2date)) {
                return -1;
            } else if (datao1date.after(datao2date)) {
                return 1;
            } else {
                return porownajseriedok(((StronaWiersza) o1),((StronaWiersza) o2));
            }
        } catch (Exception e) {  E.e(e);
            return 0;
        }
    }
    
    private int porownajseriedok(StronaWiersza o1, StronaWiersza o2) {
        String seriao1 = o1.getWiersz().getDokfk().getSeriadokfk();
        String seriao2 = o2.getWiersz().getDokfk().getSeriadokfk();
        if (seriao1.equals(seriao2)) {
            return porownajnrserii(o1,o2);
        } else {
            return seriao1.compareTo(seriao2);
        }
    }
    
    private int porownajnrserii(StronaWiersza o1, StronaWiersza o2) {
        int seriao1 = o1.getWiersz().getDokfk().getNrkolejnywserii();
        int seriao2 = o2.getWiersz().getDokfk().getNrkolejnywserii();
        if (seriao1 == seriao2) {
            return porownajnumerwiersza(o1,o2);
        } else if (seriao1 < seriao2){
            return -1;
        } else {
            return 1;
        }
    }
    
    private int porownajnumerwiersza(StronaWiersza o1, StronaWiersza o2) {
        int seriao1 = o1.getWiersz().getIdporzadkowy();
        int seriao2 = o2.getWiersz().getIdporzadkowy();
        if (seriao1 == seriao2) {
            return 0;
        } else if (seriao1 < seriao2){
            return -1;
        } else {
            return 1;
        }
    }
    
    public void drukujPdfZapisyNaKoncie() {
        try {
            if (kontozapisyfiltered != null && kontozapisyfiltered.size() > 0) {
                sumazapisow();
                sumazapisowpln();
                PdfKontoZapisy.drukujzapisy(wpisView, kontozapisyfiltered, wybranekonto, listasum, true);
            } else if (wybranezapisydosumowania != null && wybranezapisydosumowania.size() > 0) {
                sumazapisow();
                sumazapisowpln();
                PdfKontoZapisy.drukujzapisy(wpisView, wybranezapisydosumowania, wybranekonto, listasum, true);
            } else {
                PdfKontoZapisy.drukujzapisy(wpisView, kontozapisy, wybranekonto, listasum, true);
            }
        } catch (Exception e) {  
            E.e(e);
        }
    }
    
     public void drukujPdfZapisyNaKoncieKompakt() {
        try {
            if (kontozapisyfiltered != null && kontozapisyfiltered.size() > 0) {
                sumazapisow();
                sumazapisowpln();
                PdfKontoZapisy.drukujzapisyKompakt(wpisView, kontozapisyfiltered, wybranekonto, listasum, 2, nierenderujkolumnnywalut);
            } else if (wybranezapisydosumowania != null && wybranezapisydosumowania.size() > 0) {
                sumazapisow();
                sumazapisowpln();
                PdfKontoZapisy.drukujzapisyKompakt(wpisView, wybranezapisydosumowania, wybranekonto, listasum, 2, nierenderujkolumnnywalut);
            } else {
                PdfKontoZapisy.drukujzapisyKompakt(wpisView, kontozapisy, wybranekonto, listasum, 2, nierenderujkolumnnywalut);
            }
        } catch (Exception e) {  
            E.e(e);
        }
    }
    
    
    public void drukujPdfZapisyNaKoncieDuzy() {
        try {
             if (kontozapisyfiltered != null && kontozapisyfiltered.size() > 0) {
                sumazapisow();
                sumazapisowpln();
                PdfKontoZapisy.drukujzapisy(wpisView, kontozapisyfiltered, wybranekonto, listasum, false);
            } else if (wybranezapisydosumowania != null && wybranezapisydosumowania.size() > 0) {
                sumazapisow();
                sumazapisowpln();
                PdfKontoZapisy.drukujzapisy(wpisView, wybranezapisydosumowania, wybranekonto, listasum, false);
            } else {
                PdfKontoZapisy.drukujzapisy(wpisView, kontozapisy, wybranekonto, listasum, false);
            }
        } catch (Exception e) {  E.e(e);

        }
    }
    
    
    public void usunPozycjeRozliczone() {
        try {
            pokaztransakcje = true;
            for (Iterator<StronaWiersza> it = kontozapisy.iterator(); it.hasNext(); ) {
                StronaWiersza sw = it.next();
                if (!wybranaWalutaDlaKont.equals("wszystkie") && !sw.getSymbolWalutBOiSW().equals(wybranaWalutaDlaKont)) {
                    it.remove();
                } else if (Z.z(sw.getPozostalo()) == 0.0 || sw.getDokfk().getRodzajedok().getSkrot().equals("RRK")) {
                    it.remove();
                }
            }
            sumazapisowtotal();
        } catch (Exception e) {  
            E.e(e);
        }
    }
    
    public void usunzListy() {
        for (StronaWiersza p : wybranezapisydosumowania) {
            kontozapisy.remove(p);
        }
        sumazapisowtotal();
    }
    
    public void usunzListyARS() {
        for (Iterator<StronaWiersza> it = kontozapisy.iterator();it.hasNext();) {
            try {
                StronaWiersza p = it.next();
                if (p.getDokfk().getSeriadokfk().equals("ARS")) {
                    dokDAOfk.destroy(p.getDokfk());
                    zapisyRok.remove(p);
                    it.remove();
                }
            } catch (Exception e){}
        }
        sumazapisowAut();
        sumazapisowplnAut();
        Msg.msg("Usunięto dok ASR");
    }
    
    public void usunroznicekursowe() {
        for (Iterator<StronaWiersza> it = kontozapisy.iterator(); it.hasNext();) {
            StronaWiersza p = it.next();
            if (p.isRoznicaKursowa()) {
                it.remove();
            }
        }
        sumazapisowtotal();
    }
    
    public void przeksieguj() {
        try {
            if (wybranekonto != null && wybranekonto.getId() != null && kontodoprzeksiegowania != null) {
                if (KontaFKBean.czytesamekonta(wybranekonto, kontodoprzeksiegowania) == 1) {
                    Msg.msg("e", "Konto źródłowe jest tożsame z docelowym, przerywam przeksięgowanie");
                    return;
                }
                if (wybranezapisydosumowania == null || wybranezapisydosumowania.isEmpty()) {
                    Msg.msg("e", "Nie wybrano pozycji do przeksięgowania. Nie można wykonać przeksięgowania");
                    return;
                }
                if (!wybranekonto.equals(kontodoprzeksiegowania) && wybranekonto.isMapotomkow() == true && wybranekonto.getIdslownika() == kontodoprzeksiegowania.getIdslownika()) {
                    przeksiegujslownikowe();
                } else {
                    przeksiegujanalityke();
                    Msg.msg("w", "Konto żrółowe/docelowe ma analitykę. Być może trzeba ją usunąć");
                }
            } else {
                Msg.msg("e", "Nie wybrałeś konta docelowego");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
    private void przeksiegujanalityke() {
        int rozrachunkowe = 0;
        int bo = 0;
        for (StronaWiersza p : wybranezapisydosumowania) {
            if (p.getWierszbo()==null) {
                if (p.getNowetransakcje().isEmpty() && p.getPlatnosci().isEmpty()) {
                    p.setKonto(kontodoprzeksiegowania);
                    stronaWierszaDAO.edit(p);
                } else {
                    rozrachunkowe++;
                }
            } else {
              bo++;  
            }
        }
        if (rozrachunkowe > 0) {
            Msg.msg("w", "Nie przeksiegowano pozycji z rozrachunkami w liczbie " + rozrachunkowe);
        }
        if (bo > 0) {
            Msg.msg("w", "Nie przeksiegowano pozycji z zapisami BO w liczbie " + bo);
        }
        kontodoprzeksiegowania = null;
        pobierzzapisy(wpisView.getRokWpisuSt());
        publicinit();
        pobierzZapisyNaKoncieNode(wybranekonto);
        Msg.dP();
    }
    
    private void przeksiegujslownikowe() {
        int rozrachunkowe = 0;
        int bo = 0;
        List<Konto> potomne = kontoDAOfk.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), kontodoprzeksiegowania.getPelnynumer());
        if (potomne == null || potomne.size() == 0) {
            Msg.msg("e", "Konto docelowe nie zawiera podłączonego słownika. Nie można przeksięgować");
            return;
        }
        int brakkonta = 0;
        for (StronaWiersza p : wybranezapisydosumowania) {
            if (p.getWierszbo()==null) {
                if (p.getNowetransakcje().isEmpty() && p.getPlatnosci().isEmpty()) {
                    Konto kontodocelowe = znajdzpotomne(potomne,p.getKonto());
                    if (kontodocelowe != null) {
                            p.setKonto(kontodocelowe);
                            stronaWierszaDAO.edit(p);
                    } else {
                        brakkonta++;
                    }
                } else {
                    rozrachunkowe++;
                }

            } else {
              bo++;  
            }
        }
        if (rozrachunkowe > 0) {
            Msg.msg("w", "Nie przeksiegowano pozycji z rozrachunkami w liczbie " + rozrachunkowe);
        }
        if (brakkonta > 0) {
            Msg.msg("w", "Nie przeksiegowano pozycji bo brakuje pozycji w słowniku " + brakkonta);
        }
        if (bo > 0) {
            Msg.msg("w", "Nie przeksiegowano pozycji z zapisami BO w liczbie " + bo);
        }
        kontodoprzeksiegowania = null;
        pobierzzapisy(wpisView.getRokWpisuSt());
        publicinit();
        pobierzZapisyNaKoncieNode(wybranekonto);
        Msg.dP();
    }
    
    private Konto znajdzpotomne(List<Konto> potomne, Konto konto) {
        Konto zwrot = null;
        for (Konto p : potomne) {
            if (p.getNrkonta().equals(konto.getNrkonta())) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }
    
//<editor-fold defaultstate="collapsed" desc="comment">
    
    
    public List<Konto> getWykazkont() {
        return wykazkont;
    }
    
    public void setWykazkont(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
    }

    public boolean isPobierzrokpoprzedni() {
        return pobierzrokpoprzedni;
    }

    public void setPobierzrokpoprzedni(boolean pobierzrokpoprzedni) {
        this.pobierzrokpoprzedni = pobierzrokpoprzedni;
    }

    public boolean isPokaztransakcje() {
        return pokaztransakcje;
    }

    public void setPokaztransakcje(boolean pokaztransakcje) {
        this.pokaztransakcje = pokaztransakcje;
    }
    
    public Konto getWybranekonto() {
        return wybranekonto;
    }
    
    public void setWybranekonto(Konto wybranekonto) {
        this.wybranekonto = wybranekonto;
    }
    
    public List<StronaWiersza> getKontozapisy() {
        return kontozapisy;
    }
    
    public void setKontozapisy(List<StronaWiersza> kontozapisy) {
        this.kontozapisy = kontozapisy;
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
    
    public List<StronaWiersza> getWybranezapisydosumowania() {
        return wybranezapisydosumowania;
    }
    
    public void setWybranezapisydosumowania(List<StronaWiersza> wybranezapisydosumowania) {
        this.wybranezapisydosumowania = wybranezapisydosumowania;
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
    
    public Double getSumaWnPLN() {
        return sumaWnPLN;
    }
    
    public void setSumaWnPLN(Double sumaWnPLN) {
        this.sumaWnPLN = sumaWnPLN;
    }
    
    public Double getSumaMaPLN() {
        return sumaMaPLN;
    }
    
    public void setSumaMaPLN(Double sumaMaPLN) {
        this.sumaMaPLN = sumaMaPLN;
    }
    
    public List<StronaWiersza> getKontozapisyfiltered() {
        return kontozapisyfiltered;
    }
    
    public void setKontozapisyfiltered(List<StronaWiersza> kontozapisyfiltered) {
        this.kontozapisyfiltered = kontozapisyfiltered;
    }
    
    public Double getSaldoWnPLN() {
        return saldoWnPLN;
    }
    
    public void setSaldoWnPLN(Double saldoWnPLN) {
        this.saldoWnPLN = saldoWnPLN;
    }
    
    public Double getSaldoMaPLN() {
        return saldoMaPLN;
    }
    
    public void setSaldoMaPLN(Double saldoMaPLN) {
        this.saldoMaPLN = saldoMaPLN;
    }
    
    public List getZapisydopodswietlenia() {
        return zapisydopodswietlenia;
    }
    
    public void setZapisydopodswietlenia(List zapisydopodswietlenia) {
        this.zapisydopodswietlenia = zapisydopodswietlenia;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public String getWybranaWalutaDlaKont() {
        return wybranaWalutaDlaKont;
    }
    
    public void setWybranaWalutaDlaKont(String wybranaWalutaDlaKont) {
        this.wybranaWalutaDlaKont = wybranaWalutaDlaKont;
    }
    
    public List<ListaSum> getListasum() {
        return listasum;
    }
    
    public void setListasum(List<ListaSum> listasum) {
        this.listasum = listasum;
    }

    public Konto getKontodoprzeksiegowania() {
        return kontodoprzeksiegowania;
    }

    public void setKontodoprzeksiegowania(Konto kontodoprzeksiegowania) {
        this.kontodoprzeksiegowania = kontodoprzeksiegowania;
    }
    
    public List<StronaWiersza> getZapisyRok() {
        return zapisyRok;
    }
    
    public void setZapisyRok(List<StronaWiersza> zapisyRok) {
        this.zapisyRok = zapisyRok;
    }
    
    public String getWybranyRodzajKonta() {
        return wybranyRodzajKonta;
    }
    
    public void setWybranyRodzajKonta(String wybranyRodzajKonta) {
        this.wybranyRodzajKonta = wybranyRodzajKonta;
    }
    
    public boolean isNierenderujkolumnnywalut() {
        return nierenderujkolumnnywalut;
    }
    
    public void setNierenderujkolumnnywalut(boolean nierenderujkolumnnywalut) {
        this.nierenderujkolumnnywalut = nierenderujkolumnnywalut;
    }
    
    
//</editor-fold>
  

    
    
    public List<Konto> complete(String qr) {
        if (qr != null) {
            String query = null;
            List<Konto> results = Collections.synchronizedList(new ArrayList<>());
            if (wykazkont != null) {
                String nazwa = null;
                if (qr.trim().matches("^(.*\\s+.*)+$") && qr.length() > 6) {
                    String[] pola = qr.split(" ");
                    if (pola.length > 1) {
                        query = pola[0];
                        nazwa = pola[1];
                    } else {
                        query = qr;
                    }
                } else {
                    query = qr.trim();
                }
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    if (query.length() == 4 && !query.contains("-")) {
                        //wstawia - do ciagu konta
                        query = query.substring(0, 3) + "-" + query.substring(3, 4);
                    }
                    String[] qlist = {query};
                    wykazkont.stream().forEach((p)->{
                        if (p.getPelnynumer().startsWith(qlist[0])) {
                            results.add(p);
                        }
                    });
                    //rozwiazanie dla rozrachunkow szukanie po nazwie kontrahenta
                    if (nazwa != null && nazwa.length() > 2) {
                        for (Iterator<Konto> it = results.iterator(); it.hasNext();) {
                            Konto r = it.next();
                            if (!r.getNazwapelna().toLowerCase().contains(nazwa.toLowerCase())) {
                                it.remove();
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    String[] qlist = {query.toLowerCase()};
                    wykazkont.stream().forEach((p)->{
                        if (p.getNazwapelna().toLowerCase().contains(qlist[0])) {
                            results.add(p);
                        }
                    });
                } catch (Exception e) {
                    E.e(e);
                }
            }
            Collections.sort(results, new Kontocomparator());
            return results;
        }
        return null;
    }
    
    public List<Konto> completeWszystkieKonta(String qr) {
        if (qr != null) {
            String query = null;
            List<Konto> results = Collections.synchronizedList(new ArrayList<>());
            if (wszystkiekonta != null) {
                String nazwa = null;
                if (qr.trim().matches("^(.*\\s+.*)+$") && qr.length() > 6) {
                    String[] pola = qr.split(" ");
                    if (pola.length > 1) {
                        query = pola[0];
                        nazwa = pola[1];
                    } else {
                        query = qr;
                    }
                } else {
                    query = qr.trim();
                }
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    if (query.length() == 4 && !query.contains("-")) {
                        //wstawia - do ciagu konta
                        query = query.substring(0, 3) + "-" + query.substring(3, 4);
                    }
                    String[] qlist = {query};
                    wszystkiekonta.stream().forEach((p)->{
                        if (p.getPelnynumer().startsWith(qlist[0])) {
                            results.add(p);
                        }
                    });
                    //rozwiazanie dla rozrachunkow szukanie po nazwie kontrahenta
                    if (nazwa != null && nazwa.length() > 2) {
                        for (Iterator<Konto> it = results.iterator(); it.hasNext();) {
                            Konto r = it.next();
                            if (!r.getNazwapelna().toLowerCase().contains(nazwa.toLowerCase())) {
                                it.remove();
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    String[] qlist = {query.toLowerCase()};
                    wszystkiekonta.stream().forEach((p)->{
                        if (p.getNazwapelna().toLowerCase().contains(qlist[0])) {
                            results.add(p);
                        }
                    });
                } catch (Exception e) {
                    E.e(e);
                }
            }
            Collections.sort(results, new Kontocomparator());
            return results;
        }
        return null;
    }
    
    public List<Konto> completeOstAnal(String qr) {
        if (qr != null) {
            String query = null;
            List<Konto> results = Collections.synchronizedList(new ArrayList<>());
            if (ostatniaanalityka != null) {
                String nazwa = null;
                if (qr.trim().matches("^(.*\\s+.*)+$") && qr.length() > 6) {
                    String[] pola = qr.split(" ");
                    if (pola.length > 1) {
                        query = pola[0];
                        nazwa = pola[1];
                    } else {
                        query = qr;
                    }
                } else {
                    query = qr.trim();
                }
                try {
                    String q = query.substring(0, 1);
                    int i = Integer.parseInt(q);
                    if (query.length() == 4 && !query.contains("-")) {
                        //wstawia - do ciagu konta
                        query = query.substring(0, 3) + "-" + query.substring(3, 4);
                    }
                    String[] qlist = {query};
                    ostatniaanalityka.stream().forEach((p)->{
                        if (p.getPelnynumer().startsWith(qlist[0])) {
                            results.add(p);
                        }
                    });
                    //rozwiazanie dla rozrachunkow szukanie po nazwie kontrahenta
                    if (nazwa != null && nazwa.length() > 2) {
                        for (Iterator<Konto> it = results.iterator(); it.hasNext();) {
                            Konto r = it.next();
                            if (!r.getNazwapelna().toLowerCase().contains(nazwa.toLowerCase())) {
                                it.remove();
                            }
                        }
                    }
                } catch (NumberFormatException e) {
                    String[] qlist = {query.toLowerCase()};
                    ostatniaanalityka.stream().forEach((p)->{
                        if (p.getNazwapelna().toLowerCase().contains(qlist[0].toLowerCase())) {
                            results.add(p);
                        }
                    });
                } catch (Exception e) {
                    E.e(e);
                }
            }
            Collections.sort(results, new Kontocomparator());
            return results;
        }
        return null;
    }

    
    
    

}

