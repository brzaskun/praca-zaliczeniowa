/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.SesjaDAO;
import entity.Dok;
import entity.Sesja;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
    private List<Obrabiani> obrabiani;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private SesjaDAO sesjaDAO;
    @Inject private DokDAO dokDAO;

    public StatisticAdminView() {
        this.sesje = new ArrayList<>();
        this.statystyka = new ArrayList<>();
        this.obrabiani = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
       obliczstatystyki();
       obliczkontrahentow();
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
    
    private void obliczkontrahentow() {
        Map<String, String> klienci = new HashMap<>();
        List<Dok> dok = dokDAO.findAll();
        Set<String> wprowadzil = new LinkedHashSet<>();
        for (Dok p : dok) {
            klienci.put(p.getPodatnik(), p.getWprowadzil());
            wprowadzil.add(p.getWprowadzil());
        }
        for (String s :wprowadzil) {
            Obrabiani obrab = new Obrabiani();
            if (s!=null) {
                obrab.wprowadzajacy = s;
                obrab.liczbaklientow = 0;
                obrabiani.add(obrab);
            }
        }
        for (String r : klienci.values()) {
            if (r!=null) {
                for (Obrabiani t : obrabiani) {
                    if (r.equals(t.wprowadzajacy)) {
                        t.liczbaklientow += 1;
                    }
                }
            }
        }
    }

    public List<Statystyka> getStatystyka() {
        return statystyka;
    }

    public void setStatystyka(List<Statystyka> statystyka) {
        this.statystyka = statystyka;
    }

    public List<Obrabiani> getObrabiani() {
        return obrabiani;
    }

    public void setObrabiani(List<Obrabiani> obrabiani) {
        this.obrabiani = obrabiani;
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

    public static class Obrabiani {
        private String wprowadzajacy;
        private int liczbaklientow;
        
        public Obrabiani() {
        }

        //<editor-fold defaultstate="collapsed" desc="comment">
        public String getWprowadzajacy() {
            return wprowadzajacy;
        }
        
        public void setWprowadzajacy(String wprowadzajacy) {
            this.wprowadzajacy = wprowadzajacy;
        }
        
        public int getLiczbaklientow() {
            return liczbaklientow;
        }
        
        public void setLiczbaklientow(int liczbaklientow) {
            this.liczbaklientow = liczbaklientow;
        }
        
        
//</editor-fold>
    }
}
