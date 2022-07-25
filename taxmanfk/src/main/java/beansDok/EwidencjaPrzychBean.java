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
        podsumowanie.setKolumna_17(0.0);
        podsumowanie.setKolumna_15(0.0);
        podsumowanie.setKolumna_14(0.0);
        podsumowanie.setKolumna_125(0.0);
        podsumowanie.setKolumna_12(0.0);
        podsumowanie.setKolumna_10(0.0);
        podsumowanie.setKolumna_85(0.0);
        podsumowanie.setKolumna_55(0.0);
        podsumowanie.setKolumna_3(0.0);
        podsumowanie.setKolumna_2(0.0);
        podsumowanie.setRazem(0.0);
        podsumowanie.setIdDok(new Long(1222));
        podsumowanie.setKontr(new Klienci());
        return podsumowanie;
    }

    public static void rozliczkolumnyR(DokEwidPrzych dk, KwotaKolumna1 tmpX, DokEwidPrzych podsumowanie) {
        switch (tmpX.getNazwakolumny()) {
            case "17%":
                try {
                    dk.setKolumna_17(dk.getKolumna_17() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna_17(tmpX.getNetto());
                }
                podsumowanie.setKolumna_17(podsumowanie.getKolumna_17() + tmpX.getNetto());
                break;
            case "15%":
                try {
                    dk.setKolumna_15(dk.getKolumna_15() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna_15(tmpX.getNetto());
                }
                podsumowanie.setKolumna_15(podsumowanie.getKolumna_15() + tmpX.getNetto());
                break;
            case "14%":
                try {
                    dk.setKolumna_14(dk.getKolumna_14() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna_14(tmpX.getNetto());
                }
                podsumowanie.setKolumna_14(podsumowanie.getKolumna_14() + tmpX.getNetto());
                break;
            case "12.5%":
                try {
                    dk.setKolumna_125(dk.getKolumna_125() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna_125(tmpX.getNetto());
                }
                podsumowanie.setKolumna_125(podsumowanie.getKolumna_125() + tmpX.getNetto());
                break;
            case "12%":
                try {
                    dk.setKolumna_12(dk.getKolumna_12() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna_12(tmpX.getNetto());
                }
                podsumowanie.setKolumna_12(podsumowanie.getKolumna_12() + tmpX.getNetto());
                break;
            case "10%":
                try {
                    dk.setKolumna_10(dk.getKolumna_10() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna_10(tmpX.getNetto());
                }
                podsumowanie.setKolumna_10(podsumowanie.getKolumna_10() + tmpX.getNetto());
                break;
            case "8.5%":
                try {
                    dk.setKolumna_85(dk.getKolumna_85() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna_85(tmpX.getNetto());
                }
                podsumowanie.setKolumna_85(podsumowanie.getKolumna_85() + tmpX.getNetto());
                break;
            case "5.5%":
                try {
                    dk.setKolumna_55(dk.getKolumna_55() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna_55(tmpX.getNetto());
                }
                podsumowanie.setKolumna_55(podsumowanie.getKolumna_55() + tmpX.getNetto());
                break;
            case "3%":
                try {
                    dk.setKolumna_3(dk.getKolumna_3() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna_3(tmpX.getNetto());
                }
                podsumowanie.setKolumna_3(podsumowanie.getKolumna_3() + tmpX.getNetto());
                break;
            case "2%":
                try {
                    dk.setKolumna_2(dk.getKolumna_2() + tmpX.getNetto());
                } catch (Exception e) {
                    E.e(e);
                    dk.setKolumna_2(tmpX.getNetto());
                }
                podsumowanie.setKolumna_2(podsumowanie.getKolumna_2() + tmpX.getNetto());
                break;
        }
        dk.setRazem(tmpX.getNetto());
    }

    public static void rozliczkolumnysumaryczneR(DokEwidPrzych dk, DokEwidPrzych podsumowanie) {
        double suma = dk.getKolumna_17()+dk.getKolumna_15()+dk.getKolumna_14()+dk.getKolumna_125()+dk.getKolumna_12()+dk.getKolumna_10()+dk.getKolumna_85()+dk.getKolumna_55()+dk.getKolumna_3()+dk.getKolumna_2();
        podsumowanie.setRazem(podsumowanie.getRazem()+suma);
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
