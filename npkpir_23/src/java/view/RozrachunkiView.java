/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.Rozrachunki;
import dao.DokDAO;
import dao.StornoDokDAO;
import entity.Dok;
import entity.Rozrachunek1;
import entity.StornoDok;
import error.E;
import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import msg.Msg; import org.primefaces.PrimeFaces;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class RozrachunkiView implements Serializable {

    //edycja platnosci
    @Inject
    private Rozrachunek1 rozrachunek;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @ManagedProperty(value = "#{DokTabView}")
    private DokTabView dokTabView;
    @Inject
    private StornoDokDAO stornoDokDAO;
    @Inject
    private StornoDok stornoDok;
    @Inject
    private DokDAO dokDAO;
    @Inject
    private Dok selDokument;
    //dokumenty niezaplacone
    private List<Dok> niezaplacone;
    //dokumenty zaplacone
    private List<Dok> zaplacone;

    public RozrachunkiView() {
        niezaplacone = Collections.synchronizedList(new ArrayList<>());
        //dokumenty zaplacone
        zaplacone = Collections.synchronizedList(new ArrayList<>());
    }

    @PostConstruct
    private void init() {
        int numerkolejny = 1;
        if (wpisView.getPodatnikObiekt().getNumerpkpir() != null) {
            try {
                //zmienia numer gdy srodek roku
                int index = wpisView.getPodatnikObiekt().getNumerpkpir().size() - 1;
                String wartosc = wpisView.getPodatnikObiekt().getNumerpkpir().get(index).getParametr();
                numerkolejny = Integer.parseInt(wartosc);
            } catch (Exception e) { E.e(e); 
            }
        }
        List<Dok> dokumenty = dokTabView.getDokumentylista();
        for (Dok tmpx : dokumenty) {
            tmpx.setNrWpkpir(numerkolejny++);
            if (tmpx.getRozliczony() == false) {
                niezaplacone.add(tmpx);
            } else {
                //pobiera tylko przelewowe
                if (tmpx.getRozrachunki1() != null) {
                    zaplacone.add(tmpx);
                }
            }
        }
    }

    public void zaksiegujPlatnosc(ActionEvent e) {
        //pobieranie daty zeby zobaczyc czy nie ma juz dokumentu storno z ta data 
        String data = rozrachunek.getDataplatnosci();
        Integer r = Integer.parseInt(data.substring(0, 4));
        String m = data.substring(5, 7);
        String podatnik = wpisView.getPodatnikWpisu();
        try {
            stornoDok = stornoDokDAO.find(r, m, podatnik);
            FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Istnieje dokument storno. Za późno wprowadzasz te płatność", stornoDok.getMc());
            FacesContext.getCurrentInstance().addMessage(null, msg);
            PrimeFaces.current().ajax().update("form:messages");
        } catch (Exception ec) {
            List<Rozrachunek1> lista = Collections.synchronizedList(new ArrayList<>());
            double zostalo = 0;
            double kwota = 0;
            try {
                lista.addAll(selDokument.getRozrachunki1());
                zostalo = lista.get(lista.size() - 1).getDorozliczenia();
            } catch (Exception ee) {
            }
            if (zostalo == 0) {
                try {
                    kwota = -selDokument.getBrutto();
                } catch (Exception el) {
                }
            } else {
                kwota = zostalo;
            }
            int pozostalo = (int) (kwota + rozrachunek.getKwotawplacona());
            rozrachunek.setDorozliczenia(kwota + rozrachunek.getKwotawplacona());
            HttpServletRequest request;
            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
            Principal principal = request.getUserPrincipal();
            rozrachunek.setWprowadzil(principal.getName());
            rozrachunek.setDatawprowadzenia(new Date());
            rozrachunek.setDok(selDokument);
            lista.add(rozrachunek);
            if (pozostalo == 0) {
                selDokument.setRozliczony(true);
            }
            selDokument.setRozrachunki1(lista);
            try {
                dokDAO.edit(selDokument);
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Płatność zachowana" + selDokument, null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception ex) {
                FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Płatność niezachowana " + ex.getStackTrace().toString(), null);
                FacesContext.getCurrentInstance().addMessage(null, msg);
            }
        }
    }

    public void usunostatniRozrachunek1(ActionEvent e) {
        List<Rozrachunek1> lista = Collections.synchronizedList(new ArrayList<>());
        try {
            lista.addAll(selDokument.getRozrachunki1());
        } catch (Exception ee) {
        }
        lista.remove(lista.get(lista.size() - 1));
        selDokument.setRozrachunki1(lista);
        dokDAO.edit(selDokument);
    }

    public void usunzzaplaconych(RowEditEvent ex) {
        Dok tmp = (Dok) ex.getObject();
        Msg.msg("i", "Probuje zmienić rozliczenia: " + tmp.getOpis(), "form:messages");
        try {
            //jak bedzie storno to ma wyrzuci blad. trzeba usunac strono wpierw
            try {
                tmp.getStorno();
                throw new Exception();
            } catch (Exception s) {
            }
            Rozrachunki.dodajdatydlaStorno(wpisView, tmp);
            tmp.setRozliczony(false);
            tmp.setRozrachunki1(null);
            tmp.setStorno(null);
            dokDAO.edit(tmp);
            zaplacone.remove(tmp);
            niezaplacone.add(tmp);
            PrimeFaces.current().ajax().update("form:dokumentyLista");
            Msg.msg("i", "Dokument z nowymi datami zaksięgowany: " + tmp.getOpis(), "form:messages");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Nie udało się usunąć rozliczeń: " + tmp.getOpis() + "Sprawdź obecność storno.", "form:messages");
        }
    }

    public Rozrachunek1 getRozrachunek() {
        return rozrachunek;
    }

    public void setRozrachunek(Rozrachunek1 rozrachunek) {
        this.rozrachunek = rozrachunek;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public Dok getSelDokument() {
        return selDokument;
    }

    public void setSelDokument(Dok selDokument) {
        this.selDokument = selDokument;
    }

    public DokTabView getDokTabView() {
        return dokTabView;
    }

    public void setDokTabView(DokTabView dokTabView) {
        this.dokTabView = dokTabView;
    }

    public List<Dok> getNiezaplacone() {
        return niezaplacone;
    }

    public void setNiezaplacone(List<Dok> niezaplacone) {
        this.niezaplacone = niezaplacone;
    }

    public List<Dok> getZaplacone() {
        return zaplacone;
    }

    public void setZaplacone(List<Dok> zaplacone) {
        this.zaplacone = zaplacone;
    }

}
