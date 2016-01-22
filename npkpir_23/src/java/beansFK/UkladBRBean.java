/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import daoFK.KontoDAOfk;
import entityfk.Konto;
import error.E;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author Osito
 */

public class UkladBRBean {
    
    public static int czyscPozycjeKont(KontoDAOfk kontoDAO, List<Konto> listakont) {
        int zwrot = 0;
        for (Konto p : listakont) {
                p.setKontopozycjaID(null);
                try {
                    kontoDAO.edit(p);
                } catch (Exception e) {
                    zwrot = 1;
                    E.e(e);
                }
            }
        return zwrot;
    }
    
}
