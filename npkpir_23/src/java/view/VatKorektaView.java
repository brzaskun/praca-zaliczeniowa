/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import beansDok.ListaEwidencjiVat;
import beansPodpis.ObslugaPodpisuBean;
import beansVAT.EwidencjaVATSporzadzanie;
import beansVAT.VATDeklaracja;
import comparator.Rodzajedokcomparator;
import comparator.Vatcomparator;
import dao.DeklaracjaVatSchemaDAO;
import dao.DeklaracjevatDAO;
import dao.EvewidencjaDAO;
import dao.EvpozycjaDAO;
import dao.PodatnikDAO;
import dao.RodzajedokDAO;
import dao.VATDeklaracjaKorektaDokDAO;
import dao.WniosekVATZDEntityDAO;
import deklaracjaVAT7_13.VAT713;
import embeddable.EVatwpisSuma;
import embeddable.EwidencjaAddwiad;
import embeddable.Kwartaly;
import embeddable.Parametr;
import embeddable.PozycjeSzczegoloweVAT;
import embeddable.TKodUS;
import embeddable.VatKorektaDok;
import embeddable.Vatpoz;
import entity.DeklaracjaVatSchema;
import entity.Deklaracjevat;
import entity.EVatwpisSuper;
import entity.Evewidencja;
import entity.Evpozycja;
import entity.Podatnik;
import entity.Rodzajedok;
import entity.VATDeklaracjaKorektaDok;
import entity.WniosekVATZDEntity;
import error.E;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import msg.Msg;
import org.joda.time.DateTime;
import org.primefaces.context.RequestContext;
import params.Params;
import pdf.PdfVATKorektaReczna;
import serialclone.SerialClone;

/**
 *
 * @author Osito
 */
