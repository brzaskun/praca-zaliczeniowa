/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import entityfk.Konto;
import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class PrzyporzadkowanieKontView implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Konto> kontaAktywne;
    private List<Konto> kontaPasywne;
    
    
}
