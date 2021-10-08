/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import data.Data;
import entity.Dzien;
import entity.Kalendarzmiesiac;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Pasekwynagrodzen;
import entity.Rachunekdoumowyzlecenia;
import entity.Skladnikwynagrodzenia;
import entity.Zmiennawynagrodzenia;
import java.util.List;
import z.Z;

/**
 *
 * @author Osito
 */
public class NaliczenieskladnikawynagrodzeniaBean {
    
    public static Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia;
    public static Naliczenieskladnikawynagrodzenia naliczenieskladnikapremia;
    public static Naliczenieskladnikawynagrodzenia naliczenieskladnikanadgodziny50;
    public static Naliczenieskladnikawynagrodzenia naliczenieskladnikanadgodziny100;
    
    public static Naliczenieskladnikawynagrodzenia createWynagrodzenie() {
        //jak bedzie spawdzal czy null to zwroci to samo i beda dwie laczone przez referencje. zmiana kwoty jednej zmieni kwote drugiej
            naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
            naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(PasekwynagrodzenBean.create());
            naliczenieskladnikawynagrodzenia.setKwotaumownazacalymc(0.0);
            naliczenieskladnikawynagrodzenia.setKwotadolistyplac(0.0);
            naliczenieskladnikawynagrodzenia.setKwotyredukujacesuma(0.0);
            naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(SkladnikwynagrodzeniaBean.createWynagrodzenie());
        return naliczenieskladnikawynagrodzenia;
    }
    
//Przepisy o charakterze powszechnym nie regulują metody obliczania wynagrodzenia w omawianych okolicznościach. Ogólnie przyjęta praktyka odwołuje się w tym zakresie do przepisu § 12 rozporządzenia o wynagrodzeniu. 
//Według powołanej regulacji w celu obliczenia wynagrodzenia, ustalonego w stawce miesięcznej w stałej wysokości, za przepracowaną część miesiąca, jeżeli pracownik nie przepracował pełnego miesiąca z uwagi na absencję "niezasiłkową":
//a) miesięczną stawkę wynagrodzenia dzieli się przez liczbę godzin przypadających do przepracowania w danym miesiącu, następnie
//b) otrzymaną kwotę mnoży się przez liczbę godzin nieprzepracowanych, dalej
//obliczoną kwotę wynagrodzenia odejmuje się od wynagrodzenia przysługującego za cały miesiąc.
//Niektórzy autorzy stosują powołany wyżej przepis w pełnym zakresie. Jednak większość praktyków poprzestaje na etapie ustalenia stawki godzinowej, aby następnie pomnożyć ją przez ilość godzin przepracowanych podczas obowiązywania tej stawki. 
//Ten drugi sposób pośrednio popiera PIP (znak pisma: GPP-471-4560-24/09/PE/RP) w swojej opinii dotyczącej obliczania wynagrodzenia po zmianie wymiaru czasu pracy.
    
    private static double obliczproporcjekwoty(Zmiennawynagrodzenia p) {
       return 0.0; 
    }
    
    public static Naliczenieskladnikawynagrodzenia createWynagrodzenieDBZlecenie(Pasekwynagrodzen pasekwynagrodzen, Skladnikwynagrodzenia skladnikwynagrodzenia, List<Dzien> listadni, double kurs, Rachunekdoumowyzlecenia rachunekdoumowyzlecenia) {
        Naliczenieskladnikawynagrodzenia zwrot = new Naliczenieskladnikawynagrodzenia();
        double zmiennawynagrodzeniakwota = rachunekdoumowyzlecenia.getKwota();
        zwrot.setPasekwynagrodzen(pasekwynagrodzen);
        zwrot.setKwotaumownazacalymc(zmiennawynagrodzeniakwota);
        zwrot.setKwotadolistyplac(zmiennawynagrodzeniakwota);
        zwrot.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
        return zwrot;
    }
    
