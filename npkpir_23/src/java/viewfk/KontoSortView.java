/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import entityfk.Konto;
import error.E;
import java.io.Serializable;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import sortfunction.KontoSortBean;
import wydajnosc.ConstructorInterceptor;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class KontoSortView  implements Serializable {
    private static final long serialVersionUID = 1L;

    
    public KontoSortView() {
         ////E.m(this);
    }
    
         
    public int sortujKonto(Object o1, Object o2) {
         try {
            return KontoSortBean.sortZaksiegowaneDok((Konto) o1, (Konto) o2);
         } catch (Exception e) {  E.e(e);
             return 0;
         }
     }
    
}
