/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk.subroutines;

import entity.Deklaracjevat_;
import entityfk.Dokfk;
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

    public static void naniesZapisyNaKontach(Dokfk selected) {
        List<Wiersze> wiersze = selected.getListawierszy();
        String opis = "";
        for (Wiersze p : wiersze) {
            if (p.getZapisynakontach() != null) {
                p.getZapisynakontach().clear();
            }
            List<Kontozapisy> zapisynakontach = new ArrayList<>();
            if (p.getTypWiersza() == 1) {
                dodajwn(p, opis, zapisynakontach, selected);
            } else if (p.getTypWiersza() == 2) {
                dodajma(p, opis, zapisynakontach, selected);
            } else {
                opis = p.getOpisWiersza();
                dodajwn(p, opis, zapisynakontach, selected);
                dodajma(p, opis, zapisynakontach, selected);
            }
            p.setZapisynakontach(zapisynakontach);
        }
        Msg.msg("i", "Zapisy na kontacg wygenerowane ");
    }

    private static void dodajwn(Wiersze p, String opis, List<Kontozapisy> zapisynakontach,Dokfk selected) {
        Kontozapisy kontozapisy = new Kontozapisy();
        kontozapisy.setKontoobiekt(p.getKontoWn());
        kontozapisy.setKontoprzeciwstawne(p.getKontoMa().getPelnynumer());
        kontozapisy.setWiersz(p);
        kontozapisy.setPodatnik(p.getDokfk().getDokfkPK().getPodatnik());
        kontozapisy.setOpis(opis);
        kontozapisy.setKontown(p.getKontoWn().getNazwapelna());
        kontozapisy.setKontoma(p.getKontoMa().getNazwapelna());
        kontozapisy.setKwotawn(p.getKwotaWn());
        kontozapisy.setKwotama(0);
        kontozapisy.setSymbolwaluty(p.getTabelanbp().getWaluta().getSymbolwaluty());
        zapisynakontach.add(kontozapisy);
    }

    private static void dodajma(Wiersze p, String opis, List<Kontozapisy> zapisynakontach,Dokfk selected) {
        Kontozapisy kontozapisy = new Kontozapisy();
        kontozapisy.setKontoobiekt(p.getKontoMa());
        kontozapisy.setKontoprzeciwstawne(p.getKontoWn().getPelnynumer());
        kontozapisy.setWiersz(p);
        kontozapisy.setPodatnik(p.getDokfk().getDokfkPK().getPodatnik());
        kontozapisy.setOpis(opis);
        kontozapisy.setKontown(p.getKontoMa().getNazwapelna());
        kontozapisy.setKontoma(p.getKontoWn().getNazwapelna());
        kontozapisy.setKwotama(p.getKwotaMa());
        kontozapisy.setKwotawn(0);
        kontozapisy.setSymbolwaluty(p.getTabelanbp().getWaluta().getSymbolwaluty());
        zapisynakontach.add(kontozapisy);
    }
}
