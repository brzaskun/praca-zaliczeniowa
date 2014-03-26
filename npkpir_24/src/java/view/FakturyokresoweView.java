/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Dokcomparator;
import dao.DokDAO;
import dao.FakturyokresoweDAO;
import entity.Dok;
import entity.Fakturyokresowe;
import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class FakturyokresoweView implements Serializable{
    private List<Fakturyokresowe> faktury;
    @Inject private FakturyokresoweDAO fakturyokresoweDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    private Fakturyokresowe selected;
    @Inject private DokDAO dokDAO;
    //tablica obiektw danego klienta
    private List<Dok> dokumenty;

    public FakturyokresoweView() {
        faktury = new ArrayList<>();
        dokumenty = new ArrayList<>();
    }
    
    @PostConstruct
    private void init(){
        faktury.addAll(fakturyokresoweDAO.findPodatnik(wpisView.getPodatnikWpisu()));
        przeliczfaktury();
    }
    
    public void dodajfaktureokresowa(){
        Fakturyokresowe fakturyokresowe = new Fakturyokresowe();
        fakturyokresowe.setDokument(DokTabView.getGosciuwybralS().get(0));
        fakturyokresowe.setPodatnik(wpisView.getPodatnikWpisu());
        fakturyokresoweDAO.dodaj(fakturyokresowe);
        Msg.msg("i", "Dodano fakturę okresową");
    }

    public void usunfaktureokresowa(){
        faktury.remove(selected);
        fakturyokresoweDAO.destroy(selected);
        Msg.msg("i", "Usunięto fakturę okresową");
    }//<editor-fold defaultstate="collapsed" desc="comment">
    public List<Fakturyokresowe> getFaktury() {
        return faktury;
    }

    public void setFaktury(List<Fakturyokresowe> faktury) {
        this.faktury = faktury;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public Fakturyokresowe getSelected() {
        return selected;
    }

    public void setSelected(Fakturyokresowe selected) {
        this.selected = selected;
    }
    
    
    //</editor-fold>

    public void przeliczfaktury(){
            Integer rok = wpisView.getRokWpisu();
            String mc = wpisView.getMiesiacWpisu();
            String podatnik = wpisView.getPodatnikWpisu();
            Podatnik pod = wpisView.getPodatnikObiekt();
            try {
                dokumenty.addAll(dokDAO.zwrocBiezacegoKlientaRok(podatnik, rok.toString()));
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            for(Dok p : dokumenty){
                for (Fakturyokresowe r : faktury){
                    boolean kontrahenci = false;
                    boolean typdokumentu = false;
                    if (r.getDokument().getKontr().getNip().equals(p.getKontr().getNip())){
                        kontrahenci = true;
                    }
                    if (r.getDokument().getTypdokumentu().equals(p.getTypdokumentu())){
                        typdokumentu = true;
                    }
                    if(kontrahenci && typdokumentu){
                        String miesiac = p.getPkpirM();
                        switch (miesiac) {
                            case "01" :
                                r.setM1(r.getM1()+1);
                                break;
                            case "02" :
                                r.setM2(r.getM2()+1);
                                break;
                            case "03" :
                                r.setM3(r.getM3()+1);
                                break;
                            case "04" :
                                r.setM4(r.getM4()+1);
                                break;
                            case "05" :
                                r.setM5(r.getM5()+1);
                                break;
                            case "06" :
                                r.setM6(r.getM6()+1);
                                break;
                            case "07" :
                                r.setM7(r.getM7()+1);
                                break;
                            case "08" :
                                r.setM8(r.getM8()+1);
                                break;
                            case "09" :
                                r.setM9(r.getM9()+1);
                                break;
                            case "10" :
                                r.setM10(r.getM10()+1);
                                break;
                            case "11":
                                r.setM11(r.getM11() + 1);
                                break;
                            case "12" :
                                r.setM12(r.getM12()+1);
                                break;
                        }
                    }
                }
            }

        }
    
    
    
}
