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
import java.io.Serializable;
import java.util.List;
import javax.ejb.Stateless;
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
@Stateless
public class KontaFKBean implements Serializable{
    /**
     * @param wykazkont List<Konto>
     * @param kontoDAO KontoDAOfk
     * @param podatnikWpisu String
     */
    public static void czyszczenieKont(List<Konto> wykazkont, KontoDAOfk kontoDAO, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO) {
        for (Konto r : wykazkont) {
            r.setMapotomkow(false);
            r.setBlokada(false);
        }
        kontoDAO.editList(wykazkont);
//         kontoDAO.resetujKolumneMapotomkow(podatnikWpisu);
//         kontoDAO.resetujKolumneZablokowane(podatnikWpisu);
         for (Konto p : wykazkont) {
            if (!"0".equals(p.getMacierzyste())) {
                try {
                    Konto macierzyste = kontoDAO.findKonto(p.getMacierzyste(), wpisView);
                    macierzyste.setMapotomkow(true);
                    macierzyste.setBlokada(true);
                    kontoDAO.edit(macierzyste);
                    PlanKontFKBean.naniesprzyporzadkowanieSlownikowe(p, wpisView, kontoDAO, kontopozycjaZapisDAO);
                } catch (PersistenceException e) {
                    Msg.msg("e","Wystąpił błąd przy edycji konta. "+p.getPelnynumer());
                } catch (Exception ef) {
                    Msg.msg("e","Wystąpił błąd przy edycji konta. "+ef.getMessage()+" Nie wyedytowanododano: "+p.getPelnynumer());
                }
               
            } 
        }
    }
    
    public static void czyszczenieKont(List<Konto> wykazkont, KontoDAOfk kontoDAO, String wzorcowy, WpisView wpisView, KontopozycjaZapisDAO kontopozycjaZapisDAO) {
        for (Konto r : wykazkont) {
            r.setMapotomkow(false);
            r.setBlokada(false);
        }
        kontoDAO.editList(wykazkont);
//         kontoDAO.resetujKolumneMapotomkow(podatnikWpisu);
//         kontoDAO.resetujKolumneZablokowane(podatnikWpisu);
         for (Konto p : wykazkont) {
            if (!"0".equals(p.getMacierzyste())) {
                try {
                    Konto macierzyste = kontoDAO.findKontoWzorcowy(p.getMacierzyste(), wpisView);
                    macierzyste.setMapotomkow(true);
                    macierzyste.setBlokada(true);
                    kontoDAO.edit(macierzyste);
                    PlanKontFKBean.naniesprzyporzadkowanieSlownikoweWzorcowy(p, wpisView, kontoDAO, kontopozycjaZapisDAO);
                } catch (PersistenceException e) {
                    Msg.msg("e","Wystąpił błąd przy edycji konta. "+p.getPelnynumer());
                } catch (Exception ef) {
                    Msg.msg("e","Wystąpił błąd przy edycji konta. "+ef.getMessage()+" Nie wyedytowanododano: "+p.getPelnynumer());
                }
               
            } 
        }
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
            List<Konto> kontapotomne = kontoDAOfk.findKontaPotomnePodatnik(wpisView, konto.getPelnynumer());
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
        for (SaldoKonto r : przygotowanalista) {
            p.setBoWn(Z.z(p.getBoWn() + r.getBoWn()));
            p.setBoMa(Z.z(p.getBoMa() + r.getBoMa()));
            p.setObrotyWn(Z.z(p.getObrotyWn() + r.getObrotyWn()));
            p.setObrotyMa(Z.z(p.getObrotyMa() + r.getObrotyMa()));
            p.setObrotyBoWn(Z.z(p.getObrotyBoWn() + r.getObrotyBoWn()));
            p.setObrotyBoMa(Z.z(p.getObrotyBoMa() + r.getObrotyBoMa()));
            p.setSaldoWn(Z.z(p.getSaldoWn() + r.getSaldoWn()));
            p.setSaldoMa(Z.z(p.getSaldoMa() + r.getSaldoMa()));
        }
        p.setBoWn(Z.z(p.getBoWn()));
        p.setBoMa(Z.z(p.getBoMa()));
        p.setObrotyWn(Z.z(p.getObrotyWn()));
        p.setObrotyMa(Z.z(p.getObrotyMa()));
        p.setObrotyBoWn(Z.z(p.getObrotyBoWn()));
        p.setObrotyBoMa(Z.z(p.getObrotyBoMa()));
        p.setSaldoWn(Z.z(p.getSaldoWn()));
        p.setSaldoMa(Z.z(p.getSaldoMa()));
        return p;
    }
    
    public static void oznaczkontoPrzychod0Koszt1(Konto konto, KontoDAOfk kontoDAOfk, boolean przychod0koszt1, WpisView wpisView) {
        konto.setPrzychod0koszt1(przychod0koszt1);
        kontoDAOfk.edit(konto);
        if (konto.isMapotomkow()) {
            List<Konto> kontapotomne = kontoDAOfk.findKontaPotomnePodatnik(wpisView, konto.getPelnynumer());
            for (Konto p : kontapotomne) {
                oznaczkontoPrzychod0Koszt1(p, kontoDAOfk, przychod0koszt1, wpisView);
            }
        }
    }
    
    public static void oznaczkontoPrzychod0Koszt1Wzorcowy(Konto konto, KontoDAOfk kontoDAOfk, boolean przychod0koszt1, WpisView wpisView) {
        konto.setPrzychod0koszt1(przychod0koszt1);
        kontoDAOfk.edit(konto);
        if (konto.isMapotomkow()) {
            List<Konto> kontapotomne = kontoDAOfk.findKontaPotomneWzorcowy(wpisView.getRokWpisu(), konto.getPelnynumer());
            for (Konto p : kontapotomne) {
                oznaczkontoPrzychod0Koszt1Wzorcowy(p, kontoDAOfk, przychod0koszt1, wpisView);
            }
        }
    }
}
