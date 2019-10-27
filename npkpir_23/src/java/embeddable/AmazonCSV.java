/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import data.Data;
import entityfk.Waluty;
import java.util.Date;
import java.util.List;
import waluty.Z;

/**
 *
 * @author Osito
 */
public class AmazonCSV {
    private String MerchantID;
    private String OrderDate;
    private String TransactionType;
    private String OrderID;
    private String ShipmentDate;
    private String ShipmentID;
    private String TransactionID;
    private String TaxCalculationDate;
    private String TaxRate;
    private String Currency;
    private String TaxCalculationReasonCode;
    private String TaxAddressRole;
    private String JurisdictionName;
    private double OUR_PRICETaxInclusiveSellingPrice;
    private double OUR_PRICETaxAmount;
    private double OUR_PRICETaxExclusiveSellingPrice;
    private double SHIPPINGTaxInclusiveSellingPrice;
    private double SHIPPINGTaxAmount;
    private double SHIPPINGTaxExclusiveSellingPrice;
    private String SellerTaxRegistration;
    private String SellerTaxRegistrationJurisdiction;
    private String BuyerTaxRegistration;
    private String BuyerTaxRegistrationJurisdiction;
    private String InvoiceLevelCurrencyCode;
    private double InvoiceLevelExchangeRate ;
    private String InvoiceLevelExchangeRateDate;
    private double ConvertedTaxAmount;
    private String VATInvoiceNumber;
    private String ShipToCity;
    private String ShipToCountry;
    private String ShipToPostalCode;

    public AmazonCSV() {
    }

    
  
    public AmazonCSV(String[] tmpline) {
        this.MerchantID = tmpline[1];
        this.OrderDate = tmpline[2];
        this.TransactionType = tmpline[3];
        this.OrderID = tmpline[4];
        this.ShipmentDate = tmpline[5];
        this.ShipmentID = tmpline[6];
        this.TransactionID = tmpline[7];
        this.TaxCalculationDate = tmpline[11];
        this.TaxRate = tmpline[12];
        this.Currency = tmpline[14];
        this.TaxCalculationReasonCode = tmpline[16];
        this.TaxAddressRole = tmpline[17];
        this.JurisdictionName = tmpline[19];
        this.OUR_PRICETaxInclusiveSellingPrice = Double.valueOf(tmpline[20].replace(",", "."));
        this.OUR_PRICETaxAmount = Double.valueOf(tmpline[21].replace(",", "."));
        this.OUR_PRICETaxExclusiveSellingPrice = Double.valueOf(tmpline[22].replace(",", "."));
        this.SHIPPINGTaxInclusiveSellingPrice = Double.valueOf(tmpline[26].replace(",", "."));
        this.SHIPPINGTaxAmount = Double.valueOf(tmpline[27].replace(",", "."));
        this.SHIPPINGTaxExclusiveSellingPrice = Double.valueOf(tmpline[28].replace(",", "."));
        this.SellerTaxRegistration = tmpline[38];
        this.SellerTaxRegistrationJurisdiction = tmpline[39];
        this.BuyerTaxRegistration = tmpline[40];
        this.BuyerTaxRegistrationJurisdiction = tmpline[41];
        this.InvoiceLevelCurrencyCode = tmpline[43];
        this.InvoiceLevelExchangeRate = tmpline[44].equals("") ? 0.0 :Double.valueOf(tmpline[44].replace(",", "."));
        this.InvoiceLevelExchangeRateDate = tmpline[45];
        this.ConvertedTaxAmount = tmpline[47].equals("") ? 0.0 : Double.valueOf(tmpline[47].replace(",", "."));
        this.VATInvoiceNumber = tmpline[48];
        this.ShipToCity = tmpline[56];
        this.ShipToCountry = tmpline[53];
        this.ShipToPostalCode = tmpline[59];
    }
    
