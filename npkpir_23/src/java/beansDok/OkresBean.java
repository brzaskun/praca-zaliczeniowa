/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beansDok;

import data.Data;
import embeddable.Mce;
import embeddable.Okres;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class OkresBean implements Serializable{
    private static final long serialVersionUID = 1L;
    private static final List<Okres> okresylista;
    
    
    static {
        okresylista = generujokresy();
    }

    public OkresBean() {
    }
    
    
    
    static List<Okres> generujokresy() {
        List<Okres> zwrot = new ArrayList<>();
        Integer rokod = 2014;
        Integer rokdo = Integer.parseInt(data.Data.aktualnyRok())+1;
        Integer mcod = 1;
        Integer mcdo = 12;
        int i = 1;
        for (int rok = rokod;rok<=rokdo;rok++) {
            for (int mc = mcod;mc<=mcdo;mc++) {
                Okres nowy = new Okres(String.valueOf(rok),Mce.getNumberToMiesiac().get(mc), i);
                zwrot.add(nowy);
            }
        }
        return zwrot;
    }
    

//    static List<Okres> generujokresy(Nieobecnosc nieobecnosc) {
//        List<Okres> zwrot = new ArrayList<>();
//        Integer rokod = Integer.parseInt(nieobecnosc.getRokod());
//        Integer rokdo = Integer.parseInt(nieobecnosc.getRokdo());
//        Integer mcod = Integer.parseInt(nieobecnosc.getMcod());
//        Integer mcdo = Integer.parseInt(nieobecnosc.getMcdo());
//        for (int rok = rokod;rok<=rokdo;rok++) {
//            for (int mc = mcod;mc<=mcdo;mc++) {
//                Okres nowy = new Okres(String.valueOf(rok),Mce.getNumberToMiesiac().get(mc));
//                zwrot.add(nowy);
//            }
//        }
//        return zwrot;
//    }

    public static List<Okres> pobierzokresy(String dataod, String datado) {
        Set<Okres> zwrot = new HashSet<>();
        String rokod = Data.getRok(dataod);
        String mcod = Data.getMc(dataod);
        String rokdo = Data.getRok(datado);
        String mcdo = Data.getMc(datado);
        boolean start = false;
        boolean stop = false;
        for (Okres o : okresylista) {
            if (stop==false) {
                if (rokod.equals(o.getRok())&&mcod.equals(o.getMc())) {
                    zwrot.add(o);
                    start = true;
                }
                if (start) {
                    zwrot.add(o);
                }
                if (rokdo.equals(o.getRok())&&mcdo.equals(o.getMc())) {
                    zwrot.add(o);
                    stop = true;
                }
            } else {
                break;
            }
            
        }
        return new ArrayList<>(zwrot);
    }

    public List<Okres> getOkresylista() {
        return okresylista;
    }
    
    
}
