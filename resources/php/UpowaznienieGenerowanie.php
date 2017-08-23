<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Description of Zerowanieciastek
 *
 * @author Osito
 */
class UpowaznienieGenerowanie {

    public final static function sprawdzupo() {
        if (session_status() != 2) {
            session_start();
        };
        try {
            require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
            R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
        } catch (exception $e) {};
        $id = $_SESSION['uczestnik']['id'];
        date_default_timezone_set('Europe/Warsaw');
        $niewyslano = (int)R::getCell("SELECT  `wyslaneup` FROM `uczestnicy` WHERE  `uczestnicy`.`id` = '$id';");
        $email = $_SESSION['uczestnik']['email'];
        if ($niewyslano == 0 && filter_var($email, FILTER_VALIDATE_EMAIL)) {
            try {
                $grupy = self::pobierzgrupy($id);
                if (strlen($grupy) > 0) {
                    return "true";
                } else {
                    return "false";
                }
            } catch (Exception $em) {}
        }
    }
    
    
    public final static function generuj() {
        if (session_status() != 2) {
            session_start();
        };
        try {
            require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
            R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
        } catch (exception $e) {};
        $id = $_SESSION['uczestnik']['id'];
        date_default_timezone_set('Europe/Warsaw');
        $niewyslano = (int)R::getCell("SELECT  `wyslaneup` FROM `uczestnicy` WHERE  `uczestnicy`.`id` = '$id';");
        $email = $_SESSION['uczestnik']['email'];
        if ($niewyslano == 0 && filter_var($email, FILTER_VALIDATE_EMAIL)) {
            try {
                require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Mail.php'); 
            } catch (Exception $em) {}
            try {
                $grupy = self::pobierzgrupy($id);
                if (strlen($grupy) > 0) {
                    $uczestnik = $_SESSION['uczestnik'];
                    $szkolenie = $_SESSION['uczestnik']['nazwaszkolenia'];
                    $plec = $_SESSION['uczestnik']['plec'];
                    $imienaz = $_SESSION['uczestnik']['imienazwisko'];
                    $kontakt = $_SESSION['uczestnik']['kontakt'];
                    $bcc = UpowaznienieGenerowanie::pobierzBCC();
                    $nrupowaznienia = $_SESSION['uczestnik']['nrupowaznienia'];
                    $datanadania = R::getCell("SELECT  `datanadania` FROM `uczestnicy` WHERE  `uczestnicy`.`id` = '$id';");
                    $dataustania = $_SESSION['uczestnik']['dataustania'];
                    if ($dataustania == null || $dataustania == "") {
                        $sqlfirma = $_SESSION['uczestnik']['firma'];
                        $nrupowaznienia = $_SESSION['uczestnik']['nrupowaznienia'];
                        $sql = "SELECT `miejscowosc` FROM `zakladpracy` WHERE `zakladpracy`.`nazwazakladu`='$sqlfirma';";
                        $miejscowosc = R::getCell($sql);
                        $sql = "SELECT `ulica` FROM `zakladpracy` WHERE `zakladpracy`.`nazwazakladu`='$sqlfirma';";
                        $ulica = R::getCell($sql);
                        if ($bcc == "") {
                            $bcc = "mchmielewska@interia.pl";
                        }
                        require_once("resources/MPDF57/mpdf.php");
                        if ($plec == "k") {
                            $html = '<!DOCTYPE html><html lang="pl">' .
                                    '<head><meta http-equiv="content-type" content="text/html; charset=UTF-8"/>' .
                                    '<link rel="stylesheet" href="/resources/css/upowaznienie.css"/></head><body>' .
                                    '<p align="center"><b>UPOWAŻNIENIE nr ' . $nrupowaznienia . '</p>' .
                                    '<p align="center"><b>do przetwarzania danych osobowych<br/>' .
                                    'w systemie informatycznym lub w zbiorze w wersji papierowej</b></p>' .
                                    '<p align="center"> w <span>' . $sqlfirma . ' z siedzibą w ' . $miejscowosc . ' ' . $ulica . '</span></p>' .
                                    '<p></p>' .
                                    '<p style="font-size: large;">Z dniem ' . $datanadania . 'r. Pani ' . $imienaz . '</p>' .
                                    '<p>otrzymuje upoważnienie do przetwarzania danych osobowych w następujących zbiorach danych:</p>' .
                                    '<p><b>' . $grupy . '</b></p>' .
                                    '<p align="justify">Zobowiązuję Panią do przestrzegania przepisów dotyczących ochrony danych osobowych oraz wprowadzonych i wdrożonych do stosowania przez Administratora Danych „Polityki Bezpieczeństwa Informacji” oraz „Instrukcji zarządzania systemem informatycznym służącym do przetwarzania danych osobowych.”</p>' .
                                    '<p align="justify">Upoważnienie obowiązuje do dnia zakończenia wykonywania obowiązków służbowych względem Administratora Danych świadczonych na podstawie umowy o pracę lub umowy cywilnoprawnej.</p>' .
                                    '<p align="center"><b>OŚWIADCZENIE</b></p>' .
                                    '<p align="justify" style="font-size: small;">Oświadczam, iż zostałam zapoznana z przepisami dotyczących ochrony danych osobowych, w szczególności ustawy z dnia 29 sierpnia 1997r. o ochronie danych osobowych (tj. Dz.U. z 2016r., poz. 922 z późn.zm.), wydanych na jej podstawie aktów wykonawczych oraz wprowadzonych i wdrożonych do stosowania przez Administratora Danych „Polityki Bezpieczeństwa Informacji” oraz „Instrukcji zarządzania systemem informatycznym służącym do przetwarzania danych osobowych.”</p>' .
                                    '<p>Zobowiązuję się do:</p>' .
                                    '<ul>' .
                                    '<li><p>zachowania w tajemnicy danych osobowych, do których mam lub będę miała dostęp w związku z wykonywaniem zadań służbowych lub obowiązków pracowniczych</p></li>' .
                                    '<li><p>niewykorzystywania danych osobowych w celach pozasłużbowych o ile nie są one jawne</p></li>' .
                                    '<li><p>zachowania w tajemnicy sposobów zabezpieczenia danych osobowych o ile nie są one jawne</p></li>' .
                                    '<li><p>korzystania ze sprzętu IT oraz oprogramowania wyłącznie w związku z wykonywaniem obowiązków pracowniczych</p></li>' .
                                    '<li><p>wykorzystywania jedynie legalnego oprogramowania pochodzącego od Pracodawcy</p></li>' .
                                    '<li><p>należytej dbałości o sprzęt i oprogramowanie zgodnie z dokumentacją ochrony danych osobowych</p></li>' .
                                    '<li><p>korzystania z komputerów przenośnych zgodnie z dokumentacją ochrony danych osobowych</p></li>' .
                                    '</ul>' .
                                    '<p align="justify">Przyjmuję do wiadomości, iż postępowanie sprzeczne z powyższymi zobowiązaniami, może być uznane przez Pracodawcę za ciężkie naruszenie obowiązków pracowniczych w rozumieniu art. 52 § 1 pkt 1 Kodeksu Pracy lub za naruszenie przepisów karnych ww. ustawy o ochronie danych osobowych.</p>' .
                                    '<p></p>' .
                                    '<p>………………………</p>' .
                                    '<p><i> podpis pracownika</i></p>' .
                                    '</body></html>';
                        } else {
                            $html = '<!DOCTYPE html><html lang="pl">' .
                                    '<head><meta http-equiv="content-type" content="text/html; charset=UTF-8"/>' .
                                    '<link rel="stylesheet" href="/resources/css/upowaznienie.css"/></head><body>' .
                                    '<p align="center"><b>UPOWAŻNIENIE nr ' . $nrupowaznienia . '</p>' .
                                    '<p align="center"><b>do przetwarzania danych osobowych<br/>' .
                                    'w systemie informatycznym lub w zbiorze w wersji papierowej</b></p>' .
                                    '<p align="center"> w <span>' . $sqlfirma . ' z siedzibą w ' . $miejscowosc . ' ' . $ulica . '</span></p>' .
                                    '<p></p>' .
                                    '<p style="font-size: large;">Z dniem ' . $datanadania . 'r. Pan ' . $imienaz . '</p>' .
                                    '<p>otrzymuje upoważnienie do przetwarzania danych osobowych w następujących zbiorach danych:</p>' .
                                    '<p><b>' . $grupy . '</b></p>' .
                                    '<p align="justify">Zobowiązuję Pana do przestrzegania przepisów dotyczących ochrony danych osobowych oraz wprowadzonych i wdrożonych do stosowania przez Administratora Danych „Polityki Bezpieczeństwa Informacji” oraz „Instrukcji zarządzania systemem informatycznym służącym do przetwarzania danych osobowych.”</p>' .
                                    '<p align="justify">Upoważnienie obowiązuje do dnia zakończenia wykonywania obowiązków służbowych względem Administratora Danych świadczonych na podstawie umowy o pracę lub umowy cywilnoprawnej.</p>' .
                                    '<p align="center"><b>OŚWIADCZENIE</b></p>' .
                                    '<p align="justify" style="font-size: small;">Oświadczam, iż zostałem zapoznany z przepisami dotyczących ochrony danych osobowych, w szczególności ustawy z dnia 29 sierpnia 1997r. o ochronie danych osobowych (tj. Dz.U. z 2016r., poz. 922 z późn.zm.), wydanych na jej podstawie aktów wykonawczych oraz wprowadzonych i wdrożonych do stosowania przez Administratora Danych „Polityki Bezpieczeństwa Informacji” oraz „Instrukcji zarządzania systemem informatycznym służącym do przetwarzania danych osobowych.”</p>' .
                                    '<p>Zobowiązuję się do:</p>' .
                                    '<ul>' .
                                    '<li><p>zachowania w tajemnicy danych osobowych, do których mam lub będę miała dostęp w związku z wykonywaniem zadań służbowych lub obowiązków pracowniczych</p></li>' .
                                    '<li><p>niewykorzystywania danych osobowych w celach pozasłużbowych o ile nie są one jawne</p></li>' .
                                    '<li><p>zachowania w tajemnicy sposobów zabezpieczenia danych osobowych o ile nie są one jawne</p></li>' .
                                    '<li><p>korzystania ze sprzętu IT oraz oprogramowania wyłącznie w związku z wykonywaniem obowiązków pracowniczych</p></li>' .
                                    '<li><p>wykorzystywania jedynie legalnego oprogramowania pochodzącego od Pracodawcy</p></li>' .
                                    '<li><p>należytej dbałości o sprzęt i oprogramowanie zgodnie z dokumentacją ochrony danych osobowych</p></li>' .
                                    '<li><p>korzystania z komputerów przenośnych zgodnie z dokumentacją ochrony danych osobowych</p></li>' .
                                    '</ul>' .
                                    '<p align="justify">Przyjmuję do wiadomości, iż postępowanie sprzeczne z powyższymi zobowiązaniami, może być uznane przez Pracodawcę za ciężkie naruszenie obowiązków pracowniczych w rozumieniu art. 52 § 1 pkt 1 Kodeksu Pracy lub za naruszenie przepisów karnych ww. ustawy o ochronie danych osobowych.</p>' .
                                    '<p></p>' .
                                    '<p>………………………</p>' .
                                    '<p><i> podpis pracownika</i></p>' .
                                    '</body></html>';
                        }
                        $mpdf = new mPDF();
                        $mpdf->WriteHTML($html);
                        require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/ConvertNames.php');
                        $imienazplik = ConvertNames::cn($imienaz);
                        $id_szkolenia = R::getCell("SELECT id FROM szkoleniewykaz WHERE nazwa = '$szkolenie'");
                        $nazwapliku = 'resources/upowaznienia/upowaznienie' . $id . '-' . $imienazplik . '.' . $id_szkolenia . '.' . 'pdf';
                        $mpdf->Output($nazwapliku, 'F');
                        Mail::mailupowaznienie($imienaz, $plec, $email, $nazwapliku, $poziomzaswiadczenie, $kontakt, $bcc, $id);
                        //czas sesji zaswiadcza, ze funkcja zostala wykonana bez bledu do konca 
                        $czasbiezacy = date("Y-m-d H:i:s");
                        $id = $_SESSION['uczestnik']['id'];
                        R::exec("UPDATE  `uczestnicy` SET  `upowaznieniedata`='$czasbiezacy' WHERE  `uczestnicy`.`id` = '$id';");
                    }
                }
            } catch (Exception $error) {
                Mail::mailerror($error);
            }
        }
    }
    
     public final static function pobierzBCC() {
        try {
            $sciezkaroot = filter_input(INPUT_SERVER, 'DOCUMENT_ROOT');
            require_once($sciezkaroot . '/resources/php/Rb.php');
            R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
        } catch (Exception $e) {};
        $bcc = $_SESSION['uczestnik']['BCC'];
        $szkolenie = $_SESSION['uczestnik']['nazwaszkolenia'];
        $firma = $_SESSION['uczestnik']['firma'];
        $sql = "SELECT email FROM szkolenieust WHERE szkolenieust.firma = '$firma' AND szkolenieust.nazwaszkolenia = '$szkolenie'";
        $email = R::getCell($sql);
        if (isset($email)) {
            $bcc = $email;
        }
        return $bcc;
    }

    public final static function pobierzgrupy($id) {
        $sql = "SELECT uczestnikgrupy.grupa FROM uczestnikgrupy WHERE id_uczestnik = '$id'";
        $zapisanegrupy = R::getCol($sql);
        $output = mb_strtolower(implode(", ",$zapisanegrupy),'UTF-8');
        return $output;
    }
}
?>

