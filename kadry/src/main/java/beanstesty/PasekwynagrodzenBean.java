/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import dao.PasekwynagrodzenFacade;
import dao.RodzajnieobecnosciFacade;
import dao.SwiadczeniekodzusFacade;
import data.Data;
import entity.Angaz;
import entity.Definicjalistaplac;
import entity.Dzien;
import entity.EtatPrac;
import entity.FirmaKadry;
import entity.Kalendarzmiesiac;
import entity.Kalendarzwzor;
import entity.Naliczenienieobecnosc;
import entity.Naliczeniepotracenie;
import entity.Naliczenieskladnikawynagrodzenia;
import entity.Nieobecnosc;
import entity.Pasekwynagrodzen;
import entity.Podatki;
import entity.Pracownik;
import entity.Rachunekdoumowyzlecenia;
import entity.Rodzajnieobecnosci;
import entity.Skladnikwynagrodzenia;
import entity.Umowa;
import entity.Wynagrodzeniahistoryczne;
import entity.Wynagrodzenieminimalne;
import entity.Wypadkowefirma;
import entity.Zmiennawynagrodzenia;
import error.E;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import msg.Msg;
import viewsuperplace.OsobaBean;
import z.Z;

/**
 *
 * @author Osito
 */
public class PasekwynagrodzenBean {

    public static Pasekwynagrodzen pasekwynagrodzen;

    public static Pasekwynagrodzen create() {
        if (pasekwynagrodzen == null) {
            pasekwynagrodzen = new Pasekwynagrodzen();
            pasekwynagrodzen.setDefinicjalistaplac(DefinicjalistaplacBean.create());
            pasekwynagrodzen.setKalendarzmiesiac(KalendarzmiesiacBean.create());
            pasekwynagrodzen.setNaliczenienieobecnoscList(new ArrayList<>());
            pasekwynagrodzen.setNaliczeniepotracenieList(new ArrayList<>());
            pasekwynagrodzen.setNaliczenieskladnikawynagrodzeniaList(new ArrayList<>());
        }
        return pasekwynagrodzen;
    }

    public static Pasekwynagrodzen obliczWynagrodzeniesymulacja(Kalendarzmiesiac kalendarz, List<Podatki> stawkipodatkowe, boolean zlecenie0praca1, double kwotabrutto) {
        boolean umowaoprace = zlecenie0praca1;
        Pasekwynagrodzen pasek = new Pasekwynagrodzen();
        String datawyplaty = Data.ostatniDzien(kalendarz.getRok(), kalendarz.getMc());
        pasek.setDatawyplaty(datawyplaty);
        pasek.setRok(kalendarz.getRok());
//        obliczwiekNaniesnapasek(kalendarz, pasek);
//        String datakonca26lat = OsobaBean.obliczdata26(kalendarz.getDataUrodzenia());
//        boolean po26roku = Data.czyjestpoTerminData(datakonca26lat, kalendarz.getRok(), kalendarz.getMc());
//        if (po26roku==false) {
//            pasek.setDo26lat(true);
//        } else {
//            pasek.setDo26lat(false);
//        }
        boolean po26roku = true;
        pasek.setNierezydent(false);
        pasek.setKalendarzmiesiac(kalendarz);
        boolean jestoddelegowanie = false;
        if (umowaoprace) {
            jestoddelegowanie = KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDBsymulacja(kalendarz, pasek, kwotabrutto);
        } else {
            jestoddelegowanie = KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDBZlecenieSymulacja(kalendarz, pasek, 1.0);
        }
//        KalendarzmiesiacBean.naliczskladnikipotraceniaDB(kalendarz, pasek);
        PasekwynagrodzenBean.obliczbruttoumowaoprace(pasek,0,0,0);
        PasekwynagrodzenBean.wyliczpodstaweZUS(pasek);
        //PasekwynagrodzenBean.obliczbruttobezSpolecznych(pasek);
        //PasekwynagrodzenBean.obliczbruttobezzus(pasek);
        //PasekwynagrodzenBean.obliczbruttobezzusbezpodatek(pasek);
        PasekwynagrodzenBean.pracownikemerytalna(pasek);
        PasekwynagrodzenBean.pracownikrentowa(pasek);
        PasekwynagrodzenBean.pracownikchorobowa(pasek);
        PasekwynagrodzenBean.razemspolecznepracownik(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczneDB(pasek);
        if (umowaoprace) {
            PasekwynagrodzenBean.obliczpodstaweopodatkowaniaDB(pasek, stawkipodatkowe, po26roku, kalendarz.getAngaz().isKosztyuzyskania0podwyzszone());
            PasekwynagrodzenBean.obliczpodatekwstepnyDBStandard(pasek, pasek.getPodstawaopodatkowania(), stawkipodatkowe, 0.0);
        } else {
            PasekwynagrodzenBean.obliczpodstaweopodatkowaniaZlecenieSymulacja(pasek, stawkipodatkowe, pasek.isNierezydent());
            PasekwynagrodzenBean.obliczpodatekwstepnyZlecenieDB(pasek, stawkipodatkowe, pasek.isNierezydent());
        }
        PasekwynagrodzenBean.ulgapodatkowaDB(pasek, stawkipodatkowe, true, 0.0);
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent(), true,"");
        PasekwynagrodzenBean.obliczpodatekdowplaty(pasek);
        PasekwynagrodzenBean.netto(pasek);
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
        PasekwynagrodzenBean.naniesrobocze(pasek, kalendarz);
        return pasek;
    }

    public static Pasekwynagrodzen obliczWynagrodzenie(Kalendarzmiesiac kalendarz, Definicjalistaplac definicjalistaplac, SwiadczeniekodzusFacade nieobecnosckodzusFacade, List<Pasekwynagrodzen> paskidowyliczeniapodstawy,
            List<Wynagrodzeniahistoryczne> historiawynagrodzen, List<Podatki> stawkipodatkowe, double sumapoprzednich, Wynagrodzenieminimalne wynagrodzenieminimalne, double odliczonajuzkwotawolna, double kurs, double limitZUS,
            String datawyplaty, List<Nieobecnosc> nieobecnosci, double limit26, List<Kalendarzmiesiac> kalendarzlista, Rachunekdoumowyzlecenia rachunekdoumowyzlecenia, double sumabruttopoprzednich, Kalendarzwzor kalendarzwzor, 
            Definicjalistaplac definicjadlazasilkow, double sumabruttoopodatkowanapoprzednich) {
        boolean umowaoprace = definicjalistaplac.getRodzajlistyplac().getTyp() == 1;
        boolean umowazlecenia = definicjalistaplac.getRodzajlistyplac().getTyp() == 2;
        boolean umowazlecenianierezydent = definicjalistaplac.getRodzajlistyplac().getTyp() == 2 && kalendarz.isNierezydent();
        boolean umowafunkcja = definicjalistaplac.getRodzajlistyplac().getTyp() == 3;
        boolean zasilekchorobowy = definicjalistaplac.getRodzajlistyplac().getTyp() == 4;
        boolean naleznosciosobzagranicznych = definicjalistaplac.getRodzajlistyplac().getTyp() == 5;
        Pasekwynagrodzen pasek = new Pasekwynagrodzen();
        if (kalendarz.getAngaz().getPrzekroczenierok()!=null&&kalendarz.getAngaz().getPrzekroczenierok().length()>=4) {
            pasek.setPrzekroczenieoddelegowanie(true);
        }
        pasek.setKalendarzmiesiac(kalendarz);
        pasek.setDatawyplaty(datawyplaty);
        Data.obliczwiekNaniesnapasek(kalendarz.getDataUrodzenia(), pasek);
        String datakonca26lat = OsobaBean.obliczdata26(kalendarz.getDataUrodzenia());
        boolean po26roku = Data.czyjestpoTerminData(datakonca26lat, datawyplaty);
        boolean student = kalendarz.getAngaz().isStudent();
        if (pasek.isPraca() == true && po26roku == false) {
            pasek.setDo26lat(true);
        } else if (pasek.isPraca() == false && po26roku == false) {
            pasek.setDo26lat(true);
        } else {
            pasek.setDo26lat(false);
        }
        boolean nierezydent = kalendarz.isNierezydent();
        pasek.setNierezydent(nierezydent);
        pasek.setWynagrodzenieminimalne(wynagrodzenieminimalne.getKwotabrutto());
        pasek.setDefinicjalistaplac(definicjalistaplac);
        pasek.setRok(definicjalistaplac.getRok());
        pasek.setMc(definicjalistaplac.getMc());
        pasek.setKurs(kurs);
        boolean jestoddelegowanie = kalendarz.getDnioddelegowania() > 0;
        for (Skladnikwynagrodzenia s : kalendarz.getAngaz().getSkladnikwynagrodzeniaList()) {
            if (s.isOddelegowanie()) {
                jestoddelegowanie = true;
            }
        }
        EtatPrac pobierzetat = pasek.getKalendarzmiesiac().getAngaz().pobierzetat(pasek.getDatawyplaty());
        double limitzasilekchorobowy = wynagrodzenieminimalne.getLimitswiadczenchorobowych();
        if (pobierzetat != null) {
            double limitzasilekchorobowyredukcja = Z.z(limitzasilekchorobowy * pobierzetat.getEtat1() / pobierzetat.getEtat2());
            wynagrodzenieminimalne.setLimitswiadczenchorobowych(limitzasilekchorobowyredukcja);
        }
        if (umowaoprace) {
            umowaopracewyliczenie(kalendarz, kalendarzwzor, pasek, kurs, definicjalistaplac, odliczonajuzkwotawolna, jestoddelegowanie, 
                    limitZUS, stawkipodatkowe, sumapoprzednich, !po26roku, nieobecnosci, limit26, kalendarzlista, wynagrodzenieminimalne, sumabruttopoprzednich, sumabruttoopodatkowanapoprzednich);
        } else if (umowazlecenia) {
            double zmiennawynagrodzeniakwota = rachunekdoumowyzlecenia.getKwota();
            double zmiennawynagrodzeniakwotaodelegowanie = rachunekdoumowyzlecenia.getKwotaoddelegowanie();
            double zmiennawynagrodzeniakwotaodelegowaniewaluta = rachunekdoumowyzlecenia.getKwotaoddelegowaniewaluta();
            double liczbagodzin = rachunekdoumowyzlecenia.getIloscgodzin() + rachunekdoumowyzlecenia.getIloscgodzinoddelegowanie();
            umowazleceniawyliczenie(kalendarz, pasek, kurs, definicjalistaplac, odliczonajuzkwotawolna, jestoddelegowanie, limitZUS, stawkipodatkowe, 
                    sumapoprzednich, zmiennawynagrodzeniakwota, liczbagodzin, rachunekdoumowyzlecenia, limit26, sumabruttopoprzednich, 
                    zmiennawynagrodzeniakwotaodelegowanie, nieobecnosci, zmiennawynagrodzeniakwotaodelegowaniewaluta, sumabruttoopodatkowanapoprzednich);
        } else if (umowazlecenianierezydent || naleznosciosobzagranicznych) {
            double zmiennawynagrodzeniakwota = rachunekdoumowyzlecenia.getKwota();
            double zmiennawynagrodzeniakwotaodelegowanie = rachunekdoumowyzlecenia.getKwotaoddelegowanie();
            double liczbagodzin = rachunekdoumowyzlecenia.getIloscgodzin() + rachunekdoumowyzlecenia.getIloscgodzinoddelegowanie();
            umowazleceniaNRwyliczenie(kalendarz, pasek, kurs, definicjalistaplac, odliczonajuzkwotawolna, jestoddelegowanie, limitZUS, stawkipodatkowe, sumapoprzednich, 
                    zmiennawynagrodzeniakwota, liczbagodzin, rachunekdoumowyzlecenia, limit26, sumabruttopoprzednich, zmiennawynagrodzeniakwotaodelegowanie, sumabruttoopodatkowanapoprzednich);
        } else if (umowafunkcja) {
            umowafunkcjawyliczenie(kalendarz, pasek, kurs, definicjalistaplac, odliczonajuzkwotawolna, jestoddelegowanie, limitZUS, stawkipodatkowe, sumapoprzednich);
        } else if (zasilekchorobowy) {
            zasilekchorobowywyliczenie(kalendarz, pasek, kurs, definicjalistaplac, odliczonajuzkwotawolna, jestoddelegowanie, limitZUS, stawkipodatkowe, sumapoprzednich, nieobecnosci, kalendarzlista, wynagrodzenieminimalne, kalendarzwzor, definicjadlazasilkow);
            //zasilekwypadkowywyliczenie(kalendarz, pasek, kurs, definicjalistaplac, czyodlicoznokwotewolna, jestoddelegowanie, limitZUS, stawkipodatkowe, sumapoprzednich, nieobecnosci, kalendarzlista, wynagrodzenieminimalne, kalendarzwzor, definicjadlazasilkow);
        }
        PasekwynagrodzenBean.obliczpodatekdowplaty(pasek);
        PasekwynagrodzenBean.netto(pasek);
        double wolneodzajeciabrutto = wynagrodzenieminimalne.getKwotabrutto();
        double wolneodzajecia = wynagrodzenieminimalne.getKwotanetto();
        if (pobierzetat != null) {
            wolneodzajeciabrutto = Z.z(wolneodzajeciabrutto * pobierzetat.getEtat1() / pobierzetat.getEtat2());
            wolneodzajecia = PasekwynagrodzenBean.obliczminimalna(kalendarz, definicjalistaplac, stawkipodatkowe, 0, wolneodzajeciabrutto, datawyplaty);
        }
        pasek.setWolneodzajecia(wolneodzajecia);
        KalendarzmiesiacBean.naliczskladnikipotraceniaDB(kalendarz, pasek, wolneodzajecia);
        PasekwynagrodzenBean.potracenia(pasek);
        PasekwynagrodzenBean.dowyplaty(pasek);
        PasekwynagrodzenBean.emerytalna(pasek);
        PasekwynagrodzenBean.rentowa(pasek);
        PasekwynagrodzenBean.wypadkowa(pasek);
        PasekwynagrodzenBean.razemspolecznefirma(pasek);
        boolean przekroczeniewieku = czyprzekroczonowiek(kalendarz, pasek.getDatawyplaty());
        boolean ponizejwynminimalnego = czyponizejminimalnego(pasek, wynagrodzenieminimalne.getKwotabrutto());
        boolean firmamatylkozlecenia = czysatylkozlecenia(kalendarz);
        boolean powrotzmacierzynskiego = czyjestpowrotzmacierzynskiego(kalendarz.getAngaz().getNieobecnoscList(), pasek.getDatawyplaty());
        boolean bezrobotnyskierowanieFP = czynienaliczacFPbezrobotny(kalendarz);
        if (bezrobotnyskierowanieFP==true) {
        } else if (przekroczeniewieku == true || powrotzmacierzynskiego ==true) {
            //obojetnei jaka forma prawna jak jest przekroczenie wieku to nie liczymy FP i FGSP
            pasek.setFpprzekroczeniewiek(przekroczeniewieku);
            pasek.setFppowrotmacierzynski(powrotzmacierzynskiego);
        } else if (ponizejwynminimalnego == true) {
            PasekwynagrodzenBean.fgsp(pasek);
        } else if (kalendarz.getAngaz().getFirma().isOsobafizyczna()&&firmamatylkozlecenia == true) {
            PasekwynagrodzenBean.fgsp(pasek);
        } else {
            PasekwynagrodzenBean.fp(pasek);
            PasekwynagrodzenBean.fgsp(pasek);
        }
        PasekwynagrodzenBean.razem53(pasek);
        PasekwynagrodzenBean.razemkosztpracodawcy(pasek);
        PasekwynagrodzenBean.naniesrobocze(pasek, kalendarz);
        PasekwynagrodzenBean.przelicznawalute(pasek);

//        System.out.println("****************");
//        for (Naliczenieskladnikawynagrodzenia r : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
//            if (r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany()) {
//                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" "+Z.z(r.getKwotazredukowana()));
//            } else {
//                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" "+Z.z(r.getKwota()));
//            }
//        }
//        for (Naliczenienieobecnosc r : pasek.getNaliczenienieobecnoscList()) {
//            System.out.println(r.getNieobecnosc().getNieobecnosckodzus().getOpisskrocony()+" od "+r.getSkladnikwynagrodzenia().getUwagi()+" "+Z.z(r.getKwota()));
//            if (r.getKwotaredukcji()!=0.0 && r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany()) {
//                System.out.println(r.getSkladnikwynagrodzenia().getUwagi()+" redukcja za "+r.getNieobecnosc().getNieobecnosckodzus().getOpisskrocony()+" kwota redukcji "+Z.z(r.getKwotaredukcji()));
//            }
//        }
//        System.out.println("****************");
//        System.out.println(pasek.getBruttozus());
//        System.out.println(pasek.getBruttobezzus());
//        double suma = pasek.getBruttozus()+pasek.getBruttobezzus();
//        System.out.println("Razem: "+Z.z(suma));
//        System.out.println(pasek.getNetto());
//        System.out.println("");
        return pasek;
    }
    
                    

                        

