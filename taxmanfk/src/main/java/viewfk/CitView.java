/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.CechazapisuBean;
import beansFK.PozycjaRZiSFKBean;
import beansFK.StronaWierszaBean;
import dao.StronaWierszaDAO;
import dao.KontoDAOfk;
import dao.PozycjaRZiSDAO;
import embeddable.CitBiezacyPozycja;
import embeddablefk.TreeNodeExtended;
import entityfk.PozycjaRZiS;
import entityfk.PozycjaRZiSBilans;
import entityfk.StronaWiersza;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import org.primefaces.model.TreeNode;
import view.WpisView;import waluty.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class CitView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<CitBiezacyPozycja> listaPrzychody;
    private List<CitBiezacyPozycja> listaKoszty;
    private double razemprzychody;
    private double razemkoszty;
    private double wynikprzedkorektami;
    private List<StronaWiersza> zapisycechakoszt;
    private List<StronaWiersza> zapisycechaprzychod;
    private double przychodypokorekcie;
    private double kosztypokorekcie;
    private double wynikpokorektach;
    private double razemzapisycechakoszt;
    private double razemzapisycechaprzychod;
    @Inject
    private PozycjaRZiSDAO pozycjaRZiSDAO;
    @Inject
    private StronaWierszaDAO stronaWierszaDAO;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @Inject
    private WpisView wpisView;

    public CitView() {
         ////E.m(this);
        this.listaPrzychody = Collections.synchronizedList(new ArrayList<>());
        this.listaKoszty = Collections.synchronizedList(new ArrayList<>());
    }
    
    public void obliczcitbiezacy() {
        this.listaPrzychody = Collections.synchronizedList(new ArrayList<>());
        this.listaKoszty = Collections.synchronizedList(new ArrayList<>());
        rozbijprzychodyikoszty(pobierzukladprzegladRZiS());
        this.przychodypokorekcie = this.razemprzychody + this.razemzapisycechaprzychod;
        this.kosztypokorekcie = this.razemkoszty + this.razemzapisycechakoszt;
        this.wynikpokorektach = this.przychodypokorekcie - this.kosztypokorekcie;
    }
    
    private TreeNodeExtended pobierzukladprzegladRZiS() {
       List<PozycjaRZiSBilans> pozycje = Collections.synchronizedList(new ArrayList<>());
       try {
            pozycje.addAll(pozycjaRZiSDAO.findRzisuklad("Podstawowy", wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt()));
            if (pozycje.isEmpty()) {
                Msg.msg("i", "Brak zdefiniowanych pozycjiw RZiS");
            }
        } catch (Exception e) {  E.e(e);
        }
        TreeNodeExtended rootProjektRZiS =  new TreeNodeExtended("root", null);
        List<StronaWiersza> zapisy = StronaWierszaBean.pobraniezapisowwynikowe(stronaWierszaDAO, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        zapisycechakoszt = CechazapisuBean.pobierzwierszezcecha(zapisy, "NKUP", wpisView.getMiesiacWpisu());
        razemzapisycechakoszt = CechazapisuBean.sumujcecha(zapisycechakoszt, "NKUP", wpisView.getMiesiacWpisu());
        zapisycechaprzychod = CechazapisuBean.pobierzwierszezcecha(zapisy, "NPUP", wpisView.getMiesiacWpisu());
        razemzapisycechaprzychod = CechazapisuBean.sumujcecha(zapisycechaprzychod, "NPUP", wpisView.getMiesiacWpisu());
        try {
            for (Iterator<PozycjaRZiSBilans> it = pozycje.iterator(); it.hasNext();) {
                PozycjaRZiS p = (PozycjaRZiS) it.next();
                p.setPrzyporzadkowanestronywiersza(null);
            }
            PozycjaRZiSFKBean.ustawRoota(rootProjektRZiS, pozycje, zapisy);
            Msg.msg("i", "Pobrano układ ");
        } catch (Exception e){
            rootProjektRZiS.getChildren().clear();
            Msg.msg("e", e.getLocalizedMessage());
        }
        return rootProjektRZiS;
    }
    
    private void rozbijprzychodyikoszty(TreeNodeExtended rootProjektRZiS) {
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
        wynikprzedkorektami = razemprzychody - razemkoszty;
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

    public double getRazemzapisycechakoszt() {
        return razemzapisycechakoszt;
    }

    public void setRazemzapisycechakoszt(double razemzapisycechakoszt) {
        this.razemzapisycechakoszt = razemzapisycechakoszt;
    }

    public double getRazemzapisycechaprzychod() {
        return razemzapisycechaprzychod;
    }

    public void setRazemzapisycechaprzychod(double razemzapisycechaprzychod) {
        this.razemzapisycechaprzychod = razemzapisycechaprzychod;
    }

    public List<StronaWiersza> getZapisycechakoszt() {
        return zapisycechakoszt;
    }

    public void setZapisycechakoszt(List<StronaWiersza> zapisycechakoszt) {
        this.zapisycechakoszt = zapisycechakoszt;
    }

    public List<StronaWiersza> getZapisycechaprzychod() {
        return zapisycechaprzychod;
    }

    public void setZapisycechaprzychod(List<StronaWiersza> zapisycechaprzychod) {
        this.zapisycechaprzychod = zapisycechaprzychod;
    }

    public double getWynikprzedkorektami() {
        return Z.z(wynikprzedkorektami);
    }

    public void setWynikprzedkorektami(double wynikprzedkorektami) {
        this.wynikprzedkorektami = wynikprzedkorektami;
    }

    public double getWynikpokorektach() {
        return Z.z(wynikpokorektach);
    }

    public void setWynikpokorektach(double wynikpokorektach) {
        this.wynikpokorektach = wynikpokorektach;
    }

    public double getPrzychodypokorekcie() {
        return Z.z(przychodypokorekcie);
    }

    public void setPrzychodypokorekcie(double przychodypokorekcie) {
        this.przychodypokorekcie = przychodypokorekcie;
    }

    public double getKosztypokorekcie() {
        return Z.z(kosztypokorekcie);
    }

    public void setKosztypokorekcie(double kosztypokorekcie) {
        this.kosztypokorekcie = kosztypokorekcie;
    }
    
    
    
}
