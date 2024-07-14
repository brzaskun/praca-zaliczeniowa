/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.DokDAOfk;
import dao.WierszBODAO;
import entityfk.Dokfk;
import entityfk.StronaWiersza;
import entityfk.Wiersz;
import entityfk.WierszBO;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import view.WpisView;
import interceptor.ConstructorInterceptor;
/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class BilansRozniceView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private DokDAOfk dokDAOfk;
    List<WierszBO> wierszeBOroznice;
    List<StronaWiersza> wierszeDokroznice;
    private int liczbawierszyBO;
    private int liczbawierszyDok;
    private String startmsg;
    private String stopmsg;
    
    public void init() { //E.m(this);
        List<WierszBO> wierszeBO = wierszBODAO.listaRokMc(wpisView);
        liczbawierszyBO = wierszeBO.size();
        List<Dokfk> dokfkBO = dokDAOfk.findDokfkPodatnikRokMcKategoria(wpisView, "BO");
        List<Dokfk> dokfkBOR = dokDAOfk.findDokfkPodatnikRokMcKategoria(wpisView, "BOR");
        List<StronaWiersza> wierszeDok = new ArrayList<>();
        String jakidokument = "bilans otwarcia - wiersze BO";
        if (!wpisView.getMiesiacWpisu().equals("01")&&dokfkBOR!=null) {
            wierszeDok = pobierzstrony(dokfkBOR);
            jakidokument = "obroty rozpoczęcia - wiersze BOR";
        } else {
            wierszeDok = pobierzstrony(dokfkBO);
        }
        
        liczbawierszyDok = wierszeDok.size();
        startmsg = "Rozpoczęto szukanie różnic "+jakidokument;
        stopmsg = "Wystąpił błąd podczas szukania różnic "+jakidokument;
        wierszeBOroznice = zrobrozniceBO(new ArrayList<>(wierszeBO), new ArrayList<>(wierszeDok));
        wierszeDokroznice = zrobrozniceDok(new ArrayList<>(wierszeBO), new ArrayList<>(wierszeDok));
        stopmsg = "Zakończono szukanie różnic "+jakidokument;
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
                    if (sw.getKwota() == wbo.getKwota()&&sw.getKwotaPLN() == wbo.getKwotaPLN()) {
                        if (sw.getKonto().getPelnynumer().equals(wbo.getKonto().getPelnynumer())) {
                            jest = true;
                            break;
                        }
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
                    if (sw.getKwota() == wbo.getKwota()&&sw.getKwotaPLN() == wbo.getKwotaPLN()) {
                         if (sw.getKonto().getPelnynumer().equals(wbo.getKonto().getPelnynumer())) {
                            jest = true;
                            break;
                         }
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

    public String getStartmsg() {
        return startmsg;
    }

    public void setStartmsg(String startmsg) {
        this.startmsg = startmsg;
    }

    public String getStopmsg() {
        return stopmsg;
    }

    public void setStopmsg(String stopmsg) {
        this.stopmsg = stopmsg;
    }

   
    

   
 
    
}
