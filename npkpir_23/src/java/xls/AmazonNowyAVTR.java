/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package xls;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/**
 *
 * @author Osito
 */
public class AmazonNowyAVTR {
    

    public static List<TransactionRecord> importFromExcel(String filePath) throws IOException {
        List<TransactionRecord> records = new ArrayList<>();
        
        try (FileInputStream fileInputStream = new FileInputStream(filePath);
             Workbook workbook = new XSSFWorkbook(fileInputStream)) {
            
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            
            // Skip header row
            if (rowIterator.hasNext()) {
                rowIterator.next();
            }
            
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                TransactionRecord record = new TransactionRecord();

                record.setUniqueAccountIdentifier(getCellStringValue(row, 0));
                record.setActivityPeriod(getCellStringValue(row, 1));
                record.setSalesChannel(getCellStringValue(row, 2));
                record.setMarketplace(getCellStringValue(row, 3));
                record.setProgramType(getCellStringValue(row, 4));
                record.setTransactionType(getCellStringValue(row, 5));
                record.setTransactionEventId(getCellStringValue(row, 6));
                record.setActivityTransactionId(getCellStringValue(row, 7));
                record.setTaxCalculationDate(getCellStringValue(row, 8));
                record.setTransactionDepartDate(getCellStringValue(row, 9));
                record.setTransactionArrivalDate(getCellStringValue(row, 10));
                record.setTransactionCompleteDate(getCellStringValue(row, 11));
                record.setSellerSku(getCellStringValue(row, 12));
                record.setAsin(getCellStringValue(row, 13));
                record.setItemDescription(getCellStringValue(row, 14));
                record.setItemManufactureCountry(getCellStringValue(row, 15));
                record.setQty(getCellIntegerValue(row, 16));
                record.setItemWeight(getCellDoubleValue(row, 17));
                record.setTotalActivityWeight(getCellDoubleValue(row, 18));
                record.setCostPriceOfItems(getCellDoubleValue(row, 19));
                record.setPriceOfItemsAmtVatExcl(getCellDoubleValue(row, 20));
                record.setPromoPriceOfItemsAmtVatExcl(getCellDoubleValue(row, 21));
                record.setTotalPriceOfItemsAmtVatExcl(getCellDoubleValue(row, 22));
                record.setShipChargeAmtVatExcl(getCellDoubleValue(row, 23));
                record.setPromoShipChargeAmtVatExcl(getCellDoubleValue(row, 24));
                record.setTotalShipChargeAmtVatExcl(getCellDoubleValue(row, 25));
                record.setGiftWrapAmtVatExcl(getCellDoubleValue(row, 26));
                record.setPromoGiftWrapAmtVatExcl(getCellDoubleValue(row, 27));
                record.setTotalGiftWrapAmtVatExcl(getCellDoubleValue(row, 28));
                record.setTotalActivityValueAmtVatExcl(getCellDoubleValue(row, 29));
                record.setPriceOfItemsVatRatePercent(getCellDoubleValue(row, 30));
                record.setPriceOfItemsVatAmt(getCellDoubleValue(row, 31));
                record.setPromoPriceOfItemsVatAmt(getCellDoubleValue(row, 32));
                record.setTotalPriceOfItemsVatAmt(getCellDoubleValue(row, 33));
                record.setShipChargeVatRatePercent(getCellDoubleValue(row, 34));
                record.setShipChargeVatAmt(getCellDoubleValue(row, 35));
                record.setPromoShipChargeVatAmt(getCellDoubleValue(row, 36));
                record.setTotalShipChargeVatAmt(getCellDoubleValue(row, 37));
                record.setGiftWrapVatRatePercent(getCellDoubleValue(row, 38));
                record.setGiftWrapVatAmt(getCellDoubleValue(row, 39));
                record.setPromoGiftWrapVatAmt(getCellDoubleValue(row, 40));
                record.setTotalGiftWrapVatAmt(getCellDoubleValue(row, 41));
                record.setTotalActivityValueVatAmt(getCellDoubleValue(row, 42));
                record.setPriceOfItemsAmtVatIncl(getCellDoubleValue(row, 43));
                record.setPromoPriceOfItemsAmtVatIncl(getCellDoubleValue(row, 44));
                record.setTotalPriceOfItemsAmtVatIncl(getCellDoubleValue(row, 45));
                record.setShipChargeAmtVatIncl(getCellDoubleValue(row, 46));
                record.setPromoShipChargeAmtVatIncl(getCellDoubleValue(row, 47));
                record.setTotalShipChargeAmtVatIncl(getCellDoubleValue(row, 48));
                record.setGiftWrapAmtVatIncl(getCellDoubleValue(row, 49));
                record.setPromoGiftWrapAmtVatIncl(getCellDoubleValue(row, 50));
                record.setTotalGiftWrapAmtVatIncl(getCellDoubleValue(row, 51));
                record.setTotalActivityValueAmtVatIncl(getCellDoubleValue(row, 52));
                record.setTransactionCurrencyCode(getCellStringValue(row, 53));
                record.setCommodityCode(getCellStringValue(row, 54));
                record.setStatisticalCodeDepart(getCellStringValue(row, 55));
                record.setStatisticalCodeArrival(getCellStringValue(row, 56));
                record.setCommodityCodeSupplementaryUnit(getCellStringValue(row, 57));
                record.setItemQtySupplementaryUnit(getCellIntegerValue(row, 58));
                record.setTotalActivitySupplementaryUnit(getCellDoubleValue(row, 59));
                record.setProductTaxCode(getCellStringValue(row, 60));
                record.setDepartureCity(getCellStringValue(row, 61));
                record.setDepartureCountry(getCellStringValue(row, 62));
                record.setDeparturePostCode(getCellStringValue(row, 63));
                record.setArrivalCity(getCellStringValue(row, 64));
                record.setArrivalCountry(getCellStringValue(row, 65));
                record.setArrivalPostCode(getCellStringValue(row, 66));
                record.setSaleDepartCountry(getCellStringValue(row, 67));
                record.setSaleArrivalCountry(getCellStringValue(row, 68));
                record.setTransportationMode(getCellStringValue(row, 69));
                record.setDeliveryConditions(getCellStringValue(row, 70));
                record.setSellerDepartVatNumberCountry(getCellStringValue(row, 71));
                record.setSellerDepartCountryVatNumber(getCellStringValue(row, 72));
                record.setSellerArrivalVatNumberCountry(getCellStringValue(row, 73));
                record.setSellerArrivalCountryVatNumber(getCellStringValue(row, 74));
                record.setTransactionSellerVatNumberCountry(getCellStringValue(row, 75));
                record.setTransactionSellerVatNumber(getCellStringValue(row, 76));
                record.setBuyerVatNumberCountry(getCellStringValue(row, 77));
                record.setBuyerVatNumber(getCellStringValue(row, 78));
                record.setVatCalculationImputationCountry(getCellStringValue(row, 79));
                record.setTaxableJurisdiction(getCellStringValue(row, 80));
                record.setTaxableJurisdictionLevel(getCellStringValue(row, 81));
                record.setVatInvNumber(getCellStringValue(row, 82));
                record.setVatInvConvertedAmt(getCellDoubleValue(row, 83));
                record.setVatInvCurrencyCode(getCellStringValue(row, 84));
                record.setVatInvExchangeRate(getCellDoubleValue(row, 85));
                record.setVatInvExchangeRateDate(getCellStringValue(row, 86));
                record.setExportOutsideEu(getCellStringValue(row, 87));
                record.setInvoiceUrl(getCellStringValue(row, 88));
                record.setBuyerName(getCellStringValue(row, 89));
                record.setArrivalAddress(getCellStringValue(row, 90));
                record.setSupplierName(getCellStringValue(row, 91));
                record.setSupplierVatNumber(getCellStringValue(row, 92));
                record.setTaxReportingScheme(getCellStringValue(row, 93));
                record.setTaxCollectionResponsibility(getCellStringValue(row, 94));

                records.add(record);
            }
        }
        
