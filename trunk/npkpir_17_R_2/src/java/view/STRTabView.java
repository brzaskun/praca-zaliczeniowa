/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AmoDokDAO;
import dao.STRDAO;
import embeddable.Roki;
import embeddable.STRtabela;
import embeddable.Umorzenie;
import entity.Amodok;
import entity.SrodekTrw;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Osito
 */
@ManagedBean(name="STRTableView")
@RequestScoped
public class STRTabView implements Serializable{
    @Inject
    private STRDAO sTRDAO;
    @Inject
    private AmoDokDAO amoDokDAO;
    
    private SrodekTrw selectedSTR;
    private static SrodekTrw dokdoUsuniecia;

   
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
     
    private HashMap<String,SrodekTrw> dokHashTable;
    //tablica kluczy do obiektów
    private List<String> kluczDOKjsf;
    //tablica obiektów
    private List<SrodekTrw> obiektDOKjsf;
    //tablica obiektw danego klienta
    private List<SrodekTrw> obiektDOKjsfSel;
    //tablica obiektów danego klienta z określonego roku i miesiąca
    private List<SrodekTrw> obiektDOKmrjsfSel;
     //tablica obiektów danego klienta z określonego roku i miesiecy
    private List<SrodekTrw> obiektDOKmrjsfSelX;
    //wyposazenie
    private List<SrodekTrw> obiektDOKmrjsfSelWyposazenie;
    //srodki trwale wykaz rok biezacy
    private List<STRtabela> strtabela;
    

   
    
    public STRTabView() {
        selectedSTR = new SrodekTrw();
        dokHashTable = new HashMap<>();
        kluczDOKjsf = new ArrayList<>();
        obiektDOKjsf = new ArrayList<>();
        obiektDOKjsfSel = new ArrayList<>();
        obiektDOKmrjsfSel = new ArrayList<>();
        obiektDOKmrjsfSelX = new ArrayList<>();
        obiektDOKmrjsfSelWyposazenie = new ArrayList<>();
        strtabela = new ArrayList<>();
    }

