/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.FakturadodelementyDAO;
import entity.Fakturadodelementy;
import entity.FakturadodelementyPK;
import java.io.Serializable;
import java.util.ArrayList;
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
public class FakturadodelementyView implements Serializable{
    private static final List<String> elementynazwa;
    private static final List<String> trescelementu;
    static {
        elementynazwa = new ArrayList<>();
        elementynazwa.add("wezwaniedozapłaty");
        elementynazwa.add("warunkidostawy");
        elementynazwa.add("przewłaszczenie");
        trescelementu = new ArrayList<>();
        trescelementu.add("Niniejsza faktura jest jednocześnie wezwaniem do zapłaty");
        trescelementu.add("Dostawa na warunkach exworks");
        trescelementu.add("Do momentu zapłaty towar jest własnością sprzedawcy");
    }
    private List<Fakturadodelementy> fakturadodelementy;
    @Inject private FakturadodelementyDAO fakturadodelementyDAO;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    

    public FakturadodelementyView() {
    }
    
    @PostConstruct
    private void init(){
        try{
            fakturadodelementy = fakturadodelementyDAO.findFaktElementyPodatnik(wpisView.getPodatnikWpisu());
            if(fakturadodelementy.isEmpty()){
                int index = 0;
                for (String p : elementynazwa){
                    String podatnik = wpisView.getPodatnikWpisu();
                    FakturadodelementyPK fPK = new FakturadodelementyPK(podatnik, p);
                    Fakturadodelementy f = new Fakturadodelementy(fPK, trescelementu.get(index), false);
                    index++;
                    fakturadodelementy.add(f);
            }
            }
        } catch (Exception e){}
    }
    
    public void zachowajzmiany(){
        try {
            for (Fakturadodelementy p : fakturadodelementy){
                fakturadodelementyDAO.dodaj(p);
            }
            Msg.msg("i", "Zachowano dodatkowe elementy faktury.");
        } catch (Exception e) {
            for (Fakturadodelementy p : fakturadodelementy){
                fakturadodelementyDAO.edit(p);
            }
            Msg.msg("i", "Wyedytowano dodatkowe elementy faktury.");
        }
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public List<Fakturadodelementy> getFakturadodelementy() {
        return fakturadodelementy;
    }
    
    public void setFakturadodelementy(List<Fakturadodelementy> fakturadodelementy) {
        this.fakturadodelementy = fakturadodelementy;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }
    
    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
    //</editor-fold>
    
    
}
