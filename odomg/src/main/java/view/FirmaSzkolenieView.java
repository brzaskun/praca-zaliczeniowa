/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SzkolenieustFacade;
import entity.Szkolenieust;
import entity.Zakladpracy;
import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import org.primefaces.event.RowEditEvent;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class FirmaSzkolenieView implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private WpisView wpisView;
    @Inject
    private Szkolenieust szkolenieust;
    private Szkolenieust szkolenieustselected;
    private List<Szkolenieust> lista;
    @Inject
    private SzkolenieustFacade szkolenieustFacade;
    @Inject
    private Szkolenieust nowy;
    private Szkolenieust nowy_arch;
    
    @PostConstruct
    private void init() {
        lista = szkolenieustFacade.findAll();
    }
    
     public void dodaj() {
        if (nowy!=null&&nowy.getNazwaszkolenia()!=null) {
            try {
                szkolenieustFacade.create(nowy);
                //nowy.setUtworzony(new Date());
                nowy_arch = new Szkolenieust(nowy);
                nowy=new Szkolenieust();
                Msg.msg("Dodano nowego uczestnika");
            } catch (Exception e) {
                Msg.msg("e","Wystąpił błąd, nie dodano nowego uczestnika");
            }
        }
    }

    public void onRowEdit(RowEditEvent<Zakladpracy> event) {
        Msg.msg("Zmieniono dane firmy");
    }

    public void onRowCancel(RowEditEvent<Zakladpracy> event) {
        Msg.msg("w","Niezmieniono danych firmy");
    }

    public Szkolenieust getSzkolenieust() {
        return szkolenieust;
    }

    public void setSzkolenieust(Szkolenieust szkolenieust) {
        this.szkolenieust = szkolenieust;
    }

    public Szkolenieust getSzkolenieustselected() {
        return szkolenieustselected;
    }

    public void setSzkolenieustselected(Szkolenieust szkolenieustselected) {
        this.szkolenieustselected = szkolenieustselected;
    }

    public List<Szkolenieust> getLista() {
        return lista;
    }

    public void setLista(List<Szkolenieust> lista) {
        this.lista = lista;
    }

    public SzkolenieustFacade getSzkolenieustFacade() {
        return szkolenieustFacade;
    }

    public void setSzkolenieustFacade(SzkolenieustFacade szkolenieustFacade) {
        this.szkolenieustFacade = szkolenieustFacade;
    }

    public Szkolenieust getNowy() {
        return nowy;
    }

    public void setNowy(Szkolenieust nowy) {
        this.nowy = nowy;
    }

    public Szkolenieust getNowy_arch() {
        return nowy_arch;
    }

    public void setNowy_arch(Szkolenieust nowy_arch) {
        this.nowy_arch = nowy_arch;
    }

   
    
    
}
