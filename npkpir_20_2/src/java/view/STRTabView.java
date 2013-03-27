/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.AmoDokDAO;
import dao.STRDAO;
import embeddable.Mce;
import embeddable.Roki;
import embeddable.STRtabela;
import embeddable.Umorzenie;
import entity.Amodok;
import entity.SrodekTrw;
import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import msg.Msg;
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
    private static boolean napewnousunac;

   
    @ManagedProperty(value="#{WpisView}")
    private WpisView wpisView;
    
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
    //dokumenty amortyzacyjne
    private List<Amodok> amodoklist;
    /**
     * Dane informacyjne gora strony srodkitablica.xhtml
     */
    private int iloscsrodkow;
    private int zakupionewbiezacyrok;
    
   
    
    public STRTabView() {
        selectedSTR = new SrodekTrw();
        obiektDOKjsf = new ArrayList<>();
        obiektDOKjsfSel = new ArrayList<>();
        obiektDOKmrjsfSel = new ArrayList<>();
        obiektDOKmrjsfSelX = new ArrayList<>();
        obiektDOKmrjsfSelWyposazenie = new ArrayList<>();
        strtabela = new ArrayList<>();
        amodoklist = new ArrayList<>();
    }

    @PostConstruct
    public void init() {
        String rokdzisiejszy = String.valueOf(Calendar.getInstance().get(Calendar.YEAR));
        zakupionewbiezacyrok = 0;
        if (wpisView.getPodatnikWpisu() != null) {
            List<SrodekTrw> c = new ArrayList<>();
            try {
                c = sTRDAO.findStrPod(wpisView.getPodatnikWpisu());
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            if (!c.isEmpty()) {
                int i = 1;
                int j = 1;
                for (SrodekTrw tmp : c) {
                    obiektDOKjsf.add(tmp);
                    if (tmp.getPodatnik().equals(wpisView.getPodatnikWpisu())) {
                        if (tmp.getTyp().equals("wyposazenie")) {
                            tmp.setNrsrodka(i++);
                            obiektDOKmrjsfSelWyposazenie.add(tmp);

                        } else {
                            tmp.setNrsrodka(j++);
                            if(tmp.getDatazak().substring(0, 4).equals(rokdzisiejszy)){
                                zakupionewbiezacyrok++;
                            }
                            obiektDOKjsfSel.add(tmp);
                        }
                    }
                }
                iloscsrodkow = obiektDOKjsfSel.size();
                for (SrodekTrw tmpx : obiektDOKjsfSel) {
                    String m = wpisView.getMiesiacWpisu();
                    Integer r = wpisView.getRokWpisu();
                    obiektDOKmrjsfSel.add(tmpx);
                }
                //sortowanie dokumentów

                //
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
                        for (int iX = mOdI; iX <= mDoI; iX++) {
                            obiektDOKmrjsfSelX.add(tmpx);
                        }
                    }
                }
            }
        }
        /**
         * to co bylo w amodok
         */
        if (wpisView.getPodatnikWpisu() != null) {
            List<Amodok> c = new ArrayList<>();
            try {
                c = amoDokDAO.amodokklient(wpisView.getPodatnikWpisu());
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            if (c != null) {
                int ie = 1;
                for(Amodok tmp : c){
                    tmp.setId(ie++);
                    amodoklist.add(tmp);
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
            Integer mcOd=0;
            if(srodek.getStawka()==100){
                mcOd = Integer.parseInt(srodek.getDataprzek().substring(5, 7));
            } else {
                String pob = srodek.getDataprzek().substring(5, 7);
                mcOd = Integer.parseInt(pob)+1;
                if (mcOd == 13) {
                    rokOd++;
                    mcOd = 1;
                } else {
                    mcOd = Integer.parseInt(srodek.getDataprzek().substring(5, 7))+1;
                }
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
        FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Odpisy wygenerowane. Pamiętaj o wygenerowaniu dokumentów umorzeń! W tym celu wybierz w menu stronę umorzenie","");
        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void generujamodokumenty() {
        List<SrodekTrw> lista = new ArrayList<>();
        lista.addAll(obiektDOKjsfSel);
        String pod = wpisView.getPodatnikWpisu();
        List datagraniczna = new ArrayList<>();
        datagraniczna = sprawdzzaksiegowanedokumenty(pod);
        Integer rokOd = 0;
        Integer mcOd = 0;
        if(datagraniczna.get(0)==0){
            datagraniczna.clear();
            datagraniczna.add(lista.get(0).getUmorzWyk().get(0).getRokUmorzenia());
            datagraniczna.add(lista.get(0).getUmorzWyk().get(0).getMcUmorzenia());
            rokOd = (Integer) datagraniczna.get(0);
            mcOd = (Integer) datagraniczna.get(1);
            amoDokDAO.destroy(pod,rokOd,mcOd);
        } else {
            rokOd = (Integer) datagraniczna.get(0);
            mcOd = (Integer) datagraniczna.get(1);
            amoDokDAO.destroy(pod,rokOd,mcOd);
        }

        Roki roki = new Roki();
        int ostatni = roki.getRokiList().size();
        while (rokOd < roki.getRokiList().get(ostatni-1)) {
            Amodok amoDok = new Amodok();
            amoDok.setPodatnik(pod);
            amoDok.setRok(rokOd);
            amoDok.setMc(mcOd);
            amoDok.setZaksiegowane(Boolean.FALSE);
            for(SrodekTrw srodek : lista){
                List<Umorzenie> umorzeniaWyk = new ArrayList<>();
                umorzeniaWyk.addAll(srodek.getUmorzWyk());
                for(Umorzenie umAkt : umorzeniaWyk){
                    if ((umAkt.getRokUmorzenia().equals(rokOd)) && (umAkt.getMcUmorzenia().equals(mcOd))) {
                        amoDok.getUmorzenia().add(umAkt);
                    } 
                }
              srodek.setUmorzeniezaksiegowane(Boolean.TRUE);
              sTRDAO.edit(srodek);
           }
            if(amoDok.getUmorzenia().isEmpty()){
                amoDok.setZaksiegowane(true);
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
        nowalistadokamo();
        RequestContext.getCurrentInstance().update("formSTR");
        Msg.msg("i", "Dokumenty amortyzacyjne wygenerowane","formSTR:mess_add");
    }
    
    private List sprawdzzaksiegowanedokumenty(String pod) {
        List<Amodok> amodoki = amoDokDAO.amodokklient(pod);
        int rok = 0;
        int mc = 0;
        for(Amodok p : amodoki){
            if(p.getZaksiegowane()==false){
                break;
            }
            rok = p.getRok();
            mc = p.getMc();

        }
        Msg.msg("i", "Pominięto dokumenty zaksięgowane. Aktualizacja po "+rok+"/"+ Mce.getMapamcy().get(mc),"formSTR:mess_add");
        List odpowiedz = new ArrayList<>();
        odpowiedz.add(rok);
        odpowiedz.add(++mc);
        return odpowiedz;
    }

    private void nowalistadokamo(){
        amodoklist.clear();
         if (wpisView.getPodatnikWpisu() != null) {
            List<Amodok> c = new ArrayList<>();
            try {
                c = amoDokDAO.amodokklient(wpisView.getPodatnikWpisu());
            } catch (Exception e) {
                System.out.println("Blad w pobieraniu z bazy danych. Spradzic czy nie pusta, iniekcja oraz  lacze z baza dziala" + e.toString());
            }
            if (c != null) {
                int ie = 1;
                for(Amodok tmp : c){
                    tmp.setId(ie++);
                    amodoklist.add(tmp);
                }

            }
        }
    }
    public void generujTabeleRokBiezacy(){
        List<SrodekTrw> lista = new ArrayList<>();
        lista.addAll(obiektDOKjsfSel);
        int i=1;
        for (SrodekTrw str : lista){
            STRtabela strdocelowy = new STRtabela();
            strdocelowy.setId(i);
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
            try{
            strdocelowy.setUmorzeniaDo(umnar.add(new BigDecimal(str.getUmorzeniepoczatkowe())));
            } catch (Exception e){
                strdocelowy.setUmorzeniaDo(new BigDecimal(BigInteger.ZERO) );
            }
            strtabela.add(strdocelowy);            
            i++;
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
            if(dokdoUsuniecia.isUmorzeniezaksiegowane()==true&&napewnousunac==false)
            {
                throw new Exception();
            }
            obiektDOKjsfSel.remove(dokdoUsuniecia);
            sTRDAO.destroy(dokdoUsuniecia);
             FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Środek trwały usunięty", dokdoUsuniecia.getNazwa().toString());
            FacesContext.getCurrentInstance().addMessage(null, msg);
        } catch (Exception e) {
            Msg.msg("e","Nie usnieto " + dokdoUsuniecia.getNazwa() + ". Umorzenie srodka w ksiegach",":formSTR:mess_add");
            System.out.println("Nie usnieto " + dokdoUsuniecia.getNazwa() + ". Umorzenie srodka w ksiegach");
        }
       
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

    public boolean isNapewnousunac() {
        return napewnousunac;
    }

    public void setNapewnousunac(boolean napewnousunac) {
        STRTabView.napewnousunac = napewnousunac;
    }

    public int getIloscsrodkow() {
        return iloscsrodkow;
    }

    public int getZakupionewbiezacyrok() {
        return zakupionewbiezacyrok;
    }

    public List<Amodok> getAmodoklist() {
        return amodoklist;
    }

    public void setAmodoklist(List<Amodok> amodoklist) {
        this.amodoklist = amodoklist;
    }

    
   
}
