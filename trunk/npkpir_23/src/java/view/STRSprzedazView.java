/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import embeddable.Umorzenie;
import entity.SrodekTrw;
import error.E;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class STRSprzedazView extends STRTabView implements Serializable {
    private static final long serialVersionUID = 1L;
    //do sprzedawania srodko trwalych
    private List<SrodekTrw> wybranesrodkitrwale;
    private List<SrodekTrw> grupausun;
    private String data;
    private String nrwlasny;
    private STRTabView sTRTabView;
    
   
    
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
            p.setStyl("color: blue; font-style:  italic;");
            p.setDatasprzedazy(data);
            p.setNrwldokumentu(nrwlasny);
            int rok = Integer.parseInt(data.substring(0,4));
            int mc = Integer.parseInt(data.substring(5,7));
            String datazakupu = p.getDatazak();
            int rokzakupu = Integer.parseInt(datazakupu.substring(0,4));
            int mczakupu = Integer.parseInt(datazakupu.substring(5,7));
            Double suma = 0.0;
            Double umorzeniesprzedaz = 0.0;
            for(Umorzenie x : p.getUmorzWyk()){
                if (x.getRokUmorzenia()<rok){
                    suma += x.getKwota().doubleValue();
                } else if (x.getRokUmorzenia()==rok&&x.getMcUmorzenia()<mc) {
                    suma += x.getKwota().doubleValue();
                } else if (x.getRokUmorzenia()==rok&&x.getMcUmorzenia()==mc){
                    umorzeniesprzedaz = p.getNetto()-p.getUmorzeniepoczatkowe()-suma+p.getNiepodlegaamortyzacji();
                    x.setKwota(BigDecimal.valueOf(umorzeniesprzedaz).setScale(2, RoundingMode.HALF_EVEN));
                    p.setKwotaodpislikwidacja(x.getKwota().doubleValue());
                } else {
                    x.setKwota(BigDecimal.ZERO);
                }
            }
            //wypadek gdy jest zakup i sprzedaz w jednym mcu
            if (rok == rokzakupu && mc == mczakupu) {
                Umorzenie y = new Umorzenie();
                y.setKwota(new BigDecimal(p.getNetto()));
                y.setRokUmorzenia(rokzakupu);
                y.setMcUmorzenia(mczakupu);
                y.setNazwaSrodka(p.getNazwa());
                y.setNrUmorzenia(1);
                p.getUmorzWyk().clear();
                p.getUmorzWyk().add(y);
            }

            try{
                sTRDAO.edit(p);
                Msg.msg("i","Naniesiono sprzedaż: "+p.getNazwa()+". Pamiętaj o wygenerowaniu nowych dokumentow umorzeń!","dodWiad:mess_add");
            } catch (Exception e) { E.e(e); 
                Msg.msg("e","Wystapił błąd - nie naniesiono sprzedaży: "+p.getNazwa(),"dodWiad:mess_add");
        }
      }

    }
    
     public void kupsrodki(){
        ListIterator it;
        it = grupausun.listIterator();
        while(it.hasNext()){
            SrodekTrw p = (SrodekTrw) it.next();
            p.setZlikwidowany(0);
            p.setStyl("color: black;");
            p.setDatasprzedazy("");
            p.setNrwldokumentu("");
            try{
                sTRDAO.edit(p);
                Msg.msg("i","Cofnięto sprzedaż/wycofanie: "+p.getNazwa()+". Pamiętaj o wygenerowaniu nowych dokumentow umorzeń!","dodWiad:mess_add");
            } catch (Exception e) { E.e(e); 
                Msg.msg("e","Wystapił błąd - nie cofnięto sprzedaży/wycofania: "+p.getNazwa(),"dodWiad:mess_add");
        }
      }

    }

    public List<SrodekTrw> getWybranesrodkitrwale() {
        return wybranesrodkitrwale;
    }

    public void setWybranesrodkitrwale(List<SrodekTrw> wybranesrodkitrwale) {
        this.wybranesrodkitrwale = wybranesrodkitrwale;
    }

    public List<SrodekTrw> getGrupausun() {
        return grupausun;
    }

    public void setGrupausun(List<SrodekTrw> grupausun) {
        this.grupausun = grupausun;
    }
  
    public STRTabView getsTRTabView() {
        return sTRTabView;
    }

    public void setsTRTabView(STRTabView sTRTabView) {
        this.sTRTabView = sTRTabView;
    }

        
  
}
