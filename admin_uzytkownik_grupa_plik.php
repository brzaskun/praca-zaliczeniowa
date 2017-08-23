<?php
error_reporting(0);
require_once($_SERVER['DOCUMENT_ROOT'] . '/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo', 'Testdane7005*');
$_wynik_firmaall = R::getAll('SELECT * FROM zakladpracy');
?>
<script>
    var ajaxpokaz = function() {
     $("#ajax_sun").puidialog({
        height: 120,
        width: 200,
        resizable: false,
        closable: false,
        showEffect: 'fade',
        hideEffect: 'fade',
        location: "center", 
        modal: true   
    });
    $("#ajax_sun").puidialog('show');
    };
</script>
<div style="box-shadow: 10px 10px 5px #888; padding: 30px;  margin-top: 10px; background-color: gainsboro">
    <div id="plikwzorcowy" style="height: 40px;">
        <h3>jako plik do wczytania należy wykorzystać uprzednio pobrany plik z uczestnikami szkolenia z danej firmy w formacie XLS</h3>
    </div>


    <form id="form1" enctype="multipart/form-data" method="post" action="admin_uzytkownik_grupa_uploadfile.php"> 
        <div style="height: 220px; width: 500px;">
            <div id="przyciskladowanie" style="height: 60px; width: 200px;border: 1px dashed #BBB; cursor:pointer; text-align: center; vertical-align: middle; display: table-cell;">
                <label for="file" style="font-weight: 800;" onmouseover="$(this).css('background', '#339bb9');" 
                       onmouseout="$(this).css('background', 'gainsboro');">Wybierz plik formatu Excel do wczytania</label><br/>
            </div>
            <div style='height: 0px;width:0px; overflow:hidden;'>
                <input type="file" name="file" id="file" onchange="fileSelected();" accept=".xls,.xlsx" 
                       style="height: 40px; padding: 5px; padding: 10px; width: 220px; margin-left: 2%; float: left;"/>
            </div>
            <div id="fileName"></div>
            <div id="fileSize"></div>
            <div id="fileType"></div>
            <div class="row" style="width: 400px;">
                <input id="wyslij" type="submit" value="Wczytaj" 
                       style="padding: 10px; width: 120px; margin-top: 4%; margin-left: auto; margin-right: auto; display: none"
                       onclick="ajaxpokaz()"/>
                <p id="niewlasciwyplik" style="display: none; color: red;">Plik ma niewłaściwe rozszerzenie</p>
            </div>
            <div id="progressNumber"></div>
            <div id="progressEfect" style="display: none;"><p> Plik wczytano </p></div>
        </div>

    </form>
    <?php
    error_reporting(0);
    require_once $_SERVER['DOCUMENT_ROOT'] . '/resources/PHPExcel-1.8.1/Classes/PHPExcel/IOFactory.php';
    if (session_status() != 2) {
        session_start();
    };
    if (isset($_FILES["file"])) {
        if ($_FILES["file"]["error"] > 0) {
            echo "Error: " . $_FILES["file"]["error"] . "<br>";
        } else {
            echo "<p style='font-weight: 900;'>Dane ostanio wczytanego pliku:</p>";
            echo "Nazwa pliku: " . $_FILES["file"]["name"] . "<br/>";
            echo "Typ: " . $_FILES["file"]["type"] . "<br/>";
            echo "Rozmiar: " . ($_FILES["file"]["size"] / 1024) . " kB<br/>";
            echo "Nazwa tymczasowa: " . $_FILES["file"]["tmp_name"] . "<br/>";
            $sciezka = $_SERVER['DOCUMENT_ROOT'] . '/upload/' . $_SESSION['uczestnik']['id'] . '/';
            if (!is_dir($sciezka)) {
                mkdir($sciezka);
            }
            if (is_dir($sciezka)) {
                foreach (new DirectoryIterator($sciezka) as $fileInfo) {
                    if (!$fileInfo->isDot()) {
                        chdir($sciezka);
                        unlink($fileInfo->getFilename());
                    }
                }
                move_uploaded_file($_FILES["file"]["tmp_name"], $sciezka . $_FILES["file"]["name"]);
                echo "Zachowany w katalogu: " . $sciezka . $_FILES["file"]["name"];
            }
            $udanazmiana = chdir($sciezka);
            $nazwapliku = $_FILES["file"]["name"];
            $locale = 'pl_PL';
            $validLocale = PHPExcel_Settings::setLocale($locale);
            if (!$validLocale) {
                echo 'Nie mogę ustawić lokalizacji pliku na  ' . $locale . " - wracam do wersji angielskojęzycznej<br />\n";
            }
            //wyciagam maile i nazwiska z bazy zeby znalesc nowych
            $sql = 'SELECT email FROM uczestnicy';
            $mailezbazy = R::getCol($sql);
            $sql = 'SELECT nazwa FROM szkoleniewykaz';
            $szkoleniafirmy = R::getCol($sql);
            $sql = 'SELECT imienazwisko FROM uczestnicy';
            $imienazwiskozbazy = R::getCol($sql);
            try {
                $valid = false;
                $types = array('Excel2007', 'Excel5');
                $objReader = null;
                foreach ($types as $type) {
                    $reader = PHPExcel_IOFactory::createReader($type);
                    if ($reader->canRead($nazwapliku)) {
                        $valid = true;
                        $objReader = PHPExcel_IOFactory::createReader($type);
                        break;
                    }
                }
                if ($valid == true) {
                    $objPHPExcel = $objReader->load($nazwapliku);
                } else {
                    echo "Nie mogę otworzyć pliku. Czy plik był zapisywany w Microsoft Excel? Nie potrafie otwierać plików sporządzonych w OpenOffice/LibreOffice itp.";
                    die();
                }
            } catch (Exception $ex) {
                echo "Nie mogę otworzyć pliku. Czy plik był zapisywany w Microsoft Excel? Nie potrafie otwierać plików sporządzonych w OpenOffice/LibreOffice itp.";
                die();
            }
//bierzemy tylko pierszy sheet bo moga byc pliki w wieloma sheetami
            $worksheet = $objPHPExcel->getSheet(0);
            $worksheetTitle = $worksheet->getTitle();
            $highestRow = $worksheet->getHighestRow(); // e.g. 10
            $highestColumn = $worksheet->getHighestColumn(); // e.g 'F'
            $highestColumnIndex = PHPExcel_Cell::columnIndexFromString($highestColumn); 
            $dlugoscnazwycol = strlen($highestColumn);
            $nrColumns = ord($highestColumn) - 64;
            $wykrytobladkolumny = 0;
            if ($nrColumns < 8 && $dlugoscnazwycol < 2) {
                echo '<p  style="color: red;">Błąd!. Nieprawidłowa ilość kolumn ' . $nrColumns . '. Mysi być przynajmniej 8. Sprawdź ilośc kolumn w pliku i ponownie go załaduj. Czy korzystasz z innego pliku niż wcześniej pobrany?';
                $wykrytobladkolumny = 1;
                die();
            }
            if ($wykrytobladkolumny === 0) {
                echo "<br>Pobrane dane z arkusza " . $worksheetTitle . " mają ";
                echo $nrColumns . ' kolumn (A-' . $highestColumn . ') ';
                echo ' i ' . $highestRow . ' wierszy.';
                $tablicapobranychpracownikow = array();
                $wykrytoblad = array();
                echo '<br><p>Dane wczytane z pliku: </p>';
                echo '<table  id="tabeladanepobrane" class="ui-datatable"><tr>';
                $dataod = null;
                $datado = null;
                for ($row = 1; $row <= $highestRow; ++$row) {
                    try {
                    echo '<tr>';
                    $wiersz = array();
                    for ($col = 0; $col < 8; ++$col) {
                        $cell = $worksheet->getCellByColumnAndRow($col, $row); 
                        $val = $cell->getValue();
                        if (PHPExcel_Shared_Date::isDateTime($cell) && $val == "") {
                            $val = NULL;
                        }
                        if (PHPExcel_Shared_Date::isDateTime($cell) && $val != NULL) {
                            $val = PHPExcel_Style_NumberFormat::toFormattedString($cell->getValue(), 'DD.MM.YYYY');
                            $val = str_replace('/', '.', $val);
                            $val = str_replace('-', '.', $val);
                            $vartab = explode(".", $val);
                            for ($i = 0; $i < 3; $i++) { 
                                if (strlen($vartab[$i]) == 1) {
                                    $vartab[$i]= "0".$vartab[$i];
                                } else if ($i == 2 && strlen($vartab[$i]) == 2) {
                                    $vartab[$i]= "20".$vartab[$i];
                                }
                            }
                            $val = implode(".", $vartab);
                        }
                        if ($col === 5) {
                            $dataod = $val;
                        } else if ($col === 6) {
                            $datado = $val;
                        }
                        $dataType = PHPExcel_Cell_DataType::dataTypeForValue($val);
                        echo '<td>' . $val . '</td>';
                        //walidacja adresu mail
                        if ($col === 1 && $row == 1) {
                            if ($val != "email" && $val != "email") {
                                echo "Zmieniono treść nagłówków kolumn. Proszę porównać z plikiem wzorcowym. Nie można dalej przetwarzać tabeli";
                                die();
                            }
                        }
                        if ($col === 1 && $row != 1) {
                            if ($val instanceof PHPExcel_RichText) {
                                $blad = array($val, "pole zawierające adres email w nieprawidłowym formacie, prawdopodobnie przeklejone", "w wierszu " . $row);
                                array_push($wykrytoblad, $blad);
                            }
                            if (!filter_var($val, FILTER_VALIDATE_EMAIL)) {
                                $blad = array($val, "niekompletny adres mail", "w wierszu " . $row);
                                array_push($wykrytoblad, $blad);
                            }
                            if (!in_array($val, $mailezbazy)) {
                                $blad = array($val, "taki mail nie istnieje w bazie firmy", "w wierszu " . $row);
                                array_push($wykrytoblad, $blad);
                            }
                        }
                        //walidacja imienia i nazwiska
                        if ($col === 2 && $row != 1) {
                            if ($val instanceof PHPExcel_RichText) {
                                $blad = array($val, "pole zawierające imię i nazwisko w nieprawidłowym formacie, prawdopodobnie przeklejone", "w wierszu " . $row);
                                array_push($wykrytoblad, $blad);
                            }
                            if (sizeof($val) < 1) {
                                $blad = array($val, "brak imienia i nazwiska", "w wierszu " . $row);
                                array_push($wykrytoblad, $blad);
                            }
                            if (!in_array($val, $imienazwiskozbazy)) {
                                $blad = array($val, "pracownik o takim imieniu i nazwisku nie istnieje w bazie", "w wierszu " . $row);
                                array_push($wykrytoblad, $blad);
                            }
                        }
                        if ($col === 5 && $row != 1) {
                            if (sizeof($val) > 0 && !preg_match("/^(0[1-9]|[12][0-9]|3[01])[.](0[1-9]|1[012])[.](19|20)\d\d$/", $val)) {
                                $blad = array($val, "data w nieprawidłowym formacie (winno być dd.mm.rrrr)/zła data", "w wierszu " . $row);
                                array_push($wykrytoblad, $blad);
                            }
                        }
                        if ($col === 6 && $row != 1) {
                            if (sizeof($val) > 0 && !preg_match("/^(0[1-9]|[12][0-9]|3[01])[.](0[1-9]|1[012])[.](19|20)\d\d$/", $val)) {
                                $blad = array($val, "data w nieprawidłowym formacie (winno być dd.mm.rrrr)/zła data", "w wierszu " . $row);
                                array_push($wykrytoblad, $blad);
                            }
                        }
                        if ($col === 6  && $row != 1) {
                            date_default_timezone_set('Europe/Warsaw');
                            $today_time = date("jS F, Y", strtotime($dataod));;
                            $expire_time = date("jS F, Y", strtotime($datado));
                            if ($expire_time < $today_time) {
                                $blad = array($datado, "data ustania jest wcześniejsza niż data nadania", "w wierszu " . $row);
                                array_push($wykrytoblad, $blad);
                            }
                        }
                        //walidacja szkolenia do wykorzysania pozniej
//                        if ($col === 3 && $row != 1) {
//                            if (!in_array($val, $szkoleniafirmy)) {
//                                $blad = array($val, "nieprawidłowy symbol szkolenia", "w wierszu " . $row);
//                                array_push($wykrytoblad, $blad);
//                            }
//                        }
                        array_push($wiersz, $val);
                    }
                    echo '</tr>';
                    array_push($tablicapobranychpracownikow, $wiersz);
                    //tutaj przekazujemy pracownikow do drugiego pliku
                    $_SESSION['tablicapobranychpracownikow'] = $tablicapobranychpracownikow;
                    } catch (Exception $er) {
                        echo "err    ";
                    }
                }
                echo '</table>';

                $emailarray = array();
                foreach ($tablicapobranychpracownikow as $wierszyk) {
                    array_push($emailarray, $wierszyk[0]);
                }
                $dups = array();
                $dupsilosc = array();
                foreach (array_count_values($emailarray) as $val => $c) {
                    if ($c > 1) {
                        $dups[] = $val;
                        $dupsilosc[] = $c;
                    }
                }
                if (sizeof($wykrytoblad)) {
                    echo '<br/><p style="color: red;">Ilość wykrytych błędów w tabeli - ' . sizeof($wykrytoblad) . '. Popraw arkusz i załaduj ponownie.</p>';
                    echo '<p>Szczegółowy wykaz błędów zawartych w pliku: </p><table ><tr>';
                    for ($row = 0; $row < sizeof($wykrytoblad); $row++) {
                        echo '<tr>';
                        for ($i = 0; $i < 3; $i++) {
                            echo '<td>' . $wykrytoblad[$row][$i] . '</td>';
                        }
                        echo '</tr>'; 
                    }
                    echo '</table>';
                }
                if (sizeof($dups)) {
                    $wykrytoblad = $dups;
                    echo '<br/><p style="color: red;">Są duplikaty id we wprowadzonych wierszach. Usuń je przed zaimportowaniem danych do bazy.<br/>';
                    echo 'Szczegółowy wykaz powtarzających się adresów email zawartych w pliku: </p><table ><tr>';
                    for ($row = 0; $row < sizeof($dups); $row++) {
                        echo '<tr>';
                        echo '<th> id</th>';
                        echo '<th> ilość powt.</th>';
                        echo '</tr>';
                        echo '<tr>';
                        echo '<td>' . $dups[$row] . '</td>';
                        echo '<td>' . $dupsilosc[$row] . '</td>';
                        echo '</tr>';
                    }
                    echo '</table>';
                }
            }
        }
    }
    ?>
    <script>
        $(document).ready(function () {
            if (<?php error_reporting(0);
                echo sizeof($wykrytoblad)
                ?> === 0 && <?php echo isset($_FILES["file"]) ?> === 1) {
                $('#rodzajimportu').show();
                $('#wyborfirmydiv').show();
            }
        });
    </script>
    <style>
    .selectbar .ui-dropdown {
        width: 350px;
    }
    .selectbar1 .ui-dropdown {
        width: 150px;
    }
