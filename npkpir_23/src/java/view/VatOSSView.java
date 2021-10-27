/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import dao.PodsumowanieAmazonOSSDAO;
import data.Data;
import embeddable.Kwartaly;
import embeddable.TKodUS;
import entity.PodsumowanieAmazonOSS;
import error.E;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.transform.stream.StreamResult;
import msg.Msg;
import pl.gov.crd.wzor._2021._08._03._08031.Deklaracja;
import pl.gov.crd.wzor._2021._08._03._08031.MSCONSupplyType;
import pl.gov.crd.wzor._2021._08._03._08031.PozycjeSzczegolowe;
import pl.gov.crd.wzor._2021._08._03._08031.TKodFormularza;
import pl.gov.crd.wzor._2021._08._03._08031.TNaglowek;
import pl.gov.crd.wzor._2021._08._03._08031.VATReturnMSCONType;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._03._02.ed.kodycechkrajow.MSCurrCodeType;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._03._12.ed.definicjetypyoss.EurAmountPositiveType;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._03._12.ed.definicjetypyoss.OSSVATReturnDetailType;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._03._12.ed.definicjetypyoss.OSSVATReturnDetailsType;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._03._12.ed.definicjetypyoss.PeriodSplitType;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._03._12.ed.definicjetypyoss.SupplyType;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._03._12.ed.definicjetypyoss.TIdentyfikatorOsobyNiefizycznejMoss;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._03._12.ed.definicjetypyoss.VATRateType;
import pl.gov.crd.xml.schematy.dziedzinowe.mf._2021._03._12.ed.definicjetypyoss.VATRateTypeType;

/**
 *
 * @author Osito
 */
@Named(value = "vatOSSView")
@ViewScoped
public class VatOSSView  implements Serializable {
    
    private List<PodsumowanieAmazonOSS> lista;
    private List<Deklaracja> deklaracjeOSS_biezace;
    @Inject
    private PodsumowanieAmazonOSSDAO podsumowanieAmazonOSSDAO;
    @Inject
    private WpisView wpisView;
    @Inject
    private TKodUS tKodUS;
    private pl.gov.crd.wzor._2021._08._03._08031.Deklaracja deklaracja;
    private pl.gov.crd.wzor._2021._08._03._08031.Deklaracja deklossselected;
    
    public void init() {
        lista = podsumowanieAmazonOSSDAO.findByPodatnikRokMc(wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt(), wpisView.getMiesiacWpisu());
    }
    
    public void tworzdeklaracje(int test0wysylka1) {
        boolean ok = robdeklaracje(lista, false, 0, test0wysylka1);
    }

     public boolean robdeklaracje(List<PodsumowanieAmazonOSS> lista, boolean korekta, int nrkolejny, int test0wysylka1) {
        boolean zwrot = false;
        try {
            List zwrotdekl = sporzadz(lista, korekta);
            String deklaracja = (String) zwrotdekl.get(0);
            Object dekl_object = zwrotdekl.get(1);
            //Object[] walidacja = XMLValid.walidujCMLVATUE(deklaracja,dekl_object, 0);
//            if (walidacja!=null && walidacja[0]==Boolean.TRUE) {
//                if (test0wysylka1==0) {
//                    String name = Plik.zapiszplik("vatue"+wpisView.getPodatnikObiekt().getNip(), "xml", deklaracja.getBytes(java.nio.charset.Charset.forName("UTF-8")));
//                    PrimeFaces.current().executeScript("pokazwydrukpdf('"+name+"')");
//                    Msg.msg("Wygenerowano deklarację do podglądu");
//                } else {
//                    Object[] podpisanadeklaracja = podpiszDeklaracje(deklaracja);
//                    if (podpisanadeklaracja != null) {
//                        DeklaracjavatUE deklaracjavatUE = generujdeklaracje(podpisanadeklaracja);
//                        deklaracjavatUE.setNrkolejny(nrkolejny);
//                        for (VatUe p : lista) {
//                            p.setDeklaracjavatUE(deklaracjavatUE);
//                        }
//                        deklaracjavatUE.setPozycje(lista);
//                        //deklaracjavatUEDAO.create(deklaracjavatUE); dodamy ja przy wysylce bo wtedy robimy edit dok
//                        deklaracjeUE.add(deklaracjavatUE);
//                        deklaracjeOSS_biezace.add(deklaracjavatUE);
//                        Msg.msg("Sporządzono deklarację VAT-UE miesięczną");
//                        zwrot = true;
//                    } else {
//                        Msg.msg("e","Wystąpił błąd. Niesporządzono deklaracji VAT-UE. Sprawdź czy włożono kartę z podpisem! Sprawdź oznaczenia krajów i NIP-y");
//                    }
//                }
//            } else {
//                String name = Plik.zapiszplik("vatue"+wpisView.getPodatnikObiekt().getNip(), "xml", deklaracja.getBytes(java.nio.charset.Charset.forName("UTF-8")));
//                PrimeFaces.current().executeScript("pokazwydrukpdf('"+name+"')");
//                Msg.msg("Wygenerowano deklarację dla sprawdzenia błędów");
//                Msg.msg("e", "Sprawdź oznaczenia krajów i NIP-y!");
//                Msg.msg("e", (String) walidacja[1]);
//                Msg.msg("e","Wystąpił błąd. Niesporządzono deklaracji VAT-UE. Sprawdź czy włożono kartę z podpisem! Sprawdź oznaczenia krajów i NIP-y");
//            }
        } catch (Exception e) {
            Msg.msg("e","Wystąpił błąd. Niesporządzono deklaracji VAT-UE miesięczną wersja. Sprawdź parametry podatnika!");
        }
        return zwrot;
    }
    
