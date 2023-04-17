/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import data.Data;
import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class Z3daneView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private String dataod;
    private String datado;
    
     @PostConstruct
    public void init() {
         String[] poprzedniokres = Data.poprzedniOkres(Data.aktualnaData());
        if (datado==null) {
            datado = Data.ostatniDzien(poprzedniokres[1], poprzedniokres[0]);
        }
        poprzedniokres = Data.poprzedniOkres(poprzedniokres[0], poprzedniokres[1]);
        poprzedniokres = Data.poprzedniOkres(poprzedniokres[0], poprzedniokres[1]);
        if (dataod==null) {
            dataod = Data.pierwszyDzien(poprzedniokres[1], poprzedniokres[0]);
        }
    }
    
}
