/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jpkabstract;

import javax.xml.datatype.XMLGregorianCalendar;

/**
 *
 * @author Osito
 */
public abstract class ZakupWierszA {
    public abstract String getNrDostawcy();
    public abstract String getNazwaDostawcyShort();
    public abstract String getDowodZakupu();
    public abstract XMLGregorianCalendar getDataWplywu();
    public abstract XMLGregorianCalendar getDataZakupu();
    public abstract double getNetto();
    public abstract double getVat();
    public abstract String getNettoPole();
    public abstract String getVatPole();
}
