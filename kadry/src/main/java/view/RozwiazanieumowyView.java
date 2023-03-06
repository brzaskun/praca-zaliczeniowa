/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.EkwiwalentUrlopFacade;
import dao.NieobecnoscFacade;
import dao.NieobecnoscswiadectwoschemaFacade;
import dao.RozwiazanieumowyFacade;
import dao.SMTPSettingsFacade;
import dao.UmowaFacade;
import data.Data;
import embeddable.WypowiedzeniePodstawa;
import embeddable.WypowiedzenieSposob;
import entity.Dzien;
import entity.EkwiwalentUrlop;
import entity.Nieobecnosc;
import entity.Nieobecnoscswiadectwoschema;
import entity.Rozwiazanieumowy;
import entity.SMTPSettings;
import entity.Swiadectwo;
import entity.Swiadectwodni;
import entity.Umowa;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import msg.Msg;
import pdf.PdfSwiadectwo;
import pdf.PdfWypowiedzenie;

/**
 *
 * @author Osito
 */
@Named
@SessionScoped
public class RozwiazanieumowyView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private RozwiazanieumowyFacade rozwiazanieumowyFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private NieobecnoscFacade nieobecnoscFacade;
    @Inject
    private EkwiwalentUrlopFacade ekwiwalentSkladnikiFacade;
    private Rozwiazanieumowy selectedlista;
    private List<Rozwiazanieumowy> lista;
    private Umowa wybranaumowa;
    @Inject
    private Rozwiazanieumowy rozwiazanieUmowyNowe;
    @Inject
    private WpisView wpisView;
    private List<Nieobecnosc> listanieob;
    private List<Nieobecnoscswiadectwoschema> listanieobecschema;
    @Inject
    private NieobecnoscswiadectwoschemaFacade nieobecnoscswiadectwoschemaFacade;
    private List<Swiadectwodni> dnidoswiadectwa;
    private Swiadectwodni selected;
    @Inject
    private WypowiedzenieSposob wypowiedzenieSposob;
    @Inject
    private WypowiedzeniePodstawa wypowiedzeniePodstawa;
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
    private String datawystawieniaswiadectwa;
    
    
    
    @PostConstruct
    public void init() {
        lista = new ArrayList<>();
        
    }

    public void pobierzRozwiazanie() {
        lista = new ArrayList<>();
        if (wybranaumowa!=null) {
            Rozwiazanieumowy pobrane = rozwiazanieumowyFacade.findByUmowa(wybranaumowa);
            if (pobrane!=null) {
                lista.add(pobrane);
            } else {
                lista = new ArrayList<>();
                rozwiazanieUmowyNowe.setUmowa(wybranaumowa);
            }
            if (wpisView.getUmowa()!=null) {
                listanieob  = nieobecnoscFacade.findByAngaz(wpisView.getAngaz());
                listanieobecschema = nieobecnoscswiadectwoschemaFacade.findAll();
                dnidoswiadectwa = naniesnieobecnoscinascheme(listanieob, listanieobecschema, pobrane, wpisView.getRokWpisu());
            }
            Msg.msg("Pobrano rozwiązania umowy");
        } else {
            lista = new ArrayList<>();
        }
    }
    
     private List<Swiadectwodni>  naniesnieobecnoscinascheme(List<Nieobecnosc> listanieob, List<Nieobecnoscswiadectwoschema> listanieobecschema, Rozwiazanieumowy rozwiazanieumowy, String rok) {
        //urlopy
        List<Swiadectwodni> swiadectwodnilista = new ArrayList<>();
        if (listanieob!=null) {
            for (Nieobecnoscswiadectwoschema p : listanieobecschema) {
                Swiadectwodni swiadectwodni = new Swiadectwodni();
                swiadectwodni.setSwiadectwo(new Swiadectwo(rozwiazanieumowy));
                List<Nieobecnosc> filter0 = listanieob.stream().filter(r->r.getRodzajnieobecnosci().equals(p.getRodzajnieobecnosci())).collect(Collectors.toList());
                List<Dzien> dni = filter0.stream().flatMap(t->t.getDzienList().stream()).collect(Collectors.toList());
                List<Dzien> dniwroku = dni.stream().filter(s->s.getKalendarzmiesiac().getRok().equals(rok)).collect(Collectors.toList());
                List<Dzien> dniroboczelist = dniwroku.stream().filter(t->t.getTypdnia()==0).collect(Collectors.toList());
                double dnirobocze = p.getRodzajnieobecnosci().isDnikalendarzowe() ? dniwroku.size() : dniroboczelist.size();
                double godzinyrobocze = dniroboczelist.stream().mapToDouble(f->f.getUrlopPlatny()).sum();
                swiadectwodni.setNieobecnoscswiadectwoschema(p);
                swiadectwodni.setDni(dnirobocze);
                swiadectwodni.setGodziny(godzinyrobocze);
                swiadectwodni.setCzesci(filter0.size());
                swiadectwodnilista.add(swiadectwodni);
                swiadectwodni.setNieobecnoscilista(filter0);
            }
        }
        return swiadectwodnilista;
    }
    
    public void usun(Rozwiazanieumowy r) {
        if (r!=null) {
            r.getUmowa().setRozwiazanieumowy(null);
            umowaFacade.edit(r.getUmowa());
            rozwiazanieumowyFacade.remove(r);
            lista.remove(r);
            Msg.msg("Usunieto rozwiązanie");
        } else {
            Msg.msg("e","Nie wybrano rozwiązania");
        }
    }
    
    public void dodajnowy() {
        if (wybranaumowa!=null && wybranaumowa.getRozwiazanieumowy()==null) {
            rozwiazanieUmowyNowe.setData(new Date());
            rozwiazanieumowyFacade.create(rozwiazanieUmowyNowe);
            lista.add(rozwiazanieUmowyNowe);
            wybranaumowa.setRozwiazanieumowy(rozwiazanieUmowyNowe);
            umowaFacade.edit(wybranaumowa);
            Msg.msg("Dodano nowe wypowiedzenie");
        } else if (rozwiazanieUmowyNowe.getId()!=null) {
            rozwiazanieUmowyNowe.setData(new Date());
            rozwiazanieumowyFacade.edit(rozwiazanieUmowyNowe);
            Msg.msg("Edytowano wypowiedzenie");
        } else {
            Msg.msg("e","Umowa ma już wygenerowane wypowiedzenie.");   
        }
    }

    public void drukujswiadectwo() {
        Swiadectwo swiadectwo = new  Swiadectwo();
        if (selectedlista!=null) {
            swiadectwo.setDatawystawienia(Data.aktualnaData());
            if (datawystawieniaswiadectwa!=null&&!datawystawieniaswiadectwa.equals("")) {
                swiadectwo.setDatawystawienia(datawystawieniaswiadectwa);
            }
            swiadectwo.setRozwiazanieumowy(selectedlista);
            EkwiwalentUrlop ekwiwalent = ekwiwalentSkladnikiFacade.findbyUmowa(swiadectwo.getRozwiazanieumowy().getUmowa());
            PdfSwiadectwo.drukuj(swiadectwo, dnidoswiadectwa, ekwiwalent);
        } else {
            Msg.msg("e","Brak wypowiedzenia");
        }
    }
    
    public void drukujwypowiedzenie() {
        if (rozwiazanieUmowyNowe!=null && rozwiazanieUmowyNowe.getUmowa()!=null) {
            String nazwa = rozwiazanieUmowyNowe.getUmowa().getPracownik().getPesel()+"wypowiedzenie.pdf";
            PdfWypowiedzenie.drukuj(rozwiazanieUmowyNowe, nazwa);
        } else {
            Msg.msg("e","Nie wybrano wypowiedzenia do wydruku");
        }
    }
    
     public void drukujwypowiedzenie(Rozwiazanieumowy roz) {
        if (roz!=null && roz.getUmowa()!=null) {
            String nazwa = roz.getUmowa().getPracownik().getPesel()+"wypowiedzenie.pdf";
            PdfWypowiedzenie.drukuj(roz, nazwa);
        } else {
            Msg.msg("e","Nie wybrano wypowiedzenia do wydruku");
        }
    }
     
     public void edytuj(Rozwiazanieumowy roz) {
        if (roz!=null && roz.getUmowa()!=null) {
            rozwiazanieUmowyNowe = roz;
            Msg.msg("Dokument gotowy do edycji");
        } else {
            Msg.msg("e","Nie wybrano wypowiedzenia do wydruku");
        }
    }
     
     public void mailwypowiedzenie(Rozwiazanieumowy rozwiazanie) {
         if (rozwiazanie==null) {
                Msg.msg("w", "Nie wybrano wypowiedze nia");
            } else {
                String nazwa = rozwiazanie.getUmowa().getPracownik().getPesel()+"wypowiedzenie.pdf";
                ByteArrayOutputStream drukujmail = PdfWypowiedzenie.drukujMail(rozwiazanie, nazwa);
                SMTPSettings findSprawaByDef = sMTPSettingsFacade.findSprawaByDef();
                mail.Mail.mailUmowyoPrace(wpisView.getFirma(), wpisView.getFirma().getEmail(), null, findSprawaByDef, drukujmail.toByteArray(), nazwa, wpisView.getUzer().getEmail());
                Msg.msg("Wysłano umowę o pracę do klienta");
         }
    }
    
    public Rozwiazanieumowy getSelectedlista() {
        return selectedlista;
    }

    public void setSelectedlista(Rozwiazanieumowy selectedlista) {
        this.selectedlista = selectedlista;
    }

    public List<Rozwiazanieumowy> getLista() {
        return lista;
    }

    public void setLista(List<Rozwiazanieumowy> lista) {
        this.lista = lista;
    }

    public Rozwiazanieumowy getRozwiazanieUmowyNowe() {
        return rozwiazanieUmowyNowe;
    }

    public void setRozwiazanieUmowyNowe(Rozwiazanieumowy rozwiazanieUmowyNowe) {
        this.rozwiazanieUmowyNowe = rozwiazanieUmowyNowe;
    }

    public Umowa getWybranaumowa() {
        return wybranaumowa;
    }

    public void setWybranaumowa(Umowa wybranaumowa) {
        this.wybranaumowa = wybranaumowa;
    }

    public List<Nieobecnosc> getListanieob() {
        return listanieob;
    }

    public void setListanieob(List<Nieobecnosc> listanieob) {
        this.listanieob = listanieob;
    }

    public List<Swiadectwodni> getDnidoswiadectwa() {
        return dnidoswiadectwa;
    }

    public void setDnidoswiadectwa(List<Swiadectwodni> dnidoswiadectwa) {
        this.dnidoswiadectwa = dnidoswiadectwa;
    }

    public Swiadectwodni getSelected() {
        return selected;
    }

    public void setSelected(Swiadectwodni selected) {
        this.selected = selected;
    }

    public WypowiedzenieSposob getWypowiedzenieSposob() {
        return wypowiedzenieSposob;
    }

    public void setWypowiedzenieSposob(WypowiedzenieSposob wypowiedzenieSposob) {
        this.wypowiedzenieSposob = wypowiedzenieSposob;
    }

    public WypowiedzeniePodstawa getWypowiedzeniePodstawa() {
        return wypowiedzeniePodstawa;
    }

    public void setWypowiedzeniePodstawa(WypowiedzeniePodstawa wypowiedzeniePodstawa) {
        this.wypowiedzeniePodstawa = wypowiedzeniePodstawa;
    }

    public String getDatawystawieniaswiadectwa() {
        return datawystawieniaswiadectwa;
    }

    public void setDatawystawieniaswiadectwa(String datawystawieniaswiadectwa) {
        this.datawystawieniaswiadectwa = datawystawieniaswiadectwa;
    }

   

    
    
    
   
    
}
