/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import waluty.Z;

/**
 *
 * @author Osito
 */
public class FakturaEbay {
    private String Verkaufsprotokollnummer;
    private String Nutzername;
    private String NamedesKäufers;
    private String EMaildesKäufers;
    private String Käuferadresse1;
    private String Käuferadresse2;
    private String OrtdesKäufers;
    private String BundeslanddesKäufers;
    private String PLZ;
    private String LanddesKäufers;
    private String Bestellnummer;
    private String Artikelnummer;
    private String TransaktionsID;
    private String Artikelbezeichnung;
    private String Stückzahl;
    private String Verkaufspreis;
    private String InklusiveMehrwertsteuersatz;
    private String VerpackungundVersand;
    private String Versicherung;
    private String voneBayeinbehalteneSteuer;
    private String Gesamtpreis;
    private String Zahlungsmethode;
    private String PayPalTransaktionsID;
    private String Rechnungsnummer;
    private String Rechnungsdatum;
    private String Verkaufsdatum;
    private String Kaufabwicklungsdatum;
    private String Bezahltam;
    private String Verschicktam;
    private String Versandservice;
    private String NIP;
    private Double VAT_recznie;

    public FakturaEbay() {
    }

    public FakturaEbay(String[] tmpline, int i) {
        this.Verkaufsprotokollnummer = tmpline[0];
        this.Nutzername = tmpline[1];
        this.NamedesKäufers = tmpline[2];
        this.EMaildesKäufers = tmpline[3];
        this.Käuferadresse1 = tmpline[4];
        this.Käuferadresse2 = tmpline[5];
        this.OrtdesKäufers = tmpline[6];
        this.BundeslanddesKäufers = tmpline[7];
        this.PLZ = tmpline[8];
        this.LanddesKäufers = tmpline[9];
        this.Bestellnummer = tmpline[10];
        this.Artikelnummer = tmpline[11];
        this.TransaktionsID = tmpline[12];
        this.Artikelbezeichnung = tmpline[13];
        this.Stückzahl = tmpline[14];
        this.Verkaufspreis = tmpline[15];
        this.InklusiveMehrwertsteuersatz = tmpline[16];
        this.VerpackungundVersand = tmpline[17];
        this.Versicherung = tmpline[18];
        this.voneBayeinbehalteneSteuer = tmpline[19];
        this.Gesamtpreis = tmpline[20];
        this.Zahlungsmethode = tmpline[21];
        this.PayPalTransaktionsID = tmpline[22];
        this.Rechnungsnummer = tmpline[23];
        this.Rechnungsdatum = tmpline[24];
        this.Verkaufsdatum = tmpline[25];
        this.Kaufabwicklungsdatum = tmpline[26];
        this.Bezahltam = tmpline[27];
        this.Verschicktam = tmpline[28];
        this.Versandservice = tmpline[29];
    }
    

    public String getVerkaufsprotokollnummer() {
        return Verkaufsprotokollnummer;
    }

    public void setVerkaufsprotokollnummer(String Verkaufsprotokollnummer) {
        this.Verkaufsprotokollnummer = Verkaufsprotokollnummer;
    }

    public String getNutzername() {
        return Nutzername;
    }

    public void setNutzername(String Nutzername) {
        this.Nutzername = Nutzername;
    }

    public String getNamedesKäufers() {
        return NamedesKäufers;
    }

    public void setNamedesKäufers(String NamedesKäufers) {
        this.NamedesKäufers = NamedesKäufers;
    }

    public String getEMaildesKäufers() {
        return EMaildesKäufers;
    }

    public void setEMaildesKäufers(String EMaildesKäufers) {
        this.EMaildesKäufers = EMaildesKäufers;
    }

    public String getKäuferadresse1() {
        return Käuferadresse1;
    }

    public void setKäuferadresse1(String Käuferadresse1) {
        this.Käuferadresse1 = Käuferadresse1;
    }

    public String getKäuferadresse2() {
        return Käuferadresse2;
    }

    public void setKäuferadresse2(String Käuferadresse2) {
        this.Käuferadresse2 = Käuferadresse2;
    }

    public String getOrtdesKäufers() {
        return OrtdesKäufers;
    }

    public void setOrtdesKäufers(String OrtdesKäufers) {
        this.OrtdesKäufers = OrtdesKäufers;
    }

    public String getBundeslanddesKäufers() {
        return BundeslanddesKäufers;
    }

    public void setBundeslanddesKäufers(String BundeslanddesKäufers) {
        this.BundeslanddesKäufers = BundeslanddesKäufers;
    }

    public String getPLZ() {
        return PLZ;
    }

    public void setPLZ(String PLZ) {
        this.PLZ = PLZ;
    }

    public String getLanddesKäufers() {
        return LanddesKäufers;
    }

    public void setLanddesKäufers(String LanddesKäufers) {
        this.LanddesKäufers = LanddesKäufers;
    }

    public String getBestellnummer() {
        return Bestellnummer;
    }

    public void setBestellnummer(String Bestellnummer) {
        this.Bestellnummer = Bestellnummer;
    }

    public String getArtikelnummer() {
        return Artikelnummer;
    }

    public void setArtikelnummer(String Artikelnummer) {
        this.Artikelnummer = Artikelnummer;
    }

    public String getTransaktionsID() {
        return TransaktionsID;
    }

    public void setTransaktionsID(String TransaktionsID) {
        this.TransaktionsID = TransaktionsID;
    }

