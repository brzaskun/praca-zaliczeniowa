/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.GrupakadryFacade;
import dao.UmowaFacade;
import entity.Angaz;
import entity.FirmaKadry;
import entity.Grupakadry;
import entity.Umowa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.model.DualListModel;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class GrupakadryView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Grupakadry> lista;
    private Grupakadry listaselected;
    private List<Grupakadry> lista2;
    private Grupakadry listaselected2;
    @Inject 
    private Grupakadry selected;
    @Inject
    private WpisView wpisView;
    @Inject
    private GrupakadryFacade grupakadryFacade;
    private org.primefaces.model.DualListModel<Umowa> listaumowy;
    @Inject
    private UmowaFacade umowaFacade;
    
    
    @PostConstruct
    private void init() {
        if (wpisView.getFirma()!=null) {
            lista = grupakadryFacade.findByFirma(wpisView.getFirma());
            lista2 = grupakadryFacade.findByFirma(wpisView.getFirma());
            listaumowy = new DualListModel<>();
        }
    }
    
    public void zapisz() {
        List<Umowa> source = listaumowy.getSource();
        List<Umowa> target = listaumowy.getTarget();
        List<Umowa> edyt = new ArrayList<>();
        for (Umowa p : source) {
            if (p.getGrupakadry()!=null) {
                p.setGrupakadry(null);
                edyt.add(p);
            }
        }
        for (Umowa p : target) {
            if (p.getGrupakadry()==null) {
                p.setGrupakadry(listaselected2);
                edyt.add(p);
            }
        }
        if (!edyt.isEmpty()) {
            umowaFacade.editList(edyt);
        }
        Msg.msg("Naniesiono zmiany");
    }

    public void pobierz(){
        if (listaselected2!=null) {
            List<Umowa> umowy = umowaFacade.findByFirma(wpisView.getFirma());
            umowy = umowy.stream().filter(p->p.isAktywna()).collect(Collectors.toList());
            List<Umowa> umowybezgrupy = umowy.stream().filter(p->p.getGrupakadry()==null).collect(Collectors.toList());
            List<Umowa> umowyzgrupa = umowy.stream().filter(p->p.getGrupakadry()!=null&&p.getGrupakadry().equals(listaselected2)).collect(Collectors.toList());
            listaumowy.setSource(umowybezgrupy!=null?umowybezgrupy:new ArrayList<>());
            listaumowy.setTarget(umowyzgrupa!=null&&umowyzgrupa.size()>0?umowyzgrupa:new ArrayList<>());
        }
    }
    
    public void create() {
        if (selected!=null) {
           selected.setFirma(wpisView.getFirma());
           grupakadryFacade.create(selected);
           if (lista==null) {
               lista = new ArrayList<>();
           } else {
               lista.add(selected);
           }
           if (lista2==null) {
               lista2 = new ArrayList<>();
           } else {
               lista2.add(selected);
           }
           Msg.msg("Utworzono grupę");
        } else {
            Msg.msg("e","Brak nazwy");
        }
        selected = new Grupakadry();
    }
    
    public void usun(Grupakadry g) {
        if (g!=null) {
            try {
                grupakadryFacade.remove(g);
                lista.remove(g);
            } catch (Exception e) {
                Msg.msg("e","Nie można usunąc są przyporządkowani pracownicy");
            }
        } else {
           Msg.msg("e","Nie wybrnao grupy");
        }
    }
    
    public void aktywujPracAneksy(FirmaKadry firma) {
        if (firma!=null) {
            wpisView.setFirma(firma);
            if (firma.getAngazList()==null||firma.getAngazList().isEmpty()) {
                wpisView.setPracownik(null);
                wpisView.setAngaz(null);
                wpisView.setUmowa(null);
            } else {
                Angaz angaz = firma.getAngazList().get(0);
                wpisView.setPracownik(angaz.getPracownik());
                wpisView.setAngaz(angaz);
                List<Umowa> umowy = angaz.getUmowaList();
                if (umowy!=null && umowy.size()==1) {
                    wpisView.setUmowa(umowy.get(0));
                } else if (umowy!=null) {
                    try {
                        wpisView.setUmowa(umowy.stream().filter(p->p.isAktywna()).findFirst().get());
                    } catch (Exception e){}
                }
            }
            //angazView.init();
            //pracodawcaDaneView.init();
            //pasekwynagrodzenView.init();
            init();
            Msg.msg("Aktywowano firmę "+firma.getNazwa());
        }
    }
    
    
    public List<Grupakadry> getLista() {
        return lista;
    }

    public void setLista(List<Grupakadry> lista) {
        this.lista = lista;
    }

    public Grupakadry getSelected() {
        return selected;
    }

    public void setSelected(Grupakadry selected) {
        this.selected = selected;
    }

    public Grupakadry getListaselected() {
        return listaselected;
    }

    public void setListaselected(Grupakadry listaselected) {
        this.listaselected = listaselected;
    }

    public DualListModel<Umowa> getListaumowy() {
        return listaumowy;
    }

    public void setListaumowy(DualListModel<Umowa> listaumowy) {
        this.listaumowy = listaumowy;
    }

    public List<Grupakadry> getLista2() {
        return lista2;
    }

    public void setLista2(List<Grupakadry> lista2) {
        this.lista2 = lista2;
    }

    public Grupakadry getListaselected2() {
        return listaselected2;
    }

    public void setListaselected2(Grupakadry listaselected2) {
        this.listaselected2 = listaselected2;
    }
    
    
}

