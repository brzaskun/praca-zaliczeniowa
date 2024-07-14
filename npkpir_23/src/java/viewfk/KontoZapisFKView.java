/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.BilansBean;
import beansFK.DokumentFKBean;
import beansFK.KontaFKBean;
import beansFK.PlanKontFKBean;
import beansFK.RozliczTransakcjeBean;
import beansFK.UkladBRBean;
import comparator.Kontocomparator;
import dao.CechazapisuDAOfk;
import dao.DokDAOfk;
import dao.KlienciDAO;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.RodzajedokDAO;
import dao.StronaWierszaDAO;
import dao.TabelanbpDAO;
import dao.TransakcjaDAO;
import dao.UkladBRDAO;
import dao.WalutyDAOfk;
import dao.WierszBODAO;
import dao.WierszDAO;
import data.Data;
import embeddablefk.ListaSum;
import entity.Rodzajedok;
import entityfk.Cechazapisu;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Tabelanbp;
import entityfk.Transakcja;
import entityfk.UkladBR;
import entityfk.Waluty;
import entityfk.Wiersz;
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
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import msg.Msg;
import org.primefaces.PrimeFaces;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.model.TreeNode;
import pdf.PdfKontoZapisy;
 import view.WpisView;
import waluty.Z;
import interceptor.ConstructorInterceptor;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
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
    private WierszDAO wierszDAO;
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
    @Inject
    private RodzajedokDAO todzajedokDAO;
    @Inject 
    private Konto wybranekonto;
    @Inject 
    private Konto kontodoprzeksiegowania;
    private Double sumaWn;
    private Double sumaMa;
    private Double saldoWn;
    private Double saldoMa;
    private Double sumaWnPLN;
    private Double sumaMaPLN;
    private Double saldoWnPLN;
    private Double saldoMaPLN;
    private List zapisydopodswietlenia;
    @Inject
    private WpisView wpisView;
    private String wybranaWalutaDlaKont;
    private List<ListaSum> listasum;
    private List<Konto> wykazkont;
    private List<Konto> wszystkiekonta;
