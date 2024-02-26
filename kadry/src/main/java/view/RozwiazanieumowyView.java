/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beanstesty.UmowaBean;
import beanstesty.UrlopBean;
import comparator.Etatcomparator;
import comparator.Stanowiskocomparator;
import comparator.ZmiennaPotraceniacomparator;
import comparator.ZmiennaWynagrodzeniacomparator;
import dao.AngazFacade;
import dao.EkwiwalentUrlopFacade;
import dao.NieobecnoscFacade;
import dao.NieobecnoscswiadectwoschemaFacade;
import dao.RejestrurlopowFacade;
import dao.RozwiazanieumowyFacade;
import dao.SMTPSettingsFacade;
import dao.UmowaFacade;
import data.Data;
import embeddable.WypowiedzeniePodstawa;
import embeddable.WypowiedzenieSposob;
import entity.Angaz;
import entity.Dzien;
import entity.EkwiwalentUrlop;
import entity.EtatPrac;
import entity.Nieobecnosc;
import entity.Nieobecnoscprezentacja;
import entity.Nieobecnoscswiadectwoschema;
import entity.Rejestrurlopow;
import entity.Rozwiazanieumowy;
import entity.SMTPSettings;
import entity.Skladnikpotracenia;
import entity.Skladnikwynagrodzenia;
import entity.Stanowiskoprac;
import entity.Swiadectwo;
import entity.Swiadectwodni;
import entity.Umowa;
import entity.Zmiennapotracenia;
import entity.Zmiennawynagrodzenia;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
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
@ViewScoped
public class RozwiazanieumowyView  implements Serializable {
    private static final long serialVersionUID = 1L;
    @Inject
    private RozwiazanieumowyFacade rozwiazanieumowyFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private NieobecnoscFacade nieobecnoscFacade;
    @Inject
    private RejestrurlopowFacade rejestrurlopowFacade;
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
    private Nieobecnoscprezentacja urlopprezentacja;
    private List<Umowa> umowykontynuacja;
    private String datagraniczna = null;
    @Inject
    private UpdateClassView updateClassView;
    
    
    @PostConstruct
    public void init() {
        lista = new ArrayList<>();
        
    }

    public void pobierzRozwiazanie() {
        lista = new ArrayList<>();
        if (wybranaumowa!=null) {
            if (wybranaumowa.getRozwiazanieumowy()!=null) {
                lista = new ArrayList<>();
                lista.add(wybranaumowa.getRozwiazanieumowy());
                selectedlista = wybranaumowa.getRozwiazanieumowy();
                Msg.msg("Pobrano istniejące rozwiazanie umowy");
            } else {
                Rozwiazanieumowy pobrane = rozwiazanieumowyFacade.findByUmowa(wybranaumowa);
                if (pobrane!=null) {
                    Msg.msg("Pobrano istniejące rozwiazanie umowy");
                    lista = new ArrayList<>();
                    lista.add(pobrane);
                    selectedlista = pobrane;
                } else {
                    lista = new ArrayList<>();
                    rozwiazanieUmowyNowe.setUmowa(wybranaumowa);
                }
            }
            if (wpisView.getUmowa()!=null && selectedlista!=null) {
                    umowykontynuacja = umowaFacade.findByAngaz(wpisView.getAngaz());
                    UmowaBean.oznaczumowyciaglosc(umowykontynuacja);
                    int grupaumow = wybranaumowa.getGrupaumow();
                    if (umowykontynuacja!=null&&umowykontynuacja.size()>1) {
                        umowykontynuacja = umowykontynuacja.stream().filter(p->p.getGrupaumow()==grupaumow).collect(Collectors.toList());
                        if (!umowykontynuacja.isEmpty()) {
                            datagraniczna = umowykontynuacja.get(0).getDataod();
                        }
                    } else if (umowykontynuacja!=null&&umowykontynuacja.size()==1) {
                        datagraniczna = umowykontynuacja.get(0).getDataod();
                    }
                    listanieob  = nieobecnoscFacade.findByAngaz(wpisView.getAngaz());
                    if (datagraniczna!=null) {
                        listanieob = listanieob.stream().filter(p->Data.czyjestpoTerminData(datagraniczna, p.getDataod())).collect(Collectors.toList());
                    }
                    listanieobecschema = nieobecnoscswiadectwoschemaFacade.findAll();
                    dnidoswiadectwa = naniesnieobecnoscinascheme(listanieob, listanieobecschema, selectedlista, wpisView.getRokWpisu());
                    Rejestrurlopow rejestrurlopow = rejestrurlopowFacade.findByAngaz(wpisView.getAngaz(), wpisView.getRokWpisu());
                    urlopprezentacja = UrlopBean.pobierzurlopSwiadectwo(wpisView.getAngaz(), wpisView.getRokWpisu(), selectedlista.getDatauplywuokresuwyp(), selectedlista.getDatauplywuokresuwyp(), rejestrurlopow);
            }
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
                //podobno mqa byc z calego zatrudnienia
                //List<Dzien> dniwroku = dni.stream().filter(s->s.getKalendarzmiesiac().getRok().equals(rok)).collect(Collectors.toList());
                List<Dzien> dni = filter0.stream().flatMap(t->t.getDzienList().stream()).collect(Collectors.toList());
                List<Dzien> dniroboczelist = dni.stream().filter(t->t.getTypdnia()==0).collect(Collectors.toList());
                //to zlikwidowalem bo dni liczyl wszyskie
                //if (p.getRodzajnieobecnosci().getKod().equals("CH")) {
                    dni = dni.stream().filter(t->t.getRok().equals(rok)).collect(Collectors.toList());
                    dniroboczelist = dni.stream().filter(t->t.getTypdnia()==0&&t.getRok().equals(rok)).collect(Collectors.toList());
                //}
                //nie ma zrobionych zmiennej dnikalendarfzowe
                //double dnirobocze = p.getRodzajnieobecnosci().isDnikalendarzowe() ? dniwroku.size() : dniroboczelist.size();
                double godzinyrobocze = dniroboczelist.stream().mapToDouble(f->f.getUrlopPlatny()).sum();
                swiadectwodni.setNieobecnoscswiadectwoschema(p);
                swiadectwodni.setDnikalendarzowe(dni.size());
                swiadectwodni.setDnirobocze(dniroboczelist.size());
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
            rozwiazanieUmowyNowe = new Rozwiazanieumowy();
            lista.remove(r);
            Msg.msg("Usunieto rozwiązanie");
        } else {
            Msg.msg("e","Nie wybrano rozwiązania");
        }
    }
    
