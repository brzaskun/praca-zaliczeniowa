/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansPodpis.Xad;
import dao.DeklaracjavatUEDAO;
import deklaracje.vatue.m4.Deklaracja;
import deklaracje.vatue.m4.VATUEM4Bean;
import embeddable.Kwartaly;
import embeddable.TKodUS;
import embeddable.VatUe;
import entity.DeklaracjavatUE;
import error.E;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Iterator;
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
    @Inject
    private TKodUS tKodUS;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value="#{vatUeFKView}")
    private VatUeFKView vatUeFKView;
    @Inject
    private DeklaracjavatUEDAO deklaracjavatUEDAO;
    
    public void tworzdeklaracjekorekta(List<VatUe> lista) {
        deklaracjavatUEDAO.usundeklaracjeUE(wpisView);
        for (Iterator<DeklaracjavatUE> it = vatUeFKView.getDeklaracjeUE().iterator(); it.hasNext();) {
            DeklaracjavatUE d = it.next();
            if (d.getMiesiac().equals(wpisView.getMiesiacWpisu()) && d.getRok().equals(wpisView.getRokWpisuSt())) {
                vatUeFKView.getDeklaracjeUE().remove(d);
                break;
            }
        }
        tworzdeklaracje(lista);
    }
    
    public void tworzdeklaracje(List<VatUe> lista) {
        try {
            String deklaracja = sporzadz(lista);
            Object[] podpisanadeklaracja = podpiszDeklaracje(deklaracja);
            if (podpisanadeklaracja != null) {
                DeklaracjavatUE deklaracjavatUE = generujdeklaracje(podpisanadeklaracja);
                deklaracjavatUEDAO.dodaj(deklaracjavatUE);
                vatUeFKView.getDeklaracjeUE().add(deklaracjavatUE);
                Msg.msg("Sporządzono deklarację VAT-UE miesięczną wersja 4");
            } else {
                Msg.msg("e","Wystąpił błąd. Niesporządzono deklaracji VAT-UE. Sprawdź czy włożono kartę z podpisem! Sprawdź oznaczenia krajów i NIP-y");
            }
        } catch (Exception e) {
            E.e(e);
            Msg.msg("e","Wystąpił błąd. Niesporządzono deklaracji VAT-UE miesięczną wersja 4");
        }
    }
    
    private String sporzadz(List<VatUe> lista) {
        deklaracje.vatue.m4.Deklaracja deklaracja = new Deklaracja();
        String kodurzedu = tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy());
        deklaracja.setNaglowek(VATUEM4Bean.tworznaglowek(wpisView.getMiesiacWpisu(),wpisView.getRokWpisuSt(),kodurzedu));
        deklaracja.setPodmiot1(VATUEM4Bean.podmiot1(wpisView));
        deklaracja.setPozycjeSzczegolowe(VATUEM4Bean.pozycjeszczegolowe(lista));
        deklaracja.setPouczenie(BigDecimal.ONE);
        return VATUEM4Bean.marszajuldoStringu(deklaracja, wpisView).substring(17);
    }

    private Object[] podpiszDeklaracje(String xml) {
        Object[] deklaracjapodpisana = null;
        try {
            deklaracjapodpisana = Xad.podpisz(xml);
        } catch (Exception e) {
            E.e(e);
        }
        return deklaracjapodpisana;
    }
    
    private DeklaracjavatUE generujdeklaracje(Object[] podpisanadeklaracja) {
        DeklaracjavatUE deklaracjavatUE = new DeklaracjavatUE();
        deklaracjavatUE.setPodatnik(wpisView.getPodatnikWpisu());
        deklaracjavatUE.setMiesiac(wpisView.getMiesiacWpisu());
        deklaracjavatUE.setRok(wpisView.getRokWpisuSt());
        deklaracjavatUE.setDeklaracja((String) podpisanadeklaracja[1]);
        deklaracjavatUE.setDeklaracjapodpisana((byte[]) podpisanadeklaracja[0]);
        deklaracjavatUE.setNrkwartalu(Kwartaly.getMapamckw().get(wpisView.getMiesiacWpisu()));
        deklaracjavatUE.setJestcertyfikat(true);
        deklaracjavatUE.setKodurzedu(tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy()));
        deklaracjavatUE.setSporzadzil(wpisView.getWprowadzil().getLogin());
        deklaracjavatUE.setWzorschemy("http://crd.gov.pl/wzor/2017/01/11/3846/");
        return deklaracjavatUE;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public VatUeFKView getVatUeFKView() {
        return vatUeFKView;
    }

    public void setVatUeFKView(VatUeFKView vatUeFKView) {
        this.vatUeFKView = vatUeFKView;
    }

   

  
    
    
    
}
