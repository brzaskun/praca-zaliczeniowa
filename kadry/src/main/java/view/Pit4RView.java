/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.PIT4R_12Bean;
import dao.DeklaracjaPIT4SchowekFacade;
import entity.DeklaracjaPIT4Schowek;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.PrimeFaces;
import pdf.PdfPIT4;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class Pit4RView  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private DeklaracjaPIT4SchowekFacade deklaracjaPIT4SchowekFacade;
    private List<DeklaracjaPIT4Schowek> lista;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    public void init() {
        lista = deklaracjaPIT4SchowekFacade.findByRokFirma(wpisView.getRokWpisu(), wpisView.getFirma());
    }

    public List<DeklaracjaPIT4Schowek> getLista() {
        return lista;
    }

    public void setLista(List<DeklaracjaPIT4Schowek> lista) {
        this.lista = lista;
    }
    
    public void usun(DeklaracjaPIT4Schowek deklaracjaPIT4Schowek) {
        if (deklaracjaPIT4Schowek!=null) {
            deklaracjaPIT4SchowekFacade.remove(deklaracjaPIT4Schowek);
            lista.remove(deklaracjaPIT4Schowek);
            Msg.msg("Usunięto dekalracje");
        } else {
            Msg.msg("e","Błąd, nie usunięto dekalracji");
        }
    }
    
    public void pokaz(DeklaracjaPIT4Schowek deklaracjaPIT4Schowek) {
        if (deklaracjaPIT4Schowek != null) {
            ObjectInputStream is = null;
            try {
                byte[] deklaracja = deklaracjaPIT4Schowek.getDeklaracja();//        ByteArrayInputStream in = new ByteArrayInputStream(this.deklaracja);
                ByteArrayInputStream in = new ByteArrayInputStream(deklaracja);
                is = new ObjectInputStream(in);
                pl.gov.crd.wzor._2021._04._02._10568.Deklaracja dekl = (pl.gov.crd.wzor._2021._04._02._10568.Deklaracja) is.readObject();
                String sciezka = PIT4R_12Bean.marszajuldoplikuxml(dekl);
                String polecenie = "wydrukXML(\""+sciezka+"\")";
                PrimeFaces.current().executeScript(polecenie);
                Msg.msg("Pobrano deklaracje");
            } catch (Exception ex) {
                System.out.println("");
            } finally {
                try {
                    is.close();
                } catch (IOException ex) {
                }
            }
        } else {
            Msg.msg("e", "Błąd, nie pobrano dekalracji");
        }
    }
    
     public void pokazpdf(DeklaracjaPIT4Schowek deklaracjaPIT4Schowek) {
        if (deklaracjaPIT4Schowek != null) {
            ObjectInputStream is = null;
            try {
                byte[] deklaracja = deklaracjaPIT4Schowek.getDeklaracja();//        ByteArrayInputStream in = new ByteArrayInputStream(this.deklaracja);
                ByteArrayInputStream in = new ByteArrayInputStream(deklaracja);
                is = new ObjectInputStream(in);
                pl.gov.crd.wzor._2021._04._02._10568.Deklaracja dekl = (pl.gov.crd.wzor._2021._04._02._10568.Deklaracja) is.readObject();
                String nazwapliku = PdfPIT4.drukuj(dekl);
                String polecenie = "wydrukPDF(\""+nazwapliku+"\")";
                PrimeFaces.current().executeScript(polecenie);
                Msg.msg("Pobrano deklaracje");
            } catch (Exception ex) {
                System.out.println("");
            } finally {
                try {
                    is.close();
                } catch (IOException ex) {
                }
            }
        } else {
            Msg.msg("e", "Błąd, nie pobrano dekalracji");
        }
    }
    
}
