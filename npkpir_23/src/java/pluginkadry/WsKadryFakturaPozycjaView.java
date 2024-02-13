/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pluginkadry;

import dao.FakturywystokresoweDAO;
import dao.WierszfakturybazaDAO;
import data.Data;
import entity.Fakturywystokresowe;
import entity.Wierszfakturybaza;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.ws.WebServiceRef;
import msg.Msg;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@ViewScoped
public class WsKadryFakturaPozycjaView implements Serializable {
    
    private static final long serialVersionUID = 1L;
    @WebServiceRef(wsdlLocation = "http://localhost:8080/kadry/WsKadryFakturaPozycja")
    private WsKadryFakturaPozycja_Service wsKadryFakturaPozycja_Service;
    
    @Inject
    private FakturywystokresoweDAO fakturywystokresoweDAO;
    @Inject
    private WierszfakturybazaDAO wierszfakturybazaDAO;
    private String rok;
    private String mc;
    @Inject
    private WpisView wpisView;
    private List<WierszFaktury> listawierszfaktury;
    private List<Wierszfakturybaza> wierzfakturybazalist;
    
    
    @PostConstruct
    public void inita() {
        rok = wpisView.getRokWpisuSt();
        mc = wpisView.getMiesiacWpisu();
        String[] okrespoprzeni = Data.poprzedniOkres(mc, rok);
        mc = okrespoprzeni[0];
        rok = okrespoprzeni[1];
        listawierszfaktury = new ArrayList<>();
    }
    
    public void init() {
        try {
            listawierszfaktury = new ArrayList<>();
            List<Fakturywystokresowe> fakturyokresowe = fakturywystokresoweDAO.findPodatnikBiezace(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            wsKadryFakturaPozycja_Service = new WsKadryFakturaPozycja_Service();
            WsKadryFakturaPozycja wsKadryFakturaPozycjaPort = wsKadryFakturaPozycja_Service.getWsKadryFakturaPozycjaPort();
            String hello = wsKadryFakturaPozycjaPort.hello("lolo");
            System.out.println("odp: "+hello);
            if (fakturyokresowe.isEmpty()==false) {
                for (Fakturywystokresowe f : fakturyokresowe) {
                    List<WierszFaktury> wiersze = wsKadryFakturaPozycjaPort.kadryfakturapozycjamcrok(f.getDokument().getKontrahent().getNip(), rok, mc);
                    if (wiersze.isEmpty()==false) {
                        f.setRecznaedycja(true);
                        
                        listawierszfaktury.addAll(wiersze);
                        System.out.println("odp: "+wiersze.get(0).nip+" nazwa: "+wiersze.get(0).nazwa+ " "+wiersze.size());
                    } else {
                        System.out.println("odebrałem pusta baze");
                    }
                }
                fakturywystokresoweDAO.editList(fakturyokresowe);
            }
            wierzfakturybazalist = wierszfakturybazaDAO.findbyRokMc(rok, mc);
            for (WierszFaktury w : listawierszfaktury) {
                Wierszfakturybaza odnaleziony = null;
                for (Wierszfakturybaza wb :wierzfakturybazalist) {
                    if (wb.getNip().equals(w.nip)&&wb.getOpis().equals(w.getOpis())) {
                        odnaleziony= wb;
                        if (w.getIlosc()!=wb.getIlosc()||w.getKwota()!=wb.getKwota()) {
                            wb.setWymagakorekty(true);
                            wierszfakturybazaDAO.edit(wb);
                        } else {
                            wb.setWymagakorekty(false);
                            wierszfakturybazaDAO.edit(wb);
                        }
                    }
                }
                if (odnaleziony==null) {
                    odnaleziony = new Wierszfakturybaza(w);
                    wierszfakturybazaDAO.create(odnaleziony);
                    wierzfakturybazalist.add(odnaleziony);
                }
            }
        } catch (Exception ex) {
            Logger.getLogger(WsKadryFakturaPozycjaView.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void usun() {
        if (listawierszfaktury!=null&&listawierszfaktury.isEmpty()==false) {
            wierszfakturybazaDAO.removeList(wierzfakturybazalist);
            wierzfakturybazalist = new ArrayList<>();
            Msg.msg("Usunięto wiersze z miesiąca");
        }
    }

    public String getRok() {
        return rok;
    }

    public void setRok(String rok) {
        this.rok = rok;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public List<WierszFaktury> getListawierszfaktury() {
        return listawierszfaktury;
    }

    public void setListawierszfaktury(List<WierszFaktury> listawierszfaktury) {
        this.listawierszfaktury = listawierszfaktury;
    }

    public List<Wierszfakturybaza> getWierzfakturybazalist() {
        return wierzfakturybazalist;
    }

    public void setWierzfakturybazalist(List<Wierszfakturybaza> wierzfakturybazalist) {
        this.wierzfakturybazalist = wierzfakturybazalist;
    }
    
    
    
}
