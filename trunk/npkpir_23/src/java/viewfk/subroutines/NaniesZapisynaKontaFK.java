/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk.subroutines;

import entityfk.Kontozapisy;
import entityfk.Wiersze;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Singleton;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@Singleton
public class NaniesZapisynaKontaFK implements Serializable {

    public static void naniesZapisyNaKontach(List<Wiersze> wiersze) {
        String opis = "";
        for (Wiersze p : wiersze) {
            if (p.getZapisynakontach() != null) {
                p.getZapisynakontach().clear();
            }
            List<Kontozapisy> zapisynakontach = new ArrayList<>();
            if (p.getTypwiersza() == 1) {
                dodajwn(p, opis, zapisynakontach);
            } else if (p.getTypwiersza() == 2) {
                dodajma(p, opis, zapisynakontach);
            } else {
                opis = p.getOpis();
                dodajwn(p, opis, zapisynakontach);
                dodajma(p, opis, zapisynakontach);
            }
            p.setZapisynakontach(zapisynakontach);
        }
        Msg.msg("i", "Zapisy na kontacg wygenerowane ");
    }

    private static void dodajwn(Wiersze p, String opis, List<Kontozapisy> zapisynakontach) {
        Kontozapisy kontozapisy = new Kontozapisy();
        kontozapisy.setKonto(p.getWierszStronaWn().getKonto().getPelnynumer());
        kontozapisy.setKontoob(p.getWierszStronaWn().getKonto());
        kontozapisy.setKontoprzeciwstawne(p.getWierszStronaMa().getKonto().getPelnynumer());
        kontozapisy.setWiersz(p);
        kontozapisy.setPodatnik(p.getDokfk().getDokfkPK().getPodatnik());
        kontozapisy.setOpis(opis);
        kontozapisy.setKontown(p.getWierszStronaWn().getKonto().getNazwapelna());
        kontozapisy.setKontoma(p.getWierszStronaMa().getKonto().getNazwapelna());
        kontozapisy.setKwotawn(p.getWierszStronaWn().getKwota());
        kontozapisy.setKwotama(0);
        zapisynakontach.add(kontozapisy);
    }

    private static void dodajma(Wiersze p, String opis, List<Kontozapisy> zapisynakontach) {
        Kontozapisy kontozapisy = new Kontozapisy();
        kontozapisy.setKonto(p.getWierszStronaMa().getKonto().getPelnynumer());
        kontozapisy.setKontoob(p.getWierszStronaMa().getKonto());
        kontozapisy.setKontoprzeciwstawne(p.getWierszStronaWn().getKonto().getPelnynumer());
        kontozapisy.setWiersz(p);
        kontozapisy.setPodatnik(p.getDokfk().getDokfkPK().getPodatnik());
        kontozapisy.setOpis(opis);
        kontozapisy.setKontown(p.getWierszStronaMa().getKonto().getNazwapelna());
        kontozapisy.setKontoma(p.getWierszStronaWn().getKonto().getNazwapelna());
        kontozapisy.setKwotama(p.getWierszStronaMa().getKwota());
        kontozapisy.setKwotawn(0);
//           kontozapisy.setDokfk(x);
        zapisynakontach.add(kontozapisy);
    }
}
