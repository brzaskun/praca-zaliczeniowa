<?php error_reporting(0);

if (session_status() != 2) {
    session_start();
};
$creatorid = $_SESSION['uczestnik']['id'];
require_once $_SERVER['DOCUMENT_ROOT'].'/resources/php/Excelwygeneruj.php';
Excelwygeneruj::excel();
$objReader = new PHPExcel_Reader_Excel2007();
$sciezka = $_SERVER['DOCUMENT_ROOT'] . '/TestDaneOsobowe/upload/' . $creatorid . '/szkolenieDaneOsobowe.xlsx';
$objPHPExcel = $objReader->load($sciezka);
header("Pragma: public");
header("Expires: 0");
header("Cache-Control: must-revalidate, post-check=0, pre-check=0");
header("Content-Type: application/force-download");
header("Content-Type: application/octet-stream");
header("Content-Type: application/download");
header("Content-Disposition: attachment;filename=SzkolenieDaneOsobowe.xlsx");
header("Content-Transfer-Encoding: binary ");
$objWriter = new PHPExcel_Writer_Excel2007($objPHPExcel);
$objWriter->setOffice2003Compatibility(true);
$objWriter->save('php://output');
$url = 'manager.php';
header("Location: $url");
exit();
?>
