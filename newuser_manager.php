<?php error_reporting(0);
  if(session_status()!=2){     session_start(); };
  require_once($_SERVER['DOCUMENT_ROOT'].'/resources/php/Rb.php');
  R::setup('mysql:host=localhost;dbname=tb152026_testdane', 'tb152026_madrylo','Testdane7005*');
  $email = $_POST['Nemail'];
  $imienazwisko = $_POST['Nimienazwisko'];
  $firma = $_POST['Nfirmauser'];
  $uprawnienia = $_POST['Nuprawnieniauser'];
  $plec = $_POST['Nplecuser'];
  $szkolenieuser = $_POST['Nszkolenieuser'];
    $sql = 'SELECT email FROM uczestnicy';
    $mailezbazy = R::getCol($sql);
    $sql = 'SELECT imienazwisko FROM uczestnicy';
    $imienazwiskozbazy = R::getCol($sql);
   if (in_array($email, $mailezbazy)) {
         header("Location: manager.php?error=Błąd! Istnieje już taki email w bazie danych!");
        exit();
    }
    if (in_array($imienazwisko, $imienazwiskozbazy)) {
        header("Location: manager.php?error=Błąd! Istnieje już takie imie i nazwisko w bazie danych!");
        exit();
   }
  try {
  $sql = "INSERT INTO  `uczestnicy` (`id`, `email` ,`imienazwisko` ,`plec` ,`firma` , `nazwaszkolenia`, `uprawnienia` ,`wyslanymailupr` ,`sessionstart` ,
    `sessionend` ,`wyniktestu` ,`wyslanycert`)
    VALUES (0, '$email',  '$imienazwisko', '$plec', '$firma', '$szkolenieuser', '$uprawnienia' , 0, NULL , NULL , 0 , 0);";
  R::exec($sql);
  } catch (Exception $e){
      header("Location: manager.php?error=Błąd! Istnieje już taki email w bazie danych!");
      exit();
  }
