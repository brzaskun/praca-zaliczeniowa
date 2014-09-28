/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entityfk;

import daoFK.WalutyDAOfk;
import daoFK.WierszBODAO;
import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
public class BilansWprowadzanieView implements Serializable {

    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private WalutyDAOfk walutyDAOfk;

    List<WierszBO> lista0;
    List<WierszBO> lista1;
    List<WierszBO> lista2;
    List<WierszBO> lista3;
    List<WierszBO> lista6;
    List<WierszBO> lista8;
    Map<Integer, List<WierszBO>> listazbiorcza;

    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public BilansWprowadzanieView() {
        this.lista0 = new ArrayList<>();
        this.lista1 = new ArrayList<>();
        this.lista2 = new ArrayList<>();
        this.lista3 = new ArrayList<>();
        this.lista6 = new ArrayList<>();
        this.lista8 = new ArrayList<>();
        this.listazbiorcza = new HashMap<>();
        this.listazbiorcza.put(0, lista0);
        this.listazbiorcza.put(1, lista1);
        this.listazbiorcza.put(2, lista2);
        this.listazbiorcza.put(3, lista3);
        this.listazbiorcza.put(6, lista6);
        this.listazbiorcza.put(8, lista8);
    }

    @PostConstruct
    private void init() {
        Podatnik p = wpisView.getPodatnikObiekt();
        String r = wpisView.getRokWpisuSt();
        Waluty w = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        this.lista0.addAll(wierszBODAO.lista("0%"));
        if (lista0.isEmpty()) {
            this.lista0.add(new WierszBO(p, r, w));
        }
        this.lista1.addAll(wierszBODAO.lista("1%"));
        if (lista1.isEmpty()) {
            this.lista1.add(new WierszBO(p, r, w));
        }
        this.lista2.addAll(wierszBODAO.lista("2%"));
        if (lista2.isEmpty()) {
            this.lista2.add(new WierszBO(p, r, w));
        }
        this.lista3.addAll(wierszBODAO.lista("3%"));
        if (lista3.isEmpty()) {
            this.lista3.add(new WierszBO(p, r, w));
        }
        this.lista6.addAll(wierszBODAO.lista("6%"));
        if (lista6.isEmpty()) {
            this.lista6.add(new WierszBO(p, r, w));
        }
        this.lista8.addAll(wierszBODAO.lista("8%"));
        if (lista8.isEmpty()) {
            this.lista8.add(new WierszBO(p, r, w));
        }

    }

    public void dodajwiersz(int kategoria) {
        switch (kategoria) {
            case 0:
                dodawanielista(lista0);
                break;
            case 1:
                dodawanielista(lista1);
                break;
            case 2:
                dodawanielista(lista2);
                break;
            case 3:
                dodawanielista(lista3);
                break;
            case 6:
                dodawanielista(lista6);
                break;
            case 8:
                dodawanielista(lista8);
                break;
        }
    }

    private void dodawanielista(List<WierszBO> l) {
        Waluty w = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
        Podatnik p = wpisView.getPodatnikObiekt();
        String r = wpisView.getRokWpisuSt();
        l.add(new WierszBO(p, r, w));
    }

    public void usunwiersz(int kategoria) {
        switch (kategoria) {
            case 0:
                usuwanielista(lista0);
                break;
            case 1:
                usuwanielista(lista1);
                break;
            case 2:
                usuwanielista(lista2);
                break;
            case 3:
                usuwanielista(lista3);
                break;
            case 6:
                usuwanielista(lista6);
                break;
            case 8:
                usuwanielista(lista8);
                break;
        }
    }

    private void usuwanielista(List<WierszBO> l) {
        try {
            Waluty w = walutyDAOfk.findWalutaBySymbolWaluty("PLN");
            Podatnik p = wpisView.getPodatnikObiekt();
            String r = wpisView.getRokWpisuSt();
            if (l.size() > 1) {
                wierszBODAO.destroy(l.get(l.size() - 1));
                l.remove(l.size() - 1);
            } else {
                wierszBODAO.destroy(l.get(l.size() - 1));
                l.remove(l.size() - 1);
                l.add(new WierszBO(p, r, w));
            }
        } catch (Exception e) {
            
        }
    }

    public void zapiszBilansBOdoBazy() {
        Set<Integer> numerylist = listazbiorcza.keySet();
        for (Integer r : numerylist) {
            for (WierszBO p : listazbiorcza.get(r)) {
                if (p.getKonto()!=null) {
                    try {
                        if (p.getWaluta().getSymbolwaluty().equals("PLN")) {
                            p.setKwotaWnPLN(p.getKwotaWn());
                            p.setKwotaMaPLN(p.getKwotaMa());
                        }
                        wierszBODAO.dodaj(p);
                    } catch (Exception e) {
                        wierszBODAO.edit(p);
                    }
                }
            }
        }
        Msg.msg("Naniesiono zapisy BO");
    }
    
    public int weryfikacjaopisu(String opis, List<WierszBO> l) {
        int licznik = 0;
        for (WierszBO p : l) {
            if (p.getWierszBOPK().getOpis().equals(opis)) {
                licznik++;
            }
            if (licznik>1) {
                Msg.msg("e", "Taki opis już istnieje: "+opis);
               return 1;
            }
        }
        return 0;
    }
    
    public void przeliczkurs(WierszBO wiersz, double kurs, double kwotaWwalucie, String strona) {
        double kwotawPLN = Math.round(kwotaWwalucie * kurs * 100);
        kwotawPLN /= 100;
        if (strona.equals("Wn")) {
            wiersz.setKwotaWnPLN(kwotawPLN);
        } else {
            wiersz.setKwotaMaPLN(kwotawPLN);
        }
        
    }

    public List<WierszBO> getLista0() {
        return lista0;
    }

    public void setLista0(List<WierszBO> lista0) {
        this.lista0 = lista0;
    }

    public List<WierszBO> getLista1() {
        return lista1;
    }

    public void setLista1(List<WierszBO> lista1) {
        this.lista1 = lista1;
    }

    public List<WierszBO> getLista2() {
        return lista2;
    }

    public void setLista2(List<WierszBO> lista2) {
        this.lista2 = lista2;
    }

    public List<WierszBO> getLista3() {
        return lista3;
    }

    public void setLista3(List<WierszBO> lista3) {
        this.lista3 = lista3;
    }

    public List<WierszBO> getLista6() {
        return lista6;
    }

    public void setLista6(List<WierszBO> lista6) {
        this.lista6 = lista6;
    }

    public List<WierszBO> getLista8() {
        return lista8;
    }

    public void setLista8(List<WierszBO> lista8) {
        this.lista8 = lista8;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

}
