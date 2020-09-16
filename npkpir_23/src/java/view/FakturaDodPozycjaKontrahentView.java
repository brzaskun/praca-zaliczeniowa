/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import comparator.Klienci1comparator;
import dao.FakturaDodPozycjaKontrahentDAO;
import dao.FakturaDodatkowaPozycjaDAO;
import dao.FakturywystokresoweDAO;
import dao.PodatnikDAO;
import embeddable.Mce;
import entity.FakturaDodPozycjaKontrahent;
import entity.FakturaDodatkowaPozycja;
import entity.Fakturywystokresowe;
import entity.Klienci;
import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
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
public class FakturaDodPozycjaKontrahentView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private FakturaDodPozycjaKontrahentDAO fakturaDodPozycjaKontrahentDAO;
    @Inject
    private FakturaDodatkowaPozycjaDAO fakturaDodatkowaPozycjaDAO;
    @Inject
    private FakturywystokresoweDAO fakturywystokresoweDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    private List<Klienci> klienci;
    private List<FakturaDodatkowaPozycja> pozycje;
    private List<FakturaDodPozycjaKontrahent> lista_wzor;
    private List<FakturaDodPozycjaKontrahent> lista_2;
    private List<FakturaDodPozycjaKontrahent> lista_2_filter;
    @Inject
    private FakturaDodPozycjaKontrahent selected;
    private String rok;
    private String mc;
    private double sumawybranych;
    
    @PostConstruct
    private void init() {
        pozycje = fakturaDodatkowaPozycjaDAO.findAll();
        lista_wzor = fakturaDodPozycjaKontrahentDAO.findAll();
        lista_2 = new ArrayList<>();
        lista_2.addAll(lista_wzor);
    }
    
    public void pobierzklientow() {
        klienci = new ArrayList<>();
        if (mc!=null && rok!=null) {
            List<Fakturywystokresowe> wykazfaktur = fakturywystokresoweDAO.findPodatnikBiezace("GRZELCZYK", rok);
            for (Fakturywystokresowe p : wykazfaktur) {
                Podatnik pod = podatnikDAO.findPodatnikByNIP(p.getDokument().getKontrahent().getNip());
                if (pod != null) {
                    Klienci k = p.getDokument().getKontrahent();
                    k.setJezykwysylki(pod.getJezykmaila());
                    klienci.add(k);
                }
            }
        }
        Collections.sort(klienci,new Klienci1comparator());
        pozycje = fakturaDodatkowaPozycjaDAO.findAll();
        if (rok!=null&&mc!=null) {
            List<FakturaDodPozycjaKontrahent> lista_tmp = lista_wzor.stream().filter(p->p.getRok().equals(rok)&&p.getMc().equals(mc)).collect(Collectors.toList());
            lista_2 = new ArrayList<>();
            lista_2.addAll(lista_tmp);
        }
    }
    
    public void dodaj() {
        if (selected.getKontrahent()!=null && selected.getFakturaDodatkowaPozycja() !=null && selected.getIlosc()>0) {
            try {
                selected.setRok(rok);
                selected.setMc(mc);
                fakturaDodPozycjaKontrahentDAO.dodaj(selected);
                lista_2.add(selected);
                selected = new FakturaDodPozycjaKontrahent();
                Msg.msg("Zapisano nową pozycję");
            } catch (Exception e) {
                Msg.msg("e","Taka nazwa już istnieje");
            }
        } else {
            Msg.msg("e","Nie wprowadzono kontrahenta/pozycji/ilości. Nie można zapisać");
        }
    }
    
    public void usun(FakturaDodPozycjaKontrahent sel) {
        if (sel !=null) {
            try {
                fakturaDodPozycjaKontrahentDAO.destroy(sel);
                lista_2.remove(sel);
                Msg.msg("Usunięto pozycję");
            } catch (Exception e) {
                Msg.msg("e","Nieudane usunięcie pozycji");
            }
        } else {
            Msg.msg("e","Nie wybrano pozycji. Nie można usunąć");
        }
    }
    
    public void sumujwybrane() {
        List<FakturaDodPozycjaKontrahent> lista = lista_2_filter!=null && lista_2_filter.size()>0 ? lista_2_filter : lista_2;
        sumawybranych = 0.0;
        for (FakturaDodPozycjaKontrahent p : lista) {
            sumawybranych = sumawybranych+p.getFakturaDodatkowaPozycja().getKwota();
        }
        Msg.msg("Podsumowano");
    }

    public FakturaDodPozycjaKontrahent getSelected() {
        return selected;
    }

    public void setSelected(FakturaDodPozycjaKontrahent selected) {
        this.selected = selected;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public List<Klienci> getKlienci() {
        return klienci;
    }

    public void setKlienci(List<Klienci> klienci) {
        this.klienci = klienci;
    }

    public List<FakturaDodatkowaPozycja> getPozycje() {
        return pozycje;
    }

    public void setPozycje(List<FakturaDodatkowaPozycja> pozycje) {
        this.pozycje = pozycje;
    }

    public List<FakturaDodPozycjaKontrahent> getLista_2() {
        return lista_2;
    }

    public void setLista_2(List<FakturaDodPozycjaKontrahent> lista_2) {
        this.lista_2 = lista_2;
    }

    public List<FakturaDodPozycjaKontrahent> getLista_2_filter() {
        return lista_2_filter;
    }

    public void setLista_2_filter(List<FakturaDodPozycjaKontrahent> lista_2_filter) {
        this.lista_2_filter = lista_2_filter;
    }

    public double getSumawybranych() {
        return sumawybranych;
    }

    public void setSumawybranych(double sumawybranych) {
        this.sumawybranych = sumawybranych;
    }
    
    
}
