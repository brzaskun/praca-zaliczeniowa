    /*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.StronaWierszaBean;
import beansFK.UkladBRBean;
import comparator.PozycjaRZiSBilanscomparator;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.PodatnikOpodatkowanieDAO;
import dao.PozycjaBilansDAO;
import dao.PozycjaRZiSDAO;
import dao.StronaWierszaDAO;
import dao.UkladBRDAO;
import dao.WierszBODAO;
import data.Data;
import entity.Podatnik;
import entityfk.Konto;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
import error.E;
import interceptor.ConstructorInterceptor;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class PozycjaBRZestawienieNowyView implements Serializable {
    private static final long serialVersionUID = 1L;

 
    
    @Inject
    private KontoDAOfk kontoDAO;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject 
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject
    private PozycjaRZiSDAO pozycjaRZiSDAO;
    @Inject
    private PozycjaBilansDAO pozycjaBilansDAO;
    @Inject
    private PodatnikOpodatkowanieDAO podatnikOpodatkowanieDDAO;
    private UkladBR uklad;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private UkladBRDAO ukladBRDAO;
    private boolean laczlata;
    private String bilansnadzien;
    private String bilansoddnia;
    private String kontabilansowebezprzyporzadkowania;
    private List<PozycjaRZiSBilans> pozycje;
    private List<PozycjaRZiSBilans> selectedNodes;
    @Inject
    private WpisView wpisView;
    

    public PozycjaBRZestawienieNowyView() {
         ////E.m(this);
       
       
    }

    @PostConstruct
    public void init() { //E.m(this);
        try {
            uklad = ukladBRDAO.findukladBRPodatnikRokAktywny(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            bilansnadzien = Data.ostatniDzien(wpisView);
            bilansoddnia = wpisView.getRokUprzedniSt()+"-12-31";
        } catch (Exception e){}
    }
    
      public void obliczRZiSOtwarciaRZiSData() {
        if (uklad == null || uklad.getUklad() == null) {
            uklad = ukladBRDAO.findukladBRPodatnikRokPodstawowy(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        }
        pozycje = Collections.synchronizedList(new ArrayList<>());
        pobierzPozycje(pozycje);
        Collections.sort(pozycje, new PozycjaRZiSBilanscomparator());
        List<StronaWiersza> zapisy = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        //List<StronaWiersza> zapisyRokPop = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt(), "12");
         List<Konto> pustekonta = kontoDAO.findKontaWynikowePodatnikaBezPotomkowPuste(wpisView);
        if (pustekonta.isEmpty()==false) {
            pustekonta = pustekonta.stream().filter(item->item.isSaldodosprawozdania()).collect(Collectors.toList());
            kontabilansowebezprzyporzadkowania = pustekonta.stream().map(Konto::getPelnynumer).collect(Collectors.toList()).toString();
        }
        try {
           zapisy.stream().forEach(new Consumer<StronaWiersza>() {
               @Override
               public void accept(StronaWiersza t) {
                   Konto konto = znajdzkontozpozycja(t.getKonto());
                   PozycjaRZiSBilans pozycjaznaleziona = pozycje.stream().filter(item->item.getPozycjaString().equals(konto.getPozycjaWn())).findFirst().orElse(null);
                   if (pozycjaznaleziona!=null) {
                       pozycjaznaleziona.setKwota(pozycjaznaleziona.getKwota()+t.getKwotaPLN());
                   }
               }

               private Konto znajdzkontozpozycja(Konto konto) {
                   Konto zwrot = konto;
                   while (zwrot.getPozycjaWn()==null) {
                       Konto macierzyste = zwrot.getKontomacierzyste()!=null?zwrot.getKontomacierzyste():null;
                       if (macierzyste==null) {
                           break;
                       } else  if (macierzyste!=null&&macierzyste.getPozycjaWn()!=null) {
                           zwrot = macierzyste;
                           break;
                       } else {
                           zwrot = macierzyste;
                       }
                   }
                   return zwrot;
               }
           });
              Msg.msg("i", "Pobrano układ ");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", e.getLocalizedMessage());
        }
        bilansnadzien = Data.ostatniDzien(wpisView);
    }
      
       private void wyczyscKonta(String rb, Podatnik podatnik, String rok) {
        if (rb.equals("wynikowe")) {
            List<Konto> listakont = kontoDAO.findWszystkieKontaWynikowePodatnika(podatnik, rok);
            UkladBRBean.czyscPozycjeKont(kontoDAO, listakont);
        } else {
            List<Konto> listakont = kontoDAO.findWszystkieKontaBilansowePodatnika(podatnik, rok);
            UkladBRBean.czyscPozycjeKont(kontoDAO, listakont);
        }
    }
    private void pobierzPozycje(List<PozycjaRZiSBilans> pozycje) {
        try {
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(uklad));
            if (pozycje.isEmpty()) {
               pozycje.add(new PozycjaRZiS(1, "A", "A", null, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            for (Iterator<PozycjaRZiSBilans> it = pozycje.iterator(); it.hasNext();) {
                PozycjaRZiS p = (PozycjaRZiS) it.next();
                p.setPrzyporzadkowanestronywiersza(null);
            }
        } catch (Exception e) {  
            E.e(e);
        }
    }
    public boolean isLaczlata() {
        return laczlata;
    }

    public void setLaczlata(boolean laczlata) {
        this.laczlata = laczlata;
    }

    public String getBilansnadzien() {
        return bilansnadzien;
    }

    public void setBilansnadzien(String bilansnadzien) {
        this.bilansnadzien = bilansnadzien;
    }

    public String getBilansoddnia() {
        return bilansoddnia;
    }

    public void setBilansoddnia(String bilansoddnia) {
        this.bilansoddnia = bilansoddnia;
    }

    public List<PozycjaRZiSBilans> getPozycje() {
        return pozycje;
    }

    public void setPozycje(List<PozycjaRZiSBilans> pozycje) {
        this.pozycje = pozycje;
    }

    public UkladBR getUklad() {
        return uklad;
    }

    public void setUklad(UkladBR uklad) {
        this.uklad = uklad;
    }

    public List<PozycjaRZiSBilans> getSelectedNodes() {
        return selectedNodes;
    }

    public void setSelectedNodes(List<PozycjaRZiSBilans> selectedNodes) {
        this.selectedNodes = selectedNodes;
    }
    

}

