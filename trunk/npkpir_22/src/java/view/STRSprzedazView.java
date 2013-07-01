/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import com.sun.org.apache.xml.internal.utils.ListingErrorHandler;
import dao.STRDAO;
import embeddable.Umorzenie;
import entity.SrodekTrw;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class STRSprzedazView extends STRTabView implements Serializable {
    //do sprzedawania srodko trwalych
    private static List<SrodekTrw> wybranesrodkitrwale;
    private static List<SrodekTrw> grupausun;
    private static String data;
    private static String nrwlasny;
    
    public STRSprzedazView() {
        wybranesrodkitrwale = new ArrayList<>();
    }
    
    public void sprzedajgrupa(String dat, String nr) {
        grupausun = new ArrayList<>();
        grupausun = wybranesrodkitrwale;
        data = dat;
        nrwlasny = nr;
    }
   
    public void sprzedajsrodki(){
        ListIterator it;
        it = grupausun.listIterator();
        while(it.hasNext()){
            SrodekTrw p = (SrodekTrw) it.next();
            p.setZlikwidowany(9);
            p.setStyl("color: blue;");
            p.setDatasprzedazy(data);
            p.setNrwldokzak(nrwlasny);
            int rok = Integer.parseInt(data.substring(0,4));
            int mc = Integer.parseInt(data.substring(6,7));
            Double suma = 0.0;
            Double umorzeniesprzedaz = 0.0;
            for(Umorzenie x : p.getUmorzWyk()){
                if(x.getRokUmorzenia()<=rok&&x.getMcUmorzenia()<mc){
                    suma += x.getKwota().doubleValue();
                } else if (x.getRokUmorzenia()==rok&&x.getMcUmorzenia()==mc){
                    umorzeniesprzedaz = p.getNetto()-p.getUmorzeniepoczatkowe()-suma;
                    x.setKwota(BigDecimal.valueOf(umorzeniesprzedaz));
                    p.setKwotaodpislikwidacja(umorzeniesprzedaz);
                } else {
                    x.setKwota(BigDecimal.ZERO);
                }
            }
            try{
                sTRDAO.edit(p);
                Msg.msg("i","Naniesiono sprzedaż","dodWiad:mess_add");
            } catch (Exception e) {
                Msg.msg("e","Wystapił błąd - nie naniesiono sprzedaży","dodWiad:mess_add");
        }
      }
      
    }
    
  

    public List<SrodekTrw> getWybranesrodkitrwale() {
        return wybranesrodkitrwale;
    }

    public void setWybranesrodkitrwale(List<SrodekTrw> wybranesrodkitrwale) {
        this.wybranesrodkitrwale = wybranesrodkitrwale;
    }

      
  
}
