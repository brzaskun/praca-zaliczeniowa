
    <script type="text/javascript">
      var fileSelected = function() {
        var file = document.getElementById('file').files[0];
        if (file) {
          var fileSize = 0;
          if (file.size > 1024 * 1024)
            fileSize = (Math.round(file.size * 100 / (1024 * 1024)) / 100).toString() + 'MB';
          else
            fileSize = (Math.round(file.size * 100 / 1024) / 100).toString() + 'KB';

          document.getElementById('fileName').innerHTML = 'Nazwa pliku: ' + file.name;
          document.getElementById('fileSize').innerHTML = 'Rozmiar: ' + fileSize;
          document.getElementById('fileType').innerHTML = 'Typ: ' + file.type;
          var gdziekropka = file.name.lastIndexOf('.');
          var rozszerzenie = file.name.slice(gdziekropka+1,file.name.length);
          document.getElementById('fileType').innerHTML = 'Typ: ' + rozszerzenie;
          if (rozszerzenie==="xls"||rozszerzenie==="xlsx") {
            $('#wyslij').show();
            $('#niewlasciwyplik').hide();
          } else {
            $('#wyslij').hide();
            $('#niewlasciwyplik').show();
          }
        }
      }

 </script>
    
<div style="box-shadow: 10px 10px 5px #888; padding: 30px;  margin-top: 10px; background-color: gainsboro;">
    <div id="plikwzorcowy" style="height: 40px;">
        <a href="/resources/wzorcowy.xls">pobierz plik wzorcowy, żeby zobaczyć jak powinien wyglądać wykaz pracowników do załadowania</a>
    </div>
     <div id="komunikatmax" style="float: inside;">
                <p>Osiągnięto maksymalną dopuszczalną liczba pracowników, która wynosi: <?php error_reporting(0); echo $_SESSION['maxpracownikow'] ?> </p>
     </div>
  <form id="form1" enctype="multipart/form-data" method="post" action="manager.php?zaladowany=tak">
    <div style="height: 220px; width: 500px;">
    <div id="przyciskladowanie" style="height: 60px; width: 200px;border: 1px dashed #BBB; cursor:pointer; text-align: center; vertical-align: middle; display: table-cell;">
        <label for="file" style="font-weight: 800;" onmouseover="$(this).css('background','#339bb9');" onmouseout="$(this).css('background','gainsboro');">Wybierz plik formatu Excel do wczytania</label><br/>
    </div>
    <div style='height: 0px;width:0px; overflow:hidden;'>
        <input type="file" name="file" id="file" onchange="fileSelected();" accept=".xls,.xlsx" style="height: 40px; padding: 5px; padding: 10px; width: 220px; margin-left: 2%; float: left;"/>
    </div>
    <div id="fileName"></div>
    <div id="fileSize"></div>
    <div id="fileType"></div>
    <div class="row" style="width: 400px;">
      <input id="wyslij" type="submit" value="Wczytaj" style="padding: 10px; width: 120px; margin-top: 4%; margin-left: auto; margin-right: auto; display: none"/>
      <p id="niewlasciwyplik" style="display: none; color: red;">Plik ma niewłaściwe rozszerzenie</p>
    </div>
    <div id="progressNumber"></div>
    <div id="progressEfect" style="display: none;"><p> Plik wczytano </p></div>
    </div>
    
  </form>
<?php error_reporting(0);
require_once $_SERVER['DOCUMENT_ROOT'].'/resources/PHPExcel-1.8.1/Classes/PHPExcel/IOFactory.php';
if (session_status()!=2) {
    session_start();
    };
