/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package schedule;

import dao.AngazFacade;
import dao.SMTPSettingsFacade;
import dao.UmowaFacade;
import data.Data;
import entity.Angaz;
import entity.FirmaKadry;
import entity.SMTPSettings;
import entity.Umowa;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import mail.Mail;
import view.WpisView;

/**
 *
 * @author Osito
 */
@Named
@Stateless
public class UmowaReminder {
    @Inject
    private WpisView wpisView;
    @Inject
    private AngazFacade angazFacade;
    @Inject
    private UmowaFacade umowaFacade;
    @Inject
    private SMTPSettingsFacade sMTPSettingsFacade;
    
    @Schedule(hour = "21", minute = "21", persistent = false)
    public void oznaczumowy() {
        List<Angaz> angaze = angazFacade.findAll();
        List<Umowa> listaumowy = new ArrayList<>();
        try {
            for (Iterator<Angaz> it = angaze.iterator(); it.hasNext();) {
                    Angaz angaz = it.next();
                    if (angaz.jestumowaAktywna(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu())==false) {
                        it.remove();
                    }
            }
            for (Angaz a : angaze) {
                Umowa umowaAktywna = a.pobierzumowaAktywna(wpisView.getRokWpisu(), wpisView.getMiesiacWpisu());
                if (umowaAktywna!=null) {
                    listaumowy.add(umowaAktywna);
                }
            }
        } catch (Exception e) {
            System.out.println("blad umowareminder.java oznaczumowy");
            System.out.println("nie ma roku ani miesiaca we wpisview");
        }
        if (listaumowy!=null) {
            for (Umowa u : listaumowy) {
                if (u.getDatado()!=null&&!u.getDatado().equals("")&&u.getDatado().length()==10) {
                    try {
                        String data = Data.zmienkolejnosc(u.getDatado());
                        LocalDate today = LocalDate.parse(data) ;
                        LocalDate tomorrow = today.minusDays(18) ;
                        u.setDataprzypomnienia(tomorrow.toString());
                    } catch (Exception e){}
                }
            }
            umowaFacade.editList(listaumowy);
        }
        if (listaumowy!=null) {
            for (Umowa um : listaumowy) {
                Angaz u =um.getAngaz();
                if (u.getDataszkolenie5lat()!=null&&!u.getDataszkolenie5lat().equals("")) {
                    LocalDate today = LocalDate.parse(u.getDataszkolenie5lat()) ;
                    LocalDate tomorrow = today.plusDays(1804) ;
                    u.setDataprzypomnieniaszkolenie(tomorrow.toString());
                } else if (u.getDataszkolenie3lata()!=null&&!u.getDataszkolenie3lata().equals("")) {
                    LocalDate today = LocalDate.parse(u.getDataszkolenie3lata()) ;
                    LocalDate tomorrow = today.plusDays(1074) ;
                    u.setDataprzypomnieniaszkolenie(tomorrow.toString());
                } else if (u.getDataszkolenierok()!=null&&!u.getDataszkolenierok().equals("")) {
                    LocalDate today = LocalDate.parse(u.getDataszkolenierok()) ;
                    LocalDate tomorrow = today.plusDays(344) ;
                    u.setDataprzypomnieniaszkolenie(tomorrow.toString());
                }
            }
            umowaFacade.editList(listaumowy);
        }
    }
    
