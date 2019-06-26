/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import embeddablefk.SaldoKonto;
import entity.Podatnik;
import entity.PodatnikOpodatkowanieD;
import entityfk.Konto;
import entityfk.PozycjaRZiSBilans;
import entityfk.UkladBR;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import msg.Msg;
import view.PodatnikView;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class ZamknijKsiegiView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private String rok;
    private String rokpop;
    private String rokpoppop;
    private Podatnik podatnik;
    
    @PostConstruct
    private void init() {
        rok = wpisView.getRokWpisuSt();
        rokpop = wpisView.getRokUprzedniSt();
        rokpoppop = String.valueOf(wpisView.getRokUprzedni()-1);
        podatnik = wpisView.getPodatnikObiekt();
    }
    
    public void zamknijrokpoprzedni() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        saldarok(facesContext, podatnik, rokpop, rokpoppop);
        saldarok(facesContext, podatnik, rok, rokpop);
        zamknijrok(facesContext, rok);
    }

    private void saldarok(FacesContext facesContext, Podatnik podatnik, String rok, String rokpop) {
        Msg.msg(" ");
        Msg.msg("Sprawdzam salda kont "+rok);
        SaldoAnalitykaView saldoAnalitykaView = (SaldoAnalitykaView) facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "saldoAnalitykaView");
        saldoAnalitykaView.initzamknijksiegi(podatnik, rok, rokpop);
        List<SaldoKonto> sumaSaldoKonto = saldoAnalitykaView.getSumaSaldoKonto();
        SaldoKonto suma = sumaSaldoKonto.get(0);
        String wns = format.F.curr(Z.z(suma.getSaldoWn()));
        String mas = format.F.curr(Z.z(suma.getSaldoMa()));
        if (Z.z(suma.getSaldoWn())==Z.z(suma.getSaldoMa())) {
            saldoAnalitykaView.zaksiegujsaldakont();
            Msg.msg("Salda rok "+rok+" zgodne - Wn: "+wns+" Ma: "+mas);
        } else {
            Msg.msg("e","Salda rok "+rok+" niezgodne - Wn: "+wns+" Ma: "+mas);
            return;
        }
    }
    
    private void zamknijrok(FacesContext facesContext, String rok) {
        Msg.msg(" ");
        Msg.msg("Zamykam rok, księguję dokumenty za "+rok);
        PodatnikView podatnikView = (PodatnikView) facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "podatnikView");
        List<PodatnikOpodatkowanieD> podatnikOpodatkowanie = podatnikView.getPodatnikOpodatkowanie();
        PodatnikOpodatkowanieD opodatkowanie = znajdzopodatkowaniezarok(podatnikOpodatkowanie, rok);
        if (opodatkowanie!=null) {
            podatnikView.zamknijrokSF(opodatkowanie);
            Msg.msg("Udane zamknięcie roku "+rok);
        } else {
            Msg.msg("e","Błąd przy zamykaniu roku "+rok);
            return;
        }
    }
    public void weryfikujuklad() {
        wpisView.setRokWpisu(Integer.valueOf(rokpop));
        wpisView.aktualizuj();
        FacesContext facesContext = FacesContext.getCurrentInstance();
        UkladBRView ukladBRView = (UkladBRView) facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "ukladBRView");
        ukladBRView.init();
        List<UkladBR> listarokuprzedni = ukladBRView.getListarokbiezacy();
        weryfikujukladSF(facesContext, rokpop, listarokuprzedni, ukladBRView);
        ukladBRView = (UkladBRView) facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "ukladBRView");
        ukladBRView.init();
        listarokuprzedni = ukladBRView.getListarokbiezacy();
        porzadkujkonta(facesContext, rokpop, listarokuprzedni, ukladBRView);
        wpisView.setRokWpisu(Integer.valueOf(rok));
        wpisView.aktualizuj();
        ukladBRView = (UkladBRView) facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "ukladBRView");
        ukladBRView.init();
        List<UkladBR> listarokbiezacy = ukladBRView.getListarokbiezacy();
        weryfikujukladSF(facesContext, rok, listarokbiezacy, ukladBRView);
        ukladBRView = (UkladBRView) facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "ukladBRView");
        ukladBRView.init();
        listarokbiezacy = ukladBRView.getListarokbiezacy();
        porzadkujkonta(facesContext, rok, listarokbiezacy, ukladBRView);
    }
    
    public void weryfikujukladSF(FacesContext facesContext, String rok, List<UkladBR> listarokuprzedni, UkladBRView ukladBRView) {
        Msg.msg(" ");
        Msg.msg("Rozpoczynam weryfikowanie układu za "+rok);
        UkladBR podstawowy = pobierzpodstawowy(listarokuprzedni);
        List<PozycjaRZiSBilans> pozycje = null;
        if (podstawowy!=null) {
            PozycjaBRView pozycjaBRView = (PozycjaBRView) facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "pozycjaBRView");
            pozycjaBRView.setUklad(podstawowy);
            pozycjaBRView.pobierzProjektUkladuZamykanieKsiag("r", "");
            pozycje = pozycjaBRView.getPozycje();
            boolean czystarepozycje = sprawdzpozycje(pozycje);
            if (czystarepozycje) {
                Msg.msg("i","Układ za rok "+rok+" jest stary. Aktualizuje");
            } else {
                Msg.msg("Układ za rok "+rok+" jest aktualny");
            }
        } else {
            Msg.msg("i","brak układu za rok "+rok+". Tworzę");
        }
        ukladBRView.setSelected(podstawowy);
        ukladBRView.usun();
        ukladBRView.ustawukladwzorcowySF("2018");
        ukladBRView.implementujWzorcowySF(wpisView.getPodatnikObiekt(), rok);
    }
    
    public void porzadkujkonta(FacesContext facesContext, String rok, List<UkladBR> listarokuprzedni, UkladBRView ukladBRView) {
        UkladBR podstawowy = pobierzpodstawowy(listarokuprzedni);
        PozycjaBRKontaView pozycjaBRKontaView = (PozycjaBRKontaView) facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "pozycjaBRKontaView");
        pozycjaBRKontaView.init();
        pozycjaBRKontaView.setWybranyuklad(podstawowy);
        pozycjaBRKontaView.importujwzorcoweprzyporzadkowanie("r");
        pozycjaBRKontaView.importujwzorcoweprzyporzadkowanie("b");
        PlanKontView planKontView = (PlanKontView) facesContext.getELContext().getELResolver().getValue(facesContext.getELContext(), null, "planKontView");
        podstawowy = pobierzpodstawowy(listarokuprzedni);
        planKontView.setWybranyuklad(podstawowy);
        planKontView.porzadkowanieKontPodatnika(wpisView.getPodatnikObiekt(), rok);
        sprawdzczysanieprzyporzadkowane(planKontView.getWykazkont());
        Msg.msg("Udane aktualizowanie/tworzenie układu za "+rok);
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    private PodatnikOpodatkowanieD znajdzopodatkowaniezarok(List<PodatnikOpodatkowanieD> podatnikOpodatkowanie, String rok) {
        PodatnikOpodatkowanieD zwrot = null;
        for (PodatnikOpodatkowanieD p : podatnikOpodatkowanie) {
            if (p.getSymbolroku().equals(rok)) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }

    private UkladBR pobierzpodstawowy(List<UkladBR> listarokbiezacy) {
        UkladBR zwrot = null;
        for (UkladBR p : listarokbiezacy) {
            if (p.getUklad().equals("Podstawowy")) {
                zwrot=p;
                break;
            }
        }
        return zwrot;
    }

    private boolean sprawdzpozycje(List<PozycjaRZiSBilans> pozycje) {
        boolean zwrot = false;
        for (PozycjaRZiSBilans p : pozycje) {
            if (p.getNazwa().equals("Zyski nadzwyczajne")) {
                zwrot = true;
                break;
            }
        }
        return zwrot;
    }

    private void sprawdzczysanieprzyporzadkowane(List<Konto> wykazkont) {
        List<Konto> wykazkontbezpozycji = new ArrayList<>();
        for (Konto p : wykazkont) {
            if (p.getPozycjaWn()==null || p.getPozycjaMa()==null) {
                wykazkontbezpozycji.add(p);
            }
        }
        if (wykazkontbezpozycji.size()>0) {
            Msg.msg("e", "Są konta bez przyporządkowania");
        }
    }

    
    
    
    
}