    public String getData() {
        String d2 = "blad konwersji daty";
        if (this.ShipmentDate!=null) {
            String strypdate = this.ShipmentDate.subSequence(1, this.ShipmentDate.length()-1).toString();
            Date date = new Date(strypdate);
            d2 = Data.data_yyyyMMdd(date);
        }
        return d2;
    }
    public Waluty getWaluta(List<Waluty> listaWalut, Waluty walutapln) {
        Waluty waluta = walutapln;
        for (Waluty p : listaWalut) {
            if (this.Currency.equals(p.getSymbolwaluty())) {
                waluta = p;
            }
        };
        return waluta;
    }
    
    public String getAdress() {
        return this.ShipToCountry+", "+this.ShipToPostalCode+" "+this.ShipToCity;
    }
    
    public double getNetto() {
        double netto = 0.0;
        netto += this.getOUR_PRICETaxExclusiveSellingPrice()+this.getSHIPPINGTaxExclusiveSellingPrice();
        if (this.InvoiceLevelExchangeRate!=0.0) {
            netto = Z.z(this.InvoiceLevelExchangeRate*netto);
        }
        return Z.z(netto);
    }
    
    public Double getNettowaluta() {
        double netto = 0.0;
        netto += this.getOUR_PRICETaxExclusiveSellingPrice()+this.getSHIPPINGTaxExclusiveSellingPrice();
        return Z.z(netto);
    }
    
    public double getVat() {
        double vat = 0.0;
        vat += this.getOUR_PRICETaxAmount()+this.getSHIPPINGTaxAmount();
        if (this.InvoiceLevelExchangeRate!=0.0) {
            vat = Z.z(this.InvoiceLevelExchangeRate*vat);
        }
        return Z.z(vat);
    }
    
    public double getVatWaluta() {
        double vat = 0.0;
        vat += this.getOUR_PRICETaxAmount()+this.getSHIPPINGTaxAmount();
        return Z.z(vat);
    }

    public String getMerchantID() {
        return MerchantID;
    }

    public void setMerchantID(String MerchantID) {
        this.MerchantID = MerchantID;
    }

    public String getOrderDate() {
        return OrderDate;
    }

