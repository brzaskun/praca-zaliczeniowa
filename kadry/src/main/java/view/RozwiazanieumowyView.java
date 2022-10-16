/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.NieobecnoscFacade;
import dao.NieobecnoscswiadectwoschemaFacade;
import dao.RozwiazanieumowyFacade;
import dao.UmowaFacade;
import entity.Dzien;
import entity.Nieobecnosc;
import entity.Nieobecnoscswiadectwoschema;
import entity.Rozwiazanieumowy;
import entity.Swiadectwo;
import entity.Swiadectwodni;
import entity.Umowa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
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
    @Inject
    private NieobecnoscFacade nieobecnoscFacade;
    private Rozwiazanieumowy selectedlista;
    private List<Rozwiazanieumowy> lista;
    private Umowa wybranaumowa;
    @Inject
    private Rozwiazanieumowy nowy;
    @Inject
    private WpisView wpisView;
    private List<Nieobecnosc> listanieob;
    private List<Nieobecnoscswiadectwoschema> listanieobecschema;
    @Inject
    private NieobecnoscswiadectwoschemaFacade nieobecnoscswiadectwoschemaFacade;
    private List<Swiadectwodni> dnidoswiadectwa;
    private Swiadectwodni selected;
    
    
    
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
            if (wpisView.getUmowa()!=null) {
                listanieob  = nieobecnoscFacade.findByUmowa(wpisView.getUmowa());
                listanieobecschema = nieobecnoscswiadectwoschemaFacade.findAll();
                dnidoswiadectwa = naniesnieobecnoscinascheme(listanieob, listanieobecschema, pobrane, wpisView.getRokWpisu());
            }
            Msg.msg("Pobrano rozwiązania umowy");
        } else {
            lista = new ArrayList<>();
        }
    }
    
     private List<Swiadectwodni>  naniesnieobecnoscinascheme(List<Nieobecnosc> listanieob, List<Nieobecnoscswiadectwoschema> listanieobecschema, Rozwiazanieumowy rozwiazanieumowy, String rok) {
        //urlopy
        List<Swiadectwodni> swiadectwodnilista = new ArrayList<>();
        if (listanieob!=null) {
            for (Nieobecnoscswiadectwoschema p : listanieobecschema) {
                Swiadectwodni swiadectwodni = new Swiadectwodni();
                swiadectwodni.setSwiadectwo(new Swiadectwo(rozwiazanieumowy));
                List<Nieobecnosc> filter0 = listanieob.stream().filter(r->r.getRodzajnieobecnosci().equals(p.getRodzajnieobecnosci())).collect(Collectors.toList());
                List<Dzien> dni = filter0.stream().flatMap(t->t.getDzienList().stream()).collect(Collectors.toList());
                List<Dzien> dniwroku = dni.stream().filter(s->s.getKalendarzmiesiac().getRok().equals(rok)).collect(Collectors.toList());
                List<Dzien> dniroboczelist = dniwroku.stream().filter(t->t.getTypdnia()==0).collect(Collectors.toList());
                double dnirobocze = p.getRodzajnieobecnosci().isDnikalendarzowe() ? dniwroku.size() : dniroboczelist.size();
                double godzinyrobocze = dniroboczelist.stream().mapToDouble(f->f.getUrlopPlatny()).sum();
                swiadectwodni.setNieobecnoscswiadectwoschema(p);
                swiadectwodni.setDni(dnirobocze);
                swiadectwodni.setGodziny(godzinyrobocze);
                swiadectwodnilista.add(swiadectwodni);
                swiadectwodni.setNieobecnoscinieskladkowe(filter0);
            }
        }
        return swiadectwodnilista;
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

    public List<Nieobecnosc> getListanieob() {
        return listanieob;
    }

    public void setListanieob(List<Nieobecnosc> listanieob) {
        this.listanieob = listanieob;
    }

    public List<Swiadectwodni> getDnidoswiadectwa() {
        return dnidoswiadectwa;
    }

    public void setDnidoswiadectwa(List<Swiadectwodni> dnidoswiadectwa) {
        this.dnidoswiadectwa = dnidoswiadectwa;
    }

    public Swiadectwodni getSelected() {
        return selected;
    }

    public void setSelected(Swiadectwodni selected) {
        this.selected = selected;
    }

   

    
    
    
   
    
}
