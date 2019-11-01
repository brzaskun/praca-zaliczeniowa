/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import entityfk.Wiersz;
import error.E;
import java.util.List;
import javax.inject.Named;
import viewfk.subroutines.ObslugaWiersza;
import waluty.Z;

/**
 *
 * @author Osito
 */
@Named

public class DialogWpisywanie {

    public static int dodajPustyWierszNaKoncu(Dokfk selected) {
        int indexwTabeli = selected.getListawierszy().size() - 1;
        int wynik = dolaczNowyWierszPusty(selected, indexwTabeli, false);
        return wynik;
    }
    
    public static void rozliczPojedynczeSaldoWBRK(Dokfk selected, int indexwTabeli, Konto kontorozrachunkowe) {
        Wiersz wierszbiezacy = selected.getListawierszy().get(indexwTabeli);
        double sumapoprzednich = 0.0;
        if (indexwTabeli==0) {
            sumapoprzednich = selected.getSaldopoczatkowe();
        } else {
            sumapoprzednich = selected.getListawierszy().get(indexwTabeli-1).getSaldoWBRK();
        }
        double kwotawiersza = obliczsaldo(wierszbiezacy, kontorozrachunkowe);
        wierszbiezacy.setSaldoWBRK(Z.z(sumapoprzednich + kwotawiersza));
    }
    
    public static void naprawsaldo(Dokfk selected,Wiersz wiersz, Konto kontorozrachunkowe) {
    if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
        double sumapoprzednich = 0.0;
        if (wiersz.getIdporzadkowy()==1) {
            sumapoprzednich = selected.getSaldopoczatkowe();
        } else {
            sumapoprzednich = selected.getListawierszy().get(wiersz.getIdporzadkowy()-2).getSaldoWBRK();
        }
        double kwotawiersza = obliczsaldo(wiersz, kontorozrachunkowe);
        double suma = Z.z(sumapoprzednich + kwotawiersza);
        wiersz.setSaldoWBRK(suma);
        selected.setSaldokoncowe(suma);
    }
}

    public static void rozliczsalda(Dokfk selected, double saldoBO, double saldoinnedok, Konto konto) {
        if (selected.getRodzajedok().getKategoriadokumentu() == 0) {
            double sumapoprzednich = 0;
            for (Wiersz p : selected.getListawierszy()) {
                double kwota = obliczsaldo(p, konto);
                sumapoprzednich += kwota;
                p.setSaldoWBRK(saldoBO + saldoinnedok + sumapoprzednich);
            }
            selected.setSaldokoncowe(saldoBO + saldoinnedok + sumapoprzednich);
        }
    }
    
    public static int dolaczNowyWierszPusty(Dokfk selected, int wierszbiezacyIndex, boolean przenumeruj) {
        Wiersz wierszbiezacy = selected.getListawierszy().get(wierszbiezacyIndex);
        Wiersz wiersznastepny = selected.nastepnyWiersz(wierszbiezacy);
        if (wiersznastepny==null) {
            try {
                boolean czyWszystkoWprowadzono = DokFKBean.sprawdzczyWnRownasieMa(wierszbiezacy);
                if (czyWszystkoWprowadzono) {
                    int nrgrupy = wierszbiezacy.getLpmacierzystego() == 0 ? wierszbiezacy.getIdporzadkowy() : wierszbiezacy.getLpmacierzystego();
                    double roznica = 0.0;
                    if (!wierszbiezacy.getDokfk().getRodzajedok().isJednostronny()) {
                        roznica = ObslugaWiersza.obliczkwotepozostala(selected, wierszbiezacy, nrgrupy);
                    }
                    if (roznica == 0) {
                        ObslugaWiersza.generujNowyWiersz0NaKoncu(selected, wierszbiezacy, przenumeruj, roznica, 0);
                    } else if (roznica != 0.0) {
                        ObslugaWiersza.wygenerujWierszRoznicowy(wierszbiezacy, false, nrgrupy, selected);
                    }
                    //to jest tez w  DokfkView.dodajPustyWierszNaKoncu selected.przeliczKwotyWierszaDoSumyDokumentu();
                }
                return 0;
            } catch (Exception e) {  
                E.e(e);
                return 1;
            }
        }
        return 2;
    }
    
      private static double obliczsaldo(Wiersz w, Konto kontorozrachunkowe) {
        double kwota = 0;
        List<StronaWiersza> strony = w.getStronyWierszaKonto();
        for (StronaWiersza r : strony) {
            if (r.getKonto().equals(kontorozrachunkowe)) {
                if (r.getWnma().equals("Wn")) {
                    kwota += r.getKwota();
                } else {
                    kwota -= r.getKwota();
                }
            }
        }
        return kwota;
    }
}
