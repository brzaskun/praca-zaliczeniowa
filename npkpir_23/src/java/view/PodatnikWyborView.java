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
import java.text.Collator;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
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
    private List<Podatnik> listaPodatnikowNoFK;
    private List<Podatnik> listaPodatnikowFK;
    private List<Podatnik> listaPodatnikow;
    
    @PostConstruct
    private void init() {
        List<Podatnik> lista = podatnikDAO.findPodatnikFKPkpir();
        listaPodatnikowNoFK = podatnikDAO.findPodatnikNieFK();
        for (Podatnik p : lista) {
            if (!listaPodatnikowNoFK.contains(p)) {
                listaPodatnikowNoFK.add(p);
            }
        }
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