    private static void umowaopracewyliczenie(Kalendarzmiesiac kalendarz, Kalendarzwzor kalendarzglobalny, Pasekwynagrodzen pasek, double kurs, Definicjalistaplac definicjalistaplac,
            double odliczonajuzkwotawolna, boolean jestoddelegowanie, double limitZUS, List<Podatki> stawkipodatkowe, double sumapoprzednich, boolean nieodliczackup, List<Nieobecnosc> nieobecnoscilista,
            double limit26, List<Kalendarzmiesiac> kalendarzlista, Wynagrodzenieminimalne wynagrodzenieminimalne, double sumabruttopoprzednich, double sumabruttoopodatkowanapoprzednich) {
        boolean odliczaculgepodatkowa = kalendarz.getAngaz().isOdliczaculgepodatkowa();
        KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDB(kalendarz, pasek, kurs, wynagrodzenieminimalne.getKwotabrutto(), kalendarzglobalny);
        List<Nieobecnosc> nieobecnosci = pobierznieobecnosci(kalendarz, nieobecnoscilista);
        //List<Nieobecnosc> zatrudnieniewtrakciemiesiaca = pobierz(nieobecnosci, "D");
        List<Nieobecnosc> choroba = pobierz(nieobecnosci, "CH");
        //nie wiem po co on tu jest
        //List<Nieobecnosc> zasilekchorobowy = pobierz(nieobecnosci, "ZC");
        List<Nieobecnosc> urlop = pobierz(nieobecnosci, "U");
        List<Nieobecnosc> urlopsilawyzsza = pobierz(nieobecnosci, "NS");
        List<Nieobecnosc> urlopoddelegowanie = pobierz(nieobecnosci, "UD");
        List<Nieobecnosc> urlopmatkadziecko = pobierz(nieobecnosci, "MD");
        List<Nieobecnosc> urlopookolicznosciowy = pobierz(nieobecnosci, "O");
        List<Nieobecnosc> urlopojcowski = pobierz(nieobecnosci, "UO");
        List<Nieobecnosc> urloponazadanie = pobierz(nieobecnosci, "UZ");
        List<Nieobecnosc> urlopbezplatny = pobierz(nieobecnosci, "X");
        List<Nieobecnosc> nieobecnoscNN = pobierz(nieobecnosci, "NN");
        List<Nieobecnosc> nieobecnoscNP = pobierz(nieobecnosci, "NP");
        List<Nieobecnosc> opiekabaezplatna = pobierz(nieobecnosci, "UP");
        List<Nieobecnosc> oddelegowanie = pobierz(nieobecnosci, "Z");
        //piecdziesiatki
        KalendarzmiesiacBean.nalicznadgodzinyDB(kalendarz, pasek, false);
        //setki
        KalendarzmiesiacBean.nalicznadgodzinyDB(kalendarz, pasek, true);
        //nadgodziny oddelegowanie
        if (jestoddelegowanie) {
            KalendarzmiesiacBean.nalicznadgodzinyOddelegowanieDB(kalendarz, pasek);
        }
        //KalendarzmiesiacBean.nalicznadgodziny100(kalendarz, pasek);
        //najpierw musimy przyporzadkowac aktualne skladniki, aby potem prawidlowo obliczyc redukcje
        //KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, zatrudnieniewtrakciemiesiaca, pasek);
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, choroba, pasek, kalendarzlista, kurs, definicjalistaplac, null, wynagrodzenieminimalne.getLimitswiadczenchorobowych(), jestoddelegowanie, wynagrodzenieminimalne.getKwotabrutto());
        //nie wiem po co on tyu jest
        //KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, zasilekchorobowy, pasek, kalendarzlista, kurs, definicjalistaplac);
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, urlopbezplatny, pasek, kalendarzlista, kurs, definicjalistaplac, null, wynagrodzenieminimalne.getLimitswiadczenchorobowych(), jestoddelegowanie, wynagrodzenieminimalne.getKwotabrutto());
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, nieobecnoscNN, pasek, kalendarzlista, kurs, definicjalistaplac, null, wynagrodzenieminimalne.getLimitswiadczenchorobowych(), jestoddelegowanie, wynagrodzenieminimalne.getKwotabrutto());
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, opiekabaezplatna, pasek, kalendarzlista, kurs, definicjalistaplac, null, wynagrodzenieminimalne.getLimitswiadczenchorobowych(), jestoddelegowanie, wynagrodzenieminimalne.getKwotabrutto());
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, nieobecnoscNP, pasek, kalendarzlista, kurs, definicjalistaplac, null, wynagrodzenieminimalne.getLimitswiadczenchorobowych(), jestoddelegowanie, wynagrodzenieminimalne.getKwotabrutto());
        //KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, oddelegowanie, pasek);
        KalendarzmiesiacBean.redukujskladnikistale(kalendarz, pasek);
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, urlop, pasek, kalendarzlista, kurs, definicjalistaplac, null, wynagrodzenieminimalne.getLimitswiadczenchorobowych(), jestoddelegowanie, wynagrodzenieminimalne.getKwotabrutto());
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, urlopsilawyzsza, pasek, kalendarzlista, kurs, definicjalistaplac, null, wynagrodzenieminimalne.getLimitswiadczenchorobowych(), jestoddelegowanie, wynagrodzenieminimalne.getKwotabrutto());
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, urlopoddelegowanie, pasek, kalendarzlista, kurs, definicjalistaplac, null, wynagrodzenieminimalne.getLimitswiadczenchorobowych(), jestoddelegowanie, wynagrodzenieminimalne.getKwotabrutto());
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, urlopmatkadziecko, pasek, kalendarzlista, kurs, definicjalistaplac, null, wynagrodzenieminimalne.getLimitswiadczenchorobowych(), jestoddelegowanie, wynagrodzenieminimalne.getKwotabrutto());
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, urlopookolicznosciowy, pasek, kalendarzlista, kurs, definicjalistaplac, null, wynagrodzenieminimalne.getLimitswiadczenchorobowych(), jestoddelegowanie, wynagrodzenieminimalne.getKwotabrutto());
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, urloponazadanie, pasek, kalendarzlista, kurs, definicjalistaplac, null, wynagrodzenieminimalne.getLimitswiadczenchorobowych(), jestoddelegowanie, wynagrodzenieminimalne.getKwotabrutto());
        //to dodalem, zeby bylo widac godziny i dni i potracenie, chyba
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, oddelegowanie, pasek, kalendarzlista, kurs, definicjalistaplac, null, wynagrodzenieminimalne.getLimitswiadczenchorobowych(), jestoddelegowanie, wynagrodzenieminimalne.getKwotabrutto());
        //przywrocilem 21-02-2023 po tym jak zmienilem rozliczanie "D"
        //odkrecilem 26-02-2023 po przebudowie kompletnej wyliczania zasadniczego, redukcja jest juz na poczatku
        KalendarzmiesiacBean.redukujskladnikistale2(kalendarz, pasek);
        String umowakodzus = pasek.getKodZus();
        //to musi byc na dole bo inaczej nie sumuje wynagrodzenia za urlop, ktore wchodzi w ppk 29.11.2023
        Pasekpomocnik sumyprzychodow = sumujprzychodyzlisty(pasek);
        KalendarzmiesiacBean.naliczskladnikiPPKDB(kalendarz, pasek, kurs, wynagrodzenieminimalne.getKwotabrutto(), kalendarzglobalny, sumyprzychodow);
        //myaslalem ze to jest potrzebne 21-12-2023 ale potem przeredagowalem ponizek, byly niepotrzebe zaokraglania czastkowtycgh
        //PasekwynagrodzenBean.zaokraglijgrosze(pasek);
        if (definicjalistaplac.getRodzajlistyplac().getSymbol().equals("ZA")) {
            PasekwynagrodzenBean.obliczbruttobezzusZasilek(pasek);
        } else {
            PasekwynagrodzenBean.obliczbruttoumowaoprace(pasek, limit26, sumabruttopoprzednich, sumabruttoopodatkowanapoprzednich);
//            if (pasek.isDo26lat()) {
//                PasekwynagrodzenBean.obliczbrutto26(pasek, limit26, sumabruttopoprzednich);
//                PasekwynagrodzenBean.obliczbruttobezzus(pasek);
//                PasekwynagrodzenBean.obliczbruttobezzusbezpodatek(pasek);
//                PasekwynagrodzenBean.obliczbruttobezSpolecznych(pasek);
//            } else if (umowakodzus.equals("0511")) {
//                PasekwynagrodzenBean.obliczbruttoumowaopraceKod0511(pasek);
//                PasekwynagrodzenBean.obliczbruttobezzusKod0511(pasek);
//                PasekwynagrodzenBean.obliczbruttobezzusbezpodatek(pasek);
//                PasekwynagrodzenBean.obliczbruttobezSpolecznych(pasek);
//            } else {
//                PasekwynagrodzenBean.obliczbruttoumowaoprace(pasek);
//                PasekwynagrodzenBean.obliczbruttobezzus(pasek);
//                PasekwynagrodzenBean.obliczbruttobezzusbezpodatek(pasek);
//                PasekwynagrodzenBean.obliczbruttobezSpolecznych(pasek);
//            }
        }
        if (jestoddelegowanie) {
            if (jestoddelegowanie && kurs == 0.0) {
                Msg.msg("e", "Jest oddelegowanie, a brak kursu!");
            }
            pasek.setKurs(kurs);
            PasekwynagrodzenBean.obliczdietedoodliczenia(pasek, kalendarz);
            PasekwynagrodzenBean.wyliczpodstaweZUSlimit(kalendarz, pasek, kurs, limitZUS);
        } else {
            PasekwynagrodzenBean.wyliczpodstaweZUS(pasek);
        }
        if (pasek.isUlgadlaKlasySredniej() && kalendarz.getRokI() == 2022 && kalendarz.getMcI() < 7) {
            PasekwynagrodzenBean.uwzglednijulgeklasasrednia(pasek);
        }
        //ppk jest teraz wyzej, bo musi byc  doliczane do przekroczenia
        PasekwynagrodzenBean.pracownikemerytalna(pasek);
        PasekwynagrodzenBean.pracownikrentowa(pasek);
        PasekwynagrodzenBean.pracownikchorobowa(pasek);
        PasekwynagrodzenBean.razemspolecznepracownik(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczneDB(pasek);
        boolean jestaktywnaumowa = kalendarz.getAngaz().jestumowaAktywna(pasek.getRokwypl(), pasek.getMcwypl());
        nieodliczackup = !jestaktywnaumowa;
        if (pasek.isDo26lat()) {
            PasekwynagrodzenBean.obliczpodstaweopodatkowania26DB(pasek, stawkipodatkowe, nieodliczackup, kalendarz.getAngaz().isKosztyuzyskania0podwyzszone(), limit26);
            PasekwynagrodzenBean.obliczpodatekwstepnyDB26lat(pasek, stawkipodatkowe, sumapoprzednich, limit26);
        } else {
            PasekwynagrodzenBean.obliczpodstaweopodatkowaniaDB(pasek, stawkipodatkowe, nieodliczackup, kalendarz.getAngaz().isKosztyuzyskania0podwyzszone());
            PasekwynagrodzenBean.obliczpodatekwstepnyDBStandard(pasek, pasek.getPodstawaopodatkowania(), stawkipodatkowe, sumapoprzednich);
        }
        if (jestaktywnaumowa) {
            PasekwynagrodzenBean.ulgapodatkowaDB(pasek, stawkipodatkowe, false, odliczonajuzkwotawolna);
        }
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent(), true, umowakodzus);
    }

    private static void zasilekchorobowywyliczenie(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasek, double kurs, Definicjalistaplac definicjalistaplac, double odliczonajuzkwotawolna,
            boolean jestoddelegowanie, double limitZUS, List<Podatki> stawkipodatkowe, double sumapoprzednich, List<Nieobecnosc> nieobecnosci, List<Kalendarzmiesiac> kalendarzlista, Wynagrodzenieminimalne wynagrodzenieminimalne, Kalendarzwzor kalendarzwzor, Definicjalistaplac definicjadlazasilkow) {
        KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDB(kalendarz, pasek, kurs, wynagrodzenieminimalne.getKwotabrutto(), kalendarzwzor);
        boolean odliczaculgepodatkowa = kalendarz.getAngaz().isOdliczaculgepodatkowa();
        //List<Nieobecnosc> zatrudnieniewtrakciemiesiaca = pobierz(nieobecnosci, "D");
        //List<Nieobecnosc> choroba = pobierz(nieobecnosci, "CH");
        List<Nieobecnosc> zasilekchorobowy = pobierz(nieobecnosci, "ZC");
        zasilekchorobowy.addAll(pobierz(nieobecnosci, "W"));
        zasilekchorobowy.addAll(pobierz(nieobecnosci, "UO"));
        PasekwynagrodzenBean.wyliczpodstaweZUSlimit(kalendarz, pasek, kurs, limitZUS);
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, zasilekchorobowy, pasek, kalendarzlista, kurs, definicjalistaplac, definicjadlazasilkow, wynagrodzenieminimalne.getLimitswiadczenchorobowych(), jestoddelegowanie, wynagrodzenieminimalne.getKwotabrutto());
        //PasekwynagrodzenBean.obliczbruttoumowaoprace(pasek);
        PasekwynagrodzenBean.obliczbruttobezzusZasilek(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczneDB(pasek);
        PasekwynagrodzenBean.obliczpodstaweopodatkowaniaZasilekDB(pasek, stawkipodatkowe);
        PasekwynagrodzenBean.obliczpodatekwstepnyDBStandard(pasek, pasek.getPodstawaopodatkowania(), stawkipodatkowe, sumapoprzednich);
        PasekwynagrodzenBean.ulgapodatkowaDB(pasek, stawkipodatkowe, true, odliczonajuzkwotawolna);
        pasek.setNaliczenieskladnikawynagrodzeniaList(new ArrayList<>());
    }
    
    private static void zasilekwypadkowywyliczenie(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasek, double kurs, Definicjalistaplac definicjalistaplac, double odliczonajuzkwotawolna,
            boolean jestoddelegowanie, double limitZUS, List<Podatki> stawkipodatkowe, double sumapoprzednich, List<Nieobecnosc> nieobecnosci, List<Kalendarzmiesiac> kalendarzlista, Wynagrodzenieminimalne wynagrodzenieminimalne, Kalendarzwzor kalendarzwzor, Definicjalistaplac definicjadlazasilkow) {
        KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDB(kalendarz, pasek, kurs, wynagrodzenieminimalne.getKwotabrutto(), kalendarzwzor);
        boolean odliczaculgepodatkowa = kalendarz.getAngaz().isOdliczaculgepodatkowa();
        //List<Nieobecnosc> zatrudnieniewtrakciemiesiaca = pobierz(nieobecnosci, "D");
        //List<Nieobecnosc> choroba = pobierz(nieobecnosci, "CH");
        List<Nieobecnosc> zasilekchorobowy = pobierz(nieobecnosci, "W");
        KalendarzmiesiacBean.dodajnieobecnoscDB(kalendarz, zasilekchorobowy, pasek, kalendarzlista, kurs, definicjalistaplac, definicjadlazasilkow, wynagrodzenieminimalne.getLimitswiadczenchorobowych(), jestoddelegowanie, wynagrodzenieminimalne.getKwotabrutto());
        //PasekwynagrodzenBean.obliczbruttoumowaoprace(pasek);
        PasekwynagrodzenBean.obliczbruttobezzusZasilek(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczneDB(pasek);
        PasekwynagrodzenBean.obliczpodstaweopodatkowaniaZasilekDB(pasek, stawkipodatkowe);
        PasekwynagrodzenBean.obliczpodatekwstepnyDBStandard(pasek, pasek.getPodstawaopodatkowania(), stawkipodatkowe, sumapoprzednich);
        PasekwynagrodzenBean.ulgapodatkowaDB(pasek, stawkipodatkowe, true, odliczonajuzkwotawolna);
    }

    private static void umowazleceniawyliczenie(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasek, double kurs, Definicjalistaplac definicjalistaplac, double odliczonajuzkwotawolna,
            boolean jestoddelegowanie, double limitZUS, List<Podatki> stawkipodatkowe, double sumapoprzednich, double zmiennawynagrodzeniakwota, double liczbagodzin,
            Rachunekdoumowyzlecenia rachunekdoumowyzlecenia, double limit26, double sumabruttopoprzednich, double zmiennawynagrodzeniakwotaodelegowanie, 
            List<Nieobecnosc> nieobecnoscilista,double zmiennawynagrodzeniakwotaodelegowaniewaluta, double sumabruttoopodatkowanapoprzednich) {
        boolean odliczaculgepodatkowa = kalendarz.getAngaz().isOdliczaculgepodatkowa();
        KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDBZlecenie(kalendarz, pasek, kurs, zmiennawynagrodzeniakwota, liczbagodzin, zmiennawynagrodzeniakwotaodelegowanie, zmiennawynagrodzeniakwotaodelegowaniewaluta);
        List<Nieobecnosc> nieobecnosci = pobierznieobecnosci(kalendarz, nieobecnoscilista);
        List<Nieobecnosc> urlopoddelegowanie = pobierz(nieobecnosci, "UD");
           for (Nieobecnosc nieobecnosc : urlopoddelegowanie) {
                String kod = nieobecnosc.getKod();
                if (kod.equals("UD")) {
                    //urlop wypoczynowy
                    KalendarzmiesiacBean.naliczskladnikiwynagrodzeniazaUrlopOddelegowanie(kalendarz, nieobecnosc, pasek, kurs);
                }
           }
        if (rachunekdoumowyzlecenia.getUmowa().getUmowakodzus().isDzielo()) {
            PasekwynagrodzenBean.obliczbruttoumowadzielo(pasek, rachunekdoumowyzlecenia);
        } else {
            PasekwynagrodzenBean.obliczbruttoumowazlecenia(pasek, rachunekdoumowyzlecenia, limit26, sumabruttopoprzednich, sumabruttoopodatkowanapoprzednich);
        }
//        PasekwynagrodzenBean.obliczbruttoumowaoprace(pasek);
//        PasekwynagrodzenBean.obliczbruttobezzus(pasek);
//        PasekwynagrodzenBean.obliczbruttobezzusbezpodatek(pasek);
        if (jestoddelegowanie) {
            if (jestoddelegowanie && kurs == 0.0) {
                Msg.msg("e", "Jest oddelegowanie, a brak kursu!");
            }
            pasek.setKurs(kurs);
            //tego nie ma przy zleceniu
            if (jestoddelegowanie) {
                if (jestoddelegowanie && kurs == 0.0) {
                    Msg.msg("e", "Jest oddelegowanie, a brak kursu!");
                }
                pasek.setKurs(kurs);
                //to musi byc bo dieta jest zld zle cenia od zus, nie ma jej tylko od podatku
                PasekwynagrodzenBean.obliczdietedoodliczenia(pasek, kalendarz);
            }
            PasekwynagrodzenBean.wyliczpodstaweZUSlimit(kalendarz, pasek, kurs, limitZUS);
        } else {
            PasekwynagrodzenBean.wyliczpodstaweZUS(pasek);
        }
        if (pasek.isUlgadlaKlasySredniej() && kalendarz.getRokI() == 2022 && kalendarz.getMcI() < 7) {
            PasekwynagrodzenBean.uwzglednijulgeklasasrednia(pasek);
        }
        PasekwynagrodzenBean.pracownikemerytalna(pasek);
        PasekwynagrodzenBean.pracownikrentowa(pasek);
        PasekwynagrodzenBean.pracownikchorobowaZlecenie(pasek, rachunekdoumowyzlecenia);
        PasekwynagrodzenBean.razemspolecznepracownik(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczneDB(pasek);
//        if (rachunekdoumowyzlecenia.getUmowa().getUmowakodzus().isZlecenie()&&pasek.isDo26lat()) {
//            PasekwynagrodzenBean.obliczpodstaweopodatkowaniaZlecenie26(pasek, stawkipodatkowe, pasek.isNierezydent());
//            //PasekwynagrodzenBean.obliczpodstaweopodatkowania26DB(pasek, stawkipodatkowe, false, kalendarz.getAngaz().isKosztyuzyskania0podwyzszone(), limit26);
//        } else {
        PasekwynagrodzenBean.obliczpodstaweopodatkowaniaZlecenie(pasek, stawkipodatkowe, pasek.isNierezydent());
//        }
        PasekwynagrodzenBean.obliczpodatekwstepnyZlecenieDB(pasek, stawkipodatkowe, pasek.isNierezydent());
        PasekwynagrodzenBean.ulgapodatkowaDB(pasek, stawkipodatkowe, false, odliczonajuzkwotawolna);
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent(), false,"");
    }

    private static void umowazleceniaNRwyliczenie(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasek, double kurs, Definicjalistaplac definicjalistaplac, double odliczonajuzkwotawolna,
            boolean jestoddelegowanie, double limitZUS, List<Podatki> stawkipodatkowe, double sumapoprzednich, double zmiennawynagrodzeniakwota, double liczbagodzin,
            Rachunekdoumowyzlecenia rachunekdoumowyzlecenia, double limit26, double sumabruttopoprzednich, double zmiennawynagrodzeniakwotaodelegowanie, double sumabruttoopodatkowanapoprzednich) {
        boolean odliczaculgepodatkowa = kalendarz.getAngaz().isOdliczaculgepodatkowa();
        pasek.setDo26lat(false);
        KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDBNierezydent(kalendarz, pasek, kurs, zmiennawynagrodzeniakwota, liczbagodzin, zmiennawynagrodzeniakwotaodelegowanie);
//        KalendarzmiesiacBean.naliczskladnikipotraceniaDB(kalendarz, pasek);
        PasekwynagrodzenBean.obliczbruttoumowaoprace(pasek, limit26, sumabruttopoprzednich, sumabruttoopodatkowanapoprzednich);
//        PasekwynagrodzenBean.obliczbruttobezzus(pasek);
//        PasekwynagrodzenBean.obliczbruttobezzusbezpodatek(pasek);
        if (jestoddelegowanie) {
            if (jestoddelegowanie && kurs == 0.0) {
                Msg.msg("e", "Jest oddelegowanie, a brak kursu!");
            }
            pasek.setKurs(kurs);
            PasekwynagrodzenBean.obliczdietedoodliczenia(pasek, kalendarz);
            PasekwynagrodzenBean.wyliczpodstaweZUSlimit(kalendarz, pasek, kurs, limitZUS);
        } else {
            PasekwynagrodzenBean.wyliczpodstaweZUS(pasek);
        }
        if (pasek.isUlgadlaKlasySredniej() && kalendarz.getRokI() == 2022 && kalendarz.getMcI() < 7) {
            PasekwynagrodzenBean.uwzglednijulgeklasasrednia(pasek);
        }
        PasekwynagrodzenBean.pracownikemerytalna(pasek);
        PasekwynagrodzenBean.pracownikrentowa(pasek);
        PasekwynagrodzenBean.pracownikchorobowaZlecenie(pasek, rachunekdoumowyzlecenia);
        PasekwynagrodzenBean.razemspolecznepracownik(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczneDB(pasek);
        PasekwynagrodzenBean.obliczpodstaweopodatkowaniaZlecenie(pasek, stawkipodatkowe, pasek.isNierezydent());
        PasekwynagrodzenBean.obliczpodatekwstepnyZlecenieDB(pasek, stawkipodatkowe, pasek.isNierezydent());
        PasekwynagrodzenBean.ulgapodatkowaDB(pasek, stawkipodatkowe, false, odliczonajuzkwotawolna);
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent(), false,"");
    }

    private static void umowafunkcjawyliczenie(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasek, double kurs, Definicjalistaplac definicjalistaplac, double odliczonajuzkwotawolna, boolean jestoddelegowanie, double limitZUS, List<Podatki> stawkipodatkowe, double sumapoprzednich) {
        KalendarzmiesiacBean.naliczskladnikiwynagrodzeniaDBFunkcja(kalendarz, pasek, kurs);
        PasekwynagrodzenBean.obliczbruttoumowafunkcja(pasek);
//        PasekwynagrodzenBean.obliczbruttobezSpolecznych(pasek);
        PasekwynagrodzenBean.wyliczpodstaweZUS(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczneDB(pasek);
        PasekwynagrodzenBean.obliczpodstaweopodatkowaniafunkcja(pasek, stawkipodatkowe, pasek.isNierezydent());
        PasekwynagrodzenBean.obliczpodatekwstepnyFunkcjaDB(pasek, stawkipodatkowe, pasek.isNierezydent());
//        PasekwynagrodzenBean.obliczpodstaweopodatkowaniaFunkcja(pasek, stawkipodatkowe, pasek.isNierezydent());
//        PasekwynagrodzenBean.obliczpodatekwstepnyZlecenieDB(pasek, stawkipodatkowe, pasek.isNierezydent());
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent(), false,"");
    }

    
    
    
    public static double obliczminimalna(Kalendarzmiesiac kalendarz, Definicjalistaplac definicjalistaplac,
            List<Podatki> stawkipodatkowe, double sumapoprzednich, double wynagrodzenieminimalne, String datawyplaty) {
        Pasekwynagrodzen pasek = new Pasekwynagrodzen();
        pasek.setDatawyplaty(datawyplaty);
        pasek.setDefinicjalistaplac(definicjalistaplac);
        pasek.setKalendarzmiesiac(kalendarz);
        pasek.setPrzychodypodatekpolska(wynagrodzenieminimalne);
        pasek.setPrzychodyzus51(wynagrodzenieminimalne);
        pasek.setPrzychodyzus52(wynagrodzenieminimalne);
        pasek.setBruttozus(wynagrodzenieminimalne);
        pasek.setBrutto(Z.z(wynagrodzenieminimalne));
        pasek.setPodstawaskladkizus(wynagrodzenieminimalne);
        PasekwynagrodzenBean.pracownikemerytalna(pasek);
        PasekwynagrodzenBean.pracownikrentowa(pasek);
        pasek.setPracchorobowe(Z.z(pasek.getPodstawaskladkizus() * 0.0245));
        PasekwynagrodzenBean.razemspolecznepracownik(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczneDB(pasek);
        PasekwynagrodzenBean.obliczpodstaweopodatkowaniaDB(pasek, stawkipodatkowe, false, kalendarz.getAngaz().isKosztyuzyskania0podwyzszone());
        PasekwynagrodzenBean.obliczpodatekwstepnyDBStandard(pasek, pasek.getPodstawaopodatkowania(), stawkipodatkowe, sumapoprzednich);
        PasekwynagrodzenBean.ulgapodatkowaDB(pasek, stawkipodatkowe, true, 0.0);
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent(), true,"");
        PasekwynagrodzenBean.obliczpodatekdowplaty(pasek);
        PasekwynagrodzenBean.netto(pasek);
        PasekwynagrodzenBean.dowyplaty(pasek);
        return pasek.getNetto();
    }

    public static void main(String[] args) {
        Kalendarzwzor kalendarzwzor = KalendarzWzorBean.create();
        Kalendarzmiesiac kalendarz = KalendarzmiesiacBean.create();
        //Nieobecnosc korektakalendarzagora = NieobecnosciBean.createKorektakalendarzaGora();
        Nieobecnosc korektakalendarzadol = NieobecnosciBean.createKorektakalendarzaDol();
        Nieobecnosc choroba = NieobecnosciBean.createChoroba();
        Nieobecnosc choroba2 = NieobecnosciBean.createChoroba2();
        Nieobecnosc urlop = NieobecnosciBean.createUrlop();
        Nieobecnosc urlopbezplatny = NieobecnosciBean.createUrlopBezplatny();
        Pasekwynagrodzen pasek = create();
        pasek.setKalendarzmiesiac(kalendarz);
        kalendarz.getPasekwynagrodzenList().add(pasek);
        KalendarzmiesiacBean.naliczskladnikiwynagrodzenia(kalendarz, pasek);
        //KalendarzmiesiacBean.nalicznadgodziny50(kalendarz, pasek);
        //KalendarzmiesiacBean.nalicznadgodziny100(kalendarz);
        //najpierw musimy przyporzadkowac aktualne skladniki, aby potem prawidlowo obliczyc redukcje
        //korekta kalendarza musi byc na poczatku
        //KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, korektakalendarzagora, pasek);
        //KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, korektakalendarzadol, pasek);
        //KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, choroba, pasek);
        //KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, choroba2, pasek);
        //KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, urlopbezplatny, pasek);
        //urlop musi byc na samym koncu

        KalendarzmiesiacBean.redukujskladnikistale(kalendarz, pasek);
        KalendarzmiesiacBean.dodajnieobecnosc(kalendarz, urlop, pasek, new ArrayList<Kalendarzmiesiac>());
        KalendarzmiesiacBean.redukujskladnikistale2(kalendarz, pasek);
        //KalendarzmiesiacBean.naliczskladnikipotracenia(kalendarz, pasek);
        Definicjalistaplac definicjalistaplac = DefinicjalistaplacBean.create();
        pasek.setDefinicjalistaplac(definicjalistaplac);
        PasekwynagrodzenBean.obliczbruttoumowaoprace(pasek, 0,0,0);
        //PasekwynagrodzenBean.obliczbruttobezzus(pasek);
        PasekwynagrodzenBean.wyliczpodstaweZUS(pasek);
        PasekwynagrodzenBean.pracownikemerytalna(pasek);
        PasekwynagrodzenBean.pracownikrentowa(pasek);
        PasekwynagrodzenBean.pracownikchorobowa(pasek);
        PasekwynagrodzenBean.razemspolecznepracownik(pasek);
        PasekwynagrodzenBean.obliczbruttominusspoleczne(pasek);
        PasekwynagrodzenBean.obliczpodstaweopodatkowania(pasek);
        PasekwynagrodzenBean.obliczpodatekwstepny(pasek);
        PasekwynagrodzenBean.ulgapodatkowa(pasek, true);
        PasekwynagrodzenBean.naliczzdrowota(pasek, pasek.isNierezydent(), true,"");
        PasekwynagrodzenBean.obliczpodatekdowplaty(pasek);
        PasekwynagrodzenBean.potracenia(pasek);
        PasekwynagrodzenBean.netto(pasek);
        PasekwynagrodzenBean.dowyplaty(pasek);
        PasekwynagrodzenBean.doliczbezzusbezpodatek(pasek);

        System.out.println("****************");
        for (Naliczenieskladnikawynagrodzenia r : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            if (r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany()) {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi() + " kwota do listy płac: " + Z.z(r.getKwotadolistyplac()));
            } else {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi() + " nieredukowany " + Z.z(r.getKwotaumownazacalymc()));
            }
            System.out.println("dni nalezne " + r.getDninalezne() + " faktyczne " + Z.z(r.getDnifaktyczne()));
        }
        for (Naliczenienieobecnosc r : pasek.getNaliczenienieobecnoscList()) {
            if (r.getKwota() != 0.0 && r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany()) {
                System.out.println(r.getNieobecnosc().getOpisRodzajSwiadczenie() + " od " + r.getSkladnikwynagrodzenia().getUwagi() + " " + Z.z(r.getKwota()));
            }
            if (r.getKwotastatystyczna() != 0.0 && r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany()) {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi() + " statystyczna redukcja za " + r.getNieobecnosc().getOpisRodzajSwiadczenie() + " kwota " + Z.z(r.getKwotastatystyczna()));
            }
            if (r.getKwotaredukcji() != 0.0 && r.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isRedukowany()) {
                System.out.println(r.getSkladnikwynagrodzenia().getUwagi() + " redukcja za " + r.getNieobecnosc().getOpisRodzajSwiadczenie() + " kwota redukcji " + Z.z(r.getKwotaredukcji()));
            }
        }
        System.out.println("****************");
        System.out.println("brutto zus " + pasek.getBruttozus());
        System.out.println("brutto bezzus " + pasek.getBruttobezzus());
        double suma = pasek.getBruttozus() + pasek.getBruttobezzus();
        System.out.println("brutto razem " + pasek.getBrutto());
        System.out.println("redukcja " + pasek.getRedukcjeSuma());
        System.out.println("podstawa zus " + pasek.getPodstawaskladkizus());
        System.out.println("emerytalne: " + Z.z(pasek.getPracemerytalne()));
        System.out.println("podstawa: " + Z.z(pasek.getPodstawaopodatkowania()));
        System.out.println("zdrowotna: " + Z.z(pasek.getPraczdrowotne()));
        System.out.println("podatek: " + Z.z(pasek.getPodatekdochodowy()));
        System.out.println("Razem: " + Z.z(suma));
        System.out.println("do wypłaty: " + pasek.getNetto());
        System.out.println("");
        //PdfListaPlac.drukuj(pasek);
    }

    public static Pasekpomocnik sumujprzychodyzlisty(Pasekwynagrodzen pasek) {
        Pasekpomocnik zwrot = new Pasekpomocnik();
        double brutto = 0.0;
        double bruttokraj = 0.0;
        double bruttooddelegowanie = 0.0;
        double bruttooddelegowaniewaluta = 0.0;
        double przychodyzus51 = 0.0;
        double przychodyzus52 = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            //to nie ma sensu, gdyz skladnik40 zlecenie jako taki jest oznacozny jako ozusowany
//            if (p.isZus0bezzus1() == false && p.isPodatek0bezpodatek1() == false) {
            if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isPodatek0bezpodatek1()==false) {
                if (p.getSkladnikwynagrodzenia().isOddelegowanie()) {
                    bruttooddelegowanie = Z.z(bruttooddelegowanie + p.getKwotadolistyplac());
                    bruttooddelegowaniewaluta = Z.z(bruttooddelegowaniewaluta + p.getKwotadolistyplacwaluta());
                } else {
                    bruttokraj = bruttokraj + p.getKwotadolistyplac();
                }
                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isSpoleczna0bezspolecznej1()==false) {
                    przychodyzus51 = przychodyzus51+p.getKwotadolistyplac();
                } 
                if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().isZdrowotna0bezzdrowotnej()==false) {
                    przychodyzus52 = przychodyzus52+p.getKwotadolistyplac();
                }
            }
            brutto = Z.z(brutto+p.getKwotadolistyplac());
        }
        for (Naliczenienieobecnosc p : pasek.getNaliczenienieobecnoscList()) {
//            taki krzywy ryj bo nie ma oznaczenia oddelegowanie przy nieobecnosci urlop delehowanie
            if (p.getKwotawaluta()>0.0) {
                bruttooddelegowanie = bruttooddelegowanie + p.getKwota();
                bruttooddelegowaniewaluta = bruttooddelegowaniewaluta + p.getKwotawaluta();
            } else {
                bruttokraj = bruttokraj + p.getKwota();
            }
            if (p.getNieobecnosc().getRodzajnieobecnosci().isNieskladkowy()==false) {
                przychodyzus51 = przychodyzus51+p.getKwotazus();
                przychodyzus52 = przychodyzus52+p.getKwotazus();
            }
            if (p.getNieobecnosc().getSwiadczeniekodzus() != null && p.getNieobecnosc().getSwiadczeniekodzus().isZdrowotne()&& p.getNieobecnosc().getSwiadczeniekodzus().isSpoleczne()==false) {
                przychodyzus52 = przychodyzus52 + p.getKwotabezzus();
            }
            brutto = brutto+p.getKwota();
        }
        //double skladnikppk = KalendarzmiesiacBean.naliczskladnikiPPKDB(kalendarz, pasek, kurs, wynagrodzenieminimalne.getKwotabrutto(), kalendarzglobalny);
        zwrot.setBruttokraj(Z.z(bruttokraj));
        zwrot.setBruttooddelegowanie(Z.z(bruttooddelegowanie));
        zwrot.setBruttooddelegowaniewaluta(Z.z(bruttooddelegowaniewaluta));
        zwrot.setPrzychodyzus51(Z.z(przychodyzus51));
        zwrot.setPrzychodyzus52(Z.z(przychodyzus52));
        zwrot.setBrutto(Z.z(brutto));
        return zwrot;
    }
    
    private static void obliczbruttoumowaoprace(Pasekwynagrodzen pasek,double limit26, double sumapoprzednich, double sumabruttoopodatkowanapoprzednich) {
        Pasekpomocnik sumyprzychodow = sumujprzychodyzlisty(pasek);
        double przychodyBiezacyMiesiacPolska = sumyprzychodow.getBruttokraj()+sumyprzychodow.getBruttooddelegowanie();
        double przychodyBiezacyMiesiacZagranica = 0.0;
        if (pasek.isPrzekroczenieoddelegowanie()) {
            przychodyBiezacyMiesiacPolska = sumyprzychodow.getBruttokraj();
            przychodyBiezacyMiesiacZagranica = sumyprzychodow.getBruttooddelegowanie();
        }
        pasek.setPrzychodyzus51(sumyprzychodow.przychodyzus51);
        pasek.setPrzychodyzus52(sumyprzychodow.przychodyzus52);
        pasek.setPrzychodypodatekpolska(przychodyBiezacyMiesiacPolska);
        pasek.setPrzychodypodatekzagranica(przychodyBiezacyMiesiacZagranica);
        double przychodynarastajacodolimitu26 = sumapoprzednich + przychodyBiezacyMiesiacPolska;
        double doopodatkowaniapow26narast = przychodynarastajacodolimitu26 - limit26;
        pasek.setPrzekroczenie26lat(doopodatkowaniapow26narast);
        pasek.setBruttominusspolecznehipotetyczne(sumyprzychodow.getBruttokraj()+sumyprzychodow.getBruttooddelegowanie());
        if (pasek.isDo26lat()) {
            pasek.setPrzychodypodatekpolska(0.0);
            pasek.setPrzychodypodatekpolska(0.0);
            if (doopodatkowaniapow26narast > 0.0) {
               double doopodatkowaniaZaMiesiac = doopodatkowaniapow26narast-sumabruttoopodatkowanapoprzednich;
               pasek.setPrzychodypodatekpolska(doopodatkowaniaZaMiesiac);
               if (doopodatkowaniaZaMiesiac>0.0) {
                   pasek.setBruttominusspolecznehipotetyczne(doopodatkowaniaZaMiesiac);
               }
               
            }
        } 
        pasek.setOddelegowaniepln(sumyprzychodow.getBruttooddelegowanie());
        pasek.setOddelegowaniewaluta(sumyprzychodow.getBruttooddelegowaniewaluta());
        if (pasek.getKodZus().equals("0511")) {
            pasek.setPrzychodyzus51(0.0);
            pasek.setPrzychodyzus52(0.0);
            pasek.setBruttobezspolecznych(sumyprzychodow.getBruttokraj());
            pasek.setBrutto(Z.z(pasek.getBrutto() + sumyprzychodow.brutto));
        } else {
            pasek.setBruttozuskraj(sumyprzychodow.getBruttokraj());
            pasek.setBrutto(Z.z(pasek.getBrutto() + sumyprzychodow.brutto));
        }
    }
    
    private static void obliczbruttoumowafunkcja(Pasekwynagrodzen pasek) {
        Pasekpomocnik sumyprzychodow = sumujprzychodyzlisty(pasek);
        double przychodyBiezacyMiesiacPolska = sumyprzychodow.getBruttokraj()+sumyprzychodow.getBruttooddelegowanie();
        double przychodyBiezacyMiesiacZagranica = 0.0;
        if (pasek.isPrzekroczenieoddelegowanie()) {
            przychodyBiezacyMiesiacPolska = sumyprzychodow.getBruttokraj();
            przychodyBiezacyMiesiacZagranica = sumyprzychodow.getBruttooddelegowanie();
        }
        pasek.setPrzychodyzus51(sumyprzychodow.przychodyzus51);
        pasek.setPrzychodyzus52(sumyprzychodow.przychodyzus52);
        pasek.setPrzychodypodatekpolska(przychodyBiezacyMiesiacPolska);
        pasek.setPrzychodypodatekzagranica(przychodyBiezacyMiesiacZagranica);
        pasek.setBruttozuskraj(sumyprzychodow.getBruttokraj());
        pasek.setBrutto(Z.z(pasek.getBrutto() + sumyprzychodow.brutto));
    }
    
    private static void obliczbruttoumowazlecenia(Pasekwynagrodzen pasek, Rachunekdoumowyzlecenia rachunekdoumowyzlecenia, double limit26, double sumapoprzednich, double sumabruttoopodatkowanapoprzednich) {
        Pasekpomocnik sumyprzychodow = sumujprzychodyzlisty(pasek);
        double przychodyBiezacyMiesiacPolska = sumyprzychodow.getBruttokraj()+sumyprzychodow.getBruttooddelegowanie();
        double przychodyBiezacyMiesiacZagranica = 0.0;
        if (pasek.isPrzekroczenieoddelegowanie()) {
            przychodyBiezacyMiesiacPolska = sumyprzychodow.getBruttokraj();
            przychodyBiezacyMiesiacZagranica = sumyprzychodow.getBruttooddelegowanie();
        }
        pasek.setPrzychodyzus51(sumyprzychodow.getPrzychodyzus51());
        pasek.setPrzychodyzus52(sumyprzychodow.getPrzychodyzus52());
        pasek.setPrzychodypodatekpolska(przychodyBiezacyMiesiacPolska);
        pasek.setPrzychodypodatekzagranica(przychodyBiezacyMiesiacZagranica);
        double przychodynarastajacodolimitu26 = sumapoprzednich + przychodyBiezacyMiesiacPolska;
        double doopodatkowaniapow26narast = przychodynarastajacodolimitu26 - limit26;
        pasek.setPrzekroczenie26lat(doopodatkowaniapow26narast);
        if (pasek.isDo26lat() && rachunekdoumowyzlecenia.isStatusstudenta()) {
            pasek.setPrzychodyzus51(0.0);
            pasek.setPrzychodyzus52(0.0);
            pasek.setPrzychodypodatekpolska(0.0);
           if (doopodatkowaniapow26narast > 0.0) {
               double doopodatkowaniaZaMiesiac = doopodatkowaniapow26narast-sumabruttoopodatkowanapoprzednich;
               pasek.setPrzychodypodatekpolska(doopodatkowaniaZaMiesiac);
            }
        }else if (pasek.isDo26lat() && rachunekdoumowyzlecenia.isStatusstudenta()==false) {
            pasek.setPrzychodypodatekpolska(0.0);
            pasek.setPrzychodypodatekpolska(0.0);
            if (doopodatkowaniapow26narast > 0.0) {
               double doopodatkowaniaZaMiesiac = doopodatkowaniapow26narast-sumabruttoopodatkowanapoprzednich;
               pasek.setPrzychodypodatekpolska(doopodatkowaniaZaMiesiac);
               
            }
        } 
        if (rachunekdoumowyzlecenia.isInnytytulminim()) {
            pasek.setPrzychodyzus51(0.0);
        }
        pasek.setStudent(rachunekdoumowyzlecenia.isStatusstudenta());
        pasek.setOddelegowaniepln(sumyprzychodow.getBruttooddelegowanie());
        pasek.setOddelegowaniewaluta(sumyprzychodow.getBruttooddelegowaniewaluta());
        pasek.setBruttozuskraj(sumyprzychodow.getBruttokraj());
        pasek.setBrutto(Z.z(pasek.getBrutto() + sumyprzychodow.getBruttokraj() + sumyprzychodow.getBruttooddelegowanie()));
    }
    
    private static void obliczbruttoumowadzielo(Pasekwynagrodzen pasek, Rachunekdoumowyzlecenia rachunekdoumowyzlecenia) {
        double bruttozuskraj = 0.0;
        double bruttozusoddelegowanie = 0.0;
        double bruttozusoddelegowaniewaluta = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            if (p.isZus0bezzus1() == true && p.isPodatek0bezpodatek1() == false) {
                if (p.getSkladnikwynagrodzenia().isOddelegowanie()) {
                    bruttozusoddelegowanie = Z.z(bruttozusoddelegowanie + p.getKwotadolistyplac());
                    bruttozusoddelegowaniewaluta = Z.z(bruttozusoddelegowaniewaluta + p.getKwotadolistyplacwaluta());
                } else {
                    bruttozuskraj = Z.z(bruttozuskraj + p.getKwotadolistyplac());
                }
            }
        }
        for (Naliczenienieobecnosc p : pasek.getNaliczenienieobecnoscList()) {
            bruttozuskraj = Z.z(bruttozuskraj + p.getKwotazus());
            bruttozusoddelegowaniewaluta = Z.z(bruttozusoddelegowaniewaluta + p.getKwotawaluta());
        }
        double bruttobezzus = bruttozuskraj + bruttozusoddelegowanie;
        pasek.setBruttobezzus(bruttobezzus);
        pasek.setStudent(rachunekdoumowyzlecenia.isStatusstudenta());
        pasek.setOddelegowaniepln(bruttozusoddelegowanie);
        pasek.setOddelegowaniewaluta(bruttozusoddelegowaniewaluta);
        pasek.setBruttozuskraj(0.0);
        pasek.setBrutto(Z.z(bruttobezzus + bruttozusoddelegowanie));
    }

    private static void obliczbruttoumowaopraceKod0511(Pasekwynagrodzen pasek) {
        double bruttokraj = 0.0;
        double bruttooddelegowanie = 0.0;
        double bruttooddelegowaniewaluta = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            if (p.isZus0bezzus1() == false && p.isPodatek0bezpodatek1() == false) {
                if (p.getSkladnikwynagrodzenia().isOddelegowanie()) {
                    bruttooddelegowanie = bruttooddelegowanie + p.getKwotadolistyplac();
                    bruttooddelegowaniewaluta = bruttooddelegowaniewaluta + p.getKwotadolistyplacwaluta();
                } else {
                    bruttokraj = bruttokraj + p.getKwotadolistyplac();
                }
            }
        }
       for (Naliczenienieobecnosc p : pasek.getNaliczenienieobecnoscList()) {
            
            if (p.getNieobecnosc().getKod().equals("UD")) {
                bruttooddelegowaniewaluta = bruttooddelegowaniewaluta + p.getKwotawaluta();
                bruttooddelegowanie = Z.z(bruttooddelegowanie+ p.getKwotazus());
            } else {
                bruttokraj = bruttokraj + p.getKwotazus();
            }
        }
        pasek.setOddelegowaniepln(Z.z(bruttooddelegowanie));
        pasek.setOddelegowaniewaluta(Z.z(bruttooddelegowaniewaluta));
        pasek.setBruttobezzus(Z.z(bruttokraj + bruttooddelegowanie));
    }

