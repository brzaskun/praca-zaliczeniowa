/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import data.Data;
import entityfk.Waluty;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.csv.CSVRecord;
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
        int i = 0;
        try {
            this.OUR_PRICETaxInclusiveSellingPrice = Double.valueOf(tmpline[20].replace(",", "."));
        } catch (Exception e) {
            i =1;
        }
        this.MerchantID = tmpline[1];
        this.OrderDate = tmpline[2];
        this.TransactionType = tmpline[3];
        this.OrderID = tmpline[4+i];
        this.ShipmentDate = tmpline[5+i];
        this.ShipmentID = tmpline[6+i];
        this.TransactionID = tmpline[7+i];
        this.TaxCalculationDate = tmpline[11+i];
        this.TaxRate = tmpline[12+i];
        this.Currency = tmpline[14+i];
        this.TaxCalculationReasonCode = tmpline[16+i];
        this.TaxAddressRole = tmpline[17+i];
        this.JurisdictionName = tmpline[19+i];
        this.OUR_PRICETaxInclusiveSellingPrice = Double.valueOf(tmpline[20+i].replace(",", "."));
        this.OUR_PRICETaxAmount = Double.valueOf(tmpline[21+i].replace(",", "."));
        this.OUR_PRICETaxExclusiveSellingPrice = Double.valueOf(tmpline[22+i].replace(",", "."));
        this.SHIPPINGTaxInclusiveSellingPrice = Double.valueOf(tmpline[26+i].replace(",", "."));
        this.SHIPPINGTaxAmount = Double.valueOf(tmpline[27+i].replace(",", "."));
        this.SHIPPINGTaxExclusiveSellingPrice = Double.valueOf(tmpline[28+i].replace(",", "."));
        this.SellerTaxRegistration = tmpline[38+i];
        this.SellerTaxRegistrationJurisdiction = tmpline[39+i];
        this.BuyerTaxRegistration = tmpline[40+i];
        this.BuyerTaxRegistrationJurisdiction = tmpline[41+i];
        this.InvoiceLevelCurrencyCode = tmpline[43+i];
        this.InvoiceLevelExchangeRate = tmpline[44+i].equals("") ? 0.0 :Double.valueOf(tmpline[44+i].replace(",", "."));
        this.InvoiceLevelExchangeRateDate = tmpline[45+i];
        this.ConvertedTaxAmount = tmpline[47+i].equals("") ? 0.0 : Double.valueOf(tmpline[47+i].replace(",", "."));
        this.VATInvoiceNumber = tmpline[48+i];
        this.ShipToCity = tmpline[56+i];
        this.ShipToCountry = tmpline[53+i];
        this.ShipToPostalCode = tmpline[59+i];
    }

    public AmazonCSV(CSVRecord record) {
        int i = 0;
        this.MerchantID = record.get("Merchant ID");
        this.OrderDate = record.get("Order Date");
        this.TransactionType = record.get("Transaction Type");
        this.OrderID = record.get("Order ID");
        this.ShipmentDate = record.get("Shipment Date");
        this.ShipmentID = record.get("Shipment ID");
        this.TransactionID = record.get("Transaction ID");
        this.TaxCalculationDate = record.get("Tax Calculation Date");
        this.TaxRate = record.get("Tax Rate");
        this.Currency = record.get("Currency");
        this.TaxCalculationReasonCode = record.get("Tax Calculation Reason Code");
        this.TaxAddressRole = record.get("Tax Address Role");
        this.JurisdictionName = record.get("Jurisdiction Name");
        this.OUR_PRICETaxInclusiveSellingPrice = Double.valueOf(record.get("OUR_PRICE Tax Inclusive Selling Price").replace(",", "."));
        this.OUR_PRICETaxAmount = Double.valueOf(record.get("OUR_PRICE Tax Amount").replace(",", "."));
        this.OUR_PRICETaxExclusiveSellingPrice = Double.valueOf(record.get("OUR_PRICE Tax Exclusive Selling Price").replace(",", "."));
        this.SHIPPINGTaxInclusiveSellingPrice = Double.valueOf(record.get("SHIPPING Tax Inclusive Selling Price").replace(",", "."));
        this.SHIPPINGTaxAmount = Double.valueOf(record.get("SHIPPING Tax Amount").replace(",", "."));
        this.SHIPPINGTaxExclusiveSellingPrice = Double.valueOf(record.get("SHIPPING Tax Exclusive Selling Price").replace(",", "."));
        this.SellerTaxRegistration = record.get("Seller Tax Registration");
        this.SellerTaxRegistrationJurisdiction = record.get("Seller Tax Registration Jurisdiction");
        this.BuyerTaxRegistration = record.get("Buyer Tax Registration");
        this.BuyerTaxRegistrationJurisdiction = record.get("Buyer Tax Registration Jurisdiction");
        this.InvoiceLevelCurrencyCode = record.get("Invoice Level Currency Code");
        this.InvoiceLevelExchangeRate = record.get("Invoice Level Exchange Rate").equals("") ? 0.0 :Double.valueOf(record.get("Invoice Level Exchange Rate").replace(",", "."));
        this.InvoiceLevelExchangeRateDate = record.get("Invoice Level Exchange Rate Date");
        this.ConvertedTaxAmount = record.get("Converted Tax Amount").equals("") ? 0.0 : Double.valueOf(record.get("Converted Tax Amount").replace(",", "."));
        this.VATInvoiceNumber = record.get("VAT Invoice Number");
        this.ShipToCity = record.get("Ship To City");
        this.ShipToCountry = record.get("Ship To Country");
        this.ShipToPostalCode = record.get("Ship To Postal Code");
    }
    
    
    public String getData() {
        String d2 = "blad konwersji daty";
        if (this.ShipmentDate!=null) {
            String strypdate = this.ShipmentDate.subSequence(1, this.ShipmentDate.length()-1).toString();
            //"2-Jan-2021 UT" to trzeba przekonwertowac
//               DateFormat formatter;
//            formatter = new SimpleDateFormat("yyyy-MM-dd");
//            Date date = null;
//            try {
//                date = formatter.parse(strypdate);
//            } catch (ParseException ex) {
//                Logger.getLogger(AmazonCSV.class.getName()).log(Level.SEVERE, null, ex);
//            }
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
    DateFormat formatter;
    formatter = new SimpleDateFormat("yyyy-MM-dd");
    Date date = null;
    try {
        date = formatter.parse(data);
    } catch (ParseException ex) {
        Logger.getLogger(AmazonCSV.class.getName()).log(Level.SEVERE, null, ex);
    }
    String d2 = Data.data_yyyyMMdd(date);
    error.E.s("data "+d2);
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
//                    error.E.s(amazonCSV.toString());
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
