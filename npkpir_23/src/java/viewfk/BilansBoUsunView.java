/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import daoFK.DokDAOfk;
import daoFK.WierszBODAO;
import entityfk.Dokfk;
import entityfk.WierszBO;
import error.E;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class BilansBoUsunView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WierszBODAO wierszBODAO;
    @Inject
    private DokDAOfk dokDAOfk;
    
    public void usunBO(WpisView wpisView) {
        try {
            Dokfk dokbo = dokDAOfk.findDokfkLastofaType(wpisView.getPodatnikObiekt(), "BO", wpisView.getRokWpisuSt());
            if (dokbo != null) {
                dokDAOfk.destroy(dokbo);
            }
            wierszBODAO.deletePodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
            Msg.msg("Usunięto bilans otwarcia");
        } catch (Exception e) {
            E.e(e);
            Msg.msg("Wystąpił błąd, nie usunięto bilansu otwarcia");
        }
    }
}
