/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import entity.Dokfk;
import entity.DokfkPK;
import entity.Wiersz;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import msg.Msg;
import session.DokfkFacade;
import session.WierszFacade;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class DokfkView implements Serializable{
    private Dokfk dokfk;
    private DokfkPK dokfkPK;
    private Wiersz wiersz;
    private static ArrayList<Dokfk> dokfklist;
    @Inject DokfkFacade dokfkFacade;
    @Inject private WierszFacade wierszFacade;

    public DokfkView() {
        dokfklist = new ArrayList<>();
        dokfk = new Dokfk();
        dokfkPK = new DokfkPK();
        wiersz = new Wiersz();
    }

    @PostConstruct
    private void init() {
        try {
            dokfklist.addAll(dokfkFacade.findAll());
        } catch (Exception e) {}
    }
    
    
    public void dodaj() {
        List<Wiersz> listatwierszy = new ArrayList<>();
        //wiersz.setIddokumentuobcy(dokfk);
        wiersz.setDokfk(dokfk);
        listatwierszy.add(wiersz);
        dokfk.setWierszList(listatwierszy);
        dokfk.setDokfkPK(dokfkPK);
        dokfkFacade.edit(dokfk);
        Msg.msg("i", "Dodano dokument "+dokfk.getOpisdokumentu());
        Msg.msg("i", "Dodano wiersz "+wiersz.getOpiswiersza());
    }

    //<editor-fold defaultstate="collapsed" desc="comment">
    public Dokfk getDokfk() {
        return dokfk;
    }
    
    public void setDokfk(Dokfk dokfk) {
        this.dokfk = dokfk;
    }
    
    public ArrayList<Dokfk> getDokfklist() {
        return dokfklist;
    }
    
    public static ArrayList<Dokfk> getDokfklistS() {
        return dokfklist;
    }
    
    public void setDokfklist(ArrayList<Dokfk> dokfklist) {
        this.dokfklist = dokfklist;
    }
        
    public Wiersz getWiersz() {
        return wiersz;
    }

    public void setWiersz(Wiersz wiersz) {
        this.wiersz = wiersz;
    }
    
    public DokfkPK getDokfkPK() {
        return dokfkPK;
    }

    public void setDokfkPK(DokfkPK dokfkPK) {
        this.dokfkPK = dokfkPK;
    }
    
    //</editor-fold>

    

    
}