    public static Naliczenieskladnikawynagrodzenia createWynagrodzenieDB(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, Skladnikwynagrodzenia skladnikwynagrodzenia, List<Dzien> listadni, double kurs) {
        double dniroboczewmiesiacu = 0.0;
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia() == 0) {
                dniroboczewmiesiacu++;
            }
        }
        double godzinyroboczewmiesiacu = dniroboczewmiesiacu * 8.0;
        String datastart = kalendarz.getPierwszyDzien();
        String dataend = kalendarz.getOstatniDzien();
        Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
        if (skladnikwynagrodzenia.getRodzajwynagrodzenia().getKod().equals("11")) {
            double skladnikistale = 0.0;
            double dniroboczeprzepracowane = 0.0;
            double dniroboczeprzepracowanestat = 0.0;
            for (Zmiennawynagrodzenia r : skladnikwynagrodzenia.getZmiennawynagrodzeniaList()) {
                int dzienodzmienna = Data.getDzienI(r.getDataod());
                int dziendozmienna = r.getDatado()!=null ? Data.getDzienI(r.getDatado()) : Integer.parseInt(Data.getDzien(Data.ostatniDzien(kalendarz.getRok(), kalendarz.getMc())));
                if (r.czysiemiesci(kalendarz)) {
                    skladnikistale = r.getKwota();
                    for (Dzien s : kalendarz.getDzienList()) {
                        //daje norma godzin a nie z uwzglednieniem zwolnien bo przeciez rewdukcja bedzie pozniej
                        if (s.getTypdnia() == 0 && s.getNormagodzin()>0.0 && s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                            dniroboczeprzepracowane++;
                        }
                        if (s.getTypdnia() == 0 && s.getPrzepracowano()>0.0 && s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                            dniroboczeprzepracowanestat++;
                        }
                    }
                }
            }
            double godzinyobecnoscirobocze = dniroboczeprzepracowane * 8.0;
            double godzinyobecnosciroboczestat = dniroboczeprzepracowanestat * 8.0;
            double stawkadzienna = skladnikistale / godzinyroboczewmiesiacu;
            double dowyplatyzaczasprzepracowany = Z.z(stawkadzienna * godzinyobecnoscirobocze);
            naliczenieskladnikawynagrodzenia.setDataod(datastart);
            naliczenieskladnikawynagrodzenia.setDatado(dataend);
            naliczenieskladnikawynagrodzenia.setKwotaumownazacalymc(skladnikistale);
            naliczenieskladnikawynagrodzenia.setKwotadolistyplac(dowyplatyzaczasprzepracowany);
            naliczenieskladnikawynagrodzenia.setDninalezne(dniroboczewmiesiacu);
            naliczenieskladnikawynagrodzenia.setDnifaktyczne(dniroboczeprzepracowanestat);
            naliczenieskladnikawynagrodzenia.setGodzinynalezne(godzinyroboczewmiesiacu);
            naliczenieskladnikawynagrodzenia.setGodzinyfaktyczne(godzinyobecnosciroboczestat);
            naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
            naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(pasekwynagrodzen);
        } else if (skladnikwynagrodzenia.getRodzajwynagrodzenia().getKod().equals("21")) {
            naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
            pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
        }