       @PostConstruct
    public void init() {
        if (wpisView.getPodatnikWpisu() != null) {
            Collection c = null;
            try {
                c = sTRDAO.getDownloaded();
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            if (c != null) {
                Iterator it;
                it = c.iterator();
                int j=1;
                while (it.hasNext()) {
                    SrodekTrw tmp = (SrodekTrw) it.next();
                    tmp.setNrsrodka(j);
                    j++;
                    kluczDOKjsf.add(tmp.getId().toString());
                    obiektDOKjsf.add(tmp);
                    if (tmp.getPodatnik().equals(wpisView.getPodatnikWpisu())) {
                         if(tmp.getTyp().equals("wyposazenie")){
                             obiektDOKmrjsfSelWyposazenie.add(tmp);
                            
                        } else {
                             obiektDOKjsfSel.add(tmp);
                         }
                    }
                    dokHashTable.put(tmp.getId().toString(), tmp);
                }
                Iterator itx;
                itx = obiektDOKjsfSel.iterator();
                while (itx.hasNext()) {
                    SrodekTrw tmpx = (SrodekTrw) itx.next();
                    String m = wpisView.getMiesiacWpisu();
                    Integer r = wpisView.getRokWpisu();
                    //if (tmpx.getPkpirM().equals(m) && tmpx.getPkpirR().equals(r.toString())) {
                  
                     
                 
                        obiektDOKmrjsfSel.add(tmpx);
                  
                //}
            }
                if (wpisView.getMiesiacOd() != null) {
                    obiektDOKmrjsfSelX.clear();
                    Iterator itxX;
                    itxX = obiektDOKjsfSel.iterator();
                    Integer r = wpisView.getRokWpisu();
                    String mOd = wpisView.getMiesiacOd();
                    Integer mOdI = Integer.parseInt(mOd);
                    String mDo = wpisView.getMiesiacDo();
                    Integer mDoI = Integer.parseInt(mDo);
                    while (itxX.hasNext()) {
                        SrodekTrw tmpx = (SrodekTrw) itxX.next();
                        for (int i = mOdI; i <= mDoI; i++) {
                         //   if (tmpx.getPkpirM().equals(mapa.get(i)) && tmpx.getPkpirR().equals(r.toString())) {
                                obiektDOKmrjsfSelX.add(tmpx);
                         //   }
        }
                    }
                }
            }
        }
    }

    //przyporzadkowuje planowane odpisy do konkretnych miesiecy
    public void generujodpisy() {
        List<SrodekTrw> lista = new ArrayList<>();
        lista.addAll(obiektDOKjsfSel);
        Iterator it;
        it = lista.iterator();
        while (it.hasNext()) {
            SrodekTrw srodek = (SrodekTrw) it.next();
            List<Double> planowane = new ArrayList<>();
            planowane.addAll(srodek.getUmorzPlan());
            List<Umorzenie> umorzenia = new ArrayList<>();
            Integer rokOd = Integer.parseInt(srodek.getDataprzek().substring(0, 4));
            Integer mcOd;
            if(srodek.getStawka()==100){
                mcOd = Integer.parseInt(srodek.getDataprzek().substring(6, 7));
            } else {
                mcOd = Integer.parseInt(srodek.getDataprzek().substring(6, 7))+1;
            }
            Iterator itX;
            itX = planowane.iterator();
            int i = 1;
            while (itX.hasNext()) {
                Double kwotaodpisMC = (Double) itX.next();
                Umorzenie odpisZaDanyOkres = new Umorzenie();
                odpisZaDanyOkres.setKwota(BigDecimal.valueOf(kwotaodpisMC.doubleValue()));
                odpisZaDanyOkres.setRokUmorzenia(rokOd);
                odpisZaDanyOkres.setMcUmorzenia(mcOd);
                odpisZaDanyOkres.setNrUmorzenia(i);
                odpisZaDanyOkres.setNazwaSrodka(srodek.getNazwa());
                i++;
                if (mcOd == 12) {
                    rokOd++;
                    mcOd = 1;
                } else {
                    mcOd++;
                }
                umorzenia.add(odpisZaDanyOkres);
            }
            srodek.setUmorzWyk(umorzenia);
            sTRDAO.edit(srodek);
        }
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Odpisy wygenerowane","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void generujamodokumenty() {
        List<SrodekTrw> lista = new ArrayList<>();
        lista.addAll(obiektDOKjsfSel);
        String pod = wpisView.getPodatnikWpisu();
        String nazwapod = pod;
        amoDokDAO.destroy(pod);
         RequestContext.getCurrentInstance().update("formSTR:umorzeniaTablica");
        Integer rokOd = 2012;
        Integer mcOd = 1;
        Roki roki = new Roki();
        int ostatni = roki.getRokiList().size();
        while (rokOd < roki.getRokiList().get(ostatni-1)) {
            Amodok amoDok = new Amodok();
            amoDok.setPodatnik(pod);
            amoDok.setRok(rokOd);
            amoDok.setMc(mcOd);
            Iterator it;
            it = lista.iterator();
            while (it.hasNext()) {
                SrodekTrw srodek = (SrodekTrw) it.next();
                List<Umorzenie> umorzeniaWyk = new ArrayList<>();
                umorzeniaWyk.addAll(srodek.getUmorzWyk());
                Iterator itX;
                itX = umorzeniaWyk.iterator();
                while (itX.hasNext()) {
                    Umorzenie umAkt = (Umorzenie) itX.next();
                    if ((umAkt.getRokUmorzenia().equals(rokOd)) && (umAkt.getMcUmorzenia().equals(mcOd))) {
                        amoDok.getUmorzenia().add(umAkt);
                    } 
                }
            }
            if (mcOd == 12) {
                amoDokDAO.dodaj(amoDok);
                rokOd++;
                mcOd = 1;
                
            } else {
                amoDokDAO.dodaj(amoDok);
                mcOd++;
                
            }
        }
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Dokumenty amortyzacyjne wygenerowane","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
        RequestContext.getCurrentInstance().update("formSTR:dokumUmorzenieLista");
    }
    
    public void generujTabeleRokBiezacy(){
        List<SrodekTrw> lista = new ArrayList<>();
        lista.addAll(obiektDOKjsfSel);
        Iterator it;
        it = lista.iterator();
        while (it.hasNext()) {
            SrodekTrw str = (SrodekTrw) it.next();
            STRtabela strdocelowy = new STRtabela();
            strdocelowy.setId(str.getId());
            strdocelowy.setNazwa(str.getNazwa());
            strdocelowy.setKst(str.getKst());
            strdocelowy.setOdpisrok(str.getOdpisrok());
            strdocelowy.setSymbol(str.getSymbol());
            strdocelowy.setDatazak(str.getDatazak());
            strdocelowy.setDataprzek(str.getDataprzek());
            strdocelowy.setDatawy(str.getDatawy());
            strdocelowy.setNetto(str.getNetto());
            strdocelowy.setPodatnik(str.getPodatnik());
            List<Double> miesiace = new ArrayList<>();
            Iterator itX;
            itX = str.getUmorzWyk().iterator();
            BigDecimal umnar = new BigDecimal(0);
            while (itX.hasNext()) {
                Umorzenie um = (Umorzenie) itX.next();
                if (um.getRokUmorzenia().equals(wpisView.getRokWpisu())) {
                    Integer mc = um.getMcUmorzenia();
                    switch (mc) {
                        case 1:
                            strdocelowy.setStyczen(um.getKwota().doubleValue());
                            break;
                        case 2:
                            strdocelowy.setLuty(um.getKwota().doubleValue());
                            break;
                        case 3:
                            strdocelowy.setMarzec(um.getKwota().doubleValue());
                            break;
                        case 4:
                            strdocelowy.setKwiecien(um.getKwota().doubleValue());
                            break;
                        case 5:
                            strdocelowy.setMaj(um.getKwota().doubleValue());
                            break;
                        case 6:
                            strdocelowy.setCzerwiec(um.getKwota().doubleValue());
                            break;
                        case 7:
                            strdocelowy.setLipiec(um.getKwota().doubleValue());
                            break;
                        case 8:
                            strdocelowy.setSierpien(um.getKwota().doubleValue());
                            break;
                        case 9:
                            strdocelowy.setWrzesien(um.getKwota().doubleValue());
                            break;
                        case 10:
                            strdocelowy.setPazdziernik(um.getKwota().doubleValue());
                            break;
                        case 11:
                            strdocelowy.setListopad(um.getKwota().doubleValue());
                            break;
                        case 12:
                            strdocelowy.setGrudzien(um.getKwota().doubleValue());
                            break;
                    }
                } else if (um.getRokUmorzenia()<wpisView.getRokWpisu()) {
                    umnar = umnar.add(um.getKwota());
                }
            }
            strdocelowy.setUmorzeniaDo(umnar);
            strtabela.add(strdocelowy);            
        }
    }
    
     public void destroy(SrodekTrw selDok) {
        dokdoUsuniecia = new SrodekTrw();
        dokdoUsuniecia = selDok;
        
    }
    
    public void destroy2() {
//        HttpServletRequest request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        //       Principal principal = request.getUserPrincipal();
//        if(request.isUserInRole("Administrator")){
//        if(sprawdzczyniemarozrachunkow()==true){
//             FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Dokument nie usunięty - Usuń wpierw rozrachunki, proszę", dokdoUsuniecia.getIdDok().toString());
//        FacesContext.getCurrentInstance().addMessage(null, msg);
//        } else {
        try {
            obiektDOKjsfSel.remove(dokdoUsuniecia);
            sTRDAO.destroy(dokdoUsuniecia);
        } catch (Exception e) {
            System.out.println("Nie usnieto " + dokdoUsuniecia.getNazwa() + " " + e.toString());
        }
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Środek trwały usunięty", dokdoUsuniecia.getNazwa().toString());
        FacesContext.getCurrentInstance().addMessage(null, msg);
//    } else {
//            FacesMessage msg = new FacesMessage("Nie masz uprawnien do usuniecia dokumentu", selDokument.getIdDok().toString());
//          FacesContext.getCurrentInstance().addMessage(null, msg);
//        }
//     }
//    }
    }
    
    
     public STRDAO getsTRDAO() {
        return sTRDAO;
    }

    public void setsTRDAO(STRDAO sTRDAO) {
        this.sTRDAO = sTRDAO;
    }

    public AmoDokDAO getAmoDokDAO() {
        return amoDokDAO;
    }

    public void setAmoDokDAO(AmoDokDAO amoDokDAO) {
        this.amoDokDAO = amoDokDAO;
    }
    
    public SrodekTrw getSelectedSTR() {
        return selectedSTR;
    }

    public void setSelectedSTR(SrodekTrw selectedSTR) {
        this.selectedSTR = selectedSTR;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public HashMap<String, SrodekTrw> getDokHashTable() {
        return dokHashTable;
    }

    public void setDokHashTable(HashMap<String, SrodekTrw> dokHashTable) {
        this.dokHashTable = dokHashTable;
    }

    public List<String> getKluczDOKjsf() {
        return kluczDOKjsf;
    }

    public void setKluczDOKjsf(List<String> kluczDOKjsf) {
        this.kluczDOKjsf = kluczDOKjsf;
    }

    public List<SrodekTrw> getObiektDOKjsf() {
        return obiektDOKjsf;
    }

    public void setObiektDOKjsf(List<SrodekTrw> obiektDOKjsf) {
        this.obiektDOKjsf = obiektDOKjsf;
    }

    public List<SrodekTrw> getObiektDOKjsfSel() {
        return obiektDOKjsfSel;
    }

    public void setObiektDOKjsfSel(List<SrodekTrw> obiektDOKjsfSel) {
        this.obiektDOKjsfSel = obiektDOKjsfSel;
    }

    public List<SrodekTrw> getObiektDOKmrjsfSel() {
        return obiektDOKmrjsfSel;
    }

    public void setObiektDOKmrjsfSel(List<SrodekTrw> obiektDOKmrjsfSel) {
        this.obiektDOKmrjsfSel = obiektDOKmrjsfSel;
    }

    public List<SrodekTrw> getObiektDOKmrjsfSelX() {
        return obiektDOKmrjsfSelX;
    }

    public void setObiektDOKmrjsfSelX(List<SrodekTrw> obiektDOKmrjsfSelX) {
        this.obiektDOKmrjsfSelX = obiektDOKmrjsfSelX;
    }

    public List<SrodekTrw> getObiektDOKmrjsfSelWyposazenie() {
        return obiektDOKmrjsfSelWyposazenie;
    }

    public void setObiektDOKmrjsfSelWyposazenie(List<SrodekTrw> obiektDOKmrjsfSelWyposazenie) {
        this.obiektDOKmrjsfSelWyposazenie = obiektDOKmrjsfSelWyposazenie;
    }

    public List<STRtabela> getStrtabela() {
        return strtabela;
    }

    public void setStrtabela(List<STRtabela> strtabela) {
        this.strtabela = strtabela;
    }

   
}
