/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewfk;

import beansFK.RozniceKursoweBean;
import comparator.StronaWierszacomparator;
import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.TransakcjaDAO;
import embeddable.Mce;
import embeddablefk.TreeNodeExtended;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Transakcja;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;
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
    
    private List<Konto> wykazkont;
    //private List<RozrachunkiTransakcje> listaRozrachunkow;
    private List<StronaWiersza> stronyWiersza;
    private List<StronaWiersza> stronyWierszawybrane;
    @Inject private KontoDAOfk kontoDAOfk;
    @Inject private StronaWierszaDAO stronaWierszaDAO;
    @Inject private Konto wybranekonto;
    @Inject private TransakcjaDAO transakcjaDAO;
    //@Inject private RozrachunekfkDAO rozrachunekfkDAO;
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
         E.m(this);
        wykazkont = new ArrayList<>();
        //listaRozrachunkow = new ArrayList<>();
        stronyWiersza = new ArrayList<>();
        wybranaWalutaDlaKont = "wszystkie";
        coWyswietlacRozrachunkiPrzeglad = "nowe";
        wybranyRodzajTransakcji = "transakcje";
    }
    
    
    public void init() {
        wykazkont = new ArrayList<>();
        //listaRozrachunkow = new ArrayList<>();
        stronyWiersza = new ArrayList<>();
        wybranaWalutaDlaKont = "wszystkie";
        coWyswietlacRozrachunkiPrzeglad = "nowe";
        wybranyRodzajTransakcji = "transakcje";
        wykazkont = stronaWierszaDAO.findKontoByPodatnikRokBilans(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        pobierzmacierzyste(wykazkont);
        if (wykazkont != null && wykazkont.isEmpty()==false) {
            wybranekonto = wykazkont.get(0);
        }
    }

    private void pobierzmacierzyste(List<Konto> wykazkont) {
        Set<Konto> analityka = new HashSet<>();
        analityka.addAll(wykazkont);
        Set<Konto> macierzystetmp = new HashSet<>();
        do {
            macierzystetmp = new HashSet<>();
            for (Iterator<Konto> it = analityka.iterator(); it.hasNext();) {
                Konto t = it.next();
                if (t.getKontomacierzyste() != null) {
                    it.remove();
                    macierzystetmp.add(t.getKontomacierzyste());
                }
            };
            wykazkont.addAll(macierzystetmp);
            analityka.addAll(macierzystetmp);
        } while (!macierzystetmp.isEmpty());
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
    
    public void pobierzZapisyNaKoncieNode(Konto wybraneKontoNode) {
        stronyWiersza = new ArrayList<>();
        wybranekonto = serialclone.SerialClone.clone(wybraneKontoNode);
        if (wybranyRodzajTransakcji.equals("wszystkie")) {
            if (wybranaWalutaDlaKont.equals("wszystkie")) {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieNT(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt());
                stronyWiersza.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieR(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt()));
            } else {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieNT(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt());
                stronyWiersza.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieR(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt()));
            }
        } else if (wybranyRodzajTransakcji.equals("transakcje")) {
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
        if (wybranyRodzajTransakcji.equals("wszystkie")) {
            if (wybranaWalutaDlaKont.equals("wszystkie")) {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieNT(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt());
                stronyWiersza.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieR(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt()));
            } else {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieNT(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt());
                stronyWiersza.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieR(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt()));
            }
        } else if (wybranyRodzajTransakcji.equals("transakcje")) {
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
        if (wybranyRodzajTransakcji.equals("wszystkie")) {
            if (wybranaWalutaDlaKont.equals("wszystkie")) {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieNT(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt());
                stronyWiersza.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieR(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt()));
            } else {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieNT(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt());
                stronyWiersza.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieR(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt()));
            }
        } else if (wybranyRodzajTransakcji.equals("transakcje")) {
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
        if (wybranyRodzajTransakcji.equals("wszystkie")) {
            if (wybranaWalutaDlaKont.equals("wszystkie")) {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieNT(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt());
                stronyWiersza.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkieR(wpisView.getPodatnikObiekt(), wybranekonto, wpisView.getRokWpisuSt()));
            } else {
                stronyWiersza = stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieNT(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt());
                stronyWiersza.addAll(stronaWierszaDAO.findStronaByPodatnikKontoRokWalutaWszystkieR(wpisView.getPodatnikObiekt(), wybranaWalutaDlaKont, wybranekonto, wpisView.getRokWpisuSt()));
            }
        } else if (wybranyRodzajTransakcji.equals("transakcje")) {
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
        kontoZapisFKView.pobierzZapisyNaKoncieNodeRozrachunki(wybranekonto);
        kontoZapisFKView.setWybranekonto(wybranekonto);
        sumawaluta = 0.0;
        sumapl = 0.0;
        sumujwszystkie();
        RequestContext.getCurrentInstance().update("paseknorth");
        RequestContext.getCurrentInstance().update("tabelazzapisami");
        RequestContext.getCurrentInstance().update("form:dataList");
        RequestContext.getCurrentInstance().update("form:kontenertabeli");
        RequestContext.getCurrentInstance().update("tabelazsumamirozrach");
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
            } else if (pr.getDokfk().getDokfkPK().getSeriadokfk().equals("RRK")) {
                it.remove();
            }
        }
        Collections.sort(stronyWiersza, new StronaWierszacomparator());
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
    
    public void weryfikujtransakcje() {
        if (stronyWiersza != null && stronyWiersza.size() > 0) {
            if (wybranyRodzajTransakcji.equals("transakcje")) {
                for (StronaWiersza p : stronyWiersza) {
                    List<Transakcja> transakcje = pobierztransakcje(p, false);
                    if (!transakcje.isEmpty()) {
                        naprawtransakcje(p, transakcje);
                    }
                }
            } else {
                for (StronaWiersza p : stronyWiersza) {
                    List<Transakcja> transakcje = pobierztransakcje(p, true);
                    if (!transakcje.isEmpty()) {
                        naprawrozliczenia(p, transakcje);
                    }
                }
            }
            pobierzZapisyZmianaWaluty();
        }
        
    }

    private List<Transakcja> pobierztransakcje(StronaWiersza w, boolean trans0rozlicz1) {
        List<Transakcja> p = new ArrayList<>();
        if (trans0rozlicz1) {
            p = transakcjaDAO.findByRozliczajacy(w);
        } else {
            p = transakcjaDAO.findByNowaTransakcja(w);
        }
        return p;
    }
    
    private void naprawtransakcje(StronaWiersza p, List<Transakcja> transakcje) {
        boolean edytuj = false;
        for (Transakcja t : transakcje) {
            if (!p.getPlatnosci().contains(t)) {
                p.getPlatnosci().add(t);
                edytuj = true;
                StronaWiersza rozliczajacy = t.getRozliczajacy();
                if (!rozliczajacy.getNowetransakcje().contains(t)){
                    rozliczajacy.getNowetransakcje().add(t);
                    stronaWierszaDAO.edit(rozliczajacy);
                }
            }
        }
        if (edytuj) {
            stronaWierszaDAO.edit(p);
        }
    }
    
    private void naprawrozliczenia(StronaWiersza p, List<Transakcja> transakcje) {
        boolean edytuj = false;
        for (Transakcja t : transakcje) {
            if (!p.getNowetransakcje().contains(t)) {
                p.getNowetransakcje().add(t);
                edytuj = true;
                StronaWiersza nowatransakcja = t.getNowaTransakcja();
                if (!nowatransakcja.getPlatnosci().contains(t)) {
                    nowatransakcja.getPlatnosci().add(t);
                    stronaWierszaDAO.edit(nowatransakcja);
                }
            }
        }
        if (edytuj) {
            stronaWierszaDAO.edit(p);
        }
    }
    
    
    public List<Konto> complete(String query) {  
         List<Konto> results = new ArrayList<>();
         try{
             String q = query.substring(0,1);
             int i = Integer.parseInt(q);
             for(Konto p : wykazkont) {
                 if(query.length()==4&&!query.contains("-")){
                     //wstawia - do ciagu konta
                     query = query.substring(0,3)+"-"+query.substring(3,4);
                 }
                 if(p.getPelnynumer().startsWith(query)) {
                     results.add(p);
                 }
             }
         } catch (Exception e){
             for(Konto p : wykazkont) {
                 if(p.getNazwapelna().toLowerCase().contains(query.toLowerCase())) {
                     results.add(p);
                 }
             }
         }
         return results;
     }
    
    public void sumujwszystkie() {
        sumawaluta = 0.0;
        sumapl = 0.0;
        if (stronyWiersza != null && stronyWiersza.size() > 0) {
            for (StronaWiersza p : stronyWiersza) {
                 if (coWyswietlacRozrachunkiPrzeglad.equals("rozliczone")) {
                    if (p.getRozliczono() > 0.0) {
                        sumawaluta += p.getRozliczono();
                    }
                } else {
                    if (p.getPozostalo() > 0.0) {
                        sumawaluta += p.getPozostalo();
                    }
                    if (p.getPozostaloPLN() > 0.0) {
                        sumapl += p.getPozostaloPLN();
                    }
                }
            }
        }
    }
    
    
    public void sumujwybrane() {
        sumawaluta = 0.0;
        sumapl = 0.0;
        if (stronyWierszawybrane != null && stronyWierszawybrane.size() > 0) {
            for (StronaWiersza p : stronyWierszawybrane) {
                if (coWyswietlacRozrachunkiPrzeglad.equals("rozliczone")) {
                    if (p.getRozliczono() > 0.0) {
                        sumawaluta += p.getRozliczono();
                    }
                } else {
                    if (p.getPozostalo() > 0.0) {
                        sumawaluta += p.getPozostalo();
                    }
                    if (p.getPozostaloPLN() > 0.0) {
                        sumapl += p.getPozostaloPLN();
                    }
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
    
    public void oznaczjakorachunek() {
        if (stronyWierszawybrane != null && stronyWierszawybrane.size() == 1) {
           StronaWiersza w = stronyWierszawybrane.get(0);
           if (w.getRozliczono() != 0.0) {
               Msg.msg("e", "Zapis został częściowo rozliczono. Nie można dokonać zmiany.");
           } else {
                if (w.getTypStronaWiersza() == 2) {
                    w.setTypStronaWiersza(1);
                }
                w.setNowatransakcja(true);
                stronaWierszaDAO.edit(w);
                Msg.msg("Oznaczono jako rachunek "+w.toString());
           }
        } else {
            Msg.msg("e", "Należy wybrać jeden zapis w celu oznaczenia");
        }
    }
    
    public void oznaczjakoplatnosc() {
        if (stronyWierszawybrane != null && stronyWierszawybrane.size() == 1) {
           StronaWiersza w = stronyWierszawybrane.get(0);
           if (w.getRozliczono() != 0.0) {
               Msg.msg("e", "Zapis został częściowo rozliczono. Nie można dokonać zmiany.");
           } else {
                if (w.getTypStronaWiersza() == 1) {
                    w.setTypStronaWiersza(2);
                }
                w.setNowatransakcja(false);
                stronaWierszaDAO.edit(w);
                Msg.msg("Oznaczono jako płatność "+w.toString());
           }
        } else {
            Msg.msg("e", "Należy wybrać jeden zapis w celu oznaczenia");
        }
    }
    
     public void rozliczzaznaczone() {
        if (stronyWierszawybrane != null && stronyWierszawybrane.size() > 1) {
            if (RozniceKursoweBean.wiecejnizjednatransakcja(stronyWierszawybrane)) {
                Msg.msg("e", "Wśród wybranych wierszy znajdują sie dwie nowe transakcje. Nie można rozliczyć zapisów.");
            } else {
                Msg.msg("Rozliczam oznaczone transakcje");
                RozniceKursoweBean.naniestransakcje(stronyWierszawybrane);
                stronaWierszaDAO.editList(stronyWierszawybrane);
            }
        } else {
            Msg.msg("e", "Należy wybrać przynajmniej dwa zapisy po różnych stronach konta w celu rozliczenia transakcji");
        }
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public List<Konto> getWykazkont() {
        return wykazkont;
    }

    public void setWykazkont(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
    }

    public List<StronaWiersza> getStronyWiersza() {
        return stronyWiersza;
    }

    public void setStronyWiersza(List<StronaWiersza> stronyWiersza) {
        this.stronyWiersza = stronyWiersza;
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
