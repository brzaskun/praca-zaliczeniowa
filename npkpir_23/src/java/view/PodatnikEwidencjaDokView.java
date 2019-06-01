/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EvewidencjaDAO;
import dao.PodatnikEwidencjaDokDAO;
import entity.Evewidencja;
import entity.Podatnik;
import entity.PodatnikEwidencjaDok;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PodatnikEwidencjaDokView  implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<PodatnikEwidencjaDok> lista;
        @Inject
    private PodatnikEwidencjaDokDAO podatnikEwidencjaDokDAO;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
    @PostConstruct
    private void init() {
        List<Evewidencja> ewidencje = evewidencjaDAO.findAll();
        for (Iterator<Evewidencja> it = ewidencje.iterator(); it.hasNext();) {
            Evewidencja dopor = it.next();
            if (!dopor.getNazwa().startsWith("sprzedaż")) {
                it.remove();
            }
        }
        lista = podatnikEwidencjaDokDAO.findOpodatkowaniePodatnik(wpisView.getPodatnikObiekt());
        if (lista==null || lista.size()==0) {
            lista = new ArrayList<>();
            lista.addAll(zrobnowepozycje(ewidencje, wpisView.getPodatnikObiekt()));
        }
    }

    public void check(PodatnikEwidencjaDok pa) {
        try {
            boolean czyjuzjest = false;
            for (PodatnikEwidencjaDok p : lista) {
                if (p.getKolejnosc()==pa.getKolejnosc()) {
                    czyjuzjest = true;
                    break;
                }
            }
            if (czyjuzjest) {
                Msg.msg("e", "Błąd. Pozycja o takim numerze kolejnym już istnieje!");
            } else {
                podatnikEwidencjaDokDAO.editList(lista);
                Msg.msg("Przetworzono pozycje "+pa.getEwidencja().getNazwa());
            }
        } catch (Exception e) {
            Msg.msg("e", "Błąd. Nie dodano pozycje");
        }
    }
    
    private Collection<? extends PodatnikEwidencjaDok> zrobnowepozycje(List<Evewidencja> ewidencje, Podatnik podatnik) {
        List<PodatnikEwidencjaDok> zwrot = new ArrayList<>();
        for (Evewidencja p : ewidencje) {
            zwrot.add(new PodatnikEwidencjaDok(podatnik, p));
        }
        return zwrot;
    }
    
    public List<PodatnikEwidencjaDok> getLista() {
        return lista;
    }

    public void setLista(List<PodatnikEwidencjaDok> lista) {
        this.lista = lista;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    
    
    
}
