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
    
    public static void resetujBO(KontoDAOfk kontoDAOfk, String podatnik) {
        ArrayList<Konto> konta = new ArrayList<>();
        konta.addAll(kontoDAOfk.findWszystkieKontaBilansowePodatnika(podatnik));
        for (Konto p: konta) {
            p.setBoWn(0.0);
            p.setBoMa(0.0);
            p.setBlokada(false);
            kontoDAOfk.edit(p);
        }
    }
    
    public static void generujBO(KontoDAOfk kontoDAOfk, StronaWierszaDAO stronaWierszaDAO, WpisView wpisView) {
        ArrayList<StronaWiersza> kontozapisy = new ArrayList<>();
        kontozapisy.addAll(stronaWierszaDAO.findStronaByPodatnikRokWalutaBilansBO(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), "PLN"));
        ArrayList<Konto> konta = new ArrayList<>();
        konta.addAll(kontoDAOfk.findAll());
        for (StronaWiersza p : kontozapisy) {
            for (Konto r : konta) {
                if (p.getKonto().equals(r.getPelnynumer())&&p.getKwota()>0) {
                    r.setBoMa(r.getBoMa()+p.getKwota());
                    r.setBlokada(true);
                    kontoDAOfk.edit(r);
                    break;
                } else if (p.getKonto().equals(r.getPelnynumer())&&p.getKwota()>0) {
                    r.setBoWn(r.getBoWn()+p.getKwota());
                    r.setBlokada(true);
                    kontoDAOfk.edit(r);
                    break;
                }
            }
            
        }
    }
}
