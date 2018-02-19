/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansFK;

import entityfk.Konto;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import view.WpisView;

/**
 *
 * @author Osito
 */

public class PlanKontTablicaBean {

    public static List<Konto> generujTablicePlanKont(Map<Integer, List<Konto>> leveleKonta, WpisView wpisView) {
        List<Konto> plankont = new ArrayList<>();
        int maxlevel = leveleKonta.size();
        for (int i = 0 ; i <= maxlevel ; i++) {
            List<Konto> level = leveleKonta.get(i);
            for (Konto p : level) {
                p.getAllChildren(plankont, wpisView, null);
            }
        }
        return plankont;
    }
    
}
