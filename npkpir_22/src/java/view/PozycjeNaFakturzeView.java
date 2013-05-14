/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PozycjenafakturzeDAO;
import entity.Pozycjenafakturze;
import entity.PozycjenafakturzePK;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.event.ActionListener;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class PozycjeNaFakturzeView implements Serializable{
    
    private int lewy;
    private int gora;
    private String co;
    private static String lewyTablica;
    private static String goraTablica;
    private static String coTablica;
    
    @Inject private PozycjenafakturzeDAO pozycjeDAO;
    
    public void zachowajpozycje(){
    System.out.println("lolo");
    PozycjenafakturzePK klucz = new PozycjenafakturzePK();
    klucz.setNazwa(co);
    klucz.setPodatnik("Test");
    Pozycjenafakturze pozycje = new Pozycjenafakturze(klucz, lewy, gora, true);
    try{
        pozycjeDAO.dodaj(pozycje);
    } catch (Exception e){
        pozycjeDAO.edit(pozycje);
    }
    Msg.msg("i", pozycje.toString(),"form:messages");
    }

   public void odchowaj(){
       List<Pozycjenafakturze> lista = pozycjeDAO.findAll();
       if(!lista.isEmpty()){
           lewyTablica = "";
           goraTablica = "";
           coTablica = "";
       for(Pozycjenafakturze p : lista){
        lewyTablica = lewyTablica + p.getLewy()+",";
        goraTablica = goraTablica + p.getGora()+",";
        coTablica = coTablica + p.getPozycjenafakturzePK().getNazwa()+",";
       }
       }
   }

    public int getLewy() {
        return lewy;
    }

    public void setLewy(int lewy) {
        this.lewy = lewy;
    }

    public int getGora() {
        return gora;
    }

    public void setGora(int gora) {
        this.gora = gora;
    }

    public String getCo() {
        return co;
    }

    public void setCo(String co) {
        this.co = co;
    }

    public String getLewyTablica() {
        return lewyTablica;
    }

    public void setLewyTablica(String lewyTablica) {
        PozycjeNaFakturzeView.lewyTablica = lewyTablica;
    }

    public String getGoraTablica() {
        return goraTablica;
    }

    public void setGoraTablica(String goraTablica) {
        PozycjeNaFakturzeView.goraTablica = goraTablica;
    }

    public String getCoTablica() {
        return coTablica;
    }

    public void setCoTablica(String coTablica) {
        PozycjeNaFakturzeView.coTablica = coTablica;
    }

    
    
  
    
    
}
