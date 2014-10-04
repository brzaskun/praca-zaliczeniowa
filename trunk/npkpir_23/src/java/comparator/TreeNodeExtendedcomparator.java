/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package comparator;


import embeddablefk.TreeNodeExtended;
import entityfk.PozycjaRZiSBilans;
import java.util.Comparator;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class TreeNodeExtendedcomparator implements Comparator<TreeNodeExtended> {

    @Override
    public int compare(TreeNodeExtended o1, TreeNodeExtended o2) {
        Character pozycjaSymbol1 = ((PozycjaRZiSBilans) o1.getData()).getPozycjaSymbol().charAt(0);
        Character pozycjaSymbol2 = ((PozycjaRZiSBilans) o2.getData()).getPozycjaSymbol().charAt(0);
        return pozycjaSymbol1 > pozycjaSymbol2 ? 1 : (pozycjaSymbol1 == pozycjaSymbol2 ? 0 : -1);
    }
    
}   

