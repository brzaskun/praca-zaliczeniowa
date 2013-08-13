/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PozycjenafakturzeDAO;
import embeddable.Pozycjenafakturzebazadanych;
import entity.Pozycjenafakturze;
import entity.PozycjenafakturzePK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
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
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    private int lewy;
    private int gora;
    private String co;
    private static String lewyTablica;
    private static String goraTablica;
    private static String coTablica;
    private static final List<Pozycjenafakturzebazadanych> zestaw;
    
    static{
        zestaw = new ArrayList<>();
        zestaw.add(new Pozycjenafakturzebazadanych(1, "serek topiony", "20.45", "kg", 12, 8.5, 125, 23, 12, 147));
        zestaw.add(new Pozycjenafakturzebazadanych(1, "koperek topiony", "20.45", "kg", 12, 8.5, 125, 23, 12, 147));
        zestaw.add(new Pozycjenafakturzebazadanych(1, "marchewka topiona", "20.45", "kg", 12, 8.5, 125, 23, 12, 147));
    }
        
    
    @Inject private PozycjenafakturzeDAO pozycjeDAO;
    
    public void zachowajpozycje(){
    System.out.println("lolo");
    PozycjenafakturzePK klucz = new PozycjenafakturzePK();
        klucz.setNazwa(co);
        klucz.setPodatnik(wpisView.getPodatnikWpisu());
    Pozycjenafakturze pozycje = new Pozycjenafakturze(klucz, true, gora, lewy);
    try{
        pozycjeDAO.dodaj(pozycje);
    } catch (Exception e){
        pozycjeDAO.edit(pozycje);
    }
    Msg.msg("i", pozycje.toString(),"form:messages");
    }

   public void odchowaj(){
       List<Pozycjenafakturze> lista = pozycjeDAO.findFakturyPodatnik(wpisView.getPodatnikWpisu());
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

   //<editor-fold defaultstate="collapsed" desc="comment">
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
   
   public List<Pozycjenafakturzebazadanych> getZestaw() {
       return zestaw;
   }
   
   public WpisView getWpisView() {
       return wpisView;
   }
   
   public void setWpisView(WpisView wpisView) {
       this.wpisView = wpisView;
   }
   
   
   //</editor-fold>
   
}
