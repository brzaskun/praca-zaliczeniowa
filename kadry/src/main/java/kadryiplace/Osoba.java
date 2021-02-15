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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Osito
 */
@Entity
@Table(name = "osoba", catalog = "kadryiplace", schema = "dbo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Osoba.findAll", query = "SELECT o FROM Osoba o"),
    @NamedQuery(name = "Osoba.findByOsoSerial", query = "SELECT o FROM Osoba o WHERE o.osoSerial = :osoSerial"),
    @NamedQuery(name = "Osoba.findByOsoNazwisko", query = "SELECT o FROM Osoba o WHERE o.osoNazwisko = :osoNazwisko"),
    @NamedQuery(name = "Osoba.findByOsoImie1", query = "SELECT o FROM Osoba o WHERE o.osoImie1 = :osoImie1"),
    @NamedQuery(name = "Osoba.findByOsoImie2", query = "SELECT o FROM Osoba o WHERE o.osoImie2 = :osoImie2"),
    @NamedQuery(name = "Osoba.findByOsoImieOjca", query = "SELECT o FROM Osoba o WHERE o.osoImieOjca = :osoImieOjca"),
    @NamedQuery(name = "Osoba.findByOsoImieMatki", query = "SELECT o FROM Osoba o WHERE o.osoImieMatki = :osoImieMatki"),
    @NamedQuery(name = "Osoba.findByOsoUrodzData", query = "SELECT o FROM Osoba o WHERE o.osoUrodzData = :osoUrodzData"),
    @NamedQuery(name = "Osoba.findByOsoPesel", query = "SELECT o FROM Osoba o WHERE o.osoPesel = :osoPesel"),
    @NamedQuery(name = "Osoba.findByOsoNip", query = "SELECT o FROM Osoba o WHERE o.osoNip = :osoNip"),
    @NamedQuery(name = "Osoba.findByOsoUlica", query = "SELECT o FROM Osoba o WHERE o.osoUlica = :osoUlica"),
    @NamedQuery(name = "Osoba.findByOsoDom", query = "SELECT o FROM Osoba o WHERE o.osoDom = :osoDom"),
    @NamedQuery(name = "Osoba.findByOsoMieszkanie", query = "SELECT o FROM Osoba o WHERE o.osoMieszkanie = :osoMieszkanie"),
    @NamedQuery(name = "Osoba.findByOsoKod", query = "SELECT o FROM Osoba o WHERE o.osoKod = :osoKod"),
    @NamedQuery(name = "Osoba.findByOsoTel", query = "SELECT o FROM Osoba o WHERE o.osoTel = :osoTel"),
    @NamedQuery(name = "Osoba.findByOsoDataZatr", query = "SELECT o FROM Osoba o WHERE o.osoDataZatr = :osoDataZatr"),
    @NamedQuery(name = "Osoba.findByOsoDataZwol", query = "SELECT o FROM Osoba o WHERE o.osoDataZwol = :osoDataZwol"),
    @NamedQuery(name = "Osoba.findByOsoKonto", query = "SELECT o FROM Osoba o WHERE o.osoKonto = :osoKonto"),
    @NamedQuery(name = "Osoba.findByOsoUrzSerial", query = "SELECT o FROM Osoba o WHERE o.osoUrzSerial = :osoUrzSerial"),
    @NamedQuery(name = "Osoba.findByOsoZusDok", query = "SELECT o FROM Osoba o WHERE o.osoZusDok = :osoZusDok"),
    @NamedQuery(name = "Osoba.findByOsoZusNumer", query = "SELECT o FROM Osoba o WHERE o.osoZusNumer = :osoZusNumer"),
    @NamedQuery(name = "Osoba.findByOsoWynZasadn", query = "SELECT o FROM Osoba o WHERE o.osoWynZasadn = :osoWynZasadn"),
    @NamedQuery(name = "Osoba.findByOsoTyp", query = "SELECT o FROM Osoba o WHERE o.osoTyp = :osoTyp"),
    @NamedQuery(name = "Osoba.findByOsoGmina", query = "SELECT o FROM Osoba o WHERE o.osoGmina = :osoGmina"),
    @NamedQuery(name = "Osoba.findByOsoWynKoszty", query = "SELECT o FROM Osoba o WHERE o.osoWynKoszty = :osoWynKoszty"),
    @NamedQuery(name = "Osoba.findByOsoWynPotrInne1", query = "SELECT o FROM Osoba o WHERE o.osoWynPotrInne1 = :osoWynPotrInne1"),
    @NamedQuery(name = "Osoba.findByOsoWynPotrInne4", query = "SELECT o FROM Osoba o WHERE o.osoWynPotrInne4 = :osoWynPotrInne4"),
    @NamedQuery(name = "Osoba.findByOsoWynPotrInne2", query = "SELECT o FROM Osoba o WHERE o.osoWynPotrInne2 = :osoWynPotrInne2"),
    @NamedQuery(name = "Osoba.findByOsoWynPotrInne3", query = "SELECT o FROM Osoba o WHERE o.osoWynPotrInne3 = :osoWynPotrInne3"),
    @NamedQuery(name = "Osoba.findByOsoEtat1", query = "SELECT o FROM Osoba o WHERE o.osoEtat1 = :osoEtat1"),
    @NamedQuery(name = "Osoba.findByOsoEtat2", query = "SELECT o FROM Osoba o WHERE o.osoEtat2 = :osoEtat2"),
    @NamedQuery(name = "Osoba.findByOsoKodTytU12", query = "SELECT o FROM Osoba o WHERE o.osoKodTytU12 = :osoKodTytU12"),
    @NamedQuery(name = "Osoba.findByOsoKodTytU3", query = "SELECT o FROM Osoba o WHERE o.osoKodTytU3 = :osoKodTytU3"),
    @NamedQuery(name = "Osoba.findByOsoKodTytU4", query = "SELECT o FROM Osoba o WHERE o.osoKodTytU4 = :osoKodTytU4"),
    @NamedQuery(name = "Osoba.findByOsoFaks", query = "SELECT o FROM Osoba o WHERE o.osoFaks = :osoFaks"),
    @NamedQuery(name = "Osoba.findByOsoZasRodz", query = "SELECT o FROM Osoba o WHERE o.osoZasRodz = :osoZasRodz"),
    @NamedQuery(name = "Osoba.findByOsoZasRodzIlosc", query = "SELECT o FROM Osoba o WHERE o.osoZasRodzIlosc = :osoZasRodzIlosc"),
    @NamedQuery(name = "Osoba.findByOsoZasPiel", query = "SELECT o FROM Osoba o WHERE o.osoZasPiel = :osoZasPiel"),
    @NamedQuery(name = "Osoba.findByOsoZasPielIlosc", query = "SELECT o FROM Osoba o WHERE o.osoZasPielIlosc = :osoZasPielIlosc"),
    @NamedQuery(name = "Osoba.findByOsoZasWych", query = "SELECT o FROM Osoba o WHERE o.osoZasWych = :osoZasWych"),
    @NamedQuery(name = "Osoba.findByOsoPodDoch", query = "SELECT o FROM Osoba o WHERE o.osoPodDoch = :osoPodDoch"),
    @NamedQuery(name = "Osoba.findByOsoZasRodzKwota", query = "SELECT o FROM Osoba o WHERE o.osoZasRodzKwota = :osoZasRodzKwota"),
    @NamedQuery(name = "Osoba.findByOsoZasPielKwota", query = "SELECT o FROM Osoba o WHERE o.osoZasPielKwota = :osoZasPielKwota"),
    @NamedQuery(name = "Osoba.findByOsoZasWychKwota", query = "SELECT o FROM Osoba o WHERE o.osoZasWychKwota = :osoZasWychKwota"),
    @NamedQuery(name = "Osoba.findByOsoPowiat", query = "SELECT o FROM Osoba o WHERE o.osoPowiat = :osoPowiat"),
    @NamedQuery(name = "Osoba.findByOsoPoczta", query = "SELECT o FROM Osoba o WHERE o.osoPoczta = :osoPoczta"),
    @NamedQuery(name = "Osoba.findByOsoWynKosztyTyp", query = "SELECT o FROM Osoba o WHERE o.osoWynKosztyTyp = :osoWynKosztyTyp"),
    @NamedQuery(name = "Osoba.findByOsoWojewodztwo", query = "SELECT o FROM Osoba o WHERE o.osoWojewodztwo = :osoWojewodztwo"),
    @NamedQuery(name = "Osoba.findByOsoWynForma", query = "SELECT o FROM Osoba o WHERE o.osoWynForma = :osoWynForma"),
    @NamedQuery(name = "Osoba.findByOsoOddzDra", query = "SELECT o FROM Osoba o WHERE o.osoOddzDra = :osoOddzDra"),
    @NamedQuery(name = "Osoba.findByOsoPdstZus", query = "SELECT o FROM Osoba o WHERE o.osoPdstZus = :osoPdstZus"),
    @NamedQuery(name = "Osoba.findByOsoPdstChorWyp", query = "SELECT o FROM Osoba o WHERE o.osoPdstChorWyp = :osoPdstChorWyp"),
    @NamedQuery(name = "Osoba.findByOsoPdstZdrow", query = "SELECT o FROM Osoba o WHERE o.osoPdstZdrow = :osoPdstZdrow"),
    @NamedQuery(name = "Osoba.findByOsoKodTerm", query = "SELECT o FROM Osoba o WHERE o.osoKodTerm = :osoKodTerm"),
    @NamedQuery(name = "Osoba.findByOsoRegon", query = "SELECT o FROM Osoba o WHERE o.osoRegon = :osoRegon"),
    @NamedQuery(name = "Osoba.findByOsoPrzekrRpw", query = "SELECT o FROM Osoba o WHERE o.osoPrzekrRpw = :osoPrzekrRpw"),
    @NamedQuery(name = "Osoba.findByOsoUbezpWyp", query = "SELECT o FROM Osoba o WHERE o.osoUbezpWyp = :osoUbezpWyp"),
    @NamedQuery(name = "Osoba.findByOsoStaSerial", query = "SELECT o FROM Osoba o WHERE o.osoStaSerial = :osoStaSerial"),
    @NamedQuery(name = "Osoba.findByOsoPodStawka", query = "SELECT o FROM Osoba o WHERE o.osoPodStawka = :osoPodStawka"),
    @NamedQuery(name = "Osoba.findByOsoPodWolna", query = "SELECT o FROM Osoba o WHERE o.osoPodWolna = :osoPodWolna"),
    @NamedQuery(name = "Osoba.findByOsoPit40", query = "SELECT o FROM Osoba o WHERE o.osoPit40 = :osoPit40"),
    @NamedQuery(name = "Osoba.findByOsoMiejsceUr", query = "SELECT o FROM Osoba o WHERE o.osoMiejsceUr = :osoMiejsceUr"),
    @NamedQuery(name = "Osoba.findByOsoNrId", query = "SELECT o FROM Osoba o WHERE o.osoNrId = :osoNrId"),
    @NamedQuery(name = "Osoba.findByOsoPodWolna12m", query = "SELECT o FROM Osoba o WHERE o.osoPodWolna12m = :osoPodWolna12m"),
    @NamedQuery(name = "Osoba.findByOsoPanSerial2", query = "SELECT o FROM Osoba o WHERE o.osoPanSerial2 = :osoPanSerial2"),
    @NamedQuery(name = "Osoba.findByOsoUlica2", query = "SELECT o FROM Osoba o WHERE o.osoUlica2 = :osoUlica2"),
    @NamedQuery(name = "Osoba.findByOsoDom2", query = "SELECT o FROM Osoba o WHERE o.osoDom2 = :osoDom2"),
    @NamedQuery(name = "Osoba.findByOsoMieszkanie2", query = "SELECT o FROM Osoba o WHERE o.osoMieszkanie2 = :osoMieszkanie2"),
    @NamedQuery(name = "Osoba.findByOsoKod2", query = "SELECT o FROM Osoba o WHERE o.osoKod2 = :osoKod2"),
    @NamedQuery(name = "Osoba.findByOsoGmina2", query = "SELECT o FROM Osoba o WHERE o.osoGmina2 = :osoGmina2"),
    @NamedQuery(name = "Osoba.findByOsoPowiat2", query = "SELECT o FROM Osoba o WHERE o.osoPowiat2 = :osoPowiat2"),
    @NamedQuery(name = "Osoba.findByOsoPoczta2", query = "SELECT o FROM Osoba o WHERE o.osoPoczta2 = :osoPoczta2"),
    @NamedQuery(name = "Osoba.findByOsoWojewodztwo2", query = "SELECT o FROM Osoba o WHERE o.osoWojewodztwo2 = :osoWojewodztwo2"),
    @NamedQuery(name = "Osoba.findByOsoPlec", query = "SELECT o FROM Osoba o WHERE o.osoPlec = :osoPlec"),
    @NamedQuery(name = "Osoba.findByOsoEtatDoDra", query = "SELECT o FROM Osoba o WHERE o.osoEtatDoDra = :osoEtatDoDra"),
    @NamedQuery(name = "Osoba.findByOsoOgrPdstChor", query = "SELECT o FROM Osoba o WHERE o.osoOgrPdstChor = :osoOgrPdstChor"),
    @NamedQuery(name = "Osoba.findByOsoPodWolnaR", query = "SELECT o FROM Osoba o WHERE o.osoPodWolnaR = :osoPodWolnaR"),
    @NamedQuery(name = "Osoba.findByOsoPodWolnaProc", query = "SELECT o FROM Osoba o WHERE o.osoPodWolnaProc = :osoPodWolnaProc"),
    @NamedQuery(name = "Osoba.findByOsoWynKosztyR", query = "SELECT o FROM Osoba o WHERE o.osoWynKosztyR = :osoWynKosztyR"),
    @NamedQuery(name = "Osoba.findByOsoWynKosztyProc", query = "SELECT o FROM Osoba o WHERE o.osoWynKosztyProc = :osoWynKosztyProc"),
    @NamedQuery(name = "Osoba.findByOsoNazwiskoRod", query = "SELECT o FROM Osoba o WHERE o.osoNazwiskoRod = :osoNazwiskoRod"),
    @NamedQuery(name = "Osoba.findByOsoObywatelstwo", query = "SELECT o FROM Osoba o WHERE o.osoObywatelstwo = :osoObywatelstwo"),
    @NamedQuery(name = "Osoba.findByOsoKSP", query = "SELECT o FROM Osoba o WHERE o.osoKSP = :osoKSP"),
    @NamedQuery(name = "Osoba.findByOsoKCP", query = "SELECT o FROM Osoba o WHERE o.osoKCP = :osoKCP"),
    @NamedQuery(name = "Osoba.findByOsoOkresNiepOd", query = "SELECT o FROM Osoba o WHERE o.osoOkresNiepOd = :osoOkresNiepOd"),
    @NamedQuery(name = "Osoba.findByOsoKsp", query = "SELECT o FROM Osoba o WHERE o.osoKsp = :osoKsp"),
    @NamedQuery(name = "Osoba.findByOsoWspGosp", query = "SELECT o FROM Osoba o WHERE o.osoWspGosp = :osoWspGosp"),
    @NamedQuery(name = "Osoba.findByOsoKodNiezd", query = "SELECT o FROM Osoba o WHERE o.osoKodNiezd = :osoKodNiezd"),
    @NamedQuery(name = "Osoba.findByOsoOkresNiezdOd", query = "SELECT o FROM Osoba o WHERE o.osoOkresNiezdOd = :osoOkresNiezdOd"),
    @NamedQuery(name = "Osoba.findByOsoKodZawodu", query = "SELECT o FROM Osoba o WHERE o.osoKodZawodu = :osoKodZawodu"),
    @NamedQuery(name = "Osoba.findByOsoKodGorn", query = "SELECT o FROM Osoba o WHERE o.osoKodGorn = :osoKodGorn"),
    @NamedQuery(name = "Osoba.findByOsoOkrGornOd", query = "SELECT o FROM Osoba o WHERE o.osoOkrGornOd = :osoOkrGornOd"),
    @NamedQuery(name = "Osoba.findByOsoWykszt", query = "SELECT o FROM Osoba o WHERE o.osoWykszt = :osoWykszt"),
    @NamedQuery(name = "Osoba.findByOsoKodSzczeg", query = "SELECT o FROM Osoba o WHERE o.osoKodSzczeg = :osoKodSzczeg"),
    @NamedQuery(name = "Osoba.findByOsoOkresSzczOd", query = "SELECT o FROM Osoba o WHERE o.osoOkresSzczOd = :osoOkresSzczOd"),
    @NamedQuery(name = "Osoba.findByOsoKasaKod", query = "SELECT o FROM Osoba o WHERE o.osoKasaKod = :osoKasaKod"),
    @NamedQuery(name = "Osoba.findByOsoKasaNazwa", query = "SELECT o FROM Osoba o WHERE o.osoKasaNazwa = :osoKasaNazwa"),
    @NamedQuery(name = "Osoba.findByOsoKasaData", query = "SELECT o FROM Osoba o WHERE o.osoKasaData = :osoKasaData"),
    @NamedQuery(name = "Osoba.findByOsoOkresSzczDo", query = "SELECT o FROM Osoba o WHERE o.osoOkresSzczDo = :osoOkresSzczDo"),
    @NamedQuery(name = "Osoba.findByOsoOkrGornDo", query = "SELECT o FROM Osoba o WHERE o.osoOkrGornDo = :osoOkrGornDo"),
    @NamedQuery(name = "Osoba.findByOsoOkresNiezdDo", query = "SELECT o FROM Osoba o WHERE o.osoOkresNiezdDo = :osoOkresNiezdDo"),
    @NamedQuery(name = "Osoba.findByOsoOkresNiepDo", query = "SELECT o FROM Osoba o WHERE o.osoOkresNiepDo = :osoOkresNiepDo"),
    @NamedQuery(name = "Osoba.findByOsoPanSerialK", query = "SELECT o FROM Osoba o WHERE o.osoPanSerialK = :osoPanSerialK"),
    @NamedQuery(name = "Osoba.findByOsoMiaSerialK", query = "SELECT o FROM Osoba o WHERE o.osoMiaSerialK = :osoMiaSerialK"),
    @NamedQuery(name = "Osoba.findByOsoUlicaK", query = "SELECT o FROM Osoba o WHERE o.osoUlicaK = :osoUlicaK"),
    @NamedQuery(name = "Osoba.findByOsoDomK", query = "SELECT o FROM Osoba o WHERE o.osoDomK = :osoDomK"),
    @NamedQuery(name = "Osoba.findByOsoMieszkanieK", query = "SELECT o FROM Osoba o WHERE o.osoMieszkanieK = :osoMieszkanieK"),
    @NamedQuery(name = "Osoba.findByOsoKodK", query = "SELECT o FROM Osoba o WHERE o.osoKodK = :osoKodK"),
    @NamedQuery(name = "Osoba.findByOsoGminaK", query = "SELECT o FROM Osoba o WHERE o.osoGminaK = :osoGminaK"),
    @NamedQuery(name = "Osoba.findByOsoPowiatK", query = "SELECT o FROM Osoba o WHERE o.osoPowiatK = :osoPowiatK"),
    @NamedQuery(name = "Osoba.findByOsoPocztaK", query = "SELECT o FROM Osoba o WHERE o.osoPocztaK = :osoPocztaK"),
    @NamedQuery(name = "Osoba.findByOsoWojewodztwoK", query = "SELECT o FROM Osoba o WHERE o.osoWojewodztwoK = :osoWojewodztwoK"),
    @NamedQuery(name = "Osoba.findByOsoTelK", query = "SELECT o FROM Osoba o WHERE o.osoTelK = :osoTelK"),
    @NamedQuery(name = "Osoba.findByOsoFaksK", query = "SELECT o FROM Osoba o WHERE o.osoFaksK = :osoFaksK"),
    @NamedQuery(name = "Osoba.findByOsoTel2", query = "SELECT o FROM Osoba o WHERE o.osoTel2 = :osoTel2"),
    @NamedQuery(name = "Osoba.findByOsoFaks2", query = "SELECT o FROM Osoba o WHERE o.osoFaks2 = :osoFaks2"),
    @NamedQuery(name = "Osoba.findByOsoEmail", query = "SELECT o FROM Osoba o WHERE o.osoEmail = :osoEmail"),
    @NamedQuery(name = "Osoba.findByOsoDod1", query = "SELECT o FROM Osoba o WHERE o.osoDod1 = :osoDod1"),
    @NamedQuery(name = "Osoba.findByOsoDod2", query = "SELECT o FROM Osoba o WHERE o.osoDod2 = :osoDod2"),
    @NamedQuery(name = "Osoba.findByOsoDod3", query = "SELECT o FROM Osoba o WHERE o.osoDod3 = :osoDod3"),
    @NamedQuery(name = "Osoba.findByOsoSkrytkaK", query = "SELECT o FROM Osoba o WHERE o.osoSkrytkaK = :osoSkrytkaK"),
    @NamedQuery(name = "Osoba.findByOsoAdr2Tsam", query = "SELECT o FROM Osoba o WHERE o.osoAdr2Tsam = :osoAdr2Tsam"),
    @NamedQuery(name = "Osoba.findByOsoAdrKTsam", query = "SELECT o FROM Osoba o WHERE o.osoAdrKTsam = :osoAdrKTsam"),
    @NamedQuery(name = "Osoba.findByOsoZasRodzM", query = "SELECT o FROM Osoba o WHERE o.osoZasRodzM = :osoZasRodzM"),
    @NamedQuery(name = "Osoba.findByOsoZasWychS", query = "SELECT o FROM Osoba o WHERE o.osoZasWychS = :osoZasWychS"),
    @NamedQuery(name = "Osoba.findByOsoDod4", query = "SELECT o FROM Osoba o WHERE o.osoDod4 = :osoDod4"),
    @NamedQuery(name = "Osoba.findByOsoDod5", query = "SELECT o FROM Osoba o WHERE o.osoDod5 = :osoDod5"),
    @NamedQuery(name = "Osoba.findByOsoDod6", query = "SELECT o FROM Osoba o WHERE o.osoDod6 = :osoDod6"),
    @NamedQuery(name = "Osoba.findByOsoDod7", query = "SELECT o FROM Osoba o WHERE o.osoDod7 = :osoDod7"),
    @NamedQuery(name = "Osoba.findByOsoDod8", query = "SELECT o FROM Osoba o WHERE o.osoDod8 = :osoDod8"),
    @NamedQuery(name = "Osoba.findByOsoDod9", query = "SELECT o FROM Osoba o WHERE o.osoDod9 = :osoDod9"),
    @NamedQuery(name = "Osoba.findByOsoDod10", query = "SELECT o FROM Osoba o WHERE o.osoDod10 = :osoDod10"),
    @NamedQuery(name = "Osoba.findByOsoZasRodzR", query = "SELECT o FROM Osoba o WHERE o.osoZasRodzR = :osoZasRodzR"),
    @NamedQuery(name = "Osoba.findByOsoZasPielR", query = "SELECT o FROM Osoba o WHERE o.osoZasPielR = :osoZasPielR"),
    @NamedQuery(name = "Osoba.findByOsoZasWychR", query = "SELECT o FROM Osoba o WHERE o.osoZasWychR = :osoZasWychR"),
    @NamedQuery(name = "Osoba.findByOsoPodWolnaLp", query = "SELECT o FROM Osoba o WHERE o.osoPodWolnaLp = :osoPodWolnaLp"),
    @NamedQuery(name = "Osoba.findByOsoZasWychP3", query = "SELECT o FROM Osoba o WHERE o.osoZasWychP3 = :osoZasWychP3"),
    @NamedQuery(name = "Osoba.findByOsoPit4", query = "SELECT o FROM Osoba o WHERE o.osoPit4 = :osoPit4"),
    @NamedQuery(name = "Osoba.findByOsoPdstZusR", query = "SELECT o FROM Osoba o WHERE o.osoPdstZusR = :osoPdstZusR"),
    @NamedQuery(name = "Osoba.findByOsoPdstChorWypR", query = "SELECT o FROM Osoba o WHERE o.osoPdstChorWypR = :osoPdstChorWypR"),
    @NamedQuery(name = "Osoba.findByOsoPdstZdrowR", query = "SELECT o FROM Osoba o WHERE o.osoPdstZdrowR = :osoPdstZdrowR"),
    @NamedQuery(name = "Osoba.findByOsoPdstZusP", query = "SELECT o FROM Osoba o WHERE o.osoPdstZusP = :osoPdstZusP"),
    @NamedQuery(name = "Osoba.findByOsoPdstChorWypP", query = "SELECT o FROM Osoba o WHERE o.osoPdstChorWypP = :osoPdstChorWypP"),
    @NamedQuery(name = "Osoba.findByOsoPdstZdrowP", query = "SELECT o FROM Osoba o WHERE o.osoPdstZdrowP = :osoPdstZdrowP"),
    @NamedQuery(name = "Osoba.findByOsoUbezpWypC", query = "SELECT o FROM Osoba o WHERE o.osoUbezpWypC = :osoUbezpWypC"),
    @NamedQuery(name = "Osoba.findByOsoOsoSerial", query = "SELECT o FROM Osoba o WHERE o.osoOsoSerial = :osoOsoSerial"),
    @NamedQuery(name = "Osoba.findByOsoDodData1", query = "SELECT o FROM Osoba o WHERE o.osoDodData1 = :osoDodData1"),
    @NamedQuery(name = "Osoba.findByOsoDodData2", query = "SELECT o FROM Osoba o WHERE o.osoDodData2 = :osoDodData2"),
    @NamedQuery(name = "Osoba.findByOsoDodData3", query = "SELECT o FROM Osoba o WHERE o.osoDodData3 = :osoDodData3"),
    @NamedQuery(name = "Osoba.findByOsoDodData4", query = "SELECT o FROM Osoba o WHERE o.osoDodData4 = :osoDodData4"),
    @NamedQuery(name = "Osoba.findByOsoDodNum1", query = "SELECT o FROM Osoba o WHERE o.osoDodNum1 = :osoDodNum1"),
    @NamedQuery(name = "Osoba.findByOsoDodNum2", query = "SELECT o FROM Osoba o WHERE o.osoDodNum2 = :osoDodNum2"),
    @NamedQuery(name = "Osoba.findByOsoDodNum3", query = "SELECT o FROM Osoba o WHERE o.osoDodNum3 = :osoDodNum3"),
    @NamedQuery(name = "Osoba.findByOsoDodNum4", query = "SELECT o FROM Osoba o WHERE o.osoDodNum4 = :osoDodNum4"),
    @NamedQuery(name = "Osoba.findByOsoDodData5", query = "SELECT o FROM Osoba o WHERE o.osoDodData5 = :osoDodData5"),
    @NamedQuery(name = "Osoba.findByOsoDodData6", query = "SELECT o FROM Osoba o WHERE o.osoDodData6 = :osoDodData6"),
    @NamedQuery(name = "Osoba.findByOsoDodVchar1", query = "SELECT o FROM Osoba o WHERE o.osoDodVchar1 = :osoDodVchar1"),
    @NamedQuery(name = "Osoba.findByOsoDodVchar2", query = "SELECT o FROM Osoba o WHERE o.osoDodVchar2 = :osoDodVchar2"),
    @NamedQuery(name = "Osoba.findByOsoDodVchar3", query = "SELECT o FROM Osoba o WHERE o.osoDodVchar3 = :osoDodVchar3"),
    @NamedQuery(name = "Osoba.findByOsoDodVchar4", query = "SELECT o FROM Osoba o WHERE o.osoDodVchar4 = :osoDodVchar4"),
    @NamedQuery(name = "Osoba.findByOsoDod11", query = "SELECT o FROM Osoba o WHERE o.osoDod11 = :osoDod11"),
    @NamedQuery(name = "Osoba.findByOsoDod12", query = "SELECT o FROM Osoba o WHERE o.osoDod12 = :osoDod12"),
    @NamedQuery(name = "Osoba.findByOsoDod13", query = "SELECT o FROM Osoba o WHERE o.osoDod13 = :osoDod13"),
    @NamedQuery(name = "Osoba.findByOsoDod14", query = "SELECT o FROM Osoba o WHERE o.osoDod14 = :osoDod14"),
    @NamedQuery(name = "Osoba.findByOsoDod15", query = "SELECT o FROM Osoba o WHERE o.osoDod15 = :osoDod15"),
    @NamedQuery(name = "Osoba.findByOsoDodNum5", query = "SELECT o FROM Osoba o WHERE o.osoDodNum5 = :osoDodNum5"),
    @NamedQuery(name = "Osoba.findByOsoDodNum6", query = "SELECT o FROM Osoba o WHERE o.osoDodNum6 = :osoDodNum6"),
    @NamedQuery(name = "Osoba.findByOsoDodNum7", query = "SELECT o FROM Osoba o WHERE o.osoDodNum7 = :osoDodNum7"),
    @NamedQuery(name = "Osoba.findByOsoDodNum8", query = "SELECT o FROM Osoba o WHERE o.osoDodNum8 = :osoDodNum8"),
    @NamedQuery(name = "Osoba.findByOsoDodData7", query = "SELECT o FROM Osoba o WHERE o.osoDodData7 = :osoDodData7"),
    @NamedQuery(name = "Osoba.findByOsoDodData8", query = "SELECT o FROM Osoba o WHERE o.osoDodData8 = :osoDodData8"),
    @NamedQuery(name = "Osoba.findByOsoKalendarz", query = "SELECT o FROM Osoba o WHERE o.osoKalendarz = :osoKalendarz"),
    @NamedQuery(name = "Osoba.findByOsoDodInt1", query = "SELECT o FROM Osoba o WHERE o.osoDodInt1 = :osoDodInt1"),
    @NamedQuery(name = "Osoba.findByOsoDodInt2", query = "SELECT o FROM Osoba o WHERE o.osoDodInt2 = :osoDodInt2"),
    @NamedQuery(name = "Osoba.findByOsoDodInt3", query = "SELECT o FROM Osoba o WHERE o.osoDodInt3 = :osoDodInt3"),
    @NamedQuery(name = "Osoba.findByOsoDodInt4", query = "SELECT o FROM Osoba o WHERE o.osoDodInt4 = :osoDodInt4"),
    @NamedQuery(name = "Osoba.findByOsoKchSerial", query = "SELECT o FROM Osoba o WHERE o.osoKchSerial = :osoKchSerial"),
    @NamedQuery(name = "Osoba.findByOsoDodData9", query = "SELECT o FROM Osoba o WHERE o.osoDodData9 = :osoDodData9"),
    @NamedQuery(name = "Osoba.findByOsoDodData10", query = "SELECT o FROM Osoba o WHERE o.osoDodData10 = :osoDodData10"),
    @NamedQuery(name = "Osoba.findByOsoDodData11", query = "SELECT o FROM Osoba o WHERE o.osoDodData11 = :osoDodData11"),
    @NamedQuery(name = "Osoba.findByOsoDodData12", query = "SELECT o FROM Osoba o WHERE o.osoDodData12 = :osoDodData12"),
    @NamedQuery(name = "Osoba.findByOsoWktSerial", query = "SELECT o FROM Osoba o WHERE o.osoWktSerial = :osoWktSerial"),
    @NamedQuery(name = "Osoba.findByOsoDodInt5", query = "SELECT o FROM Osoba o WHERE o.osoDodInt5 = :osoDodInt5"),
    @NamedQuery(name = "Osoba.findByOsoDodInt6", query = "SELECT o FROM Osoba o WHERE o.osoDodInt6 = :osoDodInt6"),
    @NamedQuery(name = "Osoba.findByOsoUrlopowe", query = "SELECT o FROM Osoba o WHERE o.osoUrlopowe = :osoUrlopowe"),
    @NamedQuery(name = "Osoba.findByOsoChorobowe", query = "SELECT o FROM Osoba o WHERE o.osoChorobowe = :osoChorobowe"),
    @NamedQuery(name = "Osoba.findByOsoNusp", query = "SELECT o FROM Osoba o WHERE o.osoNusp = :osoNusp")})
