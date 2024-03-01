/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import comparator.KalendarzmiesiacRMNormalcomparator;
import comparator.UmowaStareNowecomparator;
import data.Data;
import embeddable.Mce;
import entity.Angaz;
import entity.Dzien;
import entity.EkwiwalentUrlop;
import entity.EtatPrac;
import entity.Kalendarzmiesiac;
import entity.Nieobecnoscprezentacja;
import entity.Nieobecnoscwykorzystanie;
import entity.Rejestrurlopow;
import entity.Staz;
import entity.Umowa;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import msg.Msg;

/**
 *
 * @author Osito
 */
public class UrlopBean {
    //tu bede zliczal urlop ale bez ekwiwalentu 
     public static Nieobecnoscprezentacja pobierzurlop(Angaz angaz, String rok, String stannadzien, String dataDlaEtatu) {
         Nieobecnoscprezentacja urlopprezentacja = new Nieobecnoscprezentacja(angaz, rok);
        if (angaz!=null) {
            EtatPrac pobierzetat = EtatBean.pobierzetat(angaz,dataDlaEtatu);
            if (pobierzetat!=null) {
                List<Kalendarzmiesiac> kalendarze = angaz.getKalendarzmiesiacList().stream().filter(p->p.getRok().equals(rok)).collect(Collectors.toList());
                //wstawilem to tu bo dzieki temu zmodyfikuje dni w kalendarzach i oznacze je jako wykorzystanie urlopu z okresu poprzedniego
//                if (angaz.getRok().equals(rok)) {
//                    urlopprezentacja.setBilansotwarciagodziny(angaz.getBourlopgodziny());
//                    urlopprezentacja.setBilansotwarciadni(angaz.getBourlopdni());
//                } else if (angaz.getSerialsp()!=null&&rok.equals("2023")) {
//                    urlopprezentacja.setBilansotwarciagodziny(angaz.getBourlopgodziny());
//                    urlopprezentacja.setBilansotwarciadni(angaz.getBourlopdni());
//                }//w metodzie nanies dni z kodem uzulepniana jest zmienna urlopprezentacja.Wykorzystanierokbiezacy
                urlopprezentacja.getNieobecnoscwykorzystanieList().addAll(naniesdnizkodem(kalendarze, urlopprezentacja, "U"));
                urlopprezentacja.getNieobecnoscwykorzystanieList().addAll(naniesdnizkodem(kalendarze, urlopprezentacja, "UD"));
                List<Umowa> umowy = angaz.getUmowaList();
                Object[] obliczwymiarwgodzinach = obliczwymiarwgodzinach(umowy, pobierzetat, rok, stannadzien, angaz, kalendarze);
                //wymiar w trakcie roku przyjecia/zwolnienia
                int wymiarbiezacydni = (int) obliczwymiarwgodzinach[0];
                urlopprezentacja.setWymiarokresbiezacydni(wymiarbiezacydni);
                int wymiarbiezacygodziny = (wymiarbiezacydni*8*pobierzetat.getEtat2()/pobierzetat.getEtat1());
                urlopprezentacja.setWymiarokresbiezacygodziny(wymiarbiezacygodziny);
                urlopprezentacja.setWymiargeneralnydni((int) obliczwymiarwgodzinach[2]);
                urlopprezentacja.setListamiesiecy((Set<String>) obliczwymiarwgodzinach[3]);
                //urlopprezentacja.getWykorzystanierokbiezacy() uzupelnaije jest wyzej w funkcji naniesdnizkodem(kalendarze, urlopprezentacja, "U"))
                int wykorzystanierokbierzacydni  = (urlopprezentacja.getWykorzystanierokbiezacy()/8*pobierzetat.getEtat2()/pobierzetat.getEtat1());
                urlopprezentacja.setWykorzystanierokbiezacydni(wykorzystanierokbierzacydni);
                int doprzeniesienia = urlopprezentacja.getBilansotwarciagodziny()+urlopprezentacja.getWymiarokresbiezacygodziny()-urlopprezentacja.getWykorzystanierokbiezacy()-urlopprezentacja.getWykorzystanierokbiezacyekwiwalent();
                urlopprezentacja.setDoprzeniesienia(doprzeniesienia);
                int doprzeniesieniadni = (doprzeniesienia/8*pobierzetat.getEtat2()/pobierzetat.getEtat1());
                urlopprezentacja.setDoprzeniesieniadni(doprzeniesieniadni);
            } else {
                urlopprezentacja = new Nieobecnoscprezentacja();
            }
            //Msg.msg("Pobrano dane urlopowe");
        }
        return urlopprezentacja;
    }
     
