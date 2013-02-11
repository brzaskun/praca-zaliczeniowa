/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DeklaracjevatDAO;
import entity.Deklaracjevat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
public class DeklaracjevatView implements Serializable {
    @Inject private DeklaracjevatDAO deklaracjevatDAO;
    private List<Deklaracjevat> wyslane;
    private List<Deklaracjevat> oczekujace;

    public DeklaracjevatView() {
        wyslane = new ArrayList<>();
        oczekujace = new ArrayList<>();
    }
    
    
    @PostConstruct
    private void init(){
        try{
        List<Deklaracjevat> temp = deklaracjevatDAO.getDownloaded();
        for(Deklaracjevat p : temp){
            if(p.getIdentyfikator().equals("")){
                oczekujace.add(p);
            } else {
                wyslane.add(p);
            }
        }
        } catch (Exception e){}
    }

    public List<Deklaracjevat> getWyslane() {
        return wyslane;
    }

    public void setWyslane(List<Deklaracjevat> wyslane) {
        this.wyslane = wyslane;
    }

    public List<Deklaracjevat> getOczekujace() {
        return oczekujace;
    }

    public void setOczekujace(List<Deklaracjevat> oczekujace) {
        this.oczekujace = oczekujace;
    }
    
    
}
