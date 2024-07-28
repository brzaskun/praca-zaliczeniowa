/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dao.FakturaDAO;
import dao.PodatnikDAO;
import data.Data;
import entity.Faktura;
import entity.Podatnik;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class CzyBiuroiSzefView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private FakturaDAO fakturaDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    private boolean biuroiszef;
    private Podatnik taxman;

    public boolean isBiuroiszef() {
        return true;
    }

    @PostConstruct
    private void czytojetsbiuroiszef() {
        try {
            biuroiszef = true;
            taxman = podatnikDAO.findPodatnikByNIP("8511005008");
            boolean czybiuro = wpisView.getPodatnikObiekt().getNip().equals("8511005008");
            if (czybiuro) {
                biuroiszef = false;
                boolean czyszef = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser().equals("szef");
                boolean czyrenata = FacesContext.getCurrentInstance().getExternalContext().getRemoteUser().equals("renata");
                if (czyszef || czyrenata) {
                    biuroiszef = true;
                }
            } else {
                try {
                    String[] nastepnyOkres = Data.nastepnyOkres(wpisView.getMiesiacWpisu(), wpisView.getRokWpisuSt());
                    List<Faktura> findOkresoweBiezace = fakturaDAO.findbyKontrahentNipRokMc(wpisView.getPodatnikObiekt().getNip(), taxman, wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
                    List<Faktura> findOkresoweOstatnie = fakturaDAO.findbyKontrahentNipRokMc(wpisView.getPodatnikObiekt().getNip(), taxman, nastepnyOkres[1], nastepnyOkres[0]);
                    String biezacyrok = Data.aktualnyRok();
                    boolean innyrok = wpisView.getRokWpisuSt().equals(biezacyrok) ? false : true;
                    if (wpisView.getPodatnikObiekt().isNiesprawdzajfaktury() == true || innyrok) {
                        biuroiszef = true;
                    } else if ((findOkresoweBiezace == null || findOkresoweBiezace.isEmpty()) && (findOkresoweOstatnie == null || findOkresoweOstatnie.isEmpty())) {
                        biuroiszef = false;
                    }
                } catch (Exception e) {
                    System.out.println("B\u0141AD*********************");
                    System.out.println(E.e(e));
                }
            }
        } catch (Exception e) {
        }
    }
    
}
