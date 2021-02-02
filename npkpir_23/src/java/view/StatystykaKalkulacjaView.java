/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanStatystyka.StatystykaBean;
import beanStatystyka.StatystykaBeanFK;
import dao.DokDAO;
import dao.FakturaDAO;
import dao.PodatnikDAO;
import dao.StatystykaDAO;
import dao.DokDAOfk;
import entity.Podatnik;
import entity.Statystyka;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.toList;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import org.apache.commons.math3.stat.descriptive.rank.Median;
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
        List<Podatnik> podatnicytmp = podatnikDAO.findPodatnikNieFK();
        List<Podatnik> podatnicy = podatnicytmp.stream().filter(p -> p.isPodmiotaktywny()).collect(collectingAndThen(toList(), Collections::synchronizedList));
        List<Podatnik> podatnicyFKtmp = podatnikDAO.findPodatnikFK();
        List<Podatnik> podatnicyFK = podatnicyFKtmp.stream().filter(p -> p.isPodmiotaktywny()).collect(collectingAndThen(toList(), Collections::synchronizedList));
        podatnikroklista = stworzliste(podatnicy);
        podatnikroklista.addAll(stworzlistefk(podatnicyFK));
        listadozachowania = Collections.synchronizedList(new ArrayList<>());
        listadozachowania.addAll(podatnikroklista);
        podatnikroklista.add(dodajsume(podatnikroklista));
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
            StatystykaBean sb = new StatystykaBean(zwrot, p, lp, rok, dokDAO, fakturaDAO);
            sb.run();
        }
        return zwrot;
    }
    
    private Collection<? extends Statystyka> stworzlistefk(List<Podatnik> podatnicy) {
        List<Statystyka> zwrot = Collections.synchronizedList(new ArrayList<Statystyka>());
        int lp = 1;
        for (Podatnik p : podatnicy) {
            StatystykaBeanFK sb = new StatystykaBeanFK(zwrot, p, lp, rok, dokDAOfk, fakturaDAO);
            sb.run();
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
