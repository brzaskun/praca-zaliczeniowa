/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import comparator.KalendarzmiesiacLastcomparator;
import comparator.KalendarzmiesiacRMcomparator;
import data.Data;
import embeddable.Mce;
import entity.Definicjalistaplac;
import entity.Dzien;
import entity.EtatPrac;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Naliczenienieobecnosc;
import entity.Naliczeniepotracenie;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnosc;
import entity.Pasekwynagrodzen;
import entity.Rodzajwynagrodzenia;
import entity.Skladnikpotracenia;
import entity.Skladnikwynagrodzenia;
import entity.Sredniadlanieobecnosci;
import entity.Zmiennawynagrodzenia;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import msg.Msg;
import z.Z;

/**
 *
 * @author Osito
 */
public class KalendarzmiesiacBean {

    public static Kalendarzmiesiac kalendarzmiesiac;

    public static Kalendarzmiesiac create() {
        if (kalendarzmiesiac == null) {
            kalendarzmiesiac = new Kalendarzmiesiac();
            kalendarzmiesiac.setAngaz(AngazBean.create());
            kalendarzmiesiac.setRok("2020");
            kalendarzmiesiac.setMc("12");
            kalendarzmiesiac.setDzienList(new ArrayList<>());
            kalendarzmiesiac.getDzienList().add(new Dzien(1, "2020-12-01", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(2, "2020-12-02", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(3, "2020-12-03", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(4, "2020-12-04", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(5, "2020-12-05", 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(6, "2020-12-06", 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(7, "2020-12-07", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(8, "2020-12-08", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(9, "2020-12-09", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(10, "2020-12-10", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(11, "2020-12-11", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(12, "2020-12-12", 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(13, "2020-12-13", 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(14, "2020-12-14", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(15, "2020-12-15", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(16, "2020-12-16", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(17, "2020-12-17", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(18, "2020-12-18", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(19, "2020-12-19", 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(20, "2020-12-20", 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(21, "2020-12-21", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(22, "2020-12-22", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(23, "2020-12-23", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(24, "2020-12-24", 3, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(25, "2020-12-25", 3, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(26, "2020-12-26", 3, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(27, "2020-12-27", 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(28, "2020-12-28", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(29, "2020-12-29", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(30, "2020-12-30", 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(31, "2020-12-31", 0, 8, 8, kalendarzmiesiac));
        }
        return kalendarzmiesiac;
    }

    public static void create(Kalendarzmiesiac kalendarzmiesiac) {
        String data = kalendarzmiesiac.getRok() + "-" + kalendarzmiesiac.getMc() + "-";
        int licznik = 1;
        kalendarzmiesiac.setDzienList(new ArrayList<>());
        kalendarzmiesiac.getDzienList().add(new Dzien(1, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(2, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(3, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(4, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(5, data + licznik++, 1, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(6, data + licznik++, 2, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(7, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(8, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(9, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(10, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(11, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(12, data + licznik++, 1, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(13, data + licznik++, 2, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(14, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(15, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(16, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(17, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(18, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(19, data + licznik++, 1, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(20, data + licznik++, 2, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(21, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(22, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(23, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(24, data + licznik++, 3, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(25, data + licznik++, 3, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(26, data + licznik++, 3, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(27, data + licznik++, 2, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(28, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(29, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(30, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(31, data + licznik++, 0, 8, 8, kalendarzmiesiac));
    }

    public static void reset(Kalendarzmiesiac kalendarzmiesiac) {
        String data = kalendarzmiesiac.getRok() + "-" + kalendarzmiesiac.getMc() + "-";
        String data2 = kalendarzmiesiac.getRok() + "-" + kalendarzmiesiac.getMc() + "-0";
        int licznik = 1;
        kalendarzmiesiac.setDzienList(new ArrayList<>());
        kalendarzmiesiac.getDzienList().add(new Dzien(1, data2 + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(2, data2 + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(3, data2 + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(4, data2 + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(5, data2 + licznik++, 1, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(6, data2 + licznik++, 2, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(7, data2 + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(8, data2 + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(9, data2 + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(10, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(11, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(12, data + licznik++, 1, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(13, data + licznik++, 2, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(14, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(15, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(16, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(17, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(18, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(19, data + licznik++, 1, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(20, data + licznik++, 2, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(21, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(22, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(23, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(24, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(25, data + licznik++, 3, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(26, data + licznik++, 3, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(27, data + licznik++, 2, 0, 0, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(28, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(29, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(30, data + licznik++, 0, 8, 8, kalendarzmiesiac));
        kalendarzmiesiac.getDzienList().add(new Dzien(31, data + licznik++, 0, 8, 8, kalendarzmiesiac));
    }

    static void dodajnieobecnosc(Kalendarzmiesiac kalendarz, Nieobecnosc nieobecnosc, Pasekwynagrodzen pasekwynagrodzen, List<Kalendarzmiesiac> kalendarzList) {
        int dzienod = Integer.parseInt(Data.getDzien(nieobecnosc.getDataod()));
        int dziendo = Integer.parseInt(Data.getDzien(nieobecnosc.getDatado()));
        for (int i = dzienod; i < dziendo + 1; i++) {
            for (Dzien p : kalendarz.getDzienList()) {
                if (p.getNrdnia() == i) {
                    if (nieobecnosc.getKod().equals("D")) {
                        p.setNormagodzin(0.0);
                    }
                    p.setPrzepracowano(0.0);
                    p.setKod(nieobecnosc.getKod());
                    break;
                }
            }
        }
        if (nieobecnosc.getKod().equals("CH")) {
            naliczskladnikiwynagrodzeniazaChorobe(kalendarz, nieobecnosc, pasekwynagrodzen, null, null, 0.0, false);
        } else if (nieobecnosc.getKod().equals("U") || nieobecnosc.getKod().equals("UZ") || nieobecnosc.getKod().equals("O") || nieobecnosc.getKod().equals("MD")) {
            naliczskladnikiwynagrodzeniazaUrlop(kalendarz, nieobecnosc, pasekwynagrodzen, kalendarzList);
        } else if (nieobecnosc.getKod().equals("X") || nieobecnosc.getKod().equals("NP") || nieobecnosc.getKod().equals("NN")) {
            naliczskladnikiwynagrodzeniazaOkresnieprzepracowany(kalendarz, nieobecnosc, pasekwynagrodzen, "X");
        } else if (nieobecnosc.getKod().equals("D")) {
            naliczskladnikiwynagrodzeniazaOkresnieprzepracowany(kalendarz, nieobecnosc, pasekwynagrodzen, "D");
        }
    }

    static void dodajnieobecnoscDB(Kalendarzmiesiac kalendarz, List<Nieobecnosc> nieobecnosclista, Pasekwynagrodzen pasekwynagrodzen, List<Kalendarzmiesiac> kalendarzList,
            double kurs, Definicjalistaplac definicjalistaplac, Definicjalistaplac definicjadlazasilkow, double limitpodstawyzasilkow, boolean jestoodelegowanie) {
        if (nieobecnosclista != null && !nieobecnosclista.isEmpty()) {
            for (Nieobecnosc nieobecnosc : nieobecnosclista) {
                String kod = nieobecnosc.getKod();
                if (kod.equals("ZC")) {
                    //zasilek chorobowy
                    naliczskladnikiwynagrodzeniazaChorobe(kalendarz, nieobecnosc, pasekwynagrodzen, definicjalistaplac, definicjadlazasilkow, limitpodstawyzasilkow, jestoodelegowanie);
                } else if (kod.equals("W")) {
                    //zasilek chorobowy
                    naliczskladnikiwynagrodzeniazaChorobe(kalendarz, nieobecnosc, pasekwynagrodzen, definicjalistaplac, definicjadlazasilkow, limitpodstawyzasilkow, jestoodelegowanie);
                } else if (kod.equals("CH")) {
                    //wynagrodzenie za czas niezdolnosci od pracy
                    naliczskladnikiwynagrodzeniazaChorobe(kalendarz, nieobecnosc, pasekwynagrodzen, definicjalistaplac, null, limitpodstawyzasilkow, jestoodelegowanie);
                } else if (kod.equals("UO")) {
                    //wynagrodzenie za czas niezdolnosci od pracy
                    naliczskladnikiwynagrodzeniazaChorobe(kalendarz, nieobecnosc, pasekwynagrodzen, definicjalistaplac, definicjadlazasilkow, limitpodstawyzasilkow, jestoodelegowanie);
                } else if (kod.equals("U") || kod.equals("UZ") || kod.equals("O") || kod.equals("MD")) {
                    //urlop wypoczynowy
                    naliczskladnikiwynagrodzeniazaUrlop(kalendarz, nieobecnosc, pasekwynagrodzen, kalendarzList);
                } else if (kod.equals("UD")) {
                    //urlop wypoczynowy
                    naliczskladnikiwynagrodzeniazaUrlopOddelegowanie(kalendarz, nieobecnosc, pasekwynagrodzen, kurs);
                } else if (kod.equals("X") || kod.equals("NP") || kod.equals("NN")) {
                    //urlopo bezpłatny
                    naliczskladnikiwynagrodzeniazaOkresnieprzepracowany(kalendarz, nieobecnosc, pasekwynagrodzen, kod);
                } else if (kod.equals("D")) {
                    //rozpoczęcie umowy w trakcie meisiąca
                    naliczskladnikiwynagrodzeniazaOkresnieprzepracowany(kalendarz, nieobecnosc, pasekwynagrodzen, "D");
                } else if (kod.equals("Z")) {
                    //oddelegowanie
                    naliczskladnikiwynagrodzeniazaOkresnieprzepracowany(kalendarz, nieobecnosc, pasekwynagrodzen, "Z");
                }
            }
        }
    }

    static void naliczskladnikiwynagrodzenia(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen) {
        double dniroboczewmiesiacu = 0.0;
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia() == 0) {
                dniroboczewmiesiacu++;
            }
        }
        double godzinyroboczewmiesiacu = dniroboczewmiesiacu * 8.0;
        String datastart = kalendarz.getPierwszyDzien();
        String dataend = kalendarz.getOstatniDzien();
        for (Skladnikwynagrodzenia p : kalendarz.getAngaz().getSkladnikwynagrodzeniaList()) {
            if (p.getRodzajwynagrodzenia().getKod().equals("11")) {
                double skladnikistale = 0.0;
                int i = 0;
                double dniroboczeprzepracowane = 0.0;
                for (Zmiennawynagrodzenia r : p.getZmiennawynagrodzeniaList()) {
                    int dzienodzmienna = Data.getDzienI(r.getDataod());
                    int dziendozmienna = Data.getDzienI(r.getDatado());
                    skladnikistale = r.getKwota();
                    for (Dzien s : kalendarz.getDzienList()) {
                        if (s.getTypdnia() == 0 && s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                            dniroboczeprzepracowane++;
                        }
                    }
                }
                double godzinyobecnoscirobocze = dniroboczeprzepracowane * 8.0;
                double stawkagodzinowa = Z.z4(skladnikistale / godzinyroboczewmiesiacu);
                double dowyplatyzaczasnieobecnosci = Z.z(stawkagodzinowa * godzinyobecnoscirobocze);
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createWynagrodzenie();
                naliczenieskladnikawynagrodzenia.setId(i++);
                naliczenieskladnikawynagrodzenia.setDataod(datastart);
                naliczenieskladnikawynagrodzenia.setDatado(dataend);
                naliczenieskladnikawynagrodzenia.setKwotaumownazacalymc(skladnikistale);
                naliczenieskladnikawynagrodzenia.setKwotadolistyplac(dowyplatyzaczasnieobecnosci);
                naliczenieskladnikawynagrodzenia.setDninalezne(dniroboczewmiesiacu);
                naliczenieskladnikawynagrodzenia.setDnifaktyczne(dniroboczeprzepracowane);
                naliczenieskladnikawynagrodzenia.setGodzinynalezne(godzinyroboczewmiesiacu);
                naliczenieskladnikawynagrodzenia.setGodzinyfaktyczne(godzinyobecnoscirobocze);
                pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
            } else if (p.getRodzajwynagrodzenia().getKod().equals("21")) {
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createPremia();
                pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
            }
        }
    }

    static boolean naliczskladnikiwynagrodzeniaDBsymulacja(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, double kwota) {
        Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
        naliczenieskladnikawynagrodzenia.setKwotaumownazacalymc(kwota);
        naliczenieskladnikawynagrodzenia.setKwotadolistyplac(kwota);
        Skladnikwynagrodzenia skladnikwynagrodzenia = new Skladnikwynagrodzenia();
        Rodzajwynagrodzenia rodzajwynagrodzenia = new Rodzajwynagrodzenia();
        skladnikwynagrodzenia.setRodzajwynagrodzenia(rodzajwynagrodzenia);
        naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
        pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
        return false;
    }

    static boolean naliczskladnikiwynagrodzeniaDBZlecenieSymulacja(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, double kwota) {
        Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
        double zmiennawynagrodzeniakwota = kwota;
        naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(pasekwynagrodzen);
        naliczenieskladnikawynagrodzenia.setKwotaumownazacalymc(zmiennawynagrodzeniakwota);
        naliczenieskladnikawynagrodzenia.setKwotadolistyplac(zmiennawynagrodzeniakwota);
        Skladnikwynagrodzenia skladnikwynagrodzenia = new Skladnikwynagrodzenia();
        Rodzajwynagrodzenia rodzajwynagrodzenia = new Rodzajwynagrodzenia();
        skladnikwynagrodzenia.setRodzajwynagrodzenia(rodzajwynagrodzenia);
        naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
        pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
        return false;
    }

    static void naliczskladnikiwynagrodzeniaDB(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, double kurs, double wynagrodzenieminimalne, Kalendarzwzor kalendarzwzor) {
        for (Skladnikwynagrodzenia p : kalendarz.getAngaz().getSkladnikwynagrodzeniaList()) {
            //trzeba usunac tylkospuerplace==true
            if (p.getRodzajwynagrodzenia().isTylkosuperplace() == false || p.getRodzajwynagrodzenia().isTylkosuperplace() == true) {
                if (p.getRodzajwynagrodzenia().getKod().equals("11") && p.isOddelegowanie() == false) {
                    List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createWynagrodzenieDB(kalendarz, pasekwynagrodzen, p, kurs, wynagrodzenieminimalne, kalendarzwzor);
                    pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().addAll(naliczenieskladnikawynagrodzenia);
                } else if (p.getRodzajwynagrodzenia().getKod().equals("11") && p.isOddelegowanie() == true) {
                    List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createWynagrodzenieDBOddelegowanie(kalendarz, pasekwynagrodzen, p, kurs);
                    pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().addAll(naliczenieskladnikawynagrodzenia);
                } else if (p.getRodzajwynagrodzenia().getKod().equals("13")) {
                    List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createWynagrodzenieDB(kalendarz, pasekwynagrodzen, p, kurs, wynagrodzenieminimalne, kalendarzwzor);
                    pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().addAll(naliczenieskladnikawynagrodzenia);
                } else if (p.getRodzajwynagrodzenia().getKod().equals("21")) {
                    Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createPremiaDB(kalendarz, pasekwynagrodzen, p);
                    //trzeba dodac daty do skladniow
                    //if (naliczenieskladnikawynagrodzenia.getKwotadolistyplac() != 0.0) {
                    pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
                    //}
                } else if (p.getRodzajwynagrodzenia().getKod().equals("PU")) {
                    Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createPremiaDB(kalendarz, pasekwynagrodzen, p);
                    //trzeba dodac daty do skladniow
                    //if (naliczenieskladnikawynagrodzenia.getKwotadolistyplac() != 0.0) {
                    pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
                    //}
                } else if (p.getRodzajwynagrodzenia().getKod().equals("90")) {
                    Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createBezZusPodatekDB(kalendarz, pasekwynagrodzen, p);
                    if (naliczenieskladnikawynagrodzenia.getKwotaumownazacalymc() != 0.0) {
                        pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
                    }
                } else if (p.getRodzajwynagrodzenia().getKod().equals("50") || p.getRodzajwynagrodzenia().getKod().equals("70")) {
                    Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createPremiaDB(kalendarz, pasekwynagrodzen, p);
                    if (naliczenieskladnikawynagrodzenia.getKwotadolistyplac() > 0.0) {
                        pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
                    }
                } else if (p.getRodzajwynagrodzenia().getKod().equals("12")) {

                } else {
                    Msg.msg("w", "Nie ma formuly naliczenia skladnika wynagrodzzenia " + p.getRodzajwynagrodzenia().getOpisskrocony());
                    System.out.println("Nie ma formuly naliczenia skladnika wynagrodzzenia " + p.getRodzajwynagrodzenia().getOpisskrocony());
                }
            }
        }
    }

    static double naliczskladnikiPPKDB(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, double kurs, double wynagrodzenieminimalne, Kalendarzwzor kalendarzwzor) {
        double kwotappk = 0.0;
        for (Skladnikwynagrodzenia p : kalendarz.getAngaz().getSkladnikwynagrodzeniaList()) {
            if (p.getRodzajwynagrodzenia().isTylkosuperplace() == false) {
                if (p.getRodzajwynagrodzenia().getKod().equals("98")) {
                    List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createWynagrodzenieDBPPK(kalendarz, pasekwynagrodzen, p, kurs);
                    pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().addAll(naliczenieskladnikawynagrodzenia);
                    for (Naliczenieskladnikawynagrodzenia skl : naliczenieskladnikawynagrodzenia) {
                        kwotappk = Z.z(kwotappk + skl.getKwotadolistyplac());
                    }
                }
            }
        }
        return kwotappk;
    }

    static boolean naliczskladnikiwynagrodzeniaDBZlecenie(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, double kurs,
            double zmiennawynagrodzeniakwota, double iloscgodzin, double zmiennawynagrodzeniakwotaodelegowanie, double zmiennawynagrodzeniakwotaodelegowaniewaluta) {
        boolean jestoddelegowanie = false;
        for (Skladnikwynagrodzenia p : kalendarz.getAngaz().getSkladnikwynagrodzeniaList()) {
            if (p.getRodzajwynagrodzenia().getKod().equals("40")) {
                double kwota = p.getRodzajwynagrodzenia().getOpispelny().contains("oddelegowanie") ? zmiennawynagrodzeniakwotaodelegowanie : zmiennawynagrodzeniakwota;
                if (p.getRodzajwynagrodzenia().getWks_serial() != 1072) {
                    zmiennawynagrodzeniakwotaodelegowaniewaluta = 0.0;
                }
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createWynagrodzenieDBZlecenie(pasekwynagrodzen, p, kalendarz.getDzienList(), kurs, kwota, zmiennawynagrodzeniakwotaodelegowaniewaluta);
                naliczenieskladnikawynagrodzenia.setGodzinyfaktyczne(iloscgodzin);
                if (naliczenieskladnikawynagrodzenia.getKwotaumownazacalymc() != 0.0) {
                    pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
                    if (p.isOddelegowanie()) {
                        jestoddelegowanie = true;
                    }
                }
            }
        }
        return jestoddelegowanie;
    }

    static boolean naliczskladnikiwynagrodzeniaDBFunkcja(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, double kurs) {
        boolean jestoddelegowanie = false;
        for (Skladnikwynagrodzenia p : kalendarz.getAngaz().getSkladnikwynagrodzeniaList()) {
            if (p.getRodzajwynagrodzenia().getKod().equals("60")) {
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createWynagrodzenieDBFunkcja(kalendarz, pasekwynagrodzen, p, kurs);
                if (naliczenieskladnikawynagrodzenia.getKwotaumownazacalymc() != 0.0) {
                    pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
                    if (p.isOddelegowanie()) {
                        jestoddelegowanie = true;
                    }
                }
            }
        }
        return jestoddelegowanie;
    }

    static boolean naliczskladnikiwynagrodzeniaDBNierezydent(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, double kurs, double zmiennawynagrodzeniakwota, double iloscgodzin, double zmiennawynagrodzeniakwotaodelegowanie) {
        boolean jestoddelegowanie = false;
        for (Skladnikwynagrodzenia p : kalendarz.getAngaz().getSkladnikwynagrodzeniaList()) {
            if (p.getRodzajwynagrodzenia().getKod().equals("NZ")) {
                double kwota = p.getRodzajwynagrodzenia().getOpispelny().contains("oddelegowanie") ? zmiennawynagrodzeniakwotaodelegowanie : zmiennawynagrodzeniakwota;
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createWynagrodzenieDBZlecenie(pasekwynagrodzen, p, kalendarz.getDzienList(), kurs, kwota, zmiennawynagrodzeniakwotaodelegowanie);
                if (naliczenieskladnikawynagrodzenia.getKwotaumownazacalymc() != 0.0) {
                    pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
                    if (p.isOddelegowanie()) {
                        jestoddelegowanie = true;
                    }
                }
            }
        }
        return jestoddelegowanie;
    }

    static void naliczskladnikipotracenia(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen) {
        for (Skladnikpotracenia p : kalendarz.getAngaz().getSkladnikpotraceniaList()) {
            Naliczeniepotracenie naliczeniepotracenie = NaliczeniepotracenieBean.create();
            pasekwynagrodzen.getNaliczeniepotracenieList().add(naliczeniepotracenie);
        }
    }

    public static void naliczskladnikipotraceniaDB(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, double wolneodzajecia) {
        for (Skladnikpotracenia p : kalendarz.getAngaz().getSkladnikpotraceniaList()) {
            Naliczeniepotracenie naliczeniepotracenie = NaliczeniepotracenieBean.createPotracenieDB(pasekwynagrodzen, p, wolneodzajecia);
            if (naliczeniepotracenie != null) {
                pasekwynagrodzen.getNaliczeniepotracenieList().add(naliczeniepotracenie);
            }
        }
    }

    public static void naliczskladnikipotraceniaPonownieDB(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, double wolneodzajecia) {
        double netto = pasekwynagrodzen.getNettoprzedpotraceniami();
        double sumazajec = 0.0;
        for (Naliczeniepotracenie p : pasekwynagrodzen.getNaliczeniepotracenieList()) {
            double dozajecia = netto - wolneodzajecia - sumazajec;
            sumazajec = sumazajec + NaliczeniepotracenieBean.przeliczPotracenieDB(pasekwynagrodzen, p, dozajecia);
        }
        pasekwynagrodzen.setPotracenia(sumazajec);
    }

    static void naliczskladnikiwynagrodzeniazaChorobe(Kalendarzmiesiac kalendarz, Nieobecnosc nieobecnosc, Pasekwynagrodzen pasekwynagrodzen, Definicjalistaplac definicjalistaplac, Definicjalistaplac definicjadlazasilkow, double limitpodstawyzasilkow, boolean jestoodelegowanie) {
        double liczbagodzinchoroby = 0.0;
        double liczbagodzinobowiazku = 0.0;
        String pierwszydzienmiesiaca = Data.pierwszyDzienKalendarz(kalendarz);
        String ostatnidzienmiesiaca = Data.ostatniDzienKalendarz(kalendarz);
        if (DataBean.czysiemiesci(pierwszydzienmiesiaca, ostatnidzienmiesiaca, nieobecnosc.getDataod(), nieobecnosc.getDatado())) {
            String dataod = Data.czyjestpo(pierwszydzienmiesiaca, nieobecnosc.getDataod()) ? nieobecnosc.getDataod() : pierwszydzienmiesiaca;
            String datado = Data.czyjestprzed(ostatnidzienmiesiaca, nieobecnosc.getDatado()) ? nieobecnosc.getDatado() : ostatnidzienmiesiaca;
            int dzienod = Data.getDzienI(dataod);
            int dziendo = Data.getDzienI(datado);
            for (Dzien p : kalendarz.getDzienList()) {
                if (p.getTypdnia() == 0 && p.getNrdnia() >= dzienod && p.getNrdnia() <= dziendo) {
                    liczbagodzinchoroby = liczbagodzinchoroby + p.getWynagrodzeniezachorobe() + p.getZasilek();
                }
            }
            double dnikalendarzoweniechoroby = Data.iletodniKalendarzowych(dataod, datado);
            boolean recznapodstawajedenskladnik = nieobecnosc.getSredniazmiennerecznie() > 0.0 ? true : false;
            for (Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia : pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList()) {
                //to z mysla o pramiach uznaniowych, ktore jako nierekukowane nei wchodza w sklad podstawyfeeffe
                if ((recznapodstawajedenskladnik && naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getKod().equals("11"))
                        || (recznapodstawajedenskladnik && naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getKod().equals("13")) || recznapodstawajedenskladnik == false) {
                    if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany() || naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getKod().equals("13") || naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getKod().equals("12")) {
                        Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
                        naliczenienieobecnosc.setDataod(dataod);
                        naliczenienieobecnosc.setDatado(datado);
                        naliczenienieobecnosc.setLiczbadniobowiazku(30);
                        naliczenienieobecnosc.setLiczbadniurlopu(dnikalendarzoweniechoroby);
                        naliczenienieobecnosc.setLiczbagodzinobowiazku(liczbagodzinobowiazku);
                        naliczenienieobecnosc.setLiczbagodzinurlopu(liczbagodzinchoroby);
                        Skladnikwynagrodzenia skladnikwynagrodzenia = naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia();
                        naliczenienieobecnosc.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                        naliczenienieobecnosc.setNieobecnosc(nieobecnosc);
                        naliczenienieobecnosc.setJakiskladnikredukowalny(naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getOpispelny());
                        double skladnikistalenetto = 0.0;
                        if (nieobecnosc.getSredniazmiennerecznie() > 0.0) {
                            double sredniadopodstawy = nieobecnosc.getSredniazmiennerecznie();
                            naliczenienieobecnosc.setPodstawadochoroby(sredniadopodstawy);
                            double procentzazwolnienie = Z.z(nieobecnosc.getZwolnienieprocent() / 100);
                            naliczenienieobecnosc.setProcentzazwolnienie(procentzazwolnienie);
                            sredniadopodstawy = sredniadopodstawy * procentzazwolnienie;
                            skladnikistalenetto = sredniadopodstawy;
                        } else {
                            double sredniadopodstawypobrana = 0.0;
                            if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getKod().equals("12")) {
                                sredniadopodstawypobrana = wyliczsredniachorobaNadgodziny(kalendarz, naliczenieskladnikawynagrodzenia, nieobecnosc, naliczenienieobecnosc, definicjalistaplac, definicjadlazasilkow);
                            } else {
                                sredniadopodstawypobrana = wyliczsredniachoroba(kalendarz, naliczenieskladnikawynagrodzenia, nieobecnosc, naliczenienieobecnosc, definicjalistaplac, definicjadlazasilkow, jestoodelegowanie);
                            }
                            double sredniadopodstawy = sredniadopodstawypobrana - (sredniadopodstawypobrana * .1371);
                            //trzeba dac te ograniczenie bo podwyzszalo podstawe dla wszystkich wyunagrodzen
                            if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getKod().equals("11")) {
                                //toi jest juz zrobione na samym poczatku
                                //                             EtatPrac pobierzetat = pasekwynagrodzen.getKalendarzmiesiac().getAngaz().pobierzetat(pasekwynagrodzen.getDatawyplaty());
                                //                            if (pobierzetat != null) {
                                //                                limitpodstawyzasilkow = Z.z(limitpodstawyzasilkow * pobierzetat.getEtat1() / pobierzetat.getEtat2());
                                //                            }
                                if (sredniadopodstawy < limitpodstawyzasilkow && pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().size()==1) {
                                    sredniadopodstawy = limitpodstawyzasilkow;
                                }
                            }
                            //hjest srednia z zus bo potemn przeciez potraca zus patrz linijki wyzej
                            naliczenienieobecnosc.setPodstawadochoroby(sredniadopodstawypobrana);
                            double procentzazwolnienie = Z.z(nieobecnosc.getZwolnienieprocent() / 100);
                            naliczenienieobecnosc.setProcentzazwolnienie(procentzazwolnienie);
                            skladnikistalenetto = sredniadopodstawy * procentzazwolnienie;
                        }
                        double skladnikistaledoredukcji = naliczenieskladnikawynagrodzenia.getKwotaumownazacalymc();
                        naliczenienieobecnosc.setSkladnikistale(skladnikistalenetto);
                        double stawkadzienna = Z.z(skladnikistalenetto / 30);
                        naliczenienieobecnosc.setStawkadzienna(stawkadzienna);
                        double dowyplatyzaczasnieobecnosci = Z.z(stawkadzienna * dnikalendarzoweniechoroby);
                        naliczenienieobecnosc.setStawkadzienna(stawkadzienna);
                        naliczenienieobecnosc.setKwota(dowyplatyzaczasnieobecnosci);
                        naliczenienieobecnosc.setKwotabezzus(dowyplatyzaczasnieobecnosci);
                        double stawkadziennaredukcji = skladnikistaledoredukcji / 30;
                        naliczenienieobecnosc.setStawkadziennaredukcji(stawkadziennaredukcji);
                        double kwotaredukcji = stawkadziennaredukcji * dnikalendarzoweniechoroby;
                        if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany()) {
                            naliczenienieobecnosc.setKwotaredukcji(kwotaredukcji);
                        }
                        naliczenienieobecnosc.setPasekwynagrodzen(pasekwynagrodzen);
                        pasekwynagrodzen.getNaliczenienieobecnoscList().add(naliczenienieobecnosc);
                    }
                }
                if (recznapodstawajedenskladnik == true && pasekwynagrodzen.getNaliczenienieobecnoscList().size() > 0) {
                    break;
                }
            }
        }
    }

    private static double wyliczsredniachoroba(Kalendarzmiesiac kalendarz, Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia, Nieobecnosc nieobecnosc, Naliczenienieobecnosc naliczenienieobecnosc,
            Definicjalistaplac definicjalistaplac, Definicjalistaplac definicjadlazasilkow, boolean jestoddelegowanie) {
        double zwrot = 0.0;
        List<Kalendarzmiesiac> kalendarze = new ArrayList<>();
        String rok = kalendarz.getRok();
        String mc = kalendarz.getMc();
        String[] poprzedniOkres = Data.poprzedniOkres(mc, rok);
        mc = poprzedniOkres[0];
        rok = poprzedniOkres[1];
        List<Kalendarzmiesiac> kalendarzmiesiacList = kalendarz.getAngaz().getKalendarzmiesiacList();
        int rokI = Integer.valueOf(rok);
        rokI = rokI-1;
        final String rokuprzedni = String.valueOf(rokI);
        final String rokS = rok;
        kalendarzmiesiacList = kalendarzmiesiacList.stream().filter(p->p.getRok().equals(rokS)||p.getRok().equals(rokuprzedni)).collect(Collectors.toList());
        Collections.sort(kalendarzmiesiacList, new KalendarzmiesiacRMcomparator());
        String dataetat = Data.ostatniDzien(kalendarz.getRok(), kalendarz.getMc());
        EtatPrac pobierzetat = kalendarz.getAngaz().pobierzetat(dataetat);
        for (Kalendarzmiesiac kal : kalendarzmiesiacList) {
            boolean czyjestZarudnienieWtrakcieMca = kal.czyjestZarudnienieWtrakcieMca();
            if (kal.getRok().equals(rok) && kal.getMc().equals(mc) && czyjestZarudnienieWtrakcieMca == false) {
                String dataetat1 = Data.ostatniDzien(kal.getRok(), kal.getMc());
                EtatPrac pobierzetat1 = kalendarz.getAngaz().pobierzetat(dataetat1);
                //pobieramy z uwzglednieniem tego samego etatu
                if (pobierzetat.equals(pobierzetat1)) {
                    kalendarze.add(kal);
                    poprzedniOkres = Data.poprzedniOkres(mc, rok);
                    mc = poprzedniOkres[0];
                    rok = poprzedniOkres[1];
                }
            }
            if (kalendarze.size() == 12 || czyjestZarudnienieWtrakcieMca) {
                break;
            }
        }
        double dniroboczewmiesiacu = 0.0;
        double godzinyroboczewmiesiacu = 0.0;
        for (Dzien pa : kalendarz.getDzienList()) {
            if (pa.getTypdnia() == 0) {
                dniroboczewmiesiacu = dniroboczewmiesiacu + 1;
                godzinyroboczewmiesiacu = godzinyroboczewmiesiacu + pa.getNormagodzin();
            }
        }

        double sredniadopodstawyzmienne = 0.0;
        double sredniadopodstawy = 0.0;
        if (kalendarze.size() == 1) {
            //wyliczenie dla skladnika stalego ze zmiennymi
            sredniadopodstawy = sredniaJedenKalendarz(naliczenieskladnikawynagrodzenia, naliczenienieobecnosc, kalendarz, godzinyroboczewmiesiacu);

        } else {
            sredniadopodstawyzmienne = 0.0;
            //kontynuacja zwolnienia jest ciaglosc trzeba pobrac poprzednia zmienna
            sredniadopodstawyzmienne = sredniaKontynuacja(naliczenieskladnikawynagrodzenia, naliczenienieobecnosc, kalendarz, kalendarze, nieobecnosc);
            //idziemy dalej jak nie bylo choroby w w okresie krtotszum niz 1 mcy
            if (sredniadopodstawyzmienne == 0.0) {
                double i = 0.0;
                for (Iterator<Kalendarzmiesiac> it = kalendarze.iterator(); it.hasNext();) {
                    Kalendarzmiesiac kalendarzdosredniej = it.next();
                    boolean czyjestwiecejniepracy = false;
                    int dnirobocze = 0;
                    int dniprzepracowaneIurlop = 0;
                    double godzinyrobocze = 0;
                    double godzinyprzepracowaneIurlop = 0;
                    if (kalendarzdosredniej.getDzienList() != null) {
                        for (Dzien d : kalendarzdosredniej.getDzienList()) {
                            if (d.getTypdnia() == 0) {
                                dnirobocze++;
                                godzinyrobocze = godzinyrobocze + d.getNormagodzin();
                            }
                            if (d.getPrzepracowano() > 0) {
                                dniprzepracowaneIurlop++;
                                godzinyprzepracowaneIurlop = godzinyprzepracowaneIurlop + d.getNormagodzin();
                            } else if (d.getUrlopPlatny() > 0) {
                                dniprzepracowaneIurlop++;
                                godzinyprzepracowaneIurlop = godzinyprzepracowaneIurlop + d.getUrlopPlatny();
                            }
                        }
                        int polowaroboczych = dnirobocze / 2;
                        if (dniprzepracowaneIurlop < polowaroboczych) {
                            czyjestwiecejniepracy = true;
                        }
                    }
                    if (czyjestwiecejniepracy || kalendarzdosredniej.equals(kalendarz)) {
                        //jest wiecej nieprzepracowanego wiec robimy tylko statystycznie
                        boolean pominiety = true;
                        sredniaStatystycznaMiesiacpominiety(naliczenieskladnikawynagrodzenia, naliczenienieobecnosc, kalendarzdosredniej, kalendarz, definicjalistaplac, dnirobocze, dniprzepracowaneIurlop, godzinyrobocze, godzinyprzepracowaneIurlop, pominiety);
                        it.remove();
                    } else {
                        //jest wiecej przepracowanych miesiaca brany do wyliczania
                        Definicjalistaplac definicjabiezaca = definicjadlazasilkow != null ? definicjadlazasilkow : definicjalistaplac;
                        boolean pominiety = false;
                        sredniadopodstawyzmienne = sredniadopodstawyzmienne+sredniaMiesiacLiczony(naliczenieskladnikawynagrodzenia, naliczenienieobecnosc, kalendarzdosredniej, kalendarz, definicjalistaplac, 
                                definicjabiezaca, dnirobocze, dniprzepracowaneIurlop, godzinyrobocze, godzinyprzepracowaneIurlop, pominiety, jestoddelegowanie);
                        i++;
                    }
                }
                naliczenienieobecnosc.setSumakwotdosredniej(sredniadopodstawyzmienne);
//                naliczenienieobecnosc.setSumagodzindosredniej(godzinyfaktyczne);
                naliczenienieobecnosc.setSkladnikizmiennesrednia(sredniadopodstawyzmienne);
//                naliczenienieobecnosc.setStawkadzienna(Z.z(kwotywyplacone/dnifaktyczne));
//                naliczenienieobecnosc.setStawkagodzinowa(stawkazagodzine);
                if (i > 0) {
                    sredniadopodstawy = Z.z(sredniadopodstawy + (sredniadopodstawyzmienne / i));
                }
            }
        }
        /**
         * ************************
         */
        if (nieobecnosc.getSredniazmiennerecznie() != 0.0) {
            sredniadopodstawy = nieobecnosc.getSredniazmiennerecznie();
        }

        return sredniadopodstawy;
    }

    private static double sredniaJedenKalendarz(Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia, Naliczenienieobecnosc naliczenienieobecnosc, Kalendarzmiesiac kalendarz, double godzinyroboczewmiesiacu) {
        double sredniadopodstawy = 0.0;
        if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getGodzinowe0miesieczne1() && naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getStale0zmienne1() == false) {
            double skladnikistale = 0.0;
            double dniroboczeprzepracowanestat = 0.0;
            double godzinyobecnoscirobocze = 0.0;
            double sredniadopodstawystale = 0.0;
            for (Zmiennawynagrodzenia r : naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getZmiennawynagrodzeniaList()) {
                double dniroboczeprzepracowanezm = 0.0;
                double godzinyobecnosciroboczezm = 0.0;
                int dzienodzmienna = DataBean.dataod(r.getDataod(), kalendarz.getRok(), kalendarz.getMc());
                int dziendozmienna = DataBean.datado(r.getDatado(), kalendarz.getRok(), kalendarz.getMc());
                if (DataBean.czysiemiesci(kalendarz.getPierwszyDzien(), kalendarz.getOstatniDzien(), r.getDataod(), r.getDatado())) {
                    skladnikistale = r.getKwota();
                    for (Dzien s : kalendarz.getDzienList()) {
                        //daje norma godzin a nie z uwzglednieniem zwolnien bo przeciez rewdukcja bedzie pozniej
                        if (s.getTypdnia() == 0 && s.getNormagodzin() > 0.0 && s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                            dniroboczeprzepracowanezm++;
                            godzinyobecnosciroboczezm = godzinyobecnosciroboczezm + s.getNormagodzin();
                        }
                        if (s.getTypdnia() == 0 && s.getPrzepracowano() > 0.0 && s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                            dniroboczeprzepracowanestat++;
                        }
                    }
                }
                godzinyobecnoscirobocze = godzinyobecnoscirobocze + godzinyobecnosciroboczezm;
                double stawkadziennazm = Z.z4(skladnikistale / godzinyroboczewmiesiacu);
                sredniadopodstawystale = sredniadopodstawystale + Z.z(stawkadziennazm * godzinyobecnosciroboczezm);
                boolean skladnikstaly = true;
                Sredniadlanieobecnosci srednia = new Sredniadlanieobecnosci(kalendarz.getRok(), kalendarz.getMc(), sredniadopodstawystale, skladnikstaly, naliczenienieobecnosc, godzinyobecnosciroboczezm,
                        naliczenieskladnikawynagrodzenia.getGodzinypracyurlopu(), naliczenieskladnikawynagrodzenia.getDnipracyurlopu(), naliczenieskladnikawynagrodzenia.getGodzinynalezne(), naliczenieskladnikawynagrodzenia.getDninalezne(), 0.0);
                naliczenienieobecnosc.getSredniadlanieobecnosciList().add(srednia);
            }

            naliczenienieobecnosc.setSkladnikistale(sredniadopodstawystale);
            naliczenienieobecnosc.setSredniazailemcy(1);
            naliczenienieobecnosc.setSumakwotdosredniej(sredniadopodstawystale);
            naliczenienieobecnosc.setSumagodzindosredniej(godzinyobecnoscirobocze);
            sredniadopodstawy = sredniadopodstawystale;
        } else if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getStale0zmienne1() == true) {
            /* nie bedzie tego bo srednia ze zmiennych jest brana z poprzednich miesiecy, a tu ich nie ma*/
        }
        return sredniadopodstawy;
    }
    
    private static double sredniaKontynuacja(Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia, Naliczenienieobecnosc naliczenienieobecnosc, Kalendarzmiesiac kalendarz,List<Kalendarzmiesiac> kalendarze, Nieobecnosc nieobecnosc) {
        double sredniadopodstawy = 0.0;
        double sumakwotdosredniej = 0.0;
        double sredniadopodstawyzmienne = 0.0;
        String kontynuacja = "";
        for (Kalendarzmiesiac kalendarzdosredniej : kalendarze) {
                if (!kalendarzdosredniej.equals(kalendarz)) {
                    if (kalendarzdosredniej.czyjestchoroba()) {
                        int ilemcy = Mce.odlegloscMcy(kalendarzdosredniej.getMc(), kalendarzdosredniej.getRok(), kalendarz.getMc(), kalendarz.getRok());
                        if (ilemcy <= 1) {
                            sumakwotdosredniej = kalendarzdosredniej.pobierzSumeKwotNieobecnosc(nieobecnosc);
                            sredniadopodstawyzmienne = kalendarzdosredniej.pobierzPodstaweNieobecnosc(nieobecnosc);
                            kontynuacja = kalendarzdosredniej.getRokMc();
                        }
                    }
                }
                if (sredniadopodstawyzmienne != 0.0) {
                    double dniroboczeprzepracowanezm = 0.0;
                    double godzinyobecnosciroboczezm = 0.0;
                    int dzienodzmienna = DataBean.dataod(Data.pierwszyDzien(kalendarz.getRok(), kalendarz.getMc()), kalendarz.getRok(), kalendarz.getMc());
                    int dziendozmienna = DataBean.datado(Data.ostatniDzien(kalendarz.getRok(), kalendarz.getMc()), kalendarz.getRok(), kalendarz.getMc());
                    for (Dzien s : kalendarz.getDzienList()) {
                        //daje norma godzin a nie z uwzglednieniem zwolnien bo przeciez rewdukcja bedzie pozniej
                        if (s.getTypdnia() == 0 && s.getNormagodzin() > 0.0 && s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                            dniroboczeprzepracowanezm++;
                            godzinyobecnosciroboczezm = godzinyobecnosciroboczezm + s.getNormagodzin();
                        }
                    }
                    boolean skladnikstaly = false;
                    Sredniadlanieobecnosci srednia = new Sredniadlanieobecnosci(kalendarz.getRok(), kalendarz.getMc(), sredniadopodstawyzmienne, skladnikstaly, naliczenienieobecnosc, godzinyobecnosciroboczezm,
                            naliczenieskladnikawynagrodzenia.getGodzinypracyurlopu(), naliczenieskladnikawynagrodzenia.getDnipracyurlopu(), naliczenieskladnikawynagrodzenia.getGodzinynalezne(), naliczenieskladnikawynagrodzenia.getDninalezne(), 0.0);
                    srednia.getNaliczenienieobecnosc().setSumakwotdosredniej(sumakwotdosredniej);
                    srednia.setKontynuacja(kontynuacja);
                    naliczenienieobecnosc.getSredniadlanieobecnosciList().add(srednia);
                    sredniadopodstawy = sredniadopodstawyzmienne;
                    break;
                }
            }
        return sredniadopodstawy;
    }
    
    public static void sredniaStatystycznaMiesiacpominiety(Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia, Naliczenienieobecnosc naliczenienieobecnosc, Kalendarzmiesiac kalendarzdosredniej,
       Kalendarzmiesiac kalendarz,Definicjalistaplac definicjalistaplac, int dnirobocze, int dniprzepracowaneIurlop, double godzinyrobocze, double godzinyprzepracowaneIurlop, boolean pominiety) {
       boolean skladnikstaly = false;
        if (!kalendarzdosredniej.equals(kalendarz)) {
            double wynagrodzeniemcwyplacone = 0.0;
            if (kalendarzdosredniej.getPasek(definicjalistaplac).getNaliczenieskladnikawynagrodzeniaList() != null) {
                for (Naliczenieskladnikawynagrodzenia pa : kalendarzdosredniej.getPasek(definicjalistaplac).getNaliczenieskladnikawynagrodzeniaList()) {
                    if (pa.getSkladnikwynagrodzenia().equals(naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia())) {
                        wynagrodzeniemcwyplacone = wynagrodzeniemcwyplacone + pa.getKwotadolistyplac();
                    }
                }
            }
            if (kalendarzdosredniej.getPasek().getNaliczenienieobecnoscList() != null) {
                for (Naliczenienieobecnosc r : kalendarzdosredniej.getPasek().getNaliczenienieobecnoscList()) {
                    if (r.getNieobecnosc().getRodzajnieobecnosci().isNieskladkowy() == false) {
                        if (r.getSkladnikwynagrodzenia().equals(naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia())) {
                            wynagrodzeniemcwyplacone = wynagrodzeniemcwyplacone + r.getKwota();
                        }
                    }
                }
            }
            Sredniadlanieobecnosci srednia = new Sredniadlanieobecnosci(kalendarzdosredniej.getRok(), kalendarzdosredniej.getMc(), wynagrodzeniemcwyplacone, skladnikstaly, 
                    naliczenienieobecnosc, pominiety, godzinyprzepracowaneIurlop, dniprzepracowaneIurlop, godzinyrobocze, dnirobocze);
            naliczenienieobecnosc.getSredniadlanieobecnosciList().add(srednia);
        }
    }
    //tutaj odbywa sie waloryzacja skladnikow stalych
    public static double sredniaMiesiacLiczony(Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia, Naliczenienieobecnosc naliczenienieobecnosc, Kalendarzmiesiac kalendarzdosredniej,
       Kalendarzmiesiac kalendarz,Definicjalistaplac definicjalistaplac, Definicjalistaplac definicjabiezaca, int dnirobocze, int dniprzepracowaneIurlop, double godzinyrobocze, double godzinyprzepracowaneIurlop, boolean pominiety, boolean jestoddelegowanie) {
         double sredniadopodstawyzmienne = 0.0;
        if (!kalendarzdosredniej.equals(kalendarz)) {
            boolean skladnikstaly = false;
            double[] czyuzupelnicskladnik = kalendarzdosredniej.uzupelnienie1norma0pominiecie2();
            boolean waloryzowac = false;
            //rozpatrujemy waloryzacje tylko skladnikow stalych
            if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getGodzinowe0miesieczne1()==true) {
                waloryzowac = czyuzupelnicskladnik[2] == 1;
            } else if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getGodzinowe0miesieczne1()==false) {
                //nie pootrzeba tego bo degfault jest false
                //waloryzowac = false;
            }
            double wynagrodzeniemcwyplacone = 0.0;
            double wynagrodzeniemczwaloryzowane = 0.0;
            Pasekwynagrodzen pasek = kalendarzdosredniej.getPasek(definicjabiezaca);
            double procentOddelegowanie = 1.0;
            if (pasek.getRokwypl()!=null) {
                procentOddelegowanie = pasek.obliczproporcjeZusOddelegowani();
            }
            naliczenienieobecnosc.setProcentoddelegowanie(procentOddelegowanie);
            if (kalendarzdosredniej.getPasek(definicjabiezaca).getNaliczenieskladnikawynagrodzeniaList() != null) {
                for (Naliczenieskladnikawynagrodzenia pa : kalendarzdosredniej.getPasek(definicjabiezaca).getNaliczenieskladnikawynagrodzeniaList()) {
                    //superpłace usunac potem "Wyn. za pracę w Niemczech"
                    //if (pa.getSkladnikwynagrodzenia().equals(naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia()) || pa.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getOpispelny().equals("Wyn. za pracę w Niemczech")) {
                    //zmienilem bo dodawalo dwa razy zmienna wynagrodzenie za prace w niemczech
                    if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().isOddelegowanie()&&pa.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getOpispelny().equals("Wyn. za pracę w Niemczech")) {
                         wynagrodzeniemcwyplacone = wynagrodzeniemcwyplacone + pa.getKwotadolistyplac();
                    } else if (pa.getSkladnikwynagrodzenia().equals(naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia())) {
                        if (waloryzowac&&jestoddelegowanie==false) {
                            if (pa.getKwotaumownazacalymc()>0.0) {
                                wynagrodzeniemczwaloryzowane = wynagrodzeniemczwaloryzowane + pa.getKwotaumownazacalymc();
                            } else {
                                //zrobione ze wzgledu na starsze wersje w tym superplace
                                wynagrodzeniemczwaloryzowane = wynagrodzeniemczwaloryzowane + pa.getKwotadolistyplac() + pa.getKwotyredukujacesuma();
                            }
                        } else {
                            wynagrodzeniemcwyplacone = wynagrodzeniemcwyplacone + pa.getKwotadolistyplac();
                        }
                    }
                }
            }
            if (kalendarzdosredniej.getPasek().getNaliczenienieobecnoscList() != null) {
                for (Naliczenienieobecnosc r : kalendarzdosredniej.getPasek(definicjabiezaca).getNaliczenienieobecnoscList()) {
                    if (r.getNieobecnosc().getRodzajnieobecnosci().isNieskladkowy() == false) {
                        if (r.getSkladnikwynagrodzenia().equals(naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia())) {
                            wynagrodzeniemcwyplacone = wynagrodzeniemcwyplacone + r.getKwota();
                        }
                    }
                }
            }
            wynagrodzeniemcwyplacone = wynagrodzeniemcwyplacone * procentOddelegowanie;
            Sredniadlanieobecnosci srednia = new Sredniadlanieobecnosci(kalendarzdosredniej.getRok(), kalendarzdosredniej.getMc(), wynagrodzeniemcwyplacone, wynagrodzeniemczwaloryzowane, skladnikstaly, naliczenienieobecnosc, pominiety,
                    godzinyprzepracowaneIurlop, dniprzepracowaneIurlop, godzinyrobocze, dnirobocze);
            srednia.setWaloryzowane(waloryzowac);
            naliczenienieobecnosc.getSredniadlanieobecnosciList().add(srednia);
            double suma = wynagrodzeniemcwyplacone + wynagrodzeniemczwaloryzowane;
            sredniadopodstawyzmienne = Z.z(sredniadopodstawyzmienne + wynagrodzeniemcwyplacone + wynagrodzeniemczwaloryzowane);
        }
        return sredniadopodstawyzmienne;
    }

    private static double wyliczsredniachorobaNadgodziny(Kalendarzmiesiac kalendarz, Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia, Nieobecnosc nieobecnosc, Naliczenienieobecnosc naliczenienieobecnosc,
            Definicjalistaplac definicjalistaplac, Definicjalistaplac definicjadlazasilkow) {
        double zwrot = 0.0;
        List<Kalendarzmiesiac> kalendarze = new ArrayList<>();
        String rok = kalendarz.getRok();
        String mc = kalendarz.getMc();
        String[] poprzedniOkres = Data.poprzedniOkres(mc, rok);
        mc = poprzedniOkres[0];
        rok = poprzedniOkres[1];
        List<Kalendarzmiesiac> kalendarzmiesiacList = kalendarz.getAngaz().getKalendarzmiesiacList();
        Collections.sort(kalendarzmiesiacList, new KalendarzmiesiacRMcomparator());
        String dataetat = Data.ostatniDzien(kalendarz.getRok(), kalendarz.getMc());
        EtatPrac pobierzetat = kalendarz.getAngaz().pobierzetat(dataetat);
        for (Kalendarzmiesiac kal : kalendarzmiesiacList) {
            boolean czyjestZarudnienieWtrakcieMca = kal.czyjestZarudnienieWtrakcieMca();
            if (kal.getRok().equals(rok) && kal.getMc().equals(mc) && czyjestZarudnienieWtrakcieMca == false) {
                String dataetat1 = Data.ostatniDzien(kal.getRok(), kal.getMc());
                EtatPrac pobierzetat1 = kalendarz.getAngaz().pobierzetat(dataetat1);
                //pobieramy z uwzglednieniem tego samego etatu
                if (pobierzetat.equals(pobierzetat1)) {
                    kalendarze.add(kal);
                    poprzedniOkres = Data.poprzedniOkres(mc, rok);
                    mc = poprzedniOkres[0];
                    rok = poprzedniOkres[1];
                }
            }
            if (kalendarze.size() == 12 || czyjestZarudnienieWtrakcieMca) {
                break;
            }
        }
        double dniroboczewmiesiacu = 0.0;
        double godzinyroboczewmiesiacu = 0.0;
        for (Dzien pa : kalendarz.getDzienList()) {
            if (pa.getTypdnia() == 0) {
                dniroboczewmiesiacu = dniroboczewmiesiacu + 1;
                godzinyroboczewmiesiacu = godzinyroboczewmiesiacu + pa.getNormagodzin();
            }
        }
        double sredniadopodstawystale = 0.0;
        double sredniadopodstawyzmienne = 0.0;
        double sredniadopodstawy = 0.0;
        double godzinyobecnoscirobocze = 0.0;
        if (kalendarze.size() == 1) {
            //wyliczenie dla skladnika stalego ze zmiennymi

        } else {
            //idziemy dalej jak nie bylo choroby w w okresie krtotszum niz 1 mcy
            if (sredniadopodstawyzmienne == 0.0) {
                double i = 0.0;
                for (Iterator<Kalendarzmiesiac> it = kalendarze.iterator(); it.hasNext();) {
                    Kalendarzmiesiac kalendarzdosredniej = it.next();
                    boolean czyjestwiecejniepracy = false;
                    int dnirobocze = 0;
                    int dniprzepracowane = 0;
                    double godzinyrobocze = 0;
                    double godzinyprzepracowane = 0;
                    int dninieobecnosci = 0;
                    double godzinynieobecnosci = 0;
                    boolean zatrudnieniewtraciemiesiaca = false;
                    if (kalendarzdosredniej.getDzienList() != null) {
                        for (Dzien d : kalendarzdosredniej.getDzienList()) {
                            if (d.getTypdnia() == 0) {
                                dnirobocze++;
                                godzinyrobocze = godzinyrobocze + d.getNormagodzin();
                            }
                            if (d.getPrzepracowano() > 0) {
                                dniprzepracowane++;
                                godzinyprzepracowane = godzinyprzepracowane + d.getNormagodzin();
                            } else {
                                dninieobecnosci++;
                                godzinynieobecnosci = godzinynieobecnosci + d.getNormagodzin();
                            }
                        }
                        int polowaroboczych = dnirobocze / 2;
                        if (dniprzepracowane < polowaroboczych) {
                            czyjestwiecejniepracy = true;
                        }
                    }

                    if (!kalendarzdosredniej.equals(kalendarz)) {
                        dnirobocze = 0;
                        dniprzepracowane = 0;
                        double godzinyroboczezm = 0.0;
                        double godzinyprzepracowanezm = 0.0;
                        if (kalendarzdosredniej.getDzienList() != null) {
                            for (Dzien d : kalendarzdosredniej.getDzienList()) {
                                if (d.getTypdnia() == 0) {
                                    dnirobocze++;
                                    godzinyrobocze = godzinyrobocze + d.getNormagodzin();
                                    godzinyroboczezm = godzinyroboczezm + d.getNormagodzin();
                                }
                                if (d.getPrzepracowano() > 0) {
                                    dniprzepracowane++;
                                    godzinyprzepracowane = godzinyprzepracowane + d.getNormagodzin();
                                    godzinyprzepracowanezm = godzinyprzepracowanezm + d.getNormagodzin();
                                } else if (d.getUrlopPlatny() > 0) {
                                    dniprzepracowane++;
                                    godzinyprzepracowane = godzinyprzepracowane + d.getUrlopPlatny();
                                    godzinyprzepracowanezm = godzinyprzepracowanezm + d.getUrlopPlatny();
                                } else {
                                    dninieobecnosci++;
                                    godzinynieobecnosci = godzinynieobecnosci + d.getNormagodzin();
                                }
                            }
                            int polowaroboczych = dnirobocze / 2;
                            if (dniprzepracowane < polowaroboczych) {
                                czyjestwiecejniepracy = true;
                            }
                        }
                        boolean pominiety = false;
                        boolean skladnikstaly = false;
                        double[] czywaloryzowac = kalendarzdosredniej.uzupelnienie1norma0pominiecie2();
                        boolean waloryzowac = czywaloryzowac[2] == 1;
                        double wynagrodzeniemcwyplacone = 0.0;
                        double wynagrodzeniemczwaloryzowane = 0.0;
                        Definicjalistaplac definicjabiezaca = definicjadlazasilkow != null ? definicjadlazasilkow : definicjalistaplac;
                        Pasekwynagrodzen pasek = kalendarzdosredniej.getPasek(definicjabiezaca);
                        double procentOddelegowanie = pasek.obliczproporcjeZusOddelegowani();
                        naliczenienieobecnosc.setProcentoddelegowanie(procentOddelegowanie);
                        if (kalendarzdosredniej.getPasek(definicjabiezaca).getNaliczenieskladnikawynagrodzeniaList() != null) {
                            for (Naliczenieskladnikawynagrodzenia pa : kalendarzdosredniej.getPasek(definicjabiezaca).getNaliczenieskladnikawynagrodzeniaList()) {
                                //superpłace usunac potem "Wyn. za pracę w Niemczech"
                                if (pa.getSkladnikwynagrodzenia().equals(naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia()) || pa.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getOpispelny().equals("Wyn. za pracę w Niemczech")) {
                                    if (waloryzowac) {
                                        wynagrodzeniemczwaloryzowane = wynagrodzeniemczwaloryzowane + pa.getKwotadolistyplac() + pa.getKwotyredukujacesuma();
                                    } else {
                                        wynagrodzeniemcwyplacone = wynagrodzeniemcwyplacone + pa.getKwotadolistyplac();
                                    }
                                }
                            }
                        }
                        if (kalendarzdosredniej.getPasek().getNaliczenienieobecnoscList() != null) {
                            for (Naliczenienieobecnosc r : kalendarzdosredniej.getPasek(definicjabiezaca).getNaliczenienieobecnoscList()) {
                                if (r.getNieobecnosc().getRodzajnieobecnosci().isNieskladkowy() == false) {
                                    if (r.getSkladnikwynagrodzenia().equals(naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia())) {
                                        wynagrodzeniemcwyplacone = wynagrodzeniemcwyplacone + r.getKwota();
                                    }
                                }
                            }
                        }
                        wynagrodzeniemcwyplacone = wynagrodzeniemcwyplacone * procentOddelegowanie;
                        Sredniadlanieobecnosci srednia = new Sredniadlanieobecnosci(kalendarzdosredniej.getRok(), kalendarzdosredniej.getMc(), wynagrodzeniemcwyplacone, wynagrodzeniemczwaloryzowane, skladnikstaly, naliczenienieobecnosc, pominiety,
                                godzinyprzepracowanezm, dniprzepracowane, godzinyroboczezm, dnirobocze);
                        srednia.setWaloryzowane(waloryzowac);
                        naliczenienieobecnosc.getSredniadlanieobecnosciList().add(srednia);
                        double suma = wynagrodzeniemcwyplacone + wynagrodzeniemczwaloryzowane;
                        sredniadopodstawyzmienne = Z.z(sredniadopodstawyzmienne + wynagrodzeniemcwyplacone + wynagrodzeniemczwaloryzowane);
                        i++;
                    }
                }
                naliczenienieobecnosc.setSumakwotdosredniej(sredniadopodstawyzmienne);
//                naliczenienieobecnosc.setSumagodzindosredniej(godzinyfaktyczne);
                naliczenienieobecnosc.setSkladnikizmiennesrednia(sredniadopodstawyzmienne);
//                naliczenienieobecnosc.setStawkadzienna(Z.z(kwotywyplacone/dnifaktyczne));
//                naliczenienieobecnosc.setStawkagodzinowa(stawkazagodzine);
                if (i > 0) {
                    sredniadopodstawy = Z.z(sredniadopodstawy + (sredniadopodstawyzmienne / i));
                }
            }
        }
        /**
         * ************************
         */
        if (nieobecnosc.getSredniazmiennerecznie() != 0.0) {
            sredniadopodstawy = nieobecnosc.getSredniazmiennerecznie();
        }

        return sredniadopodstawy;
    }

    static void naliczskladnikiwynagrodzeniazaUrlop(Kalendarzmiesiac kalendarz, Nieobecnosc nieobecnosc, Pasekwynagrodzen pasekwynagrodzen, List<Kalendarzmiesiac> kalendarzList) {
        double liczbadniobowiazku = 0.0;
        double liczbadniurlopu = 0.0;
        double liczbagodzinurlopu = 0.0;
        double liczbagodzinobowiazku = 0.0;
        String pierwszydzienmiesiaca = Data.pierwszyDzienKalendarz(kalendarz);
        String ostatnidzienmiesiaca = Data.ostatniDzienKalendarz(kalendarz);
        String dataod = Data.czyjestpo(pierwszydzienmiesiaca, nieobecnosc.getDataod()) ? nieobecnosc.getDataod() : pierwszydzienmiesiaca;
        String datado = Data.czyjestprzed(ostatnidzienmiesiaca, nieobecnosc.getDatado()) ? nieobecnosc.getDatado() : ostatnidzienmiesiaca;
        int dzienod = Data.getDzienI(dataod);
        int dziendo = Data.getDzienI(datado);
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia() == 0 && (p.getNormagodzin() != 0.0 || p.getKod().equals("D"))) {
                liczbadniobowiazku = liczbadniobowiazku + 1;
                liczbagodzinobowiazku = liczbagodzinobowiazku + p.getNormagodzin();
            }
            if (p.getTypdnia() == 0 && p.getNrdnia() >= dzienod && p.getNrdnia() <= dziendo) {
                liczbadniurlopu = liczbadniurlopu + 1;
                liczbagodzinurlopu = liczbagodzinurlopu + p.getUrlopPlatny() + p.getOpiekadziecko();
            }
        }
        for (Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia : pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList()) {
            if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getGodzinowe0miesieczne1() && naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getStale0zmienne1() == false
                    && naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isSredniaurlopowakraj() == true) {
                //bierzemy globalne nalezne godziny
                liczbagodzinobowiazku = naliczenieskladnikawynagrodzenia.getGodzinynalezne();
                double skladnikistale = 0.0;
                Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
                naliczenienieobecnosc.setDataod(dataod);
                naliczenienieobecnosc.setDatado(datado);
                naliczenienieobecnosc.setNieobecnosc(nieobecnosc);
                Skladnikwynagrodzenia skladnikwynagrodzenia = naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia();
                naliczenienieobecnosc.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                naliczenienieobecnosc.setJakiskladnikredukowalny(naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getUwagi());
                naliczenienieobecnosc.setLiczbadniobowiazku(liczbadniobowiazku);
                naliczenienieobecnosc.setLiczbadniurlopu(liczbadniurlopu);
                naliczenienieobecnosc.setLiczbagodzinobowiazku(liczbagodzinobowiazku);
                naliczenienieobecnosc.setLiczbagodzinurlopu(liczbagodzinurlopu);
                double sredniamiesieczna = wyliczsredniagodzinowaStale(kalendarz, naliczenieskladnikawynagrodzenia, liczbagodzinurlopu, liczbagodzinobowiazku, naliczenienieobecnosc);
                naliczenienieobecnosc.setSkladnikistale(sredniamiesieczna);
                double stawkadzienna = naliczenieskladnikawynagrodzenia.getStawkadzienna();
                double stawkagodzinowa = naliczenieskladnikawynagrodzenia.getStawkagodzinowa();
                //zliwkidowalem zaokragklenia bo dawid mial roznice grosza 2023-02-01
                naliczenienieobecnosc.setStawkagodzinowa(stawkagodzinowa);
                naliczenienieobecnosc.setStawkadzienna(stawkadzienna);
                double dowyplatyzaczasnieobecnosci = Z.z6(stawkagodzinowa * liczbagodzinurlopu);
                naliczenienieobecnosc.setKwota(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setKwotazus(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setKwotaredukcji(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setPasekwynagrodzen(pasekwynagrodzen);
                pasekwynagrodzen.getNaliczenienieobecnoscList().add(naliczenienieobecnosc);
            }
        }
        List<Skladnikwynagrodzenia> listaskladnikowzmiennych = kalendarz.getAngaz().getSkladnikwynagrodzeniaList();
        for (Skladnikwynagrodzenia skladnikwynagrodzenia : listaskladnikowzmiennych) {
            if (skladnikwynagrodzenia.getRodzajwynagrodzenia().getStale0zmienne1() == true && skladnikwynagrodzenia.getRodzajwynagrodzenia().isSredniaurlopowakraj() == true) {
                Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
                naliczenienieobecnosc.setDataod(dataod);
                naliczenienieobecnosc.setDatado(datado);
                naliczenienieobecnosc.setNieobecnosc(nieobecnosc);
                naliczenienieobecnosc.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                naliczenienieobecnosc.setJakiskladnikredukowalny(skladnikwynagrodzenia.getUwagi());
                naliczenienieobecnosc.setLiczbadniobowiazku(liczbadniobowiazku);
                naliczenienieobecnosc.setLiczbadniurlopu(liczbadniurlopu);
                naliczenienieobecnosc.setLiczbagodzinobowiazku(liczbagodzinobowiazku);
                naliczenienieobecnosc.setLiczbagodzinurlopu(liczbagodzinurlopu);
                double dowyplatyzaczasnieobecnosci = wyliczsredniagodzinowaZmienne(kalendarz, skladnikwynagrodzenia, liczbagodzinurlopu, liczbagodzinobowiazku, naliczenienieobecnosc, kalendarzList);
                naliczenienieobecnosc.setSkladnikistale(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setKwota(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setKwotazus(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setKwotaredukcji(0.0);
                naliczenienieobecnosc.setPasekwynagrodzen(pasekwynagrodzen);
                pasekwynagrodzen.getNaliczenienieobecnoscList().add(naliczenienieobecnosc);
            }
            //p.setKwotadolistyplac(p.getKwotadolistyplac()-dowyplatyzaczasnieobecnosci);
            //p.setKwotyredukujacesuma(p.getKwotaumownazacalymc()-p.getKwotadolistyplac());
        }
    }

    static void naliczskladnikiwynagrodzeniazaUrlopOddelegowanie(Kalendarzmiesiac kalendarz, Nieobecnosc nieobecnosc, Pasekwynagrodzen pasekwynagrodzen, double kurs) {
        double liczbadniobowiazku = 0.0;
        double liczbadniurlopu = 0.0;
        double liczbagodzinurlopu = 0.0;
        double liczbagodzinobowiazku = 0.0;
        String pierwszydzienmiesiaca = Data.pierwszyDzienKalendarz(kalendarz);
        String ostatnidzienmiesiaca = Data.ostatniDzienKalendarz(kalendarz);
        String dataod = Data.czyjestpo(pierwszydzienmiesiaca, nieobecnosc.getDataod()) ? nieobecnosc.getDataod() : pierwszydzienmiesiaca;
        String datado = Data.czyjestprzed(ostatnidzienmiesiaca, nieobecnosc.getDatado()) ? nieobecnosc.getDatado() : ostatnidzienmiesiaca;
        int dzienod = Data.getDzienI(dataod);
        int dziendo = Data.getDzienI(datado);
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia() == 0 && (p.getNormagodzin() != 0.0 || p.getKod().equals("D"))) {
                if (p.getPrzepracowano() > 0.0 || p.getKod().equals("UD") || p.getKod().equals("D")) {
                    liczbadniobowiazku = liczbadniobowiazku + 1;
                    liczbagodzinobowiazku = liczbagodzinobowiazku + p.getNormagodzin();
                }
            }
            if (p.getTypdnia() == 0 && p.getNrdnia() >= dzienod && p.getNrdnia() <= dziendo) {
                liczbadniurlopu = liczbadniurlopu + 1;
                liczbagodzinurlopu = liczbagodzinurlopu + p.getUrlopPlatny();
            }
        }
        List<Skladnikwynagrodzenia> listaskladnikowzmiennych = kalendarz.getAngaz().getSkladnikwynagrodzeniaList();
        for (Skladnikwynagrodzenia skladnikwynagrodzenia : listaskladnikowzmiennych) {
            if (skladnikwynagrodzenia.getRodzajwynagrodzenia().getStale0zmienne1() == true && skladnikwynagrodzenia.getRodzajwynagrodzenia().isSredniaurlopowaoddelegowanie() == true && skladnikwynagrodzenia.isOddelegowanie()) {
                Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
                naliczenienieobecnosc.setDataod(dataod);
                naliczenienieobecnosc.setDatado(datado);
                naliczenienieobecnosc.setNieobecnosc(nieobecnosc);
                naliczenienieobecnosc.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                //double dowyplatyzaczasnieobecnosci = wyliczsredniagodzinowaZmienneOddelegowanie(kalendarz, skladnikwynagrodzenia, liczbagodzinurlopu, liczbagodzinobowiazku, naliczenienieobecnosc, kalendarzList);
                double dowyplatyzaczasnieobecnosci = wyliczsredniagodzinowaZmienneOddelegowanieNowe(kalendarz, skladnikwynagrodzenia, liczbagodzinurlopu, liczbagodzinobowiazku, naliczenienieobecnosc, kurs);
                naliczenienieobecnosc.setJakiskladnikredukowalny(skladnikwynagrodzenia.getUwagi());
                naliczenienieobecnosc.setSkladnikistale(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setLiczbadniobowiazku(liczbadniobowiazku);
                naliczenienieobecnosc.setLiczbadniurlopu(liczbadniurlopu);
                naliczenienieobecnosc.setLiczbagodzinobowiazku(liczbagodzinobowiazku);
                naliczenienieobecnosc.setLiczbagodzinurlopu(liczbagodzinurlopu);
                naliczenienieobecnosc.setKwota(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setKwotazus(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setKwotaredukcji(0.0);
                naliczenienieobecnosc.setPasekwynagrodzen(pasekwynagrodzen);
                pasekwynagrodzen.getNaliczenienieobecnoscList().add(naliczenienieobecnosc);
            }
            //p.setKwotadolistyplac(p.getKwotadolistyplac()-dowyplatyzaczasnieobecnosci);
            //p.setKwotyredukujacesuma(p.getKwotaumownazacalymc()-p.getKwotadolistyplac());
        }
    }

    public static List<Naliczenieskladnikawynagrodzenia> pobierzpaski(String rok, String mc, Skladnikwynagrodzenia s, List<Kalendarzmiesiac> kalendarzList) {
        List<Naliczenieskladnikawynagrodzenia> zwrot = new ArrayList<>();
        Collections.sort(kalendarzList, new KalendarzmiesiacLastcomparator());
        int ilemamy = 0;
        for (Kalendarzmiesiac r : kalendarzList) {
            if (r.getRokI() <= Integer.parseInt(rok)) {
                if (Data.czyjestpomcnaprawdepo(r.getMc(), r.getRok(), mc, rok)) {
                    Naliczenieskladnikawynagrodzenia naliczonewynagrodzenie = getNaliczonewynagrodzenie(r, s);
                    if (naliczonewynagrodzenie != null) {
                        zwrot.add(naliczonewynagrodzenie);
                    }
                    ilemamy++;
                }
                if (ilemamy == 3) {
                    break;
                }
            }
        }

        return zwrot;
    }

    public static Naliczenieskladnikawynagrodzenia getNaliczonewynagrodzenie(Kalendarzmiesiac kalendarz, Skladnikwynagrodzenia s) {
        Naliczenieskladnikawynagrodzenia zwrot = null;
        List<Naliczenieskladnikawynagrodzenia> lista = skladnikiwynagrodzenialista(kalendarz);
        if (lista != null) {
            for (Naliczenieskladnikawynagrodzenia naliczenie : lista) {
                if (naliczenie.getSkladnikwynagrodzenia().equals(s)) {
                    zwrot = naliczenie;
                    break;
                }
            }
        }
        return zwrot;
    }

    public static List<Naliczenieskladnikawynagrodzenia> skladnikiwynagrodzenialista(Kalendarzmiesiac kalendarz) {
        List<Naliczenieskladnikawynagrodzenia> zwrot = new ArrayList<>();
        List<Pasekwynagrodzen> pasekwynagrodzenList = kalendarz.getPasekwynagrodzenList();
        if (kalendarz.getPasekwynagrodzenList() != null && !kalendarz.getPasekwynagrodzenList().isEmpty()) {
            for (Pasekwynagrodzen p : kalendarz.getPasekwynagrodzenList()) {
                if (p.getNaliczenieskladnikawynagrodzeniaList() != null) {
                    zwrot.addAll(p.getNaliczenieskladnikawynagrodzeniaList());
                }
            }
        }
        return zwrot;
    }

    public static List<Naliczenieskladnikawynagrodzenia> skladnikiwynagrodzeniaWybranalista(Kalendarzmiesiac kalendarz, Definicjalistaplac definicjalistaplac) {
        List<Naliczenieskladnikawynagrodzenia> zwrot = new ArrayList<>();
        List<Pasekwynagrodzen> pasekwynagrodzenList = kalendarz.getPasekwynagrodzenList();
        if (kalendarz.getPasekwynagrodzenList() != null && !kalendarz.getPasekwynagrodzenList().isEmpty()) {
            for (Pasekwynagrodzen p : kalendarz.getPasekwynagrodzenList()) {
                if (p.getNaliczenieskladnikawynagrodzeniaList() != null && p.getDefinicjalistaplac().equals(definicjalistaplac)) {
                    zwrot.addAll(p.getNaliczenieskladnikawynagrodzeniaList());
                }
            }
        }
        return zwrot;
    }

    private static double wyliczsredniagodzinowaStale(Kalendarzmiesiac kalendarz, Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia, double liczbagodzinieobecnosci, double liczbagodzinobowiazku, Naliczenienieobecnosc naliczenienieobecnosc) {
        double sredniadopodstawy = 0.0;
        //wyliczenie dla skladnika stalego ze zmiennymi
        if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getGodzinowe0miesieczne1() && naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getStale0zmienne1() == false) {
            //niemoge brac tego co w umowie bo bedzie za wiele, musze zredukowac do czasu przepracowanego po uwzgledneinu nieobecnosci wynikajace z choroby wazne!!!!!!!!!!!!!!
            //sredniadopodstawy = naliczenieskladnikawynagrodzenia.getKwotaumownazacalymc()-naliczenieskladnikawynagrodzenia.getKwotyredukujacesuma();
            //usuwam redukcje za chorobe bo robie to w momencie naliczania skladnika, w ten sposob skoryguje za bardzo
            sredniadopodstawy = naliczenieskladnikawynagrodzenia.getKwotaumownazacalymc();
            boolean skladnikstaly = true;
            Sredniadlanieobecnosci srednia = new Sredniadlanieobecnosci(kalendarz.getRok(), kalendarz.getMc(), sredniadopodstawy, skladnikstaly,
                    naliczenienieobecnosc, liczbagodzinieobecnosci, naliczenieskladnikawynagrodzenia.getGodzinyfaktyczne(), naliczenieskladnikawynagrodzenia.getDnifaktyczne(),
                    naliczenieskladnikawynagrodzenia.getGodzinynalezne(), naliczenieskladnikawynagrodzenia.getDninalezne(), 0.0);
//             Sredniadlanieobecnosci srednia = new Sredniadlanieobecnosci(kalendarz.getRok(), kalendarz.getMc(), sredniadopodstawy, skladnikstaly, naliczenienieobecnosc, liczbagodzinieobecnosci, pa.getGodzinyfaktyczne(), pa.getDnifaktyczne(), pa.getGodzinynalezne(), pa.getDninalezne());
            naliczenienieobecnosc.getSredniadlanieobecnosciList().add(srednia);
            naliczenienieobecnosc.setSkladnikistale(sredniadopodstawy);
            naliczenienieobecnosc.setSredniazailemcy(1);
            naliczenienieobecnosc.setSumakwotdosredniej(sredniadopodstawy);
            //bierzemy godziny nalezne z globalnego
            naliczenienieobecnosc.setSumagodzindosredniej(naliczenieskladnikawynagrodzenia.getGodzinynalezne());
        }
        return sredniadopodstawy;
    }

    private static double wyliczsredniagodzinowaZmienne(Kalendarzmiesiac kalendarz, Skladnikwynagrodzenia skladnikwynagrodzenia, double liczbagodzinieobecnosci,
            double liczbagodzinobowiazku, Naliczenienieobecnosc naliczenienieobecnosc, List<Kalendarzmiesiac> kalendarzList) {
        double sredniadopodstawy = 0.0;
        if (skladnikwynagrodzenia.getRodzajwynagrodzenia().getStale0zmienne1() == true) {
            String rok = kalendarz.getRok();
            String mc = kalendarz.getMc();
            String dzien = kalendarz.getAngaz().getFirma().getDzienlp();
            if (dzien != null) {
                String[] popokres = Data.poprzedniOkres(mc, rok);
                rok = popokres[1];
                mc = popokres[0];
            }
            List<Naliczenieskladnikawynagrodzenia> naliczonyskladnikdosredniej = pobierzpaski(rok, mc, skladnikwynagrodzenia, kalendarzList);
            double godzinynominalnepracownik = 0.0;
            double dnifaktyczne = 0.0;
            double kwotywyplacone = 0.0;
            double stawkazagodzine = 0.0;
            int liczba = 0;
            for (Naliczenieskladnikawynagrodzenia pa : naliczonyskladnikdosredniej) {
                if (pa.getKwotadolistyplac() > 0.0 && pa.getGodzinyfaktyczne() > 0.0) {
                    godzinynominalnepracownik = godzinynominalnepracownik + pa.getGodzinynalezne();
                    dnifaktyczne = dnifaktyczne + pa.getDninalezne();
                    kwotywyplacone = kwotywyplacone + pa.getKwotadolistyplac();
                    liczba++;
                    boolean skladnikstaly = false;
                    //                //tylko dla Toczek po inporcvie usunac!!!!!!!!!!!!!!!!!!!!!
                    //                if (pa.getGodzinyfaktyczne()==0.0) {
                    //                    pa.setGodzinyfaktyczne(liczbagodzinobowiazku);
                    //                }
                    double stawkazagodzinezm = Z.z(pa.getKwotadolistyplac() / pa.getGodzinyfaktyczne());
                    double sredniadopodstazm = Z.z(stawkazagodzinezm * liczbagodzinieobecnosci);
                    Sredniadlanieobecnosci srednia = new Sredniadlanieobecnosci(pa.getPasekwynagrodzen().getRok(), pa.getPasekwynagrodzen().getMc(), pa.getKwotadolistyplac(), skladnikstaly, naliczenienieobecnosc, liczbagodzinieobecnosci, pa.getGodzinyfaktyczne(), pa.getDnifaktyczne(), pa.getGodzinynalezne(), pa.getDninalezne(), stawkazagodzinezm);
                    naliczenienieobecnosc.getSredniadlanieobecnosciList().add(srednia);
                    naliczenienieobecnosc.setSredniazailemcy(liczba);
                    if (liczba > 3) {
                        break;
                    }
                }
            }
            if (godzinynominalnepracownik != 0.0 && dnifaktyczne != 0.0) {
                stawkazagodzine = Z.z(kwotywyplacone / godzinynominalnepracownik);
                sredniadopodstawy = sredniadopodstawy + Z.z(stawkazagodzine * liczbagodzinieobecnosci);
                naliczenienieobecnosc.setSumakwotdosredniej(kwotywyplacone);
                naliczenienieobecnosc.setSumagodzindosredniej(godzinynominalnepracownik);
                naliczenienieobecnosc.setSkladnikizmiennesrednia(sredniadopodstawy);
                naliczenienieobecnosc.setStawkadzienna(Z.z(kwotywyplacone / dnifaktyczne));
                naliczenienieobecnosc.setStawkagodzinowa(stawkazagodzine);
            }
        }
        return sredniadopodstawy;
    }

    private static double wyliczsredniagodzinowaZmienneOddelegowanie(Kalendarzmiesiac kalendarz, Skladnikwynagrodzenia skladnikwynagrodzenia, double liczbagodzinieobecnosci, double liczbagodzinobowiazku, Naliczenienieobecnosc naliczenienieobecnosc, List<Kalendarzmiesiac> kalendarzList) {
        double sredniadopodstawy = 0.0;
        if (skladnikwynagrodzenia.getRodzajwynagrodzenia().getStale0zmienne1() == true && skladnikwynagrodzenia.isOddelegowanie()) {
            List<Naliczenieskladnikawynagrodzenia> naliczonyskladnikdosredniej = pobierzpaski(kalendarz.getRok(), kalendarz.getMc(), skladnikwynagrodzenia, kalendarzList);
            double godzinyfaktyczne = 0.0;
            double dnifaktyczne = 0.0;
            double kwotywyplacone = 0.0;
            double stawkazagodzine = 0.0;
            int liczba = 0;
            for (Naliczenieskladnikawynagrodzenia pa : naliczonyskladnikdosredniej) {
                godzinyfaktyczne = godzinyfaktyczne + pa.getGodzinyfaktyczne();
                dnifaktyczne = dnifaktyczne + pa.getDnifaktyczne();
                kwotywyplacone = kwotywyplacone + pa.getKwotadolistyplac();
                liczba++;
                boolean skladnikstaly = false;
                double stawkazagodzinezm = Z.z(pa.getKwotadolistyplac() / pa.getGodzinyfaktyczne());
                double sredniadopodstazm = Z.z(stawkazagodzinezm * liczbagodzinieobecnosci);
                Sredniadlanieobecnosci srednia = new Sredniadlanieobecnosci(pa.getPasekwynagrodzen().getRok(), pa.getPasekwynagrodzen().getMc(), pa.getKwotadolistyplac(), skladnikstaly, naliczenienieobecnosc, liczbagodzinieobecnosci, pa.getGodzinyfaktyczne(), pa.getDnifaktyczne(), pa.getGodzinynalezne(), pa.getDninalezne(), stawkazagodzinezm);
                naliczenienieobecnosc.getSredniadlanieobecnosciList().add(srednia);
                naliczenienieobecnosc.setSredniazailemcy(liczba);
                if (liczba > 3) {
                    break;
                }
            }
            if (godzinyfaktyczne != 0.0 && dnifaktyczne != 0.0) {
                stawkazagodzine = Z.z(kwotywyplacone / godzinyfaktyczne);
                sredniadopodstawy = sredniadopodstawy + Z.z(stawkazagodzine * liczbagodzinieobecnosci);
                naliczenienieobecnosc.setSumakwotdosredniej(kwotywyplacone);
                naliczenienieobecnosc.setSumagodzindosredniej(godzinyfaktyczne);
                naliczenienieobecnosc.setSkladnikizmiennesrednia(sredniadopodstawy);
                naliczenienieobecnosc.setStawkadzienna(Z.z(kwotywyplacone / dnifaktyczne));
                naliczenienieobecnosc.setStawkagodzinowa(stawkazagodzine);
            }
        }
        return sredniadopodstawy;
    }

    private static double wyliczsredniagodzinowaZmienneOddelegowanieNowe(Kalendarzmiesiac kalendarz, Skladnikwynagrodzenia skladnikwynagrodzenia, double liczbagodzinieobecnosci, double liczbagodzinobowiazku,
            Naliczenienieobecnosc naliczenienieobecnosc, double kurs) {
        double sredniadopodstawy = 0.0;
        double sredniadopodstawywaluta = 0.0;
        double normadzienna = 0.0;
        if (skladnikwynagrodzenia.getRodzajwynagrodzenia().getStale0zmienne1() == true && skladnikwynagrodzenia.isOddelegowanie()) {
            if (skladnikwynagrodzenia.getRodzajwynagrodzenia().isSredniaurlopowaoddelegowanie() && skladnikwynagrodzenia.isOddelegowanie()) {
                for (Zmiennawynagrodzenia r : skladnikwynagrodzenia.getZmiennawynagrodzeniaList()) {
                    Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
                    naliczenieskladnikawynagrodzenia.setWaluta(r.getWaluta());
                    double stawkagodzinowawaluta = 0.0;
                    int dzienodzmienna = DataBean.dataod(r.getDataod(), kalendarz.getRok(), kalendarz.getMc());
                    int dziendozmienna = DataBean.datado(r.getDatado(), kalendarz.getRok(), kalendarz.getMc());
                    if (DataBean.czysiemiesci(kalendarz.getPierwszyDzien(), kalendarz.getOstatniDzien(), r.getDataod(), r.getDatado())) {
                        for (Dzien s : kalendarz.getDzienList()) {
                            //daje norma godzin a nie z uwzglednieniem zwolnien bo przeciez rewdukcja bedzie pozniej
                            //zmienilem zdanie. redukcja bedzie statystyczna
                            //tu musza byc faktycznie dni
                            if (s.getKod() != null && s.getKod().equals("Z")) {
                                if (s.getTypdnia() == 0 && s.getNormagodzin() > 0.0 && s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                                    normadzienna = s.getNormagodzin();
                                }
                            }
                        }
                        double stawkagodzinowawalutazm = r.getKwota();
                        double stawkagodzinowazm = stawkagodzinowawalutazm * kurs;
                        stawkagodzinowawaluta = stawkagodzinowawaluta + stawkagodzinowawalutazm;
                        double stawkadziennazm = Z.z(stawkagodzinowawalutazm * normadzienna);
                        sredniadopodstawy = sredniadopodstawy + Z.z(stawkagodzinowazm * liczbagodzinieobecnosci);
                        sredniadopodstawywaluta = sredniadopodstawywaluta + Z.z(stawkagodzinowawalutazm * liczbagodzinieobecnosci);
                        //tu wylicza wynagrodzenie za faktycznie przepracowany czas i date obowiazywania zmiennej
                        naliczenienieobecnosc.setSumakwotdosredniej(0.0);
                        naliczenienieobecnosc.setSumagodzindosredniej(0.0);
                        naliczenienieobecnosc.setSkladnikizmiennesrednia(0.0);
                        naliczenienieobecnosc.setStawkadzienna(stawkadziennazm);
                        naliczenienieobecnosc.setStawkagodzinowa(stawkagodzinowawalutazm);
                        naliczenienieobecnosc.setWaluta(r.getWaluta());
                        naliczenienieobecnosc.setKwotawaluta(sredniadopodstawywaluta);
                    }
                }
            }
        }
        return sredniadopodstawy;
    }

    static void naliczskladnikiwynagrodzeniazaOkresnieprzepracowany(Kalendarzmiesiac kalendarz, Nieobecnosc nieobecnosc, Pasekwynagrodzen pasekwynagrodzen, String kod) {
        double dniroboczewmiesiacu = 0.0;
        String pierwszydzienmiesiaca = Data.pierwszyDzienKalendarz(kalendarz);
        String ostatnidzienmiesiaca = Data.ostatniDzienKalendarz(kalendarz);
        String dataod = Data.czyjestpo(pierwszydzienmiesiaca, nieobecnosc.getDataod()) ? nieobecnosc.getDataod() : pierwszydzienmiesiaca;
        String datado = Data.czyjestprzed(ostatnidzienmiesiaca, nieobecnosc.getDatado()) ? nieobecnosc.getDatado() : ostatnidzienmiesiaca;
        int dzienod = Data.getDzienI(dataod);
        int dziendo = Data.getDzienI(datado);
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia() == 0) {
                dniroboczewmiesiacu++;
            }
        }
        double godzinyroboczewmiesiacu = dniroboczewmiesiacu * 8.0;
//        Rozkład czasu pracy pracownika nie ma znaczenia dla ustalenia wysokości przysługującego mu wynagrodzenia za pracę. 
//        Istotna jest liczba godzin do przepracowania, która powinna być ustalona przy zastosowaniu art. 130 K.p. 
//        W takim przypadku należy więc przyjmować nominalną, a nie rozkładową liczbę godzin pracy.
//        Wg superplac dotyczy to zaczecia pracy w miesiacu jak i bezplatnego
        for (Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia : pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList()) {
            if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany() && naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getStale0zmienne1() == false) {
                double dninieobecnoscirobocze = 0.0;
                double godzinynieobecnoscirobocze = 0.0;
                Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
                naliczenienieobecnosc.setDataod(dataod);
                naliczenienieobecnosc.setDatado(datado);
                Skladnikwynagrodzenia skladnikwynagrodzenia = naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia();
                naliczenienieobecnosc.setNieobecnosc(nieobecnosc);
                naliczenienieobecnosc.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                double skladnikistale = 0.0;
                int dzienodzmienna = DataBean.dataod(naliczenieskladnikawynagrodzenia.getDataod(), kalendarz.getRok(), kalendarz.getMc());
                int dziendozmienna = DataBean.datado(naliczenieskladnikawynagrodzenia.getDatado(), kalendarz.getRok(), kalendarz.getMc());
                skladnikistale = naliczenieskladnikawynagrodzenia.getKwotaumownazacalymc();
//                if (kod.equals("D")) {
//                    for (Dzien s : kalendarz.getDzienList()) {
//                        if (s.getTypdnia()==0 && s.getKod()!=null && s.getKod().equals("D")) {
//                            if (s.getNrdnia()>=dzienod &&s.getNrdnia()<=dziendo) {
//                                dninieobecnoscirobocze = dninieobecnoscirobocze+1;
//                                godzinynieobecnoscirobocze = godzinynieobecnoscirobocze+s.getNormagodzin();
//                            }
//                        }
//                    }
//                } else {
                for (Dzien s : kalendarz.getDzienList()) {
                    if (s.getTypdnia() == 0 && s.getNrdnia() >= dzienod && s.getNrdnia() <= dziendo) {
                        if (s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                            dninieobecnoscirobocze = dninieobecnoscirobocze + 1;
                            godzinynieobecnoscirobocze = godzinynieobecnoscirobocze + s.getNormagodzin();
                        }
                    }
                }
//                }
                naliczenienieobecnosc.setLiczbadniurlopu(dninieobecnoscirobocze);
                naliczenienieobecnosc.setLiczbagodzinurlopu(godzinynieobecnoscirobocze);
                naliczenienieobecnosc.setSkladnikistale(skladnikistale);
                naliczenienieobecnosc.setLiczbagodzinobowiazku(godzinyroboczewmiesiacu);
                naliczenienieobecnosc.setLiczbagodzinurlopu(godzinynieobecnoscirobocze);

                double stawkagodzinowa = Z.z6(naliczenieskladnikawynagrodzenia.getStawkagodzinowa());
                double stawkadzienna = Z.z4(naliczenieskladnikawynagrodzenia.getStawkadzienna());
                naliczenienieobecnosc.setStawkagodzinowa(stawkagodzinowa);
                naliczenienieobecnosc.setStawkadzienna(stawkadzienna);
                double dowyplatyzaczasnieobecnosci = Z.z(stawkagodzinowa * godzinynieobecnoscirobocze);
                naliczenienieobecnosc.setKwota(0.0);
                naliczenienieobecnosc.setKwotazus(0.0);
                if (kod.equals("D")) {
                    naliczenienieobecnosc.setKwotaredukcji(0.0);
                    naliczenienieobecnosc.setKwotastatystyczna(Z.z(dowyplatyzaczasnieobecnosci));

                } else {
                    naliczenienieobecnosc.setKwotaredukcji(Z.z(dowyplatyzaczasnieobecnosci));
                }
                naliczenienieobecnosc.setJakiskladnikredukowalny(naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getUwagi());
                naliczenienieobecnosc.setPasekwynagrodzen(pasekwynagrodzen);
                pasekwynagrodzen.getNaliczenienieobecnoscList().add(naliczenienieobecnosc);
            }
        }
    }

    static void nalicznadgodziny50(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen) {
        double godzinyrobocze = 0.0;
        double nadliczbowe = 0.0;
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia() == 0) {
                godzinyrobocze = godzinyrobocze + p.getNormagodzin();
            }
            if (p.getTypdnia() == 0 && p.getPiecdziesiatki() > 0.0) {
                nadliczbowe = nadliczbowe + p.getPiecdziesiatki();
            }
        }
        Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
        Skladnikwynagrodzenia wynagrodzeniezasadnicze = SkladnikwynagrodzeniaBean.createWynagrodzenie();
        Skladnikwynagrodzenia skladniknadgodziny50 = SkladnikwynagrodzeniaBean.createNadgodziny50();
        naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(skladniknadgodziny50);
        double skladnik = wynagrodzeniezasadnicze.getZmiennawynagrodzeniaList().get(0).getKwota();
        double stawkagodznowanormalna = skladnik / godzinyrobocze * 0.5;
        naliczenieskladnikawynagrodzenia.setKwotaumownazacalymc(Z.z(stawkagodznowanormalna * nadliczbowe));
        naliczenieskladnikawynagrodzenia.setKwotadolistyplac(Z.z(stawkagodznowanormalna * nadliczbowe));
        pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
    }

    static void nalicznadgodzinyOddelegowanieDB(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen) {
        double godzinyrobocze = 0.0;
        double dnirobocze = 0.0;
        double nadliczbowe = 0.0;
        String dataod = kalendarz.getPierwszyDzien();
        String datado = kalendarz.getOstatniDzien();
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia() == 0) {
                dnirobocze = dnirobocze + 1;
                godzinyrobocze = godzinyrobocze + p.getNormagodzin();
            }
        }
        nadliczbowe = kalendarz.getDelegowanienadgodziny();
        double nadgodziny = kalendarz.getDodatekzanadgodzinymc();
        if (nadliczbowe > 0.0) {
            Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
            Skladnikwynagrodzenia skladniknadgodzinydelegowanie = pobierzskladnikWksserial(kalendarz, 1071);
            if (skladniknadgodzinydelegowanie != null) {
                naliczenieskladnikawynagrodzenia.setDataod(dataod);
                naliczenieskladnikawynagrodzenia.setDatado(datado);
                naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(skladniknadgodzinydelegowanie);
                naliczenieskladnikawynagrodzenia.setStawkagodzinowawaluta(kalendarz.getDodatekzanadgodziny());
                naliczenieskladnikawynagrodzenia.setStawkagodzinowa(Z.z(kalendarz.getDodatekzanadgodziny() * pasekwynagrodzen.getKurs()));
                naliczenieskladnikawynagrodzenia.setGodzinyfaktyczne(nadliczbowe);
                naliczenieskladnikawynagrodzenia.setDninalezne(dnirobocze);
                naliczenieskladnikawynagrodzenia.setGodzinynalezne(godzinyrobocze);
                naliczenieskladnikawynagrodzenia.setKwotadolistyplacwaluta(Z.z(naliczenieskladnikawynagrodzenia.getStawkagodzinowawaluta() * nadliczbowe));
                naliczenieskladnikawynagrodzenia.setKwotadolistyplac(Z.z(naliczenieskladnikawynagrodzenia.getStawkagodzinowa() * nadliczbowe));
                //naliczenieskladnikawynagrodzenia.setKwotazus(Z.z(stawkagodznowanormalna*nadliczbowe));
                naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(pasekwynagrodzen);
                pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
            }
        }
    }

    static void nalicznadgodzinyDB(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, boolean sto) {
        double godzinyrobocze = 0.0;
        double dnirobocze = 0.0;
        double nadliczbowe = 0.0;
        double dninadliczbowe = 0.0;
        String datapoczatek = kalendarz.getRok() + "-" + kalendarz.getMc() + "-";
        String dataod = kalendarz.getPierwszyDzien();
        String datado = kalendarz.getOstatniDzien();
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia() == 0) {
                dnirobocze = dnirobocze + 1;
                godzinyrobocze = godzinyrobocze + p.getNormagodzin();
            }
            double nadgodziny = sto ? p.getSetki() : p.getPiecdziesiatki();
            if (nadgodziny > 0.0) {
                dninadliczbowe = dninadliczbowe + 1;
                nadliczbowe = nadliczbowe + nadgodziny;
            }
        }
        Naliczenieskladnikawynagrodzenia wynagrodzeniedopobrania = pobierzskladnik(pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList(), "11");
        if (wynagrodzeniedopobrania != null) {
            Skladnikwynagrodzenia wynagrodzeniezasadnicze = wynagrodzeniedopobrania.getSkladnikwynagrodzenia();
            Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
            Skladnikwynagrodzenia skladniknadgodziny50 = pobierzskladnik(kalendarz, "12");
            if (skladniknadgodziny50 != null) {
                naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(skladniknadgodziny50);
                Zmiennawynagrodzenia zmiennabiezaca = pobierzaktywnazmienna(wynagrodzeniezasadnicze.getZmiennawynagrodzeniaList(), kalendarz);
                double skladnik = zmiennabiezaca.getKwota();
                double wspolczynnik = sto ? 2.0 : 1.5;
                double stawkagodznowanormalna = Z.z(skladnik / godzinyrobocze * wspolczynnik);
                String uwagi = sto ? "setki" : "pięćdz.";
                naliczenieskladnikawynagrodzenia.setDataod(dataod);
                naliczenieskladnikawynagrodzenia.setDatado(datado);
                naliczenieskladnikawynagrodzenia.setUwagi(uwagi);
                naliczenieskladnikawynagrodzenia.setStawkagodzinowa(stawkagodznowanormalna);
                naliczenieskladnikawynagrodzenia.setGodzinyfaktyczne(nadliczbowe);
                naliczenieskladnikawynagrodzenia.setDnifaktyczne(dninadliczbowe);
                naliczenieskladnikawynagrodzenia.setDninalezne(dnirobocze);
                naliczenieskladnikawynagrodzenia.setGodzinynalezne(godzinyrobocze);
                naliczenieskladnikawynagrodzenia.setKwotadolistyplac(Z.z(stawkagodznowanormalna * nadliczbowe));
                //naliczenieskladnikawynagrodzenia.setKwotazus(Z.z(stawkagodznowanormalna*nadliczbowe));
                naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(pasekwynagrodzen);
                boolean czyjuzjest = czyjuzjestdodane(pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList(), naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia());
                if (czyjuzjest && nadliczbowe == 0.0) {

                } else {
                    pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
                }
            }
        }
    }

    private static boolean czyjuzjestdodane(List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList, Skladnikwynagrodzenia skladnikwynagrodzenia) {
        boolean zwrot = false;
        if (naliczenieskladnikawynagrodzeniaList != null) {
            for (Naliczenieskladnikawynagrodzenia nal : naliczenieskladnikawynagrodzeniaList) {
                if (nal.getSkladnikwynagrodzenia().equals(skladnikwynagrodzenia)) {
                    zwrot = true;
                    break;
                }
            }
        }
        return zwrot;
    }

    static void nalicznadgodziny100(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen) {
        double godzinyrobocze = 0.0;
        double nadliczbowe = 0.0;
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia() == 0) {
                godzinyrobocze = godzinyrobocze + p.getNormagodzin();
            }
            if (p.getTypdnia() == 0 && p.getSetki() > 0.0) {
                nadliczbowe = nadliczbowe + p.getSetki();
            }
        }
        Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
        Skladnikwynagrodzenia wynagrodzeniezasadnicze = SkladnikwynagrodzeniaBean.createWynagrodzenie();
        Skladnikwynagrodzenia skladniknadgodziny100 = SkladnikwynagrodzeniaBean.createNadgodziny100();
        naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(skladniknadgodziny100);
        double skladnik = wynagrodzeniezasadnicze.getZmiennawynagrodzeniaList().get(0).getKwota();
        double stawkagodznowanormalna = skladnik / godzinyrobocze;
        naliczenieskladnikawynagrodzenia.setKwotaumownazacalymc(Z.z(stawkagodznowanormalna * nadliczbowe));
        //naliczenieskladnikawynagrodzenia.setKwotazus(Z.z(stawkagodznowanormalna*nadliczbowe));
        pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
    }

    static void redukujskladnikistale(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen) {
        if (pasekwynagrodzen.getNaliczenienieobecnoscList() != null && pasekwynagrodzen.getNaliczenienieobecnoscList().size() > 0) {
            for (Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia : pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList()) {
                double redukcjazarchorobe = 0.0;
                double redukcjazaurlop = 0.0;
                double redukcjazabezplatny = 0.0;
                double redukcjazadnipozaumowa = 0.0;
                for (Naliczenienieobecnosc p : pasekwynagrodzen.getNaliczenienieobecnoscList()) {
                    if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany() && p.getSkladnikwynagrodzenia().equals(naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia())) {
                        switch (p.getNieobecnosc().getKod()) {
                            case "ZC":
                                //redukcjazarchorobe = redukcjazarchorobe + p.getKwotaredukcji();
                                break;
                            case "CH":
                                //redukcjazarchorobe = redukcjazarchorobe + p.getKwotaredukcji();
                                break;
                            case "U":
                                //redukcjazaurlop = redukcjazaurlop+p.getKwotaredukcji();
                                break;
                            case "X":
                                //redukcjazabezplatny = redukcjazabezplatny+p.getKwotaredukcji();
                                break;
                            case "D":
                                //tego nie roboimy bo to stattystyczne
                                //redukcjazadnipozaumowa = redukcjazadnipozaumowa+p.getKwotastatystyczna();
                                break;
                            case "Z":
                                //redukcjazabezplatny = redukcjazabezplatny+p.getKwotaredukcji();
                                break;
                        }
                    }
                }
                //od 12-01-2023 rozliczamy redukcje w naliczeniu skladnikka
//                if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany()) {
//                    double wartoscskladnika = Z.z(naliczenieskladnikawynagrodzenia.getKwotadolistyplac());
//                    double redukcjasuma = Z.z(redukcjazarchorobe + redukcjazaurlop + redukcjazabezplatny + redukcjazadnipozaumowa);
//                    naliczenieskladnikawynagrodzenia.setKwotyredukujacesuma(redukcjasuma);
//                    double kwotadolistyplac = Z.z(naliczenieskladnikawynagrodzenia.getKwotadolistyplac() - naliczenieskladnikawynagrodzenia.getKwotyredukujacesuma());
//                        if (kwotadolistyplac<0.0) {
//                        kwotadolistyplac = 0.0;
//                        naliczenieskladnikawynagrodzenia.setKwotyredukujacesuma(wartoscskladnika);
//                    }
//                    naliczenieskladnikawynagrodzenia.setKwotadolistyplac(kwotadolistyplac);
//                }
            }
        }
    }

    //przywrocilem dla urlopu 21-02-2023
    static void redukujskladnikistale2(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen) {
        if (pasekwynagrodzen.getNaliczenienieobecnoscList() != null && pasekwynagrodzen.getNaliczenienieobecnoscList().size() > 0) {
            for (Naliczenieskladnikawynagrodzenia pa : pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList()) {
                double redukcjazarchorobe = 0.0;
                double redukcjazaurlop = 0.0;
                double redukcjazabezplatny = 0.0;
                double redukcjazadnipozaumowa = 0.0;
                for (Naliczenienieobecnosc p : pasekwynagrodzen.getNaliczenienieobecnoscList()) {
                    if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany() && p.getSkladnikwynagrodzenia().equals(pa.getSkladnikwynagrodzenia())) {
                        switch (p.getNieobecnosc().getKod()) {
                            case "CH":
                                //redukcjazarchorobe = redukcjazarchorobe+p.getKwotaredukcji();
                                break;
                            case "U":
                                //redukcjazaurlop = Z.z(redukcjazaurlop + p.getKwotaredukcji());
                                break;
                            case "X":
                                //redukcjazabezplatny = redukcjazabezplatny+p.getKwotaredukcji();
                                break;
                            case "D":
                                //redukcjazadnipozaumowa = redukcjazadnipozaumowa+p.getKwotastatystyczna();
                                break;
                            case "Z":
                                //redukcjazabezplatny = redukcjazabezplatny+p.getKwotaredukcji();
                                break;
                        }
                    }
                }
                if (pa.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany()) {
                    double kwotyredukujace = pa.getKwotyredukujacesuma();
                    pa.setKwotyredukujacesuma(Z.z(kwotyredukujace + redukcjazabezplatny + redukcjazaurlop + redukcjazadnipozaumowa));
                    double kwotalp = pa.getKwotadolistyplac() - redukcjazaurlop > 0.0 ? pa.getKwotadolistyplac() - redukcjazaurlop : 0.0;
                    pa.setKwotadolistyplac(kwotalp);
                }
            }
        }
    }

    private static Skladnikwynagrodzenia pobierzskladnik(Kalendarzmiesiac kalendarz, String kodskladnika) {
        Skladnikwynagrodzenia zwrot = null;
        for (Skladnikwynagrodzenia p : kalendarz.getAngaz().getSkladnikwynagrodzeniaList()) {
            if (p.getRodzajwynagrodzenia().getKod().equals(kodskladnika) && p.getRodzajwynagrodzenia().isAktywne()) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }

    private static Skladnikwynagrodzenia pobierzskladnikWksserial(Kalendarzmiesiac kalendarz, int wksserial) {
        Skladnikwynagrodzenia zwrot = null;
        for (Skladnikwynagrodzenia p : kalendarz.getAngaz().getSkladnikwynagrodzeniaList()) {
            if (p.getRodzajwynagrodzenia().getWks_serial() == wksserial && p.getRodzajwynagrodzenia().isAktywne()) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }

    private static Naliczenieskladnikawynagrodzenia pobierzskladnik(List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList, String kodskladnika) {
        Naliczenieskladnikawynagrodzenia zwrot = null;
        for (Naliczenieskladnikawynagrodzenia p : naliczenieskladnikawynagrodzeniaList) {
            if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals(kodskladnika)) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }

    private static double obliczsredniadopodstawy(Kalendarzmiesiac kalendarz, Skladnikwynagrodzenia skladnikwynagrodzenia, Nieobecnosc nieobecnosc) {
        String rok = kalendarz.getRok();
        String mc = kalendarz.getMc();
        String dataodnieobecnoscdataod = nieobecnosc.getDataod();
        //double kwotazumowy = skladnikwynagrodzenia.getZmiennawynagrodzeniaList()
        //czy umowa rozlicza pierwszy miesiac
        return 2800;
    }

    private static Zmiennawynagrodzenia pobierzaktywnazmienna(List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList, Kalendarzmiesiac kalendarz) {
        Zmiennawynagrodzenia zwrot = new Zmiennawynagrodzenia();
        if (zmiennawynagrodzeniaList != null) {
            for (Zmiennawynagrodzenia zmienna : zmiennawynagrodzeniaList) {
                if (DataBean.czysiemiesci(kalendarz.getPierwszyDzien(), kalendarz.getOstatniDzien(), zmienna.getDataod(), zmienna.getDatado())) {
                    zwrot = zmienna;
                    break;
                }
            }
        }
        return zwrot;
    }

}
