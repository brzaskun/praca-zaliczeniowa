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
            if (p.getTypwiersza() == 1) {
                dodajwn(p, opis, zapisynakontach, selected);
            } else if (p.getTypwiersza() == 2) {
                dodajma(p, opis, zapisynakontach, selected);
            } else {
                opis = p.getOpis();
                dodajwn(p, opis, zapisynakontach, selected);
                dodajma(p, opis, zapisynakontach, selected);
            }
            p.setZapisynakontach(zapisynakontach);
        }
        Msg.msg("i", "Zapisy na kontacg wygenerowane ");
    }

    private static void dodajwn(Wiersze p, String opis, List<Kontozapisy> zapisynakontach,Dokfk selected) {
        Kontozapisy kontozapisy = new Kontozapisy();
        kontozapisy.setKonto(p.getWierszStronaWn().getKonto().getPelnynumer());
        kontozapisy.setKontoob(p.getWierszStronaWn().getKonto());
        kontozapisy.setKontoprzeciwstawne(p.getWierszStronaMa().getKonto().getPelnynumer());
        kontozapisy.setWiersz(p);
        kontozapisy.setPodatnik(p.getDokfk().getDokfkPK().getPodatnik());
        kontozapisy.setOpis(opis);
        kontozapisy.setDokument(selected);
        kontozapisy.setKontown(p.getWierszStronaWn().getKonto().getNazwapelna());
        kontozapisy.setKontoma(p.getWierszStronaMa().getKonto().getNazwapelna());
        kontozapisy.setKwotawn(p.getWierszStronaWn().getKwota());
        kontozapisy.setKwotama(0);
        kontozapisy.setSymbolwaluty(p.getWierszStronaWn().getSymbolwaluty());
        zapisynakontach.add(kontozapisy);
    }

    private static void dodajma(Wiersze p, String opis, List<Kontozapisy> zapisynakontach,Dokfk selected) {
        Kontozapisy kontozapisy = new Kontozapisy();
        kontozapisy.setKonto(p.getWierszStronaMa().getKonto().getPelnynumer());
        kontozapisy.setKontoob(p.getWierszStronaMa().getKonto());
        kontozapisy.setKontoprzeciwstawne(p.getWierszStronaWn().getKonto().getPelnynumer());
        kontozapisy.setWiersz(p);
        kontozapisy.setPodatnik(p.getDokfk().getDokfkPK().getPodatnik());
        kontozapisy.setOpis(opis);
        kontozapisy.setDokument(selected);
        kontozapisy.setKontown(p.getWierszStronaMa().getKonto().getNazwapelna());
        kontozapisy.setKontoma(p.getWierszStronaWn().getKonto().getNazwapelna());
        kontozapisy.setKwotama(p.getWierszStronaMa().getKwota());
        kontozapisy.setKwotawn(0);
        kontozapisy.setSymbolwaluty(p.getWierszStronaMa().getSymbolwaluty());
        zapisynakontach.add(kontozapisy);
    }
}