    public void dodajnowy() {
        if (wybranaumowa!=null && wybranaumowa.getRozwiazanieumowy()==null) {
            rozwiazanieUmowyNowe.setDatadodania(new Date());
            rozwiazanieUmowyNowe.setUtworzyl(wpisView.getUzer().getImieNazwisko());
            rozwiazanieumowyFacade.create(rozwiazanieUmowyNowe);
            lista.add(rozwiazanieUmowyNowe);
            wybranaumowa.setRozwiazanieumowy(rozwiazanieUmowyNowe);
            umowaFacade.edit(wybranaumowa);
            Angaz angaz = angazFacade.findById(wpisView.getAngaz());
            zamknijskladniki(angaz, rozwiazanieUmowyNowe.getDatauplywuokresuwyp());
            angazFacade.edit(angaz);
            wpisView.setAngaz(angaz);
            updateClassView.updateRozwiazanieSkladniki();
            Msg.msg("Dodano nowe wypowiedzenie");
            if (wybranaumowa!=null && rozwiazanieUmowyNowe!=null) {
                listanieob  = nieobecnoscFacade.findByAngaz(wpisView.getAngaz());
                listanieobecschema = nieobecnoscswiadectwoschemaFacade.findAll();
                dnidoswiadectwa = naniesnieobecnoscinascheme(listanieob, listanieobecschema, rozwiazanieUmowyNowe, wpisView.getRokWpisu());
                
            }
        } else if (rozwiazanieUmowyNowe.getId()!=null) {
            Angaz angaz = angazFacade.findById(wpisView.getAngaz());
            rozwiazanieUmowyNowe.setDatadodania(new Date());
            rozwiazanieUmowyNowe.setUtworzyl(wpisView.getUzer().getImieNazwisko());
            rozwiazanieumowyFacade.edit(rozwiazanieUmowyNowe);
            zamknijskladniki(angaz, rozwiazanieUmowyNowe.getDatauplywuokresuwyp());
            angazFacade.edit(angaz);
            wpisView.setAngaz(angaz);
            updateClassView.updateRozwiazanieSkladniki();
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
            PdfSwiadectwo.drukuj(swiadectwo, dnidoswiadectwa, ekwiwalent,wpisView.getAngaz(), urlopprezentacja, datagraniczna);
        } else {
            Msg.msg("e","Brak wypowiedzenia");
        }
    }
    
