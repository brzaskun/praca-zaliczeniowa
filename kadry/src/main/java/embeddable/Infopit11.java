/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package embeddable;

import entity.FirmaKadry;

/**
 *
 * @author Osito
 */
public class Infopit11 {
        private int id;
        private FirmaKadry firma;
        private int iloscangazy;
        private int iloscpit;
       
        
        public Infopit11() {
        }


        public Infopit11(int id, FirmaKadry firma) {
            this.id = id;
            this.firma = firma;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public FirmaKadry getFirma() {
            return firma;
        }

        public void setFirma(FirmaKadry firma) {
            this.firma = firma;
        }

        public int getIloscangazy() {
            return iloscangazy;
        }

        public void setIloscangazy(int iloscangazy) {
            this.iloscangazy = iloscangazy;
        }

        public int getIloscpit() {
            return iloscpit;
        }

        public void setIloscpit(int iloscpit) {
            this.iloscpit = iloscpit;
        }
        
        public int getPozostalo() {
            return this.iloscangazy-this.iloscpit;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 97 * hash + this.id;
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
            final Infopit11 other = (Infopit11) obj;
            if (this.id != other.id) {
                return false;
            }
            return true;
        }
        
        
    }
