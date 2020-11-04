/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "firma", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"fir_serial"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Firma.findAll", query = "SELECT f FROM Firma f"),
    @NamedQuery(name = "Firma.findByFirSerial", query = "SELECT f FROM Firma f WHERE f.firSerial = :firSerial"),
    @NamedQuery(name = "Firma.findByFirNazwaSkr", query = "SELECT f FROM Firma f WHERE f.firNazwaSkr = :firNazwaSkr"),
    @NamedQuery(name = "Firma.findByFirNazwaPel", query = "SELECT f FROM Firma f WHERE f.firNazwaPel = :firNazwaPel"),
    @NamedQuery(name = "Firma.findByFirNip", query = "SELECT f FROM Firma f WHERE f.firNip = :firNip"),
    @NamedQuery(name = "Firma.findByFirRegon", query = "SELECT f FROM Firma f WHERE f.firRegon = :firRegon"),
    @NamedQuery(name = "Firma.findByFirPanSerial", query = "SELECT f FROM Firma f WHERE f.firPanSerial = :firPanSerial"),
    @NamedQuery(name = "Firma.findByFirMiaSerial", query = "SELECT f FROM Firma f WHERE f.firMiaSerial = :firMiaSerial"),
    @NamedQuery(name = "Firma.findByFirKod", query = "SELECT f FROM Firma f WHERE f.firKod = :firKod"),
    @NamedQuery(name = "Firma.findByFirUlica", query = "SELECT f FROM Firma f WHERE f.firUlica = :firUlica"),
    @NamedQuery(name = "Firma.findByFirDom", query = "SELECT f FROM Firma f WHERE f.firDom = :firDom"),
    @NamedQuery(name = "Firma.findByFirMieszkanie", query = "SELECT f FROM Firma f WHERE f.firMieszkanie = :firMieszkanie"),
    @NamedQuery(name = "Firma.findByFirTel", query = "SELECT f FROM Firma f WHERE f.firTel = :firTel"),
    @NamedQuery(name = "Firma.findByFirFaks", query = "SELECT f FROM Firma f WHERE f.firFaks = :firFaks"),
    @NamedQuery(name = "Firma.findByFirKntSerial", query = "SELECT f FROM Firma f WHERE f.firKntSerial = :firKntSerial"),
    @NamedQuery(name = "Firma.findByFirRodzDzial", query = "SELECT f FROM Firma f WHERE f.firRodzDzial = :firRodzDzial"),
    @NamedQuery(name = "Firma.findByFirFakBanSerial", query = "SELECT f FROM Firma f WHERE f.firFakBanSerial = :firFakBanSerial"),
    @NamedQuery(name = "Firma.findByFirFakNrKonta", query = "SELECT f FROM Firma f WHERE f.firFakNrKonta = :firFakNrKonta"),
    @NamedQuery(name = "Firma.findByFirZapBanSerial", query = "SELECT f FROM Firma f WHERE f.firZapBanSerial = :firZapBanSerial"),
    @NamedQuery(name = "Firma.findByFirZapNrKonta", query = "SELECT f FROM Firma f WHERE f.firZapNrKonta = :firZapNrKonta"),
    @NamedQuery(name = "Firma.findByFirFakWiadom", query = "SELECT f FROM Firma f WHERE f.firFakWiadom = :firFakWiadom"),
    @NamedQuery(name = "Firma.findByFirNrFakAuto", query = "SELECT f FROM Firma f WHERE f.firNrFakAuto = :firNrFakAuto"),
    @NamedQuery(name = "Firma.findByFirNrFakD", query = "SELECT f FROM Firma f WHERE f.firNrFakD = :firNrFakD"),
    @NamedQuery(name = "Firma.findByFirNrFakDP", query = "SELECT f FROM Firma f WHERE f.firNrFakDP = :firNrFakDP"),
    @NamedQuery(name = "Firma.findByFirNrFakL", query = "SELECT f FROM Firma f WHERE f.firNrFakL = :firNrFakL"),
    @NamedQuery(name = "Firma.findByFirNrFakLP", query = "SELECT f FROM Firma f WHERE f.firNrFakLP = :firNrFakLP"),
    @NamedQuery(name = "Firma.findByFirNrFakS", query = "SELECT f FROM Firma f WHERE f.firNrFakS = :firNrFakS"),
    @NamedQuery(name = "Firma.findByFirNrFakSP", query = "SELECT f FROM Firma f WHERE f.firNrFakSP = :firNrFakSP"),
    @NamedQuery(name = "Firma.findByFirNrRachAuto", query = "SELECT f FROM Firma f WHERE f.firNrRachAuto = :firNrRachAuto"),
    @NamedQuery(name = "Firma.findByFirNrRachS", query = "SELECT f FROM Firma f WHERE f.firNrRachS = :firNrRachS"),
    @NamedQuery(name = "Firma.findByFirDataPlatRoz", query = "SELECT f FROM Firma f WHERE f.firDataPlatRoz = :firDataPlatRoz"),
    @NamedQuery(name = "Firma.findByFirNrKRachAuto", query = "SELECT f FROM Firma f WHERE f.firNrKRachAuto = :firNrKRachAuto"),
    @NamedQuery(name = "Firma.findByFirNrKRachS", query = "SELECT f FROM Firma f WHERE f.firNrKRachS = :firNrKRachS"),
    @NamedQuery(name = "Firma.findByFirNrKFakAuto", query = "SELECT f FROM Firma f WHERE f.firNrKFakAuto = :firNrKFakAuto"),
    @NamedQuery(name = "Firma.findByFirNrKFakS", query = "SELECT f FROM Firma f WHERE f.firNrKFakS = :firNrKFakS"),
    @NamedQuery(name = "Firma.findByFirNrRachSP", query = "SELECT f FROM Firma f WHERE f.firNrRachSP = :firNrRachSP"),
    @NamedQuery(name = "Firma.findByFirNrRachD", query = "SELECT f FROM Firma f WHERE f.firNrRachD = :firNrRachD"),
    @NamedQuery(name = "Firma.findByFirNrRachDP", query = "SELECT f FROM Firma f WHERE f.firNrRachDP = :firNrRachDP"),
    @NamedQuery(name = "Firma.findByFirNrRachL", query = "SELECT f FROM Firma f WHERE f.firNrRachL = :firNrRachL"),
    @NamedQuery(name = "Firma.findByFirNrRachLP", query = "SELECT f FROM Firma f WHERE f.firNrRachLP = :firNrRachLP"),
    @NamedQuery(name = "Firma.findByFirNrKRachSP", query = "SELECT f FROM Firma f WHERE f.firNrKRachSP = :firNrKRachSP"),
    @NamedQuery(name = "Firma.findByFirNrKRachD", query = "SELECT f FROM Firma f WHERE f.firNrKRachD = :firNrKRachD"),
    @NamedQuery(name = "Firma.findByFirNrKRachDP", query = "SELECT f FROM Firma f WHERE f.firNrKRachDP = :firNrKRachDP"),
    @NamedQuery(name = "Firma.findByFirNrKRachL", query = "SELECT f FROM Firma f WHERE f.firNrKRachL = :firNrKRachL"),
    @NamedQuery(name = "Firma.findByFirNrKRachLP", query = "SELECT f FROM Firma f WHERE f.firNrKRachLP = :firNrKRachLP"),
    @NamedQuery(name = "Firma.findByFirNrKFakSP", query = "SELECT f FROM Firma f WHERE f.firNrKFakSP = :firNrKFakSP"),
    @NamedQuery(name = "Firma.findByFirNrKFakD", query = "SELECT f FROM Firma f WHERE f.firNrKFakD = :firNrKFakD"),
    @NamedQuery(name = "Firma.findByFirNrKFakDP", query = "SELECT f FROM Firma f WHERE f.firNrKFakDP = :firNrKFakDP"),
    @NamedQuery(name = "Firma.findByFirNrKFakL", query = "SELECT f FROM Firma f WHERE f.firNrKFakL = :firNrKFakL"),
    @NamedQuery(name = "Firma.findByFirNrKFakLP", query = "SELECT f FROM Firma f WHERE f.firNrKFakLP = :firNrKFakLP"),
    @NamedQuery(name = "Firma.findByFirNrZwAuto", query = "SELECT f FROM Firma f WHERE f.firNrZwAuto = :firNrZwAuto"),
    @NamedQuery(name = "Firma.findByFirNrZwS", query = "SELECT f FROM Firma f WHERE f.firNrZwS = :firNrZwS"),
    @NamedQuery(name = "Firma.findByFirNrZwSP", query = "SELECT f FROM Firma f WHERE f.firNrZwSP = :firNrZwSP"),
    @NamedQuery(name = "Firma.findByFirNrZwD", query = "SELECT f FROM Firma f WHERE f.firNrZwD = :firNrZwD"),
    @NamedQuery(name = "Firma.findByFirNrZwDP", query = "SELECT f FROM Firma f WHERE f.firNrZwDP = :firNrZwDP"),
    @NamedQuery(name = "Firma.findByFirNrZwL", query = "SELECT f FROM Firma f WHERE f.firNrZwL = :firNrZwL"),
    @NamedQuery(name = "Firma.findByFirNrZwLP", query = "SELECT f FROM Firma f WHERE f.firNrZwLP = :firNrZwLP"),
    @NamedQuery(name = "Firma.findByFirNrWzAuto", query = "SELECT f FROM Firma f WHERE f.firNrWzAuto = :firNrWzAuto"),
    @NamedQuery(name = "Firma.findByFirNrWzS", query = "SELECT f FROM Firma f WHERE f.firNrWzS = :firNrWzS"),
    @NamedQuery(name = "Firma.findByFirNrWzSP", query = "SELECT f FROM Firma f WHERE f.firNrWzSP = :firNrWzSP"),
    @NamedQuery(name = "Firma.findByFirNrWzD", query = "SELECT f FROM Firma f WHERE f.firNrWzD = :firNrWzD"),
    @NamedQuery(name = "Firma.findByFirNrWzDP", query = "SELECT f FROM Firma f WHERE f.firNrWzDP = :firNrWzDP"),
    @NamedQuery(name = "Firma.findByFirNrWzL", query = "SELECT f FROM Firma f WHERE f.firNrWzL = :firNrWzL"),
    @NamedQuery(name = "Firma.findByFirNrWzLP", query = "SELECT f FROM Firma f WHERE f.firNrWzLP = :firNrWzLP"),
    @NamedQuery(name = "Firma.findByFirFakLogo", query = "SELECT f FROM Firma f WHERE f.firFakLogo = :firFakLogo"),
    @NamedQuery(name = "Firma.findByFirRachLogo", query = "SELECT f FROM Firma f WHERE f.firRachLogo = :firRachLogo"),
    @NamedQuery(name = "Firma.findByFirKFakLogo", query = "SELECT f FROM Firma f WHERE f.firKFakLogo = :firKFakLogo"),
    @NamedQuery(name = "Firma.findByFirKRachLogo", query = "SELECT f FROM Firma f WHERE f.firKRachLogo = :firKRachLogo"),
    @NamedQuery(name = "Firma.findByFirZwLogo", query = "SELECT f FROM Firma f WHERE f.firZwLogo = :firZwLogo"),
    @NamedQuery(name = "Firma.findByFirWzLogo", query = "SELECT f FROM Firma f WHERE f.firWzLogo = :firWzLogo"),
    @NamedQuery(name = "Firma.findByFirNrPzAuto", query = "SELECT f FROM Firma f WHERE f.firNrPzAuto = :firNrPzAuto"),
    @NamedQuery(name = "Firma.findByFirNrPzD", query = "SELECT f FROM Firma f WHERE f.firNrPzD = :firNrPzD"),
    @NamedQuery(name = "Firma.findByFirNrPzDP", query = "SELECT f FROM Firma f WHERE f.firNrPzDP = :firNrPzDP"),
    @NamedQuery(name = "Firma.findByFirNrPzL", query = "SELECT f FROM Firma f WHERE f.firNrPzL = :firNrPzL"),
    @NamedQuery(name = "Firma.findByFirNrPzLP", query = "SELECT f FROM Firma f WHERE f.firNrPzLP = :firNrPzLP"),
    @NamedQuery(name = "Firma.findByFirNrPzS", query = "SELECT f FROM Firma f WHERE f.firNrPzS = :firNrPzS"),
    @NamedQuery(name = "Firma.findByFirNrPzSP", query = "SELECT f FROM Firma f WHERE f.firNrPzSP = :firNrPzSP"),
    @NamedQuery(name = "Firma.findByFirPzLogo", query = "SELECT f FROM Firma f WHERE f.firPzLogo = :firPzLogo"),
    @NamedQuery(name = "Firma.findByFirTypOsoby", query = "SELECT f FROM Firma f WHERE f.firTypOsoby = :firTypOsoby"),
    @NamedQuery(name = "Firma.findByFirPesel", query = "SELECT f FROM Firma f WHERE f.firPesel = :firPesel"),
    @NamedQuery(name = "Firma.findByFirRodzajDok", query = "SELECT f FROM Firma f WHERE f.firRodzajDok = :firRodzajDok"),
    @NamedQuery(name = "Firma.findByFirSeriaNr", query = "SELECT f FROM Firma f WHERE f.firSeriaNr = :firSeriaNr"),
    @NamedQuery(name = "Firma.findByFirNazwisko", query = "SELECT f FROM Firma f WHERE f.firNazwisko = :firNazwisko"),
    @NamedQuery(name = "Firma.findByFirImie1", query = "SELECT f FROM Firma f WHERE f.firImie1 = :firImie1"),
    @NamedQuery(name = "Firma.findByFirDataUrodz", query = "SELECT f FROM Firma f WHERE f.firDataUrodz = :firDataUrodz"),
    @NamedQuery(name = "Firma.findByFirGmina", query = "SELECT f FROM Firma f WHERE f.firGmina = :firGmina"),
    @NamedQuery(name = "Firma.findByFirUrzSerial", query = "SELECT f FROM Firma f WHERE f.firUrzSerial = :firUrzSerial"),
    @NamedQuery(name = "Firma.findByFirPowiat", query = "SELECT f FROM Firma f WHERE f.firPowiat = :firPowiat"),
    @NamedQuery(name = "Firma.findByFirPoczta", query = "SELECT f FROM Firma f WHERE f.firPoczta = :firPoczta"),
    @NamedQuery(name = "Firma.findByFirWojewodztwo", query = "SELECT f FROM Firma f WHERE f.firWojewodztwo = :firWojewodztwo"),
    @NamedQuery(name = "Firma.findByFirKodTerm", query = "SELECT f FROM Firma f WHERE f.firKodTerm = :firKodTerm"),
    @NamedQuery(name = "Firma.findByFirZaklPrChron", query = "SELECT f FROM Firma f WHERE f.firZaklPrChron = :firZaklPrChron"),
    @NamedQuery(name = "Firma.findByFirUbezpWyp", query = "SELECT f FROM Firma f WHERE f.firUbezpWyp = :firUbezpWyp"),
    @NamedQuery(name = "Firma.findByFirUprSwUbChor", query = "SELECT f FROM Firma f WHERE f.firUprSwUbChor = :firUprSwUbChor"),
    @NamedQuery(name = "Firma.findByFirImieOjca", query = "SELECT f FROM Firma f WHERE f.firImieOjca = :firImieOjca"),
    @NamedQuery(name = "Firma.findByFirImieMatki", query = "SELECT f FROM Firma f WHERE f.firImieMatki = :firImieMatki"),
    @NamedQuery(name = "Firma.findByFirOptSerialPrac", query = "SELECT f FROM Firma f WHERE f.firOptSerialPrac = :firOptSerialPrac"),
    @NamedQuery(name = "Firma.findByFirOptSerialOsob", query = "SELECT f FROM Firma f WHERE f.firOptSerialOsob = :firOptSerialOsob"),
    @NamedQuery(name = "Firma.findByFirOptSerialWlas", query = "SELECT f FROM Firma f WHERE f.firOptSerialWlas = :firOptSerialWlas"),
    @NamedQuery(name = "Firma.findByFirTestowa", query = "SELECT f FROM Firma f WHERE f.firTestowa = :firTestowa"),
    @NamedQuery(name = "Firma.findByFirWiadomPar", query = "SELECT f FROM Firma f WHERE f.firWiadomPar = :firWiadomPar"),
    @NamedQuery(name = "Firma.findByFirKonNrIdNaDok", query = "SELECT f FROM Firma f WHERE f.firKonNrIdNaDok = :firKonNrIdNaDok"),
    @NamedQuery(name = "Firma.findByFirEkd", query = "SELECT f FROM Firma f WHERE f.firEkd = :firEkd"),
    @NamedQuery(name = "Firma.findByFirJednBudz", query = "SELECT f FROM Firma f WHERE f.firJednBudz = :firJednBudz"),
    @NamedQuery(name = "Firma.findByFirJednPozaBudz", query = "SELECT f FROM Firma f WHERE f.firJednPozaBudz = :firJednPozaBudz"),
    @NamedQuery(name = "Firma.findByFirOrgZal", query = "SELECT f FROM Firma f WHERE f.firOrgZal = :firOrgZal"),
    @NamedQuery(name = "Firma.findByFirRejestr", query = "SELECT f FROM Firma f WHERE f.firRejestr = :firRejestr"),
    @NamedQuery(name = "Firma.findByFirRejestrData", query = "SELECT f FROM Firma f WHERE f.firRejestrData = :firRejestrData"),
    @NamedQuery(name = "Firma.findByFirRejestrNr", query = "SELECT f FROM Firma f WHERE f.firRejestrNr = :firRejestrNr"),
    @NamedQuery(name = "Firma.findByFirRejestrNazwaO", query = "SELECT f FROM Firma f WHERE f.firRejestrNazwaO = :firRejestrNazwaO"),
    @NamedQuery(name = "Firma.findByFirDataObowUb", query = "SELECT f FROM Firma f WHERE f.firDataObowUb = :firDataObowUb"),
    @NamedQuery(name = "Firma.findByFirDataRozp", query = "SELECT f FROM Firma f WHERE f.firDataRozp = :firDataRozp"),
    @NamedQuery(name = "Firma.findByFirZaPrChrDOt", query = "SELECT f FROM Firma f WHERE f.firZaPrChrDOt = :firZaPrChrDOt"),
    @NamedQuery(name = "Firma.findByFirZaPrChrDUt", query = "SELECT f FROM Firma f WHERE f.firZaPrChrDUt = :firZaPrChrDUt"),
    @NamedQuery(name = "Firma.findByFirAdrDzialInny", query = "SELECT f FROM Firma f WHERE f.firAdrDzialInny = :firAdrDzialInny"),
    @NamedQuery(name = "Firma.findByFirKod2", query = "SELECT f FROM Firma f WHERE f.firKod2 = :firKod2"),
    @NamedQuery(name = "Firma.findByFirMiaSerial2", query = "SELECT f FROM Firma f WHERE f.firMiaSerial2 = :firMiaSerial2"),
    @NamedQuery(name = "Firma.findByFirGmina2", query = "SELECT f FROM Firma f WHERE f.firGmina2 = :firGmina2"),
    @NamedQuery(name = "Firma.findByFirUlica2", query = "SELECT f FROM Firma f WHERE f.firUlica2 = :firUlica2"),
    @NamedQuery(name = "Firma.findByFirDom2", query = "SELECT f FROM Firma f WHERE f.firDom2 = :firDom2"),
    @NamedQuery(name = "Firma.findByFirMieszkanie2", query = "SELECT f FROM Firma f WHERE f.firMieszkanie2 = :firMieszkanie2"),
    @NamedQuery(name = "Firma.findByFirTel2", query = "SELECT f FROM Firma f WHERE f.firTel2 = :firTel2"),
    @NamedQuery(name = "Firma.findByFirFaks2", query = "SELECT f FROM Firma f WHERE f.firFaks2 = :firFaks2"),
    @NamedQuery(name = "Firma.findByFirPanSerial2", query = "SELECT f FROM Firma f WHERE f.firPanSerial2 = :firPanSerial2"),
    @NamedQuery(name = "Firma.findByFirPowiat2", query = "SELECT f FROM Firma f WHERE f.firPowiat2 = :firPowiat2"),
    @NamedQuery(name = "Firma.findByFirPoczta2", query = "SELECT f FROM Firma f WHERE f.firPoczta2 = :firPoczta2"),
    @NamedQuery(name = "Firma.findByFirWojewodztwo2", query = "SELECT f FROM Firma f WHERE f.firWojewodztwo2 = :firWojewodztwo2"),
    @NamedQuery(name = "Firma.findByFirEmail", query = "SELECT f FROM Firma f WHERE f.firEmail = :firEmail"),
    @NamedQuery(name = "Firma.findByFirEmail2", query = "SELECT f FROM Firma f WHERE f.firEmail2 = :firEmail2"),
    @NamedQuery(name = "Firma.findByFirPanSerialK", query = "SELECT f FROM Firma f WHERE f.firPanSerialK = :firPanSerialK"),
    @NamedQuery(name = "Firma.findByFirKodK", query = "SELECT f FROM Firma f WHERE f.firKodK = :firKodK"),
    @NamedQuery(name = "Firma.findByFirMiaSerialK", query = "SELECT f FROM Firma f WHERE f.firMiaSerialK = :firMiaSerialK"),
    @NamedQuery(name = "Firma.findByFirUlicaK", query = "SELECT f FROM Firma f WHERE f.firUlicaK = :firUlicaK"),
    @NamedQuery(name = "Firma.findByFirDomK", query = "SELECT f FROM Firma f WHERE f.firDomK = :firDomK"),
    @NamedQuery(name = "Firma.findByFirMieszkanieK", query = "SELECT f FROM Firma f WHERE f.firMieszkanieK = :firMieszkanieK"),
    @NamedQuery(name = "Firma.findByFirTelK", query = "SELECT f FROM Firma f WHERE f.firTelK = :firTelK"),
    @NamedQuery(name = "Firma.findByFirFaksK", query = "SELECT f FROM Firma f WHERE f.firFaksK = :firFaksK"),
    @NamedQuery(name = "Firma.findByFirEmailK", query = "SELECT f FROM Firma f WHERE f.firEmailK = :firEmailK"),
    @NamedQuery(name = "Firma.findByFirSkrytkaK", query = "SELECT f FROM Firma f WHERE f.firSkrytkaK = :firSkrytkaK"),
    @NamedQuery(name = "Firma.findByFirTeletransK", query = "SELECT f FROM Firma f WHERE f.firTeletransK = :firTeletransK"),
    @NamedQuery(name = "Firma.findByFirGminaK", query = "SELECT f FROM Firma f WHERE f.firGminaK = :firGminaK"),
    @NamedQuery(name = "Firma.findByFirPowiatK", query = "SELECT f FROM Firma f WHERE f.firPowiatK = :firPowiatK"),
    @NamedQuery(name = "Firma.findByFirPocztaK", query = "SELECT f FROM Firma f WHERE f.firPocztaK = :firPocztaK"),
    @NamedQuery(name = "Firma.findByFirWojewodztwoK", query = "SELECT f FROM Firma f WHERE f.firWojewodztwoK = :firWojewodztwoK"),
    @NamedQuery(name = "Firma.findByFirBiuroNip", query = "SELECT f FROM Firma f WHERE f.firBiuroNip = :firBiuroNip"),
    @NamedQuery(name = "Firma.findByFirBiuroRegon", query = "SELECT f FROM Firma f WHERE f.firBiuroRegon = :firBiuroRegon"),
    @NamedQuery(name = "Firma.findByFirBiuroNazwaSkr", query = "SELECT f FROM Firma f WHERE f.firBiuroNazwaSkr = :firBiuroNazwaSkr"),
    @NamedQuery(name = "Firma.findByFirKodUprawn", query = "SELECT f FROM Firma f WHERE f.firKodUprawn = :firKodUprawn"),
    @NamedQuery(name = "Firma.findByFirDataUprawn", query = "SELECT f FROM Firma f WHERE f.firDataUprawn = :firDataUprawn"),
    @NamedQuery(name = "Firma.findByFirPanSerialD", query = "SELECT f FROM Firma f WHERE f.firPanSerialD = :firPanSerialD"),
    @NamedQuery(name = "Firma.findByFirKodD", query = "SELECT f FROM Firma f WHERE f.firKodD = :firKodD"),
    @NamedQuery(name = "Firma.findByFirMiaSerialD", query = "SELECT f FROM Firma f WHERE f.firMiaSerialD = :firMiaSerialD"),
    @NamedQuery(name = "Firma.findByFirUlicaD", query = "SELECT f FROM Firma f WHERE f.firUlicaD = :firUlicaD"),
    @NamedQuery(name = "Firma.findByFirDomD", query = "SELECT f FROM Firma f WHERE f.firDomD = :firDomD"),
    @NamedQuery(name = "Firma.findByFirMieszkanieD", query = "SELECT f FROM Firma f WHERE f.firMieszkanieD = :firMieszkanieD"),
    @NamedQuery(name = "Firma.findByFirTelD", query = "SELECT f FROM Firma f WHERE f.firTelD = :firTelD"),
    @NamedQuery(name = "Firma.findByFirFaksD", query = "SELECT f FROM Firma f WHERE f.firFaksD = :firFaksD"),
    @NamedQuery(name = "Firma.findByFirGminaD", query = "SELECT f FROM Firma f WHERE f.firGminaD = :firGminaD"),
    @NamedQuery(name = "Firma.findByFirPowiatD", query = "SELECT f FROM Firma f WHERE f.firPowiatD = :firPowiatD"),
    @NamedQuery(name = "Firma.findByFirPocztaD", query = "SELECT f FROM Firma f WHERE f.firPocztaD = :firPocztaD"),
    @NamedQuery(name = "Firma.findByFirWojewodztwoD", query = "SELECT f FROM Firma f WHERE f.firWojewodztwoD = :firWojewodztwoD"),
    @NamedQuery(name = "Firma.findByFirBanSerial3", query = "SELECT f FROM Firma f WHERE f.firBanSerial3 = :firBanSerial3"),
    @NamedQuery(name = "Firma.findByFirNrKonta3", query = "SELECT f FROM Firma f WHERE f.firNrKonta3 = :firNrKonta3"),
    @NamedQuery(name = "Firma.findByFirBanSerial4", query = "SELECT f FROM Firma f WHERE f.firBanSerial4 = :firBanSerial4"),
    @NamedQuery(name = "Firma.findByFirNrKonta4", query = "SELECT f FROM Firma f WHERE f.firNrKonta4 = :firNrKonta4"),
    @NamedQuery(name = "Firma.findByFirMiejsceUr", query = "SELECT f FROM Firma f WHERE f.firMiejsceUr = :firMiejsceUr"),
    @NamedQuery(name = "Firma.findByFirObywatelstwo", query = "SELECT f FROM Firma f WHERE f.firObywatelstwo = :firObywatelstwo"),
    @NamedQuery(name = "Firma.findByFirImie2", query = "SELECT f FROM Firma f WHERE f.firImie2 = :firImie2"),
    @NamedQuery(name = "Firma.findByFirKodRodzUpr", query = "SELECT f FROM Firma f WHERE f.firKodRodzUpr = :firKodRodzUpr"),
    @NamedQuery(name = "Firma.findByFirWiadomParTresc", query = "SELECT f FROM Firma f WHERE f.firWiadomParTresc = :firWiadomParTresc"),
    @NamedQuery(name = "Firma.findByFirAdrTsam2", query = "SELECT f FROM Firma f WHERE f.firAdrTsam2 = :firAdrTsam2"),
    @NamedQuery(name = "Firma.findByFirAdrTsamK", query = "SELECT f FROM Firma f WHERE f.firAdrTsamK = :firAdrTsamK"),
    @NamedQuery(name = "Firma.findByFirAdrTsamD", query = "SELECT f FROM Firma f WHERE f.firAdrTsamD = :firAdrTsamD"),
    @NamedQuery(name = "Firma.findByFirDodChar1", query = "SELECT f FROM Firma f WHERE f.firDodChar1 = :firDodChar1"),
    @NamedQuery(name = "Firma.findByFirDodChar2", query = "SELECT f FROM Firma f WHERE f.firDodChar2 = :firDodChar2"),
    @NamedQuery(name = "Firma.findByFirDodChar3", query = "SELECT f FROM Firma f WHERE f.firDodChar3 = :firDodChar3"),
    @NamedQuery(name = "Firma.findByFirDodChar4", query = "SELECT f FROM Firma f WHERE f.firDodChar4 = :firDodChar4"),
    @NamedQuery(name = "Firma.findByFirDodChar5", query = "SELECT f FROM Firma f WHERE f.firDodChar5 = :firDodChar5"),
    @NamedQuery(name = "Firma.findByFirDodChar6", query = "SELECT f FROM Firma f WHERE f.firDodChar6 = :firDodChar6"),
    @NamedQuery(name = "Firma.findByFirDodChar7", query = "SELECT f FROM Firma f WHERE f.firDodChar7 = :firDodChar7"),
    @NamedQuery(name = "Firma.findByFirDodChar8", query = "SELECT f FROM Firma f WHERE f.firDodChar8 = :firDodChar8"),
    @NamedQuery(name = "Firma.findByFirDodNum1", query = "SELECT f FROM Firma f WHERE f.firDodNum1 = :firDodNum1"),
    @NamedQuery(name = "Firma.findByFirDodNum2", query = "SELECT f FROM Firma f WHERE f.firDodNum2 = :firDodNum2"),
    @NamedQuery(name = "Firma.findByFirDodNum3", query = "SELECT f FROM Firma f WHERE f.firDodNum3 = :firDodNum3"),
    @NamedQuery(name = "Firma.findByFirDodNum4", query = "SELECT f FROM Firma f WHERE f.firDodNum4 = :firDodNum4"),
    @NamedQuery(name = "Firma.findByFirDodNum5", query = "SELECT f FROM Firma f WHERE f.firDodNum5 = :firDodNum5"),
    @NamedQuery(name = "Firma.findByFirDodNum6", query = "SELECT f FROM Firma f WHERE f.firDodNum6 = :firDodNum6"),
    @NamedQuery(name = "Firma.findByFirDodNum7", query = "SELECT f FROM Firma f WHERE f.firDodNum7 = :firDodNum7"),
    @NamedQuery(name = "Firma.findByFirDodNum8", query = "SELECT f FROM Firma f WHERE f.firDodNum8 = :firDodNum8"),
    @NamedQuery(name = "Firma.findByFirBiuro", query = "SELECT f FROM Firma f WHERE f.firBiuro = :firBiuro"),
    @NamedQuery(name = "Firma.findByFirUbezpWypC", query = "SELECT f FROM Firma f WHERE f.firUbezpWypC = :firUbezpWypC"),
    @NamedQuery(name = "Firma.findByFirUbezpFpC", query = "SELECT f FROM Firma f WHERE f.firUbezpFpC = :firUbezpFpC"),
    @NamedQuery(name = "Firma.findByFirUbezpFgspC", query = "SELECT f FROM Firma f WHERE f.firUbezpFgspC = :firUbezpFgspC"),
    @NamedQuery(name = "Firma.findByFirUbezpFp", query = "SELECT f FROM Firma f WHERE f.firUbezpFp = :firUbezpFp"),
    @NamedQuery(name = "Firma.findByFirUbezpFgsp", query = "SELECT f FROM Firma f WHERE f.firUbezpFgsp = :firUbezpFgsp"),
    @NamedQuery(name = "Firma.findByFirOptSerialWspo", query = "SELECT f FROM Firma f WHERE f.firOptSerialWspo = :firOptSerialWspo"),
    @NamedQuery(name = "Firma.findByFirOptSerialDod1", query = "SELECT f FROM Firma f WHERE f.firOptSerialDod1 = :firOptSerialDod1"),
    @NamedQuery(name = "Firma.findByFirOptSerialDod2", query = "SELECT f FROM Firma f WHERE f.firOptSerialDod2 = :firOptSerialDod2"),
    @NamedQuery(name = "Firma.findByFirDodSerial1", query = "SELECT f FROM Firma f WHERE f.firDodSerial1 = :firDodSerial1"),
    @NamedQuery(name = "Firma.findByFirDodVchar1", query = "SELECT f FROM Firma f WHERE f.firDodVchar1 = :firDodVchar1"),
    @NamedQuery(name = "Firma.findByFirDodSerial2", query = "SELECT f FROM Firma f WHERE f.firDodSerial2 = :firDodSerial2"),
    @NamedQuery(name = "Firma.findByFirDodVchar2", query = "SELECT f FROM Firma f WHERE f.firDodVchar2 = :firDodVchar2"),
    @NamedQuery(name = "Firma.findByFirDodChar9", query = "SELECT f FROM Firma f WHERE f.firDodChar9 = :firDodChar9"),
    @NamedQuery(name = "Firma.findByFirDodChar10", query = "SELECT f FROM Firma f WHERE f.firDodChar10 = :firDodChar10"),
    @NamedQuery(name = "Firma.findByFirDodChar11", query = "SELECT f FROM Firma f WHERE f.firDodChar11 = :firDodChar11"),
    @NamedQuery(name = "Firma.findByFirDodChar12", query = "SELECT f FROM Firma f WHERE f.firDodChar12 = :firDodChar12"),
    @NamedQuery(name = "Firma.findByFirDodChar13", query = "SELECT f FROM Firma f WHERE f.firDodChar13 = :firDodChar13"),
    @NamedQuery(name = "Firma.findByFirDodChar14", query = "SELECT f FROM Firma f WHERE f.firDodChar14 = :firDodChar14"),
    @NamedQuery(name = "Firma.findByFirDodChar15", query = "SELECT f FROM Firma f WHERE f.firDodChar15 = :firDodChar15"),
    @NamedQuery(name = "Firma.findByFirDodChar16", query = "SELECT f FROM Firma f WHERE f.firDodChar16 = :firDodChar16"),
    @NamedQuery(name = "Firma.findByFirDodVchar3", query = "SELECT f FROM Firma f WHERE f.firDodVchar3 = :firDodVchar3"),
    @NamedQuery(name = "Firma.findByFirDodVchar4", query = "SELECT f FROM Firma f WHERE f.firDodVchar4 = :firDodVchar4"),
    @NamedQuery(name = "Firma.findByFirDodVchar5", query = "SELECT f FROM Firma f WHERE f.firDodVchar5 = :firDodVchar5"),
    @NamedQuery(name = "Firma.findByFirDodVchar6", query = "SELECT f FROM Firma f WHERE f.firDodVchar6 = :firDodVchar6"),
    @NamedQuery(name = "Firma.findByFirUrzSerialV", query = "SELECT f FROM Firma f WHERE f.firUrzSerialV = :firUrzSerialV"),
    @NamedQuery(name = "Firma.findByFirDodVchar7", query = "SELECT f FROM Firma f WHERE f.firDodVchar7 = :firDodVchar7"),
    @NamedQuery(name = "Firma.findByFirDodVchar8", query = "SELECT f FROM Firma f WHERE f.firDodVchar8 = :firDodVchar8"),
    @NamedQuery(name = "Firma.findByFirDodVchar9", query = "SELECT f FROM Firma f WHERE f.firDodVchar9 = :firDodVchar9"),
    @NamedQuery(name = "Firma.findByFirDodVchar10", query = "SELECT f FROM Firma f WHERE f.firDodVchar10 = :firDodVchar10"),
    @NamedQuery(name = "Firma.findByFirNusp", query = "SELECT f FROM Firma f WHERE f.firNusp = :firNusp"),
    @NamedQuery(name = "Firma.findByFirDodChar17", query = "SELECT f FROM Firma f WHERE f.firDodChar17 = :firDodChar17"),
    @NamedQuery(name = "Firma.findByFirDodChar18", query = "SELECT f FROM Firma f WHERE f.firDodChar18 = :firDodChar18"),
    @NamedQuery(name = "Firma.findByFirDodChar19", query = "SELECT f FROM Firma f WHERE f.firDodChar19 = :firDodChar19"),
    @NamedQuery(name = "Firma.findByFirDodChar20", query = "SELECT f FROM Firma f WHERE f.firDodChar20 = :firDodChar20"),
    @NamedQuery(name = "Firma.findByFirDodChar21", query = "SELECT f FROM Firma f WHERE f.firDodChar21 = :firDodChar21"),
    @NamedQuery(name = "Firma.findByFirDodChar22", query = "SELECT f FROM Firma f WHERE f.firDodChar22 = :firDodChar22"),
    @NamedQuery(name = "Firma.findByFirDodChar23", query = "SELECT f FROM Firma f WHERE f.firDodChar23 = :firDodChar23"),
    @NamedQuery(name = "Firma.findByFirDodChar24", query = "SELECT f FROM Firma f WHERE f.firDodChar24 = :firDodChar24"),
    @NamedQuery(name = "Firma.findByFirUstawienia", query = "SELECT f FROM Firma f WHERE f.firUstawienia = :firUstawienia"),
    @NamedQuery(name = "Firma.findByFirUstawieniaKs", query = "SELECT f FROM Firma f WHERE f.firUstawieniaKs = :firUstawieniaKs"),
    @NamedQuery(name = "Firma.findByFirUstawieniaKp", query = "SELECT f FROM Firma f WHERE f.firUstawieniaKp = :firUstawieniaKp"),
    @NamedQuery(name = "Firma.findByFirDodVchar11", query = "SELECT f FROM Firma f WHERE f.firDodVchar11 = :firDodVchar11"),
    @NamedQuery(name = "Firma.findByFirDodVchar12", query = "SELECT f FROM Firma f WHERE f.firDodVchar12 = :firDodVchar12"),
    @NamedQuery(name = "Firma.findByFirDodInt1", query = "SELECT f FROM Firma f WHERE f.firDodInt1 = :firDodInt1"),
    @NamedQuery(name = "Firma.findByFirDodInt2", query = "SELECT f FROM Firma f WHERE f.firDodInt2 = :firDodInt2"),
    @NamedQuery(name = "Firma.findByFirDodInt3", query = "SELECT f FROM Firma f WHERE f.firDodInt3 = :firDodInt3"),
    @NamedQuery(name = "Firma.findByFirDodInt4", query = "SELECT f FROM Firma f WHERE f.firDodInt4 = :firDodInt4")})
