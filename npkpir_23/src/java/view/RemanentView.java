/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import embeddable.Parametr;
import entity.Podatnik;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RemanentView implements Serializable {

    private static double remanentPoczRoku;
    private static double remanentKoniecRoku;
    private static double roznica;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private Podatnik pod;

    @PostConstruct
    private void init() {
        try {
            pod = wpisView.getPodatnikObiekt();
            Integer rok = wpisView.getRokWpisu();
            Integer rokNext = wpisView.getRokWpisu()+1;
            try {
                List<Parametr> remanentLista = pod.getRemanent();
                if (remanentLista.isEmpty()) {
                     Msg.msg("e", "Nie wprowadzono remanentu! Program nie obliczy PIT-u za grudzien.");
                } else {
                    Parametr tmp = zwrocparametrzRoku(remanentLista, rok);
                    if (tmp instanceof Parametr) {
                        remanentPoczRoku = Double.valueOf(tmp.getParametr());
                    } else {
                        Msg.msg("e", "Nie wprowadzono remanentu początkowego! Program nie obliczy poprawnie PIT-u za grudzien.");
                    }
                    tmp = zwrocparametrzRoku(remanentLista, rokNext);
                    if (tmp instanceof Parametr) {
                        remanentKoniecRoku = Double.valueOf(tmp.getParametr());
                        roznica = remanentPoczRoku - remanentKoniecRoku;
                    } else {
                        Msg.msg("e", "Nie wprowadzono remanentu końcowego! Program nie obliczy poprawnie PIT-u za grudzien.");
                        roznica = 0.0;
                    }
                    //remnierem = "Wartość ostatniego remanentu za " + tmp.getRokOd() + " wynosi: " + tmp.getParametr();
                }
            } catch (Exception e) {
                    Msg.msg("e", "Nie wprowadzono remanentu! Program nie obliczy PIT-u za grudzien.");
            }
        } catch (Exception ex) {
            Msg.msg("e", "Problem z pobraniem podatnika. Nie mogę obliczyć różnicy remanentu.");
        }
    }
    
    private Parametr zwrocparametrzRoku(List<Parametr> lista, Integer szukanyRok) {
        String rokStr = String.valueOf(szukanyRok);
        for (Parametr p : lista) {
            if (p.getRokOd().equals(rokStr)) {
                return p;
            }
        }
        return null;
    }

    public double getRemanentPoczRoku() {
        return remanentPoczRoku;
    }

    public void setRemanentPoczRoku(double remanentPoczRoku) {
        RemanentView.remanentPoczRoku = remanentPoczRoku;
    }

    public double getRemanentKoniecRoku() {
        return remanentKoniecRoku;
    }

    public void setRemanentKoniecRoku(double remanentKoniecRoku) {
        RemanentView.remanentKoniecRoku = remanentKoniecRoku;
    }

    public double getRoznica() {
        return roznica;
    }
    
    public static double getRoznicaS() {
        return roznica;
    }

    public void setRoznica(double roznica) {
        RemanentView.roznica = roznica;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
    
    
}
