/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansJPK;

import embeddablefk.InterpaperXLS;
import entity.Dok;
import entity.EVatwpisKJPK;
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
            a.setEwidencjaVAT(tworzewidencjeVAT(a, d));
            zwrot.add(a);
        }
        return zwrot;
    }

    public static List<KlientJPK> zaksiegujdokJPK(List<InterpaperXLS> polskaprywatne, Podatnik podatnik, String rok, String mc) {
        List<KlientJPK> zwrot = new ArrayList<>();
        for (InterpaperXLS d : polskaprywatne) {
            KlientJPK a = new KlientJPK(d, podatnik, rok, mc);
            a.setEwidencjaVAT(tworzewidencjeVAT(a, d));
            zwrot.add(a);
        }
        return zwrot;
    }
    
    private static List<EVatwpisKJPK> tworzewidencjeVAT(KlientJPK tmpzwrot, InterpaperXLS d) {
        List<EVatwpisKJPK> zwrot = new ArrayList<>();
        EVatwpisKJPK nowa = new EVatwpisKJPK();
        nowa.setKlientJPK(tmpzwrot);
        nowa.setEwidencja(d.getEvewidencja());
        nowa.setRokEw(tmpzwrot.getRok());
        nowa.setMcEw(tmpzwrot.getMc());
        nowa.setNetto(tmpzwrot.getNetto());
        nowa.setVat(tmpzwrot.getVat());
        nowa.setEstawka(String.valueOf(tmpzwrot.getStawkavat()));
        nowa.setTylkodlajpk(true);
        zwrot.add(nowa);
        return zwrot;
    }

    private static List<EVatwpisKJPK> tworzewidencjeVAT(KlientJPK tmpzwrot, Dok d) {
        List<EVatwpisKJPK> zwrot = new ArrayList<>();
        EVatwpisKJPK nowa = new EVatwpisKJPK();
        nowa.setKlientJPK(tmpzwrot);
        nowa.setEwidencja(d.getEwidencjaVAT1().get(0).getEwidencja());
        nowa.setRokEw(tmpzwrot.getRok());
        nowa.setMcEw(tmpzwrot.getMc());
        nowa.setNetto(tmpzwrot.getNetto());
        nowa.setVat(tmpzwrot.getVat());
        nowa.setEstawka(String.valueOf(tmpzwrot.getStawkavat()));
        zwrot.add(nowa);
        return zwrot;
    }
    
}
