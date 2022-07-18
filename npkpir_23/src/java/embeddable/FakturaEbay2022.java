/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

/**
 *
 * @author Osito
 */
public class FakturaEbay2022 {
    private String Rechnungsdatum;
    private String NamedesKäufers;
    private String PLZ;
    private String OrtdesKäufers;
    private String Käuferadresse1;
    private String NIP;

    public FakturaEbay2022(String[] tmpline) {
        this.Rechnungsdatum = tmpline[0];
        this.NamedesKäufers = tmpline[0];
        this.PLZ = tmpline[0];
        this.OrtdesKäufers = tmpline[0];
        this.Käuferadresse1 = tmpline[0];
        this.NIP = tmpline[0];
    }
    
    
    
//    public String getWaluta() {
//        return this.getVerpackungundVersand().substring(1,4);
//    }
//    
//    public double getNetto() {
//        return Z.z(this.getBrutto()-this.getVAT());
//    }
//    
//    public double getVAT() {
////        if (this.VAT_recznie!=null) {
////            return this.VAT_recznie.doubleValue();
////        } else {
////            double brutto = this.getBrutto();
////            return Z.z(brutto*this.getStawka()/(100+this.getStawka()));
////        }
//    }
//    
//     public double getBrutto() {
////        return Double.parseDouble(getGesamtpreis());
//    }
//     
//     private String pobierzNIPkontrahenta(FakturaEbay wiersz) {
////        String nip = "";
////        if (wiersz.getNIP()!=null) {
////            nip = nip+wiersz.getNIP();
////        }
////        return nip;
//    }

    public String getRechnungsdatum() {
        return Rechnungsdatum;
    }

    public void setRechnungsdatum(String Rechnungsdatum) {
        this.Rechnungsdatum = Rechnungsdatum;
    }

    public String getNamedesKäufers() {
        return NamedesKäufers;
    }

    public void setNamedesKäufers(String NamedesKäufers) {
        this.NamedesKäufers = NamedesKäufers;
    }

    public String getPLZ() {
        return PLZ;
    }

    public void setPLZ(String PLZ) {
        this.PLZ = PLZ;
    }

    public String getOrtdesKäufers() {
        return OrtdesKäufers;
    }

    public void setOrtdesKäufers(String OrtdesKäufers) {
        this.OrtdesKäufers = OrtdesKäufers;
    }

    public String getKäuferadresse1() {
        return Käuferadresse1;
    }

    public void setKäuferadresse1(String Käuferadresse1) {
        this.Käuferadresse1 = Käuferadresse1;
    }
     
     
}
