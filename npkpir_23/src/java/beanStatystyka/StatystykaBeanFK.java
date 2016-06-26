/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanStatystyka;

import dao.DokDAO;
import dao.FakturaDAO;
import daoFK.DokDAOfk;
import entity.Dok;
import entity.Faktura;
import entity.Podatnik;
import entityfk.Dokfk;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Osito
 */
public class StatystykaBeanFK implements Runnable {
    
    private List<Statystyka> zwrot;
    private Podatnik p;
    private int lp;
    private String rok;
    private DokDAOfk dokDAOfk;
    private FakturaDAO fakturaDAO;

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
        List<Faktura> faktury = fakturaDAO.findbyKontrahentNipRok(p.getNip(), "GRZELCZYK", rok);
        Statystyka sb = new Statystyka(lp++, p, rok, iloscdok(dokumenty), obrotyfk(dokumenty), iloscfaktur(faktury), kwotafaktur(faktury));
        if (sb.getIloscdokumentow() > 0 && sb.getIloscfaktur() > 0) {
            zwrot.add(sb);
        }
    }
    
    private double obrotyfk(List<Dokfk> dokumenty) {
        double zwrot = 0.0;
        List<String> typydok = pobierztypydok();
        for (Dokfk p : dokumenty) {
           if (typydok.contains(p.getRodzajedok().getSkrot())) {
               if (p.getTabelanbp().getWaluta().getSymbolwaluty().equals("PLN")) {
                   zwrot += p.getWartoscdokumentu();
               } else {
                   zwrot += p.getWartoscdokumentuPLN();
               }
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
        for (Faktura p : faktury) {
            zwrot += p.getNetto();
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
        List<String> zwrot = new ArrayList<>();
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
