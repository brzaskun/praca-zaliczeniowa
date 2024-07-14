/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.BilansBean;
import beansFK.PozycjaRZiSFKBean;
import beansFK.StronaWierszaBean;
import dao.KontoDAOfk;
import dao.KontopozycjaZapisDAO;
import dao.PozycjaRZiSDAO;
import dao.StronaWierszaDAO;
import dao.UkladBRDAO;
import embeddable.Mce;
import embeddablefk.TreeNodeExtended;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import msg.Msg;
import pdffk.PdfRZiS;
import view.WpisView;
import interceptor.ConstructorInterceptor;
/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class PozycjaRZiSNarView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private PozycjaRZiSDAO pozycjaRZiSDAO;
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
        BilansBean.zmianaukladprzegladRZiSBO(uklad, ukladBRDAO, wpisView, kontoDAO, kontopozycjaZapisDAO, pozycjaRZiSDAO);
        obliczRZiSOtwarciaRZiSData();
    }
    
       
    public void obliczRZiSOtwarciaRZiSData() {
        if (uklad.getUklad() == null) {
            uklad = ukladBRDAO.findukladBRPodatnikRokPodstawowy(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        }
        List<PozycjaRZiSBilans> pozycje = BilansBean.pobierzPoszerzPozycje(uklad, pozycjaRZiSDAO, wpisView.getMiesiacWpisu());
        rootProjektRZiS.getChildren().clear();
        List<StronaWiersza> zapisy = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        try {
            PozycjaRZiSFKBean.ustawRootaNar(rootProjektRZiS, pozycje, zapisy, wpisView.getMiesiacWpisu());
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
