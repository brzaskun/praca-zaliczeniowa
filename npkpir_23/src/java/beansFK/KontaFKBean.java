/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import dao.StronaWierszaDAO;
import daoFK.KontoDAOfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class KontaFKBean implements Serializable{
    /**
     * @param wykazkont List<Konto>
     * @param kontoDAO KontoDAOfk
     * @param podatnikWpisu String
     */
    public static void czyszczenieKont(List<Konto> wykazkont, KontoDAOfk kontoDAO, String podatnikWpisu) {
        for (Konto r : wykazkont) {
            r.setMapotomkow(false);
            r.setBlokada(false);
            kontoDAO.edit(r);
        }
//         kontoDAO.resetujKolumneMapotomkow(podatnikWpisu);
//         kontoDAO.resetujKolumneZablokowane(podatnikWpisu);
         for (Konto p : wykazkont) {
            if (!"0".equals(p.getMacierzyste())) {
                try {
                    Konto macierzyste = kontoDAO.findKonto(p.getMacierzyste(), podatnikWpisu);
                    macierzyste.setMapotomkow(true);
                    macierzyste.setBlokada(true);
                    kontoDAO.edit(macierzyste);
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
    
    public static List<StronaWiersza> pobierzZapisyRokMc(Konto konto, WpisView wpisView, StronaWierszaDAO stronaWierszaDAO) {
        List<StronaWiersza> pobranezapisy = stronaWierszaDAO.findStronaByPodatnikKontoRokMcWszystkie(wpisView.getPodatnikObiekt(), konto, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        return pobranezapisy;
    }
}
