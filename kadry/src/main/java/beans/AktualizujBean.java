/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import java.io.Serializable;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.PrimeFaces;
import view.AngazView;
import view.PracownikView;

/**
 *
 * @author Osito
 */
@Named
public class AktualizujBean implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private PracownikView pracownikView;
    @Inject
    private AngazView angazView;
    
    public void aktualizuj(int depth) {
        int i = 0;
        if (i<depth){
            pracownikView.initRecznie();
            PrimeFaces.current().ajax().update("PracownikCreateForm");
            i++;
        }
        if (i<depth){
            angazView.initRecznie();
            PrimeFaces.current().ajax().update("AngazCreateForm");
            PrimeFaces.current().ajax().update("AngazListForm");
            i++;
        }
    }
    
}
