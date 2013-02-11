/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SesjaDAO;
import entity.Sesja;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Calendar;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@SessionScoped
public class SesjaView implements Serializable{
    private static String nrsesji;
    private static String uzytk;
    @Inject
    private Sesja sesja;
    @Inject
    private SesjaDAO sesjaDAO;
    
    public void dodajwydruk(){
        try {
            sesja = sesjaDAO.find(nrsesji);
            int ilosc = sesja.getIloscwydrukow();
            ilosc = ilosc+1;
            sesja.setIloscwydrukow(ilosc);
            sesjaDAO.edit(sesja);
        } catch (Exception e) {
        }
    }
    
    public String dodajdokument(){
        try {
            sesja = sesjaDAO.find(nrsesji);
            int ilosc = sesja.getIloscdokumentow();
            ilosc = ilosc+1;
            sesja.setIloscdokumentow(ilosc);
            sesjaDAO.edit(sesja);
        } catch (Exception e) {
        }
        return "/ksiegowa/ksiegowaIndex.xhtml?faces-redirect=true";
    }

    public static String getNrsesji() {
        return nrsesji;
    }

    public static void setNrsesji(String nrsesji) {
        SesjaView.nrsesji = nrsesji;
    }

    public static String getUzytk() {
        return uzytk;
    }

    public static void setUzytk(String uzytk) {
        SesjaView.uzytk = uzytk;
    }
    
    
}
