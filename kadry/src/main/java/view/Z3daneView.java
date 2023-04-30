/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.OkresBean;
import comparator.Pasekwynagrodzencomparator;
import comparator.Z3danecomparator;
import dao.PasekwynagrodzenFacade;
import data.Data;
import embeddable.Okres;
import embeddable.Z3dane;
import entity.FirmaKadry;
import entity.Pasekwynagrodzen;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class Z3daneView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    private String dataod;
    private String datado;
    @Inject
    private PasekwynagrodzenFacade pasekwynagrodzenFacade;
    private List<Z3dane> lista;
    private boolean dialogOtwarty;
    
    public void open() {
        dialogOtwarty = true;
    }
    public void close() {
        dialogOtwarty = false;
    }
    
    public void reloadDialog() {
        boolean zwrot = false;
        if (dialogOtwarty) {
            init();
        }
    }

    
    public void init() {
        String[] poprzedniokres = Data.poprzedniOkres(Data.aktualnaData());
         FirmaKadry firma = wpisView.getFirma();
         boolean przesuniecie = firma.getDzienlp()!=null;
        dataod = Data.pierwszyDzien(poprzedniokres[1], poprzedniokres[0]);
        datado = Data.ostatniDzien(poprzedniokres[1], poprzedniokres[0]);
        dataod = Data.dodajmiesiac(datado, -13);
        String rokuprzedni = Data.getRok(dataod);
        List<Pasekwynagrodzen> paskirokuprzedni = pasekwynagrodzenFacade.findByRokWyplAngaz(rokuprzedni, wpisView.getAngaz());
        List<Pasekwynagrodzen> paskirokbierzacy = pasekwynagrodzenFacade.findByRokWyplAngaz(Data.getRok(datado), wpisView.getAngaz());
        List<Pasekwynagrodzen> paski = new ArrayList<>();
        if (paskirokuprzedni!=null) {
            paski.addAll(paskirokuprzedni);
        }
        if (paskirokbierzacy!=null) {
            paski.addAll(paskirokbierzacy);
        }
        Collections.sort(paski, new Pasekwynagrodzencomparator());
        List<Okres> okresylista = OkresBean.pobierzokresy(dataod, datado);
        Map<Okres, Z3dane> okrespaski = new HashMap<>();
        if (paski!=null) {
            for (Okres ok : okresylista) {
                okrespaski.put(ok, new Z3dane(ok.getRok(), ok.getMc()));
            }
            double bruttosuma = 0.0;
            double nettosuma = 0.0;
            Z3dane suma = new Z3dane(null, null);
            for (Okres ok : okresylista) {
                Z3dane wybranyokres = okrespaski.get(ok);
                for (Pasekwynagrodzen pasek :paski) {
                    if (pasek.getDefinicjalistaplac().getRodzajlistyplac().getSymbol().equals("WZ")) {
                        if (przesuniecie==true&&pasek.getOkresWypl().equals(ok.getRokmc())) {
                            wybranyokres.setStale(pasek.getStale());
                            wybranyokres.setZmienne(pasek.getZmienne());
                            wybranyokres.setPremie(pasek.getPremie());
                            wybranyokres.setGodzinyobowiazku(pasek.getKalendarzmiesiac().getNorma());
                            wybranyokres.setGodzinyprzepracowane(pasek.getKalendarzmiesiac().getPrzepracowane());
                            wybranyokres.setDniobowiazku(pasek.getKalendarzmiesiac().getDniroboczenominalnewmiesiacu());
                            wybranyokres.setDniprzepracowane(pasek.getKalendarzmiesiac().getDnipracywmiesiacu());
                            suma.sumuj(pasek.getStale(), pasek.getZmienne(), pasek.getPremie());
                        } else if (przesuniecie==false&&pasek.getOkresNalezny().equals(ok.getRokmc())) {
                            wybranyokres.setStale(pasek.getStale());
                            wybranyokres.setZmienne(pasek.getZmienne());
                            wybranyokres.setPremie(pasek.getPremie());
                            wybranyokres.setGodzinyobowiazku(pasek.getKalendarzmiesiac().getNorma());
                            wybranyokres.setGodzinyprzepracowane(pasek.getKalendarzmiesiac().getPrzepracowane());
                            wybranyokres.setDniobowiazku(pasek.getKalendarzmiesiac().getDniroboczenominalnewmiesiacu());
                            wybranyokres.setDniprzepracowane(pasek.getKalendarzmiesiac().getDnipracywmiesiacu());
                            suma.sumuj(pasek.getStale(), pasek.getZmienne(), pasek.getPremie());
                        }
                    }
                }
            }
            lista = new ArrayList<>(okrespaski.values());
            Collections.sort(lista, new Z3danecomparator());
            lista.add(suma);
        }
    }

    public List<Z3dane> getLista() {
        return lista;
    }

    public void setLista(List<Z3dane> lista) {
        this.lista = lista;
    }

    public String getDataod() {
        return dataod;
    }

    public void setDataod(String dataod) {
        this.dataod = dataod;
    }

    public String getDatado() {
        return datado;
    }

    public void setDatado(String datado) {
        this.datado = datado;
    }

    
    
    
}
