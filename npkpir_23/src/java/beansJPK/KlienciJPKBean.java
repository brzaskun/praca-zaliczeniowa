/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansJPK;

import entity.Dok;
import entity.KlientJPK;
import entity.Podatnik;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
public class KlienciJPKBean {
    
    

    public static List<KlientJPK> zaksiegujdok(List<Dok> polskaprywatne, Podatnik podatnik, String rok, String mc) {
        List<KlientJPK> zwrot = new ArrayList<>();
        for (Dok d : polskaprywatne) {
            KlientJPK a = new KlientJPK(d, podatnik, rok, mc);
            zwrot.add(a);
        }
        return zwrot;
    }
    
}
