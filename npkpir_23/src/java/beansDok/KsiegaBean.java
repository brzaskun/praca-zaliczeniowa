/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDok;

import comparator.Dokcomparator;
import dao.DokDAO;
import embeddable.DokKsiega;
import embeddable.Mce;
import entity.Dok;
import entity.Klienci;
import entity.KwotaKolumna1;
import entity.Podatnik;
import entity.Sumypkpir;
import entity.SumypkpirPK;
import error.E;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Named;
import view.ParametrView;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named

public class KsiegaBean {

    public static int pobierznumerrecznie(Podatnik pod, Integer rok, String mc) {
        int numerkolejny = 1;
        if (pod.getNumerpkpir() != null) {
            try {
                //zmienia numer gdy srodek roku
                String wartosc = ParametrView.zwrocParametr(pod.getNumerpkpir(),rok,Mce.getMapamcyCalendar().get(mc));
                numerkolejny = Integer.parseInt(wartosc);
            } catch (Exception e) { 
                E.e(e); 
                System.out.println("Brak numeru pkpir wprowadzonego w trakcie roku");
            }
        }
        return numerkolejny;
    }

    public static List<Dok> pobierzdokumenty(DokDAO dokDAO, String podatnik, Integer rok, String mc, int numerkolejny) {
        List<Dok> dokumentyZaRok = null;
        List<Dok> dokumentyZaMc = new ArrayList<>();
        try {
            dokumentyZaRok = dokDAO.zwrocBiezacegoKlientaRok(podatnik, rok.toString());
            dokumentyZaMc = dokDAO.zwrocBiezacegoKlientaRokMC(podatnik, rok.toString(), mc);
            int iloscdo = 0;
            for (Dok p : dokumentyZaRok) {
                if (Integer.parseInt(p.getPkpirM()) < Integer.parseInt(mc)) {
                    iloscdo += 1;
                }
            }
            if (numerkolejny == 1 && dokumentyZaRok != null && dokumentyZaMc != null) {
                numerkolejny = iloscdo+1;
            }
            Collections.sort(dokumentyZaRok, new Dokcomparator());
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

    public static DokKsiega ustawpodsumowanie() {
        DokKsiega podsumowanie = new DokKsiega();
        podsumowanie.setOpis("Podsumowanie");
        podsumowanie.setKolumna7(0.0);
        podsumowanie.setKolumna8(0.0);
        podsumowanie.setKolumna9(0.0);
        podsumowanie.setKolumna10(0.0);
        podsumowanie.setKolumna11(0.0);
        podsumowanie.setKolumna12(0.0);
        podsumowanie.setKolumna13(0.0);
        podsumowanie.setKolumna14(0.0);
        podsumowanie.setKolumna15(0.0);
        podsumowanie.setIdDok(new Long(1222));
        podsumowanie.setKontr(new Klienci());
        return podsumowanie;
    }

    public static void rozliczkolumny(DokKsiega dk, KwotaKolumna1 tmpX, DokKsiega podsumowanie) {
        switch (tmpX.getNazwakolumny()) {
                    case "przych. sprz":
                        try {
                            dk.setKolumna7(dk.getKolumna7()+tmpX.getNetto());
                        } catch (Exception e) { 
                            dk.setKolumna7(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna7(podsumowanie.getKolumna7() + tmpX.getNetto());
                        break;
                    case "pozost. przych.":
                        try {
                            dk.setKolumna8(dk.getKolumna8()+tmpX.getNetto());
                        } catch (Exception e) {
                            dk.setKolumna8(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna8(podsumowanie.getKolumna8() + tmpX.getNetto());
                        break;
                    case "zakup tow. i mat.":
                        try {
                            dk.setKolumna10(dk.getKolumna10()+tmpX.getNetto());
                        } catch (Exception e) {
                            dk.setKolumna10(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna10(podsumowanie.getKolumna10() + tmpX.getNetto());
                        break;
                    case "koszty ub.zak.":
                        try {
                            dk.setKolumna11(dk.getKolumna11()+tmpX.getNetto());
                        } catch (Exception e) { 
                            dk.setKolumna11(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna11(podsumowanie.getKolumna11() + tmpX.getNetto());
                        break;
                    case "wynagrodzenia":
                        try {
                            dk.setKolumna12(dk.getKolumna12()+tmpX.getNetto());
                        } catch (Exception e) {
                            dk.setKolumna12(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna12(podsumowanie.getKolumna12() + tmpX.getNetto());
                        break;
                    case "poz. koszty":
                        try {
                            dk.setKolumna13(dk.getKolumna13()+tmpX.getNetto());
                        } catch (Exception e) {
                            dk.setKolumna13(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna13(podsumowanie.getKolumna13() + tmpX.getNetto());
                        break;
                    case "inwestycje":
                        try {
                            dk.setKolumna15(dk.getKolumna15()+tmpX.getNetto());
                        } catch (Exception e) {
                            dk.setKolumna15(tmpX.getNetto());
                        }
                        podsumowanie.setKolumna15(podsumowanie.getKolumna15() + tmpX.getNetto());
                        break;
                }
    }

    public static void rozliczkolumnysumaryczne(DokKsiega dk, DokKsiega podsumowanie) {
        if (dk.getKolumna7() != null && dk.getKolumna8() != null) {
                dk.setKolumna9(dk.getKolumna7() + dk.getKolumna8());
                podsumowanie.setKolumna9(podsumowanie.getKolumna9() + dk.getKolumna9());
            } else if (dk.getKolumna7() != null) {
                dk.setKolumna9(dk.getKolumna7());
                podsumowanie.setKolumna9(podsumowanie.getKolumna9() + dk.getKolumna9());
            } else {
                dk.setKolumna9(dk.getKolumna8());
                try {
                    podsumowanie.setKolumna9(podsumowanie.getKolumna9() + dk.getKolumna9());
                } catch (Exception e) { E.e(e); 
                }
            }
            if (dk.getKolumna12() != null && dk.getKolumna13() != null) {
                dk.setKolumna14(dk.getKolumna12() + dk.getKolumna13());
                podsumowanie.setKolumna14(podsumowanie.getKolumna14() + dk.getKolumna14());
            } else if (dk.getKolumna12() != null) {
                dk.setKolumna14(dk.getKolumna12());
                podsumowanie.setKolumna14(podsumowanie.getKolumna14() + dk.getKolumna14());
            } else {
                dk.setKolumna14(dk.getKolumna13());
                try {
                    podsumowanie.setKolumna14(podsumowanie.getKolumna14() + dk.getKolumna14());
                } catch (Exception e) { E.e(e); 
                }
            }
    }

    public static Sumypkpir zachowajsumypkpir(WpisView wpisView, DokKsiega podsumowanie) {
        Sumypkpir sumyzachowac = new Sumypkpir();
        SumypkpirPK sumyklucz = new SumypkpirPK();
        sumyklucz.setRok(wpisView.getRokWpisu().toString());
        sumyklucz.setMc(wpisView.getMiesiacWpisu());
        sumyklucz.setPodatnik(wpisView.getPodatnikWpisu());
        sumyzachowac.setSumypkpirPK(sumyklucz);
        sumyzachowac.setSumy(podsumowanie);
        return sumyzachowac;
    }

    public static DokKsiega ustawsumakoncowa() {
        DokKsiega sumakoncowa = new DokKsiega();
        sumakoncowa.setOpis("Razem");
        sumakoncowa.setKolumna7(0.0);
        sumakoncowa.setKolumna8(0.0);
        sumakoncowa.setKolumna9(0.0);
        sumakoncowa.setKolumna10(0.0);
        sumakoncowa.setKolumna11(0.0);
        sumakoncowa.setKolumna12(0.0);
        sumakoncowa.setKolumna13(0.0);
        sumakoncowa.setKolumna14(0.0);
        sumakoncowa.setKolumna15(0.0);
        sumakoncowa.setIdDok(new Long(1224));
        sumakoncowa.setKontr(new Klienci());
        return sumakoncowa;
    }

    public static DokKsiega ustawsumaposrednia() {
        DokKsiega sumaposrednia = new DokKsiega();
        sumaposrednia.setOpis("z przeniesienia");
        sumaposrednia.setKolumna7(0.0);
        sumaposrednia.setKolumna8(0.0);
        sumaposrednia.setKolumna9(0.0);
        sumaposrednia.setKolumna10(0.0);
        sumaposrednia.setKolumna11(0.0);
        sumaposrednia.setKolumna12(0.0);
        sumaposrednia.setKolumna13(0.0);
        sumaposrednia.setKolumna14(0.0);
        sumaposrednia.setKolumna15(0.0);
        sumaposrednia.setIdDok(new Long(1223));
        sumaposrednia.setKontr(new Klienci());
        return sumaposrednia;
    }

    public static void rozliczpodsumowania(List<Sumypkpir> listasum, DokKsiega sumaposrednia, DokKsiega sumakoncowa, String biezacymiesiac) {
        for (Sumypkpir p : listasum) {
                if (!p.getSumypkpirPK().getMc().equals(biezacymiesiac)) {
                    sumaposrednia.setKolumna7(sumaposrednia.getKolumna7() + p.getSumy().getKolumna7());
                    sumaposrednia.setKolumna8(sumaposrednia.getKolumna8() + p.getSumy().getKolumna8());
                    sumaposrednia.setKolumna9(sumaposrednia.getKolumna9() + p.getSumy().getKolumna9());
                    sumaposrednia.setKolumna10(sumaposrednia.getKolumna10() + p.getSumy().getKolumna10());
                    sumaposrednia.setKolumna11(sumaposrednia.getKolumna11() + p.getSumy().getKolumna11());
                    sumaposrednia.setKolumna12(sumaposrednia.getKolumna12() + p.getSumy().getKolumna12());
                    sumaposrednia.setKolumna13(sumaposrednia.getKolumna13() + p.getSumy().getKolumna13());
                    sumaposrednia.setKolumna14(sumaposrednia.getKolumna14() + p.getSumy().getKolumna14());
                    sumaposrednia.setKolumna15(sumaposrednia.getKolumna15() + p.getSumy().getKolumna15());
                } else {
                    sumakoncowa.setKolumna7(sumaposrednia.getKolumna7() + p.getSumy().getKolumna7());
                    sumakoncowa.setKolumna8(sumaposrednia.getKolumna8() + p.getSumy().getKolumna8());
                    sumakoncowa.setKolumna9(sumaposrednia.getKolumna9() + p.getSumy().getKolumna9());
                    sumakoncowa.setKolumna10(sumaposrednia.getKolumna10() + p.getSumy().getKolumna10());
                    sumakoncowa.setKolumna11(sumaposrednia.getKolumna11() + p.getSumy().getKolumna11());
                    sumakoncowa.setKolumna12(sumaposrednia.getKolumna12() + p.getSumy().getKolumna12());
                    sumakoncowa.setKolumna13(sumaposrednia.getKolumna13() + p.getSumy().getKolumna13());
                    sumakoncowa.setKolumna14(sumaposrednia.getKolumna14() + p.getSumy().getKolumna14());
                    sumakoncowa.setKolumna15(sumaposrednia.getKolumna15() + p.getSumy().getKolumna15());
                    break;
                }
            }
    }

   
    
}