    public void setOrderDate(String OrderDate) {
        this.OrderDate = OrderDate;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public void setTransactionType(String TransactionType) {
        this.TransactionType = TransactionType;
    }

    public String getOrderID() {
        return OrderID;
    }

    public void setOrderID(String OrderID) {
        this.OrderID = OrderID;
    }

    public String getShipmentID() {
        return ShipmentID;
    }

    public void setShipmentID(String ShipmentID) {
        this.ShipmentID = ShipmentID;
    }

    public String getShipmentDate() {
        return ShipmentDate;
    }

    public void setShipmentDate(String ShipmentDate) {
        this.ShipmentDate = ShipmentDate;
    }

    public String getTransactionID() {
        return TransactionID;
    }

    public void setTransactionID(String TransactionID) {
        this.TransactionID = TransactionID;
    }

    public String getTaxCalculationDate() {
        return TaxCalculationDate;
    }

    public void setTaxCalculationDate(String TaxCalculationDate) {
        this.TaxCalculationDate = TaxCalculationDate;
    }

    public String getTaxRate() {
        return TaxRate;
    }
    
     public double getTaxRateD() {
        String va= TaxRate.replace(",", ".");
        double dab = Double.valueOf(va);
        return dab;
    }

    public void setTaxRate(String TaxRate) {
        this.TaxRate = TaxRate;
    }

    public String getCurrency() {
        return Currency;
    }

    public void setCurrency(String Currency) {
        this.Currency = Currency;
    }

    public String getTaxCalculationReasonCode() {
        return TaxCalculationReasonCode;
    }

    public void setTaxCalculationReasonCode(String TaxCalculationReasonCode) {
        this.TaxCalculationReasonCode = TaxCalculationReasonCode;
    }

    public String getTaxAddressRole() {
        return TaxAddressRole;
    }

    public void setTaxAddressRole(String TaxAddressRole) {
        this.TaxAddressRole = TaxAddressRole;
    }

    public String getJurisdictionName() {
        return JurisdictionName;
    }

    public void setJurisdictionName(String JurisdictionName) {
        this.JurisdictionName = JurisdictionName;
    }

    public double getOUR_PRICETaxInclusiveSellingPrice() {
        return OUR_PRICETaxInclusiveSellingPrice;
    }

    public void setOUR_PRICETaxInclusiveSellingPrice(double OUR_PRICETaxInclusiveSellingPrice) {
        this.OUR_PRICETaxInclusiveSellingPrice = OUR_PRICETaxInclusiveSellingPrice;
    }

    public double getOUR_PRICETaxAmount() {
        return OUR_PRICETaxAmount;
    }

    public void setOUR_PRICETaxAmount(double OUR_PRICETaxAmount) {
        this.OUR_PRICETaxAmount = OUR_PRICETaxAmount;
    }

    public double getOUR_PRICETaxExclusiveSellingPrice() {
        return OUR_PRICETaxExclusiveSellingPrice;
    }

    public void setOUR_PRICETaxExclusiveSellingPrice(double OUR_PRICETaxExclusiveSellingPrice) {
        this.OUR_PRICETaxExclusiveSellingPrice = OUR_PRICETaxExclusiveSellingPrice;
    }

    public double getSHIPPINGTaxInclusiveSellingPrice() {
        return SHIPPINGTaxInclusiveSellingPrice;
    }

    public void setSHIPPINGTaxInclusiveSellingPrice(double SHIPPINGTaxInclusiveSellingPrice) {
        this.SHIPPINGTaxInclusiveSellingPrice = SHIPPINGTaxInclusiveSellingPrice;
    }

    public double getSHIPPINGTaxAmount() {
        return SHIPPINGTaxAmount;
    }

    public void setSHIPPINGTaxAmount(double SHIPPINGTaxAmount) {
        this.SHIPPINGTaxAmount = SHIPPINGTaxAmount;
    }

    public double getSHIPPINGTaxExclusiveSellingPrice() {
        return SHIPPINGTaxExclusiveSellingPrice;
    }

    public void setSHIPPINGTaxExclusiveSellingPrice(double SHIPPINGTaxExclusiveSellingPrice) {
        this.SHIPPINGTaxExclusiveSellingPrice = SHIPPINGTaxExclusiveSellingPrice;
    }

    public String getSellerTaxRegistration() {
        return SellerTaxRegistration;
    }

    public void setSellerTaxRegistration(String SellerTaxRegistration) {
        this.SellerTaxRegistration = SellerTaxRegistration;
    }

    public String getSellerTaxRegistrationJurisdiction() {
        return SellerTaxRegistrationJurisdiction;
    }

    public void setSellerTaxRegistrationJurisdiction(String SellerTaxRegistrationJurisdiction) {
        this.SellerTaxRegistrationJurisdiction = SellerTaxRegistrationJurisdiction;
    }

    public String getBuyerTaxRegistration() {
        return BuyerTaxRegistration;
    }

    public void setBuyerTaxRegistration(String BuyerTaxRegistration) {
        this.BuyerTaxRegistration = BuyerTaxRegistration;
    }

    public String getBuyerTaxRegistrationJurisdiction() {
        return BuyerTaxRegistrationJurisdiction;
    }

    public void setBuyerTaxRegistrationJurisdiction(String BuyerTaxRegistrationJurisdiction) {
        this.BuyerTaxRegistrationJurisdiction = BuyerTaxRegistrationJurisdiction;
    }

    public String getInvoiceLevelCurrencyCode() {
        return InvoiceLevelCurrencyCode;
    }

    public void setInvoiceLevelCurrencyCode(String InvoiceLevelCurrencyCode) {
        this.InvoiceLevelCurrencyCode = InvoiceLevelCurrencyCode;
    }

    public double getInvoiceLevelExchangeRate() {
        return InvoiceLevelExchangeRate;
    }

    public void setInvoiceLevelExchangeRate(double InvoiceLevelExchangeRate) {
        this.InvoiceLevelExchangeRate = InvoiceLevelExchangeRate;
    }

    public String getInvoiceLevelExchangeRateDate() {
        return InvoiceLevelExchangeRateDate;
    }

    public void setInvoiceLevelExchangeRateDate(String InvoiceLevelExchangeRateDate) {
        this.InvoiceLevelExchangeRateDate = InvoiceLevelExchangeRateDate;
    }

    public double getConvertedTaxAmount() {
        return ConvertedTaxAmount;
    }

    public void setConvertedTaxAmount(double ConvertedTaxAmount) {
        this.ConvertedTaxAmount = ConvertedTaxAmount;
    }

    public String getVATInvoiceNumber() {
        return VATInvoiceNumber;
    }

    public void setVATInvoiceNumber(String VATInvoiceNumber) {
        this.VATInvoiceNumber = VATInvoiceNumber;
    }

    public String getShipToCity() {
        return ShipToCity;
    }

    public void setShipToCity(String ShipToCity) {
        this.ShipToCity = ShipToCity;
    }

    public String getShipToCountry() {
        return ShipToCountry;
    }

    public void setShipToCountry(String ShipToCountry) {
        this.ShipToCountry = ShipToCountry;
    }

    public String getShipToPostalCode() {
        return ShipToPostalCode;
    }

    public void setShipToPostalCode(String ShipToPostalCode) {
        this.ShipToPostalCode = ShipToPostalCode;
    }

    @Override
    public String toString() {
        return "AmazonCSV{" + "MerchantID=" + MerchantID + ", OrderDate=" + OrderDate + ", TransactionType=" + TransactionType + ", OrderID=" + OrderID + ", ShipmentDate=" + ShipmentDate + ", TransactionID=" + TransactionID + ", TaxCalculationDate=" + TaxCalculationDate + ", TaxRate=" + TaxRate + ", Currency=" + Currency + ", TaxCalculationReasonCode=" + TaxCalculationReasonCode + ", TaxAddressRole=" + TaxAddressRole + ", JurisdictionName=" + JurisdictionName + ", OUR_PRICETaxInclusiveSellingPrice=" + OUR_PRICETaxInclusiveSellingPrice + ", OUR_PRICETaxAmount=" + OUR_PRICETaxAmount + ", OUR_PRICETaxExclusiveSellingPrice=" + OUR_PRICETaxExclusiveSellingPrice + ", SHIPPINGTaxInclusiveSellingPrice=" + SHIPPINGTaxInclusiveSellingPrice + ", SHIPPINGTaxAmount=" + SHIPPINGTaxAmount + ", SHIPPINGTaxExclusiveSellingPrice=" + SHIPPINGTaxExclusiveSellingPrice + ", SellerTaxRegistration=" + SellerTaxRegistration + ", SellerTaxRegistrationJurisdiction=" + SellerTaxRegistrationJurisdiction + ", BuyerTaxRegistration=" + BuyerTaxRegistration + ", BuyerTaxRegistrationJurisdiction=" + BuyerTaxRegistrationJurisdiction + ", InvoiceLevelCurrencyCode=" + InvoiceLevelCurrencyCode + ", InvoiceLevelExchangeRate=" + InvoiceLevelExchangeRate + ", InvoiceLevelExchangeRateDate=" + InvoiceLevelExchangeRateDate + ", ConvertedTaxAmount=" + ConvertedTaxAmount + ", VATInvoiceNumber=" + VATInvoiceNumber + ", ShipToCity=" + ShipToCity + ", ShipToCountry=" + ShipToCountry + ", ShipToPostalCode=" + ShipToPostalCode + '}';
    }
    
    
    
public static void main(String[] args) {
    String data = "21-Apr-2018 UTC";
    Date date = new Date(data);
    String d2 = Data.data_yyyyMMdd(date);
    System.out.println("data "+d2);
//        String csvFile = "E:\\Biuro\\Firmy\\_MAŁGOSIA\\Cieślak Paweł\\amazonVAT.csv";
//        String line = "";
//        String cvsSplitBy = ",";
//
//        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(csvFile), "windows-1252"))) {
//            while ((line = br.readLine()) != null) {
//                try {
//                    // use comma as separator
//                    String[] tmpline = line.split(cvsSplitBy);
//                    AmazonCSV amazonCSV = new AmazonCSV(tmpline);
//                    System.out.println(amazonCSV.toString());
//                } catch (Exception ex){
//                }
//            }
//            br.close();
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

  }

    

    

    
    
    
}
