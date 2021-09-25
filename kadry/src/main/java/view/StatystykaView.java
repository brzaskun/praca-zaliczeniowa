/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AngazFacade;
import dao.NieobecnoscFacade;
import entity.Nieobecnosc;
import entity.Pracownik;
import entity.Umowa;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.model.timeline.TimelineEvent;
import org.primefaces.model.timeline.TimelineModel;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class StatystykaView implements Serializable {
    private static final long serialVersionUID = 1L;
    private TimelineModel<String, ?> umowy;
    private TimelineModel<String, ?> nieobecnosci;
    private LocalDateTime start;
    private LocalDateTime end;
    @Inject
    private WpisView wpisView;
    @Inject 
    private AngazFacade angazFacade;
    @Inject
    private NieobecnoscFacade nieobecnoscFacade;
    
    

    @PostConstruct
    public void init() {
        // set initial start / end dates for the axis of the timeline
        start = LocalDate.of(2012, 1, 1).atStartOfDay();
        end = LocalDate.of(2025, 1, 2).atStartOfDay();
        List<Pracownik> pracownicy = angazFacade.findPracownicyByFirma(wpisView.getFirma());
        // create timeline umowy
        umowy = new TimelineModel<>();
        nieobecnosci = new TimelineModel<>();

        for (Pracownik pracownik : pracownicy) {
            List<Umowa> umow = pracownik.getUmowy();
            for (Umowa umowa : umow) {
                LocalDate start = LocalDate.parse(umowa.getDataod());
                LocalDate end = LocalDate.parse(umowa.getDatado()!=null&&!umowa.getDatado().equals("")?umowa.getDatado():"2025-12-31");
                end = end.plusDays(1l);
                // create an event with content, start / end dates, editable flag, group name and custom style class
                String opis = umowa.getUmowakodzus()!=null?umowa.getUmowakodzus().getOpis():"";
                String data = umowa.getNrkolejny()+" "+opis;
                String stylklasa = umowa.getUmowakodzus()==null || umowa.getUmowakodzus().isPraca()?"available":"maybe";
                TimelineEvent event = TimelineEvent.builder()
                        .data(data)
                        .startDate(start)
                        .endDate(end)
                        .editable(true)
                        .group(pracownik.getNazwiskoImie())
                        .styleClass(stylklasa)
                        .build();

                umowy.add(event);
            }
        }
        for (Pracownik pracownik : pracownicy) {
            List<Nieobecnosc> nieob = pracownik.getNieobecnosci();
            for (Nieobecnosc nieobecnosc : nieob) {
                LocalDate start = LocalDate.parse(nieobecnosc.getDataod());
                LocalDate end = LocalDate.parse(nieobecnosc.getDatado()!=null&&!nieobecnosc.getDatado().equals("")?nieobecnosc.getDatado():"2025-12-31");
                end = end.plusDays(1l);
                // create an event with content, start / end dates, editable flag, group name and custom style class
                String opis = nieobecnosc.getKodzwolnienia()!=null?nieobecnosc.getKodzwolnienia():"";
                String data = nieobecnosc.getOpis()+" "+opis;
                String stylklasa = nieobecnosc.getKodzwolnienia().equals("U")?"available":"maybe";
                TimelineEvent event = TimelineEvent.builder()
                        .data(data)
                        .startDate(start)
                        .endDate(end)
                        .editable(true)
                        .group(pracownik.getNazwiskoImie())
                        .styleClass(stylklasa)
                        .build();

                nieobecnosci.add(event);
            }
        }
    }

    public TimelineModel<String, ?> getUmowy() {
        return umowy;
    }

    public TimelineModel<String, ?> getNieobecnosci() {
        return nieobecnosci;
    }

    public LocalDateTime getStart() {
        return start;
    }

    public LocalDateTime getEnd() {
        return end;
    }
}
