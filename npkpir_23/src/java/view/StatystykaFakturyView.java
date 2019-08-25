/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.StatystykaDAO;
import embeddable.Roki;
import embeddable.StatystykaFaktury;
import entity.Statystyka;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class StatystykaFakturyView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private StatystykaDAO statystykaDAO;
    private List<StatystykaFaktury> lista;

    public StatystykaFakturyView() {
        this.lista = Collections.synchronizedList(new ArrayList<>());
    }
    
    @PostConstruct
    private void init() { //E.m(this);
        pobierz();
    }
       
    public void pobierz() {
        List<String> lata = Roki.getRokiListStr();
        for (String rok : lata) {
            List<Statystyka> podatnikroklista = statystykaDAO.findByRok(rok);
            int i = 1;
            for (Statystyka s : podatnikroklista) {
                dodajpozycje(s,i++);
            }
        }
        Msg.msg("Pobrano statystyki");
    }

 

    private void dodajpozycje(Statystyka s, int i) {
        int czyjest = czylistazawiera(s);
        if (czyjest == -1) {
            lista.add(stworz(s, i));
        } else {
            edytuj(s, czyjest);
        }
    }

    private int czylistazawiera(Statystyka s) {
        int zwrot = -1;
        for (StatystykaFaktury f : lista) {
            if (f.getPodatnik().equals(s.getPodatnik())) {
                zwrot = f.getId();
            }
        }
        return zwrot;
    }

    private StatystykaFaktury stworz(Statystyka s, int i) {
        StatystykaFaktury nowa = new StatystykaFaktury(s);
        nowa.setId(i);
        return nowa;
    }
    
    private void stworz(Statystyka s, StatystykaFaktury f) {
        f.getStatFaktRok().put(s.getRok(), new StatystykaFaktury.StatFaktRok(s));
    }
     
    private void edytuj(Statystyka s, int czyjest) {
        StatystykaFaktury f = lista.get(czyjest-1);
        stworz(s, f);
    }
     
    
       
//<editor-fold defaultstate="collapsed" desc="comment">    
  

    public List<StatystykaFaktury> getLista() {
        return lista;
    }

    public void setLista(List<StatystykaFaktury> lista) {
        this.lista = lista;
    }

    
//</editor-fold>

   

    

  

    
}
