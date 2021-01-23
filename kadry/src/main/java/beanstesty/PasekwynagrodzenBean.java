/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import dao.NieobecnosckodzusFacade;
import dao.PasekwynagrodzenFacade;
import data.Data;
import entity.Angaz;
import entity.Definicjalistaplac;
import entity.Dzien;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Naliczenienieobecnosc;
import entity.Naliczeniepotracenie;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnosc;
import entity.Nieobecnosckodzus;
import entity.Pasekwynagrodzen;
import entity.Pracownik;
import entity.Umowa;
import error.E;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import z.Z;

/**
 *
 * @author Osito
 */
public class PasekwynagrodzenBean {
    
    public static Pasekwynagrodzen pasekwynagrodzen;
    
    public static Pasekwynagrodzen create() {
        if (pasekwynagrodzen==null) {
            pasekwynagrodzen = new Pasekwynagrodzen();
            pasekwynagrodzen.setDefinicjalistaplac(DefinicjalistaplacBean.create());
            pasekwynagrodzen.setKalendarzmiesiac(KalendarzmiesiacBean.create());
            pasekwynagrodzen.setNaliczenienieobecnoscList(new ArrayList<>());
            pasekwynagrodzen.setNaliczeniepotracenieList(new ArrayList<>());
            pasekwynagrodzen.setNaliczenieskladnikawynagrodzeniaList(new ArrayList<>());
        }
        return pasekwynagrodzen;
    }
    
      
    public static Pasekwynagrodzen oblicz(Kalendarzmiesiac kalendarz, Definicjalistaplac definicjalistaplac, NieobecnosckodzusFacade nieobecnosckodzusFacade) {
        Pasekwynagrodzen pasek = new Pasekwynagrodzen();
        double kurs = 4.4745;
        double dietastawka = 49.0;
        double limitZUS = 5227.0;
        pasek.setDefinicjalistaplac(definicjalistaplac);
        pasek.setKalendarzmiesiac(kalendarz);
        List<Nieobecnosc> nieobecnosci = pobierznieobecnosci(kalendarz);
        Nieobecnosc zatrudnieniewtrakciemiesiaca = generuj(kalendarz.getUmowa(),nieobecnosckodzusFacade, kalendarz.getRok(), kalendarz.getMc());
        Nieobecnosc choroba = pobierz(nieobecnosci,"331");
        Nieobecnosc urlop = pobierz(nieobecnosci,"100");
        Nieobecnosc urlopbezplatny = pobierz(nieobecnosci,"111");
        Nieobecnosc oddelegowanie = pobierz(nieobecnosci,"777");
        boolean jestoddelegowanie = KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDB(kalendarz, pasek, kurs);
        KalendarzmiesiacBean.nalicznadgodziny50DB(kalendarz, pasek);
        //KalendarzmiesiacBean.nalicznadgodziny100(kalendarz, pasek);
        //najpierw musimy przyporzadkowac aktualne skladniki, aby potem prawidlowo obliczyc redukcje
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, choroba, pasek);
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, urlop, pasek);
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, urlopbezplatny, pasek);
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, zatrudnieniewtrakciemiesiaca, pasek);
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, oddelegowanie, pasek);
        KalendarzmiesiacBean.redukujskladnikistale(kalendarz, pasek);
        if (jestoddelegowanie) {
            PasekwynagrodzenBean.naniesdietekurslimit(pasek, dietastawka, kurs, limitZUS);
            PasekwynagrodzenBean.wyliczlimitZUS(kalendarz, pasek, kurs, dietastawka, limitZUS);
        }
