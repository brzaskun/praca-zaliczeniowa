///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package entityfk;
//
//import entity.Uz;
//import java.io.Serializable;
//import java.util.List;
//import java.util.Objects;
//import javax.persistence.CollectionTable;
//import javax.persistence.Column;
//import javax.persistence.ElementCollection;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.OneToMany;
//import javax.persistence.Table;
//
///**
// *
// * @author Osito
// */
//@Entity
//@Table(name = "test")
//public class Test implements Serializable{
//    
//    @Id
//    @Column(name="id")
//    private int id;
//    @ElementCollection
//    @CollectionTable(name = "dodatkowatabela")
//    @OneToMany
//    List<Uz> klienci;
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
//    public List<Uz> getKlienci() {
//        return klienci;
//    }
//
//    public void setKlienci(List<Uz> klienci) {
//        this.klienci = klienci;
//    }
//
//    @Override
//    public int hashCode() {
//        int hash = 7;
//        hash = 67 * hash + this.id;
//        hash = 67 * hash + Objects.hashCode(this.klienci);
//        return hash;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if (obj == null) {
//            return false;
//        }
//        if (getClass() != obj.getClass()) {
//            return false;
//        }
//        final Test other = (Test) obj;
//        if (this.id != other.id) {
//            return false;
//        }
//        if (!Objects.equals(this.klienci, other.klienci)) {
//            return false;
//        }
//        return true;
//    }
//
//    @Override
//    public String toString() {
//        return "Test{" + "id=" + id + ", klienci=" + klienci + '}';
//    }
//    
//    
//}
