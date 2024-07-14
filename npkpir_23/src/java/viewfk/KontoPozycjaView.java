/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package viewfk;

import beansFK.UkladBRBean;
import dao.KontopozycjaZapisDAO;
import dao.UkladBRDAO;
import entityfk.KontopozycjaZapis;
import entityfk.UkladBR;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.interceptor.Interceptors;
import view.WpisView;
import interceptor.ConstructorInterceptor;

/**
 *
 * @author Osito
 */
@Named @Interceptors(ConstructorInterceptor.class)
@ViewScoped
public class KontoPozycjaView implements Serializable {

    private static final long serialVersionUID = 1L;
    @Inject
    private KontopozycjaZapisDAO kontopozycjaZapisDAO;
    @Inject
    private UkladBRDAO ukladBRDAO;
    @Inject
    private WpisView wpisView;
    private UkladBR wybranyuklad;
    private List<UkladBR> listaukladow;
    private List<KontopozycjaZapis> listaKontopozycjaZapis;
    
    
    @PostConstruct
    public void init() { //E.m(this);
        listaukladow = ukladBRDAO.findPodatnikRok(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt());
        wybranyuklad = UkladBRBean.pobierzukladaktywny(ukladBRDAO, listaukladow);
        listaKontopozycjaZapis = kontopozycjaZapisDAO.findByUklad(wybranyuklad);
    }

    public List<KontopozycjaZapis> getListaKontopozycjaZapis() {
        return listaKontopozycjaZapis;
    }

    public void setListaKontopozycjaZapis(List<KontopozycjaZapis> listaKontopozycjaZapis) {
        this.listaKontopozycjaZapis = listaKontopozycjaZapis;
    }

    public UkladBR getWybranyuklad() {
        return wybranyuklad;
    }

    public void setWybranyuklad(UkladBR wybranyuklad) {
        this.wybranyuklad = wybranyuklad;
    }
    
    
}
