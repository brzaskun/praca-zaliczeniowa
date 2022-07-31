/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package viewmanager;

import dao.DokDAO;
import dao.DokDAOfk;
import dao.PodatnikDAO;
import data.Data;
import entity.Dok;
import entity.Podatnik;
import entityfk.Dokfk;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class AktywacjaPodatnikow implements Serializable{
    private static final long serialVersionUID = 1L;
    
    private List<Podatnik> listapodatnikow;
    @Inject private PodatnikDAO podatnikDAO;
    @Inject private DokDAO dokDAO;
    @Inject private DokDAOfk dokDAOfk;
    private String rok;
    
    

    public AktywacjaPodatnikow() {
        listapodatnikow = Collections.synchronizedList(new ArrayList<>());
    }
    
    @PostConstruct
    private void init() { //E.m(this);
        listapodatnikow = podatnikDAO.findAllManager();
        rok = Data.aktualnyRok();
    }
    
    public void aktywacjaDeaktywacja(Podatnik p) {
        boolean aktualnystan = p.isPodmiotaktywny();
        p.setPodmiotaktywny(!aktualnystan);
        podatnikDAO.edit(p);
        listapodatnikow = podatnikDAO.findAllManager();
        Msg.msg("Zmieniono status podatnika");
    }

    public void weryfikacja() {
        if (listapodatnikow!=null) {
            for (Podatnik p : listapodatnikow) {
               //System.out.println("Podatnik: "+p.getPrintnazwa());
               try {
                    List<Dok> pkpir = dokDAO.zwrocBiezacegoKlientaRok(p, rok);
                    List<Dokfk> fk = dokDAOfk.findDokfkPodatnikRok(p, rok);
                    boolean sapkpir = true;
                    boolean safk = true;
                    if (pkpir==null||pkpir.isEmpty()) {
                        sapkpir = false;
                    } 
                    if (fk==null||fk.isEmpty()) {
                        safk = false;
                    }
                    if (sapkpir==false&&safk==false) {
                        p.setPodmiotaktywny(false);
                    }
               } catch (Exception e){}
            }
            podatnikDAO.editList(listapodatnikow);
            Msg.msg("Lista zaktualizowana");
        }
    }
    
    
    public List<Podatnik> getListapodatnikow() {
        return listapodatnikow;
    }

    public void setListapodatnikow(List<Podatnik> listapodatnikow) {
        this.listapodatnikow = listapodatnikow;
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }
    
    
    
    
}
