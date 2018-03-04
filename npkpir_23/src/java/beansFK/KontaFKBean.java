/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import daoFK.KontopozycjaZapisDAO;
import embeddablefk.SaldoKonto;
import entity.Podatnik;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.UkladBR;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.DoubleAccumulator;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import msg.Msg;
import view.WpisView;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named

public class KontaFKBean implements Serializable{
    /**
     * @param wykazkont List<Konto>
     * @param kontoDAO KontoDAOfk
     * @param podatnikWpisu String
     */
    public static void ustawCzyMaPotomkow(List<Konto> wykazkont, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBR ukladBR) {
        for (Konto r : wykazkont) {
            r.setMapotomkow(false);
            r.setBlokada(false);
        }
        List<Konto> sprawdzonemacierzyste = new ArrayList<>();
        for (Konto p : wykazkont) {
            if (p.getKontomacierzyste()!=null) {
                try {
                    Konto macierzyste = p.getKontomacierzyste();
                    if (!sprawdzonemacierzyste.contains(macierzyste)) {
                        naniesBlokade(macierzyste, kontoDAO);
                        sprawdzonemacierzyste.add(macierzyste);
                    }
                } catch (PersistenceException e) {
                    Msg.msg("e","Wystąpił błąd przy edycji konta. "+p.getPelnynumer());
                } catch (Exception ef) {
                    Msg.msg("e","Wystąpił błąd przy edycji konta. "+ef.getMessage()+" Nie wyedytowanododano: "+p.getPelnynumer());
                }
               
            }
        }
        kontoDAO.editList(wykazkont);
    }
    
    public static void ustawCzyMaPotomkowWzorcowy(List<Konto> wykazkont, KontoDAOfk kontoDAO, String wzorcowy, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO, UkladBR ukladBR) {
        for (Konto r : wykazkont) {
            r.setMapotomkow(false);
            r.setBlokada(false);
        }
        kontoDAO.editList(wykazkont);
        List<Konto> sprawdzonemacierzyste = new ArrayList<>();
        for (Konto p : wykazkont) {
             if (p.getKontomacierzyste()!=null) {
                try {
                    Konto macierzyste = pobierzmacierzysteWzorcowy(p, kontoDAO, wpisView);
                    naniesBlokade(macierzyste, kontoDAO);
                    sprawdzonemacierzyste.add(macierzyste);
                    PlanKontFKBean.naniesprzyporzadkowanieWzorcowy(p, wpisView, kontoDAO, kontopozycjaZapisDAO, ukladBR);
                } catch (PersistenceException e) {
                    Msg.msg("e","Wystąpił błąd przy edycji konta. "+p.getPelnynumer());
                } catch (Exception ef) {
                    Msg.msg("e","Wystąpił błąd przy edycji konta. "+ef.getMessage()+" Nie wyedytowanododano: "+p.getPelnynumer());
                }
               
            } 
        }
    }
    
    private static Konto pobierzmacierzyste(Konto p, KontoDAOfk kontoDAO, WpisView wpisView) {
        Konto macierzyste = p.getKontomacierzyste();
        return macierzyste;
    }
    
    private static Konto pobierzmacierzysteWzorcowy(Konto p, KontoDAOfk kontoDAO, WpisView wpisView) {
        Konto macierzyste = p.getKontomacierzyste();
        return macierzyste;
    }
    
    private static void naniesBlokade(Konto macierzyste, KontoDAOfk kontoDAO) {
        macierzyste.setMapotomkow(true);
        macierzyste.setBlokada(true);
        kontoDAO.edit(macierzyste);
    }
    
    public static List<StronaWiersza> pobierzZapisyRok(Konto konto, WpisView wpisView, StronaWierszaDAO stronaWierszaDAO) {
        List<StronaWiersza> pobranezapisy = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkie(wpisView.getPodatnikObiekt(), konto, wpisView.getRokWpisuSt());
        return pobranezapisy;
    }
    
