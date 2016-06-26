/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanStatystyka.Statystyka;
import beanStatystyka.StatystykaBean;
import beanStatystyka.StatystykaBeanFK;
import dao.DokDAO;
import dao.FakturaDAO;
import dao.PodatnikDAO;
import daoFK.DokDAOfk;
import entity.Dok;
import entity.Faktura;
import entity.Podatnik;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class StatystykaUproszczonaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Statystyka> podatnikroklista;
    private String rok;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private DokDAOfk dokDAOfk;
    @Inject
    private FakturaDAO fakturaDAO;

    public StatystykaUproszczonaView() {
        podatnikroklista = new ArrayList<>();
        rok = "2016";
    }
    
    public void pobierz() {
        List<Podatnik> podatnicy = podatnikDAO.findPodatnikNieFK();
        List<Podatnik> podatnicyFK = podatnikDAO.findPodatnikFK();
        podatnikroklista = stworzliste(podatnicy);
        podatnikroklista.addAll(stworzlistefk(podatnicyFK));
        podatnikroklista.add(dodajsume(podatnikroklista));
        Msg.msg("Pobrano statystyki");
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
        double fakturaNaObroty = 0.0;
        double fakturaNaDokumenty = 0.0;
        double ranking = 0.0;
        double lp = 1;
        for (Statystyka p : zwrot) {
            if (p.getObroty() > 0) {
                sumadok += p.getIloscdokumentow();
                sumafak += p.getIloscfaktur();
                sumaobroty += p.getObroty();
                sumafakt += p.getKwotafaktur();
                fakturaNaObroty += p.getFakturaNaObroty();
                fakturaNaDokumenty += p.getFakturaNaDokumenty();
                ranking += p.getRanking();
                lp++;
            }
        }
        fakturaNaObroty = Z.z4(fakturaNaObroty/lp);
        fakturaNaDokumenty = Z.z4(fakturaNaDokumenty/lp);
        ranking = Z.z4(ranking/lp);
        s.setIloscdokumentow(sumadok);
        s.setIloscfaktur(sumafak);
        s.setObroty(sumaobroty);
        s.setKwotafaktur(sumafakt);
        s.setFakturaNaObroty(fakturaNaObroty);
        s.setFakturaNaDokumenty(fakturaNaDokumenty);
        s.setRanking(ranking);
        s.setRok("podsum");
        return s;
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
