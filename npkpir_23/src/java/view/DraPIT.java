/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import daoplatnik.UbezpZusrcaDAO;
import daoplatnik.ZusdraDAO;
import daoplatnik.ZusrcaDAO;
import embeddable.DRASumy;
import entityplatnik.UbezpZusrca;
import entityplatnik.Zusdra;
import entityplatnik.Zusrca;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class DraPIT implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<DRASumy> drasumy;
    private DRASumy selected1;
    @Inject
    private WpisView wpisView;
    @Inject
    private ZusdraDAO zusdraDAO;
    @Inject
    private ZusrcaDAO zusrcaDAO;
    @Inject
    private UbezpZusrcaDAO ubezpZusrcaDAO;
    
    @PostConstruct
    private void init() {
        pobierz();
    }
    
    public void pobierz() {
        drasumy = new ArrayList<>();
        String okres = "12"+wpisView.getRokUprzedniSt();
        List<Zusdra> zusdrapoprzednirok = zusdraDAO.findByOkresNip(okres, wpisView.getPodatnikObiekt().getNip());
        List<Zusdra> zusdrabiezacyrok = zusdraDAO.findByRokNip(wpisView.getRokWpisuSt(), wpisView.getPodatnikObiekt().getNip());
        List<Zusdra> zusdra = new ArrayList<>();
        if (zusdrapoprzednirok!=null) {
            zusdra.addAll(przetworzZusdra(zusdrapoprzednirok));
        }
        if (zusdrabiezacyrok!=null) {
            zusdra.addAll(przetworzZusdra(zusdrabiezacyrok));
        }
        List<Zusrca> zusrcapoprzednirok = zusrcaDAO.findByOkresNip(okres, wpisView.getPodatnikObiekt().getNip());
        List<Zusrca> zusrcabiezacyrok = zusrcaDAO.findByRokNip(wpisView.getRokWpisuSt(), wpisView.getPodatnikObiekt().getNip());
        List<Zusrca> zusrca = new ArrayList<>();
        if (zusrcapoprzednirok!=null) {
            zusrca.addAll(zusrcapoprzednirok);
        }
        if (zusrcabiezacyrok!=null) {
            zusrca.addAll(zusrcabiezacyrok);
        }
        if (!zusdra.isEmpty()) {
            int i = 1;
            for (Zusdra p : zusdra) {
                DRASumy dras = new DRASumy();
                dras.setId(i++);
                dras.setRok(pobierzrok(p.getI22okresdeklar()));
                dras.setMc(pobierzmc(p.getI22okresdeklar()));
                dras.setOkres(dras.getRok()+dras.getMc());
                dras.setZusdra(p);
                for (Zusrca r : zusrca) {
                    if (r.getI12okrrozl().equals(p.getI22okresdeklar()) && r.getIdPlatnik()==p.getIdPlatnik()) {
                        dras.setZusrca(r);
                        List<UbezpZusrca> zalezne = ubezpZusrcaDAO.findByIdDokNad(r.getIdDokument());
                        dras.setUbezpZusrca(zalezne);
                        break;
                    }
                }
                drasumy.add(dras);
            }
        }
        System.out.println("");
    }
    
    private List<Zusdra> przetworzZusdra(List<Zusdra> zusdra) {
        Map<String,Zusdra> nowe = new HashMap<>();
        if (zusdra!=null) {
            for (Zusdra p : zusdra) {
                if (!nowe.containsKey(p.getI22okresdeklar())) {
                    nowe.put(p.getI22okresdeklar(), p);
                } else {
                    Zusdra stara = nowe.get(p.getI22okresdeklar());
                    if (Integer.valueOf(stara.getI21iddekls())<Integer.valueOf(p.getI21iddekls())) {
                        nowe.remove(p.getI22okresdeklar());
                        nowe.put(p.getI22okresdeklar(), p);
                    }
                }
            }
        }
        return new ArrayList<Zusdra>(nowe.values());
    }
    
   

    private String pobierzrok(String i22okresdeklar) {
        return i22okresdeklar.substring(2);
    }

    private String pobierzmc(String i22okresdeklar) {
        return i22okresdeklar.substring(0,2);
    }

    public List<DRASumy> getDrasumy() {
        return drasumy;
    }

    public void setDrasumy(List<DRASumy> drasumy) {
        this.drasumy = drasumy;
    }

    public DRASumy getSelected1() {
        return selected1;
    }

    public void setSelected1(DRASumy selected1) {
        this.selected1 = selected1;
    }
    
    
}