//    private static void obliczbruttoumowaoprace(Pasekwynagrodzen pasek) {
//        double bruttokraj = 0.0;
//        double bruttooddelegowanie = 0.0;
//        double bruttooddelegowaniewaluta = 0.0;
//        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
//            if (p.isZus0bezzus1() == false && p.isSpoleczna0bezspolecznej1()==false && p.isPodatek0bezpodatek1() == false) {
//                if (p.getSkladnikwynagrodzenia().isOddelegowanie()) {
//                    bruttooddelegowanie = bruttooddelegowanie + p.getKwotadolistyplac();
//                    bruttooddelegowaniewaluta = bruttooddelegowaniewaluta + p.getKwotadolistyplacwaluta();
//                } else {
//                    bruttokraj = bruttokraj + p.getKwotadolistyplac();
//                }
//            }
//        }
//        for (Naliczenienieobecnosc p : pasek.getNaliczenienieobecnoscList()) {
//            
//            if (p.getNieobecnosc().getKod().equals("UD")) {
//                bruttooddelegowaniewaluta = bruttooddelegowaniewaluta + p.getKwotawaluta();
//                bruttooddelegowanie = bruttooddelegowanie+ p.getKwotazus();
//            } else {
//                bruttokraj = bruttokraj + p.getKwotazus();
//            }
//        }
//        pasek.setOddelegowaniepln(Z.z(bruttooddelegowanie));
//        pasek.setOddelegowaniewaluta(Z.z(bruttooddelegowaniewaluta));
//        pasek.setBruttozuskraj(bruttokraj);
//        double bruttozus = bruttokraj + bruttooddelegowanie;
//        pasek.setBruttozus(Z.z6(bruttozus));
//        pasek.setBrutto(Z.z6(pasek.getBrutto() + bruttozus));
//    }

    private static void obliczbrutto26(Pasekwynagrodzen pasek, double limit26, double sumapoprzednich) {
        double bruttozuskraj = 0.0;
        double bruttozusoddelegowanie = 0.0;
        double bruttozusoddelegowaniewaluta = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            if (p.isZus0bezzus1() == false && p.isPodatek0bezpodatek1() == false) {
                if (p.getSkladnikwynagrodzenia().isOddelegowanie()) {
                    bruttozusoddelegowanie = Z.z(bruttozusoddelegowanie + p.getKwotadolistyplac());
                    bruttozusoddelegowaniewaluta = Z.z(bruttozusoddelegowaniewaluta + p.getKwotadolistyplacwaluta());
                } else {
                    bruttozuskraj = bruttozuskraj + p.getKwotadolistyplac();
                }
            }
        }
        for (Naliczenienieobecnosc p : pasek.getNaliczenienieobecnoscList()) {
            
            if (p.getNieobecnosc().getKod().equals("UD")) {
                bruttozusoddelegowaniewaluta = bruttozusoddelegowaniewaluta + p.getKwotawaluta();
                bruttozusoddelegowanie = Z.z(bruttozusoddelegowanie+ p.getKwotazus());
            } else {
                bruttozuskraj = bruttozuskraj + p.getKwotazus();
            }
        }
        double przychodbiezacymiesiac = bruttozuskraj + bruttozusoddelegowanie;
        //dodalem to na szybkiego bo zrobilem przekroczenie ale popsulem przedprzekroczenie 15.11.2023
        if (pasek.isPrzekroczenieoddelegowanie()) {
            przychodbiezacymiesiac = bruttozuskraj;
            double nowasumaprzychodow = sumapoprzednich + przychodbiezacymiesiac;
            double roznicapowyzejlimitu = nowasumaprzychodow - limit26;
            if (roznicapowyzejlimitu > 0.0) {
                if (roznicapowyzejlimitu>przychodbiezacymiesiac) {
                    roznicapowyzejlimitu = przychodbiezacymiesiac;   
                }
                double bezpodatku = przychodbiezacymiesiac - roznicapowyzejlimitu;
                //pasek.setBruttozusbezpodatek(bezpodatku);
                pasek.setBruttozus(roznicapowyzejlimitu);
            } else {
                //pasek.setBruttozusbezpodatek(przychodbiezacymiesiac);
            }
        } else {
            double sumaprzejsciowa = bruttozuskraj + bruttozusoddelegowanie;
            double nowasumaprzychodow = sumapoprzednich + sumaprzejsciowa;
            double roznicapowyzejlimitu = nowasumaprzychodow - limit26;
            if (roznicapowyzejlimitu > 0.0) {
                double bezpodatku = sumaprzejsciowa - roznicapowyzejlimitu;
                 //pasek.setBruttozusbezpodatek(bezpodatku);
                pasek.setBruttozus(roznicapowyzejlimitu);
            } else {
                //pasek.setBruttozusbezpodatek(sumaprzejsciowa);
            }
        }
       
        pasek.setOddelegowaniepln(bruttozusoddelegowanie);
        pasek.setOddelegowaniewaluta(bruttozusoddelegowaniewaluta);
        pasek.setBruttozuskraj(bruttozuskraj);
        double brutto = bruttozuskraj + bruttozusoddelegowanie;
        pasek.setBrutto(Z.z(pasek.getBrutto() + brutto));
    }

    private static void obliczbruttobezzusZasilek(Pasekwynagrodzen pasek) {
        double bruttobezzus = 0.0;
        for (Naliczenienieobecnosc p : pasek.getNaliczenienieobecnoscList()) {
            bruttobezzus = Z.z(bruttobezzus + p.getKwotabezzus());
//            if (p.getNieobecnosc().getSwiadczeniekodzus()!=null&&!p.getNieobecnosc().getSwiadczeniekodzus().getZrodlofinansowania().equals('P')&&!p.getNieobecnosc().getSwiadczeniekodzus().getZrodlofinansowania().equals('B')) {
//                bruttobezzus = Z.z(bruttobezzus+p.getKwotabezzus());
//            }
        }
        pasek.setPrzychodypodatekpolska(bruttobezzus);
        pasek.setBruttobezzus(bruttobezzus);
        pasek.setBrutto(Z.z(pasek.getBrutto() + bruttobezzus));
    }

    private static void obliczbruttobezSpolecznych(Pasekwynagrodzen pasek) {
        double bruttobezspolecznych = 0.0;
         for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            if ((p.isSpoleczna0bezspolecznej1() == true ||p.isZus0bezzus1() == true) && p.isPodatek0bezpodatek1() == false) {
                bruttobezspolecznych = Z.z(bruttobezspolecznych + p.getKwotadolistyplac());
            }
        }
        for (Naliczenienieobecnosc p : pasek.getNaliczenienieobecnoscList()) {
            if (p.getNieobecnosc().getSwiadczeniekodzus() != null && p.getNieobecnosc().getSwiadczeniekodzus().isZdrowotne()&& p.getNieobecnosc().getSwiadczeniekodzus().isSpoleczne()==false) {
                bruttobezspolecznych = Z.z(bruttobezspolecznych + p.getKwotabezzus());
            }
        }
        pasek.setBruttobezspolecznych(bruttobezspolecznych);
        pasek.setBrutto(Z.z(pasek.getBrutto() + bruttobezspolecznych));
        pasek.setPrzychodypodatekpolska(bruttobezspolecznych);
    }