    public String getArtikelbezeichnung() {
        return Artikelbezeichnung;
    }

    public void setArtikelbezeichnung(String Artikelbezeichnung) {
        this.Artikelbezeichnung = Artikelbezeichnung;
    }

    public String getStückzahl() {
        return Stückzahl;
    }

    public void setStückzahl(String Stückzahl) {
        this.Stückzahl = Stückzahl;
    }

    public String getVerkaufspreis() {
        return Verkaufspreis;
    }

    public void setVerkaufspreis(String Verkaufspreis) {
        this.Verkaufspreis = Verkaufspreis;
    }

    public String getInklusiveMehrwertsteuersatz() {
        return InklusiveMehrwertsteuersatz;
    }

    public void setInklusiveMehrwertsteuersatz(String InklusiveMehrwertsteuersatz) {
        this.InklusiveMehrwertsteuersatz = InklusiveMehrwertsteuersatz;
    }

    
    public String getVerpackungundVersand() {
        return VerpackungundVersand;
    }

    public void setVerpackungundVersand(String VerpackungundVersand) {
        this.VerpackungundVersand = VerpackungundVersand;
    }

    public String getVersicherung() {
        return Versicherung;
    }

    public void setVersicherung(String Versicherung) {
        this.Versicherung = Versicherung;
    }

    public String getVoneBayeinbehalteneSteuer() {
        return voneBayeinbehalteneSteuer;
    }

    public void setVoneBayeinbehalteneSteuer(String voneBayeinbehalteneSteuer) {
        this.voneBayeinbehalteneSteuer = voneBayeinbehalteneSteuer;
    }

    public String getGesamtpreis() {
        String zwrot = this.Gesamtpreis;
        if (zwrot!=null) {
            zwrot = zwrot.replace("EUR ", "");
            zwrot = zwrot.replace(",", ".");
            zwrot = zwrot.replace("\"","");
        }
        return zwrot;
    }

    public void setGesamtpreis(String Gesamtpreis) {
        this.Gesamtpreis = Gesamtpreis;
    }

    public String getZahlungsmethode() {
        return Zahlungsmethode;
    }

    public void setZahlungsmethode(String Zahlungsmethode) {
        this.Zahlungsmethode = Zahlungsmethode;
    }

    public String getPayPalTransaktionsID() {
        return PayPalTransaktionsID;
    }

    public void setPayPalTransaktionsID(String PayPalTransaktionsID) {
        this.PayPalTransaktionsID = PayPalTransaktionsID;
    }

    public String getRechnungsnummer() {
        return Rechnungsnummer;
    }

    public void setRechnungsnummer(String Rechnungsnummer) {
        this.Rechnungsnummer = Rechnungsnummer;
    }

    public String getRechnungsdatum() {
        return data.Data.zmienkolejnosc8(Rechnungsdatum);
    }

    public void setRechnungsdatum(String Rechnungsdatum) {
        this.Rechnungsdatum = Rechnungsdatum;
    }

    public String getVerkaufsdatum() {
        return data.Data.zmienkolejnosc8(Verkaufsdatum);
    }

    public void setVerkaufsdatum(String Verkaufsdatum) {
        this.Verkaufsdatum = Verkaufsdatum;
    }

    public String getKaufabwicklungsdatum() {
        return Kaufabwicklungsdatum;
    }

    public void setKaufabwicklungsdatum(String Kaufabwicklungsdatum) {
        this.Kaufabwicklungsdatum = Kaufabwicklungsdatum;
    }

    public String getBezahltam() {
        return data.Data.zmienkolejnosc8(Bezahltam);
    }

    public void setBezahltam(String Bezahltam) {
        this.Bezahltam = Bezahltam;
    }

    public String getVerschicktam() {
        return Verschicktam;
    }

    public void setVerschicktam(String Verschicktam) {
        this.Verschicktam = Verschicktam;
    }

    public String getVersandservice() {
        return Versandservice;
    }

    public void setVersandservice(String Versandservice) {
        this.Versandservice = Versandservice;
    }
    
    public String getWaluta() {
        return this.getVerpackungundVersand().substring(1,4);
    }
    
    public double getNetto() {
        return Z.z(this.getBrutto()-this.getVAT());
    }
    
    public double getVAT() {
        if (this.VAT_recznie!=null) {
            return this.VAT_recznie.doubleValue();
        } else {
            double brutto = this.getBrutto();
            return Z.z(brutto*this.getStawka()/(100+this.getStawka()));
        }
    }
    
    public double getBrutto() {
        return Double.parseDouble(getGesamtpreis());
    }
    
        
        
    public String getNIP() {
        return NIP;
    }

    public void setNIP(String NIP) {
        this.NIP = NIP;
    }
    
    public double getStawka() {
        String zwrot = this.InklusiveMehrwertsteuersatz.replace("%", "");
        if (zwrot.contains("EUR")) {
            System.out.println("");
            zwrot = "0.9";
        }
        return Double.parseDouble(zwrot);
    }
    
    public String getDataTransakcji() {
        String zwrot = data.Data.zmienkolejnosc8(this.Rechnungsdatum);
        if (zwrot.equals("")) {
            zwrot = data.Data.zmienkolejnosc8(this.Verkaufsdatum);
        }
        return zwrot;
    }

    public Double getVAT_recznie() {
        return VAT_recznie;
    }

    public void setVAT_recznie(Double VAT_recznie) {
        this.VAT_recznie = VAT_recznie;
    }

    

   
    
    
}
