/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.ZUSDAO;
import entity.Zusstawki;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.joda.time.DateTime;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class ZusStawkiView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private ZUSDAO zusDAO;
    @Inject
    private Zusstawki selected;
    @Inject
    private Zusstawki wprowadzanie;
    private List<Zusstawki> listapobranychstawek;
    private List<Zusstawki> listapobranychstawekMalyZUS;

    public ZusStawkiView() {
        listapobranychstawek = Collections.synchronizedList(new ArrayList<>());
    }
    private String biezacadata;

    @PostConstruct
    private void init() { //E.m(this);
        try {
            listapobranychstawek = zusDAO.findZUS(0);
            listapobranychstawekMalyZUS = zusDAO.findZUS(1);
        } catch (Exception e) {
            E.e(e);
        }
        biezacadata = String.valueOf(new DateTime().getYear());
    }

    public void dodajzus() {
        try {
            zusDAO.create(wprowadzanie);
            listapobranychstawek.add(wprowadzanie);
            wprowadzanie = new Zusstawki();
            Msg.msg("Dodatno parametr ZUS do podatnika za m-c: " + wprowadzanie.getMiesiac());
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Niedodatno parametru ZUS. Wpis za rok " + wprowadzanie.getRok() + " i miesiąc " + wprowadzanie.getMiesiac() + " już istnieje");
        }

    }

    public void edytujzus() {
        try {
            zusDAO.edit(wprowadzanie);
            wprowadzanie = new Zusstawki();
            Msg.msg("Edytowano parametr ZUS do podatnika za m-c:" + wprowadzanie.getMiesiac());

        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nieudana edycja parametru ZUS za m-c:" + wprowadzanie.getMiesiac());
        }

    }

    public void dodajzusmaly() {
        try {
            wprowadzanie.setRodzajzus(1);
            zusDAO.create(wprowadzanie);
            listapobranychstawekMalyZUS.add(wprowadzanie);
            wprowadzanie = new Zusstawki();
            Msg.msg("Dodatno parametr ZUS do podatnika za m-c: " + wprowadzanie.getMiesiac());
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Niedodatno parametru ZUS. Wpis za rok " + wprowadzanie.getRok() + " i miesiąc " + wprowadzanie.getMiesiac() + " już istnieje");
        }

    }

    public void edytujzusmaly() {
        try {
            wprowadzanie.setRodzajzus(1);
            zusDAO.edit(wprowadzanie);
            wprowadzanie = new Zusstawki();
            Msg.msg("Edytowano parametr ZUS do podatnika za m-c:" + wprowadzanie.getMiesiac());

        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nieudana edycja parametru ZUS za m-c:" + wprowadzanie.getMiesiac());
        }

    }

    public void usunzus(Zusstawki zusstawki) {
        try {
            zusDAO.remove(zusstawki);
            listapobranychstawek.remove(zusstawki);
            wprowadzanie = new Zusstawki();
            Msg.msg("Usunieto parametr ZUS do podatnika za m-c: " + zusstawki.getMiesiac());
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd - nie usunięto stawki.");
        }
    }
    
    public void usunzusmaly(Zusstawki zusstawki) {
        try {
            zusDAO.remove(zusstawki);
            listapobranychstawekMalyZUS.remove(zusstawki);
            wprowadzanie = new Zusstawki();
            Msg.msg("Usunieto parametr ZUS do podatnika za m-c: " + zusstawki.getMiesiac());
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd - nie usunięto stawki.");
        }
    }


    public void wybranowiadomosc() {
        wprowadzanie = serialclone.SerialClone.clone(selected);
        Msg.msg("Wybrano stawki ZUS  za okres"+selected.getRok()+"/"+selected.getMiesiac());
    }

    public int sortujZUSstawki(Object obP, Object obW) {
        int rokO1 = Integer.parseInt(((Zusstawki) obP).getRok());
        int rokO2 = Integer.parseInt(((Zusstawki) obW).getRok());
        int mcO1 = Integer.parseInt(((Zusstawki) obP).getMiesiac());
        int mcO2 = Integer.parseInt(((Zusstawki) obW).getMiesiac());
        if (rokO1 < rokO2) {
            return -1;
        } else if (rokO1 > rokO2) {
            return 1;
        } else if (rokO1 == rokO2) {
            if (mcO1 == mcO2) {
                return 0;
            } else if (mcO1 < mcO2) {
                return -1;
            } else {
                return 1;
            }
        }
        return 0;
    }

    public ZUSDAO getZusDAO() {
        return zusDAO;
    }

    public void setZusDAO(ZUSDAO zusDAO) {
        this.zusDAO = zusDAO;
    }

    public Zusstawki getSelected() {
        return selected;
    }

    public void setSelected(Zusstawki selected) {
        this.selected = selected;
    }

    public List<Zusstawki> getListapobranychstawek() {
        return listapobranychstawek;
    }

    public String getBiezacadata() {
        return biezacadata;
    }

    public void setBiezacadata(String biezacadata) {
        this.biezacadata = biezacadata;
    }

    public Zusstawki getWprowadzanie() {
        return wprowadzanie;
    }

    public void setWprowadzanie(Zusstawki wprowadzanie) {
        this.wprowadzanie = wprowadzanie;
    }

    public List<Zusstawki> getListapobranychstawekMalyZUS() {
        return listapobranychstawekMalyZUS;
    }

    public void setListapobranychstawekMalyZUS(List<Zusstawki> listapobranychstawekMalyZUS) {
        this.listapobranychstawekMalyZUS = listapobranychstawekMalyZUS;
    }

}
