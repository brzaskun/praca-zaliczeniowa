/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SesjaDAO;
import entity.Sesja;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class StatisticAdminView implements Serializable {
    private List<Statystyka> statystyka;
    private List<Sesja> sesje;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private SesjaDAO sesjaDAO;

    public StatisticAdminView() {
        this.sesje = new ArrayList<>();
        this.statystyka = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
       obliczstatystyki();
    }
    
    private void obliczstatystyki() {
        List<String> pracownicy = new ArrayList<>();
        pracownicy.add("gosiak");
        pracownicy.add("renata");
        for (String r : pracownicy){
            Statystyka stat = new Statystyka();
            sesje = sesjaDAO.findUser(r);
            stat.ksiegowa = r;
            stat.iloscsesji = sesje.size();
            long milis = 0;
            for (Sesja p : sesje) {
                stat.iloscdokumentow += p.getIloscdokumentow();
                stat.iloscwydrukow += p.getIloscwydrukow();
                if (p.getWylogowanie() instanceof Date && p.getZalogowanie() instanceof Date) {
                    Duration duration = new Duration(new DateTime(p.getZalogowanie()),new DateTime(p.getWylogowanie()));
                    milis += (int) duration.getMillis();
                }
            }
            int minuty = (int) (milis/1000/60);
            int godziny = minuty/60;
            int dni = godziny/7;
            stat.spedzonyczas = String.format(" w minutach: %s, w godzinach: %s, w dniach roboczych: %s", minuty, godziny, dni);
            statystyka.add(stat);
        }
    }

    public List<Statystyka> getStatystyka() {
        return statystyka;
    }

    public void setStatystyka(List<Statystyka> statystyka) {
        this.statystyka = statystyka;
    }
    

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public static class Statystyka {
        private String ksiegowa;
        private int iloscsesji;
        private int iloscdokumentow;
        private int iloscwydrukow;
        private String spedzonyczas;
        
        public Statystyka() {
        }

        //<editor-fold defaultstate="collapsed" desc="comment">
        
        public String getKsiegowa() {
            return ksiegowa;
        }

        public void setKsiegowa(String ksiegowa) {
            this.ksiegowa = ksiegowa;
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
        
        //</editor-fold>
        
    }
}
