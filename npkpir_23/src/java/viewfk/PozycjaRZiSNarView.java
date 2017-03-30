/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.PozycjaRZiSFKBean;
import beansFK.StronaWierszaBean;
import beansFK.UkladBRBean;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaBiezacaDAO;
import daoFK.KontopozycjaZapisDAO;
import daoFK.PozycjaRZiSDAO;
import daoFK.UkladBRDAO;
import embeddable.Mce;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import pdffk.PdfRZiS;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PozycjaRZiSNarView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private PozycjaRZiSDAO pozycjaRZiSDAO;
    @Inject
    private KontopozycjaBiezacaDAO kontopozycjaBiezacaDAO;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject 
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject
    private UkladBR uklad;
    @Inject
    private KontoDAOfk kontoDAO;
    private TreeNodeExtended rootProjektRZiS;

    public PozycjaRZiSNarView() {
        this.rootProjektRZiS = new TreeNodeExtended("root", null);
    }
    
    
    public void zmianaukladprzegladRZiSBO() {
        UkladBRBean.ustawAktywny(uklad, ukladBRDAO);
        ArrayList<PozycjaRZiSBilans> pozycje = new ArrayList<>();
        pobierzPoszerzPozycje(pozycje);
        wyczyscKonta("wynikowe", wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        kontopozycjaBiezacaDAO.usunZapisaneKontoPozycjaPodatnikUklad(uklad, "wynikowe");
        PozycjaRZiSFKBean.naniesZachowanePozycjeNaKonta(kontoDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, uklad, wpisView, false, "wynikowe");
        obliczRZiSOtwarciaRZiSData();
    }
    
    private void pobierzPoszerzPozycje(ArrayList<PozycjaRZiSBilans> pozycje) {
        try {
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad(uklad));
            if (pozycje.isEmpty()) {
               pozycje.add(new PozycjaRZiS(1, "A", "A", 0, 0, "Kliknij tutaj i dodaj pierwszą pozycję", false));
                Msg.msg("i", "Dodaje pusta pozycje");
            }
            for (Iterator<PozycjaRZiSBilans> it = pozycje.iterator(); it.hasNext();) {
                PozycjaRZiS p = (PozycjaRZiS) it.next();
                p.setPrzyporzadkowanestronywiersza(null);
                p.setMce(new HashMap<String,Double>());
                for (String r : Mce.getMceListS()) {
                    p.getMce().put(r, 0.0);
                }
            }
            
        } catch (Exception e) {  
            E.e(e);
        }
    }
    
    private void wyczyscKonta(String rb, String podatnik, String rok) {
        if (rb.equals("wynikowe")) {
            List<Konto> listakont = kontoDAO.findWszystkieKontaWynikowePodatnika(podatnik, rok);
            UkladBRBean.czyscPozycjeKont(kontoDAO, listakont);
        } else {
            List<Konto> listakont = kontoDAO.findWszystkieKontaBilansowePodatnika(podatnik, rok);
            UkladBRBean.czyscPozycjeKont(kontoDAO, listakont);
        }
    }
    
    public void obliczRZiSOtwarciaRZiSData() {
        if (uklad.getUklad() == null) {
            uklad = ukladBRDAO.findukladBRPodatnikRokPodstawowy(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        }
        ArrayList<PozycjaRZiSBilans> pozycje = new ArrayList<>();
        pobierzPoszerzPozycje(pozycje);
        rootProjektRZiS.getChildren().clear();
        List<StronaWiersza> zapisy = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView);
        List<Konto> plankont = kontoDAO.findKontaWynikowePodatnikaBezPotomkow(wpisView);
        try {
            PozycjaRZiSFKBean.ustawRootaNar(rootProjektRZiS, pozycje, zapisy, plankont, wpisView.getMiesiacWpisu());
            Msg.msg("i", "Pobrano układ ");
        } catch (Exception e) {
            E.e(e);
            rootProjektRZiS.getChildren().clear();
            Msg.msg("e", e.getLocalizedMessage());
        }
    }
    
    public String obliczszerkosc() {
        String zwrot = "width: ";
        List mce = Mce.getMiesiaceGranica(wpisView.getMiesiacWpisu());
        switch(mce.size()) {
            case 1:
            case 2:
                zwrot += "850px;";
            case 3:
            case 4:
                zwrot += "980px;";
            case 5:
            case 6:
                zwrot += "1080px;";
            case 7:
            case 8:
                zwrot += "1280px;";
            case 9:
            case 10:
                zwrot += "1450px;";
            case 11:
            case 12:
                zwrot += "1580px;";
        }
        return zwrot;
    }
    
    public void drukujRZiSNar() {
        PdfRZiS.drukujRZiSNar(rootProjektRZiS, wpisView);
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public UkladBR getUklad() {
        return uklad;
    }

    public void setUklad(UkladBR uklad) {
        this.uklad = uklad;
    }

    public TreeNodeExtended getRootProjektRZiS() {
        return rootProjektRZiS;
    }

    public void setRootProjektRZiS(TreeNodeExtended rootProjektRZiS) {
        this.rootProjektRZiS = rootProjektRZiS;
    }
    
    
}