public class Firma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "fir_serial", nullable = false)
    private Integer firSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "fir_nazwa_skr", nullable = false, length = 64)
    private String firNazwaSkr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "fir_nazwa_pel", nullable = false, length = 128)
    private String firNazwaPel;
    @Size(max = 64)
    @Column(name = "fir_nip", length = 64)
    private String firNip;
    @Size(max = 64)
    @Column(name = "fir_regon", length = 64)
    private String firRegon;
    @Column(name = "fir_pan_serial")
    private Integer firPanSerial;
    @Column(name = "fir_mia_serial")
    private Integer firMiaSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "fir_kod", nullable = false, length = 64)
    private String firKod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "fir_ulica", nullable = false, length = 64)
    private String firUlica;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "fir_dom", nullable = false, length = 64)
    private String firDom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "fir_mieszkanie", nullable = false, length = 64)
    private String firMieszkanie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "fir_tel", nullable = false, length = 64)
    private String firTel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "fir_faks", nullable = false, length = 64)
    private String firFaks;
    @Column(name = "fir_knt_serial")
    private Integer firKntSerial;
    @Size(max = 128)
    @Column(name = "fir_rodz_dzial", length = 128)
    private String firRodzDzial;
    @Column(name = "fir_fak_ban_serial")
    private Integer firFakBanSerial;
    @Size(max = 64)
    @Column(name = "fir_fak_nr_konta", length = 64)
    private String firFakNrKonta;
    @Column(name = "fir_zap_ban_serial")
    private Integer firZapBanSerial;
    @Size(max = 64)
    @Column(name = "fir_zap_nr_konta", length = 64)
    private String firZapNrKonta;
    @Size(max = 64)
    @Column(name = "fir_fak_wiadom", length = 64)
    private String firFakWiadom;
    @Size(max = 1)
    @Column(name = "fir_nr_fak_auto", length = 1)
    private String firNrFakAuto;
    @Size(max = 64)
    @Column(name = "fir_nr_fak_d", length = 64)
    private String firNrFakD;
    @Column(name = "fir_nr_fak_d_p")
    private Integer firNrFakDP;
    @Size(max = 64)
    @Column(name = "fir_nr_fak_l", length = 64)
    private String firNrFakL;
    @Column(name = "fir_nr_fak_l_p")
    private Integer firNrFakLP;
    @Size(max = 64)
    @Column(name = "fir_nr_fak_s", length = 64)
    private String firNrFakS;
    @Column(name = "fir_nr_fak_s_p")
    private Integer firNrFakSP;
    @Size(max = 1)
    @Column(name = "fir_nr_rach_auto", length = 1)
    private String firNrRachAuto;
    @Size(max = 64)
    @Column(name = "fir_nr_rach_s", length = 64)
    private String firNrRachS;
    @Column(name = "fir_data_plat_roz")
    private Integer firDataPlatRoz;
    @Size(max = 1)
    @Column(name = "fir_nr_k_rach_auto", length = 1)
    private String firNrKRachAuto;
    @Size(max = 64)
    @Column(name = "fir_nr_k_rach_s", length = 64)
    private String firNrKRachS;
    @Size(max = 1)
    @Column(name = "fir_nr_k_fak_auto", length = 1)
    private String firNrKFakAuto;
    @Size(max = 64)
    @Column(name = "fir_nr_k_fak_s", length = 64)
    private String firNrKFakS;
    @Column(name = "fir_nr_rach_s_p")
    private Integer firNrRachSP;
    @Size(max = 64)
    @Column(name = "fir_nr_rach_d", length = 64)
    private String firNrRachD;
    @Column(name = "fir_nr_rach_d_p")
    private Integer firNrRachDP;
    @Size(max = 64)
    @Column(name = "fir_nr_rach_l", length = 64)
    private String firNrRachL;
    @Column(name = "fir_nr_rach_l_p")
    private Integer firNrRachLP;
    @Column(name = "fir_nr_k_rach_s_p")
    private Integer firNrKRachSP;
    @Size(max = 64)
    @Column(name = "fir_nr_k_rach_d", length = 64)
    private String firNrKRachD;
    @Column(name = "fir_nr_k_rach_d_p")
    private Integer firNrKRachDP;
    @Size(max = 64)
    @Column(name = "fir_nr_k_rach_l", length = 64)
    private String firNrKRachL;
    @Column(name = "fir_nr_k_rach_l_p")
    private Integer firNrKRachLP;
    @Column(name = "fir_nr_k_fak_s_p")
    private Integer firNrKFakSP;
    @Size(max = 64)
    @Column(name = "fir_nr_k_fak_d", length = 64)
    private String firNrKFakD;
    @Column(name = "fir_nr_k_fak_d_p")
    private Integer firNrKFakDP;
    @Size(max = 64)
    @Column(name = "fir_nr_k_fak_l", length = 64)
    private String firNrKFakL;
    @Column(name = "fir_nr_k_fak_l_p")
    private Integer firNrKFakLP;
    @Size(max = 1)
    @Column(name = "fir_nr_zw_auto", length = 1)
    private String firNrZwAuto;
    @Size(max = 64)
    @Column(name = "fir_nr_zw_s", length = 64)
    private String firNrZwS;
    @Column(name = "fir_nr_zw_s_p")
    private Integer firNrZwSP;
    @Size(max = 64)
    @Column(name = "fir_nr_zw_d", length = 64)
    private String firNrZwD;
    @Column(name = "fir_nr_zw_d_p")
    private Integer firNrZwDP;
    @Size(max = 64)
    @Column(name = "fir_nr_zw_l", length = 64)
    private String firNrZwL;
    @Column(name = "fir_nr_zw_l_p")
    private Integer firNrZwLP;
    @Size(max = 1)
    @Column(name = "fir_nr_wz_auto", length = 1)
    private String firNrWzAuto;
    @Size(max = 64)
    @Column(name = "fir_nr_wz_s", length = 64)
    private String firNrWzS;
    @Column(name = "fir_nr_wz_s_p")
    private Integer firNrWzSP;
    @Size(max = 64)
    @Column(name = "fir_nr_wz_d", length = 64)
    private String firNrWzD;
    @Column(name = "fir_nr_wz_d_p")
    private Integer firNrWzDP;
    @Size(max = 64)
    @Column(name = "fir_nr_wz_l", length = 64)
    private String firNrWzL;
    @Column(name = "fir_nr_wz_l_p")
    private Integer firNrWzLP;
    @Size(max = 1)
    @Column(name = "fir_fak_logo", length = 1)
    private String firFakLogo;
    @Size(max = 1)
    @Column(name = "fir_rach_logo", length = 1)
    private String firRachLogo;
    @Size(max = 1)
    @Column(name = "fir_k_fak_logo", length = 1)
    private String firKFakLogo;
    @Size(max = 1)
    @Column(name = "fir_k_rach_logo", length = 1)
    private String firKRachLogo;
    @Size(max = 1)
    @Column(name = "fir_zw_logo", length = 1)
    private String firZwLogo;
    @Size(max = 1)
    @Column(name = "fir_wz_logo", length = 1)
    private String firWzLogo;
    @Size(max = 1)
    @Column(name = "fir_nr_pz_auto", length = 1)
    private String firNrPzAuto;
    @Size(max = 64)
    @Column(name = "fir_nr_pz_d", length = 64)
    private String firNrPzD;
    @Column(name = "fir_nr_pz_d_p")
    private Integer firNrPzDP;
    @Size(max = 64)
    @Column(name = "fir_nr_pz_l", length = 64)
    private String firNrPzL;
    @Column(name = "fir_nr_pz_l_p")
    private Integer firNrPzLP;
    @Size(max = 64)
    @Column(name = "fir_nr_pz_s", length = 64)
    private String firNrPzS;
    @Column(name = "fir_nr_pz_s_p")
    private Integer firNrPzSP;
    @Size(max = 1)
    @Column(name = "fir_pz_logo", length = 1)
    private String firPzLogo;
    @Size(max = 1)
    @Column(name = "fir_typ_osoby", length = 1)
    private String firTypOsoby;
    @Size(max = 64)
    @Column(name = "fir_pesel", length = 64)
    private String firPesel;
    @Size(max = 1)
    @Column(name = "fir_rodzaj_dok", length = 1)
    private String firRodzajDok;
    @Size(max = 64)
    @Column(name = "fir_seria_nr", length = 64)
    private String firSeriaNr;
    @Size(max = 64)
    @Column(name = "fir_nazwisko", length = 64)
    private String firNazwisko;
    @Size(max = 64)
    @Column(name = "fir_imie_1", length = 64)
    private String firImie1;
    @Column(name = "fir_data_urodz")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firDataUrodz;
    @Size(max = 64)
    @Column(name = "fir_gmina", length = 64)
    private String firGmina;
    @Column(name = "fir_urz_serial")
    private Integer firUrzSerial;
    @Size(max = 64)
    @Column(name = "fir_powiat", length = 64)
    private String firPowiat;
    @Size(max = 64)
    @Column(name = "fir_poczta", length = 64)
    private String firPoczta;
    @Size(max = 64)
    @Column(name = "fir_wojewodztwo", length = 64)
    private String firWojewodztwo;
    @Size(max = 1)
    @Column(name = "fir_kod_term", length = 1)
    private String firKodTerm;
    @Size(max = 1)
    @Column(name = "fir_zakl_pr_chron", length = 1)
    private String firZaklPrChron;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "fir_ubezp_wyp", nullable = false, precision = 7, scale = 4)
    private BigDecimal firUbezpWyp;
    @Size(max = 1)
    @Column(name = "fir_upr_sw_ub_chor", length = 1)
    private String firUprSwUbChor;
    @Size(max = 64)
    @Column(name = "fir_imie_ojca", length = 64)
    private String firImieOjca;
    @Size(max = 64)
    @Column(name = "fir_imie_matki", length = 64)
    private String firImieMatki;
    @Column(name = "fir_opt_serial_prac")
    private Integer firOptSerialPrac;
    @Column(name = "fir_opt_serial_osob")
    private Integer firOptSerialOsob;
    @Column(name = "fir_opt_serial_wlas")
    private Integer firOptSerialWlas;
    @Size(max = 1)
    @Column(name = "fir_testowa", length = 1)
    private String firTestowa;
    @Size(max = 1)
    @Column(name = "fir_wiadom_par", length = 1)
    private String firWiadomPar;
    @Size(max = 1)
    @Column(name = "fir_kon_nr_id_na_dok", length = 1)
    private String firKonNrIdNaDok;
    @Size(max = 64)
    @Column(name = "fir_ekd", length = 64)
    private String firEkd;
    @Size(max = 1)
    @Column(name = "fir_jedn_budz", length = 1)
    private String firJednBudz;
    @Size(max = 1)
    @Column(name = "fir_jedn_poza_budz", length = 1)
    private String firJednPozaBudz;
    @Size(max = 64)
    @Column(name = "fir_org_zal", length = 64)
    private String firOrgZal;
    @Size(max = 1)
    @Column(name = "fir_rejestr", length = 1)
    private String firRejestr;
    @Column(name = "fir_rejestr_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firRejestrData;
    @Size(max = 64)
    @Column(name = "fir_rejestr_nr", length = 64)
    private String firRejestrNr;
    @Size(max = 64)
    @Column(name = "fir_rejestr_nazwa_o", length = 64)
    private String firRejestrNazwaO;
    @Column(name = "fir_data_obow_ub")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firDataObowUb;
    @Column(name = "fir_data_rozp")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firDataRozp;
    @Column(name = "fir_za_pr_chr_d_ot")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firZaPrChrDOt;
    @Column(name = "fir_za_pr_chr_d_ut")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firZaPrChrDUt;
    @Size(max = 1)
    @Column(name = "fir_adr_dzial_inny", length = 1)
    private String firAdrDzialInny;
    @Size(max = 64)
    @Column(name = "fir_kod_2", length = 64)
    private String firKod2;
    @Column(name = "fir_mia_serial_2")
    private Integer firMiaSerial2;
    @Size(max = 64)
    @Column(name = "fir_gmina_2", length = 64)
    private String firGmina2;
    @Size(max = 64)
    @Column(name = "fir_ulica_2", length = 64)
    private String firUlica2;
    @Size(max = 64)
    @Column(name = "fir_dom_2", length = 64)
    private String firDom2;
    @Size(max = 64)
    @Column(name = "fir_mieszkanie_2", length = 64)
    private String firMieszkanie2;
    @Size(max = 64)
    @Column(name = "fir_tel_2", length = 64)
    private String firTel2;
    @Size(max = 64)
    @Column(name = "fir_faks_2", length = 64)
    private String firFaks2;
    @Column(name = "fir_pan_serial_2")
    private Integer firPanSerial2;
    @Size(max = 64)
    @Column(name = "fir_powiat_2", length = 64)
    private String firPowiat2;
    @Size(max = 64)
    @Column(name = "fir_poczta_2", length = 64)
    private String firPoczta2;
    @Size(max = 64)
    @Column(name = "fir_wojewodztwo_2", length = 64)
    private String firWojewodztwo2;
    @Size(max = 64)
    @Column(name = "fir_email", length = 64)
    private String firEmail;
    @Size(max = 64)
    @Column(name = "fir_email_2", length = 64)
    private String firEmail2;
    @Column(name = "fir_pan_serial_k")
    private Integer firPanSerialK;
    @Size(max = 64)
    @Column(name = "fir_kod_k", length = 64)
    private String firKodK;
    @Column(name = "fir_mia_serial_k")
    private Integer firMiaSerialK;
    @Size(max = 64)
    @Column(name = "fir_ulica_k", length = 64)
    private String firUlicaK;
    @Size(max = 64)
    @Column(name = "fir_dom_k", length = 64)
    private String firDomK;
    @Size(max = 64)
    @Column(name = "fir_mieszkanie_k", length = 64)
    private String firMieszkanieK;
    @Size(max = 64)
    @Column(name = "fir_tel_k", length = 64)
    private String firTelK;
    @Size(max = 64)
    @Column(name = "fir_faks_k", length = 64)
    private String firFaksK;
    @Size(max = 64)
    @Column(name = "fir_email_k", length = 64)
    private String firEmailK;
    @Size(max = 64)
    @Column(name = "fir_skrytka_k", length = 64)
    private String firSkrytkaK;
    @Size(max = 64)
    @Column(name = "fir_teletrans_k", length = 64)
    private String firTeletransK;
    @Size(max = 64)
    @Column(name = "fir_gmina_k", length = 64)
    private String firGminaK;
    @Size(max = 64)
    @Column(name = "fir_powiat_k", length = 64)
    private String firPowiatK;
    @Size(max = 64)
    @Column(name = "fir_poczta_k", length = 64)
    private String firPocztaK;
    @Size(max = 64)
    @Column(name = "fir_wojewodztwo_k", length = 64)
    private String firWojewodztwoK;
    @Size(max = 64)
    @Column(name = "fir_biuro_nip", length = 64)
    private String firBiuroNip;
    @Size(max = 64)
    @Column(name = "fir_biuro_regon", length = 64)
    private String firBiuroRegon;
    @Size(max = 64)
    @Column(name = "fir_biuro_nazwa_skr", length = 64)
    private String firBiuroNazwaSkr;
    @Size(max = 2)
    @Column(name = "fir_kod_uprawn", length = 2)
    private String firKodUprawn;
    @Column(name = "fir_data_uprawn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firDataUprawn;
    @Column(name = "fir_pan_serial_d")
    private Integer firPanSerialD;
    @Size(max = 64)
    @Column(name = "fir_kod_d", length = 64)
    private String firKodD;
    @Column(name = "fir_mia_serial_d")
    private Integer firMiaSerialD;
    @Size(max = 64)
    @Column(name = "fir_ulica_d", length = 64)
    private String firUlicaD;
    @Size(max = 64)
    @Column(name = "fir_dom_d", length = 64)
    private String firDomD;
    @Size(max = 64)
    @Column(name = "fir_mieszkanie_d", length = 64)
    private String firMieszkanieD;
    @Size(max = 64)
    @Column(name = "fir_tel_d", length = 64)
    private String firTelD;
    @Size(max = 64)
    @Column(name = "fir_faks_d", length = 64)
    private String firFaksD;
    @Size(max = 64)
    @Column(name = "fir_gmina_d", length = 64)
    private String firGminaD;
    @Size(max = 64)
    @Column(name = "fir_powiat_d", length = 64)
    private String firPowiatD;
    @Size(max = 64)
    @Column(name = "fir_poczta_d", length = 64)
    private String firPocztaD;
    @Size(max = 64)
    @Column(name = "fir_wojewodztwo_d", length = 64)
    private String firWojewodztwoD;
    @Column(name = "fir_ban_serial_3")
    private Integer firBanSerial3;
    @Size(max = 64)
    @Column(name = "fir_nr_konta_3", length = 64)
    private String firNrKonta3;
    @Column(name = "fir_ban_serial_4")
    private Integer firBanSerial4;
    @Size(max = 64)
    @Column(name = "fir_nr_konta_4", length = 64)
    private String firNrKonta4;
    @Size(max = 64)
    @Column(name = "fir_miejsce_ur", length = 64)
    private String firMiejsceUr;
    @Size(max = 64)
    @Column(name = "fir_obywatelstwo", length = 64)
    private String firObywatelstwo;
    @Size(max = 64)
    @Column(name = "fir_imie_2", length = 64)
    private String firImie2;
    @Size(max = 2)
    @Column(name = "fir_kod_rodz_upr", length = 2)
    private String firKodRodzUpr;
    @Size(max = 64)
    @Column(name = "fir_wiadom_par_tresc", length = 64)
    private String firWiadomParTresc;
    @Size(max = 1)
    @Column(name = "fir_adr_tsam_2", length = 1)
    private String firAdrTsam2;
    @Size(max = 1)
    @Column(name = "fir_adr_tsam_k", length = 1)
    private String firAdrTsamK;
    @Size(max = 1)
    @Column(name = "fir_adr_tsam_d", length = 1)
    private String firAdrTsamD;
    @Size(max = 1)
    @Column(name = "fir_dod_char_1", length = 1)
    private String firDodChar1;
    @Size(max = 1)
    @Column(name = "fir_dod_char_2", length = 1)
    private String firDodChar2;
    @Size(max = 1)
    @Column(name = "fir_dod_char_3", length = 1)
    private String firDodChar3;
    @Size(max = 1)
    @Column(name = "fir_dod_char_4", length = 1)
    private String firDodChar4;
    @Size(max = 1)
    @Column(name = "fir_dod_char_5", length = 1)
    private String firDodChar5;
    @Size(max = 1)
    @Column(name = "fir_dod_char_6", length = 1)
    private String firDodChar6;
    @Size(max = 1)
    @Column(name = "fir_dod_char_7", length = 1)
    private String firDodChar7;
    @Size(max = 1)
    @Column(name = "fir_dod_char_8", length = 1)
    private String firDodChar8;
    @Column(name = "fir_dod_num_1", precision = 17, scale = 6)
    private BigDecimal firDodNum1;
    @Column(name = "fir_dod_num_2", precision = 17, scale = 6)
    private BigDecimal firDodNum2;
    @Column(name = "fir_dod_num_3", precision = 17, scale = 6)
    private BigDecimal firDodNum3;
    @Column(name = "fir_dod_num_4", precision = 17, scale = 6)
    private BigDecimal firDodNum4;
    @Column(name = "fir_dod_num_5", precision = 17, scale = 6)
    private BigDecimal firDodNum5;
    @Column(name = "fir_dod_num_6", precision = 17, scale = 6)
    private BigDecimal firDodNum6;
    @Column(name = "fir_dod_num_7", precision = 17, scale = 6)
    private BigDecimal firDodNum7;
    @Column(name = "fir_dod_num_8", precision = 17, scale = 6)
    private BigDecimal firDodNum8;
    @Size(max = 1)
    @Column(name = "fir_biuro", length = 1)
    private String firBiuro;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "fir_ubezp_wyp_c", nullable = false, length = 1)
    private String firUbezpWypC;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "fir_ubezp_fp_c", nullable = false, length = 1)
    private String firUbezpFpC;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "fir_ubezp_fgsp_c", nullable = false, length = 1)
    private String firUbezpFgspC;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fir_ubezp_fp", nullable = false, precision = 7, scale = 4)
    private BigDecimal firUbezpFp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fir_ubezp_fgsp", nullable = false, precision = 7, scale = 4)
    private BigDecimal firUbezpFgsp;
    @Column(name = "fir_opt_serial_wspo")
    private Integer firOptSerialWspo;
    @Column(name = "fir_opt_serial_dod1")
    private Integer firOptSerialDod1;
    @Column(name = "fir_opt_serial_dod2")
    private Integer firOptSerialDod2;
    @Column(name = "fir_dod_serial_1")
    private Integer firDodSerial1;
    @Size(max = 64)
    @Column(name = "fir_dod_vchar_1", length = 64)
    private String firDodVchar1;
    @Column(name = "fir_dod_serial_2")
    private Integer firDodSerial2;
    @Size(max = 64)
    @Column(name = "fir_dod_vchar_2", length = 64)
    private String firDodVchar2;
    @Size(max = 1)
    @Column(name = "fir_dod_char_9", length = 1)
    private String firDodChar9;
    @Size(max = 1)
    @Column(name = "fir_dod_char_10", length = 1)
    private String firDodChar10;
    @Size(max = 1)
    @Column(name = "fir_dod_char_11", length = 1)
    private String firDodChar11;
    @Size(max = 1)
    @Column(name = "fir_dod_char_12", length = 1)
    private String firDodChar12;
    @Size(max = 1)
    @Column(name = "fir_dod_char_13", length = 1)
    private String firDodChar13;
    @Size(max = 1)
    @Column(name = "fir_dod_char_14", length = 1)
    private String firDodChar14;
    @Size(max = 1)
    @Column(name = "fir_dod_char_15", length = 1)
    private String firDodChar15;
    @Size(max = 1)
    @Column(name = "fir_dod_char_16", length = 1)
    private String firDodChar16;
    @Size(max = 64)
    @Column(name = "fir_dod_vchar_3", length = 64)
    private String firDodVchar3;
    @Size(max = 64)
    @Column(name = "fir_dod_vchar_4", length = 64)
    private String firDodVchar4;
    @Size(max = 64)
    @Column(name = "fir_dod_vchar_5", length = 64)
    private String firDodVchar5;
    @Size(max = 64)
    @Column(name = "fir_dod_vchar_6", length = 64)
    private String firDodVchar6;
    @Column(name = "fir_urz_serial_v")
    private Integer firUrzSerialV;
    @Size(max = 64)
    @Column(name = "fir_dod_vchar_7", length = 64)
    private String firDodVchar7;
    @Size(max = 64)
    @Column(name = "fir_dod_vchar_8", length = 64)
    private String firDodVchar8;
    @Size(max = 64)
    @Column(name = "fir_dod_vchar_9", length = 64)
    private String firDodVchar9;
    @Size(max = 64)
    @Column(name = "fir_dod_vchar_10", length = 64)
    private String firDodVchar10;
    @Size(max = 64)
    @Column(name = "fir_nusp", length = 64)
    private String firNusp;
    @Size(max = 1)
    @Column(name = "fir_dod_char_17", length = 1)
    private String firDodChar17;
    @Size(max = 1)
    @Column(name = "fir_dod_char_18", length = 1)
    private String firDodChar18;
    @Size(max = 1)
    @Column(name = "fir_dod_char_19", length = 1)
    private String firDodChar19;
    @Size(max = 1)
    @Column(name = "fir_dod_char_20", length = 1)
    private String firDodChar20;
    @Size(max = 1)
    @Column(name = "fir_dod_char_21", length = 1)
    private String firDodChar21;
    @Size(max = 1)
    @Column(name = "fir_dod_char_22", length = 1)
    private String firDodChar22;
    @Size(max = 1)
    @Column(name = "fir_dod_char_23", length = 1)
    private String firDodChar23;
    @Size(max = 1)
    @Column(name = "fir_dod_char_24", length = 1)
    private String firDodChar24;
    @Size(max = 128)
    @Column(name = "fir_ustawienia", length = 128)
    private String firUstawienia;
    @Size(max = 128)
    @Column(name = "fir_ustawienia_ks", length = 128)
    private String firUstawieniaKs;
    @Size(max = 128)
    @Column(name = "fir_ustawienia_kp", length = 128)
    private String firUstawieniaKp;
    @Size(max = 128)
    @Column(name = "fir_dod_vchar_11", length = 128)
    private String firDodVchar11;
    @Size(max = 128)
    @Column(name = "fir_dod_vchar_12", length = 128)
    private String firDodVchar12;
    @Column(name = "fir_dod_int_1")
    private Integer firDodInt1;
    @Column(name = "fir_dod_int_2")
    private Integer firDodInt2;
    @Column(name = "fir_dod_int_3")
    private Integer firDodInt3;
    @Column(name = "fir_dod_int_4")
    private Integer firDodInt4;
    @Lob
    @Column(name = "fir_logo")
    private byte[] firLogo;

    public Firma() {
    }

    public Firma(Integer firSerial) {
        this.firSerial = firSerial;
    }

    public Firma(Integer firSerial, String firNazwaSkr, String firNazwaPel, String firKod, String firUlica, String firDom, String firMieszkanie, String firTel, String firFaks, BigDecimal firUbezpWyp, String firUbezpWypC, String firUbezpFpC, String firUbezpFgspC, BigDecimal firUbezpFp, BigDecimal firUbezpFgsp) {
        this.firSerial = firSerial;
        this.firNazwaSkr = firNazwaSkr;
        this.firNazwaPel = firNazwaPel;
        this.firKod = firKod;
        this.firUlica = firUlica;
        this.firDom = firDom;
        this.firMieszkanie = firMieszkanie;
        this.firTel = firTel;
        this.firFaks = firFaks;
        this.firUbezpWyp = firUbezpWyp;
        this.firUbezpWypC = firUbezpWypC;
        this.firUbezpFpC = firUbezpFpC;
        this.firUbezpFgspC = firUbezpFgspC;
        this.firUbezpFp = firUbezpFp;
        this.firUbezpFgsp = firUbezpFgsp;
    }

    public Integer getFirSerial() {
        return firSerial;
    }

    public void setFirSerial(Integer firSerial) {
        this.firSerial = firSerial;
    }

    public String getFirNazwaSkr() {
        return firNazwaSkr;
    }

    public void setFirNazwaSkr(String firNazwaSkr) {
        this.firNazwaSkr = firNazwaSkr;
    }

    public String getFirNazwaPel() {
        return firNazwaPel;
    }

    public void setFirNazwaPel(String firNazwaPel) {
        this.firNazwaPel = firNazwaPel;
    }

    public String getFirNip() {
        return firNip;
    }

    public void setFirNip(String firNip) {
        this.firNip = firNip;
    }

    public String getFirRegon() {
        return firRegon;
    }

    public void setFirRegon(String firRegon) {
        this.firRegon = firRegon;
    }

    public Integer getFirPanSerial() {
        return firPanSerial;
    }

    public void setFirPanSerial(Integer firPanSerial) {
        this.firPanSerial = firPanSerial;
    }

    public Integer getFirMiaSerial() {
        return firMiaSerial;
    }

    public void setFirMiaSerial(Integer firMiaSerial) {
        this.firMiaSerial = firMiaSerial;
    }

    public String getFirKod() {
        return firKod;
    }

    public void setFirKod(String firKod) {
        this.firKod = firKod;
    }

    public String getFirUlica() {
        return firUlica;
    }

    public void setFirUlica(String firUlica) {
        this.firUlica = firUlica;
    }

    public String getFirDom() {
        return firDom;
    }

    public void setFirDom(String firDom) {
        this.firDom = firDom;
    }

    public String getFirMieszkanie() {
        return firMieszkanie;
    }

    public void setFirMieszkanie(String firMieszkanie) {
        this.firMieszkanie = firMieszkanie;
    }

    public String getFirTel() {
        return firTel;
    }

    public void setFirTel(String firTel) {
        this.firTel = firTel;
    }

    public String getFirFaks() {
        return firFaks;
    }

    public void setFirFaks(String firFaks) {
        this.firFaks = firFaks;
    }

    public Integer getFirKntSerial() {
        return firKntSerial;
    }

    public void setFirKntSerial(Integer firKntSerial) {
        this.firKntSerial = firKntSerial;
    }

    public String getFirRodzDzial() {
        return firRodzDzial;
    }

    public void setFirRodzDzial(String firRodzDzial) {
        this.firRodzDzial = firRodzDzial;
    }

    public Integer getFirFakBanSerial() {
        return firFakBanSerial;
    }

    public void setFirFakBanSerial(Integer firFakBanSerial) {
        this.firFakBanSerial = firFakBanSerial;
    }

    public String getFirFakNrKonta() {
        return firFakNrKonta;
    }

    public void setFirFakNrKonta(String firFakNrKonta) {
        this.firFakNrKonta = firFakNrKonta;
    }

    public Integer getFirZapBanSerial() {
        return firZapBanSerial;
    }

    public void setFirZapBanSerial(Integer firZapBanSerial) {
        this.firZapBanSerial = firZapBanSerial;
    }

    public String getFirZapNrKonta() {
        return firZapNrKonta;
    }

    public void setFirZapNrKonta(String firZapNrKonta) {
        this.firZapNrKonta = firZapNrKonta;
    }

    public String getFirFakWiadom() {
        return firFakWiadom;
    }

    public void setFirFakWiadom(String firFakWiadom) {
        this.firFakWiadom = firFakWiadom;
    }

    public String getFirNrFakAuto() {
        return firNrFakAuto;
    }

    public void setFirNrFakAuto(String firNrFakAuto) {
        this.firNrFakAuto = firNrFakAuto;
    }

    public String getFirNrFakD() {
        return firNrFakD;
    }

    public void setFirNrFakD(String firNrFakD) {
        this.firNrFakD = firNrFakD;
    }

    public Integer getFirNrFakDP() {
        return firNrFakDP;
    }

    public void setFirNrFakDP(Integer firNrFakDP) {
        this.firNrFakDP = firNrFakDP;
    }

    public String getFirNrFakL() {
        return firNrFakL;
    }

    public void setFirNrFakL(String firNrFakL) {
        this.firNrFakL = firNrFakL;
    }

    public Integer getFirNrFakLP() {
        return firNrFakLP;
    }

    public void setFirNrFakLP(Integer firNrFakLP) {
        this.firNrFakLP = firNrFakLP;
    }

    public String getFirNrFakS() {
        return firNrFakS;
    }

    public void setFirNrFakS(String firNrFakS) {
        this.firNrFakS = firNrFakS;
    }

    public Integer getFirNrFakSP() {
        return firNrFakSP;
    }

    public void setFirNrFakSP(Integer firNrFakSP) {
        this.firNrFakSP = firNrFakSP;
    }

    public String getFirNrRachAuto() {
        return firNrRachAuto;
    }

    public void setFirNrRachAuto(String firNrRachAuto) {
        this.firNrRachAuto = firNrRachAuto;
    }

    public String getFirNrRachS() {
        return firNrRachS;
    }

    public void setFirNrRachS(String firNrRachS) {
        this.firNrRachS = firNrRachS;
    }

    public Integer getFirDataPlatRoz() {
        return firDataPlatRoz;
    }

    public void setFirDataPlatRoz(Integer firDataPlatRoz) {
        this.firDataPlatRoz = firDataPlatRoz;
    }

    public String getFirNrKRachAuto() {
        return firNrKRachAuto;
    }

    public void setFirNrKRachAuto(String firNrKRachAuto) {
        this.firNrKRachAuto = firNrKRachAuto;
    }

    public String getFirNrKRachS() {
        return firNrKRachS;
    }

    public void setFirNrKRachS(String firNrKRachS) {
        this.firNrKRachS = firNrKRachS;
    }

    public String getFirNrKFakAuto() {
        return firNrKFakAuto;
    }

    public void setFirNrKFakAuto(String firNrKFakAuto) {
        this.firNrKFakAuto = firNrKFakAuto;
    }

    public String getFirNrKFakS() {
        return firNrKFakS;
    }

    public void setFirNrKFakS(String firNrKFakS) {
        this.firNrKFakS = firNrKFakS;
    }

    public Integer getFirNrRachSP() {
        return firNrRachSP;
    }

    public void setFirNrRachSP(Integer firNrRachSP) {
        this.firNrRachSP = firNrRachSP;
    }

    public String getFirNrRachD() {
        return firNrRachD;
    }

    public void setFirNrRachD(String firNrRachD) {
        this.firNrRachD = firNrRachD;
    }

    public Integer getFirNrRachDP() {
        return firNrRachDP;
    }

    public void setFirNrRachDP(Integer firNrRachDP) {
        this.firNrRachDP = firNrRachDP;
    }

    public String getFirNrRachL() {
        return firNrRachL;
    }

    public void setFirNrRachL(String firNrRachL) {
        this.firNrRachL = firNrRachL;
    }

    public Integer getFirNrRachLP() {
        return firNrRachLP;
    }

    public void setFirNrRachLP(Integer firNrRachLP) {
        this.firNrRachLP = firNrRachLP;
    }

    public Integer getFirNrKRachSP() {
        return firNrKRachSP;
    }

    public void setFirNrKRachSP(Integer firNrKRachSP) {
        this.firNrKRachSP = firNrKRachSP;
    }

    public String getFirNrKRachD() {
        return firNrKRachD;
    }

    public void setFirNrKRachD(String firNrKRachD) {
        this.firNrKRachD = firNrKRachD;
    }

    public Integer getFirNrKRachDP() {
        return firNrKRachDP;
    }

    public void setFirNrKRachDP(Integer firNrKRachDP) {
        this.firNrKRachDP = firNrKRachDP;
    }

    public String getFirNrKRachL() {
        return firNrKRachL;
    }

    public void setFirNrKRachL(String firNrKRachL) {
        this.firNrKRachL = firNrKRachL;
    }

    public Integer getFirNrKRachLP() {
        return firNrKRachLP;
    }

    public void setFirNrKRachLP(Integer firNrKRachLP) {
        this.firNrKRachLP = firNrKRachLP;
    }

    public Integer getFirNrKFakSP() {
        return firNrKFakSP;
    }

    public void setFirNrKFakSP(Integer firNrKFakSP) {
        this.firNrKFakSP = firNrKFakSP;
    }

    public String getFirNrKFakD() {
        return firNrKFakD;
    }

    public void setFirNrKFakD(String firNrKFakD) {
        this.firNrKFakD = firNrKFakD;
    }

    public Integer getFirNrKFakDP() {
        return firNrKFakDP;
    }

    public void setFirNrKFakDP(Integer firNrKFakDP) {
        this.firNrKFakDP = firNrKFakDP;
    }

    public String getFirNrKFakL() {
        return firNrKFakL;
    }

    public void setFirNrKFakL(String firNrKFakL) {
        this.firNrKFakL = firNrKFakL;
    }

    public Integer getFirNrKFakLP() {
        return firNrKFakLP;
    }

    public void setFirNrKFakLP(Integer firNrKFakLP) {
        this.firNrKFakLP = firNrKFakLP;
    }

    public String getFirNrZwAuto() {
        return firNrZwAuto;
    }

    public void setFirNrZwAuto(String firNrZwAuto) {
        this.firNrZwAuto = firNrZwAuto;
    }

    public String getFirNrZwS() {
        return firNrZwS;
    }

    public void setFirNrZwS(String firNrZwS) {
        this.firNrZwS = firNrZwS;
    }

    public Integer getFirNrZwSP() {
        return firNrZwSP;
    }

    public void setFirNrZwSP(Integer firNrZwSP) {
        this.firNrZwSP = firNrZwSP;
    }

    public String getFirNrZwD() {
        return firNrZwD;
    }

    public void setFirNrZwD(String firNrZwD) {
        this.firNrZwD = firNrZwD;
    }

    public Integer getFirNrZwDP() {
        return firNrZwDP;
    }

    public void setFirNrZwDP(Integer firNrZwDP) {
        this.firNrZwDP = firNrZwDP;
    }

    public String getFirNrZwL() {
        return firNrZwL;
    }

    public void setFirNrZwL(String firNrZwL) {
        this.firNrZwL = firNrZwL;
    }

    public Integer getFirNrZwLP() {
        return firNrZwLP;
    }

    public void setFirNrZwLP(Integer firNrZwLP) {
        this.firNrZwLP = firNrZwLP;
    }

    public String getFirNrWzAuto() {
        return firNrWzAuto;
    }

    public void setFirNrWzAuto(String firNrWzAuto) {
        this.firNrWzAuto = firNrWzAuto;
    }

    public String getFirNrWzS() {
        return firNrWzS;
    }

    public void setFirNrWzS(String firNrWzS) {
        this.firNrWzS = firNrWzS;
    }

    public Integer getFirNrWzSP() {
        return firNrWzSP;
    }

    public void setFirNrWzSP(Integer firNrWzSP) {
        this.firNrWzSP = firNrWzSP;
    }

    public String getFirNrWzD() {
        return firNrWzD;
    }

    public void setFirNrWzD(String firNrWzD) {
        this.firNrWzD = firNrWzD;
    }

    public Integer getFirNrWzDP() {
        return firNrWzDP;
    }

    public void setFirNrWzDP(Integer firNrWzDP) {
        this.firNrWzDP = firNrWzDP;
    }

    public String getFirNrWzL() {
        return firNrWzL;
    }

    public void setFirNrWzL(String firNrWzL) {
        this.firNrWzL = firNrWzL;
    }

    public Integer getFirNrWzLP() {
        return firNrWzLP;
    }

    public void setFirNrWzLP(Integer firNrWzLP) {
        this.firNrWzLP = firNrWzLP;
    }

    public String getFirFakLogo() {
        return firFakLogo;
    }

    public void setFirFakLogo(String firFakLogo) {
        this.firFakLogo = firFakLogo;
    }

    public String getFirRachLogo() {
        return firRachLogo;
    }

    public void setFirRachLogo(String firRachLogo) {
        this.firRachLogo = firRachLogo;
    }

    public String getFirKFakLogo() {
        return firKFakLogo;
    }

    public void setFirKFakLogo(String firKFakLogo) {
        this.firKFakLogo = firKFakLogo;
    }

    public String getFirKRachLogo() {
        return firKRachLogo;
    }

    public void setFirKRachLogo(String firKRachLogo) {
        this.firKRachLogo = firKRachLogo;
    }

    public String getFirZwLogo() {
        return firZwLogo;
    }

    public void setFirZwLogo(String firZwLogo) {
        this.firZwLogo = firZwLogo;
    }

    public String getFirWzLogo() {
        return firWzLogo;
    }

    public void setFirWzLogo(String firWzLogo) {
        this.firWzLogo = firWzLogo;
    }

    public String getFirNrPzAuto() {
        return firNrPzAuto;
    }

    public void setFirNrPzAuto(String firNrPzAuto) {
        this.firNrPzAuto = firNrPzAuto;
    }

    public String getFirNrPzD() {
        return firNrPzD;
    }

    public void setFirNrPzD(String firNrPzD) {
        this.firNrPzD = firNrPzD;
    }

    public Integer getFirNrPzDP() {
        return firNrPzDP;
    }

    public void setFirNrPzDP(Integer firNrPzDP) {
        this.firNrPzDP = firNrPzDP;
    }

    public String getFirNrPzL() {
        return firNrPzL;
    }

    public void setFirNrPzL(String firNrPzL) {
        this.firNrPzL = firNrPzL;
    }

    public Integer getFirNrPzLP() {
        return firNrPzLP;
    }

    public void setFirNrPzLP(Integer firNrPzLP) {
        this.firNrPzLP = firNrPzLP;
    }

    public String getFirNrPzS() {
        return firNrPzS;
    }

    public void setFirNrPzS(String firNrPzS) {
        this.firNrPzS = firNrPzS;
    }

    public Integer getFirNrPzSP() {
        return firNrPzSP;
    }

    public void setFirNrPzSP(Integer firNrPzSP) {
        this.firNrPzSP = firNrPzSP;
    }

    public String getFirPzLogo() {
        return firPzLogo;
    }

    public void setFirPzLogo(String firPzLogo) {
        this.firPzLogo = firPzLogo;
    }

    public String getFirTypOsoby() {
        return firTypOsoby;
    }

    public void setFirTypOsoby(String firTypOsoby) {
        this.firTypOsoby = firTypOsoby;
    }

    public String getFirPesel() {
        return firPesel;
    }

    public void setFirPesel(String firPesel) {
        this.firPesel = firPesel;
    }

    public String getFirRodzajDok() {
        return firRodzajDok;
    }

    public void setFirRodzajDok(String firRodzajDok) {
        this.firRodzajDok = firRodzajDok;
    }

    public String getFirSeriaNr() {
        return firSeriaNr;
    }

    public void setFirSeriaNr(String firSeriaNr) {
        this.firSeriaNr = firSeriaNr;
    }

    public String getFirNazwisko() {
        return firNazwisko;
    }

    public void setFirNazwisko(String firNazwisko) {
        this.firNazwisko = firNazwisko;
    }

    public String getFirImie1() {
        return firImie1;
    }

    public void setFirImie1(String firImie1) {
        this.firImie1 = firImie1;
    }

    public Date getFirDataUrodz() {
        return firDataUrodz;
    }

    public void setFirDataUrodz(Date firDataUrodz) {
        this.firDataUrodz = firDataUrodz;
    }

    public String getFirGmina() {
        return firGmina;
    }

    public void setFirGmina(String firGmina) {
        this.firGmina = firGmina;
    }

    public Integer getFirUrzSerial() {
        return firUrzSerial;
    }

    public void setFirUrzSerial(Integer firUrzSerial) {
        this.firUrzSerial = firUrzSerial;
    }

    public String getFirPowiat() {
        return firPowiat;
    }

    public void setFirPowiat(String firPowiat) {
        this.firPowiat = firPowiat;
    }

    public String getFirPoczta() {
        return firPoczta;
    }

    public void setFirPoczta(String firPoczta) {
        this.firPoczta = firPoczta;
    }

    public String getFirWojewodztwo() {
        return firWojewodztwo;
    }

    public void setFirWojewodztwo(String firWojewodztwo) {
        this.firWojewodztwo = firWojewodztwo;
    }

    public String getFirKodTerm() {
        return firKodTerm;
    }

    public void setFirKodTerm(String firKodTerm) {
        this.firKodTerm = firKodTerm;
    }

    public String getFirZaklPrChron() {
        return firZaklPrChron;
    }

    public void setFirZaklPrChron(String firZaklPrChron) {
        this.firZaklPrChron = firZaklPrChron;
    }

    public BigDecimal getFirUbezpWyp() {
        return firUbezpWyp;
    }

    public void setFirUbezpWyp(BigDecimal firUbezpWyp) {
        this.firUbezpWyp = firUbezpWyp;
    }

    public String getFirUprSwUbChor() {
        return firUprSwUbChor;
    }

    public void setFirUprSwUbChor(String firUprSwUbChor) {
        this.firUprSwUbChor = firUprSwUbChor;
    }

    public String getFirImieOjca() {
        return firImieOjca;
    }

    public void setFirImieOjca(String firImieOjca) {
        this.firImieOjca = firImieOjca;
    }

    public String getFirImieMatki() {
        return firImieMatki;
    }

    public void setFirImieMatki(String firImieMatki) {
        this.firImieMatki = firImieMatki;
    }

    public Integer getFirOptSerialPrac() {
        return firOptSerialPrac;
    }

    public void setFirOptSerialPrac(Integer firOptSerialPrac) {
        this.firOptSerialPrac = firOptSerialPrac;
    }

    public Integer getFirOptSerialOsob() {
        return firOptSerialOsob;
    }

    public void setFirOptSerialOsob(Integer firOptSerialOsob) {
        this.firOptSerialOsob = firOptSerialOsob;
    }

    public Integer getFirOptSerialWlas() {
        return firOptSerialWlas;
    }

    public void setFirOptSerialWlas(Integer firOptSerialWlas) {
        this.firOptSerialWlas = firOptSerialWlas;
    }

    public String getFirTestowa() {
        return firTestowa;
    }

    public void setFirTestowa(String firTestowa) {
        this.firTestowa = firTestowa;
    }

    public String getFirWiadomPar() {
        return firWiadomPar;
    }

    public void setFirWiadomPar(String firWiadomPar) {
        this.firWiadomPar = firWiadomPar;
    }

    public String getFirKonNrIdNaDok() {
        return firKonNrIdNaDok;
    }

    public void setFirKonNrIdNaDok(String firKonNrIdNaDok) {
        this.firKonNrIdNaDok = firKonNrIdNaDok;
    }

    public String getFirEkd() {
        return firEkd;
    }

    public void setFirEkd(String firEkd) {
        this.firEkd = firEkd;
    }

    public String getFirJednBudz() {
        return firJednBudz;
    }

    public void setFirJednBudz(String firJednBudz) {
        this.firJednBudz = firJednBudz;
    }

    public String getFirJednPozaBudz() {
        return firJednPozaBudz;
    }

    public void setFirJednPozaBudz(String firJednPozaBudz) {
        this.firJednPozaBudz = firJednPozaBudz;
    }

    public String getFirOrgZal() {
        return firOrgZal;
    }

    public void setFirOrgZal(String firOrgZal) {
        this.firOrgZal = firOrgZal;
    }

    public String getFirRejestr() {
        return firRejestr;
    }

    public void setFirRejestr(String firRejestr) {
        this.firRejestr = firRejestr;
    }

    public Date getFirRejestrData() {
        return firRejestrData;
    }

    public void setFirRejestrData(Date firRejestrData) {
        this.firRejestrData = firRejestrData;
    }

    public String getFirRejestrNr() {
        return firRejestrNr;
    }

    public void setFirRejestrNr(String firRejestrNr) {
        this.firRejestrNr = firRejestrNr;
    }

    public String getFirRejestrNazwaO() {
        return firRejestrNazwaO;
    }

    public void setFirRejestrNazwaO(String firRejestrNazwaO) {
        this.firRejestrNazwaO = firRejestrNazwaO;
    }

    public Date getFirDataObowUb() {
        return firDataObowUb;
    }

    public void setFirDataObowUb(Date firDataObowUb) {
        this.firDataObowUb = firDataObowUb;
    }

    public Date getFirDataRozp() {
        return firDataRozp;
    }

    public void setFirDataRozp(Date firDataRozp) {
        this.firDataRozp = firDataRozp;
    }

    public Date getFirZaPrChrDOt() {
        return firZaPrChrDOt;
    }

    public void setFirZaPrChrDOt(Date firZaPrChrDOt) {
        this.firZaPrChrDOt = firZaPrChrDOt;
    }

    public Date getFirZaPrChrDUt() {
        return firZaPrChrDUt;
    }

    public void setFirZaPrChrDUt(Date firZaPrChrDUt) {
        this.firZaPrChrDUt = firZaPrChrDUt;
    }

    public String getFirAdrDzialInny() {
        return firAdrDzialInny;
    }

    public void setFirAdrDzialInny(String firAdrDzialInny) {
        this.firAdrDzialInny = firAdrDzialInny;
    }

    public String getFirKod2() {
        return firKod2;
    }

    public void setFirKod2(String firKod2) {
        this.firKod2 = firKod2;
    }

    public Integer getFirMiaSerial2() {
        return firMiaSerial2;
    }

    public void setFirMiaSerial2(Integer firMiaSerial2) {
        this.firMiaSerial2 = firMiaSerial2;
    }

    public String getFirGmina2() {
        return firGmina2;
    }

    public void setFirGmina2(String firGmina2) {
        this.firGmina2 = firGmina2;
    }

    public String getFirUlica2() {
        return firUlica2;
    }

    public void setFirUlica2(String firUlica2) {
        this.firUlica2 = firUlica2;
    }

    public String getFirDom2() {
        return firDom2;
    }

    public void setFirDom2(String firDom2) {
        this.firDom2 = firDom2;
    }

    public String getFirMieszkanie2() {
        return firMieszkanie2;
    }

    public void setFirMieszkanie2(String firMieszkanie2) {
        this.firMieszkanie2 = firMieszkanie2;
    }

    public String getFirTel2() {
        return firTel2;
    }

    public void setFirTel2(String firTel2) {
        this.firTel2 = firTel2;
    }

    public String getFirFaks2() {
        return firFaks2;
    }

    public void setFirFaks2(String firFaks2) {
        this.firFaks2 = firFaks2;
    }

    public Integer getFirPanSerial2() {
        return firPanSerial2;
    }

    public void setFirPanSerial2(Integer firPanSerial2) {
        this.firPanSerial2 = firPanSerial2;
    }

    public String getFirPowiat2() {
        return firPowiat2;
    }

    public void setFirPowiat2(String firPowiat2) {
        this.firPowiat2 = firPowiat2;
    }

    public String getFirPoczta2() {
        return firPoczta2;
    }

    public void setFirPoczta2(String firPoczta2) {
        this.firPoczta2 = firPoczta2;
    }

    public String getFirWojewodztwo2() {
        return firWojewodztwo2;
    }

    public void setFirWojewodztwo2(String firWojewodztwo2) {
        this.firWojewodztwo2 = firWojewodztwo2;
    }

    public String getFirEmail() {
        return firEmail;
    }

    public void setFirEmail(String firEmail) {
        this.firEmail = firEmail;
    }

    public String getFirEmail2() {
        return firEmail2;
    }

    public void setFirEmail2(String firEmail2) {
        this.firEmail2 = firEmail2;
    }

    public Integer getFirPanSerialK() {
        return firPanSerialK;
    }

    public void setFirPanSerialK(Integer firPanSerialK) {
        this.firPanSerialK = firPanSerialK;
    }

    public String getFirKodK() {
        return firKodK;
    }

    public void setFirKodK(String firKodK) {
        this.firKodK = firKodK;
    }

    public Integer getFirMiaSerialK() {
        return firMiaSerialK;
    }

    public void setFirMiaSerialK(Integer firMiaSerialK) {
        this.firMiaSerialK = firMiaSerialK;
    }

    public String getFirUlicaK() {
        return firUlicaK;
    }

    public void setFirUlicaK(String firUlicaK) {
        this.firUlicaK = firUlicaK;
    }

    public String getFirDomK() {
        return firDomK;
    }

    public void setFirDomK(String firDomK) {
        this.firDomK = firDomK;
    }

    public String getFirMieszkanieK() {
        return firMieszkanieK;
    }

    public void setFirMieszkanieK(String firMieszkanieK) {
        this.firMieszkanieK = firMieszkanieK;
    }

    public String getFirTelK() {
        return firTelK;
    }

    public void setFirTelK(String firTelK) {
        this.firTelK = firTelK;
    }

    public String getFirFaksK() {
        return firFaksK;
    }

    public void setFirFaksK(String firFaksK) {
        this.firFaksK = firFaksK;
    }

    public String getFirEmailK() {
        return firEmailK;
    }

    public void setFirEmailK(String firEmailK) {
        this.firEmailK = firEmailK;
    }

    public String getFirSkrytkaK() {
        return firSkrytkaK;
    }

    public void setFirSkrytkaK(String firSkrytkaK) {
        this.firSkrytkaK = firSkrytkaK;
    }

    public String getFirTeletransK() {
        return firTeletransK;
    }

    public void setFirTeletransK(String firTeletransK) {
        this.firTeletransK = firTeletransK;
    }

    public String getFirGminaK() {
        return firGminaK;
    }

    public void setFirGminaK(String firGminaK) {
        this.firGminaK = firGminaK;
    }

    public String getFirPowiatK() {
        return firPowiatK;
    }

    public void setFirPowiatK(String firPowiatK) {
        this.firPowiatK = firPowiatK;
    }

    public String getFirPocztaK() {
        return firPocztaK;
    }

    public void setFirPocztaK(String firPocztaK) {
        this.firPocztaK = firPocztaK;
    }

    public String getFirWojewodztwoK() {
        return firWojewodztwoK;
    }

    public void setFirWojewodztwoK(String firWojewodztwoK) {
        this.firWojewodztwoK = firWojewodztwoK;
    }

    public String getFirBiuroNip() {
        return firBiuroNip;
    }

    public void setFirBiuroNip(String firBiuroNip) {
        this.firBiuroNip = firBiuroNip;
    }

    public String getFirBiuroRegon() {
        return firBiuroRegon;
    }

    public void setFirBiuroRegon(String firBiuroRegon) {
        this.firBiuroRegon = firBiuroRegon;
    }

    public String getFirBiuroNazwaSkr() {
        return firBiuroNazwaSkr;
    }

    public void setFirBiuroNazwaSkr(String firBiuroNazwaSkr) {
        this.firBiuroNazwaSkr = firBiuroNazwaSkr;
    }

    public String getFirKodUprawn() {
        return firKodUprawn;
    }

    public void setFirKodUprawn(String firKodUprawn) {
        this.firKodUprawn = firKodUprawn;
    }

    public Date getFirDataUprawn() {
        return firDataUprawn;
    }

    public void setFirDataUprawn(Date firDataUprawn) {
        this.firDataUprawn = firDataUprawn;
    }

    public Integer getFirPanSerialD() {
        return firPanSerialD;
    }

    public void setFirPanSerialD(Integer firPanSerialD) {
        this.firPanSerialD = firPanSerialD;
    }

    public String getFirKodD() {
        return firKodD;
    }

    public void setFirKodD(String firKodD) {
        this.firKodD = firKodD;
    }

    public Integer getFirMiaSerialD() {
        return firMiaSerialD;
    }

    public void setFirMiaSerialD(Integer firMiaSerialD) {
        this.firMiaSerialD = firMiaSerialD;
    }

    public String getFirUlicaD() {
        return firUlicaD;
    }

    public void setFirUlicaD(String firUlicaD) {
        this.firUlicaD = firUlicaD;
    }

    public String getFirDomD() {
        return firDomD;
    }

    public void setFirDomD(String firDomD) {
        this.firDomD = firDomD;
    }

    public String getFirMieszkanieD() {
        return firMieszkanieD;
    }

    public void setFirMieszkanieD(String firMieszkanieD) {
        this.firMieszkanieD = firMieszkanieD;
    }

    public String getFirTelD() {
        return firTelD;
    }

    public void setFirTelD(String firTelD) {
        this.firTelD = firTelD;
    }

    public String getFirFaksD() {
        return firFaksD;
    }

    public void setFirFaksD(String firFaksD) {
        this.firFaksD = firFaksD;
    }

    public String getFirGminaD() {
        return firGminaD;
    }

    public void setFirGminaD(String firGminaD) {
        this.firGminaD = firGminaD;
    }

    public String getFirPowiatD() {
        return firPowiatD;
    }

    public void setFirPowiatD(String firPowiatD) {
        this.firPowiatD = firPowiatD;
    }

    public String getFirPocztaD() {
        return firPocztaD;
    }

    public void setFirPocztaD(String firPocztaD) {
        this.firPocztaD = firPocztaD;
    }

    public String getFirWojewodztwoD() {
        return firWojewodztwoD;
    }

    public void setFirWojewodztwoD(String firWojewodztwoD) {
        this.firWojewodztwoD = firWojewodztwoD;
    }

    public Integer getFirBanSerial3() {
        return firBanSerial3;
    }

    public void setFirBanSerial3(Integer firBanSerial3) {
        this.firBanSerial3 = firBanSerial3;
    }

    public String getFirNrKonta3() {
        return firNrKonta3;
    }

    public void setFirNrKonta3(String firNrKonta3) {
        this.firNrKonta3 = firNrKonta3;
    }

    public Integer getFirBanSerial4() {
        return firBanSerial4;
    }

    public void setFirBanSerial4(Integer firBanSerial4) {
        this.firBanSerial4 = firBanSerial4;
    }

    public String getFirNrKonta4() {
        return firNrKonta4;
    }

    public void setFirNrKonta4(String firNrKonta4) {
        this.firNrKonta4 = firNrKonta4;
    }

    public String getFirMiejsceUr() {
        return firMiejsceUr;
    }

    public void setFirMiejsceUr(String firMiejsceUr) {
        this.firMiejsceUr = firMiejsceUr;
    }

    public String getFirObywatelstwo() {
        return firObywatelstwo;
    }

    public void setFirObywatelstwo(String firObywatelstwo) {
        this.firObywatelstwo = firObywatelstwo;
    }

    public String getFirImie2() {
        return firImie2;
    }

    public void setFirImie2(String firImie2) {
        this.firImie2 = firImie2;
    }

    public String getFirKodRodzUpr() {
        return firKodRodzUpr;
    }

    public void setFirKodRodzUpr(String firKodRodzUpr) {
        this.firKodRodzUpr = firKodRodzUpr;
    }

    public String getFirWiadomParTresc() {
        return firWiadomParTresc;
    }

    public void setFirWiadomParTresc(String firWiadomParTresc) {
        this.firWiadomParTresc = firWiadomParTresc;
    }

    public String getFirAdrTsam2() {
        return firAdrTsam2;
    }

    public void setFirAdrTsam2(String firAdrTsam2) {
        this.firAdrTsam2 = firAdrTsam2;
    }

    public String getFirAdrTsamK() {
        return firAdrTsamK;
    }

    public void setFirAdrTsamK(String firAdrTsamK) {
        this.firAdrTsamK = firAdrTsamK;
    }

    public String getFirAdrTsamD() {
        return firAdrTsamD;
    }

    public void setFirAdrTsamD(String firAdrTsamD) {
        this.firAdrTsamD = firAdrTsamD;
    }

    public String getFirDodChar1() {
        return firDodChar1;
    }

    public void setFirDodChar1(String firDodChar1) {
        this.firDodChar1 = firDodChar1;
    }

    public String getFirDodChar2() {
        return firDodChar2;
    }

    public void setFirDodChar2(String firDodChar2) {
        this.firDodChar2 = firDodChar2;
    }

    public String getFirDodChar3() {
        return firDodChar3;
    }

    public void setFirDodChar3(String firDodChar3) {
        this.firDodChar3 = firDodChar3;
    }

    public String getFirDodChar4() {
        return firDodChar4;
    }

    public void setFirDodChar4(String firDodChar4) {
        this.firDodChar4 = firDodChar4;
    }

    public String getFirDodChar5() {
        return firDodChar5;
    }

    public void setFirDodChar5(String firDodChar5) {
        this.firDodChar5 = firDodChar5;
    }

    public String getFirDodChar6() {
        return firDodChar6;
    }

    public void setFirDodChar6(String firDodChar6) {
        this.firDodChar6 = firDodChar6;
    }

    public String getFirDodChar7() {
        return firDodChar7;
    }

    public void setFirDodChar7(String firDodChar7) {
        this.firDodChar7 = firDodChar7;
    }

    public String getFirDodChar8() {
        return firDodChar8;
    }

    public void setFirDodChar8(String firDodChar8) {
        this.firDodChar8 = firDodChar8;
    }

    public BigDecimal getFirDodNum1() {
        return firDodNum1;
    }

    public void setFirDodNum1(BigDecimal firDodNum1) {
        this.firDodNum1 = firDodNum1;
    }

    public BigDecimal getFirDodNum2() {
        return firDodNum2;
    }

    public void setFirDodNum2(BigDecimal firDodNum2) {
        this.firDodNum2 = firDodNum2;
    }

    public BigDecimal getFirDodNum3() {
        return firDodNum3;
    }

    public void setFirDodNum3(BigDecimal firDodNum3) {
        this.firDodNum3 = firDodNum3;
    }

    public BigDecimal getFirDodNum4() {
        return firDodNum4;
    }

    public void setFirDodNum4(BigDecimal firDodNum4) {
        this.firDodNum4 = firDodNum4;
    }

    public BigDecimal getFirDodNum5() {
        return firDodNum5;
    }

    public void setFirDodNum5(BigDecimal firDodNum5) {
        this.firDodNum5 = firDodNum5;
    }

    public BigDecimal getFirDodNum6() {
        return firDodNum6;
    }

    public void setFirDodNum6(BigDecimal firDodNum6) {
        this.firDodNum6 = firDodNum6;
    }

    public BigDecimal getFirDodNum7() {
        return firDodNum7;
    }

    public void setFirDodNum7(BigDecimal firDodNum7) {
        this.firDodNum7 = firDodNum7;
    }

    public BigDecimal getFirDodNum8() {
        return firDodNum8;
    }

    public void setFirDodNum8(BigDecimal firDodNum8) {
        this.firDodNum8 = firDodNum8;
    }

    public String getFirBiuro() {
        return firBiuro;
    }

    public void setFirBiuro(String firBiuro) {
        this.firBiuro = firBiuro;
    }

    public String getFirUbezpWypC() {
        return firUbezpWypC;
    }

    public void setFirUbezpWypC(String firUbezpWypC) {
        this.firUbezpWypC = firUbezpWypC;
    }

    public String getFirUbezpFpC() {
        return firUbezpFpC;
    }

    public void setFirUbezpFpC(String firUbezpFpC) {
        this.firUbezpFpC = firUbezpFpC;
    }

    public String getFirUbezpFgspC() {
        return firUbezpFgspC;
    }

    public void setFirUbezpFgspC(String firUbezpFgspC) {
        this.firUbezpFgspC = firUbezpFgspC;
    }

    public BigDecimal getFirUbezpFp() {
        return firUbezpFp;
    }

    public void setFirUbezpFp(BigDecimal firUbezpFp) {
        this.firUbezpFp = firUbezpFp;
    }

    public BigDecimal getFirUbezpFgsp() {
        return firUbezpFgsp;
    }

    public void setFirUbezpFgsp(BigDecimal firUbezpFgsp) {
        this.firUbezpFgsp = firUbezpFgsp;
    }

    public Integer getFirOptSerialWspo() {
        return firOptSerialWspo;
    }

    public void setFirOptSerialWspo(Integer firOptSerialWspo) {
        this.firOptSerialWspo = firOptSerialWspo;
    }

    public Integer getFirOptSerialDod1() {
        return firOptSerialDod1;
    }

    public void setFirOptSerialDod1(Integer firOptSerialDod1) {
        this.firOptSerialDod1 = firOptSerialDod1;
    }

    public Integer getFirOptSerialDod2() {
        return firOptSerialDod2;
    }

    public void setFirOptSerialDod2(Integer firOptSerialDod2) {
        this.firOptSerialDod2 = firOptSerialDod2;
    }

    public Integer getFirDodSerial1() {
        return firDodSerial1;
    }

    public void setFirDodSerial1(Integer firDodSerial1) {
        this.firDodSerial1 = firDodSerial1;
    }

    public String getFirDodVchar1() {
        return firDodVchar1;
    }

    public void setFirDodVchar1(String firDodVchar1) {
        this.firDodVchar1 = firDodVchar1;
    }

    public Integer getFirDodSerial2() {
        return firDodSerial2;
    }

    public void setFirDodSerial2(Integer firDodSerial2) {
        this.firDodSerial2 = firDodSerial2;
    }

    public String getFirDodVchar2() {
        return firDodVchar2;
    }

    public void setFirDodVchar2(String firDodVchar2) {
        this.firDodVchar2 = firDodVchar2;
    }

    public String getFirDodChar9() {
        return firDodChar9;
    }

    public void setFirDodChar9(String firDodChar9) {
        this.firDodChar9 = firDodChar9;
    }

    public String getFirDodChar10() {
        return firDodChar10;
    }

    public void setFirDodChar10(String firDodChar10) {
        this.firDodChar10 = firDodChar10;
    }

    public String getFirDodChar11() {
        return firDodChar11;
    }

    public void setFirDodChar11(String firDodChar11) {
        this.firDodChar11 = firDodChar11;
    }

    public String getFirDodChar12() {
        return firDodChar12;
    }

    public void setFirDodChar12(String firDodChar12) {
        this.firDodChar12 = firDodChar12;
    }

    public String getFirDodChar13() {
        return firDodChar13;
    }

    public void setFirDodChar13(String firDodChar13) {
        this.firDodChar13 = firDodChar13;
    }

    public String getFirDodChar14() {
        return firDodChar14;
    }

    public void setFirDodChar14(String firDodChar14) {
        this.firDodChar14 = firDodChar14;
    }

    public String getFirDodChar15() {
        return firDodChar15;
    }

    public void setFirDodChar15(String firDodChar15) {
        this.firDodChar15 = firDodChar15;
    }

    public String getFirDodChar16() {
        return firDodChar16;
    }

    public void setFirDodChar16(String firDodChar16) {
        this.firDodChar16 = firDodChar16;
    }

    public String getFirDodVchar3() {
        return firDodVchar3;
    }

    public void setFirDodVchar3(String firDodVchar3) {
        this.firDodVchar3 = firDodVchar3;
    }

    public String getFirDodVchar4() {
        return firDodVchar4;
    }

    public void setFirDodVchar4(String firDodVchar4) {
        this.firDodVchar4 = firDodVchar4;
    }

    public String getFirDodVchar5() {
        return firDodVchar5;
    }

    public void setFirDodVchar5(String firDodVchar5) {
        this.firDodVchar5 = firDodVchar5;
    }

    public String getFirDodVchar6() {
        return firDodVchar6;
    }

    public void setFirDodVchar6(String firDodVchar6) {
        this.firDodVchar6 = firDodVchar6;
    }

    public Integer getFirUrzSerialV() {
        return firUrzSerialV;
    }

    public void setFirUrzSerialV(Integer firUrzSerialV) {
        this.firUrzSerialV = firUrzSerialV;
    }

    public String getFirDodVchar7() {
        return firDodVchar7;
    }

    public void setFirDodVchar7(String firDodVchar7) {
        this.firDodVchar7 = firDodVchar7;
    }

    public String getFirDodVchar8() {
        return firDodVchar8;
    }

    public void setFirDodVchar8(String firDodVchar8) {
        this.firDodVchar8 = firDodVchar8;
    }

    public String getFirDodVchar9() {
        return firDodVchar9;
    }

    public void setFirDodVchar9(String firDodVchar9) {
        this.firDodVchar9 = firDodVchar9;
    }

    public String getFirDodVchar10() {
        return firDodVchar10;
    }

    public void setFirDodVchar10(String firDodVchar10) {
        this.firDodVchar10 = firDodVchar10;
    }

    public String getFirNusp() {
        return firNusp;
    }

    public void setFirNusp(String firNusp) {
        this.firNusp = firNusp;
    }

    public String getFirDodChar17() {
        return firDodChar17;
    }

    public void setFirDodChar17(String firDodChar17) {
        this.firDodChar17 = firDodChar17;
    }

    public String getFirDodChar18() {
        return firDodChar18;
    }

    public void setFirDodChar18(String firDodChar18) {
        this.firDodChar18 = firDodChar18;
    }

    public String getFirDodChar19() {
        return firDodChar19;
    }

    public void setFirDodChar19(String firDodChar19) {
        this.firDodChar19 = firDodChar19;
    }

    public String getFirDodChar20() {
        return firDodChar20;
    }

    public void setFirDodChar20(String firDodChar20) {
        this.firDodChar20 = firDodChar20;
    }

    public String getFirDodChar21() {
        return firDodChar21;
    }

    public void setFirDodChar21(String firDodChar21) {
        this.firDodChar21 = firDodChar21;
    }

    public String getFirDodChar22() {
        return firDodChar22;
    }

    public void setFirDodChar22(String firDodChar22) {
        this.firDodChar22 = firDodChar22;
    }

    public String getFirDodChar23() {
        return firDodChar23;
    }

    public void setFirDodChar23(String firDodChar23) {
        this.firDodChar23 = firDodChar23;
    }

    public String getFirDodChar24() {
        return firDodChar24;
    }

    public void setFirDodChar24(String firDodChar24) {
        this.firDodChar24 = firDodChar24;
    }

    public String getFirUstawienia() {
        return firUstawienia;
    }

    public void setFirUstawienia(String firUstawienia) {
        this.firUstawienia = firUstawienia;
    }

    public String getFirUstawieniaKs() {
        return firUstawieniaKs;
    }

    public void setFirUstawieniaKs(String firUstawieniaKs) {
        this.firUstawieniaKs = firUstawieniaKs;
    }

    public String getFirUstawieniaKp() {
        return firUstawieniaKp;
    }

    public void setFirUstawieniaKp(String firUstawieniaKp) {
        this.firUstawieniaKp = firUstawieniaKp;
    }

    public String getFirDodVchar11() {
        return firDodVchar11;
    }

    public void setFirDodVchar11(String firDodVchar11) {
        this.firDodVchar11 = firDodVchar11;
    }

    public String getFirDodVchar12() {
        return firDodVchar12;
    }

    public void setFirDodVchar12(String firDodVchar12) {
        this.firDodVchar12 = firDodVchar12;
    }

    public Integer getFirDodInt1() {
        return firDodInt1;
    }

    public void setFirDodInt1(Integer firDodInt1) {
        this.firDodInt1 = firDodInt1;
    }

    public Integer getFirDodInt2() {
        return firDodInt2;
    }

    public void setFirDodInt2(Integer firDodInt2) {
        this.firDodInt2 = firDodInt2;
    }

    public Integer getFirDodInt3() {
        return firDodInt3;
    }

    public void setFirDodInt3(Integer firDodInt3) {
        this.firDodInt3 = firDodInt3;
    }

    public Integer getFirDodInt4() {
        return firDodInt4;
    }

    public void setFirDodInt4(Integer firDodInt4) {
        this.firDodInt4 = firDodInt4;
    }

    public byte[] getFirLogo() {
        return firLogo;
    }

    public void setFirLogo(byte[] firLogo) {
        this.firLogo = firLogo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (firSerial != null ? firSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Firma)) {
            return false;
        }
        Firma other = (Firma) object;
        if ((this.firSerial == null && other.firSerial != null) || (this.firSerial != null && !this.firSerial.equals(other.firSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.mycompany.kadry.Firma[ firSerial=" + firSerial + " ]";
    }
    
}
