/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.UkladBRDAO;
import entityfk.UkladBR;
import entityfk.UkladBRPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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
public class UkladBRView implements Serializable{
    private static final long serialVersionUID = 1L;
    private static List<UkladBR> lista;

    public static List<UkladBR> getListaS() {
        return lista;
    }
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
            ukladBR.setPodatnik("Tymczasowy");
            ukladBR.setRok("2014");
            ukladBR.setUklad(nowy);
            nowy = "";
            ukladBRDAO.dodaj(ukladBR);
            lista.add(ukladBR);
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
        UkladBRView.lista = lista;
    }
    
    public UkladBRDAO getUkladBRDAO() {
        return ukladBRDAO;
    }
    
    public void setUkladBRDAO(UkladBRDAO ukladBRDAO) {
        this.ukladBRDAO = ukladBRDAO;
    }
    
    
//</editor-fold>
    
}
