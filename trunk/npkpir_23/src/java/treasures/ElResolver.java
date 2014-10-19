/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package treasures;

import javax.faces.context.FacesContext;
import view.DokView;

/**
 *
 * @author Osito
 */
public class ElResolver {

    public ElResolver() {
        FacesContext context = FacesContext.getCurrentInstance();
        DokView dokView = (DokView) context.getELContext().getELResolver().getValue(context.getELContext(), null,"dokumentView");   
    }
    
        
}
