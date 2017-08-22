<script type="text/javascript">
    $(document).ready(function() {
        $('#mb1').puimenubar({
            autoDisplay: true
        }); 
     });
</script>
<ul id="mb1"> 
    <li><a data-icon=" ui-icon-person" href="/admin112014_uzytkownicy.php"  style="width: 120px;">Użytkownicy</a></li>  
    <li><a data-icon="ui-icon-suitcase" href="/admin112014_firmy.php"  style="width: 120px;">Firmy</a></li>  
    <li id="menuszkolenia"><a data-icon="ui-icon-document" href="/admin112014_szkolenia.php"  style="width: 120px;">Szkolenia</a></li>  
    <li><a data-icon="ui-icon-circle-check" href="/admin112014_testy.php"  style="width: 120px;">Testy</a></li>  
    <li id="menuupowaznieniagrupa"><a data-icon="ui-icon-folder-collapsed" href="/admin112014_uzytkownik_grupy.php"  style="width: 120px;">Up.Grupy</a>
          <ul>
            <li>
                <li><a data-icon="ui-icon-circle-arrow-n" href="./admin_uzytkownik_grupa_uploadfile.php">Import z pliku</a></li>   
                <li><a data-icon="ui-icon-circle-arrow-n" href="./admin112014_upowaznienie_grupy.php">Upoważnienie - grupy</a></li>   
            </li>
        </ul>
    </li>
    <li><a data-icon="ui-icon-locked" href="/admin082016_all.php"  style="width: 120px;">Duża tabela</a></li>
    <li><a data-icon="ui-icon-locked" href="/admin112014_haslo.php"  style="width: 120px;">Hasło</a></li>
    <li id="menubackup"><a data-icon="ui-icon-suitcase" href="/admin072017_backup.php"  style="width: 120px;">Backup</a></li>
    <li id="menuupowaznienia"><a data-icon="ui-icon-suitcase" href="/admin072017_upowaznienia.php"  style="width: 120px;">Upoważnienia</a></li>
</ul>   

