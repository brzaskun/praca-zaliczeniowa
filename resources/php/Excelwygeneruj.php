<?php

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 * Description of Excelwygeneruj
 *
 * @author Osito
 */
class Excelwygeneruj {

    public static function excel() {
        if (session_status() != 2) {
            session_start();
        };
        require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
        R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
        $firmamanagera = $_SESSION['uczestnik']['firma'];
        $mailmanager = $_SESSION['uczestnik']['email'];
        $sql = "SELECT * FROM uczestnicy WHERE firma = '$firmamanagera' AND NOT email = '$mailmanager'";
        $tabela = R::getAll($sql);
        $creator = $_SESSION['uczestnik']['imienazwisko'];
        $creatorid = $_SESSION['uczestnik']['id'];

        /** PHPExcel */
        require_once $_SERVER['DOCUMENT_ROOT'] . '/resources/PHPExcel-1.8.1/Classes/PHPExcel.php';

        /** PHPExcel_Writer_Excel2007 */
        require_once $_SERVER['DOCUMENT_ROOT'] . '/resources/PHPExcel-1.8.1/Classes/PHPExcel/Writer/Excel2007.php';

        // Create new PHPExcel object
        $objPHPExcel = new PHPExcel();

        // Set properties
        $objPHPExcel->getProperties()->setCreator($creator);
        $objPHPExcel->getProperties()->setLastModifiedBy($creator);
        $objPHPExcel->getProperties()->setTitle("Szkolenie Ochrona Danych Osobowych");
        $objPHPExcel->getProperties()->setSubject("Osoby szkolone");
        $objPHPExcel->getProperties()->setDescription("Zestawienie osób szkolonych wraz z wynikami szkolenia");

        // Create the worksheet
        $objPHPExcel->setActiveSheetIndex(0);
        $objPHPExcel->getActiveSheet()->setCellValue('A1', 'id')
                ->setCellValue('B1', 'adres mail')
                ->setCellValue('C1', 'imie i nazwisko')
                ->setCellValue('D1', 'płeć')
                ->setCellValue('E1', 'firma')
                ->setCellValue('F1', 'szkolenie')
                ->setCellValue('G1', 'uprawnienia')
                ->setCellValue('H1', 'wysłano link')
                ->setCellValue('I1', 'rozpoczecie sesji')
                ->setCellValue('J1', 'zakończenie sesji')
                ->setCellValue('K1', 'wynik testu')
                ->setCellValue('L1', 'wysłano cert.')
                ->setCellValue('M1', 'ilość logowań')
                ->setCellValue('N1', 'ilość poprawnych')
                ->setCellValue('O1', 'ilość błędnych')
                ->setCellValue('P1', 'ilość odpowiedzi');


        //zaplenianie danymi z tabeli
        $objPHPExcel->getActiveSheet()->fromArray($tabela, NULL, 'A2');
        //wysokosc pierwszego rzędu
        $objPHPExcel->getActiveSheet()->getRowDimension(1)->setRowHeight(40);
        //pozycjonowanie pierwszego rzedu
        $objPHPExcel->getActiveSheet()->getStyle('A1:U1')->getAlignment()->setVertical(PHPExcel_Style_Alignment::VERTICAL_JUSTIFY);
        $objPHPExcel->getActiveSheet()->getStyle('A1:U1')->getAlignment()->setVertical(PHPExcel_Style_Alignment::HORIZONTAL_JUSTIFY);

        // Set autofilter
        // Always include the complete filter range!
        // Excel does support setting only the caption
        // row, but that's not a best practise...
        $objPHPExcel->getActiveSheet()->setAutoFilter($objPHPExcel->getActiveSheet()->calculateWorksheetDimension());

        //word wrapping
        $objPHPExcel->getActiveSheet()->getStyle('A1:U1')->getAlignment()->setWrapText(true);
        //font kolor 
        $objPHPExcel->getActiveSheet()->getStyle('A1:U1')->getFont()->setBold(true);
        $objPHPExcel->getActiveSheet()->getStyle('A1:U1')->getFont()->getColor()->setRGB('3366FF');
        //szerokosc kolumn
        $objPHPExcel->getActiveSheet()->getColumnDimension('A')->setWidth(6);
        $objPHPExcel->getActiveSheet()->getColumnDimension('B')->setWidth(22);
        $objPHPExcel->getActiveSheet()->getColumnDimension('C')->setWidth(22);
        $objPHPExcel->getActiveSheet()->getColumnDimension('D')->setWidth(6);
        $objPHPExcel->getActiveSheet()->getColumnDimension('E')->setWidth(22);
        $objPHPExcel->getActiveSheet()->getColumnDimension('F')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('G')->setWidth(15);
        $objPHPExcel->getActiveSheet()->getColumnDimension('H')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('I')->setWidth(10);
        $objPHPExcel->getActiveSheet()->getColumnDimension('J')->setWidth(10);
        $objPHPExcel->getActiveSheet()->getColumnDimension('K')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('L')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('M')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('N')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('O')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('P')->setWidth(12);

        //ramki
        $styleArray = array('borders' => array('allborders' => array('style' => PHPExcel_Style_Border::BORDER_HAIR, 'color' => array('rgb' => '000000'),),),);
        $objPHPExcel->getActiveSheet()->getStyle('A1:U1')->applyFromArray($styleArray);
        $objPHPExcel->getActiveSheet()->setShowGridlines(FALSE);

        //orientacja papieru
        $objPHPExcel->getActiveSheet()->getPageSetup()->setOrientation(PHPExcel_Worksheet_PageSetup::ORIENTATION_LANDSCAPE);
        $objPHPExcel->getActiveSheet()->getPageSetup()->setPaperSize(PHPExcel_Worksheet_PageSetup::PAPERSIZE_A4);
        $objPHPExcel->getActiveSheet()->getPageSetup()->setFitToPage(true);

        // Rename sheet
        $objPHPExcel->getActiveSheet()->setTitle($firmamanagera);

        //Oczyscic katalog z innych plikow
        $sciezka = $_SERVER['DOCUMENT_ROOT'] . '/upload/' . $creatorid . '/';
        if (!is_dir($sciezka)) {
            mkdir($sciezka);
        }
        if (is_dir($sciezka)) {
            foreach (new DirectoryIterator($sciezka) as $fileInfo)
                if (!$fileInfo->isDot()) {
                    chdir($sciezka);
                    unlink($fileInfo->getFilename());
                }
        }
        $sciezka = $_SERVER['DOCUMENT_ROOT'] . '/upload/' . $creatorid . '/SzkolenieDaneOsobowe.xlsx';
        // Save Excel 2007 file
        $objWriter = new PHPExcel_Writer_Excel2007($objPHPExcel);
        $objWriter->save($sciezka);
    }

