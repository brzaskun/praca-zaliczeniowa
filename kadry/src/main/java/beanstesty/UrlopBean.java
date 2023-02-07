/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanstesty;

import data.Data;
import embeddable.Mce;
import entity.Angaz;
import entity.Dzien;
import entity.EtatPrac;
import entity.Kalendarzmiesiac;
import entity.Nieobecnoscprezentacja;
import entity.Nieobecnoscwykorzystanie;
import entity.Umowa;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 *
 * @author Osito
 */
public class UrlopBean {
     public static Nieobecnoscprezentacja pobierzurlop(Angaz angaz, String rok, String stannadzien) {
         Nieobecnoscprezentacja urlopprezentacja = new Nieobecnoscprezentacja(angaz, rok);
        if (angaz!=null) {
            List<Kalendarzmiesiac> kalendarze = angaz.getKalendarzmiesiacList().stream().filter(p->p.getRok().equals(rok)).collect(Collectors.toList());
            urlopprezentacja.setNieobecnoscwykorzystanieList(naniesdnizkodem(kalendarze, urlopprezentacja, "U"));
            List<Umowa> umowy = angaz.getUmowaList();
            if (angaz.getRok().equals(rok)) {
                urlopprezentacja.setOkrespoprzedni(angaz.getBourlopgodziny());
            } else if (angaz.getSerialsp()!=null&&rok.equals("2023")) {
                urlopprezentacja.setOkrespoprzedni(angaz.getBourlopgodziny());
            }
            urlopprezentacja.setWymiarokresbiezacy(obliczwymiarwgodzinach(umowy, angaz.pobierzetat(stannadzien), stannadzien, rok));
            urlopprezentacja.setDoprzeniesienia(urlopprezentacja.getOkrespoprzedni()+urlopprezentacja.getWymiarokresbiezacy()-urlopprezentacja.getWykorzystanierokbiezacy()-urlopprezentacja.getWykorzystanierokbiezacyekwiwalent());
            //Msg.msg("Pobrano dane urlopowe");
        }
        return urlopprezentacja;
    }
     
     public static List<Nieobecnoscwykorzystanie> naniesdnizkodem(List<Kalendarzmiesiac> kalendarze, Nieobecnoscprezentacja urlopprezentacja, String kod) {
        List<Nieobecnoscwykorzystanie> lista = new ArrayList<>();
        Nieobecnoscwykorzystanie wykorzystaniesuma = new Nieobecnoscwykorzystanie("podsum.",0);
        for (Kalendarzmiesiac p : kalendarze) {
            for (Dzien r : p.getDzienList()) {
                if (r.getNieobecnosc()!=null) {
                    if (r.getNieobecnosc().getKod().equals(kod)) {
                        Nieobecnoscwykorzystanie wykorzystanie = new Nieobecnoscwykorzystanie();
                        wykorzystanie.setMc(p.getMc());
                        wykorzystanie.setData(Data.zrobdate(r.getNrdnia(), p.getMc(), p.getRok()));
                        wykorzystanie.setDni(1);
                        wykorzystanie.setOpis(r.getNieobecnosc().getOpisRodzajSwiadczenie());
                        wykorzystanie.setKod(r.getNieobecnosc().getKod());
                        if (kod.equals("U")) {
                            wykorzystanie.setGodziny((int) r.getUrlopPlatny());
                        }
                        if (kod.equals("CH")) {
                            wykorzystanie.setGodziny((int) r.getWynagrodzeniezachorobe());
                        }
                        if (kod.equals("ZC")) {
                            wykorzystanie.setGodziny((int) r.getZasilek());
                        }
                        if (kod.equals("Z")) {
                            wykorzystanie.setGodziny((int) r.getPrzepracowano());
                        }
                        wykorzystanie.setUrlopprezentacja(urlopprezentacja);
                        EtatPrac pobierzetat = p.getAngaz().pobierzetat(wykorzystanie.getData());
                        wykorzystanie.setEtat1(pobierzetat.getEtat1());
                        wykorzystanie.setEtat2(pobierzetat.getEtat2());
                        if (wykorzystanie.getGodziny()>0) {
                            wykorzystaniesuma.setGodziny(wykorzystaniesuma.getGodziny()+wykorzystanie.getGodziny());
                            wykorzystaniesuma.setDni(wykorzystaniesuma.getDni()+wykorzystanie.getDni());
                            lista.add(wykorzystanie);
                        }
                    }
                }
            }
        }
        if (kod.equals("U")) {
            urlopprezentacja.setWykorzystanierokbiezacy(wykorzystaniesuma.getGodziny());
        }
        if (kod.equals("CH")) {
            urlopprezentacja.setWykorzystanierokbiezacy((int) wykorzystaniesuma.getDni());
        }
        if (kod.equals("ZC")) {
            urlopprezentacja.setWykorzystanierokbiezacy((int) wykorzystaniesuma.getDni());
        }
        if (kod.equals("Z")) {
            urlopprezentacja.setWykorzystanierokbiezacy((int) wykorzystaniesuma.getDni());
        }
        lista.add(wykorzystaniesuma);
        return lista;
    }
     
