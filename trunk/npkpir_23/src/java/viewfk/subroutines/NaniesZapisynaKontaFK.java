/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk.subroutines;

import entity.Deklaracjevat_;
import entityfk.Dokfk;
import entityfk.Kontozapisy;
import entityfk.StronaWiersza;
import entityfk.Wiersz;
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
        List<Wiersz> wiersze = selected.getListawierszy();
        String opis = "";
        for (Wiersz p : wiersze) {
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

    private static void dodajwn(Wiersz p, String opis, List<Kontozapisy> zapisynakontach,Dokfk selected) {
        StronaWiersza wn = p.getStronaWn();
        StronaWiersza ma = p.getStronaMa();
        Kontozapisy kontozapisy = new Kontozapisy();
        kontozapisy.setKontoobiekt(wn.getKonto());
        kontozapisy.setKontoprzeciwstawne(ma.getKonto().getPelnynumer());
        kontozapisy.setWiersz(p);
        kontozapisy.setPodatnik(p.getDokfk().getDokfkPK().getPodatnik());
        kontozapisy.setOpis(opis);
        kontozapisy.setKontown(wn.getKonto().getNazwapelna());
        kontozapisy.setKontoma(ma.getKonto().getNazwapelna());
        kontozapisy.setKwotawn(wn.getKwota());
        kontozapisy.setKwotama(0);
        kontozapisy.setSymbolwaluty(p.getTabelanbp().getWaluta().getSymbolwaluty());
        zapisynakontach.add(kontozapisy);
    }

    private static void dodajma(Wiersz p, String opis, List<Kontozapisy> zapisynakontach,Dokfk selected) {
        StronaWiersza wn = p.getStronaWn();
        StronaWiersza ma = p.getStronaMa();
        Kontozapisy kontozapisy = new Kontozapisy();
        kontozapisy.setKontoobiekt(ma.getKonto());
        kontozapisy.setKontoprzeciwstawne(wn.getKonto().getPelnynumer());
        kontozapisy.setWiersz(p);
        kontozapisy.setPodatnik(p.getDokfk().getDokfkPK().getPodatnik());
        kontozapisy.setOpis(opis);
        kontozapisy.setKontown(ma.getKonto().getNazwapelna());
        kontozapisy.setKontoma(wn.getKonto().getNazwapelna());
        kontozapisy.setKwotama(ma.getKwota());
        kontozapisy.setKwotawn(0);
        kontozapisy.setSymbolwaluty(p.getTabelanbp().getWaluta().getSymbolwaluty());
        zapisynakontach.add(kontozapisy);
    }
}
