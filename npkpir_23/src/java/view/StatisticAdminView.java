/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import dao.SesjaDAO;
import dao.UzDAO;
import dao.DokDAOfk;
import dao.WierszDAO;
import entity.Dok;
import entity.Sesja;
import entityfk.Wiersz;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.inject.Named;

import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import msg.Msg;import org.joda.time.DateTime;
import org.joda.time.Duration;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class StatisticAdminView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Statystyka> statystyka;
    private List<Sesja> sesje;
    private List<Obrabiani> obrabiani;
    @Inject
    private WpisView wpisView;
    @Inject
    private SesjaDAO sesjaDAO;
    @Inject private DokDAO dokDAO;
    @Inject private DokDAOfk dokDAOfk;
    @Inject private UzDAO uzDAO;
    @Inject private WierszDAO wierszDAO;
    private String rok;

    public StatisticAdminView() {
        this.sesje = Collections.synchronizedList(new ArrayList<>());
        this.statystyka = Collections.synchronizedList(new ArrayList<>());
        this.obrabiani = Collections.synchronizedList(new ArrayList<>());
        this.rok = "2020";
    }

    
    public void init() { //E.m(this);
       List<String> pracownicy = uzDAO.findUzByUprawnienia("Bookkeeper");
       pracownicy.addAll(uzDAO.findUzByUprawnienia("BookkeeperFK"));
       obliczstatystyki(pracownicy);
        error.E.s("statystyka inaczej");
        statystykiinaczej(pracownicy);
        error.E.s("statystyka inaczej koniec");
       obliczkontrahentow(pracownicy);
    }
    
    
    private void obliczstatystyki(List<String> pracownicy) {
        for (String r : pracownicy){
            Statystyka stat = new Statystyka();
            sesje = sesjaDAO.findUser(r);
            stat.ksiegowa = r;
            stat.iloscsesji = sesje.size();
            long[] milis = {0};
            sesje.stream().forEach((p)->{
                stat.iloscdokumentow += p.getIloscdokumentow();
                stat.iloscwydrukow += p.getIloscwydrukow();
                if (p.getWylogowanie() instanceof Date && p.getZalogowanie() instanceof Date) {
                    Duration duration = new Duration(new DateTime(p.getZalogowanie()),new DateTime(p.getWylogowanie()));
                    milis[0] = milis[0]+duration.getMillis();
                }
            });
            int minuty = (int) (milis[0]/1000/60);
            int godziny = minuty/60;
            int dni = godziny/7;
            stat.spedzonyczas = String.format(" w minutach: %s, w godzinach: %s, w dniach roboczych: %s", minuty, godziny, dni);
            statystyka.add(stat);
        }
    }
    
    private void statystykiinaczej(List<String> pracownicy) {
        List<Wiersz> wiersze = wierszDAO.findWierszeRok(rok);
        List<Dok> dok = dokDAO.zwrocRok(rok);
        error.E.s("pobrano dane");
        for (String r : pracownicy){
            double ilosc = 0.0;
            for (Iterator<Wiersz> it = wiersze.iterator(); it.hasNext();) {
                Wiersz w = it.next();
                if (w.getDokfk()!=null && w.getDokfk().getWprowadzil()!=null && w.getDokfk().getWprowadzil().equals(r)) {
                    if (w.getDokfk().getListawierszy().size()<3) {
                        ilosc= ilosc+0.5;
                    } else {
                        ilosc= ilosc+0.2;
                    }
                    //it.remove();
                }
            }
            for (Iterator<Dok> it = dok.iterator(); it.hasNext();) {
                Dok d = it.next();
                if (d.getWprowadzil()!=null && d.getWprowadzil().equals(r)) {
                    ilosc = ilosc+1.0;
                    //it.remove();
                }
            }
            for (Statystyka s : statystyka) {
                if (s.getKsiegowa().equals(r)) {
                    s.setIloscdokumentow((int)ilosc);
                }
            }
            error.E.s("zliczono dla "+r);
            Msg.msg("Zliczono dla "+r);
        }
        
    }
    
    private void obliczkontrahentow(List<String> pracownicy) {
        Map<String, String> klienci = new ConcurrentHashMap<>();
        pracownicy.stream().forEach((s)->{
            Obrabiani obrab = new Obrabiani();
            if (s!=null) {
                obrab.wprowadzajacy = s;
                int liczba = dokDAO.znajdzDokumentPodatnikWpr(s).size();
                liczba += dokDAOfk.znajdzDokumentPodatnikWprFK(s).size();
                obrab.liczbaklientow = liczba;
                obrabiani.add(obrab);
            }
        });
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

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
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