     public static int obliczwymiarwgodzinach(List<Umowa> umowy, EtatPrac etat, String stannadzien, String rok) {
        int wymiarwdniach = 20;
        double liczbadni = 0;
        for (Umowa p : umowy) {
            if (p.isLiczdourlopu()) {
                if (p.getSlownikszkolazatrhistoria()!=null) {
                    if (p.getSlownikszkolazatrhistoria().getPraca0nauka1()) {
                        liczbadni = liczbadni+p.getSlownikszkolazatrhistoria().getDni();
                    } else {
                        LocalDate dateBefore =  LocalDate.parse(p.getDataod());
                        LocalDate dateAfter = LocalDate.parse(stannadzien);
                        if (p.getDatado()!=null && Data.czyjestpo(p.getDatado(), stannadzien)) {
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
        if (liczbadni>=3650) {
            wymiarwdniach = 26;
        }
        //a teraz sprawdzamy czy nie sa umowy tylko z tego roku i trzeba proporckjonalnie
            Set<String> napoczetemiesiace = new HashSet<>();
            for (Umowa p : umowy) {
                if (p.isLiczdourlopu()) {
                    if (p.getSlownikszkolazatrhistoria() != null) {
                        if (p.getSlownikszkolazatrhistoria().getPraca0nauka1() == false) {
                            String rokumowy = Data.getRok(p.getDataod());
                            boolean czyumowaztegoroku = rok.equals(rokumowy);
                            if (czyumowaztegoroku == false) {
                                break;
                            } else {
                                String dataod = p.getDataod();
                                String datado = stannadzien;
                                if (p.getDatado() != null && Data.czyjestpo(p.getDatado(), stannadzien)) {
                                    datado = p.getDatado();
                                }
                                napoczetemiesiace.addAll(Mce.zakresmiesiecy(Data.getMc(dataod), Data.getMc(datado)));
                            }
                        }
                    }
                }
            }
            double nowywymiarwdniach =  Math.ceil(wymiarwdniach);
            double wymiargodzin = (nowywymiarwdniach*8);
            if (etat!=null) {
                nowywymiarwdniach =  Math.ceil(wymiarwdniach*etat.getEtat1()/etat.getEtat2());
                if (napoczetemiesiace.size()>0) {
                    nowywymiarwdniach = (int) (Math.ceil(wymiarwdniach/12.0*napoczetemiesiace.size()));
                    wymiargodzin = (nowywymiarwdniach*8*etat.getEtat1()/etat.getEtat2());
                }
            }
        return (int) wymiargodzin;
        //nie wiem co z tym etatem czy badac
    }
}