    public static function exceladmin($firma, $uczestnicyrodzaj) {
        if (session_status() != 2) {
            session_start();
        };
        require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
        R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
        if ($firma != 'null') {
            if ($uczestnicyrodzaj == "wszyscy") {
                $sql = "SELECT * FROM uczestnicy WHERE `uczestnicy`.`firma` = '$firma' ORDER BY `uczestnicy`.`email` ASC";
            } else if ($uczestnicyrodzaj == "aktywni") {
                $sql = "SELECT * FROM uczestnicy WHERE `uczestnicy`.`firma` = '$firma'  AND (`uczestnicy`.`dataustania` IS NULL OR CHAR_LENGTH(`uczestnicy`.`dataustania`) < 1)  ORDER BY `uczestnicy`.`email` ASC";
            } else {
                $sql = "SELECT * FROM uczestnicy WHERE `uczestnicy`.`firma` = '$firma'  AND (`uczestnicy`.`dataustania` IS NOT NULL AND CHAR_LENGTH(`uczestnicy`.`dataustania`) = 10)  ORDER BY `uczestnicy`.`email` ASC";
            }
        } else {
            $sql = "SELECT * FROM uczestnicy";
        }
        $mailmanager = $_SESSION['uczestnik']['email'];
        $tabela = R::getAll($sql);
        $creator = $_SESSION['uczestnik']['imienazwisko'];
        $creatorid = $_SESSION['uczestnik']['id'];

        /** PHPExcel */
        require_once $_SERVER['DOCUMENT_ROOT'] . '/resources/PHPExcel-1.8.1/Classes/PHPExcel.php';

        /** PHPExcel_Writer_Excel2007 */
        require_once $_SERVER['DOCUMENT_ROOT'] . '/resources/PHPExcel-1.8.1/Classes/PHPExcel/Writer/Excel2007.php';

        // Create new PHPExcel object
        $objPHPExcel = new PHPExcel();

        // Set properties
        $objPHPExcel->getProperties()->setCreator($creator);
        $objPHPExcel->getProperties()->setLastModifiedBy($creator);
        $objPHPExcel->getProperties()->setTitle("Szkolenie Ochrona Danych Osobowych");
        $objPHPExcel->getProperties()->setSubject("Osoby szkolone");
        $objPHPExcel->getProperties()->setDescription("Zestawienie osób szkolonych wraz z wynikami szkolenia");

        // Create the worksheet
        $objPHPExcel->setActiveSheetIndex(0);
        $objPHPExcel->getActiveSheet()->setCellValue('A1', 'id')
                ->setCellValue('B1', 'adres mail')
                ->setCellValue('C1', 'imie i nazwisko')
                ->setCellValue('D1', 'płeć')
                ->setCellValue('E1', 'firma')
                ->setCellValue('F1', 'szkolenie')
                ->setCellValue('G1', 'uprawnienia')
                ->setCellValue('H1', 'wysłano link')
                ->setCellValue('I1', 'rozpoczecie sesji')
                ->setCellValue('J1', 'zakończenie sesji')
                ->setCellValue('K1', 'wynik testu')
                ->setCellValue('L1', 'wysłano cert.')
                ->setCellValue('M1', 'ilość logowań')
                ->setCellValue('N1', 'ilość poprawnych')
                ->setCellValue('O1', 'ilość błędnych')
                ->setCellValue('P1', 'ilość odpowiedzi')
                ->setCellValue('Q1', 'nr upowaznienia')
                ->setCellValue('R1', 'identyfikator')
                ->setCellValue('S1', 'data nadania')
                ->setCellValue('T1', 'data ustania')
                ->setCellValue('U1', 'wysłane upoważnienie');
        //zaplenianie danymi z tabeli
        $objPHPExcel->getActiveSheet()->fromArray($tabela, NULL, 'A2');
        //wysokosc pierwszego rzędu
        $objPHPExcel->getActiveSheet()->getRowDimension(1)->setRowHeight(40);
        //pozycjonowanie pierwszego rzedu
        $objPHPExcel->getActiveSheet()->getStyle('A1:U1')->getAlignment()->setVertical(PHPExcel_Style_Alignment::VERTICAL_JUSTIFY);
        $objPHPExcel->getActiveSheet()->getStyle('A1:U1')->getAlignment()->setVertical(PHPExcel_Style_Alignment::HORIZONTAL_JUSTIFY);

        // Set autofilter
        // Always include the complete filter range!
        // Excel does support setting only the caption
        // row, but that's not a best practise...
        $objPHPExcel->getActiveSheet()->setAutoFilter($objPHPExcel->getActiveSheet()->calculateWorksheetDimension());

        //word wrapping
        $objPHPExcel->getActiveSheet()->getStyle('A1:U1')->getAlignment()->setWrapText(true);
        //font kolor 
        $objPHPExcel->getActiveSheet()->getStyle('A1:U1')->getFont()->setBold(true);
        $objPHPExcel->getActiveSheet()->getStyle('A1:U1')->getFont()->getColor()->setRGB('3366FF');
        //szerokosc kolumn
        $objPHPExcel->getActiveSheet()->getColumnDimension('A')->setWidth(6);
        $objPHPExcel->getActiveSheet()->getColumnDimension('B')->setWidth(22);
        $objPHPExcel->getActiveSheet()->getColumnDimension('C')->setWidth(22);
        $objPHPExcel->getActiveSheet()->getColumnDimension('D')->setWidth(6);
        $objPHPExcel->getActiveSheet()->getColumnDimension('E')->setWidth(22);
        $objPHPExcel->getActiveSheet()->getColumnDimension('F')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('G')->setWidth(15);
        $objPHPExcel->getActiveSheet()->getColumnDimension('H')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('I')->setWidth(10);
        $objPHPExcel->getActiveSheet()->getColumnDimension('J')->setWidth(10);
        $objPHPExcel->getActiveSheet()->getColumnDimension('K')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('L')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('M')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('N')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('O')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('P')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('Q')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('R')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('S')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('T')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('U')->setWidth(12);
        //ramki
        $styleArray = array('borders' => array('allborders' => array('style' => PHPExcel_Style_Border::BORDER_HAIR, 'color' => array('rgb' => '000000'),),),);
        $objPHPExcel->getActiveSheet()->getStyle('A1:U1')->applyFromArray($styleArray);
        $objPHPExcel->getActiveSheet()->setShowGridlines(FALSE);

        //orientacja papieru
        $objPHPExcel->getActiveSheet()->getPageSetup()->setOrientation(PHPExcel_Worksheet_PageSetup::ORIENTATION_LANDSCAPE);
        $objPHPExcel->getActiveSheet()->getPageSetup()->setPaperSize(PHPExcel_Worksheet_PageSetup::PAPERSIZE_A4);
        $objPHPExcel->getActiveSheet()->getPageSetup()->setFitToPage(true);

        // Rename sheet
        if (strlen($firma) > 31) {
            $firmamanagera = substr($firma, 0, 30);
        }
        $objPHPExcel->getActiveSheet()->setTitle($firma);

        //Oczyscic katalog z innych plikow
        $sciezka = $_SERVER['DOCUMENT_ROOT'] . '/upload/' . $creatorid . '/';
        if (!is_dir($sciezka)) {
            mkdir($sciezka);
        }
        if (is_dir($sciezka)) {
            foreach (new DirectoryIterator($sciezka) as $fileInfo)
                if (!$fileInfo->isDot()) {
                    chdir($sciezka);
                    unlink($fileInfo->getFilename());
                }
        }
        //$file = $_SERVER['DOCUMENT_ROOT'] . '/upload/' . $creatorid . '/SzkolenieDaneOsobowe.xlsx';
        // Save Excel 2007 file
        //$objWriter = new PHPExcel_Writer_Excel2007($objPHPExcel);
        //$objWriter->save($file);
        //echo '/upload/' . $creatorid . '/SzkolenieDaneOsobowe.xlsx';
        $file = tempnam($sciezka, '');
        $file = $file . '.xlsx';
        //        $file = $_SERVER['DOCUMENT_ROOT'] . '/upload/' . $creatorid . '/listauzytkownicygrupy.xlsx'; 
        // Save Excel 2007 file
        $objWriter = new PHPExcel_Writer_Excel2007($objPHPExcel);
        $objWriter->save($file);
        $pliktablica = explode("/", $file);
        $plik = $pliktablica[sizeof($pliktablica)-1]; 
        echo '/upload/' . $creatorid . '/' .$plik;
    }

