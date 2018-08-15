/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.DokDAOfk;
import daoFK.WierszBODAO;
import entityfk.Dokfk;
import entityfk.StronaWiersza;
import entityfk.Wiersz;
import entityfk.WierszBO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class BilansRozniceView implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private DokDAOfk dokDAOfk;
    List<WierszBO> wierszeBOroznice;
    List<StronaWiersza> wierszeDokroznice;
    private int liczbawierszyBO;
    private int liczbawierszyDok;
    
    public void init() {
        List<WierszBO> wierszeBO = wierszBODAO.listaRokMc(wpisView);
        liczbawierszyBO = wierszeBO.size();
        List<Dokfk> dokfk = dokDAOfk.findDokfkPodatnikRokMcKategoria(wpisView, "BO");
        List<StronaWiersza> wierszeDok = pobierzstrony(dokfk);
        liczbawierszyDok = wierszeDok.size();
        wierszeBOroznice = zrobrozniceBO(new ArrayList<>(wierszeBO), new ArrayList<>(wierszeDok));
        wierszeDokroznice = zrobrozniceDok(new ArrayList<>(wierszeBO), new ArrayList<>(wierszeDok));
    }

    private List<StronaWiersza> pobierzstrony(List<Dokfk> dokfk) {
        List<StronaWiersza> stronywierszaDok = Collections.synchronizedList(new ArrayList<>());
        if (dokfk != null && dokfk.size() == 1) {
            Dokfk dok = dokfk.get(0);
            List<Wiersz> wiersze = dok.getListawierszy();
            stronywierszaDok = pobierzkolejne(wiersze);
        }
        return stronywierszaDok;
    }
    
    private List<StronaWiersza> pobierzkolejne(List<Wiersz> wiersze) {
        List<StronaWiersza> stronywierszaDok = Collections.synchronizedList(new ArrayList<>());
        if (wiersze != null && wiersze.size() > 0) {
            for (Wiersz w : wiersze) {
                if (w.getStronyWiersza() != null) {
                    stronywierszaDok.addAll(w.getStronyWiersza());
                }
            }
        }
        return stronywierszaDok;
    }
    
    private List<WierszBO> zrobrozniceBO(List<WierszBO> wierszeBO, List<StronaWiersza> wierszeDok) {
        for (Iterator<WierszBO> it = wierszeBO.iterator(); it.hasNext();) {
            boolean jest = false;
            WierszBO wbo = it.next();
            for (StronaWiersza sw : wierszeDok) {
                if (sw.getWierszbo().equals(wbo)) {
                    if (sw.getKwota() == wbo.getKwota()) {
                        jest = true;
                        break;
                    }
                }
            }
            if (jest) {
                it.remove();
            }
        }
        return new ArrayList<>(wierszeBO);
    }
    
    private List<StronaWiersza> zrobrozniceDok(List<WierszBO> wierszeBO, List<StronaWiersza> wierszeDok) {
        for (Iterator<StronaWiersza> it = wierszeDok.iterator(); it.hasNext();) {
            boolean jest = false;
            StronaWiersza wbo = it.next();
            for (WierszBO sw : wierszeBO) {
                if (sw.equals(wbo.getWierszbo())) {
                    if (sw.getKwota() == wbo.getKwota()) {
                        jest = true;
                        break;
                    }
                }
            }
            if (jest) {
                it.remove();
            }
        }
        return new ArrayList<>(wierszeDok);
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<WierszBO> getWierszeBOroznice() {
        return wierszeBOroznice;
    }

    public void setWierszeBOroznice(List<WierszBO> wierszeBOroznice) {
        this.wierszeBOroznice = wierszeBOroznice;
    }

    public List<StronaWiersza> getWierszeDokroznice() {
        return wierszeDokroznice;
    }

    public void setWierszeDokroznice(List<StronaWiersza> wierszeDokroznice) {
        this.wierszeDokroznice = wierszeDokroznice;
    }

    

   
 
    
}
