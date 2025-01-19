/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import comparator.OddelegowanieDatycomparator;
import dao.AngazFacade;
import dao.OddelegowanieDatyFacade;
import entity.Angaz;
import entity.OddelegowanieDaty;
import entity.Pracownik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
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
public class PracownikOddelegowanieView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private OddelegowanieDatyFacade oddelegowanieDatyFacade;
    private List<OddelegowanieDaty> pracownicyoddelegowanie;
    
    public void init() {
       List<Angaz> listaangazy = angazFacade.findByFirma(wpisView.getFirma());
       pracownicyoddelegowanie = oddelegowanieDatyFacade.findFirmaRok(wpisView.getFirma(), wpisView.getRokWpisu());
       Set<Pracownik> listapracownikow = listaangazy.stream()
                      .map(Angaz::getPracownik)
                      .collect(Collectors.toSet());
        // Pobieramy wszystkich pracowników, którzy są już w liście pracownicyoddelegowanie
    Set<Pracownik> pracownicyJuzOddelegowani = pracownicyoddelegowanie.stream()
            .map(OddelegowanieDaty::getPracownik)
            .collect(Collectors.toSet());
        List<OddelegowanieDaty> noweRekordy = new ArrayList<>();
        
        for (Pracownik pracownik : listapracownikow) {
            if (pracownicyJuzOddelegowani.contains(pracownik)==false) {
                OddelegowanieDaty nowyRekord = new OddelegowanieDaty();
                nowyRekord.setFirmaKadry(wpisView.getFirma());
                nowyRekord.setPracownik(pracownik);
                nowyRekord.setRok(wpisView.getRokWpisu());

                noweRekordy.add(nowyRekord);
            }
        }

        if (!noweRekordy.isEmpty()) {
            //oddelegowanieDatyFacade.createList(noweRekordy);
            pracownicyoddelegowanie.addAll(noweRekordy);
        }
        if (pracownicyoddelegowanie!=null) {
            Collections.sort(pracownicyoddelegowanie, new OddelegowanieDatycomparator());
        }
       
    }
    
    public void zapisz(OddelegowanieDaty oddelegowanieDaty) {
        if (oddelegowanieDaty!=null) {
            oddelegowanieDaty.setWprowadzajacy(wpisView.getUzer().getImieNazwisko());
            oddelegowanieDaty.setDatawprowadzenia(new Date());
            oddelegowanieDatyFacade.edit(oddelegowanieDaty);
            Msg.msg("Zachowano zmiany");
        }
    }

    public List<OddelegowanieDaty> getPracownicyoddelegowanie() {
        return pracownicyoddelegowanie;
    }

    public void setPracownicyoddelegowanie(List<OddelegowanieDaty> pracownicyoddelegowanie) {
        this.pracownicyoddelegowanie = pracownicyoddelegowanie;
    }
    
    
}
