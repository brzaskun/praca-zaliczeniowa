/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package beanstesty;

import entity.Pasekwynagrodzen;
import z.Z;

/**
 *
 * @author Osito
 */
public class PasekwynagrodzenBeanDetail {

    static void zdrowotnakorektaosobawspolpracujaca(Pasekwynagrodzen pasek) {
        pasek.setPraczdrowotne(0.0);
        pasek.setPraczdrowotnedoodliczenia(0.0);
        pasek.setPraczdrowotnedopotracenia(0.0);
    }

    static void zdrowotnakorektanierezydent(Pasekwynagrodzen pasek) {
        pasek.setPraczdrowotnedoodliczenia(0.0);
    }

    static double zdrowotnakorektaograniczeniepodstawyspolecznych(Pasekwynagrodzen pasek, double podstawazdrowotnawstepna) {
        double nowapodstawa = podstawazdrowotnawstepna;
        if (pasek.getPodstawaskladkizus()<pasek.getPrzychodyzus51()) {
            nowapodstawa = pasek.getPodstawaskladkizus()-pasek.getRazemspolecznepracownik();
        }
        return nowapodstawa;
    }
    
    static void zdrowotnarokprzed2023(Pasekwynagrodzen pasek, double zdrowotneskladka, double zdrowotneodliczane) {
        double podstawahipotetyczna = pasek.getBruttominusspolecznehipotetyczne()-pasek.getKosztyuzyskania();
        double limitdlazdrowotnej = Z.z(podstawahipotetyczna * 0.17 - pasek.getKwotawolnadlazdrowotnej()) > 0.0 ? Z.z(podstawahipotetyczna * 0.17 - pasek.getKwotawolnadlazdrowotnej()) : 0.0;
        //spawa
        if (zdrowotneodliczane > limitdlazdrowotnej && Z.z(pasek.getKwotawolna()) > 0.0) {
            pasek.setPraczdrowotne(limitdlazdrowotnej);
            pasek.setPraczdrowotnedoodliczenia(limitdlazdrowotnej);
            pasek.setPraczdrowotnedopotracenia(0.0);
        } else {
            pasek.setPraczdrowotnedoodliczenia(zdrowotneodliczane);
            pasek.setPraczdrowotnedopotracenia(Z.z(zdrowotneskladka - zdrowotneodliczane));
        }
    }

    static void zdrowotnakorektazleceniestudent(Pasekwynagrodzen pasek) {
        pasek.setPraczdrowotne(0.0);
        pasek.setPraczdrowotnedoodliczenia(0.0);
        pasek.setPraczdrowotnedopotracenia(0.0);
    }

    static void zdrowotnakorektazlecenieNiestudent(Pasekwynagrodzen pasek, double zdrowotneskladka) {
        double limitdlazdrowotnej = Z.z(pasek.getPodstawaopodatkowania() * 0.17 - pasek.getKwotawolnadlazdrowotnej()) > 0.0 ? Z.z(pasek.getPodstawaopodatkowania() * 0.17 - pasek.getKwotawolnadlazdrowotnej()) : 0.0;
        if (pasek.isDo26lat()==false&&pasek.getPrzekroczenie26lat()>0.0&&Z.z(pasek.getKwotawolna()) > 0.0) {
            zdrowotneskladka = zdrowotneskladka > limitdlazdrowotnej ? Z.z(limitdlazdrowotnej) : zdrowotneskladka;
        }
        pasek.setPraczdrowotne(zdrowotneskladka);
        pasek.setPraczdrowotnedoodliczenia(0.0);
        pasek.setPraczdrowotnedopotracenia(zdrowotneskladka);
    }

    static void zdrowotnakorektaumowaoprace26(Pasekwynagrodzen pasek, double zdrowotneskladka, double zdrowotneodliczane) {
        double podstawahipotetyczna = pasek.getPodstawaopodatkowania();
        if (podstawahipotetyczna>0.0&&pasek.isDo26lat()==false) {
            double limitdlazdrowotnej = Z.z(podstawahipotetyczna * 0.17 - pasek.getKwotawolnadlazdrowotnej()) > 0.0 ? Z.z(podstawahipotetyczna * 0.17 - pasek.getKwotawolnadlazdrowotnej()) : 0.0;
            zdrowotneskladka = zdrowotneskladka > limitdlazdrowotnej ? Z.z(limitdlazdrowotnej) : zdrowotneskladka;
            zdrowotneodliczane = zdrowotneodliczane > limitdlazdrowotnej ? Z.z(limitdlazdrowotnej) : zdrowotneodliczane;
        }
        pasek.setPraczdrowotne(zdrowotneskladka);
        zdrowotneodliczane = Integer.parseInt(pasek.getRokwypl()) < 2022 ? zdrowotneodliczane : 0.0;
        pasek.setPraczdrowotnedoodliczenia(zdrowotneodliczane);
        pasek.setPraczdrowotnedopotracenia(zdrowotneskladka);
    }

    static void zdrowotnakorektafunkcja(Pasekwynagrodzen pasek, double zdrowotneskladka) {
        pasek.setPraczdrowotne(zdrowotneskladka);
        pasek.setPraczdrowotnedoodliczenia(0.0);
        pasek.setPraczdrowotnedopotracenia(Z.z(zdrowotneskladka));
    }
    
}
