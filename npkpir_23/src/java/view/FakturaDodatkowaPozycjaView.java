/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaDodatkowaPozycjaDAO;
import entity.FakturaDodatkowaPozycja;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class FakturaDodatkowaPozycjaView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private FakturaDodatkowaPozycjaDAO fakturaDodatkowaPozycjaDAO;
    private List<FakturaDodatkowaPozycja> lista;
    @Inject
    private FakturaDodatkowaPozycja selected;
    
    @PostConstruct
    private void init() {
        lista = fakturaDodatkowaPozycjaDAO.findAll();
    }
    
    public void dodaj() {
        if (selected.getNazwa()!=null && selected.getKwota() > 0.0) {
            try {
                selected.setAktywny(true);
                fakturaDodatkowaPozycjaDAO.create(selected);
                lista.add(selected);
                selected = new FakturaDodatkowaPozycja();
                Msg.msg("Zapisano nową pozycję");
            } catch (Exception e) {
                Msg.msg("e","Taka nazwa już istnieje");
            }
        } else {
            Msg.msg("e","Nie wprowadzono nazwy/kwoty. Nie można zapisać");
        }
    }
    
    public void usun(FakturaDodatkowaPozycja sel) {
        if (sel !=null) {
            try {
                fakturaDodatkowaPozycjaDAO.remove(sel);
                lista.remove(sel);
                Msg.msg("Usunięto pozycję");
            } catch (Exception e) {
                Msg.msg("e","Nieudane usunięcie pozycji");
            }
        } else {
            Msg.msg("e","Nie wybrano pozycji. Nie można usunąć");
        }
    }
    
    public void edytuj(FakturaDodatkowaPozycja sel) {
        if (sel !=null) {
            try {
                fakturaDodatkowaPozycjaDAO.edit(sel);
                Msg.msg("Edytowano pozycję");
            } catch (Exception e) {
                Msg.msg("e","Nieudana edycja pozycji");
            }
        } else {
            Msg.msg("e","Nie wybrano pozycji. Nie można edytować");
        }
    }

    public List<FakturaDodatkowaPozycja> getLista() {
        return lista;
    }

    public void setLista(List<FakturaDodatkowaPozycja> lista) {
        this.lista = lista;
    }

    public FakturaDodatkowaPozycja getSelected() {
        return selected;
    }

    public void setSelected(FakturaDodatkowaPozycja selected) {
        this.selected = selected;
    }
    
    
}
