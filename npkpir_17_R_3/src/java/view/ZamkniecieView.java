/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import embeddable.Mce;
import embeddable.Okresrozliczeniowy;
import embeddable.Roki;
import entity.Zamknietemiesiace;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class ZamkniecieView implements Serializable{
    private static List<Integer> lata;
    private static List<String> miesiace;
    private String podatnik;
    @Inject private Zamknietemiesiace zamknietemiesiace;
    @Inject private Roki roki;
    @Inject private Mce mce;
    private Map<Integer,Okresrozliczeniowy> mapaokresow;

    public ZamkniecieView() {
        lata = new ArrayList<>();
        miesiace = new ArrayList<>();
        mapaokresow = new HashMap<>();
    }
    
    @PostConstruct
    private void init(){
        lata.addAll(roki.getRokiList());
        miesiace.addAll(mce.getMceList());
        Iterator it;
        it = lata.iterator();
        Integer i=0;
        while(it.hasNext()){
            Integer tmp = (Integer) it.next();
            Iterator itx;
            itx = miesiace.iterator();
            while(itx.hasNext()){
                String tmpx = (String) itx.next();
                Okresrozliczeniowy okres = new Okresrozliczeniowy();
                okres.setRok(tmp.toString());
                okres.setMiesiac(tmpx);
                mapaokresow.put(i, okres);
                i++;
            }
        }
                
    }
    
    public static void main(String[] args){
        Roki roki = new Roki();
        Mce mce = new Mce();
        Map<Integer,Okresrozliczeniowy> mapaokresow = new HashMap<>();
        List lata = new ArrayList<>();
        List miesiace = new ArrayList<>();
        lata.addAll(roki.getRokiList());
        miesiace.addAll(mce.getMceList());
        Iterator it;
        it = lata.iterator();
        Integer i=0;
        while(it.hasNext()){
            Integer tmp = (Integer) it.next();
            Iterator itx;
            itx = miesiace.iterator();
            while(itx.hasNext()){
                String tmpx = (String) itx.next();
                Okresrozliczeniowy okres = new Okresrozliczeniowy();
                okres.setRok(tmp.toString());
                okres.setMiesiac(tmpx);
                mapaokresow.put(i, okres);
                i++;
            }
        }
    }
    
}
