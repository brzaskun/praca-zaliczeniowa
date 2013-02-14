/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import dao.PodatnikDAO;
import entity.Podatnik;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean(name="Opis")
@RequestScoped
public class Opis implements Serializable{

    private List<String> opisy;
    @Inject private PodatnikDAO podatnikDAO;
    @Inject private Podatnik podatnik;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
     

    @PostConstruct
    private void init(){
        podatnik = podatnikDAO.find(wpisView.getPodatnikWpisu());
        try{
        opisy.addAll(podatnik.getOpisypkpir());
        } catch (Exception e){}
    }
    
    public Opis() {
        opisy = new ArrayList();
    }


    public void dodajOpis(ValueChangeEvent e){
        String ns = e.getNewValue().toString();
        if(ns.length()>5){
            if(!opisy.contains(ns)){
                opisy.add(ns);
            }
        }
       podatnik.setOpisypkpir(opisy);
       podatnikDAO.edit(podatnik);
    }
    
     public List<String> complete(String query) {  
        List<String> results = new ArrayList<String>();  
        String kl = new String();
         for(String p : opisy) {  
            if(p.startsWith(query)) {
                 results.add(p);
             }
        }  
        return results;  
    }  

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }
     
    

}
