/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package abstractClasses;

import java.io.Serializable;

/**
 *
 * @author Osito
 */
public abstract class ToBeATreeNodeObject implements Serializable{
    public static final long serialVersionUID = -6459954688055353581L;
    //implementuje to bo to wlazi potem do tree i korzysta ze wspolnych rozwin i zwin
    public abstract int getMacierzysty(); 

    public abstract void setMacierzysty(int macierzysty);

    public abstract int getLevel();

    public abstract void setLevel(int level);
    
    public abstract int getLp();

    public abstract void setLp(int lp);
    
}
