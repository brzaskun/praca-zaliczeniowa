/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import embeddable.DokKsiega;
import entity.Dok;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;

/**
 *
 * @author Osito
 */
@ManagedBean
public class KsiegaView {
    @ManagedProperty(value = "#{DokTabView}")
    private DokTabView dokTabView;
    private ArrayList<DokKsiega> lista;
    private DokKsiega selDokument;

    public KsiegaView() {
        lista = new ArrayList<>();
    }
    
    @PostConstruct
    private void init(){
        List<Dok> tmplist = dokTabView.getObiektDOKmrjsfSel();
        Iterator it;
        it = tmplist.iterator();
        while (it.hasNext()){
            Dok tmp = (Dok) it.next();
            DokKsiega dk = new DokKsiega();
            dk.setIdDok(tmp.getIdDok());
            dk.setTypdokumentu(tmp.getTypdokumentu());
            dk.setNrWpkpir(tmp.getNrWpkpir());
            dk.setNrWlDk(tmp.getNrWlDk());
            dk.setKontr(tmp.getKontr());
            dk.setPodatnik(tmp.getPodatnik());
            dk.setDataWyst(tmp.getDataWyst());
            dk.setOpis(tmp.getOpis());
            switch(tmp.getPkpirKol()){
                case "przych. sprz":
                    dk.setKolumna7(tmp.getKwota());
                    break;
                case "pozost. przych.":
                    dk.setKolumna8(tmp.getKwota());
                    break;
                case "zakup tow. i mat.":
                    dk.setKolumna10(tmp.getKwota());
                    break;
                case "koszty ub.zak.":
                    dk.setKolumna11(tmp.getKwota());
                    break;
                case "wynagrodzenia":
                    dk.setKolumna12(tmp.getKwota());
                    break;
                case "poz. koszty":
                    dk.setKolumna13(tmp.getKwota());
                    break;
                 case "inwestycje":
                     dk.setKolumna15(tmp.getKwota());
                    break;   
            }
            if(tmp.getPkpirKolX()!=null){
            switch(tmp.getPkpirKolX()){
                case "przych. sprz":
                    dk.setKolumna7(tmp.getKwotaX());
                    break;
                case "pozost. przych.":
                    dk.setKolumna8(tmp.getKwotaX());
                    break;
                case "zakup tow. i mat.":
                    dk.setKolumna10(tmp.getKwotaX());
                    break;
                case "koszty ub.zak.":
                    dk.setKolumna11(tmp.getKwotaX());
                    break;
                case "wynagrodzenia":
                    dk.setKolumna12(tmp.getKwotaX());
                    break;
                case "poz. koszty":
                    dk.setKolumna13(tmp.getKwotaX());
                    break;
                 case "inwestycje":
                     dk.setKolumna15(tmp.getKwotaX());
                    break;   
            }}
            if(dk.getKolumna7()!=null&&dk.getKolumna8()!=null) {
                dk.setKolumna9(dk.getKolumna7()+dk.getKolumna8());
            } else if (dk.getKolumna7()!=null) {
                dk.setKolumna9(dk.getKolumna7());
            } else {
                dk.setKolumna9(dk.getKolumna8());
            }
            if (dk.getKolumna12()!=null&&dk.getKolumna13()!=null){
                dk.setKolumna14(dk.getKolumna12()+dk.getKolumna13());
            } else if (dk.getKolumna12()!=null){
                dk.setKolumna14(dk.getKolumna12());
            } else {
                dk.setKolumna14(dk.getKolumna13());
            }
            dk.setUwagi(tmp.getUwagi());
            dk.setPkpirM(tmp.getPkpirM());
            dk.setPkpirR(tmp.getPkpirR());
            dk.setVatM(tmp.getVatM());
            dk.setVatR(tmp.getVatR());
            dk.setStatus(tmp.getStatus());
            dk.setEwidencjaVAT(tmp.getEwidencjaVAT());
            dk.setDokumentProsty(tmp.isDokumentProsty());
            lista.add(dk);
         }
    }

    public ArrayList<DokKsiega> getLista() {
        return lista;
    }

    public void setLista(ArrayList<DokKsiega> lista) {
        this.lista = lista;
    }

    public DokKsiega getSelDokument() {
        return selDokument;
    }

    public void setSelDokument(DokKsiega selDokument) {
        this.selDokument = selDokument;
    }

    public DokTabView getDokTabView() {
        return dokTabView;
    }

    public void setDokTabView(DokTabView dokTabView) {
        this.dokTabView = dokTabView;
    }
    
    
}
