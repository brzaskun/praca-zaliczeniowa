/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package viewfk;

import beansFK.DokFKBean;
import error.E;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class DokfkSortView implements Serializable {
    private static final long serialVersionUID = 1L;

    public DokfkSortView() {
         E.m(this);
    }
    
    

    public int sortZaksiegowaneDok(Object o1, Object o2) {
        return DokFKBean.sortZaksiegowaneDok(o1, o2);
    }
}
