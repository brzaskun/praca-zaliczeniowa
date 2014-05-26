/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package beansFK;

import daoFK.KontoDAOfk;
import daoFK.KontoZapisyFKDAO;
import entityfk.Konto;
import entityfk.Kontozapisy;
import java.util.ArrayList;
import javax.ejb.Singleton;
import javax.inject.Named;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class BOFKBean {
    
    public static void resetujBO(KontoDAOfk kontoDAOfk) {
        ArrayList<Konto> konta = new ArrayList<>();
        konta.addAll(kontoDAOfk.findAll());
        for (Konto p: konta) {
            p.setBoWn(0.0);
            p.setBoMa(0.0);
            p.setBlokada(false);
            kontoDAOfk.edit(p);
        }
    }
    
    public static void generujBO(KontoDAOfk kontoDAOfk, KontoZapisyFKDAO kontoZapisyFKDAO, WpisView wpisView) {
        ArrayList<Kontozapisy> kontozapisy = new ArrayList<>();
        kontozapisy.addAll(kontoZapisyFKDAO.findZapisyKontoPodatnik(wpisView.getPodatnikWpisu(), "000", "PLN"));
        ArrayList<Konto> konta = new ArrayList<>();
        konta.addAll(kontoDAOfk.findAll());
        for (Kontozapisy p : kontozapisy) {
            for (Konto r : konta) {
                if (p.getKontoprzeciwstawne().equals(r.getPelnynumer())&&p.getKwotawn()>0) {
                    r.setBoMa(r.getBoMa()+p.getKwotawn());
                    r.setBlokada(true);
                    kontoDAOfk.edit(r);
                    break;
                } else if (p.getKontoprzeciwstawne().equals(r.getPelnynumer())&&p.getKwotama()>0) {
                    r.setBoWn(r.getBoWn()+p.getKwotama());
                    r.setBlokada(true);
                    kontoDAOfk.edit(r);
                    break;
                }
            }
            
        }
    }
}
