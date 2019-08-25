/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import embeddable.Parametr;
import entity.Podatnik;
import error.E;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import msg.Msg;
/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RemanentView implements Serializable {

    private double remanentPoczRoku;
    private double remanentKoniecRoku;
    private double roznica;

 
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public RemanentView() {
        remanentPoczRoku = 0.0;
        remanentKoniecRoku = 0.0;
        roznica = 0.0;
    }
    
    

    @PostConstruct
    private void init() { //E.m(this);
        try {
            Podatnik pod = wpisView.getPodatnikObiekt();
            Integer rok = wpisView.getRokWpisu();
            Integer rokNext = wpisView.getRokWpisu()+1;
            try {
                List<Parametr> remanentLista = pod.getRemanent();
                if (remanentLista.isEmpty()) {
                     Msg.msg("e", "Nie wprowadzono remanentu! Program nie obliczy PIT-u za grudzien.");
                } else {
                    Parametr tmp = zwrocparametrzRoku(remanentLista, rok);
                    if (tmp instanceof Parametr) {
                        String parametrweryf = tmp.getParametr().replace(",", ".");
                        remanentPoczRoku = Double.valueOf(parametrweryf);
                    } else {
                        Msg.msg("e", "Nie wprowadzono remanentu początkowego! Program nie obliczy poprawnie PIT-u za grudzien.");
                    }
                    tmp = zwrocparametrzRoku(remanentLista, rokNext);
                    if (tmp instanceof Parametr) {
                        String parametrweryf = tmp.getParametr().replace(",", ".");
                        remanentKoniecRoku = Double.valueOf(parametrweryf);
                        roznica = remanentPoczRoku - remanentKoniecRoku;
                    } else {
                        Msg.msg("e", "Nie wprowadzono remanentu końcowego! Program nie obliczy poprawnie PIT-u za grudzien.");
                        roznica = 0.0;
                    }
                    //remnierem = "Wartość ostatniego remanentu za " + tmp.getRokOd() + " wynosi: " + tmp.getParametr();
                }
            } catch (Exception e) { E.e(e); 
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
        this.remanentPoczRoku = remanentPoczRoku;
    }

    public double getRemanentKoniecRoku() {
        return remanentKoniecRoku;
    }

    public void setRemanentKoniecRoku(double remanentKoniecRoku) {
        this.remanentKoniecRoku = remanentKoniecRoku;
    }

    public double getRoznica() {
        return roznica;
    }

    public void setRoznica(double roznica) {
        this.roznica = roznica;
    }

   

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    
    
    
    
}