//      private static void obliczbruttobezzus(Pasekwynagrodzen pasek) {
//        double bruttobezzus = 0.0;
//        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
//            if (p.isZus0bezzus1() == true && p.isPodatek0bezpodatek1() == false) {
//                bruttobezzus = Z.z(bruttobezzus + p.getKwotadolistyplac());
//            }
//        }
//        for (Naliczenienieobecnosc p : pasek.getNaliczenienieobecnoscList()) {
//            if (p.getNieobecnosc().getSwiadczeniekodzus() == null) {
//                bruttobezzus = Z.z(bruttobezzus + p.getKwotabezzus());
//            } else if (p.getNieobecnosc().getSwiadczeniekodzus() != null && (p.getNieobecnosc().getSwiadczeniekodzus().getZrodlofinansowania().equals('P')
//                    || p.getNieobecnosc().getSwiadczeniekodzus().getZrodlofinansowania().equals('B')) && p.getNieobecnosc().getSwiadczeniekodzus().isZdrowotne()==false && p.getNieobecnosc().getSwiadczeniekodzus().isSpoleczne()==false) {
//                bruttobezzus = Z.z(bruttobezzus + p.getKwotabezzus());
//            }
//        }
//        pasek.setBruttobezzus(bruttobezzus);
//        pasek.setBrutto(Z.z(pasek.getBrutto() + bruttobezzus));
//    }
    
//    private static void obliczbruttobezzusKod0511(Pasekwynagrodzen pasek) {
//        double bruttobezzus = pasek.getBruttobezzus();
//        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
//            if (p.isZus0bezzus1() == true && p.isPodatek0bezpodatek1() == false) {
//                bruttobezzus = Z.z(bruttobezzus + p.getKwotadolistyplac());
//            }
//        }
//        for (Naliczenienieobecnosc p : pasek.getNaliczenienieobecnoscList()) {
//            if (p.getNieobecnosc().getSwiadczeniekodzus() == null) {
//                bruttobezzus = Z.z(bruttobezzus + p.getKwotabezzus());
//            } else if (p.getNieobecnosc().getSwiadczeniekodzus() != null && (p.getNieobecnosc().getSwiadczeniekodzus().getZrodlofinansowania().equals('P')
//                    || p.getNieobecnosc().getSwiadczeniekodzus().getZrodlofinansowania().equals('B')) && !p.getNieobecnosc().getSwiadczeniekodzus().isZdrowotne()) {
//                bruttobezzus = Z.z(bruttobezzus + p.getKwotabezzus());
//            }
//        }
//        pasek.setBruttobezzus(bruttobezzus);
//        pasek.setBrutto(Z.z(pasek.getBrutto() + bruttobezzus));
//    }

  

