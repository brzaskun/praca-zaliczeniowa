/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import deklaracje.vatue.m4.Deklaracja;
import deklaracje.vatue.m4.VATUEM4Bean;
import embeddable.TKodUS;
import embeddable.VatUe;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class VATUEDeklaracjaView implements Serializable {
    private static final long serialVersionUID = 1L;
    private deklaracje.vatue.m4.Deklaracja deklaracja;
    @Inject
    private TKodUS tKodUS;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    
    public void tworzdeklarajce(List<VatUe> lista) {
        deklaracja = new Deklaracja();
        String kodurzedu = tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy());
        deklaracja.setNaglowek(VATUEM4Bean.tworznaglowek(wpisView.getMiesiacWpisu(),wpisView.getRokWpisuSt(),kodurzedu));
        deklaracja.setPodmiot1(VATUEM4Bean.podmiot1(wpisView));
        deklaracja.setPozycjeSzczegolowe(VATUEM4Bean.pozycjeszczegolowe(lista));
        deklaracja.setPouczenie(BigDecimal.ONE);
        VATUEM4Bean.marszajuldoplikuxml(deklaracja, wpisView);
        Msg.msg("Sporządzono deklarację VAT-UE miesięczną wersja 4");
    }

   
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Deklaracja getDeklaracja() {
        return deklaracja;
    }

    public void setDeklaracja(Deklaracja deklaracja) {
        this.deklaracja = deklaracja;
    }

    
    
    
}
