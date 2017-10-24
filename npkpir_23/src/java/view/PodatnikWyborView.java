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
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PodatnikWyborView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private PodatnikDAO podatnikDAO;
    private List<Podatnik> listaPodatnikowNoFKmanager;
    private List<Podatnik> listaPodatnikowNoFK;
    private List<Podatnik> listaPodatnikowFK;
    private List<Podatnik> listaPodatnikow;
    
    @PostConstruct
    public void init() {
        List<Podatnik> lista = podatnikDAO.findPodatnikFKPkpir();
        listaPodatnikowNoFK = podatnikDAO.findPodatnikNieFK();
        listaPodatnikowNoFKmanager = new ArrayList<>(listaPodatnikowNoFK);
        for (Podatnik p : lista) {
            if (!listaPodatnikowNoFK.contains(p)) {
                listaPodatnikowNoFK.add(p);
                listaPodatnikowNoFKmanager.add(p);
            }
        }
        for(Iterator<Podatnik> it = listaPodatnikowNoFK.iterator(); it.hasNext();) {
            Podatnik p = it.next();
            if (p.isTylkodlaZUS()) {
                it.remove();
            }
        }
        Collections.sort(listaPodatnikowNoFKmanager, new Podatnikcomparator());
        Collections.sort(listaPodatnikowNoFK, new Podatnikcomparator());
        listaPodatnikowFK = podatnikDAO.findPodatnikFK();
        for (Podatnik p : lista) {
            if (!listaPodatnikowFK.contains(p)) {
                listaPodatnikowFK.add(p);
            }
        }
        Collections.sort(listaPodatnikowFK, new Podatnikcomparator());
        listaPodatnikow = podatnikDAO.findAll();
        Collections.sort(listaPodatnikow, new Podatnikcomparator());
        
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
    
    public List<Podatnik> getListaPodatnikowFK() {
        return listaPodatnikowFK;
    }
    
    public void setListaPodatnikowFK(List<Podatnik> listaPodatnikowFK) {
        this.listaPodatnikowFK = listaPodatnikowFK;
    }
//</editor-fold>
    
    
    
}
