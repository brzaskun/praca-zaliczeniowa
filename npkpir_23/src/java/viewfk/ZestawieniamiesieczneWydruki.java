/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewfk;

import entity.UPO;
import interceptor.ConstructorInterceptor;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import jpkview.Jpk_VAT2NView;
import msg.Msg;
import view.DeklaracjevatView;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class ZestawieniamiesieczneWydruki  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private SymulacjaWynikuView symulacjaWynikuView;
    @Inject
    private SymulacjaWynikuNarastajacoView symulacjaWynikuNarastajacoView;
    @Inject
    private DeklaracjevatView deklaracjevatView;
    @Inject
    private Jpk_VAT2NView jpk_VAT2NView;
    
    public void wydruki() {
        symulacjaWynikuView.init();
        symulacjaWynikuView.drukuj(1);
        symulacjaWynikuView.drukuj(2);
        symulacjaWynikuNarastajacoView.init();
        symulacjaWynikuNarastajacoView.drukuj();
        deklaracjevatView.pobierzwyslane();
        deklaracjevatView.drukujdeklaracje();
        UPO upo = jpk_VAT2NView.initDruk();
        if (upo!=null) {
            jpk_VAT2NView.drukujUPO(upo);
            jpk_VAT2NView.zachowajJPK(upo);
        }
        Msg.msg("Wyzdrukowano zestawienia za miesiąc");
    }
    
}
