/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class Waluty implements Serializable {
     private static final long serialVersionUID = 1L;
     
     private List<String> listawalut;
    
     @PostConstruct
     private void init() { 
         listawalut = new ArrayList<>();
         listawalut.add("PLN");
         listawalut.add("EUR");
     }

    public List<String> getListawalut() {
        return listawalut;
    }
     
     
}
