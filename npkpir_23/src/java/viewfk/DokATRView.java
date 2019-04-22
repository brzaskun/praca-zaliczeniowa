/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.DokumentFKBean;
import dao.KlienciDAO;
import dao.RodzajedokDAO;
import daoFK.CechazapisuDAOfk;
import daoFK.DokDAOfk;
import daoFK.KontoDAOfk;
import daoFK.TabelanbpDAO;
import entityfk.Cechazapisu;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import view.WpisView;
/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokATRView  implements Serializable{
    private static final long serialVersionUID = 1L;
    @Inject
    private DokDAOfk dokDAOfk;
    private List<Dokfk> lista;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private TabelanbpDAO tabelanbpDAO;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private KlienciDAO klienciDAO;
    @Inject
    private CechazapisuDAOfk cechazapisuDAOfk;
    @Inject 
    private KontoDAOfk kontoDAOfk;
    

    @PostConstruct
    private void init() {
        lista= dokDAOfk.findDokfkPodatnikRokKategoria(wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt(), "ATR");
    }
    
    public void wyksiegowanie() {
        if (lista!=null && !lista.isEmpty()) {
            List<Dokfk> nowewyksiegowane = new ArrayList<>();
            for (Dokfk p : lista) {
                Dokfk nowy = generujnowy(p);
                Cechazapisu nkup = pobierzCeche("NKUP");
                Cechazapisu npup = pobierzCeche("NPUP");
                naniescechy(nowy, nkup, npup);
                dokDAOfk.dodaj(nowy);
                nowewyksiegowane.add(nowy);
            }
            Msg.msg("Wyksięgowano dokumenty ATR z poprzedniego roku");
        } else {
            Msg.msg("e","Nie ma dokumentów do wyksięgowania");
        }
    }
    
    private Dokfk generujnowy(Dokfk p) {
        String opis = "wyksięgowanie statystycznych różnic kursowych z "+wpisView.getRokUprzedni();
        String opiswiersza = "wyksięgowanie różnic statystycznych konto rozrachunkowe: ";
        Konto kontown = p.getListawierszy().get(0).getKontoWn();
        Konto kontoma = p.getListawierszy().get(0).getKontoMa();
        double kwotawn = p.getListawierszy().get(0).getKwotaWn();
        double kwotama = p.getListawierszy().get(0).getKwotaMa();
        double roznicawn = 0.0;
        double roznicama = 0.0;
        if (kontown.getPelnynumer().equals("764")) {
            roznicawn = -kwotawn;
        }
        if (kontoma.getPelnynumer().equals("763")) {
            roznicama = -kwotama;
        }
        double[] roznicawnroznicama = new double[]{roznicawn, roznicama};
        Dokfk zwrot = DokumentFKBean.generujdokumentAutomSaldo(opiswiersza, wpisView, klienciDAO, "ATR", opis, rodzajedokDAO, tabelanbpDAO, kontoDAOfk, p.getStronyWierszy(), roznicawnroznicama, dokDAOfk);
      return zwrot;
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
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Dokfk> getLista() {
        return lista;
    }

    public void setLista(List<Dokfk> lista) {
        this.lista = lista;
    }

    

    
    
    
}
