/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import daoFK.KontoDAOfk;
import entityfk.Konto;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class PozycjaRZiSFKBean {
    
     public static void wyluskajNieprzyporzadkowaneAnalityki(List<Konto> pobraneKontaSyntetyczne, List<Konto> wykazkont, KontoDAOfk kontoDAO, String podatnik) {
        for (Konto p : pobraneKontaSyntetyczne) {
            if (p.getPozycja() == null) {
                if (!wykazkont.contains(p)) {
                    wykazkont.add(p);
                }
            } else if (p.getPozycja().equals("analit")) {
                List<Konto> potomki = kontoDAO.findKontaPotomnePodatnik(podatnik, p.getPelnynumer());
                for (Konto r : potomki) {
                    wyluskajNieprzyporzadkowaneAnalityki(potomki, wykazkont, kontoDAO, podatnik);
                }
            }
        }
    }
}
