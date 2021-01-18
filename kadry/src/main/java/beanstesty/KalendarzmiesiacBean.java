/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import data.Data;
import entity.Dzien;
import entity.Kalendarzmiesiac;
import entity.Naliczenienieobecnosc;
import entity.Naliczeniepotracenie;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnosc;
import entity.Pasekwynagrodzen;
import entity.Skladnikpotracenia;
import entity.Skladnikwynagrodzenia;
import java.util.ArrayList;
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
            kalendarzmiesiac.getDzienList().add(new Dzien(1, 0, 8, 8, 2, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(2, 0, 8, 8, 2, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(3, 0, 8, 8, 2, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(4, 0, 8, 8, 2, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(5, 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(6, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(7, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(8, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(9, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(10, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(11, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(12, 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(13, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(14, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(15, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(16, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(17, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(18, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(19, 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(20, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(21, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(22, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(23, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(24, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(25, 3, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(26, 3, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(27, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(28, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(29, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(30, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(31, 0, 8, 8, kalendarzmiesiac));
        }
        return kalendarzmiesiac;
    }
    
    public static void create(Kalendarzmiesiac kalendarzmiesiac) {
            kalendarzmiesiac.setDzienList(new ArrayList<>());
            kalendarzmiesiac.getDzienList().add(new Dzien(1, 0, 8, 8, 2, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(2, 0, 8, 8, 2, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(3, 0, 8, 8, 2, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(4, 0, 8, 8, 2, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(5, 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(6, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(7, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(8, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(9, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(10, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(11, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(12, 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(13, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(14, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(15, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(16, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(17, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(18, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(19, 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(20, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(21, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(22, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(23, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(24, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(25, 3, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(26, 3, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(27, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(28, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(29, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(30, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(31, 0, 8, 8, kalendarzmiesiac));
    }
    
    public static void reset(Kalendarzmiesiac kalendarzmiesiac) {
            kalendarzmiesiac.setDzienList(new ArrayList<>());
            kalendarzmiesiac.getDzienList().add(new Dzien(1, 0, 8, 8, 2, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(2, 0, 8, 8, 2, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(3, 0, 8, 8, 2, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(4, 0, 8, 8, 2, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(5, 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(6, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(7, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(8, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(9, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(10, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(11, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(12, 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(13, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(14, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(15, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(16, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(17, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(18, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(19, 1, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(20, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(21, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(22, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(23, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(24, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(25, 3, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(26, 3, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(27, 2, 0, 0, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(28, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(29, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(30, 0, 8, 8, kalendarzmiesiac));
            kalendarzmiesiac.getDzienList().add(new Dzien(31, 0, 8, 8, kalendarzmiesiac));
    }

    static void dodajnieobecnosc(Kalendarzmiesiac kalendarz, Nieobecnosc nieobecnosc, Pasekwynagrodzen pasekwynagrodzen) {
        int dzienod = Integer.parseInt(Data.getDzien(nieobecnosc.getDataod()));
        int dziendo = Integer.parseInt(Data.getDzien(nieobecnosc.getDatado()));
        for (int i = dzienod;i<dziendo+1;i++) {
            for (Dzien p : kalendarz.getDzienList()) {
                if (p.getNrdnia()==i) {
                    p.setKod(nieobecnosc.getNieobecnosckodzus().getKod());
                }
            }
        }
        if (nieobecnosc.getNieobecnosckodzus().getKod().equals("331")) {
            naliczskladnikiwynagrodzeniazaChorobe(kalendarz, nieobecnosc, pasekwynagrodzen);
        } else if (nieobecnosc.getNieobecnosckodzus().getKod().equals("100")) {
            naliczskladnikiwynagrodzeniazaUrlop(kalendarz, nieobecnosc, pasekwynagrodzen);
        } else if (nieobecnosc.getNieobecnosckodzus().getKod().equals("111")) {
            naliczskladnikiwynagrodzeniazaOkresnieprzepracowany(kalendarz, nieobecnosc, pasekwynagrodzen,"111");
        }
    }
    
    static void dodajnieobecnoscDB(Kalendarzmiesiac kalendarz, Nieobecnosc nieobecnosc, Pasekwynagrodzen pasekwynagrodzen) {
        if (nieobecnosc!=null) {
            int dzienod = Integer.parseInt(Data.getDzien(nieobecnosc.getDataod()));
            int dziendo = Integer.parseInt(Data.getDzien(nieobecnosc.getDatado()));
            for (int i = dzienod;i<dziendo+1;i++) {
                for (Dzien p : kalendarz.getDzienList()) {
                    if (p.getNrdnia()==i) {
                        p.setKod(nieobecnosc.getNieobecnosckodzus().getKod());
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
    }

    static void naliczskladnikiwynagrodzenia(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen) {
        for (Skladnikwynagrodzenia p : kalendarz.getUmowa().getSkladnikwynagrodzeniaList()) {
            if (p.getRodzajwynagrodzenia().getKod().equals("11")) {
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createWynagrodzenie();
                pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
            } else if (p.getRodzajwynagrodzenia().getKod().equals("20")) {
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createPremia();
                pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
            }
        }
    }
    
    static void naliczskladnikiwynagrodzeniaDB(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen) {
        for (Skladnikwynagrodzenia p : kalendarz.getUmowa().getSkladnikwynagrodzeniaList()) {
            if (p.getRodzajwynagrodzenia().getKod().equals("11")) {
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createWynagrodzenieDB(pasekwynagrodzen, p);
                if (naliczenieskladnikawynagrodzenia.getKwota()!=0.0) {
                    pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
                }
            } else if (p.getRodzajwynagrodzenia().getKod().equals("20")) {
                Naliczenieskladnikawynagrodzenia naliczenieskladnikawynagrodzenia = NaliczenieskladnikawynagrodzeniaBean.createPremiaDB(pasekwynagrodzen, p);
                if (naliczenieskladnikawynagrodzenia.getKwota()!=0.0) {
                    pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
                }
            }
        }
    }

    static void naliczskladnikipotracenia(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen) {
        for (Skladnikpotracenia p : kalendarz.getUmowa().getSkladnikpotraceniaList()) {
            Naliczeniepotracenie naliczeniepotracenie = NaliczeniepotracenieBean.create();
            pasekwynagrodzen.getNaliczeniepotracenieList().add(naliczeniepotracenie);
        }
    }

    static void naliczskladnikiwynagrodzeniazaChorobe(Kalendarzmiesiac kalendarz, Nieobecnosc nieobecnosc, Pasekwynagrodzen pasekwynagrodzen) {
        for (Naliczenieskladnikawynagrodzenia p : pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList()) {
            if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("11")) {
                Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
                Skladnikwynagrodzenia skladnikwynagrodzenia = p.getSkladnikwynagrodzenia();
                naliczenienieobecnosc.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                naliczenienieobecnosc.setNieobecnosc(nieobecnosc);
                double skladnik = 0.0;
                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("11")) {
                    skladnik = skladnikwynagrodzenia.getZmiennawynagrodzeniaList().get(0).getKwota();
                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("20")) {
                    skladnik = Z.z(1800/3.0);
                }
                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("30")||p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("31")) {
                    skladnik = Z.z(210/3.0);
                }
                naliczenienieobecnosc.setJakiskladnikredukowalny(p.getSkladnikwynagrodzenia().getUwagi());               }
                int dninieobecnosci = Data.iletodni(nieobecnosc.getDatado(), nieobecnosc.getDataod());
                double skladnikistalenetto = skladnik-(skladnik*.1371);
                double skladnikistaledoredukcji = skladnik;
                naliczenienieobecnosc.setSkladnikistale(skladnikistalenetto);
                double procentzazwolnienie = 0.8;
                naliczenienieobecnosc.setProcentzazwolnienie(procentzazwolnienie);
                double stawkadzienna = Z.z(skladnikistalenetto/30)*procentzazwolnienie;
                naliczenienieobecnosc.setStawkadzienna(stawkadzienna);
                double dowyplatyzaczasnieobecnosci = Z.z(stawkadzienna*dninieobecnosci);
                naliczenienieobecnosc.setKwota(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setKwotabezzus(dowyplatyzaczasnieobecnosci);
                double stawkadziennaredukcji = Z.z(skladnikistaledoredukcji/30);
                naliczenienieobecnosc.setStawkadziennaredukcji(stawkadziennaredukcji);
                double kwotaredukcji = Z.z(stawkadziennaredukcji*dninieobecnosci);
                naliczenienieobecnosc.setKwotaredukcji(kwotaredukcji);
                naliczenienieobecnosc.setPasekwynagrodzen(pasekwynagrodzen);
                pasekwynagrodzen.getNaliczenienieobecnoscList().add(naliczenienieobecnosc);
            }
        }
    }

    static void naliczskladnikiwynagrodzeniazaUrlop(Kalendarzmiesiac kalendarz, Nieobecnosc nieobecnosc, Pasekwynagrodzen pasekwynagrodzen) {
        double dniroboczewmiesiacu = 0.0;
        double dninieobecnoscirobocze = 0.0;
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia()==0) {
                dniroboczewmiesiacu++;
            }
            if (p.getTypdnia()==0 && p.getKod()!=null && p.getKod().equals("100")) {
                dninieobecnoscirobocze++;
            }
        }
        for (Naliczenieskladnikawynagrodzenia p : pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList()) {
                Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
                naliczenienieobecnosc.setNieobecnosc(nieobecnosc);
                Skladnikwynagrodzenia skladnikwynagrodzenia = p.getSkladnikwynagrodzenia();
                naliczenienieobecnosc.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                double skladnik = 0.0;
                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("11")) {
                    skladnik = skladnikwynagrodzenia.getZmiennawynagrodzeniaList().get(0).getKwota();
                }
                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("20")) {
                    skladnik = Z.z(1800/3.0);
                    dniroboczewmiesiacu = 64.0/3.0;
                }
                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("30")||p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("31")) {
                    skladnik = Z.z(210/3.0);
                }
                naliczenienieobecnosc.setJakiskladnikredukowalny(p.getSkladnikwynagrodzenia().getUwagi()); 
                naliczenienieobecnosc.setSkladnikistale(skladnik);
                double liczbagodzinroboczych = dniroboczewmiesiacu * 8.0;
                naliczenienieobecnosc.setLiczbagodzinroboczych(liczbagodzinroboczych);
                double liczbagodzinurlopu = dninieobecnoscirobocze * 8.0;
                naliczenienieobecnosc.setLiczbagodzinurlopu(liczbagodzinurlopu);
                double stawkadzienna = skladnik / liczbagodzinroboczych;
                naliczenienieobecnosc.setStawkadzienna(stawkadzienna);
                double dowyplatyzaczasnieobecnosci = Z.z(stawkadzienna * liczbagodzinurlopu);
                naliczenienieobecnosc.setKwota(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setKwotazus(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setKwotaredukcji(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setPasekwynagrodzen(pasekwynagrodzen);
                pasekwynagrodzen.getNaliczenienieobecnoscList().add(naliczenienieobecnosc);
        }
    }
    
    static void naliczskladnikiwynagrodzeniazaOkresnieprzepracowany(Kalendarzmiesiac kalendarz, Nieobecnosc nieobecnosc, Pasekwynagrodzen pasekwynagrodzen, String kod) {
        double dniroboczewmiesiacu = 0;
        double dninieobecnoscirobocze = 0;
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getTypdnia()==0) {
                dniroboczewmiesiacu++;
            }
            if (p.getTypdnia()==0 && p.getKod()!=null && p.getKod().equals(kod)) {
                dninieobecnoscirobocze++;
            }
        }
        for (Naliczenieskladnikawynagrodzenia p : pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList()) {
            if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany() && p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getStale0zmienne1()==false) {
                Naliczenienieobecnosc naliczenienieobecnosc = new Naliczenienieobecnosc();
                Skladnikwynagrodzenia skladnikwynagrodzenia = p.getSkladnikwynagrodzenia();
                naliczenienieobecnosc.setNieobecnosc(nieobecnosc);
                naliczenienieobecnosc.setSkladnikwynagrodzenia(skladnikwynagrodzenia);
                double skladnikistale = skladnikwynagrodzenia.getZmiennawynagrodzeniaList().get(0).getKwota();
                naliczenienieobecnosc.setSkladnikistale(skladnikistale);
                double liczbagodzinroboczych = dniroboczewmiesiacu * 8.0;
                naliczenienieobecnosc.setLiczbagodzinroboczych(liczbagodzinroboczych);
                double liczbagodzinurlopu = dninieobecnoscirobocze * 8.0;
                naliczenienieobecnosc.setLiczbagodzinurlopu(liczbagodzinurlopu);
                double stawkadzienna = Z.z4(skladnikistale / liczbagodzinroboczych);
                naliczenienieobecnosc.setStawkadzienna(Z.z(stawkadzienna));
                double dowyplatyzaczasnieobecnosci = Z.z(stawkadzienna * liczbagodzinurlopu);
                naliczenienieobecnosc.setKwota(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setKwotazus(dowyplatyzaczasnieobecnosci);
                naliczenienieobecnosc.setKwotastatystyczna(naliczenienieobecnosc.getKwota());
                naliczenienieobecnosc.setKwotazus(0.0);
                naliczenienieobecnosc.setJakiskladnikredukowalny(p.getSkladnikwynagrodzenia().getUwagi()); 
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
        naliczenieskladnikawynagrodzenia.setKwota(Z.z(stawkagodznowanormalna*nadliczbowe));
        naliczenieskladnikawynagrodzenia.setKwotazus(Z.z(stawkagodznowanormalna*nadliczbowe));
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
            Skladnikwynagrodzenia skladniknadgodziny50 = pobierzskladnik(kalendarz, "30");
            naliczenieskladnikawynagrodzenia.setSkladnikwynagrodzenia(skladniknadgodziny50);
            double skladnik = wynagrodzeniezasadnicze.getZmiennawynagrodzeniaList().get(0).getKwota();
            double stawkagodznowanormalna = skladnik / godzinyrobocze*0.5;
            naliczenieskladnikawynagrodzenia.setKwota(Z.z(stawkagodznowanormalna*nadliczbowe));
            naliczenieskladnikawynagrodzenia.setKwotazus(Z.z(stawkagodznowanormalna*nadliczbowe));
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
        naliczenieskladnikawynagrodzenia.setKwota(Z.z(stawkagodznowanormalna*nadliczbowe));
        naliczenieskladnikawynagrodzenia.setKwotazus(Z.z(stawkagodznowanormalna*nadliczbowe));
        pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList().add(naliczenieskladnikawynagrodzenia);
    }

    static void redukujskladnikistale(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasekwynagrodzen) {
        if (pasekwynagrodzen.getNaliczenienieobecnoscList()!=null&&pasekwynagrodzen.getNaliczenienieobecnoscList().size()>0) {
            double dowyplatyzaczaschoroby = 0.0;
            double dowyplatyzaczasurlopu = 0.0;
            double dowyplatyzaczasurlopubezplatnego = 0.0;
            for (Naliczenienieobecnosc p : pasekwynagrodzen.getNaliczenienieobecnoscList()) {
                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany()) {
                    switch (p.getNieobecnosc().getNieobecnosckodzus().getKod()) {
                        case "331":
                            dowyplatyzaczaschoroby = dowyplatyzaczaschoroby+p.getKwotaredukcji();
                            break;
                        case "100":
                            dowyplatyzaczasurlopu = dowyplatyzaczasurlopu+p.getKwotaredukcji();
                        case "111":
                            dowyplatyzaczasurlopubezplatnego = dowyplatyzaczasurlopubezplatnego+p.getKwotastatystyczna();
                        case "200":
                            dowyplatyzaczasurlopubezplatnego = dowyplatyzaczasurlopubezplatnego+p.getKwotastatystyczna();

                    }
                }
            }
            for (Naliczenieskladnikawynagrodzenia p : pasekwynagrodzen.getNaliczenieskladnikawynagrodzeniaList()) {
                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany()) {
                    p.setKwotazredukowana(p.getKwota()-dowyplatyzaczaschoroby-dowyplatyzaczasurlopu-dowyplatyzaczasurlopubezplatnego);
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

    
    
}
