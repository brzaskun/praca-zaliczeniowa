/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package xls;

/**
 *
 * @author Osito
 */
public class GenerujDok {
    
//     public int generowanieDokumentu(InterpaperXLS interpaperXLS, List<Klienci> k, boolean zakup0sprzedaz, boolean towar0usluga1, boolean firmy0indycentalni1, WpisView wpisView) {
//        int ile = 0;
//        try {
//            int polska0unia1zagranica2 = 0;
//            if (interpaperXLS.getKlient().getKrajnazwa()!=null && !interpaperXLS.getKlient().getKrajkod().equals("PL")) {
//                polska0unia1zagranica2 = 2;
//                if (PanstwaEUSymb.getWykazPanstwUE().contains(interpaperXLS.getKlient().getKrajkod())) {
//                    if (interpaperXLS.getVatPLN()!=0.0) {
//                        polska0unia1zagranica2 = 0;
//                    } else {
//                        polska0unia1zagranica2 = 1;
//                    }
//                }
//            }
//            String rodzajdk = "ZZ";
//            Dokfk dokument = null;
//            if (zakup0sprzedaz) {
//                if (towar0usluga1) {
//                    rodzajdk = polska0unia1zagranica2==0 ? "SZ" : polska0unia1zagranica2==1 ? "UPTK100" : "UPTK";
//                } else {
//                    rodzajdk = polska0unia1zagranica2==0 ? "SZ" : polska0unia1zagranica2==1 ? "WDT" : "EXP";
//                }
//                dokument = stworznowydokument(oblicznumerkolejny(rodzajdk),interpaperXLS, rodzajdk, k, "przychody ze sprzedaży");
//            } else {
//                if (towar0usluga1) {
//                    rodzajdk = polska0unia1zagranica2==0 ? "ZZ" : "IU";
//                    if (interpaperXLS.getVatPLN()!=0.0 && !interpaperXLS.getKlientpaństwo().equals("Polska")) {
//                        rodzajdk = "RACH";
//                    }
//                } else {
//                    rodzajdk = polska0unia1zagranica2==0 ? "ZZ" : "WNT";
//                    if (interpaperXLS.getVatPLN()!=0.0 && !interpaperXLS.getKlientpaństwo().equals("Polska")) {
//                        rodzajdk = "RACH";
//                    }
//                }
//                dokument = stworznowydokument(oblicznumerkolejny(rodzajdk),interpaperXLS, rodzajdk, k, "zakup towarów/koszty");
//            }
//            
//            try {
//                if (dokument!=null) {
//                    dokument.setImportowany(true);
//                    dokDAOfk.create(dokument);
//                    interpaperXLS.setSymbolzaksiegowanego(dokument.getDokfkSN());
//                    ile = 1;
//                }
//            } catch (Exception e) {
//                ile = 0;
//                Msg.msg("e", "Wystąpił błąd - nie zaksięgowano dokumentu "+rodzajdok);
//            }
//        } catch (Exception e) {
//            ile = 0;
//            E.e(e);
//        }
//        return ile;
//    }
//     
//     private Dok generujdok(InterpaperXLS wiersz, WpisView wpisView, boolean firmy0indycentalni1, String dokopis, String rodzajdok, RodzajedokDAO  rodzajedokDAO, String opis) {
//        Dok selDokument = new Dok();
//        try {
//            HttpServletRequest request;
//            request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
//            Principal principal = request.getUserPrincipal();
//            selDokument.setWprowadzil(principal.getName());
//            String datawystawienia = Data.data_yyyyMMdd(wiersz.getDatawystawienia());
//            String miesiac = Data.getMc(datawystawienia);
//            String rok = Data.getRok(datawystawienia);
//            selDokument.setPkpirM(miesiac);
//            selDokument.setPkpirR(rok);
//            selDokument.setVatM(miesiac);
//            selDokument.setVatR(rok);
//            selDokument.setPodatnik(wpisView.getPodatnikObiekt());
//            selDokument.setStatus("bufor");
//            selDokument.setUsunpozornie(false);
//            selDokument.setDataWyst(datawystawienia);
//            selDokument.setDataSprz(Data.data_yyyyMMdd(wiersz.getDatasprzedaży()));
//            selDokument.setKontr(pobierzkontrahenta(wiersz, firmy0indycentalni1));
//            selDokument.setRodzajedok(pobierzrodzajrok(rodzajdok, rodzajedokDAO, wpisView.getPodatnikObiekt(), wpisView.getRokWpisuSt()));
//            selDokument.setOpis(opis);
//            selDokument.setNrWlDk(wiersz.getShipmentID());
//            List<KwotaKolumna1> listaX = Collections.synchronizedList(new ArrayList<>());
//            KwotaKolumna1 tmpX = new KwotaKolumna1();
//            tmpX.setNetto(wiersz.getNetto());
//            tmpX.setVat(wiersz.getVat());
//            tmpX.setNettowaluta(wiersz.getNettowaluta());
//            tmpX.setVatwaluta(wiersz.getVatWaluta());
//            tmpX.setNazwakolumny("przych. sprz");
//            tmpX.setDok(selDokument);
//            tmpX.setBrutto(Z.z(Z.z(wiersz.getNetto()+wiersz.getVat())));
//            listaX.add(tmpX);
//            String symbolwalt = "EUR";
//            if (selDokument.getAmazonCSV()!=null) {
//                //error.E.s(selDokument.getAmazonCSV().getCurrency());
//                symbolwalt = selDokument.getAmazonCSV().getCurrency();
//            }
//            Tabelanbp innatabela = pobierztabele(symbolwalt, selDokument.getDataWyst());
//            selDokument.setTabelanbp(innatabela);
//            Tabelanbp walutadok = pobierztabele(wiersz.getCurrency(), selDokument.getDataWyst());
//            selDokument.setWalutadokumentu(walutadok.getWaluta());
//            selDokument.setListakwot1(listaX);
//            selDokument.setNetto(tmpX.getNetto());
//            selDokument.setBrutto(tmpX.getBrutto());
//            selDokument.setRozliczony(true);
//            List<EVatwpis1> ewidencjaTransformowana = Collections.synchronizedList(new ArrayList<>());
//            EVatwpis1 eVatwpis1 = new EVatwpis1(pobierzewidencje(wiersz,evewidencje), wiersz.getNetto(), wiersz.getVat(), "sprz.op", miesiac, rok);
//            eVatwpis1.setDok(selDokument);
//            ewidencjaTransformowana.add(eVatwpis1);
//            selDokument.setEwidencjaVAT1(ewidencjaTransformowana);
//            if (selDokument.getKontr()!=null && sprawdzCzyNieDuplikat(selDokument)!=null) {
//                selDokument = null;
//            }
//        } catch (Exception e) {
//            E.e(e);
//        }
//        return selDokument;
//    }
//     
//
//     private Tabelanbp pobierztabele(String waldok, String dataWyst) {
//        DateTime dzienposzukiwany = new DateTime(dataWyst);
//        return TabelaNBPBean.pobierzTabeleNBP(dzienposzukiwany, tabelanbpDAO, waldok);
//    }
//    
//     private Klienci pobierzkontrahenta(InterpaperXLS wiersz, boolean firmy0indycentalni1) {
//        if (wybierzosobyfizyczne || wybierzfirmyzagraniczne) {
//           Klienci inc = new Klienci();
//           inc.setNip(nrKontrahenta);
//           inc.setNpelna(nazwa!=null ? nazwa: "brak nazwy indycentalnego");
//           inc.setAdresincydentalny(adres!=null ? adres: "brak adresu indycentalnego");
//           return inc;
//        } else {
//            Klienci klientznaleziony = klDAO.findKlientByNipImport(nrKontrahenta);
//            if (klientznaleziony==null) {
//                klientznaleziony = SzukajDaneBean.znajdzdaneregonAutomat(nrKontrahenta);
//                if (klientznaleziony!=null && klientznaleziony.getNpelna()!=null) {
//                    boolean juzjest = false;
//                    for (Klienci p : klienci) {
//                        if (p.getNip()!=null && p.getNip().equals(klientznaleziony.getNip())) {
//                            juzjest = true;
//                            break;
//                        }
//                    }
//                    if (juzjest==false) {
//                        klienci.add(klientznaleziony);
//                    }
//                } else if (klientznaleziony!=null){
//                     klientznaleziony.setNskrocona(klientznaleziony.getNpelna());
//                     klientznaleziony.setNpelna("nie znaleziono firmy w bazie Regon");
//                }
//            } else if (klientznaleziony==null||klientznaleziony.getNpelna()==null) {
//                klientznaleziony.setNpelna("istnieje wielu kontrahentów o tym samym numerze NIP! "+nrKontrahenta);
//            }
//            return klientznaleziony;
//        }
//    }
//    
//    private Evewidencja pobierzewidencje(double netto, double vat, List<Evewidencja> evewidencje) {
//        Evewidencja zwrot = null;
//        double stawka = obliczstawke(netto, vat);
//        for (Evewidencja p : evewidencje) {
//            if (p.getStawkavat()==stawka) {
//                zwrot = p;
//                break;
//            }
//        }
//        return zwrot;
//    }
//    
//    private double obliczstawke(double netto, double vat) {
//        double stawka = 23;
//        double procent = Z.z4(vat/netto);
//        if (procent>0.18) {
//            stawka = 23;
//        } else if (procent>0.07) {
//            stawka = 8;
//        }  else if (procent>0.04) {
//            stawka = 5;
//        } else {
//            stawka = 0;
//        }
//        return stawka;
//    }
//    
//     public Dok sprawdzCzyNieDuplikat(Dok selD) {
//        if (selD.getKontr().getNpelna().equals("OPTEGRA POLSKA sp. z o.o.")) {
//            error.E.s("");
//        }
//        Dok tmp = null;
//        tmp = dokDAO.znajdzDuplikatwtrakcie(selD, wpisView.getPodatnikObiekt(), selD.getRodzajedok().getSkrot());
//        return tmp;
//    }
//
//    private Rodzajedok pobierzrodzajrok(String rodzajdok, RodzajedokDAO rodzajedokDAO, Podatnik podatnik, String rok) {
//        return rodzajedokDAO.find(rodzajdok, podatnik, rok);
//    }
}
