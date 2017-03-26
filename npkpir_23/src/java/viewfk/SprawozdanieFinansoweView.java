/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import comparator.Podatnikcomparator;
import dao.PodatnikDAO;
import daoFK.SprawozdanieFinansoweDAO;
import embeddable.Roki;
import entity.Podatnik;
import entityfk.SprawozdanieFinansowe;
import enumy.ElementySprawozdaniafin;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class SprawozdanieFinansoweView implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private String wybranyrok;
    @Inject
    private SprawozdanieFinansowe sprawozdanieFinansowe;
    private List elementy;
    private List<SprawozdanieFinansowe> pozycjesprawozdania;
    @Inject
    private SprawozdanieFinansoweDAO sprawozdanieFinansoweDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    private List<Podatnik> listapodatnikow;

    public SprawozdanieFinansoweView() {
        
    }

    @PostConstruct
    private void init() {
        listapodatnikow = podatnikDAO.findPodatnikFK();
        Collections.sort(listapodatnikow, new Podatnikcomparator());
        pozycjesprawozdania = new ArrayList<>();
        if (wybranyrok == null) {
            wybranyrok = wpisView.getRokWpisuSt();
        }
        pozycjesprawozdania = sprawozdanieFinansoweDAO.findSprawozdanieRokPodatnik(wpisView, wybranyrok);
        elementy = new ArrayList<>();
        Class c = ElementySprawozdaniafin.class;
        Field[] p = c.getFields();
        for (Field r : p) {
            try {
                elementy.add(r.getName());
            } catch (Exception ex) {
            }
        }
        if (pozycjesprawozdania != null) {
            for (Iterator<String> it = elementy.iterator(); it.hasNext();) {
                String n = it.next();
                for (SprawozdanieFinansowe sf : pozycjesprawozdania) {
                    if (sf.getElement() == strtoint(n)) {
                        it.remove();
                        break;
                    }
                }
            }
        }
    }
    
    public void pobierzrok() {
        init();
        Msg.dP();
    }
    
    public int strtoint(String el) {
        int zwrot = 0;
        Class c = ElementySprawozdaniafin.class;
        Field[] p = c.getFields();
        for (Field r : p) {
            try {
                if (r.getName().equals(el)) {
                    zwrot = r.getInt(r);
                    break;
                }
            } catch (Exception ex) {
            }
        }
        return zwrot;
    }
    
    public String inttostr(int el) {
        String zwrot = null;
        Class c = ElementySprawozdaniafin.class;
        Field[] p = c.getFields();
        for (Field r : p) {
            try {
                if (r.getInt(r) == el) {
                    zwrot = r.getName();
                    break;
                }
            } catch (Exception ex) {
            }
        }
        return zwrot;
    }
    
    public void dodaj() {
        sprawozdanieFinansowe.setPodatnik(wpisView.getPodatnikObiekt());
        sprawozdanieFinansowe.setRok(wybranyrok);
        sprawozdanieFinansowe.setSporzadzajacy(wpisView.getWprowadzil().getLogin());
        sprawozdanieFinansowe.setDatasporzadzenia(new Date());
        pozycjesprawozdania.add(sprawozdanieFinansowe);
        usunopis();
        sprawozdanieFinansowe = new SprawozdanieFinansowe();
        Msg.dP();
    }
    
    public void usun(SprawozdanieFinansowe sf) {
        pozycjesprawozdania.remove(sf);
        dodajopis(sf);
        Msg.dP();;
    }
    
    public void dodajopis(SprawozdanieFinansowe sf) {
        elementy.add(inttostr(sf.getElement()));
    }
    
    public void usunopis() {
        for (Iterator<String> it = elementy.iterator(); it.hasNext();) {
            String n = it.next();
            if (sprawozdanieFinansowe.getElement() == strtoint(n)) {
                it.remove();
                break;
            }
        }
    }
    
    public void zatwierdz(SprawozdanieFinansowe sf) {
        sf.setZatwierdzajacy(wpisView.getWprowadzil().getLogin());
        sf.setDatazatwierdzenia(new Date());
        Msg.msg("Zatwierdzono element sprawozdania");
    }
    
    public void wyslij(SprawozdanieFinansowe sf) {
        sf.setWyslal(wpisView.getWprowadzil().getLogin());
        sf.setWyslanedopodatnika(new Date());
        Msg.msg("Oznaczono element sprawozdania");
    }
    
    public void wroc(SprawozdanieFinansowe sf) {
        sf.setWrocil(wpisView.getWprowadzil().getLogin());
        sf.setWrociloodpodatnika(new Date());
        Msg.msg("Oznaczono element sprawozdania");
    }
    
    public void zlozone(SprawozdanieFinansowe sf) {
        sf.setZlozyl(wpisView.getWprowadzil().getLogin());
        sf.setZlozonedokrs(new Date());
        Msg.msg("Oznaczono element sprawozdania");
    }
    
     public void zatwierdzone(SprawozdanieFinansowe sf) {
        sf.setZatwierdzil(wpisView.getWprowadzil().getLogin());
        sf.setZatwierdzonewkrs(new Date());
        Msg.msg("Oznaczono element sprawozdania");
    }
     
     public void urzad(SprawozdanieFinansowe sf) {
        sf.setZlozylurzad(wpisView.getWprowadzil().getLogin());
        sf.setZlozonewurzedzie(new Date());
        Msg.msg("Oznaczono element sprawozdania");
    }
     
     public void zapiszzmiany() {
         try {
            sprawozdanieFinansoweDAO.editList(pozycjesprawozdania);
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
            sprawozdanieFinansoweDAO.destroy(pozycjesprawozdania);
            init();
            Msg.dP();
         } catch(Exception e) {
             Msg.dPe();
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

    public List<SprawozdanieFinansowe> getPozycjesprawozdania() {
        return pozycjesprawozdania;
    }

    public void setPozycjesprawozdania(List<SprawozdanieFinansowe> pozycjesprawozdania) {
        this.pozycjesprawozdania = pozycjesprawozdania;
    }

    public List<Podatnik> getListapodatnikow() {
        return listapodatnikow;
    }

    public void setListapodatnikow(List<Podatnik> listapodatnikow) {
        this.listapodatnikow = listapodatnikow;
    }
    
    
    
    
}
