/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entity.Definicjalistaplac;
import entity.KalendarzWzor;
import entity.Kalendarzmiesiac;
import entity.Naliczenienieobecnosc;
import entity.Naliczeniepotracenie;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnosc;
import entity.Pasekwynagrodzen;
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
        }
        return pasekwynagrodzen;
    }
    
     public static void main (String[] args) {
        KalendarzWzor kalendarzwzor = KalendarzWzorBean.create();
        Kalendarzmiesiac kalendarz = KalendarzmiesiacBean.create();
        Nieobecnosc choroba = NieobecnosciBean.createChoroba();
        Nieobecnosc urlop = NieobecnosciBean.createUrlop();
        Nieobecnosc urlopbezplatny = NieobecnosciBean.createUrlopBezplatny();
        KalendarzmiesiacBean.naliczskladnikiwynagrodzenia(kalendarz);
        KalendarzmiesiacBean.nalicznadgodziny50(kalendarz);
        //KalendarzmiesiacBean.nalicznadgodziny100(kalendarz);
        //najpierw musimy przyporzadkowac aktualne skladniki, aby potem prawidlowo obliczyc redukcje
        KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, choroba);
        KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, urlop);
        KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, urlopbezplatny);
        KalendarzmiesiacBean.redukujskladnikistale(kalendarz);
        KalendarzmiesiacBean.naliczskladnikipotracenia(kalendarz);
        Definicjalistaplac definicjalistaplac = DefinicjalistaplacBean.create();
        Pasekwynagrodzen pasek = create();
        pasek.setDefinicjalistaplac(definicjalistaplac);
        pasek.setKalendarzmiesiac(kalendarz);
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
        for (Naliczenieskladnikawynagrodzenia r : pasek.getKalendarzmiesiac().getNaliczenieskladnikawynagrodzeniaList()) {
            if (r.getSkladnikwynagrodzenia().getRedukowanyzaczasnieobecnosci()) {
                System.out.println(r.getSkladnikwynagrodzenia().getNazwa()+" "+Z.z(r.getKwotazredukowana()));
            } else {
                System.out.println(r.getSkladnikwynagrodzenia().getNazwa()+" "+Z.z(r.getKwota()));
            }
        }
        for (Naliczenienieobecnosc r : pasek.getKalendarzmiesiac().getNaliczenienieobecnoscList()) {
            System.out.println(r.getNieobecnosc().getNazwa()+" od "+r.getSkladnikwynagrodzenia().getNazwa()+" "+Z.z(r.getKwota()));
            if (r.getKwotaredukcji()!=0.0 && r.getSkladnikwynagrodzenia().getRedukowanyzaczasnieobecnosci()) {
                System.out.println(r.getSkladnikwynagrodzenia().getNazwa()+" redukcja za "+r.getNieobecnosc().getNazwa()+" kwota redukcji "+Z.z(r.getKwotaredukcji()));
            }
        }
        System.out.println("****************");
        System.out.println(pasek.getBruttozus());
        System.out.println(pasek.getBruttobezzus());
        double suma = pasek.getBruttozus()+pasek.getBruttobezzus();
        System.out.println("Razem: "+Z.z(suma));
        System.out.println(pasek.getNetto());
        System.out.println("");
    }

    private static void obliczbruttozus(Pasekwynagrodzen pasek) {
        double bruttozus = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : pasek.getKalendarzmiesiac().getNaliczenieskladnikawynagrodzeniaList()) {
            if (p.getSkladnikwynagrodzenia().getRedukowanyzaczasnieobecnosci()) {
                bruttozus = Z.z(bruttozus+p.getKwotazredukowana());
            } else {
                bruttozus = Z.z(bruttozus+p.getKwota());
            }
        }
        for (Naliczenienieobecnosc p : pasek.getKalendarzmiesiac().getNaliczenienieobecnoscList()) {
            bruttozus = Z.z(bruttozus+p.getKwotazus());
        }
        pasek.setBruttozus(bruttozus);
    }

    private static void obliczbruttobezzus(Pasekwynagrodzen pasek) {
        double bruttobezzus = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : pasek.getKalendarzmiesiac().getNaliczenieskladnikawynagrodzeniaList()) {
            bruttobezzus = Z.z(bruttobezzus+p.getKwotabezzus());
        }
        for (Naliczenienieobecnosc p : pasek.getKalendarzmiesiac().getNaliczenienieobecnoscList()) {
            bruttobezzus = Z.z(bruttobezzus+p.getKwotabezzus());
        }
        pasek.setBruttobezzus(bruttobezzus);
    }

    private static void pracownikemerytalna(Pasekwynagrodzen pasek) {
        pasek.setPracemerytalne(Z.z(pasek.getBruttozus()*0.0976));
    }

    private static void pracownikrentowa(Pasekwynagrodzen pasek) {
        pasek.setPracrentowe(Z.z(pasek.getBruttozus()*0.015));
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

    private static void obliczpodstaweopodatkowania(Pasekwynagrodzen pasek) {
        double zzus = pasek.getBruttozus();
        double bezzus = pasek.getBruttobezzus();
        double skladki = pasek.getRazemspolecznepracownik();
        double kosztyuzyskania = pasek.getKalendarzmiesiac().getUmowa().getKosztyuzyskania();
        double podstawa = Z.z0(zzus+bezzus-skladki-kosztyuzyskania) > 0.0 ? Z.z0(zzus+bezzus-skladki-kosztyuzyskania) :0.0;
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
        double bezzus = pasek.getBruttobezzus();
        double skladki = pasek.getRazemspolecznepracownik();
        double podstawazdrowotna = Z.z(zzus+bezzus-skladki) > 0.0 ? Z.z(zzus+bezzus-skladki) :0.0;
        pasek.setPodstawaubezpzdrowotne(podstawazdrowotna);
        double zdrowotne = Z.z(podstawazdrowotna*0.09);
        pasek.setPraczdrowotne(zdrowotne);
        double zdrowotneodliczane = Z.z(podstawazdrowotna*0.0775);
        pasek.setPraczdrowotnedodoliczenia(zdrowotneodliczane);
        //trzeba zrobic tez inne opcje
    }

    private static void obliczpodatekdowplaty(Pasekwynagrodzen pasek) {
        pasek.setPodatekdochodowy(Z.z0(pasek.getPodatekwstepny()-pasek.getPraczdrowotnedodoliczenia()-pasek.getKwotawolna()));
    }

    private static void potracenia(Pasekwynagrodzen pasek) {
        double potracenia = 0.0;
        for (Naliczeniepotracenie p : pasek.getKalendarzmiesiac().getNaliczeniepotracenieList()) {
            potracenia = Z.z(potracenia+p.getKwota());
        }
        pasek.setPotracenia(potracenia);
    }

    private static void dowyplaty(Pasekwynagrodzen pasek) {
        pasek.setNetto(Z.z(pasek.getPodstawaubezpzdrowotne()-pasek.getPraczdrowotne()-pasek.getPodatekdochodowy()));
    }
}
