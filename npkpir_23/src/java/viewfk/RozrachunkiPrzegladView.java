/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.TransakcjaDAO;
import embeddable.Mce;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.StronaWiersza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import pdffk.PDFRozrachunki;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RozrachunkiPrzegladView implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private List<Konto> listaKontRozrachunkowych;
    //private List<RozrachunkiTransakcje> listaRozrachunkow;
    private List<StronaWiersza> stronyWiersza;
    private List<StronaWiersza> stronyWierszawybrane;
    @Inject private KontoDAOfk kontoDAOfk;
    @Inject private StronaWierszaDAO stronaWierszaDAO;
    @Inject private Konto wybranekonto;
    //@Inject private RozrachunekfkDAO rozrachunekfkDAO;
    private TreeNodeExtended<Konto> root;
    @Inject private TreeNodeExtended<Konto> wybranekontoNode;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{kontoZapisFKView}")
    private KontoZapisFKView kontoZapisFKView;
    private String wybranaWalutaDlaKont;
    private String wybranyRodzajTransakcji;
    private String coWyswietlacRozrachunkiPrzeglad;
    private double sumawaluta;
    private double sumapl;

    public RozrachunkiPrzegladView() {
        listaKontRozrachunkowych = new ArrayList<>();
        //listaRozrachunkow = new ArrayList<>();
        stronyWiersza = new ArrayList<>();
        wybranaWalutaDlaKont = "wszystkie";
        coWyswietlacRozrachunkiPrzeglad = "nowe";
        wybranyRodzajTransakcji = "transakcje";
    }
    
    @PostConstruct
    private void init() {
        listaKontRozrachunkowych.addAll(kontoDAOfk.findKontaRozrachunkoweWszystkie(wpisView));
        zweryfikujobecnosczapisow();
        if (listaKontRozrachunkowych != null && listaKontRozrachunkowych.isEmpty()==false) {
            wybranekonto = listaKontRozrachunkowych.get(0);
            root = rootInit(listaKontRozrachunkowych);
        }
    }

    private void zweryfikujobecnosczapisow() {
        List<StronaWiersza> wierszezzapisami = stronaWierszaDAO.findStronaByPodatnikRokBilans(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        Set<Konto> zawartekontawzapisach = new HashSet<>();
        for (StronaWiersza p : wierszezzapisami) {
            if (p.getKonto().getZwyklerozrachszczegolne().equals("rozrachunkowe")) {
                zawartekontawzapisach.add(p.getKonto());
            }
        }
        for (Iterator<Konto> it = listaKontRozrachunkowych.iterator(); it.hasNext(); ) {
                Konto p = it.next();
                if (p.isMapotomkow() == false && !zawartekontawzapisach.contains(p)) {
                    it.remove();
                }
        }
    }
    
    private TreeNodeExtended<Konto> rootInit(List<Konto> wykazKont) {
        TreeNodeExtended<Konto> r = new TreeNodeExtended("root", null);
        if (!wykazKont.isEmpty()) {
            r.createTreeNodesForElement(wykazKont);
        }
        return r;
    }
   
     public void rozrachunkimiesiace() {
         wpisView.wpisAktualizuj();
         pobierzZapisyZmianaWaluty();
    }
    
    public void pobierzZapisyNaKoncieNode(NodeSelectEvent event) {
        stronyWiersza = new ArrayList<>();
        TreeNodeExtended<Konto> node = (TreeNodeExtended<Konto>) event.getTreeNode();
        wybranekonto = (Konto) node.getData();
        if (wybranyRodzajTransakcji.equals("transakcje")) {
            if (wybranaWalutaDlaKont.equals("wszystkie")) {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieNT(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt());
            } else {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieNT(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt());
            }
        } else {
            if (wybranaWalutaDlaKont.equals("wszystkie")) {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieR(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt());
            } else {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieR(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt());
            }
        }
        wykonczrozachunki();
    }
    
    public void pobierzZapisyZmianaWaluty() {
        if (wybranyRodzajTransakcji.equals("transakcje")) {
            if (wybranaWalutaDlaKont.equals("wszystkie")) {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieNT(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt());
            } else {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieNT(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt());
            }
        } else {
            if (wybranaWalutaDlaKont.equals("wszystkie")) {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieR(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt());
            } else {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieR(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt());
            }
        }
        wykonczrozachunki();
    }
    
    public void pobierzZapisyZmianaTransakcji() {
        if (wybranyRodzajTransakcji.equals("transakcje")) {
            if (wybranaWalutaDlaKont.equals("wszystkie")) {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieNT(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt());
            } else {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieNT(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt());
            }
        } else {
            if (wybranaWalutaDlaKont.equals("wszystkie")) {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieR(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt());
            } else {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieR(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt());
            }
        }
        wykonczrozachunki();
     }
    
    public void pobierzZapisyZmianaZakresu() {
        if (wybranyRodzajTransakcji.equals("transakcje")) {
            if (wybranaWalutaDlaKont.equals("wszystkie")) {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieNT(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt());
            } else {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieNT(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt());
            }
        } else {
            if (wybranaWalutaDlaKont.equals("wszystkie")) {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieR(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt());
            } else {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieR(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt());
            }
        }
       wykonczrozachunki();
    }
    
    private void wykonczrozachunki() {
        filtrrozrachunkow();
        kontoZapisFKView.pobierzZapisyNaKoncieNode(wybranekonto);
        kontoZapisFKView.setWybranekonto(wybranekonto);
        RequestContext.getCurrentInstance().update("paseknorth");
        RequestContext.getCurrentInstance().update("tabelazzapisami");
        RequestContext.getCurrentInstance().update("form:dataList");
        RequestContext.getCurrentInstance().update("form:kontenertabeli");
        RequestContext.getCurrentInstance().update("tabelazsumami");
        sumawaluta = 0.0;
        sumapl = 0.0;
    }
    
    private void filtrrozrachunkow() {
        if (coWyswietlacRozrachunkiPrzeglad != null) {
        int granicaDolna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacOd());
        int granicaGorna = Mce.getMiesiacToNumber().get(wpisView.getMiesiacDo());
        for (Iterator<StronaWiersza> it = stronyWiersza.iterator(); it.hasNext();) {
            StronaWiersza pr = it.next();
            int mc = Mce.getMiesiacToNumber().get(pr.getWiersz().getDokfk().getMiesiac());
            if (mc < granicaDolna || mc > granicaGorna) {
                it.remove();
            }
        }
        for (Iterator<StronaWiersza> p = stronyWiersza.iterator(); p.hasNext();) {
             switch (coWyswietlacRozrachunkiPrzeglad) {
                 case "rozliczone":
                     if (p.next().getPozostalo() != 0) {
                         p.remove();
                     }
                     break;
                 case "częściowo":
                     StronaWiersza px = p.next();
                     if (px.getPozostalo() == 0 || px.getRozliczono() == 0) {
                         p.remove();
                     }
                     break;
                 case "nowe":
                     if (p.next().getRozliczono() != 0) {
                         p.remove();
                     }
                     break;
                 default:
                     p.next();
                     break;
             }
         } 
        }
    }
    
    public List<Konto> complete(String query) {  
         List<Konto> results = new ArrayList<>();
         try{
             String q = query.substring(0,1);
             int i = Integer.parseInt(q);
             for(Konto p : listaKontRozrachunkowych) {
                 if(query.length()==4&&!query.contains("-")){
                     //wstawia - do ciagu konta
                     query = query.substring(0,3)+"-"+query.substring(3,4);
                 }
                 if(p.getPelnynumer().startsWith(query)) {
                     results.add(p);
                 }
             }
         } catch (Exception e){
             for(Konto p : listaKontRozrachunkowych) {
                 if(p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                     results.add(p);
                 }
             }
         }
         return results;
     }
    
    public void sumujwybrane() {
        sumawaluta = 0.0;
        sumapl = 0.0;
        if (stronyWierszawybrane != null && stronyWierszawybrane.size() > 0) {
            for (StronaWiersza p : stronyWierszawybrane) {
                if (p.getPozostalo() > 0.0) {
                    sumawaluta += p.getPozostalo();
                }
                if (p.getPozostaloPLN() > 0.0) {
                    sumapl += p.getPozostaloPLN();
                }
            }
        }
    }
    
    public void drukuj() {
        PDFRozrachunki.drukujRozrachunki(stronyWiersza, wpisView);
    }
    
    public void pobierzZapisyNaKoncieNodeUnselect(NodeUnselectEvent event) {
        stronyWiersza.clear();
        sumapl = 0.0;
        sumawaluta = 0.0;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public List<Konto> getListaKontRozrachunkowych() {
        return listaKontRozrachunkowych;
    }

    public void setListaKontRozrachunkowych(List<Konto> listaKontRozrachunkowych) {
        this.listaKontRozrachunkowych = listaKontRozrachunkowych;
    }

    public List<StronaWiersza> getStronyWiersza() {
        return stronyWiersza;
    }

    public void setStronyWiersza(List<StronaWiersza> stronyWiersza) {
        this.stronyWiersza = stronyWiersza;
    }

    

    public TreeNodeExtended<Konto> getRoot() {
        return root;
    }

    public void setRoot(TreeNodeExtended<Konto> root) {
        this.root = root;
    }

    public TreeNodeExtended<Konto> getWybranekontoNode() {
        return wybranekontoNode;
    }

    public void setWybranekontoNode(TreeNodeExtended<Konto> wybranekontoNode) {
        this.wybranekontoNode = wybranekontoNode;
    }

    public Konto getWybranekonto() {
        return wybranekonto;
    }

    public void setWybranekonto(Konto wybranekonto) {
        this.wybranekonto = wybranekonto;
    }

    public String getWybranaWalutaDlaKont() {
        return wybranaWalutaDlaKont;
    }

    public void setWybranaWalutaDlaKont(String wybranaWalutaDlaKont) {
        this.wybranaWalutaDlaKont = wybranaWalutaDlaKont;
    }

    public String getCoWyswietlacRozrachunkiPrzeglad() {
        return coWyswietlacRozrachunkiPrzeglad;
    }

    public void setCoWyswietlacRozrachunkiPrzeglad(String coWyswietlacRozrachunkiPrzeglad) {
        this.coWyswietlacRozrachunkiPrzeglad = coWyswietlacRozrachunkiPrzeglad;
    }

    public double getSumawaluta() {
        return sumawaluta;
    }

    public void setSumawaluta(double sumawaluta) {
        this.sumawaluta = sumawaluta;
    }

    public double getSumapl() {
        return sumapl;
    }

    public void setSumapl(double sumapl) {
        this.sumapl = sumapl;
    }

    public List<StronaWiersza> getStronyWierszawybrane() {
        return stronyWierszawybrane;
    }

    public void setStronyWierszawybrane(List<StronaWiersza> stronyWierszawybrane) {
        this.stronyWierszawybrane = stronyWierszawybrane;
    }

    public KontoZapisFKView getKontoZapisFKView() {
        return kontoZapisFKView;
    }

    public void setKontoZapisFKView(KontoZapisFKView kontoZapisFKView) {
        this.kontoZapisFKView = kontoZapisFKView;
    }

    public String getWybranyRodzajTransakcji() {
        return wybranyRodzajTransakcji;
    }

    public void setWybranyRodzajTransakcji(String wybranyRodzajTransakcji) {
        this.wybranyRodzajTransakcji = wybranyRodzajTransakcji;
    }
    
    
    
    
    
    
}
