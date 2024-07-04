/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import dao.DokDAOfk;
import dao.KontoDAOfk;
import dao.PodatnikDAO;
import dao.RodzajedokDAO;
import entity.Podatnik;
import entity.Rodzajedok;
import entityfk.Dokfk;
import entityfk.Konto;
import entityfk.StronaWiersza;
import java.io.Serializable;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class NaprawyView implements Serializable {
private static final long serialVersionUID = 1L;
@Inject
private WpisView wpisView;
@Inject
private RodzajedokDAO rodzajedokDAO;
@Inject
private KontoDAOfk kontoDAOfk;
@Inject
private PodatnikDAO podatnikDAO;
@Inject
private DokDAOfk dokDAOfk;

public void naprawdokumenty() {
    List<Podatnik> podatnicy = podatnikDAO.findAktywny();
    String rok = "2024";
    for (Podatnik poda : podatnicy) {
        List<Dokfk> dokumentyrok = dokDAOfk.findDokfkPodatnikRok(poda, rok);
        if (dokumentyrok!=null&&dokumentyrok.size()>0) {
            List<Rodzajedok> rodzajedokumentowrok = rodzajedokDAO.findListaPodatnik(poda, rok);
            List<Konto> kontapodatnika = kontoDAOfk.findKontaOstAlityka(poda, Integer.valueOf(rok));

            for (Dokfk dok : dokumentyrok) {
                if (dok.getRodzajedok().getRok().equals(rok)==false) {
                    Rodzajedok rodzajaktualny = pobierzrodzajaktualny(rodzajedokumentowrok, dok.getRodzajedok());
                    if (rodzajaktualny!=null) {
                        dok.setRodzajedok(rodzajaktualny);
                    }
                    nanieskonta(kontapodatnika, dok, Integer.valueOf(rok));
                }
            }
            dokDAOfk.editList(dokumentyrok);
            System.out.println("podatnik "+poda.getPrintnazwa());
        }
    }
    System.out.println("koniec");
}

    private Rodzajedok pobierzrodzajaktualny(List<Rodzajedok> rodzajedokumentowrok, Rodzajedok rodzajstary) {
        Rodzajedok zwrot = null;
        for (Rodzajedok rodzaj : rodzajedokumentowrok) {
            if (rodzaj.getSkrot().equals(rodzajstary.getSkrot())) {
                zwrot = rodzaj;
                break;
            }
        }
        return zwrot;
    }

    private void nanieskonta(List<Konto> kontapodatnika, Dokfk dok, int rok) {
    List<StronaWiersza> stronyWierszy = dok.getStronyWierszy();
    for (StronaWiersza strona : stronyWierszy) {
        if (strona.getKonto().getRok()!=rok) {
            Konto nowekonto = pobierzaktualnekonto(kontapodatnika, strona.getKonto().getPelnynumer());
            if (nowekonto!=null) {
                strona.setKonto(nowekonto);
            }
            
        }
    }
    }

    private Konto pobierzaktualnekonto(List<Konto> kontapodatnika, String pelnynumer) {
        Konto zwrot = null;
        for (Konto konto : kontapodatnika) {
            if (konto.getPelnynumer().equals(pelnynumer)) {
                zwrot = konto;
                break;
            }
        }
        return zwrot;
    }
    
}
