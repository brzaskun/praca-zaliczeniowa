/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import embeddable.DokKsiega;
import error.E;
import interceptor.WydrukInterceptor;
import java.io.Serializable;
import java.util.ArrayList;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import pdf.PdfPkpir;

/**
 *
 * @author Osito
 */
@Named
public class Ksiega2View implements Serializable {
    
    @Interceptors(WydrukInterceptor.class)
    public void drukujPKPIR(ArrayList<DokKsiega> lista, WpisView wpisView) {
        try {
            PdfPkpir.drukujksiege(lista, wpisView, wpisView.getMiesiacWpisu());
        } catch (Exception e) { 
            E.e(e); 
        }
    }
}