        return records;
    }

   private static String getCellStringValue(Row row, int cellIndex) {
    Cell cell = row.getCell(cellIndex);
    if (cell == null) {
        return null;
    }

    switch (cell.getCellType()) {
        case STRING:
            return cell.getStringCellValue();
        case NUMERIC:
            // Zwróć liczby jako String, aby zachować elastyczność
            return String.valueOf(cell.getNumericCellValue());
        case BOOLEAN:
            return String.valueOf(cell.getBooleanCellValue());
        case FORMULA:
            try {
                // Obsługa formuł zwracających różne typy wartości
                switch (cell.getCachedFormulaResultType()) {
                    case STRING:
                        return cell.getStringCellValue();
                    case NUMERIC:
                        return String.valueOf(cell.getNumericCellValue());
                    case BOOLEAN:
                        return String.valueOf(cell.getBooleanCellValue());
                    default:
                        return null;
                }
            } catch (Exception e) {
                return null;
            }
        default:
            return null;
    }
}

    private static Integer getCellIntegerValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell != null && cell.getCellType() == CellType.NUMERIC) {
            return (int) cell.getNumericCellValue();
        } else if (cell != null && cell.getCellType() == CellType.STRING) {
            try {
                return Integer.parseInt(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    private static Double getCellDoubleValue(Row row, int cellIndex) {
        Cell cell = row.getCell(cellIndex);
        if (cell != null && cell.getCellType() == CellType.NUMERIC) {
            return cell.getNumericCellValue();
        } else if (cell != null && cell.getCellType() == CellType.STRING) {
            try {
                return Double.parseDouble(cell.getStringCellValue());
            } catch (NumberFormatException e) {
                return null;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            List<TransactionRecord> records = importFromExcel("d:\\AVTR.xlsx");
            records.forEach(System.out::println);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class TransactionRecord {

    private String uniqueAccountIdentifier;
    private String activityPeriod;
    private String salesChannel;
    private String marketplace;
    private String programType;
    private String transactionType;
    private String transactionEventId;
    private String activityTransactionId;
    private String taxCalculationDate;
    private String transactionDepartDate;
    private String transactionArrivalDate;
    private String transactionCompleteDate;
    private String sellerSku;
    private String asin;
    private String itemDescription;
    private String itemManufactureCountry;
    private Integer qty;
    private Double itemWeight;
    private Double totalActivityWeight;
    private Double costPriceOfItems;
    private Double priceOfItemsAmtVatExcl;
    private Double promoPriceOfItemsAmtVatExcl;
    private Double totalPriceOfItemsAmtVatExcl;
    private Double shipChargeAmtVatExcl;
    private Double promoShipChargeAmtVatExcl;
    private Double totalShipChargeAmtVatExcl;
    private Double giftWrapAmtVatExcl;
    private Double promoGiftWrapAmtVatExcl;
    private Double totalGiftWrapAmtVatExcl;
    private Double totalActivityValueAmtVatExcl;
    private Double priceOfItemsVatRatePercent;
    private Double priceOfItemsVatAmt;
    private Double promoPriceOfItemsVatAmt;
    private Double totalPriceOfItemsVatAmt;
    private Double shipChargeVatRatePercent;
    private Double shipChargeVatAmt;
    private Double promoShipChargeVatAmt;
    private Double totalShipChargeVatAmt;
    private Double giftWrapVatRatePercent;
    private Double giftWrapVatAmt;
    private Double promoGiftWrapVatAmt;
    private Double totalGiftWrapVatAmt;
    private Double totalActivityValueVatAmt;
    private Double priceOfItemsAmtVatIncl;
    private Double promoPriceOfItemsAmtVatIncl;
    private Double totalPriceOfItemsAmtVatIncl;
    private Double shipChargeAmtVatIncl;
    private Double promoShipChargeAmtVatIncl;
    private Double totalShipChargeAmtVatIncl;
    private Double giftWrapAmtVatIncl;
    private Double promoGiftWrapAmtVatIncl;
    private Double totalGiftWrapAmtVatIncl;
    private Double totalActivityValueAmtVatIncl;
    private String transactionCurrencyCode;
    private String commodityCode;
    private String statisticalCodeDepart;
    private String statisticalCodeArrival;
    private String commodityCodeSupplementaryUnit;
    private Integer itemQtySupplementaryUnit;
    private Double totalActivitySupplementaryUnit;
    private String productTaxCode;
    private String departureCity;
    private String departureCountry;
    private String departurePostCode;
    private String arrivalCity;
    private String arrivalCountry;
    private String arrivalPostCode;
    private String saleDepartCountry;
    private String saleArrivalCountry;
    private String transportationMode;
    private String deliveryConditions;
    private String sellerDepartVatNumberCountry;
    private String sellerDepartCountryVatNumber;
    private String sellerArrivalVatNumberCountry;
    private String sellerArrivalCountryVatNumber;
    private String transactionSellerVatNumberCountry;
    private String transactionSellerVatNumber;
    private String buyerVatNumberCountry;
    private String buyerVatNumber;
    private String vatCalculationImputationCountry;
    private String taxableJurisdiction;
    private String taxableJurisdictionLevel;
    private String vatInvNumber;
    private Double vatInvConvertedAmt;
    private String vatInvCurrencyCode;
    private Double vatInvExchangeRate;
    private String vatInvExchangeRateDate;
    private String exportOutsideEu;
    private String invoiceUrl;
    private String buyerName;
    private String arrivalAddress;
    private String supplierName;
    private String supplierVatNumber;
    private String taxReportingScheme;
    private String taxCollectionResponsibility;

    // Gettery i settery dla każdego pola...

    public String getUniqueAccountIdentifier() {
        return uniqueAccountIdentifier;
    }

    public void setUniqueAccountIdentifier(String uniqueAccountIdentifier) {
        this.uniqueAccountIdentifier = uniqueAccountIdentifier;
    }

    public String getActivityPeriod() {
        return activityPeriod;
    }

    public void setActivityPeriod(String activityPeriod) {
        this.activityPeriod = activityPeriod;
    }

    public String getSalesChannel() {
        return salesChannel;
    }

    public void setSalesChannel(String salesChannel) {
        this.salesChannel = salesChannel;
    }

    public String getMarketplace() {
        return marketplace;
    }

    public void setMarketplace(String marketplace) {
        this.marketplace = marketplace;
    }

    public String getProgramType() {
        return programType;
    }

    public void setProgramType(String programType) {
        this.programType = programType;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionEventId() {
        return transactionEventId;
    }

    public void setTransactionEventId(String transactionEventId) {
        this.transactionEventId = transactionEventId;
    }

    public String getActivityTransactionId() {
        return activityTransactionId;
    }

    public void setActivityTransactionId(String activityTransactionId) {
        this.activityTransactionId = activityTransactionId;
    }

    public String getTaxCalculationDate() {
        return taxCalculationDate;
    }

    public void setTaxCalculationDate(String taxCalculationDate) {
        this.taxCalculationDate = taxCalculationDate;
    }

    public String getTransactionDepartDate() {
        return transactionDepartDate;
    }

    public void setTransactionDepartDate(String transactionDepartDate) {
        this.transactionDepartDate = transactionDepartDate;
    }

    public String getTransactionArrivalDate() {
        return transactionArrivalDate;
    }

    public void setTransactionArrivalDate(String transactionArrivalDate) {
        this.transactionArrivalDate = transactionArrivalDate;
    }

    public String getTransactionCompleteDate() {
        return transactionCompleteDate;
    }

    public void setTransactionCompleteDate(String transactionCompleteDate) {
        this.transactionCompleteDate = transactionCompleteDate;
    }

    public String getSellerSku() {
        return sellerSku;
    }

    public void setSellerSku(String sellerSku) {
        this.sellerSku = sellerSku;
    }

    public String getAsin() {
        return asin;
    }

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public String getItemDescription() {
        return itemDescription;
    }

    public void setItemDescription(String itemDescription) {
        this.itemDescription = itemDescription;
    }

    public String getItemManufactureCountry() {
        return itemManufactureCountry;
    }

    public void setItemManufactureCountry(String itemManufactureCountry) {
        this.itemManufactureCountry = itemManufactureCountry;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Double getItemWeight() {
        return itemWeight;
    }

    public void setItemWeight(Double itemWeight) {
        this.itemWeight = itemWeight;
    }

    public Double getTotalActivityWeight() {
        return totalActivityWeight;
    }

    public void setTotalActivityWeight(Double totalActivityWeight) {
        this.totalActivityWeight = totalActivityWeight;
    }

    public Double getCostPriceOfItems() {
        return costPriceOfItems;
    }

    public void setCostPriceOfItems(Double costPriceOfItems) {
        this.costPriceOfItems = costPriceOfItems;
    }

    public Double getPriceOfItemsAmtVatExcl() {
        return priceOfItemsAmtVatExcl;
    }

    public void setPriceOfItemsAmtVatExcl(Double priceOfItemsAmtVatExcl) {
        this.priceOfItemsAmtVatExcl = priceOfItemsAmtVatExcl;
    }

    public Double getPromoPriceOfItemsAmtVatExcl() {
        return promoPriceOfItemsAmtVatExcl;
    }

    public void setPromoPriceOfItemsAmtVatExcl(Double promoPriceOfItemsAmtVatExcl) {
        this.promoPriceOfItemsAmtVatExcl = promoPriceOfItemsAmtVatExcl;
    }

    public Double getTotalPriceOfItemsAmtVatExcl() {
        return totalPriceOfItemsAmtVatExcl;
    }

    public void setTotalPriceOfItemsAmtVatExcl(Double totalPriceOfItemsAmtVatExcl) {
        this.totalPriceOfItemsAmtVatExcl = totalPriceOfItemsAmtVatExcl;
    }

    public Double getShipChargeAmtVatExcl() {
        return shipChargeAmtVatExcl;
    }

    public void setShipChargeAmtVatExcl(Double shipChargeAmtVatExcl) {
        this.shipChargeAmtVatExcl = shipChargeAmtVatExcl;
    }

    public Double getPromoShipChargeAmtVatExcl() {
        return promoShipChargeAmtVatExcl;
    }

    public void setPromoShipChargeAmtVatExcl(Double promoShipChargeAmtVatExcl) {
        this.promoShipChargeAmtVatExcl = promoShipChargeAmtVatExcl;
    }

    public Double getTotalShipChargeAmtVatExcl() {
        return totalShipChargeAmtVatExcl;
    }

    public void setTotalShipChargeAmtVatExcl(Double totalShipChargeAmtVatExcl) {
        this.totalShipChargeAmtVatExcl = totalShipChargeAmtVatExcl;
    }

    public Double getGiftWrapAmtVatExcl() {
        return giftWrapAmtVatExcl;
    }

    public void setGiftWrapAmtVatExcl(Double giftWrapAmtVatExcl) {
        this.giftWrapAmtVatExcl = giftWrapAmtVatExcl;
    }

    public Double getPromoGiftWrapAmtVatExcl() {
        return promoGiftWrapAmtVatExcl;
    }

    public void setPromoGiftWrapAmtVatExcl(Double promoGiftWrapAmtVatExcl) {
        this.promoGiftWrapAmtVatExcl = promoGiftWrapAmtVatExcl;
    }

    public Double getTotalGiftWrapAmtVatExcl() {
        return totalGiftWrapAmtVatExcl;
    }

    public void setTotalGiftWrapAmtVatExcl(Double totalGiftWrapAmtVatExcl) {
        this.totalGiftWrapAmtVatExcl = totalGiftWrapAmtVatExcl;
    }

    public Double getTotalActivityValueAmtVatExcl() {
        return totalActivityValueAmtVatExcl;
    }

    public void setTotalActivityValueAmtVatExcl(Double totalActivityValueAmtVatExcl) {
        this.totalActivityValueAmtVatExcl = totalActivityValueAmtVatExcl;
    }

    public Double getPriceOfItemsVatRatePercent() {
        return priceOfItemsVatRatePercent;
    }

    public void setPriceOfItemsVatRatePercent(Double priceOfItemsVatRatePercent) {
        this.priceOfItemsVatRatePercent = priceOfItemsVatRatePercent;
    }

    public Double getPriceOfItemsVatAmt() {
        return priceOfItemsVatAmt;
    }

    public void setPriceOfItemsVatAmt(Double priceOfItemsVatAmt) {
        this.priceOfItemsVatAmt = priceOfItemsVatAmt;
    }

    public Double getPromoPriceOfItemsVatAmt() {
        return promoPriceOfItemsVatAmt;
    }

    public void setPromoPriceOfItemsVatAmt(Double promoPriceOfItemsVatAmt) {
        this.promoPriceOfItemsVatAmt = promoPriceOfItemsVatAmt;
    }

    public Double getTotalPriceOfItemsVatAmt() {
        return totalPriceOfItemsVatAmt;
    }

    public void setTotalPriceOfItemsVatAmt(Double totalPriceOfItemsVatAmt) {
        this.totalPriceOfItemsVatAmt = totalPriceOfItemsVatAmt;
    }

    public Double getShipChargeVatRatePercent() {
        return shipChargeVatRatePercent;
    }

    public void setShipChargeVatRatePercent(Double shipChargeVatRatePercent) {
        this.shipChargeVatRatePercent = shipChargeVatRatePercent;
    }

    public Double getShipChargeVatAmt() {
        return shipChargeVatAmt;
    }

    public void setShipChargeVatAmt(Double shipChargeVatAmt) {
        this.shipChargeVatAmt = shipChargeVatAmt;
    }

    public Double getPromoShipChargeVatAmt() {
        return promoShipChargeVatAmt;
    }

    public void setPromoShipChargeVatAmt(Double promoShipChargeVatAmt) {
        this.promoShipChargeVatAmt = promoShipChargeVatAmt;
    }

    public Double getTotalShipChargeVatAmt() {
        return totalShipChargeVatAmt;
    }

    public void setTotalShipChargeVatAmt(Double totalShipChargeVatAmt) {
        this.totalShipChargeVatAmt = totalShipChargeVatAmt;
    }

    public Double getGiftWrapVatRatePercent() {
        return giftWrapVatRatePercent;
    }

    public void setGiftWrapVatRatePercent(Double giftWrapVatRatePercent) {
        this.giftWrapVatRatePercent = giftWrapVatRatePercent;
    }

    public Double getGiftWrapVatAmt() {
        return giftWrapVatAmt;
    }

    public void setGiftWrapVatAmt(Double giftWrapVatAmt) {
        this.giftWrapVatAmt = giftWrapVatAmt;
    }

    public Double getPromoGiftWrapVatAmt() {
        return promoGiftWrapVatAmt;
    }

    public void setPromoGiftWrapVatAmt(Double promoGiftWrapVatAmt) {
        this.promoGiftWrapVatAmt = promoGiftWrapVatAmt;
    }

    public Double getTotalGiftWrapVatAmt() {
        return totalGiftWrapVatAmt;
    }

    public void setTotalGiftWrapVatAmt(Double totalGiftWrapVatAmt) {
        this.totalGiftWrapVatAmt = totalGiftWrapVatAmt;
    }

    public Double getTotalActivityValueVatAmt() {
        return totalActivityValueVatAmt;
    }

    public void setTotalActivityValueVatAmt(Double totalActivityValueVatAmt) {
        this.totalActivityValueVatAmt = totalActivityValueVatAmt;
    }

    public Double getPriceOfItemsAmtVatIncl() {
        return priceOfItemsAmtVatIncl;
    }

    public void setPriceOfItemsAmtVatIncl(Double priceOfItemsAmtVatIncl) {
        this.priceOfItemsAmtVatIncl = priceOfItemsAmtVatIncl;
    }

    public Double getPromoPriceOfItemsAmtVatIncl() {
        return promoPriceOfItemsAmtVatIncl;
    }

    public void setPromoPriceOfItemsAmtVatIncl(Double promoPriceOfItemsAmtVatIncl) {
        this.promoPriceOfItemsAmtVatIncl = promoPriceOfItemsAmtVatIncl;
    }

    public Double getTotalPriceOfItemsAmtVatIncl() {
        return totalPriceOfItemsAmtVatIncl;
    }

    public void setTotalPriceOfItemsAmtVatIncl(Double totalPriceOfItemsAmtVatIncl) {
        this.totalPriceOfItemsAmtVatIncl = totalPriceOfItemsAmtVatIncl;
    }

    public Double getShipChargeAmtVatIncl() {
        return shipChargeAmtVatIncl;
    }

    public void setShipChargeAmtVatIncl(Double shipChargeAmtVatIncl) {
        this.shipChargeAmtVatIncl = shipChargeAmtVatIncl;
    }

    public Double getPromoShipChargeAmtVatIncl() {
        return promoShipChargeAmtVatIncl;
    }

    public void setPromoShipChargeAmtVatIncl(Double promoShipChargeAmtVatIncl) {
        this.promoShipChargeAmtVatIncl = promoShipChargeAmtVatIncl;
    }

    public Double getTotalShipChargeAmtVatIncl() {
        return totalShipChargeAmtVatIncl;
    }

    public void setTotalShipChargeAmtVatIncl(Double totalShipChargeAmtVatIncl) {
        this.totalShipChargeAmtVatIncl = totalShipChargeAmtVatIncl;
    }

    public Double getGiftWrapAmtVatIncl() {
        return giftWrapAmtVatIncl;
    }

    public void setGiftWrapAmtVatIncl(Double giftWrapAmtVatIncl) {
        this.giftWrapAmtVatIncl = giftWrapAmtVatIncl;
    }

    public Double getPromoGiftWrapAmtVatIncl() {
        return promoGiftWrapAmtVatIncl;
    }

    public void setPromoGiftWrapAmtVatIncl(Double promoGiftWrapAmtVatIncl) {
        this.promoGiftWrapAmtVatIncl = promoGiftWrapAmtVatIncl;
    }

    public Double getTotalGiftWrapAmtVatIncl() {
        return totalGiftWrapAmtVatIncl;
    }

    public void setTotalGiftWrapAmtVatIncl(Double totalGiftWrapAmtVatIncl) {
        this.totalGiftWrapAmtVatIncl = totalGiftWrapAmtVatIncl;
    }

    public Double getTotalActivityValueAmtVatIncl() {
        return totalActivityValueAmtVatIncl;
    }

    public void setTotalActivityValueAmtVatIncl(Double totalActivityValueAmtVatIncl) {
        this.totalActivityValueAmtVatIncl = totalActivityValueAmtVatIncl;
    }

    public String getTransactionCurrencyCode() {
        return transactionCurrencyCode;
    }

    public void setTransactionCurrencyCode(String transactionCurrencyCode) {
        this.transactionCurrencyCode = transactionCurrencyCode;
    }

    public String getCommodityCode() {
        return commodityCode;
    }

    public void setCommodityCode(String commodityCode) {
        this.commodityCode = commodityCode;
    }

    public String getStatisticalCodeDepart() {
        return statisticalCodeDepart;
    }

    public void setStatisticalCodeDepart(String statisticalCodeDepart) {
        this.statisticalCodeDepart = statisticalCodeDepart;
    }

    public String getStatisticalCodeArrival() {
        return statisticalCodeArrival;
    }

    public void setStatisticalCodeArrival(String statisticalCodeArrival) {
        this.statisticalCodeArrival = statisticalCodeArrival;
    }

    public String getCommodityCodeSupplementaryUnit() {
        return commodityCodeSupplementaryUnit;
    }

    public void setCommodityCodeSupplementaryUnit(String commodityCodeSupplementaryUnit) {
        this.commodityCodeSupplementaryUnit = commodityCodeSupplementaryUnit;
    }

    public Integer getItemQtySupplementaryUnit() {
        return itemQtySupplementaryUnit;
    }

    public void setItemQtySupplementaryUnit(Integer itemQtySupplementaryUnit) {
        this.itemQtySupplementaryUnit = itemQtySupplementaryUnit;
    }

    public Double getTotalActivitySupplementaryUnit() {
        return totalActivitySupplementaryUnit;
    }

    public void setTotalActivitySupplementaryUnit(Double totalActivitySupplementaryUnit) {
        this.totalActivitySupplementaryUnit = totalActivitySupplementaryUnit;
    }

    public String getProductTaxCode() {
        return productTaxCode;
    }

    public void setProductTaxCode(String productTaxCode) {
        this.productTaxCode = productTaxCode;
    }

    public String getDepartureCity() {
        return departureCity;
    }

    public void setDepartureCity(String departureCity) {
        this.departureCity = departureCity;
    }

    public String getDepartureCountry() {
        return departureCountry;
    }

    public void setDepartureCountry(String departureCountry) {
        this.departureCountry = departureCountry;
    }

    public String getDeparturePostCode() {
        return departurePostCode;
    }

    public void setDeparturePostCode(String departurePostCode) {
        this.departurePostCode = departurePostCode;
    }

    public String getArrivalCity() {
        return arrivalCity;
    }

    public void setArrivalCity(String arrivalCity) {
        this.arrivalCity = arrivalCity;
    }

    public String getArrivalCountry() {
        return arrivalCountry;
    }

    public void setArrivalCountry(String arrivalCountry) {
        this.arrivalCountry = arrivalCountry;
    }

    public String getArrivalPostCode() {
        return arrivalPostCode;
    }

    public void setArrivalPostCode(String arrivalPostCode) {
        this.arrivalPostCode = arrivalPostCode;
    }

    public String getSaleDepartCountry() {
        return saleDepartCountry;
    }

    public void setSaleDepartCountry(String saleDepartCountry) {
        this.saleDepartCountry = saleDepartCountry;
    }

    public String getSaleArrivalCountry() {
        return saleArrivalCountry;
    }

    public void setSaleArrivalCountry(String saleArrivalCountry) {
        this.saleArrivalCountry = saleArrivalCountry;
    }

    public String getTransportationMode() {
        return transportationMode;
    }

    public void setTransportationMode(String transportationMode) {
        this.transportationMode = transportationMode;
    }

    public String getDeliveryConditions() {
        return deliveryConditions;
    }

    public void setDeliveryConditions(String deliveryConditions) {
        this.deliveryConditions = deliveryConditions;
    }

    public String getSellerDepartVatNumberCountry() {
        return sellerDepartVatNumberCountry;
    }

    public void setSellerDepartVatNumberCountry(String sellerDepartVatNumberCountry) {
        this.sellerDepartVatNumberCountry = sellerDepartVatNumberCountry;
    }

    public String getSellerDepartCountryVatNumber() {
        return sellerDepartCountryVatNumber;
    }

    public void setSellerDepartCountryVatNumber(String sellerDepartCountryVatNumber) {
        this.sellerDepartCountryVatNumber = sellerDepartCountryVatNumber;
    }

    public String getSellerArrivalVatNumberCountry() {
        return sellerArrivalVatNumberCountry;
    }

    public void setSellerArrivalVatNumberCountry(String sellerArrivalVatNumberCountry) {
        this.sellerArrivalVatNumberCountry = sellerArrivalVatNumberCountry;
    }

    public String getSellerArrivalCountryVatNumber() {
        return sellerArrivalCountryVatNumber;
    }

    public void setSellerArrivalCountryVatNumber(String sellerArrivalCountryVatNumber) {
        this.sellerArrivalCountryVatNumber = sellerArrivalCountryVatNumber;
    }

    public String getTransactionSellerVatNumberCountry() {
        return transactionSellerVatNumberCountry;
    }

    public void setTransactionSellerVatNumberCountry(String transactionSellerVatNumberCountry) {
        this.transactionSellerVatNumberCountry = transactionSellerVatNumberCountry;
    }

    public String getTransactionSellerVatNumber() {
        return transactionSellerVatNumber;
    }

    public void setTransactionSellerVatNumber(String transactionSellerVatNumber) {
        this.transactionSellerVatNumber = transactionSellerVatNumber;
    }

    public String getBuyerVatNumberCountry() {
        return buyerVatNumberCountry;
    }

    public void setBuyerVatNumberCountry(String buyerVatNumberCountry) {
        this.buyerVatNumberCountry = buyerVatNumberCountry;
    }

    public String getBuyerVatNumber() {
        return buyerVatNumber;
    }

    public void setBuyerVatNumber(String buyerVatNumber) {
        this.buyerVatNumber = buyerVatNumber;
    }

    public String getVatCalculationImputationCountry() {
        return vatCalculationImputationCountry;
    }

    public void setVatCalculationImputationCountry(String vatCalculationImputationCountry) {
        this.vatCalculationImputationCountry = vatCalculationImputationCountry;
    }

    public String getTaxableJurisdiction() {
        return taxableJurisdiction;
    }

    public void setTaxableJurisdiction(String taxableJurisdiction) {
        this.taxableJurisdiction = taxableJurisdiction;
    }

    public String getTaxableJurisdictionLevel() {
        return taxableJurisdictionLevel;
    }

    public void setTaxableJurisdictionLevel(String taxableJurisdictionLevel) {
        this.taxableJurisdictionLevel = taxableJurisdictionLevel;
    }

    public String getVatInvNumber() {
        return vatInvNumber;
    }

    public void setVatInvNumber(String vatInvNumber) {
        this.vatInvNumber = vatInvNumber;
    }

    public Double getVatInvConvertedAmt() {
        return vatInvConvertedAmt;
    }

    public void setVatInvConvertedAmt(Double vatInvConvertedAmt) {
        this.vatInvConvertedAmt = vatInvConvertedAmt;
    }

    public String getVatInvCurrencyCode() {
        return vatInvCurrencyCode;
    }

    public void setVatInvCurrencyCode(String vatInvCurrencyCode) {
        this.vatInvCurrencyCode = vatInvCurrencyCode;
    }

    public Double getVatInvExchangeRate() {
        return vatInvExchangeRate;
    }

    public void setVatInvExchangeRate(Double vatInvExchangeRate) {
        this.vatInvExchangeRate = vatInvExchangeRate;
    }

    public String getVatInvExchangeRateDate() {
        return vatInvExchangeRateDate;
    }

    public void setVatInvExchangeRateDate(String vatInvExchangeRateDate) {
        this.vatInvExchangeRateDate = vatInvExchangeRateDate;
    }

    public String getExportOutsideEu() {
        return exportOutsideEu;
    }

    public void setExportOutsideEu(String exportOutsideEu) {
        this.exportOutsideEu = exportOutsideEu;
    }

    public String getInvoiceUrl() {
        return invoiceUrl;
    }

    public void setInvoiceUrl(String invoiceUrl) {
        this.invoiceUrl = invoiceUrl;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getArrivalAddress() {
        return arrivalAddress;
    }

    public void setArrivalAddress(String arrivalAddress) {
        this.arrivalAddress = arrivalAddress;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getSupplierVatNumber() {
        return supplierVatNumber;
    }

    public void setSupplierVatNumber(String supplierVatNumber) {
        this.supplierVatNumber = supplierVatNumber;
    }

    public String getTaxReportingScheme() {
        return taxReportingScheme;
    }

    public void setTaxReportingScheme(String taxReportingScheme) {
        this.taxReportingScheme = taxReportingScheme;
    }

    public String getTaxCollectionResponsibility() {
        return taxCollectionResponsibility;
    }

    public void setTaxCollectionResponsibility(String taxCollectionResponsibility) {
        this.taxCollectionResponsibility = taxCollectionResponsibility;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 83 * hash + Objects.hashCode(this.uniqueAccountIdentifier);
        hash = 83 * hash + Objects.hashCode(this.activityPeriod);
        hash = 83 * hash + Objects.hashCode(this.transactionEventId);
        hash = 83 * hash + Objects.hashCode(this.activityTransactionId);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final TransactionRecord other = (TransactionRecord) obj;
        if (!Objects.equals(this.uniqueAccountIdentifier, other.uniqueAccountIdentifier)) {
            return false;
        }
        if (!Objects.equals(this.activityPeriod, other.activityPeriod)) {
            return false;
        }
        if (!Objects.equals(this.transactionEventId, other.transactionEventId)) {
            return false;
        }
        return Objects.equals(this.activityTransactionId, other.activityTransactionId);
    }

    @Override
    public String toString() {
        return "TransactionRecord{" + "uniqueAccountIdentifier=" + uniqueAccountIdentifier + ", activityPeriod=" + activityPeriod + ", salesChannel=" + salesChannel + ", marketplace=" + marketplace + ", programType=" + programType + ", transactionType=" + transactionType + ", transactionEventId=" + transactionEventId + ", activityTransactionId=" + activityTransactionId + ", taxCalculationDate=" + taxCalculationDate + ", transactionDepartDate=" + transactionDepartDate + ", transactionArrivalDate=" + transactionArrivalDate + ", transactionCompleteDate=" + transactionCompleteDate + ", sellerSku=" + sellerSku + ", asin=" + asin + ", itemDescription=" + itemDescription + ", itemManufactureCountry=" + itemManufactureCountry + ", qty=" + qty + ", itemWeight=" + itemWeight + ", totalActivityWeight=" + totalActivityWeight + ", costPriceOfItems=" + costPriceOfItems + ", priceOfItemsAmtVatExcl=" + priceOfItemsAmtVatExcl + ", promoPriceOfItemsAmtVatExcl=" + promoPriceOfItemsAmtVatExcl + ", totalPriceOfItemsAmtVatExcl=" + totalPriceOfItemsAmtVatExcl + ", shipChargeAmtVatExcl=" + shipChargeAmtVatExcl + ", promoShipChargeAmtVatExcl=" + promoShipChargeAmtVatExcl + ", totalShipChargeAmtVatExcl=" + totalShipChargeAmtVatExcl + ", giftWrapAmtVatExcl=" + giftWrapAmtVatExcl + ", promoGiftWrapAmtVatExcl=" + promoGiftWrapAmtVatExcl + ", totalGiftWrapAmtVatExcl=" + totalGiftWrapAmtVatExcl + ", totalActivityValueAmtVatExcl=" + totalActivityValueAmtVatExcl + ", priceOfItemsVatRatePercent=" + priceOfItemsVatRatePercent + ", priceOfItemsVatAmt=" + priceOfItemsVatAmt + ", promoPriceOfItemsVatAmt=" + promoPriceOfItemsVatAmt + ", totalPriceOfItemsVatAmt=" + totalPriceOfItemsVatAmt + ", shipChargeVatRatePercent=" + shipChargeVatRatePercent + ", shipChargeVatAmt=" + shipChargeVatAmt + ", promoShipChargeVatAmt=" + promoShipChargeVatAmt + ", totalShipChargeVatAmt=" + totalShipChargeVatAmt + ", giftWrapVatRatePercent=" + giftWrapVatRatePercent + ", giftWrapVatAmt=" + giftWrapVatAmt + ", promoGiftWrapVatAmt=" + promoGiftWrapVatAmt + ", totalGiftWrapVatAmt=" + totalGiftWrapVatAmt + ", totalActivityValueVatAmt=" + totalActivityValueVatAmt + ", priceOfItemsAmtVatIncl=" + priceOfItemsAmtVatIncl + ", promoPriceOfItemsAmtVatIncl=" + promoPriceOfItemsAmtVatIncl + ", totalPriceOfItemsAmtVatIncl=" + totalPriceOfItemsAmtVatIncl + ", shipChargeAmtVatIncl=" + shipChargeAmtVatIncl + ", promoShipChargeAmtVatIncl=" + promoShipChargeAmtVatIncl + ", totalShipChargeAmtVatIncl=" + totalShipChargeAmtVatIncl + ", giftWrapAmtVatIncl=" + giftWrapAmtVatIncl + ", promoGiftWrapAmtVatIncl=" + promoGiftWrapAmtVatIncl + ", totalGiftWrapAmtVatIncl=" + totalGiftWrapAmtVatIncl + ", totalActivityValueAmtVatIncl=" + totalActivityValueAmtVatIncl + ", transactionCurrencyCode=" + transactionCurrencyCode + ", commodityCode=" + commodityCode + ", statisticalCodeDepart=" + statisticalCodeDepart + ", statisticalCodeArrival=" + statisticalCodeArrival + ", commodityCodeSupplementaryUnit=" + commodityCodeSupplementaryUnit + ", itemQtySupplementaryUnit=" + itemQtySupplementaryUnit + ", totalActivitySupplementaryUnit=" + totalActivitySupplementaryUnit + ", productTaxCode=" + productTaxCode + ", departureCity=" + departureCity + ", departureCountry=" + departureCountry + ", departurePostCode=" + departurePostCode + ", arrivalCity=" + arrivalCity + ", arrivalCountry=" + arrivalCountry + ", arrivalPostCode=" + arrivalPostCode + ", saleDepartCountry=" + saleDepartCountry + ", saleArrivalCountry=" + saleArrivalCountry + ", transportationMode=" + transportationMode + ", deliveryConditions=" + deliveryConditions + ", sellerDepartVatNumberCountry=" + sellerDepartVatNumberCountry + ", sellerDepartCountryVatNumber=" + sellerDepartCountryVatNumber + ", sellerArrivalVatNumberCountry=" + sellerArrivalVatNumberCountry + ", sellerArrivalCountryVatNumber=" + sellerArrivalCountryVatNumber + ", transactionSellerVatNumberCountry=" + transactionSellerVatNumberCountry + ", transactionSellerVatNumber=" + transactionSellerVatNumber + ", buyerVatNumberCountry=" + buyerVatNumberCountry + ", buyerVatNumber=" + buyerVatNumber + ", vatCalculationImputationCountry=" + vatCalculationImputationCountry + ", taxableJurisdiction=" + taxableJurisdiction + ", taxableJurisdictionLevel=" + taxableJurisdictionLevel + ", vatInvNumber=" + vatInvNumber + ", vatInvConvertedAmt=" + vatInvConvertedAmt + ", vatInvCurrencyCode=" + vatInvCurrencyCode + ", vatInvExchangeRate=" + vatInvExchangeRate + ", vatInvExchangeRateDate=" + vatInvExchangeRateDate + ", exportOutsideEu=" + exportOutsideEu + ", invoiceUrl=" + invoiceUrl + ", buyerName=" + buyerName + ", arrivalAddress=" + arrivalAddress + ", supplierName=" + supplierName + ", supplierVatNumber=" + supplierVatNumber + ", taxReportingScheme=" + taxReportingScheme + ", taxCollectionResponsibility=" + taxCollectionResponsibility + '}';
    }
    
    
    
}

