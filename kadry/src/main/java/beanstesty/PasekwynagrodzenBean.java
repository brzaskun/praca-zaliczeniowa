/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import dao.PasekwynagrodzenFacade;
import data.Data;
import entity.Definicjalistaplac;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Naliczenienieobecnosc;
import entity.Naliczeniepotracenie;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnosc;
import entity.Pasekwynagrodzen;
import error.E;
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
    
      
    public static Pasekwynagrodzen oblicz(Kalendarzmiesiac kalendarz, Definicjalistaplac definicjalistaplac) {
        Pasekwynagrodzen pasek = new Pasekwynagrodzen();
        pasek.setDefinicjalistaplac(definicjalistaplac);
        pasek.setKalendarzmiesiac(kalendarz);
        List<Nieobecnosc> nieobecnosci = pobierznieobecnosci(kalendarz);
        Nieobecnosc choroba = pobierz(nieobecnosci,"331");
        Nieobecnosc urlop = pobierz(nieobecnosci,"001");
        Nieobecnosc urlopbezplatny = pobierz(nieobecnosci,"002");
        KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDB(kalendarz, pasek);
        KalendarzmiesiacBean.nalicznadgodziny50DB(kalendarz, pasek);
        //KalendarzmiesiacBean.nalicznadgodziny100(kalendarz, pasek);
        //najpierw musimy przyporzadkowac aktualne skladniki, aby potem prawidlowo obliczyc redukcje
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, choroba, pasek);
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, urlop, pasek);
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, urlopbezplatny, pasek);
        KalendarzmiesiacBean.redukujskladnikistale(kalendarz, pasek);
//        KalendarzmiesiacBean.naliczskladnikipotracenia(kalendarz, pasek);
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
            if (r.getSkladnikwynagrodzenia().getRedukowanyzaczasnieobecnosci()) {
                System.out.println(r.getSkladnikwynagrodzenia().getNazwa()+" "+Z.z(r.getKwotazredukowana()));
            } else {
                System.out.println(r.getSkladnikwynagrodzenia().getNazwa()+" "+Z.z(r.getKwota()));
            }
        }
        for (Naliczenienieobecnosc r : pasek.getNaliczenienieobecnoscList()) {
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
            if (r.getSkladnikwynagrodzenia().getRedukowanyzaczasnieobecnosci()) {
                System.out.println(r.getSkladnikwynagrodzenia().getNazwa()+" "+Z.z(r.getKwotazredukowana()));
            } else {
                System.out.println(r.getSkladnikwynagrodzenia().getNazwa()+" "+Z.z(r.getKwota()));
            }
        }
        for (Naliczenienieobecnosc r : pasek.getNaliczenienieobecnoscList()) {
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
        //PdfListaPlac.drukuj(pasek);
    }

    private static void obliczbruttozus(Pasekwynagrodzen pasek) {
        double bruttozus = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            if (p.getSkladnikwynagrodzenia().getRedukowanyzaczasnieobecnosci()) {
                bruttozus = Z.z(bruttozus+p.getKwotazredukowana());
            } else {
                bruttozus = Z.z(bruttozus+p.getKwota());
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
        pasek.setNetto(Z.z(pasek.getPodstawaubezpzdrowotne()-pasek.getPraczdrowotne()-pasek.getPodatekdochodowy()));
    }

    private static Nieobecnosc pobierz(List<Nieobecnosc> nieobecnosci, String string) {
        Nieobecnosc zwrot = null;
        for (Nieobecnosc p : nieobecnosci) {
            if (p.getKod().equals(string)) {
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
}
