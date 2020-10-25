/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.JPKVATWersjaBean;
import dao.JPKVATWersjaDAO;
import entity.JPKVATWersja;
import java.io.Serializable;
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
public class JPKVATWersjaView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private JPKVATWersjaDAO jPKVATWersjaDAO;
    private List<JPKVATWersja> lista;
    private JPKVATWersja selected;
    @Inject
    private JPKVATWersja nowy;
    
    @PostConstruct
    private void init() {
        lista = jPKVATWersjaDAO.findAll();
    }
    
    public void skopiujschema() {
        nowy = selected;
        Msg.msg("Wybrano JPK "+selected.getNazwa());
    }
    
    public int dodajscheme() {
        int zwrot = 0;
        int czyschemaistnieje = JPKVATWersjaBean.sprawdzScheme(nowy, lista);
        if (czyschemaistnieje == 1) {
            Msg.msg("e", "Nie można dodać, taki JPK o takiej nazwie już istnieje.");
        } else if (czyschemaistnieje == 2) {
            Msg.msg("e", "Nie można dodać, niedopasowany okres JPK. Istnieją wcześniejsze.");
        } else if (czyschemaistnieje == 3) {
            Msg.msg("e", "Nie można dodać, brak nazwy nowego JPK.");
        } else if (czyschemaistnieje == 4) {
            Msg.msg("e", "Nie można dodać. Nazwa JPK nie rozpoczyna się od JPK pisanych wielką literą");
        } else {
            jPKVATWersjaDAO.dodaj(nowy);
            lista.add(nowy);
            nowy = new JPKVATWersja();
            Msg.msg("Dodano JPK");
            zwrot = 1;
        }
        return zwrot;
    }
    
    public void edytujscheme() {
        jPKVATWersjaDAO.edit(nowy);
        Msg.msg("Udana edycja JPK");
    }
    
    public void usun(JPKVATWersja s) {
        jPKVATWersjaDAO.destroy(s);
        lista.remove(s);
        Msg.msg("Usunieto JPK");
    }

    public List<JPKVATWersja> getLista() {
        return lista;
    }

    public void setLista(List<JPKVATWersja> lista) {
        this.lista = lista;
    }

    public JPKVATWersja getSelected() {
        return selected;
    }

    public void setSelected(JPKVATWersja selected) {
        this.selected = selected;
    }

    public JPKVATWersja getNowy() {
        return nowy;
    }

    public void setNowy(JPKVATWersja nowy) {
        this.nowy = nowy;
    }
    
    
}
