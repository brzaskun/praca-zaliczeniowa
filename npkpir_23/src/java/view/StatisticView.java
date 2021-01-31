/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SesjaDAO;
import entity.Sesja;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class StatisticView implements Serializable {
    private static final long serialVersionUID = 1L;
    private int iloscsesji;
    private int iloscdokumentow;
    private int iloscwydrukow;
    private String spedzonyczas;
    private List<Sesja> sesje;
    @Inject
    private WpisView wpisView;
    @Inject
    private SesjaDAO sesjaDAO;

    public StatisticView() {
        this.sesje = Collections.synchronizedList(new ArrayList<>());
        this.iloscsesji = 0;
        this.iloscdokumentow = 0;
        this.iloscwydrukow = 0;
    }

    @PostConstruct
    private void init() { //E.m(this);
        sesje = sesjaDAO.findUser(wpisView.getUzer().getLogin());
        iloscsesji = sesje.size();
        long milis = 0;
        for (Sesja p : sesje) {
            iloscdokumentow += p.getIloscdokumentow();
            iloscwydrukow += p.getIloscwydrukow();
            if (p.getWylogowanie() instanceof Date && p.getZalogowanie() instanceof Date) {
                Duration duration = new Duration(new DateTime(p.getZalogowanie()),new DateTime(p.getWylogowanie()));
                milis += duration.getMillis();
            }
        }
        int minuty = (int) (milis/1000/60);
        int godziny = minuty/60;
        int dni = godziny/7;
       spedzonyczas = String.format(" w minutach: %s, w godzinach: %s, w dniach roboczych: %s", minuty, godziny, dni);
    }

    public int getIloscsesji() {
        return iloscsesji;
    }

    public void setIloscsesji(int iloscsesji) {
        this.iloscsesji = iloscsesji;
    }

    public int getIloscdokumentow() {
        return iloscdokumentow;
    }

    public void setIloscdokumentow(int iloscdokumentow) {
        this.iloscdokumentow = iloscdokumentow;
    }

    public int getIloscwydrukow() {
        return iloscwydrukow;
    }

    public void setIloscwydrukow(int iloscwydrukow) {
        this.iloscwydrukow = iloscwydrukow;
    }

    public String getSpedzonyczas() {
        return spedzonyczas;
    }

    public void setSpedzonyczas(String spedzonyczas) {
        this.spedzonyczas = spedzonyczas;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
}
