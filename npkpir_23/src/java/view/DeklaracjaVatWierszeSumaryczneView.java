/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjaVatWierszSumarycznyDAO;
import entity.DeklaracjaVatWierszSumaryczny;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class DeklaracjaVatWierszeSumaryczneView implements Serializable {
    @Inject
    private DeklaracjaVatWierszSumarycznyDAO deklaracjaVatWierszSumarycznyDAO;
    private List<DeklaracjaVatWierszSumaryczny> wierszesumarycznelista;
    @Inject
    private DeklaracjaVatWierszSumaryczny nazwawierszasumarycznego;

    public DeklaracjaVatWierszeSumaryczneView() {
    }
    
    @PostConstruct
    private void init() { //E.m(this);
        wierszesumarycznelista = deklaracjaVatWierszSumarycznyDAO.findAll();
    }
    
    public void dodajwiersz() {
        try {
            deklaracjaVatWierszSumarycznyDAO.create(nazwawierszasumarycznego);
            wierszesumarycznelista.add(nazwawierszasumarycznego);
            nazwawierszasumarycznego = new DeklaracjaVatWierszSumaryczny();
            Msg.msg("Dodano nowy wiersz sumaryczny");
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public void usun(DeklaracjaVatWierszSumaryczny i) {
        deklaracjaVatWierszSumarycznyDAO.remove(i);
        wierszesumarycznelista.remove(i);
        Msg.msg("Usunięto wiersz sumaryczny");
    }

    public DeklaracjaVatWierszSumarycznyDAO getDeklaracjaVatWierszSumarycznyDAO() {
        return deklaracjaVatWierszSumarycznyDAO;
    }

    public void setDeklaracjaVatWierszSumarycznyDAO(DeklaracjaVatWierszSumarycznyDAO deklaracjaVatWierszSumarycznyDAO) {
        this.deklaracjaVatWierszSumarycznyDAO = deklaracjaVatWierszSumarycznyDAO;
    }

    public List<DeklaracjaVatWierszSumaryczny> getWierszesumarycznelista() {
        return wierszesumarycznelista;
    }

    public void setWierszesumarycznelista(List<DeklaracjaVatWierszSumaryczny> wierszesumarycznelista) {
        this.wierszesumarycznelista = wierszesumarycznelista;
    }

    public DeklaracjaVatWierszSumaryczny getNazwawierszasumarycznego() {
        return nazwawierszasumarycznego;
    }

    public void setNazwawierszasumarycznego(DeklaracjaVatWierszSumaryczny nazwawierszasumarycznego) {
        this.nazwawierszasumarycznego = nazwawierszasumarycznego;
    }
  
    
}
