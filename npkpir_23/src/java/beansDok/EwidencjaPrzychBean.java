/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDok;

import comparator.Dokcomparator;
import dao.DokDAO;
import embeddable.DokEwidPrzych;
import entity.Dok;
import entity.Klienci;
import entity.KwotaKolumna1;
import entity.Podatnik;
import error.E;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 *
 * @author Osito
 */
public class EwidencjaPrzychBean extends KsiegaBean {

    public static DokEwidPrzych ustawpodsumowanieR() {
        DokEwidPrzych podsumowanie = new DokEwidPrzych();
        podsumowanie.setKontr(null);
        //20%
        podsumowanie.setKolumna5(0.0);
        //17%
        podsumowanie.setKolumna6(0.0);
        //8.5
        podsumowanie.setKolumna7(0.0);
        //5.5
        podsumowanie.setKolumna8(0.0);
        //3
        podsumowanie.setKolumna9(0.0);
        //10
        podsumowanie.setKolumna10(0.0);
        //suma
        podsumowanie.setKolumna11(0.0);
        //15
        podsumowanie.setKolumna12(0.0);
        //12.5
        podsumowanie.setKolumna13(0.0);
        podsumowanie.setIdDok(new Long(1222));
        podsumowanie.setKontr(new Klienci());
        return podsumowanie;
    }

    public static void rozliczkolumnyR(DokEwidPrzych dk, KwotaKolumna1 tmpX, DokEwidPrzych podsumowanie) {
        switch (tmpX.getNazwakolumny()) {
            case "20%":
                try {
                    dk.setKolumna5(dk.getKolumna5() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna5(tmpX.getNetto());
                }
                podsumowanie.setKolumna5(podsumowanie.getKolumna5() + tmpX.getNetto());
                break;
            case "17%":
                try {
                    dk.setKolumna6(dk.getKolumna6() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna6(tmpX.getNetto());
                }
                podsumowanie.setKolumna6(podsumowanie.getKolumna6() + tmpX.getNetto());
                break;
            case "15%":
                try {
                    dk.setKolumna12(dk.getKolumna12() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna12(tmpX.getNetto());
                }
                podsumowanie.setKolumna12(podsumowanie.getKolumna12() + tmpX.getNetto());
                break;
            case "12.5%":
                try {
                    dk.setKolumna13(dk.getKolumna13() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna13(tmpX.getNetto());
                }
                podsumowanie.setKolumna13(podsumowanie.getKolumna13() + tmpX.getNetto());
                break;
            case "8.5%":
                try {
                    dk.setKolumna7(dk.getKolumna7() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna7(tmpX.getNetto());
                }
                podsumowanie.setKolumna7(podsumowanie.getKolumna7() + tmpX.getNetto());
                break;
            case "5.5%":
                try {
                    dk.setKolumna8(dk.getKolumna8() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna8(tmpX.getNetto());
                }
                podsumowanie.setKolumna8(podsumowanie.getKolumna8() + tmpX.getNetto());
                break;
            case "3%":
                try {
                    dk.setKolumna9(dk.getKolumna9() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna9(tmpX.getNetto());
                }
                podsumowanie.setKolumna9(podsumowanie.getKolumna9() + tmpX.getNetto());
                break;
            case "10%":
                try {
                    dk.setKolumna10(dk.getKolumna10() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna10(tmpX.getNetto());
                }
                podsumowanie.setKolumna10(podsumowanie.getKolumna10() + tmpX.getNetto());
                break;
        }
    }

    public static void rozliczkolumnysumaryczneR(DokEwidPrzych dk, DokEwidPrzych podsumowanie) {
        double suma = dk.getKolumna5()+dk.getKolumna6()+dk.getKolumna7()+dk.getKolumna8()+dk.getKolumna9()+dk.getKolumna10()+dk.getKolumna12()+dk.getKolumna13();
        dk.setKolumna11(suma);
        podsumowanie.setKolumna11(podsumowanie.getKolumna11() + suma);
    }
    
    public static List<Dok> pobierzdokumentyR(DokDAO dokDAO, Podatnik podatnik, Integer rok, String mc, int numerkolejny) {
        List<Dok> dokumentyZaMc = Collections.synchronizedList(new ArrayList<>());
        try {
            dokumentyZaMc = dokDAO.zwrocBiezacegoKlientaRokMcPrzychody(podatnik, rok.toString(), mc);
            Collections.sort(dokumentyZaMc, new Dokcomparator());
        } catch (Exception e) { 
            E.e(e); 
        }
        if (dokumentyZaMc != null) {
            for (Dok tmpx : dokumentyZaMc) {
                tmpx.setNrWpkpir(numerkolejny++);
            }
        }
        return dokumentyZaMc;
    }
}
