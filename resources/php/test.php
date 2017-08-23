<?php

// Sends output inline to browser
require_once("../MPDF57/mpdf.php");
$mpdf = new mPDF();

$mpdf->WriteHTML(' 
Hallo World
');

$mpdf->Output();

?>

