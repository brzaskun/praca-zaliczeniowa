/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.SumypkpirDAO;
import embeddable.DokKsiega;
import entity.Dok;
import entity.Klienci;
import entity.Sumypkpir;
import entity.SumypkpirPK;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.inject.Inject;

/**
 *
 * @author Osito
 */
@ManagedBean
@RequestScoped
public class KsiegaView implements Serializable{
    @ManagedProperty(value = "#{DokTabView}")
    private DokTabView dokTabView;
    private static ArrayList<DokKsiega> lista;
    private DokKsiega selDokument;
    @Inject private DokKsiega podsumowanie;
    @Inject private SumypkpirDAO sumypkpirDAO;
    
    

    public KsiegaView() {
        lista = new ArrayList<>();
    }
    
    @PostConstruct
    private void init(){
        List<Dok> tmplist = dokTabView.getObiektDOKmrjsfSel();
        podsumowanie.setOpis("Podsumowanie");
        podsumowanie.setKolumna7(0.0);
        podsumowanie.setKolumna8(0.0);
        podsumowanie.setKolumna9(0.0);
        podsumowanie.setKolumna10(0.0);
        podsumowanie.setKolumna11(0.0);
        podsumowanie.setKolumna12(0.0);
        podsumowanie.setKolumna13(0.0);
        podsumowanie.setKolumna14(0.0);
        podsumowanie.setKolumna15(0.0);
        for (Dok tmp : tmplist){
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
                    podsumowanie.setKolumna7(podsumowanie.getKolumna7()+tmp.getKwota());
                    break;
                case "pozost. przych.":
                    dk.setKolumna8(tmp.getKwota());
                    podsumowanie.setKolumna8(podsumowanie.getKolumna8()+tmp.getKwota());
                    break;
                case "zakup tow. i mat.":
                    dk.setKolumna10(tmp.getKwota());
                    podsumowanie.setKolumna10(podsumowanie.getKolumna10()+tmp.getKwota());
                    break;
                case "koszty ub.zak.":
                    dk.setKolumna11(tmp.getKwota());
                    podsumowanie.setKolumna11(podsumowanie.getKolumna11()+tmp.getKwota());
                    break;
                case "wynagrodzenia":
                    dk.setKolumna12(tmp.getKwota());
                    podsumowanie.setKolumna12(podsumowanie.getKolumna12()+tmp.getKwota());
                    break;
                case "poz. koszty":
                    dk.setKolumna13(tmp.getKwota());
                    podsumowanie.setKolumna13(podsumowanie.getKolumna13()+tmp.getKwota());
                    break;
                 case "inwestycje":
                     dk.setKolumna15(tmp.getKwota());
                     podsumowanie.setKolumna15(podsumowanie.getKolumna15()+tmp.getKwota());
                    break;   
            }
            if(tmp.getPkpirKolX()!=null){
            switch(tmp.getPkpirKolX()){
                case "przych. sprz":
                    dk.setKolumna7(tmp.getKwotaX());
                    podsumowanie.setKolumna7(podsumowanie.getKolumna7()+tmp.getKwotaX());
                    break;
                case "pozost. przych.":
                    dk.setKolumna8(tmp.getKwotaX());
                    podsumowanie.setKolumna8(podsumowanie.getKolumna8()+tmp.getKwotaX());
                    break;
                case "zakup tow. i mat.":
                    dk.setKolumna10(tmp.getKwotaX());
                    podsumowanie.setKolumna10(podsumowanie.getKolumna10()+tmp.getKwotaX());
                    break;
                case "koszty ub.zak.":
                    dk.setKolumna11(tmp.getKwotaX());
                    podsumowanie.setKolumna11(podsumowanie.getKolumna11()+tmp.getKwotaX());
                    break;
                case "wynagrodzenia":
                    dk.setKolumna12(tmp.getKwotaX());
                    podsumowanie.setKolumna12(podsumowanie.getKolumna12()+tmp.getKwotaX());
                    break;
                case "poz. koszty":
                    dk.setKolumna13(tmp.getKwotaX());
                    podsumowanie.setKolumna13(podsumowanie.getKolumna13()+tmp.getKwotaX());
                    break;
                 case "inwestycje":
                     dk.setKolumna15(tmp.getKwotaX());
                     podsumowanie.setKolumna15(podsumowanie.getKolumna15()+tmp.getKwotaX());
                    break;   
            }}
            if(dk.getKolumna7()!=null&&dk.getKolumna8()!=null) {
                dk.setKolumna9(dk.getKolumna7()+dk.getKolumna8());
                podsumowanie.setKolumna9(podsumowanie.getKolumna9()+dk.getKolumna9());
            } else if (dk.getKolumna7()!=null) {
                dk.setKolumna9(dk.getKolumna7());
                podsumowanie.setKolumna9(podsumowanie.getKolumna9()+dk.getKolumna9());
            } else {
                dk.setKolumna9(dk.getKolumna8());
                try {
                podsumowanie.setKolumna9(podsumowanie.getKolumna9()+dk.getKolumna9());
                } catch (Exception e){}
            }
            if (dk.getKolumna12()!=null&&dk.getKolumna13()!=null){
                dk.setKolumna14(dk.getKolumna12()+dk.getKolumna13());
                podsumowanie.setKolumna14(podsumowanie.getKolumna14()+dk.getKolumna14());
            } else if (dk.getKolumna12()!=null){
                dk.setKolumna14(dk.getKolumna12());
                podsumowanie.setKolumna14(podsumowanie.getKolumna14()+dk.getKolumna14());
            } else {
                dk.setKolumna14(dk.getKolumna13());
                try{
                podsumowanie.setKolumna14(podsumowanie.getKolumna14()+dk.getKolumna14());
                } catch (Exception e){}
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
        podsumowanie.setIdDok(new Long(1222));
        podsumowanie.setKontr(new Klienci());
        lista.add(podsumowanie);
        Sumypkpir sumyzachowac = new Sumypkpir();
        SumypkpirPK sumyklucz = new SumypkpirPK();
        sumyklucz.setRok(dokTabView.getWpisView().getRokWpisu().toString());
        sumyklucz.setMc(dokTabView.getWpisView().getMiesiacWpisu());
        sumyklucz.setPodatnik(dokTabView.getWpisView().getPodatnikWpisu());
        sumyzachowac.setSumypkpirPK(sumyklucz);
        sumyzachowac.setSumy(podsumowanie);
        sumypkpirDAO.edit(sumyzachowac);
        podsumowaniepopmc();
    }

    private void podsumowaniepopmc(){
        if(lista.get(0).getNrWpkpir()!=1){
            System.out.println("podsumowanie");
            DokKsiega ostatni = lista.get(lista.size()-1);
            List<Sumypkpir> listasum = sumypkpirDAO.findS(dokTabView.getWpisView().getPodatnikWpisu(), dokTabView.getWpisView().getRokWpisu().toString());
            String biezacymiesiac = dokTabView.getWpisView().getMiesiacWpisu();
            DokKsiega sumaposrednia = new DokKsiega();
            sumaposrednia.setOpis("z przeniesienia");
            sumaposrednia.setKolumna7(0.0);
            sumaposrednia.setKolumna8(0.0);
            sumaposrednia.setKolumna9(0.0);
            sumaposrednia.setKolumna10(0.0);
            sumaposrednia.setKolumna11(0.0);
            sumaposrednia.setKolumna12(0.0);
            sumaposrednia.setKolumna13(0.0);
            sumaposrednia.setKolumna14(0.0);
            sumaposrednia.setKolumna15(0.0);
            sumaposrednia.setIdDok(new Long(1223));
            sumaposrednia.setKontr(new Klienci());
            DokKsiega sumakoncowa = new DokKsiega();
            sumakoncowa.setOpis("Razem");
            sumakoncowa.setKolumna7(0.0);
            sumakoncowa.setKolumna8(0.0);
            sumakoncowa.setKolumna9(0.0);
            sumakoncowa.setKolumna10(0.0);
            sumakoncowa.setKolumna11(0.0);
            sumakoncowa.setKolumna12(0.0);
            sumakoncowa.setKolumna13(0.0);
            sumakoncowa.setKolumna14(0.0);
            sumakoncowa.setKolumna15(0.0);
            sumakoncowa.setIdDok(new Long(1224));
            sumakoncowa.setKontr(new Klienci());
            for(Sumypkpir p : listasum){
                if(!p.getSumypkpirPK().getMc().equals(biezacymiesiac)){
                    sumaposrednia.setKolumna7(sumaposrednia.getKolumna7()+p.getSumy().getKolumna7());
                    sumaposrednia.setKolumna8(sumaposrednia.getKolumna8()+p.getSumy().getKolumna8());
                    sumaposrednia.setKolumna9(sumaposrednia.getKolumna9()+p.getSumy().getKolumna9());
                    sumaposrednia.setKolumna10(sumaposrednia.getKolumna10()+p.getSumy().getKolumna10());
                    sumaposrednia.setKolumna11(sumaposrednia.getKolumna11()+p.getSumy().getKolumna11());
                    sumaposrednia.setKolumna12(sumaposrednia.getKolumna12()+p.getSumy().getKolumna12());
                    sumaposrednia.setKolumna13(sumaposrednia.getKolumna13()+p.getSumy().getKolumna13());
                    sumaposrednia.setKolumna14(sumaposrednia.getKolumna14()+p.getSumy().getKolumna14());
                    sumaposrednia.setKolumna15(sumaposrednia.getKolumna15()+p.getSumy().getKolumna15());
                } else {
                    sumakoncowa.setKolumna7(sumaposrednia.getKolumna7()+p.getSumy().getKolumna7());
                    sumakoncowa.setKolumna8(sumaposrednia.getKolumna8()+p.getSumy().getKolumna8());
                    sumakoncowa.setKolumna9(sumaposrednia.getKolumna9()+p.getSumy().getKolumna9());
                    sumakoncowa.setKolumna10(sumaposrednia.getKolumna10()+p.getSumy().getKolumna10());
                    sumakoncowa.setKolumna11(sumaposrednia.getKolumna11()+p.getSumy().getKolumna11());
                    sumakoncowa.setKolumna12(sumaposrednia.getKolumna12()+p.getSumy().getKolumna12());
                    sumakoncowa.setKolumna13(sumaposrednia.getKolumna13()+p.getSumy().getKolumna13());
                    sumakoncowa.setKolumna14(sumaposrednia.getKolumna14()+p.getSumy().getKolumna14());
                    sumakoncowa.setKolumna15(sumaposrednia.getKolumna15()+p.getSumy().getKolumna15());
                }
            }
             lista.add(sumaposrednia);
             System.out.println("dodanie sumy posredniej");
             lista.add(sumakoncowa);
             System.out.println("dodanie sumy koncowej");
            
        } else {
            System.out.println("podsumowanie - nie!");
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
