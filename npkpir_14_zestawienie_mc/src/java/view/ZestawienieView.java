/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.DokDAO;
import embeddable.Kolmn;
import embeddable.Mce;
import entity.Dok;
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
@ManagedBean(name="ZestawienieView")
@RequestScoped
public class ZestawienieView {
    @Inject
    private DokTabView dokTabView;
    Map<String,Double> kolumny;
    Map<String,Map<String,Double>> zestawienie;
    
    private List<Dok> lista;
    
    private List<Miesiac> listaImiesiace;
    private List<Wiersz> listaIIpkpir;
    
    class Miesiac {
        private String nazwamiesiaca;
        private Wiersz wiersz;

        public String getNazwamiesiaca() {
            return nazwamiesiaca;
        }
        public void setNazwamiesiaca(String nazwamiesiaca) {
            this.nazwamiesiaca = nazwamiesiaca;
        }
        public Wiersz getWiersz() {
            return wiersz;
        }
        public void setWiersz(Wiersz wiersz) {
            this.wiersz = wiersz;
        }
    }
    
    class Wiersz {
        private String nazwakolumny;
        private Double sumwakwot;

        public String getNazwakolumny() {
            return nazwakolumny;
        }
        public void setNazwakolumny(String nazwakolumny) {
            this.nazwakolumny = nazwakolumny;
        }
        public Double getSumwakwot() {
            return sumwakwot;
        }
        public void setSumwakwot(Double sumwakwot) {
            this.sumwakwot = sumwakwot;
        }
    }
    
    
    @PostConstruct
    public void init(){
        kolumny = new HashMap<>();
        zestawienie = new HashMap<>();
        lista = new ArrayList<>();
        lista.addAll(dokTabView.getObiektDOKjsfSelRok());
        Iterator it;
        it = lista.iterator();
        while(it.hasNext()){
            Dok tmp = (Dok) it.next();
                kolumny.put(tmp.getPkpirKol(), tmp.getKwota());
                zestawienie.put(tmp.getPkpirM(),kolumny);
            if(tmp.getKwotaX()!=null){
                kolumny.put(tmp.getPkpirKolX(), tmp.getKwotaX());
                zestawienie.put(tmp.getPkpirM(),kolumny);
            }
        }
    }
    
    

    public DokTabView getDokTabView() {
        return dokTabView;
    }

    public void setDokTabView(DokTabView dokTabView) {
        this.dokTabView = dokTabView;
    }

    public List<Dok> getLista() {
        return lista;
    }

    public void setLista(List<Dok> lista) {
        this.lista = lista;
    }

    public Map<String, Double> getKolumny() {
        return kolumny;
    }

    public void setKolumny(Map<String, Double> kolumny) {
        this.kolumny = kolumny;
    }

    public Map<String, Map<String, Double>> getZestawienie() {
        return zestawienie;
    }

    public void setZestawienie(Map<String, Map<String, Double>> zestawienie) {
        this.zestawienie = zestawienie;
    }

    private List<Miesiac> getListaImiesiace() {
        return listaImiesiace;
    }

    private void setListaImiesiace(List<Miesiac> listaImiesiace) {
        this.listaImiesiace = listaImiesiace;
    }

    private List<Wiersz> getListaIIpkpir() {
        return listaIIpkpir;
    }

    private void setListaIIpkpir(List<Wiersz> listaIIpkpir) {
        this.listaIIpkpir = listaIIpkpir;
    }

    
}
