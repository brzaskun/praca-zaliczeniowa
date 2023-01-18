/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansPodpis.ObslugaPodpisuBean;
import error.E;
import java.io.IOException;
import java.io.Serializable;
import java.security.KeyStoreException;
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
public class PodpisView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    
    private boolean jestkarta;
    
    @PostConstruct
    private void init() {
        sprawdzczymozna(wpisView);
    }
    
//    public void sprawdzczymoznaFK(WpisView wpisView, List<Deklaracjevat> oczekujace) {
//        jestkarta = false;
//        if (oczekujace!=null && oczekujace.size()>0) {
//            try {
//                if (wpisView.getPodatnikObiekt().isPodpiscertyfikowany()) {
//                    jestkarta  = ObslugaPodpisuBean.moznapodpisacError(wpisView.getPodatnikObiekt().getKartacert(), wpisView.getPodatnikObiekt().getKartapesel());
//                }
//            } catch (KeyStoreException ex) {
//                Msg.msg("e", "Brak karty w czytniku");
//            } catch (IOException ex) {
//                Msg.msg("e", "UWAGA! Błędne hasło!");
//            } catch (Exception ex) {
//                E.e(ex);
//            }
//        }
//    }
    

    public void sprawdzczymozna(WpisView wpisView) {
        jestkarta = false;
        try {
            jestkarta  = ObslugaPodpisuBean.moznapodpisacError(null,null);
        } catch (KeyStoreException ex) {
            Msg.msg("e", "Brak karty w czytniku");
        } catch (IOException ex) {
            Msg.msg("e", "UWAGA! Błędne hasło!");
        } catch (Exception ex) {
            E.e(ex);
        }
    }
    
//    public Object[] podpiszDeklaracje(String xml, WpisView wpisView) {
//        Object[] deklaracjapodpisana = null;
//        try {
//            deklaracjapodpisana = Xad.podpisz(xml,null, null);
//        } catch (Exception e) {
//            E.e(e);
//        }
//        return deklaracjapodpisana;
//    }
    
    
    public boolean isJestkarta() {
        return jestkarta;
    }

    public void setJestkarta(boolean jestkarta) {
        this.jestkarta = jestkarta;
    }
    
    
}
