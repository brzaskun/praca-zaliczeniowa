/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import com.sun.xml.rpc.processor.modeler.j2ee.xml.string;
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
    private String TRANSACTION_COMPLETE_DATE;
    private String TRANSACTION_CURRENCY_CODE;
    private String TRANSACTION_EVENT_ID;
    private double TOTAL_ACTIVITY_VALUE_AMT_VAT_EXCL;
    private double TOTAL_ACTIVITY_VALUE_VAT_AMT;
    private double VAT_INV_EXCHANGE_RATE ;
    private String PRICE_OF_ITEMS_VAT_RATE_PERCENT;
    private String ARRIVAL_CITY;
    private String ACTIVITY_TRANSACTION_ID;
    private String SALE_ARRIVAL_COUNTRY;
    private String DEPARTURE_POST_CODE;
    private String VAT_INV_CURRENCY_CODE;

    public AmazonCSV() {
    }

    public AmazonCSV(String[] tmpline) {
        if (tmpline[12].equals("B0195V6AY4")) {
            System.out.println("");
        }
        if (tmpline.length>80) {
            this.TRANSACTION_COMPLETE_DATE = tmpline[10];
            this.TRANSACTION_CURRENCY_CODE = tmpline[52];
            this.TRANSACTION_EVENT_ID = tmpline[5];
            this.ACTIVITY_TRANSACTION_ID = tmpline[6];
            this.TOTAL_ACTIVITY_VALUE_AMT_VAT_EXCL = tmpline[28].isEmpty() ? 0.0 : Double.valueOf(tmpline[28].replace(",", "."));
            this.PRICE_OF_ITEMS_VAT_RATE_PERCENT = tmpline[29];
            this.TOTAL_ACTIVITY_VALUE_VAT_AMT = tmpline[41].isEmpty() ? 0.0 : Double.valueOf(tmpline[41].replace(",", "."));
            this.VAT_INV_EXCHANGE_RATE = tmpline[84].isEmpty() ? 0.0 : Double.valueOf(tmpline[84].replace(",", "."));
            this.ARRIVAL_CITY = tmpline[63];
            this.SALE_ARRIVAL_COUNTRY = tmpline[67];
            this.VAT_INV_CURRENCY_CODE = tmpline[83];
            this.DEPARTURE_POST_CODE = tmpline[62];
        } else {
            this.TRANSACTION_COMPLETE_DATE = tmpline[10];
            this.TRANSACTION_CURRENCY_CODE = tmpline[52];
            this.TRANSACTION_EVENT_ID = tmpline[5];
            this.ACTIVITY_TRANSACTION_ID = tmpline[6];
            this.TOTAL_ACTIVITY_VALUE_AMT_VAT_EXCL = tmpline[44].isEmpty() ? 0.0 : Double.valueOf(tmpline[44].replace(",", "."));
            this.PRICE_OF_ITEMS_VAT_RATE_PERCENT = tmpline[29];
            this.TOTAL_ACTIVITY_VALUE_VAT_AMT = tmpline[45].isEmpty() ? 0.0 : Double.valueOf(tmpline[45].replace(",", "."));
            this.VAT_INV_EXCHANGE_RATE = 0.0;
            this.ARRIVAL_CITY = tmpline[63];
            this.SALE_ARRIVAL_COUNTRY = tmpline[67];
            this.VAT_INV_CURRENCY_CODE = "";
            this.DEPARTURE_POST_CODE = tmpline[62];
        }
    }

    
  
   
    public String getOrderID() {
        return this.TRANSACTION_EVENT_ID;
    }

    public String getShipmentID() {
        return this.ACTIVITY_TRANSACTION_ID;
    }

    public String getTaxRate() {
        return this.PRICE_OF_ITEMS_VAT_RATE_PERCENT;
    }
    
    public String getData() {
        String data = "blad konwersji daty";
        if (this.TRANSACTION_COMPLETE_DATE!=null) {
            data = this.TRANSACTION_COMPLETE_DATE;
        }
        return data;
    }
    public Waluty getWaluta(List<Waluty> listaWalut, Waluty walutapln) {
        Waluty waluta = walutapln;
        for (Waluty p : listaWalut) {
            if (this.TRANSACTION_CURRENCY_CODE.equals(p.getSymbolwaluty())) {
                waluta = p;
            }
        };
        return waluta;
    }
    
    public String getAdress() {
        return this.SALE_ARRIVAL_COUNTRY+", "+this.DEPARTURE_POST_CODE+" "+this.ARRIVAL_CITY;
    }
    
    public double getNetto() {
        double netto = 0.0;
        netto += this.TOTAL_ACTIVITY_VALUE_AMT_VAT_EXCL;
        if (this.VAT_INV_EXCHANGE_RATE!=0.0) {
            netto = Z.z(this.VAT_INV_EXCHANGE_RATE*netto);
        }
        return Z.z(netto);
    }
    
    public Double getNettowaluta() {
        double netto = 0.0;
        netto += this.TOTAL_ACTIVITY_VALUE_AMT_VAT_EXCL;
        return Z.z(netto);
    }
    
    public double getVat() {
        double netto = 0.0;
        netto += this.TOTAL_ACTIVITY_VALUE_VAT_AMT;
        return Z.z(netto);
    }
    
    public double getVatWaluta() {
        double netto = 0.0;
        netto += this.TOTAL_ACTIVITY_VALUE_VAT_AMT;
        if (this.VAT_INV_EXCHANGE_RATE!=0.0) {
            netto = Z.z(this.VAT_INV_EXCHANGE_RATE*netto);
        }
        return Z.z(netto);
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