if (isset($_FILES["file"])){
if ($_FILES["file"]["error"] > 0)
  {
  echo "Error: " . $_FILES["file"]["error"] . "<br>";
  }
else
  {
  echo "<p style='font-weight: 900;'>Dane ostanio wczytanego pliku:</p>";
  echo "Nazwa pliku: " . $_FILES["file"]["name"] . "<br/>";
  echo "Typ: " . $_FILES["file"]["type"] . "<br/>";
  echo "Rozmiar: " . ($_FILES["file"]["size"] / 1024) . " kB<br/>";
  echo "Nazwa tymczasowa: " . $_FILES["file"]["tmp_name"]."<br/>";
  $sciezka = $_SERVER['DOCUMENT_ROOT'].'/TestDaneOsobowe/upload/'.$_SESSION['uczestnik']['id'].'/';
  if (!is_dir($sciezka)) {
      mkdir($sciezka);
  }
  if (is_dir($sciezka)) {
    foreach (new DirectoryIterator($sciezka) as $fileInfo)
    if(!$fileInfo->isDot()) {
        chdir($sciezka);
        unlink($fileInfo->getFilename());
    }
    move_uploaded_file($_FILES["file"]["tmp_name"], $sciezka. $_FILES["file"]["name"]);
    echo "Zachowany w katalogu: ".$sciezka.$_FILES["file"]["name"];
  }
  chdir($sciezka);
  $nazwapliku = $_FILES["file"]["name"];
   $locale = 'pl_PL';
  $validLocale = PHPExcel_Settings::setLocale($locale);
  if (!$validLocale) {
    echo 'Unable to set locale to '.$locale." - reverting to en_us<br />\n";
  }
  //wyciagam maile i nazwiska z bazy zeby znalesc duplikat
   require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
    R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
    $sql = 'SELECT email FROM uczestnicy';
    $mailezbazy = R::getCol($sql);
    $sql = 'SELECT imienazwisko FROM uczestnicy';
    $imienazwiskozbazy = R::getCol($sql);
$objPHPExcel = PHPExcel_IOFactory::load($nazwapliku);
//bierzemy tylko pierszy sheet bo moga byc pliki w wieloma sheetami
    $worksheet = $objPHPExcel->getSheet(0);
    $worksheetTitle     = $worksheet->getTitle();
    $highestRow         = $worksheet->getHighestRow(); // e.g. 10
    $highestColumn      = $worksheet->getHighestColumn(); // e.g 'F'
    $highestColumnIndex = PHPExcel_Cell::columnIndexFromString($highestColumn);
    $nrColumns = ord($highestColumn) - 64;
    $wykrytobladkolumny = 0;
   if ($nrColumns!==4) {
       echo '<p  style="color: red;">Błąd!. Nieprawidłowa ilość kolumn '.$nrColumns.'. Powinny być 4; Popraw i ponownie załaduj plik';
       $wykrytobladkolumny = 1;
    }
    if ($wykrytobladkolumny===0) {
    echo "<br>Pobrane dane z arkusza ".$worksheetTitle." mają ";
    echo $nrColumns . ' kolumn (A-' . $highestColumn . ') ';
    echo ' i ' . $highestRow . ' wierszy.';
    $tablicapobranychpracownikow = array();
    $wykrytoblad = array();
    echo '<br><p>Dane wczytane z pliku: </p><table ><tr>';
    for ($row = 1; $row <= $highestRow; ++ $row) {
         echo '<tr>';
            echo '<td>' . $row . '</td>';
            $wiersz = array();
        for ($col = 0; $col < $highestColumnIndex; ++ $col) {
            $cell = $worksheet->getCellByColumnAndRow($col, $row);
            $val = $cell->getValue();
            $dataType = PHPExcel_Cell_DataType::dataTypeForValue($val);
            echo '<td>' . $val . '</td>';
            //walidacja adresu mail
            if ($col === 0 ) {
               if (!filter_var($val, FILTER_VALIDATE_EMAIL)) {
                   $blad = array($val, "niekompletny adres mail", "w wierszu ".$row);
                   array_push($wykrytoblad, $blad);
               }
               if (in_array($val, $mailezbazy)) {
                   $blad = array($val, "taki mail już istnieje w bazie", "w wierszu ".$row);
                   array_push($wykrytoblad, $blad);
               }
            }
            //walidacja imienia i nazwiska
            if ($col === 1 ) {
               if (sizeof($val)<1) {
                   $blad = array($val, "brak imienia i nazwiska", "w wierszu ".$row);
                   array_push($wykrytoblad, $blad);
               }
                if (in_array($val, $imienazwiskozbazy)) {
                   $blad = array($val, "pracownik o takim imieniu i nazwisku już istnieje w bazie", "w wierszu ".$row);
                   array_push($wykrytoblad, $blad);
               }
            }
            //walidacja pci
            if ($col === 2 ) {
               if ($val!=='k'&&$val!=='m') {
                   $blad = array($val, "nieprawidłowy symbol płci", "w wierszu ".$row);
                   array_push($wykrytoblad, $blad);
               }
            }
            //walidacja szkolenia
            if ($col === 3 ) {
               if ($val!=='szkolenie1'&&$val!=='szkolenie2'&&$val!=='szkolenie3'&&$val!=='szkoleniedemo') {
                   $blad = array($val, "nieprawidłowy symbol szkolenia", "w wierszu ".$row);
                   array_push($wykrytoblad, $blad);
               }
            }
            array_push($wiersz, $val);
        }
        echo '</tr>';
        array_push($tablicapobranychpracownikow,$wiersz);
        $_SESSION['tablicapobranychpracownikow'] = $tablicapobranychpracownikow;
    }
    echo '</table>';
    if (sizeof($wykrytoblad)) {
        echo '<br/><p style="color: red;">Ilość wykrytych błędów w tabeli - '.sizeof($wykrytoblad).'. Popraw arkusz i załaduj ponownie.</p>';
        echo '<p>Szczegółowy wykaz błędów: </p><table ><tr>';
        for ($row = 0; $row < sizeof($wykrytoblad); $row++) {
            echo '<tr>';
            for ($i = 0; $i < 3; $i++) {
                echo '<td>' . $wykrytoblad[$row][$i] . '</td>';
            }
             echo '</tr>';
            }
         echo '</table>';
        }
     $lprac = $_SESSION['liczbapracownikow']-1;
     $nadmiar = sizeof($tablicapobranychpracownikow) - $_SESSION['maxpracownikow'] + $lprac ;
     if($nadmiar>0) {
         echo '<p  style="color: red;">Błąd ilości!. Ilość wierszy w arkuszu: '.sizeof($tablicapobranychpracownikow).', po uwzględnieniu wprowadzonych już pracowników w liczbie: '.$lprac.', przekracza dopuszczalą liczbę możliwych do dodania pracowników wynoszącą: '.$_SESSION['maxpracownikow'].' o: '.$nadmiar.'.</p>';
     }
    }
}
  }

?>

        <script>
            $(document).ready(function() { 
                var wykrytoblad = <?php error_reporting(0); echo sizeof($wykrytoblad);?>;
                var nadmiar = <?php error_reporting(0); echo $nadmiar;?>;
                    if (wykrytoblad===0 && nadmiar <= 0){
                    $('#zaladuj').show();
            }
        });
        </script>
        <div class="row" style="width: 150px;">
          <form id="form2" method="post" action="upload_manager.php">
            <input id="zaladuj" type="submit" value="Załaduj do bazy"  style="padding: 10px; width: 180px; margin-top: 4%; margin-left: auto; margin-right: auto; display: none;"/>
        </form>
    </div>
</div>
