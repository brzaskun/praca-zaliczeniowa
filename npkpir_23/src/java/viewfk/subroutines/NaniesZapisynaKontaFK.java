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
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
public class NaniesZapisynaKontaFK implements Serializable {

    public static void nanieszapisynakontach(List<Wiersze> wiersze) {
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
        kontozapisy.setKonto(p.getKontoWn().getPelnynumer());
        kontozapisy.setKontoob(p.getKontoWn());
        kontozapisy.setKontoprzeciwstawne(p.getKontoMa().getPelnynumer());
        kontozapisy.setWiersz(p);
        kontozapisy.setPodatnik(p.getDokfk().getDokfkPK().getPodatnik());
        kontozapisy.setOpis(opis);
        kontozapisy.setKontown(p.getKontoWn().getNazwapelna());
        kontozapisy.setKontoma(p.getKontoMa().getNazwapelna());
        kontozapisy.setKwotawn(p.getKwotaWn());
        kontozapisy.setKwotama(0);
        zapisynakontach.add(kontozapisy);
    }

    private static void dodajma(Wiersze p, String opis, List<Kontozapisy> zapisynakontach) {
        Kontozapisy kontozapisy = new Kontozapisy();
        kontozapisy.setKonto(p.getKontoMa().getPelnynumer());
        kontozapisy.setKontoob(p.getKontoMa());
        kontozapisy.setKontoprzeciwstawne(p.getKontoWn().getPelnynumer());
        kontozapisy.setWiersz(p);
        kontozapisy.setPodatnik(p.getDokfk().getDokfkPK().getPodatnik());
        kontozapisy.setOpis(opis);
        kontozapisy.setKontown(p.getKontoWn().getNazwapelna());
        kontozapisy.setKontoma(p.getKontoMa().getNazwapelna());
        kontozapisy.setKwotawn(0);
        kontozapisy.setKwotama(p.getKwotaMa());
//           kontozapisy.setDokfk(x);
        zapisynakontach.add(kontozapisy);
    }
}
