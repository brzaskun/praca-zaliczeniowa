/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package treasures;

import javax.faces.context.FacesContext;
import view.WpisView;
/**
 *
 * @author Osito
 */
public class FacesContext_zamiast_ManagedProperty {
    public double getStrNetto() {
        double zwrot = 0.0;
        try {
            FacesContext context = FacesContext.getCurrentInstance();
            WpisView bean = context.getApplication().evaluateExpressionGet(context, "#{WpisView}", WpisView.class);
        } catch (Exception ex) {
            // Logger.getLogger(SrodekTrw.class.getName()).log(Level.SEVERE, null, ex);
        }
        return zwrot;
    }
}
