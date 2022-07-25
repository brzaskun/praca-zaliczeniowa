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
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.inject.Inject;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView; import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class Opis implements Serializable{

    private List<String> opisy;
    @Inject private PodatnikDAO podatnikDAO;
    @Inject private Podatnik podatnik;
    @Inject
    private WpisView wpisView;
     

    public Opis(){
        opisy = new ArrayList();
    }
    
    @PostConstruct
    private void init() { //E.m(this);
        podatnik = podatnikDAO.findByNazwaPelna(wpisView.getPodatnikWpisu());
        try{
            opisy.addAll(podatnik.getOpisypkpir());
        } catch (Exception e){}
    }


    public void dodajOpis(){
        Map<String, String> params = FacesContext.getCurrentInstance().getExternalContext().getRequestParameterMap();
        String ns = params.get("dodWiad:opis_input");
        if(ns.length()>5){
            if(!opisy.contains(ns.toLowerCase())){
                opisy.add(ns.toLowerCase());
                podatnik.setOpisypkpir(opisy);
                podatnikDAO.edit(podatnik);
                Msg.msg("i", "Dodano opis: "+ns);
                PrimeFaces.current().ajax().update("dodWiad:mess_add");
            }
        }
    }
    
     public List<String> complete(String query) {
        List<String> results = new ArrayList<String>();  
         for(String p : opisy) {  
            if(p.contains(query.toLowerCase())) {
                 results.add(p);
             }
        }
        if (results.isEmpty()) {
            results.add(query);
        }
        return results;  
    }  

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public List<String> getOpisy() {
        return opisy;
    }

    public void setOpisy(List<String> opisy) {
        this.opisy = opisy;
    }
     
    

}