     public static Nieobecnoscprezentacja pobierzurlopSwiadectwo(Angaz angaz, String rok, String stannadzien, String dataDlaEtatu, Rejestrurlopow rejestrurlopow, EkwiwalentUrlop ekwiwalentUrlop) {
         Nieobecnoscprezentacja urlopprezentacja = new Nieobecnoscprezentacja(angaz, rok);
        if (angaz!=null) {
            EtatPrac pobierzetat = EtatBean.pobierzetat(angaz,dataDlaEtatu);
            if (pobierzetat!=null) {
                List<Kalendarzmiesiac> kalendarze = angaz.getKalendarzmiesiacList().stream().filter(p->p.getRok().equals(rok)).collect(Collectors.toList());
                //wstawilem to tu bo dzieki temu zmodyfikuje dni w kalendarzach i oznacze je jako wykorzystanie urlopu z okresu poprzedniego
                urlopprezentacja.getNieobecnoscwykorzystanieList().addAll(naniesdnizkodem(kalendarze, urlopprezentacja, "U"));
                urlopprezentacja.getNieobecnoscwykorzystanieList().addAll(naniesdnizkodem(kalendarze, urlopprezentacja, "UD"));
                List<Umowa> umowy = angaz.getUmowaList();
                Object[] obliczwymiarwgodzinach = obliczwymiarwgodzinach(umowy, pobierzetat, rok, stannadzien, angaz, kalendarze);
                if (rejestrurlopow!=null) {
                    urlopprezentacja.setBilansotwarciadni(rejestrurlopow.getUrlopzalegly());
                    int urlopzaleglygodziny  = (rejestrurlopow.getUrlopzalegly()*8*pobierzetat.getEtat2()/pobierzetat.getEtat1());
                    urlopprezentacja.setBilansotwarciagodziny(urlopzaleglygodziny);
                } else {
                    Msg.msg("e","Brak rejestru urlopów dla pracownika");
                }
                urlopprezentacja.setWymiarokresbiezacydni((int) obliczwymiarwgodzinach[0]);
                urlopprezentacja.setWymiarokresbiezacygodziny((int) obliczwymiarwgodzinach[1]);
                urlopprezentacja.setWymiargeneralnydni((int) obliczwymiarwgodzinach[2]);
                urlopprezentacja.setListamiesiecy((Set<String>) obliczwymiarwgodzinach[3]);
                //urlopprezentacja.getWykorzystanierokbiezacy() to jest nanowszone w naniesdnizkodem
                int wykorzystanierokbiezacygodziny = urlopprezentacja.getWykorzystanierokbiezacy();
                int wykorzystanierokbierzacydni  = (wykorzystanierokbiezacygodziny/8*pobierzetat.getEtat2()/pobierzetat.getEtat1());
                urlopprezentacja.setWykorzystanierokbiezacydni(wykorzystanierokbierzacydni);
                int doprzeniesienia = urlopprezentacja.getBilansotwarciagodziny()+urlopprezentacja.getWymiarokresbiezacygodziny()-urlopprezentacja.getWykorzystanierokbiezacy()-urlopprezentacja.getWykorzystanierokbiezacyekwiwalent();
                urlopprezentacja.setDoprzeniesienia(doprzeniesienia);
                int doprzeniesieniadni = (doprzeniesienia/8*pobierzetat.getEtat2()/pobierzetat.getEtat1());
                urlopprezentacja.setDoprzeniesieniadni(doprzeniesieniadni);
                int ekwiwalentwyplaconygodziny = 0;
                if (ekwiwalentUrlop!=null) {
                    ekwiwalentwyplaconygodziny = ekwiwalentUrlop.getDni()*8*pobierzetat.getEtat2()/pobierzetat.getEtat1();
                }
                int limitdnizrokubiezacego = urlopprezentacja.getWymiarokresbiezacygodziny();
                int wykorzystaniewtymroku = wykorzystanierokbiezacygodziny+ekwiwalentwyplaconygodziny;
                int rozliczonegodzinyztegoroku = wykorzystaniewtymroku > limitdnizrokubiezacego? limitdnizrokubiezacego:wykorzystaniewtymroku;
                int doswiadectwagodziny = (rozliczonegodzinyztegoroku);
                urlopprezentacja.setDoswiadectwagodziny(doswiadectwagodziny);
                int doswiadectwadni = (doswiadectwagodziny/8*pobierzetat.getEtat2()/pobierzetat.getEtat1());
                urlopprezentacja.setDoswiadectwadni(doswiadectwadni);
                int rozliczonegodzinyztegorokuekwiwalent = ekwiwalentwyplaconygodziny>rozliczonegodzinyztegoroku?rozliczonegodzinyztegoroku:ekwiwalentwyplaconygodziny;
                int rozliczonedniztegorokuekwiwalent = (rozliczonegodzinyztegorokuekwiwalent/8*pobierzetat.getEtat2()/pobierzetat.getEtat1());
                urlopprezentacja.setDoswiadectwadniekwiwalent(rozliczonedniztegorokuekwiwalent);
                
            } else {
                urlopprezentacja = new Nieobecnoscprezentacja();
            }
            //Msg.msg("Pobrano dane urlopowe");
        }
        return urlopprezentacja;
    }
     
//     public static Nieobecnoscprezentacja pobierzurlopRokBiezacy(Angaz angaz, String rok, String stannadzien, String dataDlaEtatu) {
//         Nieobecnoscprezentacja urlopprezentacja = new Nieobecnoscprezentacja(angaz, rok);
//        if (angaz!=null) {
//            EtatPrac pobierzetat = EtatBean.pobierzetat(angaz,dataDlaEtatu);
//            if (pobierzetat!=null) {
//                List<Kalendarzmiesiac> kalendarze = angaz.getKalendarzmiesiacList().stream().filter(p->p.getRok().equals(rok)).collect(Collectors.toList());
//                urlopprezentacja.getNieobecnoscwykorzystanieList().addAll(naniesdnizkodem(kalendarze, urlopprezentacja, "U"));
//                urlopprezentacja.getNieobecnoscwykorzystanieList().addAll(naniesdnizkodem(kalendarze, urlopprezentacja, "UD"));
//                List<Umowa> umowy = angaz.getUmowaList();
//                if (angaz.getRok().equals(rok)) {
//                    urlopprezentacja.setBilansotwarciagodziny(angaz.getBourlopgodziny());
//                    urlopprezentacja.setBilansotwarciadni(angaz.getBourlopdni());
//                } else if (angaz.getSerialsp()!=null&&rok.equals("2023")) {
//                    urlopprezentacja.setBilansotwarciagodziny(angaz.getBourlopgodziny());
//                    urlopprezentacja.setBilansotwarciadni(angaz.getBourlopdni());
//                }
//                int[] obliczwymiarwgodzinach = obliczwymiarwgodzinach(umowy, pobierzetat, rok, stannadzien, angaz);
//                urlopprezentacja.setWymiarokresbiezacydni(obliczwymiarwgodzinach[0]);
//                urlopprezentacja.setWymiarokresbiezacygodziny(obliczwymiarwgodzinach[1]);
//                int doprzeniesienia = urlopprezentacja.getBilansotwarciagodziny()+urlopprezentacja.getWymiarokresbiezacygodziny()-urlopprezentacja.getWykorzystanierokbiezacy()-urlopprezentacja.getWykorzystanierokbiezacyekwiwalent();
//                urlopprezentacja.setDoprzeniesienia(doprzeniesienia);
//                int doprzeniesieniadni = (doprzeniesienia/8*pobierzetat.getEtat2()/pobierzetat.getEtat1());
//                urlopprezentacja.setDoprzeniesieniadni(doprzeniesieniadni);
//            }
//            //Msg.msg("Pobrano dane urlopowe");
//        }
//        return urlopprezentacja;
//    }
     
     
     //tego nie ruszamy bo to jest uniwersalne
     public static List<Nieobecnoscwykorzystanie> naniesdnizkodem(List<Kalendarzmiesiac> kalendarze, Nieobecnoscprezentacja urlopprezentacja, String kod) {
        List<Nieobecnoscwykorzystanie> lista = new ArrayList<>();
        Nieobecnoscwykorzystanie wykorzystaniesuma = new Nieobecnoscwykorzystanie("podsum.",0);
        Collections.sort(kalendarze, new KalendarzmiesiacRMNormalcomparator());
        for (Kalendarzmiesiac p : kalendarze) {
            for (Dzien r : p.getDzienList()) {
                if (r.getNieobecnosc()!=null&&r.getNieobecnosc().getRodzajnieobecnosci().getKod()!=null&&r.getKod()!=null) {
                    if (r.getNieobecnosc().getRodzajnieobecnosci().getKod().equals(kod)||r.getKod().equals(kod)&&r.getNieobecnosc().isNaniesiona()) {
                        Nieobecnoscwykorzystanie wykorzystanie = new Nieobecnoscwykorzystanie();
                        wykorzystanie.setMc(p.getMc());
                        wykorzystanie.setData(Data.zrobdate(r.getNrdnia(), p.getMc(), p.getRok()));
                        wykorzystanie.setDni(1);
                        wykorzystanie.setOpis(r.getNieobecnosc().getOpisRodzajSwiadczenie());
                        wykorzystanie.setKod(r.getNieobecnosc().getKod());
                        if (kod.equals("U")) {
                            wykorzystanie.setGodziny((int) r.getUrlopPlatny());
                        }
                        if (kod.equals("UD")) {
                            wykorzystanie.setGodziny((int) r.getUrlopPlatny());
                        }
                        if (kod.equals("CH")) {
                            wykorzystanie.setGodziny((int) r.getWynagrodzeniezachorobe());
                        }
                        if (kod.equals("ZC")||kod.equals("W")) {
                            wykorzystanie.setGodziny((int) r.getZasilek());
                        }
                        if (kod.equals("Z")) {
                            wykorzystanie.setGodziny((int) r.getPrzepracowano());
                        }
                        wykorzystanie.setUrlopprezentacja(urlopprezentacja);
                        EtatPrac pobierzetat = EtatBean.pobierzetat(p.getAngaz(),wykorzystanie.getData());
                        if (pobierzetat!=null) {
                            wykorzystanie.setEtat1(pobierzetat.getEtat1());
                            wykorzystanie.setEtat2(pobierzetat.getEtat2());
                        } else {
                            wykorzystanie.setEtat1(1);
                            wykorzystanie.setEtat2(1);
                        }
                        if (wykorzystanie.getGodziny()>0) {
                            wykorzystaniesuma.setGodziny(wykorzystaniesuma.getGodziny()+wykorzystanie.getGodziny());
                            wykorzystaniesuma.setDni(wykorzystaniesuma.getDni()+wykorzystanie.getDni());
                            lista.add(wykorzystanie);
                        }
                    }
                }
            }
        }
        if (kod.equals("U")||kod.equals("UD")) {
            urlopprezentacja.setWykorzystanierokbiezacy(urlopprezentacja.getWykorzystanierokbiezacy()+wykorzystaniesuma.getGodziny());
        }
        if (kod.equals("CH")) {
            urlopprezentacja.setWykorzystanierokbiezacy((int) wykorzystaniesuma.getDni());
        }
        if (kod.equals("ZC")||kod.equals("W")) {
            urlopprezentacja.setWykorzystanierokbiezacy((int) wykorzystaniesuma.getDni());
        }
        if (kod.equals("Z")) {
            urlopprezentacja.setWykorzystanierokbiezacy((int) wykorzystaniesuma.getDni());
        }
        lista.add(wykorzystaniesuma);
        return lista;
    }
     
     
     public static Staz obliczwymiarwStaz(List<Staz> umowy) {
        Staz suma = new Staz();
        double sumadniwszystkie = 0;
        double lataszkola = 0;
        for (Staz p : umowy) {
            if (p.getSlownikszkolazatrhistoria() != null) {
                if (p.getSlownikszkolazatrhistoria().getPraca0nauka1()) {
                    lataszkola = lataszkola + p.getSlownikszkolazatrhistoria().getLata();
                } else {
                    LocalDate dateBefore = LocalDate.parse(p.getDataod());
                    LocalDate dateAfter = LocalDate.parse(p.getDatado());
                    if (p.getDatado() != null && Data.czyjestpoTerminData(p.getDatado(), p.getDataod())) {
                        dateAfter = LocalDate.parse(p.getDatado());
                    }
                    sumadniwszystkie = sumadniwszystkie + ChronoUnit.DAYS.between(dateBefore, dateAfter);
                }
            }
        }
        double liczbalat = sumadniwszystkie / 365 + lataszkola;
        double liczbadni = sumadniwszystkie % 365;
        suma.setLata((int) liczbalat);
        suma.setDni((int) liczbadni);
        return suma;
    }
     
