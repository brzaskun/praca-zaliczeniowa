/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dao.FakturaDAO;
import dao.KlienciDAO;
import dao.PodatnikDAO;
import entity.Faktura;
import entity.Klienci;
import entity.Podatnik;
import java.io.Serializable;
import java.util.Iterator;
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
public class RozrachunkiKlientTaxmanView implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Faktura> faktury;
    @Inject
    private WpisView wpisView;
     @Inject
    private KlienciDAO klienciDAO;
     @Inject
    private FakturaDAO fakturaDAO;
     @Inject
     private PodatnikDAO podatnikDAO;
     
    @PostConstruct
    public void pobierzwszystkoKlienta() {
        Klienci klient = klienciDAO.findKlientByNip(wpisView.getPodatnikObiekt().getNip());
        Podatnik taxman = podatnikDAO.findPodatnikByNIP("8511005008");
        faktury = fakturaDAO.findbyKontrahentNipRok(klient.getNip(), taxman, wpisView.getRokWpisuSt());
        faktury.addAll(fakturaDAO.findbyKontrahentNipRok(klient.getNip(), taxman, wpisView.getRokUprzedniSt()));
        for (Iterator<Faktura> it = faktury.iterator(); it.hasNext();) {
            Faktura f = it.next();
            if (f.isRozrachunekarchiwalny()) {
                it.remove();
            }
        }
        Msg.msg("Pobrano dane kontrahenta");

    }

    public List<Faktura> getFaktury() {
        return faktury;
    }

    public void setFaktury(List<Faktura> faktury) {
        this.faktury = faktury;
    }
    
    
    
    
}
