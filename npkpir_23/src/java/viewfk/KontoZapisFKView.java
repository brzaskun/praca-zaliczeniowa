/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.DokumentFKBean;
import beansFK.RozniceKursoweBean;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import embeddablefk.ListaSum;
import dao.StronaWierszaDAO;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import daoFK.TransakcjaDAO;
import daoFK.WierszBODAO;
import embeddable.Mce;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Transakcja;
import error.E;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
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
    private List<StronaWiersza> wybranekontadosumowania;
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
    @Inject private Konto wybranekonto;
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
    private List<StronaWiersza> zapisyRok;
    private String wybranyRodzajKonta;
    boolean nierenderujkolumnnywalut;

    

    public KontoZapisFKView() {
        E.m(this);
        kontozapisy = new ArrayList<>();
        wybranekontadosumowania = new ArrayList<>();
        wybranaWalutaDlaKont = "wszystkie";
        listasum = new ArrayList<>();
        ListaSum l = new ListaSum();
        listasum.add(l);
    }
    

    public void init() {
        wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBez0(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        pobierzzapisy();
        usunkontabezsald();
//        if (wykazkont != null) {
//            wybranekonto = wykazkont.get(0);
//        }
    }
    
    public void publicinit() {
         wykazkont = kontoDAOfk.findWszystkieKontaPodatnikaBez0(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
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
        if (wykazkont != null) {
            wybranekonto = wykazkont.get(0);
            usunkontabezsald();
        }
    }
    
    private void usunkontabezsald() {
        Set<Konto> listakont = new HashSet<>();
        for (StronaWiersza p : zapisyRok) {
            listakont.add(p.getKonto());
        }
        for (Iterator<Konto> it = wykazkont.iterator(); it.hasNext(); ) {
            Konto p = it.next();
            if (!listakont.contains(p) && p.isMapotomkow() == false) {
                it.remove();
            }
        }
    }
    
    public void pobierzzapisy() {
        List<StronaWiersza> zapisy = new ArrayList<>();
        try {
            zapisy = stronaWierszaDAO.findStronaByPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            
        } catch (Exception e) {
            E.e(e);
        }
        zapisyRok = zapisy;
    }
    
    public void pobierzZapisyNaKoncieNode(Konto wybraneKontoNode) {
        nierenderujkolumnnywalut = true;
        try {
            wybranekontadosumowania = new ArrayList<>();
            wybranekonto = serialclone.SerialClone.clone(wybraneKontoNode);
            kontozapisy = new ArrayList<>();
            List<Konto> kontapotomnetmp = new ArrayList<>();
            List<Konto> kontapotomneListaOstateczna = new ArrayList<>();
            kontapotomnetmp.add(wybranekonto);
            pobierzKontaPotomne(kontapotomnetmp, kontapotomneListaOstateczna, wykazkont);
            int granicaDolna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacOd());
            int granicaGorna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacDo());
            
            for (StronaWiersza r : zapisyRok) {
                if (kontapotomneListaOstateczna.contains(r.getKonto())) {
                    if (!r.getSymbolWalut().equals("PLN")) {
                        nierenderujkolumnnywalut = false;
                    }
                    int mc = Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac());
                    if (mc >= granicaDolna && mc <=granicaGorna) {
                        kontozapisy.add(r);
                    }
                }
            }
            sumazapisow();
            sumazapisowpln();
            //wybranekontoNode = (TreeNodeExtended<Konto>) odnajdzNode(wybranekonto);
            System.out.println("odnalazlem pobierzZapisyNaKoncieNode() KontoZapisFKView");
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public void pobierzZapisyNaKoncieNodeRozrachunki(Konto wybraneKontoNode) {
        if (wykazkont == null) {
            init();
        }
        try {
            wybranekontadosumowania = new ArrayList<>();
            wybranekonto = serialclone.SerialClone.clone(wybraneKontoNode);
            kontozapisy = new ArrayList<>();
            List<Konto> kontapotomnetmp = new ArrayList<>();
            List<Konto> kontapotomneListaOstateczna = new ArrayList<>();
            kontapotomnetmp.add(wybranekonto);
            pobierzKontaPotomne(kontapotomnetmp, kontapotomneListaOstateczna, wykazkont);
            int granicaDolna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacOd());
            int granicaGorna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacDo());
            for (StronaWiersza r : zapisyRok) {
                if (kontapotomneListaOstateczna.contains(r.getKonto())) {
                    int mc = Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac());
                    if (mc >= granicaDolna && mc <=granicaGorna) {
                        kontozapisy.add(r);
                    }
                }
            }
            sumazapisow();
            sumazapisowpln();
            //wybranekontoNode = (TreeNodeExtended<Konto>) odnajdzNode(wybranekonto);
            System.out.println("odnalazlem pobierzZapisyNaKoncieNode() KontoZapisFKView");
        } catch (Exception e) {
            E.e(e);
        }
    }
    

    private void pobierzKontaPotomne(List<Konto> kontamacierzyste, List<Konto> kontaostateczna, List<Konto> wykazkont) {
        List<Konto> nowepotomne = new ArrayList<>();
        for (Konto p : kontamacierzyste) {
            if (p.isMapotomkow()==true) {
                for (Konto r : wykazkont) {
                    if (r.getMacierzyste().equals(p.getPelnynumer())) {
                        nowepotomne.add(r);
                    }
                }
            } else {
                kontaostateczna.add(p);
            }
        }
        if (nowepotomne.size() > 0) {
            kontamacierzyste = nowepotomne;
            pobierzKontaPotomne(kontamacierzyste, kontaostateczna, wykazkont);
        } else {
            return;
        }
    }
    
    public void zapisykontmiesiace() {
         wpisView.wpisAktualizuj();
         pobierzZapisyZmianaWaluty();
         sumazapisow();
         sumazapisowpln();
    }
    
    
    public void pobierzZapisyZmianaWaluty() {
        Konto wybraneKontoNode = serialclone.SerialClone.clone(wybranekonto);
        kontozapisy = new ArrayList<>();
        List<Konto> kontapotomnetmp = new ArrayList<>();
        List<Konto> kontapotomneListaOstateczna = new ArrayList<>();
        kontapotomnetmp.add(wybranekonto);
        pobierzKontaPotomne(kontapotomnetmp, kontapotomneListaOstateczna, wykazkont);
        int granicaDolna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacOd());
        int granicaGorna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacDo());
        for (StronaWiersza r : zapisyRok) {
            if (kontapotomneListaOstateczna.contains(r.getKonto())) {
                if (wybranaWalutaDlaKont.equals("wszystkie")) {
                    int mc = Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac());
                    if (mc >= granicaDolna && mc <= granicaGorna) {
                        kontozapisy.add(r);
                    }
                } else {
                    if (r.getSymbolWalut().equals(wybranaWalutaDlaKont)) {
                        int mc = Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac());
                        if (mc >= granicaDolna && mc <= granicaGorna) {
                            kontozapisy.add(r);
                        }
                    }
                }
            }
        }
        sumazapisow();
        sumazapisowpln();
        //wybranekontoNode = (TreeNodeExtended<Konto>) odnajdzNode(wybranekonto);
        System.out.println("odnalazlem pobierzZapisyZmianaWaluty() kontoZapisFKView");
    }
     
    public void pobierzZapisyNaKoncieNodeUnselect() {
        kontozapisy.clear();
    }
    
    
    public void pobierzZapisyNaKoncie() {
        if (wybranekonto instanceof Konto) {
            kontozapisy = new ArrayList<>();
            List<Konto> kontapotomnetmp = new ArrayList<>();
            List<Konto> kontapotomneListaOstateczna = new ArrayList<>();
            kontapotomnetmp.add(wybranekonto);
            pobierzKontaPotomne(kontapotomnetmp, kontapotomneListaOstateczna, wykazkont);
            int granicaDolna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacOd());
            int granicaGorna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacDo());
            for (StronaWiersza r : zapisyRok) {
                if (kontapotomneListaOstateczna.contains(r.getKonto())) {
                    int mc = Mce.getMiesiacToNumber().get(r.getWiersz().getDokfk().getMiesiac());
                    if (mc >= granicaDolna && mc <= granicaGorna) {
                        kontozapisy.add(r);
                    }
                }
            }
            sumazapisow();
            sumazapisowpln();
            System.out.println("odnalazlem pobierzZapisyNaKoncie() kontoZapisFKView");
        }
    }
    
   
    
    public void sumazapisowtotal() {
        sumazapisow();
        sumazapisowpln();
        odszukajsparowanerozrachunki();
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
              return kontoDAOfk.findKontaPotomnePodatnik(wpisView.getPodatnikWpisu(), wpisView.getRokWpisu(),macierzyste.getPelnynumer());
          } catch (Exception e) {  E.e(e);
              Msg.msg("e", "nie udane pobierzpotomkow");
          }
          return null;
      }
      
      private void znajdzkontazpotomkami(List<Konto> kontapotomne, List<Konto> kontamacierzyste) {
          List<Konto> listakontposrednia = new ArrayList<>();
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
            if (kontozapisyfiltered != null && kontozapisyfiltered.size() > 0) {
                for(StronaWiersza p : kontozapisyfiltered){
                    sumujstrony(p);
                }
            } else if (wybranekontadosumowania != null && wybranekontadosumowania.size() > 0) {
                for(StronaWiersza p : wybranekontadosumowania){
                    sumujstrony(p);
                }
            } else {
                for(StronaWiersza p : kontozapisy){
                    sumujstrony(p);
                }
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
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Brak tabeli NBP w dokumencie. Podsumowanie nie jest prawidłowe. KontoZapisFVView sumazapisow()");
        }
    }
    
    public void sumazapisowAut(){
        try {
            sumaWn = 0.0;
            sumaMa = 0.0;
            for(StronaWiersza p : kontozapisy){
                 sumujstrony(p);
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
        } catch (Exception e) {  E.e(e);
            Msg.msg("e", "Brak tabeli NBP w dokumencie. Podsumowanie nie jest prawidłowe. KontoZapisFVView sumazapisow()");
        }
    }
    
    private void sumujstrony(StronaWiersza p) {
        if (p.getWnma().equals("Wn")) {
            if (wybranaWalutaDlaKont.equals("wszystkie")) {
                if (!p.getSymbolWalut().equals("PLN")) {
                    sumaWn = Z.z(sumaWn + p.getKwota());
                }
            } else {
                sumaWn = Z.z(sumaWn + p.getKwota());
            }
        } else if (p.getWnma().equals("Ma")) {
            if (wybranaWalutaDlaKont.equals("wszystkie")) {
                if (!p.getSymbolWalut().equals("PLN")) {
                    sumaMa = Z.z(sumaMa + p.getKwota());
                }
            } else {
                sumaMa = Z.z(sumaMa + p.getKwota());
            }

        }
    }
    
    public void sumazapisowpln(){
        sumaWnPLN = 0.0;
        sumaMaPLN = 0.0;
        if (kontozapisyfiltered != null && kontozapisyfiltered.size() > 0) {
            for(StronaWiersza p : kontozapisyfiltered){
                if (p.getWnma().equals("Wn")) {
                    Z.z(sumaWnPLN = sumaWnPLN + p.getKwotaPLN());
                } else if (p.getWnma().equals("Ma")){
                    Z.z(sumaMaPLN = sumaMaPLN + p.getKwotaPLN());
                }
            }
        } else if (wybranekontadosumowania != null && wybranekontadosumowania.size() > 0) {
            for(StronaWiersza p : wybranekontadosumowania){
                if (p.getWnma().equals("Wn")) {
                    Z.z(sumaWnPLN = sumaWnPLN + p.getKwotaPLN());
                } else if (p.getWnma().equals("Ma")){
                    Z.z(sumaMaPLN = sumaMaPLN + p.getKwotaPLN());
                }
            }
        } else {
            for(StronaWiersza p : kontozapisy){
                if (p.getWnma().equals("Wn")) {
                    Z.z(sumaWnPLN = sumaWnPLN + p.getKwotaPLN());
                } else if (p.getWnma().equals("Ma")){
                    Z.z(sumaMaPLN = sumaMaPLN + p.getKwotaPLN());
                }
            }
        }
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
        for(StronaWiersza p : kontozapisy){
            if (p.getWnma().equals("Wn")) {
                Z.z(sumaWnPLN = sumaWnPLN + p.getKwotaPLN());
            } else if (p.getWnma().equals("Ma")){
                Z.z(sumaMaPLN = sumaMaPLN + p.getKwotaPLN());
            }
        }
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
    
    private  Map<String, ListaSum> sumujzapisy() {
        Map<String, ListaSum> zbiorcza = stworzliste(kontozapisy);
        for (StronaWiersza p : kontozapisy) {
            ListaSum r = zbiorcza.get(p.getSymbolWalut());
            r.setTabelanbp(p.getWiersz().getTabelanbp());
            r.setKurswaluty(p.getKursWalutyBOSW());
            if (r != null) {
                if (p.getWnma().equals("Wn")) {
                    r.setSumaWn(Z.z(r.getSumaWn() + p.getKwota()));
                    r.setSumaWnPLN(Z.z(r.getSumaWnPLN()+ p.getKwotaPLN()));
                } else if (p.getWnma().equals("Ma")) {
                    r.setSumaMa(Z.z(r.getSumaMa() + p.getKwota()));
                    r.setSumaMaPLN(Z.z(r.getSumaMaPLN()+ p.getKwotaPLN()));
                }
            }
        }
        for (ListaSum s : zbiorcza.values()) {
            if (s.getSumaWn() > s.getSumaMa()) {
                s.setSaldoWn(Z.z(s.getSumaWn() - s.getSumaMa()));
            } else {
                s.setSaldoMa(Z.z(s.getSumaMa() - s.getSumaWn()));
            }
            if (s.getSumaWnPLN()> s.getSumaMaPLN()) {
                s.setSaldoWnPLN(Z.z(s.getSumaWnPLN()- s.getSumaMaPLN()));
            } else {
                s.setSaldoMa(Z.z(s.getSumaMaPLN()- s.getSumaWnPLN()));
            }
        }
        return zbiorcza;
    }
    
    public void rozliczzaznaczone() {
        if (wybranekontadosumowania != null && wybranekontadosumowania.size() > 1) {
            if (RozniceKursoweBean.wiecejnizjednatransakcja(wybranekontadosumowania)) {
                Msg.msg("e", "Wśród wybranych wierszy znajdują sie dwie nowe transakcje. Nie można rozliczyć zapisów.");
            } else {
                Msg.msg("Rozliczam oznaczone transakcje");
                RozniceKursoweBean.naniestransakcje(wybranekontadosumowania);
                stronaWierszaDAO.editList(wybranekontadosumowania);
            }
        } else {
            Msg.msg("e", "Należy wybrać przynajmniej dwa zapisy po różnych stronach konta w celu rozliczenia transakcji");
        }
    }
    
    public void rozliczsaldo() {
        if (kontozapisy != null && kontozapisy.size() > 0) {
            Map<String, ListaSum> listasum = sumujzapisy();
            Dokfk nowydok = DokumentFKBean.generujdokumentAutomRozrach(wpisView, klienciDAO, "ARS", "automatyczne rozliczenie salda konta", rodzajedokDAO, tabelanbpDAO, kontoDAOfk, kontozapisy, listasum, dokDAOfk);
            dokDAOfk.dodaj(nowydok);
            dodajzapisy(kontozapisy.get(0).getKonto(), nowydok);
            this.listasum = new ArrayList<>();
            ListaSum l = new ListaSum();
            this.listasum.add(l);
            sumazapisowAut();
            sumazapisowplnAut();
            Msg.msg("Dodano rozliczenie konta");
        } else {
            Msg.msg("e", "Nie ma czego rozliczać, Lista zapisów jest pusta.");
        }
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
            StronaWiersza wybranyrozrachunek = wybranekontadosumowania.get(0);
            List<Transakcja> listytransakcjiNT = transakcjaDAO.findByNowaTransakcja(wybranyrozrachunek);
            List<Transakcja> listytransakcjiR = transakcjaDAO.findByRozliczajacy(wybranyrozrachunek);
            zapisydopodswietlenia = new ArrayList<>();
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
        String seriao1 = o1.getWiersz().getDokfk().getDokfkPK().getSeriadokfk();
        String seriao2 = o2.getWiersz().getDokfk().getDokfkPK().getSeriadokfk();
        if (seriao1.equals(seriao2)) {
            return porownajnrserii(o1,o2);
        } else {
            return seriao1.compareTo(seriao2);
        }
    }
    
    private int porownajnrserii(StronaWiersza o1, StronaWiersza o2) {
        int seriao1 = o1.getWiersz().getDokfk().getDokfkPK().getNrkolejnywserii();
        int seriao2 = o2.getWiersz().getDokfk().getDokfkPK().getNrkolejnywserii();
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
            } else if (wybranekontadosumowania != null && wybranekontadosumowania.size() > 0) {
                sumazapisow();
                sumazapisowpln();
                PdfKontoZapisy.drukujzapisy(wpisView, wybranekontadosumowania, wybranekonto, listasum, true);
            } else {
                PdfKontoZapisy.drukujzapisy(wpisView, kontozapisy, wybranekonto, listasum, true);
            }
            String wydruk = "wydrukzapisynakoncie('"+wpisView.getPodatnikWpisu()+"')";
            RequestContext.getCurrentInstance().execute(wydruk);
        } catch (Exception e) {  E.e(e);

        }
    }
    
    public void drukujPdfZapisyNaKoncieDuzy() {
        try {
             if (kontozapisyfiltered != null && kontozapisyfiltered.size() > 0) {
                sumazapisow();
                sumazapisowpln();
                PdfKontoZapisy.drukujzapisy(wpisView, kontozapisyfiltered, wybranekonto, listasum, false);
            } else if (wybranekontadosumowania != null && wybranekontadosumowania.size() > 0) {
                sumazapisow();
                sumazapisowpln();
                PdfKontoZapisy.drukujzapisy(wpisView, wybranekontadosumowania, wybranekonto, listasum, false);
            } else {
                PdfKontoZapisy.drukujzapisy(wpisView, kontozapisy, wybranekonto, listasum, false);
            }
            String wydruk = "wydrukzapisynakoncie('"+wpisView.getPodatnikWpisu()+"')";
            RequestContext.getCurrentInstance().execute(wydruk);
        } catch (Exception e) {  E.e(e);

        }
    }
    
    
    public void usunPozycjeRozliczone() {
        try {
            for (Iterator<StronaWiersza> it = kontozapisy.iterator(); it.hasNext(); ) {
                StronaWiersza sw = it.next();
                if (sw.getWiersz().getOpisWiersza().contains("zapis BO")) {
                    System.out.println("d");
                }
                if (Z.z(sw.getPozostalo()) == 0.0 || sw.getDokfk().getRodzajedok().getSkrot().equals("RRK")) {
                    it.remove();
                }
            }
            sumazapisowtotal();
        } catch (Exception e) {  
            E.e(e);
        }
    }
    
    public void usunzListy() {
        for (StronaWiersza p : wybranekontadosumowania) {
            kontozapisy.remove(p);
        }
    }

    public List<Konto> getWykazkont() {
        return wykazkont;
    }

    public void setWykazkont(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
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

    public List<StronaWiersza> getWybranekontadosumowania() {
        return wybranekontadosumowania;
    }

    public void setWybranekontadosumowania(List<StronaWiersza> wybranekontadosumowania) {
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
    
    

    private Map<String, ListaSum> stworzliste(List<StronaWiersza> kontozapisy) {
        Map<String, ListaSum> zbiorcza = new HashMap<>();
        Set<String> waluty = new HashSet<>();
        for (StronaWiersza p : kontozapisy) {
            waluty.add(p.getSymbolWalut());
        }
        for (String r : waluty) {
            ListaSum l = new ListaSum();
            l.setWaluta(r);
            zbiorcza.put(r, l);
        }
        return zbiorcza;
    }
    
    
    

}