$poziomzaswiadczenie = null;
switch ($szkolenieuser) {
    case 'szkolenie1' :
        $poziomzaswiadczenie = "poziom BASIC";
        break;
    case 'szkolenie2' :
        $poziomzaswiadczenie = "poziom OPTIMUM";
        break;
    case 'szkolenie3' :
        $poziomzaswiadczenie = "poziom PREMIUM";
        break;
    case 'szkoleniedemo' :
        $poziomzaswiadczenie = "wersja DEMO";
        break;
}

  //wysylanie maila dla nowododanego
  require_once $_SERVER['DOCUMENT_ROOT'].'/resources/swiftmailer/swift_required.php';
  try {
  $transport = Swift_SmtpTransport::newInstance('az0066.srv.az.pl', 465);
    $transport->setEncryption('ssl');
    $transport->setUsername('e-szkolenia@odomg.pl');
    $transport->setPassword('Odo1234*');
    // Create the Mailer using your created Transport
    $mailer = Swift_Mailer::newInstance($transport);
    // Create a message
    $message = null;
    if ($plec=="k") {
    $message = Swift_Message::newInstance('Rejestracja do e-szkolenia '.$poziomzaswiadczenie)
    ->setContentType('text/plain')
    ->setFrom(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
    ->setReplyTo(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
    ->setTo(array($email => $imienazwisko))
    ->setBody('
        <h4>Szanowna Pani '.$imienazwisko.'</h4>
        <h4>Dzień dobry,</h4> 
        <div style="width: 550px; text-align: justify;">
        <p>Pani adres email został zarejestrowany w systemie e-learningu. </p>
        <p>Zapraszamy do wzięcia udziału w szkoleniu przygotowanym przez firmę ODO Management Group. 
        Szkolenie kończy się testem wielokrotnego wyboru. Po ukończeniu testu generowane jest stosowne zaświadczenie (plik PDF). 
        W przypadku niezaliczenia testu należy zalogować się jeszcze raz i ponownie przystąpić do szkolenia i do testu.</p>
        <p style="color: rgb(243,112,33);">Poniższy link pozostaje aktywny przez 24 godziny od momentu pierwszego zalogowania do systemu.</p>
        <p>Kliknij w ten link, aby rozpocząć <a href="https://szkolenie.odomg.pl/index.php?mail='.urlencode($email).'">SZKOLENIE</a> </p>
        <p><a href="https://szkolenie.odomg.pl/resources/css/pics/instrukcja.pdf">instrukcja do e-szkolenia,</a>
        <p>Zalogowanie oznacza akceptację <a href="https://szkolenie.odomg.pl/resources/css/pics/regulamin.pdf">regulaminu</a>.
         Wzięcie udziału w szkoleniu wymaga umieszczenia na komputerze użytkownika plików cookie.
         Rozpoczęcie szkolenia oznacza wyrażenie zgody na użycie plików cookie.</p>
          <p>Wymagania systemowe umożliwiające prawidłowe funkcjonowanie programu: zainstalowany program Acrobat Reader (od wersji 10.0). <br/>
		 Rekomendowane przeglądarki: Chrome, Mozilla Firefox, Internet Explorer (w ostatnich wersjach)</p>
        <p>Powodzenia!</p>
        <p>Zespół ODO Management Group</p><br/>
        <p style="font-weight: bold;">Tę wiadomość wygenerowano automatycznie,  prosimy na nią nie odpowiadać.</p>
        </div>
    ','text/html');
    } else {
      $message = Swift_Message::newInstance('Rejestracja do e-szkolenia '.$poziomzaswiadczenie)
    ->setContentType('text/plain')
    ->setFrom(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
    ->setTo(array($email => $imienazwisko))
    ->setReplyTo(array('e-szkolenia@odomg.pl' => 'ODO Management Group'))
    ->setBody('
        <h4>Dzień dobry,</h4>
        <div style="width: 550px; text-align: justify;">
        <p>Pana adres email został zarejestrowany w systemie e-learningu. </p>
        <p>Zapraszamy do wzięcia udziału w szkoleniu przygotowanym przez firmę ODO Management Group. 
        Szkolenie kończy się testem wielokrotnego wyboru. Po ukończeniu testu generowane jest stosowne zaświadczenie (plik PDF). 
        W przypadku niezaliczenia testu należy zalogować się jeszcze raz i ponownie przystąpić do szkolenia i do testu.</p>
        <p style="color: rgb(243,112,33);">Poniższy link pozostaje aktywny przez 24 godziny od momentu pierwszego zalogowania do systemu.</p>
        <p>Kliknij w ten link, aby rozpocząć <a href="https://szkolenie.odomg.pl/index.php?mail='.urlencode($email).'">SZKOLENIE</a> </p>
        <p><a href="https://szkolenie.odomg.pl/resources/css/pics/instrukcja.pdf">instrukcja do e-szkolenia,</a>
        <p>Zalogowanie oznacza akceptację <a href="https://szkolenie.odomg.pl/resources/css/pics/regulamin.pdf">regulaminu</a>.
         Wzięcie udziału w szkoleniu wymaga umieszczenia na komputerze użytkownika plików cookie.
         Rozpoczęcie szkolenia oznacza wyrażenie zgody na użycie plików cookie.</p>
		 <p>Wymagania systemowe umożliwiające prawidłowe funkcjonowanie programu: zainstalowany program Acrobat Reader (od wersji 10.0). <br/>
		 Rekomendowane przeglądarki: Chrome, Mozilla Firefox, Internet Explorer (w ostatnich wersjach)</p>
        <p>Powodzenia!</p>
        <p>Zespół ODO Management Group</p><br/>
        <p style="font-weight: bold;">Tę wiadomość wygenerowano automatycznie,  prosimy na nią nie odpowiadać.</p>
        </div>
    ','text/html');
    }
    // Send the message

    $result = $mailer->send($message);
    if($result==1){
      $parametr = "email = '$email'";
      $uczestnik = R::findOne('uczestnicy', $parametr);
      $properties = $uczestnik->getProperties()['id'];
      $url = "Location: manager.php?info=Nowy użytkownik ".$imienazwisko." dodany. Mail aktywacyjny wysłany";
      $sql = "UPDATE  `uczestnicy` SET  `wyslanymailupr` = '1' WHERE  `uczestnicy`.`id` = $properties;";
      R::exec($sql);
      header($url);
      exit();
    } else {
      $url = "Location: manager.php?info=Nowy użytkownik ".$imienazwisko." dodany. Mail aktywacyjny niewysłany. Sprawdz poprawność adresu!";
      header($url);
      exit();
    }
  } catch (Exception $e){
      header("Location: manager.php?error=Wystąpił problem z wysłaniem maila aktywującego!");
      exit();
  }
  
?>
