/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.GranicaDAO;
import entity.Granica;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class GranicaView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private GranicaDAO granicaDAO;
    private List<Granica> granicevat;
    private List<Granica> granicepkpir;
    private List<Granica> graniceksiegi;
    private List<Granica> granicekasa;
    private final List<String> nazwygranic;
    private final Map<String,List<Granica>> granicewykaz;
    @Inject
    private Granica selected;
    @Inject
    private Granica nowa;

    public GranicaView() {
        this.granicevat = new ArrayList<>();
        this.granicepkpir = new ArrayList<>();
        this.graniceksiegi = new ArrayList<>();
        this.granicekasa = new ArrayList<>();
        this.nazwygranic = new ArrayList<>();
        this.granicewykaz = new ConcurrentHashMap<>();
        this.granicewykaz.put("vat", granicevat);
        this.granicewykaz.put("pkpir", granicepkpir);
        this.granicewykaz.put("ksiegi", graniceksiegi);
        this.granicewykaz.put("kasa", granicekasa);
        this.nazwygranic.add("vat");
        this.nazwygranic.add("pkpir");
        this.nazwygranic.add("ksiegi");
        this.nazwygranic.add("kasa");
    }
    
    @PostConstruct
    private void init() {
        List<Granica> granice = granicaDAO.findAll();
        for (Granica p : granice) {
            switch (p.getNazwalimitu()) {
                case "vat":
                    granicevat.add(p);
                    break;
                case "pkpir":
                    granicepkpir.add(p);
                    break;
                case "ksiegi":
                    graniceksiegi.add(p);
                    break;
                case "kasa":
                    granicekasa.add(p);
                    break;
            }
        }
    }

    public void dodaj(String nazwalimitu) {
        try {
            if (niema(nazwalimitu)) {
                nowa.setNazwalimitu(nazwalimitu);
                granicewykaz.get(nazwalimitu).add(nowa);
                granicaDAO.dodaj(nowa);
                Msg.dP();
                nowa = new Granica();
            } else {
                Msg.msg("e","Wpis dotyczący danego roku już jest");
            }
        } catch(Exception e) {
            E.e(e);
            Msg.dPe();
        }
        
    }
    
    public void edytuj() {
        try {
            granicaDAO.edit(nowa);
            Msg.dP();
            nowa = new Granica();
        } catch(Exception e) {
            E.e(e);
            Msg.dPe();
        }
        
    }
    
    public void usun(Granica granica) {
        try {
            granicewykaz.get(granica.getNazwalimitu()).remove(selected);
            granicaDAO.destroy(selected);
            Msg.dP();
            selected = new Granica();
        } catch(Exception e) {
            E.e(e);
            Msg.dPe();
        }
        
    }
    
    public void skopiuj() {
        nowa = selected;
        Msg.dP();
    }
    
    private boolean niema(String nazwalimitu) {
        boolean zwrot= true;
        List<Granica> l = new ArrayList<>();
        switch (nazwalimitu) {
                case "vat":
                    l = granicevat;
                    break;
                case "pkpir":
                    l = granicepkpir;
                    break;
                case "ksiegi":
                    l = graniceksiegi;
                    break;
                case "kasa":
                    l = granicekasa;
                    break;
            }
        for (Granica r : l) {
            if (r.getRok().equals(nowa.getRok())) {
                zwrot = false;
            }
        }
        return zwrot;
    }
    
    public GranicaDAO getGranicaDAO() {
        return granicaDAO;
    }

    public void setGranicaDAO(GranicaDAO granicaDAO) {
        this.granicaDAO = granicaDAO;
    }

    public List<Granica> getGranicevat() {
        return granicevat;
    }

    public void setGranicevat(List<Granica> granicevat) {
        this.granicevat = granicevat;
    }

    public List<Granica> getGranicepkpir() {
        return granicepkpir;
    }

    public void setGranicepkpir(List<Granica> granicepkpir) {
        this.granicepkpir = granicepkpir;
    }

    public List<Granica> getGraniceksiegi() {
        return graniceksiegi;
    }

    public void setGraniceksiegi(List<Granica> graniceksiegi) {
        this.graniceksiegi = graniceksiegi;
    }

    public List<Granica> getGranicekasa() {
        return granicekasa;
    }

    public void setGranicekasa(List<Granica> granicekasa) {
        this.granicekasa = granicekasa;
    }

    public Granica getSelected() {
        return selected;
    }

    public void setSelected(Granica selected) {
        this.selected = selected;
    }

    public Granica getNowa() {
        return nowa;
    }

    public void setNowa(Granica nowa) {
        this.nowa = nowa;
    }

   
    
    
    
}
