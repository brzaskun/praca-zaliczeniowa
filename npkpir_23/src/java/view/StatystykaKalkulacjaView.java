/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.DokDAOfk;
import dao.FakturaDAO;
import dao.PodatnikDAO;
import dao.StatystykaDAO;
import entity.Dok;
import entity.Faktura;
import entity.Podatnik;
import entity.Statystyka;
import entityfk.Dokfk;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import pdffk.PdfKlienciKalkulacja;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class StatystykaKalkulacjaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Statystyka> podatnikroklista;
    private List<Statystyka> listadozachowania;
    private String rok;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private StatystykaDAO statystykaDAO;

    public StatystykaKalkulacjaView() {
        podatnikroklista = Collections.synchronizedList(new ArrayList<>());
        listadozachowania = Collections.synchronizedList(new ArrayList<>());
        rok = "2016";
    }
    
    public void generuj() {
        List<Podatnik> podatnicy = podatnikDAO.findAktywny();
        podatnikroklista = stworzliste(podatnicy);
        listadozachowania.addAll(podatnikroklista);
        podatnikroklista.add(dodajsume(podatnikroklista));
        zaksieguj();
        Msg.msg("Wygenerowano statystyki");
    }
    
    public void pobierz() {
        podatnikroklista = statystykaDAO.findByRok(rok);
        listadozachowania = Collections.synchronizedList(new ArrayList<>());
        listadozachowania.addAll(podatnikroklista);
        podatnikroklista.add(dodajsume(podatnikroklista));
        Msg.msg("Pobrano statystyki");
    }
    
    public void drukuj() {
        if (podatnikroklista != null && podatnikroklista.size() > 0) {
            PdfKlienciKalkulacja.drukuj(podatnikroklista);
        } else {
            Msg.msg("e", "Pusta lista");
        }
    }

    private List<Statystyka> stworzliste(List<Podatnik> podatnicy) {
        List<Statystyka> zwrot = Collections.synchronizedList(new ArrayList<Statystyka>());
        int lp = 1;
        for (Podatnik p : podatnicy) {
            List<Dok> dokumenty = dokDAO.zwrocBiezacegoKlientaRok(p, rok);
            Podatnik podatnik = podatnikDAO.findPodatnikByNIP("8511005008");
            List<Faktura> faktury = fakturaDAO.findbyKontrahentNipRok(p.getNip(), podatnik, rok);
            Statystyka sb = new Statystyka(lp++, p, rok, iloscdok(dokumenty), obroty(dokumenty), iloscfaktur(faktury), kwotafaktur(faktury));
            if (sb.getIloscdokumentow() > 0 && sb.getIloscfaktur() > 0) {
                zwrot.add(sb);
            }
        }
        return zwrot;
    }
    
     private double obroty(List<Dok> dokumenty) {
        double zwrot = 0.0;
        if (dokumenty!=null&&!dokumenty.isEmpty()) {
            zwrot = dokumenty.stream().filter((p) -> p.getRodzajedok()!=null && (p.getRodzajedok().getKategoriadokumentu()==2|| p.getRodzajedok().getKategoriadokumentu()==4)).map((p) -> p.getBruttoDouble()).reduce(zwrot, (accumulator, _item) -> accumulator + _item);
        }
        return zwrot;
    }
    
     private int iloscfaktur(List<Faktura> faktury) {
        int zwrot = 0;
        if (faktury!=null&&!faktury.isEmpty()) {
            zwrot = faktury.size();
        }
        return zwrot;
    }

    private double kwotafaktur(List<Faktura> faktury) {
        double zwrot = 0.0;
        if (faktury!=null&&!faktury.isEmpty()) {
            for (Faktura p : faktury) {
                zwrot += p.getNetto();
            }
        }
        return zwrot;
    }

    private int iloscdok(List dokumenty) {
        int zwrot = 0;
        if (dokumenty!=null&&!dokumenty.isEmpty()) {
            zwrot = dokumenty.size();
        }
        return zwrot;
    }
    
    private Collection<? extends Statystyka> stworzlistefk(List<Podatnik> podatnicy) {
        List<Statystyka> zwrot = Collections.synchronizedList(new ArrayList<Statystyka>());
        int lp = 1;
        for (Podatnik p : podatnicy) {
            List<Dokfk> dokumenty = dokDAOfk.findDokfkPodatnikRok(p, rok);
            Podatnik podatnik = podatnikDAO.findPodatnikByNIP("8511005008");
            List<Faktura> faktury = fakturaDAO.findbyKontrahentNipRok(p.getNip(), podatnik, rok);
            Statystyka sb = new Statystyka(lp++, p, rok, iloscdok(dokumenty), obrotyfk(dokumenty), iloscfaktur(faktury), kwotafaktur(faktury));
            if (sb.getIloscdokumentow() > 0 || sb.getIloscfaktur() > 0) {
                zwrot.add(sb);
            }
        }
        return zwrot;
    }
    
    private double obrotyfk(List<Dokfk> dokumenty) {
           double zwrot = 0.0;
           if (dokumenty != null && dokumenty.size() > 0) {
               for (Dokfk p : dokumenty) {
                  try {
                       if (p.getRodzajedok().getKategoriadokumentu()==2|| p.getRodzajedok().getKategoriadokumentu()==4) {
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
    private Statystyka dodajsume(List<Statystyka> zwrot) {
        Statystyka s = new Statystyka();
        int sumadok = 0;
        int sumafak = 0;
        double sumaobroty = 0.0;
        double sumafakt = 0.0;
        List<Double> fakturaNaObroty = Collections.synchronizedList(new ArrayList<>());
        List<Double> fakturaNaDokumenty = Collections.synchronizedList(new ArrayList<>());
        List<Double> ranking = Collections.synchronizedList(new ArrayList<>());
        double lp = 1;
        for (Statystyka p : zwrot) {
            if (p.getObroty() > 0) {
                sumadok += p.getIloscdokumentow();
                sumafak += p.getIloscfaktur();
                sumaobroty += p.getObroty();
                sumafakt += p.getKwotafaktur();
                fakturaNaObroty.add(p.getFakturaNaObroty());
                fakturaNaDokumenty.add(p.getFakturaNaDokumenty());
                ranking.add(p.getRanking());
                lp++;
            }
        }
        double[] ld1 = fakturaNaObroty.stream().mapToDouble(Double::doubleValue).toArray();
        double[] ld2 = fakturaNaDokumenty.stream().mapToDouble(Double::doubleValue).toArray();
        double[] ld3 = ranking.stream().mapToDouble(Double::doubleValue).toArray();
        Arrays.sort(ld1);
        Arrays.sort(ld2);
        s.setIloscdokumentow(sumadok);
        s.setIloscfaktur(sumafak);
        s.setObroty(sumaobroty);
        s.setKwotafaktur(sumafakt);
        s.setFakturaNaObroty(getMedian(ld1));
        s.setFakturaNaDokumenty(getMedian(ld2));
        s.setRanking(getMedian(ld3));
        s.setRok("podsum");
        return s;
    }
    
    public double getMedian(double[] values){
        Median median = new Median();
        double medianValue = median.evaluate(values);
        return medianValue;
    }
    
    public void zaksieguj() {
        if (!listadozachowania.isEmpty()) {
            try {
                statystykaDAO.usunrok(rok);
                statystykaDAO.createList(listadozachowania);
                Msg.msg("Zaksięgowano zapisy za rok");
            } catch (Exception e) {
                Msg.msg("Wystąpił błąd nie zaksięgowano podumowania za rok");
                E.e(e);
            }
        }
    }
    
    public void czysclista() {
        podatnikroklista = Collections.synchronizedList(new ArrayList<>());
    }
    
//<editor-fold defaultstate="collapsed" desc="comment">    
    public String getRok() {    
        return rok;
    }

    public void setRok(String rok) {    
        this.rok = rok;
    }

    public List<Statystyka> getPodatnikroklista() {
        return podatnikroklista;
    }
    
    public void setPodatnikroklista(List<Statystyka> podatnikroklista) {
        this.podatnikroklista = podatnikroklista;
    }
    
//</editor-fold>

    
}