    @Schedule(dayOfWeek = "Mon-Fri", hour = "22", minute = "22", persistent = false)
    public void alarmumowy() {
        List<Angaz> angaze = angazFacade.findAll();
        List<Umowa> listaumowy = new ArrayList<>();
        String databiezaca = Data.aktualnaData();
        for (Angaz a : angaze) {
            List<Umowa> umowaList = a.getUmowaList();
            for (Umowa umowa : umowaList) {
                if (umowa.getDatado()!=null&&!umowa.getDatado().equals("")) {
                    boolean czyjestpomiedzy = Data.czyjestpomiedzy(umowa.getDataod(), databiezaca, umowa.getDatado());
                    boolean czymailniezostalwyslany = umowa.getDataprzypomnieniamail()==null||umowa.getDataprzypomnieniamail().equals("");
                    boolean czymineladataalarmu = Data.czyjestpoTerminData(umowa.getDataprzypomnienia(), databiezaca);
                    if (czyjestpomiedzy&&czymailniezostalwyslany&&czymineladataalarmu) {
                        listaumowy.add(umowa);
                        break;
                    }
                }
            }
            
        }
        if (listaumowy!=null) {
            SMTPSettings ogolne = sMTPSettingsFacade.findSprawaByDef();
            for (Umowa u : listaumowy) {
                FirmaKadry firma = u.getAngaz().getFirma();
                if (firma.getEmail()!=null) {
                    Mail.mailPrzypomnienieoUmowie(firma.getEmail(), ogolne, ogolne, "m.piwonska@taxman.biz.pl", u);
                    u.setDataprzypomnieniamail(Data.aktualnaData());
                }
            }
            umowaFacade.editList(listaumowy);
        }
        
    }
    
    @Schedule(dayOfWeek = "Mon-Fri", hour = "23", minute = "23", persistent = false)
    public void alarA1() {
        List<Angaz> angaze = angazFacade.findAll();
        List<Angaz> listaangaze = new ArrayList<>();
        String databiezaca = Data.aktualnaData();
        for (Angaz angaz : angaze) {
            if (angaz.getDataa1()!=null&&!angaz.getDataa1().equals("")) {
                boolean czymailniezostalwyslany = angaz.getDataa1mail()==null||angaz.getDataa1mail().equals("");
                String data = angaz.getDataa1mail();
                LocalDate today = LocalDate.parse(data) ;
                LocalDate tomorrow = today.minusDays(21) ;
                String dataprzypomnienia = tomorrow.toString();
                boolean czymineladataalarmu = Data.czyjestpoTerminData(dataprzypomnienia, databiezaca);
                if (czymailniezostalwyslany&&czymineladataalarmu) {
                    listaangaze.add(angaz);
                    break;
                }
            }
        }
        if (listaangaze!=null) {
            SMTPSettings ogolne = sMTPSettingsFacade.findSprawaByDef();
            for (Angaz u : listaangaze) {
                FirmaKadry firma = u.getFirma();
                if (firma.getEmail()!=null) {
                    Mail.mailPrzypomnienieoA1(firma.getEmail(), ogolne, ogolne, "n.sinkiewicz@taxman.biz.pl", u);
                    u.setDataa1mail(Data.aktualnaData());
                }
            }
            angazFacade.editList(listaangaze);
        }
        
    }
    
    @Schedule(dayOfWeek = "Mon-Fri", hour = "21", minute = "21", persistent = false)
    public void alarbadabnia() {
        List<Angaz> angaze = angazFacade.findAll();
        List<Angaz> listaangaze = new ArrayList<>();
        String databiezaca = Data.aktualnaData();
        for (Angaz angaz : angaze) {
            if (angaz.getDatabadanielekarskie()!=null&&!angaz.getDatabadanielekarskie().equals("")) {
                boolean czymailniezostalwyslany = angaz.getDatabadanielekarskiemail()==null||angaz.getDatabadanielekarskiemail().equals("");
                String data = angaz.getDatabadanielekarskiemail();
                LocalDate today = LocalDate.parse(data) ;
                LocalDate tomorrow = today.minusDays(21) ;
                String dataprzypomnienia = tomorrow.toString();
                boolean czymineladataalarmu = Data.czyjestpoTerminData(dataprzypomnienia, databiezaca);
                if (czymailniezostalwyslany&&czymineladataalarmu) {
                    listaangaze.add(angaz);
                    break;
                }
            }
        }
        if (listaangaze!=null) {
            SMTPSettings ogolne = sMTPSettingsFacade.findSprawaByDef();
            for (Angaz u : listaangaze) {
                FirmaKadry firma = u.getFirma();
                if (firma.getEmail()!=null) {
                    Mail.mailPrzypomnienieoA1(firma.getEmail(), ogolne, ogolne, "n.sinkiewicz@taxman.biz.pl", u);
                    u.setDataa1mail(Data.aktualnaData());
                }
            }
            angazFacade.editList(listaangaze);
        }
        
    }
}
