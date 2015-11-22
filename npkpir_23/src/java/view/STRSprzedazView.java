/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.STRDAO;
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
import javax.faces.bean.ManagedProperty;
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
    @ManagedProperty(value = "#{STRTableView}")
    private STRTabView sTRTabView;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    
   
    
    public STRSprzedazView() {
        wybranesrodkitrwale = new ArrayList<>();
    }
    
    public void sprzedajgrupa(String dat, String nr) {
        grupausun = new ArrayList<>();
        grupausun = wybranesrodkitrwale;
        data = dat;
        nrwlasny = nr;
    }
   
    public void sprzedajsrodki() {
        ListIterator it;
        it = grupausun.listIterator();
        while (it.hasNext()) {
            SrodekTrw sprzedawanySrodekTrw = (SrodekTrw) it.next();
            int rok = Integer.parseInt(data.substring(0,4));
            int mc = Integer.parseInt(data.substring(5,7));
            obsluztransakcje(sprzedawanySrodekTrw,rok,mc);
            try {
                sTRDAO.edit(sprzedawanySrodekTrw);
                Msg.msg("i", "Naniesiono sprzedaż: " + sprzedawanySrodekTrw.getNazwa() + ". Pamiętaj o wygenerowaniu nowych dokumentow umorzeń!", "dodWiad:mess_add");
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystapił błąd - nie naniesiono sprzedaży: " + sprzedawanySrodekTrw.getNazwa(), "dodWiad:mess_add");
            }
        }
    }

    public void sprzedazsrodkaFK() {
        SrodekTrw sprzedawanySrodekTrw = sTRTabView.getWybranysrodektrwalyPosiadane();
        if (sprzedawanySrodekTrw != null) {
            obsluztransakcje(sprzedawanySrodekTrw, wpisView.getRokWpisu(), Integer.parseInt(wpisView.getMiesiacWpisu()));
            try {
                sTRDAO.edit(sprzedawanySrodekTrw);
                Msg.msg("i", "Naniesiono sprzedaż: " + sprzedawanySrodekTrw.getNazwa() + ". Pamiętaj o wygenerowaniu nowych dokumentow umorzeń!", "dodWiad:mess_add");
            } catch (Exception e) {
                E.e(e);
                Msg.msg("e", "Wystapił błąd - nie naniesiono sprzedaży: " + sprzedawanySrodekTrw.getNazwa(), "dodWiad:mess_add");
            }
        }
    }
    
    
    private void obsluztransakcje(SrodekTrw sprzedawanySrodekTrw, int rok, int mc) {
        sprzedajsrodek(sprzedawanySrodekTrw, rok, mc);
        String datazakupu = sprzedawanySrodekTrw.getDatazak();
        int rokzakupu = Integer.parseInt(datazakupu.substring(0,4));
        int mczakupu = Integer.parseInt(datazakupu.substring(5,7));
        //wypadek gdy jest zakup i sprzedaz w jednym mcu
        if (rok == rokzakupu && mc == mczakupu) {
            sprzedajtensammc(sprzedawanySrodekTrw, rokzakupu, mczakupu);
        }
    }
    
    private void sprzedajsrodek(SrodekTrw sprzedawanySrodekTrw, int rok, int mc) {
        sprzedawanySrodekTrw.setZlikwidowany(9);
            sprzedawanySrodekTrw.setStyl("color: blue; font-style:  italic;");
            sprzedawanySrodekTrw.setDatasprzedazy(data);
            sprzedawanySrodekTrw.setNrwldokumentu(nrwlasny);
            Double suma = 0.0;
            Double umorzeniesprzedaz = 0.0;
            for(Umorzenie x : sprzedawanySrodekTrw.getUmorzWyk()){
                if (x.getRokUmorzenia()<rok){
                    suma += x.getKwota().doubleValue();
                } else if (x.getRokUmorzenia()==rok&&x.getMcUmorzenia()<mc) {
                    suma += x.getKwota().doubleValue();
                } else if (x.getRokUmorzenia()==rok&&x.getMcUmorzenia()==mc){
                    umorzeniesprzedaz = sprzedawanySrodekTrw.getNetto()-sprzedawanySrodekTrw.getUmorzeniepoczatkowe()-suma+sprzedawanySrodekTrw.getNiepodlegaamortyzacji();
                    x.setKwota(BigDecimal.valueOf(umorzeniesprzedaz).setScale(2, RoundingMode.HALF_EVEN));
                    sprzedawanySrodekTrw.setKwotaodpislikwidacja(x.getKwota().doubleValue());
                } else {
                    x.setKwota(BigDecimal.ZERO);
                }
            }
    }
    
    private void sprzedajtensammc(SrodekTrw sprzedawanySrodekTrw,int rokzakupu, int mczakupu) {
        Umorzenie y = new Umorzenie();
        y.setKwota(new BigDecimal(sprzedawanySrodekTrw.getNetto()));
        y.setRokUmorzenia(rokzakupu);
        y.setMcUmorzenia(mczakupu);
        y.setNazwaSrodka(sprzedawanySrodekTrw.getNazwa());
        y.setNrUmorzenia(1);
        sprzedawanySrodekTrw.setUmorzWyk(new ArrayList<Umorzenie>());
        sprzedawanySrodekTrw.getUmorzWyk().add(y);
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

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

   
    
  
}
