/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import java.io.Serializable;

/**
 *
 * @author Osito
 */


public class EVatViewPo implements Serializable{
//    private static final long serialVersionUID = -7659459927104523420L;
//    private static final List<String> opispol;
//    static {
//        opispol = Collections.synchronizedList(new ArrayList<>());
//        opispol.add("id");
//        opispol.add("dataWyst");
//        opispol.add("dataSprz");
//        opispol.add("nrWlDk");
//        opispol.add("kontr");
//        opispol.add("opis");
//        opispol.add("netto");
//        opispol.add("vat");
//        opispol.add("opizw");
//        opispol.add("innyMc");
//    }
//
//    public static List<String> getOpispol() {
//        return opispol;
//    }
//
//    @JoinColumn(name = "dokid", referencedColumnName = "id")
//    private Dokfk dokfk;
//    private double netto;
//    private double vat;
//    private String opizw;
//    private String innymc;
//    private String innyrok;
//    @OneToOne
//    @JoinColumn(name = "nazwaewidencji", referencedColumnName = "nazwa")
//    private Evewidencja nazwaewidencji;
//    private String nrpolanetto;
//    private String nrpolavat;
//    private double procentvat;
//    private boolean duplikat;
//    private String numerwlasnydokfk;
//
//    
//    
//    public EVatViewPo() {
//    }
//
//    public EVatViewPo(EVatViewPo old) {
//        this.dataWyst = old.getDataWyst();
//        this.dataSprz = old.getDataSprz();
//        this.nrWlDk = old.getNrWlDk();
//        this.nrKolejny = old.getNrKolejny();
//        this.kontr = old.getKontr();
//        this.opis = old.getOpis();
//        this.netto = old.getNetto();
//        this.vat = old.getVat();
//        this.opizw = old.getOpizw();
//        this.innymc = old.getInnymc();
//        this.innyrok = old.getInnyrok();
//        this.nazwaewidencji = old.getNazwaewidencji();
//        this.nrpolanetto = old.getNrpolanetto();
//        this.nrpolavat = old.getNrpolavat();
//        this.procentvat = old.getProcentvat();
//        this.dokfk = old.getDokfk();
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 53 * hash + this.id;
//        hash = 53 * hash + Objects.hashCode(this.kontr);
//        hash = 53 * hash + Objects.hashCode(this.opis);
//        hash = 53 * hash + (int) (Double.doubleToLongBits(this.netto) ^ (Double.doubleToLongBits(this.netto) >>> 32));
//        hash = 53 * hash + (int) (Double.doubleToLongBits(this.vat) ^ (Double.doubleToLongBits(this.vat) >>> 32));
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (this == obj) {
//            return true;
//        }
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final EVatViewPo other = (EVatViewPo) obj;
//        if (this.id != other.id) {
//            return false;
//        }
//        if (Double.doubleToLongBits(this.netto) != Double.doubleToLongBits(other.netto)) {
//            return false;
//        }
//        if (Double.doubleToLongBits(this.vat) != Double.doubleToLongBits(other.vat)) {
//            return false;
//        }
//        if (!Objects.equals(this.opis, other.opis)) {
//            return false;
//        }
//        if (!Objects.equals(this.kontr, other.kontr)) {
//            return false;
//        }
//        return true;
//    }
//    
//    
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public String getDataWyst() {
//        return dataWyst;
//    }
//
//    public void setDataWyst(String dataWyst) {
//        this.dataWyst = dataWyst;
//    }
//
//    public double getProcentvat() {
//        return procentvat;
//    }
//
//    public void setProcentvat(double procentvat) {
//        this.procentvat = procentvat;
//    }
//
//    public String getDataSprz() {
//        return dataSprz;
//    }
//
//    public void setDataSprz(String dataSprz) {
//        this.dataSprz = dataSprz;
//    }
//
//    public String getInnyrok() {
//        return innyrok;
//    }
//
//    public void setInnyrok(String innyrok) {
//        this.innyrok = innyrok;
//    }
//
//    public Dokfk getDokfk() {
//        return dokfk;
//    }
//
//    public void setDokfk(Dokfk dokfk) {
//        this.dokfk = dokfk;
//    }
//
//  
//    public String getNrWlDk() {
//        return nrWlDk;
//    }
//
//    public void setNrWlDk(String nrWlDk) {
//        this.nrWlDk = nrWlDk;
//    }
//
//    public Klienci getKontr() {
//        return kontr;
//    }
//
//    public void setKontr(Klienci kontr) {
//        this.kontr = kontr;
//    }
//
//   
//    public String getOpis() {
//        return opis;
//    }
//
//    public void setOpis(String opis) {
//        this.opis = opis;
//    }
//
//    public double getNetto() {
//        return netto;
//    }
//
//    public void setNetto(double netto) {
//        this.netto = netto;
//    }
//
//    public double getVat() {
//        return vat;
//    }
//
//    public void setVat(double vat) {
//        this.vat = vat;
//    }
//
//    public String getOpizw() {
//        return opizw;
//    }
//
//    public void setOpizw(String opizw) {
//        this.opizw = opizw;
//    }
//
//    public Evewidencja getNazwaewidencji() {
//        return nazwaewidencji;
//    }
//
//    public void setNazwaewidencji(Evewidencja nazwaewidencji) {
//        this.nazwaewidencji = nazwaewidencji;
//    }
//
//
//    public String getNrpolanetto() {
//        return nrpolanetto;
//    }
//
//    public void setNrpolanetto(String nrpolanetto) {
//        this.nrpolanetto = nrpolanetto;
//    }
//
//    public String getNrpolavat() {
//        return nrpolavat;
//    }
//
//    public void setNrpolavat(String nrpolavat) {
//        this.nrpolavat = nrpolavat;
//    }
//
//    public String getInnymc() {
//        return innymc;
//    }
//
//    public void setInnymc(String innymc) {
//        this.innymc = innymc;
//    }
//
//    public String getNrKolejny() {
//        return nrKolejny;
//    }
//
//    public void setNrKolejny(String nrKolejny) {
//        this.nrKolejny = nrKolejny;
//    }
//
//    public boolean isDuplikat() {
//        return duplikat;
//    }
//
//    public void setDuplikat(boolean duplikat) {
//        this.duplikat = duplikat;
//    }
//
//    public String getNumerwlasnydokfk() {
//        return numerwlasnydokfk;
//    }
//
//    public void setNumerwlasnydokfk(String numerwlasnydokfk) {
//        this.numerwlasnydokfk = numerwlasnydokfk;
//    }
//
//    public String getNrpozycji() {
//        if (!this.getOpis().equals("podsumowanie") && this.getDokfk().getRodzajedok().getKategoriadokumentu()==0 && this.getNumerwlasnydokfk() != null) {
//            return this.numerwlasnydokfk;
//        } else if (!this.getOpis().equals("podsumowanie")){
//            return this.dokfk.getNumerwlasnydokfk();
//        } else {
//            return "";
//        }
//    }
//    
//    
//
//    @Override
//    public String toString() {
//        return "EVatwpisSuper{" + "dataSprz=" + dataSprz + ", nrWlDk=" + nrWlDk + ", nrKolejny=" + nrKolejny + ", kontr=" + kontr + ", opis=" + opis + ", netto=" + netto + ", vat=" + vat + ", innymc=" + innymc + ", nazwaewidencji=" + nazwaewidencji + ", nrpolanetto=" + nrpolanetto + ", nrpolavat=" + nrpolavat + ", duplikat=" + duplikat + '}';
//    }
//
//    
//   

    
    
}
