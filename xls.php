<?php error_reporting(0);
// Include the PHPExcel library
require_once $_SERVER['DOCUMENT_ROOT'].'/resources/PHPExcel-1.8.1/Classes/PHPExcel/IOFactory.php';
// Read the Excel file
$objPHPExcel = PHPExcel_IOFactory::load('upload/30template.xls');

// Display as HTML in the browser window
$objWriter = PHPExcel_IOFactory::createWriter($objPHPExcel, 'HTML');
$objWriter->save('php://output');
?>