    private List<pl.gov.crd.wzor._2021._08._03._08031.Deklaracja> sporzadz(List<PodsumowanieAmazonOSS> lista, boolean korekta) {
         List zwrot = new ArrayList<>();
         pl.gov.crd.wzor._2021._08._03._08031.Deklaracja deklaracja = new Deklaracja();
         deklaracja.setNaglowek(robnaglowek(korekta, "1436"));
         deklaracja.setPodmiot1(robpodmiot());
         deklaracja.setPozycjeSzczegolowe(pozycjeszczegolowe(lista));
         deklaracja.setPouczenie1("Za podanie nieprawdy lub zatajenie prawdy i przez to narażenie podatku na uszczuplenie grozi odpowiedzialność przewidziana w Kodeksie karnym skarbowym.");
         deklaracja.setPouczenie2("W przypadku niewpłacenia w obowiązującym terminie kwoty podatku VAT należnej Rzeczpospolitej Polskiej lub wpłacenia jej w niepełnej wysokości, niniejsza deklaracja stanowi podstawę do wystawienia tytułu wykonawczego, "
                 + "zgodnie z art. 3a § 1 pkt 1 ustawy z dnia 17 czerwca 1966 r. o postępowaniu egzekucyjnym w administracji (Dz. U. z 2020 r. poz. 1427, z późn. zm.).");
         zwrot.add(marszajuldoStringu(deklaracja, wpisView));
         zwrot.add(deklaracja);
         return zwrot;
    }
     
    public String marszajuldoStringu(pl.gov.crd.wzor._2021._08._03._08031.Deklaracja dekl, WpisView wpisView) {
        StringWriter sw = new StringWriter();
        try {
            JAXBContext context = JAXBContext.newInstance(pl.gov.crd.wzor._2021._08._03._08031.Deklaracja.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, false);
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(dekl, new StreamResult(sw));
        } catch (Exception ex) {
            E.e(ex);
        }
        return sw.toString().replaceAll("\n|\r", "").substring(sw.toString().replaceAll("\n|\r", "").indexOf(">")+1);
    }
  

    private TNaglowek robnaglowek(boolean korekta, String kodurzedu) {
        TNaglowek naglowek = new TNaglowek();
        if (korekta) {
            naglowek.setCelZlozenia((byte)2);
        } else {
            naglowek.setCelZlozenia((byte)1);
        }
        naglowek.setDataWypelnienia(Data.dataStringToXMLGregorian(Data.aktualnaData()));
        naglowek.setKodFormularza(kodformularza());
        naglowek.setKodUrzedu(kodurzedu);
        naglowek.setKwartal(Byte.valueOf(Kwartaly.getMapamckw().get(wpisView.getMiesiacWpisu())));
        naglowek.setRok(Data.XMLGCinitRok(wpisView.getRokWpisuSt()));
        naglowek.setWariantFormularza((byte)1);
        return naglowek;
    }

    private TNaglowek.KodFormularza kodformularza() {
        TNaglowek.KodFormularza kod = new TNaglowek.KodFormularza();
        kod.setKodPodatku(kod.getKodPodatku());
        kod.setKodSystemowy(kod.getKodSystemowy());
        kod.setRodzajZobowiazania(kod.getRodzajZobowiazania());
        kod.setValue(TKodFormularza.VIU_DO);
        kod.setWersjaSchemy(kod.getWersjaSchemy());
        return kod;
    }

    private Deklaracja.Podmiot1 robpodmiot() {
        Deklaracja.Podmiot1 podmiot = new Deklaracja.Podmiot1();
        podmiot.setRola(podmiot.getRola());
        podmiot.setOsobaNiefizyczna(osobaniefizyczna());
        return podmiot;
    }