//    private static void obliczbruttobezzusbezpodatek(Pasekwynagrodzen pasek) {
//        double bruttobezzusbezpodatek = 0.0;
//        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
//            if (p.isPodatek0bezpodatek1() == true && p.isPodatek0bezpodatek1() == true) {
//                bruttobezzusbezpodatek = Z.z(bruttobezzusbezpodatek + p.getKwotadolistyplac());
//            }
//        }
//        //pasek.setBruttobezzusbezpodatek(-1000);
//        pasek.setBrutto(Z.z(pasek.getBrutto() + bruttobezzusbezpodatek));
//    }

    private static void pracownikemerytalna(Pasekwynagrodzen pasek) {
        pasek.setPracemerytalne(Z.z(pasek.getPodstawaskladkizus() * 0.0976));
    }

    private static void emerytalna(Pasekwynagrodzen pasek) {
        pasek.setEmerytalne(Z.z(pasek.getPodstawaskladkizus() * 0.0976));
    }

    private static void pracownikrentowa(Pasekwynagrodzen pasek) {
        pasek.setPracrentowe(Z.z(pasek.getPodstawaskladkizus() * 0.015));
    }

    private static void rentowa(Pasekwynagrodzen pasek) {
        pasek.setRentowe(Z.z(pasek.getPodstawaskladkizus() * 0.065));
    }

    private static void wypadkowa(Pasekwynagrodzen pasek) {
        List<Wypadkowefirma> wypadkowefirmaList = pasek.getKalendarzmiesiac().getAngaz().getFirma().getWypadkowefirmaList();
        Wypadkowefirma pobrane = null;
        if (wypadkowefirmaList != null) {
            for (Wypadkowefirma w : wypadkowefirmaList) {
                if (w.czynalezydookresu(pasek.getRok(), pasek.getMcwypl())) {
                    pobrane = w;
                    break;
                }
            }
        }
        if (pobrane == null) {
            pasek.setWypadkowe(Z.z(pasek.getPodstawaskladkizus() * 0.0167));
        } else {
            pasek.setWypadkowe(Z.z(pasek.getPodstawaskladkizus() * (pobrane.getProcent() / 100.0)));
        }
    }

    private static void fp(Pasekwynagrodzen pasek) {
        pasek.setFp(Z.z(pasek.getPodstawaskladkizus() * 0.0245));
    }

    private static void fgsp(Pasekwynagrodzen pasek) {
        pasek.setFgsp(Z.z(pasek.getPodstawaskladkizus() * 0.001));
    }

    private static void pracownikchorobowa(Pasekwynagrodzen pasek) {
        pasek.setPracchorobowe(Z.z(pasek.getPodstawaskladkizus() * 0.0245));
    }

    private static void pracownikchorobowaZlecenie(Pasekwynagrodzen pasek, Rachunekdoumowyzlecenia rachunekdoumowyzlecenia) {
        if (rachunekdoumowyzlecenia.isChorobowa()) {
            pasek.setPracchorobowe(Z.z(pasek.getPodstawaskladkizus() * 0.0245));
        }
    }

    private static void razemspolecznepracownik(Pasekwynagrodzen pasek) {
        pasek.setRazemspolecznepracownik(Z.z(pasek.getPracemerytalne() + pasek.getPracrentowe() + pasek.getPracchorobowe()));
        pasek.setSpoleczneudzialpolska(Z.z(pasek.getPracemerytalne() + pasek.getPracrentowe() + pasek.getPracchorobowe()));
        if (pasek.getPrzychodypodatekpolska()>0.0&&pasek.getPodstawaskladkizus()>0.0) {
            double proporcjaprzychodow = pasek.getPrzychodypodatekpolska()/pasek.getPodstawaskladkizus();
            //to musi byc bo jest przeciez ograniczenie wysokosci skladki zus
            if (proporcjaprzychodow>1.0) {
                proporcjaprzychodow = 1.0;
            }
            double zusproporcjonalnie = pasek.getRazemspolecznepracownik()*proporcjaprzychodow;
            pasek.setSpoleczneudzialpolska(zusproporcjonalnie);
        }
    }
    
    public static void razemspolecznepracownikkorektalp(Pasekwynagrodzen pasek) {
        pasek.setRazemspolecznepracownik(Z.z(pasek.getPracemerytalne() + pasek.getPracrentowe() + pasek.getPracchorobowe()));
        pasek.setSpoleczneudzialpolska(Z.z(pasek.getPracemerytalne() + pasek.getPracrentowe() + pasek.getPracchorobowe()));
        double przychodypolska = pasek.getPrzychodypodatekpolska();
        if (przychodypolska==0.0) {
            double oddelegowaniepln = pasek.getOddelegowaniepln();
            double oddelegowanieplnwyliczenie = Z.z(pasek.getOddelegowaniewaluta()*pasek.getKurs());
            if (oddelegowanieplnwyliczenie>oddelegowaniepln) {
                oddelegowaniepln = oddelegowanieplnwyliczenie;
            }
            przychodypolska = Z.z(pasek.getBrutto()-oddelegowaniepln);
            pasek.setPrzychodypodatekpolska(przychodypolska);
        } else {
            double oddelegowaniepln = pasek.getOddelegowaniepln();
            double oddelegowanieplnwyliczenie = Z.z(pasek.getOddelegowaniewaluta()*pasek.getKurs());
            if (oddelegowanieplnwyliczenie>oddelegowaniepln) {
                oddelegowaniepln = oddelegowanieplnwyliczenie;
                przychodypolska = Z.z(pasek.getBrutto()-oddelegowaniepln);
                pasek.setPrzychodypodatekpolska(przychodypolska);
            }
        }
        if (pasek.getOddelegowaniepln()>0.0&&pasek.getPodstawaskladkizus()>0.0) {
            double proporcjaprzychodow = przychodypolska/pasek.getPodstawaskladkizus();
            //to musi byc bo jest przeciez ograniczenie wysokosci skladki zus
            if (proporcjaprzychodow>1.0) {
                proporcjaprzychodow = 1.0;
            }
            double zusproporcjonalnie = pasek.getRazemspolecznepracownik()*proporcjaprzychodow;
            pasek.setSpoleczneudzialpolska(zusproporcjonalnie);
        }
    }

    private static void razemspolecznefirma(Pasekwynagrodzen pasek) {
        pasek.setRazemspolecznefirma(Z.z(pasek.getEmerytalne() + pasek.getRentowe() + pasek.getWypadkowe()));
    }

    private static void razem53(Pasekwynagrodzen pasek) {
        pasek.setRazem53(Z.z(pasek.getFp() + pasek.getFgsp()));
    }

    private static void razemkosztpracodawcy(Pasekwynagrodzen pasek) {
        pasek.setKosztpracodawcy(Z.z(pasek.getBrutto() + pasek.getRazemspolecznefirma() + pasek.getFp() + pasek.getFgsp()));
    }

    private static void obliczbruttominusspoleczne(Pasekwynagrodzen pasek) {
        double wynagrodzenie = pasek.getBrutto();
        double skladki = pasek.getRazemspolecznepracownik();
        double podstawa = Z.z(wynagrodzenie - skladki) > 0.0 ? Z.z(wynagrodzenie - skladki) : 0.0;
        pasek.setBruttominusspoleczne(podstawa);
    }

    private static void obliczpodstaweopodatkowania(Pasekwynagrodzen pasek) {
        double bruttominusspoleczne = pasek.getBruttominusspoleczne();
        double kosztyuzyskania = 250.0;
        double dieta30proc = pasek.getDietaodliczeniepodstawaop();
        double podstawa = Z.z0(bruttominusspoleczne - kosztyuzyskania - dieta30proc) > 0.0 ? Z.z0(bruttominusspoleczne - kosztyuzyskania - dieta30proc) : 0.0;
        pasek.setPodstawaopodatkowania(podstawa);
        pasek.setKosztyuzyskania(kosztyuzyskania);

    }

    private static void obliczbruttominusspoleczneDB(Pasekwynagrodzen pasek) {
        double wynagrodzeniebrutto = pasek.getPrzychodypodatekpolska();
//        double zzus = pasek.getBruttozus();
//        double bezzus = pasek.getBruttobezzus();
//        double bezspolecznych = pasek.getBruttobezspolecznych();
        //to ma znaczenie przy oddelegowaniu i oddelegowaniu plus 26
        double skladki = pasek.getSpoleczneudzialpolska()>0.0?pasek.getSpoleczneudzialpolska():pasek.getRazemspolecznepracownik();
        //zapisek dla kierowcow 27.10.23
        Skladnikwynagrodzenia skladnikZUS51 = pasek.getSkladnikZUS51();
        if (skladnikZUS51!=null) {
            Zmiennawynagrodzenia zmiennawynagrodzenia = KalendarzmiesiacBean.usrednijZmienna(skladnikZUS51, pasek.getKalendarzmiesiac());
            if (zmiennawynagrodzenia!=null) {
                skladki = zmiennawynagrodzenia.getKwota();
                pasek.setSpolecznedoodliczeniakierowca(skladki);
            }
        }
        //zapisek dla kierowcow 27.10.23
        //double zusbezpodatku = pasek.getBruttozusbezpodatek();
        double podstawadochdowyprzeddieta = Z.z(wynagrodzeniebrutto - skladki) > 0.0 ? Z.z(wynagrodzeniebrutto - skladki) : 0.0;
        pasek.setBruttominusspoleczne(podstawadochdowyprzeddieta);
        double jakapodstawa = pasek.getPodstawaskladkizus()<pasek.getBruttominusspolecznehipotetyczne()?pasek.getPodstawaskladkizus():pasek.getBruttominusspolecznehipotetyczne();
        double podstawahipotetyczna = Z.z(jakapodstawa-skladki) >0.0?Z.z(jakapodstawa-skladki):0.0;
        pasek.setBruttominusspolecznehipotetyczne(podstawahipotetyczna);
    }

    private static void obliczpodstaweopodatkowaniaZasilekDB(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe) {
        Podatki pierwszyprog = stawkipodatkowe.get(0);
        double bruttominusspoleczne = pasek.getBruttominusspoleczne();
        double podstawa = Z.z0(bruttominusspoleczne);
        pasek.setPodstawaopodatkowania(podstawa);
    }

    private static void obliczpodstaweopodatkowaniaDB(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nieodliczackup, boolean podwyzszonekoszty) {
        Podatki pierwszyprog = stawkipodatkowe.get(0);
        double bruttominusspoleczne = pasek.getBruttominusspoleczne();
        double kosztyuzyskania = pierwszyprog.getKup();
        pasek.setProcentkosztow(100);
        if (podwyzszonekoszty) {
            kosztyuzyskania = pierwszyprog.getKuppodwyzszone();
            pasek.setProcentkosztow(120);
        }
        if (pasek.getAngaz().getKosztyuzyskaniaprocent()==0.0) {
            pasek.setProcentkosztow(0.0);
            kosztyuzyskania = 0.0;
        }
        //pasek.getKalendarzmiesiac().getUmowa().getKosztyuzyskaniaprocent()==100?pierwszyprog.getKup():pierwszyprog.getKuppodwyzszone();
        if (nieodliczackup) {
            kosztyuzyskania = 0.0;
        }
        double dieta30proc = pasek.getDietaodliczeniepodstawaop();
        if (pasek.isPrzekroczenieoddelegowanie()) {
            pasek.setDietaodliczeniepodstawaop(0.0);
            dieta30proc = 0.0;
        }
        double ulgadlaklasysredniej = pasek.isUlgadlaKlasySredniej() ? pasek.getUlgadlaklasysredniejI() + pasek.getUlgadlaklasysredniejII() : 0.0;
        double przychodypracownikadlakosztow = Z.z0(pasek.getBrutto());
        if (przychodypracownikadlakosztow < 0) {
            kosztyuzyskania = 0.0;
        } else if (przychodypracownikadlakosztow < kosztyuzyskania) {
            kosztyuzyskania = przychodypracownikadlakosztow;
        }
        double podstawa = Z.z0(bruttominusspoleczne - kosztyuzyskania - dieta30proc - ulgadlaklasysredniej) > 0.0 ? Z.z0(bruttominusspoleczne - kosztyuzyskania - dieta30proc - ulgadlaklasysredniej) : 0.0;
        pasek.setPodstawaopodatkowania(podstawa);
        korektaprzychodyopodatkowanezagranica(podstawa,pasek);
        pasek.setKosztyuzyskania(kosztyuzyskania);

    }

    private static void obliczpodstaweopodatkowania26DB(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nieodliczackup, boolean podwyzszonekoszty, double limit26) {
        Podatki pierwszyprog = stawkipodatkowe.get(0);
        double bruttominusspoleczne = pasek.getBruttominusspoleczne();
        double udzialpodtsawyzuswbrutto = pasek.getBrutto()>0.0?pasek.getPodstawaskladkizus()/pasek.getBrutto():0.0;
        //tak bylo ale przeciez wyedty skladki od dochodu polskiego ktory nie jest przekroczony przezucamy do niemiec a tak nei powinno byc
        //double przekroczenieminusoddelegowanie = pasek.getBruttozus()-pasek.getOddelegowaniepln();
        double bruttominusniemcy = pasek.getBrutto()-pasek.getOddelegowaniepln();
        double przekrocznieozusowane = bruttominusniemcy*udzialpodtsawyzuswbrutto;
        double polskiespoleczne = przekrocznieozusowane*.1371;
        if (pasek.getSpoleczneudzialpolska()>0.0) {
            polskiespoleczne = pasek.getSpoleczneudzialpolska();
        }
        double kwotanadwyzki = pasek.getBruttozus();
        double kosztyuzyskania = pierwszyprog.getKup();
        if (podwyzszonekoszty) {
            kosztyuzyskania = pierwszyprog.getKuppodwyzszone();
        }
        //pasek.getKalendarzmiesiac().getUmowa().getKosztyuzyskaniaprocent()==100?pierwszyprog.getKup():pierwszyprog.getKuppodwyzszone();
        if (nieodliczackup && kwotanadwyzki == 0.0) {
            kosztyuzyskania = 0.0;
        }
        double dieta30proc = pasek.getDietaodliczeniepodstawaop();
        if (pasek.isPrzekroczenieoddelegowanie()) {
            pasek.setDietaodliczeniepodstawaop(0.0);
            dieta30proc = 0.0;
        }
        double ulgadlaklasysredniej = pasek.isUlgadlaKlasySredniej() ? pasek.getUlgadlaklasysredniejI() + pasek.getUlgadlaklasysredniejII() : 0.0;
        double podstawapopomniejszeniu = Z.z0(bruttominusspoleczne - dieta30proc - ulgadlaklasysredniej);
        if (podstawapopomniejszeniu < 0) {
            kosztyuzyskania = 0.0;
        } else if (podstawapopomniejszeniu < kosztyuzyskania) {
            kosztyuzyskania = podstawapopomniejszeniu;
        }
        double bruttominusspolecznehipotetyczne = pasek.getBruttominusspolecznehipotetyczne();
        double zushipotetyczny = Z.z(bruttominusspolecznehipotetyczne*.1371>pasek.getRazemspolecznepracownik()?pasek.getRazemspolecznepracownik():bruttominusspolecznehipotetyczne*.1371);
        double podstawahipotetyczna = Z.z0(bruttominusspolecznehipotetyczne - kosztyuzyskania - zushipotetyczny - dieta30proc - ulgadlaklasysredniej) > 0.0 ? Z.z0(bruttominusspolecznehipotetyczne - kosztyuzyskania - zushipotetyczny - dieta30proc - ulgadlaklasysredniej) : 0.0;
        pasek.setKosztyuzyskaniahipotetyczne(kosztyuzyskania);
        pasek.setPodstawaopodatkowaniahipotetyczna(podstawahipotetyczna);
        double podstawa = Z.z0(bruttominusspoleczne - kosztyuzyskania - dieta30proc - ulgadlaklasysredniej) > 0.0 ? Z.z0(bruttominusspoleczne - kosztyuzyskania - dieta30proc - ulgadlaklasysredniej) : 0.0;
        //kosztyuzyskania = 0.0;
        //nie wiem po co to
//        if (pasek.isPrzekroczenieoddelegowanie()) {
//            podstawa = Z.z0(kwotanadwyzki - kosztyuzyskania - polskiespoleczne - ulgadlaklasysredniej) > 0.0 ? Z.z0(kwotanadwyzki - kosztyuzyskania - polskiespoleczne  - ulgadlaklasysredniej) : 0.0;
//        } else {
//            podstawa = Z.z0(kwotanadwyzki - kosztyuzyskania - pasek.getRazemspolecznepracownik() - ulgadlaklasysredniej) > 0.0 ? Z.z0(kwotanadwyzki - kosztyuzyskania - pasek.getRazemspolecznepracownik()  - ulgadlaklasysredniej) : 0.0;
//        }
        //nie odejmujemy od podstaway calekj kwoty oddelegoanie tylko robimy proporckje w stosunku do wynagrodzenia bo w przekroczeniu tez jest proporcja
        //double procentoddelegowaniebrutto = pasek.getOddelegowaniepln()/pasek.getBrutto();
        pasek.setPodstawaopodatkowania(podstawa);
        pasek.setPodstawaprzedkorektaozagranice(podstawa);
        if (pasek.getPrzychodypodatekzagranica()==0.0) {
            //robimy to bo przy zbirgu niemcy i 26lat zawzal kwote
            pasek.setSpoleczneudzialpolska(polskiespoleczne);
        }
        korektaprzychodyopodatkowanezagranica26(podstawa,pasek);
        pasek.setKosztyuzyskania(kosztyuzyskania);
    }

    private static void obliczpodstaweopodatkowaniaZlecenie(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nierezydent) {
        Podatki pierwszyprog = stawkipodatkowe.get(0);
        double bruttominusspoleczne = pasek.getPrzychodypodatekpolska()-pasek.getSpoleczneudzialpolska();
        Rachunekdoumowyzlecenia rachunekdoumowyzlecenia = PasekwynagrodzenBean.pobierzRachunekzlecenie(pasek.getKalendarzmiesiac().getAngaz(), pasek.getKalendarzmiesiac().getRok(), pasek.getKalendarzmiesiac().getMc());
        if (rachunekdoumowyzlecenia==null) {
            rachunekdoumowyzlecenia = PasekwynagrodzenBean.pobierzRachunekdzielo(pasek.getKalendarzmiesiac().getAngaz(), pasek.getKalendarzmiesiac().getRok(), pasek.getKalendarzmiesiac().getMc());
        }
        //Rachunekdoumowyzlecenia rachunekdoumowyzlecenia =null;
        double dieta30proc = pasek.getDietaodliczeniepodstawaop();
        if (pasek.isPrzekroczenieoddelegowanie()) {
            pasek.setDietaodliczeniepodstawaop(0.0);
            dieta30proc = 0.0;
        }
        double podstawawstepna  = Z.z0(bruttominusspoleczne -  dieta30proc) > 0.0 ? Z.z0(bruttominusspoleczne - dieta30proc) : 0.0;
        pasek.setPodstawaopodatkowania(podstawawstepna);
        //to w zasadzie jest niepotrzebne bo mamy osobna kategorie w pasku juz przy obliczaniu brutto
        korektaprzychodyopodatkowanezagranicaZlecenie(podstawawstepna,pasek);
        double nowapodstawapl = pasek.getPodstawaopodatkowania();
        double procentkosztyuzyskania = rachunekdoumowyzlecenia.getProcentkosztowuzyskania();
        if (procentkosztyuzyskania==100.0) {
            procentkosztyuzyskania = 20.0;
        }
        double podstawadlakosztow = Z.z(nowapodstawapl) > 0.0 ? Z.z(nowapodstawapl) : 0.0;
        double kosztyuzyskania = Z.z(podstawadlakosztow * procentkosztyuzyskania / 100.0);
        //to jest niepotrzebne bo u gory pobiera tylko przychod do opodatkowania 13.11.2023
//        if (pasek.isDo26lat()) {
//            kosztyuzyskania = 0.0;
//        }
        if (pasek.isNierezydent()) {
            kosztyuzyskania = 0.0;
        }
        double podstawa = Z.z0(nowapodstawapl - kosztyuzyskania) > 0.0 ? Z.z0(nowapodstawapl - kosztyuzyskania) : 0.0;
        pasek.setPodstawaopodatkowania(podstawa);
        if (nierezydent) {
            pasek.setKosztyuzyskania(0.0);
        } else {
            pasek.setKosztyuzyskania(kosztyuzyskania);
        }
    }
    
