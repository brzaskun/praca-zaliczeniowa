/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanStatystyka;

import dao.FakturaDAO;
import dao.PodatnikDAO;
import dao.DokDAOfk;
import entity.Faktura;
import entity.Podatnik;
import entity.Statystyka;
import entityfk.Dokfk;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
public class StatystykaBeanFK implements Runnable {
    
    private List<Statystyka> zwrot;
    private Podatnik p;
    private int lp;
    private String rok;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    

    public StatystykaBeanFK(List<Statystyka> zwrot, Podatnik p, int lp, String rok, DokDAOfk dokDAOfk, FakturaDAO fakturaDAO) {
        this.zwrot = zwrot;
        this.p = p;
        this.lp = lp;
        this.rok = rok;
        this.dokDAOfk = dokDAOfk;
        this.fakturaDAO = fakturaDAO;
    }
    
    

    @Override
    public void run() {
        List<Dokfk> dokumenty = dokDAOfk.findDokfkPodatnikRok(p, rok);
        Podatnik podatnik = podatnikDAO.findByNazwaPelna("8511005008");
        List<Faktura> faktury = fakturaDAO.findbyKontrahentNipRok(p.getNip(), podatnik, rok);
        Statystyka sb = new Statystyka(lp++, p, rok, iloscdok(dokumenty), obrotyfk(dokumenty), iloscfaktur(faktury), kwotafaktur(faktury));
        if (sb.getIloscdokumentow() > 0 || sb.getIloscfaktur() > 0) {
            zwrot.add(sb);
        }
    }
    
    private double obrotyfk(List<Dokfk> dokumenty) {
        double zwrot = 0.0;
        if (dokumenty != null && dokumenty.size() > 0) {
            List<String> typydok = pobierztypydok();
            for (Dokfk p : dokumenty) {
               try {
                    if (typydok.contains(p.getRodzajedok().getSkrot())) {
                        if (p.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
                            zwrot += p.getWartoscdokumentu();
                        } else {
                            zwrot += p.getWartoscdokumentuPLN();
                        }
                    }
               } catch (Exception e){}
            }
        }
        return zwrot;
    }
    
     private int iloscfaktur(List<Faktura> faktury) {
        int zwrot = 0;
        if (!faktury.isEmpty()) {
            zwrot = faktury.size();
        }
        return zwrot;
    }

    private double kwotafaktur(List<Faktura> faktury) {
        double zwrot = 0.0;
        if (!faktury.isEmpty()) {
            for (Faktura p : faktury) {
                zwrot += p.getNetto();
            }
        }
        return zwrot;
    }

    private int iloscdok(List dokumenty) {
        int zwrot = 0;
        if (!dokumenty.isEmpty()) {
            zwrot = dokumenty.size();
        }
        return zwrot;
    }
    
    private List<String> pobierztypydok() {
        List<String> zwrot = Collections.synchronizedList(new ArrayList<>());
        zwrot.add("SZ");
        zwrot.add("SPRY");
        zwrot.add("RF");
        zwrot.add("SZK");
        zwrot.add("WDT");
        zwrot.add("UPTK");
        zwrot.add("RVC");
        zwrot.add("EXP");
        return zwrot;
    }
    
}