//    private List<StronaWiersza> zapisyRok;
    private String wybranyRodzajKonta;
    private boolean nierenderujkolumnnywalut;
    private boolean pokaztransakcje;
    private List<Konto> ostatniaanalityka;
    private String rokdopobrania;
    private boolean kolumnaopis;
    private boolean pokazrozliczone;
    private List<String> symboleWalut;
    private List<Waluty> pobraneRodzajeWalut;
    @Inject
    private WalutyDAOfk walutyDAOfk;

    

    public KontoZapisFKView() {
        ////E.m(this);
        kontozapisy = Collections.synchronizedList(new ArrayList<>());
        wybranezapisydosumowania = Collections.synchronizedList(new ArrayList<>());
        wybranaWalutaDlaKont = "wszystkie";
        listasum = Collections.synchronizedList(new ArrayList<>());
        ListaSum l = new ListaSum();
        listasum.add(l);
    }
    

    public void init() { //E.m(this);
        ostatniaanalityka = kontoDAOfk.findKontaOstAlityka(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
        Collections.sort(ostatniaanalityka,new Kontocomparator());
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBez0(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        wszystkiekonta = new ArrayList<>(wykazkont);
        wpisView.setMiesiacOd(wpisView.getMiesiacOd());
        wpisView.setMiesiacDo(wpisView.getMiesiacDo());
//        pobierzzapisy(wpisView.getRokWpisuSt());
        usunkontabezsald();
        wybierzgrupekont();
        kolumnaopis = true;
//        if (wykazkont != null) {
//            wybranekonto = wykazkont.get(0);
//        }
        pobraneRodzajeWalut = walutyDAOfk.findAll();
        symboleWalut = Collections.synchronizedList(new ArrayList<>());
        for (Waluty w : pobraneRodzajeWalut) {
            symboleWalut.add(w.getSymbolwaluty());
        }
    }
    
    public void publicinit() {
       ostatniaanalityka = kontoDAOfk.findKontaOstAlityka(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu());
       wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBez0(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        if (wykazkont != null) {
            wybranekonto = wykazkont.get(0);
            usunkontabezsald();
        }
        wybierzgrupekont();
        kolumnaopis = true;
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
        for (Iterator<Konto> it = listakont.iterator();it.hasNext();) {
            if (it.next()==null) {
                it.remove();
            }
        }
        listakont.stream().map((p) -> p.getKontomacierzyste()).forEachOrdered((m) -> {
            while (m != null) {
                listamacierzyste.add(m);
                m = m.getKontomacierzyste();
            }
        });
        return listamacierzyste;
    }
    
//    public void pobierzzapisy(String rok) {
//        List<StronaWiersza> zapisy = Collections.synchronizedList(new ArrayList<>());
//        try {
//            zapisy = stronaWierszaDAO.findStronaByPodatnikRokMcodMcdo(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), "01",wpisView.getMiesiacWpisu());
//        } catch (Exception e) {
//            E.e(e);
//        }
//        //zapisyRok = zapisy;
//    }
    
     public List<Konto> pobierzkontazsaldem(String rok) {
        List<Konto> zapisy = Collections.synchronizedList(new ArrayList<>());
        try {
            zapisy = stronaWierszaDAO.findStronaByPodatnikRokKontoDist(wpisView.getPodatnikObiekt(), rok);
            Collections.sort(zapisy, new Kontocomparator());
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
            String rok = rokdopobrania !=null ? rokdopobrania : wpisView.getRokWpisuSt();
            List<StronaWiersza> zapisyshort = null;
            zapisyshort = stronaWierszaDAO.findStronaByPodatnikKontoStartRokWalutyWszystkieOdswiez(wpisView.getPodatnikObiekt(), wybranekonto, rok, wpisView.getMiesiacOd(), wpisView.getMiesiacDo());
            if (zapisyshort!=null) {
                List<Konto> kontapotomnetmp = Collections.synchronizedList(new ArrayList<>());
                List<Konto> kontapotomneListaOstateczna = Collections.synchronizedList(new ArrayList<>());
                kontapotomnetmp.add(wybranekonto);
                KontaFKBean.pobierzKontaPotomne(kontapotomnetmp, kontapotomneListaOstateczna, new ArrayList<>(wykazkont));
                List<StronaWiersza> zapisyshortfilter = zapisyshort.stream().filter((r) -> (kontapotomneListaOstateczna.contains(r.getKonto()))).collect(collectingAndThen(toList(), Collections::synchronizedList));
                if (wybranaWalutaDlaKont.equals("wszystkie")) {
                    kontozapisy.addAll(zapisyshortfilter);
                } else {
                    zapisyshortfilter.stream().forEach((r) -> {
                        if (r.getSymbolWalutBOiSW().equals(wybranaWalutaDlaKont)) {
                                kontozapisy.add(r);
                        }
                    });
                }
                for (StronaWiersza p : kontozapisy) {
                    if (!p.getSymbolWalutBOiSW().equals("PLN")) {
                        nierenderujkolumnnywalut = false;
                        break;
                    }
                }
                sumazapisow();
                sumazapisowpln();
                if (wybraneKontoNode!=null&&(wybraneKontoNode.getPozycjaWn()==null||wybraneKontoNode.getPozycjaMa()==null)) {
                    weryfikujprzyporzadkowanie(wybraneKontoNode);
                }
                //wybranekontoNode = (TreeNodeExtended<Konto>) odnajdzNode(wybranekonto);
                Msg.msg("Pobrano zapisy dla konta "+wybraneKontoNode.getPelnynumer());
            }
        } catch (Exception e) {
            E.e(e);
        }
        PrimeFaces.current().executeScript("PF('dialogAjaxCzekaj').hide()");
    }
    
    @Inject
    KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject
    UkladBRDAO ukladBRDAO;
    
    private void weryfikujprzyporzadkowanie(Konto wybraneKontoNode) {
        try {
            Konto kontomacierzyste = wybraneKontoNode.getKontomacierzyste();
            if (kontomacierzyste.getSyntetykaanalityka().equals("analityka")&&kontomacierzyste.getPozycjaWn()!=null&&kontomacierzyste.getPozycjaMa()!=null) {
                wybraneKontoNode.kopiujPozycje(kontomacierzyste);
                wybraneKontoNode.setSyntetykaanalityka("rozrachunkowe");
                kontoDAOfk.edit(wybraneKontoNode);
                List<UkladBR> listaukladow = ukladBRDAO.findPodatnik(wpisView.getPodatnikObiekt());
                UkladBR wybranyuklad = UkladBRBean.pobierzukladaktywny(ukladBRDAO, listaukladow);
                PlanKontFKBean.naniesPrzyporzadkowaniePojedynczeKontoPorzadek(kontomacierzyste, wybraneKontoNode, kontopozycjaZapisDAO, wybranyuklad);
                Msg.msg("Zweryfikowano przyporządkowanie konta");
            }
        } catch (Exception e) {}
    }
    
    public void pobierzZapisyNaKoncieRokPop() {
        if (wybranekonto instanceof Konto) {
             wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBez0(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
             usunkontabezsald();
            if (rokdopobrania!=null) {
//                pobierzzapisy(rokdopobrania);
                pobierzZapisyNaKoncieNode(wybranekonto);
                Msg.msg("Rok "+rokdopobrania);
            } else {
                pobierzZapisyNaKoncieNode(wybranekonto);
                Msg.msg("Rok "+wpisView.getRokWpisuSt());
            }
        }
    }
    
//    public void pobierzZapisyNaKoncieNodeRozrachunki(Konto wybraneKontoNode) {
//        if (wykazkont == null) {
//            init();
//        }
//        try {
//            wybranezapisydosumowania = Collections.synchronizedList(new ArrayList<>());
//            wybranekonto = serialclone.SerialClone.clone(wybraneKontoNode);
//            kontozapisy = Collections.synchronizedList(new ArrayList<>());
//            List<Konto> kontapotomnetmp = Collections.synchronizedList(new ArrayList<>());
//            List<Konto> kontapotomneListaOstateczna = Collections.synchronizedList(new ArrayList<>());
//            kontapotomnetmp.add(wybranekonto);
//            KontaFKBean.pobierzKontaPotomne(kontapotomnetmp, kontapotomneListaOstateczna, wykazkont);
//            int granicaDolna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacOd());
//            int granicaGorna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacDo());
//            zapisyRok.stream().filter((r) -> (kontapotomneListaOstateczna.contains(r.getKonto()))).forEachOrdered((r) -> {
//                int mc = Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac());
//                if (mc >= granicaDolna && mc <=granicaGorna) {
//                    kontozapisy.add(r);
//                }
//            });
//            sumazapisow();
//            sumazapisowpln();
//            //wybranekontoNode = (TreeNodeExtended<Konto>) odnajdzNode(wybranekonto);
//        } catch (Exception e) {
//            E.e(e);
//        }
//    }
    

    
    
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
//            error.E.s("odnalazlem pobierzZapisyNaKoncie() kontoZapisFKView");
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
              return kontoDAOfk.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(),macierzyste);
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
    
      
      
     public void sumazapisowAll(){
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
      
      
    public void sumazapisow(){
        try {
            sumaWn = 0.0;
            sumaMa = 0.0;
            Tabelanbp tabela = null;
            DoubleAccumulator  wn = new DoubleAccumulator(Double::sum,0.d);
            DoubleAccumulator  ma = new DoubleAccumulator(Double::sum,0.d);
            if (wybranezapisydosumowania != null && wybranezapisydosumowania.size() > 0) {
                wybranezapisydosumowania.stream().forEach((p) -> {
                    sumujstrony(wn, ma, p);
                });
                for (StronaWiersza s : wybranezapisydosumowania) {
                    if (s.getWiersz().getTabelanbp()!=null&&s.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")==false) {
                        listasum.get(0).setTabelanbp(s.getTabelanbp());
                    }
                }
            } else if (kontozapisyfiltered != null && kontozapisyfiltered.size() > 0) {
                kontozapisyfiltered.stream().forEach((p) -> {
                    sumujstrony(wn, ma, p);
                });
                for (StronaWiersza s : kontozapisyfiltered) {
                    if (s.getWiersz().getTabelanbp()!=null&&s.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")==false) {
                        listasum.get(0).setTabelanbp(s.getTabelanbp());
                    }
                }
            } else {
                kontozapisy.stream().forEach((p) -> {
                    sumujstrony(wn, ma, p);
                });
                for (StronaWiersza s : kontozapisy) {
                    if (s.getWiersz().getTabelanbp()!=null&&s.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")==false) {
                        listasum.get(0).setTabelanbp(s.getWiersz().getTabelanbp());
                    }
                }
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
            for (StronaWiersza s : kontozapisy) {
                   if (s.getWiersz().getTabelanbp()!=null&&s.getWiersz().getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")==false) {
                       listasum.get(0).setTabelanbp(s.getWiersz().getTabelanbp());
                   }
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
        final String rok = wpisView.getRokWpisuSt();
        final String mc = wpisView.getMiesiacDo();
        if (pokaztransakcje) {
            wn.accumulate(p.getPozostaloZapisynakoncie(rok, mc));
        } else {
            wn.accumulate(p.getKwota());
        }
    }
    
        
    public void sumazapisowpln(){
        sumaWnPLN = 0.0;
        sumaMaPLN = 0.0;
        final String rok = wpisView.getRokWpisuSt();
        final String mc = wpisView.getMiesiacDo();
        DoubleAccumulator  wn = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator  ma = new DoubleAccumulator(Double::sum,0.d);
        if (wybranezapisydosumowania != null && wybranezapisydosumowania.size() > 0) {
            wybranezapisydosumowania.stream().forEach((p) -> {
                double kwotadlasumy = pokaztransakcje ? p.getPozostaloZapisynakoncie(rok, mc) : p.getKwotaPLN();
                if (p.getWnma().equals("Wn")) {
                    wn.accumulate(kwotadlasumy);
                } else if (p.getWnma().equals("Ma")){
                    ma.accumulate(kwotadlasumy);
                }
            });
        } else if (kontozapisyfiltered != null && kontozapisyfiltered.size() > 0) {
            kontozapisyfiltered.stream().forEach((p) -> {
                double kwotadlasumy = pokaztransakcje ? p.getPozostaloPLNZapisynakoncie(rok, mc) : p.getKwotaPLN();
                if (p.getWnma().equals("Wn")) {
                    wn.accumulate(kwotadlasumy);
                } else if (p.getWnma().equals("Ma")){
                    ma.accumulate(kwotadlasumy);
                }
            });
        }  else {
            kontozapisy.stream().forEach((p) -> {
                double kwotadlasumy = pokaztransakcje ? p.getPozostaloPLNZapisynakoncie(rok, mc) : p.getKwotaPLN();
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
        kontozapisy.forEach((p)->{
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
            Tabelanbp tabelanbp = p.getWiersz().getTabelanbp();
            if (tabelanbp!=null) {
                r.setTabelanbp(tabelanbp);
            }
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
    
    private double[] rozliczKontoRozniceATS(String nazwawaluty) {
        Tabelanbp tabelanakoniecokresu = tabelanbpDAO.findLastWalutaMcNBP(nazwawaluty, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        double kursnakoniecorkesu = tabelanakoniecokresu.getKurssredniPrzelicznik();
        double saldowalutywn = listasum.get(0).getSaldoWn();
        double saldowalutyma = listasum.get(0).getSaldoMa();
        double saldoplnwn = listasum.get(0).getSaldoWnPLN();
        double saldoplnma = listasum.get(0).getSaldoMaPLN();
        String waluta = wybranaWalutaDlaKont;
        double saldoplnwnwyliczone = Z.z(saldowalutywn*kursnakoniecorkesu);
        double saldoplnmawyliczone = Z.z(saldowalutyma*kursnakoniecorkesu);        
        double roznicawn = 0.0;
        double roznicama = 0.0;
        if (saldoplnwn>0.0) {
            if (saldoplnwnwyliczone>saldoplnwn) {
                roznicawn = Z.z(saldoplnwnwyliczone-saldoplnwn);
            } else if (saldoplnwnwyliczone<saldoplnwn) {
                roznicama = Z.z(saldoplnwn-saldoplnwnwyliczone);
            }
        } else if (saldoplnma>0.0) {
            if (saldoplnmawyliczone>saldoplnma) {
                roznicama = Z.z(saldoplnmawyliczone-saldoplnma);
            } else if (saldoplnwnwyliczone<saldoplnma) {
                roznicawn = Z.z(saldoplnma-saldoplnmawyliczone);
            }
        } 
//        if (roznicawn < 0.0) {
//            roznicama = -roznicawn;
//            roznicawn = 0.0;
//        } else if (roznicama < 0.0) {
//            roznicawn = -roznicama;
//            roznicama = 0.0;
//        }
        return new double[]{roznicawn, roznicama, kursnakoniecorkesu};
    }
    
    private double[] rozliczKontoRozrachunkowe(String nazwawaluty) {
        Tabelanbp tabelanakoniecokresu = tabelanbpDAO.findLastWalutaMcNBP(nazwawaluty, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        double kursnakoniecorkesu = tabelanakoniecokresu.getKurssredniPrzelicznik();
        double saldowalutywn = listasum.get(0).getSaldoWn();
        double saldowalutyma = listasum.get(0).getSaldoMa();
        String waluta = wybranaWalutaDlaKont;
        double sumazliczanie = 0.0;
        double sumazliczaniePLN = 0.0;
        double sumagranica = Z.z(saldowalutywn) > 0.0 ? Z.z(saldowalutywn) : Z.z(saldowalutyma);
        double saldowalutywnpln = 0.0;
        double saldowalutymapln = 0.0;
        double roznicawn = 0.0;
        double roznicama = 0.0;
        for (Iterator<StronaWiersza> it = new ReverseIterator<StronaWiersza>(kontozapisy).iterator(); it.hasNext();) {
            StronaWiersza p = it.next();
            if (!p.getSymbolWalutBOiSW().equals("PLN")) {
                    if (saldowalutywn > 0.0) {
                    if (p.isWn()) {
                        if (p.getKwota() < (sumagranica - sumazliczanie)) {
                            sumazliczanie += p.getKwota();
                            saldowalutywnpln += p.getKwotaPLN();
                            sumazliczaniePLN += Z.z(p.getKwota()*kursnakoniecorkesu);
                        } else {
                            double ilezostalo = (sumagranica - sumazliczanie);
                            double proporcja = ilezostalo / p.getKwota();
                            sumazliczaniePLN += Z.z(Z.z(p.getKwota()*kursnakoniecorkesu) * proporcja);
                            saldowalutywnpln += Z.z(p.getKwotaPLN() * proporcja);
                            if (saldowalutywnpln > 0.0) {
                                roznicawn = saldowalutywnpln > sumazliczaniePLN ? Z.z(sumazliczaniePLN- saldowalutywnpln):Z.z(saldowalutywnpln - sumazliczaniePLN);
                            } else {
                                roznicawn = saldowalutywnpln > sumazliczaniePLN ? -Z.z(sumazliczaniePLN- saldowalutymapln): -Z.z(saldowalutymapln - sumazliczaniePLN);
                            }
                            break;
                        }

                    }
                }
                if (saldowalutyma > 0.0) {
                    if (!p.isWn()) {
                        if (p.getKwota() < (sumagranica - sumazliczanie)) {
                            sumazliczanie += p.getKwota();
                            saldowalutymapln += p.getKwotaPLN();
                            sumazliczaniePLN += Z.z(p.getKwota()*kursnakoniecorkesu);
                        } else {
                            double ilezostalo = (sumagranica - sumazliczanie);
                            double proporcja = ilezostalo / p.getKwota();
                            sumazliczaniePLN += Z.z(Z.z(p.getKwota()*kursnakoniecorkesu) * proporcja);
                            saldowalutymapln += Z.z(p.getKwotaPLN() * proporcja);
                            roznicama = saldowalutymapln > 0.0 ? Z.z(saldowalutymapln - sumazliczaniePLN) : -Z.z(saldowalutywnpln + sumazliczaniePLN);
                            if (saldowalutymapln > 0.0) {
                                roznicama = saldowalutymapln > sumazliczaniePLN ? Z.z(sumazliczaniePLN- saldowalutymapln):Z.z(saldowalutymapln - sumazliczaniePLN);
                            } else {
                                roznicama = saldowalutymapln > sumazliczaniePLN ? -Z.z(sumazliczaniePLN- saldowalutywnpln): -Z.z(saldowalutywnpln - sumazliczaniePLN);
                            }
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
        return new double[]{roznicawn, roznicama};
    }
    
    private  double[] sumujzapisyKontobankowe() {
        double saldowalutyWn = listasum.get(0).getSaldoWn();
        double saldowalutyMa = listasum.get(0).getSaldoMa();
        String waluta = wybranaWalutaDlaKont;
        double sumazliczanie = 0.0;
        double saldoWnpoprawnepln = 0.0;
        double saldoMapoprawnepln = 0.0;
        double sumagranica = Z.z(saldowalutyWn) > 0.0 ? Z.z(saldowalutyWn) : Z.z(saldowalutyMa);
        double saldoWnobecnepln = listasum.get(0).getSaldoWnPLN();
        double saldoMaobecnepln=  listasum.get(0).getSaldoMaPLN();
        double roznicawn = 0.0;
        double roznicama = 0.0;
        for (Iterator<StronaWiersza> it = new ReverseIterator<StronaWiersza>(kontozapisy).iterator(); it.hasNext();) {
            StronaWiersza p = it.next();
            if (!p.getSymbolWalutBOiSW().equals("PLN")) {
                if (saldowalutyWn > 0.0) {
                    if (p.isWn()) {
                        if (p.getKwota() < (sumagranica-sumazliczanie)) {
                            sumazliczanie += p.getKwota();
                            saldoWnpoprawnepln += p.getKwotaPLN();
                        } else {
                            double ilezostalo = (sumagranica-sumazliczanie);
                            double proporcja = ilezostalo/p.getKwota();
                            saldoWnpoprawnepln += Z.z(p.getKwotaPLN()*proporcja);
                            //roznicawn = saldoWnobecnepln > 0.0 ? Z.z(saldoWnobecnepln-saldoWnpoprawnepln) : -Z.z(saldoMaobecnepln+saldoWnpoprawnepln);
                            break;
                        }

                    }
                }
                if (saldowalutyMa > 0.0) {
                    if (!p.isWn()) {
                        if (p.getKwota() < (sumagranica-sumazliczanie)) {
                            sumazliczanie += p.getKwota();
                            saldoMapoprawnepln += p.getKwotaPLN();
                        } else {
                            double ilezostalo = (sumagranica-sumazliczanie);
                            double proporcja = ilezostalo/p.getKwota();
                            saldoMapoprawnepln += Z.z(p.getKwotaPLN()*proporcja);
                            //roznicama = saldoMaobecnepln > 0.0 ? Z.z(saldoMaobecnepln-saldoMapoprawnepln) : -Z.z(saldoWnobecnepln+saldoMapoprawnepln);
                            break;
                        }

                    }
                }
            }
        }
        //gdy nie ma walut ale zostalo saldo w pln
        if (saldowalutyWn == 0.0 && saldowalutyMa == 0.0 && (saldoWnobecnepln > 0.0 || saldoMaobecnepln > 0.0)) {
            if (saldoWnobecnepln > 0.0) {
                roznicama = Z.z(saldoWnobecnepln);
            }
            if (saldoMaobecnepln > 0.0) {
                roznicawn = Z.z(saldoMaobecnepln);
            }
        } else {
            if (saldoWnobecnepln>0.0&&saldoWnpoprawnepln>0.0) {
                if (saldoWnobecnepln>saldoWnpoprawnepln) {
                    roznicama = Z.z(saldoWnobecnepln-saldoWnpoprawnepln);
                } else {
                    roznicawn = Z.z(saldoWnpoprawnepln-saldoWnobecnepln);
                }
            } else if (saldoMaobecnepln>0.0&&saldoMapoprawnepln>0.0) {
                if (saldoMaobecnepln>saldoMapoprawnepln) {
                    roznicawn = Z.z(saldoMaobecnepln-saldoMapoprawnepln);
                } else {
                    roznicama = Z.z(saldoMapoprawnepln-saldoMaobecnepln);
                }
            } else if (saldoWnobecnepln>0.0&&saldoMapoprawnepln>0.0) {
                roznicama = Z.z(saldoMapoprawnepln+saldoWnobecnepln);
            } else if (saldoMaobecnepln>0.0&&saldoWnpoprawnepln>0.0) {
                roznicawn = Z.z(saldoWnpoprawnepln+saldoMaobecnepln);
            }
                
        }
        
        return new double[]{roznicawn,roznicama};
    }
    
    public void usuntransakcje(Transakcja transakcjadousuniecia, StronaWiersza sw) {
        if (transakcjadousuniecia!=null) {
            if (sw.getPlatnosci().isEmpty()==false) {
                sw.getPlatnosci().remove(transakcjadousuniecia);
            }
            if (sw.getNowetransakcje().isEmpty()==false) {
                sw.getNowetransakcje().remove(transakcjadousuniecia);
            }
            stronaWierszaDAO.edit(sw);
            Msg.msg("Usunięto transakcje");
        }
    }
    
    public void rozliczzaznaczone() {
        if (wybranezapisydosumowania != null && wybranezapisydosumowania.size() > 1) {
            if (wybranezapisydosumowania.size()==2 && RozliczTransakcjeBean.sprawdzczyjestkorekta(wybranezapisydosumowania)==null) {
                stronaWierszaDAO.editList(wybranezapisydosumowania);
                Msg.msg("w", "Jedna z pozyji to korekta, naniesiono oznaczenia");
            }
            if (wybranezapisydosumowania.size()==2 && RozliczTransakcjeBean.sprawdznowatransakcje(wybranezapisydosumowania)==null) {
                RozliczTransakcjeBean.wybierzjednatransakcje(wybranezapisydosumowania);
            }
            if (RozliczTransakcjeBean.sprawdznowatransakcje(wybranezapisydosumowania)==null) {
                Msg.msg("e", "Żadna z pozycji nie jest oznaczona jako rachunek. Nie można zrobić szybkiego rozliczenia");
            } else if (czyroznewaluty(wybranezapisydosumowania)){
                Msg.msg("e", "Zaznaczone dokumenty są różnowalutowe. Nie można zrobić szybkiego rozliczenia");
            } else {
                Msg.msg("Rozliczam wzjemnie zaznaczone pozycje");
                List<StronaWiersza> listapoedycji = RozliczTransakcjeBean.naniestransakcjeRozne(wybranezapisydosumowania);
                stronaWierszaDAO.editList(listapoedycji);
//                for (StronaWiersza p : wybranezapisydosumowania) {
//                    if (Z.z(p.getPozostalo()) == 0.0) {
//                        kontozapisy.remove(p);
//                    }
//                }
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
            dokDAOfk.create(nowydok);
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
            dokDAOfk.create(nowydok);
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
                sumazapisowAll();
                double[] roznicawnroznicama = sumujzapisyKontobankowe();
                if (roznicawnroznicama[0] > 0.0 || roznicawnroznicama[1] > 0.0) {
                    String opis = "automatyczne rozliczenie walut na koncie bankowym na koniec "+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisu();
                    String opiswiersza = "zrealiz. róż. kursowe na środkach własnych: ";
                    Dokfk nowydok = DokumentFKBean.generujdokumentAutomSaldo(opiswiersza, wpisView, klienciDAO, "ARS", opis, rodzajedokDAO, tabelanbpDAO, kontoDAOfk, kontozapisy, roznicawnroznicama, dokDAOfk);
                    dokDAOfk.create(nowydok);
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
    
    public void rozliczsaldostatystycznie() {
        if (!wpisView.getMiesiacDo().equals(wpisView.getMiesiacWpisu())) {
            Msg.msg("e", "Miesiąc 'do' nie jest taki sam jak bieżący miesiąc wpisu. Nie można rozliczyć salda.");
        } else {
            if (kontozapisy != null && kontozapisy.size() > 0) {
                Set<String> waluty = new HashSet<>();
                for (StronaWiersza sw : kontozapisy) {
                    if (!sw.getSymbolWalutBOiSW().equals("PLN")) {
                        waluty.add(sw.getSymbolWalutBOiSW());
                        if (waluty.size()>1) {
                            break;
                        }
                    }
                }
                if (waluty.size()>1) {
                    Msg.msg("e", "Na koncie sa zapisy z wielu walut. Nie można naliczyć automatyczbnie różnic statystycznych");
                    return;
                }
                sumazapisowAll();
                double[] roznicawnroznicama = rozliczKontoRozniceATS((String) (waluty.toArray())[0]);
                if (roznicawnroznicama[0] > 0.0 || roznicawnroznicama[1] > 0.0) {
                    String opis = "rozlicz. stat. walut na koncie na koniec "+wpisView.getMiesiacWpisu()+"/"+wpisView.getRokWpisu();
                    String opiswiersza = "różn. kurs. (kurs "+Z.z4(roznicawnroznicama[2])+")statystyczne: ";
                    Dokfk nowydok = DokumentFKBean.generujdokumentAutomSaldo(opiswiersza, wpisView, klienciDAO, "ATR", opis, rodzajedokDAO, tabelanbpDAO, kontoDAOfk, kontozapisy, roznicawnroznicama, dokDAOfk);
                    Cechazapisu nkup = pobierzCeche("NKUP");
                    Cechazapisu npup = pobierzCeche("NPUP");
                    naniescechy(nowydok, nkup, npup);
                    dokDAOfk.create(nowydok);
                    dodajzapisy(kontozapisy.get(0).getKonto(), nowydok);
                    this.listasum = Collections.synchronizedList(new ArrayList<>());
                    ListaSum l = new ListaSum();
                    this.listasum.add(l);
                    sumazapisowAut();
                    sumazapisowplnAut();
                    Msg.msg("Dodano rozliczenie statystyczne walut konta rorzrachunkowego");
                } else {
                    Msg.msg("e", "Różnice statystyczne wynoszą zero. Dokument niezaksięgowany");
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
               //zapisyRok.add(p);
           }
   }
    
    //poszukuje rozrachunkow do sparowania
    public void odszukajsparowanerozrachunki() {
        try {
            StronaWiersza wybranyrozrachunek = wybranezapisydosumowania.get(0);
            if (wybranyrozrachunek.getKonto().isRozrachunkowe()) {
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
                PrimeFaces.current().ajax().update("zapisydopodswietlenia");
                PrimeFaces.current().executeScript("podswietlrozrachunki();");
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
                PdfKontoZapisy.drukujzapisy(wpisView, kontozapisyfiltered, wybranekonto, listasum, true, pokaztransakcje);
            } else if (wybranezapisydosumowania != null && wybranezapisydosumowania.size() > 0) {
                sumazapisow();
                sumazapisowpln();
                PdfKontoZapisy.drukujzapisy(wpisView, wybranezapisydosumowania, wybranekonto, listasum, true, pokaztransakcje);
            } else {
                PdfKontoZapisy.drukujzapisy(wpisView, kontozapisy, wybranekonto, listasum, true, pokaztransakcje);
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
                PdfKontoZapisy.drukujzapisyKompakt(wpisView, kontozapisyfiltered, wybranekonto, listasum, 2, nierenderujkolumnnywalut, pokaztransakcje);
            } else if (wybranezapisydosumowania != null && wybranezapisydosumowania.size() > 0) {
                sumazapisow();
                sumazapisowpln();
                PdfKontoZapisy.drukujzapisyKompakt(wpisView, wybranezapisydosumowania, wybranekonto, listasum, 2, nierenderujkolumnnywalut, pokaztransakcje);
            } else {
                PdfKontoZapisy.drukujzapisyKompakt(wpisView, kontozapisy, wybranekonto, listasum, 2, nierenderujkolumnnywalut, pokaztransakcje);
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
                PdfKontoZapisy.drukujzapisy(wpisView, kontozapisyfiltered, wybranekonto, listasum, false, pokaztransakcje);
            } else if (wybranezapisydosumowania != null && wybranezapisydosumowania.size() > 0) {
                sumazapisow();
                sumazapisowpln();
                PdfKontoZapisy.drukujzapisy(wpisView, wybranezapisydosumowania, wybranekonto, listasum, false, pokaztransakcje);
            } else {
                PdfKontoZapisy.drukujzapisy(wpisView, kontozapisy, wybranekonto, listasum, false, pokaztransakcje);
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
                } else if (pokazrozliczone&&sw.getDokfk().getRodzajedok().getSkrot().equals("RRK")) {
                    it.remove();
                } else if (pokazrozliczone==false&&Z.z(sw.getPozostalookres(wpisView.getRokWpisuSt(), wpisView.getMiesiacDo())) == 0.0 || sw.getDokfk().getRodzajedok().getSkrot().equals("RRK")) {
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
    
    public void usunzListyDok(String symbol) {
        for (Iterator<StronaWiersza> it = kontozapisy.iterator();it.hasNext();) {
            try {
                StronaWiersza p = it.next();
                if (p.getDokfk().getSeriadokfk().equals(symbol)) {
                    dokDAOfk.remove(p.getDokfk());
                    //zapisyRok.remove(p);
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
                if ((wybranezapisydosumowania == null || wybranezapisydosumowania.isEmpty())) {
                    Msg.msg("e", "Nie wybrano pozycji do przeksięgowania. Nie można wykonać przeksięgowania");
                    return;
                }
                if (!wybranekonto.equals(kontodoprzeksiegowania) && wybranekonto.isMapotomkow() == true && wybranekonto.getIdslownika() == kontodoprzeksiegowania.getIdslownika()) {
                    przeksiegujslownikowe();
                } else {
                    przeksiegujanalityke(wybranezapisydosumowania);
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
    
    public void zaksiegujpk() {
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
                if (kontodoprzeksiegowania.isMapotomkow() == true && kontodoprzeksiegowania.getIdslownika()==0) {
                    Msg.msg("e", "Konto docelowe jest kontem syntetycznym. Nie można wykonać przeksięgowania");
                    return;
                } else {
                    generujPK(kontodoprzeksiegowania);
                    kontodoprzeksiegowania = null;
                    Msg.msg("Zaksięgowano kwoty");
                }
            } else {
                Msg.msg("e", "Nie wybrałeś konta docelowego");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.dPe();
        }
    }
    
    public void generujPK(Konto docelowe) {
        try {
            Dokfk dokfk = null;
            dokfk = DokumentFKBean.generujdokumentZaksieguj(wpisView, klienciDAO, "PK", "przeksięgowanie zapisów", rodzajedokDAO, tabelanbpDAO, docelowe, kontoDAOfk, wybranezapisydosumowania, dokDAOfk);
            dokDAOfk.create(dokfk);
            Msg.msg("Wygenerowano dokument PK");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd - nie wygenerowano dokumentu PK");
        }
    }
    
    private void przeksiegujanalityke(List<StronaWiersza> wierszedoprzeksiegowania) {
        int rozrachunkowe = 0;
        int bo = 0;
        for (StronaWiersza p : wierszedoprzeksiegowania) {
            if (p.getWierszbo()==null) {
                if (p.getNowetransakcje().isEmpty() && p.getPlatnosci().isEmpty()) {
                    p.setKonto(kontodoprzeksiegowania);
                } else {
                    rozrachunkowe++;
                }
            } else {
              if (p.getNowetransakcje().isEmpty() && p.getPlatnosci().isEmpty()) {
                    p.getWierszbo().setKonto(kontodoprzeksiegowania);
                    wierszBODAO.edit(p.getWierszbo());
                    p.setKonto(kontodoprzeksiegowania);
                } else {
                    rozrachunkowe++;
                }
            }
        }
        stronaWierszaDAO.editList(wierszedoprzeksiegowania);
        kontozapisy.removeAll(wierszedoprzeksiegowania);
        if (rozrachunkowe > 0) {
            Msg.msg("w", "Nie przeksiegowano pozycji z rozrachunkami w liczbie " + rozrachunkowe);
        }
        if (bo > 0) {
            Msg.msg("w", "Nie przeksiegowano pozycji z zapisami BO w liczbie " + bo);
        }
        kontodoprzeksiegowania = null;
        //pobierzzapisy(wpisView.getRokWpisuSt());
        //publicinit();
        //pobierzZapisyNaKoncieNode(wybranekonto);
        Msg.dP();
    }
    
    private void przeksiegujslownikowe() {
        int rozrachunkowe = 0;
        int bo = 0;
        List<Konto> potomne = kontoDAOfk.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), kontodoprzeksiegowania);
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
//        pobierzzapisy(wpisView.getRokWpisuSt());
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
    
//    public List<StronaWiersza> getZapisyRok() {
//        return zapisyRok;
//    }
//    
//    public void setZapisyRok(List<StronaWiersza> zapisyRok) {
//        this.zapisyRok = zapisyRok;
//    }
    
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
                    results.addAll(wykazkont.stream().filter(p -> p.getPelnynumer().startsWith(qlist[0])).collect(Collectors.toList()));
//                    wykazkont.stream().forEach((p)->{
//                        if (p.getPelnynumer().startsWith(qlist[0])) {
//                            results.add(p);
//                        }
//                    });
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
                    results.addAll(wykazkont.stream().filter(p -> p.getNazwapelna().toLowerCase().contains(qlist[0])).collect(Collectors.toList()));
//                    wykazkont.stream().forEach((p)->{
//                        if (p.getNazwapelna().toLowerCase().contains(qlist[0])) {
//                            results.add(p);
//                        }
//                    });
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
                    results.addAll(wszystkiekonta.stream().filter(p -> p.getPelnynumer().startsWith(qlist[0])).collect(Collectors.toList()));
//                    wszystkiekonta.stream().forEach((p)->{
//                        if (p.getPelnynumer().startsWith(qlist[0])) {
//                            results.add(p);
//                        }
//                    });
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
                    results.addAll(wszystkiekonta.stream().filter(p -> p.getNazwapelna().toLowerCase().contains(qlist[0])).collect(Collectors.toList()));
//                    wszystkiekonta.stream().forEach((p)->{
//                        if (p.getNazwapelna().toLowerCase().contains(qlist[0])) {
//                            results.add(p);
//                        }
//                    });
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

    private Cechazapisu pobierzCeche(String nazwacechy) {
        Cechazapisu zwrot = null;
        List<Cechazapisu> cechy = cechazapisuDAOfk.findPodatnik(wpisView.getPodatnikObiekt());
        if (cechy!=null) {
            for (Cechazapisu c : cechy) {
                if (c.getNazwacechy().equals(nazwacechy)) {
                    zwrot = c;
                    break;
                }
            }
        }
        return zwrot;
    }

    private void naniescechy(Dokfk nowydok, Cechazapisu nkup, Cechazapisu npup) {
        for (StronaWiersza sw : nowydok.getStronyWierszy()) {
            if (sw.getKonto().getPelnynumer().equals("764")) {
                sw.getCechazapisuLista().add(nkup);
            }
            if (sw.getKonto().getPelnynumer().equals("763")) {
                sw.getCechazapisuLista().add(npup);
            }
        }
    }

    public void naniesbz() {
        if (wpisView.getMiesiacOd().equals("01")&&wpisView.getMiesiacDo().equals("12")) {
            Rodzajedok definicjabz = rodzajedokDAO.find("BZ", wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            if (definicjabz!=null) {
                if (wybranekonto==null&&listasum.isEmpty()==false) {
                    Msg.msg("e","Nie wybrano konta");
                } else {
                    boolean czyobrotytylkopojednej = true;
                    List zachowaneWiersze = wybranezapisydosumowania!=null?wybranezapisydosumowania:new ArrayList<StronaWiersza>();
                    boolean zapisy = true;
                    if (zachowaneWiersze.isEmpty()) {
                        zapisy = false;
                        zachowaneWiersze = naniessaldo(listasum, wybranekonto);
                    }
                    if (zapisy&&listasum.get(0).getSumaWnPLN()!=0.0&&listasum.get(0).getSumaMaPLN()!=0.0) {
                        czyobrotytylkopojednej = false;
                    }
                    if (czyobrotytylkopojednej) {
                        String seriadokumentu = "BZ";
                        Dokfk dok = dokDAOfk.findDokfkLastofaTypeMc(wpisView.getPodatnikObiekt(), seriadokumentu, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                        if (dok == null) {
                            dok = BilansBean.stworznowydokument(1, zachowaneWiersze, seriadokumentu, wpisView, klienciDAO, rodzajedokDAO, tabelanbpDAO, walutyDAOfk, pokaztransakcje);
                            if (dok.getListawierszy() != null) {
                                dok.przeliczKwotyWierszaDoSumyDokumentu();
                            }
                            dokDAOfk.create(dok);
                            Msg.msg("Wygenerowano dokument BZ i naniesiono zapisy");
                        } else {
                            BilansBean.edytujnowydokument(dok, zachowaneWiersze, seriadokumentu, wpisView, pokaztransakcje);
                            if (dok.getListawierszy() != null) {
                                dok.przeliczKwotyWierszaDoSumyDokumentu();
                            }
                            dokDAOfk.edit(dok);
                            Msg.msg("Wedytowano dokument BZ i zaktualizowano zapisy");
                        }
                    } else {
                        Msg.msg("e","Błąd. Wybrano zapisy po obydwu stronach dokumentu");
                    }
                }
            } else {
                Msg.msg("e","Brak dokumentu typu BZ. Proszę otworzyć parametry i aktywne dokumenty podatnika. Dokument się załaduje automatycznie");
            }
        } else {
            Msg.msg("e","Pobrano zapisy za niewłaściwe miesiące. Proszę ustawiść od 01 do 12");
        }
    }
    
    private List naniessaldo(List<ListaSum> listasum, Konto konto) {
        List<StronaWiersza> wierszsaldo = new ArrayList<>();
        if (listasum!=null&&listasum.isEmpty()==false) {
            StronaWiersza strona = new StronaWiersza();
            strona.setKonto(konto);
            if (listasum.size()==1) {
                ListaSum l = listasum.get(0);
                if (l.getSaldoWnPLN()!=0.0) {
                    strona.setWnma("Wn");
                    double kwotawaluta = l.getSaldoWn();
                    if (kwotawaluta>0.0) {
                        strona.setKwota(kwotawaluta);
                        strona.setKwotaWaluta(kwotawaluta);
                        strona.setKwotaPLN(l.getSaldoWnPLN());
                    } else {
                        strona.setKwota(l.getSaldoWnPLN());
                        strona.setKwotaWaluta(l.getSaldoWnPLN());
                        strona.setKwotaPLN(l.getSaldoWnPLN());
                    }
                } else if (l.getSaldoMaPLN()!=0.0) {
                    strona.setWnma("Ma");
                    double kwotawaluta = l.getSaldoMa();
                    if (kwotawaluta>0.0) {
                        strona.setKwota(kwotawaluta);
                        strona.setKwotaWaluta(kwotawaluta);
                        strona.setKwotaPLN(l.getSaldoMaPLN());
                    } else {
                        strona.setKwota(l.getSaldoMaPLN());
                        strona.setKwotaWaluta(l.getSaldoMaPLN());
                        strona.setKwotaPLN(l.getSaldoMaPLN());
                    }
                }
                strona.setTabelanbp(l.getTabelanbp());
            }
            if (strona.getKwota()!=0.0) {
                wierszsaldo.add(strona);
            }
        }
        return wierszsaldo;
    }
    
    
    public void oznaczjakosprawdzone(int sprawdz) {
        if (wybranekonto==null) {
            Msg.msg("e","Nie wybrano konta");
        } else {
            if (sprawdz==1) {
                wybranekonto.setSprawdzono(Data.aktualnaData());
                kontoDAOfk.edit(wybranekonto);
                Msg.msg("Oznaczono konto jako sprawdzone");
            } else if (sprawdz==0){
                wybranekonto.setSprawdzono(null);
                kontoDAOfk.edit(wybranekonto);
                Msg.msg("Oznaczono konto jako niesprawdzone");
            }  else if (sprawdz==3){
                wykazkont.stream().forEach((p) -> {
                    p.setSprawdzono(null);
                });
                kontoDAOfk.editList(wykazkont);
                Msg.msg("Odznaczono wszystkie konta");
            }
            
        }
    }
    
    public void usunwielokrotneSW() {
        try {
            List<Wiersz> usuniete = new ArrayList<>();
            for (Iterator<StronaWiersza> it=kontozapisy.iterator();it.hasNext();) {
                StronaWiersza p = it.next();
                Wiersz wiersz = p.getWiersz();
                Dokfk dok = p.getDokfk();
                boolean brak = true;
                for (Wiersz w : dok.getListawierszy()) {
                    if (wiersz.getIdwiersza()==w.getIdwiersza()) {
                        brak=false;
                        break;
                    }
                }
//                if (wiersz.getStronaWn().getId()==p.getId()||wiersz.getStronaMa().getId()==p.getId()) {
//                    brak = false;
//                }
                if (brak&&!usuniete.contains(p.getWiersz())) {
                    wierszDAO.remove(p.getWiersz());
                    usuniete.add(p.getWiersz());
//                    stronaWierszaDAO.remove(p);
                    it.remove();
                }

            }
            Msg.msg("Ukończono korektę stron");
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd nie ukończono korekty stron");
        }
    }

    public String getRokdopobrania() {
        return rokdopobrania;
    }

    public void setRokdopobrania(String rokdopobrania) {
        this.rokdopobrania = rokdopobrania;
    }

    public boolean isKolumnaopis() {
        return kolumnaopis;
    }

    public void setKolumnaopis(boolean kolumnaopis) {
        this.kolumnaopis = kolumnaopis;
    }

    public List<String> getSymboleWalut() {
        return symboleWalut;
    }

    public void setSymboleWalut(List<String> symboleWalut) {
        this.symboleWalut = symboleWalut;
    }

    public boolean isPokazrozliczone() {
        return pokazrozliczone;
    }

    public void setPokazrozliczone(boolean pokazrozliczone) {
        this.pokazrozliczone = pokazrozliczone;
    }

    

    
    
    
    

}

