/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.GranicaDAO;
import dao.PitDAO;
import dao.RyczDAO;
import data.Data;
import entity.Granica;
import entity.Pitpoz;
import entity.Ryczpoz;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.inject.Inject;
import msg.Msg;
/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScope
public class GranicaObliczView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private GranicaDAO granicaDAO;
    @Inject
    private PitDAO pitDAO;
    @Inject
    private RyczDAO ryczDAO;
    private Granica granicevat;
    private Granica granicepkpir;
    private Granica graniceksiegi;
    private Granica granicekasa;
    private int progresvat;
    private int progrespkpir;
    private int progresksiegi;
    private int progreskasa;
    private double obrot;
    
    @PostConstruct
    private void init() { //E.m(this);
        try {
        List<Granica> l = granicaDAO.findByRok(wpisView.getRokWpisuSt());
        for (Granica p : l) {
                switch (p.getNazwalimitu()) {
                    case "vat":
                        granicevat = p;
                        break;
                    case "pkpir":
                        granicepkpir = p;
                        break;
                    case "ksiegi":
                        graniceksiegi = p;
                        break;
                    case "kasa":
                        granicekasa = p;
                        break;
                }
        }
        double mcedoproporcji = Data.mcedoproporcji(wpisView.getPodatnikObiekt().getDatarozpoczecia(), wpisView);
        obrot = 0.0;
        List<Ryczpoz> ryczaltpitlist = Collections.synchronizedList(new ArrayList<>());
        if (wpisView.isKsiegaryczalt()) {
            List<Pitpoz> pitlList = pitDAO.findList(wpisView.getRokWpisuSt(), wpisView.getMiesiacUprzedni(), wpisView.getPodatnikWpisu());
            if (pitlList != null && pitlList.size() == 1) {
                obrot = pitlList.get(0).getPrzychody().doubleValue();
            } else if (pitlList != null && pitlList.size() > 1) {
                int i = 0;
                for (Pitpoz pp : pitlList) {
                    obrot = pitlList.get(i++).getPrzychody().doubleValue();
                }
            }
        } else {
            ryczaltpitlist = ryczDAO.findRyczPod(wpisView.getRokWpisuSt(), wpisView.getPodatnikWpisu());
            if (ryczaltpitlist != null) {
                for (Ryczpoz s : ryczaltpitlist) {
                    obrot += s.getPrzychody().doubleValue();
                }
            }
        }
        double proporcja = 100.0;
        proporcja = granicevat.isProporcja() ? 100.0/12.0*mcedoproporcji : 100.0;
        progresvat = (int) (obrot/granicevat.getKwota()*proporcja);
        proporcja = granicepkpir.isProporcja() ? 100.0/12.0*mcedoproporcji : 100.0;
        progrespkpir = (int) (obrot/granicepkpir.getKwota()*proporcja);
        proporcja = graniceksiegi.isProporcja() ? 100.0/12.0*mcedoproporcji : 100.0;
        progresksiegi = (int) (obrot/graniceksiegi.getKwota()*proporcja);
        proporcja = granicekasa.isProporcja() ? 100.0/12.0*mcedoproporcji : 100.0;
        progreskasa = (int) (obrot/granicekasa.getKwota()*proporcja);
        progresvat = progresvat > 150 ? 150 : progresvat;
        progrespkpir = progrespkpir > 150 ? 150 : progrespkpir;
        progresksiegi = progresksiegi > 150 ? 150 : progresksiegi;
        progreskasa = progreskasa > 150 ? 150 : progreskasa;
        } catch(Exception e) {
            E.e(e);
            Msg.msg("e", "Błąd przy generowaniu limitów");
        }
    }
//<editor-fold defaultstate="collapsed" desc="comment">
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    public int getProgresvat() {
        return progresvat;
    }
    
    public void setProgresvat(int progresvat) {
        this.progresvat = progresvat;
    }
    
    public int getProgrespkpir() {
        return progrespkpir;
    }
    
    public void setProgrespkpir(int progrespkpir) {
        this.progrespkpir = progrespkpir;
    }
    
    public int getProgresksiegi() {
        return progresksiegi;
    }
    
    public void setProgresksiegi(int progresksiegi) {
        this.progresksiegi = progresksiegi;
    }
    
    public int getProgreskasa() {
        return progreskasa;
    }
    
    public void setProgreskasa(int progreskasa) {
        this.progreskasa = progreskasa;
    }
    
    public double getObrot() {
        return obrot;
    }
    
    public void setObrot(double obrot) {
        this.obrot = obrot;
    }
    
//</editor-fold>
    
}
