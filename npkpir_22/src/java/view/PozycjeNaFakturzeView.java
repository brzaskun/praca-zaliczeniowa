/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PozycjenafakturzeDAO;
import entity.Pozycjenafakturze;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.primefaces.context.RequestContext;

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
    @Inject private PozycjenafakturzeDAO pozycjeDAO;
    
    public void zachowaj(){
    Pozycjenafakturze pozycje = new Pozycjenafakturze(1,"Test", co, lewy, gora, true);
    pozycjeDAO.edit(pozycje);
    Msg.msg("i", pozycje.toString(),"form:messages");
    }

   public void odchowaj(){
       List<Pozycjenafakturze> lista = pozycjeDAO.findAll();
       Pozycjenafakturze wiersz = lista.get(0);
       lewy = wiersz.getLewy();
       gora = wiersz.getGora();
       co = wiersz.getNazwa();
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
    
    
  
    
    
}
