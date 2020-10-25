/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EvewidencjaDAO;
import dao.JPKVATWersjaDAO;
import dao.JPKvatwersjaEvewidencjaDAO;
import entity.Evewidencja;
import entity.JPKVATWersja;
import entity.JPKvatwersjaEvewidencja;
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
public class JPKvatwersjaEvewidencjaView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private JPKvatwersjaEvewidencjaDAO jPKvatwersjaEvewidencjaDAO;
    @Inject
    private JPKVATWersjaDAO jPKVATWersjaDAO;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    private List<JPKvatwersjaEvewidencja> lista;
    private JPKVATWersja jPKVATWersja;
    private JPKVATWersja jPKVATWersjacopy;
    private JPKvatwersjaEvewidencja selected;
    private List<JPKVATWersja> listajpkwersja;
    @Inject
    private JPKvatwersjaEvewidencja nowy;
        
    @PostConstruct
    private void init() {
        listajpkwersja = jPKVATWersjaDAO.findAll();
        jPKVATWersja = listajpkwersja.get(listajpkwersja.size()-2);
        lista = jPKvatwersjaEvewidencjaDAO.findJPKEwidencje(jPKVATWersja);
    }

    public void pobierzjpkewidencja() {
        List<Evewidencja> ewidencjevat = evewidencjaDAO.findAll();
        lista = jPKvatwersjaEvewidencjaDAO.findJPKEwidencje(jPKVATWersja);
        List<Evewidencja> uzupelnioneewidencje = Collections.synchronizedList(new ArrayList<>());
        for (JPKvatwersjaEvewidencja p : lista) {
            uzupelnioneewidencje.add(p.getEvewidencja());
        }
        List<Evewidencja> ewidencjedododania = Collections.synchronizedList(new ArrayList<>());
        for (Evewidencja r : ewidencjevat) {
            if (uzupelnioneewidencje.contains(r)) {
                uzupelnioneewidencje.remove(r);
            } else {
                ewidencjedododania.add(r);
            }
        }
        if (!ewidencjedododania.isEmpty()) {
            for (Evewidencja s : ewidencjedododania) {
                JPKvatwersjaEvewidencja nowaschemaewidencja = new JPKvatwersjaEvewidencja(jPKVATWersja, s);
                lista.add(nowaschemaewidencja);
            }
        }
    }
    
    public void kopiujschemaprzyporzadkowania() {
        if (jPKVATWersja!=null && jPKVATWersjacopy!=null && !jPKVATWersja.equals(jPKVATWersjacopy)) {
            List<JPKvatwersjaEvewidencja> listaa = jPKvatwersjaEvewidencjaDAO.findJPKEwidencje(jPKVATWersja);
            List<JPKvatwersjaEvewidencja> docelowa = new ArrayList<>();
            for (JPKvatwersjaEvewidencja p : listaa) {
                JPKvatwersjaEvewidencja nowy = new JPKvatwersjaEvewidencja(p, jPKVATWersjacopy);
                docelowa.add(nowy);
            }
            jPKvatwersjaEvewidencjaDAO.editList(docelowa);
            Msg.msg("Skopiowano scheme jpk");
        } else {
            Msg.msg("e","Nie wybrano schem");
        }
    }
    
    public void zachowaj() {
        for (JPKvatwersjaEvewidencja p : lista) {
            if (p.getPolejpk_netto_sprzedaz().equals("")) {
                p.setPolejpk_netto_sprzedaz(null);
            }
            if (p.getPolejpk_vat_sprzedaz().equals("")) {
                p.setPolejpk_vat_sprzedaz(null);
            }
            if (p.getPolejpk_netto_sprzedaz_suma().equals("")) {
                p.setPolejpk_netto_sprzedaz_suma(null);
            }
            if (p.getPolejpk_netto_zakup().equals("")) {
                p.setPolejpk_netto_zakup(null);
            }
            if (p.getPolejpk_vat_zakup().equals("")) {
                p.setPolejpk_vat_zakup(null);
            }
            if (p.getPolejpk_vat_sprzedaz_suma().equals("")) {
                p.setPolejpk_vat_sprzedaz_suma(null);
            }
        }
        jPKvatwersjaEvewidencjaDAO.editList(lista);
        Msg.msg("Zachowano pozycje");
    }
    
    public List<JPKvatwersjaEvewidencja> getLista() {
        return lista;
    }

    public void setLista(List<JPKvatwersjaEvewidencja> lista) {
        this.lista = lista;
    }

    public JPKvatwersjaEvewidencja getSelected() {
        return selected;
    }

    public void setSelected(JPKvatwersjaEvewidencja selected) {
        this.selected = selected;
    }

    public List<JPKVATWersja> getListajpkwersja() {
        return listajpkwersja;
    }

    public void setListajpkwersja(List<JPKVATWersja> listajpkwersja) {
        this.listajpkwersja = listajpkwersja;
    }

   


    public JPKvatwersjaEvewidencja getNowy() {
        return nowy;
    }

    public void setNowy(JPKvatwersjaEvewidencja nowy) {
        this.nowy = nowy;
    }

    public JPKVATWersja getJPKVATWersja() {
        return jPKVATWersja;
    }

    public void setJPKVATWersja(JPKVATWersja jPKVATWersja) {
        this.jPKVATWersja = jPKVATWersja;
    }

    public JPKVATWersja getJPKVATWersjacopy() {
        return jPKVATWersjacopy;
    }

    public void setJPKVATWersjacopy(JPKVATWersja jPKVATWersjacopy) {
        this.jPKVATWersjacopy = jPKVATWersjacopy;
    }
    
    
}
