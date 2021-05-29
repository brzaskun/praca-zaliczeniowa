/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import entity.Kalendarzmiesiac;
import entity.Naliczeniepotracenie;
import entity.Pasekwynagrodzen;
import entity.Skladnikpotracenia;
import entity.Umowa;
import entity.Zmiennapotracenia;
import java.util.ArrayList;
import java.util.List;
import z.Z;

/**
 *
 * @author Osito
 */
public class NaliczeniepotracenieBean {
    
    public static Naliczeniepotracenie naliczeniepotracenie;
    
    public static Naliczeniepotracenie create() {
        if (naliczeniepotracenie == null) {
            naliczeniepotracenie = new Naliczeniepotracenie();
            naliczeniepotracenie.setPasekwynagrodzen(PasekwynagrodzenBean.create());
            naliczeniepotracenie.setKwota(2000.0);
            naliczeniepotracenie.setSkladnikpotracenia(SkladnikpotraceniaBean.create());
        }
        return naliczeniepotracenie;
    }

    static Naliczeniepotracenie createPotracenieDB(Pasekwynagrodzen pasekwynagrodzen, Skladnikpotracenia skladnikpotracenia, double wolneodzajecia) {
        Naliczeniepotracenie zwrot = new Naliczeniepotracenie();
        List<Zmiennapotracenia> zmiennawynagrodzeniaList = skladnikpotracenia.getZmiennapotraceniaList();
        if (skladnikpotracenia.getSlownikpotracenia().getLimitumowaoprace()!=0.0) {
            wolneodzajecia = Z.z(wolneodzajecia*skladnikpotracenia.getSlownikpotracenia().getLimitumowaoprace()/100);
        }
        for (Zmiennapotracenia p : zmiennawynagrodzeniaList) {
            double juzrozliczono = podsumuj(pasekwynagrodzen, skladnikpotracenia);
            if (p.getKwotastala()!=0.0) {
                if (p.getKwotastala()<pasekwynagrodzen.getNetto()) {
                    zwrot.setKwota(p.getKwotastala());
                    zwrot.setPasekwynagrodzen(pasekwynagrodzen);
                } else {
                    zwrot.setKwota(pasekwynagrodzen.getNetto());
                    zwrot.setPasekwynagrodzen(pasekwynagrodzen);
                }
            } else {
                if (p.getKwotakomornicza()>juzrozliczono) {
                    double potraceniebiezace = obliczkwotakomornicza(pasekwynagrodzen.getNetto(), wolneodzajecia, juzrozliczono, p.getKwotakomornicza());
                    zwrot.setKwota(potraceniebiezace);
                    p.setKwotakomorniczarozliczona(juzrozliczono+potraceniebiezace);
                    zwrot.setPasekwynagrodzen(pasekwynagrodzen);
                }

            }
        }
        zwrot.setSkladnikpotracenia(skladnikpotracenia);
        return zwrot;
    }

    private static double podsumuj(Pasekwynagrodzen pasekwynagrodzen, Skladnikpotracenia skladnikpotracenia) {
        Umowa umowa = pasekwynagrodzen.getKalendarzmiesiac().getUmowa();
        List<Kalendarzmiesiac> kalendarzmiesiacList = umowa.getKalendarzmiesiacList();
        List<Naliczeniepotracenie> paskizpotraceniem = pobierzpaski(kalendarzmiesiacList, skladnikpotracenia);
        double suma = 0.0;
        for (Naliczeniepotracenie p : paskizpotraceniem) {
            suma = suma + p.getKwota();
        }
        return suma;
    }

    private static List<Naliczeniepotracenie> pobierzpaski(List<Kalendarzmiesiac> kalendarzmiesiacList, Skladnikpotracenia skladnikpotracenia) {
        List<Naliczeniepotracenie> paski = new ArrayList<>();
        for (Kalendarzmiesiac p : kalendarzmiesiacList) {
            for (Pasekwynagrodzen s : p.getPasekwynagrodzenList()) {
                for (Naliczeniepotracenie t : s.getNaliczeniepotracenieList()) {
                    if (t.getSkladnikpotracenia().equals(skladnikpotracenia)) {
                        paski.add(t);
                    }
                }
            }
        }
        return paski;
    }

    private static double obliczkwotakomornicza(double dowyplaty, double wolneodzajecia, double juzrozliczono, double kwotakomornicza) {
        double potracicteraz = 0.0;
        if (kwotakomornicza>juzrozliczono) {
            double pozostalodorozliczenia = kwotakomornicza-juzrozliczono;
            if (dowyplaty>wolneodzajecia) {
                double maxkomornik = Z.z(dowyplaty-wolneodzajecia);
                if (maxkomornik>pozostalodorozliczenia) {
                    potracicteraz = pozostalodorozliczenia;
                } else {
                    potracicteraz = maxkomornik;
                }
            }
        }
        return potracicteraz;
    }

   

    

  
}
