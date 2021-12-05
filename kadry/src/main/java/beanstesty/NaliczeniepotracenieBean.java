/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import data.Data;
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
        for (Zmiennapotracenia p : zmiennawynagrodzeniaList) {
            double juzrozliczono = podsumuj(pasekwynagrodzen, skladnikpotracenia);
            if (p.getKwotastala()!=0.0) {
                if (p.getDatado()==null && Data.czyjestprzed(p.getDatado(), pasekwynagrodzen.getRok(), pasekwynagrodzen.getMc())) {
                    if (p.getKwotastala()<pasekwynagrodzen.getNetto()) {
                        zwrot.setKwota(p.getKwotastala());
                        zwrot.setPasekwynagrodzen(pasekwynagrodzen);
                    } else {
                        zwrot.setKwota(pasekwynagrodzen.getNetto());
                        zwrot.setPasekwynagrodzen(pasekwynagrodzen);
                    }
                }
            } else if (p.getKwotakomornicza()>0.0) {
                if (p.getKwotakomornicza()>juzrozliczono) {
                   double ilemozna = skladnikpotracenia.getRodzajpotracenia().getLimitumowaoprace();
                    double potracenie = Z.z(pasekwynagrodzen.getNettoprzedpotraceniami()*(ilemozna/100.0));
                    if (juzrozliczono+potracenie>p.getKwotakomornicza()) {
                        potracenie = Z.z(p.getKwotakomornicza()-juzrozliczono);
                    }
                    double nowenetto = Z.z(pasekwynagrodzen.getNettoprzedpotraceniami()-potracenie);
                    if (nowenetto>wolneodzajecia) {
                        zwrot.setKwota(potracenie);
                    } else {
                        zwrot.setKwota(Z.z(pasekwynagrodzen.getNettoprzedpotraceniami()-wolneodzajecia));
                    }
                    p.setKwotakomorniczarozliczona(juzrozliczono+zwrot.getKwota());
                    zwrot.setPasekwynagrodzen(pasekwynagrodzen);
                }

            } else if (p.isMaxustawowy()) {
                if (pasekwynagrodzen.getNettoprzedpotraceniami()>wolneodzajecia) {
                    double ilemozna = skladnikpotracenia.getRodzajpotracenia().getLimitumowaoprace();
                    double potracenie = Z.z(pasekwynagrodzen.getNettoprzedpotraceniami()*(ilemozna/100.0));
                    double nowenetto = Z.z(pasekwynagrodzen.getNettoprzedpotraceniami()-potracenie);
                    if (nowenetto>wolneodzajecia) {
                        zwrot.setKwota(potracenie);
                    } else {
                        zwrot.setKwota(Z.z(pasekwynagrodzen.getNettoprzedpotraceniami()-wolneodzajecia));
                    }
                    p.setKwotakomorniczarozliczona(juzrozliczono+zwrot.getKwota());
                    zwrot.setPasekwynagrodzen(pasekwynagrodzen);    
                }
                
            }
        }
        if (zwrot.getPasekwynagrodzen()!=null) {
            zwrot.setSkladnikpotracenia(skladnikpotracenia);
        } else {
            zwrot = null;
        }
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

    

   

    

  
}
