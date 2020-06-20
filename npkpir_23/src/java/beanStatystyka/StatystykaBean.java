/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beanStatystyka;

import dao.DokDAO;
import dao.FakturaDAO;
import dao.PodatnikDAO;
import entity.Dok;
import entity.Faktura;
import entity.Podatnik;
import entity.Statystyka;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
public class StatystykaBean implements Runnable {
    
    private List<Statystyka> zwrot;
    private Podatnik p;
    private int lp;
    private String rok;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private FakturaDAO fakturaDAO;

    public StatystykaBean(List<Statystyka> zwrot, Podatnik p, int lp, String rok, DokDAO dokDAO, FakturaDAO fakturaDAO) {
        this.zwrot = zwrot;
        this.p = p;
        this.lp = lp;
        this.rok = rok;
        this.dokDAO = dokDAO;
        this.fakturaDAO = fakturaDAO;
    }
    
    

    @Override
    public void run() {
        List<Dok> dokumenty = dokDAO.zwrocBiezacegoKlientaRok(p, rok);
        Podatnik podatnik = podatnikDAO.find("8511005008");
        List<Faktura> faktury = fakturaDAO.findbyKontrahentNipRok(p.getNip(), podatnik, rok);
        Statystyka sb = new Statystyka(lp++, p, rok, iloscdok(dokumenty), obroty(dokumenty), iloscfaktur(faktury), kwotafaktur(faktury));
        if (sb.getIloscdokumentow() > 0 && sb.getIloscfaktur() > 0) {
            zwrot.add(sb);
        }
    }
    
     private double obroty(List<Dok> dokumenty) {
        double zwrot = 0.0;
        List<String> typydok = pobierztypydok();
        zwrot = dokumenty.stream().filter((p) -> (typydok.contains(p.getRodzajedok().getSkrot()))).map((p) -> p.getBrutto()).reduce(zwrot, (accumulator, _item) -> accumulator + _item);
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
        List<String> zwrot = Collections.synchronizedList(new ArrayList<>());
        zwrot.add("SZ");
        zwrot.add("SPRY");
        zwrot.add("RF");
        zwrot.add("SZK");
        zwrot.add("WDT");
        zwrot.add("UPTK");
        zwrot.add("RVC");
        zwrot.add("RVCS");
        zwrot.add("EXP");
        zwrot.add("UPTK100");
        return zwrot;
    }
    
    public static void main(String[] args) {
    	  int[] array = {23,43,56,97,32};
    	  Arrays.stream(array).reduce((x,y) -> x+y).ifPresent(s -> error.E.s(s));
    	  Arrays.stream(array).reduce(Integer::sum).ifPresent(s -> error.E.s(s));
    	  //Set start value. Result will be start value + sum of array. 
    	  int startValue = 100;
    	  int sum = Arrays.stream(array).reduce(startValue, (x,y) -> x+y);
    	  error.E.s(sum);
    	  sum = Arrays.stream(array).reduce(startValue, Integer::sum);
    	  error.E.s(sum);
          
          List<Integer> list = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);	  
          DoubleSummaryStatistics stats = list.stream()
  			     .collect(Collectors.summarizingDouble(i -> i));
  	  error.E.s("Sum:"+stats.getSum());
    }
    
}
