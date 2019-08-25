/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjaVatPozycjeKoncoweDAO;
import entity.DeklaracjaVatPozycjeKoncowe;
import error.E;
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
public class DeklaracjaVatPozycjeKoncoweView implements Serializable {
    @Inject
    private DeklaracjaVatPozycjeKoncoweDAO deklaracjaVatPozycjeKoncoweDAO;
    private List<DeklaracjaVatPozycjeKoncowe> wierszesumarycznelista;
    @Inject
    private DeklaracjaVatPozycjeKoncowe nazwawierszasumarycznego;

    public DeklaracjaVatPozycjeKoncoweView() {
    }
    
    @PostConstruct
    private void init() { //E.m(this);
        wierszesumarycznelista = deklaracjaVatPozycjeKoncoweDAO.findAll();
    }
    
    public void dodajwiersz() {
        try {
            deklaracjaVatPozycjeKoncoweDAO.dodaj(nazwawierszasumarycznego);
            wierszesumarycznelista.add(nazwawierszasumarycznego);
            nazwawierszasumarycznego = new DeklaracjaVatPozycjeKoncowe();
            Msg.msg("Dodano nowy wiersz sumaryczny");
        } catch (Exception e) {
            E.e(e);
        }
    }
    
    public void usun(DeklaracjaVatPozycjeKoncowe i) {
        deklaracjaVatPozycjeKoncoweDAO.destroy(i);
        wierszesumarycznelista.remove(i);
        Msg.msg("Usunięto wiersz sumaryczny");
    }

    public DeklaracjaVatPozycjeKoncoweDAO getDeklaracjaVatPozycjeKoncoweDAO() {
        return deklaracjaVatPozycjeKoncoweDAO;
    }

    public void setDeklaracjaVatPozycjeKoncoweDAO(DeklaracjaVatPozycjeKoncoweDAO deklaracjaVatPozycjeKoncoweDAO) {
        this.deklaracjaVatPozycjeKoncoweDAO = deklaracjaVatPozycjeKoncoweDAO;
    }

    public List<DeklaracjaVatPozycjeKoncowe> getWierszesumarycznelista() {
        return wierszesumarycznelista;
    }

    public void setWierszesumarycznelista(List<DeklaracjaVatPozycjeKoncowe> wierszesumarycznelista) {
        this.wierszesumarycznelista = wierszesumarycznelista;
    }

    public DeklaracjaVatPozycjeKoncowe getNazwawierszasumarycznego() {
        return nazwawierszasumarycznego;
    }

    public void setNazwawierszasumarycznego(DeklaracjaVatPozycjeKoncowe nazwawierszasumarycznego) {
        this.nazwawierszasumarycznego = nazwawierszasumarycznego;
    }

   

    
    
}
