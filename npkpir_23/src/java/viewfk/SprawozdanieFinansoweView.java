/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Podatnikcomparator;
import dao.PodatnikDAO;
import dao.SprawozdanieFinansoweDAO;
import entity.Podatnik;
import entityfk.SprawozdanieFinansowe;
import error.E;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import view.WpisView;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class SprawozdanieFinansoweView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private String wybranyrok;
    @Inject
    private SprawozdanieFinansowe sprawozdanieFinansowe;
    private List elementy;
    private List<SprawozdanieFinansowe> sprawozdaniapodatnicy;
    @Inject
    private SprawozdanieFinansoweDAO sprawozdanieFinansoweDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    private List<Podatnik> listapodatnikow;

    public SprawozdanieFinansoweView() {
        
    }

    @PostConstruct
    private void init() { //E.m(this);
        listapodatnikow = podatnikDAO.findPodatnikFK();
        Collections.sort(listapodatnikow, new Podatnikcomparator());
        sprawozdaniapodatnicy = Collections.synchronizedList(new ArrayList<>());
        if (wybranyrok == null) {
            wybranyrok = String.valueOf(wpisView.getRokWpisu()+1);
        }
        sprawozdaniapodatnicy = sprawozdanieFinansoweDAO.findSprawozdanieRok(wybranyrok);
        for (Iterator<Podatnik> it = listapodatnikow.iterator(); it.hasNext();) {
            Podatnik p = it.next();
            for (SprawozdanieFinansowe f : sprawozdaniapodatnicy) {
                if (f.getPodatnik().equals(p)) {
                    it.remove();
                    break;
                }
            }
        }
    }
    
    public void pobierzrok() {
        init();
        Msg.dP();
    }
    
    public void dodaj() {
        if (sprawozdanieFinansowe.getPodatnik()!=null) {
            sprawozdanieFinansowe.setRok(wybranyrok);
            sprawozdanieFinansowe.setZaksiegowano(wpisView.getUzer().getLogin());
            sprawozdanieFinansowe.setDatazaksiegowania(new Date());
            sprawozdaniapodatnicy.add(sprawozdanieFinansowe);
            listapodatnikow.remove(sprawozdanieFinansowe.getPodatnik());
            sprawozdanieFinansowe = new SprawozdanieFinansowe();
            Msg.msg("Dodano nową firmę do odhaczania");
        }
    }
    
    public void usun(SprawozdanieFinansowe sf) {
        sprawozdaniapodatnicy.remove(sf);
        listapodatnikow.add(sf.getPodatnik());
        Msg.msg("Usunieto firmę");
    }
    
    public void cit8dodaj(SprawozdanieFinansowe sf) {
        sf.setCit8(wpisView.getUzer().getLogin());
        sf.setDatacit8(new Date());
        Msg.msg("Naniesiono wysłanie CIT-8");
    }
    
    
    public void zatwierdz(SprawozdanieFinansowe sf) {
        sf.setZatwierdzajacy(wpisView.getUzer().getLogin());
        sf.setDatazatwierdzenia(new Date());
        Msg.msg("Zatwierdzono sprawozdanie");
    }
    
    public void wyslij(SprawozdanieFinansowe sf) {
        sf.setWyslal(wpisView.getUzer().getLogin());
        sf.setWyslanedopodatnika(new Date());
        Msg.msg("Wysłano do podatnika");
    }
    
    public void wroc(SprawozdanieFinansowe sf) {
        sf.setWrocil(wpisView.getUzer().getLogin());
        sf.setWrociloodpodatnika(new Date());
        Msg.msg("Sprawozdanie wsróciło od podatnika");
    }
    
    public void zlozone(SprawozdanieFinansowe sf) {
        sf.setZlozyl(wpisView.getUzer().getLogin());
        sf.setZlozonedokrs(new Date());
        Msg.msg("Złożone w KRS");
    }
    
     public void zatwierdzone(SprawozdanieFinansowe sf) {
        sf.setZatwierdzil(wpisView.getUzer().getLogin());
        sf.setZatwierdzonewkrs(new Date());
        Msg.msg("Postanowienie z KRS");
    }
     
     public void urzad(SprawozdanieFinansowe sf) {
        sf.setZlozylurzad(wpisView.getUzer().getLogin());
        sf.setZlozonewurzedzie(new Date());
        Msg.msg("Złożono w urzędzie");
    }
     
     public void zapiszzmiany() {
         try {
            sprawozdanieFinansoweDAO.editList(sprawozdaniapodatnicy);
            Msg.dP();
         } catch(Exception e) {
             Msg.dPe();
         }
     }
     
     public void resetuj() {
         try {
            init();
            Msg.dP();
         } catch(Exception e) {
             Msg.dPe();
         }
     }
     
     public void usunwszystko() {
         try {
            sprawozdanieFinansoweDAO.remove(sprawozdaniapodatnicy);
            init();
            Msg.dP();
         } catch(Exception e) {
             Msg.dPe();
         }
     }
     
    private void dodajelement(Field r) {
        try {
            String formaprawna = wpisView.getFormaprawna();
            if (formaprawna.equals("SPOLKA_KOMANDYTOWA") && r.getInt(r)!= 6 && r.getInt(r)!= 7) {
                elementy.add(r.getName());
            } else {
                elementy.add(r.getName());
            }
        } catch (Exception ex) {
            
        }
    }
     
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public String getWybranyrok() {
        return wybranyrok;
    }

    public void setWybranyrok(String wybranyrok) {
        this.wybranyrok = wybranyrok;
    }

    public SprawozdanieFinansowe getSprawozdanieFinansowe() {
        return sprawozdanieFinansowe;
    }

    public void setSprawozdanieFinansowe(SprawozdanieFinansowe sprawozdanieFinansowe) {
        this.sprawozdanieFinansowe = sprawozdanieFinansowe;
    }

    public List getElementy() {
        return elementy;
    }

    public void setElementy(List elementy) {
        this.elementy = elementy;
    }

    public List<SprawozdanieFinansowe> getSprawozdaniapodatnicy() {
        return sprawozdaniapodatnicy;
    }

    public void setSprawozdaniapodatnicy(List<SprawozdanieFinansowe> sprawozdaniapodatnicy) {
        this.sprawozdaniapodatnicy = sprawozdaniapodatnicy;
    }

    public List<Podatnik> getListapodatnikow() {
        return listapodatnikow;
    }

    public void setListapodatnikow(List<Podatnik> listapodatnikow) {
        this.listapodatnikow = listapodatnikow;
    }

    
    
    
    
    
}
