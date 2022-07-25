/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import dao.DokDAOfk;
import dao.WierszBODAO;
import entityfk.Dokfk;
import error.E;
import java.io.Serializable;
import javax.inject.Named;
import javax.faces.view.ViewScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import msg.Msg;import view.WpisView; import org.primefaces.PrimeFaces;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class BilansBoUsunView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private DokDAOfk dokDAOfk;
    private boolean tojestbilanslikwidacyjny;
    
    public void ustawbilanslikwidacja() {
        tojestbilanslikwidacyjny = true;
        PrimeFaces.current().ajax().update("formdialog_dialog_bilans_usun");
    }
    
    public void zestawbilanslikwidacja() {
        tojestbilanslikwidacyjny = false;
        PrimeFaces.current().ajax().update("formdialog_dialog_bilans_usun");
    }
    
    public void usunBO(WpisView wpisView) {
        try {
            Dokfk dokbo = dokDAOfk.findDokfkLastofaTypeMc(wpisView.getPodatnikObiekt(), "BO", wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            if (dokbo != null) {
                dokDAOfk.remove(dokbo);
            }
            wierszBODAO.deletePodatnikRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
            FacesContext context = FacesContext.getCurrentInstance();
            BilansWprowadzanieView bean = context.getApplication().evaluateExpressionGet(context, "#{bilansWprowadzanieView}", BilansWprowadzanieView.class);
            bean.init();
            Msg.msg("Usunięto bilans otwarcia/obroty rozpoczęcia");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("Wystąpił błąd, nie usunięto bilansu otwarcia/obrotów rozpoczęcia");
        }
    }
    
    

    public boolean isTojestbilanslikwidacyjny() {
        return tojestbilanslikwidacyjny;
    }

    public void setTojestbilanslikwidacyjny(boolean tojestbilanslikwidacyjny) {
        this.tojestbilanslikwidacyjny = tojestbilanslikwidacyjny;
    }
    
    
}
