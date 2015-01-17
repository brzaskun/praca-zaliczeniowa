/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.CechazapisuBean;
import beansFK.PozycjaRZiSFKBean;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.PozycjaRZiSDAO;
import embeddable.CitBiezacyPozycja;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.model.TreeNode;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class CitView implements Serializable {
    private List<CitBiezacyPozycja> listaPrzychody;
    private List<CitBiezacyPozycja> listaKoszty;
    private List<StronaWiersza> zapisycechakoszt;
    private List<StronaWiersza> zapisycechaprzychod;
    private double razemprzychody;
    private double razemkoszty;
    @Inject
    private PozycjaRZiSDAO pozycjaRZiSDAO;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public CitView() {
        this.listaPrzychody = new ArrayList<>();
        this.listaKoszty = new ArrayList<>();
    }
    
    public void obliczcitbiezacy() {
        this.listaPrzychody = new ArrayList<>();
        this.listaKoszty = new ArrayList<>();
        TreeNodeExtended rootProjektRZiS = pobierzukladprzegladRZiS();
        int i = 1;
        for(Iterator<TreeNode> it = rootProjektRZiS.getChildren().iterator(); it.hasNext();) {
            PozycjaRZiS p = (PozycjaRZiS) it.next().getData();
            if (p.getFormula().equals("")) {
                CitBiezacyPozycja r = new CitBiezacyPozycja();
                r.setId(i++);
                r.setSymbolrzis(p.getPozycjaString());
                r.setOpis(p.getNazwa());
                r.setKwota(p.getKwota());
                r.setPrzychod0koszt1(p.isPrzychod0koszt1());
                if (p.isPrzychod0koszt1() == true) {
                    listaKoszty.add(r);
                    razemkoszty += r.getKwota();
                } else {
                    listaPrzychody.add(r);
                    razemprzychody += r.getKwota();
                }
            }
        }
        System.out.println("pobralem");
    }
    
    private TreeNodeExtended pobierzukladprzegladRZiS() {
       ArrayList<PozycjaRZiSBilans> pozycje = new ArrayList<>();
       try {
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad("Podstawowy", "Wzorcowy", wpisView.getRokWpisuSt()));
            if (pozycje.isEmpty()) {
                Msg.msg("i", "Brak zdefiniowanych pozycjiw RZiS");
            }
        } catch (Exception e) {
        }
        TreeNodeExtended rootProjektRZiS =  new TreeNodeExtended("root", null);
        List<StronaWiersza> zapisy = stronaWierszaDAO.findStronaByPodatnikRokWynik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        zapisycechakoszt = CechazapisuBean.pobierzwierszezcecha(zapisy, "NKUP");
        zapisycechaprzychod = CechazapisuBean.pobierzwierszezcecha(zapisy, "NPUP");
        List<Konto> plankont = kontoDAOfk.findKontaWynikowePodatnikaBezPotomkow(wpisView.getPodatnikWpisu());
        try {
            for (Iterator<PozycjaRZiSBilans> it = pozycje.iterator(); it.hasNext();) {
                PozycjaRZiS p = (PozycjaRZiS) it.next();
                p.setPrzyporzadkowanestronywiersza(null);
            }
            PozycjaRZiSFKBean.ustawRoota(rootProjektRZiS, pozycje, zapisy, plankont);
            Msg.msg("i", "Pobrano układ ");
        } catch (Exception e){
            rootProjektRZiS.getChildren().clear();
            Msg.msg("e", e.getLocalizedMessage());
        }
        return rootProjektRZiS;
    }

    public List<CitBiezacyPozycja> getListaPrzychody() {
        return listaPrzychody;
    }

    public void setListaPrzychody(List<CitBiezacyPozycja> listaPrzychody) {
        this.listaPrzychody = listaPrzychody;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<CitBiezacyPozycja> getListaKoszty() {
        return listaKoszty;
    }

    public void setListaKoszty(List<CitBiezacyPozycja> listaKoszty) {
        this.listaKoszty = listaKoszty;
    }

    public double getRazemprzychody() {
        return Z.z(razemprzychody);
    }

    public void setRazemprzychody(double razemprzychody) {
        this.razemprzychody = razemprzychody;
    }

    public double getRazemkoszty() {
        return Z.z(razemkoszty);
    }

    public void setRazemkoszty(double razemkoszty) {
        this.razemkoszty = razemkoszty;
    }
    
    
    
}
