/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.UczestnicyFacade;
import entity.Uczestnicy;
import java.io.Serializable;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class StatystykaView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private List<Uczestnicy> listaosoby;
    private List<Uczestnicy> listaosobyselected;
    @Inject
    private UczestnicyFacade uczestnicyFacade;
    
    @PostConstruct
    private void init() {
        listaosoby = (List<Uczestnicy>) uczestnicyFacade.findAllLast().stream().limit(1000).collect(Collectors.toList());
        listaosoby = listaosoby.stream().filter(osoba -> osoba.getSessionend()!=null).collect(Collectors.toList());
    }
    
    public long getTotalCount(Date zak) {
        return listaosoby.stream().filter(osoba -> dateToInsttant(zak).equals(dateToInsttant(osoba.getSessionend()))).count();
    }

    public Instant dateToInsttant(Date date) {
        Instant instant2 = date.toInstant();
        return instant2.truncatedTo(ChronoUnit.DAYS);
    }
    
    public List<Uczestnicy> getListaosoby() {
        return listaosoby;
    }

    public void setListaosoby(List<Uczestnicy> listaosoby) {
        this.listaosoby = listaosoby;
    }

    public List<Uczestnicy> getListaosobyselected() {
        return listaosobyselected;
    }

    public void setListaosobyselected(List<Uczestnicy> listaosobyselected) {
        this.listaosobyselected = listaosobyselected;
    }

    
   
    public static void main(String[] args) {
         // If you have Date, then:
        Date date = new Date();
        Instant instant2 = date.toInstant();
        Instant day2 = instant2.truncatedTo(ChronoUnit.DAYS);
        System.out.println(day2); //2019-01-14T00:00:00Z
    }
    
    
}
