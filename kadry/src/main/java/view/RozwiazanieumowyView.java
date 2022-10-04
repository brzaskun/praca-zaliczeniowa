/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.RozwiazanieumowyFacade;
import dao.UmowaFacade;
import entity.Rozwiazanieumowy;
import entity.Umowa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class RozwiazanieumowyView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private RozwiazanieumowyFacade rozwiazanieumowyFacade;
    @Inject
    private UmowaFacade umowaFacade;
    private Rozwiazanieumowy selectedlista;
    private List<Rozwiazanieumowy> lista;
    private Umowa wybranaumowa;
    @Inject
    private Rozwiazanieumowy nowy;
    
    
    @PostConstruct
    private void init() {
        lista = new ArrayList<>();
    }

    public void pobierzRozwiazanie() {
        if (wybranaumowa!=null) {
            Rozwiazanieumowy pobrane = rozwiazanieumowyFacade.findByUmowa(wybranaumowa);
            if (pobrane!=null) {
                lista.add(pobrane);
            } else {
                lista = new ArrayList<>();
                nowy.setUmowa(wybranaumowa);
            }
            Msg.msg("Pobrano rozwiązania umowy");
        } else {
            lista = new ArrayList<>();
        }
    }
    
    public void usun(Rozwiazanieumowy r) {
        if (r!=null) {
            r.getUmowa().setRozwiazanieumowy(null);
            umowaFacade.edit(r.getUmowa());
            rozwiazanieumowyFacade.remove(r);
            lista.remove(r);
            Msg.msg("Usunieto rozwiązanie");
        } else {
            Msg.msg("e","Nie wybrano rozwiązania");
        }
    }
    
    public void dodajnowy() {
        if (wybranaumowa!=null && wybranaumowa.getRozwiazanieumowy()==null) {
            nowy.setData(new Date());
            rozwiazanieumowyFacade.create(nowy);
            lista.add(nowy);
            wybranaumowa.setRozwiazanieumowy(nowy);
            umowaFacade.edit(wybranaumowa);
            Msg.msg("Dodano nowe wypowiedzenie");
        } else {
            Msg.msg("e","Umowa ma już wygenerowane wypowiedzenie.");   
        }
    }

    public Rozwiazanieumowy getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Rozwiazanieumowy selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Rozwiazanieumowy> getLista() {
        return lista;
    }

    public void setLista(List<Rozwiazanieumowy> lista) {
        this.lista = lista;
    }

    public Rozwiazanieumowy getNowy() {
        return nowy;
    }

    public void setNowy(Rozwiazanieumowy nowy) {
        this.nowy = nowy;
    }

    public Umowa getWybranaumowa() {
        return wybranaumowa;
    }

    public void setWybranaumowa(Umowa wybranaumowa) {
        this.wybranaumowa = wybranaumowa;
    }

    
    
    
   
    
}
