/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import comparator.Umowacomparator;
import data.Data;
import embeddable.Mce;
import entity.Dzien;
import entity.Kalendarzmiesiac;
import entity.Naliczenienieobecnosc;
import entity.Naliczeniepotracenie;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnosc;
import entity.Pasekwynagrodzen;
import entity.Rachunekdoumowyzlecenia;
import entity.Skladnikpotracenia;
import entity.Skladnikwynagrodzenia;
import entity.Sredniadlanieobecnosci;
import entity.Umowa;
import entity.Zmiennawynagrodzenia;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import z.Z;

/**
 *
 * @author Osito
 */
public class KalendarzmiesiacBean {
    
    public static Kalendarzmiesiac kalendarzmiesiac;
    
    public static Kalendarzmiesiac create() {
        if (kalendarzmiesiac==null) {
            kalendarzmiesiac = new Kalendarzmiesiac();
            kalendarzmiesiac.setUmowa(UmowaBean.create());
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
            String data = kalendarzmiesiac.getRok()+"-"+kalendarzmiesiac.getMc()+"-";
            int licznik = 1;
            kalendarzmiesiac.setDzienList(new ArrayList<>());
            kalendarzmiesiac.getDzienList().add(new Dzien(1, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(2, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(3, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(4, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(5, data+licznik++, 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(6, data+licznik++, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(7, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(8, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(9, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(10, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(11, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(12, data+licznik++, 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(13, data+licznik++, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(14, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(15, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(16, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(17, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(18, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(19, data+licznik++, 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(20, data+licznik++, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(21, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(22, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(23, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(24, data+licznik++, 3, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(25, data+licznik++, 3, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(26, data+licznik++, 3, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(27, data+licznik++, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(28, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(29, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(30, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(31, data+licznik++, 0, 8, 8, kalendarzmiesiac));
    }
    
    public static void reset(Kalendarzmiesiac kalendarzmiesiac) {
            String data = kalendarzmiesiac.getRok()+"-"+kalendarzmiesiac.getMc()+"-";
            String data2 = kalendarzmiesiac.getRok()+"-"+kalendarzmiesiac.getMc()+"-0";
            int licznik = 1;
            kalendarzmiesiac.setDzienList(new ArrayList<>());
            kalendarzmiesiac.getDzienList().add(new Dzien(1, data2+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(2, data2+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(3, data2+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(4, data2+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(5, data2+licznik++, 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(6, data2+licznik++, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(7, data2+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(8, data2+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(9, data2+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(10, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(11, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(12, data+licznik++, 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(13, data+licznik++, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(14, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(15, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(16, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(17, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(18, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(19, data+licznik++, 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(20, data+licznik++, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(21, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(22, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(23, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(24, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(25, data+licznik++, 3, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(26, data+licznik++, 3, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(27, data+licznik++, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(28, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(29, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(30, data+licznik++, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(31, data+licznik++, 0, 8, 8, kalendarzmiesiac));
    }

    static void dodajnieobecnosc(Kalendarzmiesiac kalendarz, Nieobecnosc nieobecnosc, Pasekwynagrodzen pasekwynagrodzen) {
        int dzienod = Integer.parseInt(Data.getDzien(nieobecnosc.getDataod()));
        int dziendo = Integer.parseInt(Data.getDzien(nieobecnosc.getDatado()));
        for (int i = dzienod;i<dziendo+1;i++) {
            for (Dzien p : kalendarz.getDzienList()) {
                if (p.getNrdnia()==i) {
                    if (nieobecnosc.getNieobecnosckodzus().getKod().equals("200")) {
                        p.setNormagodzin(0.0);
                    }
                    p.setPrzepracowano(0.0);
                    p.setKod(nieobecnosc.getNieobecnosckodzus().getKod());
                    break;
                }
            }
        }
        if (nieobecnosc.getNieobecnosckodzus().getKod().equals("331")) {
            naliczskladnikiwynagrodzeniazaChorobe(kalendarz, nieobecnosc, pasekwynagrodzen);
        } else if (nieobecnosc.getNieobecnosckodzus().getKod().equals("100")) {
            naliczskladnikiwynagrodzeniazaUrlop(kalendarz, nieobecnosc, pasekwynagrodzen);
        } else if (nieobecnosc.getNieobecnosckodzus().getKod().equals("111")) {
            naliczskladnikiwynagrodzeniazaOkresnieprzepracowany(kalendarz, nieobecnosc, pasekwynagrodzen,"111");
        } else if (nieobecnosc.getNieobecnosckodzus().getKod().equals("200")) {
            naliczskladnikiwynagrodzeniazaOkresnieprzepracowany(kalendarz, nieobecnosc, pasekwynagrodzen,"200");
        }
    }
    
         
    static void dodajnieobecnoscDB(Kalendarzmiesiac kalendarz, List<Nieobecnosc> nieobecnosclista, Pasekwynagrodzen pasekwynagrodzen) {
        if (nieobecnosclista!=null && !nieobecnosclista.isEmpty()) {
            for (Nieobecnosc nieobecnosc : nieobecnosclista) {
                int dzienod = Integer.parseInt(Data.getDzien(nieobecnosc.getDataod()));
                int dziendo = Integer.parseInt(Data.getDzien(nieobecnosc.getDatado()));
                for (int i = dzienod;i<dziendo+1;i++) {
                    for (Dzien p : kalendarz.getDzienList()) {
                        if (p.getNrdnia() == i) {
                            if (nieobecnosc.getNieobecnosckodzus().getKod().equals("200")) {
                                p.setNormagodzin(0.0);
                            }
                            p.setPrzepracowano(0.0);
                            p.setKod(nieobecnosc.getNieobecnosckodzus().getKod());
                            break;
                        }
            }
                }
                if (nieobecnosc.getNieobecnosckodzus().getKod().equals("313")) {
                    //wynagrodzenie za czas niezdolnosci od pracy
                    naliczskladnikiwynagrodzeniazaChorobe(kalendarz, nieobecnosc, pasekwynagrodzen);
                } else if (nieobecnosc.getNieobecnosckodzus().getKod().equals("331")) {
                    //wynagrodzenie za czas niezdolnosci od pracy
                    naliczskladnikiwynagrodzeniazaChorobe(kalendarz, nieobecnosc, pasekwynagrodzen);
                } else if (nieobecnosc.getNieobecnosckodzus().getKod().equals("100")) {
                    //urlop wypoczynowy
                    naliczskladnikiwynagrodzeniazaUrlop(kalendarz, nieobecnosc, pasekwynagrodzen);
                } else if (nieobecnosc.getNieobecnosckodzus().getKod().equals("111")) {
                    //urlopo bezpłatny
                    naliczskladnikiwynagrodzeniazaOkresnieprzepracowany(kalendarz, nieobecnosc, pasekwynagrodzen,"111");
                } else if (nieobecnosc.getNieobecnosckodzus().getKod().equals("200")) {
                    //rozpoczęcie umowy w trakcie meisiąca
                    naliczskladnikiwynagrodzeniazaOkresnieprzepracowany(kalendarz, nieobecnosc, pasekwynagrodzen,"200");
                } else if (nieobecnosc.getNieobecnosckodzus().getKod().equals("777")) {
                    //oddelegowanie
                    naliczskladnikiwynagrodzeniazaOkresnieprzepracowany(kalendarz, nieobecnosc, pasekwynagrodzen,"777");
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
        for (Skladnikwynagrodzenia p : kalendarz.getUmowa().getSkladnikwynagrodzeniaList()) {
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
                double stawkadzienna = skladnikistale / godzinyroboczewmiesiacu;
                double dowyplatyzaczasnieobecnosci = Z.z(stawkadzienna * godzinyobecnoscirobocze);
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
    
    static boolean naliczskladnikiwynagrodzeniaDB(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, double kurs) {
        boolean jestoddelegowanie = false;
        for (Skladnikwynagrodzenia p : kalendarz.getUmowa().getSkladnikwynagrodzeniaList()) {
            if (p.getRodzajwynagrodzenia().getKod().equals("11")) {
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createWynagrodzenieDB(kalendarz, pasekwynagrodzen, p, kurs);
                if (naliczenieskladnikawynagrodzenia.getKwotaumownazacalymc()!=0.0) {
                    pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
                    if (p.isOddelegowanie()) {
                        jestoddelegowanie = true;
                    }
                }
            } else if (p.getRodzajwynagrodzenia().getKod().equals("21")) {
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createPremiaDB(kalendarz, pasekwynagrodzen, p);
                    pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
            } else if (p.getRodzajwynagrodzenia().getKod().equals("90")) {
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createBezZusPodatekDB(kalendarz, pasekwynagrodzen, p);
                if (naliczenieskladnikawynagrodzenia.getKwotaumownazacalymc()!=0.0) {
                    pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
                }
            }
        }
        return jestoddelegowanie;
    }
    
    static boolean naliczskladnikiwynagrodzeniaDBZlecenie(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, double kurs) {
        boolean jestoddelegowanie = false;
        for (Skladnikwynagrodzenia p : kalendarz.getUmowa().getSkladnikwynagrodzeniaList()) {
            if (p.getRodzajwynagrodzenia().getKod().equals("11")||p.getRodzajwynagrodzenia().getKod().equals("50")) {
                Umowa umowa = kalendarz.getUmowa();
                Rachunekdoumowyzlecenia rachunekdoumowyzlecenia = umowa.pobierzRachunekzlecenie(kalendarz.getRok(), kalendarz.getMc());
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createWynagrodzenieDBZlecenie(pasekwynagrodzen, p, kalendarz.getDzienList(), kurs, rachunekdoumowyzlecenia);
                if (naliczenieskladnikawynagrodzenia.getKwotaumownazacalymc()!=0.0) {
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
        for (Skladnikpotracenia p : kalendarz.getUmowa().getSkladnikpotraceniaList()) {
            Naliczeniepotracenie naliczeniepotracenie = NaliczeniepotracenieBean.create();
            pasekwynagrodzen.getNaliczeniepotracenieList().add(naliczeniepotracenie);
        }
    }
    static void naliczskladnikipotraceniaDB(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen, double wolneodzajecia) {
        for (Skladnikpotracenia p : kalendarz.getUmowa().getSkladnikpotraceniaList()) {
            Naliczeniepotracenie naliczeniepotracenie = NaliczeniepotracenieBean.createPotracenieDB(pasekwynagrodzen, p, wolneodzajecia);
            if (naliczeniepotracenie.getKwota()>0.0) {
                pasekwynagrodzen.getNaliczeniepotracenieList().add(naliczeniepotracenie);
            }
        }
    }

    static void naliczskladnikiwynagrodzeniazaChorobe(Kalendarzmiesiac kalendarz, Nieobecnosc nieobecnosc, Pasekwynagrodzen pasekwynagrodzen) {
        for (Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia : pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList()) {
                Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
                Skladnikwynagrodzenia skladnikwynagrodzenia = naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia();
                naliczenienieobecnosc.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                naliczenienieobecnosc.setNieobecnosc(nieobecnosc);
                naliczenienieobecnosc.setJakiskladnikredukowalny(naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getUwagi());
                double sredniadopodstawy = wyliczsredniachoroba(kalendarz, naliczenieskladnikawynagrodzenia, nieobecnosc);
                naliczenienieobecnosc.setPodstawadochoroby(sredniadopodstawy);
                double dniroboczenieobecnosc = Data.iletodniKalendarzowych(nieobecnosc.getDataod(), nieobecnosc.getDatado());
                //double skladnik = obliczsredniadopodstawy(kalendarz,p.getSkladnikwynagrodzenia(), nieobecnosc);
                double skladnikistalenetto = sredniadopodstawy-(sredniadopodstawy*.1371);
                double skladnikistaledoredukcji = sredniadopodstawy;
                naliczenienieobecnosc.setSkladnikistale(skladnikistalenetto);
                double procentzazwolnienie = 0.8;
                naliczenienieobecnosc.setProcentzazwolnienie(procentzazwolnienie);
                double stawkadzienna = Z.z(skladnikistalenetto/30)*procentzazwolnienie;
                naliczenienieobecnosc.setStawkadzienna(stawkadzienna);
                double dowyplatyzaczasnieobecnosci = Z.z(stawkadzienna*dniroboczenieobecnosc);
                naliczenienieobecnosc.setKwota(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setKwotabezzus(dowyplatyzaczasnieobecnosci);
                double stawkadziennaredukcji = Z.z(skladnikistaledoredukcji/30);
                naliczenienieobecnosc.setStawkadziennaredukcji(stawkadziennaredukcji);
                double kwotaredukcji = Z.z(stawkadziennaredukcji*dniroboczenieobecnosc);
                if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany()) {
                    naliczenienieobecnosc.setKwotaredukcji(kwotaredukcji);
                }
                naliczenienieobecnosc.setPasekwynagrodzen(pasekwynagrodzen);
                pasekwynagrodzen.getNaliczenienieobecnoscList().add(naliczenienieobecnosc);
//                p.setKwotyredukujacesuma(p.getKwotyredukujacesuma()+kwotaredukcji);
//                p.setKwotadolistyplac(p.getKwotaumownazacalymc()-p.getKwotyredukujacesuma());
        }
//        11 - zasadnicze 20-premia 30-nadgodziny
//        if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("21")) {
//            skladnik = Z.z(1800 / 3.0);
//        }
//        if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("12") || p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("31")) {
//            skladnik = Z.z(210 / 3.0);
//        }
    }
    
    private static double wyliczsredniachoroba(Kalendarzmiesiac kalendarz, Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia, Nieobecnosc nieobecnosc) {
        double zwrot = 0.0;
        List<Umowa> listaumow = kalendarz.getUmowa().getAngaz().getUmowaList();
        Collections.sort(listaumow, new Umowacomparator());
        List<Kalendarzmiesiac> kalendarze = new ArrayList<>();
        for (Umowa p : listaumow) {
            for (Kalendarzmiesiac kal : p.getKalendarzmiesiacList()) {
                kalendarze.add(kal);
                if (kalendarze.size()>12) {
                    break;
                }
            }
        }
        double dniroboczewmiesiacu = 0.0;
        for (Dzien pa : kalendarz.getDzienList()) {
            if (pa.getTypdnia() == 0) {
                dniroboczewmiesiacu++;
            }
        }
        double godzinyroboczewmiesiacu = dniroboczewmiesiacu * 8.0;
        double sredniadopodstawy = 0.0;
        if (kalendarze.size()==1) {
             //wyliczenie dla skladnika stalego ze zmiennymi
            if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getGodzinowe0miesieczne1()&&naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getStale0zmienne1()==false) {
                double skladnikistale = 0.0;
                double dniroboczeprzepracowanestat = 0.0;
                for (Zmiennawynagrodzenia r : naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getZmiennawynagrodzeniaList()) {
                    double dniroboczeprzepracowanezm = 0.0;
                    double godzinyobecnosciroboczezm = 0.0;
                    int dzienodzmienna = DataBean.dataod(r.getDataod(), kalendarz.getRok(), kalendarz.getMc());
                    int dziendozmienna = DataBean.datado(r.getDatado(), kalendarz.getRok(), kalendarz.getMc());
                    if (DataBean.czysiemiesci(kalendarz, r.getDataod(), r.getDatado())) {
                        skladnikistale = r.getKwota();
                        for (Dzien s : kalendarz.getDzienList()) {
                            //daje norma godzin a nie z uwzglednieniem zwolnien bo przeciez rewdukcja bedzie pozniej
                            if (s.getTypdnia() == 0 && s.getNormagodzin()>0.0 && s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                                dniroboczeprzepracowanezm++;
                            }
                            if (s.getTypdnia() == 0 && s.getPrzepracowano()>0.0 && s.getNrdnia() >= dzienodzmienna && s.getNrdnia() <= dziendozmienna) {
                                dniroboczeprzepracowanestat++;
                            }
                        }
                    }
                    godzinyobecnosciroboczezm = dniroboczeprzepracowanezm * 8.0;
                    double stawkadziennazm = skladnikistale / godzinyroboczewmiesiacu;
                    sredniadopodstawy = sredniadopodstawy + Z.z(stawkadziennazm * godzinyobecnosciroboczezm);
                }
            } else if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getStale0zmienne1()==true) {
                  /* nie bedzie tego bo srednia ze zmiennych jest brana z poprzednich miesiecy, a tu ich nie ma*/
            }
        } else {
            sredniadopodstawy = 0.0;
            for (Kalendarzmiesiac p : kalendarze) {
                if (!p.equals(kalendarz)) {
                    if (p.czyjestchoroba()) {
                        int ilemcy = Mce.odlegloscMcy(p.getMc(), p.getRok(), kalendarz.getMc(), kalendarz.getRok());
                        if (ilemcy <= 3) {
                            sredniadopodstawy = p.pobierzPodstaweNieobecnosc(nieobecnosc);
                        }
                    }
                }
            }
            //idziemy dalej jak nie bylo choroby w ciagu ostatnich 3 mcy
            if (sredniadopodstawy==0.0) {
                double i = 0.0;
                for (Iterator<Kalendarzmiesiac> it = kalendarze.iterator();it.hasNext();) {
                    Kalendarzmiesiac p = it.next();
                    if (p.czyjestwiecejniepracy()||p.equals(kalendarz)) {
                        it.remove();
                    } else {
                        double[] czywaloryzowac = p.chorobaczywaloryzacja();
                        boolean waloryzowac = czywaloryzowac[2]==1;
                        sredniadopodstawy = sredniadopodstawy+p.getPasek().pobierznaliczeniadochorobowego(waloryzowac, naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia());
                        i++;
                    }
                }
                if (i>0) {
                    sredniadopodstawy = Z.z(sredniadopodstawy/i);
                }
            }
        }
        
        /***************************/
       
        return sredniadopodstawy;
    }

    static void naliczskladnikiwynagrodzeniazaUrlop(Kalendarzmiesiac kalendarz, Nieobecnosc nieobecnosc, Pasekwynagrodzen pasekwynagrodzen) {
        double dniroboczewmiesiacu = 0.0;
        double dninieobecnoscirobocze = 0.0;
        double liczbagodzinurlopu = 0.0;
        double liczbagodzinroboczych = 0.0;
        int dzienod = Data.getDzienI(nieobecnosc.getDataod());
        int dziendo = Data.getDzienI(nieobecnosc.getDatado());
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia()==0 && p.getNormagodzin()!=0.0) {
                if (p.getPrzepracowano()>0.0 || p.getKod().equals("100")) {
                    dniroboczewmiesiacu = dniroboczewmiesiacu+1;
                    liczbagodzinroboczych = liczbagodzinroboczych+p.getNormagodzin();
                }
            }
            if (p.getTypdnia()==0 && p.getNrdnia()>=dzienod &&p.getNrdnia()<=dziendo) {
                dninieobecnoscirobocze = dninieobecnoscirobocze+1;
                liczbagodzinurlopu = liczbagodzinurlopu+p.getUrlopPlatny();
            }
        }
        for (Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia : pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList()) {
            if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getGodzinowe0miesieczne1()&&naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getStale0zmienne1()==false) {
                double skladnikistale = 0.0;
                Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
                naliczenienieobecnosc.setNieobecnosc(nieobecnosc);
                Skladnikwynagrodzenia skladnikwynagrodzenia = naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia();
                naliczenienieobecnosc.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                double sredniagodzinowa = wyliczsredniagodzinowa(kalendarz, naliczenieskladnikawynagrodzenia, liczbagodzinurlopu, naliczenienieobecnosc);
                naliczenienieobecnosc.setJakiskladnikredukowalny(naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getUwagi()); 
                naliczenienieobecnosc.setSkladnikistale(sredniagodzinowa);
                naliczenienieobecnosc.setLiczbagodzinroboczych(liczbagodzinroboczych);
                naliczenienieobecnosc.setLiczbagodzinurlopu(liczbagodzinurlopu);
                double stawkadzienna = sredniagodzinowa / liczbagodzinroboczych;
                naliczenienieobecnosc.setStawkadzienna(stawkadzienna);
                double dowyplatyzaczasnieobecnosci = Z.z(stawkadzienna * liczbagodzinurlopu);
                naliczenienieobecnosc.setKwota(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setKwotazus(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setKwotaredukcji(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setPasekwynagrodzen(pasekwynagrodzen);
                pasekwynagrodzen.getNaliczenienieobecnoscList().add(naliczenienieobecnosc);
            } else if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getStale0zmienne1()==true) {
                Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
                naliczenienieobecnosc.setNieobecnosc(nieobecnosc);
                Skladnikwynagrodzenia skladnikwynagrodzenia = naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia();
                naliczenienieobecnosc.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                double dowyplatyzaczasnieobecnosci = wyliczsredniagodzinowa(kalendarz, naliczenieskladnikawynagrodzenia, liczbagodzinurlopu, naliczenienieobecnosc);
                naliczenienieobecnosc.setJakiskladnikredukowalny(naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getUwagi()); 
                naliczenienieobecnosc.setSkladnikistale(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setLiczbagodzinroboczych(liczbagodzinroboczych);
                naliczenienieobecnosc.setLiczbagodzinurlopu(liczbagodzinurlopu);
                double stawkadzienna = dowyplatyzaczasnieobecnosci / liczbagodzinroboczych;
                naliczenienieobecnosc.setStawkadzienna(stawkadzienna);
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
    
    static void naliczskladnikiwynagrodzeniazaOkresnieprzepracowany(Kalendarzmiesiac kalendarz, Nieobecnosc nieobecnosc, Pasekwynagrodzen pasekwynagrodzen, String kod) {
        double dniroboczewmiesiacu = 0.0;
        int dzienod = Data.getDzienI(nieobecnosc.getDataod());
        int dziendo = Data.getDzienI(nieobecnosc.getDatado());
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia()==0) {
                dniroboczewmiesiacu++;
            }
        }
        double godzinyroboczewmiesiacu = dniroboczewmiesiacu*8.0;
//        Rozkład czasu pracy pracownika nie ma znaczenia dla ustalenia wysokości przysługującego mu wynagrodzenia za pracę. 
//        Istotna jest liczba godzin do przepracowania, która powinna być ustalona przy zastosowaniu art. 130 K.p. 
//        W takim przypadku należy więc przyjmować nominalną, a nie rozkładową liczbę godzin pracy.
//        Wg superplac dotyczy to zaczecia pracy w miesiacu jak i bezplatnego
        for (Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia : pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList()) {
            if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany() && naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getStale0zmienne1()==false) {
                double dninieobecnoscirobocze = 0.0;
                Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
                Skladnikwynagrodzenia skladnikwynagrodzenia = naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia();
                naliczenienieobecnosc.setNieobecnosc(nieobecnosc);
                naliczenienieobecnosc.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                double skladnikistale = 0.0;
                int dzienodzmienna = DataBean.dataod(naliczenieskladnikawynagrodzenia.getDataod(), kalendarz.getRok(), kalendarz.getMc());
                int dziendozmienna = DataBean.datado(naliczenieskladnikawynagrodzenia.getDatado(), kalendarz.getRok(), kalendarz.getMc());
                skladnikistale = naliczenieskladnikawynagrodzenia.getKwotaumownazacalymc();
                if (kod.equals("200")) {
                    for (Dzien s : kalendarz.getDzienList()) {
                        if (s.getTypdnia()==0 && s.getKod()!=null && s.getKod().equals("200")) {
                            if (s.getNrdnia()>=dzienod &&s.getNrdnia()<=dziendo) {
                                dninieobecnoscirobocze++;
                            }
                        }
                    }
                } else {
                    for (Dzien s : kalendarz.getDzienList()) {
                       if (s.getTypdnia()==0 && s.getNrdnia()>=dzienod &&s.getNrdnia()<=dziendo) {
                           if (s.getNrdnia()>=dzienodzmienna &&s.getNrdnia()<=dziendozmienna) {
                               dninieobecnoscirobocze++;
                           }
                       }
                   }
                }
                double godzinynieobecnoscirobocze = dninieobecnoscirobocze*8.0;
                naliczenienieobecnosc.setSkladnikistale(skladnikistale);
                naliczenienieobecnosc.setLiczbagodzinroboczych(godzinyroboczewmiesiacu);
                naliczenienieobecnosc.setLiczbagodzinurlopu(godzinynieobecnoscirobocze);
                
                double stawkadzienna = Z.z(skladnikistale / godzinyroboczewmiesiacu);
                 naliczenienieobecnosc.setStawkadzienna(stawkadzienna);
                double dowyplatyzaczasnieobecnosci = Z.z4(stawkadzienna * godzinynieobecnoscirobocze);
                naliczenienieobecnosc.setKwota(0.0);
                naliczenienieobecnosc.setKwotazus(0.0);
                if (kod.equals("200")) {
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
            if (p.getTypdnia()==0) {
                godzinyrobocze = godzinyrobocze+p.getNormagodzin();
            }
            if (p.getTypdnia()==0 && p.getPiecdziesiatki()>0.0) {
                nadliczbowe = nadliczbowe+p.getPiecdziesiatki();
            }
        }
        Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
        Skladnikwynagrodzenia wynagrodzeniezasadnicze = SkladnikwynagrodzeniaBean.createWynagrodzenie();
        Skladnikwynagrodzenia skladniknadgodziny50 = SkladnikwynagrodzeniaBean.createNadgodziny50();
        naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(skladniknadgodziny50);
        double skladnik = wynagrodzeniezasadnicze.getZmiennawynagrodzeniaList().get(0).getKwota();
        double stawkagodznowanormalna = skladnik / godzinyrobocze*0.5;
        naliczenieskladnikawynagrodzenia.setKwotaumownazacalymc(Z.z(stawkagodznowanormalna*nadliczbowe));
        naliczenieskladnikawynagrodzenia.setKwotadolistyplac(Z.z(stawkagodznowanormalna*nadliczbowe));
        pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
    }
    
    static void nalicznadgodziny50DB(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen) {
        double godzinyrobocze = 0.0;
        double nadliczbowe = 0.0;
        String datapoczatek = kalendarz.getRok()+"-"+kalendarz.getMc()+"-";
        String dataod = null;
        String datado = null;
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia()==0) {
                godzinyrobocze = godzinyrobocze+p.getNormagodzin();
            }
            if (p.getTypdnia()==0 && p.getPiecdziesiatki()>0.0) {
                nadliczbowe = nadliczbowe+p.getPiecdziesiatki();
                if (dataod==null) {
                    String nrdnia = String.valueOf(p.getNrdnia());
                    if (nrdnia.length()==1) {
                        nrdnia = "0"+nrdnia;
                    }
                    dataod = datapoczatek+nrdnia;
                } else {
                    String nrdnia = String.valueOf(p.getNrdnia());
                    if (nrdnia.length()==1) {
                        nrdnia = "0"+nrdnia;
                    }
                    datado = datapoczatek+nrdnia;
                }
            }
        }
        if (nadliczbowe>0.0) {
            Naliczenieskladnikawynagrodzenia wynagrodzeniedopobrania = pobierzskladnik(pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList(), "11");
            Skladnikwynagrodzenia wynagrodzeniezasadnicze = wynagrodzeniedopobrania.getSkladnikwynagrodzenia();
            Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
            Skladnikwynagrodzenia skladniknadgodziny50 = pobierzskladnik(kalendarz, "12");
            naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(skladniknadgodziny50);
            double skladnik = wynagrodzeniezasadnicze.getZmiennawynagrodzeniaList().get(0).getKwota();
            double stawkagodznowanormalna = skladnik / godzinyrobocze*0.5;
            naliczenieskladnikawynagrodzenia.setKwotaumownazacalymc(Z.z(stawkagodznowanormalna*nadliczbowe));
            //naliczenieskladnikawynagrodzenia.setKwotazus(Z.z(stawkagodznowanormalna*nadliczbowe));
            naliczenieskladnikawynagrodzenia.setPasekwynagrodzen(pasekwynagrodzen);
            pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
        }
    }
    
    static void nalicznadgodziny100(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen) {
        double godzinyrobocze = 0.0;
        double nadliczbowe = 0.0;
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia()==0) {
                godzinyrobocze = godzinyrobocze+p.getNormagodzin();
            }
            if (p.getTypdnia()==0 && p.getSetki()>0.0) {
                nadliczbowe = nadliczbowe+p.getSetki();
            }
        }
        Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = new Naliczenieskladnikawynagrodzenia();
        Skladnikwynagrodzenia wynagrodzeniezasadnicze = SkladnikwynagrodzeniaBean.createWynagrodzenie();
        Skladnikwynagrodzenia skladniknadgodziny100 = SkladnikwynagrodzeniaBean.createNadgodziny100();
        naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(skladniknadgodziny100);
        double skladnik = wynagrodzeniezasadnicze.getZmiennawynagrodzeniaList().get(0).getKwota();
        double stawkagodznowanormalna = skladnik / godzinyrobocze;
        naliczenieskladnikawynagrodzenia.setKwotaumownazacalymc(Z.z(stawkagodznowanormalna*nadliczbowe));
        //naliczenieskladnikawynagrodzenia.setKwotazus(Z.z(stawkagodznowanormalna*nadliczbowe));
        pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
    }

    static void redukujskladnikistale(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen) {
        if (pasekwynagrodzen.getNaliczenienieobecnoscList()!=null&&pasekwynagrodzen.getNaliczenienieobecnoscList().size()>0) {
            for (Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia : pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList()) {
                double redukcjazarchorobe = 0.0;
                double redukcjazaurlop = 0.0;
                double redukcjazabezplatny = 0.0;
                for (Naliczenienieobecnosc p : pasekwynagrodzen.getNaliczenienieobecnoscList()) {
                    if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany() && p.getSkladnikwynagrodzenia().equals(naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia())) {
                        switch (p.getNieobecnosc().getNieobecnosckodzus().getKod()) {
                            case "331":
                                redukcjazarchorobe = redukcjazarchorobe+p.getKwotaredukcji();
                                break;
                            case "100":
                                redukcjazaurlop = redukcjazaurlop+p.getKwotaredukcji();
                                break;
                            case "111":
                                redukcjazabezplatny = redukcjazabezplatny+p.getKwotaredukcji();
                                break;
                            case "200":
                                //tego nie roboimy bo to stattystyczne
                                //redukcjazabezplatny = redukcjazabezplatny+p.getKwotastatystyczna();
                                break;
                            case "777":
                                redukcjazabezplatny = redukcjazabezplatny+p.getKwotaredukcji();
                                break;
                        }
                    }
                }
                if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany()) {
                    naliczenieskladnikawynagrodzenia.setKwotyredukujacesuma(Z.z(redukcjazarchorobe+redukcjazaurlop+redukcjazabezplatny));
                    naliczenieskladnikawynagrodzenia.setKwotadolistyplac(Z.z(naliczenieskladnikawynagrodzenia.getKwotadolistyplac()-naliczenieskladnikawynagrodzenia.getKwotyredukujacesuma()));
                }
            }
        }
    }
    static void redukujskladnikistale2(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen) {
        if (pasekwynagrodzen.getNaliczenienieobecnoscList()!=null&&pasekwynagrodzen.getNaliczenienieobecnoscList().size()>0) {
            for (Naliczenieskladnikawynagrodzenia pa : pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList()) {
                double redukcjazarchorobe = 0.0;
                double redukcjazaurlop = 0.0;
                double redukcjazabezplatny = 0.0;
                for (Naliczenienieobecnosc p : pasekwynagrodzen.getNaliczenienieobecnoscList()) {
                    if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany() && p.getSkladnikwynagrodzenia().equals(pa.getSkladnikwynagrodzenia())) {
                        switch (p.getNieobecnosc().getNieobecnosckodzus().getKod()) {
                            case "331":
                                //redukcjazarchorobe = redukcjazarchorobe+p.getKwotaredukcji();
                                break;
                            case "100":
                                redukcjazaurlop = redukcjazaurlop+p.getKwotaredukcji();
                                break;
                            case "111":
                                //redukcjazabezplatny = redukcjazabezplatny+p.getKwotaredukcji();
                                break;
                            case "200":
                                redukcjazabezplatny = redukcjazabezplatny+p.getKwotastatystyczna();
                                break;
                            case "777":
                                //redukcjazabezplatny = redukcjazabezplatny+p.getKwotaredukcji();
                                break;
                        }
                    }
                }
                if (pa.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany()) {
                    double kwotyredukujace = pa.getKwotyredukujacesuma();
                    pa.setKwotyredukujacesuma(Z.z(kwotyredukujace+redukcjazabezplatny+redukcjazaurlop));
                    pa.setKwotadolistyplac(Z.z(pa.getKwotadolistyplac()-redukcjazaurlop));
                }
            }
        }
    }

    private static Skladnikwynagrodzenia pobierzskladnik(Kalendarzmiesiac kalendarz, String kodskladnika) {
        Skladnikwynagrodzenia zwrot = null;
        for (Skladnikwynagrodzenia p : kalendarz.getUmowa().getSkladnikwynagrodzeniaList()) {
            if (p.getRodzajwynagrodzenia().getKod().equals(kodskladnika)) {
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
        Umowa umowa = kalendarz.getUmowa();
        String dataod = umowa.getDataod();
        String rok = kalendarz.getRok();
        String mc = kalendarz.getMc();
        String dataodnieobecnoscdataod = nieobecnosc.getDataod();
        //double kwotazumowy = skladnikwynagrodzenia.getZmiennawynagrodzeniaList()
        //czy umowa rozlicza pierwszy miesiac
        return 2800;
    }

    private static double wyliczsredniagodzinowa(Kalendarzmiesiac kalendarz, Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia, double liczbagodzinieobecnosci, Naliczenienieobecnosc naliczenienieobecnosc) {
        double sredniadopodstawy = 0.0;
        //wyliczenie dla skladnika stalego ze zmiennymi
        if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getGodzinowe0miesieczne1()&&naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getStale0zmienne1()==false) {
             sredniadopodstawy = naliczenieskladnikawynagrodzenia.getKwotadolistyplac();
             boolean skladnikstaly = true;
             Sredniadlanieobecnosci srednia = new Sredniadlanieobecnosci(kalendarz.getRok(), kalendarz.getMc(), sredniadopodstawy, skladnikstaly, naliczenienieobecnosc, liczbagodzinieobecnosci);
             naliczenienieobecnosc.getSredniadlanieobecnosciList().add(srednia);
        } else if (naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getStale0zmienne1()==true) {
              Umowa umowa = kalendarz.getUmowa();
                List<Naliczenieskladnikawynagrodzenia> naliczonyskladnikdosredniej = umowa.pobierzpaski(kalendarz.getRok(), kalendarz.getMc(), naliczenieskladnikawynagrodzenia.getSkladnikwynagrodzenia());
                double godzinyfaktyczne = 0.0;
                double kwotywyplacone = 0.0;
                double stawkazagodzine = 0.0;
                int liczba = 0;
                for (Naliczenieskladnikawynagrodzenia pa : naliczonyskladnikdosredniej) {
                    godzinyfaktyczne = godzinyfaktyczne+pa.getGodzinyfaktyczne();
                    kwotywyplacone = kwotywyplacone+pa.getKwotadolistyplac();
                    liczba++;
                    boolean skladnikstaly = false;
                    double stawkazagodzinezm = Z.z(pa.getKwotadolistyplac()/pa.getGodzinyfaktyczne());
                    double sredniadopodstazm = Z.z(stawkazagodzinezm * liczbagodzinieobecnosci);
                    Sredniadlanieobecnosci srednia = new Sredniadlanieobecnosci(pa.getPasekwynagrodzen().getRok(), pa.getPasekwynagrodzen().getMc(), pa.getKwotadolistyplac(), skladnikstaly, naliczenienieobecnosc, liczbagodzinieobecnosci, pa.getGodzinyfaktyczne(), pa.getDnifaktyczne(), pa.getGodzinynalezne(), pa.getDninalezne(), stawkazagodzinezm);
                    naliczenienieobecnosc.getSredniadlanieobecnosciList().add(srednia);
                    if(liczba>3) {
                        break;
                    }
                }
                stawkazagodzine = Z.z(kwotywyplacone/godzinyfaktyczne);
                sredniadopodstawy = sredniadopodstawy + Z.z(stawkazagodzine * liczbagodzinieobecnosci);
        }
        return sredniadopodstawy;
    }

    
    
}
