/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Podatnikcomparator;
import dao.PodatnikDAO;
import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class PodatnikWyborView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private PodatnikDAO podatnikDAO;
    private List<Podatnik> listaPodatnikowNoFKmanager;
    private List<Podatnik> listaPodatnikowNoFK;
    private List<Podatnik> listaPodatnikowNowi;
    private List<Podatnik> listaPodatnikowFK;
    private List<Podatnik> listaPodatnikowFKmanager;
    private List<Podatnik> listaPodatnikow;
    @Inject
    private WpisView wpisView;

    public PodatnikWyborView() {
        this.listaPodatnikowNoFKmanager = new ArrayList<>();
        this.listaPodatnikowNoFK = new ArrayList<>();
        this.listaPodatnikowFK = new ArrayList<>();
        this.listaPodatnikowFKmanager = new ArrayList<>();
        this.listaPodatnikowNowi = new ArrayList<>();
    }
    
    
    @PostConstruct
    public void init() { //E.m(this);
        listaPodatnikow = podatnikDAO.findAktywny();
        Collections.sort(listaPodatnikow, new Podatnikcomparator());
        listaPodatnikowNowi = podatnikDAO.findNowi();
        if (wpisView.getUzer()!=null&&wpisView.getUzer().getLogin().equals("Amelka")) {
            listaPodatnikow = listaPodatnikow.stream().filter(p->p.getKsiegowa()!=null&&p.getKsiegowa().equals(wpisView.getUzer())).collect(Collectors.toList());
        }
        for (Podatnik p : listaPodatnikow) {
            if (!p.isTylkodlaZUS()) {
                switch (p.getFirmafk()) {
                    case 0:
                        listaPodatnikowNoFK.add(p);
                        break;
                    case 1:
                        listaPodatnikowFK.add(p);
                        break;
                    case 3:
                        listaPodatnikowFK.add(p);
                        listaPodatnikowNoFK.add(p);
                        break;
                    default:
                    listaPodatnikowNoFK.add(p);
                }
            }
        }
        for (Podatnik p : listaPodatnikow) {
            switch (p.getFirmafk()) {
                case 0:
                    listaPodatnikowNoFKmanager.add(p);
                    break;
                case 1:
                    listaPodatnikowFKmanager.add(p);
                    break;
                case 3:
                    listaPodatnikowNoFKmanager.add(p);
                    listaPodatnikowFKmanager.add(p);
                    break;
                default:
                    listaPodatnikowNoFKmanager.add(p);
            }
        }
    }

    
   

    //<editor-fold defaultstate="collapsed" desc="comment">
    public List<Podatnik> getListaPodatnikowNoFK() {
        return listaPodatnikowNoFK;
    }
    
    public void setListaPodatnikowNoFK(List<Podatnik> listaPodatnikowNoFK) {
        this.listaPodatnikowNoFK = listaPodatnikowNoFK;
    }

    public List<Podatnik> getListaPodatnikowNoFKmanager() {
        return listaPodatnikowNoFKmanager;
    }

    public void setListaPodatnikowNoFKmanager(List<Podatnik> listaPodatnikowNoFKmanager) {
        this.listaPodatnikowNoFKmanager = listaPodatnikowNoFKmanager;
    }
    

    public List<Podatnik> getListaPodatnikow() {
        return listaPodatnikow;
    }

    public void setListaPodatnikow(List<Podatnik> listaPodatnikow) {
        this.listaPodatnikow = listaPodatnikow;
    }

    public List<Podatnik> getListaPodatnikowFKmanager() {
        return listaPodatnikowFKmanager;
    }

    public void setListaPodatnikowFKmanager(List<Podatnik> listaPodatnikowFKmanager) {
        this.listaPodatnikowFKmanager = listaPodatnikowFKmanager;
    }
    
    public List<Podatnik> getListaPodatnikowFK() {
        return listaPodatnikowFK;
    }
    
    public void setListaPodatnikowFK(List<Podatnik> listaPodatnikowFK) {
        this.listaPodatnikowFK = listaPodatnikowFK;
    }
    
    
//</editor-fold>

    public List<Podatnik> getListaPodatnikowNowi() {
        return listaPodatnikowNowi;
    }

    public void setListaPodatnikowNowi(List<Podatnik> listaPodatnikowNowi) {
        this.listaPodatnikowNowi = listaPodatnikowNowi;
    }
    
    
    
}
