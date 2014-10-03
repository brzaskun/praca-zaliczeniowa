/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;


import daoFK.BilansukladDAO;
import entityfk.Bilansuklad;
import entityfk.BilansukladPK;
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
public class BilansukladView implements Serializable{
    private static final long serialVersionUID = 1L;
    private static List<Bilansuklad> lista;

    public static List<Bilansuklad> getListaS() {
        return lista;
    }
    @Inject private Bilansuklad selected;
    private String nowy;
    @Inject private BilansukladDAO bilansukladDAO;

    public BilansukladView() {
        lista = new ArrayList<>();
    }
    
    @PostConstruct
    private void init() {
       try {
            lista = bilansukladDAO.findAll();
        } catch (Exception e) {} 
    }
    
    public void wybranouklad() {
        Msg.msg("i", "Wybrano uklad "+selected.getBilansukladPK().getUklad());
    }
    
    public void dodaj() {
        try {
            BilansukladPK bilansukladPK = new BilansukladPK();
            bilansukladPK.setPodatnik("Tymczasowy");
            bilansukladPK.setRok("2014");
            bilansukladPK.setUklad(nowy);
            Bilansuklad bilansuklad = new Bilansuklad();
            bilansuklad.setBilansukladPK(bilansukladPK);
            nowy = "";
            bilansukladDAO.dodaj(bilansuklad);
            lista.add(bilansuklad);
            Msg.msg("i", "Dodano nowy układ");
        } catch (Exception e) {
            Msg.msg("e", "Nieudana próba dodania układu. "+e.getMessage());
        }
    }

    public void usun(Bilansuklad bilansuklad) {
        try {
            bilansukladDAO.destroy(bilansuklad);
            lista.remove(bilansuklad);
            Msg.msg("i", "Usunięto wybrany układ");
        } catch (Exception e) {
            Msg.msg("e", "Nieudana próba usuniecia układu."+e.getMessage());
        }
    }
    
    //<editor-fold defaultstate="collapsed" desc="comment">
    public Bilansuklad getSelected() {
        return selected;
    }
    
    public void setSelected(Bilansuklad selected) {
        this.selected = selected;
    }
    
    public String getNowy() {
        return nowy;
    }
    
    public void setNowy(String nowy) {
        this.nowy = nowy;
    }
    
    
    public List<Bilansuklad> getLista() {
        return lista;
    }
    
    public void setLista(List<Bilansuklad> lista) {
        BilansukladView.lista = lista;
    }
    
    public BilansukladDAO getBilansukladDAO() {
        return bilansukladDAO;
    }
    
    public void setBilansukladDAO(BilansukladDAO bilansukladDAO) {
        this.bilansukladDAO = bilansukladDAO;
    }
    
    
//</editor-fold>
    
}
