/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kadryiplace;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "firma", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Firma.findAll", query = "SELECT f FROM Firma f"),
    @NamedQuery(name = "Firma.findByFirSerial", query = "SELECT f FROM Firma f WHERE f.firSerial = :firSerial"),
    @NamedQuery(name = "Firma.findByFirNazwaSkr", query = "SELECT f FROM Firma f WHERE f.firNazwaSkr = :firNazwaSkr"),
    @NamedQuery(name = "Firma.findByFirNazwaPel", query = "SELECT f FROM Firma f WHERE f.firNazwaPel = :firNazwaPel"),
    @NamedQuery(name = "Firma.findByFirNip", query = "SELECT f FROM Firma f WHERE f.firNip = :firNip"),
    @NamedQuery(name = "Firma.findByFirRegon", query = "SELECT f FROM Firma f WHERE f.firRegon = :firRegon"),
    @NamedQuery(name = "Firma.findByFirKod", query = "SELECT f FROM Firma f WHERE f.firKod = :firKod"),
    @NamedQuery(name = "Firma.findByFirUlica", query = "SELECT f FROM Firma f WHERE f.firUlica = :firUlica"),
    @NamedQuery(name = "Firma.findByFirDom", query = "SELECT f FROM Firma f WHERE f.firDom = :firDom"),
    @NamedQuery(name = "Firma.findByFirMieszkanie", query = "SELECT f FROM Firma f WHERE f.firMieszkanie = :firMieszkanie"),
    @NamedQuery(name = "Firma.findByFirTel", query = "SELECT f FROM Firma f WHERE f.firTel = :firTel"),
    @NamedQuery(name = "Firma.findByFirFaks", query = "SELECT f FROM Firma f WHERE f.firFaks = :firFaks"),
    @NamedQuery(name = "Firma.findByFirKntSerial", query = "SELECT f FROM Firma f WHERE f.firKntSerial = :firKntSerial"),
    @NamedQuery(name = "Firma.findByFirRodzDzial", query = "SELECT f FROM Firma f WHERE f.firRodzDzial = :firRodzDzial"),
    @NamedQuery(name = "Firma.findByFirFakNrKonta", query = "SELECT f FROM Firma f WHERE f.firFakNrKonta = :firFakNrKonta"),
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
    @NamedQuery(name = "Firma.findByFirUlicaD", query = "SELECT f FROM Firma f WHERE f.firUlicaD = :firUlicaD"),
    @NamedQuery(name = "Firma.findByFirDomD", query = "SELECT f FROM Firma f WHERE f.firDomD = :firDomD"),
    @NamedQuery(name = "Firma.findByFirMieszkanieD", query = "SELECT f FROM Firma f WHERE f.firMieszkanieD = :firMieszkanieD"),
    @NamedQuery(name = "Firma.findByFirTelD", query = "SELECT f FROM Firma f WHERE f.firTelD = :firTelD"),
    @NamedQuery(name = "Firma.findByFirFaksD", query = "SELECT f FROM Firma f WHERE f.firFaksD = :firFaksD"),
    @NamedQuery(name = "Firma.findByFirGminaD", query = "SELECT f FROM Firma f WHERE f.firGminaD = :firGminaD"),
    @NamedQuery(name = "Firma.findByFirPowiatD", query = "SELECT f FROM Firma f WHERE f.firPowiatD = :firPowiatD"),
    @NamedQuery(name = "Firma.findByFirPocztaD", query = "SELECT f FROM Firma f WHERE f.firPocztaD = :firPocztaD"),
    @NamedQuery(name = "Firma.findByFirWojewodztwoD", query = "SELECT f FROM Firma f WHERE f.firWojewodztwoD = :firWojewodztwoD"),
    @NamedQuery(name = "Firma.findByFirNrKonta3", query = "SELECT f FROM Firma f WHERE f.firNrKonta3 = :firNrKonta3"),
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
    @Size(min = 1, max = 31)
    @Column(name = "fir_nazwa_skr", nullable = false, length = 31)
    private String firNazwaSkr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 128)
    @Column(name = "fir_nazwa_pel", nullable = false, length = 128)
    private String firNazwaPel;
    @Size(max = 16)
    @Column(name = "fir_nip", length = 16)
    private String firNip;
    @Size(max = 16)
    @Column(name = "fir_regon", length = 16)
    private String firRegon;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "fir_kod", nullable = false, length = 5)
    private String firKod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "fir_ulica", nullable = false, length = 30)
    private String firUlica;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "fir_dom", nullable = false, length = 7)
    private String firDom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "fir_mieszkanie", nullable = false, length = 7)
    private String firMieszkanie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "fir_tel", nullable = false, length = 16)
    private String firTel;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "fir_faks", nullable = false, length = 16)
    private String firFaks;
    @Column(name = "fir_knt_serial")
    private Integer firKntSerial;
    @Size(max = 128)
    @Column(name = "fir_rodz_dzial", length = 128)
    private String firRodzDzial;
    @Size(max = 64)
    @Column(name = "fir_fak_nr_konta", length = 64)
    private String firFakNrKonta;
    @Size(max = 64)
    @Column(name = "fir_zap_nr_konta", length = 64)
    private String firZapNrKonta;
    @Size(max = 254)
    @Column(name = "fir_fak_wiadom", length = 254)
    private String firFakWiadom;
    @Column(name = "fir_nr_fak_auto")
    private Character firNrFakAuto;
    @Size(max = 10)
    @Column(name = "fir_nr_fak_d", length = 10)
    private String firNrFakD;
    @Column(name = "fir_nr_fak_d_p")
    private Short firNrFakDP;
    @Size(max = 10)
    @Column(name = "fir_nr_fak_l", length = 10)
    private String firNrFakL;
    @Column(name = "fir_nr_fak_l_p")
    private Short firNrFakLP;
    @Size(max = 10)
    @Column(name = "fir_nr_fak_s", length = 10)
    private String firNrFakS;
    @Column(name = "fir_nr_fak_s_p")
    private Short firNrFakSP;
    @Column(name = "fir_nr_rach_auto")
    private Character firNrRachAuto;
    @Size(max = 10)
    @Column(name = "fir_nr_rach_s", length = 10)
    private String firNrRachS;
    @Column(name = "fir_data_plat_roz")
    private Short firDataPlatRoz;
    @Column(name = "fir_nr_k_rach_auto")
    private Character firNrKRachAuto;
    @Size(max = 10)
    @Column(name = "fir_nr_k_rach_s", length = 10)
    private String firNrKRachS;
    @Column(name = "fir_nr_k_fak_auto")
    private Character firNrKFakAuto;
    @Size(max = 10)
    @Column(name = "fir_nr_k_fak_s", length = 10)
    private String firNrKFakS;
    @Column(name = "fir_nr_rach_s_p")
    private Short firNrRachSP;
    @Size(max = 10)
    @Column(name = "fir_nr_rach_d", length = 10)
    private String firNrRachD;
    @Column(name = "fir_nr_rach_d_p")
    private Short firNrRachDP;
    @Size(max = 10)
    @Column(name = "fir_nr_rach_l", length = 10)
    private String firNrRachL;
    @Column(name = "fir_nr_rach_l_p")
    private Short firNrRachLP;
    @Column(name = "fir_nr_k_rach_s_p")
    private Short firNrKRachSP;
    @Size(max = 10)
    @Column(name = "fir_nr_k_rach_d", length = 10)
    private String firNrKRachD;
    @Column(name = "fir_nr_k_rach_d_p")
    private Short firNrKRachDP;
    @Size(max = 10)
    @Column(name = "fir_nr_k_rach_l", length = 10)
    private String firNrKRachL;
    @Column(name = "fir_nr_k_rach_l_p")
    private Short firNrKRachLP;
    @Column(name = "fir_nr_k_fak_s_p")
    private Short firNrKFakSP;
    @Size(max = 10)
    @Column(name = "fir_nr_k_fak_d", length = 10)
    private String firNrKFakD;
    @Column(name = "fir_nr_k_fak_d_p")
    private Short firNrKFakDP;
    @Size(max = 10)
    @Column(name = "fir_nr_k_fak_l", length = 10)
    private String firNrKFakL;
    @Column(name = "fir_nr_k_fak_l_p")
    private Short firNrKFakLP;
    @Column(name = "fir_nr_zw_auto")
    private Character firNrZwAuto;
    @Size(max = 10)
    @Column(name = "fir_nr_zw_s", length = 10)
    private String firNrZwS;
    @Column(name = "fir_nr_zw_s_p")
    private Short firNrZwSP;
    @Size(max = 10)
    @Column(name = "fir_nr_zw_d", length = 10)
    private String firNrZwD;
    @Column(name = "fir_nr_zw_d_p")
    private Short firNrZwDP;
    @Size(max = 10)
    @Column(name = "fir_nr_zw_l", length = 10)
    private String firNrZwL;
    @Column(name = "fir_nr_zw_l_p")
    private Short firNrZwLP;
    @Column(name = "fir_nr_wz_auto")
    private Character firNrWzAuto;
    @Size(max = 10)
    @Column(name = "fir_nr_wz_s", length = 10)
    private String firNrWzS;
    @Column(name = "fir_nr_wz_s_p")
    private Short firNrWzSP;
    @Size(max = 10)
    @Column(name = "fir_nr_wz_d", length = 10)
    private String firNrWzD;
    @Column(name = "fir_nr_wz_d_p")
    private Short firNrWzDP;
    @Size(max = 10)
    @Column(name = "fir_nr_wz_l", length = 10)
    private String firNrWzL;
    @Column(name = "fir_nr_wz_l_p")
    private Short firNrWzLP;
    @Column(name = "fir_fak_logo")
    private Character firFakLogo;
    @Column(name = "fir_rach_logo")
    private Character firRachLogo;
    @Column(name = "fir_k_fak_logo")
    private Character firKFakLogo;
    @Column(name = "fir_k_rach_logo")
    private Character firKRachLogo;
    @Column(name = "fir_zw_logo")
    private Character firZwLogo;
    @Column(name = "fir_wz_logo")
    private Character firWzLogo;
    @Column(name = "fir_nr_pz_auto")
    private Character firNrPzAuto;
    @Size(max = 10)
    @Column(name = "fir_nr_pz_d", length = 10)
    private String firNrPzD;
    @Column(name = "fir_nr_pz_d_p")
    private Short firNrPzDP;
    @Size(max = 10)
    @Column(name = "fir_nr_pz_l", length = 10)
    private String firNrPzL;
    @Column(name = "fir_nr_pz_l_p")
    private Short firNrPzLP;
    @Size(max = 10)
    @Column(name = "fir_nr_pz_s", length = 10)
    private String firNrPzS;
    @Column(name = "fir_nr_pz_s_p")
    private Short firNrPzSP;
    @Column(name = "fir_pz_logo")
    private Character firPzLogo;
    @Column(name = "fir_typ_osoby")
    private Character firTypOsoby;
    @Size(max = 16)
    @Column(name = "fir_pesel", length = 16)
    private String firPesel;
    @Column(name = "fir_rodzaj_dok")
    private Character firRodzajDok;
    @Size(max = 16)
    @Column(name = "fir_seria_nr", length = 16)
    private String firSeriaNr;
    @Size(max = 31)
    @Column(name = "fir_nazwisko", length = 31)
    private String firNazwisko;
    @Size(max = 22)
    @Column(name = "fir_imie_1", length = 22)
    private String firImie1;
    @Column(name = "fir_data_urodz")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firDataUrodz;
    @Size(max = 26)
    @Column(name = "fir_gmina", length = 26)
    private String firGmina;
    @Column(name = "fir_urz_serial")
    private Integer firUrzSerial;
    @Size(max = 26)
    @Column(name = "fir_powiat", length = 26)
    private String firPowiat;
    @Size(max = 26)
    @Column(name = "fir_poczta", length = 26)
    private String firPoczta;
    @Size(max = 26)
    @Column(name = "fir_wojewodztwo", length = 26)
    private String firWojewodztwo;
    @Column(name = "fir_kod_term")
    private Character firKodTerm;
    @Column(name = "fir_zakl_pr_chron")
    private Character firZaklPrChron;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "fir_ubezp_wyp", nullable = false, precision = 7, scale = 4)
    private BigDecimal firUbezpWyp;
    @Column(name = "fir_upr_sw_ub_chor")
    private Character firUprSwUbChor;
    @Size(max = 22)
    @Column(name = "fir_imie_ojca", length = 22)
    private String firImieOjca;
    @Size(max = 22)
    @Column(name = "fir_imie_matki", length = 22)
    private String firImieMatki;
    @Column(name = "fir_opt_serial_prac")
    private Integer firOptSerialPrac;
    @Column(name = "fir_opt_serial_osob")
    private Integer firOptSerialOsob;
    @Column(name = "fir_opt_serial_wlas")
    private Integer firOptSerialWlas;
    @Column(name = "fir_testowa")
    private Character firTestowa;
    @Column(name = "fir_wiadom_par")
    private Character firWiadomPar;
    @Column(name = "fir_kon_nr_id_na_dok")
    private Character firKonNrIdNaDok;
    @Size(max = 16)
    @Column(name = "fir_ekd", length = 16)
    private String firEkd;
    @Column(name = "fir_jedn_budz")
    private Character firJednBudz;
    @Column(name = "fir_jedn_poza_budz")
    private Character firJednPozaBudz;
    @Size(max = 31)
    @Column(name = "fir_org_zal", length = 31)
    private String firOrgZal;
    @Column(name = "fir_rejestr")
    private Character firRejestr;
    @Column(name = "fir_rejestr_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firRejestrData;
    @Size(max = 16)
    @Column(name = "fir_rejestr_nr", length = 16)
    private String firRejestrNr;
    @Size(max = 96)
    @Column(name = "fir_rejestr_nazwa_o", length = 96)
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
    @Column(name = "fir_adr_dzial_inny")
    private Character firAdrDzialInny;
    @Size(max = 5)
    @Column(name = "fir_kod_2", length = 5)
    private String firKod2;
    @Size(max = 26)
    @Column(name = "fir_gmina_2", length = 26)
    private String firGmina2;
    @Size(max = 30)
    @Column(name = "fir_ulica_2", length = 30)
    private String firUlica2;
    @Size(max = 7)
    @Column(name = "fir_dom_2", length = 7)
    private String firDom2;
    @Size(max = 7)
    @Column(name = "fir_mieszkanie_2", length = 7)
    private String firMieszkanie2;
    @Size(max = 16)
    @Column(name = "fir_tel_2", length = 16)
    private String firTel2;
    @Size(max = 16)
    @Column(name = "fir_faks_2", length = 16)
    private String firFaks2;
    @Column(name = "fir_pan_serial_2")
    private Integer firPanSerial2;
    @Size(max = 26)
    @Column(name = "fir_powiat_2", length = 26)
    private String firPowiat2;
    @Size(max = 26)
    @Column(name = "fir_poczta_2", length = 26)
    private String firPoczta2;
    @Size(max = 26)
    @Column(name = "fir_wojewodztwo_2", length = 26)
    private String firWojewodztwo2;
    @Size(max = 96)
    @Column(name = "fir_email", length = 96)
    private String firEmail;
    @Size(max = 96)
    @Column(name = "fir_email_2", length = 96)
    private String firEmail2;
    @Column(name = "fir_pan_serial_k")
    private Integer firPanSerialK;
    @Size(max = 5)
    @Column(name = "fir_kod_k", length = 5)
    private String firKodK;
    @Size(max = 30)
    @Column(name = "fir_ulica_k", length = 30)
    private String firUlicaK;
    @Size(max = 7)
    @Column(name = "fir_dom_k", length = 7)
    private String firDomK;
    @Size(max = 7)
    @Column(name = "fir_mieszkanie_k", length = 7)
    private String firMieszkanieK;
    @Size(max = 16)
    @Column(name = "fir_tel_k", length = 16)
    private String firTelK;
    @Size(max = 16)
    @Column(name = "fir_faks_k", length = 16)
    private String firFaksK;
    @Size(max = 96)
    @Column(name = "fir_email_k", length = 96)
    private String firEmailK;
    @Size(max = 5)
    @Column(name = "fir_skrytka_k", length = 5)
    private String firSkrytkaK;
    @Size(max = 16)
    @Column(name = "fir_teletrans_k", length = 16)
    private String firTeletransK;
    @Size(max = 26)
    @Column(name = "fir_gmina_k", length = 26)
    private String firGminaK;
    @Size(max = 26)
    @Column(name = "fir_powiat_k", length = 26)
    private String firPowiatK;
    @Size(max = 26)
    @Column(name = "fir_poczta_k", length = 26)
    private String firPocztaK;
    @Size(max = 26)
    @Column(name = "fir_wojewodztwo_k", length = 26)
    private String firWojewodztwoK;
    @Size(max = 10)
    @Column(name = "fir_biuro_nip", length = 10)
    private String firBiuroNip;
    @Size(max = 11)
    @Column(name = "fir_biuro_regon", length = 11)
    private String firBiuroRegon;
    @Size(max = 31)
    @Column(name = "fir_biuro_nazwa_skr", length = 31)
    private String firBiuroNazwaSkr;
    @Size(max = 2)
    @Column(name = "fir_kod_uprawn", length = 2)
    private String firKodUprawn;
    @Column(name = "fir_data_uprawn")
    @Temporal(TemporalType.TIMESTAMP)
    private Date firDataUprawn;
    @Column(name = "fir_pan_serial_d")
    private Integer firPanSerialD;
    @Size(max = 5)
    @Column(name = "fir_kod_d", length = 5)
    private String firKodD;
    @Size(max = 30)
    @Column(name = "fir_ulica_d", length = 30)
    private String firUlicaD;
    @Size(max = 7)
    @Column(name = "fir_dom_d", length = 7)
    private String firDomD;
    @Size(max = 7)
    @Column(name = "fir_mieszkanie_d", length = 7)
    private String firMieszkanieD;
    @Size(max = 16)
    @Column(name = "fir_tel_d", length = 16)
    private String firTelD;
    @Size(max = 16)
    @Column(name = "fir_faks_d", length = 16)
    private String firFaksD;
    @Size(max = 26)
    @Column(name = "fir_gmina_d", length = 26)
    private String firGminaD;
    @Size(max = 26)
    @Column(name = "fir_powiat_d", length = 26)
    private String firPowiatD;
    @Size(max = 26)
    @Column(name = "fir_poczta_d", length = 26)
    private String firPocztaD;
    @Size(max = 26)
    @Column(name = "fir_wojewodztwo_d", length = 26)
    private String firWojewodztwoD;
    @Size(max = 64)
    @Column(name = "fir_nr_konta_3", length = 64)
    private String firNrKonta3;
    @Size(max = 64)
    @Column(name = "fir_nr_konta_4", length = 64)
    private String firNrKonta4;
    @Size(max = 26)
    @Column(name = "fir_miejsce_ur", length = 26)
    private String firMiejsceUr;
    @Size(max = 22)
    @Column(name = "fir_obywatelstwo", length = 22)
    private String firObywatelstwo;
    @Size(max = 22)
    @Column(name = "fir_imie_2", length = 22)
    private String firImie2;
    @Size(max = 2)
    @Column(name = "fir_kod_rodz_upr", length = 2)
    private String firKodRodzUpr;
    @Size(max = 64)
    @Column(name = "fir_wiadom_par_tresc", length = 64)
    private String firWiadomParTresc;
    @Column(name = "fir_adr_tsam_2")
    private Character firAdrTsam2;
    @Column(name = "fir_adr_tsam_k")
    private Character firAdrTsamK;
    @Column(name = "fir_adr_tsam_d")
    private Character firAdrTsamD;
    @Column(name = "fir_dod_char_1")
    private Character firDodChar1;
    @Column(name = "fir_dod_char_2")
    private Character firDodChar2;
    @Column(name = "fir_dod_char_3")
    private Character firDodChar3;
    @Column(name = "fir_dod_char_4")
    private Character firDodChar4;
    @Column(name = "fir_dod_char_5")
    private Character firDodChar5;
    @Column(name = "fir_dod_char_6")
    private Character firDodChar6;
    @Column(name = "fir_dod_char_7")
    private Character firDodChar7;
    @Column(name = "fir_dod_char_8")
    private Character firDodChar8;
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
    @Column(name = "fir_biuro")
    private Character firBiuro;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fir_ubezp_wyp_c", nullable = false)
    private Character firUbezpWypC;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fir_ubezp_fp_c", nullable = false)
    private Character firUbezpFpC;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fir_ubezp_fgsp_c", nullable = false)
    private Character firUbezpFgspC;
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
    @Column(name = "fir_dod_char_9")
    private Character firDodChar9;
    @Column(name = "fir_dod_char_10")
    private Character firDodChar10;
    @Column(name = "fir_dod_char_11")
    private Character firDodChar11;
    @Column(name = "fir_dod_char_12")
    private Character firDodChar12;
    @Column(name = "fir_dod_char_13")
    private Character firDodChar13;
    @Column(name = "fir_dod_char_14")
    private Character firDodChar14;
    @Column(name = "fir_dod_char_15")
    private Character firDodChar15;
    @Column(name = "fir_dod_char_16")
    private Character firDodChar16;
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
    @Size(max = 64)
    @Column(name = "fir_dod_vchar_7", length = 64)
    private String firDodVchar7;
    @Size(max = 64)
    @Column(name = "fir_dod_vchar_8", length = 64)
    private String firDodVchar8;
    @Size(max = 254)
    @Column(name = "fir_dod_vchar_9", length = 254)
    private String firDodVchar9;
    @Size(max = 254)
    @Column(name = "fir_dod_vchar_10", length = 254)
    private String firDodVchar10;
    @Size(max = 32)
    @Column(name = "fir_nusp", length = 32)
    private String firNusp;
    @Column(name = "fir_dod_char_17")
    private Character firDodChar17;
    @Column(name = "fir_dod_char_18")
    private Character firDodChar18;
    @Column(name = "fir_dod_char_19")
    private Character firDodChar19;
    @Column(name = "fir_dod_char_20")
    private Character firDodChar20;
    @Column(name = "fir_dod_char_21")
    private Character firDodChar21;
    @Column(name = "fir_dod_char_22")
    private Character firDodChar22;
    @Column(name = "fir_dod_char_23")
    private Character firDodChar23;
    @Column(name = "fir_dod_char_24")
    private Character firDodChar24;
    @Size(max = 254)
    @Column(name = "fir_ustawienia", length = 254)
    private String firUstawienia;
    @Size(max = 254)
    @Column(name = "fir_ustawienia_ks", length = 254)
    private String firUstawieniaKs;
    @Size(max = 254)
    @Column(name = "fir_ustawienia_kp", length = 254)
    private String firUstawieniaKp;
    @Size(max = 254)
    @Column(name = "fir_dod_vchar_11", length = 254)
    private String firDodVchar11;
    @Size(max = 254)
    @Column(name = "fir_dod_vchar_12", length = 254)
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
    @OneToMany(mappedBy = "dliFirSerial")
    private List<DaneLiDa> daneLiDaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "depFirSerial")
    private List<Dep> depList;
    @OneToMany(mappedBy = "dokFirSerial")
    private List<Dokument> dokumentList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ksiFirSerial")
    private List<Ksiegapir> ksiegapirList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lgrFirSerial")
    private List<LokGrupa> lokGrupaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zakFirSerial")
    private List<Zakupy> zakupyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dlsFirSerial")
    private List<DaneLiSl> daneLiSlList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zdoFirSerial")
    private List<Zakdok> zakdokList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rejFirSerial")
    private List<Rejestr> rejestrList;
    @OneToMany(mappedBy = "varFirSerial")
    private List<Vatrej> vatrejList;
    @OneToMany(mappedBy = "vatFirSerial")
    private List<Vat> vatList;
    @JoinColumn(name = "fir_fak_ban_serial", referencedColumnName = "ban_serial")
    @ManyToOne
    private Bank firFakBanSerial;
    @JoinColumn(name = "fir_zap_ban_serial", referencedColumnName = "ban_serial")
    @ManyToOne
    private Bank firZapBanSerial;
    @JoinColumn(name = "fir_ban_serial_3", referencedColumnName = "ban_serial")
    @ManyToOne
    private Bank firBanSerial3;
    @JoinColumn(name = "fir_ban_serial_4", referencedColumnName = "ban_serial")
    @ManyToOne
    private Bank firBanSerial4;
    @JoinColumn(name = "fir_mia_serial_2", referencedColumnName = "mia_serial")
    @ManyToOne
    private Miasto firMiaSerial2;
    @JoinColumn(name = "fir_mia_serial_k", referencedColumnName = "mia_serial")
    @ManyToOne
    private Miasto firMiaSerialK;
    @JoinColumn(name = "fir_mia_serial_d", referencedColumnName = "mia_serial")
    @ManyToOne
    private Miasto firMiaSerialD;
    @JoinColumn(name = "fir_mia_serial", referencedColumnName = "mia_serial")
    @ManyToOne
    private Miasto firMiaSerial;
    @JoinColumn(name = "fir_pan_serial", referencedColumnName = "pan_serial")
    @ManyToOne
    private Panstwo firPanSerial;
    @JoinColumn(name = "fir_urz_serial_v", referencedColumnName = "urz_serial")
    @ManyToOne
    private Urzad firUrzSerialV;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stgFirSerial")
    private List<StanGrup> stanGrupList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wypFirSerial")
    private List<Wyposaz> wyposazList;
    @OneToMany(mappedBy = "dhiFirSerial")
    private List<DaneHiDa> daneHiDaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wpoFirSerial")
    private List<WynPotracenia> wynPotraceniaList;
    @OneToMany(mappedBy = "mdtFirSerial")
    private List<Magdoktyp> magdoktypList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sroFirSerial")
    private List<Srodki> srodkiList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sdpFirSerial")
    private List<SuperDep> superDepList;
    @OneToMany(mappedBy = "celFirSerial")
    private List<Cele> celeList;
    @OneToMany(mappedBy = "dstFirSerial")
    private List<DaneStat> daneStatList;
    @OneToMany(mappedBy = "kslFirSerial")
    private List<KonSlow> konSlowList;
    @OneToMany(mappedBy = "magFirSerial")
    private List<Magazyn> magazynList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "mdoFirSerial")
    private List<Magdok> magdokList;
    @OneToMany(mappedBy = "ozgFirSerial")
    private List<Opiszdgosp> opiszdgospList;
    @OneToMany(mappedBy = "kntFirSerial")
    private List<Konto> kontoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "stpFirSerial")
    private List<StanPodgrup> stanPodgrupList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kgrFirSerial")
    private List<Kongrupa> kongrupaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "staFirSerial")
    private List<Stanowisko> stanowiskoList;
    @OneToMany(mappedBy = "przFirSerial")
    private List<Przedmiot> przedmiotList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pioFirSerial")
    private List<Pion> pionList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sprFirSerial")
    private List<Sprzedaz> sprzedazList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ozlFirSerial")
    private List<OsobaZlec> osobaZlecList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "gruFirSerial")
    private List<Grupa> grupaList;
    @OneToMany(mappedBy = "fakFirSerial")
    private List<Fakrach> fakrachList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sckFirSerial")
    private List<ScKamp> scKampList;
    @OneToMany(mappedBy = "dasFirSerial")
    private List<DaneStDa> daneStDaList;
    @OneToMany(mappedBy = "ssdFirSerial")
    private List<StSystOpis> stSystOpisList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lokFirSerial")
    private List<Lokalizacja> lokalizacjaList;
    @OneToMany(mappedBy = "rokFirSerial")
    private List<Rok> rokList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tpoFirSerial")
    private List<TytulWpo> tytulWpoList;
    @OneToMany(mappedBy = "adhFirSerial")
    private List<AdresHist> adresHistList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "osoFirSerial")
    private List<Osoba> osobaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "traFirSerial")
    private List<Trasy> trasyList;
    @OneToMany(mappedBy = "nmrFirSerial")
    private List<Numer> numerList;
    @Transient
    private boolean zaimportowana;

    public Firma() {
    }

    public Firma(Integer firSerial) {
        this.firSerial = firSerial;
    }

    public Firma(Integer firSerial, String firNazwaSkr, String firNazwaPel, String firKod, String firUlica, String firDom, String firMieszkanie, String firTel, String firFaks, BigDecimal firUbezpWyp, Character firUbezpWypC, Character firUbezpFpC, Character firUbezpFgspC, BigDecimal firUbezpFp, BigDecimal firUbezpFgsp) {
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

    public String getFirFakNrKonta() {
        return firFakNrKonta;
    }

    public void setFirFakNrKonta(String firFakNrKonta) {
        this.firFakNrKonta = firFakNrKonta;
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

    public Character getFirNrFakAuto() {
        return firNrFakAuto;
    }

    public void setFirNrFakAuto(Character firNrFakAuto) {
        this.firNrFakAuto = firNrFakAuto;
    }

    public String getFirNrFakD() {
        return firNrFakD;
    }

    public void setFirNrFakD(String firNrFakD) {
        this.firNrFakD = firNrFakD;
    }

    public Short getFirNrFakDP() {
        return firNrFakDP;
    }

    public void setFirNrFakDP(Short firNrFakDP) {
        this.firNrFakDP = firNrFakDP;
    }

    public String getFirNrFakL() {
        return firNrFakL;
    }

    public void setFirNrFakL(String firNrFakL) {
        this.firNrFakL = firNrFakL;
    }

    public Short getFirNrFakLP() {
        return firNrFakLP;
    }

    public void setFirNrFakLP(Short firNrFakLP) {
        this.firNrFakLP = firNrFakLP;
    }

    public String getFirNrFakS() {
        return firNrFakS;
    }

    public void setFirNrFakS(String firNrFakS) {
        this.firNrFakS = firNrFakS;
    }

    public Short getFirNrFakSP() {
        return firNrFakSP;
    }

    public void setFirNrFakSP(Short firNrFakSP) {
        this.firNrFakSP = firNrFakSP;
    }

    public Character getFirNrRachAuto() {
        return firNrRachAuto;
    }

    public void setFirNrRachAuto(Character firNrRachAuto) {
        this.firNrRachAuto = firNrRachAuto;
    }

    public String getFirNrRachS() {
        return firNrRachS;
    }

    public void setFirNrRachS(String firNrRachS) {
        this.firNrRachS = firNrRachS;
    }

    public Short getFirDataPlatRoz() {
        return firDataPlatRoz;
    }

    public void setFirDataPlatRoz(Short firDataPlatRoz) {
        this.firDataPlatRoz = firDataPlatRoz;
    }

    public Character getFirNrKRachAuto() {
        return firNrKRachAuto;
    }

    public void setFirNrKRachAuto(Character firNrKRachAuto) {
        this.firNrKRachAuto = firNrKRachAuto;
    }

    public String getFirNrKRachS() {
        return firNrKRachS;
    }

    public void setFirNrKRachS(String firNrKRachS) {
        this.firNrKRachS = firNrKRachS;
    }

    public Character getFirNrKFakAuto() {
        return firNrKFakAuto;
    }

    public void setFirNrKFakAuto(Character firNrKFakAuto) {
        this.firNrKFakAuto = firNrKFakAuto;
    }

    public String getFirNrKFakS() {
        return firNrKFakS;
    }

    public void setFirNrKFakS(String firNrKFakS) {
        this.firNrKFakS = firNrKFakS;
    }

    public Short getFirNrRachSP() {
        return firNrRachSP;
    }

    public void setFirNrRachSP(Short firNrRachSP) {
        this.firNrRachSP = firNrRachSP;
    }

    public String getFirNrRachD() {
        return firNrRachD;
    }

    public void setFirNrRachD(String firNrRachD) {
        this.firNrRachD = firNrRachD;
    }

    public Short getFirNrRachDP() {
        return firNrRachDP;
    }

    public void setFirNrRachDP(Short firNrRachDP) {
        this.firNrRachDP = firNrRachDP;
    }

    public String getFirNrRachL() {
        return firNrRachL;
    }

    public void setFirNrRachL(String firNrRachL) {
        this.firNrRachL = firNrRachL;
    }

    public Short getFirNrRachLP() {
        return firNrRachLP;
    }

    public void setFirNrRachLP(Short firNrRachLP) {
        this.firNrRachLP = firNrRachLP;
    }

    public Short getFirNrKRachSP() {
        return firNrKRachSP;
    }

    public void setFirNrKRachSP(Short firNrKRachSP) {
        this.firNrKRachSP = firNrKRachSP;
    }

    public String getFirNrKRachD() {
        return firNrKRachD;
    }

    public void setFirNrKRachD(String firNrKRachD) {
        this.firNrKRachD = firNrKRachD;
    }

    public Short getFirNrKRachDP() {
        return firNrKRachDP;
    }

    public void setFirNrKRachDP(Short firNrKRachDP) {
        this.firNrKRachDP = firNrKRachDP;
    }

    public String getFirNrKRachL() {
        return firNrKRachL;
    }

    public void setFirNrKRachL(String firNrKRachL) {
        this.firNrKRachL = firNrKRachL;
    }

    public Short getFirNrKRachLP() {
        return firNrKRachLP;
    }

    public void setFirNrKRachLP(Short firNrKRachLP) {
        this.firNrKRachLP = firNrKRachLP;
    }

    public Short getFirNrKFakSP() {
        return firNrKFakSP;
    }

    public void setFirNrKFakSP(Short firNrKFakSP) {
        this.firNrKFakSP = firNrKFakSP;
    }

    public String getFirNrKFakD() {
        return firNrKFakD;
    }

    public void setFirNrKFakD(String firNrKFakD) {
        this.firNrKFakD = firNrKFakD;
    }

    public Short getFirNrKFakDP() {
        return firNrKFakDP;
    }

    public void setFirNrKFakDP(Short firNrKFakDP) {
        this.firNrKFakDP = firNrKFakDP;
    }

    public String getFirNrKFakL() {
        return firNrKFakL;
    }

    public void setFirNrKFakL(String firNrKFakL) {
        this.firNrKFakL = firNrKFakL;
    }

    public Short getFirNrKFakLP() {
        return firNrKFakLP;
    }

    public void setFirNrKFakLP(Short firNrKFakLP) {
        this.firNrKFakLP = firNrKFakLP;
    }

    public Character getFirNrZwAuto() {
        return firNrZwAuto;
    }

    public void setFirNrZwAuto(Character firNrZwAuto) {
        this.firNrZwAuto = firNrZwAuto;
    }

    public String getFirNrZwS() {
        return firNrZwS;
    }

    public void setFirNrZwS(String firNrZwS) {
        this.firNrZwS = firNrZwS;
    }

    public Short getFirNrZwSP() {
        return firNrZwSP;
    }

    public void setFirNrZwSP(Short firNrZwSP) {
        this.firNrZwSP = firNrZwSP;
    }

    public String getFirNrZwD() {
        return firNrZwD;
    }

    public void setFirNrZwD(String firNrZwD) {
        this.firNrZwD = firNrZwD;
    }

    public Short getFirNrZwDP() {
        return firNrZwDP;
    }

    public void setFirNrZwDP(Short firNrZwDP) {
        this.firNrZwDP = firNrZwDP;
    }

    public String getFirNrZwL() {
        return firNrZwL;
    }

    public void setFirNrZwL(String firNrZwL) {
        this.firNrZwL = firNrZwL;
    }

    public Short getFirNrZwLP() {
        return firNrZwLP;
    }

    public void setFirNrZwLP(Short firNrZwLP) {
        this.firNrZwLP = firNrZwLP;
    }

    public Character getFirNrWzAuto() {
        return firNrWzAuto;
    }

    public void setFirNrWzAuto(Character firNrWzAuto) {
        this.firNrWzAuto = firNrWzAuto;
    }

    public String getFirNrWzS() {
        return firNrWzS;
    }

    public void setFirNrWzS(String firNrWzS) {
        this.firNrWzS = firNrWzS;
    }

    public Short getFirNrWzSP() {
        return firNrWzSP;
    }

    public void setFirNrWzSP(Short firNrWzSP) {
        this.firNrWzSP = firNrWzSP;
    }

    public String getFirNrWzD() {
        return firNrWzD;
    }

    public void setFirNrWzD(String firNrWzD) {
        this.firNrWzD = firNrWzD;
    }

    public Short getFirNrWzDP() {
        return firNrWzDP;
    }

    public void setFirNrWzDP(Short firNrWzDP) {
        this.firNrWzDP = firNrWzDP;
    }

    public String getFirNrWzL() {
        return firNrWzL;
    }

    public void setFirNrWzL(String firNrWzL) {
        this.firNrWzL = firNrWzL;
    }

    public Short getFirNrWzLP() {
        return firNrWzLP;
    }

    public void setFirNrWzLP(Short firNrWzLP) {
        this.firNrWzLP = firNrWzLP;
    }

    public Character getFirFakLogo() {
        return firFakLogo;
    }

    public void setFirFakLogo(Character firFakLogo) {
        this.firFakLogo = firFakLogo;
    }

    public Character getFirRachLogo() {
        return firRachLogo;
    }

    public void setFirRachLogo(Character firRachLogo) {
        this.firRachLogo = firRachLogo;
    }

    public Character getFirKFakLogo() {
        return firKFakLogo;
    }

    public void setFirKFakLogo(Character firKFakLogo) {
        this.firKFakLogo = firKFakLogo;
    }

    public Character getFirKRachLogo() {
        return firKRachLogo;
    }

    public void setFirKRachLogo(Character firKRachLogo) {
        this.firKRachLogo = firKRachLogo;
    }

    public Character getFirZwLogo() {
        return firZwLogo;
    }

    public void setFirZwLogo(Character firZwLogo) {
        this.firZwLogo = firZwLogo;
    }

    public Character getFirWzLogo() {
        return firWzLogo;
    }

    public void setFirWzLogo(Character firWzLogo) {
        this.firWzLogo = firWzLogo;
    }

    public Character getFirNrPzAuto() {
        return firNrPzAuto;
    }

    public void setFirNrPzAuto(Character firNrPzAuto) {
        this.firNrPzAuto = firNrPzAuto;
    }

    public String getFirNrPzD() {
        return firNrPzD;
    }

    public void setFirNrPzD(String firNrPzD) {
        this.firNrPzD = firNrPzD;
    }

    public Short getFirNrPzDP() {
        return firNrPzDP;
    }

    public void setFirNrPzDP(Short firNrPzDP) {
        this.firNrPzDP = firNrPzDP;
    }

    public String getFirNrPzL() {
        return firNrPzL;
    }

    public void setFirNrPzL(String firNrPzL) {
        this.firNrPzL = firNrPzL;
    }

    public Short getFirNrPzLP() {
        return firNrPzLP;
    }

    public void setFirNrPzLP(Short firNrPzLP) {
        this.firNrPzLP = firNrPzLP;
    }

    public String getFirNrPzS() {
        return firNrPzS;
    }

    public void setFirNrPzS(String firNrPzS) {
        this.firNrPzS = firNrPzS;
    }

    public Short getFirNrPzSP() {
        return firNrPzSP;
    }

    public void setFirNrPzSP(Short firNrPzSP) {
        this.firNrPzSP = firNrPzSP;
    }

    public Character getFirPzLogo() {
        return firPzLogo;
    }

    public void setFirPzLogo(Character firPzLogo) {
        this.firPzLogo = firPzLogo;
    }

    public Character getFirTypOsoby() {
        return firTypOsoby;
    }

    public void setFirTypOsoby(Character firTypOsoby) {
        this.firTypOsoby = firTypOsoby;
    }

    public String getFirPesel() {
        return firPesel;
    }

    public void setFirPesel(String firPesel) {
        this.firPesel = firPesel;
    }

    public Character getFirRodzajDok() {
        return firRodzajDok;
    }

    public void setFirRodzajDok(Character firRodzajDok) {
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

    public Character getFirKodTerm() {
        return firKodTerm;
    }

    public void setFirKodTerm(Character firKodTerm) {
        this.firKodTerm = firKodTerm;
    }

    public Character getFirZaklPrChron() {
        return firZaklPrChron;
    }

    public void setFirZaklPrChron(Character firZaklPrChron) {
        this.firZaklPrChron = firZaklPrChron;
    }

    public BigDecimal getFirUbezpWyp() {
        return firUbezpWyp;
    }

    public void setFirUbezpWyp(BigDecimal firUbezpWyp) {
        this.firUbezpWyp = firUbezpWyp;
    }

    public Character getFirUprSwUbChor() {
        return firUprSwUbChor;
    }

    public void setFirUprSwUbChor(Character firUprSwUbChor) {
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

    public Character getFirTestowa() {
        return firTestowa;
    }

    public void setFirTestowa(Character firTestowa) {
        this.firTestowa = firTestowa;
    }

    public Character getFirWiadomPar() {
        return firWiadomPar;
    }

    public void setFirWiadomPar(Character firWiadomPar) {
        this.firWiadomPar = firWiadomPar;
    }

    public Character getFirKonNrIdNaDok() {
        return firKonNrIdNaDok;
    }

    public void setFirKonNrIdNaDok(Character firKonNrIdNaDok) {
        this.firKonNrIdNaDok = firKonNrIdNaDok;
    }

    public String getFirEkd() {
        return firEkd;
    }

    public void setFirEkd(String firEkd) {
        this.firEkd = firEkd;
    }

    public Character getFirJednBudz() {
        return firJednBudz;
    }

    public void setFirJednBudz(Character firJednBudz) {
        this.firJednBudz = firJednBudz;
    }

    public Character getFirJednPozaBudz() {
        return firJednPozaBudz;
    }

    public void setFirJednPozaBudz(Character firJednPozaBudz) {
        this.firJednPozaBudz = firJednPozaBudz;
    }

    public String getFirOrgZal() {
        return firOrgZal;
    }

    public void setFirOrgZal(String firOrgZal) {
        this.firOrgZal = firOrgZal;
    }

    public Character getFirRejestr() {
        return firRejestr;
    }

    public void setFirRejestr(Character firRejestr) {
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

    public Character getFirAdrDzialInny() {
        return firAdrDzialInny;
    }

    public void setFirAdrDzialInny(Character firAdrDzialInny) {
        this.firAdrDzialInny = firAdrDzialInny;
    }

    public String getFirKod2() {
        return firKod2;
    }

    public void setFirKod2(String firKod2) {
        this.firKod2 = firKod2;
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

    public String getFirNrKonta3() {
        return firNrKonta3;
    }

    public void setFirNrKonta3(String firNrKonta3) {
        this.firNrKonta3 = firNrKonta3;
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

    public Character getFirAdrTsam2() {
        return firAdrTsam2;
    }

    public void setFirAdrTsam2(Character firAdrTsam2) {
        this.firAdrTsam2 = firAdrTsam2;
    }

    public Character getFirAdrTsamK() {
        return firAdrTsamK;
    }

    public void setFirAdrTsamK(Character firAdrTsamK) {
        this.firAdrTsamK = firAdrTsamK;
    }

    public Character getFirAdrTsamD() {
        return firAdrTsamD;
    }

    public void setFirAdrTsamD(Character firAdrTsamD) {
        this.firAdrTsamD = firAdrTsamD;
    }

    public Character getFirDodChar1() {
        return firDodChar1;
    }

    public void setFirDodChar1(Character firDodChar1) {
        this.firDodChar1 = firDodChar1;
    }

    public Character getFirDodChar2() {
        return firDodChar2;
    }

    public void setFirDodChar2(Character firDodChar2) {
        this.firDodChar2 = firDodChar2;
    }

    public Character getFirDodChar3() {
        return firDodChar3;
    }

    public void setFirDodChar3(Character firDodChar3) {
        this.firDodChar3 = firDodChar3;
    }

    public Character getFirDodChar4() {
        return firDodChar4;
    }

    public void setFirDodChar4(Character firDodChar4) {
        this.firDodChar4 = firDodChar4;
    }

    public Character getFirDodChar5() {
        return firDodChar5;
    }

    public void setFirDodChar5(Character firDodChar5) {
        this.firDodChar5 = firDodChar5;
    }

    public Character getFirDodChar6() {
        return firDodChar6;
    }

    public void setFirDodChar6(Character firDodChar6) {
        this.firDodChar6 = firDodChar6;
    }

    public Character getFirDodChar7() {
        return firDodChar7;
    }

    public void setFirDodChar7(Character firDodChar7) {
        this.firDodChar7 = firDodChar7;
    }

    public Character getFirDodChar8() {
        return firDodChar8;
    }

    public void setFirDodChar8(Character firDodChar8) {
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

    public Character getFirBiuro() {
        return firBiuro;
    }

    public void setFirBiuro(Character firBiuro) {
        this.firBiuro = firBiuro;
    }

    public Character getFirUbezpWypC() {
        return firUbezpWypC;
    }

    public void setFirUbezpWypC(Character firUbezpWypC) {
        this.firUbezpWypC = firUbezpWypC;
    }

    public Character getFirUbezpFpC() {
        return firUbezpFpC;
    }

    public void setFirUbezpFpC(Character firUbezpFpC) {
        this.firUbezpFpC = firUbezpFpC;
    }

    public Character getFirUbezpFgspC() {
        return firUbezpFgspC;
    }

    public void setFirUbezpFgspC(Character firUbezpFgspC) {
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

    public Character getFirDodChar9() {
        return firDodChar9;
    }

    public void setFirDodChar9(Character firDodChar9) {
        this.firDodChar9 = firDodChar9;
    }

    public Character getFirDodChar10() {
        return firDodChar10;
    }

    public void setFirDodChar10(Character firDodChar10) {
        this.firDodChar10 = firDodChar10;
    }

    public Character getFirDodChar11() {
        return firDodChar11;
    }

    public void setFirDodChar11(Character firDodChar11) {
        this.firDodChar11 = firDodChar11;
    }

    public Character getFirDodChar12() {
        return firDodChar12;
    }

    public void setFirDodChar12(Character firDodChar12) {
        this.firDodChar12 = firDodChar12;
    }

    public Character getFirDodChar13() {
        return firDodChar13;
    }

    public void setFirDodChar13(Character firDodChar13) {
        this.firDodChar13 = firDodChar13;
    }

    public Character getFirDodChar14() {
        return firDodChar14;
    }

    public void setFirDodChar14(Character firDodChar14) {
        this.firDodChar14 = firDodChar14;
    }

    public Character getFirDodChar15() {
        return firDodChar15;
    }

    public void setFirDodChar15(Character firDodChar15) {
        this.firDodChar15 = firDodChar15;
    }

    public Character getFirDodChar16() {
        return firDodChar16;
    }

    public void setFirDodChar16(Character firDodChar16) {
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

    public Character getFirDodChar17() {
        return firDodChar17;
    }

    public void setFirDodChar17(Character firDodChar17) {
        this.firDodChar17 = firDodChar17;
    }

    public Character getFirDodChar18() {
        return firDodChar18;
    }

    public void setFirDodChar18(Character firDodChar18) {
        this.firDodChar18 = firDodChar18;
    }

    public Character getFirDodChar19() {
        return firDodChar19;
    }

    public void setFirDodChar19(Character firDodChar19) {
        this.firDodChar19 = firDodChar19;
    }

    public Character getFirDodChar20() {
        return firDodChar20;
    }

    public void setFirDodChar20(Character firDodChar20) {
        this.firDodChar20 = firDodChar20;
    }

    public Character getFirDodChar21() {
        return firDodChar21;
    }

    public void setFirDodChar21(Character firDodChar21) {
        this.firDodChar21 = firDodChar21;
    }

    public Character getFirDodChar22() {
        return firDodChar22;
    }

    public void setFirDodChar22(Character firDodChar22) {
        this.firDodChar22 = firDodChar22;
    }

    public Character getFirDodChar23() {
        return firDodChar23;
    }

    public void setFirDodChar23(Character firDodChar23) {
        this.firDodChar23 = firDodChar23;
    }

    public Character getFirDodChar24() {
        return firDodChar24;
    }

    public void setFirDodChar24(Character firDodChar24) {
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

    @XmlTransient
    public List<DaneLiDa> getDaneLiDaList() {
        return daneLiDaList;
    }

    public void setDaneLiDaList(List<DaneLiDa> daneLiDaList) {
        this.daneLiDaList = daneLiDaList;
    }

    @XmlTransient
    public List<Dep> getDepList() {
        return depList;
    }

    public void setDepList(List<Dep> depList) {
        this.depList = depList;
    }

    @XmlTransient
    public List<Dokument> getDokumentList() {
        return dokumentList;
    }

    public void setDokumentList(List<Dokument> dokumentList) {
        this.dokumentList = dokumentList;
    }

    @XmlTransient
    public List<Ksiegapir> getKsiegapirList() {
        return ksiegapirList;
    }

    public void setKsiegapirList(List<Ksiegapir> ksiegapirList) {
        this.ksiegapirList = ksiegapirList;
    }

    @XmlTransient
    public List<LokGrupa> getLokGrupaList() {
        return lokGrupaList;
    }

    public void setLokGrupaList(List<LokGrupa> lokGrupaList) {
        this.lokGrupaList = lokGrupaList;
    }

    @XmlTransient
    public List<Zakupy> getZakupyList() {
        return zakupyList;
    }

    public void setZakupyList(List<Zakupy> zakupyList) {
        this.zakupyList = zakupyList;
    }

    @XmlTransient
    public List<DaneLiSl> getDaneLiSlList() {
        return daneLiSlList;
    }

    public void setDaneLiSlList(List<DaneLiSl> daneLiSlList) {
        this.daneLiSlList = daneLiSlList;
    }

    @XmlTransient
    public List<Zakdok> getZakdokList() {
        return zakdokList;
    }

    public void setZakdokList(List<Zakdok> zakdokList) {
        this.zakdokList = zakdokList;
    }

    @XmlTransient
    public List<Rejestr> getRejestrList() {
        return rejestrList;
    }

    public void setRejestrList(List<Rejestr> rejestrList) {
        this.rejestrList = rejestrList;
    }

    @XmlTransient
    public List<Vatrej> getVatrejList() {
        return vatrejList;
    }

    public void setVatrejList(List<Vatrej> vatrejList) {
        this.vatrejList = vatrejList;
    }

    @XmlTransient
    public List<Vat> getVatList() {
        return vatList;
    }

    public void setVatList(List<Vat> vatList) {
        this.vatList = vatList;
    }

    public Bank getFirFakBanSerial() {
        return firFakBanSerial;
    }

    public void setFirFakBanSerial(Bank firFakBanSerial) {
        this.firFakBanSerial = firFakBanSerial;
    }

    public Bank getFirZapBanSerial() {
        return firZapBanSerial;
    }

    public void setFirZapBanSerial(Bank firZapBanSerial) {
        this.firZapBanSerial = firZapBanSerial;
    }

    public Bank getFirBanSerial3() {
        return firBanSerial3;
    }

    public void setFirBanSerial3(Bank firBanSerial3) {
        this.firBanSerial3 = firBanSerial3;
    }

    public Bank getFirBanSerial4() {
        return firBanSerial4;
    }

    public void setFirBanSerial4(Bank firBanSerial4) {
        this.firBanSerial4 = firBanSerial4;
    }

    public Miasto getFirMiaSerial2() {
        return firMiaSerial2;
    }

    public void setFirMiaSerial2(Miasto firMiaSerial2) {
        this.firMiaSerial2 = firMiaSerial2;
    }

    public Miasto getFirMiaSerialK() {
        return firMiaSerialK;
    }

    public void setFirMiaSerialK(Miasto firMiaSerialK) {
        this.firMiaSerialK = firMiaSerialK;
    }

    public Miasto getFirMiaSerialD() {
        return firMiaSerialD;
    }

    public void setFirMiaSerialD(Miasto firMiaSerialD) {
        this.firMiaSerialD = firMiaSerialD;
    }

    public Miasto getFirMiaSerial() {
        return firMiaSerial;
    }

    public void setFirMiaSerial(Miasto firMiaSerial) {
        this.firMiaSerial = firMiaSerial;
    }

    public Panstwo getFirPanSerial() {
        return firPanSerial;
    }

    public void setFirPanSerial(Panstwo firPanSerial) {
        this.firPanSerial = firPanSerial;
    }

    public Urzad getFirUrzSerialV() {
        return firUrzSerialV;
    }

    public void setFirUrzSerialV(Urzad firUrzSerialV) {
        this.firUrzSerialV = firUrzSerialV;
    }

    @XmlTransient
    public List<StanGrup> getStanGrupList() {
        return stanGrupList;
    }

    public void setStanGrupList(List<StanGrup> stanGrupList) {
        this.stanGrupList = stanGrupList;
    }

    @XmlTransient
    public List<Wyposaz> getWyposazList() {
        return wyposazList;
    }

    public void setWyposazList(List<Wyposaz> wyposazList) {
        this.wyposazList = wyposazList;
    }

    @XmlTransient
    public List<DaneHiDa> getDaneHiDaList() {
        return daneHiDaList;
    }

    public void setDaneHiDaList(List<DaneHiDa> daneHiDaList) {
        this.daneHiDaList = daneHiDaList;
    }

    @XmlTransient
    public List<WynPotracenia> getWynPotraceniaList() {
        return wynPotraceniaList;
    }

    public void setWynPotraceniaList(List<WynPotracenia> wynPotraceniaList) {
        this.wynPotraceniaList = wynPotraceniaList;
    }

    @XmlTransient
    public List<Magdoktyp> getMagdoktypList() {
        return magdoktypList;
    }

    public void setMagdoktypList(List<Magdoktyp> magdoktypList) {
        this.magdoktypList = magdoktypList;
    }

    @XmlTransient
    public List<Srodki> getSrodkiList() {
        return srodkiList;
    }

    public void setSrodkiList(List<Srodki> srodkiList) {
        this.srodkiList = srodkiList;
    }

    @XmlTransient
    public List<SuperDep> getSuperDepList() {
        return superDepList;
    }

    public void setSuperDepList(List<SuperDep> superDepList) {
        this.superDepList = superDepList;
    }

    @XmlTransient
    public List<Cele> getCeleList() {
        return celeList;
    }

    public void setCeleList(List<Cele> celeList) {
        this.celeList = celeList;
    }

    @XmlTransient
    public List<DaneStat> getDaneStatList() {
        return daneStatList;
    }

    public void setDaneStatList(List<DaneStat> daneStatList) {
        this.daneStatList = daneStatList;
    }

    @XmlTransient
    public List<KonSlow> getKonSlowList() {
        return konSlowList;
    }

    public void setKonSlowList(List<KonSlow> konSlowList) {
        this.konSlowList = konSlowList;
    }

    @XmlTransient
    public List<Magazyn> getMagazynList() {
        return magazynList;
    }

    public void setMagazynList(List<Magazyn> magazynList) {
        this.magazynList = magazynList;
    }

    @XmlTransient
    public List<Magdok> getMagdokList() {
        return magdokList;
    }

    public void setMagdokList(List<Magdok> magdokList) {
        this.magdokList = magdokList;
    }

    @XmlTransient
    public List<Opiszdgosp> getOpiszdgospList() {
        return opiszdgospList;
    }

    public void setOpiszdgospList(List<Opiszdgosp> opiszdgospList) {
        this.opiszdgospList = opiszdgospList;
    }

    @XmlTransient
    public List<Konto> getKontoList() {
        return kontoList;
    }

    public void setKontoList(List<Konto> kontoList) {
        this.kontoList = kontoList;
    }

    @XmlTransient
    public List<StanPodgrup> getStanPodgrupList() {
        return stanPodgrupList;
    }

    public void setStanPodgrupList(List<StanPodgrup> stanPodgrupList) {
        this.stanPodgrupList = stanPodgrupList;
    }

    @XmlTransient
    public List<Kongrupa> getKongrupaList() {
        return kongrupaList;
    }

    public void setKongrupaList(List<Kongrupa> kongrupaList) {
        this.kongrupaList = kongrupaList;
    }

    @XmlTransient
    public List<Stanowisko> getStanowiskoList() {
        return stanowiskoList;
    }

    public void setStanowiskoList(List<Stanowisko> stanowiskoList) {
        this.stanowiskoList = stanowiskoList;
    }

    @XmlTransient
    public List<Przedmiot> getPrzedmiotList() {
        return przedmiotList;
    }

    public void setPrzedmiotList(List<Przedmiot> przedmiotList) {
        this.przedmiotList = przedmiotList;
    }

    @XmlTransient
    public List<Pion> getPionList() {
        return pionList;
    }

    public void setPionList(List<Pion> pionList) {
        this.pionList = pionList;
    }

    @XmlTransient
    public List<Sprzedaz> getSprzedazList() {
        return sprzedazList;
    }

    public void setSprzedazList(List<Sprzedaz> sprzedazList) {
        this.sprzedazList = sprzedazList;
    }

    @XmlTransient
    public List<OsobaZlec> getOsobaZlecList() {
        return osobaZlecList;
    }

    public void setOsobaZlecList(List<OsobaZlec> osobaZlecList) {
        this.osobaZlecList = osobaZlecList;
    }

    @XmlTransient
    public List<Grupa> getGrupaList() {
        return grupaList;
    }

    public void setGrupaList(List<Grupa> grupaList) {
        this.grupaList = grupaList;
    }

    @XmlTransient
    public List<Fakrach> getFakrachList() {
        return fakrachList;
    }

    public void setFakrachList(List<Fakrach> fakrachList) {
        this.fakrachList = fakrachList;
    }

    @XmlTransient
    public List<ScKamp> getScKampList() {
        return scKampList;
    }

    public void setScKampList(List<ScKamp> scKampList) {
        this.scKampList = scKampList;
    }

    @XmlTransient
    public List<DaneStDa> getDaneStDaList() {
        return daneStDaList;
    }

    public void setDaneStDaList(List<DaneStDa> daneStDaList) {
        this.daneStDaList = daneStDaList;
    }

    @XmlTransient
    public List<StSystOpis> getStSystOpisList() {
        return stSystOpisList;
    }

    public void setStSystOpisList(List<StSystOpis> stSystOpisList) {
        this.stSystOpisList = stSystOpisList;
    }

    @XmlTransient
    public List<Lokalizacja> getLokalizacjaList() {
        return lokalizacjaList;
    }

    public void setLokalizacjaList(List<Lokalizacja> lokalizacjaList) {
        this.lokalizacjaList = lokalizacjaList;
    }

    @XmlTransient
    public List<Rok> getRokList() {
        return rokList;
    }

    public void setRokList(List<Rok> rokList) {
        this.rokList = rokList;
    }

    @XmlTransient
    public List<TytulWpo> getTytulWpoList() {
        return tytulWpoList;
    }

    public void setTytulWpoList(List<TytulWpo> tytulWpoList) {
        this.tytulWpoList = tytulWpoList;
    }

    @XmlTransient
    public List<AdresHist> getAdresHistList() {
        return adresHistList;
    }

    public void setAdresHistList(List<AdresHist> adresHistList) {
        this.adresHistList = adresHistList;
    }

    @XmlTransient
    public List<Osoba> getOsobaList() {
        return osobaList;
    }

    public void setOsobaList(List<Osoba> osobaList) {
        this.osobaList = osobaList;
    }

    @XmlTransient
    public List<Trasy> getTrasyList() {
        return trasyList;
    }

    public void setTrasyList(List<Trasy> trasyList) {
        this.trasyList = trasyList;
    }

    @XmlTransient
    public List<Numer> getNumerList() {
        return numerList;
    }

    public void setNumerList(List<Numer> numerList) {
        this.numerList = numerList;
    }

    public boolean isZaimportowana() {
        return zaimportowana;
    }

    public void setZaimportowana(boolean zaimportowana) {
        this.zaimportowana = zaimportowana;
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
        return "Firma{" + "firNazwaSkr=" + firNazwaSkr + ", firNazwaPel=" + firNazwaPel + ", firNip=" + firNip + '}';
    }

    
}
