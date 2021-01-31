/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturaDAO;
import dao.FakturaDuplikatDAO;
import entity.Faktura;
import entity.FakturaDuplikat;
import error.E;
import java.io.Serializable;
import java.util.List;
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
public class FakturaDuplikatView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private FakturaDuplikatDAO fakturaDuplikatDAO;
    @Inject
    private FakturaDAO fakturaDAO;
    
    public void generujduplikat(List<Faktura> gosciwybralokres) {
        if (gosciwybralokres != null && gosciwybralokres.size()==1) {
            Faktura f = gosciwybralokres.get(0);
            FakturaDuplikat duplikat = new FakturaDuplikat();
            duplikat.setDokument(f);
            duplikat.setDatawystawienia(data.Data.aktualnaData());
            fakturaDuplikatDAO.create(duplikat);
            f.getDuplikaty().add(duplikat);
            fakturaDAO.edit(f);
            msg.Msg.msg("Wygenerowano duplikat do faktury. Kliknij na strzałkę przy fakturze "+f.getNumerkolejny());
        }
    }
    
    public void usunDuplikat(Faktura selected, FakturaDuplikat duplikat) {
        try {
            selected.getDuplikaty().remove(duplikat);
            fakturaDAO.edit(selected);
            Msg.msg("Usunięto duplikat");
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd. Nie usunięto duplikatu.");
            E.e(e);
        }
    }
    
}
