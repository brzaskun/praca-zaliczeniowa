/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beans;

import daoFK.KontoDAOfk;
import entityfk.Konto;
import java.io.Serializable;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;
import javax.persistence.PersistenceException;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class KontaFK implements Serializable{
    /**
     * @param wykazkont List<Konto>
     * @param kontoDAO KontoDAOfk
     * @param podatnikWpisu String
     */
    public static void czyszczenieKont(List<Konto> wykazkont, KontoDAOfk kontoDAO, String podatnikWpisu) {
         kontoDAO.resetujKolumneMapotomkow(podatnikWpisu);
         kontoDAO.resetujKolumneZablokowane(podatnikWpisu);
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
}
