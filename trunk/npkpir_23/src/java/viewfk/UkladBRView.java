/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.UkladBRDAO;
import entityfk.UkladBR;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class UkladBRView implements Serializable{
    private static final long serialVersionUID = 1L;
    private List<UkladBR> lista;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;

   
    @Inject private UkladBR selected;
    private String nowy;
    @Inject private UkladBRDAO ukladBRDAO;

    public UkladBRView() {
        lista = new ArrayList<>();
    }
    
    @PostConstruct
    private void init() {
       try {
            lista = ukladBRDAO.findAll();
        } catch (Exception e) {} 
    }
    
    public void wybranouklad() {
        Msg.msg("i", "Wybrano uklad "+selected.getUklad());
    }
    
    public void dodaj() {
        try {
            UkladBR ukladBR = new UkladBR();
            ukladBR.setPodatnik("Wzorcowy");
            ukladBR.setRok(wpisView.getRokWpisuSt());
            ukladBR.setUklad(nowy);
            ukladBRDAO.dodaj(ukladBR);
            lista.add(ukladBR);
            nowy = "";
            Msg.msg("i", "Dodano nowy układ");
        } catch (Exception e) {
            Msg.msg("e", "Nieudana próba dodania układu. "+e.getMessage());
        }
    }

    public void usun(UkladBR ukladBR) {
        try {
            ukladBRDAO.destroy(ukladBR);
            lista.remove(ukladBR);
            Msg.msg("i", "Usunięto wybrany układ");
        } catch (Exception e) {
            Msg.msg("e", "Nieudana próba usuniecia układu."+e.getMessage());
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public UkladBR getSelected() {
        return selected;
    }
    
    public void setSelected(UkladBR selected) {
        this.selected = selected;
    }
    
    public String getNowy() {
        return nowy;
    }
    
    public void setNowy(String nowy) {
        this.nowy = nowy;
    }

    public List<UkladBR> getLista() {
        return lista;
    }

    public void setLista(List<UkladBR> lista) {
        this.lista = lista;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    
    
    public UkladBRDAO getUkladBRDAO() {
        return ukladBRDAO;
    }
    
    public void setUkladBRDAO(UkladBRDAO ukladBRDAO) {
        this.ukladBRDAO = ukladBRDAO;
    }
    
    
//</editor-fold>
    
}