//    private static void obliczpodstaweopodatkowaniaZlecenie26(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nierezydent) {
//        Podatki pierwszyprog = stawkipodatkowe.get(0);
//        double bruttominusspoleczne = pasek.getBruttominusspoleczne();
//        Rachunekdoumowyzlecenia rachunekdoumowyzlecenia = PasekwynagrodzenBean.pobierzRachunekzlecenie(pasek.getKalendarzmiesiac().getAngaz(), pasek.getKalendarzmiesiac().getRok(), pasek.getKalendarzmiesiac().getMc());
//        if (rachunekdoumowyzlecenia==null) {
//            rachunekdoumowyzlecenia = PasekwynagrodzenBean.pobierzRachunekdzielo(pasek.getKalendarzmiesiac().getAngaz(), pasek.getKalendarzmiesiac().getRok(), pasek.getKalendarzmiesiac().getMc());
//        }
//        //Rachunekdoumowyzlecenia rachunekdoumowyzlecenia =null;
//        double dieta30proc = pasek.getDietaodliczeniepodstawaop();
//        if (pasek.isPrzekroczenieoddelegowanie()) {
//            pasek.setDietaodliczeniepodstawaop(0.0);
//            dieta30proc = 0.0;
//        }
//        double podstawawstepna  = Z.z0(bruttominusspoleczne -  dieta30proc) > 0.0 ? Z.z0(bruttominusspoleczne - dieta30proc) : 0.0;
//        pasek.setPodstawaopodatkowania(podstawawstepna);
//        korektaprzychodyopodatkowanezagranicaZlecenie(podstawawstepna,pasek);
//        double nowapodstawapl = pasek.getPodstawaopodatkowania();
//        double procentkosztyuzyskania = rachunekdoumowyzlecenia.getProcentkosztowuzyskania();
//        double podstawadlakosztow = Z.z(nowapodstawapl) > 0.0 ? Z.z(nowapodstawapl) : 0.0;
//        double kosztyuzyskania = Z.z(podstawadlakosztow * procentkosztyuzyskania / 100.0);
//        if (pasek.isDo26lat()&&pasek.getBruttozus()==0.0) {
//            kosztyuzyskania = 0.0;
//        }
//        if (pasek.isNierezydent()) {
//            kosztyuzyskania = 0.0;
//        }
//        double podstawa = Z.z0(nowapodstawapl - kosztyuzyskania) > 0.0 ? Z.z0(nowapodstawapl - kosztyuzyskania) : 0.0;
//        pasek.setPodstawaopodatkowania(podstawa);
//        if (nierezydent) {
//            pasek.setKosztyuzyskania(0.0);
//        } else {
//            pasek.setKosztyuzyskania(kosztyuzyskania);
//        }
//    }

    private static void obliczpodstaweopodatkowaniafunkcja(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nierezydent) {
        Podatki pierwszyprog = stawkipodatkowe.get(0);
        double bruttominusspoleczne = pasek.getBruttominusspoleczne();
        //Rachunekdoumowyzlecenia rachunekdoumowyzlecenia =null;
        double kosztyuzyskania = pierwszyprog.getKup();
        pasek.setProcentkosztow(100);
        double podstawadlakosztow = Z.z0(bruttominusspoleczne) > 0.0 ? Z.z0(bruttominusspoleczne) : 0.0;
        if (pasek.isDo26lat()) {
            kosztyuzyskania = 0.0;
        }
        double dieta30proc = pasek.getDietaodliczeniepodstawaop();
        double podstawa = Z.z0(bruttominusspoleczne - kosztyuzyskania - dieta30proc) > 0.0 ? Z.z0(bruttominusspoleczne - kosztyuzyskania - dieta30proc) : 0.0;
        pasek.setPodstawaopodatkowania(podstawa);
        korektaprzychodyopodatkowanezagranica(podstawa,pasek);
        if (nierezydent) {
            pasek.setKosztyuzyskania(0.0);
        } else {
            pasek.setKosztyuzyskania(kosztyuzyskania);
        }
    }