</style>
    <div id="rodzajimportu"  class="selectbar" style="height: 80px; display: none; ">
        <p>Rodzaj zasysanych danych</p>
        <select id="rodzajdanych" name="rodzajdanych" style="width: 150px;">
            <option value="wd">wszystkie dane z pliku</option>
            <option value="du">pobierz tylko daty ustania</option>
            <option value="ndu">pobierz jedynie nowe daty ustania</option>
        </select>
    </div>
    <!--    zapelnia to javascript-->
    <div id="wyborfirmydiv" class="selectbar" style="height: 80px; display: none; ">
        <p>Nazwa firmy, do której będą importowani pracownicy</p>
        <select id="IMPfirmauser" name="IMPfirmauser" style="width: 350px;">
        </select>
    </div>
    <div class="row" style="width: 450px;">
        <form id="form2"> 
            <input id="zaladuj" type="submit" value="Załaduj do bazy"  onclick="uploadfile_uzyt_grupa()" 
                   style="padding: 10px; width: 180px; margin-top: 4%; margin-left: auto; margin-right: auto; display: none;"/>
        </form>
    </div>
    <div>
        <p  style="height: 300px;">
        
        </p>
    </div>
</div>
 <div id="ajax_sun" title="ładowanie danych" style="display: none; text-align: center; z-index: -1;" > 
        <img src="/images/ajax_loaderc.gif" alt="ajax" height="65" width="65">;
    </div> 