    public static List<StronaWiersza> pobierzZapisyRokMc(Konto konto, Podatnik podatnik, String rok, String mc, StronaWierszaDAO stronaWierszaDAO) {
        List<StronaWiersza> pobranezapisy = stronaWierszaDAO.findStronaByPodatnikKontoRokMcWszystkie(podatnik, konto, rok, mc);
        return pobranezapisy;
    }
    
    public static List<StronaWiersza> pobierzZapisyRokSyntetyka(KontoDAOfk kontoDAOfk, WpisView wpisView, Konto konto, Podatnik podatnik, String rok, StronaWierszaDAO stronaWierszaDAO) {
        List<StronaWiersza> pobranezapisy = stronaWierszaDAO.findStronaByPodatnikKontoRokWszystkie(podatnik, konto, rok);
        if (konto.isMapotomkow()) {
            List<Konto> kontapotomne = kontoDAOfk.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), rok, konto.getPelnynumer());
            for (Konto p : kontapotomne) {
                pobranezapisy.addAll(pobierzZapisyRokSyntetyka(kontoDAOfk, wpisView, p, podatnik, rok, stronaWierszaDAO));
            }
        }
        return pobranezapisy;
    }
    
    public static List<StronaWiersza> pobierzZapisyVATRokMc(Konto konto, Podatnik podatnik, String rok, String mc, StronaWierszaDAO stronaWierszaDAO) {
        List<StronaWiersza> pobranezapisy = stronaWierszaDAO.findStronaByPodatnikKontoRokMcVAT(podatnik, konto, rok, mc);
        return pobranezapisy;
    }
    
    public static List<StronaWiersza> pobierzZapisyVATRok(Konto konto, Podatnik podatnik, String rok, StronaWierszaDAO stronaWierszaDAO) {
        List<StronaWiersza> pobranezapisy = stronaWierszaDAO.findStronaByPodatnikKontoRokVAT(podatnik, konto, rok);
        return pobranezapisy;
    }
    
    public static SaldoKonto sumujsaldakont(List<SaldoKonto> przygotowanalista) {
        SaldoKonto p = new SaldoKonto();
        DoubleAccumulator  bown = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator boma = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator obwnmc = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator obmamc = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator obwn = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator obma = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator obbown = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator obboma = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator saldown = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator saldoma = new DoubleAccumulator(Double::sum,0.d);
        przygotowanalista.parallelStream().forEach(r-> {
            bown.accumulate(r.getBoWn());
            boma.accumulate(r.getBoMa());
            obwnmc.accumulate(r.getObrotyWnMc());
            obmamc.accumulate(r.getObrotyMaMc());
            obwn.accumulate(r.getObrotyWn());
            obma.accumulate(r.getObrotyMa());
            obbown.accumulate(r.getObrotyBoWn());
            obboma.accumulate(r.getObrotyBoMa());
            saldown.accumulate(r.getSaldoWn());
            saldoma.accumulate(r.getSaldoMa());
        });
        p.setBoWn(Z.z(bown.doubleValue()));
        p.setBoMa(Z.z(boma.doubleValue()));
        p.setObrotyWnMc(Z.z(obwnmc.doubleValue()));
        p.setObrotyMaMc(Z.z(obmamc.doubleValue()));
        p.setObrotyWn(Z.z(obwn.doubleValue()));
        p.setObrotyMa(Z.z(obma.doubleValue()));
        p.setObrotyBoWn(Z.z(obbown.doubleValue()));
        p.setObrotyBoMa(Z.z(obboma.doubleValue()));
        p.setSaldoWn(Z.z(saldown.doubleValue()));
        p.setSaldoMa(Z.z(saldoma.doubleValue()));
        return p;
    }
    
    public static SaldoKonto sumujsaldakont(Map<String,SaldoKonto> przygotowanalista) {
        SaldoKonto p = new SaldoKonto();
        DoubleAccumulator  bown = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator boma = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator obwnmc = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator obmamc = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator obwn = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator obma = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator obbown = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator obboma = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator saldown = new DoubleAccumulator(Double::sum,0.d);
        DoubleAccumulator saldoma = new DoubleAccumulator(Double::sum,0.d);
        przygotowanalista.values().parallelStream().forEach(r-> {
            bown.accumulate(r.getBoWn());
            boma.accumulate(r.getBoMa());
            obwnmc.accumulate(r.getObrotyWnMc());
            obmamc.accumulate(r.getObrotyMaMc());
            obwn.accumulate(r.getObrotyWn());
            obma.accumulate(r.getObrotyMa());
            obbown.accumulate(r.getObrotyBoWn());
            obboma.accumulate(r.getObrotyBoMa());
            saldown.accumulate(r.getSaldoWn());
            saldoma.accumulate(r.getSaldoMa());
        });
        p.setBoWn(Z.z(bown.doubleValue()));
        p.setBoMa(Z.z(boma.doubleValue()));
        p.setObrotyWnMc(Z.z(obwnmc.doubleValue()));
        p.setObrotyMaMc(Z.z(obmamc.doubleValue()));
        p.setObrotyWn(Z.z(obwn.doubleValue()));
        p.setObrotyMa(Z.z(obma.doubleValue()));
        p.setObrotyBoWn(Z.z(obbown.doubleValue()));
        p.setObrotyBoMa(Z.z(obboma.doubleValue()));
        p.setSaldoWn(Z.z(saldown.doubleValue()));
        p.setSaldoMa(Z.z(saldoma.doubleValue()));
        return p;
    }
    
    public static void oznaczkontoPrzychod0Koszt1(Konto konto, KontoDAOfk kontoDAOfk, boolean przychod0koszt1, WpisView wpisView) {
        konto.setPrzychod0koszt1(przychod0koszt1);
        kontoDAOfk.edit(konto);
        if (konto.isMapotomkow()) {
            List<Konto> kontapotomne = kontoDAOfk.findKontaPotomnePodatnik(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), konto.getPelnynumer());
            for (Konto p : kontapotomne) {
                oznaczkontoPrzychod0Koszt1(p, kontoDAOfk, przychod0koszt1, wpisView);
            }
        }
    }
    
    public static void oznaczkontoPrzychod0Koszt1Wzorcowy(Konto konto, KontoDAOfk kontoDAOfk, boolean przychod0koszt1, WpisView wpisView) {
        konto.setPrzychod0koszt1(przychod0koszt1);
        kontoDAOfk.edit(konto);
        if (konto.isMapotomkow()) {
            List<Konto> kontapotomne = kontoDAOfk.findKontaPotomneWzorcowy(wpisView.getRokWpisu(), konto.getPelnynumer(), "wynikowe");
            for (Konto p : kontapotomne) {
                oznaczkontoPrzychod0Koszt1Wzorcowy(p, kontoDAOfk, przychod0koszt1, wpisView);
            }
        }
    }

    public static void pobierzKontaPotomne(List<Konto> kontamacierzyste, List<Konto> kontaostateczna, List<Konto> wykazkont) {
        List<Konto> nowepotomne = new ArrayList<>();
        for (Konto p : kontamacierzyste) {
            if (p.isMapotomkow()==true) {
                wykazkont.parallelStream().filter((r) -> (r.getKontomacierzyste() != null && r.getKontomacierzyste().equals(p))).forEachOrdered((r) -> {
                    nowepotomne.add(r);
                });
            } else {
                kontaostateczna.add(p);
            }
        }
        if (nowepotomne.size() > 0) {
            kontamacierzyste = nowepotomne;
            pobierzKontaPotomne(kontamacierzyste, kontaostateczna, wykazkont);
        } else {
            return;
        }
    }
   
    public static int czytesamekonta(Konto kontozrodlowe, Konto kontodocelowe) {
        int zwrot = 0;
        if (kontozrodlowe.equals(kontodocelowe) && kontozrodlowe.isMapotomkow()==false) {
            zwrot = 1;
        }
        return zwrot;
    }
    
}