//        Naliczenieskladnikawynagrodzenia zwrot = new Naliczenieskladnikawynagrodzenia();
//        double zmiennawynagrodzeniakwota = 0.0;
//        if (skladnikwynagrodzenia.getRodzajwynagrodzenia().getGodzinowe0miesieczne1()==true) {
//            List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList = skladnikwynagrodzenia.getZmiennawynagrodzeniaList();
//            for (Zmiennawynagrodzenia p : zmiennawynagrodzeniaList) {
//                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("11")) {
//                    zmiennawynagrodzeniakwota = p.getKwota();
//                }
//            }
//        } else {
//            double godzinyoddelegowanie = 0.0;
//            for (Dzien p : listadni) {
//                if (p.getTypdnia()>-1 && p.getKod().equals("777")) {
//                    godzinyoddelegowanie = godzinyoddelegowanie+p.getPrzepracowano() ;
//                }
//            }
//            List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList = skladnikwynagrodzenia.getZmiennawynagrodzeniaList();
//            for (Zmiennawynagrodzenia p : zmiennawynagrodzeniaList) {
//                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("11")) {
//                    zmiennawynagrodzeniakwota = Z.z(p.getKwota()*kurs*godzinyoddelegowanie);
//                }
//            }
//        }
//        zwrot.setPasekwynagrodzen(pasekwynagrodzen);
//        zwrot.setKwotaumownazacalymc(zmiennawynagrodzeniakwota);
//        zwrot.setKwotadolistyplac(zmiennawynagrodzeniakwota);
//        zwrot.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
        return naliczenieskladnikawynagrodzenia;
    }
    
    public static Naliczenieskladnikawynagrodzenia createPremia() {
        if (naliczenieskladnikapremia == null) {
            naliczenieskladnikapremia = new Naliczenieskladnikawynagrodzenia();
            naliczenieskladnikapremia.setPasekwynagrodzen(PasekwynagrodzenBean.create());
            naliczenieskladnikapremia.setKwotaumownazacalymc(100.0);
            naliczenieskladnikapremia.setKwotadolistyplac(100.0);
            naliczenieskladnikapremia.setSkladnikwynagrodzenia(SkladnikwynagrodzeniaBean.createPremiaUznaniowa());
        }
        return naliczenieskladnikapremia;
    }
    
    public static Naliczenieskladnikawynagrodzenia createPremiaDB(Pasekwynagrodzen pasekwynagrodzen, Skladnikwynagrodzenia skladnikwynagrodzenia) {
            Naliczenieskladnikawynagrodzenia zwrot = new Naliczenieskladnikawynagrodzenia();
            double zmiennawynagrodzeniakwota = 0.0;
            List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList = skladnikwynagrodzenia.getZmiennawynagrodzeniaList();
            for (Zmiennawynagrodzenia p : zmiennawynagrodzeniaList) {
                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("21")) {
                    zmiennawynagrodzeniakwota = p.getKwota();
                }
            }
            zwrot.setPasekwynagrodzen(pasekwynagrodzen);
            zwrot.setKwotaumownazacalymc(zmiennawynagrodzeniakwota);
            zwrot.setKwotadolistyplac(0.0);
            zwrot.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
        return zwrot;
    }
    
    
    
    public static Naliczenieskladnikawynagrodzenia createNadgodziny50() {
        if (naliczenieskladnikanadgodziny50 == null) {
            naliczenieskladnikanadgodziny50 = new Naliczenieskladnikawynagrodzenia();
            naliczenieskladnikanadgodziny50.setPasekwynagrodzen(PasekwynagrodzenBean.create());
            naliczenieskladnikanadgodziny50.setKwotaumownazacalymc(100.0);
            naliczenieskladnikanadgodziny50.setKwotadolistyplac(100.0);
            naliczenieskladnikanadgodziny50.setSkladnikwynagrodzenia(SkladnikwynagrodzeniaBean.createNadgodziny50());
        }
        return naliczenieskladnikanadgodziny50;
    }
    
    public static Naliczenieskladnikawynagrodzenia createNadgodziny100() {
        if (naliczenieskladnikanadgodziny100 == null) {
            naliczenieskladnikanadgodziny100 = new Naliczenieskladnikawynagrodzenia();
            naliczenieskladnikanadgodziny100.setPasekwynagrodzen(PasekwynagrodzenBean.create());
            naliczenieskladnikanadgodziny100.setKwotaumownazacalymc(100.0);
            naliczenieskladnikanadgodziny100.setKwotadolistyplac(100.0);
            naliczenieskladnikanadgodziny100.setSkladnikwynagrodzenia(SkladnikwynagrodzeniaBean.createNadgodziny100());
        }
        return naliczenieskladnikanadgodziny100;
    }
    
   public static Naliczenieskladnikawynagrodzenia createBezZusPodatekDB(Pasekwynagrodzen pasekwynagrodzen, Skladnikwynagrodzenia skladnikwynagrodzenia) {
            Naliczenieskladnikawynagrodzenia zwrot = new Naliczenieskladnikawynagrodzenia();
            double zmiennawynagrodzeniakwota = 0.0;
            List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList = skladnikwynagrodzenia.getZmiennawynagrodzeniaList();
            for (Zmiennawynagrodzenia p : zmiennawynagrodzeniaList) {
                if (p.isAktywna()) {
                    if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("90")) {
                        zmiennawynagrodzeniakwota = p.getKwota();
                        zwrot.setPasekwynagrodzen(pasekwynagrodzen);
                        zwrot.setKwotaumownazacalymc(zmiennawynagrodzeniakwota);
                        zwrot.setKwotadolistyplac(zmiennawynagrodzeniakwota);
                        zwrot.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                    }
                }
            }
            
        return zwrot;
    }

    
    
}