    private TIdentyfikatorOsobyNiefizycznejMoss osobaniefizyczna() {
        TIdentyfikatorOsobyNiefizycznejMoss osoba = new TIdentyfikatorOsobyNiefizycznejMoss();
        osoba.setNIP(wpisView.getPodatnikObiekt().getNip());
        osoba.setPelnaNazwa(wpisView.getPrintNazwa());
        return osoba;
    }

    private PozycjeSzczegolowe pozycjeszczegolowe(List<PodsumowanieAmazonOSS> lista) {
        PozycjeSzczegolowe poz = new PozycjeSzczegolowe();
        poz.setPeriod(period());
        poz.setVATReturnMSCON(vatreturnmscon(lista));
        return poz;
    }

    
    private PeriodSplitType period() {
        PeriodSplitType period = new PeriodSplitType();
        period.setStartDate(Data.dataStringToXMLGregorian("2021-07-01"));
        period.setEndDate(Data.dataStringToXMLGregorian("2021-09-30"));
        return period;
    }
    
    
    private VATReturnMSCONType vatreturnmscon(List<PodsumowanieAmazonOSS> lista) {
        VATReturnMSCONType vATReturnMSCONType = new VATReturnMSCONType();
        vATReturnMSCONType.setSupplies(supplies(lista));
        return vATReturnMSCONType;
    }

    private MSCONSupplyType supplies(List<PodsumowanieAmazonOSS> lista) {
        MSCONSupplyType mSCONSupplyType = new MSCONSupplyType();
        mSCONSupplyType.getMSIDSupplies().addAll(msidsupplies(lista));
        return mSCONSupplyType;
    }
    
 
    private Collection<? extends MSCONSupplyType.MSIDSupplies> msidsupplies(List<PodsumowanieAmazonOSS> lista) {
        List<MSCONSupplyType.MSIDSupplies> zwrot = new ArrayList<>();
        for (PodsumowanieAmazonOSS p : lista) {
            MSCONSupplyType.MSIDSupplies sup = new MSCONSupplyType.MSIDSupplies();
            sup.setMSCONCountryCode(p.getJurysdykcja());
            sup.setMSIDSupply(ossreturndetail(p));
            zwrot.add(sup);
        }
        return zwrot;
    }

    private OSSVATReturnDetailsType ossreturndetail(PodsumowanieAmazonOSS p) {
        OSSVATReturnDetailsType oss = new OSSVATReturnDetailsType();
        oss.getOSSVATReturnDetail().addAll(detail(p));
        return oss;
    }

    private Collection<? extends OSSVATReturnDetailType> detail(PodsumowanieAmazonOSS p) {
        List<OSSVATReturnDetailType> det = new ArrayList<>();
        OSSVATReturnDetailType r = new OSSVATReturnDetailType();
        r.setSupplyType(SupplyType.GOODS);
        r.setVATRate(vatratetype(p.getVatstawka()));
        r.setTaxableAmount(podstawa(p));
        r.setVATAmount(kwotavat(p));
        det.add(r);
        return det;
    }
    
    
    private VATRateType vatratetype(double vatstawka) {
        VATRateType v = new VATRateType();
        v.setValue(BigDecimal.valueOf(vatstawka*100));
        v.setType(VATRateTypeType.STANDARD);
        return v;
    }
    
     private EurAmountPositiveType podstawa(PodsumowanieAmazonOSS p) {
        EurAmountPositiveType eu = new EurAmountPositiveType();
        eu.setCurrency(MSCurrCodeType.EUR);
        eu.setValue(BigDecimal.valueOf(p.getNettowaluta()));
        return eu;
    }

    private EurAmountPositiveType kwotavat(PodsumowanieAmazonOSS p) {
        EurAmountPositiveType eu = new EurAmountPositiveType();
        eu.setCurrency(MSCurrCodeType.EUR);
        eu.setValue(BigDecimal.valueOf(p.getVatwaluta()));
        return eu;
    }

     
     
   public List<PodsumowanieAmazonOSS> getLista() {
        return lista;
    }

    public void setLista(List<PodsumowanieAmazonOSS> lista) {
        this.lista = lista;
    }      

    public Deklaracja getDeklossselected() {
        return deklossselected;
    }

    public void setDeklossselected(Deklaracja deklossselected) {
        this.deklossselected = deklossselected;
    }

    public List<Deklaracja> getDeklaracjeOSS_biezace() {
        return deklaracjeOSS_biezace;
    }

    public void setDeklaracjeOSS_biezace(List<Deklaracja> deklaracjeOSS_biezace) {
        this.deklaracjeOSS_biezace = deklaracjeOSS_biezace;
    }

    
   
    
    
    
}
