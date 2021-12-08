/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.KalendarzmiesiacFacade;
import dao.RachunekdoumowyzleceniaFacade;
import data.Data;
import embeddable.Mce;
import entity.Kalendarzmiesiac;
import entity.Rachunekdoumowyzlecenia;
import entity.Umowa;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import z.Z;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class RachunekZlecenieView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private Umowa umowabiezaca;
    private Rachunekdoumowyzlecenia rachunekdoumowyzlecenia;
    private boolean trzebazrobicrachunek;
    @Inject
    private WpisView wpisView;
    @Inject
    private KalendarzmiesiacFacade kalendarzmiesiacFacade;
    @Inject
    private RachunekdoumowyzleceniaFacade rachunekdoumowyzleceniaFacade;
    
    

    public void init() {
       umowabiezaca = wpisView.getUmowa();
       if (umowabiezaca!=null && umowabiezaca.getUmowakodzus().isZlecenie()) {
        String datado = umowabiezaca.getDatado();
        trzebazrobicrachunek = false;
        rachunekdoumowyzlecenia = rachunekdoumowyzleceniaFacade.findByRokMcUmowa(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu(), umowabiezaca);
         if (rachunekdoumowyzlecenia == null) {
             if (Integer.parseInt(umowabiezaca.getRok()) < wpisView.getRokWpisuInt()) {
                 trzebazrobicrachunek = true;
             } else if (Integer.parseInt(umowabiezaca.getRok()) == wpisView.getRokWpisuInt()) {
                 if (Mce.getMiesiacToNumber().get(umowabiezaca.getMc()) <= Integer.parseInt(wpisView.getMiesiacWpisu())) {
                     trzebazrobicrachunek = true;
                 }
             }
             if (datado != null && !datado.equals("")) {
                 String rokdo = Data.getRok(datado);
                 String mcdo = Data.getMc(datado);
                 if (Integer.parseInt(rokdo) < wpisView.getRokWpisuInt()) {
                     trzebazrobicrachunek = false;
                 } else if (Integer.parseInt(rokdo) == wpisView.getRokWpisuInt()) {
                     if (Mce.getMiesiacToNumber().get(mcdo) < Integer.parseInt(wpisView.getMiesiacWpisu())) {
                         trzebazrobicrachunek = false;
                     }
                 }
             }
             Kalendarzmiesiac kalendarz = kalendarzmiesiacFacade.findByRokMcUmowa(wpisView.getUmowa(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
             if (trzebazrobicrachunek && kalendarz!=null) {
                 rachunekdoumowyzlecenia = new Rachunekdoumowyzlecenia(wpisView.getUmowa());
                 rachunekdoumowyzlecenia.setDataod(Data.pierwszyDzien(wpisView));
                 rachunekdoumowyzlecenia.setDatado(Data.ostatniDzien(wpisView));
                 rachunekdoumowyzlecenia.setRok(wpisView.getRokWpisu());
                 rachunekdoumowyzlecenia.setMc(wpisView.getMiesiacWpisu());
                 if (datado != null && !datado.equals("")) {
                     String mcdo = Data.getRok(datado);
                     if (mcdo.equals(wpisView.getMiesiacWpisu())) {
                         rachunekdoumowyzlecenia.setDatado(datado);
                     }
                 }
                 double kwota = umowabiezaca.pobierzwynagrodzenieKwota();
                 double iloscgodzinzkalendarza = pobierzgodzinyzkalendarza();
                 rachunekdoumowyzlecenia.setIloscgodzin(iloscgodzinzkalendarza);
                 if (umowabiezaca.czywynagrodzeniegodzinowe()) {
                     rachunekdoumowyzlecenia.setWynagrodzeniegodzinowe(kwota);
                     rachunekdoumowyzlecenia.setKwota(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniegodzinowe() * iloscgodzinzkalendarza));
                 } else {
                     rachunekdoumowyzlecenia.setWynagrodzeniemiesieczne(kwota);
                     rachunekdoumowyzlecenia.setKwota(Z.z(kwota));
                 }
                 rachunekdoumowyzlecenia.setProcentkosztowuzyskania(umowabiezaca.getKosztyuzyskaniaprocent());
                 rachunekdoumowyzlecenia.setKoszt(Z.z(rachunekdoumowyzlecenia.getKwota() * umowabiezaca.getKosztyuzyskaniaprocent() / 100.0));
             }
         } else {
             Msg.msg("Pobrano zachowany rachunek");
         }
       }
    }

    private double pobierzgodzinyzkalendarza() {
        double zwrot = 0;
        Kalendarzmiesiac kalendarz = kalendarzmiesiacFacade.findByRokMcUmowa(wpisView.getUmowa(), wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
        double[] roboczegodz = kalendarz.roboczegodz();
        if (roboczegodz!=null) {
            zwrot = roboczegodz[1];
        }
        return zwrot;
    }
    
    public void przeliczrachunek() {
        if (rachunekdoumowyzlecenia!=null) {
            if (rachunekdoumowyzlecenia.getWynagrodzeniemiesieczne()>0.0) {
                rachunekdoumowyzlecenia.setKwota(rachunekdoumowyzlecenia.getWynagrodzeniemiesieczne());
            } else {
                rachunekdoumowyzlecenia.setKwota(Z.z(rachunekdoumowyzlecenia.getWynagrodzeniegodzinowe()*rachunekdoumowyzlecenia.getIloscgodzin()));
            }
            Msg.msg("Przeliczono kwotę rachunku");
        }
    }
    
    public void zaksieguj() {
        if (rachunekdoumowyzlecenia!=null) {
            rachunekdoumowyzleceniaFacade.create(rachunekdoumowyzlecenia);
            Msg.msg("Zachowano rachunek");
        }
    }
    
    public void usun() {
        if (rachunekdoumowyzlecenia!=null) {
            rachunekdoumowyzleceniaFacade.remove(rachunekdoumowyzlecenia);
            Msg.msg("Usunięto rachunek");
        }
    }
    
    public Umowa getUmowabiezaca() {
        return umowabiezaca;
    }

    public void setUmowabiezaca(Umowa umowabiezaca) {
        this.umowabiezaca = umowabiezaca;
    }

    public boolean isTrzebazrobicrachunek() {
        return trzebazrobicrachunek;
    }

    public void setTrzebazrobicrachunek(boolean trzebazrobicrachunek) {
        this.trzebazrobicrachunek = trzebazrobicrachunek;
    }

    public Rachunekdoumowyzlecenia getRachunekdoumowyzlecenia() {
        return rachunekdoumowyzlecenia;
    }

    public void setRachunekdoumowyzlecenia(Rachunekdoumowyzlecenia rachunekdoumowyzlecenia) {
        this.rachunekdoumowyzlecenia = rachunekdoumowyzlecenia;
    }

    

   
    
    
    
}