public class Osoba implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_serial", nullable = false)
    private Integer osoSerial;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 31)
    @Column(name = "oso_nazwisko", nullable = false, length = 31)
    private String osoNazwisko;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 22)
    @Column(name = "oso_imie1", nullable = false, length = 22)
    private String osoImie1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 22)
    @Column(name = "oso_imie2", nullable = false, length = 22)
    private String osoImie2;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 22)
    @Column(name = "oso_imie_ojca", nullable = false, length = 22)
    private String osoImieOjca;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 22)
    @Column(name = "oso_imie_matki", nullable = false, length = 22)
    private String osoImieMatki;
    @Column(name = "oso_urodz_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoUrodzData;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "oso_pesel", nullable = false, length = 16)
    private String osoPesel;
    @Size(max = 16)
    @Column(name = "oso_nip", length = 16)
    private String osoNip;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 30)
    @Column(name = "oso_ulica", nullable = false, length = 30)
    private String osoUlica;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "oso_dom", nullable = false, length = 7)
    private String osoDom;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 7)
    @Column(name = "oso_mieszkanie", nullable = false, length = 7)
    private String osoMieszkanie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 5)
    @Column(name = "oso_kod", nullable = false, length = 5)
    private String osoKod;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "oso_tel", nullable = false, length = 16)
    private String osoTel;
    @Column(name = "oso_data_zatr")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoDataZatr;
    @Column(name = "oso_data_zwol")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoDataZwol;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "oso_konto", nullable = false, length = 64)
    private String osoKonto;
    @Column(name = "oso_urz_serial")
    private Integer osoUrzSerial;
    @Column(name = "oso_zus_dok")
    private Character osoZusDok;
    @Size(max = 16)
    @Column(name = "oso_zus_numer", length = 16)
    private String osoZusNumer;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "oso_wyn_zasadn", precision = 13, scale = 2)
    private BigDecimal osoWynZasadn;
    @Column(name = "oso_typ")
    private Character osoTyp;
    @Size(max = 26)
    @Column(name = "oso_gmina", length = 26)
    private String osoGmina;
    @Column(name = "oso_wyn_koszty", precision = 13, scale = 2)
    private BigDecimal osoWynKoszty;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_wyn_potr_inne_1", nullable = false, precision = 13, scale = 2)
    private BigDecimal osoWynPotrInne1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_wyn_potr_inne_4", nullable = false, precision = 13, scale = 2)
    private BigDecimal osoWynPotrInne4;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_wyn_potr_inne_2", nullable = false, precision = 13, scale = 2)
    private BigDecimal osoWynPotrInne2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_wyn_potr_inne_3", nullable = false, precision = 13, scale = 2)
    private BigDecimal osoWynPotrInne3;
    @Column(name = "oso_etat_1")
    private Short osoEtat1;
    @Column(name = "oso_etat_2")
    private Short osoEtat2;
    @Size(max = 8)
    @Column(name = "oso_kod_tyt_u_1_2", length = 8)
    private String osoKodTytU12;
    @Column(name = "oso_kod_tyt_u_3")
    private Character osoKodTytU3;
    @Column(name = "oso_kod_tyt_u_4")
    private Character osoKodTytU4;
    @Size(max = 16)
    @Column(name = "oso_faks", length = 16)
    private String osoFaks;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_zas_rodz", nullable = false)
    private Character osoZasRodz;
    @Column(name = "oso_zas_rodz_ilosc")
    private Short osoZasRodzIlosc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_zas_piel", nullable = false)
    private Character osoZasPiel;
    @Column(name = "oso_zas_piel_ilosc")
    private Short osoZasPielIlosc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_zas_wych", nullable = false)
    private Character osoZasWych;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_pod_doch", nullable = false)
    private Character osoPodDoch;
    @Column(name = "oso_zas_rodz_kwota", precision = 13, scale = 2)
    private BigDecimal osoZasRodzKwota;
    @Column(name = "oso_zas_piel_kwota", precision = 13, scale = 2)
    private BigDecimal osoZasPielKwota;
    @Column(name = "oso_zas_wych_kwota", precision = 13, scale = 2)
    private BigDecimal osoZasWychKwota;
    @Size(max = 26)
    @Column(name = "oso_powiat", length = 26)
    private String osoPowiat;
    @Size(max = 26)
    @Column(name = "oso_poczta", length = 26)
    private String osoPoczta;
    @Column(name = "oso_wyn_koszty_typ")
    private Short osoWynKosztyTyp;
    @Size(max = 26)
    @Column(name = "oso_wojewodztwo", length = 26)
    private String osoWojewodztwo;
    @Column(name = "oso_wyn_forma")
    private Short osoWynForma;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_oddz_dra", nullable = false)
    private Character osoOddzDra;
    @Column(name = "oso_pdst_zus", precision = 13, scale = 2)
    private BigDecimal osoPdstZus;
    @Column(name = "oso_pdst_chor_wyp", precision = 13, scale = 2)
    private BigDecimal osoPdstChorWyp;
    @Column(name = "oso_pdst_zdrow", precision = 13, scale = 2)
    private BigDecimal osoPdstZdrow;
    @Column(name = "oso_kod_term")
    private Character osoKodTerm;
    @Size(max = 16)
    @Column(name = "oso_regon", length = 16)
    private String osoRegon;
    @Column(name = "oso_przekr_rpw")
    private Character osoPrzekrRpw;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_ubezp_wyp", nullable = false, precision = 5, scale = 2)
    private BigDecimal osoUbezpWyp;
    @Column(name = "oso_sta_serial")
    private Integer osoStaSerial;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_pod_stawka", nullable = false, precision = 5, scale = 2)
    private BigDecimal osoPodStawka;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_pod_wolna", nullable = false, precision = 13, scale = 2)
    private BigDecimal osoPodWolna;
    @Column(name = "oso_pit_40")
    private Character osoPit40;
    @Size(max = 26)
    @Column(name = "oso_miejsce_ur", length = 26)
    private String osoMiejsceUr;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 16)
    @Column(name = "oso_nr_id", nullable = false, length = 16)
    private String osoNrId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_pod_wolna_12m", nullable = false, precision = 13, scale = 2)
    private BigDecimal osoPodWolna12m;
    @Column(name = "oso_pan_serial_2")
    private Integer osoPanSerial2;
    @Size(max = 30)
    @Column(name = "oso_ulica_2", length = 30)
    private String osoUlica2;
    @Size(max = 10)
    @Column(name = "oso_dom_2", length = 10)
    private String osoDom2;
    @Size(max = 10)
    @Column(name = "oso_mieszkanie_2", length = 10)
    private String osoMieszkanie2;
    @Size(max = 5)
    @Column(name = "oso_kod_2", length = 5)
    private String osoKod2;
    @Size(max = 26)
    @Column(name = "oso_gmina_2", length = 26)
    private String osoGmina2;
    @Size(max = 26)
    @Column(name = "oso_powiat_2", length = 26)
    private String osoPowiat2;
    @Size(max = 26)
    @Column(name = "oso_poczta_2", length = 26)
    private String osoPoczta2;
    @Size(max = 26)
    @Column(name = "oso_wojewodztwo_2", length = 26)
    private String osoWojewodztwo2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_plec", nullable = false)
    private Character osoPlec;
    @Column(name = "oso_etat_do_dra")
    private Character osoEtatDoDra;
    @Column(name = "oso_ogr_pdst_chor")
    private Character osoOgrPdstChor;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_pod_wolna_r", nullable = false)
    private Character osoPodWolnaR;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_pod_wolna_proc", nullable = false, precision = 5, scale = 2)
    private BigDecimal osoPodWolnaProc;
    @Column(name = "oso_wyn_koszty_r")
    private Character osoWynKosztyR;
    @Column(name = "oso_wyn_koszty_proc", precision = 5, scale = 2)
    private BigDecimal osoWynKosztyProc;
    @Size(max = 31)
    @Column(name = "oso_nazwisko_rod", length = 31)
    private String osoNazwiskoRod;
    @Size(max = 22)
    @Column(name = "oso_obywatelstwo", length = 22)
    private String osoObywatelstwo;
    @Column(name = "oso_k_s_p")
    private Character osoKSP;
    @Column(name = "oso_k_c_p")
    private Character osoKCP;
    @Column(name = "oso_okres_niep_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoOkresNiepOd;
    @Size(max = 2)
    @Column(name = "oso_ksp", length = 2)
    private String osoKsp;
    @Column(name = "oso_wsp_gosp")
    private Character osoWspGosp;
    @Size(max = 2)
    @Column(name = "oso_kod_niezd", length = 2)
    private String osoKodNiezd;
    @Column(name = "oso_okres_niezd_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoOkresNiezdOd;
    @Size(max = 16)
    @Column(name = "oso_kod_zawodu", length = 16)
    private String osoKodZawodu;
    @Size(max = 16)
    @Column(name = "oso_kod_gorn", length = 16)
    private String osoKodGorn;
    @Column(name = "oso_okr_gorn_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoOkrGornOd;
    @Size(max = 2)
    @Column(name = "oso_wykszt", length = 2)
    private String osoWykszt;
    @Size(max = 16)
    @Column(name = "oso_kod_szczeg", length = 16)
    private String osoKodSzczeg;
    @Column(name = "oso_okres_szcz_od")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoOkresSzczOd;
    @Size(max = 8)
    @Column(name = "oso_kasa_kod", length = 8)
    private String osoKasaKod;
    @Size(max = 23)
    @Column(name = "oso_kasa_nazwa", length = 23)
    private String osoKasaNazwa;
    @Column(name = "oso_kasa_data")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoKasaData;
    @Column(name = "oso_okres_szcz_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoOkresSzczDo;
    @Column(name = "oso_okr_gorn_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoOkrGornDo;
    @Column(name = "oso_okres_niezd_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoOkresNiezdDo;
    @Column(name = "oso_okres_niep_do")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoOkresNiepDo;
    @Column(name = "oso_pan_serial_k")
    private Integer osoPanSerialK;
    @Column(name = "oso_mia_serial_k")
    private Integer osoMiaSerialK;
    @Size(max = 30)
    @Column(name = "oso_ulica_k", length = 30)
    private String osoUlicaK;
    @Size(max = 7)
    @Column(name = "oso_dom_k", length = 7)
    private String osoDomK;
    @Size(max = 7)
    @Column(name = "oso_mieszkanie_k", length = 7)
    private String osoMieszkanieK;
    @Size(max = 5)
    @Column(name = "oso_kod_k", length = 5)
    private String osoKodK;
    @Size(max = 26)
    @Column(name = "oso_gmina_k", length = 26)
    private String osoGminaK;
    @Size(max = 26)
    @Column(name = "oso_powiat_k", length = 26)
    private String osoPowiatK;
    @Size(max = 26)
    @Column(name = "oso_poczta_k", length = 26)
    private String osoPocztaK;
    @Size(max = 26)
    @Column(name = "oso_wojewodztwo_k", length = 26)
    private String osoWojewodztwoK;
    @Size(max = 16)
    @Column(name = "oso_tel_k", length = 16)
    private String osoTelK;
    @Size(max = 16)
    @Column(name = "oso_faks_k", length = 16)
    private String osoFaksK;
    @Size(max = 16)
    @Column(name = "oso_tel_2", length = 16)
    private String osoTel2;
    @Size(max = 16)
    @Column(name = "oso_faks_2", length = 16)
    private String osoFaks2;
    @Size(max = 96)
    @Column(name = "oso_email", length = 96)
    private String osoEmail;
    @Column(name = "oso_dod_1")
    private Character osoDod1;
    @Column(name = "oso_dod_2")
    private Character osoDod2;
    @Column(name = "oso_dod_3")
    private Character osoDod3;
    @Size(max = 5)
    @Column(name = "oso_skrytka_k", length = 5)
    private String osoSkrytkaK;
    @Column(name = "oso_adr_2_tsam")
    private Character osoAdr2Tsam;
    @Column(name = "oso_adr_k_tsam")
    private Character osoAdrKTsam;
    @Column(name = "oso_zas_rodz_m")
    private Character osoZasRodzM;
    @Column(name = "oso_zas_wych_s")
    private Character osoZasWychS;
    @Column(name = "oso_dod_4")
    private Character osoDod4;
    @Column(name = "oso_dod_5")
    private Character osoDod5;
    @Column(name = "oso_dod_6")
    private Character osoDod6;
    @Column(name = "oso_dod_7")
    private Character osoDod7;
    @Column(name = "oso_dod_8")
    private Character osoDod8;
    @Column(name = "oso_dod_9")
    private Character osoDod9;
    @Column(name = "oso_dod_10")
    private Character osoDod10;
    @Column(name = "oso_zas_rodz_r")
    private Character osoZasRodzR;
    @Column(name = "oso_zas_piel_r")
    private Character osoZasPielR;
    @Column(name = "oso_zas_wych_r")
    private Character osoZasWychR;
    @Column(name = "oso_pod_wolna_lp")
    private Character osoPodWolnaLp;
    @Column(name = "oso_zas_wych_p_3")
    private Character osoZasWychP3;
    @Column(name = "oso_pit_4")
    private Character osoPit4;
    @Column(name = "oso_pdst_zus_r")
    private Character osoPdstZusR;
    @Column(name = "oso_pdst_chor_wyp_r")
    private Character osoPdstChorWypR;
    @Column(name = "oso_pdst_zdrow_r")
    private Character osoPdstZdrowR;
    @Column(name = "oso_pdst_zus_p", precision = 5, scale = 2)
    private BigDecimal osoPdstZusP;
    @Column(name = "oso_pdst_chor_wyp_p", precision = 5, scale = 2)
    private BigDecimal osoPdstChorWypP;
    @Column(name = "oso_pdst_zdrow_p", precision = 5, scale = 2)
    private BigDecimal osoPdstZdrowP;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_ubezp_wyp_c", nullable = false)
    private Character osoUbezpWypC;
    @Column(name = "oso_oso_serial")
    private Integer osoOsoSerial;
    @Column(name = "oso_dod_data_1")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoDodData1;
    @Column(name = "oso_dod_data_2")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoDodData2;
    @Column(name = "oso_dod_data_3")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoDodData3;
    @Column(name = "oso_dod_data_4")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoDodData4;
    @Column(name = "oso_dod_num_1", precision = 17, scale = 6)
    private BigDecimal osoDodNum1;
    @Column(name = "oso_dod_num_2", precision = 17, scale = 6)
    private BigDecimal osoDodNum2;
    @Column(name = "oso_dod_num_3", precision = 17, scale = 6)
    private BigDecimal osoDodNum3;
    @Column(name = "oso_dod_num_4", precision = 17, scale = 6)
    private BigDecimal osoDodNum4;
    @Column(name = "oso_dod_data_5")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoDodData5;
    @Column(name = "oso_dod_data_6")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoDodData6;
    @Size(max = 16)
    @Column(name = "oso_dod_vchar_1", length = 16)
    private String osoDodVchar1;
    @Size(max = 16)
    @Column(name = "oso_dod_vchar_2", length = 16)
    private String osoDodVchar2;
    @Size(max = 64)
    @Column(name = "oso_dod_vchar_3", length = 64)
    private String osoDodVchar3;
    @Size(max = 64)
    @Column(name = "oso_dod_vchar_4", length = 64)
    private String osoDodVchar4;
    @Column(name = "oso_dod_11")
    private Character osoDod11;
    @Column(name = "oso_dod_12")
    private Character osoDod12;
    @Column(name = "oso_dod_13")
    private Character osoDod13;
    @Column(name = "oso_dod_14")
    private Character osoDod14;
    @Column(name = "oso_dod_15")
    private Character osoDod15;
    @Column(name = "oso_dod_num_5", precision = 17, scale = 6)
    private BigDecimal osoDodNum5;
    @Column(name = "oso_dod_num_6", precision = 17, scale = 6)
    private BigDecimal osoDodNum6;
    @Column(name = "oso_dod_num_7", precision = 17, scale = 6)
    private BigDecimal osoDodNum7;
    @Column(name = "oso_dod_num_8", precision = 17, scale = 6)
    private BigDecimal osoDodNum8;
    @Column(name = "oso_dod_data_7")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoDodData7;
    @Column(name = "oso_dod_data_8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoDodData8;
    @Column(name = "oso_kalendarz")
    private Character osoKalendarz;
    @Column(name = "oso_dod_int_1")
    private Integer osoDodInt1;
    @Column(name = "oso_dod_int_2")
    private Integer osoDodInt2;
    @Column(name = "oso_dod_int_3")
    private Integer osoDodInt3;
    @Column(name = "oso_dod_int_4")
    private Integer osoDodInt4;
    @Column(name = "oso_kch_serial")
    private Integer osoKchSerial;
    @Column(name = "oso_dod_data_9")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoDodData9;
    @Column(name = "oso_dod_data_10")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoDodData10;
    @Column(name = "oso_dod_data_11")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoDodData11;
    @Column(name = "oso_dod_data_12")
    @Temporal(TemporalType.TIMESTAMP)
    private Date osoDodData12;
    @Column(name = "oso_wkt_serial")
    private Integer osoWktSerial;
    @Column(name = "oso_dod_int_5")
    private Integer osoDodInt5;
    @Column(name = "oso_dod_int_6")
    private Integer osoDodInt6;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_urlopowe", nullable = false)
    private Character osoUrlopowe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "oso_chorobowe", nullable = false)
    private Character osoChorobowe;
    @Size(max = 32)
    @Column(name = "oso_nusp", length = 32)
    private String osoNusp;
    @OneToMany(mappedBy = "dliOsoSerial")
    private List<DaneLiDa> daneLiDaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ospOsoSerial")
    private List<OsobaPit> osobaPitList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "wehOsoSerial")
    private List<WymiarHist> wymiarHistList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sklOsoSerial")
    private List<PlaceSkl> placeSklList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zahOsoSerial")
    private List<ZatrudHist> zatrudHistList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "opoOsoSerial")
    private List<OsobaPot> osobaPotList;
    @OneToMany(mappedBy = "dhiOsoSerial")
    private List<DaneHiDa> daneHiDaList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pzzOsoSerial")
    private List<PlacePrzZus> placePrzZusList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sthOsoSerial")
    private List<StanHist> stanHistList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "przOsoSerial")
    private List<PlacePrz> placePrzList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dzhOsoSerial")
    private List<DzialHist> dzialHistList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lplOsoSerial")
    private List<Place> placeList;
    @OneToMany(mappedBy = "eahOsoSerial")
    private List<EtataHist> etataHistList;
    @OneToMany(mappedBy = "rzuOsoSerial")
    private List<Rozliczus> rozliczusList;
    @OneToMany(mappedBy = "dstOsoSerial")
    private List<DaneStat> daneStatList;
    @OneToMany(mappedBy = "pojOsoSerial")
    private List<Pojazdy> pojazdyList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "kulOsoSerial")
    private List<KursList> kursListList;
    @OneToMany(mappedBy = "kntOsoSerial")
    private List<Konto> kontoList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "pzlOsoSerial")
    private List<PlaceZlec> placeZlecList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "jelOsoSerial")
    private List<JezykList> jezykListList;
    @OneToMany(mappedBy = "kldOsoSerial")
    private List<Kalendarz> kalendarzList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rodOsoSerial")
    private List<RozlOdli> rozlOdliList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ozlOsoSerial")
    private List<OsobaZlec> osobaZlecList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ossOsoSerial")
    private List<OsobaSkl> osobaSklList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "osdOsoSerial")
    private List<OsobaDet> osobaDetList;
    @OneToMany(mappedBy = "ebhOsoSerial")
    private List<EtatbHist> etatbHistList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ppoOsoSerial")
    private List<PlacePot> placePotList;
    @OneToMany(mappedBy = "dasOsoSerial")
    private List<DaneStDa> daneStDaList;
    @OneToMany(mappedBy = "ssdOsoSerial")
    private List<StSystOpis> stSystOpisList;
    @OneToMany(mappedBy = "adhOsoSerial")
    private List<AdresHist> adresHistList;
    @JoinColumn(name = "oso_ban_serial", referencedColumnName = "ban_serial")
    @ManyToOne
    private Bank osoBanSerial;
    @JoinColumn(name = "oso_dep_serial", referencedColumnName = "dep_serial")
    @ManyToOne
    private Dep osoDepSerial;
    @JoinColumn(name = "oso_fir_serial", referencedColumnName = "fir_serial", nullable = false)
    @ManyToOne(optional = false)
    private Firma osoFirSerial;
    @JoinColumn(name = "oso_mia_serial_2", referencedColumnName = "mia_serial")
    @ManyToOne
    private Miasto osoMiaSerial2;
    @JoinColumn(name = "oso_mia_serial", referencedColumnName = "mia_serial")
    @ManyToOne
    private Miasto osoMiaSerial;
    @JoinColumn(name = "oso_pan_serial", referencedColumnName = "pan_serial")
    @ManyToOne
    private Panstwo osoPanSerial;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "rzrOsoSerial")
    private List<RozlZrodel> rozlZrodelList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ospOsoSerial")
    private List<OsobaPrz> osobaPrzList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "osrOsoSerial")
    private List<OsobaRod> osobaRodList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "ochOsoSerial")
    private List<OcenaHist> ocenaHistList;

    public Osoba() {
    }

    public Osoba(Integer osoSerial) {
        this.osoSerial = osoSerial;
    }

    public Osoba(Integer osoSerial, String osoNazwisko, String osoImie1, String osoImie2, String osoImieOjca, String osoImieMatki, String osoPesel, String osoUlica, String osoDom, String osoMieszkanie, String osoKod, String osoTel, String osoKonto, BigDecimal osoWynPotrInne1, BigDecimal osoWynPotrInne4, BigDecimal osoWynPotrInne2, BigDecimal osoWynPotrInne3, Character osoZasRodz, Character osoZasPiel, Character osoZasWych, Character osoPodDoch, Character osoOddzDra, BigDecimal osoUbezpWyp, BigDecimal osoPodStawka, BigDecimal osoPodWolna, String osoNrId, BigDecimal osoPodWolna12m, Character osoPlec, Character osoPodWolnaR, BigDecimal osoPodWolnaProc, Character osoUbezpWypC, Character osoUrlopowe, Character osoChorobowe) {
        this.osoSerial = osoSerial;
        this.osoNazwisko = osoNazwisko;
        this.osoImie1 = osoImie1;
        this.osoImie2 = osoImie2;
        this.osoImieOjca = osoImieOjca;
        this.osoImieMatki = osoImieMatki;
        this.osoPesel = osoPesel;
        this.osoUlica = osoUlica;
        this.osoDom = osoDom;
        this.osoMieszkanie = osoMieszkanie;
        this.osoKod = osoKod;
        this.osoTel = osoTel;
        this.osoKonto = osoKonto;
        this.osoWynPotrInne1 = osoWynPotrInne1;
        this.osoWynPotrInne4 = osoWynPotrInne4;
        this.osoWynPotrInne2 = osoWynPotrInne2;
        this.osoWynPotrInne3 = osoWynPotrInne3;
        this.osoZasRodz = osoZasRodz;
        this.osoZasPiel = osoZasPiel;
        this.osoZasWych = osoZasWych;
        this.osoPodDoch = osoPodDoch;
        this.osoOddzDra = osoOddzDra;
        this.osoUbezpWyp = osoUbezpWyp;
        this.osoPodStawka = osoPodStawka;
        this.osoPodWolna = osoPodWolna;
        this.osoNrId = osoNrId;
        this.osoPodWolna12m = osoPodWolna12m;
        this.osoPlec = osoPlec;
        this.osoPodWolnaR = osoPodWolnaR;
        this.osoPodWolnaProc = osoPodWolnaProc;
        this.osoUbezpWypC = osoUbezpWypC;
        this.osoUrlopowe = osoUrlopowe;
        this.osoChorobowe = osoChorobowe;
    }

    public Integer getOsoSerial() {
        return osoSerial;
    }

    public void setOsoSerial(Integer osoSerial) {
        this.osoSerial = osoSerial;
    }

    public String getOsoNazwisko() {
        return osoNazwisko;
    }

    public void setOsoNazwisko(String osoNazwisko) {
        this.osoNazwisko = osoNazwisko;
    }

    public String getOsoImie1() {
        return osoImie1;
    }

    public void setOsoImie1(String osoImie1) {
        this.osoImie1 = osoImie1;
    }

    public String getOsoImie2() {
        return osoImie2;
    }

    public void setOsoImie2(String osoImie2) {
        this.osoImie2 = osoImie2;
    }

    public String getOsoImieOjca() {
        return osoImieOjca;
    }

    public void setOsoImieOjca(String osoImieOjca) {
        this.osoImieOjca = osoImieOjca;
    }

    public String getOsoImieMatki() {
        return osoImieMatki;
    }

    public void setOsoImieMatki(String osoImieMatki) {
        this.osoImieMatki = osoImieMatki;
    }

    public Date getOsoUrodzData() {
        return osoUrodzData;
    }

    public void setOsoUrodzData(Date osoUrodzData) {
        this.osoUrodzData = osoUrodzData;
    }

    public String getOsoPesel() {
        return osoPesel;
    }

    public void setOsoPesel(String osoPesel) {
        this.osoPesel = osoPesel;
    }

    public String getOsoNip() {
        return osoNip;
    }

    public void setOsoNip(String osoNip) {
        this.osoNip = osoNip;
    }

    public String getOsoUlica() {
        return osoUlica;
    }

    public void setOsoUlica(String osoUlica) {
        this.osoUlica = osoUlica;
    }

    public String getOsoDom() {
        return osoDom;
    }

    public void setOsoDom(String osoDom) {
        this.osoDom = osoDom;
    }

    public String getOsoMieszkanie() {
        return osoMieszkanie;
    }

    public void setOsoMieszkanie(String osoMieszkanie) {
        this.osoMieszkanie = osoMieszkanie;
    }

    public String getOsoKod() {
        return osoKod;
    }

    public void setOsoKod(String osoKod) {
        this.osoKod = osoKod;
    }

    public String getOsoTel() {
        return osoTel;
    }

    public void setOsoTel(String osoTel) {
        this.osoTel = osoTel;
    }

    public Date getOsoDataZatr() {
        return osoDataZatr;
    }

    public void setOsoDataZatr(Date osoDataZatr) {
        this.osoDataZatr = osoDataZatr;
    }

    public Date getOsoDataZwol() {
        return osoDataZwol;
    }

    public void setOsoDataZwol(Date osoDataZwol) {
        this.osoDataZwol = osoDataZwol;
    }

    public String getOsoKonto() {
        return osoKonto;
    }

    public void setOsoKonto(String osoKonto) {
        this.osoKonto = osoKonto;
    }

    public Integer getOsoUrzSerial() {
        return osoUrzSerial;
    }

    public void setOsoUrzSerial(Integer osoUrzSerial) {
        this.osoUrzSerial = osoUrzSerial;
    }

    public Character getOsoZusDok() {
        return osoZusDok;
    }

    public void setOsoZusDok(Character osoZusDok) {
        this.osoZusDok = osoZusDok;
    }

    public String getOsoZusNumer() {
        return osoZusNumer;
    }

    public void setOsoZusNumer(String osoZusNumer) {
        this.osoZusNumer = osoZusNumer;
    }

    public BigDecimal getOsoWynZasadn() {
        return osoWynZasadn;
    }

    public void setOsoWynZasadn(BigDecimal osoWynZasadn) {
        this.osoWynZasadn = osoWynZasadn;
    }

    public Character getOsoTyp() {
        return osoTyp;
    }

    public void setOsoTyp(Character osoTyp) {
        this.osoTyp = osoTyp;
    }

    public String getOsoGmina() {
        return osoGmina;
    }

    public void setOsoGmina(String osoGmina) {
        this.osoGmina = osoGmina;
    }

    public BigDecimal getOsoWynKoszty() {
        return osoWynKoszty;
    }

    public void setOsoWynKoszty(BigDecimal osoWynKoszty) {
        this.osoWynKoszty = osoWynKoszty;
    }

    public BigDecimal getOsoWynPotrInne1() {
        return osoWynPotrInne1;
    }

    public void setOsoWynPotrInne1(BigDecimal osoWynPotrInne1) {
        this.osoWynPotrInne1 = osoWynPotrInne1;
    }

    public BigDecimal getOsoWynPotrInne4() {
        return osoWynPotrInne4;
    }

    public void setOsoWynPotrInne4(BigDecimal osoWynPotrInne4) {
        this.osoWynPotrInne4 = osoWynPotrInne4;
    }

    public BigDecimal getOsoWynPotrInne2() {
        return osoWynPotrInne2;
    }

    public void setOsoWynPotrInne2(BigDecimal osoWynPotrInne2) {
        this.osoWynPotrInne2 = osoWynPotrInne2;
    }

    public BigDecimal getOsoWynPotrInne3() {
        return osoWynPotrInne3;
    }

    public void setOsoWynPotrInne3(BigDecimal osoWynPotrInne3) {
        this.osoWynPotrInne3 = osoWynPotrInne3;
    }

    public Short getOsoEtat1() {
        return osoEtat1;
    }

    public void setOsoEtat1(Short osoEtat1) {
        this.osoEtat1 = osoEtat1;
    }

    public Short getOsoEtat2() {
        return osoEtat2;
    }

    public void setOsoEtat2(Short osoEtat2) {
        this.osoEtat2 = osoEtat2;
    }

    public String getOsoKodTytU12() {
        return osoKodTytU12;
    }

    public void setOsoKodTytU12(String osoKodTytU12) {
        this.osoKodTytU12 = osoKodTytU12;
    }

    public Character getOsoKodTytU3() {
        return osoKodTytU3;
    }

    public void setOsoKodTytU3(Character osoKodTytU3) {
        this.osoKodTytU3 = osoKodTytU3;
    }

    public Character getOsoKodTytU4() {
        return osoKodTytU4;
    }

    public void setOsoKodTytU4(Character osoKodTytU4) {
        this.osoKodTytU4 = osoKodTytU4;
    }

    public String getOsoFaks() {
        return osoFaks;
    }

    public void setOsoFaks(String osoFaks) {
        this.osoFaks = osoFaks;
    }

    public Character getOsoZasRodz() {
        return osoZasRodz;
    }

    public void setOsoZasRodz(Character osoZasRodz) {
        this.osoZasRodz = osoZasRodz;
    }

    public Short getOsoZasRodzIlosc() {
        return osoZasRodzIlosc;
    }

    public void setOsoZasRodzIlosc(Short osoZasRodzIlosc) {
        this.osoZasRodzIlosc = osoZasRodzIlosc;
    }

    public Character getOsoZasPiel() {
        return osoZasPiel;
    }

    public void setOsoZasPiel(Character osoZasPiel) {
        this.osoZasPiel = osoZasPiel;
    }

    public Short getOsoZasPielIlosc() {
        return osoZasPielIlosc;
    }

    public void setOsoZasPielIlosc(Short osoZasPielIlosc) {
        this.osoZasPielIlosc = osoZasPielIlosc;
    }

    public Character getOsoZasWych() {
        return osoZasWych;
    }

    public void setOsoZasWych(Character osoZasWych) {
        this.osoZasWych = osoZasWych;
    }

    public Character getOsoPodDoch() {
        return osoPodDoch;
    }

    public void setOsoPodDoch(Character osoPodDoch) {
        this.osoPodDoch = osoPodDoch;
    }

    public BigDecimal getOsoZasRodzKwota() {
        return osoZasRodzKwota;
    }

    public void setOsoZasRodzKwota(BigDecimal osoZasRodzKwota) {
        this.osoZasRodzKwota = osoZasRodzKwota;
    }

    public BigDecimal getOsoZasPielKwota() {
        return osoZasPielKwota;
    }

    public void setOsoZasPielKwota(BigDecimal osoZasPielKwota) {
        this.osoZasPielKwota = osoZasPielKwota;
    }

    public BigDecimal getOsoZasWychKwota() {
        return osoZasWychKwota;
    }

    public void setOsoZasWychKwota(BigDecimal osoZasWychKwota) {
        this.osoZasWychKwota = osoZasWychKwota;
    }

    public String getOsoPowiat() {
        return osoPowiat;
    }

    public void setOsoPowiat(String osoPowiat) {
        this.osoPowiat = osoPowiat;
    }

    public String getOsoPoczta() {
        return osoPoczta;
    }

    public void setOsoPoczta(String osoPoczta) {
        this.osoPoczta = osoPoczta;
    }

    public Short getOsoWynKosztyTyp() {
        return osoWynKosztyTyp;
    }

    public void setOsoWynKosztyTyp(Short osoWynKosztyTyp) {
        this.osoWynKosztyTyp = osoWynKosztyTyp;
    }

    public String getOsoWojewodztwo() {
        return osoWojewodztwo;
    }

    public void setOsoWojewodztwo(String osoWojewodztwo) {
        this.osoWojewodztwo = osoWojewodztwo;
    }

    public Short getOsoWynForma() {
        return osoWynForma;
    }

    public void setOsoWynForma(Short osoWynForma) {
        this.osoWynForma = osoWynForma;
    }

    public Character getOsoOddzDra() {
        return osoOddzDra;
    }

    public void setOsoOddzDra(Character osoOddzDra) {
        this.osoOddzDra = osoOddzDra;
    }

    public BigDecimal getOsoPdstZus() {
        return osoPdstZus;
    }

    public void setOsoPdstZus(BigDecimal osoPdstZus) {
        this.osoPdstZus = osoPdstZus;
    }

    public BigDecimal getOsoPdstChorWyp() {
        return osoPdstChorWyp;
    }

    public void setOsoPdstChorWyp(BigDecimal osoPdstChorWyp) {
        this.osoPdstChorWyp = osoPdstChorWyp;
    }

    public BigDecimal getOsoPdstZdrow() {
        return osoPdstZdrow;
    }

    public void setOsoPdstZdrow(BigDecimal osoPdstZdrow) {
        this.osoPdstZdrow = osoPdstZdrow;
    }

    public Character getOsoKodTerm() {
        return osoKodTerm;
    }

    public void setOsoKodTerm(Character osoKodTerm) {
        this.osoKodTerm = osoKodTerm;
    }

    public String getOsoRegon() {
        return osoRegon;
    }

    public void setOsoRegon(String osoRegon) {
        this.osoRegon = osoRegon;
    }

    public Character getOsoPrzekrRpw() {
        return osoPrzekrRpw;
    }

    public void setOsoPrzekrRpw(Character osoPrzekrRpw) {
        this.osoPrzekrRpw = osoPrzekrRpw;
    }

    public BigDecimal getOsoUbezpWyp() {
        return osoUbezpWyp;
    }

    public void setOsoUbezpWyp(BigDecimal osoUbezpWyp) {
        this.osoUbezpWyp = osoUbezpWyp;
    }

    public Integer getOsoStaSerial() {
        return osoStaSerial;
    }

    public void setOsoStaSerial(Integer osoStaSerial) {
        this.osoStaSerial = osoStaSerial;
    }

    public BigDecimal getOsoPodStawka() {
        return osoPodStawka;
    }

    public void setOsoPodStawka(BigDecimal osoPodStawka) {
        this.osoPodStawka = osoPodStawka;
    }

    public BigDecimal getOsoPodWolna() {
        return osoPodWolna;
    }

    public void setOsoPodWolna(BigDecimal osoPodWolna) {
        this.osoPodWolna = osoPodWolna;
    }

    public Character getOsoPit40() {
        return osoPit40;
    }

    public void setOsoPit40(Character osoPit40) {
        this.osoPit40 = osoPit40;
    }

    public String getOsoMiejsceUr() {
        return osoMiejsceUr;
    }

    public void setOsoMiejsceUr(String osoMiejsceUr) {
        this.osoMiejsceUr = osoMiejsceUr;
    }

    public String getOsoNrId() {
        return osoNrId;
    }

    public void setOsoNrId(String osoNrId) {
        this.osoNrId = osoNrId;
    }

    public BigDecimal getOsoPodWolna12m() {
        return osoPodWolna12m;
    }

    public void setOsoPodWolna12m(BigDecimal osoPodWolna12m) {
        this.osoPodWolna12m = osoPodWolna12m;
    }

    public Integer getOsoPanSerial2() {
        return osoPanSerial2;
    }

    public void setOsoPanSerial2(Integer osoPanSerial2) {
        this.osoPanSerial2 = osoPanSerial2;
    }

    public String getOsoUlica2() {
        return osoUlica2;
    }

    public void setOsoUlica2(String osoUlica2) {
        this.osoUlica2 = osoUlica2;
    }

    public String getOsoDom2() {
        return osoDom2;
    }

    public void setOsoDom2(String osoDom2) {
        this.osoDom2 = osoDom2;
    }

    public String getOsoMieszkanie2() {
        return osoMieszkanie2;
    }

    public void setOsoMieszkanie2(String osoMieszkanie2) {
        this.osoMieszkanie2 = osoMieszkanie2;
    }

    public String getOsoKod2() {
        return osoKod2;
    }

    public void setOsoKod2(String osoKod2) {
        this.osoKod2 = osoKod2;
    }

    public String getOsoGmina2() {
        return osoGmina2;
    }

    public void setOsoGmina2(String osoGmina2) {
        this.osoGmina2 = osoGmina2;
    }

    public String getOsoPowiat2() {
        return osoPowiat2;
    }

    public void setOsoPowiat2(String osoPowiat2) {
        this.osoPowiat2 = osoPowiat2;
    }

    public String getOsoPoczta2() {
        return osoPoczta2;
    }

    public void setOsoPoczta2(String osoPoczta2) {
        this.osoPoczta2 = osoPoczta2;
    }

    public String getOsoWojewodztwo2() {
        return osoWojewodztwo2;
    }

    public void setOsoWojewodztwo2(String osoWojewodztwo2) {
        this.osoWojewodztwo2 = osoWojewodztwo2;
    }

    public Character getOsoPlec() {
        return osoPlec;
    }

    public void setOsoPlec(Character osoPlec) {
        this.osoPlec = osoPlec;
    }

    public Character getOsoEtatDoDra() {
        return osoEtatDoDra;
    }

    public void setOsoEtatDoDra(Character osoEtatDoDra) {
        this.osoEtatDoDra = osoEtatDoDra;
    }

    public Character getOsoOgrPdstChor() {
        return osoOgrPdstChor;
    }

    public void setOsoOgrPdstChor(Character osoOgrPdstChor) {
        this.osoOgrPdstChor = osoOgrPdstChor;
    }

    public Character getOsoPodWolnaR() {
        return osoPodWolnaR;
    }

    public void setOsoPodWolnaR(Character osoPodWolnaR) {
        this.osoPodWolnaR = osoPodWolnaR;
    }

    public BigDecimal getOsoPodWolnaProc() {
        return osoPodWolnaProc;
    }

    public void setOsoPodWolnaProc(BigDecimal osoPodWolnaProc) {
        this.osoPodWolnaProc = osoPodWolnaProc;
    }

    public Character getOsoWynKosztyR() {
        return osoWynKosztyR;
    }

    public void setOsoWynKosztyR(Character osoWynKosztyR) {
        this.osoWynKosztyR = osoWynKosztyR;
    }

    public BigDecimal getOsoWynKosztyProc() {
        return osoWynKosztyProc;
    }

    public void setOsoWynKosztyProc(BigDecimal osoWynKosztyProc) {
        this.osoWynKosztyProc = osoWynKosztyProc;
    }

    public String getOsoNazwiskoRod() {
        return osoNazwiskoRod;
    }

    public void setOsoNazwiskoRod(String osoNazwiskoRod) {
        this.osoNazwiskoRod = osoNazwiskoRod;
    }

    public String getOsoObywatelstwo() {
        return osoObywatelstwo;
    }

    public void setOsoObywatelstwo(String osoObywatelstwo) {
        this.osoObywatelstwo = osoObywatelstwo;
    }

    public Character getOsoKSP() {
        return osoKSP;
    }

    public void setOsoKSP(Character osoKSP) {
        this.osoKSP = osoKSP;
    }

    public Character getOsoKCP() {
        return osoKCP;
    }

    public void setOsoKCP(Character osoKCP) {
        this.osoKCP = osoKCP;
    }

    public Date getOsoOkresNiepOd() {
        return osoOkresNiepOd;
    }

    public void setOsoOkresNiepOd(Date osoOkresNiepOd) {
        this.osoOkresNiepOd = osoOkresNiepOd;
    }

    public String getOsoKsp() {
        return osoKsp;
    }

    public void setOsoKsp(String osoKsp) {
        this.osoKsp = osoKsp;
    }

    public Character getOsoWspGosp() {
        return osoWspGosp;
    }

    public void setOsoWspGosp(Character osoWspGosp) {
        this.osoWspGosp = osoWspGosp;
    }

    public String getOsoKodNiezd() {
        return osoKodNiezd;
    }

    public void setOsoKodNiezd(String osoKodNiezd) {
        this.osoKodNiezd = osoKodNiezd;
    }

    public Date getOsoOkresNiezdOd() {
        return osoOkresNiezdOd;
    }

    public void setOsoOkresNiezdOd(Date osoOkresNiezdOd) {
        this.osoOkresNiezdOd = osoOkresNiezdOd;
    }

    public String getOsoKodZawodu() {
        return osoKodZawodu;
    }

    public void setOsoKodZawodu(String osoKodZawodu) {
        this.osoKodZawodu = osoKodZawodu;
    }

    public String getOsoKodGorn() {
        return osoKodGorn;
    }

    public void setOsoKodGorn(String osoKodGorn) {
        this.osoKodGorn = osoKodGorn;
    }

    public Date getOsoOkrGornOd() {
        return osoOkrGornOd;
    }

    public void setOsoOkrGornOd(Date osoOkrGornOd) {
        this.osoOkrGornOd = osoOkrGornOd;
    }

    public String getOsoWykszt() {
        return osoWykszt;
    }

    public void setOsoWykszt(String osoWykszt) {
        this.osoWykszt = osoWykszt;
    }

    public String getOsoKodSzczeg() {
        return osoKodSzczeg;
    }

    public void setOsoKodSzczeg(String osoKodSzczeg) {
        this.osoKodSzczeg = osoKodSzczeg;
    }

    public Date getOsoOkresSzczOd() {
        return osoOkresSzczOd;
    }

    public void setOsoOkresSzczOd(Date osoOkresSzczOd) {
        this.osoOkresSzczOd = osoOkresSzczOd;
    }

    public String getOsoKasaKod() {
        return osoKasaKod;
    }

    public void setOsoKasaKod(String osoKasaKod) {
        this.osoKasaKod = osoKasaKod;
    }

    public String getOsoKasaNazwa() {
        return osoKasaNazwa;
    }

    public void setOsoKasaNazwa(String osoKasaNazwa) {
        this.osoKasaNazwa = osoKasaNazwa;
    }

    public Date getOsoKasaData() {
        return osoKasaData;
    }

    public void setOsoKasaData(Date osoKasaData) {
        this.osoKasaData = osoKasaData;
    }

    public Date getOsoOkresSzczDo() {
        return osoOkresSzczDo;
    }

    public void setOsoOkresSzczDo(Date osoOkresSzczDo) {
        this.osoOkresSzczDo = osoOkresSzczDo;
    }

    public Date getOsoOkrGornDo() {
        return osoOkrGornDo;
    }

    public void setOsoOkrGornDo(Date osoOkrGornDo) {
        this.osoOkrGornDo = osoOkrGornDo;
    }

    public Date getOsoOkresNiezdDo() {
        return osoOkresNiezdDo;
    }

    public void setOsoOkresNiezdDo(Date osoOkresNiezdDo) {
        this.osoOkresNiezdDo = osoOkresNiezdDo;
    }

    public Date getOsoOkresNiepDo() {
        return osoOkresNiepDo;
    }

    public void setOsoOkresNiepDo(Date osoOkresNiepDo) {
        this.osoOkresNiepDo = osoOkresNiepDo;
    }

    public Integer getOsoPanSerialK() {
        return osoPanSerialK;
    }

    public void setOsoPanSerialK(Integer osoPanSerialK) {
        this.osoPanSerialK = osoPanSerialK;
    }

    public Integer getOsoMiaSerialK() {
        return osoMiaSerialK;
    }

    public void setOsoMiaSerialK(Integer osoMiaSerialK) {
        this.osoMiaSerialK = osoMiaSerialK;
    }

    public String getOsoUlicaK() {
        return osoUlicaK;
    }

    public void setOsoUlicaK(String osoUlicaK) {
        this.osoUlicaK = osoUlicaK;
    }

    public String getOsoDomK() {
        return osoDomK;
    }

    public void setOsoDomK(String osoDomK) {
        this.osoDomK = osoDomK;
    }

    public String getOsoMieszkanieK() {
        return osoMieszkanieK;
    }

    public void setOsoMieszkanieK(String osoMieszkanieK) {
        this.osoMieszkanieK = osoMieszkanieK;
    }

    public String getOsoKodK() {
        return osoKodK;
    }

    public void setOsoKodK(String osoKodK) {
        this.osoKodK = osoKodK;
    }

    public String getOsoGminaK() {
        return osoGminaK;
    }

    public void setOsoGminaK(String osoGminaK) {
        this.osoGminaK = osoGminaK;
    }

    public String getOsoPowiatK() {
        return osoPowiatK;
    }

    public void setOsoPowiatK(String osoPowiatK) {
        this.osoPowiatK = osoPowiatK;
    }

    public String getOsoPocztaK() {
        return osoPocztaK;
    }

    public void setOsoPocztaK(String osoPocztaK) {
        this.osoPocztaK = osoPocztaK;
    }

    public String getOsoWojewodztwoK() {
        return osoWojewodztwoK;
    }

    public void setOsoWojewodztwoK(String osoWojewodztwoK) {
        this.osoWojewodztwoK = osoWojewodztwoK;
    }

    public String getOsoTelK() {
        return osoTelK;
    }

    public void setOsoTelK(String osoTelK) {
        this.osoTelK = osoTelK;
    }

    public String getOsoFaksK() {
        return osoFaksK;
    }

    public void setOsoFaksK(String osoFaksK) {
        this.osoFaksK = osoFaksK;
    }

    public String getOsoTel2() {
        return osoTel2;
    }

    public void setOsoTel2(String osoTel2) {
        this.osoTel2 = osoTel2;
    }

    public String getOsoFaks2() {
        return osoFaks2;
    }

    public void setOsoFaks2(String osoFaks2) {
        this.osoFaks2 = osoFaks2;
    }

    public String getOsoEmail() {
        return osoEmail;
    }

    public void setOsoEmail(String osoEmail) {
        this.osoEmail = osoEmail;
    }

    public Character getOsoDod1() {
        return osoDod1;
    }

    public void setOsoDod1(Character osoDod1) {
        this.osoDod1 = osoDod1;
    }

    public Character getOsoDod2() {
        return osoDod2;
    }

    public void setOsoDod2(Character osoDod2) {
        this.osoDod2 = osoDod2;
    }

    public Character getOsoDod3() {
        return osoDod3;
    }

    public void setOsoDod3(Character osoDod3) {
        this.osoDod3 = osoDod3;
    }

    public String getOsoSkrytkaK() {
        return osoSkrytkaK;
    }

    public void setOsoSkrytkaK(String osoSkrytkaK) {
        this.osoSkrytkaK = osoSkrytkaK;
    }

    public Character getOsoAdr2Tsam() {
        return osoAdr2Tsam;
    }

    public void setOsoAdr2Tsam(Character osoAdr2Tsam) {
        this.osoAdr2Tsam = osoAdr2Tsam;
    }

    public Character getOsoAdrKTsam() {
        return osoAdrKTsam;
    }

    public void setOsoAdrKTsam(Character osoAdrKTsam) {
        this.osoAdrKTsam = osoAdrKTsam;
    }

    public Character getOsoZasRodzM() {
        return osoZasRodzM;
    }

    public void setOsoZasRodzM(Character osoZasRodzM) {
        this.osoZasRodzM = osoZasRodzM;
    }

    public Character getOsoZasWychS() {
        return osoZasWychS;
    }

    public void setOsoZasWychS(Character osoZasWychS) {
        this.osoZasWychS = osoZasWychS;
    }

    public Character getOsoDod4() {
        return osoDod4;
    }

    public void setOsoDod4(Character osoDod4) {
        this.osoDod4 = osoDod4;
    }

    public Character getOsoDod5() {
        return osoDod5;
    }

    public void setOsoDod5(Character osoDod5) {
        this.osoDod5 = osoDod5;
    }

    public Character getOsoDod6() {
        return osoDod6;
    }

    public void setOsoDod6(Character osoDod6) {
        this.osoDod6 = osoDod6;
    }

    public Character getOsoDod7() {
        return osoDod7;
    }

    public void setOsoDod7(Character osoDod7) {
        this.osoDod7 = osoDod7;
    }

    public Character getOsoDod8() {
        return osoDod8;
    }

    public void setOsoDod8(Character osoDod8) {
        this.osoDod8 = osoDod8;
    }

    public Character getOsoDod9() {
        return osoDod9;
    }

    public void setOsoDod9(Character osoDod9) {
        this.osoDod9 = osoDod9;
    }

    public Character getOsoDod10() {
        return osoDod10;
    }

    public void setOsoDod10(Character osoDod10) {
        this.osoDod10 = osoDod10;
    }

    public Character getOsoZasRodzR() {
        return osoZasRodzR;
    }

    public void setOsoZasRodzR(Character osoZasRodzR) {
        this.osoZasRodzR = osoZasRodzR;
    }

    public Character getOsoZasPielR() {
        return osoZasPielR;
    }

    public void setOsoZasPielR(Character osoZasPielR) {
        this.osoZasPielR = osoZasPielR;
    }

    public Character getOsoZasWychR() {
        return osoZasWychR;
    }

    public void setOsoZasWychR(Character osoZasWychR) {
        this.osoZasWychR = osoZasWychR;
    }

    public Character getOsoPodWolnaLp() {
        return osoPodWolnaLp;
    }

    public void setOsoPodWolnaLp(Character osoPodWolnaLp) {
        this.osoPodWolnaLp = osoPodWolnaLp;
    }

    public Character getOsoZasWychP3() {
        return osoZasWychP3;
    }

    public void setOsoZasWychP3(Character osoZasWychP3) {
        this.osoZasWychP3 = osoZasWychP3;
    }

    public Character getOsoPit4() {
        return osoPit4;
    }

    public void setOsoPit4(Character osoPit4) {
        this.osoPit4 = osoPit4;
    }

    public Character getOsoPdstZusR() {
        return osoPdstZusR;
    }

    public void setOsoPdstZusR(Character osoPdstZusR) {
        this.osoPdstZusR = osoPdstZusR;
    }

    public Character getOsoPdstChorWypR() {
        return osoPdstChorWypR;
    }

    public void setOsoPdstChorWypR(Character osoPdstChorWypR) {
        this.osoPdstChorWypR = osoPdstChorWypR;
    }

    public Character getOsoPdstZdrowR() {
        return osoPdstZdrowR;
    }

    public void setOsoPdstZdrowR(Character osoPdstZdrowR) {
        this.osoPdstZdrowR = osoPdstZdrowR;
    }

    public BigDecimal getOsoPdstZusP() {
        return osoPdstZusP;
    }

    public void setOsoPdstZusP(BigDecimal osoPdstZusP) {
        this.osoPdstZusP = osoPdstZusP;
    }

    public BigDecimal getOsoPdstChorWypP() {
        return osoPdstChorWypP;
    }

    public void setOsoPdstChorWypP(BigDecimal osoPdstChorWypP) {
        this.osoPdstChorWypP = osoPdstChorWypP;
    }

    public BigDecimal getOsoPdstZdrowP() {
        return osoPdstZdrowP;
    }

    public void setOsoPdstZdrowP(BigDecimal osoPdstZdrowP) {
        this.osoPdstZdrowP = osoPdstZdrowP;
    }

    public Character getOsoUbezpWypC() {
        return osoUbezpWypC;
    }

    public void setOsoUbezpWypC(Character osoUbezpWypC) {
        this.osoUbezpWypC = osoUbezpWypC;
    }

    public Integer getOsoOsoSerial() {
        return osoOsoSerial;
    }

    public void setOsoOsoSerial(Integer osoOsoSerial) {
        this.osoOsoSerial = osoOsoSerial;
    }

    public Date getOsoDodData1() {
        return osoDodData1;
    }

    public void setOsoDodData1(Date osoDodData1) {
        this.osoDodData1 = osoDodData1;
    }

    public Date getOsoDodData2() {
        return osoDodData2;
    }

    public void setOsoDodData2(Date osoDodData2) {
        this.osoDodData2 = osoDodData2;
    }

    public Date getOsoDodData3() {
        return osoDodData3;
    }

    public void setOsoDodData3(Date osoDodData3) {
        this.osoDodData3 = osoDodData3;
    }

    public Date getOsoDodData4() {
        return osoDodData4;
    }

    public void setOsoDodData4(Date osoDodData4) {
        this.osoDodData4 = osoDodData4;
    }

    public BigDecimal getOsoDodNum1() {
        return osoDodNum1;
    }

    public void setOsoDodNum1(BigDecimal osoDodNum1) {
        this.osoDodNum1 = osoDodNum1;
    }

    public BigDecimal getOsoDodNum2() {
        return osoDodNum2;
    }

    public void setOsoDodNum2(BigDecimal osoDodNum2) {
        this.osoDodNum2 = osoDodNum2;
    }

    public BigDecimal getOsoDodNum3() {
        return osoDodNum3;
    }

    public void setOsoDodNum3(BigDecimal osoDodNum3) {
        this.osoDodNum3 = osoDodNum3;
    }

    public BigDecimal getOsoDodNum4() {
        return osoDodNum4;
    }

    public void setOsoDodNum4(BigDecimal osoDodNum4) {
        this.osoDodNum4 = osoDodNum4;
    }

    public Date getOsoDodData5() {
        return osoDodData5;
    }

    public void setOsoDodData5(Date osoDodData5) {
        this.osoDodData5 = osoDodData5;
    }

    public Date getOsoDodData6() {
        return osoDodData6;
    }

    public void setOsoDodData6(Date osoDodData6) {
        this.osoDodData6 = osoDodData6;
    }

    public String getOsoDodVchar1() {
        return osoDodVchar1;
    }

    public void setOsoDodVchar1(String osoDodVchar1) {
        this.osoDodVchar1 = osoDodVchar1;
    }

    public String getOsoDodVchar2() {
        return osoDodVchar2;
    }

    public void setOsoDodVchar2(String osoDodVchar2) {
        this.osoDodVchar2 = osoDodVchar2;
    }

    public String getOsoDodVchar3() {
        return osoDodVchar3;
    }

    public void setOsoDodVchar3(String osoDodVchar3) {
        this.osoDodVchar3 = osoDodVchar3;
    }

    public String getOsoDodVchar4() {
        return osoDodVchar4;
    }

    public void setOsoDodVchar4(String osoDodVchar4) {
        this.osoDodVchar4 = osoDodVchar4;
    }

    public Character getOsoDod11() {
        return osoDod11;
    }

    public void setOsoDod11(Character osoDod11) {
        this.osoDod11 = osoDod11;
    }

    public Character getOsoDod12() {
        return osoDod12;
    }

    public void setOsoDod12(Character osoDod12) {
        this.osoDod12 = osoDod12;
    }

    public Character getOsoDod13() {
        return osoDod13;
    }

    public void setOsoDod13(Character osoDod13) {
        this.osoDod13 = osoDod13;
    }

    public Character getOsoDod14() {
        return osoDod14;
    }

    public void setOsoDod14(Character osoDod14) {
        this.osoDod14 = osoDod14;
    }

    public Character getOsoDod15() {
        return osoDod15;
    }

    public void setOsoDod15(Character osoDod15) {
        this.osoDod15 = osoDod15;
    }

    public BigDecimal getOsoDodNum5() {
        return osoDodNum5;
    }

    public void setOsoDodNum5(BigDecimal osoDodNum5) {
        this.osoDodNum5 = osoDodNum5;
    }

    public BigDecimal getOsoDodNum6() {
        return osoDodNum6;
    }

    public void setOsoDodNum6(BigDecimal osoDodNum6) {
        this.osoDodNum6 = osoDodNum6;
    }

    public BigDecimal getOsoDodNum7() {
        return osoDodNum7;
    }

    public void setOsoDodNum7(BigDecimal osoDodNum7) {
        this.osoDodNum7 = osoDodNum7;
    }

    public BigDecimal getOsoDodNum8() {
        return osoDodNum8;
    }

    public void setOsoDodNum8(BigDecimal osoDodNum8) {
        this.osoDodNum8 = osoDodNum8;
    }

    public Date getOsoDodData7() {
        return osoDodData7;
    }

    public void setOsoDodData7(Date osoDodData7) {
        this.osoDodData7 = osoDodData7;
    }

    public Date getOsoDodData8() {
        return osoDodData8;
    }

    public void setOsoDodData8(Date osoDodData8) {
        this.osoDodData8 = osoDodData8;
    }

    public Character getOsoKalendarz() {
        return osoKalendarz;
    }

    public void setOsoKalendarz(Character osoKalendarz) {
        this.osoKalendarz = osoKalendarz;
    }

    public Integer getOsoDodInt1() {
        return osoDodInt1;
    }

    public void setOsoDodInt1(Integer osoDodInt1) {
        this.osoDodInt1 = osoDodInt1;
    }

    public Integer getOsoDodInt2() {
        return osoDodInt2;
    }

    public void setOsoDodInt2(Integer osoDodInt2) {
        this.osoDodInt2 = osoDodInt2;
    }

    public Integer getOsoDodInt3() {
        return osoDodInt3;
    }

    public void setOsoDodInt3(Integer osoDodInt3) {
        this.osoDodInt3 = osoDodInt3;
    }

    public Integer getOsoDodInt4() {
        return osoDodInt4;
    }

    public void setOsoDodInt4(Integer osoDodInt4) {
        this.osoDodInt4 = osoDodInt4;
    }

    public Integer getOsoKchSerial() {
        return osoKchSerial;
    }

    public void setOsoKchSerial(Integer osoKchSerial) {
        this.osoKchSerial = osoKchSerial;
    }

    public Date getOsoDodData9() {
        return osoDodData9;
    }

    public void setOsoDodData9(Date osoDodData9) {
        this.osoDodData9 = osoDodData9;
    }

    public Date getOsoDodData10() {
        return osoDodData10;
    }

    public void setOsoDodData10(Date osoDodData10) {
        this.osoDodData10 = osoDodData10;
    }

    public Date getOsoDodData11() {
        return osoDodData11;
    }

    public void setOsoDodData11(Date osoDodData11) {
        this.osoDodData11 = osoDodData11;
    }

    public Date getOsoDodData12() {
        return osoDodData12;
    }

    public void setOsoDodData12(Date osoDodData12) {
        this.osoDodData12 = osoDodData12;
    }

    public Integer getOsoWktSerial() {
        return osoWktSerial;
    }

    public void setOsoWktSerial(Integer osoWktSerial) {
        this.osoWktSerial = osoWktSerial;
    }

    public Integer getOsoDodInt5() {
        return osoDodInt5;
    }

    public void setOsoDodInt5(Integer osoDodInt5) {
        this.osoDodInt5 = osoDodInt5;
    }

    public Integer getOsoDodInt6() {
        return osoDodInt6;
    }

    public void setOsoDodInt6(Integer osoDodInt6) {
        this.osoDodInt6 = osoDodInt6;
    }

    public Character getOsoUrlopowe() {
        return osoUrlopowe;
    }

    public void setOsoUrlopowe(Character osoUrlopowe) {
        this.osoUrlopowe = osoUrlopowe;
    }

    public Character getOsoChorobowe() {
        return osoChorobowe;
    }

    public void setOsoChorobowe(Character osoChorobowe) {
        this.osoChorobowe = osoChorobowe;
    }

    public String getOsoNusp() {
        return osoNusp;
    }

    public void setOsoNusp(String osoNusp) {
        this.osoNusp = osoNusp;
    }

    @XmlTransient
    public List<DaneLiDa> getDaneLiDaList() {
        return daneLiDaList;
    }

    public void setDaneLiDaList(List<DaneLiDa> daneLiDaList) {
        this.daneLiDaList = daneLiDaList;
    }

    @XmlTransient
    public List<OsobaPit> getOsobaPitList() {
        return osobaPitList;
    }

    public void setOsobaPitList(List<OsobaPit> osobaPitList) {
        this.osobaPitList = osobaPitList;
    }

    @XmlTransient
    public List<WymiarHist> getWymiarHistList() {
        return wymiarHistList;
    }

    public void setWymiarHistList(List<WymiarHist> wymiarHistList) {
        this.wymiarHistList = wymiarHistList;
    }

    @XmlTransient
    public List<PlaceSkl> getPlaceSklList() {
        return placeSklList;
    }

    public void setPlaceSklList(List<PlaceSkl> placeSklList) {
        this.placeSklList = placeSklList;
    }

    @XmlTransient
    public List<ZatrudHist> getZatrudHistList() {
        return zatrudHistList;
    }

    public void setZatrudHistList(List<ZatrudHist> zatrudHistList) {
        this.zatrudHistList = zatrudHistList;
    }

    @XmlTransient
    public List<OsobaPot> getOsobaPotList() {
        return osobaPotList;
    }

    public void setOsobaPotList(List<OsobaPot> osobaPotList) {
        this.osobaPotList = osobaPotList;
    }

    @XmlTransient
    public List<DaneHiDa> getDaneHiDaList() {
        return daneHiDaList;
    }

    public void setDaneHiDaList(List<DaneHiDa> daneHiDaList) {
        this.daneHiDaList = daneHiDaList;
    }

    @XmlTransient
    public List<PlacePrzZus> getPlacePrzZusList() {
        return placePrzZusList;
    }

    public void setPlacePrzZusList(List<PlacePrzZus> placePrzZusList) {
        this.placePrzZusList = placePrzZusList;
    }

    @XmlTransient
    public List<StanHist> getStanHistList() {
        return stanHistList;
    }

    public void setStanHistList(List<StanHist> stanHistList) {
        this.stanHistList = stanHistList;
    }

    @XmlTransient
    public List<PlacePrz> getPlacePrzList() {
        return placePrzList;
    }

    public void setPlacePrzList(List<PlacePrz> placePrzList) {
        this.placePrzList = placePrzList;
    }

    @XmlTransient
    public List<DzialHist> getDzialHistList() {
        return dzialHistList;
    }

    public void setDzialHistList(List<DzialHist> dzialHistList) {
        this.dzialHistList = dzialHistList;
    }

    @XmlTransient
    public List<Place> getPlaceList() {
        return placeList;
    }

    public void setPlaceList(List<Place> placeList) {
        this.placeList = placeList;
    }

    @XmlTransient
    public List<EtataHist> getEtataHistList() {
        return etataHistList;
    }

    public void setEtataHistList(List<EtataHist> etataHistList) {
        this.etataHistList = etataHistList;
    }

    @XmlTransient
    public List<Rozliczus> getRozliczusList() {
        return rozliczusList;
    }

    public void setRozliczusList(List<Rozliczus> rozliczusList) {
        this.rozliczusList = rozliczusList;
    }

    @XmlTransient
    public List<DaneStat> getDaneStatList() {
        return daneStatList;
    }

    public void setDaneStatList(List<DaneStat> daneStatList) {
        this.daneStatList = daneStatList;
    }

    @XmlTransient
    public List<Pojazdy> getPojazdyList() {
        return pojazdyList;
    }

    public void setPojazdyList(List<Pojazdy> pojazdyList) {
        this.pojazdyList = pojazdyList;
    }

    @XmlTransient
    public List<KursList> getKursListList() {
        return kursListList;
    }

    public void setKursListList(List<KursList> kursListList) {
        this.kursListList = kursListList;
    }

    @XmlTransient
    public List<Konto> getKontoList() {
        return kontoList;
    }

    public void setKontoList(List<Konto> kontoList) {
        this.kontoList = kontoList;
    }

    @XmlTransient
    public List<PlaceZlec> getPlaceZlecList() {
        return placeZlecList;
    }

    public void setPlaceZlecList(List<PlaceZlec> placeZlecList) {
        this.placeZlecList = placeZlecList;
    }

    @XmlTransient
    public List<JezykList> getJezykListList() {
        return jezykListList;
    }

    public void setJezykListList(List<JezykList> jezykListList) {
        this.jezykListList = jezykListList;
    }

    @XmlTransient
    public List<Kalendarz> getKalendarzList() {
        return kalendarzList;
    }

    public void setKalendarzList(List<Kalendarz> kalendarzList) {
        this.kalendarzList = kalendarzList;
    }

    @XmlTransient
    public List<RozlOdli> getRozlOdliList() {
        return rozlOdliList;
    }

    public void setRozlOdliList(List<RozlOdli> rozlOdliList) {
        this.rozlOdliList = rozlOdliList;
    }

    @XmlTransient
    public List<OsobaZlec> getOsobaZlecList() {
        return osobaZlecList;
    }

    public void setOsobaZlecList(List<OsobaZlec> osobaZlecList) {
        this.osobaZlecList = osobaZlecList;
    }

    @XmlTransient
    public List<OsobaSkl> getOsobaSklList() {
        return osobaSklList;
    }

    public void setOsobaSklList(List<OsobaSkl> osobaSklList) {
        this.osobaSklList = osobaSklList;
    }

    @XmlTransient
    public List<OsobaDet> getOsobaDetList() {
        return osobaDetList;
    }

    public void setOsobaDetList(List<OsobaDet> osobaDetList) {
        this.osobaDetList = osobaDetList;
    }

    @XmlTransient
    public List<EtatbHist> getEtatbHistList() {
        return etatbHistList;
    }

    public void setEtatbHistList(List<EtatbHist> etatbHistList) {
        this.etatbHistList = etatbHistList;
    }

    @XmlTransient
    public List<PlacePot> getPlacePotList() {
        return placePotList;
    }

    public void setPlacePotList(List<PlacePot> placePotList) {
        this.placePotList = placePotList;
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
    public List<AdresHist> getAdresHistList() {
        return adresHistList;
    }

    public void setAdresHistList(List<AdresHist> adresHistList) {
        this.adresHistList = adresHistList;
    }

    public Bank getOsoBanSerial() {
        return osoBanSerial;
    }

    public void setOsoBanSerial(Bank osoBanSerial) {
        this.osoBanSerial = osoBanSerial;
    }

    public Dep getOsoDepSerial() {
        return osoDepSerial;
    }

    public void setOsoDepSerial(Dep osoDepSerial) {
        this.osoDepSerial = osoDepSerial;
    }

    public Firma getOsoFirSerial() {
        return osoFirSerial;
    }

    public void setOsoFirSerial(Firma osoFirSerial) {
        this.osoFirSerial = osoFirSerial;
    }

    public Miasto getOsoMiaSerial2() {
        return osoMiaSerial2;
    }

    public void setOsoMiaSerial2(Miasto osoMiaSerial2) {
        this.osoMiaSerial2 = osoMiaSerial2;
    }

    public Miasto getOsoMiaSerial() {
        return osoMiaSerial;
    }

    public void setOsoMiaSerial(Miasto osoMiaSerial) {
        this.osoMiaSerial = osoMiaSerial;
    }

    public Panstwo getOsoPanSerial() {
        return osoPanSerial;
    }

    public void setOsoPanSerial(Panstwo osoPanSerial) {
        this.osoPanSerial = osoPanSerial;
    }

    @XmlTransient
    public List<RozlZrodel> getRozlZrodelList() {
        return rozlZrodelList;
    }

    public void setRozlZrodelList(List<RozlZrodel> rozlZrodelList) {
        this.rozlZrodelList = rozlZrodelList;
    }

    @XmlTransient
    public List<OsobaPrz> getOsobaPrzList() {
        return osobaPrzList;
    }

    public void setOsobaPrzList(List<OsobaPrz> osobaPrzList) {
        this.osobaPrzList = osobaPrzList;
    }

    @XmlTransient
    public List<OsobaRod> getOsobaRodList() {
        return osobaRodList;
    }

    public void setOsobaRodList(List<OsobaRod> osobaRodList) {
        this.osobaRodList = osobaRodList;
    }

    @XmlTransient
    public List<OcenaHist> getOcenaHistList() {
        return ocenaHistList;
    }

    public void setOcenaHistList(List<OcenaHist> ocenaHistList) {
        this.ocenaHistList = ocenaHistList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (osoSerial != null ? osoSerial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Osoba)) {
            return false;
        }
        Osoba other = (Osoba) object;
        if ((this.osoSerial == null && other.osoSerial != null) || (this.osoSerial != null && !this.osoSerial.equals(other.osoSerial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "kadryiplace.Osoba[ osoSerial=" + osoSerial + " ]";
    }
    
}
