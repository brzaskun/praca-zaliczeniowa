/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjevatDAO;
import dao.PodatnikDAO;
import entity.Deklaracjevat;
import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class JPKListaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @Inject 
    private DeklaracjevatDAO deklaracjevatDAO;
    private List<Deklaracjevat> wyslaneVAT7;
    @Inject
    private PodatnikDAO podatnikDAO;
    private List<Podatnik> jpkmoznarobic;
    
    
    @PostConstruct
    public void init() {
        jpkmoznarobic = new ArrayList<>();
        List<Podatnik> podatnicy = podatnikDAO.findAll();
        List<Deklaracjevat> wyslaneVAT7 = deklaracjevatDAO.findDeklaracjeWyslane200RokMc(wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
        Set<Podatnik> podatnikdowyslania = new HashSet<>();
        for (Deklaracjevat p : wyslaneVAT7) {
            podatnikdowyslania.add(znajdzpodanik(p.getPodatnik(), podatnicy));
        }
        jpkmoznarobic.addAll(podatnikdowyslania);
    }
    
   private Podatnik znajdzpodanik(String podatnik, List<Podatnik> podatnicy) {
       Podatnik zwrot = null;
        for (Iterator<Podatnik> it = podatnicy.iterator(); it.hasNext();) {
            Podatnik pod = it.next();
            if (pod.getNazwapelna().equals(podatnik)) {
                zwrot = pod;
                break;
            }
        }
        return zwrot;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Deklaracjevat> getWyslaneVAT7() {
        return wyslaneVAT7;
    }

    public void setWyslaneVAT7(List<Deklaracjevat> wyslaneVAT7) {
        this.wyslaneVAT7 = wyslaneVAT7;
    }

    public List<Podatnik> getJpkmoznarobic() {
        return jpkmoznarobic;
    }

    public void setJpkmoznarobic(List<Podatnik> jpkmoznarobic) {
        this.jpkmoznarobic = jpkmoznarobic;
    }

    

    
    
    
}
