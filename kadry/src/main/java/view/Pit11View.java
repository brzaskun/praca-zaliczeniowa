/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.PIT11_27Bean;
import dao.DeklaracjaPIT11SchowekFacade;
import entity.DeklaracjaPIT11Schowek;
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
import pdf.PdfPIT11;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class Pit11View  implements Serializable {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private DeklaracjaPIT11SchowekFacade deklaracjaPIT11SchowekFacade;
    private List<DeklaracjaPIT11Schowek> lista;
    @Inject
    private WpisView wpisView;
    
    @PostConstruct
    public void init() {
        lista = deklaracjaPIT11SchowekFacade.findByRokFirma(wpisView.getRokWpisu(), wpisView.getFirma());
    }

    public List<DeklaracjaPIT11Schowek> getLista() {
        return lista;
    }

    public void setLista(List<DeklaracjaPIT11Schowek> lista) {
        this.lista = lista;
    }
    
    public void usun(DeklaracjaPIT11Schowek deklaracjaPIT11Schowek) {
        if (deklaracjaPIT11Schowek!=null) {
            deklaracjaPIT11SchowekFacade.remove(deklaracjaPIT11Schowek);
            lista.remove(deklaracjaPIT11Schowek);
            Msg.msg("Usunięto dekalracje");
        } else {
            Msg.msg("e","Błąd, nie usunięto dekalracji");
        }
    }
    
    public void pokaz(DeklaracjaPIT11Schowek deklaracjaPIT11Schowek) {
        if (deklaracjaPIT11Schowek != null) {
            ObjectInputStream is = null;
            try {
                byte[] deklaracja = deklaracjaPIT11Schowek.getDeklaracja();//        ByteArrayInputStream in = new ByteArrayInputStream(this.deklaracja);
                ByteArrayInputStream in = new ByteArrayInputStream(deklaracja);
                is = new ObjectInputStream(in);
                pl.gov.crd.wzor._2021._03._04._10477.Deklaracja dekl = (pl.gov.crd.wzor._2021._03._04._10477.Deklaracja) is.readObject();
                String sciezka = PIT11_27Bean.marszajuldoplikuxml(dekl);
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
    
    public void pokazpdf(DeklaracjaPIT11Schowek deklaracjaPIT11Schowek) {
        if (deklaracjaPIT11Schowek != null) {
            ObjectInputStream is = null;
            try {
                byte[] deklaracja = deklaracjaPIT11Schowek.getDeklaracja();//        ByteArrayInputStream in = new ByteArrayInputStream(this.deklaracja);
                ByteArrayInputStream in = new ByteArrayInputStream(deklaracja);
                is = new ObjectInputStream(in);
                pl.gov.crd.wzor._2021._03._04._10477.Deklaracja dekl = (pl.gov.crd.wzor._2021._03._04._10477.Deklaracja) is.readObject();
                String nazwapliku = PdfPIT11.drukuj(dekl, wpisView.getUzer().getImieNazwiskoTelefon());
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
