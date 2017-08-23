<?php
if (session_status() != 2) {
    session_start();
};
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/CertyfikatGenerowanie.php');
$nazwazaswiadczenia = $_GET["nazwazaswiadczenia"];
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
$uczestnik = $_SESSION['uczestnik'];
$id = $_SESSION['uczestnik']['id'];
$plec = $_SESSION['uczestnik']['plec'];
$email = $_SESSION['uczestnik']['email'];
$imienaz = $_SESSION['uczestnik']['imienazwisko'];
$kontakt = $_SESSION['uczestnik']['kontakt'];
$szkolenie = $_SESSION['uczestnik']['nazwaszkolenia'];
$bcc = $_SESSION['uczestnik']['BCC'];
if ($bcc == "") {
    $bcc = "mchmielewska@interia.pl";
}
$dataukonczeniatmp = substr($_SESSION['uczestnik']['sessionend'], 0, 10); 
$dataukonczenia = substr($dataukonczeniatmp, 8, 2) . substr($dataukonczeniatmp, 4, 4) . substr($dataukonczeniatmp, 0, 4);
$poziomzaswiadczenie = CertyfikatGenerowanie::pobierzPoziomZaswiadczeniaPodglad($nazwazaswiadczenia);
$html = CertyfikatGenerowanie::pobierzTrescZaswiadczeniaPodglad($imienaz, $dataukonczenia, $poziomzaswiadczenie,$nazwazaswiadczenia);
require_once("resources/MPDF57/mpdf.php");
$mpdf = new mPDF();
$mpdf->SetImportUse();
$pdf = R::getCell("SELECT pdf FROM zaswiadczenia WHERE nazwa = '$nazwazaswiadczenia'");
if ($pdf) {
    $pagecount = $mpdf->SetSourceFile('resources/css/pics/' . $pdf);
} else {
    $pagecount = $mpdf->SetSourceFile('resources/css/pics/zaswiadczenie1.pdf');
}
$tplId = $mpdf->ImportPage($pagecount);
$mpdf->UseTemplate($tplId);
$mpdf->WriteHTML($html);
$mpdf->Output();
?>
