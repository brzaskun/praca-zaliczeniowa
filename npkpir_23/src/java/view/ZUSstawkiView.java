/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.ZUSDAO;
import entity.Zusstawki;
import entity.ZusstawkiPK;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.joda.time.DateTime;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class ZUSstawkiView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private ZUSDAO zusDAO;
    @Inject
    private Zusstawki selected;
    @Inject
    private Zusstawki wprowadzanie;
    private List<Zusstawki> listapobranychstawek;
    private List<Zusstawki> listapobranychstawekMalyZUS;

    public ZUSstawkiView() {
        listapobranychstawek = Collections.synchronizedList(new ArrayList<>());
    }
    private String biezacadata;

    @PostConstruct
    private void init() {
        try {
            listapobranychstawek = zusDAO.findZUS(false);
            listapobranychstawekMalyZUS = zusDAO.findZUS(true);
        } catch (Exception e) {
            E.e(e);
        }
        biezacadata = String.valueOf(new DateTime().getYear());
    }

    public void dodajzus() {
        try {
            zusDAO.dodaj(wprowadzanie);
            obsluzZUSduzy();
            Msg.msg("Dodatno parametr ZUS do podatnika za m-c: " + wprowadzanie.getZusstawkiPK().getMiesiac());
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Niedodatno parametru ZUS. Wpis za rok " + wprowadzanie.getZusstawkiPK().getRok() + " i miesiąc " + wprowadzanie.getZusstawkiPK().getMiesiac() + " już istnieje");
        }

    }

    public void edytujzus() {
        try {
            zusDAO.edit(wprowadzanie);
            obsluzZUSduzy();
            Msg.msg("Edytowano parametr ZUS do podatnika za m-c:" + wprowadzanie.getZusstawkiPK().getMiesiac());

        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nieudana edycja parametru ZUS za m-c:" + wprowadzanie.getZusstawkiPK().getMiesiac());
        }

    }

    public void dodajzusmaly() {
        try {
            wprowadzanie.getZusstawkiPK().setMalyzus(true);
            zusDAO.dodaj(wprowadzanie);
            obsluzZUSmaly();
            Msg.msg("Dodatno parametr ZUS do podatnika za m-c: " + wprowadzanie.getZusstawkiPK().getMiesiac());
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Niedodatno parametru ZUS. Wpis za rok " + wprowadzanie.getZusstawkiPK().getRok() + " i miesiąc " + wprowadzanie.getZusstawkiPK().getMiesiac() + " już istnieje");
        }

    }

    public void edytujzusmaly() {
        try {
            wprowadzanie.getZusstawkiPK().setMalyzus(true);
            zusDAO.edit(wprowadzanie);
            obsluzZUSmaly();
            Msg.msg("Edytowano parametr ZUS do podatnika za m-c:" + wprowadzanie.getZusstawkiPK().getMiesiac());

        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd. Nieudana edycja parametru ZUS za m-c:" + wprowadzanie.getZusstawkiPK().getMiesiac());
        }

    }

    public void usunzus(Zusstawki zusstawki) {
        try {
            zusDAO.destroy(zusstawki);
            obsluzZUSduzy();
            Msg.msg("Usunieto parametr ZUS do podatnika za m-c: " + zusstawki.getZusstawkiPK().getMiesiac());
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd - nie usunięto stawki.");
        }
    }
    
    public void usunzusmaly(Zusstawki zusstawki) {
        try {
            zusDAO.destroy(zusstawki);
            obsluzZUSmaly();
            Msg.msg("Usunieto parametr ZUS do podatnika za m-c: " + zusstawki.getZusstawkiPK().getMiesiac());
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e", "Wystąpił błąd - nie usunięto stawki.");
        }
    }

    private void obsluzZUSmaly() {
        listapobranychstawekMalyZUS = Collections.synchronizedList(new ArrayList<>());
        listapobranychstawekMalyZUS = zusDAO.findZUS(true);
        wprowadzanie = new Zusstawki();
    }

    private void obsluzZUSduzy() {
        listapobranychstawek = Collections.synchronizedList(new ArrayList<>());
        listapobranychstawek = zusDAO.findZUS(false);
        wprowadzanie = new Zusstawki();
    }

    public void wybranowiadomosc() {
        wprowadzanie = serialclone.SerialClone.clone(selected);
        Msg.msg("Wybrano stawki ZUS  za okres"+selected.getZusstawkiPK().getRok()+"/"+selected.getZusstawkiPK().getMiesiac());
    }

    public int sortujZUSstawki(Object obP, Object obW) {
        int rokO1 = Integer.parseInt(((ZusstawkiPK) obP).getRok());
        int rokO2 = Integer.parseInt(((ZusstawkiPK) obW).getRok());
        int mcO1 = Integer.parseInt(((ZusstawkiPK) obP).getMiesiac());
        int mcO2 = Integer.parseInt(((ZusstawkiPK) obW).getMiesiac());
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