@ManagedBean
@ViewScoped
public class VatKorektaView implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @Inject
    private Deklaracjevat deklaracjaVAT;
    @Inject
    private Deklaracjevat deklaracjaVATPoKorekcie;
    @Inject
    private DeklaracjevatDAO deklaracjevatDAO;
    @Inject
    private EvewidencjaDAO evewidencjaDAO;
    @Inject
    private EvpozycjaDAO evpozycjaDAO;
    @Inject
    private VATDeklaracjaKorektaDokDAO vATDeklaracjaKorektaDokDAO;
    @Inject
    private PozycjeSzczegoloweVAT pozycjeSzczegoloweVATKorekta;
    @Inject
    private TKodUS tKodUS;
    @Inject
    private ListaEwidencjiVat listaEwidencjiVat;
    @Inject
    private VATDeklaracjaKorektaDok vATDeklaracjaKorektaDok;
    @Inject
    private RodzajedokDAO rodzajedokDAO;
    @Inject
    private PodatnikDAO podatnikDAO;
    @Inject
    private DeklaracjaVatSchemaDAO deklaracjaVatSchemaDAO;
    private boolean pokazFormularze;
    private Integer nowaWartoscVatZPrzeniesienia;
    private boolean pole70zreki;
    private List<Deklaracjevat> deklaracjeWyslane;
    private List<Rodzajedok> rodzajedokKlienta;
    private List<VatKorektaDok> listadokumentowDoKorekty;
    @Inject
    private VatKorektaDok vatKorektaDok;
    @ManagedProperty(value = "#{WpisView}")
    private WpisView wpisView;
    @Inject
    private WniosekVATZDEntityDAO wniosekVATZDEntityDAO;
    private WniosekVATZDEntity wniosekVATZDEntity;

    public VatKorektaView() {
        deklaracjeWyslane = new ArrayList<>();
        rodzajedokKlienta = new ArrayList<>();
        listadokumentowDoKorekty = new ArrayList<>();
    }

    @PostConstruct
    private void init() {
        try {
            deklaracjeWyslane = deklaracjevatDAO.findDeklaracjeWyslane200(wpisView.getPodatnikWpisu(), wpisView.getRokWpisuSt());
            Podatnik podatnik = wpisView.getPodatnikObiekt();
            List<Rodzajedok> rodzajedokumentow = rodzajedokDAO.findListaPodatnik(wpisView.getPodatnikObiekt());
            ArrayList<Rodzajedok> rodzajedokumentowFilter = new ArrayList<>();
            Collections.sort(rodzajedokumentow, new Rodzajedokcomparator());
            for (Rodzajedok p : rodzajedokumentow) {
                List opisewidencji = new ArrayList<>();
                opisewidencji.addAll(listaEwidencjiVat.pobierzEvewidencje(p.getRodzajtransakcji()));
                if (!opisewidencji.isEmpty()) {
                    rodzajedokumentowFilter.add(p);
                }
            }
            rodzajedokKlienta.addAll(rodzajedokumentowFilter);
        } catch (Exception e) { E.e(e); 
        }
        Collections.sort(deklaracjeWyslane, new Vatcomparator());
        List<WniosekVATZDEntity> wniosekVATZDEntityList = wniosekVATZDEntityDAO.findByPodatnikRokMcFK(wpisView);
        if(wniosekVATZDEntityList!=null && wniosekVATZDEntityList.size()>0) {
            wniosekVATZDEntity = wniosekVATZDEntityList.get(0);
        }
    }

    public void podepnijEwidencjeVat() {
        vatKorektaDok = new VatKorektaDok();
        String skrotRT = (String) Params.params("akordeon:wprowadzDokument:rodzajTrans");
        String transakcjiRodzaj = "";
        for (Rodzajedok temp : rodzajedokKlienta) {
            if (temp.getSkrotNazwyDok().equals(skrotRT)) {
                transakcjiRodzaj = temp.getRodzajtransakcji();
                break;
            }
        }
        /*wyswietlamy ewidencje VAT*/
        List<Evewidencja> ewidencje = new ArrayList<>();
        ewidencje.addAll(listaEwidencjiVat.pobierzEvewidencje(transakcjiRodzaj));
        List<EwidencjaAddwiad> ewidencja = new ArrayList<>();
        int k = 0;
        for (Evewidencja p : ewidencje) {
            EwidencjaAddwiad ewidencjaAddwiad = new EwidencjaAddwiad();
            ewidencjaAddwiad.setLp(k++);
            ewidencjaAddwiad.setEvewidencja(p);
            ewidencjaAddwiad.setNetto(0.0);
            ewidencjaAddwiad.setVat(0.0);
            ewidencjaAddwiad.setBrutto(0.0);
            ewidencjaAddwiad.setOpzw("op");
            ewidencja.add(ewidencjaAddwiad);
        }
        vatKorektaDok.setEwidencjaVAT(ewidencja);
    }
    
    public void wyliczbrutto(EwidencjaAddwiad e) {
        int lp = e.getLp();
        vatKorektaDok.getEwidencjaVAT().get(lp).setBrutto(e.getNetto() + e.getVat());
        String update = "akordeon:wprowadzDokument:tablicavat:" + lp + ":brutto";
        RequestContext.getCurrentInstance().update(update);
        String activate = "document.getElementById('akordeon:wprowadzDokument:tablicavat:" + lp + ":brutto_input').select();";
        RequestContext.getCurrentInstance().execute(activate);
    }
    
    public void wybranoDeklaracje(){
        if (deklaracjaVAT instanceof Deklaracjevat) {
            pokazFormularze = true;
            Msg.msg(String.format("Wybrano deklaracje nr: %s. Wpisz dokumenty koryguj\u0105ce", deklaracjaVAT));
        }
        
    }
    
    public void dodajDok(){
        if (deklaracjaVAT != null) {
            double netto = 0.0;
            double vat = 0.0;
            for (EwidencjaAddwiad p : vatKorektaDok.getEwidencjaVAT()) {
                netto += p.getNetto();
                vat += p.getVat();
            }
            vatKorektaDok.setId(listadokumentowDoKorekty.size()+1);
//            vatKorektaDok.setIdDeklaracji(deklaracjaVAT.getId());
            vatKorektaDok.setNetto(netto);
            vatKorektaDok.setVat(vat);
            vatKorektaDok.setBrutto(netto+vat);
            listadokumentowDoKorekty.add(vatKorektaDok);
            Msg.msg("Dodano dokument "+vatKorektaDok.getNrwłasny());
            vatKorektaDok = new VatKorektaDok();
        } else {
            Msg.msg("Nie wybrano deklaracji");
        }
    }
    
    public void usunDok(VatKorektaDok vatKorektaDok) {
        listadokumentowDoKorekty.remove(vatKorektaDok);
        Msg.msg("Usunięto dokument "+vatKorektaDok.getNrwłasny());
    }
    
    public void przetworzListeVatKorektaDok() {
        vATDeklaracjaKorektaDok.setDeklaracjaPierwotna(deklaracjaVAT);
        vATDeklaracjaKorektaDok.setNowaWartoscVatZPrzeniesienia(nowaWartoscVatZPrzeniesienia);
        vATDeklaracjaKorektaDok.setListadokumentowDoKorekty(listadokumentowDoKorekty);
        List<EVatwpisSuper> listadokvatprzetworzona = new ArrayList<>();
        /**
         * Sporzadza i przeksztalca dokumenty korekty w ewidencje vat
         */
        EwidencjaVATSporzadzanie.transferujDokdoEVatwpis(listadokumentowDoKorekty, listadokvatprzetworzona, wpisView.getRokWpisuSt() , wpisView.getMiesiacWpisu());
        HashMap<String, List<EVatwpisSuper>> listaewidencji = new HashMap<>();
        HashMap<String, EVatwpisSuma> sumaewidencjiPierwotna = new HashMap<>();
        EwidencjaVATSporzadzanie.rozdzielEVatwpisNaEwidencje(listadokvatprzetworzona, listaewidencji, sumaewidencjiPierwotna, evewidencjaDAO);
        ArrayList<EVatwpisSuma> ewidencjeUzupelniane = new ArrayList<>(sumaewidencjiPierwotna.values());
        /**
         * pobiera stowrzone ewidencje i robi pozycje szczegolowe korekta zeby wyswietlic
         */
        VATDeklaracja.duplikujZapisyDlaTransakcji(ewidencjeUzupelniane);
        Evpozycja evpozycja = evpozycjaDAO.find("Nabycie towarów i usług pozostałych");
        VATDeklaracja.agregacjaEwidencjiZakupowych5152(ewidencjeUzupelniane,evpozycja);
        VATDeklaracja.przyporzadkujPozycjeSzczegolowe(ewidencjeUzupelniane, pozycjeSzczegoloweVATKorekta, nowaWartoscVatZPrzeniesienia);
        /**
         * tworze nowa deklaracje kopiujac stara binarnie
         */
        deklaracjaVATPoKorekcie = SerialClone.clone(deklaracjaVAT);
        HashMap<String, EVatwpisSuma> sumaewidencjiNowakorekta = deklaracjaVATPoKorekcie.getPodsumowanieewidencji();
        /**
         * sklejam dwie ewidencje: z pierwotnej deklaracji i z korekt
         */
        EwidencjaVATSporzadzanie.dodajDoEwidencjiPozycjeKorekt(sumaewidencjiNowakorekta, sumaewidencjiPierwotna, evewidencjaDAO);
        /**
         * ze sklejonej ewidencji robie pozycje szczegolowe nowej deklaracji
         */
        ewidencjeUzupelniane.clear();
        ewidencjeUzupelniane.addAll(sumaewidencjiNowakorekta.values());
        VATDeklaracja.duplikujZapisyDlaTransakcji(ewidencjeUzupelniane);
        VATDeklaracja.agregacjaEwidencjiZakupowych5152(ewidencjeUzupelniane,evpozycja);
        VATDeklaracja.przyporzadkujPozycjeSzczegolowe(ewidencjeUzupelniane, deklaracjaVATPoKorekcie.getPozycjeszczegolowe(), nowaWartoscVatZPrzeniesienia);
        /**
         * robie podsumowanie szczegolowych oraz uzupelniam pozycje nowej deklaracji, usuwam jakies statusy i wpisy
         */
        if (pole70zreki == true) {
            deklaracjaVATPoKorekcie.getPozycjeszczegolowe().setPole70("1");
            deklaracjaVATPoKorekcie.getPozycjeszczegolowe().setPoleI70(1);
        }
        VATDeklaracja.podsumujSzczegolowe(deklaracjaVATPoKorekcie.getPozycjeszczegolowe());
        deklaracjaVATPoKorekcie.setIdentyfikator("");
        deklaracjaVATPoKorekcie.setUpo("");
        deklaracjaVATPoKorekcie.setStatus("");
        deklaracjaVATPoKorekcie.setOpis("");
        deklaracjaVATPoKorekcie.setDataupo(null);
        deklaracjaVATPoKorekcie.setDatazlozenia(null);
//        deklaracjaVATPoKorekcie.setId(null);
        deklaracjaVATPoKorekcie.setPodsumowanieewidencji(sumaewidencjiNowakorekta);
        deklaracjaVATPoKorekcie.setOrdzu("Korekta");
        deklaracjaVATPoKorekcie.setVatzt(null);
        deklaracjaVATPoKorekcie.setVatzz(null);
        Vatpoz pozycjeDeklaracjiVAT = deklaracjaVATPoKorekcie.getSelected();
        pozycjeDeklaracjiVAT.setPozycjeszczegolowe(deklaracjaVATPoKorekcie.getPozycjeszczegolowe());
        pozycjeDeklaracjiVAT.setCelzlozenia("2");
        pozycjeDeklaracjiVAT.setPodatnik(wpisView.getPodatnikWpisu());
        pozycjeDeklaracjiVAT.setKodurzedu(tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy()));
        pozycjeDeklaracjiVAT.setNazwaurzedu(wpisView.getPodatnikObiekt().getUrzadskarbowy());
        pozycjeDeklaracjiVAT.setAdres(VATDeklaracja.uzupelnijAdres(wpisView.getPodatnikObiekt()));
        pozycjeDeklaracjiVAT.setKwotaautoryzacja(pobierzKwoteAutoryzujaca());
        /**
         * tu stwarzam ten wiersz gdzie faktycznie jest budowany Strig deklaracji
         */
        String vatokres = sprawdzjakiokresvat();
        String mckw = wpisView.getMiesiacWpisu();
        if (!vatokres.equals("miesięczne")) {
            mckw = Kwartaly.getMapamcMcwkw().get(wpisView.getMiesiacWpisu());
        }
        List<DeklaracjaVatSchema> schemyLista = deklaracjaVatSchemaDAO.findAll();
        DeklaracjaVatSchema pasujacaSchema = VATDeklaracja.odnajdzscheme(vatokres, wpisView.getRokWpisuSt(), mckw, schemyLista);
        boolean vatzd = wniosekVATZDEntity!=null;
        stworzdeklaracje(pozycjeDeklaracjiVAT, deklaracjaVATPoKorekcie, pasujacaSchema, vatzd);
        int czyjestcosdowysylki = czynieczekajuzcosdowyslania();
        if (czyjestcosdowysylki == 0) {
            if (vatzd) {
                dodajzalacznikVATZD(deklaracjaVATPoKorekcie);
                wniosekVATZDEntity.setDeklaracjevat(deklaracjaVATPoKorekcie);
                deklaracjaVATPoKorekcie.setWniosekVATZDEntity(wniosekVATZDEntity);
            }
            deklaracjevatDAO.dodaj(deklaracjaVATPoKorekcie);
            vATDeklaracjaKorektaDok.setDeklaracjaKorekta(deklaracjaVATPoKorekcie);
            vATDeklaracjaKorektaDokDAO.dodaj(vATDeklaracjaKorektaDok);
            if (vatzd) {
                wniosekVATZDEntityDAO.edit(wniosekVATZDEntity);
            }
            deklaracjaVATPoKorekcie.setVatDeklaracjaKorektaDokWykaz(vATDeklaracjaKorektaDok);
            deklaracjevatDAO.edit(deklaracjaVATPoKorekcie);
            Msg.msg("i", "Zachowano nową deklaracje VAT");
        }
    }
    
    public void dodajzalacznikVATZD(Deklaracjevat temp) {
        String podatnik = wpisView.getPodatnikWpisu();
        String zalacznik;
        String trescdeklaracji = temp.getDeklaracja();
        //pozbywamy sie koncowki </ns:Deklaracja> ale szukamy wpierw czy isteje juz inny zalacznik
        int lastIndexOf = trescdeklaracji.lastIndexOf("</Zalaczniki>");
        if (lastIndexOf == -1) {
            zalacznik = "<Zalaczniki>"+wniosekVATZDEntity.getZalacznik()+"</Zalaczniki>";
            lastIndexOf = trescdeklaracji.lastIndexOf("<podp:DaneAutoryzujace");
            if (lastIndexOf==-1) {
                lastIndexOf = trescdeklaracji.lastIndexOf("</Deklaracja>");
            }
        } else {
            zalacznik = wniosekVATZDEntity.getZalacznik();
        }
        String koncowka = trescdeklaracji.substring(lastIndexOf);
        trescdeklaracji = trescdeklaracji.substring(0, lastIndexOf);
        //zalaczamy zalacznik
        trescdeklaracji = trescdeklaracji+zalacznik;
        //dodajemy usuniete zakonczenie
        trescdeklaracji = trescdeklaracji+koncowka;
        temp.setDeklaracja(trescdeklaracji);
        try{
            Msg.msg("i","Sukces, załączono VAT-ZD.");
        } catch (Exception e) { E.e(e); 
            Msg.msg("e","Wystapil błąd. Nie udało się załączyć VAT-ZD.");
        }
    }
    
    private String sprawdzjakiokresvat() {
        Integer rok = wpisView.getRokWpisu();
        Integer mc = Integer.parseInt(wpisView.getMiesiacWpisu());
        List<Parametr> parametry = wpisView.getPodatnikObiekt().getVatokres();
        return ParametrView.zwrocParametr(parametry, rok, mc);
    }
    private int czynieczekajuzcosdowyslania(){
        try{
            Deklaracjevat badana = deklaracjevatDAO.findDeklaracjeDowyslania(deklaracjaVATPoKorekcie.getPodatnik());
            if(badana.getStatus().isEmpty()){
                Msg.msg("e", "Wcześniej sporządzona deklaracja nie jest wyslana. Przerywam sporządzanie tej deklaracji!");
                return 1;
            }
        } catch (Exception e) { E.e(e); 
                return 0;   
        }
        return 0;
    }
    
    private void stworzdeklaracje(Vatpoz pozycje, Deklaracjevat nowadeklaracja, DeklaracjaVatSchema schema, boolean vatzd) {
        VAT713 vat713 = null;
        try {
            if (ObslugaPodpisuBean.moznaPodpisac()) {
                vat713 = new VAT713(pozycje, schema, true, vatzd);
            } else {
                vat713 = new VAT713(pozycje, schema, false, vatzd);
            }
            String wiersz = vat713.getWiersz();
            nowadeklaracja.setDeklaracja(wiersz);
            Msg.msg("Stworzono deklaracje korekte");
        } catch (Exception ex) {
            Msg.msg("Wystąpił błąd podczas tworzenia deklaracji korekty.");
        }
        
    }

    public String pobierzKwoteAutoryzujaca() {
        String kwotaautoryzujaca = null;
        String kodus = tKodUS.getMapaUrzadKod().get(wpisView.getPodatnikObiekt().getUrzadskarbowy());
        try {
            boolean equals = kodus.isEmpty();
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Brak wpisanego urzędu skarbowego!", "form:msg");
        }
        try {
            List<Parametr> listakwotaautoryzujaca = wpisView.getPodatnikObiekt().getKwotaautoryzujaca();
            if(listakwotaautoryzujaca.isEmpty()){
                throw new Exception();
            }
            //bo wazny jet nie rok na deklaracji ale rok z ktorego sie wysyla
            DateTime datawysylki = new DateTime();
            String rokwysylki = String.valueOf(datawysylki.getYear());
            for (Parametr par : listakwotaautoryzujaca) {
                if (par.getRokOd().equals(rokwysylki)) {
                    kwotaautoryzujaca = par.getParametr();
                    break;
                }
            }
        } catch (Exception e) { E.e(e); 
            Msg.msg("e", "Wystapil blad, brak kwoty autoryzujacej w ustawieniach!", "form:msg");
        }
        return kwotaautoryzujaca;
    }

    public void drukujkorektareczna() {
        try {
            PdfVATKorektaReczna.drukuj(vATDeklaracjaKorektaDok, wpisView, podatnikDAO);
        } catch (Exception e) { E.e(e); 
            
        }
    }
    public List<Deklaracjevat> getDeklaracjeWyslane() {
        return deklaracjeWyslane;
    }

    public void setDeklaracjeWyslane(List<Deklaracjevat> deklaracjeWyslane) {
        this.deklaracjeWyslane = deklaracjeWyslane;
    }

    public WpisView getWpisView() {
        return wpisView;
    }

    public void setWpisView(WpisView wpisView) {
        this.wpisView = wpisView;
    }

    public VatKorektaDok getVatKorektaDok() {
        return vatKorektaDok;
    }

    public void setVatKorektaDok(VatKorektaDok vatKorektaDok) {
        this.vatKorektaDok = vatKorektaDok;
    }

    public List<Rodzajedok> getRodzajedokKlienta() {
        return rodzajedokKlienta;
    }

    public void setRodzajedokKlienta(List<Rodzajedok> rodzajedokKlienta) {
        this.rodzajedokKlienta = rodzajedokKlienta;
    }

    public Deklaracjevat getDeklaracjaVAT() {
        return deklaracjaVAT;
    }

    public void setDeklaracjaVAT(Deklaracjevat deklaracjaVAT) {
        this.deklaracjaVAT = deklaracjaVAT;
    }

    public List<VatKorektaDok> getListadokumentowDoKorekty() {
        return listadokumentowDoKorekty;
    }

    public void setListadokumentowDoKorekty(List<VatKorektaDok> listadokumentowDoKorekty) {
        this.listadokumentowDoKorekty = listadokumentowDoKorekty;
    }

    public PozycjeSzczegoloweVAT getPozycjeSzczegoloweVATKorekta() {
        return pozycjeSzczegoloweVATKorekta;
    }

    public void setPozycjeSzczegoloweVATKorekta(PozycjeSzczegoloweVAT pozycjeSzczegoloweVATKorekta) {
        this.pozycjeSzczegoloweVATKorekta = pozycjeSzczegoloweVATKorekta;
    }

    public Deklaracjevat getDeklaracjaVATPoKorekcie() {
        return deklaracjaVATPoKorekcie;
    }

    public void setDeklaracjaVATPoKorekcie(Deklaracjevat deklaracjaVATPoKorekcie) {
        this.deklaracjaVATPoKorekcie = deklaracjaVATPoKorekcie;
    }

    public Integer getNowaWartoscVatZPrzeniesienia() {
        return nowaWartoscVatZPrzeniesienia;
    }

    public void setNowaWartoscVatZPrzeniesienia(Integer nowaWartoscVatZPrzeniesienia) {
        this.nowaWartoscVatZPrzeniesienia = nowaWartoscVatZPrzeniesienia;
    }

    public boolean isPokazFormularze() {
        return pokazFormularze;
    }

    public void setPokazFormularze(boolean pokazFormularze) {
        this.pokazFormularze = pokazFormularze;
    }

    public VATDeklaracjaKorektaDok getvATDeklaracjaKorektaDok() {
        return vATDeklaracjaKorektaDok;
    }

    public void setvATDeklaracjaKorektaDok(VATDeklaracjaKorektaDok vATDeklaracjaKorektaDok) {
        this.vATDeklaracjaKorektaDok = vATDeklaracjaKorektaDok;
    }

    public boolean isPole70zreki() {
        return pole70zreki;
    }

    public void setPole70zreki(boolean pole70zreki) {
        this.pole70zreki = pole70zreki;
    }

    
    
}