     public static Object[] obliczwymiarwgodzinach(List<Umowa> umowy, EtatPrac etat,String rok, String stannadzien, Angaz angaz, List<Kalendarzmiesiac> kalendarze) {
        int wymiarproporcjonalny = 20;
        double liczbadni = 0;
        Collections.sort(umowy,new UmowaStareNowecomparator());
        for (Umowa p : umowy) {
            if (p.isLiczdourlopu()) {
                if (p.getSlownikszkolazatrhistoria()!=null) {
                    if (p.getSlownikszkolazatrhistoria().getPraca0nauka1()) {
                        liczbadni = liczbadni+p.getSlownikszkolazatrhistoria().getDni();
                    } else {
                        LocalDate dateBefore =  LocalDate.parse(p.getDataod());
                        LocalDate dateAfter = LocalDate.parse(stannadzien);
                        if (p.getDatado()!=null && Data.czyjestpoTerminData(p.getDatado(), stannadzien)) {
                            dateAfter = LocalDate.parse(p.getDatado());
                        }
                        long daysBetween =  ChronoUnit.DAYS.between(dateBefore, dateAfter);
                        liczbadni = liczbadni+daysBetween;
                        if (liczbadni>=3650) {
                            break;
                        }
                    }
                } 
            }
        }
        double angazstazlata = angaz.getPracownik().getStazlata();
        double liczbalatumowy = liczbadni / 365;
        if (angazstazlata>=10) {
            wymiarproporcjonalny = 26;
        } else if (angazstazlata+liczbalatumowy>=10){
            wymiarproporcjonalny = 26;
        } else {
            double angazstadni = angazstazlata*365+angaz.getPracownik().getStazdni();
            double duzasumadni = angazstadni+liczbadni;
            if (duzasumadni>=3650) {
                wymiarproporcjonalny = 26;
            }
        }
        //trzeba pobrad date od, albo poczatek roku albo data pierwszej umowy w roku
            Set<String> napoczetemiesiace = new HashSet<>();
            String dataod = data.Data.pierwszyDzien(rok, "01");
            for (Umowa p : umowy) {
                if (p.isLiczdourlopu() && p.isPraca()) {
                    if (p.getSlownikszkolazatrhistoria() != null) {
                        if (p.getSlownikszkolazatrhistoria().getPraca0nauka1() == false) {
                            boolean czyumowaztegoroku = p.czynalezydoroku(rok);
                            if (czyumowaztegoroku == true) {
                                dataod = p.getDataod();
                                String rokdataod = Data.getRok(dataod);
                                if (!rok.equals(rokdataod)) {
                                    dataod = Data.pierwszyDzien(rok, "01");
                                }
                                String datado = stannadzien;
                                if (p.getDatado() != null && Data.czyjestpoTerminData(p.getDatado(), stannadzien)) {
                                    datado = p.getDatado();
                                }
                                napoczetemiesiace.addAll(Mce.zakresmiesiecy(Data.getMc(dataod), Data.getMc(datado)));
                            }
                        }
                    }
                }
            }
            Set<String> napoczetemiesiacepokorekcie = korygujnapiczetemiesiaceobezplatny(napoczetemiesiace, kalendarze);
            double nowywymiarwdniach =  Math.ceil(wymiarproporcjonalny);
            double wymiarwgstazu = nowywymiarwdniach;
            double wymiargodzin = (nowywymiarwdniach*8);
            if (etat!=null) {
                //to musi bo moze byc zatrudnienie nie od pocztaku roku i jest proporcja
                if (napoczetemiesiacepokorekcie.size()>0) {
                    wymiarproporcjonalny = (int) (Math.ceil(wymiarproporcjonalny/12.0*napoczetemiesiacepokorekcie.size()));
                }
                wymiargodzin = (wymiarproporcjonalny*8*etat.getEtat1()/etat.getEtat2());
            }
        Object[] zwrot = new Object[]{(int)wymiarproporcjonalny,(int)wymiargodzin, (int)wymiarwgstazu, napoczetemiesiacepokorekcie};
        return zwrot;
        //nie wiem co z tym etatem czy badac
    }
     private static Set<String> korygujnapiczetemiesiaceobezplatny(Set<String> napoczetemiesiace, List<Kalendarzmiesiac> kalendarze) {
        Set<String> zwrot = new HashSet<>();
        if (napoczetemiesiace!=null&&kalendarze!=null) {
            for (Kalendarzmiesiac kal : kalendarze) {
                if (napoczetemiesiace.contains(kal.getMc())) {
                    boolean jets30dnibezplatnego = kal.czyjesttrzydziescidnibezplatnego();
                    if (jets30dnibezplatnego==false) {
                        zwrot.add(kal.getMc());
                    }
                }
            }
        }
        return zwrot;
    }
     public static void main(String[] args) {
         int dni = 741;
         int lat = dni / 365;
         int zostalodni = dni % 365;
         System.out.println("lat "+lat);
         System.out.println("zostalo dni "+zostalodni);
     }

    
}
