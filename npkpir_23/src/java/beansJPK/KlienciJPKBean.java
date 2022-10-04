/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansJPK;

import embeddablefk.ImportJPKSprzedaz;
import embeddablefk.InterpaperXLS;
import entity.Dok;
import entity.EVatwpisKJPK;
import entity.Evewidencja;
import entity.KlientJPK;
import entity.Podatnik;
import entityfk.Dokfk;
import entityfk.EVatwpisFK;
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
            if (a.getOpissprzedaz()==null||a.getOpissprzedaz().equals("")) {
                a.setOpissprzedaz("FP");
            }
            a.setEwidencjaVAT(tworzewidencjeVAT(a, d));
            zwrot.add(a);
        }
        return zwrot;
    }
  
    
    public static List<KlientJPK> zaksiegujdokJPKJPK(List<ImportJPKSprzedaz> lista, Podatnik podatnik, String rok, String mc, List<Evewidencja> ewidencjalista) {
        List<KlientJPK> zwrot = new ArrayList<>();
//        for (ImportJPKSprzedaz d : lista) {
//            KlientJPK a = new KlientJPK(d, podatnik, rok, mc);
//            Evewidencja ewidencja = pobierzewidencje(ewidencjalista,a);
//            a.setEwidencjaVAT(tworzewidencjeVAT(a, d));
//            zwrot.add(a);
//        }
        return zwrot;
    }
    
    
    public static List<KlientJPK> zaksiegujdokJPKJPKFA(List<Dokfk> lista, Podatnik podatnik, String rok, String mc) {
        List<KlientJPK> zwrot = new ArrayList<>();
        for (Dokfk d : lista) {
            KlientJPK a = new KlientJPK(d, podatnik, rok, mc);
            a.setEwidencjaVAT(tworzewidencjeVAT(a, d));
            zwrot.add(a);
        }
        return zwrot;
    }
    
    private static Evewidencja pobierzewidencje(List<Evewidencja> ewidencjalista, KlientJPK a) {
        for (Evewidencja r : ewidencjalista) {
            if (a.getStawkavat() == 0.23) {
                if (r.getNazwa().equals("sprzedaż 23%")) {
                    return r;
                }
            }
            if (a.getStawkavat() == 0.08) {
                if (r.getNazwa().equals("sprzedaż 8%")) {
                    return r;
                }
            }
        }
        return null;
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
    
    private static List<EVatwpisKJPK> tworzewidencjeVAT(KlientJPK tmpzwrot, Dokfk dok) {
        List<EVatwpisKJPK> zwrot = new ArrayList<>();
        for (EVatwpisFK d : dok.getEwidencjaVAT()) {
            EVatwpisKJPK nowa = new EVatwpisKJPK();
            nowa.setKlientJPK(tmpzwrot);
            nowa.setEwidencja(d.getEwidencja());
            nowa.setRokEw(tmpzwrot.getRok());
            nowa.setMcEw(tmpzwrot.getMc());
            nowa.setNetto(tmpzwrot.getNetto());
            nowa.setVat(tmpzwrot.getVat());
            nowa.setEstawka(String.valueOf(tmpzwrot.getStawkavat()));
            nowa.setTylkodlajpk(true);
            zwrot.add(nowa);
        }
        return zwrot;
    }

    



    
}