//        KalendarzmiesiacBean.naliczskladnikipotracenia(kalendarz, pasek);
        PasekwynagrodzenBean.obliczbruttozus(pasek);
        PasekwynagrodzenBean.obliczbruttobezzus(pasek);
        PasekwynagrodzenBean.pracownikemerytalna(pasek);
        PasekwynagrodzenBean.pracownikrentowa(pasek);
        PasekwynagrodzenBean.pracownikchorobowa(pasek);
        PasekwynagrodzenBean.razemspolecznepracownik(pasek);
        if (jestoddelegowanie) {
            PasekwynagrodzenBean.obliczdietedoodliczenia(pasek, kalendarz);
        }
        PasekwynagrodzenBean.obliczpodstaweopodatkowania(pasek);
        PasekwynagrodzenBean.obliczpodatekwstepny(pasek);
        PasekwynagrodzenBean.ulgapodatkowa(pasek);
        PasekwynagrodzenBean.naliczzdrowota(pasek);
        PasekwynagrodzenBean.obliczpodatekdowplaty(pasek);
        PasekwynagrodzenBean.potracenia(pasek);
        PasekwynagrodzenBean.dowyplaty(pasek);
        PasekwynagrodzenBean.emerytalna(pasek);
        PasekwynagrodzenBean.rentowa(pasek);
        PasekwynagrodzenBean.wypadkowa(pasek);
        PasekwynagrodzenBean.razemspolecznefirma(pasek);
        PasekwynagrodzenBean.fp(pasek);
        PasekwynagrodzenBean.fgsp(pasek);
        PasekwynagrodzenBean.razem53(pasek);
        PasekwynagrodzenBean.razemkosztpracodawcy(pasek);
        System.out.println("****************");
        for (Naliczenieskladnikawynagrodzenia r : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            if (r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany()) {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" "+Z.z(r.getKwotazredukowana()));
            } else {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" "+Z.z(r.getKwota()));
            }
        }
        for (Naliczenienieobecnosc r : pasek.getNaliczenienieobecnoscList()) {
            System.out.println(r.getNieobecnosc().getNieobecnosckodzus().getOpisskrocony()+" od "+r.getSkladnikwynagrodzenia().getUwagi()+" "+Z.z(r.getKwota()));
            if (r.getKwotaredukcji()!=0.0 && r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany()) {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" redukcja za "+r.getNieobecnosc().getNieobecnosckodzus().getOpisskrocony()+" kwota redukcji "+Z.z(r.getKwotaredukcji()));
            }
        }
        System.out.println("****************");
        System.out.println(pasek.getBruttozus());
        System.out.println(pasek.getBruttobezzus());
        double suma = pasek.getBruttozus()+pasek.getBruttobezzus();
        System.out.println("Razem: "+Z.z(suma));
        System.out.println(pasek.getNetto());
        System.out.println("");
        return pasek;
    }
    
    
     public static void main (String[] args) {
        Kalendarzwzor kalendarzwzor = KalendarzWzorBean.create();
        Kalendarzmiesiac kalendarz = KalendarzmiesiacBean.create();
        Nieobecnosc choroba = NieobecnosciBean.createChoroba();
        Nieobecnosc urlop = NieobecnosciBean.createUrlop();
        Nieobecnosc urlopbezplatny = NieobecnosciBean.createUrlopBezplatny();
        Pasekwynagrodzen pasek = create();
        pasek.setKalendarzmiesiac(kalendarz);
        kalendarz.getPasekwynagrodzenList().add(pasek);
        KalendarzmiesiacBean.naliczskladnikiwynagrodzenia(kalendarz, pasek);
        KalendarzmiesiacBean.nalicznadgodziny50(kalendarz, pasek);
        //KalendarzmiesiacBean.nalicznadgodziny100(kalendarz);
        //najpierw musimy przyporzadkowac aktualne skladniki, aby potem prawidlowo obliczyc redukcje
        KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, choroba, pasek);
        KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, urlop, pasek);
        KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, urlopbezplatny, pasek);
        KalendarzmiesiacBean.redukujskladnikistale(kalendarz, pasek);
        KalendarzmiesiacBean.naliczskladnikipotracenia(kalendarz, pasek);
        Definicjalistaplac definicjalistaplac = DefinicjalistaplacBean.create();
        
        pasek.setDefinicjalistaplac(definicjalistaplac);
        PasekwynagrodzenBean.obliczbruttozus(pasek);
        PasekwynagrodzenBean.obliczbruttobezzus(pasek);
        PasekwynagrodzenBean.pracownikemerytalna(pasek);
        PasekwynagrodzenBean.pracownikrentowa(pasek);
        PasekwynagrodzenBean.pracownikchorobowa(pasek);
        PasekwynagrodzenBean.razemspolecznepracownik(pasek);
        PasekwynagrodzenBean.obliczpodstaweopodatkowania(pasek);
        PasekwynagrodzenBean.obliczpodatekwstepny(pasek);
        PasekwynagrodzenBean.ulgapodatkowa(pasek);
        PasekwynagrodzenBean.naliczzdrowota(pasek);
        PasekwynagrodzenBean.obliczpodatekdowplaty(pasek);
        PasekwynagrodzenBean.potracenia(pasek);
        PasekwynagrodzenBean.dowyplaty(pasek);
        System.out.println("****************");
        for (Naliczenieskladnikawynagrodzenia r : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            if (r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany()) {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" "+Z.z(r.getKwotazredukowana()));
            } else {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" "+Z.z(r.getKwota()));
            }
        }
        for (Naliczenienieobecnosc r : pasek.getNaliczenienieobecnoscList()) {
            System.out.println(r.getNieobecnosc().getNieobecnosckodzus().getOpisskrocony()+" od "+r.getSkladnikwynagrodzenia().getUwagi()+" "+Z.z(r.getKwota()));
            if (r.getKwotaredukcji()!=0.0 && r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany()) {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" redukcja za "+r.getNieobecnosc().getNieobecnosckodzus().getOpisskrocony()+" kwota redukcji "+Z.z(r.getKwotaredukcji()));
            }
        }
        System.out.println("****************");
        System.out.println(pasek.getBruttozus());
        System.out.println(pasek.getBruttobezzus());
        double suma = pasek.getBruttozus()+pasek.getBruttobezzus();
        System.out.println("Razem: "+Z.z(suma));
        System.out.println(pasek.getNetto());
        System.out.println("");
        //PdfListaPlac.drukuj(pasek);
    }

    private static void obliczbruttozus(Pasekwynagrodzen pasek) {
        double bruttozus = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getRedukowany()) {
                bruttozus = Z.z(bruttozus+p.getKwotazredukowana());
            } else {
                bruttozus = Z.z(bruttozus+p.getKwotazus());
            }
        }
        for (Naliczenienieobecnosc p : pasek.getNaliczenienieobecnoscList()) {
            bruttozus = Z.z(bruttozus+p.getKwotazus());
        }
        pasek.setBruttozus(bruttozus);
    }

    private static void obliczbruttobezzus(Pasekwynagrodzen pasek) {
        double bruttobezzus = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            bruttobezzus = Z.z(bruttobezzus+p.getKwotabezzus());
        }
        for (Naliczenienieobecnosc p : pasek.getNaliczenienieobecnoscList()) {
            bruttobezzus = Z.z(bruttobezzus+p.getKwotabezzus());
        }
        pasek.setBruttobezzus(bruttobezzus);
    }

    private static void pracownikemerytalna(Pasekwynagrodzen pasek) {
        pasek.setPracemerytalne(Z.z(pasek.getBruttozus()*0.0976));
    }
    
    private static void emerytalna(Pasekwynagrodzen pasek) {
        pasek.setEmerytalne(Z.z(pasek.getBruttozus()*0.0976));
    }

    private static void pracownikrentowa(Pasekwynagrodzen pasek) {
        pasek.setPracrentowe(Z.z(pasek.getBruttozus()*0.015));
    }
    
    private static void rentowa(Pasekwynagrodzen pasek) {
        pasek.setRentowe(Z.z(pasek.getBruttozus()*0.065));
    }
    
    private static void wypadkowa(Pasekwynagrodzen pasek) {
        pasek.setWypadkowe(Z.z(pasek.getBruttozus()*0.0167));
    }
    
    private static void fp(Pasekwynagrodzen pasek) {
        pasek.setFp(Z.z(pasek.getBruttozus()*0.0245));
    }
    
    private static void fgsp(Pasekwynagrodzen pasek) {
        pasek.setFgsp(Z.z(pasek.getBruttozus()*0.001));
    }

    private static void pracownikchorobowa(Pasekwynagrodzen pasek) {
        boolean podlega = pasek.getKalendarzmiesiac().getUmowa().getChorobowe() || pasek.getKalendarzmiesiac().getUmowa().getChorobowedobrowolne();
        if (podlega) {
            pasek.setPracchorobowe(Z.z(pasek.getBruttozus()*0.0245));
        }
    }

    private static void razemspolecznepracownik(Pasekwynagrodzen pasek) {
        pasek.setRazemspolecznepracownik(Z.z(pasek.getPracemerytalne()+pasek.getPracrentowe()+pasek.getPracchorobowe()));
    }
    
    private static void razemspolecznefirma(Pasekwynagrodzen pasek) {
        pasek.setRazemspolecznefirma(Z.z(pasek.getEmerytalne()+pasek.getRentowe()+pasek.getWypadkowe()));
    }
    
    private static void razem53(Pasekwynagrodzen pasek) {
        pasek.setRazem53(Z.z(pasek.getFp()+pasek.getFgsp()));
    }

     private static void razemkosztpracodawcy(Pasekwynagrodzen pasek) {
         pasek.setKosztpracodawcy(Z.z(pasek.getRazemspolecznefirma()+pasek.getFgsp()+pasek.getFgsp()));
    }
    private static void obliczpodstaweopodatkowania(Pasekwynagrodzen pasek) {
        double zzus = pasek.getBruttozus();
        double bezzus = pasek.getBruttobezzus();
        double skladki = pasek.getRazemspolecznepracownik();
        double kosztyuzyskania = pasek.getKalendarzmiesiac().getUmowa().getKosztyuzyskania();
        double dieta30proc = pasek.getDietaodliczeniepodstawaop();
        double podstawa = Z.z0(zzus+bezzus-skladki-kosztyuzyskania-dieta30proc) > 0.0 ? Z.z0(zzus+bezzus-skladki-kosztyuzyskania-dieta30proc) :0.0;
        pasek.setPodstawaopodatkowania(podstawa);
        pasek.setKosztyuzyskania(kosztyuzyskania);
        
    }

    private static void obliczpodatekwstepny(Pasekwynagrodzen pasek) {
        double podatek = Z.z(Z.z0(pasek.getPodstawaopodatkowania())*0.17);
        pasek.setPodatekwstepny(podatek);
    }

    private static void ulgapodatkowa(Pasekwynagrodzen pasek) {
        boolean ulga = pasek.getKalendarzmiesiac().getUmowa().getOdliczaculgepodatkowa();
        if (ulga) {
            double kwotawolna = 43.76;
            pasek.setKwotawolna(kwotawolna);
        }
    }

    private static void naliczzdrowota(Pasekwynagrodzen pasek) {
        double zzus = pasek.getBruttozus();
        double skladki = pasek.getRazemspolecznepracownik();
        double podstawazdrowotna = Z.z(zzus-skladki) > 0.0 ? Z.z(zzus-skladki) :0.0;
        pasek.setPodstawaubezpzdrowotne(podstawazdrowotna);
        double zdrowotne = Z.z(podstawazdrowotna*0.09);
        pasek.setPraczdrowotne(zdrowotne);
        double zdrowotneodliczane = Z.z(podstawazdrowotna*0.0775);
        pasek.setPraczdrowotnedopotracenia(zdrowotneodliczane);
        //trzeba zrobic tez inne opcje
    }

    private static void obliczpodatekdowplaty(Pasekwynagrodzen pasek) {
        pasek.setPodatekdochodowy(Z.z0(pasek.getPodatekwstepny()-pasek.getPraczdrowotnedopotracenia()-pasek.getKwotawolna()));
    }

    private static void potracenia(Pasekwynagrodzen pasek) {
        double potracenia = 0.0;
        for (Naliczeniepotracenie p : pasek.getNaliczeniepotracenieList()) {
            potracenia = Z.z(potracenia+p.getKwota());
        }
        pasek.setPotracenia(potracenia);
    }

    private static void dowyplaty(Pasekwynagrodzen pasek) {
        pasek.setNetto(Z.z(pasek.getPodstawaubezpzdrowotne()+pasek.getBruttobezzus()-pasek.getPraczdrowotne()-pasek.getPodatekdochodowy()));
    }

    private static Nieobecnosc pobierz(List<Nieobecnosc> nieobecnosci, String string) {
        Nieobecnosc zwrot = null;
        for (Nieobecnosc p : nieobecnosci) {
            if (p.getNieobecnosckodzus().getKod().equals(string)) {
                zwrot = p;
                break;
            }
        }
        return zwrot;
    }

    public static void usunpasekjeslijest(Pasekwynagrodzen selected, PasekwynagrodzenFacade pasekwynagrodzenFacade) {
        Pasekwynagrodzen jesttaki = null;
        try {
            jesttaki = pasekwynagrodzenFacade.findByDefKal(selected.getDefinicjalistaplac(), selected.getKalendarzmiesiac());
            if (jesttaki!=null) {
                pasekwynagrodzenFacade.remove(jesttaki);
            }
        } catch (Exception e) {
            System.out.println(E.e(e));
        }
    }

    private static List<Nieobecnosc> pobierznieobecnosci(Kalendarzmiesiac kalendarz) {
        String rok = kalendarz.getRok();
        String mc = kalendarz.getMc();
        boolean jest = false;
        List<Nieobecnosc> zwrot = new ArrayList<>();
        for (Nieobecnosc p : kalendarz.getUmowa().getNieobecnoscList()) {
            jest = Data.czydatajestwmcu(p.getDataod(), rok, mc);
            jest = Data.czydatajestwmcu(p.getDatado(), rok, mc);
            if (jest) {
                zwrot.add(p);
            }
        }
        return zwrot;
    }

    private static Nieobecnosc generuj(Umowa umowa, NieobecnosckodzusFacade nieobecnosckodzusFacade, String rok, String mc) {
        Nieobecnosc zwrot =null;
        String rokumowa = Data.getRok(umowa.getDataod());
        String mcumowa = Data.getMc(umowa.getDataod());
        String dzienumowa = Data.getDzien(umowa.getDataod());
        if (rokumowa.equals(rok)&&mcumowa.equals(mc)&&!dzienumowa.equals("01")) {
            Nieobecnosckodzus nieobecnosckodzus = nieobecnosckodzusFacade.findByKod("200");
            zwrot = new Nieobecnosc();
            zwrot.setUmowa(umowa);
            zwrot.setNieobecnosckodzus(nieobecnosckodzus);
            String dataod = Data.pierwszyDzien(umowa.getDataod());
            LocalDate today = LocalDate.parse(umowa.getDataod());
            LocalDate yesterday = today.minusDays(1);  
            String datado = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            zwrot.setDataod(dataod);
            zwrot.setDatado(datado);
        }
        return zwrot;
    }

    public static Pasekwynagrodzen sumujpaski(List<Pasekwynagrodzen> lista) {
        Pasekwynagrodzen sumapasek = new Pasekwynagrodzen();
        sumapasek.setKalendarzmiesiac(new Kalendarzmiesiac());
        sumapasek.getKalendarzmiesiac().setUmowa(new Umowa());
        sumapasek.getKalendarzmiesiac().getUmowa().setAngaz(new Angaz());
        sumapasek.getKalendarzmiesiac().getUmowa().getAngaz().setPracownik(new Pracownik("podsumowanie"," "));
        for (Pasekwynagrodzen p : lista) {
            sumapasek.dodajPasek(p);
        }
        return sumapasek;
    }

    private static void wyliczlimitZUS(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasek, double kurs, double dieta, double limitZUS) {
        double dnioddelegowanie = 0.0;
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getKod().equals("777")) {
                dnioddelegowanie++;
            }
        }
        double dietypln = Z.z(dieta*dnioddelegowanie*kurs);
        double sumawynagrodzen = Z.z(PasekwynagrodzenBean.sumujwynagrodzenia(pasek.getNaliczenieskladnikawynagrodzeniaList()));
        double sumawynagrodzenbezdiet = sumawynagrodzen-dietypln >- 0.0 ? Z.z(sumawynagrodzen-dietypln) : 0.0;
        double kwotabezzus = 0.0;
        if (sumawynagrodzen>limitZUS) {
            if (sumawynagrodzenbezdiet<limitZUS) {
                kwotabezzus = Z.z(sumawynagrodzen-limitZUS);
            } else {
                kwotabezzus = Z.z(dietypln);
            }
        }
        PasekwynagrodzenBean.modyfikujwynagrodzenia(pasek.getNaliczenieskladnikawynagrodzeniaList(), kwotabezzus);
    }

    private static double sumujwynagrodzenia(List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList) {
        double suma = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : naliczenieskladnikawynagrodzeniaList) {
            //to trzeba bedzie zmienic!!!!! bo nie ma polksiego wyn
            if (p.getKwotazredukowana()!=0.0) {
                suma = suma +p.getKwotazredukowana();
            } else {
                suma = suma +p.getKwota();
            }
        }
        return suma;
    }

    private static void modyfikujwynagrodzenia(List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList, double kwotabezzzus) {
        for (Naliczenieskladnikawynagrodzenia p : naliczenieskladnikawynagrodzeniaList) {
            //to trzeba bedzie zmienic!!!!! bo nie ma polksiego wyn
            if (p.getSkladnikwynagrodzenia().isOddelegowanie()) {
                if (p.getKwota()>=kwotabezzzus) {
                    p.setKwotazus(Z.z(p.getKwota()-kwotabezzzus));
                    p.setKwotabezzus(kwotabezzzus);
                }
            }
        }
    }

    private static void obliczdietedoodliczenia(Pasekwynagrodzen pasek, Kalendarzmiesiac kalendarz) {
        double dnioddelegowanie = 0.0;
        for (Dzien p : kalendarz.getDzienList()) {
            if (p.getKod().equals("777")) {
                dnioddelegowanie++;
            }
        }
        double dietypln = Z.z(pasek.getDietastawka()*dnioddelegowanie*pasek.getKurs());
        dietypln = Z.z(dietypln*0.3);
        pasek.setDietaodliczeniepodstawaop(dietypln);
    }

    private static void naniesdietekurslimit(Pasekwynagrodzen pasek, double dietastawka, double kurs, double limitZUS) {
        pasek.setDietastawka(dietastawka);
        pasek.setKurs(kurs);
        pasek.setLimitzus(limitZUS);
    }

    
   
}
