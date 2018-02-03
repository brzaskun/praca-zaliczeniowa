/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author Osito
 */
public abstract class  JPKSuper<T>  implements Serializable{
    private static final long serialVersionUID = 1L;

    public  abstract List<T> getSprzedazWiersz();

    public abstract T getSprzedazCtrl();

    public abstract List<T> getZakupWiersz();

    public abstract T getZakupCtrl();
    
}
