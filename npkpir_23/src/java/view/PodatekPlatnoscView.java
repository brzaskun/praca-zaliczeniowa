/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dao.PodatekPlatnoscDAO;
import entity.PodatekPlatnosc;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class PodatekPlatnoscView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<PodatekPlatnosc> listaPodatekPlatnosc;
    private PodatekPlatnosc podatekPlatnosc;
    @Inject
    private PodatekPlatnoscDAO podatekPlatnoscDAO;
    @Inject
    private WpisView wpisView;
    private double sumawplat;
    
    
    @PostConstruct
    public void init() {
        podatekPlatnosc = new PodatekPlatnosc();
        podatekPlatnosc.setPodatnik(wpisView.getPodatnikObiekt());
        podatekPlatnosc.setRok(wpisView.getRokWpisuSt());
        listaPodatekPlatnosc = podatekPlatnoscDAO.findAll();
        sumujkwoty();
    }

    public void create() {
        try {
            podatekPlatnoscDAO.create(podatekPlatnosc);
            listaPodatekPlatnosc.add(podatekPlatnosc);
            sumujkwoty();
            podatekPlatnosc = new PodatekPlatnosc();
            podatekPlatnosc.setPodatnik(wpisView.getPodatnikObiekt());
            podatekPlatnosc.setRok(wpisView.getRokWpisuSt());
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Podatek Platnosc dodany"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd", "Nie udało się dodać podatku"));
        }
    }

    public void onRowEdit(RowEditEvent event) {
        PodatekPlatnosc editedPodatekPlatnosc = (PodatekPlatnosc) event.getObject();
        try {
            podatekPlatnoscDAO.edit(editedPodatekPlatnosc);
            sumujkwoty();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Podatek Platnosc zaktualizowany"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd", "Nie udało się zaktualizować podatku"));
        }
    }

    public void onRowEditCancel(RowEditEvent event) {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Edycja anulowana"));
    }

    public void delete(PodatekPlatnosc podatekPlatnosc) {
        try {
            podatekPlatnoscDAO.remove(podatekPlatnosc);
            listaPodatekPlatnosc.remove(podatekPlatnosc);
            sumujkwoty();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Podatek Platnosc usunięty"));
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd", "Nie udało się usunąć podatku"));
        }
    }
    
    // Metoda sumująca kwoty z listy
    public void sumujkwoty() {
        sumawplat = listaPodatekPlatnosc.stream()
                .map(PodatekPlatnosc::getKwota)
                .filter(kwota -> kwota != null)
                .reduce(BigDecimal.ZERO, BigDecimal::add).doubleValue();
    }

    public List<PodatekPlatnosc> getListaPodatekPlatnosc() {
        return listaPodatekPlatnosc;
    }

    public void setListaPodatekPlatnosc(List<PodatekPlatnosc> listaPodatekPlatnosc) {
        this.listaPodatekPlatnosc = listaPodatekPlatnosc;
    }

    public PodatekPlatnosc getPodatekPlatnosc() {
        return podatekPlatnosc;
    }

    public void setPodatekPlatnosc(PodatekPlatnosc podatekPlatnosc) {
        this.podatekPlatnosc = podatekPlatnosc;
    }

    public double getSumawplat() {
        return sumawplat;
    }

    public void setSumawplat(double sumawplat) {
        this.sumawplat = sumawplat;
    }
    
    
    
}
