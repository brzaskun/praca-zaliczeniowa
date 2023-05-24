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
import entity.Kalendarzmiesiac;
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
import msg.Msg;

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
            Msg.msg("Pobrano paski");
        }
        Collections.sort(paski, new Pasekwynagrodzencomparator());
        List<Okres> okresylista = OkresBean.pobierzokresy(dataod, datado);
        Map<Okres, Z3dane> okrespaski = new HashMap<>();
        if (paski!=null) {
            for (Okres ok : okresylista) {
                okrespaski.put(ok, new Z3dane(ok.getRok(), ok.getMc()));
            }
            Z3dane suma = new Z3dane(null, null);
            for (Okres ok : okresylista) {
                Z3dane wybranyokres = okrespaski.get(ok);
                boolean uxzupelniac = false;
                for (Pasekwynagrodzen pasek :paski) {
                    if (pasek.getDefinicjalistaplac().getRodzajlistyplac().getSymbol().equals("WZ")) {
                        if (przesuniecie==true&&pasek.getOkresWypl().equals(ok.getRokmc())) {
                            Kalendarzmiesiac kalendarzmiesiac = pasek.getKalendarzmiesiac();
                            double[] czyuzupelnicskladnik = kalendarzmiesiac.uzupelnienie1norma0pominiecie2();
                            wybranyokres.setGodzinyobowiazku(czyuzupelnicskladnik[0]);
                            wybranyokres.setGodzinyprzepracowane(czyuzupelnicskladnik[1]);
                            wybranyokres.setDniobowiazku(czyuzupelnicskladnik[3]);
                            wybranyokres.setDniprzepracowane(czyuzupelnicskladnik[4]);
                            uxzupelniac = czyuzupelnicskladnik[2] == 1;
                            wybranyokres.setUzupelniane(uxzupelniac);
                            wybranyokres.setStale(pasek.getStale(uxzupelniac));
                            wybranyokres.setZmienne(pasek.getZmienne());
                            wybranyokres.setPremie(pasek.getPremie(uxzupelniac));
                            wybranyokres.setUzupelniane(uxzupelniac);
                            suma.sumuj(pasek.getStale(uxzupelniac), pasek.getZmienne(), pasek.getPremie(uxzupelniac));
                        } else if (przesuniecie==false&&pasek.getOkresNalezny().equals(ok.getRokmc())) {
                            Kalendarzmiesiac kalendarzmiesiac = pasek.getKalendarzmiesiac();
                            double[] czyuzupelnicskladnik = kalendarzmiesiac.uzupelnienie1norma0pominiecie2();
                            wybranyokres.setGodzinyobowiazku(czyuzupelnicskladnik[0]);
                            wybranyokres.setGodzinyprzepracowane(czyuzupelnicskladnik[1]);
                            wybranyokres.setDniobowiazku(czyuzupelnicskladnik[3]);
                            wybranyokres.setDniprzepracowane(czyuzupelnicskladnik[4]);
                            uxzupelniac = czyuzupelnicskladnik[2] == 1;
                            wybranyokres.setStale(pasek.getStale(uxzupelniac));
                            wybranyokres.setZmienne(pasek.getZmienne());
                            wybranyokres.setPremie(pasek.getPremie(uxzupelniac));
                            wybranyokres.setUzupelniane(uxzupelniac);
                            suma.sumuj(pasek.getStale(uxzupelniac), pasek.getZmienne(), pasek.getPremie(uxzupelniac));
                        }
                    } else if (pasek.getDefinicjalistaplac().getRodzajlistyplac().getSymbol().equals("UZ")) {
                        if (przesuniecie==true&&pasek.getOkresWypl().equals(ok.getRokmc())) {
                            Kalendarzmiesiac kalendarzmiesiac = pasek.getKalendarzmiesiac();
                            double[] czyuzupelnicskladnik = kalendarzmiesiac.uzupelnienie1norma0pominiecie2();
                            wybranyokres.setGodzinyobowiazku(czyuzupelnicskladnik[0]);
                            wybranyokres.setGodzinyprzepracowane(czyuzupelnicskladnik[1]);
                            wybranyokres.setDniobowiazku(czyuzupelnicskladnik[3]);
                            wybranyokres.setDniprzepracowane(czyuzupelnicskladnik[4]);
                            uxzupelniac = czyuzupelnicskladnik[2] == 1;
                            wybranyokres.setUzupelniane(uxzupelniac);
                            wybranyokres.setStale(pasek.getStale(uxzupelniac));
                            wybranyokres.setZmienne(pasek.getZmienne());
                            wybranyokres.setPremie(pasek.getPremie(uxzupelniac));
                            wybranyokres.setUzupelniane(uxzupelniac);
                            suma.sumuj(pasek.getStale(uxzupelniac), pasek.getZmienne(), pasek.getPremie(uxzupelniac));
                        } else if (przesuniecie==false&&pasek.getOkresNalezny().equals(ok.getRokmc())) {
                            Kalendarzmiesiac kalendarzmiesiac = pasek.getKalendarzmiesiac();
                            double[] czyuzupelnicskladnik = kalendarzmiesiac.uzupelnienie1norma0pominiecie2();
                            wybranyokres.setGodzinyobowiazku(czyuzupelnicskladnik[0]);
                            wybranyokres.setGodzinyprzepracowane(czyuzupelnicskladnik[1]);
                            wybranyokres.setDniobowiazku(czyuzupelnicskladnik[3]);
                            wybranyokres.setDniprzepracowane(czyuzupelnicskladnik[4]);
                            uxzupelniac = czyuzupelnicskladnik[2] == 1;
                            wybranyokres.setStale(pasek.getStale(uxzupelniac));
                            wybranyokres.setZmienne(pasek.getZmienne());
                            wybranyokres.setPremie(pasek.getPremie(uxzupelniac));
                            wybranyokres.setUzupelniane(uxzupelniac);
                            suma.sumuj(pasek.getStale(uxzupelniac), pasek.getZmienne(), pasek.getPremie(uxzupelniac));
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