//    private static void obliczpodstaweopodatkowaniaFunkcja(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nierezydent) {
//        Podatki pierwszyprog = stawkipodatkowe.get(0);
//        double bruttominusspoleczne = pasek.getBruttominusspoleczne();
//        double kosztyuzyskania = 100;
//        double podstawa = Z.z0(bruttominusspoleczne-kosztyuzyskania) > 0.0 ? Z.z0(bruttominusspoleczne-kosztyuzyskania) :0.0;
//        pasek.setPodstawaopodatkowania(podstawa);
//        pasek.setKosztyuzyskania(kosztyuzyskania);
//        
//    }
    private static void obliczpodstaweopodatkowaniaZlecenieSymulacja(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nierezydent) {
        Podatki pierwszyprog = stawkipodatkowe.get(0);
        double bruttominusspoleczne = pasek.getBruttominusspoleczne();
        double procentkosztyuzyskania = 20.0;
        double podstawadlakosztow = Z.z0(bruttominusspoleczne) > 0.0 ? Z.z0(bruttominusspoleczne) : 0.0;
        double kosztyuzyskania = Z.z(podstawadlakosztow * procentkosztyuzyskania / 100);
        double dieta30proc = pasek.getDietaodliczeniepodstawaop();
        double podstawa = Z.z0(bruttominusspoleczne - kosztyuzyskania - dieta30proc) > 0.0 ? Z.z0(bruttominusspoleczne - kosztyuzyskania - dieta30proc) : 0.0;
        pasek.setPodstawaopodatkowania(podstawa);
        if (nierezydent) {
            pasek.setKosztyuzyskania(0.0);
        } else {
            pasek.setKosztyuzyskania(kosztyuzyskania);
        }
        pasek.setProcentkosztow(100);

    }

    private static void obliczpodatekwstepny(Pasekwynagrodzen pasek) {
        double podatek = Z.z(Z.z0(pasek.getPodstawaopodatkowania()) * 0.17);
        pasek.setPodatekwstepny(podatek);
    }

    private static void obliczpodatekwstepnyDB26lat(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, double sumapoprzednich, double limit26) {
        double podstawaopodatkowania = pasek.getPodstawaopodatkowaniahipotetyczna();
        double podstawaopodatkowaniaPodatek = pasek.getPodstawaopodatkowania();
        double podatek = Z.z(Z.z0(podstawaopodatkowania + podstawaopodatkowania) * stawkipodatkowe.get(0).getStawka());
        double drugiprog = stawkipodatkowe.get(0).getKwotawolnado();
        if (sumapoprzednich >= drugiprog) {
            podatek = Z.z(Z.z0(podstawaopodatkowania) * stawkipodatkowe.get(1).getStawka());
            pasek.setDrugiprog(true);
        } else if (sumapoprzednich < drugiprog) {
            double razemzbiezacym = sumapoprzednich + podstawaopodatkowania;
            if (razemzbiezacym > drugiprog) {
                double podatekdol = Z.z(Z.z0(drugiprog - sumapoprzednich) * stawkipodatkowe.get(0).getStawka());
                double podatekgora = Z.z(Z.z0(razemzbiezacym - drugiprog) * stawkipodatkowe.get(1).getStawka());
                podatek = podatekdol + podatekgora;
                pasek.setDrugiprog(true);
            } else {
                podatek = Z.z(Z.z0(podstawaopodatkowania) * stawkipodatkowe.get(0).getStawka());
            }
        }
        pasek.setPodatekwstepnyhipotetyczny(podatek);
        pasek.setPodatekwstepny(0.0);
        double sumadladrugiegoprogu = sumapoprzednich + podstawaopodatkowania - podstawaopodatkowaniaPodatek;
        if (podstawaopodatkowaniaPodatek > 0.0) {
            obliczpodatekwstepnyDB26Przekroczenie(pasek, podstawaopodatkowaniaPodatek, stawkipodatkowe, sumadladrugiegoprogu);
        }
    }

    private static void obliczpodatekwstepnyDB26Przekroczenie(Pasekwynagrodzen pasek, double podstawaopodatkowaniaponadlimit, List<Podatki> stawkipodatkowe, double sumapoprzednich) {
        double podatek = Z.z(Z.z0(podstawaopodatkowaniaponadlimit) * stawkipodatkowe.get(0).getStawka());
        double drugiprog = stawkipodatkowe.get(0).getKwotawolnado();
        if (sumapoprzednich >= drugiprog) {
            podatek = Z.z(Z.z0(podstawaopodatkowaniaponadlimit) * stawkipodatkowe.get(1).getStawka());
            pasek.setDrugiprog(true);
        } else if (sumapoprzednich < drugiprog) {
            double razemzbiezacym = sumapoprzednich + podstawaopodatkowaniaponadlimit;
            if (razemzbiezacym > drugiprog) {
                double podatekdol = Z.z(Z.z0(drugiprog - sumapoprzednich) * stawkipodatkowe.get(0).getStawka());
                double podatekgora = Z.z(Z.z0(razemzbiezacym - drugiprog) * stawkipodatkowe.get(1).getStawka());
                podatek = podatekdol + podatekgora;
                pasek.setDrugiprog(true);
            } else {
                podatek = Z.z(Z.z0(podstawaopodatkowaniaponadlimit) * stawkipodatkowe.get(0).getStawka());
            }
        }
        pasek.setPodatekwstepny(podatek);
    }

    private static void obliczpodatekwstepnyDBStandard(Pasekwynagrodzen pasek, double podstawaopodatkowania, List<Podatki> stawkipodatkowe, double sumapoprzednich) {
        double podatek = Z.z(Z.z0(podstawaopodatkowania) * stawkipodatkowe.get(0).getStawka());
        double drugiprog = stawkipodatkowe.get(0).getKwotawolnado();
        if (sumapoprzednich >= drugiprog) {
            podatek = Z.z(Z.z0(podstawaopodatkowania) * stawkipodatkowe.get(1).getStawka());
            pasek.setDrugiprog(true);
        } else if (sumapoprzednich < drugiprog) {
            double razemzbiezacym = sumapoprzednich + podstawaopodatkowania;
            if (razemzbiezacym > drugiprog) {
                double podatekdol = Z.z(Z.z0(drugiprog - sumapoprzednich) * stawkipodatkowe.get(0).getStawka());
                double podatekgora = Z.z(Z.z0(razemzbiezacym - drugiprog) * stawkipodatkowe.get(1).getStawka());
                podatek = podatekdol + podatekgora;
                pasek.setDrugiprog(true);
            } else {
                podatek = Z.z(Z.z0(podstawaopodatkowania) * stawkipodatkowe.get(0).getStawka());
            }
        }
        pasek.setPodatekwstepny(podatek);
    }
    
    

    private static void obliczpodatekwstepnyZlecenieDB(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nierezydent) {
        double podatek = Z.z(Z.z0(pasek.getPodstawaopodatkowania()) * stawkipodatkowe.get(0).getStawka());
        if (nierezydent) {
            podatek = Z.z(Z.z0(pasek.getBrutto()) * 0.2);
        } else if (pasek.isDo26lat()&&pasek.getPrzychodypodatekpolska()==0.0) {
            podatek = 0.0;
        }
        pasek.setPodatekwstepny(podatek);
    }

    private static void obliczpodatekwstepnyFunkcjaDB(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean nierezydent) {
        double podatek = Z.z(Z.z0(pasek.getPodstawaopodatkowania()) * stawkipodatkowe.get(0).getStawka());
        if (nierezydent) {
            podatek = Z.z(Z.z0(pasek.getBrutto()) * 0.2);
        } else if (pasek.isDo26lat()) {
            podatek = 0.0;
        }
        pasek.setPodatekwstepny(podatek);
    }

    private static void ulgapodatkowa(Pasekwynagrodzen pasek, boolean ulga) {
        double kwotawolna = 43.76;
        if (ulga && pasek.isDo26lat() == false) {
            double podatek = pasek.getPodatekwstepny();
            if (kwotawolna > podatek) {
                pasek.setKwotawolna(podatek);
            } else {
                pasek.setKwotawolna(kwotawolna);
            }

        } else {
            pasek.setKwotawolna(0.0);
        }
    }

    private static void ulgapodatkowaDB(Pasekwynagrodzen pasek, List<Podatki> stawkipodatkowe, boolean ignoruj26lat, double odliczonajuzkwotawolna) {
        double kwotawolna = stawkipodatkowe.get(0).getWolnamc() - odliczonajuzkwotawolna;
        double kwotawolnadlazdrowotnej = stawkipodatkowe.get(0).getWolnadlazdrowotnej();
        if (pasek.isDrugiprog()) {
            // wywalilem tu 29.11.2023 bo dziewczyny na czacie z urzedem ustalily ze mozna odejmowac
            //kwotawolna = 0.0;
            pasek.setKwotawolna(kwotawolna);
        }
        if (pasek.isNierezydent() && pasek.isPraca() == false) {
            kwotawolna = 0.0;
            pasek.setKwotawolna(kwotawolna);
        } else if (pasek.isDo26lat() == false || ignoruj26lat) {
            double podatek = pasek.getPodatekwstepny();
            if (kwotawolna > podatek) {
                pasek.setKwotawolna(podatek);
            } else {
                pasek.setKwotawolna(kwotawolna);
            }
        } else {
            pasek.setKwotawolnahipotetyczna(kwotawolna);
            pasek.setKwotawolna(0.0);
        }
        if (pasek.isDo26lat() == true && pasek.getPodatekwstepny() > 0.0) {
            double podatek = pasek.getPodatekwstepny();
            if (kwotawolna > podatek) {
                pasek.setKwotawolna(podatek);
            } else {
                pasek.setKwotawolna(kwotawolna);
            }
        }
        pasek.setKwotawolnadlazdrowotnej(kwotawolnadlazdrowotnej);
    }

    private static void naliczzdrowotaFunkcja(Pasekwynagrodzen pasek, boolean nierezydent, boolean praca) {

    }

    private static void naliczzdrowota(Pasekwynagrodzen pasek, boolean nierezydent, boolean praca, String umowakodzus) {
        boolean funkcja = pasek.isFunkcja();
        double podstawazdrowotna = Z.z(pasek.getPrzychodyzus52()-pasek.getRazemspolecznepracownik());
        if (pasek.getPodstawaskladkizus()<pasek.getPrzychodyzus51()) {
            podstawazdrowotna = pasek.getPodstawaskladkizus()-pasek.getRazemspolecznepracownik();
        }
        //usuwamy z podstawy zasilki chorobowe
        List<Naliczenienieobecnosc> nieobecnoscilist = pasek.getNaliczenienieobecnoscList();
        for (Naliczenienieobecnosc n : nieobecnoscilist) {
            if (n.getNieobecnosc().getSwiadczeniekodzus() != null && n.getNieobecnosc().getSwiadczeniekodzus().isZdrowotne() == false && !n.getNieobecnosc().getSwiadczeniekodzus().getRodzajnieobecnosci().getKod().equals("ZC")) {
                podstawazdrowotna = Z.z(podstawazdrowotna - n.getKwota());
            }
        }
        pasek.setPodstawaubezpzdrowotne(podstawazdrowotna);
        double zdrowotne = Z.z(podstawazdrowotna * 0.09);
        pasek.setPraczdrowotne(zdrowotne);
        double zdrowotneodliczane = Z.z(podstawazdrowotna * 0.0775);
        if (nierezydent) {
            pasek.setPraczdrowotnedoodliczenia(0.0);
            pasek.setPraczdrowotnedopotracenia(zdrowotneodliczane);
        } else {
            if (umowakodzus.equals("0511")) {
                pasek.setPraczdrowotne(0.0);
                pasek.setPraczdrowotnedoodliczenia(0.0);
                pasek.setPraczdrowotnedopotracenia(0.0);
            } else if (pasek.isDo26lat() && pasek.isPraca() == false && pasek.isStudent()) {
                zdrowotne = 0.0;
                zdrowotneodliczane = 0.0;
                pasek.setPraczdrowotne(zdrowotne);
                pasek.setPraczdrowotnedoodliczenia(0.0);
                pasek.setPraczdrowotnedopotracenia(0.0);
            } else if (pasek.isDo26lat() && pasek.isPraca() == false) {
                double limitdlazdrowotnej = Z.z(pasek.getPodstawaopodatkowania() * 0.17 - pasek.getKwotawolnadlazdrowotnej()) > 0.0 ? Z.z(pasek.getPodstawaopodatkowania() * 0.17 - pasek.getKwotawolnadlazdrowotnej()) : 0.0;
                if (Z.z(pasek.getKwotawolna()) > 0.0) {
                    zdrowotne = zdrowotne > limitdlazdrowotnej ? Z.z(limitdlazdrowotnej) : zdrowotne;
                    zdrowotneodliczane = zdrowotneodliczane > limitdlazdrowotnej ? Z.z(limitdlazdrowotnej) : zdrowotneodliczane;
                }
                pasek.setPraczdrowotne(zdrowotne);
                pasek.setPraczdrowotnedoodliczenia(0.0);
                pasek.setPraczdrowotnedopotracenia(0.0);
            } else if (pasek.isDo26lat()) {
                double podstawahipotetyczna = pasek.getPodstawaopodatkowania();
                if (podstawahipotetyczna>0.0) {
                    double limitdlazdrowotnej = Z.z(podstawahipotetyczna * 0.17 - pasek.getKwotawolnadlazdrowotnej()) > 0.0 ? Z.z(podstawahipotetyczna * 0.17 - pasek.getKwotawolnadlazdrowotnej()) : 0.0;
                    zdrowotne = zdrowotne > limitdlazdrowotnej ? Z.z(limitdlazdrowotnej) : zdrowotne;
                    zdrowotneodliczane = zdrowotneodliczane > limitdlazdrowotnej ? Z.z(limitdlazdrowotnej) : zdrowotneodliczane;
                }
                pasek.setPraczdrowotne(zdrowotne);
                zdrowotneodliczane = Integer.parseInt(pasek.getRokwypl()) < 2022 ? zdrowotneodliczane : 0.0;
                pasek.setPraczdrowotnedoodliczenia(zdrowotneodliczane);
                pasek.setPraczdrowotnedopotracenia(zdrowotne);
            } else {
                if (Integer.parseInt(pasek.getRokwypl()) < 2022) {
                    double podstawahipotetyczna = pasek.getBruttominusspolecznehipotetyczne()-pasek.getKosztyuzyskania();
                    double limitdlazdrowotnej = Z.z(podstawahipotetyczna * 0.17 - pasek.getKwotawolnadlazdrowotnej()) > 0.0 ? Z.z(podstawahipotetyczna * 0.17 - pasek.getKwotawolnadlazdrowotnej()) : 0.0;
                    //spawa
                    if (zdrowotneodliczane > limitdlazdrowotnej && Z.z(pasek.getKwotawolna()) > 0.0) {
                        pasek.setPraczdrowotne(limitdlazdrowotnej);
                        pasek.setPraczdrowotnedoodliczenia(limitdlazdrowotnej);
                        pasek.setPraczdrowotnedopotracenia(0.0);
                    } else {
                        pasek.setPraczdrowotnedoodliczenia(zdrowotneodliczane);
                        pasek.setPraczdrowotnedopotracenia(Z.z(zdrowotne - zdrowotneodliczane));
                    }
                } else {
                    if (praca) {
                        //to ograniczenie nie jest dla wsyzstkich jest tylko dla 26lat i emerytow nie pobierajacych emerytury
                        //double limitdlazdrowotnej = Z.z(pasek.getPodstawaopodatkowania() * 0.17 - pasek.getKwotawolnadlazdrowotnej()) > 0.0 ? Z.z(pasek.getPodstawaopodatkowania() * 0.17 - pasek.getKwotawolnadlazdrowotnej()) : 0.0;
//                        if (zdrowotne > limitdlazdrowotnej && Z.z(pasek.getKwotawolna()) > 0.0) {
//                            pasek.setPraczdrowotne(limitdlazdrowotnej);
//                            pasek.setPraczdrowotnedoodliczenia(0.0);
//                            pasek.setPraczdrowotnedopotracenia(limitdlazdrowotnej);
//                        } else {
//                            pasek.setPraczdrowotnedoodliczenia(0.0);
//                            pasek.setPraczdrowotnedopotracenia(Z.z(zdrowotne));
//                        }
// wracamy bo jednak to odliczenie jest dla wszystkich 2904.2023
                        double limitdlazdrowotnej = Z.z(pasek.getPodstawaopodatkowania() * 0.17 - pasek.getKwotawolnadlazdrowotnej()) > 0.0 ? Z.z(pasek.getPodstawaopodatkowania() * 0.17 - pasek.getKwotawolnadlazdrowotnej()) : 0.0;
                        //zmniana 29052023
                        //bylo if (zdrowotne > limitdlazdrowotnej && Z.z(pasek.getKwotawolna()) > 0.0) {
                       if (zdrowotne > limitdlazdrowotnej && pasek.isDo26lat()==false && pasek.isEmeryt()==false) {
                            pasek.setPraczdrowotne(limitdlazdrowotnej);
                            pasek.setPraczdrowotnedoodliczenia(0.0);
                            pasek.setPraczdrowotnedopotracenia(limitdlazdrowotnej);
                        } else {
                            pasek.setPraczdrowotnedoodliczenia(0.0);
                            pasek.setPraczdrowotnedopotracenia(Z.z(zdrowotne));
                        }
                    } else if (funkcja) {
                        podstawazdrowotna = pasek.getPrzychodyzus52();
                        zdrowotne = Z.z(podstawazdrowotna * 0.09);
                        pasek.setPraczdrowotne(zdrowotne);
                        pasek.setPraczdrowotnedoodliczenia(0.0);
                        pasek.setPraczdrowotnedopotracenia(Z.z(zdrowotne));
                    } else {
                        pasek.setPraczdrowotnedoodliczenia(0.0);
                        pasek.setPraczdrowotnedopotracenia(Z.z(zdrowotne));
                    }
                }
            }
        }
        //trzeba zrobic tez inne opcje
    }

    private static void obliczpodatekdowplaty(Pasekwynagrodzen pasek) {
        double podateknetto = Z.z0(pasek.getPodatekwstepny() - pasek.getPraczdrowotnedoodliczenia() - pasek.getKwotawolna());
        pasek.setPodatekdochodowy(podateknetto < 0.0 ? 0.0 : podateknetto);
    }

    public static void potracenia(Pasekwynagrodzen pasek) {
        double potracenia = 0.0;
        for (Naliczeniepotracenie p : pasek.getNaliczeniepotracenieList()) {
            potracenia = Z.z(potracenia + p.getKwota());
        }
        pasek.setPotracenia(potracenia);
    }

    private static void netto(Pasekwynagrodzen pasek) {
        double wyliczenie = Z.z(pasek.getBrutto() - pasek.getRazemspolecznepracownik() - pasek.getPraczdrowotne() - pasek.getPodatekdochodowy());
        double ppk = 0.0;
        for (Naliczenieskladnikawynagrodzenia nal : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            if (nal.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod()!=null&&nal.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("98")) {
                ppk = ppk+nal.getKwotadolistyplac();
            }
        }
        //korekta o ppk
        wyliczenie = wyliczenie-ppk;
        double wyliczenieminuspodatekwaluta = Z.z(wyliczenie-pasek.getPodatekdochodowyzagranica());
        pasek.setNettoprzedpotraceniamisafe(wyliczenieminuspodatekwaluta < 0.0 ? 0.0 : wyliczenieminuspodatekwaluta);
        pasek.setNettoprzedpotraceniami(wyliczenieminuspodatekwaluta < 0.0 ? 0.0 : wyliczenieminuspodatekwaluta);
    }

    public static void dowyplaty(Pasekwynagrodzen pasek) {
        pasek.setNetto(Z.z(pasek.getNettoprzedpotraceniami() - pasek.getPotracenia()));
    }

    private static List<Nieobecnosc> pobierz(List<Nieobecnosc> nieobecnosci, String string) {
        List<Nieobecnosc> zwrot = new ArrayList<>();
        for (Nieobecnosc p : nieobecnosci) {
            if (p.getSwiadczeniekodzus() != null && p.getSwiadczeniekodzus().getKod().equals(string)) {
                zwrot.add(p);
            } else if (String.valueOf(p.getRodzajnieobecnosci().getKod()).equals(string)) {
                zwrot.add(p);
            }
        }
        return zwrot;
    }

    public static void usunpasekjeslijest(Pasekwynagrodzen selected, PasekwynagrodzenFacade pasekwynagrodzenFacade) {
        Pasekwynagrodzen jesttaki = null;
        try {
            jesttaki = pasekwynagrodzenFacade.findByDefKal(selected.getDefinicjalistaplac(), selected.getKalendarzmiesiac());
            if (jesttaki != null) {
                pasekwynagrodzenFacade.remove(jesttaki);
            }
        } catch (Exception e) {
            System.out.println(E.e(e));
        }
    }

    private static List<Nieobecnosc> pobierznieobecnosci(Kalendarzmiesiac kalendarz, List<Nieobecnosc> nieobecnosci) {
        String rok = kalendarz.getRok();
        String mc = kalendarz.getMc();
        boolean jestod = false;
        boolean jestdo = false;
        List<Nieobecnosc> zwrot = new ArrayList<>();
        for (Nieobecnosc p : nieobecnosci) {
            jestod = Data.czydatajestwmcu(p.getDataod(), rok, mc);
            jestdo = Data.czydatajestwmcu(p.getDatado(), rok, mc);
            if ((jestod || jestdo) && p.isNaniesiona()) {
                zwrot.add(p);
            }
        }
        return zwrot;
    }

    public static List<Nieobecnosc> rozpoczecieumowywtrakcieMiesiaca(Angaz angaz, String dataod, String datado, RodzajnieobecnosciFacade rodzajnieobecnosciFacade, String rok, String mc, Kalendarzmiesiac kalendarzmiesiac, String dataostatniejumowy) {
        List<Nieobecnosc> zwrotlist = new ArrayList<>();
        Nieobecnosc zwrot = new Nieobecnosc();
        String rokumowa = Data.getRok(dataod);
        String mcumowa = Data.getMc(dataod);
        String dzienumowa = Data.getDzien(dataod);
        if (rokumowa.equals(rok) && mcumowa.equals(mc) && !dzienumowa.equals("01")) {
            Rodzajnieobecnosci nieobecnosckodzus = rodzajnieobecnosciFacade.findByKod("D");
            zwrot = new Nieobecnosc();
            zwrot.setAngaz(angaz);
            zwrot.setRodzajnieobecnosci(nieobecnosckodzus);
            String pierwszydzienmca = Data.pierwszyDzien(dataod);
            if (dataostatniejumowy != null) {
                LocalDate pierwszydzienmcald = LocalDate.parse(dataostatniejumowy);
                LocalDate tumorow = pierwszydzienmcald.plusDays(1);
                pierwszydzienmca = tumorow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            }
            LocalDate pierwszydzienumowy = LocalDate.parse(dataod);
            LocalDate yesterday = pierwszydzienumowy.minusDays(1);
            String dzienprzedumowa = yesterday.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            zwrot.setDataod(pierwszydzienmca);
            zwrot.setDatado(dzienprzedumowa);
            zwrot.setDnikalendarzowe(Data.iletodniKalendarzowych(zwrot.getDataod(), zwrot.getDatado()));
            zwrot.setDniroboczenieobecnosci(Data.iletodniRoboczych(zwrot.getDataod(), zwrot.getDatado(), kalendarzmiesiac.getDzienList()));
            zwrot.setRokod(Data.getRok(zwrot.getDataod()));
            zwrot.setRokdo(Data.getRok(zwrot.getDatado()));
            zwrot.setMcod(Data.getMc(zwrot.getDataod()));
            zwrot.setMcdo(Data.getMc(zwrot.getDatado()));
            zwrotlist.add(zwrot);
        }
        if (datado != null && !datado.equals("")) {
            String rokumowado = Data.getRok(datado);
            String mcumowado = Data.getMc(datado);
            String dzienumowado = Data.getDzien(datado);
            String ostatnidzienmca = Data.getDzien(Data.ostatniDzien(rokumowado, mcumowado));
            if (rokumowado.equals(rok) && mcumowado.equals(mc) && !dzienumowado.equals(ostatnidzienmca)) {
                Rodzajnieobecnosci nieobecnosckodzus = rodzajnieobecnosciFacade.findByKod("D");
                zwrot = new Nieobecnosc();
                zwrot.setAngaz(angaz);
                zwrot.setRodzajnieobecnosci(nieobecnosckodzus);
                LocalDate ostatnidzienumowy = LocalDate.parse(datado);
                LocalDate tomorrow = ostatnidzienumowy.plusDays(1);
                String dzienpoumowie = tomorrow.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                zwrot.setDataod(dzienpoumowie);
                zwrot.setDatado(Data.ostatniDzien(rokumowado, mcumowado));
                zwrot.setDnikalendarzowe(Data.iletodniKalendarzowych(zwrot.getDataod(), zwrot.getDatado()));
                zwrot.setDniroboczenieobecnosci(Data.iletodniRoboczych(zwrot.getDataod(), zwrot.getDatado(), kalendarzmiesiac.getDzienList()));
                zwrot.setRokod(Data.getRok(zwrot.getDataod()));
                zwrot.setRokdo(Data.getRok(zwrot.getDatado()));
                zwrot.setMcod(Data.getMc(zwrot.getDataod()));
                zwrot.setMcdo(Data.getMc(zwrot.getDatado()));
                zwrotlist.add(zwrot);
            }
        }
        return zwrotlist;
    }

    public static Pasekwynagrodzen sumujpaski(List<Pasekwynagrodzen> lista) {
        Pasekwynagrodzen sumapasek = new Pasekwynagrodzen();
        sumapasek.setKalendarzmiesiac(new Kalendarzmiesiac());
        sumapasek.getKalendarzmiesiac().setAngaz(new Angaz());
        sumapasek.getKalendarzmiesiac().getAngaz().setPracownik(new Pracownik("podsumowanie", " "));
        for (Pasekwynagrodzen p : lista) {
            sumapasek.dodajPasek(p);
        }
        return sumapasek;
    }

    //diety rozlicza sie dwa razy, raz obnizaja zus raz dochodowy
    private static void wyliczpodstaweZUSlimit(Kalendarzmiesiac kalendarz, Pasekwynagrodzen pasek, double kurs, double limitZUS) {
        double sumawynagrodzen = pasek.getPrzychodyzus51();
        double sumawynagrodzenpopotraceniudiet = sumawynagrodzen - pasek.getDieta() > -0.0 ? Z.z(sumawynagrodzen - pasek.getDieta()) : 0.0;
        double kwotabezzus = 0.0;
        if (sumawynagrodzen > limitZUS) {
            if (sumawynagrodzenpopotraceniudiet < limitZUS) {
                kwotabezzus = Z.z(sumawynagrodzen - limitZUS);
            } else {
                kwotabezzus = Z.z(pasek.getDieta());
            }
        }
        pasek.setLimitzus(limitZUS);
        pasek.setLimitzuspoza(kwotabezzus);
        pasek.setPodstawaskladkizus(sumawynagrodzen - kwotabezzus);
    }

    private static double sumujwynagrodzenia(List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList) {
        double suma = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : naliczenieskladnikawynagrodzeniaList) {
            //to trzeba bedzie zmienic!!!!! bo nie ma polksiego wyn
            if (p.getKwotadolistyplac() != 0.0) {
                suma = suma + p.getKwotadolistyplac();
            } else {
                suma = suma + p.getKwotaumownazacalymc();
            }
        }
        return suma;
    }

    private static void obliczdietedoodliczenia(Pasekwynagrodzen pasek, Kalendarzmiesiac kalendarz) {
            List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList = kalendarz.getAngaz().getSkladnikwynagrodzeniaList();
            double dnioddelegowanie = 0.0;
            double dietawaluta = 0.0;
            for (Dzien p : kalendarz.getDzienList()) {
                if (p.getKod() != null && p.getKod().equals("Z")) {
                    dnioddelegowanie++;
                    dietawaluta = dietawaluta + p.getNieobecnosc().getDietaoddelegowanie();
                }
            }
            double dietypln = Z.z(dietawaluta * pasek.getKurs());
            if (dnioddelegowanie==0.0) {
                List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList2 = kalendarz.getAngaz().getSkladnikwynagrodzeniaList();
                for (Skladnikwynagrodzenia skl : skladnikwynagrodzeniaList2) {
                    if (skl.getRodzajwynagrodzenia().getKod().equals("52")) {
                        List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList = skl.getZmiennawynagrodzeniaList();
                        for (Zmiennawynagrodzenia r : zmiennawynagrodzeniaList) {
                            int dzienodzmienna = DataBean.dataod(r.getDataod(), kalendarz.getRok(), kalendarz.getMc());
                            int dziendozmienna = DataBean.datado(r.getDatado(), kalendarz.getRok(), kalendarz.getMc());
                            if (DataBean.czysiemiesci(kalendarz.getPierwszyDzien(), kalendarz.getOstatniDzien(), r.getDataod(), r.getDatado())) {
                                dietypln = Z.z(r.getKwota());
                            }
                        }
                    }
                }
            }

                pasek.setDietawaluta(dietawaluta);
                pasek.setDieta(dietypln);
            if (pasek.isPraca()) {
                if (skladnikwynagrodzeniaList.isEmpty()==false) {
                    skladnikwynagrodzeniaList = skladnikwynagrodzeniaList.stream().filter(p->p.getRodzajwynagrodzenia().getKod().equals("G1")).collect(Collectors.toList());
                }
                if (skladnikwynagrodzeniaList.isEmpty()==false) {
                    //pobieranie diety dla kierowcow wg nowych zasad od 2023
                    double dietyplnkierowca = 0.0;
                    for (Skladnikwynagrodzenia skl : skladnikwynagrodzeniaList) {
                        if (skl.getRodzajwynagrodzenia().getKod().equals("G1")) {
                            List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList = skl.getZmiennawynagrodzeniaList();
                            for (Zmiennawynagrodzenia r : zmiennawynagrodzeniaList) {
                                if (DataBean.czysiemiesci(kalendarz.getPierwszyDzien(), kalendarz.getOstatniDzien(), r.getDataod(), r.getDatado())) {
                                    dietyplnkierowca = Z.z(r.getKwota());
                                }
                            }
                        }
                    }
                    pasek.setDietaodliczeniepodstawaop(dietyplnkierowca);
                } else {
                    double dietyplnpodatek = Z.z(dietypln * 0.3);
                    pasek.setDietaodliczeniepodstawaop(dietyplnpodatek);
                }
            }
    }

    private static void naniesrobocze(Pasekwynagrodzen pasek, Kalendarzmiesiac kalendarz) {
        int[] robocze = kalendarz.robocze();
        pasek.setDniobowiazku(robocze[0]);
        pasek.setDniprzepracowane(robocze[1]);
    }

    private static void doliczbezzusbezpodatek(Pasekwynagrodzen pasek) {
        double bruttobezzusbezpodatek = 0.0;
        for (Naliczenieskladnikawynagrodzenia p : pasek.getNaliczenieskladnikawynagrodzeniaList()) {
            //bruttobezzusbezpodatek = Z.z(bruttobezzusbezpodatek+p.getKwotabezzusbezpodatek());
        
        pasek.setNettoprzedpotraceniamisafe(Z.z(pasek.getNettoprzedpotraceniami()) + bruttobezzusbezpodatek);}
        pasek.setNettoprzedpotraceniami(Z.z(pasek.getNettoprzedpotraceniami()) + bruttobezzusbezpodatek);
    }

    public static double sumapodstawaopodpopmce(PasekwynagrodzenFacade pasekwynagrodzenFacade, Kalendarzmiesiac p, double prog, String rokwyplaty) {
        List<Pasekwynagrodzen> paskipodatnika = pasekwynagrodzenFacade.findByRokWyplAngaz(rokwyplaty, p.getAngaz());
        double suma = 0.0;
        int mckalendarza = p.getMcI();
        int rokkalendarza = Integer.parseInt(rokwyplaty);
        for (Pasekwynagrodzen r : paskipodatnika) {
            if (r.getMcwyplI() <= mckalendarza || r.getRokI() < rokkalendarza) {
                suma = suma + r.getPodstawaopodatkowania();
            }
        }
        return suma;
    }

    public static double sumabruttopodstawaopodpopmce(PasekwynagrodzenFacade pasekwynagrodzenFacade, String rokwyplaty, String mcwyplaty, Angaz angaz) {
        List<Pasekwynagrodzen> paskipodatnika = pasekwynagrodzenFacade.findByRokWyplAngaz(rokwyplaty, angaz);
        double suma = 0.0;
        int mckalendarza = Integer.parseInt(mcwyplaty);
        int rokkalendarza = Integer.parseInt(rokwyplaty);
        for (Pasekwynagrodzen r : paskipodatnika) {
            if (r.getMcI() < mckalendarza || r.getRokI() < rokkalendarza) {
                suma = suma + r.getBrutto();
                if (r.isPrzekroczenieoddelegowanie()) {
                    suma = suma - sumujoddelegowaniewartosc(r);
                }
            }
        }
        return suma;
    }
    
     public static double sumabruttopolskaopodpopmce(PasekwynagrodzenFacade pasekwynagrodzenFacade, String rokwyplaty, String mcwyplaty, Angaz angaz) {
        List<Pasekwynagrodzen> paskipodatnika = pasekwynagrodzenFacade.findByRokWyplAngaz(rokwyplaty, angaz);
        double suma = 0.0;
        int mckalendarza = Integer.parseInt(mcwyplaty);
        
        for (Pasekwynagrodzen r : paskipodatnika) {
            if (r.getMcI() < mckalendarza) {
                suma = suma + r.getPrzychodypodatekpolska();
            }
        }
        return suma;
    }

    private static double sumujoddelegowaniewartosc(Pasekwynagrodzen r) {
        double suma = 0.0;
        List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList = r.getNaliczenieskladnikawynagrodzeniaList();
        for (Naliczenieskladnikawynagrodzenia nal : naliczenieskladnikawynagrodzeniaList) {
            if (nal.getWaluta()!=null&&nal.getWaluta().equals("EUR")) {
                suma = suma + nal.getKwotadolistyplac();
            }
        }
        List<Naliczenienieobecnosc> naliczenienieobecnoscList = r.getNaliczenienieobecnoscList();
        for (Naliczenienieobecnosc nal : naliczenienieobecnoscList) {
            if (nal.getWaluta()!=null&&nal.getWaluta().equals("EUR")) {
                suma = suma + nal.getKwota();
            }
        }
        return suma;
    }
    
    public static double czyodliczonokwotewolna(String rok, String mc, Angaz angaz, PasekwynagrodzenFacade pasekwynagrodzenFacade) {
        double zwrot = 0.0;
        List<Pasekwynagrodzen> innepaskiwtymmiesiacu = pasekwynagrodzenFacade.findByRokMcAngaz(rok, mc, angaz);
        if (innepaskiwtymmiesiacu != null) {
            for (Pasekwynagrodzen p : innepaskiwtymmiesiacu) {
                zwrot = zwrot + p.getKwotawolna();
            }
        }
        if (angaz.isOdliczaculgepodatkowa()==false) {
            zwrot = 300;
        }
        return zwrot;
    }

    private static void wyliczpodstaweZUS(Pasekwynagrodzen pasek) {
        pasek.setPodstawaskladkizus(pasek.getPrzychodyzus51());
    }

    private static boolean obliczczynierezydent(Umowa umowa, String termwyplaty) {
        boolean zwrot = false;
        if (umowa.getAngaz().getDataprzyjazdudopolski() != null && !umowa.getAngaz().getDataprzyjazdudopolski().equals("")) {
            String dataprzyjazdu = umowa.getAngaz().getDataprzyjazdudopolski();
            LocalDate dataprzyj = LocalDate.parse(dataprzyjazdu);
            LocalDate datawypl = LocalDate.parse(termwyplaty);
            String rok = Data.getRok(termwyplaty);
            String pierwszydzienroku = rok + "-01-01";
            LocalDate datapoczrok = LocalDate.parse(pierwszydzienroku);
            if (dataprzyj.isBefore(datapoczrok)) {
                dataprzyj = datapoczrok;
            }
            long dni = ChronoUnit.DAYS.between(dataprzyj, datawypl);
            if (dni < 183) {
                zwrot = true;
            }
        }
        return zwrot;
    }

