/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.KontoDAOfk;
import daoFK.KontokategoriaDAOfk;
import entityfk.Konto;
import entityfk.Kontokategoria;
import error.E;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class KontokategoriaPrzypView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private List<Kontokategoria> lista;
    private List<Konto> wykazkont;
    private List<Konto> wykazkontwzor;
    @Inject
    private Kontokategoria selectedDod;
    @Inject
    private KontokategoriaDAOfk kontokategoriaDAOfk;
    @Inject
    private KontoDAOfk kontoDAOfk;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public KontokategoriaPrzypView() {
         //E.m(this);
    }
    
    @PostConstruct
    private void init() {
        this.lista = kontokategoriaDAOfk.findAll();
        this.wykazkont = kontoDAOfk.findKontazLevelu(wpisView.getPodatnikObiekt(), wpisView.getRokWpisu(), 0);
        this.wykazkontwzor = kontoDAOfk.findKontazLevelu(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisu(), 0);
    }
    
    public void zachowajkontaWzorzec() {
        try {
            List<Konto> wszystkiekonta = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikwzorcowy(), wpisView.getRokWpisuSt());
            usunzerowe(wszystkiekonta);
            kontoDAOfk.editList(wykazkontwzor);
            nanieskategorie(wykazkontwzor, wszystkiekonta);
            kontoDAOfk.editList(wszystkiekonta);
            Msg.msg("Zachowano zmiany w przyporządkowaniu");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nie zachowano zmian");
        }
    }
    
    
    public void zachowajkonta() {
        try {
            List<Konto> wszystkiekonta = kontoDAOfk.findWszystkieKontaPodatnika(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            usunzerowe(wszystkiekonta);
            kontoDAOfk.editList(wykazkont);
            nanieskategorie(wykazkont, wszystkiekonta);
            kontoDAOfk.editList(wszystkiekonta);
            Msg.msg("Zachowano zmiany w przyporządkowaniu");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nie zachowano zmian");
        }
    }
    
    private void usunzerowe(List<Konto> wszystkiekonta) {
        for (Iterator<Konto> it = wszystkiekonta.iterator(); it.hasNext();) {
            if (it.next().getLevel() == 0) {
                it.remove();
            }
        }
    }
    
    private void nanieskategorie(List<Konto> kontazero, List<Konto> wszystkiekonta) {
        for (Konto p : kontazero) {
            for (Konto r : wszystkiekonta) {
                if (r.getSyntetycznenumer().equals(p.getPelnynumer()) && p.getKontokategoria() != null) {
                    r.setKontokategoria(p.getKontokategoria());
                }
            }
        }
    }
    
    public void importujkategorie() {
        try {
            for (Konto p : wykazkontwzor) {
                for (Konto r : wykazkont) {
                    if (r.getPelnynumer().equals(p.getPelnynumer())) {
                        r.setKontokategoria(p.getKontokategoria());
                    }
                }
            }
            kontoDAOfk.editList(wykazkont);
            Msg.msg("Zaimportowano kategorie");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nie zaimportowani kategorii");
        }
    }
    
    public void zachowaj() {
        if (lista != null) {
            try {
                kontokategoriaDAOfk.editList(lista);
                Msg.msg("Zachowano kategorie");
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd. Nie zachowano kategori");
            }
        }
    }
    
    public void dodaj() {
        if (selectedDod != null) {
            try {
                selectedDod.setSymbol(selectedDod.getSymbol());
                kontokategoriaDAOfk.dodaj(selectedDod);
                lista.add(selectedDod);
                selectedDod = new Kontokategoria();
                Msg.msg("Dodano nową kategorię");
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd. Nie dodano nowej kategorii");
            }
        }
    }
    
    public void edytuj(Kontokategoria k) {
        if (k != null) {
            try {
                selectedDod.setSymbol(k.getSymbol());
                kontokategoriaDAOfk.edit(k);
                Msg.msg("Zmieniono opis kategorii");
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd. Nie zmieniono opisu kategorii");
            }
        }
    }
    
    public void usun(Kontokategoria k) {
        if (k != null) {
            try {
                kontokategoriaDAOfk.destroy(k);
                lista.remove(k);
                Msg.msg("Usunięto kategorię");
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystąpił błąd. Nie usunięto kategorii");
            }
        }
    }
    
    public void sprawdzduplikat() {
        for (Kontokategoria p : lista) {
            if (p.getSymbol().equals(selectedDod.getSymbol())) {
                selectedDod.setSymbol(null);
                Msg.msg("e", "Taki symbol został już wprowadzony");
            }
        }
    }

    public List<Kontokategoria> getLista() {
        return lista;
    }

    public void setLista(List<Kontokategoria> lista) {
        this.lista = lista;
    }

   
    public Kontokategoria getSelectedDod() {
        return selectedDod;
    }

    public void setSelectedDod(Kontokategoria selectedDod) {
        this.selectedDod = selectedDod;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<Konto> getWykazkont() {
        return wykazkont;
    }

    public void setWykazkont(List<Konto> wykazkont) {
        this.wykazkont = wykazkont;
    }

    public List<Konto> getWykazkontwzor() {
        return wykazkontwzor;
    }

    public void setWykazkontwzor(List<Konto> wykazkontwzor) {
        this.wykazkontwzor = wykazkontwzor;
    }

   

   
    
    
 
            
}
