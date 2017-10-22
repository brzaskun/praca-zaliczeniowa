/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansPodpis.Xad;
import dao.Deklaracjavat27DAO;
import deklaracje.vatue.m4.Deklaracja;
import deklaracje.vatue.m4.VATUEM4Bean;
import embeddable.Kwartaly;
import embeddable.TKodUS;
import embeddable.VatUe;
import entity.Deklaracjavat27;
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
public class VAT27DeklaracjaView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private TKodUS tKodUS;
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value="#{vat27View}")
    private Vat27View vat27View;
    @Inject
    private Deklaracjavat27DAO deklaracjavat27DAO;
    
    public void tworzdeklaracjekorekta(List<VatUe> lista) {
        deklaracjavat27DAO.usundeklaracjeUE(wpisView);
        for (Iterator<Deklaracjavat27> it = vat27View.getDeklaracjevat27().iterator(); it.hasNext();) {
            Deklaracjavat27 d = it.next();
            if (d.getMiesiac().equals(wpisView.getMiesiacWpisu()) && d.getRok().equals(wpisView.getRokWpisuSt())) {
                vat27View.getDeklaracjevat27().remove(d);
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
                Deklaracjavat27 deklaracjavat27 = generujdeklaracje(podpisanadeklaracja);
                deklaracjavat27.setPozycje(lista);
                deklaracjavat27DAO.dodaj(deklaracjavat27);
                vat27View.getDeklaracjevat27().add(deklaracjavat27);
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
    
    private Deklaracjavat27 generujdeklaracje(Object[] podpisanadeklaracja) {
        Deklaracjavat27 deklaracjavat27 = new Deklaracjavat27();
        deklaracjavat27.setPodatnik(wpisView.getPodatnikWpisu());
        deklaracjavat27.setMiesiac(wpisView.getMiesiacWpisu());
        deklaracjavat27.setRok(wpisView.getRokWpisuSt());
        deklaracjavat27.setDeklaracja((String) podpisanadeklaracja[1]);
        deklaracjavat27.setDeklaracjapodpisana((byte[]) podpisanadeklaracja[0]);
        deklaracjavat27.setNrkwartalu(Kwartaly.getMapamckw().get(wpisView.getMiesiacWpisu()));
        deklaracjavat27.setJestcertyfikat(true);
        deklaracjavat27.setKodurzedu(tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy()));
        deklaracjavat27.setSporzadzil(wpisView.getWprowadzil().getLogin());
        deklaracjavat27.setWzorschemy("http://crd.gov.pl/wzor/2017/01/11/3846/");
        return deklaracjavat27;
    }
    
    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Vat27View getVat27View() {
        return vat27View;
    }

    public void setVat27View(Vat27View vat27View) {
        this.vat27View = vat27View;
    }

   
    
    
    
}