//    Za miesiące, w których podatnik uzyskał przychody w wysokości wynoszącej od 5 701 zł do 11 141 zł miesięcznie, 
//    które podlegają opodatkowaniu według skali, pomniejsza się dochód o kwotę ulgi dla 
//    pracowników w każdym miesiącu w wysokości obliczonej według wzoru:
//    (A x 6,68% – 380,50 zł) ÷ 0,17, dla A wynoszącego co najmniej 5 701 zł i nieprzekraczającego kwoty 8 549 zł,
//    (A x (-7,35%) + 819,08 zł) ÷ 0,17, dla A wyższego od 8 549 zł i nieprzekraczającego kwoty 11 141 zł.
    private static void uwzglednijulgeklasasrednia(Pasekwynagrodzen pasek) {
        double kwotaprzychodu = Z.z(pasek.getBrutto());
        double kwotaulgi = 0.0;
        if (kwotaprzychodu >= 5701.0 && kwotaprzychodu <= 8549.0) {
            kwotaulgi = Z.z((Z.z(kwotaprzychodu * 0.0668) - 380.50) / 0.17);
            pasek.setUlgadlaklasysredniejI(kwotaulgi);
        } else if (kwotaprzychodu > 8549.0 && kwotaprzychodu <= 11141) {
            kwotaulgi = Z.z((Z.z(kwotaprzychodu * (-0.0735)) + 819.08) / 0.17);
            pasek.setUlgadlaklasysredniejII(kwotaulgi);
        }
    }

    public static Rachunekdoumowyzlecenia pobierzRachunekzlecenie(Angaz angaz, String rok, String mc) {
        Rachunekdoumowyzlecenia zwrot = null;
        try {
            Umowa umowa = angaz.pobierzumowaZlecenia(rok, mc);
            if (umowa!=null) {
                List<Rachunekdoumowyzlecenia> rachunekdoumowyzleceniaList = umowa.getRachunekdoumowyzleceniaList();
                if (rachunekdoumowyzleceniaList != null) {
                    zwrot = rachunekdoumowyzleceniaList.stream().filter(pa -> pa.getMc().equals(mc) && pa.getRok().equals(rok)).findAny().get();
                }
            }
        } catch (Exception e) {
        }
        return zwrot;
    }
    
    public static Rachunekdoumowyzlecenia pobierzRachunekdzielo(Angaz angaz, String rok, String mc) {
        Rachunekdoumowyzlecenia zwrot = null;
        try {
            Umowa umowa = angaz.pobierzumowaDzielo(rok, mc);
            if (umowa!=null) {
                List<Rachunekdoumowyzlecenia> rachunekdoumowyzleceniaList = umowa.getRachunekdoumowyzleceniaList();
                if (rachunekdoumowyzleceniaList != null) {
                    zwrot = rachunekdoumowyzleceniaList.stream().filter(pa -> pa.getMc().equals(mc) && pa.getRok().equals(rok)).findAny().get();
                }
            }
        } catch (Exception e) {
        }
        return zwrot;
    }

    private static boolean czyprzekroczonowiek(Kalendarzmiesiac kalendarz, String datalisty) {
        boolean takprzekroczonowiek = false;
        boolean mezczyzna = pobierzplec(kalendarz.getAngaz().getPracownik());
        LocalDate dataurodzin = Data.stringToLocalDate(kalendarz.getPracownik().getDataurodzenia());
        int lata = mezczyzna ? 60 : 55;
        LocalDate ukonczeniewieku = dataurodzin.plus(lata, ChronoUnit.YEARS);
        LocalDate datalisty1 = Data.stringToLocalDate(datalisty);
        if (ukonczeniewieku.getYear() < datalisty1.getYear()) {
            takprzekroczonowiek = true;
        } else if (ukonczeniewieku.getYear() == datalisty1.getYear()) {
            if (ukonczeniewieku.getMonthValue() < datalisty1.getMonthValue()) {
                takprzekroczonowiek = true;
            }
        }
        return takprzekroczonowiek;
    }

    private static boolean pobierzplec(Pracownik pracownik) {
        boolean mezczyzna = true;
        if (pracownik.getPlec() != null && pracownik.getPlec().equals("K")) {
            mezczyzna = false;
        }
        return mezczyzna;
    }

    private static boolean czyponizejminimalnego(Pasekwynagrodzen pasek, double wynagrodzenieminimalne) {
        boolean czyjestponizejminimalnego = false;
        if (pasek.getPodstawaskladkizus() < wynagrodzenieminimalne) {
            czyjestponizejminimalnego = true;
        }
        return czyjestponizejminimalnego;
    }

    private static boolean czysatylkozlecenia(Kalendarzmiesiac kalendarz) {
        boolean czysatylkozlecenia = true;
        try {
            FirmaKadry firma = kalendarz.getAngaz().getFirma();
            if (firma.isOsobafizyczna() == true) {
                List<Angaz> angazewFirmie = firma.getAngazList();
                for (Angaz a : angazewFirmie) {
                    Umowa u = a.getAktywnaUmowa();
                    if (u!=null&&u.getUmowakodzus().isPraca()) {
                        czysatylkozlecenia = false;
                        break;
                    }
                }
            } else {
                czysatylkozlecenia = false;
            }
        } catch (Exception e){
            czysatylkozlecenia = false;
            System.out.println(E.e(e));
        }
        return czysatylkozlecenia;
    }

    public static void przelicznawalute(Pasekwynagrodzen pasek) {
        double kurs = pasek.getKurs();
        double netto = pasek.getNetto();
        double kwotawwalucie = Z.z(netto / kurs);
        pasek.setNettowaluta(kwotawwalucie);
        pasek.setSymbolwaluty("EUR");
    }
    
    
    
    private static void korektaprzychodyopodatkowanezagranicaZlecenie(double podstawa, Pasekwynagrodzen pasek) {
        try {
            if (pasek.isPrzekroczenieoddelegowanie()) {
                List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList = pasek.getNaliczenieskladnikawynagrodzeniaList();
                double zagranicapln = 0.0;
                double zagranicawaluta = 0.0;
                for (Naliczenieskladnikawynagrodzenia p : naliczenieskladnikawynagrodzeniaList) {
                    if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("13")||p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getWks_serial().equals(1072)) {
                        zagranicawaluta = zagranicawaluta + p.getKwotadolistyplacwaluta();
                        zagranicapln = zagranicapln + p.getKwotadolistyplac();
                    }
                }
                List<Naliczenienieobecnosc> naliczenienieobecnoscList = pasek.getNaliczenienieobecnoscList();
                for (Naliczenienieobecnosc p : naliczenienieobecnoscList) {
                    if (p.getNieobecnosc().getRodzajnieobecnosci().getKod().equals("UD")) {
                        zagranicawaluta = zagranicawaluta + p.getKwotawaluta();
                        zagranicapln = zagranicapln + p.getKwota();
                    }
                }
                pasek.setPodstawaopodatkowaniazagranicawaluta(Z.z(zagranicawaluta));
                pasek.setPodstawaopodatkowaniazagranica(Z.z(zagranicapln));
//                if (pasek.getPodstawaopodatkowania()>0.0) {
//                    double nowapodstawapolska = Z.z0(pasek.getPodstawaopodatkowania()-zagranicapln) > 0.0? Z.z0(pasek.getPodstawaopodatkowania()-zagranicapln) : 0.0;
//                    pasek.setPodstawaopodatkowania(Z.z0(nowapodstawapolska));
//                }
            }
        } catch (Exception e){
            E.e(e);
        }
    }

    private static void korektaprzychodyopodatkowanezagranica(double podstawa, Pasekwynagrodzen pasek) {
        try {
            if (pasek.isPrzekroczenieoddelegowanie()) {
                List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList = pasek.getNaliczenieskladnikawynagrodzeniaList();
                double zagranicapln = 0.0;
                double zagranicawaluta = 0.0;
                for (Naliczenieskladnikawynagrodzenia p : naliczenieskladnikawynagrodzeniaList) {
                    if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("13")||p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getWks_serial().equals(1072)) {
                        zagranicawaluta = zagranicawaluta + p.getKwotadolistyplacwaluta();
                        zagranicapln = zagranicapln + p.getKwotadolistyplac();
                    }
                }
                List<Naliczenienieobecnosc> naliczenienieobecnoscList = pasek.getNaliczenienieobecnoscList();
                for (Naliczenienieobecnosc p : naliczenienieobecnoscList) {
                    if (p.getNieobecnosc().getRodzajnieobecnosci().getKod().equals("UD")) {
                        zagranicawaluta = zagranicawaluta + p.getKwotawaluta();
                        zagranicapln = zagranicapln + p.getKwota();
                    }
                }
                pasek.setPodstawaopodatkowaniazagranicawaluta(Z.z(zagranicawaluta));
                pasek.setPodstawaopodatkowaniazagranica(Z.z(zagranicapln));
//                if (pasek.getPodstawaopodatkowania()>0.0) {
//                    double nowapodstawapolska = Z.z0(pasek.getPodstawaopodatkowania()-zagranicapln) > 0.0? Z.z0(pasek.getPodstawaopodatkowania()-zagranicapln) : 0.0;
//                    pasek.setPodstawaopodatkowania(Z.z0(nowapodstawapolska));
//                }
            }
        } catch (Exception e){
            E.e(e);
        }
    }
    
    private static void korektaprzychodyopodatkowanezagranica26(double podstawa, Pasekwynagrodzen pasek) {
        try {
            if (pasek.isPrzekroczenieoddelegowanie()) {
                List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList = pasek.getNaliczenieskladnikawynagrodzeniaList();
                double zagranicapln = 0.0;
                double zagranicawaluta = 0.0;
                for (Naliczenieskladnikawynagrodzenia p : naliczenieskladnikawynagrodzeniaList) {
                    if (p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("13")||p.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getWks_serial().equals(1072)) {
                        zagranicawaluta = zagranicawaluta + p.getKwotadolistyplacwaluta();
                        zagranicapln = zagranicapln + p.getKwotadolistyplac();
                    }
                }
                List<Naliczenienieobecnosc> naliczenienieobecnoscList = pasek.getNaliczenienieobecnoscList();
                for (Naliczenienieobecnosc p : naliczenienieobecnoscList) {
                    if (p.getNieobecnosc().getRodzajnieobecnosci().getKod().equals("UD")) {
                        zagranicawaluta = zagranicawaluta + p.getKwotawaluta();
                        zagranicapln = zagranicapln + p.getKwota();
                    }
                }
                pasek.setPodstawaopodatkowaniazagranicawaluta(Z.z(zagranicawaluta));
                pasek.setPodstawaopodatkowaniazagranica(Z.z(zagranicapln));
//                if (pasek.getPodstawaopodatkowania()>0.0) {
//                    double nowapodstawapolska = Z.z0(pasek.getPodstawaopodatkowania()-zagranicapln) > 0.0? Z.z0(pasek.getPodstawaopodatkowania()-zagranicapln) : 0.0;
//                    pasek.setPodstawaopodatkowania(Z.z0(nowapodstawapolska));
//                }
            }
        } catch (Exception e){
            E.e(e);
        }
    }

    private static boolean czyjestpowrotzmacierzynskiego(List<Nieobecnosc> nieobecnoscList, String datawyplaty) {
        boolean jestpowrot = false;
        if (nieobecnoscList!=null) {
            String datakoncazwolnienia = null;
            
            for (Nieobecnosc nieob : nieobecnoscList) {
                if (nieob.getSwiadczeniekodzus()!=null&&(nieob.getSwiadczeniekodzus().getKod().equals("319")||nieob.getSwiadczeniekodzus().getKod().equals("121"))) {
                    if (nieob.getDatado()!=null&&datakoncazwolnienia==null) {
                        datakoncazwolnienia = nieob.getDatado();
                        //nie moze byc break bo sie zatryzma na poprzednim zwolnieniu sprzed roku
                        //break;
                    } else if (nieob.getDatado()!=null&&datakoncazwolnienia!=null) {
                        if (Data.czyjestpo(datakoncazwolnienia, nieob.getDatado())) {
                            datakoncazwolnienia = nieob.getDatado();
                        }
                    }
                }
            }
            if (datakoncazwolnienia!=null) {
                String datapowrotu = Data.dodajdzien(datakoncazwolnienia, 1);
                String data36mcy = Data.dodajmiesiac(datapowrotu, 36);
                jestpowrot = Data.czyjestpoTerminData(datawyplaty, data36mcy);
            }
        }
        return jestpowrot;
    }

    private static boolean czynienaliczacFPbezrobotny(Kalendarzmiesiac kalendarz) {
        boolean zwrot = false;
        String data = kalendarz.getAngaz().getDatabezrobotnyskierowanie();
        if (data!=null&&!data.equals("")) {
            String datalisty = Data.ostatniDzien(kalendarz.getRok(), kalendarz.getMc());
            String limit12mcy = Data.dodajmiesiac(data, 12);
            zwrot = Data.czyjestprzed(limit12mcy, datalisty);
        }
        return zwrot;
    }

    //21-12-2023 przy stalym zasadniczym suma zasadniczego zredukowanego i urlopow poinna rownac sie zasadniczemu. czasami jednak jest jakis grosz. to zalatwia sprawe
    private static void zaokraglijgrosze(Pasekwynagrodzen pasek) {
        List<Naliczenieskladnikawynagrodzenia> naliczenieskladnikawynagrodzeniaList = pasek.getNaliczenieskladnikawynagrodzeniaList();
        List<Naliczenienieobecnosc> naliczenienieobecnoscList = pasek.getNaliczenienieobecnoscList();
        if (naliczenienieobecnoscList.isEmpty()==false) {
            double brutto = 0.0;
            double wynagrodzeniezasadnicze = 0.0;
            boolean czyjest11 = false;
            Naliczenieskladnikawynagrodzenia nale = null;
            for (Naliczenieskladnikawynagrodzenia nal : naliczenieskladnikawynagrodzeniaList) {
                if (nal.getSkladnikwynagrodzenia().getRodzajwynagrodzenia().getKod().equals("11")) {
                    czyjest11 = true;
                    wynagrodzeniezasadnicze = nal.getKwotaumownazacalymc();
                    brutto = Z.z(nal.getKwotadolistyplac());
                    nale = nal;
                    break;
                }
            }
            boolean czyjesturlop = false;
            for (Naliczenienieobecnosc nieob : naliczenienieobecnoscList) {
                if (nieob.getNieobecnosc().getKod().equals("U")) {
                    brutto = brutto + Z.z(nieob.getKwota());
                    czyjesturlop = true;
                }
            }
            if (czyjest11&&czyjesturlop &&(brutto!=wynagrodzeniezasadnicze)) {
                double roznica = Z.z(Z.z(wynagrodzeniezasadnicze)-Z.z(brutto));
                nale.setKwotadolistyplac(nale.getKwotadolistyplac()+roznica);
            }
        }
        
    }

    

}