    public void drukujwypowiedzenie() {
        if (rozwiazanieUmowyNowe!=null && rozwiazanieUmowyNowe.getUmowa()!=null) {
            if (rozwiazanieUmowyNowe.getUmowa().getAngaz().getFirma().getReprezentant()==null&&rozwiazanieUmowyNowe.getUmowa().getAngaz().getFirma().getReprezentant().equals("")) {
                Msg.msg("w","Prosze uzupełnić imię i nazwisko osoby reprezentującej firmę.");
            }
            String nazwa = rozwiazanieUmowyNowe.getUmowa().getPracownik().getPesel()+"wypowiedzenie.pdf";
            PdfWypowiedzenie.drukuj(rozwiazanieUmowyNowe, nazwa);
        } else {
            Msg.msg("e","Nie wybrano wypowiedzenia do wydruku");
        }
    }
    
     public void drukujwypowiedzenie(Rozwiazanieumowy roz) {
        if (roz!=null && roz.getUmowa()!=null) {
            if (roz.getUmowa().getAngaz().getFirma().getReprezentant()==null||roz.getUmowa().getAngaz().getFirma().getReprezentant().equals("")) {
                Msg.msg("w","Prosze uzupełnić imię i nazwisko osoby reprezentującej firmę.");
            } else {
                String nazwa = roz.getUmowa().getPracownik().getPesel()+"wypowiedzenie.pdf";
                PdfWypowiedzenie.drukuj(roz, nazwa);
            }
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
                findSprawaByDef.setUseremail(wpisView.getUzer().getEmail());
                findSprawaByDef.setPassword(wpisView.getUzer().getEmailhaslo());
                mail.Mail.mailUmowyoPrace(wpisView.getFirma(), wpisView.getFirma().getEmail(), null, findSprawaByDef, drukujmail.toByteArray(), nazwa, wpisView.getUzer().getEmail());
                Msg.msg("Wysłano umowę o pracę do klienta");
         }
    }
    
    private void zamknijskladniki(Angaz angaz, String data) {
        if (angaz!=null&&rozwiazanieUmowyNowe!=null) {
            List<Skladnikwynagrodzenia> skladnikwynagrodzeniaList = angaz.getSkladnikwynagrodzeniaList();
            for (Skladnikwynagrodzenia sklad : skladnikwynagrodzeniaList) {
                List<Zmiennawynagrodzenia> zmiennawynagrodzeniaList = sklad.getZmiennawynagrodzeniaList();
                if (zmiennawynagrodzeniaList!=null&&zmiennawynagrodzeniaList.size()>0) {
                    Collections.sort(zmiennawynagrodzeniaList, new ZmiennaWynagrodzeniacomparator());
                    zmiennawynagrodzeniaList.get(0).setDatado(data);
                }
            }
            List<Skladnikpotracenia> skladnikpotraceniaList = angaz.getSkladnikpotraceniaList();
            for (Skladnikpotracenia sklad : skladnikpotraceniaList) {
                List<Zmiennapotracenia> zmiennapotraceniaList = sklad.getZmiennapotraceniaList();
                if (zmiennapotraceniaList!=null&&zmiennapotraceniaList.size()>0) {
                    Collections.sort(zmiennapotraceniaList, new ZmiennaPotraceniacomparator());
                    zmiennapotraceniaList.get(0).setDatado(data);
                }
            }
            List<Stanowiskoprac> stanowiskopracList = angaz.getStanowiskopracList();
            if (stanowiskopracList!=null&&stanowiskopracList.size()>0) {
                Collections.sort(stanowiskopracList, new Stanowiskocomparator());
                stanowiskopracList.get(0).setDatado(data);
            }
            List<EtatPrac> etatpracList = angaz.getEtatList();
            if (etatpracList!=null&&etatpracList.size()>0) {
                Collections.sort(etatpracList, new Etatcomparator());
                etatpracList.get(0).setDatado(data);
            }
        }
        //angazFacade.edit(angaz);
        Msg.msg("Zamknięto składniki umowy");
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

    public Nieobecnoscprezentacja getUrlopprezentacja() {
        return urlopprezentacja;
    }

    public void setUrlopprezentacja(Nieobecnoscprezentacja urlopprezentacja) {
        this.urlopprezentacja = urlopprezentacja;
    }

    public List<Umowa> getUmowykontynuacja() {
        return umowykontynuacja;
    }

    public void setUmowykontynuacja(List<Umowa> umowykontynuacja) {
        this.umowykontynuacja = umowykontynuacja;
    }

    public String getDatagraniczna() {
        return datagraniczna;
    }

    public void setDatagraniczna(String datagraniczna) {
        this.datagraniczna = datagraniczna;
    }

    

    
    
    
   
    
}
