/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PitDAO;
import dao.RyczDAO;
import entity.Pitpoz;
import entity.Ryczpoz;
import error.E;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import pdf.PdfRyczpoz;
import waluty.Z;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class Pit36View implements Serializable {

    private List<Pitpoz> lista;
     private List<Ryczpoz> listaryczalt;
    @Inject
    private PitDAO pitDAO;
    @Inject private RyczDAO ryczDAO;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

    public Pit36View() {
        lista = Collections.synchronizedList(new ArrayList<>());
        listaryczalt = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    private void init() {
        lista = pitDAO.findPitPod(wpisView.getRokWpisu().toString(), wpisView.getPodatnikWpisu());
        listaryczalt =  ryczDAO.findRyczPod(wpisView.getRokWpisu().toString(), wpisView.getPodatnikWpisu());
        sumujryczalt();
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public List<Pitpoz> getLista() {
        return lista;
    }
    
    public void setLista(List<Pitpoz> lista) {
        this.lista = lista;
    }
    
    public List<Ryczpoz> getListaryczalt() {
        return listaryczalt;
    }
    
    public void setListaryczalt(List<Ryczpoz> listaryczalt) {
        this.listaryczalt = listaryczalt;
    }
    
    
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
//</editor-fold>

    private void sumujryczalt() {
        Ryczpoz suma = new Ryczpoz();
        suma.setPodatnik("podsumowanie");
        suma.setUdzialowiec("podsumowanie");
        double przychody = 0.0;
        double przychodyWgUdzialu = 0.0;
        double zus51 = 0.0;
        double zus52 = 0.0;
        double naleznazaliczka = 0.0;
        int id = 0;
        double p17 = 0.0;
        double p85 = 0.0;
        double p55 = 0.0;
        double p30 = 0.0;
        double p17p = 0.0;
        double p85p = 0.0;
        double p55p = 0.0;
        double p30p = 0.0;
        for (Ryczpoz p : listaryczalt) {
            p17 += p.getP17();
            p85 += p.getP85();
            p55 += p.getP55();
            p30 += p.getP30();
            p17p += p.getP17p();
            p85p += p.getP85p();
            p55p += p.getP55p();
            p30p += p.getP30p();
            przychody += p.getPrzychody().doubleValue();
            przychodyWgUdzialu += p.getPrzychodyudzial().doubleValue();
            if(p.getZus51() != null) {
                zus51 += p.getZus51().doubleValue();
            }
            if (p.getZus52() != null) {
                zus52 += p.getZus52().doubleValue();
            }
            naleznazaliczka += p.getNaleznazal().doubleValue();
            id = p.getId() > id ? p.getId() : id;
        }
        suma.setSumowaniePrzych(p17,p85,p55,p30,p17p,p85p,p55p,p30p);
        suma.setPrzychody(new BigDecimal(Z.z(przychody)));
        suma.setPrzychodyudzial(new BigDecimal(Z.z(przychodyWgUdzialu)));
        suma.setZus51(new BigDecimal(Z.z(zus51)));
        suma.setZus52(new BigDecimal(Z.z(zus52)));
        suma.setNaleznazal(new BigDecimal(Z.z(naleznazaliczka)));
        suma.setId(id+1);
        listaryczalt.add(suma);
    }
    
    public void drukujryczalt() {
        try {
            String nazwa = wpisView.getPodatnikObiekt().getNip()+"ryczpoz";
            PdfRyczpoz.drukujryczalt(nazwa, listaryczalt, wpisView);
        } catch (Exception e) {
            Msg.msg("e", "Wystąpił błąd. Nie wydrukowano zestawienia");
            E.e(e);
        }
    }
}
