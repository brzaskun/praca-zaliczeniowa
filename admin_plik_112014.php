<?php error_reporting(0);
require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
$_wynik_firmaall = R::getAll('SELECT * FROM zakladpracy');
?>
 
<div id="panelladowaniapliku" style="box-shadow: 10px 10px 5px #888; padding: 30px;  margin-top: 10px; background-color: #e9e9e9;">
    <div id="plikwzorcowy" style="height: 40px;">
        <a href="/resources/wzorcowy.xls">pobierz plik wzorcowy, żeby zobaczyć jak powinien wyglądać wykaz pracowników do załadowania</a>
    </div>
    <form id="form1" enctype="multipart/form-data" method="post" action="admin112014_uploadfile.php"> 
        <div style="height: 220px; width: 500px;">
            <div id="przyciskladowanie" style="cursor:pointer; text-align: center; vertical-align: middle; display: table-cell;">
                <label for="file" style="font-weight: 800; padding: 10px;" onmouseover="$(this).css('background', '#339bb9');" onmouseout="$(this).css('background', 'gainsboro');">Wybierz plik formatu Excel do wczytania</label><br/>
            </div>
            <div style='height: 0px;width:0px; overflow:hidden;'>
                <input type="file" name="file" id="file" onchange="fileSelected();" onclick="$('#divzbledami').hide();" accept=".xls,.xlsx" />
            </div>
            <div id="fileName"></div> 
            <div id="fileSize"></div>
            <div id="fileType"></div>
            <div class="row" style="width: 400px;">
                <input id="wyslij" type="submit" value="Wczytaj" onclick="$('#divzbledami').show();"
                       style="padding: 10px; width: 120px; margin-top: 4%; margin-left: auto; margin-right: auto; display: none"/>
                <p id="niewlasciwyplik" style="display: none; color: red;">Plik ma niewłaściwe rozszerzenie</p>
            </div>
            <div id="progressNumber"></div>
            <div id="progressEfect" style="display: none;"><p> Plik wczytano </p></div>
        </div>

    </form>
    <div id="divzbledami">
    <?php
    error_reporting(0);
    require_once $_SERVER['DOCUMENT_ROOT'] . '/resources/PHPExcel-1.8.1/Classes/PHPExcel/IOFactory.php';
    if (session_status() != 2) {
        session_start();
    };
    if (isset($_FILES["file"])) {
        if ($_FILES["file"]["error"] > 0) {
            echo "Wystąpił błąd podczas pobierania pliku: " . $_FILES["file"]["error"] . "<br>";
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
            chdir($sciezka);
            $nazwapliku = $_FILES["file"]["name"];
            $locale = 'pl_PL';
            $validLocale = PHPExcel_Settings::setLocale($locale);
            if (!$validLocale) {
                echo 'Nie mogę ustawić lokalizacji pliku na  ' . $locale . " - wracam do wersji angielskojęzycznej<br />\n";
            }
            //wyciagam maile i nazwiska z bazy zeby znalesc duplikat
            //$sql = 'SELECT email FROM uczestnicy';
            //$mailezbazy = R::getCol($sql); 
            $sql = 'SELECT nazwa FROM szkoleniewykaz';
            $szkoleniafirmy = R::getCol($sql);
            //$sql = 'SELECT imienazwisko FROM uczestnicy';
            //$imienazwiskozbazy = R::getCol($sql);
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
            $nrColumns = ord($highestColumn) - 64;
            $wykrytobladkolumny = 0;
            if ($nrColumns < 6) {
                echo '<p  style="color: red;">Błąd!. Nieprawidłowa ilość kolumn ' . $nrColumns . '. Mysi być przynajmniej 6; Sprawdź ilośc kolumn w pliku i ponownie go załaduj.';
                $wykrytobladkolumny = 1;
                die();
            }
            if ($wykrytobladkolumny === 0) {
                echo "<br>Pobrane dane z arkusza " . $worksheetTitle . " mają ";
                echo $nrColumns . ' kolumn (A-' . $highestColumn . ') ';
                echo ' i ' . $highestRow . ' wierszy.';
                $_SESSION['highestRow'] = $highestRow-1;
                $tablicapobranychpracownikow = array();
                $wykrytoblad = array();
                echo '<br><p>Dane wczytane z pliku: </p>';
                echo '<div style="width:1200px;"><table  id="tabeladanepobrane" class="compact" class="ckeditor" style="margin: 0px;">';
                echo '<thead>';
                echo '<tr>';
                echo '<th>lp</th>';
                $wiersz = array();
                for ($col = 0; $col < $highestColumnIndex; ++$col) {
                    $cell = $worksheet->getCellByColumnAndRow($col, 1);
                    $val = $cell->getValue();
                    echo '<th>' . $val . '</th>';
                    array_push($wiersz, $val);
                }
                array_push($tablicapobranychpracownikow, $wiersz);
                echo '</tr>';
                echo '</thead>';
                echo '<tbody>';
                for ($row = 2; $row <= $highestRow; ++$row) {
                    echo '<tr>';
                    $lp = $row-1;
                    echo '<td>' . $lp . '</td>';
                    $wiersz = array();
                    for ($col = 0; $col < $highestColumnIndex; ++$col) {
                        $cell = $worksheet->getCellByColumnAndRow($col, $row);
                        $val = $cell->getValue();
                        $dataType = PHPExcel_Cell_DataType::dataTypeForValue($val);
                        echo '<td>' . $val . '</td>';
                        //walidacja adresu mail
                        if ($col === 0 && $row == 1) { 
                            if ($val != "adres e-mail" && $val != "adres email") {
                                echo "Zmieniono treść nagłówków kolumn. Proszę porównać z plikiem wzorcowym. Nie można dalej przetwarzać tabeli";
                                die();
                            }
                        } 
                        if ($col === 0 && $row != 1) { 
                            if ($val instanceof PHPExcel_RichText) {
                                $blad = array($val, "pole zawierające adres email w nieprawidłowym formacie, prawdopodobnie przeklejone", "w wierszu " . $lp);
                                array_push($wykrytoblad, $blad);
                            }
                            if (!filter_var($val, FILTER_VALIDATE_EMAIL)) {
                                $blad = array($val, "niekompletny adres mail", "w wierszu " . $lp);
                                array_push($wykrytoblad, $blad);
                            }
//                            if (in_array($val, $mailezbazy)) {
//                                $blad = array($val, "taki mail już istnieje w bazie", "w wierszu " . $lp);
//                                array_push($wykrytoblad, $blad);
//                            }
                        }
                        //walidacja imienia i nazwiska
                        if ($col === 1 && $row != 1) {
                            if ($val instanceof PHPExcel_RichText) {
                                $blad = array($val, "pole zawierające imię i nazwisko w nieprawidłowym formacie, prawdopodobnie przeklejone", "w wierszu " . $lp);
                                array_push($wykrytoblad, $blad);
                            }
                            if (sizeof($val) < 1) {
                                $blad = array($val, "brak imienia i nazwiska", "w wierszu " . $lp);
                                array_push($wykrytoblad, $blad);
                            }
//                            if (in_array($val, $imienazwiskozbazy)) {
//                                $blad = array($val, "pracownik o takim imieniu i nazwisku już istnieje w bazie", "w wierszu " . $row);
//                                array_push($wykrytoblad, $blad);
//                            }
                        }
                        //walidacja pci
                        if ($col === 2 && $row != 1) {
                            if ($val !== 'k' && $val !== 'm') {
                                $blad = array($val, "nieprawidłowy symbol płci", "w wierszu " . $lp);
                                array_push($wykrytoblad, $blad);
                            }
                        }
                        //walidacja szkolenia
                        if ($col === 3 && $row != 1) {
                            if (!in_array($val, $szkoleniafirmy)) {
                                $blad = array($val, "nieprawidłowy symbol szkolenia", "w wierszu " . $lp);
                                array_push($wykrytoblad, $blad);
                            }
                        }
                        array_push($wiersz, $val);
                    }
                    echo '</tr>';
                    array_push($tablicapobranychpracownikow, $wiersz);
                    $_SESSION['tablicapobranychpracownikow'] = $tablicapobranychpracownikow;
                }
                echo '</tbody>';
                echo '</table></div>';
                //ta czesc sluzy do obliczen duplikatow maili i szkolen
                $dupsbaza = array();
                $dupsplik = array();
                $email_szkolenie_array = array();
                foreach ($tablicapobranychpracownikow as $wierszyk) {
                    $email_szkolenie_array[$wierszyk[0]] = $wierszyk[3];
                    $parametr = "email='$wierszyk[0]' AND nazwaszkolenia='$wierszyk[3]' AND (dataustania IS NULL OR CHAR_LENGTH(dataustania) < 1)";
                    $jestwbazie = R::findAll("uczestnicy", $parametr);
//                    $jestwbazie = R::getAll( 'Select * FROM uczestnicy WHERE email = :email n', 
//                        array(':email'=>$wierszyk[0],':nazwaszkolenia'=>$wierszyk[3]) 
//                    );
                        $jeden = 0;
                        foreach ($tablicapobranychpracownikow as $wierszyk2) {
                            if ($wierszyk2[0]==$wierszyk[0] && $wierszyk2[3]==$wierszyk[3]) {
                                if ($jeden == 0) {
                                    $jeden = 1;
                                } else if ($jeden == 1) {
                                    array_push($dupsplik, $wierszyk2);
                                }
                            }
                        }
                    if (!empty($jestwbazie)) {
                        array_push($dupsbaza, $wierszyk);
                    } 
                }
                //koniec czesci do obliczen duplikatow maili i szkolen
                if (sizeof($wykrytoblad)) {
                    echo '<br/><p style="color: red;">Ilość wykrytych błędów w tabeli - ' . sizeof($wykrytoblad) . '. Popraw arkusz i załaduj ponownie.</p>';
                    echo '<p>Szczegółowy wykaz błędów zawartych w pliku: </p>';
                    echo '<div style="width:600px;"><table class="compact" class="ckeditor" id="tabelawierszebledy">';
                    echo '<thead>';
                    echo '<tr>';
                    echo '<th>lp</th>';
                    echo '<th>zawartość pola</th>';
                    echo '<th>błąd</th>';
                    echo '<th>wiersz</th>';
                    echo '</tr>';
                    echo '<thead>';
                    echo '<tbody>';
                    for ($row = 0; $row < sizeof($wykrytoblad); $row++) {
                        $lp = $row+1;
                        echo '<tr>';
                        echo '<td style="text-align: center;">'.$lp.'</td>';
                        for ($i = 0; $i < 3; $i++) {
                            echo '<td>' . $wykrytoblad[$row][$i] . '</td>';
                        }
                        echo '</tr>';
                    }
                    echo '<tbody>';
                    echo '</table></div>';
                }
                if (sizeof($dupsbaza)) {
                    $wykrytoblad = $dupsbaza;
                    echo '<br/><p style="color: red;">We wprowadzonych wierszach są już pary email-szkolenie, które występują już w bazie. Zweryfikuj plik importu. Czy nazwa szkolenia nie powinna być inna?.<br/>';
                    echo 'Szczegółowy wykaz powtarzających się adresów email-szkoleń zawartych w bazie i pliku: </p>';
                    echo '<div style="width:500px;"><table class="compact" class="ckeditor" id="tabeladuplikatybaza">';
                    echo '<thead>';
                    echo '<tr>';
                    echo '<th>lp</th>';
                    echo '<th>email</th>';
                    echo '<th>szkolenie</th>';
                    echo '</tr>';
                    echo '<thead>';
                    echo '<tbody>';
                    for ($row = 0; $row < sizeof($dupsbaza); $row++) {
                        $lp = $row+1;
                        echo '<tr>';
                        echo '<td style="text-align: center;">'.$lp.'</td>';
                        echo '<td>' . $dupsbaza[$row][0] . '</td>';
                        echo '<td>' . $dupsbaza[$row][3] . '</td>';
                        echo '</tr>';
                    }
                    echo '<tbody>';
                    echo '</table></div>';
                }
                if (sizeof($dupsplik)) {
                    $wykrytoblad = $dupsplik;
                    echo '<br/><p style="color: red;">We wprowadzonych wierszach są już pary email-szkolenie, które występują już w bazie. Zweryfikuj plik importu. Czy nazwa szkolenia nie powinna być inna?.<br/>';
                    echo 'Szczegółowy wykaz powtarzających się duplikatów adresów email-szkoleń pliku: </p>';
                    echo '<div style="width:500px;"><table class="compact" class="ckeditor" id="tabeladuplikatyplik">';
                    echo '<thead>';
                    echo '<tr>';
                    echo '<th>lp</th>';
                    echo '<th>email</th>';
                    echo '<th>szkolenie</th>';
                    echo '</tr>';
                    echo '<thead>';
                    echo '<tbody>';
                    for ($row = 0; $row < sizeof($dupsplik); $row++) {
                        $lp = $row+1;
                        echo '<tr>';
                        echo '<td style="text-align: center;">'.$lp.'</td>';
                        echo '<td>' . $dupsplik[$row][0] . '</td>';
                        echo '<td>' . $dupsplik[$row][3] . '</td>';
                        echo '</tr>';
                    }
                    echo '<tbody>';
                    echo '</table></div>';
                }
            }
        }
    } 
    ?>
   </div>
      <script> 
        $(document).ready(function() {
             try {
                var uTable = $('#tabelawierszebledy').dataTable( {
                        "bJQueryUI": true, 
                        "sPaginationType": "full_numbers",
                        "keys": true,
                        "select": "single",
                        "language": {
                                    "url": "resources/dataTableNew/Polish.json"
                                },
                    });
                } catch(e){}
            try {
                var uTable = $('#tabeladuplikatybaza').dataTable( {
                        "bJQueryUI": true, 
                        "sPaginationType": "full_numbers",
                        "keys": true,
                        "select": "single",
                        "language": {
                                    "url": "resources/dataTableNew/Polish.json"
                                },
                    });
                } catch(e){}
            try {
            var uTable = $('#tabeladanepobrane').dataTable( {
                        "bJQueryUI": true, 
                        "sPaginationType": "full_numbers",
                        "keys": true,
                        "select": "single",
                        "language": {
                                    "url": "resources/dataTableNew/Polish.json"
                                },
                    });
                } catch(e){}
                    uTable.fnSort([[0, 'asc']]);
                     var uTable = $('#tabeladuplikatyplik').dataTable( {
                        "bJQueryUI": true, 
                        "sPaginationType": "full_numbers",
                        "keys": true,
                        "select": "single",
                        "language": {
                                    "url": "resources/dataTableNew/Polish.json"
                                },
                    });
                    uTable.fnSort([[0, 'asc']]);
            if (<?php error_reporting(0);
    echo sizeof($wykrytoblad) ?> === 0 && <?php echo isset($_FILES["file"]) ?> === 1) {
                
                      $('#wyborfirmydiv').show();
            }
        });
 </script>
    <div id="wyborfirmydiv" style="height: 80px; display: none;">
        <p>Nazwa firmy, do której będą importowani pracownicy w liczbie <?php echo $_SESSION['highestRow'] ?></p>
        <select id="IMPfirmauser" name="IMPfirmauser" style="width: 250px;">
        </select>
    </div>
<!--    <div class="row" style="width: 450px;">
        <form id="form2" method="post" action="upload_admin_112014.php">
            <input id="zaladuj" type="submit" value="Załaduj do bazy"  
                   style="padding: 10px; width: 180px; margin-top: 4%; margin-left: auto; margin-right: auto; display: none;"/>
        </form>
    </div>-->
</div>
<div class="row" id="panelprzyciskladowanie"  style="box-shadow: 10px 10px 5px #888; padding: 30px; background-color: #e9e9e9;">
        <form id="form2" >
            <input id="zaladuj" type="button" value="Załaduj do bazy"  onclick="uploadusers()"
                   style="padding: 10px; width: 180px; margin-top: 4%; margin-left: auto; margin-right: auto; display: none;"/>
            <div id="rezultat">
                <p id="rezultat_text"></p>
            </div>
        </form>
</div>