    public static function excel_uzytkownik_grupy($naglowki,$tab2,$nazwafirmy) {
        $wiersze = json_decode($tab2);
        if (session_status() != 2) {
            session_start();
        };
        require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
        R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
        $mailmanager = $_SESSION['uczestnik']['email'];
        $creator = $_SESSION['uczestnik']['imienazwisko'];
        $creatorid = $_SESSION['uczestnik']['id'];

        /** PHPExcel */
        require_once $_SERVER['DOCUMENT_ROOT'] . '/resources/PHPExcel-1.8.1/Classes/PHPExcel.php';

        /** PHPExcel_Writer_Excel2007 */
        require_once $_SERVER['DOCUMENT_ROOT'] . '/resources/PHPExcel-1.8.1/Classes/PHPExcel/Writer/Excel2007.php';

        // Create new PHPExcel object
        $objPHPExcel = new PHPExcel();

        // Set properties
        $objPHPExcel->getProperties()->setCreator($creator);
        $objPHPExcel->getProperties()->setLastModifiedBy($creator);
        $objPHPExcel->getProperties()->setTitle("Szkolenie Ochrona Danych Osobowych");
        $objPHPExcel->getProperties()->setSubject("Osoby szkolone");
        $objPHPExcel->getProperties()->setDescription("Zestawienie osób szkolonych wraz z zakresem");
        // Create the worksheet
        $objPHPExcel->setActiveSheetIndex(0);
        $objPHPExcel->getActiveSheet()->setCellValue('A1', "id")
                ->setCellValue('B1', "email")
                ->setCellValue('C1', "imię i nazwisko")
                ->setCellValue('D1', "nr upoważnienia")
                ->setCellValue('E1', "identyfikator")
                ->setCellValue('F1', "data nadania")
                ->setCellValue('G1', "data ustania") 
                ->setCellValue('H1', "wysłano up"); 
        $colnumer = 8; 
        foreach ($naglowki as $nazwanagl) {
            $objPHPExcel->getActiveSheet()->setCellValueByColumnAndRow($colnumer++, 1, $nazwanagl[nazwagrupy]);
        }
        //zaplenianie danymi z tabeli
        $objPHPExcel->getActiveSheet()->fromArray($wiersze, NULL, 'A2');
        $objPHPExcel->getActiveSheet()->removeColumn("I");
        //wysokosc pierwszego rzędu
        $objPHPExcel->getActiveSheet()->getRowDimension(1)->setRowHeight(40);
        //pozycjonowanie pierwszego rzedu
        $ostatniacol = PHPExcel_Cell::stringFromColumnIndex($colnumer - 2);
        $objPHPExcel->getActiveSheet()->getStyle('A1:' . $ostatniacol . '1')->getAlignment()->setVertical(PHPExcel_Style_Alignment::VERTICAL_CENTER);
        $objPHPExcel->getActiveSheet()->getStyle('I1:' . $ostatniacol . '1')->getAlignment()->setTextRotation(90);
        $objPHPExcel->getActiveSheet()->getStyle('A1:H1')->getAlignment()->setHorizontal(PHPExcel_Style_Alignment::HORIZONTAL_JUSTIFY);
        $objPHPExcel->getActiveSheet()->getStyle('I1:' . $ostatniacol . '1')->getAlignment()->setHorizontal(PHPExcel_Style_Alignment::HORIZONTAL_LEFT);
        $objPHPExcel->getActiveSheet()->getRowDimension(1)->setRowHeight(-1);

        // Set autofilter
        // Always include the complete filter range!
        // Excel does support setting only the caption
        // row, but that's not a best practise...
        $objPHPExcel->getActiveSheet()->setAutoFilter($objPHPExcel->getActiveSheet()->calculateWorksheetDimension());

        //word wrapping
        $objPHPExcel->getActiveSheet()->getStyle('A1:' . $ostatniacol . '1')->getAlignment()->setWrapText(true);
        //font kolor 
        $objPHPExcel->getActiveSheet()->getStyle('A1:' . $ostatniacol . '1')->getFont()->setBold(true);
        $objPHPExcel->getActiveSheet()->getStyle('A1:' . $ostatniacol . '1')->getFont()->getColor()->setRGB('3366FF');
        //szerokosc kolumn
        $objPHPExcel->getActiveSheet()->getColumnDimension('A')->setWidth(6);
        $objPHPExcel->getActiveSheet()->getColumnDimension('B')->setWidth(35);
        $objPHPExcel->getActiveSheet()->getColumnDimension('C')->setWidth(30);
        $objPHPExcel->getActiveSheet()->getColumnDimension('D')->setWidth(22);
        $objPHPExcel->getActiveSheet()->getColumnDimension('E')->setWidth(22);
        $objPHPExcel->getActiveSheet()->getColumnDimension('F')->setWidth(12);
        $objPHPExcel->getActiveSheet()->getColumnDimension('G')->setWidth(15);
        $objPHPExcel->getActiveSheet()->getColumnDimension('H')->setWidth(10);
        for ($i = 7; $i <= $ostatniacol; $i++) {
            $objPHPExcel->getActiveSheet()->getColumnDimension(PHPExcel_Cell::stringFromColumnIndex($i))->setWidth(16);
        }
        //ramki
        $styleArray = array('borders' => array('allborders' => array('style' => PHPExcel_Style_Border::BORDER_HAIR, 'color' => array('rgb' => '000000'),),),);
        $objPHPExcel->getActiveSheet()->getStyle('A1:' . $ostatniacol . '1')->applyFromArray($styleArray);
        $objPHPExcel->getActiveSheet()->setShowGridlines(FALSE);
        
        //orientacja papieru
        $objPHPExcel->getActiveSheet()->getPageSetup()->setOrientation(PHPExcel_Worksheet_PageSetup::ORIENTATION_LANDSCAPE);
        $objPHPExcel->getActiveSheet()->getPageSetup()->setPaperSize(PHPExcel_Worksheet_PageSetup::PAPERSIZE_A4);
        $objPHPExcel->getActiveSheet()->getPageSetup()->setFitToPage(true);
 
        // Rename sheet 
        if (strlen($nazwafirmy) > 31) {
            $nazwafirmy = substr($nazwafirmy, 0, 30);
        }
        $objPHPExcel->getActiveSheet()->setTitle($nazwafirmy);
        
        //Oczyscic katalog z innych plikow
        $sciezka = $_SERVER['DOCUMENT_ROOT'] . '/upload/' . $creatorid . '/';
        if (!is_dir($sciezka)) {
            mkdir($sciezka);
        }
        if (is_dir($sciezka)) {
            foreach (new DirectoryIterator($sciezka) as $fileInfo)
                if (!$fileInfo->isDot()) {
                    chdir($sciezka);
                    unlink($fileInfo->getFilename());
                }
        }
        $file = tempnam($sciezka, '');
        $file = $file . '.xlsx';
        //        $file = $_SERVER['DOCUMENT_ROOT'] . '/upload/' . $creatorid . '/listauzytkownicygrupy.xlsx'; 
        // Save Excel 2007 file
        $objWriter = new PHPExcel_Writer_Excel2007($objPHPExcel);
        $objWriter->save($file);
        $pliktablica = explode("/", $file);
        $plik = $pliktablica[sizeof($pliktablica)-1]; 
        echo '/upload/' . $creatorid . '/' .$plik; 
    }

}

?>
