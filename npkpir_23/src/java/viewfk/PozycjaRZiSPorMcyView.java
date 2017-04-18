/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.BilansBean;
import beansFK.PozycjaRZiSFKBean;
import beansFK.StronaWierszaBean;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaBiezacaDAO;
import daoFK.KontopozycjaZapisDAO;
import daoFK.PozycjaRZiSDAO;
import daoFK.UkladBRDAO;
import embeddable.Mce;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
import error.E;
import java.io.Serializable;
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
public class PozycjaRZiSPorMcyView  implements Serializable {
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

    public PozycjaRZiSPorMcyView() {
        this.rootProjektRZiS = new TreeNodeExtended("root", null);
    }
    
    
    public void zmianaukladprzegladRZiSBO() {
        BilansBean.zmianaukladprzegladRZiSBO(uklad, ukladBRDAO, wpisView, kontoDAO, kontopozycjaBiezacaDAO, kontopozycjaZapisDAO, pozycjaRZiSDAO);
        obliczRZiSOtwarciaRZiSData();
    }
    
       
    public void obliczRZiSOtwarciaRZiSData() {
        if (uklad.getUklad() == null) {
            uklad = ukladBRDAO.findukladBRPodatnikRokPodstawowy(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
        }
        List<PozycjaRZiSBilans> pozycje = BilansBean.pobierzPoszerzPozycje(uklad, pozycjaRZiSDAO, wpisView.getMiesiacWpisu());
        rootProjektRZiS.getChildren().clear();
        //mce narastajace
        //List<StronaWiersza> zapisymc = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt(), wpisView.getPodatnikObiekt());
        List<Konto> plankont = kontoDAO.findKontaWynikowePodatnikaBezPotomkow(wpisView);
        try {
            List<StronaWiersza> zapisymc = StronaWierszaBean.pobraniezapisowwynikoweMCRok(stronaWierszaDAO, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            PozycjaRZiSFKBean.ustawRootaSlot(rootProjektRZiS, pozycje, zapisymc, plankont, "01");
            zapisymc = StronaWierszaBean.pobraniezapisowwynikoweMCRok(stronaWierszaDAO, wpisView.getPodatnikObiekt(), wpisView.getRokUprzedniSt(), wpisView.getMiesiacWpisu());
            PozycjaRZiSFKBean.ustawRootaSlot(rootProjektRZiS, pozycje, zapisymc, plankont, "02");
            zapisymc = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt(), wpisView.getPodatnikObiekt());
            PozycjaRZiSFKBean.ustawRootaSlot(rootProjektRZiS, pozycje, zapisymc, plankont, "03");
            zapisymc = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView.getMiesiacWpisu(), wpisView.getRokUprzedniSt(), wpisView.getPodatnikObiekt());
            PozycjaRZiSFKBean.ustawRootaSlot(rootProjektRZiS, pozycje, zapisymc, plankont, "04");
            Msg.msg("i", "Pobrano układ ");
        } catch (Exception e) {
            E.e(e);
            rootProjektRZiS.getChildren().clear();
            Msg.msg("e", e.getLocalizedMessage());
        }
    }
    
    public String obliczszerkosc() {
        String zwrot = "width: ";
        String mcwp = wpisView.getMiesiacWpisu();
        if (wpisView.getMiesiacWpisu().equals("CR")) {
            mcwp = "01";
        }
        List mce = Mce.getMiesiaceGranica(mcwp);
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
        PdfRZiS.drukujRZiSPorMcy(rootProjektRZiS, wpisView);
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
